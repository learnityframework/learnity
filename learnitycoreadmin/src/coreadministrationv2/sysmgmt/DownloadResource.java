package coreadministrationv2.sysmgmt;

/**
 * Title:        Learnity Interface Management   
 * Description:
 * Copyright:    Copyright (c) 2008
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
//import jmesa.*;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
public class DownloadResource extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws IOException, ServletException {
        String strFileType = "";
        InputStream in;
        String interface_id = req.getParameter("interface_id");
        String resource_id = req.getParameter("resource_id");
        String filename = req.getParameter("filename");
        
        Vector vModule=DataBaseLayer.getFileDetails(interface_id,resource_id,filename);
        in = (InputStream)vModule.elementAt(0);


			res.setContentType ("application/download");
			res.setHeader ("Content-Disposition", "attachment; filename=\""+filename+"\"");
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