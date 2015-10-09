package geeks.in.geeks;

import java.util.HashMap;

/**
 * Given an array A[] and a number x, check for pair in A[] with sum as x
 * @author shruti_sharma1
 *
 */
public class Array1
{
   /**
    * Algorithm  :In hashmap put all the elements of the array.
    * Then check if sum-A[i] is in hashmap then it means that 
    * @param args
    */
   public static void main(String[] args)
   {
      // TODO Auto-generated method stub
      int arr[] = {1,2,3,4,5,6,7,8};
      int sum = 8;
      Array1 solution = new Array1();
      String result = solution.getPair(arr, sum);
      
      System.out.println(result);

   }
   
   public String getPair(int[] arr, int sum)
   {
      HashMap<Integer, Integer> pairVal = new HashMap<Integer, Integer>();
      String result = "";
      
      // takes O(n) time to put into map
      for (int i = 0; i < arr.length; i++)
      {
         // key and value are both the actual array elements
         pairVal.put(arr[i], arr[i]);
      }
      
      for (int i = 0; i < arr.length; i++)
      {
         if (pairVal.get(sum - arr[i]) != null)
         {
            result += "[" + arr[i] + "," + (sum - arr[i]) + "]";
         }
      }
      return result;
   }

}
