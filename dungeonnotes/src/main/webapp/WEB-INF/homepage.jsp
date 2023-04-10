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
	
	<button onClick="avatarButtonClick()">Avatar Button</button>
	
	<div id="profile-window" style="position: absolute;"></div><br>
	<a href="/logout">Logout</a>

	<table>
		<thead>
			<tr>
				<th>World</th>
				<th>Creator</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="world" items="${worldList}">
				<tr>
					<td><a href="/world/${world.id}"><c:out value="${world.name}"/></a></td>
					<td><c:out value="${world.creator.userName}"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<a href="/world/add">Add a world</a>
</body>


<script>
	function avatarButtonClick() {
		var profileWindow = document.getElementById("profile-window");
		
		if(profileWindow.innerHTML == ""){
			profileWindow.innerHTML = 
			`
			<ul>
				<li style="color: white;"><a href="/user/${userId}/worlds">Your Worlds</a></li>
				<li><a>Your Characters</a></li>
			</ul>
			`;
			
			profileWindow.style.backgroundColor = "rgba(150, 150, 150, .9)";
			profileWindow.style.padding = "10px";
		} else {
			profileWindow.innerHTML = "";
			profileWindow.style = "";
			profileWindow.style.position = "absolute";
		}
	}
</script>
</html>