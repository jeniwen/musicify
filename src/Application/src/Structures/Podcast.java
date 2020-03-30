package Application.src.Structures;

import java.sql.ResultSet;
import java.util.ArrayList;

import Application.src.QueryExecuter;

public class Podcast {
	private final String podName;
	private final String category;
	private final String description;
	private ArrayList<PodcastEpisode> episodes;
	private final int numEpisodes;

	public Podcast(String podName, String category, String description) {
		this.podName = podName;
		this.category = category;
		this.description = description;
		this.episodes = new ArrayList<PodcastEpisode>();
		this.numEpisodes = episodes.size();
	}

	public void setEpisodes() {
		try {
			ResultSet rs = QueryExecuter.instance().getPodEpisodes(podName);
			if (rs != null) {
				while (rs.next()) {
					PodcastEpisode plToAdd = new PodcastEpisode(rs.getInt(1), rs.getInt(2), rs.getString(3),
							rs.getDate(4), rs.getString(5));
					this.episodes.add(plToAdd);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		for (PodcastEpisode p: episodes) {
//			System.out.println(p.getEpisodeName());
//		}
	}

	public String getPodName() {
		return podName;
	}

	public String getCategory() {
		return category;
	}

	public String getDescription() {
		return description;
	}

	public ArrayList<PodcastEpisode> getEpisodes() {
		return episodes;
	}

	public int getNumEpisodes() {
		return numEpisodes;
	}

}
