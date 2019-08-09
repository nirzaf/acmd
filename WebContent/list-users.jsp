<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
<title> Users List </title>
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
