package org.sdrc.slr.repository.springdata;

import org.sdrc.slr.domain.UserCounter;
import org.sdrc.slr.repository.UserCounterRepository;
import org.springframework.data.repository.RepositoryDefinition;



/**
 * 
 * @author Harsh Pratyush(harsh@sdrc.co.in)
 *
 */
@RepositoryDefinition(domainClass=UserCounter.class,idClass=Integer.class)
public interface SpringDataUserCounterRepository extends UserCounterRepository {

}
