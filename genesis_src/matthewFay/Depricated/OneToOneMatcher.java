/*  1:   */ package matthewFay.Depricated;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Function;
/*  5:   */ import java.io.PrintStream;
/*  6:   */ import matchers.original.BasicMatcherOriginal;
/*  7:   */ import minilisp.LList;
/*  8:   */ import utils.PairOfEntities;
/*  9:   */ 
/* 10:   */ @Deprecated
/* 11:   */ public class OneToOneMatcher
/* 12:   */   extends BasicMatcherOriginal
/* 13:   */ {
/* 14:   */   private static OneToOneMatcher matcher;
/* 15:   */   
/* 16:   */   public static OneToOneMatcher getOneToOneMatcher()
/* 17:   */   {
/* 18:16 */     if (matcher == null) {
/* 19:17 */       matcher = new OneToOneMatcher();
/* 20:   */     }
/* 21:19 */     return matcher;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public boolean allowMatch(LList<PairOfEntities> matches, Entity datum, Entity pattern)
/* 25:   */   {
/* 26:26 */     for (Object o : matches)
/* 27:   */     {
/* 28:27 */       PairOfEntities pairOfThings = (PairOfEntities)o;
/* 29:29 */       if ((pairOfThings.getPattern().isEqual(pattern)) || (pairOfThings.getDatum().isEqual(datum))) {
/* 30:31 */         return false;
/* 31:   */       }
/* 32:   */     }
/* 33:34 */     return true;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public static void main(String[] ignore)
/* 37:   */   {
/* 38:38 */     Entity t1 = new Entity("foo");
/* 39:39 */     t1.addType("name");
/* 40:40 */     t1.addType("john");
/* 41:41 */     Entity t2 = new Entity("foo");
/* 42:42 */     t2.addType("name");
/* 43:43 */     t2.addType("john");
/* 44:44 */     Function d1 = new Function("d", t1);
/* 45:45 */     Function d2 = new Function("d", t2);
/* 46:46 */     OneToOneMatcher matcher = getOneToOneMatcher();
/* 47:47 */     System.out.println("Match: " + matcher.match(d1, d2));
/* 48:48 */     System.out.println("Match: " + matcher.match(t1, t2));
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.Depricated.OneToOneMatcher
 * JD-Core Version:    0.7.0.1
 */