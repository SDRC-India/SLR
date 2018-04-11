/**
 * 
 */
package org.sdrc.slr.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Harsh Pratyush (harsh@sdrc.co.in)
 * conatins all the sections present in the xfrom
 *
 */
@Entity
@Table(name="Sections")
public class Sections implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int sectorId;
	
	@Column(name="Sector_Name" , nullable=false)
	String sectorName;
	
	// -1 for the main sector
	@Column(name="Parent_Sector_Id" , nullable=false)
	String parentSectorId;
	
	@OneToMany(mappedBy="sections")
	private List<SectionRawXpathsMapping> sectionRawXpathsMappping;

	public List<SectionRawXpathsMapping> getSectionRawXpathsMappping() {
		return sectionRawXpathsMappping;
	}

	public void setSectionRawXpathsMappping(
			List<SectionRawXpathsMapping> sectionRawXpathsMappping) {
		this.sectionRawXpathsMappping = sectionRawXpathsMappping;
	}

	public int getSectorId() {
		return sectorId;
	}

	public void setSectorId(int sectorId) {
		this.sectorId = sectorId;
	}

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public String getParentSectorId() {
		return parentSectorId;
	}

	public void setParentSectorId(String parentSectorId) {
		this.parentSectorId = parentSectorId;
	}

}
