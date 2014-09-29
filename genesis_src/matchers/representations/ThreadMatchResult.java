/*  1:   */ package matchers.representations;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Thread;
/*  4:   */ 
/*  5:   */ public class ThreadMatchResult
/*  6:   */ {
/*  7: 6 */   public int minLength = 0;
/*  8: 7 */   public int maxLength = 0;
/*  9: 9 */   public int matches = 0;
/* 10:10 */   public double score = -1.0D;
/* 11:12 */   public Thread thread1 = null;
/* 12:13 */   public Thread thread2 = null;
/* 13:15 */   public boolean match = false;
/* 14:17 */   public boolean identityMatch = false;
/* 15:   */   
/* 16:   */   public ThreadMatchResult(Thread thread1, Thread thread2)
/* 17:   */   {
/* 18:20 */     this.thread1 = thread1;
/* 19:21 */     this.thread2 = thread2;
/* 20:   */   }
/* 21:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matchers.representations.ThreadMatchResult
 * JD-Core Version:    0.7.0.1
 */