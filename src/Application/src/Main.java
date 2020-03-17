
package Application.src;

import java.sql.SQLException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

	Stage pStage;
	ConnectionManager conManager;
	QueryExecuter queryExecuter;
	
	public static void main(String[] args) 
	{
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		pStage = primaryStage;
		conManager = ConnectionManager.instance();
		conManager.createConnection();
		queryExecuter = QueryExecuter.instance();
		queryExecuter.setConnnection(conManager.getConnection());
		
		
		VBox root = createLoginPage();
		
		root.getStyleClass().add("login_b");
		
		
		
		Scene scene = new Scene(root, 500, 700);
		scene.getStylesheets().add("Application/src/style.css");
		
		primaryStage.setTitle("Application");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private VBox createLoginPage() 
	{
		VBox loginBox = new VBox();
		
		HBox usernameBox = new HBox();
		usernameBox.getStyleClass().add("inner");
		Label usernameLabel = new Label("Username: ");
		usernameLabel.getStyleClass().add("sub_label");
		TextField usernameField = new TextField();
		usernameField.getStyleClass().add("field");
		usernameBox.getChildren().addAll(usernameLabel, usernameField);
		loginBox.getChildren().add(usernameBox);
		
		HBox passwordBox = new HBox();
		passwordBox.getStyleClass().add("inner");
		Label passwordLabel = new Label("Password: ");
		passwordLabel.getStyleClass().add("sub_label");
		TextField passwordField = new TextField();
		passwordField.getStyleClass().add("field");
		passwordBox.getChildren().addAll(passwordLabel, passwordField);
		loginBox.getChildren().add(passwordBox);
		
		HBox buttonBox = new HBox();
		buttonBox.getStyleClass().add("inner");
		Button loginButton = new Button("Login");
		loginButton.getStyleClass().add("button");
		loginButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) 
			{
				// logic to make it so that the user is logged in only if password works
				
				String usernameValue = usernameField.getText();
				String passwordValue = passwordField.getText();
				
				// obtain real password through query : add new class for query throwing
				String realPassword = queryExecuter.getUserPassword(usernameValue);
				
				if(passwordValue.equals(realPassword))
				{
					VBox root = new VBox();
					root.getStyleClass().add("outer_box");
					root.getChildren().add(createProfileSection());
					root.getChildren().add(createSearchSection());
					root.getChildren().add(createRecentlyPlayedSection());
					root.getChildren().add(createUtilitiesBar());
					Scene scene = new Scene(root, 500, 700);
					scene.getStylesheets().add("Application/src/style.css");
					
					pStage.setTitle("Application");
					pStage.setScene(scene);
					pStage.show();
				}else{
					// handle the password error...
					System.out.println("username or password error handled in right place");
				}
				

				
			}
			
		});
		Button quitButton = new Button("Quit");
		quitButton.getStyleClass().add("button");
		quitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) 
			{
				conManager.closeConnection();
				System.exit(0);
			}
			
		});
		buttonBox.getChildren().addAll(loginButton, quitButton);
		loginBox.getChildren().add(buttonBox);
		
		return loginBox;
	}

	private VBox createRecentlyPlayedSection() 
	{
		VBox recentlyPlayedBox = new VBox();
		recentlyPlayedBox.getStyleClass().add("inner");
		recentlyPlayedBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
		
		Label title = new Label("Recently Played:");
		title.getStyleClass().add("title");
		recentlyPlayedBox.getChildren().add(title);
		
		HBox recentlyPlayedSongsBox = new HBox();
		Label recentlyPlayedSongsLabel = new Label("Songs: ");
		recentlyPlayedSongsLabel.getStyleClass().add("sub_label");
		ListView recentlyPlayedSongs = new ListView();
		recentlyPlayedSongs.setPrefWidth(380);
		recentlyPlayedSongs.setPrefHeight(80);
		recentlyPlayedSongs.getItems().add("Song 1");
		recentlyPlayedSongs.getItems().add("Song 2");
		recentlyPlayedSongs.getItems().add("Song 3");
		recentlyPlayedSongs.getItems().add("Song 4");
		recentlyPlayedSongs.getItems().add("Song 5");
		recentlyPlayedSongs.getItems().add("Song 6");
		recentlyPlayedSongs.getItems().add("Song 7");
		recentlyPlayedSongs.getItems().add("Song 8");
		recentlyPlayedSongs.getItems().add("Song 9");
		recentlyPlayedSongsBox.getChildren().addAll(recentlyPlayedSongsLabel, recentlyPlayedSongs);
		recentlyPlayedBox.getChildren().add(recentlyPlayedSongsBox);
		
		HBox recentlyPlayedPodEpisodesBox = new HBox();
		Label recentlyPlayedPodEpisodesLabel = new Label("Songs: ");
		recentlyPlayedPodEpisodesLabel.getStyleClass().add("sub_label");
		ListView recentlyPlayedPodEpisodes = new ListView();
		recentlyPlayedPodEpisodes.setPrefWidth(380);
		recentlyPlayedPodEpisodes.setPrefHeight(80);
		recentlyPlayedPodEpisodes.getItems().add("PodcastEpisode 1");
		recentlyPlayedPodEpisodes.getItems().add("PodcastEpisode 2");
		recentlyPlayedPodEpisodes.getItems().add("PodcastEpisode 3");
		recentlyPlayedPodEpisodes.getItems().add("PodcastEpisode 4");
		recentlyPlayedPodEpisodes.getItems().add("PodcastEpisode 5");
		recentlyPlayedPodEpisodes.getItems().add("PodcastEpisode 6");
		recentlyPlayedPodEpisodes.getItems().add("PodcastEpisode 7");
		recentlyPlayedPodEpisodes.getItems().add("PodcastEpisode 8");
		recentlyPlayedPodEpisodes.getItems().add("PodcastEpisode 9");
		recentlyPlayedPodEpisodesBox.getChildren().addAll(recentlyPlayedPodEpisodesLabel, recentlyPlayedPodEpisodes);
		recentlyPlayedBox.getChildren().add(recentlyPlayedPodEpisodesBox);
		
		return recentlyPlayedBox;
	}

	private HBox createUtilitiesBar() 
	{
		HBox utilitiesBar = new HBox();
		utilitiesBar.getStyleClass().add("inner");
		utilitiesBar.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
		
		Button logoutButton = new Button("Log Out");
		logoutButton.getStyleClass().add("button");
		logoutButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) 
			{
				// return to the initial menu
				// necessary to forget person's info?
				
				VBox root = createLoginPage();
				
				root.getStyleClass().add("login_b");
				Scene scene = new Scene(root, 500, 700);
				scene.getStylesheets().add("Application/src/style.css");
				
				pStage.setTitle("Application");
				pStage.setScene(scene);
				pStage.show();
				
			}
			
		});
		
		Button quitButton = new Button("Quit");
		quitButton.getStyleClass().add("button");
		quitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				conManager.closeConnection();
				System.exit(0);
			}
			
		});
		
		utilitiesBar.getChildren().addAll(logoutButton, quitButton);
		
		return utilitiesBar;
	}

	private VBox createProfileSection()
	{
		VBox profileBox = new VBox();
		profileBox.getStyleClass().add("inner");
		profileBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
		
		Label title = new Label("Profile Section");
		title.getStyleClass().add("title");
		profileBox.getChildren().add(title);
		
		HBox fullNameBox = new HBox();
		Label fullNameLabel = new Label("Full Name: ");
		fullNameLabel.getStyleClass().add("sub_label");
		Text fullName = new Text();
		fullName.getStyleClass().add("text");
		fullNameBox.getChildren().addAll(fullNameLabel, fullName);
		profileBox.getChildren().add(fullNameBox);
		
		HBox usernameBox = new HBox();
		Label usernameLabel = new Label("Username: ");
		usernameLabel.getStyleClass().add("sub_label");
		Text username = new Text();
		username.getStyleClass().add("text");
		usernameBox.getChildren().addAll(usernameLabel, username);
		profileBox.getChildren().add(usernameBox);
		
		HBox emailBox = new HBox();
		Label emailLabel = new Label("Email: ");
		emailLabel.getStyleClass().add("sub_label");
		Text email = new Text();
		email.getStyleClass().add("text");
		emailBox.getChildren().addAll(emailLabel, email);
		profileBox.getChildren().add(emailBox);
		
		HBox subscriptionNoBox = new HBox();
		Label subscriptionNoLabel = new Label("Subscription Number: ");
		subscriptionNoLabel.getStyleClass().add("sub_label");
		Text subscriptionNo = new Text();
		subscriptionNo.getStyleClass().add("text");
		subscriptionNoBox.getChildren().addAll(subscriptionNoLabel, subscriptionNo);
		profileBox.getChildren().add(subscriptionNoBox);
		
		HBox playlistListBox = new HBox();
		Label playlistListLabel = new Label("Playlists: ");
		playlistListLabel.getStyleClass().add("sub_label");
		ListView playlistList = new ListView();
		playlistList.setPrefWidth(380);
		playlistList.setPrefHeight(80);
		playlistList.getItems().add("Playlist 1");
		playlistList.getItems().add("Playlist 2");
		playlistList.getItems().add("Playlist 3");
		playlistList.getItems().add("Playlist 4");
		playlistList.getItems().add("Playlist 5");
		playlistList.getItems().add("Playlist 6");
		playlistList.getItems().add("Playlist 7");
		playlistList.getItems().add("Playlist 8");
		playlistList.getItems().add("Playlist 9");
		playlistListBox.getChildren().addAll(playlistListLabel, playlistList);
		profileBox.getChildren().add(playlistListBox);
		
		
		return profileBox;
	}

	private VBox createSearchSection() {
		VBox searchBox = new VBox();
		
		searchBox.getStyleClass().add("inner");
		searchBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
		Label title = new Label("Search Section");
		title.getStyleClass().add("title");
		searchBox.getChildren().add(title);
		
		ObservableList<String> comboOptions = FXCollections.observableArrayList();
		ComboBox<String> combo = new ComboBox<String>(comboOptions);
		
		HBox searchAreaBox = new HBox();
		ToggleGroup searchArea = new ToggleGroup();
		RadioButton songOption = new RadioButton("Song");
		songOption.setToggleGroup(searchArea);
		songOption.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				comboOptions.clear();
				comboOptions.addAll("Song Name",
									"Artist Name",
									"Album Name",
									"Playlist Name",
									"Genre");
				combo.setValue("Song Name");
			}
			
		});
		searchAreaBox.getChildren().add(songOption);
		RadioButton artistOption = new RadioButton("Artist");
		artistOption.setToggleGroup(searchArea);
		artistOption.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				comboOptions.clear();
				comboOptions.addAll("Band Name",
									"Album Name",
									"Song Name");
				combo.setValue("Band Name");
			}
			
		});
		searchAreaBox.getChildren().add(artistOption);
		RadioButton albumOption = new RadioButton("Album");
		albumOption.setToggleGroup(searchArea);
		albumOption.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				comboOptions.clear();
				comboOptions.addAll("Album Name",
									"Artist Name",
									"Song Name",
									"Release Year",
									"Genre");
				combo.setValue("Album Name");
			}
			
		});
		searchAreaBox.getChildren().add(albumOption);
		RadioButton playlistOption = new RadioButton("Playlist");
		playlistOption.setToggleGroup(searchArea);
		playlistOption.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				comboOptions.clear();
				comboOptions.addAll("Playlist Name",
									"Creator Username",
									"Song Name");
				combo.setValue("Playlist Name");
			}
			
		});
		searchAreaBox.getChildren().add(playlistOption);
		RadioButton podcastOption = new RadioButton("Podcast");
		podcastOption.setToggleGroup(searchArea);
		podcastOption.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				comboOptions.clear();
				comboOptions.addAll("Podcast Name",
									"Category",
									"Podcast Episode Name");
				combo.setValue("Podcast Name");
			}
			
		});
		searchAreaBox.getChildren().add(podcastOption);
		RadioButton podcastEpisodeOption = new RadioButton("Podcast Episode");
		podcastEpisodeOption.setToggleGroup(searchArea);
		podcastEpisodeOption.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				comboOptions.clear();
				comboOptions.addAll("Podcast Episode Name",
									"Podcast Name",
									"Category");
				combo.setValue("Podcast Episode Name");
			}
			
		});
		searchAreaBox.getChildren().add(podcastEpisodeOption);
		searchBox.getChildren().add(searchAreaBox);
		
		HBox searchBarBox = new HBox();
