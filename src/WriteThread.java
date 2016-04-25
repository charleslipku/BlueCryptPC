import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.microedition.io.StreamConnection;

public class WriteThread extends Thread{

	String response;
	StreamConnection streamConnection;
	
	public WriteThread(String response, StreamConnection streamConnection) {
		
		this.response = response;
		this.streamConnection = streamConnection;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		 
		try {
			OutputStream outputStream = streamConnection.openOutputStream();
			PrintWriter printWriter=new PrintWriter(new OutputStreamWriter(outputStream));
			printWriter.write(response);
			printWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	
	
}
