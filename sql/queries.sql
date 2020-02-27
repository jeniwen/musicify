-- Get all artists who released music in 2018 and 2019
SELECT a.band_name, al.album_name, al.release_year
FROM Artist a INNER JOIN Album al
    ON a.email=al.email
WHERE al.release_year IN (2018,2019)
ORDER BY a.band_name DESC;
-- Artist
--                                 Data type                     Column
-- Column name                     schema    Data type name      Length     Scale Nulls
-- ------------------------------- --------- ------------------- ---------- ----- ------
-- EMAIL                           SYSIBM    VARCHAR                     30     0 No    
-- BAND_NAME                       SYSIBM    VARCHAR                     30     0 Yes   
-- BIOGRAPHY                       SYSIBM    VARCHAR                    600     0 Yes 


-- Album
-- Column name                     schema    Data type name      Length     Scale Nulls
-- ------------------------------- --------- ------------------- ---------- ----- ------
-- EMAIL                           SYSIBM    VARCHAR                     30     0 No    
-- ALBUM_NAME                      SYSIBM    VARCHAR                     50     0 No    
-- RELEASE_YEAR                    SYSIBM    INTEGER                      4     0 Yes   
-- GENRE                           SYSIBM    VARCHAR                     20     0 Yes   
-- COVER_IMAGE                     SYSIBM    VARCHAR                     50     0 Yes 


-- Number of streams per category of podcast, ordered by most number of streams
SELECT p.category, SUM(X.streamsum) as catsum
FROM Podcast p,
(SELECT pe.pod_name, COUNT(s.stream_id) as streamsum
FROM Stream s INNER JOIN Podcast_Episode pe
    ON s.audiofile_id=pe.audiofile_id
GROUP BY pe.pod_name
)X
WHERE X.pod_name=p.pod_name
GROUP BY p.category
ORDER BY catsum DESC
;
-- Podcast
--                                Data type                     Column
-- Column name                     schema    Data type name      Length     Scale Nulls
-- ------------------------------- --------- ------------------- ---------- ----- ------
-- POD_NAME                        SYSIBM    VARCHAR                    130     0 No    
-- DESCRIPTION                     SYSIBM    VARCHAR                   2000     0 Yes   
-- CATEGORY                        SYSIBM    VARCHAR                     30     0 Yes   
-- COVER_IMAGE                     SYSIBM    VARCHAR                     50     0 Yes   

-- Stream
--                                Data type                     Column
-- Column name                     schema    Data type name      Length     Scale Nulls
-- ------------------------------- --------- ------------------- ---------- ----- ------
-- POD_NAME                        SYSIBM    VARCHAR                    130     0 No    
-- DESCRIPTION                     SYSIBM    VARCHAR                   2000     0 Yes   
-- CATEGORY                        SYSIBM    VARCHAR                     30     0 Yes   
-- COVER_IMAGE                     SYSIBM    VARCHAR                     50     0 Yes   

-- Podcast_Episode
-- Column name                     schema    Data type name      Length     Scale Nulls
-- ------------------------------- --------- ------------------- ---------- ----- ------
-- AUDIOFILE_ID                    SYSIBM    INTEGER                      4     0 No    
-- EPISODE_NO                      SYSIBM    INTEGER                      4     0 Yes   
-- RELEASE_DATE                    SYSIBM    DATE                         4     0 Yes   
-- COVER_IMAGE                     SYSIBM    VARCHAR                     50     0 Yes   
-- DESCRIPTION                     SYSIBM    VARCHAR                   2000     0 Yes   
-- POD_NAME                        SYSIBM    VARCHAR                    130     0 Yes   
-- POD_EPISODE_NAME                SYSIBM    VARCHAR                    150     0 Yes





-- Get the average number of songs in the playlists with the most followers
SELECT F.playlist_name, F.follow_sum, H.songs_sum
FROM 
(
SELECT fp.playlist_maker_email, fp.playlist_name, COUNT(*) as follow_sum 
FROM Follows_Playlist fp, Playlist p
WHERE fp.playlist_name=p.playlist_name AND fp.playlist_maker_email=p.email
GROUP BY fp.playlist_maker_email, fp.playlist_name
ORDER BY follow_sum DESC
FETCH FIRST 5 ROWS ONLY
)F,
(
SELECT phs.playlist_name, phs.email, COUNT(*) as songs_sum
FROM Playlist_Has_Song phs, Playlist p
WHERE phs.playlist_name=p.playlist_name AND phs.email=p.email
GROUP BY phs.email, phs.playlist_name
)H
WHERE F.playlist_maker_email=H.email AND F.playlist_name=H.playlist_name
ORDER BY F.follow_sum DESC
;

-- Playlist
-- Column name                     schema    Data type name      Length     Scale Nulls
-- ------------------------------- --------- ------------------- ---------- ----- ------
-- EMAIL                           SYSIBM    VARCHAR                     30     0 No    
-- PLAYLIST_NAME                   SYSIBM    VARCHAR                     30     0 No    
-- ACCESSIBILITY                   SYSIBM    CHARACTER                    1     0 Yes   
-- DESCRIPTION                     SYSIBM    VARCHAR                   2000     0 Yes 



