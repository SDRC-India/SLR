<!-- 
@author Devikrushna 
 -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html ng-app="reportApp">
<head>

<title>SLR-About CIFF</title>
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
<%-- <spring:url value="/webjars/bootstrap/3.1.1/js/bootstrap.min.js"
	var="bootstrapjs" /> --%>
<script src="${pageContext.request.contextPath}/webjars/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<%-- <spring:url value="/webjars/angularjs/1.2.16/angular.min.js"
	var="angularmin" /> --%>
<script src="${pageContext.request.contextPath}/webjars/angularjs/1.2.16/angular.min.js" type="text/javascript"></script>

</head>

<body>
	<jsp:include page="fragments/header.jsp"></jsp:include>
	
	<div id="mymain" class="container report-height">
		
						
				
<!-- 			<div class="terms-margin"><h3>About CIFF: </h3></div> -->
			<form class="sitesection" name="siteForm">
				<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="terms-margin"><h3>About CIFF: </h3></div>
					<p class="termsdata">CIFF is an independent philanthropic organisation, headquartered in London
					 with offices in Nairobi and New Delhi. CIFF works with a wide range of partners to transform the
					  lives of poor and vulnerable children in developing countries. The major focus areas are Survive & Thrive, 
					  Climate Change and Child Protection. CIFF is funding the Safe Care Saving Lives program implemented by
					   ACCESS Health International in partnership with the Aarogyasri Health care trust in Telangana and NTR
					    Vaidya Seva Trust in Andhra Pradesh and the Department of Medical Health and Family Welfare. 
					    CIFF has funded the development of the Standardization of Labour rooms Information Technology platform. </p>
					
				</div>
			</form>
		</div>
	
	
	
	
	<jsp:include page="fragments/footer.jsp"></jsp:include>
	<script type="text/javascript"
		src="resources/js/angularServices/services.js"></script>
	<script type="text/javascript"
		src="resources/js/angularControllers/reportController.js"></script>
	
	<script type="text/javascript">
		$("#msgBox").show().delay(2000).fadeOut(400);
	</script>
	
</body>

</html>