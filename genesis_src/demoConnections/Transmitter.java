/*  1:   */ package demoConnections;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Ports;
/*  5:   */ import connections.WiredBox;
/*  6:   */ 
/*  7:   */ public class Transmitter
/*  8:   */   implements WiredBox
/*  9:   */ {
/* 10:   */   public void sendSignal(String s)
/* 11:   */   {
/* 12: 8 */     Connections.getPorts(this).transmit(s);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public String getName()
/* 16:   */   {
/* 17:14 */     return "Wire demo transmitter";
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     demoConnections.Transmitter
 * JD-Core Version:    0.7.0.1
 */