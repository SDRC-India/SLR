package org.sdrc.slr.repository;

import java.util.List;

import org.sdrc.slr.domain.TimePeriod;

/**
 * 
 * @author Harsh Pratyush
 *
 */
public interface TimePeriodRepository {
	
	/**
	 * find all
	 * @return
	 */
	List<TimePeriod> findAll();

	/**
	 * fetch by timeperiod id
	 * @param timeperiodId
	 * @return
	 */
	TimePeriod findByTimePeriodId(int timeperiodId);

	/**
	 * fetch by order by StartDate descending
	 * @return
	 */
	List<TimePeriod> findByOrderByStartDateDesc();

}
