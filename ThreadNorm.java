/**
Program to make use of java threads to calculate Norm of a vector of integers
@author SJSU Student : Shruti
@version 1.01 2015/3/09
*/

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.Thread;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * 
 * Class to make use of java threads to compute the norm of a vector of integers that comes from a file.
 *
 */
public class ThreadNorm extends Thread
{
	private int low;
	private int high;
	private int finalResult = 0;
	private ThreadNorm spawner;
	private boolean done;
	 
	private static final String DELIMITER = " ";
	private static final int MINUS_ONE = -1;
	 
	//create a Vector object
	private static Vector<Integer> valueVector = new Vector<Integer>();

	/**
	 * Constructor for ThreadNorm function
	 */
	public ThreadNorm (ThreadNorm spawner, int low, int high)
	{
	   this.high = high;
	   this.low = low;
	   this.spawner = spawner;
	   done = false;
	   finalResult = 0;
	}

	/**
	 * When the thread start is called the run method is invoked
	 */
	public void run ()
	{
	   ThreadNorm firstHalf = null;
	   ThreadNorm secondHalf = null;
	   done = false;
	   
	   if (low == high)
	   {
	      int val = (int) valueVector.get(low);
	      finalResult = val * val;
	   }
	   else
	   {
	      int mid = (low + high) / 2;
	      firstHalf = new ThreadNorm(this, low, mid);
	      firstHalf.start();
	      secondHalf = new ThreadNorm(this, mid + 1, high);
	      secondHalf.start();
	   }
	   
	   if (low != high)
	   {
	      firstHalf.sync();
	      secondHalf.sync();
	      finalResult = firstHalf.finalResult + secondHalf.finalResult;
	   }
	   
	   done = true;
	   if (spawner == null)
	   {
	      System.out.println("This is the final result : " + finalResult);
	      int norm = finalResult;
	      System.out.println("The result of calculating the norm is : " + Math.sqrt(norm));	
	   }
	}

	/**
	 * Sync the results after processing
	 */
	public synchronized void sync()
	{
	   if (!done)
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

	/**
	 * Main method : Driver method for ThreadNorm
	 * @param args : command line arguments array, args[0] should contain
     * an text input file which contains the vectors one on each line. 
	 */
	public static void main(String args[])
	{
	   FileReader fileReader = null;
	     
	   if (args[0] != null)
	   {
	      System.out.println(args[0]);
	     	
	      try 
	      {
	         fileReader = new FileReader(args[0]);
	      } 
	      catch (FileNotFoundException exc) 
	      {
	         exc.printStackTrace();
	      }
	         
	      @SuppressWarnings("resource")
	      final Scanner scanner = new Scanner(fileReader);
	         
	      // Boolean that will become false when -1 is encountered and 
	      // is then used to stop the reading process.
	      boolean cont = true;
	         
	      while (cont && scanner.hasNextLine()) 
	      {
	         final String line = scanner.nextLine();
	         final StringTokenizer tokenizer = new StringTokenizer(line, DELIMITER);
	         
	         while (cont && tokenizer.hasMoreTokens()) 
	         {
	            final int number = Integer.parseInt(tokenizer.nextToken());
	            
	            if (number == MINUS_ONE) 
	            {
	               cont = false;
	            } 
	            else 
	            { 
	               /*
	               Add elements to Vector using
	               boolean add(Object o) method. It returns true as a general behavior
	               of Collection.add method. The specified object is appended at the end
	               of the Vector.
	                */
	            	valueVector.add(number);
	               
	            }
	         }
	      }
	         
	      System.out.println("Size of the vector : " + valueVector.size());
	         
	      for (int i = 0; i < valueVector.size(); i++)
	      {
	         System.out.println(valueVector.get(i));
	      }
	
	      ThreadNorm parent = new ThreadNorm(null, 0, valueVector.size() - 1);
	      
	      // time to start the nano time
	      long before = System.nanoTime();
	      System.out.println("Before : " + before);
	      parent.start();
	      
	      // time to end the nano time
	      long after = System.nanoTime();
	      System.out.println("After : " + after);
	      long estimatedTime = System.nanoTime() - before;
	      System.out.println("Estimated time : " + estimatedTime);
	   }
	   else
	   {
	      System.out.println("File not found");
	   }    
	}
}