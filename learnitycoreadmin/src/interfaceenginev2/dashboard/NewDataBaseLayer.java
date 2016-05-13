package interfaceenginev2.dashboard;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.sql.DataSource;

import org.grlea.log.SimpleLogger;

import comv2.aunwesha.param.CoreAdminInitHostInfo;

/**
 * Title:        LearnITy
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Aunwesha Knowledge Technologies Pvt. Ltd.
 * @author 		Sumanta Roy
 * @version 	 5.0
 */

public class NewDataBaseLayer {
	
	
	/* public static DataSource ds=InitHostInformation.ds;
	 public static Hashtable htID;
	 private static String LastID;    */
// 	 public static DataSource ds=InitHostInformation.ds;
 public static DataSource ds=CoreAdminInitHostInfo.ds;
 public static DataSource ds1=CoreAdminInitHostInfo.ds1;


	 
  	private static final SimpleLogger log = new SimpleLogger(NewDataBaseLayer.class);
// 	public synchronized static boolean insertDashboard(String strDId, String strName, String strdesc) {
// 		Statement  oStmt;
//     	boolean flag = false;
// 		
// //     	//DBConnectionManager connMgr = DBConnectionManager.getInstance();
// //         //Connection oConn = connMgr.getConnection("mysql");
// // 	
//     	
//     	try	{
// 		Connection oConn = ds.getConnection(); 
//       		oConn.setAutoCommit(false);
//       		oStmt = oConn.createStatement();
//     		flag=oStmt.execute("insert into dashboardmgmt (id, title, description) values('"+strDId+"','"+strName+"','"+strdesc+"')");
// 			
// 		oConn.commit();
// 		oConn.setAutoCommit(true);
//       		oStmt.close();
//     	}
//     	catch (SQLException e) {
//     		System.out.println(e.toString());
//       		
//     	}
//     	catch (Exception ex) {
//       		System.out.println(ex.toString());
//     	}
//     	//connMgr.freeConnection("mysql", oConn);
// 		return flag;
// 	}
// 	
	
// 	public synchronized static boolean modifyDashboard(String strId, String strName,String desc) {
// 		
// 		Statement  oStmt;
//     	boolean flag = false;
//     	
//     	
// //     	//DBConnectionManager connMgr = DBConnectionManager.getInstance();
// //         //Connection oConn = connMgr.getConnection("mysql");
// 	Connection oConn =null;
//     	try	{
// 		 oConn = ds.getConnection();
//       		oConn.setAutoCommit(false);
//       		oStmt = oConn.createStatement();
//       		flag=oStmt.execute("update dashboardmgmt set title = '"+strName+"',description ='"+desc+"' where id = '"+strId+"'");
// 			
// 			
// 		oConn.commit();
// 		oConn.setAutoCommit(true);
//       		oStmt.close();
//     	}
//     	catch (SQLException e) {
//       		try {
// 				System.err.print("Transaction is being ");
// 				System.err.println("rolled back");
// 				oConn.rollback();
// 			}
// 			catch(SQLException excep) {
// 				System.err.print("SQLException: "+excep);
// 				
// 			}
//     	}
//     	catch (Exception ex) {
//       		try {
// 				System.err.print("Transaction is being ");
// 				System.err.println("rolled back");
// 				oConn.rollback();
// 			}
// 			catch(SQLException excep) {
// 				System.err.print("SQLException: "+excep);
// 				
// 			}
//     	}
//     	//connMgr.freeConnection("mysql", oConn);
// 		return flag;
// 	}
	
	
// 	public synchronized static boolean deleteDashboard(String strId) {
// 		
// 		Statement  oStmt;
// 		boolean flag = false;
// 		
// 		//DBConnectionManager connMgr = DBConnectionManager.getInstance();
//         	//Connection oConn = connMgr.getConnection("mysql");
// 			
// 		try	{
// 			Connection oConn = ds.getConnection(); 
// 	  		oConn.setAutoCommit(false);
// 	  		oStmt = oConn.createStatement();
// 	  		flag=oStmt.execute("delete from dashboardmgmt where id = '"+strId+"'");
// 			oConn.commit();
// 			oConn.setAutoCommit(true);
// 	  		oStmt.close();
// 		}
// 		catch (SQLException e) {
// 	  		System.out.println("Error=="+e);
// 		}
// 		catch (Exception ex) {
// 	  		System.out.println("Error=="+ex);
// 		}
// 		//connMgr.freeConnection("mysql", oConn);
// 		return flag;
// 	}
// 	
	
