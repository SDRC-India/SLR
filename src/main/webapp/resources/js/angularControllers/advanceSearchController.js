function advanceSearchController($scope, $http, allServices){
	$scope.activeMenu = 'advanceSearch';
	$scope.allPatterns = [{value:1, name:"> (greater than)"}, {value: 2, name:"= (equal to)"}, {value: 3, name:">= (greater than/equal to)"}, {value: 4, name: "< (less than)"}, {value:5, name:"<= (less than/equal to)"}]
	$('[data-toggle="tooltip"]').tooltip();
	
	$scope.getLevels = function() {
		allServices.getSectors().then(function(data) {
			$scope.levels = data;
//			$scope.selectLevel($scope.levels[0]);
//			$scope.getAllDistricts(3);
			
			//check for all district successful(used for 1st load and get pushpin)
			$scope.getSectorsSuccessful = true;
			
		});
	};
	
	$scope.getTimePeriod=function()
	{
		allServices.getAllTimeperiods().then(function(data)
		{
			$scope.timeperiods=data;
		});
	};
	
	$scope.getDistricts=function()
	{
		allServices.getAllDistricts(3).then(function(data)
		{
			$scope.districts=data;
			$scope.districts[0].areaName='All Districts';
		});
	};
	$scope.getLevels();
	$scope.getTimePeriod();
	$scope.getDistricts();
	
	$scope.selectLevel = function(level) {
		$scope.selectedLevel = level;
		$scope.selectedCriteria = "";
	};
	$scope.selectCriteria = function(criteria) {
		$scope.selectedCriteria = criteria;
	};
	$scope.selectPattern = function(pattern){
		$scope.selectedPattern = pattern;
	};
	
	$scope.selectTimePeriod=function(timeperiod)
	{
		$scope.selectedTimePeriod=timeperiod;
	};
	
	$scope.selectDistrict=function(district)
	{
		$scope.selectedDistrict=district
	};
	
	$scope.viewData = function(){
		setTimeout(function(){
			$('[data-toggle="tooltip"]').tooltip();

			}, 500)
		if(!$scope.selectedLevel){
			$scope.errorMsg = "Please select a Facility Type";
			$("#errorMessage").modal("show");
		}
		else if(!$scope.selectedCriteria){
			$scope.errorMsg = "Please select a Component/Service Area";
			$("#errorMessage").modal("show");
		}
		else if(!$scope.selectedDistrict)
			{
			$scope.errorMsg = "Please select a District";
			$("#errorMessage").modal("show");
			}
		else if(!$scope.selectedTimePeriod)
			{
			$scope.errorMsg = "Please select a Timeperiod";
			$("#errorMessage").modal("show");
			}
		
		else if(!$scope.selectedPattern){
			$scope.errorMsg = "Please select Rule";
			$("#errorMessage").modal("show");
		}
		else if($("#percentValue").val() == ""){
			$scope.errorMsg = "Please select Percentage Value";
			$("#errorMessage").modal("show");
		}
		else{
			$(".loader").show();
			allServices.getAdvanceSearchData(3, $scope.selectedLevel.sectorId, $scope.selectedCriteria.formXpathScoreMappingId, $scope.selectedPattern.value, $scope.percentValue, $scope.selectedCriteria.label,$scope.selectedDistrict.areaId,$scope.selectedTimePeriod.timePeriodId).then(function(data){
				$scope.tableData = data;
				if($scope.tableData.length){
					$scope.columns = Object.keys($scope.tableData[0]);
					$scope.sortSelectedColumn = "District Name";
					$scope.sortType = '';
					$scope.order($scope.sortSelectedColumn);
				}
				else{
					$scope.errorMsg = "No data available for this selection";
					$("#errorMessage").modal("show");
				}
				$(".loader").fadeOut();
			});
		}
		
	};
	$scope.order = function (sortType) {  
        $scope.sortReverse = ($scope.sortType === sortType) ? !$scope.sortReverse : false;  
        $scope.sortType = sortType;  
      };
      
    $scope.filterType = function(val){
      	if(val['District'] == "Chhattisgarh"){
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

        var tab_text= "<table border='2px'><tr bgcolor='#87AFC6'>";
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
        link.download = "advanceSearch.xls";
        link.href = uri + base64(format(template, ctx));
        link.click();
    };
    
    
    $scope.selectSortColumn = function(column){
    	if($scope.sortSelectedColumn != column){
    		$(".sortSelected").removeClass("sortSelected");
        	$scope.sortSelectedColumn = column;
    	}
    };
    $scope.filterFloat = function(value) {
        if (/^(\-|\+)?([0-9]+(\.[0-9]+)?|Infinity)$/
        	      .test(value))
        	      return Number(value);
        	  return value;
    };
	
}