/**
 * 
 */
package org.sdrc.slr.repository;

import java.util.List;

import org.sdrc.slr.domain.FacilityType;

/**
 * @author Harsh Pratyush
 *
 */
public interface FacilityTypeRepository {
	
	/**
	 * fetch all facility type
	 * @return
	 */
	List<FacilityType> findAll();

}
