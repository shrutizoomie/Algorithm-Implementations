package geeks.in.geeks;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SumTest
{
   private Sum a;

   @Before
   public void setup() {
      a = new Sum();
   }
   
   
   @Test
   public void testAddNos()
   {
      assertEquals("Expected sum of numbers wasn't reached", 5, a.addNos(3, 1));
      assertEquals(0, a.addNos(0, 1));
      assertNull(a);
      assertTrue(true);
   }
}
