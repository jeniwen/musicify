package Application.src;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

	public static void main(String[] args) 
	{
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{

		VBox root = new VBox();
		root.getStyleClass().add("outer_box");
		root.getChildren().add(createProfileSection());
		root.getChildren().add(createSearchSection());
		root.getChildren().add(createUtilitiesBar());
		
		Scene scene = new Scene(root, 500, 500);
		scene.getStylesheets().add("Application/src/style.css");
		
		primaryStage.setTitle("Application");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private HBox createUtilitiesBar() 
	{
		HBox utilitiesBar = new HBox();
		utilitiesBar.getStyleClass().add("outer_box");
		utilitiesBar.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
		
		Button logoutButton = new Button("Log Out");
		logoutButton.getStyleClass().add("button");
		logoutButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// how to logout
			}
			
		});
		
		Button quitButton = new Button("Quit");
		quitButton.getStyleClass().add("button");
		quitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
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
		fullNameBox.getStyleClass().add("inner");
		Label fullNameLabel = new Label("Full Name: ");
		fullNameLabel.getStyleClass().add("sub_label");
		Text fullName = new Text();
		fullName.getStyleClass().add("text");
		fullNameBox.getChildren().addAll(fullNameLabel, fullName);
		profileBox.getChildren().add(fullNameBox);
		
		HBox usernameBox = new HBox();
		usernameBox.getStyleClass().add("inner");
		Label usernameLabel = new Label("Username: ");
		usernameLabel.getStyleClass().add("sub_label");
		Text username = new Text();
		username.getStyleClass().add("text");
		usernameBox.getChildren().addAll(usernameLabel, username);
		profileBox.getChildren().add(usernameBox);
		
		HBox emailBox = new HBox();
		emailBox.getStyleClass().add("inner");
		Label emailLabel = new Label("Email: ");
		emailLabel.getStyleClass().add("sub_label");
		Text email = new Text();
		email.getStyleClass().add("text");
		emailBox.getChildren().addAll(emailLabel, email);
		profileBox.getChildren().add(emailBox);
		
		HBox subscriptionNoBox = new HBox();
		subscriptionNoBox.getStyleClass().add("inner");
		Label subscriptionNoLabel = new Label("Subscription Number: ");
		subscriptionNoLabel.getStyleClass().add("sub_label");
		Text subscriptionNo = new Text();
		subscriptionNo.getStyleClass().add("text");
		subscriptionNoBox.getChildren().addAll(subscriptionNoLabel, subscriptionNo);
		profileBox.getChildren().add(subscriptionNoBox);
		
		HBox playlistListBox = new HBox();
		playlistListBox.getStyleClass().add("inner");
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
	
	private VBox createSearchSection()
	{
		VBox searchBox = new VBox();
		
		searchBox.getStyleClass().add("outer_box");
		searchBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
		Label title = new Label("Search Section");
		title.getStyleClass().add("title");
		searchBox.getChildren().add(title);
		
		HBox searchAreaBox = new HBox();
		ToggleGroup searchArea = new ToggleGroup();
		RadioButton songOption = new RadioButton("Song");
		songOption.setToggleGroup(searchArea);
		searchAreaBox.getChildren().add(songOption);
		RadioButton artistOption = new RadioButton("Artist");
		artistOption.setToggleGroup(searchArea);
		searchAreaBox.getChildren().add(artistOption);
		RadioButton albumOption = new RadioButton("Album");
		albumOption.setToggleGroup(searchArea);
		searchAreaBox.getChildren().add(albumOption);
		RadioButton playlistOption = new RadioButton("Playlist");
		playlistOption.setToggleGroup(searchArea);
		searchAreaBox.getChildren().add(playlistOption);
		searchBox.getChildren().add(searchAreaBox);
		
		HBox searchBarBox = new HBox();
		Label searchBarLabel = new Label("Search: ");
		searchBarLabel.getStyleClass().add("sub_label");
		TextField searchBarField = new TextField();
		searchBarField.getStyleClass().add("field");
		Button enterButton = new Button("Enter");
		enterButton.getStyleClass().add("button");
		searchBarBox.getChildren().addAll(searchBarLabel, searchBarField, enterButton);
		searchBox.getChildren().add(searchBarBox);
		
		return searchBox;
	}

}
