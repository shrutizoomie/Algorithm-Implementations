/**

   COPYRIGHT (C) 2015 SJSU Student. All Rights Reserved.
   Public Class to Compute the Parallel MIS algorithm
   @author Shruti
   @version 1.01 03/06/2015
*/

//package graphs;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class is one of the Threads used to Mark a vertex based on probability 1/2d(v)
 * @author shruti_sharma1
 *
 */
class Loop1 extends Thread
{
	private Loop1 spawner1;
	private boolean done1;
	private int low;
	private int high;
	
	public Loop1(Loop1 spawner1, int low, int high) 
	{
		super();
		this.low = low;
		this.high = high;
		this.spawner1 = spawner1;
	}
	
	/**
	 When the thread start is called the run method is invoked
	  
	 The run methods implements the below logic
	 For all v in V do in parallel
	         If d(v) = 0 then add v to I and delete v from V.
	         else mark v with probability 1/(2d(v)).
	 @author shruti_sharma1
	 */
	public void run ()
	{
		Loop1 firstHalf = null;
		Loop1 secondHalf = null;
		int vertexDegree = 0;
		done1 = false;
		
		if (low == high)
		{
			try
			{
				vertexDegree = ParallelMIS.degree(low);
			}
			catch (ArrayIndexOutOfBoundsException ie)
			{
				ie.printStackTrace();
			}
			
			//If d(v) = 0 then add v to I and delete v from V.
			if (vertexDegree == 0)
			{
				ParallelMIS.independentSet.put(low, 0);
				ParallelMIS.vertexSet.remove(low);
			}
			//mark v with probability 1/(2d(v))
			else
			{
				float probability = ParallelMIS.findProbability(low);
				ParallelMIS.markVertex(probability, low);
			}
		}
		else
		{
			int mid = (low + high) / 2;
			firstHalf = new Loop1(this, low, mid);
			firstHalf.start();
			secondHalf = new Loop1(this, mid + 1, high);
			secondHalf.start();
		}
		   
		if (low != high)
		{
			firstHalf.sync();
			secondHalf.sync();
		}
	   done1 = true;
	}
	
	/**
	 * Sync the results after processing
	 */
	public synchronized void sync()
	{
		if (!done1)
		{
			try
			{
				wait(); 
			}
			catch (InterruptedException ie)
			{
				ie.printStackTrace();
			}
		}
	}	
}

/**
 * This class is one of the Threads used to Unmark a lower degree vertex if both are marked
 * @author shruti_sharma1
 *
 */
class Loop2 extends Thread
{
	private Loop2 spawner2;

	int edgesCounted = 0;	
	private int i = 0;
	private int j = 0;
	
	public Loop2(Loop2 spawner1) 
	{
		super();
		this.spawner2 = spawner2;
	}
	
	/**
	 When the thread start is called the run method is invoked
	 For all (u,v) in E do in parallel
	         if both u and v are marked
	             then unmark the lower degree vertex.
	 */
	public void run ()
	{
		ExecutorService service = Executors.newFixedThreadPool(ParallelMIS.numEdges);
		
		while (edgesCounted < ParallelMIS.numEdges)
		{
			for (i = 0; i < ParallelMIS.numVertices - 1; i++)
			{
				for  (j = 0; j < ParallelMIS.numVertices - 1; j++)
				{
					if (1 == ParallelMIS.adjMatrix[i][j])
					{
						edgesCounted++;
						service.execute(new Runnable()
						{
							@Override
							public void run() 
							{
								// check if both u and v are marked
								if ((true == ParallelMIS.markVertex[i]) && (true == ParallelMIS.markVertex[j]))
								{
									int degreeOfU = ParallelMIS.degree(i);
									int degreeOfV = ParallelMIS.degree(j);	
									
									// unmark the lower degree vertex
									if (degreeOfU < degreeOfV)
									{
										ParallelMIS.markVertex[i] = false;
									}
									else if (degreeOfU > degreeOfV)
									{
										ParallelMIS.markVertex[j] = false;	
									}
									else if (degreeOfU == degreeOfV)
									{
										ParallelMIS.markVertex[i] = false;
									}
								}
							}
						});
					}
				}
			}
		}
		service.shutdown();
	}
}


/**
 * This class is one of the Threads used to add marked vertices to the set S
 * @author shruti_sharma1
 *
 */
class Loop3 extends Thread
{
	private Loop3 spawner3;
	private boolean done3;
	
	private int low;
	private int high;
	
