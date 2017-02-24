package application;
	
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try 
		{
			
	        AnchorPane root = null;
	        
	        primaryStage.setTitle("Login");
	        String loginPath = "/forms/login.fxml";
	        
	        
	        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent event) {

	                // consume event
	                event.consume();

	                // show close dialog
	                Alert alert = new Alert(AlertType.CONFIRMATION);
	                alert.setTitle("Close Confirmation");
	                alert.setHeaderText("Do you really want to quit?");
	                alert.initOwner( primaryStage);

	                Optional<ButtonType> result = alert.showAndWait();
	                if (result.get() == ButtonType.OK){
	                    Platform.exit();
	                }
	            }
	        });
	        try
	        {
	        	
				root = FXMLLoader.load(getClass().getResource(loginPath));

	        }
	        
	        catch(Exception e)
	        {
	        	
	        	
	        }
	        
	        primaryStage.setScene(new Scene(root));
	        primaryStage.show();
	        primaryStage.setResizable(false);
	        
	        

		
		} 
		
		catch(Exception e)
		{
			
			e.printStackTrace();
			
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
