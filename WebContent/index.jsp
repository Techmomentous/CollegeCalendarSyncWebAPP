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
            <div id="pre-header" class="container" style="height:340px">
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
                                    <a href="index.html" class="fa-home">Home</a>
                                </li>
                                <li>
                                    <a href="login.jsp" class="fa-sign-in">Login</a>
                                </li>
<!--                                 <li> -->
<!--                                     <a href="signup.jsp" class="fa-user">Signup</a> -->
<!--                                 </li> -->
                                <li>
                                    
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <!-- End Top Menu -->
            <div id="post_header" class="container" style="height:340px">
            </div>
            <div id="content-top-border" class="container">
            </div>
            <!-- === END HEADER === -->
            <!-- === BEGIN CONTENT === -->
            <div id="content">
                <div class="container no-padding">
                    <div class="row">
                        <!-- Carousel Slideshow -->
                        <div id="carousel-example" class="carousel slide" data-ride="carousel">
                            <!-- Carousel Indicators -->
                            <ol class="carousel-indicators">
                                <li data-target="#carousel-example" data-slide-to="0" class="active"></li>
                                <li data-target="#carousel-example" data-slide-to="1"></li>
                                <li data-target="#carousel-example" data-slide-to="2"></li>
                            </ol>
                            <div class="clearfix"></div>
                            <!-- End Carousel Indicators -->
                            <!-- Carousel Images -->
                            <div class="carousel-inner">
                                <div class="item active">
                                    <img src="img/slideshow/slide1.jpg">
                                </div>
                                <div class="item">
                                    <img src="img/slideshow/slide2.jpg">
                                </div>
                                <div class="item">
                                    <img src="img/slideshow/slide3.jpg">
                                </div>
                                <div class="item">
                                    <img src="img/slideshow/slide4.jpg">
                                </div>
                            </div>
                            <!-- End Carousel Images -->
                            <!-- Carousel Controls -->
                            <a class="left carousel-control" href="#carousel-example" data-slide="prev">
                                <span class="glyphicon glyphicon-chevron-left"></span>
                            </a>
                            <a class="right carousel-control" href="#carousel-example" data-slide="next">
                                <span class="glyphicon glyphicon-chevron-right"></span>
                            </a>
                            <!-- End Carousel Controls -->
                        </div>
                        <!-- End Carousel Slideshow -->
                    </div>
                </div>
                <div class="container background-gray-lighter">
                    <div class="row margin-vert-40">
                        <div class="col-md-4">
                            <div class="col-md-4">
                                <i class="fa-users fa-5x color-primary"></i>
                            </div>
                            <div class="col-md-8">
                                <h2 class="margin-top-5 margin-bottom-0">3500</h2>
                                <p>Total Students</p>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="col-md-4">
                                <i class="fa-tachometer fa-5x color-primary"></i>
                            </div>
                            <div class="col-md-8">
                                <h2 class="margin-top-5 margin-bottom-0">474</h2>
                                <p>Number Of Lecturer</p>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="col-md-4">
                                <i class="fa-mobile-phone fa-5x color-primary"></i>
                            </div>
                            <div class="col-md-8">
                                <h2 class="margin-top-5 margin-bottom-0">4500</h2>
                                <p>App User</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="container background-white">
                    <div class="row margin-vert-30">
                        <!-- Main Text -->
                        <div class="col-md-12">
                            <h2 class="text-center">Welcome to College Calendar</h2>
                            <p class="text-center">In the era of mobile technology everyone wants quick and easy way to gets the updates on their interested area. Even the education sector needs an updated systems where college faculties required a system which provides on the go updates to their students.</p>
                            <p class="text-center">With the advancement in mobile technology we proposed a calendar based android application which sync with the college server and get the events, schedule and lectures details right on the app.</p>
                        </div>
                        <!-- End Main Text -->
                    </div>
                </div>
                <div class="container background-gray-lighter">
                    <div class="row padding-vert-20">
                        <div class="col-md-1">
                        </div>
                        <div class="col-md-10">
                            <!-- Portfolio -->
                            <ul class="portfolio-group">
                                <!-- Portfolio Item -->
                                <li class="portfolio-item col-sm-6 col-xs-6 padding-20">
                                    <a href="#">
                                        <figure class="animate fadeInLeft">
                                            <img alt="image1" src="img/frontpage/image1.jpg">
                                            <figcaption>
                                                <h3>Managing lectures schedules</h3>
                                                <span>Using this feature a lecturer can manage their subject periods which sync up on students app.</span>
                                            </figcaption>
                                        </figure>
                                    </a>
                                </li>
                                <!-- //Portfolio Item// -->
                                <!-- Portfolio Item -->
                                <li class="portfolio-item col-sm-6 col-xs-6 padding-20">
                                    <a href="#">
                                        <figure class="animate fadeInRight">
                                            <img alt="image2" src="img/frontpage/image2.jpg">
                                            <figcaption>
                                                <h3>Managing exams details.</h3>
                                                <span>All the exam time table directory shows on the students app. If any exam details is added by college or lecturer.</span>
                                            </figcaption>
                                        </figure>
                                    </a>
                                </li>
                                <!-- //Portfolio Item// -->
                                <!-- Portfolio Item -->
                                <li class="portfolio-item col-sm-6 col-xs-6 padding-20">
                                    <a href="#">
                                        <figure class="animate fadeInLeft">
                                            <img alt="image3" src="img/frontpage/image3.jpg">
                                            <figcaption>
                                                <h3>Assignment details</h3>
                                                <span>Lecturer can set the assignments and its dates for their subjects.</span>
                                            </figcaption>
                                        </figure>
                                    </a>
                                </li>
                                <!-- //Portfolio Item// -->
                                <!-- Portfolio Item -->
                                <li class="portfolio-item col-sm-6 col-xs-6 padding-20">
                                    <a href="#">
                                        <figure class="animate fadeInRight">
                                            <img alt="image4" src="img/frontpage/image4.jpg">
                                            <figcaption>
                                                <h3>Events details</h3>
                                                <span>Lecturer can add the university or college events which will be coming.</span>
                                            </figcaption>
                                        </figure>
                                    </a>
                                </li>
                                <!-- //Portfolio Item// -->
                               
                            </ul>
                            <!-- End Portfolio -->
                        </div>
                        <div class="col-md-1">
                        </div>
                    </div>
                </div>
                <div class="container background-white">
                    <div class="row padding-vert-40">
                        <div class="col-md-12">
                            <h2 class="animate fadeIn text-center">WE ARE PROVIDING ALL DETAILS ON THE GO!</h2>
                            <p class="animate fadeIn text-center">Stay update with all the details publish on College Calendar.</p>
                            <p class="animate fadeInUp text-center">
<!--                                 <button class="btn btn-primary btn-lg" type="button">View Details</button> -->
                            </p>
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