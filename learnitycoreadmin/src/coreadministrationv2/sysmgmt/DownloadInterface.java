package coreadministrationv2.sysmgmt;

/**
 * Title:        Learnity Interface Management   
 * Description:
 * Copyright:    Copyright (c) 2008
 * Company:      Aunwesha
 * @author Shibaji Chatterjee
 */




import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.ecs.*;
import org.apache.ecs.html.*;
import comv2.aunwesha.param.*;
import comv2.aunwesha.JSPGrid.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import com.oreilly.servlet.MultipartRequest;
import java.text.*;
import java.util.Vector;
import java.util.Random;
import java.io.*;
import java.net.*;
import  org.w3c.dom.Document;
import org.apache.xerces.parsers.DOMParser;
import java.util.zip.*;
import  org.w3c.dom.Element;
import coreadministrationv2.dbconnection.DataBaseLayer;
import coreadministrationv2.utility.*;
import org.apache.xerces.dom.*;
import org.apache.xml.serialize.*;

public class DownloadInterface extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws IOException, ServletException {
        String strFileType = "";
        InputStream in;
       
        String interface_id = req.getParameter("interface_id");
		  
        String filename = req.getParameter("filename");
        String type=req.getParameter("type");
		  System.out.println("....interface_id..........."+interface_id+"......type...."+type+"......file..."+filename);
         if(type.equals("RoleXML"))
 			{
				Vector vModule=DataBaseLayer.getFramework_FileDetails(interface_id,filename);
				in = (InputStream)vModule.elementAt(0);
			
			
						res.setContentType ("application/download");
						res.setHeader ("Content-Disposition", "attachment; filename=\""+filename+"\"");
						ServletOutputStream out = res.getOutputStream();
						int len = 0;
						byte buffer[]= new byte[1024];
						try {
							while ((in != null) && ((len = in.read(buffer)) != -1)) {
								out.write(buffer,0,len);
							}
						}
						finally {
							if (in != null) in.close();
						}
	      }	
 
	   	else if(type.equals("Manifest"))
 			{
						Vector vModule=DataBaseLayer.getFramework_FileDetails(interface_id,filename);
						in = (InputStream)vModule.elementAt(0);
					
					
								res.setContentType ("application/download");
								res.setHeader ("Content-Disposition", "attachment; filename=\""+filename+"\"");
								ServletOutputStream out = res.getOutputStream();
								int len = 0;
								byte buffer[]= new byte[1024];
								try {
									while ((in != null) && ((len = in.read(buffer)) != -1)) {
										out.write(buffer,0,len);
									}
								}
								finally {
									if (in != null) in.close();
								}
			}
			
			else if(type.equals("Interface"))
			{
				int size = 1;
				String name = interface_id+".zip";
				String xmlname="manifest.xml";
				res.setContentType ("application/zip");
				res.setHeader ("Content-Disposition", "attachment; filename=\""+name+"\"");
				String path="";
				ZipOutputStream ouF;
				String folderpath="";
				ResourceBundle rb = ResourceBundle.getBundle("portal",Locale.getDefault());      
				String key1= "downloadzip"; 
				path = rb.getString(key1);
				File ff=new File(path+interface_id);
				ff.mkdir();
				ServletOutputStream out = res.getOutputStream(); 
				ouF = new ZipOutputStream(out);  
				Interfacecreation(interface_id,path, size,ouF, name);
			}
			
