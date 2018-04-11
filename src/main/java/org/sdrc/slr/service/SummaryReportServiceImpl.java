/**
 * 
 */
package org.sdrc.slr.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.sdrc.slr.domain.Area;
import org.sdrc.slr.domain.Sections;
import org.sdrc.slr.domain.TimePeriod;
import org.sdrc.slr.model.SectionsModel;
import org.sdrc.slr.repository.AreaDetailsRepository;
import org.sdrc.slr.repository.FacilityScoreRepository;
import org.sdrc.slr.repository.RawDataScoreRepository;
import org.sdrc.slr.repository.SectionsRepository;
import org.sdrc.slr.repository.TimePeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Harsh Pratyush (harsh@sdrc.co.in)
 *
 */
@Service
public class SummaryReportServiceImpl implements SummaryReportService {

	@Autowired
	SectionsRepository sectionsRepository;

	@Autowired
	TimePeriodRepository timePeriodRepository;

	@Autowired
	AreaDetailsRepository areaDetailsRepository;

	@Autowired
	FacilityScoreRepository facilityScoreRepository;

	@Autowired
	RawDataScoreRepository rawDataScoreRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sdrc.slr.service.SummaryReportService#getAllSections()
	 * This method will give the List of SectionsModel containing the
	 *         Sections
	 */
	@Override
	@Transactional
	public List<SectionsModel> getAllSections() {
		List<Sections> sections = sectionsRepository.findAll();

		List<SectionsModel> sectionsModels = new ArrayList<SectionsModel>();

		for (Sections section : sections) {
			SectionsModel sectionsModel = new SectionsModel();
			sectionsModel.setSectorId(section.getSectorId());
			sectionsModel.setSectorName(section.getSectorName());
			sectionsModel.setParentSectorId(section.getParentSectorId());

			sectionsModels.add(sectionsModel);
		}
		return sectionsModels;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sdrc.slr.service.SummaryReportService#getSummaryData(int, int,
	 * int, int)
	 * This method is responsible for the generation of summary report for a
	 * seleected facilityType for a selected sections within a timeperiod of a
	 * state in district wise order
	 */
	@Override
	public Object getSummaryData(int facilityTypeId, int sectionId,
			int timePeriodId, int stateId) {
		TimePeriod timePeriod = timePeriodRepository
				.findByTimePeriodId(timePeriodId);
		
		Area stateArea=areaDetailsRepository.findByAreaId(stateId);
		List<Area> areas = areaDetailsRepository.findByParentAreaId(stateId);
		Map<String, String> summaryDataMap = new LinkedHashMap<String, String>();
		List<Map<String, String>> summaryDataObject = new ArrayList<Map<String, String>>();

		// Facility Type Other Than All-All facilityId==0
		if (facilityTypeId != 0) {
			List<Integer> lastVisitDataIds = facilityScoreRepository
					.findAllLastVisitDataForAfacility(facilityTypeId);
			if (lastVisitDataIds.size() > 0)
				for (Area area : areas) {
					List<Object[]> dataAggregate = rawDataScoreRepository
							.findByTimeperiodANdLastVisitIdAndYESNoType(
									area.getAreaId(), lastVisitDataIds,
									sectionId, facilityTypeId,
									timePeriod.getStartDate(),
									timePeriod.getEndDate());
					dataAggregate.addAll(rawDataScoreRepository
							.findByTimeperiodANdLastVisitIdAndIntegerType(
									area.getAreaId(), lastVisitDataIds,
									sectionId, facilityTypeId,
									timePeriod.getStartDate(),
									timePeriod.getEndDate()));
					if (dataAggregate.size() > 0) {
						summaryDataMap = new LinkedHashMap<String, String>();
						summaryDataMap.put("DistrictId",
								String.valueOf(area.getAreaId()));
						summaryDataMap.put("District", area.getAreaName());

						for (Object[] data : dataAggregate) {
							summaryDataMap.put(data[0].toString(),
									data[1].toString());
						}

						summaryDataObject.add(summaryDataMap);
					}

				}
		}
		// For facility Type All
		else {
			for (Area area : areas) {
				List<Object[]> dataAggregate = rawDataScoreRepository
						.findByTimeperiodANdLastVisitIdAndYESNoTypeForAllSector(
								area.getAreaId(), sectionId,
								timePeriod.getStartDate(),
								timePeriod.getEndDate());
				dataAggregate
						.addAll(rawDataScoreRepository
								.findByTimeperiodANdLastVisitIdAndIntegerTypeForAllSector(
										area.getAreaId(), sectionId,
										timePeriod.getStartDate(),
										timePeriod.getEndDate()));
				if (dataAggregate.size() > 0) {
					summaryDataMap = new LinkedHashMap<String, String>();
					summaryDataMap.put("DistrictId",
							String.valueOf(area.getAreaId()));
					summaryDataMap.put("District", area.getAreaName());

					for (Object[] data : dataAggregate) {
						summaryDataMap.put(data[0].toString(),
								data[1].toString());
					}

					summaryDataObject.add(summaryDataMap);
				}

			}

		}
		if(summaryDataObject.size()>0)
		{
		summaryDataMap = new  HashMap<String, String>();
		summaryDataMap.put("DistrictId", String.valueOf(0));
		summaryDataMap.put("District", stateArea.getAreaName());
		for(Map<String, String> districtSummaryData:summaryDataObject)
		{
			for(Map.Entry<String, String> mapEntry:districtSummaryData.entrySet())
			{
				
				if(!(mapEntry.getKey().equalsIgnoreCase("District")||mapEntry.getKey().equalsIgnoreCase("DistrictId")))
				{
					if(summaryDataMap.containsKey(mapEntry.getKey()))
					{
						try
						{
							double value=Double.parseDouble(summaryDataMap.get(mapEntry.getKey()))+Double.parseDouble(mapEntry.getValue());
							summaryDataMap.put(mapEntry.getKey(),String.valueOf(value));
						}
						catch(Exception e)
						{
							
						}
					}
					
					else
					{
						try
						{
							Double.parseDouble(mapEntry.getValue());
						summaryDataMap.put(mapEntry.getKey(), mapEntry.getValue());
						}
						
						catch(Exception e)
						{
							
						}
					}					
				}
			}
			
			
		}
		summaryDataObject.add(summaryDataMap);
		}
		return summaryDataObject;
	}

