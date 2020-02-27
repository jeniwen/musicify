-- ------------------------------------------------------------------------------------------------------------------------------
-- delete any artist who has not released an album since 2010
SELECT a.band_name
FROM Artist a
WHERE a.band_name NOT IN (
        SELECT a.band_name
        FROM Album al INNER JOIN Artist a ON al.email=a.email
        WHERE al.release_year > 2010
);
-- ------------------------------------------------------------------------------------------------------------------------------

-- delete all Follows_artist relations for users following Milky Chance

SELECT * from Follows_Artist 
WHERE artist_email LIKE 'milkychance.hq@gmail.com';

DELETE FROM Follows_Artist 
WHERE artist_email LIKE 'milkychance.hq@gmail.com';

SELECT * from Follows_Artist 
WHERE artist_email LIKE 'milkychance.hq@gmail.com';

-- USER_EMAIL                   ARTIST_EMAIL
-- -------------------------    ------------------------
-- cvalencia294@hotmail.com     milkychance.hq@gmail.com
-- kbonilla431@hotmail.com      milkychance.hq@gmail.com
-- lfrazier445@hotmail.com      milkychance.hq@gmail.com
-- tcox196@live.com             milkychance.hq@gmail.com
-- ystewart716@hotmail.com      milkychance.hq@gmail.com

-- Empty result set fetched

-- ------------------------------------------------------------------------------------------------------------------------------

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

-- Album
-- Column name                     schema    Data type name      Length     Scale Nulls
-- ------------------------------- --------- ------------------- ---------- ----- ------
-- EMAIL                           SYSIBM    VARCHAR                     30     0 No    
-- ALBUM_NAME                      SYSIBM    VARCHAR                     50     0 No    
-- RELEASE_YEAR                    SYSIBM    INTEGER                      4     0 Yes   
-- GENRE                           SYSIBM    VARCHAR                     20     0 Yes   
-- COVER_IMAGE                     SYSIBM    VARCHAR                     50     0 Yes 


-- ------------------------------------------------------------------------------------------------------------------------------
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


-- Playlist_Has_Song
-- Column name                     schema    Data type name      Length     Scale Nulls
-- ------------------------------- --------- ------------------- ---------- ----- ------
-- AUDIOFILE_ID                    SYSIBM    INTEGER                      4     0 No    
-- PLAYLIST_NAME                   SYSIBM    VARCHAR                     30     0 No    
-- EMAIL                           SYSIBM    VARCHAR                     30     0 No 

-- Song 
-- Column name                     schema    Data type name      Length     Scale Nulls
-- ------------------------------- --------- ------------------- ---------- ----- ------
-- AUDIOFILE_ID                    SYSIBM    INTEGER                      4     0 No    
-- ALBUM_NAME                      SYSIBM    VARCHAR                     50     0 Yes   
-- EMAIL                           SYSIBM    VARCHAR                     30     0 Yes   
-- SONG_NAME                       SYSIBM    VARCHAR                     50     0 Yes 

-- Album
-- Column name                     schema    Data type name      Length     Scale Nulls
-- ------------------------------- --------- ------------------- ---------- ----- ------
-- EMAIL                           SYSIBM    VARCHAR                     30     0 No    
-- ALBUM_NAME                      SYSIBM    VARCHAR                     50     0 No    
-- RELEASE_YEAR                    SYSIBM    INTEGER                      4     0 Yes   
-- GENRE                           SYSIBM    VARCHAR                     20     0 Yes   
-- COVER_IMAGE                     SYSIBM    VARCHAR                     50     0 Yes 

-- ------------------------------------------------------------------------------------------------------------------------------

--Have one user follow all comedy podcasts
SELECT * FROM Follows_Podcast
WHERE user_email='nbutler362@hotmail.com';

INSERT INTO Follows_Podcast (
    SELECT 'nbutler362@hotmail.com', pod_name FROM Podcast
    WHERE category='Comedy'
);

SELECT * FROM Follows_Podcast
WHERE user_email='nbutler362@hotmail.com';

-- Follows Podcast
-- Column name                     schema    Data type name      Length     Scale Nulls
-- ------------------------------- --------- ------------------- ---------- ----- ------
-- USER_EMAIL                      SYSIBM    VARCHAR                     30     0 No    
-- POD_NAME                        SYSIBM    VARCHAR                    130     0 No  


-- Podcast
--                                Data type                     Column
-- Column name                     schema    Data type name      Length     Scale Nulls
-- ------------------------------- --------- ------------------- ---------- ----- ------
-- POD_NAME                        SYSIBM    VARCHAR                    130     0 No    
-- DESCRIPTION                     SYSIBM    VARCHAR                   2000     0 Yes   
-- CATEGORY                        SYSIBM    VARCHAR                     30     0 Yes   
-- COVER_IMAGE                     SYSIBM    VARCHAR                     50     0 Yes   
