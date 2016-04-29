package coreadministrationv2.sysmgmt;

import interfaceenginev2.display.ApplicationTemplateEngine;
import interfaceenginev2.display.DisplayEngine;
import interfaceenginev2.display.ThemeEngine;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ecs.html.Body;
import org.apache.ecs.html.Form;
import org.apache.ecs.html.Head;
import org.apache.ecs.html.Html;
import org.apache.ecs.html.Input;
import org.apache.ecs.html.Link;
import org.apache.ecs.html.Option;
import org.apache.ecs.html.Script;
import org.apache.ecs.html.Select;
import org.apache.ecs.html.TD;
import org.apache.ecs.html.TR;
import org.apache.ecs.html.Table;
import org.apache.ecs.html.Title;

import comv2.aunwesha.JSPGrid.JSPGridPro2;
import comv2.aunwesha.lfutil.GenericUtil;
import comv2.aunwesha.lfutil.LfServlet;
import comv2.aunwesha.lfutil.Pair;

import coreadministrationv2.dbconnection.DataBaseLayer;
import coreadministrationv2.utility.TableExtension;

/**
 * Title: LearnITy
 * 
 * Description: Copyright: Copyright (c) 2006 Company: Aunwesha
 * 
 * @version
 */

public class InterfaceTemplateThemeManagement extends LfServlet {

	private static final long serialVersionUID = -7258717012840654659L;
	private static final String REFRESH_ALL_TYPE = "REFRESH_ALL";
	private static final String _APPLICATION_TEMPLATE_THEME_CHANGE_OPERATION = "APPLICATION_TEMPLATE_THEME_CHANGE";
	private static final String _APPLICATION_TEMPLATE_THEME_CHANGE_SAVE_OPERATION = "APPLICATION_TEMPLATE_THEME_CHANGE_SAVE";
	private static final String REFRESH = "REFRESH";
	private static final String _APLICATION_TEMPLATE_SELECT_NAME = "aplication_template_select";
	private static final String _THEME_SELECT_NAME = "theme_select";

	// private static final SimpleLogger LOG = new
	// SimpleLogger(ManifestManagement.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		super.doGet(request, response);

