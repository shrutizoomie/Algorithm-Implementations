package geeks.geeks.Strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Print duplicate characters in a string
 * @author shruti_sharma1
 *
 */
public class PrintDupsInString
{
   
   Map<Character, Integer> hashMap = new HashMap<Character, Integer>();
   
   public static void main(String[] args)
   {
      PrintDupsInString printDup = new PrintDupsInString(); 
      String userInput = "I am aaaa";

      char[] inpArray = userInput.toCharArray();
      
      for (int i = 0; i < inpArray.length; i++)
      {
         if (inpArray[i] != ' ' )
         {
            // already contains the character. Increment the count
            if (printDup.hashMap.containsKey(inpArray[i]))
            {
               printDup.hashMap.put((Character)inpArray[i], (printDup.hashMap.get(inpArray[i])+1));
            }
            // first occurrence so add it to the maps
            else
            {
               printDup.hashMap.put(inpArray[i], 1);
            }
         }
      }
      
      for(Map.Entry<Character, Integer> val : printDup.hashMap.entrySet())
      {
         if (val.getValue() > 1)
         {
            System.out.print(val.getKey());
         }
      }
   }

}
