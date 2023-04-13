<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="true"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dungeon Notes</title>
</head>
<body>
	<form:form action="/character/add" method="POST" modelAttribute="newCharacter">
	
		<form:input path="creator" type="hidden" value="${user.id}"/>
	
		<form:label path="name">Name: </form:label>
		<form:input path="name" type="text"/>
		<form:label path="description">Description: </form:label>
		<form:input path="description" type="text"/>
		<form:label path="race">Race: </form:label>
		<form:input path="race" type="text"/>
		
		<input type="submit" value="Create Character"/>
	</form:form>
</body>
</html>