<!-- 
@author swarna,Devikrushna,Laxman
 -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html ng-app="homeApp">
<head>

<title>SLR-Home</title>
<link rel="shortcut icon" href="resources/images/icons/slr_favicon.png" type="image/x-icon">
<link rel="icon" href="resources/images/icons/slr_favicon.png" type="image/x-icon">
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/animate.css">
<link href="https://fonts.googleapis.com/css?family=Questrial" rel="stylesheet">
<link rel="stylesheet" href="resources/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/animate.min.css">
<link rel="stylesheet" href="resources/css/bootstrap-dropdownhover.min.css">
<link rel="stylesheet" href="resources/css/customLoader.css">
<link rel="stylesheet" href="resources/css/style.css">
<script src="${pageContext.request.contextPath}/webjars/jquery/2.0.3/jquery.min.js"></script>
<%-- <spring:url value="/webjars/bootstrap/3.1.1/js/bootstrap.min.js"
	var="bootstrapjs" /> --%>
<script src="${pageContext.request.contextPath}/webjars/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<%-- <spring:url value="/webjars/angularjs/1.2.16/angular.min.js"
		var="angularmin" /> --%>
	<script src="${pageContext.request.contextPath}/webjars/angularjs/1.2.16/angular.min.js" type="text/javascript"></script>
</head>

<body ng-controller="HomeController" class="slrhome">
<style type="text/css">
.slrhome .home-menu-font{
	position: absolute;
    z-index: 555;
    width: 100%;
    box-shadow: none;
    background-color: rgba(255, 255, 255, 0.68);
}
.inner-slr-img{
display:none;
}
@media(max-width: 767px){
	.slrhome .home-menu-font{
	position: relative;  
		width: 100%;
		
	}
}
	

</style>
<div class="container logoHeader">
		<div class="col-md-6 col-sm-6 col-xs-6 text-left">
		<a href="http://www.telangana.gov.in/" target="_blank" ><img
				class="logo1" src="resources/images/Telangana-govt_505_053114102100.png"
				alt="Telangana Logo" width="auto;" height="74px" ></a>
			<a href="#" target="_blank" ><img
				class="" src="resources/images/slr_logo_header.svg"
				alt="Telangana Logo" width="auto;" height="74px"></a>
<!-- 				<h4 class="headerinfo"> -->
<!-- 						Standardisation of Labour Rooms<span -->
<!-- 							style="font-size: 12px; color: #79bdf8;">&nbsp; 1.1.0</span><br> -->
<!-- 							<span style="font-size: 14px; color: #333a3b;">Government of Telangana</span> -->
<!-- 					</h4> -->
		</div>
		<div class="col-md-6 col-sm-6 col-xs-6 logo-home" style="text-align: right;">
