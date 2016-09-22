package comv2.aunwesha.param;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.sql.DataSource;

import org.grlea.log.SimpleLogger;

public class DataBaseLayer
{
    public static final SimpleLogger log;
    public static DataSource ds;
    public static DataSource ds1;
    
    public static synchronized void deleteCoreAdminDynamicInfo() {
        try {
            final Connection oConn = DataBaseLayer.ds.getConnection();
            final Statement oStmt = oConn.createStatement();
            oStmt.executeUpdate("delete from administrator_logged_in_at");
            oStmt.close();
            oConn.close();
        }
        catch (SQLException e) {
            DataBaseLayer.log.debug("Error due to SQL exception in deleteCoreAdminDynamicInfo(): " + e.getMessage());
        }
        catch (Exception ex) {
            DataBaseLayer.log.debug("Error due to java.lang.exception in deleteCoreAdminDynamicInfo()");
            ex.printStackTrace();
        }
    }
    
    public static synchronized void deleteDynamicInformation() {
        try {
            final Connection oConn = DataBaseLayer.ds.getConnection();
            final Statement oStmt = oConn.createStatement();
            oStmt.executeUpdate("delete from student_dynamic_information");
            oStmt.executeUpdate("delete from administrator_logged_in_at");
            oStmt.executeUpdate("delete from user_login_time");
            oStmt.executeUpdate("delete from forum_dynamic_information");
            oStmt.close();
            oConn.close();
        }
        catch (SQLException e) {
            DataBaseLayer.log.debug("Error due to SQL exception : " + e.getMessage());
        }
        catch (Exception ex) {
            DataBaseLayer.log.debug("Error due to java.lang.exception");
            ex.printStackTrace();
            DataBaseLayer.log.debug(" printStack is :: " + ex.getMessage());
        }
    }
    
