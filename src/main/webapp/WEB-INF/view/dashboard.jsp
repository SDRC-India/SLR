<!-- 
@author Laxman (laxman@sdrc.co.in)
 -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html ng-app="dashboardApp">
<head>

<title>SLR-Dashboard</title>
<link rel="shortcut icon" href="resources/images/icons/slr_favicon.png"
	type="image/x-icon">
<link rel="icon" href="resources/images/icons/slr_favicon.png"
	type="image/x-icon">
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/css/lightbox.css">
<!-- <link rel="stylesheet" href="resources/css/blueimp-gallery.min.css"> -->
<link href="https://fonts.googleapis.com/css?family=Questrial"
	rel="stylesheet">
<link rel="stylesheet" href="resources/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/customLoader.css">
<link
	href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css"
	rel="Stylesheet"></link>
<%-- <spring:url value="/webjars/jquery-ui/1.10.3/themes/base/jquery-ui.css"
	var="jQueryUiCss" /> --%>
<link href="${pageContext.request.contextPath}/webjars/jquery-ui/1.10.3/themes/base/jquery-ui.css" rel="stylesheet"></link>
<link rel="stylesheet" href="resources/css/style.css">



</head>

<body ng-controller="DashboardController" ng-cloak>
	<jsp:include page="fragments/header.jsp"></jsp:include>
	<div id="errMsg" class="text-center">
		<serror:Error id="msgBox" errorList="${formError}"
			cssInfClass="${className}">
		</serror:Error>
	</div>
	<div id="mymain" class="container">
		<section id="map-section">
			<div class="row mar-bot-10" style="margin: 30px auto;">
				<div class="col-md-12">
					<div class="row">

						<div class="col-md-12">
							<div
								style="position: fixed; top: 115px; right: -114px;; z-index: 1000;"
								class="download-container"
								ng-class="{'remove': !chartData.length &amp;&amp; !pieChartData.length}">
								<button type="button" id="pdfDownloadBtn"
									style="padding: 0 13px 0 2px;" class="btn pdfDownloadBtn"
									title="Download PDF" ng-click="sdrc_export()">
									<img style="width: 37px;" alt=""
										src="resources/images/icons/icon_download_pdf.svg">
									&nbsp; Download PDF
								</button>
							</div>
							<div
								style="position: fixed; top: 175px; right: -121px; z-index: 1000;"
								class="download-container-excel"
								ng-class="{'remove': !chartData.length &amp;&amp; !pieChartData.length}">
								<button type="button" id="excelDownloadBtn"
									style="padding: 0 13px 0 2px;" class="btn excelDownloadBtn"
									title="Download Excel" ng-click="sdrc_export()">
									<img style="width: 37px;" alt=""
										src="resources/images/icons/icon_download_excel.svg">
									&nbsp; Download Excel
								</button>
							</div>
							<div class="row selection-bar">
								<div class="col-md-4 col-sm-4 col-xs-6 timeperiodWidth">
									<div class="select-container text-center">
										<div class="input-group">
											<input type="text" placeholder="Time Period *"
												id="timeperiod" class="form-control not-visible-input"
												name="indicator" readonly=""
												ng-model="selectedTimePeriod.timeperiod">
											<div class="input-group-btn" style="position: relative;">
												<button data-toggle="dropdown"
													class="btn btn-color dropdown-toggle" type="button">
													<i class="fa fa-calendar"></i>
												</button>
												<ul class="dropdown-menu timeperioddropdown" role="menu">
													<li ng-repeat="timeperiod in allTimePeriods"
														ng-click="selectTimePeriod(timeperiod); getPushpinData(); getSpiderData();"><a
														href="">{{timeperiod.timeperiod}}</a></li>
												</ul>
											</div>
										</div>
									</div>
								</div>
								<div class="col-md-4 col-sm-4 col-xs-6 indicatorWidth">
									<div class="select-container text-center">
										<div class="input-group" style="margin: auto;">
											<input type="text" placeholder="Indicator *" id="indicator"
												class="form-control not-visible-input" name="indicator"
												readonly="" ng-model="selectedIndicator.label">
											<div class="input-group-btn" style="position: relative;">
												<button data-toggle="dropdown"
													class="btn btn-color dropdown-toggle" type="button">
													<i class="fa fa-list"></i>
												</button>
												<ul class="dropdown-menu" role="menu">
													<li ng-repeat="indicator in selectedSector.formXpathModel"
														ng-click="selectIndicator(indicator); getPushpinData(); "><a
														href="">{{indicator.label}}</a></li>
												</ul>
											</div>
										</div>
									</div>
								</div>
								<div class="col-md-4 col-sm-4 col-xs-6 stateWidth mobileiew">
									<div class="select-container dist-list text-center">
										<div class="input-group" style="float: right;">
											<input type="text" placeholder="Districts *" id="Dist"
												class="form-control not-visible-input" name="Dist"
												readonly="" ng-model="selectedDistrict.areaName">
											<div class="input-group-btn" style="position: relative;">
												<button data-toggle="dropdown"
													class="btn btn-color dropdown-toggle" type="button">
													<i class="fa fa-list"></i>
												</button>
												<ul class="dropdown-menu stateDropdown" role="menu">
													<li ng-repeat="district in allDistricts"
														ng-class="{'allDistDropdown': $index == 0}"
														ng-click="pushpinFilterWord = ''; resetPushpinDataCallDone(); selectDistrict(district); getPushpinData(); getSpiderData();"><a
														href="">{{district.areaName}}</a></li>
												</ul>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- 				</div> -->

					<!-- 				<div class="row mar-bot-10"> -->
					<div class="row" id="googleMap" style="background-color: #FFF;">
						<div class="col-md-12"
							style="margin: 10px auto 95px; background-color: #FFF;">
							<div id="mapContainerId">

								<!-- <div class="col-md-4 area_level" data-html2canvas-ignore="true">
																			<h4 class="bar_style">Vector View</h4>
										<ul class="list-unstyled list-inline arealist">
											<li
												ng-click="resetPushpinDataCallDone(); selectParentSector(parentSector)"
												ng-repeat="parentSector in parentSectors"><a
												ng-class="{active:selectedParentSector.formId == parentSector.formId}"
												href="#"> <i ng-if="parentSector.formId == 43"
													class="fa fa-map-marker"></i> <i
													ng-if="parentSector.formId == 45" class="fa fa-plus"></i> <i
													ng-if="parentSector.formId == 44" class="fa fa-star"></i>
													{{parentSector.label}}
											</a></li>

										</ul>
									</div> -->
								<div style="position: relative;">
									<section class="legends"
										style="min-width: 210px; max-width: 300px;">

										<ul style="padding-left: 10px;">
											<li class="legend_list" style="margin-bottom: 28px;">
												<h4>{{selectedIndicator.label}}</h4>
											</li>
											<li class="legend_list" style="margin-bottom: 2px;">
												<h4>Facility Covered&nbsp:&nbsp{{map.markers.length}}</h4>
											</li>
											<li class="legend_list ng-scope"><span
												class="legend_key ">0 - 59.9</span> <span
												class="firstslices legnedblock"> </span> (<span
												style="color: red;">{{redMarkers}}</span>)</li>
											<!-- end ngRepeat: legend in legends -->
											<li class="legend_list "><span class="legend_key ">60.0
													- 79.9</span> <span class="secondslices legnedblock"> </span> (<span
												style="color: orange;">{{orangeMarkers}}</span>)</li>
											<!-- end ngRepeat: legend in legends -->
											<li class="legend_list "><span
												class="legend_key ng-binding">80.0 - 100</span> <span
												class="fourthslices legnedblock"> </span> (<span
												style="color: green;">{{greenMarkers}}</span>)</li>
										</ul>
									</section>
									
									<section class="selectSectorMap">
										<div class="select-container text-center">
											<div class="input-group" style="margin: auto;">
												<input type="text" placeholder="Sector *" id="Sector"
													class="form-control not-visible-input" name="sector"
													readonly="" ng-model="selectedSector.sectorName">
												<div class="input-group-btn" style="position: relative;">
													<button data-toggle="dropdown"
														class="btn btn-color dropdown-toggle" type="button">
														<i class="fa fa-chevron-down"></i>
													</button>
													<ul class="dropdown-menu sectrsDropdownlist" role="menu">
														<li ng-repeat="sector in sectors"
															ng-click="selectSector(sector); getPushpinData();getSpiderData();"><a
															href="">{{sector.sectorName}}</a></li>
													</ul>
												</div>
											</div>
										</div>
									</section>
									<section class="searchFacility">
										<div class="select-container text-center">
											<div class="input-group" style="margin: auto;">
												<input type="text" placeholder="Search Facility"
													id="searchDashboard" class="form-control not-visible-input"
													name="searchFacility" ng-model="pushpinFilterWord"
													ng-keyup="searchNodeDashboard()">
												<div class="input-group-btn" style="position: relative;">
													<button data-toggle="dropdown" class="btn btn-color"
														type="button">
														<i class="fa fa-search"></i>
													</button>
												</div>
											</div>
										</div>
									</section>
									<google-map center="map.center" zoom="map.zoom"
										draggable="true"> <markers class="pushpin"
										models="map.markers" coords="'self'" icon="'icon'"
										events="map.events"> <windows show="'showWindow'"
										closeClick="'closeClick'" options='pixelOffset'>
									<p ng-non-bindable
										style="width: 130px; height: 30px; font-size: 15px; color: #313e4d; display: inline;">
										<strong>{{title}}</strong><br> <strong>Score:{{dataValue}}%</strong>
									</p>
									</windows> </markers> <polygon static="true"
										ng-repeat="p in polygons track by p.id" path="p.path"
										stroke="p.stroke" visible="p.visible" geodesic="p.geodesic"
										fill="p.fill" fit="false" editable="p.editable"
										draggable="p.draggable"></polygon> </google-map>
									<section class="pushpinDesc">
										<ul style="margin-bottom: 0;">
											<li class="pin_list"><span class="legnedblock"> <img
													src="resources/images/pushpins/th-gray.png"></span> <span
												class="legend_key ">Teaching Hospital</span></li>
											<li class="pin_list"><span class="legnedblock"> <img
													src="resources/images/pushpins/dh-gray.png"></span> <span
												class="legend_key ">District Hospital</span></li>
											<li class="pin_list"><span class="legnedblock"> <img
													src="resources/images/pushpins/chc-gray.png"></span> <span
												class="legend_key ">Community Health Centre</span></li>
											<li class="pin_list"><span class="legnedblock"> <img
													src="resources/images/pushpins/ah-gray.png"></span> <span
												class="legend_key ">Area Hospital</span></li>
											<li class="pin_list"><span class="legnedblock"> <img
													src="resources/images/pushpins/mhc-gray.png"></span> <span
												class="legend_key ">Maternal and Child Health Centre</span>
											</li>
											<li class="pin_list"><span class="legnedblock"> <img
													src="resources/images/pushpins/phc-gray.png"></span> <span
												class="legend_key ">Primary Health Centre</span></li>
											<li class="pin_list"><span class="legnedblock"> <img
													src="resources/images/pushpins/24x7-gray.png"></span> <span
												class="legend_key ">24x7 PHC</span></li>
											<!-- 												<li class="pin_list">
													<span class="legnedblock"> <img src="resources/images/pushpins/sbbm-gray.png"></span>
													<span class="legend_key ">SBBM</span> 
												</li> -->
											<li class="pin_list"><span class="legnedblock"> <img
													src="resources/images/pushpins/uphc-gray.png"></span> <span
												class="legend_key ">UPHC</span></li>
										</ul>
									</section>
								</div>
							</div>
						</div>
					</div>

					<!-- Photos on click of push pin @swarna-->
					<div id="photoGallery" ng-show="beforePhotos.length > 0">
						<div class="col-md-6 borderRight contain-box">
							<h4 class="beforephotos">Before</h4>
							<div class="col-md-6"
								ng-repeat="photo in beforePhotos track by $index"
								style="margin: 10px 0">
								<a href="{{photo}}"
									dashimage class="img-responsive example-image-link" data-lightbox="example-set" > <img class="example-image img img-responsive"  alt="" src="{{photo}}"
									style="width: 100%; height: 132px;">
								</a>
							</div>
						</div>
						<div class="col-md-6 backgroundclr contain-box"
							ng-show="afterPhotos.length > 1">
							<h4 class="afterphotos">After</h4>
							<div class="col-md-6"
								ng-repeat="photo in afterPhotos track by $index"
								style="margin: 10px 0">
								<a href="{{photo}}"
									dashimage class="img-responsive example-image-link" data-lightbox="example-set1"> <img class="example-image img img-responsive" alt=""
									src="{{photo}}" style="width: 100%; height: 132px;">
								</a>
							</div>
						</div>
						<div class="col-md-6 backgroundclr"
							ng-show="afterPhotos.length == 1">
							<h4 class="afterphotos">After</h4>
							<a href=""> <img alt=""
								src="resources/images/nodataavailable.png"
								style="width: 70%; margin-top: 45px;">
							</a>
						</div>
