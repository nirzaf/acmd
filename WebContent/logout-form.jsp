<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<meta http-equiv="Pragma" content="no-cache"> 
<meta http-equiv="Expires" content="-1"> 
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate"> 

	<%
		 response.setHeader("Cache-Control", "no-cache");
		 response.setHeader("Pragma", "no-cache");
		 response.setHeader("Expires" ,"0"); 
   		
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

<script type="text/javascript">
window.onload = function() {
    if(!window.location.hash) {
      	window.location = window.location + '#loaded';
        window.location.reload();
    }
}

function disableBackButton(){window.history.forward();}setTimeout("disableBackButton()", 0);
</script>
