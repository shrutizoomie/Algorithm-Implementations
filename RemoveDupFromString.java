package geeks.geeks.Strings;

import java.util.HashSet;

/**
 * Program to remove duplicate characters from a string
 * @author shruti_sharma1
 *
 */
public class RemoveDupFromString
{
   HashSet<Character> hashSet = new HashSet<Character>();

   public static void main(String[] args)
   {
     String inputVal = "This is this and that is that";
     RemoveDupFromString removedup = new RemoveDupFromString();
     
     char[] inpArray = inputVal.toLowerCase().toCharArray();
     
     for (int i = 0; i < inpArray.length; i++)
     {
        if(inpArray[i] != ' ')
        {
           removedup.hashSet.add(inpArray[i]);
        }
     }
     
     
     for(Character ch : removedup.hashSet)
     {
        System.out.print(ch);
     }
     
   }
}
