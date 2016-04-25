package interfaceenginev2.display;


/**
 * Title:        Learnity Interface Management   
 * Description:
 * Copyright:    Copyright (c) 2010
 * Company:      Aunwesha
 * @author Shibaji Chatterjee
 */


import interfaceenginev2.NewDataBaseLayer;
import interfaceenginev2.bean.ApplicationTemplate;
import interfaceenginev2.display.bean.GridProperty;
import interfaceenginev2.display.component.ConditionalDBgrid;
import interfaceenginev2.display.component.DBgrid;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.cyberneko.html.parsers.DOMFragmentParser;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import comv2.aunwesha.lfutil.GenericUtil;
import comv2.aunwesha.lfutil.LFResource;
import comv2.aunwesha.lfutil.Pair;




/**
 * Title:        LearnITy  Display Engine
 * Description:
 * Copyright:    Copyright (c) 2007
 * Company:      Aunwesha
 * @author 		 Shibaji   Chatterjee
 * @version 	 1.0
 */

public class DisplayEngine {
	public static final String BOOTSTRAP_UI = "bootstrap";
	private static final String ROOT_PART_ID = "root";
	/**
	 * 
	 */
	Document doc=null;
	String  interface_id_name=null;
	String layout=null;
	String content=null;
	String behaviour=null;
	String style=null;
	ServletOutputStream out=null;
	String sqlscript=null;
	String contextpath="";
	String realpath="";

	String gridstring="";
	String treestring="";
	String dom_ready="";	 

	String datejs="";	
	ApplicationTemplate applicationTemplate=null;

	public  void createStructure(String interface_id_fromclass,String layout_id_fromclass,String content_id_fromclass,String behaviour_id_fromclass,String style_id_fromclass)
	{
		
		interface_id_name=interface_id_fromclass;
		Pair<String, String> applicationTemplateDetails=ApplicationTemplateEngine.retrieveTemplateIdAndComment(interface_id_fromclass);
		
		
		
		String applicationTemplateId=applicationTemplateDetails.getFirst();
		String templateComment=applicationTemplateDetails.getSecond();
		this.applicationTemplate=NewDataBaseLayer.getApplicationTemplateConfigValues(applicationTemplateId);
		
		boolean isBootstrap=false;
		
		
		if(applicationTemplate!=null){
			if(GenericUtil.hasString(applicationTemplate.getUiFramework())){
				isBootstrap=BOOTSTRAP_UI.equalsIgnoreCase(applicationTemplate.getUiFramework());
			}
		}
		
		Pair<String, String> themeDetails=ThemeEngine.retrieveThemeIdAndCommnts(interface_id_name,applicationTemplateId);
		String themeId=themeDetails.getFirst();
		String themeComment=themeDetails.getSecond();
		List<String> addedJsResources=new ArrayList<>();
		StyleEngine styleEngine=new StyleEngine();

		layout=layout_id_fromclass;
		content=content_id_fromclass;
		behaviour=behaviour_id_fromclass;
		style=style_id_fromclass;
		DocumentBuilder builder = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try
		{
			factory.setValidating(true);
			builder = factory.newDocumentBuilder();
			doc = builder.newDocument();
		}
		catch (ParserConfigurationException e) {
		}

		Element root = (Element) doc.createElement("html");
		String windowstylevalue=NewDataBaseLayer.getStyleValueWindow(layout,style,interface_id_name);
		if(windowstylevalue==null)
		{
			windowstylevalue="";
		}
		if(!windowstylevalue.equals(""))
		{
			root.setAttribute("style",windowstylevalue);
		}	
		doc.appendChild(root);

		Element itemhead = doc.createElement("head");
		root.appendChild(itemhead);

		Element itemtitle = doc.createElement("title");
		itemhead.appendChild(itemtitle);
		String PageTilte=NewDataBaseLayer.getPageTitle(interface_id_name);
		itemtitle.appendChild(doc.createTextNode(PageTilte));
		Element itemMeta = doc.createElement("meta");
		itemMeta.setAttribute("content","text/html; charset=utf8");
		itemMeta.setAttribute("http-equiv","Content-Type");
		itemMeta.setAttribute("Pragma","no-cache");
		itemMeta.setAttribute("Cache-Control","no-cache");
		itemMeta.setAttribute("Expires","0");
		
		if(isBootstrap){
			itemMeta.setAttribute("viewport","width=device-width, initial-scale=1");
		}
		
		itemhead.appendChild(itemMeta);				



		List<String> rootbehaviourevent=NewDataBaseLayer.getEvent(layout,behaviour,interface_id_name);
		
		Element itembody = doc.createElement("body");//////////BODY CREATE//////////
		
		root.appendChild(itembody);//////////BODY APPEND WITH DOCUMENT//////////




		String rootstylevalue=NewDataBaseLayer.getStyleValueroot(layout,style,interface_id_name);
		if(rootstylevalue==null)
		{
			rootstylevalue="";
		}
		if(!rootstylevalue.equals(""))
		{
			itembody.setAttribute("style",rootstylevalue);
		}	
		String rootbehaviourvalue="";
		String checkInterfaceType=NewDataBaseLayer.GetInterfaceType(interface_id_name);/* This function is return interface type ( Interface or InterfaceFragment ) */
		if(checkInterfaceType.equals("InterfaceFragment"))
		{
			rootbehaviourvalue=NewDataBaseLayer.getbehaviourvalueforroot(interface_id_name+"_root",layout,behaviour,interface_id_name);
			if(rootbehaviourvalue==null)
			{
				rootbehaviourvalue="";	  					 	
			}
		}
		else
		{	 
			ThemeEngine.setStyleClassFromThemes(themeId, ROOT_PART_ID,itembody);
			//itembody.setAttribute("class",classfromThemes);
			rootbehaviourvalue=NewDataBaseLayer.getbehaviourvalueforroot(ROOT_PART_ID,layout,behaviour,interface_id_name);
			if(rootbehaviourvalue==null)
			{
				rootbehaviourvalue="";	  					 	
			}
		}	
		dom_ready=NewDataBaseLayer.getDomReadyFunctionName(layout,behaviour,interface_id_name);	
		if(rootbehaviourvalue.equals(""))
		{

		}
		else
		{
			createReferenceBehaviourForRoot(itemhead,itembody,interface_id_name,rootbehaviourvalue,addedJsResources);
		}

		if(GenericUtil.hasListData(rootbehaviourevent))
		{
			createBehaviourEvent(rootbehaviourevent.get(2), rootbehaviourevent.get(1), itembody, null,rootbehaviourevent.get(0) , interface_id_name, ROOT_PART_ID);
			//itembody.setAttribute("onLoad",rootbehaviourevent);
		}
		String rootcontentvaluelocation=NewDataBaseLayer.getContentlocationvalueforroot(layout,content,interface_id_name);
		if(rootcontentvaluelocation==null)
		{
			rootcontentvaluelocation="";	  					 	
		}
		if(rootcontentvaluelocation.equals("end"))
		{
			if(isBootstrap){
				Element parentBootstrapDiv=doc.createElement("div");
				parentBootstrapDiv.setAttribute("class", "container-fluid");
				itembody.appendChild(parentBootstrapDiv);
				createParentLayout(doc,itemhead,parentBootstrapDiv,layout,content,style,behaviour,themeId,addedJsResources,styleEngine);
			}else{
				createParentLayout(doc,itemhead,itembody,layout,content,style,behaviour,themeId,addedJsResources, styleEngine);
			}
			
			createContent(layout,content,itembody,ROOT_PART_ID,interface_id_name,doc);
		}
		else
		{
			createContent(layout,content,itembody,ROOT_PART_ID,interface_id_name,doc);
			if(isBootstrap){
				Element parentBootstrapDiv=doc.createElement("div");
				parentBootstrapDiv.setAttribute("class", "container-fluid");
				itembody.appendChild(parentBootstrapDiv);
				createParentLayout(doc,itemhead,parentBootstrapDiv,layout,content,style,behaviour,themeId,addedJsResources,styleEngine);
			}else{
				createParentLayout(doc,itemhead,itembody,layout,content,style,behaviour,themeId,addedJsResources, styleEngine);
			}
			
		}

		/**************************************Template Asset ADD**********************/

		try
		{
			ApplicationTemplateEngine.interfaceResourceWithTemplate(applicationTemplateId,root,itemhead,itembody,doc);
		}catch(Exception resourceadd)
		{
			resourceadd.printStackTrace();
		}
		/*
		 * Changes made by Dibyarup
		 * Template id is returned back from the method and added in the comment.
		 */
		Comment comm=doc.createComment("Generated by LearnITy Framework\n InterfaceID - "+interface_id_fromclass+
				"\nCreation Date - "+new Date()+"\n"+templateComment+"\n"+themeComment+"\n");
		root.insertBefore(comm,itemhead);

	}

	private void 	createParentLayout(Document doc,Element itemhead,Element itembody,String layout,String content,String style,String behaviour,String themeId,List<String> addedResources,StyleEngine styleEngine)
	{
		Vector<String> getstructurelayoutinformation=NewDataBaseLayer.getParentlayout(layout,interface_id_name); 
		for(int i=0;i<getstructurelayoutinformation.size();i=i+21)
		{
			String part_id=getstructurelayoutinformation.elementAt(i);	
			String position=getstructurelayoutinformation.elementAt(i+1);
			String x=getstructurelayoutinformation.elementAt(i+2);
			String y=getstructurelayoutinformation.elementAt(i+3);
			String width=getstructurelayoutinformation.elementAt(i+4);
			String height=getstructurelayoutinformation.elementAt(i+5);
			String partclass=getstructurelayoutinformation.elementAt(i+6);
			String interface_id=getstructurelayoutinformation.elementAt(i+7);
			//String resize=(String)getstructurelayoutinformation.elementAt(i+8);
			//String border=(String)getstructurelayoutinformation.elementAt(i+9);
			//String cols=(String)getstructurelayoutinformation.elementAt(i+10);
			//String rows=(String)getstructurelayoutinformation.elementAt(i+11);
			//String scrolling=(String)getstructurelayoutinformation.elementAt(i+12);
			//String spacing=(String)getstructurelayoutinformation.elementAt(i+13);
			//String colspan=(String)getstructurelayoutinformation.elementAt(i+14);
			//String maxlength=(String)getstructurelayoutinformation.elementAt(i+15);
			//String size=(String)getstructurelayoutinformation.elementAt(i+16);
			//String tabindex=(String)getstructurelayoutinformation.elementAt(i+17);
			//String archieve=(String)getstructurelayoutinformation.elementAt(i+18);
			//String codebase=(String)getstructurelayoutinformation.elementAt(i+19);
			//String mayscript=(String)getstructurelayoutinformation.elementAt(i+20);
			//String anotherclassfromdatabase=""; 
			Element itemmain=doc.createElement("div");
			itemmain.setAttribute("id",part_id);
			itembody.appendChild(itemmain);
			//String stylevalue=""; 
			//String cssClassValue=""; 
			
			/**
			 * TODO : Delete
			 */
			/*String styleval=NewDataBaseLayer.getStyleValue(layout,style,part_id,interface_id);
			Pair<String, String> styleRefPair=NewDataBaseLayer.getStyleValueType(layout,style,part_id,interface_id);
			String styletype=styleRefPair.getFirst();
			String styleResourceId=styleRefPair.getSecond();



			if(GenericUtil.hasString(styletype)){
				if(styletype.equals("reference"))
				{
					//System.out.println("...................STYLE TYPE........"+styletype);
					cssClassValue=styleval;
					createReferenceStyle(itemmain,itemhead,itembody,interface_id,styleResourceId,addedCssResources);
			
				}	
				if(styletype.equals("inline"))
				{
					stylevalue =styleval;        					        
				}	
			}
				

			Pair<String, String> classStylePair=ThemeEngine.generateClassFromThemes(themeId,partclass);     //From Themes
			ThemeEngine.setStyleClassAttribute(itemmain, classStylePair,"position:"+position+";left :"+x+"; top:"+y+";width:"+width+"; height:"+height+";"+stylevalue,null);
			*/
			styleEngine.createStyle(layout, style, part_id, interface_id, themeId, partclass, position, x, y, width, height, itemmain,itemhead,itembody,doc);
			//itemmain.setAttribute("class",classfromThemes);
			//itemmain.setAttribute("style",);
			getChildnode(doc,itemhead,itembody,layout,content,behaviour,style,interface_id,itemmain,part_id,themeId,addedResources,styleEngine);

		}	

	}


	private   void getChildnode(Document doc,Element itemhead,Element itembody,String layout,String content,String behaviour,String style,String interface_id,Element itemmain,String part_id,String themeId,List<String> addedResources,StyleEngine styleEngine)
	{

		Vector getlayoutchild=NewDataBaseLayer.getlayoutinformationchild(layout,interface_id,part_id);
		if(getlayoutchild.size()==0)
		{

		}
		else
		{
			for(int j=0;j<getlayoutchild.size();j=j+21)
			{
				String child_id=(String)getlayoutchild.elementAt(j);	
				String childposition=(String)getlayoutchild.elementAt(j+1);
				String childx=(String)getlayoutchild.elementAt(j+2);
				String childpartclass=(String)getlayoutchild.elementAt(j+6);
				if(childx==null || childx.equals(""))
				{
					childx=getApplicationDefaultValue(interface_id,childpartclass,"left");
				}
				String childy=(String)getlayoutchild.elementAt(j+3);
				if(childy==null || childy.equals(""))
				{
					childy=getApplicationDefaultValue(interface_id,childpartclass,"top");
				}
				String childwidth=(String)getlayoutchild.elementAt(j+4);
				if(childwidth==null || childwidth.equals(""))
				{
					childwidth=getApplicationDefaultValue(interface_id,childpartclass,"width");
				}
				String childheight=(String)getlayoutchild.elementAt(j+5);
				if(childheight==null || childheight.equals(""))
				{
					childheight=getApplicationDefaultValue(interface_id,childpartclass,"height");
				}

				String childinterface_id=(String)getlayoutchild.elementAt(j+7);
				String childresize=(String)getlayoutchild.elementAt(j+8);

				String childborder=(String)getlayoutchild.elementAt(j+9);
				String childcols=(String)getlayoutchild.elementAt(j+10);
				if(childcols==null || childcols.equals(""))
				{
					childcols=getApplicationDefaultValue(interface_id,childpartclass,"cols");
				}
				String childrows=(String)getlayoutchild.elementAt(j+11);
				if(childrows==null || childrows.equals(""))
				{
					childrows=getApplicationDefaultValue(interface_id,childpartclass,"rows");
				}
				String childscrolling=(String)getlayoutchild.elementAt(j+12);
				String childspacing=(String)getlayoutchild.elementAt(j+13);	
				String childcolspan=(String)getlayoutchild.elementAt(j+14);	
				String childmaxlength=(String)getlayoutchild.elementAt(j+15);	
				String childsize=(String)getlayoutchild.elementAt(j+16);	
				if(childsize==null || childsize.equals(""))
				{
					childsize=getApplicationDefaultValue(interface_id,childpartclass,"size");
				}

				String childtabindex=(String)getlayoutchild.elementAt(j+17);	
				String childarchieve=(String)getlayoutchild.elementAt(j+18);	
				String childcodebase=(String)getlayoutchild.elementAt(j+19);	
				String childmayscript=(String)getlayoutchild.elementAt(j+20);	


				String checkparentornot=NewDataBaseLayer.getChild(child_id,childinterface_id);
				if(checkparentornot==null || checkparentornot.equals(""))
				{  
					Element child=createLayout(doc,itemmain,itemhead,itembody,layout,content,behaviour,style,child_id,childposition,childx,childy,childwidth,childheight,childpartclass,childinterface_id,childresize,childborder,childcols,childrows,childscrolling,childspacing,childcolspan,childmaxlength,childsize,childtabindex,childarchieve,childcodebase,childmayscript,themeId,addedResources,styleEngine);
					if((childpartclass.equals("label")))
					{
						createContent(layout,content,child,child_id,childinterface_id,doc);
					} 
				}

				else
				{	
					Element child=createLayout(doc,itemmain,itemhead,itembody,layout,content,behaviour,style,child_id,childposition,childx,childy,childwidth,childheight,childpartclass,childinterface_id,childresize,childborder,childcols,childrows,childscrolling,childspacing,childcolspan,childmaxlength,childsize,childtabindex,childarchieve,childcodebase,childmayscript,themeId,addedResources,styleEngine);
					String contentlocation=NewDataBaseLayer.Getcontentlocation(childinterface_id,content,child_id);
					if(contentlocation==null || (contentlocation.equals("")))
					{
						contentlocation="";
					}
					if(contentlocation.equalsIgnoreCase("end"))
					{
						getChildnode(doc,itemhead,itembody,layout,content,behaviour,style,childinterface_id,child,child_id,themeId,addedResources,styleEngine);
						if((childpartclass.equals("label")))
						{
							createContent(layout,content,child,child_id,childinterface_id,doc);
						}
					} 
					if((contentlocation.equals("")) || contentlocation.equalsIgnoreCase("start"))
					{
						if((childpartclass.equals("label")))
						{	
							createContent(layout,content,child,child_id,childinterface_id,doc);
						}
						getChildnode(doc,itemhead,itembody,layout,content,behaviour,style,childinterface_id,child,child_id,themeId,addedResources,styleEngine);
					}

				}
			}
		}
	}



