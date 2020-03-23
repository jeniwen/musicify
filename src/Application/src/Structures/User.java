package Application.src.Structures;

import java.sql.*;
import java.util.ArrayList;

import Application.src.QueryExecuter;

public class User {
	private String fullName;
	private String username;
	private String email;
	private int subscriptionNum;
	private ArrayList<Playlist> playlists;

	public User(String username) {
		this.email = "";
		this.username = username;
		this.subscriptionNum = -1;
		this.fullName = "";
		try {
			ResultSet rs = QueryExecuter.instance().getUser(this.username);
			rs.next();
			if (rs != null) {
				this.fullName = rs.getString(4);
				this.email = rs.getString(1);
				this.subscriptionNum = rs.getInt(5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.playlists = new ArrayList<Playlist> ();
		setPlaylists();
	}

	public String getFullName() {
		return fullName;
	}


	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public int getSubscriptionNum() {
		return subscriptionNum;
	}
	
	public void setPlaylists() {
		try {
			ResultSet rs = QueryExecuter.instance().getPlaylistsOfUser(this.email);
			while (rs.next()) {
				Playlist toAdd = new Playlist(rs.getString(2), this, rs.getInt(3), rs.getString(4));
				this.playlists.add(toAdd);
//				System.out.println(toAdd.getPlaylistName() + " " + toAdd.getDescription());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Playlist> getPlaylists() {
		return this.playlists;
	}
	
	public void addNewPlaylist(String playlistName) {
		// Call query executor to insert new playlis
	}
}
