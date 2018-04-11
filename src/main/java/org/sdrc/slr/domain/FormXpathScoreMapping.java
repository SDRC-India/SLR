package org.sdrc.slr.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This domain will contain the mapping between FacilityType and its  indicator(FormXpath)
 * @author Harsh Pratyush(harsh@sdrc.co.in)
 * @since version 1.0.0
 * 
 */

@Entity
@Table
public class FormXpathScoreMapping implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int formXpathScoreId;
	
	@ManyToOne
	@JoinColumn(name="facilityType")
	private FacilityType facilityType;
	
	@ManyToOne
	@JoinColumn(name="formXpath")
	private FormXpath formXpath;
	
	
	@OneToMany(mappedBy="formXpathScoreMapping")
	private List<FacilityScore> facilityScores;
	
	public FacilityType getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(FacilityType facilityType) {
		this.facilityType = facilityType;
	}

	public FormXpath getFormXpath() {
		return formXpath;
	}

	public void setFormXpath(FormXpath formXpath) {
		this.formXpath = formXpath;
	}

	public int getFormXpathScoreId() {
		return formXpathScoreId;
	}

	public void setFormXpathScoreId(int formXpathScoreId) {
		this.formXpathScoreId = formXpathScoreId;
	}

	public List<FacilityScore> getFacilityScores() {
		return facilityScores;
	}

	public void setFacilityScores(List<FacilityScore> facilityScores) {
		this.facilityScores = facilityScores;
	}

	
	

}
