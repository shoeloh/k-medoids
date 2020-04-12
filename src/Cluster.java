/**
 * 
 * @author Shane Zabel
 *
 */
import java.util.ArrayList;
import java.util.List;
 
public class Cluster {
	/*
	 * Cluster data contains 	1) a list of points in the cluster (List<Point>)
	 * 							2) the cluster centroid (Point)
	 * 							3) the cluster id (Int)
	 */
	private List<Point> clusterPoints;
	private Point centroid;
	private int clusterId;
	
	/********************************************************************************
	 * Init methods
	 *******************************************************************************/
	//Default constructor
	public Cluster() {
		List<Point> clusterPoints = new ArrayList<Point>();
		this.setPoints(clusterPoints);
		this.setCentroid(null);
		this.setId(-1);
	}
 
	/********************************************************************************
	 * Set methods
	 *******************************************************************************/
	//Standard setters
	public void addPoint(Point point) {
		clusterPoints.add(point);
	}
	public void setPoints(List<Point> points) {
		this.clusterPoints = points;
	}
	public void setCentroid(Point centroid) {
		this.centroid = centroid;
	}
	public void setId(int ID) { 
		this.clusterId = ID;
	}
	public void clear() {
		clusterPoints.clear();
	}
	
	/********************************************************************************
	 * Get methods
	 *******************************************************************************/
	//Standard getters
	public List<Point> getPoints() {
		return this.clusterPoints;
	}
	public Point getCentroid() {
		return this.centroid;
	}
	public int getId() {
		return this.clusterId;
	}
	
	/********************************************************************************
	 * Print methods
	 *******************************************************************************/
	//Standard printer
	protected void printCluster() {
		System.out.println("[Cluster: " + this.getId() + "]");
		System.out.println("[Centroid: " + this.getCentroid().getId() + ": " + this.centroid.getTweet() + "]");
		System.out.println("[Points:");
		for(Point p : this.getPoints() ) {
			System.out.println( p.getId() + ":[" + p.getTweet() + "]" );
		}
		System.out.println("]");
	}
 
	/********************************************************************************
	 * Support methods
	 *******************************************************************************/
	//NA
	
}//End of Cluster class
