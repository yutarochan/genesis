/*  1:   */ package memory.soms.mergers;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Bundle;
/*  4:   */ import bridge.reps.entities.Entity;
/*  5:   */ import bridge.reps.entities.Thread;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import java.util.HashSet;
/*  8:   */ import java.util.List;
/*  9:   */ import java.util.Map;
/* 10:   */ import java.util.Set;
/* 11:   */ import magicLess.distancemetrics.Operations;
/* 12:   */ import magicLess.distancemetrics.Point;
/* 13:   */ import magicLess.distancemetrics.ThreadWithSimilarityDistance;
/* 14:   */ import memory.soms.ElementMerger;
/* 15:   */ 
/* 16:   */ public class EntityMerger
/* 17:   */   implements ElementMerger<Entity>
/* 18:   */ {
/* 19:   */   public Set<Entity> merge(Entity seed, Set<Entity> neighbors)
/* 20:   */   {
/* 21:25 */     Set<Entity> results = new HashSet();
/* 22:27 */     for (Entity targ : neighbors) {
/* 23:28 */       results.add(merge(seed, targ));
/* 24:   */     }
/* 25:30 */     return results;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static Entity merge(Entity seed, Entity targ)
/* 29:   */   {
/* 30:42 */     Entity newThing = new Entity();
/* 31:43 */     Bundle newBundle = new Bundle();
/* 32:44 */     newThing.setBundle(newBundle);
/* 33:   */     
/* 34:   */ 
/* 35:47 */     List<Point<Thread>> seedList = getPointList(seed.getBundle());
/* 36:48 */     List<Point<Thread>> targList = getPointList(targ.getBundle());
/* 37:49 */     Map<Point<Thread>, Point<Thread>> bestMatches = Operations.hungarian(seedList, targList);
/* 38:51 */     if (bestMatches.keySet().containsAll(seedList))
/* 39:   */     {
/* 40:53 */       for (Point<Thread> t : bestMatches.keySet())
/* 41:   */       {
/* 42:54 */         Thread mergedThread = pruneThread((Thread)t.getWrapped(), (Thread)((Point)bestMatches.get(t)).getWrapped());
/* 43:55 */         targList.remove(bestMatches.get(t));
/* 44:56 */         newBundle.add(mergedThread);
/* 45:   */       }
/* 46:59 */       for (Point<Thread> remaining : targList) {
/* 47:60 */         newBundle.add((Thread)remaining.getWrapped());
/* 48:   */       }
/* 49:   */     }
/* 50:   */     else
/* 51:   */     {
/* 52:65 */       for (Point<Thread> t : bestMatches.keySet())
/* 53:   */       {
/* 54:66 */         Thread mergedThread = pruneThread((Thread)((Point)bestMatches.get(t)).getWrapped(), (Thread)t.getWrapped());
/* 55:67 */         newBundle.add(mergedThread);
/* 56:   */       }
/* 57:   */     }
/* 58:71 */     return newThing;
/* 59:   */   }
/* 60:   */   
/* 61:   */   private static List<Point<Thread>> getPointList(Bundle a)
/* 62:   */   {
/* 63:74 */     List<Point<Thread>> aa = new ArrayList(a.size());
/* 64:75 */     for (Thread t : a) {
/* 65:76 */       aa.add(new ThreadWithSimilarityDistance(t));
/* 66:   */     }
/* 67:78 */     return aa;
/* 68:   */   }
/* 69:   */   
/* 70:   */   private static Thread pruneThread(Thread base, Thread prune)
/* 71:   */   {
/* 72:84 */     if (prune.isEmpty()) {
/* 73:84 */       return prune;
/* 74:   */     }
/* 75:85 */     if (base.equals(prune)) {
/* 76:86 */       return new Thread(prune);
/* 77:   */     }
/* 78:88 */     if (base.contains(prune.get(prune.size() - 1))) {
/* 79:89 */       return new Thread(prune);
/* 80:   */     }
/* 81:91 */     Thread pruned = new Thread(prune);
/* 82:92 */     pruned.remove(prune.get(prune.size() - 1));
/* 83:93 */     return pruned;
/* 84:   */   }
/* 85:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.soms.mergers.EntityMerger
 * JD-Core Version:    0.7.0.1
 */