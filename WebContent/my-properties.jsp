<%@ taglib prefix="q" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<title>Properties</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>
	<%@include file="owners-navigation.jsp"%>

	<!-- conditional display field -->
	<form name="command">
		<input type="button" value="Add Property"
			onclick="window.location.href='add-property.jsp'; return false;"
			class="add-student-button">
	</form>

	<form name="searchForm" action="propertyController" method="GET">
		<!-- 		<input type="text" name="search" class="form-control"
			placeholder="Search Properties">
		<button type="submit" value="search" class="add-student-button">Search</button> -->
		<div id="container">
			<div id="content">
				<table>
					<tr>
						<th>Property Type</th>
						<th>Address</th>
						<th>Suitable For</th>
						<th>Availability</th>
						<th>Currently Rented By</th>
						<th>Charge</th>
						<th>Payment</th>
					</tr>
					<q:forEach var="tempProperty" items="${PROPERTY}">

						<q:url var="tempLink" value="propertyController">
							<q:param name="command" value="PAY" />
							<q:param name="property_id" value="${tempProperty.property_id}" />
							<q:param name="owner_id" value="${tempProperty.owner_id}" />
						</q:url>
						<tr>
							<td>${tempProperty.property_type}</td>
							<td>${tempProperty.address}</td>
							<td>${tempProperty.suitable_for}</td>
							<td>${tempProperty.is_available}</td>
							<td>${tempProperty.rented_by}</td>
							<td>${tempProperty.charge}</td>
							<td><a href="${tempLink}">${(tempProperty.status)?'Paid':'Pay Now'}</a>
							</td>
						</tr>
					</q:forEach>
				</table>
			</div>
		</div>
	</form>
</body>
</html>

