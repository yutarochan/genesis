/*  1:   */ package utils;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ 
/*  5:   */ public class PairOfEntities
/*  6:   */ {
/*  7:   */   private Entity pattern;
/*  8:   */   private Entity datum;
/*  9:   */   
/* 10:   */   public PairOfEntities(Entity pattern, Entity datum)
/* 11:   */   {
/* 12:18 */     this.pattern = pattern;
/* 13:19 */     this.datum = datum;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public Entity getDatum()
/* 17:   */   {
/* 18:23 */     return this.datum;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Entity getPattern()
/* 22:   */   {
/* 23:27 */     return this.pattern;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean equals(Object o)
/* 27:   */   {
/* 28:31 */     if ((o instanceof PairOfEntities))
/* 29:   */     {
/* 30:32 */       PairOfEntities thatPair = (PairOfEntities)o;
/* 31:33 */       if ((getDatum() == thatPair.getDatum()) && (getPattern() == thatPair.getPattern())) {
/* 32:35 */         return true;
/* 33:   */       }
/* 34:   */     }
/* 35:39 */     return false;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String toString()
/* 39:   */   {
/* 40:45 */     return "<" + (this.pattern == null ? "null" : this.pattern.getName()) + ", " + (this.datum == null ? "null" : this.datum.getName()) + ">";
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     utils.PairOfEntities
 * JD-Core Version:    0.7.0.1
 */