package org.sdrc.slr.web;

import java.util.List;
import java.util.Map;

import org.sdrc.slr.service.AdvanceSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author Harsh Pratyush
 * this controller belongs to advance search jsp
 *
 */

@Controller
public class AdvanceSearchController {

	@Autowired
	AdvanceSearchService advanceSearchService;

	/**
	 * @param stateId
	 * @param facilityTpeId
	 * @param formXpathScoreMappingId
	 * @param patternValue
	 * @param score
	 * @param formXpathLabel
	 * @return It will return the value of each each facility within a sector
	 *         with its facility type and district on the basis of selected
	 *         pattern value i.e. for a given score and selected pattern it will
	 *         return List of Facilities
	 */
	@RequestMapping("/getAdvanceSearchData")
	@ResponseBody
	public List<Map<String,String>> getAdvanceSearchData(
			@RequestParam("stateId") int stateId,
			@RequestParam("facilityTpeId") int facilityTpeId,
			@RequestParam("formXpathScoreMappingId") int formXpathScoreMappingId,
			@RequestParam("patternValue") int patternValue,
			@RequestParam("score") String score,
			@RequestParam("formXpathLabel") String formXpathLabel,
			@RequestParam("districtId")int districtId,
			@RequestParam("timePerioId") int timePeriodId) {
		return advanceSearchService.getAdvanceSearchData(stateId,
				facilityTpeId, formXpathScoreMappingId, patternValue, score,
				formXpathLabel,districtId,timePeriodId);
	}

}
