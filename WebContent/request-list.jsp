<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<title>View Requests</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>
	 <%@include file="owners-navigation.jsp" %>

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

	<form name="searchForm" action="propertyController" method="GET">
		<input type="text" name="search" class="form-control"
			placeholder="Search Properties">
		<button type="submit" value="search" class="add-student-button">Search</button>
		<div id="container">
			<div id="content">
				<table>
					<tr>
						<th>Request Id</th>
						<th>Requested by</th>
						<th>Requested Property</th>
						<th>Requested Date</th>
						<th>Date of view</th>
						<th>Status</th>
						<th>Action</th>
					</tr>
					<c:forEach var="Request" items="${REQ_LIST}">

						<c:url var="tempLink" value="viewRequestController">
							<c:param name="command" value="LIST" />
							<c:param name="Id" value="${tempRequest.Request_id}" />
						</c:url>
						<tr>
							<td>${Request.request_id}</td>
							<td>${Request.requested_by}</td>
							<td>${Request.requested_property}</td>
							<td>${Request.requested_date}</td>
							<td>${Request.date_of_view}</td>
							<td>${(Request.status)?'Approved':'Not Approved'}</td>
							<td><a href="${tempLink}">Approve Now</a></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</form>
</body>
</html>

