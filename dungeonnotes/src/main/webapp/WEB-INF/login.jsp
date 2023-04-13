<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css">
<title>Dungeon Notes</title>
</head>
<body class="has-background-grey-dark" style="min-height: 100vh;">	
	<div class="has-text-centered p-6">
		<h1 class="has-text-danger-dark is-family-sans-serif" style="font-size: 48px;">Welcome to Dungeon Notes!</h1>
	</div>
	<div class="columns is-centered">
		<div class="column is-3">
			<h3 class="title has-text-centered has-text-grey-light">Login User</h3>
			<form:form action="/login" method="POST" modelAttribute="newLogin">
				<div class="field">
					<form:errors class="has-text-danger" path="email"/>
					<form:label path="email" class="label has-text-grey-light">Email</form:label>
					<div class="control">
						<form:input path="email" type="email" class="input"/>
					</div>
				</div>
				<div class="field">
					<form:label path="password" class="label has-text-grey-light">Password</form:label>
					<div class="control">
						<form:input path="password" type="password" class="input"/>
					</div>
				</div>
				<a href="/register">Register New Account</a>
				<input class="button is-dark has-text-warning is-pulled-right" type="submit" value="Login"/>
			</form:form>
		</div>
	</div>
</body>
</html>