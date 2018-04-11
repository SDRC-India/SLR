package org.sdrc.slr.repository.springdata;

import org.sdrc.slr.domain.XForm;
import org.sdrc.slr.repository.XFormReposisotry;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * 
 * @author Harsh Pratyush(harsh@sdrc.co.in)
 *
 */
@RepositoryDefinition(domainClass=XForm.class ,idClass=Integer.class)
public interface SpringDataXFormRepository extends XFormReposisotry{

}