<!-- 							<div id="blueimp-gallery" class="blueimp-gallery"> -->
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
<!-- 											<button type="button" class="btn btn-primary nextbtn next"> -->
<!-- 												Next <i class="glyphicon glyphicon-chevron-right"></i> -->
<!-- 											</button> -->
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</div>	 -->
						
						<!-- <div id="blueimp-gallery-after" class="blueimp-gallery">
							<div class="slides"></div>
							<h3 class="title"></h3>
							<a class="prev">&lt;</a> <a class="next">&gt;</a> <a
								class="close">×</a> <a class="play-pause"></a>
							<ol class="indicator"></ol>
							The - dialog, which will be used to wrap the lightbox content
							<div class="modal fade">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" aria-hidden="true">&times;</button>
											<h4 class="modal-title"></h4>
										</div>
										<div class="modal-body next mapimage"></div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default pull-left prev">
												<i class="glyphicon glyphicon-chevron-left"></i> Previous
											</button>
											<button type="button" class="btn btn-primary nextbtn next">
												Next <i class="glyphicon glyphicon-chevron-right"></i>
											</button>
										</div>
									</div>
								</div>
							</div>
						</div> -->
					</div>

					<!-- end Photos on click of push pin -->
					<div class="row">
						<div class="col-md-12 chrtHead"
							style="margin-bottom: 5px; margin-top: 25px;">
							<div class="chart-head">
								<h5 class="text-center" style="display: inline-block;">{{selectedSector.sectorName}}
									&nbsp; &#x3e; &nbsp; {{selectedDistrict.areaName}}
									{{lastVisitDataId ? "&nbsp; &#x3e; &nbsp;" : ""}}
									{{selectedPushpin}} &nbsp; &#x3e; &nbsp;
									{{selectedTimePeriod.timeperiod}}</h5>
								<div class="selection-bar" style="float: right;">
									<div class="select-container text-center" style="float: right;">
										<div class="input-group">
											<input type="text" placeholder="Time Period *"
												id="timeperiod" class="form-control not-visible-input"
												name="indicator" readonly=""
												ng-model="selectedChartTimePeriod.timeperiod">
											<div class="input-group-btn" style="position: relative;">
												<button data-toggle="dropdown"
													class="btn btn-color dropdown-toggle" type="button">
													<i class="fa fa-calendar"></i>
												</button>
												<ul class="dropdown-menu" role="menu">
													<h5 style="color: #cf4d46; padding: 0 10px;"
														ng-if="allTimePeriods.length < 2">No timeperiod
														available for comparison</h5>
													<li
														ng-repeat="timeperiod in allTimePeriods | filter:compareChartTimeperiod"
														ng-click="selectChartTimePeriod(timeperiod);getSpiderData()"><a
														href="">{{timeperiod.timeperiod}}</a></li>
												</ul>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12 padding-right-0 padding-left-0"
							id="chartContainerId">
							<div class="tabs mar-top-15">
								<ul id="myTab" class="nav nav-tabs" role="tablist"
									data-html2canvas-ignore="true">

									<li ng-repeat="subSector in subSectors"
										ng-class="{'active': $index == 1}"><a data-toggle="tab"
										role="tab" ng-click="selectSubSector(subSector); "
										href="#home">{{subSector}}</a></li>

								</ul>
								<div class="row">
									<div class="col-md-6">
										<div id="myTabContent" class="tab-content">
											<div id="home" class="tab-pane fade active in">
												<sdrc-spider></sdrc-spider>
												
												<!-- legends in spider chart @Swarna -->
												
												<div class="row legendSpider">
													<div class="col-md-12 text-center svgInline">
														<div class="inlinelegendData"
															ng-if="spiderdata.dataCollection.length > 0">
