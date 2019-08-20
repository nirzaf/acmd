<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Property Details</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
<link type="text/css" rel="stylesheet" href="css/add-student-style.css">
</head>

<body>

	<%@include file="navigation.jsp"%>

	<div id="container">
		<h3>Update Property Details</h3>

		<form action="propertyController" method="GET">
			<input type="hidden" name="command" value="EDIT" /> <input
				type="hidden" name="property_id" value="${MYPROPERTY.property_id}" />
			<table>
				<tr>
					<th>Description</th>
					<th>Values</th>
				</tr>
				<tbody>
					<tr>
						<td><label>Property Type: </label></td>
						<td><select name="property_type">
								<option selected value="${MYPROPERTY.property_type}">${PROPERTY.property_type}</option>
								<option value="1">Single Room</option>
								<option value="2">Double Room</option>
								<option value="3">Annex</option>
								<option value="4">Studio Flat</option>
								<option value="5">Apartment</option>
								<option value="6">Villa</option>
								<option value="7">Bungalow</option>
								<option value="8">House</option>
						</select></td>
					</tr>

					<tr>
						<td><label>Address: </label></td>
						<td><input type="text" name="address"
							value="${MYPROPERTY.address}"></td>
					</tr>

					<tr>
						<td><label>Suitable For: </label></td>
						<td><input type="number" name="suitable_for"
							value="${MYPROPERTY.suitable_for}" /></td>
					</tr>

					<tr>
						<td><label>Rented By : </label></td>
						<td><input type="number" name="rented_by"
							value="${MYPROPERTY.rented_by}" /></td>
					</tr>

					<tr>
						<td><label>Charge : </label></td>
						<td><input type="number" name="charge"
							value="${MYPROPERTY.charge}" /></td>
						<td><input type="hidden" name="owner_id" value="<%=userId%>" /></td>
					</tr>

					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Update" class="save" onclick="if (!(confirm('Are you sure you want to update this property?'))) return false"/></td>
					</tr>
				</tbody>
			</table>
		</form>
		<div id="container">
		<h3>Student Details</h3>
			<div id="content">
				<table>
					<tr>
						<th>Student Id</th>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Address</th>
						<th>Email</th>
						<th>Telephone</th>
					</tr>
					<c:forEach var="tempStudent" items="${STUDENT_LIST}">
						<tr>
							<td>${tempStudent.student_id}</td>
							<td>${tempStudent.first_name}</td>
							<td>${tempStudent.last_name}</td>
							<td>${tempStudent.address}</td>
							<td>${tempStudent.email}</td>
							<td>${tempStudent.telephone}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>

		<div style="clear: both;"></div>
	</div>
</body>
</html>