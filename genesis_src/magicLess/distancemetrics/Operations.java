/*   1:    */ package magicLess.distancemetrics;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Thread;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Set;
/*  11:    */ import magicLess.specialMath.Tableau;
/*  12:    */ import magicLess.specialMath.TransportationProblem;
/*  13:    */ 
/*  14:    */ public class Operations
/*  15:    */ {
/*  16:    */   public static final double EPSILON = 1.E-009D;
/*  17:    */   public static final double MAX_DIST = 1.0D;
/*  18:    */   
/*  19:    */   public static double distance(Point a, Point b)
/*  20:    */   {
/*  21: 15 */     return a.getDistanceTo(b);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static double distance(Thread a, Thread b)
/*  25:    */   {
/*  26: 18 */     return distance(new ThreadWithSimilarityDistance(a), new ThreadWithSimilarityDistance(b));
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static double distance(Bundle a, Bundle b)
/*  30:    */   {
/*  31: 21 */     return distance(new HungarianBundle(a), new HungarianBundle(b));
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static double distance(Entity a, Entity b)
/*  35:    */   {
/*  36: 24 */     return distance(new EntityPointWithHeuristicDistance(a), new EntityPointWithHeuristicDistance(b));
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static boolean bestMappingHasExactMatch(Bundle a, Bundle b)
/*  40:    */   {
/*  41: 28 */     List al = new ArrayList();
/*  42: 29 */     List bl = new ArrayList();
/*  43: 30 */     for (Thread t : a) {
/*  44: 31 */       al.add(new ThreadWithSimilarityDistance(t));
/*  45:    */     }
/*  46: 33 */     for (Thread t : b) {
/*  47: 34 */       bl.add(new ThreadWithSimilarityDistance(t));
/*  48:    */     }
/*  49: 36 */     HashMap bestMapping = hungarian(al, bl);
/*  50: 37 */     assert (bestMapping.keySet().size() == Math.min(a.size(), b.size()));
/*  51: 38 */     for (Object key : bestMapping.keySet()) {
/*  52: 39 */       if (distance((Point)key, (Point)bestMapping.get(key)) <= 1.E-009D) {
/*  53: 40 */         return true;
/*  54:    */       }
/*  55:    */     }
/*  56: 43 */     return false;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static HashMap<Thread, Thread> getBestMapping(Bundle a, Bundle b)
/*  60:    */   {
/*  61: 47 */     List al = new ArrayList();
/*  62: 48 */     List bl = new ArrayList();
/*  63: 49 */     for (Thread t : a) {
/*  64: 50 */       al.add(new ThreadWithSimilarityDistance(t));
/*  65:    */     }
/*  66: 52 */     for (Thread t : b) {
/*  67: 53 */       bl.add(new ThreadWithSimilarityDistance(t));
/*  68:    */     }
/*  69: 55 */     HashMap bestMapping = hungarian(al, bl);
/*  70: 56 */     Object result = new HashMap();
/*  71: 57 */     for (Object key : bestMapping.keySet())
/*  72:    */     {
/*  73: 58 */       Thread t1 = (Thread)((Point)key).getWrapped();
/*  74: 59 */       Thread t2 = (Thread)((Point)bestMapping.get(key)).getWrapped();
/*  75: 60 */       ((HashMap)result).put(t1, t2);
/*  76:    */     }
/*  77: 62 */     return result;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static HashMap hungarian(List a, List b)
/*  81:    */   {
/*  82: 69 */     List longList = a.size() > b.size() ? a : b;
/*  83: 70 */     List shortList = longList == a ? b : a;
/*  84: 71 */     int maxLen = longList.size();
/*  85: 72 */     int minLen = shortList.size();
/*  86: 73 */     Tableau t = new Tableau(maxLen, maxLen);
/*  87: 76 */     for (int i = 0; i < minLen; i++) {
/*  88: 77 */       for (int j = 0; j < maxLen; j++) {
/*  89: 78 */         t.set(i, j, distance((Point)shortList.get(i), (Point)longList.get(j)));
/*  90:    */       }
/*  91:    */     }
/*  92: 81 */     for (int i = minLen; i < maxLen; i++) {
/*  93: 82 */       for (int j = 0; j < maxLen; j++) {
/*  94: 83 */         t.set(i, j, 0.0D);
/*  95:    */       }
/*  96:    */     }
/*  97: 86 */     t = TransportationProblem.doHungarian(t);
/*  98:    */     
/*  99: 88 */     HashMap result = new HashMap();
/* 100: 89 */     for (int i = 0; i < minLen; i++) {
/* 101: 90 */       for (int j = 0; j < maxLen; j++) {
/* 102: 91 */         if (t.isStarred(i, j)) {
/* 103: 92 */           result.put(shortList.get(i), longList.get(j));
/* 104:    */         }
/* 105:    */       }
/* 106:    */     }
/* 107: 96 */     return result;
/* 108:    */   }
/* 109:    */   
/* 110:    */   private static class MutInt
/* 111:    */   {
/* 112:    */     public int foo;
/* 113:    */     
/* 114:    */     public int hashCode()
/* 115:    */     {
/* 116:104 */       return this.foo;
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   private static class Test
/* 121:    */     extends Point<Operations.MutInt>
/* 122:    */   {
/* 123:    */     private Operations.MutInt f;
/* 124:    */     
/* 125:    */     protected double getDistance(Operations.MutInt a, Operations.MutInt b)
/* 126:    */     {
/* 127:109 */       return Math.abs(a.foo - b.foo);
/* 128:    */     }
/* 129:    */     
/* 130:    */     public Operations.MutInt getWrapped()
/* 131:    */     {
/* 132:113 */       if (this.f == null) {
/* 133:113 */         this.f = new Operations.MutInt(null);
/* 134:    */       }
/* 135:114 */       return this.f;
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   public static void main(String[] args)
/* 140:    */   {
/* 141:120 */     Point<MutInt> bla = new Test(null);
/* 142:121 */     ((MutInt)bla.getWrapped()).foo = 5;
/* 143:122 */     Test bar = new Test(null);
/* 144:123 */     bar.getWrapped().foo = 6;
/* 145:124 */     System.out.println(distance(bla, bar));
/* 146:125 */     Thread t = new Thread("woot");
/* 147:126 */     Point<Thread> pt = new ThreadWithSimilarityDistance(t);
/* 148:127 */     System.out.println("this is an error:");
/* 149:128 */     System.out.flush();
/* 150:129 */     System.err.flush();
/* 151:130 */     System.out.println(distance(bla, pt));
/* 152:    */   }
/* 153:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     magicLess.distancemetrics.Operations
 * JD-Core Version:    0.7.0.1
 */