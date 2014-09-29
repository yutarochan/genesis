/*  1:   */ package bridge.utils;
/*  2:   */ 
/*  3:   */ public class HashCode
/*  4:   */ {
/*  5:16 */   public static final int trueHash = Boolean.TRUE.hashCode();
/*  6:   */   
/*  7:   */   public static final int hash(Object o)
/*  8:   */   {
/*  9:20 */     if (o == null) {
/* 10:20 */       return 0;
/* 11:   */     }
/* 12:20 */     return o.hashCode();
/* 13:   */   }
/* 14:   */   
/* 15:   */   public static final int hash(int i)
/* 16:   */   {
/* 17:22 */     return i;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public static final int hash(byte b)
/* 21:   */   {
/* 22:23 */     return b;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static final int hash(short s)
/* 26:   */   {
/* 27:24 */     return s;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public static final int hash(char c)
/* 31:   */   {
/* 32:25 */     return c;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public static final int hash(long l)
/* 36:   */   {
/* 37:26 */     return new Long(l).hashCode();
/* 38:   */   }
/* 39:   */   
/* 40:   */   public static final int hash(double d)
/* 41:   */   {
/* 42:27 */     return new Double(d).hashCode();
/* 43:   */   }
/* 44:   */   
/* 45:   */   public static final int hash(float f)
/* 46:   */   {
/* 47:28 */     return new Float(f).hashCode();
/* 48:   */   }
/* 49:   */   
/* 50:   */   public static final int hash(boolean b)
/* 51:   */   {
/* 52:30 */     if (b) {
/* 53:31 */       return Boolean.TRUE.hashCode();
/* 54:   */     }
/* 55:33 */     return Boolean.FALSE.hashCode();
/* 56:   */   }
/* 57:   */   
/* 58:   */   public static boolean areEqual(Object a, Object b)
/* 59:   */   {
/* 60:37 */     if (a == b) {
/* 61:38 */       return true;
/* 62:   */     }
/* 63:39 */     if ((a == null) || (b == null)) {
/* 64:40 */       return false;
/* 65:   */     }
/* 66:42 */     return a.equals(b);
/* 67:   */   }
/* 68:   */   
/* 69:   */   public static final int identityHash(Object o)
/* 70:   */   {
/* 71:47 */     if (o == null) {
/* 72:47 */       return 0;
/* 73:   */     }
/* 74:47 */     return System.identityHashCode(o);
/* 75:   */   }
/* 76:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.utils.HashCode
 * JD-Core Version:    0.7.0.1
 */