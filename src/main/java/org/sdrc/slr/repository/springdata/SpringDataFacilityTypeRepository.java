/**
 * 
 */
package org.sdrc.slr.repository.springdata;

import org.sdrc.slr.domain.FacilityType;
import org.sdrc.slr.repository.FacilityTypeRepository;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * @author Harsh Pratyush
 *
 */

@RepositoryDefinition(domainClass=FacilityType.class,idClass=Integer.class)
public interface SpringDataFacilityTypeRepository extends
		FacilityTypeRepository {

}
