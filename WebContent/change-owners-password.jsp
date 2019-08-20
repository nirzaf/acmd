<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Welcome to XYZ University's Accommodation Management Web Application</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
<link type="text/css" rel="stylesheet" href="css/add-student-style.css">
</head>
<body>
	<%@include file="navigation.jsp" %>
	
	<div id="container">
		<h3>Sign-Up Now</h3>
		<h5 style="color:red">Please enter new password</h5>

		<form action="userAccountControllerServlet" method="GET">
			<input type="hidden" name="command" value="CHANGE_PW" />
			<table>
				<tbody>
					<tr>
						<td><input type="hidden" name="user_id" value="<%= userId %>" required></td>
					</tr>
					<tr>
						<td><input id="password" name="password" type="password" pattern="^\S{4,}$" onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Must have at least 6 characters' : ''); if(this.checkValidity()) form.password_two.pattern = this.value;" placeholder="Password" required></td>
					</tr>

					<tr>
						<td><input id="password_two" name="confirm_password" type="password" pattern="^\S{4,}$" onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Please enter the same Password as above' : '');" placeholder="Verify Password" required></td>
					</tr>
						
					<tr>
						<td><input type="submit" value="Change-Password"/> </td>
					</tr>									        
				</tbody>		
			</table>
		</form>

		<div style="clear: both;"></div>
		<h5 style="color:red">${SUCCESS}</h5>
	</div>
</body>

</html>