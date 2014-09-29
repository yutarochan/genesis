/*   1:    */ package memory.soms;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Set;
/*   7:    */ import junit.framework.TestCase;
/*   8:    */ import memory.soms.mergers.EntityMerger;
/*   9:    */ import memory.soms.metrics.EntityMetric;
/*  10:    */ 
/*  11:    */ public class NewSomTest
/*  12:    */   extends TestCase
/*  13:    */ {
/*  14:    */   private Som<Entity> som;
/*  15:    */   private Entity t1;
/*  16:    */   private Entity t2;
/*  17:    */   private Entity t3;
/*  18:    */   private Entity t4;
/*  19:    */   private Entity t5;
/*  20:    */   
/*  21:    */   public void setUp()
/*  22:    */   {
/*  23: 18 */     this.som = new NewSom(new EntityMetric(), new EntityMerger(), 0.4D);
/*  24:    */     
/*  25: 20 */     this.t1 = new Entity();
/*  26: 21 */     this.t1.addTypes("thing", "living animal human programmer male sam");
/*  27: 22 */     this.t2 = new Entity();
/*  28: 23 */     this.t2.addTypes("thing", "living animal human programmer male adam");
/*  29: 24 */     this.t3 = new Entity();
/*  30: 25 */     this.t3.addTypes("thing", "living animal human programmer female lucy");
/*  31: 26 */     this.t4 = new Entity();
/*  32: 27 */     this.t4.addTypes("thing", "living animal dog fido");
/*  33: 28 */     this.t5 = new Entity();
/*  34: 29 */     this.t5.addTypes("thing", "dead rotting corpse");
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void testSomConstructors()
/*  38:    */   {
/*  39: 36 */     Som<Entity> s = new NewSom(new EntityMetric(), new EntityMerger(), 0.4D);
/*  40: 37 */     assertTrue(s.getMemory().isEmpty());
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void testAdd()
/*  44:    */   {
/*  45: 44 */     this.som.add(this.t1);
/*  46: 45 */     assertTrue(this.som.getMemory().contains(this.t1));
/*  47: 46 */     this.som.add(this.t5);
/*  48: 47 */     assertTrue(this.som.getMemory().contains(this.t5));
/*  49: 48 */     assertFalse(this.som.neighbors(this.t1).contains(this.t5));
/*  50: 49 */     assertFalse(this.som.neighbors(this.t5).contains(this.t1));
/*  51: 50 */     this.som.add(this.t2);
/*  52: 51 */     assertFalse(this.som.neighbors(this.t5).contains(this.t5));
/*  53: 52 */     assertTrue(this.som.containsEquivalent(this.t2));
/*  54: 53 */     assertFalse(this.som.getMemory().contains(this.t1));
/*  55: 54 */     assertEquals(3, this.som.getMemory().size());
/*  56:    */     
/*  57: 56 */     Entity t1b = new Entity();
/*  58: 57 */     t1b.addTypes("thing", "living animal human programmer male");
/*  59:    */     
/*  60: 59 */     assertTrue(this.som.containsEquivalent(t1b));
/*  61:    */     
/*  62: 61 */     this.som.add(this.t3);
/*  63: 62 */     this.som.add(this.t4);
/*  64:    */     
/*  65: 64 */     assertTrue(this.som.getMemory().contains(this.t3));
/*  66: 65 */     assertTrue(this.som.getMemory().contains(this.t4));
/*  67: 66 */     assertFalse(this.som.getMemory().contains(this.t2));
/*  68: 67 */     assertTrue(this.som.containsEquivalent(t1b));
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void testContainsEquivalent()
/*  72:    */   {
/*  73: 71 */     this.som.add(this.t1);
/*  74: 72 */     assertTrue(this.som.containsEquivalent(this.t1));
/*  75: 73 */     assertFalse(this.som.containsEquivalent(null));
/*  76: 74 */     assertFalse(this.som.containsEquivalent(this.t2));
/*  77:    */     
/*  78: 76 */     this.som.add(this.t5);
/*  79: 77 */     assertTrue(this.som.containsEquivalent(this.t1));
/*  80: 78 */     assertFalse(this.som.containsEquivalent(null));
/*  81: 79 */     assertFalse(this.som.containsEquivalent(this.t2));
/*  82: 80 */     assertTrue(this.som.containsEquivalent(this.t5));
/*  83:    */     
/*  84: 82 */     Entity t1copy = new Entity();
/*  85: 83 */     t1copy.addTypes("thing", "living animal human programmer male sam");
/*  86:    */     
/*  87: 85 */     Entity t5copy = new Entity();
/*  88: 86 */     t5copy.addTypes("thing", "dead rotting corpse");
/*  89:    */     
/*  90: 88 */     assertTrue(this.som.containsEquivalent(t1copy));
/*  91: 89 */     assertTrue(this.som.containsEquivalent(t5copy));
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void testNeighbors()
/*  95:    */   {
/*  96: 94 */     this.som.add(this.t1);
/*  97: 95 */     this.som.add(this.t2);
/*  98: 96 */     this.som.add(this.t3);
/*  99: 97 */     this.som.add(this.t4);
/* 100: 98 */     assertTrue(this.som.neighbors(this.t1).contains(this.t3));
/* 101: 99 */     assertFalse(this.som.neighbors(this.t3).contains(this.t3));
/* 102:    */     
/* 103:101 */     assertTrue(this.som.neighbors(this.t5).isEmpty());
/* 104:102 */     this.som.add(this.t1);
/* 105:103 */     this.som.add(this.t2);
/* 106:104 */     this.som.add(this.t3);
/* 107:105 */     this.som.add(this.t4);
/* 108:    */     
/* 109:107 */     assertTrue(this.som.neighbors(this.t1).contains(this.t3));
/* 110:    */     
/* 111:109 */     assertFalse(this.som.neighbors(this.t3).contains(this.t3));
/* 112:    */     
/* 113:111 */     assertTrue(this.som.neighbors(this.t5).isEmpty());
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void testNearest()
/* 117:    */   {
/* 118:115 */     this.som.add(this.t1);
/* 119:116 */     this.som.add(this.t3);
/* 120:117 */     NewSom<Entity> ns = (NewSom)this.som;
/* 121:118 */     System.out.println(ns);
/* 122:119 */     System.out.println(this.t2);
/* 123:120 */     System.out.println(ns.getNearest(this.t1, 1));
/* 124:121 */     assertEquals(1, ns.getNearest(this.t1, 1).size());
/* 125:122 */     this.som.add(this.t2);
/* 126:123 */     assertTrue(ns.getNearest(this.t1, 1).contains(this.t2));
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void testClone()
/* 130:    */   {
/* 131:127 */     this.som.add(this.t1);
/* 132:128 */     this.som.add(this.t2);
/* 133:129 */     this.som.add(this.t3);
/* 134:130 */     this.som.add(this.t4);
/* 135:131 */     Som<Entity> som2 = this.som.clone();
/* 136:    */     
/* 137:133 */     assertEquals(this.som.getMemory(), som2.getMemory());
/* 138:134 */     assertEquals(this.som.neighbors(this.t1), som2.neighbors(this.t1));
/* 139:135 */     assertEquals(this.som.neighbors(this.t4), som2.neighbors(this.t4));
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void testGetDistance()
/* 143:    */   {
/* 144:140 */     this.som.add(this.t3);
/* 145:141 */     this.som.add(this.t4);
/* 146:142 */     this.som.add(this.t5);
/* 147:143 */     EntityMetric dm = new EntityMetric();
/* 148:144 */     assertEquals(Double.valueOf(dm.distance(this.t3, this.t4)), Double.valueOf(this.som.getDistance(this.t3, this.t4)));
/* 149:145 */     assertEquals(Double.valueOf(dm.distance(this.t5, this.t4)), Double.valueOf(this.som.getDistance(this.t5, this.t4)));
/* 150:146 */     assertEquals(Double.valueOf(1.0D), Double.valueOf(this.som.getDistance(this.t3, null)));
/* 151:147 */     assertEquals(Double.valueOf(dm.distance(this.t1, this.t4)), Double.valueOf(this.som.getDistance(this.t1, this.t4)));
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void testMultithreads()
/* 155:    */   {
/* 156:152 */     for (int i = 0; i < 222; i++)
/* 157:    */     {
/* 158:153 */       Thread thread = new Thread()
/* 159:    */       {
/* 160:    */         public void run()
/* 161:    */         {
/* 162:155 */           NewSomTest.this.som.add(NewSomTest.this.t2);
/* 163:156 */           NewSomTest.this.som.add(NewSomTest.this.t3);
/* 164:157 */           NewSomTest.this.som.getDistance(NewSomTest.this.t2, NewSomTest.this.t3);
/* 165:158 */           NewSomTest.this.som.add(NewSomTest.this.t4);
/* 166:    */         }
/* 167:159 */       };
/* 168:160 */       thread.start();
/* 169:    */     }
/* 170:    */   }
/* 171:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.soms.NewSomTest
 * JD-Core Version:    0.7.0.1
 */