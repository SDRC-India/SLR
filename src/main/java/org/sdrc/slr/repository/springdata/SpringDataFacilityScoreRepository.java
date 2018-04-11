package org.sdrc.slr.repository.springdata;

import java.sql.Date;
import java.util.List;

import org.sdrc.slr.domain.FacilityScore;
import org.sdrc.slr.repository.FacilityScoreRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

/**
 * 
 * @author Harsh Pratyush(harsh@sdrc.co.in)
 *
 */
@RepositoryDefinition(domainClass = FacilityScore.class, idClass = Integer.class)
public interface SpringDataFacilityScoreRepository extends
		FacilityScoreRepository {

	@Query("SELECT lvd.lastVisitDataId , lvd.area.areaId , lvd.area.areaName,"
			+ " AVG ((fc.score/fc.formXpathScoreMapping.formXpath.maxScore)*100), "
			+ " lvd.latitude , lvd.longitude , lvd.markedAsCompleteDate "
			+ " , fc.formXpathScoreMapping.facilityType.facilityId "
			+ " FROM FacilityScore fc JOIN fc.lastVisitData lvd"
			+ " WHERE lvd.area.parentAreaId = :districtId "
			+ " AND fc.score is not null"
			+ " AND lvd.isLive = true"
			+ " AND lvd.markedAsCompleteDate BETWEEN :startDate AND :endDate"
			+ " GROUP BY lvd.lastVisitDataId , lvd.area.areaId , lvd.area.areaName"
			+ " ,lvd.latitude , lvd.longitude , lvd.markedAsCompleteDate , "
			+ "fc.formXpathScoreMapping.facilityType.facilityId")
	@Override
	public List<Object[]> getOverAllScoreForAllSectorOfDistrict(
			@Param("districtId") int districtId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT lvd.lastVisitDataId , lvd.area.areaId , lvd.area.areaName, "
			+ " AVG(((fc.score)/(fc.formXpathScoreMapping.formXpath.maxScore))*100), "
			+ " lvd.latitude , lvd.longitude , lvd.markedAsCompleteDate "
			+ ", fc.formXpathScoreMapping.facilityType.facilityId "
			+ " FROM FacilityScore fc JOIN  fc.lastVisitData lvd ,Area area"
			+ " WHERE area.parentAreaId = :stateId "
			+ " AND lvd.isLive = true"
			+ " AND fc.score is not null"
			+ " AND lvd.area.parentAreaId = area.areaId "
			+ " AND lvd.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " GROUP BY lvd.lastVisitDataId , lvd.area.areaId , lvd.area.areaName"
			+ " ,lvd.latitude , lvd.longitude , lvd.markedAsCompleteDate , "
			+ " fc.formXpathScoreMapping.facilityType.facilityId")
	@Override
	public List<Object[]> getOverAllScoreForAllSectorOfState(
			@Param("stateId") int stateId, @Param("startDate") Date startDate,
			@Param("endDate") Date endDate);

	@Query("SELECT lvd.lastVisitDataId , lvd.area.areaId , lvd.area.areaName, "
			+ " AVG(((fc.score)/(fc.formXpathScoreMapping.formXpath.maxScore))*100), "
			+ " lvd.latitude , lvd.longitude , lvd.markedAsCompleteDate "
			+ " FROM FacilityScore fc JOIN  fc.lastVisitData lvd"
			+ " WHERE lvd.area.parentAreaId = :districtId "
			+ " AND lvd.isLive = true "
			+ " AND fc.score is not null"
			+ " AND lvd.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " AND fc.formXpathScoreMapping.facilityType.facilityId = :sectorId "
			+ " GROUP BY lvd.lastVisitDataId , lvd.area.areaId , lvd.area.areaName"
			+ " ,lvd.latitude , lvd.longitude , lvd.markedAsCompleteDate")
	@Override
	public List<Object[]> getOverAllScoreForSectorOfDistrict(
			@Param("districtId") int districtId,
			@Param("sectorId") int sectorId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT lvd.lastVisitDataId , lvd.area.areaId , lvd.area.areaName, "
			+ " AVG(((fc.score)/(fc.formXpathScoreMapping.formXpath.maxScore)))*100, "
			+ " lvd.latitude , lvd.longitude , lvd.markedAsCompleteDate "
			+ " FROM FacilityScore fc JOIN  fc.lastVisitData lvd , Area area"
			+ " WHERE area.parentAreaId = :stateId "
			+ " AND lvd.area.parentAreaId = area.areaId "
			+ " AND lvd.isLive = true "
			+ " AND fc.score is not null"
			+ " AND lvd.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " AND fc.formXpathScoreMapping.facilityType.facilityId = :sectorId "
			+ " GROUP BY lvd.lastVisitDataId , lvd.area.areaId , lvd.area.areaName"
			+ " ,lvd.latitude , lvd.longitude , lvd.markedAsCompleteDate")
	@Override
	public List<Object[]> getOverAllScoreForSectorOfState(
			@Param("stateId") int stateId, @Param("sectorId") int sectorId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT lvd.lastVisitDataId , lvd.area.areaId , lvd.area.areaName, "
			+ "(SUM(fc.score)/SUM(fc.formXpathScoreMapping.formXpath.maxScore))*100, "
			+ " lvd.latitude , lvd.longitude , lvd.markedAsCompleteDate "
			+ " , fc.formXpathScoreMapping.facilityType.facilityId "
			+ " FROM FacilityScore fc JOIN fc.lastVisitData lvd"
			+ " WHERE lvd.area.parentAreaId = :districtId "
			+ " AND lvd.isLive = true "
			+ " AND lvd.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " AND fc.formXpathScoreMapping.formXpath.label = :indicatorName "
			+ " GROUP BY lvd.lastVisitDataId , lvd.area.areaId , lvd.area.areaName"
			+ " ,lvd.latitude , lvd.longitude , lvd.markedAsCompleteDate ,"
			+ " fc.formXpathScoreMapping.facilityType.facilityId ")
	@Override
	public List<Object[]> getScoreForSelectedindicatorOfAllSectorsOfDistrict(
			@Param("districtId") int districtId,
			@Param("indicatorName") String indicatorName,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT lvd.lastVisitDataId , lvd.area.areaId , lvd.area.areaName, "
			+ "(SUM(fc.score)/SUM(fc.formXpathScoreMapping.formXpath.maxScore))*100, "
			+ " lvd.latitude , lvd.longitude , lvd.markedAsCompleteDate ,"
			+ " fc.formXpathScoreMapping.facilityType.facilityId "
			+ " FROM FacilityScore fc JOIN fc.lastVisitData lvd , Area area "
			+ " WHERE area.parentAreaId = :stateId "
			+ " AND lvd.isLive = true "
			+ " AND lvd.area.parentAreaId = area.areaId "
			+ " AND lvd.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " AND fc.formXpathScoreMapping.formXpath.label = :indicatorName "
			+ " GROUP BY lvd.lastVisitDataId , lvd.area.areaId , lvd.area.areaName"
			+ " ,lvd.latitude , lvd.longitude , lvd.markedAsCompleteDate ,"
			+ " fc.formXpathScoreMapping.facilityType.facilityId ")
	@Override
	public List<Object[]> getScoreForSelectedindicatorOfAllSectorsOfState(
			@Param("stateId") int stateId,
			@Param("indicatorName") String indicatorName,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT lvd.lastVisitDataId , lvd.area.areaId , lvd.area.areaName, "
			+ "(SUM(fc.score)/SUM(fc.formXpathScoreMapping.formXpath.maxScore))*100, "
			+ " lvd.latitude , lvd.longitude , lvd.markedAsCompleteDate "
			+ " FROM FacilityScore fc JOIN fc.lastVisitData lvd "
			+ " WHERE lvd.area.parentAreaId = :districtId "
			+ " AND lvd.isLive = true "
			+ " AND lvd.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " AND fc.formXpathScoreMapping.formXpathScoreId = :formXpathScoreMappingId "
			+ " GROUP BY lvd.lastVisitDataId , lvd.area.areaId , lvd.area.areaName"
			+ " ,lvd.latitude , lvd.longitude , lvd.markedAsCompleteDate")
	@Override
	public List<Object[]> getScoreOfSelectedIndicatorofDistrict(
			@Param("districtId") int districtId,
			@Param("formXpathScoreMappingId") int formXpathScoreMappingId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT lvd.lastVisitDataId , lvd.area.areaId , lvd.area.areaName, "
			+ "(SUM(fc.score)/SUM(fc.formXpathScoreMapping.formXpath.maxScore))*100, "
			+ " lvd.latitude , lvd.longitude , lvd.markedAsCompleteDate "
			+ " FROM FacilityScore fc JOIN  fc.lastVisitData lvd , Area area"
			+ " WHERE area.parentAreaId = :stateId "
			+ " AND lvd.isLive = true "
			+ " AND lvd.area.parentAreaId = area.areaId "
			+ " AND lvd.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " AND fc.formXpathScoreMapping.formXpathScoreId = :formXpathScoreMappingId "
			+ " GROUP BY lvd.lastVisitDataId , lvd.area.areaId , lvd.area.areaName"
			+ " ,lvd.latitude , lvd.longitude , lvd.markedAsCompleteDate")
	@Override
	public List<Object[]> getScoreOfSelectedIndicatorofState(
			@Param("stateId") int stateId,
			@Param("formXpathScoreMappingId") int formXpathScoreMappingId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT lvd.lastVisitDataId , lvd.area.areaId , lvd.area.areaName, "
			+ "(SUM(fc.score)/SUM(fc.formXpathScoreMapping.formXpath.maxScore))*100, "
			+ " lvd.latitude , lvd.longitude , lvd.markedAsCompleteDate "
			+ " FROM FacilityScore fc JOIN  fc.lastVisitData lvd"
			+ " WHERE lvd.area.parentAreaId = :districtId "
			+ " AND lvd.isLive = true "
			+ " AND fc.formXpathScoreMapping.facilityType.facilityId = :sectorId "
			+ " GROUP BY lvd.lastVisitDataId , lvd.area.areaId , lvd.area.areaName"
			+ " ,lvd.latitude , lvd.longitude , lvd.markedAsCompleteDate")
	@Override
	public List<Object[]> getOverAllScoreForSectorOfDistrictWithoutDate(
			@Param("districtId") int districtId, @Param("sectorId") int sectorId);

	@Query("Select fc.formXpathScoreMapping.formXpath.label , fc.formXpathScoreMapping.formXpath.module ,"
			+ "(SUM(fc.score)/SUM(fc.formXpathScoreMapping.formXpath.maxScore))*100 "
			+ ",fc.formXpathScoreMapping.formXpath.shortName  "
			+ " FROM FacilityScore fc JOIN  fc.lastVisitData lvd "
			+ " WHERE  lvd.area.parentAreaId = :districtId "
			+ " AND lvd.isLive = true "
			+ " AND fc.formXpathScoreMapping.facilityType.facilityId = :facilityTypeId "
			+ " AND lvd.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " AND fc.formXpathScoreMapping.formXpath.module = :module "
			+ " GROUP BY fc.formXpathScoreMapping.formXpath.label , fc.formXpathScoreMapping.formXpath.module "
			+ " , fc.formXpathScoreMapping.formXpath.shortName"
			+ " ORDER BY fc.formXpathScoreMapping.formXpath.label  ASC ")
	@Override
	public List<Object[]> getSpiderDataForDistrictForSelectedFacility(
			@Param("districtId") int districtId,
			@Param("facilityTypeId") int facilityTypeId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("module") String module);

	@Query("Select fc.formXpathScoreMapping.formXpath.label , fc.formXpathScoreMapping.formXpath.module, "
			+ "(SUM(fc.score)/SUM(fc.formXpathScoreMapping.formXpath.maxScore))*100   "
			+ " , fc.formXpathScoreMapping.formXpath.shortName"
			+ " FROM FacilityScore fc JOIN  fc.lastVisitData lvd "
			+ " WHERE  lvd.area.parentAreaId = :districtId "
			+ " AND lvd.isLive = true "
			+ " AND lvd.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " AND fc.formXpathScoreMapping.formXpath.module = :module "
			+ " GROUP BY fc.formXpathScoreMapping.formXpath.label , fc.formXpathScoreMapping.formXpath.module "
			+ " , fc.formXpathScoreMapping.formXpath.shortName"
			+ " ORDER BY fc.formXpathScoreMapping.formXpath.label  ASC ")
	@Override
	public List<Object[]> getSpiderDataForDistrictOfAllIndicator(
			@Param("districtId") int districtId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("module") String module);

	@Query("Select fc.formXpathScoreMapping.formXpath.label , fc.formXpathScoreMapping.formXpath.module ,"
			+ "(SUM(fc.score)/SUM(fc.formXpathScoreMapping.formXpath.maxScore))*100   "
			+ " , fc.formXpathScoreMapping.formXpath.shortName"
			+ " FROM FacilityScore fc JOIN  fc.lastVisitData lvd "
			+ " WHERE  lvd.area.areaId = :areaId "
			+ " AND lvd.isLive = true "
			+ " AND fc.formXpathScoreMapping.facilityType.facilityId = :facilityTypeId "
			+ " AND lvd.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " AND fc.formXpathScoreMapping.formXpath.module = :module "
			+ " GROUP BY fc.formXpathScoreMapping.formXpath.label , fc.formXpathScoreMapping.formXpath.module "
			+ " , fc.formXpathScoreMapping.formXpath.shortName"
			+ " ORDER BY fc.formXpathScoreMapping.formXpath.label  ASC ")
	@Override
	public List<Object[]> getSpiderDataForLastVisitData(
			@Param("areaId") int areaId,
			@Param("facilityTypeId") int facilityTypeId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("module") String module);

	@Query("Select fc.formXpathScoreMapping.formXpath.label , fc.formXpathScoreMapping.formXpath.module ,"
			+ "(SUM(fc.score)/SUM(fc.formXpathScoreMapping.formXpath.maxScore))*100  "
			+ " , fc.formXpathScoreMapping.formXpath.shortName"
			+ " FROM FacilityScore fc JOIN  fc.lastVisitData lvd , Area area "
			+ " WHERE  area.parentAreaId = :stateId "
			+ " AND  lvd.area.parentAreaId = area.areaId "
			+ " AND lvd.isLive = true "
			+ " AND lvd.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " AND fc.formXpathScoreMapping.formXpath.module = :module "
			+ " GROUP BY fc.formXpathScoreMapping.formXpath.label , fc.formXpathScoreMapping.formXpath.module "
			+ " , fc.formXpathScoreMapping.formXpath.shortName"
			+ " ORDER BY fc.formXpathScoreMapping.formXpath.label  ASC ")
	@Override
	public List<Object[]> getSpiderDataForStateOfAllIndicator(
			@Param("stateId") int stateId, @Param("startDate") Date startDate,
			@Param("endDate") Date endDate, @Param("module") String module);

	@Query("Select fc.formXpathScoreMapping.formXpath.label , fc.formXpathScoreMapping.formXpath.module, "
			+ "(SUM(fc.score)/SUM(fc.formXpathScoreMapping.formXpath.maxScore))*100  "
			+ " , fc.formXpathScoreMapping.formXpath.shortName"
			+ " FROM FacilityScore fc JOIN  fc.lastVisitData lvd , Area area "
			+ " WHERE  area.parentAreaId = :stateId "
			+ " AND  lvd.area.parentAreaId = area.areaId "
			+ " AND fc.formXpathScoreMapping.facilityType.facilityId = :facilityTypeId "
			+ " AND lvd.isLive = true "
			+ " AND lvd.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " AND fc.formXpathScoreMapping.formXpath.module = :module "
			+ " GROUP BY fc.formXpathScoreMapping.formXpath.label , fc.formXpathScoreMapping.formXpath.module "
			+ " , fc.formXpathScoreMapping.formXpath.shortName"
			+ " ORDER BY fc.formXpathScoreMapping.formXpath.label  ASC ")
	@Override
	public List<Object[]> getSpiderDataForStateofSelectedFacility(
			@Param("stateId") int stateId,
			@Param("facilityTypeId") int facilityTypeId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("module") String module);

	@Query("SELECT AVG(((fc.score)/(fc.formXpathScoreMapping.formXpath.maxScore)))*100 ,lvd.area.areaId, fc.formXpathScoreMapping.facilityType.facilityId "
			+ " FROM FacilityScore fc JOIN  fc.lastVisitData lvd , Area area "
			+ " WHERE  area.parentAreaId = :stateId "
			+ " AND lvd.isLive = true "
			+ " AND lvd.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " AND  lvd.area.parentAreaId = area.areaId "
			+ " AND fc.formXpathScoreMapping.facilityType.facilityId = :facilityTypeId "
			+ " GROUP BY lvd.area.areaId , lvd.lastVisitDataId ,fc.formXpathScoreMapping.facilityType.facilityId ,area.areaName "
			+ " ORDER BY area.areaName")
	@Override
	public List<Object[]> getAdvancaeSearchDataForOverAllScoreofSelctedFacility(
			@Param("stateId") int stateId,
			@Param("facilityTypeId") int facilityTypeId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT AVG(((fc.score)/(fc.formXpathScoreMapping.formXpath.maxScore)))*100 ,lvd.area.areaId , fc.formXpathScoreMapping.facilityType.facilityId"
			+ " FROM FacilityScore fc JOIN  fc.lastVisitData lvd , Area area"
			+ " WHERE  area.parentAreaId = :stateId "
			+ " AND lvd.isLive = true "
			+ " AND lvd.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " AND  lvd.area.parentAreaId = area.areaId "
			+ " GROUP BY lvd.area.areaId , lvd.lastVisitDataId , fc.formXpathScoreMapping.facilityType.facilityId , area.areaName"
			+ " ORDER BY area.areaName")
	@Override
	public List<Object[]> getAdvanceSearchDataForOverAllScoreOfAllFacility(
			@Param("stateId") int stateId, @Param("startDate") Date startDate,
			@Param("endDate") Date endDate);

	@Query("SELECT (SUM(fc.score)/SUM(fc.formXpathScoreMapping.formXpath.maxScore))*100 ,lvd.area.areaId , fc.formXpathScoreMapping.facilityType.facilityId"
			+ " FROM FacilityScore fc JOIN  fc.lastVisitData lvd , Area area "
			+ " WHERE  area.parentAreaId = :stateId "
			+ " AND  lvd.area.parentAreaId = area.areaId "
			+ " AND lvd.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " AND lvd.isLive = true "
			+ " AND fc.formXpathScoreMapping.formXpath.label = :indicatorName "
			+ " GROUP BY lvd.area.areaId , lvd.lastVisitDataId , fc.formXpathScoreMapping.facilityType.facilityId , area.areaName"
			+ " ORDER BY area.areaName")
	@Override
	public List<Object[]> getAdvanceSearchDataForSelectedIndicatorOfAllFacility(
			@Param("stateId") int stateId,
			@Param("indicatorName") String indicatorName,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT (SUM(fc.score)/SUM(fc.formXpathScoreMapping.formXpath.maxScore))*100 ,lvd.area.areaId , fc.formXpathScoreMapping.facilityType.facilityId "
			+ " FROM FacilityScore fc JOIN  fc.lastVisitData lvd , Area area"
			+ " WHERE  area.parentAreaId = :stateId "
			+ " AND  lvd.area.parentAreaId = area.areaId "
			+ " AND lvd.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " AND lvd.isLive = true "
			+ " AND fc.formXpathScoreMapping.formXpathScoreId = :formXpathScoreMappingId "
			+ " GROUP BY lvd.area.areaId , lvd.lastVisitDataId , fc.formXpathScoreMapping.facilityType.facilityId , area.areaName"
			+ " ORDER BY area.areaName")
	@Override
	public List<Object[]> getAdvcanceSearchDataForSelectedFormXpathMapping(
			@Param("stateId") int stateId,
			@Param("formXpathScoreMappingId") int formXpathMapping,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Override
	@Query("SELECT DISTINCT(fc.lastVisitData.lastVisitDataId) "
			+ "FROM FacilityScore fc JOIN fc.formXpathScoreMapping fxm "
			+ " WHERE fxm.facilityType.facilityId = :facilityTypeId"
			+ " AND fc.lastVisitData.isLive = true")
	public List<Integer> findAllLastVisitDataForAfacility(
			@Param("facilityTypeId") int facilityTypeId);

	@Override
	@Query("SELECT AVG(((fc.score)/(fc.formXpathScoreMapping.formXpath.maxScore))*100) ,lvd.area.areaId, fc.formXpathScoreMapping.facilityType.facilityId "
			+ " FROM FacilityScore fc JOIN  fc.lastVisitData lvd "
			+ " WHERE  lvd.isLive = true "
			+ " AND lvd.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " AND  lvd.area.parentAreaId = :districtId "
			+ " AND fc.formXpathScoreMapping.facilityType.facilityId = :facilityTypeId "
			+ " GROUP BY lvd.area.areaId , lvd.lastVisitDataId ,fc.formXpathScoreMapping.facilityType.facilityId ,lvd.area.areaName "
			+ " ORDER BY lvd.area.areaName")
	public List<Object[]> getAdvancaeSearchDataForOverAllScoreofSelctedFacilityofDistrict(
			@Param("districtId") int districtId,
			@Param("facilityTypeId") int facilityTpeId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Override
	@Query("SELECT (SUM(fc.score)/SUM(fc.formXpathScoreMapping.formXpath.maxScore))*100 ,lvd.area.areaId , fc.formXpathScoreMapping.facilityType.facilityId"
			+ " FROM FacilityScore fc JOIN  fc.lastVisitData lvd "
			+ " WHERE   lvd.isLive = true "
			+ " AND lvd.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " AND  lvd.area.parentAreaId = :districtId "
			+ " GROUP BY lvd.area.areaId , lvd.lastVisitDataId , fc.formXpathScoreMapping.facilityType.facilityId ,lvd.area.areaName"
			+ " ORDER BY lvd.area.areaName")
	public List<Object[]> getAdvanceSearchDataForOverAllScoreOfAllFacilityOfDistrict(
			@Param("districtId")int districtId,@Param("startDate") Date startDate,@Param("endDate") Date endDate);

	@Override
	@Query("SELECT (SUM(fc.score)/SUM(fc.formXpathScoreMapping.formXpath.maxScore))*100 ,lvd.area.areaId , fc.formXpathScoreMapping.facilityType.facilityId"
			+ " FROM FacilityScore fc JOIN  fc.lastVisitData lvd "
			+ " WHERE  lvd.area.parentAreaId = :districtId "
			+ " AND lvd.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " AND lvd.isLive = true "
			+ " AND fc.formXpathScoreMapping.formXpath.label = :indicatorName "
			+ " GROUP BY lvd.area.areaId , lvd.lastVisitDataId , fc.formXpathScoreMapping.facilityType.facilityId ,lvd.area.areaName"
			+ " ORDER BY lvd.area.areaName")
	public List<Object[]> getAdvanceSearchDataForSelectedIndicatorOfAllFacilityofDistrict(
			@Param("districtId")int districtId,@Param("indicatorName") String formXpathLabel,@Param("startDate") Date startDate,@Param("endDate") Date endDate);

	@Override
	@Query("SELECT (SUM(fc.score)/SUM(fc.formXpathScoreMapping.formXpath.maxScore))*100 ,lvd.area.areaId , fc.formXpathScoreMapping.facilityType.facilityId "
			+ " FROM FacilityScore fc JOIN  fc.lastVisitData lvd "
			+ " WHERE  lvd.area.parentAreaId = :districtId "
			+ " AND lvd.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " AND lvd.isLive = true "
			+ " AND fc.formXpathScoreMapping.formXpathScoreId = :formXpathScoreMappingId "
			+ " GROUP BY lvd.area.areaId , lvd.lastVisitDataId , fc.formXpathScoreMapping.facilityType.facilityId ,lvd.area.areaName"
			+ " ORDER BY lvd.area.areaName")
	public List<Object[]> getAdvcanceSearchDataForSelectedFormXpathMappingofDistrict(
			@Param("districtId")int districtId,@Param("formXpathScoreMappingId") int formXpathScoreMappingId,@Param("startDate") Date startDate,
			@Param("endDate")Date endDate);
}
