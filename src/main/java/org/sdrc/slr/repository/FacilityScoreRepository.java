package org.sdrc.slr.repository;

import java.sql.Date;
import java.util.List;

import org.sdrc.slr.domain.FacilityScore;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Harsh Pratyush (harsh@sdrc.co.in)
 *
 */
public interface FacilityScoreRepository {

	/**
	 * persist submission's response 
	 * @param facilityScore
	 */
	@Transactional
	public void save(FacilityScore facilityScore);

	/**
	 * This method will return the overall score in %age format of each
	 * facilities of all sector of a selected state and selected Facility Type
	 * Overall Score means average score of all the indicator
	 * 
	 * @param stateId
	 * @param sectorId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Object[]> getOverAllScoreForSectorOfState(int stateId,
			int sectorId, Date startDate, Date endDate);

	/**
	 * This method will return the score of each facility for a selected
	 * indicator of a selected state for a Selected facility Type
	 * 
	 * @param stateId
	 * @param formXpathScoreMappingId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Object[]> getScoreOfSelectedIndicatorofState(int stateId,
			int formXpathScoreMappingId, Date startDate, Date endDate);

	/**
	 * This will return the overall score for each facility for a selected state
	 * of all FacilityType
	 * 
	 * @param stateId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Object[]> getOverAllScoreForAllSectorOfState(int stateId,
			Date startDate, Date endDate);

	/**
	 * This method will return the score of each facility for a selected
	 * indicator of a selected state for every FacilityType
	 * 
	 * @param stateId
	 * @param indicatorName
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Object[]> getScoreForSelectedindicatorOfAllSectorsOfState(
			int stateId, String indicatorName, Date startDate, Date endDate);

	/**
	 * This method will return the Overall score of each Facility for a selected
	 * FacilityType within a Selected district
	 * 
	 * @param districtId
	 * @param sectorId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Object[]> getOverAllScoreForSectorOfDistrict(int districtId,
			int sectorId, Date startDate, Date endDate);

	/**
	 * This will return the score of each facility for a Selected FacilityType
	 * and Selected indicator within a district
	 * 
	 * @param districtId
	 * @param formXpathScoreMappingId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Object[]> getScoreOfSelectedIndicatorofDistrict(int districtId,
			int formXpathScoreMappingId, Date startDate, Date endDate);

	/**
	 * This method will return the overall score of each facility for every
	 * FacilityType within a selected district
	 * 
	 * @param districtId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Object[]> getOverAllScoreForAllSectorOfDistrict(int districtId,
			Date startDate, Date endDate);

	/**
	 * This method will return the score of each facility for a selected
	 * indicator of every FacilityType within a selected district
	 * 
	 * @param districtId
	 * @param indicatorName
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Object[]> getScoreForSelectedindicatorOfAllSectorsOfDistrict(
			int districtId, String indicatorName, Date startDate, Date endDate);

	/**
	 * This repo has not been used..Just a dummy
	 * 
	 * @param districtId
	 * @param sectorId
	 * @return
	 */
	public List<Object[]> getOverAllScoreForSectorOfDistrictWithoutDate(
			int districtId, int sectorId);

	/**
	 * This method will return the score of each indicator aggeragated at state
	 * level for every FacilityType for a selected module
	 * 
	 * @param stateId
	 * @param startDate
	 * @param endDate
	 * @param module
	 * @return
	 */
	public List<Object[]> getSpiderDataForStateOfAllIndicator(int stateId,
			Date startDate, Date endDate, String module);

	/**
	 * This method will return the score of each indicator for a selected
	 * FacilityType aggregated at state level
	 * 
	 * @param stateId
	 * @param facilityTypeId
	 * @param startDate
	 * @param endDate
	 * @param module
	 * @return
	 */
	public List<Object[]> getSpiderDataForStateofSelectedFacility(int stateId,
			int facilityTypeId, Date startDate, Date endDate, String module);

	/**
	 * This method will return the score of each indicator for all FacilityType
	 * aggregated at district level
	 * 
	 * @param districtId
	 * @param startDate
	 * @param endDate
	 * @param module
	 * @return
	 */
	public List<Object[]> getSpiderDataForDistrictOfAllIndicator(
			int districtId, Date startDate, Date endDate, String module);

