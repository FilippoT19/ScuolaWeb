
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="model.*"%>
<%@ page import="dao.UserDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%

%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<title>About Us</title>

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




			<!-- Widget Start -->
            <div class="container-fluid pt-4 px-4">
            <div class="bg-secondary rounded h-100 p-4">
                <div class="row g-4">
                    <div class="col-sm-12 col-xl-6">
                        <div class="bg-secondary rounded h-100 p-4">
                            <h6 class="mb-4"></h6>
                            <div class="owl-carousel testimonial-carousel">
                                <div class="testimonial-item text-center">
                                    <img class="img-fluid rounded-circle mx-auto mb-4" src="resources/img/Topi.png" style="width: 100px; height: 100px;">
                                    <h5 class="mb-1">Filippo Topinelli</h5>
                                    <p>Ceo, Direttore Esecutivo</p>
                                    <p class="mb-0">Alla grande</p>
                                </div>
                                <div class="testimonial-item text-center">
                                    <img class="img-fluid rounded-circle mx-auto mb-4" src="resources/img/Corso.png" style="width: 100px; height: 100px;">
                                    <h5 class="mb-1">Davide Corso</h5>
                                    <p>Coding e Design</p>
                                    <p class="mb-0">Memento Mori</p>
                                </div>
                                <div class="testimonial-item text-center">
                                    <img class="img-fluid rounded-circle mx-auto mb-4" src="resources/img/Fattor.png" style="width: 100px; height: 100px;">
                                    <h5 class="mb-1">Giuseppe Fattor</h5>
                                    <p>Fannullone</p>
                                    <p class="mb-0">Stuzzichino</p>
                                </div>
                                <div class="testimonial-item text-center">
                                    <img class="img-fluid rounded-circle mx-auto mb-4" src="resources/img/Glend.png" style="width: 100px; height: 100px;">
                                    <h5 class="mb-1">Glend Xhymitiku</h5>
                                    <p>Supporto Morale</p>
                                    <p class="mb-0">vezë të Pashkëve</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-12 col-xl-6">
                        <div class="bg-secondary rounded h-100 p-4">
                    
                            <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d49747.83264457717!2d12.182163386139404!3d46.143286762549224!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x477907b078861a55%3A0x6797cdf47b3e57a6!2sIIS%20Segato%20-%20sede%20ITIS%20%22Girolamo%20Segato%22!5e0!3m2!1sit!2sit!4v1716458758500!5m2!1sit!2sit" width="530" height="315" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
                            
                        </div>
                    </div>
                </div>
            </div>
            <!-- Widget End -->
        </div>
			<!-- Content End -->
			
			<%@include file="/resources/externalJSP/footer.jsp"%>
			</div>


			<!-- Back to Top -->
			<a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i
				class="bi bi-arrow-up"></i></a>
		</div>
	

	<%@include file="/resources/externalJSP/scripts.jsp"%>

</body>

</html>