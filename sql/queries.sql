-- Get all artists who released music in 2018 and 2019

SELECT a.band_name, al.album_name, al.release_year
FROM Artist a INNER JOIN Album al
    ON a.email=al.email
WHERE al.release_year IN (2018,2019)
ORDER BY a.band_name DESC;

-- Number of streams per category of podcast
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
-- Get album lengths across all albums, singles not included. 
SELECT nosingles.album_name, TIME('00:00:00') + (SUM(MIDNIGHT_SECONDS(af.duration)))seconds AS album_duration  
FROM (
        SELECT so.album_name, so.sc, s.audiofile_id
        FROM song s, (
                select s.album_name, count(album_name) as sc 
                from song s
                GROUP BY s.album_name
                ORDER BY sc DESC
                 ) so 
         where so.sc > 1 AND s.album_name = so.album_name
         ) nosingles 
INNER JOIN Audiofile af
    ON af.audiofile_id = nosingles.audiofile_id
 GROUP BY nosingles.album_name
 ;        
-- Get the average album length across all albums, singles not included. 
