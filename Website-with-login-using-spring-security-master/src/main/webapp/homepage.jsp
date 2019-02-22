<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<style>
/* Remove the navbar's default margin-bottom and rounded borders */
.navbar {
	margin-bottom: 0;
	border-radius: 0;
}

/* Add a gray background color and some padding to the footer */
footer {
	background-color: #f2f2f2;
	padding: 25px;
}

.carousel-inner img {
	width: 100%; /* Set width to 100% */
	margin: auto;
	min-height: 200px;
}

.carousel-inner {
	height: 350px;
}
/* Hide the carousel text when the screen is less than 600 pixels wide */
@media ( max-width : 600px) {
	.carousel-caption {
		display: none;
	}
}

.itemlogo {
	margin-top: -8px;
	margin-left: -10px;
	width: 100px;
	height: 50px;
	border-radius: 2px;
}

#searchbox {
	margin-top: 12px;
	margin-left: 15px;
}

.btn {
	margin-left: 180px;
	margin-top: -60px;
}

#ex1 {
	margin-left: -20px;
}

#myNavbar {
	margin-left: 90px;
}

#cart{
	margin-left: 1200px;
	
}

.dropbtn {
  background-color: gray;
  color: white;
  padding: 16px;
  font-size: 16px;
  border: none;
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
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
}

.dropdown-content a {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}

