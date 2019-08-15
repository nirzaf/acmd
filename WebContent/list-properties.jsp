<%@ taglib prefix="q" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
<title> Properties </title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>

	<%@include file="student-navigation.jsp" %>
	
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

<form name="command">
	<input type="button" value="Add Property" onclick="window.location.href='add-property.jsp';return false;" 
	class="add-student-button"> 	
</form>
	
<form name="searchForm" action="propertyController" method="GET">
<input type="text" name="search" class="form-control" placeholder="Search Properties">
<button type="submit" value="search" class="add-student-button">Search</button>
	<div id="container">		
		<div id="content">
			<table>
				<tr>
					<th>Property Type</th>
					<th>Address</th>
					<th>Suitable For</th>
					<th>Availability</th>
					<th>Owner</th>
					<th>Currently Rented By</th>
					<th>Charge</th>
				</tr>	
				<q:forEach var="tempProperty"  items="${PROPERTY_LIST}" >				
					<tr>
						<td> ${tempProperty.property_type} </td>
						<td> ${tempProperty.address} </td>
						<td> ${tempProperty.suitable_for} </td>	
						<td> ${tempProperty.is_available} </td>
						<td> ${tempProperty.owner} </td>	
						<td> ${tempProperty.rented_by} </td>
						<td> ${tempProperty.charge} </td>		
					</tr>
				</q:forEach>
			</table>
		</div>	
	</div>
	</form>
</body>
</html>

