/*  1:   */ package demoConnections;
/*  2:   */ 
/*  3:   */ import connections.Connections.NetWireException;
/*  4:   */ import java.io.PrintStream;
/*  5:   */ 
/*  6:   */ public class RpcDemo
/*  7:   */ {
/*  8:16 */   public static String ID = "Rpc demonstration";
/*  9:   */   
/* 10:   */   public void demonstrateClientServerInteraction()
/* 11:   */     throws Connections.NetWireException
/* 12:   */   {
/* 13:20 */     Proxy rpcBox = Proxy.getRcpProxy(ID);
/* 14:   */     
/* 15:   */ 
/* 16:   */ 
/* 17:   */ 
/* 18:25 */     System.out.println("Server returned this value: " + rpcBox.rpc("echo", new Object[] { "Hello", "RPC", Double.valueOf(1.0D) }));
/* 19:   */   }
/* 20:   */   
/* 21:   */   public static void main(String[] ignore)
/* 22:   */     throws Exception
/* 23:   */   {
/* 24:30 */     RpcServer server = new RpcServer();
/* 25:   */     
/* 26:32 */     new RpcDemo().demonstrateClientServerInteraction();
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     demoConnections.RpcDemo
 * JD-Core Version:    0.7.0.1
 */