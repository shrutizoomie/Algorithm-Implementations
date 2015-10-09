package geeks.geeks.algos;

/**
 * Selection Sort Algo
 * Put the smallest element in its position
 * for i = 0 to n - 1
 *    min = i
 *    for j = i + 1 to n
 *       if a[j] < a[min] 
 *          min = j
 *    swap a[i] and a[min]
 * @author shruti_sharma1
 *
 */
public class SelectionSort
{

   public static void main(String[] args)
   {
      // TODO Auto-generated method stub
      int arr[] = {5,4,3,2,1};
      
      SelectionSort sort = new SelectionSort();
      int result[] = sort.selection(arr);

      for (int i = 0; i < result.length; i++)
      {
         System.out.print(result[i] + "\t");
      }
   }
   
   private int[] selection(int arr[])
   {
      for (int i = 0; i < arr.length - 1; i++)
      {
         int min = i;
         for (int j = i + 1; j < arr.length; j++)
         {
            if (arr[j] < arr[min])
            {
               min = j;
            }
         }
         
         int temp = arr[i];
         arr[i] = arr[min];
         arr[min] = temp;
      }
      
      return arr;
   }

}
