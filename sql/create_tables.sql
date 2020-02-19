-- Script to create our tables

CREATE TABLE Podcast (
	pod_name VARCHAR(30) NOT NULL PRIMARY KEY, 
	description VARCHAR(250), 
	category VARCHAR(15), 
	cover_image VARCHAR(50));
	
	
CREATE TABLE Subscription (
	subscription_no INTEGER NOT NULL PRIMARY KEY,
	subscription_type INTEGER CHECK (subscription_type >=0 AND subscription_type <=2),
	start_date DATE,
	payment_method VARCHAR(50));
	
CREATE TABLE Audiofile (
	audiofile_id INTEGER NOT NULL PRIMARY KEY,
	aname VARCHAR(30),
	duration TIME);
	
CREATE TABLE b_user (
	email VARCHAR(30) NOT NULL PRIMARY KEY,
	username VARCHAR(10),
	password VARCHAR(20),
	full_name VARCHAR(20),
	subscription_no INTEGER,
	FOREIGN KEY (subscription_no) REFERENCES Subscription (subscription_no));
	
CREATE TABLE Administrator (
	email VARCHAR(30) NOT NULL PRIMARY KEY,
	FOREIGN KEY (email) REFERENCES b_user(email),
	subscription_no INTEGER,
	FOREIGN KEY (subscription_no) REFERENCES Subscription (subscription_no));

CREATE TABLE Artist (
	email VARCHAR(30) NOT NULL PRIMARY KEY,
	band_name VARCHAR(30),
	biography VARCHAR(250));

CREATE TABLE Playlist (
	email VARCHAR(30) NOT NULL,
	FOREIGN KEY (email) REFERENCES b_user(email),
	playlist_name VARCHAR(30) NOT NULL,
	accessibility BIT,
	description VARCHAR(250),
	PRIMARY KEY (email, playlist_name));
	
CREATE TABLE Podcast_Episode (
	audiofile_id INTEGER NOT NULL PRIMARY KEY,
	FOREIGN KEY (audiofile_id) REFERENCES Audiofile (audiofile_id),
	episode_no INTEGER,
	release_date DATE,
	cover_image VARCHAR(50),
	description VARCHAR(250),
	pod_name VARCHAR(30),
	FOREIGN KEY (pod_name) REFERENCES Podcast (pod_name),
	pod_episode_name VARCHAR(30),
	duration TIME);
	
CREATE TABLE Stream (
	stream_id INTEGER NOT NULL PRIMARY KEY,
	start_time TIMESTAMP CHECK (start_time <= CURRENT_TIMESTAMP),
	email VARCHAR(30),
	FOREIGN KEY (email) REFERENCES b_user(email),
	audiofile_id INTEGER,
	FOREIGN KEY (audiofile_id) REFERENCES Audiofile(audiofile_id));

CREATE TABLE Album (
	email VARCHAR(30) NOT NULL,
	FOREIGN KEY (email) REFERENCES Artist(email),
	album_name VARCHAR(30) NOT NULL,
	release_year INTEGER,
	genre VARCHAR(20),
	cover_image VARCHAR(50),
	PRIMARY KEY (email, album_name));

CREATE TABLE Song (
	audiofile_id INTEGER NOT NULL PRIMARY KEY,
	FOREIGN KEY (audiofile_id) REFERENCES Audiofile(audiofile_id),
	album_name VARCHAR(30),
	email VARCHAR(30),
	FOREIGN KEY (email, album_name) REFERENCES Album(email, album_name),
	song_name VARCHAR(30),
	duration TIME); 

CREATE TABLE Follows_Playlist (
	user_email VARCHAR(30) NOT NULL,
	FOREIGN KEY (user_email) REFERENCES b_user(email),
	playlist_maker_email VARCHAR(30) NOT NULL,
	playlist_name VARCHAR(30) NOT NULL,
	FOREIGN KEY (playlist_maker_email, playlist_name) REFERENCES Playlist(email, playlist_name),
	PRIMARY KEY (user_email, playlist_name, playlist_maker_email));

CREATE TABLE Follows_Podcast (
	user_email VARCHAR(30) NOT NULL,
	FOREIGN KEY (user_email) REFERENCES b_user(email),
	pod_name VARCHAR(30) NOT NULL,
	FOREIGN KEY (pod_name) REFERENCES Podcast(pod_name),
	PRIMARY KEY (user_email, pod_name));

CREATE TABLE Follows_Artist (
	user_email VARCHAR(30) NOT NULL,
	FOREIGN KEY (user_email) REFERENCES b_user(email),
	artist_email VARCHAR(30) NOT NULL,
	FOREIGN KEY (artist_email) REFERENCES Artist(email),
	PRIMARY KEY (user_email, artist_email));

CREATE TABLE Playlist_Has_Song (
	audiofile_id INTEGER NOT NULL,
	FOREIGN KEY (audiofile_id) REFERENCES Song(audiofile_id),
	playlist_name VARCHAR(30) NOT NULL,
	email VARCHAR(30) NOT NULL,
	FOREIGN KEY (email, playlist_name) REFERENCES Playlist(email, playlist_name),
	PRIMARY KEY (audiofile_id, playlist_name, email)); 

	
	
	































