package Application.src.Structures;

import java.sql.ResultSet;

import Application.src.QueryExecuter;

public class PodcastSearchResult extends SearchResult<Podcast> {

	public PodcastSearchResult(String searchOn, String searchString) {
		super(searchOn, searchString);
		performSearch();
	}

	@Override
	public void performSearch() {
		String searchAttribute = "";
		switch (searchOn) { 		// add more cases if we want to search on more!
		case "Podcast Name":
			searchAttribute = "p.pod_name"; break;
		case "Category":
			searchAttribute = "p.category"; break;
		default: break;
		}
		try {
			ResultSet rs = QueryExecuter.instance().getPodcastSearch(searchAttribute, searchString);
			while (rs.next()) {
				Podcast pToAdd = new Podcast(rs.getString(1), rs.getString(2), rs.getString(3));
				this.resultlist.add(pToAdd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		for (Podcast p: resultlist) {
//			System.out.println(p.getPodName());
//		}
	}

}
