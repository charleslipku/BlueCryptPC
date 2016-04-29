import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.microedition.io.StreamConnection;

/**
 * 
 */

/**
 * @author charles
 *
 */
public class ReadThread extends Thread{

	StreamConnection streamConnection;
	volatile String readMessage;
	private static Credential credential = new Credential();
	
	
	
	public ReadThread(StreamConnection streamConnection, String readMessage) {
		this.streamConnection = streamConnection;
		this.readMessage=readMessage;
	}

	public static byte[] readStream(InputStream inStream) throws Exception {  
	    ByteArrayOutputStream outSteam = new ByteArrayOutputStream();  
	    byte[] buffer = new byte[1024];  
	    int len = -1;  
	    while ((len = inStream.read(buffer)) != -1) {  
	        outSteam.write(buffer, 0, len);  
	    }  
	    outSteam.close();  
	    inStream.close();  
	    return outSteam.toByteArray();  
	}  


	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			InputStream inputStream=streamConnection.openInputStream();
			while(true){
			
			DataInputStream dataInputStream=new DataInputStream(inputStream);
			byte [] buffer=new byte[dataInputStream.available()];
			if (buffer.length!=0) {
				dataInputStream.readFully(buffer);
				readMessage=new String(buffer);
				System.out.println(readMessage);
				WriteThread writeThread=new WriteThread("Connection Success!", streamConnection);
				writeThread.start();
				
				JSONUtil.parseJSON(readMessage, credential);
				System.out.println("DEVICE_KEY:"+credential.device_key);
				System.out.println("USER_KEY:"+credential.user_key);
				String response_success = JSONUtil.generateJSON("Login Success");
				String response_fail = JSONUtil.generateJSON("Fail, you are a new user!");
				if (DataUtil.checkCredential(credential)) {
					writeThread.sendMessage(response_success);
					System.out.println("Index:"+DataUtil.getCredentialIndex(credential));
					String [] folderPath = DataUtil.getFolderPathArray(credential);
					
//					CryptUtil cryptUtil = new CryptUtil(folderPath, "C:\\Users\\charles\\Desktop\\keyfolder\\", "zx9956123", credential);
//					cryptUtil.generateKey();
//					cryptUtil.encryptFile();
					
					CryptUtil cryptUtil=new CryptUtil(folderPath, "C:\\Users\\charles\\Desktop\\keyfolder\\", "zx9956123",credential);
					cryptUtil.loadKey();
					cryptUtil.decryptionFile();
				}else {
					writeThread.sendMessage(response_fail);
					DataUtil.inserCredential(credential);
				}
				
			}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
