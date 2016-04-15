package interfaceenginev2;
import java.io.File;
import java.io.InputStream;

import org.directwebremoting.io.FileTransfer;
/*import learnityasmtserver.assessmentportal.dbconnection.AsmtPortalDataBaseLayer;
import oracle.xml.parser.v2.*;
import org.grlea.log.*;
import java.util.Locale;
import org.w3c.dom.*;
import com.oreilly.servlet.MultipartRequest;
import java.util.ResourceBundle;
import java.util.Properties;
import java.text.*;
import java.net.*;*/

public class FileUploaderPojo 
{
	private static FileTransfer filepathname = null;
	public FileUploaderPojo(FileTransfer file)
	{
		filepathname = file;
		System.out.println("==============filepathname= in constructor======"+filepathname);
		getMimeType();
		getFilename();
		getInputStream();
	
	
	}
	
	public static String getMimeType()
	{
		FileTransfer file = filepathname;
		System.out.println("==============filepathname======="+filepathname);
		String MimeType="";
		try 
		{
			MimeType = file.getMimeType();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return MimeType;
	
	}
	
	public static String getFilename()
	{
		FileTransfer file = filepathname;
		String name="";
		try 
		{
			String dir = file.getFilename();
			int index = dir.lastIndexOf("\\");
			if(index != -1){
				name = dir.substring(index+1);
				System.out.println("===File Name Using \\ ===:  "+name);
			} else {
				name = dir.substring(dir.lastIndexOf(File.separatorChar)+1);
				System.out.println("===File Name Using File.separatorChar===:  "+name);
			}
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return name;
	
	}
	
	public static InputStream getInputStream()
	{
		FileTransfer file = filepathname;
		InputStream fin=null;
		try 
		{
			fin = file.getInputStream();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return fin;
	
	}
	
/*try {
	InputStream fin =  file.getInputStream();
          
	BufferedReader myInput = new BufferedReader
	(new InputStreamReader(fin));
	while ((thisLine = myInput.readLine()) != null) {  
	System.out.println(thisLine);
}
}
	catch (Exception e) {
	e.printStackTrace();
}*/	
	
	
}