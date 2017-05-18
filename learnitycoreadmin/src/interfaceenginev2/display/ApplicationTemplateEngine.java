package interfaceenginev2.display;

import interfaceenginev2.NewDataBaseLayer;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import comv2.aunwesha.lfutil.GenericUtil;
import comv2.aunwesha.lfutil.Pair;

public class ApplicationTemplateEngine {
	
	public static  Pair<String, String> retrieveTemplateIdAndComment(String interface_id_name){
		String templateComment=null;
		String templateexist=NewDataBaseLayer.templateexist(interface_id_name);
		String template_id="";

		String templateIdPresnt="";
		String defaultTemplateName="";
		if(GenericUtil.hasString(templateexist)){
			template_id=NewDataBaseLayer.gettemplate_id(templateexist);
			templateIdPresnt=template_id;
		}

		if(template_id==null || template_id.equals("")){
			List<String> returnValues = NewDataBaseLayer.get_default_template_id();
			if(GenericUtil.hasListData(returnValues)){
				template_id=returnValues.get(0);
				defaultTemplateName=returnValues.get(1);
			}

		}

		if(GenericUtil.isEmptyString(templateexist) && GenericUtil.isEmptyString(defaultTemplateName)){
			templateComment="No template is mentioned. Default template is not present.";
		}
		else if(GenericUtil.hasString(templateexist) && GenericUtil.isEmptyString(templateIdPresnt) && GenericUtil.isEmptyString(defaultTemplateName)){
			templateComment="Template is mentioned. But that template is not present. Default template is also not present.";
		}
		else if(GenericUtil.isEmptyString(templateexist) && GenericUtil.hasString(defaultTemplateName)){
			templateComment="No template is mentioned. Default template is used. Template Name - "+defaultTemplateName;
		}else if(GenericUtil.hasString(templateexist)  && GenericUtil.isEmptyString(templateIdPresnt) && GenericUtil.hasString(defaultTemplateName)){
			templateComment="Template is mentioned. But that template is not present. Default template is used. Template Name - "+defaultTemplateName;
		}else if(GenericUtil.hasString(templateexist)  && GenericUtil.hasString(templateIdPresnt)){
			templateComment="Template is mentioned. Template is present. Template Name - "+templateexist;
		}else{
			templateComment="Template error case!! Template Exist = "+templateexist+", Template_id = "+template_id+", Template Id Presnt = "+templateIdPresnt+
					", Default Template Name = "+defaultTemplateName ;
		}
		
		Pair<String, String> returnPair=new Pair<String, String>(template_id, templateComment);
		return returnPair;
	}
	public static  void interfaceResourceWithTemplate(String template_Id,Element itemhtml,Element itemhead,Element bodyelement,Document doc)
			throws IOException, ServletException{
		generateResourceWithTemplate(itemhtml,itemhead,bodyelement,template_Id,doc);
		
	}