	/////////////////////////matrics//////////////////////
	
// 	public synchronized static boolean insertmatrics(String strDId, String strName, String strdesc,String textdata,String numberdata,String numberdata2,String numberdata3,String legenddata,String legenddata2,String legenddata3,String type,String subtype,String width,String height,String yaxis,String xaxis,String bcolor,String transpose,String stacked,String dimension) {
// 		Statement  oStmt;
//     	boolean flag = false;
// 		
//     	//DBConnectionManager connMgr = DBConnectionManager.getInstance();
//         //Connection oConn = connMgr.getConnection("mysql");
//     	
//     	try	{
// 		Connection oConn = null; 	
//       		oConn.setAutoCommit(false);
//       		oStmt = oConn.createStatement();
// 		flag=oStmt.execute("insert into matricsmgmt (id, title, description,textdata,numberdata,numberdata2,numberdata3,legenddata1,legenddata2,legenddata3,type,subtype,width,height,yaxis,xaxis,color,transpose,stacked,dimension) values('"+strDId+"','"+strName+"','"+strdesc+"','"+textdata+"','"+numberdata+"','"+numberdata2+"','"+numberdata3+"','"+legenddata+"','"+legenddata2+"','"+legenddata3+"','"+type+"','"+subtype+"','"+width+"','"+height+"','"+yaxis+"','"+xaxis+"','"+bcolor+"','"+transpose+"','"+stacked+"','"+dimension+"')");
// 			
// 		oConn.commit();
// 		oConn.setAutoCommit(true);
//       		oStmt.close();
//     	}
//     	catch (SQLException e) {
//     		System.out.println(e.toString());
//       		
//     	}
//     	catch (Exception ex) {
//       		System.out.println(ex.toString());
//     	}
//     	//connMgr.freeConnection("mysql", oConn);
// 		return flag;
// 	}
	
	
// 	public synchronized static boolean modifymatrics(String strId, String strName,String desc,String textdata,String numberdata,String numberdata2,String numberdata3,String legenddata,String legenddata2,String legenddata3,String type,String subtype,String width,String height,String yaxis,String xaxis,String bcolor,String transpose,String stacked,String dimension) {
// 		
// 		Statement  oStmt;
//     	boolean flag = false;
//     	
// 	
//     	//DBConnectionManager connMgr = DBConnectionManager.getInstance();
//         //Connection oConn = connMgr.getConnection("mysql");
// 	Connection oConn =null;
//     	try	{
// 		oConn = ds.getConnection(); 	
//       		oConn.setAutoCommit(false);
//       		oStmt = oConn.createStatement();
// 		flag=oStmt.execute("update matricsmgmt set title = '"+strName+"',description ='"+desc+"',textdata='"+textdata+"',numberdata='"+numberdata+"',numberdata2='"+numberdata2+"',numberdata3='"+numberdata3+"',legenddata1='"+legenddata+"',legenddata2='"+legenddata2+"',legenddata3='"+legenddata3+"',type='"+type+"',subtype='"+subtype+"',width='"+width+"',height='"+height+"',yaxis='"+yaxis+"',xaxis='"+xaxis+"',color='"+bcolor+"',transpose='"+transpose+"',stacked='"+stacked+"',dimension='"+dimension+"' where id = '"+strId+"'");
// 			
// 			
// 		oConn.commit();
// 		oConn.setAutoCommit(true);
//       		oStmt.close();
//     	}
//     	catch (SQLException e) {
//       		
// 				System.err.print("SQLException: "+e);
// 			
//     	}
//     	catch (Exception ex) {
//       		try {
// 				System.err.print("Transaction is being ");
// 				System.err.println("rolled back");
// 				oConn.rollback();
// 			}
// 			catch(SQLException excep) {
// 				System.err.print("SQLException: "+excep);
// 				
// 			}
//     	}
//     	//connMgr.freeConnection("mysql", oConn);
// 		return flag;
// 	}
// 	
	
// 	public synchronized static boolean deletematrics(String strId) {
// 		
// 		Statement  oStmt;
// 		boolean flag = false;
// 		
// 		//DBConnectionManager connMgr = DBConnectionManager.getInstance();
//         	//Connection oConn = connMgr.getConnection("mysql");
// 		
// 		try	{
// 			Connection oConn = null; 	
// 	  		oConn.setAutoCommit(false);
// 	  		oStmt = oConn.createStatement();
// 	  		System.out.println("delete from matricsmgmt where id = '"+strId+"'");
// 	  		flag=oStmt.execute("delete from matricsmgmt where id = '"+strId+"'");
// 			oConn.commit();
// 			oConn.setAutoCommit(true);
// 	  		oStmt.close();
// 		}
// 		catch (SQLException e) {
// 	  		System.out.println("Error=="+e);
// 		}
// 		catch (Exception ex) {
// 	  		System.out.println("Error=="+ex);
// 		}
// 		//connMgr.freeConnection("mysql", oConn);
// 		return flag;
// 	}
	
	////////////////////////association///////////////////
	
// 	public synchronized static boolean insertdashboardmatrics(String strDId, String strMid) {
// 		Statement  oStmt;
//     	boolean flag = false;
//     	    	
//     	//DBConnectionManager connMgr = DBConnectionManager.getInstance();
//         //Connection oConn = connMgr.getConnection("mysql");
// 	
//     	try	{
// 		Connection oConn = null; 	
//       		oConn.setAutoCommit(false);
//       		oStmt = oConn.createStatement();
//     		flag=oStmt.execute("insert into matricdashboard (dashboard_id, matrics_id) values('"+strDId+"','"+strMid+"')");
// 			
// 		oConn.commit();
// 		oConn.setAutoCommit(true);
//       		oStmt.close();
//     	}
//     	catch (SQLException e) {
//     		System.out.println(e.toString());
//       		
//     	}
//     	catch (Exception ex) {
//       		System.out.println(ex.toString());
//     	}
//     	//connMgr.freeConnection("mysql", oConn);
// 		return flag;
// 	}
// 	
// 	
	
	
	
// 	public synchronized static boolean deletedashboardmatrics(String strId,String strMid) {
// 		
// 		Statement  oStmt;
// 		boolean flag = false;
// 		Connection oConn = null; 	
// 		//DBConnectionManager connMgr = DBConnectionManager.getInstance();
//         	//Connection oConn = connMgr.getConnection("mysql");
// 		
// 		try	{
// 			oConn = ds.getConnection(); 
// 	  		oConn.setAutoCommit(false);
// 	  		oStmt = oConn.createStatement();
// 	  		flag=oStmt.execute("delete from matricdashboard where dashboard_id = '"+strId+"' and matrics_id='"+strMid+"'");
// 			oConn.commit();
// 			oConn.setAutoCommit(true);
// 	  		oStmt.close();
// 		}
// 		catch (SQLException e) {
// 	  		System.out.println("Error=="+e);
// 		}
// 		catch (Exception ex) {
// 	  		System.out.println("Error=="+ex);
// 		}
// 		//connMgr.freeConnection("mysql", oConn);
// 		return flag;
// 	}
// 	
	
// 	public synchronized static Vector getDashboard() {
//     	Statement  oStmt;
//     	ResultSet  oRset;
// 		Vector vAdministratorList = new Vector();
// 		Connection oConn = null; 	
// 		//DBConnectionManager connMgr = DBConnectionManager.getInstance();
//         //Connection oConn = connMgr.getConnection("mysql");
// 		
//     	try	{
// 		oConn = ds.getConnection(); 	
//       		oStmt = oConn.createStatement();
//       		oRset=oStmt.executeQuery("select id, title from dashboardmgmt");
// 			while(oRset.next())	{
// 				Vector vAdministrator = new Vector();
// 				vAdministrator.addElement(oRset.getString(1));
// 				vAdministrator.addElement(oRset.getString(2));
// 				vAdministratorList.addElement(vAdministrator);
// 			}
//       		oRset.close();
//       		oStmt.close();
//     	}
//     	catch (SQLException e){
//       		System.out.println(" error due to SQL exception");
//       		//int errCode=e.getErrorCode();
//       		//String errMessage = e.getMessage();
//     	}
//     	catch (Exception ex){
//       		System.out.println(" error due to java.lang.exception");
//       		ex.printStackTrace();
//       		
//     	}
//     	//connMgr.freeConnection("mysql", oConn);
// 		return vAdministratorList;
// 	}
// 	  
	  
	  
	  
// 	public synchronized static Vector getMatrics() {
//     	Statement  oStmt;
//     	ResultSet  oRset;
// 		Vector vAdministratorList = new Vector();
// 		
// 		//DBConnectionManager connMgr = DBConnectionManager.getInstance();
//         //Connection oConn = connMgr.getConnection("mysql");
// 		Connection oConn = null; 	
//     	try	{
// 		oConn = ds.getConnection(); 	
//       		oStmt = oConn.createStatement();
//       		oRset=oStmt.executeQuery("select id, title from matricsmgmt");
// 			while(oRset.next())	{
// 				Vector vAdministrator = new Vector();
// 				vAdministrator.addElement(oRset.getString(1));
// 				vAdministrator.addElement(oRset.getString(2));
// 				vAdministratorList.addElement(vAdministrator);
// 			}
//       		oRset.close();
//       		oStmt.close();
//     	}
//     	catch (SQLException e){
//       		System.out.println(" error due to SQL exception");
//       		//int errCode=e.getErrorCode();
//       		//String errMessage = e.getMessage();
//     	}
//     	catch (Exception ex){
//       		System.out.println(" error due to java.lang.exception");
//       		ex.printStackTrace();
//       		
//     	}
//     	//connMgr.freeConnection("mysql", oConn);
// 		return vAdministratorList;
// 	}
// 	
	
	
	
	
	
	
	
	
	
