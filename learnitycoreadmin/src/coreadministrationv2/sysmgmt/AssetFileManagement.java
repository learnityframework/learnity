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
import org.apache.ecs.html.Option;
import org.apache.ecs.html.Script;
import org.apache.ecs.html.Select;
import org.apache.ecs.html.TBody;
import org.apache.ecs.html.TD;
import org.apache.ecs.html.TR;
import org.apache.ecs.html.Table;
import org.apache.ecs.html.Title;

import com.oreilly.servlet.MultipartRequest;
import comv2.aunwesha.JSPGrid.JSPGridPro2;

import coreadministrationv2.utility.TableExtension;
//import oracle.xml.parser.v2.*;
//import jmesa.*;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;

public class AssetFileManagement extends HttpServlet {

	private static final String LOGIN_SESSION_NAME = "ADMIN_LOG_ON";
	private static final String OBJ = "OBJ";
	//private static final SimpleLogger log = new SimpleLogger(AssetFileManagement.class);

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
   
		  
		  
        Body body = new Body();
        Form form = new Form();
	     Input inputButton1 = new Input();
	     Input inputButton2 = new Input();
	     Input inputButton3 = new Input();
		 // Input inputButton4 = new Input();
		  inputButton1.setOnClick("addLayout_onclick();");
		  inputButton2.setOnClick("download_onclick();");
		  inputButton3.setOnClick("deleteLayout_onclick();");
		 // inputButton4.setOnClick("setDefault_onclick();");
	     Html html = new Html()
               .addElement(new Head()
					.addElement(new Title("CSS File Management"))
					.addElement(new Link()
						.setHref("../coreadmin/js/stylesheet.css")
						.setRel("stylesheet"))
					.addElement(new Script()
						.setLanguage("JavaScript")
						.setSrc("../coreadmin/js/check.js"))
					.addElement(new Script()
					.setLanguage("JavaScript")
				//	.addElement(javaScript)))
               .setSrc("../coreadmin/js/coreadministrationv2.sysmgmt/AssetFileManagement.js")))

               .addElement(body
					.addElement(form
	              	.setName("frm")
				  	.addElement(new TableExtension()
				  .headerTable("<b>System Administrator: </b>"+strAdminId, strDate, strTime, "<b>System Administration: </b>Framework Asset Management"))));
	    
		  
		  
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
		  String sql = "select a.asset_id as \"Select\", a.file_name as \"File Name\", "+
				         " a.upload_on as \" Upload On\",a.asset_type as \" Type\" "+
				         " from framework_asset a ";
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
			grid1.setCaption("Currently Defined Asset File");
			grid1.setMaxRowsPerPage(5);   		//how many records displayed per page
			grid1.setMaxResultPagesPerLoad(5);  //Page : 1 2 3 4 5 6 7 8 9 10 (max 10 pages displayed)
			grid1.setLineNoHeaderBgColor("#48E6F7");
			grid1.Cols(0).setFieldType(grid1.FIELD_RADIO);
			grid1.Cols(1).setFieldType(grid1.FIELD_HIDDEN);		
			grid1.Cols(2).setFieldType(grid1.FIELD_HIDDEN);	
			grid1.Cols(3).setFieldType(grid1.FIELD_HIDDEN);	
			
			
			
			grid1.Cols(0).setFieldName("checkbox");
			grid1.Cols(1).setFieldName("cssfilename");
			grid1.Cols(2).setFieldName("hiddendate");
			grid1.Cols(3).setFieldName("hiddentype");
			
			
			
			grid1.Cols(0).Header().setClassID("swb");
			grid1.Cols(1).Header().setClassID("swb");
			grid1.Cols(2).Header().setClassID("swb");
			grid1.Cols(3).Header().setClassID("swb");
			
			grid1.Cols(0).insertFieldScript("onclick=\"CCA();checkbox_onclick();\"");
			grid1.setEachRowHeight("20");
			grid1.canSort(0, false);
			grid1.canSort(1, true);
			grid1.canSort(2, true);
			grid1.canSort(3, true);
			
			
			
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
				form.addElement("<p>Total No. Of Framework Asset: " +grid1.getRows());
				form.addElement(grid1.getGrid());
			}	
			//Added by Diptendu 29-Oct-2015
			
			grid1.closeConnection();
			
			
		}
		catch (Exception exp) {
			
		}	
		Input styleid= new Input().setType("file").setName("filename").setClassId("PPRLabelText");
		Option[] option12 = {new Option("0").addElement("[Choose One]"),
			new Option("css").addElement("css"),
			new Option("js").addElement("js")
		
		};
		
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
				
// 				.addElement(new TR()
// 				.addElement(new TD()
// 				.setClassId("PPRLabelText")
// 				.setWidth("160")
// 				.addElement("Asset Type"))
// 				.addElement(new TD()
// 				.setWidth("336")
// 				.addElement(new Input()
// 				.setClassId("PPRLabelText")
// 				.setName("asset_type")
// 				.setValue("")
// 				.setType("text")
// 							  )))
				
				.addElement(new TR()
				.addElement(new TD()
				.setClassId("PPRLabelText")
				.setWidth("160")
				.addElement("Select Type"))
				.addElement(new TD()
				.setWidth("336")
				.addElement(new Select()
				.setClassId("drpdwn")
				.setName("asset_type")
				.setTabindex("2")
				.addElement(option12))))
						
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

							  ));
		table.addElement(new TR()
				.addElement(new TD()
				.addElement(table2)))
				.addElement(new TR()
				.addElement(new TD()
				.addElement(table1)));
				form.addElement(table);
				form.addElement("<input type=\"hidden\" name=\"asset_id\">");	
				form.addElement("<input type=\"hidden\" name=\"asset_file_name\">");	
				
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
 		      String asset_id="";
				ResourceBundle rb = ResourceBundle.getBundle("portal",Locale.getDefault());      
				String filename="";
				String asset_type="";
				String s7="";
				String key1= "themescss"; 
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
						asset_type=multipartrequest.getParameter("asset_type");
						asset_id=multipartrequest.getParameter("asset_id");
				}	
				catch(IOException ioexception)
				{
					ioexception.printStackTrace();
				}
				coreadministrationv2.dbconnection.DataBaseLayer.AssetFileDelete(asset_id);
				coreadministrationv2.dbconnection.DataBaseLayer.AssetFileInsert(filename,attachmentname,s7,strSize,asset_type);

				
     }
     public void modify(HttpServletRequest request, String strModBy,PrintWriter out1)
        throws IOException, ServletException {
		 
    }
    public void delete(HttpServletRequest request, PrintWriter out1)
        	throws IOException, ServletException {
		 String asset_id=request.getParameter("asset_id");
		 coreadministrationv2.dbconnection.DataBaseLayer.AssetFileDelete(asset_id);
    }
	 
	
     
}