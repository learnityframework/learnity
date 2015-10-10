package coreadministrationv2.sysmgmt;

/**
 * Title:        Learnity Interface Management   
 * Description:
 * Copyright:    Copyright (c) 2008
 * Company:      Aunwesha
 * @author Shibaji Chatterjee
 */
import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.ecs.*;
import org.apache.ecs.html.*;
import comv2.aunwesha.JSPGrid.*;
import comv2.aunwesha.param.*;
import org.grlea.log.*;
import coreadministrationv2.utility.*;
import coreadministrationv2.dbconnection.DataBaseLayer;

public class InterfaceRoleManagement extends HttpServlet {

	private static final String LOGIN_SESSION_NAME = "ADMIN_LOG_ON";
	private static final String OBJ = "OBJ";
	private static final SimpleLogger log = new SimpleLogger(InterfaceRoleManagement.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        response.setContentType("text/html");

        // Disable the browser cache
        response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
        PrintWriter out = response.getWriter();
		String strAdminId="";
        /***************************************************************************************************/
        /*                                      Check Authentication                                       */
        /***************************************************************************************************/
        HttpSession mysession=request.getSession(true);
		Object obj = mysession.getAttribute(LOGIN_SESSION_NAME);
		if (obj ==null)
			response.sendRedirect("../servlets/login.html");
        else {
        	strAdminId = obj.toString();
        	String strPrmAddModDel = request.getParameter("prmAddModify");

        	//String strAdministratorPreviledge = DataBaseLayer.getAdminstratorPreviledge(strAdminId);
        	//if (strAdministratorPreviledge != null) {
	        	if (strPrmAddModDel!=null) {
		        	int iPrmAddModify = Integer.parseInt(strPrmAddModDel);
			        switch(iPrmAddModify) {

			        	case 0:
				        	
				        		add(request, strAdminId, out);
				        	        break;
			        	case 1:
			        		
			        		//	modify(request, strAdminId,out);
			        		//        break;
			        	case 2:
			        		
			        		//	delete(request, out);
			        		//        break;
			        		
			        	
					}
				}
	        
        	
        }
   
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
        //String strProgramID = request.getParameter("ProgramID");
        //String Program_Identifier = request.getParameter("Program_Identifier");
        //log.debug("**************Program_Identifier*********************"+Program_Identifier);
        //String Program_Unit_ID = request.getParameter("Program_Unit_ID");
        //log.debug("**************Program_Unit_ID*********************"+Program_Unit_ID);
        
        String role=request.getParameter("role");
        if(role==null)
        {
           role="";
        }
        String interfaceid=request.getParameter("interfaceid");
	 if(interfaceid==null)
        {
           interfaceid="";
        }
        String javaScript = "\n	var index = 0;"+
        					"\n	var rowId = 0;"+
        					//"\n var ProgramID = \""+strProgramID+"\";"+
        					"\n var program_comp_ident=0;"+
        					"\n	function findRow(){"+
        					"\n		for (var counter=0; counter<document.frm.checkbox.length; counter++) {"+
            				"\n			if (document.frm.checkbox[counter].value == user) {"+
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
            				"\n				alert(\'You must select at least one file type\');"+
            				"\n				return false;"+
        					"\n			}"+
        					"\n		}"+
        					"\n		if(i==1)  {"+
            				"\n			if (document.frm.checkbox.checked == true) {"+
                			"\n				filledIn = true;"+
							"\n			}"+
        					"\n			if (filledIn == false){"+
            				"\n				alert(\'You must select at least one file type\');"+
            				"\n				return false;"+
        					"\n			}"+
        					"\n		}"+
        					"\n		return true;"+
    			    		"\n	}"+
    			    		"\n"+
        					"\n	function add_onclick(){"+
        				
        					"\n			document.frm.method=\"post\";"+
        					"\n			document.frm.action = \"coreadministrationv2.sysmgmt.InterfaceRoleManagement?prmAddModify=0\";" + 
        					"\n			document.frm.submit();"+
        					
        					"\n	}"+
        					"\n	function modify_onclick() {"+
        		     		"\n		var i = test();"+
        					"\n		if(i>1) {"+
							
							"\n			if((checkEntries()) && (validate())){"+
							"\n				document.frm.method=\"post\";"+
							
							"\n				document.frm.action = \"coreadministrationv2.sysmgmt.InterfaceRoleManagement?prmAddModify=1&user=\"+document.frm.id.value;"+
							"\n				document.frm.submit();"+
							"\n			}"+
							"\n		}"+
        					"\n		if(i==1) {"+
							
							"\n			if(checkEntries()){"+
							"\n				document.frm.method=\"post\";"+
							"\n				document.frm.action = \"coreadministrationv2.sysmgmt.InterfaceRoleManagement?prmAddModify=1&user=\"+document.frm.id.value;"+
							"\n				document.frm.submit();"+
							"\n			}"+

        					"\n		}"+
							"\n	}"+
							
							
							"\n	function delete_onclick() {"+
        		     		"\n		var i = test();"+
        					"\n		if(i>1) {"+
							"\n			if(!checkEntries())"+
							"\n				return false;"+
							"\n			doyou = confirm(\"Are you Sure to Delete The Selected Hierarchy?\"); //Your question."+
							"\n"+
							"\n			if (doyou == true) {"+
							"\n				document.frm.method=\"post\";"+
							"\n				document.frm.action = \"coreadministrationv2.sysmgmt.InterfaceRoleManagement?prmAddModify=2&user=\"+document.frm.id.value;"+
							"\n				document.frm.submit();"+
							"\n			}"+
							"\n			else {"+
							"\n			}"+
							"\n		}"+
        					"\n		if(i==1) {"+
							"\n			if(!checkEntries())"+
							"\n				return false;"+
							"\n			doyou = confirm(\"Are you Sure to Delete The Selected Program Hierarchy?\"); //Your question."+
							"\n"+
							"\n			if (doyou == true) {"+
							"\n				document.frm.method=\"post\";"+
							"\n				document.frm.action = \"coreadministrationv2.sysmgmt.InterfaceRoleManagement?prmAddModify=2&user=\"+document.frm.id.value;"+
							"\n				document.frm.submit();"+
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
                                                       // "\n                             var select = document.frm.checkbox[counter].value;"+
	        					"\n                             document.frm.content.value=document.frm.hcontentid[counter].value;"+
	        					 "\n                             document.frm.layout.value= document.frm.hlayout[counter].value;"+
	        					 "\n                             document.frm.style.value=document.frm.hstyle[counter].value;"+
							 "\n                             document.frm.behaviour.value=document.frm.hbehaviour[counter].value;"+
							 "\n					break;"+
							 "\n				}"+
							 "\n			}"+
							 "\n		}"+
							 "\n		if (i==1) {"+
        					         "\n			if(document.frm.checkbox.checked) {"+
        					        "\n                             document.frm.content.value=document.frm.hcontentid.value;"+
	        					 "\n                             document.frm.layout.value= document.frm.hlayout.value;"+
	        					 "\n                             document.frm.style.value=document.frm.hstyle.value;"+
							 "\n                             document.frm.behaviour.value=document.frm.hbehaviour.value;"+
        					
							"\n			}"+
							"\n		}"+
							"\n	}"+
							"\n"+

        					"\n	function checkID(){"+
        		     		"\n		var i = test();"+
        					"\n		if(i>1) {"+
        					"\n			if(document.frm.checkbox[index].value!=document.frm.Program.value){"+
        					"\n				alert(\"You cannot change the Program\");"+
        					"\n				document.frm.Program.value=document.frm.checkbox[index].value;"+
        					"\n			}"+
        					"\n		} "+
        					"\n		if(i==1) {"+
        					"\n			if(document.frm.checkbox.value!=document.frm.Program.value){"+
        					"\n				alert(\"You cannot change the Program\");"+
        					"\n				document.frm.Program.value=document.frm.checkbox.value;"+
        					"\n			}"+
        					"\n		}"+
        					"\n		return true;"+
        					"\n	}"+
        					
        					"\n	function validate(){"+
        					 
   							"\n		if(!fnCheckZero(document.frm.user.value,\" ProgramUnit\")){"+
   							"\n			document.frm.user.focus();"+
     						"\n			return false;"+
   							"\n		}"+
   							"\n		return true;"+
   							"\n	}"+
   							
   						"\n	function select_user_onchange() {"+
        					        
        					       "\n		document.frm.method=\"post\";"+
        					       
        					       "\n		document.frm.action=\"coreadministrationv2.sysmgmt.InterfaceRoleManagement?role=\"+document.frm.role.value;" + 
        					      
        					       "\n		document.frm.submit();"+
        					        "\n	}"+
        			                        
   							
   							"\n function load() {"+
        					      
							"\n	}";
							
							
	    Option[] option1 = null;
	    Vector parentrole = DataBaseLayer.getRole();
	    if(parentrole==null) {
	    	option1 = new Option[1];
	    	option1[0] = new Option("0").addElement("[Choose One]");
	    }
	    else {
	    	option1 = new Option[parentrole.size()+1];
	    	option1[0] = new Option("0").addElement("[Choose One]");
	    	for(int i=0; i<parentrole.size(); i++) {
	    		Vector parentrolesub = (Vector) parentrole.elementAt(i);
	    		String roleid = (String) parentrolesub.elementAt(0);
	    		String rolename = (String) parentrolesub.elementAt(1);
	    		option1[i+1] = new Option(roleid).addElement(rolename);
	    		if(role.equals(roleid))
				{
				option1[i + 1].setSelected(true);
				}
	    	   	}
	    }							
		
		
		
		Option[] option2 = null;
	    Vector interfacev = DataBaseLayer.getInterface(role);
	    if(interfacev==null) {
	    	option2 = new Option[1];
	    	option2[0] = new Option("0").addElement("[Choose One]");
	    }
	    else {
	    	option2 = new Option[interfacev.size()+1];
	    	option2[0] = new Option("0").addElement("[Choose One]");
	    	for(int i=0; i<interfacev.size(); i++) {
	    		Vector interfacevsub = (Vector) interfacev.elementAt(i);
	    		String Interfaceid = (String) interfacevsub.elementAt(0);
	    		
	    		String interfacetitle = (String) interfacevsub.elementAt(1);
	    		
	    		option2[i+1] = new Option(Interfaceid).addElement(interfacetitle);
	    		if(interfaceid.equals(Interfaceid))
				{
				option2[i + 1].setSelected(true);
				}
	    		
	    	}
	    }							
		
	Option[] option3 = null;
	    Vector layoutv = DataBaseLayer.getLayout(interfaceid);
	    if(layoutv==null) {
	    	option3 = new Option[1];
	    	option3[0] = new Option("0").addElement("[Choose One]");
	    }
	    else {
	    	option3 = new Option[layoutv.size()+1];
	    	option3[0] = new Option("0").addElement("[Choose One]");
	    	for(int i=0; i<layoutv.size(); i++) {
	    		Vector layoutvsub = (Vector) layoutv.elementAt(i);
	    		String layoutid = (String) layoutvsub.elementAt(0);
	    		
	    		String layouttitle = (String) layoutvsub.elementAt(1);
	    		
	    		option3[i+1] = new Option(layoutid).addElement(layouttitle);
	    		
	    		
	    	}
	    }								
	
         Option[] option4 = null;
	    Vector contentv = DataBaseLayer.getContent(interfaceid);
	    if(contentv==null) {
	    	option4 = new Option[1];
	    	option4[0] = new Option("0").addElement("[Choose One]");
	    }
	    else {
	    	option4 = new Option[contentv.size()+1];
	    	option4[0] = new Option("0").addElement("[Choose One]");
	    	for(int i=0; i<contentv.size(); i++) {
	    		Vector contentvsub = (Vector) contentv.elementAt(i);
	    		String contentid = (String) contentvsub.elementAt(0);
	    		
	    		String contenttitle = (String) contentvsub.elementAt(1);
	    		
	    		option4[i+1] = new Option(contentid).addElement(contenttitle);
	    		
	    		
	    	}
	    }								
					

             Option[] option5 = null;
	    Vector stylev = DataBaseLayer.getStyle(interfaceid);
	    if(stylev==null) {
	    	option5 = new Option[1];
	    	option5[0] = new Option("0").addElement("[Choose One]");
	    }
	    else {
	    	option5 = new Option[stylev.size()+1];
	    	option5[0] = new Option("0").addElement("[Choose One]");
	    	for(int i=0; i<stylev.size(); i++) {
	    		Vector stylevsub = (Vector) stylev.elementAt(i);
	    		String styleid = (String) stylevsub.elementAt(0);
	    		
	    		String styletitle = (String) stylevsub.elementAt(1);
	    		
	    		option5[i+1] = new Option(styleid).addElement(styletitle);
	    	}
	    }																

	   Option[] option6 = null;
	    Vector behaviourv = DataBaseLayer.getBehaviour(interfaceid);
	    if(behaviourv==null) {
	    	option6 = new Option[1];
	    	option6[0] = new Option("0").addElement("[Choose One]");
	    }
	    else {
	    	option6 = new Option[behaviourv.size()+1];
	    	option6[0] = new Option("0").addElement("[Choose One]");
	    	for(int i=0; i<behaviourv.size(); i++) {
	    		Vector behaviourvsub = (Vector) behaviourv.elementAt(i);
	    		String behaviourid = (String) behaviourvsub.elementAt(0);
	    		
	    		String behaviourtitle = (String) behaviourvsub.elementAt(1);
	    		
	    		option6[i+1] = new Option(behaviourid).addElement(behaviourtitle);
	    	}
	    }							
	
        Body body = new Body();
        Form form = new Form();
	    Input inputButton1 = new Input();
	    Input inputButton2 = new Input();
	    Input inputButton3 = new Input();
	   
  	      Html html = new Html()
              .addElement(new Head()
                    .addElement(new Title("Master Administration"))
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
				  			.headerTable("<b>System Administrator: </b>"+strAdminId, strDate, strTime, "<b>System Administration: </b>Interface Role Management"))));
	    
		
		  
		  
		  
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
								
										
									.addElement(new TD()
									  .addElement(new Table()
										.setBorder(0)
										.setCellPadding(0)
										.setCellSpacing(0)
										.addElement(new TBody()
											.addElement(new TRExtension()
												.add())
											.addElement(new TRExtension()
												.addSelect("Select Role","role","1", option1,"select_user_onchange()"))
											
											.addElement(new TRExtension()
												.addSelect("Select Interface","interfaceid","2", option2,"select_user_onchange()"))
											)))
									
											
																								
												)));
		  
		  
		  
		     String sql = "select  a.role_id as \"Select\",a.role_id as \"Role ID\",a.interface_id as \"Interface ID\",a.content_id as \"Content ID\",a.layout_id as \"Layout ID\",a.style_id as \"Style ID\",a.behaviour_id as \"Behaviour ID\" from roleassociation a  where a.role_id='"+role+"' and a.interface_id='"+interfaceid+"'";
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
			grid1.setCaption("Currently Defined Interface role Association");
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

			grid1.Cols(0).setFieldName("checkbox");
                        grid1.Cols(1).setFieldName("hroleid");
			grid1.Cols(2).setFieldName("hinterfaceid");
			grid1.Cols(3).setFieldName("hcontentid");
			grid1.Cols(4).setFieldName("hlayout");
			grid1.Cols(5).setFieldName("hstyle");
			grid1.Cols(6).setFieldName("hbehaviour");

			grid1.Cols(0).Header().setClassID("swb");
			grid1.Cols(1).Header().setClassID("swb");
			grid1.Cols(2).Header().setClassID("swb");
			grid1.Cols(3).Header().setClassID("swb");
			grid1.Cols(4).Header().setClassID("swb");
			grid1.Cols(5).Header().setClassID("swb");
			grid1.Cols(6).Header().setClassID("swb");

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
				form.addElement("<p>Total No. Of  Interface Role Association : " +grid1.getRows());
				form.addElement(grid1.getGrid());
			}	
		}
		catch (Exception exp) {
			
		}	
		
		
               
                    
                    
              
	    form.addElement("<input type=\"hidden\" name=\"id\">");
	    form.addElement("<input type=\"hidden\" name=\"roleid\">");
	    
	   
	    
	    
                
		form.addElement(new TableExtension()
				.add()
				.addElement(new Table()
					.setBorder(0)
					.setCellPadding(0)
					.setCellSpacing(0)
					.setWidth("100%")
					.addElement(new TRExtension()
						.addLine())
					.addElement(new TR()
						.addElement(new TD()
							.setBgColor("023264")
							.setWidth(1))
						.addElement(new TD()
						
						       .addElement(new TableExtension()
										.addSelect("Select Layout", "layout", "1", option3))
							
							  .addElement(new TableExtension()
										.addSelect("Select Content", "content", "1", option4))
							  .addElement(new TableExtension()
										.addSelect("Select Style", "style", "1", option5))
							  .addElement(new TableExtension()
										.addSelect("Select Behaviour", "behaviour", "1", option6))
							
							.addElement(new TableExtension()
								.add())
												
							
							
							.addElement(new Table()
									.setBorder(0)
									.setCellPadding(0)
									.setCellSpacing(0)
									.addElement(new TBody()
										.addElement(new TR()
												.addElement(new TD()
								 					.setRowSpan(3)
								 					.addElement(new IMG()
								 						.setBorder(0)
														.setHeight(8)
														.setWidth(10)
														.setSrc("../coreadmin/images/T.gif")))
												.addElement(new TD()
													.setHeight(5)))
											
												))
							
							
							.addElement(new TableExtension()
								.add())
							.addElement(new Table()
								.setBorder(0)
								.setCellPadding(0)
								.setCellSpacing(0)
								.setWidth("%100")
								.addElement(new TBody()
									.addElement(new TR()
										.addElement("<TD><IMG border=0 height=5 src=\"../images/T.gif\" width=10></TD>")
										.addElement("<TD width=\"100%\"><font color=\"#FF0000\">* Required Fields.</font></TD>"))))
								)
						.addElement(new TD()
							.setBgColor("023264")
							.setWidth("1")))
					.addElement(new TRExtension()
						.addLine()))
			.addElement(new TableExtension()
				.add()));
				form.addElement(new Table()
					.setBorder(0)
					.setCellPadding(0)
					.setCellSpacing(0)
					.addElement(new TR()
						.addElement(new TD()
							.addElement(inputButton1
								.setClassId("sbttn")
								.setName("add")
								.setTabindex(2)
								.setTitleValue("SET")
								.setType("button")
								.setValue("SET")))
						.addElement(new TD()
							.setWidth(5))
// 						.addElement(new TD()
// 							.addElement(inputButton2
// 								.setClassId("sbttn")
// 								.setName("modify")
// 								.setTabindex(2)
// 								.setTitleValue("Modify")
// 								.setType("button")
// 								.setValue("Modify")))
// 						.addElement(new TD()
// 							.setWidth(5))
// 						.addElement(new TD()
// 							.addElement(inputButton3
// 								.setClassId("sbttn")
// 								.setName("delete")
// 								.setTabindex(2)
// 								.setTitleValue("Delete")
// 								.setType("button")
// 								.setValue("Delete")))
// 						.addElement(new TD()
// 							.setWidth(5))
// 						
						.addElement(new TD()
							.setWidth(5))
							
						
						));
		body.setOnLoad("scrollit(100);load()");
        inputButton1.setOnClick("add_onclick();");
