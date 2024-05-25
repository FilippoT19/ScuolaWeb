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
User user = (User) request.getAttribute("user");
String id = "0";
if (request.getAttribute("id") != null) {
	id = Integer.toString((Integer) request.getAttribute("id"));
}

List<Classi> classi = (ArrayList<Classi>) request.getAttribute("classi");
List<Materie> materie = (ArrayList<Materie>) request.getAttribute("materie");

int counterCM = 0;

Studenti studente = new Studenti();
List<Insegnanti_classi_materie> insegnanti_classi_materie = new ArrayList<>();

String action = (String) request.getAttribute("action");

String titolo = "";

if (action.equals("INSERT") ){
	
	titolo = "CREAZIONE";
}else{
	titolo = "MODIFICA";
}

%>

<!DOCTYPE html>
<html>
<head>
<title>Edit User</title>
<%@include file="/resources/externalJSP/links.jsp" %> 

<style>
        .wrapper {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 10px;
        }
    </style>
</head>
<body class="bg-light" onload="addFieldsOnLoad()">

<div class="container-fluid position-relative d-flex p-0">
	
		
		
		<%@include file="/resources/externalJSP/sidebar.jsp" %> 
		
		<!-- Content Start -->
		<div class="content">
		
		<%@include file="/resources/externalJSP/navbar.jsp" %> 


