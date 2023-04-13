<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
	
	
	function onDeleteButtonClick() {
		const response = confirm(`You are about to delete one of your worlds.
			This action can't be undone. Continue?`, );
		if(response == false) {
			event.preventDefault();
		}
		return response;
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
				<li><a>Your Characters</a></li>
				<li><a href="/logout">Logout</a></li>
			</ul>
		</div>
	</nav>
	<div class="columns is-centered pt-5 has-background-grey-dark" style="min-height: 100vh;">
		<div class="column is-8">
			<div class="card has-background-grey-light">
				<div class="card-header p-3 has-background-white is-flex is-justify-content-space-between is-align-items-center">
					<h3 class="is-size-2 has-text-danger">${world.name}</h3>
					<h3 class="is-size-5 has-text-grey">Created By: <span class="has-text-danger"><c:out value="${world.creator.userName}"/></span></h3>
				</div>
				<div class="card-content ml-6 p-6">
					<p class="is-size-5 my-5 has-text-black">${world.description}</p>
					
					<div>
						<h3 class="is-size-4 has-text-danger is-inline">Points of Interest</h3>
						<c:if test="${world.creator.id == userId}">
							<a class="is-pulled-right" href="/poi/add">Add a Point of Interest</a> <br>
						</c:if>
						<c:forEach var="poi" items="${world.poiList}">
							<div>
								<a class="ml-5" href="/poi/${poi.id}">${poi.name}</a>
							</div>
						</c:forEach>
					</div>
					
	 				<c:if test="${world.creator.id == userId}">
	 					<div class="is-flex is-pulled-right">
							<a href="/world/${world.id}/edit" class="button has-background-warning has-text-black mr-2">Edit</a>
							<form:form action="/world/${world.id}/delete" method="POST" onSubmit="onDeleteButtonClick();">
								<input type="hidden" name="_method" value="DELETE"/>
								<input type="submit" value="Delete" class="button has-background-danger has-text-white"/>
							</form:form>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</body>
</html>