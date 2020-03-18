package Structures;

import java.sql.ResultSet;
import java.util.ArrayList;

import Application.src.QueryExecuter;

public abstract class SearchResult<T> {
	protected ArrayList<T> resultlist;
	private String searchOn;
	private String searchString;

	public SearchResult(String searchOn, String searchString) {
		this.resultlist = new ArrayList<T>();
		this.searchOn = searchOn;
		this.searchString = searchString;
	}

	public ArrayList<T> getResultList() {
		return this.resultlist;
	}
}

//Calling example: SongSearchResult ssr = new SongSearchResult("Song Name", "The Duck Song")
class SongSearchResult extends SearchResult<Song> {
	public SongSearchResult(String searchOn, String searchString) {
		super(searchOn, searchString);
		String searchAttribute = "";
		switch (searchOn) {
		case "Song Name":
			searchAttribute = "song_name";
			break;
		// add more cases if we want to search on more!
		}

		try {
			ResultSet rs = QueryExecuter.instance().getSearch("Song", searchAttribute, searchString);
			while (rs.next()) {
				Song songToAdd = new Song(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
				this.resultlist.add(songToAdd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}


// TO DO: fill in below search results

class PlaylistSearchResult extends SearchResult<Playlist> {

	public PlaylistSearchResult(String searchOn, String searchString) {
		super(searchOn, searchString);
	}
	
} 
