package Application.src;
import java.sql.*;
import java.util.ArrayList;

import Application.src.Structures.Playlist;

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

	public static ProfileInfo getBasicUserInfo(ProfileInfo currentUser) 
	{
		// obtain fullName, email, subscriptionNumber
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT b_user.full_name, b_user.email, b_user.subscription_no "
					+ "FROM b_user "
					+ "WHERE b_user.username = \'" + currentUser.username + "\'";

			ResultSet rs = stmt.executeQuery(query);

			while(rs.next())
			{
				currentUser.fullName = rs.getString(1);
				currentUser.email = rs.getString(2);
				currentUser.subscriptionNo = rs.getString(3);
			}
			
					
		}catch (SQLException e)
		{
			System.out.println("Error fetching profile information.");
		}
		
		
		
		
		return currentUser;
	}

	public static ProfileInfo getAllPlaylistNamesForUser(ProfileInfo currentUser) 
	{
		
		try {
			Statement stmt = connection.createStatement();
			String query = "SELECT Playlist.playlist_name "
					+ "FROM Playlist "
					+ "WHERE Playlist.email = \'" + currentUser.email + "\'";

			ResultSet rs = stmt.executeQuery(query);

			while(rs.next())
			{
				currentUser.playlistNames.add(rs.getString(1));
			}
			
					
		}catch (SQLException e)
		{
			System.out.println("Error fetching profile information.");
		}
		
		
		return currentUser;
		
		
	}
	
	public ResultSet executeQuery(String querySQL) {
		int sqlCode;
		String sqlState;
		try {
			Statement stmt = connection.createStatement();
			System.out.println("Executing query: "+ querySQL);
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
	
	public ResultSet getSongSearch(String attribute, String searchString) {
		String str ="SELECT s.audiofile_id, s.song_name, art.band_name, al.album_name, al.genre FROM Song s, Audiofile au,  Album al, Artist art WHERE LOWER(" + attribute + ") LIKE LOWER(\'%" + searchString + "%\')";
		str += " AND s.audiofile_id = au.audiofile_id";
		str += " AND s.email = al.email";
		str += " AND s.album_name= al.album_name";
		str += " AND al.email = art.email";
		return executeQuery(str);
	}
	
	public ResultSet getSongSearchPlaylist(String searchString) {
		String str ="SELECT s.audiofile_id, s.song_name, art.band_name, al.album_name, al.genre FROM Song s, Audiofile au,  Album al, Artist art, Playlist_Has_Song phs WHERE phs.playlist_name = \'"+ searchString + "\'";
		str += " AND s.audiofile_id = phs.audiofile_id";
		str += " AND s.audiofile_id = au.audiofile_id";
		str += " AND s.email = al.email";
		str += " AND s.album_name= al.album_name";
		str += " AND al.email = art.email";
		return executeQuery(str);
	}
	
	public ResultSet getArtistSearch(String attribute, String searchString) {
		String str = "SELECT a.email, a.band_name, a.biography FROM Artist a";
		str += " WHERE LOWER(" + attribute + ") LIKE LOWER(\'%" + searchString + "%\')";
		return executeQuery(str);
	}
	
	public ResultSet getAlbumSearch(String attribute, String searchString, boolean isOnYear) {
		String str = "SELECT a.album_name, b.band_name, a.genre, a.release_year FROM Album a, Artist b";
		if (!isOnYear)
			str += " WHERE LOWER(" + attribute + ") LIKE LOWER(\'%" + searchString + "%\')";
		else 
			str += (" WHERE " + attribute + "=" + searchString);
		str += " AND b.email = a.email";
		return executeQuery(str);
	}
	
	public ResultSet getPlaylistSearch(String attribute, String searchString) {
		String str = "SELECT p.playlist_name, p.description, p.accessibility, u.username FROM Playlist p, b_user u";
		str += " WHERE LOWER(" + attribute + ") LIKE LOWER(\'%" + searchString + "%\')";
		str += " AND p.email = u.email";
		str += " AND p.accessibility = 1";
		return executeQuery(str);
	}
	
	public ResultSet getPodEpisodes(String podName) {
		String str = "SELECT audiofile_id, episode_no, pod_episode_name, release_date, pod_name FROM Podcast_Episode";
		str += " WHERE pod_name = \'" + podName + "\'";
		return executeQuery(str);
	}
	
	public ResultSet getPodcastSearch(String attribute, String searchString) {
		String str = "SELECT p.pod_name, p.category, p.description FROM Podcast p";
		str += " WHERE LOWER(" + attribute + ") LIKE LOWER(\'%" + searchString + "%\')";
		return executeQuery(str);
	}

	public static int insertPlaylist(String email, String playlistName, int isAccessible, String description) 
	{
		int errorCode = 0;
		
		try {
			
			Statement stmt = connection.createStatement();
			
			String query = "INSERT INTO Playlist"
					+ " VALUES (\'" + email
					+ "\', \'" + playlistName
					+ "\', " + isAccessible
					+ ", \'" + description + "\')";
			
			System.out.println(query);
			
			stmt.executeUpdate(query);
			
			
		}catch(SQLException e)
		{
			errorCode = 1;
			e.printStackTrace();
		}
		
		return errorCode;
	}

	public static int insertSongsIntoPlaylist(String playlistName, ArrayList<Integer> audiofile_ids, String email) 
	{
		int errorCode = 0;
		
		try {
			
			Statement stmt = connection.createStatement();
			
			for(Integer i : audiofile_ids)
			{
				String query = "INSERT INTO Playlist_Has_Song"
						+ " VALUES (" + i
						+ ", \'" + playlistName
						+ "\', \'" + email + "\')";
				
				//System.out.println(query);
				
				stmt.executeUpdate(query);
			}
			
			
		}catch(SQLException e)
		{
			errorCode = 1;
			e.printStackTrace();
		}
		
		return errorCode;
	}
	
	public static int deleteFromFollowsArtist(String email, ArrayList<String> artistEmails) 
	{
		int errorCode = 0;
		
		try {
			
			Statement stmt = connection.createStatement();
			
			for(String a_email : artistEmails)
			{
				String query = "DELETE FROM Follows_Artist"
						+ " WHERE user_email = \'" + email + '\''
						+ " AND artist_email = \'" + a_email + "\'";
				
				System.out.println(query);
				
				stmt.executeUpdate(query);
			}
			
			
		}catch(SQLException e)
		{
			errorCode = 1;
			e.printStackTrace();
		}
		
		return errorCode;
	}

	public static int insertIntoFollowsArtist(String email, ArrayList<String> artistEmails) 
	{
		int errorCode = 0;
		
		try {
			
			Statement stmt = connection.createStatement();
			
			for(String a_email : artistEmails)
			{
				String query = "INSERT INTO Follows_Artist"
						+ " VALUES (\'" + email
						+ "\', \'" + a_email + "\')";
				
				System.out.println(query);
				
				stmt.executeUpdate(query);
			}
			
			
		}catch(SQLException e)
		{
			errorCode = 1;
			e.printStackTrace();
		}
		
		return errorCode;
	}

	public static int insertIntoFollowsPlaylist(String email, ArrayList<Playlist> playlists) 
	{
		int errorCode = 0;
		
		try {
			
			Statement stmt = connection.createStatement();
			
			for(Playlist p : playlists)
			{
				String query = "INSERT INTO Follows_Playlist"
						+ " VALUES (\'" + email
						+ "\', \'" + p.getCreator() 
						+ "\', \'" + p.getPlaylistName() + "\')";
				
				System.out.println(query);
				
				stmt.executeUpdate(query);
			}
			
			
		}catch(SQLException e)
		{
			errorCode = 1;
			e.printStackTrace();
		}
		
		return errorCode;
	}
	
	public static int deleteFromFollowsPodcast(String email, ArrayList<String> podcastNames) {
			int errorCode = 0;
		
		try {
			
			Statement stmt = connection.createStatement();
			
			for(String podname : podcastNames)
			{
				String query = "DELETE FROM Follows_Podcast"
						+ " WHERE user_email = \'" + email + '\''
						+ " AND pod_name = \'" + podname + "\'";
				
				System.out.println(query);
				
				stmt.executeUpdate(query);
			}
			
			
		}catch(SQLException e)
		{
			errorCode = 1;
			e.printStackTrace();
		}
		return 0;
	}

	public static int insertIntoFollowsPodcast(String email, ArrayList<String> podcastNames) 
	{
		int errorCode = 0;
		
		try {
			
			Statement stmt = connection.createStatement();
			
			for(String podname : podcastNames)
			{
				String query = "INSERT INTO Follows_Podcast"
						+ " VALUES (\'" + email
						+ "\', \'" + podname + "\')";
				
				System.out.println(query);
				
				stmt.executeUpdate(query);
			}
			
			
		}catch(SQLException e)
		{
			errorCode = 1;
			e.printStackTrace();
		}
		
		return errorCode;
	}

	public static ArrayList<String> getArtistsFollowed(String email) 
	{
		ArrayList<String> artistEmails = new ArrayList<String>();
		
		try {
			Statement stmt = connection.createStatement();
			
			String query = "SELECT artist_email "
					+ "FROM Follows_Artist "
					+ "WHERE user_email = \'" + email + "\'";

			ResultSet rs = stmt.executeQuery(query);

			while(rs.next())
			{
				artistEmails.add(rs.getString(1));
			}
			
					
		}catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return artistEmails;
	}

	public static ArrayList<String> getPodcastsFollowed(String email) 
	{
		ArrayList<String> podcastNames = new ArrayList<String>();
		
		try {
			Statement stmt = connection.createStatement();
			
			String query = "SELECT pod_name "
					+ "FROM Follows_Podcast "
					+ "WHERE user_email = \'" + email + "\'";

			ResultSet rs = stmt.executeQuery(query);

			while(rs.next())
			{
				podcastNames.add(rs.getString(1));
			}
			
					
		}catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return podcastNames;
	}
	
}
