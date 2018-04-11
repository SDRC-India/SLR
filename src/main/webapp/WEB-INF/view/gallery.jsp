<!-- 
@author Swarna (swarnaprava@sdrc.co.in)
 -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html >
<head>
<title>SLR-Gallery</title>
<link rel="shortcut icon" href="resources/images/icons/slr_favicon.png" type="image/x-icon">
<link rel="icon" href="resources/images/icons/slr_favicon.png" type="image/x-icon">
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/css/lightbox.css">
<!-- <link rel="stylesheet" href="resources/css/blueimp-gallery.min.css"> -->
<link href="https://fonts.googleapis.com/css?family=Questrial"
	rel="stylesheet">
<link rel="stylesheet" href="resources/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/customLoader.css">
<link rel="stylesheet" href="resources/css/style.css">

<%-- <spring:url value="${pageContext.request.contextPath}/webjars/jquery/2.0.3/jquery.min.js" var="jQuery" /> --%>
<script src="${pageContext.request.contextPath}/webjars/jquery/2.0.3/jquery.min.js"></script>
<%-- <spring:url value="/webjars/bootstrap/3.1.1/js/bootstrap.min.js" --%>
<%-- 	var="bootstrapjs" /> --%>
<%-- <script src="${bootstrapjs}"></script> --%>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="resources/js/photo-gallery.js"></script>
<%-- <spring:url value="/webjars/angularjs/1.2.16/angular.min.js"
	var="angularmin" /> --%>
<script src="${pageContext.request.contextPath}/webjars/angularjs/1.2.16/angular.min.js" type="text/javascript"></script>
</head>

<body ng-app="toolsApp" ng-controller="toolsController" ng-cloak>
	<jsp:include page="fragments/header.jsp"></jsp:include>
	<div id="mymain" class="container">
		<section class="recent-causes section-padding">
  <div class="container contain-box">
  <div class="row margintop">
      <div class="col-md-12 marginBottom">


<div class="container">    
        <div class="row" style="text-align:center; border-bottom:1px dashed #ccc;  padding:0 0 20px 0; margin-bottom:40px;">
            <h3 style="font-family:inherit; font-weight:bold; font-size:30px;color: #333a3b;">
               Photo Gallery
        </h3>
        </div>
        
        <ul class="row photogallery">
           
            <a href="resources/images/photo_big_1.jpg" class="img-responsive example-image-link" data-lightbox="example-set" data-title="After LR at CHCFRU" data-gallery>
             <li class="col-lg-3 col-md-3 col-sm-3 col-xs-4">
                <img class="example-image img img-responsive" src="resources/images/photo_sm_1.jpg">
            </li>
            </a>
            <a href="resources/images/photo_big_21.jpg" class="img-responsive example-image-link" data-lightbox="example-set" data-title="Before LR at CHCFRU" data-gallery>
            <li class="col-lg-3 col-md-3 col-sm-3 col-xs-4">
                <img class="example-image img img-responsive" src="resources/images/photo_sm_2.jpg">
            </li>
            </a>
            <a href="resources/images/photo_big_3.jpg" class="img-responsive example-image-link" data-lightbox="example-set" data-title="CHC After" data-gallery>
            <li class="col-lg-3 col-md-3 col-sm-3 col-xs-4">
                <img class="example-image img img-responsive" src="resources/images/photo_sm_3.jpg">
            </li>
            </a>
            <a href="resources/images/photo_big_4.jpg" class="img-responsive example-image-link" data-lightbox="example-set" data-title="HDU" data-gallery>
            <li class="col-lg-3 col-md-3 col-sm-3 col-xs-4">
                <img class="example-image img img-responsive" src="resources/images/photo_sm_4.jpg">
            </li>
            </a>
            <a href="resources/images/photo_big_5.jpg" class="img-responsive example-image-link" data-lightbox="example-set" data-title="HDU Nursing Station" data-gallery>
            <li class="col-lg-3 col-md-3 col-sm-3 col-xs-4">
                <img class="example-image img img-responsive" src="resources/images/photo_sm_5.jpg">
            </li>
            </a>
            <a href="resources/images/photo_big_6.jpg" class="img-responsive example-image-link" data-lightbox="example-set" data-title="HDU unit" data-gallery>
            <li class="col-lg-3 col-md-3 col-sm-3 col-xs-4">
                <img class="example-image img img-responsive"  src="resources/images/photo_sm_6.jpg">
            </li>
            </a>
            <a href="resources/images/photo_big_7.jpg" class="img-responsive example-image-link" data-lightbox="example-set" data-title="Labor Table With All Equipment"  data-gallery>
            <li class="col-lg-3 col-md-3 col-sm-3 col-xs-4">
                <img class="example-image img img-responsive" src="resources/images/photo_sm_7.jpg">
            </li>
            </a>
            <a href="resources/images/photo_big_8.jpg" class="img-responsive example-image-link" data-lightbox="example-set" data-title="LDR Crash Cart" data-gallery>
            <li class="col-lg-3 col-md-3 col-sm-3 col-xs-4">
                <img class="example-image img img-responsive" src="resources/images/photo_sm_8.jpg">
            </li>
            </a>
            <a href="resources/images/photo_big_9.jpg" class="img-responsive example-image-link" data-lightbox="example-set" data-title="LDR Unit" data-gallery>
            <li class="col-lg-3 col-md-3 col-sm-3 col-xs-4">
                <img class="example-image img img-responsive" src="resources/images/photo_sm_9.jpg">
            </li>
            </a>
            <a href="resources/images/photo_big_10.jpg" class="img-responsive example-image-link" data-lightbox="example-set" data-title="LR Complex Entry" data-gallery>
            <li class="col-lg-3 col-md-3 col-sm-3 col-xs-4">
                <img class="example-image img img-responsive" src="resources/images/photo_sm_10.jpg">
            </li>
            </a>
            <a href="resources/images/photo_big_11.jpg" class="img-responsive example-image-link" data-lightbox="example-set" data-title="Mother And Baby" data-gallery>
            <li class="col-lg-3 col-md-3 col-sm-3 col-xs-4">
                <img class="example-image img img-responsive" src="resources/images/photo_sm_11.jpg">
            </li>
            </a>
            <a href="resources/images/photo_big_12.jpg" class="img-responsive example-image-link" data-lightbox="example-set" data-title="Recovery Room" data-gallery>
            <li class="col-lg-3 col-md-3 col-sm-3 col-xs-4">
                <img class="example-image img img-responsive" src="resources/images/photo_sm_12.jpg">
            </li>
            </a>
           
          </ul>             
    </div> <!-- /container -->
    
