
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="model.*"%>
<%@ page import="dao.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String username = "";
if (request.getAttribute("username") != null) {
	username = (String) request.getAttribute("username");
}

Map<Integer, Integer> studentiCountPerClasse = (Map<Integer, Integer>) request.getAttribute("studentiCountPerClasse");
Map<Integer, Integer> classiCountPerMateria = (Map<Integer, Integer>) request.getAttribute("classiCountPerMateria");

List<Classi> classi = (ArrayList<Classi>) request.getAttribute("classi");
List<Materie> materie = (ArrayList<Materie>) request.getAttribute("materie");

int indiceClassi = 1;
int indiceMaterie = 1;
%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<title>Classi e Materie</title>

<%@include file="/resources/externalJSP/links.jsp"%>

<style>
.limited-columnLarge {
	max-width: 150px; /* Set your desired maximum width here */
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
}

.limited-columnSmall {
	max-width: 100px; /* Set your desired maximum width here */
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
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
				<div class="row g-4">
					<div class="col-sm-12 col-xl-6">
						<div class="bg-secondary rounded h-100 p-4">
					
							<h4 class="mb-4">Classi</h4>
						
							<table id="classiTable" class="table table-hover">
								<thead>
									<tr>
										<th scope="col">#</th>
										<th scope="col">Nome</th>
										<th scope="col">N Studenti</th>
										<th scope="col">Azioni</th>


									</tr>
								</thead>
								<tbody>
									<%
									for (Classi classe : classi) {
									%>
									<tr>

										<td><%=indiceClassi%></td>
										<td><%=classe.getNome_classe()%></td>
										
										<%
										
										%>

										<td><%=studentiCountPerClasse.get(classe.getId_classe())%></td>

										<td>

											<div class="btn-group" role="group">

												<a
													href="ClassiMaterie?action=EDITCLASSE&id=<%=classe.getId_classe()%>">
													<button type="button"
														class="btn btn-square btn-outline-primary m-2">
														<i class="fas fa-edit"></i>
													</button>
												</a> 
												
												<%
												
												if(studentiCountPerClasse.get(classe.getId_classe()) == 0){
												
												%>
												
												<a
													href="ClassiMaterie?action=DELETECLASSE&id=<%=classe.getId_classe()%>">
													<button type="button"
														class="btn btn-square btn-outline-primary m-2">
														<i class="fas fa-trash"></i>
													</button>
												</a>
												
												<%
												}
												%>

											</div>
										</td>


									</tr>

									<%
									indiceClassi++;
									}
									%>
								</tbody>
							</table>
						</div>
					</div>

					<div class="col-sm-12 col-xl-6">
						<div class="bg-secondary rounded h-100 p-4">

							<h4 class="mb-4">Materie</h4>

							<table id="materieTable" class="table table-hover">
								<thead>
									<tr>
										<th scope="col">#</th>
										<th scope="col">Nome</th>
										<th scope="col">N Classi</th>
										<th scope="col">Azioni</th>


									</tr>
								</thead>
								<tbody>
									<%
									for (Materie materia : materie) {
									%>
									<tr>

										<td><%=indiceMaterie%></td>
										<td><%=materia.getNome_materia()%></td>

										<td><%=classiCountPerMateria.get(materia.getId_materia())%></td>

										<td>

											<div class="btn-group" role="group">

												<a
													href="ClassiMaterie?action=EDITMATERIA&id=<%=materia.getId_materia()%>">
													<button type="button"
														class="btn btn-square btn-outline-primary m-2">
														<i class="fas fa-edit"></i>
													</button>
												</a> 
												<%
												if(classiCountPerMateria.get(materia.getId_materia()) == 0){
												%>
												<a
													href="ClassiMaterie?action=DELETEMATERIA&id=<%=materia.getId_materia()%>">
													<button type="button"
														class="btn btn-square btn-outline-primary m-2">
														<i class="fas fa-trash"></i>
													</button>
												</a>
												
												<%
												}
												%>

											</div>
										</td>


									</tr>

									<%
									indiceMaterie++;
									}
									%>
								</tbody>
							</table>
						</div>
					</div>
				</div>




			</div>
			<!-- Content End -->

			<%@include file="/resources/externalJSP/footer.jsp"%>


			<!-- Back to Top -->
			<a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i
				class="bi bi-arrow-up"></i></a>
		</div>
	</div>

	<%@include file="/resources/externalJSP/scripts.jsp"%>

</body>

</html>