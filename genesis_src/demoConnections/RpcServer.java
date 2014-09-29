/*  1:   */ package demoConnections;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Connections.NetWireException;
/*  5:   */ import connections.WiredBox;
/*  6:   */ 
/*  7:   */ public class RpcServer
/*  8:   */   implements WiredBox
/*  9:   */ {
/* 10:10 */   public static String ID = "Rpc demonstration";
/* 11:   */   
/* 12:   */   public RpcServer()
/* 13:   */     throws Connections.NetWireException
/* 14:   */   {
/* 15:14 */     Connections.publish(this, ID);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String echo(Object arg1, String arg2, Double arg3)
/* 19:   */   {
/* 20:19 */     return "Echoing: " + arg1 + " " + arg2 + " " + arg3;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getName()
/* 24:   */   {
/* 25:25 */     return "RpcServer demonstration system";
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     demoConnections.RpcServer
 * JD-Core Version:    0.7.0.1
 */