package org.sdrc.slr.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.sdrc.slr.service.MasterRawDataService;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 
 * @author Harsh Pratyush(harsh@sdrc.co.in)
 * @since version 1.0.0
 * this job will get called everyday 12:01 AM and will persist raw data for each xpath for each submission
 * 
 */
public class EveryDayJob extends QuartzJobBean{
	
	private MasterRawDataService masterRawDataService;

	public void setMasterRawDataService(MasterRawDataService masterRawDataService) {
		this.masterRawDataService = masterRawDataService;
	}
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		try {
			masterRawDataService.persistRawData();
			masterRawDataService.generateExcel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
