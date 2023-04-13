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
	<c:forEach var="character" items="${characterList}">
		<h3><c:out value="${character.name}"/></h3>
		<ul>
			<li><c:out value="${character.race}"/></li>
			<li><c:out value="${character.description}"/></li>
			<li>
				<ul>
					<c:forEach var="classLevel" items="${character.classLevels}">
						<li><c:out value="${classLevel.dndClass.name}"/>: <c:out value="${classLevel.levels}"/></li>
					</c:forEach>
				</ul>
			</li>
		</ul>
	</c:forEach>
	
	<a href="/homepage">Back to homepage</a>
</body>
</html>