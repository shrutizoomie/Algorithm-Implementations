package leet.code;

import java.util.HashMap;
import java.util.Map;

public class RomanNumerals {

   Map<Character, Integer> map = new HashMap<Character, Integer>();
   
   public static void main(String[] args) {
      // TODO Auto-generated method stub
      String val = "DCXXI";
      
      RomanNumerals rom = new RomanNumerals();
      rom.populateMap();
      
      int result = rom.romanToInt(val);
      System.out.println(result);

   }
   
   void populateMap()
   {
      map.put('I', 1);
      map.put('V', 5);
      map.put('X', 10);
      map.put('L', 50);
      map.put('C', 100);
      map.put('D', 500);
      map.put('M', 1000);
   }

      public int romanToInt(String s) {
         int result = 0;
         int maxValEncountered = 0;
          
         char[] romArray = s.toCharArray();
         
         for (int i = romArray.length - 1; i >= 0; i--)
         {
            int val = map.get(romArray[i]);
            System.out.println("Val = " + val);
            System.out.println("Max Val = " + maxValEncountered);
            if (val >= maxValEncountered)
            {
               maxValEncountered = val;   
               result = result + val;
               System.out.println("1 :" + result);
            }
            else
            {
               result = result - val;
               System.out.println("2 : " + result);
            }
         }
          
          return result;
      }

  

}
