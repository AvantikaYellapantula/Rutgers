

package controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import resources.user;

public class login_controller extends Application
implements Initializable
{
	@FXML TextField UNInput;
	@FXML PasswordField passwordInput;
	@FXML Button OKBut, cancelBut;
	@FXML AnchorPane loginPane;
	
	private String UserName = "";
	private String Password = "";
	private ObservableList <user> users;
	private user thisUser;
	private FileInputStream fileIn;
	
    @SuppressWarnings("unchecked")
	public void initialize(URL url, ResourceBundle resourceBundle) {
    	passwordInput.setOnKeyReleased(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent arg0) {
				if (arg0.getCode().equals(KeyCode.ENTER)){
					try {
						verifyName();
					} catch (Exception e) {e.printStackTrace();}
				}
			}
            
        });
    	
		try {
			fileIn = new FileInputStream("./src/dat/users.dat");
			ObjectInputStream ois = new ObjectInputStream(fileIn);
			users=FXCollections.observableArrayList((ArrayList<user>) ois.readObject());
		} catch (IOException | ClassNotFoundException e) {e.printStackTrace();}
    }
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
	   
    public void verifyName() throws Exception
    {
    	UserName = UNInput.getText();
    	Password = passwordInput.getText();
    	
    	try
    	{
    		Stage setStage = new Stage();	
	
	    	if(UserName.equalsIgnoreCase("admin") && Password.equals("Password123$"))
	    	{
	    		userController.setUsers(users);
	    		loadAdmin(setStage);
	    			
	    	}
	    	
	    	else
	    	{
		    	for(int i = 0; i<users.size(); i++)
		    		if(users.get(i).getUserName().compareTo(UserName) == 0)
		    		{

		    			if(users.get(i).getPassword().compareTo(Password) == 0)
		    			{
		    				thisUser = users.get(i);
		    				albumController.setUser(thisUser);
		    				loadProgram(setStage);
		    				
		    			}
		    		}
	    	}
	    	
	    	//throw new NullPointerException();
    	}
    	
    	catch (NullPointerException e)
    	{
    		e.printStackTrace();
    		/*
    		Alert changesMade = new Alert(AlertType.INFORMATION);
    	    changesMade.setTitle("Incorrect Information");
    	    changesMade.setHeaderText(null);
    	    changesMade.setContentText("Username or password incorrect!");
    	    
    	    changesMade.showAndWait();
    	    
    	    System.out.println(e.getMessage());
		*/
    	}

    }
    
    private void loadAdmin(Stage primaryStage) throws Exception
    {
    	
    	Parent root = null;
        
        primaryStage.setTitle("User Accounts");
        
        String programPath = "/forms/UserProfiles.fxml";
        
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {

                // consume event
                event.consume();
                
            }
        });

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
        
        Stage stage = (Stage) OKBut.getScene().getWindow();
        stage.close();
        try{
        	
        	this.init();
        	
        }
        catch(Exception e)
        {
        	
        }
        
    }
    
    private void loadProgram(Stage primaryStage)
    {
    	
    	Parent root = null;
        
        primaryStage.setTitle("Album Viewer");
        String programPath = "/forms/albums.fxml";

        try
        {

        	root = FXMLLoader.load(getClass().getResource(programPath));
            
        	primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent event) {

                    // consume event
                    event.consume();
                    
                }
            });
        	
            primaryStage.setScene(new Scene(root));


        }
        
        catch(Exception e)
        {
        	
        	
        }
        
        primaryStage.show();
        primaryStage.setResizable(false);
		
        Stage stage = (Stage) OKBut.getScene().getWindow();
        stage.close();

    }
    
    public void exit()
    {
    	
    	System.exit(0);
    	
    }


}