/**
 * 
 * @author Shane Zabel
 *
 */
import java.util.ArrayList;
import java.util.List;

import java.lang.String;

public class Point {
	/*
	 * Point data contains 		1) the tweet (String)
	 * 							3) the id of the point (String)
	 * 							3) the id of the cluster the point is associated with (Int)
	 */
    private String tweet;
    private String pointId;
    private int cluster_number;
 
	/********************************************************************************
	 * Init methods
	 *******************************************************************************/
    //Default constructor
	public Point() {
	    this.setTweet("");
	    this.setId("");
	    this.setCluster(-1);
	}
    //Initialize a Point with tweet and ID data
    public Point(String x, String y)
    {
        this.setTweet(x);
        this.setId(y);
    }//End of the Point class
    
	/********************************************************************************
	 * Set methods
	 *******************************************************************************/
	//Standard setters
    public void setTweet(String x) {
        this.tweet = x;
    }
    public void setId(String y) {
        this.pointId = y;
    }
    public void setCluster(int n) {
        this.cluster_number = n;
    }
    
	/********************************************************************************
	 * Get methods
	 *******************************************************************************/
	//Standard getters
    public String getTweet()  {
        return this.tweet;
    }
    public String getId() {
        return this.pointId;
    }
    public int getCluster() {
        return this.cluster_number;
    }
    
	/********************************************************************************
	 * Print methods
	 *******************************************************************************/
	//Standard printers
    protected void printPoint() {
		System.out.println("Tweet: " + this.getTweet() );
		System.out.println("Point ID: " + this.getId() );
		System.out.println("Cluster Number: " + this.getCluster() );
	}
    protected void printPointId() {
		System.out.println("Point ID: " + this.getId() );
	}
    protected void printTweet() {
		System.out.println("Point ID: " + this.getTweet() );
	}
    
	/********************************************************************************
	 * Support methods
	 *******************************************************************************/
    //createPoints returns a List of points from an input Matrix
    protected static List<Point> createPoints(String[ ][ ] data, int number) {
    	//Set delimiter
    	String delims = "\": ";
    	//Create an List of Points of size equal to number of line of data
    	List<Point> points = new ArrayList<Point>(number);
    	//For every line of data
    	for(int i = 0; i < number; i++) {
    		//Tokenize tweet string based on delimiter
    		String[] tokens1 = data[i][0].split(delims);
    		//Remove leading and trailing " from tweet
        	String myTweet = tokens1[1].substring(1, tokens1[1].length()-1);
        	
    		//Tokenize ID string based on delimiter
        	String[] tokens2 = data[i][5].split(delims);
        	//Convert ID to int
    		String myId = tokens2[1];
    		
    		//Create a point using the parsed tweet and tweet ID
    		Point newPoint = new Point( myTweet , myId );
    		//Add the new point to the list of points
    		points.add(newPoint);
    	}
    	//Return the list of points
    	return points;
    }//End of the createPoints class
    
    //Calculates the Jaccard distance between a point and another given point.
    protected double distance(Point b) {
    	//Set delimiter
    	String delims = " ";
 
    	//Parse this tweet and input tweet based on delimiter into String arrays
    	String[] s1=this.getTweet().split(delims);
    	String[] s2=b.getTweet().split(delims);
    	
    	//Calculate the union of the two strings. Only count unique instances
    	//Create an ArrayList of Strings to hold the union words
    	ArrayList<String> union = new ArrayList<>();
    	// Add elements of s1
    	for(String s : s1){ if(!union.contains(s) ){ union.add(s); } }
    	// Conditionally add elements of s2
    	for(String s : s2){ if(!union.contains(s) ){ union.add(s); } }
    	
    	//Calculate the intersection of the two strings. Only count unique instances
    	//Create an ArrayList of Strings to hold the intersection words
    	ArrayList<String> intersection = new ArrayList<>();
    	// For each element of s1
    	for(String sa : s1){
    		// Add to intersection if contained in s2
    		for(String sb : s2){ if(sb.equals(sa) && !intersection.contains(sa) ){ intersection.add(sa); } }
    	}
    	
    	//Create union and intersection count variables and set to size of the respective ArrayList (# of words)
    	int unionCount=union.size();
    	int intersectionCount=intersection.size();
    	
    	//Calculate and return Jaccard distance
    	if(unionCount!=0){
    		
    		/** For Debugging
    		for(int i=0;i<s1.length;i++){
    			System.out.print(s1[i] + " ");
    		}
    		System.out.println("");
    		for(int i=0;i<s2.length;i++){
    			System.out.print(s2[i] + " ");
    		}
    		System.out.println("");
    		System.out.print(intersection);
    		System.out.println("");
    		System.out.print(union);
    		System.out.println("");
    		System.out.print("Intersection count: ");
    		System.out.println(intersectionCount);
    		System.out.print("Union count: ");
    		System.out.println(unionCount);
    		System.out.print("Jaccard distance: ");
    		double ans=1-(double)intersectionCount/(double)unionCount;
    		System.out.println(ans);
    		**/
    		
    		return ( 1-(double)intersectionCount/(double)unionCount );
    	}else{
    		System.out.println("WARNING! unionCount = 0. Should not get here." );
        	return 0.0;
    	}
    }//End of the Kmedoids class
}
