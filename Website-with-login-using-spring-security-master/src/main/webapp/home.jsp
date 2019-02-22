<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script src="webjars/jquery/jquery.min.js"></script>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
.nav-link {
	font-size: large;
	margin-left: 45px;
}
.search-container input [type=text] {
	font-size: 17px;
	border: 1px solid grey;
	background: #F1F1F1
}
.search-container button {
	width: 10%;
	padding: 6px;
	font-size: 17px;
	border: 1px solid grey;
	float: left;
	background: #2196F3;
	color: white;
	border-left: none;
	cursor: pointer;
	font-size: 12px;
	line-height: normal;
	padding: 11px;
}
.search-container button:hover {
	background: #0b7dda;
}
.search-container ::after {
	content: "";
	clear: both;
	display: table;
}
#search {
	float: left;
	width: 90%;
	padding: 6px;
}
.dropbtn {
	padding: 0.5rem 1rem;
	margin-left: 3rem;
}
.dropdown {
	position: relative;
	display: inline-block;
}
.dropdown-content {
	display: none;
	position: absolute;
	background-color: #f1f1f1;
	min-width: 160px;
	box-shadow: 0px8px16px0pxrgba(0, 0, 0, 0.2);
	z-index: 1;
}
.dropdown-content a {
	color: black;
	padding: 12px16px;
	text-decoration: none;
	display: block;
}
.dropdown-content a:hover {
	background-color: #ddd;
}
.dropdown:hover .dropdown-content {
	display: block;
}
.dropdown:hover .dropbtn {
	background-color: #086cbd;
}
.img {
	background-color: rgb(250, 241, 218);
	width: 100%;
	height: 900px;
	padding-top: 2px;
	margin-top: 0px;
}
.img1 {
	width: 250px;
	height: 400px;
	margin-top: 50px;
	margin-left: 50px;
}
.img2 {
	width: 250px;
	height: 400px;
	margin-top: -400px;
	margin-left: 420px;
}
.img3 {
	width: 250px;
	height: 400px;
	margin-top: -400px;
	margin-left: 800px;
}
.img4 {
	width: 250px;
	height: 400px;
	margin-top: -400px;
	margin-left: 1150px;
}
.img5 {
	margin-bottom: 70px;
	margin-left: 50px;
}
.img6 {
	width: 250px;
	height: 400px;
	margin-top: -400px;
	margin-left: 1150px;
}
.img7 {
	width: 250px;
	height: 400px;
	margin-top: -400px;
	margin-left: 800px;
}
.img8 {
	width: 250px;
	height: 400px;
	margin-top: -400px;
	margin-left: 420px;
}
p {
	margin-left: 40px;
}
.productName1 {
	margin-left: 40px;
}
.buyNow2 {
	margin-left: 0px;
}
.buyNow3 {
	margin-left: -30px;
}
.buyNow4 {
	margin-left:-20px;
}
.buyNow5 {
	margin-left: 0px;
}
.buyNow6 {
	margin-left: -40px;
}
.buyNow7 {
	margin-left: -40px;
}
.buyNow8 {
	margin-left: 15px;
}
</style>
</head>
<body>

	<div class="col-sm-12">
		<nav class='navbar navbar-expand navbar-light bg-light'>
			<div class="col-sm-2">
				<a class='navbar-brand' href="/homepage"><img
					src="https://github.com/EshoppingZone/Product_images/blob/experimental/logo.JPG?raw=true" 
					alt="EShoppingZone"></a>
			</div>
			<div class="col-sm-3">
				<ul class='nav nav-pills'>
					<li><a href="/homepage">Home</a></li>
					<li>
						<div class="dropdown">
							<button class="btn btn-primary dropbtn">Categories</button>
							<div class="dropdown-content">
								<a href="/getProductsByCategory?category=electronics">Electronics</a> 
								<a href="/getProductsByCategory?category=books">Books&Stationary</a>
								<a href="/getProductsByCategory?category=sports">Sports</a> 
								<a href="/getProductsByCategory?category=kitchen">Home & Kitchen</a> 
								<a href="/getProductsByCategory?category=bags">Bags & Luggage</a>
							</div>
						</div>
					</li>

				</ul>
			</div>
			<div class="col-sm-4 search-container">
				<form action="/searchproduct">
					<input type="text" placeholder="Search.." name="search" id="search">
					<button type="submit">
						<i class="fa fa-search"></i>
					</button>
				</form>
			</div>
			<div class="col-sm-4">

				<ul class='nav nav-pills'>
				<div class="unauthenticated">
				<c:if test="${pageContext.request.userPrincipal.name == null}">
					<li><a href="/homelogin" style="margin-left: 32px" class="">Sign
							In</a></li>
							</c:if>
		<form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>			
        
        
        
							<c:if test="${pageContext.request.userPrincipal.name != null}">
									<div class="dropdown">
							<button class="btn btn-primary dropbtn">${pageContext.request.userPrincipal.name}</button>
							<div class="dropdown-content">
								<a href="/viewprofile">View Profile</a> 
								<a href="/yourorders">Your Orders</a>
								<a href="/yourwallet">View Wallet</a> 
								<c:if test="${profile.password!=null}" >
                                <a onclick="document.forms['logoutForm'].submit()">Logout</a></c:if>
                                <c:if test="${profile.password==null}" >
                                <a href="#" onclick="myFunction();document.forms['logoutForm'].submit()">Logout</a></c:if>
							</div>
						</div>
        
        

    </c:if>
				</div>
					<li><a href="/shoppingcart"> <i class="fa fa-shopping-cart"
							style="margin-left: 67px">Cart</i></a></li>
				</ul>
		
			</div>
		</nav>
	</div>

	<script>
	   function myFunction() {
	        window.open("https://github.com/logout");
	        }
	   
		$.get("/user", function(data) {
			$("#user").html(data.userAuthentication.details.name);
			$(".unauthenticated").hide()
			$(".authenticated").show()
		});
		var logout = function() {
			$.post("/logout", function() {
				window.open('https://github.com/logout');
				$("#user").html('');
				$(".unauthenticated").show();
				$(".authenticated").hide();
			});
			return true;
		} 

	</script>
	
</body>
</html>