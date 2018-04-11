/**
 * 
 */
package org.sdrc.slr.service;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.sdrc.slr.domain.Area;
import org.sdrc.slr.domain.FacilityType;
import org.sdrc.slr.domain.TimePeriod;
import org.sdrc.slr.repository.AreaDetailsRepository;
import org.sdrc.slr.repository.FacilityScoreRepository;
import org.sdrc.slr.repository.FacilityTypeRepository;
import org.sdrc.slr.repository.LastVisitDataRepository;
import org.sdrc.slr.repository.TimePeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Harsh Pratyush
 *
 */

@Service
public class AdvanceSearchServiceImpl implements AdvanceSearchService {

	@Autowired
	private FacilityScoreRepository facilityScoreRepository;

	@Autowired
	private FacilityTypeRepository facilityTypeRepository;

	@Autowired
	private AreaDetailsRepository areaDetailsRepository;
	
	@Autowired
	LastVisitDataRepository lastVisitDataRepository;
	
	@Autowired
	private TimePeriodRepository timePeriodRepository;

	private static DecimalFormat df = new DecimalFormat(".#");

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sdrc.slr.service.AdvanceSearchService#getAdvanceSearchData(int,
	 * int, java.lang.String, java.lang.String) 
	 * It will return the value of each
	 * each facility within a sector with its facility type and district on the
	 * basis of selected pattern value i.e. for a given score and selected
	 * pattern it will return List of Facilities
	 */
	@Override
	public List<Map<String,String>> getAdvanceSearchData(int stateId,int facilityTpeId,
			int formXpathScoreMappingId, int patternValue, String score,
			String formXpathLabel,int districtId,int timePeriodId) {

		List<Area> areaDetails = areaDetailsRepository.findAll();
		List<FacilityType> facilityTypes = facilityTypeRepository.findAll();
		TimePeriod timePeriod = timePeriodRepository
				.findByTimePeriodId(timePeriodId);
		Date startDate, endDate;
		startDate = timePeriod.getStartDate();
		endDate = timePeriod.getEndDate();
		Map<Integer, Area> areaMap = new LinkedHashMap<Integer, Area>();
		for (Area areaDetail : areaDetails) {
			areaMap.put(areaDetail.getAreaId(), areaDetail);
		}
		


		Map<Integer, FacilityType> faciliTypeMap = new LinkedHashMap<Integer, FacilityType>();

		for (FacilityType facilityType : facilityTypes) {
			faciliTypeMap.put(facilityType.getFacilityId(), facilityType);
		}

		List<Map<String,String>> advanceSearchDataModels = new ArrayList<Map<String,String>>();
		Map<String, String> advanceSearchDataMaps = new LinkedHashMap<String, String>();
		List<Object[]> advanceSearchDatas = new ArrayList<Object[]>();
		if(districtId==0)
		{
		if (facilityTpeId == 0 && formXpathScoreMappingId == -1) 
		{
			advanceSearchDatas = facilityScoreRepository.getAdvanceSearchDataForOverAllScoreOfAllFacility(stateId,startDate,endDate);
		} else if (facilityTpeId == 0 && formXpathScoreMappingId == 0) 
		{
			advanceSearchDatas = facilityScoreRepository.getAdvanceSearchDataForSelectedIndicatorOfAllFacility(stateId, formXpathLabel,startDate,endDate);
		}

		else if (facilityTpeId != 0 && formXpathScoreMappingId == 0) 
		{

			advanceSearchDatas=facilityScoreRepository.getAdvancaeSearchDataForOverAllScoreofSelctedFacility(stateId, facilityTpeId,startDate,endDate);
		} else if (facilityTpeId != 0 && formXpathScoreMappingId != 0) 
		{

			advanceSearchDatas=facilityScoreRepository.getAdvcanceSearchDataForSelectedFormXpathMapping(stateId, formXpathScoreMappingId,startDate,endDate);
		}
		}
		else
		{
			if (facilityTpeId == 0 && formXpathScoreMappingId == -1) 
			{
				advanceSearchDatas = facilityScoreRepository.getAdvanceSearchDataForOverAllScoreOfAllFacilityOfDistrict(districtId,startDate,endDate);
			} else if (facilityTpeId == 0 && formXpathScoreMappingId == 0) 
			{
				advanceSearchDatas = facilityScoreRepository.getAdvanceSearchDataForSelectedIndicatorOfAllFacilityofDistrict(districtId, formXpathLabel,startDate,endDate);
			}

			else if (facilityTpeId != 0 && formXpathScoreMappingId == 0) 
			{

				advanceSearchDatas=facilityScoreRepository.getAdvancaeSearchDataForOverAllScoreofSelctedFacilityofDistrict(districtId, facilityTpeId,startDate,endDate);
			} else if (facilityTpeId != 0 && formXpathScoreMappingId != 0) 
			{

				advanceSearchDatas=facilityScoreRepository.getAdvcanceSearchDataForSelectedFormXpathMappingofDistrict(districtId, formXpathScoreMappingId,startDate,endDate);
			}
			}
		 for(Object [] advcanceSearchData :advanceSearchDatas)
		 {
			if(advcanceSearchData[0] instanceof Double)
			{
			 switch(patternValue)
			 {
			// greater than 
			 case 1:
				 if(Double.valueOf(score)< Double.valueOf(df.format(advcanceSearchData[0])))
				 {
					break; 
				 }
			 
				 else
				 {
					 continue; 
				 }
				 
			// equal to	 
			 case 2:
				 
				 if((Double.valueOf(score) - Double.valueOf(df.format(advcanceSearchData[0])))==0)
				 {
					break; 
				 }
			 
				 else
				 {
					 continue; 
				 }
				 
			// equal to greater than	 
			 case 3:
				 
				 if(Double.valueOf(score)<= Double.valueOf(df.format(advcanceSearchData[0])))
				 {
					break; 
				 }
			 
				 else
				 {
					 continue; 
				 }
				 
		// less than	 
			 case 4:
				 
				 if(Double.valueOf(score)> Double.valueOf(df.format(advcanceSearchData[0])))
				 {
					break; 
				 }
			 
				 else
				 {
					 continue; 
				 }
			// less than equal to	 
			 case 5:
				 if(Double.valueOf(score)>= Double.valueOf(df.format(advcanceSearchData[0])))
				 {
					break; 
				 }
			 
				 else
				 {
					 continue; 
				 }
			 
			default : continue;
				
				
			 }
			 
			 advanceSearchDataMaps = new LinkedHashMap<String, String>();
			 advanceSearchDataMaps.put("District Name",areaMap.get(areaMap.get(advcanceSearchData[1]).getParentAreaId()).getAreaName());
			 advanceSearchDataMaps.put("Facility Name",areaMap.get(advcanceSearchData[1]).getAreaName());
			 if (facilityTpeId == 0)
						{
			 advanceSearchDataMaps.put("Facility Type",faciliTypeMap.get(advcanceSearchData[2]).getFacilityName());
						}
			/* advanceSearchDataMaps.put("Time Period",advcanceSearchData[3].toString());	*/
			 if(Double.valueOf(advcanceSearchData[0].toString())>=1)
			 {
			 advanceSearchDataMaps.put("Score",df.format(advcanceSearchData[0]));
			 }
			 else
			 {
				 advanceSearchDataMaps.put("Score",0+df.format(advcanceSearchData[0]));
			 }
			 advanceSearchDataModels.add(advanceSearchDataMaps);
			}
			}
		return advanceSearchDataModels;
	}
}
