package interfaceenginev2;

import interfaceenginev2.display.DisplayEngine;

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
import javax.servlet.http.HttpSession;
//import org.grlea.log.*;
// import net.sf.ehcache.Ehcache;




/**
 * Title:		 DisplayEngine
 * Description:	 Portal Opening Page
 * Copyright:    Copyright (c) 2008
 * Company:		 Aunwesha Knowledge Technologies Pvt. Ltd.
 * @author		Shibaji Chatterjee
*/

public class PortalServlet extends HttpServlet  {

private InterfaceCachePojo ICP=null;
private String useInterfaceCaching = "";
private String default_cache = "";
private Vector vApplicationTemplate = new Vector();
private Vector vDefaultApplicationTemplate = new Vector();
// InterfaceCachePojo ICP = new InterfaceCachePojo();

   public void init(ServletConfig config) throws ServletException {
	   
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

  public void doPost(HttpServletRequest request, HttpServletResponse response)
  			 throws ServletException, IOException {
	  
	      ServletOutputStream out=response.getOutputStream();
	     
	      InputStream in=null;
	  		HttpSession mysession=request.getSession();
			String d="DEFAULT";
			String InterfaceID=request.getParameter("IID");
         String getSessionCheck=NewDataBaseLayer.getSessionCheck(InterfaceID);
         if(getSessionCheck==null)
			getSessionCheck="";
         String getRoleCheck=NewDataBaseLayer.getRoleCheck(InterfaceID);
         if(getRoleCheck==null)
			getRoleCheck="";
         String getContentTypeHtml=NewDataBaseLayer.getContentTypeHtml(InterfaceID);
	 String cacheStatus = "";
         if(getContentTypeHtml==null)
			getContentTypeHtml="";
			if(getContentTypeHtml.equals(""))
                     {
           						response.setContentType("text/html");
                     }
                     else
							{
	                        response.setContentType(getContentTypeHtml);
                     }
         
							
							
			String getcachecontrol=NewDataBaseLayer.getcachecontrol(InterfaceID);
			if(getcachecontrol==null)
			{
				getcachecontrol="";
			}
			
			else
			{
				response.setHeader("Cache-Control", getcachecontrol);
			}
			String getexpire=NewDataBaseLayer.getexpires(InterfaceID);
			//Long  exp=Long.parseLong(getexpire) ;
			response.setHeader("Expires",getexpire);
			String getlast=NewDataBaseLayer.getLast(InterfaceID);
			//Long  lastmod=Long.parseLong(getlast) ;
			response.setHeader("Last-Modified",getlast);	
			
// 			if(InterfaceID.equals("LoginPage"))
				
			
			
			 if(getSessionCheck.equals(""))
							{
								mysession=request.getSession(false);
							}      
			  else
						   {
										if(getSessionCheck.equals("true"))
												{
													mysession=request.getSession(true);
												}	
										if(getSessionCheck.equals("false"))
												{
													mysession=request.getSession(false);
												}		
						   }
		 
			DisplayEngine de = new DisplayEngine();
			System.out.println(" GET ROLE CHECK................................."+getRoleCheck);

         if(getRoleCheck.equals("") || getRoleCheck.equals("true"))
			{
				System.out.println("==========getRoleCheck====1="+getRoleCheck);
				String  user_id = (String)mysession.getAttribute("user_id");
            if(user_id==null)
				{
					user_id="";
					response.sendRedirect("./interfaceenginev2.PortalServlet?IID=LoginPage");
				}
			   else
				{
					String cache_name = getCacheName(InterfaceID);
					String cache_key = user_id+InterfaceID;
					String page = null;
					if (ICP != null) page = ICP.getInterfaceFromCacheName(cache_name,cache_key);
					if(page==null)
						page="";
// 					System.out.println("==================page=========="+page);
					
					if(page.equals(""))
					{
						boolean caching_status = checkCachingRequired(InterfaceID);
						System.out.println("=========caching_status=======11111==="+caching_status);
						if(caching_status==true)
						{
							String role_id=NewDataBaseLayer.getRoleID(user_id);
							String interfaceengine_role_id=NewDataBaseLayer.getInterfaceengineRoleID(role_id.toUpperCase());
							System.out.println(".................interfaceengine_role_id........"+interfaceengine_role_id);
							Vector imagefile=NewDataBaseLayer.getHTMLAsString(interfaceengine_role_id,InterfaceID);
							if (imagefile!=null) 
							{
								for(int j=0;j<imagefile.size();j=j+1)
								{
// 									in = (InputStream)imagefile.elementAt(0);
// 									String page_string = org.apache.commons.io.IOUtils.toString(in); 
									String page_string = (String)imagefile.elementAt(0);
									ICP.setInterfaceFromCacheName(cache_name,cache_key,page_string);
									in = org.apache.commons.io.IOUtils.toInputStream(page_string);
									int len = 0;
									byte buffer[]= new byte[1024];
									try {
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
							String role_id=NewDataBaseLayer.getRoleID(user_id);
					  
							String interfaceengine_role_id=NewDataBaseLayer.getInterfaceengineRoleID(role_id.toUpperCase());
							System.out.println(".................interfaceengine_role_id........"+interfaceengine_role_id);
							Vector imagefile=NewDataBaseLayer.getHTML(interfaceengine_role_id,InterfaceID);
							if (imagefile!=null) 
							{
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
					else
					{
						System.out.println("============caching===1====");
						in = org.apache.commons.io.IOUtils.toInputStream(page);
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
							
			
			else if(getRoleCheck.equals("nouser"))
			{
				String cache_name = getCacheName(InterfaceID);
				String cache_key = InterfaceID;
				String page = null;
				if (ICP != null) page = ICP.getInterfaceFromCacheName(cache_name,cache_key);
				if(page==null)
					page="";
// 				System.out.println("==================page=========="+page);
				if(page.equals(""))
				{
					boolean caching_status = checkCachingRequired(InterfaceID);
					System.out.println("=========caching_status=======22222==="+caching_status);
					if(caching_status==true)
					{
						String role=(String)mysession.getAttribute("role_id");
						String role_id=NewDataBaseLayer.getDefaultRoleID(role);
						System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>role_id>>>>>>>>>>"+role);
						Vector imagefile=NewDataBaseLayer.getHTMLAsString(role_id,InterfaceID);
						if (imagefile!=null) {
							for(int j=0;j<imagefile.size();j=j+1){
// 										in = (InputStream)imagefile.elementAt(0);
						
								String page_string = (String)imagefile.elementAt(0);
								ICP.setInterfaceFromCacheName(cache_name,cache_key,page_string);
								in = org.apache.commons.io.IOUtils.toInputStream(page_string);
															
								
								
								int len = 0;
								byte buffer[]= new byte[1024];
								try {
									while ((in != null) && ((len = in.read(buffer)) != -1)) {
										out.write(buffer,0,len);
									}
// 											ICP.setInterface(InterfaceID,buffer);
								}
								finally {
									if (in != null) in.close();
								}
								
							
									
							}
							
						}
					}
					else
					{
						String role=(String)mysession.getAttribute("role_id");
						String role_id=NewDataBaseLayer.getDefaultRoleID(role);
						System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>role_id>>>>>>>>>>"+role);
						Vector imagefile=NewDataBaseLayer.getHTML(role_id,InterfaceID);
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
				else
				{
					System.out.println("============caching===2====");
					in = org.apache.commons.io.IOUtils.toInputStream(page);
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
				
				
				
				
				
			else if(getRoleCheck.equals("false"))
			{
				String cache_name = getCacheName(InterfaceID);
				System.out.println("========cache_name====="+cache_name);
				String cache_key = InterfaceID;
				String page = null;
				if (ICP != null) page = ICP.getInterfaceFromCacheName(cache_name,cache_key);
				if(page==null)
					page="";
// 				System.out.println("==================page=========="+page);
				if(page.equals(""))
				{
					boolean caching_status = checkCachingRequired(InterfaceID);
					System.out.println("=========caching_status=======3333==="+caching_status);
					if(caching_status==true)
					{
						String role=NewDataBaseLayer.getDefaultRoleID(d);
						Vector imagefile=NewDataBaseLayer.getHTMLAsString(role,InterfaceID);
						if (imagefile!=null) {
							for(int j=0;j<imagefile.size();j=j+1){
								String page_string = (String)imagefile.elementAt(0);
								ICP.setInterfaceFromCacheName(cache_name,cache_key,page_string);
								in = org.apache.commons.io.IOUtils.toInputStream(page_string);
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
					else
					{
						System.out.println("==========checkCachingRequired====false========");
						String role=NewDataBaseLayer.getDefaultRoleID(d);
						Vector imagefile=NewDataBaseLayer.getHTML(role,InterfaceID);
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
				else
				{
					System.out.println("============caching===3====");
					in = org.apache.commons.io.IOUtils.toInputStream(page);
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
public void doGet(HttpServletRequest request,HttpServletResponse response)
				throws ServletException,IOException
{
			
          doPost(request, response);
}

public boolean checkCachingRequired(String InterfaceID)
{
	boolean flag = false;
	String cache_name = "";
	
	// Added by Diptendu 28-Oct-2015
	if (ICP==null) return false;
	// end addition

	String cacheStatus = ICP.getStatus();
	if(useInterfaceCaching.equals("true") && cacheStatus.equals("STATUS_ALIVE"))
	{
		
		String InterfaceCachingStatus = NewDataBaseLayer.getInterfaceCachingStatus(InterfaceID);
		System.out.println("================InterfaceCachingStatus======"+InterfaceCachingStatus);
		cache_name = NewDataBaseLayer.getInterfaceCacheName(InterfaceID);
		System.out.println("==========cache_name======="+cache_name);
		
		if(InterfaceCachingStatus.equalsIgnoreCase("true") && !cache_name.equals(""))
		{
			flag = true;
		}//end of InterfaceCachingStatus true if
		else
		{
			if(InterfaceCachingStatus.equalsIgnoreCase("false"))
				flag = false;
			else
			{
				String application_template_id = NewDataBaseLayer.getApplicationTemplateId(InterfaceID);
				String template_caching_status = "";
				System.out.println("===============application_template_id======"+application_template_id);
				if(vApplicationTemplate!=null)
				{
					for(int i=0;i<vApplicationTemplate.size();i++)
					{
						Vector vApplicationTemplateSub = (Vector)vApplicationTemplate.elementAt(i);
						String v_template_id = (String)vApplicationTemplateSub.elementAt(0);
						if(v_template_id.equals(application_template_id))
						{
							template_caching_status = (String)vApplicationTemplateSub.elementAt(2);
						}
					}
				}
				if(template_caching_status.equals("true"))
					flag = true;
				else
				{
					String d_template_caching_status = "";
					System.out.println("===============application_template_id======"+application_template_id);
					if(vDefaultApplicationTemplate!=null)
					{
						for(int i=0;i<vDefaultApplicationTemplate.size();i++)
						{
							Vector vDefaultApplicationTemplateSub = (Vector)vDefaultApplicationTemplate.elementAt(i);
							d_template_caching_status = (String)vDefaultApplicationTemplateSub.elementAt(2);
							
						}
					}
					if(d_template_caching_status.equals("true"))
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
//	String cacheStatus = ICP.getStatus();
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
