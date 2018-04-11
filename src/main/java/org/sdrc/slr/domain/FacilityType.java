package org.sdrc.slr.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author Harsh Pratyush(harsh@sdrc.co.in)
 * @since version 1.0.0
 * contains the facility type (CHC/ PHC etc)
 * 
 */

@Entity
@Table
public class FacilityType {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int facilityId;
	
	
	@Column(name="Facility_Name")
	private String facilityName;
	
	@ManyToOne
	@JoinColumn(name="formId")
	private XForm form; 
	
	@OneToMany(mappedBy="facilityType")
	private List<FormXpathScoreMapping> formXpathScoreMappings;
	
	@OneToMany(mappedBy="facilityType")
	private List<SectionRawXpathsMapping> sectionRawXpathsMappping;

	public List<SectionRawXpathsMapping> getSectionRawXpathsMappping() {
		return sectionRawXpathsMappping;
	}

	public void setSectionRawXpathsMappping(
			List<SectionRawXpathsMapping> sectionRawXpathsMappping) {
		this.sectionRawXpathsMappping = sectionRawXpathsMappping;
	}

	public int getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(int facilityId) {
		this.facilityId = facilityId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public XForm getForm() {
		return form;
	}

	public void setForm(XForm form) {
		this.form = form;
	}

	public List<FormXpathScoreMapping> getFormXpathScoreMappings() {
		return formXpathScoreMappings;
	}

	public void setFormXpathScoreMappings(
			List<FormXpathScoreMapping> formXpathScoreMappings) {
		this.formXpathScoreMappings = formXpathScoreMappings;
	}

}
