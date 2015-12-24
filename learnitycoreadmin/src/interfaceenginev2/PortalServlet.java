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
import javax.servlet.http.HttpSession;
//import org.grlea.log.*;
// import net.sf.ehcache.Ehcache;




/**
 * Title:		 Portal Servlet
 * Description:	 Portal Opening Page
 * Copyright:    Copyright (c) 2008
 * Company:		 Aunwesha Knowledge Technologies Pvt. Ltd.
 * @author		Shibaji Chatterjee
*/

public class PortalServlet extends HttpServlet  {

   private static InterfaceCachePojo ICP=null;
   private static String useInterfaceCaching = "";
   private static String defaultCacheName = "";
   
   public void init(ServletConfig config) throws ServletException {
	   
    super.init(config);
    ServletContext sc = config.getServletContext();
    useInterfaceCaching = (String)sc.getAttribute("useInterfaceCaching");
    defaultCacheName = (String)sc.getAttribute("DefaultCacheName");
    ICP=(InterfaceCachePojo)sc.getAttribute("ICP");
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
  			 throws ServletException, IOException {
	  
	      ServletOutputStream out=response.getOutputStream();
	     
			String InterfaceID=request.getParameter("IID");
			
			if (InterfaceID == null)
			{
				out.println("No Interface ID sent as parameter");
			    System.out.println("No Interface ID sent as parameter");
				return;
			}

/*			
			if (false == NewDataBaseLayer.checkIfInterfaceExists(InterfaceID))
			{
				out.println("Interface \"" + InterfaceID + "\" not found");
			    System.out.println("Interface \"" + InterfaceID + "\" not found");
				return;
			}
*/
			String getRoleCheck=NewDataBaseLayer.getRoleCheck(InterfaceID);
			if(getRoleCheck==null)
				getRoleCheck="";
			
			String getContentTypeHtml=NewDataBaseLayer.getContentTypeHtml(InterfaceID);
			if(getContentTypeHtml==null)
				response.setContentType("text/html");
            else
                response.setContentType(getContentTypeHtml);
							
			String getcachecontrol=NewDataBaseLayer.getcachecontrol(InterfaceID);
			if(getcachecontrol!=null)
				response.setHeader("Cache-Control", getcachecontrol);

			String getexpire=NewDataBaseLayer.getexpires(InterfaceID);
			//Long  exp=Long.parseLong(getexpire) ;
			response.setHeader("Expires",getexpire);

			String getlast=NewDataBaseLayer.getLast(InterfaceID);
			//Long  lastmod=Long.parseLong(getlast) ;
			response.setHeader("Last-Modified",getlast);	

		  	HttpSession mysession = null;
			String getSessionCheck=NewDataBaseLayer.getSessionCheck(InterfaceID);
			if (getSessionCheck.equals("true"))
				mysession=request.getSession(true);
			else 
				mysession=request.getSession(false);

			String page = null;
			boolean cacheRunning = isCacheRunning(); 
			InputStream in= null;


if(getRoleCheck.equals("") || getRoleCheck.equals("true"))
	{
//		System.out.println("==========getRoleCheck====1="+getRoleCheck);
		String  user_id = (String)mysession.getAttribute("user_id");
        if(user_id==null)
		{
			user_id="";
			System.out.println("role=true and user id = null ????");
			out.println("role=true and user id = null ????");
//			response.sendRedirect("./interfaceenginev2.PortalServlet?IID=LoginPage");
			return;
		}
		else
		{
//			if ((true == cacheRunning) && (true == ICP.checkCachingRequired(InterfaceID)))
//  per interface cache enablement checking is too expensive performance wise					
			if ((true == cacheRunning))
			{
				System.out.println("role=user and caching=true");
//				String cache_name = ICP.getCacheName(InterfaceID);
				String cache_name = defaultCacheName;   // per interface cache name is too expensive performance wise
				String cache_key = user_id+InterfaceID;
				page = ICP.getInterfaceFromCacheName(cache_name,cache_key);
				if(page==null)
					page="";
				if(page.equals(""))
				{
					System.out.println("cache is empty");
					String role_id=NewDataBaseLayer.getRoleID(user_id);
					String interfaceengine_role_id=NewDataBaseLayer.getInterfaceengineRoleID(role_id.toUpperCase());
					Vector imagefile=NewDataBaseLayer.getHTMLAsString(interfaceengine_role_id,InterfaceID);
					if (imagefile!=null) 
					{
						for(int j=0;j<imagefile.size();j=j+1)
						{
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
						return;
					}
					out.println("Cache is empty but markup not avalible from database");   //This should not happen
					System.out.println("Cache is empty but markup not avalible from database");
					return;
				}
				else  //send the contents of the cache
				{
					System.out.println("cache is not empty; send markup from cache");
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
					return;
				}
			}
			//No caching
			System.out.println("role=user and caching=false");
			String role_id=NewDataBaseLayer.getRoleID(user_id);
			String interfaceengine_role_id=NewDataBaseLayer.getInterfaceengineRoleID(role_id.toUpperCase());
//			System.out.println(".................interfaceengine_role_id........"+interfaceengine_role_id);
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
				return;
			}
			else    //This should never happen
			{				
				out.println("Markup not avalible from database");   //This should not happen
				System.out.println("Markup not avalible from database");
				return;
			}	
		}
	}



if(getRoleCheck.equals("nouser"))
	{
		if ((true == cacheRunning))  // && (true == ICP.checkCachingRequired(InterfaceID)))
		{
			System.out.println("role=nouser and caching=true");
//			String cache_name = ICP.getCacheName(InterfaceID);
			String cache_name = defaultCacheName;   // per interface cache name is too expensive performance wise
			String cache_key = InterfaceID;
			page = ICP.getInterfaceFromCacheName(cache_name,cache_key);
			if(page==null)
				page="";
			if(page.equals(""))
			{
				System.out.println("cache is empty");
				String role=(String)mysession.getAttribute("role_id");
				String role_id=NewDataBaseLayer.getDefaultRoleID(role);
				Vector imagefile=NewDataBaseLayer.getHTMLAsString(role_id,InterfaceID);
				if (imagefile!=null) 
				{
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
					return;
				}
				else
				{
					out.println("Cache is empty but markup not avalible from database");   //This should not happen
					System.out.println("Cache is empty but markup not avalible from database");
					return;
				}
			}
			else //send the contents of the cache
			{
				System.out.println("cache is not empty; send markup from cache");
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
				return;
			}					
		}	
		// no caching
		System.out.println("role=nouser and caching=false");
		String role=(String)mysession.getAttribute("role_id");
		String role_id=NewDataBaseLayer.getDefaultRoleID(role);
//		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>role_id>>>>>>>>>>"+role);
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
			return;
		}
		else
		{
			out.println("Markup not avalible from database");   //This should not happen
			System.out.println("Markup not avalible from database");
			return;
		}
	}

String roleString = "DEFAULT";

if(getRoleCheck.equals("false"))
	{
		if ((true == cacheRunning))// && (true == ICP.checkCachingRequired(InterfaceID)))
		{
			System.out.println("role=false and caching=true");
//			String cache_name = ICP.getCacheName(InterfaceID);
			String cache_name = defaultCacheName;   // per interface cache name is too expensive performance wise
			String cache_key = InterfaceID;
			page = ICP.getInterfaceFromCacheName(cache_name,cache_key);
			if(page==null)
				page="";
			if(page.equals(""))  //cache is empty
			{
				System.out.println("cache is empty");					
				String role=NewDataBaseLayer.getDefaultRoleID(roleString);
				Vector imagefile=NewDataBaseLayer.getHTMLAsString(role,InterfaceID); //read from database
				if (imagefile!=null) {
					for(int j=0;j<imagefile.size();j=j+1){
						String page_string = (String)imagefile.elementAt(0);
						ICP.setInterfaceFromCacheName(cache_name,cache_key,page_string); //write to cache
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
					return;
				}
				else
				{
					out.println("Cache is empty but markup not avalible from database");   //This should not happen
					System.out.println("Cache is empty but markup not avalible from database");
					return;
				}
			}
			else  //send the contents of the cache
			{
				System.out.println("cache is not empty; send markup from cache");					
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
				return;
			}
		}
		// no caching
		System.out.println("role=false and caching=false");
		String role=NewDataBaseLayer.getDefaultRoleID(roleString);
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
			return;
		}
		else
		{
			out.println("Markup not avalible from database");   //This should not happen
			System.out.println("Markup not avalible from database");
			return;
		}
	}        
										
}

  public void doGet(HttpServletRequest request,HttpServletResponse response)
				throws ServletException,IOException
{
			
          doPost(request, response);
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
