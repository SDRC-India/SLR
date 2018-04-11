/**
 * 
 */
package org.sdrc.slr.service;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kxml2.io.KXmlSerializer;
import org.opendatakit.briefcase.model.DocumentDescription;
import org.opendatakit.briefcase.model.ServerConnectionInfo;
import org.opendatakit.briefcase.model.TerminationFuture;
import org.opendatakit.briefcase.util.AggregateUtils;
import org.opendatakit.briefcase.util.WebUtils;
import org.sdrc.slr.domain.Area;
import org.sdrc.slr.domain.AreaLevel;
import org.sdrc.slr.domain.ChoicesDetails;
import org.sdrc.slr.domain.FacilityScore;
import org.sdrc.slr.domain.FacilityType;
import org.sdrc.slr.domain.FormXpathScoreMapping;
import org.sdrc.slr.domain.LastVisitData;
import org.sdrc.slr.domain.RawDataScore;
import org.sdrc.slr.domain.RawFormXapths;
import org.sdrc.slr.domain.TimePeriod;
import org.sdrc.slr.domain.XForm;
import org.sdrc.slr.repository.AreaDetailsRepository;
import org.sdrc.slr.repository.ChoiceDetailsRepository;
import org.sdrc.slr.repository.FacilityScoreRepository;
import org.sdrc.slr.repository.FacilityTypeRepository;
import org.sdrc.slr.repository.LastVisitDataRepository;
import org.sdrc.slr.repository.RawDataScoreRepository;
import org.sdrc.slr.repository.RawFormXapthsRepository;
import org.sdrc.slr.repository.TimePeriodRepository;
import org.sdrc.slr.repository.XFormReposisotry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlSerializer;

/**
 * @author Harsh Pratyush(harsh@sdrc.co.in)
 *
 */
@Service
public class MasterRawDataServiceImpl implements MasterRawDataService {

	@Autowired
	private XFormReposisotry xFormRepository;

	@Autowired
	private RawFormXapthsRepository rawFormXapthsRepository;

	@Autowired
	private AreaDetailsRepository areaDetailsRepository;

	@Autowired
	private FacilityTypeRepository facilityTypeRepository;

	@Autowired
	private LastVisitDataRepository lastVisitDataRepository;

	@Autowired
	private RawDataScoreRepository rawDataScoreRepository;

	@Autowired
	private ChoiceDetailsRepository choiceDetailsRepository;

	@Autowired
	private ResourceBundleMessageSource applicationMessageSource;

	@Autowired
	private FacilityScoreRepository facilityScoreRepository;

	@Autowired
	private TimePeriodRepository timePeriodRepository;

	@Autowired
	private ServletContext context;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("yyyy-MM-dd HH:mm:ss.S");
	SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sdrc.slr.service.MasterRawDataService#generateXpath()
	 * This method will be used to read the xfrom excel and persist the
	 * corresponding xpath in Database
	 */
	@Transactional
	@Override
	public boolean generateXpath() throws Exception {

		List<XForm> xforms = xFormRepository.findByIsLiveTrue();

		XForm xform = xforms.get(0);

		String baseUrl = xform.getOdkServerURL().concat("view/submissionList");
		String serverURL = xform.getOdkServerURL();
		String userName = xform.getUsername();
		String password = xform.getPassword();
		String submission_xml_url = xform.getOdkServerURL().concat(
				"view/downloadSubmission");
		String base_xml_download_url = xform.getOdkServerURL().concat(
				"formXml?formId=");
		// String xFormId = xform.getxFormId();
		// String rootElement = xform.getxFormId();

		StringWriter id_list = new StringWriter();
		AggregateUtils.DocumentFetchResult result = null;
		XmlSerializer serializer = new KXmlSerializer();

		String formRooTitle = "";

		StringWriter base_xlsForm = getXML(xform.getxFormId(), serverURL,
				userName, password, base_xml_download_url);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document core_xml_doc = dBuilder.parse(new InputSource(
				new ByteArrayInputStream(base_xlsForm.toString().getBytes(
						"utf-8"))));
		if (core_xml_doc != null) {
			core_xml_doc.getDocumentElement().normalize();
			Element eElement = (Element) core_xml_doc.getElementsByTagName(
					"group").item(0);
			formRooTitle = eElement.getAttribute("ref").split("/")[1];
		}

		Map<String, String> params = new HashMap<String, String>();
		params.put("formId", xform.getxFormId());
		params.put("cursor", "");
		params.put("numEntries", "");
		String fullUrl = WebUtils.createLinkWithProperties(baseUrl, params);

		ServerConnectionInfo serverInfo = new ServerConnectionInfo(serverURL,
				userName, password.toCharArray());
		DocumentDescription submissionDescription = new DocumentDescription(
				"Fetch of manifest failed. Detailed reason: ",
				"Fetch of manifest failed ", "form manifest",
				new TerminationFuture());
		result = AggregateUtils.getXmlDocument(fullUrl, serverInfo, false,
				submissionDescription, null);
		serializer.setOutput(id_list);
		result.doc.write(serializer);

		Document doc_id_list = dBuilder
				.parse(new InputSource(new ByteArrayInputStream(id_list
						.toString().getBytes("utf-8"))));
		String inputFilePath = "D://Standardization_of_Birthing_Unit_R2.xlsx";
		FileInputStream fileInputStream = new FileInputStream(inputFilePath);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);

		XSSFSheet sheet = xssfWorkbook.getSheet("Phase-1 data");

		StringBuilder queryString = new StringBuilder("/submission/data/");
		queryString.append(xform.getxFormId());
		String splittingStr = "";

