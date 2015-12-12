package structures;

import java.util.ArrayList;

/**
 * This class is a repository of sorting methods used by the interval tree.
 * It's a utility class - all methods are static, and the class cannot be instantiated
 * i.e. no objects can be created for this class.
 * 
 * @author runb-cs112
 */
public class Sorter {

	private Sorter() { }
	
	/**
	 * Sorts a set of intervals in place, according to left or right endpoints.  
	 * At the end of the method, the parameter array list is a sorted list. 
	 * 
	 * @param intervals Array list of intervals to be sorted.
	 * @param lr If 'l', then sort is on left endpoints; if 'r', sort is on right endpoints
	 */
	public static void sortIntervals(ArrayList<Interval> intervals, char lr) {
		// COMPLETE THIS METHOD
		//left sort
		if (lr=='l'){
			for (int i = 0; i < intervals.size() - 1; i++)
	        {
	            int index = i;
	            for (int j = i + 1; j < intervals.size(); j++)
	                if (intervals.get(j).leftEndPoint < intervals.get(index).leftEndPoint) 
	                    index = j;
	      
	            Interval smallerNumber = intervals.get(index);  
	            intervals.set(index, intervals.get(i));
	            intervals.set(i, smallerNumber);
	        }
		}
		//right sort
		else {
			for (int i = 0; i < intervals.size() - 1; i++)
	        {
	            int index = i;
	            for (int j = i + 1; j < intervals.size(); j++)
	                if (intervals.get(j).rightEndPoint < intervals.get(index).rightEndPoint) 
	                    index = j;
	      
	            Interval smallerNumber = intervals.get(index);  
	            intervals.set(index, intervals.get(i));
	            intervals.set(i, smallerNumber);
	        }
		}
	}
	
	/**
	 * Given a set of intervals (left sorted and right sorted), extracts the left and right end points,
	 * and returns a sorted list of the combined end points without duplicates.
	 * 
	 * @param leftSortedIntervals Array list of intervals sorted according to left endpoints
	 * @param rightSortedIntervals Array list of intervals sorted according to right endpoints
	 * @return Sorted array list of all endpoints without duplicates
	 */
	public static ArrayList<Integer> getSortedEndPoints(ArrayList<Interval> leftSortedIntervals, ArrayList<Interval> rightSortedIntervals) {
		// COMPLETE THIS METHOD
		ArrayList<Integer> total=new ArrayList<Integer>();
		
		for (int i=0; leftSortedIntervals.size()>i; i++){
			boolean found=false;
			for (int k=0; total.size()>k; k++){
			if (total.get(k).equals(leftSortedIntervals.get(i).leftEndPoint))
				found=true;
			}
			if (!found)
				total.add(leftSortedIntervals.get(i).leftEndPoint);
		}
		for (int i=0; rightSortedIntervals.size()>i; i++){
			boolean found=false;
			for (int k=0; total.size()>k; k++){
			if (total.get(k).equals(rightSortedIntervals.get(i).rightEndPoint))
				found=true;
			}
			if (!found)
				total.add(rightSortedIntervals.get(i).rightEndPoint);
		}
		
		int max, i ,j, temp;
	     for (i = 0; i < total.size()-1; i++)
	      {
	        max = i;
	        for (j = i+1; j < total.size(); j++)
	        {
	          if (total.get(max).compareTo(total.get(j)) > 0)
	            max = j;
	        }
	        temp = total.get(i);
	        total.set(i, total.get(max));
	        total.set(max, temp);
	      }
		return total;
	}
}
