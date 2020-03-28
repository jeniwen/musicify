package Application.src.Structures;
import java.sql.ResultSet;
import Application.src.QueryExecuter;

//Calling example: SongSearchResult ssr = new SongSearchResult("Song Name", "The Duck Song")
public class SongSearchResult extends SearchResult<Song> {
	public SongSearchResult(String searchOn, String searchString) {
		super(searchOn, searchString);
		performSearch();

	}
//	Song Name",
//	"Artist Name",
//	"Album Name",
//	"Genre");

	@Override
	public void performSearch() {
		String searchAttribute = "";
		switch (searchOn) { 		// add more cases if we want to search on more!
		case "Song Name":
			searchAttribute = "s.song_name"; break;
		case "Artist Name":
			searchAttribute = "art.band_name"; break;
		case "Album Name":
			searchAttribute = "al.album_name"; break;
		case "Genre":
			searchAttribute = "al.genre"; break;
		default:
			searchAttribute = "s.song_name";
		}

		try {
			ResultSet rs = QueryExecuter.instance().getSongSearch(searchAttribute, searchString);
			while (rs.next()) {
				Song songToAdd = new Song(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				this.resultlist.add(songToAdd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Song s: resultlist) {
			System.out.println(s.getSongName());
		}
		
	}
}