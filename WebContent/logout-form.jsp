<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

	<%
		response.setHeader("Cache-Control","no-cache");   
    	response.setHeader("Cache-Control","no-store");  
   		
		int userId = 0;
	    int user_type = 0;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for(Cookie cookie : request.getCookies()) {
	            cookie.setMaxAge(0);
	        }

		}
		if (userId == 0)
			response.sendRedirect("userAccountControllerServlet");
	%>