<!--     <div id="blueimp-gallery" class="blueimp-gallery"> -->
<!-- 							<div class="slides"></div> -->
<!-- 							<h3 class="title"></h3> -->
<!-- 							<a class="prev">&lt;</a> <a class="next">&gt;</a> <a -->
<!-- 								class="close">×</a> <a class="play-pause"></a> -->
<!-- 							<ol class="indicator"></ol> -->
<!-- 							The - dialog, which will be used to wrap the lightbox content -->
<!-- 							<div class="modal fade"> -->
<!-- 								<div class="modal-dialog"> -->
<!-- 									<div class="modal-content"> -->
<!-- 										<div class="modal-header"> -->
<!-- 											<button type="button" class="close" aria-hidden="true">&times;</button> -->
<!-- 											<h4 class="modal-title"></h4> -->
<!-- 										</div> -->
<!-- 										<div class="modal-body next mapimage"></div> -->
<!-- 										<div class="modal-footer"> -->
<!-- 											<button type="button" class="btn btn-default pull-left prev"> -->
<!-- 												<i class="glyphicon glyphicon-chevron-left"></i> Previous -->
<!-- 											</button> -->
<!-- 											<button type="button" class="btn btn-primary next"> -->
<!-- 												Next <i class="glyphicon glyphicon-chevron-right"></i> -->
<!-- 											</button> -->
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</div>	 -->
    
     
<!--     <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"> -->
<!--       <div class="modal-dialog"> -->
<!--         <div class="modal-content">          -->
<!--           <div class="modal-body">                 -->
<!--           </div> -->
<!--         </div>/.modal-content -->
<!--       </div>/.modal-dialog -->
<!--     </div>/.modal -->
          
        </div>
      </div>
	  <br/>
      </div>
      </section>
    </div> <br>
    
	<jsp:include page="fragments/footer.jsp"></jsp:include>
	<script type="text/javascript"
		src="resources/js/angularServices/services.js"></script>
	<script type="text/javascript"
		src="resources/js/angularControllers/toolsController.js"></script>
		<script src="resources/js/lightbox-plus-jquery.min.js"></script>
<!-- 		<script src="resources/js/blueimp.gallery.min.js"></script> -->
<!-- <script src="resources/js/bootstrapimage.gallery.js"></script> -->
	<script type="text/javascript">
		var app = angular.module("toolsApp", []);
		var myAppConstructor = angular.module("toolsApp");
		myAppConstructor.controller("toolsController",
				toolsController);
		myAppConstructor.service('allServices', allServices);
	</script>
	<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-36251023-1']);
  _gaq.push(['_setDomainName', 'jqueryscript.net']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</body>

</html>