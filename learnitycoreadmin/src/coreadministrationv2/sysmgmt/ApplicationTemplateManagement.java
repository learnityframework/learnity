package coreadministrationv2.sysmgmt;

/**
 * Title:        Learnity Interface Management   
 * Description:
 * Copyright:    Copyright (c) 2010
 * Company:      Aunwesha
 * @author Shibaji Chatterjee
 */




import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.ecs.*;
import org.apache.ecs.html.*;
import comv2.aunwesha.param.*;
import comv2.aunwesha.JSPGrid.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
//import oracle.xml.parser.v2.*;
import com.oreilly.servlet.MultipartRequest;
import java.text.*;
import java.util.Vector;
import java.util.Random;
import java.io.*;
import java.net.*;
import  org.w3c.dom.Document;
//import jmesa.*;
import org.apache.xerces.parsers.DOMParser;

//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
import java.util.zip.*;
import  org.w3c.dom.Element;
import coreadministrationv2.dbconnection.DataBaseLayer;
import coreadministrationv2.utility.*;
import interfaceenginev2.*;
import  org.apache.xml.serialize.OutputFormat;
import  org.apache.xml.serialize.Serializer;
import  org.apache.xml.serialize.SerializerFactory;
import  org.apache.xml.serialize.XMLSerializer;
import  org.apache.xerces.dom.DocumentImpl;

public class ApplicationTemplateManagement extends HttpServlet {

