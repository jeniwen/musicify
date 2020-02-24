-- view: all listeners listening to music between 11:00 and 11:30pm 
CREATE VIEW UsersListeningAt23 (email, full_name, subscription_no) AS
SELECT b.email, b.full_name, b.subscription_no
FROM b_user b
WHERE b.email IN (
	SELECT b.email
	FROM Stream s INNER JOIN b_user b ON s.email=b.email
	WHERE s.start_time>TIMESTAMP('2020-02-24 23:00:00') AND s.start_time<TIMESTAMP('2020-02-24 23:30:00')      
);

-- query involving it the view: users of this view who are not administrators
SELECT u.full_name
FROM UsersingListeningAt23 u
WHERE u.full_name NOT IN (
	SELECT a.full_name
	FROM Administrator a
);

-- update view: give all the same subscription number
UPDATE UsersListeningAt23 
SET subscription_no=100;

-- view: all streams of U2
CREATE VIEW ListeningToU2 (stream_id, audiofile_id, email) AS
SELECT st.stream_id, st.audiofile_id, b.email
FROM Stream st INNER JOIN b_user b ON st.email=b.email
WHERE st.email LIKE 'u2.official@icloud.com';

-- query involving it the view: see users who have streamed U2 and Gorillaz
SELECT l.email
FROM ListeningToU2 l INNER JOIN (
	SELECT st.stream_id, st.audiofile_id, b.email
	FROM Stream st  INNER JOIN b_user b ON st.email = b.email
	WHERE st.email LIKE 'gorillazzz@me.com'
)X  
ON l.email = X.email;

-- update view: set every audiofile_id to 3
UPDATE ListeningToU2
SET audiofile_id=3;
-- not permitted by our schema