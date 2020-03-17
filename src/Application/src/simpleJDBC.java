package Application.src;
import java.sql.*;

public class simpleJDBC {
	private static String id = "cs421g11";
	private static String password = "Spotify124!";
	private static String url = "jdbc:db2://comp421.cs.mcgill.ca:50000/cs421";

	public simpleJDBC() throws SQLException {
		
		//Eclipse: Make sure db2jcc4.jar is in class path
		//Right click Application > Properties > Libraries > Classpath > Add External jars
		

		try {
			DriverManager.registerDriver(new com.ibm.db2.jcc.DB2Driver());
			System.out.println("Successfully registered db2jcc driver.");
		} catch (Exception cnfe) {
			System.out.println("Class not found");
		}
		
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, id, password);
			System.out.println("Created connection");
		} catch (Exception e) {
			System.out.println("Could not connect");
			e.printStackTrace();
		}

		Statement statement = con.createStatement();
		// Querying a table
		int sqlCode=0;
		String sqlState="00000";
		try {
			String querySQL = "SELECT * FROM Podcast FETCH FIRST 5 ROWS ONLY";
			System.out.println(querySQL);
			java.sql.ResultSet rs = statement.executeQuery(querySQL);
			while (rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				System.out.println("podname:  " + id);
				System.out.println("description:  " + name);
			}
			System.out.println("DONE");
		} catch (SQLException e) {
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
		statement.close();
		con.close();
		System.out.println("Connection closed.");
		
	}
}
