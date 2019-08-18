<%@ taglib prefix="z" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<title>My Requests</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>
	 <%@include file="student-navigation.jsp" %>

	<form name="viewRequest" action="#" method="GET">
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
					<z:forEach var="tempList" items="${MYREQ_LIST}">
						<z:url var="deleteLink" value="studentControllerServlet">
							<z:param name="command" value="DEL" />
							<z:param name="request_id" value="${tempList.request_id}" />
							<z:param name="student_id" value="<%= userId %>" />
						</z:url>
						<tr>
							<td>${tempList.request_id}</td>
							<td>${tempList.requested_by}</td>
							<td>${tempList.requested_property}</td>
							<td>${tempList.requested_date}</td>
							<td>${tempList.date_of_view}</td>
							<td>${(tempList.status)?'Accepted':'Not Accepted'}</td>
							<td><a href="${deleteLink}" onclick="if (!(confirm('Are you sure you want to delete the request?'))) return false">Delete</a></td>
						</tr>
					</z:forEach>
				</table>
			</div>
		</div>
	</form>
</body>
</html>

