<!DOCTYPE html>
<html>

<head>
<title>Welcome to XYZ University's Accommodation Management Web Application</title>

<link type="text/css" rel="stylesheet" href="css/style.css">
<link type="text/css" rel="stylesheet" href="css/add-student-style.css">
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>XYZ University</h2>
		</div>
	</div>

	<div id="container">
		<h3>Sign-Up Now</h3>
		<h5 style="color:red">Please fill up following details</h5>

		<form action="userAccountControllerServlet" method="POST">

			<input type="hidden" name="command" value="REGISTER" />

			<table>
				<tbody>
					<tr>
						<td><label>Username:</label></td>
						<td><input type="text" name="username" placeholder="Username" required/></td>
					</tr>

					<tr>
						<td><label>Password:</label></td>
						<td><input id="password" name="password" type="password" pattern="^\S{6,}$" onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Must have at least 6 characters' : ''); if(this.checkValidity()) form.password_two.pattern = this.value;" placeholder="Password" required></td>
					</tr>

					<tr>
						<td><label>Confirm PW:</label></td>
						<td><input id="password_two" name="confirm_password" type="password" pattern="^\S{6,}$" onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Please enter the same Password as above' : '');" placeholder="Verify Password" required></td>
					</tr>
					
					<tr>
						<td><label>Account Type:</label></td>
						<td><select name="account-type" required> 
						 <option value = "1">Student</option>
						 <option value = "2">Owner</option>
						</select></td>
					</tr>
					
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Sign-up" class="save" /> </td>
					</tr>									        
				</tbody>		
			</table>
		</form>

		<div style="clear: both;"></div>
		
	</div>
</body>

</html>