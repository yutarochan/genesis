/*   1:    */ package memory.utilities;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Thread;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Collections;
/*   8:    */ import java.util.HashSet;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Set;
/*  12:    */ import magicLess.distancemetrics.Operations;
/*  13:    */ import magicLess.distancemetrics.Point;
/*  14:    */ import magicLess.utils.EntityUtils;
/*  15:    */ 
/*  16:    */ public class Distances
/*  17:    */ {
/*  18:    */   public static double distance(Entity t1, Entity t2)
/*  19:    */   {
/*  20: 29 */     if ((t1.getChildren().isEmpty()) && (t2.getChildren().isEmpty())) {
/*  21: 30 */       return distance(t1.getBundle(), t2.getBundle());
/*  22:    */     }
/*  23: 32 */     double score = 0.3333333333333333D * getChildrenAverage(EntityUtils.getOrderedChildren(t1), EntityUtils.getOrderedChildren(t2)) + 
/*  24: 33 */       0.6666666666666666D * distance(t1.getBundle(), t2.getBundle());
/*  25:    */     
/*  26:    */ 
/*  27: 36 */     return score;
/*  28:    */   }
/*  29:    */   
/*  30:    */   private static double getChildrenAverage(List<Entity> t1Level, List<Entity> t2Level)
/*  31:    */   {
/*  32: 40 */     List<Entity> temp1 = new ArrayList(t1Level);
/*  33: 41 */     List<Entity> temp2 = new ArrayList(t2Level);
/*  34:    */     
/*  35:    */ 
/*  36:    */ 
/*  37: 45 */     Map<Entity, Entity> pairing = NeedlemanWunsch.pair(temp1, temp2);
/*  38:    */     
/*  39: 47 */     double sum = 0.0D;
/*  40: 48 */     int count = 0;
/*  41: 50 */     for (Entity p1 : pairing.keySet())
/*  42:    */     {
/*  43: 51 */       count++;
/*  44: 52 */       sum += distance(p1, (Entity)pairing.get(p1));
/*  45:    */     }
/*  46: 55 */     temp1.removeAll(pairing.keySet());
/*  47: 56 */     for (Entity t : temp1)
/*  48:    */     {
/*  49: 57 */       sum += 1.0D;
/*  50: 58 */       count++;
/*  51:    */     }
/*  52: 60 */     temp2.removeAll(pairing.values());
/*  53: 61 */     for (Entity t : temp2)
/*  54:    */     {
/*  55: 62 */       sum += 1.0D;
/*  56: 63 */       count++;
/*  57:    */     }
/*  58: 65 */     double avg = sum / count;
/*  59:    */     
/*  60: 67 */     return avg;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static double distance(Bundle b1, Bundle b2)
/*  64:    */   {
/*  65: 82 */     if ((b1.size() == 0) || (b2.size() == 0)) {
/*  66: 82 */       return 1.0D;
/*  67:    */     }
/*  68: 83 */     List<ThreadPoint> b1Threads = new ArrayList(b1.size());
/*  69: 84 */     List<ThreadPoint> b2Threads = new ArrayList(b2.size());
/*  70: 85 */     for (Thread t : b1) {
/*  71: 86 */       b1Threads.add(new ThreadPoint(t));
/*  72:    */     }
/*  73: 88 */     for (Thread t : b2) {
/*  74: 89 */       b2Threads.add(new ThreadPoint(t));
/*  75:    */     }
/*  76: 91 */     Map<ThreadPoint, ThreadPoint> optimalPairing = Operations.hungarian(b1Threads, b2Threads);
/*  77:    */     
/*  78: 93 */     double score = 0.0D;
/*  79: 94 */     for (ThreadPoint p : optimalPairing.keySet()) {
/*  80: 95 */       score += p.getDistanceTo((Point)optimalPairing.get(p));
/*  81:    */     }
/*  82: 99 */     double unmatched = Math.max(b1Threads.size(), b2Threads.size()) - optimalPairing.keySet().size();
/*  83:100 */     score += unmatched;
/*  84:101 */     return score / Math.max(b1Threads.size(), b2Threads.size());
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static double distance(Thread t1, Thread t2)
/*  88:    */   {
/*  89:116 */     if ((t1 == null) && (t2 == null)) {
/*  90:116 */       return 0.0D;
/*  91:    */     }
/*  92:117 */     if ((t1 == null) || (t2 == null)) {
/*  93:117 */       return 1.0D;
/*  94:    */     }
/*  95:118 */     if ((t1.isEmpty()) && (t2.isEmpty())) {
/*  96:118 */       return 0.0D;
/*  97:    */     }
/*  98:119 */     if ((t1.isEmpty()) || (t2.isEmpty())) {
/*  99:119 */       return 1.0D;
/* 100:    */     }
/* 101:122 */     boolean t1Larger = t1.size() > t2.size();
/* 102:    */     Thread smaller;
/* 103:    */     Thread larger;
/* 104:    */     Thread smaller;
/* 105:123 */     if (t1Larger)
/* 106:    */     {
/* 107:124 */       Thread larger = new Thread(t1);
/* 108:125 */       smaller = new Thread(t2);
/* 109:    */     }
/* 110:    */     else
/* 111:    */     {
/* 112:128 */       larger = new Thread(t2);
/* 113:129 */       smaller = new Thread(t1);
/* 114:    */     }
/* 115:131 */     Thread overlap = new Thread();
/* 116:132 */     for (String s : larger) {
/* 117:133 */       if (smaller.contains(s)) {
/* 118:134 */         overlap.add(s);
/* 119:    */       }
/* 120:    */     }
/* 121:139 */     double ans = shortenTillSubSet(overlap, larger);
/* 122:    */     
/* 123:141 */     return ans;
/* 124:    */   }
/* 125:    */   
/* 126:    */   private static double shortenTillSubSet(Thread overlap, Thread larger)
/* 127:    */   {
/* 128:145 */     if (SubSetUtils.isSubSet(overlap, larger)) {
/* 129:146 */       return (larger.size() - overlap.size()) / larger.size();
/* 130:    */     }
/* 131:148 */     Set<Double> scores = new HashSet();
/* 132:149 */     for (String s : overlap)
/* 133:    */     {
/* 134:150 */       Thread temp = new Thread(overlap);
/* 135:151 */       temp.remove(s);
/* 136:152 */       double score = shortenTillSubSet(temp, larger);
/* 137:153 */       scores.add(Double.valueOf(score));
/* 138:    */     }
/* 139:155 */     return ((Double)Collections.min(scores)).doubleValue();
/* 140:    */   }
/* 141:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.utilities.Distances
 * JD-Core Version:    0.7.0.1
 */