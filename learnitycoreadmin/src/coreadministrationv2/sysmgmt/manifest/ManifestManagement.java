package coreadministrationv2.sysmgmt.manifest;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ecs.html.Body;
import org.apache.ecs.html.Form;
import org.apache.ecs.html.Head;
import org.apache.ecs.html.Html;
import org.apache.ecs.html.Input;
import org.apache.ecs.html.Link;
import org.apache.ecs.html.Script;
import org.apache.ecs.html.TD;
import org.apache.ecs.html.TR;
import org.apache.ecs.html.Table;
import org.apache.ecs.html.Title;

import comv2.aunwesha.JSPGrid.JSPGridPro2;
import comv2.aunwesha.lfutil.GenericUtil;
import comv2.aunwesha.lfutil.LfServlet;
import comv2.aunwesha.lfutil.Pair;

import coreadministrationv2.dbconnection.DataBaseLayer;
import coreadministrationv2.sysmgmt.InterfaceManagement;
import coreadministrationv2.sysmgmt.xml.dao.ManifestDao;
import coreadministrationv2.utility.TableExtension;

/**
 * Title: LearnITy
 * 
 * Description: Copyright: Copyright (c) 2006 Company: Aunwesha
 * 
 * @version
 */

public class ManifestManagement extends LfServlet {

	private static final long serialVersionUID = -7258717012840654659L;
	private static final String _NEW_OPERATION = "new";
	private static final String _SAVE_OPERATION = "save";
	private static final String _TYPE_SELECT_NAME = "type";
	private static final String _INTERFACE_INPUT_NAME = "interfaceName";
	private static final String _MANIFEST_NAME = "manifestName";
	private static final String _INTERFACE_TITLE = "interfaceTitle";
	private static final String _ZIP_INPUT_NAME = "zipName";

