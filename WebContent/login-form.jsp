<!DOCTYPE html>
<html>

<head>
<title>Welcome to XYZ University's Accommodation Management Web
	Application</title>

<link type="text/css" rel="stylesheet" href="css/style.css">
<link type="text/css" rel="stylesheet" href="css/add-student-style.css">
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>XYZ University</h2>
		</div>
	</div>


	<h5 style="color: red"><%=(request.getAttribute("errMessage") == null) ? "" : "Successfully account registered"%></h5>

	<h5 style="color: red">${Message}</h5>

	<div id="container">
		<h3>Login Now</h3>
		<h5 style="color: red">Please login to your Account</h5>

		<form action="userAccountControllerServlet" method="POST">
			<input type="hidden" name="command" value="LOGIN" />
			<table>
				<tbody>
					<tr>
						<td><label>Username:</label></td>
						<td><input type="text" name="username" /></td>
					</tr>

					<tr>
						<td><label>Password:</label></td>
						<td><input type="password" name="password" /></td>
					</tr>

					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Sign-in" class="save" /></td>
					</tr>
					<tr></tr>
				</tbody>
			</table>
		</form>
	</div>
	<div style="clear: both;"></div>
	<div>
		<form action="userAccountControllerServlet" method="GET">
			<input type="hidden" name="command" value="SIGNUP" />
			<table>
				<tbody>
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Sign-up" class="save" /></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<div style="clear: both;"></div>

</body>

</html>