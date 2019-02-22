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
	<form action="/updateDetails">
			<label><b>Enter Your Name</b></label> 
            <input type="text" placeholder="Name" name="name" value="${profile.fullName}" readonly="readonly"> 
            <br>
            <br>
            
            <label ><b>Email</b></label> 
            <input type="text" placeholder="Email" name="email" value="${profile.emailId}"> 
            
            <br>
            <br>
            
            <label><b>Mobile Number</b></label> 
            <input type="text" placeholder="Mobile number" name="mobile" value="${profile.mobileNumber}"> 
            
            <br>
            <br>
            
            <label><b>Gender</b></label> 
            <input type="text" placeholder="gender" name="gender" value="${profile.gender}">
            <br>
            <br>
			<input type="submit"  value="OK">

</form>
</body>
</html>