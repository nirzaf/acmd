<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
/* 
	if (request.getAttribute("user_id") == null)
		response.sendRedirect("userAccountControllerServlet?command=LOGOUT"); */
%>

<title>Update Student</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
<link type="text/css" rel="stylesheet" href="css/add-student-style.css">
<style>
body {
	margin: 0;
	font-family: Arial, Helvetica, sans-serif;
}

.topnav {
	overflow: hidden;
}

.topnav a {
	float: left;
	color: #00000;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
	font-size: 17px;
}

.topnav a:hover {
	background-color: #ddd;
	color: black;
}

.topnav a.active {
	background-color: #4CAF50;
	color: white;
}
</style>

</head>

<body>
	<div class="topnav">
		<div id="header">
			<h2 style="text-align: center; background-color: #333;">XYZ
				University</h2>
		</div>
	</div>

	<div>
		<div class="topnav">
			<a class="active" href="#home">My Profile</a> <a href="#news">Properties</a>
			<a href="#contact">Payment</a> <a href="#about">About</a> <a
				href="userAccountControllerServlet?command=LOGOUT">Logout</a>
		</div>
	</div>
	<%
		// This scriplet function will validate the user logged in or not
		String userName = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("user_id"))
					userName = cookie.getValue();
			}
		}
		if (userName == null)
			response.sendRedirect("userAccountControllerServlet");
	%>

	<div id="container">
		<h3>Student Profile</h3>

		<form action="studentControllerServlet" method="GET">

			<input type="hidden" name="command" value="UPDATE" /> <input
				type="hidden" name="student_id" value="${THE_STUDENT.student_id}" />

			<table>
				<tbody>
					<tr>
						<td><label>First name:</label></td>
						<td><input type="text" name="first_name"
							value="${THE_STUDENT.first_name}" /></td>
					</tr>

					<tr>
						<td><label>Last name:</label></td>
						<td><input type="text" name="last_name"
							value="${THE_STUDENT.last_name}" /></td>
					</tr>

					<tr>
						<td><label>Address:</label></td>
						<td><input type="text" name="address"
							value="${THE_STUDENT.address}" /></td>
					</tr>

					<tr>
						<td><label>Email:</label></td>
						<td><input type="text" name="email"
							value="${THE_STUDENT.email}" /></td>
					</tr>

					<tr>
						<td><label>Telephone:</label></td>
						<td><input type="text" name="telephone"
							value="${THE_STUDENT.telephone}" /></td>
					</tr>

					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Save" class="save" /></td>
					</tr>

				</tbody>
			</table>
		</form>

		<div style="clear: both;"></div>

		<p>
			<a href="studentControllerServlet">Back to List</a>
		</p>
	</div>
</body>
</html>