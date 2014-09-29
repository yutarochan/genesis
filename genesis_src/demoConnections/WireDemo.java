/*  1:   */ package demoConnections;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ 
/*  5:   */ public class WireDemo
/*  6:   */ {
/*  7:   */   public static void main(String[] ignore)
/*  8:   */     throws Exception
/*  9:   */   {
/* 10: 8 */     Receiver receiver = new Receiver();
/* 11: 9 */     Transmitter transmitor = new Transmitter();
/* 12:   */     
/* 13:   */ 
/* 14:12 */     Connections.wire(transmitor, receiver);
/* 15:   */     
/* 16:   */ 
/* 17:15 */     transmitor.sendSignal("Hello Wire World");
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     demoConnections.WireDemo
 * JD-Core Version:    0.7.0.1
 */