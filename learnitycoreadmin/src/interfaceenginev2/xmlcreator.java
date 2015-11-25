
package interfaceenginev2;
//import java.sql.*;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.SQLException;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.grlea.log.SimpleLogger;

public class xmlcreator extends HttpServlet{

private static final SimpleLogger log = new SimpleLogger(xmlcreator.class);
private InterfaceCachePojo ICP=null;
private String useInterfaceCaching = "";
private String default_cache = "";
private Vector vApplicationTemplate = new Vector();
private Vector vDefaultApplicationTemplate = new Vector();

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
	System.out.println("default_cache");
	if(default_cache==null)
		default_cache = "";
    
	vApplicationTemplate = (Vector)sc.getAttribute("ApplicationTemplateConf");
	vDefaultApplicationTemplate = (Vector)sc.getAttribute("DefaultApplicationTemplateConf");
    
}


	public void doGet(HttpServletRequest req,HttpServletResponse res)
		throws ServletException,IOException{
			ServletOutputStream out=res.getOutputStream();
			InputStream in;
			res.setContentType("text/xml; charset=UTF-8");
	/**
	 * @param JDBC
	 *            ResultSet to be transformed into a DOM instance
	 * @return DOM instance conforming to W3C's Document interface
	 * @throws SQLException
	 */
	 HttpSession mysession = req.getSession(true);
	 String page = req.getParameter("page"); // get the requested page
	 if(page==null)page="1";
	  String limit = req.getParameter("rows"); // get how many rows we want to have into the grid
	  String sidx = req.getParameter("sidx"); // get index row - i.e. user click to sort
	  String sord = req.getParameter("sord"); // get the direction
	  String interface_id = req.getParameter("interface_id");
	  String part_id =req.getParameter("part_id");
	  String search = req.getParameter("_search");
	  String  user_id = (String)mysession.getAttribute("user_id");

	  String cache_key = "";
	  System.out.println("==============xmlcreator========cache_key======="+cache_key);

	  Vector reuseVector = (Vector)mysession.getAttribute("REUSE_STRING");
	  String reuseString = "";
	  if(reuseVector!=null)
	  {
	  	for(int i=0;i<reuseVector.size();i++)
	  	{
			if(((String)reuseVector.elementAt(i)).equals(interface_id+"-"+part_id+"-reuse"))
		  	{
				reuseString="Reuse";
		  	} 
		  	else
		  	{
				reuseString="";
		  	}
	  	}
	  }
	  
	  if(reuseString==null)
		  reuseString="";
	     System.out.println("================reuseString============"+reuseString);
		  if(search==null)
	     search="false";

	  if(sord==null || (sord.equalsIgnoreCase("null")) || (sord.equals("")))
		  sord = NewDataBaseLayer.getSortOrder(interface_id,part_id);
	  if(sidx==null || (sidx.equalsIgnoreCase("null")) || (sidx.equals("")))
		  sidx = NewDataBaseLayer.getSortname(interface_id,part_id);
	  //System.out.println(".........2......SORDER..............SIDX........."+sord+"........."+sidx);
	  if(sord==null)
		  sord="";

	  if(sidx==null)
		  sidx="";
	  //System.out.println(".........3......SORDER..............SIDX........."+sord+"........."+sidx);
	  int int_page = Integer.parseInt(page);
	  int int_limit = Integer.parseInt(limit);
	  
	  int query_limit_start = 0;
	  if(int_page==1)
	  query_limit_start=0;
	  else
	  query_limit_start=(int_page-1)*int_limit;
	  
// 	  System.out.println("=============int_page========="+int_page);
// 	  System.out.println("=============int_limit========="+int_limit);
// 	  System.out.println("=============query_limit_start========="+query_limit_start);
	  
	  
	  
	  String sql_query = "";
	  String[] namepairvalue = (String[])mysession.getAttribute("namepairvalue");
// 	  System.out.println("==================namepairvalue==========="+namepairvalue);
	  Vector param_title_vector = new Vector();
	  Vector param_title_value = new Vector();
	  String parameter_title=NewDataBaseLayer.getLoadQueryParameterTitle(interface_id,part_id);
	  System.out.println("===============parameter_title========="+parameter_title);
	  if(parameter_title==null)
		  parameter_title="";
	  if(!parameter_title.equals(""))
	    	param_title_vector = StringtoVector(parameter_title);
	  System.out.println("===============param_title_vector.size()========="+param_title_vector.size());
	  if(param_title_vector.size()==0)
	  {
		  System.out.println("==================if========");
		  sql_query = PortalEngine.ChangeVectorGridLoadQueryForGrid(interface_id,part_id,namepairvalue);  
	  }
	  else
	  {
		  System.out.println("==================else========");
		  for(int j=0;j<param_title_vector.size();j++)
		  {
				
			  System.out.println("===============param_title_vector.elementAt(j)========"+(String)param_title_vector.elementAt(j));
			  
			  String param_value =(String)mysession.getAttribute((String)param_title_vector.elementAt(j));
			  System.out.println("==============param_value========"+param_value);
			  param_title_value.addElement((String)param_title_vector.elementAt(j));
			  param_title_value.addElement(param_value);
		  }
		  
		  sql_query = PortalEngine.ChangeVectorGridLoadQueryForGrid(interface_id,part_id,namepairvalue,param_title_value);
	  }
// 	  System.out.println("============sql_query============"+sql_query);
	  String sql1 = "";
	  String sql2 = "";
	  String count_query = "";
	  int query_length = 0;
	  query_length = sql_query.length();
	  System.out.println("=============query_length========"+query_length);
	  sql1 = sql_query.substring(0,7);
	  System.out.println("================sql1============="+sql1);
	  sql2 = sql_query.substring(7,query_length);
	   
	  
	  System.out.println("================sql2============="+sql2);
	  
	  count_query = sql1 + " sql_calc_found_rows " + sql2;
	  System.out.println("===========count_query==========="+count_query);
	  
	  
	  int total_row_count = 0;
	  //ResultSet rs=null;
	  String d = "";
	  String modified_search_query = "";
	  
	  if((sord.equals("")) && (sidx.equals("")))
		  modified_search_query = sql_query;
	  else if((sord.equals("")) && !(sidx.equals("")))
		  modified_search_query = sql_query+" order by "+sidx;
	  else if(!(sord.equals("")) && (sidx.equals("")))
		  modified_search_query = sql_query;
	  else
		  modified_search_query = sql_query+" order by "+sidx+" "+sord;
	  
	  System.out.println("==================modified_search_query========="+modified_search_query);
	String cacheStatus = ICP.getStatus();
	
	String user_id_included_cache = NewDataBaseLayer.getGridCacheIncludeUserID(interface_id,part_id);
	System.out.println("user_id_included_cache============"+user_id_included_cache);
	if(user_id_included_cache.equalsIgnoreCase("true"))
		cache_key = user_id+interface_id+part_id+page;
	else
		cache_key = interface_id+part_id+page;
	
	String cache_name = getCacheName(interface_id,part_id);
	
	String page_string = ICP.getInterfaceFromCacheName(cache_name,cache_key);
	if(page_string==null)
		page_string="";
	System.out.println("===xmlcreator=======page_string====="+page_string);
	if(page_string.equals(""))
	{
		boolean caching_status = checkCachingRequired(interface_id,part_id);
		System.out.print("===========caching_status========"+caching_status);
		if(caching_status==true)
		{
			
			if(reuseString.equals(""))
			{
				if(search.equals("false"))
				{
					total_row_count = NewDataBaseLayer.getSqlQueryCount(count_query,query_limit_start,int_limit);
					d = NewDataBaseLayer.getTableDataFromQuery(sql_query,query_limit_start,int_limit,sord,sidx,page,limit,total_row_count);
				}
				if(search.equals("true"))
				{
					String query_edit = "";
					String col_index = req.getParameter("searchField");
					System.out.println("==================col_index========="+col_index);
		
					if(col_index==null)
						col_index = "";
					if(col_index.equals(""))
					{
						String multi_search_param=req.getParameter("filters");
			//multi_search_param = "[" + multi_search_param + "]";
						System.out.println("==================multi_search_param========="+multi_search_param);
						JSONObject Obj=new JSONObject(multi_search_param);
						String searchoperation = Obj.getString("groupOp");
						System.out.println("==================searchoperation========="+searchoperation);
						JSONArray rules =  Obj.getJSONArray("rules");
			/*String rules =  Obj.getString("rules");
						System.out.println("==================rules========="+rules);
						String json_rules = rules.substring(rules.indexOf("[")+1,rules.indexOf("]"));
						json_rules = "{\"rules\":[" + json_rules + "]}";
						System.out.println("================json_rules========="+json_rules);
						JSONObject rules_Obj = new JSONObject(json_rules);
						JSONArray field_array = rules_Obj.getJSONArray("rules"); */
// 			JSONArray field_array = json_rules.getJSONObject("field");
						for(int k=0;k<rules.length();k++)
						{
							System.out.println("==================rules["+k+"]========="+rules.getString(k));
							JSONObject item_Obj=new JSONObject(rules.getString(k));
							String multi_col_index = item_Obj.getString("field");
							System.out.println("multi_col_index==========="+multi_col_index);
							String multi_col_name = NewDataBaseLayer.getColumnNamefromIndex(interface_id,part_id,multi_col_index);
				
							String multi_operation = item_Obj.getString("op");
				
							String multi_search_data = item_Obj.getString("data");
				
				
				
				
				
							String replace_substring = "";
							String replace_substring1 = "";
							if(CheckString("."+multi_col_name,modified_search_query))
							{
								try
								{
			  
									replace_substring1 = modified_search_query.substring(0,modified_search_query.indexOf("."+multi_col_name));
			  
// 						System.out.println("===========replace_substring1 in search==============="+replace_substring1);
								}
								catch(Exception e)
								{
									e.printStackTrace();
								}
							}
			  
			  
// 			System.out.println("================replace_substring1.length() in search============"+replace_substring1.length());
							int check=0;
							if(replace_substring1.length()==0)
							{
								try
								{
					  
									replace_substring = modified_search_query.substring(0,modified_search_query.indexOf(multi_col_name));
// 					System.out.println("===========replace_substring in search==============="+replace_substring);
								}
								catch(Exception e)
								{
									e.printStackTrace();
								}
								replace_substring1=replace_substring;
							}
							for(int m=replace_substring1.length();m>=0;m--)
							{
// 				System.out.println("=========k in search========"+k);
								String currentChar = replace_substring1.substring(m-1, m);
// 				System.out.println("=========currentChar in search========"+currentChar);
								if ((currentChar.equals(",")) || (currentChar.equals("(")) || (currentChar.equals(" ")))
								{
									check = m;
									break;
								}
							}
// 			System.out.println("=========check in search========"+check);
			  
							String pre_string =  replace_substring1.substring(check);
// 			System.out.println("==========pre_string==========="+pre_string);
	
							if(pre_string.equals(""))
							{
				  
							}
							else
							{
								multi_col_name = pre_string+"."+multi_col_name;
							}
				
							System.out.println("==================multi_col_name========="+multi_col_name);
							System.out.println("multi_operation==========="+multi_operation);
							System.out.println("multi_search_data==========="+multi_search_data);
				
							if(multi_operation.equals("cn"))
								query_edit=multi_col_name+" like '%"+multi_search_data+"%'";
							if(multi_operation.equals("bw"))
								query_edit=multi_col_name+" like '"+multi_search_data+"%'";
							if(multi_operation.equals("eq"))
								query_edit=multi_col_name+" ='"+multi_search_data+"'";
							if(multi_operation.equals("ne"))
								query_edit=multi_col_name+" <>'"+multi_search_data+"'";
							if(multi_operation.equals("lt"))
								query_edit=multi_col_name+" <'"+multi_search_data+"'";
							if(multi_operation.equals("gt"))
								query_edit=multi_col_name+" >'"+multi_search_data+"'";
							if(multi_operation.equals("ew"))
								query_edit=multi_col_name+" like '%"+multi_search_data+"'";
				
				
							if(!CheckString("where",modified_search_query))
							{
					//System.out.println("============if==========");
								if((CheckString("group by",modified_search_query)) && (CheckString("order by",modified_search_query)))
								{
									int test2=modified_search_query.length();
									int test3=modified_search_query.indexOf("group by");
									String before_group_by = modified_search_query.substring(0,modified_search_query.indexOf("group by"));
					
									String after_group_by = modified_search_query.substring(test3,test2);
						//System.out.println("=========before_group_by========"+before_group_by);
						//System.out.println("=========after_group_by========"+after_group_by);
									modified_search_query=before_group_by+" where "+query_edit+" "+after_group_by;
								}
								else if((!CheckString("group by",modified_search_query)) && (CheckString("order by",modified_search_query)))
								{
									int test2=modified_search_query.length();
									int test3=modified_search_query.indexOf("order by");
									String before_order_by = modified_search_query.substring(0,modified_search_query.indexOf("order by"));
									String after_order_by = modified_search_query.substring(test3,test2);
						//System.out.println("=========before_order_by========"+before_order_by);
						//System.out.println("=========after_order_by========"+after_order_by);
									modified_search_query=before_order_by+" where "+query_edit+" "+after_order_by;
								}
								else if((CheckString("group by",modified_search_query)) && (!CheckString("order by",modified_search_query)))
								{
									int test2=modified_search_query.length();
									int test3=modified_search_query.indexOf("group by");
									String before_group_by = modified_search_query.substring(0,modified_search_query.indexOf("group by"));
									String after_group_by = modified_search_query.substring(test3,test2);
						//System.out.println("=========before_group_by========"+before_group_by);
						//System.out.println("=========after_group_by========"+after_group_by);
									modified_search_query=before_group_by+" where "+query_edit+" "+after_group_by;
								}
								else
								{
						//System.out.println("==========if==last else==========");
									modified_search_query=modified_search_query+" where "+query_edit;
								}
					//System.out.println("============if========modified_search_query=="+modified_search_query);
				
							}
							else
							{
					//System.out.println("============else==========");
								if((CheckString("group by",modified_search_query)) && (CheckString("order by",modified_search_query)))
								{
									int test2=modified_search_query.length();
									int test3=modified_search_query.indexOf("group by");
						//System.out.println("============111111111==========");
									String before_group_by = modified_search_query.substring(0,modified_search_query.indexOf("group by"));
									String after_group_by = modified_search_query.substring(test3,test2);
						//System.out.println("====else=====before_group_by========"+before_group_by);
						//System.out.println("====else=====after_group_by========"+after_group_by);
									modified_search_query=before_group_by+" and "+query_edit+" "+after_group_by;
								}
								else if((!CheckString("group by",modified_search_query)) && (CheckString("order by",modified_search_query)))
								{
									int test2=modified_search_query.length();
									int test3=modified_search_query.indexOf("order by");
						//System.out.println("============2222222==========");
									String before_order_by = modified_search_query.substring(0,modified_search_query.indexOf("order by"));
									String after_order_by = modified_search_query.substring(test3,test2);
						//System.out.println("====else=====before_order_by========"+before_order_by);
						//System.out.println("====else=====after_order_by========"+after_order_by);
									modified_search_query=before_order_by+" and "+query_edit+" "+after_order_by;
					
								}
								else if((CheckString("group by",modified_search_query)) && (!CheckString("order by",modified_search_query)))
								{
									int test2=modified_search_query.length();
									int test3=modified_search_query.indexOf("group by");
						//System.out.println("============3333333==========");
									String before_group_by = modified_search_query.substring(0,modified_search_query.indexOf("group by"));
									String after_group_by = modified_search_query.substring(test3,test2);
						//System.out.println("====else=====before_group_by========"+before_group_by);
						//System.out.println("====else=====after_group_by========"+after_group_by);
									modified_search_query=before_group_by+" and "+query_edit+" "+after_group_by;
								}
								else
								{
						//System.out.println("========else====last else==========");
									modified_search_query=modified_search_query+" and "+query_edit;
								}
					
					//System.out.println("============else==========modified_search_query===="+modified_search_query);
				
	 			//sql_query=sql_query+" and "+query_edit;
							}
				
				
				
				
				
						}  
					}
					else
					{
						String col_name = NewDataBaseLayer.getColumnNamefromIndex(interface_id,part_id,col_index);
						System.out.println("==================col_name========="+col_name);
						/**************************** For Search with tablename.colname *************/
								String replace_substring = "";
						String replace_substring1 = "";
						if(CheckString("."+col_name,modified_search_query))
						{
							try
							{
				  
								replace_substring1 = modified_search_query.substring(0,modified_search_query.indexOf("."+col_name));
			  
// 					System.out.println("===========replace_substring1 in search==============="+replace_substring1);
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
						}
			  
// 			System.out.println("================replace_substring1.length() in search============"+replace_substring1.length());
						int check=0;
						if(replace_substring1.length()==0)
						{
							try
							{
					  
								replace_substring = modified_search_query.substring(0,modified_search_query.indexOf(col_name));
// 					System.out.println("===========replace_substring in search==============="+replace_substring);
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
							replace_substring1=replace_substring;
						}
						for(int k=replace_substring1.length();k>=0;k--)
						{
// 				System.out.println("=========k in search========"+k);
							String currentChar = replace_substring1.substring(k-1, k);
// 				System.out.println("=========currentChar in search========"+currentChar);
							if ((currentChar.equals(",")) || (currentChar.equals("(")) || (currentChar.equals(" ")))
							{
								check = k;
								break;
							}
						}
// 			System.out.println("=========check in search========"+check);
			  
						String pre_string =  replace_substring1.substring(check);
// 			System.out.println("==========pre_string==========="+pre_string);
	
						if(pre_string.equals(""))
						{
				  
						}
						else
						{
							col_name = pre_string+"."+col_name;
						}
// 			System.out.println("==============col_name======="+col_name);
		
				
						/************************* End of Search with tablename.colname ********************/
		
		
		
								String search_operation = req.getParameter("searchOper");
						if(search_operation.equals("cn"))
							query_edit=col_name+" like '%"+req.getParameter("searchString")+"%'";
						if(search_operation.equals("bw"))
							query_edit=col_name+" like '"+req.getParameter("searchString")+"%'";
						if(search_operation.equals("eq"))
							query_edit=col_name+" ='"+req.getParameter("searchString")+"'";
						if(search_operation.equals("ne"))
							query_edit=col_name+" <>'"+req.getParameter("searchString")+"'";
						if(search_operation.equals("lt"))
							query_edit=col_name+" <'"+req.getParameter("searchString")+"'";
						if(search_operation.equals("gt"))
							query_edit=col_name+" >'"+req.getParameter("searchString")+"'";
						if(search_operation.equals("ew"))
							query_edit=col_name+" like '%"+req.getParameter("searchString")+"'";
	 		
				  
						if(!CheckString("where",modified_search_query))
						{
							System.out.println("============if==========");
							if((CheckString("group by",modified_search_query)) && (CheckString("order by",modified_search_query)))
							{
								int test2=modified_search_query.length();
								int test3=modified_search_query.indexOf("group by");
								String before_group_by = modified_search_query.substring(0,modified_search_query.indexOf("group by"));
					
								String after_group_by = modified_search_query.substring(test3,test2);
								System.out.println("=========before_group_by========"+before_group_by);
								System.out.println("=========after_group_by========"+after_group_by);
								modified_search_query=before_group_by+" where "+query_edit+" "+after_group_by;
							}
							else if((!CheckString("group by",modified_search_query)) && (CheckString("order by",modified_search_query)))
							{
								int test2=modified_search_query.length();
								int test3=modified_search_query.indexOf("order by");
								String before_order_by = modified_search_query.substring(0,modified_search_query.indexOf("order by"));
								String after_order_by = modified_search_query.substring(test3,test2);
								System.out.println("=========before_order_by========"+before_order_by);
								System.out.println("=========after_order_by========"+after_order_by);
								modified_search_query=before_order_by+" where "+query_edit+" "+after_order_by;
							}
							else if((CheckString("group by",modified_search_query)) && (!CheckString("order by",modified_search_query)))
							{
								int test2=modified_search_query.length();
								int test3=modified_search_query.indexOf("group by");
								String before_group_by = modified_search_query.substring(0,modified_search_query.indexOf("group by"));
								String after_group_by = modified_search_query.substring(test3,test2);
								System.out.println("=========before_group_by========"+before_group_by);
								System.out.println("=========after_group_by========"+after_group_by);
								modified_search_query=before_group_by+" where "+query_edit+" "+after_group_by;
							}
							else
							{
								System.out.println("==========if==last else==========");
								modified_search_query=modified_search_query+" where "+query_edit;
							}
							System.out.println("============if========modified_search_query=="+modified_search_query);
				
						}
						else
						{
							System.out.println("============else==========");
							if((CheckString("group by",modified_search_query)) && (CheckString("order by",modified_search_query)))
							{
								int test2=modified_search_query.length();
								int test3=modified_search_query.indexOf("group by");
								System.out.println("============111111111==========");
								String before_group_by = modified_search_query.substring(0,modified_search_query.indexOf("group by"));
								String after_group_by = modified_search_query.substring(test3,test2);
								System.out.println("====else=====before_group_by========"+before_group_by);
								System.out.println("====else=====after_group_by========"+after_group_by);
								modified_search_query=before_group_by+" and "+query_edit+" "+after_group_by;
							}
							else if((!CheckString("group by",modified_search_query)) && (CheckString("order by",modified_search_query)))
							{
								int test2=modified_search_query.length();
								int test3=modified_search_query.indexOf("order by");
								System.out.println("============2222222==========");
								String before_order_by = modified_search_query.substring(0,modified_search_query.indexOf("order by"));
								String after_order_by = modified_search_query.substring(test3,test2);
								System.out.println("====else=====before_order_by========"+before_order_by);
								System.out.println("====else=====after_order_by========"+after_order_by);
								modified_search_query=before_order_by+" and "+query_edit+" "+after_order_by;
					
							}
							else if((CheckString("group by",modified_search_query)) && (!CheckString("order by",modified_search_query)))
							{
								int test2=modified_search_query.length();
								int test3=modified_search_query.indexOf("group by");
								System.out.println("============3333333==========");
								String before_group_by = modified_search_query.substring(0,modified_search_query.indexOf("group by"));
								String after_group_by = modified_search_query.substring(test3,test2);
								System.out.println("====else=====before_group_by========"+before_group_by);
								System.out.println("====else=====after_group_by========"+after_group_by);
								modified_search_query=before_group_by+" and "+query_edit+" "+after_group_by;
							}
							else
							{
								System.out.println("========else====last else==========");
								modified_search_query=modified_search_query+" and "+query_edit;
							}
					
							System.out.println("============else==========modified_search_query===="+modified_search_query);
				
	 			//sql_query=sql_query+" and "+query_edit;
						}
					}
		
		
		

					query_length = modified_search_query.length();
					System.out.println("=============query_length===search====="+query_length);
					sql1 = modified_search_query.substring(0,7);
					System.out.println("================sql1======search======="+sql1);
					sql2 = modified_search_query.substring(7,query_length);
					System.out.println("================sql2=======search======"+sql2);
					count_query = sql1 + " sql_calc_found_rows " + sql2;
					System.out.println("===========count_query=====search======"+count_query);
		
					total_row_count = NewDataBaseLayer.getSqlQueryCount(count_query,query_limit_start,int_limit);
					d = NewDataBaseLayer.getTableDataFromQuerySearch(modified_search_query,query_limit_start,int_limit,sord,sidx,page,limit,total_row_count);
				}
			}
			else
			{
				int count=0;
				Vector  interface_part_vector = new Vector();
				interface_part_vector=(Vector)mysession.getAttribute("INTERFACE_PART_VECTOR");
				for(int i=0;i<interface_part_vector.size();i++)
				{
					String string_to_check = (String)interface_part_vector.elementAt(i);
					if(string_to_check.equals(interface_id+"-"+part_id))
					{
						interface_part_vector.remove(i);
						count=i;
						break;
					}
				
				}
				System.out.println("===================count================"+count);
				Vector  sql_query_vector = new Vector();
				sql_query_vector=(Vector)mysession.getAttribute("SQL_QUERY_VECTOR");
				String reuseSQL = (String)sql_query_vector.elementAt(count);
				total_row_count = NewDataBaseLayer.getSqlQueryCount(reuseSQL,query_limit_start,int_limit);
				d = NewDataBaseLayer.getTableDataFromQuerySearch(reuseSQL,query_limit_start,int_limit,sord,sidx,page,limit,total_row_count);
				sql_query_vector.remove(count);
				mysession.setAttribute("INTERFACE_PART_VECTOR",interface_part_vector);
				mysession.setAttribute("SQL_QUERY_VECTOR",sql_query_vector);
			}		
								
			ICP.setInterfaceFromCacheName(cache_name,cache_key,d);
		}
		else
		{
			if(reuseString.equals(""))
			{
				if(search.equals("false"))
				{
					total_row_count = NewDataBaseLayer.getSqlQueryCount(count_query,query_limit_start,int_limit);
					d = NewDataBaseLayer.getTableDataFromQuery(sql_query,query_limit_start,int_limit,sord,sidx,page,limit,total_row_count);
				}
				if(search.equals("true"))
				{
					String query_edit = "";
					String col_index = req.getParameter("searchField");
					System.out.println("==================col_index========="+col_index);
		
					if(col_index==null)
						col_index = "";
					if(col_index.equals(""))
					{
						String multi_search_param=req.getParameter("filters");
			//multi_search_param = "[" + multi_search_param + "]";
						System.out.println("==================multi_search_param========="+multi_search_param);
						JSONObject Obj=new JSONObject(multi_search_param);
						String searchoperation = Obj.getString("groupOp");
						System.out.println("==================searchoperation========="+searchoperation);
						JSONArray rules =  Obj.getJSONArray("rules");
			/*String rules =  Obj.getString("rules");
						System.out.println("==================rules========="+rules);
						String json_rules = rules.substring(rules.indexOf("[")+1,rules.indexOf("]"));
						json_rules = "{\"rules\":[" + json_rules + "]}";
						System.out.println("================json_rules========="+json_rules);
						JSONObject rules_Obj = new JSONObject(json_rules);
						JSONArray field_array = rules_Obj.getJSONArray("rules"); */
// 			JSONArray field_array = json_rules.getJSONObject("field");
						for(int k=0;k<rules.length();k++)
						{
							System.out.println("==================rules["+k+"]========="+rules.getString(k));
							JSONObject item_Obj=new JSONObject(rules.getString(k));
							String multi_col_index = item_Obj.getString("field");
							System.out.println("multi_col_index==========="+multi_col_index);
							String multi_col_name = NewDataBaseLayer.getColumnNamefromIndex(interface_id,part_id,multi_col_index);
				
							String multi_operation = item_Obj.getString("op");
				
							String multi_search_data = item_Obj.getString("data");
				
				
				
				
				
							String replace_substring = "";
							String replace_substring1 = "";
							if(CheckString("."+multi_col_name,modified_search_query))
							{
								try
								{
			  
									replace_substring1 = modified_search_query.substring(0,modified_search_query.indexOf("."+multi_col_name));
			  
// 						System.out.println("===========replace_substring1 in search==============="+replace_substring1);
								}
								catch(Exception e)
								{
									e.printStackTrace();
								}
							}
			  
			  
// 			System.out.println("================replace_substring1.length() in search============"+replace_substring1.length());
							int check=0;
							if(replace_substring1.length()==0)
							{
								try
								{
					  
									replace_substring = modified_search_query.substring(0,modified_search_query.indexOf(multi_col_name));
// 					System.out.println("===========replace_substring in search==============="+replace_substring);
								}
								catch(Exception e)
								{
									e.printStackTrace();
								}
								replace_substring1=replace_substring;
							}
							for(int m=replace_substring1.length();m>=0;m--)
							{
// 				System.out.println("=========k in search========"+k);
								String currentChar = replace_substring1.substring(m-1, m);
// 				System.out.println("=========currentChar in search========"+currentChar);
								if ((currentChar.equals(",")) || (currentChar.equals("(")) || (currentChar.equals(" ")))
								{
									check = m;
									break;
								}
							}
// 			System.out.println("=========check in search========"+check);
			  
							String pre_string =  replace_substring1.substring(check);
// 			System.out.println("==========pre_string==========="+pre_string);
	
							if(pre_string.equals(""))
							{
				  
							}
							else
							{
								multi_col_name = pre_string+"."+multi_col_name;
							}
				
							System.out.println("==================multi_col_name========="+multi_col_name);
							System.out.println("multi_operation==========="+multi_operation);
							System.out.println("multi_search_data==========="+multi_search_data);
				
							if(multi_operation.equals("cn"))
								query_edit=multi_col_name+" like '%"+multi_search_data+"%'";
							if(multi_operation.equals("bw"))
								query_edit=multi_col_name+" like '"+multi_search_data+"%'";
							if(multi_operation.equals("eq"))
								query_edit=multi_col_name+" ='"+multi_search_data+"'";
							if(multi_operation.equals("ne"))
								query_edit=multi_col_name+" <>'"+multi_search_data+"'";
							if(multi_operation.equals("lt"))
								query_edit=multi_col_name+" <'"+multi_search_data+"'";
							if(multi_operation.equals("gt"))
								query_edit=multi_col_name+" >'"+multi_search_data+"'";
							if(multi_operation.equals("ew"))
								query_edit=multi_col_name+" like '%"+multi_search_data+"'";
				
				
							if(!CheckString("where",modified_search_query))
							{
					//System.out.println("============if==========");
								if((CheckString("group by",modified_search_query)) && (CheckString("order by",modified_search_query)))
								{
									int test2=modified_search_query.length();
									int test3=modified_search_query.indexOf("group by");
									String before_group_by = modified_search_query.substring(0,modified_search_query.indexOf("group by"));
					
									String after_group_by = modified_search_query.substring(test3,test2);
						//System.out.println("=========before_group_by========"+before_group_by);
						//System.out.println("=========after_group_by========"+after_group_by);
									modified_search_query=before_group_by+" where "+query_edit+" "+after_group_by;
								}
								else if((!CheckString("group by",modified_search_query)) && (CheckString("order by",modified_search_query)))
								{
									int test2=modified_search_query.length();
									int test3=modified_search_query.indexOf("order by");
									String before_order_by = modified_search_query.substring(0,modified_search_query.indexOf("order by"));
									String after_order_by = modified_search_query.substring(test3,test2);
						//System.out.println("=========before_order_by========"+before_order_by);
						//System.out.println("=========after_order_by========"+after_order_by);
									modified_search_query=before_order_by+" where "+query_edit+" "+after_order_by;
								}
								else if((CheckString("group by",modified_search_query)) && (!CheckString("order by",modified_search_query)))
								{
									int test2=modified_search_query.length();
									int test3=modified_search_query.indexOf("group by");
									String before_group_by = modified_search_query.substring(0,modified_search_query.indexOf("group by"));
									String after_group_by = modified_search_query.substring(test3,test2);
						//System.out.println("=========before_group_by========"+before_group_by);
						//System.out.println("=========after_group_by========"+after_group_by);
									modified_search_query=before_group_by+" where "+query_edit+" "+after_group_by;
								}
								else
								{
						//System.out.println("==========if==last else==========");
									modified_search_query=modified_search_query+" where "+query_edit;
								}
					//System.out.println("============if========modified_search_query=="+modified_search_query);
				
							}
							else
							{
					//System.out.println("============else==========");
								if((CheckString("group by",modified_search_query)) && (CheckString("order by",modified_search_query)))
								{
									int test2=modified_search_query.length();
									int test3=modified_search_query.indexOf("group by");
						//System.out.println("============111111111==========");
									String before_group_by = modified_search_query.substring(0,modified_search_query.indexOf("group by"));
									String after_group_by = modified_search_query.substring(test3,test2);
						//System.out.println("====else=====before_group_by========"+before_group_by);
						//System.out.println("====else=====after_group_by========"+after_group_by);
									modified_search_query=before_group_by+" and "+query_edit+" "+after_group_by;
								}
								else if((!CheckString("group by",modified_search_query)) && (CheckString("order by",modified_search_query)))
								{
									int test2=modified_search_query.length();
									int test3=modified_search_query.indexOf("order by");
						//System.out.println("============2222222==========");
									String before_order_by = modified_search_query.substring(0,modified_search_query.indexOf("order by"));
									String after_order_by = modified_search_query.substring(test3,test2);
						//System.out.println("====else=====before_order_by========"+before_order_by);
						//System.out.println("====else=====after_order_by========"+after_order_by);
									modified_search_query=before_order_by+" and "+query_edit+" "+after_order_by;
					
								}
								else if((CheckString("group by",modified_search_query)) && (!CheckString("order by",modified_search_query)))
								{
									int test2=modified_search_query.length();
									int test3=modified_search_query.indexOf("group by");
						//System.out.println("============3333333==========");
									String before_group_by = modified_search_query.substring(0,modified_search_query.indexOf("group by"));
									String after_group_by = modified_search_query.substring(test3,test2);
						//System.out.println("====else=====before_group_by========"+before_group_by);
						//System.out.println("====else=====after_group_by========"+after_group_by);
									modified_search_query=before_group_by+" and "+query_edit+" "+after_group_by;
								}
								else
								{
						//System.out.println("========else====last else==========");
									modified_search_query=modified_search_query+" and "+query_edit;
								}
					
					//System.out.println("============else==========modified_search_query===="+modified_search_query);
				
	 			//sql_query=sql_query+" and "+query_edit;
							}
				
				
				
				
				
						}  
					}
					else
					{
						String col_name = NewDataBaseLayer.getColumnNamefromIndex(interface_id,part_id,col_index);
						System.out.println("==================col_name========="+col_name);
						/**************************** For Search with tablename.colname *************/
								String replace_substring = "";
						String replace_substring1 = "";
						if(CheckString("."+col_name,modified_search_query))
						{
							try
							{
				  
								replace_substring1 = modified_search_query.substring(0,modified_search_query.indexOf("."+col_name));
			  
// 					System.out.println("===========replace_substring1 in search==============="+replace_substring1);
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
						}
			  
// 			System.out.println("================replace_substring1.length() in search============"+replace_substring1.length());
						int check=0;
						if(replace_substring1.length()==0)
						{
							try
							{
					  
								replace_substring = modified_search_query.substring(0,modified_search_query.indexOf(col_name));
// 					System.out.println("===========replace_substring in search==============="+replace_substring);
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
							replace_substring1=replace_substring;
						}
						for(int k=replace_substring1.length();k>=0;k--)
						{
// 				System.out.println("=========k in search========"+k);
							String currentChar = replace_substring1.substring(k-1, k);
// 				System.out.println("=========currentChar in search========"+currentChar);
							if ((currentChar.equals(",")) || (currentChar.equals("(")) || (currentChar.equals(" ")))
							{
								check = k;
								break;
							}
						}
// 			System.out.println("=========check in search========"+check);
			  
						String pre_string =  replace_substring1.substring(check);
// 			System.out.println("==========pre_string==========="+pre_string);
	
						if(pre_string.equals(""))
						{
				  
						}
						else
						{
							col_name = pre_string+"."+col_name;
						}
// 			System.out.println("==============col_name======="+col_name);
		
				
						/************************* End of Search with tablename.colname ********************/
		
		
		
								String search_operation = req.getParameter("searchOper");
						if(search_operation.equals("cn"))
							query_edit=col_name+" like '%"+req.getParameter("searchString")+"%'";
						if(search_operation.equals("bw"))
							query_edit=col_name+" like '"+req.getParameter("searchString")+"%'";
						if(search_operation.equals("eq"))
							query_edit=col_name+" ='"+req.getParameter("searchString")+"'";
						if(search_operation.equals("ne"))
							query_edit=col_name+" <>'"+req.getParameter("searchString")+"'";
						if(search_operation.equals("lt"))
							query_edit=col_name+" <'"+req.getParameter("searchString")+"'";
						if(search_operation.equals("gt"))
							query_edit=col_name+" >'"+req.getParameter("searchString")+"'";
						if(search_operation.equals("ew"))
							query_edit=col_name+" like '%"+req.getParameter("searchString")+"'";
	 		
				  
						if(!CheckString("where",modified_search_query))
						{
							System.out.println("============if==========");
							if((CheckString("group by",modified_search_query)) && (CheckString("order by",modified_search_query)))
							{
								int test2=modified_search_query.length();
								int test3=modified_search_query.indexOf("group by");
								String before_group_by = modified_search_query.substring(0,modified_search_query.indexOf("group by"));
					
								String after_group_by = modified_search_query.substring(test3,test2);
								System.out.println("=========before_group_by========"+before_group_by);
								System.out.println("=========after_group_by========"+after_group_by);
								modified_search_query=before_group_by+" where "+query_edit+" "+after_group_by;
							}
							else if((!CheckString("group by",modified_search_query)) && (CheckString("order by",modified_search_query)))
							{
								int test2=modified_search_query.length();
								int test3=modified_search_query.indexOf("order by");
								String before_order_by = modified_search_query.substring(0,modified_search_query.indexOf("order by"));
								String after_order_by = modified_search_query.substring(test3,test2);
								System.out.println("=========before_order_by========"+before_order_by);
								System.out.println("=========after_order_by========"+after_order_by);
								modified_search_query=before_order_by+" where "+query_edit+" "+after_order_by;
							}
							else if((CheckString("group by",modified_search_query)) && (!CheckString("order by",modified_search_query)))
							{
								int test2=modified_search_query.length();
								int test3=modified_search_query.indexOf("group by");
								String before_group_by = modified_search_query.substring(0,modified_search_query.indexOf("group by"));
								String after_group_by = modified_search_query.substring(test3,test2);
								System.out.println("=========before_group_by========"+before_group_by);
								System.out.println("=========after_group_by========"+after_group_by);
								modified_search_query=before_group_by+" where "+query_edit+" "+after_group_by;
							}
							else
							{
								System.out.println("==========if==last else==========");
								modified_search_query=modified_search_query+" where "+query_edit;
							}
							System.out.println("============if========modified_search_query=="+modified_search_query);
				
						}
						else
						{
							System.out.println("============else==========");
							if((CheckString("group by",modified_search_query)) && (CheckString("order by",modified_search_query)))
							{
								int test2=modified_search_query.length();
								int test3=modified_search_query.indexOf("group by");
								System.out.println("============111111111==========");
								String before_group_by = modified_search_query.substring(0,modified_search_query.indexOf("group by"));
								String after_group_by = modified_search_query.substring(test3,test2);
								System.out.println("====else=====before_group_by========"+before_group_by);
								System.out.println("====else=====after_group_by========"+after_group_by);
								modified_search_query=before_group_by+" and "+query_edit+" "+after_group_by;
							}
							else if((!CheckString("group by",modified_search_query)) && (CheckString("order by",modified_search_query)))
							{
								int test2=modified_search_query.length();
								int test3=modified_search_query.indexOf("order by");
								System.out.println("============2222222==========");
								String before_order_by = modified_search_query.substring(0,modified_search_query.indexOf("order by"));
								String after_order_by = modified_search_query.substring(test3,test2);
								System.out.println("====else=====before_order_by========"+before_order_by);
								System.out.println("====else=====after_order_by========"+after_order_by);
								modified_search_query=before_order_by+" and "+query_edit+" "+after_order_by;
					
							}
							else if((CheckString("group by",modified_search_query)) && (!CheckString("order by",modified_search_query)))
							{
								int test2=modified_search_query.length();
								int test3=modified_search_query.indexOf("group by");
								System.out.println("============3333333==========");
								String before_group_by = modified_search_query.substring(0,modified_search_query.indexOf("group by"));
								String after_group_by = modified_search_query.substring(test3,test2);
								System.out.println("====else=====before_group_by========"+before_group_by);
								System.out.println("====else=====after_group_by========"+after_group_by);
								modified_search_query=before_group_by+" and "+query_edit+" "+after_group_by;
							}
							else
							{
								System.out.println("========else====last else==========");
								modified_search_query=modified_search_query+" and "+query_edit;
							}
					
							System.out.println("============else==========modified_search_query===="+modified_search_query);
				
	 			//sql_query=sql_query+" and "+query_edit;
						}
					}
		
		
		

					query_length = modified_search_query.length();
					System.out.println("=============query_length===search====="+query_length);
					sql1 = modified_search_query.substring(0,7);
					System.out.println("================sql1======search======="+sql1);
					sql2 = modified_search_query.substring(7,query_length);
					System.out.println("================sql2=======search======"+sql2);
					count_query = sql1 + " sql_calc_found_rows " + sql2;
					System.out.println("===========count_query=====search======"+count_query);
		
					total_row_count = NewDataBaseLayer.getSqlQueryCount(count_query,query_limit_start,int_limit);
					d = NewDataBaseLayer.getTableDataFromQuerySearch(modified_search_query,query_limit_start,int_limit,sord,sidx,page,limit,total_row_count);
				}
			}
			else
			{
				int count=0;
				Vector  interface_part_vector = new Vector();
				interface_part_vector=(Vector)mysession.getAttribute("INTERFACE_PART_VECTOR");
				for(int i=0;i<interface_part_vector.size();i++)
				{
					String string_to_check = (String)interface_part_vector.elementAt(i);
					if(string_to_check.equals(interface_id+"-"+part_id))
					{
						interface_part_vector.remove(i);
						count=i;
						break;
					}
				
				}
				System.out.println("===================count================"+count);
				Vector  sql_query_vector = new Vector();
				sql_query_vector=(Vector)mysession.getAttribute("SQL_QUERY_VECTOR");
				String reuseSQL = (String)sql_query_vector.elementAt(count);
				total_row_count = NewDataBaseLayer.getSqlQueryCount(reuseSQL,query_limit_start,int_limit);
				d = NewDataBaseLayer.getTableDataFromQuerySearch(reuseSQL,query_limit_start,int_limit,sord,sidx,page,limit,total_row_count);
				sql_query_vector.remove(count);
				mysession.setAttribute("INTERFACE_PART_VECTOR",interface_part_vector);
				mysession.setAttribute("SQL_QUERY_VECTOR",sql_query_vector);
			}		
								
			
		}
	}
	else
	{
		d = page_string;
	}
	
	
	
	
	
// 		System.out.println("==============d======="+d);
		out.println(d);
			

	}
	 
	 
	 
// 	public  String ResultSet2DOM(ResultSet rs,String page,String limit,int total_row_count) throws SQLException {
// 		
// 		String xmldoc = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
// 		//just a hack to make things easier
// 		/*root.setAttribute(
// 			"xmlns:xsi",
// 			"http://www.w3.org/2001/XMLSchema-instance");*/
// 		int page_no=0;
// 		ResultSetMetaData rsmd = rs.getMetaData();
//                 String value;
// //                 log.debug("====================rs========="+rs);
// //                 System.out.println("====================rsmdount column c========="+rsmd.getColumnCount());
//                 String xdoc="";
//                 int k=0;
// 		    
// 		while (rs.isLast() == false) {
// 			rs.next();
// 			int m = k+1;
// 			//xdoc=xdoc+"<row id=\""+m+"\">";
//                          xdoc=xdoc+"<row>";
// 			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
// // 				log.debug("====================rsmd column========="+rsmd.getColumnLabel(i));
// 				value = rs.getString(i);
// 				
// // 				log.debug("===============value============="+value);
// 				
// 				if (value == null) {
// 					value="";
// 				}
// 				xdoc=xdoc+"<cell><![CDATA["+value+"]]></cell>";
// 			}
// 			k=k+1;
// 			
// 			
// 			
// 			xdoc=xdoc+"</row>";
// 		}
// 		
// 		int row_per_page = Integer.parseInt(limit);
// 		
// // 		System.out.println("============row_per_page========="+row_per_page);
// // 		System.out.println("============k========="+k);
// 		if((total_row_count%row_per_page)==0)
// 		page_no=total_row_count/row_per_page;
// 		else
// 		page_no=total_row_count/row_per_page+1;
// 		
// 		xmldoc=xmldoc+"<rows><page>"+page+"</page><total>"+page_no+"</total><records>"+total_row_count+"</records>"+xdoc;
// 		xmldoc=xmldoc+"</rows>";
// 		return xmldoc;
// 	}
	

public  boolean CheckString(String StringtoCheck,String checkstring)
	  {
	  	int index = 0;
	  	boolean flag = false;
		System.out.println("========StringtoCheck========="+StringtoCheck);
		index = checkstring.indexOf(StringtoCheck);
	  	
 	  	System.out.println("==============index==========="+index);
	  	if(index==0 || index<0)
	  		flag=false;
	  	else
	  		flag=true;
	  	
		System.out.println("=============flag==========="+flag);
		
	  	return flag;
	  }
	  	
	  public  Vector StringtoVector(String StringtoConvert)
	  {
		  int index = 0;
		  Vector vv = new Vector();
	  	
		  String remain_string = "";
		  String table_name="";
	  	
		  for(int i=0;i<StringtoConvert.length();i++)
		  {
			  if(StringtoConvert.charAt(i)==',')
			  {
				  index= index+1;
			  }
		  }
	  	
		  if(index == 0)
			  vv.addElement(StringtoConvert);
		  else
		  {
			  for(int j=0;j<index;j++)
			  {
				  table_name = StringtoConvert.substring(0,StringtoConvert.indexOf(","));
				  StringtoConvert = StringtoConvert.substring(StringtoConvert.indexOf(",")+1);
				  vv.addElement(table_name);
			  }
			  vv.addElement(StringtoConvert);
		  }
	  	
// 				System.out.println("==============vv==========="+vv);
	  	
	  	
		  return vv;
	  }
	  
	
public void doPost(HttpServletRequest req,HttpServletResponse res)
		throws ServletException,IOException
{
	doGet(req,res);

}


public boolean checkCachingRequired(String InterfaceID,String part_id)
{
	boolean flag = false;
	String cache_name = "";
	String cacheStatus = ICP.getStatus();
	if(useInterfaceCaching.equals("true") && cacheStatus.equals("STATUS_ALIVE"))
	{
		
		String InterfaceCachingStatus = NewDataBaseLayer.getGridCacheStatus(InterfaceID,part_id);
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
	System.out.println("===============flag======="+flag);
	return flag;
	
	
}

public String getCacheName(String InterfaceID,String part_id)
{
	boolean flag = checkCachingRequired(InterfaceID,part_id);
	System.out.println("==============flag in getCacheName====="+flag);
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
				if(cache_name.equals(""))
				{
					cache_name = default_cache;
				}
				System.out.println("======222====cache_name======="+cache_name);
			}

			CacheName = cache_name;
		}//end of cache_name equal blank if
		else
		{
			CacheName = cache_name;
		}
		
		
		
	}//end of flag true if
	System.out.println("======111====CacheName======="+CacheName);
	return CacheName;
	
	
}


}