	/**
	 * This method will return the score of each indicator for a selected
	 * FacilityType aggregated at district level
	 * 
	 * @param districtId
	 * @param facilityTypeId
	 * @param startDate
	 * @param endDate
	 * @param module
	 * @return
	 */
	public List<Object[]> getSpiderDataForDistrictForSelectedFacility(
			int districtId, int facilityTypeId, Date startDate, Date endDate,
			String module);

	/**
	 * This method will return score of each indicator for a selected facility
	 * i.e. for a selected submission
	 * 
	 * @param areaId
	 * @param facilityTypeId
	 * @param startDate
	 * @param endDate
	 * @param module
	 * @return
	 */
	public List<Object[]> getSpiderDataForLastVisitData(int areaId,
			int facilityTypeId, Date startDate, Date endDate, String module);

	/**
	 * This method will return overall Score of a every FacilityType for each
	 * Facility for overall state
	 * 
	 * @param stateId
	 * @param endDate 
	 * @param startDate 
	 * @return
	 */
	public List<Object[]> getAdvanceSearchDataForOverAllScoreOfAllFacility(
			int stateId, Date startDate, Date endDate);

	/**
	 * This method will give the score for each Facility for a selected
	 * Indicator for every FacilityType of overall state
	 * 
	 * @param stateId
	 * @param indicatorName
	 * @param endDate 
	 * @param startDate 
	 * @return
	 */
	public List<Object[]> getAdvanceSearchDataForSelectedIndicatorOfAllFacility(
			int stateId, String indicatorName, Date startDate, Date endDate);

	/**
	 * This method will return the overall score of each facility for a Selected FacilityType 
	 * of overall state
	 * @param endDate 
	 * @param startDate 
	 */
	public List<Object[]> getAdvancaeSearchDataForOverAllScoreofSelctedFacility(
			int stateId, int facilityTypeId, Date startDate, Date endDate);

	/**
	 * This method will return the score of each facility for a selected Indicator and a selected FacilityType
	 * of overall state
	 * @param stateId
	 * @param formXpathMapping
	 * @param endDate 
	 * @param startDate 
	 * @return
	 */
	public List<Object[]> getAdvcanceSearchDataForSelectedFormXpathMapping(
			int stateId, int formXpathMapping, Date startDate, Date endDate);

	/**
	 * This method will return the lastVisitDataId for a given FacilityType
	 * @param facilityTypeId
	 * @return
	 */
	public List<Integer> findAllLastVisitDataForAfacility(int facilityTypeId);

	/**
	 * This method will return overall Score of a every FacilityType for each
	 * Facility of a district
	 * 
	 * @param districtId
	 * @param endDate 
	 * @param startDate 
	 * @return
	 */
	public List<Object[]> getAdvanceSearchDataForOverAllScoreOfAllFacilityOfDistrict(
			int districtId, Date startDate, Date endDate);

	/**
	 * This method will give the score for each Facility for a selected
	 * Indicator for every FacilityType of a district
	 * 
	 * @param districtId
	 * @param indicatorName
	 * @param endDate 
	 * @param startDate 
	 * @return
	 */
	public List<Object[]> getAdvanceSearchDataForSelectedIndicatorOfAllFacilityofDistrict(
			int districtId, String formXpathLabel, Date startDate, Date endDate);

	/**
	 * This method will return the overall score of each facility for a Selected FacilityType 
	 * of a district
	 * @param endDate 
	 * @param startDate 
	 */
	public List<Object[]> getAdvancaeSearchDataForOverAllScoreofSelctedFacilityofDistrict(
			int districtId, int facilityTpeId, Date startDate, Date endDate);

	/**
	 * This method will return the score of each facility for a selected Indicator and a selected FacilityType
	 * of a district
	 * @param districtId
	 * @param formXpathMapping
	 * @param endDate 
	 * @param startDate 
	 * @return
	 */
	public List<Object[]> getAdvcanceSearchDataForSelectedFormXpathMappingofDistrict(
			int districtId, int formXpathScoreMappingId, Date startDate,
			Date endDate);
}