<div class="container-fluid pt-4 px-4">
	<div class="bg-secondary rounded h-100 p-4">
	 <h4 class="mb-4"><%=titolo%> UTENTE</h4>
	 
                            <div class="form-floating mb-3">

	
	<form class="form-floating mb-3" action="Users" method="post">
		<input type="hidden" name="id" value="<%=id%>"> <input
			type="hidden" name="action"
			value="<%=request.getAttribute("action")%>">

		<div class="border border-dark rounded mb-3 p-3">
		<div class="mb-3">
			<label for="username" class="form-label fw-bold">Username:</label> <input
				type="text" class="form-control" name="username"
				value="<%=user.getUsername() != null ? user.getUsername() : StringUtils.STRING_EMPTY%>"
				required="required" />
		</div>

		<div class="mb-3">
			<label for="first_name" class="form-label fw-bold">Nome:</label> <input
				type="text" class="form-control" name="first_name"
				value="<%=user.getFirst_name() != null ? user.getFirst_name() : StringUtils.STRING_EMPTY%>"
				required="required" />
		</div>

		<div class="mb-3">
			<label for="last_name" class="form-label fw-bold">Cognome:</label> <input
				type="text" class="form-control" name="last_name"
				value="<%=user.getLast_name() != null ? user.getLast_name() : StringUtils.STRING_EMPTY%>"
				required="required" />
		</div>

		<div class="mb-3">
			<label for="password" class="form-label fw-bold">Password:</label> <input
				type="password" class="form-control" name="password"
				value="<%=user.getPassword() != null ? user.getPassword() : StringUtils.STRING_EMPTY%>"
				required="required" />
		</div>

		<div class="mb-3">
			<label for="email" class="form-label fw-bold">Email:</label> <input
				type="email" class="form-control" name="email"
				value="<%=user.getEmail() != null ? user.getEmail() : StringUtils.STRING_EMPTY%>"
				required="required" />
		</div>

		<div class="mb-3">
			<label for="cellphone" class="form-label fw-bold">Numero di
				cellulare:</label> <input type="text" class="form-control" name="cellphone"
				value="<%=user.getCellphone() != null ? user.getCellphone() : StringUtils.STRING_EMPTY%>"
				required="required" />
		</div>

		<div class="mb-3">
			<label for="birth_date" class="form-label fw-bold">Data di
				nascita:</label> <input type="date" class="form-control" name="birth_date"
				value="<%=user.getBirth_date() != null ? user.getBirth_date() : StringUtils.STRING_EMPTY%>"
				required="required" />
		</div>

		<div class="mb-3">
			<label for="id_role" class="form-label fw-bold">Ruolo:</label> <select
				onChange="addFields()" class="form-select  mb-3" name="id_role"
				id="id_role" required="required">
				<option value="1" <%=user.getIdRole() == 1 ? "selected" : ""%>>Dirigenza</option>
				<option value="2" <%=user.getIdRole() == 2 ? "selected" : ""%>>Insegnante</option>
				<option value="3" <%=user.getIdRole() == 3 ? "selected" : ""%>>Segreteria</option>
				<option value="4" <%=user.getIdRole() == 4 ? "selected" : ""%>>Studente</option>

			</select>
		</div>
		</div>

		<!-- Container for dynamic fields -->
		<div class="wrapper mb-1" id="dynamicFields"></div>
		
				<!-- Container for dynamic fields -->
		<div class="mb-3" id="bottoneClassiMaterie"></div>
		
		<button type="submit" class="btn btn-primary">salva</button>

	</form>
	</div>
	</div>
	
	</div>
	
	<%@include file="/resources/externalJSP/footer.jsp" %> 
			
		</div>
		<!-- Content End -->


		<!-- Back to Top -->
		<a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i
			class="bi bi-arrow-up"></i></a>
	</div>

	<%@include file="/resources/externalJSP/scripts.jsp" %> 
	
	<script>

	var counterClassiMaterie;

	function addFieldsOnLoad() {
		
		//aggiungo o no il bottone
		aggiungoBottone();
	    
		if (document.getElementById("id_role") != null) {
		    var roleSelect = document.getElementById("id_role");
		    var selectedRole = roleSelect.options[roleSelect.selectedIndex].value;
		}

		var container = document.getElementById("dynamicFields");

		// Clear any existing dynamic fields
		container.innerHTML = "";

		// If the selected role is "Studente" (value 4), add class selection field
		if (selectedRole === "4") {
		    <% if (request.getAttribute("studente") != null) {
		        studente = (Studenti) request.getAttribute("studente");
		    %>

		    // Crea un nuovo div per la selezione della classe
		    var classDiv = document.createElement("div");
		    classDiv.className = "border border-danger rounded mb-1 p-3";

		    var classSelect = document.createElement("select");
		    classSelect.name = "id_classe";
		    classSelect.className = "form-select  mb-3";

		    // Aggiungi opzioni per ogni classe
		    <% for (Classi classe : classi) {
		        %>
		        var option = document.createElement("option");
		        option.value = "<%=classe.getId_classe()%>";
		        option.text = "<%=classe.getNome_classe()%>";
		        classSelect.appendChild(option);
		        
		        // Controlla se questa opzione è selezionata
		        if ("<%=classe.getId_classe()%>" === "<%=studente.getId_classe()%>") {
		            option.selected = true;
		        }
		    <%}%>

		    var label = document.createElement("label");
		    label.innerHTML = "Classe:";
		    label.className = "form-label fw-bold";

		    classDiv.appendChild(label);
		    classDiv.appendChild(classSelect);

		    // Aggiungi il nuovo div al container
		    container.appendChild(classDiv);
		    <%}%>
		}


	     // If the selected role is "Insegnante" (value 2), add class and subject selection fields
	     if (selectedRole === "2") {
	    	 
	    	 //aggiungi il bottone 
	    	 aggiungoBottone();

			counterClassiMaterie = 0;
			
			<% if (request.getAttribute("insegnanti_classi_materie") != null) {
			    insegnanti_classi_materie = (List<Insegnanti_classi_materie>) request.getAttribute("insegnanti_classi_materie");
			    %>
			    <% for (Insegnanti_classi_materie insegnante_classe_materia : insegnanti_classi_materie) {
			        %>
			        // Crea un nuovo div per ogni coppia classe-materia
			        var newDiv = document.createElement("div");
			        newDiv.className = "border border-danger rounded mb-1 p-3";

			        var classLabel = document.createElement("label");
			        classLabel.innerHTML = "Classe:";
			        classLabel.className = "form-label fw-bold";
			        newDiv.appendChild(classLabel);

			        var classSelect = document.createElement("select");
			        classSelect.name = "id_classe_" + counterClassiMaterie;
			        classSelect.className = "form-select  mb-3";
			        // Aggiungi opzioni per ogni classe
			        <% for (Classi classe : classi) {
			            %>
			            var option = document.createElement("option");

			            if ("<%=classe.getId_classe()%>" == "<%=insegnante_classe_materia.getId_classe()%>") {
			                option.selected = true;
			            }

			            option.value = "<%=classe.getId_classe()%>";
			            option.text = "<%=classe.getNome_classe()%>";
			            classSelect.appendChild(option);
			        <%}%>
			        newDiv.appendChild(classSelect);

			        var subjectLabel = document.createElement("label");
			        subjectLabel.innerHTML = "Materia:";
			        subjectLabel.className = "form-label fw-bold";
			        newDiv.appendChild(subjectLabel);

			        var subjectSelect = document.createElement("select");
			        subjectSelect.name = "id_materia_" + counterClassiMaterie;
			        subjectSelect.className = "form-select  mb-3";
			        // Aggiungi opzioni per ogni materia
			        <% for (Materie materia : materie) {
			            %>
			            var option = document.createElement("option");

			            if ("<%=materia.getId_materia()%>" == "<%=insegnante_classe_materia.getId_materia()%>") {
			                option.selected = true;
			            }

			            option.value = "<%=materia.getId_materia()%>";
			            option.text = "<%=materia.getNome_materia()%>";
			            subjectSelect.appendChild(option);
			        <%}%>
			        newDiv.appendChild(subjectSelect);
			        
			        
			     // Crea tasto Rimuovi
			    	var removeLink = document.createElement("a");
			    	removeLink.innerHTML = "Rimuovi";
			    	removeLink.href = "#";
			    	removeLink.className = "btn btn-link rounded-pill m-2";
			    	removeLink.setAttribute("onclick", "rimuoviDiv(this); event.preventDefault();");
			    	
			    	// Aggiungi il link al fieldContainer
			    	newDiv.appendChild(removeLink);
			        
			        

			        // Aggiungi il nuovo div al container
			        container.appendChild(newDiv);

			        counterClassiMaterie++;
			    <%}%>
			<%}%>

	     }

		}


	function addFields() {
		//aggiungo o no il bottone
		aggiungoBottone();
		
		var roleSelect = document.getElementById("id_role");
		var selectedRole = roleSelect.options[roleSelect.selectedIndex].value;
		var container = document.getElementById("dynamicFields");

		// Clear any existing dynamic fields
		container.innerHTML = "";

		// If the selected role is "Studente" (value 4), add class selection field
		if (selectedRole === "4") {
		    // Crea un nuovo div per la selezione della classe
		    var classDiv = document.createElement("div");
		    classDiv.className = "border border-danger rounded mb-1 p-3";

		    var classSelect = document.createElement("select");
		    classSelect.name = "id_classe";
		    classSelect.className = "form-select  mb-3";

		    // Aggiungi opzioni per ogni classe
		    <%for (Classi classe : classi) {%>
		        var option = document.createElement("option");
		        option.value = "<%=classe.getId_classe()%>";
		        option.text = "<%=classe.getNome_classe()%>";
		        classSelect.appendChild(option);
		    <%}%>

		    var label = document.createElement("label");
		    label.innerHTML = "Classe:";
		    label.className = "form-label fw-bold";

		    classDiv.appendChild(label);
		    classDiv.appendChild(classSelect);

		    // Aggiungi il nuovo div al container
		    container.appendChild(classDiv);
		}


		// If the selected role is "Insegnante" (value 2), add class and subject selection fields
		if (selectedRole === "2") {
			counterClassiMaterie = 0;
		    // Crea un nuovo div per la selezione della classe e della materia
		    var classSubjectDiv = document.createElement("div");
		    classSubjectDiv.className = "border border-danger rounded mb-1 p-3";

		    var classLabel = document.createElement("label");
		    classLabel.innerHTML = "Classe:";
		    classLabel.className = "form-label fw-bold";
		    classSubjectDiv.appendChild(classLabel);

		    var classSelect = document.createElement("select");
		    classSelect.name = "id_classe_" + counterClassiMaterie;
		    classSelect.className = "form-select  mb-3";
		    // Aggiungi opzioni per ogni classe
		    <%for (Classi classe : classi) {%>
		        var option = document.createElement("option");
		        option.value = "<%=classe.getId_classe()%>";
		        option.text = "<%=classe.getNome_classe()%>";
		        classSelect.appendChild(option);
		    <%}%>
		    classSubjectDiv.appendChild(classSelect);

		    var subjectLabel = document.createElement("label");
		    subjectLabel.innerHTML = "Materia:";
		    subjectLabel.className = "form-label fw-bold";
		    classSubjectDiv.appendChild(subjectLabel);

		    var subjectSelect = document.createElement("select");
		    subjectSelect.name = "id_materia_" + counterClassiMaterie;
		    subjectSelect.className = "form-select  mb-3";
		    // Aggiungi opzioni per ogni materia
		    <%for (Materie materia : materie) {%>
		        var option = document.createElement("option");
		        option.value = "<%=materia.getId_materia()%>";
		        option.text = "<%=materia.getNome_materia()%>";
		        subjectSelect.appendChild(option);
		    <%}%>
		    classSubjectDiv.appendChild(subjectSelect);
		    
		 // Crea tasto Rimuovi
			var removeLink = document.createElement("a");
			removeLink.innerHTML = "Rimuovi";
			removeLink.href = "#";
			removeLink.className = "btn btn-link rounded-pill m-2";
			removeLink.addEventListener("click", function(event) {
				 event.preventDefault();
			    removeField(classSubjectDiv, container); // Chiamata alla funzione di rimozione	   
			});
			
			// Aggiungi il link al fieldContainer
			classSubjectDiv.appendChild(removeLink);
		 

		    // Aggiungi il nuovo div al container
		    container.appendChild(classSubjectDiv);
		    counterClassiMaterie++;
		} else {
		    counterClassiMaterie = 0;
		}

	}


	function aggiungoBottone() {
		
			
			 var roleSelect = document.getElementById("id_role");
			 var selectedRole = roleSelect.options[roleSelect.selectedIndex].value;

		     if (selectedRole === "2") { //insegnante

		    // Crea un nuovo elemento  	  
		    
		    var bottone = document.createElement("a");
		    
		    // Aggiungi un testo al bottone
		    bottone.innerHTML = "Aggiungi una classe";
		    
		    bottone.href = "#";
		    
		    //stile
		    bottone.className = "btn btn-outline-primary mt-2 mb-2";
		    
		    // Aggiungi un gestore di eventi al bottone
		    bottone.setAttribute("onclick", "addClasseMateria(); event.preventDefault();");
		    
		    // Trova il div con l'id "bottoneClasseMateria"
		    var divBottone = document.getElementById("bottoneClassiMaterie");
		    
		    if(divBottone.innerHTML.trim() === ""){
		    
		    // Aggiungi il bottone al div
		    divBottone.appendChild(bottone);
		    
		    	 }
		     }else{
		    	 //cancella bottone
		    	 var divBottone = document.getElementById("bottoneClassiMaterie");
		    	 divBottone.innerHTML = "";
		    	 counterClassiMaterie = 0;
		     }
	}

	
