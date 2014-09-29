/*  1:   */ package bridge.utils;
/*  2:   */ 
/*  3:   */ public class Pair<A, B>
/*  4:   */ {
/*  5:   */   A cardata;
/*  6:   */   B cdrdata;
/*  7:   */   
/*  8:   */   public Pair(A car, B cdr)
/*  9:   */   {
/* 10: 8 */     this.cardata = car;
/* 11: 9 */     this.cdrdata = cdr;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public A car()
/* 15:   */   {
/* 16:13 */     return this.cardata;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public B cdr()
/* 20:   */   {
/* 21:17 */     return this.cdrdata;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String toString()
/* 25:   */   {
/* 26:   */     String carstr;
/* 27:   */     String carstr;
/* 28:22 */     if (this.cardata != null) {
/* 29:22 */       carstr = this.cardata.toString();
/* 30:   */     } else {
/* 31:22 */       carstr = "#f";
/* 32:   */     }
/* 33:   */     String cdrstr;
/* 34:   */     String cdrstr;
/* 35:23 */     if (this.cdrdata != null) {
/* 36:23 */       cdrstr = this.cdrdata.toString();
/* 37:   */     } else {
/* 38:23 */       cdrstr = "#f";
/* 39:   */     }
/* 40:24 */     return "(" + carstr + " . " + cdrstr + ")";
/* 41:   */   }
/* 42:   */   
/* 43:   */   public boolean equals(Object p)
/* 44:   */   {
/* 45:28 */     return ((p instanceof Pair)) && (this.cardata.equals(((Pair)p).cardata)) && 
/* 46:29 */       (this.cdrdata.equals(((Pair)p).cdrdata));
/* 47:   */   }
/* 48:   */   
/* 49:   */   public int hashCode()
/* 50:   */   {
/* 51:33 */     return this.cardata.hashCode() + 2 * this.cdrdata.hashCode();
/* 52:   */   }
/* 53:   */   
/* 54:   */   public Object clone()
/* 55:   */   {
/* 56:37 */     return new Pair(this.cardata, this.cdrdata);
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.utils.Pair
 * JD-Core Version:    0.7.0.1
 */