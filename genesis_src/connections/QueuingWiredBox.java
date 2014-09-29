/*  1:   */ package connections;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ 
/*  5:   */ public class QueuingWiredBox
/*  6:   */   extends AbstractWiredBox
/*  7:   */ {
/*  8:   */   TimingThread timingThread;
/*  9:15 */   ArrayList<Object> queue = new ArrayList();
/* 10:17 */   int persistance = 250;
/* 11:19 */   int maxQueueLength = 50;
/* 12:   */   
/* 13:   */   public QueuingWiredBox()
/* 14:   */   {
/* 15:22 */     Connections.getPorts(this).addSignalProcessor("process");
/* 16:   */   }
/* 17:   */   
/* 18:   */   public QueuingWiredBox(int p, int q)
/* 19:   */   {
/* 20:26 */     this(p);
/* 21:27 */     this.maxQueueLength = q;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public QueuingWiredBox(int p)
/* 25:   */   {
/* 26:31 */     this();
/* 27:32 */     this.persistance = p;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void process(Object o)
/* 31:   */   {
/* 32:36 */     this.queue.add(o);
/* 33:37 */     getTimingThread();
/* 34:   */   }
/* 35:   */   
/* 36:   */   protected synchronized TimingThread getTimingThread()
/* 37:   */   {
/* 38:41 */     if (this.timingThread == null)
/* 39:   */     {
/* 40:42 */       this.timingThread = new TimingThread();
/* 41:43 */       this.timingThread.start();
/* 42:   */     }
/* 43:45 */     return this.timingThread;
/* 44:   */   }
/* 45:   */   
/* 46:   */   class TimingThread
/* 47:   */     extends Thread
/* 48:   */   {
/* 49:   */     TimingThread() {}
/* 50:   */     
/* 51:   */     public void run()
/* 52:   */     {
/* 53:50 */       int i = 0;
/* 54:   */       for (;;)
/* 55:   */       {
/* 56:52 */         i++;
/* 57:52 */         if (i % 10 == 0) {
/* 58:53 */           i = 0;
/* 59:   */         }
/* 60:55 */         while (QueuingWiredBox.this.queue.size() > QueuingWiredBox.this.maxQueueLength) {
/* 61:56 */           QueuingWiredBox.this.queue.remove(0);
/* 62:   */         }
/* 63:   */         try
/* 64:   */         {
/* 65:59 */           if (QueuingWiredBox.this.queue.size() > 0)
/* 66:   */           {
/* 67:60 */             Object input = QueuingWiredBox.this.queue.remove(0);
/* 68:61 */             Connections.getPorts(QueuingWiredBox.this).transmit(input);
/* 69:62 */             Thread.sleep(QueuingWiredBox.this.persistance);
/* 70:   */           }
/* 71:   */           else
/* 72:   */           {
/* 73:65 */             Thread.sleep(QueuingWiredBox.this.persistance);
/* 74:   */           }
/* 75:   */         }
/* 76:   */         catch (InterruptedException e)
/* 77:   */         {
/* 78:69 */           e.printStackTrace();
/* 79:   */         }
/* 80:   */       }
/* 81:   */     }
/* 82:   */   }
/* 83:   */   
/* 84:   */   public int getPersistance()
/* 85:   */   {
/* 86:76 */     return this.persistance;
/* 87:   */   }
/* 88:   */   
/* 89:   */   public void setPersistance(int persistance)
/* 90:   */   {
/* 91:80 */     this.persistance = persistance;
/* 92:   */   }
/* 93:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     connections.QueuingWiredBox
 * JD-Core Version:    0.7.0.1
 */