  	/////////////////////////////////portal///////////////////////////////////////
  	
//   public static  Vector getdashboarddetails(String id)
//     {
//         String systemaccess1 = null;
// 	Connection oConn = null; 	
// 	
//         //DBConnectionManager connMgr = DBConnectionManager.getInstance();
//         //Connection oConn = connMgr.getConnection("mysql"); 
//         Vector vv=new Vector();
//         String as[];
//         try
//         {
// 	  oConn = ds.getConnection(); 	
//             Statement statement = oConn.createStatement();
//             ResultSet resultset = statement.executeQuery("select a.type,a.textdata,a.numberdata,a.numberdata2,a.numberdata3,a.legenddata1,a.legenddata2,a.legenddata3,a.subtype,a.title,a.width,a.height,a.yaxis,a.xaxis,a.color,a.transpose,a.stacked  from matricsmgmt a,matricdashboard b where b.dashboard_id='"+id+"' and b.matrics_id=a.id");
//             while(resultset.next()){
//             	as=new String [17];
//             	as[0]=resultset.getString(1);
//             	as[1]=resultset.getString(2);
//             	as[2]=resultset.getString(3);
//             	as[3]=resultset.getString(4);
//             	as[4]=resultset.getString(5);
//             	as[5]=resultset.getString(6);
//             	as[6]=resultset.getString(7);
//             	as[7]=resultset.getString(8);
//             	as[8]=resultset.getString(9);
//             	as[9]=resultset.getString(10);
//             	as[10]=resultset.getString(11);
//             	as[11]=resultset.getString(12);
//             	as[12]=resultset.getString(13);
//             	as[13]=resultset.getString(14);
//             		as[14]=resultset.getString(15);
//             		as[15]=resultset.getString(16);
//             		as[16]=resultset.getString(17);
//             	vv.addElement(as);
//            // vv.addElement(resultset.getString(1));
//            // vv.addElement(resultset.getString(2));
//             //vv.addElement(resultset.getString(3));
//             // vv.addElement(resultset.getString(4));
//            // System.out.println("99999999999999999999999ffffffffffffff"+systemaccess1);
//         }
//             resultset.close();
//             statement.close();
//         }
//         catch(SQLException sqlexception)
//         {
// 		sqlexception.printStackTrace();
//         }
//         catch(Exception exception)
//         {
//             System.out.println("Inside NewDataBaseLayer getPluginName(), Exception !!!!");
//             exception.printStackTrace();
//         }
//         //connMgr.freeConnection("mysql", oConn);
//         return vv;
//     }     
// 	
	
	

 public static Vector getBarChartText(String sql)
    {
        //DBConnectionManager connMgr = DBConnectionManager.getInstance();
        //Connection oConn = connMgr.getConnection("mysql"); 
        Vector vector = new Vector(4, 4);
	Connection oConn = null; 	
        try
        {
		 oConn = ds1.getConnection(); 	
            Statement statement = oConn.createStatement();
            Statement statement1 = oConn.createStatement();
           
            
          
           
          	ResultSet resultset = statement1.executeQuery(sql); 
            	while(resultset.next()){
                vector.addElement(resultset.getString(1));
             //   System.out.println("Course Name========="+resultset.getString(1));
        	}
		
            resultset.close();
        	
            statement.close();
            
//  Added on 15-Oct-2015 by Diptendu
            
            oConn.close();            
        }
        catch(SQLException sqlexception)
        {
           sqlexception.printStackTrace();
            
        }
        catch(Exception exception)
        {
            
            exception.printStackTrace();
            
        }
        return vector;
    }
 	
 	
  public static ArrayList getNumberdata(String sql)
    {
        //DBConnectionManager connMgr = DBConnectionManager.getInstance();
        //Connection oConn = connMgr.getConnection("mysql"); 
        Vector vector = new Vector(4, 4);
        ArrayList vn1 = new ArrayList();
        ResultSet resultset;
	    Connection oConn = null;
	    Statement statement1;
        try
        {
		     oConn = ds1.getConnection(); 
             statement1 = oConn.createStatement();
            long llvv=0;
            for(resultset = statement1.executeQuery(sql); resultset.next();)
            {
                
                double lll=resultset.getDouble(1);
               // llvv=llvv+lll;
              
              // double ddd=(double)llvv;
               // double bbb=(ddd/60);
                lll=Math.floor(lll);
		vn1.add(lll);
	}
        	
            resultset.close();
            statement1.close();
            oConn.close();
        }
        catch(SQLException sqlexception)
        {
           sqlexception.printStackTrace();
            System.out.println("Error due to SQL exception inside NewDataBaseLayer.getNumberdata()");
            int i = sqlexception.getErrorCode();
            String s2 = sqlexception.getMessage();
        }
        catch(Exception exception)
        {
            System.out.println("Exception inside NewDataBaseLayer.getNumberdata()");
            exception.printStackTrace();
            System.out.println(" printStack is :: " + exception.getMessage());
        }
        return vn1;
    }
    
    
    //////////////////////////////////////SHIBAJI/////////////////////////////////////////////
    public static ArrayList getNumberdata1(String sql1)
    {
        //DBConnectionManager connMgr = DBConnectionManager.getInstance();
        //Connection oConn = connMgr.getConnection("mysql"); 
        Vector vector = new Vector(4, 4);
        ArrayList vn2 = new ArrayList();
	    Connection oConn = null; 	
	    Statement statement1;
	    ResultSet resultset;
        
        try
        {
		 oConn = ds1.getConnection(); 
            statement1 = oConn.createStatement();
            long llvv=0;
            //String sql=;
	    System.out.println("----getNumberdata1---------"+sql1);
           for(resultset = statement1.executeQuery(sql1); resultset.next();)
            
            {
                
                double lll=resultset.getDouble(1);
               // llvv=llvv+lll;
              
              // double ddd=(double)llvv;
               // double bbb=(ddd/60);
                lll=Math.floor(lll);
		vn2.add(lll);
	}
        	
           resultset.close();
           statement1.close();
           oConn.close();
        }
        catch(SQLException sqlexception)
        {
           sqlexception.printStackTrace();
            System.out.println("Error due to SQL exception inside NewDataBaseLayer.getNumberdata1()");
            int i = sqlexception.getErrorCode();
            String s2 = sqlexception.getMessage();
        }
        catch(Exception exception)
        {
            System.out.println("Exception inside NewDataBaseLayer.getNumberdata1()");
            exception.printStackTrace();
            System.out.println(" printStack is :: " + exception.getMessage());
        }
        return vn2;
    }
    
    
    
    
    
    
    
    
    
    
    
