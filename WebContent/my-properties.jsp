<%@ taglib prefix="q" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<title>My Properties</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>
	 <%@include file="owners-navigation.jsp" %>

	<h3>My Properties</h3>

	<!-- conditional display field -->
	<%
		if (user_type == "2") {
	%>
	<form name="command">
		<input type="button" value="Add Owner"
			onclick="window.location.href='add-owner-form.jsp'; return false;"
			class="add-student-button">
	</form>

	<%
		}
	%>

	<form name="myproperties" action="propertyController" method="GET">
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
						<th>View Request</th>
					</tr>
					<q:forEach var="myProperty" items="${MY_LIST}">

						<q:url var="tempLink" value="propertyController">
							<q:param name="command" value="LOAD" />
							<q:param name="property_id" value="${tempProperty.property_id}" />
						</q:url>
						<tr>
							<td>${myProperty.property_type}</td>
							<td>${myProperty.address}</td>
							<td>${myProperty.suitable_for}</td>
							<td>${myProperty.is_available}</td>
							<td>${myProperty.owner}</td>
							<td>${myProperty.rented_by}</td>
							<td>${myProperty.charge}</td>
							<td> 
								<a href="${tempLink}">View</a>	
							</td>	
						</tr>
					</q:forEach>
				</table>
			</div>
		</div>
	</form>
</body>
</html>

