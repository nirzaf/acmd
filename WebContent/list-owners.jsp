<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>

<head>
<title>Owners List</title>
<link type="text/css" rel="stylesheet" href="css/style.css">

</head>

<body>
	<%@include file="navigation.jsp"%>

	<div id="container">
		<div id="content">
			<table>
				<tr>
					<th>Owner Id</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Address</th>
					<th>Email</th>
					<th>Telephone</th>
					<th>Action<>
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
						<td>${tempOwner.owner_id}</td>
						<td>${tempOwner.first_name}</td>
						<td>${tempOwner.last_name}</td>
						<td>${tempOwner.address}</td>
						<td>${tempOwner.email}</td>
						<td>${tempOwner.telephone}</td>
						<td><a href="${tempLink}">Update</a> | <a href="${deleteLink}"
							onclick="if (!(confirm('Are you sure you want to delete this owner?'))) return false">Delete</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>

