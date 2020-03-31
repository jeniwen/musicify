package Application.src.Structures;

import java.sql.ResultSet;

import Application.src.QueryExecuter;

public class ArtistSearchResult extends SearchResult<Artist> {

	public ArtistSearchResult(String searchOn, String searchString) {
		super(searchOn, searchString);
		performSearch();
	}

	@Override
	public void performSearch() {
		String searchAttribute = "";
		switch (searchOn) { 		// add more cases if we want to search on more!
		case "Band Name":
			searchAttribute = "a.band_name"; break;
		case "email":
			searchAttribute = "a.email"; break;
		default:
			searchAttribute = "a.band_name"; break;
		}
		try {
			ResultSet rs = QueryExecuter.instance().getArtistSearch(searchAttribute, searchString);
			while (rs.next()) {
				Artist artistToAdd = new Artist(rs.getString(1), rs.getString(2), rs.getString(3));
				this.resultlist.add(artistToAdd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Artist s: resultlist) {
			System.out.println(s.getBandName());
		}
		
	}

}
