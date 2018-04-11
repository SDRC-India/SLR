/**
 * 
 */
package org.sdrc.slr.service;

import java.util.List;

import org.sdrc.slr.model.SectionsModel;

/**
 * @author Harsh Pratyush (harsh@sdrc.co.in)
 * contains all the methods releated to summary report
 *
 */
public interface SummaryReportService {

	/**
	 * 
	 * @return This method will give the List of SectionsModel containing the
	 *         Sections
	 */
	public List<SectionsModel> getAllSections();

	/**
	 * This method is responsible for the generation of summary report for a
	 * seleected facilityType for a selected sections within a timeperiod of a
	 * state in district wise order
	 * 
	 * @param facilityTypeId
	 * @param sectionId
	 * @param timePeriodId
	 * @param stateId
	 * @return
	 */
	public Object getSummaryData(int facilityTypeId, int sectionId,
			int timePeriodId, int stateId);

	/**
	 * This method will return the submission of the data of for a district of
	 * each facility for a facility level and sector and timeperiod
	 * 
	 * @param facilityTypeId
	 * @param sectionId
	 * @param timePeriodId
	 * @param districtId
	 * @return
	 */
	public Object getfacilityData(int facilityTypeId, int sectionId,
			int timePeriodId, int districtId);

}
