
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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
					root.getChildren().add(createNewPlaylistSection());
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
	
	private VBox createNewPlaylistSection() 
	{
		VBox newPlaylistBox = new VBox();
		newPlaylistBox.getStyleClass().add("inner");
		newPlaylistBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
		
		Label title = new Label("Create New Playlist:");
		title.getStyleClass().add("title");
		newPlaylistBox.getChildren().add(title);
		
		HBox playlistNameBox = new HBox();
		
		Label playListNameLabel = new Label("Playlist Name: ");
		playListNameLabel.getStyleClass().add("sub_label");
		
		TextField playlistNameTextField = new TextField();
		playlistNameTextField.getStyleClass().add("text");
		
		playlistNameBox.getChildren().addAll(playListNameLabel, playlistNameTextField);
		
		newPlaylistBox.getChildren().add(playlistNameBox);
		
		HBox playlistAccessBox = new HBox();
		
		Label playlistAccessLabel = new Label("Accessibility: ");
		playlistAccessLabel.getStyleClass().add("sub_label");
		
		ToggleGroup accessibleGroup = new ToggleGroup();
		RadioButton publicOption = new RadioButton("Public");
		RadioButton privateOption = new RadioButton("Private");
		publicOption.setToggleGroup(accessibleGroup);
		privateOption.setToggleGroup(accessibleGroup);
		publicOption.setSelected(true);
		
		playlistAccessBox.getChildren().addAll(playlistAccessLabel, publicOption, privateOption);
		
		newPlaylistBox.getChildren().add(playlistAccessBox);
		
		HBox playlistDescriptionBox = new HBox();
		
		Label playlistDescriptionLabel = new Label("Description: ");
		playlistDescriptionLabel.getStyleClass().add("sub_label");
		
		TextArea playlistDescriptionTextField = new TextArea();
		playlistDescriptionTextField.getStyleClass().add("text");
		playlistDescriptionTextField.setMaxSize(400, 50);
		
		playlistDescriptionBox.getChildren().addAll(playlistDescriptionLabel, playlistDescriptionTextField);
		
		newPlaylistBox.getChildren().add(playlistDescriptionBox);
		
		Button createPlaylistButton = new Button("Create");
		
		createPlaylistButton.getStyleClass().add("button");
		createPlaylistButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) 
			{
				// create the playlist, if there is an SQL error, state that there was an error, maybe that playlist already exists for this user
				// throw an error message if the field is empty.
				
				if(playlistNameTextField.getText().equals(""))
				{
					displayError("You must enter a playlist name. ");
				}else
				{
					String playlistName = playlistNameTextField.getText();
					int isAccessible = 0;
					if(publicOption.isSelected())
					{
						isAccessible = 1;
					}
					String playlistDescription = playlistDescriptionTextField.getText();
					
					int errorCode = QueryExecuter.insertPlaylist(currentUser.email, playlistName, isAccessible, playlistDescription);
					
					if(errorCode == 1)
					{
						// say there was an error with insert (maybe already exists)
						displayError("There was an SQLException, maybe this playlist already exists.");
						
					}else if(errorCode == 0)
					{
						// reset the main page (so that it shows the playlist now
						// maybe display a success message?
						
						VBox root = new VBox();
						root.getStyleClass().add("outer_box");
						root.getChildren().add(createProfileSection());
						root.getChildren().add(createNewPlaylistSection());
						root.getChildren().add(createSearchSection());
						root.getChildren().add(createRecentlyPlayedSection());
						root.getChildren().add(createUtilitiesBar());
						Scene scene = new Scene(root, 500, 700);
						scene.getStylesheets().add("Application/src/style.css");
						
						pStage.setTitle("Application");
						pStage.setScene(scene);
						pStage.show();
						
						displayMessage("Playlist creation successful.");
						
					}
					
				}
				
			}
			
		});
		
		newPlaylistBox.getChildren().add(createPlaylistButton);		
		
		return newPlaylistBox;
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
		Scene sceneB = new Scene(rootB, 380, 50);
		sceneB.getStylesheets().add("Application/src/style.css");
		errorStage.setScene(sceneB);
		errorStage.show();
	}
	
	private void displayMessage(String errorMsg)
	{
		Stage errorStage = new Stage();
		
		VBox rootB = new VBox();
		rootB.getStyleClass().add("root");
		rootB.getStyleClass().add("outer_box");
		Label errorMsgLabel = new Label(errorMsg);
		errorMsgLabel.getStyleClass().add("sub_label");
		errorMsgLabel.setTextFill(Paint.valueOf("green"));
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
		
		Button goToFollowSectionButton = new Button("Follow Section");
		goToFollowSectionButton.getStyleClass().add("button");
		goToFollowSectionButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) 
			{
				// create new scene for follow with 3 sections
				
				VBox root = new VBox();
				root.getStyleClass().add("outer_box");
				
				root.getChildren().add(createFollowArtistSection());
				root.getChildren().add(createFollowPlaylistSection());
				root.getChildren().add(createFollowPodcastSection());
				
				// buttons section
				
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
						root.getChildren().add(createNewPlaylistSection());
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
				
				root.getChildren().add(buttonBox);
				
				
				
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
		
		utilitiesBar.getChildren().addAll(logoutButton, goToFollowSectionButton, quitButton);
		
		return utilitiesBar;
	}

	private VBox createFollowPodcastSection() 
	{
		VBox followPodcastSection = new VBox();
		followPodcastSection.getStyleClass().add("inner");
		followPodcastSection.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
		
		Label title = new Label("Podcasts You Currently Follow:");
		title.getStyleClass().add("title");
		followPodcastSection.getChildren().add(title);
		
		// make table view for podcasts
		
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
		
		podTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		podTable.setMaxSize(400, 100);
		
		// this is where we fill the table
//		PodcastSearchResult data = new PodcastSearchResult(parameters.comboOptionValue, parameters.searchFieldValue);
//		podTable.setItems(data.getResultList());
		
		ArrayList<String> podcastNames = QueryExecuter.getPodcastsFollowed(currentUser.email);
		ArrayList<Podcast> podcasts = new ArrayList<Podcast>();
		
		for(String podname : podcastNames)
		{
			podcasts.addAll(new PodcastSearchResult("Podcast Name", podname).getResultList());
		}
		
		//ArtistSearchResult data = new ArtistSearchResult(parameters.comboOptionValue, parameters.searchFieldValue);
		
		
		podTable.getItems().addAll(podcasts);
		
		podTable.getColumns().addAll(podcastName, category, numberOfEpisodes, description);
		followPodcastSection.getChildren().add(podTable);
		
		Button unfollowButton = new Button("Unfollow");
		unfollowButton.getStyleClass().add("button");
		unfollowButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) 
			{
				// query to delete the follow entry
				
			}
			
		});
		
		followPodcastSection.getChildren().add(unfollowButton);
		
		
		return followPodcastSection;
	}

	private VBox createFollowPlaylistSection() 
	{
		VBox followPlaylistSection = new VBox();
		followPlaylistSection.getStyleClass().add("inner");
		followPlaylistSection.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
		
		Label title = new Label("Playlists You Currently Follow:");
		title.getStyleClass().add("title");
		followPlaylistSection.getChildren().add(title);
		
		// make table view for playlists
		
		TableView<Playlist> plTable = new TableView<Playlist>();
		TableColumn playlistName = new TableColumn("Playlist Name");
		playlistName.setCellValueFactory(new PropertyValueFactory<Song,String>("playlistName"));
		TableColumn description = new TableColumn("Description");
		description.setCellValueFactory(new PropertyValueFactory<Song,String>("description"));
		TableColumn creatorUsername = new TableColumn("Creator Username");
		creatorUsername.setCellValueFactory(new PropertyValueFactory<Song,String>("creator"));
		
		plTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		plTable.setMaxSize(400, 100);
		
		// this is to fill the table
//		PlaylistSearchResult data = new PlaylistSearchResult(parameters.comboOptionValue, parameters.searchFieldValue);
//		plTable.setItems(data.getResultList());
		
		
		plTable.getColumns().addAll(playlistName,creatorUsername, description);
		followPlaylistSection.getChildren().add(plTable);
		
		
		Button unfollowButton = new Button("Unfollow");
		unfollowButton.getStyleClass().add("button");
		unfollowButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) 
			{
				// query to delete the follow entry
				
			}
			
		});
		
		followPlaylistSection.getChildren().add(unfollowButton);
		
		
		return followPlaylistSection;
	}

	private VBox createFollowArtistSection() 
	{
		VBox followArtistSection = new VBox();
		followArtistSection.getStyleClass().add("inner");
		followArtistSection.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
		
		Label title = new Label("Artists You Currently Follow:");
		title.getStyleClass().add("title");
		followArtistSection.getChildren().add(title);
		
		// make table view for artist
		
		TableView<Artist> artistTable = new TableView<Artist>();
		TableColumn bandName = new TableColumn("Band Name");
		bandName.setCellValueFactory(new PropertyValueFactory<Song,String>("bandName"));
		TableColumn description = new TableColumn("Bio");
		description.setCellValueFactory(new PropertyValueFactory<Song,String>("bio"));
		artistTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		artistTable.setMaxSize(400, 100);
		
		// this is to fill the table
		
		ArrayList<String> artistEmails = QueryExecuter.getArtistsFollowed(currentUser.email);
		ArrayList<Artist> artists = new ArrayList<Artist>();
		
		for(String email : artistEmails)
		{
			artists.addAll(new ArtistSearchResult("email", email).getResultList());
		}
		
		//ArtistSearchResult data = new ArtistSearchResult(parameters.comboOptionValue, parameters.searchFieldValue);
		
		
		artistTable.getItems().addAll(artists);
		
		artistTable.getColumns().addAll(bandName, description);
		followArtistSection.getChildren().add(artistTable);
		
		Button unfollowButton = new Button("Unfollow");
		unfollowButton.getStyleClass().add("button");
		unfollowButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) 
			{
				// query to delete the follow entry
				
			}
			
		});
		
		followArtistSection.getChildren().add(unfollowButton);
		
		return followArtistSection;
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
		
		TableView<Song> songTable = new TableView<Song>();
		TableView<Artist> artistTable = new TableView<Artist>();
		TableView<Playlist> plTable = new TableView<Playlist>();
		TableView<Podcast> podTable = new TableView<Podcast>();
		TableView<PodcastEpisode> podEpTable = new TableView<PodcastEpisode>();
		
		// to create the columns
		if(parameters.radioOptionValue == "Song")
		{

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
			songTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			
			songTable.getColumns().addAll(songName, bandName, albumName, genre, duration);
			
			songTable.setRowFactory( tv -> {
			    TableRow<Song> row = new TableRow<>();
			    row.setOnMouseClicked(event -> {
			        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
			        	Song rowData = row.getItem();
			        	
			        	//DO INSERTING INTO STREAM TABLE HERE
			 
			            System.out.println("Now Streaming: " + rowData.getAudiofileID() + " || " + rowData.getSongName() + " by " + rowData.getBandName());
			   
			        }
			    });
			    return row ;
			});
			resultsSection.getChildren().add(songTable);
			
		}else if (parameters.radioOptionValue == "Artist")
		{

			TableColumn bandName = new TableColumn("Band Name");
			bandName.setCellValueFactory(new PropertyValueFactory<Song,String>("bandName"));
			TableColumn description = new TableColumn("Bio");
			description.setCellValueFactory(new PropertyValueFactory<Song,String>("bio"));
			artistTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			
			ArtistSearchResult data = new ArtistSearchResult(parameters.comboOptionValue, parameters.searchFieldValue);
			artistTable.setItems(data.getResultList());
			
			artistTable.getColumns().addAll(bandName, description);
			
			artistTable.setRowFactory( tv -> {
			    TableRow<Artist> row = new TableRow<>();
			    row.setOnMouseClicked(event -> {
			        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
			        	Artist rowData = row.getItem();
			            System.out.println("Clicked on:" + rowData.getBandName());
			            VBox rootC = new VBox();
						rootC.getStyleClass().add("outer_box");
						rootC.getChildren().add(createResultsSection(new SearchQueryParams("Album","Artist Name",rowData.getBandName())));
						Scene scene = new Scene(rootC, 500, 700);
						scene.getStylesheets().add("Application/src/style.css");
						pStage.setScene(scene);
			        }
			    });
			    return row ;
			});
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
			albumTable.setRowFactory( tv -> {
			    TableRow<Album> row = new TableRow<>();
			    row.setOnMouseClicked(event -> {
			        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
			        	Album rowData = row.getItem();
			            System.out.println("Clicked on:" + rowData.getAlbumName());
			            VBox rootC = new VBox();
						rootC.getStyleClass().add("outer_box");
						rootC.getChildren().add(createResultsSection(new SearchQueryParams("Song","Album Name",rowData.getAlbumName())));
						Scene scene = new Scene(rootC, 500, 700);
						scene.getStylesheets().add("Application/src/style.css");
						pStage.setScene(scene);
			        }
			    });
			    return row ;
			});
			
			
			resultsSection.getChildren().add(albumTable);
			
		}else if(parameters.radioOptionValue == "Playlist")
		{

			TableColumn playlistName = new TableColumn("Playlist Name");
			playlistName.setCellValueFactory(new PropertyValueFactory<Song,String>("playlistName"));
			TableColumn description = new TableColumn("Description");
			description.setCellValueFactory(new PropertyValueFactory<Song,String>("description"));
			TableColumn creatorUsername = new TableColumn("Creator Username");
			creatorUsername.setCellValueFactory(new PropertyValueFactory<Song,String>("creator"));
			
			plTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			
			PlaylistSearchResult data = new PlaylistSearchResult(parameters.comboOptionValue, parameters.searchFieldValue);
			plTable.setItems(data.getResultList());
			plTable.getColumns().addAll(playlistName,creatorUsername, description);
			
			plTable.setRowFactory( tv -> {
			    TableRow<Playlist> row = new TableRow<>();
			    row.setOnMouseClicked(event -> {
			        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
			        	Playlist rowData = row.getItem();
			            System.out.println("Clicked on:" + rowData.getPlaylistName());
			            VBox rootC = new VBox();
						rootC.getStyleClass().add("outer_box");
						rootC.getChildren().add(createResultsSection(new SearchQueryParams("Song","Playlist",rowData.getPlaylistName())));
						Scene scene = new Scene(rootC, 500, 700);
						scene.getStylesheets().add("Application/src/style.css");
						pStage.setScene(scene);
			        }
			    });
			    return row ;
			});
			
			
			resultsSection.getChildren().add(plTable);
			
		}else if(parameters.radioOptionValue == "Podcast")
		{

			TableColumn podcastName = new TableColumn("Podcast Name");
			podcastName.setCellValueFactory(new PropertyValueFactory<Podcast,String>("podName"));
			TableColumn category = new TableColumn("Category");
			category.setCellValueFactory(new PropertyValueFactory<Song,String>("category"));
			// make sure to capture description in podcast
			TableColumn numberOfEpisodes = new TableColumn("Number of Episodes");
			numberOfEpisodes.setCellValueFactory(new PropertyValueFactory<Song,String>("numEpisodes"));
			TableColumn description = new TableColumn("Description");
			description.setCellValueFactory(new PropertyValueFactory<Song,String>("description"));
			
			podTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			
			PodcastSearchResult data = new PodcastSearchResult(parameters.comboOptionValue, parameters.searchFieldValue);
			podTable.setItems(data.getResultList());
			podTable.getColumns().addAll(podcastName, category, numberOfEpisodes, description);
			
			podTable.setRowFactory( tv -> {
			    TableRow<Podcast> row = new TableRow<>();
			    row.setOnMouseClicked(event -> {
			        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
			        	Podcast rowData = row.getItem();
			            System.out.println("Clicked on:" + rowData.getPodName());
			            VBox rootC = new VBox();
						rootC.getStyleClass().add("outer_box");
						rootC.getChildren().add(createResultsSection(new SearchQueryParams("Podcast Episode","",rowData.getPodName())));
						Scene scene = new Scene(rootC, 500, 700);
						scene.getStylesheets().add("Application/src/style.css");
						pStage.setScene(scene);
			        }
			    });
			    return row ;
			});
			resultsSection.getChildren().add(podTable);
		}
		else if(parameters.radioOptionValue == "Podcast Episode")
		{
			TableColumn podcastEpisodeNumber = new TableColumn("Podcast Episode Number");
			podcastEpisodeNumber.setCellValueFactory(new PropertyValueFactory<PodcastEpisode,String>("episodeNo"));
			TableColumn podcastEpisodeName = new TableColumn("Podcast Episode Name");
			podcastEpisodeName.setCellValueFactory(new PropertyValueFactory<PodcastEpisode,String>("episodeName"));
			TableColumn podcastName = new TableColumn("Podcast Name");
			podcastName.setCellValueFactory(new PropertyValueFactory<PodcastEpisode,String>("podcastName"));
			TableColumn releaseDate = new TableColumn("Release Date");
			releaseDate.setCellValueFactory(new PropertyValueFactory<PodcastEpisode,String>("releaseDate"));
			// make sure to capture description in podcast episode
//			TableColumn description = new TableColumn("Description");
			
			PodcastEpisodeSearchResult data = new PodcastEpisodeSearchResult(parameters.searchFieldValue);
			podEpTable.setItems(data.getResultList());
			podEpTable.getColumns().addAll(podcastName, podcastEpisodeNumber, podcastEpisodeName, releaseDate);
			resultsSection.getChildren().add(podEpTable);
		}
		else
		{
			TableColumn dummy = new TableColumn("");
			table.getColumns().add(dummy);
		}
		
