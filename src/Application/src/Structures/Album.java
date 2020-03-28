package Application.src.Structures;


public class Album {
	private final String albumName;
	private final String bandName;
	private final String genre;
	private final String releaseYear;
	
	public Album(String albumName, String bandName, String genre, String releaseYear) {
		this.albumName = albumName;
		this.bandName = bandName;
		this.genre = genre;
		this.releaseYear = releaseYear;
	}

	public String getAlbumName() {
		return albumName;
	}

	public String getBandName() {
		return bandName;
	}

	public String getGenre() {
		return genre;
	}

	public String getReleaseYear() {
		return releaseYear;
	}
	

}
