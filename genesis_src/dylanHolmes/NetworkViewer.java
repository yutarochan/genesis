/*  1:   */ package dylanHolmes;
/*  2:   */ 
/*  3:   */ import connections.WiredBox;
/*  4:   */ import edu.uci.ics.jung.graph.DirectedSparseGraph;
/*  5:   */ import edu.uci.ics.jung.graph.Graph;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import java.util.Collection;
/*  8:   */ import java.util.HashMap;
/*  9:   */ import java.util.Observable;
/* 10:   */ 
/* 11:   */ public class NetworkViewer<V extends WiredBox>
/* 12:   */   extends Observable
/* 13:   */   implements WiredBox
/* 14:   */ {
/* 15:   */   protected String name;
/* 16:   */   protected Graph<V, String> network;
/* 17:   */   protected HashMap<String, ArrayList<V>> boxGroups;
/* 18:   */   
/* 19:   */   public String getName()
/* 20:   */   {
/* 21:28 */     return this.name;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Graph<V, String> getNetwork()
/* 25:   */   {
/* 26:31 */     return this.network;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public V getBoxByName(String name)
/* 30:   */   {
/* 31:45 */     Collection<V> boxes = this.network.getVertices();
/* 32:46 */     for (V b : boxes) {
/* 33:47 */       if (b.getName() == name) {
/* 34:48 */         return b;
/* 35:   */       }
/* 36:   */     }
/* 37:51 */     return null;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void changed()
/* 41:   */   {
/* 42:56 */     setChanged();
/* 43:57 */     notifyObservers();
/* 44:   */   }
/* 45:   */   
/* 46:   */   protected void changed(Object o)
/* 47:   */   {
/* 48:61 */     setChanged();
/* 49:62 */     notifyObservers(o);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public NetworkViewer(String name)
/* 53:   */   {
/* 54:68 */     this.name = name;
/* 55:69 */     this.network = new DirectedSparseGraph();
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     dylanHolmes.NetworkViewer
 * JD-Core Version:    0.7.0.1
 */