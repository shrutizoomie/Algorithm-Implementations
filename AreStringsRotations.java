package geeks.geeks.Strings;

/**
 * This API is to find if one string is a rotation of the other or not
 * @author shruti_sharma1
 *
 */
public class AreStringsRotations
{
   public static void main(String[] args)
   {
      // TODO Auto-generated method stub
      String s1 = "ABCD";
      String s2 = "CDAB";
      boolean result = areRotations(s1, s2);
      System.out.println("Result is : " + result);
   }

   private static boolean areRotations(String s1, String s2)
   {
      // TODO Auto-generated method stub
      String appended = s1 + s2;
      if (appended.contains(s2))
         return true;
      return false;
   }

}
