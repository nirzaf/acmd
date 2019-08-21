<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>

<head>
<title>Students List</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
<script src="js/exceljs.js"></script>
</head>

<body>

	<%@include file="navigation.jsp"%>
	<%
	 response.setHeader("Cache-Control", "no-cache");
	 response.setHeader("Pragma", "no-cache");
	 response.setHeader("Expires" ,"0");
	%>
	<div id="container">
		<div id="content">
			<table id="tblData">
				<tr>
					<th>Student Id</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Address</th>
					<th>Email</th>
					<th>Telephone</th>
					<th>Action<>
				</tr>
				<c:forEach var="tempStudent" items="${STUDENT_LIST}">
					<!-- set up a link for each student -->
					<c:url var="tempLink" value="studentControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="student_id" value="${tempStudent.student_id}" />
					</c:url>

					<!--  set up a link to delete a student -->
					<c:url var="deleteLink" value="studentControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="student_id" value="${tempStudent.student_id}" />
					</c:url>
					<tr>
						<td>${tempStudent.student_id}</td>
						<td>${tempStudent.first_name}</td>
						<td>${tempStudent.last_name}</td>
						<td>${tempStudent.address}</td>
						<td>${tempStudent.email}</td>
						<td>${tempStudent.telephone}</td>
						<td><a href="${tempLink}">Update</a> | 
						<a href="${deleteLink}" onclick="if (!(confirm('Are you sure you want to delete this student?'))) return false">Delete</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<button onclick="exportTableToExcel('tblData', 'list-students')">Export Table Data To Excel File</button>
	</div>
</body></html>

