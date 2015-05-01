 /**
   Class to compute random permutations based on the given no and mode 
   @author Shruti
   @version 1.01 2015/2/10

*/
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 * Class to compute random permutations based on the given no and mode 
 *
 */
public class GenerateAllPermutations 
{

   //here n gets its values from the command line input	
   private  int nInput;
   private  int nPermutations;
   private  int nRandomPermutationsCall;
   private  ArrayList<Integer> permutedList;
   private  static HashMap<Integer, Integer> hMap = new HashMap<Integer, Integer>();
   
   GenerateAllPermutations( int input)
   {
	   nInput = input;
	   nPermutations = 0;
	   nRandomPermutationsCall = 0;
	   permutedList = new ArrayList<Integer>();
	   
   }

   
   public static void main(String[] args)
   {
	   
	  //check for no of command line arguments
	  if(args.length < 2){
         System.out.println("Please enter the correct no of command line arguments");
      }
	  else
	  {
         int input = Integer.parseInt(args[0]);
		 String mode = args[1];
		 
		 //Initialize GenerateAllPermutations object
		 GenerateAllPermutations obj = new GenerateAllPermutations(input);
		 
		 //Calculate total no of possible permutations.
		 obj.calculateTotalPermutations();
		 
		 //Generate all the permutations
		 obj.generatePermutation();
		 
		 //output based on verbose or quiet
		 if(mode.equals("verbose"))
		 {
	         for(int i = 0; i < obj.permutedList.size();i++)
	         {
	             System.out.println(obj.permutedList.get(i));
	         }
	     }
		 System.out.println("number of times randomPermutation(n) was called : "+ obj.nRandomPermutationsCall);	
			
      }//end of if-else
   }//end of main method

   /**
    * This method calculate the total no of possible permutations.
    */
   public void calculateTotalPermutations()
   {
	   int totalPermutations = 1;
	   for(int i = 1; i <= nInput; i++)
	   {
		   totalPermutations = totalPermutations * i;
	   }
	   nPermutations = totalPermutations;
		
   }

   /**
    * this method call int[] randomPermutation method till all the permutations are found and 
    * stores the result in permutedList
    */
   public void generatePermutation()
   {
      if(nPermutations > 0){
    	 
    	 //loop till the permutedList contains all the possible permutations 
         while(permutedList.size() < nPermutations ) 
         {
            nRandomPermutationsCall++;
			
            //find the permutation array using randomPermutation method and store in permuteArr
            int[] permuteArr = new int[nInput];
            permuteArr = randomPermutation(nInput);
            
            //convert the permuteArr to an integer by concatenating all its value
			String permutedValue = "";
			for(int k = 0; k < nInput; k++){
			   permutedValue =  permutedValue + "" + permuteArr[k] + "";
			}
			   
			int permutedValueInt = Integer.parseInt(permutedValue);
			
			//Add the value to the permutedList if permutedList doesnot contain this value already
			if(!permutedList.contains(permutedValueInt))
			{
				permutedList.add(permutedValueInt) ;
			}
			   
			   
		 }//end of while
		 //Sort the permutedList  
		 Collections.sort(permutedList);
			
      }
		
   }
   
   /**
    * This method generate a single permutation using (1, n^3) method
    * @param n value for which all the permutation needs to be found
    * @return int[] permuteArr which contains a random permutation of n
    */
   public static int[] randomPermutation(int n)
   {

      int[] permuteArr = new int[n];
	  int range = n * n * n;
	  
	  //Calculate random priority for each no from 1 to n and store the no and corresponding priority
	  //to hMap
	  for(int i = 1; i <= n ;i++)
	  {
	     int priority = (int)(Math.random() *range) + 1;
		 hMap.put(i, priority);
	  }
	   
	   //sort hMap based on priority
	   permuteArr = sortHmap(hMap, n);
	   return permuteArr;   
   }
   
   /**
    * This method sorts the HashMap based on the priority values and return an array
    * @param hashmap, which contains the a single permutation of n as key priority as value
    * @param n length of the array to be returned
    * @return int[] permuteArr which contains contains a random permutation of n 
    */
   public static int[] sortHmap(HashMap<Integer, Integer> hashmap, int permuteArrLength)
   {
      int[] permuteArr = new int[permuteArrLength];
	   
      // Convert Map to List
	  List<Map.Entry<Integer, Integer>> list = 
	  new LinkedList<Map.Entry<Integer, Integer>>(hashmap.entrySet());
	 
	  // Sort list with comparator, to compare the Map values
	  Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() 
	  {
	     public int compare(Map.Entry<Integer, Integer> o1,Map.Entry<Integer, Integer> o2) 
	     {
	        return (o1.getValue()).compareTo(o2.getValue());
		 }
	  });
	  
	 // Convert sorted map back to a sorted array based on priorities values 
      int i =0;
      HashMap<Integer, Integer> sortedMap = new LinkedHashMap<Integer, Integer>();
      for (Iterator<Map.Entry<Integer, Integer>> it = list.iterator(); it.hasNext();) 
      {
         Map.Entry<Integer, Integer> entry = it.next();
         permuteArr[i] = entry.getKey();
		 i++;
      } 
      return permuteArr;
   }


}

