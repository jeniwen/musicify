package Application.src;
import java.sql.*;

public class ConnectionManager 
{
	// implements the singleton design pattern
	private static ConnectionManager INSTANCE = new ConnectionManager();
	private static String id = "cs421g11";
	private static String password = "Spotify124!";
	private static String url = "jdbc:db2://comp421.cs.mcgill.ca:50000/cs421";
	private Connection connection;
	
	private ConnectionManager() {}
	
	public static ConnectionManager instance()
	{
		return INSTANCE;
	}
	
	public void createConnection()
	{
		try {
			DriverManager.registerDriver(new com.ibm.db2.jcc.DB2Driver());
		} catch (Exception cnfe) {
			System.out.println("Class not found");
		}
		try {
			connection = DriverManager.getConnection(url, id, password);
			
		} catch (Exception e) {
			System.out.println("Could not connect");
			e.printStackTrace();
		}
	}
	
	public void closeConnection()
	{
		try {
			connection.close();
		}catch (SQLException e)
		{
			System.out.println("Could not close connection");
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
}
