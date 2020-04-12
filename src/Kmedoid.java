/**
 * 
 * @author Shane Zabel
 *
 */
import java.util.ArrayList;
import java.util.List;

import java.lang.Math;

public class Kmedoid {
 
	/*
	 * Kmedoids data contains 	1) a list of points in the data
	 * 				2) a list of clusters
	 * 				3) the number of clusters
	 * 				4) the number of points
	 */

    private List<Point> points;
    private List<Cluster> clusters;
    private int numClusters;
    private int numPoints;
    private double SSE;
    
	/********************************************************************************
	 * Init methods
	 *******************************************************************************/
    //Kmedoids is the default constructor
	public Kmedoid() {
		List<Point> newPoints = new ArrayList<Point>();
	    List<Cluster> newClusters = new ArrayList<Cluster>(); 
	    this.setPoints(newPoints);
	    this.setClusters(newClusters);
	    this.numClusters = 0;
	    this.numPoints = 0;
	    this.SSE = 0.0;
	}//End of the Kmedoids class
	
    //init initializes Kmedoids data based on input of the k number of clusters and file location of the data
	public void init(String k, List<String> selectedCentroids, String[][] data) {
		//Set number of clusters based on input
	    this.numClusters = Integer.parseInt(k);   
	    
	    //Create Points from data
	    this.points = Point.createPoints(data,this.numPoints);		
		
	    //Create Clusters with Centroids from selectedCentroids
	    for (int i = 0; i < this.getNumClusters() ; i++) {
	    	Cluster cluster = new Cluster();
	    	cluster.setId(i);
	    	Point centroid = new Point("","");
    		centroid = getPointFromId(selectedCentroids.get(i));
	    	cluster.setCentroid(centroid);
	    	this.clusters.add(cluster);
	    }
	    //Assign points to clusters now that centroids are set
        this.assignCluster();  
		
      //Print cluster info
        System.out.println("#################");
        System.out.println("Initial Cluster Centroid Assignments:");
        plotClusters();
        this.SSE();
    	System.out.print("SSE: ");
    	System.out.println( this.getSSE() );
    	
	}//End of the init class
	
	/********************************************************************************
	 * Set methods
	 *******************************************************************************/
	//Standard setters
	public void setPoints(List<Point> newPoints) {
		this.points = newPoints;
	}
	public void setClusters(List<Cluster> newClusters) {
		this.clusters = newClusters;
	}
	public void setNumClusters(int numClusters) {
		this.numClusters = numClusters;
	}
	public void setNumPoints(int numPoints) {
		this.numPoints = numPoints;
	}
	public void setSSE(double sse) {
		this.SSE = sse;
	}
	//clearClusters clears all data in all clusters
	private void clearClusters() {
	    for(Cluster cluster : clusters) {
	    	cluster.clear();
	    }
	}//End of the clearClusters class

	/********************************************************************************
	 * Get methods
	 *******************************************************************************/
	//Standard getters
	public List<Point> getPoints() {
		return(this.points);
	}
	public List<Cluster> getClusters() {
		return(this.clusters);
	}
	public int getNumClusters() {
		return(this.numClusters);
	}
	public int getNumPoints() {
		return(this.numPoints);
	}
	public double getSSE() {
		return(this.SSE);
	}
	public Point getPointFromId(String Id){
		for(Point point : this.points){
			if(point.getId().equals(Id)){
				return point;
			}
		}
	return null;
	}
	//getCentroids gets a list of all centroid points 
	public List<Point> getCentroids() {
	    List<Point> centroids = new ArrayList<Point>(this.numClusters);
	    for(Cluster cluster : clusters) {
	    	Point aux = cluster.getCentroid();
	    	Point point = new Point(aux.getTweet() ,aux.getId());
	    	centroids.add(point);
	    }
	    return centroids;
	}//End of the getCentroids class
		
	/********************************************************************************
	 * Print methods
	 *******************************************************************************/
	//plotClusters prints out cluster data
	protected void plotClusters() {
	    for (int i = 0; i < this.getNumClusters() ; i++) {
	    	Cluster c = this.getClusters().get(i);
	    	c.printCluster();
	    }
	}//End of the plotClusters class
	
