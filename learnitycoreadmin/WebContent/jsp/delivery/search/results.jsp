<%@ page import = " javax.servlet.*, javax.servlet.http.*, java.io.*, org.apache.lucene.analysis.*, org.apache.lucene.analysis.standard.StandardAnalyzer, org.apache.lucene.document.*, org.apache.lucene.index.*, org.apache.lucene.search.*, org.apache.lucene.queryParser.*, org.apache.lucene.demo.*, org.apache.lucene.demo.html.Entities, java.net.URLEncoder,highlight.*" %>

<% /* Author : Subir Bhaumik */ %>

<script type="text/javascript">
function showFile(URL)
{
	window.parent.document.getElementById("deliveryIframe").src=URL;
}
</script>
 
<%!
public String escapeHTML(String s) {
  s = s.replaceAll("&", "&amp;");
  s = s.replaceAll("<", "&lt;");
  s = s.replaceAll(">", "&gt;");
  s = s.replaceAll("\"", "&quot;");
  s = s.replaceAll("'", "&apos;");
  return s;
}
%>
<%@ include file="header.jsp"%>
<%
        boolean error = false;                  
        String indexName = indexLocation;       
        IndexSearcher searcher = null;          
        Query query = null;                     
        Hits hits = null;                       
        int startindex = 0;                     
        int maxpage    = 50;                    
        String queryString = null;              
        String startVal    = null;              
        String maxresults  = null;              
        int thispage = 0;                       
                                                
                                                

        try {
          searcher = new IndexSearcher(indexName);      
                                                        
        } catch (Exception e) {                        
                                                      
%>
                <p>ERROR opening the Index - contact LearnITy Administrator!</p>
                <p>Error message: <%=escapeHTML(e.getMessage())%></p>   
<%                error = true;                                  
        }
%>
<%
	Analyzer analyzer = new StandardAnalyzer();           
                
       if (error == false) {                                           
                queryString = request.getParameter("query");           
                startVal    = request.getParameter("startat");         
                maxresults  = request.getParameter("maxresults");      
                try {
                        maxpage    = Integer.parseInt(maxresults);    
                        startindex = Integer.parseInt(startVal);      
                } catch (Exception e) { }
                                          
                if (queryString == null)
                        throw new ServletException("no query "+      
                                                   "specified");     
          
                try {
                        QueryParser qp = new QueryParser("contents", analyzer);
                        query = qp.parse(queryString); 
                } catch (ParseException e) {                          
                                                                      
%>
                        <p>Error while parsing query: <%=escapeHTML(e.getMessage())%></p>
<%
                        error = true;                                 
                                                                     
                }
        }
