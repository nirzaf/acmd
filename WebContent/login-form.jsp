<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
<title>Welcome to XYZ University's Accommodation Management Web
	Application</title>

<link type="text/css" rel="stylesheet" href="css/style.css">
<link type="text/css" rel="stylesheet" href="css/add-student-style.css">
</head>

<body>

<%
	 response.setHeader("Cache-Control", "no-cache");
	 response.setHeader("Pragma", "no-cache");
	 Cookie userIdCookie = new Cookie("user_id", null);
	 Cookie userTypeCookie = new Cookie("user_type", null);
	 userIdCookie.setMaxAge(0);
	 userTypeCookie.setMaxAge(0);
	 userIdCookie.setPath("/");
	 userTypeCookie.setPath("/");
     response.addCookie(userIdCookie);
     response.addCookie(userTypeCookie);
%>

	<div id="wrapper">
		<div id="header">
			<h2>XYZ University</h2>
		</div>
	</div>
	<h5 style="color: red"><%=(request.getAttribute("errMessage") == null) ? "" : "Successfully account registered"%></h5>

	<h5 style="color: red">${Message}</h5>

	<div id="container">
		<h3>Login Now</h3>
		<h5 style="color: red">Please login to your Account</h5>

		<form action="userAccountControllerServlet" method="POST">
			<input type="hidden" name="command" value="LOGIN" />
			<table>
				<tbody>
					<tr>
						<td><label>Username:</label></td>
						<td><input type="text" name="username" placeholder="enter username here" required/></td>				
					</tr>
					
					<tr>
						<td><label>Password:</label></td>
						<td><input type="password" name="password"  placeholder="enter password here" required/></td>
					</tr>
					
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Sign-in" /></td>
					</tr>
					
					<tr>
						<td><label></label></td>
						<td><label></label></td>
					</tr>
					<tr>
						<td><label></label></td>
						<td><label></label></td>
					</tr>

					<tr>
						<td><label></label></td>
						<td><input type="button" value="Sign-up" onclick="location.href='userAccountControllerServlet?command=SIGNUP';"/></td>
					</tr>
					<tr></tr>
				</tbody>
			</table>
		</form>
	</div>
</body>

</html>