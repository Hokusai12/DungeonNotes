<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
					<span class="icon is-large"><ion-icon size="large" name="person"></ion-icon></span>
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
	<div class="columns has-background-grey-dark is-centered" style="min-height: 100vh;">
		<div class="column is-6 m-6">
			<div class="card has-background-grey-light">
				<div class="card-header p-3 has-background-white">
					<h1 class="is-size-3 has-text-danger"><c:out value="${poi.name}"/></h1>
				</div>
				<div class="card-content">
					<p class="is-size-5 mb-3"><c:out value="${poi.description}"/></p>
					
					<div class="controls is-flex is-justify-content-space-between">						
						<a href="/world/${worldId}">Go back</a>
							<c:if test="${poi.hostWorld.creator.id == userId}">
								<div class="creator-controls is-flex">
									<a class="button is-warning mr-2" href="/poi/${poi.id}/edit">Edit</a>
									<form action="/poi/${poi.id}/delete" method="POST">
										<input type="hidden" name="_method" value="DELETE"/>
										<input class="button is-danger" type="submit" value="Delete"/>	
									</form>
								</div>
							</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>