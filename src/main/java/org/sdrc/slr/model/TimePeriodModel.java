/**
 * 
 */
package org.sdrc.slr.model;

/**
 * @author Harsh(harsh@sdrc.co.in)
 *
 */
public class TimePeriodModel 
{
 
	private int timePeriodId;
	
	private String startDate;
	
	private String endDate;
	 
	private String timeperiod;
	
	private int perodicity;
	
	private String shortName;

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public int getTimePeriodId() {
		return timePeriodId;
	}

	public void setTimePeriodId(int timePeriodId) {
		this.timePeriodId = timePeriodId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTimeperiod() {
		return timeperiod;
	}

	public void setTimeperiod(String timeperiod) {
		this.timeperiod = timeperiod;
	}

	public int getPerodicity() {
		return perodicity;
	}

	public void setPerodicity(int perodicity) {
		this.perodicity = perodicity;
	}



}
