package Application.src.Structures;
import java.sql.ResultSet;
import java.util.ArrayList;

import Application.src.QueryExecuter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//Calling example: SongSearchResult ssr = new SongSearchResult("Song Name", "The Duck Song")
public class StreamResult {
	protected ArrayList<Song> song_resultlist;
	protected ArrayList<PodcastEpisode> pod_resultlist;

	public StreamResult() {
		this.song_resultlist = new ArrayList<Song>();
		performSearch();
	}
	public StreamResult(String email) {
		this.song_resultlist = new ArrayList<Song>();
		this.pod_resultlist = new ArrayList<PodcastEpisode>();
		performSearch(email);
	}

	//top streams
	public void performSearch() {
		
		try {
			ResultSet rs = QueryExecuter.instance().getTopSongs();
			while (rs.next()) {
				Song songToAdd = new Song(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
				this.song_resultlist.add(songToAdd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		for (Song s: song_resultlist) {
//			System.out.println(s.getSongName());
//		}
		
	}
	
	//top songs
	public void performSearch(String email) {
		
		try {
			ResultSet rs = QueryExecuter.instance().getRecentSongs(email);
			while (rs.next()) {
				Song songToAdd = new Song(rs.getInt(1),rs.getString(2),"",rs.getString(3),"");
				this.song_resultlist.add(songToAdd);
			}
			rs = QueryExecuter.instance().getRecentPodcasts(email);
			while (rs.next()) {
				//public PodcastEpisode(int audiofileID, int episodeNo, String episodeName, java.sql.Date releaseDate, String podcastName) {
				PodcastEpisode epToAdd = new PodcastEpisode(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getDate(4),rs.getString(5));
				this.pod_resultlist.add(epToAdd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		for (Song s: song_resultlist) {
//			System.out.println(s.getSongName());
//		}
//		for (PodcastEpisode p: pod_resultlist) {
//			System.out.println(p.getPodcastName()+" "+p.getEpisodeName());
//		}
		
	}
	
	public ObservableList<Song> getResultList() {
		return FXCollections.observableArrayList(this.song_resultlist);
	}
	
	public ArrayList<String> getSongNames() {
		ArrayList <String> names = new ArrayList<String> ();
		for (Song s : song_resultlist) {
			names.add(s.getSongName());
		}
		return names;
	}
	
	public ArrayList<String> getPodNames() {
		ArrayList <String> names = new ArrayList<String> ();
		for (PodcastEpisode p : pod_resultlist) {
			names.add(p.getEpisodeName());
		}
		return names;
	}
}