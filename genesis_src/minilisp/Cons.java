/*  1:   */ package minilisp;
/*  2:   */ 
/*  3:   */ public class Cons<A, D>
/*  4:   */ {
/*  5:   */   private A car;
/*  6:   */   private D cdr;
/*  7:   */   public boolean eol;
/*  8:   */   
/*  9:   */   public Cons()
/* 10:   */   {
/* 11:19 */     this.eol = true;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public Cons(A first, D second)
/* 15:   */   {
/* 16:24 */     this.car = first;
/* 17:25 */     this.cdr = second;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public A car()
/* 21:   */   {
/* 22:29 */     return this.car;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public D cdr()
/* 26:   */   {
/* 27:33 */     return this.cdr;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public A first()
/* 31:   */   {
/* 32:37 */     return car();
/* 33:   */   }
/* 34:   */   
/* 35:   */   public A second()
/* 36:   */   {
/* 37:41 */     D cdr = cdr();
/* 38:42 */     if ((cdr instanceof Cons)) {
/* 39:43 */       return ((Cons)cdr).first();
/* 40:   */     }
/* 41:45 */     return null;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public boolean listP()
/* 45:   */   {
/* 46:49 */     if ((this instanceof LList)) {
/* 47:50 */       return true;
/* 48:   */     }
/* 49:52 */     return false;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public String toString()
/* 53:   */   {
/* 54:56 */     if (listP()) {
/* 55:57 */       return printList((LList)this);
/* 56:   */     }
/* 57:59 */     return "(" + this.car + " . " + this.cdr + ")";
/* 58:   */   }
/* 59:   */   
/* 60:   */   private String printList(LList x)
/* 61:   */   {
/* 62:64 */     return "(" + printListAux(x) + ")";
/* 63:   */   }
/* 64:   */   
/* 65:   */   private String printListAux(Object o)
/* 66:   */   {
/* 67:68 */     if (o == null) {
/* 68:69 */       return "";
/* 69:   */     }
/* 70:71 */     if ((o instanceof LList))
/* 71:   */     {
/* 72:72 */       LList x = (LList)o;
/* 73:73 */       if (x.endP()) {
/* 74:74 */         return "";
/* 75:   */       }
/* 76:76 */       if (((x.cdr() instanceof LList)) && (((LList)x.cdr()).endP())) {
/* 77:77 */         return x.car().toString();
/* 78:   */       }
/* 79:79 */       if ((x instanceof LList)) {
/* 80:80 */         return x.car() + " " + printListAux(x.cdr());
/* 81:   */       }
/* 82:   */     }
/* 83:83 */     return o.toString();
/* 84:   */   }
/* 85:   */   
/* 86:   */   public static void main(String[] ignore)
/* 87:   */   {
/* 88:87 */     LList.main(ignore);
/* 89:   */   }
/* 90:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     minilisp.Cons
 * JD-Core Version:    0.7.0.1
 */