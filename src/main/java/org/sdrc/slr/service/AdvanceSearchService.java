package org.sdrc.slr.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Harsh Pratyush
 *
 */
public interface AdvanceSearchService {

	/**
	 * It will return the value of each each facility within a sector with its
	 * facility type and district on the basis of selected pattern value i.e.
	 * for a given score and selected pattern it will return List of Facilities
	 * 
	 * @param facilityTpeId
	 * @param formXpathScoreMappingId
	 * @param patternValue
	 * @param score
	 * @param formXpathLabel
	 * @param timePeriodId
	 * @param districtId
	 * @return
	 */
	public List<Map<String,String>> getAdvanceSearchData(int stateId,int facilityTpeId,
			int formXpathScoreMappingId, int patternValue, String score,
			String formXpathLabel, int districtId, int timePeriodId);

}
