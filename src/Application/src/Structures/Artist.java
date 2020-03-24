package Application.src.Structures;

public class Artist {
	private final String email;
	private final String bandName;
	private final String bio;
	
	public Artist(String email, String bandName, String bio) {
		this.email = email;
		this.bandName = bandName;
		this.bio = bio;
	}

	public String getEmail() {
		return email;
	}

	public String getBandName() {
		return bandName;
	}

	public String getBio() {
		return bio;
	}

}
