package geeks.in.geeks;

public class LinkedList
{
   private Link first;
   
   LinkedList()
   {
      first = null;
   }
   
   boolean isEmpty()
   {
      return (first == null);
   }
 
   Link insertFirst(int newVal)
   {
      Link newNode = new Link(newVal);
      
      if (first == null)
      {
         return newNode;
      }
      
      newNode.next = first;
      first = newNode;
      return first;
      
   }
   
   Link deleteFirst()
   {
      // list is empty
      if (first == null)
      {
         return null;
      }
      // only 1 node in list
      if (first.next == null)
      {
         return first;
      }
      Link tempNode = first;
      first = first.next;
      return tempNode;
   }
   
   void displayList()
   {
      Link temp = first;
      while (temp != null)
      {
         System.out.println(temp.getdata());
         temp = temp.next;
      }
   }
   
   public static void main(String[] args)
   {
      // TODO Auto-generated method stub

   }

}

