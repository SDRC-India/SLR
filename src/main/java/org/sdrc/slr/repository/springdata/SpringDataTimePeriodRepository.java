package org.sdrc.slr.repository.springdata;

import org.sdrc.slr.domain.TimePeriod;
import org.sdrc.slr.repository.TimePeriodRepository;
import org.springframework.data.repository.RepositoryDefinition;
/**
 * 
 * @author Harsh Pratyush (harsh@sdrc.co.in)
 *
 */
@RepositoryDefinition(domainClass=TimePeriod.class,idClass=Integer.class)
public interface SpringDataTimePeriodRepository extends TimePeriodRepository{

}
