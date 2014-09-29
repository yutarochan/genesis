/*   1:    */ package memory.soms.mergers;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Thread;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.HashSet;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import java.util.Set;
/*  13:    */ import lexicons.WorkingVocabulary;
/*  14:    */ import magicLess.distancemetrics.Operations;
/*  15:    */ import magicLess.distancemetrics.Point;
/*  16:    */ import magicLess.distancemetrics.ThreadWithSimilarityDistance;
/*  17:    */ import memory.soms.ElementMerger;
/*  18:    */ import memory.utilities.Distances;
/*  19:    */ 
/*  20:    */ public class ConditionalMerger
/*  21:    */   implements ElementMerger<Entity>
/*  22:    */ {
/*  23:    */   public Set<Entity> merge(Entity seed, Set<Entity> neighbors)
/*  24:    */   {
/*  25: 29 */     Set<Entity> newThings = new HashSet();
/*  26: 30 */     for (Entity n : neighbors)
/*  27:    */     {
/*  28: 31 */       Entity newThing = merge(seed, n);
/*  29: 32 */       newThings.add(newThing);
/*  30:    */     }
/*  31: 35 */     return newThings;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static Entity merge(Entity seed, Entity n)
/*  35:    */   {
/*  36: 46 */     Entity newThing = n.deepClone();
/*  37:    */     
/*  38: 48 */     newThing.setBundle(condMerge(seed, n).getBundle());
/*  39:    */     
/*  40: 50 */     mergeChildren(seed, newThing);
/*  41:    */     
/*  42: 52 */     return newThing;
/*  43:    */   }
/*  44:    */   
/*  45:    */   private static void mergeChildren(Entity seed, Entity n)
/*  46:    */   {
/*  47: 56 */     if (n.getChildren().isEmpty()) {
/*  48: 56 */       return;
/*  49:    */     }
/*  50: 57 */     Map<Entity, Entity> pairing = getOptimalPairing(n.getChildren(), seed.getChildren());
/*  51: 61 */     for (Entity matchingChild : pairing.keySet())
/*  52:    */     {
/*  53: 62 */       Entity seedChild = (Entity)pairing.get(matchingChild);
/*  54: 63 */       if (seedChild != null)
/*  55:    */       {
/*  56: 68 */         matchingChild.setBundle(condMerge(seedChild, matchingChild).getBundle());
/*  57:    */         
/*  58: 70 */         mergeChildren(seedChild, matchingChild);
/*  59:    */       }
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   private static Map<Entity, Entity> getOptimalPairing(Set<Entity> a, Set<Entity> b)
/*  64:    */   {
/*  65: 78 */     Set<Entity> firstSet = new HashSet(a);
/*  66: 79 */     Set<Entity> secondSet = new HashSet(b);
/*  67:    */     
/*  68: 81 */     Map<Entity, Entity> mapping = new HashMap();
/*  69: 83 */     while ((!firstSet.isEmpty()) && (!secondSet.isEmpty()))
/*  70:    */     {
/*  71: 84 */       Entity[] match = getBestMatch(firstSet, secondSet);
/*  72: 85 */       mapping.put(match[0], match[1]);
/*  73: 86 */       firstSet.remove(match[0]);
/*  74: 87 */       secondSet.remove(match[1]);
/*  75:    */     }
/*  76: 90 */     if (!firstSet.isEmpty()) {
/*  77: 91 */       for (Entity leftover : firstSet) {
/*  78: 92 */         mapping.put(leftover, null);
/*  79:    */       }
/*  80:    */     }
/*  81: 96 */     return mapping;
/*  82:    */   }
/*  83:    */   
/*  84:    */   private static Entity[] getBestMatch(Set<Entity> a, Set<Entity> b)
/*  85:    */   {
/*  86:101 */     Entity bestT1 = null;
/*  87:102 */     Entity bestT2 = null;
/*  88:103 */     double bestScore = 2.0D;
/*  89:    */     Iterator localIterator2;
/*  90:104 */     for (Iterator localIterator1 = a.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/*  91:    */     {
/*  92:104 */       Entity t1 = (Entity)localIterator1.next();
/*  93:105 */       localIterator2 = b.iterator(); continue;Entity t2 = (Entity)localIterator2.next();
/*  94:106 */       double score = Distances.distance(t1, t2);
/*  95:107 */       if (score < bestScore)
/*  96:    */       {
/*  97:108 */         bestT1 = t1;
/*  98:109 */         bestT2 = t2;
/*  99:110 */         bestScore = score;
/* 100:    */       }
/* 101:    */     }
/* 102:114 */     Entity[] results = new Entity[2];
/* 103:115 */     results[0] = bestT1;
/* 104:116 */     results[1] = bestT2;
/* 105:117 */     return results;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static Entity condMerge(Entity seed, Entity targ)
/* 109:    */   {
/* 110:133 */     Entity newThing = new Entity();
/* 111:134 */     Bundle newBundle = new Bundle();
/* 112:135 */     newThing.setBundle(newBundle);
/* 113:    */     
/* 114:    */ 
/* 115:138 */     List<Point<Thread>> seedList = getPointList(seed.getBundle());
/* 116:139 */     List<Point<Thread>> targList = getPointList(targ.getBundle());
/* 117:140 */     Map<Point<Thread>, Point<Thread>> bestMatches = Operations.hungarian(seedList, targList);
/* 118:142 */     if (bestMatches.keySet().containsAll(seedList))
/* 119:    */     {
/* 120:144 */       for (Point<Thread> t : bestMatches.keySet())
/* 121:    */       {
/* 122:145 */         Thread mergedThread = pruneThread((Thread)t.getWrapped(), (Thread)((Point)bestMatches.get(t)).getWrapped());
/* 123:146 */         targList.remove(bestMatches.get(t));
/* 124:147 */         newBundle.add(mergedThread);
/* 125:    */       }
/* 126:150 */       for (Point<Thread> remaining : targList) {
/* 127:151 */         newBundle.add((Thread)remaining.getWrapped());
/* 128:    */       }
/* 129:    */     }
/* 130:    */     else
/* 131:    */     {
/* 132:156 */       for (Point<Thread> t : bestMatches.keySet())
/* 133:    */       {
/* 134:157 */         Thread mergedThread = pruneThread((Thread)((Point)bestMatches.get(t)).getWrapped(), (Thread)t.getWrapped());
/* 135:158 */         newBundle.add(mergedThread);
/* 136:    */       }
/* 137:    */     }
/* 138:162 */     return newThing;
/* 139:    */   }
/* 140:    */   
/* 141:    */   private static List<Point<Thread>> getPointList(Bundle a)
/* 142:    */   {
/* 143:165 */     List<Point<Thread>> aa = new ArrayList(a.size());
/* 144:166 */     for (Thread t : a) {
/* 145:167 */       aa.add(new ThreadWithSimilarityDistance(t));
/* 146:    */     }
/* 147:169 */     return aa;
/* 148:    */   }
/* 149:    */   
/* 150:    */   private static Thread pruneThread(Thread base, Thread prune)
/* 151:    */   {
/* 152:175 */     if (prune.isEmpty()) {
/* 153:175 */       return prune;
/* 154:    */     }
/* 155:176 */     if (base.equals(prune)) {
/* 156:177 */       return new Thread(prune);
/* 157:    */     }
/* 158:179 */     if (base.contains(prune.get(prune.size() - 1))) {
/* 159:180 */       return new Thread(prune);
/* 160:    */     }
/* 161:184 */     Thread pruned = new Thread(prune);
/* 162:186 */     for (int i = prune.size() - 2; i > 2; i--)
/* 163:    */     {
/* 164:188 */       String testword = (String)prune.get(i);
/* 165:190 */       if ((base.contains(testword)) && (WorkingVocabulary.getWorkingVocabulary().contains(testword)))
/* 166:    */       {
/* 167:192 */         pruned.remove(i + 1, pruned.size());
/* 168:    */         
/* 169:194 */         break;
/* 170:    */       }
/* 171:    */     }
/* 172:199 */     return pruned;
/* 173:    */   }
/* 174:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.soms.mergers.ConditionalMerger
 * JD-Core Version:    0.7.0.1
 */