/*  1:   */ package memory.patterns;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ 
/*  5:   */ public final class Pattern
/*  6:   */ {
/*  7:   */   private Entity t1;
/*  8:   */   private Entity t2;
/*  9:   */   private Relation r;
/* 10:   */   
/* 11:   */   public static enum Relation
/* 12:   */   {
/* 13:16 */     causes,  proceeds;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public Pattern(Entity t1, Entity t2, Relation r)
/* 17:   */   {
/* 18:24 */     this.t1 = t1;
/* 19:25 */     this.t2 = t2;
/* 20:26 */     this.r = r;
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.patterns.Pattern
 * JD-Core Version:    0.7.0.1
 */