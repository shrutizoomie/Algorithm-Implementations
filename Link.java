package geeks.in.geeks;

public class Link
{
      private int data;
      Link next;
      
      public Link(int newVal)
      {
         this.data = newVal;
         this.next = null;
      }
      
      public int getdata()
      {
         return data;
      }
}
