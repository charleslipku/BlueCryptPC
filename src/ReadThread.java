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
	String readMessage;
	
	
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
			
			}
			}
			/*InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
			BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
			readMessage=bufferedReader.readLine();*/
			
			/*while (readMessage!=null) {
				
				System.out.println(readMessage);
				readMessage=bufferedReader.readLine();
				
			}*/
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
