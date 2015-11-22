
package interfaceenginev2;
import java.lang.Exception;
import javax.servlet.ServletException;
import java.util.*;
import org.directwebremoting.ScriptBuffer ;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.ScriptSession ;
import org.grlea.log.*;
import org.w3c.dom.*;

import java.util.List;
import java.util.ResourceBundle;
import java.util.Properties;
import java.text.*;
import java.net.*;
import javax.servlet.http.*;
import  org.w3c.dom.Document;

import  org.w3c.dom.Element;
import java.io.*;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import  org.apache.xerces.dom.DocumentImpl;
//import  org.apache.xerces.dom.DOMImplementationImpl;
import java.util.*;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.cyberneko.html.parsers.DOMParser;
import java.io.InputStream;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tcs.genericlib.util.GenericUtil;

/**
 * 
 * @author Shibaji  Chatterjee
 */
public class PortalEngine
{  
	public  static final SimpleLogger log = new SimpleLogger(PortalEngine.class, true);// Create a SimpleLogger:



public void ChangeGridLoadQuery(String interface_id,String part_id,String name,String value)
	{
// 		String html = "";
// 		
// 		WebContext wctx1 = WebContextFactory.get();
// 		javax.servlet.http.HttpSession mysession = wctx1.getSession();
// 		System.out.println("================interface_id=========="+interface_id);
// 		System.out.println("================part_id=========="+part_id);
// 		System.out.println("================name=========="+name);
// 		System.out.println("================value=========="+value);
// 		String sql_query = NewDataBaseLayer.getSqlQuery(interface_id,part_id);
// 		String stringtoreplace = "%"+name+"%";
// 	  	String stringreplacewith ="'"+value+"'";
// 	  	
// 	  	sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
// 	  	System.out.println("==============sql_query in POJO========="+sql_query);		
// 		NewDataBaseLayer.updateactiveQuery(interface_id,part_id,sql_query);
		
		String html = "";
	try
	{	
		WebContext wctx1 = WebContextFactory.get();
		javax.servlet.http.HttpSession mysession = wctx1.getSession();
		
		String namepairvalue[]=new String[2];
		namepairvalue[0]=name;
		namepairvalue[1]=value;
		 
		mysession.removeAttribute("namepairvalue");
		System.out.println("=============namepairvalue.size()=========="+namepairvalue.length);
		mysession.setAttribute("namepairvalue",namepairvalue);
	}catch(Exception e){e.printStackTrace();}
		
	}

public void ChangeGridActiveQuery(String interface_id,String part_id,String name,String value)
	{
// 		String html = "";
// 		
// 		WebContext wctx1 = WebContextFactory.get();
// 		javax.servlet.http.HttpSession mysession = wctx1.getSession();
// 		System.out.println("================interface_id=========="+interface_id);
// 		System.out.println("================part_id=========="+part_id);
// 		System.out.println("================name=========="+name);
// 		System.out.println("================value=========="+value);
// 		String sql_query = NewDataBaseLayer.getSqlActiveQuery(interface_id,part_id);
// 		String stringtoreplace = "%"+name+"%";
// 	  	String stringreplacewith ="'"+value+"'";
// 	  	
// 	  	sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
// 	  	System.out.println("==========active====sql_query in POJO========="+sql_query);	
// 	  		
// 		NewDataBaseLayer.updateactiveQuery(interface_id,part_id,sql_query);
		
		String html = "";
		
		WebContext wctx1 = WebContextFactory.get();
		javax.servlet.http.HttpSession mysession = wctx1.getSession();
		
		String namepairvalue[]=new String[2];
		namepairvalue[0]=name;
		namepairvalue[1]=value;
		 
		mysession.removeAttribute("namepairvalue");
		System.out.println("=============namepairvalue.size()=========="+namepairvalue.length);
		mysession.setAttribute("namepairvalue",namepairvalue);
		
		
	}



public String getUpdatedDropDown(String interface_id,String part_id,String[]  dropdownname)
	{
		String html = "";
		
		WebContext wctx1 = WebContextFactory.get();
		javax.servlet.http.HttpSession mysession = wctx1.getSession();
		System.out.println("================update interface_id=========="+interface_id);
		System.out.println("================update part_id=========="+part_id);
		
		String sql_query = NewDataBaseLayer.getDropdownSqlQuery(interface_id,part_id);
		for(int i=0;i<dropdownname.length;i=i+2)
		{
		String stringtoreplace = "%"+dropdownname[i]+"%";
	  	String stringreplacewith ="'"+dropdownname[i+1]+"'";
	  	
	  	sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
	   }
	  	System.out.println("**************************************  DROPDWON QUERY**************************"+sql_query);	
		NewDataBaseLayer.updatedropdownactiveQuery(interface_id,part_id,sql_query);
		html=GeneratePageDropDownString(sql_query);
		
		System.out.println("==============html in updated dropdown======="+html);
		
		
		return html;
		
	}

