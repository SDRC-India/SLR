/**
 * 
 */
package org.sdrc.slr.repository;

import java.sql.Date;
import java.util.List;

import org.sdrc.slr.domain.RawDataScore;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Harsh Pratyush(harsh@sdrc.co.in)
 *
 */
public interface RawDataScoreRepository {

	/**
	 * save RawDataScore
	 * @param rawDataScore
	 */
	@Transactional
	void save(RawDataScore rawDataScore);

	/**
	 * This method will return the aggreagete score over every district of yes
//	 * no type indicator of a selected section and a selected FacilityType
	 * 
	 * @param districtId
	 * @param lastVisitId
	 * @param sectionId
	 * @param facilityTypeId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Object[]> findByTimeperiodANdLastVisitIdAndYESNoType(int districtId,
			List<Integer> lastVisitId, int sectionId, int facilityTypeId,
			Date startDate, Date endDate);

	/**
	 * This method will return the aggregated data over district of integer type
	 * indicator of a selected section and a selected FacilityType
	 * 
	 * @param areaId
	 * @param lastVisitDataIds
	 * @param sectionId
	 * @param facilityTypeId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Object[]> findByTimeperiodANdLastVisitIdAndIntegerType(Integer areaId,
			List<Integer> lastVisitDataIds, int sectionId, int facilityTypeId,
			Date startDate, Date endDate);

	/**
	 * This method will give the raw submission of every facility for each
	 * indicator for a selected section and selected district and selected FacilityType
	 * 
	 * @param districtId
	 * @param lastVisitDataIds
	 * @param sectionId
	 * @param facilityTypeId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Object[]> findFacilitySubmissionForATimePerodAndDistrict(
			Integer districtId, List<Integer> lastVisitDataIds, int sectionId,
			int facilityTypeId, Date startDate, Date endDate);

	/**
 * This method will return the aggreagete score over every district of yes
//	 * no type indicator of a selected section for every FacilityType
	 * @param districtId
	 * @param sectionId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Object[]> findByTimeperiodANdLastVisitIdAndYESNoTypeForAllSector(
			int districtId, int sectionId, Date startDate, Date endDate);
/**
 * This method will return the aggreagete score over every district of Integer
 type indicator of a selected section for every FacilityType
 * @param areaId
 * @param sectionId
 * @param startDate
 * @param endDate
 * @return
 */
	List<Object[]> findByTimeperiodANdLastVisitIdAndIntegerTypeForAllSector(
			Integer areaId, int sectionId, Date startDate, Date endDate);

	/**
	  * This method will give the raw submission of every facility for each
	 * indicator for a selected section and selected district for every FacilityType
	 * @param districtId
	 * @param sectionId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Object[]> findFacilitySubmissionForATimePeriodAndDistrictOfAllSections(
			int districtId, int sectionId, Date startDate, Date endDate);

	/**
	 * 
	 * @param imageXpathIds
	 * @param areaIds
	 * @return
	 */
	List<RawDataScore> findByRawFormXapthsXPathIdInAndLastVisitDataAreaAreaIdInOrderByLastVisitDataAreaAreaIdAscLastVisitDataMarkedAsCompleteDateAscRawFormXapthsXPathIdAsc(
			List<Integer> imageXpathIds, List<Integer> areaIds);

	/**
	 * 
	 * @param imageXpathIds
	 * @param areaIds
	 * @return
	 */
	List<RawDataScore> findByRawFormXapthsXPathIdInAndLastVisitDataAreaAreaIdInAndLastVisitDataIsLiveTrueOrderByLastVisitDataAreaAreaIdAscLastVisitDataMarkedAsCompleteDateAscRawFormXapthsXPathIdAsc(
			List<Integer> imageXpathIds, List<Integer> areaIds);

}
