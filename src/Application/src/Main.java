
package Application.src;


import Application.src.Structures.*;
import java.sql.SQLException;
import java.util.ArrayList;

//import Application.src.Structures.SongSearchResult;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	Stage pStage;
	ConnectionManager conManager;
	QueryExecuter queryExecuter;
	ProfileInfo currentUser = new ProfileInfo();
	
	public static void main(String[] args) 
	{
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		System.out.println("Connecting...");
		pStage = primaryStage;
		conManager = ConnectionManager.instance();
		conManager.createConnection();
		
		//System.out.println("Connection created");
		queryExecuter = QueryExecuter.instance();
		QueryExecuter.setConnnection(conManager.getConnection());
		
		
		VBox root = createLoginPage();
		
		root.getStyleClass().add("login_b");
		
		
		
		Scene scene = new Scene(root, 500, 700);
		scene.getStylesheets().add("Application/src/style.css");
		
		primaryStage.setTitle("Application");
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent t) {
		        Platform.exit();
		        conManager.closeConnection();
		        System.exit(0);
		    }
		});
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
				currentUser.username = usernameValue;
				
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
					Scene scene = new Scene(root, 700, 750);
					scene.getStylesheets().add("Application/src/style.css");
					
					pStage.setTitle("Application");
					pStage.setScene(scene);
					pStage.show();
				}else{
					// handle the password error...
					//System.out.println("username or password error handled in right place");
					displayError("Username or password incorrect. ");
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
	
	private void displayError(String errorMsg)
	{
		Stage errorStage = new Stage();
		
		VBox rootB = new VBox();
		rootB.getStyleClass().add("root");
		rootB.getStyleClass().add("outer_box");
		Label errorMsgLabel = new Label(errorMsg);
		errorMsgLabel.getStyleClass().add("sub_label");
		errorMsgLabel.setTextFill(Paint.valueOf("red"));
		rootB.getChildren().add(errorMsgLabel);
		Scene sceneB = new Scene(rootB, 250, 50);
		sceneB.getStylesheets().add("Application/src/style.css");
		errorStage.setScene(sceneB);
		errorStage.show();
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
		
		currentUser = QueryExecuter.getBasicUserInfo(currentUser);
		
		
		HBox fullNameBox = new HBox();
		Label fullNameLabel = new Label("Full Name: ");
		fullNameLabel.getStyleClass().add("sub_label");
		Text fullName = new Text(currentUser.fullName);
		fullName.getStyleClass().add("text");
		fullNameBox.getChildren().addAll(fullNameLabel, fullName);
		profileBox.getChildren().add(fullNameBox);
		
		HBox usernameBox = new HBox();
		Label usernameLabel = new Label("Username: ");
		usernameLabel.getStyleClass().add("sub_label");
		Text usernameText = new Text(currentUser.username);
		usernameText.getStyleClass().add("text");
		usernameBox.getChildren().addAll(usernameLabel, usernameText);
		profileBox.getChildren().add(usernameBox);
		
		HBox emailBox = new HBox();
		Label emailLabel = new Label("Email: ");
		emailLabel.getStyleClass().add("sub_label");
		Text email = new Text(currentUser.email);
		email.getStyleClass().add("text");
		emailBox.getChildren().addAll(emailLabel, email);
		profileBox.getChildren().add(emailBox);
		
		HBox subscriptionNoBox = new HBox();
		Label subscriptionNoLabel = new Label("Subscription Number: ");
		subscriptionNoLabel.getStyleClass().add("sub_label");
		Text subscriptionNo = new Text(currentUser.subscriptionNo);
		subscriptionNo.getStyleClass().add("text");
		subscriptionNoBox.getChildren().addAll(subscriptionNoLabel, subscriptionNo);
		profileBox.getChildren().add(subscriptionNoBox);
		
		currentUser.playlistNames.clear();
		currentUser = QueryExecuter.getAllPlaylistNamesForUser(currentUser);
		
		HBox playlistListBox = new HBox();
		Label playlistListLabel = new Label("Playlists: ");
		playlistListLabel.getStyleClass().add("sub_label");
		ListView playlistList = new ListView();
		playlistList.setPrefWidth(380);
		playlistList.setPrefHeight(80);
		
		playlistList.getItems().addAll(currentUser.playlistNames);
		
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
		// for the default
		comboOptions.addAll("Song Name",
				"Artist Name",
				"Album Name",
				"Genre");
		combo.setValue("Song Name");
		// event handling
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
				comboOptions.addAll("Band Name");
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
									"Creator Username");
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
									"Category");
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
		searchArea.selectToggle(songOption);
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
				SearchQueryParams parameters = new SearchQueryParams();
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
				}else
				{
					parameters.radioOptionValue = "None of the above";
				}
				parameters.comboOptionValue = combo.getValue();
				parameters.searchFieldValue = searchBarField.getText();
				
