/*  1:   */ package transitionSpace;
/*  2:   */ 
/*  3:   */ class RowLabel
/*  4:   */ {
/*  5:   */   String object;
/*  6:   */   String label;
/*  7:   */   String key;
/*  8:   */   
/*  9:   */   public RowLabel(String object, String label)
/* 10:   */   {
/* 11:18 */     this.object = object;
/* 12:19 */     this.label = label;
/* 13:20 */     this.key = (object + " " + label);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String toString()
/* 17:   */   {
/* 18:24 */     return "<" + this.object + " / " + this.label + ">";
/* 19:   */   }
/* 20:   */   
/* 21:   */   public int hashCode()
/* 22:   */   {
/* 23:29 */     return this.key.hashCode();
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     transitionSpace.RowLabel
 * JD-Core Version:    0.7.0.1
 */