	private  Element  createLayout(Document doc,Element itemmain,Element itemhead,Element itembody,String layout,String content,String behaviour,String style,String child_id,String position,String x,String y,String width, String height,String partclass,String interface_id,String resize,String border,String cols,String rows,String scrolling,String spacing,String childcolspan,String childmaxlength,String childsize,String childtabindex,String childarchieve,String childcodebase,String childmayscript,String themeId,List<String> addedResources,StyleEngine styleEngine )
	{

		String chartServletURL=LFResource.DISPLAY_ENGINE.retriveResourceValue("chartServletURL");
		String reportServletURL=LFResource.DISPLAY_ENGINE.retriveResourceValue("reportServletURL");
		
		String anotherclassfromdatabase="";   
		Element layoutelement=null;
		//Element itemstyle;
		/*String styleval=NewDataBaseLayer.getStyleValue(layout,style,child_id,interface_id);
		Pair<String, String> styleRefPair=NewDataBaseLayer.getStyleValueType(layout,style,child_id,interface_id);
		String styletype=styleRefPair.getFirst();
		String styleResourceId=styleRefPair.getSecond();
		
		if(GenericUtil.hasString(styletype))
		{	
			if(styletype.equals("reference"))
			{
				//System.out.println("...................STYLE TYPE........"+styletype);
				cssClassValue=styleval;
				createReferenceStyle(itemmain,itemhead,itembody,interface_id,styleResourceId,addedCssResources);
			}	

			if(styletype.equals("inline"))
			{
				stylevalue =styleval;        					        
			}
		}		*/																										

		/////////////////////////////////////////////////////////////////////////////IMAGES/////////////////////////////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("image"))
		{  
			layoutelement=doc.createElement("div");
			layoutelement.setAttribute("id","image"+child_id);
			Element item=doc.createElement("img");
			item.setAttribute("id",child_id);
			item.setAttribute("border","0");
			if(GenericUtil.hasString(width)){
				item.setAttribute("width",width);
			}
			if(GenericUtil.hasString(height)){
				item.setAttribute("height",height);
			}
			
			
			
			layoutelement.appendChild(item);
			itemmain.appendChild(layoutelement);
			createContent( layout,content,item,child_id,interface_id,doc);
			createBehabiour(layout,behaviour,item,child_id,interface_id,addedResources);
			
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, null, null, null, null, null, item, itemhead, itembody, doc);
			styleEngine.createStyle(null, null, null, null, null, null, position, x, y, width, height, layoutelement, null, null, null);

		}   


		/////////////////////////////////////////////////////////////////////////////?IMAGES   END//////////////////////////////////////////////////////////////////////////////////////


