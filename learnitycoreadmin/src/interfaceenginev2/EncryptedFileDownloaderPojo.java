package interfaceenginev2;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.directwebremoting.io.FileTransfer;
import org.grlea.log.DebugLevel;
import org.grlea.log.SimpleLogger;
//import sun.misc.BASE64Decoder;



public class EncryptedFileDownloaderPojo 
{
	private static byte[] databyte = new byte[4000];
	private static String MimeType = "";
	private static String filename = "";
	public  static final SimpleLogger log = new SimpleLogger(EncryptedFileDownloaderPojo.class, true);// Create a SimpleLogger:
	
	private static String algorithm = "DESede";
	private static Key key = null;
	private static Cipher cipher = null;

	private static void setUp() throws Exception 
	{       
		key = KeyGenerator.getInstance(algorithm).generateKey();
		cipher = Cipher.getInstance(algorithm);
	}
	
	
	public EncryptedFileDownloaderPojo(InputStream is,String MType,String name)
	throws Exception {
		
		String strEncryptedData="";
		String strInputstream="aaa";
		int bytesread = 0;
		try
		{
		setUp();
		MimeType = MType;
		filename = name; 
		log.debug("********EncryptedFileDownloaderPojo*********111111*************");
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		//int bytesread = 0;
		byte[] data = new byte[4000]; 
		log.debug("********EncryptedFileDownloaderPojo*********22222*************");                                 
		while(true)
		{
			
				log.debug("********EncryptedFileDownloaderPojo*********33333*************");                     
				bytesread = is.read(data);
				if (bytesread == -1)
					break;
				buffer.write(data,0,bytesread);
				log.debug("********EncryptedFileDownloaderPojo*********44444*************");
				
		}
		
		 data = buffer.toByteArray();
		 databyte = encrypt(data);
		 String recovered=decrypt(databyte);
		 log.debug("*******recovered*************"+recovered);
		
		 //byte[] databyte1 = encrypt(is);
		 // encrypt(is);
		//databyte=databyte1;
 
		}
		catch (IOException e) {
			log.fatal("IOException in EncryptedFileDownloaderPojo");
			log.dbe(DebugLevel.L1_FATAL,e);           	
      		
		}
		catch (Exception e1) {
			log.fatal("Exception in EncryptedFileDownloaderPojo");
			log.dbe(DebugLevel.L1_FATAL,e1);           	
      		
		}
		returnFileFormat();
		
		
	
	}
	
	public static FileTransfer returnFileFormat()
	{
		return new FileTransfer(filename, MimeType, databyte);
	
	}
	private static byte[] encrypt(byte[] inputBytes)
			throws InvalidKeyException, 
	BadPaddingException,
	IllegalBlockSizeException {
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(inputBytes);
	}
	
	private static byte[] encrypt(String input)
			throws InvalidKeyException, 
	BadPaddingException,
	IllegalBlockSizeException {
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] inputBytes = input.getBytes();
		return cipher.doFinal(inputBytes);
	}
	
	private static String decrypt(byte[] encryptionBytes)
			throws InvalidKeyException, 
	BadPaddingException,
	IllegalBlockSizeException {
		String recovered=null;
		try{
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] recoveredBytes = 
					cipher.doFinal(encryptionBytes);
			recovered = new String(recoveredBytes);
		}
	 
		catch (Exception e1) {
			log.fatal("Exception in decrypt method");
			log.dbe(DebugLevel.L1_FATAL,e1);           	
      		
		}
		return recovered;
	}
	
	
	//private static byte[] encrypt(InputStream inFile) throws Exception
	private static void encrypt(InputStream inFile)throws Exception {  
        
		try{
           
		// File to encrypt.  It does not have to be a text file!
 
		//filename = "/temp/ObjectBank.ob";
 
       // Password must be at least 8 characters (bytes) long
 
		String password = "super_secret";
	        
		//FileInputStream inFile1 = new FileInputStream(filename);
		FileOutputStream outFile = new FileOutputStream("/temp/ObjectBankEcrypted.ob");
 
       // Use PBEKeySpec to create a key based on a password.
      // The password is passed as a character array
 
		PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
		SecretKeyFactory keyFactory =
				SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		SecretKey passwordKey = keyFactory.generateSecret(keySpec);
 
       // PBE = hashing + symmetric encryption.  A 64 bit random
       // number (the salt) is added to the password and hashed
       // using a Message Digest Algorithm (MD5 in this example.).
       // The number of times the password is hashed is determined
       // by the interation count.  Adding a random number and
       // hashing multiple times enlarges the key space.
 
		byte[] salt = new byte[8];
		Random rnd = new Random();
		rnd.nextBytes(salt);
		int iterations = 100;
 
       //Create the parameter spec for this salt and interation count
 
		PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, iterations);
 
       // Create the cipher and initialize it for encryption.
 
		Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
		cipher.init(Cipher.ENCRYPT_MODE, passwordKey, parameterSpec);

      // Need to write the salt to the (encrypted) file.  The
       // salt is needed when reconstructing the key for decryption.

		//outFile.write(salt);
 
       // Read the file and encrypt its bytes.

		byte[] input = new byte[4000];
		int bytesRead;
		while ((bytesRead = inFile.read(input)) != -1)
		{
			byte[] output = cipher.update(input, 0, bytesRead);
			if (output != null) outFile.write(output);
			log.debug("********while******loop**********");
		}
 
		byte[] output = cipher.doFinal();
		if (output != null) outFile.write(output);
		
		int ilength=output.length;
		log.debug("********output length******2**********"+ilength);
		
		inFile.close();
		outFile.flush();
		outFile.close();
		}
		
		catch (Exception e) {
			log.fatal("**************Exception:********************");	
			log.dbe(DebugLevel.L1_FATAL, e);
		}
		
		
		//return output;
		
		
 
	}
	

}