			else if(type.equals("InterfaceFragment"))
			{
				int size = 1;
				String name = interface_id+".zip";
				String xmlname="manifest.xml";
				res.setContentType ("application/zip");
				res.setHeader ("Content-Disposition", "attachment; filename=\""+name+"\"");
				String path="";
				ZipOutputStream ouF;
				String folderpath="";
				ResourceBundle rb = ResourceBundle.getBundle("portal",Locale.getDefault());      
				String key1= "downloadzip"; 
				path = rb.getString(key1);
				File ff=new File(path+interface_id);
				ff.mkdir();
				ServletOutputStream out = res.getOutputStream(); 
				ouF = new ZipOutputStream(out);  
				Interfacecreation(interface_id,path, size,ouF, name);
			}
	else
		   {
				int size = 1;
				ZipOutputStream ouF;
				Vector pathvector=new Vector();
				Vector pathvector1=new Vector();
				String name = interface_id+".zip";
				String xmlname="manifest.xml";
				res.setContentType ("application/zip");
				res.setHeader ("Content-Disposition", "attachment; filename=\""+name+"\"");
				//res.setHeader ("Content-Disposition", "attachment; filename=\"LearnityInterface\"");

				String path="";
				String folderpath="";
				ResourceBundle rb = ResourceBundle.getBundle("portal",Locale.getDefault());      
				String key1= "downloadzip"; 
				path = rb.getString(key1);
				File ff=new File(path+interface_id);
				ff.mkdir();
				ServletOutputStream out = res.getOutputStream(); 
				ouF = new ZipOutputStream(out);  
				
				/****************************** FOR INTERFACEROLE*********************/
				Vector rolefile=DataBaseLayer.getInterfaceRoleFile();		
				//byte[] buffer = new byte[18024];
				try {
					for (int i = 0; i < rolefile.size(); i++) {
						         Vector vModule = (Vector)rolefile.elementAt(i);
									in =(InputStream)vModule.elementAt(1);
									String file_name=(String)vModule.elementAt(0);
									File dst = new File(path+interface_id+File.separator+file_name);
									OutputStream resourceout = new FileOutputStream(dst);
									pathvector.addElement(path+interface_id+File.separator+file_name);
									pathvector1.addElement(interface_id+File.separator+file_name);
									byte[] buf = new byte[1024];
									int len;
									while ((len = in.read(buf)) > 0) {
										resourceout.write(buf, 0, len);
									}
									in.close();
									resourceout.close();
							}
				}
				catch (IllegalArgumentException iae) {
					iae.printStackTrace();
				}
				catch (FileNotFoundException fnfe) {
					fnfe.printStackTrace();
				}
				/****************************** FOR MANIFEST*********************/
				Vector manifestfile=DataBaseLayer.getManifestFile();		
				//byte[] buffer = new byte[18024];
				try {
					     for (int i = 0; i < manifestfile.size(); i++) {
						      Vector vModule = (Vector)manifestfile.elementAt(i);
								in =(InputStream)vModule.elementAt(1);
								String file_name=(String)vModule.elementAt(0);
								File dst = new File(path+interface_id+File.separator+file_name);
								OutputStream resourceout = new FileOutputStream(dst);
								pathvector.addElement(path+interface_id+File.separator+file_name);
								pathvector1.addElement(interface_id+File.separator+file_name);
								byte[] buf1 = new byte[1024];
								int len;
								while ((len = in.read(buf1)) > 0) {
									resourceout.write(buf1, 0, len);
								}
								in.close();
								resourceout.close();
							}
				}
				catch (IllegalArgumentException iae) {
					iae.printStackTrace();
				}
				catch (FileNotFoundException fnfe) {
					fnfe.printStackTrace();
				}
				/*************************ALL INTERFACE ZIP***********************/
						
				Vector interfacefile=DataBaseLayer.getAllInterFaceIDUnderCollection(interface_id);			
				   try{
					         for (int i = 0; i < interfacefile.size(); i=i+2) {
								String ind_interface_id=(String)interfacefile.elementAt(i);
								String ind_file_name=(String)interfacefile.elementAt(i+1);
								File finterface=new File(path+interface_id+File.separator+ind_interface_id);
								finterface.mkdir();
								ZipOutputStream ouF1;
								ouF1 = new ZipOutputStream(new FileOutputStream(path+interface_id+File.separator+ind_file_name));
								InterfacecreationAll(ind_interface_id,path+interface_id+File.separator,size,ouF1,ind_file_name);
								pathvector.addElement(path+interface_id+File.separator+ind_file_name);
								pathvector1.addElement(interface_id+File.separator+ind_file_name);
							   }
				      }		
						catch (Exception a) {
							a.printStackTrace();
						}
				/************************ ZIP ALL CONTENT*************************/
						makeZip(name,ouF,pathvector1,pathvector,size);
				
		   }
    }

    
	public void doPost(HttpServletRequest request, HttpServletResponse response)
        	throws IOException, ServletException {
        doGet(request, response);
    }
 
 public void makeZip(String name,ZipOutputStream ouF,Vector strUnitId,Vector dst_path,int size) {
       	byte[] buffer = new byte[18024];
			
			//System.out.println("................strUnitId............:"+strUnitId);
			//System.out.println("................dst_path............:"+dst_path);
     	try {
     		for (int i = 0; i < strUnitId.size(); i++) {
					ouF.setLevel(Deflater.DEFAULT_COMPRESSION);
					FileInputStream in = new FileInputStream((String)dst_path.elementAt(i));
					ouF.putNextEntry(new ZipEntry((String)strUnitId.elementAt(i)));
					int len;
							while ((len = in.read(buffer)) > 0) 
							{
								ouF.write(buffer, 0, len);
							}
							ouF.closeEntry();
							in.close();
					}
					ouF.close();
		} 
		catch (IllegalArgumentException iae) {
		iae.printStackTrace();
		}
		catch (FileNotFoundException fnfe) {
		fnfe.printStackTrace();
		}
		catch (IOException ioe)
		{
		ioe.printStackTrace();
		}
		catch (Exception a)
		{
			a.printStackTrace();
		}
   }  
	
	
	public void makeZip2(String name,ZipOutputStream ouF,Vector strUnitId,Vector dst_path,int size) {
		byte[] buffer = new byte[18024];
			try {
						for (int i = 0; i < strUnitId.size(); i++) {
							ouF.setLevel(Deflater.DEFAULT_COMPRESSION);
							FileInputStream in = new FileInputStream((String)dst_path.elementAt(i));
							ouF.putNextEntry(new ZipEntry((String)strUnitId.elementAt(i)));
							int len;
							while ((len = in.read(buffer)) > 0) 
							{
								ouF.write(buffer, 0, len);
							}
							ouF.closeEntry();
							in.close();
						}
						//ouF.close();
		       } 
				catch (IllegalArgumentException iae) {
					iae.printStackTrace();
				}
				catch (FileNotFoundException fnfe) {
					fnfe.printStackTrace();
				}
				catch (IOException ioe)
				{
					ioe.printStackTrace();
				}
				catch (Exception a)
				{
					a.printStackTrace();
				}
	}  

