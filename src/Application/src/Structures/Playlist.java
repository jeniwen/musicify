package Structures;

public class Playlist {
	private String playlistName;
	private String creator;
	private int accessibility;
	private String description;
	
	public Playlist(String playlistName, User u, int access, String desc) {
		this.playlistName = playlistName;
		this.creator = u.getEmail();
		this.accessibility = access;
		this.description = desc;
		
	}

	public String getPlaylistName() {
		return playlistName;
	}

	public String getCreator() {
		return creator;
	}

	public int getAccessibility() {
		return accessibility;
	}

	public String getDescription() {
		return description;
	}
}