//		Label searchBarLabel = new Label("Search: ");
//		searchBarLabel.getStyleClass().add("sub_label");
		//ComboBox<String> combo = new ComboBox<String>(comboOptions);
		TextField searchBarField = new TextField();
		searchBarField.getStyleClass().add("field");
		Button enterButton = new Button("Enter");
		enterButton.getStyleClass().add("button");
		enterButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) 
			{
				// obtain parameters
				QueryParams parameters = new QueryParams();
				if(searchArea.getSelectedToggle().equals(songOption))
				{
					parameters.radioOptionValue = "Song";
				}else if (searchArea.getSelectedToggle().equals(artistOption))
				{
					parameters.radioOptionValue = "Artist";
				}else if(searchArea.getSelectedToggle().equals(albumOption))
				{
					parameters.radioOptionValue = "Album";
				}else if(searchArea.getSelectedToggle().equals(playlistOption))
				{
					parameters.radioOptionValue = "Playlist";
				}else if(searchArea.getSelectedToggle().equals(podcastOption))
				{
					parameters.radioOptionValue = "Podcast";
				}else if(searchArea.getSelectedToggle().equals(podcastEpisodeOption))
				{
					parameters.radioOptionValue = "Podcast Episode";
				}
				parameters.comboOptionValue = combo.getValue();
				parameters.searchFieldValue = searchBarField.getText();
				
				System.out.println(parameters.radioOptionValue);
				System.out.println(parameters.comboOptionValue);
				System.out.println(parameters.searchFieldValue);
				// pass parameters to code behind
				
				
			}
			
		});
		searchBarBox.getChildren().addAll(combo, searchBarField, enterButton);
		searchBox.getChildren().add(searchBarBox);
		
		return searchBox;
	}

}
