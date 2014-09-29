/*  1:   */ package matthewFay.Utilities;
/*  2:   */ 
/*  3:   */ public class StopWatch
/*  4:   */ {
/*  5: 4 */   private long startTime = -1L;
/*  6: 5 */   private long stopTime = -1L;
/*  7: 6 */   private boolean running = false;
/*  8:   */   
/*  9:   */   public StopWatch start()
/* 10:   */   {
/* 11: 9 */     this.startTime = System.currentTimeMillis();
/* 12:10 */     this.running = true;
/* 13:11 */     return this;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public StopWatch stop()
/* 17:   */   {
/* 18:15 */     this.stopTime = System.currentTimeMillis();
/* 19:16 */     this.running = false;
/* 20:17 */     return this;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public long getElapsedTime()
/* 24:   */   {
/* 25:21 */     if (this.startTime == -1L) {
/* 26:22 */       return 0L;
/* 27:   */     }
/* 28:24 */     if (this.running) {
/* 29:25 */       return System.currentTimeMillis() - this.startTime;
/* 30:   */     }
/* 31:27 */     return this.stopTime - this.startTime;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public StopWatch reset()
/* 35:   */   {
/* 36:32 */     this.startTime = -1L;
/* 37:33 */     this.stopTime = -1L;
/* 38:34 */     this.running = false;
/* 39:35 */     return this;
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.Utilities.StopWatch
 * JD-Core Version:    0.7.0.1
 */