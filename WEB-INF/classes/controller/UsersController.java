package controller;

import java.io.IOException;
import java.nio.channels.NonReadableChannelException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

@WebServlet("/Users")
public class UsersController extends AuthorizedController {
	private static final long serialVersionUID = 1L;

	private UserDao usersDao = null;
	private StudentiDao studentiDao = null;
	private InsegnantiDao insegnantiDao = null;
	private ClassiDao classiDao = null;
	private MaterieDao materieDao = null;
	private Insegnanti_classi_materieDao insegnanti_classi_materieDao = null;
	private ValutazioniDao valutazioniDao = null;

	public UsersController() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		try {
			super.init(config);

			usersDao = new UserDao(
					getServletContext().getRealPath("/") + config.getServletContext().getInitParameter("config"), this.id_utente_sessione);
			studentiDao = new StudentiDao(
					getServletContext().getRealPath("/") + config.getServletContext().getInitParameter("config"), this.id_utente_sessione);
			classiDao = new ClassiDao(
					getServletContext().getRealPath("/") + config.getServletContext().getInitParameter("config"), this.id_utente_sessione);
			materieDao = new MaterieDao(
					getServletContext().getRealPath("/") + config.getServletContext().getInitParameter("config"), this.id_utente_sessione);
			insegnantiDao = new InsegnantiDao(
					getServletContext().getRealPath("/") + config.getServletContext().getInitParameter("config"), this.id_utente_sessione);
			insegnanti_classi_materieDao = new Insegnanti_classi_materieDao(
					getServletContext().getRealPath("/") + config.getServletContext().getInitParameter("config"), this.id_utente_sessione);
			valutazioniDao = new ValutazioniDao(
					getServletContext().getRealPath("/") + config.getServletContext().getInitParameter("config"), this.id_utente_sessione);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doGet(request, response);
		
		
		if(this.isAuthorized) {
			HttpSession session = request.getSession();
			Integer idRoleUser = ((User)session.getAttribute("session_user")).getIdRole();
			
			if(!idRoleUser.equals(2) && !idRoleUser.equals(4) ) {
		
		
		session.setAttribute("paginaAttuale", "user");
		String action = null;
		int id = 0;
		User user = null;
		String view = null;
		String username = null;

		try {
			super.doGet(request, response);
			action = request.getParameter("action");

			if (request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
				id = Integer.parseInt(request.getParameter("id"));
			}

			List<Classi> listClassi = classiDao.getAllClassi();
			request.setAttribute("classi", listClassi);

			List<Materie> listMaterie = materieDao.getAllMaterie();
			request.setAttribute("materie", listMaterie);

			if (this.isAuthorized) {
				if (ACTION_DELETE.equals(action)) {

					User deletingUser = usersDao.getById(id);
					if (deletingUser != null) {
						int idRole = deletingUser.getIdRole();
						if (idRole == 2) { // Insegnante

							int idInsegnante = insegnantiDao.getInsegnanteByIdUtente(id).getId_insegnante();
							insegnanti_classi_materieDao.deleteInsegnantiClassiMaterie(idInsegnante);
							insegnantiDao.deleteInsegnante(idInsegnante);

						} else if (idRole == 4) { // Studente

							int idStudente = studentiDao.getStudenteByIdUtente(id).getId_studente();
							valutazioniDao.deleteValutazioneFromStudente(idStudente);
							studentiDao.deleteStudente(idStudente);

						}
						usersDao.deleteUser(id);
					}
					List<User> listUsers = usersDao.getAll(username);
					request.setAttribute("users", listUsers);
					view = "view/elencoUser/index.jsp";

				} else if (ACTION_INSERT.equals(action)) {
					user = new User();
					request.setAttribute("user", user);
					request.setAttribute("action", action);
					view = "view/elencoUser/edit.jsp";
				} else if (ACTION_EDIT.equals(action)) {
					user = usersDao.getById(id);

					List<Insegnanti_classi_materie> insegnantiClassiMaterie = new ArrayList<>();
					if (user.getIdRole() == 2) { // Insegnante
						int idInsegnante = insegnantiDao.getInsegnanteByIdUtente(id).getId_insegnante();
						insegnantiClassiMaterie = insegnanti_classi_materieDao
								.getInsegnantiClassiMaterieById(idInsegnante);
						request.setAttribute("insegnanti_classi_materie", insegnantiClassiMaterie);
					}
					Studenti studente = new Studenti();
					if (user.getIdRole() == 4) { // studente
						studente = studentiDao.getStudenteByIdUtente(id);
						request.setAttribute("studente", studente);
					}

					request.setAttribute("user", user);
					request.setAttribute("id_role", user.getIdRole());
					request.setAttribute("id", id);
					request.setAttribute("action", action);
					view = "view/elencoUser/edit.jsp";
				} else {
					username = request.getParameter("username");
					List<User> listUsers = usersDao.getAll(username);
					request.setAttribute("users", listUsers);
					request.setAttribute("username", username);
					view = "view/elencoUser/index.jsp";
				}
				RequestDispatcher dispatcher = request.getRequestDispatcher(view);
				dispatcher.forward(request, response);
			} else {
				response.sendRedirect("login");
			}
		} catch (Exception e) {
			processError(request, response);
			e.printStackTrace();
		}
		
		}else {
			response.sendRedirect("login");
		}
		}else {
			response.sendRedirect("login");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			User user = new User();
			super.doPost(request, response);

			if (this.isAuthorized) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
				Date birth_date = formatter.parse(request.getParameter("birth_date"));
				String username = request.getParameter("username");
				String first_name = request.getParameter("first_name");
				String last_name = request.getParameter("last_name");
				// String password = request.getParameter("password");
				String password = StringUtils.encrypt(request.getParameter("password"));
				String email = request.getParameter("email");
				String cellphone = request.getParameter("cellphone");
				int id_role = Integer.parseInt(request.getParameter("id_role"));
				Integer id = null;

				if (request.getParameter("id") != null && Integer.parseInt(request.getParameter("id")) > 0) {
					id = Integer.parseInt(request.getParameter("id"));
				}

				user.setUsername(username);
				user.setFirst_name(first_name);
				user.setLast_name(last_name);
				user.setPassword(password);
				user.setEmail(email);
				user.setCellphone(cellphone);
				user.setBirth_date(birth_date);
				user.setIdRole(id_role);
				user.setId(id);

				Insegnanti insegnante = new Insegnanti();
				Insegnanti_classi_materie insegnanti_classi_materie = new Insegnanti_classi_materie();
				Studenti studente = new Studenti();

				System.out.println("\n\niniziotest\n\n");

				

				if (id_role == 4) { // Studente

					studente.setId_classe(Integer.valueOf(request.getParameter("id_classe")));
					System.out.println("\n\n\n\nINSERITO ID_CLASSE\n\n\n\n");

				}

				// guarda se e' da fare update o insert
				if (id == null) { // insert utente
					usersDao.insertUser(user);
					if (id_role == 4) { // Studente

						studente.setId_utente(usersDao.getIdLastUser());
						studentiDao.insertStudente(studente);
					

					}

				} else { // update user
					
					usersDao.updateUser(user);
					if (id_role == 4) { // Studente

						if (!studentiDao.studenteExistsByIdUtente(id)) { // insert studente
							studente.setId_utente(id);
							studentiDao.insertStudente(studente);
							
							System.out.println("\n\n\n\nINSERITO studente\n\n\n\n");

						} else { // update studente
							
							System.out.println("\n\n\n\nupdatestudente\n\n\n\n");

							studente.setId_utente(studentiDao.getStudenteByIdUtente(id).getId_utente());
							studentiDao.updateStudente(studente);
						}


						// check if there are insegnanti
						int idInsegnante = insegnantiDao.getInsegnanteByIdUtente(id).getId_insegnante();
						insegnanti_classi_materieDao.deleteInsegnantiClassiMaterie(idInsegnante);
						insegnantiDao.deleteInsegnante(idInsegnante);

					}
					if (id_role == 2) { // insegnante

						// check if there are studenti
						int idStudente = studentiDao.getStudenteByIdUtente(id).getId_studente();
						valutazioniDao.deleteValutazioneFromStudente(idStudente);
						studentiDao.deleteStudente(idStudente);

					}
				}
				
				// Modifica del metodo doPost per gestire l'associazione
				// insegnante-classe-materia
				if (id_role == 2) { // Insegnante

					// gestisco tabella insegnante

					int idInsegnante = -1;

					if (!insegnantiDao.insegnanteExistsByIdUtente(id)) { // insert insegnante
						if (id == null) { // nuovo utente
							insegnante.setId_utente(usersDao.getIdLastUser());
						} else { // vecchio utente
							insegnante.setId_utente(id);
						}
						insegnantiDao.insertInsegnante(insegnante);
						idInsegnante = insegnantiDao.getIdLastInsegnante();
					} else { // update
						idInsegnante = insegnantiDao.getInsegnanteByIdUtente(id).getId_insegnante();
					}

					int counterClasseMateria = 0;
					String idmateriaString = "id_materia_";
					String idclasseString = "id_classe_";

					// Ottenere le coppie classe-materia attualmente associate all'insegnante dal
					// database
					List<Insegnanti_classi_materie> insegnantiClassiMaterieAttuali = insegnanti_classi_materieDao
							.getInsegnantiClassiMaterieById(idInsegnante);

					// gestisci classe e materia
					while (counterClasseMateria < 30) {

						if (request.getParameter(idclasseString + (counterClasseMateria + "")) != null) {

							System.out.println("\n\nSto facendo While con questo " + counterClasseMateria + "\n\n");

							int idClasseIns = Integer
									.parseInt(request.getParameter(idclasseString + (counterClasseMateria + "")));
							int idMateriaIns = Integer
									.parseInt(request.getParameter(idmateriaString + (counterClasseMateria + "")));

							// Controlla se questa coppia classe-materia è già presente nel database
							boolean presenteNelDatabase = false;
							for (Insegnanti_classi_materie icm : insegnantiClassiMaterieAttuali) {
								if (icm.getId_classe() == idClasseIns && icm.getId_materia() == idMateriaIns) {
									presenteNelDatabase = true;
									break;
								}
							}

							// Se non è presente nel database, inseriscilo
							if (!presenteNelDatabase) {

								System.out.println("\n\ninserito\n\n");
								insegnanti_classi_materie.setId_insegnante(idInsegnante);
								insegnanti_classi_materie.setId_classe(idClasseIns);
								insegnanti_classi_materie.setId_materia(idMateriaIns);

								insegnanti_classi_materieDao.insertInsegnantiClassiMaterie(insegnanti_classi_materie);
							}

						}

						counterClasseMateria++;
					}

					// Elimina le coppie classe-materia che non sono presenti nella pagina JSP ma
					// sono presenti nel database
					for (Insegnanti_classi_materie icm : insegnantiClassiMaterieAttuali) {
						boolean presenteNellaPaginaJSP = false;
						for (int i = 0; i < counterClasseMateria; i++) {
							if (request.getParameter(idclasseString + (i + "")) != null) {
								int idClasseIns = Integer.parseInt(request.getParameter(idclasseString + i));
								int idMateriaIns = Integer.parseInt(request.getParameter(idmateriaString + i));
								if (icm.getId_classe() == idClasseIns && icm.getId_materia() == idMateriaIns) {
									presenteNellaPaginaJSP = true;
									break;
								}
							}
						}
						if (!presenteNellaPaginaJSP) {
							// Elimina la coppia classe-materia non presente nel form
							insegnanti_classi_materieDao.deleteClasseMateriaFromInsegnante(icm);
						}
					}

				}
				
				response.sendRedirect("Users");
			} else {
				response.sendRedirect("login");
			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}
}
