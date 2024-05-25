package controller;
//corso


import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AuthorizedController
 */
@WebServlet("/AuthorizedController")
public class AuthorizedController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(AuthorizedController.class);

    protected static final String ACTION_DELETE = "DELETE";
    protected static final String ACTION_EDIT = "EDIT";
    protected static final String ACTION_INSERT = "INSERT";
    public static int id_utente_sessione = 0;

    protected boolean isAuthorized = false;
    protected User session_user = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthorizedController() {
        super();

    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config,HttpServletRequest request, HttpServletResponse response ) throws ServletException {
		super.init(config);
		
		HttpSession session = request.getSession();

		if(session.getAttribute("session_user") != null ) {

			
			session_user = (User) session.getAttribute("session_user");
			this.id_utente_sessione = session_user.getId();
		}else {
			System.out.println("\n\n\n\n\nERRORE AUTHORIZED SESSION USER\n\n\n\n\n");
		}
	
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		if(session.getAttribute("session_user") != null ) {
			isAuthorized = true;
			
			session_user = (User) session.getAttribute("session_user");
			this.id_utente_sessione = session_user.getId();
		}else {
			isAuthorized = false;
			
			session_user = null;
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		if(session.getAttribute("session_user") != null ) {
			isAuthorized = true;
			
			session_user = (User) session.getAttribute("session_user");
			this.id_utente_sessione = session_user.getId();
		}else {
			isAuthorized = false;
			
			session_user = null;
		}
		
	}

    /**
     * Error page
     *
     * @param request
     * @param response
     * @throws IOException
     */

    protected void processError(HttpServletRequest request, HttpServletResponse response) throws IOException {

//        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
//        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
//        if (servletName == null) {
//            servletName = "Unknown";
//        }
//        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
//        if (requestUri == null) {
//            requestUri = "Unknown";
//        }
//
//        // Log the error
//        logger.error("Error occurred in servlet: " + servletName);
//        logger.error("Status code: " + statusCode);
//        logger.error("Requested URI: " + requestUri, throwable);
//
//        // Set response content type
//        response.setContentType("text/html");
//
//        PrintWriter out = response.getWriter();
//        out.write("<html><head><title>Exception/Error Details</title></head><body>");
//        if (statusCode != 500) {
//            out.write("<h3>Error Details</h3>");
//            out.write("<strong>Status Code</strong>:" + statusCode + "<br>");
//            out.write("<strong>Requested URI</strong>:" + requestUri);
//	      }else{
//	    	  out.write("<h3>Exception Details</h3>");
//	    	  out.write("<ul><li>Servlet Name:"+servletName+"</li>");
//	    	  out.write("<li>Exception Name:"+throwable.getClass().getName()+"</li>");
//	    	  out.write("<li>Requested URI:"+requestUri+"</li>");
//	    	  out.write("<li>Exception Message:"+throwable.getMessage()+"</li>");
//	    	  out.write("</ul>");
//	      }
//	      
//	      out.write("<br><br>");
//	      out.write("<a href=\"index.jsp\">Home Page</a>");	//da modificare con la pagina a cui si intende reindirizzare in caso di errore
//	      out.write("</body></html>");
	}

}
