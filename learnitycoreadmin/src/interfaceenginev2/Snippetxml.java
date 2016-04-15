package interfaceenginev2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.grlea.log.SimpleLogger;

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