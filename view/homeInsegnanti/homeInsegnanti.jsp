<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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


<%@include file="/resources/externalJSP/links.jsp" %> 


</head>


<body>
	<div class="container-fluid position-relative d-flex p-0">
	
		
		
		<%@include file="/resources/externalJSP/sidebar.jsp" %> 
		
		<!-- Content Start -->
		<div class="content">
		
		<%@include file="/resources/externalJSP/navbar.jsp" %> 

			
			<%@include file="/resources/externalJSP/footer.jsp" %> 
		</div>
		<!-- Content End -->
		
		


		<!-- Back to Top -->
		<a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i
			class="bi bi-arrow-up"></i></a>
	</div>

	<%@include file="/resources/externalJSP/scripts.jsp" %> 

</body>

</html>