package Structures;

public class StructuresExamples {
	public StructuresExamples() {
		User u = new User("ilove424"); //constructor automatically queries database
		System.out.println(u.getEmail() + " " + u.getFullName() + " " + u.getSubscriptionNum());
		
		
		System.out.println("\n" + u.getFullName() + "\'s playlists");
		for (Playlist p : u.getPlaylists()) {
			System.out.println(p.getPlaylistName());
		}
		
		Audiofile a = new Audiofile(1); //get audiofile with id 1;
		System.out.println(a.getAudiofileID() + " " + a.getDuration());
		
		SongSearchResult ssr = new SongSearchResult("Song Name", "Me");
		for (Song s: ssr.getResultList()) {
			System.out.println(s.getSongName() + " " + s.getAlbumName());
		}
		
	}

}