	/********************************************************************************
	 * Protected Support methods
	 *******************************************************************************/
	//calculate performs the iterated KMedoids calculation on the data
	protected void calculate() {
		boolean finish = false;
	    int iteration = 0;
	        
	    // Add in new data, one at a time, recalculating centroids with each new one. 
	    while(!finish) {
	    	//Clear cluster state
	        clearClusters();
	        //Assign points to the closest cluster
	        this.assignCluster();  
	        //Calculate the SSE for this centroid assignment
	        this.SSE();
	        double variationLast = this.getSSE();
	        
	        //Calculate new centroids.
	        calculateCentroids();
	        //Assign points to the closest cluster
	        this.assignCluster();  
	        //Calculate the SSE for this centroid assignment
	        this.SSE();
	        double variationCurrent = this.getSSE();
	        
	        //Calculate the difference of SSE's for last and current centroids
	        double variation = Math.abs(variationCurrent-variationLast);

	        //Print cluster info
	        System.out.println("#################");
	        System.out.println("Iteration: " + (iteration+1) );
	    	System.out.println("SSE Last: " + variationLast);
	    	System.out.println("SSE Current: " + variationCurrent);
	        System.out.println("SSE Variation: " + variation);
	        plotClusters();
	        this.SSE();
	    	System.out.print("SSE: ");
	    	System.out.println( this.getSSE() );
	    	
	        //Stop if new centroid does not move the SSE enough
	        if(variation < 0.00001) {
	        	finish = true;
	        }
	        //Or stop after 25 iterations
	        iteration++;
	        if(iteration == 25) {
	        	finish = true;
	        }
	    }
	}//End of the calculate class
	
	/********************************************************************************
	 * Private Support methods
	 *******************************************************************************/
	//assignCluster assigns the data points to the cluster whose centroid it is nearest to
	private void assignCluster() {
		//Initialize variables
	    int cluster = 0;                 
	    double maxDist = 0.0; 
	    
		//Calculate maximum distance between any two points	in the data	
		//The distance should never be greater than this value
		for(int i=0;i<points.size();i++){
			Point currentPoint1=points.get(i);
			for(int j=0;j<points.size();j++){
				Point currentPoint2=points.get(j);
				if(currentPoint1.distance(currentPoint2)>maxDist){
					maxDist=currentPoint1.distance(currentPoint2);
				}
			}
		}

    	//Set minimum distance to maximum distance
	    double min = maxDist; 

	    //For each point, find closest cluster centroid
	    double distance=0.0;
	    for(Point point : points) {
	        min = maxDist;
	        for(int i = 0; i < this.getNumClusters() ; i++) {
	            Cluster c = clusters.get(i);
	            distance = point.distance(c.getCentroid() );
	            if(distance < min){
	                min = distance;
	                cluster = i;
	            }
	        }
	        //Assign closest cluster centroid to each point
	        point.setCluster(cluster);
	        //Add the point to the cluster list
	        clusters.get(cluster).addPoint(point);
	    }
	}//End assignCluster class
	
	//calculateCentroids sets the centroid location for all clusters based on the points assigned to it
	private void calculateCentroids() {
	    for(Cluster cluster : clusters) {
	    	//Get a list of all points in the current cluster 
	        List<Point> list = cluster.getPoints();
	            
	      //Initialize maximum distance between any two points in the cluster            
		    double maxDist = 0.0; 
		    
			//Calculate maximum distance between any two points	in the cluster	
			for(int i=0;i<list.size();i++){
				Point currentPoint1=list.get(i);
				for(int j=0;j<list.size();j++){
					Point currentPoint2=list.get(j);
					if(currentPoint1.distance(currentPoint2)>maxDist){
						maxDist=currentPoint1.distance(currentPoint2);
					}
				}
			}

	    	//Scale the maximum distance based on the number of points
			//The summed distance should never be greater than this value
		    maxDist = maxDist*list.size(); 

		    //Find the point with minimum distance to other points
		    //Initialize the target centroid to the first point
		    Point newCentroid=list.get(0);
		    //For every point in the list of points belonging to the cluster
		    for(Point point : list) {
		    	//Iniitialize distance variables
		    	double distance=0.0;
	    		double minDist = maxDist;
	    		//Calculate the distance of the point to all other points in the same cluster and sum all distances
		    	for( Point point2 : list){
		    		distance += point.distance(point2);
		    	}
		    	//If the summed distance calculated is the minimum, set the point as the target centroid
		    	if(distance<minDist){
		    		minDist=distance;
		    		newCentroid=point;
		    	}
		    }
	    	//Set the centroid of the cluster to the point with minimum distance to all other points in its cluster
	        cluster.setCentroid(newCentroid);
	    }
	}//End of calculateCentroids class
	
	//SSE calculates the Sum Squared Error for all the data given its clustering
	private void SSE() {         
	    //Initialize variables for distance and SSE
		double distanceSquared = 0.0; 
	    double sserror=0.0;
	    
	    //For every point in the data
	    for(Point point : this.getPoints()) {
	    	//Run through every cluster
	        for(int i = 0; i < this.getNumClusters() ; i++) {
	        	//If point belongs to cluster
	        	if(point.getCluster()==i){
	        		//Get cluster info
	        		Cluster c = clusters.get(i);
	        		//Calculate the Jaccard distance between the point and the cluster centroid
	        		distanceSquared = Math.pow( point.distance(c.getCentroid() ) , 2);
	        		//Add this distance to the SSE
	        		sserror+=distanceSquared;
	        	}
	        }
	    }
	    //Set the SSE to the calculated value
	    this.setSSE(sserror);
	}//End of the SSE class
	
}//End of the Kmeans class
