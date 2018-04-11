package org.sdrc.slr.web;

import org.sdrc.slr.service.MasterRawDataService;
import org.sdrc.slr.service.ODKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author Harsh Pratyush(harsh@sdrc.co.in)
 *
 */

@Controller
public class ODKController {
	
	@Autowired
	ODKService odkService;
	
	@Autowired
	MasterRawDataService masterRawDataService;

	@RequestMapping("updateFacilityScore")
	@ResponseBody
	public boolean updateFacilityScore() throws Exception {
		try
		{
		return odkService.updateFacilityScore();
		}
		catch(Exception e)
		{
			return false;
		}
		}
	/**
	 This controller will call the persistData for the raw data upadet in db
	 * followed by excel generation
	 *
	 */
	
	@RequestMapping("updateRawData")
	@ResponseBody
	public boolean updateRawData() throws Exception {
		try
		{
			masterRawDataService.persistRawData();
			masterRawDataService.generateExcel();
	
		 return true;
		}
		catch(Exception e)
		{			
			e.printStackTrace();
			return false;
		} 
	}
	
	
	@RequestMapping("generateExcel")
	@ResponseBody
	public boolean generateExcel() throws Exception {
		try
		{
			masterRawDataService.generateExcel();
	
		 return true;
		}
		catch(Exception e)
		{			
			e.printStackTrace();
			return false;
		} 
	}

}
