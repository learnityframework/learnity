package coreadministrationv2.sysmgmt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Title:        LearnITy Content Platform
 * Description:
 * Copyright:    Copyright (c) 2009
 * Company:      Aunwesha Knowledge Technologies Pvt. Ltd.
 * @author 	     Subir Bhaumik
 * @version      1.4
 */

public class FolderZiper {

	private ZipOutputStream zip = null;
	private FileOutputStream fileWriter = null;
	
    	public void zipFolder(String srcFolder, String destZipFile) {
		
		try {
			fileWriter = new FileOutputStream(destZipFile);
			zip = new ZipOutputStream(fileWriter);
			addFolderToZip("", srcFolder);
		}
		catch (Exception ex){
			System.out.println("Exception in zipFolder()");
			ex.printStackTrace();
		}
		finally{
			try{
				zip.flush();
				zip.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void zipFolder(String srcFolder, String fileList[], String destZipFile) {
		
		try {
			fileWriter = new FileOutputStream(destZipFile);
			zip = new ZipOutputStream(fileWriter);
			addFileListToZip(srcFolder, "", fileList);
		}
		catch (Exception ex){
			System.out.println("Exception in zipFolder()");
			ex.printStackTrace();
		}
		finally{
			try{
				zip.flush();
				zip.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void zipFolder(String srcFolder, String destZipFile, ArrayList exclude) {
		
		try {
			fileWriter = new FileOutputStream(destZipFile);
			zip = new ZipOutputStream(fileWriter);
			addFolderToZip("", srcFolder, exclude);
		}
		catch (Exception ex){
			System.out.println("Exception in zipFolder()");
			ex.printStackTrace();
		}
		finally{
			try{
				zip.flush();
				zip.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private void addToZip(String path, String srcFile) {
		
		File folder = new File(srcFile);
		if (folder.isDirectory()) {
			addFolderToZip(path, srcFile);
		}
		else {
			byte[] buf = new byte[1024];
			int len;
			FileInputStream in = null;
			try {
				in = new FileInputStream(srcFile);
				if(path.equals("")){
					zip.putNextEntry(new ZipEntry(folder.getName()));
				} else { 
					zip.putNextEntry(new ZipEntry(path +"/"+ folder.getName()));
				}
				while ((len = in.read(buf)) > 0) {
					zip.write(buf, 0, len);
				}
			}
			catch (Exception ex){
				System.out.println("Exception in addToZip()");
				ex.printStackTrace();
			}
			finally{
				try{
					in.close();
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	private void addFolderToZip(String path, String srcFolder) {
		
		File folder = new File(srcFolder);
		String fileListe[] = folder.list();
		try {
			for(int i=0; i<fileListe.length; i++) {
				File fileItem = new File(srcFolder+"/"+fileListe[i]);
				if(fileItem.isFile()){
					addToZip(path, srcFolder+"/"+fileListe[i]);
				}
				else{
					String  path1 = "";
					if(path.equals("")){
						path1 = fileListe[i];
					}
					else{
						path1 = path+"/"+fileListe[i];
					}
					addToZip(path1, srcFolder+"/"+fileListe[i]);
				}
			}
		}
		catch (Exception ex) {
			System.out.println("Exception in addFolderToZip()");
			ex.printStackTrace();
		}
	}
	
	private void addFileListToZip(String srcFolder, String path, String fileListe[]) {
		
		try {
			for(int i=0; i<fileListe.length; i++) {
				File fileItem = new File(srcFolder+"/"+fileListe[i]);
				String currentDir = "";
				int pos;
				int pos1 = fileListe[i].lastIndexOf(File.separatorChar);
				int pos2 = fileListe[i].lastIndexOf("/");
				int pos3 = fileListe[i].lastIndexOf("\\");
				if(pos1 != -1)
					pos = pos1;
				else if(pos2 != -1)
					pos = pos2;
				else if(pos3 != -1)
					pos = pos3;
				else 
					pos = -1;
				
				if(pos != -1) {
					currentDir = fileListe[i].substring(0, pos);
				}
				
				if(currentDir.equals("")){
					addToZip(path, srcFolder+"/"+fileListe[i]);
				}
				else{
					String  path1 = "";
					if(path.equals("")){
						path1 = currentDir;
					}
					else{
						path1 = path+ "/" + currentDir;
					}
					addToZip(path1, srcFolder+"/"+fileListe[i]);
				}
			}
		}
		catch (Exception ex) {
			System.out.println("Exception in addFileListToZip()");
			ex.printStackTrace();
		}
	}
	
	private void addFolderToZip(String path, String srcFolder, ArrayList exclude) {
		
		File folder = new File(srcFolder);
		String fileListe[] = folder.list();
		try {
			for(int i=0; i<fileListe.length; i++) {
				
				File fileItem = new File(srcFolder+"/"+fileListe[i]);
				if(fileItem.isFile()){
					
					addToZip(path, srcFolder+"/"+fileListe[i]);
				}
				else{
					String  path1 = "";
					if(path.equals("")){
						path1 = fileListe[i];
					}
					else{
						path1 = path+"/"+fileListe[i];
					}
					addToZip(path1, srcFolder+"/"+fileListe[i]);
				}
			}
		}
		catch (Exception ex) {
			System.out.println("Exception in addFolderToZip()");
			ex.printStackTrace();
		}
	}
	
	public void unZip(String inFile, String destDir) {
		
		try {
			
			File sourceZipFile = new File(inFile);
			File unzipDestinationDirectory = new File(destDir);
			ZipFile zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);
			Enumeration zipFileEntries = zipFile.entries();
			
			while (zipFileEntries.hasMoreElements()) {
		         
				ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
				String currentEntry = entry.getName();
				File destFile = new File(unzipDestinationDirectory, currentEntry);
		          	File destinationParent = destFile.getParentFile();
		         	destinationParent.mkdirs();
		         	if (!entry.isDirectory()) {
					BufferedInputStream is = new BufferedInputStream(zipFile.getInputStream(entry));
					int currentByte;
					int BUFFER = 2048;
			           	byte data[] = new byte[BUFFER];
			           	FileOutputStream fos = new FileOutputStream(destFile);
					BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
			           	while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
						dest.write(data, 0, currentByte);
					}
					dest.flush();
					dest.close();
					is.close();
				}
			}
			zipFile.close();
		}
		catch (Exception ioe) {
			ioe.printStackTrace();
		}
	}
	
	public ArrayList unZipEntries(String inFile) {
		
		ArrayList entries = null;
		try {
			entries = new ArrayList();
			File sourceZipFile = new File(inFile);
			ZipFile zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);
			Enumeration zipFileEntries = zipFile.entries();
			
			while (zipFileEntries.hasMoreElements()) {
		         
				ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
				String currentEntry = entry.getName();
				if (!entry.isDirectory())
					entries.add(currentEntry);
			}
			zipFile.close();
		}
		catch (Exception ioe) {
			ioe.printStackTrace();
		}
		return entries;
	}
	
	public ArrayList unZipEntries(File sourceZipFile) {
		
		ArrayList entries = null;
		try {
			entries = new ArrayList();
			ZipFile zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);
			Enumeration zipFileEntries = zipFile.entries();
			
			while (zipFileEntries.hasMoreElements()) {
		         
				ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
				String currentEntry = entry.getName();
				if (!entry.isDirectory())
					entries.add(currentEntry);
			}
			zipFile.close();
		}
		catch (Exception ioe) {
			ioe.printStackTrace();
		}
		return entries;
	}
	
	/**
	* Closes this file input stream and releases any system resources
	* associated with the stream.
	*
	* @exception  IOException  if an I/O error occurs.
	*
	* @revised 1.4
	*/
	public void close() throws IOException {
		if (zip != null)
			zip.close();
	}
}