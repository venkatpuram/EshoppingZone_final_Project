<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="details" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<th>orderId </th>	
		<th>orderDate </th>
		<th>amountPaid </th>
		<th>modeOfPayment</th>
		<th>quantity</th>
	</tr>
	
	<details:forEach var="orders" items="${orders}">
	
	<tr>
		<td>${orders.orderId}</td>
		<td>${orders.orderDate}</td>
		<td>${orders.ammountPaid}</td>
		<td>${orders.modeOfPayment}</td>
		<td>${orders.quantity}</td>
	</tr>
	
	</details:forEach>
	</table>
</body>
</html>