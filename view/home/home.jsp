<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%
	User u = (User)session.getAttribute("session_user");
%>
<!DOCTYPE html>
<html>
  <head>
    <%@include file="/view/structure/head.jsp" %>
    <title>Home</title>
  </head>
  <body>
    <main class="d-flex justify-content-center h-100 align-items-center">
      <div class="card px-4 py-5 my-5 text-center">
        <h1 class="display-5 fw-bold text-body-emphasis">
          <strong>Benvenuto, <span class="text-primary"><%= u.getUsername() %></span></strong>
        </h1>
        <div class="col-lg-6 mx-auto">
          <p class="lead mb-4">
            Ti trovi ora nella tua area privata. scegli l'operazione che intendi
            esegure.
          </p>
          
          <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
            <a href="Users"
              ><button type="button" class="btn btn-primary">
                Gestione degli utenti
              </button></a
            >
          </div><br>
          
        </div>
      </div>
    </main>
  </body>
</html>
