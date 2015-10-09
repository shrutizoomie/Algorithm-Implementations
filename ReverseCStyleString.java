package algorithms;

import java.util.Scanner;

public class ReverseCStyleString {

	private static Scanner in;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s;
		 
	      in = new Scanner(System.in);
	 
	      System.out.println("Enter a string");
	      s = in.nextLine();
	      System.out.println("You entered string " + s);
	      
	      String revStr = reverseString(s);
	      
	      System.out.println("Reversed String is :" + revStr);
	}
	
	static String reverseString(String aInput)
	{
		char[] userChars = aInput.toCharArray();
		
		for (int i = 0; i < (userChars.length - 1)/2 + 1; i++)
		{
			char lowerChar = userChars[i];
			userChars[i] = userChars[userChars.length - i -1];
			userChars[userChars.length - i -1] = lowerChar;
		}
		
		//System.out.println("Char array is :" + userChars);
		return new String(userChars) + '\0';
	}
	
}
