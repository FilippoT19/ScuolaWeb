<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="org.eclipse.jdt.internal.compiler.ast.IfStatement"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.If"%>
<%@page import="javax.lang.model.type.NullType"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="model.*"%>
<%@ page import="utils.*"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.DateFormat"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
User user = (User) request.getAttribute("user");
String id = "0";
if (request.getAttribute("id") != null) {
	id = Integer.toString((Integer) request.getAttribute("id"));
}

Studenti studente = new Studenti();
List<Insegnanti_classi_materie> insegnanti_classi_materie = new ArrayList<>();

String action = (String) request.getAttribute("action");

List<Insegnanti_classi_materie> elencoCoppieClasseMateria = null;
if (request.getAttribute("icmAuthorized") != null) {
	elencoCoppieClasseMateria = (List<Insegnanti_classi_materie>) request.getAttribute("icmAuthorized");

}

List<User> listUsersClasse = new ArrayList<User>();
if (request.getAttribute("listUsersClasse") != null) {
	listUsersClasse = (List<User>) request.getAttribute("listUsersClasse");
}

List<Studenti> listStudentiClasse = new ArrayList<Studenti>();
if (request.getAttribute("listStudentiClasse") != null) {
	listStudentiClasse = (List<Studenti>) request.getAttribute("listStudentiClasse");
}

int id_valutazioneCorrente = -1;
Valutazioni valutazione = new Valutazioni();
if (request.getAttribute("valutazioneCorrente") != null) {
	valutazione = (Valutazioni) request.getAttribute("valutazioneCorrente");
	id_valutazioneCorrente = valutazione.getId_valutazione();
}
Integer studenteSelect = null;
String studenteNomeCognome = ": ";
if (request.getAttribute("studenteSelect") != null) {
	studenteSelect = Integer.valueOf((String) request.getAttribute("studenteSelect"));

	for (User user1 : (List<User>) request.getAttribute("listUsersClasse")) {
		if (user1.getId().equals(studenteSelect)) {
	studenteNomeCognome += user1.getFirst_name() + " " + user1.getLast_name();
		}
	}

}

List<Classi> classi = (ArrayList<Classi>) request.getAttribute("classi");
List<Materie> materie = (ArrayList<Materie>) request.getAttribute("materie");

int classeSelect = (int) request.getAttribute("classeSelect");
int materiaSelect = (int) request.getAttribute("materiaSelect");

String titolo = "";

if (action.equals("insertValutazione")) {

	titolo = "Nuovo";
} else {
	titolo = "Modifica";
}
%>

<!DOCTYPE html>
<html>
<head>
<title>Edit User</title>
<%@include file="/resources/externalJSP/links.jsp"%>
<style>
.wrapper {
	display: grid;
	grid-template-columns: repeat(3, 1fr);
	gap: 10px;
}
.icon_date{
  position: absolute;
    right: 5px;
    top: 50%;
    transform: translateY(-50%);
    pointer-events: none; /* So the icon does not block clicks to the input */
    font-size: 16px;

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
					<h4 class="mb-4"><%=titolo%>
						Voto<%=studenteNomeCognome%></h4>
					<div class="d-flex justify-content-between align-items-center ">


						<div class="container p-30">

							<form class="form-floating mb-3" action="Valutazioni"
								method="post">

								<input type="hidden" name="id" value="<%=id%>"> <input
									type="hidden" name="action"
									value="<%=request.getAttribute("action")%>"> <input
									type="hidden" name="classeSelect" value="<%=classeSelect%>">
								<input type="hidden" name="materiaSelect"
									value="<%=materiaSelect%>"> <input type="hidden"
									name="valutazioneSelect" value="<%=id_valutazioneCorrente%>">
								<input type="hidden" name="studenteSelect"
									value="<%=studenteSelect%>"></input>




								<!-- select materia-classe -->

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





								<div class="mb-3">
									<label for="data_valutazione" class="form-label fw-bold">Data
										della valutazione:</label>
									<div class="col-2">
										<div class="input-group date" id="data_valutazione">
											<input type="date" class="form-control" id="datepicker"
												name="data_valutazione"
												value="<%=valutazione.getDataValutazione() != null ? valutazione.getDataValutazione() : StringUtils.STRING_EMPTY%>"
												required="required" /> <span class="input-group-append">
												<span id="icon_date" class="icon_date input-group-text d-block bg-secondary"
												> <i  class="fas fa-calendar-alt"></i>
											</span>
											</span>
										</div>
									</div>
								</div>

								<!-- select orale /  scritto -->

								<div class="mb-3">
									<label class="form-label fw-bold">Tipo Valutazione:</label>
									<div>
										<div class="form-check">
											<input class="form-check-label" type="radio"
												name="tipo_valutazione" id="tipo_valutazione_orale"
												value="orale"
												<%="orale".equals(valutazione.getTipoValutazione()) ? "checked" : ""%>
												required> <label class="form-check-label"
												for="tipo_valutazione_orale"> Orale </label>
										</div>
										<div class="form-check">
											<input class="form-check-label" type="radio"
												name="tipo_valutazione" id="tipo_valutazione_scritto"
												value="scritto"
												<%="scritto".equals(valutazione.getTipoValutazione()) ? "checked" : ""%>
												required> <label class="form-check-label"
												for="tipo_valutazione_scritto"> Scritto </label>
										</div>
									</div>
								</div>

								<!-- select voto -->

								<div class="mb-3">
									<div class="col-2">
										<label for="voto" class="form-label fw-bold">Voto:</label> <input
											type="number" class="form-control" name="voto"
											value="<%=(valutazione.getVoto() != null ? valutazione.getVoto() : StringUtils.STRING_EMPTY)%>"
											required="required" min="1" max="10" step="0.01" />
									</div>
								</div>



								<button type="submit" class="btn btn-primary">salva</button>

							</form>

						</div>
					</div>

				</div>



			</div>
			<%@include file="/resources/externalJSP/footer.jsp"%>
			<!-- Content End -->


			<!-- Back to Top -->
			<a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i
				class="bi bi-arrow-up"></i></a>
		</div>
	</div>


	<%@include file="/resources/externalJSP/scripts.jsp"%>
	<script>
	
	   $(document).ready(function() {
		      // Inizializza il datepicker
		      $('#datepicker').datepicker({
		         format: 'dd/mm/yyyy',
		         todayHighlight: true,
		         autoclose: true
		      });

		      // Apri il datepicker quando si clicca sull'icona
		      $('#icon_date').click(function() {
		         $('#datepicker').datepicker('show');
		      });
		   });

	
	</script>

</body>

</html>

