<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="_csrf" content="${_csrf.token}"/>
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>
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
			<c:if test="${param['error'] != null}">
				<p class="has-text-danger">Invalid Login</p>
			</c:if>
			<c:if test="${param['logout'] != null}">
				<p class="has-text-sucess">Logout successful</p>
			</c:if>
			<form action="/login" method="POST">
				<input type="hidden"
					name="${_csrf.parameterName}"
					value="${_csrf.token}"/>
				<div class="field">
					<label for="username" class="label has-text-grey-light">Email</label>
					<div class="control">
						<input name="username" type="text" class="input"/>
					</div>
				</div>
				<div class="field">
					<label for="password" class="label has-text-grey-light">Password</label>
					<div class="control">
						<input name="password" type="password" class="input"/>
					</div>
				</div>
				<input class="button is-dark has-text-warning is-pulled-right" type="submit" value="Login"/>
			</form>
			<a href="/register">Register New Account</a>
		</div>
	</div>
</body>
</html>