//				System.out.println(parameters.radioOptionValue);
//				System.out.println(parameters.comboOptionValue);
//				System.out.println(parameters.searchFieldValue);
				// pass parameters to code behind
				
				// display results window
				
				VBox rootC = new VBox();
				rootC.getStyleClass().add("outer_box");
				rootC.getChildren().add(createResultsSection(parameters));
				Scene scene = new Scene(rootC, 500, 700);
				scene.getStylesheets().add("Application/src/style.css");
				
				pStage.setTitle("Application");
				pStage.setScene(scene);
				pStage.show();
			}
			
		});
		searchBarBox.getChildren().addAll(combo, searchBarField, enterButton);
		searchBox.getChildren().add(searchBarBox);
		
		return searchBox;
	}
	
	private VBox createResultsSection(SearchQueryParams parameters)
	{
		VBox resultsSection = new VBox();
		
		Label resultsSectionLabel = new Label("Results Section");
		resultsSectionLabel.getStyleClass().add("title");
		resultsSection.getChildren().add(resultsSectionLabel);
		
		TableView table = new TableView();
		table.setEditable(false);
		
		// to create the columns
		if(parameters.radioOptionValue == "Song")
		{
			TableView<Song> songTable = new TableView<Song>();
			TableColumn songName = new TableColumn("Song Name");
			songName.setCellValueFactory(new PropertyValueFactory<Song,String>("songName"));
			TableColumn bandName = new TableColumn("Band Name");
			bandName.setCellValueFactory(new PropertyValueFactory<Song,String>("bandName"));
			TableColumn albumName = new TableColumn("Album Name");
			albumName.setCellValueFactory(new PropertyValueFactory<Song,String>("albumName"));
			TableColumn genre = new TableColumn("Genre");
			genre.setCellValueFactory(new PropertyValueFactory<Song,String>("genre"));
			TableColumn duration = new TableColumn("Duration");
			duration.setCellValueFactory(new PropertyValueFactory<Song,String>("duration"));
			
			SongSearchResult data = new SongSearchResult(parameters.comboOptionValue, parameters.searchFieldValue);
			songTable.setItems(data.getResultList());
			
			songTable.getColumns().addAll(songName, bandName, albumName, genre, duration);
			resultsSection.getChildren().add(songTable);
			
		}else if (parameters.radioOptionValue == "Artist")
		{
			TableView<Artist> artistTable = new TableView<Artist>();
			TableColumn bandName = new TableColumn("Band Name");
			bandName.setCellValueFactory(new PropertyValueFactory<Song,String>("bandName"));
			TableColumn description = new TableColumn("Bio");
			description.setCellValueFactory(new PropertyValueFactory<Song,String>("bio"));
			
			ArtistSearchResult data = new ArtistSearchResult(parameters.comboOptionValue, parameters.searchFieldValue);
			artistTable.setItems(data.getResultList());
			
			artistTable.getColumns().addAll(bandName, description);
			resultsSection.getChildren().add(artistTable);
			
		}else if(parameters.radioOptionValue == "Album")
		{
			TableView<Album> albumTable = new TableView<Album>();
			TableColumn albumName = new TableColumn("Album Name");
			albumName.setCellValueFactory(new PropertyValueFactory<Song,String>("albumName"));
			TableColumn bandName = new TableColumn("Band Name");
			bandName.setCellValueFactory(new PropertyValueFactory<Song,String>("bandName"));
			TableColumn genre = new TableColumn("Genre");
			genre.setCellValueFactory(new PropertyValueFactory<Song,String>("genre"));
			TableColumn releaseYear = new TableColumn("Release Year");
			releaseYear.setCellValueFactory(new PropertyValueFactory<Song,String>("releaseYear"));
			
			AlbumSearchResult data = new AlbumSearchResult(parameters.comboOptionValue, parameters.searchFieldValue);
			
			albumTable.setItems(data.getResultList());
			albumTable.getColumns().addAll(albumName, bandName, genre, releaseYear);
			resultsSection.getChildren().add(albumTable);
			
		}else if(parameters.radioOptionValue == "Playlist")
		{
			TableView<Playlist> plTable = new TableView<Playlist>();
			TableColumn playlistName = new TableColumn("Playlist Name");
			playlistName.setCellValueFactory(new PropertyValueFactory<Song,String>("playlistName"));
			TableColumn description = new TableColumn("Description");
			description.setCellValueFactory(new PropertyValueFactory<Song,String>("description"));
			TableColumn creatorUsername = new TableColumn("Creator Username");
			creatorUsername.setCellValueFactory(new PropertyValueFactory<Song,String>("creator"));
			
			PlaylistSearchResult data = new PlaylistSearchResult(parameters.comboOptionValue, parameters.searchFieldValue);
			plTable.setItems(data.getResultList());
			plTable.getColumns().addAll(playlistName,creatorUsername, description);
			resultsSection.getChildren().add(plTable);
			
		}else if(parameters.radioOptionValue == "Podcast")
		{
			TableView<Podcast> podTable = new TableView<Podcast>();
			TableColumn podcastName = new TableColumn("Podcast Name");
			podcastName.setCellValueFactory(new PropertyValueFactory<Song,String>("podName"));
			TableColumn category = new TableColumn("Category");
			category.setCellValueFactory(new PropertyValueFactory<Song,String>("category"));
			// make sure to capture description in podcast
			TableColumn numberOfEpisodes = new TableColumn("Number of Episodes");
			numberOfEpisodes.setCellValueFactory(new PropertyValueFactory<Song,String>("numEpisodes"));
			TableColumn description = new TableColumn("Description");
			description.setCellValueFactory(new PropertyValueFactory<Song,String>("description"));
			
			
			PodcastSearchResult data = new PodcastSearchResult(parameters.comboOptionValue, parameters.searchFieldValue);
			podTable.setItems(data.getResultList());
			podTable.getColumns().addAll(podcastName, category, numberOfEpisodes, description);
			resultsSection.getChildren().add(podTable);
		}
//		else if(parameters.radioOptionValue == "Podcast Episode")
//		{
//			TableColumn podcastEpisodeNumber = new TableColumn("Podcast Episode Number");
//			TableColumn podcastEpisodeName = new TableColumn("Podcast Episode Name");
//			TableColumn podcastName = new TableColumn("Podcast Name");
//			TableColumn releaseDate = new TableColumn("Release Date");
//			// make sure to capture description in podcast episode
//			TableColumn description = new TableColumn("Description");
//			
//			table.getColumns().addAll(podcastEpisodeNumber, podcastEpisodeName, podcastName, releaseDate, description);
//			
//		}
		else
		{
			TableColumn dummy = new TableColumn("");
			table.getColumns().add(dummy);
		}
		
//		resultsSection.getChildren().add(table);
		
		// for the buttons at the bottom
		
		HBox buttonBox = new HBox();
		buttonBox.getStyleClass().add("inner");
		Button returnButton = new Button("Return to Main Window");
		returnButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) 
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
			}
			
		});
		returnButton.getStyleClass().add("button");
		buttonBox.getChildren().add(returnButton);
		
		Button logoutButton = new Button("Logout");
		logoutButton.getStyleClass().add("button");
		logoutButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) 
			{
				VBox root = createLoginPage();
				
				root.getStyleClass().add("login_b");
				Scene scene = new Scene(root, 500, 700);
				scene.getStylesheets().add("Application/src/style.css");
				
				pStage.setTitle("Application");
				pStage.setScene(scene);
				pStage.show();
				
			}
			
		});
		buttonBox.getChildren().add(logoutButton);
		
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
		buttonBox.getChildren().add(quitButton);
		
		resultsSection.getChildren().add(buttonBox);
		
		return resultsSection;
	}

}
