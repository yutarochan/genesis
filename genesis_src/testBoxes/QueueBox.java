/*  1:   */ package testBoxes;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Ports;
/*  5:   */ import connections.WiredBox;
/*  6:   */ import java.util.concurrent.BlockingDeque;
/*  7:   */ import java.util.concurrent.Callable;
/*  8:   */ import java.util.concurrent.LinkedBlockingDeque;
/*  9:   */ 
/* 10:   */ public class QueueBox
/* 11:   */   implements WiredBox, Callable<Object>
/* 12:   */ {
/* 13:13 */   public BlockingDeque<Object> results = new LinkedBlockingDeque();
/* 14:   */   
/* 15:   */   public String getName()
/* 16:   */   {
/* 17:16 */     return "Anti Box";
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void process(Object input)
/* 21:   */   {
/* 22:20 */     this.results.offer(input);
/* 23:21 */     Connections.getPorts(this).transmit(input);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public QueueBox()
/* 27:   */   {
/* 28:25 */     Connections.getPorts(this).addSignalProcessor("process");
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Object call()
/* 32:   */     throws InterruptedException
/* 33:   */   {
/* 34:29 */     return this.results.take();
/* 35:   */   }
/* 36:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     testBoxes.QueueBox
 * JD-Core Version:    0.7.0.1
 */