		String operation = request.getParameter("operation");
		if (REFRESH_ALL_TYPE.equals(operation)) {

			String statusMessage = InterfaceManagement.updateInterface(null, REFRESH_ALL_TYPE, null, null, null, request, response,
					getLoggedInUserName(request));
			generateMainPage(request, response, statusMessage);

		} else if (REFRESH.equals(operation)) {

			String interfaceId = request.getParameter("interface_id");
			String[] frameworkElements = DataBaseLayer.getFrameworkDataForInterface(interfaceId);

			String inlinecss = frameworkElements[1];
			String inlinejs = frameworkElements[2];
			String imagepath = frameworkElements[3];
			String type = frameworkElements[4];

			String statusMessage = InterfaceManagement.updateInterface(interfaceId, type, inlinecss, inlinejs, imagepath, request, response,
					getLoggedInUserName(request));
			generateMainPage(request, response, statusMessage);

		} else if (_APPLICATION_TEMPLATE_THEME_CHANGE_OPERATION.equals(operation)) {
			String interfaceId = request.getParameter("interface_id");
			generateNewPopUpPage(request, response, "", interfaceId);
		} else if (_APPLICATION_TEMPLATE_THEME_CHANGE_SAVE_OPERATION.equals(operation)) {
			String interfaceId = request.getParameter("interface_id");
			String statusMessage=updateTemplateTheme(interfaceId,request);
			generateNewPopUpPage(request, response, statusMessage, interfaceId);
		} else {
			generateMainPage(request, response, "");
		}
		// String operation = request.getParameter("operation");

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request, response);

	}

	private void generateMainPage(HttpServletRequest request, HttpServletResponse response, String statusMessage) throws IOException {
		String formName = "interfaceTemplateThemeManagementForm";
		Pair<String, String> dateTime = this.retrieveDateTime();
		Html html = new Html().addElement(new Head().addElement(new Title("Interface Template Theme Management"))
				.addElement(new Link().setHref("../coreadmin/css/stylesheet.css").setRel("stylesheet"))
				.addElement(new Script().setLanguage("JavaScript").setSrc("../coreadmin/js/check.js"))
		/*
		 * .addElement(new
		 * Script().setLanguage("JavaScript").addElement(javaScript))
		 */);

		Form form = new Form().setName(formName).setMethod("post");
		Body body = new Body().addElement(new TableExtension().headerTable("<b>Administrator:</b> " + getLoggedInUserName(request),
				dateTime.getFirst(), dateTime.getSecond(), "<b>Portal Administration:</b> Interface Template Theme Management"));
		try {
			generateInterfaceStatusTable(request, formName, form);
		} catch (Exception e) {
			e.printStackTrace();
		}
		createOperations(html, form, formName);
		form.addElement("<input type=\"hidden\" name=\"interface_id\">");
		form.addElement("<div id=\"status-message\" style=\"color:red;height: 300px;overflow: auto\">" + statusMessage + "</div>");

		body.addElement(form);
		html.addElement(body.setClass("bodyadmin"));

		PrintWriter out = response.getWriter();
		out.print(html.toString());
	}

	private void generateNewPopUpPage(HttpServletRequest request, HttpServletResponse response, String statusMessage, String selectedInterfaceId)
			throws IOException {
		String formName = "newInterfaceTemplateThemeManagementForm";
		Pair<String, String> dateTime = this.retrieveDateTime();

		Html html = new Html().addElement(new Head().addElement(new Title("Interface Management"))
				.addElement(new Link().setHref("../coreadmin/css/stylesheet.css").setRel("stylesheet"))
				.addElement(new Script().setLanguage("JavaScript").setSrc("../coreadmin/js/check.js"))
		/*
		 * .addElement(new
		 * Script().setLanguage("JavaScript").addElement(javaScript))
		 */);

		Form form = new Form().setName(formName).setMethod("post");
		Body body = new Body();
		body.addAttribute("onload", "opener.location.reload();");
		body.addElement(new TableExtension().headerTable("<b>Administrator:</b> " + getLoggedInUserName(request), dateTime.getFirst(),
				dateTime.getSecond(), "<b>Portal Administration:</b> New Manifest Add"));

		Table newInterfaceTemplateThemeTable = new Table().setCellPadding(2).setCellSpacing(1).setBorder(1);

		// newManifestTable.addElement(new TR().addElement(new
		// TD(" ").setColSpan(3).setClass("swb")));

		{
			TR interfaceIDRow = new TR();

			TD interfaceIDColumn = new TD();
			interfaceIDColumn = interfaceIDColumn.addElement("Interface ID");
			TD interfaceIDDisplayIdColumn = new TD().setColSpan(2);

			if (GenericUtil.hasString(selectedInterfaceId)) {
				interfaceIDDisplayIdColumn = interfaceIDDisplayIdColumn.addElement(selectedInterfaceId);
			} else {

			}
			interfaceIDRow.addElement(interfaceIDColumn).addElement(interfaceIDDisplayIdColumn);
			newInterfaceTemplateThemeTable = newInterfaceTemplateThemeTable.addElement(interfaceIDRow);
		}

		Pair<String, String> templateIdCommentPair = ApplicationTemplateEngine.retrieveTemplateIdAndComment(selectedInterfaceId);
		String selectedTemplete = null;
		if (templateIdCommentPair != null && GenericUtil.hasString(templateIdCommentPair.getFirst())) {
			selectedTemplete = templateIdCommentPair.getFirst();
		}

		{

			Option[] aplicationTemplateArray = null;
			List<Pair<Integer, String>> applicationTemplateList = DataBaseLayer.retrieveApplicationTemplates();
			if (GenericUtil.isListNullOrEmpty(applicationTemplateList)) {
				aplicationTemplateArray = new Option[1];
				aplicationTemplateArray[0] = new Option("-1").addElement("[Choose One]");
			} else {

				aplicationTemplateArray = new Option[applicationTemplateList.size() + 1];
				aplicationTemplateArray[0] = new Option("-1").addElement("[Choose One]");
				int aplicationTemplateElementItr = 1;
				for (Pair<Integer, String> applicationTemplate : applicationTemplateList) {
					Integer applicationTemplateID = applicationTemplate.getFirst();
					String applicationTemplateName = applicationTemplate.getSecond();
					Option currentOption = new Option(applicationTemplateID.toString()).addElement(applicationTemplateName);
					if (applicationTemplateID.toString().equalsIgnoreCase(selectedTemplete)) {
						currentOption.setSelected(true);
					}
					aplicationTemplateArray[aplicationTemplateElementItr] = currentOption;
					aplicationTemplateElementItr++;
				}
			}

			TR aplicationTemplateSelectRow = new TR();

			TD aplicationTemplateNameColumn = new TD();
			aplicationTemplateNameColumn = aplicationTemplateNameColumn.addElement("Application Template");

			TD aplicationTemplateSelectColumn = new TD().setColSpan(2);
			Select aplicationTemplateSelect = new Select().setName(_APLICATION_TEMPLATE_SELECT_NAME).addElement(aplicationTemplateArray);

			aplicationTemplateSelectColumn = aplicationTemplateSelectColumn.addElement(aplicationTemplateSelect);

			aplicationTemplateSelectRow.addElement(aplicationTemplateNameColumn).addElement(aplicationTemplateSelectColumn);

			newInterfaceTemplateThemeTable = newInterfaceTemplateThemeTable.addElement(aplicationTemplateSelectRow);
		}

		{
			Pair<String, String> themeIdCommentPair = ThemeEngine.retrieveThemeIdAndCommnts(selectedInterfaceId, selectedTemplete);

			Option[] themeArray = null;
			List<String> themeList = DataBaseLayer.retrieveThemes();
			if (GenericUtil.isListNullOrEmpty(themeList)) {
				themeArray = new Option[1];
				themeArray[0] = new Option("-1").addElement("[Choose One]");
			} else {
				String selectedTheme = null;
				if (themeIdCommentPair != null && GenericUtil.hasString(themeIdCommentPair.getFirst())) {
					selectedTheme = themeIdCommentPair.getFirst();
				}
				themeArray = new Option[themeList.size() + 1];
				themeArray[0] = new Option("-1").addElement("[Choose One]");
				int themeElementItr = 1;
				for (String theme : themeList) {
					Option currentOption = new Option(theme).addElement(theme);
					if (theme.toString().equalsIgnoreCase(selectedTheme)) {
						currentOption.setSelected(true);
					}
					themeArray[themeElementItr] = currentOption;
					themeElementItr++;
				}
			}

			TR themeSelectRow = new TR();

			TD themeNameColumn = new TD();
			themeNameColumn = themeNameColumn.addElement("Theme");

			TD themeSelectColumn = new TD().setColSpan(2);
			Select themeSelect = new Select().setName(_THEME_SELECT_NAME).addElement(themeArray);

			themeSelectColumn = themeSelectColumn.addElement(themeSelect);

			themeSelectRow.addElement(themeNameColumn).addElement(themeSelectColumn);

			newInterfaceTemplateThemeTable = newInterfaceTemplateThemeTable.addElement(themeSelectRow);
		}

		form.addElement("<input type=\"hidden\" name=\"interface_id\" value=\"" + selectedInterfaceId + "\">");
		form.addElement(newInterfaceTemplateThemeTable);

		createChangeOperations(html, form, formName);
		form.addElement("<div id=\"status-message\" style=\"color:red;\">" + statusMessage + "</div>");
		body.addElement(form);
		html.addElement(body.setClass("bodyadmin"));

		PrintWriter out = response.getWriter();
		out.print(html.toString());
	}

	private void generateInterfaceStatusTable(HttpServletRequest request, String formName, Form form) throws Exception {

		// String manifestId = ManifestDao.getManifestId();
		/*
		 * String manifestQuerySql =
		 * "select f.framework_file_id as \"Interface Title\",f.filename as \"File Name\",f.type as \"File Type\" from"
		 * +
		 * " manifestinterfaceassociation m,framework_file f where m.interface_id=f.framework_file_id"
		 * ;
		 */

		String interfaceStatusQuery = "select interface_id as \"Select\",interface_id as \"Interface\",last_updated as \"Last Updated\","
				+
				// "type as \"Type\",filesize as \"File Size\",inlinecss as \"Inline CSS\",inlinejs as \"Inline JS\",imagepath as \"Image Path\","+
				"application_template as \"Application Template\",theme_id as \"Theme\",if(theme_uploaded is null,'Not Present',theme_uploaded) as \"Theme Upload Time\","
				+ "if(application_template_uploaded is null,'Not Present',application_template_uploaded) as \"Application Template Upload Time\","
				+ "if(theme_uploaded>=last_updated OR application_template_uploaded>=last_updated,'Y','') as \"Refresh Required\""
				+ "from(select interface_id,last_updated,type,filesize,inlinecss,inlinejs,imagepath,application_template, theme_id,(SELECT upload_on "
				+ "FROM `themes` t WHERE t.themes_id=theme_id) theme_uploaded,(SELECT upload_on FROM `application_template_master` am "
				+ "where am.application_template_title=application_template) application_template_uploaded from "
				+ "(select interface_id,last_updated,type,filesize,inlinecss,inlinejs,imagepath,application_template,if(theme_id is null or theme_id = '',"
				+ "(select t.themes_id from themes t where t.default_value='yes' ),theme_id) theme_id from "
				+ "(select interface_id,last_updated,type,filesize,inlinecss,inlinejs,imagepath,application_template,if(themes_id is null or themes_id='' ,"
				+ "(SELECT d.default_value FROM application_template_master a , application_template_default d where a.application_template_id=d.application_template_id "
				+ "and a.application_template_title=application_template and d.attribute_name='ThemeID'),themes_id) theme_id from "
				+ "(SELECT i.interface_id,f.last_updated,f.type,f.filesize,f.inlinecss,f.inlinejs,f.imagepath,IF(c.template is null OR c.template='',"
				+ "(select a.application_template_title from application_template_master a where a.default_value='yes' ),c.template) application_template,c.themes_id "
				+ "FROM interface i LEFT OUTER JOIN configuration_item c ON i.interface_id=c.interface_id,framework_file f "
				+ "where f.framework_file_id=i.interface_id) temp) temp1) temp2)temp3 where type in ('"+LayoutUploader.INTERFACE_TYPE+"','"+LayoutUploader.INTERFACE_FRAGMENT_TYPE+"')";

		JSPGridPro2 grid1 = new JSPGridPro2(request, formName);
		grid1.setConnectionParameters(interfaceStatusQuery);

		grid1.setWidth("100%");
		grid1.setCellPadding(2);
		grid1.setCellSpacing(1);
		grid1.setFontFace("Arial");
		grid1.setFontSize(2);
		grid1.setEvenRowBgColor("#C0C0C0");
		grid1.setOddRowBgColor("#F0F0F0");
		grid1.setCaption("");
		grid1.setMaxRowsPerPage(5); // how many records displayed per page
		grid1.setMaxResultPagesPerLoad(5); // Page : 1 2 3 4 5 6 7 8 9 10 (max
											// 10 pages displayed)
		grid1.setLineNoHeaderBgColor("#48E6F7");

		grid1.Cols(0).setFieldType(JSPGridPro2.FIELD_RADIO);
		grid1.Cols(1).setFieldType(JSPGridPro2.FIELD_HIDDEN);
		grid1.Cols(2).setFieldType(JSPGridPro2.FIELD_HIDDEN);
		grid1.Cols(3).setFieldType(JSPGridPro2.FIELD_HIDDEN);
		grid1.Cols(4).setFieldType(JSPGridPro2.FIELD_HIDDEN);
		grid1.Cols(5).setFieldType(JSPGridPro2.FIELD_HIDDEN);
		grid1.Cols(6).setFieldType(JSPGridPro2.FIELD_HIDDEN);
		grid1.Cols(7).setFieldType(JSPGridPro2.FIELD_HIDDEN);

		grid1.Cols(0).setFieldName("checkbox");
		grid1.Cols(1).setFieldName("hiddenname");
		grid1.Cols(2).setFieldName("uploadon");
		grid1.Cols(3).setFieldName("applicationtemplate");
		grid1.Cols(4).setFieldName("theme");
		grid1.Cols(5).setFieldName("applicationtemplateupload");
		grid1.Cols(6).setFieldName("themeupload");
		grid1.Cols(7).setFieldName("refreshrequired");

		grid1.Cols(0).Header().setClassID("swb");
		grid1.Cols(1).Header().setClassID("swb");
		grid1.Cols(2).Header().setClassID("swb");
		grid1.Cols(3).Header().setClassID("swb");
		grid1.Cols(4).Header().setClassID("swb");
		grid1.Cols(5).Header().setClassID("swb");
		grid1.Cols(6).Header().setClassID("swb");
		grid1.Cols(7).Header().setClassID("swb");

		grid1.Cols(0).insertFieldScript("onclick=\"checkbox_onclick();\"");
		grid1.setEachRowHeight("20");
		grid1.canSort(0, false);
		grid1.canSort(1, true);
		grid1.canSort(2, true);
		grid1.canSort(3, true);
		grid1.canSort(4, true);
		grid1.canSort(5, true);
		grid1.canSort(6, true);
		grid1.canSort(7, true);

		grid1.setSortableColumnsToolTip("Click to Sort");
		grid1.showPageNavigationFirst();
		grid1.showPageNavigationLast();
		grid1.hidePageNavigationHTML();
		grid1.setPageNavigationFontFace("Arial");
		grid1.setPageNavigationFontSize(2);
		grid1.setASCImageSource("../coreadmin/images/asc.gif");
		grid1.setDESCImageSource("../coreadmin/images/desc.gif");
		grid1.buildGrid(); // result set being processed, and cell values are
							// available

		grid1.closeConnection();

		if (grid1.isResultSetEmpty()) {
			form.addElement("<p id=\"record\">No Records Found");
		} else {
			List<String> types=new ArrayList<>();
			types.add(LayoutUploader.INTERFACE_TYPE);
			types.add(LayoutUploader.INTERFACE_FRAGMENT_TYPE);
			List<Pair<Integer, String>> itemCounts = DataBaseLayer.retrieveDifferentItemsCount(types);
			grid1.countResultSet();
			form.addElement("<p><b>Total No. Of Items:</b> " + grid1.getRows());
			if (GenericUtil.hasListData(itemCounts)) {
				for (Pair<Integer, String> itemCount : itemCounts) {
					// form.addElement("<br/>Total No. of "+itemCount.getSecond()+": "+itemCount.getFirst());
					form.addElement("<span>&nbsp;&nbsp;&nbsp;<b>Total No. of " + itemCount.getSecond() + ":</b> " + itemCount.getFirst() + "</span>");
				}
			}
			form.addElement("</p>");
			form.addElement(grid1.getGrid());
		}

	}

	private void createOperations(Html html, Form form, String formName) {
		String javaScript = "\n	function refreshAll_onclick() {"
				+ "\n			doyou = confirm(\"Are you Sure to refresh all interface and interface fragements?\"); " + "\n			if (doyou == true) {"
				+ "\n				document."
				+ formName
				+ ".method=\"post\";"
				+ "\n				document."
				+ formName
				+ ".action = \"coreadministrationv2.sysmgmt.InterfaceTemplateThemeManagement?operation="
				+ REFRESH_ALL_TYPE
				+ "&type="
				+ REFRESH_ALL_TYPE
				+ "\""
				+ "\n             document."
				+ formName
				+ ".target=\"bodyFrame\";"
				+ "\n				document."
				+ formName
				+ ".submit();"
				+ "\n			}"
				+ "\n	}"
				+ "\n	function refresh_onclick() {"
				+
				// "\n			alert(document."+formName+".interface_id.value);"+
				"\n                    if(document."
				+ formName
				+ ".interface_id.value==''){"
				+ "\n                    	alert('Please select one item');return false;}"
				+ "\n                   else{"
				+ "\n				doyou = confirm(\"Are you Sure to refresh selected item ?\"); //Your question."
				+ "\n				if (doyou == true) {"
				+ "\n				document."
				+ formName
				+ ".method=\"post\";"
				+ "\n             document."
				+ formName
				+ ".target=\"bodyFrame\";"
				+ "\n				document."
				+ formName
				+ ".action = \"coreadministrationv2.sysmgmt.InterfaceTemplateThemeManagement?operation="
				+ REFRESH
				+ "&interface_id=\"+document."
				+ formName
				+ ".interface_id.value"
				+ "\n				document."
				+ formName
				+ ".submit();"
				+ "\n				}"
				+ "\n                   else{"
				+

				"\n				}"
				+ "\n				}"
				+ "\n	}"
				+ "\n	function checkbox_onclick() {"
				+ "\n for (var i=0;i<document."
				+ formName
				+ ".elements.length;i++){"
				+ "\n	var e = document."
				+ formName
				+ ".elements[i];"
				+ "\n	if (e.type=='radio'){"
				+ "\n		if (e.checked)"
				+ "\n			hL(e);"
				+ "\n		else"
				+ "\n			dL(e);"
				+ "\n	 }"
				+ "\n }"
				+ "\n		var i = test();"
				+ "\n		if(i>1) {"
				+ "\n			for(var counter=0; counter<document."
				+ formName
				+ ".checkbox.length; counter++) {"
				+ "\n				if(document."
				+ formName
				+ ".checkbox[counter].checked) {"
				+ "\n				document."
				+ formName
				+ ".interface_id.value = document."
				+ formName
				+ ".checkbox[counter].value;"
				+ "\n					break;"
				+ "\n				}"
				+ "\n			}"
				+ "\n		}"
				+ "\n		if(i==1) {"
				+ "\n			document."
				+ formName
				+ ".interface_id.value = document."
				+ formName
				+ ".checkbox.value;"
				+ "\n		}"
				+ "\n	}"
				+ "\n	function test() {"
				+ "\n		var index = 0;"
				+ "\n		for (var i=0;i<document."
				+ formName
				+ ".elements.length;i++){"
				+ "\n			var e = document."
				+ formName
				+ ".elements[i];"
				+ "\n			if (e.type=='radio'){"
				+ "\n				index++;"
				+ "\n			}"
				+ "\n		}"
				+ "\n		return index;"
				+ "\n	}"
				+ "\n	function applicationTemplateThemeChangeProcess() {"
				+ "\n	document."
				+ formName
				+ ".method=\"post\";"
				+ "\n	document."
				+ formName
				+ ".action = \"coreadministrationv2.sysmgmt.InterfaceTemplateThemeManagement?operation="
				+ _APPLICATION_TEMPLATE_THEME_CHANGE_OPERATION
				+ "&interface_id=\"+document."
				+ formName
				+ ".interface_id.value;"
				+ "\n window.open(\"\",\"application_template_theme_change\",\"width=500,height=230,status=yes,scrollbars=no,resizable=no,toolbar=no,menubar=no\");"
				+ "\n document." + formName + ".target=\"application_template_theme_change\";\n document." + formName + ".submit(); \n }" + "\n";

		Input refreshButton = new Input();
		Input refreshAllButton = new Input();
		Input applicationTemplateThemeChangeButton = new Input();

		refreshButton.setOnClick("refresh_onclick();");
		refreshAllButton.setOnClick("refreshAll_onclick();");
		applicationTemplateThemeChangeButton.setOnClick("applicationTemplateThemeChangeProcess();");

		Table operationTable = new Table();
		operationTable.addElement(new Table()
				.setBorder(0)
				.setCellPadding(0)
				.setCellSpacing(0)
				.addElement(
						new TR().addElement(new TD().setWidth(5))
								.addElement(
										new TD().addElement(refreshButton.setClassId("sbttn").setName("refresh").setTabindex(2)
												.setTitleValue("Refresh").setType("button").setValue("Refresh")))
								.addElement(new TD().setWidth(5))
								.addElement(
										new TD().addElement(refreshAllButton.setClassId("sbttn").setName("refreshAll").setTabindex(2)
												.setTitleValue("Refresh All").setType("button").setValue("Refresh All")))
								.addElement(new TD().setWidth(5))
								.addElement(
										new TD().addElement(applicationTemplateThemeChangeButton.setClassId("sbttn")
												.setName("applicationTemplateThemeChange").setTabindex(2)
												.setTitleValue("Application Template Theme Change").setType("button")
												.setValue("Application Template Theme Change")))));

		Table table = new Table();
		table.addElement(new TR().addElement(new TD().addElement(operationTable)));
		form.addElement(table);

		html.addElement(new Script().setLanguage("JavaScript").addElement(javaScript));
	}

	private void createChangeOperations(Html html, Form form, String formName) {
		String javaScript =

		"\n	function saveChangeApplicationTemplateTheme() {" + "\n	document." + formName + ".method=\"post\";" + "\n	document." + formName
				+ ".action = \"coreadministrationv2.sysmgmt.InterfaceTemplateThemeManagement?operation="
				+ _APPLICATION_TEMPLATE_THEME_CHANGE_SAVE_OPERATION + "&interface_id=\"+document." + formName + ".interface_id.value;"
				+ "\n document." + formName + ".target=\"application_template_theme_change\";\n document." + formName + ".submit(); \n }" + "\n";

		Input saveApplicationTemplateThemeButton = new Input();
		saveApplicationTemplateThemeButton.setOnClick("saveChangeApplicationTemplateTheme();");

		Input closeButton = new Input();
		closeButton.setOnClick("window.close();");

		Table operationTable = new Table();
		operationTable.addElement(new Table()
				.setBorder(0)
				.setCellPadding(0)
				.setCellSpacing(0)
				.addElement(
						new TR().addElement(
								new TD().addElement(saveApplicationTemplateThemeButton.setClassId("sbttn").setName("Save").setTabindex(1)
										.setTitleValue("Save").setType("button").setValue("Save")))
								.addElement(new TD().setWidth(5))
								.addElement(
										new TD().addElement(closeButton.setClassId("sbttn").setName("Close").setTabindex(3).setTitleValue("Close")
												.setType("button").setValue("Close")))));

		Table table = new Table();
		table.addElement(new TR().addElement(new TD().addElement(operationTable)));
		form.addElement(table);

		html.addElement(new Script().setLanguage("JavaScript").addElement(javaScript));
	}

	private String updateTemplateTheme(String interface_id,HttpServletRequest request) {
		String statusMessage = null;
		String themeID = request.getParameter(_THEME_SELECT_NAME);
		String templateID = request.getParameter(_APLICATION_TEMPLATE_SELECT_NAME);
		boolean isSuccess=DataBaseLayer.updateTemplateTheme(templateID, themeID, interface_id);
		if(isSuccess){
			
			Vector<String> toltallayout = DataBaseLayer.getLayout_ID(interface_id);

			for (int aq = 0; aq < toltallayout.size(); aq = aq + 4) {
				String htmlString = "";
				String layout_id = toltallayout.elementAt(aq);
				String content_id = toltallayout.elementAt(aq + 1);
				String behaviour_id = toltallayout.elementAt(aq + 2);
				String style_id = toltallayout.elementAt(aq + 3);
				DisplayEngine de = new DisplayEngine();
				de.createStructure(interface_id, layout_id, content_id, behaviour_id, style_id);
				try {

					htmlString = de.show();

				} catch (Exception e1) {
					e1.printStackTrace();
					statusMessage = "Failed to update";
				}
				// DataBaseLayer.insetHTML(interface_id,layout_id,content_id,behaviour_id,style_id,htmlgeneratepath+"inreface.html");
				DataBaseLayer.insetHTML(interface_id, layout_id, content_id, behaviour_id, style_id, htmlString);

			}
			if (GenericUtil.isEmptyString(statusMessage)) {
				statusMessage = "Successfully Updated";
			}
		}else{
			statusMessage = "Failed to update";
		}
		return statusMessage;
	}

}
