/**
 * 
 */
package org.sdrc.slr.repository;

import java.util.List;

import org.sdrc.slr.domain.Sections;



/**
 * @author Harsh Pratyush (harsh@sdrc.co.in)
 *
 */
public interface SectionsRepository {

	List<Sections> findAll();

}
