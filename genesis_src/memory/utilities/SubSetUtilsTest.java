/*  1:   */ package memory.utilities;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Bundle;
/*  4:   */ import bridge.reps.entities.Entity;
/*  5:   */ import bridge.reps.entities.Function;
/*  6:   */ import bridge.reps.entities.Thread;
/*  7:   */ import java.io.PrintStream;
/*  8:   */ import junit.framework.TestCase;
/*  9:   */ 
/* 10:   */ public class SubSetUtilsTest
/* 11:   */   extends TestCase
/* 12:   */ {
/* 13: 9 */   Thread t2 = new Thread();
/* 14: 9 */   Thread t1 = new Thread();
/* 15:10 */   Bundle b2 = new Bundle();
/* 16:10 */   Bundle b1 = new Bundle();
/* 17:11 */   Entity thing1 = new Entity();
/* 18:12 */   Entity thing2 = new Entity();
/* 19:   */   
/* 20:   */   public void setUp()
/* 21:   */   {
/* 22:15 */     this.t1.add("thing");
/* 23:16 */     this.t1.add("element");
/* 24:17 */     this.t1.add("fire");
/* 25:18 */     this.t2.add("thing");
/* 26:19 */     this.t2.add("element");
/* 27:20 */     this.b1.add(this.t1);
/* 28:21 */     this.b1.add(this.t2);
/* 29:22 */     this.b2.add(this.t2);
/* 30:23 */     this.thing1.setBundle(this.b1);
/* 31:24 */     this.thing2.setBundle(this.b2);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void testIsSubSetThread()
/* 35:   */   {
/* 36:28 */     assertTrue(SubSetUtils.isSubSet(this.t2, this.t1));
/* 37:29 */     assertFalse(SubSetUtils.isSubSet(this.t1, this.t2));
/* 38:30 */     this.t2.add("water");
/* 39:31 */     assertFalse(SubSetUtils.isSubSet(this.t2, this.t1));
/* 40:32 */     this.t1.add("water");
/* 41:33 */     assertTrue(SubSetUtils.isSubSet(this.t2, this.t1));
/* 42:34 */     this.t2.add("fire");
/* 43:35 */     assertFalse(SubSetUtils.isSubSet(this.t2, this.t1));
/* 44:36 */     this.t1 = null;
/* 45:37 */     assertFalse(SubSetUtils.isSubSet(this.t2, this.t1));
/* 46:38 */     this.t2 = null;
/* 47:39 */     assertTrue(SubSetUtils.isSubSet(this.t2, this.t1));
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void testIsSubSetBundle()
/* 51:   */   {
/* 52:43 */     assertTrue(SubSetUtils.isSubSet(this.b2, this.b1));
/* 53:44 */     assertFalse(SubSetUtils.isSubSet(this.b1, this.b2));
/* 54:45 */     this.b2.add(this.t1);
/* 55:46 */     assertTrue(SubSetUtils.isSubSet(this.b1, this.b2));
/* 56:47 */     this.t2.add("water");
/* 57:48 */     this.b2.remove(this.t2);
/* 58:49 */     assertTrue(SubSetUtils.isSubSet(this.b2, this.b1));
/* 59:50 */     assertFalse(SubSetUtils.isSubSet(this.b1, this.b2));
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void testIsSubSetThing()
/* 63:   */   {
/* 64:54 */     assertTrue(SubSetUtils.isSubSet(this.thing2, this.thing1));
/* 65:55 */     assertFalse(SubSetUtils.isSubSet(this.thing1, this.thing2));
/* 66:   */     
/* 67:57 */     Entity thing3 = new Entity();
/* 68:58 */     Thread t3 = new Thread();
/* 69:59 */     t3.add("thing");
/* 70:60 */     t3.add("element");
/* 71:61 */     t3.add("water");
/* 72:62 */     Bundle b3 = new Bundle();
/* 73:63 */     b3.add(t3);
/* 74:64 */     thing3.setBundle(b3);
/* 75:65 */     Function d1 = new Function(thing3);
/* 76:66 */     d1.setBundle((Bundle)this.b1.clone());
/* 77:   */     
/* 78:68 */     assertTrue(SubSetUtils.isSubSet(this.thing2, d1));
/* 79:69 */     assertFalse(SubSetUtils.isSubSet(d1, this.thing2));
/* 80:   */     
/* 81:71 */     Function d2 = new Function(this.thing2);
/* 82:72 */     d2.setBundle((Bundle)this.b1.clone());
/* 83:73 */     System.out.println(d1);
/* 84:74 */     System.out.println(d2);
/* 85:   */     
/* 86:76 */     assertTrue(SubSetUtils.isSubSet(d2, d1));
/* 87:77 */     assertFalse(SubSetUtils.isSubSet(d1, d2));
/* 88:   */   }
/* 89:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.utilities.SubSetUtilsTest
 * JD-Core Version:    0.7.0.1
 */