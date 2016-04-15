
package interfaceenginev2;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
//import  org.apache.xml.serialize.Serializer;

public class Part
{
private static int cols=0;
private static String part_id="";
private static Document doc2=null;
private static int vecsize=0;

Part(String partid)
{
part_id=partid;	

}

public static Element getElement(Document doc,String child_id,String colsfromdisplay)
{
	Element itemtable=doc.createElement("table");
	itemtable.setAttribute("id",child_id);
	Element headrow=doc.createElement("tr");
       	headrow.setAttribute("id","GridHeadRow"); 
       	itemtable.appendChild(headrow);	   
	cols=Integer.parseInt(colsfromdisplay);
	doc2=doc;
	
	return itemtable;
}


public static void generateGrid(Vector s)
{
	
			 Element e=null;
			 NodeList item=doc2.getElementsByTagName("table");
	                 for( int i=0; i<item.getLength(); i++)
              			 {
              			 	Element x=(Element)item.item(i);
              			 	String y = x.getAttribute("id");
              			 	if(y.equals(part_id))
              			 	{
              			 	e=x;
              			 	break;	
              			 		
              			 	}
	                          }
	                          
	                        
	 int count=0;
	 System.out.println("============s.size========"+s.size());
	 for(int i=0;i<s.size();i=i+cols*3)
	 {
	 
	 Element itemrow=doc2.createElement("tr");
	 if(count%2==0)
	 {
	 	itemrow.setAttribute("class","EvenRow");
	 }	
	 else
	 {
	 	itemrow.setAttribute("class","OddRow");
	 }
	 
	 
	 for(int j=0;j<cols*3;j=j+3)
	 {
	 
	 Element itemcol=doc2.createElement("td");
	 itemcol.setAttribute("id","Row"+count+"Col"+(j/3));
	 itemcol.setAttribute("class","Row"+count+"Col"+(j/3));
	 itemcol.setAttribute("width","150px");
	 itemcol.setAttribute("style","font-size:11px;font-weight:normal;font-family:verdana;text-align:center;");
	 
	 //for(int k=i+j;k<s.size();k=k+3)
	 //{
	   String type=(String)s.elementAt(i+j);	
	   String check=(String)s.elementAt(i+j+1);	
	   String value=(String)s.elementAt(i+j+2);	
	   if(value==null)
		 value="";	
	  
	   if(type.equals("radio"))
	   {
	    Element e1=doc2.createElement("input");
	    e1.setAttribute("type","radio");
	    e1.setAttribute("name","Col"+(j/3));
	    e1.setAttribute("id","Row"+count+"Col"+(j/3));
	    e1.setAttribute("value",value);
	    if(check.equals("true"))
	    e1.setAttribute("checked","checked");
	    
	    itemcol.appendChild(e1);	
	   }	
	   
	   if(type.equals("checkbox"))
	   {
	   Element e1=doc2.createElement("input");
	    e1.setAttribute("type","checkbox");
	    e1.setAttribute("name","Row"+count+"Col"+(j/3));
	    e1.setAttribute("id","Row"+count+"Col"+(j/3));
	    e1.setAttribute("value",value);
	    if(check.equals("true"))
	    e1.setAttribute("checked","checked");
	   
	    itemcol.appendChild(e1);	
	   }
	   
	   if(type.equals("text"))
	   {
	      itemcol.appendChild(doc2.createTextNode(value));
	   }	
	 //}
	 		
	 itemrow.appendChild(itemcol);
	 //col_count++;
	 }
	 count++;
	 e.setAttribute("cellpadding","2");
	 e.setAttribute("cellspacing","1");
	 e.appendChild(itemrow);	
	}
	
	
}


/*public static void generateGridHead(Vector s)
{
	
			 Element e=null;
			 NodeList item=doc2.getElementsByTagName("table");
	                 for( int i=0; i<item.getLength(); i++)
              			 {
              			 	Element x=(Element)item.item(i);
              			 	String y = x.getAttribute("id");
              			 	if(y.equals(part_id))
              			 	{
              			 	e=x;
              			 	break;	
              			 		
              			 	}
	                          }
	 int counthead=0;
	 for(int i=0;i<s.size();i=i+cols)
	 {
	 
	 Element itemrow=doc2.createElement("tr");
	
	 
	 for(int j=0;j<cols;j++)
	 {
	 String s1 = (String)s.elementAt(i+j);	
	 Element itemcol=doc2.createElement("td");
	 itemcol.setAttribute("id","Col"+j);
	 itemcol.setAttribute("class","ColHead");
	 itemcol.setAttribute("style","font-size:11px;font-weight:bold;font-family:verdana;text-align:center;");
	 itemcol.setAttribute("width","150px");	
	 if(s1==null)
	 s1="";	
	 itemcol.appendChild(doc2.createTextNode(s1));
	 itemrow.appendChild(itemcol);
	 }
	 counthead++;
	 e.appendChild(itemrow);	
	}
	
	
}*/


public static void setStyle(String colname,String property,String value)
{
	NodeList itemhead=doc2.getElementsByTagName("style");
	                 	for( int i=0; i<itemhead.getLength(); i++)
              			 {
              	 		 	Element x=(Element)itemhead.item(i);
					x.appendChild(doc2.createTextNode("."+colname+"{"+property+":"+value+";}"));
				 }
}


public static void setOnclick(String colname,String ref)
{
	NodeList td=doc2.getElementsByTagName("td");
	                 	for( int i=0; i<td.getLength(); i++)
              			 {
              			            Element x=(Element)td.item(i);
              			 	    String y = x.getAttribute("id");
              	 		 	    if( y.equals(colname)){
		  		        	String value = x.getFirstChild().getNodeValue();
		  		        	
		  		        	
		  		        	Text text1 = (Text)x.getFirstChild();
		  		        	x.removeChild(text1);
		  		          	Element linkref=doc2.createElement("a");
		  		           	linkref.setAttribute("href","javascript:"+ref+";");
		  		           	linkref.appendChild(doc2.createTextNode(value));	
		  		           	x.appendChild(linkref);	
       						}
				 }
}



public static void setLayout(String colname,String property,String value)
{
	NodeList itemhead=doc2.getElementsByTagName("td");
	                 	for( int i=0; i<itemhead.getLength(); i++)
              			 {
              	 		 	Element x=(Element)itemhead.item(i);
					 String y = x.getAttribute("id");
              	 		 	    if( y.equals(colname)){
              	 		 	    	
              	 		 	    	x.setAttribute(property,value);
              	 		 	        }
				 }
}


}