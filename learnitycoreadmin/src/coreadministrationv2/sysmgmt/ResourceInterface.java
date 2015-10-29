package coreadministrationv2.sysmgmt;

/**
 * Title:        Learnity Interface Management   
 * Description:
 * Copyright:    Copyright (c) 2010
 * Company:      Aunwesha
 * @author Shibaji Chatterjee
 */




import interfaceenginev2.DisplayEngine;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.parsers.DOMParser;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.oreilly.servlet.MultipartRequest;
import comv2.aunwesha.JSPGrid.JSPGridPro2;

import coreadministrationv2.dbconnection.DataBaseLayer;
import coreadministrationv2.utility.TRExtension;
//import oracle.xml.parser.v2.*;
//import jmesa.*;
public class ResourceInterface extends HttpServlet {

	private static final String LOGIN_SESSION_NAME = "ADMIN_LOG_ON";
	    //public static final SimpleLogger log = new SimpleLogger(InterfaceManagement.class, true);
	
	String s7="";
	String s6="";
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html");

        // Disable the browser cache
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		PrintWriter out = response.getWriter();
		/***************************************************************************************************/
				/*                                      Check Authentication                                       */
				/***************************************************************************************************/
				HttpSession mysession=request.getSession(true);
		Object obj = mysession.getAttribute(LOGIN_SESSION_NAME);
		//String obj="superadmin";
		
