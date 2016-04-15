package coreadministrationv2.sysmgmt;

/**
 * Title:        Learnity Interface Management   
 * Description:
 * Copyright:    Copyright (c) 2008
 * Company:      Aunwesha
 * @author Shibaji Chatterjee
 */




import interfaceenginev2.InterfaceCachePojo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Statistics;
import net.sf.ehcache.Status;

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

import coreadministrationv2.dbconnection.DataBaseLayer;
import coreadministrationv2.utility.TRExtension;
//import oracle.xml.parser.v2.*;
//import jmesa.*;
public class CacheManagement extends HttpServlet {

	private static final String LOGIN_SESSION_NAME = "ADMIN_LOG_ON";
	    //public static final SimpleLogger log = new SimpleLogger(CacheManagement.class, true);
	private InterfaceCachePojo ICP=null;
	public void init(ServletConfig config) throws ServletException {
	   
		super.init(config);
		ServletContext sc = config.getServletContext();
		ICP=(InterfaceCachePojo)sc.getAttribute("ICP");
		if(ICP==null)
			System.out.println("null===============");
		else
			System.out.println("not null===============");
	}
	
	
	
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
						clearCache(request, response, strAdminId, out);
						break;
					case 1:
						shutdownCache(request, response, strAdminId, out);
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
				String cacheName = request.getParameter("cacheName");
				if(cacheName==null)
					cacheName="";
				
				int totalelements = 0;
				long hits = 0;
				long memoryhits = 0;
				long diskhits = 0;
				long misses = 0;
				long putcount = 0;
				float averageGetTime = 0;
				String strCacheStatus="";
				String strtotalelements = "";
				String strhits = "";
				String strmemoryhits = "";
				String strdiskhits = "";
				String strmisses = "";
				String strputcount = "";
// 				String[] cacheNames = ICP.getCacheNames();
				String cacheManagerStatus = "";
				
				cacheManagerStatus = ICP.getStatus();
				System.out.println("============cacheManagerStatus======"+cacheManagerStatus);
				if(cacheManagerStatus.equals("STATUS_ALIVE"))
				{
					Cache testcache = ICP.getCache(cacheName);
				
				
				System.out.println("=======strCacheStatus======"+strCacheStatus.toString());
				
				
				
				
				
				if(testcache!=null)
				{
// 					LiveCacheStatistics stat = testcache.getLiveCacheStatistics();
// 					
// 					
// 					totalelements = testcache.getSize();
// // 					System.out.println("===========testcache.toString() ========="+testcache.toString());
// 					System.out.println("==============stat===="+stat.toString());
// 					averageGetTime = stat.getAverageGetTimeMillis();
// 					 
// 					hits = stat.getCacheHitCount();
// 					memoryhits = stat.getInMemoryHitCount();	
// 					diskhits = stat.getOnDiskHitCount();
// 					misses = stat.getCacheMissCount();
// 					putcount = stat.getPutCount();
					
// 					if(!cacheName.equals(""))
// 					{
					System.out.println("=================cacheName======"+testcache.toString());
					Status cacheStatus = testcache.getStatus();
					strCacheStatus = cacheStatus.toString();
// 					}
					if(strCacheStatus.equals("STATUS_ALIVE"))
					{
// 						LiveCacheStatistics stat = testcache.getLiveCacheStatistics();
						Statistics stat = testcache.getStatistics();
					
// 						hits = stat.getCacheHitCount();
// 						memoryhits = stat.getInMemoryHitCount();	
// 						diskhits = stat.getOnDiskHitCount();
// 						misses = stat.getCacheMissCount();
// 						putcount = stat.getPutCount();
						
						
						
						totalelements = testcache.getSize();
// 						System.out.println("===========testcache.toString() ========="+testcache.toString());
						System.out.println("==============stat===="+stat.toString());
						averageGetTime = stat.getAverageGetTime();
					 
						hits = stat.getCacheHits();
						memoryhits = stat.getInMemoryHits();	
						diskhits = stat.getOnDiskHits();
						misses = stat.getCacheMisses();
						
						
					
					
						System.out.println("==============averageGetTime===="+averageGetTime);
						System.out.println("==============hits===="+hits);
						System.out.println("==============memoryhits===="+memoryhits);
						System.out.println("==============diskhits===="+diskhits);
						System.out.println("==============misses===="+misses);
// 						System.out.println("==============putcount===="+putcount);
					}
					
				}
				}
				
				strtotalelements = Integer.toString(totalelements);
				strhits = Long.toString(hits);
				strmemoryhits = Long.toString(memoryhits);
				strdiskhits = Long.toString(diskhits);
				strmisses = Long.toString(misses);
// 				strputcount = Long.toString(putcount);
				
				
				
				
				String javaScript = " var index = 0;"+
        					"\n	function clear_onclick() {"+
						"\n		document.frm.method=\"post\";"+
						"\n		document.frm.action = \"./coreadministrationv2.sysmgmt.CacheManagement?prmAddModify=0\";"+
						"\n		document.frm.submit();"+
						"\n	}"+
						"\n	function shutdown_onclick() {"+
						
						"\n			document.frm.method=\"post\";"+
						"\n			document.frm.action = \"coreadministrationv2.sysmgmt.CacheManagement?prmAddModify=1\";"+        					    
						"\n			document.frm.submit();"+
						
						"\n	}"+
						"\n"+
							
							
						
                            
						"\n	function validate(){"+
						"\n		if(!fnCheckZero(document.frm.cacheName.value,\"Cache Name\")){"+
						"\n			document.frm.cacheName.focus();"+
						"\n			return false;"+
						"\n		}"+
						"\n		return true;"+
						"\n	}"+

