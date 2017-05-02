<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!-- === BEGIN HEADER === -->
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- === INCLUDE HEADER === -->
<%@include file="pages/header.jsp"%>
<!-- === INCLUDE HEADER === -->
<body>
	<div id="body-bg">

		<div id="pre-header" class="container" style="height: 40px">
			<!-- Spacing above header -->
		</div>
		<div id="header">
			<div class="container">
				<div class="row">
					<!-- Logo -->
					<div class="logo">
						<a href="index.jsp" title=""> <img src="img/logo.png"
							alt="Logo" />
						</a>
					</div>
					<!-- End Logo -->
				</div>
			</div>
		</div>
		<!-- Top Menu -->
		<div id="hornav" class="container no-padding">
			<div class="row">
				<div class="col-md-12 no-padding">
					<div class="text-center visible-lg">
						<ul id="hornavmenu" class="nav navbar-nav">
							<li><a href="index.jsp" class="fa-home">Home</a></li>
<!-- 							<li><a href="signup.jsp" class="fa-user">Signup</a></li> -->
							<li><a href="contact.jsp" class="fa-comment">Contact</a></li>

						</ul>
					</div>
				</div>
			</div>
		</div>
		<!-- End Top Menu -->
		<div id="post_header" class="container" style="height: 40px">
			<!-- Spacing below header -->
		</div>
		<div id="content-top-border" class="container"></div>
		<!-- === END HEADER === -->
		<!-- === BEGIN CONTENT === -->
		<div id="content">
			<div class="container background-white">
				<div class="container">
					<div class="row margin-vert-30">
						<!-- Login Box -->
						<div class="col-md-6 col-md-offset-3 col-sm-offset-3">
							<form class="login-page"
								action="AuthService?serviceRequest=login" method="post">
								<div class="login-header margin-bottom-30">
									<h2>Login to your account</h2>
								</div>
								<div class="input-group margin-bottom-20">
									<span class="input-group-addon"> <i class="fa fa-user"></i>
									</span> <input placeholder="Eamil" class="form-control" type="text"
										id="email" name="email" required>
								</div>
								<div class="input-group margin-bottom-20">
									<span class="input-group-addon"> <i class="fa fa-lock"></i>
									</span> <input placeholder="Password" class="form-control"
										type="password" id="password" name="password" required>
								</div>
								<div class="button-container">
									<button type="submit">
										<span>Login</span>
									</button>
									<hr>
									<h4>New At College Calendar ?</h4>
									<p>
										<a href="signup.jsp">Click here</a>to register.
									</p>
							</form>
						</div>
						<!-- End Login Box -->
					</div>
				</div>
			</div>
		</div>
		<!-- === END CONTENT === -->

		<!-- === BEGIN FOOTER === -->
		<!-- === INCLUDE FOOTER === -->
		<%@include file="pages/footer.jsp"%>
		<!-- === INCLUDE FOOTER === -->
</body>
</html>
<!-- === END FOOTER === -->