    ///////////////////////////////////SHIBAJI////////////////////////////////////////////////
 	
 //////////////////////////////////////////dial//////////////
  public static Vector getdialTextData(String sql)
    {
        //DBConnectionManager connMgr = DBConnectionManager.getInstance();
        //Connection oConn = connMgr.getConnection("mysql"); 
        Vector vector = new Vector(4, 4);
	Connection oConn = null; 	
        try
        {
		oConn = ds1.getConnection(); 
            Statement statement = oConn.createStatement();
            Statement statement1 = oConn.createStatement();
           
            
          
           
          	ResultSet resultset = statement1.executeQuery(sql); 
            	while(resultset.next()){
                vector.addElement(resultset.getString(1));
             //   System.out.println("Course Name========="+resultset.getString(1));
        	}
		
            resultset.close();
        	
            statement.close();
            
//  Added on 15-Oct-2015 by Diptendu
            
            oConn.close();            
        }
        catch(SQLException sqlexception)
        {
           sqlexception.printStackTrace();
            System.out.println("Error due to SQL exception inside NewDataBaseLayer.getdialTextData()");
            int i = sqlexception.getErrorCode();
            String s2 = sqlexception.getMessage();
        }
        catch(Exception exception)
        {
            System.out.println("Exception inside NewDataBaseLayer.getdialTextData()");
            exception.printStackTrace();
            System.out.println(" printStack is :: " + exception.getMessage());
        }
        return vector;
    }	
 	
 
  public static ArrayList getdialNumberdata(String sql)
    {
        //DBConnectionManager connMgr = DBConnectionManager.getInstance();
        //Connection oConn = connMgr.getConnection("mysql"); 
        Vector vector = new Vector(4, 4);
        ArrayList vn1 = new ArrayList();
         // ResultSet resultset;
	Connection oConn = null; 	
        try
        {
		oConn = ds1.getConnection(); 
             Statement statement1 = oConn.createStatement();
            long llvv=0;
           for(ResultSet resultset = statement1.executeQuery(sql); resultset.next();)
            
            {
                
                double lll=resultset.getDouble(1);
               // llvv=llvv+lll;
              
              // double ddd=(double)llvv;
               // double bbb=(ddd/60);
                lll=Math.floor(lll);
		vn1.add(lll);
	}
        	
            statement1.close();
        }
        catch(SQLException sqlexception)
        {
           sqlexception.printStackTrace();
            System.out.println("Error due to SQL exception inside NewDataBaseLayer.getdialNumberdata()");
            int i = sqlexception.getErrorCode();
            String s2 = sqlexception.getMessage();
        }
        catch(Exception exception)
        {
            System.out.println("Exception inside NewDataBaseLayer.getdialNumberdata()");
            exception.printStackTrace();
            System.out.println(" printStack is :: " + exception.getMessage());
        }
        return vn1;
    }
 
 
 //////////////////////////////////////////pie//////////////
  public static Vector getpieTextData(String sql)
    {
        //DBConnectionManager connMgr = DBConnectionManager.getInstance();
        //Connection oConn = connMgr.getConnection("mysql"); 
        Vector vector = new Vector(4, 4);
	Connection oConn = null; 	
        try
        {
		oConn = ds1.getConnection(); 
            Statement statement = oConn.createStatement();
            Statement statement1 = oConn.createStatement();
           
            
	    System.out.println("--getpieTextData---------"+sql);
           
          	ResultSet resultset = statement1.executeQuery(sql); 
            	while(resultset.next()){
                vector.addElement(resultset.getString(1));
             //   System.out.println("Course Name========="+resultset.getString(1));
        	}
		
            resultset.close();
        	
            statement.close();
            
//  Added on 15-Oct-2015 by Diptendu
            
            oConn.close();            
        }
        catch(SQLException sqlexception)
        {
           sqlexception.printStackTrace();
            System.out.println("Error due to SQL exception inside NewDataBaseLayer.getpieTextData()");
            int i = sqlexception.getErrorCode();
            String s2 = sqlexception.getMessage();
        }
        catch(Exception exception)
        {
            System.out.println("Exception inside NewDataBaseLayer.getpieTextData()");
            exception.printStackTrace();
            System.out.println(" printStack is :: " + exception.getMessage());
        }
        return vector;
    }	
 	
 
  public static ArrayList getpieNumberdata(String sql)
    {
        //DBConnectionManager connMgr = DBConnectionManager.getInstance();
        //Connection oConn = connMgr.getConnection("mysql"); 
        Vector vector = new Vector(4, 4);
        ArrayList vn1 = new ArrayList();
    ResultSet resultset = null;
	Connection oConn = null; 	
        try
        {
		oConn = ds1.getConnection(); 
             Statement statement1 = oConn.createStatement();
            long llvv=0;
	    System.out.println("--getpieNumberdata---------"+sql);
           for(resultset = statement1.executeQuery(sql); resultset.next();)
            
            {
                
                double lll=resultset.getDouble(1);
               // llvv=llvv+lll;
              
               //double ddd=(double)llvv;
               // double bbb=(ddd/60);
                lll=Math.floor(lll);
		vn1.add(lll);
	}
        	
            statement1.close();
            resultset.close();
            
//  Added on 15-Oct-2015 by Diptendu
            
            oConn.close();            
            
        }
        catch(SQLException sqlexception)
        {
           sqlexception.printStackTrace();
            System.out.println("Error due to SQL exception inside NewDataBaseLayer.getpieNumberdata()");
            int i = sqlexception.getErrorCode();
            String s2 = sqlexception.getMessage();
        }
        catch(Exception exception)
        {
            System.out.println("Exception inside NewDataBaseLayer.getpieNumberdata()");
            exception.printStackTrace();
            System.out.println(" printStack is :: " + exception.getMessage());
        }
        return vn1;
    }
 
 
 
