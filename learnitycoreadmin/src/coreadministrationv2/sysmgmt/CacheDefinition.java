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
import org.apache.ecs.html.Input;
import org.apache.ecs.html.Link;
import org.apache.ecs.html.Option;
import org.apache.ecs.html.Script;
import org.apache.ecs.html.TBody;
import org.apache.ecs.html.TD;
import org.apache.ecs.html.TR;
import org.apache.ecs.html.Table;
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

public class CacheDefinition extends HttpServlet {

	private static final String LOGIN_SESSION_NAME = "ADMIN_LOG_ON";
	private static final String OBJ = "OBJ";
	private static final SimpleLogger log = new SimpleLogger(CacheDefinition.class);

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
							String role_id = request.getParameter("role_id");
        
        

					Body body = new Body();
					Form form = new Form();
					Input inputButton1 = new Input();
					Input inputButton2 = new Input();
					Input inputButton3 = new Input();
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
							//.addElement(javaScript)))
							 .setSrc("../coreadmin/js/coreadministrationv2.sysmgmt/CacheDefinition.js")))

							.addElement(body
							.addElement(form
							.setName("frm")
							.addElement(new TableExtension()
							.headerTable("<b>System Administrator: </b>"+strAdminId, strDate, strTime, "<b>System Administration: </b>Cache Definition"))));
					
					
					
					
					Option[] option1 = {new Option("in_process").addElement("In Process"),
						new Option("Remote").addElement("Remote")};
						
					Option[] option2 = {new Option("TRUE").addElement("True"),
						new Option("FALSE").addElement("False")};
						
					Option[] option3 = {new Option("LFU").addElement("LFU"),
						new Option("LRU").addElement("LRU"),
						new Option("FIFO").addElement("FIFO")};
					
					Option[] option4 = {new Option("TRUE").addElement("True"),
						new Option("FALSE").addElement("False")};
					Option[] option5 = {new Option("TRUE").addElement("True"),
						new Option("FALSE").addElement("False")};
						
					Option[] option6 = {new Option("No").addElement("No"),
						new Option("Yes").addElement("Yes")};
					
	    
					String sql = "select a.cache_id as \"Select\", a.cache_name as \"Cache Name\", "+
							" a.cache_type as \" Type\",a.max_element as \"Max Element\",a.overflowtodisk as \"Overflow to Disk\","+
							" a.timetoliveseconds as \"Time to Live\",a.timetoidleseconds as \"Time to Idle\","+
							"a.diskpersistent as \"Disk Persistent\","+
							" a.eternal as \"Eternal\",a.diskexpirythreadintervalseconds as \"Disk Expiry Interval\","+
							"a.memorystoreevictionpolicy as \"Eviction Policy\", "+
							" a.diskstorepath as \"Disk Store Path\",a.default_cache as \"Default\" "+
		             
							" from cache_definition a ";
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
						grid1.setCaption("Currently Defined Cache");
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
						grid1.Cols(8).setFieldType(grid1.FIELD_HIDDEN);		
						grid1.Cols(9).setFieldType(grid1.FIELD_HIDDEN);
						grid1.Cols(10).setFieldType(grid1.FIELD_HIDDEN);		
						grid1.Cols(11).setFieldType(grid1.FIELD_HIDDEN);
						grid1.Cols(12).setFieldType(grid1.FIELD_HIDDEN);
			
			
			
						grid1.Cols(0).setFieldName("checkbox");
						grid1.Cols(1).setFieldName("hiddenname");
						grid1.Cols(2).setFieldName("hiddentype");
						grid1.Cols(3).setFieldName("hiddenmaxelem");
						grid1.Cols(4).setFieldName("hiddenoverflow");
						grid1.Cols(5).setFieldName("hiddentimelive");
						grid1.Cols(6).setFieldName("hiddentimeidle");
						grid1.Cols(7).setFieldName("hiddendiskpersistent");
						grid1.Cols(8).setFieldName("hiddeneternal");
						grid1.Cols(9).setFieldName("hiddenthreadinterval");
						grid1.Cols(10).setFieldName("hiddeneviction");
						grid1.Cols(11).setFieldName("hiddendiskstorepath");
						grid1.Cols(12).setFieldName("hiddendefault");
			
			
			
						grid1.Cols(0).Header().setClassID("swb");
						grid1.Cols(1).Header().setClassID("swb");
						grid1.Cols(2).Header().setClassID("swb");
						grid1.Cols(3).Header().setClassID("swb");
						grid1.Cols(4).Header().setClassID("swb");
						grid1.Cols(5).Header().setClassID("swb");
						grid1.Cols(6).Header().setClassID("swb");
						grid1.Cols(7).Header().setClassID("swb");
						grid1.Cols(8).Header().setClassID("swb");
						grid1.Cols(9).Header().setClassID("swb");
						grid1.Cols(10).Header().setClassID("swb");
						grid1.Cols(11).Header().setClassID("swb");
						grid1.Cols(12).Header().setClassID("swb");
			
			
						grid1.Cols(0).insertFieldScript("onclick=\"CCA();checkbox_onclick();\"");
						grid1.setEachRowHeight("20");
						grid1.canSort(0, false);
						grid1.canSort(1, true);
						grid1.canSort(2, false);
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
							form.addElement("<p>Total No. Of Cache: " +grid1.getRows());
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
										.addElement(new TR()
										.addElement(new TD()
							
											.addElement(new TableExtension()
												.addRequiredNew("Cache Name","text","cachename","60","30","1"))
											.addElement(new TableExtension()
												.add())))
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
														.addSelect("Select Type","cache_type","1", option1))))
													.addElement(new TableExtension()
														.add())))
										
										.addElement(new TR()
										.addElement(new TD()
							
											.addElement(new TableExtension()
												.addRequiredNew("Max Element","text","maxelem","60","30","1"))
											.addElement(new TableExtension()
												.add())))
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
														.addSelect("Overflow to Disk","overflowtodisk","1", option2))))
													.addElement(new TableExtension()
														.add())))
										
										
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
														.addSelect("Eviction Policy","evictionpolicy","1", option3))))
													.addElement(new TableExtension()
														.add())))
										
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
														.addSelect("Eternal","eternal","1", option4))))
													.addElement(new TableExtension()
														.add())))
										
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
														.addSelect("Disk Persistent","diskpersistent","1", option5))))
													.addElement(new TableExtension()
														.add())))
										
										.addElement(new TR()
										.addElement(new TD()
							
											.addElement(new TableExtension()
												.addRequiredNew("Time to Live(seconds)","text","timetolive","60","30","1"))
											.addElement(new TableExtension()
												.add())))
										
										.addElement(new TR()
										.addElement(new TD()
							
											.addElement(new TableExtension()
												.addRequiredNew("Time to Idle(seconds)","text","timetoidle","60","30","1"))
											.addElement(new TableExtension()
												.add())))
										
										
										.addElement(new TR()
										.addElement(new TD()
							
											.addElement(new TableExtension()
												.addRequiredNew("Expiry Thread Interval(seconds)","text","timetothread","60","30","1"))
											.addElement(new TableExtension()
												.add())))
										
										
										.addElement(new TR()
										.addElement(new TD()
							
											.addElement(new TableExtension()
												.addNew("Disk Store Path","text","diskstorepath","60","30","1"))
											.addElement(new TableExtension()
												.add())))
										
										
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
														.addSelect("Default","default_cache","1", option6))))
													.addElement(new TableExtension()
														.add())))
										
										
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
							throws IOException, ServletException 
							{
								String cachename = request.getParameter("cachename");
								String cache_type = request.getParameter("cache_type");
								String maxelem = request.getParameter("maxelem");
								String overflowtodisk = request.getParameter("overflowtodisk");
								String memoryStoreEvictionPolicy = request.getParameter("evictionpolicy");
								String eternal = request.getParameter("eternal");
								String timeToLiveSeconds = request.getParameter("timetolive");
								String timeToIdleSeconds = request.getParameter("timetoidle");
								String diskExpiryThreadIntervalSeconds = request.getParameter("timetothread");
								String diskStorePath = request.getParameter("diskstorepath");
								String diskPersistent = request.getParameter("diskpersistent");
								String default_cache = request.getParameter("default_cache");
								System.out.println("=======default_cache===="+default_cache);
								if(default_cache.equals("Yes"))
								{
									System.out.println("coreadministrationv2.dbconnection.DataBaseLayer.isDefaultCacheExists()====="+coreadministrationv2.dbconnection.DataBaseLayer.isDefaultCacheExists());
									if(coreadministrationv2.dbconnection.DataBaseLayer.isDefaultCacheExists())
										out1.println("<font color=red size=4>Already a default cache exists</font>");
									else
                								coreadministrationv2.dbconnection.DataBaseLayer.addCacheDefinition(cachename,cache_type,maxelem,overflowtodisk,memoryStoreEvictionPolicy,eternal,timeToLiveSeconds,timeToIdleSeconds,diskExpiryThreadIntervalSeconds,diskStorePath,diskPersistent,default_cache);
								}
								else
								{
									coreadministrationv2.dbconnection.DataBaseLayer.addCacheDefinition(cachename,cache_type,maxelem,overflowtodisk,memoryStoreEvictionPolicy,eternal,timeToLiveSeconds,timeToIdleSeconds,diskExpiryThreadIntervalSeconds,diskStorePath,diskPersistent,default_cache);
								}
								
							}
							public void modify(HttpServletRequest request, String strModBy,PrintWriter out1)
							throws IOException, ServletException 
							{
								String cacheid = request.getParameter("cacheid");
								String cachename = request.getParameter("cachename");
								String cache_type = request.getParameter("cache_type");
								String maxelem = request.getParameter("maxelem");
								String overflowtodisk = request.getParameter("overflowtodisk");
								String memoryStoreEvictionPolicy = request.getParameter("evictionpolicy");
								String eternal = request.getParameter("eternal");
								String timeToLiveSeconds = request.getParameter("timetolive");
								String timeToIdleSeconds = request.getParameter("timetoidle");
								String diskExpiryThreadIntervalSeconds = request.getParameter("timetothread");
								String diskStorePath = request.getParameter("diskstorepath");
								String diskPersistent = request.getParameter("diskpersistent");
								String default_cache = request.getParameter("default_cache");
								
								if(default_cache.equals("Yes"))
								{
									if(coreadministrationv2.dbconnection.DataBaseLayer.isDefaultCacheExists())
										out1.println("<font color=red size=4>Already a default cache exists</font>");
									else
										coreadministrationv2.dbconnection.DataBaseLayer.modifyCacheDefinition(cacheid,cachename,cache_type,maxelem,overflowtodisk,memoryStoreEvictionPolicy,eternal,timeToLiveSeconds,timeToIdleSeconds,diskExpiryThreadIntervalSeconds,diskStorePath,diskPersistent,default_cache);
									
								}
								else
								{
									coreadministrationv2.dbconnection.DataBaseLayer.modifyCacheDefinition(cacheid,cachename,cache_type,maxelem,overflowtodisk,memoryStoreEvictionPolicy,eternal,timeToLiveSeconds,timeToIdleSeconds,diskExpiryThreadIntervalSeconds,diskStorePath,diskPersistent,default_cache);
								}
								
								
									
							}
							public void delete(HttpServletRequest request, PrintWriter out1)
							throws IOException, ServletException 
							{
								String cacheid = request.getParameter("cacheid");
								coreadministrationv2.dbconnection.DataBaseLayer.deleteCacheDefinition(cacheid);
							}
    
     
}
