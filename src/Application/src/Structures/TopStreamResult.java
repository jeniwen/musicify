package Application.src.Structures;
import java.sql.ResultSet;
import Application.src.QueryExecuter;

//Calling example: SongSearchResult ssr = new SongSearchResult("Song Name", "The Duck Song")
public class TopStreamResult extends SearchResult<Song> {
	public TopStreamResult() {
		super("", "");
		performSearch();

	}
//	Song Name",
//	"Artist Name",
//	"Album Name",
//	"Genre");

	@Override
	public void performSearch() {
		try {
			ResultSet rs = QueryExecuter.instance().getTopSongs();
			while (rs.next()) {
				Song songToAdd = new Song(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
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