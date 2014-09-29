/*  1:   */ package tomLarson;
/*  2:   */ 
/*  3:   */ public class Type
/*  4:   */ {
/*  5:   */   private String name;
/*  6:   */   private double weight;
/*  7:   */   
/*  8:   */   public Type(String name, double weight)
/*  9:   */   {
/* 10:14 */     this.name = name.toLowerCase();
/* 11:15 */     this.weight = weight;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public double getWeight()
/* 15:   */   {
/* 16:19 */     return this.weight;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getName()
/* 20:   */   {
/* 21:23 */     return this.name;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void setWeight(double newWeight)
/* 25:   */   {
/* 26:27 */     this.weight = newWeight;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String toString()
/* 30:   */   {
/* 31:31 */     return this.name + ":" + this.weight;
/* 32:   */   }
/* 33:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     tomLarson.Type
 * JD-Core Version:    0.7.0.1
 */