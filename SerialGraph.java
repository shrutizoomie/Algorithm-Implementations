package graphs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

public class SerialGraph {
	
	private static final String INPUT_FILE_NAME = "src/matinput.txt";
	public static int[][] adjMatrix = null;
	public static int numVertices = 0;
	public static int numEdges = 0;
	public static int edgesCounted = 0;
	
	public static HashMap<Integer, Integer> vertexSet = null;
	public static HashMap<Integer, Integer> independentSet = new HashMap<Integer, Integer>();
	public static HashMap<Integer, Integer> sSet = new HashMap<Integer, Integer>();
	public static boolean[] markVertex = null;
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException 
	{
		adjMatrix = getAdjMatrix(INPUT_FILE_NAME);
		
		System.out.println("print the adjacency matrix");
		
		for(int k = 0; k<adjMatrix.length; k++)
	    {
	    	for (int l = 0; l < adjMatrix.length; l++)
	    	{
	    		System.out.print(adjMatrix[k][l] + "\t");
	    	}
	    	System.out.print("\n");
	    }  

		createVertexSet();
		markVertex = new boolean[numVertices]; 
		
		while (!vertexSet.isEmpty())
		{
			/**
			 * For all v in V do in parallel
	         If d(v) = 0 then add v to I and delete v from V.
	         else mark v with probability 1/(2d(v)).
			 */
			for (int i = 0; i < numVertices; i++)
			{
				int vertexDegree = degree(i);	
				if (vertexDegree == 0)
				{
					// add v to I
					independentSet.put(i, 0);
					
					// delete v from V
					vertexSet.remove(i);
						
				}
				else
				{
					// mark v with probability 1 / 2 d (v)
					
					System.out.print("Mark V with Probability");
					
					float probability = findProbability(i);
					System.out.println("probability : " + probability);
					
					markTheVertex(probability, i);
				}
			}
			
			/**
			 * For all (u,v) in E do in parallel
			         if both u and v are marked
			             then unmark the lower degree vertex.
			 */
//			 while (edgesCounted < numEdges)
//			   {
				   for (int i = 0; i < numVertices - 1; i++)
				   {
					   for  (int j = 0; j < numVertices - 1; j++)
					   {
						   if (1 == adjMatrix[i][j])
						   {
							   edgesCounted++;
							   
							   if ((true == markVertex[i]) && (true == markVertex[j]))
								{
										int degreeOfU = degree(i);
										int degreeOfV = degree(j);	
										
										//System.out.println("Degree of V : " + degreeOfV);
									
									if (degreeOfU < degreeOfV)
									{
										markVertex[i] = false;
									}
									else if (degreeOfU > degreeOfV)
									{
										markVertex[j] = false;	
									}
							}
						}  
					  }
				   }
//			   }
			 
		 		for (int v = 0; v < numVertices; v++)
		 		{
		 			System.out.println("After Value of Mark Vertex : " + v +  markVertex[v]);	
		 		}
		 		
		 		/**
		 		 * For all v in V do in parallel
         			if v is marked then add v to S
		 		 */
		 		for (int i = 0; i < numVertices; i++)
		 		{
		 			if (true == markVertex[i])
		 			{
		 				// add v to S
		 				sSet.put(i, 0);
		 			}
		 		}
		 		
		 		/**
		 		 * I := I union S
		 		 */
		 		for (Integer keyFromTwo : sSet.keySet()) {
					independentSet.put(keyFromTwo, sSet.get(keyFromTwo));
					
					System.out.println();
					
					
				}
		 		
		 		/**
		 		 * Delete S union Gamma(S) from V and all incident edges from E 
		 		 */
		 		deleteSUnionGammaS();
			}
		
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
			System.out.println("Value to delete is : " + valueToDelete);
			
			
			// Delete i from V
			vertexSet.remove(valueToDelete);
			
			// Delete gamma i from V
			
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
	 * This API is used to mark a vertex based on its probability
	 * @param probability
	 */
	public static void markTheVertex(float probability, int vertex)
	{
		try
		{
			Random rand = new Random();
			float genRandom = rand.nextFloat() * 1;
			if (genRandom < probability)
			{
				markVertex[vertex] = true;	
			}
//			
//			for (int i = 0; i < markVertex.length; i++)
//			{
//				System.out.println("Marked Value : " + i + markVertex[i]);
//			}
				
		}
		catch (Exception ie)
		{
			ie.printStackTrace();
		}	
	}
	
	/**
	 * This API is used to create a vertex set
	 * @return
	 */
	public static void createVertexSet()
	{
		//System.out.println("createVertexSet Invoked");
		vertexSet = new HashMap<Integer, Integer>();
		for (int i = 0; i < numVertices; i++)
		{
			vertexSet.put(i, degree(i));
		}
		
		for (int i = 0; i < vertexSet.size(); i++)
		{
			System.out.println(vertexSet.get(i));
		}
	}
	
	/**
	 * This API will return the degree of a vertex given its index
	 * @param index
	 * @return
	 */
	public static int degree(int index)
	{
    	int numNeighbors = 0;
    	
    	// Scan the row corresponding to vertex in the adjacency
    	// matrix, counting the number of neighbors
//    	for (int j = 0; j <= index; j++)
//    	{
//    		if(0 != adjMatrix[index][j])
//    		{
//    			numNeighbors++;
//    		}
//    	}
//	    		    
//	    for (int j = index+1; j < numVertices; j++)
//	    {
//	    	if(0 != adjMatrix[j][index])
//	    	{
//	    		numNeighbors++;
//	    	}	
//	    }	
	    
	    for ( int k = 0; k < numVertices; k++) {
	    	if (adjMatrix[index][k] != 0) {
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
	public static int[][] getAdjMatrix(String filePath) throws FileNotFoundException, IOException {
        FileReader reader = new FileReader(filePath);
        BufferedReader buffer = new BufferedReader(reader);
        ArrayList<String> list = new ArrayList<String>();
        String line = buffer.readLine();
        while (line != null) {
            list.add(line);
            line = buffer.readLine();
        }
        int row = list.size();
        int col = list.get(0).split(" ").length;
        
        numVertices = row;
        
        System.out.println("Number of Vertices is : " + numVertices);
        
        
        int[][] result = new int[row][col];
        int i = 0;
        for (String str : list) {
            String[] arr = str.split(" ");
            int j = 0;
            for (String s : arr) {
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
        			//setEdge(j, k);
        			// set the other edge to zero
        			//result[k][j] = 0;
        			numEdges++;
        		}
        	}
        }

//        numEdges = numEdges / 2;
        System.out.println("Number of Edges is : " + numEdges); 
     	
        buffer.close();
        return result;
    }
}
