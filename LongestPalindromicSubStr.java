package algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//this algorithm is to find the longest palindromic substring in a given string
public class LongestPalindromicSubStr {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//ask user to input the string
		System.out.println("Enter the string to find the longest palindrome");
		
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String userInput = br.readLine();
			
			String longestMatch = longPalin(userInput);
			System.out.print("Longest matching palindrome is : " + longestMatch);
		}
		catch(IOException aErr)
		{
			System.out.print("Input exception");
		}
		
		
		
		
		
	}

	public static String longPalin(String aInput)
	{
		// if the length is null return null
		if (aInput == null)
		{
			return null;
		}
		
		// if the length is 1 return the input string
		if (aInput.length() == 1)
		{
			return aInput;
		}
		
		//loop over the string and find the longest substring
		
		//the length is atleast > 1 when the control reaches here: initialize longest to 1
		String longest = aInput.substring(0, 1);
		
		for(int i=0; i < aInput.length(); i++)
		{
			//Case1 : String length is odd number : only 1 center that is i
			//get longest palindrome with i as center
			String tmp = helper(aInput, i, i);
			if (tmp.length() > longest.length()) {
				longest = tmp;
			}
			
			//Case2: string length is even number : 2 centers i and i+1 
			//get longest palindrome with i and i+1 as center
			tmp = helper(aInput, i, i+1);
			if (tmp.length() > longest.length()) {
				longest = tmp;
			}
	 
		}
		return longest;
	}
	
	// Given a center, either one letter or two letter, 
	// Find longest palindrome
	public static String helper(String s, int begin, int end) {
		while (begin >= 0 && end <= s.length() - 1 && s.charAt(begin) == s.charAt(end)) {
			begin--;
			end++;
		}
		return s.substring(begin + 1, end);
	}
	
}
