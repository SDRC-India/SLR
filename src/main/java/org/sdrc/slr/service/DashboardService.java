package org.sdrc.slr.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.sdrc.slr.model.AreaModel;
import org.sdrc.slr.model.GoogleMapDataModel;
import org.sdrc.slr.model.SectorModel;
import org.sdrc.slr.model.SpiderDataCollection;
import org.sdrc.slr.model.TimePeriodModel;

/**
 * 
 * @author Harsh Pratyush
 *
 */
public interface DashboardService {

	/**
	 * 
	 * @param stateId
	 * @return District List of Selected Area;
	 */
	public List<AreaModel> getAllDistricts(int stateId);

	/**
	 * 
	 * @return List of the Sectors and its respective xPaths
	 */
	public List<SectorModel> getAllSectors();

	/**
	 * 
	 * @param stateId
	 * @param districtId
	 * @param formXpathScoreMappingId
	 * @param xpathName
	 * @param sectorId
	 * @param timeperiodId
	 * @return List<GoogleMapDataModel> which contains the map data of each
	 *         facility according to timeperiod and selected sector or Xforms
	 * @throws ParseException
	 */
	public List<GoogleMapDataModel> getMapData(int stateId, int districtId,
			int formXpathScoreMappingId, String xpathName, int sectorId,
			int timeperiodId) throws ParseException;

	/**
	 * 
	 * @return TimePeriod data
	 * get all timeperiods
	 */
	public List<TimePeriodModel> getTimePeriod();

	/**
	 * 
	 * @param stateId
	 * @param districtId
	 * @param facilityTypeId
	 * @param lastVisitDataId
	 * @param timeperiodId1
	 * @param timeperiodId2
	 * @return Map<String, SpiderDataCollection> Data For the Spider chart and Line Chart
	 */
	public Map<String, SpiderDataCollection> getSpiderAndLineChartData(
			int stateId, int districtId, int facilityTypeId,
			int lastVisitDataId, int timeperiodId1, int timeperiodId2);

	/**
	 * This method will generate the PDF for Dashboard
	 * 
	 * @param svgs
	 * @param stateId
	 * @param districtId
	 * @param facilityTypeId
	 * @param lastVisitDataId
	 * @param formXpathScoreMappingId
	 * @param xpathName
	 * @param timeperiodId1
	 * @param timeperiodId2
	 * @param isComponent
	 * @return Output Path of Generated Pdf File for Dashboard
	 * @throws Exception
	 */
	public String exportToPdf(List<String> svgs, int stateId, int districtId,
			int facilityTypeId, int lastVisitDataId,
			int formXpathScoreMappingId, String xpathName, int timeperiodId1,
			int timeperiodId2, boolean isComponent) throws Exception;

	/**
	 * This method will generate the EXCEL for Dashboard
	 * @param svgs
	 * @param stateId
	 * @param districtId
	 * @param facilityTypeId
	 * @param lastVisitDataId
	 * @param timeperiodId1
	 * @param timeperiodId2
	 * @param isComponent
	 * @param xpathName 
	 * @param formXpathScoreMappingId 
	 * @return Output Path of Generated EXCEL File for Dashboard
	 * @throws Exception
	 */
	public String exportToExcel(List<String> svgs, int stateId, int districtId,
			int facilityTypeId, int lastVisitDataId, int timeperiodId1,
			int timeperiodId2, boolean isComponent, int formXpathScoreMappingId, String xpathName) throws Exception;
	
	
	/**
	 * @param b
	 * @return  This method counts the visitors
	 */
	public long getVisitorCount(boolean b);
}