//		resultsSection.getChildren().add(table);
		
		// ADD THE SECTION HERE
		
		if(parameters.radioOptionValue == "Song")
		{
			// this is to add the song to a playlist
			
			VBox addSongToPlaylistBox = new VBox();
			addSongToPlaylistBox.getStyleClass().add("inner");
			addSongToPlaylistBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
			
			Label title = new Label("Add Song to Playlist:");
			title.getStyleClass().add("title");
			addSongToPlaylistBox.getChildren().add(title);
			
			HBox playlistNameBox = new HBox();
			
			Label playListNameLabel = new Label("Playlist Name: ");
			playListNameLabel.getStyleClass().add("sub_label");
			
			TextField playlistNameTextField = new TextField();
			playlistNameTextField.getStyleClass().add("text");
			
			Button addButton = new Button("Add");
			addButton.getStyleClass().add("button");
			addButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) 
				{
					// would add the selected song to the playlist here
					// please beware of non existant playlists (displayError)
					// also playlists which are not "mine" as in not the currentUser's (displayError)
					
					// this is to fetch the currently selected songs
					
					ArrayList<Integer> audiofile_ids = new ArrayList<Integer>();
					
					for(Song s : songTable.getSelectionModel().getSelectedItems())
					{
						audiofile_ids.add(s.getAudiofileID());
						
					}
					
					String playlistName = playlistNameTextField.getText();
					
					if(playlistName.equals(""))
					{
						displayError("You must enter a playlist name.");
					}else
					{
						// must check that it is a legal playlist to add to
						boolean flag = false;
						
						for(String name : currentUser.playlistNames)
						{
							if(playlistName.equalsIgnoreCase(name))
							{
								flag = true;
							}
						}
						
						if(!flag)
						{
							displayError("You do not own a playlist named \""+playlistName+"\".");
						}else
						{
							// then must add to the playlist
							
							int errorCode = QueryExecuter.insertSongsIntoPlaylist(playlistName, audiofile_ids, currentUser.email);
							
							if(errorCode == 1) {
								// there was an SQL Exception, maybe the song is already in the playlsit
								displayError("There was an SQLException, maybe some of the selected songs already belong to this playlist.");
							}else
							{
								displayMessage("Songs successfully added to your playlist. ");
							}
						}		
						
					}
	
				}
				
			});
			playlistNameBox.getChildren().addAll(playListNameLabel, playlistNameTextField, addButton);
			addSongToPlaylistBox.getChildren().addAll(playlistNameBox);
			resultsSection.getChildren().add(addSongToPlaylistBox);
			
			
			
		}else if(parameters.radioOptionValue == "Artist" || parameters.radioOptionValue == "Playlist" || parameters.radioOptionValue == "Podcast")
		{
			// this is to follow {artist, playlist, podcast}
			
			VBox followXXXBox = new VBox();
			followXXXBox.getStyleClass().add("inner");
			followXXXBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
			
			String titleString = "Follow " + parameters.radioOptionValue;
			Label title = new Label(titleString);
			title.getStyleClass().add("title");
			followXXXBox.getChildren().add(title);
			
			
			Button followButton = new Button("Follow");
			followButton.getStyleClass().add("button");
			followButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) 
				{
					// add code to follow the {artist, playlist, podcast}
					// beware if the user is already following it (displayError)
					
					if(parameters.radioOptionValue == "Artist")
					{
						ArrayList<String> artistEmails = new ArrayList<String>();
						
						for(Artist a : artistTable.getSelectionModel().getSelectedItems())
						{
							artistEmails.add(a.getEmail());
							
						}
						
						int errorCode = QueryExecuter.insertIntoFollowsArtist(currentUser.email, artistEmails);
						
						if(errorCode == 1)
						{
							displayError("There was an SQLException, maybe you are already following one of these.");
						}else if(errorCode == 0)
						{
							displayMessage("Operation was successful.");
						}
						
						
					}else if(parameters.radioOptionValue == "Playlist")
					{
						ArrayList<Playlist> playlists = new ArrayList<Playlist>(plTable.getSelectionModel().getSelectedItems());
						
						int errorCode = QueryExecuter.insertIntoFollowsPlaylist(currentUser.email, playlists);
						
						if(errorCode == 1)
						{
							displayError("There was an SQLException, maybe you are already following one of these.");
						}else if(errorCode == 0)
						{
							displayMessage("Operation was successful.");
						}
						
						
					}else if(parameters.radioOptionValue == "Podcast")
					{
						ArrayList<String> podcastNames = new ArrayList<String>();
						
						for(Podcast p : podTable.getSelectionModel().getSelectedItems())
						{
							podcastNames.add(p.getPodName());
						}
						
						int errorCode = QueryExecuter.insertIntoFollowsPodcast(currentUser.email, podcastNames);
						
						if(errorCode == 1)
						{
							displayError("There was an SQLException, maybe you are already following one of these.");
						}else if(errorCode == 0)
						{
							displayMessage("Operation was successful.");
						}
						
					}
				}
				
			});
			
			followXXXBox.getChildren().addAll(followButton);
			resultsSection.getChildren().add(followXXXBox);
		}
		
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
				root.getChildren().add(createNewPlaylistSection());
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
