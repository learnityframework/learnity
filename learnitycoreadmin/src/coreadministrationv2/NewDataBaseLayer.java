package coreadministrationv2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.sql.DataSource;

import org.grlea.log.SimpleLogger;

import comv2.aunwesha.param.CoreAdminInitHostInfo;

public class NewDataBaseLayer
{
    private static final SimpleLogger log;
    public static DataSource ds;
    
    public static synchronized boolean isAuthenticated(final String a_strUserID, final String a_strPassword, final char a_chRole) {
        Statement oStmt = null;
        ResultSet oRset = null;
        Connection oConn = null;
        boolean l_bAuth = false;
        Label_0036: {
            if (NewDataBaseLayer.ds == null) {
                System.out.println("==========ds in NewDataBaseLayer is null===============");
                break Label_0036;
            }
            System.out.println("============ds not null==========");
            try {
                oConn = NewDataBaseLayer.ds.getConnection();
                oStmt = oConn.createStatement();
                System.out.println("select *  from admin_user where user_id='" + a_strUserID + "' and password='" + a_strPassword + "'");
                oRset = oStmt.executeQuery("select *  from admin_user where user_id='" + a_strUserID + "' and password='" + a_strPassword + "'");
                if (oRset.next()) {
                    l_bAuth = true;
                }
            }
            catch (SQLException e) {
                System.out.println("Error due to SQL exception from isAuthenticated()");
                final int errCode = e.getErrorCode();
                System.out.println(errCode);
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            catch (Exception ex) {
                System.out.println(" error due to java.lang.exception");
                ex.printStackTrace();
                System.out.println(" printStack is :: " + ex.getMessage());
            }
            finally {
                if (oConn != null) {
                    try {
                        if (oRset != null) {
                            oRset.close();
                            oRset = null;
                        }
                        if (oStmt != null) {
                            oStmt.close();
                            oStmt = null;
                        }
                        oConn.close();
                    }
                    catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
        return l_bAuth;
    }
    
    public static synchronized Vector<String> getLastLoginTime(final String a_strUserID) {
        Vector<String> vLoginTime = null;
        try {
            final Connection oConn = NewDataBaseLayer.ds.getConnection();
            Statement oStmt = oConn.createStatement();
            ResultSet oRset = oStmt.executeQuery("select count(*) from administrator_login_time where user_id='" + a_strUserID + "'");
            int l_i = 0;
            while (oRset.next()) {
                l_i = oRset.getInt(1);
            }
            oRset.close();
            oStmt.close();
            if (l_i <= 0) {
                vLoginTime = new Vector<String>();
                oStmt = oConn.createStatement();
                oRset = oStmt.executeQuery("select concat(administrator_details.user_first_name,' ', COALESCE(concat(administrator_details.user_middle_name,' '),''),administrator_details.user_last_name) from administrator_details where user_id='" + a_strUserID + "'");
                oRset.next();
                vLoginTime.addElement(oRset.getString(1));
 //Added 17th Feb 2016
                oRset.close();
                oStmt.close();
                oConn.close();
//End of Add                
            }
            else {
                vLoginTime = new Vector<String>();
                oStmt = oConn.createStatement();
                oRset = oStmt.executeQuery("select date_format(administrator_login_time.login_time,\"%M %e, %Y %H:%i\"), concat(administrator_details.user_first_name,' ', COALESCE(concat(administrator_details.user_middle_name,' '),''),administrator_details.user_last_name) from administrator_login_time, administrator_details where administrator_login_time.user_id=administrator_details.user_id and administrator_login_time.user_id='" + a_strUserID + "'");
                oRset.next();
                vLoginTime.addElement(oRset.getString(1));
                vLoginTime.addElement(oRset.getString(2));
                oRset.close();
                oStmt.close();
                oConn.close();
            }
        }
        catch (SQLException e) {
            System.out.println("Error due to SQL exception");
        }
        catch (Exception ex) {
            System.out.println(" error due to java.lang.exception");
            ex.printStackTrace();
            System.out.println(" printStack is :: " + ex.getMessage());
        }
        return vLoginTime;
    }
    
    public static synchronized int updateLoginTime(final String a_strUserID, final boolean update) {
        int rows = 0;
        try {
            final Connection oConn = NewDataBaseLayer.ds.getConnection();
            oConn.setAutoCommit(false);
            final Statement oStmt = oConn.createStatement();
            if (update) {
                rows = oStmt.executeUpdate("update administrator_login_time set login_time = sysdate() where user_id='" + a_strUserID + "'");
            }
            else {
                rows = oStmt.executeUpdate("insert into administrator_login_time values ('" + a_strUserID + "',sysdate())");
            }
            oStmt.close();
            oConn.commit();
            oConn.setAutoCommit(true);
            oConn.close();
        }
        catch (SQLException e) {}
        catch (Exception ex) {}
        return rows;
    }
    
    public static synchronized int setLoginTime(final String a_strUserID) {
        int rows = 0;
        try {
            final Connection oConn = NewDataBaseLayer.ds.getConnection();
            final Statement oStmt = oConn.createStatement();
            rows = oStmt.executeUpdate("delete from administrator_logged_in_at where user_id = '" + a_strUserID + "'");
            rows = oStmt.executeUpdate("insert into administrator_logged_in_at values ('" + a_strUserID + "',sysdate())");
            oConn.close();
        }
        catch (SQLException e) {
            System.out.println(" error due to SQL exception");
        }
        catch (Exception ex) {
            System.out.println(" error due to java.lang.exception");
            ex.printStackTrace();
            System.out.println(" printStack is :: " + ex.getMessage());
        }
        return rows;
    }
    
    public static synchronized int decreaseAdministrator(final String id) {
        int rows = 0;
        try {
            final Connection oConn = NewDataBaseLayer.ds.getConnection();
            oConn.setAutoCommit(false);
            final Statement oStmt = oConn.createStatement();
            rows = oStmt.executeUpdate("delete from administrator_logged_in_at where user_id = '" + id + "'");
            oStmt.close();
            oConn.setAutoCommit(true);
            oConn.close();
        }
        catch (Exception ex) {}
        return rows;
    }
    
    static {
        log = new SimpleLogger((Class)NewDataBaseLayer.class);
        NewDataBaseLayer.ds = CoreAdminInitHostInfo.ds;
    }
}
