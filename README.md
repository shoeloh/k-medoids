# ABOUT
In this program I implement and test the k-medoids algorithm.  

## Tweets Clustering using k-medoids  
Twitter provides a service for posting short messages. In practice, many of the tweets are very similar to each other and can be clustered together. By clustering similar tweets together, we can generate a more concise and organized representation of the raw tweets, which will be very useful for many Twitter-based applications (e.g., truth discovery, trend analysis, search ranking, etc.) In this code, I cluster tweets by utilizing Jaccard Distance metric and the K-medoids clustering algorithm.  

## Objectives:  
Compute the similarity between tweets using the Jaccard Distance metric.  
Cluster tweets using the K-medoids clustering algorithm.  

## Introduction to Jaccard Distance:  
The Jaccard distance measures dissimilarity between two sample sets (A and B). It is defined as the difference of the sizes of the union and the intersection of two sets divided by the size of the union of the sets.  

For example, consider the following tweets:  
Tweet A: the long march  
Tweet B: ides of march  
|A ∩ B | = 1 and |A U B | = 5, therefore the distance is 1 – (1/5)  

In this code, a tweet can be considered as an unordered set of words such as {a,b,c}. By "unordered", we mean that {a,b,c}={b,a,c}={a,c,b}=…  

A Jaccard Distance Dist(A, B) between tweet A and B has the following properties:  
· It is small if tweet A and B are similar.  
· It is large if they are not similar.  
· It is 0 if they are the same.  
· It is 1 if they are completely different (i.e., no overlapping words).

Here is the reference for more details about Jaccard Distance:  
https://en.wikipedia.org/wiki/Jaccard_index  

## Objective:
Implement the tweet clustering function using the Jaccard Distance metric and K-medoids clustering algorithm to cluster redundant/repeated tweets into the same cluster. Note that while the K-medoids algorithm is proved to converge, the algorithm is sensitive to the k initial selected cluster centroids (i.e., seeds) and the clustering result is not necessarily optimal on a random selection of seeds. In this code, I provide you with a list of K initial centroids that have been tested to generate good results.

## Inputs to the K-means Algorithm:  
(1) The number of clusters K (default to K=25).  
(2) A real world dataset (Tweets.json) sampled from Twitter during the Boston Marathon Bombing event in April 2013 that contains 251 tweets. The tweet dataset is in JSON format  
(3) The list of initial centroids (InitialSeeds.txt). Note that each element in this list is the tweet ID (i.e., the id field in JSON format) of the tweet in the dataset.

# COMPILING, INSTALLATION AND RUNNING  
Program was written in Java  

Program files are Kmedoids.java, Cluster.java, Point.java, Kmedoids_main.java  

## Compiling:  
Can compile with the following command:  
$ javac -g Cluster.java Kmedoid.java Point.java Kmedoid_main.java  
 
Or by running the compile_script.bash  
$ ./compile_script.bash  

## How to run the code and a genric run command statement along with an example:  
You can run the compiled code via the command in the unpacked directory:  
$ java Kmedoid_main 25 data/InitialSeeds.txt data/Tweets.json output.txt  

Or you can run the code using the script: run_script.bash  

Generic run command: ./run_script.bash  

Example using directory structure as unpacked:  
$ ./run_script.bash  

## RESULTS  

Results are shown in the Results.pdf file  

Runtime prints are sent to the console.  

Detailed output is written to file.  

## LICENSE  
[MIT License](https://github.com/shoeloh/k-medoids/blob/master/LICENSE)  

