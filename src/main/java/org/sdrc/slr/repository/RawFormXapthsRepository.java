package org.sdrc.slr.repository;

import java.util.List;

import org.sdrc.slr.domain.RawFormXapths;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Harsh Pratyush
 *
 */
public interface RawFormXapthsRepository {

	/**
	 * save RawFormXapths
	 * @param rawFormXapths
	 */
	@Transactional
	void save(RawFormXapths rawFormXapths);

	/**
	 * Find all
	 * @return
	 */
	List<RawFormXapths> findAll();

}
