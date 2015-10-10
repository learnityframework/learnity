package interfaceenginev2;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Properties;
import java.text.*;
import java.net.*;

public class ResourceVideo extends HttpServlet{
	public void doGet(HttpServletRequest req,HttpServletResponse res)
		throws ServletException,IOException{
		ServletOutputStream out=res.getOutputStream();
			InputStream in;
			String resource_id=req.getParameter("resource_id");
			String interface_id=req.getParameter("interface_id");
			res.setContentType("video/x-ms-wmv");
			//byte[] buffer=NewDataBaseLayer.getimage(resource_id);
			
			//out.write(buffer);
			
			
		Vector imagefile=NewDataBaseLayer.getimage(resource_id,interface_id);
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