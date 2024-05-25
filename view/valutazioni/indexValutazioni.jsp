<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page import="model.*"%>
<%@ page import="dao.UserDao"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.DateFormat"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.List"%>
<%@page import="java.text.ParseException"%>
<%@page import="java.util.Date"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String transformedDate = "";
String username = "";
List<User> users = (ArrayList<User>) request.getAttribute("users");
if (request.getAttribute("username") != null) {
	username = (String) request.getAttribute("username");
}

List<Insegnanti_classi_materie> elencoCoppieClasseMateria = null;
if (request.getAttribute("icmAuthorized") != null) {
	elencoCoppieClasseMateria = (List<Insegnanti_classi_materie>) request.getAttribute("icmAuthorized");
}
Map<Integer, List<Valutazioni>> valutazioniPerStudenteMap = new HashMap<>();

if (request.getAttribute("valutazioniPerStudenteMap") != null) {
	valutazioniPerStudenteMap = (Map<Integer, List<Valutazioni>>) request.getAttribute("valutazioniPerStudenteMap");
}
int maxNumeroVoti = 0;
if (request.getAttribute("maxNumeroVoti") != null) {
	maxNumeroVoti = (int) request.getAttribute("maxNumeroVoti");
}

List<User> listUsersClasse = new ArrayList<User>();
if (request.getAttribute("listUsersClasse") != null) {
	listUsersClasse = (List<User>) request.getAttribute("listUsersClasse");
}

List<Studenti> listStudentiClasse = new ArrayList<Studenti>();
if (request.getAttribute("listStudentiClasse") != null) {
	listStudentiClasse = (List<Studenti>) request.getAttribute("listStudentiClasse");
}

List<Classi> classi = (ArrayList<Classi>) request.getAttribute("classi");
List<Materie> materie = (ArrayList<Materie>) request.getAttribute("materie");

Insegnanti_classi_materie coppiaAttuale = new Insegnanti_classi_materie();
String classeAttuale = "";
String materiaAttuale = "";

String classeMateria = "";
if (request.getAttribute("classeMateria") != null) {
	classeMateria = (String) request.getAttribute("classeMateria");

	String[] parts = classeMateria.split("-");

	// Converte le parti in interi
	int classeSelect = Integer.parseInt(parts[0]);
	int materiaSelect = Integer.parseInt(parts[1]);

	for (Classi classe : classi) {
		if (classe.getId_classe() == classeSelect) {
	classeAttuale = classe.getNome_classe();
		}
	}

	for (Materie materia : materie) {
		if (materia.getId_materia() == materiaSelect) {
	materiaAttuale = materia.getNome_materia();
		}
	}
}
%>

<!DOCTYPE html>
<html>
<head>
<title>Valutazioni</title>
<%@include file="/resources/externalJSP/links.jsp"%>

<style>
.dropdown {
	position: relative;
	display: inline-block;
}

.dropbtn {
	border: none;
	cursor: pointer;
	padding: 5px;
	font-size: 14px;
}

.dropdown-content {
	display: none;
	position: absolute;
	min-width: 160px;
	box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
	z-index: 1;
}

.dropdown-content a {
	background-color: #1B1B1B;
	padding: 12px 16px;
	text-decoration: none;
	display: block;
}

.dropdown-content a:hover {
	background-color: #dddddd;
}

.dropdown:hover .dropdown-content {
	display: block;
}

.dropdown:hover .dropbtn {
	background-color: #3e8e41;
	color: white;
}

/* table */
thead th.sticky-col {
	background-color: #343a40;
}

tbody td.sticky-col {
	background-color: ##f8f9fa;
}
/* Centrare l'elemento <i> */
.center-cell {
	display: flex;
	justify-content: center; /* Centra orizzontalmente */
	align-items: center; /* Centra verticalmente */
	height: 100%;
	/* Assicurati che la cella occupi l'intera altezza disponibile */
}

/* titolo + form */
.d-flex {
	display: flex;
}

.justify-content-between {
	justify-content: space-between;
}

.align-items-center {
	align-items: center;
}

.form-inline .form-select {
	width: auto; /* Opzionale: per regolare la larghezza del select */
}
</style>

