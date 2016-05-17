package interfaceenginev2;

import java.util.Vector;

import javax.servlet.ServletException;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;
import net.sf.ehcache.config.CacheConfiguration;
// import java.awt.Color;
// import java.util.Iterator;
// import java.util.ArrayList;

public class InterfaceCachePojo {
  CacheManager  cacheManager;
  
  private String CacheName;
  private String CacheType;
  private int max_element;
  private boolean OverflowToDisk;
  
  private int timeToLiveSeconds;
  private int timeToIdleSeconds;
  
  private boolean eternal;
  private boolean diskPersistent;
  private String memoryStoreEvictionPolicy;
  private String diskStorePath;
  private int diskExpiryThreadIntervalSeconds;
//   DiskStoreConfiguration diskStoreConfigurationParameter = new DiskStoreConfiguration();
  
  private String default_cache = "";
  private Vector vApplicationTemplate = new Vector();
  private Vector vDefaultApplicationTemplate = new Vector();

   
  public InterfaceCachePojo() {
	  System.out.println("================");
	  cacheManager = new CacheManager();
	  
	  
// 	  Vector vCacheName = NewDataBaseLayer.getCacheDefinition();
	  String CacheId = "";
	  CacheName = "";
	  CacheType = "";
	  String MaxElem = "";
	 
	  max_element=0;
	  
	  
	  Vector vCacheName = NewDataBaseLayer.getCacheDefinition();
	  System.out.println("=========vCacheName==================="+vCacheName.size());
	  if(vCacheName.size()!=0)
	  {
		  for(int i=0;i<vCacheName.size();i++)
		  {
			  Vector vCacheSub = (Vector)vCacheName.elementAt(i);
			  CacheId = (String)vCacheSub.elementAt(0);
			  CacheName = (String)vCacheSub.elementAt(1);
			  System.out.println("============CacheName========"+CacheName);
			  CacheType = (String)vCacheSub.elementAt(2);
			  MaxElem = (String)vCacheSub.elementAt(3);
			  OverflowToDisk = (Boolean)vCacheSub.elementAt(4);
			  
			  timeToLiveSeconds=(Integer)vCacheSub.elementAt(5);
			  timeToIdleSeconds=(Integer)vCacheSub.elementAt(6);
			  eternal=(Boolean)vCacheSub.elementAt(8);
			  diskPersistent=(Boolean)vCacheSub.elementAt(7);
			  memoryStoreEvictionPolicy = (String)vCacheSub.elementAt(10);
// 			  diskStorePath=(String)vCacheName.elementAt(11);
			  diskExpiryThreadIntervalSeconds=(Integer)vCacheSub.elementAt(9);
			  
			  String definedPath = (String)vCacheSub.elementAt(11);
			  if (definedPath.equals("")) 
				  definedPath="java.io.tmpdir";
			  diskStorePath = System.getProperty(definedPath) + "/"+ cacheManager;
// 			  diskStoreConfigurationParameter.setPath(diskStorePath);
			  System.out.println("=====OverflowToDisk==="+OverflowToDisk);
			  
			  max_element = Integer.parseInt(MaxElem);
			  
// 			  cacheManager.addCache(CacheName);          
// 			  Cache memoryOnlyCache = new Cache(CacheName, 5000, false, false, 5, 2);
			  Cache testCache = new Cache(
					  new CacheConfiguration(CacheName, max_element)
					  .memoryStoreEvictionPolicy(memoryStoreEvictionPolicy)
					  .overflowToDisk(OverflowToDisk)
					  .eternal(eternal)
					  .timeToLiveSeconds(timeToLiveSeconds)
					  .timeToIdleSeconds(timeToIdleSeconds)
					  .diskPersistent(diskPersistent)
					  .diskStorePath(diskStorePath)
					  .diskExpiryThreadIntervalSeconds(diskExpiryThreadIntervalSeconds));   
			  
			  cacheManager.addCache(testCache);
			  testCache.setStatisticsEnabled(true);
		  }
		  
	  }
	  else
	  {
		  CacheName="sampleCacheDefault";
		  timeToLiveSeconds=1000;
		  timeToIdleSeconds=1000;
		  diskStorePath="java.io.tmpdir";
		  diskExpiryThreadIntervalSeconds=0;
		  max_element=10;
		  Cache testCache = new Cache(
				  new CacheConfiguration(CacheName, max_element)
				  .memoryStoreEvictionPolicy("LRU")
				  .overflowToDisk(true)
				  .eternal(false)
				  .timeToLiveSeconds(timeToLiveSeconds)
				  .timeToIdleSeconds(timeToIdleSeconds)
				  .diskPersistent(true)
				  .diskStorePath(diskStorePath)
				  .diskExpiryThreadIntervalSeconds(diskExpiryThreadIntervalSeconds));
		  cacheManager.addCache(testCache);  
	  }
	  
	  
	  
// // 	  cacheManager = CacheManager.create();
// 	  System.out.println("=====111111===========");
	  
    
  }

  
  public String getInterface(String key) {
	  Element elem = getCacheDetails(key).get(key);
	  
		  if(elem == null)
		  {
			  return null;
		  }
		  
	  return (String)elem.getValue();
  }
  
