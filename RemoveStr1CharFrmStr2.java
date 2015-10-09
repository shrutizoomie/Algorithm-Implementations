package geeks.geeks.Strings;

import java.util.HashMap;
import java.util.Map;

public class RemoveStr1CharFrmStr2
{
   Map<Character, Integer> str1Map = new HashMap<Character, Integer>();
   Map<Character, Integer> str2Map = new HashMap<Character, Integer>();
   
   public static void main(String[] args)
   {
      RemoveStr1CharFrmStr2 removeData = new RemoveStr1CharFrmStr2();
      // TODO Auto-generated method stub
      String input1 = "MICROSOFT";
      String input2 = "APPLESOFT";
      
      char[] arr1 = input1.toCharArray();
      char[] arr2 = input2.toCharArray();
      
      for(int i = 0; i < arr1.length; i++)
      {
         
         if (arr1[i] != ' ' )
         {
            if (!removeData.str1Map.containsKey(arr1[i]))
            {
               removeData.str1Map.put((Character)arr1[i], 1);
            }
         }
      }

      
      for(int i = 0; i < arr2.length; i++)
      {
         if (arr2[i] != ' ' )
         {
            if (removeData.str1Map.containsKey(arr2[i]))
            {
               removeData.str1Map.put((Character)arr1[i], 0);
            }
         }
      }
      
      
      for(Map.Entry<Character, Integer> val : removeData.str1Map.entrySet())
      {
         if (val.getValue() != 0)
         {
            System.out.print(val.getKey());
         }
      }
   }

}
