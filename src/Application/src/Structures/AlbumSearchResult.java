package Application.src.Structures;

import java.sql.ResultSet;

import Application.src.QueryExecuter;

public class AlbumSearchResult extends SearchResult<Album>{

	public AlbumSearchResult(String searchOn, String searchString) {
		super(searchOn, searchString);
		performSearch();
	}

	@Override
	public void performSearch() {
		boolean isOnYear = false;
		String searchAttribute = "";
		switch (searchOn) { 		// add more cases if we want to search on more!
		case "Album Name":
			searchAttribute = "a.album_name"; break;
		case "Artist Name":
			searchAttribute = "b.band_name"; break;
		case "Release Year":
			searchAttribute = "a.release_year"; isOnYear = true; break;
		case "Genre":
			searchAttribute = "a.genre"; break;
		default: break;
		}
		try {
			ResultSet rs = QueryExecuter.instance().getAlbumSearch(searchAttribute, searchString, isOnYear);
			while (rs.next()) {
				Album albumToAdd = new Album(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4)+"");
				this.resultlist.add(albumToAdd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Album a: resultlist) {
			System.out.println(a.getAlbumName());
		}
	}
	


}
