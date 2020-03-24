package Application.src.Structures;

public class PodcastEpisode extends Audiofile {
	private final int episodeNo;
	private final String episodeName;
	private final java.sql.Date releaseDate;
	private final String podcastName;

//	public PodcastEpisode(int audiofileID) {
//		super(audiofileID);
//	}
	
	public PodcastEpisode(int audiofileID, int episodeNo, String episodeName, java.sql.Date releaseDate, String podcastName) {
		super(audiofileID);
		this.episodeNo = episodeNo;
		this.episodeName = episodeName;
		this.releaseDate = releaseDate;
		this.podcastName = podcastName;
	}

	public int getEpisodeNo() {
		return episodeNo;
	}

	public String getEpisodeName() {
		return episodeName;
	}

	public java.sql.Date getReleaseDate() {
		return releaseDate;
	}

	public String getPodcastName() {
		return podcastName;
	}
	


}
