package search;

import java.io.*;
import java.util.*;

/**
 * This class encapsulates an occurrence of a keyword in a document. It stores the
 * document name, and the frequency of occurrence in that document. Occurrences are
 * associated with keywords in an index hash table.
 * 
 * @author Sesh Venugopal
 * 
 */
class Occurrence {
	/**
	 * Document in which a keyword occurs.
	 */
	String document;
	
	/**
	 * The frequency (number of times) the keyword occurs in the above document.
	 */
	int frequency;
	
	/**
	 * Initializes this occurrence with the given document,frequency pair.
	 * 
	 * @param doc Document name
	 * @param freq Frequency
	 */
	public Occurrence(String doc, int freq) {
		document = doc;
		frequency = freq;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "(" + document + "," + frequency + ")";
	}
}

/**
 * This class builds an index of keywords. Each keyword maps to a set of documents in
 * which it occurs, with frequency of occurrence in each document. Once the index is built,
 * the documents can searched on for keywords.
 *
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in descending
	 * order of occurrence frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash table of all noise words - mapping is from word to itself.
	 */
	HashMap<String,String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashMap<String,String>(100,2.0f);
	}
	
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.put(word,word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeyWords(docFile);
			mergeKeyWords(kws);
		}
		
	}

	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeyWords(String docFile) throws FileNotFoundException {
		HashMap<String,Occurrence> keyWORDs= new HashMap<String,Occurrence>();
		try{
		Scanner scan = new Scanner(new File(docFile));
		while (scan.hasNext()) {
			String word=getKeyWord(scan.next());
			if(word!=null){
				if(!keyWORDs.containsKey(word)){
					Occurrence e= new Occurrence(docFile, 1);
					keyWORDs.put(word, e);}
				else keyWORDs.get(word).frequency++;
			}
		}
		return keyWORDs;}catch(FileNotFoundException e){throw new FileNotFoundException("File Not Found");}
	}
	
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeyWords(HashMap<String,Occurrence> kws) {
		for(String x: kws.keySet()){
			Occurrence o=kws.get(x); 
			if(!keywordsIndex.containsKey(x)){
				ArrayList<Occurrence> list= new ArrayList<Occurrence>();
				list.add(o);
				keywordsIndex.put(x, list);}
			else{
				keywordsIndex.get(x).add(o);
				insertLastOccurrence(keywordsIndex.get(x));}				
		}
	}
	
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * TRAILING punctuation, consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyWord(String word) {
		String hold=word.toLowerCase();
		boolean p=false, f=false;
		int sp=-1;
		
		for(int i=0; i<hold.length(); i++){
			char ch=hold.charAt(i);
			if(Character.isLetter(ch) && p==true)
				return null;	
			if(!Character.isLetter(ch))
				p=true;
			if(!Character.isLetter(ch) && f==false){
				f=true;
				sp=i;}
		}
		
		if(sp==0)
			return null;
		String NW="";
		if(sp==-1)
			NW=hold;
		
		int holdstartpt=sp;
		if(sp>0){
			boolean okay=true;
			while(holdstartpt<hold.length() && okay){
				char ch=hold.charAt(holdstartpt);
				if(ch!='.' && ch!=',' && ch!='?' && ch!=':' && ch!=';' && ch!='!')
					okay=false;
				holdstartpt++;}
			if(okay)
				NW=hold.substring(0,sp);
			else
				return NW=null;
		}
		
		if(noiseWords.containsKey(NW))
			return null;
		
		return NW;
	}
	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * same list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion of the last element
	 * (the one at index n-1) is done by first finding the correct spot using binary search, 
	 * then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		
		int L=0, R=occs.size()-2, num=occs.get(occs.size()-1).frequency, M=(L+R)/2, midnumf=occs.get(M).frequency;
		ArrayList<Integer> x= new ArrayList<Integer>();
		
		if(occs.size()==1)
			return x=null;
		
		while(L<=R){
			M=(L+R)/2;
			x.add(M);
			midnumf=occs.get(M).frequency;
			if(midnumf==num)
				break;
			if(num>midnumf)
				R=M-1;
			if(num<midnumf)
				L=M+1;
		}
		
		Occurrence e= occs.get(occs.size()-1);
		
		if(midnumf==num || midnumf>num)
			occs.add(M+1, e);
		if(midnumf<num)
			occs.add(M, e);
		occs.remove(occs.size()-1);
		return x;
	}
	
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of occurrence frequencies. (Note that a
	 * matching document will only appear once in the result.) Ties in frequency values are broken
	 * in favor of the first keyword. (That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2
	 * also with the same frequency f1, then doc1 will appear before doc2 in the result. 
	 * The result set is limited to 5 entries. If there are no matching documents, the result is null.
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of NAMES of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matching documents,
	 *         the result is null.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		
		ArrayList<String> result1= new ArrayList<String>();
		
		if(!keywordsIndex.containsKey(kw1) && !keywordsIndex.containsKey(kw2))
			return result1=null;
		
		boolean searchone=false, searchtwo=false;
		int kw2size=0, kw1size=0;
		
		if(!keywordsIndex.containsKey(kw1)){
			searchtwo=true;
			kw2size=(keywordsIndex.get(kw2)).size();}
		if(!keywordsIndex.containsKey(kw2)){
			searchone=true;
			kw1size=(keywordsIndex.get(kw1)).size();}
		
		if(keywordsIndex.containsKey(kw2) && keywordsIndex.containsKey(kw1)){
			kw1size=(keywordsIndex.get(kw1)).size();
			kw2size=(keywordsIndex.get(kw2)).size();}
		
		int count=0, one=0, two=0;	
		
		while(searchone!=true && searchtwo!=true && count<10)
		{
				int kw1f=keywordsIndex.get(kw1).get(one).frequency, kw2f=keywordsIndex.get(kw2).get(two).frequency;
				if(kw1f>=kw2f){
					result1.add(keywordsIndex.get(kw1).get(one).document);
					count++;one++;}
				if(kw2f>kw1f){
					result1.add(keywordsIndex.get(kw2).get(two).document);
					count++;two++;}
				if(one==kw1size)
					searchtwo=true;
				if(two==kw2size)
					searchone=true;
		}
		
		if(count<10 && searchone && one<kw1size){
			while(count<10 && one<kw1size){
				result1.add(keywordsIndex.get(kw1).get(one).document);
				count++;one++;}
		}
		
		if(count<10 && searchtwo && two<kw2size){
			while(count<10 && two<kw2size){
				result1.add(keywordsIndex.get(kw2).get(two).document);
				count++;two++;}
		}
	
		ArrayList<String> result2= new ArrayList<String>();
		for(int i=0; i<result1.size(); i++){
			if(!result2.contains(result1.get(i)) && result2.size()<5)
				result2.add(result1.get(i));}
	
		return result2;
	}
}

