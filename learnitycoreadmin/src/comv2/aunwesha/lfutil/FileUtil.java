package comv2.aunwesha.lfutil;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public final class FileUtil {

	private FileUtil(){

	}
	public static String createFile(byte[] bFile, String path) {
		FileOutputStream fileOuputStream = null;
		try {
			fileOuputStream = new FileOutputStream(path);
			fileOuputStream.write(bFile);
			fileOuputStream.close();

			System.out.println("Done");
		} catch (Exception e) {
			path = null;
			e.printStackTrace();
		} finally {
			if (fileOuputStream != null) {
				try {
					fileOuputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return path;
	}

	public static void downlaodFile(byte[] xmlByteArray,String fileName,HttpServletResponse res) throws IOException{
		InputStream in = new ByteArrayInputStream(xmlByteArray);
		downlaodFile(in, fileName, res);
	}

	public static void downlaodFile(InputStream in,String fileName,HttpServletResponse res) throws IOException{
		res.setContentType("application/download");
		res.setHeader("Content-Disposition", "attachment; filename=\""
				+ fileName + "\"");

		ServletOutputStream out = res.getOutputStream();
		int len = 0;
		byte buffer[] = new byte[1024];
		try {
			while ((in != null) && ((len = in.read(buffer)) != -1)) {
				out.write(buffer, 0, len);
			}
		} finally {
			if (in != null)
				in.close();
		}
	}

}
