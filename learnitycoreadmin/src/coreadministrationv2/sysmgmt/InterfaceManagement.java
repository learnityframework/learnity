		package coreadministrationv2.sysmgmt;
		
		/**
		* Title:        Learnity Interface Management   
		* Description:
		* Copyright:    Copyright (c) 2010
		* Company:      Aunwesha
		* @author Shibaji Chatterjee
		*/
		
		
		//import org.htmlparser.util.*;
		import interfaceenginev2.display.DisplayEngine;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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
import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.parsers.DOMParser;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.oreilly.servlet.MultipartRequest;
import comv2.aunwesha.JSPGrid.JSPGridPro2;
import comv2.aunwesha.lfutil.GenericUtil;

import coreadministrationv2.dbconnection.DataBaseLayer;
import coreadministrationv2.utility.TableExtension;
//import oracle.xml.parser.v2.*;
		
/*import org.htmlparser.Parser;*/
// import org.htmlparser.tags.BodyTag;
//import jmesa.*;
		
//import javax.xml.parsers.DocumentBuilder;
		//import javax.xml.parsers.DocumentBuilderFactory;
		//import javax.xml.parsers.ParserConfigurationException;
		
		
		
		public class InterfaceManagement extends HttpServlet {
		
			private static final String INTERFACE_COLLECTION_TYPE = "InterfaceCollection";

			private static final String INTERFACE_XML_FILE_NAME = "interface.xml";

			private static final String INTERFACE_TYPE = "Interface";
			
			private static final String REFRESH_ALL_TYPE = "REFRESH_ALL";

			private static final String INTERFACE_FRAGMENT_TYPE = "InterfaceFragment";

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
				//Object obj = mysession.getAttribute(LOGIN_SESSION_NAME);
				String obj="superadmin";
				
				if (obj ==null)
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
								addlayout(request, response,strAdminId, out,loggedInUserId);
								break;
							case 1:
									//modifylayout(request, strAdminId, out);
								break;
							case 2:
								deletelayout(request, strAdminId, out);
								break;
							case 3:
								updateTemplateConf(request, response, out,loggedInUserId);
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
								"\n		document.frm.submit();"+
								"\n	}"+
								"\n	}"+
								"\n	function showLayout_onclick() {"+
								"\n		var i = test();"+
								"\n		if(i==1) {"+
								"\n         if(document.frm.type.value=='InterfaceCollection'){"+
								"\n				return false;"+
								"\n			}"+
								"\n     	else if(document.frm.type.value=='InterfaceFragment'){"+
								"\n				document.frm.method=\"post\";"+
								"\n				document.frm.action = \"coreadministrationv2.sysmgmt.ResourceInterface?interface_id=\"+document.frm.interface_id.value;"+
								"\n				window.open(\"\",\"new11\",\"width=700,height=460,status=yes,scrollbars=no,resizable=no,toolbar=no,menubar=no\");"+
								"\n      		document.frm.target=\"new11\";"+
								"\n				document.frm.submit();"+
								"\n			}"+
								"\n     	else{"+
								"\n				document.frm.method=\"post\";"+
								"\n				document.frm.action = \"coreadministrationv2.sysmgmt.ResourceInterface?interface_id=\"+document.frm.interface_id.value;"+
								"\n				window.open(\"\",\"new11\",\"width=700,height=460,status=yes,scrollbars=no,resizable=no,toolbar=no,menubar=no\");"+
								"\n     		document.frm.target=\"new11\";"+
								"\n				document.frm.submit();"+
								"\n			}"+
								"\n		}"+
								"\n			if(i>1) {"+
								"\n            if(document.frm.type.value=='InterfaceCollection'){"+
								"\n					return false;"+
								"\n			}"+
								"\n         	else if(document.frm.type.value=='InterfaceFragment'){"+
								"\n					document.frm.method=\"post\";"+
								"\n			        document.frm.action = \"coreadministrationv2.sysmgmt.ResourceInterface?interface_id=\"+document.frm.interface_id.value;"+
								"\n		        	window.open(\"\",\"new11\",\"width=700,height=460,status=yes,scrollbars=no,resizable=no,toolbar=no,menubar=no\");"+
								"\n                 document.frm.target=\"new11\";"+
								"\n			        document.frm.submit();"+
								"\n			}"+
								"\n           	else{"+
								"\n			        document.frm.method=\"post\";"+
								"\n			        document.frm.action = \"coreadministrationv2.sysmgmt.ResourceInterface?interface_id=\"+document.frm.interface_id.value;"+
								"\n		        	window.open(\"\",\"new11\",\"width=700,height=460,status=yes,scrollbars=no,resizable=no,toolbar=no,menubar=no\");"+
								"\n                 document.frm.target=\"new11\";"+
								"\n			        document.frm.submit();"+
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
								"\n			document.frm.submit();"+
								"\n		}"+
								"\n		}"+
								"\n		if(i>1) {"+
								"\n                    if(document.frm.size.value=='0'){"+
								"\n                    alert('The Zip is Empty');return false;}"+
								"\n                   else{"+
								"\n			document.frm.method=\"post\";"+
								"\n			document.frm.action = \"coreadministrationv2.sysmgmt.DownloadInterface?interface_id=\"+document.frm.interface_id.value+\"&filename=\"+document.frm.filename1.value+\"&type=\"+document.frm.type.value;"+
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
								"\n				document.frm.submit();"+
								"\n				}"+
								"\n					else {"+
								"\n				}"+
								"\n				}"+
								"\n				}"+
								"\n			}"+
								"\n		}"+
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
								 "\n				document.frm.submit();"+
								 "\n			}"+
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
						Input inputButton4 = new Input();
						/*
						 * Modified By Dibyarup to add refresh all functionality
						 */
						Input refreshAllButton = new Input();
						
						inputButton1.setOnClick("addLayout_onclick();");
						inputButton2.setOnClick("download_onclick();");
						inputButton3.setOnClick("deleteLayout_onclick();");
						inputButton6.setOnClick("showLayout_onclick();");
						inputButton4.setOnClick("refresh_onclick();");
						refreshAllButton.setOnClick("refreshAll_onclick();");
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
									.addElement(inputButton6
									.setClassId("sbttn")
									.setName("show")
									.setTabindex(2)
									.setTitleValue("Show")
									.setType("button")
									.setValue("Show")))
									.addElement(new TD()
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
									.setValue("Refresh All")))
		
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
								grid1.Cols(0).setFieldType(grid1.FIELD_RADIO);
								grid1.Cols(1).setFieldType(grid1.FIELD_HIDDEN);		
								grid1.Cols(2).setFieldType(grid1.FIELD_HIDDEN);	
								grid1.Cols(3).setFieldType(grid1.FIELD_HIDDEN);
								grid1.Cols(4).setFieldType(grid1.FIELD_HIDDEN);
								grid1.Cols(5).setFieldType(grid1.FIELD_HIDDEN);
								grid1.Cols(6).setFieldType(grid1.FIELD_HIDDEN);
								grid1.Cols(7).setFieldType(grid1.FIELD_HIDDEN);
								
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
					
								if (grid1.isResultSetEmpty()) {
									form.addElement("<p id=\"record\">No Records Found");
								}
								else {						
									grid1.countResultSet();				
									form.addElement("<p>Total No. Of Interface: " +grid1.getRows());
									form.addElement(grid1.getGrid());
								}	
								
								grid1.closeConnection();
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
							body.addElement(form);
							html.addElement(body.setClass("bodyadmin"));
							out.print(html.toString());
							}
			
					}
					public void doPost(HttpServletRequest request, HttpServletResponse response)
							throws IOException, ServletException {
						doGet(request, response);
							}
		
					public void addlayout(HttpServletRequest request, HttpServletResponse response,String strCreatedBy, PrintWriter out1,String loggedInUserId)
							throws IOException, ServletException {
							
											String type=request.getParameter("type");
											String interface_id=request.getParameter("interface_id");
											String inlinecss="";
											String inlinejs="";
											String imagepath="";
											ResourceBundle rb = ResourceBundle.getBundle("portal",Locale.getDefault());      
											String filename="";
											String s7="";
											String strSize="";
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
													Long size=new Long(uploadfile.length());
													strSize=size.toString();	
												}
												filename=multipartrequest.getFilesystemName("filename");
												inlinecss=multipartrequest.getParameter("inlinecss");
												inlinejs=multipartrequest.getParameter("inlinejs");
												imagepath=multipartrequest.getParameter("imagepath");
												//System.out.println("..................................INLINECSS................"+inlinecss);
							
											}	
											catch(IOException ioexception)
											{
											}
											if(type.equals(INTERFACE_COLLECTION_TYPE))
											{
												uploadInterfaceCollection(attachmentname,s7,type,strSize,request,response,inlinecss,inlinejs,imagepath,loggedInUserId);
											}
											if(type.equals(INTERFACE_TYPE))
											{
												uploadInterface(attachmentname,attachmentname,s7,type,strSize,request,response,inlinecss,inlinejs,imagepath,loggedInUserId);
											}
											if(type.equals("Manifest"))
											{
												uploadManifest(interface_id,attachmentname,s7,type,strSize);
											}
											if(type.equals("RoleXML"))
											{
												uploadRoleXML(interface_id,attachmentname,s7,type,strSize);
											}
											
											if(type.equals(INTERFACE_FRAGMENT_TYPE))
											{
												uploadInterfaceFragment(attachmentname,attachmentname,s7,type,strSize,request,response,inlinecss,inlinejs,imagepath,loggedInUserId);
											}
									}
		
									
		
			public void uploadInterfaceCollection( String attachmentname,String s7,String typecollection,String fsize,HttpServletRequest request, HttpServletResponse response,String inlinecss,String inlinejs,String imagepath,String loggedInUserId)
									{
										   UnZipInterface(attachmentname,s7); 
											String  inFileName=attachmentname+s7; 
											String name = inFileName.substring(inFileName.lastIndexOf(File.separator)+1,inFileName.lastIndexOf("."));                                             
											DOMParser parser = new DOMParser();
											DOMParser parser2 = new DOMParser();
											String rolexmlSize="0";
											String mfileSize="0";
											String applicationdefault="0";
											try
											{
														File rolefile=new File(attachmentname+name+File.separator+name+File.separator+"interfacerole.xml");   	
														if(rolefile.exists())
														{
															Long csize=new Long(rolefile.length());
															rolexmlSize=csize.toString();	
														}
														DataBaseLayer.deleteall("rolexml");
														DataBaseLayer.FrameworkFile("rolexml","rolexml",attachmentname+name+File.separator+name+File.separator,"interfacerole.xml","RoleXML",rolexmlSize,inlinecss,inlinejs,imagepath);
														DataBaseLayer.DeleteinterfaceRole();
														parser2.parse(attachmentname+name+File.separator+name+File.separator+"interfacerole.xml");	
														Document document3 = parser2.getDocument();
														NodeList nodelistroles = document3.getElementsByTagName("roles");
														NodeList nodelistrole = ((Element)nodelistroles.item(0)).getElementsByTagName("role");	
														for(int x1=0; x1<nodelistrole.getLength() ; x1++)
														{
															Element roleelement = (Element)nodelistrole.item(x1);
															String role_id = roleelement.getAttribute("id");
															NodeList interfacerole = ((Element)nodelistrole.item(x1)).getElementsByTagName("interface");
															for(int x2=0; x2<interfacerole.getLength() ; x2++)
															{
																Element interfaceelement = (Element)interfacerole.item(x2);
																String interfacerole_id = interfaceelement.getAttribute("id");
																String layoutrole_id="";
																String contentrole_id ="";
																String behaviourrole_id="";
																String stylerole_id="";
																NodeList layoutrole = ((Element)interfacerole.item(x2)).getElementsByTagName("layout");
																for(int x3=0; x3<layoutrole.getLength() ; x3++)
																{
																	Element layoutelement = (Element)layoutrole.item(x3);
																	layoutrole_id = layoutelement.getAttribute("id");
																}	
																					
																NodeList contentrole = ((Element)interfacerole.item(x2)).getElementsByTagName("content");
																for(int x4=0; x4<contentrole.getLength() ; x4++)
																{
																	Element contentelement = (Element)contentrole.item(x4);
																	contentrole_id = contentelement.getAttribute("id");
																}	
																					
																NodeList behaviourrole = ((Element)interfacerole.item(x2)).getElementsByTagName("behaviour");
																for(int x5=0; x5<behaviourrole.getLength() ; x5++)
																{
																	Element behaviourelement = (Element)behaviourrole.item(x5);
																	behaviourrole_id = behaviourelement.getAttribute("id");
																}	
																					
																NodeList stylerole = ((Element)interfacerole.item(x2)).getElementsByTagName("style");
																for(int x6=0; x6<stylerole.getLength() ; x6++)
																{
																	Element styleelement = (Element)stylerole.item(x6);
																	stylerole_id = styleelement.getAttribute("id");
																}	
																				
																DataBaseLayer.InsertInterfaceRoleAssociation(role_id,interfacerole_id,layoutrole_id,contentrole_id,behaviourrole_id,stylerole_id);	
															}
														}		
											}
											catch (SAXException e) {
						
											} 
											catch (IOException e1) {
											
											} 
											try
											{
														File mfile=new File(attachmentname+name+File.separator+name+File.separator+"manifest.xml");   	
														if(mfile.exists())
														{
															Long csize=new Long(mfile.length());
															mfileSize=csize.toString();	
														}
														DataBaseLayer.deleteall("manifestxml");
														DataBaseLayer.FrameworkFile("manifestxml","manifestxml",attachmentname+name+File.separator+name+File.separator,"manifest.xml","Manifest",mfileSize,inlinecss,inlinejs,imagepath);
					
														parser.parse(attachmentname+name+File.separator+name+File.separator+"manifest.xml");	
														Document document2 = parser.getDocument();	
														NodeList nodelistmanifest = document2.getElementsByTagName("manifest");
														Element manifest = (Element)nodelistmanifest.item(0);
														String manifestid = manifest.getAttribute("id");	
														String title=manifest.getAttribute("title");
														String childSize="0";
														DataBaseLayer.DeleteinterfaceCollection(manifestid);
														DataBaseLayer.FrameworkFile2(manifestid,title,attachmentname,s7,INTERFACE_COLLECTION_TYPE,fsize,inlinecss,inlinejs,imagepath);
														DataBaseLayer.insertinterfaceCollection(manifestid,title,typecollection,fsize);
														NodeList interface1 = ((Element)nodelistmanifest.item(0)).getElementsByTagName("interface"); 
														for(int ma=0; ma<interface1.getLength() ; ma++){
															Element interfaceelement = (Element)interface1.item(ma);		
															String interfaceid= interfaceelement.getAttribute("id");	
															String zip = interfaceelement.getAttribute("zip");
															String interface_title = interfaceelement.getAttribute("title");
															String childtype=interfaceelement.getAttribute("type");     
															File f1=new File(attachmentname+name+File.separator+name+File.separator+zip);   	
															if(f1.exists())
																	{
																		Long csize=new Long(f1.length());
																		childSize=csize.toString();	
																		DataBaseLayer.insertinterfacemanifestassociation(interfaceid,manifestid);
																		//DataBaseLayer.insertinterface(interfaceid,interface_title,childtype,childSize);
																		if(childtype.equals(INTERFACE_TYPE))
																		{
																			uploadInterface(attachmentname,attachmentname+name+File.separator+name+File.separator,zip,childtype,childSize,request,response,inlinecss,inlinejs,imagepath,loggedInUserId);
																		}
																		if(childtype.equals(INTERFACE_FRAGMENT_TYPE))
																		{
																			uploadInterfaceFragment(attachmentname,attachmentname+name+File.separator+name+File.separator,zip,childtype,childSize,request,response,inlinecss,inlinejs,imagepath,loggedInUserId);
																		}
																	}
																	else
																	{
																		DataBaseLayer.insertinterfacemanifestassociation(interfaceid,manifestid);
																		DataBaseLayer.insertinterface(interfaceid,interface_title,childtype,childSize);
																		DataBaseLayer.FrameworkFile1(interfaceid,interface_title,zip,childtype,"0");
		
																	}
														}
											}
											catch (SAXException e) {
												e.printStackTrace();
											} 
											catch (IOException e1) {
												e1.printStackTrace();
											} 
		
					}
		
			public void uploadInterface(String htmlgeneratepath,String attachmentname,String s7,String typecollection,String fsize,HttpServletRequest request, HttpServletResponse response,String inlinecss,String inlinejs,String imagepath,String loggedInUserId)
			{
		
		
				//UnZip(attachmentname+s7); 
				UnZipInterface(attachmentname,s7); 
				String  inFileName=attachmentname+s7; 
				String name = inFileName.substring(inFileName.lastIndexOf(File.separator)+1,inFileName.lastIndexOf("."));                                             
				DOMParser parser = new DOMParser();
				try
				{
					String xmlpath=attachmentname+name+File.separator+name+File.separator+INTERFACE_XML_FILE_NAME;	
		
					parser.parse(attachmentname+name+File.separator+name+File.separator+INTERFACE_XML_FILE_NAME);	
					Document document1 = parser.getDocument();
					NodeList nodelist = document1.getElementsByTagName("interface");
					if(nodelist.getLength() > 0)
					{
						Element e = (Element)nodelist.item(0);
						String interface_id = e.getAttribute("id");	
						String interface_title = e.getAttribute("title");
						DataBaseLayer.deleteall(interface_id);
						//DataBaseLayer.FrameworkFile(interface_id,interface_title,attachmentname,s7,typecollection,fsize,inlinecss,inlinejs);
						DataBaseLayer.FrameworkFile2(interface_id,interface_title,attachmentname,s7,typecollection,fsize,inlinecss,inlinejs,imagepath);
						DataBaseLayer.InsertInterfaceXML(INTERFACE_XML_FILE_NAME,xmlpath, interface_id,"Interfacexml",interface_id+"interfacexml",loggedInUserId) ;    
						DataBaseLayer.insertinterface(interface_id,interface_title,typecollection,fsize);	
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
													for(int x=0; x<formoptionitem.getLength() ;x++){
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
									//Element addel = (Element)addquery.item(addqueryel);
									NodeList insertitemquery = ((Element)addquery.item(addqueryel)).getElementsByTagName("query");	
									for(int el=0; el<insertitemquery.getLength() ;el++)
									{
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
													}catch(Exception e11){}
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
											}catch(Exception e12){} 
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
													}catch(Exception e4){}
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
											try
											{
											sql_value = sql_value.substring(sql_value.lastIndexOf("[")+1,sql_value.indexOf("]"));
											}catch(Exception e5){} 
		
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
													}catch(Exception e6){}
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
											}catch(Exception e7){} 
		
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
									for(int tr=0; tr<treedataitem.getLength() ;tr++){
										Element treedatanode = (Element)treedataitem.item(tr);
										Document doc111= new DocumentImpl();
										org.w3c.dom.Node nd1 =doc111.importNode(treedatanode, true);
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
		
						//////////////////RESOURCE//////////////////////////////	
						NodeList resource = document1.getElementsByTagName("resource");	
						for(int j=0; j<resource.getLength() ; j++){  
							NodeList resourceitem = ((Element)resource.item(j)).getElementsByTagName("resourceitem");	
							for(int i=0; i<resourceitem.getLength() ; i++){
								Element e2 = (Element)resourceitem.item(i);	
								String id = e2.getAttribute("id");	
								String href = e2.getAttribute("href");	
								String type = e2.getAttribute("valuetype");
								String keyvalue = e2.getAttribute("keyvalue");	
								String resourcelocation=e2.getAttribute("resourcelocation");
		
								String resource_path=attachmentname+name+File.separator+name+File.separator; 								
								DataBaseLayer.insertresourceinterface(id,href,resource_path,type,keyvalue,interface_id,resourcelocation,loggedInUserId);	
							}
						}
						//////////////////RESOURCE//////////////////////////////	
		
						/////////////////////SNIPETS////////////////////////////////
						NodeList snippets = document1.getElementsByTagName("snippets");	
						for(int q=0; q<snippets.getLength() ; q++){  
							NodeList snippet = ((Element)snippets.item(q)).getElementsByTagName("snippet");	
							for(int r=0; r<snippet.getLength() ; r++){  
								Element e2 = (Element)snippet.item(r);
								String id = e2.getAttribute("id");		
								String valuesnippet = e2.getFirstChild().getNodeValue();
								DataBaseLayer.insertsnippet(id,valuesnippet,interface_id);	
							}
						}
						/////////////////////SNIPETS////////////////////////////////
						//////////////////////PAGE CREATION//////////////////////////
						String htmlString="";
						Vector toltallayout=DataBaseLayer.getLayout_ID(interface_id);
						
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
		
								}
							catch(Exception e1)
							{
								e1.printStackTrace();	
							}
							//DataBaseLayer.insetHTML(interface_id,layout_id,content_id,behaviour_id,style_id,htmlgeneratepath+"inreface.html");
							DataBaseLayer.insetHTML(interface_id,layout_id,content_id,behaviour_id,style_id,htmlString);
						}
							
						//////////////////////PAGE CREATION//////////////////////////
					
					}	
		
				}
				catch (SAXException e) {
					e.printStackTrace();
				} 
				catch (IOException e1) {
					e1.printStackTrace();
				} 
			}
		
		
		
			public void uploadRoleXML( String interface_id,String attachmentname,String s7,String typecollection,String fsize)
						{
										String  inFileName=attachmentname+s7; 
										DOMParser parser2 = new DOMParser();
										try
										{
											DataBaseLayer.updateRoleXML(interface_id,attachmentname,s7,typecollection,fsize);
											DataBaseLayer.DeleteinterfaceRole();
											parser2.parse(inFileName);	
											Document document3 = parser2.getDocument();
											NodeList nodelistroles = document3.getElementsByTagName("roles");
											NodeList nodelistrole = ((Element)nodelistroles.item(0)).getElementsByTagName("role");	
											for(int x1=0; x1<nodelistrole.getLength() ; x1++)
											{
												Element roleelement = (Element)nodelistrole.item(x1);
												String role_id = roleelement.getAttribute("id");
												NodeList interfacerole = ((Element)nodelistrole.item(x1)).getElementsByTagName("interface");
												for(int x2=0; x2<interfacerole.getLength() ; x2++)
												{
													Element interfaceelement = (Element)interfacerole.item(x2);
													String interfacerole_id = interfaceelement.getAttribute("id");
													String layoutrole_id="";
													String contentrole_id ="";
													String behaviourrole_id="";
													String stylerole_id="";
													NodeList layoutrole = ((Element)interfacerole.item(x2)).getElementsByTagName("layout");
													for(int x3=0; x3<layoutrole.getLength() ; x3++)
													{
														Element layoutelement = (Element)layoutrole.item(x3);
														layoutrole_id = layoutelement.getAttribute("id");
													}	
															
													NodeList contentrole = ((Element)interfacerole.item(x2)).getElementsByTagName("content");
													for(int x4=0; x4<contentrole.getLength() ; x4++)
													{
														Element contentelement = (Element)contentrole.item(x4);
														contentrole_id = contentelement.getAttribute("id");
													}	
															
													NodeList behaviourrole = ((Element)interfacerole.item(x2)).getElementsByTagName("behaviour");
													for(int x5=0; x5<behaviourrole.getLength() ; x5++)
													{
														Element behaviourelement = (Element)behaviourrole.item(x5);
														behaviourrole_id = behaviourelement.getAttribute("id");
													}	
															
													NodeList stylerole = ((Element)interfacerole.item(x2)).getElementsByTagName("style");
													for(int x6=0; x6<stylerole.getLength() ; x6++)
													{
														Element styleelement = (Element)stylerole.item(x6);
														stylerole_id = styleelement.getAttribute("id");
													}	
														
													DataBaseLayer.InsertInterfaceRoleAssociation(role_id,interfacerole_id,layoutrole_id,contentrole_id,behaviourrole_id,stylerole_id);	
												}
											}		
										}
										catch (SAXException e) {
										} 
										catch (IOException e1) {
										} 			
		
					   }
																		
									public void uploadManifest( String interface_id,String attachmentname,String s7,String typecollection,String fsize)
									{
										String  inFileName=attachmentname+s7; 
										DOMParser parser2 = new DOMParser();
										try
										{
											DataBaseLayer.updateManifest(interface_id,attachmentname,s7,typecollection,fsize);
											parser2.parse(inFileName);	
											Document document2 = parser2.getDocument();	
											NodeList nodelistmanifest = document2.getElementsByTagName("manifest");
											Element manifest = (Element)nodelistmanifest.item(0);
											String manifestid = manifest.getAttribute("id");	
											String title=manifest.getAttribute("title");
											String childSize="0";
																	
											NodeList interface1 = ((Element)nodelistmanifest.item(0)).getElementsByTagName("interface"); 
											for(int ma=0; ma<interface1.getLength() ; ma++){
												Element interfaceelement = (Element)interface1.item(ma);		
												String interfaceid= interfaceelement.getAttribute("id");	
												String zip = interfaceelement.getAttribute("zip");
												String interface_title = interfaceelement.getAttribute("title");
												String childtype=interfaceelement.getAttribute("type");    
																		//System.out.println(">>>>>>>>>>>>>>>>>>>>interfaceid>>>>>>>>>>>>>>"+interfaceid); 
												String checkid=DataBaseLayer.CheckInterfaceID(interfaceid);
																		//System.out.println(">>>>>>>>>>>>>>>>>>>>CHECKID>>>>>>>>>>>>>>"+checkid);
												if(checkid==null)
													checkid="";
												if(checkid.equals(""))
												{
																		//DataBaseLayer.FrameworkFile(interfaceid,interface_title,"",zip,childtype,"0");
													DataBaseLayer.insertinterfacemanifestassociation(interfaceid,manifestid);
													DataBaseLayer.insertinterface(interfaceid,interface_title,childtype,"0");
													DataBaseLayer.FrameworkFile1(interfaceid,interface_title,zip,childtype,"0");
		
												}	
											}	
										}
										catch (SAXException e) {
										} 
										catch (IOException e1)  {
										} 		
		
									}
		
									public void deletelayout(HttpServletRequest request, String strCreatedBy, PrintWriter out1)
											throws IOException, ServletException 
									{
											String interface_id=request.getParameter("interface_id");
											String itype=request.getParameter("type");
											if(itype.equals(INTERFACE_COLLECTION_TYPE))
											{
												DataBaseLayer.DeleteinterfaceCollection(interface_id);
												DataBaseLayer.DeleteinterfaceRole();
											}
											else
											{
												DataBaseLayer.deleteall(interface_id);
											}
				
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
			
			public void UnZipInterface(String path,String filename)
			{
		
				String attachmentname=path;	
				try
				{
					String inFileName = path+filename;
					String name = filename.substring(filename.lastIndexOf(File.separator)+1,filename.lastIndexOf("."));
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
			
			/////////////////////////////SUBIR////////////////////////////							
			
			public void updateTemplateConf(HttpServletRequest req, HttpServletResponse res, PrintWriter out,String loggedInUserId) {
				
				String strFileType = "";
				InputStream in;
				
				String interface_id = req.getParameter("interface_id");
				String filename = req.getParameter("filename");
				String type=req.getParameter("type");
				
				String inlinecss = req.getParameter("inlinecss");
				String inlinejs = req.getParameter("inlinejs");
				String imagepath = req.getParameter("imagepath");
				
				inlinecss = (inlinecss == null) ? "no" : inlinecss;
				inlinejs = (inlinejs == null) ? "no" : inlinejs;
				imagepath = (imagepath == null) ? "" : imagepath;
				
				int size = 1;
				String name = interface_id+".zip";
				String path="";
				ResourceBundle rb = ResourceBundle.getBundle("portal",Locale.getDefault());      
				String key1= "xml"; 
				path = rb.getString(key1);
				
				if(type.equals(INTERFACE_TYPE) || type.equals(INTERFACE_FRAGMENT_TYPE)) { 
					collectInterfaceComponents(interface_id,path+interface_id+File.separator+interface_id);
					try {
						FolderZiper fz = new FolderZiper();
						fz.zipFolder(path+interface_id, path+name);
						fz.close();
					} catch(IOException ioe) {
						ioe.printStackTrace();
					}
					
					File f = new File(path+name);
					String strSize = (new Long(f.length())).toString();
					upload(type, path, name, strSize, req, res, inlinecss, inlinejs, imagepath,loggedInUserId);
				}
				else if(type.equals(REFRESH_ALL_TYPE)) { 
					String[] requriedTypes={INTERFACE_TYPE,INTERFACE_FRAGMENT_TYPE};
					Vector vIIDs = DataBaseLayer.getFrameworkData(requriedTypes);
					refreshAllItems(vIIDs, req, res, path, loggedInUserId, null);
				}
				else if(type.equals(INTERFACE_COLLECTION_TYPE)) { 
					Vector vIIDs = DataBaseLayer.getFrameworkData();
					refreshAllItems(vIIDs, req, res, path, loggedInUserId, type);
				}
			}
			
			private void refreshAllItems(Vector vIIDs,HttpServletRequest req, HttpServletResponse res,String path,String loggedInUserId,String type){
				boolean isTypeEmpty=GenericUtil.isEmptyString(type);
				for(int i=0;i<vIIDs.size();i++) {
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
					collectInterfaceComponents(i_id,path+i_id+File.separator+i_id);
					try {
						FolderZiper fz = new FolderZiper();
						fz.zipFolder(path+i_id, path+name);
						fz.close();
					} catch(IOException ioe) {
						ioe.printStackTrace();
					}
				
					File f = new File(path+name);
					String strSize = (new Long(f.length())).toString();
					upload(type, path, name, strSize, req, res, inlinecss, inlinejs, imagepath,loggedInUserId);
				}
			}
			private void collectInterfaceComponents(String interface_id, String destDir) {
				
				File f = new File(destDir);
				if(!f.exists())
					f.mkdirs();
				Vector vContent = DataBaseLayer.getModuleSrc(interface_id);
				InputStream in;
				if (vContent!=null) {
					for(int m=0;m<vContent.size();m++){
						try{
							Vector vContent1 = (Vector)vContent.elementAt(m);
							String file_name=(String)vContent1.elementAt(0);
							in = (InputStream)vContent1.elementAt(1);
							FileOutputStream out = new FileOutputStream(destDir+File.separator+file_name);
							int len = 0;
							byte buffer[]= new byte[1024];
							try {
								while ((in != null) && ((len = in.read(buffer)) != -1)) {
									out.write(buffer,0,len);
								}
							}
							finally {
								if (in != null) in.close();
							}
						}catch(IOException ioe){
							System.out.println("***IOException in method collectInterfaceComponents()  : "+ioe.getMessage());
						}
																
					}
				}
			}
			
			public void upload(String type, String path, String fileName, String strSize, HttpServletRequest req, HttpServletResponse res,String inlinecss,String inlinejs,String imagepath,String loggedInUserId) {
				
				if(type.equals(INTERFACE_COLLECTION_TYPE)) {
					uploadInterface(path,path,fileName,INTERFACE_COLLECTION_TYPE,strSize,req,res,inlinecss,inlinejs,imagepath,loggedInUserId);
				}
				if(type.equals(INTERFACE_TYPE)) {
					uploadInterface(path,path,fileName,type,strSize,req,res,inlinecss,inlinejs,imagepath,loggedInUserId);
				}
				if(type.equals(INTERFACE_FRAGMENT_TYPE)) {
					uploadInterfaceFragment(path,path,fileName,type,strSize,req,res,inlinecss,inlinejs,imagepath,loggedInUserId);
				}
			}
			
			public void uploadInterfaceFragment(String htmlgeneratepath,String attachmentname,String s7,String typecollection,String fsize,HttpServletRequest request, HttpServletResponse response,String inlinecss,String inlinejs,String imagepath,String loggedInUserId) {
				
				//UnZip(attachmentname+s7); 
				UnZipInterface(attachmentname,s7); 
				String  inFileName=attachmentname+s7; 
				String name = inFileName.substring(inFileName.lastIndexOf(File.separator)+1,inFileName.lastIndexOf("."));                                             
				DOMParser parser = new DOMParser();
				try
				{
					String xmlpath=attachmentname+name+File.separator+name+File.separator+INTERFACE_XML_FILE_NAME;	
					parser.parse(attachmentname+name+File.separator+name+File.separator+INTERFACE_XML_FILE_NAME);	
					
					Document document1 = parser.getDocument();
					NodeList nodelist = document1.getElementsByTagName("interface");
					if(nodelist.getLength() > 0)
					{
						Element e = (Element)nodelist.item(0);
						String interface_id = e.getAttribute("id");	
						String interface_title = e.getAttribute("title");
						DataBaseLayer.deleteall(interface_id);
						DataBaseLayer.FrameworkFile2(interface_id,interface_title,attachmentname,s7,typecollection,fsize,inlinecss,inlinejs,imagepath);
						DataBaseLayer.InsertInterfaceXML(INTERFACE_XML_FILE_NAME,xmlpath, interface_id,"Interfacefragmentxml",interface_id+"interfacefragmentxml",loggedInUserId) ;    
						DataBaseLayer.insertinterface(interface_id,interface_title,typecollection,fsize);	
						
						/*//////////////////////////////////////////////////////////CONFIGURATION ITEM////////////////////////////////////////
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
						//////////////////////////////////////////////////////////CONFIGURATION ITEM END////////////////////////////////////////*/
						
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
									DataBaseLayer.insertOptionItem(interface_id,interface_id+"_"+part_id,option_id,optionname,optionvalue);
								}
								////////////////////////////////////////////////////////////////////////////////  FOR RADIO/// AND CHECKBOX//////////////////////////////////////////////////////
		
								////////////////////////////////////////////////////////////////////////////////  FOR DB FORM ELEMENT//////////////////////////////////////////////////////
		
								NodeList elementitem = ((Element)part.item(d)).getElementsByTagName("element");	
								for(int el=0; el<elementitem.getLength() ;el++){
									Element el1 = (Element)elementitem.item(el);
									String element_id = el1.getAttribute("id");
									/////////////////////////////////////////////////////////////////////////////////  FOR RADIO IN FORM ELEMENT//////////////////////////////////////////////////////
									NodeList formoptionitem= ((Element)elementitem.item(el)).getElementsByTagName("option");	
									for(int x=0; x<formoptionitem.getLength() ;x++){
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
									DataBaseLayer.InsertDBFormElement(interface_id,interface_id+"_"+part_id,element_id,element_class,element_type,element_key,tabindex,selectindex,modifyindex,insertindex,rows,cols,forlabel,required,minlength,maxlength,equalto,number,email,requiredmess,minlengthmess,maxlengthmess,equaltomess,numbermess,emailmess,element_size);
								}
								
								NodeList addquery = ((Element)part.item(d)).getElementsByTagName("add");
								for(int addqueryel=0; addqueryel<addquery.getLength() ;addqueryel++)
								{
									//Element addel = (Element)addquery.item(addqueryel);
									NodeList insertitemquery = ((Element)addquery.item(addqueryel)).getElementsByTagName("query");	
									for(int el=0; el<insertitemquery.getLength() ;el++)
									{
										Element el1 = (Element)insertitemquery.item(el);
										String insert_id = el1.getAttribute("id");
										String insert_sql = el1.getAttribute("sql");	
										String insert_parameter = el1.getAttribute("parameter");	
										DataBaseLayer.InsertFormInsertQuery(interface_id,interface_id+"_"+part_id,insert_id,insert_sql,insert_parameter);
									
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
										DataBaseLayer.ModifyFormInsertQuery(interface_id,interface_id+"_"+part_id,modify_id,modify_sql,modify_parameter);
									
									}
								}
								
								NodeList deleteitemquery = ((Element)part.item(d)).getElementsByTagName("deletequery");	
								for(int el=0; el<deleteitemquery.getLength() ;el++){
									Element el1 = (Element)deleteitemquery.item(el);
									String delete_id = el1.getAttribute("id");
									String delete_sql = el1.getAttribute("sql");	
									String delete_parameter = el1.getAttribute("parameter");	
									DataBaseLayer.DeleteFormInsertQuery(interface_id,interface_id+"_"+part_id,delete_id,delete_sql,delete_parameter);
									
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
										DataBaseLayer.SelectFormInsertQuery(interface_id,interface_id+"_"+part_id,select_id,select_sql,select_parameter);
									
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
									DataBaseLayer.addSelecetor(interface_id,selector_id,selector_class,domaintype,value,gridrefresh,influence,interface_id+"_"+part_id,contentid,behaviourid,show_choose_one,choose_one_label,choose_one_value);
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
										DataBaseLayer.setEditOption(interface_id,interface_id+"_"+part_id,col_name,type,size,rows,cols,editdomaintype,value,multiple);
									}
									DataBaseLayer.setColModel(interface_id,interface_id+"_"+part_id,col_head,col_name,col_index,col_width,col_editable,hidden,key,required,minval,maxval,dbcolumn,gridhidden,colinfluence,col_email,col_number,custom,custom_func,default_type,default_value);
								}
		
								NodeList keycolumnitem = ((Element)part.item(d)).getElementsByTagName("keycolumn");	
								for(int x=0; x<keycolumnitem.getLength() ;x++){
									Element x1 = (Element)keycolumnitem.item(x);
									String keycolumn_value = x1.getAttribute("value");
									DataBaseLayer.InsertkeyColumn(interface_id,interface_id+"_"+part_id,keycolumn_value);
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
													}catch(Exception e11){}
												}			
																				
												String parameter_value = x1.getAttribute("parameter");
												String message= x1.getAttribute("message");
												String function_name=x1.getAttribute("classname");
												DataBaseLayer.InsertValidationDeleteitem(interface_id,interface_id+"_"+part_id,validationtype,id_value,sql_value,parameter_value,message,function_name);
																				
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
											}catch(Exception e12){} 
											String parameter_value = x1.getAttribute("parameter");
											DataBaseLayer.InsertDeleteitem(interface_id,interface_id+"_"+part_id,id_value,sql_value,parameter_value);
															
										}
												
									}
									NodeList actionitem = ((Element)deleteitem.item(x)).getElementsByTagName("action");	
									for(int y1=0; y1<actionitem.getLength() ;y1++)
									{
										Element x1 = (Element)actionitem.item(y1);
										String sequence = x1.getAttribute("sequence");
										String actionname = x1.getAttribute("actionname");
										DataBaseLayer.Insertdelete_action_param(interface_id,interface_id+"_"+part_id,sequence,actionname);
											
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
													}catch(Exception e4){}
												}			
																				
												String parameter_value = x1.getAttribute("parameter");
												String message= x1.getAttribute("message");
												String function_name=x1.getAttribute("classname");
												DataBaseLayer.InsertValidationAdditem(interface_id,interface_id+"_"+part_id,validationtype,id_value,sql_value,parameter_value,message,function_name);
																				
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
											try
											{
												sql_value = sql_value.substring(sql_value.lastIndexOf("[")+1,sql_value.indexOf("]"));
											}catch(Exception e5){} 
		
											System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>sql_value>>>>>>>>>>>>>>>>>>>>>>>>>>>"+sql_value);
											String parameter_value = x1.getAttribute("parameter");
											DataBaseLayer.InsertAdditem(interface_id,interface_id+"_"+part_id,id_value,sql_value,parameter_value);
															
										}
												
												
									}
									NodeList actionitem = ((Element)additem.item(x)).getElementsByTagName("action");	
									for(int y1=0; y1<actionitem.getLength() ;y1++)
									{
										Element x1 = (Element)actionitem.item(y1);
										String sequence = x1.getAttribute("sequence");
										String actionname = x1.getAttribute("actionname");
										DataBaseLayer.Insertadd_action_param(interface_id,interface_id+"_"+part_id,sequence,actionname);
											
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
													}catch(Exception e6){}
												}			
																				
												String parameter_value = x1.getAttribute("parameter");
												String message= x1.getAttribute("message");
												String function_name=x1.getAttribute("classname");
												DataBaseLayer.InsertValidationModifyitem(interface_id,interface_id+"_"+part_id,validationtype,id_value,sql_value,parameter_value,message,function_name);
																				
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
											}catch(Exception e7){} 
		
											System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>sql_value>>>>>>>>>>>>>>>>>>>>>>>>>>>"+sql_value);
											String parameter_value = x1.getAttribute("parameter");
											DataBaseLayer.InsertModifyitem(interface_id,interface_id+"_"+part_id,id_value,sql_value,parameter_value);
															
										}
												
												
									}
		
									NodeList actionitem = ((Element)modifyitem.item(x)).getElementsByTagName("action");	
									for(int y1=0; y1<actionitem.getLength() ;y1++)
									{
										Element x1 = (Element)actionitem.item(y1);
										String sequence = x1.getAttribute("sequence");
										String actionname = x1.getAttribute("actionname");
										DataBaseLayer.Insertmodify_action_param(interface_id,interface_id+"_"+part_id,sequence,actionname);
											
									}		
										
								}
						
								NodeList queryitem = ((Element)part.item(d)).getElementsByTagName("loadquery");	
								for(int x=0; x<queryitem.getLength() ;x++){
									Element x1 = (Element)queryitem.item(x);
									String query_value = x1.getAttribute("value");
									String load_parameter = x1.getAttribute("parameter");
									DataBaseLayer.InsertQueryitem(interface_id,interface_id+"_"+part_id,query_value,load_parameter,ServerCacheGrid,ServerCacheKeyIncludeUserId);
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
								DataBaseLayer.insertpart(interface_id,interface_id+"_"+part_id,part_class,resize,border,cols,rows,scrolling,spacing,colspan,maxlength,size,tabindex,archieve,codebase,mayscript,loadurl,editurl,caption,sortname,sortorder,treedataremotefunction,onselectremotefunction,autocollapse,initialiseonload,gridhidden,gridnavbar,grid_multiselect,rownum,rowlist,dateformat,tree_lazynode,tree_tooltip,grid_multiboxonly,tree_parentquery,tree_childquery,tree_parameter,resetSearchOnClose,multiplesearch,customeditbutton,griddata,griddatatype);
								DataBaseLayer.insertformelement(interface_id,interface_id+"_"+part_id,jscontrol,formaction,formmethod);
								//////////////////////////////////FOR STATIC TREE /////////////////////////
								NodeList treedataitem = ((Element)part.item(d)).getElementsByTagName("treedata");
								if(treedataitem.getLength()>0)
								{	
									for(int tr=0; tr<treedataitem.getLength() ;tr++){
										Element treedatanode = (Element)treedataitem.item(tr);
										Document doc111= new DocumentImpl();
										org.w3c.dom.Node nd1 =doc111.importNode(treedatanode, true);
										doc111.appendChild(nd1); 
										OutputFormat  format1  = new OutputFormat( doc111 );         
										StringWriter  stringOut1 = new StringWriter(); 
										XMLSerializer    serial1 = new XMLSerializer( stringOut1, format1 );
										serial1.asDOMSerializer();   
										serial1.serialize( doc111.getDocumentElement() );
										String xml11= stringOut1.toString();
										DataBaseLayer.InsertTreeDataitem(interface_id,interface_id+"_"+part_id,xml11);
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
								//System.out.println("............................."+Pnodename1);
								String part_id = e2.getAttribute("id");	
								String height = e2.getAttribute("height");	
								String width = e2.getAttribute("width");	
								String left = e2.getAttribute("left");	
								String top = e2.getAttribute("top");	
								String position = e2.getAttribute("position");	
								String parent_id = e2.getAttribute("parent_id");
								//System.out.println(".....................PARENT_ID..........."+parent_id);
								if(parent_id.equals(""))
								{
									
								}
								else
								{
									parent_id=interface_id+"_"+parent_id;
								}
								//System.out.println(".....................PARENT_ID.1.........."+parent_id);
		
								DataBaseLayer.insertlayout(layout_id,interface_id+"_"+part_id,height,width,left,top,position,parent_id,interface_id);			
												
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
									DataBaseLayer.insertDropdownItem(interface_id,interface_id+"_"+part_id,dropdownname,dropdownvalue);
								}
								////////////////////////////////////////////////////////////////////////////////  FOR DROPDOWN END//////////////////////////////////////////////////////
		
								String classtype=DataBaseLayer.getClass(interface_id,interface_id+"_"+part_id);
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
										
										DataBaseLayer.insertChart(interface_id,interface_id+"_"+part_id,content_id,matric_title,description,X_axis_query,series1_query,series2_query,series3_query,legenddata1,legenddata2,legenddata3,chart_type,subtype,width,height,yaxis_label,color,transpose,stacked,chart_dimension,xaxis_label);
													
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
		
											DataBaseLayer.AddReportParameter(interface_id,interface_id+"_"+part_id,content_id,parameter_name,parameter_value,valuesourceitemname,parameter_value_type);														}
										
									}
									DataBaseLayer.ReportContent(interface_id,interface_id+"_"+part_id,content_id,viewer_type,report_chooser,report_executer,rpt_file);
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
											DataBaseLayer.AddFlashParameter(interface_id,interface_id+"_"+part_id,content_id,parameter_name,parameter_value);														
										}
												
									}
									      
								}
								///////////////////////////////////////////////// FOR FLASH COMPONENT///////////////////////	
								
								if(valuetype.equals("cdata"))
								{
									NodeList fstNm = e2.getChildNodes();
									value=(fstNm.item(0)).getNodeValue();	
									DataBaseLayer.insertcontentcdata(content_id,interface_id+"_"+part_id,value,valuetype,interface_id,contentlocation);
									//DataBaseLayer.insertcontent(content_id,interface_id+"_"+part_id,value,valuetype,interface_id,contentlocation,show_choose_one,choose_one_label,choose_one_value);	
		
								}
								else
								{
									DataBaseLayer.insertcontent(content_id,interface_id+"_"+part_id,value,valuetype,interface_id,contentlocation,show_choose_one,choose_one_label,choose_one_value);	
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
																
								DataBaseLayer.insertstyle(style_id,interface_id+"_"+part_id,value,valuetype,interface_id);	
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
									
									DataBaseLayer.insertbehaviour(behaviour_id,interface_id+"_"+part_id,value,valuetype,target,event1,interface_id,type,resourceid,callback,javaclass,packagename,resourcelocation);
									
								}	
							}
						}
						//////////////////BEHAVIOUR//////////////////////////////
															
						//////////////////RESOURCE//////////////////////////////////////	
						NodeList resource = document1.getElementsByTagName("resource");	
						for(int j=0; j<resource.getLength() ; j++){  
							NodeList resourceitem = ((Element)resource.item(j)).getElementsByTagName("resourceitem");	
							for(int i=0; i<resourceitem.getLength() ; i++){
								Element e2 = (Element)resourceitem.item(i);	
								String id = e2.getAttribute("id");	
								String href = e2.getAttribute("href");	
								String type = e2.getAttribute("valuetype");
								String keyvalue = e2.getAttribute("keyvalue");	
								String resourcelocation=e2.getAttribute("resourcelocation");
								String resource_path=attachmentname+name+File.separator+name+File.separator; 								

								DataBaseLayer.insertresourceinterface(id,href,resource_path,type,keyvalue,interface_id,resourcelocation,loggedInUserId);	
							}
						}
						//////////////////RESOURCE//////////////////////////////
						
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
							try
							{
								htmlString=de.show(response);
							}
							catch(Exception e1)
							{
								e1.printStackTrace();
							}
							
							String htmlsnippet="";
							try
							{
//			 					DataBaseLayer.insetHTML(interface_id,layout_id,content_id,behaviour_id,style_id,htmlgeneratepath+"inreface.html");
								DataBaseLayer.insetHTML(interface_id,layout_id,content_id,behaviour_id,style_id,htmlString);

							}
							catch(Exception f){f.printStackTrace();}
						}
							
						//////////////////////PAGE CREATION//////////////////////////
					}	
		
				}
				catch (SAXException e) {
					e.printStackTrace();
				} 
				catch (IOException e1) {
					e1.printStackTrace();
				} 
			}
			
			/////////////////////////////SUBIR////////////////////////////
			
		}
