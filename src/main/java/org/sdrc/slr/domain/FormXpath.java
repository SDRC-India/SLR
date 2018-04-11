package org.sdrc.slr.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author Harsh Pratyush(harsh@sdrc.co.in)
 * @since version 1.0.0
 * This entity contains aggregate xpath present in the xform
 * 
 */
@Entity
@Table
public class FormXpath implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int formXpathId;
	
	private String xPath;
	
	private Double maxScore;
	
	private String label;
	
	private String type;
	
	private String module;
	
	private String shortName;
	
	private int parentXpath;
	
	@OneToMany(mappedBy="formXpath")
	private List<FormXpathScoreMapping> formXpathScoreMappings;

	public List<FormXpathScoreMapping> getFormXpathScoreMappings() {
		return formXpathScoreMappings;
	}

	public void setFormXpathScoreMappings(
			List<FormXpathScoreMapping> formXpathScoreMappings) {
		this.formXpathScoreMappings = formXpathScoreMappings;
	}

	public int getParentXpath() {
		return parentXpath;
	}

	public void setParentXpath(int parentXpath) {
		this.parentXpath = parentXpath;
	}

	public int getFormXpathId() {
		return formXpathId;
	}

	public void setFormXpathId(int formXpathId) {
		this.formXpathId = formXpathId;
	}

	public String getxPath() {
		return xPath;
	}

	public void setxPath(String xPath) {
		this.xPath = xPath;
	}

	public Double getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Double maxScore) {
		this.maxScore = maxScore;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

}
