<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dungeon Notes</title>
</head>
<body>
	<form:form action="/poi/add" method="POST" modelAttribute="newPOI">
		<form:input path="hostWorld" type="hidden" value="${worldId}"/>
	
		<form:errors path="name"/>
		<form:label path="name">Name: </form:label>
		<form:input path="name" type="text"/>
		
		<form:errors path="description"/>
		<form:label path="description">Description: </form:label>
		<form:textarea path="description" cols="15" rows="2"></form:textarea>
		
		<input type="submit" value="Create POI"/>
	</form:form>
</body>
</html>