	public Loop3(Loop3 spawner3, int low, int high) 
	{
		super();
		this.spawner3 = spawner3;
		this.done3 = done3;
		this.low = low;
		this.high = high;
	}
	
	/**
	 * When the thread start is called the run method is invoked
		For all v in V do in parallel
	         if v is marked then add v to S
	 */
	public void run ()
	{
		Loop3 firstHalf = null;
		Loop3 secondHalf = null;
		
		done3 = false;
		
		if (low == high)
		{
			// if v is marked then add v to S
			if (true == ParallelMIS.markVertex[low])
			{
				ParallelMIS.sSet.put(low, 0);
			}
		}
		else
		{
			int mid = (low + high) / 2;
			firstHalf = new Loop3(this, low, mid);
			firstHalf.start();
			secondHalf = new Loop3(this, mid + 1, high);
			secondHalf.start();
		}
		   
		if (low != high)
		{
			firstHalf.sync();
			secondHalf.sync();
		}
	   
		done3 = true;
		if (spawner3 == null)
		{
//			for (int i = 0; i < ParallelMIS.sSet.size(); i++)
//			{
//				System.out.println(ParallelMIS.sSet.get(i));
//			}
		}
	}
	
	/**
	 * Sync the results after processing
	 */
	public synchronized void sync()
	{
		if (!done3)
		{
			try
			{
				wait(); 
			}
			catch (InterruptedException ie)
			{
				ie.printStackTrace();
			}
		}
	}
}

/**
 * This is the main class which will execute the Parallel MIS algorithm
 *
 * Input: G=(V,E)
 * Output: A maximal independent set I contained in V
	1. I := emptyset
	2. Repeat {
	   a) For all v in V do in parallel
	         If d(v) = 0 then add v to I and delete v from V.
	         else mark v with probability 1/(2d(v)).
	   b) For all (u,v) in E do in parallel
	         if both u and v are marked
	             then unmark the lower degree vertex.
	   c) For all v in V do in parallel
	         if v is marked then add v to S
	   d) I := I union S
	   e) Delete S union Gamma(S) from V and all incident edges from E 
	   } Until V is empty.
 * @return
 * @author shruti_sharma1
 *
 */
public class ParallelMIS
{
	private static final String INPUT_FILE_NAME = "src/matinput.txt";
	public static int[][] adjMatrix = null;
	public static int numVertices = 0;
	public static int numEdges = 0;
	public static Map<Integer,Integer> vertexSet = null;
	public static Map<Integer,Integer> independentSet = new HashMap<Integer,Integer>();
	public static Map<Integer,Integer> sSet = new HashMap<Integer,Integer>();
	public static boolean[] markVertex = null;
	
