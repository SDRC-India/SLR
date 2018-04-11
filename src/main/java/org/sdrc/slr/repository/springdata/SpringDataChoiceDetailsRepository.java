/**
 * 
 */
package org.sdrc.slr.repository.springdata;

import org.sdrc.slr.domain.ChoicesDetails;
import org.sdrc.slr.repository.ChoiceDetailsRepository;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * @author Harsh Pratyush (harsh@sdrc.co.in)
 *
 */
@RepositoryDefinition(domainClass=ChoicesDetails.class,idClass=Integer.class)
public interface SpringDataChoiceDetailsRepository extends
		ChoiceDetailsRepository {

}
