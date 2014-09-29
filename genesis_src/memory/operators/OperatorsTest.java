/*  1:   */ package memory.operators;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Thread;
/*  5:   */ import junit.framework.TestCase;
/*  6:   */ 
/*  7:   */ public class OperatorsTest
/*  8:   */   extends TestCase
/*  9:   */ {
/* 10:   */   public void setUp() {}
/* 11:   */   
/* 12:   */   public void testThreadCompare()
/* 13:   */   {
/* 14: 9 */     Thread a = new Thread();
/* 15:10 */     Thread b = new Thread();
/* 16:11 */     a.add("thing");
/* 17:12 */     b.add("thing");
/* 18:13 */     a.add("place");
/* 19:14 */     b.add("place");
/* 20:15 */     a.add("property");
/* 21:16 */     a.add("garage");
/* 22:17 */     b.add("property");
/* 23:18 */     b.add("home");
/* 24:19 */     b.add("house");
/* 25:20 */     assertEquals(Double.valueOf(0.3333333333333333D), Double.valueOf(Operators.compare(a, b)));
/* 26:   */     
/* 27:22 */     Thread c = new Thread();
/* 28:23 */     c.add("thing");
/* 29:24 */     assertEquals(Double.valueOf(0.6D), Double.valueOf(Operators.compare(a, c)));
/* 30:   */     
/* 31:26 */     c.add("garage");
/* 32:27 */     assertEquals(Double.valueOf(0.3333333333333333D), Double.valueOf(Operators.compare(a, c)));
/* 33:   */     
/* 34:29 */     c.add("door");
/* 35:30 */     assertEquals(Double.valueOf(0.4285714285714286D), Double.valueOf(Operators.compare(a, c)));
/* 36:   */     
/* 37:32 */     c.add("property");
/* 38:33 */     assertEquals(Double.valueOf(0.25D), Double.valueOf(Operators.compare(a, c)));
/* 39:   */     
/* 40:   */ 
/* 41:36 */     assertEquals(Double.valueOf(1.0D), Double.valueOf(Operators.compare(a, null)));
/* 42:37 */     assertEquals(Double.valueOf(0.0D), Double.valueOf(Operators.compare(null, null)));
/* 43:   */     
/* 44:   */ 
/* 45:40 */     assertEquals(Double.valueOf(Operators.compare(a, c)), Double.valueOf(Operators.compare(c, a)));
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void testThingCompare()
/* 49:   */   {
/* 50:45 */     Entity t1 = new Entity();
/* 51:46 */     Entity t2 = new Entity();
/* 52:47 */     t1.addTypes("thing", "person male student sam");
/* 53:48 */     t2.addTypes("thing", "person male student adam");
/* 54:   */     
/* 55:50 */     assertEquals(Double.valueOf(0.2D), Double.valueOf(Operators.compare(t1, t2)));
/* 56:   */     
/* 57:52 */     t1.addTypes("occupation", "researcher AI");
/* 58:53 */     assertEquals(Double.valueOf(0.6D), Double.valueOf(Operators.compare(t1, t2)));
/* 59:   */     
/* 60:   */ 
/* 61:56 */     assertEquals(Double.valueOf(1.0D), Double.valueOf(Operators.compare(t1, null)));
/* 62:57 */     assertEquals(Double.valueOf(0.0D), Double.valueOf(Operators.compare(null, null)));
/* 63:   */     
/* 64:   */ 
/* 65:60 */     assertEquals(Double.valueOf(Operators.compare(t1, t2)), Double.valueOf(Operators.compare(t2, t1)));
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.operators.OperatorsTest
 * JD-Core Version:    0.7.0.1
 */