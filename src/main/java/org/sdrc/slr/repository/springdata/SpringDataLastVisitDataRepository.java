package org.sdrc.slr.repository.springdata;

import java.sql.Date;
import java.util.List;

import org.sdrc.slr.domain.LastVisitData;
import org.sdrc.slr.repository.LastVisitDataRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

/**
 * 
 * @author Harsh Pratyush(harsh@sdrc.co.in)
 *
 */
@RepositoryDefinition(domainClass = LastVisitData.class, idClass = Integer.class)
public interface SpringDataLastVisitDataRepository extends
		LastVisitDataRepository {

	/* (non-Javadoc)
	 * @see org.sdrc.slr.repository.LastVisitDataRepository#findAreaIdsForATimePeriod(java.sql.Date, java.sql.Date)
	 * find all active LastVisitData area id for a given markedAsCompleteDate range
	 */
	@Override
	@Query("SELECT lvd.area.areaId from LastVisitData lvd where lvd.markedAsCompleteDate between :startDate and :endDate"
			+ " AND lvd.isLive=True")
	public List<Integer> findAreaIdsForATimePeriod(@Param("startDate")Date startDate,@Param("endDate") Date endDate);

/*	@Override
	@Query(" SELECT rs.score , rs.lastVisitData.area.areaId FROM LastVisitData lvd,RawDataScore rs "
			+ " WHERE rs.lastVisitData.area.areaId in(SELECT lvd.area.areaId from LastVisitData lvd where lvd.markedAsCompleteDate between :startDate and :endDate)"
			+ " AND rs.rawFormXapths.xPathId IN :imageXpathIds")
	public List<Object[]> findOldPicPaths(Date startDate, Date endDate,
			List<Integer> imageXpathIds);*/
}
