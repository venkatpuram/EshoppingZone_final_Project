<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div>
		<jsp:include page="home.jsp" />
	</div>
	<table>
		<tr>
			<td>Full Name: ${profile.fullName}</td>
		</tr>

		<tr>
			<td>Email: ${profile.emailId}</td>
		</tr>
		<tr>
			<td>Mobile Number: ${profile.mobileNumber}</td>
		</tr>
		<tr>
			<td>Date Of Birth: ${profile.dateOfBirth}</td>
		</tr>
		<tr>
			<td>Gender: ${profile.gender}</td>
		</tr>
		<tr>
			<td>About: ${profile.about}</td>
		</tr>
	</table>

	<h3>${wallet}</h3>
	<h3>${orders.orderId}</h3>
	<a href="/updateProfile">Update Profile</a>
</body>
</html>