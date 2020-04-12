/**
 * 
 * @author Shane Zabel
 *
 */
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class Kmedoid_main {
	//Main
	public static void main(String[] args){
		//Print out command line args 
		System.out.print("Arg 0 = ");
		System.out.println(args[0]);
		System.out.print("Arg 1 = ");
		System.out.println(args[1]);
		System.out.print("Arg 2 = ");
		System.out.println(args[2]);
		System.out.print("Arg 3 = ");
		System.out.println(args[3]);
		
		//Create a Kmedoid object
    	Kmedoid kmedoid = new Kmedoid();
    	
    	//Create K, selectedCentroid and data objects for command line args
    	String K = args[0];
    	List<String> selectedCentroids = new ArrayList<String>();
    	String[ ][ ] data = new String [ 251 ][ 10 ]; 
    	
    	//Read in data from file
    	data=readData(args[2]);
    	
    	//Set number of data points
    	int rows=data.length;
    	kmedoid.setNumPoints(rows);
    	
    	//Read in starting centroid locations from file
    	selectedCentroids=readCentroids(args[1]);
    	
    	//Initialize Kmedoid object based on K, starting centroids, and data
    	kmedoid.init(K,selectedCentroids,data);
    	
    	//Perform KMedoid calculation
    	kmedoid.calculate();
    	
    	//Print results to output file
    	outputToFile(kmedoid,args[3]);
    	
	}//End of main class
	
	/********************************************************************************
	 * Support methods
	 *******************************************************************************/
    //readData reads data from a file into a String matrix and returns it
	private static String[][] readData(String inputFile) {
		//Initialize file and data objects
		File inFile = null;
		FileReader fr = null;
		BufferedReader br = null;
		String line = null;
		String[ ][ ] data = new String[ 251 ][ 10 ] ; 
			
		//Create File named inFile based on inputFile=arg[2]
		if (inputFile!=null) {
			inFile = new File(inputFile);
		} else {
			System.err.println("Invalid input file argument.");
			System.exit(1);
		}
			
		//Read File into FileReader object
		try{
			fr = new FileReader(inFile);
		}catch(FileNotFoundException fnfe) {
			System.out.println(inFile.getAbsolutePath());
			System.out.println(inFile.exists());
			System.out.println(inFile.canRead());
	           fnfe.printStackTrace();
		}
				
		//Parse FileReader object using BufferedReader with delimiter separators into the data String matrix
		//Set delimiter
    	String delims = ", \"";
		try{
			//Initialize BufferedReader and counter
			br = new BufferedReader(fr);
			int i=0;
			//For every line in the File
		    while ((line = br.readLine()) != null) {
		    	//Split line based on delimiter
		    	String[] values = line.split(delims);
		    	//No header so use all lines
		    	if(i!=-1){
		    		//Populate data String matrix
			    	data[i][0]=values[0];
			    	data[i][1]=values[1];
			    	data[i][2]=values[2];
			    	data[i][3]=values[3];
			    	data[i][4]=values[4];
			    	data[i][5]=values[5];
			    	data[i][6]=values[6];
			    	data[i][7]=values[7];
			    	data[i][8]=values[8];
			    	data[i][9]=values[9];
		    	}
		    	i++;
		    } 
		    //Close BufferedReader
		    br.close();
		}catch(IOException ioe) {
	        ioe.printStackTrace();
		}
	//Return data String matrix
	return data;
	}
	
    //readData reads data from a file into a List of Strings
	private static List<String> readCentroids(String inputFile) {
		//Initialize file and data objects
		File inFile = null;
		FileReader fr = null;
		BufferedReader br = null;
		String line = null;
		List<String> data = new ArrayList<String>(); 
			
		//Create File named inFile based on inputFile=arg[1]
		if (inputFile!=null) {
				inFile = new File(inputFile);
		} else {
				System.err.println("Invalid input file argument.");
				System.exit(1);
		}
			
		//Read File into FileReader object
		try{
			fr = new FileReader(inFile);
		}catch(FileNotFoundException fnfe) {
			System.out.println(inFile.getAbsolutePath());
			System.out.println(inFile.exists());
			System.out.println(inFile.canRead());
	        fnfe.printStackTrace();
		}
				
		//Parse FileReader object using BufferedReader with delimiter separators into the String List
		//Set delimiter
    	String delims = ",";
		try{
			//Initialize BufferedReader and counter
			br = new BufferedReader(fr);
			int i=0;
			//For every line in the File
		    while ((line = br.readLine()) != null) {
		    	//Split line based on delimiter
		    	String[] values = line.split(delims);
		    	//No header so use all lines
		    	if(i!=-1){
		    		//Populate String List
			    	data.add(values[0]);
		    	}
		    	i++;
		    } 
		    //Close BufferedReader
		    br.close();
		}catch(IOException ioe) {
	        ioe.printStackTrace();
		}
	//Return data String List
	return data;
	}
	
	//outputToFile prints results to file specified by outFile
	private static void outputToFile(Kmedoid kmedoid, String outFileName) {
		//Initialize file and data objects
		File outFile = null;
		FileWriter fw = null;
		BufferedWriter bw = null;

		//Create File named outFile based on outFileName
		if (outFileName!=null) {
			outFile = new File(outFileName);
		} else {
			System.err.println("Invalid output file argument.");
			System.exit(1);
		}
					
		//Create FileWriter for outFile
		try{
			fw = new FileWriter(outFile);
		}catch(IOException ioe) {
			System.out.println(outFile.getAbsolutePath());
			System.out.println(outFile.exists());
			System.out.println(outFile.canWrite());
			ioe.printStackTrace();
		}
			
		//Write to outFile 
		try{
			//Initialize BufferedReader and counter
			bw = new BufferedWriter(fw);
	        int counter = 0;
			//Write header line
			bw.write("<cluster-id>	 <List of points ids separated by comma>");
	        bw.write("\n");
	        //Write cluster info for each cluster
		    for(int i = 0; i < kmedoid.getNumClusters(); i++) {
		    	//Initialize String to write out
		        String line = "";
		        //Get current cluster in the for loop
		        Cluster c = kmedoid.getClusters().get(i);
		        //Add the cluster ID to the line to write out
		        line += c.getId() + 1;
		        line += "\t\t";
		        //Write out the ID for every point in the current cluster
		        for( Point point : kmedoid.getPoints() ) {
		        	//Only write out if point belongs to current cluster
		    	    if(point.getCluster()==c.getId() ){
		    	    	//Formatting
		    	        if(counter == 0){
		    	        line += " ";	
		    	        }else{
		    	        line += ", ";
		    	        }
		    	        //Add ID of point to line to write out
		    	        line += point.getId();
		    	        //Increment counter
		    	        counter++;
		    	    }
		    	}
		        //Write the line to file
		        bw.write(line);
		        bw.write("\n");
		        //Reset counter
		        counter = 0;
		    }
		    //Close BufferedReader
		    bw.close();
		}catch(IOException ioe) {
	        ioe.printStackTrace();
		}
	}//End of the outputToFile class
		
}//End of the Kmedoid_main class
