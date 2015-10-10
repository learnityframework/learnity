package interfaceenginev2;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.reflect.Field;
import java.util.*;
import org.grlea.log.*;

public class Snippetxml extends HttpServlet { 
	private final SimpleLogger log = new SimpleLogger(Snippetxml.class,false);
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException { 

		response.setContentType("text/xml"); 
	
               String snippet_id=request.getParameter("snippet_id");
        	ServletOutputStream out=response.getOutputStream();
			InputStream in;
             //  String xmlvalue=NewDataBaseLayer.getSnippetXml(snippet_id);
              
	      Vector imagefile=NewDataBaseLayer.getSnippetXml(snippet_id);
                      if (imagefile!=null) {
				for(int j=0;j<imagefile.size();j=j+1){
				        in = (InputStream)imagefile.elementAt(0);
			
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
			}
			
		}
	}