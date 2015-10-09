package leet.code;

public class Anagrams {

   public static void main(String[] args) {
      // TODO Auto-generated method stub
      Anagrams ana = new Anagrams();
      boolean result = ana.isAnagram("bat", "cat");
      System.out.println(result);
   }
   
   public boolean isAnagram(String s, String t) {
      
      // unequal length not anagrams
      if (s.length() != t.length())
      {
          return false;
      }
      
      
      int[] anagram = new int[256];
      char[] sArray = s.toCharArray();
      char[] tArray = t.toCharArray();
      
      for (int i = 0; i < sArray.length; i++)
      {
          anagram[sArray[i]]++;
          System.out.print(anagram[sArray[i]] + " ");
          
      }
      System.out.println();
      
      for (int i = 0; i < tArray.length; i++)
      {
          anagram[tArray[i]]--;

          System.out.print(anagram[tArray[i]] + " ");
      }
      
      for (int i = 0; i < 256; i++)
      {
          if (anagram[i] != 0)
          {
              return false;
          }
      }
      
      return true;
  }

}
