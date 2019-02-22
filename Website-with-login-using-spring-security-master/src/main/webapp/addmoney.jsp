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
	<form action="addmoney">
	<h3>Add Money to Wallet</h3>
		<table>
			
			<tr>
				<td>Enter Amount to Add:</td>
				<td><input type="text" name="money"></td>
			</tr>
			<tr>
				<td><input type="submit" value="Addmoney"></td>
			</tr>
		</table>
	</form>
</body>
</html>