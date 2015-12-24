package comv2.aunwesha.param;

import javax.sql.*;
import org.apache.xerces.parsers.*;
import com.websina.license.*;
import java.text.*;
import interfaceenginev2.*;
import javax.naming.*;
import org.w3c.dom.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class CoreAdminInitHostInfo extends HttpServlet
{
    private static Document doc;
    public static DataSource ds;
    public static DataSource ds1;
    public String useInterfaceCaching;
    DOMParser parser;
    
    private static String checkValidity() throws Exception {
        final LicenseManager manager = LicenseManager.getInstance();
        final boolean valid = manager.isValid();
        final String feature_x = manager.getFeature("User No");
        final String feature_x2 = manager.getFeature("Date of Installation");
        final String _feature_x = null;
        final StringBuffer buf = new StringBuffer();
        if (valid) {
            buf.append("License is valid: " + valid);
            System.out.println("User No: " + feature_x);
            System.out.println("Date of Installation: " + feature_x2);
            final Date now = new Date();
            final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            final String s = df.format(now);
            System.out.println("Current Date: " + s);
            final Date d = df.parse(feature_x2);
            final long dins = d.getTime();
            final long nowLong = now.getTime();
            final long cal = nowLong - dins;
            final long daysDifference = cal / 86400000L;
            System.out.println("Days Expired: " + daysDifference);
            System.out.println("Days Left: " + manager.daysLeft());
            buf.append('\n').append("Days left: " + manager.daysLeft());
            buf.append("\nlicense is valid and so the program continues....");
            return buf.toString();
        }
        buf.append("\nlicense is invalid and so the program exits....");
        System.exit(1);
        return buf.toString();
    }
    
    public void init(final ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        final ServletContext sc = servletConfig.getServletContext();
        try {
            System.out.println(checkValidity());
        }
        catch (Throwable ee) {
            System.out.println("Error:" + ee);
            System.exit(1);
        }
        final ResourceBundle rb = ResourceBundle.getBundle("portal", Locale.getDefault());
        final String key1 = "frameworkdatasource";
        final String key2 = "applicationdatasource";
        final String dbcon_name = rb.getString(key1);
        final String dbcon_name2 = rb.getString(key2);
        Context initCtx = null;
        Context envCtx = null;
        try {
            System.out.println("======================dbcon_name===============" + dbcon_name);
            initCtx = new InitialContext();
            envCtx = (Context)initCtx.lookup("java:comp/env");
            CoreAdminInitHostInfo.ds = (DataSource)envCtx.lookup(dbcon_name);
            CoreAdminInitHostInfo.ds1 = (DataSource)envCtx.lookup(dbcon_name2);
            if (CoreAdminInitHostInfo.ds == null) {
                System.out.println("==========ds in CoreAdminInitHost is null===============");
            }
            else {
                System.out.println("============CoreAdminInitHost ds not null==========");
            }
            if (CoreAdminInitHostInfo.ds1 == null) {
                System.out.println("==========ds1 in CoreAdminInitHost is null===============");
            }
            else {
                System.out.println("============CoreAdminInitHost ds1 not null==========");
            }
            final Vector vApplicationTemplate = DataBaseLayer.getApplicationTemplateCachingStatus();
            System.out.println("=========vApplicationTemplate=======" + vApplicationTemplate);
            final Vector vApplicationDefaultTemplate = DataBaseLayer.getApplicationTemplateDefaultCachingStatus();
            System.out.println("=========vApplicationDefaultTemplate=======" + vApplicationDefaultTemplate);
            sc.setAttribute("ApplicationTemplateConf", (Object)vApplicationTemplate);
            sc.setAttribute("DefaultApplicationTemplateConf", (Object)vApplicationDefaultTemplate);
            final String key3 = "InterfaceCaching";
            this.useInterfaceCaching = rb.getString(key3);
            if (this.useInterfaceCaching == null) {
                this.useInterfaceCaching = "false";
            }
            sc.setAttribute("useInterfaceCaching", (Object)this.useInterfaceCaching);
            final String default_cache = DataBaseLayer.getDefaultCache();
            sc.setAttribute("DefaultCacheName", (Object)default_cache);
            final InterfaceCachePojo ICP = new InterfaceCachePojo();
            if (ICP == null) {
                System.out.println("==========ICP null===============");
            }
            else {
                System.out.println("========ICP not null===============");
            }
            ICP.InitialiseCacheDataFromTemplate(default_cache, vApplicationTemplate, vApplicationDefaultTemplate );
            sc.setAttribute("ICP", (Object)ICP);
        }
        catch (NamingException ne) {
            System.out.println("Naming Exception " + ne.getMessage());
        }
        catch (DOMException e) {
            e.printStackTrace();
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        System.out.println("=============================");
        DataBaseLayer.deleteCoreAdminDynamicInfo();
    }
    
    public void destroy() {
        super.destroy();
    }
    
    public void doPost(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) throws ServletException, IOException {
    }
    
    public void doGet(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) throws ServletException, IOException {
        this.doPost(httpServletRequest, httpServletResponse);
    }
    
    public String getServletInfo() {
        return "CoreAdminInitHostInfo";
    }
    
    public CoreAdminInitHostInfo() {
        this.useInterfaceCaching = "";
        this.parser = new DOMParser();
    }
    
    static {
        CoreAdminInitHostInfo.doc = null;
        CoreAdminInitHostInfo.ds = null;
        CoreAdminInitHostInfo.ds1 = null;
    }
}