	public void ChangeVectorGridLoadQuery(String interface_id,String part_id,String[] namepairvalue)
	{
// 		String html = "";
		// 		
// 		WebContext wctx1 = WebContextFactory.get();
// 		javax.servlet.http.HttpSession mysession = wctx1.getSession();
// 		System.out.println("================interface_id=========="+interface_id);
// 		System.out.println("================part_id=========="+part_id);
// 		String sql_query = NewDataBaseLayer.getSqlQuery(interface_id,part_id);
// 		System.out.println("=============namepairvalue.size()=========="+namepairvalue.length);
		// 		
// 		for(int i=0;i<namepairvalue.length;i=i+2)
// 		{
// 		String stringtoreplace = "%"+namepairvalue[i]+"%";
// 	  	String stringreplacewith ="'"+namepairvalue[i+1]+"'";
		// 	  	
// 	  	sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
// 	        }
// 	  	System.out.println("==============sql_query in POJO vector========="+sql_query);		
// 		NewDataBaseLayer.updateactiveQuery(interface_id,part_id,sql_query);
		
		
		String html = "";
		
		WebContext wctx1 = WebContextFactory.get();
		javax.servlet.http.HttpSession mysession = wctx1.getSession();
		System.out.println("================interface_id=========="+interface_id);
		System.out.println("================part_id=========="+part_id);
		mysession.removeAttribute("namepairvalue");
		String sql_query = NewDataBaseLayer.getSqlQuery(interface_id,part_id);
		System.out.println("=============namepairvalue.size()=========="+namepairvalue.length);
		mysession.setAttribute("namepairvalue",namepairvalue);
		
		
	}

/**************************** Partha on 26.11.08 **********************************/
		
		/*------------------------jayanta fir scarch 14/01/09---------------------------------------*/
		
public void ChangeVectorGridLoadQuerySearch(String interface_id,String part_id,String[] namepairvalue)
{
	String html = "";
		
	WebContext wctx1 = WebContextFactory.get();
	javax.servlet.http.HttpSession mysession = wctx1.getSession();
	System.out.println("////////////////////////my work area////////////////////////////////");
	System.out.println("================interface_id=========="+interface_id);
	System.out.println("================part_id=========="+part_id);
	String sql_query = NewDataBaseLayer.getSqlQuery(interface_id,part_id);
	System.out.println("=============namepairvalue.size()=========="+namepairvalue.length);
	for(int i=0;i<namepairvalue.length;i=i+2)
	{
		String stringtoreplace = "%%"+namepairvalue[i]+"%%";
		String stringreplacewith ="'%"+namepairvalue[i+1]+"%'";
		System.out.println("==============sql_query=before update========"+sql_query);
		sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
	}
	System.out.println("==============sql_query in POJO vector========="+sql_query);		
	NewDataBaseLayer.updateactiveQuery(interface_id,part_id,sql_query);
	System.out.println("////////////////////////end my work area////////////////////////////////");
		
		
}
		
		
/*------------------------End jayanta fir scarch---------------------------------------*/
		
		
		
		
		
		

