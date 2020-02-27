ALTER TABLE Subscription 
ADD CONSTRAINT conSub 
CHECK (subscription_type >=0 AND subscription_type <=2);

ALTER TABLE b_user 
ADD CONSTRAINT  conUser 
CHECK (email LIKE ‘%@%.%‘);

ALTER TABLE Playlist 
ADD CONSTRAINT conPlaylist
CHECK (accessibility = '1' OR accessibility = '0');

ALTER TABLE Stream 
ADD CONSTRAINT conStream 
CHECK (start_time <= '2020‑02‑28‑00.00.00');

