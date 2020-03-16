package Application.src;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

//	public static void main(String[] args) 
//	{
//		System.out.println("Hello world!");
//	}

	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		GridPane root = new GridPane();
		
		VBox profileBox = createProfileSection();
		VBox searchBox = createSearchSection();
		
		VBox base = new VBox();
		
		base.getChildren().add(profileBox);
		base.getChildren().add(searchBox);

		root.add(base, 0, 0, 1, 1);
		
		Scene scene = new Scene(root);
		scene.setFill(Color.GAINSBORO);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private VBox createProfileSection()
	{
		VBox profileBox = new VBox();
		
		profileBox.setMinSize(500, 300);
		profileBox.getChildren().add(new Text("profile section"));
		
		return profileBox;
	}
	
	private VBox createSearchSection()
	{
		VBox searchBox = new VBox();
		
		searchBox.setMinSize(500, 300);
		searchBox.getChildren().add(new Text("search section"));
		
		return searchBox;
	}

}
