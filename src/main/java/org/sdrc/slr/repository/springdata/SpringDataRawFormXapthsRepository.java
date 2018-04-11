/**
 * 
 */
package org.sdrc.slr.repository.springdata;

import org.sdrc.slr.domain.RawFormXapths;
import org.sdrc.slr.repository.RawFormXapthsRepository;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * @author Harsh Pratyush
 *
 */
@RepositoryDefinition(domainClass=RawFormXapths.class,idClass=Integer.class)
public interface SpringDataRawFormXapthsRepository extends
		RawFormXapthsRepository {

}
