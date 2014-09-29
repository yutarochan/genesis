/*  1:   */ package adamKraft.co57May2011Simulator;
/*  2:   */ 
/*  3:   */ import adam.RPCBox;
/*  4:   */ import connections.Connections;
/*  5:   */ import connections.Ports;
/*  6:   */ import connections.WiredBox;
/*  7:   */ import java.io.PrintStream;
/*  8:   */ import utils.Mark;
/*  9:   */ 
/* 10:   */ public class HeresHowToUseTheStubForDebugging
/* 11:   */   implements WiredBox
/* 12:   */ {
/* 13:   */   public void print(Object o)
/* 14:   */   {
/* 15:11 */     System.out.println(o.getClass().getName());
/* 16:12 */     System.out.println(o);
/* 17:13 */     System.out.println("*******");
/* 18:   */   }
/* 19:   */   
/* 20:   */   public HeresHowToUseTheStubForDebugging()
/* 21:   */   {
/* 22:17 */     Connections.getPorts(this).addSignalProcessor("print");
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static void main(String[] args)
/* 26:   */     throws Throwable
/* 27:   */   {
/* 28:21 */     WiredBox s = Connections.subscribe("C057 may demo stub");
/* 29:22 */     HeresHowToUseTheStubForDebugging r = new HeresHowToUseTheStubForDebugging();
/* 30:23 */     Connections.wire(s, r);
/* 31:24 */     ((RPCBox)s).rpc("go", new Object[0]);
/* 32:25 */     Mark.betterSay(new Object[] {"Done" });
/* 33:26 */     Thread.sleep(5000L);
/* 34:27 */     ((RPCBox)s).rpc("go", new Object[0]);
/* 35:28 */     Thread.sleep(5000L);
/* 36:29 */     Mark.say(new Object[] {"Done again.\n\n" });
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String getName()
/* 40:   */   {
/* 41:34 */     return null;
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adamKraft.co57May2011Simulator.HeresHowToUseTheStubForDebugging
 * JD-Core Version:    0.7.0.1
 */