package coreadministrationv2.dbconnection;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.sql.DataSource;

import org.grlea.log.DebugLevel;
import org.grlea.log.SimpleLogger;

import comv2.aunwesha.param.CoreAdminInitHostInfo;
import coreadministrationv2.sysmgmt.xml.dto.manifest.InterfaceElement;
/**
 * Title:        
 * Description:
 * Copyright:    Copyright (c) 2008
 * Company:      Aunwesha
 * @author 	 Chandan Kumar Konar	and Shibaji Chatterjee
 * @version 	 
 */

public class DataBaseLayer
{
	public static final SimpleLogger log = new SimpleLogger(DataBaseLayer.class, true);
	public static DataSource ds=CoreAdminInitHostInfo.ds;
	public DataBaseLayer()
	{
	}


	public static void errorMessage(int errCode, PrintWriter out) {

		out.println("<font color=#ff0000>* ");
		switch(errCode) {
		case 1216:
			out.println("No referrenced data.");
			break;
		case 1217:
			out.println("You might have deleted a record that has " +
					"a referrence related to it, or you might have violated " +
					"a check constraint. The delete operation has been terminated.");
			break;
		case 1062:
			out.println("Insertion not possible. Duplicate entry. " +
					"The INSERT statement has been terminated.");
			break;
		case 1203:
			out.println("Too many user connected to the database. "+
					"Please try after some time.");
			break;
		case 1196:
			out.println("Warring rollback not completed.");
			break;
		case 1171:
			out.println("Id cnnot be null.");
			break;
		case 218:
			out.println("Database Error. Contact database administrator.");
			break;
		case 1063:
			out.println("Already Registered.");
			break;
		case 1064:
			out.println("First you have to upload imsmanifest file for the given unit.");
			break;
		case 1065:
			out.println("First you have to register the prerequisite unit");
			break;	
		case 1066:
			out.println("First you have to complete the prerequisite unit");
			break;        

		case 2000: out.println("The Unit Already Associated");
		break;         

		case 2001: out.println("Give With Minimum Days");
		break;         


		case 2002: out.println("Give With Maximum Days");
		break;                   
		default:
			out.println(" Database Error. Contact database administrator.");

		}
		out.println("</font>");
		out.println("<BR>");
	}


	public synchronized static void addAdminUser(String strUserId, String strPassword,String strAccessTillAllowed,String strAging ,String strCreatedBy) {
		Connection oConn =null;


		try
		{
			oConn =ds.getConnection();
			oConn.setAutoCommit(false);
			Statement statement = oConn.createStatement();

			statement.execute("Insert into admin_user(user_id, password, access_allowed_till,password_aging,created_by,creation_date) values ('" + strUserId + "','" + strPassword + "','" + strAccessTillAllowed + "','" + strAging + "','" + strCreatedBy + "',sysdate())");
			statement.close();
			oConn.commit();
			oConn.setAutoCommit(true);
		}
		catch(SQLException e){
			//log.debug("Exception in addQuestionBank.Rolling Back Operation");
			//e.printStackTrace();
			log.fatal("SQLException:");
			log.dbe(DebugLevel.L1_FATAL, e);
			try{
				// oConn.rollback();
			}catch(Exception re){
				//log.debug("Exception while rolling back");
				//re.printStackTrace();
				log.fatal("Exception:");
				log.dbe(DebugLevel.L1_FATAL, re);
			}

		}


	}

	public synchronized static boolean deleteAdminUserDetails(String stid, PrintWriter out) {

		Statement  oStmt;
		//ResultSet  oRset;
		boolean l_bAuth = false ;

		try	{
			Connection oConn =ds.getConnection();
			oConn.setAutoCommit(false);
			oStmt = oConn.createStatement();
			l_bAuth = oStmt.execute("delete from admin_user where user_id ='"+stid+"'" );
			//l_bAuth = oStmt.execute("delete from student_creation_details where student_id ='"+stid+"'" );
			//l_bAuth = oStmt.execute("delete from student_password where student_id ='"+stid+"'" );
			oConn.commit();
			oConn.setAutoCommit(true);
			oStmt.close();
		}
		catch (SQLException e) {

		}
		catch (Exception ex) {

		}

		return l_bAuth;
	}

	public synchronized static boolean   updateAdminUserPassword(String sid ,String spass ) {
		Statement  oStmt;
		boolean succ =false ;
		log.debug("***********Studend id*******************"+sid);
		log.debug("***********Studend password*******************"+spass);




		try	{
			Connection oConn =ds.getConnection();
			oStmt = oConn.createStatement();
			oStmt.execute("update  admin_user set password='"+spass+"' where user_id ='"+sid+"'");
			oStmt.close();
			succ = true ;
		}
		catch (SQLException e) {
			log.debug(" error due to SQL exception "+e.toString());
			//int errCode=e.getErrorCode();
			//String errMessage = e.getMessage();
		}
		catch (Exception ex) {
			log.debug(" error due to java.lang.exception");
			ex.printStackTrace();
			log.debug(" printStack is :: " + ex.getMessage());
		}

		return succ ;
	}

	public synchronized static Vector<Vector<String>> getAdminUser() {

		Statement  oStmt;
		ResultSet  oRset;
		Vector<Vector<String>> vPUserList = new Vector<Vector<String>>();

		try	{
			Connection oConn =ds.getConnection();
			oStmt = oConn.createStatement();
			oRset=oStmt.executeQuery("select user_id from admin_user");
			while(oRset.next()) {
				Vector<String> vPUser = new Vector<String>();
				vPUser.addElement(oRset.getString(1));
				log.debug("DataBaseLayer.getAdminUser:user_id"+oRset.getString(1));

				vPUserList.addElement(vPUser);

				//oRset1.close();
				//oStmt1.close();
			}
			oRset.close();
			oStmt.close();
		}
		catch (SQLException e) {
			log.debug("getCourseDetailsList: error due to SQL exception");
			//int errCode=e.getErrorCode();
			//String errMessage = e.getMessage();
		}
		catch (Exception ex) {
			log.debug("getCourseDetailsList1: error due to SQL exception");
			ex.printStackTrace();
			log.debug(" printStack is :: " + ex.getMessage());
		}

		return vPUserList;
	}

	public synchronized static Vector<Vector<String>> getPersonList(String UserType) {

		Statement  oStmt;
		ResultSet  oRset=null;
		Statement  oStmt1;
		ResultSet  oRset1=null;
		Vector<Vector<String>> vPUserList = new Vector<Vector<String>>();




		try	{
			Connection oConn =ds.getConnection();
			oStmt = oConn.createStatement();
			oStmt1 = oConn.createStatement();
			if(UserType==null)
				//return null;
				oRset=oStmt.executeQuery("select person_id from person_details");
			else{
				if(UserType.equals("Student"))
				{
					oRset=oStmt.executeQuery("select a.person_id from person_details a,student_personal_details b where a.person_id=b.person_id");
				}
				if(UserType.equals("FacultyMember"))
				{
					oRset=oStmt.executeQuery("select a.person_id from person_details a,faculty_member b where a.person_id=b.person_id");
				}
				if(UserType.equals("Employee"))
				{
					oRset=oStmt.executeQuery("select a.person_id from person_details a,employee_master b where a.person_id=b.person_id");
				}
			}
			//oRset1=oStmt1.executeQuery("select concat(first_name,' ', middle_name,' ', last_name) from person_details where person_id=");
			while(oRset.next()) {
				Vector<String> vPUser = new Vector<String>();
				vPUser.addElement(oRset.getString(1));
				log.debug("DataBaseLayer.getPortalUser:person_id"+oRset.getString(1));
				String personid=oRset.getString(1);
				log.debug("***************personid************"+personid);
				//oRset1=oStmt1.executeQuery("select concat(first_name,' ', middle_name,' ', last_name) from person_details where person_id="+personid+"");
				oRset1=oStmt1.executeQuery("select first_name, middle_name, last_name from person_details where person_id='"+personid+"'");
				while(oRset1.next()) {
					log.debug("DataBaseLayer.getPortalUser:person_name"+oRset1.getString(1));
					vPUser.addElement(oRset1.getString(1)+""+oRset1.getString(2)+""+oRset1.getString(3));
				}
				vPUserList.addElement(vPUser);

				//oRset1.close();
				//oStmt1.close();
			}
			oRset.close();
			oStmt.close();
		}
		catch (SQLException e) {
			log.debug("getPersonList: error due to SQL exception");
			//int errCode=e.getErrorCode();
			//String errMessage = e.getMessage();
		}
		catch (Exception ex) {
			log.debug("getPersonList: error due to SQL exception");
			ex.printStackTrace();
			log.debug(" printStack is :: " + ex.getMessage());
		}

		return vPUserList;
	}

	public synchronized static void AdminUserPersonAssociation(String Userid,String UserType, int Personid,String strCreatedBy) {




		try
		{
			Connection oConn = ds.getConnection();
			oConn.setAutoCommit(false);
			Statement statement = oConn.createStatement();

			statement.execute("Insert into admin_user_person_association(user_id,user_type, person_id, associated_by,association_date) values ('" + Userid + "','" + UserType + "','" + Personid+ "','" + strCreatedBy + "',sysdate())");
			statement.close();
			oConn.commit();
			oConn.setAutoCommit(true);
		}
		catch(SQLException e){
			//log.debug("Exception in addQuestionBank.Rolling Back Operation");
			//e.printStackTrace();
			log.fatal("SQLException:");
			log.dbe(DebugLevel.L1_FATAL, e);
			try{
				// oConn.rollback();
			}catch(Exception re){
				//log.debug("Exception while rolling back");
				//re.printStackTrace();
				log.fatal("Exception:");
				log.dbe(DebugLevel.L1_FATAL, re);
			}

		}

	}

	public synchronized static boolean modifyAdminUserPerson(String Userid,String UserType, int Personid,String strCreatedBy) {

		Statement  oStmt;
		boolean count = false;


		try {
			Connection oConn = ds.getConnection();
			oConn.setAutoCommit(false);
			oStmt = oConn.createStatement();

			oStmt.executeQuery("update admin_user_person_association set person_id = '"+Personid+"',user_type = '"+UserType+"',associated_by = '"+strCreatedBy+"',association_date = sysdate() where user_id = '"+Userid+"'");

			oStmt.close();
			oConn.commit();
			oConn.setAutoCommit(true);
			count = true;
		}
		catch (SQLException e) {

		}
		catch (Exception ex) {


		}

		return count ;
	}

	public synchronized static boolean deleteAdminUserPerson(String strUserId) {
		Statement  oStmt;
		//ResultSet  oRset;
		boolean l_bAuth = false ;
		//CourseDetails l_arCourseDetails = null;




		try {
			Connection oConn = ds.getConnection();
			oStmt = oConn.createStatement();
			oStmt.execute("delete from admin_user_person_association where user_id ='"+strUserId+"'" );
			oStmt.close();
			l_bAuth = true;
		}
		catch (SQLException e) {
			log.debug(" error due to SQL exception");
			//int errCode=e.getErrorCode();
			//String errMessage = e.getMessage();
		}
		catch (Exception ex) {
			log.debug(" error due to java.lang.exception");
			ex.printStackTrace();
			log.debug(" printStack is :: " + ex.getMessage());
		}

		return l_bAuth;
	}

	public synchronized static void addrole(String title, String strDesc) {
		try
		{
			Connection oConn = ds.getConnection();
			oConn.setAutoCommit(false);
			Statement statement = oConn.createStatement();
			String sql="Insert into role(title,description) values ('" +title+ "','" + strDesc+ "')";
			log.debug(sql);
			statement.execute(sql);
			//boolean flag = statement.execute();            
			statement.close();
			oConn.commit();
			oConn.setAutoCommit(true);
		}
		catch(SQLException e){
			log.debug("SQLException in addrole. Rolling Back Operation "+e);
			try{
				//oConn.rollback();
			}catch(Exception re){log.debug("Exception: "+re);}

		}


	}


	public synchronized static boolean modifyrole(String role_id,String title,String desc) {

		Statement  oStmt;
		boolean count = false;



		try {
			Connection oConn = ds.getConnection();
			oConn.setAutoCommit(false);
			oStmt = oConn.createStatement();
			//log.debug("**************modifyCourse11******************************");
			oStmt.executeQuery("update role set title = '"+title+"',description = '"+desc+"' where role_id = '"+role_id+"'");
			//log.debug("**************modifyCourse22******************************");
			oStmt.close();
			oConn.commit();
			oConn.setAutoCommit(true);
			count = true;
		}
		catch (SQLException e) {

		}
		catch (Exception ex) {


		}
		return count ;
	}

	public synchronized static boolean deleterole(String role_id) {
		Statement  oStmt;
		//ResultSet  oRset;
		boolean l_bAuth = false ;
		//CourseDetails l_arCourseDetails = null;




		try {
			Connection oConn = ds.getConnection();
			oStmt = oConn.createStatement();
			oStmt.execute("delete from role where role_id ='"+role_id+"'" );
			oStmt.close();
			l_bAuth = true;
		}
		catch (SQLException e) {
			log.debug(" error due to SQL exception");
			//int errCode=e.getErrorCode();
			//String errMessage = e.getMessage();
		}
		catch (Exception ex) {
			log.debug(" error due to java.lang.exception");
			ex.printStackTrace();
			log.debug(" printStack is :: " + ex.getMessage());
		}
		return l_bAuth;
	}


	public static void insertinterface(String s,String type,String interfacetype,String fsize)
	{

		Connection connection =null;
		Statement statement = 	null;	 
		try
		{
			connection = ds.getConnection();
			statement = connection.createStatement();
			statement.execute("Insert into interface(interface_id,interface_title,type,filesize) values ('" + s + "','"+type+"','"+interfacetype+"','"+fsize+"')");
			statement.close();
			connection.close();
		}
		catch(SQLException sqlexception)
		{
			//log.fatal("Exception in DataBaseLayer.style()");
			//log.dbe(DebugLevel.L1_FATAL,sqlexception);

			while((sqlexception = sqlexception.getNextException()) != null) 
			{
				//log.fatal("The Error Message");
				//log.dbe(DebugLevel.L1_FATAL,sqlexception);

			}
		}
		catch(Exception exception)
		{


		}
		finally{
			if(connection!=null)
			{
				try
				{

					statement.close();
					connection.close();
				}catch(Exception e){}	
			}
		}	
	}

	public static void insertpart(String interface_id,String s,String part_class,String resize,String border,String cols,String rows,String scrolling,String spacing,String colspan,String maxlength,String size,String tabindex,String archieve,String codebase,String mayscript,String loadurl,String editurl,String caption,String sortname,String sortorder,String treedataremotefunction,String onselectremotefunction,String autocollapse,String initializeonload,String gridhidden,String gridnavbar,String multiselect,String rownum,String rowlist,String dateformat,String lazynode,String tooltip,String multiboxonly,String parentquery,String childquery,String tree_parameter,String resetSearchOnClose,String multiplesearch,String customeditbutton,String griddata,String griddatatype)
	{

		Connection connection =null;
		Statement statement = null;
		//Statement statement1 = null;
		PreparedStatement pstmt =null;
		try
		{
			connection = ds.getConnection();
			statement = connection.createStatement();
			//statement1 = connection.createStatement();
			statement.execute("Insert into structure(interface_id,part_id,part_class,resize,border,cols,rows,scrolling,spacing,colspan,length,size,tabindex,archieve,codebase,mayscript,loadurl,editurl,caption,sortname,sortorder,gridhidden,gridnavbar,multiselect,gridrowNum,gridrowList,dateformat,multiboxonly,resetSearchOnClose,multiplesearch,customeditbutton,griddata,griddatatype) values ('" + interface_id + "','" + s + "','"+part_class+"','"+resize+"','"+border+"','"+cols+"','"+rows+"','"+scrolling+"','"+spacing+"','"+colspan+"','"+maxlength+"','"+size+"','"+tabindex+"','"+archieve+"','"+codebase+"','"+mayscript+"','"+loadurl+"','"+editurl+"','"+caption+"','"+sortname+"','"+sortorder+"','"+gridhidden+"','"+gridnavbar+"','"+multiselect+"','"+rownum+"','"+rowlist+"','"+dateformat+"','"+multiboxonly+"','"+resetSearchOnClose+"','"+multiplesearch+"','"+customeditbutton+"','"+griddata+"','"+griddatatype+"')");
			statement.close();
			// 				statement1.execute("Insert into tree_structure(interface_id,part_id,treedataremotefunction,onselectremotefunction,autocollapse,initialiseonload,islazynode,tooltip) values ('" + interface_id + "','" + s + "','"+treedataremotefunction+"','"+onselectremotefunction+"','"+autocollapse+"','"+initializeonload+"','"+lazynode+"','"+tooltip+"')");
			//             statement1.close();

			pstmt = connection.prepareStatement("Insert into tree_structure(interface_id,part_id,treedataremotefunction,onselectremotefunction,autocollapse,initialiseonload,islazynode,tooltip,parentsql,childnodesql,tree_parameter) values (?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString( 1, interface_id);
			pstmt.setString( 2, s);
			pstmt.setString( 3, treedataremotefunction);
			pstmt.setString( 4, onselectremotefunction);
			pstmt.setString( 5, autocollapse);
			pstmt.setString( 6, initializeonload);
			pstmt.setString( 7, lazynode);
			pstmt.setString( 8, tooltip);
			pstmt.setString( 9, parentquery);
			pstmt.setString( 10, childquery);
			pstmt.setString( 11, tree_parameter);
			pstmt.executeUpdate();
			pstmt.close();
			connection.close();
		}
		catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();

		}
		catch(Exception exception)
		{


		}
		finally
		{
			if(connection!=null)
			{
				try
				{
//Modified by Diptendu 02-Nov-2015					
					if(statement!=null) statement.close();
					if(pstmt!=null) pstmt.close();
//end modification					
					connection.close();
				} catch(Exception e){}	
			}
		}

	}

