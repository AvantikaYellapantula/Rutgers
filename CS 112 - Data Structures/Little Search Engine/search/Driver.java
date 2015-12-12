package search;

import java.io.FileNotFoundException;


public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LittleSearchEngine lse=new LittleSearchEngine();
		try {
			lse.makeIndex("docs.txt", "noisewords.txt");
			System.out.println(lse.top5search("deep", "world"));
			System.out.println(lse.getKeyWord("we're"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
