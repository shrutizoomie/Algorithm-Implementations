package geeks.geeks.Strings;

/**
 * find all permutations of an input string
 * @author shruti_sharma1
 *
 */
public class AllPermutations
{

   public static void main(String[] args)
   {
      // TODO Auto-generated method stub
      String input = "Shruti";
      allPermutes(input);
   }

   private static void allPermutes(String input)
   {
      char[] inputArr = input.toCharArray();
      
      allPermutes(inputArr, inputArr.length);
   }

   private static void allPermutes(char[] inputArr, int length)
   {
      if (length == 1)
      {
         System.out.println(inputArr);
         return;
      }
      
      for (int i = 0; i < length; i++)
      {
         swap(inputArr, i, length - 1);
         allPermutes(inputArr, length - 1);
         swap(inputArr, i, length - 1);
      }
   }

   // swap the characters at indices i and j
   private static void swap(char[] a, int i, int j) {
       char c = a[i];
       a[i] = a[j];
       a[j] = c;
   }

}