	public String ModifySelectLoadQuery(String interface_id,String part_id,String column_id,String name,String value)
	{
		String html;
		WebContext wctx1 = WebContextFactory.get();
		javax.servlet.http.HttpSession mysession = wctx1.getSession();
		String sql_query = NewDataBaseLayer.getGridSelectSqlQuery(interface_id,part_id,column_id);
		String stringtoreplace = "%"+name+"%";
		String stringreplacewith ="'"+value+"'";
	  	sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
		System.out.println("==============sql_query in ModifySelectLoadQuery========="+sql_query);		
		NewDataBaseLayer.updateGridSelectActiveQuery(interface_id,part_id,column_id,sql_query);
		html=NewDataBaseLayer.returnpagedropdownstring(sql_query);
		return html;
		
	}
	
	
	/*public String getSelectOption(String interface_id,String part_id,String column_id)
	{
		String html = "";
		WebContext wctx1 = WebContextFactory.get();
		javax.servlet.http.HttpSession mysession = wctx1.getSession();
		
		String sql_query = NewDataBaseLayer.getGridSelectActiveQuery(interface_id,part_id,column_id);
		
		System.out.println("==============sql_query in getSelectOption========="+sql_query);	
		html=NewDataBaseLayer.returndropdownstring(sql_query);
		return html;
		
	}*/
	
	
	public void ModifyVectorSelectLoadQuery(String interface_id,String part_id,String column_id,String[] namepairvalue)
	{
		String html = "";
		WebContext wctx1 = WebContextFactory.get();
		javax.servlet.http.HttpSession mysession = wctx1.getSession();
		String sql_query = NewDataBaseLayer.getGridSelectSqlQuery(interface_id,part_id,column_id);
		for(int i=0;i<namepairvalue.length;i=i+2)
		{
			String stringtoreplace = "%"+namepairvalue[i]+"%";
			String stringreplacewith ="'"+namepairvalue[i+1]+"'";
	  	
			sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
		}
		System.out.println("==============sql_query in ModifyVectorSelectLoadQuery========="+sql_query);		
		NewDataBaseLayer.updateGridSelectActiveQuery(interface_id,part_id,column_id,sql_query);
		
		
	}
	
	
	

	/********************************* End of Partha on 26.11.08 ***************************/

	public  static String GeneratePageDropDownString(String sql_query)
	{
		String html="";
		System.out.println("**************************GeneratePageDropDownString********"+sql_query);

		Vector dropdownstring=NewDataBaseLayer.returnpagedropdown(sql_query);
		for(int i=0;i<dropdownstring.size();i=i+2)
		{
			String name=(String)dropdownstring.elementAt(i);
			System.out.println("........................name................."+name);
			String value=(String)dropdownstring.elementAt(i+1);
			System.out.println("........................value................."+value);
			html+="<option value=\""+name+"\">"+value+"</option>";
		}	
		
		System.out.println("**************************GeneratePageDropDownString********"+html);
		return html;
	}
	
	public   String userparameterstring()
	{
		String html="";
		WebContext wctx1 = WebContextFactory.get();
		javax.servlet.http.HttpSession mysession = wctx1.getSession();
		String user_id=(String) mysession.getAttribute("user_id");
		html="{current_login_user_id:'"+user_id+"'}";
		return html;
	}
	
	
	public String WritetoSession(String session_name,String session_value)
	{
		String html="";
		WebContext wctx1 = WebContextFactory.get();
		javax.servlet.http.HttpSession mysession = wctx1.getSession();
		mysession.setAttribute(session_name,session_value);
		return html;
	}
	
	public String getFromSession(String session_name)
	{
		String value="";
		WebContext wctx1 = WebContextFactory.get();
		javax.servlet.http.HttpSession mysession = wctx1.getSession();
		value=(String) mysession.getAttribute(session_name);
		return value;
	}
	
	public String[] getFromSessionForReset(String iid,String pid,String page_no)
	{
		String page_novalue="";
		WebContext wctx1 = WebContextFactory.get();
		javax.servlet.http.HttpSession mysession = wctx1.getSession();
		page_novalue=(String) mysession.getAttribute(page_no);
		String[] resetDetails=new String[3];
		try
		{
		resetDetails[0]=iid;
		resetDetails[1]=pid;
		if(page_novalue==null)
		{
			page_novalue="1";
		}
		resetDetails[2]=page_novalue;
		}catch(Exception e){e.printStackTrace();}
		return resetDetails;
	}
	