	// private static final SimpleLogger LOG = new
	// SimpleLogger(ManifestManagement.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		super.doGet(request, response);
		String operation = request.getParameter("operation");
		if (_NEW_OPERATION.equals(operation)) {
			generateNewPopUpPage(request, response, "");
		} else if (_SAVE_OPERATION.equals(operation)) {
			String statusMessage = addInterface(request);
			generateNewPopUpPage(request, response, statusMessage);
		} else {
			generateMainPage(request, response);
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request, response);

	}

	private String addInterface(HttpServletRequest request) {
		String manifestId = ManifestDao.getManifestId();
		
		if(GenericUtil.isEmptyString(manifestId)){
			manifestId = request.getParameter(_MANIFEST_NAME);
		}
		
		String statusMessage = "";
		
		if(GenericUtil.hasString(manifestId)){
			String interfaceId = request.getParameter(_INTERFACE_INPUT_NAME);
			String checkId = DataBaseLayer.CheckInterfaceID(interfaceId);
			
			if (GenericUtil.isEmptyString(checkId)) {
				String fileName = request.getParameter(_ZIP_INPUT_NAME);
				if (!fileName.contains(".zip")) {
					fileName = fileName + ".zip";
				}
				String interfaceTitle = request.getParameter(_INTERFACE_TITLE);
				String type = request.getParameter(_TYPE_SELECT_NAME);
				DataBaseLayer.insertinterfacemanifestassociation(interfaceId, manifestId);
				DataBaseLayer.insertinterface(interfaceId, interfaceTitle, type, "0");
				DataBaseLayer.FrameworkFile1(interfaceId, interfaceTitle, fileName, type, "0");
				statusMessage = "Interface added. Please uplaod from interface Management!";
			} else {
				statusMessage = "Interface already exists!";
			}
		}else{
			statusMessage = "Manifest id is not present";
		}
		return statusMessage;
	}

	private void generateMainPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String formName = "manifestForm";
		Pair<String, String> dateTime = this.retrieveDateTime();
		Html html = new Html().addElement(new Head().addElement(new Title("Interface Management"))
				.addElement(new Link().setHref("../coreadmin/css/stylesheet.css").setRel("stylesheet"))
				.addElement(new Script().setLanguage("JavaScript").setSrc("../coreadmin/js/check.js"))
		/*
		 * .addElement(new
		 * Script().setLanguage("JavaScript").addElement(javaScript))
		 */);

		Form form = new Form().setName(formName).setMethod("post");
		Body body = new Body().addElement(new TableExtension().headerTable("<b>Administrator:</b> " + getLoggedInUserName(request),
				dateTime.getFirst(), dateTime.getSecond(), "<b>Portal Administration:</b> Manifest Management"));
		try {
			generateManifestTable(request, formName, form);
		} catch (Exception e) {
			e.printStackTrace();
		}
		createOperations(html, form, formName);
		body.addElement(form);
		html.addElement(body.setClass("bodyadmin"));

		PrintWriter out = response.getWriter();
		out.print(html.toString());
	}

	private void generateNewPopUpPage(HttpServletRequest request, HttpServletResponse response, String statusMessage) throws IOException {
		String formName = "newManifestForm";
		Pair<String, String> dateTime = this.retrieveDateTime();

		Html html = new Html().addElement(new Head().addElement(new Title("Interface Management"))
				.addElement(new Link().setHref("../coreadmin/css/stylesheet.css").setRel("stylesheet"))
				.addElement(new Script().setLanguage("JavaScript").setSrc("../coreadmin/js/check.js"))
		/*
		 * .addElement(new
		 * Script().setLanguage("JavaScript").addElement(javaScript))
		 */);

		String manifestId = ManifestDao.getManifestId();
		
		Form form = new Form().setName(formName).setMethod("post");
		Body body=new Body();
		body.addAttribute("onload", "opener.location.reload();");
		body.addElement(new TableExtension().headerTable("<b>Administrator:</b> " + getLoggedInUserName(request),
				dateTime.getFirst(), dateTime.getSecond(), "<b>Portal Administration:</b> New Manifest Add"));

		Table newManifestTable = new Table().setCellPadding(2).setCellSpacing(1).setBorder(1);

		// newManifestTable.addElement(new TR().addElement(new
		// TD(" ").setColSpan(3).setClass("swb")));

		{
			TR manifestNameRow = new TR();

			TD manifestNameColumn = new TD();
			manifestNameColumn = manifestNameColumn.addElement("Manifest Name");
			TD manifestNameInputColumn = new TD().setColSpan(2);

			if(GenericUtil.isEmptyString(manifestId)){
				
				Input manifestNameInput = new Input().setName(_MANIFEST_NAME).setType("text").setTitleValue(_MANIFEST_NAME);
				manifestNameInputColumn = manifestNameInputColumn.addElement(manifestNameInput);
				
			}else{

				manifestNameInputColumn = manifestNameInputColumn.addElement(manifestId);
				
			}
			manifestNameRow.addElement(manifestNameColumn).addElement(manifestNameInputColumn);
			newManifestTable = newManifestTable.addElement(manifestNameRow);
		}

		{
			TR selectionRow = new TR();

			TD typeColumn = new TD();
			typeColumn = typeColumn.addElement("Type");

			TD interfaceSelectionColumn = new TD();
			Input interfaceSelection = new Input().setName(_TYPE_SELECT_NAME).setType("radio").setTitleValue(InterfaceManagement.INTERFACE_TYPE)
					.setValue(InterfaceManagement.INTERFACE_TYPE);
			interfaceSelectionColumn = interfaceSelectionColumn.addElement("Interface").addElement(interfaceSelection);

			TD interfaceFragmentSelectionColumn = new TD();
			Input interfaceFragementSelection = new Input().setName(_TYPE_SELECT_NAME).setType("radio")
					.setTitleValue(InterfaceManagement.INTERFACE_FRAGMENT_TYPE).setValue(InterfaceManagement.INTERFACE_FRAGMENT_TYPE);
			interfaceFragmentSelectionColumn = interfaceFragmentSelectionColumn.addElement("Interface Fragment").addElement(
					interfaceFragementSelection);

			selectionRow.addElement(typeColumn).addElement(interfaceSelectionColumn).addElement(interfaceFragmentSelectionColumn);

			newManifestTable = newManifestTable.addElement(selectionRow);
		}

		
		
		{
			TR interfcaeNameRow = new TR();

			TD fileNameColumn = new TD();
			fileNameColumn = fileNameColumn.addElement("Name");

			TD fileNameInputColumn = new TD().setColSpan(2);
			Input fileNameInput = new Input().setName(_INTERFACE_INPUT_NAME).setType("text").setTitleValue(_INTERFACE_INPUT_NAME);
			fileNameInputColumn = fileNameInputColumn.addElement(fileNameInput);

			interfcaeNameRow.addElement(fileNameColumn).addElement(fileNameInputColumn);

			newManifestTable = newManifestTable.addElement(interfcaeNameRow);
		}

		{
			TR interfcaeTitleRow = new TR();

			TD interfcaeTitleColumn = new TD();
			interfcaeTitleColumn = interfcaeTitleColumn.addElement("Title");

			TD interfcaeTitleInputColumn = new TD().setColSpan(2);
			Input interfcaeTitleInputInput = new Input().setName(_INTERFACE_TITLE).setType("text").setTitleValue(_INTERFACE_TITLE);
			interfcaeTitleInputColumn = interfcaeTitleInputColumn.addElement(interfcaeTitleInputInput);

			interfcaeTitleRow.addElement(interfcaeTitleColumn).addElement(interfcaeTitleInputColumn);

			newManifestTable = newManifestTable.addElement(interfcaeTitleRow);
		}

		{
			TR zipNameRow = new TR();

			TD zipNameColumn = new TD();
			zipNameColumn = zipNameColumn.addElement("File Name");

			TD zipNameInputColumn = new TD().setColSpan(2);
			Input zipNameInput = new Input().setName(_ZIP_INPUT_NAME).setType("text").setTitleValue(_ZIP_INPUT_NAME);
			zipNameInputColumn = zipNameInputColumn.addElement(zipNameInput);

			zipNameRow.addElement(zipNameColumn).addElement(zipNameInputColumn);

			newManifestTable = newManifestTable.addElement(zipNameRow);
		}

		form.addElement(newManifestTable);

		createNewManifestOperations(html, form, formName);
		form.addElement("<div id=\"status-message\" style=\"color:red;\">" + statusMessage + "</div>");
		body.addElement(form);
		html.addElement(body.setClass("bodyadmin"));

		PrintWriter out = response.getWriter();
		out.print(html.toString());
	}

	private void generateManifestTable(HttpServletRequest request, String formName, Form form) throws Exception {

		//String manifestId = ManifestDao.getManifestId();

		String manifestQuerySql = "select f.framework_file_id as \"Interface Title\",f.filename as \"File Name\",f.type as \"File Type\" from"
				+ " manifestinterfaceassociation m,framework_file f where m.interface_id=f.framework_file_id";
		JSPGridPro2 grid1 = new JSPGridPro2(request, formName);
		grid1.setConnectionParameters(manifestQuerySql);

		grid1.setWidth("100%");
		grid1.setCellPadding(2);
		grid1.setCellSpacing(1);
		grid1.setFontFace("Arial");
		grid1.setFontSize(2);
		grid1.setEvenRowBgColor("#C0C0C0");
		grid1.setOddRowBgColor("#F0F0F0");
		grid1.setCaption("");
		grid1.setMaxRowsPerPage(10); // how many records displayed per page
		grid1.setMaxResultPagesPerLoad(10); // Page : 1 2 3 4 5 6 7 8 9 10
											// (max 10 pages displayed)
		grid1.setLineNoHeaderBgColor("#48E6F7");
		grid1.Cols(0).setFieldType(JSPGridPro2.FIELD_HIDDEN);
		grid1.Cols(1).setFieldType(JSPGridPro2.FIELD_HIDDEN);
		grid1.Cols(2).setFieldType(JSPGridPro2.FIELD_HIDDEN);

		grid1.Cols(0).setFieldName("hiddenTilte");
		grid1.Cols(1).setFieldName("hiddenName");
		grid1.Cols(2).setFieldName("hiddenType");

		grid1.Cols(0).Header().setClassID("swb");
		grid1.Cols(1).Header().setClassID("swb");
		grid1.Cols(2).Header().setClassID("swb");

		grid1.setEachRowHeight("20");
		grid1.canSort(0, true);
		grid1.canSort(1, true);
		grid1.canSort(2, true);

		grid1.setSortableColumnsToolTip("Click to Sort");
		grid1.showPageNavigationFirst();
		grid1.showPageNavigationLast();
		grid1.hidePageNavigationHTML();
		grid1.setPageNavigationFontFace("Arial");
		grid1.setPageNavigationFontSize(2);
		grid1.setASCImageSource("../coreadmin/images/asc.gif");
		grid1.setDESCImageSource("../coreadmin/images/desc.gif");
		grid1.buildGrid(); // result set being processed, and cell values
							// are available

		if (grid1.isResultSetEmpty()) {
			form.addElement("<p id=\"record\">No Records Found");
		} else {
			grid1.countResultSet();
			form.addElement("<p>Total No. Of Interface: " + grid1.getRows());
			form.addElement(grid1.getGrid());
		}

		grid1.closeConnection();
	}

	private void createOperations(Html html, Form form, String formName) {
		String javaScript = "\n	function newManifest() {" + "\n	document." + formName + ".method=\"post\";" + "\n document." + formName
				+ ".action = \"coreadministrationv2.sysmgmt.ManifestManagement?operation=" + _NEW_OPERATION + "\";"
				+ "\n window.open(\"\",\"newManifest\",\"width=500,height=230,status=yes,scrollbars=no,resizable=no,toolbar=no,menubar=no\");"
				+ "\n document." + formName + ".target=\"newManifest\";\n document." + formName + ".submit(); \n }" + "\n";

		Input newManifestButton = new Input();
		newManifestButton.setOnClick("newManifest();");

		Table operationTable = new Table();
		operationTable.addElement(new Table()
				.setBorder(0)
				.setCellPadding(0)
				.setCellSpacing(0)
				.addElement(
						new TR().addElement(new TD().addElement(newManifestButton.setClassId("sbttn").setName("New").setTabindex(2)
								.setTitleValue("New").setType("button").setValue("New")))));

		Table table = new Table();
		table.addElement(new TR().addElement(new TD().addElement(operationTable)));
		form.addElement(table);

		html.addElement(new Script().setLanguage("JavaScript").addElement(javaScript));
	}

	private void createNewManifestOperations(Html html, Form form, String formName) {
		String javaScript =

		"function saveManifestDetails() { " + "\n	var type = \"\";" + "\n if (document.getElementsByName(\"" + _TYPE_SELECT_NAME
				+ "\")[0].checked) {" + "\n	type = document.getElementsByName(\"" + _TYPE_SELECT_NAME + "\")[0].value;"
				+ "\n } else if (document.getElementsByName(\"" + _TYPE_SELECT_NAME + "\")[1].checked) {" + "\n	type = document.getElementsByName(\""
				+ _TYPE_SELECT_NAME + "\")[1].value;" + "\n }" + "\n if (type != \"\") {" + "\n	var name = document.getElementsByName(\""
				+ _INTERFACE_INPUT_NAME + "\")[0].value.trim();" + "\n	if (name) {var title = document.getElementsByName(\"" + _INTERFACE_TITLE
				+ "\")[0].value.trim();" + "\n if (title) {" + "\n		var fileName = document.getElementsByName(\"" + _ZIP_INPUT_NAME
				+ "\")[0].value.trim();" + "\n		if (fileName) {" + "\n			document." + formName + ".method=\"post\";" + "\n document." + formName
				+ ".action = \"coreadministrationv2.sysmgmt.ManifestManagement?operation=" + _SAVE_OPERATION + "\";" + "\n document." + formName
				+ ".submit(); \n" + "\n		} else {" + "\n			alert(\"Please provide fileName name.\");" + "\n		}" + "\n		}" + "else {"
				+ "\n			alert(\"Please provide interface title name.\");" + "\n		}" + "\n	} else {"
				+ "\n		alert(\"Please provide interface name.\");" + "\n	}" + "\n" + "\n } else {" + "\n	alert(\"Please select a type.\");" + "\n }"
				+ "\n}";

		Input newManifestButton = new Input();
		newManifestButton.setOnClick("saveManifestDetails();");

		Input closeButton = new Input();
		closeButton.setOnClick("window.close();");

		Table operationTable = new Table();
		operationTable.addElement(new Table()
				.setBorder(0)
				.setCellPadding(0)
				.setCellSpacing(0)
				.addElement(
						new TR().addElement(
								new TD().addElement(newManifestButton.setClassId("sbttn").setName("Save").setTabindex(1).setTitleValue("Save")
										.setType("button").setValue("Save")))
								.addElement(new TD().setWidth(5))
								.addElement(
										new TD().addElement(closeButton.setClassId("sbttn").setName("Close").setTabindex(3).setTitleValue("Close")
												.setType("button").setValue("Close")))));

		Table table = new Table();
		table.addElement(new TR().addElement(new TD().addElement(operationTable)));
		form.addElement(table);

		html.addElement(new Script().setLanguage("JavaScript").addElement(javaScript));
	}
}
