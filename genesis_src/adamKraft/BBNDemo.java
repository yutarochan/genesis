/*  1:   */ package adamKraft;
/*  2:   */ 
/*  3:   */ import adam.RPCBox;
/*  4:   */ import bridge.reps.entities.Entity;
/*  5:   */ import connections.Connections;
/*  6:   */ import connections.Ports;
/*  7:   */ import connections.WiredBox;
/*  8:   */ import java.io.PrintStream;
/*  9:   */ 
/* 10:   */ public class BBNDemo
/* 11:   */   implements WiredBox
/* 12:   */ {
/* 13:   */   public String getName()
/* 14:   */   {
/* 15:12 */     return "collector";
/* 16:   */   }
/* 17:   */   
/* 18:   */   public BBNDemo()
/* 19:   */   {
/* 20:16 */     Connections.getPorts(this).addSignalProcessor("myMethod");
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void myMethod(Object payload)
/* 24:   */   {
/* 25:20 */     System.out.println("myMethod called!");
/* 26:21 */     System.out.println(payload);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static void main(String[] args)
/* 30:   */     throws Exception
/* 31:   */   {
/* 32:25 */     RPCBox cPlusPlus = (RPCBox)Connections.subscribe("Simple Example RPC Service", -1.0D);
/* 33:26 */     WiredBox python = Connections.subscribe("python box for BBN demo");
/* 34:27 */     BBNDemo d = new BBNDemo();
/* 35:28 */     Connections.wire(d, python);
/* 36:29 */     Connections.wire(python, d);
/* 37:   */     for (;;)
/* 38:   */     {
/* 39:32 */       Entity t = new Entity("Created in Java");
/* 40:   */       
/* 41:   */ 
/* 42:35 */       t = (Entity)cPlusPlus.rpc("modifyBundle", new Object[] { t });
/* 43:   */       
/* 44:   */ 
/* 45:38 */       Connections.getPorts(d).transmit(t);
/* 46:   */       
/* 47:40 */       Thread.sleep(1000L);
/* 48:   */     }
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adamKraft.BBNDemo
 * JD-Core Version:    0.7.0.1
 */