%>
<%
        if (error == false && searcher != null) {                     
                                                                      
                                                                      
                thispage = maxpage;                                   
                hits = searcher.search(query);                        
                if (hits.length() == 0) {                             
%>
                <p> Your search - <font size=4><b><%=queryString  %></b></font> - did not match any documents. </p>
<%
                error = true;                                        
                                                                    
                }
        }

        if (error == false && searcher != null) {                   
%>
<%
int totalpage= hits.length();
	if ((startindex + maxpage) > hits.length()) {
                        totalpage = hits.length();     
} else {
	totalpage = startindex + maxpage;
}
%>
		<p align="right">
			Results <font size=4><b><%=startindex + 1 %></b></font> - <font size=4><b><%=totalpage%></b></font> of <font size=4><b><%=hits.length() %></b></font> for <font size=4><b><%=queryString %></b></font>
     		</p>           
<table>
               
 
<%
                if ((startindex + maxpage) > hits.length()) {
                        thispage = hits.length() - startindex;      
                }                                                   

                for (int i = startindex; i < (thispage + startindex); i++) {  
%>

<%
				String summary = "";
                        Document doc = hits.doc(i);                    
                        String doctitle = doc.get("title");    
                        
//                        for (String field : doc.getFields()) {
//                        	  System.out.println(field);
//                          }
                        
                        String url = doc.get("url");
                        
                        String filename = url.substring(url.lastIndexOf("/")+1);
                        String filePath = "'http://localhost:8080"+serverDocFolderURL + course_id + "/" + filename + "'";
                        System.out.println(filePath);
                        String functionName = "javascript:showFile(" + filePath + ")";
                    /*    
                  	//String url = doc.get("path");//get its path field
                        String truncatedUrl = "";
                        String window_id = "";
                        String page_id = "";
                        String file_type = "";
                        String rel_path = "";
                        String cookieval = "";
                        String cookiehndlr = "";
                        
                        if (url != null) {
                        //        url = url.substring(url.indexOf(context_path));
                                rel_path = url.substring(0,url.lastIndexOf(File.separator)+1);
                                file_type = url.substring(url.lastIndexOf(".")+1);
                                String filename = url.substring(url.lastIndexOf(File.separator)+1);
                                if(filename.startsWith("P")) {
                                	window_id = url.substring(url.indexOf("_")+1,url.lastIndexOf("_P"));
                                	page_id = url.substring(url.indexOf("_P")+1,url.lastIndexOf("."));
                                	cookieval = "P_" + window_id + "_" + page_id + "." + file_type+":"+page_id+"-"+window_id;
                                	cookiehndlr = "createCookie('contensearchcookie','"+cookieval+"',7)";
                                } 
					else if(filename.startsWith("S")) {
						window_id = url.substring(url.indexOf("_")+1,url.lastIndexOf("_P"));
						page_id = url.substring(url.indexOf("_P")+1,url.lastIndexOf("_root"));
                                	cookieval = "P_" + window_id + "_" + page_id + "." + file_type+":"+page_id+"-"+window_id;
                                	cookiehndlr = "createCookie('contensearchcookie','"+cookieval+"',7)";
                             }
					else if(filename.startsWith("W")) {
						window_id = url.substring(url.indexOf("_")+1,url.lastIndexOf("."));
						cookiehndlr = "eraseCookie('contensearchcookie')";
                              }
                        }
                  */      
                                
                        if ((doctitle == null) || doctitle.equals("")) 
                                doctitle = url;
                         summary = highlight("summary",hits,analyzer,query,doc);
                                                                 
%>
                        <tr><td><a href="<%=functionName%>"><%=doctitle%></a></td></tr>
                        <tr><td><%=summary%>"></td><tr>
                        <%-- <td><%=doc.get("summary")%></td> --%>
 
<%
                }
%>
</table>
<table>
<%                if ( (startindex + maxpage) < hits.length()) {   
                                                                   

                        String moreurl="results.jsp?query=" + 
                                       URLEncoder.encode(queryString) +  
                                       "&amp;maxresults=" + maxpage + 
                                       "&amp;startat=" + (startindex + maxpage);
%>
                <tr>
                        <td></td><td><a href="<%=moreurl%>">More Results>></a></td>
                </tr>
<%
                }
%>
                </table>

<%       }                                            
         if (searcher != null)
                searcher.close();
%>
<%!
public String highlight(String FIELD_NAME,Hits hit,Analyzer analyze,Query query,Document hdoc) {
		String result = new String();
		String text = new String();

		try {
			Highlighter highlighter = new Highlighter(new QueryScorer(query));
			highlighter.setTextFragmenter(new SimpleFragmenter(100));
			text = hdoc.get(FIELD_NAME);
			if(text!=null) {
				TokenStream tokenStream = analyze.tokenStream(FIELD_NAME,new StringReader(text));
				try {
					result = highlighter.getBestFragments(tokenStream,text,5,"....");
					if(result.equals("") || result.equals(" "))
						result = text;
				}
				catch (Exception e) {
    					e.printStackTrace();
				}
			}
		}
		catch (Exception e) {
    			e.printStackTrace();
		}
		return(result);
}

%>


<p align="center">
 Powered By LMS
</p>
</body>       
