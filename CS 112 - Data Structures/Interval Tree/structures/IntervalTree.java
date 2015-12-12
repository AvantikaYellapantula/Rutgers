package structures;

import java.util.*;

/**
 * Encapsulates an interval tree.
 * 
 * @author runb-cs112
 */
public class IntervalTree {
	
	/**
	 * The root of the interval tree
	 */
	IntervalTreeNode root;
	
	/**
	 * Constructs entire interval tree from set of input intervals. Constructing the tree
	 * means building the interval tree structure and mapping the intervals to the nodes.
	 * 
	 * @param intervals Array list of intervals for which the tree is constructed
	 */
	public IntervalTree(ArrayList<Interval> intervals) {
		
		// make a copy of intervals to use for right sorting
		ArrayList<Interval> intervalsRight = new ArrayList<Interval>(intervals.size());
		for (Interval iv : intervals) {
			intervalsRight.add(iv);
		}
		
		// rename input intervals for left sorting
		ArrayList<Interval> intervalsLeft = intervals;
		
		// sort intervals on left and right end points
		Sorter.sortIntervals(intervalsLeft, 'l');
		Sorter.sortIntervals(intervalsRight,'r');
		
		// get sorted list of end points without duplicates
		ArrayList<Integer> sortedEndPoints = Sorter.getSortedEndPoints(intervalsLeft, intervalsRight);
		System.out.println(sortedEndPoints);//TODO take this out!!!
		// build the tree nodes
		root = buildTreeNodes(sortedEndPoints);
		
		// map intervals to the tree nodes
		mapIntervalsToTree(intervalsLeft, intervalsRight);
	}
	
	/**
	 * Builds the interval tree structure given a sorted array list of end points.
	 * 
	 * @param endPoints Sorted array list of end points
	 * @return Root of the tree structure
	 */
	public static IntervalTreeNode buildTreeNodes(ArrayList<Integer> endPoints) {
		// COMPLETE THIS METHOD
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE PROGRAM COMPILE
		Queue<IntervalTreeNode> QueueT = new Queue<IntervalTreeNode>();
		
		for (int x=0; x<endPoints.size(); x++){
			IntervalTreeNode temp = new IntervalTreeNode(endPoints.get(x),endPoints.get(x),endPoints.get(x));
			temp.leftIntervals = new ArrayList<Interval>();
			temp.rightIntervals = new ArrayList<Interval>();
			QueueT.enqueue(temp);
		}
		for (int w=0; w<QueueT.size; w++){
			IntervalTreeNode x = QueueT.dequeue();
			QueueT.enqueue(x);
		}
		int Size = QueueT.size;

		while (Size > 0){
			if (Size != 1){
				int temps = Size;
				while (temps > 1){
					IntervalTreeNode Tree1 = QueueT.dequeue();
					IntervalTreeNode Tree2 = QueueT.dequeue();
					float x = (Tree1.maxSplitValue+Tree2.minSplitValue)/(2);
					IntervalTreeNode N = new IntervalTreeNode(x, Tree1.minSplitValue, Tree2.maxSplitValue);
					N.leftIntervals = new ArrayList<Interval>();
					N.rightIntervals = new ArrayList<Interval>();
					N.leftChild = Tree1;
					N.rightChild = Tree2;
					QueueT.enqueue(N);
					temps = temps-2;
				}
				if (temps == 1){
					IntervalTreeNode temporary = QueueT.dequeue();
					QueueT.enqueue(temporary);
				}
				Size = QueueT.size;
			}
			else return QueueT.dequeue();
		}
		return QueueT.dequeue();
	}
	
	/**
	 * Maps a set of intervals to the nodes of this interval tree. 
	 * 
	 * @param leftSortedIntervals Array list of intervals sorted according to left endpoints
	 * @param rightSortedIntervals Array list of intervals sorted according to right endpoints
	 */
	public void mapIntervalsToTree(ArrayList<Interval> leftSortedIntervals, ArrayList<Interval> rightSortedIntervals) {
		// COMPLETE THIS METHOD
		for (Interval x : leftSortedIntervals){
			MP(x, root, true);
		}
		for (Interval x : rightSortedIntervals){
			MP(x, root, false);
		}
		return;
	}
	
	private void MP(Interval x, IntervalTreeNode r, boolean left){
		if (x.contains(r.splitValue)){
			if (left)
				r.leftIntervals.add(x);
			else 
				r.rightIntervals.add(x);
			return;
		}
		
		if (r.splitValue < x.leftEndPoint)
			MP(x, r.rightChild, left);
		else 
			MP(x, r.leftChild, left);
		
	}
	
	/**
	 * Gets all intervals in this interval tree that intersect with a given interval.
	 * 
	 * @param q The query interval for which intersections are to be found
	 * @return Array list of all intersecting intervals; size is 0 if there are no intersections
	 */
	public ArrayList<Interval> findIntersectingIntervals(Interval q) {
		// COMPLETE THIS METHOD
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE PROGRAM COMPILE
		return finder(q,root);
		}
		
		private ArrayList<Interval> finder(Interval INT, IntervalTreeNode R){
			ArrayList<Interval> resultant = new ArrayList<Interval>();

			if (R == null)
				return resultant;
			
			float splitVal = R.splitValue;
								
			if (INT.contains(splitVal)){
				for (Interval x : R.leftIntervals){
					resultant.add(x);
				}
				resultant.addAll(finder(INT,R.rightChild));
				resultant.addAll(finder(INT,R.leftChild));
			}
			
			else if (splitVal < INT.leftEndPoint){
				int i = R.rightIntervals.size()-1;
				while (i >= 0 && (R.rightIntervals.get(i).intersects(INT))){
					resultant.add(R.rightIntervals.get(i));
					i = i-1;
				}
				
				resultant.addAll(finder(INT,R.rightChild));
			}
			
			else if (splitVal > INT.rightEndPoint){
				int i = 0;
				while ((i < R.leftIntervals.size()) && (R.leftIntervals.get(i).intersects(INT))){
					resultant.add(R.leftIntervals.get(i));						
					i = i+1;
				}
				resultant.addAll(finder(INT,R.leftChild));
			}
			return resultant;
		}
	/**
	 * Returns the root of this interval tree.
	 * 
	 * @return Root of interval tree.
	 */
	public IntervalTreeNode getRoot() {
		return root;
	}
}

