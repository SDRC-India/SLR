package org.sdrc.slr.model;

import java.util.List;

/**
 * 
 * @author Harsh Pratyush
 *
 */
public class SectorModel 
{
	private int sectorId;
	
	private int formId;
	
	private String sectorName;
	
	private List<FormXpathModel> formXpathModel;

	public int getSectorId() {
		return sectorId;
	}

	public void setSectorId(int sectorId) {
		this.sectorId = sectorId;
	}

	public int getFormId() {
		return formId;
	}

	public void setFormId(int formId) {
		this.formId = formId;
	}

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public List<FormXpathModel> getFormXpathModel() {
		return formXpathModel;
	}

	public void setFormXpathModel(List<FormXpathModel> formXpathModel) {
		this.formXpathModel = formXpathModel;
	}

}
