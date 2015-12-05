package coreadministrationv2.sysmgmt;

/**
 * Title:        Learnity Interface Management   
 * Description:
 * Copyright:    Copyright (c) 2008
 * Company:      Aunwesha
 * @author Shibaji Chatterjee
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comv2.aunwesha.lfutil.FileUtil;
import comv2.aunwesha.lfutil.GenericUtil;

import coreadministrationv2.dbconnection.DataBaseLayer;
import coreadministrationv2.sysmgmt.xml.dao.InterfaceRoleDao;
import coreadministrationv2.sysmgmt.xml.dao.ManifestDao;
import coreadministrationv2.sysmgmt.xml.dto.interfacerole.InterfaceElement;
import coreadministrationv2.sysmgmt.xml.dto.interfacerole.Role;
import coreadministrationv2.sysmgmt.xml.dto.interfacerole.RoleContainer;
import coreadministrationv2.sysmgmt.xml.dto.manifest.ManifestContainer;
import coreadministrationv2.sysmgmt.xml.util.GenericDto;
import coreadministrationv2.sysmgmt.xml.util.XMLGenerator;

public class DownloadInterface extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		InputStream in;

		String interface_id = req.getParameter("interface_id");

		String filename = req.getParameter("filename");
		String type = req.getParameter("type");
		System.out.println("....interface_id..........." + interface_id
				+ "......type...." + type + "......file..." + filename);
		
		/*
		 * Modified by Dibyarup from creating the role xml from database
		 */
		ResourceBundle rb = ResourceBundle.getBundle("portal",Locale.getDefault());
		String filePath = rb.getString("xml");
		
		if(type.equals("GenerateRoleXML")){
			GenericDto genericDto=createRoleXml();
			byte[] xmlByteArray=XMLGenerator.getXmlDoc(genericDto);
			if(xmlByteArray.length>0){
				String fileName="RoleXml_"+new Date().getTime()+".xml";
				String fileFullPath=filePath.concat(fileName);
				fileFullPath=FileUtil.createFile(xmlByteArray, fileFullPath);
				
				if(GenericUtil.hasString(fileFullPath)){
					DataBaseLayer.updateRoleXML("rolexml", filePath, fileName, null, new Integer(xmlByteArray.length).toString());
					FileUtil.downlaodFile(xmlByteArray, fileName, res);
				}
			}
		}
		else if(type.equals("GenerateManifestXML")){
			GenericDto genericDto=createManifestXml();
			byte[] xmlByteArray=XMLGenerator.getXmlDoc(genericDto);
			if(xmlByteArray.length>0){
				String fileName="ManifestXml_"+new Date().getTime()+".xml";
				String fileFullPath=filePath.concat(fileName);
				fileFullPath=FileUtil.createFile(xmlByteArray, fileFullPath);
				
				if(GenericUtil.hasString(fileFullPath)){
					DataBaseLayer.updateManifest("manifestxml",filePath, fileName, null, new Integer(xmlByteArray.length).toString());
					FileUtil.downlaodFile(xmlByteArray, fileName, res);
				}
			}
		}
		else if (type.equals("RoleXML")) {
			
			
			Vector vModule = DataBaseLayer.getFramework_FileDetails(
					interface_id, filename);

			in = (InputStream) vModule.elementAt(0);
			FileUtil.downlaodFile(in, filename, res);
		}

		else if (type.equals("Manifest")) {
			Vector vModule = DataBaseLayer.getFramework_FileDetails(
					interface_id, filename);
			in = (InputStream) vModule.elementAt(0);
			FileUtil.downlaodFile(in, filename, res);
		}

		else if (type.equals("Interface")) {
			int size = 1;
			String name = interface_id + ".zip";
			res.setContentType("application/zip");
			res.setHeader("Content-Disposition", "attachment; filename=\""
					+ name + "\"");
			String path = "";
			ZipOutputStream ouF;
			String key1 = "downloadzip";
			path = rb.getString(key1);
			File ff = new File(path + interface_id);
			ff.mkdir();
			ServletOutputStream out = res.getOutputStream();
			ouF = new ZipOutputStream(out);
			Interfacecreation(interface_id, path, size, ouF, name);
		}

		else if (type.equals("InterfaceFragment")) {
			int size = 1;
			String name = interface_id + ".zip";
			String xmlname = "manifest.xml";
			res.setContentType("application/zip");
			res.setHeader("Content-Disposition", "attachment; filename=\""
					+ name + "\"");
			String path = "";
			ZipOutputStream ouF;
			String folderpath = "";
			String key1 = "downloadzip";
			path = rb.getString(key1);
			File ff = new File(path + interface_id);
			ff.mkdir();
			ServletOutputStream out = res.getOutputStream();
			ouF = new ZipOutputStream(out);
			Interfacecreation(interface_id, path, size, ouF, name);
		} else {
			int size = 1;
			ZipOutputStream ouF;
			Vector pathvector = new Vector();
			Vector pathvector1 = new Vector();
			String name = interface_id + ".zip";
			String xmlname = "manifest.xml";
			res.setContentType("application/zip");
			res.setHeader("Content-Disposition", "attachment; filename=\""
					+ name + "\"");
			// res.setHeader ("Content-Disposition",
			// "attachment; filename=\"LearnityInterface\"");

			String path = "";
			String folderpath = "";
			String key1 = "downloadzip";
			path = rb.getString(key1);
			File ff = new File(path + interface_id);
			ff.mkdir();
			ServletOutputStream out = res.getOutputStream();
			ouF = new ZipOutputStream(out);

			/****************************** FOR INTERFACEROLE *********************/
			Vector rolefile = DataBaseLayer.getInterfaceRoleFile();
			// byte[] buffer = new byte[18024];
			try {
				for (int i = 0; i < rolefile.size(); i++) {
					Vector vModule = (Vector) rolefile.elementAt(i);
					in = (InputStream) vModule.elementAt(1);
					String file_name = (String) vModule.elementAt(0);
					File dst = new File(path + interface_id + File.separator
							+ file_name);
					OutputStream resourceout = new FileOutputStream(dst);
					pathvector.addElement(path + interface_id + File.separator
							+ file_name);
					pathvector1.addElement(interface_id + File.separator
							+ file_name);
					byte[] buf = new byte[1024];
					int len;
					while ((len = in.read(buf)) > 0) {
						resourceout.write(buf, 0, len);
					}
					in.close();
					resourceout.close();
				}
			} catch (IllegalArgumentException iae) {
				iae.printStackTrace();
			} catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			}
			/****************************** FOR MANIFEST *********************/
			Vector manifestfile = DataBaseLayer.getManifestFile();
			// byte[] buffer = new byte[18024];
			try {
				for (int i = 0; i < manifestfile.size(); i++) {
					Vector vModule = (Vector) manifestfile.elementAt(i);
					in = (InputStream) vModule.elementAt(1);
					String file_name = (String) vModule.elementAt(0);
					File dst = new File(path + interface_id + File.separator
							+ file_name);
					OutputStream resourceout = new FileOutputStream(dst);
					pathvector.addElement(path + interface_id + File.separator
							+ file_name);
					pathvector1.addElement(interface_id + File.separator
							+ file_name);
					byte[] buf1 = new byte[1024];
					int len;
					while ((len = in.read(buf1)) > 0) {
						resourceout.write(buf1, 0, len);
					}
					in.close();
					resourceout.close();
				}
			} catch (IllegalArgumentException iae) {
				iae.printStackTrace();
			} catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			}
			/************************* ALL INTERFACE ZIP ***********************/

			Vector interfacefile = DataBaseLayer
					.getAllInterFaceIDUnderCollection(interface_id);
			try {
				for (int i = 0; i < interfacefile.size(); i = i + 2) {
					String ind_interface_id = (String) interfacefile
							.elementAt(i);
					String ind_file_name = (String) interfacefile
							.elementAt(i + 1);
					File finterface = new File(path + interface_id
							+ File.separator + ind_interface_id);
					finterface.mkdir();
					ZipOutputStream ouF1;
					ouF1 = new ZipOutputStream(new FileOutputStream(path
							+ interface_id + File.separator + ind_file_name));
					InterfacecreationAll(ind_interface_id, path + interface_id
							+ File.separator, size, ouF1, ind_file_name);
					pathvector.addElement(path + interface_id + File.separator
							+ ind_file_name);
					pathvector1.addElement(interface_id + File.separator
							+ ind_file_name);
				}
			} catch (Exception a) {
				a.printStackTrace();
			}
			/************************ ZIP ALL CONTENT *************************/
			makeZip(name, ouF, pathvector1, pathvector, size);

		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}

	public void makeZip(String name, ZipOutputStream ouF, Vector strUnitId,
			Vector dst_path, int size) {
		byte[] buffer = new byte[18024];

		// System.out.println("................strUnitId............:"+strUnitId);
		// System.out.println("................dst_path............:"+dst_path);
		try {
			for (int i = 0; i < strUnitId.size(); i++) {
				ouF.setLevel(Deflater.DEFAULT_COMPRESSION);
				FileInputStream in = new FileInputStream(
						(String) dst_path.elementAt(i));
				ouF.putNextEntry(new ZipEntry((String) strUnitId.elementAt(i)));
				int len;
				while ((len = in.read(buffer)) > 0) {
					ouF.write(buffer, 0, len);
				}
				ouF.closeEntry();
				in.close();
			}
			ouF.close();
		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception a) {
			a.printStackTrace();
		}
	}

	public void makeZip2(String name, ZipOutputStream ouF, Vector strUnitId,
			Vector dst_path, int size) {
		byte[] buffer = new byte[18024];
		try {
			for (int i = 0; i < strUnitId.size(); i++) {
				ouF.setLevel(Deflater.DEFAULT_COMPRESSION);
				FileInputStream in = new FileInputStream(
						(String) dst_path.elementAt(i));
				ouF.putNextEntry(new ZipEntry((String) strUnitId.elementAt(i)));
				int len;
				while ((len = in.read(buffer)) > 0) {
					ouF.write(buffer, 0, len);
				}
				ouF.closeEntry();
				in.close();
			}
			// ouF.close();
		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception a) {
			a.printStackTrace();
		}
	}

	public void Interfacecreation(String interface_id, String path, int size,
			ZipOutputStream ouF, String name) {
		InputStream in;
		Vector resourcefile = DataBaseLayer.getModuleSrc(interface_id);
		Vector pathvector = new Vector();
		Vector pathvector1 = new Vector();
		byte[] buffer = new byte[18024];

		try {
			for (int i = 0; i < resourcefile.size(); i++) {
				Vector vModule = (Vector) resourcefile.elementAt(i);
				in = (InputStream) vModule.elementAt(1);
				String file_name = (String) vModule.elementAt(0);
				File dst = new File(path + interface_id + File.separator
						+ file_name);
				OutputStream resourceout = new FileOutputStream(dst);
				pathvector.addElement(path + interface_id + File.separator
						+ file_name);
				pathvector1.addElement(interface_id + File.separator
						+ file_name);
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					resourceout.write(buf, 0, len);
				}
				in.close();
				resourceout.close();
			}
		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		makeZip(name, ouF, pathvector1, pathvector, size);

	}

	public void InterfacecreationAll(String interface_id, String path,
			int size, ZipOutputStream ouF, String name) {
		InputStream in;
		Vector resourcefile = DataBaseLayer.getModuleSrc(interface_id);
		Vector pathvector = new Vector();
		Vector pathvector1 = new Vector();
		byte[] buffer = new byte[18024];

		try {
			for (int i = 0; i < resourcefile.size(); i++) {
				Vector vModule = (Vector) resourcefile.elementAt(i);
				in = (InputStream) vModule.elementAt(1);
				String file_name = (String) vModule.elementAt(0);
				File dst = new File(path + interface_id + File.separator
						+ file_name);
				OutputStream resourceout = new FileOutputStream(dst);
				pathvector.addElement(path + interface_id + File.separator
						+ file_name);
				pathvector1.addElement(interface_id + File.separator
						+ file_name);
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					resourceout.write(buf, 0, len);
				}
				in.close();
				resourceout.close();
				// System.out.println(".................pathvector1........"+pathvector1);
				// System.out.println(".................pathvector........"+pathvector);

			}
			makeZip2(name, ouF, pathvector1, pathvector, size);
			ouF.close();

		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	private static GenericDto createRoleXml() {
		RoleContainer roleContainer = new RoleContainer();
		List<Role> roleList = InterfaceRoleDao.getDistinctRoleList();
		for (Role role : roleList) {
			List<InterfaceElement> interfaceElementList = InterfaceRoleDao
					.getRoleInterfaceDetails(role);
			role.setInterfaceElementList(interfaceElementList);
		}
		roleContainer.setRoleList(roleList);

		return roleContainer;
	}
	
	private static GenericDto createManifestXml() {
		String manifestId = ManifestDao.getManifestId();
		ManifestContainer manifestContainer = new ManifestContainer(manifestId,
				manifestId);

		manifestContainer.setInterfaceElements(ManifestDao
				.getManifestDetails(manifestId));
		return manifestContainer;
	}
}