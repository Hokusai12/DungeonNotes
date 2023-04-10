<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="true"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dungeon Notes</title>
</head>
<body>
	<form:form style="display:flex;" action="/world/add" method="POST" modelAttribute="newWorld">
	
		<form:input type="hidden" path="creator" value="${userId}"/>
		
		<form:errors path="name"/>
		<form:label path="name">World Name: </form:label>
		<form:input type="text" path="name"/>
		
		<form:errors path="description"/>
		<form:label path="description">Description: </form:label>
		<form:textarea path="description" cols="15" rows="2"></form:textarea>
		
		<input type="submit" value="Add World"/>
	</form:form>
</body>
</html>