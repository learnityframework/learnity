package interfaceenginev2;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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