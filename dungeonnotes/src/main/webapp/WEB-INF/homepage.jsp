<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<script src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
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
				<li style="color: white;"><a href="/user/${userId}/worlds">Your Worlds</a></li>
				<li><a href="/user/${userId}/characters">Your Characters</a></li>
				<li><a href="/logout">Logout</a></li>
			</ul>
		</div>
	</nav>

	<div class="columns is-centered has-background-grey-dark pt-5" style="min-height: 100vh;">
		<div class="column is-6">
			<p class="title has-text-danger">Recent Worlds</p>
			<div class="column ml-6">
				<c:forEach var="world" items="${worldList}">
					<div class="card has-background-grey-light mb-5">
					<div class="card-header has-background-white p-3 is-flex is-justify-content-space-between is-align-items-center">
						<p class="is-size-4"><a href="/world/${world.id}" class="has-text-danger"><c:out value="${world.name}"/></a></p>
						<p><fmt:formatDate pattern="MMMM dd" type="both" value="${world.createdAt}"/></p>
					</div>
						<p class="card-content"><c:out value="${world.description}"/></p>
						<h3 class="card-footer p-3">Created by: <span class="has-text-danger ml-2"><c:out value="${world.creator.username}"/></span></h3>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="<c:url value="/js/events.js"/>"></script>
</body>
</html>