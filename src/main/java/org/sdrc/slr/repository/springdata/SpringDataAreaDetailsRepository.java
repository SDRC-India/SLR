 package org.sdrc.slr.repository.springdata;

import org.sdrc.slr.domain.Area;
import org.sdrc.slr.repository.AreaDetailsRepository;
import org.springframework.data.repository.RepositoryDefinition;
/**
 * 
 * @author Harsh Pratyush(harsh@sdrc.co.in)
 *
 */
@RepositoryDefinition(domainClass=Area.class,idClass=Integer.class)
public interface SpringDataAreaDetailsRepository extends AreaDetailsRepository{

}
