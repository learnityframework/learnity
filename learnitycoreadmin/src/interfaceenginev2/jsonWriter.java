package interfaceenginev2;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import  org.w3c.dom.Document;
import org.xml.sax.InputSource;

import org.apache.xerces.parsers.DOMParser;

import  org.w3c.dom.Element;
import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import  org.apache.xerces.dom.DocumentImpl;
import  org.apache.xerces.dom.DOMImplementationImpl;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.util.*;

public class jsonWriter extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public jsonWriter() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 String key = request.getParameter("key");  
		 System.out.println("Key value ======"+key);
		 String part_id=request.getParameter("part_id");
		 String interface_id=request.getParameter("interface_id");
		 String classname=request.getParameter("classname");
		 String islazynode=request.getParameter("islazynode");
		 String tooltip=request.getParameter("tooltip");
		 
		 System.out.println("part_id value ======"+part_id);
		 System.out.println("interface_id value ======"+interface_id);
		 System.out.println("classname value ======"+classname);
		 HttpSession mysession=request.getSession();

		 JSONArray arrayObj=new JSONArray();
		 if(classname.equals("dynamictree"))
		 {
			 String strQuery[]=new String[2];				 
			 strQuery=getDynamicTreeQueries(interface_id,part_id,mysession);
			 
			 if(islazynode.equals("true")){
				 if(key.equals("root"))
				 {
					 arrayObj = createRootNodes(interface_id,part_id,islazynode,tooltip,strQuery[0],strQuery[1]);			 	
				 }
				 else 
				 {
					 arrayObj = createChildNodes(interface_id,part_id,islazynode,tooltip,strQuery[0],strQuery[1],key);
				 }
			 }
			 else{
				 arrayObj =createDynamicTree(interface_id,part_id,islazynode,tooltip,strQuery[0],strQuery[1]);
			 }
		 }
		 else 
		 {
			      InputStream nodestructure =NewDataBaseLayer.getNodeStructure(interface_id,part_id);

					if(islazynode.equals("true")){
								if(key.equals("root"))
								{
									arrayObj =getStaticNode(interface_id,part_id,nodestructure,islazynode,tooltip);		 	
								}
								else 
								{
									arrayObj =getStaticNode(key,interface_id,part_id,nodestructure,islazynode,tooltip);		 	
								}
						
					}
					else{
								arrayObj =getStaticNodeNotLazy(interface_id,part_id,nodestructure,islazynode,tooltip);		 	
					}
		 }
		 PrintWriter out = response.getWriter();
		 out.println(arrayObj);		 
	}


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
			
	public static JSONArray getStaticNode(String interface_id,String part_id,InputStream nodestruct,String lazy,String tooltip){
	
		JSONArray arrayObj1=new JSONArray();
		GsonChildjs childobj = null;
		Gson gson = new Gson();
		Document d=null;
		try
		{
			InputStreamReader input_reader = new InputStreamReader(nodestruct,"UTF-8");
			InputSource input_source = new InputSource(input_reader); 
			input_source.setEncoding("UTF-8");
			DocumentBuilderFactory dbf= DocumentBuilderFactory.newInstance();
			DocumentBuilder db= dbf.newDocumentBuilder();
			d= db.parse(input_source);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
       			
		NodeList treedata = d.getElementsByTagName("treedata");
		for(int a=0; a<treedata.getLength() ; a++){
			Element treedatanode = (Element)treedata.item(a);
					
			NodeList nodeitem = ((Element)treedata.item(a)).getElementsByTagName("node");
			for(int b=0; b<nodeitem.getLength() ; b++){
				Element nodeelement = (Element)nodeitem.item(b);
				if(nodeelement.getParentNode().getNodeName().equals("treedata")){
					String id = nodeelement.getAttribute("key");		
					//System.out.println(".....................ID..........."+id);
					String title = nodeelement.getAttribute("title");	
				//	System.out.println(".....................title..........."+title);
					String isFolder = nodeelement.getAttribute("isFolder");	
					//System.out.println(".....................isFolder..........."+isFolder);
					String url = nodeelement.getAttribute("url");
					//System.out.println(".....................url..........."+url);
					String param="0";	
					childobj = new GsonChildjs(id,title,param,tooltip,url,isFolder,lazy);
					arrayObj1.put(gson.toJson(childobj));
				}
				System.out.println(".........getStaticNode........arrayobject..............."+arrayObj1);
			}
		}
		return arrayObj1;
	}
			
	public static JSONArray getStaticNode(String p_id,String interface_id,String part_id,InputStream nodestruct,String lazy,String tooltip){
	
			JSONArray arrayObj1=new JSONArray();
			GsonChildjs childobj = null;
			Gson gson = new Gson();
			Document d=null;
			try
			{
				InputStreamReader input_reader = new InputStreamReader(nodestruct,"UTF-8");
				InputSource input_source = new InputSource(input_reader); 
				input_source.setEncoding("UTF-8");
				DocumentBuilderFactory dbf= DocumentBuilderFactory.newInstance();
				DocumentBuilder db= dbf.newDocumentBuilder();
				d= db.parse(input_source);
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
				
			NodeList nodeitem = d.getElementsByTagName("node");
			for(int b=0; b<nodeitem.getLength() ; b++){
				Element nodeelement = (Element)nodeitem.item(b);
				String nodeelement_id=nodeelement.getAttribute("key");
				if(nodeelement_id.equals(p_id))
				{
					NodeList nodeitemchild = ((Element)nodeitem.item(b)).getElementsByTagName("node");
					for(int c=0; c<nodeitemchild.getLength() ; c++){
					Element nodeelementchild = (Element)nodeitemchild.item(c);
					Element nodeelementchild_parent =(Element) nodeelementchild.getParentNode( );
					String nodeelementchild_parent_id=nodeelementchild_parent.getAttribute("key");
					if(nodeelementchild_parent_id.equals(p_id)){
					//	System.out.println("..............nodeelementchild_parent_id check............");
						String id = nodeelementchild.getAttribute("key");		
					//	System.out.println(".....................ID..........."+id);
						String title = nodeelementchild.getAttribute("title");	
					//	System.out.println(".....................title..........."+title);
						String isFolder = nodeelementchild.getAttribute("isFolder");	
					//	System.out.println(".....................isFolder..........."+isFolder);
						String url = nodeelementchild.getAttribute("url");
					//	System.out.println(".....................url..........."+url);
						childobj = new GsonChildjs(id,title,p_id,tooltip,url,isFolder,lazy);
						arrayObj1.put(gson.toJson(childobj));	
					}
					
					System.out.println(".........getStaticNode CHILS........arrayobject..............."+arrayObj1);
			 	   }
			   }
		  }
		  return arrayObj1;
	}
	
	public static JSONArray getStaticNodeNotLazy(String interface_id,String part_id,InputStream nodestruct,String lazy,String tooltip){
	
		JSONArray arrayObj1=new JSONArray();
		JSONArray arrayObj2=new JSONArray();
		GsonChildjs childobj = null;
		GsonChildjs childobj1 = null;
		Gson gson = new Gson();
		Document d=null;
		try
		{
			InputStreamReader input_reader = new InputStreamReader(nodestruct,"UTF-8");
			InputSource input_source = new InputSource(input_reader); 
			input_source.setEncoding("UTF-8");
			DocumentBuilderFactory dbf= DocumentBuilderFactory.newInstance();
			DocumentBuilder db= dbf.newDocumentBuilder();
			d= db.parse(input_source);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
       			
		NodeList treedata = d.getElementsByTagName("treedata");
		for(int a=0; a<treedata.getLength() ; a++){
			Element treedatanode = (Element)treedata.item(a);
					
			NodeList nodeitem = ((Element)treedata.item(a)).getElementsByTagName("node");
			for(int b=0; b<nodeitem.getLength() ; b++){
				Element nodeelement = (Element)nodeitem.item(b);
				if(nodeelement.getParentNode().getNodeName().equals("treedata")){
					
					String id = nodeelement.getAttribute("key");		
					String title = nodeelement.getAttribute("title");	
					String isFolder = nodeelement.getAttribute("isFolder");	
					String url = nodeelement.getAttribute("url");
					String param="0";	
					NodeList nodeitemchild = ((Element)nodeitem.item(b)).getElementsByTagName("node");
					if(nodeitemchild.getLength()>0){
						arrayObj2=getStaticNodeNotLazyChild(d,childobj,gson,id,interface_id,part_id,nodestruct,lazy,tooltip);
						childobj = new GsonChildjs(id,title,param,tooltip,url,isFolder,lazy,arrayObj2);
						arrayObj1.put(gson.toJson(childobj));
					}
					else
					{
						childobj = new GsonChildjs(id,title,param,tooltip,url,isFolder,lazy);
						arrayObj1.put(gson.toJson(childobj));
					}
					
				}
			}
		}
		System.out.println("............ARRAY.........getStaticNodeNotLazy........."+arrayObj1);
		return arrayObj1;
	}
	
	public static JSONArray getStaticNodeNotLazyChild(Document d,GsonChildjs childobj,Gson gson,String p_id,String interface_id,String part_id,InputStream nodestruct,String lazy,String tooltip){
		
		JSONArray arrayObj2=new JSONArray();   	
		JSONArray arrayObj1=new JSONArray();   			
		NodeList nodeitem = d.getElementsByTagName("node");
		for(int b=0; b<nodeitem.getLength() ; b++){
			Element nodeelement = (Element)nodeitem.item(b);
			Element nodeelement_parent =(Element)nodeelement.getParentNode();
			String nodeelement_parent_id=nodeelement_parent.getAttribute("key");
			if(nodeelement_parent_id.equals(p_id))
			{
				String id = nodeelement.getAttribute("key");		
				String title = nodeelement.getAttribute("title");	
				String isFolder = nodeelement.getAttribute("isFolder");	
				String url = nodeelement.getAttribute("url");
				
				NodeList nodeitemchild = ((Element)nodeitem.item(b)).getElementsByTagName("node");
				if(nodeitemchild.getLength()>0){
					arrayObj2=getStaticNodeNotLazyChild(d,childobj,gson,id,interface_id,part_id,nodestruct,lazy,tooltip);
					childobj = new GsonChildjs(id,title,p_id,tooltip,url,isFolder,lazy,arrayObj2);
					arrayObj1.put(gson.toJson(childobj));
				}
				else
				{
					childobj = new GsonChildjs(id,title,p_id,tooltip,url,isFolder,lazy);
					arrayObj1.put(gson.toJson(childobj));
				}
				
			}
		}
		System.out.println("................return..getStaticNodeNotLazyChild.......>>>>>ARRAY OBJECT>>>>>>>>>>>>>>........."+arrayObj1);
		return arrayObj1;
	}
	/////////////////////////////dynamictree start/////////////////////////////////////////////
	
	public static JSONArray createRootNodes(String interface_id,String part_id,String islazynode,String tooltip,String parentquery,String childquery)
	{
		//System.out.println("createRootNodes===root==");
		JSONArray arrayObj=new JSONArray();
		GsonChildjs childobj = null;
		Gson gson = new Gson();
		
		Vector vrootnode=NewDataBaseLayer.getRootNodes(parentquery);
		for(int i=0;i<vrootnode.size();i++)
		{
			String strRootNodes[]=new String[2];
			strRootNodes=(String[])vrootnode.elementAt(i);
			//System.out.println("key=="+strRootNodes[0]+"	name="+strRootNodes[1]);
			childobj = new GsonChildjs(strRootNodes[0],strRootNodes[1],islazynode,tooltip,"0",childquery);
			arrayObj.put(gson.toJson(childobj));
		}			
		System.out.println("arrayObj=createRootNodes=="+arrayObj);
		
		return arrayObj;
	}
	public static JSONArray createChildNodes(String interface_id,String part_id,String islazynode,String tooltip,String parentquery,String childquery,String parentid)
	{
		//System.out.println("createChildNodes===parentid=="+parentid);
		JSONArray arrayObj=new JSONArray();
		GsonChildjs childobj = null;
		Gson gson = new Gson();
		
		Vector vchildnode=NewDataBaseLayer.getChildNodes(childquery,parentid);
		//System.out.println("createChildNodes===vchildnode.size()=="+vchildnode.size());
		for(int i=0;i<vchildnode.size();i++)
		{
			String strChildNodes[]=new String[2];
			strChildNodes=(String[])vchildnode.elementAt(i);
			//System.out.println("key=="+strChildNodes[0]+"	name="+strChildNodes[1]);
			childobj = new GsonChildjs(strChildNodes[0],strChildNodes[1],islazynode,tooltip,parentid,childquery);
			arrayObj.put(gson.toJson(childobj));
		}
			
		System.out.println("arrayObj=createChildNodes=="+arrayObj);
		
		return arrayObj;
	}
	public static JSONArray createDynamicTree(String interface_id,String part_id,String islazynode,String tooltip,String parentquery,String childquery)
	{
		System.out.println("createDynamicTree=====");
		JSONArray arrayObj=new JSONArray();
		JSONArray arrayObjchild=new JSONArray();
		GsonChildjs childobj = null;
		Gson gson = new Gson();
		
		Vector vrootnode=NewDataBaseLayer.getRootNodes(parentquery);
		for(int i=0;i<vrootnode.size();i++)
		{
			String strRootNodes[]=new String[2];
			strRootNodes=(String[])vrootnode.elementAt(i);
			//System.out.println("key=="+strRootNodes[0]+"	name="+strRootNodes[1]);
			if(NewDataBaseLayer.hasChildNodes(strRootNodes[0],childquery))
			{
				arrayObjchild=createDynamicTreeChild(interface_id,part_id,islazynode,tooltip,childquery,strRootNodes[0]);
				childobj = new GsonChildjs(strRootNodes[0],strRootNodes[1],"0",tooltip,arrayObjchild);
				arrayObj.put(gson.toJson(childobj));
			}
			else{
				childobj = new GsonChildjs(strRootNodes[0],strRootNodes[1],tooltip,"0");
				arrayObj.put(gson.toJson(childobj));
			}
			
		}			
		System.out.println("arrayObj======"+arrayObj);
		
		return arrayObj;
	}
	public static JSONArray createDynamicTreeChild(String interface_id,String part_id,String islazynode,String tooltip,String childquery,String parentid)
	{
		//System.out.println("createDynamicTreeChild=====");
		JSONArray arrayObjparent=new JSONArray();
		JSONArray arrayObjchild=new JSONArray();
		GsonChildjs childobj=null;
		Gson gson = new Gson();
		
		Vector vchildnode=NewDataBaseLayer.getChildNodes(childquery,parentid);
		//System.out.println("createChildNodes===vchildnode.size()=="+vchildnode.size());
		for(int i=0;i<vchildnode.size();i++)
		{
			String strChildNodes[]=new String[2];
			strChildNodes=(String[])vchildnode.elementAt(i);
			//System.out.println("key=="+strChildNodes[0]+"	name="+strChildNodes[1]);
			if(NewDataBaseLayer.hasChildNodes(strChildNodes[0],childquery))
			{
				arrayObjchild=createDynamicTreeChild(interface_id,part_id,islazynode,tooltip,childquery,strChildNodes[0]);
				childobj = new GsonChildjs(strChildNodes[0],strChildNodes[1],parentid,tooltip,arrayObjchild);
				arrayObjparent.put(gson.toJson(childobj));
			}
			else{
				childobj = new GsonChildjs(strChildNodes[0],strChildNodes[1],tooltip,parentid);
				arrayObjparent.put(gson.toJson(childobj));
			}
			
		}			
		System.out.println("arrayObjparent====="+arrayObjparent);
		
		return arrayObjparent;
	}
	public static String[] getDynamicTreeQueries(String interface_id,String part_id,HttpSession mysession)
	{
		String strQuery[]=new String[2];
		String strQuery1[]=new String[2];
		strQuery1=NewDataBaseLayer.getDynamicTreeQueries(interface_id,part_id);
		String QParameters=NewDataBaseLayer.getQParameters(interface_id,part_id);
		System.out.println("QParameters====="+QParameters);
		String parentquery=strQuery1[0];
		String childquery=strQuery1[1];
		Vector vQparamentrs = convertVector(QParameters);
		//mysession.setAttribute("course_id","1");
		for(int j=0;j<vQparamentrs.size();j++)
		{
			String param=(String)vQparamentrs.elementAt(j);
			String stringtoreplace = "%"+param+"%";
			String stringreplacewith="'"+(String)mysession.getAttribute(param)+"'";
			parentquery = parentquery.replace(stringtoreplace,stringreplacewith);
			childquery = childquery.replace(stringtoreplace,stringreplacewith);
			System.out.println("parentquery====="+parentquery);
			System.out.println("childquery====="+childquery);
		}
		strQuery[0]=parentquery;
		strQuery[1]=childquery;
		return strQuery;
	}
	public static Vector convertVector(String QParameters)
	{
		Vector vv = new Vector();
	  	
	  	/*		
		StringTokenizer st =new StringTokenizer(StringtoConvert,",");
		System.out.println("tokens count: " + st.countTokens());
		while (st.hasMoreElements())
		{
			  String token = st.nextToken();
			  System.out.println("token = " + token);
			  vv.addElement(token.trim());
		}*/	  	
		
		String[] array = QParameters.split(",");
		for (int x=0; x<array.length; x++)
		{
			vv.addElement(array[x].trim());
		}
			

		//System.out.println("vv = " + vv);
		return vv;
	}
	/////////////////////////////dynamictree end////////////////////////////////////////////////////
	
		
}