	public   String  Insert(String interface_id,String part_id,String formelement[])
	{
		String html="";
		WebContext wctx1 = WebContextFactory.get();
		javax.servlet.http.HttpSession mysession = wctx1.getSession();
		//String user_id=(String) mysession.getAttribute("user_id");
		int no_of_addquery=NewDataBaseLayer.getNo_of_AddQuery_Form(interface_id,part_id);
		String  add_query="";	
		String  parameter_title="";	
		
			
		System.out.println("..........................no of query..............add...."+no_of_addquery);
		for(int i=0;i<no_of_addquery;i++)
		{
			Vector  parameter_title_vector=new Vector();	
			add_query=NewDataBaseLayer.get_Add_Query(interface_id,part_id,i+1);
			parameter_title=NewDataBaseLayer.getAddQueryParameterForm(interface_id,part_id,i+1);
			parameter_title_vector = StringtoVector(parameter_title);
			int l = formelement.length;
			Vector  addvaluevector=new Vector();
			System.out.println("..................FORM ELEMENT...ARRAY LENGTH............."+l);
			for(int k=0;k<parameter_title_vector.size();k++)
			{
					for (int j = 0;j < l;j=j+2 )
					{
						String parameter= (String)parameter_title_vector.elementAt(k);
						if(parameter.equals(formelement[j]))
						{
							String stringtoreplace = "%"+formelement[j]+"%";
							System.out.println("stringtoreplace  ........................." + stringtoreplace);
							String stringreplacewith="?";
							System.out.println("stringreplacewith  ........................." + stringreplacewith);
							add_query = add_query.replace(stringtoreplace,stringreplacewith);
							System.out.println("value  ........................." + formelement[j+1]);
							String getClass=NewDataBaseLayer.getClass(interface_id,formelement[j]);
							if(getClass.equalsIgnoreCase("formdate"))
							{
								String df = "";	
								String str1 =""; 
								String str2 =""; 
								String str3 =""; 
								String str4 =""; 
								
									try
									{
										formelement[j+1] = (formelement[j+1]==null || formelement[j+1].equals("")) ? "00/00/0000" : formelement[j+1];
										if (!formelement[j+1].equals(""))
										{
											str1 = formelement[j+1].substring(0,formelement[j+1].indexOf('/'));
											str2 = formelement[j+1].substring(formelement[j+1].indexOf('/')+1);
											str3 = str2.substring(0,str2.indexOf('/'));
											str4 = str2.substring(str2.indexOf('/')+1);
											df = str4+"-"+str3+"-"+str1;
										}
									}
									catch(Exception e)
									{df="0000-00-00";}
									addvaluevector.addElement(df);
							}
							else
							{
								addvaluevector.addElement(formelement[j+1]);
							}
							
							
						}
					}
					System.out.println(".................................ADD QUERY IN FORM...AFTER REPLACE.............."+add_query);
					System.out.println(".................................ADD QUERY IN FORM...VECTOR.............."+addvaluevector);

			}
			NewDataBaseLayer.ExecuteSqlQueryForForm(add_query,addvaluevector);
			
		}
		return add_query;
	}
	
	
	public   void  Modify(String interface_id,String part_id,String formelement[])
	{
		String html="";
		WebContext wctx1 = WebContextFactory.get();
		int no_of_addquery=NewDataBaseLayer.getNo_of_ModifyQuery_Form(interface_id,part_id);
		String  add_query="";	
		String  parameter_title="";	
		
		for(int i=0;i<no_of_addquery;i++)
		{
			Vector  parameter_title_vector=new Vector();	
			Vector  addvaluevector=new Vector();		
			add_query=NewDataBaseLayer.get_Modify_Query(interface_id,part_id,i+1);
			int l = formelement.length;
			parameter_title=NewDataBaseLayer.getModifyQueryParameterForm(interface_id,part_id,i+1);
			parameter_title_vector = StringtoVector(parameter_title);
			System.out.println(".....................ARRAY LENGTH............."+l);
			for(int k=0;k<parameter_title_vector.size();k++)
			{
						for (int j = 0;j < l;j=j+2 )
						{
							String parameter= (String)parameter_title_vector.elementAt(k);
							if(parameter.equals(formelement[j]))
							{
							String stringtoreplace = "%"+formelement[j]+"%";
							System.out.println("stringtoreplace  ........................." + stringtoreplace);
							String stringreplacewith="?";
							System.out.println("stringreplacewith  ........................." + stringreplacewith);
							add_query = add_query.replace(stringtoreplace,stringreplacewith);
							String getClass=NewDataBaseLayer.getClass(interface_id,formelement[j]);
									if(getClass.equalsIgnoreCase("formdate"))
									{
												String df = "";	
												String str1 =""; 
												String str2 =""; 
												String str3 =""; 
												String str4 =""; 
												
												try
												{
													formelement[j+1] = (formelement[j+1]==null || formelement[j+1].equals("")) ? "00/00/0000" : formelement[j+1];
													if (!formelement[j+1].equals(""))
													{
														str1 = formelement[j+1].substring(0,formelement[j+1].indexOf('/'));
														str2 = formelement[j+1].substring(formelement[j+1].indexOf('/')+1);
														str3 = str2.substring(0,str2.indexOf('/'));
														str4 = str2.substring(str2.indexOf('/')+1);
														df = str4+"-"+str3+"-"+str1;
													}
												}
												catch(Exception e)
												{df="0000-00-00";}
												addvaluevector.addElement(df);
									}
									else
									{
										addvaluevector.addElement(formelement[j+1]);
									}
							
							}
						}
						System.out.println(".................................Modify QUERY IN FORM................."+add_query);
						System.out.println(".................................Modify QUERY IN FORM...VECTOR.............."+addvaluevector);
			}
			NewDataBaseLayer.ExecuteSqlQueryForForm(add_query,addvaluevector);
			
		}
		//return add_query;
	}
	
	
	public   String[]  getFormElement(String interface_id,String part_id)
	{
		String [] arr; 
		
		arr=NewDataBaseLayer.getdbform_element1(interface_id,part_id);
		System.out.println(".........................  FORMELEMNT ARRAY................"+arr.length);
		return arr;
		
	}
	
	
	public   String[]  Select(String interface_id,String part_id,String formelement[])
	{
		String [] arr=null; 
		String  select_query="";	
		String  parameter_title="";	
		
		int l = formelement.length;
		System.out.println("......................length array select................"+l);
		int no_of_selectquery=NewDataBaseLayer.getNo_of_SelectQuery_Form(interface_id,part_id);
		for(int i=0;i<no_of_selectquery;i++)
		{
			Vector  parameter_title_vector=new Vector();	
			select_query=NewDataBaseLayer.get_Select_Query(interface_id,part_id,i+1);
			parameter_title=NewDataBaseLayer.getSelectQueryParameterForm(interface_id,part_id,i+1);
			parameter_title_vector = StringtoVector(parameter_title);
			for(int k=0;k<parameter_title_vector.size();k++)
			{
				for (int j = 0;j < l;j=j+2 )
				{
					String parameter= (String)parameter_title_vector.elementAt(k);
					if(parameter.equals(formelement[j]))
					{
						String stringtoreplace = "%"+formelement[j]+"%";
						System.out.println("stringtoreplace  ........................." + stringtoreplace);
						String stringreplacewith="'"+formelement[j+1]+"'";
						System.out.println("stringreplacewith  ........................." + stringreplacewith);
						select_query = select_query.replace(stringtoreplace,stringreplacewith);
					}
				}
			}
			System.out.println("..........................SELECT   QUERY AFTER CHANGE ................."+select_query);

			arr=NewDataBaseLayer.ExecuteSqlQueryForm(interface_id,part_id,select_query);
		}
		System.out.println("..........................SELECT ................."+arr.length);
		
		return arr;
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
		System.out.println("===========VECTOR========="+vv);
	  	return vv;
	}
	
