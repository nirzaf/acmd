<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
	if(user_type == 1)
		response.sendRedirect("studentControllerServlet?command=LOAD&student_id="+userId);
	else if(user_type == 2)
		response.sendRedirect("ownerControllerServlet?command=LOAD&owner_id="+userId);
	else if(user_type == 3)
		response.sendRedirect("userAccountControllerServlet?command=LIST");
	else
		response.sendRedirect("userAccountControllerServlet");
%>