/*  1:   */ package adamKraft;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Ports;
/*  5:   */ import connections.WiredBox;
/*  6:   */ import java.io.PrintStream;
/*  7:   */ 
/*  8:   */ public class WireServerStressor
/*  9:   */ {
/* 10: 7 */   public static int DATA_SIZE = 1;
/* 11:   */   
/* 12:   */   public static class Receiver
/* 13:   */     implements WiredBox
/* 14:   */   {
/* 15:   */     int counter;
/* 16:   */     
/* 17:   */     public String getName()
/* 18:   */     {
/* 19:12 */       return null;
/* 20:   */     }
/* 21:   */     
/* 22:   */     public void receive(Object in)
/* 23:   */     {
/* 24:17 */       byte[] data = (byte[])in;
/* 25:18 */       this.counter += 1;
/* 26:19 */       if (this.counter % 10 == 0)
/* 27:   */       {
/* 28:20 */         System.out.println("Server received " + this.counter + " " + data.length / 1048576.0D + " Mbyte chunks.");
/* 29:21 */         System.out.flush();
/* 30:   */       }
/* 31:   */     }
/* 32:   */     
/* 33:   */     public Receiver()
/* 34:   */     {
/* 35:25 */       Connections.getPorts(this).addSignalProcessor("receive");
/* 36:   */     }
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static class Dummy
/* 40:   */     implements WiredBox
/* 41:   */   {
/* 42:   */     public String getName()
/* 43:   */     {
/* 44:33 */       return null;
/* 45:   */     }
/* 46:   */   }
/* 47:   */   
/* 48:   */   public static void main(String[] args)
/* 49:   */     throws Throwable
/* 50:   */   {
/* 51:40 */     String id = "Wire Server Stressor Receiver";
/* 52:41 */     if (args.length > 1)
/* 53:   */     {
/* 54:42 */       Receiver r = new Receiver();
/* 55:43 */       Connections.publish(r, id);
/* 56:44 */       Thread.sleep(1000L);
/* 57:45 */       System.out.println("Commencing server hosage");
/* 58:46 */       int nChunks = 1000000;
/* 59:47 */       long start = System.currentTimeMillis();
/* 60:48 */       for (int i = 0; i < nChunks; i++)
/* 61:   */       {
/* 62:49 */         byte[] huge = new byte[DATA_SIZE];
/* 63:   */         try
/* 64:   */         {
/* 65:52 */           Connections.getPorts(r).transmit(huge);
/* 66:   */         }
/* 67:   */         catch (Throwable e)
/* 68:   */         {
/* 69:55 */           System.out.println("Blew out. i=" + i);
/* 70:56 */           throw e;
/* 71:   */         }
/* 72:   */       }
/* 73:59 */       long finish = System.currentTimeMillis();
/* 74:60 */       System.out.println("Done.");
/* 75:61 */       System.out.println("Total Mbytes sent: " + nChunks * DATA_SIZE / 1048576.0D);
/* 76:62 */       System.out.println("Total time: " + (finish - start) / 1000L + " seconds");
/* 77:63 */       System.out.println("Avg. rate: " + nChunks * DATA_SIZE / 1048576 / ((finish - start) / 1000.0D) + " Mbytes/s");
/* 78:   */     }
/* 79:   */     else
/* 80:   */     {
/* 81:66 */       Receiver d = new Receiver();
/* 82:67 */       WiredBox subscribed = Connections.subscribe(id, -1.0D);
/* 83:68 */       Connections.wire(d, subscribed);
/* 84:69 */       Connections.wire(subscribed, d);
/* 85:70 */       System.out.println("waiting for server hosage");
/* 86:   */       for (;;)
/* 87:   */       {
/* 88:72 */         Thread.sleep(1000L);
/* 89:   */       }
/* 90:   */     }
/* 91:   */   }
/* 92:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adamKraft.WireServerStressor
 * JD-Core Version:    0.7.0.1
 */