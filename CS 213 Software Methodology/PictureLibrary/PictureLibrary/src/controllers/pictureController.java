package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import resources.Album;
import resources.MyImage;
import resources.tags;
import resources.user;

public class pictureController extends Application
implements Initializable
{
	@FXML AnchorPane albumView;
	@FXML AnchorPane splitTop;
	@FXML TilePane List;
	@FXML Button addBut, deleteBut, openBut, addTagBut, removeTagBut, captionBut, copyBut, moveBut, exitBut;

	private static int index;
	private static Album myAlbum;
	private static user thisUser;
	private static ObservableList<Album> albums= FXCollections.observableArrayList();
	private static String curPic="";
	
	public pictureController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		//System.out.println(thisUser.getUserName()+"|"+albums.get(index).getName()+"|"+myAlbum.getName()+"|"+myAlbum.getImages());
		for (MyImage cur: myAlbum.getImages())
			List.getChildren().addAll(createImageView(cur.getImage(),cur.getCaption()));

		for(int i = 0; i<List.getChildren().size(); i++)
			TilePane.setMargin(List.getChildren().get(i), new Insets(5,5,5,5));
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
	}
	
	private int indexOfPic(String path){
		for (int i=0; i<List.getChildrenUnmodifiable().size(); i++){
			//System.out.println("|"+(List.getChildrenUnmodifiable().get(i)).getId());
			if ((List.getChildrenUnmodifiable().get(i)).getId().equals(path))
				return i;
		}
		return -1;
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
            imageView.setFitHeight(150);
            imageView.fitHeightProperty();
            imageView.fitWidthProperty();
            imageView.setId(imageFile.getAbsolutePath());
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                        if(mouseEvent.getClickCount() == 1){
                        	//System.out.println("||"+((ImageView)mouseEvent.getSource()).getId());
                        	curPic=((ImageView)mouseEvent.getSource()).getId();
                        }
                        
                        if(mouseEvent.getClickCount() == 2){
                        	curPic=((ImageView)mouseEvent.getSource()).getId();
                        	openACT();
                        }
                    }
                }
            });
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        
        pane.setId(imageFile.getAbsolutePath());
        pane.getChildren().add(imageView);
	    pane.getChildren().add(cap);
	    
        return pane;
    }
		
	public void closeACT(){
		//saves albums list to file
		try {
			FileOutputStream fout = new FileOutputStream("./src/dat/" + thisUser.getUserName()+".dat");
    		ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(new ArrayList<Album>(albums));
			oos.close();
		} catch (IOException e) {e.printStackTrace();}
		
		Stage primaryStage = new Stage();
		Parent root = null;
        
		albumController.setUser(thisUser);
        primaryStage.setTitle("Albums Viewer");
        String programPath = "/forms/albums.fxml";

        try{
        	root = FXMLLoader.load(getClass().getResource(programPath));
        	
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent event) {

                    // consume event
                    event.consume();
                    
                }
            });
            
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

	public void captionACT(){
		if (curPic.equals("")){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("warning");
				alert.setHeaderText("user didn't select an picture.");
				alert.setContentText("Please select an picture");

				alert.showAndWait();
				return;
		}
			
		TextInputDialog dialog = new TextInputDialog(((MyImage)albums.get(index).getImages().get(indexOfPic(curPic))).getCaption());
		dialog.setTitle("Set caption for "+List.getChildren().get(indexOfPic(curPic)).getId());
		dialog.setHeaderText("Please enter the caption:");
		dialog.setContentText("caption:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			((MyImage)albums.get(index).getImages().get(indexOfPic(curPic))).setCaption(result.get());
		}
		else 
			System.out.println("error");
	}
	
	public void openACT(){
		if (curPic.equals("")){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("warning");
			alert.setHeaderText("user didn't select an picture.");
			alert.setContentText("Please select an picture");

			alert.showAndWait();
			return;
	}
		
		Stage primaryStage = new Stage();
		Parent root = null;
        
		picViewerController.setAlbum(myAlbum, indexOfPic(curPic));
        primaryStage.setTitle("Picture Viewer");
        String programPath = "/forms/picViewer.fxml";

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
	}
	
	private boolean checkExtension(File inFile)
	{
		if(inFile.toString().contains(".jpg"))
			
			return true;
		
		else if(inFile.toString().contains(".png"))
			
			return true;
		
		else if(inFile.toString().contains(".mpg"))
			
			return true;
		
		else if(inFile.toString().contains(".pcx"))
			
			return true;
		
		else if(inFile.toString().contains(".bmp"))
			
			return true;
		
		return false;
		
	}
	
	public void addACT(){
		
		try{
			FileChooser dialog = new FileChooser();
			dialog.setTitle("Link image file");
			
			File result = dialog.showOpenDialog(null);
			if (result.exists() && this.checkExtension(result)){
				MyImage tempPic = new MyImage(result);
				tempPic.setTags(new ArrayList<tags>());
				albums.get(index).addImage(tempPic);
	            List.getChildren().addAll(createImageView(result,""));
				TilePane.setMargin(List.getChildren().get((List.getChildren().size())-1), new Insets(5,5,5,5));
			}
			else 
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error");
				alert.setHeaderText("There was an error with that add.");
				alert.setContentText("Please make sure that was a valid image file");
		
				alert.showAndWait();	
		
			}
		}
		
		catch(NullPointerException e)
		{
			
			//Do Nothing, as this only comes up when you hit cancel
			
		}
	}
	
	public void deleteACT(){
		if (curPic.equals("")){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("warning");
			alert.setHeaderText("user didn't select an picture.");
			alert.setContentText("Please select an picture");

			alert.showAndWait();
			return;
	}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Please Confirm!");
    	alert.setHeaderText("Delete "+List.getChildren().get(indexOfPic(curPic)).getId()+" Album?!?!");
    	alert.setContentText("Are you sure?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		albums.get(index).removeImage(indexOfPic(curPic));
    		List.getChildren().remove(indexOfPic(curPic));
    	}
	}
	
	public void addTagACT(){
		if (curPic.equals("")){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("warning");
			alert.setHeaderText("user didn't select an picture.");
			alert.setContentText("Please select an picture");

			alert.showAndWait();
			return;
	}
		
		ArrayList<String> choices = new ArrayList<String>();
		choices.add("Name");
		choices.add("Place");
		choices.add("Event");
		choices.add("Other");

		ChoiceDialog<String> dialog = new ChoiceDialog<>("Name", choices);
		dialog.setTitle("Choose tag");
		dialog.setHeaderText("Pick a tag to assign to this image");
		dialog.setContentText("Tag:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			TextInputDialog dialog1 = new TextInputDialog("New Tag");
			dialog1.setTitle("New Tag for "+List.getChildren().get(indexOfPic(curPic)).getId());
			dialog1.setHeaderText("Please enter the name of the new Tag:");
			dialog1.setContentText("Tag:");
	
			Optional<String> result1 = dialog1.showAndWait();
			if (result1.isPresent()){
				tags temp=new tags(result.get(),result1.get());
				((MyImage)albums.get(index).getImages().get(indexOfPic(curPic))).getTags().add(temp);
			}
			else 
				System.out.println("error");	
		}
	}
	
	public void removeTagACT(){
		if (curPic.equals("")){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("warning");
			alert.setHeaderText("user didn't select an picture.");
			alert.setContentText("Please select an picture");

			alert.showAndWait();
			return;
	}
		
		ChoiceDialog<Object> dialog = new ChoiceDialog<>("", ((MyImage)albums.get(index).getImages().get(indexOfPic(curPic))).getTags().toArray());
		dialog.setTitle("Choice Dialog");
		dialog.setHeaderText("Look, a Choice Dialog");
		dialog.setContentText("Choose your letter:");

		// Traditional way to get the response value.
		Optional<Object> result = dialog.showAndWait();
		if (result.isPresent()){
			System.out.println(result.get());
			((MyImage)albums.get(index).getImages().get(indexOfPic(curPic))).getTags().remove(result.get());
		}
	}
	
	public void copyACT(){
		if (myAlbum.getImages().size()<=0){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("warning");
			alert.setHeaderText("user didn't select an picture.");
			alert.setContentText("Please select an picture");

			alert.showAndWait();
			return;
	}
		
		//pick album
		ChoiceDialog<Object> dialog = new ChoiceDialog<>("", albums.toArray());
		dialog.setTitle("Choice Dialog");
		dialog.setHeaderText("Look, a Choice Dialog");
		dialog.setContentText("Choose your letter:");

		Optional<Object> result = dialog.showAndWait();
		if (result.isPresent()){
			//System.out.println(result.get());
			((Album)result.get()).addImage(albums.get(index).getImages().get(indexOfPic(curPic)));
		}
		
	}
	
	public void moveACT(){
		if (myAlbum.getImages().size()<=0){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("warning");
			alert.setHeaderText("user didn't select an picture.");
			alert.setContentText("Please select an picture");

			alert.showAndWait();
			return;
	}
		
		//pick album
		ChoiceDialog<Object> dialog = new ChoiceDialog<>("", albums.toArray());
		dialog.setTitle("Choice Dialog");
		dialog.setHeaderText("Look, a Choice Dialog");
		dialog.setContentText("Choose your letter:");

		Optional<Object> result = dialog.showAndWait();
		if (result.isPresent()){
			//System.out.println(result.get());
			((Album)result.get()).addImage(albums.get(index).getImages().get(indexOfPic(curPic)));
			deleteACT();
		}
	}
	
	public static void setAlbum(ObservableList<Album> alIn, int i, user user){albums = alIn; index=i; myAlbum=albums.get(i); thisUser=user;}
	

}
