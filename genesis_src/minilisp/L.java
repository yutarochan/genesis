/*  1:   */ package minilisp;
/*  2:   */ 
/*  3:   */ import utils.Mark;
/*  4:   */ 
/*  5:   */ public class L
/*  6:   */ {
/*  7:   */   public static Cons list(Object a)
/*  8:   */   {
/*  9:13 */     return cons(a, null);
/* 10:   */   }
/* 11:   */   
/* 12:   */   public static Cons list(Object a, Object b)
/* 13:   */   {
/* 14:17 */     return cons(a, list(b));
/* 15:   */   }
/* 16:   */   
/* 17:   */   public static Object first(Cons a)
/* 18:   */   {
/* 19:21 */     return a.first();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public static Object second(Cons a)
/* 23:   */   {
/* 24:25 */     return a.second();
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static Object car(Cons a)
/* 28:   */   {
/* 29:29 */     return a.car();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public static Object cdr(Cons a)
/* 33:   */   {
/* 34:33 */     return a.cdr();
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static Cons cons(Object a, Cons b)
/* 38:   */   {
/* 39:37 */     return new Cons(a, b);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public static Object rest(Cons cell)
/* 43:   */   {
/* 44:41 */     if (cell == null) {
/* 45:42 */       return null;
/* 46:   */     }
/* 47:44 */     Object cdr = cdr(cell);
/* 48:45 */     if (cdr == null) {
/* 49:46 */       return null;
/* 50:   */     }
/* 51:49 */     return cdr;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public static Cons cons(Object a, Object b)
/* 55:   */   {
/* 56:54 */     return new Cons(a, b);
/* 57:   */   }
/* 58:   */   
/* 59:   */   public static Cons append(Cons a, Cons b)
/* 60:   */   {
/* 61:59 */     if (a == null) {
/* 62:60 */       return b;
/* 63:   */     }
/* 64:62 */     if (cdr(a) == null) {
/* 65:63 */       return cons(first(a), b);
/* 66:   */     }
/* 67:65 */     if ((cdr(a) instanceof Cons)) {
/* 68:66 */       return cons(first(a), append((Cons)cdr(a), b));
/* 69:   */     }
/* 70:68 */     Mark.say(new Object[] {"Error in appending two lists", a, b });
/* 71:69 */     return null;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public static void main(String[] ignore)
/* 75:   */   {
/* 76:73 */     Cons c = list("patrick", "winston");
/* 77:74 */     Cons d = list("karen", "prendergast");
/* 78:75 */     Mark.say(new Object[] {Boolean.valueOf(true), c });
/* 79:76 */     Mark.say(new Object[] {Boolean.valueOf(true), d });
/* 80:77 */     Mark.say(new Object[] {Boolean.valueOf(true), append(c, d) });
/* 81:78 */     Mark.say(new Object[] {Boolean.valueOf(true), rest(append(c, d)) });
/* 82:79 */     Mark.say(new Object[] {Boolean.valueOf(true), second(append(c, d)) });
/* 83:80 */     Mark.say(new Object[] {Boolean.valueOf(true), cons("karen", "prendergast") });
/* 84:81 */     Mark.say(new Object[] {Boolean.valueOf(true), append(c, cons("karen", list("prendergast"))) });
/* 85:82 */     Cons x = append(c, d);
/* 86:83 */     Cons y = cons("Hello", rest(x));
/* 87:84 */     Cons z = cons("World", rest(x));
/* 88:85 */     Mark.say(new Object[] {Boolean.valueOf(true), x });
/* 89:86 */     Mark.say(new Object[] {Boolean.valueOf(true), y });
/* 90:87 */     Mark.say(new Object[] {Boolean.valueOf(true), z });
/* 91:   */   }
/* 92:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     minilisp.L
 * JD-Core Version:    0.7.0.1
 */