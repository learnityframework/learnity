package coreadministrationv2.sysmgmt;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import comv2.aunwesha.JSPGrid.JSPGridPro2;
import comv2.aunwesha.lfutil.GenericUtil;
import comv2.aunwesha.lfutil.Pair;

import org.apache.ecs.html.BR;
import org.apache.ecs.html.Body;
import org.apache.ecs.html.Font;
import org.apache.ecs.html.Form;
import org.apache.ecs.html.Head;
import org.apache.ecs.html.Html;
import org.apache.ecs.html.IMG;
import org.apache.ecs.html.Input;
import org.apache.ecs.html.Link;
import org.apache.ecs.html.Option;
import org.apache.ecs.html.Script;
import org.apache.ecs.html.TBody;
import org.apache.ecs.html.TD;
import org.apache.ecs.html.TR;
import org.apache.ecs.html.Table;
import org.apache.ecs.html.Title;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.oreilly.servlet.MultipartRequest;

import coreadministrationv2.dbconnection.DataBaseLayer;
import coreadministrationv2.sysmgmt.LayoutUploader;
import coreadministrationv2.sysmgmt.xml.util.SchemaValidatation;
import coreadministrationv2.utility.TRExtension;

/**
 * Servlet implementation class ResourceApplicationTemplate
 */
