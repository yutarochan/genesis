/*  1:   */ package demoConnections;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Connections.NetWireException;
/*  5:   */ import connections.Ports;
/*  6:   */ import connections.WiredBox;
/*  7:   */ import java.io.PrintStream;
/*  8:   */ 
/*  9:   */ public class Receiver
/* 10:   */   implements WiredBox
/* 11:   */ {
/* 12: 9 */   public static String ID = "Wire demonstration";
/* 13:   */   
/* 14:   */   public Receiver()
/* 15:   */     throws Connections.NetWireException
/* 16:   */   {
/* 17:14 */     Connections.getPorts(this).addSignalProcessor("process");
/* 18:   */     
/* 19:16 */     Connections.publish(this, ID);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getName()
/* 23:   */   {
/* 24:20 */     return "Wire demo receiver";
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void process(Object object)
/* 28:   */   {
/* 29:26 */     System.out.println("Reciever received this signal from transmitter: " + object.toString());
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     demoConnections.Receiver
 * JD-Core Version:    0.7.0.1
 */