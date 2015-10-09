import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


public class findBomb
{

   public static void main(String[] args)
   {
      // TODO Auto-generated method stub
      int[] arr = {1,2,2,3,3,4,4,5,5,6,6,7};
      boolean result =  contains_bomb(arr);
      System.out.println("Result is " + result);

   }

   static boolean contains_bomb(int[] arr)
   {
      if (arr == null || arr.length == 0) {
         return false;
      }
      
      LinkedHashMap<Integer, Integer> linkMap = new LinkedHashMap<Integer, Integer>();
      
      // Creation of the linked hash map
      for(int x : arr)
      {
         // already contains the integer. Increment the count
         if (linkMap.containsKey(x))
         {
            linkMap.put((Integer)x, (linkMap.get(x) + 1));
         }
         // first occurrence so add it to the maps
         else
         {
            linkMap.put(x, 1);
         }
      }
      
      // check the linked hashmap for bomb
      Set<Integer> keySet = linkMap.keySet();
      int result = 0;
      int prevKey = 0;
      int key;
      Iterator<Integer> keySetIterator = keySet.iterator();
      while(keySetIterator.hasNext())
      {
         key = keySetIterator.next();
         if (key == 0) {
            result = updateResult(result, linkMap, key);
         }
         else {
            if ((key - 1) == prevKey) {
               result = updateResult(result, linkMap, key);
            }
         }
         
         if (result == 3) {
            return true;
         }
         prevKey = key;
      }
      return false;
   }
   
   static int updateResult(int result, LinkedHashMap<Integer, Integer> linkMap, int key) {
      if (linkMap.get(key) > 1) {
         result++;
      } 
      else {
         result = 0;
      }
      return result;
   }
}
