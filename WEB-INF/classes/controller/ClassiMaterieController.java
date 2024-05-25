package controller;

import java.io.IOException;
import java.nio.channels.NonReadableChannelException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.core.impl.ExtendedClassInfo;
import org.eclipse.jdt.internal.compiler.classfmt.NonNullDefaultAwareTypeAnnotationWalker;

import com.mysql.cj.x.protobuf.MysqlxCrud.Update;

import dao.StudentiDao;
import dao.UserDao;
import dao.ValutazioniDao;
import dao.InsegnantiDao;
import dao.Insegnanti_classi_materieDao;
import dao.MaterieDao;
import dao.ClassiDao;

import model.User;
import model.Studenti;
import model.Insegnanti;
import model.Materie;
import model.Classi;
import model.Insegnanti_classi_materie;

import utils.StringUtils;

@WebServlet("/ClassiMaterie")
public class ClassiMaterieController extends AuthorizedController {
	private static final long serialVersionUID = 1L;


	private StudentiDao studentiDao = null;
	private ClassiDao classiDao = null;
	private MaterieDao materieDao = null;
	private Insegnanti_classi_materieDao insegnanti_classi_materieDao = null;


	public ClassiMaterieController() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		try {
			super.init(config);

			
			studentiDao = new StudentiDao(
					getServletContext().getRealPath("/") + config.getServletContext().getInitParameter("config"),
					this.id_utente_sessione);
			classiDao = new ClassiDao(
					getServletContext().getRealPath("/") + config.getServletContext().getInitParameter("config"),
					this.id_utente_sessione);
			materieDao = new MaterieDao(
					getServletContext().getRealPath("/") + config.getServletContext().getInitParameter("config"),
					this.id_utente_sessione);
			
			insegnanti_classi_materieDao = new Insegnanti_classi_materieDao(
					getServletContext().getRealPath("/") + config.getServletContext().getInitParameter("config"),
					this.id_utente_sessione);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doGet(request, response);

		if (this.isAuthorized) {
			HttpSession session = request.getSession();
			Integer idRoleUser = ((User) session.getAttribute("session_user")).getIdRole();

			if (!idRoleUser.equals(2) && !idRoleUser.equals(4)) {

				session.setAttribute("paginaAttuale", "classiMaterie");
				String action = null;
				int id = 0;
				User user = null;
				String view = null;
				String username = null;

				try {
	
					action = request.getParameter("action");
					request.setAttribute("action", action);

					if (request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
						id = Integer.parseInt(request.getParameter("id"));
					}


						view = "view/editClassiMaterie/index.jsp";

						if ("DELETECLASSE".equals(action)) {
							classiDao.deleteClasse(id);
							view = "view/editClassiMaterie/index.jsp";
						}
						if ("EDITCLASSE".equals(action)) {
							Classi classeSelect = classiDao.getClassiById(id);
							view = "view/editClassiMaterie/edit.jsp";
							request.setAttribute("classeSelect", classeSelect);
							request.setAttribute("id", id);
						}
						if ("DELETEMATERIA".equals(action)) {
							materieDao.deleteMateria(id);
							view = "view/editClassiMaterie/index.jsp";
						}
						if ("EDITMATERIA".equals(action)) {
							Materie materiaSelect = materieDao.getMateriaById(id);
							request.setAttribute("materiaSelect", materiaSelect);
							request.setAttribute("id", id);
							view = "view/editClassiMaterie/edit.jsp";
						}
						if ("INSERTCLASSE".equals(action)) {
							Classi classeSelect = new Classi();
							view = "view/editClassiMaterie/edit.jsp";
							request.setAttribute("classeSelect", classeSelect);
						}
						if ("INSERTMATERIA".equals(action)) {
							Materie materiaSelect = new Materie();
							request.setAttribute("materiaSelect", materiaSelect);
							view = "view/editClassiMaterie/edit.jsp";
						}
						

						List<Classi> listClassi = classiDao.getAllClassi();
						request.setAttribute("classi", listClassi);

						List<Materie> listMaterie = materieDao.getAllMaterie();
						request.setAttribute("materie", listMaterie);

						//
						
					        Map<Integer, Integer> studentiCountPerClasse = new HashMap<>();
					        
					        for (Classi classe : listClassi) {
					            int classId = classe.getId_classe();
					            int studentCount = studentiDao.getStudentiByIdClasse(classId).size();
					            studentiCountPerClasse.put(classId, studentCount);
					        }
					      
					             
					        Map<Integer, Integer> classiCountPerMateria = new HashMap<>();
					        
					        for (Materie materia : listMaterie) {
					            int materiaId = materia.getId_materia();
					            int classiCount = insegnanti_classi_materieDao.getNumeroDiClassiPerMateria(materiaId);
					            classiCountPerMateria.put(materiaId, classiCount);
					        }
						
						request.setAttribute("studentiCountPerClasse", studentiCountPerClasse);
						
						request.setAttribute("classiCountPerMateria", classiCountPerMateria);

		

						RequestDispatcher dispatcher = request.getRequestDispatcher(view);
						dispatcher.forward(request, response);
					
				} catch (Exception e) {
					processError(request, response);
					e.printStackTrace();
				}

			} else {
				response.sendRedirect("login");
			}
		} else {
			response.sendRedirect("login");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Classi classe = new Classi();
			Materie materia = new Materie();
			Integer id = null;

			super.doPost(request, response);

			if (this.isAuthorized) {

				if (request.getParameter("idclasse") != null) {
						
					if (request.getParameter("idclasse") != null && Integer.parseInt(request.getParameter("idclasse")) > 0) {
						id = Integer.parseInt(request.getParameter("idclasse"));
						classe.setId_classe(id);
					}
					
					
					classe.setNome_classe(request.getParameter("classeNome"));
					
					if (id == null) {// insert 
						classiDao.insertClasse(classe);
					}else {
						classiDao.updateClasse(classe);
					}

				}

				if (request.getParameter("idmateria") != null) {
					
					if (request.getParameter("idmateria") != null && Integer.parseInt(request.getParameter("idmateria")) > 0) {
						id = Integer.parseInt(request.getParameter("idmateria"));
						materia.setId_materia(id);
					}
					
					
					materia.setNome_materia(request.getParameter("materiaNome"));
					
					if (id == null) {// insert 
						
						materieDao.insertMateria(materia);
					}else {
						materieDao.updateMateria(materia);
					}

				}


				response.sendRedirect("ClassiMaterie");
			} else {
				response.sendRedirect("login");
			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}
}
