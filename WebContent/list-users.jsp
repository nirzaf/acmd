<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
<title> Users List </title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>
	
	<%@include file="navigation.jsp"%>
	<div id="container">		
		<div id="content">
			<table>
				<tr>
					<th>User Id</th>
					<th>Username</th>
					<th>Password</th>
					<th>User Type</th>
				    <th>Status</th>
					<th>Action</th>
				</tr>	
				<c:forEach var="tempUser" items="${USER_LIST}">
										
					<!--  set up a link to enable or disable user account -->
					<c:url var="deleteLink" value="userAccountControllerServlet">
						<c:param name="command" value="DISABLE" />
						<c:param name="user_id" value="${tempUser.user_id}" />
					</c:url>
				
					<tr>
						<td> ${tempUser.user_id} </td>
						<td> ${tempUser.username} </td>
						<td> ${tempUser.password} </td>	
						<td> ${(tempUser.user_type)==1?'Owner':'Student'} </td>			
						<td> ${(tempUser.status)?'Enabled':'Disabled'} </td>
						<td> 
							<a href="${deleteLink}" onclick="if (!(confirm('Are you sure you want to  ${!(tempUser.status)?'Enable':'Disable'} this User?'))) return false">${(tempUser.status)?'Disable':'Enable'}</a>	
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>	
	</div>
</body>
</html>
