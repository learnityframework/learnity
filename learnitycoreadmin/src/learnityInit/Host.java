package learnityInit;

public class Host
{
    private static String ConnUrl;
    private static String DBUserID;
    private static String DBPassword;
    private static String AdminPath;
    private static String Utility;
    private static String ServerDocumentPath;
    private static String coursePath;
    private static String coursePathEngine;
    private static String PortalImagePath;
    private static String PortalLoginImagePath;
    private static String PortalImagePathEngine;
    private static String ReportImagesRepository;
    private static String ServerDocumentPathEngine;
    private static String RTEPath;
    
    public static void setConnUrl(final String dbconurl) {
        Host.ConnUrl = dbconurl;
    }
    
    public static void setDBUserID(final String dbuid) {
        Host.DBUserID = dbuid;
    }
    
    public static void setDBPassword(final String dbpwd) {
        Host.DBPassword = dbpwd;
    }
    
    public static void setAdminPath(final String adminpath) {
        Host.AdminPath = adminpath;
    }
    
    public static void setUtil(final String utility) {
        Host.Utility = utility;
    }
    
    public static void setServerDocumentPath(final String serverdocumentpath) {
        Host.ServerDocumentPath = serverdocumentpath;
    }
    
    public static void setServerCoursePath(final String servercoursepath) {
        Host.coursePath = servercoursepath;
    }
    
    public static void setServerCoursePathEngine(final String servercoursepatheng) {
        Host.coursePathEngine = servercoursepatheng;
    }
    
    public static void setPortalImagePath(final String portalImagePath) {
        Host.PortalImagePath = portalImagePath;
    }
    
    public static void setPortalLoginImagePath(final String portalLoginImagePath) {
        Host.PortalLoginImagePath = portalLoginImagePath;
    }
    
    public static void setPortalImagePathEngine(final String portalImagePathEngine) {
        Host.PortalImagePathEngine = portalImagePathEngine;
    }
    
    public static void setReportImagesRepository(final String reportImagesRepository) {
        Host.ReportImagesRepository = reportImagesRepository;
    }
    
    public static void setServerDocumentPathEngine(final String serverDocumentPathEngine) {
        Host.ServerDocumentPathEngine = serverDocumentPathEngine;
    }
    
    public static String getRTEPath() {
        return Host.RTEPath;
    }
    
    public static String getConnUrl() {
        return Host.ConnUrl;
    }
    
    public static String getDBUserID() {
        return Host.DBUserID;
    }
    
    public static String getDBPassword() {
        return Host.DBPassword;
    }
    
    public static String getAdminPath() {
        return Host.AdminPath;
    }
    
    public static String getUtil() {
        return Host.Utility;
    }
    
    public static String getServerDocumentPath() {
        return Host.ServerDocumentPath;
    }
    
    public static String getServerCoursePath() {
        return Host.coursePath;
    }
    
    public static String getServerCoursePathEngine() {
        return Host.coursePathEngine;
    }
    
    public static String getPortalImagePath() {
        return Host.PortalImagePath;
    }
    
    public static String getPortalLoginImagePath() {
        return Host.PortalLoginImagePath;
    }
    
    public static String getPortalImagePathEngine() {
        return Host.PortalImagePathEngine;
    }
    
    public static String getReportImagesRepository() {
        return Host.ReportImagesRepository;
    }
    
    public static String getServerDocumentPathEngine() {
        return Host.ServerDocumentPathEngine;
    }
    
    static {
        Host.ConnUrl = null;
        Host.DBUserID = null;
        Host.DBPassword = null;
        Host.AdminPath = null;
        Host.Utility = null;
        Host.ServerDocumentPath = null;
        Host.coursePath = null;
        Host.coursePathEngine = null;
        Host.PortalImagePath = null;
        Host.PortalLoginImagePath = null;
        Host.PortalImagePathEngine = null;
        Host.ReportImagesRepository = null;
        Host.ServerDocumentPathEngine = null;
        Host.RTEPath = null;
    }
}