</head>
<body>


	<div class="container-fluid position-relative d-flex p-0">
		<%@include file="/resources/externalJSP/sidebar.jsp"%>

		<!-- Content Start -->
		<div class="content">

			<%@include file="/resources/externalJSP/navbar.jsp"%>

			<div class="container-fluid pt-4 px-4">

				<div class="bg-secondary rounded h-100 p-4">
					<div class="d-flex justify-content-between align-items-center ">
						<h4 class="mb-4">
							Valutazioni<%
						if (classeMateria != "") {
						%>: 
							<%=classeAttuale%>,
							<%=materiaAttuale%>

							<%
							}
							%>

						</h4>

						<form class="form-inline my-2 my-lg-0 bg-dark"
							action="Valutazioni" method="GET" id="formCoppia">
							<input type="hidden" name="action" value="classeMateria"></input>

							<%
							// Creazione delle mappe per una ricerca veloce
							Map<Integer, String> mappaMaterie = new HashMap<>();
							Map<Integer, String> mappaClassi = new HashMap<>();

							// Popolazione della mappa delle materie
							for (Materie materia : materie) {
								mappaMaterie.put(materia.getId_materia(), materia.getNome_materia());
							}

							// Popolazione della mappa delle classi
							for (Classi classe : classi) {
								mappaClassi.put(classe.getId_classe(), classe.getNome_classe());
							}
							%>

							<select class="form-select " name="classeMateria"
								id="selectCoppia">
								<option class="" value="-1">Seleziona una coppia
									Classe-Materia</option>
								<%
								for (Insegnanti_classi_materie coppia : elencoCoppieClasseMateria) {
									String nomeClasse = mappaClassi.get(coppia.getId_classe());
									String nomeMateria = mappaMaterie.get(coppia.getId_materia());
								%>
								<option class="bg-dark"
									value="<%=coppia.getId_classe() + "-" + coppia.getId_materia()%>">

									<%=nomeClasse%> -
									<%=nomeMateria%>

									<%
									coppiaAttuale = coppia;
									%>
								</option>
								<%
								}
								%>
							</select>
						</form>
					</div>

					<%
					if (classeMateria != "") {
					%>

					<div class="pl-5 pr-5 pt-2 table-responsive text-center">


						<table class="table table-hover">
							<thead>
								<tr>
									<th scope="col" style="width: 50px;"></th>
									<th scope="col" style="width: 200px;">Alunno</th>
									<th scope="col" style="width: 70px;">Media</th>
									<th scope="col" style="width: 75px;">N Voti</th>

									<%
									for (int i = 1; i <= maxNumeroVoti; i++) {
									%>
									<th scope="col"><%=i%></th>
									<%
									}
									%>
								</tr>
							</thead>

							<tbody>
								<%
								for (User userStudente : listUsersClasse) {
									Studenti studente = null;
									for (Studenti s : listStudentiClasse) {
										if (s.getId_utente() == userStudente.getId()) {
									studente = s;
									break;
										}
									}

									if (studente == null) {
										continue;
									}

									int idStudente = studente.getId_studente();
									List<Valutazioni> valutazioniStudente = valutazioniPerStudenteMap.getOrDefault(idStudente,
									new ArrayList<Valutazioni>());

									// Calcola media e numero voti
									double sommaVoti = 0;
									int numeroVoti = valutazioniStudente.size();
									for (Valutazioni valutazione : valutazioniStudente) {
										sommaVoti += valutazione.getVoto();
									}
									double mediaVoti = numeroVoti > 0 ? sommaVoti / numeroVoti : 0;
								%>
								<tr>
									<td><a
										href="?action=insertValutazione&classeMateria=<%=classeMateria%>&studenteSelect=<%=userStudente.getId()%>">
											<i style="font-size: 30px;"
											class="center-cell far fa-plus-square fa-lg"></i>
									</a></td>
									<td><%=userStudente.getFirst_name()%> <%=userStudente.getLast_name()%></td>
									<td><%=String.format("%.2f", mediaVoti)%></td>
									<td><%=numeroVoti%></td>


									<%
									// Ordina le valutazioni per data
									Collections.sort(valutazioniStudente, new Comparator<Valutazioni>() {
										@Override
										public int compare(Valutazioni v1, Valutazioni v2) {
											return v1.getDataValutazione().compareTo(v2.getDataValutazione());
										}
									});

									for (Valutazioni valutazione : valutazioniStudente) {
										// Formattazione della data
										DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
										String dataValutazione = dateFormat.format(valutazione.getDataValutazione());
										int idValutazione = valutazione.getId_valutazione(); // Assumi che ci sia un metodo getId_valutazione() in Valutazioni

										//cambio format data
										SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
										SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM");

										try {
											Date date = inputFormat.parse(dataValutazione);
											transformedDate = outputFormat.format(date);

										} catch (ParseException e) {
											e.printStackTrace();
										}
									%>
									<td>
										<div class="dropdown">

											<%
											if ((Double) valutazione.getVoto() >= 6) {
											%>
											<button class=" btn btn-outline-success m-2">


												<%
												} else {
												%>
												<button class=" btn btn-outline-primary m-2">
													<%
													}
													%>

													<strong style="font-size: 15px"><%=valutazione.getVoto()%></strong><br><%=transformedDate%></button>
												<div class="dropdown-content">
													<a
														href="?action=updateValutazione&id_valutazione=<%=idValutazione%>&classeMateria=<%=classeMateria%>&studenteSelect=<%=userStudente.getId()%>">Modifica</a>
													<a
														href="?action=deleteValutazione&id_valutazione=<%=idValutazione%>&classeMateria=<%=classeMateria%>">Elimina</a>
												</div>
										</div>
									</td>
									<%
									}
									%>
								</tr>
								<%
								}
								%>
							</tbody>
						</table>
					</div>

					<%
					} 
					%>

				
				</div>
			</div>



			<%@include file="/resources/externalJSP/footer.jsp"%>

		</div>
		<!-- Content End -->


		<!-- Back to Top -->
		<a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i
			class="bi bi-arrow-up"></i></a>
	</div>

	<%@include file="/resources/externalJSP/scripts.jsp"%>


	<script>
		document.getElementById("selectCoppia").addEventListener("change",
				function() {
					if (this.value !== "-1") {
						document.getElementById("formCoppia").submit();
					}
				});
	</script>
</body>
</html>
