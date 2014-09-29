/*   1:    */ package memory.utilities;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Function;
/*   6:    */ import bridge.reps.entities.Sequence;
/*   7:    */ import bridge.reps.entities.Thread;
/*   8:    */ import java.io.PrintStream;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import junit.framework.TestCase;
/*  13:    */ 
/*  14:    */ public class DistancesTest
/*  15:    */   extends TestCase
/*  16:    */ {
/*  17: 14 */   Thread t2 = new Thread();
/*  18: 14 */   Thread t1 = new Thread();
/*  19: 15 */   Bundle b2 = new Bundle();
/*  20: 15 */   Bundle b1 = new Bundle();
/*  21: 16 */   Entity thing1 = new Entity();
/*  22: 17 */   Entity thing2 = new Entity();
/*  23:    */   Entity th1;
/*  24:    */   Entity th2;
/*  25:    */   Entity th3;
/*  26:    */   Entity th4;
/*  27:    */   Entity th5;
/*  28:    */   
/*  29:    */   public void setUp()
/*  30:    */   {
/*  31: 21 */     this.t1.add("thing");
/*  32: 22 */     this.t1.add("element");
/*  33: 23 */     this.t1.add("fire");
/*  34: 24 */     this.t2.add("thing");
/*  35: 25 */     this.t2.add("element");
/*  36: 26 */     this.b1.add(this.t1);
/*  37: 27 */     this.b1.add(this.t2);
/*  38: 28 */     this.b2.add(this.t2);
/*  39: 29 */     this.thing1.setBundle(this.b1);
/*  40: 30 */     this.thing2.setBundle(this.b2);
/*  41:    */     
/*  42: 32 */     this.th1 = new Entity();
/*  43: 33 */     this.th1.addTypes("thing", "living animal human programmer male sam");
/*  44: 34 */     this.th2 = new Entity();
/*  45: 35 */     this.th2.addTypes("thing", "living animal human programmer male adam");
/*  46: 36 */     this.th3 = new Entity();
/*  47: 37 */     this.th3.addTypes("thing", "living animal human programmer female lucy");
/*  48: 38 */     this.th4 = new Entity();
/*  49: 39 */     this.th4.addTypes("thing", "living animal dog fido");
/*  50: 40 */     this.th5 = new Entity();
/*  51: 41 */     this.th5.addTypes("thing", "dead rotting corpse");
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void testDistanceThreads()
/*  55:    */   {
/*  56: 45 */     assertEquals(Double.valueOf(0.3333333333333333D), Double.valueOf(Distances.distance(this.t1, this.t2)));
/*  57: 46 */     assertEquals(Double.valueOf(Distances.distance(this.t2, this.t1)), Double.valueOf(Distances.distance(this.t1, this.t2)));
/*  58: 47 */     this.t1.add("yellow");
/*  59: 48 */     this.t1.add("hot");
/*  60: 49 */     this.t1.add("natural");
/*  61: 50 */     this.t2.add("water");
/*  62: 51 */     this.t2.add("blue");
/*  63: 52 */     this.t2.add("cold");
/*  64: 53 */     this.t2.add("natural");
/*  65: 54 */     assertEquals(Double.valueOf(0.5D), Double.valueOf(Distances.distance(this.t1, this.t2)));
/*  66: 55 */     this.t2.add("hot");
/*  67: 56 */     assertEquals(Double.valueOf(0.4285714285714286D), Double.valueOf(Distances.distance(this.t1, this.t2)));
/*  68: 57 */     assertEquals(Double.valueOf(0.0D), Double.valueOf(Distances.distance(this.t1, this.t1)));
/*  69: 58 */     Thread t3 = new Thread();
/*  70: 59 */     assertEquals(Double.valueOf(1.0D), Double.valueOf(Distances.distance(this.t1, t3)));
/*  71: 60 */     assertEquals(Double.valueOf(0.0D), Double.valueOf(Distances.distance(t3, t3)));
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void testDistanceBundles()
/*  75:    */   {
/*  76: 64 */     assertEquals(Double.valueOf(0.5D), Double.valueOf(Distances.distance(this.b1, this.b2)));
/*  77: 65 */     assertEquals(Double.valueOf(Distances.distance(this.b2, this.b1)), Double.valueOf(Distances.distance(this.b1, this.b2)));
/*  78: 66 */     assertEquals(Double.valueOf(0.0D), Double.valueOf(Distances.distance(this.b2, this.b2)));
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void testDistanceThings()
/*  82:    */   {
/*  83: 71 */     assertEquals(Double.valueOf(Distances.distance(this.b1, this.b2)), Double.valueOf(Distances.distance(this.thing1, this.thing2)));
/*  84: 72 */     assertEquals(Double.valueOf(0.5D), Double.valueOf(Distances.distance(this.thing1, this.thing2)));
/*  85:    */     
/*  86:    */ 
/*  87: 75 */     List<Entity> l1 = new ArrayList();
/*  88: 76 */     l1.add(this.th1);
/*  89: 77 */     l1.add(this.th2);
/*  90: 78 */     l1.add(this.th3);
/*  91:    */     
/*  92: 80 */     List<Entity> l2 = new ArrayList();
/*  93: 81 */     l2.add(this.th2);
/*  94: 82 */     l2.add(this.th3);
/*  95: 83 */     l2.add(this.th1);
/*  96: 84 */     Map<Entity, Entity> pairing = NeedlemanWunsch.pair(l1, l2);
/*  97: 85 */     assertTrue(((Entity)pairing.get(this.th3)).equals(this.th3));
/*  98: 86 */     assertTrue(((Entity)pairing.get(this.th2)).equals(this.th2));
/*  99: 87 */     assertFalse(pairing.containsKey(this.th1));
/* 100:    */     
/* 101:    */ 
/* 102: 90 */     Sequence s1 = new Sequence();
/* 103: 91 */     Function d1 = new Function(this.th1);
/* 104: 92 */     Thread thread = new Thread();
/* 105: 93 */     thread.add("thing");
/* 106: 94 */     thread.add("worker");
/* 107: 95 */     thread.add("lab");
/* 108: 96 */     thread.add("csail");
/* 109: 97 */     d1.addThread(new Thread(thread));
/* 110:    */     
/* 111: 99 */     s1.addElement(this.th1);
/* 112:100 */     s1.addElement(this.th2);
/* 113:101 */     s1.addElement(this.th3);
/* 114:    */     
/* 115:103 */     assertEquals(Double.valueOf(0.5D), Double.valueOf(Distances.distance(s1.getBundle(), d1.getBundle())));
/* 116:    */     
/* 117:    */ 
/* 118:    */ 
/* 119:    */ 
/* 120:    */ 
/* 121:109 */     assertEquals(Double.valueOf(0.5555555555555556D), Double.valueOf(Distances.distance(s1, d1)));
/* 122:    */     
/* 123:111 */     assertEquals(Double.valueOf(0.9523809523809523D), Double.valueOf(Distances.distance(this.th1, d1)));
/* 124:    */     
/* 125:113 */     s1.addElement(d1);
/* 126:    */     
/* 127:115 */     Sequence s2 = new Sequence();
/* 128:116 */     s2.addElement(this.th2);
/* 129:117 */     s2.addElement(this.th3);
/* 130:118 */     s2.addElement(this.th1);
/* 131:    */     
/* 132:    */ 
/* 133:121 */     assertTrue(Math.abs((1.0D + Distances.distance(this.th1, d1)) / 4.0D / 3.0D - Distances.distance(s1, s2)) < 0.001D);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void testDistances2()
/* 137:    */   {
/* 138:126 */     assertEquals(Double.valueOf(0.0D), Double.valueOf(Distances.distance(this.th1, this.th1)));
/* 139:127 */     Function d1 = new Function(this.th1);
/* 140:    */     
/* 141:    */ 
/* 142:130 */     assertEquals(Double.valueOf(0.9047619047619047D), Double.valueOf(Distances.distance(d1, this.th1)));
/* 143:    */     
/* 144:132 */     Function d2 = new Function(new Entity());
/* 145:133 */     d2.setBundle((Bundle)this.th1.getBundle().clone());
/* 146:134 */     System.out.println(this.th1);
/* 147:135 */     System.out.println(d2);
/* 148:136 */     assertEquals(Double.valueOf(0.3333333333333333D), Double.valueOf(Distances.distance(d2, this.th1)));
/* 149:137 */     assertEquals(Double.valueOf(0.3333333333333333D), Double.valueOf(Distances.distance(this.th1, d2)));
/* 150:    */     
/* 151:139 */     Function d3 = new Function(d2);
/* 152:140 */     Function d4 = new Function(this.th1);
/* 153:141 */     assertEquals(Double.valueOf(0.111111111111111D), Double.valueOf(Distances.distance(d3, d4)));
/* 154:    */   }
/* 155:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.utilities.DistancesTest
 * JD-Core Version:    0.7.0.1
 */