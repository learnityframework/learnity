package coreadministrationv2.sysmgmt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Title:        Learnity Interface Management   
 * Description:
 * Copyright:    Copyright (c) 2010
 * Company:      Aunwesha
 * @author Shibaji Chatterjee
 */


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import coreadministrationv2.dbconnection.DataBaseLayer;

public class DownloadDefaultXML extends HttpServlet {
	
	
   	private static final long serialVersionUID = -2871943780085812505L;
      public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws IOException, ServletException 
          
    	{
    	  
    	  
    	  
		  String template_id = req.getParameter("template_id");
		  String fileName = req.getParameter("filename");
		  System.out.println("....template_id..........." + template_id
					+ "......file..." + fileName);
        
		  
		//	String type = req.getParameter("type1");

			
		    	ResourceBundle rb = ResourceBundle.getBundle("portal",Locale.getDefault());
		
				String name = template_id + ".zip";
				res.setContentType("application/zip");
				res.setHeader("Content-Disposition", "attachment; filename=\""
						+ name + "\"");
				String s7="";
				String key1 = "downloadzip";
				String path=rb.getString(key1);
				String strSize="";
				File ff=new File(path + template_id);
				ZipOutputStream ouF;		
				ff.mkdir();
				ServletOutputStream out = res.getOutputStream();
				ouF = new ZipOutputStream(out);
				templateCreation(template_id, path, ouF, name, req);
				
			}
   
			
			public void templateCreation(String template_id, String path,
					ZipOutputStream ouF, String name, HttpServletRequest req) throws FileNotFoundException {
				InputStream in;
				
				ServletContext servletContext = ((HttpServletRequest) req).getSession().getServletContext();
				String contextPath = servletContext.getRealPath(File.separator);
				
				Vector resourcefile =DataBaseLayer.getTemplateSrc(template_id);
				Vector resourcefileFromDynamicInline =DataBaseLayer.getResourceSrc(template_id);
				Vector resourcefileUriPath= DataBaseLayer.getResourcesFromURIpath(template_id,contextPath );
				Vector combine=new Vector();
				combine.addAll(resourcefile);
				combine.addAll(resourcefileFromDynamicInline);
				combine.addAll(resourcefileUriPath);
				Vector<String> pathvector = new Vector<String>();
				Vector<String> pathvector1 = new Vector<String>();

				try {
					for (int i = 0; i < combine.size(); i++) {
						Vector vModule = ( Vector) combine.elementAt(i);
						in = (InputStream) vModule.elementAt(1);
						if(in!=null){
							String file_name = (String) vModule.elementAt(0);
							File dst = new File(path + template_id + File.separator
									+ file_name);
							OutputStream resourceout = new FileOutputStream(dst);
							pathvector.addElement(path + template_id + File.separator
									+ file_name);
							pathvector1.addElement(template_id + File.separator
									+ file_name);
							byte[] buf = new byte[1024];
							int len;
							while ((len = in.read(buf)) > 0) {
								resourceout.write(buf, 0, len);
							}
							in.close();
							resourceout.close();
						}
					}
				} catch (IllegalArgumentException iae) {
					iae.printStackTrace();
				} catch (FileNotFoundException fnfe) {
					fnfe.printStackTrace();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
				
			
			
				makeZip(ouF, pathvector1, pathvector);

			}


			//Vector getResourceFileFromUriPath(String template_id,HttpServletRequest req) throws FileNotFoundException
			// { 
				//HttpSession session = req.getSession();
		  	//  String filename= (String) session.getAttribute("file_name");
				//  String attachmentname=req.getParameter("asset_path");
				
				   
			      
					
			//  }
		
  

    
	public void doPost(HttpServletRequest request, HttpServletResponse response)
        	throws IOException, ServletException {
        doGet(request, response);
    }
 
	public void makeZip(ZipOutputStream ouF, Vector<String> strUnitId,
			Vector<String> dst_path) {
		byte[] buffer = new byte[1024];

		// System.out.println("................strUnitId............:"+strUnitId);
		// System.out.println("................dst_path............:"+dst_path);
		try {
			for (int i = 0; i < strUnitId.size(); i++) {
				ouF.setLevel(Deflater.DEFAULT_COMPRESSION);
				FileInputStream in = new FileInputStream(
						(String) dst_path.elementAt(i));
				ouF.putNextEntry(new ZipEntry((String) strUnitId.elementAt(i)));
				int len;
				while ((len = in.read(buffer)) > 0) {
					ouF.write(buffer, 0, len);
				}
				ouF.closeEntry();
				in.close();
			}
			ouF.close();
		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception a) {
			a.printStackTrace();
		}
	}
	
	
	
	
 }
