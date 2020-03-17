package Application.src;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
		utilitiesBar.getStyleClass().add("inner_box");
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
		
		profileBox.getStyleClass().add("inner_box");
		profileBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
		Label title = new Label("Profile Section");
		title.getStyleClass().add("label");
		profileBox.getChildren().add(title);
		
		return profileBox;
	}
	
	private VBox createSearchSection()
	{
		VBox searchBox = new VBox();
		
		searchBox.getStyleClass().add("inner_box");
		searchBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
		Label title = new Label("Search Section");
		title.getStyleClass().add("label");
		searchBox.getChildren().add(title);
		
		return searchBox;
	}

}
