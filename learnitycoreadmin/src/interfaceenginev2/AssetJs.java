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

import comv2.aunwesha.param.*;
import org.apache.commons.io.IOUtils;

public class AssetJs extends HttpServlet{

	public void doGet(HttpServletRequest req,HttpServletResponse res)
		throws ServletException,IOException
	{
			ServletOutputStream out=res.getOutputStream();
			InputStream in;
			String file_name=req.getParameter("file_name");
			res.setContentType("application/x-javascript ");
			Vector vjs=NewDataBaseLayer.getAssetFile(file_name);
			if (vjs!=null) 
				{
					for(int j=0;j<vjs.size();j=j+1)
					{
						in = (InputStream)vjs.elementAt(0);
						int len = 0;
						byte buffer[]= new byte[1024];
						try 
						{
							while ((in != null) && ((len = in.read(buffer)) != -1)) 
							{
								out.write(buffer,0,len);
							}
						}
						finally 
						{
							if (in != null) in.close();
						}
					}
				}
			
	}
		
		
public void doPost(HttpServletRequest req,HttpServletResponse res)
		throws ServletException,IOException
{
	doGet(req,res);

}


	}