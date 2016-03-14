package coreadministrationv2.sysmgmt;

/**
 * Title:        Learnity Interface Management   
 * Description:
 * Copyright:    Copyright (c) 2010
 * Company:      Aunwesha
 * @author Shibaji Chatterjee
 */




import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ecs.html.Body;
import org.apache.ecs.html.Form;
import org.apache.ecs.html.Head;
import org.apache.ecs.html.Html;
import org.apache.ecs.html.IMG;
import org.apache.ecs.html.Input;
import org.apache.ecs.html.Link;
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
import comv2.aunwesha.JSPGrid.JSPGridPro2;
import comv2.aunwesha.lfutil.Pair;

import coreadministrationv2.sysmgmt.xml.util.SchemaValidatation;
import coreadministrationv2.utility.TableExtension;
//import oracle.xml.parser.v2.*;
//import jmesa.*;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;

public class ThemesManagement extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1256765254476358723L;
	private static final String LOGIN_SESSION_NAME = "ADMIN_LOG_ON";
	private static final String _DEFAULT_VALUE_YES = "yes";
	//private static final SimpleLogger log = new SimpleLogger(ThemesManagement.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        response.setContentType("text/html");
        response.setHeader("Pragma", "no-cache");
	     response.setHeader("Cache-Control", "no-cache");
	     response.setDateHeader("Expires", 0);
        PrintWriter out = response.getWriter();
        String statusMessage="";
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
				        	
			        		statusMessage=add(request, strAdminId, out);
				        	        break;
			        	case 1:
			        		
			        		statusMessage=modify(request, strAdminId,out);
			        		        break;
			        	case 2:
			        		
			        		statusMessage=delete(request, out);
			        		        break;
			        		
			        	
					}
				}
	        	getResult(request, response, out, strAdminId,statusMessage);
        	
        }
    }

    public void getResult(HttpServletRequest request, HttpServletResponse response, PrintWriter out, String strAdminId,String statusMessage)
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
		//  String template_id = request.getParameter("template_id");
         
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
            			"\n				alert(\'You must select at least one Themes\');"+
            			"\n				return false;"+
        					"\n			}"+
        					"\n		}"+
        					"\n		if(i==1)  {"+
            			"\n			if (document.frm.checkbox.checked == true) {"+
                		"\n				filledIn = true;"+
							"\n			}"+
        					"\n			if (filledIn == false){"+
				         "\n				alert(\'You must select at least one Themes\');"+
            			"\n				return false;"+
        					"\n			}"+
        					"\n		}"+
        					"\n		return true;"+
    			    		"\n	}"+
    			    		"\n"+
							"\n	function addLayout_onclick() {"+
							"\n		document.frm.method=\"post\";"+
				         "\n		document.frm.action = \"./coreadministrationv2.sysmgmt.ThemesManagement?prmAddModify=0\";"+
							"\n		document.frm.encoding = \"multipart/form-data\";"+
							"\n		document.frm.submit();"+
							"\n	}"+
				  
							"\n	function download_onclick() {"+
							"\n		var i = test();"+
							"\n		if(i==1) {"+
				         "\n			if(!checkEntries())"+
				         "\n			return false;"+
							"\n			document.frm.method=\"post\";"+
				         "\n			document.frm.action = \"coreadministrationv2.sysmgmt.DownloadThemes?themes_id=\"+document.frm.themes_id.value;"+
							"\n			document.frm.submit();"+
							
							"\n		}"+
							"\n		if(i>1) {"+
				         "\n			if(!checkEntries())"+
				         "\n			return false;"+
							"\n			document.frm.method=\"post\";"+
				         "\n			document.frm.action = \"coreadministrationv2.sysmgmt.DownloadThemes?themes_id=\"+document.frm.themes_id.value;"+
							"\n			document.frm.submit();"+
							
									
							"\n	} "+
							"\n	} "+
				      
							"\n	function setDefault_onclick() {"+
							"\n		var i = test();"+
							"\n		if(i==1) {"+
				         "\n			if(!checkEntries())"+
				         "\n				return false;"+			
							"\n			document.frm.method=\"post\";"+
				         "\n			document.frm.action = \"coreadministrationv2.sysmgmt.ThemesManagement?prmAddModify=1&themes_id=\"+document.frm.themes_id.value;"+
							"\n			document.frm.submit();"+
										
							"\n		}"+
							"\n		if(i>1) {"+
				         "\n			if(!checkEntries())"+
				         "\n				return false;"+			
							"\n			document.frm.method=\"post\";"+
				         "\n			document.frm.action = \"coreadministrationv2.sysmgmt.ThemesManagement?prmAddModify=1&themes_id=\"+document.frm.themes_id.value;"+
							"\n			document.frm.submit();"+
							"\n	} "+
							"\n	} "+
				  
							"\n	function deleteLayout_onclick() {"+
        		     		"\n		var i = test();"+
        					"\n		if(i>1) {"+
							"\n			if(!checkEntries())"+
							"\n			return false;"+
				         "\n			doyou = confirm(\"Are you Sure to Delete The Selected Themes Item?\"); //Your question."+
							"\n"+
							"\n			if (doyou == true) {"+
				         "\n				location.href = \"coreadministrationv2.sysmgmt.ThemesManagement?prmAddModify=2&themes_id=\"+document.frm.themes_id.value;"+
							"\n			}"+
							"\n			else {"+
							"\n			}"+
							"\n		}"+
        					"\n		if(i==1) {"+
							"\n			if(!checkEntries())"+
							"\n				return false;"+
				  			"\n			doyou = confirm(\"Are you Sure to Delete The Selected Themes Item?\"); //Your question."+
							"\n"+
							"\n			if (doyou == true) {"+
				         "\n				location.href = \"coreadministrationv2.sysmgmt.ThemesManagement?prmAddModify=2&themes_id=\"+document.frm.themes_id.value;"+ 
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
				         "\n				   document.frm.themes_id.value=document.frm.themesid[counter].value;"+
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
							"\n				   document.frm.themes_id.value=document.frm.themesid.value;"+
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
					.addElement(new Title("Themes Management"))
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
				  		.headerTable("<b>System Administrator: </b>"+strAdminId, strDate, strTime, "<b>System Administration: </b>Themes Management"))));
	    
		  
		  
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
	    String sql = "select a.themes_id as \"Select\", a.themes_id as \"Themes\", "+
				        " a.default_value as \" Default value\", "+
		                 " CONCAT(ROUND(length(a.xml_value)/1024,2),' MB') as \"File Size\","+
				         " a.upload_on as \"Uploaded On\""+
				        " from themes a ";
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
			grid1.setCaption("Currently Defined Themes");
			grid1.setMaxRowsPerPage(5);   		//how many records displayed per page
			grid1.setMaxResultPagesPerLoad(5);  //Page : 1 2 3 4 5 6 7 8 9 10 (max 10 pages displayed)
			grid1.setLineNoHeaderBgColor("#48E6F7");
			grid1.Cols(0).setFieldType(JSPGridPro2.FIELD_RADIO);
			grid1.Cols(1).setFieldType(JSPGridPro2.FIELD_HIDDEN);		
			grid1.Cols(2).setFieldType(JSPGridPro2.FIELD_HIDDEN);
			grid1.Cols(3).setFieldType(JSPGridPro2.FIELD_HIDDEN);
			grid1.Cols(4).setFieldType(JSPGridPro2.FIELD_HIDDEN);
			
			
			
			
			grid1.Cols(0).setFieldName("checkbox");
			grid1.Cols(1).setFieldName("themesid");
			grid1.Cols(2).setFieldName("defaultvalue");
			grid1.Cols(3).setFieldName("fileSize");
			grid1.Cols(4).setFieldName("uploadedOn");
			
			
			
			
			grid1.Cols(0).Header().setClassID("swb");
			grid1.Cols(1).Header().setClassID("swb");
			grid1.Cols(2).Header().setClassID("swb");
			grid1.Cols(3).Header().setClassID("swb");
			grid1.Cols(4).Header().setClassID("swb");
			
			
			grid1.Cols(0).insertFieldScript("onclick=\"CCA();checkbox_onclick();\"");
			grid1.setEachRowHeight("20");
			grid1.canSort(0, false);
			grid1.canSort(1, true);
			grid1.canSort(2, true);
			grid1.canSort(3, true);
			grid1.canSort(4, true);
			
			
			
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
				form.addElement("<p>Total No. Of Themes: " +grid1.getRows());
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
				form.addElement("<input type=\"hidden\" name=\"themes_id\">");	
				form.addElement("<div id=\"status-message\" style=\"color:red;\">"+statusMessage+"</div>");
				body.addElement(form);			
				//body.setOnLoad("scrollit(100);load()");
			
				out.println(html.toString());
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        doGet(request, response);
    }
    public String add(HttpServletRequest request, String strCreatedBy, PrintWriter out1)
       throws IOException, ServletException 
	 {
    			String statusMessage=null; 
    			String themes_id="";
    			String default_value="";
				ResourceBundle rb = ResourceBundle.getBundle("portal",Locale.getDefault());     
				boolean isSuccess=true;
				//String filename="";
				String s7="";
				String key1= "themesxml"; 
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
					themes_id=multipartrequest.getParameter("themes_id");
					default_value=multipartrequest.getParameter("defaultvalue1");
						//filename=multipartrequest.getFilesystemName("filename");
				}	
				catch(IOException ioexception)
				{
					ioexception.printStackTrace();
					isSuccess=false;
				}
				if(isSuccess){
					statusMessage=uploadThemesXML(request,themes_id,attachmentname,s7,strSize,default_value);
				}else{
					statusMessage="Failed to upload theme";
				}
				return statusMessage;
				
     }
     public String modify(HttpServletRequest request, String strModBy,PrintWriter out1)
        throws IOException, ServletException {
		  String themes_id=request.getParameter("themes_id");
		  //String default_value=request.getParameter("defaultvalue1");
		  boolean isSuccess=coreadministrationv2.dbconnection.DataBaseLayer.setDefaultValue(themes_id);
		  if(isSuccess){
			  return "Successfully set default Theme.";
		  }else{
			  return "Failed to set default Theme.";
		  }
		 
    }
    public String delete(HttpServletRequest request, PrintWriter out1)
        	throws IOException, ServletException {
		 String themes_id=request.getParameter("themes_id");
		 boolean isSuccess=coreadministrationv2.dbconnection.DataBaseLayer.themesDelete(themes_id);
		 if(isSuccess){
			  return "Successfully delete theme '"+themes_id+"'.";
		  }else{
			  return "Failed to delete theme '"+themes_id+"'.";
		  }
    }
	 
	 private String uploadThemesXML(HttpServletRequest request,String selected_themes_id,String attachmentname,String s7,String strSize, String default_value)
	 {
		 String statusMessage="";
		 String  inFileName=attachmentname+s7; 
		 Pair<Boolean, String> validationStatus=SchemaValidatation.validateThemeXml(request.getServletContext(),inFileName);
		 boolean isSuccess=validationStatus.getFirst();
		 String currentThemeId=null;
		 if(isSuccess){
			 DOMParser parser2 = new DOMParser();
			 try
			 {
				 parser2.parse(inFileName);	
				 Document document = parser2.getDocument();
				 NodeList themeslist = document.getElementsByTagName("theme");
				 for(int x1=0; x1<themeslist.getLength() ; x1++)
				 {
					 Element theme = (Element)themeslist.item(x1);
					 coreadministrationv2.dbconnection.DataBaseLayer.themesDelete(selected_themes_id);
					 currentThemeId= theme.getAttribute("title");
					 isSuccess=coreadministrationv2.dbconnection.DataBaseLayer.ThemesInsert(currentThemeId,attachmentname,s7,strSize);
					 if(isSuccess){
						 NodeList themeselementlist = ((Element)themeslist.item(x1)).getElementsByTagName("themeselement");
						 for(int x2=0; x2<themeselementlist.getLength() ; x2++)
						 {
							 Element themeselement = (Element)themeselementlist.item(x2);
							 String class_type = themeselement.getAttribute("class");
							 String type = themeselement.getAttribute("type");
							 String cssclasses = themeselement.getAttribute("cssclasses");
							 String property = themeselement.getAttribute("property");
							 String propertyapplication = themeselement.getAttribute("propertyapplication");
							 coreadministrationv2.dbconnection.DataBaseLayer.ThemesElementInsert(currentThemeId,class_type,type,cssclasses,property,propertyapplication);

						 }

						 NodeList cssfilelist = ((Element)themeslist.item(x1)).getElementsByTagName("cssfile");
						 for(int x3=0; x3<cssfilelist.getLength() ; x3++)
						 {
							 NodeList filenamelist = ((Element)cssfilelist.item(x3)).getElementsByTagName("file");
							 for(int x4=0; x4<filenamelist.getLength() ; x4++)
							 {
								 Element filenameelement = (Element)filenamelist.item(x4);
								 String name = filenameelement.getAttribute("name");
								 coreadministrationv2.dbconnection.DataBaseLayer.ThemesCssFileInsert(currentThemeId,name);
							 }
						 }
					 }else{
						 statusMessage="Failed to upload Theme. Reason : Theme with same title '"+currentThemeId+"' already exists";
						 break;
					 }
				 }
				 if(isSuccess && _DEFAULT_VALUE_YES.equalsIgnoreCase(default_value)){
					 coreadministrationv2.dbconnection.DataBaseLayer.setDefaultValue(currentThemeId);
				 }
				 if(isSuccess){
					 statusMessage="Theme uploaded successfully";
				 }
				 
			 }catch (SAXException e) {
				 statusMessage="Failed to upload theme. Reason : "+e.getMessage();
				 e.printStackTrace();
			 } 
			 catch (IOException e1) {
				 statusMessage="Failed to upload theme. Reason : "+e1.getMessage();
				 e1.printStackTrace();
			 } 	
		 }else{
			 statusMessage=validationStatus.getSecond();
		 }
		 return statusMessage;
	 }
    
     
}
