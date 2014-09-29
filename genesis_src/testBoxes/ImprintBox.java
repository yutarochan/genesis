/*  1:   */ package testBoxes;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Ports;
/*  5:   */ import connections.WiredBox;
/*  6:   */ import java.util.concurrent.Callable;
/*  7:   */ import java.util.concurrent.CountDownLatch;
/*  8:   */ 
/*  9:   */ public class ImprintBox
/* 10:   */   implements WiredBox, Callable<Object>
/* 11:   */ {
/* 12:11 */   private CountDownLatch gate = new CountDownLatch(1);
/* 13:   */   private Object result;
/* 14:13 */   private boolean isFinished = false;
/* 15:   */   
/* 16:   */   public ImprintBox()
/* 17:   */   {
/* 18:16 */     Connections.getPorts(this).addSignalProcessor("process");
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getName()
/* 22:   */   {
/* 23:20 */     return "Inprint Box";
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void process(Object input)
/* 27:   */   {
/* 28:24 */     if (!this.isFinished)
/* 29:   */     {
/* 30:25 */       this.result = input;
/* 31:26 */       this.gate.countDown();
/* 32:27 */       this.isFinished = true;
/* 33:   */     }
/* 34:29 */     Connections.getPorts(this).transmit(input);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public Object call()
/* 38:   */     throws InterruptedException
/* 39:   */   {
/* 40:33 */     this.gate.await();
/* 41:34 */     return this.result;
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     testBoxes.ImprintBox
 * JD-Core Version:    0.7.0.1
 */