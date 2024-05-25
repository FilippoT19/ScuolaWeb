<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
User u_navbar = (User) session.getAttribute("session_user");
%>

		<!-- Navbar Start -->
			<nav
				class="navbar navbar-expand bg-secondary navbar-dark sticky-top px-4 py-0">
				<a href="index.html" class="navbar-brand d-flex d-lg-none me-4">
					<h2 class="text-primary mb-0">
						<i class="fa fa-user-edit"></i>
					</h2>
				</a> <a href="#" class="sidebar-toggler flex-shrink-0"> <i
					class="fa fa-bars"></i>
				</a>
				<div class="navbar-nav align-items-center ms-auto">
					<div class="nav-item dropdown">
						<div class="nav-item dropdown">
							<a href="#" class="nav-link dropdown-toggle"
								data-bs-toggle="dropdown"> <img
								class="rounded-circle me-lg-2" src="resources/img/elon_profile.jpeg" alt=""
								style="width: 40px; height: 40px;"> <span
								class="d-none d-lg-inline-flex"><%=u_navbar.getUsername()%></span>
							</a>
							<div
								class="dropdown-menu dropdown-menu-end bg-secondary border-0 rounded-0 rounded-bottom m-0">
								 <a href="/ScuolaWeb"
									class="dropdown-item">Log Out</a>
							</div>
						</div>
					</div>
					</div>
			</nav>
			<!-- Navbar End -->
			
