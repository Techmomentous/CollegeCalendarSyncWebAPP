<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- === BEGIN HEADER === -->

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
							<li><a href="login.jsp" class="fa-sign-in">Login</a></li>
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
				<div class="row margin-vert-30">
					<!-- Register Box -->
					<div class="col-md-6 col-md-offset-3 col-sm-offset-3">
						<form class="signup-page"
							action="AuthService?serviceRequest=signup" method="post">
							<div class="signup-header">
								<h2>Register a new account</h2>
								<p>
									Already a member? Click <a href="login.jsp">HERE</a>to login to
									your account.
								</p>
							</div>
							<label>Name</label> <input class="form-control margin-bottom-20"
								type="text" id="name" name="name" required> <label>Email
								Address <span class="color-red">*</span>
							</label> <input class="form-control margin-bottom-20" type="text"
								id="email" name="email" required>
							<div class="row">
								<div class="col-sm-6">
									<label>Password <span class="color-red">*</span>
									</label> <input class="form-control margin-bottom-20" type="password"
										id="password" name="password" required>
								</div>
								<div class="col-sm-6">
									<label>Confirm Password <span class="color-red">*</span>
									</label> <input class="form-control margin-bottom-20" type="password"
										required>
								</div>
								<div class="col-sm-6" style="display: none">
									<label>Confirm Password <span class="color-red">*</span>
									</label> <input class="form-control margin-bottom-20" type="text"
										id="usertype" name="usertype">
								</div>
							</div>
							<div class="row">
								<div class="col-sm-6">
									<div class="btn-group">
										<button type="button" class="btn btn-info">User Type</button>
										<button type="button" class="btn btn-info dropdown-toggle"
											data-toggle="dropdown">
											<span class="caret"></span> <span class="sr-only">Toggle
												Dropdown</span>
										</button>
										<ul class="dropdown-menu" role="menu">
											<li><a href="javascript:setUserType('admin')">admin</a></li>
											<li><a href="javascript:setUserType('faculty')">Faculty</a></li>
										</ul>
									</div>
								</div>
							</div>


							<div class="row">
								<div class="col-lg-8">
									<label class="checkbox"> <input type="checkbox"
										required>I read the <a href="#">Terms and
											Conditions</a>
									</label>
								</div>
								<div class="col-lg-4 text-right">
									<button class="btn btn-primary" type="submit">Register</button>
								</div>
							</div>
						</form>
					</div>
					<!-- End Register Box -->
				</div>
			</div>
		</div>
		<!-- === END CONTENT === -->
		<!-- === BEGIN FOOTER === -->
		<!-- === INCLUDE FOOTER === -->
		<%@include file="pages/footer.jsp"%>
		<!-- === INCLUDE FOOTER === -->
		<script>
			function setUserType(userType) {
				debugger
				document.getElementById("usertype").value = userType;
			}
		</script>
</body>
</html>
<!-- === END FOOTER === -->