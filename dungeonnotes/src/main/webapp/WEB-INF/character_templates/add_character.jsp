<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="true"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css">

<style>
	#username {
		font-size: 18px;
		vertical-align: middle;
		margin-top: 10px;
	}
</style>

<script>
	var classCount = 1;

	function avatarButtonClick() {
		var profileWindow = document.getElementById("profile-window");
		profileWindow.classList.toggle("is-hidden");
	}
	
	
	function onDeleteButtonClick() {
		const response = confirm(`You are about to delete one of your worlds.
			This action can't be undone. Continue?`, );
		if(response == false) {
			event.preventDefault();
		}
		return response;
	}
	
	function onAddClassBtnClick() {
		var classSelectDiv = document.querySelector(".class-select-".concat(classCount.toString()));
		classCount++;
		var divHTML = "<div class='field class-select-".concat(classCount.toString()) + "'><select class='select' name='" + "dnd-class-".concat(classCount.toString()) + "'><c:forEach var='dnd_class' items='${classList}'><option value='${dnd_class.id}'><c:out value='${dnd_class.name}'/></option></c:forEach></select><label for='" + "class-levels-".concat(classCount.toString()) + "'>Levels: </label><input name='" + "class-levels-".concat(classCount.toString()) + "' type='number'/></div>";
		
		classSelectDiv.insertAdjacentHTML("afterend", divHTML);
	}
</script>

<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
<title>Dungeon Notes</title>
</head>
<body class="has-background-black" style="min-height: 100vh;">
	<nav class="navbar has-background-black">
		<div class="navbar-start">
			<div class="navbar-item">
				<span class="is-size-1 has-text-danger-dark">
					Dungeon Notes
				</span>
			</div>
		</div>
		<div class="navbar-end">
			<div class="navbar-item" style="right:50px">
				<span class="icon-text" onClick="avatarButtonClick()">
					<span class="icon is-large"><ion-icon size="large" name="person"/></span>
					<span id="username" class="has-text-warning"><c:out value="${user.userName}"/></span>
				</span>
			</div>
		</div>
		<div id="profile-window" class="has-background-grey-light is-hidden" style="position: absolute; right: 40px; top: 50px; padding: 10px;">
			<ul>
				<li><a href="/homepage">Homepage</a></li>
				<li><a href="/user/${user.id}/worlds">Your Worlds</a></li>
				<li><a href="/user/${user.id}/characters">Your Characters</a></li>
				<li><a href="/logout">Logout</a></li>
			</ul>
		</div>
	</nav>
	<div class="columns is-centered has-background-grey-dark" style="min-height: 100vh;">
		<div class="column is-4 mt-6">
			<h3 class="title has-text-centered has-text-danger">Add a Character</h3>
			<form:form action="/character/add" method="POST" modelAttribute="newCharacter">
			
				<form:input path="creator" type="hidden" value="${user.id}"/>
			
				<div class="field">	
					<form:errors class="has-text-grey-light" path="name"/>
					<form:label class="label has-text-grey-light" path="name">Name: </form:label>
					<form:input class="input" path="name" type="text"/>
				</div>
				<div class="field">
					<form:errors class="has-text-grey-light" path="description"/>
					<form:label class="label has-text-grey-light" path="description">Description: </form:label>
					<form:input class="input" path="description" type="text"/>
				</div>
				<div class="field">
					<form:errors class="has-text-grey-light" path="race"/>
					<form:label class="label has-text-grey-light" path="race">Race: </form:label>
					<form:input class="input" path="race" type="text"/>
				</div>
				
				<div class="field class-select-1">
					<select class="select" name="dnd-class-1">
						<c:forEach var="dnd_class" items="${classList}">
							<option value="${dnd_class.id}"><c:out value="${dnd_class.name}"/></option>
						</c:forEach>
					</select>
					<label for="class-levels-1">Levels: </label>
					<input name="class-levels-1" type="number" id="class-levels-1" min=1 max=20/>
				</div>
				
				
				<input class="button is-dark has-text-warning is-pulled-right" type="submit" value="Create Character"/>
			</form:form>
			
			<button class="button is-dark has-text-danger" onClick="onAddClassBtnClick()">Add another Class</button>
		</div>
	</div> 
</body>
</html>