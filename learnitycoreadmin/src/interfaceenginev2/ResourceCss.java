package interfaceenginev2;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ResourceCss extends HttpServlet{

private static InterfaceCachePojo ICP=null;
private static String useInterfaceCaching = "";
private static String defaultCacheName = "";

   public void init(ServletConfig config) throws ServletException 
   {
	  super.init(config);
	  ServletContext sc = config.getServletContext();
	  ICP=(InterfaceCachePojo)sc.getAttribute("ICP");
	  useInterfaceCaching = (String)sc.getAttribute("useInterfaceCaching");
	  defaultCacheName = (String)sc.getAttribute("DefaultCacheName");
   }


   public void doGet(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
   {
	ServletOutputStream out=res.getOutputStream();
	InputStream in;
	String resource_id=req.getParameter("resource_id");
	String interface_id=req.getParameter("interface_id");
	res.setContentType("text/css");
			
	boolean cacheRunning = isCacheRunning(); 
			
//	if ((true == cacheRunning) && (true == ICP.checkCSSCachingRequired(interface_id)))
	//  per interface cache enablement checking is too expensive performance wise					
	if ((true == cacheRunning))
	{
		System.out.println("CSS caching=true");
		
		String cache_key = interface_id+resource_id;	
//		String cache_name = ICP.getCacheName(interface_id);	
		String cache_name = defaultCacheName;   // per interface cache name is too expensive performance wise

		String CSSresource = ICP.getInterfaceFromCacheName(cache_name,cache_key);
		if(CSSresource==null)
			CSSresource="";
		if(CSSresource.equals(""))
		{
			System.out.println("CSS cache is empty");
			Vector imagefile=NewDataBaseLayer.getImageAsString(resource_id,interface_id);
			if (imagefile!=null) 
			{
				for(int j=0;j<imagefile.size();j=j+1)
				{
					String page_string = (String)imagefile.elementAt(0);
					ICP.setInterfaceFromCacheName(cache_name,cache_key,page_string);
					in = org.apache.commons.io.IOUtils.toInputStream(page_string);
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
				return;
			}
			out.println("Cache is empty but CSS file not avalible from database");   //This should not happen
			System.out.println("Cache is empty but CSS file not avalible from database");
			return;
		}
		else //send the contents of the cache
		{
			System.out.println("cache not empty; send CSS from cache");
			in = org.apache.commons.io.IOUtils.toInputStream(CSSresource);
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
			return;
		}
	}
	else  //either cache not running or CSS caching not enabled
	{
		Vector imagefile=NewDataBaseLayer.getimage(resource_id,interface_id);
		if (imagefile!=null) 
		{
			for(int j=0;j<imagefile.size();j=j+1)
			{
				in = (InputStream)imagefile.elementAt(0);
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
			return;
		}
		out.println("Cache is not running or not enabled but CSS file not avalible from database");   //This should not happen
		System.out.println("Cache is not running or not enabled but CSS file not avalible from database");
		return;
	}
		
}
   
   
   public void doPost(HttpServletRequest req,HttpServletResponse res)
		   throws ServletException,IOException
   {
	   doGet(req,res);

   }  
   
	public boolean isCacheRunning()
	{
		boolean flag = false;
		String cacheStatus = "";
		if (ICP != null) cacheStatus = ICP.getStatus();
		if(useInterfaceCaching.equals("true") && cacheStatus.equals("STATUS_ALIVE"))
			flag = true;
		return flag;
	}		

}
