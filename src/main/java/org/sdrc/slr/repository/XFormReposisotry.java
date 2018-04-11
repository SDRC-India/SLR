/**
 * 
 */
package org.sdrc.slr.repository;

import java.util.List;

import org.sdrc.slr.domain.XForm;

/**
 * @author Harsh Pratyush
 *
 */
public interface XFormReposisotry {
	
	/**
	 * find all active xforms
	 * @return
	 */
	List<XForm> findByIsLiveTrue();

}
