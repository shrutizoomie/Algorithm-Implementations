package leet.code;

/**
 * Given a non-negative integer num, repeatedly add all its digits until the result has only one digit.

For example:

Given num = 38, the process is like: 3 + 8 = 11, 1 + 1 = 2. Since 2 has only one digit, return it.
 * @author Niranjan
 *
 */
public class AddDigitsOfNumber {

   public static void main(String[] args) {
      // TODO Auto-generated method stub

   }

   public int addDigits(int num) {
      
      int sum = 0;
      
      while (num > 9)
      {
          int digit = num % 10;
          sum += digit;
          num = num / 10;
          
          if (num <= 9)
          {
              sum += num;
              num = sum;
              sum = 0;
          }
      }
      return num;
      
      
  }
}


