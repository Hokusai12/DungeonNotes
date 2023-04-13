<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dungeon Notes</title>
</head>
<body>
	<form:form action="/character/add-class" method="POST" modelAttribute="newClassLevel">
		<form:input path="character" type="hidden" value="${newCharacterId}"/> 	
		
		<form:select path="dndClass">
			<c:forEach var="dnd_class" items="${classList}">
				<option value="${dnd_class.id}"><c:out value="${dnd_class.name}"/></option>
			</c:forEach>
		</form:select>
		<form:label path="levels">Levels: </form:label>
		<form:input path="levels" type="number"/>
		<input type="submit" value="Save Classes"/>
	</form:form>
</body>
</html>