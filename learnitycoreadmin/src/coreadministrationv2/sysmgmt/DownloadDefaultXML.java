package coreadministrationv2.sysmgmt;

/**
 * Title:        Learnity Interface Management   
 * Description:
 * Copyright:    Copyright (c) 2010
 * Company:      Aunwesha
 * @author Shibaji Chatterjee
 */




import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.ecs.*;
import org.apache.ecs.html.*;
import comv2.aunwesha.param.*;
import comv2.aunwesha.JSPGrid.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import com.oreilly.servlet.MultipartRequest;
import java.text.*;
import java.util.Vector;
import java.util.Random;
import java.io.*;
import java.net.*;
import  org.w3c.dom.Document;
import org.apache.xerces.parsers.DOMParser;
import java.util.zip.*;
import  org.w3c.dom.Element;
import coreadministrationv2.dbconnection.DataBaseLayer;
import coreadministrationv2.utility.*;
import org.apache.xerces.dom.*;
import org.apache.xml.serialize.*;

public class DownloadDefaultXML extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws IOException, ServletException {
        String strFileType = "";
        InputStream in;
       
		  String template_id = req.getParameter("template_id");
		  System.out.println("........................template_id................."+template_id);
        
		  Vector vModule=DataBaseLayer.getApplicationDefaultXMLValue(template_id);
			in = (InputStream)vModule.elementAt(0);
		
		
					res.setContentType ("application/download");
					res.setHeader ("Content-Disposition", "attachment; filename=\"application-template.xml\"");
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