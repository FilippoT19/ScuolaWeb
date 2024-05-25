package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;

/**
 * Servlet implementation class HomeController
 */
@WebServlet("/homeInsegnanti")
public class HomeInsegnantiController extends AuthorizedController {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see AuthorizedController#AuthorizedController()
     */
    public HomeInsegnantiController() {
        super();

    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		super.doGet(request, response);

		if(this.isAuthorized) {
			
			HttpSession session = request.getSession();
			
			int idRoleUser = ((User)session.getAttribute("session_user")).getIdRole();
			if(idRoleUser ==2) {
			
			session.setAttribute("paginaAttuale", "home");
			RequestDispatcher dispatcher = request.getRequestDispatcher("view/homeInsegnanti/homeInsegnanti.jsp");
	        dispatcher.forward(request, response);
		}else {
			response.sendRedirect("login");
		}
		}else {
			response.sendRedirect("login");
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
