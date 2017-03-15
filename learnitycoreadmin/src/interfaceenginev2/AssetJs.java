package interfaceenginev2;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AssetJs extends HttpServlet{

	public void doGet(HttpServletRequest req,HttpServletResponse res)
		throws ServletException,IOException
	{
			ServletOutputStream out=res.getOutputStream();
			InputStream in;
			String file_name=req.getParameter("file_name");
			String tid=req.getParameter("tid");
			res.setContentType("application/x-javascript ");
			Vector vjs=NewDataBaseLayer.getAssetFile(file_name,tid);
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