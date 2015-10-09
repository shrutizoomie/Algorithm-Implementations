package geeks.geeks.algos;

/**
 * Bubble sort Algo
 * Bubble the largest element to the end
 * 
 * for (i = 0 to n -1)
 * {
 *    for (j = 0 to n - i - 1)
 *    {
 *       if (a[j] > a[j + 1])
 *       {
 *          swap (a[j], a[j+1])
 *       }
 *    }
 * }
 * @author shruti_sharma1
 *
 */
public class BubbleSort
{

   public static void main(String[] args)
   {
      // TODO Auto-generated method stub
      int arr[] = {5,4,3,2,1};
      BubbleSort sort = new BubbleSort();
      int[] result = sort.bubble(arr);
      
      for (int i = 0; i < result.length; i++)
      {
         System.out.print(result[i] + "\t");
      }
   }
   
   private int[] bubble(int arr[])
   {
      int n = arr.length;
      for (int i = 0; i < n - 1; i++ )
      {
         for (int j = 0; j < n - i - 1; j++)
         {
            if (arr[j] > arr[j + 1])
            {
               // swap a[j] and a[j+1]
               int temp = arr[j];
               arr[j] = arr[j + 1];
               arr[j + 1] = temp;
            }
         }
      }

      return arr;
   }

}
