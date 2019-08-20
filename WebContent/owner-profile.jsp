<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>${OWNER.first_name}'s Details</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
<link type="text/css" rel="stylesheet" href="css/add-student-style.css">
</head>

<body>
	<%@include file="navigation.jsp"%>

	<div id="container">
		<h3>${OWNER.first_name}'s Profile</h3>

		<form action="ownerControllerServlet" method="GET">

			<input type="hidden" name="command" value="LOAD" /> 
			<input type="hidden" name="owner_id" value="${OWNER.owner_id}" />

			<table>
				<tbody>
					<tr>					
						<td><label>First Name : </label></td>
						<td><label> ${OWNER.first_name} </label></td>
					</tr>

					<tr>
						<td><label>Last Name : </label></td>
						<td><label>${OWNER.last_name}</label></td>
					</tr>

					<tr>
						<td><label>Address: </label></td>
						<td><label>${OWNER.address}</label></td>
					</tr>

					<tr>
						<td><label>Email : </label></td>
						<td><label>${OWNER.email}</label></td>
					</tr>

					<tr>
						<td><label>Telephone No : </label></td>
						<td><label>${OWNER.telephone}</label></td>
					</tr>
				
					<tr>
						<td><input type="hidden" name="user_id" value="<%=request.getSession().getAttribute("user_id")%>" /></td>
						<td><input type="submit" value="Update Profile" class="save" /></td>
					</tr>
				</tbody>
			</table>
		</form>

		<div style="clear: both;"></div>
	</div>
</body>
</html>