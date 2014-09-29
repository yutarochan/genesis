/*  1:   */ package memory.utilities;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Thread;
/*  4:   */ import magicLess.distancemetrics.Point;
/*  5:   */ 
/*  6:   */ public class ThreadPoint
/*  7:   */   extends Point<Thread>
/*  8:   */ {
/*  9:   */   private Thread myThread;
/* 10:   */   
/* 11:   */   protected double getDistance(Thread a, Thread b)
/* 12:   */   {
/* 13:17 */     return Distances.distance(a, b);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public ThreadPoint(Thread t)
/* 17:   */   {
/* 18:21 */     this.myThread = t;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Thread getWrapped()
/* 22:   */   {
/* 23:25 */     return this.myThread;
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.utilities.ThreadPoint
 * JD-Core Version:    0.7.0.1
 */