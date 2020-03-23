package Application.src.Structures;

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
	
	public Playlist(String playlistName, String description, int access, String email) {
		this.playlistName = playlistName;
		this.creator = email;
		this.accessibility = access;
		this.description = description;
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
