/**
 * 
 */
package org.sdrc.slr.repository.springdata;

import org.sdrc.slr.domain.Sections;
import org.sdrc.slr.repository.SectionsRepository;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * @author Harsh Pratyush (harsh@sdrc.co.in)
 *
 */

@RepositoryDefinition(domainClass=Sections.class,idClass=Integer.class)
public interface SpringDataSectionsRepository extends SectionsRepository {

}
