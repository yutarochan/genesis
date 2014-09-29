/*  1:   */ package memory.time;
/*  2:   */ 
/*  3:   */ import ADTs.DirectedMultiGraph;
/*  4:   */ import bridge.reps.entities.Entity;
/*  5:   */ import java.util.HashMap;
/*  6:   */ import java.util.HashSet;
/*  7:   */ import java.util.Iterator;
/*  8:   */ import java.util.Map;
/*  9:   */ 
/* 10:   */ public class TimeLine
/* 11:   */   implements Time
/* 12:   */ {
/* 13:   */   private static TimeLine timeline;
/* 14:   */   
/* 15:   */   public static TimeLine getTimeLine()
/* 16:   */   {
/* 17:20 */     if (timeline == null) {
/* 18:21 */       timeline = new TimeLine();
/* 19:   */     }
/* 20:23 */     return timeline;
/* 21:   */   }
/* 22:   */   
/* 23:28 */   private DirectedMultiGraph<Entity, TimeRelation> graph = new DirectedMultiGraph();
/* 24:   */   
/* 25:   */   public synchronized void addBefore(Entity a, Entity b)
/* 26:   */   {
/* 27:32 */     this.graph.addEdge(a, b, TimeRelation.before);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public synchronized void addMeets(Entity a, Entity b)
/* 31:   */   {
/* 32:36 */     this.graph.addEdge(a, b, TimeRelation.meets);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public synchronized void addStarts(Entity a, Entity b)
/* 36:   */   {
/* 37:39 */     this.graph.addEdge(a, b, TimeRelation.starts);
/* 38:40 */     this.graph.addEdge(b, a, TimeRelation.starts);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public synchronized void addFinishes(Entity a, Entity b)
/* 42:   */   {
/* 43:43 */     this.graph.addEdge(a, b, TimeRelation.finishes);
/* 44:44 */     this.graph.addEdge(b, a, TimeRelation.finishes);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public synchronized void addOverlaps(Entity a, Entity b)
/* 48:   */   {
/* 49:47 */     this.graph.addEdge(a, b, TimeRelation.overlaps);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public synchronized void addDuring(Entity sub, Entity sup)
/* 53:   */   {
/* 54:50 */     this.graph.addEdge(sub, sup, TimeRelation.during);
/* 55:   */   }
/* 56:   */   
/* 57:   */   public synchronized void addEquals(Entity a, Entity b)
/* 58:   */   {
/* 59:53 */     this.graph.addEdge(a, b, TimeRelation.equals);
/* 60:54 */     this.graph.addEdge(b, a, TimeRelation.equals);
/* 61:   */   }
/* 62:   */   
/* 63:   */   public synchronized TimeRelation getRelation(Entity a, Entity b)
/* 64:   */   {
/* 65:60 */     Iterator localIterator = this.graph.getEdgesBetween(a, b).iterator();
/* 66:60 */     if (localIterator.hasNext())
/* 67:   */     {
/* 68:60 */       TimeRelation t = (TimeRelation)localIterator.next();
/* 69:61 */       return t;
/* 70:   */     }
/* 71:63 */     return null;
/* 72:   */   }
/* 73:   */   
/* 74:66 */   private Map<Entity, Long> timestamps = new HashMap();
/* 75:   */   
/* 76:   */   public long getTimestamp(Entity t)
/* 77:   */   {
/* 78:75 */     if (this.timestamps.get(t) == null) {
/* 79:75 */       return 0L;
/* 80:   */     }
/* 81:76 */     return ((Long)this.timestamps.get(t)).longValue();
/* 82:   */   }
/* 83:   */   
/* 84:   */   public void timestamp(Entity t, long time)
/* 85:   */   {
/* 86:80 */     this.timestamps.put(t, Long.valueOf(time));
/* 87:   */   }
/* 88:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.time.TimeLine
 * JD-Core Version:    0.7.0.1
 */