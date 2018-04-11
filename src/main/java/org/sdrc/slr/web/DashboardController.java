/**
 * 
 */
package org.sdrc.slr.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sdrc.slr.model.AreaModel;
import org.sdrc.slr.model.GoogleMapDataModel;
import org.sdrc.slr.model.SectorModel;
import org.sdrc.slr.model.SpiderDataCollection;
import org.sdrc.slr.model.TimePeriodModel;
import org.sdrc.slr.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Harsh Pratyush
 *this controller belongs to dashboard operations
 */

@Controller
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;

	/**
	 * 
	 * @param stateId
	 *            -Id of selected state
	 * @return List<AreaModel> The list of districts of selected state
	 */
	@RequestMapping("/getDistricts")
	@ResponseBody
	public List<AreaModel> getDistricts(@RequestParam("stateId") int stateId) {
		return dashboardService.getAllDistricts(stateId);
	}

	/**
	 * 
	 * @return List<SectorModel> It will give all the Facility Level and its
	 *         corresponding sector
	 */
	@RequestMapping("/getSectors")
	@ResponseBody
	public List<SectorModel> getSectors() {
		return dashboardService.getAllSectors();
	}

	/**
	 * 
	 * @param stateId
	 * @param districtId
	 * @param formXpathScoreMappingId
	 * @param xpathName
	 * @param sectorId
	 * @param timePeriodId
	 * @return It will give the List<GoogleMapDataModel> of pushpin of each
	 *         facility and its image path in resources folder
	 * @throws ParseException
	 */
	@RequestMapping("/getGoogleMapData")
	@ResponseBody
	public List<GoogleMapDataModel> getGoogleMapData(
			@RequestParam("stateId") int stateId,
			@RequestParam("districtId") int districtId,
			@RequestParam("formXpathScoreMappingId") int formXpathScoreMappingId,
			@RequestParam("xpathName") String xpathName,
			@RequestParam("sectorId") int sectorId,
			@RequestParam("timePerioId") int timePeriodId)
			throws ParseException {

		return dashboardService.getMapData(stateId, districtId,
				formXpathScoreMappingId, xpathName, sectorId, timePeriodId);
	}

	/**
	 * 
	 * @return List<TimePeriodModel> Available TimePeriod in Database
	 */
	@RequestMapping("/getAllTimePeriods")
	@ResponseBody
	public List<TimePeriodModel> getAllTimePeriods() {
		return dashboardService.getTimePeriod();
	}

	/**
	 * 
	 * @param stateId
	 * @param districtId
	 * @param facilityTypeId
	 * @param lastVisitDataId
	 * @param timeperiodId1
	 * @param timeperiodId2
	 * @return Map<String, SpiderDataCollection> It will give the data of each
	 *         sector for Spider and line chart
	 */
	@RequestMapping("/getSpiderData")
	@ResponseBody
	public Map<String, SpiderDataCollection> getSpiderData(
			@RequestParam(value = "stateId", required = false) int stateId,
			@RequestParam(value = "districtId", required = false) int districtId,
			@RequestParam(value = "facilityTypeId", required = false) int facilityTypeId,
			@RequestParam(value = "lastVisitDataId", required = false) int lastVisitDataId,
			@RequestParam(value = "timeperiodId1", required = false) int timeperiodId1,
			@RequestParam(value = "timeperiodId2", required = false) int timeperiodId2) {
		return dashboardService.getSpiderAndLineChartData(stateId, districtId,
				facilityTypeId, lastVisitDataId, timeperiodId1, timeperiodId2);
	}

	/**
	 * This method will go to service for the generation of pdf of dashboard
	 * 
	 * @param svgs
	 * @param stateId
	 * @param districtId
	 * @param facilityTypeId
	 * @param lastVisitDataId
	 * @param formXpathScoreMappingId
	 * @param xpathName
	 * @param timeperiodId1
	 * @param timeperiodId2
	 * @param isComponent
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportToPdf")
	@ResponseBody
	public String exportPdf(
			@RequestBody List<String> svgs,
			@RequestParam(value = "stateId", required = false) int stateId,
			@RequestParam(value = "districtId", required = false) int districtId,
			@RequestParam(value = "facilityTypeId", required = false) int facilityTypeId,
			@RequestParam(value = "lastVisitDataId", required = false) int lastVisitDataId,
			@RequestParam(value = "formXpathScoreMappingId", required = false) int formXpathScoreMappingId,
			@RequestParam(value = "xpathName", required = false) String xpathName,
			@RequestParam(value = "timeperiodId1", required = false) int timeperiodId1,
			@RequestParam(value = "timeperiodId2", required = false) int timeperiodId2,
			@RequestParam(value = "isComponent", required = false) boolean isComponent)
			throws Exception {
		return dashboardService.exportToPdf(svgs, stateId, districtId,
				facilityTypeId, lastVisitDataId, formXpathScoreMappingId,
				xpathName, timeperiodId1, timeperiodId2, isComponent);
	}

	/**
	 * This method will go to service for the generation of excel of dashboard
	 * 
	 * @param svgs
	 * @param stateId
	 * @param districtId
	 * @param facilityTypeId
	 * @param lastVisitDataId
	 * @param timeperiodId1
	 * @param timeperiodId2
	 * @param isComponent
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportToExcel")
	@ResponseBody
	public String exportExcel(
			@RequestBody List<String> svgs,
			@RequestParam(value = "stateId", required = false) int stateId,
			@RequestParam(value = "districtId", required = false) int districtId,
			@RequestParam(value = "facilityTypeId", required = false) int facilityTypeId,
			@RequestParam(value = "lastVisitDataId", required = false) int lastVisitDataId,
			@RequestParam(value = "formXpathScoreMappingId", required = false) int formXpathScoreMappingId,
			@RequestParam(value = "xpathName", required = false) String xpathName,
			@RequestParam(value = "timeperiodId1", required = false) int timeperiodId1,
			@RequestParam(value = "timeperiodId2", required = false) int timeperiodId2,
			@RequestParam(value = "isComponent", required = false) boolean isComponent)
			throws Exception {
		try {
			return dashboardService.exportToExcel(svgs, stateId, districtId,
					facilityTypeId, lastVisitDataId, timeperiodId1,
					timeperiodId2, isComponent ,  formXpathScoreMappingId,
					xpathName);
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}

	/**
	 * This method will be used to download the file
	 * 
	 * @param name
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
	public void downLoad(@RequestParam("fileName") String name,
			HttpServletResponse response) throws IOException {
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			new File(fileName).delete();
		}
	}

	/**
	 * This method will update the visitor count
	 */
	
	@RequestMapping("/getCounterJson")
	@ResponseBody
	public long getCounterJson(HttpServletResponse response,HttpServletRequest request)
	{
		String uri=request.getHeader("referer");
		boolean flag=false;
		if(uri.endsWith("/home")||uri.endsWith("/"))
			flag=true;
		return dashboardService.getVisitorCount(flag);
	}
}
