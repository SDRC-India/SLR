function reportController($scope, $http, allServices){
	$scope.activeMenu = 'report';
	
	
	
	$scope.allReportTypes = ['Raw Report', 'Summary Report'];
	
	
	
	
	function convert(array) {
		var map = {};
		for (var i = 0; i < array.length; i++) {
			var obj = array[i];
			if (!(obj.sectorId in map)) {
				map[obj.sectorId] = obj;
				map[obj.sectorId].children = [];
			}

			if (typeof map[obj.sectorId].name == 'undefined') {
				map[obj.sectorId].sectorName = String(obj.sectorName);
				map[obj.sectorId].sectorId = obj.sectorId;
				map[obj.sectorId].parentSectorId = String(obj.parentSectorId);
			}

			var parent = obj.parentSectorId || '-';
			if (!(parent in map)) {
				map[parent] = {};
				map[parent].children = [];
			}

			map[parent].children.push(map[obj.sectorId]);
		}
		return map['-1'];
	}
	
	$scope.getFacilityTypes = function() {
		allServices.getSectors().then(function(data) {
//			data.splice(0, 1)
			$scope.allFacilityTypes = data;
			$scope.getAllFacilityTypesSuccessful = true;
			
		});
	};
	$scope.getSections = function() {
		allServices.getSections().then(function(data) {
			$scope.allSectionSubsections = data;
			$scope.allSections = convert(data).children;
			$scope.getAllSectionsSuccessful= true;
		});
	};
	$scope.getAllTimePeriods = function() {
		allServices.getAllTimeperiods().then(function(data) {
			$scope.allTimePeriods = data;
			
			//check for all district successful(used for 1st load and get pushpin)
			$scope.getAllTimePeriodsSuccessful = true;
		});
	};
	$scope.getFacilityTypes();
	$scope.getSections();
	$scope.getAllTimePeriods();
	
	
	$scope.selectReportType = function(type){
		if($scope.selectedReportType != type)
			$scope.tableData = [];
		$scope.selectedReportType = type;
		if($scope.selectedReportType == "Raw Report"){
			$scope.selectedFacilityType = "";
			$scope.selectedSection = "";
			$scope.selectedSubsection = "";
			$scope.selectedTimePeriod ="";
		}
	};
	$scope.selectFacilityType = function(facilityType) {
		if($scope.selectedFacilityType != facilityType)
			$scope.tableData = [];
		$scope.selectedFacilityType = facilityType;
	};
	$scope.selectSection = function(section){
		if($scope.selectedSection != section)
			$scope.tableData = [];
		$scope.selectedSection = section;
		$scope.selectedSubsection = "";
	};
	$scope.selectSubsection = function(subsection){
		if($scope.selectedSubsection != subsection)
			$scope.tableData = [];
		$scope.selectedSubsection = subsection;
	};
	$scope.selectTimePeriod = function(timeperiod) {
		if($scope.selectedTimePeriod != timeperiod)
			$scope.tableData = [];
		$scope.selectedTimePeriod = timeperiod;
	};
	
	$scope.submitForm = function(){
		if(!$scope.selectedReportType){
			$scope.errorMsg = "Please select Report Type";
			$("#errorMessage").modal("show");
			return false;
		}
		if($scope.selectedReportType == "Raw Report"){
			$(".loader").show();
			allServices.generateRawData().then(function(data) {
				$(".loader").fadeOut();
				$scope.rawReportPath = data;
				var fileName = {
						"fileName" : data
					};
					$.download("downloadRawData/", fileName, 'POST');
			});
		}
		else{
			if(!$scope.selectedFacilityType){
				$scope.errorMsg = "Please select Facility Type";
				$("#errorMessage").modal("show");
			}
			else if(!$scope.selectedSection){
				$scope.errorMsg = "Please select Section";
				$("#errorMessage").modal("show");
			}
			else if(!$scope.selectedSubsection && $scope.selectedSection.children.length){
				$scope.errorMsg = "Please select Subsection";
				$("#errorMessage").modal("show");
			}
			else if(!$scope.selectedTimePeriod){
				$scope.errorMsg = "Please select Time Period";
				$("#errorMessage").modal("show");
			}
			else{
				if($scope.selectedSubsection){
					$scope.secId = $scope.selectedSubsection.sectorId;
				}
				else{
					$scope.secId = $scope.selectedSection.sectorId;
				}
				
					$(".loader").show();
					allServices.getSummaryData($scope.selectedFacilityType.sectorId, $scope.secId, $scope.selectedTimePeriod.timePeriodId, 3).then(function(data) {
						$(".loader").fadeOut();
						$scope.tableData = data;
						if($scope.tableData.length){
							$scope.columns = Object.keys($scope.tableData[0]);
							$scope.columns.splice(0, 1);
							$scope.sortSelectedColumn = "District";
							$scope.sortType = '';
							$scope.order($scope.sortSelectedColumn);
							setTimeout(function() {
								$('html, body').animate({
									scrollTop : $("#reportTable").offset().top()
								}, 1000);
							}, 200)
						}
						else{
							$scope.errorMsg = "No data available for this selection";
							$("#errorMessage").modal("show");
						}
						setTimeout(function(){
							$('[data-toggle="tooltip"]').tooltip();
						},500);
						
					});
				
			}
		}
		
	};
	$scope.getDistrictSummary = function(district){
		$(".loader").show();
		allServices.getFacilitySummary($scope.selectedFacilityType.sectorId, $scope.secId, $scope.selectedTimePeriod.timePeriodId, district.DistrictId).then(function(data) {
			$(".loader").fadeOut();
			$scope.districtTableData = data;
			if($scope.tableData.length){
				$scope.districtTableColumns = Object.keys($scope.districtTableData[0]);
//				$scope.sortSelectedColumn = "District";
//				$scope.sortType = '';
//				$scope.order($scope.sortSelectedColumn);
				$("#districtTable").modal("show");
			}
			else{
				$scope.errorMsg = "No data available for this selection";
				$("#errorMessage").modal("show");
			}
		});
	};
	 $scope.exportTableData = function(id){
	    	var htmls = "";
	        var uri = 'data:application/vnd.ms-excel;base64,';
	        var template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'; 
	        var base64 = function(s) {
	            return window.btoa(unescape(encodeURIComponent(s)));
	        };

	        var format = function(s, c) {
	            return s.replace(/{(\w+)}/g, function(m, p) {
	                return c[p];
	            });
	        };
	        if(id == "dataTable"){
	        	 var tab_text="<table border='2px'><tr bgcolor='#87AFC6'>";
	        }
	        else
	        var tab_text="<table border='2px'><tr bgcolor='#87AFC6'>";
	        var textRange; var j=0;
	        tab = document.getElementById(id); // id of table

	        for(j = 0 ; j < tab.rows.length ; j++) 
	        {     
	            tab_text=tab_text+tab.rows[j].innerHTML+"</tr>";
	            //tab_text=tab_text+"</tr>";
	        }

	        tab_text=tab_text+"</table>";
	        tab_text= tab_text.replace(/<A[^>]*>|<\/A>/g, "");//remove if u want links in your table
	        tab_text= tab_text.replace(/<img[^>]*>/gi,""); // remove if u want images in your table
	        tab_text= tab_text.replace(/<input[^>]*>|<\/input>/gi, ""); // reomves input params


	        var ctx = {
	            worksheet : 'Worksheet',
	            table : tab_text
	        };


	        var link = document.createElement("a");
	        link.download = "summary-report.xls";
	        link.href = uri + base64(format(template, ctx));
	        link.click();
	    };
	    
	    $scope.order = function (sortType) {  
	        $scope.sortReverse = ($scope.sortType === sortType) ? !$scope.sortReverse : false;  
	        $scope.sortType = sortType;  
	      };
	    
	    $scope.filterType = function(val){
	        	if(val['DistrictId'] == 0){
	        		if(isNaN(parseInt(val[$scope.sortType]))){
	    	    		if($scope.sortReverse == true)
	    	    			return "";
	    	    		else
	    	    			return "zzz";
	        		}	
	        		else{
	        			if($scope.sortReverse == true)
	    	    			return -1;
	    	    		else
	    	    			return 9e12;
	        		}
	        	}
	        	if(isNaN(parseInt(val[$scope.sortType])))
	        	return val[$scope.sortType];
	        	else
	        		return parseInt(val[$scope.sortType]);
	      }; 
	        
	    $scope.selectSortColumn = function(column){
	    	if($scope.sortSelectedColumn != column){
	    		$(".sortSelected").removeClass("sortSelected");
	        	$scope.sortSelectedColumn = column;
	    	}
	    };   
	 $scope.filterFloat = function(value) {
		 	if(typeof value == 'undefined'){
		 		return '_';
		 	}
	        if (/^(\-|\+)?([0-9]+(\.[0-9]+)?|Infinity)$/
	        	      .test(value))
	        	      return Number(value);
	        	  return value;
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
		}	;
}