<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="true"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
			<h3 class="title has-text-centered has-text-grey-light">Register New User</h3>
			<form:form action="/register" method="POST" modelAttribute="newUser">
				<div class="field">
					<form:errors class="has-text-danger" path="username"/>
					<form:label path="username" class="label has-text-grey-light">User Name</form:label>
					<div class="control">
						<form:input class="input" path="username" type="text"/>
					</div>
				</div>
				<div class="field">
					<form:errors class="has-text-danger" path="email"/>
					<form:label path="email" class="label has-text-grey-light">Email</form:label>
					<div class="control">
					 	<form:input path="email" type="email" class="input"/>
					</div>
				</div>
				<div class="field">
					<form:errors class="has-text-danger" path="password"/>
					<form:label path="password" class="label has-text-grey-light">Password</form:label>
					<div class="control">				
						<form:input path="password" type="password" class="input"/>
					</div>
				</div>
				<div class="field">
					<form:errors class="has-text-danger" path="confirm"/>
					<form:label path="confirm" class="label has-text-grey-light">Confirm Password</form:label>
					<div class="control">					
						<form:input path="confirm" type="password" class="input"/>
					</div>
				</div>
				<a href="/login">Already a User? Login</a>
				<input class="button is-dark has-text-warning is-pulled-right" type="submit" value="Register"/>
			</form:form>
			</div>
	</div>
</body>
</html>