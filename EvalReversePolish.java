package algorithms;

import java.util.Stack;

public class EvalReversePolish {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String[] tokens = new String[] { "2", "1", "+", "3", "*" };
		System.out.println(eval(tokens));
	}
	
	static int eval(String[] aTokens)
	{
		int iResult = 0;
		String sOperators = "+-*/";
		
		//creates a generic string type for stack
		Stack<String> polishStack = new Stack<String>(); 
		
		for (String t : aTokens)
		{
			// if number then push the token onto the stack
			if(!sOperators.contains(t))
			{
				polishStack.push(t);
			}
			else
			{
				// pop 2 numbers from the stack. pop the number and then convert it to an integer
				int iPop1 = Integer.valueOf(polishStack.pop());
				int iPop2 = Integer.valueOf(polishStack.pop());
				
				//Apply the operation on the 2 popped operands
				
				
				//find which is the operator
				int index = sOperators.indexOf(t);
				
				switch(index)
				{
					//add
					case 0:
						polishStack.push(String.valueOf(iPop1 + iPop2));
						break;
						
					//subtract	
					case 1:
						polishStack.push(String.valueOf(iPop2 - iPop1));
						break;
						
					//multiply
					case 2:
						polishStack.push(String.valueOf(iPop1 * iPop2));
						break;
						
					//divide	
					case 3:
						polishStack.push(String.valueOf(iPop2 / iPop1));
						break;
				}
					
			}
				
		}
		
		iResult = Integer.valueOf(polishStack.pop());
		return iResult;
	}

}
