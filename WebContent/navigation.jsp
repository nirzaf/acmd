<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<link type="text/css" rel="stylesheet" href="css/style.css">
<script type="text/javaScript"> function disableBackButton(){window.history.forward();}setTimeout("disableBackButton()", 0); </script>
<%
	// This scriplet function will validate the logged user type
	int userId = 0;
	int user_type = 0;
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("user_id"))
				userId = Integer.parseInt(cookie.getValue());
			if (cookie.getName().equals("user_type"))
				user_type = Integer.parseInt(cookie.getValue());
		}
	}
	if (userId == 0)
		response.sendRedirect("userAccountControllerServlet");
%>
<div class="topnav">
	<div id="header">
		<h2 style="text-align: center; background-color: #333;">XYZ University</h2>
	</div>
</div>

<div class="topnav">
	<%
		if (user_type == 3) {
	%>
	<a href="propertyController">Properties</a> 
	<a href="userAccountControllerServlet?command=LIST">User accounts </a>
	<a href="studentControllerServlet?command=LIST">List of Students</a> 
	<a href="ownerControllerServlet?command=LIST">List of Owners</a>
	<a href="userAccountControllerServlet?command=CHANGEA">Change Password</a>  
	<a href="userAccountControllerServlet?command=LOGOUT">Logout</a>
	<%
		} else if (user_type == 2) {
	%>
	<a href="ownerControllerServlet?command=PROFILE&owner_id=<%=userId%>"> My Profile </a>
	<a href="propertyController">All Properties </a>  
	<a href="propertyController?command=MYLIST&owner_id=<%=userId%>">My Properties </a> 
	<a href="viewRequestController?command=LIST&owner_id=<%=userId%>">View Request</a> 
	<a href="userAccountControllerServlet?command=CHANGEO">Change Password</a> 
	<a href="userAccountControllerServlet?command=LOGOUT">Logout</a>
	<%
		} else if (user_type == 1) {
	%>
	<a href="studentControllerServlet?command=PROFILE&student_id=<%=userId%>">My Profile </a> 
	<a href="propertyController">Properties</a> 
	<a href="viewRequestController?command=MYLIST&student_id=<%=userId%>">My View Requests </a> 
	<a href="userAccountControllerServlet?command=CHANGE">Change Password</a> 
	<a href="userAccountControllerServlet?command=LOGOUT">Logout</a>
	<%
		}
	%>
</div>