var sdrc_export = new function() {
	"use strict";
	this.root = "http://localhost/";
	this.init = function(rootUri) {
		this.root = rootUri;
		console.log("in init");
	};
	// Please give container Id and export pdf btn ids
	this.export_pdf = function(containerId, exportPdfbtn) {
		var serverURL;
		$("#" + exportPdfbtn).on("click",function(event) {
			var $scope = angular.element("body").scope();
			event.preventDefault();
			d3.selectAll("svg").attr("version", 1.1).attr("xmlns",
					"http://www.w3.org/2000/svg");
			html2canvas($("#googleMap"), {
	            useCORS: true,
//	                        allowTaint:true,
	            onrendered: function(canvas) {
//	                theCanvas = canvas;
//	                document.body.appendChild(canvas);

	                // Convert and download as image 
//	                Canvas2Image.saveAsPNG(canvas); 
	                $scope.imgMap = canvas.toDataURL('image/png', 1.0);
	                $scope.windowsHeight=$(window).width()
	                sendData();
	                // Clean up 
	                //document.body.removeChild(canvas);
	            }
	        });
			var spiderSvg = $("sdrc-spider").html().replace(/\&nbsp;/g, " ");
			var columnSvg = $("sdrc-bar-chart").html().replace(/\&nbsp;/g, " ");
			var svgs = [];
			 
			
			$(".loader").show();
			
			var serverURL = "exportToPdf?stateId=" + 3 + 
			"&districtId=" + $scope.selectedDistrict.areaId +
			"&facilityTypeId=" + $scope.selectedSector.sectorId +
			"&lastVisitDataId=" + $scope.lastVisitDataId +
			"&formXpathScoreMappingId=" + $scope.selectedIndicator.formXpathScoreMappingId + 
			"&xpathName=" + $scope.selectedIndicator.label + 
			"&timeperiodId1=" + $scope.selectedTimePeriod.timePeriodId + 
			"&timeperiodId2=" + $scope.selectedChartTimePeriod.timePeriodId + 
			"&isComponent=" + ($scope.selectedSubSector == "Component");
			
			function sendData(){
				svgs.push(spiderSvg); svgs.push(columnSvg); svgs.push($scope.imgMap);
				svgs.push($scope.windowsHeight);
				$.ajax({
					url : serverURL, 
					method : 'POST',
					data : JSON.stringify(svgs),
					contentType : 'application/json',
					success : function(data) {
						var fileName = {
								"fileName" : data
							};
						if(typeof data == 'string' && data.indexOf("You are not authorized to view this page") != -1){
							$("body").append('<div id="sessionOutMessage" class="modal fade" role="dialog"><div class="modal-dialog"><div class="modal-content"><div class="modal-body text-center"><h3>Session is expired</h3><a href="home" class="btn btn-default errorOk" type="submit">OK</a></div></div></div></div>');
							$("#sessionOutMessage").modal("show");
						}
						else
							$.download("downloadFile/", fileName, 'POST');
					}
				});
			}
			
		});
	};
	this.export_excel = function(containerId, exportExcelbtn) {
		var serverURL;
		$("#" + exportExcelbtn).on("click",function(event) {
			var $scope = angular.element("body").scope();
			event.preventDefault();
			d3.selectAll("svg").attr("version", 1.1).attr("xmlns",
					"http://www.w3.org/2000/svg");
			
			 html2canvas($("#googleMap"), {
		            useCORS: true,
//		                        allowTaint:true,
		            onrendered: function(canvas) {
//		                theCanvas = canvas;
//		                document.body.appendChild(canvas);

		                // Convert and download as image 
//		                Canvas2Image.saveAsPNG(canvas); 
		                $scope.imgMap = canvas.toDataURL('image/jpeg', 1.0);
		                $scope.windowsHeight=$(window).width();
		                sendData();
		                // Clean up 
		                //document.body.removeChild(canvas);
		            }
		        });
			var spiderSvg = $("sdrc-spider").html().replace(/\&nbsp;/g, " ");
			var columnSvg = $("sdrc-bar-chart").html().replace(/\&nbsp;/g, " ");
			var svgs = [];
			
			$(".loader").show();
			
			var serverURL = "exportToExcel?stateId=" + 3 + 
			"&districtId=" + $scope.selectedDistrict.areaId +
			"&facilityTypeId=" + $scope.selectedSector.sectorId +
			"&lastVisitDataId=" + $scope.lastVisitDataId +
			"&formXpathScoreMappingId=" + $scope.selectedIndicator.formXpathScoreMappingId + 
			"&xpathName=" + $scope.selectedIndicator.label + 
			"&timeperiodId1=" + $scope.selectedTimePeriod.timePeriodId + 
			"&timeperiodId2=" + $scope.selectedChartTimePeriod.timePeriodId + 
			"&isComponent=" + ($scope.selectedSubSector == "Component");
			
			function sendData(){
				svgs.push(spiderSvg); svgs.push(columnSvg); svgs.push($scope.imgMap);
				svgs.push($scope.windowsHeight);
				$.ajax({
					url : serverURL, 
					method : 'POST',
					data : JSON.stringify(svgs),
					contentType : 'application/json',
					success : function(data) {
						var fileName = {
								"fileName" : data
							};
							$.download("downloadFile/", fileName, 'POST');
					}
				});
			}
		});
	};
	// download a file
	$.download = function(url, data, method) {
		// url and data options required
		if (url && data) {
			// data can be string of parameters or array/object
			data = typeof data == 'string' ? data : jQuery.param(data);
			// split params into form inputs
			var inputs = '';
			jQuery.each(data.split('&'), function() {
				var pair = this.split('=');
				inputs += '<input type="hidden" name="' + pair[0] + '" value="'	+ pair[1] + '" />';
			});
			// send request
			jQuery(
					'<form action="' + url + '" method="' + (method || 'post')
							+ '">' + inputs + '</form>').appendTo('body')
					.submit().remove();
			$(".loader").css("display", "none");
		}
		;
	this.export_excel = function() {
		alert("excel exported");
	};
	};
};

$(document).ready(function(){
	$("#pdfDownloadBtn").hover(function(){
		
	});
});