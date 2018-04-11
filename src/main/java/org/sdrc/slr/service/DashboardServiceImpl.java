package org.sdrc.slr.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.poi.POIXMLProperties;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sdrc.slr.domain.Area;
import org.sdrc.slr.domain.FacilityType;
import org.sdrc.slr.domain.FormXpathScoreMapping;
import org.sdrc.slr.domain.LastVisitData;
import org.sdrc.slr.domain.RawDataScore;
import org.sdrc.slr.domain.TimePeriod;
import org.sdrc.slr.domain.UserCounter;
import org.sdrc.slr.model.AreaModel;
import org.sdrc.slr.model.FormXpathModel;
import org.sdrc.slr.model.GoogleMapDataModel;
import org.sdrc.slr.model.SectorModel;
import org.sdrc.slr.model.SpiderDataCollection;
import org.sdrc.slr.model.SpiderDataModel;
import org.sdrc.slr.model.TimePeriodModel;
import org.sdrc.slr.repository.AreaDetailsRepository;
import org.sdrc.slr.repository.FacilityScoreRepository;
import org.sdrc.slr.repository.FacilityTypeRepository;
import org.sdrc.slr.repository.FormXpathRepository;
import org.sdrc.slr.repository.LastVisitDataRepository;
import org.sdrc.slr.repository.RawDataScoreRepository;
import org.sdrc.slr.repository.TimePeriodRepository;
import org.sdrc.slr.repository.UserCounterRepository;
import org.sdrc.slr.util.HeaderFooter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 
 * @author Harsh Pratyush
 *
 */
