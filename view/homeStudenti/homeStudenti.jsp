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
List<Valutazioni> valutazioniStudente = new ArrayList<Valutazioni>();
double media = 0;
if (request.getAttribute("valutazioniStudente") != null) {
	valutazioniStudente = (List<Valutazioni>) request.getAttribute("valutazioniStudente");
	//calcolo media
	// Calcola la somma dei voti
	double somma = 0;
	for (Valutazioni valutazione : valutazioniStudente) {
		somma += valutazione.getVoto();
	}

	// Calcola la media
	media = somma / valutazioniStudente.size();
	media = Math.round(media * 100.0) / 100.0;
}
List<Valutazioni> valutazioniStudenteMateria = new ArrayList<Valutazioni>();
if (request.getAttribute("valutazioniStudenteMateria") != null) {
	valutazioniStudenteMateria = (List<Valutazioni>) request.getAttribute("valutazioniStudenteMateria");
}
List<Materie> listMaterieStudente = new ArrayList<Materie>();
if (request.getAttribute("listMaterieStudente") != null) {
	listMaterieStudente = (List<Materie>) request.getAttribute("listMaterieStudente");
}
String classeStudente = "";
if (request.getAttribute("classeStudente") != null) {
	classeStudente = (String) request.getAttribute("classeStudente");
}

String nomeMateria1 = "";
if (request.getAttribute("nomeMateria") != null) {
	nomeMateria1 = (String) request.getAttribute("nomeMateria");
}
%>

<!DOCTYPE html>
<html lang="en">

<head>

<style>
.wrapper {
	display: grid;
	grid-template-columns: repeat(3, 1fr);
	gap: 10px;
}
</style>

<meta charset="utf-8">
<title>Homepage</title>


<%@include file="/resources/externalJSP/links.jsp"%>


</head>


<body>
	<div class="container-fluid position-relative d-flex p-0">



		<%@include file="/resources/externalJSP/sidebar.jsp"%>

		<!-- Content Start -->
		<div class="content">

			<%@include file="/resources/externalJSP/navbar.jsp"%>



			<div class="container-fluid pt-4 px-4">
				<div class="row g-4">
					<div class="col-sm-6 col-xl-3">
						<div
							class="bg-secondary rounded d-flex align-items-center justify-content-between p-4">
							<i class="fas fa-school fa-3x text-primary"></i>
							<div class="ms-3">
								<p class="mb-2">Classe</p>
								<h6 class="mb-0"><%=classeStudente%></h6>
							</div>
						</div>
					</div>
					<div class="col-sm-6 col-xl-3">
						<div
							class="bg-secondary rounded d-flex align-items-center justify-content-between p-4">
							<i class="fas fa-book fa-3x text-primary"></i>
							<div class="ms-3">
								<p class="mb-2">Numero Materie</p>
								<h6 class="mb-0"><%=listMaterieStudente.size()%></h6>
							</div>
						</div>
					</div>
					<div class="col-sm-6 col-xl-3">
						<div
							class="bg-secondary rounded d-flex align-items-center justify-content-between p-4">
							<i class="fas fa-graduation-cap fa-3x text-primary"></i>
							<div class="ms-3">
								<p class="mb-2">Media Totale</p>
								<h6 class="mb-0"><%=media%></h6>
							</div>
						</div>
					</div>
					<div class="col-sm-6 col-xl-3">
						<div
							class="bg-secondary rounded d-flex align-items-center justify-content-between p-4">
							<i class="fas fa-space-shuttle fa-3x text-primary"></i>
							<div class="ms-3">
								<p class="mb-2">Numero Voti</p>
								<h6 class="mb-0"><%=valutazioniStudente.size()%></h6>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- Sale & Revenue End -->




			<div class="container-fluid pt-4 px-4">
				<div class="row g-4">
					<div class="col-sm-12 col-xl-6">
						<div class="bg-secondary rounded d-flex align-items-center justify-content-between p-4">




								<table class="table table-hover">
									<thead>
										<tr>
											<th scope="col">Materia</th>
											<th scope="col">Media</th>
											<th scope="col">N Voti</th>


										</tr>
									</thead>

									<tbody>
										<%
										for (Materie materia : listMaterieStudente) {
											String nomeMateria = materia.getNome_materia();
											int idMateria = materia.getId_materia();
											double sommaVoti = 0;
											int numeroVoti = 0;
											double mediaAttuale = 0;

											for (Valutazioni valutazioneAttuale : valutazioniStudente) {
												if (valutazioneAttuale.getId_materia().equals(idMateria)) {
											sommaVoti += valutazioneAttuale.getVoto();
											numeroVoti++;
												}

											}

											mediaAttuale = sommaVoti / numeroVoti;
											mediaAttuale = Math.round(mediaAttuale * 100.0) / 100.0;
										%>
										<tr>
											<td><%=nomeMateria%></td>
											<td><%=mediaAttuale%></td>
											<td><%=numeroVoti%></td>
										</tr>
										<%
										}
										%>

									</tbody>
								</table>
							</div>
						</div>



					<div class="col-sm-12 col-xl-6">
						<div class="bg-secondary rounded h-100 p-4">
							<div class="d-flex justify-content-between align-items-center ">
								<h5 class="mb-4">
									Valutazioni
									<%=nomeMateria1%></h5>


								<form class="form-inline my-2 my-lg-0 bg-dark"
									action="homeStudenti" method="GET" id="formMateria">
									<select class="form-select " name="materiaValutazioni"
										id="selectMateria">
										<%
										for (Valutazioni valutazione : valutazioniStudenteMateria) {

										}
										%>

										<option class="" value="-1">Seleziona una materia</option>

										<%
										for (Materie materia : listMaterieStudente) {
											String nomeMateria = materia.getNome_materia();
											int idMateria = materia.getId_materia();
										%>
										<option class="" value="<%=idMateria%>">

											<%=nomeMateria%>


										</option>
										<%
										}
										%>


									</select>
								</form>

							</div>

							<canvas id="voti_studente"></canvas>
						</div>
					</div>

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
// voti Chart

<%if (request.getAttribute("valutazioniStudenteMateria") != null) {%>

var ctx2 = $("#voti_studente").get(0).getContext("2d");
var myChart2 = new Chart(ctx2, {
    type: "line",
    data: {
        labels: [
        	
					<%//giusto

	int i = 1;
	for (Valutazioni valutazione4 : valutazioniStudenteMateria) {%>
                		<%=i%>,
                		
                		 <%i++;
}%>

        	],
        datasets: [
            {
                label: "Voto",
                data: [
                	
                	<%for (Valutazioni valutazione : valutazioniStudenteMateria) {%>
                		<%=valutazione.getVoto()%>,
                		
                		 <%}%>
                ],
                backgroundColor: "rgba(235, 22, 22, .5)",
                fill: true
            }
            
           
        ]
        },
    options: {
        responsive: true
    }
});
<%}%>

//manda subito select
document.getElementById("selectMateria").addEventListener("change",
		function() {
			if (this.value !== "-1") {
				document.getElementById("formMateria").submit();
			}
		});


</script>
</body>

</html>