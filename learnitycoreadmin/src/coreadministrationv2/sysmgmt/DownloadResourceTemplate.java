package coreadministrationv2.sysmgmt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import coreadministrationv2.dbconnection.DataBaseLayer;


@WebServlet("/DownloadResourceTemplate")
public class DownloadResourceTemplate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String _TEMPLATE_TYPE = "template";
	private static final String _TEMPLATE_RESOURCE_TYPE = "templateresource";
       
	 public void doGet(HttpServletRequest req, HttpServletResponse res)
		        throws IOException, ServletException {
		        String strFileType = "";
		        InputStream in = null;
		        String template_id = req.getParameter("template_id");
		        String filename = req.getParameter("filename");
		        String type = req.getParameter("type1");
		        String deliveryMode=req.getParameter("delivery_mode");
		        String attachmentname=req.getParameter("asset_path");
		        
		        
		        if (_TEMPLATE_TYPE.equalsIgnoreCase(type))
		            {
		            Vector vModule=DataBaseLayer.getTemplateDetails(template_id);
		            in = (InputStream)vModule.elementAt(0);
		            }
		        
		        else  if (_TEMPLATE_RESOURCE_TYPE.equalsIgnoreCase(type)) {
					
					
					 if(deliveryMode.equals("Dynamic")||deliveryMode.equals("Inline"))
					 {
						 Vector vModule=DataBaseLayer.getResourceDetails(template_id,filename,deliveryMode);
				            in = (InputStream)vModule.elementAt(0);
					 }
					 else if(deliveryMode.equals("URIPath"))
						 
					 { 
						 ServletContext servletContext = req.getSession().getServletContext();
						String contextPath = servletContext.getRealPath(File.separator);
						System.out.println(contextPath);
						 String attachmentname1= attachmentname.replaceAll("[\\.]","");
						String resourcePath = File.separator+contextPath+attachmentname1+filename;
						File file= new File(resourcePath);
					    in = new FileInputStream(file);
					  
					 }
					
		        }
		        
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
