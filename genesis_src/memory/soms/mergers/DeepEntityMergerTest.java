/*   1:    */ package memory.soms.mergers;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Function;
/*   6:    */ import bridge.reps.entities.Sequence;
/*   7:    */ import bridge.reps.entities.Thread;
/*   8:    */ import junit.framework.TestCase;
/*   9:    */ import memory.soms.metrics.EntityMetric;
/*  10:    */ 
/*  11:    */ public class DeepEntityMergerTest
/*  12:    */   extends TestCase
/*  13:    */ {
/*  14:    */   private Entity t1;
/*  15:    */   private Entity t2;
/*  16:    */   private Entity t3;
/*  17:    */   private Entity t4;
/*  18:    */   private Entity t5;
/*  19: 18 */   EntityMetric tm = new EntityMetric();
/*  20:    */   
/*  21:    */   public void setUp()
/*  22:    */   {
/*  23: 20 */     this.t1 = new Entity();
/*  24: 21 */     this.t1.addTypes("thing", "living animal human programmer male sam");
/*  25: 22 */     this.t2 = new Entity();
/*  26: 23 */     this.t2.addTypes("thing", "living animal human programmer male adam");
/*  27: 24 */     this.t3 = new Entity();
/*  28: 25 */     this.t3.addTypes("thing", "living animal human programmer female lucy");
/*  29: 26 */     this.t4 = new Entity();
/*  30: 27 */     this.t4.addTypes("thing", "living animal dog fido");
/*  31: 28 */     this.t5 = new Entity();
/*  32: 29 */     this.t5.addTypes("thing", "dead rotting corpse");
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void testThingMerge()
/*  36:    */   {
/*  37: 34 */     Entity t12 = EntityMerger.merge(this.t1, this.t2);
/*  38: 35 */     Entity desired = new Entity();
/*  39: 36 */     desired.addTypes("thing", "living animal human programmer male");
/*  40: 37 */     assertEquals(Double.valueOf(0.0D), Double.valueOf(this.tm.distance(t12, desired)));
/*  41:    */     
/*  42:    */ 
/*  43: 40 */     Entity t1a = new Entity();
/*  44: 41 */     t1a.addTypes("thing", "living animal human programmer male sam");
/*  45: 42 */     Entity t2a = new Entity();
/*  46: 43 */     t2a.addTypes("thing", "living animal human programmer male adam");
/*  47: 44 */     assertEquals(Double.valueOf(0.0D), Double.valueOf(this.tm.distance(this.t1, t1a)));
/*  48: 45 */     assertEquals(Double.valueOf(0.0D), Double.valueOf(this.tm.distance(this.t2, t2a)));
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void testDeepThingMerger()
/*  52:    */   {
/*  53: 49 */     Function d1 = new Function(this.t1);
/*  54: 50 */     Thread thread = new Thread();
/*  55: 51 */     thread.add("thing");
/*  56: 52 */     thread.add("worker");
/*  57: 53 */     thread.add("lab");
/*  58: 54 */     thread.add("csail");
/*  59: 55 */     d1.addThread(new Thread(thread));
/*  60: 56 */     Function d1a = (Function)d1.deepClone();
/*  61: 57 */     Function d2 = new Function(this.t2);
/*  62: 58 */     thread.add("winston");
/*  63: 59 */     d2.addThread(new Thread(thread));
/*  64: 60 */     Function d2a = (Function)d2.deepClone();
/*  65:    */     
/*  66: 62 */     Function d12 = (Function)DeepEntityMerger.merge(d1, d2);
/*  67:    */     
/*  68: 64 */     Entity desiredThing = new Entity();
/*  69: 65 */     desiredThing.addTypes("thing", "living animal human programmer male");
/*  70: 66 */     Function desired = new Function(desiredThing);
/*  71: 67 */     thread.remove("winston");
/*  72: 68 */     desired.addThread(thread);
/*  73:    */     
/*  74: 70 */     assertEquals(Double.valueOf(0.0D), Double.valueOf(this.tm.distance(desired, d12)));
/*  75: 71 */     assertEquals(Double.valueOf(0.0D), Double.valueOf(this.tm.distance(d1, d1a)));
/*  76: 72 */     assertEquals(Double.valueOf(0.0D), Double.valueOf(this.tm.distance(d2, d2a)));
/*  77:    */     
/*  78:    */ 
/*  79: 75 */     Sequence s1 = new Sequence();
/*  80: 76 */     s1.addElement(d1);
/*  81: 77 */     Bundle b = new Bundle();
/*  82: 78 */     Thread thread2 = new Thread();
/*  83: 79 */     thread2.add("thing");
/*  84: 80 */     thread2.add("gamer");
/*  85: 81 */     thread2.add("computer");
/*  86: 82 */     thread2.add("starcraft");
/*  87: 83 */     b.add(thread2);
/*  88: 84 */     d1a.setBundle(b);
/*  89: 85 */     s1.addElement(d1a);
/*  90:    */     
/*  91: 87 */     Sequence s2 = new Sequence();
/*  92: 88 */     s2.addElement(d2);
/*  93: 89 */     Sequence s12 = (Sequence)DeepEntityMerger.merge(s1, s2);
/*  94: 90 */     Sequence s12desired = new Sequence();
/*  95: 91 */     s12desired.addElement(desired);
/*  96:    */     
/*  97: 93 */     assertEquals(Double.valueOf(0.0D), Double.valueOf(this.tm.distance(s12, s12desired)));
/*  98: 94 */     Sequence s21 = (Sequence)DeepEntityMerger.merge(s2, s1);
/*  99: 95 */     Sequence s21desired = (Sequence)s1.deepClone();
/* 100: 96 */     for (Entity tempD : s21desired.getAllComponents()) {
/* 101: 97 */       if (tempD.isA("worker")) {
/* 102: 98 */         ((Function)tempD).setSubject(desiredThing);
/* 103:    */       }
/* 104:    */     }
/* 105:101 */     assertEquals(Double.valueOf(0.0D), Double.valueOf(this.tm.distance(s21, s21desired)));
/* 106:    */   }
/* 107:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.soms.mergers.DeepEntityMergerTest
 * JD-Core Version:    0.7.0.1
 */