  public String getInterfaceFromCacheName(String cache_name,String key) {
	  
//	  System.out.println("=========cache_name====="+cache_name);
	  if(cache_name.equals(""))
	  {
		  return null;
	  }
	  else
	  {
		  Element elem = getCacheDetails(cache_name).get(key);
		  if(elem == null)
			  return null;
		  else
		  	return (String)elem.getValue();
	  }
  }

 public byte [] getByteArrayFromCacheName(String cache_name,String key) {
	  
//	  System.out.println("=========cache_name====="+cache_name);
	  if(cache_name.equals(""))
	  {
		  return null;
	  }
	  else
	  {
		  Element elem = getCacheDetails(cache_name).get(key);
		  if(elem == null)
			  return null;
		  else
		  	return (byte[])elem.getValue();
	  }
  }

  public void setInterfaceFromCacheName(String cache_name,String key,String page) {
// 	  Element elem = getCache().get(name);
	  
	  try{
		  getCacheDetails(cache_name).put(new Element(key, page));
	  }
	  catch(Exception e){e.printStackTrace();}
  }
  
  public void setByteArrayFromCacheName(String cache_name,String key,byte [] bytes) {
// 	  Element elem = getCache().get(name);
	  
	  try{
		  getCacheDetails(cache_name).put(new Element(key, bytes));
	  }
	  catch(Exception e){e.printStackTrace();}
  }
  
  public void setInterface(String key,String page) {
// 	  Element elem = getCache().get(name);
	  try{
		  getCacheDetails(key).put(new Element(key, page));
	  }
	  catch(Exception e){e.printStackTrace();}
  }
  
  
  public void removeKey(String cache_name,Object key) {
// 	  Element elem = getCache().get(name);
	  try{
		  getCacheDetails(cache_name).remove(key);
	  }
	  catch(Exception e){e.printStackTrace();}
  }
  
  
  public boolean isKeyInCache(String cache_name,String key) {
// 	  Element elem = getCache().get(name);
	  return getCacheDetails(cache_name).isKeyInCache(key);
	  
  }
//   public long getTTL() {
//     return getCache().getCacheConfiguration().getTimeToLiveSeconds();
//   }
// 
//   public long getTTI() {
//     return getCache().getCacheConfiguration().getTimeToIdleSeconds();
//   }
// 
//   public int getSize() {
//     return getCache().getSize();
//   }

  
  public Cache getCache(String name) {
	  return cacheManager.getCache(name);
  }
  public void shutdownCache() {
	  cacheManager.shutdown();
  }
  
  public void clearCache(String name) {
	  cacheManager.removeCache(name);
  }
  
  public String getName() {
	  return cacheManager.getName();
  }
  
  public String getStatus() {
	  Status cacheManagerStatus = cacheManager.getStatus();
	  return cacheManagerStatus.toString();
  }

  
//   public String[] getCacheNames(){
// 	  return cacheManager.getCacheNames();
//   }
  