	public static String ChangeVectorGridLoadQueryForGrid(String interface_id,String part_id,String[] namepairvalue)
	{
		
		String html = "";
		System.out.println("================interface_id===2======="+interface_id);
		System.out.println("================part_id=========="+part_id);
		String sql_query = NewDataBaseLayer.getSqlQuery(interface_id,part_id);
		System.out.println("==================sql_query============="+sql_query);
		
		if(namepairvalue!=null)
		{
			for(int i=0;i<namepairvalue.length;i=i+2)
			{
				if(namepairvalue[i+1]==null)
				namepairvalue[i+1]="";
				System.out.println("===============namepairvalue["+i+"]========"+namepairvalue[i]);
				System.out.println("===============namepairvalue["+i+1+"]========"+namepairvalue[i+1]);
				String stringtoreplace = "%"+namepairvalue[i]+"%";
				String stringreplacewith ="'"+namepairvalue[i+1]+"'";
	  	
				sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
			}
		}
		else
		{
			Vector all_selector = NewDataBaseLayer.getAllSelectorId(interface_id,part_id);
			for(int i=0;i<all_selector.size();i++)
			{
				String stringtoreplace = "%"+(String)all_selector.elementAt(i)+"%";
				String stringreplacewith ="''";
	  	
				sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
			}
		}
		System.out.println("==============sql_query in POJO vector========="+sql_query);		
		
		html=sql_query;
		return html;
	}
	
	
	public String ChangeArrayDropDownSelectQuery(String interface_id,String content,String part_id,String[]  dropdownname)
	{
		String html = "";
		
		WebContext wctx1 = WebContextFactory.get();
		javax.servlet.http.HttpSession mysession = wctx1.getSession();
		
		Vector choose_one_information=NewDataBaseLayer.getChoose_one_information(content,part_id,interface_id);
		String show_choose_one="true";
		String choose_one_label="Choose One";
		String choose_one_value="0";
		String visible_choose="";
		System.out.println("..................choose_one_information.size()........"+choose_one_information.size());
		if(choose_one_information.size()!=0)
		{
			for(int ch=0;ch<choose_one_information.size();ch=ch+3)
			{
				show_choose_one=(String)choose_one_information.elementAt(ch);
				choose_one_label=(String)choose_one_information.elementAt(ch+1);
				choose_one_value=(String)choose_one_information.elementAt(ch+2);
			}
		}
				 
		if(show_choose_one.equals("false"))
		{
			visible_choose="";
		}
		if(show_choose_one==null || show_choose_one.equals("") || show_choose_one.equals("true"))
		{
			if(choose_one_label==null || choose_one_label.equals(""))
			{
				choose_one_label="Choose One";
			}
					 
			if(choose_one_value==null || choose_one_value.equals(""))
			{
				choose_one_value="0";
			}
			visible_choose="<option value=\""+choose_one_value+"\">"+choose_one_label+"</option>";
		}
	
		String sql_query = NewDataBaseLayer.getDropdownSqlQuery(interface_id,part_id);
		for(int i=0;i<dropdownname.length;i=i+2)
		{
			String stringtoreplace = "%"+dropdownname[i]+"%";
			String stringreplacewith ="'"+dropdownname[i+1]+"'";
	  		sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
		}
		html=GeneratePageDropDownString(sql_query);
		//System.out.println("...................ChangeArrayDropDownSelectQuery....."+html);
		return visible_choose+html;
		
	}
	
	
	public void SetVectorNamePairValueinSession(String interface_id,String part_id,String[] namepairvalue)
	{

		
		String html = "";
		try
		{	
			WebContext wctx1 = WebContextFactory.get();
			javax.servlet.http.HttpSession mysession = wctx1.getSession();
		
			mysession.removeAttribute("namepairvalue");		 
			mysession.setAttribute("namepairvalue",namepairvalue);
		}catch(Exception e){e.printStackTrace();}
		
	}
	