<!-- 															<svg width="20" height="20"> -->
<!--   															<rect width="20" height="20" style="fill:#1F77B4;" /> -->
<!-- 														</svg> -->
															<img src="resources/images/legendImage/Blue.png" width="20" height="20">
															<div class="timeperiodLegend">{{selectedTimePeriod.timeperiod}}</div>
														</div>
														<div class="inlinelegendData"
															ng-if="spiderdata.dataCollection.length > 1">
<!-- 															<svg width="20" height="20"> -->
<!--   															<rect width="20" height="20" style="fill:#FF8C26;" /> -->
<!-- 														</svg> -->
															<img src="resources/images/legendImage/Orange.png" width="20" height="20">
															<div class="timeperiodLegend">{{selectedChartTimePeriod.timeperiod}}</div>
														</div>
													</div>
												</div>
												
												<!-- end legends in column chart @Swarna -->
												
											</div>
										</div>
									</div>
									<div class="col-md-6">
										<div id="myTabContent1" class="tab-content">
											<div id="home1" class="tab-pane fade active in">
												<sdrc-bar-chart></sdrc-bar-chart>
												
												<!-- legends in column chart @Swarna -->
												
												<div class="legendWithAverage"
													ng-if="spiderdata.dataCollection.length==1">
													<img class="img-responsive"
														src="resources/images/legendImage/legendWithavrage.png">
												</div>
												<div class="withoutAverage"
													ng-if="spiderdata.dataCollection.length>1">
													<img class="img-responsive"
														src="resources/images/legendImage/legendWithoutAverage.jpg">
												</div>
												<!-- end legends in column chart -->
											</div>
										</div>
									</div>
									
									<div class="row">
						<div class="col-md-12"
							style="margin-bottom: 5px;">
							<div class="chart-head1">
								<h4 class="text-center" style="display: inline-block;">
												Method Of Computation
									</h4>
									<p>{{methodOfComputation}}
													</p>
							</div>
						</div>
									
									<div class="col-md-12" style="margin-bottom: 139px;" ng-if="selectedSubSector != 'Component'">
										<section class="facilityShortName">
											<ul style="margin-bottom: 0;">
												<li class="pin_list"><span class="legnedblock"><b>
															CC :</b></span> <span class="legend_key ">Cross Cutting</span></li>
												<li class="pin_list"><span class="legnedblock"><b>
															HDU :</b></span> <span class="legend_key ">High Dependency
														Unit</span></li>
												<li class="pin_list"><span class="legnedblock"><b>
															HR :</b></span> <span class="legend_key ">Human Resource</span></li>
												<li class="pin_list"><span class="legnedblock"><b>
															LR :</b></span> <span class="legend_key ">Labour Room</span></li>
												<li class="pin_list"><span class="legnedblock"><b>
															MW :</b></span> <span class="legend_key ">Maternity Ward</span></li>
												<li class="pin_list"><span class="legnedblock"><b>
															NC :</b></span> <span class="legend_key ">Neonatal Care</span></li>
												<li class="pin_list"><span class="legnedblock"><b>
															OT :</b></span> <span class="legend_key ">Operation Theatre</span></li>
												<li class="pin_list"><span class="legnedblock"><b>
															PORW :</b></span> <span class="legend_key ">Post OP and
														Recovery Ward</span></li>
												<li class="pin_list"><span class="legnedblock"><b>
															PT :</b></span> <span class="legend_key ">Pre-Triage</span></li>
												<li class="pin_list"><span class="legnedblock"><b>
															TRA :</b></span> <span class="legend_key ">Training</span></li>
												<li class="pin_list"><span class="legnedblock"><b>
															TRI :</b></span> <span class="legend_key ">Triage</span></li>

											</ul>
										</section>
									</div>
									<div class="col-md-12" style="margin-bottom: 139px;"  ng-if="selectedSubSector == 'Component'">
										<section class="facilityShortName">
											<ul style="margin-bottom: 0;">
												<li class="pin_list"><span class="legnedblock"><b>
															B :</b></span> <span class="legend_key ">Infrastructure</span></li>
												<li class="pin_list"><span class="legnedblock"><b>
															C :</b></span> <span class="legend_key ">Equipment and
														Accessories for Labour Rooms</span></li>
												<li class="pin_list"><span class="legnedblock"><b>
															D :</b></span> <span class="legend_key ">Equipment and
														Accessories for ISOLATION/SEPTIC Labour Rooms</span></li>
											</ul>
										</section>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="modal fade" id="noDataModall" role="dialog"
						style="margin-top: 12%;">
						<div class="modal-dialog">

							<!-- Modal content-->
							<div class="modal-content">
								<div class="modal-header"
									style="background-color: #e8a05d; color: #fff;">
									<button type="button" class="close" data-dismiss="modal">&times;</button>
									<h4 class="modal-title" style="text-align: center;">Info</h4>
								</div>
								<div class="modal-body">
									<p
										style="text-align: center; font-size: 20px; margin-bottom: -10px;">NO
										DATA AVAILABLE</p>
								</div>
								<div class="modal-footer" style="text-align: center;">
									<button type="button"
										class="btn btn-default btn-view text-center"
										data-dismiss="modal">Ok</button>
								</div>
							</div>

						</div>
					</div>
					<!-- //end  -->
		</section>
	</div>
	<!-- Modal for error message -->
	<div id="errorMessage" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content -->
			<div class="modal-content">
				<div class="modal-body text-center">
					<h3>
						{{errorMsg}}</span>
					</h3>
					<button type="button" class="btn errorOk" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<!--end of thematic and chklist  -->

	<!-- before after image open modal -->

