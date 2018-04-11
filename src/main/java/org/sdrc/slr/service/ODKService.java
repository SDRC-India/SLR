package org.sdrc.slr.service;

/**
 * 
 * @author Harsh Pratyush
 *
 */
public interface ODKService {
	/**
	 * This method will update the facility score  for each new submission
	 * @return
	 * @throws Exception
	 */
	boolean updateFacilityScore() throws Exception;

}
