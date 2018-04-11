/**
 * 
 */
package org.sdrc.slr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Harsh Pratyush (harsh@sdrc.co.in)
 *contains mapping between Sections and RawFormXapths
 */

@Entity
@Table
public class SectionRawXpathsMapping {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int sectionRawXpathsMappingId;
	
	@Column(name="Indicator_Label")
	String indicatorLabel;
	
	@ManyToOne
	@JoinColumn(name="facilityType")
	private FacilityType facilityType;
	
	@ManyToOne
	@JoinColumn(name="sections")
	private Sections sections;
	
	@ManyToOne
	@JoinColumn(name="xPathId")
	private RawFormXapths rawFormXapths;

	public int getSectionRawXpathsMappingId() {
		return sectionRawXpathsMappingId;
	}

	public void setSectionRawXpathsMappingId(int sectionRawXpathsMappingId) {
		this.sectionRawXpathsMappingId = sectionRawXpathsMappingId;
	}

	public String getIndicatorLabel() {
		return indicatorLabel;
	}

	public void setIndicatorLabel(String indicatorLabel) {
		this.indicatorLabel = indicatorLabel;
	}

	public FacilityType getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(FacilityType facilityType) {
		this.facilityType = facilityType;
	}

	public Sections getSections() {
		return sections;
	}

	public void setSections(Sections sections) {
		this.sections = sections;
	}

	public RawFormXapths getRawFormXapths() {
		return rawFormXapths;
	}

	public void setRawFormXapths(RawFormXapths rawFormXapths) {
		this.rawFormXapths = rawFormXapths;
	}
	
	
}
