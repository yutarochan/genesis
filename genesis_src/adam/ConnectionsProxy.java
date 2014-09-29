/*  1:   */ package adam;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Port;
/*  5:   */ import connections.Ports;
/*  6:   */ import connections.WiredBox;
/*  7:   */ import java.util.ArrayList;
/*  8:   */ import java.util.Collection;
/*  9:   */ import java.util.HashMap;
/* 10:   */ import java.util.HashSet;
/* 11:   */ import java.util.Iterator;
/* 12:   */ import java.util.Map;
/* 13:   */ import java.util.Set;
/* 14:   */ 
/* 15:   */ public class ConnectionsProxy
/* 16:   */ {
/* 17:   */   public ConnectionsProxy()
/* 18:   */   {
/* 19:17 */     initialize();
/* 20:   */   }
/* 21:   */   
/* 22:20 */   private Map<String, WiredBox> allNodes = new HashMap();
/* 23:21 */   private Map<String, WiredBox> justMyNodes = new HashMap();
/* 24:   */   
/* 25:   */   private void initialize()
/* 26:   */   {
/* 27:24 */     for (WiredBox box : Connections.getInstance().getBoxes()) {
/* 28:25 */       if (box != null)
/* 29:   */       {
/* 30:26 */         String id = WireClientEndpoint.getInstance().getUUID(box);
/* 31:27 */         this.allNodes.put(id, box);
/* 32:28 */         if ((box instanceof WireClientEndpoint)) {
/* 33:29 */           this.justMyNodes.put(id, box);
/* 34:   */         }
/* 35:   */       }
/* 36:   */     }
/* 37:   */   }
/* 38:   */   
/* 39:   */   public Set<WiredBox> getProxyNodesWithConnectionsOut()
/* 40:   */   {
/* 41:36 */     Set<WiredBox> result = new HashSet();
/* 42:   */     Iterator localIterator2;
/* 43:37 */     for (Iterator localIterator1 = this.justMyNodes.values().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 44:   */     {
/* 45:37 */       WiredBox node = (WiredBox)localIterator1.next();
/* 46:38 */       localIterator2 = Connections.getPorts(node).getPorts().iterator(); continue;Port p = (Port)localIterator2.next();
/* 47:39 */       if (p.getSourceBox() == node) {
/* 48:40 */         result.add(node);
/* 49:   */       }
/* 50:   */     }
/* 51:44 */     return result;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public Set<String> getConnectedOutPorts(WiredBox b)
/* 55:   */   {
/* 56:48 */     Set<String> result = new HashSet();
/* 57:49 */     for (Port p : Connections.getPorts(b).getPorts()) {
/* 58:50 */       if (p.getSourceBox() == b) {
/* 59:51 */         result.add(p.getSourceName());
/* 60:   */       }
/* 61:   */     }
/* 62:54 */     return result;
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adam.ConnectionsProxy
 * JD-Core Version:    0.7.0.1
 */