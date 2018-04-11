/**
 * 
 */
package org.sdrc.slr.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.sdrc.slr.domain.UserCounter;

/**
 * @author Harsh Pratyush (harsh@sdrc.co.in)
 *
 */
public interface UserCounterRepository {

	/**
	 * find all
	 * @return
	 */
	List<UserCounter> findAll();

	/**
	 * save UserCounter
	 * @param userCounter
	 * @return
	 */
	@Transactional
	UserCounter save(UserCounter userCounter);

}
