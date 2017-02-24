
/*
	 
	 Christopher Pellegrino & Jared Patriarca
	 
	 SongLib
	 10/3/2016
	 Song Library GUI Design & Implementation
 
*/

package songs;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class Controller implements Initializable
{
	@FXML TextField Title, Artist, Album, Year;
    @FXML Button Save, SaveAdd, Edit, Add, Delete, Exit, Cancel;
    @FXML ListView<Song> List;
    ObservableList<Song> songs;
    	
    @SuppressWarnings("unchecked")
	public void initialize(URL url, ResourceBundle resourceBundle)
    {

    	try 
    	{
    	
    		FileInputStream fout = new FileInputStream("MyLibrary.songs");
    		@SuppressWarnings("resource")
			ObjectInputStream ois = new ObjectInputStream(fout);
			songs=FXCollections.observableArrayList((ArrayList<Song>) ois.readObject());
			
			
		
    	} 
    	
    	catch (ClassNotFoundException | IOException e) 
    	{
    				
    		e.printStackTrace();
    	
    	
    	}
    	

    	List.setItems(songs.sorted());
    	
    	if(!List.getItems().isEmpty())
	    	List.getSelectionModel().selectFirst();
    	List.refresh();
    	
    	
    	//onListSelection();

    	
    	
    }
    
    @SuppressWarnings("unchecked")
	private void sortIt()
    {
    	
    	List.setItems(songs.sorted());
    	songs.setAll(List.getItems());
    	


    }
    
    
    @FXML public void onListSelection()//(ActionEvent event)
    {
    	if(!List.getSelectionModel().isEmpty()){
    		
	    	Title.setText(songs.get(List.getSelectionModel().getSelectedIndex()).title);
	    	Artist.setText(songs.get(List.getSelectionModel().getSelectedIndex()).artist);
	    	Album.setText(songs.get(List.getSelectionModel().getSelectedIndex()).album);
	    	Year.setText(songs.get(List.getSelectionModel().getSelectedIndex()).year);
    	}
    }

    @FXML public void Edit(ActionEvent event){
    	//setting details
    	Title.setText(songs.get(List.getSelectionModel().getSelectedIndex()).title);
    	Artist.setText(songs.get(List.getSelectionModel().getSelectedIndex()).artist);
    	Album.setText(songs.get(List.getSelectionModel().getSelectedIndex()).album);
    	Year.setText(songs.get(List.getSelectionModel().getSelectedIndex()).year);
    	
    	
    	//Make lines editable
    	
    	Title.setEditable(true);
    	Artist.setEditable(true);
    	Album.setEditable(true);
    	Year.setEditable(true);
    	
    	//changing controls
    	Save.setVisible(true);
    	Cancel.setVisible(true);
    	Add.setDisable(true);
    	Delete.setDisable(true);
    	Edit.setDisable(true);
    }
    @FXML public void Add(ActionEvent event){
    	//setting details
    	Title.setText("ENTER TITLE");
    	Artist.setText("ENTER ARTIST");
    	Album.setText("ENTER ALBUM");
    	Year.setText("ENTER YEAR");
    	
    	//set editable
    	
    	Title.setEditable(true);
    	Artist.setEditable(true);
    	Album.setEditable(true);
    	Year.setEditable(true);
    	
    	//changing controls
    	SaveAdd.setVisible(true);
    	Cancel.setVisible(true);
    	Add.setDisable(true);
    	Delete.setDisable(true);
    	Edit.setDisable(true);
    	

    }
    @FXML public void Delete(ActionEvent event){
    	    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Please Confirm!");
    	alert.setHeaderText("Delete Song?!?!");
    	alert.setContentText("Are you sure?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		
    		int i = List.getSelectionModel().getSelectedIndex();
    		songs.remove(i);
    		List.getSelectionModel().selectPrevious();
    		List.refresh();
        	onListSelection();
    	
    	} 
    	
    	else {
    	    
    	}
    	
    	sortIt();
    	
    	

    	
    }
    @FXML public void Save(ActionEvent event){
    	//remove cases
    	int i=0;
    	Title.setText(Title.getText().toLowerCase());
    	Artist.setText(Artist.getText().toLowerCase());
    	Album.setText(Album.getText().toLowerCase());
    	Year.setText(Year.getText().toLowerCase());
    	Song cur = new Song("","","","");
    	
    	//checking if song exists
        for (i = 0; i<(songs.size()-1); i++){
        	cur=songs.get(i);
        	System.out.println(cur.album);
        	if (cur.title.equals(Title.getText()) && cur.artist.equals(Artist.getText())){
        		
               	Alert alert = new Alert(AlertType.CONFIRMATION);
            	alert.setTitle("Confirm Replacement");
            	alert.setHeaderText("Duplicate Song!");
            	alert.setContentText("Are you ok with this?");
            	
            	Optional<ButtonType> result = alert.showAndWait();
            	
            	if (result.get() == ButtonType.OK){
    	            
	        	    
	        	    cur = (new Song(Title.getText(), Artist.getText(),Album.getText(), Year.getText()));
	        	    songs.set(i, cur);
	        	    
	        	    List.refresh();
	        	    
	        		Alert changesMade = new Alert(AlertType.INFORMATION);
	        	    changesMade.setTitle("Changes Made");
	        	    changesMade.setHeaderText(null);
	        	    changesMade.setContentText(Title.getText() + " has been changed...");
	
	        	    changesMade.showAndWait();
	        	    
	        	    //sortIt();

	        	    List.getSelectionModel().select(songs.indexOf(cur));
	        	    
	            	//removing button
	            	Save.setVisible(false);
	            	Cancel.setVisible(false);
	            	Add.setDisable(false);
	            	Edit.setDisable(false);
	            	Delete.setDisable(false);
	            	
	            	//set Editable to false
	            	
	            	Title.setEditable(false);
	            	Artist.setEditable(false);
	            	Album.setEditable(false);
	            	Year.setEditable(false);
	            	
	        	    return;
	        	    
            	}
        	
	        	else {
	        		
	        		Alert noChanges = new Alert(AlertType.INFORMATION);
	        		noChanges.setTitle("Changes Canceled");
	        		noChanges.setHeaderText(null);
	        		noChanges.setContentText("No changes have been made...");
	
	        		noChanges.showAndWait();
	            	
	        		return;
	        	}
            	
        	}
        	
        	sortIt();
        }
    	
    	//error checking
        if (Title.getText().equals("enter title") || Title.getText().equals("")){
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Title Incorrect!");
        	alert.setHeaderText(null);
        	alert.setContentText("Fix the title!");

        	alert.showAndWait();        	return;
        }
        if (Artist.getText().equals("enter artist") || Artist.getText().equals("")){
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Artist Invalid");
        	alert.setHeaderText(null);
        	alert.setContentText("Fix the Artist!!");

        	alert.showAndWait();        	return;
        }
        if (Album.getText().equals("enter album") )
        	Album.setText("");
        if (Year.getText().equals("enter year") )
        	Year.setText("");
    	        
        //editing song info 
	    cur = (new Song(Title.getText(), Artist.getText(),Album.getText(), Year.getText()));
	    songs.add(cur);
 
    	
    	//removing button
    	Save.setVisible(false);
    	Cancel.setVisible(false);
    	Add.setDisable(false);
    	Edit.setDisable(false);
    	Delete.setDisable(false);
    	
    	//set Editable to false
    	
    	Title.setEditable(false);
    	Artist.setEditable(false);
    	Album.setEditable(false);
    	Year.setEditable(false);
    	
    	
    	List.refresh();
       	List.getSelectionModel().select(i);
    }
    @FXML public void Cancel(ActionEvent event){
    	//hides the controls
    	Save.setVisible(false);
    	SaveAdd.setVisible(false);
    	Cancel.setVisible(false);
    	Add.setDisable(false);
    	Delete.setDisable(false);
    	Edit.setDisable(false);
    	
    	//resets the detail fields
    	if (List.getSelectionModel().getSelectedIndex()!=-1){
	    	Title.setText(songs.get(List.getSelectionModel().getSelectedIndex()).title);
	    	Artist.setText(songs.get(List.getSelectionModel().getSelectedIndex()).artist);
	    	Album.setText(songs.get(List.getSelectionModel().getSelectedIndex()).album);
	    	Year.setText(songs.get(List.getSelectionModel().getSelectedIndex()).year);
    	}
    	
    	//set Editable to false
    	
    	Title.setEditable(false);
    	Artist.setEditable(false);
    	Album.setEditable(false);
    	Year.setEditable(false);
    }
    @FXML public void SaveAdd(ActionEvent event){ 
    	//remove cases
    	Title.setText(Title.getText().toUpperCase());
    	Artist.setText(Artist.getText().toUpperCase());
    	Album.setText(Album.getText().toLowerCase());
    	Year.setText(Year.getText().toLowerCase());
    	Song cur;
    	
    	    	
    	//error checking
        if (Title.getText().equals("enter title") || Title.getText().equals("")){
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Title incorrect!");
        	alert.setHeaderText(null);
        	alert.setContentText("Fix your title!!");

        	alert.showAndWait();        	
        	return;
        }
        if (Artist.getText().equals("enter artist") || Artist.getText().equals("")){
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Artist incorrect!");
        	alert.setHeaderText(null);
        	alert.setContentText("Fix your artist (Just don't put Justin Bieber)!");

        	alert.showAndWait();
        	
        	return;
        }
        if (Album.getText().equals("enter album") )
        	Album.setText("");
        if (Year.getText().equals("enter year") )
        	Year.setText("");

    	
    	//checking if song exists
        for (int i = 0; (i<songs.size()-1); i++){

        	cur = (songs.get(i));
        	
        	if (cur.title.equals(Title.getText()) && cur.artist.equals(Artist.getText())){

            	Alert alert = new Alert(AlertType.CONFIRMATION);
            	alert.setTitle("Confirm replacement");
            	alert.setHeaderText("Duplicate Song!");
            	alert.setContentText("Do you want to replace " + cur.title +"?");
            	
            	Optional<ButtonType> result = alert.showAndWait();
           	
            	if (result.get() == ButtonType.OK){
	            

	        	    Song tempSong = (new Song(Title.getText(), Artist.getText(),Album.getText(), Year.getText()));
	        	    songs.set(i, tempSong);

	        	    
	        		Alert changesMade = new Alert(AlertType.INFORMATION);
	        	    changesMade.setTitle("Changes Made");
	        	    changesMade.setHeaderText(null);
	        	    changesMade.setContentText(Title.getText() + " has been changed...");
	
	        	    changesMade.showAndWait();
	        	    
	    	        Song tSong = new Song(Title.getText(), Artist.getText(), Album.getText(), Year.getText());
	    	        songs.set(i, tSong);
	    		        
	    	    	SaveAdd.setVisible(false);
	    	    	Cancel.setVisible(false);
	    	    	Add.setDisable(false);
	    	    	Edit.setDisable(false);
	    	    	Delete.setDisable(false);
	    	    	
	    	    	//set editable to false
	    	    	Title.setEditable(false);
	    	    	Artist.setEditable(false);
	    	    	Album.setEditable(false);
	    	    	Year.setEditable(false);  
	    	    	
	    	    	sortIt();       	    

	        	    
	    	    	return;
	        	   
	        	    
            	} 
	        	else {
	        		
	        		Alert noChanges = new Alert(AlertType.INFORMATION);
	        		noChanges.setTitle("Changes Canceled");
	        		noChanges.setHeaderText(null);
	        		noChanges.setContentText("No changes have been made...");
	
	        		noChanges.showAndWait();
	        		
	        		return;
	        	}
	        }
        }
		    	        
        Song tSong = new Song(Title.getText(), Artist.getText(), Album.getText(), Year.getText());
        songs.add(tSong);
        sortIt();

        
    	SaveAdd.setVisible(false);
    	Cancel.setVisible(false);
    	Add.setDisable(false);
    	Edit.setDisable(false);
    	Delete.setDisable(false);
    	
    	//set editable to false
    	Title.setEditable(false);
    	Artist.setEditable(false);
    	Album.setEditable(false);
    	Year.setEditable(false); 
    }

    @FXML public void Exit(ActionEvent event){
    	//saves objects
    	try {
    		FileOutputStream fout = new FileOutputStream("MyLibrary.songs");
    		ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(new ArrayList<Song>(songs.sorted()));
			oos.close();
		} catch (IOException e) {e.printStackTrace();}
    	
    	//exits
        System.exit(0);
    }
}