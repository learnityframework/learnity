package coreadministrationv2;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Logout extends HttpServlet
{
    private static final String LOGIN_SESSION_NAME = "ADMIN_LOG_ON";
    
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
    }
    
    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final HttpSession mysession = request.getSession(true);
        final Object obj = mysession.getAttribute("ADMIN_LOG_ON");
        String l_strLogin = null;
        if (obj != null) {
            l_strLogin = obj.toString();
        }
        mysession.removeAttribute("ADMIN_LOG_ON");
        NewDataBaseLayer.decreaseAdministrator(l_strLogin);
        response.sendRedirect("../coreadmin/login.html");
    }
    
    public void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
