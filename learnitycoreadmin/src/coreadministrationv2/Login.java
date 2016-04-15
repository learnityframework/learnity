package coreadministrationv2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.grlea.log.SimpleLogger;

public class Login extends HttpServlet
{
    private static final String LOGIN_SESSION_NAME = "ADMIN_LOG_ON";
    private static final SimpleLogger log;
    
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
    }
    
    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        final PrintWriter sos = response.getWriter();
        final String strUserId = request.getParameter("userName");
        final String strUserPasswd = request.getParameter("userPasswd");
        if (NewDataBaseLayer.isAuthenticated(strUserId, strUserPasswd, 'C')) {
            NewDataBaseLayer.setLoginTime(strUserId);
            final HttpSession mysession = request.getSession(true);
            mysession.setAttribute("ADMIN_LOG_ON", (Object)strUserId);
            final Object obj = mysession.getAttribute("ADMIN_LOG_ON");
            String l_strLogin = null;
            if (obj != null) {
                l_strLogin = obj.toString();
            }
            final Vector<String> vLastLoginTime = NewDataBaseLayer.getLastLoginTime(l_strLogin.trim());
            if (vLastLoginTime.size() == 2) {
                final String strDateTime = vLastLoginTime.elementAt(0);
                final String strName = vLastLoginTime.elementAt(1);
                final int count = NewDataBaseLayer.updateLoginTime(l_strLogin.trim(), true);
                sos.println("<HTML><HEAD><TITLE>LearnITy</TITLE>");
                sos.println("<META content=\"text/html; charset=windows-1252\" http-equiv=Content-Type>");
                sos.println("<META content=\"MSHTML 5.00.2614.3500\" name=GENERATOR></HEAD>");
                sos.println("<frameset rows=\"65,*\" cols=\"*\" frameborder=\"NO\" border=\"0\" framespacing=\"0\">");
                sos.println("<frame name=\"topFrame\" scrolling=\"NO\" noresize src=\"../coreadmin/servlets/top.html\">");
                sos.println("<frameset cols=\"253,*\" frameborder=\"NO\" border=\"0\" framespacing=\"0\" rows=\"*\">");
                sos.println("<frame name=\"treeFrame\" noresize scrolling=\"NO\" src=\"../coreadmin/servlets/User.html\">");
                sos.println("<frame name=\"bodyFrame\" src=\"./coreadministrationv2.Welcome?user_id=" + l_strLogin + "&user_name=" + strName + "&date_time=" + strDateTime + "\">");
                sos.println("</frameset>");
                sos.println("</frameset>");
                sos.println("</HTML>");
            }
            else {
                final String strName2 = vLastLoginTime.elementAt(0);
                final int count2 = NewDataBaseLayer.updateLoginTime(l_strLogin.trim(), false);
                sos.println("<HTML><HEAD><TITLE>LearnITy</TITLE>");
                sos.println("<META content=\"text/html; charset=windows-1252\" http-equiv=Content-Type>");
                sos.println("<META content=\"MSHTML 5.00.2614.3500\" name=GENERATOR></HEAD>");
                sos.println("<frameset rows=\"65,*\" cols=\"*\" frameborder=\"NO\" border=\"0\" framespacing=\"0\">");
                sos.println("<frame name=\"topFrame\" scrolling=\"NO\" noresize src=\"../coreadmin/servlets/top.html\">");
                sos.println("<frameset cols=\"253,*\" frameborder=\"NO\" border=\"0\" framespacing=\"0\" rows=\"*\">");
                sos.println("<frame name=\"treeFrame\" noresize scrolling=\"NO\" src=\"../coreadmin/servlets/User.html\">");
                sos.println("<frame name=\"bodyFrame\" src=\"./coreadministrationv2.Welcome?user_id=" + l_strLogin + "&user_name=" + strName2 + "\">");
                sos.println("</frameset>");
                sos.println("</frameset>");
                sos.println("</HTML>");
            }
        }
        else {
            final String s = "";
            sos.println("<HTML><HEAD><TITLE>Please sign in</TITLE>\n<META http-equiv=Content-Type content=\"text/html; charset=iso-8859-1\">\n<LINK href=\"../coreadmin/js/learnity.css\" type=text/css rel=stylesheet>\n</HEAD>\n<BODY bgColor=\"D6D6C0\" leftMargin=0 topMargin=10 rightMargin=0 marginwidth=\"0\" marginheight=\"0\" onload=\"document.frm.userName.focus()\">\n<FORM name=frm method=post action=\"./coreadministrationv2.Login\">");
            sos.println("<TABLE cellSpacing=0 cellPadding=0 width=308 bgColor=#CCFFFF border=0 align=\"center\">\n<TR>\n<TD rowSpan=2><IMG height=1 src=\"../coreadmin/images/T.gif\" width=10 border=0></TD>\n<TD width=\"100%\"><IMG height=5 src=\"../coreadmin/images/T.gif\" width=1 border=0></TD>\n<TD rowSpan=2><IMG height=1 src=\"../coreadmin/images/T.gif\" width=10 border=0></TD></TR>\n<TR>\n<TD><span class=\"title\">Sorry!</span>\n<p><font color=\"#FF0000\">*</font> Don't recognize the User ID and Password you entered. Please try again.</p>\n<p>If you're not a Administrator, register now.</p></TD></TR></TABLE><BR>");
            sos.println(s);
            sos.println("</form></body>");
            sos.println("</html>");
        }
    }
    
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
    
    static {
        log = new SimpleLogger((Class)Login.class);
    }
}
