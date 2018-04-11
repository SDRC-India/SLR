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
<title>SLR-Tools</title>
<link rel="shortcut icon" href="resources/images/icons/slr_favicon.png" type="image/x-icon">
<link rel="icon" href="resources/images/icons/slr_favicon.png" type="image/x-icon">
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<link rel="stylesheet" href="resources/css/bootstrap.min.css">
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
<%-- <spring:url value="${pageContext.request.contextPath}/webjars/angularjs/1.2.16/angular.min.js"
	var="angularmin" /> --%>
<script src="${pageContext.request.contextPath}/webjars/angularjs/1.2.16/angular.min.js" type="text/javascript"></script>
</head>

<body ng-app="toolsApp" ng-controller="toolsController" ng-cloak>
	<jsp:include page="fragments/header.jsp"></jsp:include>
	<div id="mymain" class="container">
		<section class="recent-causes section-padding marginBottm">
  <div class="container">
  <div class="row margintop">
      <div class="col-md-12 marginBottom">
<!-- 	  <div class="page-breadcroumb"> -->
<!--           <p>Home / Resources</p> -->
<!--         </div> -->

        <div class="inner-page-title titleClr">
          <h2> Tools </h2>
              </div>
          <table class="table table-responsive table-striped factsheet">
            <thead class="toolsContent">
              <tr>
                <th>Assessment Tools </th>
                <th style="text-align:center;">Download</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td class="fontSIze">SDRC Collect(apk)</td>
                <td style="text-align:center;"><a href="resources/pdfFiles/SDRCCollect.apk" download><i class="fa fa-android"></i></a></td>
              </tr>
			   <tr>
                <td class="fontSIze">User Manual</td>
                <td style="text-align:center;"><a href="resources/pdfFiles/LR_Telangana_SDRC_Collect_User_Manual_v1.pdf" download><i class="fa fa-2x fa-file-pdf-o"></i></a></td>
              </tr>
              <tr>
                <td class="fontSIze">SLR Questionnaire</td>
                <td style="text-align:center;"><a href="resources/pdfFiles/Checklist for Standardization of Birthing Units Labour Rooms V5.pdf" download><i class="fa fa-2x fa-file-pdf-o"></i></a></td>
              </tr>
           </tbody>
          </table>
          
          
          <table class="table table-responsive table-striped factsheet">
            <thead class="toolsContent">
              <tr>
                <th  style="width:62%">Guidelines</th>
                <th style="text-align:center;">Download</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td class="fontSIze">LR Guidelines</td>
                <td style="text-align:center;"><a href="resources/pdfFiles/Final Labor Rooms Guidelines (4).pdf" download><i class="fa fa-2x fa-file-pdf-o"></i></a></td>
              </tr>
              <tr>
                <td class="fontSIze">MNH Toolkit</td>
                <td style="text-align:center;"><a href="resources/pdfFiles/MNH Toolkit_23_11_2013.pdf" download><i class="fa fa-2x fa-file-pdf-o"></i></a></td>
              </tr>
           </tbody>
          </table>
          
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
	<script type="text/javascript">
		var app = angular.module("toolsApp", []);
		var myAppConstructor = angular.module("toolsApp");
		myAppConstructor.controller("toolsController",
				toolsController);
		myAppConstructor.service('allServices', allServices);
	</script>
</body>

</html>