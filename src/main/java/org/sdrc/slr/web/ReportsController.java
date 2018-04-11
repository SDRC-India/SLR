package org.sdrc.slr.web;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.sdrc.slr.model.SectionsModel;
import org.sdrc.slr.service.MasterRawDataService;
import org.sdrc.slr.service.SummaryReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author Harsh Pratyush
 * this controller belongs to report jsp
 *
 */

@Controller
public class ReportsController {

	@Autowired
	MasterRawDataService masterRawDataService;

	@Autowired
	SummaryReportService summaryReportService;

	@Autowired
	ResourceBundleMessageSource applicationMessageSource;

	@Autowired
	private ServletContext context;

	@RequestMapping("/generateRawData")
	@ResponseBody
	public String generateRawData() {
	
		return context.getRealPath(applicationMessageSource.getMessage(
				"rawDataExcelPath", null, null));
	}

	/**
	 * 
	 * @param name
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/downloadRawData", method = RequestMethod.POST)
	public void downLoad(@RequestParam("fileName") String name,
			HttpServletResponse response) throws Exception {
		InputStream inputStream;
		String fileName = "";
		try {
			fileName = name.replaceAll("%3A", ":").replaceAll("%2F", "/")
					.replaceAll("%5C", "/").replaceAll("%2C", ",")
					.replaceAll("\\+", " ").replaceAll("%22", "")
					.replaceAll("%3F", "?").replaceAll("%3D", "=");
			inputStream = new FileInputStream(fileName);
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"",
					new java.io.File(fileName).getName());
			response.setHeader(headerKey, headerValue);
			response.setContentType("application/octet-stream"); // for all file
																	// type
			ServletOutputStream outputStream = response.getOutputStream();
			FileCopyUtils.copy(inputStream, outputStream);
			outputStream.close();
		} catch (FileNotFoundException e) {

			fileName = masterRawDataService.generateExcel();

			fileName = name.replaceAll("%3A", ":").replaceAll("%2F", "/")
					.replaceAll("%5C", "/").replaceAll("%2C", ",")
					.replaceAll("\\+", " ").replaceAll("%22", "")
					.replaceAll("%3F", "?").replaceAll("%3D", "=");
			inputStream = new FileInputStream(fileName);
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"",
					new java.io.File(fileName).getName());
			response.setHeader(headerKey, headerValue);
			response.setContentType("application/octet-stream"); // for all file
																	// type
			ServletOutputStream outputStream = response.getOutputStream();
			FileCopyUtils.copy(inputStream, outputStream);
			outputStream.close();
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return List<SectionsModel> It will return the all the sections with
	 *         parent child relation
	 */
	@RequestMapping("/getAllSections")
	@ResponseBody
	public List<SectionsModel> getAllSections() {
		return summaryReportService.getAllSections();
	}

	/**
	 * This method will return the data for each indicator of a sector according
	 * to district
	 * 
	 * @param facilityTypeId
	 * @param sectionId
	 * @param timePeriodId
	 * @param stateId
	 * @return
	 */
	@RequestMapping("/getSummaryData")
	@ResponseBody
	public Object getSummaryData(
			@RequestParam("facilityTypeId") int facilityTypeId,
			@RequestParam("sectionId") int sectionId,
			@RequestParam("timePeriodId") int timePeriodId,
			@RequestParam("stateId") int stateId) {
		return summaryReportService.getSummaryData(facilityTypeId, sectionId,
				timePeriodId, stateId);

	}

	/**
	 * This method will return the facility submission of the indicator within a
	 * district
	 * 
	 * @param facilityTypeId
	 * @param sectionId
	 * @param timePeriodId
	 * @param districtId
	 * @return
	 */
	@RequestMapping("/getFacilitySummary")
	@ResponseBody
	public Object getFacilitySummary(
			@RequestParam("facilityTypeId") int facilityTypeId,
			@RequestParam("sectionId") int sectionId,
			@RequestParam("timePeriodId") int timePeriodId,
			@RequestParam("districtId") int districtId) {
		return summaryReportService.getfacilityData(facilityTypeId, sectionId,
				timePeriodId, districtId);

	}

}
