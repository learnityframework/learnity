package coreadministrationv2.sysmgmt;

/**
 * Title:        Learnity Interface Management   
 * Description:
 * Copyright:    Copyright (c) 2010
 * Company:      Aunwesha
 * @author Shibaji Chatterjee
 */




import interfaceenginev2.display.DisplayEngine;

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
		String statusMessage="";
		if (obj ==null)
			response.sendRedirect("../coreadmin/login.html");
		else {
			String strAdminId = "superadmin"; //obj.toString();
			String strPrmAddModDel = request.getParameter("prmAddModify");
			String invalid = request.getParameter("invalid");
			String loggedInUserId = mysession.getAttribute(LOGIN_SESSION_NAME).toString();
        	
			if (strPrmAddModDel!=null) {
				int iPrmAddModify = Integer.parseInt(strPrmAddModDel);
				switch(iPrmAddModify) {

					case 0:
						statusMessage=addlayout(request, strAdminId, out,response,loggedInUserId);
						break;
					case 1:
			        		//modifylayout(request, strAdminId, out);
						break;
					case 2:
						statusMessage=deletelayout(request, strAdminId, out);
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
						//sql = "select resource_id as \"Select\" ,href as \"File Name\"  from resource where interface_id='"+interface_id+"' and type='Interfacexml'";
						sql = "select resource_id as \"Select\" ,href as \"File Name\",size as \"File Size\",date_format(date,'%d-%m-%Y %H:%i:%s') as \"Uploaded Date\",uploaded_by as \"Uploaded By\"" +
								"  from resource where interface_id='"+interface_id+"' and type='Interfacexml'";
					}
					else if(type1.equals("Resources"))
					{
						//sql = "select resource_id as \"Select\" ,href as \"File Name\"  from resource where interface_id='"+interface_id+"' and type <>'Interfacexml'";
						sql = "select resource_id as \"Select\" ,href as \"File Name\",size as \"File Size\",date_format(date,'%d-%m-%Y %H:%i:%s') as \"Uploaded Date\",uploaded_by as \"Uploaded By\"" +
								"  from resource where interface_id='"+interface_id+"' and type <> 'Interfacexml'";
					}
					else{
						//sql = "select resource_id as \"Select\" ,href as \"File Name\"  from resource where interface_id='"+interface_id+"'";
						sql = "select resource_id as \"Select\" ,href as \"File Name\",size as \"File Size\",date_format(date,'%d-%m-%Y %H:%i:%s') as \"Uploaded Date\",uploaded_by as \"Uploaded By\"" +
								"  from resource where interface_id='"+interface_id+"'";
						
						
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
						grid1.Cols(2).Header().setClassID("swb");
						grid1.Cols(3).Header().setClassID("swb");
						grid1.Cols(4).Header().setClassID("swb");
			
			
			
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

					public String addlayout(HttpServletRequest request, String strCreatedBy, PrintWriter out1,HttpServletResponse response,String loggedInUserId)
							throws IOException, ServletException {
						String resource_id=request.getParameter("resource_id");
						String type=DataBaseLayer.getType(resource_id);
						String statusMessage=LayoutUploader.addlayout(request, response, strCreatedBy, out1, loggedInUserId, type, false);
						return statusMessage;
					}

							public String deletelayout(HttpServletRequest request, String strCreatedBy, PrintWriter out1)
									throws IOException, ServletException {
								return "Under developement.";
									}



   
}
