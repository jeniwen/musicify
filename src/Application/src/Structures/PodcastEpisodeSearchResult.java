package Application.src.Structures;

import java.sql.ResultSet;

import Application.src.QueryExecuter;

public class PodcastEpisodeSearchResult extends SearchResult<PodcastEpisode> {

	public PodcastEpisodeSearchResult(String searchString) {
		super("", searchString);
		performSearch();
	}

	@Override
	public void performSearch() {
		try {
			ResultSet rs = QueryExecuter.instance().getPodEpisodes(searchString);
			while (rs.next()) {
				PodcastEpisode peToAdd = new PodcastEpisode(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDate(4), rs.getString(5));
				this.resultlist.add(peToAdd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		for (PodcastEpisode p: resultlist) {
//			System.out.println(p.getEpisodeNo() + " " + p.getEpisodeName());
//		}
		
	}

}
