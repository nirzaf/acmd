<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Add Property</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
<link type="text/css" rel="stylesheet" href="css/add-student-style.css">
</head>

<body>

	<%@include file="navigation.jsp"%>

	<div id="container">
		<h3>Add Property</h3>

		<form action="propertyController" method="GET">
			<input type="hidden" name="command" value="ADD" />
			<table>
				<tbody>
					<tr>
						<td><label>Types</label></td>
						<td><select name="property_type">
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
						<td><label>Address</label></td>
						<td><input type="text" name="address" /></td>
					</tr>
					<tr>
						<td><label>Suitable For:</label></td>
						<td><input type="text" name="suitable" /></td>
						<td><input type="hidden" name="is_available" value="1" /></td>
						<td><input type="hidden" name="owner_id" value="<%=request.getSession().getAttribute("user_id")%>" /></td>
						<td><input type="hidden" name="rented_by" value="0" /></td>
					</tr>
					<tr>
						<td><label>Charge:</label></td>
						<td><input type="text" name="charge" /></td>
					</tr>
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Save" class="save" /></td>
					</tr>
				</tbody>
			</table>
		</form>
		<div style="clear: both;"></div>
		<p>
			<a href="propertyController?command=MYLIST&owner_id=<%=userId%>">Back to List</a>
		</p>
	</div>
</body>

</html>