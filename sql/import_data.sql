-- this is to import the different .csv in the folder data into a database which follows create_tables.sql's schema

import from Podcast.csv of del insert into Podcast;

import from Subscription.csv of del insert into Subscription;

import from Audiofile.csv of del insert into Audiofile;

import from b_user.csv of del insert into b_user;

import from Administrator.csv of del insert into Administrator;

import from Artist.csv of del insert into Artist;

import from Playlist.csv of del insert into Playlist;

import from Podcast_Episode.csv of del insert into Podcast_Episode;

import from Stream.csv of del insert into Stream;

import from Album.csv of del insert into Album;

import from Song.csv of del insert into Song;

import from Follows_Playlist.csv of del insert into Follows_Playlist;

import from Follows_Podcast.csv of del insert into Follows_Podcast;

import from Follows_Artist.csv of del insert into Follows_Artist;

import from Playlist_Has_Song.csv of del insert into Playlist_Has_Song;