@SuppressWarnings("deprecation")
@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private FacilityTypeRepository facilityTypeRepository;

	@Autowired
	private AreaDetailsRepository areaDetailsRepository;

	@Autowired
	private FacilityScoreRepository facilityScoreRepository;

	@Autowired
	private FormXpathRepository formXpathRepository;

	@Autowired
	private TimePeriodRepository timePeriodRepository;

	@Autowired
	private LastVisitDataRepository lastVisitDataRepository;

	@Autowired
	private ResourceBundleMessageSource messages;

	@Autowired
	private ResourceBundleMessageSource applicationMessageSource;
	
	@Autowired
	private RawDataScoreRepository rawDataScoreRepository;

	@Autowired
	private ServletContext context;
	
	
	@Autowired
	private UserCounterRepository userCounterRepository;

	private static DecimalFormat df = new DecimalFormat(".#");

	/* (non-Javadoc)
	 * @see org.sdrc.slr.service.DashboardService#getAllDistricts(int)
	 * District List of Selected Area;
	 */
	@Override
	public List<AreaModel> getAllDistricts(int stateId) {
		List<Area> areaDetails = areaDetailsRepository
				.findByParentAreaId(stateId);
		Area parentArea = areaDetailsRepository.findByAreaId(stateId);
		List<AreaModel> areaModelList = new ArrayList<AreaModel>();
		AreaModel areaModel = new AreaModel();
		areaModel.setAreaName(parentArea.getAreaName());
		areaModel.setAreaCode(parentArea.getAreaCode());
		areaModel.setParentAreaId(parentArea.getParentAreaId());

		areaModelList.add(areaModel);
		areaModel.setAreaId(0);
		for (Area areaDetail : areaDetails) {
			areaModel = new AreaModel();
			areaModel.setAreaCode(areaDetail.getAreaCode());
			areaModel.setAreaId(areaDetail.getAreaId());
			areaModel
					.setAreaLevelId(areaDetail.getAreaLevel().getAreaLevelId());
			areaModel.setAreaName(areaDetail.getAreaName());
			areaModel.setParentAreaId(areaDetail.getParentAreaId());

			areaModelList.add(areaModel);
		}

		return areaModelList;
	}

	/* (non-Javadoc)
	 * @see org.sdrc.slr.service.DashboardService#getAllSectors()
	 *  List of the Sectors and its respective xPaths
	 */
	@Override
	@Transactional
	public List<SectorModel> getAllSectors() {

		List<FacilityType> facilityTypeList = facilityTypeRepository.findAll();
		List<Object[]> indicatorNames = formXpathRepository
				.findDistinctFormXpathLabel();

		List<SectorModel> sectorModels = new ArrayList<SectorModel>();
		List<FormXpathModel> xPathModels = new ArrayList<FormXpathModel>();

		SectorModel sectorModel = new SectorModel();

		sectorModel.setSectorId(0);
		sectorModel.setSectorName("Facility Type (All)");
		sectorModel.setFormId(1);

		xPathModels = new ArrayList<FormXpathModel>();

		FormXpathModel formXpathModel = new FormXpathModel();
		formXpathModel.setFormXpathId(-1);
		formXpathModel.setLabel("Overall Score");
		formXpathModel.setParentXpath(-1);
		xPathModels.add(formXpathModel);
		formXpathModel.setFormXpathScoreMappingId(-1);

		for (Object[] facilityName : indicatorNames) {
			formXpathModel = new FormXpathModel();
			formXpathModel.setLabel(facilityName[0].toString());
			formXpathModel.setFormXpathScoreMappingId(0);
			formXpathModel.setxPath("");
			formXpathModel.setFormXpathId(0);

			xPathModels.add(formXpathModel);
		}
		sectorModel.setFormXpathModel(xPathModels);
		sectorModels.add(sectorModel);

		for (FacilityType facilityType : facilityTypeList) {
			
			if(facilityType.getFacilityId()!=8)
			{
			sectorModel = new SectorModel();

			sectorModel.setSectorId(facilityType.getFacilityId());
			sectorModel.setSectorName(facilityType.getFacilityName());
			sectorModel.setFormId(facilityType.getForm().getFormId());

			xPathModels = new ArrayList<FormXpathModel>();

			formXpathModel = new FormXpathModel();
			formXpathModel.setFormXpathId(0);
			formXpathModel.setLabel("Overall Score");
			formXpathModel.setParentXpath(-1);
			xPathModels.add(formXpathModel);

			for (FormXpathScoreMapping formXpathScoreMapping : facilityType
					.getFormXpathScoreMappings()) {
				formXpathModel = new FormXpathModel();
				formXpathModel.setLabel(formXpathScoreMapping.getFormXpath()
						.getLabel());
				formXpathModel.setFormXpathScoreMappingId(formXpathScoreMapping
						.getFormXpathScoreId());
				formXpathModel.setxPath(formXpathScoreMapping.getFormXpath()
						.getxPath());
				formXpathModel.setFormXpathId(formXpathScoreMapping
						.getFormXpath().getFormXpathId());

				xPathModels.add(formXpathModel);
			}
			sectorModel.setFormXpathModel(xPathModels);
			sectorModels.add(sectorModel);
			}
		}
		return sectorModels;
	}

	/* (non-Javadoc)
	 * @see org.sdrc.slr.service.DashboardService#getMapData(int, int, int, java.lang.String, int, int)
	 * List<GoogleMapDataModel> which contains the map data of each
	 *         facility according to timeperiod and selected sector or Xforms
	 */
	@Override
	@Transactional
	public List<GoogleMapDataModel> getMapData(int stateId, int districtId,
			int formXpathScoreMappingId, String xpathName, int sectorId,
			int timeperiodId) throws ParseException {
		// All pusphin of selected state

		Date startDate, endDate;
		TimePeriod timePeriod = timePeriodRepository
				.findByTimePeriodId(timeperiodId);
		
		List<String> imagesXpaths=Arrays.asList(messages.getMessage("image.xpath.ids", null,null).split(","));
		List<Integer>imageXpathIds=new ArrayList<Integer>();
		for(String imageXpath:imagesXpaths)
		{
			imageXpathIds.add(Integer.parseInt(imageXpath));
		}
		
		List<Integer> areaIds=lastVisitDataRepository.findAreaIdsForATimePeriod(timePeriod.getStartDate(),timePeriod.getEndDate());
		
		List<RawDataScore> rawDataScores=rawDataScoreRepository.findByRawFormXapthsXPathIdInAndLastVisitDataAreaAreaIdInAndLastVisitDataIsLiveTrueOrderByLastVisitDataAreaAreaIdAscLastVisitDataMarkedAsCompleteDateAscRawFormXapthsXPathIdAsc(imageXpathIds,areaIds);
		
		Map<Integer,List<String>> facilityPhotos=new HashMap<Integer, List<String>>();
		
		for(RawDataScore rawDataScore:rawDataScores)
		{
			if(facilityPhotos.containsKey(rawDataScore.getLastVisitData().getArea().getAreaId()))
			{
				facilityPhotos.get(rawDataScore.getLastVisitData().getArea().getAreaId()).add(rawDataScore.getScore()+","+rawDataScore.getLastVisitData().getInstanceId());
			}
			else
			{
				List<String> tempImage=new ArrayList<String>();
				tempImage.add(rawDataScore.getScore()+","+rawDataScore.getLastVisitData().getInstanceId());
				facilityPhotos.put(rawDataScore.getLastVisitData().getArea().getAreaId(),tempImage);
			}
		}
		
		startDate = timePeriod.getStartDate();
		endDate = timePeriod.getEndDate();

		Map<Integer, FacilityType> faciliTypeMap = new LinkedHashMap<Integer, FacilityType>();
		List<FacilityType> facilityTypeList = facilityTypeRepository.findAll();
		List<LastVisitData> lastVisitDatas = lastVisitDataRepository
				.findAllByIsLiveTrue();
		Map<Integer, LastVisitData> lastVisitDataMap = new HashMap<Integer, LastVisitData>();
		for (LastVisitData lastVisitData : lastVisitDatas) {
			lastVisitDataMap.put(lastVisitData.getLastVisitDataId(),
					lastVisitData);

		}
		for (FacilityType facilityType : facilityTypeList) {
			faciliTypeMap.put(facilityType.getFacilityId(), facilityType);
		}

		List<Object[]> googleMapDatas = new ArrayList<Object[]>();

		List<GoogleMapDataModel> googleMapDataModels = new ArrayList<GoogleMapDataModel>();

		// state data
		if (districtId == 0) {

			// for overAll Score of a Specific facilityType-If user Select a
			// Facility type other than All and select sector of overall
			if (formXpathScoreMappingId == 0 && sectorId != 0) {
				googleMapDatas = facilityScoreRepository
						.getOverAllScoreForSectorOfState(stateId, sectorId,
								startDate, endDate);
			}

			// When user select a specific XformMapping
			else if (formXpathScoreMappingId != 0 && sectorId != 0) {
				googleMapDatas = facilityScoreRepository
						.getScoreOfSelectedIndicatorofState(stateId,
								formXpathScoreMappingId, startDate, endDate);
			}

			// When user select all the facility Type and Specific XformMapping
			else if (formXpathScoreMappingId == 0 && sectorId == 0) {
				googleMapDatas = facilityScoreRepository
						.getScoreForSelectedindicatorOfAllSectorsOfState(
								stateId, xpathName, startDate, endDate);
			}

			// When user select all the facility Type and OverAllScore As Xform
			// Mapping
			else if (formXpathScoreMappingId == -1 && sectorId == 0) {
				googleMapDatas = facilityScoreRepository
						.getOverAllScoreForAllSectorOfState(stateId, startDate,
								endDate);
			}

		}

		// district pushpins only
		else {
			// for overAll Score of a Specific facilityType-If user Select a
			// Facility type other than All and select sector of overall
			if (formXpathScoreMappingId == 0 && sectorId != 0) {
				googleMapDatas = facilityScoreRepository
						.getOverAllScoreForSectorOfDistrict(districtId,
								sectorId, startDate, endDate);
			}
			// When user select a specific XformMapping
			else if (formXpathScoreMappingId != 0 && sectorId != 0) {
				googleMapDatas = facilityScoreRepository
						.getScoreOfSelectedIndicatorofDistrict(districtId,
								formXpathScoreMappingId, startDate, endDate);
			}
			// When user select all the facility Type and Specific XformMapping
			else if (formXpathScoreMappingId == 0 && sectorId == 0) {
				googleMapDatas = facilityScoreRepository
						.getScoreForSelectedindicatorOfAllSectorsOfDistrict(
								districtId, xpathName, startDate, endDate);
			}
			// When user select all the facility Type and Specific XformMapping
			else if (formXpathScoreMappingId == -1 && sectorId == 0) {
				googleMapDatas = facilityScoreRepository
						.getOverAllScoreForAllSectorOfDistrict(districtId,
								startDate, endDate);
			}

		}

		for (Object[] googleData : googleMapDatas) {
			if (googleData[3] != null && googleData[3] instanceof Double) {

				String icon = null;
				GoogleMapDataModel googleMapDataModel = new GoogleMapDataModel();
				googleMapDataModel.setAreaID(googleData[1].toString());
				if (googleData[3] != null
						&& Double.valueOf(googleData[3].toString()) >= 1) {

					googleMapDataModel.setDataValue(googleData[3] != null ? df
							.format(googleData[3]) : "0.0");
				} else {
					if (googleData[3] != null)
						googleMapDataModel.setDataValue(0 + df
								.format(googleData[3]));
				}
				googleMapDataModel.setDateOfVisit(googleData[6].toString());

				if (sectorId == 0) {
					if (googleData[3] != null
							&& googleData[3] instanceof Double)

					{
						if (Double.valueOf(df.format(googleData[3])) < 60) {
							icon = faciliTypeMap.get(googleData[7])
									.getFacilityName().replace(" ", ".")
									+ ".Red";
						} else if (Double.valueOf(df.format(googleData[3])) >= 60
								&& Double.valueOf(df.format(googleData[3])) < 80) {
							icon = faciliTypeMap.get(googleData[7])
									.getFacilityName().replace(" ", ".")
									+ ".Orange";
						}

						else if (Double.valueOf(df.format(googleData[3])) >= 80) {
							icon = faciliTypeMap.get(googleData[7])
									.getFacilityName().replace(" ", ".")
									+ ".Green";
						}

					} else {
						icon = faciliTypeMap.get(googleData[7])
								.getFacilityName().replace(" ", ".")
								+ ".Red";
					}
				} else {
					if (googleData[3] != null
							&& googleData[3] instanceof Double)

					{
						if (Double.valueOf(df.format(googleData[3])) < 60) {
							icon = faciliTypeMap.get(sectorId)
									.getFacilityName().replace(" ", ".")
									+ ".Red";
						} else if (Double.valueOf(df.format(googleData[3])) >= 60
								&& Double.valueOf(df.format(googleData[3])) < 80) {
							icon = faciliTypeMap.get(sectorId)
									.getFacilityName().replace(" ", ".")
									+ ".Orange";
						}

						else if (Double.valueOf(df.format(googleData[3])) >= 80) {
							icon = faciliTypeMap.get(sectorId)
									.getFacilityName().replace(" ", ".")
									+ ".Green";
						}

					} else {
						icon = faciliTypeMap.get(sectorId).getFacilityName()
								.replace(" ", ".")
								+ ".Red";
					}

				}

				googleMapDataModel.setIcon(messages
						.getMessage(icon, null, null));
				googleMapDataModel.setId(Integer.parseInt(googleData[0]
						.toString()));

				googleMapDataModel
						.setLatitude(googleData[4] != null ? googleData[4]
								.toString() : null);
				String images = lastVisitDataMap.get(
						Integer.parseInt(googleData[0].toString()))
						.getImageFileNames()==null?"":lastVisitDataMap.get(
								Integer.parseInt(googleData[0].toString()))
								.getImageFileNames();
				String instance = lastVisitDataMap
						.get(Integer.parseInt(googleData[0].toString()))
						.getInstanceId().replaceAll(":", "");
				if (!images.trim().equalsIgnoreCase("")) {
					List<String> imageFiles = new ArrayList<String>();
					imageFiles = Arrays.asList(images.split(","));
					String imageBase64 = null;
					for (String file : imageFiles) {
						if (imageBase64 == null) {
							imageBase64 = applicationMessageSource.getMessage(
									"lvd.images.path", null, null)
									+ "//"
									+ instance + "//" + file;
						}

						else {
							imageBase64 += ","
									+ applicationMessageSource.getMessage(
											"lvd.images.path", null, null)
									+ "//" + instance + "//" + file;
						}
					}
					googleMapDataModel.setImages(imageBase64);
				} else {
					googleMapDataModel.setImages("");
				}
				
				if(facilityPhotos.containsKey(lastVisitDataMap.get(Integer.parseInt(googleData[0].toString())).getArea().getAreaId()))
				{
					List<String> allImages=facilityPhotos.get(lastVisitDataMap.get(Integer.parseInt(googleData[0].toString())).getArea().getAreaId());
						
					List<String>beforePhotos=new ArrayList<String>();
					for(String photos:allImages.subList(0,4))
					{
						if(photos.split(",")[0].trim().equalsIgnoreCase(""))
						{
							beforePhotos.add(messages.getMessage("image.not.available",null,null));
						}
						else
						{
							beforePhotos.add(applicationMessageSource.getMessage(
									"lvd.images.path", null, null)
									+ "//"
									+ photos.split(",")[1].replaceAll(":", "") + "//" +photos.split(",")[0]);
						}
						
					}
					googleMapDataModel.setBeforeImages(beforePhotos);
					
					if(allImages.size()>4)
					{
						List<String>afterPhotos=new ArrayList<String>();
						for(String photos:allImages.subList(allImages.size()-4,allImages.size()))
						{
							if(photos.split(",")[0].trim().equalsIgnoreCase(""))
							{
								afterPhotos.add(messages.getMessage("image.not.available",null,null));
							}
							else
							{
								afterPhotos.add(applicationMessageSource.getMessage(
										"lvd.images.path", null, null)
										+ "//"
										+ photos.split(",")[1].replaceAll(":", "") + "//" +photos.split(",")[0]);
							}
							
						}
						googleMapDataModel.setAfterImages(afterPhotos);
						/*googleMapDataModel.set*/
					}
					else
					{
						List<String>afterPhotos=new ArrayList<String>();
						afterPhotos.add(messages.getMessage("data.not.available",null,null));
						googleMapDataModel.setAfterImages(afterPhotos);
					
					}
					
					
					
					/*image.not.available*/
				}
				googleMapDataModel
						.setLongitude(googleData[5] != null ? googleData[5]
								.toString() : null);
				googleMapDataModel.setShowWindow(false);
				googleMapDataModel.setTitle(googleData[2].toString());
				googleMapDataModels.add(googleMapDataModel);

			}
		}

		return googleMapDataModels;
	}

	/* (non-Javadoc)
	 * @see org.sdrc.slr.service.DashboardService#getTimePeriod()
	 * get all timeperiods
	 */
	@Override
	public List<TimePeriodModel> getTimePeriod() {
		List<TimePeriodModel> timePeriodModels = new ArrayList<TimePeriodModel>();

		for (TimePeriod timePeriod : timePeriodRepository.findAll()) {

			TimePeriodModel timePeriodModel = new TimePeriodModel();

			timePeriodModel.setStartDate(new String(timePeriod.getStartDate()
					.toString()));
			timePeriodModel.setEndDate(new String(timePeriod.getEndDate()
					.toString()));
			timePeriodModel.setTimeperiod(timePeriod.getTimeperiod());
			timePeriodModel.setPerodicity(timePeriod.getPerodicity());
			timePeriodModel.setTimePeriodId(timePeriod.getTimePeriodId());
			timePeriodModels.add(timePeriodModel);
		}
		// throw new NullPointerException();
		return timePeriodModels;
	}

	/* (non-Javadoc)
	 * @see org.sdrc.slr.service.DashboardService#getSpiderAndLineChartData(int, int, int, int, int, int)
	 * return  Data For the Spider chart and Line Chart for selected parameters
	 */
	@Override
	@Transactional
	public Map<String, SpiderDataCollection> getSpiderAndLineChartData(
			int stateId, int districtId, int facilityTypeId,
			int lastVisitDataId, int timeperiodId1, int timeperiodId2) {

		List<Integer> timePeriodIds = new ArrayList<Integer>();
		SpiderDataCollection spiderDataCollection = new SpiderDataCollection();
		List<Object[]> spiderDatas = null;
		Map<String, SpiderDataCollection> spiderDataMap = new LinkedHashMap<String, SpiderDataCollection>();
		timePeriodIds.add(timeperiodId1);
		if (timeperiodId2 != 0) {
			timePeriodIds.add(timeperiodId2);
		}

		List<TimePeriod> timePeriods = timePeriodRepository.findAll();
		Map<Integer, TimePeriod> timePeriodMap = new HashMap<Integer, TimePeriod>();
		for (TimePeriod timePeriod : timePeriods) {
			timePeriodMap.put(timePeriod.getTimePeriodId(), timePeriod);
		}

		// Component
		// Service Area

		spiderDataMap.put("Component", null);
		spiderDataMap.put("Service Area", null);
		for (String moduleType : spiderDataMap.keySet()) {

			spiderDataCollection = new SpiderDataCollection();
			List<List<SpiderDataModel>> spiderdataModelsList = new ArrayList<List<SpiderDataModel>>();
			for (int timeperiodId : timePeriodIds) {
				List<SpiderDataModel> spiderModels = new ArrayList<SpiderDataModel>();
				if (lastVisitDataId == 0) {
					if (districtId == 0) {
						if (facilityTypeId == 0) {
							spiderDatas = facilityScoreRepository
									.getSpiderDataForStateOfAllIndicator(
											stateId,
											timePeriodMap.get(timeperiodId)
													.getStartDate(),
											timePeriodMap.get(timeperiodId)
													.getEndDate(), moduleType);
						}

						else {
							spiderDatas = facilityScoreRepository
									.getSpiderDataForStateofSelectedFacility(
											stateId, facilityTypeId,
											timePeriodMap.get(timeperiodId)
													.getStartDate(),
											timePeriodMap.get(timeperiodId)
													.getEndDate(), moduleType);
						}

					}

					else {

						if (facilityTypeId == 0) {
							spiderDatas = facilityScoreRepository
									.getSpiderDataForDistrictOfAllIndicator(
											districtId,
											timePeriodMap.get(timeperiodId)
													.getStartDate(),
											timePeriodMap.get(timeperiodId)
													.getEndDate(), moduleType);
						}

						else {
							spiderDatas = facilityScoreRepository
									.getSpiderDataForDistrictForSelectedFacility(
											districtId, facilityTypeId,
											timePeriodMap.get(timeperiodId)
													.getStartDate(),
											timePeriodMap.get(timeperiodId)
													.getEndDate(), moduleType);

						}

					}
				}

				else {
					LastVisitData lastVisitData = lastVisitDataRepository
							.findByLastVisitDataId(lastVisitDataId);

					if (lastVisitData != null) {
						spiderDatas = facilityScoreRepository
								.getSpiderDataForLastVisitData(lastVisitData
										.getArea().getAreaId(), lastVisitData
										.getFacilityScores().get(0)
										.getFormXpathScoreMapping()
										.getFacilityType().getFacilityId(),
										timePeriodMap.get(timeperiodId)
												.getStartDate(),
										timePeriodMap.get(timeperiodId)
												.getEndDate(), moduleType);
					}

				}

				if (spiderDatas != null && spiderDatas.size() > 0)
					for (Object[] spiderData : spiderDatas)

					{
						SpiderDataModel spiderDataModel = new SpiderDataModel();

						spiderDataModel
								.setAxis(spiderData[0].toString().trim());
						spiderDataModel.setTimePeriod(timePeriodMap.get(timeperiodId).getTimeperiod());
						if (spiderData[2] instanceof Double) {
							if (Double.valueOf(spiderData[2].toString()) >= 1) {
								spiderDataModel.setValue(df
										.format(spiderData[2]));
							} else {
								spiderDataModel.setValue(0 + df
										.format(spiderData[2]));
							}
							spiderDataModel.setShortName(spiderData[3]
									.toString().trim());
							spiderModels.add(spiderDataModel);
						}

					}
				if (spiderDatas != null && spiderDatas.size() > 0) {
					if (moduleType.equalsIgnoreCase("Component")) {
						Collections.reverse(spiderModels);
					}
					spiderdataModelsList.add(spiderModels);
				}
			}

			spiderDataCollection.setDataCollection(spiderdataModelsList);
			spiderDataMap.put(moduleType, spiderDataCollection);
		}
		return spiderDataMap;
	}

	/* (non-Javadoc)
	 * @see org.sdrc.slr.service.DashboardService#exportToPdf(java.util.List, int, int, int, int, int, java.lang.String, int, int, boolean)
	 *  Output Path of Generated Pdf File for Dashboard
	 */
	@SuppressWarnings({ "resource", "unused" })
	@Override
	@Transactional
	public String exportToPdf(List<String> svgs, int stateId, int districtId,
			int facilityTypeId, int lastVisitDataId,
			int formXpathScoreMappingId, String xpathName, int timeperiodId1,
			int timeperiodId2, boolean isComponent) throws Exception {

		List<GoogleMapDataModel> googleMapList = getMapData(stateId,
				districtId, formXpathScoreMappingId, xpathName, facilityTypeId,
				timeperiodId1);
		Map<String, SpiderDataCollection> spiderDataMap = getSpiderAndLineChartData(
				stateId, districtId, facilityTypeId, lastVisitDataId,
				timeperiodId1, timeperiodId2);
		new FileOutputStream(new File(context.getRealPath("")
				+ "\\resources\\spider.svg")).write(svgs.get(0).getBytes());
		new FileOutputStream(new File(context.getRealPath("")
				+ "\\resources\\column.svg")).write(svgs.get(1).getBytes());

		List<Area> areaDetails = areaDetailsRepository.findAll();
		Map<Integer, Area> areaMap = new HashMap<Integer, Area>();
		for (Area area : areaDetails) {
			areaMap.put(area.getAreaId(), area);
		}

		List<LastVisitData> lastVisitDatas = lastVisitDataRepository
				.findAllByIsLiveTrue();
		Map<Integer, LastVisitData> lastVisitDatasMap = new HashMap<Integer, LastVisitData>();
		for (LastVisitData lastVisitData : lastVisitDatas) {
			lastVisitDatasMap.put(lastVisitData.getLastVisitDataId(),
					lastVisitData);
		}
		List<TimePeriod> timePeriods = timePeriodRepository.findAll();
		Map<Integer, TimePeriod> timePeriodMap = new HashMap<Integer, TimePeriod>();
		for (TimePeriod timePeriod : timePeriods) {
			timePeriodMap.put(timePeriod.getTimePeriodId(), timePeriod);
		}

		List<FacilityType> facilityTypes = facilityTypeRepository.findAll();
		Map<Integer, FacilityType> facilityTypesMap = new HashMap<Integer, FacilityType>();
		for (FacilityType facilityType : facilityTypes) {
			facilityTypesMap.put(facilityType.getFacilityId(), facilityType);
		}

		String facilityType = null;
		if (facilityTypeId != 0) {
			facilityType = facilityTypesMap.get(facilityTypeId)
					.getFacilityName();
		} else {
			facilityType = "Facility Type(All)";
		}
		String area = null;
		if (districtId == 0) {
			area = areaMap.get(stateId).getAreaName();
		} else {
			area = areaMap.get(districtId).getAreaName();
		}

		String spiderArea = null, spiderfacilityType = null;
		if (lastVisitDataId == 0) {
			spiderArea = area;
			spiderfacilityType = facilityType;
		} else {
			spiderArea = lastVisitDatasMap.get(lastVisitDataId).getArea()
					.getAreaName();
			spiderfacilityType = lastVisitDatasMap.get(lastVisitDataId)
					.getFacilityScores().get(0).getFormXpathScoreMapping()
					.getFacilityType().getFacilityName();
		}

		SpiderDataCollection spiderDataCollection = null;
		if (isComponent) {
			spiderDataCollection = spiderDataMap.get("Component");
		} else {
			spiderDataCollection = spiderDataMap.get("Service Area");
		}

		Font smallBold = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD , BaseColor.WHITE);
		Font dataFont = new Font(Font.FontFamily.HELVETICA, 10);

		Document document = new Document(PageSize.A4.rotate());

		String outputPath = messages.getMessage("outputPathPdf", null, null);
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream(outputPath));

		String domainName = applicationMessageSource.getMessage("domainName",
				null, null);
		// setting Header Footer.PLS Refer to org.sdrc.slr.util.HeaderFooter
		HeaderFooter headerFooter = new HeaderFooter(context, domainName);
		writer.setPageEvent(headerFooter);

		document.open();
		
		BaseColor cellColor = WebColors.getRGBColor("#E8E3E2");
		BaseColor headerCOlor = WebColors.getRGBColor("#333a3b");
		BaseColor siNoColor=WebColors.getRGBColor("#a6bdd9");

		Paragraph dashboardTitle = new Paragraph();
		dashboardTitle.setAlignment(Element.ALIGN_CENTER);
		dashboardTitle.setSpacingAfter(10);
		Chunk dashboardChunk = new Chunk("Score Card");
		dashboardTitle.add(dashboardChunk);

		Paragraph blankSpace = new Paragraph();
		blankSpace.setAlignment(Element.ALIGN_CENTER);
		blankSpace.setSpacingAfter(10);
		Chunk blankSpaceChunk = new Chunk("          ");
		blankSpace.add(blankSpaceChunk);

		Paragraph numberOfFacility = new Paragraph();
		numberOfFacility.setAlignment(Element.ALIGN_CENTER);
		Chunk numberOfFacilityChunk = new Chunk("N = " + googleMapList.size());
		numberOfFacility.add(numberOfFacilityChunk);

		Paragraph spiderDataParagraph = new Paragraph();
		spiderDataParagraph.setAlignment(Element.ALIGN_CENTER);
		spiderDataParagraph.setSpacingAfter(10);
		Chunk spiderChunk = new Chunk("Area / Facility: " + spiderArea
				+ "\t  \t  Facility Level: " + spiderfacilityType);
		spiderDataParagraph.add(spiderChunk);

		byte[] googleMapImageByte = Base64
				.decodeBase64(svgs.get(2).split(",")[1]);
		Image googleMapImage = Image.getInstance(googleMapImageByte);
		int indentationMap = 0;
		float scalerMap = 0;
		
		if(Integer.parseInt(svgs.get(3))>1200)
		{
			scalerMap=((document.getPageSize().getWidth()
					- document.leftMargin() - document.rightMargin() - indentationMap) / googleMapImage
					.getWidth()) * 100;
		}
		
		else if(Integer.parseInt(svgs.get(3))>1000)
		{
			scalerMap=((document.getPageSize().getWidth()
					- document.leftMargin() - document.rightMargin() - indentationMap) / googleMapImage
					.getWidth()) * 85;
		}
		else if(Integer.parseInt(svgs.get(3))>800)
		{
			scalerMap=((document.getPageSize().getWidth()
					- document.leftMargin() - document.rightMargin() - indentationMap) / googleMapImage
					.getWidth()) * 65;
			googleMapImage.setAlignment(Image.ALIGN_CENTER);
		}
		else if(Integer.parseInt(svgs.get(3))>768)
		{
			scalerMap=((document.getPageSize().getWidth()
					- document.leftMargin() - document.rightMargin() - indentationMap) / googleMapImage
					.getWidth()) * 55;
			googleMapImage.setAlignment(Image.ALIGN_CENTER);
		}
		
		else if(Integer.parseInt(svgs.get(3))>500)
		{
			scalerMap=((document.getPageSize().getWidth()
					- document.leftMargin() - document.rightMargin() - indentationMap) / googleMapImage
					.getWidth()) * 45;
			googleMapImage.setAlignment(Image.ALIGN_CENTER);
		}
		else if(Integer.parseInt(svgs.get(3))>300)
		{
			scalerMap=((document.getPageSize().getWidth()
					- document.leftMargin() - document.rightMargin() - indentationMap) / googleMapImage
					.getWidth()) * 35;
			googleMapImage.setAlignment(Image.ALIGN_CENTER);
		}
		else if(Integer.parseInt(svgs.get(3))<300)
		{
			scalerMap=((document.getPageSize().getWidth()
					- document.leftMargin() - document.rightMargin() - indentationMap) / googleMapImage
					.getWidth()) * 30;
			googleMapImage.setAlignment(Image.ALIGN_CENTER);
		}
		googleMapImage.scalePercent(scalerMap);
		/*googleMapImage.setAbsolutePosition(40,5);*/
		
		Paragraph googleMapParagraph = new Paragraph();
		googleMapParagraph.setAlignment(Element.ALIGN_CENTER);
		googleMapParagraph.setSpacingAfter(10);
		Chunk googleMapChunk = new Chunk("Area: " + area
				+ "\t  \t  Facility Level: " + facilityType
				+ "\t  \t  Sector: " + xpathName
				+ "\n \t  \t  \t  TimePeriod: "
				+ timePeriodMap.get(timeperiodId1).getTimeperiod());
		googleMapParagraph.add(googleMapChunk);

		String css = "svg {" + "shape-rendering: geometricPrecision;"
				+ "text-rendering:  geometricPrecision;"
				+ "color-rendering: optimizeQuality;"
				+ "image-rendering: optimizeQuality;" + "}";
		File cssFile = File.createTempFile("batik-default-override-", ".css");
		FileUtils.writeStringToFile(cssFile, css);

		String svg_URI_input = Paths
				.get(new File(context.getRealPath("")
						+ "\\resources\\spider.svg").getPath()).toUri().toURL()
				.toString();
		TranscoderInput input_svg_image = new TranscoderInput(svg_URI_input);
		// Step-2: Define OutputStream to PNG Image and attach to
		// TranscoderOutput
		ByteArrayOutputStream png_ostream = new ByteArrayOutputStream();
		TranscoderOutput output_png_image = new TranscoderOutput(png_ostream);
		// Step-3: Create PNGTranscoder and define hints if required
		PNGTranscoder my_converter = new PNGTranscoder();
		// Step-4: Convert and Write output
		my_converter.transcode(input_svg_image, output_png_image);
		png_ostream.flush();

		Image spiderImage = Image.getInstance(png_ostream.toByteArray());
		int indentation1 = 0;
		float scaler1 = 0;

		if (!isComponent) {
			scaler1 = ((document.getPageSize().getWidth()
					- document.leftMargin() - document.rightMargin() - indentation1) / spiderImage
					.getWidth()) * 50;

			spiderImage.setAbsolutePosition(0, 75);
		} else {

			scaler1 = ((document.getPageSize().getWidth()
					- document.leftMargin() - document.rightMargin() - indentation1) / spiderImage
					.getWidth()) * 50;
			spiderImage.setAbsolutePosition(0, 40);
		}
		spiderImage.scalePercent(scaler1);

		svg_URI_input = Paths
				.get(new File(context.getRealPath("")
						+ "\\resources\\column.svg").getPath()).toUri().toURL()
				.toString();
		input_svg_image = new TranscoderInput(svg_URI_input);
		// Step-2: Define OutputStream to PNG Image and attach to
		// TranscoderOutput
		png_ostream = new ByteArrayOutputStream();
		output_png_image = new TranscoderOutput(png_ostream);
		// Step-3: Create PNGTranscoder and define hints if required
		my_converter = new PNGTranscoder();
		// Step-4: Convert and Write output
		my_converter.transcode(input_svg_image, output_png_image);
		png_ostream.flush();

		Image chartImage = Image.getInstance(png_ostream.toByteArray());
		int indentation2 = 0;
		float scaler2 = 0;

		if (!isComponent) {
			scaler2 = ((document.getPageSize().getWidth()
					- document.rightMargin() - indentation2) / chartImage
					.getWidth()) * 48;
			chartImage.setAbsolutePosition(430, 180);
		} else {
			scaler2 = ((document.getPageSize().getWidth()
					- document.rightMargin() - indentation2) / chartImage
					.getWidth()) * 42;
			chartImage.setAbsolutePosition(430, 190);
		}

		chartImage.scalePercent(scaler2);

		PdfPTable mapDataTable = new PdfPTable(3);

		float[] mapDatacolumnWidths = new float[] { 8f, 30f, 30f };
		mapDataTable.setWidths(mapDatacolumnWidths);

		PdfPCell mapDataCell0 = new PdfPCell(new Paragraph("Sl.No.", smallBold));
		PdfPCell mapDataCell1 = new PdfPCell(new Paragraph("Facility ",
				smallBold));

		PdfPCell mapDataCell3 = new PdfPCell(new Paragraph("Score", smallBold));

		mapDataCell0.setBackgroundColor(siNoColor);
		mapDataCell1.setBackgroundColor(headerCOlor);

		mapDataCell3.setBackgroundColor(headerCOlor);

		mapDataCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		mapDataCell0.setHorizontalAlignment(Element.ALIGN_CENTER);
		mapDataCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		mapDataCell0.setBorderColor(BaseColor.WHITE);
		mapDataCell1.setBorderColor(BaseColor.WHITE);
		mapDataCell3.setBorderColor(BaseColor.WHITE);
		
		mapDataTable.addCell(mapDataCell0);
		mapDataTable.addCell(mapDataCell1);

		mapDataTable.addCell(mapDataCell3);
		mapDataTable.setHeaderRows(1);

		int i = 1;
		for (GoogleMapDataModel mapData : googleMapList) {

			PdfPCell data0 = new PdfPCell(new Paragraph(Integer.toString(i),
					dataFont));
			data0.setHorizontalAlignment(Element.ALIGN_CENTER);
			data0.setBorderColor(BaseColor.WHITE);
			PdfPCell data1 = new PdfPCell(new Paragraph(mapData.getTitle(),
					dataFont));
			data1.setHorizontalAlignment(Element.ALIGN_LEFT);
			data1.setBorderColor(BaseColor.WHITE);

			PdfPCell data3 = new PdfPCell(new Paragraph(mapData.getDataValue(),
					dataFont));
			data3.setHorizontalAlignment(Element.ALIGN_CENTER);
			data3.setBorderColor(BaseColor.WHITE);

			if(i%2==0)
			{
			data0.setBackgroundColor(cellColor);
			data1.setBackgroundColor(cellColor);
			data3.setBackgroundColor(cellColor);
			}
			else
			{
				data0.setBackgroundColor(BaseColor.LIGHT_GRAY);
				data1.setBackgroundColor(BaseColor.LIGHT_GRAY);
				data3.setBackgroundColor(BaseColor.LIGHT_GRAY);
			}

			mapDataTable.addCell(data0);
			mapDataTable.addCell(data1);

			mapDataTable.addCell(data3);

			i++;

		}
		// Spider Data Table

		PdfPTable spiderDataTable = null;
		Image legendImage = null;
		Image spiderlegendImage = null;
		float scalerLegend=0;
		if (spiderDataCollection.getDataCollection().size() > 1) {
			spiderDataTable = new PdfPTable(4);
			legendImage = Image.getInstance(context
					.getRealPath("resources\\images\\legend.png"));
			scaler2 = ((document.getPageSize().getWidth()
					- document.rightMargin() - indentation2) / legendImage
					.getWidth()) * 42;
			legendImage.setAbsolutePosition(620, 100);
			legendImage.scalePercent(65);
		} else {
			spiderDataTable = new PdfPTable(3);
			legendImage = Image.getInstance(context
					.getRealPath("resources\\images\\legend_Average.png"));
			scaler2 = ((document.getPageSize().getWidth()
					- document.rightMargin() - indentation2) / legendImage
					.getWidth()) * 42;
			legendImage.setAbsolutePosition(620, 100);
			legendImage.scalePercent(65);
		}

		if(isComponent)
		{
			spiderlegendImage=Image.getInstance(context
					.getRealPath("resources\\images\\Component_Legend.png"));
			scalerLegend = ((document.getPageSize().getWidth()
					- document.rightMargin() - indentation2) / legendImage
					.getWidth()) * 42;
			spiderlegendImage.setAbsolutePosition(95, 50);
			spiderlegendImage.scalePercent(65);
			
		}
		
		else
		{
			spiderlegendImage=Image.getInstance(context
					.getRealPath("resources\\images\\Service_Area_Legend.png"));
			scalerLegend = ((document.getPageSize().getWidth()
					- document.rightMargin() - indentation2) / legendImage
					.getWidth()) * 42;
			spiderlegendImage.setAbsolutePosition(85, 50);
			spiderlegendImage.scalePercent(65);
			
		}
		// Spider Datas Table
		float[] spiderDatacolumnWidths;
		if (spiderDataCollection.getDataCollection().size() > 1) {
			spiderDatacolumnWidths = new float[] { 8f, 30f, 30f, 30f };

		} else {
			spiderDatacolumnWidths = new float[] { 8f, 30f, 30f };
		}
		spiderDataTable.setWidths(spiderDatacolumnWidths);

		PdfPCell spiderDataCell0 = new PdfPCell(new Paragraph("Sl.No.",
				smallBold));
		PdfPCell spiderDataCell1 = new PdfPCell(new Paragraph("Indicator",
				smallBold));
		PdfPCell spiderDataCell3 = new PdfPCell(new Paragraph(timePeriodMap
				.get(timeperiodId1).getShortName(), smallBold));

		spiderDataCell0.setBackgroundColor(siNoColor);
		spiderDataCell1.setBackgroundColor(headerCOlor);
		spiderDataCell3.setBackgroundColor(headerCOlor);
		spiderDataCell3.setBorderColor(BaseColor.WHITE);
		spiderDataCell0.setBorderColor(BaseColor.WHITE);
		spiderDataCell1.setBorderColor(BaseColor.WHITE);
		
		spiderDataCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		spiderDataCell0.setHorizontalAlignment(Element.ALIGN_CENTER);
		spiderDataCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		spiderDataTable.addCell(spiderDataCell0);
		spiderDataTable.addCell(spiderDataCell1);
		spiderDataTable.addCell(spiderDataCell3);
		if (spiderDataCollection.getDataCollection().size() > 1) {
			PdfPCell spiderDataCell4 = new PdfPCell(new Paragraph(timePeriodMap
					.get(timeperiodId2).getShortName(), smallBold));
			spiderDataCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			spiderDataCell4.setBackgroundColor(headerCOlor);
			spiderDataCell4.setBorderColor(BaseColor.WHITE);
			spiderDataTable.addCell(spiderDataCell4);
		}

		if (spiderDataCollection.getDataCollection().size() > 0
				&& spiderDataCollection.getDataCollection().get(0) != null
				&& !spiderDataCollection.getDataCollection().get(0).isEmpty()) {
			i = 1;
			List<SpiderDataModel> listSpiderDataModel = spiderDataCollection
					.getDataCollection().get(0);

			for (SpiderDataModel spiderDataModel : listSpiderDataModel) {

				PdfPCell data0 = new PdfPCell(new Paragraph(
						Integer.toString(i), dataFont));
				data0.setHorizontalAlignment(Element.ALIGN_CENTER);

				PdfPCell data1 = new PdfPCell(new Paragraph(
						spiderDataModel.getAxis(),
						dataFont));
				data1.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell data3 = new PdfPCell(new Paragraph(
						spiderDataModel.getValue(), dataFont));
				data3.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				if(i%2==0)
				{
				data0.setBackgroundColor(cellColor);
				data0.setBorderColor(BaseColor.WHITE);
				data1.setBorderColor(BaseColor.WHITE);
				data3.setBorderColor(BaseColor.WHITE);
				data1.setBackgroundColor(cellColor);
				data3.setBackgroundColor(cellColor);
				}
				else
				{
					data0.setBackgroundColor(BaseColor.LIGHT_GRAY);
					data0.setBorderColor(BaseColor.WHITE);
					data1.setBorderColor(BaseColor.WHITE);
					data3.setBorderColor(BaseColor.WHITE);
					data1.setBackgroundColor(BaseColor.LIGHT_GRAY);
					data3.setBackgroundColor(BaseColor.LIGHT_GRAY);
					}
				spiderDataTable.addCell(data0);
				spiderDataTable.addCell(data1);
				spiderDataTable.addCell(data3);
				if (spiderDataCollection.getDataCollection().size() > 1) {
					for (SpiderDataModel spiderDataModelTime2 : spiderDataCollection
							.getDataCollection().get(1)) {
						if (spiderDataModelTime2.getAxis().equalsIgnoreCase(
								spiderDataModel.getAxis())) {
							PdfPCell data4 = new PdfPCell(new Paragraph(
									spiderDataModelTime2.getValue(), dataFont));
							data4.setBorderColor(BaseColor.WHITE);
							data4.setHorizontalAlignment(Element.ALIGN_CENTER);
							if(i%2==0)
							{
							data4.setBackgroundColor(cellColor);
							}
							else
							{
								data4.setBackgroundColor(BaseColor.LIGHT_GRAY);
							}
							spiderDataTable.addCell(data4);
						}
					}
				}

				i++;

			}

			document.add(blankSpace);

			document.add(googleMapParagraph);

			document.add(googleMapImage);

			document.newPage();
			document.add(mapDataTable);

			document.newPage();
			document.add(spiderDataParagraph);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(spiderImage);
			document.add(chartImage);
			document.add(legendImage);
			document.add(spiderlegendImage);

			document.newPage();
			document.add(spiderDataTable);

			document.close();
		}

		return outputPath;
	}

	public String createImgFromFile(String path) throws Exception {

		JPEGTranscoder t = new JPEGTranscoder();

		t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(.8));

		TranscoderInput input = new TranscoderInput(path);

		String fileName = ResourceBundle.getBundle("spring/app").getString(
				"output.filepath");
		OutputStream ostream = new FileOutputStream(fileName + "/CHART_"
				+ ".jpg");
		TranscoderOutput output = new TranscoderOutput(ostream);

		t.transcode(input, output);

		ostream.flush();
		ostream.close();

		return fileName + "/CHART_" + ".jpg";
	}

	/* (non-Javadoc)
	 * @see org.sdrc.slr.service.DashboardService#exportToExcel(java.util.List, int, int, int, int, int, int, boolean, int, java.lang.String)
	 * Output Path of Generated EXCEL File for Dashboard
	 */
	@SuppressWarnings({ "resource" })
	@Override
	@Transactional
	public String exportToExcel(List<String> svgs, int stateId, int districtId,
			int facilityTypeId, int lastVisitDataId, int timeperiodId1,
			int timeperiodId2, boolean isComponent,
			int formXpathScoreMappingId, String xpathName) throws Exception {

		Map<String, SpiderDataCollection> spiderDataMap = getSpiderAndLineChartData(
				stateId, districtId, facilityTypeId, lastVisitDataId,
				timeperiodId1, timeperiodId2);

		List<GoogleMapDataModel> mapData = getMapData(stateId, districtId,
				formXpathScoreMappingId, xpathName, facilityTypeId,
				timeperiodId1);
		List<Area> areaDetails = areaDetailsRepository.findAll();
		Map<Integer, Area> areaMap = new HashMap<Integer, Area>();
		for (Area area : areaDetails) {
			areaMap.put(area.getAreaId(), area);
		}

		List<LastVisitData> lastVisitDatas = lastVisitDataRepository
				.findAllByIsLiveTrue();
		Map<Integer, LastVisitData> lastVisitDatasMap = new HashMap<Integer, LastVisitData>();
		for (LastVisitData lastVisitData : lastVisitDatas) {
			lastVisitDatasMap.put(lastVisitData.getLastVisitDataId(),
					lastVisitData);
		}
		List<TimePeriod> timePeriods = timePeriodRepository.findAll();
		Map<Integer, TimePeriod> timePeriodMap = new HashMap<Integer, TimePeriod>();
		for (TimePeriod timePeriod : timePeriods) {
			timePeriodMap.put(timePeriod.getTimePeriodId(), timePeriod);
		}

		List<FacilityType> facilityTypes = facilityTypeRepository.findAll();
		Map<Integer, FacilityType> facilityTypesMap = new HashMap<Integer, FacilityType>();
		for (FacilityType facilityType : facilityTypes) {
			facilityTypesMap.put(facilityType.getFacilityId(), facilityType);
		}

		String facilityType = null;
		if (facilityTypeId != 0) {
			facilityType = facilityTypesMap.get(facilityTypeId)
					.getFacilityName();
		} else {
			facilityType = "Facility Type (All)";
		}
		String area = null;
		if (districtId == 0) {
			area = areaMap.get(stateId).getAreaName();
		} else {
			area = areaMap.get(districtId).getAreaName();
		}

		String spiderArea = null, spiderfacilityType = null;
		if (lastVisitDataId == 0) {
			spiderArea = area;
			spiderfacilityType = facilityType;
		} else {
			spiderArea = lastVisitDatasMap.get(lastVisitDataId).getArea()
					.getAreaName();
			spiderfacilityType = lastVisitDatasMap.get(lastVisitDataId)
					.getFacilityScores().get(0).getFormXpathScoreMapping()
					.getFacilityType().getFacilityName();
		}

		SpiderDataCollection spiderDataCollection = null;
		if (isComponent) {
			spiderDataCollection = spiderDataMap.get("Component");
		} else {
			spiderDataCollection = spiderDataMap.get("Service Area");
		}

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Score Card");
		CreationHelper creationHelper = workbook.getCreationHelper();
		XSSFHyperlink link = (XSSFHyperlink) creationHelper
				.createHyperlink(Hyperlink.LINK_URL);
		link.setAddress(applicationMessageSource.getMessage("domainName", null,
				null));
		link.setTooltip(applicationMessageSource.getMessage("domainName", null,
				null));
		link.setLabel(applicationMessageSource.getMessage("domainName", null,
				null));
		POIXMLProperties xmlProps = workbook.getProperties();
		POIXMLProperties.CoreProperties coreProps = xmlProps
				.getCoreProperties();
		coreProps.setCreator(applicationMessageSource.getMessage("domainName",
				null, null));

		new FileOutputStream(new File(context.getRealPath("")
				+ "\\resources\\spider.svg")).write(svgs.get(0).getBytes());
		new FileOutputStream(new File(context.getRealPath("")
				+ "\\resources\\column.svg")).write(svgs.get(1).getBytes());

		int rowId = 0;
		int colId = 0;
		Row row = sheet.createRow(rowId);
		Cell col = row.createCell(colId);
		XSSFCellStyle headCellStyle = workbook.createCellStyle();

		XSSFFont headFont = workbook.createFont();
		headFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		headFont.setColor(HSSFColor.BLACK.index);
		headFont.setFontHeight(18);
		
		XSSFFont smallFont = workbook.createFont();
		smallFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		smallFont.setColor(HSSFColor.BLACK.index);
		smallFont.setFontHeight(10);

		headCellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		headCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		if(Integer.parseInt(svgs.get(3))>1000)
		{
		headCellStyle.setFont(headFont);
		}
		else
		{
			headCellStyle.setFont(smallFont);
		}
		headCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);

		col.setCellValue("Area / Facility: " + spiderArea
				+ "\t  \t  Facility Level: " + spiderfacilityType);
		col.setHyperlink(link);
		col.setCellStyle(headCellStyle);
		
		sheet.addMergedRegion(new CellRangeAddress(rowId, rowId, colId, 19));
		
		rowId = 30;

		row = sheet.createRow(rowId++);
		colId = 0;
		col = row.createCell(colId++);
		XSSFCellStyle headerCellStyle = workbook.createCellStyle();

		XSSFFont headerFont = workbook.createFont();
		headerFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		headerFont.setColor(HSSFColor.BLACK.index);

		headerCellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		headerCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
		headerCellStyle.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
		headerCellStyle.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
		headerCellStyle.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);

		XSSFCellStyle leftHeaderCellStyle = workbook.createCellStyle();
		leftHeaderCellStyle.setFont(headerFont);
		leftHeaderCellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		leftHeaderCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		leftHeaderCellStyle.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
		leftHeaderCellStyle.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
		leftHeaderCellStyle.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
		leftHeaderCellStyle.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
		leftHeaderCellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);

		col.setCellValue("SI No.");
		headerCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		col.setCellStyle(headerCellStyle);
		sheet.autoSizeColumn(colId);

		col = row.createCell(colId++);
		headerCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		col.setCellValue("Sector");
		col.setCellStyle(headerCellStyle);

		col = row.createCell(colId);
		sheet.autoSizeColumn(colId);

		sheet.autoSizeColumn(colId);
		headerCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		col.setCellValue(timePeriodMap.get(timeperiodId1).getShortName());
		col.setCellStyle(headerCellStyle);
		sheet.autoSizeColumn(colId);

		if (spiderDataCollection.getDataCollection().size() > 1) {
			col = row.createCell(++colId);
			sheet.autoSizeColumn(colId);

			sheet.autoSizeColumn(colId);
			headerCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			col.setCellValue(timePeriodMap.get(timeperiodId2).getShortName());
			col.setCellStyle(headerCellStyle);
			sheet.autoSizeColumn(colId);

		}

		int i = 1;

		for (SpiderDataModel spiderData : spiderDataCollection
				.getDataCollection().get(0)) {
			XSSFFont font = workbook.createFont();
			XSSFCellStyle cellStyle = workbook.createCellStyle();
			font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
			font.setColor(HSSFColor.GREY_80_PERCENT.index);

			cellStyle.setFont(font);
			cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
			cellStyle.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
			cellStyle.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
			cellStyle.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
			cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);

			XSSFCellStyle leftCellStyle = workbook.createCellStyle();
			leftCellStyle.setFont(font);
			leftCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			leftCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			leftCellStyle.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
			leftCellStyle.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
			leftCellStyle.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
			leftCellStyle.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
			leftCellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);

			colId = 0;
			row = sheet.createRow(rowId++);
			col = row.createCell(colId++);
			col.setCellValue(i);
			col.setCellStyle(cellStyle);

			sheet.autoSizeColumn(colId);

			col = row.createCell(colId++);
			col.setCellValue(spiderData.getAxis());
			col.setCellStyle(leftCellStyle);

			col = row.createCell(colId++);
			col.setCellValue(spiderData.getValue());
			col.setCellStyle(cellStyle);

			sheet.autoSizeColumn(colId);

			if (spiderDataCollection.getDataCollection().size() > 1) {
				for (SpiderDataModel spiderDataModelTime2 : spiderDataCollection
						.getDataCollection().get(1)) {
					if (spiderDataModelTime2.getAxis().equalsIgnoreCase(
							spiderData.getAxis())) {

						col = row.createCell(colId);
						col.setCellValue(spiderDataModelTime2.getValue());
						col.setCellStyle(cellStyle);

						sheet.autoSizeColumn(colId);
					}
				}
			}
			i++;
		}
		row = sheet.createRow(1);
		col = row.createCell(0);

		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));
		col = row.createCell(2);

		sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 4));
		FileInputStream fileInputStream;
		String svg_URI_input = Paths
				.get(new File(context.getRealPath("")
						+ "\\resources\\column.svg").getPath()).toUri().toURL()
				.toString();
		String path = createImgFromFile(svg_URI_input);

		fileInputStream = new FileInputStream(path);
		byte[] imageBytes = IOUtils.toByteArray(fileInputStream);
		int pictureureIdx = workbook.addPicture(imageBytes,
				Workbook.PICTURE_TYPE_JPEG);

		CreationHelper helper = workbook.getCreationHelper();
		Drawing drawing = sheet.createDrawingPatriarch();
		ClientAnchor anchor = helper.createClientAnchor();

		anchor.setCol1(0);
		anchor.setRow1(3);
		anchor.setCol2(9);
		anchor.setRow2(8);

		Picture pict = drawing.createPicture(anchor, pictureureIdx);
		pict.resize(0.9);
		svg_URI_input = Paths
				.get(new File(context.getRealPath("")
						+ "\\resources\\spider.svg").getPath()).toUri().toURL()
				.toString();
		path = createImgFromFile(svg_URI_input);

		fileInputStream = new FileInputStream(path);
		imageBytes = IOUtils.toByteArray(fileInputStream);
		pictureureIdx = workbook.addPicture(imageBytes,
				Workbook.PICTURE_TYPE_JPEG);

		helper = workbook.getCreationHelper();
		drawing = sheet.createDrawingPatriarch();
		anchor = helper.createClientAnchor();

		anchor.setCol1(5);
		anchor.setRow1(3);
		anchor.setCol2(19);
		anchor.setRow2(8);
		pict = drawing.createPicture(anchor, pictureureIdx);
		pict.resize(0.7);

		fileInputStream = new FileInputStream(
				context.getRealPath("resources\\images\\legend.png"));
		imageBytes = IOUtils.toByteArray(fileInputStream);

		pictureureIdx = workbook.addPicture(imageBytes,
				Workbook.PICTURE_TYPE_JPEG);
		helper = workbook.getCreationHelper();
		drawing = sheet.createDrawingPatriarch();
		anchor = helper.createClientAnchor();

		anchor.setCol1(1);
		anchor.setRow1(21);
		anchor.setCol2(1);
		anchor.setRow2(26);
		/* anchor.setDx2(1023); */
		pict = drawing.createPicture(anchor, pictureureIdx);
		pict.resize(0.8);
		rowId = 1 + anchor.getRow2();
		
		if(isComponent)
		{
		fileInputStream = new FileInputStream(
				context.getRealPath("resources\\images\\Component_Legend.png"));
		}
		else
		{
			fileInputStream = new FileInputStream(
					context.getRealPath("resources\\images\\Service_Area_Legend.png"));
			
		}
		imageBytes = IOUtils.toByteArray(fileInputStream);

		pictureureIdx = workbook.addPicture(imageBytes,
				Workbook.PICTURE_TYPE_JPEG);
		helper = workbook.getCreationHelper();
		drawing = sheet.createDrawingPatriarch();
		anchor = helper.createClientAnchor();

		anchor.setCol1(0);
		anchor.setRow1(26);
		anchor.setCol2(4);
		anchor.setRow2(28);
		/* anchor.setDx2(1023); */
		pict = drawing.createPicture(anchor, pictureureIdx);
		pict.resize(0.8);
		rowId = 1 + anchor.getRow2();

		XSSFSheet mapDataSheet = workbook.createSheet("Google Map Data");
		rowId = 0;
		colId = 0;
		row = mapDataSheet.createRow(rowId);
		col = row.createCell(0);
		headFont = workbook.createFont();
		headFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		headFont.setColor(HSSFColor.BLACK.index);
		headFont.setFontHeight(18);
		
		smallFont = workbook.createFont();
		smallFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		smallFont.setColor(HSSFColor.BLACK.index);
		smallFont.setFontHeight(8);

		headCellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		headCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		if(Integer.parseInt(svgs.get(3))>1000)
		{
		headCellStyle.setFont(headFont);
		}
		
		else
		{
			headCellStyle.setFont(smallFont);
		}
		headCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);

		col.setCellValue("Area : " + area + "\t  \t  Facility Level: "
				+ facilityType + "\t \t Indicator Name: " + xpathName);
		col.setHyperlink(link);
		col.setCellStyle(headCellStyle);
		mapDataSheet.addMergedRegion(new CellRangeAddress(rowId, rowId, colId,
				20));
		rowId = rowId + 2;

		row = mapDataSheet.createRow(rowId++);
		colId = 0;
		col = row.createCell(colId++);

		col.setCellValue("SI No.");
		headerCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		col.setCellStyle(headerCellStyle);
		mapDataSheet.autoSizeColumn(colId);

		col = row.createCell(colId++);
		headerCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		col.setCellValue("Facility Name");
		col.setCellStyle(headerCellStyle);

		col = row.createCell(colId);
		mapDataSheet.autoSizeColumn(colId);

		mapDataSheet.autoSizeColumn(colId);
		headerCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		col.setCellValue("Data Value");
		col.setCellStyle(headerCellStyle);
		mapDataSheet.autoSizeColumn(colId);
		i = 1;
		for (GoogleMapDataModel dataModel : mapData) {

			XSSFFont font = workbook.createFont();
			XSSFCellStyle cellStyle = workbook.createCellStyle();
			font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
			font.setColor(HSSFColor.GREY_80_PERCENT.index);

			cellStyle.setFont(font);
			cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
			cellStyle.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
			cellStyle.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
			cellStyle.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
			cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);

			XSSFCellStyle leftCellStyle = workbook.createCellStyle();
			leftCellStyle.setFont(font);
			leftCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			leftCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			leftCellStyle.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
			leftCellStyle.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
			leftCellStyle.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
			leftCellStyle.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
			leftCellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);

			colId = 0;
			row = mapDataSheet.createRow(rowId++);
			col = row.createCell(colId++);
			col.setCellValue(i);
			col.setCellStyle(cellStyle);

			mapDataSheet.autoSizeColumn(colId);

			col = row.createCell(colId++);
			col.setCellValue(dataModel.getTitle());
			col.setCellStyle(leftCellStyle);

			col = row.createCell(colId++);
			col.setCellValue(dataModel.getDataValue());
			col.setCellStyle(cellStyle);

			mapDataSheet.autoSizeColumn(colId);

			i++;

		}

		String outputPath = messages.getMessage("outputPathExcel", null, null);
		FileOutputStream fileOut = null;
		fileOut = new FileOutputStream(outputPath);
		workbook.write(fileOut);
		fileOut.close();
		// End of image
		
		return outputPath;

	}

	/* (non-Javadoc)
	 * @see org.sdrc.slr.service.DashboardService#getVisitorCount(boolean)
	 * This method counts the visitors
	 */
	@Override
	public synchronized long  getVisitorCount(boolean flag) {

		List<UserCounter> userCounters=userCounterRepository.findAll();
		
		if(userCounters.isEmpty())
		{
			UserCounter userCounter=new UserCounter();
			userCounter.setVisitorCount(1);
			userCounter.setUpdatedDate(new Timestamp(new java.util.Date().getTime()));
			UserCounter newCounter=userCounterRepository.save(userCounter);
			return newCounter.getVisitorCount();
		}
		
		else
		{
			UserCounter userCounter=userCounters.get(0);
			if(flag)
			{
			userCounter.setVisitorCount(userCounter.getVisitorCount()+1);
			userCounter.setUpdatedDate(new Timestamp(new java.util.Date().getTime()));
			UserCounter newCounter=userCounterRepository.save(userCounter);
			return newCounter.getVisitorCount();
			}
			return userCounter.getVisitorCount();
			}
	}


}