	private static final String LOGIN_SESSION_NAME = "ADMIN_LOG_ON";
	private static final String OBJ = "OBJ";
	//private static final SimpleLogger log = new SimpleLogger(ApplicationTemplateManagement.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        response.setContentType("text/html");
        response.setHeader("Pragma", "no-cache");
	     response.setHeader("Cache-Control", "no-cache");
	     response.setDateHeader("Expires", 0);
        PrintWriter out = response.getWriter();
        /***************************************************************************************************/
        /*                                      Check Authentication                                       */
        /***************************************************************************************************/
        HttpSession mysession=request.getSession(true);
		  Object obj = mysession.getAttribute(LOGIN_SESSION_NAME);
		  if (obj ==null)
			response.sendRedirect("../coreadmin/login.html");
        else {
        	String strAdminId = obj.toString();
        	String strPrmAddModDel = request.getParameter("prmAddModify");

        	//String strAdministratorPreviledge = coreadministration.dbconnection.DataBaseLayer.getAdminstratorPreviledge(strAdminId);
        	//if (strAdministratorPreviledge != null) {
	        	if (strPrmAddModDel!=null) {
		        	int iPrmAddModify = Integer.parseInt(strPrmAddModDel);
			        switch(iPrmAddModify) {

			        	case 0:
				        	
				        		add(request, strAdminId, out);
				        	        break;
			        	case 1:
			        		
			        			modify(request, strAdminId,out);
			        		        break;
			        	case 2:
			        		
			        			delete(request, out);
			        		        break;
			        		
			        	
					}
				}
	        	getResult(request, response, out, strAdminId);
        	
        }
    }

    public void getResult(HttpServletRequest request, HttpServletResponse response, PrintWriter out, String strAdminId)
    		throws IOException, ServletException {
    	/***************************************************************************************************/
        /*                                        For Date And Time										   */
        /***************************************************************************************************/
        Calendar calendar = new GregorianCalendar();
		Date trialTime = new Date();
		calendar.setTime(trialTime);

		//create array of string to hold days.
		String months[]={"January","Feburary","March", "April", "May","June",
							"July","August","September","October","November","December"};

		String strTime = calendar.get(Calendar.HOUR)+":"+ calendar.get(Calendar.MINUTE);
		String strDate = months[calendar.get(Calendar.MONTH)]+" "+ calendar.get(Calendar.DATE)+", "+
																calendar.get(Calendar.YEAR);

        /********************************************End of Date and Time***********************************/

        /***************************************************************************************************/
        /*                                        Get Parameter Value									   */
        /***************************************************************************************************/
		  String template_id = request.getParameter("template_id");
         
        String javaScript = "\n	var index = 0;"+
        					"\n	var rowId = 0;"+
        				//	"\n var role_id = \""+role_id+"\";"+
        					"\n	function findRow(){"+
        					"\n		for (var counter=0; counter<document.frm.checkbox.length; counter++) {"+
            				"\n			if (document.frm.checkbox[counter].value == role_id) {"+
                			"\n				rowId = counter;"+
							"\n			}"+
							"\n		}"+
        					"\n	return true;"+
    			    		"\n	}"+
    			    		"\n	function test() {"+
        					"\n		var index = 0;"+
        					"\n		for (var i=0;i<document.frm.elements.length;i++){"+
							"\n			var e = document.frm.elements[i];"+
							"\n			if (e.type=='radio'){"+
							"\n				index++;"+
							"\n			}"+
							"\n		}"+
							"\n		return index;"+
							"\n	}"+
    			    		"\n	function checkEntries(){"+
        		     		"\n		var filledIn = false;"+
        		     		"\n		var i = test();"+
        					"\n		if(i>1) {"+
        					"\n			for (var counter=0; counter<document.frm.checkbox.length; counter++)"+
            			"\n				if (document.frm.checkbox[counter].checked == true) {"+
                		"\n					index = counter;"+
                		"\n					filledIn = true;"+
							"\n				}"+
        					"\n			if (filledIn == false){"+
            			"\n				alert(\'You must select at least one Template\');"+
            			"\n				return false;"+
        					"\n			}"+
        					"\n		}"+
        					"\n		if(i==1)  {"+
            			"\n			if (document.frm.checkbox.checked == true) {"+
                		"\n				filledIn = true;"+
							"\n			}"+
        					"\n			if (filledIn == false){"+
				  "\n				alert(\'You must select at least one Template\');"+
            			"\n				return false;"+
        					"\n			}"+
        					"\n		}"+
        					"\n		return true;"+
    			    		"\n	}"+
    			    		"\n"+
							"\n	function addLayout_onclick() {"+
							"\n		document.frm.method=\"post\";"+
				         "\n		document.frm.action = \"./coreadministrationv2.sysmgmt.ApplicationTemplateManagement?prmAddModify=0\";"+
							"\n		document.frm.encoding = \"multipart/form-data\";"+
							"\n		document.frm.submit();"+
							"\n	}"+
				  
							"\n	function download_onclick() {"+
							"\n		var i = test();"+
							"\n		if(i==1) {"+
				         "\n			if(!checkEntries())"+
				         "\n			return false;"+
							"\n			document.frm.method=\"post\";"+
				         "\n			document.frm.action = \"coreadministrationv2.sysmgmt.DownloadDefaultXML?template_id=\"+document.frm.template_id.value;"+
							"\n			document.frm.submit();"+
							
							"\n		}"+
							"\n		if(i>1) {"+
				         "\n			if(!checkEntries())"+
				         "\n			return false;"+
							"\n			document.frm.method=\"post\";"+
				         "\n			document.frm.action = \"coreadministrationv2.sysmgmt.DownloadDefaultXML?template_id=\"+document.frm.template_id.value;"+
							"\n			document.frm.submit();"+
							
									
							"\n	} "+
							"\n	} "+
				      
							"\n	function setDefault_onclick() {"+
							"\n		var i = test();"+
							"\n		if(i==1) {"+
				         "\n			if(!checkEntries())"+
				         "\n				return false;"+			
							"\n			document.frm.method=\"post\";"+
				         "\n			document.frm.action = \"coreadministrationv2.sysmgmt.ApplicationTemplateManagement?prmAddModify=1&template_id=\"+document.frm.template_id.value;"+
							"\n			document.frm.submit();"+
										
							"\n		}"+
							"\n		if(i>1) {"+
				         "\n			if(!checkEntries())"+
				         "\n				return false;"+			
							"\n			document.frm.method=\"post\";"+
				         "\n			document.frm.action = \"coreadministrationv2.sysmgmt.ApplicationTemplateManagement?prmAddModify=1&template_id=\"+document.frm.template_id.value;"+
							"\n			document.frm.submit();"+
							"\n	} "+
							"\n	} "+
				  
							"\n	function deleteLayout_onclick() {"+
        		     		"\n		var i = test();"+
        					"\n		if(i>1) {"+
							"\n			if(!checkEntries())"+
							"\n			return false;"+
				         "\n			doyou = confirm(\"Are you Sure to Delete The Selected Template Item?\"); //Your question."+
							"\n"+
							"\n			if (doyou == true) {"+
				         "\n				location.href = \"coreadministrationv2.sysmgmt.ApplicationTemplateManagement?prmAddModify=2&template_id=\"+document.frm.template_id.value;"+
							"\n			}"+
							"\n			else {"+
							"\n			}"+
							"\n		}"+
        					"\n		if(i==1) {"+
							"\n			if(!checkEntries())"+
							"\n				return false;"+
				  			"\n			doyou = confirm(\"Are you Sure to Delete The Selected Template Item?\"); //Your question."+
							"\n"+
							"\n			if (doyou == true) {"+
				         "\n				location.href = \"coreadministrationv2.sysmgmt.ApplicationTemplateManagement?prmAddModify=2&template_id=\"+document.frm.template_id.value;"+ 
							"\n			}"+
							"\n			else {"+
							"\n			}"+

        					"\n		}"+
							"\n		return true;"+
							"\n	}"+
							
							
        					
        					"\n	function checkbox_onclick() {"+
        		     		"\n		var i = test();"+
        		     	
        		     		"\n		if(i>1) {"+
        		     		
        					"\n			for(var counter=0; counter<document.frm.checkbox.length; counter++) {"+
        					"\n				if(document.frm.checkbox[counter].checked) {"+
				         "\n				   document.frm.template_id.value=document.frm.checkbox[counter].value;"+
				         //"\n				   document.frm.defaultvalue1.value=document.frm.defaultvalue[counter].value;"+
				  			"\n                        if(document.frm.defaultvalue[counter].value=='yes'){"+
				 			"\n									document.frm.defaultvalue1.checked=true;"+
							"\n								}"+
				         "\n                        if(document.frm.defaultvalue[counter].value!='yes'){"+
				         "\n									document.frm.defaultvalue1.checked=false;"+
				         "\n								}"+
				        	"\n					break;"+
        					"\n				}"+
        					"\n			}"+
        					"\n		}"+
        					"\n		if (i==1) {"+
        					
        					"\n			if(document.frm.checkbox.checked) {"+
				         "\n				   document.frm.template_id.value=document.frm.checkbox.value;"+
							"\n                        if(document.frm.defaultvalue.value=='yes'){"+
							"\n									document.frm.defaultvalue1.checked=true;"+
							"\n								}"+
				         "\n                        if(document.frm.defaultvalue[counter].value!='yes'){"+
				         "\n									document.frm.defaultvalue1.checked=false;"+
				         "\n								}"+
				 			"\n			}"+
        					"\n		}"+
        					"\n	}"+
        					"\n"+

        					
        					
   							
				   		"\n function load() {"+
        		     		"\n		var i = test();"+
        				   "\n		if(i>1) {"+
							"\n 		findRow();"+
							"\n			document.frm.checkbox[rowId].checked=true;"+
							"\n			CCA();"+
							"\n		}"+
							"\n		if(i==1) {"+
							"\n			document.frm.checkbox.checked=true;"+
							"\n			CCA();"+
							"\n		}"+
							"\n	}";
		  
		  
		  

        Body body = new Body();
        Form form = new Form();
	     Input inputButton1 = new Input();
	     Input inputButton2 = new Input();
	     Input inputButton3 = new Input();
		  Input inputButton4 = new Input();
		  inputButton1.setOnClick("addLayout_onclick();");
		  inputButton2.setOnClick("download_onclick();");
		  inputButton3.setOnClick("deleteLayout_onclick();");
		  inputButton4.setOnClick("setDefault_onclick();");
	     Html html = new Html()
               .addElement(new Head()
					.addElement(new Title("Template Management"))
					.addElement(new Link()
						.setHref("../coreadmin/js/stylesheet.css")
						.setRel("stylesheet"))
					.addElement(new Script()
						.setLanguage("JavaScript")
						.setSrc("../coreadmin/js/check.js"))
					.addElement(new Script()
					.setLanguage("JavaScript")
					.addElement(javaScript)))

               .addElement(body
					.addElement(form
	              	.setName("frm")
				  	.addElement(new TableExtension()
				  		.headerTable("<b>System Administrator: </b>"+strAdminId, strDate, strTime, "<b>System Administration: </b>Application Template Management"))));
	    
		  
		  
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
								    )))
									
									
								    )));
		  String sql = "select a.application_template_id as \"Select\", a.application_template_title as \"Template\", "+
				         " a.default_value as \" Default value\" "+
		             
				        " from application_template_master a ";
		try {
			JSPGridPro2 grid1 = new JSPGridPro2(request,"frm"); 
			grid1.setConnectionParameters(sql);		
			
			grid1.setWidth("100%");
			grid1.setCellPadding(2);
			grid1.setCellSpacing(1);
			grid1.setFontFace("Arial");
			grid1.setFontSize(2);
			grid1.setEvenRowBgColor("#C0C0C0"); 
			grid1.setOddRowBgColor("#F0F0F0");
			grid1.setCaption("Currently Defined Template");
			grid1.setMaxRowsPerPage(5);   		//how many records displayed per page
			grid1.setMaxResultPagesPerLoad(5);  //Page : 1 2 3 4 5 6 7 8 9 10 (max 10 pages displayed)
			grid1.setLineNoHeaderBgColor("#48E6F7");
			grid1.Cols(0).setFieldType(grid1.FIELD_RADIO);
			grid1.Cols(1).setFieldType(grid1.FIELD_HIDDEN);		
			grid1.Cols(2).setFieldType(grid1.FIELD_HIDDEN);	
			
			
			
			
			grid1.Cols(0).setFieldName("checkbox");
			grid1.Cols(1).setFieldName("themesid");
			grid1.Cols(2).setFieldName("defaultvalue");
			
			
			
			
			grid1.Cols(0).Header().setClassID("swb");
			grid1.Cols(1).Header().setClassID("swb");
			grid1.Cols(2).Header().setClassID("swb");
			
			
			grid1.Cols(0).insertFieldScript("onclick=\"CCA();checkbox_onclick();\"");
			grid1.setEachRowHeight("20");
			grid1.canSort(0, false);
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
			grid1.buildGrid(); //result set being processed, and cell values are available			
			
			if (grid1.isResultSetEmpty()) {
				form.addElement("<p id=\"record\">No Records Found");
			}
			else {						
				grid1.countResultSet();				
				form.addElement("<p>Total No. Of Template: " +grid1.getRows());
				form.addElement(grid1.getGrid());
			}	
			//Added by Diptendu 29-Oct-2015
			
			grid1.closeConnection();
			
			
		}
		catch (Exception exp) {
			
		}	
		Input styleid= new Input().setType("file").setName("filename").setClassId("PPRLabelText");

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
				.setWidth("160")
				.addElement("Default Value"))
				.addElement(new TD()
				.setWidth("336")
				.addElement(new Input()
				.setClassId("PPRLabelText")
				.setName("defaultvalue1")
				.setValue("yes")
				.setType("checkbox")
							  )))
							
				
							
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
				.setName("modifyGroup")
				.setTabindex(2)
				.setTitleValue("Download")
				.setType("button")
				.setValue("Download")))
				.addElement(new TD()
				.setWidth(5))
				.addElement(new TD()
				.addElement(inputButton3
				.setClassId("sbttn")
				.setName("deleteGroup")
				.setTabindex(2)
				.setTitleValue("Delete")
				.setType("button")
				.setValue("Delete")))
				
				.addElement(new TD()
				.setWidth(5))
				.addElement(new TD()
				.addElement(inputButton4
				.setClassId("sbttn")
				.setName("deleteGroup")
				.setTabindex(2)
				.setTitleValue("Set As Default")
				.setType("button")
				.setValue("Set As Default")))

							  ));
		table.addElement(new TR()
				.addElement(new TD()
				.addElement(table2)))
				.addElement(new TR()
				.addElement(new TD()
				.addElement(table1)));
				form.addElement(table);
				form.addElement("<input type=\"hidden\" name=\"template_id\">");	
				body.addElement(form);			
				//body.setOnLoad("scrollit(100);load()");
			
				out.println(html.toString());
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        doGet(request, response);
    }
    public void add(HttpServletRequest request, String strCreatedBy, PrintWriter out1)
       throws IOException, ServletException 
	 {
		 String template_id="";
				ResourceBundle rb = ResourceBundle.getBundle("portal",Locale.getDefault());      
				String filename="";
				String s7="";
				String key1= "templatexml"; 
				String photopath = rb.getString(key1);
				String path=photopath;
				String strSize="";
				File ff=new File(path);
				ff.mkdir();
				String attachmentname=path;
				File uploadfile = new File(attachmentname);
				try
				{
					MultipartRequest multipartrequest = new MultipartRequest(request,attachmentname,50*1024*1024);
					for(Enumeration enumeration = multipartrequest.getFileNames(); enumeration.hasMoreElements();)
					{	
						String s6 = (String)enumeration.nextElement();
						s7 = multipartrequest.getFilesystemName(s6);
						uploadfile = multipartrequest.getFile(s6);
						Long size=new Long(uploadfile.length());
						strSize=size.toString();	
					}
					filename=multipartrequest.getFilesystemName("filename");
					template_id=multipartrequest.getParameter("template_id");
				}	
				catch(IOException ioexception)
				{
					ioexception.printStackTrace();
				}
				uploadTemplateXML(template_id,attachmentname,s7,strSize);
				
     }
     public void modify(HttpServletRequest request, String strModBy,PrintWriter out1)
        throws IOException, ServletException {
		  String template_id=request.getParameter("template_id");
		  String default_value=request.getParameter("defaultvalue1");
		  coreadministrationv2.dbconnection.DataBaseLayer.SetDefaultValueTemplate(template_id,default_value);
		 
    }
    public void delete(HttpServletRequest request, PrintWriter out1)
        	throws IOException, ServletException {
		 String template_id=request.getParameter("template_id");
		 coreadministrationv2.dbconnection.DataBaseLayer.TemplateDelete(template_id);
    }
	 
	 public synchronized void uploadTemplateXML(String template_id,String attachmentname,String s7,String strSize)
	 {
		 String  inFileName=attachmentname+s7; 
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
					System.out.println("..............OLD TEMPLATE ID .............."+template_id);
					coreadministrationv2.dbconnection.DataBaseLayer.TemplateDelete(template_id);
					coreadministrationv2.dbconnection.DataBaseLayer.TemplateInsert(template_title,attachmentname,s7,strSize);
					String current_template_id=coreadministrationv2.dbconnection.DataBaseLayer.getCurrentTemplate_ID();
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
							coreadministrationv2.dbconnection.DataBaseLayer.TemplateAsset(current_template_id,type,deliverymode,pagelocation,deliverysequence,filename,assetpath);
						}
					}
			}
			 
	}catch (SAXException e) {
			 e.printStackTrace();
		 } 
		 catch (IOException e1) {
			 e1.printStackTrace();
		 } 			
	 }
    
     
}
