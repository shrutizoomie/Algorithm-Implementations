package geeks.in.geeks;

public class Sum
{
   int addNos(int a, int b)
   {
      if((a == 0) || (b == 0))
      {
         return 0;
      }

      return (a + b);
   }

   public static void main(String[] args)
   {
      // TODO Auto-generated method stub
      Sum var = new Sum();
      int x = var.addNos(3, 4);
      System.out.println("Sum is : " + x);
   }

}
