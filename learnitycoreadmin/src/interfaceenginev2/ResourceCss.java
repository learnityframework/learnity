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


public class ResourceCss extends HttpServlet{

private InterfaceCachePojo ICP=null;
private String useInterfaceCaching = "";
private String default_cache = "";
private Vector vApplicationTemplate = new Vector();
private Vector vDefaultApplicationTemplate = new Vector();
// InterfaceCachePojo ICP = new InterfaceCachePojo();

   public void init(ServletConfig config) throws ServletException 
   {
	  super.init(config);
	  ServletContext sc = config.getServletContext();
	  ICP=(InterfaceCachePojo)sc.getAttribute("ICP");
	  if(ICP==null)
		   System.out.println("null===============");
	  else
		   System.out.println("not null===============");
      
	  useInterfaceCaching = (String)sc.getAttribute("useInterfaceCaching");
	  System.out.println("=============useInterfaceCaching======"+useInterfaceCaching);
	  default_cache = (String)sc.getAttribute("DefaultCacheName");
	  System.out.println("default_cache======="+default_cache);
	  if(default_cache==null)
		   default_cache = "";
    
	  vApplicationTemplate = (Vector)sc.getAttribute("ApplicationTemplateConf");
	  vDefaultApplicationTemplate = (Vector)sc.getAttribute("DefaultApplicationTemplateConf");
    
   }


   public void doGet(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
   {
	ServletOutputStream out=res.getOutputStream();
	InputStream in;
	String resource_id=req.getParameter("resource_id");
	String interface_id=req.getParameter("interface_id");
	res.setContentType("text/css");
	//byte[] buffer=NewDataBaseLayer.getimage(resource_id);
			
	//out.write(buffer);
			
			
	String cache_key = interface_id+resource_id;	
	String cache_name = getCacheName(interface_id);	
			
		
	String page = ICP.getInterfaceFromCacheName(cache_name,cache_key);
	if(page==null)
		page="";
	if(page.equals(""))
	{
		boolean caching_status = checkCachingRequired(interface_id);
		System.out.println("=========caching_status=======11111==="+caching_status);
		if(caching_status==true)
		{
			Vector imagefile=NewDataBaseLayer.getImageAsString(resource_id,interface_id);
			if (imagefile!=null) 
			{
				for(int j=0;j<imagefile.size();j=j+1)
				{
					String page_string = (String)imagefile.elementAt(0);
					ICP.setInterfaceFromCacheName(cache_name,cache_key,page_string);
					in = org.apache.commons.io.IOUtils.toInputStream(page_string);
// 				        	in = (InputStream)imagefile.elementAt(0);
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
		else
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
			}
		}
	}
	else
	{
		in = org.apache.commons.io.IOUtils.toInputStream(page);
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
   
   
   public void doPost(HttpServletRequest req,HttpServletResponse res)
		   throws ServletException,IOException
   {
	   doGet(req,res);

   }


   public boolean checkCachingRequired(String InterfaceID)
   {
	   boolean flag = false;
	   String cache_name = "";
	   String cacheStatus = ICP.getStatus();
	   if(useInterfaceCaching.equals("true") && cacheStatus.equals("STATUS_ALIVE"))
	   {
		
		   String ResourceCSSCachingStatus = NewDataBaseLayer.getResourceCSSCachingStatus(InterfaceID);
		   System.out.println("================ResourceCSSCachingStatus======"+ResourceCSSCachingStatus);
		   cache_name = NewDataBaseLayer.getInterfaceCacheName(InterfaceID);
		   System.out.println("==========cache_name======="+cache_name);
		
		   if(ResourceCSSCachingStatus.equalsIgnoreCase("true") && !cache_name.equals(""))
		   {
			   flag = true;
		   }//end of InterfaceCachingStatus true if
		   else
		   {
			   if(ResourceCSSCachingStatus.equalsIgnoreCase("false"))
				   flag = false;
			   else
			   {
				   String application_template_id = NewDataBaseLayer.getApplicationTemplateId(InterfaceID);
				   String template_css_caching_status = "";
				   System.out.println("===============application_template_id======"+application_template_id);
				   if(vApplicationTemplate!=null)
				   {
					   for(int i=0;i<vApplicationTemplate.size();i++)
					   {
						   Vector vApplicationTemplateSub = (Vector)vApplicationTemplate.elementAt(i);
						   String v_template_id = (String)vApplicationTemplateSub.elementAt(0);
						   if(v_template_id.equals(application_template_id))
						   {
							   template_css_caching_status = (String)vApplicationTemplateSub.elementAt(5);
						   }
					   }
				   }
				   if(template_css_caching_status.equals("true"))
					   flag = true;
				   else
				   {
					   String d_template_css_caching_status = "";
					   System.out.println("===============application_template_id======"+application_template_id);
					   if(vDefaultApplicationTemplate!=null)
					   {
						   for(int i=0;i<vDefaultApplicationTemplate.size();i++)
						   {
							   Vector vDefaultApplicationTemplateSub = (Vector)vDefaultApplicationTemplate.elementAt(i);
							   d_template_css_caching_status = (String)vDefaultApplicationTemplateSub.elementAt(5);
							
						   }
					   }
					   if(d_template_css_caching_status.equals("true"))
						   flag = true;
				   }
			   }
			
		   }//end of InterfaceCachingStatus true else
		
		
		
	   }//end of useInterfaceCaching true if
	   else
	   {
		   flag = false;
	   }//end of useInterfaceCaching true else 
	   return flag;
	
	
   }
		
		
		
   public String getCacheName(String InterfaceID)
   {
	   boolean flag = checkCachingRequired(InterfaceID);
	   String cache_name = "";
	   String CacheName = "";
	   String cacheStatus = ICP.getStatus();
	   if(flag==true)
	   {
		   cache_name = NewDataBaseLayer.getInterfaceCacheName(InterfaceID);
		   System.out.println("======111====cache_name======="+cache_name);
		   if(cache_name.equals(""))
		   {
			   String application_template_id = NewDataBaseLayer.getApplicationTemplateId(InterfaceID);
			   if(vApplicationTemplate!=null)
			   {
				   for(int i=0;i<vApplicationTemplate.size();i++)
				   {
					   Vector vApplicationTemplateSub = (Vector)vApplicationTemplate.elementAt(i);
					   String v_template_id = (String)vApplicationTemplateSub.elementAt(0);
					   if(v_template_id.equals(application_template_id))
					   {
						   cache_name = (String)vApplicationTemplateSub.elementAt(3);
					   }
				   }
			   }
			   if(cache_name.equals(""))
			   {
				   if(vDefaultApplicationTemplate!=null)
				   {
					   for(int i=0;i<vDefaultApplicationTemplate.size();i++)
					   {
						   Vector vDefaultApplicationTemplateSub = (Vector)vDefaultApplicationTemplate.elementAt(i);
						   cache_name = (String)vDefaultApplicationTemplateSub.elementAt(3);
						
					   }
				   }
				   System.out.println("======222====cache_name======="+cache_name);
				   if(cache_name.equals(""))
				   {
					   cache_name = default_cache;
				   }
			   }

			   CacheName = cache_name;
		   }//end of cache_name equal blank if
		   else
		   {
			   CacheName = cache_name;
		   }
		
		   System.out.println("======333====cache_name======="+cache_name);
		
		
	   }//end of flag true if
	
	   return CacheName;
	
	
   }
   
   
   
   
   
}