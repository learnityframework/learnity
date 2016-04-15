package comv2.aunwesha.lfutil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public final class FileUtil {

	private FileUtil() {

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

	public static void downlaodFile(byte[] xmlByteArray, String fileName, HttpServletResponse res) throws IOException {
		InputStream in = new ByteArrayInputStream(xmlByteArray);
		downlaodFile(in, fileName, res);
	}

	public static void downlaodFile(InputStream in, String fileName, HttpServletResponse res) throws IOException {
		res.setContentType("application/download");
		res.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

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

	public static void delete(File file) throws IOException {

		if (file.isDirectory()) {
			if (file.list().length == 0) {
				file.delete();
			} else {
				String files[] = file.list();
				for (String temp : files) {
					File fileDelete = new File(file, temp);
					delete(fileDelete);
				}
				if (file.list().length == 0) {
					file.delete();
				}
			}

		} else {
			file.delete();
		}
	}

	public static void moveFile(String filePath, String destnFilePath) {
		InputStream inStream = null;
		OutputStream outStream = null;

		try {

			File afile = new File(filePath);
			File bfile = new File(destnFilePath);

			inStream = new FileInputStream(afile);
			outStream = new FileOutputStream(bfile);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0) {

				outStream.write(buffer, 0, length);

			}

			inStream.close();
			outStream.close();

			afile.delete();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getStringFromInputStream(InputStream is) {

		StringWriter writer = new StringWriter();
		String theString = null;
		try {
			IOUtils.copy(is, writer);
			theString = writer.toString();
			System.out.println(theString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return theString;

	}

}
