/**
 * 
 */
package org.sdrc.slr.model;


/**
 * @author Harsh Pratyush (harsh@sdrc.co.in)
 *
 */
public class SectionsModel {

	int sectorId;

	String sectorName;

	// -1 for the main sector

	String parentSectorId;

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
