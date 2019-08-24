<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<meta http-equiv="Pragma" content="no-cache"> 
<meta http-equiv="Expires" content="0"> 
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate"> 

	<%
		 Cookie[] cookies = request.getCookies();
	 	 if(cookies != null){
			 for(Cookie cookie : request.getCookies()) {
				 if (cookie.getName().equals("user_id"))
						cookie.setMaxAge(1);
				 if(cookie.getName().equals("user_type"))
					 	cookie.setMaxAge(1);
	        }      
		} 
		response.sendRedirect("userAccountControllerServlet?command=LOGINPAGE");
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
