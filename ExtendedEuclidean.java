/**
   Class to compute Extended-Euclid  based on the given two numbers 
   Solves CS255 homework assignment #4
   @author Shruthi, Anumeha and Neha
   @version 1.01 2015/4/19
*/
import java.util.List;
/**
  Class to compute Extended-Euclid based on the given two input numbers 
 */
public class ExtendedEuclidean 
{
   /**
      Driver method for ExtendedEuclidean
      @param args  command line arguments array, args[0] and args[1] 
	     should contain an integer whose gcd needs to be calculated. 
   */
   public static void main(String[] args) 
   {
     if(args.length < 2)
     {
       System.out.println("Please enter the correct no of command line arguments");
       return;
     }
     int x = Integer.valueOf(args[0]);
     int y = Integer.valueOf(args[1]);
     ExtendedEuclidean ee = new ExtendedEuclidean();
     int result[] = ee.Euclid(x, y);
     System.out.println("gcd(" + x + ", " + y + ") = " + result[0]);
     System.out.println("(" + result[0] + "," + result[1] + "," + result[2] + ")");
   }
   /**
      This method outputs the results of the Extended-Euclid algorithm
	  of given two input numbers
	  @param a number, is the first integer given as input 
	  @param b number, is the second integer given as input
      @return int[] d, which returns the result of Extended-Euclid algorithm	  

	  Extended-Euclid(a,b) : This API implements the Extended Euclids Algorithm
		1. if b = 0 then return (a, 1, 0)
		2. (d', x', y') = Extended-Euclid(b, a mod b)
		3. (d, x, y) = (d', y', x' - floor(a/b)*y')
		4. return (d, x, y)	  
   */
   private int[] Euclid(int a, int b) 
   {	
     if (b == 0)
     {
       return new int[] { a, 1, 0 };
     }   
     int[] result = Euclid(b, a % b);
     int d = result[0];
     int x = result[2];
     int y = result[1] - (a / b) * result[2];
     return new int[] { d, x, y };
   }
}
