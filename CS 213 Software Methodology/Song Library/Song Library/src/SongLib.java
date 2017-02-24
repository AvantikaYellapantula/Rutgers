/*
	 
	 Christopher Pellegrino & Jared Patriarca
	 
	 SongLib
	 10/3/2016
	 Song Library GUI Design & Implementation
 
*/

package songs;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
 
public class SongLib extends javafx.application.Application {
		
	    @Override
	    public void start(Stage primaryStage) {
	 	    	
	        primaryStage.setTitle("Song Library");
	  
	        AnchorPane root = null;
			try {
				root = FXMLLoader.load(getClass().getResource("/songs/GUI.fxml"));
			} catch (IOException e) {e.printStackTrace();}
				
	        primaryStage.setScene(new Scene(root, 800, 800));
	        primaryStage.show();
	        primaryStage.setResizable(false);
	    }
	 
	    public static void main(String[] args) {
	        launch(args);
	    }
}