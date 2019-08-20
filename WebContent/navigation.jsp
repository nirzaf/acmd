<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<link type="text/css" rel="stylesheet" href="css/style.css">
<style>
body {
  margin: 0;
  font-family: Arial, Helvetica, sans-serif;
}

.topnav {
  overflow: hidden;
}

.topnav a {
  float: left;
  color: #00000;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  font-size: 17px;
}

.topnav a:hover {
  background-color: #ddd;
  color: black;
}

.topnav a.active {
  background-color: #4CAF50;
  color: white;
}

</style>

	<%
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		// This scriplet function will validate the logged user type
		int userId = 0;
	    int user_type = 0;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("user_id"))
					userId = Integer.parseInt(cookie.getValue());
				if(cookie.getName().equals("user_type"))
					user_type = Integer.parseInt(cookie.getValue());
			}
		}
		if (userId == 0)
			response.sendRedirect("userAccountControllerServlet");
	%>
	<div class="topnav">
		<div id="header">
			<h2 style="text-align:center; background-color: #333;" >XYZ University</h2>
		</div>
	</div>

	<div class="topnav">
		<% if(user_type == 3){ %>	
			<a href="userAccountControllerServlet?command=LIST">User accounts <%= user_type %></a> 
			<a href="studentControllerServlet?command=LIST">List of Students</a> 
			<a href="ownerControllerServlet?command=LIST">List of Owners</a> 
			<a href="userAccountControllerServlet">Logout</a>
		<% }else if(user_type == 2) {%>
			<a href="ownerControllerServlet?command=PROFILE&owner_id=<%=userId%>">My Profile <%= user_type %> </a> 
			<a href="propertyController?command=MYLIST&owner_id=<%=userId%>">My Properties </a> 
			<a href="viewRequestController?command=LIST&owner_id=<%=userId%>">View Request</a>
			<a href="userAccountControllerServlet?command=CHANGEO">Change Password</a>
			<a href="userAccountControllerServlet">Logout</a>
		<% }else if(user_type == 1) {%>
			<a href="studentControllerServlet?command=PROFILE&student_id=<%=userId%>">My Profile <%= user_type %></a> 
			<a href="propertyController">Properties </a> 
			<a href="viewRequestController?command=MYLIST&student_id=<%=userId%>">My View Requests </a> 
			<a href="userAccountControllerServlet?command=CHANGE">Change Password</a>
			<a href="userAccountControllerServlet" id="Logout">Logout</a>
		<% }%>
	</div>
	<script type="text/javascript">
		$("#Logout").click(function(){
	    $form=$("<form>").attr({"action":"${pageContext.request.contextPath}"+"/userAccountControllerServlet","method":"GET"})
	    .append($("<input>").attr({"type":"hidden","name":"${_csrf.parameterName}","value":"${_csrf.token}"}))
	    $("#Logout").append($form);
	    $form.submit();
	});
	</script>

	
	