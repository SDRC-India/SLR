<!-- 
@author Laxman (laxman@sdrc.co.in)
 -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html ng-app="reportApp">
<head>

<title>SLR-Report</title>
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
<style>
ul.dropdown-menu {
	max-width: 256px;
	max-height: 219px !important;
}
#dataTable th, #dataTable td{
	text-align: center !important;
}
#dataTable td:first-child {
    text-align: left !important;
}
</style>
</head>

<body ng-controller="ReportController" ng-cloak>
	<jsp:include page="fragments/header.jsp"></jsp:include>
	<div id="errMsg" class="text-center">
		<serror:Error id="msgBox" errorList="${formError}"
			cssInfClass="${className}">
		</serror:Error>
	</div>
	<div id="mymain" class="container report-height">
		<section id="form-section">
			<div class="row mar-bot-10" style="margin: 30px auto;">
				<div class="col-md-12">
					<div class="form-container marginBottm">
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12"
								style="margin-bottom: 40px; margin-left: 1px;">
								<div class="select-container dist-list text-center">
									<div class="input-group" style="max-width: initial;">
										<input type="text" style="max-width: initial;"
											placeholder="Report Type *" id="reportType"
											class="form-control not-visible-input" name="Dist"
											readonly="" ng-model="selectedReportType">
										<div class="input-group-btn" style="position: relative;">
											<button data-toggle="dropdown" style="height: 34px;"
												class="btn btn-color dropdown-toggle" type="button">
												<i class="fa fa-list"></i>
											</button>
											<ul class="dropdown-menu" role="menu">
												<li ng-repeat="type in allReportTypes"
													ng-click="selectReportType(type)"><a href="">{{type}}</a></li>
											</ul>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-6 col-sm-6 col-xs-6">
								<div class="select-container dist-list text-center">
									<div class="input-group" style="float: right;">
										<input type="text" placeholder="Facility Type *"
											id="facilityType" class="form-control not-visible-input"
											name="Dist" readonly=""
											ng-model="selectedFacilityType.sectorName">
										<div class="input-group-btn" style="position: relative;">
											<button data-toggle="dropdown" ng-disabled="selectedReportType == 'Raw Report'"
												class="btn btn-color dropdown-toggle" type="button">
												<i class="fa fa-list"></i>
											</button>
											<ul class="dropdown-menu" role="menu">
												<li ng-repeat="facilityType in allFacilityTypes"
													ng-click="selectFacilityType(facilityType)"><a href="">{{facilityType.sectorName}}</a></li>
											</ul>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-6 col-sm-6 col-xs-6">
								<div class="select-container dist-list text-center">
									<div class="input-group" style="float: right;">
										<input type="text" placeholder="Section *" id="section"
											class="form-control not-visible-input" name="section"
											readonly="" ng-model="selectedSection.sectorName">
										<div class="input-group-btn" style="position: relative;">
											<button data-toggle="dropdown" ng-disabled="selectedReportType == 'Raw Report'"
												class="btn btn-color dropdown-toggle" type="button">
												<i class="fa fa-list"></i>
											</button>
											<ul class="dropdown-menu" role="menu">
												<li ng-repeat="section in allSections"
													ng-click="selectSection(section)"><a href="">{{section.sectorName}}</a></li>
											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 col-sm-6 col-xs-6">
								<div class="select-container dist-list text-center">
									<div class="input-group" style="float: right;">
										<input type="text" placeholder="Subsection *" id="subsection"
											class="form-control not-visible-input" name="subsection"
											readonly="" ng-model="selectedSubsection.sectorName">
										<div class="input-group-btn" style="position: relative;">
											<button data-toggle="dropdown" ng-disabled="selectedReportType == 'Raw Report' || (selectedSection && !selectedSection.children.length)"
												class="btn btn-color dropdown-toggle" type="button">
												<i class="fa fa-list"></i>
											</button>
											<ul class="dropdown-menu" role="menu">
												<li ng-repeat="subsection in selectedSection.children"
													ng-click="selectSubsection(subsection)"><a href="">{{subsection.sectorName}}</a></li>
											</ul>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-6 col-sm-6 col-xs-6">
								<div class="select-container dist-list text-center">
									<div class="input-group" style="float: right;">
										<input type="text" placeholder="Time Period *" id="timeperiod"
											class="form-control not-visible-input" name="indicator"
											readonly="" ng-model="selectedTimePeriod.timeperiod">
										<div class="input-group-btn" style="position: relative;">
											<button data-toggle="dropdown" ng-disabled="selectedReportType == 'Raw Report'"
												class="btn btn-color dropdown-toggle" type="button">
												<i class="fa fa-calendar"></i>
											</button>
											<ul class="dropdown-menu" role="menu">
												<li ng-repeat="timeperiod in allTimePeriods"
													ng-click="selectTimePeriod(timeperiod);"><a
													href="">{{timeperiod.timeperiod}}</a></li>
											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row marginFrmbottom">
							<div class="col-md-12">
								<button ng-click="submitForm()">{{selectedReportType == 'Raw Report' ? 'Generate': 'Submit'}}</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
		<section class="tableSection" ng-if="tableData.length" id="reportTable">
			<div class="container">
				<div class="searchedDataForTable">
					<h5 style="font-size:18px;">
						Data Search Result
						<div ng-if="tableData.length" 
						style="float: right; margin-top: -15px; cursor: pointer;"
						 data-toggle="tooltip" title="Download Excel">
						 <img ng-click="exportTableData('dataTable')" style="width: 35px;" alt="" src="resources/images/icons/icon_download_excel.svg"></div>
					</h5>
				</div>
				<div class="tableMargin table-responsive" style="width: 100%;margin-bottom: 200px;">
					<table items="tableData" show-filter="true" cellpadding="0"
						cellspacing="0" border="0" class="dataTable table table-striped report"
						id="dataTable">
						<thead>
							<tr>
								<th ng-repeat="column in columns" ng-class="sortSelectedColumn == column ? 'sortSelected':''"
									style="position: relative;">{{column}}
									<div class="sorting1" ng-click="order(column); selectSortColumn(column)">
										<i class="fa fa-caret-up asc" aria-hidden="true"
											ng-class="{'hiding': !(sortType != column || sortReverse == true)}"></i>
										<i class="fa fa-caret-down dsc" aria-hidden="true"
											ng-class="{'hiding': !(sortType != column || sortReverse == false)}"></i>
									</div>
								</th>
							</tr>
						</thead>
						<tbody>
							<tr
								ng-repeat="rowData in tableData | orderBy:filterType:sortReverse">
								<td ng-repeat="column in columns | filter:removeRowId" ng-click="$index==0 && rowData.DistrictId != 0?getDistrictSummary(rowData): ''"
									sortable="'{{rowData.column}}'">{{filterFloat(rowData[column])}}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</section>
	</div>
	<!-- Modal for division table -->
	<div id="districtTable" data-keyboard="false" data-backdrop="static"  class="modal fade" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content -->
			<div class="modal-content" style="margin-top:-19px;">
				<div class="modal-body">
					
			<button type="button" id="excelDownloadBtn" style="padding: 0 13px 0 2px; margin-bottom: 10px;"
										class="btn excelDownloadBtn" ng-if="districtTableData.length"
										 ng-click="exportTableData('districtDataTable')"
										>
										<img style="width: 37px;" alt="" src="resources/images/icons/icon_download_excel.svg"> &nbsp; Download Excel
									</button>
			<button type="button" class="close" data-dismiss="modal">×</button>
					<div class=" table-responsive" style="width: 100%">
						<table items="tableData" show-filter="true" cellpadding="0"
							cellspacing="0" border="0" class="dataTable table table-bordered modalTable"
							id="districtDataTable">
							<thead>
								<tr>
									<th ng-repeat="column in districtTableColumns"
										ng-click="order(column)" style="position: relative;">{{column}}
										<div class="sorting1">
											<i class="fa fa-caret-up asc" aria-hidden="true"
												ng-class="{'hiding': !(sortType != column || sortReverse == true)}"></i>
											<i class="fa fa-caret-down dsc" aria-hidden="true"
												ng-class="{'hiding': !(sortType != column || sortReverse == false)}"></i>
										</div>
									</th>
								</tr>
							</thead>
							<tbody>
								<tr
									ng-repeat="rowData in districtTableData | orderBy:filterType:sortReverse">
									<td ng-repeat="column in districtTableColumns"
										sortable="'{{rowData.column}}'">{{filterFloat(rowData[column])}}</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Modal for error message -->
	<div id="errorMessage" data-keyboard="false" data-backdrop="static" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content -->
			<div class="modal-content">
				<div class="modal-body text-center">
					<h3>{{errorMsg}}</span></h3>
					<button type="button" class="btn errorOk" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>	
	<!--end of thematic and chklist  -->
	<jsp:include page="fragments/footer.jsp"></jsp:include>
	<script type="text/javascript"
		src="resources/js/angularServices/services.js"></script>
	<script type="text/javascript"
		src="resources/js/angularControllers/reportController.js"></script>
	<script type="text/javascript">
		var app = angular.module("reportApp", []);
		var myAppConstructor = angular.module("reportApp");
		myAppConstructor.controller("ReportController", reportController);
		myAppConstructor.service('allServices', allServices);
	</script>
	<script type="text/javascript">
		$("#msgBox").show().delay(2000).fadeOut(400);
	</script>
	<script type="text/javascript">
$(document).ready(function () {
    $(document).click(function (event) {
        var clickover = $(event.target);
        var _opened = $(".navbar-collapse").hasClass("navbar-collapse in");
        if (_opened === true && !clickover.hasClass("navbar-toggle")) {
            $("button.navbar-toggle").click();
        }
    });
});
</script>
</body>

</html>