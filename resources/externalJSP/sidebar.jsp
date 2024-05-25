<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
User u_sidebar = (User) session.getAttribute("session_user");
int u_sidebar_role =  u_sidebar.getIdRole() ;

String homeActive = "";
String valutazioniActive ="";
String utentiActive = "";
String aboutActive = "";
String pricesActive = "";
String classiMaterieActive = "";

if (session.getAttribute("paginaAttuale") != null){
	String attuale = (String)session.getAttribute("paginaAttuale");
	
	if(attuale.equals("home")){
		homeActive = "active";
	}else if(attuale.equals("valutazioni")){
		valutazioniActive = "active";
	}else if(attuale.equals("utenti")){
		utentiActive = "active";
	}else if(attuale.equals("about")){
		aboutActive = "active";
	}else if(attuale.equals("prices")){
		pricesActive = "active";
	}else if(attuale.equals("classiMaterie")){
		classiMaterieActive = "active";
	}
}

%>

<!-- Spinner Start -->
	<!-- 	<div id="spinner"
			class="show bg-dark position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
			<div class="spinner-border text-primary"
				style="width: 3rem; height: 3rem;" role="status">
				<span class="sr-only">Loading...</span>
			</div>
		</div> -->
		<!-- Spinner End -->
		
		<!-- Sidebar Start -->
		<div class="sidebar pe-4 pb-3">
			<nav class="navbar bg-secondary navbar-dark">
				<a href="homeDirigenza" class="navbar-brand mx-4 mb-3">
					<h3 class="text-primary">
						<i class="fas fa-user-edit me-2"></i>ScuolaWeb
					</h3>
				</a>
				<div class="d-flex align-items-center ms-4 mb-4">
					<div class="position-relative">
						<img class="rounded-circle" src="resources/img/elon_profile.jpeg" alt=""
							style="width: 40px; height: 40px;">
						<div
							class="bg-success rounded-circle border border-2 border-white position-absolute end-0 bottom-0 p-1"></div>
					</div>
					<div class="ms-3">
						<h6 class="mb-0"><%=u_sidebar.getUsername()%></h6>
						<span><%=u_sidebar.getRoleString()%></span>
					</div>
				</div>

<%
if(u_sidebar_role == 2){ //insegnante
%>

		

				<div class="navbar-nav w-100">
					<a href="/ScuolaWeb/homeInsegnanti" class="nav-item nav-link <%=homeActive%>"><i class="fas fa-home me-2"></i>Home</a>
					<a href="Valutazioni" class="nav-item nav-link  <%=valutazioniActive%>"><i class="fas fa-school me-2"></i>Valutazioni</a>
					<a href="/ScuolaWeb/prices" class="nav-item nav-link  <%=pricesActive%>"><i class="fas fa-money-bill-wave me-2"></i>Prezzi</a>
					<a href="/ScuolaWeb/about" class="nav-item nav-link  <%=aboutActive%>"><i class="fas fa-pepper-hot me-2"></i>About Us</a> 
				</div>
			</nav>
		</div>
		<!-- Sidebar End -->
		
		
<%
}else if(u_sidebar_role == 4){ //studente
%>


				<div class="navbar-nav w-100">
					<a href="/ScuolaWeb/homeStudenti" class="nav-item nav-link  <%=homeActive%>">
					<i class="fas fa-home me-2"></i>Home</a>
					<a href="/ScuolaWeb/prices" class="nav-item nav-link  <%=pricesActive%>"><i class="fas fa-money-bill-wave me-2"></i>Prezzi</a>
					<a href="/ScuolaWeb/about" class="nav-item nav-link  <%=aboutActive%>"><i class="fas fa-pepper-hot me-2"></i>About Us</a>
				</div>
			</nav>
		</div>
		<!-- Sidebar End -->

<%
}else{ //segreteria
%>


		
				<div class="navbar-nav w-100">
					<a href="/ScuolaWeb/homeDirigenza" class="nav-item nav-link  <%=homeActive%>"><i class="fas fa-home me-2"></i>Home</a>
					<div class="nav-item dropdown">
						<a href="#" class="nav-link dropdown-toggle <%=utentiActive%>"
							data-bs-toggle="dropdown"><i class="fas fa-users me-2"></i>Utenti</a>
						<div class="dropdown-menu bg-transparent border-0">
							<a href="Users" class="dropdown-item">Lista Utenti</a> <a
								href="Users?action=INSERT" class="dropdown-item">Aggiungi
								Utente</a>
						</div>
					</div>
					<a href="Valutazioni" class="nav-item nav-link  <%=valutazioniActive%>"><i class="fas fa-school me-2"></i>Valutazioni</a>
					
					<div class="nav-item dropdown">
						<a href="#" class="nav-link dropdown-toggle <%=classiMaterieActive%>"
							data-bs-toggle="dropdown"><i class="fas fa-users me-2"></i>Classi Materie</a>
						<div class="dropdown-menu bg-transparent border-0">
							<a href="ClassiMaterie" class="dropdown-item">Lista Classi Materie</a> 
							<a
								href="ClassiMaterie?action=INSERTCLASSE" class="dropdown-item">Aggiungi
								Classe</a>
								<a
								href="ClassiMaterie?action=INSERTMATERIA" class="dropdown-item">Aggiungi
								Materia</a>
						</div>
					</div>
					
					
					
					<a href="/ScuolaWeb/prices" class="nav-item nav-link  <%=pricesActive%>"><i class="fas fa-money-bill-wave me-2"></i>Prezzi</a>
					<a href="/ScuolaWeb/about" class="nav-item nav-link  <%=aboutActive%>"><i class="fas fa-pepper-hot me-2"></i>About Us</a>
				</div>
			</nav>
		</div>
		<!-- Sidebar End -->
<%
}
%>
		
			