	public static void insertlayout(String layout_id, String part_id, String height, String width, String left, String top, String position, String parent_id, 
			String interface_id)
	{

		Connection connection = null;
		Statement statement = null;
		try
		{
			connection = ds.getConnection();
			statement = connection.createStatement();
			statement.execute((new StringBuilder()).append("Insert into layout(layout_id,part_id,height,width,x,y,position,parent_id,interface_id) values ('").append(layout_id).append("','").append(part_id).append("','").append(height).append("','").append(width).append("','").append(left).append("','").append(top).append("','").append(position).append("','").append(parent_id).append("','").append(interface_id).append("')").toString());
			statement.close();
			connection.close();
		}
		catch(SQLException sqlexception)
		{
			while((sqlexception = sqlexception.getNextException()) != null) ;
		}
		catch(Exception exception) { }
		finally
		{
			if(connection!=null)
			{
				try
				{
					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		}

	}

	public static void insertcontent(String content_id,String part_id, String value, String valuetype, String interface_id,String contentlocation,String show_choose_one,String choose_one_label,String choose_one_value)
	{
		Connection connection =null;
		Statement statement = null;

		try
		{
			connection = ds.getConnection();
			statement = connection.createStatement();
			statement.execute("Insert into content(content_id,part_id,value,contenttype,interface_id,active_value,contentlocation,show_choose_one,choose_one_label,choose_one_value) values ('"+content_id+"','"+part_id+"','"+value+"','"+valuetype+"','"+interface_id+"','"+value+"','"+contentlocation+"','"+show_choose_one+"','"+choose_one_label+"','"+choose_one_value+"')");
			statement.close();
			connection.close();
		}
		catch(SQLException sqlexception)
		{
			while((sqlexception = sqlexception.getNextException()) != null){
				sqlexception.printStackTrace();
			}
			
		}
		catch(Exception exception) { }
		finally
		{
			if(connection!=null)
			{
				try
				{
					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		}
	}

	public static void insertcontentcdata(String content_id,String part_id, String value, String valuetype, String interface_id,String contentlocation)
	{

		Connection connection =null;
		Statement statement = null;
		try
		{
			connection = ds.getConnection();
			statement = connection.createStatement();
			statement.execute("Insert into content(content_id,part_id,contenttype,interface_id,contentlocation) values ('"+content_id+"','"+part_id+"','"+valuetype+"','"+interface_id+"','"+contentlocation+"')");
			PreparedStatement pstmt = connection.prepareStatement("update  content SET value= ? ,active_value= ? where content_id=? and interface_id=? and part_id=?");
			pstmt.setString( 1, value);
			pstmt.setString( 2, value);
			pstmt.setString(3,content_id);
			pstmt.setString(4,interface_id);
			pstmt.setString(5,part_id);
			pstmt.executeUpdate();
			pstmt.close();

			statement.close();
			connection.close();
		}
		catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();
			while((sqlexception = sqlexception.getNextException()) != null) ;
		}
		catch(Exception exception) { }
		finally
		{
			if(connection!=null)
			{
				try
				{
					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		}
	}


	public static void insertstyle(String style_id ,String part_id, String value, String valuetype, String interface_id)
	{

		Connection connection =null;
		Statement statement =null;
		try
		{
			connection = ds.getConnection();
			statement = connection.createStatement();
			statement.execute("Insert into style(style_id,part_id,value,styletype,interface_id) values ('"+style_id+"','"+part_id+"','"+value+"','"+valuetype+"','"+interface_id+"')");
			statement.close();
			connection.close();
		}
		catch(SQLException sqlexception)
		{
			while((sqlexception = sqlexception.getNextException()) != null) ;
		}
		catch(Exception exception) { }
		finally
		{
			if(connection!=null)
			{
				try
				{
					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		}
	}

	public static void insertbehaviour(String behaviour_id,String part_id, String value, String valuetype, String target, String event1, String interface_id, String type, String resourceid, String callback, String javaclass,String package_name,String resource_location)
	{
		Connection connection =null;
		Statement statement = null;

		try
		{
			connection =ds.getConnection();
			statement = connection.createStatement();
			statement.execute("Insert into behaviour(behaviour_id,part_id,value,valuetype,target,behaviourevent,interface_id,type,callback,resourceid,javaclass,package,resource_location) values ('"+behaviour_id+"','"+part_id+"','"+value+"','"+valuetype+"','"+target+"','"+event1+"','"+interface_id+"','"+type+"','"+callback+"','"+resourceid+"','"+javaclass+"','"+package_name+"','"+resource_location+"')");
			statement.close();
			connection.close();
		}
		catch(SQLException sqlexception)
		{
			while((sqlexception = sqlexception.getNextException()) != null) ;
		}
		catch(Exception exception) { }
		finally
		{
			if(connection!=null)
			{
				try
				{
					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		}

	}
	public static void insertresource(String id,String href,String name,String childname,String type,String keyvalue,String interface_id,String resource_location,String userId)
	{

		Connection connection =null;
		Statement statement = null;


		try
		{
			connection =ds.getConnection();
			statement = connection.createStatement();

			String xml="";
			ResourceBundle rb = ResourceBundle.getBundle("portal",Locale.getDefault());      
			String key1= "xml"; 
			xml = rb.getString(key1);
			String file_path =xml+name+File.separator+name+File.separator+childname+File.separator+childname+File.separator+href;
			// System.out.println("...................FILEPATH......................."+file_path);
			File inFile=new File(file_path);
			InputStream inStream= new FileInputStream(inFile);


			statement.execute("Insert into resource(resource_id,href,type,keyvalue,interface_id,resource_location,uploaded_by,date) values ('" +id + "','" +href+"','"+type+"','"+keyvalue+"','"+interface_id+"','"+resource_location+"','"+userId+"',NOW())");


			//  System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Insert into resource(resource_id,href,type,keyvalue,interface_id) values ('" +id + "','" +href+"','"+type+"','"+keyvalue+"','"+interface_id+"')");

			PreparedStatement pstmt = connection.prepareStatement("update  resource SET value= ?, size= ? where resource_id=? and href=? and type=?");
			pstmt.setBinaryStream( 1, inStream, (int)(inFile.length()));
			pstmt.setString(2, (inFile.length())+" Bytes");
			pstmt.setString(3,id);
			pstmt.setString(4,href);
			pstmt.setString(5,type);

			pstmt.executeUpdate();
			pstmt.close();
			statement.close();
			connection.close();
		}
		catch(SQLException sqlexception)
		{       //log.fatal("Exception in DataBaseLayer.style()");
			//log.dbe(DebugLevel.L1_FATAL,sqlexception);
			while((sqlexception = sqlexception.getNextException()) != null) 
			{   //log.fatal("The Error Message");
				//log.dbe(DebugLevel.L1_FATAL,sqlexception);
			}
		}
		catch(Exception exception)
		{


		}
		finally
		{
			if(connection!=null)
			{
				try
				{
					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		}

	}


	public static void insertresourceinterface(String id,String href,String path,String type,String keyvalue,String interface_id,String resource_location,String userId)
	{

		Connection connection =null;
		Statement statement = null;
		try
		{
			connection =ds.getConnection();
			statement = connection.createStatement();

			//String xml="";
			// 		    ResourceBundle rb = ResourceBundle.getBundle("portal",Locale.getDefault());      
			// 		    String key1= "xml"; 
			// 		    xml = rb.getString(key1);
			String file_path =path+href;
			System.out.println(".............................................FILE PATH......"+file_path);

			statement.execute("Insert into resource(resource_id,href,type,keyvalue,interface_id,resource_location,uploaded_by,date) values ('" +id + "','" +href+"','"+type+"','"+keyvalue+"','"+interface_id+"','"+resource_location+"','"+userId+"',NOW())");
			
			/*
			 * Changes made by Dibyarup
			 * If file does not exists then make the entry for the file but dont try to upload it.
			 * Making the entry in the table will allow the user to upload it afterwards.
			 */
			File inFile=new File(file_path);
			if(inFile.exists()){
				InputStream inStream= new FileInputStream(inFile);
				PreparedStatement pstmt = connection.prepareStatement("update  resource SET value= ?, size= ? where resource_id=? and href=? and type=?");
				pstmt.setBinaryStream( 1, inStream, (int)(inFile.length()));
				pstmt.setString(2, (inFile.length())+" Bytes");
				pstmt.setString(3,id);
				pstmt.setString(4,href);
				pstmt.setString(5,type);

				pstmt.executeUpdate();
				pstmt.close();
				inStream.close();
			}else{
				log.error(file_path +"is not present");
			}
			

			statement.close();
			connection.close();
		}
		catch(SQLException sqlexception)
		{  
			sqlexception.printStackTrace();
		}
		catch(Exception exception)
		{
			exception.printStackTrace();

		}
		finally
		{
			if(connection!=null)
			{
				try
				{
					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		}

	}



	public static void insertsnippet(String id,String value,String interface_id)
	{

		Connection connection =null;
		Statement statement = null;

		try
		{
			connection =ds.getConnection();
			statement = connection.createStatement();
			statement.execute("Insert into snippet(snippet_id,interface_id) values ('" +id + "','"+interface_id+"')");
			PreparedStatement pstmt = connection.prepareStatement("update  snippet SET value= ? where snippet_id=? ");
			pstmt.setString( 1,value);
			pstmt.setString(2,id);
			pstmt.executeUpdate();

			pstmt.close();

			statement.close();

			connection.close();
		}
		catch(SQLException sqlexception)
		{     
			//     System.out.println("................................  SNIPPET ERROR............................"+sqlexception.getMessage());
		}
		catch(Exception exception)
		{

		}
		finally
		{
			if(connection!=null)
			{
				try
				{
					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		}

	}

	/* public static  String resourcetype(String interface_id)
    {
        String loginno = null;
        DBConnectionManager connMgr = DBConnectionManager.getInstance();
        Connection oConn = connMgr.getConnection("mysql"); 
         try
        {
           Statement statement = oConn.createStatement();
           ResultSet resultset = statement.executeQuery("select type from resource where resource_id='"+resource_id+"' and interface_id='"+interface_id+"'");
            resultset.next();
            loginno=resultset.getString(1);
            resultset.close();
            statement.close();
        }
        catch(SQLException sqlexception)
        {
         }
        connMgr.freeConnection("mysql", oConn);
        return loginno;
    } 
	 */
	public synchronized static void deleteall(String interface_id)
	{
		Connection connection =null;
		Statement statement = null;

		try
		{
			connection =ds.getConnection();
			statement = connection.createStatement();
			statement.execute("delete from framework_file where framework_file_id='"+interface_id+"'");
			statement.execute("delete from interface where interface_id='"+interface_id+"'");
			statement.execute("delete from structure where interface_id='"+interface_id+"'");
			statement.execute("delete from layout where interface_id='"+interface_id+"'");
			statement.execute("delete from content where interface_id='"+interface_id+"'");
			statement.execute("delete from style where interface_id='"+interface_id+"'");
			statement.execute("delete from behaviour where interface_id='"+interface_id+"'");
			statement.execute("delete from resource where interface_id='"+interface_id+"'");
			statement.execute("delete from optionmenu where interface_id='"+interface_id+"'");
			statement.execute("delete from columnmodel where interface_id='"+interface_id+"'");
			statement.execute("delete from gridquery where interface_id='"+interface_id+"'");
			statement.execute("delete from keycolumn where interface_id='"+interface_id+"'");
			statement.execute("delete from delete_param where interface_id='"+interface_id+"'");
			statement.execute("delete from add_param where interface_id='"+interface_id+"'");
			statement.execute("delete from modify_param where interface_id='"+interface_id+"'");
			statement.execute("delete from edit_type where interface_id='"+interface_id+"'");
			statement.execute("delete from tree_structure where interface_id='"+interface_id+"'");
			statement.execute("delete from selector where interface_id='"+interface_id+"'");
			statement.execute("delete from delete_validation_param where interface_id='"+interface_id+"'");
			statement.execute("delete from add_validation_param where interface_id='"+interface_id+"'");
			statement.execute("delete from modify_validation_param where interface_id='"+interface_id+"'");
			statement.execute("delete from dropdownmenu where interface_id='"+interface_id+"'");
			statement.execute("delete from add_action_param where interface_id='"+interface_id+"'");
			statement.execute("delete from modify_action_param where interface_id='"+interface_id+"'");
			statement.execute("delete from delete_action_param where interface_id='"+interface_id+"'");
			statement.execute("delete from configuration_item where interface_id='"+interface_id+"'");
			statement.execute("delete from formelement where interface_id='"+interface_id+"'");
			statement.execute("delete from interfaceenginecalling where interface_id='"+interface_id+"'");

			statement.execute("delete from form_delete_param where interface_id='"+interface_id+"'");
			statement.execute("delete from form_add_param where interface_id='"+interface_id+"'");
			statement.execute("delete from form_modify_param where interface_id='"+interface_id+"'");
			statement.execute("delete from form_element where interface_id='"+interface_id+"'");
			statement.execute("delete from form_select_param where interface_id='"+interface_id+"'");
			statement.execute("delete from interface_chart_content where interface_id='"+interface_id+"'");
			statement.execute("delete from interface_report_content where interface_id='"+interface_id+"'");
			statement.execute("delete from report_parameter where interface_id='"+interface_id+"'");
			statement.execute("delete from flash_parameter where interface_id='"+interface_id+"'");

			statement.close();
			connection.close();
		}
		catch(SQLException sqlexception)
		{    
			//   System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>DELETE ALL>>>>>>>>>>>>>>>>>>>>"+sqlexception.getMessage());
		}
		catch(Exception exception)
		{
		}
		finally
		{
			if(connection!=null)
			{
				try
				{
					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		}
	}

	public static void insertinterfaceCollection(String s,String title,String type,String fsize)
	{

		Connection connection =null;
		Statement statement = null;
		try
		{
			connection = ds.getConnection();
			statement = connection.createStatement();

			statement.execute("Insert into interface(interface_id,interface_title,type,filesize) values ('" + s + "','"+title+"','"+type+"','"+fsize+"')");
			statement.close();
			connection.close();
		}
		catch(SQLException sqlexception)
		{
		}
		finally
		{
			if(connection!=null)
			{
				try
				{
					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		}
	}


	public static void insertinterfacemanifestassociation(String interface_id,String manifestid)
	{

		Connection connection = null;
		Statement statement = null;
		try
		{
			connection = ds.getConnection();
			statement = connection.createStatement();

			statement.execute("Insert into manifestinterfaceassociation(interface_id,manifest_id) values ('" +interface_id+ "','"+manifestid+"')");
			statement.close();
			connection.close();
		}
		catch(SQLException sqlexception)
		{
		}
		finally
		{
			if(connection!=null)
			{
				try
				{
					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		}
	}

	public synchronized static void DeleteinterfaceCollection(String manifestid)
	{
		Connection connection =null;
		Statement statement =null;
		//Statement stmt = null;

		try
		{
			connection = ds.getConnection();
			statement = connection.createStatement();
			// stmt = connection.createStatement();
			statement.execute("delete from interface where interface_id='"+manifestid+"'");
			statement.execute("delete from framework_file where framework_file_id='"+manifestid+"'");
			for (ResultSet r1=statement.executeQuery("select interface_id from manifestinterfaceassociation where manifest_id='"+manifestid+"'");r1.next();)
			{
				String interface_id=r1.getString(1);
				deleteall(interface_id);
			}
			statement.execute("delete from manifestinterfaceassociation where manifest_id='"+manifestid+"'");
			statement.close();
			connection.close();
		}
		catch(SQLException sqlexception)
		{    
			//  System.out.println(".....................................................ERROR.................................."+sqlexception.getMessage());
		}
		catch(Exception exception)
		{
		}
		finally
		{
			if(connection!=null)
			{
				try
				{
					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		}

	}

	public static void insertresourceOnly(String id,String path,String href,String interface_id,String userId)
	{
		Connection connection =null;
		Statement statement =null;
		try
		{
			connection = ds.getConnection();

			statement = connection.createStatement();
			String file_path=path+href;
			//System.out.println("...........................................................................................PATH...................."+file_path);
			File inFile=new File(file_path);

			InputStream inStream= new FileInputStream(inFile);

			PreparedStatement pstmt = connection.prepareStatement("update  resource SET value= ?, size= ?, uploaded_by= ?, date= NOW() , href= ? where resource_id=? and interface_id= ?");
			pstmt.setBinaryStream( 1, inStream, (int)(inFile.length()));
			pstmt.setString(2, (inFile.length())+" Bytes");
			pstmt.setString(3,userId);
			pstmt.setString(4,href);
			pstmt.setString(5,id);
			pstmt.setString(6,interface_id);

			pstmt.executeUpdate();

			pstmt.close();

			statement.close();
			// connection.commit();
			// connection.setAutoCommit(true);
			connection.close();
		}
		catch(SQLException sqlexception)
		{    
			//System.out.println(">>>>>>>>>>>>>>>>>>>>>>> UPDATE RESOURCE>>>>>>>>>>>>>"+sqlexception.getMessage());
		}
		catch(Exception exception)
		{


		}
		finally
		{
			if(connection!=null)
			{
				try
				{
					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		}
	}	  



	public static void InsertInterfaceXML(String href,String path,String interface_id,String type,String resource_id,String userId)
	{
		Connection connection = null;
		Statement statement = null;
		try
		{
			connection = ds.getConnection();
			statement = connection.createStatement();
			File inFile=new File(path);
			InputStream inStream= new FileInputStream(inFile);
			statement.execute("Insert into resource(interface_id,href,type,resource_id,uploaded_by,date) values ('" +interface_id + "','" +href+"','"+type+"','"+resource_id+"','"+userId+"',NOW())");
			PreparedStatement pstmt = connection.prepareStatement("update  resource SET value= ? , size= ?, href= ? where  interface_id=? and resource_id= ?");
			pstmt.setBinaryStream( 1, inStream, (int)(inFile.length()));
			pstmt.setString(2, (inFile.length())+" Bytes");
			pstmt.setString(3,href);
			pstmt.setString(4,interface_id);
			pstmt.setString(5,resource_id);
			pstmt.executeUpdate();
			pstmt.close();
			statement.close();
			connection.close();
			//connection.setAutoCommit(true);
		}
		catch(SQLException sqlexception)
		{
			System.out.println("....................................InsertInterfaceXML..........EXCEPTION.............................."+sqlexception);     
		}
		catch(Exception exception)
		{

		}
		finally
		{
			if(connection!=null)
			{
				try
				{
					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		}

	}

	public static void deletefromStructureLayoutContentStyleBehaviour(String interface_id)
	{

		Connection connection =null;
		Statement statement =null;

		try
		{
			connection =ds.getConnection();
			statement = connection.createStatement();

			statement.execute("delete from structure where interface_id='"+interface_id+"'");
			statement.execute("delete from layout where interface_id='"+interface_id+"'");
			statement.execute("delete from content where interface_id='"+interface_id+"'");
			statement.execute("delete from style where interface_id='"+interface_id+"'");
			statement.execute("delete from behaviour where interface_id='"+interface_id+"'");
			statement.execute("delete from optionmenu where interface_id='"+interface_id+"'");
			statement.execute("delete from columnmodel where interface_id='"+interface_id+"'");
			statement.execute("delete from gridquery where interface_id='"+interface_id+"'");
			statement.execute("delete from keycolumn where interface_id='"+interface_id+"'");
			statement.execute("delete from delete_param where interface_id='"+interface_id+"'");
			statement.execute("delete from add_param where interface_id='"+interface_id+"'");
			statement.execute("delete from modify_param where interface_id='"+interface_id+"'");
			statement.execute("delete from edit_type where interface_id='"+interface_id+"'");
			statement.execute("delete from tree_structure where interface_id='"+interface_id+"'");
			statement.execute("delete from selector where interface_id='"+interface_id+"'");
			statement.execute("delete from delete_validation_param where interface_id='"+interface_id+"'");
			statement.execute("delete from add_validation_param where interface_id='"+interface_id+"'");
			statement.execute("delete from modify_validation_param where interface_id='"+interface_id+"'");
			statement.execute("delete from dropdownmenu where interface_id='"+interface_id+"'");
			statement.execute("delete from add_action_param where interface_id='"+interface_id+"'");
			statement.execute("delete from modify_action_param where interface_id='"+interface_id+"'");
			statement.execute("delete from delete_action_param where interface_id='"+interface_id+"'");
			statement.execute("delete from configuration_item where interface_id='"+interface_id+"'");
			statement.execute("delete from formelement where interface_id='"+interface_id+"'");
			statement.execute("delete from interfaceenginecalling where interface_id='"+interface_id+"'");

			statement.execute("delete from form_delete_param where interface_id='"+interface_id+"'");
			statement.execute("delete from form_add_param where interface_id='"+interface_id+"'");
			statement.execute("delete from form_modify_param where interface_id='"+interface_id+"'");
			statement.execute("delete from form_element where interface_id='"+interface_id+"'");
			statement.execute("delete from form_select_param where interface_id='"+interface_id+"'");
			statement.execute("delete from interface_chart_content where interface_id='"+interface_id+"'");
			statement.execute("delete from interface_report_content where interface_id='"+interface_id+"'");
			statement.execute("delete from report_parameter where interface_id='"+interface_id+"'");


			statement.close();
			connection.close();
		}
		catch(SQLException sqlexception)
		{    
			//   System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>DELETE ALL>>>>>>>>>>>>>>>>>>>>"+sqlexception.getMessage());
		}
		catch(Exception exception)
		{
		}
		finally
		{
			if(connection!=null)
			{
				try
				{
					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		} 
	}

	public  static String getType(String resource_id) {
		String r_id="";
		Connection oConn =null;
		Statement stmt=null;	
		ResultSet resultset1=null;	
		try {
			oConn = ds.getConnection();
			stmt=oConn.createStatement();	
			resultset1=stmt.executeQuery("select type from resource where resource_id='"+resource_id+"'");
			resultset1.next();
			r_id=resultset1.getString(1);
			resultset1.close();
			stmt.close();
			oConn.close();
		}
		catch (SQLException e){

		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					resultset1.close();
					stmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		} 
		return r_id;       	
	}                                                          

	public  static String getInterfacetile(String interface_id) {
		String r_id="";
		Connection oConn =null;
		Statement stmt=null;	
		ResultSet resultset1=null;	

		try {
			oConn =ds.getConnection();
			stmt=oConn.createStatement();	
			resultset1=stmt.executeQuery("select interface_title from interface where interface_id='"+interface_id+"'");
			resultset1.next();
			r_id=resultset1.getString(1);
			resultset1.close();
			stmt.close();
			oConn.close();
		}
		catch (SQLException e){

		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					resultset1.close();
					stmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		} 	
		return r_id;       	
	}                                                          



	public static Vector<InputStream> getFileDetails(String interface_id,String resource_id,String filename)
	{

		Connection oConn =null;
		Statement  oStmt=null;
		ResultSet oRset =null;
		Vector<InputStream> cols=new Vector<InputStream>();
		try {
			oConn =ds.getConnection();
			oStmt = oConn.createStatement();
			for(oRset = oStmt.executeQuery("select value from resource where interface_id='"+interface_id+"' and resource_id='"+resource_id+"' and href='"+filename+"'");oRset.next();)
			{
				cols.addElement(oRset.getAsciiStream(1));
			}
			oStmt.close();
			oConn.close();
		}
		catch (SQLException sqlexception) {
		}
		catch (Exception ex) {
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					oRset.close();
					oStmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		} 	
		return cols;		
	}


	//////////////////////////////////////////////////////   CHANDAN KONAR START///////////////////////////////////////////////////////////////////

	public synchronized static boolean isAuthenticated(String a_strUserID, String a_strPassword, char a_chRole) {

		Statement  oStmt;
		ResultSet  oRset;
		boolean l_bAuth = false ;


		try {
			Connection oConn = ds.getConnection();
			oStmt = oConn.createStatement();
			//oRset=oStmt.executeQuery("select *  from admin_user where user_id='"+a_strUserID +"' and password='"+a_strPassword+"'");
			oRset=oStmt.executeQuery("select *  from admin_user a,admin_user_person_association b where a.user_id='"+a_strUserID +"' and a.password='"+a_strPassword+"' and a.user_id=b.user_id");
			while(oRset.next()) {
				l_bAuth = true;
				break;
			}
			oRset.close();
			oStmt.close();
			log.debug("******************l_bAuth****************************"+l_bAuth);
		}
		catch (SQLException e) {
			log.debug("Error due to SQL exception from isAuthenticated()");
			int errCode=e.getErrorCode();
			log.debug("*****errCode************"+errCode);
			log.debug(e.getMessage());
		}
		catch (Exception ex) {
			log.debug(" error due to java.lang.exception");
			ex.printStackTrace();
			log.debug(" printStack is :: " + ex.getMessage());
		}

		return l_bAuth;
	}


	public synchronized static int setLoginTime(String a_strUserID){
		Statement  oStmt;
		int rows = 0 ;




		try {
			Connection oConn =ds.getConnection();
			oStmt = oConn.createStatement();
			rows=oStmt.executeUpdate("delete from administrator_logged_in_at "+
					"where user_id = '"+a_strUserID+"'");
			rows=oStmt.executeUpdate("insert into administrator_logged_in_at "+
					"values ('"+a_strUserID+
					"',sysdate())");
		}
		catch (SQLException e){
			log.debug(" error due to SQL exception");
		}
		catch (Exception ex){
			log.debug(" error due to java.lang.exception");
			ex.printStackTrace();
			log.debug(" printStack is :: " + ex.getMessage());
		}

		return rows;
	}

	public synchronized static Vector<String> getLastLoginTime(String a_strUserID) {
		Statement  oStmt=null;
		ResultSet  oRset=null;
		Vector<String> vLoginTime = null;
		Connection oConn=null;



		try {
			oConn =ds.getConnection();
			oStmt = oConn.createStatement();
			oRset=oStmt.executeQuery("select count(*) from administrator_login_time where user_id=\'"+a_strUserID+"\'");
			int  l_i=0;
			while(oRset.next())	{
				l_i = oRset.getInt(1);
			}
			oRset.close();
			oStmt.close();
			if(l_i <= 0) {
				vLoginTime = new Vector<String>();
				oStmt = oConn.createStatement();
				//oRset=oStmt.executeQuery("select concat(administrator_details.user_first_name,' ', administrator_details.user_middle_name,' ',"+
				// "administrator_details.user_last_name) from administrator_details where user_id='"+a_strUserID+"'");
				oRset=oStmt.executeQuery("select concat(person_details.first_name,' ', person_details.middle_name,' ',"+
						"person_details.last_name) from person_details,admin_user_person_association where admin_user_person_association.user_id='"+a_strUserID+"' and admin_user_person_association.person_id=person_details.person_id");
				oRset.next();
				vLoginTime.addElement(oRset.getString(1));
				log.debug("****************vLoginTime1*****************************"+vLoginTime);
			}
			else {
				vLoginTime = new Vector<String>();
				oStmt = oConn.createStatement();
				/*oRset=oStmt.executeQuery("select date_format(administrator_login_time.login_time,\"%M %e, %Y %H:%i\"), "+
			    "concat(administrator_details.user_first_name,\' \', administrator_details.user_middle_name,\' \',"+
			    "administrator_details.user_last_name) "+
			    "from administrator_login_time, administrator_details "+
			    "where administrator_login_time.user_id=administrator_details.user_id and "+
			    "administrator_login_time.user_id=\'"+a_strUserID+"\'"
										);*/
				oRset=oStmt.executeQuery("select date_format(administrator_login_time.login_time,\"%M %e, %Y %H:%i\"), "+
						"concat(person_details.first_name,\' \', person_details.middle_name,\' \',"+
						"person_details.last_name) "+
						"from administrator_login_time, person_details ,admin_user_person_association"+
						"where administrator_login_time.user_id=admin_user_person_association.user_id and "+
						" person_details.person_id=admin_user_person_association.person_id and "+
						"administrator_login_time.user_id=\'"+a_strUserID+"\'"
						);
				oRset.next();
				vLoginTime.addElement(oRset.getString(1));
				vLoginTime.addElement(oRset.getString(2));
				log.debug("****************vLoginTime*******2**********************"+vLoginTime);
				
			}
		}
		catch (SQLException e) {
			log.debug("Error due to SQL exception");
			//int errCode=e.getErrorCode();
			//String errMessage = e.getMessage();
		}
		catch (Exception ex) {
			log.debug(" error due to java.lang.exception");
			ex.printStackTrace();
			log.debug(" printStack is :: " + ex.getMessage());
		}finally{
			try {
				if(oRset!=null){
					oRset.close();
				}
				if(oStmt!=null){
					oStmt.close();
				}
				if(oConn!=null){
					oConn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}

		return vLoginTime;
	}


	public synchronized static int decreaseAdministrator(String id){
		Statement  oStmt;
		int rows = 0 ;




		try {
			Connection oConn =ds.getConnection();
			oConn.setAutoCommit(false);
			oStmt = oConn.createStatement();
			rows=oStmt.executeUpdate("delete from administrator_logged_in_at "+
					"where user_id = '"+id+"'");
			oStmt.close();
			oConn.commit();
			oConn.setAutoCommit(true);
		}
		catch (SQLException e) {

		}
		catch (Exception ex){

		}

		return rows;
	}



	public static Vector<Vector<Object>> getModuleSrc(String interface_id)
	{


		Connection oConn =null; 
		Vector<Vector<Object>> vSrcFile = new Vector<Vector<Object>>(3,3);
		Statement  oStmt=null;
		ResultSet oRset =null; 
		try {
			oConn = ds.getConnection();
			oStmt = oConn.createStatement();
			for(oRset = oStmt.executeQuery("select href,value from resource where interface_id='"+interface_id+"' ");oRset.next();)
			{
				Vector<Object> cols = new Vector<Object>();
				cols.addElement(oRset.getString(1));
				cols.addElement(oRset.getAsciiStream(2));
				vSrcFile.addElement(cols);

			}
			oStmt.close();
			oConn.close();
		}
		catch (SQLException sqlexception) {
			System.out.println("Exception in ConfDatabaseLayer.getModuleSrc()");
			System.out.println("The Error Message - " + sqlexception.getMessage());
		}
		catch (Exception ex) {
			System.out.println("Exception in ConfDatabaseLayer.getModuleSrc()");
			System.out.println("The Error Message - " + ex.getMessage());
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					oRset.close();
					oStmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}
		return vSrcFile;		
	}

	public synchronized static Vector<Vector<String>> getResourceType() {
		Statement  oStmt=null;
		ResultSet  oRset=null;
		Vector<Vector<String>> vAdministratorList = new Vector<Vector<String>>();
		Connection oConn =null;


		try	{
			oConn = ds.getConnection();
			oStmt = oConn.createStatement();
			oRset=oStmt.executeQuery("select resource_type_id,resource_type_title from  resource_type");
			while(oRset.next())	{
				Vector<String> vAdministrator = new Vector<String>();

				String program_id=oRset.getString(1);
				String name=oRset.getString(2);
				vAdministrator.addElement(program_id);
				vAdministrator.addElement(name);
				vAdministratorList.addElement(vAdministrator);
			}
			oRset.close();
			oStmt.close();
			oConn.close();
		}
		catch (SQLException e){
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					oRset.close();
					oStmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}
		return vAdministratorList;
	}

	public static Vector<String> getInterfaceIDDetails(String interface_id) {
		Statement  oStmt=null;
		Statement  oStmt1=null;
		ResultSet  oRset=null;
		ResultSet  oRset1=null;
		Vector<String> interfacevector = new Vector<String>();
		Connection oConn = null;


		try	{
			oConn = ds.getConnection();
			oStmt = oConn.createStatement();
			oStmt1 = oConn.createStatement();
			//System.out.println("select interface_id from manifestinterfaceassociation where manifest_id='"+interface_id+"'");
			for( oRset=oStmt.executeQuery("select interface_id from manifestinterfaceassociation where manifest_id='"+interface_id+"'"); oRset.next();)
			{
				String child_interface_id=oRset.getString(1);
				//System.out.println("select interface_title from interface where interface_id='"+child_interface_id+"'");
				for(oRset1=oStmt1.executeQuery("select interface_title from interface where interface_id='"+child_interface_id+"'");oRset1.next();)
				{ 
					String interface_title=oRset1.getString(1);
					interfacevector.addElement(child_interface_id);
					interfacevector.addElement(interface_title);
				}
			}
			oRset1.close();
			oRset.close();
			oConn.close();
		}
		catch (SQLException e){
			//  System.out.println(".........................ERRROR........................................"+e.getMessage());
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					oRset1.close();
					oRset.close();
					oStmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}
		return interfacevector;
	}

	public static synchronized void InsertInterfaceRoleAssociation(String role_id,String interface_id,String layout_id,String content_id,String behaviour_id,String style_id)
	{
		ResultSet resultset1 = null;
		Connection oConn = null;
		Statement statement  = null;
		Statement statement1  = null;
		Statement stmt  = null;
		Statement stmt1 = null;

		try
		{
			oConn = ds.getConnection();
			statement = oConn.createStatement();
			statement1 = oConn.createStatement();
			stmt = oConn.createStatement();
			stmt1 = oConn.createStatement();
			String r_id="";

			if((role_id.toUpperCase()).equals("DEFAULT"))
			{
				resultset1=stmt.executeQuery("select role_id from role where title='"+role_id.toUpperCase()+"' ");
				while(resultset1.next()){
					r_id=resultset1.getString(1);
				}
				if(r_id==null || r_id.equals(""))
				{
					statement1.execute("Insert into role(title)values('"+role_id.toUpperCase()+"')");
					ResultSet resultset2=stmt1.executeQuery("select max(role_id) from role where title='"+role_id.toUpperCase()+"' ");
					resultset2.next();
					String r1_id=resultset2.getString(1);
					statement.execute("Insert into roleassociation(interface_id, role_id, layout_id,content_id,behaviour_id,style_id) values('"+interface_id+"','"+r1_id+"','"+layout_id+"','"+content_id+"','"+behaviour_id+"','"+style_id+"')");


				}
				else
				{
					statement.execute("Insert into roleassociation(interface_id, role_id, layout_id,content_id,behaviour_id,style_id) values('"+interface_id+"','"+r_id+"','"+layout_id+"','"+content_id+"','"+behaviour_id+"','"+style_id+"')");
				}

			}
			if(!(role_id.toUpperCase()).equals("DEFAULT"))
			{

				resultset1=stmt.executeQuery("select role_id from role where title='"+role_id.toUpperCase()+"' ");
				while(resultset1.next()){
					r_id=resultset1.getString(1);
				}
				if(r_id==null || r_id.equals(""))
				{
					statement1.execute("Insert into role(title)values('"+role_id.toUpperCase()+"')");
					ResultSet resultset2=stmt1.executeQuery("select max(role_id) from role where title='"+role_id.toUpperCase()+"' ");
					resultset2.next();
					String r1_id=resultset2.getString(1);
					statement.execute("Insert into roleassociation(interface_id, role_id, layout_id,content_id,behaviour_id,style_id) values('"+interface_id+"','"+r1_id+"','"+layout_id+"','"+content_id+"','"+behaviour_id+"','"+style_id+"')");


				}
				else
				{
					statement.execute("Insert into roleassociation(interface_id, role_id, layout_id,content_id,behaviour_id,style_id) values('"+interface_id+"','"+r_id+"','"+layout_id+"','"+content_id+"','"+behaviour_id+"','"+style_id+"')");
				}


			}


			oConn.close();

		}
		catch(SQLException e)
		{
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+e.getMessage());
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					statement1.close();
					stmt1.close();
					statement.close();
					resultset1.close();
					stmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}

	}

	public synchronized static Vector<Vector<String>> getInterface(String role_id) {
		Statement  oStmt=null;
		ResultSet  oRset=null;
		Connection oConn =null;
		Vector<Vector<String>> vAdministratorList = new Vector<Vector<String>>();

		try	{
			oConn =ds.getConnection();
			oStmt = oConn.createStatement();
			//   System.out.println("select a.interface_id,b.interface_title from  interface b,roleassociation a where a.interface_id=b.interface_id and a.role_id='"+role_id+"'");
			oRset=oStmt.executeQuery("select a.interface_id,b.interface_title from  interface b,roleassociation a where a.interface_id=b.interface_id and a.role_id='"+role_id+"'");
			while(oRset.next())	{
				Vector<String> vAdministrator = new Vector<String>();

				String interface_id=oRset.getString(1);
				String interface_title=oRset.getString(2);
				vAdministrator.addElement(interface_id);
				vAdministrator.addElement(interface_title);
				vAdministratorList.addElement(vAdministrator);
			}
			oRset.close();
			oStmt.close();
			oConn.close();
		}
		catch (SQLException e){
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+e.getMessage());
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					oRset.close();
					oStmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}

		return vAdministratorList;
	}
	public synchronized static Vector<Vector<String>> getLayout(String interface_id) {
		Statement  oStmt=null;
		ResultSet  oRset=null;
		Vector<Vector<String>> vAdministratorList = new Vector<Vector<String>>();
		Connection oConn =null;

		try	
		{
			oConn =ds.getConnection();
			oStmt = oConn.createStatement();
			oRset=oStmt.executeQuery("select distinct(b.layout_id) from  layout b where b.interface_id='"+interface_id+"' ");
			while(oRset.next())	
			{
				Vector<String> vAdministrator = new Vector<String>();
				String layout_id=oRset.getString(1);

				vAdministrator.addElement(layout_id);
				vAdministrator.addElement(layout_id);
				vAdministratorList.addElement(vAdministrator);

			}
			oRset.close();
			oStmt.close();
			oConn.close();
		}
		catch (SQLException e)
		{
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					oRset.close();
					oStmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}

		return vAdministratorList;
	}

	public synchronized static Vector<Vector<String>> getContent(String interface_id) {
		Statement  oStmt=null;
		ResultSet  oRset=null;
		Vector<Vector<String>> vAdministratorList = new Vector<Vector<String>>();
		Connection oConn =null;


		try	{
			oConn = ds.getConnection();
			oStmt = oConn.createStatement();
			oRset=oStmt.executeQuery("select distinct(b.content_id) from  content b where b.interface_id='"+interface_id+"' ");
			while(oRset.next())	{
				Vector<String> vAdministrator = new Vector<String>();

				String content_id=oRset.getString(1);

				vAdministrator.addElement(content_id);
				vAdministrator.addElement(content_id);
				vAdministratorList.addElement(vAdministrator);
			}
			oRset.close();
			oStmt.close();
			oConn.close();
		}
		catch (SQLException e){
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					oRset.close();
					oStmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}
		return vAdministratorList;
	}
	public synchronized static Vector<Vector<String>> getStyle(String interface_id) {
		Statement  oStmt = null;
		ResultSet  oRset = null;
		Vector<Vector<String>> vAdministratorList = new Vector<Vector<String>>();
		Connection oConn = null;


		try	{
			oConn = ds.getConnection();
			oStmt = oConn.createStatement();
			oRset=oStmt.executeQuery("select distinct(b.style_id) from  style b where b.interface_id='"+interface_id+"' ");
			while(oRset.next())	{
				Vector<String> vAdministrator = new Vector<String>();

				String style_id=oRset.getString(1);

				vAdministrator.addElement(style_id);
				vAdministrator.addElement(style_id);
				vAdministratorList.addElement(vAdministrator);
			}
			oRset.close();
			oStmt.close();
			oConn.close();
		}
		catch (SQLException e)
		{
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					oRset.close();
					oStmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}
		return vAdministratorList;
	}

	public synchronized static Vector<Vector<String>> getBehaviour(String interface_id) {
		Statement  oStmt=null;
		ResultSet  oRset=null;
		Vector<Vector<String>> vAdministratorList = new Vector<Vector<String>>();
		Connection oConn =null;


		try	{
			oConn =ds.getConnection();
			oStmt = oConn.createStatement();
			oRset=oStmt.executeQuery("select distinct(b.behaviour_id) from  behaviour b where b.interface_id='"+interface_id+"' ");
			while(oRset.next())	{
				Vector<String> vAdministrator = new Vector<String>();

				String behaviour_id=oRset.getString(1);

				vAdministrator.addElement(behaviour_id);
				vAdministrator.addElement(behaviour_id);
				vAdministratorList.addElement(vAdministrator);
			}
			oRset.close();
			oStmt.close();
			oConn.close();
		}
		catch (SQLException e){
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					oRset.close();
					oStmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}
		return vAdministratorList;
	}

	public synchronized static Vector<Vector<String>> getRole() {
		Statement  oStmt=null;
		ResultSet  oRset=null;
		Vector<Vector<String>> vAdministratorList = new Vector<Vector<String>>();
		Connection oConn =null;


		try	{
			oConn =ds.getConnection();
			oStmt = oConn.createStatement();
			oRset=oStmt.executeQuery("select b.role_id,b.title from  role b");
			while(oRset.next())	{
				Vector<String> vAdministrator = new Vector<String>();

				String role_id=oRset.getString(1);
				String role_title=oRset.getString(2);
				vAdministrator.addElement(role_id);
				vAdministrator.addElement(role_title);
				vAdministratorList.addElement(vAdministrator);
			}
			oRset.close();
			oStmt.close();
			oConn.close();
		}
		catch (SQLException e){
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					oRset.close();
					oStmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}
		return vAdministratorList;
	}

	public synchronized static void UpdateInterfaceRoleAssociation(String role,String interfaceid,String layout,String content,String style,String behaviour) {
		Statement  oStmt=null;
		Connection oConn=null;


		try	{
			oConn =ds.getConnection();
			oStmt = oConn.createStatement();
			oStmt.execute("update  roleassociation set layout_id='"+layout+"',style_id='"+style+"',content_id='"+content+"',behaviour_id='"+behaviour+"'  where role_id ='"+role+"' and interface_id='"+interfaceid+"' ");
			oStmt.close();
			oConn.close();
		}
		catch (SQLException e) {
			System.out.println(" error due to SQL..........................................UpdateInterfaceRoleAssociation.......... exception "+e.getMessage());

		}
		finally
		{
			if(oConn!=null)
			{
				try
				{

					oStmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}

	}

	public static Vector<String> getInterfaceRoleDetails() {
		Statement  oStmt=null;
		Statement  oStmt1=null;
		ResultSet  oRset=null;
		ResultSet  oRset1=null;
		Vector<String> interfacevector = new Vector<String>();
		Connection oConn =null;


		try	{
			oConn =ds.getConnection();
			oStmt = oConn.createStatement();
			oStmt1 = oConn.createStatement();
			for( oRset=oStmt.executeQuery("select a.role_id,b.title from roleassociation a,role b where a.role_id=b.role_id"); oRset.next();)
			{
				String role_id=oRset.getString(1);
				String role_title=oRset.getString(2);
				for(oRset1=oStmt1.executeQuery("select interface_id,layout_id,content_id,style_id,behaviour_id from roleassociation where role_id='"+role_id+"'");oRset1.next();)
				{ 
					String interface_id=oRset1.getString(1);
					String layout_id=oRset1.getString(2);
					String content_id=oRset1.getString(3);
					String style_id=oRset1.getString(4);
					String behaviour_id=oRset1.getString(5);
					interfacevector.addElement(role_id);
					interfacevector.addElement(role_title);
					interfacevector.addElement(interface_id);
					interfacevector.addElement(layout_id);
					interfacevector.addElement(content_id);
					interfacevector.addElement(style_id);
					interfacevector.addElement(behaviour_id);
				}
			}
			oRset1.close();
			oRset.close();
			oConn.close();
		}
		catch (SQLException e){
			System.out.println(".........................ERRROR.................getInterfaceRoleDetails..................................."+e.getMessage());
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					oRset1.close();
					oStmt1.close();
					oRset.close();
					oStmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}
		return interfacevector;
	}

	public synchronized static void DeleteinterfaceRole( )
	{
		Connection connection =null;
		Statement statement =null;
		//Statement stmt =null;

		try
		{
			connection =ds.getConnection();
			statement = connection.createStatement();
			//stmt = connection.createStatement();
			statement.execute("delete from roleassociation");
			connection.close();
		}
		catch(SQLException sqlexception)
		{    
			System.out.println(".....................................................ERROR..........DeleteinterfaceRole........................"+sqlexception.getMessage());
		}
		catch(Exception exception)
		{
		}
		finally
		{
			if(connection!=null)
			{
				try
				{

					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		}  
	}

	public synchronized static void insertOptionItem(String interface_id,String part_id,String option_id,String optionname,String optionvalue) {

		Connection oConn =null;
		Statement statement =null;
		try
		{
			oConn =ds.getConnection();
			statement = oConn.createStatement();
			String sql="Insert into optionmenu(interface_id,part_id,option_id,labelname,labelvalue) values ('" +interface_id+ "','" + part_id+ "','"+option_id+"','"+optionname+"','"+optionvalue+"')";
			statement.execute(sql);
			oConn.close();
		}
		catch(SQLException e){
			System.out.println(".....................................................ERROR..........insertOptionItem........................"+e.getMessage());

		}
		finally
		{
			if(oConn!=null)
			{
				try
				{

					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}  

	}

	public synchronized static void insertDropdownItem(String interface_id,String part_id,String dropdownname,String dropdownvalue) {
		Connection oConn =null;
		Statement statement=null;
		try
		{
			oConn =ds.getConnection();
			statement = oConn.createStatement();
			String sql="Insert into dropdownmenu(interface_id,part_id,name,value) values ('" +interface_id+ "','" + part_id+ "','"+dropdownname+"','"+dropdownvalue+"')";
			statement.execute(sql);
			statement.close();
			oConn.close();
		}
		catch(SQLException e){
			System.out.println(".....................................................ERROR..........insertDropdownItem........................"+e.getMessage());

		}
		finally
		{
			if(oConn!=null)
			{
				try
				{

					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}  

	}


	public synchronized static void setColModel(String interface_id,String part_id,String col_head,String col_name,String col_index,String col_width,String col_editable,String hidden,String key_value,String required,String minval,String maxval,String dbcolumn,String gridhidden,String colinfluence,String col_email,String col_number,String custom,String custom_func,String default_type,String default_value) {

		Connection oConn =null;
		Statement statement =null;

		try
		{
			oConn =ds.getConnection();
			statement = oConn.createStatement();
			String sql="Insert into columnmodel(interface_id,part_id,col_head,col_name,col_index,col_width,col_editable,col_hidden,key_value,required,minval,maxval,dbcolumnname,edithidden,influence,email,number_check,custom,custom_func,default_type,default_value) values ('" +interface_id+ "','" + part_id+ "','"+col_head+"','"+col_name+"','"+col_index+"','"+col_width+"','"+col_editable+"','"+hidden+"','"+key_value+"','"+required+"','"+minval+"','"+maxval+"','"+dbcolumn+"','"+gridhidden+"','"+colinfluence+"','"+col_email+"','"+col_number+"','"+custom+"','"+custom_func+"','"+default_type+"','"+default_value+"')";
			statement.execute(sql);
			statement.close();
			oConn.close();
		}
		catch(SQLException e){
			System.out.println(".....................................................ERROR..........setColModel........................"+e.getMessage());

		}

		finally
		{
			if(oConn!=null)
			{
				try
				{

					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}    
	}


	public synchronized static void InsertkeyColumn(String interface_id,String part_id,String  keycolumn_value) 
	{
		Connection oConn =null;
		Statement statement = null;
		try
		{
			oConn =ds.getConnection();
			statement = oConn.createStatement();
			String sql="Insert into keycolumn(interface_id,part_id,keycolumn_value) values ('" +interface_id+ "','" + part_id+ "','"+keycolumn_value+"')";
			statement.execute(sql);
			statement.close();
			oConn.close();
		}
		catch(SQLException e)
		{
			System.out.println(".....................................................ERROR..........InsertkeyColumn........................"+e.getMessage());

		}
		finally
		{
			if(oConn!=null)
			{
				try
				{

					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}    

	}

	public synchronized static void InsertDeleteitem(String interface_id,String part_id,String  id,String svalue,String parameter) {


		Connection oConn = null;
		Statement statement = null;
		try
		{
			oConn = ds.getConnection();
			statement = oConn.createStatement();
			//String sql="Insert into delete_param(interface_id,part_id,query_id,delete_param_value,delete_parameter) values ('" +interface_id+ "','" + part_id+ "','"+id+"','"+svalue+"','"+parameter+"')";
			//  System.out.println(sql);
			statement.execute("Insert into delete_param(interface_id,part_id,query_id,delete_param_value,delete_parameter) values ('" +interface_id+ "','" + part_id+ "','"+id+"','"+svalue+"','"+parameter+"')");
			statement.close(); 
			oConn.close();
		}
		catch(SQLException e){
			System.out.println(".....................................................ERROR..........InsertDeleteitem........................"+e.getMessage());

		}
		finally
		{
			if(oConn!=null)
			{
				try
				{

					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}    



	}

	public synchronized static void InsertAdditem(String interface_id,String part_id,String id,String  add_value,String parameter) {

		Connection oConn =null;
		Statement statement =null;
		try
		{
			oConn =ds.getConnection();
			statement = oConn.createStatement();
			String sql="Insert into add_param(interface_id,part_id,query_id,add_param_value,add_parameter) values ('" +interface_id+ "','" + part_id+ "','"+id+"','"+add_value+"','"+parameter+"')";
			statement.execute(sql);
			statement.close();
			oConn.close();
		}
		catch(SQLException e){
			System.out.println(".....................................................ERROR..........InsertAdditem........................"+e.getMessage());

		}
		finally
		{
			if(oConn!=null)
			{
				try
				{

					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}    

	}


	public synchronized static void InsertModifyitem(String interface_id,String part_id,String id,String  modify_value,String parameter)
	{

		Connection oConn =null;
		Statement statement =null;
		try
		{
			oConn =ds.getConnection();
			statement = oConn.createStatement();
			String sql="Insert into modify_param(interface_id,part_id,query_id,modify_param_value,modify_parameter) values ('" +interface_id+ "','" + part_id+ "','"+id+"','"+modify_value+"','"+parameter+"')";
			statement.execute(sql);
			statement.close();
			oConn.close();
		}
		catch(SQLException e){
			System.out.println(".....................................................ERROR..........InsertModifyitem........................"+e.getMessage());

		}
		finally
		{
			if(oConn!=null)
			{
				try
				{

					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}    

	}


	public synchronized static void InsertQueryitem(String interface_id,String part_id,String  query_value,String load_parameter,String server_cache_grid,String cachekey_includeuserid) {
		Connection oConn =null;
		Statement statement =null;
		try
		{
			oConn =ds.getConnection();
			statement = oConn.createStatement();
			String sql="Insert into gridquery(interface_id,part_id,grid_query,active_query,load_parameter,server_cache_grid,cachekey_includeuserid) values ('" +interface_id+ "','" + part_id+ "','"+query_value+"','"+query_value+"','"+load_parameter+"','"+server_cache_grid+"','"+cachekey_includeuserid+"')";
			statement.execute(sql);
			statement.close();
			oConn.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{

					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}    

	}


	public synchronized static void setEditOption(String interface_id,String part_id,String col_name,String type,String size,String rows,String cols,String editdomaintype,String value,String multiple) {

		Connection oConn =null;
		Statement statement = null;
		try
		{
			oConn =ds.getConnection();
			statement = oConn.createStatement();
			String sql="Insert into edit_type(interface_id,part_id,col_name,type,size,rows,cols,editdomaintype,value,active_value,multiple) values ('" +interface_id+ "','" + part_id+ "','"+col_name+"','"+type+"','"+size+"','"+rows+"','"+cols+"','"+editdomaintype+"','"+value+"','"+value+"','"+multiple+"')";
			statement.execute(sql);
			statement.close();
			oConn.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{

					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}    

	}

	public synchronized static void addSelecetor(String interface_id,String selector_id,String selector_class,String domaintype,String value,String gridrefresh,String influence,String part_id,String contentid,String behaviourid,String show_choose_one,String choose_one_label,String choose_one_value) {

		Connection oConn =null;
		Statement statement=null;
		Statement statement1=null;
		Statement statement2 =null;
		Statement statement3 =null;

		try
		{
			oConn =ds.getConnection();
			statement = oConn.createStatement();
			statement1 = oConn.createStatement();
			statement2 = oConn.createStatement();
			statement3 = oConn.createStatement();
			String sql="Insert into selector(interface_id,selector_id,gridrefresh,influence,part_id) values ('" +interface_id+ "','" + selector_id+ "','"+gridrefresh+"','"+influence+"','"+part_id+"')";
			statement.execute(sql);
			String sql2="Insert into structure(interface_id,part_id,part_class) values ('" +interface_id+ "','" + selector_id+ "','"+selector_class+"')";
			System.out.println(sql2);
			statement1.execute(sql2);
			String sql3="Insert into content(interface_id,part_id,contenttype,value,active_value,content_id,show_choose_one,choose_one_label,choose_one_value) values ('" +interface_id+ "','" + selector_id+ "','"+domaintype+"','"+value+"','"+value+"','"+contentid+"','"+show_choose_one+"','"+choose_one_label+"','"+choose_one_value+"')";

			System.out.println("****************CONTENT*********************************"+sql3);               
			statement2.execute(sql3);
			String sql4="Insert into behaviour(interface_id,part_id,behaviourevent,value,behaviour_id,valuetype) values ('" +interface_id+ "','" +selector_id+ "','onchange','"+selector_id+"function()','"+behaviourid+"','inline')";
			statement3.execute(sql4);
			oConn.close();
		}
		catch(SQLException e){
			System.out.println(".....................................................ERROR..........addSelecetor........................"+e.getMessage());
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{

					statement.close();
					statement1.close();
					statement2.close();
					statement3.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}    
	}

	public synchronized static void InsertValidationDeleteitem(String interface_id,String part_id,String validationtype,String  id,String svalue,String parameter,String message,String function_name) {

		Connection oConn =null;
		Statement statement =null;

		try
		{
			oConn = ds.getConnection();
			statement = oConn.createStatement();
			// 		 String sql="Insert into delete_param(interface_id,part_id,query_id,delete_param_value,delete_parameter) values ('" +interface_id+ "','" + part_id+ "','"+id+"','"+svalue+"','"+parameter+"')";
			//                  System.out.println(sql);
			statement.execute("Insert into delete_validation_param(interface_id,part_id,query_id,delete_param_value,delete_parameter,message,type,function_name) values ('" +interface_id+ "','" + part_id+ "','"+id+"','"+svalue+"','"+parameter+"','"+message+"','"+validationtype+"','"+function_name+"')");
			statement.close();
			oConn.close();
		}
		catch(SQLException e){
			System.out.println(".....................................................ERROR..........InsertValidationDeleteitem........................"+e.getMessage());

		}
		finally
		{
			if(oConn!=null)
			{
				try
				{

					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}    


	}
	public synchronized static void InsertValidationAdditem(String interface_id,String part_id,String validationtype,String  id,String svalue,String parameter,String message,String function_name) {

		Connection oConn =null;
		Statement statement =null;
		try
		{
			oConn = ds.getConnection();
			statement = oConn.createStatement();
			statement.execute("Insert into add_validation_param(interface_id,part_id,query_id,add_param_value,add_parameter,message,type,function_name) values ('" +interface_id+ "','" + part_id+ "','"+id+"','"+svalue+"','"+parameter+"','"+message+"','"+validationtype+"','"+function_name+"')");
			statement.close(); 
			oConn.close();
		}
		catch(SQLException e){
			System.out.println(".....................................................ERROR..........InsertValidationAdditem........................"+e.getMessage());

		}
		finally
		{
			if(oConn!=null)
			{
				try
				{

					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}    

	}

	public synchronized static void InsertValidationModifyitem(String interface_id,String part_id,String validationtype,String  id,String svalue,String parameter,String message,String function_name) {

		Connection oConn =null;
		Statement statement =null;

		try
		{
			oConn = ds.getConnection();
			statement = oConn.createStatement();
			statement.execute("Insert into modify_validation_param(interface_id,part_id,query_id,modify_param_value,modify_parameter,message,type,function_name) values ('" +interface_id+ "','" + part_id+ "','"+id+"','"+svalue+"','"+parameter+"','"+message+"','"+validationtype+"','"+function_name+"')");
			statement.close();
			oConn.close();
		}
		catch(SQLException e){
			System.out.println(".....................................................ERROR..........InsertValidationModifyitem........................"+e.getMessage());

		}
		finally
		{
			if(oConn!=null)
			{
				try
				{

					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}   
	}


	public synchronized static void Insertdelete_action_param(String interface_id,String part_id,String sequence,String actionname) {
		Connection oConn =null;
		Statement statement =null;   

		try
		{
			oConn = ds.getConnection();
			statement = oConn.createStatement();
			statement.execute("Insert into delete_action_param(interface_id,part_id,action_sequence,action_name) values ('" +interface_id+ "','" + part_id+ "','"+sequence+"','"+actionname+"')");
			statement.close();
			oConn.close();
		}
		catch(SQLException e){
			System.out.println(".....................................................ERROR..........Insertdelete_action_param........................"+e.getMessage());

		}
		finally
		{
			if(oConn!=null)
			{
				try
				{

					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}   
	}
	public synchronized static void Insertadd_action_param(String interface_id,String part_id,String sequence,String actionname) {
		Connection oConn =null;
		Statement statement =null;    

		try
		{
			oConn = ds.getConnection();
			statement = oConn.createStatement();
			statement.execute("Insert into add_action_param(interface_id,part_id,action_sequence,action_name) values ('" +interface_id+ "','" + part_id+ "','"+sequence+"','"+actionname+"')");
			statement.close();
			oConn.close();
		}
		catch(SQLException e){
			System.out.println(".....................................................ERROR..........Insertadd_action_param........................"+e.getMessage());

		}
		finally
		{
			if(oConn!=null)
			{
				try
				{

					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}   
	}
	public synchronized static void Insertmodify_action_param(String interface_id,String part_id,String sequence,String actionname) {

		Connection oConn = null;
		Statement statement = null;

		try
		{
			oConn = ds.getConnection();
			statement = oConn.createStatement();
			statement.execute("Insert into modify_action_param(interface_id,part_id,action_sequence,action_name) values ('" +interface_id+ "','" + part_id+ "','"+sequence+"','"+actionname+"')");
			statement.close();
			oConn.close();
		}
		catch(SQLException e)
		{
			System.out.println(".....................................................ERROR..........Insertmodify_action_param........................"+e.getMessage());

		}
		finally
		{
			if(oConn!=null)
			{
				try
				{

					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}   
	}

	public static void insertConfigurationItem(String interface_id,String createsession,String checkrole,String contenttype,String doctypepublic,String doctypesystem,String cachecontrol,String expires,String lastmod,String template,String themes,String enable_chaching,String cache_name,String cachedynamicjs,String cachedynamiccss,String cachedynamicimage)
	{
		Connection connection =null;

		Statement statement = null;
		try
		{
			connection = ds.getConnection();
			statement = connection.createStatement();
			statement.execute("Insert into configuration_item(interface_id,createsession,checkrole,contenttype,doctypepublic,doctypesystem,cachecontrol,expires,lastmodify,template,themes_id,enable_caching,cache_name,cache_dynamic_js,cache_dynamic_css,cache_dynamic_image) values ('" + interface_id + "','"+createsession+"','"+checkrole+"','"+contenttype+"','"+doctypepublic+"','"+doctypesystem+"','"+cachecontrol+"','"+expires+"','"+lastmod+"','"+template+"','"+themes+"','"+enable_chaching+"','"+cache_name+"','"+cachedynamicjs+"','"+cachedynamiccss+"','"+cachedynamicimage+"')");
			statement.close();
			connection.close();
		}
		catch(SQLException sqlexception)
		{
			System.out.println(".......................................................insertConfigurationItem......................................."+sqlexception.getMessage());
		}
		catch(Exception exception)
		{


		}
		finally
		{
			if(connection!=null)
			{
				try
				{

					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		}   
	}

	public synchronized static Vector<Vector<String>> getUser() {
		Statement  oStmt=null;
		ResultSet  oRset=null;
		Connection oConn = null;
		Vector<Vector<String>> vAcamedicUnit = new Vector<Vector<String>>();
		try	{
			oConn = ds.getConnection();
			oStmt = oConn.createStatement();

			oRset=oStmt.executeQuery("select student_id,concat(first_name,' ',sname) from student_details");

			while(oRset.next()) {
				Vector<String> vAcamedic = new Vector<String>();
				vAcamedic.addElement(oRset.getString(1));
				vAcamedic.addElement(oRset.getString(2));
				vAcamedicUnit.addElement(vAcamedic);
			}
			oRset.close();
			oStmt.close();
		}
		catch (SQLException e) {
			System.out.println("user Drop Down: error due to SQL exception"+e.getMessage());
		}
		catch (Exception ex) {
			System.out.println("user: error due to SQL exception");
			ex.printStackTrace();
			System.out.println(" printStack is :: " + ex.getMessage());
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					oRset.close();
					oStmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}   
		return vAcamedicUnit;
	}

	public synchronized static void adduserrole(String user,String role,String user_id) {
		Connection oConn = null;
		Statement statement =  null;
		try
		{
			oConn = ds.getConnection();
			statement = oConn.createStatement();
			statement.execute("Insert into user_role(user_id,role_id,date,entered_by) values ('" +user+ "','"+role+"',sysdate(),'"+user_id+"')");            
			statement.close();
			oConn.close();
		}
		catch(SQLException e){
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{

					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}      
	}

	public synchronized static void modifyuserrole(String user,String roleid,String role,String user_id) {

		Statement  oStmt=null;
		Connection oConn = null;
		//boolean count = false;
		try {

			oConn = ds.getConnection();
			oStmt = oConn.createStatement();
			oStmt.executeQuery("update user_role set date=sysdate(),entered_by='"+user_id+"',role_id = '"+role+"' where role_id = '"+roleid+"'and user_id='"+user+"'");
			oStmt.close();
		}
		catch (SQLException e) {

		}
		finally
		{
			if(oConn!=null)
			{
				try
				{

					oStmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}    

	}

	public synchronized static boolean deleteuserrole(String user,String roleid) {
		Statement  oStmt=null;
		Connection oConn = null;
		boolean l_bAuth = false ;
		try {
			oConn = ds.getConnection();
			oStmt = oConn.createStatement();
			oStmt.execute("delete from user_role where role_id ='"+roleid+"' and user_id='"+user+"'" );
			oStmt.close();
			l_bAuth = true;
		}
		catch (SQLException e) {
			System.out.println(" error due to SQL exception");

		}
		finally
		{
			if(oConn!=null)
			{
				try
				{

					oStmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}    

		return l_bAuth;
	}







	/////////////////////////////////////////////////////////////////////////SHIBAJI/////////////////////////////////////////
	/*public static void insertFramework_File(String s,String title,String type,String fsize)
	{

		Connection connection =null;

		Statement statement =null;


		try
		{
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			statement = connection.createStatement();

			statement.execute("Insert into framework_file(framework_file_id,framework_file_title,type,filesize,last_updated) values ('" + s + "','"+title+"','"+type+"','"+fsize+"',sysdate())");
			statement.close();
			connection.commit();
			connection.setAutoCommit(true);
			connection.close();
		}
		catch(SQLException sqlexception)
		{
		}
		finally
		{
			if(connection!=null)
			{
				try
				{

					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		}    


	}*/

	public static Vector<InputStream> getFramework_FileDetails(String interface_id,String filename)

	{
		Statement  oStmt=null;
		ResultSet oRset=null;
		Connection oConn =null;
		Vector<InputStream> cols=new Vector<InputStream>();
		try {
			oConn =ds.getConnection();
			oStmt = oConn.createStatement();
			System.out.println("select value from framework_file where framework_file_id='"+interface_id+"'  and filename='"+filename+"' ");
			for(oRset = oStmt.executeQuery("select value from framework_file where framework_file_id='"+interface_id+"'  and filename='"+filename+"' ");oRset.next();)
			{
				cols.addElement(oRset.getAsciiStream(1));
			}
			oRset.close();
			oStmt.close();
			oConn.close();
		}
		catch (SQLException sqlexception) {
			System.out.println(">>>>>>>>>>>>>>getFramework_FileDetails>>>>>>>>>>>>>>>>>>>>"+sqlexception.getMessage());
		}
		catch (Exception ex) {
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					oRset.close();
					oStmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}    


		return cols;		
	}
	public static void updateRoleXML(String interface_id,String attachmentname,String s7,String typecollection,String fsize)
	{
		Connection connection =null;
		Statement statement = null;
		try
		{

			//System.out.println("????????????????????????"+interface_id+attachmentname+s7+typecollection+fsize);
			connection = ds.getConnection();
			statement = connection.createStatement();
			String file_path=attachmentname+s7;
			InputStream inStream= new FileInputStream(file_path);
			PreparedStatement pstmt = connection.prepareStatement("update  framework_file SET value= ? , filesize= ? where framework_file_id=? ");
			pstmt.setBinaryStream( 1, inStream,Integer.parseInt(fsize));
			pstmt.setString(2,fsize);

			pstmt.setString(3,interface_id);                       
			pstmt.executeUpdate();

			pstmt.close();

			statement.close();
			// connection.commit();
			// connection.setAutoCommit(true);
			connection.close();
		}
		catch(SQLException sqlexception)
		{    
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>> updateRoleXML>>>>>>>>>>>>>"+sqlexception.getMessage());
		}
		catch(Exception exception)
		{


		}
		finally
		{
			if(connection!=null)
			{
				try
				{

					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		}    
	}


	public static void FrameworkFile(String framework_file_id,String title,String path,String filename,String type,String size,String inlinecss,String inlinejs,String imagepath)
	{
		Connection connection =null;
		Statement statement = null;
		try
		{
			connection = ds.getConnection();
			statement = connection.createStatement();
			if(inlinejs==null)
			{
				inlinejs="no";
			}
			if(inlinecss==null)
			{
				inlinecss="no";
			}
			if(imagepath==null)
			{
				imagepath="";
			}
			File inFile=new File(path+filename);
			InputStream inStream= new FileInputStream(inFile);
			statement.execute("Insert into framework_file(framework_file_id,framework_file_title,type,filesize,inlinecss,inlinejs,imagepath,last_updated) values ('" +framework_file_id + "','"+framework_file_id+"','"+type+"','"+size+"','"+inlinecss+"','"+inlinejs+"','"+imagepath+"',sysdate())");
			PreparedStatement pstmt = connection.prepareStatement("update  framework_file SET value= ? ,filename= ? where  framework_file_id=?" );
			pstmt.setBinaryStream( 1, inStream, (int)(inFile.length()));
			pstmt.setString(2,filename);
			pstmt.setString(3,framework_file_id);

			pstmt.executeUpdate();
			pstmt.close();
			statement.close();
			connection.close();
			//connection.setAutoCommit(true);
		}
		catch(SQLException sqlexception)
		{
			System.out.println("....................................FrameworkFile..........EXCEPTION.............................."+sqlexception.getMessage());     
		}
		catch(Exception exception)
		{

		}
		finally
		{
			if(connection!=null)
			{
				try
				{

					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		}    



	}

	public static void FrameworkFile2(String framework_file_id,String title,String path,String filename,String type,String size,String inlinecss,String inlinejs,String imagepath)
	{
		Connection connection =null;
		Statement statement = null;
		try
		{
			connection = ds.getConnection();
			statement = connection.createStatement();
			if(inlinejs==null)
			{
				inlinejs="no";
			}
			if(inlinecss==null)
			{
				inlinecss="no";
			}
			if(imagepath==null)
			{
				imagepath="";
			}
			statement.execute("Insert into framework_file(framework_file_id,framework_file_title,type,filename,filesize,inlinecss,inlinejs,imagepath,last_updated) values ('" +framework_file_id + "','"+framework_file_id+"','"+type+"','"+filename+"','"+size+"','"+inlinecss+"','"+inlinejs+"','"+imagepath+"',sysdate())");
			statement.close();
			connection.close();

		}
		catch(SQLException sqlexception)
		{
			System.out.println("....................................FrameworkFile2..........EXCEPTION.............................."+sqlexception.getMessage());     
		}
		catch(Exception exception)
		{

		}
		finally
		{
			if(connection!=null)
			{
				try
				{

					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		}    
	}

	public static void FrameworkFile1(String framework_file_id,String title,String filename,String type,String size)
	{

		try
		{
			Connection connection = ds.getConnection();
			//  connection.setAutoCommit(false);
			Statement statement = connection.createStatement();

			statement.execute("Insert into framework_file(framework_file_id,framework_file_title,filename,type,filesize,last_updated) values ('" +framework_file_id + "','"+framework_file_id+"','"+filename+"','"+type+"','"+size+"',sysdate())");

			statement.close();
			connection.close();
			//connection.setAutoCommit(true);
		}
		catch(SQLException sqlexception)
		{
			System.out.println("....................................FrameworkFile1..........EXCEPTION.............................."+sqlexception);     
		}
		catch(Exception exception)
		{

		}

	}
	public static void updateInterfaceZip(String interface_id,String interface_title,String attachmentname,String s7,String typecollection,String fsize)
	{
		try
		{
			Connection connection = ds.getConnection();
			Statement statement = connection.createStatement();
			String file_path=attachmentname+s7;
			InputStream inStream= new FileInputStream(file_path);
			PreparedStatement pstmt = connection.prepareStatement("update  framework_file SET value= ? ,framework_file_title= ?, filename= ?,type= ?,filesize= ? where framework_file_id=?");
			pstmt.setBinaryStream( 1, inStream, (int)(file_path.length()));
			pstmt.setString(2,interface_title);
			pstmt.setString(3,s7);
			pstmt.setString(4,typecollection);
			pstmt.setString(5,fsize);
			pstmt.setString(6,interface_id);                       
			pstmt.executeUpdate();

			pstmt.close();

			statement.close();
			// connection.commit();
			// connection.setAutoCommit(true);
			connection.close();
		}
		catch(SQLException sqlexception)
		{    
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>> updateInterfaceZip>>>>>>>>>>>>>"+sqlexception.getMessage());
		}
		catch(Exception exception)
		{


		}
	}


	public static void updateManifest(String interface_id,String attachmentname,String s7,String typecollection,String fsize)
	{
		try
		{

			//System.out.println("????????????????????????"+interface_id+attachmentname+s7+typecollection+fsize);
			Connection connection = ds.getConnection();
			Statement statement = connection.createStatement();
			String file_path=attachmentname+s7;
			//System.out.println("//////////////////////////////////////////////////file path/////////////////////////////////"+file_path);
			InputStream inStream= new FileInputStream(file_path);
			//System.out.println("update  framework_file SET value= ? , filesize= ? where framework_file_id=? ");
			PreparedStatement pstmt = connection.prepareStatement("update  framework_file SET value= ? , filesize= ? where framework_file_id=? ");
			pstmt.setBinaryStream( 1, inStream,Integer.parseInt(fsize));
			pstmt.setString(2,fsize);

			pstmt.setString(3,interface_id);                       
			pstmt.executeUpdate();

			pstmt.close();

			statement.close();
			// connection.commit();
			// connection.setAutoCommit(true);
			connection.close();
		}
		catch(SQLException sqlexception)
		{    
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>> updateManifest>>>>>>>>>>>>>"+sqlexception.getMessage());
		}
		catch(Exception exception)
		{


		}
	}

	public static  String CheckInterfaceID(String interface_id)
	{
		String checkid = null;
		Connection oConn = null;

		try
		{
			oConn = ds.getConnection();
			Statement statement = oConn.createStatement();
			ResultSet resultset = statement.executeQuery("select interface_id from interface where  interface_id='"+interface_id+"'");
			while(resultset.next())
			{
				checkid=resultset.getString(1);
			}
			resultset.close();
			statement.close();
			oConn.close();
		}
		catch(SQLException sqlexception)
		{
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		return checkid;
	} 

	public static void insertformelement(String interface_id,String part_id,String jscontrol,String formaction,String formmethod)
	{

		Connection connection =null;
		Statement statement = null;
		Statement statement1 = null;
		try
		{
			connection = ds.getConnection();
			statement = connection.createStatement();
			statement1 = connection.createStatement();
			statement.execute("Insert into formelement(interface_id,part_id,formmethod,formaction,jscontrol) values ('" + interface_id + "','" + part_id + "','"+formmethod+"','"+formaction+"','"+jscontrol+"')");
			statement.close();
			connection.close();
		}
		catch(SQLException sqlexception)
		{
			System.out.println(".........................................................insertformelement........................."+sqlexception.getMessage());
		}
		catch(Exception exception)
		{


		}

		finally
		{
			if(connection!=null)
			{
				try
				{
					statement.close();
					statement1.close();
					connection.close();
				} catch(Exception e){}	
			}
		}    


	}

	public static void insertpartformelement(String interface_id,String partid,String partclass,String cols,String rows,String size,String tabindex)
	{

		Connection connection =null;
		Statement statement = null;
		Statement statement1 = null;
		try
		{
			connection = ds.getConnection();
			statement = connection.createStatement();
			statement1 = connection.createStatement();

			statement.execute("Insert into structure(interface_id,part_id,part_class,cols,rows,size,tabindex) values ('" + interface_id + "','" + partid + "','"+partclass+"','"+cols+"','"+rows+"','"+size+"','"+tabindex+"')");
			statement.close();

			connection.close();
		}
		catch(SQLException sqlexception)
		{
			//log.fatal("Exception in DataBaseLayer.style()");
			//log.dbe(DebugLevel.L1_FATAL,sqlexception);

			while((sqlexception = sqlexception.getNextException()) != null) 
			{
				//log.fatal("The Error Message");
				//log.dbe(DebugLevel.L1_FATAL,sqlexception);

			}
		}
		catch(Exception exception)
		{


		}
		finally
		{
			if(connection!=null)
			{
				try
				{
					statement.close();
					statement1.close();
					connection.close();
				} catch(Exception e){}	
			}
		}    
	}

	public static Vector<String>   getLayout_ID(String interface_id)
	{
		//String checkid = null;
		Connection oConn = null;
		Vector<String> v=new Vector<String>();
		ResultSet resultset=null;
		Statement statement = null;
		try
		{
			oConn = ds.getConnection();
			statement = oConn.createStatement();
			for(resultset = statement.executeQuery("select layout_id,content_id,behaviour_id,style_id from roleassociation where  interface_id='"+interface_id+"'");resultset.next();)

			{
				v.addElement(resultset.getString(1));
				v.addElement(resultset.getString(2));
				v.addElement(resultset.getString(3));
				v.addElement(resultset.getString(4));
			}
			resultset.close();
			statement.close();
			oConn.close();
		}
		catch(SQLException sqlexception)
		{
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					resultset.close();
					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}    
		return v;
	} 

	// 	 public static void insetHTML(String interface_id,String layout_id,String content_id,String behaviour_id,String style_id,String path)
	// 	 {
	// 		 Connection connection =null;
	// 		 Statement statement =null;
	// 		 try
	// 		 {
	// 			 connection = ds.getConnection();
	// 			 statement = connection.createStatement();
	// 			 File inFile=new File(path);
	// 			 InputStream inStream= new FileInputStream(inFile);
	// 			 boolean flag1 =statement.execute("delete from interfaceenginecalling where interface_id='"+interface_id+"' and layout_id='"+layout_id+"' and content_id='"+content_id+"' and behaviour_id='"+behaviour_id+"' and style_id='"+style_id+"'");
	// 			 boolean flag = statement.execute("Insert into interfaceenginecalling(interface_id,layout_id,content_id,behaviour_id,style_id) values ('" +interface_id + "','"+layout_id+"','"+content_id+"','"+behaviour_id+"','"+style_id+"')");
	// 			 PreparedStatement pstmt = connection.prepareStatement("update  interfaceenginecalling SET valueblob= ?  where  layout_id=? and content_id=? and behaviour_id=? and style_id=? and interface_id=?" );
	// 			 pstmt.setBinaryStream( 1, inStream, (int)(inFile.length()));
	// 			 pstmt.setString(2,layout_id);
	// 			 pstmt.setString(3,content_id);
	// 			 pstmt.setString(4,behaviour_id);
	// 			 pstmt.setString(5,style_id);
	// 			 pstmt.setString(6,interface_id);
	//                  
	// 			 pstmt.executeUpdate();
	// 			 pstmt.close();
	// 			 statement.close();
	// 			 connection.close();
	// 		    //connection.setAutoCommit(true);
	// 		 }
	// 		 catch(SQLException sqlexception)
	// 		 {
	// 			 sqlexception.printStackTrace();
	// 		 }
	// 		 catch(Exception exception)
	// 		 {
	// 			 exception.printStackTrace();
	// 		 }
	// 		 finally
	// 		 {
	// 			 if(connection!=null)
	// 			 {
	// 				 try
	// 				 {
	// 					 
	// 					 statement.close();
	// 					 connection.close();
	// 				 } catch(Exception e){}	
	// 			 }
	// 		 }    
	// 	 }
	// 	 
	public static void insetHTML(String interface_id,String layout_id,String content_id,String behaviour_id,String style_id,String htmlString)
	{
		Connection connection =null;
		Statement statement =null;
		try
		{
			connection = ds.getConnection();
			statement = connection.createStatement();
			// File inFile=new File(path);
			//InputStream inStream= new FileInputStream(inFile);
			statement.execute("delete from interfaceenginecalling where interface_id='"+interface_id+"' and layout_id='"+layout_id+"' and content_id='"+content_id+"' and behaviour_id='"+behaviour_id+"' and style_id='"+style_id+"'");
			statement.execute("Insert into interfaceenginecalling(interface_id,layout_id,content_id,behaviour_id,style_id) values ('" +interface_id + "','"+layout_id+"','"+content_id+"','"+behaviour_id+"','"+style_id+"')");
			PreparedStatement pstmt = connection.prepareStatement("update  interfaceenginecalling SET valueblob= ?  where  layout_id=? and content_id=? and behaviour_id=? and style_id=? and interface_id=?" );
			pstmt.setString( 1, htmlString);
			pstmt.setString(2,layout_id);
			pstmt.setString(3,content_id);
			pstmt.setString(4,behaviour_id);
			pstmt.setString(5,style_id);
			pstmt.setString(6,interface_id);

			pstmt.executeUpdate();
			pstmt.close();
			statement.close();
			connection.close();
			//connection.setAutoCommit(true);
		}
		catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			if(connection!=null)
			{
				try
				{

					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		}    
	}


	public static void insertFragment(String interface_id,String layout_id,String content_id,String behaviour_id,String style_id,String snippet)
	{
		Connection connection =null;
		Statement statement =null;
		try
		{
			connection = ds.getConnection();
			statement = connection.createStatement();
			statement.execute("delete from interfaceenginecalling where interface_id='"+interface_id+"' and layout_id='"+layout_id+"' and content_id='"+content_id+"' and behaviour_id='"+behaviour_id+"' and style_id='"+style_id+"'");
			statement.execute("Insert into interfaceenginecalling(interface_id,layout_id,content_id,behaviour_id,style_id) values ('" +interface_id + "','"+layout_id+"','"+content_id+"','"+behaviour_id+"','"+style_id+"')");
			PreparedStatement pstmt = connection.prepareStatement("update  interfaceenginecalling SET valueblob= ?  where  layout_id=? and content_id=? and behaviour_id=? and style_id=? and interface_id=?" );
			pstmt.setString( 1, snippet);
			pstmt.setString(2,layout_id);
			pstmt.setString(3,content_id);
			pstmt.setString(4,behaviour_id);
			pstmt.setString(5,style_id);
			pstmt.setString(6,interface_id);

			pstmt.executeUpdate();
			pstmt.close();
			statement.close();
			connection.close();
			//connection.setAutoCommit(true);
		}
		catch(SQLException sqlexception)
		{
			System.out.println(">>>>>>>>>>>>>>insetHTML>>>>>>>>>>>>>>>>>>>>>>>>>>"+sqlexception.getMessage());
		}
		catch(Exception exception)
		{

		}
		finally
		{
			if(connection!=null)
			{
				try
				{

					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		}    
	}


	// 	 public synchronized static void addTemplate(String template, String comment) {
	// 		 try
	// 		 {
	// 			 Connection oConn = ds.getConnection();
	// 			 oConn.setAutoCommit(false);
	// 			 Statement statement = oConn.createStatement();
	// 			 template=template.toUpperCase();
	// 			 String sql="Insert into template_mgmt(template,templatecomment) values ('" +template+ "','" + comment+ "')";
	// 			 log.debug(sql);
	// 			 statement.execute(sql);
	// 			 statement.close();
	// 			 oConn.commit();
	// 			 oConn.setAutoCommit(true);
	// 		 }
	// 		 catch(SQLException e){
	// 			 log.debug("SQLException in addTemplate. Rolling Back Operation "+e.getMessage());
	// 			 try{
	// 			 }catch(Exception re){log.debug("Exception: "+re);}
	//     	   	
	// 		 }
	// 	 }	 
	// 	 
	// 	 public synchronized static boolean modifyTemplate(String templatenameid,String templatename,String comment) {
	// 		
	// 		 Statement  oStmt;
	// 		 boolean count = false;
	// 		 try {
	// 			 Connection oConn = ds.getConnection();
	// 			 oStmt = oConn.createStatement();
	// 			 oStmt.execute("update template_mgmt set template = '"+templatename+"',templatecomment = '"+comment+"' where template_id = '"+templatenameid+"'");
	// 			 oStmt.close();
	// 			 count = true;
	// 		    }
	// 			catch (SQLException e) {
	// 				log.debug("SQLException in modifyTemplate. Rolling Back Operation "+e.getMessage());
	// 			}
	// 			catch (Exception ex) {
	// 			}
	// 			return count ;
	// 	 }	 
	// 	 
	// 	 public synchronized static boolean deleteTemplate(String templatenameid) {
	// 		 Statement  oStmt;
	// 		 boolean l_bAuth = false ;
	// 		 try {
	// 			 Connection oConn = ds.getConnection();
	// 			 oStmt = oConn.createStatement();
	// 			 oStmt.execute("delete from template_mgmt where template_id ='"+templatenameid+"'" );
	// 			 oStmt.close();
	// 			 l_bAuth = true;
	// 		 }
	// 		 catch (SQLException e) {
	// 			 log.debug(" error due to SQL exception deleteTemplate"+e.getMessage());
	// 		 }
	// 		 catch (Exception ex) {
	// 			 log.debug(" error due to java.lang.exception");
	// 			 ex.printStackTrace();
	// 			 log.debug(" printStack is :: " + ex.getMessage());
	// 		 }
	// 		 return l_bAuth;
	// 	 }

	// 	 public synchronized static void addTemplateCinfigurationItem(String template_id,String item, String type,String location,String sequence_no,String inline) {
	// 		 try
	// 		 {
	// 			 Connection oConn = ds.getConnection();
	// 			 oConn.setAutoCommit(false);
	// 			 Statement statement = oConn.createStatement();
	// 			// template=template.toUpperCase();
	// 			 String sql="Insert into configuration_template_definition(template_id,href,href_type,location,sequence_no,inline) values ('" +template_id+ "','" + item+ "','" + type+ "','"+location+"','"+sequence_no+"','"+inline+"')";
	// 			 log.debug(sql);
	// 			 statement.execute(sql);
	// 			 statement.close();
	// 			 oConn.commit();
	// 			 oConn.setAutoCommit(true);
	// 		 }
	// 		 catch(SQLException e)
	// 		 {
	// 			 log.debug("SQLException in addTemplateCinfigurationItem. Rolling Back Operation "+e.getMessage());
	// 			 try{
	// 			 }catch(Exception re){log.debug("Exception: "+re);}
	//     	   	
	// 		 }
	// 	 }	

	public synchronized static Vector<Vector<String>> getTemplate() {
		Statement  oStmt=null;
		ResultSet  oRset=null;
		Connection oConn = null;
		Vector<Vector<String>> vAcamedicUnit = new Vector<Vector<String>>();
		try	{
			oConn = ds.getConnection();
			oStmt = oConn.createStatement();

			oRset=oStmt.executeQuery("select template_id,template from template_mgmt");

			while(oRset.next()) {
				Vector<String> vAcamedic = new Vector<String>();
				vAcamedic.addElement(oRset.getString(1));
				vAcamedic.addElement(oRset.getString(2));
				vAcamedicUnit.addElement(vAcamedic);
			}
			oRset.close();
			oStmt.close();
		}
		catch (SQLException e) {
			System.out.println("getTemplate Drop Down: error due to SQL exception"+e.getMessage());
		}
		catch (Exception ex) {
			System.out.println("user: error due to SQL exception");
			ex.printStackTrace();
			System.out.println(" printStack is :: " + ex.getMessage());
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					oRset.close();
					oStmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}   
		return vAcamedicUnit;
	}	 

	// 	 public synchronized static boolean modifyTemplateCinfigurationItem(String template_id, String item,String mimetype,String location,String sequence_no,String sequence_no_id,String inline) 
	// 	 {
	// 		 Statement  oStmt;
	// 		 boolean count = false;
	// 		 try 
	// 		 {
	// 			 Connection oConn = ds.getConnection();
	// 			 oStmt = oConn.createStatement();
	// 			 oStmt.execute("update configuration_template_definition set href = '"+item+"',href_type = '"+mimetype+"',location='"+location+"',sequence_no='"+sequence_no+"',inline='"+inline+"'  where template_id = '"+template_id+"' and sequence_no='"+sequence_no_id+"'");
	// 			 oStmt.close();
	// 			 count = true;
	// 		 }
	// 		 catch (SQLException e) 
	// 		 {
	// 			 log.debug("SQLException in modifyTemplateCinfigurationItem. Rolling Back Operation "+e.getMessage());
	// 		 }
	// 		 catch (Exception ex) 
	// 		 {
	// 			 
	// 		 }
	// 		 return count ;
	// 	 }	 
	// 	 
	// 	 public synchronized static boolean deleteTemplateCinfigurationItem(String templatenameid,String sequence_no_id) 
	// 	 {
	// 		 Statement  oStmt;
	// 		 boolean l_bAuth = false ;
	// 		 try 
	// 		 {
	// 			 
	// 			 Connection oConn = ds.getConnection();
	// 			 oStmt = oConn.createStatement();
	// 			 System.out.println("delete from configuration_template_definition where template_id ='"+templatenameid+"' and sequence_no='"+sequence_no_id+"'" );
	// 			 oStmt.execute("delete from configuration_template_definition where template_id ='"+templatenameid+"' and sequence_no='"+sequence_no_id+"'" );
	// 			 oStmt.close();
	// 			 l_bAuth = true;
	// 		 }
	// 		 catch (SQLException e) 
	// 		 {
	// 			 log.debug(" error due to SQL exception deleteTemplateCinfigurationItem"+e.getMessage());
	// 		 }
	// 		 catch (Exception ex) 
	// 		 {
	// 			 log.debug(" error due to java.lang.exception");
	// 			 ex.printStackTrace();
	// 			 log.debug(" printStack is :: " + ex.getMessage());
	// 		 }
	// 		 return l_bAuth;
	// 	 }


	public synchronized static void InsertDBFormElement(String interface_id,String part_id,String element_id, String element_class,String element_type,String element_key,String tabindex,String selectindex,String modifyindex,String insertindex,String rows,String cols,String forlabel, String required,String minlength,String maxlength,String equalto,String number,String email,String requiredmess,String minlengthmess,String maxlengthmess,String equaltomess,String numbermess,String emailmess,String size) {

		Connection oConn =null;
		Statement statement =null;
		Statement statement1 =null;
		try
		{
			oConn =ds.getConnection();
			statement = oConn.createStatement();
			statement1 = oConn.createStatement();
			System.out.println("............selectindex......"+selectindex+".............modifyindex.........."+modifyindex+".............inserindex.........."+insertindex);
			String sql="Insert into form_element(interface_id,part_id,element_id,element_type,element_key,selectindex,modifyindex,insertindex,forlabel,required,minlength,maxlength,equalto,numbercheck,email,requiredmess,minlengthmess,maxlengthmess,equaltomess,numbercheckmess,emailmess) values ('" +interface_id+ "','" + part_id+ "','"+element_id+"','"+element_type+"','"+element_key+"','"+selectindex+"','"+modifyindex+"','"+insertindex+"','"+forlabel+"','"+required+"','"+minlength+"','"+maxlength+"','"+equalto+"','"+number+"','"+email+"','"+requiredmess+"','"+minlengthmess+"','"+maxlengthmess+"','"+equaltomess+"','"+numbermess+"','"+emailmess+"')";
			statement.execute(sql);

			String sql1="Insert into structure(interface_id,part_id,part_class,tabindex,rows,cols,size) values ('" +interface_id+ "','" + element_id+ "','"+element_class+"','"+tabindex+"','"+rows+"','"+cols+"','"+size+"')";
			statement1.execute(sql1);
			oConn.close();
		}
		catch(SQLException e){
			System.out.println(".....................................................ERROR..........InsertFormElement........................"+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{

					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}  

	}

	public synchronized static void InsertFormInsertQuery(String interface_id,String part_id,String insert_id, String insert_sql,String insert_parameter) {

		Connection oConn =null;
		Statement statement =null;
		Statement statement1 =null;
		try
		{
			oConn =ds.getConnection();
			statement = oConn.createStatement();
			statement1 = oConn.createStatement();
			String sql1="Insert into form_add_param(interface_id,part_id,query_id,add_param_value,add_parameter) values ('" +interface_id+ "','" + part_id+ "','"+insert_id+"','"+insert_sql+"','"+insert_parameter+"')";
			statement1.execute(sql1);
			oConn.close();
		}
		catch(SQLException e){
			System.out.println(".....................................................ERROR..........InsertFormInsertQuery........................"+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{

					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}  

	}

	public synchronized static void ModifyFormInsertQuery(String interface_id,String part_id,String insert_id, String insert_sql,String insert_parameter) {

		Connection oConn =null;
		Statement statement =null;
		Statement statement1 =null;
		try
		{
			oConn =ds.getConnection();
			statement = oConn.createStatement();
			statement1 = oConn.createStatement();
			String sql1="Insert into form_modify_param(interface_id,part_id,query_id,modify_param_value,modify_parameter) values ('" +interface_id+ "','" + part_id+ "','"+insert_id+"','"+insert_sql+"','"+insert_parameter+"')";
			statement1.execute(sql1);
			oConn.close();
		}
		catch(SQLException e){
			System.out.println(".....................................................ERROR..........ModifyFormInsertQuery........................"+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{

					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}  

	}

	public synchronized static void DeleteFormInsertQuery(String interface_id,String part_id,String insert_id, String insert_sql,String insert_parameter) {

		Connection oConn =null;
		Statement statement =null;
		Statement statement1 =null;
		try
		{
			oConn =ds.getConnection();
			statement = oConn.createStatement();
			statement1 = oConn.createStatement();
			String sql1="Insert into form_delete_param(interface_id,part_id,query_id,delete_param_value,delete_parameter) values ('" +interface_id+ "','" + part_id+ "','"+insert_id+"','"+insert_sql+"','"+insert_parameter+"')";
			statement1.execute(sql1);
			oConn.close();
		}
		catch(SQLException e){
			System.out.println(".....................................................ERROR..........DeleteFormInsertQuery........................"+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{

					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}  

	}

	public synchronized static void SelectFormInsertQuery(String interface_id,String part_id,String insert_id, String insert_sql,String insert_parameter) {

		Connection oConn =null;
		Statement statement =null;
		Statement statement1 =null;
		try
		{
			oConn =ds.getConnection();
			statement = oConn.createStatement();
			statement1 = oConn.createStatement();
			String sql1="Insert into form_select_param(interface_id,part_id,query_id,select_param_value,select_parameter) values ('" +interface_id+ "','" + part_id+ "','"+insert_id+"','"+insert_sql+"','"+insert_parameter+"')";
			statement1.execute(sql1);
			oConn.close();
		}
		catch(SQLException e){
			System.out.println(".....................................................ERROR..........SelectFormInsertQuery........................"+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}  

	}


	public static void InsertTreeDataitem(String interface_id,String part_id,String treedatanode)
	{

		Connection connection =null;
		Statement statement = null;
		try
		{
			connection = ds.getConnection();
			statement = connection.createStatement();
			System.out.println("...........treedatanode........................."+treedatanode);
			PreparedStatement pstmt = connection.prepareStatement("update  tree_structure SET nodestructure= ?  where  interface_id=? and part_id=?");
			pstmt.setString( 1, treedatanode);
			pstmt.setString( 2, interface_id);
			pstmt.setString(3,part_id);
			pstmt.executeUpdate();
			pstmt.close();

			statement.close();
			connection.close();
		}
		catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();
		}
		catch(Exception exception) { exception.printStackTrace(); }
		finally
		{
			if(connection!=null)
			{
				try
				{
					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		}
	}

	public static  String getClass(String interface_id,String part_id)
	{
		String classtype = "";
		Connection oConn = null;
		Statement statement = null;
		ResultSet resultset = null;    
		try
		{
			oConn = ds.getConnection();
			statement = oConn.createStatement();
			resultset = statement.executeQuery("select part_class from structure where part_id='"+part_id+"' and interface_id='"+interface_id+"'");
			while(resultset.next())
			{
				classtype=resultset.getString(1);
			}
			resultset.close();
			statement.close();
			oConn.close();
		}
		catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					resultset.close();
					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}
		return classtype;
	}  

	public static void insertChart(String interface_id,String part_id,String content_id,String matric_title,String description,String X_axis_query,String series1_query, String series2_query,String series3_query,String legenddata1,String legenddata2,String legenddata3,String chart_type,String subtype,String width,String height,String yaxis_label,String color,String transpose,String stacked,String chart_dimension,String xaxis_label)
	{

		Connection connection =null;
		Statement statement = null;
		//Statement statement1 = null;
		PreparedStatement pstmt =null;
		try
		{
			connection = ds.getConnection();
			statement = connection.createStatement();
			//statement1 = connection.createStatement();

			pstmt = connection.prepareStatement("Insert into interface_chart_content(interface_id,part_id,content_id,metric_title,description,X_axis_query,series1_query,series2_query,series3_query,legenddata1,legenddata2,legenddata3,chart_type,subtype,width,height,yaxis_label,color,transpose,stacked,chart_dimension,xaxis_label) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString( 1, interface_id);
			pstmt.setString( 2, part_id);
			pstmt.setString( 3, content_id);
			pstmt.setString( 4, matric_title);
			pstmt.setString( 5, description);
			pstmt.setString( 6, X_axis_query);
			pstmt.setString( 7, series1_query);
			pstmt.setString( 8, series2_query);
			pstmt.setString( 9, series3_query);
			pstmt.setString( 10, legenddata1);
			pstmt.setString( 11, legenddata2);
			pstmt.setString( 12, legenddata3);
			pstmt.setString( 13, chart_type);
			pstmt.setString( 14, subtype);
			pstmt.setString( 15, width);
			pstmt.setString( 16, height);
			pstmt.setString( 17, yaxis_label);
			pstmt.setString( 18, color);
			pstmt.setString( 19, transpose);
			pstmt.setString( 20, stacked);
			pstmt.setString( 21, chart_dimension);
			pstmt.setString( 22, xaxis_label);

			pstmt.executeUpdate();
			pstmt.close();
			connection.close();
		}
		catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();

		}
		catch(Exception exception)
		{


		}
		finally
		{
			if(connection!=null)
			{
				try
				{
					statement.close();
					pstmt.close();
					connection.close();
				} catch(Exception e){}	
			}
		}

	}

	public static void AddReportParameter(String interface_id,String part_id,String content_id,String parameter_name,String parameter_value,String valuesourceitemname,String parameter_value_type)
	{

		Connection connection =null;
		Statement statement = null;
		//Statement statement1 = null;
		PreparedStatement pstmt =null;
		try
		{
			connection = ds.getConnection();
			statement = connection.createStatement();
			//statement1 = connection.createStatement();

			pstmt = connection.prepareStatement("Insert into report_parameter(interface_id,part_id,content_id,parameter_name,parameter_value,value_source_item_name,parameter_value_type) values (?,?,?,?,?,?,?)");
			pstmt.setString( 1, interface_id);
			pstmt.setString( 2, part_id);
			pstmt.setString( 3, content_id);
			pstmt.setString( 4, parameter_name);
			pstmt.setString( 5, parameter_value);
			pstmt.setString( 6, valuesourceitemname);
			pstmt.setString( 7, parameter_value_type);

			pstmt.executeUpdate();
			pstmt.close();
			connection.close();
		}
		catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();
		}
		catch(Exception exception)
		{

		}
		finally
		{
			if(connection!=null)
			{
				try
				{
					statement.close();
					pstmt.close();
					connection.close();
				}  catch(Exception e){}	
			}
		}

	}

	public static void ReportContent(String interface_id,String part_id,String content_id,String viewer_type,String report_chooser,String report_executer,String rpt_file)
	{

		Connection connection =null;
		Statement statement = null;
		//Statement statement1 = null;
		PreparedStatement pstmt =null;
		try
		{
			connection = ds.getConnection();
			statement = connection.createStatement();
			//statement1 = connection.createStatement();

			pstmt = connection.prepareStatement("Insert into interface_report_content(interface_id,part_id,content_id,viewer_type,report_chooser,report_executer,rptdesign_file_name) values (?,?,?,?,?,?,?)");
			pstmt.setString( 1, interface_id);
			pstmt.setString( 2, part_id);
			pstmt.setString( 3, content_id);
			pstmt.setString( 4, viewer_type);
			pstmt.setString( 5, report_chooser);
			pstmt.setString( 6, report_executer);
			pstmt.setString( 7, rpt_file);
			pstmt.executeUpdate();
			pstmt.close();
			connection.close();
		}
		catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();
		}
		catch(Exception exception)
		{

		}
		finally
		{
			if(connection!=null)
			{
				try
				{
					statement.close();
					pstmt.close();
					connection.close();
				}  catch(Exception e){}	
			}
		}

	}


	////////////////////////   FOR APPLICATION DEFAULT////////////////////
	public static  String getCurrentTemplate_ID()
	{
		String template_id = "";
		Connection oConn = null;
		Statement statement = null;
		ResultSet resultset = null;    
		try
		{
			oConn = ds.getConnection();
			statement = oConn.createStatement();
			resultset = statement.executeQuery("select max(application_template_id) from application_template_master");
			while(resultset.next())
			{
				template_id=resultset.getString(1);
			}
			resultset.close();
			statement.close();
			oConn.close();

		}
		catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();
		}
		finally{
			if(oConn!=null)
			{
				try
				{
					resultset.close();
					statement.close();
					oConn.close();
				}catch(Exception e){}	
			}
		}
		return template_id;
	} 
	public static void InsertApplicationDefaultValue(String template_id,String class_name,String section_name,String attribute_name,String attribute_value)
	{

		Connection connection =null;
		Statement statement = null;
		//Statement statement1 = null;
		PreparedStatement pstmt =null;
		try
		{
			connection = ds.getConnection();
			statement = connection.createStatement();
			pstmt = connection.prepareStatement("Insert into application_template_default(application_template_id,section_name,class_name,attribute_name,default_value) values (?,?,?,?,?)");
			pstmt.setString( 1, template_id);
			pstmt.setString( 2, section_name);
			pstmt.setString( 3, class_name);
			pstmt.setString( 4, attribute_name);
			pstmt.setString( 5, attribute_value);
			pstmt.executeUpdate();
			pstmt.close();
			connection.close();
		}
		catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();

		}
		catch(Exception exception)
		{


		}
		finally
		{
			if(connection!=null)
			{
				try
				{
					statement.close();
					pstmt.close();
					connection.close();
				} catch(Exception e){}	
			}
		}

	}

	public synchronized static void TemplateAsset(String current_template_id,String type,String deliverymode,String pagelocation,String deliverysequence,String filename,String assetpath)
	{

		Connection connection =null;
		Statement statement = null;
		//Statement statement1 = null;
		PreparedStatement pstmt =null;
		try
		{
			connection = ds.getConnection();
			statement = connection.createStatement();
			pstmt = connection.prepareStatement("Insert into application_template_asset(application_template_id,delivery_mode,asset_type,location,sequence_no,file_name,asset_path) values (?,?,?,?,?,?,?)");
			pstmt.setString( 1, current_template_id);
			pstmt.setString( 2, deliverymode);
			pstmt.setString( 3, type);
			pstmt.setString( 4, pagelocation);
			pstmt.setString( 5, deliverysequence);
			pstmt.setString( 6, filename);
			pstmt.setString( 7, assetpath);
			pstmt.executeUpdate();
			pstmt.close();
			connection.close();
		}
		catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();

		}
		catch(Exception exception)
		{


		}
		finally
		{
			if(connection!=null)
			{
				try
				{
					statement.close();
					pstmt.close();
					connection.close();
				} catch(Exception e){}	
			}
		}

	}

	public static void updateApplicationDefaultXML(String interface_id,String attachmentname,String s7,String typecollection,String fsize)
	{
		Connection connection =null;
		Statement statement = null;
		try
		{
			connection = ds.getConnection();
			statement = connection.createStatement();
			String file_path=attachmentname+s7;
			InputStream inStream= new FileInputStream(file_path);
			PreparedStatement pstmt = connection.prepareStatement("update  framework_file SET value= ? , filesize= ? where framework_file_id=? ");
			pstmt.setBinaryStream( 1, inStream,Integer.parseInt(fsize));
			pstmt.setString(2,fsize);
			pstmt.setString(3,interface_id);                       
			pstmt.executeUpdate();

			pstmt.close();

			statement.close();
			connection.close();
		}
		catch(SQLException sqlexception)
		{    
			sqlexception.printStackTrace();
		}
		catch(Exception exception)
		{


		}
		finally
		{
			if(connection!=null)
			{
				try
				{

					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		}    
	}

	public synchronized static void DeleteApplicationDefault( )
	{
		Connection connection =null;
		Statement statement =null;
		//Statement stmt =null;

		try
		{
			connection =ds.getConnection();
			statement = connection.createStatement();
			//stmt = connection.createStatement();
			statement.execute("delete from application_default");
			connection.close();
		}
		catch(SQLException sqlexception)
		{    
			sqlexception.printStackTrace();
		}
		catch(Exception exception)
		{
		}
		finally
		{
			if(connection!=null)
			{
				try
				{

					statement.close();
					connection.close();
				} catch(Exception e){}	
			}
		}  
	}

	public static synchronized void ThemesInsert(String themes_id,String attachmentname,String s7,String size)
	{
		//ResultSet resultset1 = null;
		Connection oConn = null;
		Statement statement  = null;
		try
		{
			oConn = ds.getConnection();
			statement = oConn.createStatement();
			String file_path=attachmentname+s7;
			InputStream inStream= new FileInputStream(file_path);
			statement.execute("Insert into themes(themes_id)values('"+themes_id+"')");

			PreparedStatement pstmt = oConn.prepareStatement("update  themes SET xml_value= ? where themes_id=?");
			pstmt.setBinaryStream( 1, inStream,Integer.parseInt(size));
			pstmt.setString(2,themes_id);    
			pstmt.executeUpdate();
			pstmt.close();
			oConn.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}

	}

	public static synchronized void TemplateInsert(String template_title,String attachmentname,String s7,String size)
	{
		ResultSet resultset1 = null;
		Connection oConn = null;
		Statement statement  = null;
		Statement statement1  = null;
		try
		{
			oConn = ds.getConnection();
			statement = oConn.createStatement();
			statement1 = oConn.createStatement();
			String file_path=attachmentname+s7;
			InputStream inStream= new FileInputStream(file_path);
			statement.execute("Insert into application_template_master(application_template_title,upload_on)values('"+template_title+"',sysdate())");
			for(resultset1=statement1.executeQuery("select max(application_template_id) from application_template_master");resultset1.next();)
			{
				String  application_template_id=resultset1.getString(1);
				PreparedStatement pstmt = oConn.prepareStatement("update  application_template_master SET applivation_xml_value= ? where application_template_id=?");
				pstmt.setBinaryStream( 1, inStream,Integer.parseInt(size));
				pstmt.setString(2,application_template_id);    
				pstmt.executeUpdate();
				pstmt.close();
			}
			oConn.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}

	}

	public static synchronized void ThemesElementInsert(String themes_id,String class_type,String type,String cssclasses,String property,String propertyapplication)
	{
		//ResultSet resultset1 = null;
		Connection oConn = null;
		Statement statement  = null;
		try
		{
			oConn = ds.getConnection();
			statement = oConn.createStatement();
			statement.execute("Insert into themes_definition(themes_id,class_type,prop_type,css_classes,properties,property_application)values('"+themes_id+"','"+class_type+"','"+type+"','"+cssclasses+"','"+property+"','"+propertyapplication+"')");
			oConn.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}

	}

	public static synchronized void ThemesCssFileInsert(String themes_id,String name)
	{
		//ResultSet resultset1 = null;
		Connection oConn = null;
		Statement statement  = null;
		try
		{
			oConn = ds.getConnection();
			statement = oConn.createStatement();
			statement.execute("Insert into themes_css(themes_id,css_value)values('"+themes_id+"','"+name+"')");
			oConn.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}

	}

	public static Vector<InputStream> getThemesXMLValue(String themes_id)
	{
		Statement  oStmt=null;
		ResultSet oRset=null;
		Connection oConn =null;
		Vector<InputStream> cols=new Vector<InputStream>();
		try {
			oConn =ds.getConnection();
			oStmt = oConn.createStatement();
			for(oRset = oStmt.executeQuery("select xml_value from themes where themes_id='"+themes_id+"'");oRset.next();)
			{
				cols.addElement(oRset.getAsciiStream(1));
			}
			oRset.close();
			oStmt.close();
			oConn.close();
		}
		catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
		}
		catch (Exception ex) {
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					oRset.close();
					oStmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}    
		return cols;		
	}

	public static Vector<InputStream> getApplicationDefaultXMLValue(String application_template_id)
	{
		Statement  oStmt=null;
		ResultSet oRset=null;
		Connection oConn =null;
		Vector<InputStream> cols=new Vector<InputStream>();
		try {
			oConn =ds.getConnection();
			oStmt = oConn.createStatement();
			for(oRset = oStmt.executeQuery("select applivation_xml_value from application_template_master where application_template_id='"+application_template_id+"'");oRset.next();)
			{
				cols.addElement(oRset.getAsciiStream(1));
			}
			oRset.close();
			oStmt.close();
			oConn.close();
		}
		catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
		}
		catch (Exception ex) {
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					oRset.close();
					oStmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}    
		return cols;		
	}



	public static synchronized boolean themesDelete(String themes_id)
	{
		//ResultSet resultset1 = null;
		boolean isSuccess=true;
		Connection oConn = null;
		Statement statement  = null;
		try
		{
			oConn = ds.getConnection();
			statement = oConn.createStatement();
			statement.execute("Delete from themes where themes_id='"+themes_id+"'");
			statement.execute("Delete from themes_definition where themes_id='"+themes_id+"'");
			statement.execute("Delete from themes_css where themes_id='"+themes_id+"'");

			oConn.close();
		}
		catch(SQLException e)
		{
			isSuccess=false;
			e.printStackTrace();
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}
		return isSuccess;

	}

	public static synchronized boolean templateDelete(String template_id)
	{
		
		Connection oConn = null;
		Statement statement  = null;
		boolean isSuccess=true;;
		try
		{
			oConn = ds.getConnection();
			statement = oConn.createStatement();
			statement.execute("Delete from application_template_master where application_template_id='"+template_id+"'");
			statement.execute("Delete from application_template_asset where application_template_id='"+template_id+"'");
			statement.execute("Delete from application_template_default where application_template_id='"+template_id+"'");

			oConn.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			isSuccess=false;
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}
		return isSuccess;
	}


	public static synchronized boolean setDefaultValue(String themes_id)
	{
		
		Connection oConn = null;
		Statement statement  = null;
		boolean isSuccess=true;
		try
		{
			oConn = ds.getConnection();
			statement = oConn.createStatement();
			statement.execute("update themes set default_value=NULL");
			statement.execute("update themes set default_value='yes' where themes_id='"+themes_id+"'");
			
			// statement.execute("update themes set default_value='"+value+"'where themes_id='"+themes_id+"'");

			oConn.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			isSuccess=false;
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}
		return isSuccess;
	}

	public static synchronized boolean setDefaultValueTemplate(String application_template_id)
	{
		Connection oConn = null;
		Statement statement  = null;
		boolean isSuccess=true;
		try
		{
			oConn = ds.getConnection();
			statement = oConn.createStatement();
			statement.execute("update application_template_master set default_value=NULL");
			statement.execute("update application_template_master set default_value='yes' where application_template_id='"+application_template_id+"'");

			oConn.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			isSuccess=false;
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}
		return isSuccess;
	}

	public static synchronized void AssetFileInsert(String file_name,String attachmentname,String s7,String size,String asset_type)
	{
		ResultSet resultset = null;
		Connection oConn = null;
		Statement statement  = null;
		try
		{
			oConn = ds.getConnection();
			statement = oConn.createStatement();
			String file_path=attachmentname+s7;
			InputStream inStream= new FileInputStream(file_path);
			Integer asset_id=0;
			 statement.execute("Insert into framework_asset(file_name,upload_on,asset_type)values('"+file_name+"',sysdate(),'"+asset_type+"')");

			for(resultset=statement.executeQuery("select max(asset_id) from framework_asset");resultset.next();)
			{
				asset_id=resultset.getInt(1); 
				PreparedStatement pstmt = oConn.prepareStatement("update  framework_asset SET value= ? where asset_id=? and file_name=?");
				pstmt.setBinaryStream( 1, inStream,Integer.parseInt(size));
				pstmt.setInt(2,asset_id);  
				pstmt.setString(3,file_name);    
				pstmt.executeUpdate();
				pstmt.close();
			}

			oConn.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}

	}
	public static synchronized void AssetFileDelete(String asset_id)
	{
		Connection oConn = null;
		Statement statement  = null;
		try
		{
			oConn = ds.getConnection();
			statement = oConn.createStatement();
			 statement.execute("Delete from framework_asset where asset_id='"+asset_id+"'");
			oConn.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					statement.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}

	}

	public static Vector<InputStream> getThemesCSSValue(String asset_id)
	{
		Statement  oStmt=null;
		ResultSet oRset=null;
		Connection oConn =null;
		Vector<InputStream> cols=new Vector<InputStream>();
		try {
			oConn =ds.getConnection();
			oStmt = oConn.createStatement();
			for(oRset = oStmt.executeQuery("select value from framework_asset where asset_id='"+asset_id+"'");oRset.next();)
			{
				cols.addElement(oRset.getAsciiStream(1));
			}
			oRset.close();
			oStmt.close();
			oConn.close();
		}
		catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
		}
		catch (Exception ex) {
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					oRset.close();
					oStmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}    
		return cols;		
	}

	/*********************************** Partha For Cache**************************/

	public synchronized static void addCacheDefinition(String cachename,String cache_type,String maxelem,String overflowtodisk,String memoryStoreEvictionPolicy,String eternal,String timeToLiveSeconds,String timeToIdleSeconds,String diskExpiryThreadIntervalSeconds,String diskStorePath,String diskPersistent,String default_cache) {
		Connection oConn = null;
		Statement statement = null;
		try
		{
			oConn = ds.getConnection();
			oConn.setAutoCommit(false);
			statement = oConn.createStatement();
			String sql="Insert into cache_definition(cache_name,cache_type,max_element,overflowtodisk,timetoliveseconds,timetoidleseconds,diskpersistent,eternal,diskexpirythreadintervalseconds,memorystoreevictionpolicy,diskstorepath,default_cache) values ('" +cachename+ "','" + cache_type+ "','" + maxelem+ "'," + overflowtodisk+ ",'" + timeToLiveSeconds+ "','" + timeToIdleSeconds+ "'," + diskPersistent+ "," + eternal+ ",'"+diskExpiryThreadIntervalSeconds+"','"+memoryStoreEvictionPolicy+"','"+diskStorePath+"','"+default_cache+"')";
			statement.execute(sql);

			oConn.commit();
			oConn.setAutoCommit(true);
		}
		catch(SQLException e){
			e.printStackTrace();
			try{
			}catch(Exception re){log.debug("Exception: "+re);}

		}
		finally{
			if(oConn!=null)
			{
				try
				{
					if(statement!=null)
						statement.close();

					oConn.close();
				}catch(Exception e){}	
			}
		}

	}	 

	public synchronized static void modifyCacheDefinition(String cacheid,String cachename,String cache_type,String maxelem,String overflowtodisk,String memoryStoreEvictionPolicy,String eternal,String timeToLiveSeconds,String timeToIdleSeconds,String diskExpiryThreadIntervalSeconds,String diskStorePath,String diskPersistent,String default_cache) {

		Statement  oStmt=null;
		Connection oConn = null;
		try {
			oConn = ds.getConnection();
			oStmt = oConn.createStatement();
			oStmt.execute("update cache_definition set cache_name = '"+cachename+"',cache_type = '"+cache_type+"',max_element='"+maxelem+"',overflowtodisk="+overflowtodisk+",timetoliveseconds='"+timeToLiveSeconds+"',timetoidleseconds='"+timeToIdleSeconds+"',diskpersistent="+diskPersistent+",eternal="+eternal+",diskexpirythreadintervalseconds='"+diskExpiryThreadIntervalSeconds+"',memorystoreevictionpolicy='"+memoryStoreEvictionPolicy+"',diskstorepath='"+diskStorePath+"',default_cache='"+default_cache+"' where cache_id = '"+cacheid+"'");

		}
		catch (SQLException e) {
			log.debug("SQLException in modifyCacheDefinition. Rolling Back Operation "+e.getMessage());
		}
		catch (Exception ex) {
		}
		finally{
			if(oConn!=null)
			{
				try
				{
					if(oStmt!=null)
						oStmt.close();
					oConn.close();
				}catch(Exception e){}	
			}
		}


	}	 

	public synchronized static void deleteCacheDefinition(String cacheid) {
		Statement  oStmt=null;
		Connection oConn = null;
		try {
			oConn = ds.getConnection();
			oStmt = oConn.createStatement();
			oStmt.execute("delete from cache_definition where cache_id ='"+cacheid+"'" );


		}
		catch (SQLException e) {
			log.debug(" error due to SQL exception deleteTemplate"+e.getMessage());
		}
		catch (Exception ex) {
			log.debug(" error due to java.lang.exception");
			ex.printStackTrace();
			log.debug(" printStack is :: " + ex.getMessage());
		}
		finally{
			if(oConn!=null)
			{
				try
				{
					if(oStmt!=null)
						oStmt.close();
					oConn.close();
				}catch(Exception e){}	
			}
		}

	}

	
	public  static Vector<Vector<String>> getCacheVector() {
		Statement  oStmt=null;
		ResultSet  oRset=null;
		Vector<Vector<String>> vAdministratorList = new Vector<Vector<String>>();
		Connection oConn =null;


		try	{
			oConn = ds.getConnection();
			oStmt = oConn.createStatement();
			oRset=oStmt.executeQuery("select cache_name,cache_name from  cache_definition");
			while(oRset.next())	{
				Vector<String> vAdministrator = new Vector<String>();

				String program_id=oRset.getString(1);
				String name=oRset.getString(2);
				vAdministrator.addElement(program_id);
				vAdministrator.addElement(name);
				vAdministratorList.addElement(vAdministrator);
			}
			oRset.close();
			oStmt.close();
			oConn.close();
		}
		catch (SQLException e){
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{

					if(oRset!=null)
						oRset.close();
					if(oStmt!=null)
						oStmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}
		return vAdministratorList;
	}

	public synchronized static boolean isDefaultCacheExists() {

		Statement  oStmt=null;
		ResultSet oRset = null;
		Connection oConn = null;
		boolean flag=false;

		try {
			oConn = ds.getConnection();
			oStmt = oConn.createStatement();
			System.out.println("select cache_id from cache_definition where default_cache='Yes'");
			oRset=oStmt.executeQuery("select cache_id from cache_definition where default_cache='Yes'");
			if(oRset.next())
				flag=true;
			else
			{

				flag=false;
			}

		}
		catch (SQLException e) {
			log.debug("SQLException in isDefaultCacheExists. Rolling Back Operation "+e.getMessage());
		}
		catch (Exception ex) {
		}
		finally{
			if(oConn!=null)
			{
				try
				{
					if(oStmt!=null)
						oStmt.close();
					if(oRset!=null)
						oRset.close();
					oConn.close();
				}catch(Exception e){}	
			}
		}
		return flag;

	}

	/**************************** End of Partha For Cache **********************/

	/////////////////////////SUBIR/////////////////////////////////

	public static Vector<String[]> getFrameworkData(String...types ) {

		Vector<String[]> vIIDs = new Vector<String[]>();
		Connection oConn = null;
		Statement oStmt = null;
		ResultSet oRset = null;
		try	{
			oConn = ds.getConnection();
			oStmt = oConn.createStatement();
			if(types!=null && types.length>0){
				String inQuery="(";
				int typeItr=1;
				for(String type:types){
					inQuery=inQuery.concat("'"+type+"'");
					if(typeItr!=types.length){
						inQuery=inQuery.concat(", ");
					}
					typeItr++;
				}
				inQuery=inQuery.concat(")");
				oRset=oStmt.executeQuery("SELECT f.`framework_file_id`, f.`inlinecss`, f.`inlinejs`, f.`imagepath`,f.`type` FROM framework_file f WHERE f.`type` in "+inQuery);
			}else{
				oRset=oStmt.executeQuery("SELECT f.`framework_file_id`, f.`inlinecss`, f.`inlinejs`, f.`imagepath`,f.`type` FROM framework_file f WHERE f.`type`='Interface'");
			}
			
			while(oRset.next()) {
				String str[] = new String[5];
				str[0] = oRset.getString(1);
				str[1] = oRset.getString(2);
				str[2] = oRset.getString(3);
				str[3] = oRset.getString(4);
				str[4] = oRset.getString(5);
				vIIDs.addElement(str);
			}
			oRset.close();
			oConn.close();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					oRset.close();
					oStmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}
		return vIIDs;
	}

	/////////////////////////SUBIR/////////////////////////////////
	///////////////////////SHIBAJI////////////////////////////////
	public static Vector<Vector<Object>> getInterfaceRoleFile()
	{


		Connection oConn =null; 
		Vector<Vector<Object>> vSrcFile = new Vector<Vector<Object>>(3,3);
		Statement  oStmt=null;
		ResultSet oRset =null; 
		try {
			oConn = ds.getConnection();
			oStmt = oConn.createStatement();
			for(oRset = oStmt.executeQuery("SELECT filename,value FROM framework_file f WHERE f.type=\"RoleXML\"");oRset.next();)
			{
				Vector<Object> cols = new Vector<Object>();
				cols.addElement(oRset.getString(1));
				cols.addElement(oRset.getAsciiStream(2));
				vSrcFile.addElement(cols);

			}
			oStmt.close();
			oConn.close();
		}
		catch (SQLException sqlexception) {
			System.out.println("Exception in ConfDatabaseLayer.getModuleSrc()");
			System.out.println("The Error Message - " + sqlexception.getMessage());
		}
		catch (Exception ex) {
			System.out.println("Exception in ConfDatabaseLayer.getModuleSrc()");
			System.out.println("The Error Message - " + ex.getMessage());
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					oRset.close();
					oStmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}
		return vSrcFile;		
	}

	public static Vector<Vector<Object>> getManifestFile()
	{


		Connection oConn =null; 
		Vector<Vector<Object>> vSrcFile = new Vector<Vector<Object>>(3,3);
		Statement  oStmt=null;
		ResultSet oRset =null; 
		try {
			oConn = ds.getConnection();
			oStmt = oConn.createStatement();
			for(oRset = oStmt.executeQuery("SELECT filename,value FROM framework_file f WHERE f.type=\"Manifest\"");oRset.next();)
			{
				Vector<Object> cols = new Vector<Object>();
				cols.addElement(oRset.getString(1));
				cols.addElement(oRset.getAsciiStream(2));
				vSrcFile.addElement(cols);

			}
			oStmt.close();
			oConn.close();
		}
		catch (SQLException sqlexception) {
			System.out.println("Exception in ConfDatabaseLayer.getModuleSrc()");
			System.out.println("The Error Message - " + sqlexception.getMessage());
		}
		catch (Exception ex) {
			System.out.println("Exception in ConfDatabaseLayer.getModuleSrc()");
			System.out.println("The Error Message - " + ex.getMessage());
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					oRset.close();
					oStmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}
		return vSrcFile;		
	}


	public static Vector<String> getAllInterFaceIDUnderCollection(String manifest_id)
	{


		Connection oConn =null; 
		Vector<String> vSrcFile = new Vector<String>();
		Statement  oStmt=null;
		ResultSet oRset =null; 
		try {
			oConn = ds.getConnection();
			oStmt = oConn.createStatement();
			for(oRset = oStmt.executeQuery("select a.interface_id,b.filename from manifestinterfaceassociation a,framework_file b where a.manifest_id='"+manifest_id+"' and a.interface_id=b.framework_file_id");oRset.next();)
			{
				vSrcFile.addElement(oRset.getString(1));
				vSrcFile.addElement(oRset.getString(2));
			}
			oStmt.close();
			oConn.close();
		}
		catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally
		{
			if(oConn!=null)
			{
				try
				{
					oRset.close();
					oStmt.close();
					oConn.close();
				} catch(Exception e){}	
			}
		}
		return vSrcFile;		
	}

	public static void AddFlashParameter(String interface_id,String part_id,String content_id,String parameter_name,String parameter_value)
	{

		Connection connection =null;
		Statement statement = null;
		PreparedStatement pstmt =null;
		try
		{
			connection = ds.getConnection();
			statement = connection.createStatement();

			pstmt = connection.prepareStatement("Insert into flash_parameter(interface_id,part_id,content_id,parameter_name,parameter_value) values (?,?,?,?,?)");
			pstmt.setString( 1, interface_id);
			pstmt.setString( 2, part_id);
			pstmt.setString( 3, content_id);
			pstmt.setString( 4, parameter_name);
			pstmt.setString( 5, parameter_value);


			pstmt.executeUpdate();
			pstmt.close();
			connection.close();
		}
		catch(SQLException sqlexception)
		{
			sqlexception.printStackTrace();
		}
		catch(Exception exception)
		{

		}
		finally
		{
			if(connection!=null)
			{
				try
				{
					statement.close();
					pstmt.close();
					connection.close();
				}  catch(Exception e){}	
			}
		}

	}
	
	public static  String checkInterfaceRoleID(String interfaceId,String roleId,String layoutId,String contentId,String behaviourId,String styleId)
	{
		String checkId = null;
		Connection oConn = null;

		try
		{
			oConn = ds.getConnection();
			PreparedStatement statement = oConn.prepareStatement("select interface_id from roleassociation where interface_id = ? and role_id = ? and layout_id = ? and content_id=? and behaviour_id=? and style_id=?");
			statement.setString(1, interfaceId);
			statement.setString(2, roleId);
			statement.setString(3, layoutId);
			statement.setString(4, contentId);
			statement.setString(5, behaviourId);
			statement.setString(6, styleId);
			ResultSet resultset = statement.executeQuery();
			while(resultset.next())
			{
				checkId=resultset.getString(1);
			}
			resultset.close();
			statement.close();
			oConn.close();
		}
		catch(SQLException sqlexception)
		{
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		return checkId;
	}
	public static  String addInterfaceRole(String roleId,String interfaceId,String layoutId,String contentId,String behaviourId,String styleId)
	{
		String checkId = null;
		Connection oConn = null;

		try
		{
			oConn = ds.getConnection();
			Statement statement = oConn.createStatement();
			statement.execute("Insert into roleassociation(interface_id, role_id, layout_id,content_id,behaviour_id,style_id) values('"+interfaceId+"','"+roleId+"','"+layoutId+"','"+contentId+"','"+behaviourId+"','"+styleId+"')");
			statement.close();
			oConn.close();
		}
		catch(SQLException sqlexception)
		{
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		return checkId;
	}
	public static List<InterfaceElement> retrieveAvailaibleInterfaceForRoleID(String roleId)
	{
		Connection oConn = null;
		List<InterfaceElement> interfaceList=null;
		try
		{
			oConn = ds.getConnection();
			PreparedStatement statement = oConn.prepareStatement("select interface_id,interface_title from interface where interface_id not in (select interface_id from roleassociation where role_id=?)");
			statement.setString(1, roleId);
			ResultSet resultset = statement.executeQuery();
			interfaceList=new ArrayList<>();
			while(resultset.next())
			{
				InterfaceElement interfaceElement=new InterfaceElement();
				interfaceElement.setId(resultset.getString(1));
				interfaceElement.setTitle(resultset.getString(2));
				interfaceList.add(interfaceElement);
			}
			resultset.close();
			statement.close();
			oConn.close();
		}
		catch(SQLException sqlexception)
		{
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		return interfaceList;
	}
	
	
}
