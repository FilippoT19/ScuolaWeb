package controller;

import java.io.IOException;
import java.nio.channels.NonReadableChannelException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import javax.swing.plaf.multi.MultiInternalFrameUI;

import org.apache.jasper.tagplugins.jstl.core.If;

import dao.*;

import model.*;

@WebServlet("/Valutazioni")
public class ValutazioniController extends AuthorizedController {
	private static final long serialVersionUID = 1L;

	private UserDao usersDao = null;
	private StudentiDao studentiDao = null;
	private InsegnantiDao insegnantiDao = null;
	private ClassiDao classiDao = null;
	private MaterieDao materieDao = null;
	private Insegnanti_classi_materieDao insegnanti_classi_materieDao = null;
	private ValutazioniDao valutazioniDao = null;

	public ValutazioniController() {
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
		
		if (this.isAuthorized) {
		HttpSession session = request.getSession();
		int idRoleUser = ((User)session.getAttribute("session_user")).getIdRole();
		
		if(idRoleUser !=4 ) {
		
		
		
		session.setAttribute("paginaAttuale", "valutazioni");
		String action = null;
		int id = 0;
		User user = null;
		User sessionUser = (User) session.getAttribute("session_user");
		String view = null;
		String username = null;

		try {
			super.doGet(request, response);

			if (request.getParameter("action") != null) {
				action = request.getParameter("action");
			}

			request.setAttribute("action", action);

			List<Classi> listClassi = classiDao.getAllClassi();
			request.setAttribute("classi", listClassi);

			List<Materie> listMaterie = materieDao.getAllMaterie();
			request.setAttribute("materie", listMaterie);

			if (this.isAuthorized) {

				// dirigenza e segreteria
				if (sessionUser.getIdRole() == 1 || sessionUser.getIdRole() == 3) {

					List<Insegnanti_classi_materie> icmAuthorized = (List<Insegnanti_classi_materie>) insegnanti_classi_materieDao
							.getAllInsegnantiClassiMaterie();
					request.setAttribute("icmAuthorized", icmAuthorized);

				}

				// insegnante
				if (sessionUser.getIdRole() == 2) {
					List<Insegnanti_classi_materie> icmAuthorized = (List<Insegnanti_classi_materie>) insegnanti_classi_materieDao
							.getInsegnantiClassiMaterieById(insegnantiDao.getInsegnanteByIdUtente(sessionUser.getId()).getId_insegnante());

					request.setAttribute("icmAuthorized", icmAuthorized);

				}

				if ("classeMateria".equals(action)) {

					String classeMateria = request.getParameter("classeMateria");
					request.setAttribute("classeMateria", classeMateria);

					int classeSelect = -1;
					int materiaSelect = -1;

					// Verifica che il parametro non sia nullo o vuoto
					if (classeMateria != null && !classeMateria.isEmpty() && !classeMateria.equals("-1")) {
						try {
							// Divide la stringa in due parti utilizzando il trattino come separatore
							String[] parts = classeMateria.split("-");

							// Converte le parti in interi
							classeSelect = Integer.parseInt(parts[0]);
							materiaSelect = Integer.parseInt(parts[1]);

							List<Studenti> listStudenti = studentiDao.getStudentiByIdClasse(classeSelect);

							List<User> listUsers = new ArrayList<>();

							for (Studenti studente : listStudenti) {
								int idUtente = studente.getId_utente();
								User utente = usersDao.getById(idUtente);
								listUsers.add(utente);
							}
							request.setAttribute("listUsersClasse", listUsers);
							request.setAttribute("listStudentiClasse", listStudenti);

							List<Valutazioni> listValutazioni = valutazioniDao
									.getValutazioniByClasseMateria(classeSelect, materiaSelect);
							request.setAttribute("listValutazioni", listValutazioni);

							// associo valutazioni a id_studenti
							// Mappa per associare una lista di valutazioni a ciascuno studente
							Map<Integer, List<Valutazioni>> valutazioniPerStudenteMap = new HashMap<>();

							// Itera attraverso le valutazioni
							for (Valutazioni valutazione : listValutazioni) {
								int idStudente = valutazione.getId_studente();

								// Se la mappa già contiene l'ID dello studente, aggiungi la valutazione alla
								// lista esistente
								if (valutazioniPerStudenteMap.containsKey(idStudente)) {
									valutazioniPerStudenteMap.get(idStudente).add(valutazione);
								} else {
									// Altrimenti, crea una nuova lista e aggiungi la valutazione
									List<Valutazioni> valutazioniStudente = new ArrayList<>();
									valutazioniStudente.add(valutazione);
									valutazioniPerStudenteMap.put(idStudente, valutazioniStudente);
								}
							}

							request.setAttribute("valutazioniPerStudenteMap", valutazioniPerStudenteMap);

							// conto numero voti
							List<Studenti> studentiClasse = studentiDao.getStudentiByIdClasse(classeSelect);
							int maxNumeroVoti = 0;

							for (Studenti studenteSelect : studentiClasse) {

								int idStudente = studenteSelect.getId_studente();


								int numeroVoti = valutazioniDao.getValutazioniByStudenteMateria(idStudente,materiaSelect).size();

								if (numeroVoti > maxNumeroVoti ) {
									maxNumeroVoti = numeroVoti;
								}

							}
							view = "view/valutazioni/indexValutazioni.jsp";
							request.setAttribute("maxNumeroVoti", maxNumeroVoti);

						} catch (NumberFormatException e) {
							// Gestione dell'errore nel caso in cui la conversione fallisca
							e.printStackTrace();
						}
					} else {
						// Gestione del caso in cui il parametro non sia valido
						System.out.println("Parametro classeMateria non valido");
						view = "view/valutazioni/indexValutazioni.jsp";
					}

					

				} else if ("insertValutazione".equals(action)) {
					
			        // Recupera il parametro dal request
			        String classeMateria = request.getParameter("classeMateria");
			        String studenteSelect = request.getParameter("studenteSelect");

			        // Verifica che il parametro non sia nullo o vuoto
			        if (classeMateria != null && !classeMateria.isEmpty() && !classeMateria.equals("-1")) {
			            try {
			                // Divide la stringa in due parti utilizzando il trattino come separatore
			                String[] parts = classeMateria.split("-");
			                
			                // Converte le parti in interi
			                int classeSelect = Integer.parseInt(parts[0]);
			                int materiaSelect = Integer.parseInt(parts[1]);

			                
			                List<Studenti> listStudenti = studentiDao.getStudentiByIdClasse(classeSelect);
			                
			                List<User> listUsers = new ArrayList<>();

							for (Studenti studente : listStudenti) {
								int idUtente = studente.getId_utente();
								User utente = usersDao.getById(idUtente);
								listUsers.add(utente);
							}
							
							Valutazioni valutazione = new Valutazioni();
							
							//request.setAttribute("valutazioneCorrente", valutazione);
							request.setAttribute("classeSelect", classeSelect);
							request.setAttribute("materiaSelect", materiaSelect);
							request.setAttribute("listUsersClasse", listUsers);
							request.setAttribute("listStudentiClasse", listStudenti);
							request.setAttribute("studenteSelect", studenteSelect);
							
							view = "view/valutazioni/editValutazioni.jsp";
			               

			            } catch (NumberFormatException e) {
			                // Gestione dell'errore nel caso in cui la conversione fallisca
			                e.printStackTrace();
			            }
			        } else {
			            // Gestione del caso in cui il parametro non sia valido
			            System.out.println("Parametro classeMateria non valido");
			        }
			    

				} else if ("updateValutazione".equals(action)) {
					
			        // Recupera il parametro dal request
			        String classeMateria = request.getParameter("classeMateria");
			        String studenteSelect = request.getParameter("studenteSelect");

			        // Verifica che il parametro non sia nullo o vuoto
			        if (classeMateria != null && !classeMateria.isEmpty() && !classeMateria.equals("-1")) {
			            try {
			                // Divide la stringa in due parti utilizzando il trattino come separatore
			                String[] parts = classeMateria.split("-");
			                
			                // Converte le parti in interi
			                int classeSelect = Integer.parseInt(parts[0]);
			                int materiaSelect = Integer.parseInt(parts[1]);

			                
			                List<Studenti> listStudenti = studentiDao.getStudentiByIdClasse(classeSelect);
			                
			                List<User> listUsers = new ArrayList<>();

							for (Studenti studente : listStudenti) {
								int idUtente = studente.getId_utente();
								User utente = usersDao.getById(idUtente);
								listUsers.add(utente);
							}
							
							
							Valutazioni valutazione = valutazioniDao.getById(Integer.valueOf((String) request.getParameter("id_valutazione")));
							
							request.setAttribute("valutazioneCorrente", valutazione);
							request.setAttribute("classeSelect", classeSelect);
							request.setAttribute("materiaSelect", materiaSelect);
							request.setAttribute("listUsersClasse", listUsers);
							request.setAttribute("listStudentiClasse", listStudenti);
							request.setAttribute("studenteSelect", studenteSelect);
							
							view = "view/valutazioni/editValutazioni.jsp";
			               

			            } catch (NumberFormatException e) {
			                // Gestione dell'errore nel caso in cui la conversione fallisca
			                e.printStackTrace();
			            }
			        } else {
			            // Gestione del caso in cui il parametro non sia valido
			            System.out.println("Parametro classeMateria non valido");
			        }

				} else if ("deleteValutazione".equals(action)) {
					
					Valutazioni valutazione = valutazioniDao.getById(Integer.valueOf((String)request.getParameter("id_valutazione")));
					String classeMateria = request.getParameter("classeMateria");
					
					view = "view/valutazioni/indexValutazioni?action=classeMateria&classeMateria=" + classeMateria + ".jsp";

				} else {
					view = "view/valutazioni/indexValutazioni.jsp";
				}

				RequestDispatcher dispatcher = request.getRequestDispatcher(view);
				dispatcher.forward(request, response);

			} else {
				response.sendRedirect("login");
			}
		} catch (Exception e) {
			//processError(request, response);
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
        	Valutazioni valutazione = new Valutazioni();
            super.doPost(request, response);

            if (this.isAuthorized) {
            	
            	
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Date data_valutazione = formatter.parse(request.getParameter("data_valutazione"));
                String tipo_valutazione = request.getParameter("tipo_valutazione");
                Double voto =  Double.valueOf(request.getParameter("voto")); 
                
                int id_studente = studentiDao.getStudenteByIdUtente(Integer.parseInt(request.getParameter("studenteSelect"))).getId_studente();
                System.out.println(id_studente);
                int id_classe = Integer.parseInt(request.getParameter("classeSelect"));
                int id_materia  = Integer.parseInt(request.getParameter("materiaSelect"));
                
                int id_valutazione = -1;
                
                if (request.getParameter("valutazioneSelect") != null && Integer.parseInt(request.getParameter("valutazioneSelect")) > 0) {
                	id_valutazione = Integer.parseInt(request.getParameter("valutazioneSelect"));
                	valutazione.setId_valutazione(id_valutazione);
                }
                
                valutazione.setVoto(voto);
                valutazione.setTipoValutazione(tipo_valutazione);
                valutazione.setDataValutazione(data_valutazione);
                valutazione.setId_classe(id_classe);
                valutazione.setId_materia(id_materia);
                valutazione.setId_studente(id_studente);
                
                
                //guarda se e' da fare update o insert
                if (id_valutazione == -1) { //insert
                      valutazioniDao.insertValutazione(valutazione);
                } else { //update
                	valutazioniDao.updateValutazione(valutazione);
                }

                response.sendRedirect("Valutazioni?action=classeMateria&classeMateria=" + id_classe + "-" + id_materia);
            } else {
                response.sendRedirect("login");
            }

        } catch (Exception e) {
        	//processError(request, response);
            e.printStackTrace();
        }
    
		
	}
	
		
}
