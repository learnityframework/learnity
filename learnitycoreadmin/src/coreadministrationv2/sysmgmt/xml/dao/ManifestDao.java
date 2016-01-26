package coreadministrationv2.sysmgmt.xml.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import comv2.aunwesha.param.CoreAdminInitHostInfo;

import coreadministrationv2.sysmgmt.xml.dto.manifest.InterfaceElement;

public class ManifestDao {

	private static DataSource dataSource = CoreAdminInitHostInfo.ds;

	public static String getManifestId() {
		String manifestId = null;
		try {
			Connection oConn = dataSource.getConnection();
			Statement oStmt = null;
			ResultSet oRset = null;
			oStmt = oConn.createStatement();
			oRset = oStmt.executeQuery("select distinct manifest_id from manifestinterfaceassociation");
			if (oRset != null) {
				while (oRset.next()) {
					manifestId = oRset.getString(1);
				}
			}
			closeConnection(oConn, oStmt, oRset);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return manifestId;
	}

	public static List<InterfaceElement> getManifestDetails(String manifestId) {
		List<InterfaceElement> interfaceElements = null;
		try {
			Connection oConn = dataSource.getConnection();
			Statement oStmt = null;
			ResultSet oRset = null;
			oStmt = oConn.createStatement();
			oRset = oStmt
					.executeQuery("select f.framework_file_id,f.framework_file_title,f.type,f.filename from manifestinterfaceassociation m,framework_file f "
							+ "where m.interface_id=f.framework_file_id and m.manifest_id='" + manifestId + "'");
			if (oRset != null) {
				interfaceElements = new ArrayList<>();
				while (oRset.next()) {
					InterfaceElement interfaceElement = new InterfaceElement();
					interfaceElement.setId(oRset.getString(1));
					interfaceElement.setTitle(oRset.getString(2));
					interfaceElement.setType(oRset.getString(3));
					interfaceElement.setZip(oRset.getString(4));
					interfaceElements.add(interfaceElement);
				}
			}
			closeConnection(oConn, oStmt, oRset);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return interfaceElements;
	}

	public static boolean checkManifestAssociation(String interfaceId) {
		boolean isAssociated=false;
		try {
			Connection oConn = dataSource.getConnection();
			Statement oStmt = null;
			ResultSet oRset = null;
			oStmt = oConn.createStatement();
			oRset = oStmt.executeQuery("select count(*) from manifestinterfaceassociation where interface_id='" + interfaceId + "'");
			if (oRset != null) {
				while (oRset.next()) {
					int count=oRset.getInt(1);
					if(count==1){
						isAssociated=true;
					}
				}
			}
			closeConnection(oConn, oStmt, oRset);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isAssociated;
	}

	private static void closeConnection(Connection connection, Statement oStmt, ResultSet oRset) throws SQLException {
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
