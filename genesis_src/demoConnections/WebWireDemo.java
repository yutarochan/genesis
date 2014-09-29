/*  1:   */ package demoConnections;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.WiredBox;
/*  5:   */ 
/*  6:   */ public class WebWireDemo
/*  7:   */ {
/*  8: 9 */   private static String ID = "Wire demonstration";
/*  9:   */   
/* 10:   */   public static void main(String[] ignore)
/* 11:   */     throws Exception
/* 12:   */   {
/* 13:15 */     Receiver server = new Receiver();
/* 14:16 */     Transmitter transmitter = new Transmitter();
/* 15:   */     
/* 16:18 */     WiredBox proxy = Proxy.getWireProxy(ID);
/* 17:   */     
/* 18:   */ 
/* 19:21 */     Connections.wire(transmitter, proxy);
/* 20:22 */     transmitter.sendSignal("Hello Wire 1.0");
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     demoConnections.WebWireDemo
 * JD-Core Version:    0.7.0.1
 */