//         inputButton2.setOnClick("modify_onclick();");
//         inputButton3.setOnClick("delete_onclick();");
        out.println(html.toString());
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        	throws IOException, ServletException {
        doGet(request, response);
    }
    public void add(HttpServletRequest request, String strCreatedBy, PrintWriter out1)
        	throws IOException, ServletException {
                String role = request.getParameter("role");
                String interfaceid = request.getParameter("interfaceid");
		String layout = request.getParameter("layout");
		String content = request.getParameter("content");
		String style = request.getParameter("style");
		String behaviour = request.getParameter("behaviour");
                DataBaseLayer.UpdateInterfaceRoleAssociation(role,interfaceid,layout,content,style,behaviour);
    }
     public void modify(HttpServletRequest request, String strModBy,PrintWriter out1)
        	throws IOException, ServletException {
        	
        	/*
                String user = request.getParameter("user");
                String roleid = request.getParameter("roleid");
                String role = request.getParameter("role");
                DataBaseLayer.modifyuserrole(user,roleid,role,strModBy);*/
        
    }
    public void delete(HttpServletRequest request, PrintWriter out1)
        	throws IOException, ServletException {
        	// String user = request.getParameter("user");
               // String roleid = request.getParameter("roleid");
        	//System.out.println("................................user................."+user);
               
        	//System.out.println("................................functionid................"+functionid);
        	//DataBaseLayer.deleteuserrole(user,roleid);
        
    }
    
     
}