/**
 * 
 */
package org.sdrc.slr.repository.springdata;

import java.sql.Date;
import java.util.List;

import org.sdrc.slr.domain.RawDataScore;
import org.sdrc.slr.repository.RawDataScoreRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

/**
 * @author Harsh Pratyush(harsh@sdrc.co.in)
 *
 */

@RepositoryDefinition(domainClass = RawDataScore.class, idClass = Integer.class)
public interface SpringDataRawDataScoreRepository extends
		RawDataScoreRepository {

	@Override
	@Query("SELECT srxm.indicatorLabel, SUM((CAST (rds.score AS float))) FROM RawDataScore rds JOIN rds.rawFormXapths rfx , SectionRawXpathsMapping srxm"
			+ " WHERE srxm.facilityType.facilityId = :facilityTypeId "
			+ " AND srxm.sections.sectorId = :sectionId "
			+ " AND rfx.xPathId = srxm.rawFormXapths.xPathId "
			+ " AND rds.lastVisitData.lastVisitDataId IN :lastVisitDataIds "
			+ " AND rds.lastVisitData.area.parentAreaId = :districtId "
			+ " AND rfx.type NOT LIKE 'select_one yes_no%' "
			+ " AND rds.score NOT LIKE '%NaN%'"
			+ " AND  ISNUMERIC(rds.score) = 1 "
			+ " AND rds.lastVisitData.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " GROUP BY srxm.sectionRawXpathsMappingId , srxm.indicatorLabel ")
	public List<Object[]> findByTimeperiodANdLastVisitIdAndIntegerType(
			@Param("districtId") Integer districtId,
			@Param("lastVisitDataIds") List<Integer> lastVisitDataIds,
			@Param("sectionId") int sectionId,
			@Param("facilityTypeId") int facilityTypeId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Override
	@Query("SELECT srxm.indicatorLabel, COUNT(case when rds.score = 'YES' then 1 END) FROM RawDataScore rds JOIN rds.rawFormXapths rfx , SectionRawXpathsMapping srxm"
			+ " WHERE srxm.facilityType.facilityId = :facilityTypeId "
			+ " AND srxm.sections.sectorId = :sectionId "
			+ " AND rfx.xPathId = srxm.rawFormXapths.xPathId "
			+ " AND rds.lastVisitData.lastVisitDataId IN :lastVisitDataIds "
			+ " AND rfx.type LIKE 'select_one yes_no%' "
			+ " AND rds.lastVisitData.area.parentAreaId = :districtId "
			+ " AND rds.lastVisitData.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " GROUP BY srxm.sectionRawXpathsMappingId , srxm.indicatorLabel ")
	public List<Object[]> findByTimeperiodANdLastVisitIdAndYESNoType(
			@Param("districtId") int districtId,
			@Param("lastVisitDataIds") List<Integer> lastVisitDataIds,
			@Param("sectionId") int sectionId,
			@Param("facilityTypeId") int facilityTypeId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Override
	@Query("SELECT srxm.indicatorLabel, rds.score ,rds.lastVisitData.area.areaId FROM RawDataScore rds JOIN rds.rawFormXapths rfx , SectionRawXpathsMapping srxm"
			+ " WHERE srxm.facilityType.facilityId = :facilityTypeId "
			+ " AND srxm.sections.sectorId = :sectionId "
			+ " AND rfx.xPathId = srxm.rawFormXapths.xPathId "
			+ " AND rds.lastVisitData.lastVisitDataId IN :lastVisitDataIds "
			+ " AND rds.lastVisitData.area.parentAreaId = :districtId "
			+ " AND rds.lastVisitData.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " GROUP BY srxm.sectionRawXpathsMappingId , srxm.indicatorLabel , rds.lastVisitData.area.areaId , rds.score"
			+ " ORDER BY  rds.lastVisitData.area.areaId ")
	public List<Object[]> findFacilitySubmissionForATimePerodAndDistrict(
			@Param("districtId") Integer districtId,
			@Param("lastVisitDataIds") List<Integer> lastVisitDataIds,
			@Param("sectionId") int sectionId,
			@Param("facilityTypeId") int facilityTypeId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Override
	@Query("SELECT srxm.indicatorLabel, SUM((CAST (rds.score AS float))) FROM RawDataScore rds JOIN rds.rawFormXapths rfx , SectionRawXpathsMapping srxm"
			+ " WHERE srxm.sections.sectorId = :sectionId "
			+ " AND rfx.xPathId = srxm.rawFormXapths.xPathId "
			+ " AND rds.lastVisitData.area.parentAreaId = :districtId "
			+ " AND rfx.type NOT LIKE 'select_one yes_no%' "
			+ " AND rds.score NOT LIKE '%NaN%'"
			+ " AND  ISNUMERIC(rds.score) = 1 "
			+ " AND rds.lastVisitData.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " GROUP BY srxm.sectionRawXpathsMappingId , srxm.indicatorLabel ")
	public List<Object[]> findByTimeperiodANdLastVisitIdAndIntegerTypeForAllSector(
			@Param("districtId") Integer districtId,
			@Param("sectionId") int sectionId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Override
	@Query("SELECT srxm.indicatorLabel, COUNT(case when rds.score = 'YES' then 1 END) FROM RawDataScore rds JOIN rds.rawFormXapths rfx , SectionRawXpathsMapping srxm"
			+ " WHERE srxm.sections.sectorId = :sectionId "
			+ " AND rfx.xPathId = srxm.rawFormXapths.xPathId "
			+ " AND rfx.type LIKE 'select_one yes_no%' "
			+ " AND rds.lastVisitData.area.parentAreaId = :districtId "
			+ " AND rds.lastVisitData.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " GROUP BY srxm.sectionRawXpathsMappingId , srxm.indicatorLabel ")
	public List<Object[]> findByTimeperiodANdLastVisitIdAndYESNoTypeForAllSector(
			@Param("districtId") int districtId,
			@Param("sectionId") int sectionId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Override
	@Query("SELECT srxm.indicatorLabel, rds.score ,rds.lastVisitData.area.areaId FROM RawDataScore rds JOIN rds.rawFormXapths rfx , SectionRawXpathsMapping srxm"
			+ " WHERE srxm.sections.sectorId = :sectionId "
			+ " AND rfx.xPathId = srxm.rawFormXapths.xPathId "
			+ " AND rds.lastVisitData.area.parentAreaId = :districtId "
			+ " AND rds.lastVisitData.markedAsCompleteDate BETWEEN :startDate AND :endDate "
			+ " GROUP BY srxm.sectionRawXpathsMappingId , srxm.indicatorLabel , rds.lastVisitData.area.areaId , rds.score"
			+ " ORDER BY  rds.lastVisitData.area.areaId ")
	public List<Object[]> findFacilitySubmissionForATimePeriodAndDistrictOfAllSections(
			@Param("districtId") int districtId,
			@Param("sectionId") int sectionId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
