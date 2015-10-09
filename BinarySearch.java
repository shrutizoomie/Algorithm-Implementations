package geeks.geeks.algos;

public class BinarySearch
{
   public static void main(String[] args)
   {
      // TODO Auto-generated method stub
      BinarySearch binSearch = new BinarySearch();
      
      int arr[] = {6,7,8,9,10,1,2,3,4,5};
//      int result = binSearch.binarySearch(arr, 0, arr.length - 1, 11);
//      int result1 = binSearch.nonRecursiveBinarySearch(arr, 0, arr.length - 1, 5);

//      int result2 = binSearch.floorValueofKey(arr, 0, arr.length - 1, 4);
      
      int result2 = binSearch.randomlyRotatedBinarySearch(arr, 0, arr.length - 1);
      System.out.println(result2);
   }

   /**
    * recursive binary serach algo
    * @param arr
    * @param low
    * @param high
    * @param key
    * @return
    */
   private int binarySearch(int[] arr, int low, int high, int key)
   {
      if (low <= high)
      {
         int mid = (low + high) / 2;
         
         if (key == arr[mid])
         {
            return mid;
         }
         else if (key > arr[mid])
         {
            return binarySearch(arr, mid + 1, high, key);
         }
         else if (key < arr[mid])
         {
            return binarySearch(arr, low, mid - 1, key);
         }
      }
      return -1;
   }

   /**
    * non recursive binary search
    * @param arr
    * @param low
    * @param high
    * @param key
    * @return
    */
   private int nonRecursiveBinarySearch(int[] arr, int low, int high, int key)
   {
      while (low <= high)
      {
         int mid = (low + high) / 2;
         
         if (key == arr[mid])
         {
            return mid;
         }
         else if (key > arr[mid])
         {
            low = mid + 1;
         }
         else if (key < arr[mid])
         {
            high = mid - 1;
         }
      }
      return -1;
      
   }

   /**
    *  Given an array of N distinct integers, find floor value of input ‘key’
    *  Say, A = {-1, 2, 3, 5, 6, 8, 9, 10} and key = 7, we should return 6 as outcome.
    */
   private int floorValueofKey(int[] arr, int low, int high, int key)
   {
      while (high - low > 1)
      {
         int mid = (low + high) / 2;
         
         if (arr[mid] <= key)
         {
            low = mid;
         }
         else
         {
            high = mid;
         }
      }
      return arr[low];
   }
   
   /**
    * Find the minimum element in a randomly rotated array [6,7,8,9,10,1,2,3,4,5]
    */
   private int randomlyRotatedBinarySearch(int arr[], int low, int high)
   {
      // if array not rotated, arr[low] < arr[high] then low is the position of minimum element
      if (arr[low] < arr[high])
      {
         return arr[low];
      }
      
      while (low <= high)
      {
         if (low == high)
         {
            return arr[low];
         }
         
         int mid = (low + high)/2;
         
         if (arr[mid] > arr[high])
         {
            low = mid + 1;
         }
         else
         {
            high = mid;
         }
      }
      
      return -1;
   }
}
