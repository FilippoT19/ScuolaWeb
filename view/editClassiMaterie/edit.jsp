<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="org.eclipse.jdt.internal.compiler.ast.IfStatement"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.If"%>
<%@page import="javax.lang.model.type.NullType"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="model.*"%>
<%@ page import="utils.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String action = (String) request.getAttribute("action");

Classi classeSelect = null;
Materie materiaSelect = null;
String titolo = "";
String cm = "";
Integer id = null;

if (request.getAttribute("classeSelect") != null) {
	classeSelect = (Classi) request.getAttribute("classeSelect");
	id = classeSelect.getId_classe();
	cm = "classe";

}

if (request.getAttribute("materiaSelect") != null) {
	materiaSelect = (Materie) request.getAttribute("materiaSelect");
	id = materiaSelect.getId_materia();
	cm = "materia";
}
%>

<!DOCTYPE html>
<html>
<head>
<title><%=action%></title>
<%@include file="/resources/externalJSP/links.jsp"%>

<style>
.wrapper {
	display: grid;
	grid-template-columns: repeat(3, 1fr);
	gap: 10px;
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
					<h4 class="mb-4"><%=action%>
						<%=cm%></h4>

					<div class="form-floating mb-3">


						<form class="form-floating mb-3" action="ClassiMaterie" method="post">
							<input type="hidden" name="id<%=cm%>" value="<%=id%>"> <input
								type="hidden" name="id<%=cm%>" value="<%=id%>">

							<div class="border border-dark rounded mb-3 p-3">

								<%
								if (cm.equals("classe")) {
								%>
								<div class="mb-3">
									<label for="classeNome" class="form-label fw-bold">Nome:</label>
									<input type="text" class="form-control" name="classeNome"
										value="<%=classeSelect.getNome_classe() != null ? classeSelect.getNome_classe() : StringUtils.STRING_EMPTY%>"
										required="required" />
								</div>

								<%
								} else {
								%>

								<div class="mb-3">
									<label for="materiaNome" class="form-label fw-bold">Nome:</label>
									<input type="text" class="form-control" name="materiaNome"
										value="<%=materiaSelect.getNome_materia() != null ? materiaSelect.getNome_materia() : StringUtils.STRING_EMPTY%>"
										required="required" />
								</div>
								<%
								}
								%>

							</div>

							<button type="submit" class="btn btn-primary">salva</button>

						</form>
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


</body>

</html>