  public Cache getCacheDetails(String name) {
	  Cache test=null;
	  test = cacheManager.getCache(name);
	  return test;
  }
  
  public boolean checkCachingRequired(String InterfaceID)
  {
  	String InterfaceCachingStatus = NewDataBaseLayer.getInterfaceCachingStatus(InterfaceID);
  	
  	if(InterfaceCachingStatus.equalsIgnoreCase("true"))
  		return true;
  	
  	if(InterfaceCachingStatus.equalsIgnoreCase("false"))
  		return false;

  	String template_caching_status = "";
  	String application_template_id = NewDataBaseLayer.getApplicationTemplateId(InterfaceID);
  	if (application_template_id != "")
  	{
  		if(vApplicationTemplate!=null)
  		{
  			for(int i=0;i<vApplicationTemplate.size();i++)
  			{
  				Vector vApplicationTemplateSub = (Vector)vApplicationTemplate.elementAt(i);
  				String v_template_id = (String)vApplicationTemplateSub.elementAt(1);
  				if(v_template_id.equals(application_template_id))
  				{
  					template_caching_status = (String)vApplicationTemplateSub.elementAt(2);
  					if(template_caching_status.equals("true")) 
  						return true;
  					if(template_caching_status.equals("false")) 
  						return false;
  					return false;   // application template is defined but the template does not define caching
  									// the Default Template is NOT consulted for caching status if the interface
  									// defines an application template and that template does not define caching
  				}
  			}
  			// This should never happen - vApplicationTemplate is not null but none of the template ids match
  			System.out.println("==vApplicationTemplate is not null but none of the template ids match==");
  			return false;
  		}
  		// This should never happen - application template is defined but vApplicationTemplate is null
  		System.out.println("==application template is defined but vApplicationTemplate is null==");
  		return false;
  	}

  	// The interface does not define a template so we consult the Default Template
  	
  	if(vDefaultApplicationTemplate!=null)
  	{
  		for(int i=0;i<vDefaultApplicationTemplate.size();i++)
  		{
  			Vector vDefaultApplicationTemplateSub = (Vector)vDefaultApplicationTemplate.elementAt(i);
  			template_caching_status = (String)vDefaultApplicationTemplateSub.elementAt(2);
  			if(template_caching_status.equals("true")) 
  				return true;
  			if(template_caching_status.equals("false")) 
  				return false;
  		}
  	}

  	return false;   // none of the 3 entities (interface, template, default template) define "caching_anabled" 
}
  		
  public boolean checkJSCachingRequired(String InterfaceID)
  {
  	String InterfaceCachingStatus = NewDataBaseLayer.getResourceJSCachingStatus(InterfaceID);
  	
  	if(InterfaceCachingStatus.equalsIgnoreCase("true"))
  		return true;
  	
  	if(InterfaceCachingStatus.equalsIgnoreCase("false"))
  		return false;

  	String template_caching_status = "";
  	String application_template_id = NewDataBaseLayer.getApplicationTemplateId(InterfaceID);
  	if (application_template_id != "")
  	{
  		if(vApplicationTemplate!=null)
  		{
  			for(int i=0;i<vApplicationTemplate.size();i++)
  			{
  				Vector vApplicationTemplateSub = (Vector)vApplicationTemplate.elementAt(i);
  				String v_template_id = (String)vApplicationTemplateSub.elementAt(1);
  				if(v_template_id.equals(application_template_id))
  				{
  					template_caching_status = (String)vApplicationTemplateSub.elementAt(4);
  					if(template_caching_status.equals("true")) 
  						return true;
  					if(template_caching_status.equals("false")) 
  						return false;
  					return false;   // application template is defined but the template does not define caching
  									// the Default Template is NOT consulted for caching status if the interface
  									// defines an application template and that template does not define caching
  				}
  			}
  			// This should never happen - vApplicationTemplate is not null but none of the template ids match
  			System.out.println("==vApplicationTemplate is not null but none of the template ids match==");
  			return false;
  		}
  		// This should never happen - application template is defined but vApplicationTemplate is null
  		System.out.println("==application template is defined but vApplicationTemplate is null==");
  		return false;
  	}

  	// The interface does not define a template so we consult the Default Template
  	
  	if(vDefaultApplicationTemplate!=null)
  	{
  		for(int i=0;i<vDefaultApplicationTemplate.size();i++)
  		{
  			Vector vDefaultApplicationTemplateSub = (Vector)vDefaultApplicationTemplate.elementAt(i);
  			template_caching_status = (String)vDefaultApplicationTemplateSub.elementAt(4);
  			if(template_caching_status.equals("true")) 
  				return true;
  			if(template_caching_status.equals("false")) 
  				return false;
  		}
  	}

  	return false;   // none of the 3 entities (interface, template, default template) define "cache_dynamic_js" 
  }
  
