<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
<title> Users List </title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>

<div id="wrapper">
		<div id="header">
			<h2 align="center">XYZ University</h2>
		</div>
	</div>
		
	<div id="container">
		
		<div id="content">
			<table>
				<tr>
					<th>User Id</th>
					<th>Username</th>
					<th>Password</th>
					<th>User Type</th>
					<th>Action</th>
				</tr>	
				<c:forEach var="tempUser" items="${USER_LIST}">
					
					<!-- set up a link for each student -->
					<c:url var="tempLink" value="userAccountControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="user_id" value="${tempUser.user_id}" />
					</c:url>
					
					<!--  set up a link to delete a student -->
					<c:url var="deleteLink" value="studentControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="user_id" value="${tempUser.user_id}" />
					</c:url>
				
					<tr>
						<td> ${tempUser.user_id} </td>
						<td> ${tempUser.username} </td>
						<td> ${tempUser.password} </td>	
						<td> ${(tempUser.user_type)==1?'Owner':'Student'} </td>
						<td> 
							<a href="${tempLink}">Update</a> | <a href="${deleteLink}" onclick="if (!(confirm('Are you sure you want to delete this User?'))) return false">Delete</a>	
						</td>			
					</tr>
				</c:forEach>
			</table>
		</div>	
	</div>
</body>
</html>

