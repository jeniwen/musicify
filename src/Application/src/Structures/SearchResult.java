package Application.src.Structures;

import java.sql.ResultSet;
import java.util.ArrayList;

import Application.src.QueryExecuter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class SearchResult<T> {
	protected ArrayList<T> resultlist;
	protected String searchOn;
	protected String searchString;

	public SearchResult(String searchOn, String searchString) {
		this.resultlist = new ArrayList<T>();
		this.searchOn = searchOn;
		this.searchString = searchString;
	}

	public ObservableList<T> getResultList() {
		return FXCollections.observableArrayList(this.resultlist);
	}
	
	public abstract void performSearch();
	
//	public ObservableList<T> arrayToObservable()
}




// TO DO: fill in below search results

//class PlaylistSearchResult extends SearchResult<Playlist> {
//
//	public PlaylistSearchResult(String searchOn, String searchString) {
//		super(searchOn, searchString);
//	}
//	
//} 
