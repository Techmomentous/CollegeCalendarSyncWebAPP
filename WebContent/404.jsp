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
   <%@include file="pages/header.jsp" %>
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
                            <a href="index.jsp" title="">
                                <img src="img/logo.png" alt="Logo" />
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
                                <li>
                                    <a href="index.jsp" class="fa-home">Home</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <!-- End Top Menu -->
            <div id="post_header" class="container" style="height: 40px">
                <!-- Spacing below header -->
            </div>
            <div id="content-top-border" class="container">
            </div>
            <!-- === END HEADER === -->
            <!-- === BEGIN CONTENT === -->
            <div id="content">
                <div class="container background-white">
                    <div class="row margin-vert-30">
                        <div class="col-md-12">
                            <div class="error-404-page text-center">
                                <h2>404!</h2>
                                <h3>The page can not be found</h3>
                                <p>The page you are looking for might have been removed,
                                    <br>had its name changed or is temporarily unavailable.</p>
                              
                            </div>
                        </div>
                    </div>
                </div>
            </div>
              <!-- === END CONTENT === -->
            <!-- === BEGIN FOOTER === -->
           <!-- === INCLUDE FOOTER === -->
          <%@include file="pages/footer.jsp" %>
          <!-- === INCLUDE FOOTER === -->
    </body>
</html>
<!-- === END FOOTER === -->