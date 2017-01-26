package coreadministrationv2.sysmgmt;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
import org.apache.ecs.html.TextArea;
import org.apache.ecs.html.Title;
import org.grlea.log.SimpleLogger;

import comv2.aunwesha.JSPGrid.JSPGridPro2;

import coreadministrationv2.utility.TRExtension;
import coreadministrationv2.utility.TableExtension;

/**
 * Title:        LearnITy
 * Description:
 * Copyright:    Copyright (c) 2006
 * Company:      Aunwesha
 * @author 	Shibaji Chatterjee	
 * @version 	 
 */

public class RoleManagement extends HttpServlet {

	private static final String LOGIN_SESSION_NAME = "ADMIN_LOG_ON";
	private static final String OBJ = "OBJ";
	private static final SimpleLogger log = new SimpleLogger(RoleManagement.class);

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
		if (obj ==null)
			response.sendRedirect("../coreadmin/login.html");
        else {
        	String strAdminId = obj.toString();
        	String strPrmAddModDel = request.getParameter("prmAddModify");

        	//String strAdministratorPreviledge = coreadministrationv2.dbconnection.DataBaseLayer.getAdminstratorPreviledge(strAdminId);
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
        String role_id = request.getParameter("role_id");
        
        
   
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
					//.addElement(javaScript)))
              .setSrc("../coreadmin/js/coreadministrationv2.sysmgmt/RoleManagement.js")))

              .addElement(body
					.addElement(form
	              		.setName("frm")
				  		.addElement(new TableExtension()
				  			.headerTable("<b>System Administrator: </b>"+strAdminId, strDate, strTime, "<b>System Administration: </b>Role Management"))));
	    
		String sql = "select a.role_id as \"Select\", a.title as \"Title Name\", "+
		             " a.description as \" Description\" "+
		             
		             " from role a ";
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
			grid1.setCaption("Currently Defined Role");
			grid1.setMaxRowsPerPage(5);   		//how many records displayed per page
			grid1.setMaxResultPagesPerLoad(5);  //Page : 1 2 3 4 5 6 7 8 9 10 (max 10 pages displayed)
			grid1.setLineNoHeaderBgColor("#48E6F7");
			grid1.Cols(0).setFieldType(grid1.FIELD_RADIO);
			grid1.Cols(1).setFieldType(grid1.FIELD_HIDDEN);		
			grid1.Cols(2).setFieldType(grid1.FIELD_HIDDEN);	
			
			
			
			
			grid1.Cols(0).setFieldName("checkbox");
			grid1.Cols(1).setFieldName("hiddentitle");
			grid1.Cols(2).setFieldName("hiddendesc");
			
			
			
			
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
				form.addElement("<p>Total No. Of Role: " +grid1.getRows());
				form.addElement(grid1.getGrid());
			}	
			
			//Added by Diptendu 29-Oct-2015
			
			grid1.closeConnection();
			
		}
		catch (Exception exp) {
			
		}	
		
		
		
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
								.addRequired("Role Title","text","roletitle","60","30","1"))
							.addElement(new TableExtension()
								.add())
												.addElement(new Table()
													.setBorder(0)
													.setCellPadding(2)
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
																.setClassId("PPRLabelText")
																.addElement("Description"))
																.addElement(new TD()
								 					                       .setRowSpan(3)
								 					                       .addElement(new IMG()
								 						               .setBorder(0)
														               .setHeight(8)
														               .setWidth(10)
														               .setSrc("../coreadmin/images/T.gif")))
															.addElement(new TD()
																.addElement(new TextArea()
									                                                                      .setRows(5)
									                                                                      .setName("idesc")
									                                                                      .setCols(30)))
									                                               )))
							//.addElement(new TableExtension()
								//.add())
							
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
										.addElement("<TD><IMG border=0 height=5 src=\"../coreadmin/images/T.gif\" width=10></TD>")
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
								.setName("addNewUnit")
								.setTabindex(2)
								.setTitleValue("Add")
								.setType("button")
								.setValue("Add")))
						.addElement(new TD()
							.setWidth(5))
						.addElement(new TD()
							.addElement(inputButton2
								.setClassId("sbttn")
								.setName("modifyUnit")
								.setTabindex(2)
								.setTitleValue("Modify")
								.setType("button")
								.setValue("Modify")))
						.addElement(new TD()
							.setWidth(5))
						.addElement(new TD()
							.addElement(inputButton3
								.setClassId("sbttn")
								.setName("deleteUnit")
								.setTabindex(2)
								.setTitleValue("Delete")
								.setType("button")
								.setValue("Delete")))
						.addElement(new TD()
							.setWidth(5))
						
						.addElement(new TD()
							.setWidth(5))
							
						
						));
		body.setOnLoad("scrollit(100);load()");
        inputButton1.setOnClick("addCourse_onclick();");
        inputButton2.setOnClick("modifyCourse_onclick();");
        inputButton3.setOnClick("deleteCourse_onclick();");
        out.println(html.toString());
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        	throws IOException, ServletException {
        doGet(request, response);
    }
    public void add(HttpServletRequest request, String strCreatedBy, PrintWriter out1)
        	throws IOException, ServletException {
                String roletitle = request.getParameter("roletitle");
        	
                String idesc = request.getParameter("idesc");
                
                coreadministrationv2.dbconnection.DataBaseLayer.addrole(roletitle,idesc);
    }
     public void modify(HttpServletRequest request, String strModBy,PrintWriter out1)
        	throws IOException, ServletException {
        	String role_id = request.getParameter("role_id");
        	
        	String roletitle = request.getParameter("roletitle");
        	
                String idesc = request.getParameter("idesc");
               
                coreadministrationv2.dbconnection.DataBaseLayer.modifyrole(role_id,roletitle,idesc);
        
    }
    public void delete(HttpServletRequest request, PrintWriter out1)
        	throws IOException, ServletException {
        	String role_id = request.getParameter("role_id");
        	
        	coreadministrationv2.dbconnection.DataBaseLayer.deleterole(role_id);
        
    }
    
     
}
