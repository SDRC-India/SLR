/**
 * 
 */
package org.sdrc.slr.repository.springdata;

import java.util.List;

import org.sdrc.slr.domain.FormXpath;
import org.sdrc.slr.repository.FormXpathRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * @author Harsh Pratyush
 *
 */

@RepositoryDefinition(domainClass=FormXpath.class,idClass=Integer.class)
public interface SpringDataFormXpathRepository extends FormXpathRepository {
	
	@Query("SELECT DISTINCT(form.label) , form.formXpathId  FROM FormXpath form ORDER BY form.formXpathId ")
	@Override
	public List<Object []> findDistinctFormXpathLabel();

}
