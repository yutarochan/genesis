/*  1:   */ package frames.utilities;
/*  2:   */ 
/*  3:   */ public class Utils
/*  4:   */ {
/*  5:   */   public static String[] arraycat(String[] a, String[] b)
/*  6:   */   {
/*  7:12 */     String[] result = new String[a.length + b.length];
/*  8:13 */     System.arraycopy(a, 0, result, 0, a.length);
/*  9:14 */     System.arraycopy(b, 0, result, a.length, b.length);
/* 10:15 */     return result;
/* 11:   */   }
/* 12:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.utilities.Utils
 * JD-Core Version:    0.7.0.1
 */