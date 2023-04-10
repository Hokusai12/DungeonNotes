<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dungeon Notes</title>
</head>
<body>
	<table>
		<thead>
			<tr>
				<th>World</th>
				<th>Created On</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="world" items="${userWorldList}">
				<tr>
					<td><a href="/world/${world.id}"><c:out value="${world.name}"/></a></td>
					<td><fmt:formatDate type="date" value="${world.createdAt}"/></td>
				</tr>
			</c:forEach>
		
		</tbody>
	</table>
</body>
</html>