						"\n	function cache_onchange() {"+
						"\n		document.frm.method=\"post\";"+
						"\n		document.frm.action=\"coreadministrationv2.sysmgmt.CacheManagement;\""+
						"\n		document.frm.submit();"+
						"\n	}"+

						"\n	function refresh_onclick() {"+
						"\n		document.frm.method=\"post\";"+
						"\n		document.frm.action=\"coreadministrationv2.sysmgmt.CacheManagement;\""+
						"\n		document.frm.submit();"+
						"\n	}";
						

       
				Input inputButton1 = new Input();
				Input inputButton2 = new Input();
				Input inputButton3 = new Input();

				
        
     //   inputButton1.setOnClick("addLayout_onclick();");
				inputButton1.setOnClick("clear_onclick();");
       // inputButton3.setOnClick("deleteLayout_onclick();");
				inputButton2.setOnClick("shutdown_onclick();");
				inputButton3.setOnClick("refresh_onclick();");


				Option[] option1 = null;
				Vector getCacheVector = DataBaseLayer.getCacheVector();
				if(getCacheVector==null) 
				{
					option1 = new Option[1];
					option1[0] = new Option("0").addElement("[All]");
				}
				else 
				{
					option1= new Option[getCacheVector.size()+1];
					option1[0] = new Option("0").addElement("[All]");
					for(int i=0; i<getCacheVector.size(); i++) 
					{
						Vector getCacheVectorsub = (Vector) getCacheVector.elementAt(i);
						String  cache_name_value = (String) getCacheVectorsub.elementAt(0);
						String cache_name = (String) getCacheVectorsub.elementAt(1);
						option1[i+1] = new Option(cache_name_value).addElement(cache_name);
						if(cache_name_value.equals(cacheName))
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
						.headerTable("<b>Administrator:</b> "+strAdminId, strDate, strTime, "<b>Portal Administration:</b> Cache Management"));
					           		

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
													  .addSelect("Select Cache", "cacheName", "1", option1,"cache_onchange()")))))));
											
					Table table=new Table();
					Table table2=new Table();
					
					table2.addElement(new TR()
						 .addElement(new TD()
							.addElement("No of Entries  :  "))
						 .addElement(new TD()
							.addElement(strtotalelements)));
					table2.addElement(new TR()
						 .addElement(new TD()
							.addElement("No. of Hits  :  "))
						 .addElement(new TD()
							.addElement(strhits)));
								 
					table2.addElement(new TR()
						 .addElement(new TD()
							.addElement("Hits in Memory  :  "))
						 .addElement(new TD()
							.addElement(strmemoryhits)));
							
					table2.addElement(new TR()
						 .addElement(new TD()
							.addElement("Hits on Disk  :  "))
						 .addElement(new TD()
							.addElement(strdiskhits)));
							
					table2.addElement(new TR()
						 .addElement(new TD()
							.addElement("No. of Miss  :  "))
						 .addElement(new TD()
							.addElement(strmisses)));
							
					      
				

				
				
					Table table1=new Table(); 
					table1.addElement(new Table()
							.setBorder(0)
							.setCellPadding(0)
							.setCellSpacing(0)
							.addElement(new TR()
							.addElement(new TD()
							.addElement(inputButton1
							.setClassId("sbttn")
							.setName("clear")
							.setTabindex(2)
							.setTitleValue("Clear Cache")
							.setType("button")
							.setValue("Clear Cache")))
							.addElement(new TD()
							.setWidth(5))
					
							.addElement(new TD()
							.addElement(inputButton2
							.setClassId("sbttn")
							.setName("shutdown")
							.setTabindex(2)
							.setTitleValue("Shutdown Cache")
							.setType("button")
							.setValue("Shutdown Cache")))

							.addElement(new TD()
							.setWidth(5))
					
							.addElement(new TD()
							.addElement(inputButton3
							.setClassId("sbttn")
							.setName("refresh")
							.setTabindex(2)
							.setTitleValue("Refresh")
							.setType("button")
							.setValue("Refresh")))
										  ));
						
					
							
					table
							.addElement(new TR()
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
		throws IOException, ServletException 
		{
		doGet(request, response);
		}

		public void clearCache(HttpServletRequest request,HttpServletResponse response, String strCreatedBy, PrintWriter out1)
		throws IOException, ServletException 
		{
			String cacheName = request.getParameter("cacheName");
			System.out.println("cacheName===in clearCache==========="+cacheName);
			String cacheManagerStatus = ICP.getStatus();
			
			
			
			
			if(cacheManagerStatus.equals("STATUS_ALIVE"))
			{
				System.out.println("======ALIVE in clearCache===========");
// 				ICP.clearCache(cacheName);
				Cache testcache = ICP.getCache(cacheName);
				testcache.removeAll();
				testcache.clearStatistics();  
			}
		}
						

		public void shutdownCache(HttpServletRequest request,HttpServletResponse response, String strCreatedBy, PrintWriter out1)
		throws IOException, ServletException 
		{
			String cacheName = request.getParameter("cacheName");
			System.out.println("cacheName=============="+cacheName);
			Cache cache = ICP.getCache(cacheName);
			String cacheManagerName = ICP.getName();
			System.out.println("========cacheManagerName========"+cacheManagerName);
			ICP.shutdownCache();
// 			cache.dispose();
      		}
   
}