    public static Vector getApplicationTemplateCachingStatus() {
        final Vector vTemplateCachingStatus = new Vector();
        Statement oStmt = null;
        Statement oStmt2 = null;
        Statement oStmt3 = null;
        Statement oStmt4 = null;
        Statement oStmt5 = null;
        Statement oStmt6 = null;
        Connection oConn = null;
        ResultSet oRset = null;
        ResultSet oRset2 = null;
        ResultSet oRset3 = null;
        ResultSet oRset4 = null;
        ResultSet oRset5 = null;
        ResultSet oRset6 = null;
        try {
            oConn = DataBaseLayer.ds.getConnection();
            oStmt = oConn.createStatement();
            oStmt2 = oConn.createStatement();
            oStmt3 = oConn.createStatement();
            oStmt4 = oConn.createStatement();
            oStmt5 = oConn.createStatement();
            oStmt6 = oConn.createStatement();
            System.out.println("select application_template_id,application_template_title from application_template_master where default_value!='yes'");
            oRset = oStmt.executeQuery("select application_template_id,application_template_title from application_template_master where default_value!='yes'");
            while (oRset.next()) {
                final Vector vTemplateCachingStatusSub = new Vector();
                final String application_template_id = oRset.getString(1);
                final String application_template_title = oRset.getString(2);
                vTemplateCachingStatusSub.addElement(application_template_id);
                vTemplateCachingStatusSub.addElement(application_template_title);
                oRset2 = oStmt2.executeQuery("select default_value from application_template_default where application_template_id='" + application_template_id + "' and attribute_name='Enable_Caching'");
                if (oRset2.next()) {
                    vTemplateCachingStatusSub.addElement(oRset2.getString(1));
                }
                else {
                    vTemplateCachingStatusSub.addElement("");
                }
                oRset3 = oStmt3.executeQuery("select default_value from application_template_default where application_template_id='" + application_template_id + "' and attribute_name='CacheName'");
                if (oRset3.next()) {
                    vTemplateCachingStatusSub.addElement(oRset3.getString(1));
                }
                else {
                    vTemplateCachingStatusSub.addElement("");
                }
                oRset4 = oStmt4.executeQuery("select default_value from application_template_default where application_template_id='" + application_template_id + "' and attribute_name='CacheDynamicJS'");
                if (oRset4.next()) {
                    vTemplateCachingStatusSub.addElement(oRset4.getString(1));
                }
                else {
                    vTemplateCachingStatusSub.addElement("");
                }
                oRset5 = oStmt5.executeQuery("select default_value from application_template_default where application_template_id='" + application_template_id + "' and attribute_name='CacheDynamicCSS'");
                if (oRset5.next()) {
                    vTemplateCachingStatusSub.addElement(oRset5.getString(1));
                }
                else {
                    vTemplateCachingStatusSub.addElement("");
                }
                oRset6 = oStmt6.executeQuery("select default_value from application_template_default where application_template_id='" + application_template_id + "' and attribute_name='CacheDynamicImage'");
                if (oRset6.next()) {
                    vTemplateCachingStatusSub.addElement(oRset6.getString(1));
                }
                else {
                    vTemplateCachingStatusSub.addElement("");
                }
                vTemplateCachingStatus.addElement(vTemplateCachingStatusSub);
            }
        }
        catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (oConn != null) {
                try {
                    if (oRset != null) {
                        oRset.close();
                    }
                    if (oRset2 != null) {
                        oRset2.close();
                    }
                    if (oRset3 != null) {
                        oRset3.close();
                    }
                    if (oRset4 != null) {
                        oRset4.close();
                    }
                    if (oRset5 != null) {
                        oRset5.close();
                    }
                    if (oRset6 != null) {
                        oRset6.close();
                    }
                    if (oStmt != null) {
                        oStmt.close();
                    }
                    if (oStmt2 != null) {
                        oStmt2.close();
                    }
                    if (oStmt3 != null) {
                        oStmt3.close();
                    }
                    if (oStmt4 != null) {
                        oStmt4.close();
                    }
                    if (oStmt5 != null) {
                        oStmt5.close();
                    }
                    if (oStmt6 != null) {
                        oStmt6.close();
                    }
                    oConn.close();
                }
                catch (Exception ex2) {}
            }
        }
        return vTemplateCachingStatus;
    }
    
    public static Vector getApplicationTemplateDefaultCachingStatus() {
        final Vector vTemplateCachingStatus = new Vector();
        Statement oStmt = null;
        Statement oStmt2 = null;
        Statement oStmt3 = null;
        Statement oStmt4 = null;
        Statement oStmt5 = null;
        Statement oStmt6 = null;
        Connection oConn = null;
        ResultSet oRset = null;
        ResultSet oRset2 = null;
        ResultSet oRset3 = null;
        ResultSet oRset4 = null;
        ResultSet oRset5 = null;
        ResultSet oRset6 = null;
        try {
            oConn = DataBaseLayer.ds.getConnection();
            oStmt = oConn.createStatement();
            oStmt2 = oConn.createStatement();
            oStmt3 = oConn.createStatement();
            oStmt4 = oConn.createStatement();
            oStmt5 = oConn.createStatement();
            oStmt6 = oConn.createStatement();
            System.out.println("select application_template_id,application_template_title from application_template_master where default_value='yes'");
            oRset = oStmt.executeQuery("select application_template_id,application_template_title from application_template_master where default_value='yes'");
            while (oRset.next()) {
                final Vector vTemplateCachingStatusSub = new Vector();
                final String application_template_id = oRset.getString(1);
                final String application_template_title = oRset.getString(2);
                vTemplateCachingStatusSub.addElement(application_template_id);
                vTemplateCachingStatusSub.addElement(application_template_title);
                oRset2 = oStmt2.executeQuery("select default_value from application_template_default where application_template_id='" + application_template_id + "' and attribute_name='Enable_Caching'");
                if (oRset2.next()) {
                    vTemplateCachingStatusSub.addElement(oRset2.getString(1));
                }
                else {
                    vTemplateCachingStatusSub.addElement("");
                }
                oRset3 = oStmt3.executeQuery("select default_value from application_template_default where application_template_id='" + application_template_id + "' and attribute_name='CacheName'");
                if (oRset3.next()) {
                    vTemplateCachingStatusSub.addElement(oRset3.getString(1));
                }
                else {
                    vTemplateCachingStatusSub.addElement("");
                }
                oRset4 = oStmt4.executeQuery("select default_value from application_template_default where application_template_id='" + application_template_id + "' and attribute_name='CacheDynamicJS'");
                if (oRset4.next()) {
                    vTemplateCachingStatusSub.addElement(oRset4.getString(1));
                }
                else {
                    vTemplateCachingStatusSub.addElement("");
                }
                oRset5 = oStmt5.executeQuery("select default_value from application_template_default where application_template_id='" + application_template_id + "' and attribute_name='CacheDynamicCSS'");
                if (oRset5.next()) {
                    vTemplateCachingStatusSub.addElement(oRset5.getString(1));
                }
                else {
                    vTemplateCachingStatusSub.addElement("");
                }
                oRset6 = oStmt6.executeQuery("select default_value from application_template_default where application_template_id='" + application_template_id + "' and attribute_name='CacheDynamicImage'");
                if (oRset6.next()) {
                    vTemplateCachingStatusSub.addElement(oRset6.getString(1));
                }
                else {
                    vTemplateCachingStatusSub.addElement("");
                }
                vTemplateCachingStatus.addElement(vTemplateCachingStatusSub);
            }
        }
        catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (oConn != null) {
                try {
                    if (oRset != null) {
                        oRset.close();
                    }
                    if (oRset2 != null) {
                        oRset2.close();
                    }
                    if (oRset3 != null) {
                        oRset3.close();
                    }
                    if (oRset4 != null) {
                        oRset4.close();
                    }
                    if (oRset5 != null) {
                        oRset5.close();
                    }
                    if (oRset6 != null) {
                        oRset6.close();
                    }
                    if (oStmt != null) {
                        oStmt.close();
                    }
                    if (oStmt2 != null) {
                        oStmt2.close();
                    }
                    if (oStmt3 != null) {
                        oStmt3.close();
                    }
                    if (oStmt4 != null) {
                        oStmt4.close();
                    }
                    if (oStmt5 != null) {
                        oStmt5.close();
                    }
                    if (oStmt6 != null) {
                        oStmt6.close();
                    }
                    oConn.close();
                }
                catch (Exception ex2) {}
            }
        }
        return vTemplateCachingStatus;
    }
    
    public static String getDefaultCache() {
        String CacheName = "";
        Statement oStmt = null;
        Connection oConn = null;
        ResultSet oRset = null;
        try {
            oConn = DataBaseLayer.ds.getConnection();
            oStmt = oConn.createStatement();
            System.out.println("select cache_name from cache_definition where default_cache='Yes'");
            oRset = oStmt.executeQuery("select cache_name from cache_definition where default_cache='Yes'");
            while (oRset.next()) {
                CacheName = oRset.getString(1);
            }
        }
        catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (oConn != null) {
                try {
                    if (oRset != null) {
                        oRset.close();
                    }
                    if (oStmt != null) {
                        oStmt.close();
                    }
                    oConn.close();
                }
                catch (Exception ex2) {}
            }
        }
        return CacheName;
    }
    
    static {
        log = new SimpleLogger((Class)DataBaseLayer.class, true);
        DataBaseLayer.ds = CoreAdminInitHostInfo.ds;
        DataBaseLayer.ds1 = CoreAdminInitHostInfo.ds1;
    }
    
	public static boolean validateFrameworkDBConnection()
			throws SQLException
	{
		boolean flag = false;
		try(Connection oConn = DataBaseLayer.ds.getConnection();)
		{
			System.out.println(" Creation of Framework DB connection successful");
			flag = true;
		}
		catch(SQLException sqlexception)
		{
			System.out.println(" Error while creating Framework DB connection");
			System.out.println("Error due to SQLException , the error code - " + sqlexception.getErrorCode());
			System.out.println("Error message - " + sqlexception.getMessage());
			sqlexception.printStackTrace();
		}
		return flag;
	}
	
	public static boolean validateApplicationDBConnection()
			throws SQLException
	{
		boolean flag = false;
		try(Connection oConn1 = DataBaseLayer.ds1.getConnection();)
		{
			System.out.println(" Creation of Application DB connection successful");
			flag = true;
		}
		catch(SQLException sqlexception)
		{
			System.out.println(" Error while creating Application DB connection");
			System.out.println("Error due to SQLException , the error code - " + sqlexception.getErrorCode());
			System.out.println("Error message - " + sqlexception.getMessage());
			sqlexception.printStackTrace();
		}
		return flag;
	}
}