	/* (non-Javadoc)
	 * @see org.sdrc.slr.service.SummaryReportService#getfacilityData(int, int, int, int)
	 *  This method will return the submission of the data of for a district of
	 * each facility for a facility level and sector and timeperiod
	 */
	@Override
	public Object getfacilityData(int facilityTypeId, int sectionId,
			int timePeriodId, int districtId) {
		TimePeriod timePeriod = timePeriodRepository
				.findByTimePeriodId(timePeriodId);
		List<Area> areas = areaDetailsRepository.findByParentAreaId(districtId);
		Map<Integer, Area> areaMap = new HashMap<Integer, Area>();
		for (Area area : areas) {
			areaMap.put(area.getAreaId(), area);
		}
		Map<String, String> summaryDataMap = new LinkedHashMap<String, String>();
		List<Map<String, String>> summaryDataObject = new ArrayList<Map<String, String>>();
		List<Object[]> facilityDataAggregates = null;
		// facility Type other than the All
		if (facilityTypeId != 0) {
			List<Integer> lastVisitDataIds = facilityScoreRepository
					.findAllLastVisitDataForAfacility(facilityTypeId);
			facilityDataAggregates = rawDataScoreRepository
					.findFacilitySubmissionForATimePerodAndDistrict(districtId,
							lastVisitDataIds, sectionId, facilityTypeId,
							timePeriod.getStartDate(), timePeriod.getEndDate());
		}
		// facility type selected is all
		else {
			facilityDataAggregates = rawDataScoreRepository
					.findFacilitySubmissionForATimePeriodAndDistrictOfAllSections(
							districtId, sectionId, timePeriod.getStartDate(),
							timePeriod.getEndDate());
		}
		for (Object[] facilityDataAggregate : facilityDataAggregates) {
			if (summaryDataMap.isEmpty()) {
				summaryDataMap = new LinkedHashMap<String, String>();
				summaryDataMap.put(
						"Facility",
						areaMap.get(
								Integer.parseInt(facilityDataAggregate[2]
										.toString())).getAreaName());
			} else if (summaryDataMap.get("Facility") != areaMap.get(
					Integer.parseInt(facilityDataAggregate[2].toString()))
					.getAreaName()) {
				summaryDataObject.add(summaryDataMap);
				summaryDataMap = new LinkedHashMap<String, String>();
				summaryDataMap.put(
						"Facility",
						areaMap.get(
								Integer.parseInt(facilityDataAggregate[2]
										.toString())).getAreaName());
			}

			summaryDataMap.put(facilityDataAggregate[0].toString(),
					facilityDataAggregate[1].toString());

		}
		if (!summaryDataMap.isEmpty()) {
			summaryDataObject.add(summaryDataMap);
		}
		return summaryDataObject;
	}

}