  public boolean checkCSSCachingRequired(String InterfaceID)
  {
  	String InterfaceCachingStatus = NewDataBaseLayer.getResourceCSSCachingStatus(InterfaceID);
  	
  	if(InterfaceCachingStatus.equalsIgnoreCase("true"))
  		return true;
  	
  	if(InterfaceCachingStatus.equalsIgnoreCase("false"))
  		return false;

  	String template_caching_status = "";
  	String application_template_id = NewDataBaseLayer.getApplicationTemplateId(InterfaceID);
  	if (application_template_id != "")
  	{
  		if(vApplicationTemplate!=null)
  		{
  			for(int i=0;i<vApplicationTemplate.size();i++)
  			{
  				Vector vApplicationTemplateSub = (Vector)vApplicationTemplate.elementAt(i);
  				String v_template_id = (String)vApplicationTemplateSub.elementAt(1);
  				if(v_template_id.equals(application_template_id))
  				{
  					template_caching_status = (String)vApplicationTemplateSub.elementAt(5);
  					if(template_caching_status.equals("true")) 
  						return true;
  					if(template_caching_status.equals("false")) 
  						return false;
  					return false;   // application template is defined but the template does not define caching
  									// the Default Template is NOT consulted for caching status if the interface
  									// defines an application template and that template does not define caching
  				}
  			}
  			// This should never happen - vApplicationTemplate is not null but none of the template ids match
  			System.out.println("==vApplicationTemplate is not null but none of the template ids match==");
  			return false;
  		}
  		// This should never happen - application template is defined but vApplicationTemplate is null
  		System.out.println("==application template is defined but vApplicationTemplate is null==");
  		return false;
  	}

  	// The interface does not define a template so we consult the Default Template
  	
  	if(vDefaultApplicationTemplate!=null)
  	{
  		for(int i=0;i<vDefaultApplicationTemplate.size();i++)
  		{
  			Vector vDefaultApplicationTemplateSub = (Vector)vDefaultApplicationTemplate.elementAt(i);
  			template_caching_status = (String)vDefaultApplicationTemplateSub.elementAt(5);
  			if(template_caching_status.equals("true")) 
  				return true;
  			if(template_caching_status.equals("false")) 
  				return false;
  		}
  	}

  	return false;   // none of the 3 entities (interface, template, default template) define "cache_dynamic_css" 
  }
  