<!-- 	<div class="modal fade" id="imagemodal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<img src="{{photo}}" class="imagepreview" style="width: 100%;">
				</div>
			</div>
		</div>
	</div> -->

	<!--end  before after image open modal -->
<!-- 	<script src="resources/js/photo-gallery.js"></script> -->
	<jsp:include page="fragments/footer.jsp"></jsp:include>
	<script src="resources/js/lightbox-plus-jquery.min.js"></script>
<%-- 	<spring:url value="/webjars/jquery/2.0.3/jquery.min.js" var="jQuery" /> --%>
	<script src="${pageContext.request.contextPath}/webjars/jquery/2.0.3/jquery.min.js"></script>
	<%-- <spring:url value="/webjars/bootstrap/3.1.1/js/bootstrap.min.js"
		var="bootstrapjs" /> --%>
	<script src="${pageContext.request.contextPath}/webjars/bootstrap/3.1.1/js/bootstrap.min.js"></script>
	<script src="resources/js/jquery-ui.js"></script>
	<script
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBedTd_YjXAiOM8I34K2MRUzqso2wu0wlA&v=3.31"
		type="text/javascript"></script>
	<script src="resources/js/lodash.min.js" type="text/javascript"></script>
<%-- 	<spring:url value="/webjars/d3js/3.4.6/d3.min.js" var="d3js" />
 --%>	
 <script src="${pageContext.request.contextPath}/webjars/d3js/3.4.6/d3.min.js"></script>
	<%-- <spring:url value="/webjars/angularjs/1.2.16/angular.min.js"
		var="angularmin" /> --%>
	<script src="${pageContext.request.contextPath}/webjars/angularjs/1.2.16/angular.min.js" type="text/javascript"></script>
	<script src="resources/js/ng-table.min.js" type="text/javascript"></script>
	<script src="resources/js/ng-table-export.js" type="text/javascript"></script>
	<script src="resources/js/html2canvas.js" type="text/javascript"></script>
	<script src="resources/js/sdrc.export.js" type="text/javascript"></script>
	<%-- <spring:url value="/webjars/angularjs/1.2.16/angular-animate.min.js"
		var="angularaAnimatemin" /> --%>
	<script src="${pageContext.request.contextPath}/webjars/angularjs/1.2.16/angular-animate.min.js" type="text/javascript"></script>
	<script src="resources/js/angular-google-maps.min.js"
		type="text/javascript"></script>
	<script type="text/javascript"
		src="resources/js/angularServices/services.js"></script>
	<script type="text/javascript"
		src="resources/js/angularControllers/dashboardController.js"></script>
		
<!-- 		<script src="resources/js/blueimp.gallery.min.js"></script> -->
<!-- <script src="resources/js/bootstrapimage.gallery.js"></script> -->
	<script type="text/javascript">
		var app = angular.module("dashboardApp", [ 'ngAnimate', 'google-maps',
				'ngTable' ]);
		var myAppConstructor = angular.module("dashboardApp");
		myAppConstructor.controller("DashboardController", dashboardController);
		myAppConstructor.service('allServices', allServices);
	</script>
<!-- 	<script type="text/javascript"> -->
		
<!-- 	</script> -->
	<script type="text/javascript"
		src="resources/js/angularDirectives/directives.js"></script>
	<script type="text/javascript">
		$("#msgBox").show().delay(2000).fadeOut(400);
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			openFooter();
			sdrc_export.export_pdf("", "pdfDownloadBtn");
			sdrc_export.export_excel("", "excelDownloadBtn");
		});
	</script>

</body>

</html>