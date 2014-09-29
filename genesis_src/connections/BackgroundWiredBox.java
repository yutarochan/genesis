/*  1:   */ package connections;
/*  2:   */ 
/*  3:   */ public class BackgroundWiredBox
/*  4:   */   extends QueuingWiredBox
/*  5:   */ {
/*  6:   */   public BackgroundWiredBox()
/*  7:   */   {
/*  8:11 */     getTimingThread().setPriority(1);
/*  9:   */   }
/* 10:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     connections.BackgroundWiredBox
 * JD-Core Version:    0.7.0.1
 */