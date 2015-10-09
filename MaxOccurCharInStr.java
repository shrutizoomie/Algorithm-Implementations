package geeks.geeks.Strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Find the character which occurs max number of times in an input string
 * @author shruti_sharma1
 *
 */
public class MaxOccurCharInStr
{
   HashMap<Character, Integer> inputMap = new HashMap<Character, Integer>();
   
   public static void main(String[] args)
   {
      // TODO Auto-generated method stub
      String userInput = "I am very very hungry";
      MaxOccurCharInStr maxOccur = new MaxOccurCharInStr();
      
      char[] userIn = userInput.toCharArray();
      
      
      maxOccur.parseString(userIn);
      Character val = maxOccur.maxOccuringChar();
      System.out.println("Maximum occuring character is :" + val);
   }
   
   void parseString(char[] userIn)
   {
      for(int i = 0; i < userIn.length; i++)
      {
         if (userIn[i] != ' ' )
         {
            // already contains the character. Increment the count
            if (inputMap.containsKey(userIn[i]))
            {
               inputMap.put((Character)userIn[i], (inputMap.get(userIn[i])+1));
            }
            // first occurrence so add it to the maps
            else
            {
               inputMap.put(userIn[i], 1);   
            }
         }
         
      }
   }
   
   Character maxOccuringChar()
   {
      int maxVal = 0;
      Character maxChar = ' ';
      for (Map.Entry<Character, Integer> entry: inputMap.entrySet())
      {
         if (entry.getValue() > maxVal)
         {
            maxVal = entry.getValue();
            maxChar = entry.getKey();
         }
      }
      
      System.out.println(maxVal);
      System.out.println(maxChar);

      return maxChar;
   }
   

}
