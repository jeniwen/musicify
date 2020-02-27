-- delete any artist who has not released an album since 2010
SELECT a.band_name
FROM Artist a
WHERE a.band_name NOT IN (
        SELECT a.band_name
        FROM Album al INNER JOIN Artist a ON al.email=a.email
        WHERE al.release_year > 2010
);

-- set the year of all albums to the oldest album's year
SELECT Album.album_name, Album.release_year
FROM ALBUM
FETCH FIRST 10 ROWS ONLY;

UPDATE Album al 
SET al.release_year = (
	SELECT MIN(al.release_year)
	FROM Album al
);

SELECT Album.album_name, Album.release_year
FROM ALBUM
FETCH FIRST 10 ROWS ONLY;

--Insert all songs from an album into a playlist 
SELECT * FROM Playlist_Has_Song
WHERE playlist_name='late night study';


INSERT INTO Playlist_Has_Song (
    SELECT Song.audiofile_id, 'late night study', 'adelarosa342@yahoo.com'
    FROM Song
    INNER JOIN Album
        ON Song.email = Album.email AND Song.album_name=Album.album_name
    WHERE Album.album_name='Two Hands' AND Album.email='bigthief@gmail.com'
);

SELECT * FROM Playlist_Has_Song
WHERE playlist_name='late night study';


--Have one user follow all comedy podcasts
SELECT * FROM Follows_Podcast
WHERE user_email='nbutler362@hotmail.com';

INSERT INTO Follows_Podcast (
    SELECT 'nbutler362@hotmail.com', pod_name FROM Podcast
    WHERE category='Comedy'
);

SELECT * FROM Follows_Podcast
WHERE user_email='nbutler362@hotmail.com';