	private static void generateResourceWithTemplate(Element itemhtml,Element itemhead,Element bodyelement,String template_id,Document doc)
			throws IOException, ServletException{

		Vector<String> TemplateAssestDetails=NewDataBaseLayer.getTemplateAssestDetails(template_id);
		
		for(int te=0;te<TemplateAssestDetails.size();te=te+5)
		{
			String asset_type=TemplateAssestDetails.elementAt(te);
			String file_name=TemplateAssestDetails.elementAt(te+1);
			String location=TemplateAssestDetails.elementAt(te+2);
			String delivery_mode=TemplateAssestDetails.elementAt(te+3);
			String asset_path=TemplateAssestDetails.elementAt(te+4);
			if(delivery_mode==null)
			{
				delivery_mode="";
			}
			if(asset_type.equalsIgnoreCase("js"))
			{
				Element gscript19= doc.createElement("script");
				gscript19.setAttribute("type","text/javascript");
				if(location.equalsIgnoreCase("EOB"))
				{
					setFrameworkAssestJS(bodyelement,gscript19,delivery_mode,asset_path,file_name,template_id,doc);
				}
				if(location.equalsIgnoreCase("BOB"))
				{
					Element childbody=(Element)bodyelement.getFirstChild();
					setFrameworkAssestJSWithStartLocation(bodyelement,childbody,gscript19,delivery_mode,asset_path,file_name,template_id,doc);
				}
				if(location.equalsIgnoreCase("EOH"))
				{
					setFrameworkAssestJS(itemhead,gscript19,delivery_mode,asset_path,file_name,template_id,doc);

				}
				if(location.equalsIgnoreCase("BOH"))
				{
					Element childhead=(Element)itemhead.getFirstChild();
					setFrameworkAssestJSWithStartLocation(itemhead,childhead,gscript19,delivery_mode,asset_path,file_name,template_id,doc);
				}

			}
			if(asset_type.equalsIgnoreCase("css"))
			{
				Element   link15= doc.createElement("link");
				link15.setAttribute("type","text/css");
				link15.setAttribute("rel","stylesheet");

				Element stylehead=doc.createElement("style");
				stylehead.setAttribute("type","text/css");

				if(location.equalsIgnoreCase("EOB"))
				{
					setFrameworkAssestCSS(bodyelement,link15,stylehead,delivery_mode,asset_path,file_name,template_id,doc);

				}
				if(location.equalsIgnoreCase("BOB"))
				{
					Element childbody=(Element)bodyelement.getFirstChild();
					setFrameworkAssestCSSWithStartLocation(bodyelement,childbody,link15,stylehead,delivery_mode,asset_path,file_name,template_id,doc);

				}

				if(location.equalsIgnoreCase("EOH"))
				{
					setFrameworkAssestCSS(itemhead,link15,stylehead,delivery_mode,asset_path,file_name,template_id,doc);

				}
				if(location.equalsIgnoreCase("BOH"))
				{
					Element childhead=(Element)itemhead.getFirstChild();
					setFrameworkAssestCSSWithStartLocation(itemhead,childhead,link15,stylehead,delivery_mode,asset_path,file_name,template_id,doc);

				}
			}

		}
	}

//	private static  void setFrameworkAssestCSS(Element item,Element link15,Element stylehead,String delivery_mode ,String asset_path,String file_name,Document doc)
	private static  void setFrameworkAssestCSS(Element item,Element link15,Element stylehead,String delivery_mode ,String asset_path,String file_name,String tid,Document doc)	
			throws IOException, ServletException{									
		if(delivery_mode.equals("Dynamic"))
		{
		//	link15.setAttribute("href","./interfaceenginev2.AssetCss?file_name="+file_name);
			link15.setAttribute("href","./interfaceenginev2.AssetCss?template_id="+tid+" &file_name="+file_name);
			item.appendChild(link15);
		}
		if(delivery_mode.equals("Inline"))
		{
			//String fileString=NewDataBaseLayer.getInlineFrameworkAssetFile(file_name);
			String fileString=NewDataBaseLayer.getInlineFrameworkAssetFile(file_name,tid);
			stylehead.appendChild(doc.createTextNode(fileString));
			item.appendChild(stylehead);
		}
		if(delivery_mode.equals("URIPath")||delivery_mode.equals("CDN"))
		{
			link15.setAttribute("href",asset_path+file_name);
			//link15.setAttribute("href",asset_path+file_name+tid);
			item.appendChild(link15);
		}						


	}
	//private static  void setFrameworkAssestCSSWithStartLocation(Element item,Element childelement,Element link15,Element stylehead,String delivery_mode ,String asset_path,String file_name,Document doc)
	private static  void setFrameworkAssestCSSWithStartLocation(Element item,Element childelement,Element link15,Element stylehead,String delivery_mode ,String asset_path,String file_name,String tid,Document doc)
			throws IOException, ServletException{									
		if(delivery_mode.equals("Dynamic"))
		{
			//link15.setAttribute("href","./interfaceenginev2.AssetCss?file_name="+file_name);
			link15.setAttribute("href","./interfaceenginev2.AssetCss?template_id="+tid+" &file_name="+file_name);
			item.insertBefore(link15, childelement); 
		}
		if(delivery_mode.equals("Inline"))
		{
			//String fileString=NewDataBaseLayer.getInlineFrameworkAssetFile(file_name);
			//String fileString=NewDataBaseLayer.getInlineFrameworkAssetFile(file_name);
			String fileString=NewDataBaseLayer.getInlineFrameworkAssetFile(file_name,tid);
			stylehead.appendChild(doc.createTextNode(fileString));
			item.insertBefore(stylehead, childelement); 
		}
		if(delivery_mode.equals("URIPath")||delivery_mode.equals("CDN"))
		{
			link15.setAttribute("href",asset_path+file_name);
			//link15.setAttribute("href",asset_path+file_name+tid);
			item.insertBefore(link15, childelement); 

		}						
	}

	//private static  void setFrameworkAssestJSWithStartLocation(Element item,Element childelement,Element gscript19,String delivery_mode ,String asset_path,String file_name,Document doc)
	private static  void setFrameworkAssestJSWithStartLocation(Element item,Element childelement,Element gscript19,String delivery_mode ,String asset_path,String file_name,String tid,Document doc)
			throws IOException, ServletException{									
		if(delivery_mode.equals("Dynamic"))
		{
			//gscript19.setAttribute("src","./interfaceenginev2.AssetJs?file_name="+file_name);
			gscript19.setAttribute("src","./interfaceenginev2.AssetJs?template_id="+tid+" &file_name="+file_name);
			item.insertBefore(gscript19, childelement); 
		}
		if(delivery_mode.equals("Inline"))
		{
			//String fileString=NewDataBaseLayer.getInlineFrameworkAssetFile(file_name);
			String fileString=NewDataBaseLayer.getInlineFrameworkAssetFile(file_name,tid);
			gscript19.appendChild(doc.createTextNode(fileString));
			item.insertBefore(gscript19, childelement); 
		}
		if(delivery_mode.equals("URIPath")||delivery_mode.equals("CDN"))
		{
			gscript19.setAttribute("src",asset_path+file_name);
			//gscript19.setAttribute("src",asset_path+file_name+tid);
			item.insertBefore(gscript19, childelement); 
		}
	}

//	private static  void setFrameworkAssestJS(Element item,Element gscript19,String delivery_mode ,String asset_path,String file_name,Document doc)
	private static  void setFrameworkAssestJS(Element item,Element gscript19,String delivery_mode ,String asset_path,String file_name,String tid,Document doc)	
			throws IOException, ServletException{									
		if(delivery_mode.equals("Dynamic"))
		{
			//gscript19.setAttribute("src","./interfaceenginev2.AssetJs?file_name="+file_name);
			gscript19.setAttribute("src","./interfaceenginev2.AssetJs?template_id="+tid+" &file_name="+file_name);
			item.appendChild(gscript19);
		}
		if(delivery_mode.equals("Inline"))
		{
			//String fileString=NewDataBaseLayer.getInlineFrameworkAssetFile(file_name);
			String fileString=NewDataBaseLayer.getInlineFrameworkAssetFile(file_name,tid);
			gscript19.appendChild(doc.createTextNode(fileString));
		}
		if(delivery_mode.equals("URIPath")||delivery_mode.equals("CDN"))
		{
			gscript19.setAttribute("src",asset_path+file_name);
			//gscript19.setAttribute("src",asset_path+file_name+tid);
			item.appendChild(gscript19);
		}



	}
}
