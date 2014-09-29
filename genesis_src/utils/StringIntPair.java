/*  1:   */ package utils;
/*  2:   */ 
/*  3:   */ public class StringIntPair
/*  4:   */ {
/*  5:   */   String label;
/*  6:   */   int value;
/*  7:   */   
/*  8:   */   public String getLabel()
/*  9:   */   {
/* 10:15 */     return this.label;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int getValue()
/* 14:   */   {
/* 15:19 */     return this.value;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String toString()
/* 19:   */   {
/* 20:23 */     return "<" + this.label + ", " + this.value + ">";
/* 21:   */   }
/* 22:   */   
/* 23:   */   public StringIntPair(String label, int value)
/* 24:   */   {
/* 25:28 */     this.label = label;
/* 26:29 */     this.value = value;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void setValue(int i)
/* 30:   */   {
/* 31:33 */     this.value = i;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void incrementValue()
/* 35:   */   {
/* 36:37 */     this.value += 1;
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     utils.StringIntPair
 * JD-Core Version:    0.7.0.1
 */