  public boolean checkImageCachingRequired(String InterfaceID)
  {
  	String InterfaceCachingStatus = NewDataBaseLayer.getResourceImageCachingStatus(InterfaceID);
  	
  	if(InterfaceCachingStatus.equalsIgnoreCase("true"))
  		return true;
  	
  	if(InterfaceCachingStatus.equalsIgnoreCase("false"))
  		return false;

  	String template_caching_status = "";
  	String application_template_id = NewDataBaseLayer.getApplicationTemplateId(InterfaceID);
  	if (application_template_id != "")
  	{
  		if(vApplicationTemplate!=null)
  		{
  			for(int i=0;i<vApplicationTemplate.size();i++)
  			{
  				Vector vApplicationTemplateSub = (Vector)vApplicationTemplate.elementAt(i);
  				String v_template_id = (String)vApplicationTemplateSub.elementAt(1);
  				if(v_template_id.equals(application_template_id))
  				{
  					template_caching_status = (String)vApplicationTemplateSub.elementAt(6);
  					if(template_caching_status.equals("true")) 
  						return true;
  					if(template_caching_status.equals("false")) 
  						return false;
  					return false;   // application template is defined but the template does not define caching
  									// the Default Template is NOT consulted for caching status if the interface
  									// defines an application template and that template does not define caching
  				}
  			}
  			// This should never happen - vApplicationTemplate is not null but none of the template ids match
  			System.out.println("==vApplicationTemplate is not null but none of the template ids match==");
  			return false;
  		}
  		// This should never happen - application template is defined but vApplicationTemplate is null
  		System.out.println("==application template is defined but vApplicationTemplate is null==");
  		return false;
  	}

  	// The interface does not define a template so we consult the Default Template
  	
  	if(vDefaultApplicationTemplate!=null)
  	{
  		for(int i=0;i<vDefaultApplicationTemplate.size();i++)
  		{
  			Vector vDefaultApplicationTemplateSub = (Vector)vDefaultApplicationTemplate.elementAt(i);
  			template_caching_status = (String)vDefaultApplicationTemplateSub.elementAt(6);
  			if(template_caching_status.equals("true")) 
  				return true;
  			if(template_caching_status.equals("false")) 
  				return false;
  		}
  	}

  	return false;   // none of the 3 entities (interface, template, default template) define "cache_dynamic_image" 
  }
  
  public String getCacheName(String InterfaceID)
  {
  	String cache_name = "";
  	cache_name = NewDataBaseLayer.getInterfaceCacheName(InterfaceID);
  	if ( !cache_name.equals("") ) 
  		return cache_name;
  	else     						//interface xml does not define any cache name
  	{
  		System.out.println("==interface xml does not define any cache name; going to template ==");
  		String application_template_id = NewDataBaseLayer.getApplicationTemplateId(InterfaceID);
  		
  		if (!application_template_id.equals(""))
  		{
  			if(vApplicationTemplate!=null)
  			{
  				for(int i=0;i<vApplicationTemplate.size();i++)
  				{
  					Vector vApplicationTemplateSub = (Vector)vApplicationTemplate.elementAt(i);
  					String v_template_id = (String)vApplicationTemplateSub.elementAt(1);
  					if(v_template_id.equals(application_template_id))
  					{
  						cache_name = (String)vApplicationTemplateSub.elementAt(3);
  						if (cache_name.equals(""))
  							System.out.println("==application template does not define cache name ==");
  						break;
  					}
  				}
  			}
  			else
  			{
  				// This should never happen - application template is defined but vApplicationTemplate is null
  				System.out.println("==application template is defined but vApplicationTemplate is null==");
  			}
  		}
  		else  //the interface does not define an application template 
  		{
  			System.out.println("==no template is defined going to default template ==");
  			if(vDefaultApplicationTemplate!=null)   // check for cache name in the default application template
  			{
  				for(int i=0;i<vDefaultApplicationTemplate.size();i++)
  				{
  					Vector vDefaultApplicationTemplateSub = (Vector)vDefaultApplicationTemplate.elementAt(i);
  					cache_name = (String)vDefaultApplicationTemplateSub.elementAt(3);
  					if (cache_name.equals(""))
  						System.out.println("==default template does not define cache name ==");
  					break;
  				}
  			}
  			else
  			{
  				// This should never happen - neither the interface defines a template nor is there a default template
  				System.out.println("==no default template is avaiable ==");
  			}
  		}
  		if(cache_name.equals("")) //neither the interface's template nor the default template define cache name
  		{
  			if (default_cache.equals(""))
  				System.out.println("== interface, template, default template do not define cache name and no default cache is available");
  			else
  			{
  				System.out.println("==interface, template, default template do not define cache name; using the default cache name ==");
  				cache_name = default_cache;
  			}
  		}
  	}//end of cache_name equal blank if
  		
  	return cache_name;
  }

  public void InitialiseCacheDataFromTemplate(String defaultCache, Vector templates, Vector defaultTemplate) throws ServletException 
  {
	    default_cache = defaultCache;
	    vApplicationTemplate = templates;
	    vDefaultApplicationTemplate = defaultTemplate;
  }
}
