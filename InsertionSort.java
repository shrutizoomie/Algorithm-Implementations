package geeks.geeks.algos;

/**
 * like sorting cards.
 * Take a card and insert it in its correct position among all the cards on its left
 * 
 * for (int i = 1 to n-1)
      key = arr[i]
      j = i - 1;
      
      while (j >= 0 && arr[j] > key)
      {
         arr[j + 1] = arr[j]
         j = j - 1
      }
      
      arr[j + 1] = key;
 * @author shruti_sharma1
 *
 */
public class InsertionSort
{

   public static void main(String[] args)
   {
      // TODO Auto-generated method stub
      InsertionSort sort = new InsertionSort();
      int[] arr = {5,3,4,2,7,1};
      
      int[] result = sort.insertionSort(arr);
      
      for (int i = 0; i < result.length; i++)
      {
         System.out.print(result[i] + "\t");
      }
   }
   
   private int[] insertionSort(int arr[])
   {
      for (int i = 1; i < arr.length; i++)
      {
         int key = arr[i];
         int j = i - 1;
         
         while (j >= 0 && arr[j] > key)
         {
            arr[j + 1] = arr[j];
            j = j - 1;
         }
         
         arr[j + 1] = key;
      }
      
      return arr;
   }
   

}
