/*
 * All content copyright (c) Terracotta, Inc., except as may otherwise be noted in a separate copyright notice. All
 * rights reserved.
 */
package interfaceenginev2;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import net.sf.ehcache.config.DiskStoreConfiguration;
// import java.awt.Color;
// import java.util.Iterator;
// import java.util.ArrayList;
import java.io.*;
import java.util.*;
import net.sf.ehcache.Status;

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
			  
			  diskStorePath = System.getProperty((String)vCacheSub.elementAt(11)) + "/"+ cacheManager;
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
	  
	  System.out.println("=========cache_name====="+cache_name);
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


  public void setInterfaceFromCacheName(String cache_name,String key,String page) {
// 	  Element elem = getCache().get(name);
	  
	  try{
		  getCacheDetails(cache_name).put(new Element(key, page));
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
}
