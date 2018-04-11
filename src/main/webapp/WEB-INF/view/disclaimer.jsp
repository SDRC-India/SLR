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

<title>SLR-Disclaimer</title>
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


</head>

<body>
	<jsp:include page="fragments/header.jsp"></jsp:include>
	
	<div id="mymain" class="container report-height">
		
						
				
			
			<form class="sitesection" name="siteForm">
				<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="terms-margin"><h3>Disclaimer</h3></div>
					<p class="termsdata">The information contained in this website
						is for general information purposes only. The information is
						provided by SLR and while we endeavour to keep the information
						up to date and correct, we make no representations or warranties
						of any kind, express or implied, about the completeness, accuracy,
						reliability, suitability or availability with respect to the
						website or the information, products, services, or related
						graphics contained on the website for any purpose. Any reliance
						you place on such information is therefore strictly at your own
						risk. Users are advised to verify/ check any information, and to
						obtain any appropriate professional advice before acting on the
						information provided on the website.</p>
					<p class="termsdata">In no event will we be liable for any loss
						or damage including without limitation, indirect or consequential
						loss or damage, or any loss or damage whatsoever arising from loss
						of data or profits arising out of, or in connection with, the use
						of this website.</p>
					<p class="termsdata">Every effort is made to keep the website
						up and running smoothly. However, SLR takes no responsibility
						for, and will not be liable for the website being temporarily
						unavailable due to technical issues beyond our control.</p>
				</div>
			</form>
		</div>
	
	
	
	
	<jsp:include page="fragments/footer.jsp"></jsp:include>
	
	
	<script type="text/javascript">
		$("#msgBox").show().delay(2000).fadeOut(400);
	</script>
	
</body>

</html>