<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
<title> Students List </title>
<link type="text/css" rel="stylesheet" href="css/style.css">
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
			<h2 style="text-align:center; background-color: #333;" >XYZ University</h2>
		</div>
	</div>

	<div>
		<div class="topnav">
			<a class="active" href="#home">My Profile</a> 
			<a href="#news">Properties</a> 
			<a href="#contact">Payment</a> 
			<a href="#about">About</a>
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
		
		<div id="content">
			<table>
					<tr> 
						<input type="button" value="Add Student" onclick="window.location.href='add-student-form.jsp'; return false;" class="add-student-button">
					</tr>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Address</th>
					<th>Email</th>
					<th>Telephone</th>
					<th>Action</th>
				</tr>	
				<c:forEach var="tempStudent" items="${STUDENT_LIST}">
					
					<!-- set up a link for each student -->
					<c:url var="tempLink" value="studentControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="student_id" value="${tempStudent.student_id}" />
					</c:url>
					
					<!--  set up a link to delete a student -->
					<c:url var="deleteLink" value="studentControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="student_id" value="${tempStudent.student_id}" />
					</c:url>
				
					<tr>
						<td> ${tempStudent.first_name} </td>
						<td> ${tempStudent.last_name} </td>
						<td> ${tempStudent.address} </td>	
						<td> ${tempStudent.email} </td>
						<td> ${tempStudent.telephone} </td>	
						<td> 
							<a href="${tempLink}">Update</a> | <a href="${deleteLink}" onclick="if (!(confirm('Are you sure you want to delete this student?'))) return false">Delete</a>	
						</td>			
					</tr>
				</c:forEach>
			</table>
		</div>	
	</div>
</body>
</html>

