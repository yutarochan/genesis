/*  1:   */ package memory.multithreading;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import java.util.concurrent.BlockingQueue;
/*  5:   */ import java.util.concurrent.LinkedBlockingQueue;
/*  6:   */ 
/*  7:   */ public class Worker
/*  8:   */ {
/*  9:11 */   BlockingQueue<Task> q = new LinkedBlockingQueue();
/* 10:   */   
/* 11:   */   public Worker()
/* 12:   */   {
/* 13:25 */     new Thread()
/* 14:   */     {
/* 15:   */       public void run()
/* 16:   */       {
/* 17:   */         try
/* 18:   */         {
/* 19:   */           for (;;)
/* 20:   */           {
/* 21:16 */             Task t = (Task)Worker.this.q.take();
/* 22:17 */             System.out.println("Executing++++++++++++++++++++++");
/* 23:18 */             t.execute();
/* 24:19 */             System.out.println("Not Executing------------------");
/* 25:   */           }
/* 26:   */         }
/* 27:   */         catch (InterruptedException e)
/* 28:   */         {
/* 29:22 */           System.err.println("[MEMORY] Worker Thread Interrupted");
/* 30:   */         }
/* 31:   */       }
/* 32:   */     }.start();
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void put(Task t)
/* 36:   */   {
/* 37:   */     try
/* 38:   */     {
/* 39:29 */       this.q.put(t);
/* 40:   */     }
/* 41:   */     catch (InterruptedException e)
/* 42:   */     {
/* 43:32 */       System.err.println("[MEMORY] Worker Thread Interrupted");
/* 44:   */     }
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.multithreading.Worker
 * JD-Core Version:    0.7.0.1
 */