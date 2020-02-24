-- delete any artist who has not released an album since 2010
SELECT a.band_name
FROM Artist a
WHERE a.band_name NOT IN (
        SELECT a.band_name
        FROM Album al INNER JOIN Artist a ON al.email=a.email
        WHERE al.release_year > 2010
);

-- set the year of all albums to the oldest album's year
UPDATE Album al 
SET al.release_year = (
	SELECT MIN(al.release_year)
	FROM Abum al
);

--FIX CONSTANT VALUES BASED ON ACTUAL DATA
--Insert all songs from an album into a playlist !!CHANGE FIXED VALUES BASED ON DATA!!
INSERT INTO Playlist_Has_Song (
    SELECT Song.audiofile_id, "Lol", "jennn@hotmail.com"
    FROM Song
    INNER JOIN Album
        ON Song.email = Album.email AND Song.album_name=Album.album_name
    WHERE Album.album_name="Homemade Buzzcut"
);

--Have one user follow all comedy podcasts
INSERT INTO Follows_Podcast (
    SELECT "jennn@hotmail.com", pod_name FROM Podcast
    WHERE category="Comedy"
);