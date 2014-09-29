/*  1:   */ package utils;
/*  2:   */ 
/*  3:   */ public class Pair<F, S>
/*  4:   */ {
/*  5:   */   public F first;
/*  6:   */   public S second;
/*  7:   */   
/*  8:   */   public Pair(F first)
/*  9:   */   {
/* 10:19 */     this(first, null);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public Pair(F first, S second)
/* 14:   */   {
/* 15:25 */     this.first = first;
/* 16:26 */     this.second = second;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public F getFirst()
/* 20:   */   {
/* 21:30 */     return this.first;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public S getSecond()
/* 25:   */   {
/* 26:34 */     return this.second;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public boolean equals(Object o)
/* 30:   */   {
/* 31:38 */     if ((o instanceof PairOfEntities))
/* 32:   */     {
/* 33:39 */       PairOfEntities thatPair = (PairOfEntities)o;
/* 34:40 */       if ((getFirst() == thatPair.getDatum()) && (getSecond() == thatPair.getPattern())) {
/* 35:41 */         return true;
/* 36:   */       }
/* 37:   */     }
/* 38:44 */     return false;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public String toString()
/* 42:   */   {
/* 43:48 */     return "<" + this.first.toString() + ", " + this.second.toString() + ">";
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     utils.Pair
 * JD-Core Version:    0.7.0.1
 */