public  void Interfacecreation (String interface_id,String path,int size,ZipOutputStream ouF,String name)
{
                    InputStream in;
		               Vector resourcefile=DataBaseLayer.getModuleSrc(interface_id);
							Vector pathvector=new Vector();
							Vector pathvector1=new Vector();
							byte[] buffer = new byte[18024];
							
							try {
								for (int i = 0; i < resourcefile.size(); i++) {
											Vector vModule = (Vector)resourcefile.elementAt(i);
											in =(InputStream)vModule.elementAt(1);
											String file_name=(String)vModule.elementAt(0);
											File dst = new File(path+interface_id+File.separator+file_name);
											OutputStream resourceout = new FileOutputStream(dst);
											pathvector.addElement(path+interface_id+File.separator+file_name);
											pathvector1.addElement(interface_id+File.separator+file_name);
											byte[] buf = new byte[1024];
											int len;
											while ((len = in.read(buf)) > 0) {
											resourceout.write(buf, 0, len);
											}
											in.close();
											resourceout.close();
										}
								}
						catch (IllegalArgumentException iae) {
						iae.printStackTrace();
						}
						catch (FileNotFoundException fnfe) {
						fnfe.printStackTrace();
						}
						catch (IOException ioe)
						{
						ioe.printStackTrace();
						}
						makeZip(name,ouF,pathvector1,pathvector,size);

 }

 public  void InterfacecreationAll(String interface_id,String path,int size,ZipOutputStream ouF,String name)
 {
	 InputStream in;
	 Vector resourcefile=DataBaseLayer.getModuleSrc(interface_id);
	 Vector pathvector=new Vector();
	 Vector pathvector1=new Vector();
	 byte[] buffer = new byte[18024];
							
	 try {
				for (int i = 0; i < resourcefile.size(); i++) {
					Vector vModule = (Vector)resourcefile.elementAt(i);
					in =(InputStream)vModule.elementAt(1);
					String file_name=(String)vModule.elementAt(0);
					File dst = new File(path+interface_id+File.separator+file_name);
					OutputStream resourceout = new FileOutputStream(dst);
					pathvector.addElement(path+interface_id+File.separator+file_name);
					pathvector1.addElement(interface_id+File.separator+file_name);
					byte[] buf = new byte[1024];
					int len;
					while ((len = in.read(buf)) > 0) {
						resourceout.write(buf, 0, len);
					}
					in.close();
					resourceout.close();
					//System.out.println(".................pathvector1........"+pathvector1);
					//System.out.println(".................pathvector........"+pathvector);
					
					}
					makeZip2(name,ouF,pathvector1,pathvector,size);
					ouF.close();

	 }
	 catch (IllegalArgumentException iae) {
		 iae.printStackTrace();
	 }
	 catch (FileNotFoundException fnfe) {
		 fnfe.printStackTrace();
	 }
	 catch (IOException ioe)
	 {
		 ioe.printStackTrace();
	 }

 }
    
}