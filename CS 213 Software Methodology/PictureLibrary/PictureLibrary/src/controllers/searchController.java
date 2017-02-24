package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import resources.Album;
import resources.MyImage;
import resources.tags;
import resources.user;

public class searchController extends Application
implements Initializable
{
	@FXML TilePane list;
	@FXML TextField value, caption;
	@FXML DatePicker dateStart, dateEnd;
	@FXML Button search, makeAlbum, close;
	@FXML ChoiceBox<String> tag;
	
	private static ObservableList<Album> albums= FXCollections.observableArrayList();
	private static ArrayList<MyImage> results=new ArrayList<MyImage>();
	private static user thisUser;
	
	public searchController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		ObservableList<String> choices = FXCollections.observableArrayList();
		choices.add("Name");
		choices.add("Place");
		choices.add("Event");
		choices.add("Other");
		
		tag.setItems(choices);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
	}
	
	public void createAlbumACT(){
		if (results.size()<=0){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("warning");
			alert.setHeaderText("user didn't select an picture.");
			alert.setContentText("Please search for pictures");

			alert.showAndWait();
			return;
	}
		TextInputDialog dialog = new TextInputDialog("New Name");
		dialog.setTitle("New Album");
		dialog.setHeaderText("Please enter the name of the new album:");
		dialog.setContentText("Album Name:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			Album tempAlbum = new Album(thisUser, result.get());
			tempAlbum.setImages(new ArrayList<MyImage>());
			for (MyImage i:results){
				if (i!=null)//TODO fix this null i error
					tempAlbum.addImage(i);
			}
			albums.add(tempAlbum);
		}
		else 
			System.out.println("error");
	}
	
	public void searchACT(){
		if (list.getChildren().size()>=1){
		for (int i=0; i<list.getChildren().size(); i++)
			list.getChildren().remove(i);
		}
		
		//System.out.println("|"+tag.getSelectionModel().getSelectedIndex()+"|"+value.getText()+"|"+caption.getText()+"|"+dateStart.getValue()+"|"+dateEnd.getValue()+"|");
		
		if (tag.getSelectionModel().getSelectedIndex()!=-1 && value.getText().equals("")){
			//System.out.println("tag");
			
			if (albums!=null){
				for (Album curAlb:albums){
					if (curAlb!=null){
						for (MyImage curImg:curAlb.getImages()){
							if (curImg!=null){
								for (tags curTag:curImg.getTags()){
									if (curTag!=null && curTag.equals(tag.getSelectionModel().getSelectedItem())){
										list.getChildren().addAll(createImageView(curImg.getImage(),""));
										results.add(curImg);
									}
								}
							}
						}
					}
				}
			}
		}
		
		else if (!caption.getText().equals("")){
			//System.out.println("caption");
			
			if (albums!=null){
				for (Album curAlb:albums){
					if (curAlb!=null){
						for (MyImage curImg:curAlb.getImages()){
							if(curImg.getCaption()!=null && curImg.getCaption().equals(caption.getText())){
								list.getChildren().addAll(createImageView(curImg.getImage(),""));
								results.add(curImg);
							}
						}
					}
				}
			}
		}
		
		else if (dateStart.getValue()!=null && dateEnd.getValue()!=null){
			//System.out.println("dates");
			
			if (albums!=null){
				for (Album curAlb:albums){
					if (curAlb!=null){
						for (MyImage curImg:curAlb.getImages()){
							if (curImg!=null){ 
								Date ds=Date.from(Instant.from(dateStart.getValue().atStartOfDay(ZoneId.systemDefault())));
								Date de=Date.from(Instant.from(dateEnd.getValue().atStartOfDay(ZoneId.systemDefault())));
								
								if (curImg.getDate().compareTo(ds)>=0 && curImg.getDate().compareTo(de)<=0){
									list.getChildren().addAll(createImageView(curImg.getImage(),""));
									results.add(curImg);
								}
							}
						}
					}
				}
			}
		}
		
		dateStart.setValue(null);
		dateEnd.setValue(null);
		caption.setText("");
		tag.getSelectionModel().select(-1);
		value.setText("");
	}
	
	public void closeACT(){	
		//saves albums list to file
		try {
			FileOutputStream fout = new FileOutputStream("./src/dat/" + thisUser.getUserName()+".dat");
    		ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(new ArrayList<Album>(albums));
			oos.close();fout.close();
		} catch (IOException e) {e.printStackTrace();}
		
		Stage primaryStage = new Stage();
		Parent root = null;
        
		primaryStage.setTitle("Album Viewer");
        String programPath = "/forms/albums.fxml";
        albumController.setUser(thisUser);

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
	
        Stage thisStage = (Stage) close.getScene().getWindow();
        thisStage.close();
	}
	
	private StackPane createImageView(final File imageFile, String capL) {
        // DEFAULT_THUMBNAIL_WIDTH is a constant you need to define
        // The last two arguments are: preserveRatio, and use smooth (slower)
        // resizing

		StackPane pane = new StackPane();
   	 	pane.setAlignment(Pos.BOTTOM_CENTER);
   	 	
   	 	Label cap=new Label(capL);
        
   	 	ImageView imageView = null;
        try {
            final Image image = new Image(new FileInputStream(imageFile), 150, 0, true,
                    true);
            imageView = new ImageView(image);
            imageView.setFitWidth(150);
            imageView.setId(imageFile.getAbsolutePath());
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        
        pane.setId(imageFile.getAbsolutePath());
        pane.getChildren().add(imageView);
	    pane.getChildren().add(cap);
	    
        return pane;
    }

	public static void setAlbum(ObservableList<Album> alIn, user i){albums=alIn; thisUser=i;}
}
