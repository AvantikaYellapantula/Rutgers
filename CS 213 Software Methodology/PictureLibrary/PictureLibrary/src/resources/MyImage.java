package resources;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javafx.collections.ObservableList;

public class MyImage implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MyImage [image=" + image + ", Caption=" + Caption + ", date=" + date + ", tags=" + tags + "]";
	}

	private File image;
	private String Caption;
	private Date date;
	private ArrayList<tags> tags;
	
	/**
	 * @param image
	 */
	public MyImage(File image) {
		super();
		this.image = image;
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND,0);
		
		this.date = cal.getTime();
		
		tags=null;
		Caption=null;
	}
	
	/**
	 * @return the image
	 */
	public File getImage() {
		
		return image;
		
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(File image) {
		
		this.image = image;
		
	}

	/**
	 * @return the caption
	 */
	public String getCaption() {
		return Caption;
	}

	/**
	 * @param caption the caption to set
	 */
	public void setCaption(String caption) {
		
		Caption = caption;
		
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		
		return date;
		
	}

	/**
	 * @return the tags
	 */
	public ArrayList<tags> getTags() {
		
		return tags;
		
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(ArrayList<tags> tags) {
		
		this.tags = tags;
		
	}
	
}	



