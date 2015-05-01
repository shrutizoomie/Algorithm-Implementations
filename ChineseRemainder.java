/**
   Class to compute Chinese Remainder  based on the given input numbers
   Solves CS255 homework assignment #4
   @author Shruthi, Anumeha and Neha
   @version 1.01 2015/4/19
*/
import java.util.ArrayList;
import java.util.List;
/**
  Class to compute Chinese Remainder based on the given input numbers 
 */
public class ChineseRemainder 
{
   /**
      This method outputs the results of the Extended-Euclid algorithm
	  of given mi and ni which was invoked by Chinese Remainder where
	  n = n1 * n2 ..nk and ni are pairwise relatively prime and
	  mi = n / ni.
	  @param a number, is the mi invoked by Chinese Remainder algorithm 
	  @param b number, is the ni invoked by Chinese Remainder algorithm
      @return int[] d, which returns result of Extended-Euclid algorithm
	  
      Extended-Euclid(a,b) : This API implements the Extended Euclids Algorithm
        1. if b = 0 then return (a, 1, 0)
        2. (d', x', y') = Extended-Euclid(b, a mod b)
        3. (d, x, y) = (d', y', x' - floor(a/b)*y')
        4. return (d, x, y)
   */
   private static int[] Euclid(int a, int b) 
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
   /**
      Driver method for Chinese Remainder
      @param args  command line arguments array, a_0 n_0 a_1 n_1 ... a_n n_m 
        where 0 <= a_i < n_i and goal is to compute number a mod n_1 n_2 .. n_m 	  
	    such that a =~ a_i mod n_i for each i.
   */
   public static void main(String[] args) 
   {
      // Step 1 : Read the command line arguments and store them into values for a and n
	  if (args.length < 2)
	  {
	      return;
	  }
	  List<Integer> aValues = new ArrayList<Integer>();
      List<Integer> nValues = new ArrayList<Integer>();
	  int totalLength = args.length;
	  int i = 0;
	  while (i < totalLength)
	  {
         aValues.add(Integer.valueOf(args[i]));
		 nValues.add(Integer.valueOf(args[i + 1]));
		 i += 2;
	  }
      int nProduct = 1;
      // Step 2 : Compute the product of all n values : n1 * n2 * n3
      for (Integer k : nValues)
      {
        nProduct = nProduct * k; 
      }
      //Step 3 : Compute mi = n / ni
      List<Integer> mValues = new ArrayList<Integer>();
	  if (nProduct != 0)
	  {
        for (Integer niValues : nValues)
		{
		  mValues.add(nProduct / niValues);
		}
	  }
	  // Step 4 : Invoke extended Euclids algorithm(mi, ni) and store the result variable d into tValues
      List<Integer> tValues = new ArrayList<Integer>();
	  for (int x = 0; x < mValues.size(); x++)
	  {
        int[] res = Euclid(mValues.get(x), nValues.get(x));
		tValues.add(res[0]);
	  }
	  // Step 5 : Compute the C values : mi * ti
      List<Integer> cValues = new ArrayList<Integer>();
      for (int x = 0; x < mValues.size(); x++)
      {
        cValues.add(mValues.get(x) * tValues.get(x));
	  }
	  // Step 6 :Final a value = a1 * c1 + a2 * c2 + a3 * c3 .....
	  int finalAValue = 0;
	  for (int x = 0; x < aValues.size(); x++)
	  {
	    finalAValue += (aValues.get(x) * cValues.get(x));
	  }
      System.out.println("The chinese remainder is : " + finalAValue % nProduct);
   }
}
