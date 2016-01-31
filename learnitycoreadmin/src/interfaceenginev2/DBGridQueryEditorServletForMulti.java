package interfaceenginev2;

import interfaceenginev2.display.DisplayEngine;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DBGridQueryEditorServletForMulti extends HttpServlet{

//private static final SimpleLogger log = new SimpleLogger(DBGridQueryEditorServlet.class);
      private InterfaceCachePojo ICP=null;
      private String useInterfaceCaching = "";
      private String default_cache = "";

      @Override
	public void init(ServletConfig config) throws ServletException {
	   
	      super.init(config);
	      ServletContext sc = config.getServletContext();
	      ICP=(InterfaceCachePojo)sc.getAttribute("ICP");
	      useInterfaceCaching = (String)sc.getAttribute("useInterfaceCaching");
	      default_cache = (String)sc.getAttribute("DefaultCacheName");
      }

	@Override
	public void doGet(HttpServletRequest req,HttpServletResponse res)
			throws ServletException,IOException{
		ServletOutputStream out=res.getOutputStream();
		res.setContentType("text/html");
	/**
			* @param JDBC
			*            ResultSet to be transformed into a DOM instance
			* @return DOM instance conforming to W3C's Document interface
			* @throws SQLException
	*/
			HttpSession mysession=req.getSession();
	String sql_query = "";
	String validation_sql_query = "";
	String parameter_title = "";
	String validation_parameter_title = "";
	String page = req.getParameter("page"); // get the requested page
	if(page==null)page="1";
	req.getParameter("rows");
	req.getParameter("sidx");
	req.getParameter("sord");
	String interface_id = req.getParameter("interface_id");
	String part_id = req.getParameter("part_id");
	String oper= req.getParameter("oper");
	String  user_id = (String)mysession.getAttribute("user_id");   
	Vector parameter_title_vector = new Vector();
	Vector validation_parameter_title_vector = new Vector();
//	System.out.println("============oper========="+oper);
	if(oper==null)
		oper="";
	  
	String cache_key = "";
//	Object key_cache = cache_key;
	  
	String custom_validation_class = "";
	//parameter_title = de.getParameterTitle(interface_id,part_id);//original
	int no_of_query = 0;
	int no_of_validation_query=0;
	int no_of_custom_validation_query=0;
	String actionSequence="";
	String custom_action_class = "";
	
	if(oper.equals("del"))
	{
		boolean flag=true;
	  	
		no_of_validation_query = NewDataBaseLayer.NoofDeleteValidationQuery(interface_id,part_id);
//		System.out.println("=======================no_of_validation_query===="+no_of_validation_query);
	  		
		for(int i=0;i<no_of_validation_query;i++)
		{
			validation_sql_query = NewDataBaseLayer.getDeleteValidationQuery(interface_id,part_id,i+1);
			validation_parameter_title = NewDataBaseLayer.getDeleteValidationParameter(interface_id,part_id,i+1);
			validation_parameter_title_vector = StringtoVector(validation_parameter_title);
	  			
//			System.out.println("================validation_parameter_title========="+validation_parameter_title);
//			System.out.println("================validation_parameter_title_vector========="+validation_parameter_title_vector);
			String stringreplacewith1="";
			for(int j=0;j<validation_parameter_title_vector.size();j++)
			{
				String stringtoreplace1 = "%"+(String)validation_parameter_title_vector.elementAt(j)+"%";
				if(stringtoreplace1.equals("%current_login_user_id%"))
				{
					stringreplacewith1="'"+(String)mysession.getAttribute("user_id")+"'";
				}
				else
				{
					stringreplacewith1 ="'"+ req.getParameter((String)validation_parameter_title_vector.elementAt(j))+"'";
				}
				validation_sql_query = validation_sql_query.replace(stringtoreplace1,stringreplacewith1);
	  		
			}
//			System.out.println("================validation_sql_query========="+validation_sql_query);
			if(!NewDataBaseLayer.ExecuteValidationSqlQuery(validation_sql_query))
			{
				String error_message = NewDataBaseLayer.getDeleteValidationMessage(interface_id,part_id,i+1);
				out.println(error_message);
				flag=false;
				break;
	  					
			}
			else
			{
				flag=true;
			}
	  			
		}
			
		/******************************* For Custom validation **********************/
			
				no_of_custom_validation_query = NewDataBaseLayer.NoofCustomValidationMethod("Del",interface_id,part_id);
//		System.out.println("=======================no_of_validation_query===="+no_of_custom_validation_query);
			 
		for(int i=0;i<no_of_custom_validation_query;i++)
		{
			custom_validation_class = NewDataBaseLayer.getCustomValidationClass("Del",interface_id,part_id,i+1);
			String custom_flag = "";
			String validation="";
			String message="";
				
			try
			{
				ValidatorFunction v = (ValidatorFunction)(Class.forName(custom_validation_class).newInstance());
				custom_flag=v.validatedelete(req);
//				System.out.println("===============custom_flag in delete========"+custom_flag);
			}
			catch(ClassNotFoundException e)
			{
			}
			catch(InstantiationException e)
			{
			}
			catch(IllegalAccessException e)
			{
			}
				
			validation = custom_flag.substring(0,custom_flag.indexOf(','));
//			System.out.println("===============validation========"+validation);
			message = custom_flag.substring(custom_flag.indexOf(',')+1);
//			System.out.println("===============message========"+message);
			if(validation.equals("false"))
			{
				NewDataBaseLayer.getCustomValidationMessage("Del",interface_id,part_id,i+1);
				out.println(message);
				flag=false;
				break;
	  					
			}
			else
			{
				flag=true;
			}
				
		}
			
			
		/******************************* End of For Custom validation **********************/
			
			
	  		
				if(flag==true)
		{
			
			actionSequence = NewDataBaseLayer.getActionSequence("Del",interface_id,part_id);
			
			if(actionSequence==null)
				actionSequence="";
			
			if(actionSequence.equalsIgnoreCase("before"))
			{
				custom_action_class = NewDataBaseLayer.getCustomActionClass("Del",interface_id,part_id);
				boolean custom_action_flag = true;
				
				try
				{
					CustomEditAction cea = (CustomEditAction)(Class.forName(custom_action_class).newInstance());
					custom_action_flag=cea.DeleteAction(req);
//					System.out.println("===============custom_action_flag in delete========"+custom_action_flag);
				}
				catch(ClassNotFoundException e)
				{
				}
				catch(InstantiationException e)
				{
				}
				catch(IllegalAccessException e)
				{
				}
	  		 
				no_of_query = NewDataBaseLayer.getTotalDeleteQuery(interface_id,part_id);
				for(int j=0;j<no_of_query;j++)
				{
					sql_query = NewDataBaseLayer.getDeleteParam(interface_id,part_id,j+1); 	
					parameter_title = NewDataBaseLayer.getDeleteParameter(interface_id,part_id,j+1);
					parameter_title_vector = StringtoVector(parameter_title);
	  
					String stringreplacewith="";
					for(int i=0;i<parameter_title_vector.size();i++)
					{
						String stringtoreplace = "%"+(String)parameter_title_vector.elementAt(i)+"%";
						if(stringtoreplace.equals("%current_login_user_id%"))
						{
							stringreplacewith="'"+(String)mysession.getAttribute("user_id")+"'";
						}
						else
						{	
							stringreplacewith ="'"+ req.getParameter((String)parameter_title_vector.elementAt(i))+"'";
						}
						sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
	  		
					}
//					System.out.println("===============sql_query=========="+sql_query);
					NewDataBaseLayer.ExecuteSqlQuery(sql_query);
	  	
				}
		
				out.println("Record Deleted");
			}
			
			else if(actionSequence.equalsIgnoreCase("after"))
			{
				custom_action_class = NewDataBaseLayer.getCustomActionClass("Del",interface_id,part_id);
				boolean custom_action_flag = true;
				
				no_of_query = NewDataBaseLayer.getTotalDeleteQuery(interface_id,part_id);
				for(int j=0;j<no_of_query;j++)
				{
					sql_query = NewDataBaseLayer.getDeleteParam(interface_id,part_id,j+1); 	
					parameter_title = NewDataBaseLayer.getDeleteParameter(interface_id,part_id,j+1);
					parameter_title_vector = StringtoVector(parameter_title);
	  
					String stringreplacewith ="";
					for(int i=0;i<parameter_title_vector.size();i++)
					{
						String stringtoreplace = "%"+(String)parameter_title_vector.elementAt(i)+"%";
						if(stringtoreplace.equals("%current_login_user_id%"))
						{
							stringreplacewith="'"+(String)mysession.getAttribute("user_id")+"'";
						}
						else
						{
							stringreplacewith ="'"+ req.getParameter((String)parameter_title_vector.elementAt(i))+"'";
						}
						sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
	  		
					}
//					System.out.println("===============sql_query=========="+sql_query);
					NewDataBaseLayer.ExecuteSqlQuery(sql_query);
	  	
				}
				
				
				try
				{
					CustomEditAction cea = (CustomEditAction)(Class.forName(custom_action_class).newInstance());
					custom_action_flag=cea.DeleteAction(req);
//					System.out.println("===============custom_action_flag in delete========"+custom_action_flag);
				}
				catch(ClassNotFoundException e)
				{
				}
				catch(InstantiationException e)
				{
				}
				catch(IllegalAccessException e)
				{
				}
		
				out.println("Record Deleted");
			}
			
			else if(actionSequence.equalsIgnoreCase("replace"))
			{
				custom_action_class = NewDataBaseLayer.getCustomActionClass("Del",interface_id,part_id);
				boolean custom_action_flag = true;
				
				
				try
				{
					CustomEditAction cea = (CustomEditAction)(Class.forName(custom_action_class).newInstance());
					custom_action_flag=cea.DeleteAction(req);
//					System.out.println("===============custom_action_flag in delete========"+custom_action_flag);
				}
				catch(ClassNotFoundException e)
				{
				}
				catch(InstantiationException e)
				{
				}
				catch(IllegalAccessException e)
				{
				}
		
				out.println("Record Deleted");
			}
			
			else
			{
				
				no_of_query = NewDataBaseLayer.getTotalDeleteQuery(interface_id,part_id);
				for(int j=0;j<no_of_query;j++)
				{
					sql_query = NewDataBaseLayer.getDeleteParam(interface_id,part_id,j+1); 	
					parameter_title = NewDataBaseLayer.getDeleteParameter(interface_id,part_id,j+1);
					parameter_title_vector = StringtoVector(parameter_title);
	  
					String stringreplacewith ="";
					for(int i=0;i<parameter_title_vector.size();i++)
					{
						String stringtoreplace = "%"+(String)parameter_title_vector.elementAt(i)+"%";
						
						if(stringtoreplace.equals("%current_login_user_id%"))
						{
							stringreplacewith="'"+(String)mysession.getAttribute("user_id")+"'";
						}
						else
						{
							stringreplacewith ="'"+ req.getParameter((String)parameter_title_vector.elementAt(i))+"'";
						}
						sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
	  		
					}
//					System.out.println("===============sql_query=========="+sql_query);
					NewDataBaseLayer.ExecuteSqlQuery(sql_query);
	  	
				}
				
				out.println("Record Deleted");
			}
			
			
		}
		
	}
	  
	  
	  
	if(oper.equals("add"))
	{
		  
		  
		boolean flag=true;
	  	
		no_of_validation_query = NewDataBaseLayer.NoofAddValidationQuery(interface_id,part_id);
	  		
		for(int i=0;i<no_of_validation_query;i++)
		{
			validation_sql_query = NewDataBaseLayer.getAddValidationQuery(interface_id,part_id,i+1);
			validation_parameter_title = NewDataBaseLayer.getAddValidationParameter(interface_id,part_id,i+1);
			validation_parameter_title_vector = StringtoVector(validation_parameter_title);
				
			String stringreplacewith1 ="";
			for(int j=0;j<validation_parameter_title_vector.size();j++)
			{
				String stringtoreplace1 = "%"+(String)validation_parameter_title_vector.elementAt(j)+"%";
				if(stringtoreplace1.equals("%current_login_user_id%"))
				{
					stringreplacewith1="'"+(String)mysession.getAttribute("user_id")+"'";
				}
				else
				{
				
					stringreplacewith1 ="'"+ req.getParameter((String)validation_parameter_title_vector.elementAt(j))+"'";
				}
				validation_sql_query = validation_sql_query.replace(stringtoreplace1,stringreplacewith1);
			  					  			  		
			}
			if(!NewDataBaseLayer.ExecuteValidationSqlQuery(validation_sql_query))
			{
				String error_message = NewDataBaseLayer.getAddValidationMessage(interface_id,part_id,i+1);
				out.println(error_message);
				flag=false;
				break;
	  					
			}
			else
			{
				flag=true;
			}
	  			
		}
		  
		  
		/******************************* For Custom validation **********************/
			
				no_of_custom_validation_query = NewDataBaseLayer.NoofCustomValidationMethod("Add",interface_id,part_id);
//		System.out.println("=======================no_of_custom_validation_query===ADD="+no_of_custom_validation_query);
			 
		for(int i=0;i<no_of_custom_validation_query;i++)
		{
			custom_validation_class = NewDataBaseLayer.getCustomValidationClass("Add",interface_id,part_id,i+1);
			String custom_flag = "";
			String validation="";
			String message="";
			  //custom_validation_function = NewDataBaseLayer.getAddCustomValidationFunction(interface_id,part_id,i+1);
			try
			{
//				System.out.println("==============after try===========");
				ValidatorFunction v = (ValidatorFunction)(Class.forName(custom_validation_class).newInstance());
				custom_flag=v.validateadd(req);
//				System.out.println("===============custom_flag in add========"+custom_flag);
			}
			catch(ClassNotFoundException e)
			{
			}
			catch(InstantiationException e)
			{
			}
			catch(IllegalAccessException e)
			{
			}
			  
			validation = custom_flag.substring(0,custom_flag.indexOf(','));
//			System.out.println("===============validation==ADD======"+validation);
			message = custom_flag.substring(custom_flag.indexOf(',')+1);
//			System.out.println("===============message==ADD======"+message);
			if(validation.equals("false"))
			{
				NewDataBaseLayer.getCustomValidationMessage("Add",interface_id,part_id,i+1);
				out.println(message);
				flag=false;
				break;
			}
			else
			{
				flag=true;
			}
			  
		}
			
			
		/******************************* End of For Custom validation **********************/
		  
		  
		  
		  
	  		
				if(flag==true)
		{
			actionSequence = NewDataBaseLayer.getActionSequence("Add",interface_id,part_id);
			
			if(actionSequence==null)
				actionSequence="";
			
			if(actionSequence.equalsIgnoreCase("before"))
			{
				custom_action_class = NewDataBaseLayer.getCustomActionClass("Add",interface_id,part_id);
				boolean custom_action_flag = true;
				
				try
				{
					CustomEditAction cea = (CustomEditAction)(Class.forName(custom_action_class).newInstance());
					custom_action_flag=cea.AddAction(req);
//					System.out.println("===============custom_action_flag in before ADD========"+custom_action_flag);
				}
				catch(ClassNotFoundException e)
				{
				}
				catch(InstantiationException e)
				{
				}
				catch(IllegalAccessException e)
				{
				}
			   
// 				no_of_query = NewDataBaseLayer.getTotalAddQuery(interface_id,part_id);
// 				System.out.println("=================no_of_query=========="+no_of_query);
// 				for(int j=0;j<no_of_query;j++)
// 				{
// 					sql_query = NewDataBaseLayer.getAddSql(interface_id,part_id,j+1); 	
// 					String stringreplacewith="";
// 					System.out.println("================sql_query============"+sql_query);
// 					parameter_title=NewDataBaseLayer.getAddParameter(interface_id,part_id,j+1);
// 					System.out.println("=============parameter_title============"+parameter_title);
// 					parameter_title_vector = StringtoVector(parameter_title);
// 	  
// 	  		
// 					for(int i=0;i<parameter_title_vector.size();i++)
// 					{
// 						String stringtoreplace = "%"+(String)parameter_title_vector.elementAt(i)+"%";
// 						
// 						if(stringtoreplace.equals("%current_login_user_id%"))
// 						{
// 							stringreplacewith="'"+(String)mysession.getAttribute("user_id")+"'";
// 						}
// 						else
// 						{
// 						
// 							String col_type = NewDataBaseLayer.getGridColType(interface_id,part_id,(String)parameter_title_vector.elementAt(i));
// 							if(col_type.equals("date"))
// 							{
// 								if((req.getParameter((String)parameter_title_vector.elementAt(i))).equals(""))
// 								{
// 									stringreplacewith ="NULL";
// 								}
// 								else
// 								{
// 									stringreplacewith ="STR_TO_DATE('"+ req.getParameter((String)parameter_title_vector.elementAt(i))+"','%d-%m-%Y')";
// 								}
// 							}
// 							else
// 							{
// 								stringreplacewith ="'"+ req.getParameter((String)parameter_title_vector.elementAt(i))+"'";
// 							}
// 						}
// 						sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
// 	  		
// 	  		
// 	  		
// 	  		
// 					}
// 					System.out.println("===============sql_query=========="+sql_query);
// 					NewDataBaseLayer.ExecuteSqlQuery(sql_query);
// 	  	
// 				}
// 		
// 				out.println("Record Inserted");
				
				
				no_of_query = NewDataBaseLayer.getTotalAddQuery(interface_id,part_id);
//				System.out.println("=================no_of_query=========="+no_of_query);
				for(int j=0;j<no_of_query;j++)
				{
					sql_query = NewDataBaseLayer.getAddParam(interface_id,part_id,j+1); 	
//					System.out.println("================sql_query============"+sql_query);
					parameter_title=NewDataBaseLayer.getAddParameter(interface_id,part_id,j+1);
//					System.out.println("=============parameter_title============"+parameter_title);
					parameter_title_vector = StringtoVector(parameter_title);
					String stringreplacewith="";
	  		
					Vector sql_query_vector = new Vector();
					sql_query_vector.addElement(sql_query);			
					
					for(int i=0;i<parameter_title_vector.size();i++)
					{
						Vector param_vector = new Vector();
						String stringtoreplace = "%"+(String)parameter_title_vector.elementAt(i)+"%";
//						System.out.println("??????????????????????REPLACE ELSE??????????????????"+stringtoreplace);
						String[] paramValues = req.getParameterValues((String)parameter_title_vector.elementAt(i));
						if(stringtoreplace.equals("%current_login_user_id%"))
						{
							stringreplacewith="'"+(String)mysession.getAttribute("user_id")+"'";
							for(int l=0;l<sql_query_vector.size();l++)
							{
								String temp_sql_query = (String)sql_query_vector.elementAt(l);
//								System.out.println("======================temp_sql_query=============="+temp_sql_query);
								temp_sql_query = temp_sql_query.replace(stringtoreplace,stringreplacewith);
								sql_query_vector.remove(l);
								sql_query_vector.add(l,temp_sql_query);
							}
						}
						else
						{
							if(paramValues==null)
							{
								stringreplacewith="''";
								for(int l=0;l<sql_query_vector.size();l++)
								{
									String temp_sql_query = (String)sql_query_vector.elementAt(l);
//									System.out.println("======================temp_sql_query=============="+temp_sql_query);
									temp_sql_query = temp_sql_query.replace(stringtoreplace,stringreplacewith);
									sql_query_vector.remove(l);
									sql_query_vector.add(l,temp_sql_query);
								}
								
							}	
							else if (paramValues.length == 1) 
							{
								String paramValue = paramValues[0];
								if (paramValue.length() == 0)
									System.out.println("*************No Value**********");
								else
								{
									System.out.println("====================paramValue====11111======="+paramValue);
									if(CheckString(",",paramValue))
									{       //System.out.println("============yes==========");
									int no_of_times = numOfTimes(paramValue,',');
//									System.out.println("============no_of_times=========="+no_of_times);
									for(int z=0;z<no_of_times+1;z++)
									{
										if(z==no_of_times)
										{
										String temp_value = paramValue.substring(0,paramValue.length());
//										System.out.println("==================temp_value========="+temp_value);
										param_vector.addElement(temp_value);
										}	
										else
										{
											
										String temp_value = paramValue.substring(0,paramValue.indexOf(","));
//										System.out.println("==================temp_value========="+temp_value);
										param_vector.addElement(temp_value);
										paramValue=paramValue.substring(paramValue.indexOf(",")+1,paramValue.length());
										}
									} 
									}
									else
									{
//										System.out.println("============no==========");
										param_vector.addElement(paramValue);
									}
//									System.out.println("=======================param_vector==========="+param_vector);
//									System.out.println("==============sql_query_vector.size()=11111===check=="+sql_query_vector.size());
//									System.out.println("=======paramValues.length=========check=1111111======"+param_vector.size());
									if(sql_query_vector.size()<param_vector.size())
									{
								
										String last_query = (String)sql_query_vector.lastElement();
//										System.out.println("==============sql_query_vector.size()====check=="+sql_query_vector.size());
//										System.out.println("=======paramValues.length=========check======="+param_vector.size());
										int extra_vlue_size=param_vector.size()-sql_query_vector.size();
//										System.out.println("==================extra_vlue_size=========="+extra_vlue_size);
										for(int n=0;n<extra_vlue_size;n++)
										{
//										System.out.println("============******************========");
										sql_query_vector.addElement(last_query);
										}
//										System.out.println("==============sql_query_vector.size()====check=111111="+sql_query_vector.size());
									}
// 									String col_type = NewDataBaseLayer.getGridColType(interface_id,part_id,(String)parameter_title_vector.elementAt(i));
// 									if(col_type.equals("date"))
// 									{
// 										if((req.getParameter((String)parameter_title_vector.elementAt(i))).equals(""))
// 										{
// 											stringreplacewith ="NULL";
// 										}
// 										else
// 										{
// 											stringreplacewith ="STR_TO_DATE('"+paramValue+"','%d-%m-%Y')";
// 										}
// 									}
// 									else
// 									{
// 										stringreplacewith ="'"+paramValue+"'";
// 									}
									
									for(int k=0; k<param_vector.size(); k++) 
									{
								
//										System.out.println("**********paramValues[k]*********222222222*****" + param_vector.elementAt(k));
//										System.out.println("================stringtoreplace========"+stringtoreplace);
										stringreplacewith ="'"+(String)param_vector.elementAt(k)+"'";
//										System.out.println("================stringreplacewith========"+stringreplacewith);
// 										sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
										
									
// 										String temp_sql_query = (String)sql_query_vector.elementAt(k);
// 										System.out.println("==================temp_sql_query==========="+temp_sql_query);
// 										temp_sql_query = temp_sql_query.replace(stringtoreplace,stringreplacewith);
// 										sql_query_vector.remove(k);
// 										sql_query_vector.add(k,temp_sql_query);
										
//										System.out.println("=================sql_query_vector.size()=========="+sql_query_vector.size());
// 										for(int l=0;l<sql_query_vector.size();l++)
// 										{
										String temp_sql_query = (String)sql_query_vector.elementAt(k);
//										System.out.println("======================temp_sql_query=============="+temp_sql_query);
										temp_sql_query = temp_sql_query.replace(stringtoreplace,stringreplacewith);
										sql_query_vector.remove(k);
										sql_query_vector.add(k,temp_sql_query);
// 										}
									}
									
								}
															
							}
							else
							{
//								System.out.println("==============many========else======");
							
								if(sql_query_vector.size()<paramValues.length)
								{
								
									String last_query = (String)sql_query_vector.lastElement();
//									System.out.println("==============sql_query_vector.size()====check=="+sql_query_vector.size());
//									System.out.println("=======paramValues.length=========check======="+paramValues.length);
									int extra_vlue_size=paramValues.length-sql_query_vector.size();
//									System.out.println("==================extra_vlue_size=========="+extra_vlue_size);
									for(int n=0;n<extra_vlue_size;n++)
									{
//										System.out.println("============******************========");
										sql_query_vector.addElement(last_query);
									}
//									System.out.println("==============sql_query_vector.size()====check=111111="+sql_query_vector.size());
								}	
								for(int k=0; k<paramValues.length; k++) 
								{
								
//									System.out.println("**********paramValues[k]*********222222222*****" + paramValues[k]);
									
									stringreplacewith ="'"+paramValues[k]+"'";
//									System.out.println("================stringreplacewith========"+stringreplacewith);
// 									sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
									
									String temp_sql_query = (String)sql_query_vector.elementAt(k);
//									System.out.println("==================temp_sql_query==========="+temp_sql_query);
									temp_sql_query = temp_sql_query.replace(stringtoreplace,stringreplacewith);
									sql_query_vector.remove(k);
									sql_query_vector.add(k,temp_sql_query);
								}
							}
						}
					}
//					System.out.println("sql_query_vector.size()========all======"+sql_query_vector.size());
					for(int m=0;m<sql_query_vector.size();m++)
					{
						String query_to_execute = (String)sql_query_vector.elementAt(m);
//						System.out.println("===============sql_query=========="+query_to_execute);
						NewDataBaseLayer.ExecuteSqlQuery(query_to_execute);
					}
				
				}
				out.println("Record Inserted");
				
			}
			
			else if(actionSequence.equalsIgnoreCase("after"))
			{
				custom_action_class = NewDataBaseLayer.getCustomActionClass("Add",interface_id,part_id);
				boolean custom_action_flag = true;
				
// 				no_of_query = NewDataBaseLayer.getTotalAddQuery(interface_id,part_id);
// 				System.out.println("=================no_of_query=========="+no_of_query);
// 				String stringreplacewith="";
// 				for(int j=0;j<no_of_query;j++)
// 				{
// 					sql_query = NewDataBaseLayer.getAddParam(interface_id,part_id,j+1); 	
// 					System.out.println("================sql_query============"+sql_query);
// 					parameter_title=NewDataBaseLayer.getAddParameter(interface_id,part_id,j+1);
// 					System.out.println("=============parameter_title============"+parameter_title);
// 					parameter_title_vector = StringtoVector(parameter_title);
// 	  
// 	  		
// 					for(int i=0;i<parameter_title_vector.size();i++)
// 					{
// 						String stringtoreplace = "%"+(String)parameter_title_vector.elementAt(i)+"%";
// 						System.out.println("??????????????????????after??????????????????"+stringtoreplace);
// 
// 						if(stringtoreplace.equals("%current_login_user_id%"))
// 						{
// 							stringreplacewith="'"+(String)mysession.getAttribute("user_id")+"'";
// 						}
// 						else
// 						{
// 							String col_type = NewDataBaseLayer.getGridColType(interface_id,part_id,(String)parameter_title_vector.elementAt(i));
// 							if(col_type.equals("date"))
// 							{
// 								if((req.getParameter((String)parameter_title_vector.elementAt(i))).equals(""))
// 								{
// 									stringreplacewith ="NULL";
// 								}
// 								else
// 								{
// 									stringreplacewith ="STR_TO_DATE('"+ req.getParameter((String)parameter_title_vector.elementAt(i))+"','%d-%m-%Y')";
// 								}
// 							}
// 							else
// 							{
// 								stringreplacewith ="'"+ req.getParameter((String)parameter_title_vector.elementAt(i))+"'";
// 							}
// 						}
// 	  		
// 						sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
// 	  		
// 	  		
// 	  		
// 	  		
// 					}
// 					System.out.println("===============sql_query=========="+sql_query);
// 					NewDataBaseLayer.ExecuteSqlQuery(sql_query);
// 	  	
// 				}
				
				
				
				no_of_query = NewDataBaseLayer.getTotalAddQuery(interface_id,part_id);
//				System.out.println("=================no_of_query=========="+no_of_query);
				for(int j=0;j<no_of_query;j++)
				{
					sql_query = NewDataBaseLayer.getAddParam(interface_id,part_id,j+1); 	
//					System.out.println("================sql_query============"+sql_query);
					parameter_title=NewDataBaseLayer.getAddParameter(interface_id,part_id,j+1);
//					System.out.println("=============parameter_title============"+parameter_title);
					parameter_title_vector = StringtoVector(parameter_title);
					String stringreplacewith="";
	  		
					Vector sql_query_vector = new Vector();
					sql_query_vector.addElement(sql_query);			
					
					for(int i=0;i<parameter_title_vector.size();i++)
					{
						Vector param_vector = new Vector();
						String stringtoreplace = "%"+(String)parameter_title_vector.elementAt(i)+"%";
//						System.out.println("??????????????????????REPLACE ELSE??????????????????"+stringtoreplace);
						String[] paramValues = req.getParameterValues((String)parameter_title_vector.elementAt(i));
						if(stringtoreplace.equals("%current_login_user_id%"))
						{
							stringreplacewith="'"+(String)mysession.getAttribute("user_id")+"'";
							for(int l=0;l<sql_query_vector.size();l++)
							{
								String temp_sql_query = (String)sql_query_vector.elementAt(l);
//								System.out.println("======================temp_sql_query=============="+temp_sql_query);
								temp_sql_query = temp_sql_query.replace(stringtoreplace,stringreplacewith);
								sql_query_vector.remove(l);
								sql_query_vector.add(l,temp_sql_query);
							}
						}
						else
						{
							if(paramValues==null)
							{
								stringreplacewith="''";
								for(int l=0;l<sql_query_vector.size();l++)
								{
									String temp_sql_query = (String)sql_query_vector.elementAt(l);
//									System.out.println("======================temp_sql_query=============="+temp_sql_query);
									temp_sql_query = temp_sql_query.replace(stringtoreplace,stringreplacewith);
									sql_query_vector.remove(l);
									sql_query_vector.add(l,temp_sql_query);
								}
								
							}	
							else if (paramValues.length == 1) 
							{
								String paramValue = paramValues[0];
								if (paramValue.length() == 0)
									System.out.println("*************No Value**********");
								else
								{
									System.out.println("====================paramValue====11111======="+paramValue);
									if(CheckString(",",paramValue))
									{     //  System.out.println("============yes==========");
									int no_of_times = numOfTimes(paramValue,',');
//									System.out.println("============no_of_times=========="+no_of_times);
									for(int z=0;z<no_of_times+1;z++)
									{
										if(z==no_of_times)
										{
										String temp_value = paramValue.substring(0,paramValue.length());
//										System.out.println("==================temp_value========="+temp_value);
										param_vector.addElement(temp_value);
										}	
										else
										{
											
										String temp_value = paramValue.substring(0,paramValue.indexOf(","));
//										System.out.println("==================temp_value========="+temp_value);
										param_vector.addElement(temp_value);
										paramValue=paramValue.substring(paramValue.indexOf(",")+1,paramValue.length());
										}
									} 
									}
									else
									{
//										System.out.println("============no==========");
										param_vector.addElement(paramValue);
									}
//									System.out.println("=======================param_vector==========="+param_vector);
//									System.out.println("==============sql_query_vector.size()=11111===check=="+sql_query_vector.size());
//									System.out.println("=======paramValues.length=========check=1111111======"+param_vector.size());
									if(sql_query_vector.size()<param_vector.size())
									{
								
										String last_query = (String)sql_query_vector.lastElement();
//										System.out.println("==============sql_query_vector.size()====check=="+sql_query_vector.size());
//										System.out.println("=======paramValues.length=========check======="+param_vector.size());
										int extra_vlue_size=param_vector.size()-sql_query_vector.size();
//										System.out.println("==================extra_vlue_size=========="+extra_vlue_size);
										for(int n=0;n<extra_vlue_size;n++)
										{
//										System.out.println("============******************========");
										sql_query_vector.addElement(last_query);
										}
//										System.out.println("==============sql_query_vector.size()====check=111111="+sql_query_vector.size());
									}
// 									String col_type = NewDataBaseLayer.getGridColType(interface_id,part_id,(String)parameter_title_vector.elementAt(i));
// 									if(col_type.equals("date"))
// 									{
// 										if((req.getParameter((String)parameter_title_vector.elementAt(i))).equals(""))
// 										{
// 											stringreplacewith ="NULL";
// 										}
// 										else
// 										{
// 											stringreplacewith ="STR_TO_DATE('"+paramValue+"','%d-%m-%Y')";
// 										}
// 									}
// 									else
// 									{
// 										stringreplacewith ="'"+paramValue+"'";
// 									}
									
									for(int k=0; k<param_vector.size(); k++) 
									{
								
//										System.out.println("**********paramValues[k]*********222222222*****" + param_vector.elementAt(k));
										
//										System.out.println("================stringtoreplace========"+stringtoreplace);
									
										stringreplacewith ="'"+(String)param_vector.elementAt(k)+"'";
//										System.out.println("================stringreplacewith========"+stringreplacewith);
// 										sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
										
// 										String temp_sql_query = (String)sql_query_vector.elementAt(k);
// 										System.out.println("==================temp_sql_query==========="+temp_sql_query);
// 										temp_sql_query = temp_sql_query.replace(stringtoreplace,stringreplacewith);
// 										sql_query_vector.remove(k);
// 										sql_query_vector.add(k,temp_sql_query);
										
//										System.out.println("=================sql_query_vector.size()=========="+sql_query_vector.size());
// 										for(int l=0;l<sql_query_vector.size();l++)
// 										{
										String temp_sql_query = (String)sql_query_vector.elementAt(k);
//										System.out.println("======================temp_sql_query=============="+temp_sql_query);
										temp_sql_query = temp_sql_query.replace(stringtoreplace,stringreplacewith);
										sql_query_vector.remove(k);
										sql_query_vector.add(k,temp_sql_query);
// 										}
									}
									
								}
								
							}
							else
							{
//								System.out.println("==============many========else======");
							
								if(sql_query_vector.size()<paramValues.length)
								{
								
									String last_query = (String)sql_query_vector.lastElement();
//									System.out.println("==============sql_query_vector.size()====check=="+sql_query_vector.size());
//									System.out.println("=======paramValues.length=========check======="+paramValues.length);
									int extra_vlue_size=paramValues.length-sql_query_vector.size();
//									System.out.println("==================extra_vlue_size=========="+extra_vlue_size);
									for(int n=0;n<extra_vlue_size;n++)
									{
//										System.out.println("============******************========");
										sql_query_vector.addElement(last_query);
									}
//									System.out.println("==============sql_query_vector.size()====check=111111="+sql_query_vector.size());
								}	
								for(int k=0; k<paramValues.length; k++) 
								{
								
//									System.out.println("**********paramValues[k]*********222222222*****" + paramValues[k]);
									
//									System.out.println("================stringtoreplace========"+stringtoreplace);
									stringreplacewith ="'"+paramValues[k]+"'";
//									System.out.println("================stringreplacewith========"+stringreplacewith);
// 									sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
									
									String temp_sql_query = (String)sql_query_vector.elementAt(k);
//									System.out.println("==================temp_sql_query==========="+temp_sql_query);
									temp_sql_query = temp_sql_query.replace(stringtoreplace,stringreplacewith);
									sql_query_vector.remove(k);
									sql_query_vector.add(k,temp_sql_query);
								}
							}
						}
					}
//					System.out.println("sql_query_vector.size()========all======"+sql_query_vector.size());
					for(int m=0;m<sql_query_vector.size();m++)
					{
						String query_to_execute = (String)sql_query_vector.elementAt(m);
//						System.out.println("===============sql_query=========="+query_to_execute);
						NewDataBaseLayer.ExecuteSqlQuery(query_to_execute);
					}
				
				}
				
				try
				{
					CustomEditAction cea = (CustomEditAction)(Class.forName(custom_action_class).newInstance());
					custom_action_flag=cea.AddAction(req);
//					System.out.println("===============custom_action_flag in after ADD========"+custom_action_flag);
				}
				catch(ClassNotFoundException e)
				{
				}
				catch(InstantiationException e)
				{
				}
				catch(IllegalAccessException e)
				{
				}
				out.println("Record Inserted");
			}
			
			else if(actionSequence.equalsIgnoreCase("replace"))
			{
				custom_action_class = NewDataBaseLayer.getCustomActionClass("Add",interface_id,part_id);
				boolean custom_action_flag = true;
				
				try
				{
					CustomEditAction cea = (CustomEditAction)(Class.forName(custom_action_class).newInstance());
					custom_action_flag=cea.AddAction(req);
//					System.out.println("===============custom_action_flag in replace ADD========"+custom_action_flag);
				}
				catch(ClassNotFoundException e)
				{
				}
				catch(InstantiationException e)
				{
				}
				catch(IllegalAccessException e)
				{
				}
		
				out.println("Record Inserted");
			}
			
			else
			{
				
				no_of_query = NewDataBaseLayer.getTotalAddQuery(interface_id,part_id);
//				System.out.println("=================no_of_query=========="+no_of_query);
				for(int j=0;j<no_of_query;j++)
				{
					sql_query = NewDataBaseLayer.getAddParam(interface_id,part_id,j+1); 	
//					System.out.println("================sql_query============"+sql_query);
					parameter_title=NewDataBaseLayer.getAddParameter(interface_id,part_id,j+1);
//					System.out.println("=============parameter_title============"+parameter_title);
					parameter_title_vector = StringtoVector(parameter_title);
					String stringreplacewith="";
	  		
					Vector sql_query_vector = new Vector();
					sql_query_vector.addElement(sql_query);			
					
					for(int i=0;i<parameter_title_vector.size();i++)
					{
						Vector param_vector = new Vector();
						String stringtoreplace = "%"+(String)parameter_title_vector.elementAt(i)+"%";
//						System.out.println("??????????????????????REPLACE ELSE??????????????????"+stringtoreplace);
						String[] paramValues = req.getParameterValues((String)parameter_title_vector.elementAt(i));
						if(stringtoreplace.equals("%current_login_user_id%"))
						{
							stringreplacewith="'"+(String)mysession.getAttribute("user_id")+"'";
							for(int l=0;l<sql_query_vector.size();l++)
							{
								String temp_sql_query = (String)sql_query_vector.elementAt(l);
//								System.out.println("======================temp_sql_query=============="+temp_sql_query);
								temp_sql_query = temp_sql_query.replace(stringtoreplace,stringreplacewith);
								sql_query_vector.remove(l);
								sql_query_vector.add(l,temp_sql_query);
							}
						}
						else
						{
							if(paramValues==null)
							{
								stringreplacewith="''";
								for(int l=0;l<sql_query_vector.size();l++)
								{
									String temp_sql_query = (String)sql_query_vector.elementAt(l);
//									System.out.println("======================temp_sql_query=============="+temp_sql_query);
									temp_sql_query = temp_sql_query.replace(stringtoreplace,stringreplacewith);
									sql_query_vector.remove(l);
									sql_query_vector.add(l,temp_sql_query);
								}
								
							}	
							else if (paramValues.length == 1) 
							{
								String paramValue = paramValues[0];
								if (paramValue.length() == 0)
									System.out.println("*************No Value**********");
								else
								{
									System.out.println("====================paramValue====11111======="+paramValue);
									if(CheckString(",",paramValue))
									{       System.out.println("============yes==========");
										int no_of_times = numOfTimes(paramValue,',');
//										System.out.println("============no_of_times=========="+no_of_times);
										for(int z=0;z<no_of_times+1;z++)
										{
											if(z==no_of_times)
											{
												String temp_value = paramValue.substring(0,paramValue.length());
//												System.out.println("==================temp_value========="+temp_value);
												param_vector.addElement(temp_value);
											}	
											else
											{
											
												String temp_value = paramValue.substring(0,paramValue.indexOf(","));
//												System.out.println("==================temp_value========="+temp_value);
												param_vector.addElement(temp_value);
												paramValue=paramValue.substring(paramValue.indexOf(",")+1,paramValue.length());
											}
										} 
									}
									else
									{
//										System.out.println("============no==========");
										param_vector.addElement(paramValue);
									}
//									System.out.println("=======================param_vector==========="+param_vector);
//									System.out.println("==============sql_query_vector.size()=11111===check=="+sql_query_vector.size());
//									System.out.println("=======paramValues.length=========check=1111111======"+param_vector.size());
									if(sql_query_vector.size()<param_vector.size())
									{
								
										String last_query = (String)sql_query_vector.lastElement();
//										System.out.println("==============sql_query_vector.size()====check=="+sql_query_vector.size());
//										System.out.println("=======paramValues.length=========check======="+param_vector.size());
										int extra_vlue_size=param_vector.size()-sql_query_vector.size();
//										System.out.println("==================extra_vlue_size=========="+extra_vlue_size);
										for(int n=0;n<extra_vlue_size;n++)
										{
//											System.out.println("============******************========");
											sql_query_vector.addElement(last_query);
										}
//										System.out.println("==============sql_query_vector.size()====check=111111="+sql_query_vector.size());
									}
// 									String col_type = NewDataBaseLayer.getGridColType(interface_id,part_id,(String)parameter_title_vector.elementAt(i));
// 									if(col_type.equals("date"))
// 									{
// 										if((req.getParameter((String)parameter_title_vector.elementAt(i))).equals(""))
// 										{
// 											stringreplacewith ="NULL";
// 										}
// 										else
// 										{
// 											stringreplacewith ="STR_TO_DATE('"+paramValue+"','%d-%m-%Y')";
// 										}
// 									}
// 									else
// 									{
// 										stringreplacewith ="'"+paramValue+"'";
// 									}
									
									for(int k=0; k<param_vector.size(); k++) 
									{
								
//										System.out.println("**********paramValues[k]*********222222222*****" + param_vector.elementAt(k));
//										System.out.println("================stringtoreplace========"+stringtoreplace);
										stringreplacewith ="'"+(String)param_vector.elementAt(k)+"'";
//										System.out.println("================stringreplacewith========"+stringreplacewith);
// 										sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
									
// 										String temp_sql_query = (String)sql_query_vector.elementAt(k);
// 										System.out.println("==================temp_sql_query==========="+temp_sql_query);
// 										temp_sql_query = temp_sql_query.replace(stringtoreplace,stringreplacewith);
// 										sql_query_vector.remove(k);
// 										sql_query_vector.add(k,temp_sql_query);
										
//										System.out.println("=================sql_query_vector.size()=========="+sql_query_vector.size());
// 										for(int l=0;l<sql_query_vector.size();l++)
// 										{
											String temp_sql_query = (String)sql_query_vector.elementAt(k);
//											System.out.println("======================temp_sql_query=============="+temp_sql_query);
											temp_sql_query = temp_sql_query.replace(stringtoreplace,stringreplacewith);
											sql_query_vector.remove(k);
											sql_query_vector.add(k,temp_sql_query);
// 										}
									}
									
								}
							
							}
							else
							{
//								System.out.println("==============many========else======");
							
								if(sql_query_vector.size()<paramValues.length)
								{
								
									String last_query = (String)sql_query_vector.lastElement();
//									System.out.println("==============sql_query_vector.size()====check=="+sql_query_vector.size());
//									System.out.println("=======paramValues.length=========check======="+paramValues.length);
									int extra_vlue_size=paramValues.length-sql_query_vector.size();
//									System.out.println("==================extra_vlue_size=========="+extra_vlue_size);
									for(int n=0;n<extra_vlue_size;n++)
									{
//										System.out.println("============******************========");
										sql_query_vector.addElement(last_query);
									}
//									System.out.println("==============sql_query_vector.size()====check=111111="+sql_query_vector.size());
								}	
								for(int k=0; k<paramValues.length; k++) 
								{
								
//									System.out.println("**********paramValues[k]*********222222222*****" + paramValues[k]);
//									System.out.println("================stringtoreplace========"+stringtoreplace);
									stringreplacewith ="'"+paramValues[k]+"'";
//									System.out.println("================stringreplacewith========"+stringreplacewith);
// 									sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
							        	
									String temp_sql_query = (String)sql_query_vector.elementAt(k);
//									System.out.println("==================temp_sql_query==========="+temp_sql_query);
									temp_sql_query = temp_sql_query.replace(stringtoreplace,stringreplacewith);
									sql_query_vector.remove(k);
									sql_query_vector.add(k,temp_sql_query);
								}
							}
						}
					}
//					System.out.println("sql_query_vector.size()========all======"+sql_query_vector.size());
					for(int m=0;m<sql_query_vector.size();m++)
					{
						String query_to_execute = (String)sql_query_vector.elementAt(m);
//						System.out.println("===============sql_query=========="+query_to_execute);
						NewDataBaseLayer.ExecuteSqlQuery(query_to_execute);
					}
				
				}
				out.println("Record Inserted");
			}
			
		}
				
	}

	if(oper.equals("edit"))
	{
		  
		boolean flag=true;
	  	
		no_of_validation_query = NewDataBaseLayer.NoofModifyValidationQuery(interface_id,part_id);
	  		
		for(int i=0;i<no_of_validation_query;i++)
		{
			validation_sql_query = NewDataBaseLayer.getModifyValidationQuery(interface_id,part_id,i+1);
			validation_parameter_title = NewDataBaseLayer.getModifyValidationParameter(interface_id,part_id,i+1);
			validation_parameter_title_vector = StringtoVector(validation_parameter_title);
			
			String stringreplacewith1 ="";	
			for(int j=0;j<validation_parameter_title_vector.size();j++)
			{
				String stringtoreplace1 = "%"+(String)validation_parameter_title_vector.elementAt(j)+"%";
				if(stringtoreplace1.equals("%current_login_user_id%"))
				{
					stringreplacewith1="'"+(String)mysession.getAttribute("user_id")+"'";
				}
				else
				{
					stringreplacewith1 ="'"+ req.getParameter((String)validation_parameter_title_vector.elementAt(j))+"'";
				}
				validation_sql_query = validation_sql_query.replace(stringtoreplace1,stringreplacewith1);
	  		}

			if(!NewDataBaseLayer.ExecuteValidationSqlQuery(validation_sql_query))
			{
				String error_message = NewDataBaseLayer.getModifyValidationMessage(interface_id,part_id,i+1);
				out.println(error_message);
				flag=false;
				break;
	  					
			}
			else
			{
				flag=true;
			}
	  			
		}
		  
		  
		/******************************* For Custom validation **********************/
			
		no_of_custom_validation_query = NewDataBaseLayer.NoofCustomValidationMethod("Edit",interface_id,part_id);
//		System.out.println("=======================no_of_validation_query===="+no_of_custom_validation_query);
			 
		for(int i=0;i<no_of_custom_validation_query;i++)
		{
			custom_validation_class = NewDataBaseLayer.getCustomValidationClass("Edit",interface_id,part_id,i+1);
			String custom_flag = "";
			String validation="";
			String message="";
			  //custom_validation_function = NewDataBaseLayer.getModifyCustomValidationFunction(interface_id,part_id,i+1);
			try
			{
				ValidatorFunction v = (ValidatorFunction)(Class.forName(custom_validation_class).newInstance());
				custom_flag=v.validateedit(req);
//				System.out.println("===============custom_flag in edit========"+custom_flag);
			}
			catch(ClassNotFoundException e)
			{
			}
			catch(InstantiationException e)
			{
			}
			catch(IllegalAccessException e)
			{
			}
			validation = custom_flag.substring(0,custom_flag.indexOf(','));
//			System.out.println("===============validation========"+validation);
			message = custom_flag.substring(custom_flag.indexOf(',')+1);
//			System.out.println("===============message========"+message);
			if(validation.equals("false"))
			{
				NewDataBaseLayer.getCustomValidationMessage("Edit",interface_id,part_id,i+1);
				out.println(message);
				flag=false;
				break;
			}
			else
			{
				flag=true;
			}
			  
		}
			
			
		/******************************* End of For Custom validation **********************/
		  
		  
	  		
		if(flag==true)
		{
			actionSequence = NewDataBaseLayer.getActionSequence("Edit",interface_id,part_id);
			
			if(actionSequence==null)
				actionSequence="";
			
			if(actionSequence.equalsIgnoreCase("before"))
			{
				custom_action_class = NewDataBaseLayer.getCustomActionClass("Edit",interface_id,part_id);
				boolean custom_action_flag = true;
				
				try
				{
					CustomEditAction cea = (CustomEditAction)(Class.forName(custom_action_class).newInstance());
					custom_action_flag=cea.EditAction(req);
//					System.out.println("===============custom_action_flag in delete========"+custom_action_flag);
				}
				catch(ClassNotFoundException e)
				{
				}
				catch(InstantiationException e)
				{
				}
				catch(IllegalAccessException e)
				{
				}		  
		  
		  
				no_of_query = NewDataBaseLayer.getTotalModifyQuery(interface_id,part_id);
				for(int j=0;j<no_of_query;j++)
				{
					sql_query = NewDataBaseLayer.getModifyParam(interface_id,part_id,j+1); 	
					parameter_title=NewDataBaseLayer.getModifyParameter(interface_id,part_id,j+1);
					parameter_title_vector = StringtoVector(parameter_title);
					String stringreplacewith="";
	  		
					for(int i=0;i<parameter_title_vector.size();i++)
					{
						String stringtoreplace = "%"+(String)parameter_title_vector.elementAt(i)+"%";
						if(stringtoreplace.equals("%current_login_user_id%"))
						{
							stringreplacewith="'"+(String)mysession.getAttribute("user_id")+"'";
						}
						else
						{
							stringreplacewith ="'"+ req.getParameter((String)parameter_title_vector.elementAt(i))+"'";
						}
						sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
	  		
					}
//					System.out.println("===============sql_query=========="+sql_query);
					NewDataBaseLayer.ExecuteSqlQuery(sql_query);
	  	
				}
				out.println("Record Updated");
			}
			
			else if(actionSequence.equalsIgnoreCase("after"))
			{
				custom_action_class = NewDataBaseLayer.getCustomActionClass("Edit",interface_id,part_id);
				boolean custom_action_flag = true;
				
				no_of_query = NewDataBaseLayer.getTotalModifyQuery(interface_id,part_id);
				for(int j=0;j<no_of_query;j++)
				{
					sql_query = NewDataBaseLayer.getModifyParam(interface_id,part_id,j+1); 	
					parameter_title=NewDataBaseLayer.getModifyParameter(interface_id,part_id,j+1);
					parameter_title_vector = StringtoVector(parameter_title);
					String stringreplacewith ="";
	  		
					for(int i=0;i<parameter_title_vector.size();i++)
					{
						String stringtoreplace = "%"+(String)parameter_title_vector.elementAt(i)+"%";
						if(stringtoreplace.equals("%current_login_user_id%"))
						{
							stringreplacewith="'"+(String)mysession.getAttribute("user_id")+"'";
						}
						else
						{
							stringreplacewith ="'"+ req.getParameter((String)parameter_title_vector.elementAt(i))+"'";
						}
						sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
	  		
					}
//					System.out.println("===============sql_query=========="+sql_query);
					NewDataBaseLayer.ExecuteSqlQuery(sql_query);
	  	
				}
				
				
				try
				{
					CustomEditAction cea = (CustomEditAction)(Class.forName(custom_action_class).newInstance());
					custom_action_flag=cea.EditAction(req);
//					System.out.println("===============custom_action_flag in delete========"+custom_action_flag);
				}
				catch(ClassNotFoundException e)
				{
				}
				catch(InstantiationException e)
				{
				}
				catch(IllegalAccessException e)
				{
				}		  
		  
		  
				
				out.println("Record Updated");
			}
			
			else if(actionSequence.equalsIgnoreCase("replace"))
			{
				custom_action_class = NewDataBaseLayer.getCustomActionClass("Edit",interface_id,part_id);
				boolean custom_action_flag = true;
				
				try
				{
					CustomEditAction cea = (CustomEditAction)(Class.forName(custom_action_class).newInstance());
					custom_action_flag=cea.EditAction(req);
//					System.out.println("===============custom_action_flag in delete========"+custom_action_flag);
				}
				catch(ClassNotFoundException e)
				{
				}
				catch(InstantiationException e)
				{
				}
				catch(IllegalAccessException e)
				{
				}		  
				
				out.println("Record Updated");
			}
			
			else
			{
				
				no_of_query = NewDataBaseLayer.getTotalModifyQuery(interface_id,part_id);
				for(int j=0;j<no_of_query;j++)
				{
					sql_query = NewDataBaseLayer.getModifyParam(interface_id,part_id,j+1); 	
					parameter_title=NewDataBaseLayer.getModifyParameter(interface_id,part_id,j+1);
					parameter_title_vector = StringtoVector(parameter_title);
	  
					String stringreplacewith ="";
					for(int i=0;i<parameter_title_vector.size();i++)
					{
						String stringtoreplace = "%"+(String)parameter_title_vector.elementAt(i)+"%";
						if(stringtoreplace.equals("%current_login_user_id%"))
						{
							stringreplacewith="'"+(String)mysession.getAttribute("user_id")+"'";
						}
						else
						{
							stringreplacewith ="'"+ req.getParameter((String)parameter_title_vector.elementAt(i))+"'";
						}
						sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
	  		
					}
//					System.out.println("===============sql_query=========="+sql_query);
					NewDataBaseLayer.ExecuteSqlQuery(sql_query);
	  	
				}
				
				out.println("Record Updated");
			}
				  
		}
  }
//	boolean caching_status = checkCachingRequired(interface_id,part_id);

	  boolean caching_status = isCacheRunning(); 

//		if ((true == cacheRunning) && (true == ICP.checkGridCachingRequired(interface_id, part_id)))
//per interface cache enablement checking is too expensive performance wise					

if ((true == caching_status)) 
{
	  String[] namepairvalue = (String[])mysession.getAttribute("namepairvalue");

	String user_id_included_cache = NewDataBaseLayer.getGridCacheIncludeUserID(interface_id,part_id);
//	System.out.println("user_id_included_cache============"+user_id_included_cache);
	if(user_id_included_cache.equalsIgnoreCase("true"))
		cache_key = user_id+interface_id+part_id+"_p_"+page;
	else
		cache_key = interface_id+part_id+"_p_"+page;

	if (namepairvalue != null)
	{
		for(int i=0;i<namepairvalue.length;i=i+2)
		{
			cache_key = cache_key.concat(namepairvalue[i]+namepairvalue[i+1]);
		}
	}

//	String cache_name = getCacheName(interface_id,part_id);
//	String cache_name = ICP.getCacheName(interface_id);	

	String cache_name = default_cache;   // per interface cache name is too expensive performance wise
	
	if(ICP.isKeyInCache(cache_name,cache_key))
	{
		ICP.removeKey(cache_name,cache_key);
	}		
  }
}

	public  Vector StringtoVector(String StringtoConvert)
	{
				int index = 0;
				Vector vv = new Vector();
	  	
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
	  	
//				System.out.println("==============vv==========="+vv);
	  	
	  	
				return vv;
	}
	  
	public  boolean CheckString(String StringtoCheck,String checkstring)
	{
				int index = 0;
				boolean flag = false;
	  	
				index = checkstring.indexOf(StringtoCheck);
	  	
//				System.out.println("==============index==========="+index);
				if(index==0 || index<0)
					flag=false;
				else
					flag=true;
	  	
				return flag;
	}

	public  int numOfTimes(String str,char charactertocheck) 
	{
		int numA = 0;
  
		for(int i = 0;i < str.length();i++) 
		{
			if(str.charAt(i)==charactertocheck) 
			{
//		 System.out.println("============i=========="+i);  
    		 numA++;
			}
		}
//   System.out.println("============numA=========="+numA);
   		return numA;
	}
	  
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException 
	{

		doGet(request, response);
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
