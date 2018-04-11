package org.sdrc.slr.repository;

import java.util.List;

/**
 * 
 * @author Harsh Pratyush
 *
 */
public interface FormXpathRepository {

	/**
	 * This will return the Name of each DISTINCT indicator name 
	 * @return
	 */
	List<Object[]> findDistinctFormXpathLabel();
}
