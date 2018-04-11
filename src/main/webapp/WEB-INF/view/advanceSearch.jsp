<!-- 
@author Laxman (laxman@sdrc.co.in)
 -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html ng-app="advanceSearchApp">
<head>

<title>SLR-Search</title>
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
<%-- <spring:url value="${pageContext.request.contextPath}/webjars/bootstrap/3.1.1/js/bootstrap.min.js"
	var="bootstrapjs" /> --%>
<script src="${pageContext.request.contextPath}/webjars/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<%-- <spring:url value="/webjars/angularjs/1.2.16/angular.min.js"
	var="angularmin" /> --%>
<script src="${pageContext.request.contextPath}/webjars/angularjs/1.2.16/angular.min.js" type="text/javascript"></script>
</head>

<body ng-controller="AdvanceSearchController" ng-cloak>
	<jsp:include page="fragments/header.jsp"></jsp:include>
	<div id="errMsg" class="text-center">
		<serror:Error id="msgBox" errorList="${formError}"
			cssInfClass="${className}">
		</serror:Error>
	</div>
	<div id="mymain" class="container report-height">
		<section id="form-section">
			<div class="row mar-bot-10" style="margin-top: 30px;">
				<div class="col-md-12">
					<div class="form-container">
						<div class="row">
							<div class="col-md-6">
								<div class="select-container dist-list text-center">
									<div class="input-group" style="margin: auto;">
										<input type="text" placeholder="Facility Type *" id="level"
											class="form-control not-visible-input" name="level"
											readonly="" ng-model="selectedLevel.sectorName">
										<div class="input-group-btn" style="position: relative;">
											<button data-toggle="dropdown"
												class="btn btn-color dropdown-toggle" type="button">
												<i class="fa fa-list"></i>
											</button>
											<ul class="dropdown-menu" role="menu">
												<li ng-repeat="level in levels"
													ng-click="selectLevel(level);"><a href="">{{level.sectorName}}</a></li>
											</ul>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="select-container dist-list text-center">
									<div class="input-group" style="margin: auto;">
										<input type="text" placeholder="Component / Service Area *" id="criteria"
											class="form-control not-visible-input" name="criteria"
											readonly="" ng-model="selectedCriteria.label">
										<div class="input-group-btn" style="position: relative;">
											<button data-toggle="dropdown"
												class="btn btn-color dropdown-toggle" type="button">
												<i class="fa fa-list"></i>
											</button>
											<ul class="dropdown-menu" role="menu">
												<li ng-repeat="criteria in selectedLevel.formXpathModel"
													ng-click="selectCriteria(criteria);"><a href="">{{criteria.label}}</a></li>
											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="select-container dist-list text-center">
									<div class="input-group" style="float: right;">
										<input type="text" placeholder="District *" id="pattern"
											class="form-control not-visible-input" name="pattern"
											readonly="" ng-model="selectedDistrict.areaName">
										<div class="input-group-btn" style="position: relative;">
											<button data-toggle="dropdown"
												class="btn btn-color dropdown-toggle" type="button">
												<i class="fa fa-list"></i>
											</button>
											<ul class="dropdown-menu" role="menu">
												<li ng-repeat="district in districts"
													ng-click="selectDistrict(district)"><a href="">{{district.areaName}}</a></li>
											</ul>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="select-container dist-list text-center">
									<div class="input-group" style="float: right;">
										<input type="text" placeholder="Timeperiod *" id="pattern"
											class="form-control not-visible-input" name="pattern"
											readonly="" ng-model="selectedTimePeriod.timeperiod">
										<div class="input-group-btn" style="position: relative;">
											<button data-toggle="dropdown"
												class="btn btn-color dropdown-toggle" type="button">
												<i class="fa fa-calendar"></i>
											</button>
											<ul class="dropdown-menu" role="menu">
												<li ng-repeat="timeperiod in timeperiods"
													ng-click="selectTimePeriod(timeperiod)"><a href="">{{timeperiod.timeperiod}}</a></li>
											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>
				<div class="row">
				<div class="col-md-6">
								<div class="select-container dist-list text-center">
									<div class="input-group" style="float: right;">
										<input type="text" placeholder="Rule *" id="pattern"
											class="form-control not-visible-input" name="pattern"
											readonly="" ng-model="selectedPattern.name">
										<div class="input-group-btn" style="position: relative;">
											<button data-toggle="dropdown"
												class="btn btn-color dropdown-toggle" type="button">
												<i class="fa fa-list"></i>
											</button>
											<ul class="dropdown-menu" role="menu">
												<li ng-repeat="pattern in allPatterns"
													ng-click="selectPattern(pattern)"><a href="">{{pattern.name}}</a></li>
											</ul>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="select-container dist-list text-center"
									style="width: 100%">
									<div class="input-group" style="float: right; width: 100%;">
										<input type="text" style="width: 100%; max-width: 100%;"
											placeholder="Percentage Value *" id="percentValue"
											class="form-control not-visible-input" name="Dist"
											onkeyup="this.value = minmax(this.value, 0, 100)"
											ng-model="percentValue">
									</div>
								</div>
							</div>
				</div>		
						
						<div class="row">
							<div class="col-md-12">
								<button ng-click="viewData()">View Data</button> 
							</div>
						</div>
						<div></div>
					</div>
				</div>
			</div><br><br><br>&nbsp;&nbsp;<br><br>
		</section>
		<section class="tableSection" ng-if="tableData.length">
			<div class="col-md-12 col-xs-12 searchedDataForTable">
				<div class="col-md-4 col-xs-4">
					<h5 class="leftMargin" style="font-size:18px;">Data Search Result</h5>
				</div>
				<div class="col-md-4 col-xs-4 totaldataCenter"><h5>Total no. of Facilities :
					{{tableData.length}}</h5></div>
				<div class="col-md-4 excelPosition" ng-if="tableData.length"
					>
					<img ng-click="exportTableData('dataTable')" class="excelbtn" data-toggle="tooltip" title="Download Excel" 
					data-placement="top"
						alt="" src="resources/images/icons/icon_download_excel.svg">
						
				</div>
			</div>
			<div class="container">
				<div class="tableMargin table-responsive" style="width: 100%;margin-bottom:200px;">
					<table items="tableData" show-filter="true" cellpadding="0"
						cellspacing="0" border="0" class="dataTable table table-striped advanceSearch"
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
								<td ng-repeat="column in columns | filter:removeRowId"
									sortable="'{{rowData.column}}'">{{filterFloat(rowData[column])}}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</section>
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
		src="resources/js/angularControllers/advanceSearchController.js"></script>
	<script type="text/javascript">
		var app = angular.module("advanceSearchApp", []);
		var myAppConstructor = angular.module("advanceSearchApp");
		myAppConstructor.controller("AdvanceSearchController",
				advanceSearchController);
		myAppConstructor.service('allServices', allServices);
	</script>
	<script type="text/javascript">
		$("#msgBox").show().delay(2000).fadeOut(400);
	</script>
	<script>
		$(document).ready(function() { 
 			 $('[data-toggle="tooltip"]').tooltip(); 
 		});
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