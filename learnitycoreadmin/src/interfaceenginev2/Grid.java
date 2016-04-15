
package interfaceenginev2;
import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
//import com.aunwesha.JSPGrid.*;
//import  org.apache.xml.serialize.Serializer;

public class Grid extends HttpServlet
{
//private static int cols=0;
private static String part_id="";
private static Document doc2=null;
private static int vecsize=0;

Grid(String partid)
{
part_id=partid;	

}


public void doGet(HttpServletRequest request, HttpServletResponse res)
	throws IOException, ServletException
	 {
	 
               
	 }
	
 public void doPost(HttpServletRequest request, HttpServletResponse response)
        	throws IOException, ServletException {

		doGet(request, response);
	}
	

public static Element getElement(Document doc,String child_id)
{
	Element itemtable=doc.createElement("table");
	itemtable.setAttribute("id",child_id);
	
	//cols=Integer.parseInt(colsfromdisplay);
	doc2=doc;
	
	return itemtable;
}


public static void generateGrid(Vector s)
{
		/*	 Element e=null;
			 NodeList item=doc2.getElementsByTagName("table");
	                 for( int l=0; l<item.getLength(); l++)
              			 {
              			 	Element x=(Element)item.item(l);
              			 	String y = x.getAttribute("id");
              			 	if(y.equals(part_id))
              			 	{
              			 	       String colfrompart_id=NewDataBaseLayer.getCols(part_id);
						int cols=Integer.parseInt(colfrompart_id);                      
						int count=0;
                                                int nrows=(s.size()/(cols*3));
						String noofrows=Integer.toString(nrows);
		
					for(int i=0;i<s.size();i=i+cols*3)
					{
	 
						Element itemrow=doc2.createElement("tr");
						if(count%2==0)
						{
							itemrow.setAttribute("class","EvenRow");
							itemrow.setAttribute("onmouseout","this.className='evenout';");
						}	
						else
						{
							itemrow.setAttribute("class","OddRow");
							itemrow.setAttribute("onmouseout","this.className='oddout';");
						}
						itemrow.setAttribute("onmouseover","this.className='hover';");
	 
						for(int j=0;j<cols*3;j=j+3)
						{
	 
							Element itemcol=doc2.createElement("td");
							itemcol.setAttribute("id",y+"Row"+count+"Col"+(j/3));
							itemcol.setAttribute("class",y+"Row"+count+"Col"+(j/3));
							itemcol.setAttribute("width","150px");
							itemcol.setAttribute("style","font-size:11px;font-weight:normal;font-family:century gothic;text-align:center;");
	 
						
							String type=(String)s.elementAt(i+j);	
							String check=(String)s.elementAt(i+j+1);	
							String value=(String)s.elementAt(i+j+2);	
							if(value==null)
								value="";	
							
							if(type.equals("radio"))
							{
							Element e1=doc2.createElement("input");
							e1.setAttribute("type","radio");
							e1.setAttribute("name",part_id+"Col"+(j/3));
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
							itemrow.appendChild(itemcol);
						}
							count++;
							x.setAttribute("cellpadding","2");
							x.setAttribute("cellspacing","1");
							x.appendChild(itemrow);	
                                                   
					   }
              			 		        Element noofrow=doc2.createElement("tr");
                                                        Element noofrowcol=doc2.createElement("td");
                                                        noofrowcol.appendChild(doc2.createTextNode("No of Records :"+noofrows));
                                                        noofrow.appendChild(noofrowcol);
                                                        x.appendChild(noofrow);
              			 	}
	                          }
	                          
	
	*/

	generateDataGrid(s);
}


public static void generateDataGrid( Vector s)
{
              
                                                String colfrompart_id=NewDataBaseLayer.getCols(part_id);
						int cols=Integer.parseInt(colfrompart_id);                      
						int count=0;
                                                int page=1;
                                                int max_page=1;
                                                int nrows=(s.size()/(cols*3));
                                               // max_page=(nrows/2);        ////SET  PAGE 2 means how much row 
                                              
                                                String max_pages=Integer.toString(max_page);
						String noofrows=Integer.toString(nrows);
		                                NodeList itemtable=doc2.getElementsByTagName("table");
                                                for( int l=0; l<itemtable.getLength(); l++)
							{
									Element xtable=(Element)itemtable.item(l);
									String y = xtable.getAttribute("id");
									if(y.equals(part_id))
										{
											   Element xtablerow=doc2.createElement("tr");
											   Element xtablerowmessage=doc2.createElement("tr");
											   Element xtablerowcol=doc2.createElement("td");
											   xtablerow.appendChild(xtablerowcol);	
											   Element xtablerowcolmess=doc2.createElement("td");
											   xtablerowmessage.appendChild(xtablerowcolmess);
											  // xtablerowmessage.appendChild(doc2.createTextNode("No of Records :"+noofrows+"  Page   :"+max_pages));
											    xtablerowmessage.appendChild(doc2.createTextNode("No of Records :"+noofrows));
                                                                                           xtable.appendChild(xtablerow);
											   xtable.appendChild(xtablerowmessage);
											   
										           ////////////////////////////////////////   ADD TO GRID DATA///////////
													 Element xtablegrid=doc2.createElement("table");
                                                                                                         xtablerow.appendChild(xtablegrid);
													// for(int j=0;j<=(cols*3)*(2-1);j=j+cols*3)   ///////////////////SET ROW 2 means how much row 
													  for(int j=0;j<s.size();j=j+cols*3)
															{
																	
																	Element xtablegridrow=doc2.createElement("tr");
																	 xtablegrid.appendChild(xtablegridrow);
																	if(count%2==0)
																			{
																				xtablegridrow.setAttribute("class","EvenRow");
																				xtablegridrow.setAttribute("onmouseout","this.className='evenout';");
																			}	
																			else
																			{	
																				xtablegridrow.setAttribute("class","OddRow");
																				xtablegridrow.setAttribute("onmouseout","this.className='oddout';");
																			}
																			
																	for(int k=0;k<cols*3;k=k+3)
                                                                                                                                                       {
																				Element xtablegridrowcol=doc2.createElement("td");
																				xtablegridrow.appendChild(xtablegridrowcol);
																				xtablegridrowcol.setAttribute("id",y+"Row"+count+"Col"+(k/3));
																				xtablegridrowcol.setAttribute("class",y+"Row"+count+"Col"+(k/3));
																				xtablegridrowcol.setAttribute("width","150px");
																				xtablegridrowcol.setAttribute("style","font-size:11px;font-weight:normal;font-family:century gothic;text-align:center;");
																				String type=(String)s.elementAt(j+k);	
																				String check=(String)s.elementAt(j+k+1);	
																				String value=(String)s.elementAt(j+k+2);	
																				if(type.equals("text"))
																							{
																								xtablegridrowcol.appendChild(doc2.createTextNode(value));
																							}	
																			}																																	
																		count++;
															}
											   ////////////////////////////////////////   ADD TO GRID DATA///////////
										}
							}
		
}



public static void setStyle(String colname,String property,String value)
{
	NodeList itemhead=doc2.getElementsByTagName("style");
	                 	for( int i=0; i<itemhead.getLength(); i++)
              			 {
              	 		 	Element x=(Element)itemhead.item(i);
					x.appendChild(doc2.createTextNode("."+colname+"{"+property+":"+value+";}"));
					x.appendChild(doc2.createTextNode(".hover{background-color:green;}"));
					x.appendChild(doc2.createTextNode(".evenout{background-color:#C0C0C0;}"));
					x.appendChild(doc2.createTextNode(".oddout{background-color:#FFFFFF;}"));
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

public static void setHref(String colname,String ref)
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
		  		           	linkref.setAttribute("href",ref);
		  		           	linkref.appendChild(doc2.createTextNode(value));	
		  		           	x.appendChild(linkref);	
       						}
				  }
}


public static void checkOnclick(String colname,String ref)
{
	NodeList td=doc2.getElementsByTagName("input");
	                 	for( int i=0; i<td.getLength(); i++)
              			 {
              			            Element x=(Element)td.item(i);
              			 	    String y = x.getAttribute("id");
              	 		 	    if( y.equals(colname))
              	 		 	        {
		  		        	
		  		           	x.setAttribute("onclick",ref+"();");
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