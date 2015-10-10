package interfaceenginev2;
import java.io.*;
import java.lang.Exception;

import javax.servlet.ServletException;
import java.util.*;
import org.directwebremoting.ScriptBuffer ;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.ScriptSession ;
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

public class FileDownloaderPojo 
{
	private static byte[] databyte = new byte[4000];
	private static String MimeType = "";
	private static String filename = "";
	public FileDownloaderPojo(InputStream is,String MType,String name)
	{
		MimeType = MType;
		filename = name; 
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int bytesread = 0;
		byte[] data = new byte[4000];                                  
		while(true)
		{
			try
			{
				bytesread = is.read(data);
				if (bytesread == -1)
					break;
				buffer.write(data,0,bytesread);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		data = buffer.toByteArray();
		databyte = data;
		//returnFileFormat();
	
	}
	
	public static FileTransfer returnFileFormat()
	{
		return new FileTransfer(filename, MimeType, databyte);
	
	}
	
	
	
}