.dropdown-content a:hover {background-color: #ddd;}

.dropdown:hover .dropdown-content {display: block;}

.dropdown:hover .dropbtn {background-color: #3e8e41;}
</style>
</head>
<body>

	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>

				<a class='navbar-brand'><img class="itemlogo"
					src="https://github.com/EshoppingZone/Product_images/blob/experimental/logo.JPG?raw=true"
					alt="EShoppingZone"></a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav">
					<li class="active"><a href="/home">Home</a></li>
					<li class="active"><a
						href="/getProductsByCategory?category=electronics">Electronics</a></li>
					<li class="active"><a
						href="/getProductsByCategory?category=books">Books</a></li>
					<li class="active"><a
						href="/getProductsByCategory?category=Clothing">Clothing</a></li>
					<li class="active"><a
						href="/getProductsByCategory?category=footwears">Footwears</a></li>
					<li class="active"><a
						href="/getProductsByCategory?category=sports">Sports</a></li>
					<li class="active"><a
						href="/getProductsByCategory?category=kitchen">Home & Kitchen</a></li>
					<li class="active"><a
						href="/getProductsByCategory?category=Bags And Luggage">Bags &
							Luggage</a></li>
					<li class="active" id="searchbox">
						<div class="col-xs-9">
							<form action="/searchproduct">
								<input class="form-control" type="text" placeholder="Search.."
									name="search" id="ex1">
								<button type="submit" class="btn">Submit</button>
							</form>
						</div>
					</li>

					<c:if test="${pageContext.request.userPrincipal.name == null}">
						<li><a href="/homelogin" style="margin-left: 32px" class="">Sign
								In</a></li>
					</c:if>
					<form id="logoutForm" method="POST" action="${contextPath}/logout">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form>


					<c:if test="${pageContext.request.userPrincipal.name != null}">
						<li>
							<div class="dropdown">
  <button class="dropbtn">${pageContext.request.userPrincipal.name}</button>
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
						</li>
					</c:if>

					<li><a href="/shoppingcart"> <i
							class="fa fa-shopping-cart" id="cart">Cart</i></a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div id="myCarousel" class="carousel slide" data-ride="carousel">
		<!-- Indicators -->
		<ol class="carousel-indicators">
			<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
			<li data-target="#myCarousel" data-slide-to="1"></li>
		</ol>

		<!-- Wrapper for slides -->
		<div class="carousel-inner" role="listbox">
			<div class="item active">
				<img
					src="https://github.com/EshoppingZone/Product_images/blob/experimental/image1.jpg?raw=true"
					alt="Image">
				<div class="carousel-caption">
					<h3>EMIfest !</h3>
					<p>conditions apply.</p>
				</div>
			</div>

			<div class="item">
				<img
					src="https://github.com/EshoppingZone/Product_images/blob/experimental/image2.jpg?raw=true"
					alt="Image">
				<div class="carousel-caption">
					<h3>Apple fest !</h3>
					<p>conditions apply.</p>
				</div>
			</div>
		</div>

		<!-- Left and right controls -->
		<a class="left carousel-control" href="#myCarousel" role="button"
			data-slide="prev"> <span class="glyphicon glyphicon-chevron-left"
			aria-hidden="true"></span> <span class="sr-only">Previous</span>
		</a> <a class="right carousel-control" href="#myCarousel" role="button"
			data-slide="next"> <span
			class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
			<span class="sr-only">Next</span>
		</a>
	</div>
	<div class="container text-center">
		<div class="row">
			<div class="col-sm-4">
				<img
					src="https://github.com/EshoppingZone/Product_images/blob/experimental/106.jpg?raw=true"
					class="img-responsive" style="width: 200px; height: 200px;"
					alt="Image">
				<p>Price:Rs 40800</p>
				<a class="productName1" href="searchProduct?productId=106">Acer
					Aspire E15 Laptop</a>
				<p>
					<a class="buyNow" href="addToCart?productId=106&quantity=1">Add
						To Cart</a> || <a href="addToCart?productId=106&quantity=1">Buy
						Now</a>
				</p>
			</div>
			<div class="col-sm-4">
				<img
					src="https://github.com/EshoppingZone/Product_images/blob/experimental/138.jpg?raw=true"
					style="width: 200px; height: 200px;" alt="Image">
				<p>Price:Rs 4995</p>
				<a class="productName2" href="searchProduct?productId=138">Sujata
					Powermatic Juicer Mixer Grinder</a>
				<p>
					<a class="buyNow2" href="addToCart?productId=138&quantity=1">Add
						To Cart</a> || <a href="addToCart?productId=138&quantity=1">Buy
						Now</a>
				</p>
			</div>
			<div class="col-sm-4">
				<img
					src="https://github.com/EshoppingZone/Product_images/blob/experimental/111.jpeg?raw=true"
					class="img-responsive" style="width: 200px; height: 200px;"
					alt="Image">
				<p>Price:Rs 244</p>
				<a class="productName3" href="searchProduct?productId=111">The
					Power of your Subconscious Mind</a>
				<p>
					<a class="buyNow3" href="addToCart?productId=111&quantity=1">Add
						To Cart</a> || <a href="addToCart?productId=111&quantity=1">Buy
						Now</a>
				</p>
			</div>
			<div class="col-sm-4">
				<img
					src="https://github.com/EshoppingZone/Product_images/blob/experimental/122.jpg?raw=true"
					class="img-responsive" style="width: 200px; height: 200px;"
					alt="Image">
				<p>Price:Rs 1299</p>
				<a class="productName4" href="searchProduct?productId=122">Fur
					Jaden 15 Ltrs Grey Anti Theft Waterproof Backpack</a>
				<p>
					<a class="buyNow4" href="addToCart?productId=122&quantity=1">Add
						To Cart</a> || <a href="addToCart?productId=122&quantity=1">Buy
						Now</a>
				</p>
			</div>
			<div class="col-sm-4">
				<img
					src="https://github.com/EshoppingZone/Product_images/blob/experimental/141.jpg?raw=true"
					class="img-responsive" style="width: 200px; height: 200px;"
					alt="Image">
				<p>Price:Rs 2004</p>
				<a class="productName5" href="searchProduct?productId=141">Adidas
					Men's Arius 1 M Running Shoes</a>
				<p>
					<a class="buyNow5" href="addToCart?productId=141&quantity=1">Add
						To Cart</a> || <a href="addToCart?productId=141&quantity=1">Buy
						Now</a>
				</p>
			</div>
			<div class="col-sm-4">
				<img
					src="https://github.com/EshoppingZone/Product_images/blob/experimental/151.jpg?raw=true"
					class="img-responsive" style="width: 200px; height: 200px;"
					alt="Image">
				<p>Price:Rs 199</p>
				<a class="productName6" href="searchProduct?productId=151">Fitkit
					Classic Bottle Shaker 700ml</a>
				<p>
					<a class="buyNow6" href="addToCart?productId=151&quantity=1">Add
						To Cart</a> || <a href="addToCart?productId=151&quantity=1">Buy
						Now</a>
				</p>
			</div>
			<div class="col-sm-4">
				<img
					src="https://github.com/EshoppingZone/Product_images/blob/experimental/161.jpg?raw=true"
					class="img-responsive" style="width: 200px; height: 200px;"
					alt="Image">
				<p>Price:Rs 599</p>
				<a class="productName7" href="searchProduct?productId=161">Dennis
					Lingo Men's Solid Chinese Collar Blue Casual Shirt</a>
				<p>
					<a class="buyNow7" href="addToCart?productId=161&quantity=1">Add
						To Cart</a> || <a href="addToCart?productId=161&quantity=1">Buy
						Now</a>
				</p>
			</div>
			<div class="col-sm-4">
				<img
					src="https://github.com/EshoppingZone/Product_images/blob/experimental/171_1.jpg?raw=true"
					class="img-responsive" style="width: 200px; height: 200px;"
					alt="Image">
				<p>Price:Rs 55990</p>
				<a class="productName8" href="searchProduct?productId=171">Sony
					108 cm (43 Inches) Full HD LED Smart Android TV </a>
				<p>
					<a class="buyNow8" href="addToCart?productId=171&quantity=1">Add
						To Cart</a> || <a href="addToCart?productId=171&quantity=1">Buy
						Now</a>
				</p>
			</div>
			<div class="col-sm-4">
				<img
					src="https://github.com/EshoppingZone/Product_images/blob/experimental/107.jpg?raw=true"
					class="img-responsive" style="width: 200px; height: 200px;"
					alt="Image">
				<p>Price:Rs 24800</p>
				<a class="productName8" href="searchProduct?productId=107">Vivo
					V9</a>
				<p>
					<a class="buyNow8" href="addToCart?productId=107&quantity=1">Add
						To Cart</a> || <a href="addToCart?productId=171&quantity=1">Buy
						Now</a>
				</p>
			</div>

		</div>
	</div>
	<br>

	<footer class="container-fluid text-center">
		<p>© Online Store Copyright</p>
		<p>© 2019, EshoppingZone.com, Inc. or its affiliates</p>
	</footer>
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
