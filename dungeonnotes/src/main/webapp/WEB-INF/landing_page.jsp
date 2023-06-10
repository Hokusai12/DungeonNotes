<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css">
<link rel="stylesheet" href="/css/style.css"/>
<title>Dungeon Notes</title>
</head>
<body>
	<div id="background-div">
		<nav id="main-navbar" class="navbar p-3">
				<div class="navbar-brand">
					<img width="200px" src="<c:url value="imgs/logo.png"/>"/>
				</div>
			<div class="navbar-end">
				<div class="navbar-item">		
					<a href="/register"><button class="button">Register</button></a>
				</div>
				<div class="navbar-item">		
					<a href="/login"><button  class="button" >Login</button></a>
				</div>
			</div>
		</nav>
		
		<div class="container">
			<div class="content">
				<h3 class="has-font-empire">Create Worlds</h3>
				<p>
					Bring your ideas to life with our easy note-keeping and document storing system.
				</p>
				<h3 class="has-font-empire">Track Campaigns</h3>
				<p>
					Keep a track on your game or play through your game with our built-in tools.
				</p>
				<h3 class="has-font-empire">
					Draw Maps
				</h3>
				<p>
					Whether for your whole world or just a few dungeons, you can get artsy here.
				</p>
			</div>
		</div>
		<footer class="footer">
			<a href="#">About</a>
			<a href="#">Explore</a>
			<a href="#">Homepage</a>
		</footer>
	</div>
</body>
</html>