package Application.src.Structures;

import java.sql.ResultSet;

import Application.src.QueryExecuter;

public class Song extends Audiofile{
	private final String songName;
	private final String bandName;
	private final String albumName;
	private final String genre;
	private final String duration;
	private final String streams;
//	private String duration;
//	public Song(int audiofileID) {
//		super(audiofileID);
//		try {
//			ResultSet rs = QueryExecuter.instance().getAudiofile(audiofileID);
//			rs.next();
//			if (rs != null) {
//				this.albumName = rs.getString(2);
//				this.bandEmail = rs.getString(3);
//				this.songName = rs.getString(4);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
	public Song(int audiofileID, String songName, String bandName, String albumName, String genre) {
		super(audiofileID);
		this.songName = songName;
		this.bandName = bandName;
		this.albumName = albumName;
		this.genre = genre;
		this.duration = super.getDuration().toString();
		this.streams = "-1";
	}
	
	public Song(int audiofileID,String songName, String bandName, int totalStreams) {
		super(audiofileID);
		this.songName = songName;
		this.bandName = bandName;
		this.albumName = "";
		this.genre = "";
		this.duration = "";
		this.streams = String.valueOf(totalStreams);
	}

	public String getAlbumName() {
		return albumName;
	}

	public String getBandName() {
		return bandName;
	}

	public String getSongName() {
		return songName;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public String getStreams() {
		return streams;
	}

}
