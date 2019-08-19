<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Property Details</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
<link type="text/css" rel="stylesheet" href="css/add-student-style.css">
</head>

<body>

	<%@include file="owners-navigation.jsp"%>

	<div id="container">
		<h3>Property Details</h3>

		<form action="propertyController" method="GET">

			<input type="hidden" name="command" value="VIEW" /> 
			<input type="hidden" name="owner_id" value="${PROPERTY.property_id}" />

			<table>
				<tbody>
					<tr>					
						<td><label>Property Type: </label></td>
						<td><label> ${PROPERTY.property_type} </label></td>
						<td><input type="hidden" name="property_id" value="${PROPERTY.property_id}" /></td>
					</tr>

					<tr>
						<td><label>Address: </label></td>
						<td><label>${PROPERTY.address}</label></td>
					</tr>

					<tr>
						<td><label>Suitable For: </label></td>
						<td><label>${PROPERTY.suitable_for}</label></td>
					</tr>

					<tr>
						<td><label>Availability : </label></td>
						<td><label>${PROPERTY.is_available}</label></td>
					</tr>

					<tr>
						<td><label>Owner: </label></td>
						<td><label>${PROPERTY.owner}</label></td>
					</tr>

					<tr>
						<td><label>Rented By : </label></td>
						<td><label>${PROPERTY.rented_by}</label></td>
						<td><input type="hidden" name="user_id" value="<%=request.getSession().getAttribute("user_id")%>" /></td>
					</tr>

					<tr>
						<td><label>Charge : </label></td>
						<td><label>${PROPERTY.charge}</label></td>
					</tr>

					<tr>
						<td><label>Date of View:</label></td>
						<td><input type="date" name="date_of_view" id="today" /></td>
					</tr>
					
					<tr>
						<td><input type="submit" value="Send Request" class="save" /></td>
					</tr>
				</tbody>
			</table>
		</form>

		<div style="clear: both;"></div>

		<p>
			<a href="propertyController">Back to List</a>
		</p>
	</div>
</body>
</html>