	public static void main(String[] args) throws FileNotFoundException, IOException 
	{
		if(args.length < 1)
	     {
	         System.out.println(
	            "Please enter the correct no of command line arguments");
	         return;
	     }
	     
		adjMatrix = getAdjMatrix(args[0]);
		
		System.out.println("Adjacency matrix is : ");
		
		for(int k = 0; k<adjMatrix.length; k++)
		{
	    	for (int l = 0; l < adjMatrix.length; l++)
	    	{
	    		System.out.print(adjMatrix[k][l] + "\t");
	    	}
	    	System.out.print("\n");
	    }  

		// create the set which has all the vertices
    	//System.out.println("Print the vertex set");
		createVertexSet();
    	
    	// find the degree of all the vertices
    	for (int i = 0; i < vertexSet.size(); i++)
    	{
    		int vDeg = degree((int) vertexSet.get(i));
	      	//System.out.println("Degree of vertex :" + vertexSet.get(i) + " : is : "+ vDeg );
    	}
    	
    	markVertex = new boolean[numVertices]; 
    	
    	while(!vertexSet.isEmpty())
    	{
    		Loop1 parent1 = new Loop1(null, 0, numVertices - 1);
	    	parent1.start();
	    	
	    	Loop2 parent2 = new Loop2(null);
	    	parent2.start();
	    	
	    	Loop3 parent3 = new Loop3(null, 0, numVertices - 1);
	    	parent3.start();
	    	
	    	//let all threads finish execution before finishing main thread
	        try 
	        {
	        	parent1.join();
	        	parent2.join();
	        	parent3.join();
	        } 
	        catch (InterruptedException e) 
	        {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    	
	    	/**
	 		 * I := I union S
	 		 */
	 		for (Integer keyFromTwo : sSet.keySet()) 
	 		{
				independentSet.put(keyFromTwo, sSet.get(keyFromTwo));
				//System.out.println();
			}
	 		
	 		/**
	 		 * Delete S union Gamma(S) from V and all incident edges from E 
	 		 */
	 		deleteSUnionGammaS();
    	}
    	
    	//System.out.print("Independent set is : ");
		for (int indvert : independentSet.keySet())
		{
			System.out.println(indvert);
		}
	}
	
	/**
	 * This API is used to delete S union Gamma S from V and all incident Edges from E
	 */
	public static void deleteSUnionGammaS()
	{
		for (Integer valueToDelete : sSet.keySet())
		{
			// Delete i from V
			vertexSet.remove(valueToDelete);
			
			// find the neighbor nodes of vertex i
			ArrayList<Integer> neighborSet = findNeighbor(valueToDelete);
			
			// delete the neighbor nodes of vertex i
			for (int neigh : neighborSet)
			{
				vertexSet.remove(neigh);
			}
			
			for (int k = 0; k < numVertices; k++)
			{
				adjMatrix[valueToDelete][k] = 0;
				adjMatrix[k][valueToDelete] = 0;
			}
		}
	}
	
	/**
	 * This API is used to find neighbors of a given vertex
	 * @param vertex
	 * @return
	 */
	public static ArrayList<Integer> findNeighbor(int vertex)
	{
		ArrayList<Integer> neighborSet = new ArrayList<Integer>();
		for (int i = 0; i < numVertices; i++)
		{
			if (adjMatrix[vertex][i] == 1)
			{
				neighborSet.add(i);
			}
		}
		return neighborSet;
	}
	
	/**
	 * This API is used to mark a vertex based on its probability
	 * @param probability
	 */
	public static void markVertex(float probability, int vertex)
	{
		try
		{
			Random rand = new Random();
			float genRandom = rand.nextFloat() * 1;
			if (genRandom < probability)
			{
				markVertex[vertex] = true;	
			}
				
		}
		catch (Exception ie)
		{
			ie.printStackTrace();
		}	
	}
	
	/**
	 * This API will return the probability 
	 * @param vertex
	 * @return
	 */
	public static float findProbability(int vertex)
	{
		float probability = 0;
		int degreeOfVertex = 0; 

		try
		{
			degreeOfVertex = 2 * degree(vertex);		
		}
		catch (Exception ie)
		{
			System.out.println("exception in findProbability");
			ie.printStackTrace();
		}
		
		if (0 != degreeOfVertex)
		{
			probability = ((float)1 / (float)(degreeOfVertex));
		}
		
		return  probability;
	}
	
	/**
	 * This API is used to create a vertex set
	 * @return
	 */
	public static void createVertexSet()
	{
		vertexSet = new HashMap<Integer, Integer>();
		for (int i = 0; i < numVertices; i++)
		{
			vertexSet.put(i, degree(i));
		}
		
//		for (int i = 0; i < vertexSet.size(); i++)
//		{
//			System.out.println(vertexSet.get(i));
//		}
	}
	
	/**
	 * This API will return the degree of a vertex given its index
	 * @param index
	 * @return
	 */
	public static int degree(int index)
	{
    	int numNeighbors = 0;
    	
    	/* Scan the row corresponding to vertex in the adjacency
    	 matrix, counting the number of neighbors*/	
	    
	    for (int k = 0; k < numVertices; k++) 
	    {
	    	if (adjMatrix[index][k] != 0) 
	    	{
	    		numNeighbors++;
	    	}
	    }
	    
		return numNeighbors;	
	}
	
	/**
	 * This API is used to read the adjacency matrix from the input file
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static int[][] getAdjMatrix(String filePath) throws FileNotFoundException, IOException 
	{
        FileReader reader = new FileReader(filePath);
        BufferedReader buffer = new BufferedReader(reader);
        ArrayList<String> list = new ArrayList<String>();
        String line = buffer.readLine();
        
        while (line != null) 
        {
            list.add(line);
            line = buffer.readLine();
        }
        int row = list.size();
        int col = list.get(0).split(" ").length;
        
        numVertices = row;
        
        int[][] result = new int[row][col];
        
        int i = 0;
        for (String str : list) 
        {
            String[] arr = str.split(" ");
            int j = 0;
            for (String s : arr) 
            {
                result[i][j] = new Integer(s).intValue();
                j++;
            }
            i++;
        }

        for (int j = 0; j < numVertices; j++)
        {
        	for (int k = 0; k < numVertices; k++)
        	{
        		if (1 == result[j][k])
        		{
        			numEdges++;
        		}
        	}
        }
        buffer.close();
        return result;
    }
}
