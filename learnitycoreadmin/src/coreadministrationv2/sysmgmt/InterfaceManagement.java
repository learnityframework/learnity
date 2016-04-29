		package coreadministrationv2.sysmgmt;
		
		/**
		* Title:        Learnity Interface Management   
		* Description:
		* Copyright:    Copyright (c) 2010
		* Company:      Aunwesha
		* @author Shibaji Chatterjee
		*/
		
		
		//import org.htmlparser.util.*;
		import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
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
import comv2.aunwesha.lfutil.Pair;

import coreadministrationv2.dbconnection.DataBaseLayer;
import coreadministrationv2.sysmgmt.xml.dao.ManifestDao;
import coreadministrationv2.utility.TableExtension;
//import oracle.xml.parser.v2.*;
		
/*import org.htmlparser.Parser;*/
// import org.htmlparser.tags.BodyTag;
//import jmesa.*;
		
//import javax.xml.parsers.DocumentBuilder;
		//import javax.xml.parsers.DocumentBuilderFactory;
		//import javax.xml.parsers.ParserConfigurationException;
		
		
		
		public class InterfaceManagement extends HttpServlet {
		
			/**
			 * 
			 */
			private static final long serialVersionUID = 636145714070029990L;

			private static final String INTERFACE_COLLECTION_TYPE = "InterfaceCollection";

			public static final String INTERFACE_TYPE = "Interface";
			
			private static final String REFRESH_ALL_TYPE = "REFRESH_ALL";

			public static final String INTERFACE_FRAGMENT_TYPE = "InterfaceFragment";

			private static final String LOGIN_SESSION_NAME = "ADMIN_LOG_ON";
				//public static final SimpleLogger log = new SimpleLogger(InterfaceManagement.class, true);
			
			private static final String LINE_SEPERATOR="<br/>";
			
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
				//Object obj = mysession.getAttribute(LOGIN_SESSION_NAME);
				String obj="superadmin";
				String statusMessage="";
				if (obj ==null || mysession.getAttribute(LOGIN_SESSION_NAME)==null)
					response.sendRedirect("../coreadmin/login.html");
				else {
					String loggedInUserId = mysession.getAttribute(LOGIN_SESSION_NAME).toString();
					String strAdminId = "superadmin"; //obj.toString();
					String strPrmAddModDel = request.getParameter("prmAddModify");
					String invalid = request.getParameter("invalid");
					
					if (strPrmAddModDel!=null) {
						int iPrmAddModify = Integer.parseInt(strPrmAddModDel);
						switch(iPrmAddModify) {
		
							case 0:
								statusMessage=addlayout(request, response,strAdminId, out,loggedInUserId);
								break;
							case 1:
									//modifylayout(request, strAdminId, out);
								break;
							case 2:
								statusMessage=deletelayout(request, strAdminId, out);
								break;
							case 3:
								statusMessage=updateTemplateConf(request, response, out,loggedInUserId);
								break;
							case 4:
								statusMessage=deleteAll();
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
								//    String strGroupId = request.getParameter("group_id");
								//    String strUserId = request.getParameter("user_id");
								//  String iPrmAddModify = request.getParameter("prmAddModify");
		// 						String jmesaJS="\n	function onInvokeAction(id) {"+
		// 						"\n	setExportToLimit(id, '');createHiddenInputFieldsForLimitAndSubmit(id);}"+
		// 						"\n	function onInvokeExportAction(id) {"+
		// 						"\n	var parameterString = createParameterStringForLimit(id);"+
		// 						"\n	location.href = './coreadministrationv2.sysmgmt.InterfaceManagement?'+parameterString;"+
		// 						"\n	}";
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
								"\n            if(document.frm.type.value=='0' || document.frm.type.value==' '){"+
								"\n            alert('Please select Type');"+
								"\n             return false;"+
								"\n	         }"+
								"\n            else{"+
								"\n		document.frm.method=\"post\";"+
								"\n		document.frm.action = \"./coreadministrationv2.sysmgmt.InterfaceManagement?prmAddModify=0&interface_id=\"+document.frm.interface_id.value+\"&type=\"+document.frm.type.value;"+
								"\n		document.frm.encoding = \"multipart/form-data\";"+
								"\n     document.frm.target=\"bodyFrame\";"+
								"\n		document.frm.submit();"+
								"\n	}"+
								"\n	}"+
								"\n	function showLayout_onclick() {"+
								"\n		var i = test();"+
								"\n		if(i==1) {"+
								"\n         if(document.frm.type.value=='InterfaceFragment' || document.frm.type.value=='Interface'){"+
								"\n					document.frm.method=\"post\";"+
								"\n			        document.frm.action = \"coreadministrationv2.sysmgmt.ResourceInterface?interface_id=\"+document.frm.interface_id.value;"+
								"\n		        	window.open(\"\",\"new11\",\"width=700,height=460,status=yes,scrollbars=no,resizable=no,toolbar=no,menubar=no\");"+
								"\n                 document.frm.target=\"new11\";"+
								"\n			        document.frm.submit();"+
								"\n			}"+
								"\n           	else{"+
								"\n			        alert(\"Please select an 'Interface' or 'Interface Fragment'\");"+
								"\n			}"+
								"\n		}"+
								"\n			if(i>1) {"+
								"\n         if(document.frm.type.value=='InterfaceFragment' || document.frm.type.value=='Interface'){"+
								"\n					document.frm.method=\"post\";"+
								"\n			        document.frm.action = \"coreadministrationv2.sysmgmt.ResourceInterface?interface_id=\"+document.frm.interface_id.value;"+
								"\n		        	window.open(\"\",\"new11\",\"width=700,height=460,status=yes,scrollbars=no,resizable=no,toolbar=no,menubar=no\");"+
								"\n                 document.frm.target=\"new11\";"+
								"\n			        document.frm.submit();"+
								"\n			}"+
								"\n           	else{"+
								"\n			       alert(\"Please select an 'Interface' or 'Interface Fragment'\");"+
								"\n			}"+
								"\n	   	}"+
								"\n	   }"+
								"\n"+
									
									
								"\n	function download_onclick() {"+
								"\n		var i = test();"+
								"\n		if(i==1) {"+
								"\n                    if(document.frm.size.value=='0'){"+
								"\n                    alert('The Zip is Empty');return false;}"+
								"\n                   else{"+
								"\n			document.frm.method=\"post\";"+
								"\n			document.frm.action = \"coreadministrationv2.sysmgmt.DownloadInterface?interface_id=\"+document.frm.interface_id.value+\"&filename=\"+document.frm.filename1.value+\"&type=\"+document.frm.type.value;"+
								"\n             document.frm.target=\"bodyFrame\";"+
								"\n			document.frm.submit();"+
								"\n		}"+
								"\n		}"+
								"\n		if(i>1) {"+
								"\n                    if(document.frm.size.value=='0'){"+
								"\n                    alert('The Zip is Empty');return false;}"+
								"\n                   else{"+
								"\n			document.frm.method=\"post\";"+
								"\n			document.frm.action = \"coreadministrationv2.sysmgmt.DownloadInterface?interface_id=\"+document.frm.interface_id.value+\"&filename=\"+document.frm.filename1.value+\"&type=\"+document.frm.type.value;"+
								"\n             document.frm.target=\"bodyFrame\";"+
								"\n			document.frm.submit();"+
								"\n		}"+
								
								"\n	} "+
								"\n	} "+	
								
									
								"\n	function deleteLayout_onclick() {"+
								"\n		var i = test();"+
								"\n		if(i==1) {"+
								"\n			if(checkEntries()){"+
								"\n				doyou = confirm(\"Are you Sure to Delete ?\"); //Your question."+
								"\n				if (doyou == true) {"+
								"\n				document.frm.method=\"post\";"+
								"\n				document.frm.action = \"./coreadministrationv2.sysmgmt.InterfaceManagement?prmAddModify=2&interface_id=\"+document.frm.interface_id.value+\"&type=\"+document.frm.type.value;"+
								"\n             document.frm.target=\"bodyFrame\";"+
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
								"\n				doyou = confirm(\"Are you Sure to Delete The ?\"); //Your question."+
								"\n				if (doyou == true) {"+
								"\n				document.frm.method=\"post\";"+
								"\n				document.frm.action = \"./coreadministrationv2.sysmgmt.InterfaceManagement?prmAddModify=2&interface_id=\"+document.frm.interface_id.value+\"&type=\"+document.frm.type.value;"+
								"\n             document.frm.target=\"bodyFrame\";"+
								"\n				document.frm.submit();"+
								"\n				}"+
								"\n					else {"+
								"\n				}"+
								"\n				}"+
								"\n				}"+
								"\n			}"+
								"\n		}"+
								"\n	}"+
								
								
								"\n	function deleteAllLayout_onclick() {"+
								"\n				doyou = confirm(\"Are you Sure to Delete All ?\"); //Your question."+
								"\n				if (doyou == true) {"+
								"\n				document.frm.method=\"post\";"+
								"\n				document.frm.action = \"./coreadministrationv2.sysmgmt.InterfaceManagement?prmAddModify=4\""+
								"\n             document.frm.target=\"bodyFrame\";"+
								"\n				document.frm.submit();"+
								"\n				}"+
								"\n				else {"+
								"\n				}"+
								"\n	}"+
								
									
								"\n	function refresh_onclick() {"+
								"\n		var i = test();"+
								"\n		if(i==1) {"+
								"\n                    if(document.frm.interface_id.value==''){"+
								"\n                    	alert('Please select one item');return false;}"+
								"\n                   else{"+
								"\n				doyou = confirm(\"Are you Sure to refresh selected item ?\"); //Your question."+
								"\n				if (doyou == true) {"+
								"\n					document.frm.method=\"post\";"+
								"\n                 document.frm.target=\"bodyFrame\";"+
								"\n					document.frm.action = \"coreadministrationv2.sysmgmt.InterfaceManagement?prmAddModify=3&interface_id=\"+document.frm.interface_id.value+\"&filename=\"+document.frm.filename1.value+\"&type=\"+document.frm.type.value;"+
								"\n					document.frm.submit();"+
								"\n				}"+
								"\n                   else{"+
								
								"\n				}"+
								"\n				}"+
								"\n		}"+
								"\n		if(i>1) {"+
								//"\n			alert(document.frm.interface_id.value);"+
								"\n                    if(document.frm.interface_id.value==''){"+
								"\n                    	alert('Please select one item');return false;}"+
								"\n                   else{"+
								"\n				doyou = confirm(\"Are you Sure to refresh selected item ?\"); //Your question."+
								"\n				if (doyou == true) {"+
								"\n				document.frm.method=\"post\";"+
								"\n             document.frm.target=\"bodyFrame\";"+
								"\n				document.frm.action = \"coreadministrationv2.sysmgmt.InterfaceManagement?prmAddModify=3&interface_id=\"+document.frm.interface_id.value+\"&filename=\"+document.frm.filename1.value+\"&type=\"+document.frm.type.value;"+
								"\n				document.frm.submit();"+
								"\n				}"+
								"\n                   else{"+
								
								"\n				}"+
								"\n				}"+
								
								"\n		} "+
								"\n	}"+
								"\n"+
								
								/*
								 * Modified By Dibyarup to add refresh all functionality
								 */
								 "\n	function refreshAll_onclick() {"+
								 "\n			doyou = confirm(\"Are you Sure to refresh all interface and interface fragements?\"); "+
								 "\n			if (doyou == true) {"+
								 "\n				document.frm.method=\"post\";"+
								 "\n				document.frm.action = \"coreadministrationv2.sysmgmt.InterfaceManagement?prmAddModify=3&type="+REFRESH_ALL_TYPE+"\""+
								 "\n             document.frm.target=\"bodyFrame\";"+
								 "\n				document.frm.submit();"+
								 "\n			}"+
								 "\n	}"+
								/*
								 * End 
								 * 
								 */
									
								 	/*
									 *  Modified By Dibyarup to add generate role xml functionality
									 */
									 "\n	function generateRoleXml_onclick() {"+
									 "\n		document.frm.method=\"post\";"+
									 "\n		document.frm.action = \"coreadministrationv2.sysmgmt.DownloadInterface?type=GenerateRoleXML\""+
									 "\n             document.frm.target=\"bodyFrame\";"+
									 "\n		document.frm.submit();"+
									 "\n	}"+
									/*
									 * End 
									 */
									 /*
										 * Modified By Dibyarup to add generate manifest xml functionality
										 */
										 "\n	function generateManifestXml_onclick() {"+
										 "\n		document.frm.method=\"post\";"+
										 "\n		document.frm.action = \"coreadministrationv2.sysmgmt.DownloadInterface?type=GenerateManifestXML\""+
										 "\n             document.frm.target=\"bodyFrame\";"+
										 "\n		document.frm.submit();"+
										 "\n	}"+
										/*
										 * End 
										 */
										 /*
											 * Modified By Dibyarup to add download all xml functionality
											 */
											 "\n	function download_allInterface() {"+
											 "\n		document.frm.method=\"post\";"+
											 "\n		document.frm.action = \"coreadministrationv2.sysmgmt.DownloadInterface?type=DownloadAll\""+
											 "\n             document.frm.target=\"bodyFrame\";"+
											 "\n		document.frm.submit();"+
											 "\n	}"+
											/*
											 * End 
											 */
										 

									 
								"\n	function validate(){"+
								"\n		if(!fnCheckNull(document.frm.styleid.value,\"Group Id\")){"+
								"\n			document.frm.styleid.focus();"+
								"\n			return false;"+
								"\n		}"+
								"\n		return true;"+
								"\n	}"+
		
								"\n	function checkbox_onclick() {"+
								"\n		var i = test();"+
								"\n		if(i>1) {"+
								"\n			for(var counter=0; counter<document.frm.checkbox.length; counter++) {"+
								"\n				if(document.frm.checkbox[counter].checked) {"+
								"\n				document.frm.interface_id.value = document.frm.checkbox[counter].value;"+
								"\n			        document.frm.filename1.value = document.frm.hiddenname[counter].value;"+
								"\n			        document.frm.type.value = document.frm.hiddentype[counter].value;"+
								"\n			        document.frm.size.value = document.frm.hiddensize[counter].value;"+
								"\n			        document.frm.imagepath.value = document.frm.hiddenimage[counter].value;"+
								"\n                        if(document.frm.hiddencss[counter].value=='yes'){"+
								"\n									document.frm.inlinecss.checked=true;"+
								"\n								}"+
								"\n                        if(document.frm.hiddenjs[counter].value=='yes'){"+
								"\n									document.frm.inlinejs.checked=true;"+
								"\n								}"+
								"\n					break;"+
								"\n				}"+
								"\n			}"+
								"\n		}"+
								"\n		if(i==1) {"+
								"\n			document.frm.interface_id.value = document.frm.checkbox.value;"+
								"\n			document.frm.filename1.value = document.frm.hiddenname.value;"+
								"\n			document.frm.type.value = document.frm.hiddentype.value;"+
								"\n			document.frm.size.value = document.frm.hiddensize.value;"+
								"\n			document.frm.imagepath.value = document.frm.hiddenimage.value;"+
								"\n                        if(document.frm.hiddencss.value=='yes'){"+
								"\n									document.frm.inlinecss.checked=true;"+
								"\n								}"+
								"\n                        if(document.frm.hiddenjs.value=='yes'){"+
								"\n									document.frm.inlinejs.checked=true;"+
								"\n								}"+
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
						//Input inputButton4 = new Input();
						
						Input deleteAllButton = new Input();
						/*
						 * Modified By Dibyarup to add refresh all functionality
						 */
						//Input refreshAllButton = new Input();
						
						/*
						 * Modified By Dibyarup to add generate role xml functionality
						 */
						Input generateRoleXmlButton = new Input();
						
						/*
						 * Modified By Dibyarup to add generate manifest xml functionality
						 */
						Input generateManifestXmlButton = new Input();
						
						/*
						 * Modified By Dibyarup to add download all xml functionality
						 */
						Input downloadAllInterfaceButton = new Input();
						
						inputButton1.setOnClick("addLayout_onclick();");
						inputButton2.setOnClick("download_onclick();");
						inputButton3.setOnClick("deleteLayout_onclick();");
						deleteAllButton.setOnClick("deleteAllLayout_onclick();");
						inputButton6.setOnClick("showLayout_onclick();");
						//inputButton4.setOnClick("refresh_onclick();");
						//refreshAllButton.setOnClick("refreshAll_onclick();");
						generateRoleXmlButton.setOnClick("generateRoleXml_onclick();");
						generateManifestXmlButton.setOnClick("generateManifestXml_onclick();");
						downloadAllInterfaceButton.setOnClick("download_allInterface();");
						Option[] option12 = {new Option("0").addElement("[Choose One]"),
							new Option(INTERFACE_COLLECTION_TYPE).addElement(INTERFACE_COLLECTION_TYPE),
							new Option(INTERFACE_TYPE).addElement(INTERFACE_TYPE),
							new Option("Manifest").addElement("Manifest"),
							new Option("RoleXML").addElement("RoleXML"),
							new Option(INTERFACE_FRAGMENT_TYPE).addElement(INTERFACE_FRAGMENT_TYPE),
							new Option("ApplicationDefault").addElement("ApplicationDefault")
						};
		
						Html html = new Html()
								.addElement(new Head()
								.addElement(new Title("Interface Management"))
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
						Body body=new Body().addElement(new TableExtension()
								.headerTable("<b>Administrator:</b> "+strAdminId, strDate, strTime, "<b>Portal Administration:</b> Interface Management"));
												
		
						if(invalid != null && invalid.equals("1"))
						{
							body.addElement(new BR()).addElement(new BR()).addElement(new Font().setColor("#990000").addElement(" * Image Exists ")).addElement(new BR());
						} else
		
							if(invalid != null && invalid.equals("2"))
							{
								body.addElement(new BR()).addElement(new BR()).addElement(new Font().setColor("#990000").addElement(" * No Image Exists ")).addElement(new BR());
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
									.addElement("Select Type"))
									.addElement(new TD()
									.setWidth("336")
									.addElement(new Select()
									.setClassId("drpdwn")
									.setName("type")
									.setTabindex("2")
									.addElement(option12))))
									
									.addElement(new TR()
									.addElement(new TD()
									.setClassId("PPRLabelText")
									.setWidth("160")
									.addElement("Inline CSS"))
									.addElement(new TD()
									.setWidth("336")
									.addElement(new Input()
									.setClassId("PPRLabelText")
									.setName("inlinecss")
									.setValue("yes")
									.setType("checkbox")
									)))
									
									.addElement(new TR()
									.addElement(new TD()
									.setClassId("PPRLabelText")
									.setWidth("160")
									.addElement("Inline JS"))
									.addElement(new TD()
									.setWidth("336")
									.addElement(new Input()
									.setClassId("PPRLabelText")
									.setName("inlinejs")
									.setValue("yes")
									.setType("checkbox")
												)))
									
									.addElement(new TR()
									.addElement(new TD()
									.setClassId("PPRLabelText")
									.setWidth("160")
									.addElement("Path For Static Images"))
									.addElement(new TD()
									.setWidth("336")
									.addElement(new Input()
									.setClassId("PPRLabelText")
									.setName("imagepath")
									.setValue("")
									.setType("text")
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
									.addElement(deleteAllButton
									.setClassId("sbttn")
									.setName("deleteGroupAll")
									.setTabindex(2)
									.setTitleValue("Delete All")
									.setType("button")
									.setValue("Delete All")))
									
									.addElement(new TD()
									.setWidth(5))
									.addElement(new TD()
									.addElement(inputButton6
									.setClassId("sbttn")
									.setName("show")
									.setTabindex(2)
									.setTitleValue("Show")
									.setType("button")
									.setValue("Show")))
									/*.addElement(new TD()
									.setWidth(5))
									.addElement(new TD()
									.addElement(inputButton4
									.setClassId("sbttn")
									.setName("refresh")
									.setTabindex(2)
									.setTitleValue("Refresh")
									.setType("button")
									.setValue("Refresh")))
									.addElement(new TD()
									.setWidth(5))
									.addElement(new TD()
									.addElement(refreshAllButton
									.setClassId("sbttn")
									.setName("refreshAll")
									.setTabindex(2)
									.setTitleValue("Refresh All")
									.setType("button")
									.setValue("Refresh All")))*/
									.addElement(new TD()
									.setWidth(5))
									.addElement(new TD()
									.addElement(generateRoleXmlButton
									.setClassId("sbttn")
									.setName("generateRoleXml")
									.setTabindex(2)
									.setTitleValue("Generate Role XML")
									.setType("button")
									.setValue("Generate Role XML")))
									.addElement(new TD()
									.setWidth(5))
									.addElement(new TD()
									.addElement(generateManifestXmlButton
									.setClassId("sbttn")
									.setName("generateManifestXml")
									.setTabindex(2)
									.setTitleValue("Generate Manifest XML")
									.setType("button")
									.setValue("Generate Manifest XML")))
									.addElement(new TD()
									.setWidth(5))
									.addElement(new TD()
									.addElement(downloadAllInterfaceButton
											.setClassId("sbttn")
											.setName("downloadAllInterface")
											.setTabindex(2)
											.setTitleValue("Download All Interface")
											.setType("button")
											.setValue("Download All Interface")))
									
									
									
									
		
												));
								
								
								
							String sql = "select framework_file_id as \"Select\" ,filename as \"File Name\",framework_file_title as \"Interface Title\",type as \"Type\",filesize as \"File Size\",inlinecss as \"Inline CSS\",inlinejs as \"InlineJS\",imagepath as \"Image Path\", last_updated as \"Last Updated\"  from framework_file";
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
								grid1.Cols(2).setFieldName("hiddentitle");
								grid1.Cols(3).setFieldName("hiddentype");
								grid1.Cols(4).setFieldName("hiddensize");
								grid1.Cols(5).setFieldName("hiddencss");
								grid1.Cols(6).setFieldName("hiddenjs");
								grid1.Cols(7).setFieldName("hiddenimage");
								grid1.Cols(8).setFieldName("hiddenLU");
								
								grid1.Cols(0).Header().setClassID("swb");
								grid1.Cols(1).Header().setClassID("swb");
								grid1.Cols(2).Header().setClassID("swb");
								grid1.Cols(3).Header().setClassID("swb");
								grid1.Cols(4).Header().setClassID("swb");
								grid1.Cols(5).Header().setClassID("swb");
								grid1.Cols(6).Header().setClassID("swb");
								grid1.Cols(7).Header().setClassID("swb");
								grid1.Cols(8).Header().setClassID("swb");
								
								grid1.Cols(0).insertFieldScript("onclick=\"CCA();checkbox_onclick();\"");
								grid1.setEachRowHeight("20");
								grid1.canSort(0, false);
								grid1.canSort(1, true);
								grid1.canSort(2, true);
								grid1.canSort(3, true);
								grid1.canSort(4, true);
								grid1.canSort(5, true);
								grid1.canSort(6, true);
								grid1.canSort(7, true);
								grid1.canSort(8, true);
								
								grid1.setSortableColumnsToolTip("Click to Sort");
								grid1.showPageNavigationFirst();
								grid1.showPageNavigationLast();
								grid1.hidePageNavigationHTML();
								grid1.setPageNavigationFontFace("Arial");
								grid1.setPageNavigationFontSize(2);
								grid1.setASCImageSource("../coreadmin/images/asc.gif");
								grid1.setDESCImageSource("../coreadmin/images/desc.gif");
								grid1.buildGrid(); //result set being processed, and cell values are available			
					
								grid1.closeConnection();

								if (grid1.isResultSetEmpty()) {
									form.addElement("<p id=\"record\">No Records Found");
								}
								else {		
									List<Pair<Integer, String>> itemCounts=DataBaseLayer.retrieveDifferentItemsCount(null);
									grid1.countResultSet();				
									form.addElement("<p><b>Total No. Of Items:</b> " +grid1.getRows());
									if(GenericUtil.hasListData(itemCounts)){
										for(Pair<Integer, String> itemCount : itemCounts){
//											form.addElement("<br/>Total No. of "+itemCount.getSecond()+": "+itemCount.getFirst());
											form.addElement("<span>&nbsp;&nbsp;&nbsp;<b>Total No. of "+itemCount.getSecond()+":</b> "+itemCount.getFirst() +"</span>");
										}
									}
									form.addElement("</p>");
									form.addElement(grid1.getGrid());
								}	
								
							}
							catch (Exception exp) {
					
							}			
							form.addElement("<input type=\"hidden\" name=\"interface_id\">");	
							form.addElement("<input type=\"hidden\" name=\"size\">");	
							form.addElement("<input type=\"hidden\" name=\"filename1\">");				
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
		
					public String addlayout(HttpServletRequest request, HttpServletResponse response,String strCreatedBy, PrintWriter out1,String loggedInUserId)
							throws IOException, ServletException {
						String type = request.getParameter("type");
						String statusMessage=LayoutUploader.addlayout(request, response, strCreatedBy, out1, loggedInUserId,type,true);
						/*if(returnStatus.getFirst()){
							statusMessage="Deletion Successful!";
						}else{
							statusMessage="Failed to delete! Reason - "+returnStatus.getSecond();
						}*/
						return statusMessage;
					}
		
									
		
			
		
			
		
		
					public String deleteAll() throws IOException, ServletException {
						String interface_id = ManifestDao.getManifestId();
						String statusMessage="";
						Pair<Boolean, String> returnStatus=null;
						returnStatus=DataBaseLayer.DeleteinterfaceCollection(interface_id);
						returnStatus=DataBaseLayer.DeleteinterfaceRole();
						returnStatus=DataBaseLayer.DeleteinterfaceCollection(LayoutUploader.MANIFEST_XML);
						returnStatus=DataBaseLayer.DeleteinterfaceCollection(LayoutUploader.ROLE_XML);
						
						if(returnStatus.getFirst()){
							statusMessage="Deletion Successful!";
						}else{
							statusMessage="Failed to delete! Reason - "+returnStatus.getSecond();
						}
						return statusMessage;
					}	
		
					public String deletelayout(HttpServletRequest request, String strCreatedBy, PrintWriter out1) throws IOException, ServletException {
						String interface_id = request.getParameter("interface_id");
						//String itype = request.getParameter("type");
						String statusMessage="";
						Pair<Boolean, String> returnStatus;
						/*if (itype.equals(INTERFACE_COLLECTION_TYPE)) {
							returnStatus=DataBaseLayer.DeleteinterfaceCollection(interface_id);
							returnStatus=DataBaseLayer.DeleteinterfaceRole();
						} else {*/
							returnStatus=DataBaseLayer.deleteFromAllTables(interface_id);
							if(returnStatus.getFirst()){
								returnStatus=DataBaseLayer.deleteInterfaceRoleManifestAssociation(interface_id);
							}
							
						//}
						if(returnStatus.getFirst()){
							statusMessage="Deletion Successful!";
						}else{
							statusMessage="Failed to delete! Reason - "+returnStatus.getSecond();
						}
						return statusMessage;
					}		
							
							
		/*	public void UnZip(String path)
			{
				ResourceBundle rb = ResourceBundle.getBundle("portal",Locale.getDefault());      
				String filename="";
				String s7="";
				String key1= "xml"; 
				String photopath = rb.getString(key1);
				String path1=photopath;
				File ff=new File(path1);
				ff.mkdir();
				String attachmentname=path1;	
				try
				{
					String inFileName = ""+path;
					String name = inFileName.substring(inFileName.lastIndexOf(File.separator)+1,inFileName.lastIndexOf("."));
					// Specify destination where file will be unzipped
					String destinationDirectory = attachmentname+name;
					File sourceZipFile = new File(inFileName);
					File unzipDestinationDirectory = new File(destinationDirectory);
					// Open Zip file for reading
					ZipFile zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);
					// Create an enumeration of the entries in the zip file
					Enumeration zipFileEntries = zipFile.entries();
					// Process each entry
					while (zipFileEntries.hasMoreElements())
					{
						// grab a zip file entry
						ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
						String currentEntry = entry.getName();
						//System.out.println("Extracting: " + currentEntry);
						File destFile = new File(unzipDestinationDirectory, currentEntry);
		
						// grab file's parent directory structure
						File destinationParent = destFile.getParentFile();
						// create the parent directory structure if needed
						destinationParent.mkdirs();
						// extract file if not a directory
						if (!entry.isDirectory())
						{
							BufferedInputStream is = new BufferedInputStream(zipFile.getInputStream(entry));
							int currentByte;
							int BUFFER = 2048;
							// establish buffer for writing file
							byte data[] = new byte[BUFFER];
							// write the current file to disk
							FileOutputStream fos = new FileOutputStream(destFile);
							BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
							// read and write until last byte is encountered
							while ((currentByte = is.read(data, 0, BUFFER)) != -1)
							{
								dest.write(data, 0, currentByte);
							}
							dest.flush();
							dest.close();
							is.close();
						}
					}
					zipFile.close();
				}
				catch (IOException ioe)
				{
					ioe.printStackTrace();
				}
			}
		
			public void UnZipchild(String path,String parentname)
			{
				ResourceBundle rb = ResourceBundle.getBundle("portal",Locale.getDefault());      
				String filename="";
				String s7="";
				String key1= "xml"; 
				String photopath = rb.getString(key1);
				String path1=photopath;
				File ff=new File(path1);
				ff.mkdir();
				String attachmentname=path1;	
				try
				{
					String inFileName = ""+path;
					String name = inFileName.substring(inFileName.lastIndexOf(File.separator)+1,inFileName.lastIndexOf("."));
					String destinationDirectory = attachmentname+parentname+File.separator+parentname+File.separator+name;
					File sourceZipFile = new File(inFileName);
					File unzipDestinationDirectory = new File(destinationDirectory);
					ZipFile zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);
					Enumeration zipFileEntries = zipFile.entries();
					while (zipFileEntries.hasMoreElements())
					{
						ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
						String currentEntry = entry.getName();
						File destFile = new File(unzipDestinationDirectory, currentEntry);
						File destinationParent = destFile.getParentFile();
						destinationParent.mkdirs();
						// extract file if not a directory
						if (!entry.isDirectory())
						{
							BufferedInputStream is = new BufferedInputStream(zipFile.getInputStream(entry));
							int currentByte;
							int BUFFER = 2048;
							// establish buffer for writing file
							byte data[] = new byte[BUFFER];
							// write the current file to disk
							FileOutputStream fos = new FileOutputStream(destFile);
							BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
							// read and write until last byte is encountered
							while ((currentByte = is.read(data, 0, BUFFER)) != -1)
							{
								dest.write(data, 0, currentByte);
							}
							dest.flush();
							dest.close();
							is.close();
						}
					}
					zipFile.close();
				}
				catch (IOException ioe)
				{
					ioe.printStackTrace();
				}
			}
		*/
			
			
			
			/////////////////////////////SUBIR////////////////////////////							
			
			public String updateTemplateConf(HttpServletRequest req, HttpServletResponse res, PrintWriter out,String loggedInUserId) {
				
				
				
				String interface_id = req.getParameter("interface_id");
			//	String filename = req.getParameter("filename");
				String type=req.getParameter("type");
				
				String inlinecss = req.getParameter("inlinecss");
				String inlinejs = req.getParameter("inlinejs");
				String imagepath = req.getParameter("imagepath");
				String statusMessage=updateInterface(interface_id, type, inlinecss, inlinejs, imagepath, req, res, loggedInUserId);
				return statusMessage;
				
			}
			
			public static String updateInterface(String interface_id,String type,String inlinecss,String inlinejs,String imagepath,HttpServletRequest req, HttpServletResponse res, String loggedInUserId){
				String statusMessage="";
				inlinecss = (inlinecss == null) ? "no" : inlinecss;
				inlinejs = (inlinejs == null) ? "no" : inlinejs;
				imagepath = (imagepath == null) ? "" : imagepath;
				
				String name = interface_id+".zip";
				String path="";
				ResourceBundle rb = ResourceBundle.getBundle("portal",Locale.getDefault());      
				String key1= "xml"; 
				path = rb.getString(key1);
				
				if(type.equals(INTERFACE_TYPE) || type.equals(INTERFACE_FRAGMENT_TYPE)) { 
					Pair<Boolean,String> returnStatus=collectInterfaceComponents(interface_id,path+interface_id+File.separator+interface_id);
					if(returnStatus.getFirst()){
						boolean isSuccess=true;
						try {
							FolderZiper fz = new FolderZiper();
							fz.zipFolder(path+interface_id, path+name);
							fz.close();
						} catch(IOException ioe) {
							isSuccess=false;
							statusMessage="Failed to refresh! Reason - "+ioe.getMessage();
							ioe.printStackTrace();
						}
						if(isSuccess){
							File f = new File(path+name);
							String strSize = (new Long(f.length())).toString();
							returnStatus=upload(type, path, name, strSize, req, res, inlinecss, inlinejs, imagepath,loggedInUserId);
							if(returnStatus.getFirst()){
								statusMessage="Refresh Successful!";
							}else{
								statusMessage="Failed to refresh! Reason - "+returnStatus.getSecond();
							}
						}
						
					}else{
						statusMessage="Failed to refresh! Reason - "+returnStatus.getSecond();
					}
					
				}
				else if(type.equals(REFRESH_ALL_TYPE)) { 
					String[] requriedTypes={INTERFACE_TYPE,INTERFACE_FRAGMENT_TYPE};
					Vector<String[]> vIIDs = DataBaseLayer.getFrameworkData(requriedTypes);
					statusMessage=refreshAllItems(vIIDs, req, res, path, loggedInUserId, null);
				}
				else if(type.equals(INTERFACE_COLLECTION_TYPE)) { 
					Vector<String[]> vIIDs = DataBaseLayer.getFrameworkData();
					statusMessage=refreshAllItems(vIIDs, req, res, path, loggedInUserId, type);
				}
				return statusMessage;
			}
			
			private static String refreshAllItems(Vector<String[]> vIIDs,HttpServletRequest req, HttpServletResponse res,String path,String loggedInUserId,String type){
				String statusMessage="";
				boolean isTypeEmpty=GenericUtil.isEmptyString(type);
				for(int i=0;i<vIIDs.size();i++) {
					String interfaceRefreshStatus="";
					String[] strIIDs = (String[])vIIDs.elementAt(i);
					
					String i_id = strIIDs[0];
					String inlinecss = strIIDs[1];
					String inlinejs = strIIDs[2];
					String imagepath = strIIDs[3];
					if(isTypeEmpty){
						type=strIIDs[4];
					}
					
					inlinecss = (inlinecss == null) ? "no" : inlinecss;
					inlinejs = (inlinejs == null) ? "no" : inlinejs;
					imagepath = (imagepath == null) ? "" : imagepath;
					
					String name = i_id+".zip";
					Pair<Boolean,String> returnStatus=collectInterfaceComponents(i_id,path+i_id+File.separator+i_id);
					if(returnStatus.getFirst()){
						boolean isSuccess=true;
						try {
							FolderZiper fz = new FolderZiper();
							fz.zipFolder(path+i_id, path+name);
							fz.close();
						} catch(IOException ioe) {
							isSuccess=false;
							interfaceRefreshStatus="'"+i_id+"' Failed to refresh. Reaosn :"+ioe.getMessage();
							ioe.printStackTrace();
						}
						
						if(isSuccess){
							File f = new File(path+name);
							String strSize = (new Long(f.length())).toString();
							returnStatus=upload(type, path, name, strSize, req, res, inlinecss, inlinejs, imagepath,loggedInUserId);
							
							if(returnStatus.getFirst()){
								interfaceRefreshStatus="'"+i_id+"' Refresh Successful!";
							}else{
								interfaceRefreshStatus="'"+i_id+"' Failed to refresh. Reaosn :"+returnStatus.getSecond();
							}
						}
						
					}else{
						interfaceRefreshStatus="'"+i_id+"' Failed to refresh. Reaosn :"+returnStatus.getSecond();
					}
					statusMessage=statusMessage+LINE_SEPERATOR+interfaceRefreshStatus;
				}
				return statusMessage;
			}
			private static Pair<Boolean,String> collectInterfaceComponents(String interface_id, String destDir) {
				Pair<Boolean,String> returnStatus=new Pair<>();;
				File f = new File(destDir);
				if(!f.exists())
					f.mkdirs();
				Vector<Vector<Object>> vContent = DataBaseLayer.getModuleSrc(interface_id);
				InputStream in;
				FileOutputStream out =null;
				if (GenericUtil.hasCollectionData(vContent)) {
					for(int m=0;m<vContent.size();m++){
						try{
							Vector<Object> vContent1 = (Vector<Object>)vContent.elementAt(m);
							String file_name=(String)vContent1.elementAt(0);
							in = (InputStream)vContent1.elementAt(1);
							out = new FileOutputStream(destDir+File.separator+file_name);
							int len = 0;
							byte buffer[]= new byte[1024];
							try {
								while ((in != null) && ((len = in.read(buffer)) != -1)) {
									out.write(buffer,0,len);
								}
							}
							finally {
								if (in != null) in.close();
								if(out!=null) out.close();
							}
							returnStatus.setFirst(true);
						}catch(IOException ioe){
							System.out.println("***IOException in method collectInterfaceComponents()  : "+ioe.getMessage());
							returnStatus.setFirst(false);
							returnStatus.setSecond(ioe.getMessage());
							break;
						}
																
					}
				}else{
					returnStatus.setFirst(false);
					returnStatus.setSecond("No Data Found for InterfaceId : "+interface_id);
				}
				return returnStatus;
			}
			
			private static Pair<Boolean,String> upload(String type, String path, String fileName, String strSize, HttpServletRequest req, HttpServletResponse res,String inlinecss,String inlinejs,String imagepath,String loggedInUserId) {
				Pair<Boolean,String> returnStatus=null;
				if(type.equals(INTERFACE_COLLECTION_TYPE)) {
					returnStatus=LayoutUploader.uploadInterface(path,path,fileName,INTERFACE_COLLECTION_TYPE,strSize,req,res,inlinecss,inlinejs,imagepath,loggedInUserId,true);
				}
				if(type.equals(INTERFACE_TYPE)) {
					returnStatus=LayoutUploader.uploadInterface(path,path,fileName,type,strSize,req,res,inlinecss,inlinejs,imagepath,loggedInUserId,true);
				}
				if(type.equals(INTERFACE_FRAGMENT_TYPE)) {
					returnStatus=LayoutUploader.uploadInterfaceFragment(path,path,fileName,type,strSize,req,res,inlinecss,inlinejs,imagepath,loggedInUserId,true);
				}
				return returnStatus;
			}
			
			
			
			/////////////////////////////SUBIR////////////////////////////
			
		}
