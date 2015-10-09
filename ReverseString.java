package geeks.geeks.Strings;

/**
 * Reverse a string
 * @author shruti_sharma1
 *
 */
public class ReverseString
{
   public static void main(String[] args)
   {
      // TODO Auto-generated method stub
      String input = "Shruti";
      String result = revString(input);
      System.out.println(result);
   }

   private static String revString(String input)
   {
      // TODO Auto-generated method stub
      int len = input.length();
      
      char[] inpArray = input.toCharArray();
      
      for (int i = 0; i < (len / 2); i++)
      {
         char temp = inpArray[i];
         inpArray[i] = inpArray[len - i - 1];
         inpArray[len - i - 1] = temp;
      }
      
      return new String(inpArray);
   }

}