function addClasseMateria() {
	var container = document.getElementById("dynamicFields");
	
	   // Controllo del limite di coppie ClassiMaterie
    if (counterClassiMaterie < 30) {

	// Create a container for the new fields
	var fieldContainer = document.createElement("div");
	fieldContainer.className = "border border-danger rounded mb-1 p-3";

	// Add class selection field
	var classLabel = document.createElement("label");
	classLabel.innerHTML = "Classe:";
	classLabel.className = "form-label fw-bold";
	fieldContainer.appendChild(classLabel);

	var classSelect = document.createElement("select");
	classSelect.name = "id_classe_" + counterClassiMaterie;
	classSelect.className = "form-select  mb-3";
	// Add options for each class
	<%for (Classi classe : classi) {%>
	    var option = document.createElement("option");
	    option.value = "<%=classe.getId_classe()%>";
	    option.text = "<%=classe.getNome_classe()%>";
	    classSelect.appendChild(option);
	<%}%>
	fieldContainer.appendChild(classSelect);

	// Add subject selection field
	var subjectLabel = document.createElement("label");
	subjectLabel.innerHTML = "Materia:";
	subjectLabel.className = "form-label fw-bold";
	fieldContainer.appendChild(subjectLabel);

	var subjectSelect = document.createElement("select");
	subjectSelect.name = "id_materia_" + counterClassiMaterie;
	subjectSelect.className = "form-select  mb-3";
	// Add options for each subject
	<%for (Materie materia : materie) {%>
	    var option = document.createElement("option");
	    option.value = "<%=materia.getId_materia()%>";
	    option.text = "<%=materia.getNome_materia()%>";
	    subjectSelect.appendChild(option);
	<%}%>
	fieldContainer.appendChild(subjectSelect);
	
	// Crea tasto Rimuovi
	var removeLink = document.createElement("a");
	removeLink.innerHTML = "Rimuovi";
	removeLink.href = "#";
	removeLink.className = "btn btn-link rounded-pill m-2";
	removeLink.addEventListener("click", function(event) {
		event.preventDefault();
	    removeField(fieldContainer, container); // Chiamata alla funzione di rimozione	   
	});
	
	// Aggiungi il link al fieldContainer
	fieldContainer.appendChild(removeLink);



	// Add the new fields container to the main container
	container.appendChild(fieldContainer);

	counterClassiMaterie++;
    }

}

//Funzione per rimuovere il campo dinamico
function removeField(fieldContainer, container) {
    container.removeChild(fieldContainer);
}
function rimuoviDiv(button) {
    // Rimuovi il genitore del pulsante, che è il div che vogliamo eliminare
    button.parentNode.parentNode.removeChild(button.parentNode);
}



</script>
</body>

</html>

