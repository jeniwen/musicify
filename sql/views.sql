-- view: all listeners listening to music in 2014 
CREATE VIEW UsersListening2014 (email, full_name, subscription_no) AS
SELECT b.email, b.full_name, b.subscription_no
FROM b_user b
WHERE b.email IN (
	SELECT b.email
	FROM Stream s INNER JOIN b_user b ON s.email=b.email
	WHERE s.start_time>TIMESTAMP('2014-01-01 00:00:01') AND s.start_time<TIMESTAMP('2014-12-31 23:59:59')      
);

-- query involving it the view: users of this view who are not administrators
SELECT u.full_name
FROM UsersListening2014 u
WHERE u.full_name NOT IN (
	SELECT us.full_name
	FROM Administrator a INNER JOIN b_user us ON a.email LIKE u.email
);

-- update view: give all in the view the same name
UPDATE UsersListening2014 
SET full_name='Mary Contrary';

-- clean up
DROP VIEW UsersListening2014;

-- view: all streams of U2
CREATE VIEW ListeningToU2 (stream_id, audiofile_id, email) AS
SELECT st.stream_id, st.audiofile_id, st.email
FROM Stream st INNER JOIN Song s ON st.audiofile_id=s.audiofile_id
WHERE s.email LIKE 'u2.official@icloud.com';

-- query involving it the view: see users who have streamed U2 and Bryan Adams
SELECT DISTINCT l.email 
FROM ListeningToU2 l INNER JOIN (
	SELECT st.stream_id, st.audiofile_id, st.email
	FROM Stream st  INNER JOIN Song s ON st.audiofile_id=s.audiofile_id
	WHERE s.email LIKE 'bryan.adams41@hotmail.com'
)X  
ON l.email = X.email;

-- update view: set every audiofile_id to 3
UPDATE ListeningToU2
SET audiofile_id=3; -- not permitted by our schema

-- clean up
DROP VIEW ListeningToU2;
