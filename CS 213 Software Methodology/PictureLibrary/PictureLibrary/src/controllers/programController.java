package controllers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import resources.Album;
import resources.user;

public class programController extends Application
implements Initializable
{
	@FXML AnchorPane splitTop;
	@FXML ListView<Album> albums;
	@FXML Button Open, New, Delete, Rename, Search, Save_Exit;
	
	private FileInputStream fileIn;
	private FileOutputStream fileOut;
	public user thisUser;
	
	public programController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		/*thisUser=new user("j", "123", false);
		thisUser.getAlbums().add(new Album("aaaa"));
		thisUser.getAlbums().add(new Album("bbbb"));
		thisUser.getAlbums().add(new Album("cccc"));
		thisUser.getAlbums().add(new Album("dddd"));
		*/
		if (thisUser == null)
		System.out.println("null");
		
		albums.setItems(thisUser.getAlbums());
		
		if(!albums.getItems().isEmpty())
			albums.getSelectionModel().selectFirst();
		albums.refresh();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		

		
	}

	public static void setUser(user userIn)
	{
		//thisUser = userIn;
	}
	
	public void openACT(){
		Stage primaryStage = new Stage();
		
		System.out.println("TEST");
		
    	primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {

                // consume event
                event.consume();
                
            }
        });
		
		Parent root = null;
        
		int i = albums.getSelectionModel().getSelectedIndex();
        primaryStage.setTitle("Album: "+ thisUser.getAlbums().get(i).getName());
        String programPath = "/forms/albums.fxml";

        try
        {

        	root = FXMLLoader.load(getClass().getResource(programPath));

        }
        
        catch(Exception e)
        {
        	
        	
        }
        

    	
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        

    	
        primaryStage.setResizable(false);
        
        try{
        	
        	this.init();
        	
        }
        catch(Exception e)
        {
        	
        }
	}

	public void newACT(){
		TextInputDialog dialog = new TextInputDialog("New Name");
		dialog.setTitle("New Album");
		dialog.setHeaderText("Please enter the name of the new album:");
		dialog.setContentText("Album Name:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			Album NEW = new Album(result.get());
			thisUser.getAlbums().add(NEW);
		}
		else 
			System.out.println("error");
		
		albums.getSelectionModel().selectLast();
	}

	public void deleteACT(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Please Confirm!");
    	alert.setHeaderText("Delete Album?!?!");
    	alert.setContentText("Are you sure?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		
    		int i = albums.getSelectionModel().getSelectedIndex();
    		thisUser.getAlbums().remove(i);
    		albums.getSelectionModel().selectPrevious();
    		albums.refresh();    	
    	} 
    	
    	albums.getSelectionModel().selectNext();
	}
	
	public void renameACT(){
		int i = albums.getSelectionModel().getSelectedIndex();
		
		TextInputDialog dialog = new TextInputDialog(thisUser.getAlbums().get(i).getName());
		dialog.setTitle("Rename Album");
		dialog.setHeaderText("Please enter the name of the album:");
		dialog.setContentText("Album Name:");

    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent()){
    		
    		thisUser.getAlbums().get(i).setName(result.get());
    		//TODO check to see if the name doesnt equal any other albums
    		albums.refresh();    	
    	} 
    	
    	albums.getSelectionModel().selectFirst();
	}
	
	public void searchACT(){
		
	}
	
	public void saveData()
	{
		
		try {
			
    		fileOut = new FileOutputStream("./src/dat/"+thisUser.getUserName()+".dat");
    		ObjectOutputStream oos = new ObjectOutputStream(fileOut);
			oos.writeObject(thisUser);
			oos.close();
			
		} 
		
		catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
		cancel();
    	
	}
	
	public void cancel(){

		Stage primaryStage = new Stage();
		
		Parent root = null;
        
        primaryStage.setTitle("Login");
        String programPath = "/forms/login.fxml";

        try
        {

        	root = FXMLLoader.load(getClass().getResource(programPath));

        }
        
        catch(Exception e)
        {
        	
        	
        }
        
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setResizable(false);
        
        try{
        	
        	this.init();
        	
        }
        catch(Exception e)
        {
        	
        }
		
        Stage thisStage = (Stage) Save_Exit.getScene().getWindow();
        thisStage.close();
		
	}
	

}
