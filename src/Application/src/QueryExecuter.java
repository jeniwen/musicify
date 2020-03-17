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
}