	public void ReuseCurrentGridQuery(String interface_id,String part_id)
	{
		WebContext wctx1 = WebContextFactory.get();
		javax.servlet.http.HttpSession mysession = wctx1.getSession();
		Vector interfacepart_reuse = new Vector();
		interfacepart_reuse.addElement(interface_id+"-"+part_id+"-reuse"); 
		mysession.setAttribute("REUSE_STRING",interfacepart_reuse);
		
	}
	
	
	public void StopReuseCurrentGridQuery(String interface_id,String part_id)
	{
		WebContext wctx1 = WebContextFactory.get();
		javax.servlet.http.HttpSession mysession = wctx1.getSession();
		Vector interfacepart_reuse = new Vector();
		interfacepart_reuse=(Vector)mysession.getAttribute("REUSE_STRING");
		for(int i=0;i<interfacepart_reuse.size();i++)
		{
			String string_to_check = (String)interfacepart_reuse.elementAt(i);
			if(string_to_check.equals(interface_id+"-"+part_id+"-reuse"))
			{
				interfacepart_reuse.remove(i);
				//count=i;
				break;
			}
				
		}
		 
		mysession.setAttribute("REUSE_STRING",interfacepart_reuse);
		
	}
	
	/*********************** Partha for Load query parameter**************************/
			
