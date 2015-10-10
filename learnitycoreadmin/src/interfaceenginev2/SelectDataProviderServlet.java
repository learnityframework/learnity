package interfaceenginev2;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Vector;
import java.text.SimpleDateFormat;
import org.w3c.dom.*;



//import org.grlea.log.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Properties;
import java.util.*;
import java.io.*;
import java.text.*;





/**
 * Title:		 DisplayEngine
 * Description:	 DropDown Generate
 * Copyright:    Copyright (c) 2010
 * Company:		 Aunwesha Knowledge Technologies Pvt. Ltd.
 * @author		Shibaji Chatterjee
*/

public class SelectDataProviderServlet extends HttpServlet  {

   public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
  			 throws ServletException, IOException {
	       ServletOutputStream out=response.getOutputStream();
			 String child_id=request.getParameter("child_id");
			 String interface_id=request.getParameter("interface_id");
			 String colname=request.getParameter("colname");
			 String sql_query="";
			 Vector getEditOption=NewDataBaseLayer.getEditOption(child_id,interface_id,colname);
			 for(int j=0;j<getEditOption.size();j=j+7)
			 {
				 sql_query=(String)getEditOption.elementAt(j+5);			
			 }
			 
			 String html="<option value=\"0\">Choose One</option>";

			 Vector dropdownstring=NewDataBaseLayer.returnpagedropdown(sql_query);
			 for(int i=0;i<dropdownstring.size();i=i+2)
			 {
				 String name=(String)dropdownstring.elementAt(i);
				 String value=(String)dropdownstring.elementAt(i+1);
				 html+="<option value=\""+name+"\">"+value+"</option>";
			 }	
		
			 out.println("<select>"+html+"</select>");
									
}
public void doGet(HttpServletRequest request,HttpServletResponse response)
				throws ServletException,IOException{
			
          doPost(request, response);
                }
}					                                                                                                 									