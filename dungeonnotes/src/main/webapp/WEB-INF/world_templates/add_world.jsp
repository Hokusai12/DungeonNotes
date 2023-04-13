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
	function avatarButtonClick() {
		var profileWindow = document.getElementById("profile-window");
		profileWindow.classList.toggle("is-hidden");
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
					<span class="icon is-large"><ion-icon size="large" name="person"></ion-icon></span>
					<span id="username" class="has-text-warning"><c:out value="${user.userName}"/></span>
				</span>
			</div>
		</div>
		<div id="profile-window" class="has-background-grey-light is-hidden" style="position: absolute; right: 40px; top: 50px; padding: 10px;">
			<ul>
				<li><a href="/homepage">Homepage</a></li>
				<li><a>Your Characters</a></li>
				<li><a href="/logout">Logout</a></li>
			</ul>
		</div>
	</nav>
	<div class="columns is-centered has-background-grey-dark" style="min-height: 100vh;">
		<div class="column is-3 mt-6">
			<h3 class="title has-text-centered has-text-danger">Add New World</h3>
			<form:form action="/world/add" method="POST" modelAttribute="newWorld">
			
				<form:input type="hidden" path="creator" value="${user.id}"/>
				
				<div class="field">
					<form:errors path="name"/>
					<form:label path="name" class="label has-text-grey-light">World Name: </form:label>
					<div class="control">
						<form:input type="text" path="name" class="input"/>
					</div>
				</div>
				
				<div class="field">
					<form:errors path="description"/>
					<form:label path="description" class="label has-text-grey-light">Description: </form:label>
					<div class="control">
						<form:textarea path="description" cols="30" rows="5" class="textarea"></form:textarea>
					</div>
				</div>
				
				<input type="submit" value="Add World" class="button is-dark has-text-warning is-pulled-right"/>
			</form:form>
		</div>
	</div>
</body>
</html>