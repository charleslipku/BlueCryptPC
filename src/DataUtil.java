import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataUtil {

	public static void connectionDatabase() throws Exception {
		
		try {
			
			Connection connection = DriverManager.getConnection("jdbc:ucanaccess://credential.accdb","","");
			java.sql.Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from credential");
			while (resultSet.next()) {
				System.out.println(resultSet.getString(2));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean checkCredential(Credential credential) throws Exception {
		Connection connection = DriverManager.getConnection("jdbc:ucanaccess://credential.accdb","","");
		java.sql.Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select * from credential where DEVICE_KEY="+credential.device_key+"and USER_KEY="+credential.user_key);
		if (resultSet.next()) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public static int getCredentialIndex(Credential credential) throws Exception {
		Connection connection = DriverManager.getConnection("jdbc:ucanaccess://credential.accdb","","");
		java.sql.Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select id from credential where DEVICE_KEY="+credential.device_key+"and USER_KEY="+credential.user_key);
		if (resultSet.next()) {
			return resultSet.getInt(1);
		}
		else {
			return 0;
		}
	}
	
	public static String[] getFolderPathArray(Credential credential) throws Exception {
		
		Connection connection = DriverManager.getConnection("jdbc:ucanaccess://credential.accdb","","");
		java.sql.Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select FOLDER_DIRECTORY from credential where DEVICE_KEY="+credential.device_key+"and USER_KEY="+credential.user_key);
		String reString=null;
		if (resultSet.next()) {
			reString=resultSet.toString();
			String [] folderPath=reString.split(",");
			return folderPath;
		}
		else {
			return null;
		}
	}
	public static void inserCredential(Credential credential) throws Exception {
		Connection connection = DriverManager.getConnection("jdbc:ucanaccess://credential.accdb","","");
		PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement("insert into credential(DEVICE_KEY,USER_KEY) values("+credential.device_key+","+credential.user_key+")");
		preparedStatement.executeUpdate();
		preparedStatement.close();
	}
	
	
	
	
	public static void insertDirectory(Credential credential,String directory) throws Exception {
		Connection connection = DriverManager.getConnection("jdbc:ucanaccess://credential.accdb","","");
		PreparedStatement preparedStatement1 = (PreparedStatement)connection.prepareStatement("select FOLDER_DIRECTORY from credential where DEVICE_KEY="+credential.device_key+"and USER_KEY="+credential.user_key);
		ResultSet resultSet1=preparedStatement1.executeQuery();
		String orignalDir=null;
		if (resultSet1.next()) {
			orignalDir=resultSet1.toString();
		}
		preparedStatement1.close();
		String newDir=orignalDir+","+directory;
		PreparedStatement preparedStatement2 = (PreparedStatement)connection.prepareStatement("update credential(DEVICE_KEY,USER_KEY) set FOLDER_DIRECTORY="+newDir+"where DEVICE_KEY="+credential.device_key+"and USER_KEY="+credential.user_key);
		preparedStatement2.executeUpdate();
	}
}
