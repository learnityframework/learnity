package learnityInit;

import org.apache.xerces.parsers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class InitHostInfo extends HttpServlet
{
    DOMParser parser;
    
    public void init(final ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        final ServletContext sc = servletConfig.getServletContext();
        final String adminpath = servletConfig.getInitParameter("AdminPath");
        final String utility = servletConfig.getInitParameter("Util");
        final String strServerDocumentPath = servletConfig.getInitParameter("ServerDocumentPath");
        final String strPortalImagePath = servletConfig.getInitParameter("PortalImagePath");
        final String strPortalImagePathEngine = servletConfig.getInitParameter("PortalImagePathEngine");
        final String strServerDocumentPathEngine = servletConfig.getInitParameter("ServerDocumentPathEngine");
        final String strServerCoursePath = servletConfig.getInitParameter("ServerCoursePath");
        final String strServerCoursePathEngine = servletConfig.getInitParameter("ServerCoursePathEngine");
        Host.setAdminPath(adminpath);
        Host.setUtil(utility);
        Host.setServerDocumentPath(strServerDocumentPath);
        Host.setPortalImagePath(strPortalImagePath);
        Host.setPortalImagePathEngine(strPortalImagePathEngine);
        Host.setServerDocumentPathEngine(strServerDocumentPathEngine);
        Host.setServerCoursePath(strServerCoursePath);
        Host.setServerCoursePathEngine(strServerCoursePathEngine);
        System.out.println("===========================Core Admin Initialized Parameters========================");
        System.out.println(adminpath);
        System.out.println(utility);
        System.out.println(strServerDocumentPath);
        System.out.println(strServerDocumentPathEngine);
        System.out.println(strPortalImagePath);
        System.out.println(strPortalImagePathEngine);
        System.out.println(strServerCoursePath);
        System.out.println(strServerCoursePathEngine);
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
    
    public InitHostInfo() {
        this.parser = new DOMParser();
    }
}
