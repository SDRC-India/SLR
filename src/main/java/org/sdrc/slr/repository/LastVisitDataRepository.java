/**
 * 
 */
package org.sdrc.slr.repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.sdrc.slr.domain.LastVisitData;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Harsh Pratyush
 *
 */
public interface LastVisitDataRepository {
	List<LastVisitData> findAllByIsLiveTrue();

	/**
	 * save LastVisitData
	 * @param lvd
	 * @return
	 */
	@Transactional
	LastVisitData save(LastVisitData lvd);
	
	/**
	 * find LastVisitData by id
	 * @param lastVisitDataId
	 * @return
	 */
	LastVisitData findByLastVisitDataId(int lastVisitDataId);

	/**
	 * find all
	 * @return
	 */
	List<LastVisitData> findAll();

	/**
	 * fetch all active data order by MarkedAsCompleteDate
	 * @return
	 */
	List<LastVisitData> findAllByIsLiveTrueOrderByMarkedAsCompleteDateDesc();

	/**
	 * fetch all active data order by MarkedAsCompleteDate ascending
	 * @return
	 */
	List<LastVisitData> findAllByIsLiveTrueOrderByMarkedAsCompleteDateAsc();

	/**
	 * fetch all active data for a given markedAsCompleteDate
	 * @param markedAsCompleteDate
	 * @return
	 */
	List<LastVisitData> findAllByIsLiveTrueAndMarkedAsCompleteDate(
			Timestamp markedAsCompleteDate);

	/**find all active LastVisitData area id for a given markedAsCompleteDate range
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Integer> findAreaIdsForATimePeriod(Date startDate, Date endDate);

/*	List<Object[]> findOldPicPaths(Date startDate, Date endDate,
			List<Integer> imageXpathIds);*/

}
