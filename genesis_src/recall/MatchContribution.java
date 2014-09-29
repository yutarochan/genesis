/*  1:   */ package recall;
/*  2:   */ 
/*  3:   */ public class MatchContribution
/*  4:   */   implements Comparable
/*  5:   */ {
/*  6:   */   String dimension;
/*  7:   */   double value;
/*  8:   */   
/*  9:   */   public MatchContribution(String dimension, double value)
/* 10:   */   {
/* 11:16 */     this.dimension = dimension;
/* 12:17 */     this.value = value;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public String getDimension()
/* 16:   */   {
/* 17:21 */     return this.dimension;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public double getValue()
/* 21:   */   {
/* 22:25 */     return this.value;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String toString()
/* 26:   */   {
/* 27:29 */     return "(" + this.dimension + ", " + this.value + ")";
/* 28:   */   }
/* 29:   */   
/* 30:   */   public int compareTo(Object o)
/* 31:   */   {
/* 32:34 */     return this.dimension.compareTo(((MatchContribution)o).dimension);
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     recall.MatchContribution
 * JD-Core Version:    0.7.0.1
 */