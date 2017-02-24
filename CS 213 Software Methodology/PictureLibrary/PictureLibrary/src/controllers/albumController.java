package controllers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
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
import resources.MyImage;
import resources.user;

	public class albumController extends Application
	implements Initializable
	{
		
		@FXML AnchorPane splitTop;
		@FXML AnchorPane albumView;
		@FXML ListView<Album> List;
		@FXML Button openBut, exitBut, searchBut, renameBut, deleteBut, addBut;
		
		private static user thisUser;
		private ObservableList<Album> albums= FXCollections.observableArrayList();
		
		public albumController() {
		}

		@SuppressWarnings("unchecked")
		@Override
		public void initialize(URL location, ResourceBundle resources) 
		{
			try {
				FileInputStream fileIn = new FileInputStream("./src/dat/" + thisUser.getUserName()+".dat");
				ObjectInputStream ois = new ObjectInputStream(fileIn);
				albums=FXCollections.observableArrayList((ArrayList<Album>) ois.readObject());
				ois.close();fileIn.close();
			} catch (IOException | ClassNotFoundException e) {e.printStackTrace();}
						
			List.setItems(albums);
			
			if(!List.getItems().isEmpty())
				List.getSelectionModel().selectFirst();
			List.refresh();
			
			
		}
				
		@Override
		public void start(Stage primaryStage) throws Exception {
			
		}
		
		public void openACT(){
			int i = List.getSelectionModel().getSelectedIndex();
			if (i<=-1){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("warning");
				alert.setHeaderText("user didn't select an album to open.");
				alert.setContentText("Please select an album");

				alert.showAndWait();
				return;
			}
			pictureController.setAlbum(albums, i, thisUser);
						
			Stage primaryStage = new Stage();
			Parent root = null;
	        
	        primaryStage.setTitle(albums.get(i).getName());
	        
	    	primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent event) {

	                // consume event
	                event.consume();
	                
	            }
	        });
	    	
	        String programPath = "/forms/pictures.fxml";

	        try{
	        	root = FXMLLoader.load(getClass().getResource(programPath));
		        primaryStage.setScene(new Scene(root));
		    }catch(Exception e){}
	        
	        primaryStage.show();
	        primaryStage.setResizable(false);
	        
	        try{
	        	this.init();
	        }
	        catch(Exception e){e.printStackTrace();}
			
	        Stage thisStage = (Stage) exitBut.getScene().getWindow();
	        thisStage.close();
		}
		
		public void renameACT(){
			int i = List.getSelectionModel().getSelectedIndex();
			if (i<=-1){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("warning");
				alert.setHeaderText("user didn't select an album to rename.");
				alert.setContentText("Please select an album");

				alert.showAndWait();
				return;
			}
			
			TextInputDialog dialog = new TextInputDialog(albums.get(i).getName());
			dialog.setTitle("Rename Album");
			dialog.setHeaderText("Please enter the name of the album:");
			dialog.setContentText("Album Name:");

	    	Optional<String> result = dialog.showAndWait();
	    	if (result.isPresent()){
	    		
	    		albums.get(i).setName(result.get());
	    		//TODO check to see if the name doesnt equal any other albums
	    		List.refresh();    	
	    	} 
	    	
	    	List.getSelectionModel().selectFirst();
		}
				
		public void deleteACT(){
			int i = List.getSelectionModel().getSelectedIndex();
			if (i<=-1){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("warning");
				alert.setHeaderText("user didn't select an album to delete.");
				alert.setContentText("Please select an album");

				alert.showAndWait();
				return;
			}
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
	    	alert.setTitle("Please Confirm!");
	    	alert.setHeaderText("Delete "+albums.get(i).getName()+" Album?!?!");
	    	alert.setContentText("Are you sure?");

	    	Optional<ButtonType> result = alert.showAndWait();
	    	if (result.get() == ButtonType.OK){
	    		
	    		albums.remove(i);
	    		List.getSelectionModel().selectPrevious();
	    		List.refresh();    	
	    	} 
	    	
	    	List.getSelectionModel().selectNext();
		}
		
		public void addACT(){
			TextInputDialog dialog = new TextInputDialog("New Name");
			dialog.setTitle("New Album");
			dialog.setHeaderText("Please enter the name of the new album:");
			dialog.setContentText("Album Name:");

			Optional<String> result = dialog.showAndWait();
			
			if (result.isPresent()){
				Album tempAlbum = new Album(thisUser, result.get());
				tempAlbum.setImages(new ArrayList<MyImage>());
				albums.add(tempAlbum);
			}
			
			else 
				System.out.println("error");			
		}
		
		public void searchACT(){
			if (albums.size()<=0){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("warning");
				alert.setHeaderText("No pictures");
				alert.setContentText("Please add an album");

				alert.showAndWait();
				return;
			}
			searchController.setAlbum(albums, thisUser);
			
			Stage primaryStage = new Stage();
			Parent root = null;
	        
	        primaryStage.setTitle("Search");
	        String programPath = "/forms/search.fxml";

	        try{
	        	root = FXMLLoader.load(getClass().getResource(programPath));
		        primaryStage.setScene(new Scene(root));
		    }catch(Exception e){}
	        
	        primaryStage.show();
	        primaryStage.setResizable(false);
	        
	        try{
	        	this.init();
	        }
	        catch(Exception e){e.printStackTrace();}
			
	        Stage thisStage = (Stage) exitBut.getScene().getWindow();
	        thisStage.close();
		}
		
		public void closeACT(){
			//saves albums list to file
			try {
				FileOutputStream fout = new FileOutputStream("./src/dat/" + thisUser.getUserName()+".dat");
	    		ObjectOutputStream oos = new ObjectOutputStream(fout);
				oos.writeObject(new ArrayList<Album>(albums));
				oos.close();fout.close();
			} catch (IOException e) {e.printStackTrace();}
			
			
			Stage thisStage = (Stage) exitBut.getScene().getWindow();
	        thisStage.close();
	        
	        Stage primaryStage = new Stage();
	        
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
			
			Parent root = null;
	        
	        primaryStage.setTitle("Login");
	        String programPath = "/forms/login.fxml";

	        try {
	        	root = FXMLLoader.load(getClass().getResource(programPath));
	        }catch(Exception e){}
	        
	        primaryStage.setScene(new Scene(root));
	        primaryStage.show();
	        primaryStage.setResizable(false);
		}
				
		public static void setUser(user userIn){thisUser = userIn;}
		
		
	}
