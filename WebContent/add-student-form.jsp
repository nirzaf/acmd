<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Add Student</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
<link type="text/css" rel="stylesheet" href="css/add-student-style.css">
</head>

<body>

	<%@include file="navigation.jsp" %>

	<div id="container">
		<h3>Add Student</h3>

		<form action="studentControllerServlet" method="GET">

			<input type="hidden" name="command" value="ADD" />

			<table>
				<tbody>
					<tr>
						<td><label>First name:</label></td>
						<td><input type="text" name="firstName" /></td>
					</tr>

					<tr>
						<td><label>Last name:</label></td>
						<td><input type="text" name="lastName" /></td>
					</tr>

					<tr>
						<td><label>Address:</label></td>
						<td><input type="text" name="address" /></td>
					</tr>

					<tr>
						<td><label>Email:</label></td>
						<td><input type="text" name="email" /></td>
					</tr>

					<tr>
						<td><label>Telephone:</label></td>
						<td><input type="text" name="telephone" /></td>
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