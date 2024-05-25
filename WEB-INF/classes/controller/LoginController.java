package controller;

import dao.ClassiDao;
import dao.DAO;
import dao.InsegnantiDao;
import dao.Insegnanti_classi_materieDao;
import dao.MaterieDao;
import dao.StudentiDao;
import dao.UserDao;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Classi;
import model.Insegnanti;
import model.Insegnanti_classi_materie;
import model.Materie;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.JDOMException;
import utils.StringUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class LoginController extends HttpServlet  {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(LoginController.class);

	private UserDao usersDao = null;
	private StudentiDao studentiDao = null;
	private InsegnantiDao insegnantiDao = null;
	private ClassiDao classiDao = null;
	private MaterieDao materieDao = null;
	private Insegnanti_classi_materieDao insegnanti_classi_materieDao = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
		super();

	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			super.init(config);

			// Configure Log4j
			String log4jConfigFile = getServletContext().getRealPath("/") + config.getServletContext().getInitParameter("log4jConfig");
			System.setProperty("log4j.configurationFile", log4jConfigFile);

			usersDao = new UserDao(getServletContext().getRealPath("/") + config.getServletContext().getInitParameter("config"),0);
			studentiDao = new StudentiDao(getServletContext().getRealPath("/") + config.getServletContext().getInitParameter("config"),0);
			classiDao = new ClassiDao(getServletContext().getRealPath("/") + config.getServletContext().getInitParameter("config"),0);
			materieDao = new MaterieDao(getServletContext().getRealPath("/") + config.getServletContext().getInitParameter("config"),0);
			insegnantiDao = new InsegnantiDao(getServletContext().getRealPath("/") + config.getServletContext().getInitParameter("config"),0);
			insegnanti_classi_materieDao = new Insegnanti_classi_materieDao(getServletContext().getRealPath("/") + config.getServletContext().getInitParameter("config"),0);
			
		} catch (ClassNotFoundException | JDOMException | IOException | SQLException e) {
			logger.error("Error initializing LoginController", e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			RequestDispatcher dispatcher = request.getRequestDispatcher("view/login/login.jsp");
			//RequestDispatcher dispatcher = request.getRequestDispatcher("darkpan-main/signin.html");
			dispatcher.forward(request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			String password = StringUtils.encrypt(request.getParameter("password"));
			//String password = (request.getParameter("password"));
			
			String username = request.getParameter("username");
			
			User user = usersDao.getByUserAndPassword(username, password);
			
		
			
			if(user != null) {
				HttpSession session = request.getSession();

				session.setAttribute("session_user", user);
				
				AuthorizedController authorizedController = new AuthorizedController();
				
				authorizedController.doGet(request, response);
				
				
				
				session.setAttribute("id_ruolo", user.getIdRole());
				
				usersDao.updateUser4DateAccess(user);				
				
				//dirigenza e segreteria
				if (user.getIdRole() == 1 || user.getIdRole() == 3) {
					
		            response.sendRedirect("homeDirigenza");
				}
				
				//insegnante
				if (user.getIdRole() == 2) {
					 
					Insegnanti insegnante = insegnantiDao.getInsegnanteByIdUtente(user.getId());
					session.setAttribute("insegnante", insegnante);
					
				
					
					List<Insegnanti_classi_materie> listInsegnanti_classi_materie = (List<Insegnanti_classi_materie>) insegnanti_classi_materieDao.getInsegnantiClassiMaterieById(insegnante.getId_insegnante());
					
					List<Classi> listClassiProfessore = new ArrayList<>();
					
					for (Insegnanti_classi_materie listInsegnante_classe_materia : listInsegnanti_classi_materie) {
						
						listClassiProfessore.add(classiDao.getClassiById(listInsegnante_classe_materia.getId_classe()));	
					}
					
					session.setAttribute("classiProfessore", listClassiProfessore);
					
					
					
					response.sendRedirect("homeInsegnanti");
				}
				
				//studente
				if (user.getIdRole() == 4) {
					response.sendRedirect("homeStudenti");
				}
				
			}else {
				
				response.sendRedirect("login");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
