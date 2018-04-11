package org.sdrc.slr.service;

/**
 * 
 * @author Harsh Pratyush(harsh@sdrc.co.in)
 *
 */
public interface MasterRawDataService {

	/**
	 * This method will be used to read the xfrom excel and persist the
	 * corresponding xpath in Database
	 * 
	 * @return
	 * @throws Exception 
	 */
	public boolean generateXpath() throws Exception;

	/**
	 * it will perisit raw data for each xpath for each submission
	 * 
	 * @return
	 * @throws Exception 
	 */
	public boolean persistRawData() throws Exception;
	
	/**
	 * This will generate raw data of all submission when user will request for generation of excel
	 * @return
	 * @throws Exception
	 */
	public String generateExcel() throws Exception;

	/**
	 * Persisting phase1 data from excel file
	 * @throws Exception
	 */
	void persisitPhase1Data() throws Exception;

	/**
	 * Persist PHASE1 data for dashboard
	 * @throws Exception
	 */
	void persisitPhase1DataDashboard() throws Exception;

}
