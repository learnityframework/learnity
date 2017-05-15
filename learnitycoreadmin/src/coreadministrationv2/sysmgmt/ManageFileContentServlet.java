package coreadministrationv2.sysmgmt;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
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

import comv2.aunwesha.lfutil.FileUtil;
import comv2.aunwesha.lfutil.GenericUtil;
import comv2.aunwesha.lfutil.LfServlet;
import comv2.aunwesha.lfutil.Pair;

import coreadministrationv2.dbconnection.DataBaseLayer;
import coreadministrationv2.utility.TableExtension;

public class ManageFileContentServlet extends LfServlet {

	private static final long serialVersionUID = -2871943780085812505L;
	private static final String _SAVE_OPERATION = "save";
	private static final String _THEME_TYPE = "theme";
	private static final String _TEMPLATE_TYPE = "template";
	private static final String _TEMPLATE_RESOURCE_TYPE = "templateresource";
	private String resourceIdInput=null;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		super.doGet(request, response);
		String operation = request.getParameter("operation");

		this.resourceIdInput = request.getParameter("resource_id");
		String interfaceId = request.getParameter("interface_id");
		String templateId = request.getParameter("template_id");
		String type = request.getParameter("type1");
		String deliveryMode=request.getParameter("delivery_mode");
		String attachmentname=request.getParameter("asset_path");
		String fileName = request.getParameter("file_name");
		String statusMessage = "";
		if (_SAVE_OPERATION.equals(operation)) {
			statusMessage = saveFileContent(request, response, this.resourceIdInput, interfaceId,templateId,type);
		}