		//////////////////////////////////////////////////////////////////////////////ANIMATION///////////////////////////////////////////////////////////////////////////////////////	
		if(partclass.equalsIgnoreCase("flashcomponent"))
		{
			layoutelement=doc.createElement("div");
			layoutelement.setAttribute("id","flashcomponent"+child_id);
			layoutelement.setAttribute("class","cflashcomponent"+child_id);
			itemmain.appendChild(layoutelement);

			Element objectitem=doc.createElement("object");
			Element embeditem=doc.createElement("embed");
			objectitem.setAttribute("id",child_id);

			Element paramitem=doc.createElement("param");
			paramitem.setAttribute("name","movie");

			String contentvalue=NewDataBaseLayer.getContentvalue(layout,content,child_id,interface_id);
			String resource_ani="./interfaceenginev2.ResourceAnimation?resource_id="+contentvalue+"&interface_id="+interface_id+"";		

			paramitem.setAttribute("value",resource_ani);
			objectitem.appendChild(paramitem);
			embeditem.setAttribute("name",child_id);
			objectitem.appendChild(embeditem);
			layoutelement.appendChild(objectitem);

			Vector parameter_list_vector=NewDataBaseLayer.getParameterList_flashcomponent(interface_id,content,child_id);
			if(parameter_list_vector.size()>0)
			{
				for(int param=0;param<parameter_list_vector.size();param=param+2)
				{
					String param_name=(String)parameter_list_vector.elementAt(param);
					String param_value=(String)parameter_list_vector.elementAt(param+1);

					if(param_value==null || param_value.equals(""))
					{
						param_value=getApplicationDefaultValue(interface_id,partclass,param_name);
					}


					if(param_name.equals("classid") || param_name.equals("codebase"))
					{
						objectitem.setAttribute(param_name,param_value);
					}
					if(param_name.equals("swLiveConnect") || param_name.equals("allowScriptAccess") || param_name.equals("type") || param_name.equals("pluginspage"))
					{
						embeditem.setAttribute(param_name,param_value);
					}
					if(param_name.equals("align") || param_name.equals("height") || param_name.equals("width") || param_name.equals("bgcolor"))
					{
						objectitem.setAttribute(param_name,param_value);
						embeditem.setAttribute(param_name,param_value);
					}

				} 

			}

			//ThemeEngine.setCssClassAttribute(layoutelement, cssClassValue);

			createContent( layout,content,embeditem,child_id,interface_id,doc);
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, position, x, y, width, height, layoutelement, itemhead, itembody, doc);
			//createBehabiour(layout,behaviour,item,child_id,interface_id);
		}
		//////////////////////////////////////////////////////////////////////////////ANIMATION END//////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////VIDEO///////////////////////////////////////////////////////////////////////////////////////	
		if(partclass.equalsIgnoreCase("video"))
		{
			layoutelement=doc.createElement("div");
			layoutelement.setAttribute("id",child_id);
			layoutelement.setAttribute("class","c"+child_id);
			Element item=doc.createElement("embed");
			layoutelement.appendChild(item);
			itemmain.appendChild(layoutelement);
			//ThemeEngine.setCssClassAttribute(layoutelement, cssClassValue);
			createContent(layout,content,item,child_id,interface_id,doc);
			createBehabiour(layout,behaviour,item,child_id,interface_id,addedResources);
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, position, x, y, width, height, layoutelement, itemhead, itembody, doc);
			
		}
		//////////////////////////////////////////////////////////////////////////////VIDEO END//////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////TEXTLINK///////////////////////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("textlink"))
		{
			layoutelement =doc.createElement("div");
			Element itemtexlinkhref =doc.createElement("a");
			//itemtexlinkhref.setAttribute("class",classfromThemes);

			layoutelement.appendChild(itemtexlinkhref);
			itemtexlinkhref.setAttribute("id",child_id);
			itemmain.appendChild(layoutelement);
			layoutelement.setAttribute("id","textlink"+child_id);
			layoutelement.setAttribute("class","c"+child_id);
			//ThemeEngine.setCssClassAttribute(itemtexlinkhref, cssClassValue);
			createContent(layout,content,itemtexlinkhref,child_id,interface_id,doc);
			createBehabiour(layout,behaviour,itemtexlinkhref,child_id,interface_id,addedResources);
			
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, null, null, null, null, null, itemtexlinkhref, itemhead, itembody, doc);
			styleEngine.createStyle(null, null, null, null, null, null, position, x, y, width, height, layoutelement, null, null, null);
		}

		///////////////////////////////////////////////////////////////////////////////TEXTLINK  END///////////////////////////////////////////////////////////////////////////////////////

		///////////////////////////////////////////////////////////////////////////////LABEL//////////////////////////////////////////////////////////////////////////////////////////////

		if(partclass.equalsIgnoreCase("label"))
		{
			layoutelement=doc.createElement("div");
			itemmain.appendChild(layoutelement);

			//layoutelement.setAttribute("class",classfromThemes);
			layoutelement.setAttribute("id",child_id);
			//ThemeEngine.setCssClassAttribute(layoutelement, cssClassValue);
			//layoutelement.setAttribute("style","position:"+position+";left :"+x+"; top:"+y+";width:"+width+"; height:"+height+";"+stylevalue);
			////////// HERE IS NOT CREATE CONTENT METHOD REQUIRED ITS CALL UPPER PORTION OF CODE///////////
			createBehabiour(layout,behaviour,layoutelement,child_id,interface_id,addedResources);
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, position, x, y, width, height, layoutelement, itemhead, itembody, doc);
		}	

		///////////////////////////////////////////////////////////////////////////////LABEL END/////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////FORM/////////////////////////////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("form"))
		{
			layoutelement=doc.createElement("form");
			//layoutelement.setAttribute("class",classfromThemes);
			layoutelement.setAttribute("id",child_id);
			layoutelement.setAttribute("name",child_id);
			itemmain.appendChild(layoutelement);
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, position, x, y, width, height, layoutelement, itemhead, itembody, doc);
			//ThemeEngine.setCssClassAttribute(layoutelement, cssClassValue);
		}   
		/////////////////////////////////////////////////////////////////////////////FORM   END//////////////////////////////////////////////////////////////////////////////////////


		///////////////////////////////////////////////////////////////////////////////TAB//////////////////////////////////////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("tab"))
		{
			layoutelement=doc.createElement("div");
			Element itemtexlinkhref =doc.createElement("a");
			itemtexlinkhref.setAttribute("id",child_id);
			layoutelement.appendChild(itemtexlinkhref);
			itemmain.appendChild(layoutelement);
			layoutelement.setAttribute("id","tab"+child_id);
			layoutelement.setAttribute("class","c"+child_id); 
			//ThemeEngine.setCssClassAttribute(layoutelement, cssClassValue);
			createContent( layout,content,itemtexlinkhref,child_id,interface_id,doc);
			createBehabiour(layout,behaviour,itemtexlinkhref,child_id,interface_id,addedResources);
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, position, x, y, width, height, layoutelement, itemhead, itembody, doc);
			
		}
		////////////////////////////////////////////////////////////////////////////////TAB END/////////////////////////////////////////////////////////////////////////////////////////

		//////////////////////////////////////////////////////////////////////////////////IMAGE LINK/////////////////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("imagelink"))
		{
			layoutelement=doc.createElement("div");
			Element itemtexlinkhref =doc.createElement("a");
			itemtexlinkhref.setAttribute("id",child_id);
			layoutelement.appendChild(itemtexlinkhref);
			itemmain.appendChild(layoutelement);
			layoutelement.setAttribute("id","imagelink"+child_id);
			layoutelement.setAttribute("class","c"+child_id); 
			Element itemimage=doc.createElement("img");
			itemimage.setAttribute("name",child_id);
			itemimage.setAttribute("border","0");
			itemimage.setAttribute("width",width);
			itemimage.setAttribute("height",height);
			//ThemeEngine.setCssClassAttribute(itemimage, cssClassValue);
			itemtexlinkhref.appendChild(itemimage);
			createContent( layout,content,itemimage,child_id,interface_id,doc);
			createBehabiour(layout,behaviour,itemtexlinkhref,child_id,interface_id,addedResources);
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, position, x, y, width, height, layoutelement, itemhead, itembody, doc);
		}

		//////////////////////////////////////////////////////////////////////////////////IMAGE LINK  END/////////////////////////////////////////////////////////////////////////////////

		//////////////////////////////////////////////////////////////////////////////////IFRAME//////////////////////////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("iframe"))
		{
			layoutelement=doc.createElement("div");
			Element itemframe=doc.createElement("iframe");
			//itemframe.setAttribute("class",classfromThemes);
			itemframe.setAttribute("width",width);
			itemframe.setAttribute("height",height);
			itemframe.setAttribute("frameborder","0");
			itemframe.setAttribute("allowtransparency","true");
			itemframe.setAttribute("scrolling","auto");
			itemframe.setAttribute("background-color","transparent");
			itemframe.setAttribute("name",child_id);
			itemframe.setAttribute("id",child_id);
			layoutelement.setAttribute("id","iframe"+child_id);
			layoutelement.setAttribute("class","c"+child_id);
			layoutelement.appendChild(itemframe);
			itemmain.appendChild(layoutelement);
			//itemframe.setAttribute("style",stylevalue);
			//ThemeEngine.setCssClassAttribute(itemframe, cssClassValue);
			createContent( layout,content,itemframe,child_id,interface_id,doc);
			createBehabiour(layout,behaviour,itemframe,child_id,interface_id,addedResources);
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, "", "", "", width, height, itemframe, itemhead, itembody, doc);
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, position, x, y, width, height, layoutelement, itemhead, itembody, doc);
		}

		///////////////////////////////////////////////////////////////////////////////////IFRAME END/////////////////////////////////////////////////////////////////////////////////

		//////////////////////////////////////////////////////////////////////////////////CHART//////////////////////////////////////////////////////////////////////////////////////////
		if(partclass.equals("chart"))
		{
			layoutelement=doc.createElement("div");
			Element itemframe=doc.createElement("iframe");
			itemframe.setAttribute("width",width);
			itemframe.setAttribute("height",height);
			itemframe.setAttribute("frameborder","0");
			itemframe.setAttribute("allowtransparency","true");
			itemframe.setAttribute("scrolling","auto");
			itemframe.setAttribute("background-color","transparent");
			itemframe.setAttribute("name",child_id);
			itemframe.setAttribute("id",child_id);
			itemframe.setAttribute("src","../servlet/"+chartServletURL+"?IID="+interface_id+"&part_id="+child_id);

			layoutelement.setAttribute("id","chartiframe"+child_id);
			layoutelement.setAttribute("class","c"+child_id);
			layoutelement.appendChild(itemframe);
			itemmain.appendChild(layoutelement);
			//ThemeEngine.setCssClassAttribute(layoutelement, cssClassValue);
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, position, x, y, width, height, layoutelement, itemhead, itembody, doc);
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, null, null, null, null, null, itemframe, itemhead, itembody, doc);

		}

		///////////////////////////////////////////////////////////////////////////////////CHART END/////////////////////////////////////////////////////////////////////////////////


		//////////////////////////////////////////////////////////////////////////////////REPORT//////////////////////////////////////////////////////////////////////////////////////////
		if(partclass.equals("report"))
		{
			layoutelement=doc.createElement("div");
			Element itemframe=doc.createElement("iframe");
			itemframe.setAttribute("width",width);
			itemframe.setAttribute("height",height);
			itemframe.setAttribute("frameborder","0");
			itemframe.setAttribute("allowtransparency","true");
			itemframe.setAttribute("scrolling","auto");
			itemframe.setAttribute("background-color","transparent");
			itemframe.setAttribute("name",child_id);
			itemframe.setAttribute("id",child_id);

			layoutelement.setAttribute("id","reportiframe"+child_id);
			layoutelement.setAttribute("class","c"+child_id);
			layoutelement.appendChild(itemframe);
			itemmain.appendChild(layoutelement);
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, position, x, y, width, height, layoutelement, itemhead, itembody, doc);
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, null, null, null, null, null, itemframe, itemhead, itembody, doc);


			ResourceBundle rb = ResourceBundle.getBundle("portal",Locale.getDefault());      
			String key1= "frameworkreportpath"; 
			String reportpath = rb.getString(key1);

			String dateFormatjs="";
			String default_rptdesign_file_name=NewDataBaseLayer.get_rptdesign_file_name(interface_id,content,child_id);					
			String viewer_type=NewDataBaseLayer.get_viewer_type(interface_id,content,child_id);					
			String report_chooser=NewDataBaseLayer.get_report_chooser(interface_id,content,child_id);					
			Vector parameter_list_vector=NewDataBaseLayer.getParameterList(interface_id,content,child_id);
			String parameter_list="";
			String parameter_list_initialize="";
			String parameter_list_value="";

			if((parameter_list_vector.size())!=0)
			{
				for(int i=0;i<parameter_list_vector.size();i=i+4)
				{
					String name=(String)parameter_list_vector.elementAt(i);
					String value=(String)parameter_list_vector.elementAt(i+1);
					String value_source_item_name=(String)parameter_list_vector.elementAt(i+2);
					String value_type=(String)parameter_list_vector.elementAt(i+3);

					String classtype=NewDataBaseLayer.getClass(interface_id,value);
					String element_value="";
					if(classtype.equals("DBgrid") || classtype.equals("Conditionalgrid"))
					{
						element_value="var parameterval"+i+"=getSelectedRow('"+value+"','"+value_source_item_name+"')";
					}
					else
					{
						if(value_type.equals("function"))
						{
							element_value="var parameterval"+i+"="+value+";";
						}
						else
						{
							element_value="var parameterval"+i+"=getValue(\""+value+"\")";
						}
					}

					if(name.equals("__report"))/////////////FOR GETTING FIRST PARAMETER//////////
					{
						parameter_list=parameter_list+"?"+name+"="+reportpath+"'+parameterval"+i+"+";
					}
					else
					{
						parameter_list=parameter_list+"'&"+name+"='+parameterval"+i+"+";
					}

					parameter_list_value=parameter_list_value+element_value;
					parameter_list_initialize=parameter_list_initialize+"var parameterval"+i+";";	
				}
			}
			String report_chooser_value="";
			if(!default_rptdesign_file_name.equals(""))
			{
				report_chooser=default_rptdesign_file_name;
				report_chooser_value= "\n chooser='"+report_chooser+"';";
			}
			else
			{
				report_chooser_value="chooser=getValue(\""+report_chooser+"\");";
			}

			String generateScriptForReport="";
			if(!report_chooser.equals(""))
			{
				generateScriptForReport="\n var chooser;"+
						"\n function LoadReport(){"+
						report_chooser_value+
						parameter_list_value+
						"\n document.getElementById(\""+child_id+"\").src='../"+reportServletURL+"?__report="+reportpath+"'+chooser+"+parameter_list+"'&__format="+viewer_type+"';"+
						"\n }";
			}
			else
			{

				generateScriptForReport="\n var chooser;"+
						"\n function LoadReport(){"+
						parameter_list_value+
						"\n document.getElementById(\""+child_id+"\").src='../"+reportServletURL+parameter_list+"'&__format="+viewer_type+"';"+
						"\n }";
			}


			Element headelement=null;
			NodeList headelementn=doc.getElementsByTagName("head");
			for( int i=0; i<headelementn.getLength(); i++)
			{
				headelement=(Element)headelementn.item(i);
			}	 
			Element gscript14= doc.createElement("script");
			gscript14.setAttribute("type","text/javascript");
			gscript14.appendChild(doc.createTextNode(dateFormatjs+parameter_list_initialize+generateScriptForReport));  
			headelement.appendChild(gscript14);
			//ThemeEngine.setCssClassAttribute(layoutelement, cssClassValue);
		}

		///////////////////////////////////////////////////////////////////////////////////REPORT END/////////////////////////////////////////////////////////////////////////////////

		//////////////////////////////////////////////////////////////////////////////////REPORTWINDOW//////////////////////////////////////////////////////////////////////////////////////////
		if(partclass.equals("reportwindow"))
		{
			ResourceBundle rb = ResourceBundle.getBundle("portal",Locale.getDefault());      
			String reportkey= "frameworkreportpath"; 
			String reportpath = rb.getString(reportkey);
			System.out.println(".............hhh......................"+reportpath);
			String dateFormatjs="";
			String default_rptdesign_file_name=NewDataBaseLayer.get_rptdesign_file_name(interface_id,content,child_id);					
			String viewer_type=NewDataBaseLayer.get_viewer_type(interface_id,content,child_id);					
			String report_chooser=NewDataBaseLayer.get_report_chooser(interface_id,content,child_id);					
			Vector parameter_list_vector=NewDataBaseLayer.getParameterList(interface_id,content,child_id);
			String parameter_list="";
			String parameter_list_initialize="";
			String parameter_list_value="";

			if((parameter_list_vector.size())!=0)
			{
				for(int i=0;i<parameter_list_vector.size();i=i+4)
				{
					String name=(String)parameter_list_vector.elementAt(i);
					String value=(String)parameter_list_vector.elementAt(i+1);
					String value_source_item_name=(String)parameter_list_vector.elementAt(i+2);
					String value_type=(String)parameter_list_vector.elementAt(i+3);

					String classtype=NewDataBaseLayer.getClass(interface_id,value);
					String element_value="";
					if(classtype.equals("DBgrid") || classtype.equals("Conditionalgrid"))
					{
						element_value="var parameterval"+i+"=getSelectedRow('"+value+"','"+value_source_item_name+"')";
					}
					else
					{
						if(value_type.equals("function"))
						{
							element_value="var parameterval"+i+"="+value+";";
						}
						else
						{
							element_value="var parameterval"+i+"=getValue(\""+value+"\")";
						}
					}

					if(name.equals("__report"))/////////////FOR GETTING FIRST PARAMETER//////////
					{
						parameter_list=parameter_list+"?"+name+"="+reportpath+"'+parameterval"+i+"+";
					}
					else
					{
						parameter_list=parameter_list+"'&"+name+"='+parameterval"+i+"+";
					}

					parameter_list_value=parameter_list_value+element_value;
					parameter_list_initialize=parameter_list_initialize+"var parameterval"+i+";";	
				}
			}
			String report_chooser_value="";
			if(!default_rptdesign_file_name.equals(""))
			{
				report_chooser=default_rptdesign_file_name;
				report_chooser_value= "\n chooser='"+report_chooser+"';";
			}
			else
			{
				report_chooser_value="chooser=getValue(\""+report_chooser+"\");";
			}

			String generateScriptForReport="";
			if(!report_chooser.equals(""))
			{
				generateScriptForReport="\n var chooser;"+
						"\n function LoadReport(){"+
						report_chooser_value+
						parameter_list_value+
						"\n window.open('../"+reportServletURL+"?__report="+reportpath+"'+chooser+"+parameter_list+"'&__format="+viewer_type+"',\""+child_id+"\",\"status=1\");"+
						"\n }";
			}

			else
			{
				generateScriptForReport="\n var chooser;"+
						"\n function LoadReport(){"+
						parameter_list_value+
						"\n window.open('../"+reportServletURL+parameter_list+"'&__format="+viewer_type+"',\""+child_id+"\",\"status=1\");"+
						"\n }";
			}



			Element headelement=null;
			NodeList headelementn=doc.getElementsByTagName("head");
			for( int i=0; i<headelementn.getLength(); i++)
			{
				headelement=(Element)headelementn.item(i);
			}	 
			Element gscript14= doc.createElement("script");
			gscript14.setAttribute("type","text/javascript");
			gscript14.appendChild(doc.createTextNode(dateFormatjs+parameter_list_initialize+generateScriptForReport));  
			headelement.appendChild(gscript14);
			//ThemeEngine.setCssClassAttribute(layoutelement, cssClassValue);
		}

		///////////////////////////////////////////////////////////////////////////////////REPORTWINDOW END/////////////////////////////////////////////////////////////////////////////////



		////////////////////////////////////////////////////////////////////////////////IFRAME CONTAINER///////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("framecontainer"))
		{
			layoutelement=doc.createElement("div");
			Element itemframe=doc.createElement("iframe");
			itemframe.setAttribute("width",width);
			itemframe.setAttribute("height",height);
			itemframe.setAttribute("frameborder","0");
			itemframe.setAttribute("allowtransparency","true");
			itemframe.setAttribute("background-color","transparent");
			itemframe.setAttribute("name",child_id);
			itemframe.setAttribute("id",child_id);
			layoutelement.setAttribute("id","framecontainer"+child_id);
			layoutelement.setAttribute("class","c"+child_id);
			layoutelement.appendChild(itemframe);
			itemmain.appendChild(layoutelement);
			//ThemeEngine.setCssClassAttribute(layoutelement, cssClassValue);
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, position, x, y, width, height, layoutelement, itemhead, itembody, doc);
			createContent( layout,content,itemframe,child_id,interface_id,doc);
			createBehabiour(layout,behaviour,itemframe,child_id,interface_id,addedResources);
		}
		/////////////////////////////////////////////////////////////////////////////////IFRAME CONTAINER END//////////////////////////////////////////////////////////////

		////////////////////////////////////////////////////////////////////////////////////////INPUT TEXT//////////////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("inputtext"))
		{
			layoutelement=doc.createElement("div");
			Element layoutelementtext=doc.createElement("input");
			layoutelementtext.setAttribute("type","text");
			layoutelementtext.setAttribute("tabindex",childtabindex);
			layoutelementtext.setAttribute("name",child_id);

			//layoutelementtext.setAttribute("class",classfromThemes);

			layoutelementtext.setAttribute("maxLength",childmaxlength);
			layoutelementtext.setAttribute("size",childsize);
			//layoutelementtext.setAttribute("style",stylevalue);
			layoutelementtext.setAttribute("id",child_id);
			layoutelement.setAttribute("id","inputtext"+child_id);
			//ThemeEngine.setCssClassAttribute(layoutelementtext, cssClassValue);
			layoutelement.appendChild(layoutelementtext);
			itemmain.appendChild(layoutelement);
			createContent(layout,content,layoutelementtext,child_id,interface_id,doc);
			createBehabiour(layout,behaviour,layoutelementtext,child_id,interface_id,addedResources);
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, null, null, null, null, null, layoutelementtext, itemhead, itembody, doc);
			styleEngine.createStyle(null, null, null, null, null, null, position, x, y, width, height, layoutelement, null, null, null);
		}

		////////////////////////////////////////////////////////////////////////////////////INPUT TEXT  END//////////////////////////////////////////////////////////////////////

		//////////////////////////////////////////////////////////////////////////////////////// SUBMIT BUTTON //////////////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("submitbutton"))
		{

			layoutelement=doc.createElement("div");
			Element layoutelementtext=doc.createElement("input");
			layoutelementtext.setAttribute("type","button");
			layoutelementtext.setAttribute("tabindex",childtabindex);
			layoutelementtext.setAttribute("name",child_id);
			layoutelementtext.setAttribute("id",child_id);
			
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, null, null, null, null, null, layoutelementtext, itemhead, itembody, doc);
			styleEngine.createStyle(null, null, null, null, null, null, position, x, y, width, height, layoutelement, null, null, null);
			
			layoutelement.appendChild(layoutelementtext);
			itemmain.appendChild(layoutelement);
			String formcontrol=NewDataBaseLayer.getFormControl(interface_id,child_id);
			if(formcontrol==null)
				formcontrol="";
			if(formcontrol.equals("custom"))
			{
				createContent(layout,content,layoutelementtext,child_id,interface_id,doc);
				createBehabiour(layout,behaviour,layoutelementtext,child_id,interface_id,addedResources);
			}	
			else
			{	
				String formmethod=NewDataBaseLayer.getFormMethod(interface_id,child_id);
				String formaction=NewDataBaseLayer.getFormAction(interface_id,child_id);
				String formname=NewDataBaseLayer.getParentFormName(interface_id,child_id);
				layoutelementtext.setAttribute("onclick",child_id+"onclick()");

				String mainstring="function "+child_id+"onclick(){"+
						"\n document."+formname+".method="+formmethod+";"+
						"\n document."+formname+".action="+formaction+";"+
						"\n document."+formname+".submit(); }";	
				Element submitscript= doc.createElement("script");
				submitscript.setAttribute("type","text/javascript");
				submitscript.appendChild(doc.createTextNode(mainstring));
				itemhead.appendChild(submitscript);
			}
			//ThemeEngine.setCssClassAttribute(layoutelementtext, cssClassValue);

		}

		////////////////////////////////////////////////////////////////////////////////////SUBMIT BUTTON  END//////////////////////////////////////////////////////////////////////



		////////////////////////////////////////////////////////////////////////////////////////////CHECKBOX///////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("checkbox"))
		{

			layoutelement=doc.createElement("div");
			Element layoutelementtext=doc.createElement("input");
			layoutelementtext.setAttribute("type","checkbox");
			layoutelementtext.setAttribute("tabindex",childtabindex);
			//layoutelementtext.setAttribute("class",classfromThemes);

			layoutelementtext.setAttribute("name",child_id);
			//layoutelementtext.setAttribute("style",stylevalue);
			layoutelementtext.setAttribute("id",child_id);
			layoutelement.setAttribute("id","inputtext"+child_id);
			layoutelement.appendChild(layoutelementtext);
			itemmain.appendChild(layoutelement);
			//ThemeEngine.setCssClassAttribute(layoutelementtext, cssClassValue);
			createContent( layout,content,layoutelementtext,child_id,interface_id,doc);
			createBehabiour(layout,behaviour,layoutelementtext,child_id,interface_id,addedResources);
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, null, null, null, null, null, layoutelementtext, itemhead, itembody, doc);
			styleEngine.createStyle(null, null, null, null, null, null, position, x, y, width, height, layoutelement, null, null, null);
			
		}

		/////////////////////////////////////////////////////////////////////////////////CHECKBOX  END///////////////////////////////////////////////////////////////////

		////////////////////////////////////////////////////////////////////////////////////////////RADIO BUTTON///////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("radio"))
		{
			Element layoutelementtext=null;		
			layoutelement=doc.createElement("div");
			Element layoutelementtable=doc.createElement("table");
			Element layoutelementtr=doc.createElement("tr");
			Element layoutelementtr1=doc.createElement("tr");
			Element layoutelementtd2=doc.createElement("td");
			Vector voption=NewDataBaseLayer.getvoption(child_id,interface_id);
			for(int i=0;i<voption.size();i=i+3)
			{
				String option_id=(String)voption.elementAt(i);
				String option_name=(String)voption.elementAt(i+1);
				String option_value=(String)voption.elementAt(i+2);
				Element layoutelementtd=doc.createElement("td");
				Element layoutelementtd1=doc.createElement("td");
				layoutelementtext=doc.createElement("input");
				//layoutelementtext.setAttribute("class",classfromThemes);

				layoutelementtext.setAttribute("type","radio");
				layoutelementtext.setAttribute("tabindex",childtabindex);
				layoutelementtext.setAttribute("id",option_id);
				layoutelementtext.setAttribute("value",option_value);
				layoutelementtext.setAttribute("name",child_id);
				layoutelementtd1.appendChild(layoutelementtext);
				layoutelementtd.appendChild(doc.createTextNode(option_name));
				layoutelementtr.appendChild(layoutelementtd);
				layoutelementtr.appendChild(layoutelementtd1);

				// layoutelement.appendChild(layoutelementtd);
			}
			layoutelement.setAttribute("id","div"+child_id);
			layoutelement.appendChild(layoutelementtable);
			layoutelementtable.appendChild(layoutelementtr);
			layoutelementtable.appendChild(layoutelementtr1);
			layoutelementtable.appendChild(layoutelementtr1);
			layoutelementtr1.appendChild(layoutelementtd2);
			//layoutelementtd2.appendChild(doc.createTextNode("<div class=\"error\"></div>"));
			itemmain.appendChild(layoutelement);
			createBehabiour(layout,behaviour,layoutelementtext,child_id,interface_id,addedResources);
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, null, null, null, null, null, layoutelementtext, itemhead, itembody, doc);
			styleEngine.createStyle(null, null, null, null, null, null, position, x, y, width, height, layoutelement, null, null, null);
		}

		/////////////////////////////////////////////////////////////////////////////////RADIO BUTTON END///////////////////////////////////////////////////////////////////



		//////////////////////////////////////////////////I///////////////////////////////////NPUT AREA////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("textarea"))
		{
			layoutelement=doc.createElement("div");	
			Element layoutelementtextarea=doc.createElement("textarea");								
			//layoutelementtextarea.setAttribute("type","text");
			layoutelementtextarea.setAttribute("name",child_id);
			// layoutelementtextarea.setAttribute("maxLength",childmaxlength);
			//layoutelementtextarea.setAttribute("class",classfromThemes);

			layoutelementtextarea.setAttribute("size",childsize);
			layoutelementtextarea.setAttribute("cols",cols);
			layoutelementtextarea.setAttribute("rows",rows);	
			layoutelementtextarea.setAttribute("tabindex",childtabindex);			   
			layoutelementtextarea.setAttribute("id",child_id);
			//layoutelementtextarea.setAttribute("style",stylevalue);
			layoutelement.setAttribute("id","textarea"+child_id);  
			layoutelement.appendChild(layoutelementtextarea);
			itemmain.appendChild(layoutelement);
			createContent(layout,content,layoutelementtextarea,child_id,interface_id,doc);
			createBehabiour(layout,behaviour,layoutelementtextarea,child_id,interface_id,addedResources);
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, null, null, null, null, null, layoutelementtextarea, itemhead, itembody, doc);
			styleEngine.createStyle(null, null, null, null, null, null, position, x, y, width, height, layoutelement, null, null, null);
		}

		///////////////////////////////////////////////////////////////////////////////////INPUT AREA END/////////////////////////////////////////////////////////////////////       

		//////////////////////////////////////////////////////////////////////////////////////COMBO////////////////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("combo"))
		{
			layoutelement=doc.createElement("div");	
			Element layoutelementcombo=doc.createElement("select");	
			//layoutelementcombo.setAttribute("class",classfromThemes);	
			layoutelementcombo.setAttribute("name",child_id);
			layoutelementcombo.setAttribute("id",child_id);
			//layoutelementcombo.setAttribute("style",stylevalue);
			layoutelement.appendChild(layoutelementcombo);
			layoutelement.setAttribute("id","combo"+child_id);
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, null, null, null, null, null, layoutelementcombo, itemhead, itembody, doc);
			styleEngine.createStyle(null, null, null, null, null, null, position, x, y, width, height, layoutelement, null, null, null);
			itemmain.appendChild(layoutelement);

			String contenttype=NewDataBaseLayer.getContentType(layout,content,child_id,interface_id);
			String contentvalue=NewDataBaseLayer.getDropDownContentvalue(layout,content,child_id,interface_id);
			Vector choose_one_information=NewDataBaseLayer.getChoose_one_information(content,child_id,interface_id);
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
			System.out.println(".................visible_choose.........."+visible_choose);	 
			if(contenttype.equals("query"))
			{
				Element comboscript= doc.createElement("script");
				comboscript.setAttribute("type","text/javascript");
				String scriptvalue="PortalEngine.GeneratePageDropDownString(\""+contentvalue+"\",dropdown);"+
						"function dropdown(data) {"+
						//" alert (data);"+
						"	 $(\"#"+child_id+"\").html('"+visible_choose+"'+data);"+
						" };";
				comboscript.appendChild(doc.createTextNode(scriptvalue));
				layoutelement.appendChild(comboscript);


			}
			if(contenttype.equalsIgnoreCase("fixed"))
			{
				Element optionelement1=doc.createElement("option");	
				if(!show_choose_one.equals("false"))
				{
					optionelement1.setAttribute("value",choose_one_value);
					optionelement1.appendChild(doc.createTextNode(choose_one_label));
					layoutelementcombo.appendChild(optionelement1);
				}
				Vector  vadd=NewDataBaseLayer.GenerateFixedDropDownString(child_id,interface_id);
				for(int i=0;i<vadd.size();i=i+2)
				{
					String name=(String)vadd.elementAt(i);
					String value=(String)vadd.elementAt(i+1);
					Element optionelement=doc.createElement("option");	
					optionelement.setAttribute("value",name);
					optionelement.appendChild(doc.createTextNode(value));
					layoutelementcombo.appendChild(optionelement);

				}
			}
			createBehabiour(layout,behaviour,layoutelementcombo,child_id,interface_id,addedResources);
		}

		///////////////////////////////////////////////////////////////////////////////////////////////COMBOEND////////////////////////////////////////////////////////////////////////////

		//////////////////////////////////////////////////////////////////////////////////////COMBO////////////////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("combooption"))
		{
			Element layoutelementcombo=doc.createElement("option");		
			layoutelementcombo.setAttribute("name",child_id);
			layoutelementcombo.setAttribute("id",child_id);
			itemmain.appendChild(layoutelementcombo);
			createContent(layout,content,layoutelementcombo,child_id,interface_id,doc);
			createBehabiour(layout,behaviour,layoutelementcombo,child_id,interface_id,addedResources);
		}

		///////////////////////////////////////////////////////////////////////////////////////////////COMBOEND////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////FRAMESET///////////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("frameset"))
		{
			layoutelement=doc.createElement("frameset");
			layoutelement.setAttribute("id",child_id);
			layoutelement.setAttribute("MARGINWIDTH",width);
			layoutelement.setAttribute("MARGINHEIGHT",height);
			layoutelement.setAttribute("LEFTMARGIN",x);
			layoutelement.setAttribute("TOPMARGIN",y);
			layoutelement.setAttribute("FRAMESPACING",spacing);
			layoutelement.setAttribute("BORDER",border);
			layoutelement.setAttribute("ROWS",rows);
			layoutelement.setAttribute("COLS",cols);
			itemmain.appendChild(layoutelement);
			createContent(layout,content,layoutelement,child_id,interface_id,doc);
			createBehabiour(layout,behaviour,layoutelement,child_id,interface_id,addedResources);
		}

		/////////////////////////////////////////////////////////////////////////////////////////////////FRAMESET  END//////////////////////////////////////////////////////////////////

		//////////////////////////////////////////////////////////////////////////////////////////////////FRAME//////////////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("frame"))
		{
			layoutelement=doc.createElement("frame");

			layoutelement.setAttribute("MARGINWIDTH",width);
			layoutelement.setAttribute("MARGINHEIGHT",height);
			layoutelement.setAttribute("LEFTMARGIN",x);
			layoutelement.setAttribute("TOPMARGIN",y);
			layoutelement.setAttribute("FRAMEBORDER",border);
			layoutelement.setAttribute("SCROLLING",scrolling);
			layoutelement.setAttribute("NORESIZE",resize);
			layoutelement.setAttribute("NAME",child_id);
			layoutelement.setAttribute("id",child_id);
			layoutelement.setAttribute("class","c"+child_id);
			createContent( layout,content,layoutelement,child_id,interface_id,doc);
			createBehabiour(layout,behaviour,layoutelement,child_id,interface_id,addedResources);
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////FRAME  END////////////////////////////////////////////////////////////////

		/////////////////////////////////////////////////////////////////////////////////////////////////////INPUT FILE/////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("inputfile"))
		{
			layoutelement=doc.createElement("input");
			layoutelement.setAttribute("type","file");
			layoutelement.setAttribute("name",child_id);
			layoutelement.setAttribute("maxLength",childmaxlength);
			layoutelement.setAttribute("size",childsize);
			//layoutelement.setAttribute("class",classfromThemes);
			layoutelement.setAttribute("id",child_id);
			//layoutelement.setAttribute("style",stylevalue);
			itemmain.appendChild(layoutelement);

			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, position, x, y, width, height, layoutelement, itemhead, itembody, doc);
			
			createContent( layout,content,layoutelement,child_id,interface_id,doc);
			createBehabiour(layout,behaviour,layoutelement,child_id,interface_id,addedResources);
		}
		/////////////////////////////////////////////////////////////////////////////////////////////////////INPUT FILE  END/////////////////////////////////////////////////////////////////

		/////////////////////////////////////////////////////////////////////////////////////////////////////HIDDEN TYPE//////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("hidden"))
		{
			layoutelement=doc.createElement("input");
			layoutelement.setAttribute("type","hidden");
			layoutelement.setAttribute("name",child_id);
			layoutelement.setAttribute("maxLength",childmaxlength);
			layoutelement.setAttribute("size",childsize);
			layoutelement.setAttribute("id",child_id);
			// layoutelement.setAttribute("class","c"+child_id+" "+anotherclassfromdatabase);
			itemmain.appendChild(layoutelement);
			createContent( layout,content,layoutelement,child_id,interface_id,doc);
			createBehabiour(layout,behaviour,layoutelement,child_id,interface_id,addedResources);
		}

		///////////////////////////////////////////////////////////////////////////////////////////////HIDDEN TYPE  END////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////////INPUT PASSWORD///////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("password"))
		{
			Element  elementopassword=doc.createElement("input");
			layoutelement=doc.createElement("div");
			elementopassword.setAttribute("type","password");
			elementopassword.setAttribute("tabindex",childtabindex);
			elementopassword.setAttribute("name",child_id);
			elementopassword.setAttribute("maxLength",childmaxlength);
			elementopassword.setAttribute("size",childsize);
			//elementopassword.setAttribute("class",classfromThemes);
			elementopassword.setAttribute("id",child_id);
			//elementopassword.setAttribute("style",stylevalue);
			layoutelement.setAttribute("id","password"+child_id);
			layoutelement.appendChild(elementopassword);
			itemmain.appendChild(layoutelement);
			createContent( layout,content,elementopassword,child_id,interface_id,doc);
			createBehabiour(layout,behaviour,elementopassword,child_id,interface_id,addedResources);
			
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, null, null, null, null, null, elementopassword, itemhead, itembody, doc);
			styleEngine.createStyle(null, null, null, null, null, null, position, x, y, width, height, layoutelement, null, null, null);
		}

		///////////////////////////////////////////////////////////////////////////////////////////////////////INPUT PASSWORD  END///////////////////////////////////////////////////////////////

		///////////////////////////////////////////////////////////////////////////////////////////////////////INPUT BUTTON///////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("button"))
		{
			layoutelement=doc.createElement("div");	
			Element buttonlayoutelement=doc.createElement("input");
			buttonlayoutelement.setAttribute("type","button");
			buttonlayoutelement.setAttribute("id",child_id);
			// buttonlayoutelement.setAttribute("class","c"+child_id);
			buttonlayoutelement.setAttribute("name",child_id);
			//buttonlayoutelement.setAttribute("style",stylevalue);
			layoutelement.appendChild(buttonlayoutelement);

			//buttonlayoutelement.setAttribute("class",classfromThemes);

			layoutelement.setAttribute("id",child_id+"button");
			itemmain.appendChild(layoutelement);
			String contentvalue=NewDataBaseLayer.getContentvalue(layout,content,child_id,interface_id);
			buttonlayoutelement.setAttribute("value",contentvalue);
			createBehabiour(layout,behaviour,buttonlayoutelement,child_id,interface_id,addedResources);
			
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, null, null, null, null, null, buttonlayoutelement, itemhead, itembody, doc);
			styleEngine.createStyle(null, null, null, null, null, null, position, x, y, width, height, layoutelement, null, null, null);

		}
		///////////////////////////////////////////////////////////////////////////////////////////////////////INPUT BUTTON/  END/////////////////////////////////////////////////////////

		/////////////////////////////////////////////////////////////////////////////////////////////////////////TABLE/////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("table"))
		{
			layoutelement=doc.createElement("table");
			itemmain.appendChild(layoutelement);
			//layoutelement.setAttribute("class",classfromThemes);
			layoutelement.setAttribute("id",child_id);
			/*				 layoutelement.setAttribute("class","c"+child_id);*/
			//layoutelement.setAttribute("style","position:"+position+";left :"+x+"; top:"+y+";width:"+width+"; height:"+height+";");
			createContent( layout,content,layoutelement,child_id,interface_id,doc);
			createBehabiour(layout,behaviour,layoutelement,child_id,interface_id,addedResources);
			
			
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, position, x, y, width, height, layoutelement, itemhead, itembody, doc);
		}

		//////////////////////////////////////////////////////////////////////////////////////////////////////TABLE  END////////////////////////////////////////////////////////////////////////			
		//////////////////////////////////////////////////////////////////////////////////////////////////////TABLE ROW////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("tablerow"))
		{
			layoutelement=doc.createElement("tr");
			itemmain.appendChild(layoutelement);
			//layoutelement.setAttribute("class",classfromThemes);
			layoutelement.setAttribute("id",child_id);
			//layoutelement.setAttribute("class","c"+child_id+" "+anotherclassfromdatabase+"  "+classfromThemes);
			//layoutelement.setAttribute("style","position:"+position+";left :"+x+"; top:"+y+";width:"+width+"; height:"+height+";"+stylevalue);
			createContent( layout,content,layoutelement,child_id,interface_id,doc);
			createBehabiour(layout,behaviour,layoutelement,child_id,interface_id,addedResources);
			
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, position, x, y, width, height, layoutelement, itemhead, itembody, doc);
		}

		//////////////////////////////////////////////////////////////////////////////////////////////////////////TABLE ROW  END////////////////////////////////////////////////////////////

		/////////////////////////////////////////////////////////////////////////////////////////////TABLE COLS////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("tablecell"))
		{

			layoutelement=doc.createElement("td");
			Element tablecelllink=doc.createElement("a");
			layoutelement.appendChild(tablecelllink);
			itemmain.appendChild(layoutelement);
			tablecelllink.setAttribute("id",child_id);
			layoutelement.setAttribute("id",child_id);
			layoutelement.setAttribute("colspan",childcolspan);
			createContent( layout,content,layoutelement,child_id,interface_id,doc);
			createBehabiour(layout,behaviour,layoutelement,child_id,interface_id,addedResources);

		}
		/////////////////////////////////////////////////////////////////////////////////////////////TABLE COLS  END////////////////////////////////////////////////////////////////////////////////////////////////////////

		/////////////////////////////////////////////////////////////////////////////////////////////APPLET////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("applet"))
		{

			layoutelement=doc.createElement("div");
			Element appletelement=doc.createElement("APPLET");
			appletelement.setAttribute("width",width);
			appletelement.setAttribute("height",height);
			appletelement.setAttribute("id",child_id);
			appletelement.setAttribute("NAME",child_id);
			appletelement.setAttribute("archive",childarchieve);
			appletelement.setAttribute("codebase",childcodebase);
			appletelement.setAttribute("mayscript",childmayscript);
			layoutelement.appendChild(appletelement);
			itemmain.appendChild(layoutelement);
			layoutelement.setAttribute("id","applet"+child_id);
			String contentvalue=NewDataBaseLayer.getContentvalue(layout,content,child_id,interface_id);
			appletelement.setAttribute("value",contentvalue);
			String behaviourvalue=NewDataBaseLayer.getBehaviourValue(layout,behaviour,child_id,interface_id);
			appletelement.setAttribute("CODE",behaviourvalue);
			
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, position, x, y, width, height, layoutelement, itemhead, itembody, doc);

		}
		/////////////////////////////////////////////////////////////////////////////////////////////APPLET END////////////////////////////////////////////////////////////////////////////////////////////////////////

		/////////////////////////////////////////////////////////////////////////////////////////////APPLET PARAM////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("appletparam"))
		{

			layoutelement=doc.createElement("PARAM");
			itemmain.appendChild(layoutelement);
			layoutelement.setAttribute("NAME",child_id);
			String contentvalue=NewDataBaseLayer.getContentvalue(layout,content,child_id,interface_id);
			layoutelement.setAttribute("VALUE",contentvalue);

		}
		/////////////////////////////////////////////////////////////////////////////////////////////APPLET PARAM END////////////////////////////////////////////////////////////////////////////////////////////////////////

		/////////////////////////////////////////////////////////////////////////////////////////////LIST////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("list"))
		{

			layoutelement=doc.createElement("ul");
			layoutelement.setAttribute("id",child_id);
			//layoutelement.setAttribute("class",classfromThemes);	
			itemmain.appendChild(layoutelement);
			createContent(layout,content,layoutelement,child_id,interface_id,doc);
			createBehabiour(layout,behaviour,layoutelement,child_id,interface_id,addedResources);
			
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, null, null, null, null, null, layoutelement, itemhead, itembody, doc);
			
			

		}
		/////////////////////////////////////////////////////////////////////////////////////////////LIST END////////////////////////////////////////////////////////////////////////////////////////////////////////

		/////////////////////////////////////////////////////////////////////////////////////////////LIST IETM////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("listitem"))
		{

			layoutelement=doc.createElement("li");
			layoutelement.setAttribute("id",child_id);
			//layoutelement.setAttribute("class",classfromThemes);	
			itemmain.appendChild(layoutelement);
			createContent( layout,content,layoutelement,child_id,interface_id,doc);
			createBehabiour(layout,behaviour,layoutelement,child_id,interface_id,addedResources);
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, null, null, null, null, null, layoutelement, itemhead, itembody, doc);
			

		}
		/////////////////////////////////////////////////////////////////////////////////////////////LIST iTEM END////////////////////////////////////////////////////////////////////////////////////////////////////////

		/////////////////////////////////////////////////////////////////////////////////////////////SPAN////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("span"))
		{

			layoutelement=doc.createElement("span");
			itemmain.appendChild(layoutelement);
			//layoutelement.setAttribute("class",classfromThemes);	
			createContent( layout,content,layoutelement,child_id,interface_id,doc);
			createBehabiour(layout,behaviour,layoutelement,child_id,interface_id,addedResources);
			
			
			
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, null, null, null, null, null, layoutelement, itemhead, itembody, doc);
			

		}
		/////////////////////////////////////////////////////////////////////////////////////////////SPAN END////////////////////////////////////////////////////////////////////////////////////////////////////////

		//////////////////////////////////////////////////////////////////////////////////GRID/////////////////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("DBgrid"))
		{
			GridProperty gridProperty=NewDataBaseLayer.getDBGridStructure(interface_id, child_id);
			gridstring=DBgrid.createLayout(interface_id, child_id, doc, height, layoutelement, itemmain, position, x,y, 
					width,layout, style,themeId,partclass,itemhead, itembody,styleEngine,this.applicationTemplate,gridProperty);	
		}

		////////////////////////////////////////////////////////////CONDITIONAL GRID////////////////////////////////////////////////////////////

		if(partclass.equalsIgnoreCase("Conditionalgrid"))
		{
			GridProperty gridProperty=NewDataBaseLayer.getDBGridStructure(interface_id, child_id);
			gridstring=ConditionalDBgrid.createLayout(interface_id, child_id, doc,itemhead,itembody, height, layoutelement, itemmain, position, x,y,
					width,layout, style,themeId,partclass,styleEngine,this.applicationTemplate,gridProperty);	
		}


		//////////////////////////////////////////////////////////////////////////////////GRID  END/////////////////////////////////////////////////////////////////////////////////

		//////////////////////////////////////////////////////////////////////////////////TREE/////////////////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("tree"))
		{
			layoutelement=doc.createElement("div");
			itemmain.appendChild(layoutelement);
			layoutelement.setAttribute("id",child_id);
			
			
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, position, x, y, width, height, layoutelement, itemhead, itembody, doc);

			String TreeDataRemoteFunction=NewDataBaseLayer.getTreeDataRemoteFunction(interface_id,child_id);
			
			String OnselectRemoteFunction=NewDataBaseLayer.getOnselectRemoteFunction(interface_id,child_id);
			OnselectRemoteFunction=GenericUtil.isEmptyString(OnselectRemoteFunction)?"":"\n  onActivate: function(dtnode) {"+
					"\n  "+OnselectRemoteFunction+"(dtnode);"+
					"\n  }";
			
			String onPostInitFunction=NewDataBaseLayer.getOnPostInitFunction(interface_id,child_id);
			onPostInitFunction=GenericUtil.isEmptyString(onPostInitFunction)?"":"\n  onPostInit: function(isReloading, isError) {" +
					"\n  "+ onPostInitFunction+"(isReloading,isError);"+
					"\n  },";
			
			String AutoCollapse=NewDataBaseLayer.getAutoCollapse(interface_id,child_id);
			String loadinitialize=NewDataBaseLayer.getloadinitialize(interface_id,child_id);
			String checkInterfaceType=NewDataBaseLayer.GetInterfaceType(interface_id);/* This function is return interface type ( Interface or InterfaceFragment ) */

			/*String resource_id=NewDataBaseLayer.getBehaviourresourceID(layout,behaviour,child_id,interface_id);
			Vector js=NewDataBaseLayer.getjs(resource_id,interface_id);
			String jsscript="";
			for(int k=0;k<js.size();k=k+1)
			{
				jsscript = (String)js.elementAt(0);
			}*/

			String load="";
			if(loadinitialize==null || loadinitialize.equals(""))
			{
				loadinitialize="true";
			}
			if(loadinitialize.equals("true"))
			{
				load="GenerateTree();";                                                               
			}     
			treestring=load;                             
			String s="\n  function GenerateTree()"+
					"\n  {"+
					"\n  "+TreeDataRemoteFunction+
					"    (function(data){ "+
					"\n  setValue('"+child_id+"',data);"+
					"\n  $(\"#"+child_id+"\").dynatree({"+
					"\n  title: \"Sample\","+
					"\n  autoCollapse: "+AutoCollapse+","+
					"\n	 "+onPostInitFunction+
					"\n  keyboard: true,"+
					"\n" + OnselectRemoteFunction +
					"\n });}); "+
					"\n  }"+
					"\n function reloadTree(){" +
					"\n  "+TreeDataRemoteFunction+
					"    (function(data){ "+
					"\n  setValue('"+child_id+"',data);"+
					"\n $(\"#"+child_id+"\").dynatree(\"getTree\").reload();"+
					"\n });}";

					/*+

						    "\n   function "+OnselectRemoteFunction+"(dtnode){"+
						    "\n   "+jsscript+
						    "\n   }"*/;

			Element gscript9= doc.createElement("script");
			gscript9.setAttribute("type","text/javascript");
			gscript9.appendChild(doc.createTextNode(s));

			if(checkInterfaceType.equals("InterfaceFragment"))    /* This block is use for InterfaceFragment Check    */
			{
				itembody.appendChild(gscript9);
				Element grdFragementFunction= doc.createElement("script");
				grdFragementFunction.setAttribute("type","text/javascript");
				grdFragementFunction.appendChild(doc.createTextNode("GenerateTree();")); /* Fragment tree function is call under body tag  */
				itembody.appendChild(grdFragementFunction);
			}
			else
			{
				itemhead.appendChild(gscript9);
			}
		}
		////////////////////////////////////////////////////////////////////////////  STATIC TREE      ////////////////////////////////////////////////////////////////////////////////////

		if(partclass.equalsIgnoreCase("statictree"))
		{
			String checkInterfaceType=NewDataBaseLayer.GetInterfaceType(interface_id);/* This function is return interface type ( Interface or InterfaceFragment ) */

			layoutelement=doc.createElement("div");
			itemmain.appendChild(layoutelement);
			layoutelement.setAttribute("id",child_id);
			
			
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, position, x, y, width, height, layoutelement, itemhead, itembody, doc);
			
			Vector getStaticTreeDetails=NewDataBaseLayer.getStaticTreeDetails(interface_id,child_id);
			String remotefuntionname="";
			String loadinitialize="";
			String tooltip="";
			String lazynodecheck="";
			String load="";
			String lazynode="";
			String jsscript="";
			for(int tr=0;tr<getStaticTreeDetails.size();tr=tr+4)
			{
				remotefuntionname=(String)getStaticTreeDetails.elementAt(tr);
				loadinitialize=(String)getStaticTreeDetails.elementAt(tr+1);
				tooltip=(String)getStaticTreeDetails.elementAt(tr+2);
				lazynodecheck=(String)getStaticTreeDetails.elementAt(tr+3);
			}

			String resource_id=NewDataBaseLayer.getBehaviourresourceID(layout,behaviour,child_id,interface_id);
			if((resource_id!=null) || (!resource_id.equals("")))
			{
				Vector js=NewDataBaseLayer.getjs(resource_id,interface_id);
				for(int k=0;k<js.size();k=k+1)
				{
					jsscript = (String)js.elementAt(0);
				}
			}

			if(loadinitialize==null || loadinitialize.equals(""))
			{
				loadinitialize="true";
			}
			if(loadinitialize.equals("true"))
			{
				load="GenerateTree();";                                                               
			}     
			treestring=load;   


			if(lazynodecheck.equalsIgnoreCase("true"))
			{
				lazynode= "\n ,onLazyRead: function(dtnode){"+
						"\n			dtnode.appendAjax("+
						"\n			{url: \"./interfaceenginev2.jsonWriter?interface_id="+interface_id+"&part_id="+child_id+"&islazynode="+lazynodecheck+"&tooltip="+tooltip+"&classname=statictree\","+
						"\n			data: {key: dtnode.data.key"+
						"\n			      }                     "+
						"\n 				});"+
						"\n       }";
			}

			String s="\n  function GenerateTree()"+
					"\n    {"+
					"\n           $(\"#"+child_id+"\").dynatree({"+
					"\n                   initAjax: {url: \"./interfaceenginev2.jsonWriter?interface_id="+interface_id+"&part_id="+child_id+"&islazynode="+lazynodecheck+"&tooltip="+tooltip+"&classname=statictree\","+
					"\n                                data: {key: \"root\"}"+ 
					"\n                             },"+
					"\n                   onActivate: function(dtnode) {"+
					//  "\n                               alert(dtnode.data.key);"+
					"\n                               "+remotefuntionname+"(dtnode);"+
					"\n                               }"+
					lazynode                             +                              
					"\n           });"+
					"\n    }"+

						    "\n   function "+remotefuntionname+"(dtnode){"+
						    "\n   "+jsscript+
						    "\n  }";

			Element gscript9= doc.createElement("script");
			gscript9.setAttribute("type","text/javascript");
			gscript9.appendChild(doc.createTextNode(s));
			if(checkInterfaceType.equals("InterfaceFragment"))    /* This block is use for InterfaceFragment Check    */
			{
				itembody.appendChild(gscript9);
				Element grdFragementFunction= doc.createElement("script");
				grdFragementFunction.setAttribute("type","text/javascript");
				grdFragementFunction.appendChild(doc.createTextNode("GenerateTree();")); /* Fragment tree function is call under body tag  */
				itembody.appendChild(grdFragementFunction);
			}
			else
			{
				itemhead.appendChild(gscript9);
			}

		}
		//////////////////////////////////////////////////////////////////////////// STATIC TREE     ////////////////////////////////////////////////////////////////////////////////////

		//////////////////////////////////////////////////////DYNAMIC TREE START///////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("dynamictree"))
		{
			String checkInterfaceType=NewDataBaseLayer.GetInterfaceType(interface_id);/* This function is return interface type ( Interface or InterfaceFragment ) */

			layoutelement=doc.createElement("div");
			itemmain.appendChild(layoutelement);
			layoutelement.setAttribute("id",child_id);
			
			
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, position, x, y, width, height, layoutelement, itemhead, itembody, doc);
			
			String strDynatree[]=new String[4];

			strDynatree=NewDataBaseLayer.getDynamicTreeDetails(interface_id,child_id);
			String onselectremotefunction=strDynatree[0];
			String initialiseonload=strDynatree[1];
			String tooltip=strDynatree[2];
			String islazynode=strDynatree[3];
			String AutoCollapse=NewDataBaseLayer.getAutoCollapse(interface_id,child_id);

			String load="";
			String lazynode="";
			String jsscript="";

			String resource_id=NewDataBaseLayer.getBehaviourresourceID(layout,behaviour,child_id,interface_id);
			if((resource_id!=null) && (!resource_id.equals("")))
			{
				Vector js=NewDataBaseLayer.getjs(resource_id,interface_id);
				for(int k=0;k<js.size();k=k+1)
				{
					jsscript = (String)js.elementAt(0);
				}
			}

			if(initialiseonload==null || initialiseonload.equals(""))
			{
				initialiseonload="true";
			}
			if(initialiseonload.equals("true"))
			{
				load="GenerateTree();";                                                               
			}     
			treestring=load;   
			System.out.println("islazynode=displayengine=="+islazynode);                         

			if(islazynode.equalsIgnoreCase("true"))
			{
				lazynode= "\n onLazyRead: function(dtnode){"+
						"\n			dtnode.appendAjax("+
						"\n			{url: \"./interfaceenginev2.jsonWriter?interface_id="+interface_id+"&part_id="+child_id+"&islazynode="+islazynode+"&tooltip="+tooltip+"&classname=dynamictree\","+
						"\n			data: {key: dtnode.data.key}"+
						// 							 "\n			}"+
						"\n 				});"+
						"\n     }";
			}
			String s="";
			if(islazynode.equalsIgnoreCase("true"))
			{
				s="\n  function GenerateTree()"+
						"\n    {"+
						"\n           $(\"#"+child_id+"\").dynatree({"+
						"\n   autoCollapse: "+AutoCollapse+","+
						"\n                   initAjax: {url: \"./interfaceenginev2.jsonWriter?interface_id="+interface_id+"&part_id="+child_id+"&islazynode="+islazynode+"&tooltip="+tooltip+"&classname=dynamictree\","+
						"\n                                data: {key: \"root\"}"+ 
						"\n                              },"+
						"\n                   onActivate: function(dtnode) {"+
						// "\n                               alert(dtnode.data.key);"+
						"\n                               "+onselectremotefunction+"(dtnode);"+
						"\n                               },"+
						lazynode                             +                              
						"\n           });"+
						"\n    }"+

							 "\n   function "+onselectremotefunction+"(dtnode){"+
							 "\n   "+jsscript+
							 "\n  }";
			}
			else{
				s="\n  function GenerateTree()"+
						"\n    {"+
						"\n           $(\"#"+child_id+"\").dynatree({"+
						// 						 "\n   autoCollapse: "+AutoCollapse+","+
						"\n                   initAjax: {url: \"./interfaceenginev2.jsonWriter?interface_id="+interface_id+"&part_id="+child_id+"&islazynode="+islazynode+"&tooltip="+tooltip+"&classname=dynamictree\","+
						"\n                                data: {key: \"root\"}"+ 
						"\n                              },"+
						"\n                   onActivate: function(dtnode) {"+
						// "\n                               alert(dtnode.data.key);"+
						"\n                               "+onselectremotefunction+"(dtnode);"+
						"\n                               }"+                            
						"\n           });"+
						"\n    }"+

							 "\n   function "+onselectremotefunction+"(dtnode){"+
							 "\n   "+jsscript+
							 "\n  }";
			}


			System.out.println("s==displayengine="+s);
			Element gscript9= doc.createElement("script");
			gscript9.setAttribute("type","text/javascript");
			gscript9.appendChild(doc.createTextNode(s));
			if(checkInterfaceType.equals("InterfaceFragment"))    /* This block is use for InterfaceFragment Check    */
			{
				itembody.appendChild(gscript9);
				Element grdFragementFunction= doc.createElement("script");
				grdFragementFunction.setAttribute("type","text/javascript");
				grdFragementFunction.appendChild(doc.createTextNode("GenerateTree();")); /* Fragment tree function is call under body tag  */
				itembody.appendChild(grdFragementFunction);
			}
			else
			{
				itemhead.appendChild(gscript9);
			}

		}

		//////////////////////////////////////////////////////DYNAMIC TREE END///////////////////////////////////////////////


		//////////////////////////////////////////////////////////////////////////////AJAXCLASS///////////////////////////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("ajaxcomponent"))
		{
			String javacclass=NewDataBaseLayer.getJavaClass(layout,behaviour,interface_id,child_id);
			Element itemScript=doc.createElement("script");
			itemScript.setAttribute("type","text/javascript");
			String resource_js3="../dwr/interface/"+javacclass+".js";		
			itemScript.setAttribute("src",resource_js3);
			itemhead.appendChild(itemScript);

		}

		//////////////////////////////////////////////////AJAXCLASS///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


		/////////////////////////////////////////////////////////////////////////////FIELDSET////////////////////////////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("fieldset"))
		{  
			layoutelement=doc.createElement("fieldset");
			layoutelement.setAttribute("id",child_id);
			
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, null, null, null, null, null, layoutelement, itemhead, itembody, doc);
			
			
			//layoutelement.setAttribute("class",classfromThemes);	
			itemmain.appendChild(layoutelement);
			//createContent( layout,content,layoutelement,child_id,interface_id,doc);
			//createBehabiour(layout,behaviour,layoutelement,child_id,interface_id);

		}   

		/////////////////////////////////////////////////////////////////////////////FIELDSET////////////////////////////////////////////////////////////////////////////////////////////


		/////////////////////////////////////////////////////////////////////////////legend////////////////////////////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("legend"))
		{  
			layoutelement=doc.createElement("legend");
			layoutelement.setAttribute("id",child_id);
			//layoutelement.setAttribute("class",classfromThemes);	
			//layoutelement.setAttribute("style","position:"+position+";left :"+x+"; top:"+y+";width:"+width+"; height:"+height+";"+stylevalue);

			itemmain.appendChild(layoutelement);
			String contentvalue=NewDataBaseLayer.getContentvalue(layout,content,child_id,interface_id);
			layoutelement.appendChild(doc.createTextNode(contentvalue));
			
			
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, position, x, y, width, height, layoutelement, itemhead, itembody, doc);
		}   

		/////////////////////////////////////////////////////////////////////////////legend////////////////////////////////////////////////////////////////////////////////////////////


		/////////////////////////////////////////////////////////////////////////////legend////////////////////////////////////////////////////////////////////////////////////////////
		if(partclass.equalsIgnoreCase("formlabel"))
		{  
			layoutelement=doc.createElement("label");
			layoutelement.setAttribute("id",child_id);
			//layoutelement.setAttribute("class",classfromThemes);	
			//layoutelement.setAttribute("style","position:"+position+";left :"+x+"; top:"+y+";width:"+width+"; height:"+height+";"+stylevalue);
			String label_for=NewDataBaseLayer.getFormlabel_for(interface_id,child_id);
			layoutelement.setAttribute("for",label_for);

			itemmain.appendChild(layoutelement);
			createContent( layout,content,layoutelement,child_id,interface_id,doc);
			
			
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, position, x, y, width, height, layoutelement, itemhead, itembody, doc);


		}   

		/////////////////////////////////////////////////////////////////////////////legend////////////////////////////////////////////////////////////////////////////////////////////


		//////////////////////////////////////////////////FORM ELEMENT//////////////////////////////////////////

		if(partclass.equalsIgnoreCase("formtext"))
		{
			layoutelement=doc.createElement("div");
			Element layoutelementtext=doc.createElement("input");
			
			//layoutelementtext.setAttribute("class",classfromThemes);	
			String element_type=NewDataBaseLayer.getForm_element_type(interface_id,child_id);

			String requiredcheck=NewDataBaseLayer.requiredcheck(interface_id,child_id);
			if(requiredcheck==null)
			{
				requiredcheck="";
			}

			if(element_type.equalsIgnoreCase("auto"))
			{
				layoutelementtext.setAttribute("type","hidden");
			}
			else
			{
				layoutelementtext.setAttribute("type","text");
			}
			layoutelementtext.setAttribute("tabindex",childtabindex);
			layoutelementtext.setAttribute("name",child_id);
			layoutelementtext.setAttribute("maxLength",childmaxlength);
			layoutelementtext.setAttribute("size",childsize);
			layoutelementtext.setAttribute("id",child_id);
			
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, null, null, null, null, null, layoutelementtext, itemhead, itembody, doc);
			styleEngine.createStyle(null, null, null, null, null, null, position, x, y, width, height, layoutelement, null, null, null);
			
			layoutelement.appendChild(layoutelementtext);
			if(requiredcheck.equals("true"))
			{
				ThemeEngine.setCssStyleAttribute(layoutelementtext, "float:left;");
				//layoutelementtext.setAttribute("style","float:left;");
				Element layoutelementtextdiv=doc.createElement("div");
				layoutelementtextdiv.appendChild(doc.createTextNode("*"));
				layoutelementtextdiv.setAttribute("style","color:red;font-size: 12px;width: 2px; height: 2px;float:left;margin-left: 2px;margin-top:1px;");
				layoutelement.appendChild(layoutelementtextdiv);
			}
			itemmain.appendChild(layoutelement);

		}


		if(partclass.equalsIgnoreCase("formdate"))
		{

			String requiredcheck=NewDataBaseLayer.requiredcheck(interface_id,child_id);
			if(requiredcheck==null)
			{
				requiredcheck="";
			}
			layoutelement=doc.createElement("div");
			Element layoutelementtext=doc.createElement("input");
			//layoutelementtext.setAttribute("class",classfromThemes);	
			layoutelementtext.setAttribute("type","text");
			layoutelementtext.setAttribute("tabindex",childtabindex);
			layoutelementtext.setAttribute("name",child_id);
			layoutelementtext.setAttribute("maxLength",childmaxlength);
			layoutelementtext.setAttribute("size",childsize);
			layoutelementtext.setAttribute("id",child_id);
			layoutelement.appendChild(layoutelementtext);
			
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, null, null, null, null, null, layoutelementtext, itemhead, itembody, doc);
			styleEngine.createStyle(null, null, null, null, null, null, position, x, y, width, height, layoutelement, null, null, null);

			if(requiredcheck.equals("true"))
			{
				ThemeEngine.setCssStyleAttribute(layoutelementtext, "float:left;");
				//layoutelementtext.setAttribute("style","float:left;");
				Element layoutelementtextdiv=doc.createElement("div");
				layoutelementtextdiv.appendChild(doc.createTextNode("*"));
				layoutelementtextdiv.setAttribute("style","color:red;font-size: 12px;width: 2px; height: 2px;float:left;margin-left: 2px;margin-top:1px;");
				layoutelement.appendChild(layoutelementtextdiv);
			}

			itemmain.appendChild(layoutelement);


		}


		if(partclass.equalsIgnoreCase("formemail"))
		{
			layoutelement=doc.createElement("div");
			Element layoutelementtext=doc.createElement("input");
			String element_type=NewDataBaseLayer.getForm_element_type(interface_id,child_id);
			String requiredcheck=NewDataBaseLayer.requiredcheck(interface_id,child_id);
			if(requiredcheck==null)
			{
				requiredcheck="";
			}

			if(element_type.equalsIgnoreCase("auto"))
			{
				layoutelementtext.setAttribute("type","hidden");
			}
			else
			{
				layoutelementtext.setAttribute("type","text");
			}
			layoutelementtext.setAttribute("tabindex",childtabindex);
			layoutelementtext.setAttribute("name",child_id);
			layoutelementtext.setAttribute("maxLength",childmaxlength);
			layoutelementtext.setAttribute("size",childsize);
			layoutelementtext.setAttribute("id",child_id);
			
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, null, null, null, null, null, layoutelementtext, itemhead, itembody, doc);
			styleEngine.createStyle(null, null, null, null, null, null, position, x, y, width, height, layoutelement, null, null, null);
			
			//layoutelementtext.setAttribute("class",classfromThemes);	
			layoutelement.setAttribute("style","position:"+position+";left :"+x+"; top:"+y+";width:"+width+"; height:"+height+";");
			layoutelement.appendChild(layoutelementtext);
			if(requiredcheck.equals("true"))
			{
				//layoutelementtext.setAttribute("style","float:left;");
				ThemeEngine.setCssStyleAttribute(layoutelementtext, "float:left;");
				Element layoutelementtextdiv=doc.createElement("div");
				layoutelementtextdiv.appendChild(doc.createTextNode("*"));
				layoutelementtextdiv.setAttribute("style","color:red;font-size: 12px;width: 2px; height: 2px;float:left;margin-left: 2px;margin-top:1px;");
				layoutelement.appendChild(layoutelementtextdiv);

			}
			itemmain.appendChild(layoutelement);

		}

		if(partclass.equalsIgnoreCase("formnumber"))
		{
			layoutelement=doc.createElement("div");
			Element layoutelementtext=doc.createElement("input");
			String element_type=NewDataBaseLayer.getForm_element_type(interface_id,child_id);

			String requiredcheck=NewDataBaseLayer.requiredcheck(interface_id,child_id);
			if(requiredcheck==null)
			{
				requiredcheck="";
			}

			if(element_type.equalsIgnoreCase("auto"))
			{
				layoutelementtext.setAttribute("type","hidden");
			}
			else
			{
				layoutelementtext.setAttribute("type","text");
			}
			layoutelementtext.setAttribute("tabindex",childtabindex);
			layoutelementtext.setAttribute("name",child_id);
			layoutelementtext.setAttribute("maxLength",childmaxlength);
			layoutelementtext.setAttribute("size",childsize);
			layoutelementtext.setAttribute("id",child_id);
			//layoutelementtext.setAttribute("class",classfromThemes);	
			
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, null, null, null, null, null, layoutelementtext, itemhead, itembody, doc);
			styleEngine.createStyle(null, null, null, null, null, null, position, x, y, width, height, layoutelement, null, null, null);
			
			layoutelement.appendChild(layoutelementtext);
			if(requiredcheck.equals("true"))
			{
				//layoutelementtext.setAttribute("style","float:left;");
				ThemeEngine.setCssStyleAttribute(layoutelementtext, "float:left;");
				Element layoutelementtextdiv=doc.createElement("div");
				layoutelementtextdiv.appendChild(doc.createTextNode("*"));
				layoutelementtextdiv.setAttribute("style","color:red;font-size: 12px;width: 2px; height: 2px;float:left;margin-left: 2px;margin-top:1px;");
				layoutelement.appendChild(layoutelementtextdiv);				
			}
			itemmain.appendChild(layoutelement);

		}

		if(partclass.equalsIgnoreCase("formpassword"))
		{
			layoutelement=doc.createElement("div");
			Element layoutelementtext=doc.createElement("input");
			String element_type=NewDataBaseLayer.getForm_element_type(interface_id,child_id);

			String requiredcheck=NewDataBaseLayer.requiredcheck(interface_id,child_id);
			if(requiredcheck==null)
			{
				requiredcheck="";
			}
			if(element_type.equalsIgnoreCase("auto"))
			{
				layoutelementtext.setAttribute("type","hidden");
			}
			else
			{
				layoutelementtext.setAttribute("type","password");
			}
			layoutelementtext.setAttribute("tabindex",childtabindex);
			layoutelementtext.setAttribute("name",child_id);
			layoutelementtext.setAttribute("maxLength",childmaxlength);
			layoutelementtext.setAttribute("size",childsize);
			layoutelementtext.setAttribute("id",child_id);
			//layoutelementtext.setAttribute("class",classfromThemes);	
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, null, null, null, null, null, layoutelementtext, itemhead, itembody, doc);
			styleEngine.createStyle(null, null, null, null, null, null, position, x, y, width, height, layoutelement, null, null, null);
			
			layoutelement.appendChild(layoutelementtext);
			if(requiredcheck.equals("true"))
			{
				ThemeEngine.setCssStyleAttribute(layoutelementtext, "float:left;");
				//layoutelementtext.setAttribute("style","float:left;");
				Element layoutelementtextdiv=doc.createElement("div");
				layoutelementtextdiv.appendChild(doc.createTextNode("*"));
				layoutelementtextdiv.setAttribute("style","color:red;font-size: 12px;width: 2px; height: 2px;float:left;margin-left: 2px;margin-top:1px;");
				layoutelement.appendChild(layoutelementtextdiv);
			}
			itemmain.appendChild(layoutelement);

		}

		if(partclass.equalsIgnoreCase("DBForm"))
		{
			layoutelement=doc.createElement("form");
			// Element layoutelementtext=doc.createElement("form");
			layoutelement.setAttribute("name",child_id);
			layoutelement.setAttribute("id",child_id);
			//layoutelement.setAttribute("class",classfromThemes);	
			
			styleEngine.createStyle(layout, style, child_id, interface_id, themeId, partclass, null, null, null, null, null, layoutelement, itemhead, itembody, doc);
			
			
			//layoutelement.setAttribute("style","position:"+position+";left :"+x+"; top:"+y+";width:"+width+"; height:"+height+";");
			//layoutelement.appendChild(layoutelementtext);
			itemmain.appendChild(layoutelement);
			/////////////////// FOR INSERT//////////////////////////////////		  
			String arrayElement="var InsertArr=new Array();";
			String elstring="";
			Vector dbform_element=NewDataBaseLayer.getdbform_element(interface_id,child_id);
			for(int i=0;i<dbform_element.size();i=i+2)
			{
				String fr_element =(String)dbform_element.elementAt(i);
				String fr_element_type =(String)dbform_element.elementAt(i+1);
				if(fr_element_type.equalsIgnoreCase("auto"))
				{
				}
				else
				{
					elstring=elstring+"var evalue"+i+"=getValue('"+fr_element+"');InsertArr.push('"+fr_element+"');InsertArr.push(evalue"+i+"); ";
				}
			}

			String add=NewDataBaseLayer.getAddSqlForm(interface_id,child_id);
			System.out.println("..........................ADD..............................."+add);
			if(add==null || add.equals(""))
			{
			}
			else
			{
				String s="function  "+child_id+"_Insert()"+
						"{ "+
						arrayElement+
						elstring+

						"   PortalEngine.Insert('"+interface_id+"','"+child_id+"',InsertArr,InsertData);"+

							     "} "+
							     " function InsertData(data){"+
							     " }";

				String s1="function  "+child_id+"_Insert_with_Callback(F)"+
						"{ "+
						arrayElement+
						elstring+
						"   PortalEngine.Insert('"+interface_id+"','"+child_id+"',InsertArr,F);"+
						"}  ";

				Element scripthead=doc.createElement("script");
				scripthead.setAttribute("type","text/javascript");
				scripthead.appendChild(doc.createTextNode(s+s1));
				itemhead.appendChild(scripthead);

			}
			/////////////////// FOR INSERT//////////////////////////////////

			/////////////////// FOR MODIFY//////////////////////////////////		  
			String arrayElementmod="var ModifytArr=new Array();";
			String elstringmod="";
			Vector dbform_element_modify=NewDataBaseLayer.getdbform_element_modify(interface_id,child_id);

			for(int i=0;i<dbform_element_modify.size();i=i+2)
			{
				String fr_element =(String)dbform_element.elementAt(i);
				//String fr_element_type =(String)dbform_element.elementAt(i+1);
				elstringmod=elstringmod+"var evalue"+i+"=getValue('"+fr_element+"');ModifytArr.push('"+fr_element+"');ModifytArr.push(evalue"+i+"); ";

			}

			String modify=NewDataBaseLayer.getModifySqlForm(interface_id,child_id);
			if(modify==null || modify.equals(""))
			{
			}
			else
			{
				String s="function  "+child_id+"_Modify()"+
						"{ "+
						arrayElementmod+
						elstringmod+
						"   PortalEngine.Modify('"+interface_id+"','"+child_id+"',ModifytArr);"+
						"}";
				String s1="function  "+child_id+"_Modify_with_Callback(F)"+
						"{ "+
						arrayElementmod+
						elstringmod+
						"   PortalEngine.Modify('"+interface_id+"','"+child_id+"',ModifytArr,F);"+
						"}";

				Element scripthead=doc.createElement("script");
				scripthead.setAttribute("type","text/javascript");
				scripthead.appendChild(doc.createTextNode(s+s1));
				itemhead.appendChild(scripthead);
			}
			/////////////////// FOR MODIFY//////////////////////////////////

			/////////////////// FOR SELECT//////////////////////////////////
			String select=NewDataBaseLayer.getSelectSqlForm(interface_id,child_id);

			String elstringsel="";
			String arrayElementsel="var SelectArr=new Array();";
			Vector dbform_element_select=NewDataBaseLayer.getdbform_element_select(interface_id,child_id);

			for(int i=0;i<dbform_element_select.size();i=i+2)
			{
				String fr_element =(String)dbform_element.elementAt(i);
				//String fr_element_type =(String)dbform_element.elementAt(i+1);
				elstringsel=elstringsel+"var evalue"+i+"=getValue('"+fr_element+"');SelectArr.push('"+fr_element+"');SelectArr.push(evalue"+i+"); ";

			}

			if(select==null || select.equals(""))
			{
			}
			else
			{

				String s="var SelecttArr=new Array();"+
						"var ElementArr=new Array();"+

							    "function  getElement(data1)"+
							    "       {"+
							    "       ElementArr=data1;"+
							    "       for(i=0;i<ElementArr.length;i++)"+
							    "			{    "+  
							    "        var xele=document.getElementById(ElementArr[i]);"+
							    "        if(xele!=null){"+
							    "        if(xele.type=='checkbox' && SelecttArr[i]=='true')"+
							    "           {				"+
							    "             xele.checked=true;"+
							    "           }"+
							    "        else "+
							    "           {				"+
							    "               var x=document.getElementsByName(ElementArr[i]);"+
							    "               if(x.length==1)     "+    
							    "               {                   "+          
							    "                   setValue(ElementArr[i],SelecttArr[i]);"+
							    "               }                   "+ 
							    "           }"+
							    "			    }    "+
							    "         else{   "+
							    "               setValue(ElementArr[i],SelecttArr[i]);"+
							    "             }   "+ 
							    "			 }    "+
							    "    }"+


							    "function  setElement(data)"+
							    "       {"+
							    "        SelecttArr=data;"+
							    "        PortalEngine.getFormElement('"+interface_id+"','"+child_id+"',getElement);"+
							    "       }"+
							    "function  "+child_id+"_Select()"+
							    "{ "+
							    arrayElementsel+
							    elstringsel+
							    "   PortalEngine.Select('"+interface_id+"','"+child_id+"',SelectArr,setElement);"+
							    //"   PortalEngine.getFormElement('"+interface_id+"','"+child_id+"',getElement);"+
							    "}";

				Element scripthead=doc.createElement("script");
				scripthead.setAttribute("type","text/javascript");
				scripthead.appendChild(doc.createTextNode(s));
				itemhead.appendChild(scripthead);


			}
			/////////////////// FOR SELECT//////////////////////////////////
		} 



		return layoutelement;

	}

	public  String show() throws IOException, ServletException
	{
		//ResourceBundle rb = ResourceBundle.getBundle("portal",Locale.getDefault());      
		String xmlString ="";
		//String key1= "xml"; 
		//String attachmentname = rb.getString(key1);
		try{
			Transformer trans2 = TransformerFactory.newInstance().newTransformer();
			trans2.setOutputProperty(OutputKeys.INDENT, "yes");
			trans2.setOutputProperty(OutputKeys.METHOD, "html");
			trans2.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,
					"-//W3C//DTD XHTML 1.0 Transitional//EN ");
			trans2.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,
					"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd");

			//DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
			// DOMImplementationLS impl = (DOMImplementationLS)registry.getDOMImplementation("LS");
			//LSSerializer writer = impl.createLSSerializer();

			Element root1 = (Element) doc.createElement("html");
			root1.setAttribute("xmlns","http://www.w3.org/1999/xhtml");
			root1.setAttribute("xml:lang","en");
			root1.setAttribute("lang","en");
			Element headelement=null;
			NodeList headelementn=doc.getElementsByTagName("head");
			for( int i=0; i<headelementn.getLength(); i++)
			{
				headelement=(Element)headelementn.item(i);
			}	 
			//Element bodyelement=null;
			NodeList bodyelementtn=doc.getElementsByTagName("body");
			for( int bo=0; bo<bodyelementtn.getLength(); bo++)
			{
				//bodyelement=(Element)bodyelementtn.item(bo);

			}


			//////////////////////////DATEJS INCLUDE//////////////////
			String date_part_id="";
			String datestring="";
			Vector get_date_part_id=NewDataBaseLayer.getDatePartID(interface_id_name);
			if(get_date_part_id.size()== 0)
			{
				date_part_id="";
			}
			else
			{
				for(int g=0;g<get_date_part_id.size();g=g+2)
				{
					String date_id=(String)get_date_part_id.elementAt(g);
					String date_format=(String)get_date_part_id.elementAt(g+1);
					if(date_format==null || date_format.equals(""))
					{
						date_format="dd/mm/yy";
					}
					date_part_id=date_part_id+"$(\"#"+date_id+"\").datepicker({dateFormat:\""+date_format+"\"});";

				}
				datestring="$(function(){"+date_part_id+"});";	
			}
			//////////////////////////DATEJS INCLUDE  END//////////////////////////////////////

			///////////////////////  FORM VALIDATION/////////////////////////
			Vector getDB_Form_ID=NewDataBaseLayer.getDB_Form_ID(interface_id_name);
			String formvalidation="";
			String validatorobject="";
			if(getDB_Form_ID.size()== 0)
			{
				formvalidation="";
				validatorobject="";
			}
			else
			{
				for(int fr=0;fr<getDB_Form_ID.size();fr++)
				{
					String formid=(String)getDB_Form_ID.elementAt(fr);
					formvalidation=FormValidationJsGenerate(interface_id_name,formid);
					validatorobject=validatorobject+"var validator"+formid+";";
				}
			}	 
			///////////////////////  FORM VALIDATION /////////////////////////

			Element gscript14= doc.createElement("script");
			gscript14.setAttribute("type","text/javascript");
			String domReadyScript=GenericUtil.isEmptyString(dom_ready)?"":dom_ready+";";
			gscript14.appendChild(doc.createTextNode(validatorobject+" jQuery(document).ready(function(){"+domReadyScript+treestring+gridstring+datestring+formvalidation+" })"));  
			headelement.appendChild(gscript14);	
			treestring="";
			gridstring="";

			//OutputStream outfile2 = new  FileOutputStream(attachmentname+"inreface.html");
			//trans2.transform(new DOMSource(doc), new StreamResult(outfile2));
			// outfile2.close();

			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(doc);
			trans2.transform(source, result);
			xmlString = result.getWriter().toString();

			//xmlString = writer.writeToString(doc);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		//System.out.println(".........."+xmlString);
		return xmlString;				 		      		
	}
	public  void setNameDoc(Document doc1)
	{
		doc=doc1;
	}


	public  Document getNameDoc()
	{
		return doc;
	}



	private  void createContent(String layout,String content,Element item,String part_id,String interface_id,Document document)
	{
		String contenttype=NewDataBaseLayer.getContentType(layout,content,part_id,interface_id);
		String contentvalue=NewDataBaseLayer.getContentvalue(layout,content,part_id,interface_id);

		if( contenttype==null || contenttype.equals("") )
		{
			contenttype="";
		}	
		if(contenttype.equalsIgnoreCase("reference"))
		{
			String resourcetype=NewDataBaseLayer.resourcetype(contentvalue,interface_id);
			if(resourcetype==null)
			{
				resourcetype="";
			}
			else if(resourcetype.equals("")) 
			{

			}
			else if(resourcetype.equalsIgnoreCase("image"))
			{
				String imagepth=NewDataBaseLayer.checkimagefrompath(interface_id);
				if(!imagepth.equals(""))
				{
					String getimagename=NewDataBaseLayer.getImageName(contentvalue,interface_id);

					item.setAttribute("src",imagepth+getimagename);
				}
				else
				{
					String resource_image="./interfaceenginev2.ResourceImage?resource_id="+contentvalue+"&interface_id="+interface_id+"";		
					item.setAttribute("src",resource_image);
				}
			}
			else if(resourcetype.equalsIgnoreCase("swf"))
			{
				String resource_ani="./interfaceenginev2.ResourceAnimation?resource_id="+contentvalue+"&interface_id="+interface_id+"";		
				item.setAttribute("src",resource_ani);
			}
			else if(resourcetype.equalsIgnoreCase("wmv"))
			{
				String resource_ani="./interfaceenginev2.ResourceVideo?resource_id="+contentvalue+"&interface_id="+interface_id+"";		
				item.setAttribute("src",resource_ani);
				item.setAttribute("autopaly","true");
			}										

			else
			{
				//System.out.println("Inline COntent...................................");
				String jsscript="";
				Vector js=NewDataBaseLayer.getjs(contentvalue,interface_id);
				for(int k=0;k<js.size();k=k+1)
				{
					jsscript = (String)js.elementAt(0);
				}

				String path="";	
				OutputStream hos1 =null;
				try
				{
					ResourceBundle rb = ResourceBundle.getBundle("portal",Locale.getDefault());      
					//String filename="";
					String key1= "xml"; 
					path= rb.getString(key1);
					hos1 = new FileOutputStream(path+"content.html");
					byte buf1[]=(jsscript).getBytes();
					hos1.write(buf1);
				}
				catch(Exception e){}
				try{
					DOMFragmentParser parser = new DOMFragmentParser();
					parser.setProperty("http://cyberneko.org/html/properties/names/elems","lower");
					parser.setFeature("http://cyberneko.org/html/features/balance-tags/document-fragment",true);
					DocumentFragment fragment = document.createDocumentFragment();
					InputSource ins = new InputSource(new FileInputStream(path+"content.html"));
					parser.parse(ins, fragment);
					Node fragmentednode = fragment.cloneNode(true);
					item.appendChild(fragmentednode);
				}
				catch(org.xml.sax.SAXException se){
					System.out.println("***Error during Fragment Parsing : "+se);
				}
				catch(Exception e){}
			}				
		} 

		if(contenttype.equals("inline"))
		{
			if(contentvalue==null)
			{
				contentvalue="";
			}
			item.appendChild(doc.createTextNode(contentvalue));
		}
		if(contenttype.equals("cdata"))
		{
			if(contentvalue==null)
			{
				contentvalue="";
			}
			String path="";	
			OutputStream hos1 =null;
			try
			{
				ResourceBundle rb = ResourceBundle.getBundle("portal",Locale.getDefault());      
				//String filename="";
				String key1= "xml"; 
				path= rb.getString(key1);
				hos1 = new FileOutputStream(path+"content.html");
				byte buf1[]=(contentvalue).getBytes();
				hos1.write(buf1);
			}
			catch(Exception e){}
			try{
				DOMFragmentParser parser = new DOMFragmentParser();
				parser.setProperty("http://cyberneko.org/html/properties/names/elems","lower");
				parser.setFeature("http://cyberneko.org/html/features/balance-tags/document-fragment",true);
				DocumentFragment fragment = document.createDocumentFragment();
				InputSource ins = new InputSource(new FileInputStream(path+"content.html"));
				parser.parse(ins, fragment);
				Node fragmentednode = fragment.cloneNode(true);
				item.appendChild(fragmentednode);
			}
			catch(org.xml.sax.SAXException se){
				System.out.println("***Error during Fragment Parsing : "+se);
			}
			catch(Exception e){}

		}

	}

	private void createBehabiour(String layout,String behaviour,Element item,String part_id,String interface_id,List<String> addedResources)
	{
		Element headelement=null;
		NodeList headelementn=doc.getElementsByTagName("head");
		for( int i=0; i<headelementn.getLength(); i++)
		{
			headelement=(Element)headelementn.item(i);
		}

		Element bodyelement=null;
		NodeList bodyelementlist=doc.getElementsByTagName("body");
		for( int bo=0; bo<bodyelementlist.getLength(); bo++)
		{
			bodyelement=(Element)bodyelementlist.item(bo);
		}
		Vector behaviourallvector=new Vector();
		behaviourallvector=NewDataBaseLayer.getBehaviourAll(layout,behaviour,part_id,interface_id);
		//List<String> addedResources=new ArrayList<>();
		for(int i=0;i<behaviourallvector.size();i=i+5)
		{
			String behaviourevent=(String)behaviourallvector.elementAt(i);
			String behaviourvaluetype=(String)behaviourallvector.elementAt(i+1);
			String behaviourvalue=(String)behaviourallvector.elementAt(i+2);
			String behaviourtarget=(String)behaviourallvector.elementAt(i+3);
			String resource_id=(String)behaviourallvector.elementAt(i+4);
			String resource_location=NewDataBaseLayer.Getresourcelocation(interface_id,resource_id);
			
			if(resource_location==null)
				resource_location="";
			if(resource_id==null || resource_id.equals(""))
			{
				resource_id="";
			}
			if(behaviourevent==null || behaviourevent.equals(""))
			{
				behaviourevent="";
			}
			if(behaviourvaluetype==null || behaviourvaluetype.equals(""))
			{
				behaviourvaluetype="";
			}

			if(behaviourvalue==null || behaviourvalue.equals(""))
			{
				behaviourvalue="";
			}
			if(behaviourtarget==null || behaviourtarget.equals(""))
			{
				behaviourtarget="";
			}

			if(!resource_id.equals(""))
			{

				String resourcetype=NewDataBaseLayer.resourcetype(resource_id,interface_id);
				if(resourcetype==null || resourcetype.equals(""))
				{
					resourcetype="";
				}
				//System.out.println(".....................QQQQQQQQQQQQQQQQQQQQQQQQ......."+resourcetype);
				if(resourcetype.equalsIgnoreCase("js") && !GenericUtil.inList(resource_id, addedResources))
				{
					String checkInterfaceType=NewDataBaseLayer.GetInterfaceType(interface_id);/* This function is return interface type ( Interface or InterfaceFragment ) */
					if(checkInterfaceType.equals("InterfaceFragment"))
					{
						String vectorcss=NewDataBaseLayer.getcssandjs(resource_id,interface_id);
						if (vectorcss!=null) 
						{
							Element scripthead=doc.createElement("script");
							scripthead.setAttribute("type","text/javascript");
							scripthead.appendChild(doc.createTextNode(vectorcss));
							bodyelement.appendChild(scripthead); 
						}
					}
					else
					{
						String inlinejs=NewDataBaseLayer.checkinlinejs(interface_id);
						if(inlinejs.equalsIgnoreCase("yes"))
						{
							String vectorcss=NewDataBaseLayer.getcssandjs(resource_id,interface_id);
							if (vectorcss!=null) 
							{
								Element scripthead=doc.createElement("script");
								scripthead.setAttribute("type","text/javascript");
								scripthead.appendChild(doc.createTextNode(vectorcss));
								if(resource_location.equalsIgnoreCase("body"))
								{
									bodyelement.appendChild(scripthead); 
								}
								else
								{
									headelement.appendChild(scripthead);
								}
							}
						}
						else
						{
							Element gscript4= doc.createElement("script");
							gscript4.setAttribute("type","text/javascript");
							String resource_js="./interfaceenginev2.ResourceJS?resource_id="+resource_id+"&interface_id="+interface_id+"";		
							gscript4.setAttribute("src",resource_js);
							if(resource_location.equalsIgnoreCase("body"))
							{
								bodyelement.appendChild(gscript4); 
							}
							else
							{
								headelement.appendChild(gscript4);
							}
						}

					}
					addedResources.add(resource_id);
				}

			}

			/*if(behaviourevent.equalsIgnoreCase("onclick"))
			{

				System.out.println(".....................ONCLICK........................");
				if(behaviourvaluetype.equals("reference"))
				{
					String resource_htmlchild="./interfaceenginev2.ResourceHtml?resource_id="+behaviourvalue+"&interface_id="+interface_id+"";	
					item.setAttribute("href",resource_htmlchild);
					item.setAttribute("target",behaviourtarget);
				}	
				if(behaviourvaluetype.equalsIgnoreCase("jsevent"))
				{
					String param=NewDataBaseLayer.getparametervalue(part_id,interface_id);
					if(param==null || param.equals(" "))
						param="";
					item.setAttribute("onclick",behaviourvalue+"("+param+")");
				}

				if(behaviourvaluetype.equalsIgnoreCase("inline"))
				{
					if(!behaviourtarget.equals(""))
					{
						if(behaviourtarget.equals("new"))
						{
							item.setAttribute("href","javascript:"+behaviourvalue+"();");
						}

						else if(behaviourtarget.equalsIgnoreCase("self"))
						{
							item.setAttribute("href",behaviourvalue);
						}	
						else
						{
							item.setAttribute("href",behaviourvalue);
							item.setAttribute("target",behaviourtarget);
						}	

					}
					else
					{
						item.setAttribute(behaviourevent,behaviourvalue+"()");

					}		
				}			
			}

			else if(behaviourevent.equalsIgnoreCase("invokeurl"))
			{
				System.out.println(".....................invokeurl........................");

				if(behaviourvaluetype.equals("reference"))
				{
					String resourcetypechild=NewDataBaseLayer.resourcetype(behaviourvalue,interface_id);
					if(resourcetypechild.equals("html"))
					{
						String resource_htmlchild="./interfaceenginev2.ResourceHtml?resource_id="+behaviourvalue+"&interface_id="+interface_id+"";	
						item.setAttribute("src",resource_htmlchild);
					}
				}

				if(behaviourvaluetype.equalsIgnoreCase("inline"))
				{
					item.setAttribute("src",behaviourvalue);
				}	

			}	
			else
			{
				if(behaviourvaluetype.equalsIgnoreCase("inline"))
				{	
					System.out.println("....................behaviourevent............."+behaviourevent);
					if(!behaviourevent.equals(""))
					{
						item.setAttribute(behaviourevent,behaviourvalue);
					}
				}
			}	*/		
			createBehaviourEvent(behaviourevent, behaviourvaluetype, item, behaviourtarget, behaviourvalue, interface_id, part_id);
		}////////////////End for loop/////////////


	}

	private void createBehaviourEvent(String behaviourevent, String behaviourvaluetype, Element item, String behaviourtarget, String behaviourvalue,
			String interface_id, String part_id) {

		if (behaviourvaluetype.equals("reference")) {
			
			/*
			 * the special onclick is changed to a new event 'openurl'
			 */
			if (behaviourevent.equalsIgnoreCase("openurl")) {

				String resource_htmlchild = "./interfaceenginev2.ResourceHtml?resource_id=" + behaviourvalue + "&interface_id=" + interface_id + "";
				item.setAttribute("href", resource_htmlchild);
				item.setAttribute("target", behaviourtarget);

			} 
			
			else if (behaviourevent.equalsIgnoreCase("invokeurl")) {

				String resourcetypechild = NewDataBaseLayer.resourcetype(behaviourvalue, interface_id);
				if (resourcetypechild.equals("html")) {
					String resource_htmlchild = "./interfaceenginev2.ResourceHtml?resource_id=" + behaviourvalue + "&interface_id=" + interface_id
							+ "";
					item.setAttribute("src", resource_htmlchild);
				}
			}
		} 
		
		else if (behaviourvaluetype.equalsIgnoreCase("jsevent")) {
			
			if (!behaviourevent.equals("")) {
				String param = NewDataBaseLayer.getparametervalue(part_id, interface_id);
				if (param == null || param.equals(" "))
					param = "";
				item.setAttribute(behaviourevent, behaviourvalue + "(" + param + ")");
			}

			
		}
		
		else if (behaviourvaluetype.equalsIgnoreCase("inline")) {
			
			/*
			 * the special onclick is changed to a new event 'openurl'
			 */
			if (behaviourevent.equalsIgnoreCase("openurl")) {

				if (behaviourtarget.equals("new")) {
					item.setAttribute("href", "javascript:" + behaviourvalue + "();");
				}

				else if (behaviourtarget.equalsIgnoreCase("self") || GenericUtil.isEmptyString(behaviourtarget)) {
					item.setAttribute("href", behaviourvalue);
				} else {
					item.setAttribute("href", behaviourvalue);
					item.setAttribute("target", behaviourtarget);
				}


			} 
			
			else if (behaviourevent.equalsIgnoreCase("invokeurl")) {

				item.setAttribute("src", behaviourvalue);

			} 
			
			else if (!behaviourevent.equals("")) {
				String param = NewDataBaseLayer.getparametervalue(part_id, interface_id);
				if (param == null || param.equals(" "))
					param = "";
				item.setAttribute(behaviourevent, behaviourvalue + "(" + param + ")");
			}
			/*else if (!behaviourevent.equals("")) {
				item.setAttribute(behaviourevent, behaviourvalue+ "()");
			}*/
		}
	}

	

	private  void createReferenceBehaviourForRoot(Element itemhead,Element itembody,String interface_id,String rootbehaviourvalue,List<String> addedResources)
	{

		String inlinejs=NewDataBaseLayer.checkinlinejs(interface_id);
		String resource_location=NewDataBaseLayer.Getresourcelocation(rootbehaviourvalue,interface_id);
		if(resource_location==null)
		{
			resource_location="";
		}

		String checkInterfaceType=NewDataBaseLayer.GetInterfaceType(interface_id);/* This function is return interface type ( Interface or InterfaceFragment ) */
		if(checkInterfaceType.equals("InterfaceFragment"))
		{
			String vectorcss=NewDataBaseLayer.getcssandjs(rootbehaviourvalue,interface_id);

			if (vectorcss!=null) 
			{
				System.out.println("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
				Element scripthead=doc.createElement("script");
				scripthead.setAttribute("type","text/javascript");
				scripthead.appendChild(doc.createTextNode(vectorcss));
				itembody.appendChild(scripthead);

			}
		}
		else
		{	 

			if(inlinejs.equals("yes"))
			{
				String vectorcss=NewDataBaseLayer.getcssandjs(rootbehaviourvalue,interface_id);

				if (vectorcss!=null) 
				{
					Element scripthead=doc.createElement("script");
					scripthead.setAttribute("type","text/javascript");
					scripthead.appendChild(doc.createTextNode(vectorcss));
					if(resource_location.equalsIgnoreCase("body"))
					{
						itembody.appendChild(scripthead);
					}
					else
					{
						itemhead.appendChild(scripthead);
					}
				}

			}
			else
			{
				Element headscript = doc.createElement("script");
				headscript.setAttribute("type","text/javascript");
				String resource_js="./interfaceenginev2.ResourceJS?resource_id="+rootbehaviourvalue+"&interface_id="+interface_id+"";		
				headscript.setAttribute("src",resource_js);
				if(resource_location.equalsIgnoreCase("body"))
				{
					itembody.appendChild(headscript);
				}
				else
				{
					itemhead.appendChild(headscript);
				}
				addedResources.add(resource_js);

			}
		}

	}

	private  String FormValidationJsGenerate(String interface_id,String part_id)
	{
		Vector getFormElementForJs=NewDataBaseLayer.getFormElementForJs(interface_id,part_id);

		Vector getFormElementForJsMessage=NewDataBaseLayer.getFormElementForJsMessage(interface_id,part_id);

		String jsscript="";
		if(getFormElementForJs.size()=='0')
		{
			jsscript="";
		}
		else
		{
			String 	addrulesjs="";
			String addmessage="";
			for(int j=0;j<getFormElementForJs.size();j=j+9)
			{
				String element_id=(String)getFormElementForJs.elementAt(j);
				String element_class=(String)getFormElementForJs.elementAt(j+1);
				String forlabel=(String)getFormElementForJs.elementAt(j+2);
				String required=(String)getFormElementForJs.elementAt(j+3);
				String minlength=(String)getFormElementForJs.elementAt(j+4);
				String maxlength=(String)getFormElementForJs.elementAt(j+5);
				String equalto=(String)getFormElementForJs.elementAt(j+6);
				String numbercheck=(String)getFormElementForJs.elementAt(j+7);
				String email=	(String)getFormElementForJs.elementAt(j+8);	
				if(j==(getFormElementForJs.size()-9))
				{		
					addrulesjs=addrulesjs+FormValidationRulesGenerate(element_id,element_class,forlabel,required,minlength,maxlength,equalto,numbercheck,email);
				}
				else
				{
					addrulesjs=addrulesjs+FormValidationRulesGenerate(element_id,element_class,forlabel,required,minlength,maxlength,equalto,numbercheck,email)+",";		 		 		 

				}

			}
			if(getFormElementForJsMessage.size()=='0')
			{
				addmessage="";
			}
			else
			{
				for(int k=0;k<getFormElementForJsMessage.size();k=k+8)
				{
					String element_id=(String)getFormElementForJsMessage.elementAt(k);
					String element_class=(String)getFormElementForJsMessage.elementAt(k+1);
					String requiredmess=(String)getFormElementForJsMessage.elementAt(k+2);
					String minlengthmess=(String)getFormElementForJsMessage.elementAt(k+3);
					String maxlengthmess=(String)getFormElementForJsMessage.elementAt(k+4);
					String equaltomess=(String)getFormElementForJsMessage.elementAt(k+5);
					String numbercheckmess=(String)getFormElementForJsMessage.elementAt(k+6);
					String emailmess=	(String)getFormElementForJsMessage.elementAt(k+7);
					if(k==(getFormElementForJsMessage.size()-8))
					{			
						addmessage=addmessage+FormValidationMessageGenerate(element_id,element_class,requiredmess,minlengthmess,maxlengthmess,equaltomess,numbercheckmess,emailmess);
					}
					else
					{
						addmessage=addmessage+FormValidationMessageGenerate(element_id,element_class,requiredmess,minlengthmess,maxlengthmess,equaltomess,numbercheckmess,emailmess)+",";

					}

				}
			}
			jsscript="validator"+part_id+" = $(\"#"+part_id+"\").validate({"+
					"  rules: {"+
					addrulesjs+
					"         },"+
					"   messages: {"+
					addmessage+
					"         },"+
					"  errorElement: \"div\","+
					" submitHandler: function() {"+
					" }"+	
					" });";	 
		}
		return jsscript;

	}

	private  String FormValidationRulesGenerate(String element_id,String element_class,String forlabel,String required, String minlength,String maxlength,String equalto,String numbercheck,String email)
	{
		String add_rules_string="";
		if(!required.equals(""))
		{
			required="required: "+required;
		}
		if(!required.equals(""))
		{
			email=",email: "+email;
		}

		if(!required.equals("") && !equalto.equals(""))
		{
			equalto=",equalTo: \""+equalto+"\"";
		}




		if(!numbercheck.equals(""))
		{
			numbercheck=",number: "+numbercheck;
		}

		if(!minlength.equals(""))
		{
			minlength=",minlength: "+minlength;
		}

		if(!maxlength.equals(""))
		{
			maxlength=",maxlength: "+maxlength;
		}


		if(element_class.equalsIgnoreCase("hidden"))
		{
			add_rules_string=element_id+": {"+required+"}";
		}
		if(element_class.equalsIgnoreCase("textarea"))
		{
			add_rules_string=element_id+": {"+required+"}";
		}

		if(element_class.equalsIgnoreCase("formdate"))
		{
			add_rules_string=element_id+": {"+required+"}";
		}

		if(element_class.equalsIgnoreCase("radio"))
		{
			add_rules_string=element_id+": {"+required+"}";
		}

		if(element_class.equalsIgnoreCase("checkbox"))
		{
			add_rules_string=element_id+": {"+required+"}";
		}

		if(element_class.equalsIgnoreCase("formtext"))
		{
			add_rules_string=element_id+": {"+required+"}";
		}

		if(element_class.equalsIgnoreCase("combo"))
		{
			add_rules_string=element_id+": {"+required+"}";
		}
		if(element_class.equalsIgnoreCase("formemail"))
		{
			add_rules_string=element_id+": {"+required+email+"}";
		}

		if(element_class.equalsIgnoreCase("formpassword"))
		{
			add_rules_string=element_id+": {"+required+equalto+minlength+"}";
		}

		if(element_class.equalsIgnoreCase("formnumber"))
		{
			add_rules_string=element_id+": {"+required+numbercheck+minlength+maxlength+"}";
		}
		return add_rules_string;
	}



	private  String FormValidationMessageGenerate(String element_id,String element_class,String required, String minlength,String maxlength,String equalto,String numbercheck,String email)
	{
		String add_message_string="";
		if(!required.equals(""))
		{
			required="required: \""+required+"\"";
		}
		if(!required.equals(""))
		{
			email=",email: \""+email+"\"";
		}

		if(!required.equals("") && !equalto.equals(""))
		{
			equalto=",equalTo: \""+equalto+"\"";
		}

		if(!numbercheck.equals(""))
		{
			numbercheck=",number: \""+numbercheck+"\"";
		}

		if(!minlength.equals(""))
		{
			minlength=",minlength: \""+minlength+"\"";
		}

		if(!maxlength.equals(""))
		{
			maxlength=",maxlength: \""+maxlength+"\"";
		}

		if(element_class.equalsIgnoreCase("formtext"))
		{
			add_message_string=element_id+": {"+required+"}";
		}

		if(element_class.equalsIgnoreCase("formpassword"))
		{
			add_message_string=element_id+": {"+required+equalto+minlength+"}";
		}

		if(element_class.equalsIgnoreCase("formdate"))
		{
			add_message_string=element_id+": {"+required+"}";
		}


		if(element_class.equalsIgnoreCase("textarea"))
		{
			add_message_string=element_id+": {"+required+"}";
		}

		if(element_class.equalsIgnoreCase("hidden"))
		{
			add_message_string=element_id+": {"+required+"}";
		}

		if(element_class.equalsIgnoreCase("radio"))
		{
			add_message_string=element_id+": {"+required+"}";
		}

		if(element_class.equalsIgnoreCase("checkbox"))
		{
			add_message_string=element_id+": {"+required+"}";
		}

		if(element_class.equalsIgnoreCase("combo"))
		{
			add_message_string=element_id+": {"+required+"}";
		}


		if(element_class.equalsIgnoreCase("formemail"))
		{
			add_message_string=element_id+": {"+required+email+"}";
		}


		if(element_class.equalsIgnoreCase("formnumber"))
		{
			add_message_string=element_id+": {"+required+numbercheck+minlength+maxlength+"}";
		}
		return add_message_string;
	}


	public static  String  getApplicationDefaultValue(String interface_id,String classtype,String attribute_name)
	{
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
		String default_value=NewDataBaseLayer.getApplicationDefaultValue(template_id,classtype,attribute_name);
		return default_value;
	}




	/************************** End of Partha on 01.12.2008 ***********************/
}