			public static String ChangeVectorGridLoadQueryForGrid(String interface_id,String part_id,String[] namepairvalue,Vector param_vector)
	{
		
		String html = "";
		String sql_query = "";
		System.out.println("===================param_vector.size()======"+param_vector.size());
		
		
		try
		{
			System.out.println("================interface_id===2======="+interface_id);
			System.out.println("================part_id=========="+part_id);
			sql_query = NewDataBaseLayer.getSqlQuery(interface_id,part_id);
			System.out.println("==================sql_query============="+sql_query);
		
			if(namepairvalue!=null)
			{
				for(int i=0;i<namepairvalue.length;i=i+2)
				{
					if(namepairvalue[i+1]==null)
						namepairvalue[i+1]="";
					System.out.println("===============namepairvalue["+i+"]========"+namepairvalue[i]);
					System.out.println("===============namepairvalue["+i+1+"]========"+namepairvalue[i+1]);
					String stringtoreplace = "%"+namepairvalue[i]+"%";
					String stringreplacewith ="'"+namepairvalue[i+1]+"'";
	  	
					sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
				}
				for(int j=0;j<param_vector.size();j=j+2)
				{
				
					System.out.println("===============param_vector.elementAt(j)========"+(String)param_vector.elementAt(j));
					System.out.println("==============param_vector.elementAt(j) value========"+(String)param_vector.elementAt(j+1));
					String stringtoreplace = "%"+(String)param_vector.elementAt(j)+"%";
					String stringreplacewith ="'"+(String)param_vector.elementAt(j+1)+"'";
	  	
					sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
				}
			}
			else
			{
				Vector all_selector = NewDataBaseLayer.getAllSelectorId(interface_id,part_id);
				for(int i=0;i<all_selector.size();i++)
				{
					String stringtoreplace = "%"+(String)all_selector.elementAt(i)+"%";
					String stringreplacewith ="''";
	  	
					sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
				}
				for(int j=0;j<param_vector.size();j=j+2)
				{
				
					System.out.println("===============param_vector.elementAt(j)========"+(String)param_vector.elementAt(j));
					System.out.println("==============param_vector.elementAt(j) value========"+(String)param_vector.elementAt(j+1));
					String stringtoreplace = "%"+(String)param_vector.elementAt(j)+"%";
					String stringreplacewith ="'"+(String)param_vector.elementAt(j+1)+"'";
	  	
					sql_query = sql_query.replace(stringtoreplace,stringreplacewith);
				}
			}
			System.out.println("==============sql_query in POJO vector========="+sql_query);		
		}
		catch(Exception e) {
			e.printStackTrace();}
			html=sql_query;
			return html;
	}
	/******************************* End of Partha **********************************/
			
	//////////////////////////////////////////////SUBIR/////////////////////////////////
			
	/*public String getInterfaceFragment(String interface_id) {
		String htmlsnippet="";
		try{
			WebContext wctx1 = WebContextFactory.get();
			HttpServletRequest request = wctx1.getHttpServletRequest();
			HttpServletResponse response = wctx1.getHttpServletResponse();
			javax.servlet.http.HttpSession mysession = wctx1.getSession();
			String  user_id = (String)mysession.getAttribute("user_id");
			String role_id="";
			if(user_id==null || user_id.equals(""))
			{
				role_id=NewDataBaseLayer.getDefaultRoleID("DEFAULT");
				htmlsnippet=NewDataBaseLayer.GetFragementSnippet(role_id,interface_id);
			}
			else
			{
			role_id=NewDataBaseLayer.getRoleID(user_id);
			String interfaceengine_role_id=NewDataBaseLayer.getInterfaceengineRoleID(role_id.toUpperCase());

			htmlsnippet=NewDataBaseLayer.GetFragementSnippet(interfaceengine_role_id,interface_id);
			}
		  }
		catch(Exception e) {
			e.printStackTrace();
		}
		//System.out.println("........................HTMLLLLLLLLLLLLLLL............."+htmlsnippet);
		return htmlsnippet;
	}	
	*/		
	
