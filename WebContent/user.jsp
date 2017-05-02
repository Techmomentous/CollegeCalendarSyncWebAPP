<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!-- === BEGIN HEADER === -->
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
<!-- === INCLUDE HEADER === -->
<%@include file="pages/header.jsp"%>
<!-- === INCLUDE HEADER === -->
<%
	Long userID=Long.valueOf(request.getParameter("id"));

%>
</head>
<body>
	<div id="body-bg">

		<div id="pre-header" class="container" style="height: 40px; display:none;">
			<!-- Spacing above header -->
		</div>
		<div id="header">
			<div class="container">
				<div class="row">
					<!-- Logo -->
					<div class="logo">
						<a href="admin.jsp" title=""> <img src="img/logo.png"
							alt="Logo" />
						</a>
					</div>
					<!-- End Logo -->
				</div>
			</div>
		</div>
		<div id="userid" style="display:none;"><%=userID %></div>
		<!-- Top Menu -->
		<div id="hornav" class="container no-padding">
			<div class="row">
				<div class="col-md-12 no-padding">
					<div class="text-center visible-lg">
						<ul id="hornavmenu" class="nav navbar-nav">
							<li><a href="index.jsp" class="fa-home">Home</a></li>
							<li><span class="fa-gears">Features</span>
								<ul>
									<li class="parent"><span>Event Details</span>
										<ul>
											<li><a href="javascript:publishTimeTable()">Publish Time Table</a></li>
											<li><a href="javascript:publishExam()">Publish Exam Dates</a></li>
											<li><a href="javascript:publishAssignment()">Publish Assignment</a></li>
										</ul></li>

								</ul></li>
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

				<div class="col-md-12">
					<!-- Manage Event JSP -->
<%-- 					<%@include file="pages/event_panel.jsp"%> --%>
					<!-- Calendar Event  JSP -->
					<%@include file="pages/user_calendar.jsp"%>
					<%@include file="pages/exam_calendar.jsp"%>
						<%@include file="pages/assignment_calendar.jsp"%>
<!-- 						<div id="calendar"></div> -->
				</div>
			</div>
		</div>
		<!-- === END CONTENT === -->
		<!-- === INCLUDE FOOTER === -->
		<%@include file="pages/footer.jsp"%>
		<script type="text/javascript" src="js/user.js"></script>
		<!-- === INCLUDE FOOTER === -->
</body>
</html>
<!-- === END FOOTER === -->