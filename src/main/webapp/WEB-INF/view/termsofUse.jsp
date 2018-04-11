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

<title>SLR-Terms of Use</title>
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
<%-- <spring:url value="${pageContext.request.contextPath}/webjars/bootstrap/3.1.1/js/bootstrap.min.js"
	var="bootstrapjs" /> --%>
<script src="${pageContext.request.contextPath}/webjars/bootstrap/3.1.1/js/bootstrap.min.js"></script>


</head>

<body>
	<jsp:include page="fragments/header.jsp"></jsp:include>
	
	<div id="mymain" class="container report-height">
		
						
				
			
			<form class="sitesection" name="siteForm">
				<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="terms-margin"><h3>Terms of Use</h3></div>
					<p class="termsdata">Welcome to our website. If you continue to browse and use this
					 website ,you are agreeing to comply with and be bound by the following
					  terms and conditions of use: </p>
					  <ul class="termsdata">
						<li>The content of the pages of this website is for your
							general information and use only. It is subject to change without
							notice.</li>
						<li>This website does not provide any warranty or guarantee
							as to the accuracy, timeliness, performance, completeness or
							suitability of the information and materials found or offered on
							this website for any particular purpose. You acknowledge that
							such information and materials may contain inaccuracies or errors
							and we expressly exclude liability for any such inaccuracies or
							errors to the fullest extent permitted by law.</li>
						<li>Your use of any information or materials on this website
							is entirely at your own risk, for which we shall not be liable.</li>
						<li>This website contains material which is owned by or
							licensed to us. This material includes, but is not limited to,
							the design, layout, look, appearance and graphics. Reproduction
							is prohibited other than in accordance with the copyright notice,
							which forms part of these terms and conditions</li>
						<li>All trademarks reproduced in this website, which are not
							the property of, or licensed to the operator, are acknowledged on
							the website</li>
						<li>Unauthorized use of this website may give rise to a claim
							for damages and/or be a criminal offence.</li>
						<li>From time to time, this website may also include links to
							other websites. These links are provided for your convenience to
							provide further information. They do not signify that we endorse
							the website(s). We have no responsibility for the content of the
							linked website(s).</li>


					</ul>
				</div>
			</form>
		</div>
	
	
	
	
	<jsp:include page="fragments/footer.jsp"></jsp:include>
	
	
	<script type="text/javascript">
		$("#msgBox").show().delay(2000).fadeOut(400);
	</script>
	
</body>

</html>