@WebServlet("/ResourceApplicationTemplate")
public class ResourceApplicationTemplate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String _DEFAULT_VALUE_YES = "yes";
	private static final String LOGIN_SESSION_NAME = "ADMIN_LOG_ON";
	
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	String s7="";
	String s6="";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String statusMessage="";
        response.setContentType("text/html");
        response.setHeader("Pragma", "no-cache");
	     response.setHeader("Cache-Control", "no-cache");
	     response.setDateHeader("Expires", 0);
        PrintWriter out = response.getWriter();
        HttpSession mysession=request.getSession(true);
		  Object obj = mysession.getAttribute(LOGIN_SESSION_NAME);
		  String type1 = request.getParameter("type1");
			if(type1==null)
				type1="";
		  if (obj ==null)
			response.sendRedirect("../coreadmin/login.html");
      else {
    	  String strAdminId = "superadmin";
      	String strPrmAddModDel = request.getParameter("prmAddModify");
     	String invalid = request.getParameter("invalid");
     //	String fileName = request.getParameter("file_name");
		String loggedInUserId = mysession.getAttribute(LOGIN_SESSION_NAME).toString();
		//  String template_id = request.getParameter("template_id");
      	//String strAdministratorPreviledge = coreadministration.dbconnection.DataBaseLayer.getAdminstratorPreviledge(strAdminId);
      	//if (strAdministratorPreviledge != null) {
	        	if (strPrmAddModDel!=null) {
		        	int iPrmAddModify = Integer.parseInt(strPrmAddModDel);
			        switch(iPrmAddModify) {

			        	case 0:
				        	
			        		statusMessage=add(request, strAdminId, out, response, type1);
				        	        break;
			        	case 1:
			        		
			        	//	statusMessage=modify(request, strAdminId,out);
			        		        break;
			        	case 2:
			        		
			            	statusMessage=deletelayout(request,strAdminId, out);
			        		        break;
			        		
			        	
					}
	        	}
	        	
      

	        	Calendar calendar = new GregorianCalendar();
				Date trialTime = new Date();
				calendar.setTime(trialTime);

			//create array of string to hold days.
				String months[]={"January","February","March", "April", "May","June",
					"July","August","September","October","November","December"};

					String strTime = calendar.get(Calendar.HOUR)+":"+ calendar.get(Calendar.MINUTE);
					String strDate = months[calendar.get(Calendar.MONTH)]+" "+ calendar.get(Calendar.DATE)+", "+ calendar.get(Calendar.YEAR);

	
	                 String template_id = request.getParameter("template_id");
	         
	 
	               Input inputButton1 = new Input();
		           Input inputButton2 = new Input();
		           Input viewButton = new Input();
		           Input closeButton = new Input();
		           
		           inputButton1.setOnClick("uploadLayout_onclick();");
		 		  inputButton2.setOnClick("download_onclick();");
		 		 viewButton.setOnClick("viewLayout_onclick();");
		 		 closeButton.setOnClick("window.close();");
		 		  
		 		 Option[] option1 = null;
		 		//Option[] option1;
		 		 option1= new Option[3];
		 	//	option1[0] = new Option("0").addElement("[All]");
		 		option1[0] = new Option("0").addElement("Select");
		 		option1[1] = new Option("Resources").addElement("Resources");
		 		if(type1.equals("Resources"))
		 		
		 				option1[1].setSelected(true);
		 		
		 		option1[2] = new Option("Templatexml").addElement("Templatexml");
		 		if(type1.equals("Templatexml"))
		 			
		 		        option1[2].setSelected(true);
		 		
		 	//String s=option1[1].getLabel();
			/*		Vector<Vector<String>> gettype = DataBaseLayer.getResourceType();
					if(gettype==null) 
					{
						option1 = new Option[1];
						option1[0] = new Option("0").addElement("[All]");
					}
					else 
					{
						option1= new Option[gettype.size()+1];
						option1[0] = new Option("0").addElement("[All]");
						for(int i=0; i<gettype.size(); i++) 
						{
							Vector<String> gettypesub = gettype.elementAt(i);
							String  type_id =  gettypesub.elementAt(0);
							String type_name =  gettypesub.elementAt(1);
							option1[i+1] = new Option(type_id).addElement(type_name);
							if(type_id.equals(type1))
							{
								option1[i + 1].setSelected(true);
							}
						}
					}	
	       */
	       
	       Html html = new Html()
					.addElement(new Head()
					.addElement(new Title("Resource Application Management"))
					.addElement(new Link()
					.setHref("../coreadmin/css/stylesheet.css")
					.setRel("stylesheet"))
					.addElement(new Script()
					.setLanguage("JavaScript")
					.setSrc("../coreadmin/js/check.js"))
					.addElement(new Script()
					.setLanguage("JavaScript")
					.setSrc("../coreadmin/js/coreadministrationv2.sysmgmt/ResourceApplicationTemplate.js")));
				//	.addElement(javaScript)));
				
			Form form = new Form().setName("frm").setMethod("post");
			Body body=new Body().addElement(new TRExtension()
					.headerTable("<b>Administrator:</b> "+strAdminId, strDate, strTime, "Resource Application Template of <b>'"+template_id+"'</b>"));
				           		
			//String invalid = request.getParameter("invalid");
			if(invalid != null && invalid.equals("1"))
			{
				body.addElement(new BR()).addElement(new BR()).addElement(new Font().setColor("#990000").addElement(" * Image Exists ")).addElement(new BR());
			} else

				if(invalid != null && invalid.equals("2"))
				{
					body.addElement(new BR()).addElement(new BR()).addElement(new Font().setColor("#990000").addElement(" * No Image Exists ")).addElement(new BR());
				}
				Input styleid= new Input().setType("file").setName("filename").setClassId("PPRLabelText");



				form.addElement(new Table()
												  .setBorder(0)
												  .setCellPadding(0)
												  .setCellSpacing(0)
												  .setWidth("100%")
												  .addElement(new TR()
												  .addElement(new TD()
												  .addElement(new Table()
												  .setBorder(0)
												  .setCellPadding(0)
												  .setCellSpacing(0)
												  .setWidth("100%")
												  .addElement(new TBody()
												  .addElement(new TR()
												  .addElement(new TD()
												  .addElement(new IMG()
												  .setBorder(0)
												  .setHeight(8)
												  .setWidth(10)
												  .setSrc("../coreadmin/images/T.gif")))
																									
																 )))))
															
												  .addElement(new TR()	
												  .addElement(new TD()
												  .addElement(new Table()
												  .setBorder(0)
												  .setCellPadding(0)
												  .setCellSpacing(0)
												  .addElement(new TBody()
												  .addElement(new TRExtension()
												  .add())
												  .addElement(new TRExtension()
												  .addSelect("Select Type", "type1", "1", option1,"type_onchange()")))))));
										
				Table table=new Table();
				Table table2=new Table();
				table2.addElement(new Table(1)
						.setWidth("100%")
						.setBorder(0)
						.setCellPadding(0)
						.setCellSpacing(0)
			
						.addElement(new TR()
						.addElement(new TD()
						.setClassId("PPRLabelText")
						.addElement("File ")
									  )
						.addElement(new TD()					
						.setClassId("PPRLabelText")
						.addElement(styleid))
									  )
						.addElement(new TR()
						.addElement(new TD()
						.setWidth(47)
									  ))
				
									  );
					 
			

			
			
				Table table1=new Table(); 
				table1.addElement(new Table()
						.setBorder(0)
						.setCellPadding(0)
						.setCellSpacing(0)
						.addElement(new TR()
						.addElement(new TD()
						.addElement(inputButton1
						.setClassId("sbttn")
						.setName("addGrop")
						.setTabindex(2)
						.setTitleValue("Upload")
						.setType("button")
						.setValue("Upload")))
						.addElement(new TD()
						.setWidth(5))
				
						.addElement(new TD()
						.addElement(inputButton2
						.setClassId("sbttn")
						.setName("addGrop")
						.setTabindex(2)
						.setTitleValue("Download")
						.setType("button")
						.setValue("Download")))
						
						
						
						.addElement(new TD()
						.setWidth(5))
				
						.addElement(new TD()
						.addElement(viewButton
						.setClassId("sbttn")
						.setName("addGrop")
						.setTabindex(2)
						.setTitleValue("View")
						.setType("button")
						.setValue("View")))
					
						
						
						.addElement(new TD()
						.setWidth(5))
						
						.addElement(new TD()
						.addElement(closeButton
						.setClassId("sbttn")
						.setName("addGrop")
						.setTabindex(3)
						.setTitleValue("Close")
						.setType("button")
						.setValue("Close")))

									  ));
					
				String sql="";		
				System.out.println("lllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll"+type1);		
		
				
				
				if(type1.equals("Templatexml"))
				{
				sql="select  application_template_id as \"select\" ,application_template_title as \"Title\" , upload_on as\"upload_on\", ui_framework as \"UI Framework\" , CONCAT(ROUND(length(applivation_xml_value)/1024,2),' KB') as \"File Size\" , block_ui_timeout as \"B U I\" from application_template_master where application_template_id='"+template_id+"'";
				}
				else
				if	(type1.equals("Resources"))
			      {
					sql="select application_template_id as \"select\" ,file_name as \"File Name\" , delivery_mode as \"Delivery Mode\" , asset_type as \"Asset Type\" , sequence_no as \"Seq No\" ,asset_path as \"Asset Path\"  from application_template_asset where application_template_id='"+template_id+"'";
				}
				
				try {
					if(type1!="")
					{
					JSPGridPro2 grid1 = new JSPGridPro2(request,"frm"); 
					grid1.setConnectionParameters(sql);		
		
					grid1.setWidth("100%");
					grid1.setCellPadding(2);
					grid1.setCellSpacing(1);
					grid1.setFontFace("Arial");
					grid1.setFontSize(2);
					grid1.setEvenRowBgColor("#C0C0C0"); 
					grid1.setOddRowBgColor("#F0F0F0");
					grid1.setCaption("");
					grid1.setMaxRowsPerPage(5);   		//how many records displayed per page
					grid1.setMaxResultPagesPerLoad(5);  //Page : 1 2 3 4 5 6 7 8 9 10 (max 10 pages displayed)
					grid1.setLineNoHeaderBgColor("#48E6F7");
					grid1.Cols(0).setFieldType(JSPGridPro2.FIELD_RADIO);
					grid1.Cols(1).setFieldType(JSPGridPro2.FIELD_HIDDEN);
					grid1.Cols(2).setFieldType(JSPGridPro2.FIELD_HIDDEN);
					grid1.Cols(5).setFieldType(JSPGridPro2.FIELD_HIDDEN);
		
		
	
					grid1.Cols(0).setFieldName("checkbox");
					grid1.Cols(1).setFieldName("filename");
					grid1.Cols(2).setFieldName("deliverymode");
					grid1.Cols(5).setFieldName("assetpath");
		
		
		
					grid1.Cols(0).Header().setClassID("swb");
					grid1.Cols(1).Header().setClassID("swb");
					grid1.Cols(2).Header().setClassID("swb");
					grid1.Cols(3).Header().setClassID("swb");
					grid1.Cols(4).Header().setClassID("swb");
					grid1.Cols(5).Header().setClassID("swb");
		
		
		
					grid1.Cols(0).insertFieldScript("onclick=\"CCA();checkbox_onclick();\"");
					grid1.setEachRowHeight("20");
					grid1.canSort(0, false);
					grid1.canSort(1, true);
		
		
					grid1.setSortableColumnsToolTip("Click to Sort");
					grid1.showPageNavigationFirst();
					grid1.showPageNavigationLast();
					grid1.hidePageNavigationHTML();
					grid1.setPageNavigationFontFace("Arial");
					grid1.setPageNavigationFontSize(2);
					grid1.setASCImageSource("../coreadmin/images/asc.gif");
					grid1.setDESCImageSource("../coreadmin/images/desc.gif");
					
				 
					grid1.buildGrid(); //result set being processed, and cell values are available			
				  
					if (grid1.isResultSetEmpty()) {
						form.addElement("<p id=\"record\">No Records Found");
					}
					else {						
						grid1.countResultSet();				
						form.addElement("<p>Total No. Of Resource: " +grid1.getRows());
						form.addElement(grid1.getGrid());
					}	
					//Added by Diptendu 29-Oct-2015
					
					grid1.closeConnection();
					
					}					
				}

				catch (Exception exp) {
					exp.printStackTrace();
				}			
				form.addElement("<input type=\"hidden\" name=\"resource_id\">");	
				form.addElement("<input type=\"hidden\" name=\"selectedfilename\">");	
				form.addElement("<input type=\"hidden\" name=\"selecteddeliverymode\">");
				form.addElement("<input type=\"hidden\" name=\"selectedassetpath\">");
				form.addElement(new Input()
												  .setType("hidden")
												  .setName("template_id")
												  .setValue(template_id));			
				table.addElement(new TR()
						.addElement(new TD()
						.addElement(table2)))
						.addElement(new TR()
						.addElement(new TD()
						.addElement(table1)));
				form.addElement(table);
				form.addElement("<div id=\"status-message\" style=\"color:red;height: 300px;overflow: auto\">"+statusMessage+"</div>");
				body.addElement(form);
				html.addElement(body.setClass("bodyadmin"));
				out.print(html.toString());


		}
	}
		
	
	    public void doPost(HttpServletRequest request, HttpServletResponse response)
				throws IOException, ServletException {
			doGet(request, response);
				}

				public String add(HttpServletRequest request, String strCreatedBy, PrintWriter out1, HttpServletResponse response, String type1 )
						throws IOException, ServletException {
					String resource_id=request.getParameter("resource_id");
					String template_id=request.getParameter("template_id");
					String statusMessage=uploadTemplate(request, response, strCreatedBy, out1, template_id, resource_id, false, type1);
					return statusMessage;
				}
				public static String uploadTemplate(HttpServletRequest request, HttpServletResponse response, String strCreatedBy, PrintWriter out1, String template_id, String resource_id, boolean isNew, String type1) throws IOException, ServletException {

					String statusMessage = "";
					String attachmentname = null;
					String fileName=null;
					String s7 = "";
					String strSize = "";
					//String inlinecss = "";
				//	String inlinejs = "";
					//String imagepath = "";
					String default_value= null;
					 String foldername="application-template.xml";
				//	String template_id = "";
					if (isNew) {
						template_id = request.getParameter("template_id");
						
						ResourceBundle rb = ResourceBundle.getBundle("portal", Locale.getDefault());
						// String filename = "";

						String key1 = "xml";
						String resourcePath = rb.getString(key1);
						String path =resourcePath;
						File ff = new File(path);
						ff.mkdir();
						attachmentname = path;
						//String default_value= null;
						File uploadfile = new File(attachmentname);
						try {
							MultipartRequest multipartrequest = new MultipartRequest(request, attachmentname, 50 * 1024 * 1024);
							for (Enumeration enumeration = multipartrequest.getFileNames(); enumeration.hasMoreElements();) {
								String s6 = (String) enumeration.nextElement();
								s7 = multipartrequest.getFilesystemName(s6);
								uploadfile = multipartrequest.getFile(s6);
								if (uploadfile != null) {
									Long size = new Long(uploadfile.length());
									strSize = size.toString();
								} else {
									statusMessage = "File is not selected!";
									break;
								}
							}
							 fileName = multipartrequest.getFilesystemName("filename");
							//inlinecss = multipartrequest.getParameter("inlinecss");
							//inlinejs = multipartrequest.getParameter("inlinejs");
							//imagepath = multipartrequest.getParameter("imagepath");
							// System.out.println("..................................INLINECSS................"+inlinecss);

						} catch (IOException ioexception) {
							statusMessage = "Failed due to - " + ioexception.getMessage();
							ioexception.printStackTrace();
						}
					} else {
						template_id = request.getParameter("template_id");
						ResourceBundle rb = ResourceBundle.getBundle("portal", Locale.getDefault());
						// String filename="";

						String key1 = "xml";
						String photopath = rb.getString(key1);
						String path = photopath;
						File ff = new File(path);
						ff.mkdir();
						attachmentname = path;

						try {
							MultipartRequest multipartrequest = new MultipartRequest(request, attachmentname, 50 * 1024 * 1024);
							for (Enumeration enumeration = multipartrequest.getFileNames(); enumeration.hasMoreElements();) {
								String s6 = (String) enumeration.nextElement();
								s7 = multipartrequest.getFilesystemName(s6);
								if (s7 == null) {
									statusMessage = "File is not selected!";
									break;
								}
								// uploadfile = multipartrequest.getFile(s6);
							}
							// filename=multipartrequest.getFilesystemName("filename");
						} catch (IOException ioexception) {
							statusMessage = "Failed due to - " + ioexception.getMessage();
							ioexception.printStackTrace();
						}
					}
					 if (type1.equals("Resources"))
							{
						 statusMessage = uploadResource(request, template_id, attachmentname, s7, type1);
							}
					 else if(type1.equals("Templatexml"))
						{
					 statusMessage =uploadTemplateXml(request,template_id,attachmentname,foldername,s7,strSize,default_value);
						}
				return statusMessage;
				}
				
				
				
				private static String uploadResource(HttpServletRequest request, String template_id, String attachmentname, String s7, String type1
			) {
					
					Pair<Boolean, String> returnStatus=new Pair<>();
					String statusMessage = "";
					try {
					//	Pair<Boolean, String> validationStatus = SchemaValidatation.validate(request.getServletContext(), inFileName);
					//	boolean isSuccess = getFirst();
					//	if (isSuccess) {
						//String fileName = request.getParameter("file_name");
                     
                     returnStatus = DataBaseLayer.updateResource(template_id,s7,attachmentname,type1);
                     if(returnStatus.getFirst()==true){
							
							statusMessage = "Successfully Uploaded resource.";
                     }
                     else
                     {
                    	 statusMessage = "Failed to Upload resource.";
                     }
					} catch (Exception e) {
						statusMessage = "Failed to upload resource: " + e.getMessage();
						e.printStackTrace();
					}
					return statusMessage;
				}

