/*  1:   */ package matthewFay.Utilities;
/*  2:   */ 
/*  3:   */ public class Pair<A, B>
/*  4:   */ {
/*  5:   */   public A a;
/*  6:   */   public B b;
/*  7:   */   
/*  8:   */   public Pair(A a, B b)
/*  9:   */   {
/* 10: 9 */     this.a = a;
/* 11:10 */     this.b = b;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String toString()
/* 15:   */   {
/* 16:14 */     StringBuilder sb = new StringBuilder();
/* 17:15 */     sb.append("(");
/* 18:16 */     sb.append(this.a);
/* 19:17 */     sb.append(",");
/* 20:18 */     sb.append(this.b);
/* 21:19 */     sb.append(")");
/* 22:20 */     return sb.toString();
/* 23:   */   }
/* 24:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.Utilities.Pair
 * JD-Core Version:    0.7.0.1
 */