package coreadministrationv2.sysmgmt;

/**
 * Title:        Learnity Interface Management   
 * Description:
 * Copyright:    Copyright (c) 2010
 * Company:      Aunwesha
 * @author Shibaji Chatterjee
 */




import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import coreadministrationv2.dbconnection.DataBaseLayer;

public class DownloadAsset extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws IOException, ServletException {
        String strFileType = "";
        InputStream in;
       
		  String asset_id = req.getParameter("asset_id");
		  System.out.println("........................>>>asset_id.................."+asset_id);
		  String asset_file_name=req.getParameter("asset_file_name");
        
		  Vector vModule=DataBaseLayer.getThemesCSSValue(asset_id);
			in = (InputStream)vModule.elementAt(0);
		
		
					res.setContentType ("application/download");
					res.setHeader ("Content-Disposition", "attachment; filename=\""+asset_file_name+"\"");
					ServletOutputStream out = res.getOutputStream();
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
			}	
 
	  
  

    
	public void doPost(HttpServletRequest request, HttpServletResponse response)
        	throws IOException, ServletException {
        doGet(request, response);
    }
 

}