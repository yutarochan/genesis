/*   1:    */ package magicLess.distancemetrics;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Thread;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.List;
/*   9:    */ 
/*  10:    */ public class HungarianBundle
/*  11:    */   extends Point<Bundle>
/*  12:    */ {
/*  13:    */   public static final double MAX_DIST = 1.0D;
/*  14:    */   private Bundle myBundle;
/*  15:    */   
/*  16:    */   protected double getDistance(Bundle a, Bundle b)
/*  17:    */   {
/*  18: 11 */     if ((a.size() == 0) || (b.size() == 0)) {
/*  19: 11 */       return 1.0D;
/*  20:    */     }
/*  21: 14 */     List<Point<Thread>> aa = new ArrayList(a.size());
/*  22: 15 */     List<Point<Thread>> bb = new ArrayList(b.size());
/*  23: 16 */     for (Thread t : a) {
/*  24: 17 */       aa.add(new ThreadWithSimilarityDistance(t));
/*  25:    */     }
/*  26: 19 */     for (Thread t : b) {
/*  27: 20 */       bb.add(new ThreadWithSimilarityDistance(t));
/*  28:    */     }
/*  29: 22 */     return getDistance(aa, bb);
/*  30:    */   }
/*  31:    */   
/*  32:    */   private double getDistance(List<Point<Thread>> a, List<Point<Thread>> b)
/*  33:    */   {
/*  34: 25 */     HashMap optimalPairing = Operations.hungarian(a, b);
/*  35:    */     
/*  36:    */ 
/*  37: 28 */     double accum = 0.0D;
/*  38: 29 */     for (Object k : optimalPairing.keySet()) {
/*  39: 30 */       accum += Operations.distance((Point)k, (Point)optimalPairing.get(k));
/*  40:    */     }
/*  41: 34 */     return accum / Math.min(a.size(), b.size());
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Bundle getWrapped()
/*  45:    */   {
/*  46: 38 */     return this.myBundle;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public HungarianBundle(Bundle b)
/*  50:    */   {
/*  51: 41 */     this.myBundle = b;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static void main(String[] args)
/*  55:    */   {
/*  56: 46 */     Bundle a = new Bundle();
/*  57: 47 */     Thread at = new Thread();
/*  58: 48 */     at.add("a");
/*  59: 49 */     at.add("b");
/*  60: 50 */     at.add("c");
/*  61: 51 */     at.add("d");
/*  62: 52 */     at.add("e");
/*  63: 53 */     a.add(at);
/*  64: 54 */     at = new Thread();
/*  65: 55 */     at.add("a");
/*  66: 56 */     at.add("b");
/*  67: 57 */     at.add("c");
/*  68: 58 */     at.add("h");
/*  69: 59 */     at.add("q");
/*  70: 60 */     a.add(at);
/*  71: 61 */     at = new Thread();
/*  72: 62 */     at.add("a");
/*  73: 63 */     at.add("b");
/*  74: 64 */     at.add("r");
/*  75: 65 */     at.add("w");
/*  76: 66 */     at.add("g");
/*  77: 67 */     a.add(at);
/*  78: 68 */     at = new Thread();
/*  79: 69 */     at.add("h");
/*  80: 70 */     at.add("q");
/*  81: 71 */     at.add("x");
/*  82: 72 */     a.add(at);
/*  83: 73 */     at = new Thread();
/*  84: 74 */     at.add("h");
/*  85: 75 */     at.add("l");
/*  86: 76 */     at.add("i");
/*  87: 77 */     a.add(at);
/*  88: 78 */     at = new Thread();
/*  89: 79 */     at.add("h");
/*  90: 80 */     at.add("y");
/*  91: 81 */     at.add("i");
/*  92: 82 */     a.add(at);
/*  93: 83 */     at = new Thread();
/*  94: 84 */     at.add("t");
/*  95: 85 */     at.add("r");
/*  96: 86 */     at.add("t");
/*  97: 87 */     a.add(at);
/*  98:    */     
/*  99: 89 */     at = new Thread();
/* 100: 90 */     at.add("e");
/* 101: 91 */     at.add("r");
/* 102: 92 */     at.add("h");
/* 103: 93 */     a.add(at);
/* 104: 94 */     at = new Thread();
/* 105: 95 */     at.add("h");
/* 106: 96 */     at.add("q");
/* 107: 97 */     at.add("t");
/* 108: 98 */     at.add("o");
/* 109: 99 */     a.add(at);
/* 110:    */     
/* 111:    */ 
/* 112:102 */     HungarianBundle b1 = new HungarianBundle(a);
/* 113:    */     
/* 114:104 */     a = new Bundle();
/* 115:105 */     at = new Thread();
/* 116:106 */     at.add("a");
/* 117:107 */     at.add("b");
/* 118:108 */     at.add("c");
/* 119:109 */     at.add("d");
/* 120:110 */     at.add("e");
/* 121:111 */     a.add(at);
/* 122:112 */     at = new Thread();
/* 123:113 */     at.add("a");
/* 124:114 */     at.add("b");
/* 125:115 */     at.add("c");
/* 126:116 */     at.add("o");
/* 127:117 */     at.add("p");
/* 128:118 */     a.add(at);
/* 129:119 */     at = new Thread();
/* 130:120 */     at.add("a");
/* 131:121 */     at.add("b");
/* 132:122 */     at.add("y");
/* 133:123 */     at.add("u");
/* 134:124 */     at.add("r");
/* 135:125 */     a.add(at);
/* 136:126 */     at = new Thread();
/* 137:127 */     at.add("h");
/* 138:128 */     at.add("q");
/* 139:129 */     at.add("t");
/* 140:130 */     a.add(at);
/* 141:131 */     at = new Thread();
/* 142:132 */     at.add("h");
/* 143:133 */     at.add("l");
/* 144:134 */     at.add("p");
/* 145:135 */     a.add(at);
/* 146:136 */     at = new Thread();
/* 147:137 */     at.add("h");
/* 148:138 */     at.add("y");
/* 149:139 */     at.add("e");
/* 150:140 */     a.add(at);
/* 151:141 */     at = new Thread();
/* 152:142 */     at.add("t");
/* 153:143 */     at.add("y");
/* 154:144 */     at.add("t");
/* 155:145 */     a.add(at);
/* 156:146 */     at = new Thread();
/* 157:147 */     at.add("e");
/* 158:148 */     at.add("r");
/* 159:149 */     at.add("t");
/* 160:150 */     a.add(at);
/* 161:151 */     at = new Thread();
/* 162:152 */     at.add("h");
/* 163:153 */     at.add("q");
/* 164:154 */     at.add("t");
/* 165:155 */     at.add("m");
/* 166:156 */     a.add(at);
/* 167:157 */     HungarianBundle b2 = new HungarianBundle(a);
/* 168:    */     
/* 169:159 */     System.out.println(b1);
/* 170:160 */     System.out.println(b2);
/* 171:    */     
/* 172:162 */     System.out.println("hungarian distance: ");
/* 173:163 */     long time = System.currentTimeMillis();
/* 174:164 */     System.out.println(Operations.distance(b1, b2));
/* 175:165 */     time = System.currentTimeMillis() - time;
/* 176:166 */     System.out.println("time for this computation in milliseconds: " + time);
/* 177:    */   }
/* 178:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     magicLess.distancemetrics.HungarianBundle
 * JD-Core Version:    0.7.0.1
 */