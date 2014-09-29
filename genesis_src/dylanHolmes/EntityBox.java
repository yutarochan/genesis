/*  1:   */ package dylanHolmes;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import connections.WiredBox;
/*  5:   */ 
/*  6:   */ public class EntityBox
/*  7:   */   implements WiredBox
/*  8:   */ {
/*  9:   */   private String name;
/* 10:   */   Entity entity;
/* 11:24 */   private boolean mode_explained = false;
/* 12:25 */   private boolean mode_predicted = false;
/* 13:26 */   private boolean mode_negated = false;
/* 14:27 */   private boolean mode_assumed = false;
/* 15:   */   
/* 16:   */   public String getName()
/* 17:   */   {
/* 18:31 */     return this.name;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Entity getEntity()
/* 22:   */   {
/* 23:32 */     return this.entity;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public EntityBox(Entity e)
/* 27:   */   {
/* 28:35 */     this.entity = e;
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     dylanHolmes.EntityBox
 * JD-Core Version:    0.7.0.1
 */