		if (obj ==null)
			response.sendRedirect("../coreadmin/login.html");
		else {
			String strAdminId = "superadmin"; //obj.toString();
			String strPrmAddModDel = request.getParameter("prmAddModify");
			String invalid = request.getParameter("invalid");
        	
			if (strPrmAddModDel!=null) {
				int iPrmAddModify = Integer.parseInt(strPrmAddModDel);
				switch(iPrmAddModify) {

					case 0:
						addlayout(request, strAdminId, out,response);
						break;
					case 1:
			        		//modifylayout(request, strAdminId, out);
						break;
					case 2:
						deletelayout(request, strAdminId, out);
						break;
                    
				}
			}
	       

			/***************************************************************************************************/
					/*                                        For Date And Time										   */
					/***************************************************************************************************/
					Calendar calendar = new GregorianCalendar();
			Date trialTime = new Date();
			calendar.setTime(trialTime);

		//create array of string to hold days.
			String months[]={"January","February","March", "April", "May","June",
				"July","August","September","October","November","December"};

				String strTime = calendar.get(Calendar.HOUR)+":"+ calendar.get(Calendar.MINUTE);
				String strDate = months[calendar.get(Calendar.MONTH)]+" "+ calendar.get(Calendar.DATE)+", "+ calendar.get(Calendar.YEAR);

				/***************************************************************************************************/
						/*                                        Get Parameter Value									   */
						/***************************************************************************************************/
						String interface_id = request.getParameter("interface_id");
				String type1 = request.getParameter("type1");
				if(type1==null)
					type1="";
				String jmesaJS="\n	function onInvokeAction(id) {"+
						"\n	setExportToLimit(id, '');createHiddenInputFieldsForLimitAndSubmit(id);}"+
						"\n	function onInvokeExportAction(id) {"+
						"\n	var parameterString = createParameterStringForLimit(id);"+
						"\n	location.href = './coreadministrationv2.sysmgmt.InterfaceManagement?'+parameterString;"+
						"\n	}";
				String javaScript = " var index = 0;"+
        					
        					
						"\n function CCA1(x){"+
						"\n 	if (document.frm.checkbox[x].checked)"+
						"\n 		hL(document.frm.checkbox[x]);"+
						"\n 	else"+
						"\n 		dL(document.frm.checkbox[x]);"+
						"\n }"+
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
						"\n	function findRow(){"+
						"\n		for (var counter=0; counter<document.frm.checkbox.length; counter++) {"+
						"\n			if (document.frm.checkbox[counter].value == groupId) {"+
						"\n				rowId = counter;"+
						"\n			}"+
						"\n		}"+
						"\n		return true;"+
						"\n	}"+
						"\n"+
						"\n	function checkEntries(){"+
						"\n		var filledIn = false;"+
						"\n		var n = test();"+
						"\n		if(n==1){"+
						"\n			if (document.frm.checkbox.checked == true) {"+
						"\n				filledIn = true;"+
						"\n				index = 0;"+
						"\n			}"+
						"\n			if (filledIn == false){"+
						"\n				alert(\'You must select one Style\');"+
						"\n				return false;"+
						"\n			}"+
						"\n		}"+
						"\n		if(n>1){"+
						"\n			for (var counter=0; counter<document.frm.checkbox.length; counter++)"+
						"\n				if (document.frm.checkbox[counter].checked == true) {"+
						"\n					filledIn = true;"+
						"\n					index = counter;"+
						"\n				}"+
						"\n			if (filledIn == false){"+
						"\n				alert(\'You must select  one Style\');"+
						"\n				return false;"+
						"\n			}"+
						"\n		}"+
						"\n		return true;"+
						"\n	}"+
						"\n"+
						"\n	function addLayout_onclick() {"+
						"\n		document.frm.method=\"post\";"+
						"\n		document.frm.action = \"./coreadministrationv2.sysmgmt.InterfaceManagement?prmAddModify=0&styleid=\"+document.frm.selectedfilename.value+\"&type=\"+document.frm.type.value;"+
						"\n		document.frm.encoding = \"multipart/form-data\";"+
						"\n		document.frm.submit();"+
						"\n	}"+
						"\n	function showLayout_onclick() {"+
						"\n		var i = test();"+
						"\n		if(i==1) {"+
						"\n			document.frm.method=\"post\";"+
						"\n			document.frm.action = \"coreadministrationv2.sysmgmt.ResourceInterface?prmAddModify=0&resource_id=\"+document.frm.resource_id.value+\"&interface_id=\"+document.frm.interface_id.value+\"&type1=\"+document.frm.type1.value;"+        					    
						"\n			document.frm.encoding = \"multipart/form-data\";"+
						"\n			document.frm.submit();"+
						"\n		}"+
						"\n		if(i>1) {"+
						"\n			document.frm.method=\"post\";"+
						"\n			document.frm.action = \"coreadministrationv2.sysmgmt.ResourceInterface?prmAddModify=0&resource_id=\"+document.frm.resource_id.value+\"&interface_id=\"+document.frm.interface_id.value+\"&type1=\"+document.frm.type1.value;"+
						"\n			document.frm.encoding = \"multipart/form-data\";"+
						"\n			document.frm.submit();"+
						"\n		}"+
						"\n	}"+
						"\n"+
							
							
						"\n	function download_onclick() {"+
						"\n		var i = test();"+
						"\n		if(i==1) {"+
						"\n			document.frm.method=\"post\";"+
						"\n			document.frm.action = \"coreadministrationv2.sysmgmt.DownloadResource?prmAddModify=0&resource_id=\"+document.frm.resource_id.value+\"&interface_id=\"+document.frm.interface_id.value+\"&filename=\"+document.frm.selectedfilename.value+\"&type1=\"+document.frm.type1.value;"+
						"\n			document.frm.submit();"+
						"\n		}"+
						"\n		if(i>1) {"+
						"\n			document.frm.method=\"post\";"+
						"\n			document.frm.action = \"coreadministrationv2.sysmgmt.DownloadResource?prmAddModify=0&resource_id=\"+document.frm.resource_id.value+\"&interface_id=\"+document.frm.interface_id.value+\"&filename=\"+document.frm.selectedfilename.value+\"&type1=\"+document.frm.type1.value;"+
						"\n			document.frm.submit();"+
						"\n		}"+
						"\n	}"+
							
							
							
						"\n	function deleteLayout_onclick() {"+
						"\n		var i = test();"+
						"\n		if(i==1) {"+
						"\n			if(checkEntries()){"+
						"\n				doyou = confirm(\"Are you Sure to Delete The style?\"); //Your question."+
						"\n				if (doyou == true) {"+
						"\n				document.frm.method=\"post\";"+
							//"\n				alert(\"sumanta\");"+
						"\n					document.frm.action = \"./coreadministrationv2.sysmgmt.InterfaceManagement?prmAddModify=2&styleid=\"+document.frm.hiddenempid.value;"+
							//"\n				alert(\"sumanta11\");"+
						"\n			document.frm.encoding = \"multipart/form-data\";"+
						"\n				document.frm.submit();"+
						"\n				}"+
						"\n				else {"+
						"\n				}"+
						"\n			}"+
						"\n		}"+
						"\n		if(i>1) {"+
						"\n			for(var counter=0; counter<document.frm.checkbox.length; counter++) {"+
						"\n				if(document.frm.checkbox[counter].checked) {"+
						"\n			if(checkEntries()){"+
						"\n				doyou = confirm(\"Are you Sure to Delete The Style?\"); //Your question."+
						"\n				if (doyou == true) {"+
						"\n				document.frm.method=\"post\";"+
							//"\n				alert(\"sumanta222\");"+
						"\n				document.frm.action = \"./coreadministrationv2.sysmgmt.InterfaceManagement?prmAddModify=2&styleid=\"+document.frm.hiddenempid[counter].value;"+
							//"\n				alert(\"sumanta333\");"+
						"\n				document.frm.encoding = \"multipart/form-data\";"+
						"\n				document.frm.submit();"+
						"\n				}"+
						"\n				else {"+
						"\n				}"+
						"\n			}"+
						"\n			}"+
							
						"\n			}"+
						"\n		}"+
						"\n	}"+
						"\n"+
                            
						"\n	function validate(){"+
						"\n		if(!fnCheckNull(document.frm.styleid.value,\"Group Id\")){"+
						"\n			document.frm.styleid.focus();"+
						"\n			return false;"+
						"\n		}"+
						"\n		return true;"+
						"\n	}"+

						"\n	function type_onchange() {"+
						"\n		document.frm.method=\"post\";"+
						"\n		document.frm.action=\"coreadministrationv2.sysmgmt.ResourceInterface;\""+
						"\n		document.frm.submit();"+
						"\n	}"+	
						"\n	function checkbox_onclick() {"+
						"\n		var i = test();"+
						"\n		if(i>1) {"+
						"\n			for(var counter=0; counter<document.frm.checkbox.length; counter++) {"+
						"\n				if(document.frm.checkbox[counter].checked) {"+
						"\n				document.frm.resource_id.value = document.frm.checkbox[counter].value;"+
						"\n		   document.frm.selectedfilename.value = document.frm.hiddentitle[counter].value;"+
						"\n					break;"+
						"\n				}"+
						"\n			}"+
						"\n		}"+
						"\n		if(i==1) {"+
						"\n			document.frm.resource_id.value = document.frm.checkbox.value;"+
						"\n		   document.frm.selectedfilename.value = document.frm.hiddentitle.value;"+
						"\n		}"+
						"\n	}"+
						"\n"+
						"\n	function load() {"+
						"\n		if (window.parent.leftFrame1 != null) {"+
						"\n			window.parent.leftFrame1.location.reload();"+
						"\n		}"+
						"\n }";

       
				Input inputButton1 = new Input();
				Input inputButton2 = new Input();
				Input inputButton3 = new Input();
				Input inputButton6 = new Input();
				
				Input closeButton = new Input();
        
     //   inputButton1.setOnClick("addLayout_onclick();");
				inputButton2.setOnClick("download_onclick();");
       // inputButton3.setOnClick("deleteLayout_onclick();");
				inputButton1.setOnClick("showLayout_onclick();");
				closeButton.setOnClick("window.close();");
				

				Option[] option1 = null;
				Vector gettype = DataBaseLayer.getResourceType();
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
						Vector gettypesub = (Vector) gettype.elementAt(i);
						String  type_id = (String) gettypesub.elementAt(0);
						String type_name = (String) gettypesub.elementAt(1);
						option1[i+1] = new Option(type_id).addElement(type_name);
						if(type_id.equals(type1))
						{
							option1[i + 1].setSelected(true);
						}
					}
				}	
	    
    
				Html html = new Html()
						.addElement(new Head()
						.addElement(new Title("Resource Management"))
						.addElement(new Link()
						.setHref("../coreadmin/css/stylesheet.css")
						.setRel("stylesheet"))
						.addElement(new Script()
						.setLanguage("JavaScript")
						.setSrc("../coreadmin/js/check.js"))
						.addElement(new Script()
						.setLanguage("JavaScript")
						.addElement(javaScript)));
					
					
				Form form = new Form().setName("frm").setMethod("post");
				Body body=new Body().addElement(new TRExtension()
						.headerTable("<b>Administrator:</b> "+strAdminId, strDate, strTime, "<b>Portal Administration:</b> Resource Management"));
					           		

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
					if(type1.equals("Interfacexml"))
					{				
						sql = "select resource_id as \"Select\" ,href as \"File Name\"  from resource where interface_id='"+interface_id+"' and type='Interfacexml'";
					}
					else if(type1.equals("Resources"))
					{
						sql = "select resource_id as \"Select\" ,href as \"File Name\"  from resource where interface_id='"+interface_id+"' and type <>'Interfacexml'";
					}
					else{
						sql = "select resource_id as \"Select\" ,href as \"File Name\"  from resource where interface_id='"+interface_id+"'";
					}
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
						grid1.setCaption("");
						grid1.setMaxRowsPerPage(5);   		//how many records displayed per page
						grid1.setMaxResultPagesPerLoad(5);  //Page : 1 2 3 4 5 6 7 8 9 10 (max 10 pages displayed)
						grid1.setLineNoHeaderBgColor("#48E6F7");
						grid1.Cols(0).setFieldType(grid1.FIELD_RADIO);
						grid1.Cols(1).setFieldType(grid1.FIELD_HIDDEN);		
			
			
		
						grid1.Cols(0).setFieldName("checkbox");
						grid1.Cols(1).setFieldName("hiddentitle");
			
			
			
						grid1.Cols(0).Header().setClassID("swb");
						grid1.Cols(1).Header().setClassID("swb");
			
			
			
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
					}
					catch (Exception exp) {
						exp.printStackTrace();
					}			
					form.addElement("<input type=\"hidden\" name=\"resource_id\">");	
					form.addElement("<input type=\"hidden\" name=\"selectedfilename\">");	
					form.addElement(new Input()
													  .setType("hidden")
													  .setName("interface_id")
													  .setValue(interface_id));			
					table.addElement(new TR()
							.addElement(new TD()
							.addElement(table2)))
							.addElement(new TR()
							.addElement(new TD()
							.addElement(table1)));
					form.addElement(table);
					body.addElement(form);
					html.addElement(body.setClass("bodyadmin"));
					out.print(html.toString());
		}
	
			}
			public void doPost(HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException {
				doGet(request, response);
					}

					public void addlayout(HttpServletRequest request, String strCreatedBy, PrintWriter out1,HttpServletResponse response)
							throws IOException, ServletException {
						String interface_id=request.getParameter("interface_id");
						String resource_id=request.getParameter("resource_id");
						ResourceBundle rb = ResourceBundle.getBundle("portal",Locale.getDefault());      
						String filename="";
						String s7="";
						String key1= "xml"; 
						String photopath = rb.getString(key1);
						String path=photopath;
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
							}
							filename=multipartrequest.getFilesystemName("filename");
						}	
						catch(IOException ioexception)
						{
						}
						String getType=DataBaseLayer.getType(resource_id);
						if(getType.equals("Interfacexml"))
						{
							DataBaseLayer.insertresourceOnly(resource_id,attachmentname,s7,interface_id);
							DOMParser parser = new DOMParser();
							try
							{
														
								parser.parse(attachmentname+"interface.xml");	
								Document document1 = parser.getDocument();
								NodeList nodelist = document1.getElementsByTagName("interface");
								if(nodelist.getLength() > 0)
								{
									Element e = (Element)nodelist.item(0);
								//String interface_id = e.getAttribute("id");	
								//String interface_title = e.getAttribute("type");
									DataBaseLayer.deletefromStructureLayoutContentStyleBehaviour(interface_id);
									//////////////////////////////////////////////////////////CONFIGURATION ITEM////////////////////////////////////////
									NodeList configuration = document1.getElementsByTagName("configuration");	
									int totalconfiguration = configuration.getLength();
									for(int con=0; con<configuration.getLength() ; con++){
										Element e1 = (Element)configuration.item(con);
										String createsession = e1.getAttribute("createsession");
										String checkrole = e1.getAttribute("checkrole");	
										String contenttype = e1.getAttribute("contenttype");		
										String doctypepublic = e1.getAttribute("doctypepublic");
										String doctypesystem = e1.getAttribute("doctypesystem");		
										String cachecontrol=	e1.getAttribute("cachecontrol");
										String expires=e1.getAttribute("expires");
										String lastmod=e1.getAttribute("lastmodify");
										String template=e1.getAttribute("TemplateID");
										String themes=e1.getAttribute("ThemeID");
										String enable_chaching=e1.getAttribute("Enable_Caching");
										String cache_name=e1.getAttribute("CacheName");
										String cachedynamicjs=e1.getAttribute("CacheDynamicJS");
										String cachedynamiccss=e1.getAttribute("CacheDynamicCSS");
										String cachedynamicimage=e1.getAttribute("CacheDynamicImage");
										DataBaseLayer.insertConfigurationItem(interface_id,createsession,checkrole,contenttype,doctypepublic,doctypesystem,cachecontrol,expires,lastmod,template,themes,enable_chaching,cache_name,cachedynamicjs,cachedynamiccss,cachedynamicimage);
									
									}
									//////////////////////////////////////////////////////////CONFIGURATION ITEM END////////////////////////////////////////
									NodeList structure = document1.getElementsByTagName("structure");	
									int totalstructure = structure.getLength();
									//////////////////  STRUCTURE//////////////////////////        
									for(int s=0; s<structure.getLength() ; s++){
										NodeList part = ((Element)structure.item(s)).getElementsByTagName("part");
										int totalpart = part.getLength();	
															
										for(int d=0; d<part.getLength() ; d++){
											Element e1 = (Element)part.item(d);
											String part_id = e1.getAttribute("id");	
											String ServerCacheGrid = e1.getAttribute("ServerCacheGrid");
											String ServerCacheKeyIncludeUserId = e1.getAttribute("ServerCacheKeyIncludeUserId");
											/////////////////////////////////////////////////////////////////////////////////  FOR RADIO/// AND CHECKBOX//////////////////////////////////////////////////////
											NodeList optionitem = ((Element)part.item(d)).getElementsByTagName("option");	
											for(int x=0; x<optionitem.getLength() ;x++){
												Element x1 = (Element)optionitem.item(x);
												String option_id = x1.getAttribute("id");
												String optionname = x1.getAttribute("name");	
												String optionvalue = x1.getAttribute("value");
												DataBaseLayer.insertOptionItem(interface_id,part_id,option_id,optionname,optionvalue);
											}
											////////////////////////////////////////////////////////////////////////////////  FOR RADIO/// AND CHECKBOX//////////////////////////////////////////////////////
            
											////////////////////////////////////////////////////////////////////////////////  FOR DB FORM ELEMENT//////////////////////////////////////////////////////

											NodeList elementitem = ((Element)part.item(d)).getElementsByTagName("element");	
											for(int el=0; el<elementitem.getLength() ;el++){
												Element el1 = (Element)elementitem.item(el);
												String element_id = el1.getAttribute("id");
															/////////////////////////////////////////////////////////////////////////////////  FOR RADIO IN FORM ELEMENT//////////////////////////////////////////////////////
															NodeList formoptionitem= ((Element)elementitem.item(el)).getElementsByTagName("option");	
															for(int x=0; x<formoptionitem.getLength() ;x++)
															{
																Element x1 = (Element)formoptionitem.item(x);
																String option_id = x1.getAttribute("id");
																String optionname = x1.getAttribute("name");	
																String optionvalue = x1.getAttribute("value");
																DataBaseLayer.insertOptionItem(interface_id,element_id,option_id,optionname,optionvalue);
															}
															////////////////////////////////////////////////////////////////////////////////  FOR RADIO IN FORM ELEMENT//////////////////////////////////////////////////////
												String element_class = el1.getAttribute("class");	
												String element_type = el1.getAttribute("type");	
												String element_key = el1.getAttribute("key");	
												String tabindex=el1.getAttribute("tabindex");
												String rows=el1.getAttribute("rows");
												String cols=el1.getAttribute("cols");
												String selectindex=el1.getAttribute("selectindex");
												if(selectindex==null ||selectindex.equals(""))
													selectindex="0";
																														
												String modifyindex=el1.getAttribute("modifyindex");
												if(modifyindex==null ||modifyindex.equals(""))
													modifyindex="0";
												String insertindex=el1.getAttribute("insertindex");
												if(insertindex==null ||insertindex.equals(""))
													insertindex="0";
												String forlabel=el1.getAttribute("for");
												String required=el1.getAttribute("required");
												String minlength=el1.getAttribute("minlength");
												String maxlength=el1.getAttribute("maxlength");
												String equalto=el1.getAttribute("equalto");
												String number=el1.getAttribute("number");
												String email=el1.getAttribute("email");
												String requiredmess=el1.getAttribute("requiredmess");
												String minlengthmess=el1.getAttribute("minlengthmess");
												String maxlengthmess=el1.getAttribute("maxlengthmess");
												String equaltomess=el1.getAttribute("equaltomess");
												String numbermess=el1.getAttribute("numbermess");
												String emailmess=el1.getAttribute("emailmess");
												String element_size=el1.getAttribute("size");
												DataBaseLayer.InsertDBFormElement(interface_id,part_id,element_id,element_class,element_type,element_key,tabindex,selectindex,modifyindex,insertindex,rows,cols,forlabel,required,minlength,maxlength,equalto,number,email,requiredmess,minlengthmess,maxlengthmess,equaltomess,numbermess,emailmess,element_size);	
																									
											}
												
											NodeList addquery = ((Element)part.item(d)).getElementsByTagName("add");
											for(int addqueryel=0; addqueryel<addquery.getLength() ;addqueryel++)
											{
												NodeList insertitemquery = ((Element)addquery.item(addqueryel)).getElementsByTagName("query");	
												for(int el=0; el<insertitemquery.getLength() ;el++){
													Element el1 = (Element)insertitemquery.item(el);
													String insert_id = el1.getAttribute("id");
													String insert_sql = el1.getAttribute("sql");	
													String insert_parameter = el1.getAttribute("parameter");	
													DataBaseLayer.InsertFormInsertQuery(interface_id,part_id,insert_id,insert_sql,insert_parameter);
													
												}
											}
												
											NodeList modiquery = ((Element)part.item(d)).getElementsByTagName("modify");
											for(int modqueryel=0; modqueryel<modiquery.getLength() ;modqueryel++)
											{

												NodeList modifyitemquery = ((Element)modiquery.item(modqueryel)).getElementsByTagName("query");	
												for(int el=0; el<modifyitemquery.getLength() ;el++){
													Element el1 = (Element)modifyitemquery.item(el);
													String modify_id = el1.getAttribute("id");
													String modify_sql = el1.getAttribute("sql");	
													String modify_parameter = el1.getAttribute("parameter");	
													DataBaseLayer.ModifyFormInsertQuery(interface_id,part_id,modify_id,modify_sql,modify_parameter);
													
												}
											}
												
											NodeList deleteitemquery = ((Element)part.item(d)).getElementsByTagName("deletequery");	
											for(int el=0; el<deleteitemquery.getLength() ;el++){
												Element el1 = (Element)deleteitemquery.item(el);
												String delete_id = el1.getAttribute("id");
												String delete_sql = el1.getAttribute("sql");	
												String delete_parameter = el1.getAttribute("parameter");	
												DataBaseLayer.DeleteFormInsertQuery(interface_id,part_id,delete_id,delete_sql,delete_parameter);
													
											}
												
											NodeList selquery = ((Element)part.item(d)).getElementsByTagName("select");
											for(int selqueryel=0; selqueryel<selquery.getLength() ;selqueryel++)
											{
												NodeList selectitem = ((Element)selquery.item(selqueryel)).getElementsByTagName("query");	
												for(int el=0; el<selectitem.getLength() ;el++){
													Element el1 = (Element)selectitem.item(el);
													String select_id = el1.getAttribute("id");
													String select_sql = el1.getAttribute("sql");	
													String select_parameter = el1.getAttribute("parameter");	
													DataBaseLayer.SelectFormInsertQuery(interface_id,part_id,select_id,select_sql,select_parameter);
													
												}

											}
												
											////////////////////////////////////////////////////////////////////////////////  FOR DB FORM ELEMENT END//////////////////////////////////////////////////////
											////////////////////////////////////////////////////////////////////////////////////////SELECTOR//////////////////////////////////////////////////////////////////////////////
											NodeList selectoritem = ((Element)part.item(d)).getElementsByTagName("selector");	
											for(int u=0; u<selectoritem.getLength() ;u++){
												Element u1 = (Element)selectoritem.item(u);
												String selector_id = u1.getAttribute("id");
												String selector_class = u1.getAttribute("class");
												String domaintype = u1.getAttribute("domaintype");
												String value = u1.getAttribute("value");
												String gridrefresh = u1.getAttribute("gridrefresh");
												String influence = u1.getAttribute("influence");
												String contentid = u1.getAttribute("contentid");
												String behaviourid = u1.getAttribute("behaviourid");
												String show_choose_one = u1.getAttribute("show_choose_one");
												String choose_one_label = u1.getAttribute("choose_one_label");
												String choose_one_value = u1.getAttribute("choose_one_value");
												/////////////////////////////////////////////////////////////////////////////////  FOR DROPDOWN//////////////////////////////////////////////////////
												NodeList combooption = ((Element)selectoritem.item(u)).getElementsByTagName("selectoroption");	
												for(int xq=0; xq<combooption.getLength() ;xq++){
													Element x1 = (Element)combooption.item(xq);
													String dropdownname = x1.getAttribute("name");	
													String dropdownvalue = x1.getAttribute("value");
													DataBaseLayer.insertDropdownItem(interface_id,selector_id,dropdownname,dropdownvalue);
												}
												////////////////////////////////////////////////////////////////////////////////  FOR DROPDOWN END//////////////////////////////////////////////////////
												DataBaseLayer.addSelecetor(interface_id,selector_id,selector_class,domaintype,value,gridrefresh,influence,part_id,contentid,behaviourid,show_choose_one,choose_one_label,choose_one_value);
											}
											////////////////////////////////////////////////////////////////////////////////////////SELECTOR  END//////////////////////////////////////////////////////////////////////////////
											////////////////////////////////////////////////////////////////////////////////////  FOR  DBGrid///////////////////////////////////////////////////////////////////////////////////////
											NodeList columnitem = ((Element)part.item(d)).getElementsByTagName("column");	
											for(int x=0; x<columnitem.getLength() ;x++){
												Element x1 = (Element)columnitem.item(x);
												String col_head = x1.getAttribute("head");
												String col_name = x1.getAttribute("name");
												String col_index = x1.getAttribute("index");
												String col_width = x1.getAttribute("width");
												String col_editable = x1.getAttribute("editable");
												String hidden = x1.getAttribute("hidden");
												String key = x1.getAttribute("key");
												String required = x1.getAttribute("required");
												String minval = x1.getAttribute("minval");
												String maxval = x1.getAttribute("maxval");
												String dbcolumn=x1.getAttribute("dbcolumn");
												String gridhidden=x1.getAttribute("edithidden");
												String colinfluence=x1.getAttribute("influence");
												String col_email = x1.getAttribute("email");
												String col_number = x1.getAttribute("number");
												String custom = x1.getAttribute("custom");
												String custom_func = x1.getAttribute("custom_func");
												String default_type = x1.getAttribute("default_type");
												String default_value = x1.getAttribute("default_value");
																
												NodeList edititem = ((Element)columnitem.item(x)).getElementsByTagName("edit");	
												for(int z=0; z<edititem.getLength() ;z++){
													Element xz1 = (Element)edititem.item(z);
													String type = xz1.getAttribute("type");
													String size = xz1.getAttribute("size");
													String rows = xz1.getAttribute("rows");
													String cols = xz1.getAttribute("cols");
													String editdomaintype = xz1.getAttribute("editdomaintype");
													String value=xz1.getAttribute("value");
													String multiple = xz1.getAttribute("multiple");
													DataBaseLayer.setEditOption(interface_id,part_id,col_name,type,size,rows,cols,editdomaintype,value,multiple);
												}
												DataBaseLayer.setColModel(interface_id,part_id,col_head,col_name,col_index,col_width,col_editable,hidden,key,required,minval,maxval,dbcolumn,gridhidden,colinfluence,col_email,col_number,custom,custom_func,default_type,default_value);
											}

											NodeList keycolumnitem = ((Element)part.item(d)).getElementsByTagName("keycolumn");	
											for(int x=0; x<keycolumnitem.getLength() ;x++){
												Element x1 = (Element)keycolumnitem.item(x);
												String keycolumn_value = x1.getAttribute("value");
												DataBaseLayer.InsertkeyColumn(interface_id,part_id,keycolumn_value);
											}
											NodeList deleteitem = ((Element)part.item(d)).getElementsByTagName("delete");	
											for(int x=0; x<deleteitem.getLength() ;x++){
												/////////////////////////////////////////////////////////////////////////VALIDATIONS////////////////////////////////////////////////////////////////////////////
												NodeList validationsitem = ((Element)deleteitem.item(x)).getElementsByTagName("validations");	
												for(int va=0; va<validationsitem.getLength() ;va++)
												{
													NodeList validationitem = ((Element)validationsitem.item(va)).getElementsByTagName("validation");	
													for(int y=0; y<validationitem.getLength() ;y++)
													{
														Element validation = (Element)validationitem.item(y);
														String validationtype=validation.getAttribute("type");
														NodeList validationqueryitem = ((Element)validationitem.item(y)).getElementsByTagName("validationquery");	
														for(int z=0; z<validationqueryitem.getLength() ;z++)
														{
															Element x1 = (Element)validationqueryitem.item(z);
															String id_value = x1.getAttribute("id");
															String sql_value = x1.getAttribute("sql");
															if(validationtype.equals("custom"))
															{
																sql_value="";
															}
															else
															{
																try
																{
																sql_value = sql_value.substring(sql_value.lastIndexOf("[")+1,sql_value.indexOf("]"));
																}catch(Exception e8){}
															}			
																								
															String parameter_value = x1.getAttribute("parameter");
															String message= x1.getAttribute("message");
															String function_name=x1.getAttribute("classname");
															DataBaseLayer.InsertValidationDeleteitem(interface_id,part_id,validationtype,id_value,sql_value,parameter_value,message,function_name);
																								
														}
																
															
													}
												}
												/////////////////////////////////////////////////////////////////////////VALIDATIONS////////////////////////////////////////////////////////////////////////////
												NodeList queriesitem = ((Element)deleteitem.item(x)).getElementsByTagName("queries");	
												for(int y=0; y<queriesitem.getLength() ;y++){
													NodeList queryitem = ((Element)queriesitem.item(y)).getElementsByTagName("query");	
													for(int z=0; z<queryitem.getLength() ;z++){
			                                                                                  		 	
														Element x1 = (Element)queryitem.item(z);
														String id_value = x1.getAttribute("id");
														String sql_value = x1.getAttribute("sql");
														try
														{
														sql_value = sql_value.substring(sql_value.lastIndexOf("[")+1,sql_value.indexOf("]")); 
														}catch(Exception e2){}
														System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>sql_value>>>>>>>>>>>>>>>>>>>>>>>>>>>"+sql_value);
														String parameter_value = x1.getAttribute("parameter");
														DataBaseLayer.InsertDeleteitem(interface_id,part_id,id_value,sql_value,parameter_value);
			                                                                                  		 	
													}
                                                                                  		 	
                                                                                  		 	
												}
												NodeList actionitem = ((Element)deleteitem.item(x)).getElementsByTagName("action");	
												for(int y1=0; y1<actionitem.getLength() ;y1++)
												{
													Element x1 = (Element)actionitem.item(y1);
													String sequence = x1.getAttribute("sequence");
													String actionname = x1.getAttribute("actionname");
													DataBaseLayer.Insertdelete_action_param(interface_id,part_id,sequence,actionname);
															
												}
                                                                                   	
											}

											NodeList additem = ((Element)part.item(d)).getElementsByTagName("add");	
											for(int x=0; x<additem.getLength() ;x++){
												/////////////////////////////////////////////////////////////////////////VALIDATIONS////////////////////////////////////////////////////////////////////////////
												NodeList validationsitem = ((Element)additem.item(x)).getElementsByTagName("validations");	
												for(int va=0; va<validationsitem.getLength() ;va++)
												{
													NodeList validationitem = ((Element)validationsitem.item(va)).getElementsByTagName("validation");	
													for(int y=0; y<validationitem.getLength() ;y++)
													{
														Element validation = (Element)validationitem.item(y);
														String validationtype=validation.getAttribute("type");
														NodeList validationqueryitem = ((Element)validationitem.item(y)).getElementsByTagName("validationquery");	
														for(int z=0; z<validationqueryitem.getLength() ;z++)
														{
															Element x1 = (Element)validationqueryitem.item(z);
															String id_value = x1.getAttribute("id");
															String sql_value = x1.getAttribute("sql");
															if(validationtype.equals("custom"))
															{
																sql_value="";
															}
															else
															{
																try
																{		
																		sql_value = sql_value.substring(sql_value.lastIndexOf("[")+1,sql_value.indexOf("]"));
																}catch(Exception e3){}
															}			
																								
															String parameter_value = x1.getAttribute("parameter");
															String message= x1.getAttribute("message");
															String function_name=x1.getAttribute("classname");
															DataBaseLayer.InsertValidationAdditem(interface_id,part_id,validationtype,id_value,sql_value,parameter_value,message,function_name);
																								
														}
																
															
													}
												}
												/////////////////////////////////////////////////////////////////////////VALIDATIONS////////////////////////////////////////////////////////////////////////////
												NodeList queriesitem = ((Element)additem.item(x)).getElementsByTagName("queries");	
												for(int y=0; y<queriesitem.getLength() ;y++){
													NodeList queryitem = ((Element)queriesitem.item(y)).getElementsByTagName("query");	
													for(int z=0; z<queryitem.getLength() ;z++){
			                                                                                  		 	
														Element x1 = (Element)queryitem.item(z);
														String id_value = x1.getAttribute("id");
														String sql_value = x1.getAttribute("sql");
														try{
														sql_value = sql_value.substring(sql_value.lastIndexOf("[")+1,sql_value.indexOf("]"));
														}catch(Exception e4){} 

														System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>sql_value>>>>>>>>>>>>>>>>>>>>>>>>>>>"+sql_value);
														String parameter_value = x1.getAttribute("parameter");
														DataBaseLayer.InsertAdditem(interface_id,part_id,id_value,sql_value,parameter_value);
			                                                                                  		 	
													}
                                                                                  		 	
												}
												NodeList actionitem = ((Element)additem.item(x)).getElementsByTagName("action");	
												for(int y1=0; y1<actionitem.getLength() ;y1++)
												{
													Element x1 = (Element)actionitem.item(y1);
													String sequence = x1.getAttribute("sequence");
													String actionname = x1.getAttribute("actionname");
													DataBaseLayer.Insertadd_action_param(interface_id,part_id,sequence,actionname);
															
												}
                                                                                 
											}

											NodeList modifyitem = ((Element)part.item(d)).getElementsByTagName("modify");	
											for(int x=0; x<modifyitem.getLength() ;x++){
												/////////////////////////////////////////////////////////////////////////VALIDATIONS////////////////////////////////////////////////////////////////////////////
												NodeList validationsitem = ((Element)modifyitem.item(x)).getElementsByTagName("validations");	
												for(int va=0; va<validationsitem.getLength() ;va++)
												{
													NodeList validationitem = ((Element)validationsitem.item(va)).getElementsByTagName("validation");	
													for(int y=0; y<validationitem.getLength() ;y++)
													{
														Element validation = (Element)validationitem.item(y);
														String validationtype=validation.getAttribute("type");
														NodeList validationqueryitem = ((Element)validationitem.item(y)).getElementsByTagName("validationquery");	
														for(int z=0; z<validationqueryitem.getLength() ;z++)
														{
															Element x1 = (Element)validationqueryitem.item(z);
															String id_value = x1.getAttribute("id");
															String sql_value = x1.getAttribute("sql");
															if(validationtype.equals("custom"))
															{
																sql_value="";
															}
															else
															{
																try
																{
																sql_value = sql_value.substring(sql_value.lastIndexOf("[")+1,sql_value.indexOf("]"));
																}catch(Exception e5){}
															}			
																								
															String parameter_value = x1.getAttribute("parameter");
															String message= x1.getAttribute("message");
															String function_name=x1.getAttribute("classname");
															DataBaseLayer.InsertValidationModifyitem(interface_id,part_id,validationtype,id_value,sql_value,parameter_value,message,function_name);
																								
														}
																
															
													}
												}
												/////////////////////////////////////////////////////////////////////////VALIDATIONS////////////////////////////////////////////////////////////////////////////
												NodeList queriesitem = ((Element)modifyitem.item(x)).getElementsByTagName("queries");	
												for(int y=0; y<queriesitem.getLength() ;y++){
													NodeList queryitem = ((Element)queriesitem.item(y)).getElementsByTagName("query");	
													for(int z=0; z<queryitem.getLength() ;z++){
			                                                                                  		 	
														Element x1 = (Element)queryitem.item(z);
														String id_value = x1.getAttribute("id");
														String sql_value = x1.getAttribute("sql");
														try
														{
														sql_value = sql_value.substring(sql_value.lastIndexOf("[")+1,sql_value.indexOf("]"));
														}catch(Exception e6){} 

														System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>sql_value>>>>>>>>>>>>>>>>>>>>>>>>>>>"+sql_value);
														String parameter_value = x1.getAttribute("parameter");
														DataBaseLayer.InsertModifyitem(interface_id,part_id,id_value,sql_value,parameter_value);
			                                                                                  		 	
													}
                                                                                  		 	                                                                                  		 	
												}
												NodeList actionitem = ((Element)modifyitem.item(x)).getElementsByTagName("action");	
												for(int y1=0; y1<actionitem.getLength() ;y1++)
												{
													Element x1 = (Element)actionitem.item(y1);
													String sequence = x1.getAttribute("sequence");
													String actionname = x1.getAttribute("actionname");
													DataBaseLayer.Insertmodify_action_param(interface_id,part_id,sequence,actionname);
															
												}		
                                                                                   
											}
											NodeList queryitem = ((Element)part.item(d)).getElementsByTagName("loadquery");	
											for(int x=0; x<queryitem.getLength() ;x++){
												Element x1 = (Element)queryitem.item(x);
												String query_value = x1.getAttribute("value");
												String load_parameter = x1.getAttribute("parameter");
												DataBaseLayer.InsertQueryitem(interface_id,part_id,query_value,load_parameter,ServerCacheGrid,ServerCacheKeyIncludeUserId);
										   }
											////////////////////////////////////////////////////////////////////////////////////  FOR  DBGrid   END//////////////////////////////////////////////////////////////////////////////
											String part_class = e1.getAttribute("class");
											String resize = e1.getAttribute("resize");
											String border = e1.getAttribute("border");
											String cols = e1.getAttribute("cols");
											String rows = e1.getAttribute("rows");
											String scrolling = e1.getAttribute("scrolling");
											String spacing = e1.getAttribute("spacing");
											String colspan = e1.getAttribute("colspan");
											String maxlength = e1.getAttribute("length");
											String size = e1.getAttribute("size");
											String tabindex = e1.getAttribute("tabindex");
											String archieve = e1.getAttribute("archieve");
											String codebase = e1.getAttribute("codebase");
											String mayscript = e1.getAttribute("mayscript");
											String loadurl = e1.getAttribute("loadurl");
											String editurl = e1.getAttribute("editurl");
											String caption = e1.getAttribute("caption");
											String sortname = e1.getAttribute("sortname");
											String sortorder = e1.getAttribute("sortorder");
											String treedataremotefunction = e1.getAttribute("treedataremotefunction");
											String onselectremotefunction = e1.getAttribute("onselectremotefunction");
											String autocollapse = e1.getAttribute("autocollapse");
											String initialiseonload = e1.getAttribute("initialiseonload");
											String gridhidden = e1.getAttribute("gridhidden");
											String gridnavbar = e1.getAttribute("gridnavbar");
											String jscontrol=e1.getAttribute("jscontrol");
											String formaction=e1.getAttribute("formaction");
											String formmethod=e1.getAttribute("formmethod");
											String grid_multiselect = e1.getAttribute("multiselect");
											String rownum = e1.getAttribute("rownum");
											String rowlist = e1.getAttribute("rowlist");
											String dateformat = e1.getAttribute("dateformat");
											String tree_lazynode = e1.getAttribute("lazynode");
											String tree_tooltip= e1.getAttribute("tooltip");
											String grid_multiboxonly = e1.getAttribute("multiboxonly");
											String tree_parentquery = e1.getAttribute("parentquery");
											String tree_childquery = e1.getAttribute("childquery");
											String tree_parameter = e1.getAttribute("parameter");
											String resetSearchOnClose = e1.getAttribute("resetSearchOnClose");
											String multiplesearch = e1.getAttribute("multiplesearch");
											String customeditbutton=e1.getAttribute("CustomEditButton");

											String griddata = e1.getAttribute("data");
											String griddatatype = e1.getAttribute("datatype");
											DataBaseLayer.insertpart(interface_id,part_id,part_class,resize,border,cols,rows,scrolling,spacing,colspan,maxlength,size,tabindex,archieve,codebase,mayscript,loadurl,editurl,caption,sortname,sortorder,treedataremotefunction,onselectremotefunction,autocollapse,initialiseonload,gridhidden,gridnavbar,grid_multiselect,rownum,rowlist,dateformat,tree_lazynode,tree_tooltip,grid_multiboxonly,tree_parentquery,tree_childquery,tree_parameter,resetSearchOnClose,multiplesearch,customeditbutton,griddata,griddatatype);
											DataBaseLayer.insertformelement(interface_id,part_id,jscontrol,formaction,formmethod);
											//////////////////////////////////FOR STATIC TREE /////////////////////////
											NodeList treedataitem = ((Element)part.item(d)).getElementsByTagName("treedata");
											if(treedataitem.getLength()>0)
											{	
												for(int tr=0; tr<treedataitem.getLength() ;tr++)
												{
													Element treedatanode = (Element)treedataitem.item(tr);
													Document doc111= new DocumentImpl();
													Node nd1 =doc111.importNode(treedatanode, true);
													doc111.appendChild(nd1); 
													OutputFormat  format1  = new OutputFormat( doc111 );         
													StringWriter  stringOut1 = new StringWriter(); 
													XMLSerializer    serial1 = new XMLSerializer( stringOut1, format1 );
													serial1.asDOMSerializer();   
													serial1.serialize( doc111.getDocumentElement() );
													String xml11= stringOut1.toString();
													DataBaseLayer.InsertTreeDataitem(interface_id,part_id,xml11);
												}
											}
																
											//////////////////////////////////FOR STATIC TREE END/////////////////////////										
										}
														
																
									}
															
									///////////////////STRUCTURE/////////////////////////       
									////////////////////LAYOUT/////////////////////////////
									NodeList layout = document1.getElementsByTagName("layout");
									for(int a=0; a<layout.getLength() ; a++){  
										Element layoutelement = (Element)layout.item(a);
										String layout_id=layoutelement.getAttribute("id");		
						
										NodeList part = ((Element)layout.item(a)).getElementsByTagName("part");
										for(int c=0; c<part.getLength() ; c++){
												
											Element e2 = (Element)part.item(c);
											String Pnodename1=part.item(c).getParentNode().getNodeName();
												
											String part_id = e2.getAttribute("id");	
											String height = e2.getAttribute("height");	
											String width = e2.getAttribute("width");	
											String left = e2.getAttribute("left");	
											String top = e2.getAttribute("top");	
											String position = e2.getAttribute("position");	
											String parent_id = e2.getAttribute("parent_id");
											DataBaseLayer.insertlayout(layout_id,part_id,height,width,left,top,position,parent_id,interface_id);			
										}
											
									}
									//////////////////LAYOUT//////////////////////////////	
							
									//////////////////CONTENT//////////////////////////////	
									NodeList content = document1.getElementsByTagName("content");	
									for(int d=0; d<content.getLength() ; d++){  
										Element contentelement = (Element)content.item(d);
										String content_id=contentelement.getAttribute("id");	
										NodeList part = ((Element)content.item(d)).getElementsByTagName("part");	
										for(int z=0; z<part.getLength() ; z++){
											Element e2 = (Element)part.item(z);	
											String part_id = e2.getAttribute("id");	
											String value = e2.getAttribute("value");	
											String valuetype = e2.getAttribute("valuetype");
											String contentlocation = e2.getAttribute("contentlocation");
											String show_choose_one = e2.getAttribute("show_choose_one");
											String choose_one_label = e2.getAttribute("choose_one_label");
											String choose_one_value = e2.getAttribute("choose_one_value");
											/////////////////////////////////////////////////////////////////////////////////  FOR DROPDOWN//////////////////////////////////////////////////////
											NodeList combooption = ((Element)part.item(z)).getElementsByTagName("combooption");	
											for(int xq=0; xq<combooption.getLength() ;xq++){
												Element x1 = (Element)combooption.item(xq);
												String dropdownname = x1.getAttribute("name");	
												String dropdownvalue = x1.getAttribute("value");
												DataBaseLayer.insertDropdownItem(interface_id,part_id,dropdownname,dropdownvalue);
											}
											////////////////////////////////////////////////////////////////////////////////  FOR DROPDOWN END//////////////////////////////////////////////////////
	
											
											
											String classtype=DataBaseLayer.getClass(interface_id,part_id);
											/////////////////////////////////////////////////////////////////////////////////  FOR CHART ONLY//////////////////////////////////////////////////////
											if(classtype.equals("chart"))
											{
												NodeList chartstructurelist = ((Element)part.item(z)).getElementsByTagName("chartstructure");	
												for(int chartlist=0; chartlist<chartstructurelist.getLength() ;chartlist++)
												{
													NodeList matric_titlelist = ((Element)chartstructurelist.item(chartlist)).getElementsByTagName("matric_title");	
													Element matric_title_element = (Element)matric_titlelist.item(0);
													String matric_title=matric_title_element.getTextContent();
																	
													NodeList descriptionlist = ((Element)chartstructurelist.item(chartlist)).getElementsByTagName("description");	
													Element description_element = (Element)descriptionlist.item(0);
													String description=description_element.getTextContent();
																	
													NodeList X_axis_querylist = ((Element)chartstructurelist.item(chartlist)).getElementsByTagName("X_axis_query");	
													Element X_axis_query_element = (Element)X_axis_querylist.item(0);
													String X_axis_query=X_axis_query_element.getTextContent();
																	
													NodeList series1_querylist = ((Element)chartstructurelist.item(chartlist)).getElementsByTagName("series1_query");	
													Element series1_query_element = (Element)series1_querylist.item(0);
													String series1_query=series1_query_element.getTextContent();
																	
													NodeList series2_querylist = ((Element)chartstructurelist.item(chartlist)).getElementsByTagName("series2_query");	
													Element series2_query_element = (Element)series2_querylist.item(0);
													String series2_query=series2_query_element.getTextContent();
																	
													NodeList series3_querylist = ((Element)chartstructurelist.item(chartlist)).getElementsByTagName("series3_query");	
													Element series3_query_element = (Element)series3_querylist.item(0);
													String series3_query=series3_query_element.getTextContent();
																	
													NodeList legenddata1list = ((Element)chartstructurelist.item(chartlist)).getElementsByTagName("legenddata1");	
													Element legenddata1_element = (Element)legenddata1list.item(0);
													String legenddata1=legenddata1_element.getTextContent();
																	
													NodeList legenddata2list = ((Element)chartstructurelist.item(chartlist)).getElementsByTagName("legenddata2");	
													Element legenddata2_element = (Element)legenddata2list.item(0);
													String legenddata2=legenddata2_element.getTextContent();
																	
													NodeList legenddata3list = ((Element)chartstructurelist.item(chartlist)).getElementsByTagName("legenddata3");	
													Element legenddata3_element = (Element)legenddata3list.item(0);
													String legenddata3=legenddata3_element.getTextContent();
																	
													NodeList chart_typelist = ((Element)chartstructurelist.item(chartlist)).getElementsByTagName("chart_type");	
													Element chart_type_element = (Element)chart_typelist.item(0);
													String chart_type=chart_type_element.getTextContent();
																	
													NodeList subtypelist = ((Element)chartstructurelist.item(chartlist)).getElementsByTagName("subtype");	
													Element subtype_element = (Element)subtypelist.item(0);
													String subtype=subtype_element.getTextContent();
																	
													NodeList widthlist = ((Element)chartstructurelist.item(chartlist)).getElementsByTagName("width");	
													Element width_element = (Element)widthlist.item(0);
													String width=width_element.getTextContent();
																	
													NodeList heightlist = ((Element)chartstructurelist.item(chartlist)).getElementsByTagName("height");	
													Element height_element = (Element)heightlist.item(0);
													String  height=height_element.getTextContent();
																	
													NodeList yaxis_labellist = ((Element)chartstructurelist.item(chartlist)).getElementsByTagName("yaxis_label");	
													Element yaxis_label_element = (Element)yaxis_labellist.item(0);
													String yaxis_label=yaxis_label_element.getTextContent();
																	
													NodeList colorlist = ((Element)chartstructurelist.item(chartlist)).getElementsByTagName("color");	
													Element color_element = (Element)colorlist.item(0);
													String color=color_element.getTextContent();
																	
													NodeList transposelist = ((Element)chartstructurelist.item(chartlist)).getElementsByTagName("transpose");	
													Element transpose_element = (Element)transposelist.item(0);
													String transpose=transpose_element.getTextContent();
																	
													NodeList stackedlist = ((Element)chartstructurelist.item(chartlist)).getElementsByTagName("stacked");	
													Element stacked_element = (Element)stackedlist.item(0);
													String stacked=stacked_element.getTextContent();
																	
													NodeList chart_dimensionlist = ((Element)chartstructurelist.item(chartlist)).getElementsByTagName("chart_dimension");	
													Element chart_dimension_element = (Element)chart_dimensionlist.item(0);
													String chart_dimension=chart_dimension_element.getTextContent();
																	
													NodeList xaxis_labellist = ((Element)chartstructurelist.item(chartlist)).getElementsByTagName("xaxis_label");	
													Element xaxis_label_element = (Element)xaxis_labellist.item(0);
													String xaxis_label=xaxis_label_element.getTextContent();
													DataBaseLayer.insertChart(interface_id,part_id,content_id,matric_title,description,X_axis_query,series1_query,series2_query,series3_query,legenddata1,legenddata2,legenddata3,chart_type,subtype,width,height,yaxis_label,color,transpose,stacked,chart_dimension,xaxis_label);
																	
												}
											}
											////////////////////////////////////////////////////////////////////////////////  FOR CHART ONLY//////////////////////////////////////////////////////
											
										   /////////////////////////////////////////////////////FOR REPORT ONLY//////////////////////////////////////////////////////////////////////////////////
											
											if(classtype.equals("report") || classtype.equals("reportwindow"))
											{
													String viewer_type = e2.getAttribute("viewer_type");
													String report_chooser = e2.getAttribute("report_chooser");
													String report_executer = e2.getAttribute("report_executer");
													String rpt_file = e2.getAttribute("rpt_file");
													NodeList report_parameters_Node_list = ((Element)part.item(z)).getElementsByTagName("report_parameters");	
													for(int parameters_node_list=0; parameters_node_list<report_parameters_Node_list.getLength() ;parameters_node_list++)
													{
															NodeList report_parameter_list = ((Element)report_parameters_Node_list.item(parameters_node_list)).getElementsByTagName("report_parameter");	
															for(int parameter_list=0; parameter_list<report_parameter_list.getLength() ;parameter_list++)
															{
																Element report_parameter_element = (Element)report_parameter_list.item(parameter_list);
																String parameter_name = report_parameter_element.getAttribute("name");
																String parameter_value = report_parameter_element.getAttribute("value");
																String valuesourceitemname=report_parameter_element.getAttribute("valuesourceitemname");
																String parameter_value_type=report_parameter_element.getAttribute("value_type");
																DataBaseLayer.AddReportParameter(interface_id,part_id,content_id,parameter_name,parameter_value,valuesourceitemname,parameter_value_type);

															}
														
													}
													DataBaseLayer.ReportContent(interface_id,part_id,content_id,viewer_type,report_chooser,report_executer,rpt_file);
											}
											////////////////////////////////////////////////////FOR REPORT ONLY/////////////////////////////////////////////////////////////////////////////////
											///////////////////////////////////////////////// FOR FLASH COMPONENT///////////////////////
											if(classtype.equals("flashcomponent"))
											{
												NodeList flashcomponent_Node_list = ((Element)part.item(z)).getElementsByTagName("parameters");	
												for(int parameters_node_list=0; parameters_node_list<flashcomponent_Node_list.getLength() ;parameters_node_list++)
												{
													NodeList flashcomponent_parameter_list = ((Element)flashcomponent_Node_list.item(parameters_node_list)).getElementsByTagName("parameter");	
													for(int parameter_list=0; parameter_list<flashcomponent_parameter_list.getLength() ;parameter_list++)
													{
														Element flashcomponent_parameter_element = (Element)flashcomponent_parameter_list.item(parameter_list);
														String parameter_name = flashcomponent_parameter_element.getAttribute("name");
														String parameter_value = flashcomponent_parameter_element.getAttribute("value");
														DataBaseLayer.AddFlashParameter(interface_id,part_id,content_id,parameter_name,parameter_value);														
													}
												
												}
									      
											}
											///////////////////////////////////////////////// FOR FLASH COMPONENT///////////////////////	
											
											
											if(valuetype.equals("cdata"))
											{
												NodeList fstNm = e2.getChildNodes();
												value=(fstNm.item(0)).getNodeValue();	
												DataBaseLayer.insertcontentcdata(content_id,part_id,value,valuetype,interface_id,contentlocation);
												//DataBaseLayer.insertcontent(content_id,part_id,value,valuetype,interface_id,contentlocation,show_choose_one,choose_one_label,choose_one_value);	
					
											}
											else
											{
												DataBaseLayer.insertcontent(content_id,part_id,value,valuetype,interface_id,contentlocation,show_choose_one,choose_one_label,choose_one_value);	
											}			
										}
									}
									//////////////////CONTENT//////////////////////////////	
							
									//////////////////STYLE//////////////////////////////	
									NodeList style = document1.getElementsByTagName("style");	
									for(int f=0; f<style.getLength() ; f++){  
										Element styleelement = (Element)style.item(f);
										String style_id=styleelement.getAttribute("id");	
										NodeList part = ((Element)style.item(f)).getElementsByTagName("part");	
										for(int g=0; g<part.getLength() ; g++){
											Element e2 = (Element)part.item(g);	
											String part_id = e2.getAttribute("id");	
											String value = e2.getAttribute("value");	
											String valuetype = e2.getAttribute("valuetype");	
											//System.out.println("..................................... VALUE.............."+value);
											if(valuetype.equals("inline"))
											{
												if(value==null || value.equals(""))
												{
																
												}
												else
												{ 	
													try
													{
													  value = value.substring(value.lastIndexOf("[")+1,value.indexOf("]"));
													}catch(Exception e1){} 
												}
															
											}
														
											DataBaseLayer.insertstyle(style_id,part_id,value,valuetype,interface_id);	
										}
									}
									//////////////////STYLE//////////////////////////////	
							
									//////////////////BEHAVIOUR//////////////////////////////	
									NodeList behaviour = document1.getElementsByTagName("behaviour");	
									for(int h=0; h<behaviour.getLength() ; h++){  
										Element behaviourelement = (Element)behaviour.item(h);
										String behaviour_id=behaviourelement.getAttribute("id");	
										NodeList part = ((Element)behaviour.item(h)).getElementsByTagName("part");	
										for(int i=0; i<part.getLength() ; i++){
											Element e2 = (Element)part.item(i);	
											String part_id = e2.getAttribute("id");	
											NodeList event = ((Element)part.item(i)).getElementsByTagName("event");	
											for(int q=0; q<event.getLength() ; q++){
												Element e3 = (Element)event.item(q);	
												String type = e3.getAttribute("type");
												String value = e3.getAttribute("function");	
												String valuetype = e3.getAttribute("valuetype");
												String target = e3.getAttribute("target");	
												String event1 = e3.getAttribute("name");
												String resourceid = e3.getAttribute("resourceid");
												String callback = e3.getAttribute("callback");
												String javaclass = e3.getAttribute("javaclass");
												String packagename=e3.getAttribute("package");
												String resourcelocation=e3.getAttribute("resourcelocation");
												//String dom_ready=e3.getAttribute("dom_ready");
												DataBaseLayer.insertbehaviour(behaviour_id,part_id,value,valuetype,target,event1,interface_id,type,resourceid,callback,javaclass,packagename,resourcelocation);
											}	
										}
									}
									//////////////////BEHAVIOUR//////////////////////////////	
									//////////////////////PAGE CREATION//////////////////////////
									Vector toltallayout=DataBaseLayer.getLayout_ID(interface_id);
									String htmlString="";
									for(int aq=0;aq<toltallayout.size();aq=aq+4)
									{
										String layout_id=(String)toltallayout.elementAt(aq);
										String content_id=(String)toltallayout.elementAt(aq+1);
										String behaviour_id=(String)toltallayout.elementAt(aq+2);
										String style_id=(String)toltallayout.elementAt(aq+3);
										DisplayEngine de = new DisplayEngine();
										de.createStructure(interface_id,layout_id,content_id,behaviour_id,style_id,request);
										try{
											htmlString=de.show(response);

										}catch(Exception e1){
											e1.printStackTrace();
										}
										//DataBaseLayer.insetHTML(interface_id,layout_id,content_id,behaviour_id,style_id,attachmentname+"inreface.html");
										DataBaseLayer.insetHTML(interface_id,layout_id,content_id,behaviour_id,style_id,htmlString);

									}
											
									//////////////////////PAGE CREATION//////////////////////////										
								}	
								
							}
							catch (SAXException e) {
										
							} 
							catch (IOException e1) {
										
							} 
						}
						else
						{
							DataBaseLayer.insertresourceOnly(resource_id,attachmentname,s7,interface_id);
						}


							}

							public void deletelayout(HttpServletRequest request, String strCreatedBy, PrintWriter out1)
									throws IOException, ServletException {
      
									}



   
}