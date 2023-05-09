<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
				<li><a href="/user/${user.id}/world">Your Worlds</a></li>
				<li><a href="/user/${user.id}/characters">Your Characters</a></li>
				<li><a href="/logout">Logout</a></li>
			</ul>
		</div>
	</nav>
	<div class="columns is-centered has-background-grey-dark" style="min-height: 100vh;">
		<div class="column is-3 mt-6">
			<h3 class="title has-text-centered has-text-danger">Edit Point of Interest</h3>
			<form:form action="/poi/${poi.id}/update" method="POST" modelAttribute="poi">
				<input type="hidden" name="_method" value="PUT"/>
				<form:input path="hostWorld" type="hidden" value="${worldId}"/>
			
				<div class="field">
					<form:errors path="name"/>
					<form:label class="label has-text-grey-light" path="name">Name: </form:label>
					<form:input class="input" path="name" type="text"/>
				</div>
				
				<div class="field">
					<form:errors path="description"/>
					<form:label class="label has-text-grey-light" path="description">Description: </form:label>
					<form:textarea class="textarea" path="description" cols="15" rows="2"></form:textarea>
				</div>
				
				<input type="submit" value="Save Changes"/>
			</form:form>
		</div>
	</div>
	<script type="text/javascript" src="<c:url value="/js/events.js"/>"></script>
</body>
</html>