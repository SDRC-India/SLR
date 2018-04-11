package org.sdrc.slr.model;


/**
 * 
 * @author Harsh Pratyush
 *
 */
public class FormXpathModel {
	
   private int formXpathId;
	
	private String xPath;
	
	private Double maxScore;
	
	private String label;
	
	private String type;
	
	private String module;
	
	private int parentXpath;
	
	private int  formXpathScoreMappingId;

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

	public int getParentXpath() {
		return parentXpath;
	}

	public void setParentXpath(int parentXpath) {
		this.parentXpath = parentXpath;
	}

	public int getFormXpathScoreMappingId() {
		return formXpathScoreMappingId;
	}

	public void setFormXpathScoreMappingId(int formXpathScoreMappingId) {
		this.formXpathScoreMappingId = formXpathScoreMappingId;
	}

}
