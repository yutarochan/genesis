/*   1:    */ package memory.soms.mergers;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Set;
/*   9:    */ import memory.soms.ElementMerger;
/*  10:    */ import memory.utilities.Distances;
/*  11:    */ 
/*  12:    */ public class DeepEntityMerger
/*  13:    */   implements ElementMerger<Entity>
/*  14:    */ {
/*  15:    */   public Set<Entity> merge(Entity seed, Set<Entity> neighbors)
/*  16:    */   {
/*  17: 18 */     Set<Entity> newThings = new HashSet();
/*  18: 19 */     for (Entity n : neighbors)
/*  19:    */     {
/*  20: 20 */       Entity newThing = merge(seed, n);
/*  21: 21 */       newThings.add(newThing);
/*  22:    */     }
/*  23: 24 */     return newThings;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static Entity merge(Entity seed, Entity n)
/*  27:    */   {
/*  28: 35 */     Entity newThing = n.deepClone();
/*  29:    */     
/*  30: 37 */     newThing.setBundle(EntityMerger.merge(seed, n).getBundle());
/*  31:    */     
/*  32: 39 */     mergeChildren(seed, newThing);
/*  33:    */     
/*  34: 41 */     return newThing;
/*  35:    */   }
/*  36:    */   
/*  37:    */   private static void mergeChildren(Entity seed, Entity n)
/*  38:    */   {
/*  39: 45 */     if (n.getChildren().isEmpty()) {
/*  40: 45 */       return;
/*  41:    */     }
/*  42: 46 */     Map<Entity, Entity> pairing = getOptimalPairing(n.getChildren(), seed.getChildren());
/*  43: 50 */     for (Entity matchingChild : pairing.keySet())
/*  44:    */     {
/*  45: 51 */       Entity seedChild = (Entity)pairing.get(matchingChild);
/*  46: 52 */       if (seedChild != null)
/*  47:    */       {
/*  48: 57 */         matchingChild.setBundle(EntityMerger.merge(seedChild, matchingChild).getBundle());
/*  49:    */         
/*  50: 59 */         mergeChildren(seedChild, matchingChild);
/*  51:    */       }
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   private static Map<Entity, Entity> getOptimalPairing(Set<Entity> a, Set<Entity> b)
/*  56:    */   {
/*  57: 67 */     Set<Entity> firstSet = new HashSet(a);
/*  58: 68 */     Set<Entity> secondSet = new HashSet(b);
/*  59:    */     
/*  60: 70 */     Map<Entity, Entity> mapping = new HashMap();
/*  61: 72 */     while ((!firstSet.isEmpty()) && (!secondSet.isEmpty()))
/*  62:    */     {
/*  63: 73 */       Entity[] match = getBestMatch(firstSet, secondSet);
/*  64: 74 */       mapping.put(match[0], match[1]);
/*  65: 75 */       firstSet.remove(match[0]);
/*  66: 76 */       secondSet.remove(match[1]);
/*  67:    */     }
/*  68: 79 */     if (!firstSet.isEmpty()) {
/*  69: 80 */       for (Entity leftover : firstSet) {
/*  70: 81 */         mapping.put(leftover, null);
/*  71:    */       }
/*  72:    */     }
/*  73: 85 */     return mapping;
/*  74:    */   }
/*  75:    */   
/*  76:    */   private static Entity[] getBestMatch(Set<Entity> a, Set<Entity> b)
/*  77:    */   {
/*  78: 90 */     Entity bestT1 = null;
/*  79: 91 */     Entity bestT2 = null;
/*  80: 92 */     double bestScore = 2.0D;
/*  81:    */     Iterator localIterator2;
/*  82: 93 */     for (Iterator localIterator1 = a.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/*  83:    */     {
/*  84: 93 */       Entity t1 = (Entity)localIterator1.next();
/*  85: 94 */       localIterator2 = b.iterator(); continue;Entity t2 = (Entity)localIterator2.next();
/*  86: 95 */       double score = Distances.distance(t1, t2);
/*  87: 96 */       if (score < bestScore)
/*  88:    */       {
/*  89: 97 */         bestT1 = t1;
/*  90: 98 */         bestT2 = t2;
/*  91: 99 */         bestScore = score;
/*  92:    */       }
/*  93:    */     }
/*  94:103 */     Entity[] results = new Entity[2];
/*  95:104 */     results[0] = bestT1;
/*  96:105 */     results[1] = bestT2;
/*  97:106 */     return results;
/*  98:    */   }
/*  99:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.soms.mergers.DeepEntityMerger
 * JD-Core Version:    0.7.0.1
 */