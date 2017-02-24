/*
	 
	 Christopher Pellegrino & Jared Patriarca
	 
	 SongLib
	 10/3/2016
	 Song Library GUI Design & Implementation
 
*/

package songs;

import java.io.Serializable;

public class Song implements Serializable {
String title, artist, album, year;
Song next, prev;

public Song(String title, String artist, String album, String year){
	this.title=title;
	this.artist=artist;
	this.album=album;
	this.year=year;
}

public String toString() { 
    return this.title;
 } 
}