-- Playlist_Has_Song
-- Column name                     schema    Data type name      Length     Scale Nulls
-- ------------------------------- --------- ------------------- ---------- ----- ------
-- EMAIL                           SYSIBM    VARCHAR                     30     0 No    
-- PLAYLIST_NAME                   SYSIBM    VARCHAR                     30     0 No    
-- ACCESSIBILITY                   SYSIBM    CHARACTER                    1     0 Yes   
-- DESCRIPTION                     SYSIBM    VARCHAR                   2000     0 Yes 

-- Follows_Playlist
-- Column name                     schema    Data type name      Length     Scale Nulls
-- ------------------------------- --------- ------------------- ---------- ----- ------
-- USER_EMAIL                      SYSIBM    VARCHAR                     30     0 No    
-- PLAYLIST_MAKER_EMAIL            SYSIBM    VARCHAR                     30     0 No    
-- PLAYLIST_NAME                   SYSIBM    VARCHAR                     30     0 No  

-- Get the users who streamed the most so far this year, ordered by number of streams
SELECT U.full_name, U.email, sum(s.scount) as stream_sum
FROM b_User U,
        (
        SELECT s.email, count(s.stream_id) as scount
        FROM Stream s
        GROUP BY s.email
        ORDER BY scount DESC
        FETCH FIRST 5 ROWS ONLY
         ) S
WHERE U.email = S.email
GROUP BY U.email, U.full_name
ORDER BY stream_sum DESC
;

--Stream
--Column name                     schema    Data type name      Length     Scale Nulls
------------------------------- --------- ------------------- ---------- ----- ------
--STREAM_ID                       SYSIBM    INTEGER                      4     0 No
--START_TIME                      SYSIBM    TIMESTAMP                   10     6 Yes
--EMAIL                           SYSIBM    VARCHAR                     30     0 Yes
--AUDIOFILE_ID                    SYSIBM    INTEGER                      4     0 Yes

--b_User
--Column name                     schema    Data type name      Length     Scale Nulls
------------------------------- --------- ------------------- ---------- ----- ------
--EMAIL                           SYSIBM    VARCHAR                     30     0 No
--USERNAME                        SYSIBM    VARCHAR                     20     0 Yes
--PASSWORD                        SYSIBM    VARCHAR                     20     0 Yes
--FULL_NAME                       SYSIBM    VARCHAR                     20     0 Yes
--SUBSCRIPTION_NO                 SYSIBM    INTEGER                      4     0 Yes

-- Get album lengths across all albums, singles not included. This is a subquery for the next query, but it might be useful in the future.
SELECT nosingles.album_name, TIME('00:00:00') + (SUM(MIDNIGHT_SECONDS(af.duration)))seconds AS album_duration  
        FROM (   
               --exclude singles and get song durations
                SELECT so.album_name, so.sc, s.audiofile_id
                FROM song s, (
                        SELECT s.album_name, COUNT(album_name) AS sc 
                        FROM song s
                        GROUP BY s.album_name
                        ORDER BY sc DESC
                         ) so 
                 WHERE so.sc > 1 AND s.album_name = so.album_name
                 ) nosingles 
        INNER JOIN Audiofile af
            ON af.audiofile_id = nosingles.audiofile_id
        GROUP BY nosingles.album_name
 ;        
                                                                      
-- Get the average album length across all albums, singles not included.                                                                       
SELECT TIME('00:00:00') + (AVG(MIDNIGHT_SECONDS(albums.album_duration)))seconds AS album_average_duration
FROM(   --get album durations w/o singles
        SELECT nosingles.album_name, TIME('00:00:00') + (SUM(MIDNIGHT_SECONDS(af.duration)))seconds AS album_duration  
        FROM (   
               --exclude singles and get song durations
                SELECT so.album_name, so.sc, s.audiofile_id
                FROM song s, (
                        SELECT s.album_name, COUNT(album_name) AS sc 
                        FROM song s
                        GROUP BY s.album_name
                        ORDER BY sc DESC
                         ) so 
                 WHERE so.sc > 1 AND s.album_name = so.album_name
                 ) nosingles 
        INNER JOIN Audiofile af
            ON af.audiofile_id = nosingles.audiofile_id
        GROUP BY nosingles.album_name
    )albums
 ;        
 
--song 
--                                 Data type                     Column
--Column name                     schema    Data type name      Length     Scale Nulls
------------------------------- --------- ------------------- ---------- ----- ------
--AUDIOFILE_ID                    SYSIBM    INTEGER                      4     0 No
--ALBUM_NAME                      SYSIBM    VARCHAR                     50     0 Yes
--EMAIL                           SYSIBM    VARCHAR                     30     0 Yes
--SONG_NAME                       SYSIBM    VARCHAR                     50     0 Yes
                                                             
--audiofile 
--                                Data type                     Column
--Column name                     schema    Data type name      Length     Scale Nulls
------------------------------- --------- ------------------- ---------- ----- ------
--AUDIOFILE_ID                    SYSIBM    INTEGER                      4     0 No
--DURATION                        SYSIBM    TIME                         3     0 Yes

