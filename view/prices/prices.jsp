
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="model.*"%>
<%@ page import="dao.UserDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Prezzi</title>
<style>
.rotate-45 {
	--transform-rotate: 45deg;
	transform: rotate(45deg);
}

.group:hover .group-hover\:flex {
	display: flex;
}

/* Cambia il colore del testo dei prezzi */
.text-5xl, .text-6xl {
	color: #eb1616; /* Rosso acceso */
}
</style>

	
  <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/tailwindcss/1.8.10/tailwind.min.css'>
	
<%@include file="/resources/externalJSP/links.jsp"%>
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


						<!-- partial:index.partial.html -->
						<div
							class="flex flex-col items-center justify-center mt-5 text-gray-700 ">
							<h2 class="text-2xl font-medium">Acquista un account</h2>

							<!-- Component Start -->
							<div
								class="flex flex-wrap items-center justify-center w-full max-w-1xl mt-8 mb-6">
								<!-- Tile 1 -->
								<div
									class="flex flex-col flex-grow mt-8 overflow-hidden bg-gray-800 rounded-lg shadow-lg">
									<div class="flex flex-col items-center p-10 bg-gray-700">
										<span class="font-semibold text-white">BASE</span>
										<!-- Cambia il colore del testo -->
										<div class="flex items-center">
											<span class="text-3xl">$</span> <span
												class="text-5xl font-bold">20</span> <span
												class="text-2xl text-gray-500">/mo</span>
										</div>
									</div>
									<div class="p-10">
										<ul>
											<li class="flex items-center text-white">
												<!-- Cambia il colore del testo --> <svg
													class="w-5 h-5 text-red-600 fill-current"
													xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"
													fill="currentColor">
              <path fill-rule="evenodd"
														d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
														clip-rule="evenodd" />
            </svg> <span class="ml-2">Zaino Magico</span>
											</li>
											<li class="flex items-center text-white">
												<!-- Cambia il colore del testo --> <svg
													class="w-5 h-5 text-red-600 fill-current"
													xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"
													fill="currentColor">
              <path fill-rule="evenodd"
														d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
														clip-rule="evenodd" />
            </svg> <span class="ml-2">Scuola di Dragologia</span>
											</li>
											<li class="flex items-center text-white">
												<!-- Cambia il colore del testo --> <svg
													class="w-5 h-5 text-red-600 fill-current"
													xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"
													fill="currentColor">
              <path fill-rule="evenodd"
														d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
														clip-rule="evenodd" />
            </svg> <span class="ml-2">Gara di Meme Scolastici</span>
											</li>
										</ul>
									</div>
									<div class="flex px-10 pb-10 justfy-center">
										<button type="button"
											class="flex items-center justify-center w-full h-12 px-6 text-sm btn btn-primary btn-lg px-4 gap-3">Acquista</button>

									</div>
								</div>
								<!-- Tile 2 -->
								<div
									class="z-10 flex flex-col flex-grow mt-8 overflow-hidden transform bg-gray-800 rounded-lg shadow-lg md:scale-110">
									<div class="flex flex-col items-center p-10 bg-gray-700">
										<span class="font-semibold text-white">MEDIO</span>
										<!-- Cambia il colore del testo -->
										<div class="flex items-center">
											<span class="text-3xl">$</span> <span
												class="text-6xl font-bold">50</span> <span
												class="text-2xl text-gray-500">/mo</span>
										</div>
									</div>
									<div class="p-10">
										<ul>
											<li class="flex items-center text-white">
												<!-- Cambia il colore del testo --> <svg
													class="w-5 h-5 text-red-600 fill-current"
													xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"
													fill="currentColor">
              <path fill-rule="evenodd"
														d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
														clip-rule="evenodd" />
            </svg> <span class="ml-2">Laboratorio di Magia</span>
											</li>
											<li class="flex items-center text-white">
												<!-- Cambia il colore del testo --> <svg
													class="w-5 h-5 text-red-600 fill-current"
													xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"
													fill="currentColor">
              <path fill-rule="evenodd"
														d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
														clip-rule="evenodd" />
            </svg> <span class="ml-2">Scudo dei Compiti</span>
											</li>
											<li class="flex items-center text-white">
												<!-- Cambia il colore del testo --> <svg
													class="w-5 h-5 text-red-600 fill-current"
													xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"
													fill="currentColor">
              <path fill-rule="evenodd"
														d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
														clip-rule="evenodd" />
            </svg> <span class="ml-2">Scuola dei Superpoteri</span>
											</li>
										</ul>
									</div>
									<div class="flex px-10 pb-10 justfy-center">
										<button type="button"
											class="flex items-center justify-center w-full h-12 px-6 text-sm btn btn-primary btn-lg px-4 gap-3">Acquista</button>

									</div>
								</div>

								<!-- Tile 3 -->
								<div
									class="flex flex-col flex-grow overflow-hidden bg-gray-800 rounded-lg shadow-lg mt-8">
									<div class="flex flex-col items-center p-10 bg-gray-700">
										<span class="font-semibold text-white">PRO</span>
										<!-- Cambia il colore del testo -->
										<div class="flex items-center">
											<span class="text-3xl">$</span> <span
												class="text-5xl font-bold">99</span> <span
												class="text-2xl text-gray-500">/mo</span>
										</div>
									</div>
									<div class="p-10">
										<ul>
											<li class="flex items-center text-white">
												<!-- Cambia il colore del testo --> <svg
													class="w-5 h-5 text-red-600 fill-current"
													xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"
													fill="currentColor">
              <path fill-rule="evenodd"
														d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
														clip-rule="evenodd" />
            </svg> <span class="ml-2">Teletrasporto in Classe</span>
											</li>
											<li class="flex items-center text-white">
												<!-- Cambia il colore del testo --> <svg
													class="w-5 h-5 text-red-600 fill-current"
													xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"
													fill="currentColor">
              <path fill-rule="evenodd"
														d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
														clip-rule="evenodd" />
            </svg> <span class="ml-2">Oracolo degli Studi</span>
											</li>
											<li class="flex items-center text-white">
												<!-- Cambia il colore del testo --> <svg
													class="w-5 h-5 text-red-600 fill-current"
													xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"
													fill="currentColor">
              <path fill-rule="evenodd"
														d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
														clip-rule="evenodd" />
            </svg> <span class="ml-2">Generatore di Scuse Creative</span>
											</li>
										</ul>
									</div>
									<div class="flex px-10 pb-10 justfy-center">

										<button type="button"
											class="flex items-center justify-center w-full h-12 px-6 text-sm btn btn-primary btn-lg px-4 gap-3">Acquista</button>
									</div>
								</div>
							</div>
							<!-- Component End  -->

						</div>
					</div>
				</div>
			</div>

			<!-- partial -->
			<%@include file="/resources/externalJSP/footer.jsp"%>
		</div>


		<!-- Back to Top -->
		<a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i
			class="bi bi-arrow-up"></i></a>
	</div>


	<%@include file="/resources/externalJSP/scripts.jsp"%>
</body>
</html>
