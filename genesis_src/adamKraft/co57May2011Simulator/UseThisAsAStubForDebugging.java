/*  1:   */ package adamKraft.co57May2011Simulator;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Connections.NetWireException;
/*  5:   */ import connections.Ports;
/*  6:   */ import connections.WiredBox;
/*  7:   */ import java.io.BufferedInputStream;
/*  8:   */ import java.io.FileInputStream;
/*  9:   */ import java.io.IOException;
/* 10:   */ import java.io.InputStream;
/* 11:   */ import java.io.ObjectInput;
/* 12:   */ import java.io.ObjectInputStream;
/* 13:   */ import java.net.URL;
/* 14:   */ import java.util.List;
/* 15:   */ import utils.Mark;
/* 16:   */ 
/* 17:   */ public class UseThisAsAStubForDebugging
/* 18:   */   implements WiredBox
/* 19:   */ {
/* 20:   */   public static final String serviceName = "C057 may demo stub";
/* 21:   */   private List traceElements;
/* 22:   */   
/* 23:   */   public void go()
/* 24:   */   {
/* 25:25 */     Mark.betterSay(
/* 26:   */     
/* 27:   */ 
/* 28:   */ 
/* 29:   */ 
/* 30:   */ 
/* 31:   */ 
/* 32:32 */       new Object[] { "Got a request" });new Thread()
/* 33:   */     {
/* 34:   */       public void run()
/* 35:   */       {
/* 36:29 */         UseThisAsAStubForDebugging.this.run();
/* 37:   */       }
/* 38:   */     }.start();
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void run()
/* 42:   */   {
/* 43:35 */     synchronized (this)
/* 44:   */     {
/* 45:36 */       for (Object o : this.traceElements)
/* 46:   */       {
/* 47:37 */         Connections.getPorts(this).transmit(o);
/* 48:   */         try
/* 49:   */         {
/* 50:39 */           Thread.sleep(33L);
/* 51:   */         }
/* 52:   */         catch (InterruptedException e)
/* 53:   */         {
/* 54:41 */           e.printStackTrace();
/* 55:   */         }
/* 56:   */       }
/* 57:   */     }
/* 58:   */   }
/* 59:   */   
/* 60:   */   public UseThisAsAStubForDebugging()
/* 61:   */     throws Connections.NetWireException, IOException, ClassNotFoundException
/* 62:   */   {
/* 63:   */     try
/* 64:   */     {
/* 65:49 */       InputStream file = new FileInputStream(getClass().getResource("attntrace.java.serialized").getPath());
/* 66:50 */       InputStream buffer = new BufferedInputStream(file);
/* 67:51 */       ObjectInput input = new ObjectInputStream(buffer);
/* 68:52 */       this.traceElements = ((List)input.readObject());
/* 69:53 */       input.close();
/* 70:54 */       Connections.publish(this, "C057 may demo stub");
/* 71:   */     }
/* 72:   */     catch (Connections.NetWireException e)
/* 73:   */     {
/* 74:56 */       Mark.betterSay(new Object[] {"failed to publish" });
/* 75:57 */       throw e;
/* 76:   */     }
/* 77:   */   }
/* 78:   */   
/* 79:   */   public String getName()
/* 80:   */   {
/* 81:63 */     return null;
/* 82:   */   }
/* 83:   */   
/* 84:   */   public static void main(String[] args)
/* 85:   */     throws Throwable
/* 86:   */   {
/* 87:67 */     UseThisAsAStubForDebugging d = new UseThisAsAStubForDebugging();
/* 88:68 */     Mark.betterSay(new Object[] {"running..." });
/* 89:   */     for (;;)
/* 90:   */     {
/* 91:71 */       d.run();
/* 92:   */     }
/* 93:   */   }
/* 94:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adamKraft.co57May2011Simulator.UseThisAsAStubForDebugging
 * JD-Core Version:    0.7.0.1
 */