 //////////////////////////////////////////Line//////////////
  public static Vector getLineTextData(String sql)
    {
        //DBConnectionManager connMgr = DBConnectionManager.getInstance();
        //Connection oConn = connMgr.getConnection("mysql"); 
        Vector vector = new Vector(4, 4);
	Connection oConn = null; 	
        try
        {
		oConn = ds1.getConnection(); 
            Statement statement = oConn.createStatement();
            Statement statement1 = oConn.createStatement();
           
            
          
           
          	ResultSet resultset = statement1.executeQuery(sql); 
            	while(resultset.next()){
                vector.addElement(resultset.getString(1));
             //   System.out.println("Course Name========="+resultset.getString(1));
        	}
		
            resultset.close();
        	
            statement.close();
            
//  Added on 15-Oct-2015 by Diptendu
            
            oConn.close();            
        }
        catch(SQLException sqlexception)
        {
           sqlexception.printStackTrace();
            System.out.println("Error due to SQL exception inside NewDataBaseLayer.getLineTextData()");
            int i = sqlexception.getErrorCode();
            String s2 = sqlexception.getMessage();
        }
        catch(Exception exception)
        {
            System.out.println("Exception inside NewDataBaseLayer.getLineTextData()");
            exception.printStackTrace();
            System.out.println(" printStack is :: " + exception.getMessage());
        }
        return vector;
    }	
 	
 
  public static ArrayList getlineNumberdata(String sql)
    {
        //DBConnectionManager connMgr = DBConnectionManager.getInstance();
        //Connection oConn = connMgr.getConnection("mysql"); 
        Vector vector = new Vector(4, 4);
        ArrayList vn1 = new ArrayList();
         // ResultSet resultset;
	Connection oConn = null; 	
        try
        {
		oConn = ds1.getConnection(); 
             Statement statement1 = oConn.createStatement();
            long llvv=0;
           for(ResultSet resultset = statement1.executeQuery(sql); resultset.next();)
            
            {
                
                double lll=resultset.getDouble(1);
               // llvv=llvv+lll;
              
             //  double ddd=(double)llvv;
               // double bbb=(ddd/60);
                lll=Math.floor(lll);
		vn1.add(lll);
	}
        	
            statement1.close();
        }
        catch(SQLException sqlexception)
        {
           sqlexception.printStackTrace();
            System.out.println("Error due to SQL exception inside NewDataBaseLayer.getlineNumberdata()");
            int i = sqlexception.getErrorCode();
            String s2 = sqlexception.getMessage();
        }
        catch(Exception exception)
        {
            System.out.println("Exception inside NewDataBaseLayer.getlineNumberdata()");
            exception.printStackTrace();
            System.out.println(" printStack is :: " + exception.getMessage());
        }
        return vn1;
    }
 
