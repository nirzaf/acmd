<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
		// This scriplet function will validate the user 
		String Id = null;
	    String Type = null;
	    String Uname = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("user_id"))
					Id = cookie.getValue();
				if(cookie.getName().equals("user_type"))
					Type = cookie.getValue();
				if(cookie.getName().equals("username"))
					Uname = cookie.getValue();
			}
		}
		if (Id == null)
			response.sendRedirect("userAccountControllerServlet");
	%>