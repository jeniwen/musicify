package Application.src.Structures;

import java.sql.ResultSet;

import Application.src.QueryExecuter;

public class PlaylistSearchResult extends SearchResult<Playlist> {

	public PlaylistSearchResult(String searchOn, String searchString) {
		super(searchOn, searchString);
		performSearch();
	}

	@Override
	public void performSearch() {
		String searchAttribute = "";
		switch (searchOn) {
		case "Playlist Name":
			searchAttribute = "p.playlist_name"; break;
		case "Creator Username":
			searchAttribute = "u.username"; break;
		default: break;
		}
		
		try {
			ResultSet rs = QueryExecuter.instance().getPlaylistSearch(searchAttribute, searchString);
			while (rs.next()) {
				Playlist plToAdd = new Playlist(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4));
				this.resultlist.add(plToAdd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Playlist pl: resultlist) {
			System.out.println(pl.getPlaylistName());
		}
		
		
	}

}
