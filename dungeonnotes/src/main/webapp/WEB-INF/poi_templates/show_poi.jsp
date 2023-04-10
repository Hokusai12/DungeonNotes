<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dungeon Notes</title>
</head>
<body>
	<h1><c:out value="${poi.name}"/></h1>
	<p><c:out value="${poi.description}"/></p>
	
	<c:if test="${poi.hostWorld.creator.id == userId}">
		<a href="/poi/${poi.id}/edit"><button>Edit</button></a>
		<form action="/poi/${poi.id}/delete" method="POST">
			<input type="hidden" name="_method" value="DELETE"/>
			<input type="submit" value="Delete"/>	
		</form>
	</c:if>
	
	<a href="/world/${worldId}">Go back</a>
</body>
</html>