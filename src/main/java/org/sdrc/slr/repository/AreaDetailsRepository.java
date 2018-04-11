package org.sdrc.slr.repository;

import java.util.List;

import org.sdrc.slr.domain.Area;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Harsh Pratyush
 *
 */
public interface AreaDetailsRepository {

	/**
	 * Find all area
	 * @return
	 */
	public List<Area> findAll();

	/**
	 * persist other area
	 * @param area
	 * @return
	 */
	@Transactional
	public Area save(Area area);

	/**
	 * This method will return the facility list within a district ordered by
	 * their area code in descending format
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Area> findTopOneByParentAreaIdOrderByAreaCodeDesc(
			Integer parentId);

	/**
	 * fetch area list by parent id
	 * @param stateId
	 * @return
	 */
	public List<Area> findByParentAreaId(int stateId);

	/**
	 * fetch area by id
	 * @param stateId
	 * @return
	 */
	public Area findByAreaId(int stateId);

}