<!-- 		<a href="http://www.telangana.gov.in/" target="_blank" ><img -->
<!-- 				class="logo1" src="resources/images/Telangana-govt_505_053114102100.png" -->
<!-- 				alt="Telangana Logo" width="auto;" height="74px" ></a> -->
			<a href="http://nhm.gov.in/" target="_blank" ><img class="logo2"
				src="resources/images/nhmlogo.png" alt="nhm_logo" width="auto;"
				height="70px" style="margin-top: -5px;"></a>
		</div>
	</div>

	<div id="errMsg" class="text-center">
		<serror:Error id="msgBox" errorList="${formError}"
			cssInfClass="${className}">
		</serror:Error>
	</div>
	<div id="mymain">
		<section id="homeslide">

			<div>
				<div>
					<div id="myCarousel" class="carousel slide" data-ride="carousel">

						<!-- Indicators -->
						<!-- 						  <ol class="carousel-indicators"> -->
						<!-- 						    <li data-target="#myCarousel" data-slide-to="0" class="active"></li> -->
						<!-- 						    <li data-target="#myCarousel" data-slide-to="1"></li> -->
						<!-- 						    <li data-target="#myCarousel" data-slide-to="2"></li> -->
						<!-- 						  </ol> -->
						<div class="carousel-inner" role="listbox">
							<jsp:include page="fragments/header.jsp"></jsp:include>
							<div class="item img-height active">
								<img src="resources/images/slr_slider1.jpg" alt="SLR"
									width="100%;">
							</div>
							<div class="item img-height">
								<img src="resources/images/slr-slider2 .jpg" alt="SLR"
									width="100%;">
							</div>
							<div class="item img-height">
								<img src="resources/images/slr-slider3.jpg" alt="SLR"
									width="100%;">
							</div>
						</div>
						<a class="left carousel-control" href="#myCarousel" role="button"
							data-slide="prev"> <!--     <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span> -->
							<span class="sr-only">Previous</span>
						</a> <a class="right carousel-control" href="#myCarousel"
							role="button" data-slide="next"> <!--     <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span> -->
							<span class="sr-only">Next</span>
						</a>
					</div>
				</div>
			</div>
		</section>


		<div class="blank-45"></div>
		<div class="container dashboard-home-margin">
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<div class="col-md-4 animate-dashboard">
						<a href="dashboard" target="_blank">
							<div class="col-md-12 col-sm-12 col-xs-12 dashboard-div">
								<h5 class="title partnersLogo">DASHBOARD</h5>

								<div class="col-md-12 col-sm-12 col-xs-12 border-gallery">
									<img class="img-responsive home-img-gallery" alt=""
										src="resources/images/slr_home_icon_dashboard.svg">
								</div>
								<!-- 							<h4 class="learn-more">Click here to learn more</h4> -->
							</div>
						</a>
					</div>
					<div class="col-md-4 animate-dashboard">
						<a href="report" target="_blank">
							<div class="col-md-12 col-sm-12 col-xs-12 dashboard-div">
								<h5 class="title partnersLogo">REPORT</h5>
								<div class="col-md-12 col-sm-12 col-xs-12 border-gallery">
									<img class="img-responsive home-img-gallery" alt=""
										src="resources/images/slr_home_icon_report.svg">
								</div>
								<!-- 							<h4 class="learn-more">Click here to learn more</h4> -->
							</div>
						</a>
					</div>
					<div class="col-md-4 animate-dashboard">
						<a href="gallery" target="_blank">
							<div class="col-md-12 col-sm-12 col-xs-12 dashboard-div">
								<h5 class="title partnersLogo">GALLERY</h5>
								<div class="col-md-12 col-sm-12 col-xs-12 border-gallery">
									<img class="img-responsive home-img-gallery" alt=""
										src="resources/images/slr_home_icon_gallery.svg">

								</div>
								<!-- 							<h4 class="learn-more">Click here to learn more</h4> -->
							</div>
						</a>
					</div>
				</div>
			</div>
		</div>


		<div class="blank-45"></div>
		<div class="container" id="about">
			<div class="col-md-12">
				<div class="col-md-12 col-sm-12 col-xs-12"
					style="background-color: rgba(51, 58, 59, 0.87);">
					<h3 class="about-center">ABOUT US</h3>
					<div class="col-md-4 col-sm-12 col-xs-12">
						<img class="img-responsive about-img" alt=""
							src="resources/images/slr_aboutus_img.jpg">
					</div>
					<div class="col-md-8 col-sm-12">

						<p class="about-content">Government of Telangana is committed
							to provide an affordable and quality health care to Women and
							Children who constitute the vulnerable group in the community.
							Ministry of Health and Family Welfare, Government of India,
							released "Guidelines for Standardization of Labor Rooms at
							Delivery Points" in the month of April 2016 and conducted a
							workshop in October 2016 to sensitize the states. Subsequently,
							the State conducted workshop for the district level officials. In
							November 2016, Telangana State took the initiative of
							"Standardization labor rooms" with an aim to improve the coverage
							and quality of institutional deliveries at each level of health
							facility. Further, the State Government is also focusing on
							quality of care and reducing unwarranted surgical interventions

							during deliveries. The State is also tracking of high risk
							pregnancies to reduce maternal mortality .</p>
						<ul class="about-ahi-ul">
							<li class="about-ahi-li"><span><h3
										class="about-ahi">
										<a href="aboutUs#aboutAhi" style="color: #fff;">About
											AHI</a>
									</h3></span></li>
							<li class="about-ciff-li"><span><h3
										class="about-ahi">
										<a href="aboutUs#aboutCiff" style="color: #fff;">About
											CIFF</a>
									</h3></span></li>
						</ul>
					</div>
					<div></div>
				</div>
			</div>
		</div>
		<div class="blank-45"></div>



		<div class="contactColor" id="contactus">
			<div class="contactus1">
				<div class="container ">
					<div class="col-md-12 "></div>
					<h3 class="contactus-font">CONTACT US</h3>
					<div class="col-md-6 txtCenter contact-head">
						<h5 class="contactus-font1">Mission Director, National Health Mission</h5>
						<span class="contactus-font2">Commissioner of Health and Family Welfare 
							<br>DME building, 3rd Floor, DM&HS Campus, Sultan Bazaar, Koti,<br> Hyderabad, Telangana 500195, India <br>
							Phone:040 2460 2515<br> <b>Dr. S Padmaja</b><br> <b>Mail
								to: </b> <a href="mailto:jdmhnts@gmail.com ">jdmhnts@gmail.com
						</a><br>
						</span>
						
						<h5 class="contactus-font1">Indian Institute of Health and Family Welfare</h5>
						<span class="contactus-font2">Vengal Rao Nagar, Opposite
							Ramalayam,<br> Hyderabad, Telangana 500038, India <br>
							Phone:040 2381 0400<br> <b>Dr. Neelima Singh</b><br> <b>Mail
								to: </b> <a href="mailto:singh.neelima18@gmail.com ">singh.neelima18@gmail.com
						</a><br>
						</span>
						
					</div>
					<div class="col-md-6 text-center marginMap">
					<div class="box contact-form-wrapper">
							<div style="height:270px; border:2px solid #eee; overflow:hidden;">
						<iframe style="position:relative; top:-47px; border:none;" src=https://www.google.com/maps/d/u/1/embed?mid=1KpU5yAJA2y8Q6pjEJDUmnzgkYQRkTfdC width="100%" height="340" allowfullscreen></iframe>
					</div>
					</div>
					</div>
				</div>
			</div>
		</div>
		<div class="blank-45"></div>
		<div class="container backgrndClr partner-margin">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<h3 class="partner-font">PARTNERS</h3>
				<div class="col-md-3 col-sm-12 col-xs-12 text-center ">
					<a href="http://accessh.org/" target="_blank"> <img
						class="imageLogoRes" alt="" src="resources/images/ah-logo.png"
						height="30px"></a>
				</div>
				<div class="col-md-3 col-sm-12 col-xs-12 text-center partner-line">
					<a href="https://ciff.org/" target="_blank"> <img
						class="fundedBy" alt="" src="resources/images/ciff_logo.png">
					</a>
				</div>
				<div class="col-md-3 col-sm-12 col-xs-12 text-center partner-line">
					<a href="https://www.jhpiego.org/" target="_blank"> <img
						class="imageLogoRes" alt=""
						src="resources/images/Jhpiego_logo_PMS.png" height="30px"></a>
				</div>
				<div class="col-md-3 col-sm-12 col-xs-12 text-center partner-line">
					<a href="http://unicef.in/" target="_blank"> <img
						class="partner-unicef" alt="unicef"
						src="resources/images/UNICEF International .png"></a>
				</div>
			</div>
		</div>

		<!-- 		<div class="blank-45"></div> -->
		<!-- share div -->
		<div class="container backgrndClr partner-margin">
			<h3 class="partner-font">SHARE</h3>
			<div class="col-md-12 col-sm-12 col-xs-12 text-center">
				<div class="col-md-6 col-sm-6 col-xs-6 text-right ">
					<a href="https://www.facebook.com/" target="_blank"
						title="facebook"> <img class="facebook-img" alt="facebook"
						src="resources/images/facebook.png"></a> <a
						href="https://twitter.com/login" target="_blank" title="twitter">
						<img class="twitter-img" alt="twitter"
						src="resources/images/twitter.png">
					</a>
				</div>
				<div class="col-md-6 col-sm-6 col-xs-6 text-left ">
					<a href="https://www.instagram.com/accounts/login/" target="_blank"
						title="instagram"> <img class="insta-img" alt="instagram"
						src="resources/images/instagram.png">
					</a> <a href="https://in.linkedin.com/" target="_blank"
						title="linkedin"> <img class="link-img" alt="linkedin"
						src="resources/images/linkedin.png">
					</a>
				</div>

			</div>
		</div>
	</div>

	<!--end of thematic and chklist  -->
	<jsp:include page="fragments/footer.jsp"></jsp:include>
	<script type="text/javascript" src="resources/js/angularControllers/homeController.js"></script>
	<script type="text/javascript">
		var app = angular.module("homeApp", []);
		var myAppConstructor = angular.module("homeApp");
		myAppConstructor.controller("HomeController", homeController);
	</script>
	
<script type="text/javascript">
	$(window).scroll(function(){
		if($(window).scrollTop()>120){
			$(".slrhome .home-menu-font").css({"position":"fixed","top":"0","background-color":"#FFF"});
		}
		else
			$(".slrhome .home-menu-font").css({"position":"absolute","top":"none","background-color":"rgba(255, 255, 255, 0.68)"});
		
	})
	$("#msgBox").show().delay(2000).fadeOut(400);
</script>
</body>

</html>