public static synchronized String uploadTemplateXml(HttpServletRequest request,String template_id,String attachmentname,String foldername,String s7,String strSize, String default_value)
{
	 String  inFileName=attachmentname+s7; 
	 String pathname=attachmentname+foldername;
	 String statusMessage="";
	 String current_template_id= null;
	 Pair<Boolean, String> validationStatus=SchemaValidatation.validateApplicationTemplateXml(request.getServletContext(),inFileName);
	 boolean isSuccess=validationStatus.getFirst();
	 if(isSuccess){
		 DOMParser parser2 = new DOMParser();
		 try
		 {
			
			 parser2.parse(inFileName);	
			 Document document = parser2.getDocument();
			 NodeList templatelist = document.getElementsByTagName("application-template");
			 for(int x1=0; x1<templatelist.getLength() ; x1++)
			 {
				 Element template = (Element)templatelist.item(x1);
				 String template_title= template.getAttribute("title");
				 String uiFramework= template.getAttribute("UIframework");
				 uiFramework=GenericUtil.hasString(uiFramework)?uiFramework:null;
				 Integer blockUITimeout= GenericUtil.convertStringToInt(template.getAttribute("BlockUItimeout"));
				 
				 System.out.println("..............OLD TEMPLATE ID .............."+template_id);
				 coreadministrationv2.dbconnection.DataBaseLayer.templateDelete(template_id);
				 isSuccess=coreadministrationv2.dbconnection.DataBaseLayer.TemplateInsert(template_title,attachmentname,s7,strSize,uiFramework,blockUITimeout);
				 if(isSuccess){

					 current_template_id=coreadministrationv2.dbconnection.DataBaseLayer.getCurrentTemplate_ID();
					 NodeList application_defaults_list = ((Element)templatelist.item(x1)).getElementsByTagName("application-defaults");
					 for(int x2=0; x2<application_defaults_list.getLength(); x2++)
					 {
						 NodeList configuration_section_list = ((Element)application_defaults_list.item(x2)).getElementsByTagName("configuration-section");
						 for(int a=0; a<configuration_section_list.getLength() ;a++)
						 {
							 //Element configuration_section_list_element= (Element)configuration_section_list.item(x1);
							 //String themes= configuration_section_list_element.getAttribute("ThemeID");
							 NodeList configuration_attribute_list = ((Element)configuration_section_list.item(a)).getElementsByTagName("attribute");
							 for(int g=0; g<configuration_attribute_list.getLength() ; g++)
							 {
								 Element configuration_attribute_element = (Element)configuration_attribute_list.item(g);
								 String  configuration_attribute_name=configuration_attribute_element.getAttribute("name");
								 String  configuration_attribute_value=configuration_attribute_element.getAttribute("defaultvalue");
								 coreadministrationv2.dbconnection.DataBaseLayer.InsertApplicationDefaultValue(current_template_id,"","Configuration",configuration_attribute_name,configuration_attribute_value);
							 }
						 }

						 
							 NodeList nodelistclass = ((Element)application_defaults_list.item(x2)).getElementsByTagName("class");	
							 for(int b=0; b<nodelistclass.getLength() ; b++)
							 {
								 Element classelement = (Element)nodelistclass.item(b);
								 String class_name = classelement.getAttribute("name");
								 NodeList nodelistsection = ((Element)nodelistclass.item(b)).getElementsByTagName("section");
								 for(int c=0;c<nodelistsection.getLength() ; c++)
								 {
									 Element sectionelement = (Element)nodelistsection.item(c);
									 String section_name = sectionelement.getAttribute("name");
									 NodeList nodelistattribute = ((Element)nodelistsection.item(c)).getElementsByTagName("attribute");
									 for(int d=0; d<nodelistattribute.getLength() ; d++)
									 {
										 Element attributeelement = (Element)nodelistattribute.item(d);
										 String  attribute_name=attributeelement.getAttribute("name");
										 String  attribute_value=attributeelement.getAttribute("defaultvalue");
										 coreadministrationv2.dbconnection.DataBaseLayer.InsertApplicationDefaultValue(current_template_id,class_name,section_name,attribute_name,attribute_value);
									 }
								 }

							 }

						 }

						 NodeList framework_asset_delivery_list = ((Element)templatelist.item(x1)).getElementsByTagName("framework-asset-delivery");
						 for(int e=0; e<framework_asset_delivery_list.getLength() ; e++)
						 {
							 NodeList asset_list = ((Element)framework_asset_delivery_list.item(e)).getElementsByTagName("asset");
							 for(int f=0; f<asset_list.getLength() ;f++)
							 {
								 Element asset_list_element= (Element)asset_list.item(f);
								 String type= asset_list_element.getAttribute("type");
								 String deliverymode= asset_list_element.getAttribute("deliverymode");
								 String pagelocation= asset_list_element.getAttribute("pagelocation");
								 String deliverysequence= asset_list_element.getAttribute("deliverysequence");
								 String filename= asset_list_element.getAttribute("filename");
								 String assetpath= asset_list_element.getAttribute("assetpath");
								 String asset_value= asset_list_element.getAttribute("asset_value");
								 
				                  coreadministrationv2.dbconnection.DataBaseLayer.TemplateAsset(current_template_id,type,deliverymode,pagelocation,deliverysequence,filename,assetpath,pathname);
							 }
						 }
					 }else{
						 statusMessage="Failed to upload Application Template. Reason : Application Template with same title '"+template_title+"' already exists";
						 break;
					 }
				 }
				 if(current_template_id!=null && isSuccess){
					 if(_DEFAULT_VALUE_YES.equalsIgnoreCase(default_value)){
						 coreadministrationv2.dbconnection.DataBaseLayer.setDefaultValueTemplate(current_template_id);
					 } 
				 }
				 if(isSuccess){
					 statusMessage="Application Template uploaded successfully.";
				 }
			 }catch (SAXException e) {
				 statusMessage="Failed to upload Application Template. Reason : "+e.getMessage();
				 //current_template_id=null;
				 e.printStackTrace();
			 } 
			 catch (IOException e1) {
				 statusMessage="Failed to upload Application Template. Reason : "+e1.getMessage();
				 //current_template_id=null;
				 e1.printStackTrace();
			 } 	
		 }else{
			 statusMessage=validationStatus.getSecond();
		 }
		 return statusMessage;
	 }
					 
				 
				 public String deletelayout(HttpServletRequest request, String strCreatedBy, PrintWriter out1)
								throws IOException, ServletException {
							return "Under developement.";
								}

          


}

