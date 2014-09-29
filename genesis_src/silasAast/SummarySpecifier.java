/*  1:   */ package silasAast;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Sequence;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import java.util.Iterator;
/*  7:   */ import java.util.Vector;
/*  8:   */ import matchers.StandardMatcher;
/*  9:   */ 
/* 10:   */ public class SummarySpecifier
/* 11:   */   extends AbstractWiredBox
/* 12:   */ {
/* 13:   */   public static String NARRATOR_VIEW_IN;
/* 14:   */   public static String INSTANTIATED_REFLECTIONS;
/* 15:17 */   private static Sequence desiredReaction = new Sequence();
/* 16:   */   
/* 17:   */   public static Sequence setGoal(Sequence narratorView, Sequence reflections)
/* 18:   */   {
/* 19:24 */     StandardMatcher matcher = StandardMatcher.getBasicMatcher();
/* 20:   */     Iterator localIterator2;
/* 21:26 */     for (Iterator localIterator1 = narratorView.getElements().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 22:   */     {
/* 23:26 */       Entity storyElement = (Entity)localIterator1.next();
/* 24:27 */       localIterator2 = reflections.getElements().iterator(); continue;Entity reflection = (Entity)localIterator2.next();
/* 25:28 */       if (matcher.match(storyElement, reflection) != null) {
/* 26:29 */         desiredReaction.addElement(storyElement);
/* 27:   */       }
/* 28:   */     }
/* 29:34 */     return desiredReaction;
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     silasAast.SummarySpecifier
 * JD-Core Version:    0.7.0.1
 */