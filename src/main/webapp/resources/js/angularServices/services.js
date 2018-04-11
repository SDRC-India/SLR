/*
 * @Laxman Paikaray(laxman@sdrc.co.in)
 * 
 * */
function checkSessionOut(data){
	if(typeof data == 'string' && data.indexOf("You are not authorized to view this page") != -1){
		$("body").append('<div id="sessionOutMessage" class="modal fade" role="dialog"><div class="modal-dialog"><div class="modal-content"><div class="modal-body text-center"><h3>Session is expired</h3><a href="home" class="btn btn-default errorOk" type="submit">OK</a></div></div></div></div>');
		$("#sessionOutMessage").modal("show");
	}
}

 
  function allServices($http, $q) {
	
	this.getAllTimeperiods = function(){
		var deferred = $q.defer();
        // get posts from database
		$http.get("getAllTimePeriods")
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getAllSectors = function(){
		var deferred = $q.defer();
        // get posts from database
		$http.get("getAllSectors")
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getSections = function(){
		var deferred = $q.defer();
        // get posts from database
		$http.get("getAllSections")
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getGoogleMapData = function(stateId, districtId, formXpathScoreMappingId, xpathName, sectorId, timePerioId){
		var deferred = $q.defer();
        // get posts from database
		$http.get("getGoogleMapData?stateId=" + stateId +
							"&districtId=" + districtId + 
							"&formXpathScoreMappingId=" + formXpathScoreMappingId +
							"&xpathName=" + xpathName +
							"&sectorId=" + sectorId +
							"&timePerioId=" + timePerioId
				)
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getAdvanceSearchData = function(stateId, facilityTpeId, formXpathScoreMappingId, patternValue, score, formXpathLabel,districtId,timepriodId){
		var deferred = $q.defer();
        // get posts from database
		$http.get("getAdvanceSearchData?stateId=" + stateId +
							"&facilityTpeId=" + facilityTpeId + 
							"&formXpathScoreMappingId=" + formXpathScoreMappingId +
							"&patternValue=" + patternValue +
							"&score=" + score +
							"&formXpathLabel=" + formXpathLabel+
							"&districtId=" + districtId +
							"&timePerioId=" + timepriodId 
				)
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getRawData = function(division, checklistId, sectionId, timperiodNid){
		var deferred = $q.defer();
        // get posts from database
		$http.get("getRawData?division=" + division +
							"&checklistId=" + checklistId + 
							"&sectionId=" + sectionId +
							"&timperiodNid=" + timperiodNid
				)
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getSpiderData = function(stateId, districtId, facilityTypeId, lastVisitDataId, timeperiodId1, timeperiodId2){
		var deferred = $q.defer();
        // get posts from database
		$http.get('getSpiderData?stateId=' + stateId +
							"&districtId=" + districtId +
							"&facilityTypeId=" + facilityTypeId +
							"&lastVisitDataId=" + lastVisitDataId +
							"&timeperiodId1=" + timeperiodId1 +
							"&timeperiodId2=" + timeperiodId2)
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getPlannedFacilities = function(formId, timePeriodId, districtId){
		var deferred = $q.defer();
        // get posts from database
		$http.get('getPlannedFacilities?formId=' + formId +
							"&timeperiodId=" + timePeriodId +
							"&areaId=" + districtId)
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getStateMap = function(){
		var deferred = $q.defer();
        // get posts from database
		$http.get('resources/geoJson/telangana.json')
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getParentSectors = function(){
		var deferred = $q.defer();
        // get posts from database
		$http.get('getParentSectors')
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getSectors = function(){
		var deferred = $q.defer();
        // get posts from database
		$http.get('getSectors')
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getAllDistricts = function(stateId){
		var deferred = $q.defer();
        // get posts from database
		$http.get('getDistricts?stateId=' + stateId)
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getTreeData = function(){
		var deferred = $q.defer();
        // get posts from database
		$http.get('treeData')
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getBubbleChartData = function(sectorId, areaId){
		var deferred = $q.defer();
        // get posts from database
		$http.get('bubbleChartData?sectorId='+sectorId + '&areaId=' + areaId)
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	/*this.exportToPdf = function(){
		var deferred = $q.defer();
        // get posts from database
		$http.get('exportToPdf')
          .then(function(result) {
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};*/
	this.getFacilitiesForCrossTab = function(){
		var deferred = $q.defer();
        // get posts from database
		$http.get('getFacilitiesForCrossTab')
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getCrossTabData = function(crosstab, areaId){
		var deferred = $q.defer();
        // get posts from database
		$http.get('getCrossTabData?id=' + crosstab.parentid +
									'&colid=' + crosstab.colid + 
									'&rowid=' + crosstab.rowid +
									'&areaId=' + areaId)
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getFacilityImprovementSelection = function(){
		var deferred = $q.defer();
        // get posts from database
		$http.get('getAllFacilityList')
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.generateRawData = function(){
		var deferred = $q.defer();
        // get posts from database
		$http.get('generateRawData')
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getSummaryData = function(facilityTypeId, sectionId, timePeriodId, stateId){
		var deferred = $q.defer();
        // get posts from database
		$http.get('getSummaryData?facilityTypeId=' + facilityTypeId +
									'&sectionId=' + sectionId +
									'&timePeriodId=' + timePeriodId +
									'&stateId=' + stateId)
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getFacilitySummary = function(facilityTypeId, sectionId, timePeriodId, districtId){
		var deferred = $q.defer();
        // get posts from database
		$http.get('getFacilitySummary?facilityTypeId=' + facilityTypeId +
									'&sectionId=' + sectionId +
									'&timePeriodId=' + timePeriodId +
									'&districtId=' + districtId)
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
}	
$(document).ready(function(){
	$("#mymain").css("min-height", $(window).height()-92-58);
	    $('[data-toggle="tooltip"]').tooltip();   
});  
$(window).scroll(function(){
	if($(window).scrollTop() > 92){
		$(".navbar.nav-menu-container").addClass("fixed");
	}
	else{
		$(".navbar.nav-menu-container").removeClass("fixed");
	}
});
function minmax(value, min, max) 
{
    if(parseInt(value) < min || isNaN(value)) 
        return 0; 
    else if(parseInt(value) > max) 
        return 100; 
    else return value;
}
