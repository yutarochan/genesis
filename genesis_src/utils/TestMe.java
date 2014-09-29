/*  1:   */ package utils;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ 
/*  5:   */ public class TestMe
/*  6:   */ {
/*  7: 5 */   public static int instanceCounter = 0;
/*  8: 7 */   public int myNumber = 0;
/*  9:   */   
/* 10:   */   public TestMe()
/* 11:   */   {
/* 12:10 */     this.myNumber = (instanceCounter++);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public String toString()
/* 16:   */   {
/* 17:14 */     return "Hello, myNumber is " + this.myNumber;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public static void main(String[] ignore)
/* 21:   */   {
/* 22:19 */     TestMe testMe1 = new TestMe();
/* 23:   */     
/* 24:21 */     TestMe testMe2 = new TestMe();
/* 25:   */     
/* 26:23 */     System.out.println("testMe1 myNumber = " + testMe1.myNumber);
/* 27:   */     
/* 28:25 */     System.out.println("testMe2 myNumber = " + testMe2.myNumber);
/* 29:   */     
/* 30:27 */     System.out.println("testMe1 = " + testMe1.toString());
/* 31:   */     
/* 32:29 */     System.out.println("testMe2 = " + testMe2.toString());
/* 33:   */     
/* 34:31 */     System.out.println("testMe1 = " + testMe1);
/* 35:   */     
/* 36:33 */     System.out.println("testMe2 = " + testMe2);
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     utils.TestMe
 * JD-Core Version:    0.7.0.1
 */