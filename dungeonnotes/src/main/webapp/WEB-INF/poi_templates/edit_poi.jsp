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
	<form:form action="/poi/${poi.id}/update" method="POST" modelAttribute="poi">
		<input type="hidden" name="_method" value="PUT"/>
		<form:input path="hostWorld" type="hidden" value="${worldId}"/>
	
		<form:errors path="name"/>
		<form:label path="name">Name: </form:label>
		<form:input path="name" type="text"/>
		
		<form:errors path="description"/>
		<form:label path="description">Description: </form:label>
		<form:textarea path="description" cols="15" rows="2"></form:textarea>
		
		<input type="submit" value="Save Changes"/>
	</form:form>
</body>
</html>