package coreadministrationv2.sysmgmt.xml.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import comv2.aunwesha.lfutil.GenericUtil;
import comv2.aunwesha.param.CoreAdminInitHostInfo;

import coreadministrationv2.sysmgmt.xml.dto.interfacerole.BehaviourElement;
import coreadministrationv2.sysmgmt.xml.dto.interfacerole.ContentElement;
import coreadministrationv2.sysmgmt.xml.dto.interfacerole.InterfaceElement;
import coreadministrationv2.sysmgmt.xml.dto.interfacerole.LayoutElement;
import coreadministrationv2.sysmgmt.xml.dto.interfacerole.Role;
import coreadministrationv2.sysmgmt.xml.dto.interfacerole.StyleElement;

public class InterfaceRoleDao {

	private static DataSource dataSource=CoreAdminInitHostInfo.ds;
	
	public static List<InterfaceElement> getRoleInterfaceDetails(Role role) {
		List<InterfaceElement> interfaceElements=null;
		try {
			Connection oConn = dataSource.getConnection();
			PreparedStatement pStmt = null;
			ResultSet oRset = null;
			pStmt = oConn.prepareStatement("select interface_id, layout_id,content_id,behaviour_id,style_id from roleassociation ra where ra.role_id=?");
			pStmt.setInt(1, role.getId());
			oRset=pStmt.executeQuery();
			if(oRset!=null){
				interfaceElements=new ArrayList<InterfaceElement>();
				while(oRset.next()) {
					InterfaceElement interfaceElement=new InterfaceElement();
					interfaceElement.setId(oRset.getString(1));
					if(GenericUtil.hasString(oRset.getString(2))){
						interfaceElement.setLayoutElement(new LayoutElement(oRset.getString(2)));
					}
					if(GenericUtil.hasString(oRset.getString(3))){
						interfaceElement.setContentElement(new ContentElement(oRset.getString(3)));
					}
					if(GenericUtil.hasString(oRset.getString(4))){
						interfaceElement.setBehaviourElement(new BehaviourElement(oRset.getString(4)));
					}
					if(GenericUtil.hasString(oRset.getString(5))){
						interfaceElement.setStyleElement(new StyleElement(oRset.getString(5)));
					}
					
					interfaceElements.add(interfaceElement);
				}
			}
			closeConnection(oConn, pStmt, oRset);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return interfaceElements;
	}
	
	public static List<Role> getDistinctRoleList(){

		List<Role> roleList=null;
		try {
			Connection oConn = dataSource.getConnection();
			Statement oStmt = null;
			ResultSet oRset = null;
			oStmt = oConn.createStatement();
			oRset = oStmt
					.executeQuery("select distinct r.title,r.role_id from role r,roleassociation ra where ra.role_id=r.role_id");
			if(oRset!=null){
				roleList=new ArrayList<Role>();
				while(oRset.next()) {
					Role role=new Role();
					role.setId(oRset.getInt(2));
					role.setTitle(oRset.getString(1));
					roleList.add(role);
				}
			}
			closeConnection(oConn, oStmt, oRset);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return roleList;
	
	}
	
	private static void closeConnection(Connection connection, Statement oStmt,
			ResultSet oRset) throws SQLException {
		if (oRset != null && !oRset.isClosed()) {
			oRset.close();
		}
		if (oStmt != null && !oStmt.isClosed()) {
			oStmt.close();
		}
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}
}
