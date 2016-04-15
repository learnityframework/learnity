package interfaceenginev2;


import net.sf.json.JSONArray;


public class GsonChildjs {
	private String title;
	private String key;
	private String isFolder;
	private String isLazy;
	private String tooltip;
	private String p_id;
	private String url;
	private String children;
	public GsonChildjs()
	{
		
	}
	public GsonChildjs(String strkey)
	{
		
		
	}
	public GsonChildjs(String strkey,String strname)
	{
		
	}
	public GsonChildjs(String strkey,String strname,String strp_id,String tooltip1,String url1,String folder,String lazy)
	{
			title=strname;
			key = strkey;
			if(folder.equals("true"))
			{
			isFolder = folder;
			isLazy = lazy;
			}
			
	// 		if(lazy.equals("true"))
	// 		{
	// 		isLazy = lazy;
	// 		}
			if(tooltip1.equals("true"))
			{
				tooltip = strname;
			}
			p_id = strp_id;
			
			if(url1==null  || url1.equals(""))
			{
				url="";
			}
			if(!url1.equals("")) 
			{
				url=url1;
			}
		
	}
	
	
	
	public GsonChildjs(String strkey,String strname,String strp_id,String tooltip1,String url1,String folder,String lazy,JSONArray arrayObj1)
	{
		try
		{
				title=strname;
				key = strkey;
				if(folder.equals("true"))
				{
					isFolder = folder;
					isLazy = lazy;
					children=arrayObj1.toString() ;
				}
	// 			if(lazy.equals("true"))
	// 			{
	// 				isLazy = lazy;
	// 			}
			
				if(url1==null || url1.equals(""))
				{
					url="";
				}
				if(!url1.equals("")) 
				{
					url=url1;
				}
						
				if(tooltip1.equals("true"))
				{
					tooltip = strname;
				}
				p_id = strp_id;
			}
		catch(Exception e){e.printStackTrace();}
	}
	
	
	////////////////////dynamictree////////////////////////
	
	public GsonChildjs(String strkey,String strname,String islazy1,String tooltip1,String parent_id,String childquery)
	{// constructor with six parameter
		title=strname;
		key = strkey;
		if(NewDataBaseLayer.hasChildNodes(strkey,childquery))
		{
			isFolder = "true";
			isLazy= islazy1;
		}		
		if(tooltip1.equals("true"))
		{
			tooltip = strname;
		}
		p_id = parent_id;	
		
	}	
	public GsonChildjs(String strkey,String strname,String parent_id,String tooltip1,JSONArray arrayObj)
	{// constructor with five parameter
		title=strname;
		key = strkey;		
		isFolder = "true";		
		if(tooltip1.equals("true"))
		{
			tooltip = strname;
		}		
		p_id = parent_id;
		children=arrayObj.toString() ;	
		
	}
	public GsonChildjs(String strkey,String strname,String tooltip1,String parent_id)
	{// constructor with four parameter
		title=strname;
		key = strkey;		
		if(tooltip1.equals("true"))
		{
			tooltip = strname;
		}		
		p_id = parent_id;		
	}
	///////////////////////////////////////////////////////

	
	
}
