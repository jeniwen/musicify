package Application.src.Structures;

import java.sql.ResultSet;

import Application.src.QueryExecuter;

public class Audiofile {
	private int audiofileID;
	private java.sql.Time duration;
	
	public Audiofile(int audiofileID) {
		this.audiofileID = audiofileID;
		try {
			ResultSet rs = QueryExecuter.instance().getAudiofile(audiofileID);
			rs.next();
			if (rs != null) {
				this.duration = rs.getTime(2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(audiofileID + " " + duration);
		
	}

	public int getAudiofileID() {
		return audiofileID;
	}

	public java.sql.Time getDuration() {
		return duration;
	}
	
}
