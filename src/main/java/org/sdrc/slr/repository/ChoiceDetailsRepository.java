/**
 * 
 */
package org.sdrc.slr.repository;

import java.util.List;

import org.sdrc.slr.domain.ChoicesDetails;

/**
 * @author Harsh Pratyush (harsh@sdrc.co.in)
 *
 */
public interface ChoiceDetailsRepository {
	
	/**
	 * save entity for other choice
	 * @param choicesDetails
	 */
	void save(ChoicesDetails choicesDetails);


	/**
	 * find all choice master data
	 * @return
	 */
	List<ChoicesDetails> findAll();

}
