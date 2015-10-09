package geeks.geeks.algos;

public class MergeSort
{

   public static void main(String[] args)
   {
      // TODO Auto-generated method stub
         int arr[] = {3, 5, 6, 1, 8, 4, 2};
         MergeSort sort = new MergeSort();
         int result[] = sort.mergeSort(arr, 0,arr.length - 1);

         for(int i = 0; i < result.length; i++)
         {
            System.out.print(result[i] + "\t");
         }
   }
   
   private  int[] mergeSort(int[] A, int l, int r)
   {
      if (l < r)
      {
         // copy half of A to B and rest to C
         int mid = (l + r) / 2;
         mergeSort(A, l, mid);
         mergeSort(A, mid + 1, r);
         merge (A, l, mid, r);
      }
      
      return A;
   }

   private void merge(int[] a, int left, int mid, int right)
   {
      // TODO Auto-generated method stub
      int i, j, k;
      int n1 = mid - left + 1;
      int n2 = right - mid;
      
      int[] L = new int[n1];
      int[] R = new int[n2];
      
      for (i = 0; i < n1; i++)
      {
         // copy first half into L
         L[i] = a[left + i];
         
      }
      
      for (j = 0; j < n2; j++)
      {
         // copy first half into L
         R[j] = a[mid + 1 + j];
         
      }
      
      
      // merge temp arrays back
      
      i = 0;
      j = 0;
      k = left;
      
      while(i < n1 && j < n2)
      {
         if (L[i] <= R[j])
         {
            a[k] = L[i];
            i++;
         }
         else
         {
            a[k] = R[j];
            j++;
         }
         k++;
      }
      
      while (i < n1)
      {
         a[k] = L[i];
         i++;
         k++;
      }
      
      while (j < n2)
      {
         a[k] = R[j];
         j++;
         k++;
      }
   }

}
