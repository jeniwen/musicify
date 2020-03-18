package Structures;

import java.sql.ResultSet;

import Application.src.QueryExecuter;

public class Song extends Audiofile{
	private String albumName;
	private String bandEmail;
	private String songName;
	public Song(int audiofileID) {
		super(audiofileID);
		try {
			ResultSet rs = QueryExecuter.instance().getAudiofile(audiofileID);
			rs.next();
			if (rs != null) {
				this.albumName = rs.getString(2);
				this.bandEmail = rs.getString(3);
				this.songName = rs.getString(4);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Song(int audiofileID, String albumName, String bandEmail, String songName) {
		super(audiofileID);
		this.albumName = albumName;
		this.bandEmail = bandEmail;
		this.songName = songName;
	}

	public String getAlbumName() {
		return albumName;
	}

	public String getBandEmail() {
		return bandEmail;
	}

	public String getSongName() {
		return songName;
	}

}
