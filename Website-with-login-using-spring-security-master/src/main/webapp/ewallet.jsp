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
<form action="paymoney">
	<h3>Wallet Balance : ${wallet.walletmoney}</h3>
	<h3>Amount To Be Paid : ${wallet.cartprice}</h3>
	<input type="submit" value="Click Here to Pay" name="pay">
	</form>
</body>
</html>