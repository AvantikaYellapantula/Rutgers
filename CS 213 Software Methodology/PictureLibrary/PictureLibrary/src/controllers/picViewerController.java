package controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import resources.Album;
import resources.tags;

public class picViewerController extends Application
implements Initializable
{
	@FXML ListView<tags> tags;
	@FXML Label date, caption;
	@FXML Button prev, next, close;
	@FXML ImageView image;

	private static Album myAlbum;
	private static int index;
	
	public picViewerController() {
		//TODO search: make album - jared will finish this
		//TODO disable close X button at top right of frame for all frames
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		Image i=null;
		try {
			i = new Image(new FileInputStream(myAlbum.getImage(index)));
		} catch (FileNotFoundException e) {e.printStackTrace();}
        image.setImage(i);
        image.setStyle("-fx-background-color: BLACK");
        image.setFitHeight(300);//stage.getHeight() - 10);
        image.setPreserveRatio(true);
        image.setSmooth(true);
        image.setCache(true);
        
        date.setText(myAlbum.getImages().get(index).getDate().toString());
        caption.setText(myAlbum.getImages().get(index).getCaption());
        tags.setItems(FXCollections.observableArrayList(myAlbum.getImages().get(index).getTags()));
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
	}
	
	public void closeACT(){	
        Stage thisStage = (Stage) next.getScene().getWindow();
        thisStage.close();
	}

	public void nextACT(){
		index++;
		if (index>=myAlbum.getImages().size())
			index--;
		
		Image i=null;
		try {
			i = new Image(new FileInputStream(myAlbum.getImage(index)));
		} catch (FileNotFoundException e) {e.printStackTrace();}
        image.setImage(i);
        
        date.setText(myAlbum.getImages().get(index).getDate().toString());
        caption.setText(myAlbum.getImages().get(index).getCaption());
        tags.setItems(FXCollections.observableArrayList(myAlbum.getImages().get(index).getTags()));
	}
	
	public void prevACT(){
		index--;
		if (index<0)
			index++;
		
		Image i=null;
		try {
			i = new Image(new FileInputStream(myAlbum.getImage(index)));
		} catch (FileNotFoundException e) {e.printStackTrace();}
        image.setImage(i);
        
        date.setText(myAlbum.getImages().get(index).getDate().toString());
        caption.setText(myAlbum.getImages().get(index).getCaption());
        tags.setItems(FXCollections.observableArrayList(myAlbum.getImages().get(index).getTags()));
	}
	
	public static void setAlbum(Album alIn, int i){myAlbum=alIn; index=i;}
}
