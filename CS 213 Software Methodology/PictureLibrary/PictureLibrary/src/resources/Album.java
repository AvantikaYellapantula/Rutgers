package resources;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javafx.collections.ObservableList;

public class Album implements Serializable{

	private static final long serialVersionUID = 1L;

	private user albumOwner;
	private int totalPics=0;
	private String name;
	private Date Oldest, Earliest, Latest;
	private ArrayList<MyImage> images;
	
	/**
	 * @param name
	 */
	public Album(user userIn, String name) {
		super();
		albumOwner = userIn;
		this.name=name;
		Oldest = new Date();
	}	
	
	/**
	 * @param MyImage
	 * @return void
	 */
	public void addImage(MyImage i){
		images.add(i);
		totalPics++;
		
		if (Earliest==null ||i.getDate().compareTo(Earliest)<0)
			Earliest=i.getDate();
		
		if (Latest==null || i.getDate().compareTo(Latest)>0)
			Latest=i.getDate();
		
	}
	
	/**
	 * @param index
	 * @return void
	 */
	public void removeImage(int i){
		images.remove(i);
		totalPics--;
	}
	
	/**
	 * @return the name
	 */
	
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the Username
	 */
	public String getUserName() {
		return albumOwner.getUserName();
	}
	/**
	 * @param name the name to set
	 */
	public void setOwner(user userIn) {
		albumOwner = userIn;
	}
	/**
	 * @return the totalPics
	 */
	public int getTotalPics() {
		return totalPics;
	}
	/**
	 * @return the oldest
	 */
	public Date getOldest() {
		return Oldest;
	}
	/**
	 * @param oldest the oldest to set
	 */
	public void setOldest(Date oldest) {
		Oldest = oldest;
	}
	/**
	 * @return the earliest
	 */
	public Date getEarliest() {
		return Earliest;
	}
	/**
	 * @param earliest the earliest to set
	 */
	public void setEarliest(Date earliest) {
		Earliest = earliest;
	}
	/**
	 * @return the latest
	 */
	public Date getLatest() {
		return Latest;
	}
	/**
	 * @param latest the latest to set
	 */
	public void setLatest(Date latest) {
		Latest = latest;
	}
	/**
	 * @return the images
	 */
	public ArrayList<MyImage> getImages() {
		return images;
	}
	/**
	 * @param images the images to set
	 */
	public void setImages(ArrayList<MyImage> images) {
		this.images = images;
	}
	
	public File getImage(int i)
	{
		return images.get(i).getImage();
	}
	
	public String toString() {
		return "Album [Name=" + name + ", totalPics=" + totalPics + ", Oldest=" + Oldest + ", Earliest=" + Earliest
				+ ", Latest=" + Latest + "]";
	}
	
	
}
