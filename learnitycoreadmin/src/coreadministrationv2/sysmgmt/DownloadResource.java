package coreadministrationv2.sysmgmt;

/**
 * Title:        Learnity Interface Management   
 * Description:
 * Copyright:    Copyright (c) 2008
 * Company:      Aunwesha
 * @author Shibaji Chatterjee
 */




import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.ecs.*;
import org.apache.ecs.html.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import com.oreilly.servlet.MultipartRequest;
import java.text.*;
import java.util.Vector;
import java.util.Random;
import java.io.*;
import java.net.*;
import  org.w3c.dom.Document;
//import jmesa.*;
import org.apache.xerces.parsers.DOMParser;

//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
import java.util.zip.*;
import  org.w3c.dom.Element;
import coreadministrationv2.dbconnection.*;
import coreadministrationv2.utility.*;
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