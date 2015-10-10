package coreadministrationv2;

import javax.servlet.http.*;
import java.io.*;
import javax.servlet.*;

public class Welcome extends HttpServlet
{
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        final PrintWriter out = response.getWriter();
        final HttpSession session = request.getSession(true);
        final String strName = request.getParameter("user_name");
        String strDateTime = request.getParameter("date_time");
        String strWelcome = "Welcome";
        if (strDateTime != null) {
            strWelcome += " Back";
            strDateTime = "You had last login at " + strDateTime;
        }
        else {
            strDateTime = "";
        }
        String strpage = "<title>Welcome " + strName + "</title>" + "\n<link href=\"../coreadmin/js/stylesheet.css\" rel=\"stylesheet\"><script src=\"../coreadmin/js/check.js\" language=\"JavaScript\"></script>" + "\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">" + "\n</head>" + "\n<body bgColor=D6D6C0 onLoad=\"scrollit(100);\">" + "\n<CENTER>" + "\n<TABLE cellSpacing=0 cellPadding=0 border=0 width=\"60%\" align=\"center\">" + "\n<TR><TD><IMG src=\"../coreadmin/images/T.gif\" width=\"100%\" height=100 border=0></TD></TR>" + "\n<TR> <TD>" + "\n<table cellspacing=0 cellpadding=1 width=\"100%\" bgcolor=#990000 border=0 align=\"center\">" + "\n  <tbody> " + "\n  <tr bgcolor=#990000> " + "\n  <tr> " + "\n    <td> " + "\n      <table cellspacing=0 cellpadding=4 width=\"100%\" bgcolor=#ffffff border=0>" + "\n        <tbody> " + "\n        <tr bgcolor=#990000> " + "\n          <td align=middle><font color=#ffffff size=+1><b>" + strWelcome + " <a style=\"COLOR: #ffffff\" " + "                                href=\"./coreadministrationv2.ChangeProfile\">" + strName + "</a>!</b> " + strDateTime + "\n            </font></td>" + "\n        </tr>" + "\n        </tbody>" + "\n      </table>" + "\n      <table cellspacing=0 cellpadding=0 width=\"100%\" bgcolor=#ffffff border=0>" + "\n        <tbody> " + "\n        <tr> " + "\n          <td> " + "\n            <center>" + "\n              <a title=\"Sign Out\" " + "\n                                href=\"./coreadministrationv2.Logout\" target=\"_parent\"><img " + "\n                                height=25 " + "\n                                src=\"../coreadmin/images/toolbox_signout.gif\" " + "\n                                width=74 border=0></a>" + "\n            </center>" + "\n            </td>" + "\n        </tr><tr><td width=\"100%\"><img " + "\n                                height=25 " + "\n                                src=\"../coreadmin/images/T.gif\" " + "\n                                width=66 border=0></td></TR>" + "\n        </tbody>" + "\n      </table>" + "\n    </td>" + "\n  </tr>" + "\n  </tbody>" + "\n</table>" + "\n</TD></TR></TABLE>";
        strpage = strpage + "\n</CENTER>" + "\n</body>" + "\n</html>";
        out.println(strpage);
    }
    
    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
        this.doGet(request, response);
    }
}