		if (doc_id_list != null) {
			doc_id_list.getDocumentElement().normalize();

			NodeList nodeIdList = doc_id_list.getElementsByTagName("id");
			// LocalDateTime currentDate = LocalDateTime.now();
			// LocalDateTime dbMarkedAsCompleteDateTime = null;
			// String dbMarkedAsCompleteDate = null;
			for (int node_no = 0; node_no < 1; node_no++) {

				String instance_id = nodeIdList.item(node_no).getFirstChild()
						.getNodeValue();

				String link_formID = generateFormID(xform.getxFormId(),
						formRooTitle, instance_id);
				Map<String, String> submiteParams = new HashMap<String, String>();
				submiteParams.put("formId", link_formID);
				String full_url = WebUtils.createLinkWithProperties(
						submission_xml_url, submiteParams);

				serializer = new KXmlSerializer();
				StringWriter data_writer = new StringWriter();
				result = AggregateUtils.getXmlDocument(full_url, serverInfo,
						false, submissionDescription, null);
				serializer.setOutput(data_writer);
				result.doc.write(serializer);

				Document submission_doc = dBuilder.parse(new InputSource(
						new ByteArrayInputStream(data_writer.toString()
								.getBytes("utf-8"))));

				submission_doc.getDocumentElement().normalize();

				XSSFRow headRrow = sheet.getRow(0);
				XSSFCell headerValueCell = headRrow.createCell(node_no + 3);
				headerValueCell.setCellValue("Response_" + (node_no + 1));

				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					RawFormXapths rawFormXapths = new RawFormXapths();

					XSSFRow row = sheet.getRow(i);
					if (null != row) {

						XSSFCell typeCell = row.getCell(0);
						XSSFCell nameCell = row.getCell(1);
						if (null != typeCell
								&& !typeCell.getStringCellValue().isEmpty()) {

							if (typeCell.getStringCellValue().equalsIgnoreCase(
									"begin group")) {
								rawFormXapths.setType(typeCell
										.getStringCellValue());
								queryString = queryString.append("/"
										+ nameCell.getStringCellValue());
							} else if (typeCell.getStringCellValue()
									.equalsIgnoreCase("end group")) {
								rawFormXapths.setType(typeCell
										.getStringCellValue());
								splittingStr = queryString.toString()
										.split("/")[queryString.toString()
										.split("/").length - 1];
								queryString = new StringBuilder(
										queryString.substring(
												0,
												queryString.lastIndexOf("/"
														+ splittingStr)));
							}

							if (!(typeCell.getStringCellValue()
									.equalsIgnoreCase("begin group") || typeCell
									.getStringCellValue().equalsIgnoreCase(
											"end group"))) {

								queryString = queryString.append("/"
										+ nameCell.getStringCellValue());

								rawFormXapths.setType(typeCell
										.getStringCellValue());

								rawFormXapths.setForm(xform);
								rawFormXapths.setXpath(queryString.toString());
								// formXpathScoreMapping.setLive(true);
								if (row.getCell(2) != null) {
									rawFormXapths.setLabel(row.getCell(2)
											.toString());
								}
								// row.getCell(2).setCellValue(rawFormXapths.getXpath());
								rawFormXapthsRepository.save(rawFormXapths);

								splittingStr = queryString.toString()
										.split("/")[queryString.toString()
										.split("/").length - 1];
								queryString = new StringBuilder(
										queryString.substring(
												0,
												queryString.lastIndexOf("/"
														+ splittingStr)));
							}

						}
					}

				}

			}
		}
		XSSFSheet choicesSheet = xssfWorkbook.getSheet("choices");

		/*
		 * Map<String, String> nameLabelMap = new HashMap<String, String>();
		 * Map<String, Map<String, String>> listNameLabelMap = new
		 * HashMap<String, Map<String, String>>();
		 */

		for (int i = 1; i <= choicesSheet.getLastRowNum(); i++) {
			XSSFRow row = choicesSheet.getRow(i);

			if (null != row) {
				XSSFCell listNameCell = row.getCell(0);
				XSSFCell nameCell = row.getCell(1);
				XSSFCell labelCell = row.getCell(2);

				if (null != listNameCell && null != labelCell
						&& null != nameCell) {

					ChoicesDetails choicesDetails = new ChoicesDetails();
					String nameVal = nameCell.getCellType() == Cell.CELL_TYPE_STRING ? nameCell
							.getStringCellValue() : Integer
							.toString(((Double) nameCell.getNumericCellValue())
									.intValue());

					String labelVal = labelCell.getCellType() == Cell.CELL_TYPE_STRING ? labelCell
							.getStringCellValue()
							: Integer.toString(((Double) labelCell
									.getNumericCellValue()).intValue());

					choicesDetails.setChoicName(listNameCell
							.getStringCellValue());
					choicesDetails.setLabel(nameVal);
					choicesDetails.setChoiceValue(labelVal);
					choiceDetailsRepository.save(choicesDetails);

				}

			}

		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sdrc.slr.service.MasterRawDataService#persistRawData()
	 * it will perisit raw data for each xpath for each submission
	 */
	@Override
	@Transactional
	public boolean persistRawData() throws Exception {

		List<Area> areaDetails = areaDetailsRepository.findAll();

		List<RawFormXapths> rawFormXapths = rawFormXapthsRepository.findAll();

		Map<String, Area> areaMap = new HashMap<String, Area>();

		for (Area area : areaDetails) {
			areaMap.put(area.getAreaCode(), area);
		}

		List<FacilityType> facilityTypes = facilityTypeRepository.findAll();

		Map<Integer, FacilityType> facilityTypeMap = new HashMap<Integer, FacilityType>();

		for (FacilityType facilityType : facilityTypes) {
			facilityTypeMap.put(facilityType.getFacilityId(), facilityType);
		}

		for (Area area : areaDetails) {
			if (area.getAreaLevel().getAreaLevelId() == 4) {
				areaMap.put(area.getParentAreaId() + "_" + area.getAreaName(),
						area);
			}
		}

		List<LastVisitData> lastVisitDatas = lastVisitDataRepository.findAll();
		Map<String, LastVisitData> lastVisitDataMap = new HashMap<String, LastVisitData>();
		for (LastVisitData lastVisitData : lastVisitDatas) {
			lastVisitDataMap.put(lastVisitData.getInstanceId(), lastVisitData);

		}
		XForm xform = facilityTypes.get(0).getForm();

		String baseUrl = xform.getOdkServerURL().concat("view/submissionList");
		String serverURL = xform.getOdkServerURL();
		String userName = xform.getUsername();
		String password = xform.getPassword();
		String submission_xml_url = xform.getOdkServerURL().concat(
				"view/downloadSubmission");
		String base_xml_download_url = xform.getOdkServerURL().concat(
				"formXml?formId=");
		// String xFormId = xform.getxFormId();
		String rootElement = xform.getxFormId();

		StringWriter id_list = new StringWriter();
		AggregateUtils.DocumentFetchResult result = null;
		XmlSerializer serializer = new KXmlSerializer();

		String formRooTitle = "";

		StringWriter base_xlsForm = getXML(xform.getxFormId(), serverURL,
				userName, password, base_xml_download_url);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document core_xml_doc = dBuilder.parse(new InputSource(
				new ByteArrayInputStream(base_xlsForm.toString().getBytes(
						"utf-8"))));
		if (core_xml_doc != null) {
			core_xml_doc.getDocumentElement().normalize();
			Element eElement = (Element) core_xml_doc.getElementsByTagName(
					"group").item(0);
			formRooTitle = eElement.getAttribute("ref").split("/")[1];
		}

		Map<String, String> params = new HashMap<String, String>();
		params.put("formId", xform.getxFormId());
		params.put("cursor", "");
		params.put("numEntries", "");
		String fullUrl = WebUtils.createLinkWithProperties(baseUrl, params);

		ServerConnectionInfo serverInfo = new ServerConnectionInfo(serverURL,
				userName, password.toCharArray());
		DocumentDescription submissionDescription = new DocumentDescription(
				"Fetch of manifest failed. Detailed reason: ",
				"Fetch of manifest failed ", "form manifest",
				new TerminationFuture());
		result = AggregateUtils.getXmlDocument(fullUrl, serverInfo, false,
				submissionDescription, null);
		serializer.setOutput(id_list);
		result.doc.write(serializer);

		Document doc_id_list = dBuilder
				.parse(new InputSource(new ByteArrayInputStream(id_list
						.toString().getBytes("utf-8"))));

		if (doc_id_list != null) {
			doc_id_list.getDocumentElement().normalize();

			NodeList nodeIdList = doc_id_list.getElementsByTagName("id");
			// LocalDateTime currentDate = LocalDateTime.now();
			// LocalDateTime dbMarkedAsCompleteDateTime = null;
			// String dbMarkedAsCompleteDate = null;
			for (int node_no = 0; node_no < nodeIdList.getLength(); node_no++) {
				String instance_id = nodeIdList.item(node_no).getFirstChild()
						.getNodeValue();
				if (!lastVisitDataMap.containsKey(instance_id)
						|| lastVisitDataMap.get(instance_id).getRawDataScore() == null
						|| lastVisitDataMap.get(instance_id).getRawDataScore()
								.size() < 1
						|| lastVisitDataMap.get(instance_id).getRawDataScore()
								.isEmpty()) {
					String link_formID = generateFormID(xform.getxFormId(),
							formRooTitle, instance_id);
					Map<String, String> submiteParams = new HashMap<String, String>();
					submiteParams.put("formId", link_formID);
					String full_url = WebUtils.createLinkWithProperties(
							submission_xml_url, submiteParams);

					serializer = new KXmlSerializer();
					StringWriter data_writer = new StringWriter();
					result = AggregateUtils.getXmlDocument(full_url,
							serverInfo, false, submissionDescription, null);
					serializer.setOutput(data_writer);
					result.doc.write(serializer);

					Document submission_doc = dBuilder.parse(new InputSource(
							new ByteArrayInputStream(data_writer.toString()
									.getBytes("utf-8"))));
					XPath xPath = XPathFactory.newInstance().newXPath();
					submission_doc.getDocumentElement().normalize();

					String markedAsCompleteDate = xPath.compile(
							"/submission/data/" + rootElement
									+ "/@markedAsCompleteDate").evaluate(
							submission_doc);

					LastVisitData lvd = new LastVisitData();
					if (!lastVisitDataMap.containsKey(instance_id)) {
						lvd.setMarkedAsCompleteDate(new Timestamp((sdf
								.parse(markedAsCompleteDate)).getTime()));
						lvd.setInstanceId(instance_id);

						if (xPath
								.compile(
										"/submission/data/" + rootElement
												+ xform.getAreaXPath())
								.evaluate(submission_doc)
								.equalsIgnoreCase("Others")) {
							String areaName = xPath.compile(
									"/submission/data/" + rootElement
											+ "/bg_a/other_hf").evaluate(
									submission_doc);
							String district = xPath.compile(
									"/submission/data/" + rootElement
											+ "/bg_a/district").evaluate(
									submission_doc);
							if (areaMap.containsKey(areaMap.get(district)
									.getAreaId() + "_" + areaName)) {
								lvd.setArea(areaMap.get(areaMap.get(district)
										.getAreaId() + "_" + areaName));
							} else {
								Area area = new Area();
								area.setAreaLevel(new AreaLevel(4));
								area.setAreaName(areaName);
								area.setParentAreaId(areaMap.get(district)
										.getAreaId());
								area.setIsLive(true);
								String areaCode = areaDetailsRepository
										.findTopOneByParentAreaIdOrderByAreaCodeDesc(
												area.getParentAreaId()).get(0)
										.getAreaCode();
								int areaCodeNumeric = Integer.parseInt(areaCode
										.split("D")[1]) + 1;
								area.setAreaCode("IND" + areaCodeNumeric);
								area = areaDetailsRepository.save(area);
								lvd.setArea(area);
								areaMap.put(area.getAreaCode(), area);
								areaMap.put(
										area.getParentAreaId() + "_"
												+ area.getAreaName(), area);

							}

						} else {
							lvd.setArea(areaMap.get(xPath.compile(
									"/submission/data/" + rootElement
											+ xform.getAreaXPath()).evaluate(
									submission_doc)));
						}
						lvd.setLive(true);
						// to be uncommented and images should be set into the
						// lvds
						List<String> imagexPaths = Arrays
								.asList(applicationMessageSource.getMessage(
										"images.xpath", null, null).split(","));
						String images = null;
						for (String imagexPath : imagexPaths) {
							String mediaFiles = xPath.compile(
									"/submission/data/" + rootElement + "/"
											+ imagexPath).evaluate(
									submission_doc);
							if (mediaFiles != null
									&& !mediaFiles.trim().equalsIgnoreCase("")) {
								if (images == null) {
									images = mediaFiles;
								} else {
									images += "," + mediaFiles;
								}
							}

						}

						if (!xPath
								.compile(
										"/submission/data/" + rootElement
												+ xform.getLocationXPath())
								.evaluate(submission_doc).trim()
								.equalsIgnoreCase("")) {
							lvd.setLatitude(xPath
									.compile(
											"/submission/data/" + rootElement
													+ xform.getLocationXPath())
									.evaluate(submission_doc).split(" ")[0]);
							lvd.setLongitude(xPath
									.compile(
											"/submission/data/" + rootElement
													+ xform.getLocationXPath())
									.evaluate(submission_doc).split(" ")[1]);
						}
						lvd.setxForm(xform);
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"dd-MM-yyyy");
						lvd.setDateOfVisit(new Date(dateFormat.parse(
								xPath.compile(
										"/submission/data/" + rootElement
												+ xform.getDateOfVisitXPath())
										.evaluate(submission_doc)).getTime()));
						if (images != null) {
							lvd.setImageFileNames(images);
						} else {
							lvd.setImageFileNames("");
						}
						lvd = lastVisitDataRepository.save(lvd);

					} else {
						lvd = lastVisitDataMap.get(instance_id);
					}
					String s = xPath.compile(
							"/submission/data/" + rootElement
									+ "/bg_a/facility")
							.evaluate(submission_doc);
					for (RawFormXapths formXapths : rawFormXapths) {
						RawDataScore rawDataScore = new RawDataScore();
						String score = null;
						if (formXapths.getXpath().equalsIgnoreCase(
								"/submission/data/" + rootElement
										+ "/bg_a/district")) {
							score = areaMap.get(
									xPath.compile(
											"/submission/data/" + rootElement
													+ "/bg_a/district")
											.evaluate(submission_doc))
									.getAreaName();
						} else if (formXapths.getXpath().equalsIgnoreCase(
								"/submission/data/" + rootElement + "/bg_a/hf")) {
							score = lvd.getArea().getAreaName();
						}

						else if (formXapths.getXpath().equalsIgnoreCase(
								"/submission/data/" + rootElement
										+ "/bg_a/facility")) {
							score = facilityTypeMap.get(Integer.parseInt(s))
									.getFacilityName();

						} else {
							if (xPath.compile(formXapths.getXpath()).evaluate(
									submission_doc) == null) {
								score = "";
							} else
								score = xPath.compile(formXapths.getXpath())
										.evaluate(submission_doc);

						}
						rawDataScore.setLastVisitData(lvd);
						rawDataScore.setRawFormXapths(formXapths);
						rawDataScore.setScore(score);
						rawDataScoreRepository.save(rawDataScore);
					}

					lastVisitDataMap.put(instance_id, lvd);
				}
			}

		}
		return true;
	}

	private String generateFormID(String getxFormId, String formRooTitle,
			String instance_id) {

		return getxFormId + "[@version=null and @uiVersion=null]/"
				+ formRooTitle + "" + "[@key=" + instance_id + "]";
	}

	private StringWriter getXML(String Form, String serverURL, String userName,
			String password, String base_xml_download_url) throws Exception {
		AggregateUtils.DocumentFetchResult result = null;
		XmlSerializer serializer = new KXmlSerializer();
		StringWriter base_xml = new StringWriter();

		ServerConnectionInfo serverInfo = new ServerConnectionInfo(serverURL,

		userName, password.toCharArray());

		DocumentDescription submissionDescription = new DocumentDescription(
				"Fetch of manifest failed. Detailed reason: ",
				"Fetch of manifest failed ", "form manifest",
				new TerminationFuture());

		result = AggregateUtils.getXmlDocument(
				base_xml_download_url.concat(Form), serverInfo, false,
				submissionDescription, null);
		serializer.setOutput(base_xml);
		result.doc.write(serializer);

		return base_xml;
	}

	/* (non-Javadoc)
	 * @see org.sdrc.slr.service.MasterRawDataService#generateExcel()
	 * This will generate raw data of all submission when user will request for generation of excel
	 */
	@Override
	@Transactional
	public String generateExcel() throws Exception {
		List<ChoicesDetails> choicesDetailsList = choiceDetailsRepository
				.findAll();

		List<RawFormXapths> formXapths = rawFormXapthsRepository.findAll();
		List<TimePeriod> timePeriods = timePeriodRepository
				.findByOrderByStartDateDesc();

		Map<Integer, TimePeriod> timePeriodMap = new HashMap<Integer, TimePeriod>();
		for (TimePeriod timePeriod : timePeriods) {
			timePeriodMap.put(timePeriod.getTimePeriodId(), timePeriod);
		}
		List<LastVisitData> lastVisitDatas = lastVisitDataRepository
				.findAllByIsLiveTrueOrderByMarkedAsCompleteDateDesc();
		Map<String, ChoicesDetails> choicesMap = new HashMap<String, ChoicesDetails>();
		for (ChoicesDetails choicesDetails : choicesDetailsList) {
			choicesMap.put("select_one " + choicesDetails.getChoicName() + "_"
					+ choicesDetails.getLabel(), choicesDetails);
		}

		// select_one district

		XSSFWorkbook rawDataWorkBook = new XSSFWorkbook();
		XSSFCell headerCell=null;
		XSSFRow headerRow = null;
		XSSFRow xpathRow = null;
		XSSFFont font =null;
		XSSFSheet rawDataSheet=null;
		

		XSSFCellStyle style = rawDataWorkBook.createCellStyle();
		style.setFont(font);

		style.setAlignment(CellStyle.ALIGN_GENERAL);
		style.setVerticalAlignment(CellStyle.ALIGN_GENERAL);

		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		
		
		int i;
		
		Map<String, Integer> xpathMap = new HashMap<String, Integer>();
		
		if(lastVisitDatas.get(0).getMarkedAsCompleteDate().after(
				timePeriods.get(0).getEndDate())&& DateUtils.isSameDay(lastVisitDatas.get(0).getMarkedAsCompleteDate(), timePeriods.get(0).getEndDate()))
		{
		 rawDataSheet = rawDataWorkBook.createSheet("Phase "
				+ (timePeriods.size() + 1));

		 headerRow = rawDataSheet.createRow(0);
		 xpathRow = rawDataSheet.createRow(1);
		 font = rawDataWorkBook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);

		 headerCell = headerRow.createCell(0);
		headerCell.setCellValue("Response");
		headerCell.setCellStyle(style);
		rawDataSheet.autoSizeColumn(headerCell.getColumnIndex());
		 i = 1;
		for (RawFormXapths rawFormXapth : formXapths) {
			if (!rawFormXapth.getLabel().trim().equalsIgnoreCase(""))
			{
			headerCell = headerRow.createCell(i);
			XSSFCell xpathCell = xpathRow.createCell(i);
			headerCell.setCellValue(rawFormXapth.getLabel());
				if(rawFormXapth.getXpath().contains("L1L2"))
				{
					headerCell.setCellValue(rawFormXapth.getLabel()+" \n(For Facility Level : L1 or L2) ");
				}
				else if(rawFormXapth.getXpath().contains("L3"))
				{
					headerCell.setCellValue(rawFormXapth.getLabel()+" \n(For Facility Level : L3)");
				}
			headerCell.setCellStyle(style);
				rawDataSheet.autoSizeColumn(headerCell.getColumnIndex());
				

			xpathCell.setCellValue(rawFormXapth.getXpath());
			xpathMap.put(rawFormXapth.getXpath(), xpathCell.getColumnIndex());

			i++;
			}
		}
		}

		XSSFRow valueRow;
		int rowIndex = 2;
		int responseNo = 1;
		XSSFCellStyle colStyle = rawDataWorkBook.createCellStyle();
		colStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		colStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		colStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		colStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		for (LastVisitData lastVisitData : lastVisitDatas) {
			if (lastVisitData.getRawDataScore() != null
					|| lastVisitData.getRawDataScore().size() > 0) {

				if (timePeriods.size() > 0) {
					if ((lastVisitData.getMarkedAsCompleteDate().before(
							timePeriods.get(0).getEndDate()) && lastVisitData
							.getMarkedAsCompleteDate().after(
									timePeriods.get(0).getStartDate()))
							|| DateUtils.isSameDay(
									lastVisitData.getMarkedAsCompleteDate(),
									timePeriods.get(0).getEndDate())
							|| DateUtils.isSameDay(
									lastVisitData.getMarkedAsCompleteDate(),
									timePeriods.get(0).getStartDate())) {
						i = 1;

						int timePeriodSize = timePeriods.size();
						if(rawDataSheet!=null)
						removeRow(rawDataSheet, xpathRow.getRowNum());
						rawDataSheet = rawDataWorkBook.createSheet("Phase "
								+ timePeriodSize);
						timePeriods.remove(0);
						xpathMap = new HashMap<String, Integer>();
						headerRow = rawDataSheet.createRow(0);
						xpathRow = rawDataSheet.createRow(1);
						headerCell = headerRow.createCell(0);
						headerCell.setCellValue("Response");
						headerCell.setCellStyle(style);
						rawDataSheet
								.autoSizeColumn(headerCell.getColumnIndex());
						for (RawFormXapths rawFormXapth : formXapths) {
							if (!rawFormXapth.getLabel().trim()
									.equalsIgnoreCase(""))
							{
							headerCell = headerRow.createCell(i);
							XSSFCell xpathCell = xpathRow.createCell(i);
							headerCell.setCellValue(rawFormXapth.getLabel());
							if(rawFormXapth.getXpath().contains("L1L2"))
							{
								headerCell.setCellValue(rawFormXapth.getLabel()+" \n(For Facility Level : L1 or L2) ");
							}
							else if(rawFormXapth.getXpath().contains("L3"))
							{
								headerCell.setCellValue(rawFormXapth.getLabel()+" \n(For Facility Level : L3)");
							}
							headerCell.setCellStyle(style);
								rawDataSheet.autoSizeColumn(headerCell
										.getColumnIndex());

							xpathCell.setCellValue(rawFormXapth.getXpath());
							xpathMap.put(rawFormXapth.getXpath(),
									xpathCell.getColumnIndex());

							i++;
							}
						}
						rowIndex = 2;
						responseNo = 1;

					}
				}
				valueRow = rawDataSheet.createRow(rowIndex);
				XSSFCell responseCell = valueRow.createCell(0);
				responseCell.setCellValue("Response_" + responseNo);
				responseCell.setCellStyle(colStyle);
				rawDataSheet.autoSizeColumn(responseCell.getColumnIndex());
				Map<String,RawDataScore> rawDataScoreMap=new HashMap<String, RawDataScore>();
				for (RawDataScore rawDataScore : lastVisitData
						.getRawDataScore()) {
					rawDataScoreMap.put(rawDataScore.getRawFormXapths().getXpath(),rawDataScore);

				}
				String hf=null;
				if(timePeriods.size()>0)
				{
				hf=rawDataScoreMap.get("/submission/data/Checklist_for_Standardization_of_Birthing_Units_Labour_Rooms_040217_V5/bg_a/cal_4.1").getScore();
				}
				else
				{
				hf=	rawDataScoreMap.get("/submission/data/Checklist_for_Standardization_of_Birthing_Units_Labour_Rooms_040217_V5/bg_a/level").getScore();
				}
				for (RawDataScore rawDataScore : lastVisitData
						.getRawDataScore()) {
				
					if(xpathMap.containsKey(rawDataScore.getRawFormXapths().getXpath()))
					{
					if(timePeriods.size()>0)
					{
				responseCell = valueRow.createCell(xpathMap
							.get(rawDataScore.getRawFormXapths().getXpath()));
					if (rawDataScore.getRawFormXapths().getType()
							.contains("select_one")) {
						try {
							Integer.parseInt(rawDataScore.getScore());
							responseCell.setCellValue(choicesMap.get(
									rawDataScore.getRawFormXapths().getType()
											+ "_" + rawDataScore.getScore())
									.getChoiceValue());

						} catch (Exception e) {
							responseCell.setCellValue(rawDataScore.getScore());
						}

					} else {
						if(rawDataScore.getRawFormXapths().getCalXpaths()==null)
						{
							
							if(rawDataScore.getRawFormXapths().getType().equalsIgnoreCase("image") && !rawDataScore.getScore().trim().equalsIgnoreCase("") )
							{
								String url="http://prod1.sdrc.co.in:8080/EVMCG/view/binaryData?blobKey=Checklist_for_Standardization_of_Birthing_Units_Labour_Rooms_040217_V5%5B%40version%3Dnull+and+%40uiVersion%3Dnull%5D%2FChecklist_for_Standardization_of_Birthing_Units_Labour_Rooms_040217_V5%5B%40key%3D";
								String instanceId=rawDataScore.getLastVisitData().getInstanceId().replace(":","%3A");
								url+=instanceId+"%5D%2F"+rawDataScore.getRawFormXapths().getXpath().split("/")[rawDataScore.getRawFormXapths().getXpath().split("/").length-1];
								responseCell.setCellValue(url);
								CreationHelper creationHelper = rawDataWorkBook.getCreationHelper();
								XSSFHyperlink link = (XSSFHyperlink) creationHelper
										.createHyperlink(Hyperlink.LINK_URL);
								link.setAddress(url);
								responseCell.setHyperlink(link);
							}
							else
							{	
						responseCell.setCellValue(rawDataScore.getScore());
							}
							}
						else
						{
							if(((hf.equalsIgnoreCase("L1")||hf.equalsIgnoreCase("L2"))&&!rawDataScore.getRawFormXapths().getXpath().contains("L3"))||((hf.equalsIgnoreCase("L3"))&&!rawDataScore.getRawFormXapths().getXpath().contains("L1L2")))
							responseCell.setCellValue(rawDataScoreMap.get(rawDataScore.getRawFormXapths().getCalXpaths()).getScore());
						}
					}
					responseCell.setCellStyle(colStyle);
					}
					else
					{
						responseCell = valueRow.createCell(xpathMap
								.get(rawDataScore.getRawFormXapths().getXpath()));
						if (rawDataScore.getRawFormXapths().getType()
								.contains("select_one")) {
							try {
								Integer.parseInt(rawDataScore.getScore());
								responseCell.setCellValue(choicesMap.get(
										rawDataScore.getRawFormXapths().getType()
												+ "_" + rawDataScore.getScore())
										.getChoiceValue());

							} catch (Exception e) {
								responseCell.setCellValue(rawDataScore.getScore());
							}

						} else {
							if(((hf.equalsIgnoreCase("L1")||hf.equalsIgnoreCase("L2"))&&!rawDataScore.getRawFormXapths().getXpath().contains("L3"))||((hf.equalsIgnoreCase("L3"))&&!rawDataScore.getRawFormXapths().getXpath().contains("L1L2")))
							responseCell.setCellValue(rawDataScore.getScore());
						}
						responseCell.setCellStyle(colStyle);
						}
					}
				}
				if (timePeriods.size() == 0) {
					for (int j = 0; j < valueRow.getLastCellNum(); j++) {
						XSSFCell cell = valueRow.getCell(j);
						if (cell == null) {
							cell = valueRow.createCell(j);
							cell.setCellStyle(colStyle);
						}
					}

				}

			}
			rowIndex++;
			responseNo++;
		}

		removeRow(rawDataSheet, xpathRow.getRowNum());
		String outputPath = context.getRealPath("resources\\excelFiles\\"
				+ applicationMessageSource.getMessage("rawDataOuputPath", null,
						null));
		FileOutputStream fileOut = null;
		fileOut = new FileOutputStream(outputPath);
		rawDataWorkBook.write(fileOut);
		fileOut.close();
		return outputPath;
	}

	private static void removeRow(XSSFSheet sheet, int rowIndex) {
		int lastRowNum = sheet.getLastRowNum();
		if (rowIndex >= 0 && rowIndex < lastRowNum) {
			sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
		}
		if (rowIndex == lastRowNum) {
			XSSFRow removingRow = sheet.getRow(rowIndex);
			if (removingRow != null) {
				sheet.removeRow(removingRow);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.sdrc.slr.service.MasterRawDataService#persisitPhase1Data()
	 * Persisting phase1 data from excel file
	 */
	@Override
	@Transactional
	public void persisitPhase1Data() throws Exception {
		List<RawFormXapths> rawXpaths = rawFormXapthsRepository.findAll();
		List<XForm> xforms = xFormRepository.findByIsLiveTrue();

		XForm xform = xforms.get(0);
		Map<String, RawFormXapths> rawFormXpathsMap = new HashMap<String, RawFormXapths>();
		for (RawFormXapths formXapths : rawXpaths) {
			String[] tempVariable = formXapths.getXpath().split("/");
			rawFormXpathsMap.put(
					formXapths.getXpath().split("/")[tempVariable.length - 1],
					formXapths);
		}

		List<Area> areas = areaDetailsRepository.findAll();
		Map<String, Area> areaMap = new HashMap<String, Area>();

		for (Area area : areas) {
			areaMap.put(area.getAreaCode(), area);
		}

		String inputFilePath = "D://Standardization_of_Birthing_Unit_R3_r1.xlsx";
		FileInputStream fileInputStream = new FileInputStream(inputFilePath);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);

		XSSFSheet sheet = xssfWorkbook.getSheet("SCORE");

		for (int i = 4; i <= sheet.getRow(0).getLastCellNum() - 1; i++) {
			XSSFRow areaRow = sheet.getRow(6);
			XSSFCell areaCell = areaRow.getCell(i);
			LastVisitData lastVisitData = new LastVisitData();
			lastVisitData.setArea(areaMap.get(areaCell.getStringCellValue()));
			lastVisitData.setxForm(xform);
			lastVisitData.setDateOfVisit(new Date(sdf2.parse("01-01-2017")
					.getTime()));
			lastVisitData.setMarkedAsCompleteDate(new Timestamp(sdf2.parse(
					"01-01-2017").getTime()));
			lastVisitData.setInstanceId("uuid:slrphase1" + i);
			lastVisitData.setLive(true);
			lastVisitData = lastVisitDataRepository.save(lastVisitData);
			for (int j = 3; j <= sheet.getLastRowNum(); j++) {
				XSSFRow rawScoreRow = sheet.getRow(j);
				XSSFCell rawScoreCell = rawScoreRow.getCell(i);
				XSSFCell xpathCell = rawScoreRow.getCell(1);
				if (xpathCell != null && xpathCell.getStringCellValue() != null) {
					RawDataScore rawDataScore = new RawDataScore();
					rawDataScore.setLastVisitData(lastVisitData);
					try {
						if (areaMap.containsKey(rawScoreCell
								.getStringCellValue())) {
							rawDataScore.setScore(areaMap.get(
									rawScoreCell.getStringCellValue())
									.getAreaName());
						} else {
							rawDataScore.setScore(rawScoreCell
									.getStringCellValue() == null ? ""
									: rawScoreCell.getStringCellValue());
						}
					} catch (Exception e) {
						if (rawScoreCell == null) {
							rawDataScore.setScore("");
						} else {
							rawDataScore.setScore(String.valueOf(rawScoreCell
									.getNumericCellValue()));
						}
					}
					rawDataScore.setRawFormXapths(rawFormXpathsMap
							.get(xpathCell.getStringCellValue()));
					if (rawDataScore.getRawFormXapths() != null)
						rawDataScoreRepository.save(rawDataScore);
				}
			}

		}
	}

	/* (non-Javadoc)
	 * @see org.sdrc.slr.service.MasterRawDataService#persisitPhase1DataDashboard()
	 * Persist PHASE1 data for dashboard
	 */
	@Transactional
	@Override
	public void persisitPhase1DataDashboard() throws Exception {
		// List<RawFormXapths> rawXpaths=rawFormXapthsRepository.findAll();
		// List<XForm> xforms = xFormRepository.findByIsLiveTrue();
		List<LastVisitData> lastVisitDatas = lastVisitDataRepository
				.findAllByIsLiveTrueAndMarkedAsCompleteDate(new Timestamp(sdf2
						.parse("01-01-2017").getTime()));

		Map<String, LastVisitData> lastVisitDataMap = new HashMap<String, LastVisitData>();
		for (LastVisitData lastVisitData : lastVisitDatas) {
			lastVisitDataMap.put(lastVisitData.getArea().getAreaCode(),
					lastVisitData);

		}

		List<FacilityType> facilityTypes = facilityTypeRepository.findAll();

		Map<Integer, FacilityType> facilityTypeMap = new HashMap<Integer, FacilityType>();

		for (FacilityType facilityType : facilityTypes) {
			facilityTypeMap.put(facilityType.getFacilityId(), facilityType);
		}

		// XForm xform = xforms.get(0);
		// Map<String,RawFormXapths> rawFormXpathsMap=new HashMap<String,
		// RawFormXapths>();
		/*
		 * for(RawFormXapths formXapths:rawXpaths) { String []
		 * tempVariable=formXapths.getXpath().split("/");
		 * rawFormXpathsMap.put(formXapths
		 * .getXpath().split("/")[tempVariable.length-1], formXapths); }
		 */

		/*
		 * List<Area> areas=areaDetailsRepository.findAll(); Map<String, Area>
		 * areaMap = new HashMap<String, Area>();
		 * 
		 * for (Area area : areas) { areaMap.put(area.getAreaCode(), area); }
		 */

		String inputFilePath = "D://Standardization_of_Birthing_Unit_R3_r1.xlsx";
		FileInputStream fileInputStream = new FileInputStream(inputFilePath);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);

		XSSFSheet sheet = xssfWorkbook.getSheet("SCORE");

		for (int i = 4; i <= sheet.getRow(0).getLastCellNum() - 1; i++) {
			XSSFRow areaRow = sheet.getRow(6);
			XSSFCell areaCell = areaRow.getCell(i);

			XSSFRow facilityRow = sheet.getRow(9);
			XSSFCell facilityCell = facilityRow.getCell(i);

			Map<String, FormXpathScoreMapping> formXpathScoreMappingMap = new HashMap<String, FormXpathScoreMapping>();
			for (FormXpathScoreMapping formXpathScoreMappings : facilityTypeMap
					.get((int) facilityCell.getNumericCellValue())
					.getFormXpathScoreMappings()) {
				String[] tempVariable = formXpathScoreMappings.getFormXpath()
						.getxPath().split("/");
				formXpathScoreMappingMap.put(
						formXpathScoreMappings.getFormXpath().getxPath()
								.split("/")[tempVariable.length - 1].replace(
								"cal", "sec"), formXpathScoreMappings);
			}
			/*
			 * LastVisitData lastVisitData=new LastVisitData();
			 * lastVisitData.setArea
			 * (areaMap.get(areaCell.getStringCellValue()));
			 * lastVisitData.setxForm(xform); lastVisitData.setDateOfVisit(new
			 * Date(sdf2.parse("01-01-2017").getTime()));
			 * lastVisitData.setMarkedAsCompleteDate(new
			 * Timestamp(sdf2.parse("01-01-2017").getTime()));
			 * lastVisitData.setInstanceId("uuid:slrphase1"+i);
			 * lastVisitData.setLive(true);
			 * lastVisitData=lastVisitDataRepository.save(lastVisitData);
			 */
			for (int j = 3; j <= sheet.getLastRowNum(); j++) {
				XSSFRow rawScoreRow = sheet.getRow(j);
				XSSFCell rawScoreCell = rawScoreRow.getCell(i);
				XSSFCell xpathCell = rawScoreRow.getCell(1);
				if (xpathCell != null
						&& xpathCell.getStringCellValue() != null
						&& formXpathScoreMappingMap.containsKey(xpathCell
								.getStringCellValue())) {

					FacilityScore facilityScore = new FacilityScore();

					facilityScore
							.setFormXpathScoreMapping(formXpathScoreMappingMap
									.get(xpathCell.getStringCellValue()));
					facilityScore.setLastVisitData(lastVisitDataMap
							.get(areaCell.getStringCellValue()));
					try {
						if(rawScoreCell
								.getStringCellValue().trim().equalsIgnoreCase(""))
						{
							facilityScore.setScore(null);
						}
						else
						{
						facilityScore.setScore(rawScoreCell
								.getStringCellValue() == null ? null : Double
								.valueOf(rawScoreCell.getStringCellValue()));
						}
					} catch (Exception e) {
						if (rawScoreCell == null) {
							facilityScore.setScore(null);

						} else {
							facilityScore.setScore((rawScoreCell
									.getNumericCellValue()));
						}
					}
					facilityScoreRepository.save(facilityScore);
					/*
					 * RawDataScore rawDataScore=new RawDataScore();
					 * rawDataScore.setLastVisitData(lastVisitDataMap.get); try
					 * {
					 * if(areaMap.containsKey(rawScoreCell.getStringCellValue()
					 * )) { rawDataScore.setScore(areaMap.get(rawScoreCell.
					 * getStringCellValue()).getAreaName()); } else {
					 * rawDataScore
					 * .setScore(rawScoreCell.getStringCellValue()==null
					 * ?"":rawScoreCell.getStringCellValue()); } }
					 * catch(Exception e) { if(rawScoreCell==null) {
					 * rawDataScore.setScore(""); } else {
					 * rawDataScore.setScore(
					 * String.valueOf(rawScoreCell.getNumericCellValue())); } }
					 * rawDataScore
					 * .setRawFormXapths(rawFormXpathsMap.get(xpathCell
					 * .getStringCellValue()));
					 * if(rawDataScore.getRawFormXapths()!=null)
					 * rawDataScoreRepository.save(rawDataScore);
					 */
				}
			}

		}
	}

}
