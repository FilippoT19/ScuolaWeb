package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.protocol.ServerSessionStateController.SessionStateChange;

import dao.ClassiDao;
import dao.InsegnantiDao;
import dao.Insegnanti_classi_materieDao;
import dao.MaterieDao;
import dao.StudentiDao;
import dao.UserDao;
import dao.ValutazioniDao;
import model.Materie;
import model.Studenti;
import model.User;
import model.Valutazioni;

/**
 * Servlet implementation class HomeController
 */
@WebServlet("/homeStudenti")
public class HomeStudentiController extends AuthorizedController {
	private static final long serialVersionUID = 1L;
	
	private UserDao usersDao = null;
	private StudentiDao studentiDao = null;
	private InsegnantiDao insegnantiDao = null;
	private ClassiDao classiDao = null;
	private MaterieDao materieDao = null;
	private Insegnanti_classi_materieDao insegnanti_classi_materieDao = null;
	private ValutazioniDao valutazioniDao = null;
       
    /**
     * @see AuthorizedController#AuthorizedController()
     */
    public HomeStudentiController() {
        super();

    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
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
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			
		super.doGet(request, response);
		
	
		
		
		if(this.isAuthorized ) {
			HttpSession session = request.getSession();
			int idRoleUser = ((User)session.getAttribute("session_user")).getIdRole();
			if(idRoleUser ==4 ) {
			
			session.setAttribute("paginaAttuale", "home");
			User sessionUser = (User) session.getAttribute("session_user");
			int id_user = sessionUser.getId();
			Studenti sessionStudente = studentiDao.getStudenteByIdUtente(id_user);
			int id_studente = sessionStudente.getId_studente();
			String classeStudente = classiDao.getClassiById(sessionStudente.getId_classe()).getNome_classe();
			request.setAttribute("classeStudente", classeStudente);
			
			List <Valutazioni> valutazioniStudente = valutazioniDao.getValutazioniByIdStudente(id_studente);
			
			
			
			List <Valutazioni> valutazioniStudenteMateria = new ArrayList<Valutazioni>();
			List <Materie> listMaterieStudente =new ArrayList<Materie>();
			List <Integer> listIdMaterie = insegnanti_classi_materieDao.getMaterieByClasseId(sessionStudente.getId_classe());
			String nomeMateria = "";
			
			for (Integer integer : listIdMaterie) {
				listMaterieStudente.add(materieDao.getMateriaById(integer));
			}
			
			
			if (request.getParameter("materiaValutazioni") != null) {
				int idMateriaValutazioni = Integer.valueOf((String)request.getParameter("materiaValutazioni"));
				
				nomeMateria = materieDao.getMateriaById(idMateriaValutazioni).getNome_materia();
				 
				valutazioniStudenteMateria = valutazioniDao.getValutazioniByStudenteMateria(id_studente, idMateriaValutazioni);
				
			}
			
			System.out.println("\n\n\n\n\n\n" + valutazioniStudenteMateria + "\n\n\na"+ request.getAttribute("materiaValutazioni") + "\n\n\n\n\n");
			request.setAttribute("valutazioniStudenteMateria", valutazioniStudenteMateria);
			request.setAttribute("listMaterieStudente", listMaterieStudente);
			request.setAttribute("valutazioniStudente", valutazioniStudente);
			request.setAttribute("nomeMateria", nomeMateria);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("view/homeStudenti/homeStudenti.jsp");
	        dispatcher.forward(request, response);
	        
		}else {
			response.sendRedirect("login");
		}
		}else {
			response.sendRedirect("login");
		}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
		
		if(this.isAuthorized) {
			
		}else {
			response.sendRedirect("login");
		}
	}

}
