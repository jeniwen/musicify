package Application.src;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
		
		root.add(new TextField("Test"), 0, 0, 1, 1);
		
		primaryStage.setScene(new Scene(root));
		
		primaryStage.show();
	}

}