	public String getInterfaceFragment(String master_interface_id,String interface_id) {
		String htmlsnippet="";
		InputStream htmlfragment=null;
		try
		{
			WebContext wctx1 = WebContextFactory.get();
			HttpServletRequest request = wctx1.getHttpServletRequest();
			HttpServletResponse response = wctx1.getHttpServletResponse();
			javax.servlet.http.HttpSession mysession = wctx1.getSession();
			String  user_id = (String)mysession.getAttribute("user_id");
			String role_id="";
			if(user_id==null || user_id.equals(""))
			{
				role_id=NewDataBaseLayer.getDefaultRoleID("DEFAULT");
				htmlfragment=NewDataBaseLayer.GetFragementSnippet(role_id,interface_id);
			}
			else
			{
				role_id=NewDataBaseLayer.getRoleID(user_id);
				String interfaceengine_role_id=NewDataBaseLayer.getInterfaceengineRoleID(role_id.toUpperCase());
				htmlfragment=NewDataBaseLayer.GetFragementSnippet(interfaceengine_role_id,interface_id);
			}
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
			
		Document fragdocument=null;
		try
		{
			org.cyberneko.html.parsers.DOMParser parser = new org.cyberneko.html.parsers.DOMParser();
			parser.parse(new InputSource(htmlfragment));
			fragdocument = parser.getDocument();
			NodeList inputtag = fragdocument.getElementsByTagName("input");
			//System.out.println("................INPUT LENGTH..........."+inputtag.getLength());
			for(int a=0; a<inputtag.getLength() ; a++)
			{
				    Element inputelement = (Element)inputtag.item(a);	
					 System.out.println("................INPUT LENGTH..........."+inputelement.getAttribute("type"));
					 if(inputelement.getAttribute("type").equals("button"))
					 {
						 String classfromThemes=GenerateClassFromThemes(master_interface_id,"button");     //From Themes
						 inputelement.setAttribute("class",classfromThemes);
					 }
					 if(inputelement.getAttribute("type").equals("text"))
					 {
						 String classfromThemes=GenerateClassFromThemes(master_interface_id,"inputtext");     //From Themes
						 inputelement.setAttribute("class",classfromThemes);
					 }
					 
					 if(inputelement.getAttribute("type").equals("password"))
					 {
						 String classfromThemes=GenerateClassFromThemes(master_interface_id,"password");     //From Themes
						 inputelement.setAttribute("class",classfromThemes);
					 }
					 if(inputelement.getAttribute("type").equals("textarea"))
					 {
						 String classfromThemes=GenerateClassFromThemes(master_interface_id,"textarea");     //From Themes
						 inputelement.setAttribute("class",classfromThemes);
					 }
					 
					 if(inputelement.getAttribute("type").equals("file"))
					 {
						 String classfromThemes=GenerateClassFromThemes(master_interface_id,"inputfile");     //From Themes
						 inputelement.setAttribute("class",classfromThemes);
					 }
			}
			
			NodeList selecttag = fragdocument.getElementsByTagName("select");
			for(int b=0; b<selecttag.getLength() ; b++)
			{
				Element selectelement = (Element)selecttag.item(b);	
				String classfromThemes=GenerateClassFromThemes(master_interface_id,"combo");     //From Themes
				selectelement.setAttribute("class",classfromThemes);
				
			}
			
			NodeList imgtag = fragdocument.getElementsByTagName("img");
			for(int c=0; c<imgtag.getLength() ; c++)
			{
				Element imgelement = (Element)imgtag.item(c);	
				String classfromThemes=GenerateClassFromThemes(master_interface_id,"image");     //From Themes
				imgelement.setAttribute("class",classfromThemes);
				
			}
			
			NodeList divtag = fragdocument.getElementsByTagName("div");
			for(int d=0; d<divtag.getLength() ; d++)
			{
				Element divelement = (Element)divtag.item(d);	
				String classfromThemes=GenerateClassFromThemes(master_interface_id,"label");     //From Themes
				divelement.setAttribute("class",classfromThemes);
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		try
		{
			Transformer trans2 = TransformerFactory.newInstance().newTransformer();
			trans2.setOutputProperty(OutputKeys.METHOD, "html");
			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(fragdocument);
			trans2.transform(source, result);
			htmlsnippet = result.getWriter().toString();
		}
		catch(Exception tran)
		{
			tran.printStackTrace();
		}
		///System.out.println("........................................................"+htmlsnippet);
		return htmlsnippet;
	}	
			
	
	
	
	
	//////////////////////////////////////////////SUBIR/////////////////////////////////
	
	public  String  GenerateClassFromThemes(String interface_id,String partclass)
	{
		String themes_id="";
		String template_id="";
		String template_name=NewDataBaseLayer.templateexist(interface_id);
		if(template_name.equals(""))
		{
			List<String> returnValues = NewDataBaseLayer.get_default_template_id();
			 if(GenericUtil.hasListData(returnValues)){
				 template_id=returnValues.get(0);
			 }
		}
		else
		{
			template_id=NewDataBaseLayer.gettemplate_id(template_name);
		}
		themes_id=NewDataBaseLayer.getThemes(interface_id,template_id);
		String themes_class=NewDataBaseLayer.getThemesClass(themes_id,partclass,"reference");
		return themes_class;
	}
}