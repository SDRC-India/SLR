/**
 * Return the spider data for dashboard
 * 
 * @author Harsh(harsh@sdrc.co.in)
 * 
 */
/**
 */
package org.sdrc.slr.model;
/**
 * 
 * @author Harsh Pratyush (harsh@sdrc.co.in)
 *
 */
public class SpiderDataModel {
	
	String axis ;
	String value;
	String timePeriod;
	
	String shortName;
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	/**
	 * @return the axis
	 */
	public String getAxis() {
		return axis;
	}
	/**
	 * @param axis the axis to set
	 */
	public void setAxis(String axis) {
		this.axis = axis;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	public String getTimePeriod() {
		return timePeriod;
	}
	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}
	
	

}
