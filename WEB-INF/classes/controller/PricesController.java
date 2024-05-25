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
@WebServlet("/prices")
public class PricesController extends AuthorizedController {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see AuthorizedController#AuthorizedController()
     */
    public PricesController() {
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

		HttpSession session = request.getSession();
		
		super.doGet(request, response);
		


		
		if(this.isAuthorized ) {
			session.setAttribute("paginaAttuale", "prices");
			RequestDispatcher dispatcher = request.getRequestDispatcher("view/prices/prices.jsp");
	        dispatcher.forward(request, response);
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
