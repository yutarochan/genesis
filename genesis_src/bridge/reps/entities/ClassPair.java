/*  1:   */ package bridge.reps.entities;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ 
/*  5:   */ public class ClassPair
/*  6:   */ {
/*  7:   */   public static String makeClassPair(String upper, String lower)
/*  8:   */   {
/*  9:19 */     return "{" + upper + ", " + lower + "}";
/* 10:   */   }
/* 11:   */   
/* 12:   */   public static String getUpper(String cp)
/* 13:   */   {
/* 14:23 */     return cp.substring(1, cp.indexOf(','));
/* 15:   */   }
/* 16:   */   
/* 17:   */   public static String getLower(String cp)
/* 18:   */   {
/* 19:27 */     return cp.substring(cp.indexOf(','), cp.length());
/* 20:   */   }
/* 21:   */   
/* 22:   */   public static Vector<String> getClassesFromPair(String cp)
/* 23:   */   {
/* 24:31 */     Vector<String> result = new Vector();
/* 25:32 */     result.add(getUpper(cp));
/* 26:33 */     result.add(getLower(cp));
/* 27:34 */     return result;
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.ClassPair
 * JD-Core Version:    0.7.0.1
 */