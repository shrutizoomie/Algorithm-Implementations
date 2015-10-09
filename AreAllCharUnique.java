package algorithms;

import java.util.Scanner;

/* This program will check if all the characters in a string are unique/repeated */
public class AreAllCharUnique {

	private static Scanner in;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s;
		 
	      in = new Scanner(System.in);
	 
	      System.out.println("Enter a string");
	      s = in.nextLine();
	      System.out.println("You entered string " + s);
	      
	      boolean bUnique = isUnique(s);
	      
	      if(bUnique)
	      {
	    	  System.out.println("String has unique characters");
	      }
	      else
	      {
	    	  System.out.println("String has characters which are repeated");
	      }
	}
	
	//main algo logic 1: Considering ASCII chars in string " O(n) is the time complexity of this
	/*static boolean isUnique(String aInput)
	{
		boolean[] boolAscii = new boolean[256];
		
		for (int i = 0; i < aInput.length(); i++)
		{
			// ascii value of the character at location i in string aInput
			int val = aInput.charAt(i);
			
			// check if the value at the ascii location is already set?
			if (boolAscii[val])
			{
				// If value set means the character is repeated so return from here with false
				return false;
			}
			//Set the location value to true
			boolAscii[val] = true;
		}
		return true;
	}*/
	
	// main algo logic 2: 
	static boolean isUnique(String aInput)
	{
		int checker = 0;
		for (int i = 0; i < aInput.length(); ++i) 
		{
			int val = aInput.charAt(i) - 'a';
			
			System.out.println("The Value after computing difference : " + val);
			if ((checker & (1 << val)) > 0) return false;
			
			// cumulates the value and if again the same value occurs, the & will give result > 0
			checker |= (1 << val);
		}
		return true;
	}
}
