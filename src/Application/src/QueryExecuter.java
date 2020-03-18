package Application.src;
import java.sql.*;

public class QueryExecuter 
{
	private static QueryExecuter INSTANCE = new QueryExecuter();
	private static Connection connection;
	
	private QueryExecuter() {}
	
	public static QueryExecuter instance()
	{
		return INSTANCE;
	}
	
	public static void setConnnection(Connection connection)
	{
		QueryExecuter.connection = connection;
	}
	
	public String getUserPassword(String username)
	{
		String result = "ERROR";
		
		// create the statement to fetch the username's password
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT b_user.password "
					+ "FROM b_user "
					+ "WHERE b_user.username = \'" + username + "\'";

			ResultSet rs = stmt.executeQuery(query);

			while(rs.next())
			{
				result = rs.getString(1);
			}
			if(result.equals(""))
			{
				throw new SQLException("There is no such username."); 
			}
					
		}catch (SQLException e)
		{
			//result = "ERROR IN PASSWORD";
			//e.printStackTrace();
		}
		
		return result;
	}
	
	public ResultSet executeQuery(String querySQL) {
		int sqlCode;
		String sqlState;
		try {
			Statement stmt = connection.createStatement();
			java.sql.ResultSet rs = stmt.executeQuery(querySQL);
			return rs;
		} catch (SQLException e) {
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE
			System.out.println("Problem with query: " + querySQL);
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
		return null;
	}

	public ResultSet getUser(String username) {
		return executeQuery("SELECT * from b_user WHERE b_user.username = \'" + username + "\'");
	}
	
	public ResultSet getPlaylistsOfUser(String useremail) {
		return executeQuery("SELECT * from Playlist WHERE email = \'" + useremail + "\'");
	}
	
	public ResultSet getAudiofile(int audiofileID) {
		return executeQuery("SELECT * from Audiofile WHERE audiofile_id = " + audiofileID);
	}
	
	public ResultSet getSearch(String tablename, String attribute, String searchString) {
		return executeQuery("SELECT * from " + tablename + " WHERE " + attribute + " LIKE \'%" + searchString + "%\'");
	}
	
}
