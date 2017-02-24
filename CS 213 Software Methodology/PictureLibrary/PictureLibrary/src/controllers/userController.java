package controllers;

//Declare imports

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import resources.Album;
import resources.user;


public class userController extends Application implements Initializable {
	
	private static ObservableList<user> users = FXCollections.observableArrayList();        
	
	
	//Declaration of controls
	
	@FXML ListView<user> userList;
	@FXML TextField nameInput;
	@FXML PasswordField passInput;
	
	@FXML Button addBut;
	@FXML Button editBut;
	@FXML Button deleteBut;
	@FXML Button saveBut;
	@FXML Button cancelBut;
	
	user inputUser = new user();
	
	//private ListProperty<user> listProperty = new SimpleListProperty<>();
		
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		userList.setItems(users);



	}
	
	public static void setUsers(ObservableList<user> usersIn){
		
		users = usersIn;
		
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void addUser()
	{
		inputUser = new user();
		
		inputUser.setUserName(nameInput.getText());
		inputUser.setPassword(passInput.getText());
		
		nameInput.clear();
		passInput.clear();
		try{
			for(int i = 0; i<users.size(); i++)
				if(users.get(i).getUserName().equalsIgnoreCase(inputUser.getUserName()))
				{
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Please Confirm!");
				    	alert.setHeaderText("Delete Song?!?!");
				    	alert.setContentText("Are you sure?");
		
				    	Optional<ButtonType> result = alert.showAndWait();
				    	if (result.get() == ButtonType.OK){
				    		users.add(inputUser);
				    		userList.refresh();
				    		return;
				    	}
				    	
				    	else
				    		throw new NullPointerException();
				}
			
			//saves albums list to file
			boolean success = true;
			try {
				FileOutputStream fout = new FileOutputStream("./src/dat/" + inputUser.getUserName()+".dat");
	    		ObjectOutputStream oos = new ObjectOutputStream(fout);
	    		ObservableList<Album> albums= FXCollections.observableArrayList();
				oos.writeObject(new ArrayList<Album>(albums));
				oos.close();fout.close();
			} catch (IOException e) {success=false; e.printStackTrace();}
			
			if(success)
			{
        		Alert dirCreated = new Alert(AlertType.INFORMATION);
        		dirCreated.setTitle("Success!");
        		dirCreated.setHeaderText(null);
        		dirCreated.setContentText("Album folder created!");

        		dirCreated.showAndWait();
        		
			}
			
			else
			{
				Alert dirCreated = new Alert(AlertType.INFORMATION);
        		dirCreated.setTitle("Failed!");
        		dirCreated.setHeaderText(null);
        		dirCreated.setContentText("Album folder not created! If this is an edited profile, ignore this...");

        		dirCreated.showAndWait();
        		
				
			}
			
    		users.add(inputUser);
    		userList.refresh();
    		
    		
		}
		
		catch (NullPointerException e)
		{
			
		}
		
	}
	
	public void editUser()
	{
		
		System.out.println("This worked!");
		
		user tempUser = null;
		
		for(int i = 0; i<users.size(); i++)
		{
			System.out.println(users.get(i));
			if(users.get(i).getUserName().compareTo(userList.getSelectionModel().getSelectedItem().getUserName()) == 0)
				tempUser = users.remove(i);
		}
		
		userList.refresh();
		
		nameInput.textProperty().set(tempUser.getUserName());
		passInput.textProperty().set(tempUser.getPassword());
		
		
	}
	
	public void deleteUser()
	{
		
		inputUser = (userList.getItems().remove(userList.getSelectionModel().getSelectedIndex()));
		
		File remDir = new File("./src/dat/" + inputUser.getUserName()+".dat");

		
		boolean success = remDir.delete();
		
		if(success)
		{
    		Alert dirCreated = new Alert(AlertType.INFORMATION);
    		dirCreated.setTitle("Success!");
    		dirCreated.setHeaderText(null);
    		dirCreated.setContentText("Album folder deleted!");

    		dirCreated.showAndWait();
    		
		}
		
		else
		{
			Alert dirCreated = new Alert(AlertType.INFORMATION);
    		dirCreated.setTitle("Failed!");
    		dirCreated.setHeaderText(null);
    		dirCreated.setContentText("Album folder not deleted!");

    		dirCreated.showAndWait();
    		
			
		}
		
	}
	
	public void saveUsers()
	{
		
		try {
			
    		FileOutputStream fout = new FileOutputStream("./src/dat/users.dat");
    		ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(new ArrayList<user>(users.sorted()));
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
		
        Stage thisStage = (Stage) cancelBut.getScene().getWindow();
        thisStage.close();
		
	}

}
