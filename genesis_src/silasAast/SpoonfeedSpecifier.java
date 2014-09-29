/*  1:   */ package silasAast;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Sequence;
/*  4:   */ import connections.AbstractWiredBox;
/*  5:   */ 
/*  6:   */ public class SpoonfeedSpecifier
/*  7:   */   extends AbstractWiredBox
/*  8:   */ {
/*  9:10 */   private static Sequence desiredReaction = new Sequence();
/* 10:   */   
/* 11:   */   public static Sequence setGoal(Sequence narratorView)
/* 12:   */   {
/* 13:17 */     if (((narratorView instanceof Sequence)) && (narratorView != null)) {
/* 14:18 */       desiredReaction = narratorView;
/* 15:   */     }
/* 16:20 */     return desiredReaction;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     silasAast.SpoonfeedSpecifier
 * JD-Core Version:    0.7.0.1
 */