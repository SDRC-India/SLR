<!-- 
@author Devikrushna 
 -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html>
<head>

<title>SLR-Privacy Policy</title>
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
	
	<div id="mymain" class="container report-height ">
		
						
				
		
			<form class="sitesection" name="siteForm">
				<div class="col-md-12 col-sm-12 col-xs-12 ">
					<div class="terms-margin"><h3>Privacy Policy</h3></div>
					<p class="termsdata">This privacy policy sets out how SLR
						uses and protects any information that you give while you use this
						website.</p>
					<p class="termsdata">SLR is committed to ensuring that your
						privacy is protected. When we ask you to provide certain
						information by which you can be identified when using this
						website, then you can be assured that it will only be used in
						accordance with this privacy statement.</p>
					<p class="termsdata">SLR may change this policy from time to
						time by updating this page. You should check this page from time
						to time to ensure that you are happy with any changes.</p>
					<h3 class="privacyinfo">What we collect</h3>
					<p class="termsdata">While using our Site, we may ask you to
						provide us with certain Personal Information (information that can
						be used to contact or identify you) and Non-Personal Information.
					</p>
					<h3 class="privacyinfo">What we do with the information we gather</h3>
					<p class="termsdata">Except as otherwise stated in this privacy
						policy, we do not sell, trade, rent or otherwise share for
						marketing purposes your personal information with third parties
						without your consent. In general, the Personal Information you
						provide to us is used to help us communicate with you. For
						example, we use Personal Information to contact users in response
						to questions, solicit feedback from users, provide technical
						support, and inform users about promotional offers.</p>
					<h3 class="privacyinfo">Security</h3>
					<p class="termsdata">We are committed to ensuring that your
						information is secure. In order to prevent unauthorized access or
						disclosure, we have put in place suitable physical, electronic and
						managerial procedures to safeguard and secure the information we
						collect online.</p>
					<h3 class="privacyinfo">Why we use cookies</h3>
					<p class="termsdata">The site may use cookies to enhance users'
						experience. Cookies help us provide you with a better website, by
						enabling us to monitor which pages you find useful and which you
						do not. A cookie in no way gives us access to your computer or any
						information about you, other than the data you choose to share
						with us. The user may choose to set their web browser to refuse
						Cookies or alert the user when the Cookies are being sent.
						However, this may prevent you from taking full advantage of the
						website.</p>
					<h3 class="privacyinfo">Links to other websites</h3>
					<p class="termsdata">At many places in this website, you will
						find links to other websites/ portals. These links have been
						placed for your convenience. SLR has no control over the
						nature, content and availability of those sites. The inclusion of
						any links does not necessarily imply a recommendation or endorse
						the views expressed within them.</p>
					<h3 class="privacyinfo">Copyright Policy</h3>
					<p class="termsdata">This website and its content is copyright
						of SLR - © SLR 2018. All rights reserved. Any
						redistribution or reproduction of part or all of the contents in
						any form is prohibited other than the following:</p>
					<ul class="termsdata">
						<li>You may reproduce the content partially or fully, with
							duly & prominently acknowledging the source.</li>
					</ul>
					<p class="termsdata">However, the permission to reproduce any
						material that is copyright of any third party has to be obtained
						from the copyright holders concerned. The contents of this website
						cannot be used in any misleading or objectionable context or
						derogatory manner.</p><br><br><br><br><br>&nbsp;<br><br><br><br><br>
				</div>
			</form>
		</div>
	
	
	
	
	<jsp:include page="fragments/footer.jsp"></jsp:include>
	
	
	<script type="text/javascript">
		$("#msgBox").show().delay(2000).fadeOut(400);
	</script>
	
</body>

</html>