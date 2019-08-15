<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
<title> Owners List </title>
<link type="text/css" rel="stylesheet" href="css/style.css">

</head>

<body>
	<%@include file="student-navigation.jsp"%>
	
	<%
		// This scriplet function will validate the user logged in or not
		String userId = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("user_id"))
					userId = cookie.getValue();
			}
		}
		if (userId == null)
			response.sendRedirect("userAccountControllerServlet");
	%>
	
	<div id="container">		
		<div id="content">
			<table>
					<!-- <tr> 
						<input type="button" value="Add Owner" onclick="window.location.href='add-owner-form.jsp'; return false;" class="add-student-button">
					</tr> -->
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Address</th>
					<th>Email</th>
					<th>Telephone</th>
					<th>Action</th>
				</tr>	
				<c:forEach var="tempOwner" items="${OWNER_LIST}">
					
					<!-- set up a link for each owner -->
					<c:url var="tempLink" value="ownerControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="owner_id" value="${tempOwner.owner_id}" />
					</c:url>
					
					<!--  set up a link to delete a owner -->
					<c:url var="deleteLink" value="ownerControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="owner_id" value="${tempOwner.owner_id}" />
					</c:url>
				
					<tr>
						<td> ${tempOwner.first_name} </td>
						<td> ${tempOwner.last_name} </td>
						<td> ${tempOwner.address} </td>	
						<td> ${tempOwner.email} </td>
						<td> ${tempOwner.telephone} </td>	
						<td> 
							<a href="${tempLink}">Update</a> | <a href="${deleteLink}" onclick="if (!(confirm('Are you sure you want to delete this owner?'))) return false">Delete</a>	
						</td>			
					</tr>
				</c:forEach>
			</table>
		</div>	
	</div>
</body>
</html>

