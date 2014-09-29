/*   1:    */ package memory.operators;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import bridge.reps.entities.Thread;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Collections;
/*  10:    */ import java.util.HashSet;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Vector;
/*  14:    */ 
/*  15:    */ public class OperatorHelpers
/*  16:    */ {
/*  17:    */   public static int numberLacking(Thread thread, ArrayList<Entity> things)
/*  18:    */   {
/*  19: 21 */     int count = 0;
/*  20: 22 */     for (Entity thing : things) {
/*  21: 23 */       if (!thing.getBundle().contains(thread)) {
/*  22: 24 */         count++;
/*  23:    */       }
/*  24:    */     }
/*  25: 27 */     return count;
/*  26:    */   }
/*  27:    */   
/*  28:    */   protected static int getNumberCrossing(ArrayList<Integer> match, ArrayList<ArrayList<Integer>> lines)
/*  29:    */   {
/*  30: 31 */     int counter = 0;
/*  31: 32 */     int i = ((Integer)match.get(0)).intValue();
/*  32: 33 */     int j = ((Integer)match.get(1)).intValue();
/*  33: 34 */     for (int a = 0; a < lines.size(); a++)
/*  34:    */     {
/*  35: 35 */       if ((i < ((Integer)((ArrayList)lines.get(a)).get(0)).intValue()) && (j > ((Integer)((ArrayList)lines.get(a)).get(1)).intValue())) {
/*  36: 36 */         counter++;
/*  37:    */       }
/*  38: 38 */       if ((i > ((Integer)((ArrayList)lines.get(a)).get(0)).intValue()) && (j < ((Integer)((ArrayList)lines.get(a)).get(1)).intValue())) {
/*  39: 39 */         counter++;
/*  40:    */       }
/*  41:    */     }
/*  42: 42 */     return counter;
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected static ArrayList<ArrayList<Integer>> getLinks(Sequence s1, Sequence s2)
/*  46:    */   {
/*  47: 54 */     ArrayList<ArrayList<Integer>> matches = new ArrayList();
/*  48: 55 */     for (int i = 0; i < s1.getElements().size(); i++) {
/*  49: 57 */       for (Iterator localIterator = getIndexBestMatch(s1.getElement(i), s2).iterator(); localIterator.hasNext();)
/*  50:    */       {
/*  51: 57 */         int k = ((Integer)localIterator.next()).intValue();
/*  52: 58 */         ArrayList<Integer> temp = new ArrayList();
/*  53: 59 */         temp.add(Integer.valueOf(i));
/*  54: 60 */         temp.add(Integer.valueOf(k));
/*  55: 61 */         matches.add(temp);
/*  56:    */       }
/*  57:    */     }
/*  58: 65 */     System.out.println("Raw matches: " + matches);
/*  59:    */     for (;;)
/*  60:    */     {
/*  61: 68 */       ArrayList<Integer> worst = new ArrayList();
/*  62: 69 */       worst.add(Integer.valueOf(0));
/*  63: 70 */       worst.add(Integer.valueOf(-1));
/*  64: 71 */       for (int i = 0; i < matches.size(); i++)
/*  65:    */       {
/*  66: 72 */         int num = getNumberCrossing((ArrayList)matches.get(i), matches);
/*  67: 73 */         if (((Integer)worst.get(0)).intValue() < num)
/*  68:    */         {
/*  69: 74 */           worst.set(0, Integer.valueOf(num));
/*  70: 75 */           worst.set(1, Integer.valueOf(i));
/*  71:    */         }
/*  72:    */       }
/*  73: 78 */       if (((Integer)worst.get(1)).intValue() == -1) {
/*  74:    */         break;
/*  75:    */       }
/*  76: 79 */       int removeInt = ((Integer)worst.get(1)).intValue();
/*  77: 80 */       matches.remove(removeInt);
/*  78:    */     }
/*  79: 82 */     System.out.println("Cross-free matches: " + matches);
/*  80: 84 */     for (int i = 0; i < matches.size(); i++) {
/*  81: 85 */       for (int j = 0; j < matches.size(); j++) {
/*  82: 86 */         if ((j != i) && (
/*  83: 87 */           (((ArrayList)matches.get(i)).get(0) == ((ArrayList)matches.get(j)).get(0)) || (((ArrayList)matches.get(i)).get(1) == ((ArrayList)matches.get(j)).get(1)))) {
/*  84: 88 */           matches.remove(j);
/*  85:    */         }
/*  86:    */       }
/*  87:    */     }
/*  88: 92 */     System.out.println("Final matches: " + matches);
/*  89: 93 */     return matches;
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected static ArrayList<Integer> getIndexBestMatch(Entity t, Sequence s)
/*  93:    */   {
/*  94: 97 */     ArrayList<Integer> ints = new ArrayList();
/*  95: 98 */     ArrayList<Double> scores = new ArrayList();
/*  96: 99 */     for (Entity targ : s.getElements()) {
/*  97:100 */       scores.add(Double.valueOf(Operators.compare(t, targ)));
/*  98:    */     }
/*  99:102 */     double minVal = ((Double)Collections.min(scores)).doubleValue();
/* 100:104 */     if (minVal < 0.5D) {
/* 101:    */       for (;;)
/* 102:    */       {
/* 103:106 */         int occurance = scores.lastIndexOf(Double.valueOf(minVal));
/* 104:107 */         if (occurance == -1) {
/* 105:    */           break;
/* 106:    */         }
/* 107:108 */         ints.add(Integer.valueOf(occurance));
/* 108:109 */         scores.subList(occurance, scores.size()).clear();
/* 109:    */       }
/* 110:    */     }
/* 111:114 */     return ints;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public static ArrayList<Entity> getNearMisses(Entity targ, ArrayList<Entity> things)
/* 115:    */   {
/* 116:125 */     HashSet<Entity> results = new HashSet();
/* 117:    */     Iterator localIterator2;
/* 118:126 */     for (Iterator localIterator1 = targ.getBundle().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 119:    */     {
/* 120:126 */       Thread t = (Thread)localIterator1.next();
/* 121:127 */       localIterator2 = things.iterator(); continue;Entity miss = (Entity)localIterator2.next();
/* 122:128 */       for (Thread m : miss.getBundle()) {
/* 123:129 */         if (t.getSupertype().equals(m.getSupertype())) {
/* 124:130 */           results.add(miss);
/* 125:    */         }
/* 126:    */       }
/* 127:    */     }
/* 128:135 */     return new ArrayList(results);
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected static int getShortestList(ArrayList<ArrayList<Entity>> list, int except)
/* 132:    */   {
/* 133:146 */     int l = 2147483647;
/* 134:147 */     for (int i = 0; i < list.size(); i++) {
/* 135:148 */       if (i != except) {
/* 136:150 */         if (((ArrayList)list.get(i)).size() < l) {
/* 137:151 */           l = ((ArrayList)list.get(i)).size();
/* 138:    */         }
/* 139:    */       }
/* 140:    */     }
/* 141:153 */     return l;
/* 142:    */   }
/* 143:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.operators.OperatorHelpers
 * JD-Core Version:    0.7.0.1
 */