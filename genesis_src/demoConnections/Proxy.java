/*  1:   */ package demoConnections;
/*  2:   */ 
/*  3:   */ import adam.RPCBox;
/*  4:   */ import connections.Connections;
/*  5:   */ import connections.Connections.NetWireException;
/*  6:   */ import connections.WiredBox;
/*  7:   */ import java.util.HashMap;
/*  8:   */ 
/*  9:   */ public class Proxy
/* 10:   */ {
/* 11:   */   private RPCBox rpcBox;
/* 12:19 */   private static HashMap<String, Proxy> proxies = new HashMap();
/* 13:21 */   private static HashMap<String, WiredBox> wiredBoxes = new HashMap();
/* 14:   */   
/* 15:   */   private Proxy(String globalUniqueID)
/* 16:   */     throws Connections.NetWireException
/* 17:   */   {
/* 18:24 */     this.rpcBox = ((RPCBox)Connections.subscribe(globalUniqueID));
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Object rpc(String remoteMethodName, Object... arguments)
/* 22:   */   {
/* 23:28 */     return this.rpcBox.rpc(remoteMethodName, arguments);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public static Proxy getRcpProxy(String globalUniqueID)
/* 27:   */     throws Connections.NetWireException
/* 28:   */   {
/* 29:32 */     Proxy proxy = (Proxy)proxies.get(globalUniqueID);
/* 30:33 */     if (proxy != null) {
/* 31:34 */       return proxy;
/* 32:   */     }
/* 33:36 */     proxy = new Proxy(globalUniqueID);
/* 34:37 */     proxies.put(globalUniqueID, proxy);
/* 35:38 */     return proxy;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public static WiredBox getWireProxy(String globalUniqueID)
/* 39:   */     throws Connections.NetWireException
/* 40:   */   {
/* 41:42 */     WiredBox proxy = (WiredBox)wiredBoxes.get(globalUniqueID);
/* 42:43 */     if (proxy != null) {
/* 43:44 */       return proxy;
/* 44:   */     }
/* 45:46 */     proxy = Connections.subscribe(globalUniqueID);
/* 46:47 */     wiredBoxes.put(globalUniqueID, proxy);
/* 47:48 */     return proxy;
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     demoConnections.Proxy
 * JD-Core Version:    0.7.0.1
 */