  public static  Vector getdashboardLayoutdetails(String id)
    {
        String systemaccess1 = null;
	Connection oConn = null; 	
        //DBConnectionManager connMgr = DBConnectionManager.getInstance();
        //Connection oConn = connMgr.getConnection("mysql"); 
        Vector vv=new Vector();
        String as[];
        try
        {
		oConn = ds1.getConnection(); 
            Statement statement = oConn.createStatement();
            ResultSet resultset = statement.executeQuery("select a.width,a.height from matricsmgmt a,matricdashboard b where b.dashboard_id='"+id+"' and b.matrics_id=a.id");
            while(resultset.next()){
            //	as=new String [4];
            //	as[0]=resultset.getString(1);
            //	as[1]=resultset.getString(2);
            //	as[2]=resultset.getString(3);
            //	as[3]=resultset.getString(4);
           // 	vv.addElement(as);
            vv.addElement(resultset.getInt(1));
            vv.addElement(resultset.getInt(2));
           // vv.addElement(resultset.getString(3));
            // vv.addElement(resultset.getString(4));
           // System.out.println("99999999999999999999999ffffffffffffff"+systemaccess1);
        }
            resultset.close();
            statement.close();
            
//  Added on 15-Oct-2015 by Diptendu
            
            oConn.close();            
        }
        catch(SQLException sqlexception)
        {
		sqlexception.printStackTrace();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        //connMgr.freeConnection("mysql", oConn);
        return vv;
    }     
////////////////////////////////////// Dashboard Export and Import //////////////////////////

//  public static synchronized Vector getid(String id)
//     {
//     	//DBConnectionManager connMgr = DBConnectionManager.getInstance();
//         //Connection oConn = connMgr.getConnection("mysql");
// 	    Connection oConn = null; 	
//         Vector vector = new Vector();
//         try
//         {
// 		oConn = ds.getConnection(); 
//             Statement statement = oConn.createStatement();
//             //String as[];
//             for(ResultSet oRset = statement.executeQuery("select distinct(a.dashboard_id),b.title from dashboardmgmt b,matricdashboard a where a.dashboard_id='"+id+"' and a.dashboard_id = b.id ");oRset.next();)
//             {
//               String as[] = new String[2];
//               as[0] = oRset.getString(1);
//               as[1] = oRset.getString(2);
//              
//              // as.addElement(oRset.getString(2));
//               /*as[1]=oRset.getString(2);
//               as[2]=oRset.getString(3);
//               as[3]=oRset.getString(4);
//               as[4]=oRset.getString(5);
//                as[5]=oRset.getString(6);
//                 as[6]=oRset.getString(7);*/
//               
//               vector.addElement(as);
//             }
// 
//             statement.close();
//         }
//         catch(SQLException sqlexception)
//         {
//             System.out.println("Exception in NewDataBaseLayer.getdashboardid()");
//             System.out.println("The Error Message - " + sqlexception.getMessage());
//         }
//         catch(Exception exception)
//         {
//             System.out.println("Exception in NewDataBaseLayer.getdashboardid()");
//             System.out.println("The Error Message - " + exception.getMessage());
//         }
//         //connMgr.freeConnection("mysql", oConn);
//         return vector;
//     }    






//  public static synchronized Vector getdashboardid(String module)
//     {
//     	//DBConnectionManager connMgr = DBConnectionManager.getInstance();
//         //Connection oConn = connMgr.getConnection("mysql");
// 	    Connection oConn = null; 	
//         Vector vector = new Vector();
//         try
//         {
// 		oConn = ds.getConnection(); 
//             Statement statement = oConn.createStatement();
//             //String as[];
//             for(ResultSet oRset = statement.executeQuery("select a.matrics_id,b.title,a.dashboard_id from matricdashboard a,dashboardmgmt b where dashboard_id ='"+module+"' and a.dashboard_id=b.id ");oRset.next();)
//             {
//               String as[] = new String[3];
//               as[0] = oRset.getString(1);
//               as[1] = oRset.getString(2);
//               as[2] = oRset.getString(3);
//              // as.addElement(oRset.getString(2));
//               /*as[1]=oRset.getString(2);
//               as[2]=oRset.getString(3);
//               as[3]=oRset.getString(4);
//               as[4]=oRset.getString(5);
//                as[5]=oRset.getString(6);
//                 as[6]=oRset.getString(7);*/
//               
//               vector.addElement(as);
//             }
// 
//             statement.close();
//         }
//         catch(SQLException sqlexception)
//         {
//             System.out.println("Exception in NewDataBaseLayer.getdashboardid()");
//             System.out.println("The Error Message - " + sqlexception.getMessage());
//         }
//         catch(Exception exception)
//         {
//             System.out.println("Exception in NewDataBaseLayer.getdashboardid()");
//             System.out.println("The Error Message - " + exception.getMessage());
//         }
//         //connMgr.freeConnection("mysql", oConn);
//         return vector;
//     }    


//  public static synchronized Vector getMetricDetails(String m_id)
//     {
//     	//DBConnectionManager connMgr = DBConnectionManager.getInstance();
//         //Connection oConn = connMgr.getConnection("mysql");
// 	    Connection oConn = null; 	
//         Vector vector = new Vector();
//         try
//         {
// 		oConn = ds.getConnection(); 
//             Statement statement = oConn.createStatement();
//             //String as[];
//             for(ResultSet oRset = statement.executeQuery("select id,title,description,textdata,numberdata,numberdata2,numberdata3,legenddata1,legenddata2,legenddata3,type,subtype,width,height,yaxis,xaxis,color,transpose,stacked from matricsmgmt where id='"+m_id+"'");oRset.next();)
//             {
//               String as[] = new String[19];
//               as[0]=oRset.getString(1);
//              
//               as[1]=oRset.getString(2);
//               as[2]=oRset.getString(3);
//               as[3]=oRset.getString(4);
//               as[4]=oRset.getString(5);
//               as[5]=oRset.getString(6);
//               as[6]=oRset.getString(7);
//               as[7]=oRset.getString(8);
//               as[8]=oRset.getString(9);
//               as[9]=oRset.getString(10);
//               as[10]=oRset.getString(11);
//               as[11]=oRset.getString(12);
//               as[12]=oRset.getString(13);
//               as[13]=oRset.getString(14);
//               as[14]=oRset.getString(15);
//               as[15]=oRset.getString(16);
//               as[16]=oRset.getString(17);
//               as[17]=oRset.getString(18);
//               as[18]=oRset.getString(19);
//               
//               vector.addElement(as);
//             }
// 
//             statement.close();
//         }
//         catch(SQLException sqlexception)
//         {
//             System.out.println("Exception in NewDataBaseLayer.getMetricDetails()");
//             System.out.println("The Error Message - " + sqlexception.getMessage());
//         }
//         catch(Exception exception)
//         {
//             System.out.println("Exception in NewDataBaseLayer.getMetricDetails()");
//             System.out.println("The Error Message - " + exception.getMessage());
//         }
//         //connMgr.freeConnection("mysql", oConn);
//         return vector;
//     }    

// public static synchronized void ImportDashDef(String id,String title)
//       {
//       	//DBConnectionManager connMgr = DBConnectionManager.getInstance();
//         //Connection oConn = connMgr.getConnection("mysql");
// 	      
// 	      Connection oConn = null; 	
//     	try
//             {
// 		    oConn = ds.getConnection(); 
// 				oConn.setAutoCommit(false);
// 				Statement statement1 = oConn.createStatement();
// 				
// 
// 				
// 				statement1.execute("insert into dashboardmgmt(id, title) values('"+id+"','"+title+"')"); 
// 				
// 				
// 			
// 				
// 				
// 				
// 				
// 				statement1.close();
// 				oConn.commit();
// 				oConn.setAutoCommit(true);
//             }
//         
//     	catch(SQLException sqlexception)
//         {
//             System.out.println("Exception in NewDatabaseLayer.ImportDashDef()");
//             System.out.println("The Error Message - " + sqlexception.getMessage());
//         }
//         catch(Exception ex)
//         {
//             System.out.println("Exception in NewDatabaseLayer.ImportDashDef()");
//             System.out.println("The Error Message - " + ex.getMessage());
//         }
//         //connMgr.freeConnection("mysql", oConn);
//     }

// public static synchronized void ImportMatricData(String id,String c_def[])
//       {
//       	//DBConnectionManager connMgr = DBConnectionManager.getInstance();
//         //Connection oConn = connMgr.getConnection("mysql");
// 	      Connection oConn = null; 	
// 	      
//     	try
//             {
// 				oConn.setAutoCommit(false);
// 				Statement statement1 = oConn.createStatement();
// 				
// 
// 				
// 				statement1.execute("insert into matricdashboard(dashboard_id, matrics_id) values('"+id+"','"+c_def[0]+"')"); 
// 				statement1.execute("insert into matricsmgmt(id,title,description,textdata,numberdata,numberdata2,numberdata3,legenddata1,legenddata2,legenddata3,type,subtype,width,height,xaxis,yaxis,color,transpose,stacked) values ('"+c_def[0]+"','"+c_def[1]+"','"+c_def[2]+"','"+c_def[3]+"','"+c_def[4]+"','"+c_def[5]+"','"+c_def[6]+"','"+c_def[7]+"','"+c_def[8]+"','"+c_def[9]+"','"+c_def[10]+"','"+c_def[11]+"','"+c_def[12]+"','"+c_def[13]+"','"+c_def[14]+"','"+c_def[15]+"','"+c_def[16]+"','"+c_def[17]+"','"+c_def[18]+"')");
// 				
// 			
// 				
// 				
// 				
// 				
// 				statement1.close();
// 				oConn.commit();
// 				oConn.setAutoCommit(true);
//             }
//         
//     	catch(SQLException sqlexception)
//         {
//             System.out.println("Exception in NewDatabaseLayer.ImportMatricData()");
//             System.out.println("The Error Message - " + sqlexception.getMessage());
//         }
//         catch(Exception ex)
//         {
//             System.out.println("Exception in NewDatabaseLayer.ImportMatricData()");
//             System.out.println("The Error Message - " + ex.getMessage());
//         }
//         //connMgr.freeConnection("mysql", oConn);
//     }
// 
// 


    public static ArrayList getNumberdata2(String sql1)
    {
        //DBConnectionManager connMgr = DBConnectionManager.getInstance();
        //Connection oConn = connMgr.getConnection("mysql"); 
        Vector vector = new Vector(4, 4);
        ArrayList vn3 = new ArrayList();
	Connection oConn = null; 	
	Statement statement1;
	ResultSet resultset;
        try
        {
		oConn = ds1.getConnection(); 
             statement1 = oConn.createStatement();
            long llvv=0;
            //String sql=;
	    log.debug("---------"+sql1);
           for(resultset = statement1.executeQuery(sql1); resultset.next();)
            
            {
                
                double lll=resultset.getDouble(1);
               // llvv=llvv+lll;
              
              // double ddd=(double)llvv;
               // double bbb=(ddd/60);
                lll=Math.floor(lll);
		vn3.add(lll);
	}
           resultset.close();
            statement1.close();
            oConn.close();
        }
        catch(SQLException sqlexception)
        {
           sqlexception.printStackTrace();
            
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            
        }
        return vn3;
    }


    public static  Vector getmetdetails(String interface_id,String part_id)
    {
        String systemaccess1 = null;
        
        //DBConnectionManager connMgr = DBConnectionManager.getInstance();
        //Connection oConn = connMgr.getConnection("mysql"); 
	Connection oConn = null; 	
        Vector vv=new Vector();
        String as[];
        try
        {
		oConn = ds.getConnection(); 
            Statement statement = oConn.createStatement();
	    //ResultSet resultset = statement.executeQuery("select a.type,a.textdata,a.numberdata,a.numberdata2,a.numberdata3,a.legenddata1,a.legenddata2,a.legenddata3,a.subtype,a.title,a.width,a.height,a.yaxis,a.xaxis,a.color,a.transpose,a.stacked,a.dimension  from matricsmgmt a where a.id='"+id+"'");
	    System.out.println("-----------a----------SELECT i.chart_type, i.X_axis_query, i.series1_query, i.series2_query, i.series3_query, i.legenddata1, i.legenddata2, i.legenddata3, i.subtype,  i.metric_title, i.width, i.height, i.yaxis_label, i.xaxis_label, i.color, i.transpose, i.stacked, i.chart_dimension   FROM  interface_chart_content i  WHERE i.interface_id='"+interface_id+"' AND i.part_id='"+part_id+"' ");
	    ResultSet resultset = statement.executeQuery("SELECT i.chart_type, i.X_axis_query, i.series1_query, i.series2_query, i.series3_query, i.legenddata1, i.legenddata2, i.legenddata3, i.subtype,  i.metric_title, i.width, i.height, i.yaxis_label, i.xaxis_label, i.color, i.transpose, i.stacked, i.chart_dimension   FROM  interface_chart_content i  WHERE i.interface_id='"+interface_id+"' AND i.part_id='"+part_id+"' ");
	    
            while(resultset.next()){
            	as=new String [18];
            	as[0]=resultset.getString(1);
            	as[1]=resultset.getString(2);
            	as[2]=resultset.getString(3);
            	as[3]=resultset.getString(4);
            	as[4]=resultset.getString(5);
            	as[5]=resultset.getString(6);
            	as[6]=resultset.getString(7);
            	as[7]=resultset.getString(8);
            	as[8]=resultset.getString(9);
            	as[9]=resultset.getString(10);
            	as[10]=resultset.getString(11);
            	as[11]=resultset.getString(12);
            	as[12]=resultset.getString(13);
            	as[13]=resultset.getString(14);
            		as[14]=resultset.getString(15);
            		as[15]=resultset.getString(16);
            		as[16]=resultset.getString(17);
			as[17]=resultset.getString(18);
            	vv.addElement(as);
           // vv.addElement(resultset.getString(1));
           // vv.addElement(resultset.getString(2));
            //vv.addElement(resultset.getString(3));
            // vv.addElement(resultset.getString(4));
           // System.out.println("99999999999999999999999ffffffffffffff"+systemaccess1);
        }
            resultset.close();
            statement.close();            
            
//  Added on 15-Oct-2015 by Diptendu
            
            oConn.close();
            
        }
        catch(SQLException sqlexception)
        {
		sqlexception.printStackTrace();
        }
        catch(Exception exception)
        {
            System.out.println("Inside NewDataBaseLayer getPluginName(), Exception !!!!");
            exception.printStackTrace();
        }
        //connMgr.freeConnection("mysql", oConn);
        return vv;
    }     



    public static  Vector getmetLayoutdetails(String interface_id,String part_id)
    {
        String systemaccess1 = null;
	Connection oConn = null; 	
        //DBConnectionManager connMgr = DBConnectionManager.getInstance();
        //Connection oConn = connMgr.getConnection("mysql"); 
        Vector vv=new Vector();
        String as[];
        try
        {
		oConn = ds.getConnection(); 
            Statement statement = oConn.createStatement();
//             ResultSet resultset = statement.executeQuery("select a.width,a.height from matricsmgmt a where a.id='"+id+"'");
	    System.out.println("-----------------------------select a.width,a.height from interface_chart_content a where a.interface_id='"+interface_id+"' and a.part_id='"+part_id+"'");
	    ResultSet resultset = statement.executeQuery("select a.width,a.height from interface_chart_content a where a.interface_id='"+interface_id+"' and a.part_id='"+part_id+"'");
            while(resultset.next()){
            //	as=new String [4];
            //	as[0]=resultset.getString(1);
            //	as[1]=resultset.getString(2);
            //	as[2]=resultset.getString(3);
            //	as[3]=resultset.getString(4);
           // 	vv.addElement(as);
            vv.addElement(resultset.getInt(1));
            vv.addElement(resultset.getInt(2));
           // vv.addElement(resultset.getString(3));
            // vv.addElement(resultset.getString(4));
           // System.out.println("99999999999999999999999ffffffffffffff"+systemaccess1);
        }
            resultset.close();
            statement.close();
            
//  Added on 15-Oct-2015 by Diptendu
            
            oConn.close();            
        }
        catch(SQLException sqlexception)
        {
		sqlexception.printStackTrace();
        }
        catch(Exception exception)
        {
            log.debug("Inside NewDataBaseLayer getdashboardLayoutdetails(), Exception !!!!");
            exception.printStackTrace();
        }
        //connMgr.freeConnection("mysql", oConn);
        return vv;
    }     


 /*public static synchronized Vector getMetricDetails1(String m_id)
    {
    	//DBConnectionManager connMgr = DBConnectionManager.getInstance();
        //Connection oConn = connMgr.getConnection("mysql");
	    Connection oConn = null; 	
        Vector vector = new Vector();
        try
        {
		oConn = ds.getConnection(); 
            Statement statement = oConn.createStatement();
            //String as[];
            for(ResultSet oRset = statement.executeQuery("select * from matricsmgmt where id='"+m_id+"'");oRset.next();)
            {
              String as[] = new String[19];
              as[0]=oRset.getString(1);
             
              as[1]=oRset.getString(2);
              as[2]=oRset.getString(3);
              as[3]=oRset.getString(4);
              as[4]=oRset.getString(5);
              as[5]=oRset.getString(6);
              as[6]=oRset.getString(7);
              as[7]=oRset.getString(8);
              as[8]=oRset.getString(9);
              as[9]=oRset.getString(10);
              as[10]=oRset.getString(11);
              as[11]=oRset.getString(12);
              as[12]=oRset.getString(13);
              as[13]=oRset.getString(14);
              as[14]=oRset.getString(15);
              as[15]=oRset.getString(16);
              as[16]=oRset.getString(17);
              as[17]=oRset.getString(18);
              as[18]=oRset.getString(19);
              
              vector.addElement(as);
            }

            statement.close();
        }
        catch(SQLException sqlexception)
        {
            System.out.println("Exception in NewDataBaseLayer.getMetricDetails1()");
            System.out.println("The Error Message - " + sqlexception.getMessage());
        }
        catch(Exception exception)
        {
            System.out.println("Exception in NewDataBaseLayer.getMetricDetails1()");
            System.out.println("The Error Message - " + exception.getMessage());
        }
        //connMgr.freeConnection("mysql", oConn);
        return vector;
    } */   

// public static synchronized void ImportMatricData1(String c_def[])
//       {
//       	//DBConnectionManager connMgr = DBConnectionManager.getInstance();
//         //Connection oConn = connMgr.getConnection("mysql");
// 	      Connection oConn = null; 	
//     	try
//             {
// 				
// 		    oConn = ds.getConnection(); 
// 		    oConn.setAutoCommit(false);
// 				Statement statement1 = oConn.createStatement();
// 				
// 
// 				
// 				//statement1.execute("insert into matricdashboard(dashboard_id, matrics_id) values('"+id+"','"+c_def[0]+"')"); 
// 				statement1.execute("insert into matricsmgmt(id,title,description,textdata,numberdata,numberdata2,numberdata3,legenddata1,legenddata2,legenddata3,type,subtype,width,height,xaxis,yaxis,color,transpose,stacked,dimension) values ('"+c_def[0]+"','"+c_def[1]+"','"+c_def[2]+"','"+c_def[3]+"','"+c_def[4]+"','"+c_def[5]+"','"+c_def[6]+"','"+c_def[7]+"','"+c_def[8]+"','"+c_def[9]+"','"+c_def[10]+"','"+c_def[11]+"','"+c_def[12]+"','"+c_def[13]+"','"+c_def[14]+"','"+c_def[15]+"','"+c_def[16]+"','"+c_def[17]+"','"+c_def[18]+"','TwoDimensionalwithdepth')");
// 				
// 			
// 				//statement1.execute("insert into matricsmgmt(id,title,description,textdata,numberdata,numberdata2,numberdata3,legenddata1,legenddata2,legenddata3,type,subtype,width,height,xaxis,yaxis,color,transpose,stacked) values ('"+c_def[0]+"','"+c_def[1]+"','"+c_def[2]+"','"+c_def[3]+"','"+c_def[4]+"','"+c_def[5]+"','"+c_def[6]+"','"+c_def[7]+"','"+c_def[8]+"','"+c_def[9]+"','"+c_def[10]+"','"+c_def[11]+"','"+c_def[12]+"','"+c_def[13]+"','"+c_def[14]+"','"+c_def[15]+"','"+c_def[16]+"','"+c_def[17]+"','"+c_def[18]+"')");
// 				
// 			
// 				
// 				
// 				
// 				
// 				statement1.close();
// 				oConn.commit();
// 				oConn.setAutoCommit(true);
//             }
//         
//     	catch(SQLException sqlexception)
//         {
//             System.out.println("Exception in NewDatabaseLayer.ImportMatricData1()");
//             System.out.println("The Error Message - " + sqlexception.getMessage());
//         }
//         catch(Exception ex)
//         {
//             System.out.println("Exception in NewDatabaseLayer.ImportMatricData1()");
//             System.out.println("The Error Message - " + ex.getMessage());
//         }
//         //connMgr.freeConnection("mysql", oConn);
//     }
//     
    
    
//  public static synchronized Vector getAllMetrics()
//     {
//     	//DBConnectionManager connMgr = DBConnectionManager.getInstance();
//         //Connection oConn = connMgr.getConnection("mysql");
// 	    Connection oConn = null; 	
//         Vector vector = new Vector();
//         try
//         {
// 		oConn = ds.getConnection(); 
//             Statement statement = oConn.createStatement();
//             //String as[];
//             for(ResultSet oRset = statement.executeQuery("select id from matricsmgmt");oRset.next();)
//             {
//               String as[] = new String[1];
//               as[0]=oRset.getString(1);
//              
//          
//               vector.addElement(as);
//             }
// 
//             statement.close();
//         }
//         catch(SQLException sqlexception)
//         {
//             System.out.println("Exception in NewDataBaseLayer.getMetricDetails1()");
//             System.out.println("The Error Message - " + sqlexception.getMessage());
//         }
//         catch(Exception exception)
//         {
//             System.out.println("Exception in NewDataBaseLayer.getMetricDetails1()");
//             System.out.println("The Error Message - " + exception.getMessage());
//         }
//         //connMgr.freeConnection("mysql", oConn);
//         return vector;
//     }     

	
}
	                                                                           
