<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dungeon Notes</title>
</head>
<body>
	<a href="/homepage">Go to homepage</a>
	<h1>${world.name}</h1>
	<h3>Created By: <c:out value="${world.creator.userName}"/></h3>
	<p>${world.description}</p>
	
	<div>
		<c:forEach var="poi" items="${world.poiList}">
			<a href="/poi/${poi.id}">${poi.name}</a>
		</c:forEach>
	</div>
	<c:if test="${world.creator.id == userId}">
		<a href="/poi/add">Add a Point of Interest</a> <br>
	</c:if>
	
	<c:if test="${world.creator.id == userId}">
		<a href="/world/${world.id}/edit"><button>Edit</button></a>
		<form:form action="/world/${world.id}/delete" method="POST">
			<input type="hidden" name="_method" value="DELETE"/>
			<input type="submit" value="Delete"/>
		</form:form>
	</c:if>
</body>
</html>