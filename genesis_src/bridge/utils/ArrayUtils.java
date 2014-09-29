/*  1:   */ package bridge.utils;
/*  2:   */ 
/*  3:   */ import java.util.Arrays;
/*  4:   */ 
/*  5:   */ public class ArrayUtils
/*  6:   */ {
/*  7:   */   public static String toString(Object[] array)
/*  8:   */   {
/*  9:17 */     return "[" + StringUtils.join(Arrays.asList(array), ", ") + "]";
/* 10:   */   }
/* 11:   */   
/* 12:   */   public static boolean contains(int[] array, int element)
/* 13:   */   {
/* 14:22 */     for (int i = 0; i < array.length; i++)
/* 15:   */     {
/* 16:23 */       int test = array[i];
/* 17:24 */       if (test == element) {
/* 18:24 */         return true;
/* 19:   */       }
/* 20:   */     }
/* 21:26 */     return false;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public static boolean contains(double[] array, double element)
/* 25:   */   {
/* 26:31 */     for (int i = 0; i < array.length; i++)
/* 27:   */     {
/* 28:32 */       double test = array[i];
/* 29:33 */       if (test == element) {
/* 30:33 */         return true;
/* 31:   */       }
/* 32:   */     }
/* 33:35 */     return false;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public static boolean contains(Object[] array, Object element)
/* 37:   */   {
/* 38:49 */     for (int i = 0; i < array.length; i++)
/* 39:   */     {
/* 40:50 */       Object test = array[i];
/* 41:51 */       if (test == null)
/* 42:   */       {
/* 43:52 */         if (element == null) {
/* 44:54 */           return true;
/* 45:   */         }
/* 46:   */       }
/* 47:56 */       else if (test.equals(element)) {
/* 48:58 */         return true;
/* 49:   */       }
/* 50:   */     }
/* 51:63 */     return false;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public static void copyInto(Object[] source, Object[] target)
/* 55:   */   {
/* 56:72 */     if (source == null) {
/* 57:72 */       return;
/* 58:   */     }
/* 59:73 */     for (int i = 0; i < source.length; i++) {
/* 60:74 */       target[i] = source[i];
/* 61:   */     }
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.utils.ArrayUtils
 * JD-Core Version:    0.7.0.1
 */