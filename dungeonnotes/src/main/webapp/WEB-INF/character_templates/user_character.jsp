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
				<span class="icon-text" id="avatar-username">
					<span class="icon is-large"><ion-icon size="large" name="person"></ion-icon></span>
					<span id="username" class="has-text-warning"><c:out value="${user.username}"/></span>
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
		<div class="column is-8 mt-6">
			<div class="is-flex is-justify-content-space-between is-align-items-center">
				<p class="title has-text-danger">Your Characters</p>
				<a href="/character/add">Add New Character</a>
			</div>
			<c:forEach var="character" items="${characterList}">
				<div class="card has-background-grey-light mb-3">
					<div class="card-header p-3 has-background-white is-flex is-justify-content-space-between">
						<h3 class="is-size-5"><c:out value="${character.name}"/></h3>
						<h3 class="is-size-5">Race: <c:out value="${character.race}"/></h3>
					</div>
					<div class="card-content is-flex is-justify-content-space-around is-flex-direction-column">
						<div class="is-flex">
							<c:forEach var="classLevel" items="${character.classLevels}">
								<p class="mr-3"><c:out value="${classLevel.dndClass.name} Lvl"/>: <c:out value="${classLevel.levels}"/></p>
							</c:forEach>
						</div>
						<c:out value="${character.description}"/>
						<div>
							<div class="is-flex is-pulled-right">
								<form action="/character/${character.id}/delete" method="POST" class="delete-form" id="character-delete">
									<input type="hidden"
										name="${_csrf.parameterName}"
										value="${_csrf.token}"/>
									<input type="hidden" name="_method" value="DELETE"/>
									<input type="submit" value="Delete" class="button has-background-danger has-text-white"/>
								</form>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	<script type="text/javascript" src="<c:url value="/js/events.js"/>"></script>
</body>
</html>