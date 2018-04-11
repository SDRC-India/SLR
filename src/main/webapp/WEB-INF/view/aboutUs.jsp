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

<title>SLR-About AHI</title>
<link rel="shortcut icon" href="resources/images/icons/slr_favicon.png" type="image/x-icon">
<link rel="icon" href="resources/images/icons/slr_favicon.png" type="image/x-icon">
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link href="https://fonts.googleapis.com/css?family=Questrial"
	rel="stylesheet">
<link rel="stylesheet" href="resources/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/customLoader.css">
<link rel="stylesheet" href="resources/css/style.css">

<%-- <spring:url value="/webjars/jquery/2.0.3/jquery.min.js" var="jQuery" /> --%>
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

				<div class="">
					<h3>About Us</h3>
				</div>
				<p class="termsdata">Government of Telangana is committed to
					provide an affordable and quality health care to Women and Children
					who constitute the vulnerable group in the community. Ministry of
					Health and Family Welfare, Government of India, released
					"Guidelines for Standardization of Labor Rooms at Delivery Points"
					in the month of April 2016 and conducted a workshop in October 2016
					to sensitize the states. Subsequently, the State conducted workshop
					for the district level officials. In November 2016, Telangana State
					took the initiative of "Standardization labor rooms" with an aim to
					improve the coverage and quality of institutional deliveries at
					each level of health facility. Further, the State Government is
					also focusing on quality of care and reducing unwarranted surgical
					interventions during deliveries. The State is also tracking of high
					risk pregnancies to reduce maternal mortality .</p>
					
				<div class="blank-45"></div>
				
				<div class="terms-margin" id="aboutAhi">
					<h3>About ACCESS Health International</h3>
				</div>
				<p class="termsdata">ACCESS Health International is a non-profit
					think tank, advisory group, and knowledge and implementation
					partner to governments and the private sector. Our mission is to
					improve access to high quality, affordable health care for people
					everywhere. Our principal focus areas are Maternal & Child Health,
					Primary Care, Quality & Process Improvement, Health Care Technology
					and Training. ACCESS Health International is the Technical Partner
					to the Commissioner of Health and Family Welfare and has developed
					the Information Technology Platform for Standardization of Labour
					Rooms.</p>
					
				<div class="blank-45"></div>
				
				<div class="terms-margin" id="aboutCiff">
					<h3>About CIFF</h3>
				</div>
				<p class="termsdata">CIFF is an independent philanthropic
					organisation, headquartered in London with offices in Nairobi and
					New Delhi. CIFF works with a wide range of partners to transform
					the lives of poor and vulnerable children in developing countries.
					The major focus areas are Survive & Thrive, Climate Change and
					Child Protection. CIFF is funding the Safe Care Saving Lives
					program implemented by ACCESS Health International in partnership
					with the Aarogyasri Health care trust in Telangana and NTR Vaidya
					Seva Trust in Andhra Pradesh and the Department of Medical Health
					and Family Welfare. CIFF has funded the development of the
					Standardization of Labour rooms Information Technology platform.</p>
					<div class="blank-45"></div>
			</div>
			<div class="blank-45"></div>
		</form>
	</div>
	
	
	
	
	<jsp:include page="fragments/footer.jsp"></jsp:include>
	
	
	<script type="text/javascript">
		$("#msgBox").show().delay(2000).fadeOut(400);
	</script>
	
</body>

</html>