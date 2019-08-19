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

	<%@include file="owners-navigation.jsp"%>

	<div id="container">
		<h3>Property Details</h3>

		<form action="propertyController" method="GET">
			<input type="hidden" name="command" value="EDIT" /> 
			<input type="hidden" name="property_id" value="${MYPROPERTY.property_id}" />			
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
						<td><input type="text" name="suitable_for"
							value="${MYPROPERTY.suitable_for}" /></td>
					</tr>

					<tr>
						<td><label>Rented By : </label></td>
						<td><input type="text" name="rented_by" value="${MYPROPERTY.rented_by}"/></td>
						<td><select >
								<c:forEach items="${STUDENT_LIST}" var="list">
									<option value="${list.student_id}">Student Id = ${list.student_id}, Student Name = ${list.first_name}</option>
								</c:forEach>
						</select></td>
					</tr>

					<tr>
						<td><label>Charge : </label></td>
						<td><input type="text" name="charge"
							value="${MYPROPERTY.charge}" /></td>
							<td><input type="hidden" name="owner_id" value="<%=userId %>" /></td>
					</tr>
					
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Update" class="save" /></td>
					</tr>
				</tbody>
			</table>
		</form>

		<div style="clear: both;"></div>
	</div>
</body>
</html>