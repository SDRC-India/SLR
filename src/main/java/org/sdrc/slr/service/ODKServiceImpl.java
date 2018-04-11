/**
 * 
 */
package org.sdrc.slr.service;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.kxml2.io.KXmlSerializer;
import org.opendatakit.briefcase.model.DocumentDescription;
import org.opendatakit.briefcase.model.ServerConnectionInfo;
import org.opendatakit.briefcase.model.TerminationFuture;
import org.opendatakit.briefcase.util.AggregateUtils;
import org.opendatakit.briefcase.util.WebUtils;
import org.sdrc.slr.domain.Area;
import org.sdrc.slr.domain.AreaLevel;
import org.sdrc.slr.domain.FacilityScore;
import org.sdrc.slr.domain.FacilityType;
import org.sdrc.slr.domain.FormXpathScoreMapping;
import org.sdrc.slr.domain.LastVisitData;
import org.sdrc.slr.domain.XForm;
import org.sdrc.slr.repository.AreaDetailsRepository;
import org.sdrc.slr.repository.FacilityScoreRepository;
import org.sdrc.slr.repository.FacilityTypeRepository;
import org.sdrc.slr.repository.LastVisitDataRepository;
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
 * @author Harsh Pratyush
 *
 */
@Service
public class ODKServiceImpl implements ODKService {

	@Autowired
	AreaDetailsRepository areaDetailsRepository;

	@Autowired
	FacilityTypeRepository facilityTypeRepository;

	@Autowired
	LastVisitDataRepository lastVisitDataRepository;

	@Autowired
	FacilityScoreRepository facilityScoreRepository;

	@Autowired
	ResourceBundleMessageSource applicationMessageSource;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("yyyy-MM-dd HH:mm:ss.S");

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sdrc.slr.service.ODKService#updateFacilityScore()
	 */
	@Override
	@Transactional
	public boolean updateFacilityScore() throws Exception {

		List<Area> areaDetails = areaDetailsRepository.findAll();

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
						|| lastVisitDataMap.get(instance_id)
								.getFacilityScores() == null
						|| lastVisitDataMap.get(instance_id)
								.getFacilityScores().size() < 1
						|| lastVisitDataMap.get(instance_id)
								.getFacilityScores().isEmpty()) {
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
					for (FormXpathScoreMapping formXpathScoreMapping : facilityTypeMap
							.get(Integer.parseInt(s))
							.getFormXpathScoreMappings()) {
						FacilityScore facilityScore = new FacilityScore();
						facilityScore
								.setFormXpathScoreMapping(formXpathScoreMapping);
						if (xPath
								.compile(
										"/submission/data/"
												+ rootElement
												+ "/"
												+ formXpathScoreMapping
														.getFormXpath()
														.getxPath())
								.evaluate(submission_doc).trim()
								.equalsIgnoreCase("")) {

						} else {
							facilityScore.setScore(Double.parseDouble(xPath
									.compile(
											"/submission/data/"
													+ rootElement
													+ "/"
													+ formXpathScoreMapping
															.getFormXpath()
															.getxPath())
									.evaluate(submission_doc)));

						}
						facilityScore.setLastVisitData(lvd);
						facilityScoreRepository.save(facilityScore);
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

}
