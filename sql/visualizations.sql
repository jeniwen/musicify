--podcast episodes released per year
--must have more than 3 episodes

SELECT p.pod_name,  year(e.release_date) as year, COUNT(e.audiofile_id) as numberOfEpisodes
FROM podcast p inner join podcast_episode e
        on p.pod_name = e.pod_name
WHERE p.pod_name in (SELECT pod_name
                        FROM podcast_episode
                        GROUP BY pod_name
                        HAVING COUNT(audiofile_id) > 3)
GROUP BY p.pod_name, year(e.release_date)
;

--user genre preferences from their artist follows

SELECT  a.genre, COUNT(a.genre) AS num, COUNT(a.genre) * 100.0 / (select count(*) 
                                                                from follows_artist
                                                                where user_email='ilove424@live.com') AS percentage
FROM b_user b,(SELECT DISTINCT a.email, a.genre,  f.user_email
                FROM follows_artist f INNER JOIN album a
                        ON f.artist_email = a.email
                ) a
WHERE b.email='ilove424@live.com' AND a.user_email = b.email
GROUP BY a.genre
;


