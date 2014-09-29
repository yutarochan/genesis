/*  1:   */ package magicLess.distancemetrics;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Thread;
/*  4:   */ 
/*  5:   */ public class ThreadWithSimilarityDistance
/*  6:   */   extends Point<Thread>
/*  7:   */ {
/*  8:   */   private Thread myThread;
/*  9:   */   
/* 10:   */   protected double getDistance(Thread a, Thread b)
/* 11:   */   {
/* 12: 5 */     return topDownCompare(a, b);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public static double topDownCompare(Thread t1, Thread t2)
/* 16:   */   {
/* 17: 8 */     double overlap = 0.0D;
/* 18: 9 */     for (int i = 0; (i < t1.size()) && (i < t2.size()); i++)
/* 19:   */     {
/* 20:10 */       if (!((String)t1.get(i)).equals(t2.get(i))) {
/* 21:   */         break;
/* 22:   */       }
/* 23:10 */       overlap += 1.0D;
/* 24:   */     }
/* 25:13 */     if (Math.min(t1.size(), t2.size()) == 0) {
/* 26:13 */       return 1.0D;
/* 27:   */     }
/* 28:14 */     return 1.0D - overlap / Math.min(t1.size(), t2.size());
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Thread getWrapped()
/* 32:   */   {
/* 33:20 */     return this.myThread;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public ThreadWithSimilarityDistance(Thread t)
/* 37:   */   {
/* 38:25 */     this.myThread = t;
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     magicLess.distancemetrics.ThreadWithSimilarityDistance
 * JD-Core Version:    0.7.0.1
 */