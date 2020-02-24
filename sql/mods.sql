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