		generateMainPage(request, response, statusMessage, this.resourceIdInput, interfaceId,templateId,fileName, type,deliveryMode,attachmentname);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request, response);

	}

	private String saveFileContent(HttpServletRequest request, HttpServletResponse response, String resourceId, String interfaceId,String templateId,String type) {
		String contentData = request.getParameter("file_content");
		String defaultValue = request.getParameter("default_value");
		ResourceBundle rb = ResourceBundle.getBundle("portal", Locale.getDefault());
		String fileName = null;
		String filePath = null;
		String statusMessage = "";
		if (_THEME_TYPE.equalsIgnoreCase(type) || _TEMPLATE_TYPE.equalsIgnoreCase(type)) {

			if (_THEME_TYPE.equalsIgnoreCase(type)) {
				filePath = rb.getString("themesxml");
				fileName = "themeModifyScreen.xml";

			} else {
				filePath = rb.getString("templatexml");
				fileName = "templateModifyScreen.xml";

			}

		} else {
			Pair<String, String> typeFileName = DataBaseLayer.getType(resourceId, interfaceId);
			type = typeFileName.getFirst();
			fileName = typeFileName.getSecond();
			filePath = rb.getString("xml");
		}

		

		File ff = new File(filePath);
		ff.mkdirs();

		File file = new File(filePath.concat(fileName));
		if (file.exists()) {
			file.delete();
		}
		System.out.println(file.length());
		try {
			FileUtils.writeStringToFile(new File(filePath.concat(fileName)), contentData);

			Long size = new Long(file.length());

			System.out.println(file.length());

			String loggedInUserId = getLoggedInUserName(request);

			if (_THEME_TYPE.equalsIgnoreCase(type)) {

				statusMessage=ThemesManagement.uploadThemesXML(request, resourceId, filePath, fileName, size.toString(), defaultValue);

			} else if (_TEMPLATE_TYPE.equalsIgnoreCase(type)) {

				Pair<String,String> returnPair=ApplicationTemplateManagement.uploadTemplateXML(request, resourceId, templateId, filePath, fileName, size.toString(), defaultValue);
				statusMessage=returnPair.getFirst();
				this.resourceIdInput=returnPair.getSecond();

			} else {
				String[] frameworkElements = DataBaseLayer.getFrameworkDataForInterface(interfaceId);

				String inlinecss = frameworkElements[1];
				String inlinejs = frameworkElements[2];
				String imagepath = frameworkElements[3];

				if (type.equals(LayoutUploader.INTERFACE_XML)) {
					Pair<Boolean, String> returnStatus = LayoutUploader.uploadInterface(filePath, filePath, fileName, type, size.toString(), request,
							response, inlinecss, inlinejs, imagepath, loggedInUserId, false);

					if (returnStatus.getFirst()) {
						statusMessage = statusMessage + LayoutUploader.LINE_SEPERATOR + " Interface Uploaded Successfully";
					} else {
						statusMessage = statusMessage + LayoutUploader.LINE_SEPERATOR + returnStatus.getSecond();
					}
				}

				else if (type.equals(LayoutUploader.INTERFACE_FRAGMENT_XML)) {
					Pair<Boolean, String> returnStatus = LayoutUploader.uploadInterfaceFragment(filePath, filePath, fileName, type, size.toString(),
							request, response, inlinecss, inlinejs, imagepath, loggedInUserId, false);
					if (returnStatus.getFirst()) {
						statusMessage = statusMessage + LayoutUploader.LINE_SEPERATOR + " Interface Fragment Uploaded Successfully";
					} else {
						statusMessage = statusMessage + LayoutUploader.LINE_SEPERATOR + returnStatus.getSecond();
					}
				}

				else {
					String resource_id = request.getParameter("resource_id");
					Pair<Boolean, String> returnStatus = DataBaseLayer.insertresourceOnly(resource_id, filePath, fileName, interfaceId,
							loggedInUserId);
					if (returnStatus.getFirst()) {
						statusMessage = "Resource Inserted!";
					} else {
						statusMessage = "Failed to insert! Reason - " + returnStatus.getSecond();
					}
				}
			}

		} catch (IOException e) {
			statusMessage = "Failed to save!";
			e.printStackTrace();
		}

		System.out.println(contentData);
		return statusMessage;
	}

	private void generateMainPage(HttpServletRequest request, HttpServletResponse response, String statusMessage, String resourceId,
			String interfaceId,String templateId,String fileName, String type,String deliveryMode, String attachmentname ) throws IOException {
		String formName = "viewResourceForm";
		Pair<String, String> dateTime = this.retrieveDateTime();
		Html html = new Html().addElement(new Head().addElement(new Title("View Resource"))
				.addElement(new Link().setHref("../coreadmin/css/stylesheet.css").setRel("stylesheet"))
				.addElement(new Script().setLanguage("JavaScript").setSrc("../coreadmin/js/check.js"))
		/*
		 * .addElement(new
		 * Script().setLanguage("JavaScript").addElement(javaScript))
		 */);

		Form form = new Form().setName(formName).setMethod("post");
		Body body = new Body().addElement(new TableExtension().headerTable("<b>Administrator:</b> " + getLoggedInUserName(request),
				dateTime.getFirst(), dateTime.getSecond(), "<b>Portal Administration:</b> " + resourceId + " (" + interfaceId + ")"));

		String fileString = null;
		String defaultValue = null;
		
		InputStream is = null;
		if (_THEME_TYPE.equalsIgnoreCase(type)) {
			Pair<InputStream, String> retrievePair = DataBaseLayer.retrieveTheme(resourceId);
			is = retrievePair.getFirst();
			defaultValue = retrievePair.getSecond();

		} else if (_TEMPLATE_TYPE.equalsIgnoreCase(type)) {
			Pair<InputStream, String> retrievePair = DataBaseLayer.retrieveTemplate(templateId);
			is = retrievePair.getFirst();
			defaultValue = retrievePair.getSecond();
		}else if (_TEMPLATE_RESOURCE_TYPE.equalsIgnoreCase(type)) {
			//Pair<InputStream, String> retrievePair = DataBaseLayer.retrieveTemplateResource(templateId,fileName);
			
			
			 if(deliveryMode.equals("Dynamic")||deliveryMode.equals("Inline"))
			 {
			   is =DataBaseLayer.retrieveTemplateResource(templateId,fileName,attachmentname);
			 }
			 else{
				 ServletContext servletContext = request.getSession().getServletContext();
					String contextPath = servletContext.getRealPath(File.separator);
					System.out.println(contextPath);
					 String attachmentname1= removeChar(attachmentname);
					// String attachmentname1=attachmentname;
					// System.out.println(attachmentname1.replace("..", " ")); 
					String resourcePath = File.separator+contextPath+attachmentname1+fileName;
					File file= new File(resourcePath);
				    is = new FileInputStream(file);
			 }
			 
			 // Pair<InputStream, String> retrievePair = DataBaseLayer.retrieveTemplateResource(templateId,fileName);
			//defaultValue = retrievePair.getSecond();
		}
		
		
		
		
		
		
		else {
			is = DataBaseLayer.retrieveFile(interfaceId, resourceId);
		}

		if (is != null) {
			fileString = FileUtil.getStringFromInputStream(is);
			is.close();
		}

		if (GenericUtil.hasString(fileString)) {
			String htmlContent = escapeHtml4(fileString);
			form.addElement("<textarea rows=\"150\" cols=\"93\" name=\"file_content\">" + htmlContent + "</textarea>");
		}

		if (_THEME_TYPE.equalsIgnoreCase(type) || _TEMPLATE_TYPE.equalsIgnoreCase(type)) {
			form.addElement("<input type=\"hidden\" name=\"type1\" value=\"" + type + "\">");
			form.addElement("<input type=\"hidden\" name=\"default_value\" value=\"" + defaultValue + "\">");
		}

		form.addElement("<div id=\"status-message\" style=\"color:red;\">" + statusMessage + "</div>");

		form.addElement("<input type=\"hidden\" name=\"interface_id\" value=\"" + interfaceId + "\">");
		form.addElement("<input type=\"hidden\" name=\"resource_id\" value=\"" + resourceId + "\">");
		createManageFileContentOperations(html, form, formName);
		body.addElement(form);

		html.addElement(body.setClass("bodyadmin"));

		PrintWriter out = response.getWriter();
		out.print(html.toString());
	}
	
	public String removeChar(String attachmentname) 
    {
     if (attachmentname == null || attachmentname.length() == 0)
       {
         return attachmentname;
       }
      String str=attachmentname.replaceAll("[\\.]","");
      return str;
    }

	private void createManageFileContentOperations(Html html, Form form, String formName) {
		String javaScript =

		"\n	function saveFileContent() {" + "\n			document." + formName + ".method=\"post\";" + "\n			document." + formName + ".target=\"_self\";"
				+ "\n			document." + formName + ".action = \"manageFileContent?operation=" + _SAVE_OPERATION + "\";" + "\n			document." + formName
				+ ".submit();" + "\n	}" + "\n";

		Input saveFileContent = new Input();
		saveFileContent.setOnClick("saveFileContent();");

		Input closeButton = new Input();
		closeButton.setOnClick("window.close();");

		Table operationTable = new Table();
		operationTable.addElement(new Table()
				.setBorder(0)
				.setCellPadding(0)
				.setCellSpacing(0)
				.addElement(
						new TR().addElement(
								new TD().addElement(saveFileContent.setClassId("sbttn").setName("Save").setTabindex(1).setTitleValue("Save")
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
