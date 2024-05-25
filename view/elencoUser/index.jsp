
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="model.*"%>
<%@ page import="dao.UserDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String username = "";
List<User> users = (ArrayList<User>) request.getAttribute("users");
if (request.getAttribute("username") != null) {
	username = (String) request.getAttribute("username");
}

List<Classi> classiList = (ArrayList<Classi>) request.getAttribute("classiList");
List<Materie> materieList = (ArrayList<Materie>) request.getAttribute("materieList");

int indice = 1;
%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<title>View Users</title>

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

				<div class="bg-secondary rounded h-100 p-4">
				<div class="d-flex justify-content-between align-items-center ">
					<h4 class="mb-4">Lista Utenti</h4>
					
					<div class="d-flex justify-content-between align-items-center">
					
					<button class="btn btn-primary btn-sm px-4 gap-3" onclick="generatePDF()">Crea PDF</button>
					
					<form class="d-none d-md-flex ms-4" role="search" action="Users"
				method="get">
				<div class="container text-right">
					<div class="row">
						<div class="col">
							<input class="form-control bg-dark border-0" type="text" placeholder="username"
								name="username" value="<%=username%>">
						</div>
						<div class="col">

							<button class="btn btn-outline-light" type="submit">Search</button>
						</div>
					</div>
				</div>

			</form>
			</div>
			</div>
			
			

					<table id="userTable" class="table table-hover">
						<thead>
							<tr>
								<th scope="col">#</th>
								<th scope="col">Username</th>
								<th scope="col">Nome</th>
								<th scope="col">Cognome</th>
								<th scope="col">Email</th>
								<th scope="col">Cellulare</th>
								<th scope="col">Data nascita</th>
								<th scope="col">Ruolo</th>
								<th scope="col">ultimo accesso</th>
								<th scope="col">Data registrazione</th>
								<th scope="col">Azioni</th>


							</tr>
						</thead>
						<tbody>
							<%
							for (User user : users) {
							%>
							<tr>

								<td><%=indice%></td>
								<td><%=user.getUsername()%></td>
								<td class="limited-columnSmall"><%=user.getFirst_name()%></td>
								<td class="limited-columnSmall"><%=user.getLast_name()%></td>
								<td class="limited-columnSmall"><%=user.getEmail()%></td>
								<td><%=user.getCellphone()%></td>
								<td><%=user.getBirth_date()%></td>
								<td><%=user.getRoleString()%></td>
								<td><%=user.getDate_access()%></td>
								<td><%=user.getDate_insert()%></td>
								<td>
								
								<div class="btn-group" role="group">
						
								
								<a href="Users?action=DELETE&id=<%=user.getId()%>">
										<button type="button"
											class="btn btn-square btn-outline-primary m-2">
											<i class="fas fa-trash"></i>
										</button>
								</a> 
								
								 
								<a href="Users?action=EDIT&id=<%=user.getId()%>">
										<button type="button"
											class="btn btn-square btn-outline-primary m-2">
											<i class="fas fa-edit"></i>
										</button>
								</a>
						
								</div>
								</td>


							</tr>

							<%
							indice++;
							}
							%>
						</tbody>
					</table>

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

<script>
function generatePDF() {
    const { jsPDF } = window.jspdf;
    const doc = new jsPDF();

    const columns = [
        { header: '#', dataKey: 'index', width: 10 },
        { header: 'Username', dataKey: 'username', width: 30 },
        { header: 'Nome', dataKey: 'first_name', width: 30 },
        { header: 'Cognome', dataKey: 'last_name', width: 30 },
        { header: 'Email', dataKey: 'email', width: 50 },
        { header: 'Cellulare', dataKey: 'cellphone', width: 30 },
        { header: 'Data nascita', dataKey: 'birth_date', width: 30 },
        { header: 'Ruolo', dataKey: 'role', width: 20 },
        { header: 'Ultimo accesso', dataKey: 'date_access', width: 30 },
        { header: 'Data registrazione', dataKey: 'date_insert', width: 30 }
    ];

    const rows = Array.from(document.querySelectorAll('#userTable tbody tr')).map((row, index) => {
        const cells = row.querySelectorAll('td');
        return {
            index: cells[0].innerText,
            username: cells[1].innerText,
            first_name: cells[2].innerText,
            last_name: cells[3].innerText,
            email: cells[4].innerText,
            cellphone: cells[5].innerText,
            birth_date: cells[6].innerText,
            role: cells[7].innerText,
            date_access: cells[8].innerText,
            date_insert: cells[9].innerText
        };
    });

    doc.autoTable({
        columns: columns,
        body: rows,
        startY: 20,
        theme: 'grid',
        styles: {
            fontSize: 10,
            cellPadding: 3,
            overflow: 'linebreak' // Enable word wrapping
        },
        headStyles: {
            fillColor: [202,29,29],
            textColor: [255, 255, 255],
            halign: 'center'
        },
        bodyStyles: {
            halign: 'center'
        },
        columnStyles: {
            email: { cellWidth: 'auto' }, // Adjust the width for the email column
            first_name: { cellWidth: 'auto' },
            last_name: { cellWidth: 'auto' },
            cellphone: { cellWidth: 'auto' },
            birth_date: { cellWidth: 'auto' },
            role: { cellWidth: 'auto' },
            date_access: { cellWidth: 'auto' },
            date_insert: { cellWidth: 'auto' }
        }
    });

    doc.save('utenti.pdf');
}
</script>

</body>

</html>