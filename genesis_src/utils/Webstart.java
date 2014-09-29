/*  1:   */ package utils;
/*  2:   */ 
/*  3:   */ public class Webstart
/*  4:   */ {
/*  5:10 */   private static boolean webstart = false;
/*  6:   */   
/*  7:   */   public static boolean isWebStart()
/*  8:   */   {
/*  9:13 */     return webstart;
/* 10:   */   }
/* 11:   */   
/* 12:   */   public static void setWebStart(boolean b)
/* 13:   */   {
/* 14:17 */     if (b) {
/* 15:18 */       Mark.say(new Object[] {"Appear to be running in WebStart mode" });
/* 16:   */     } else {
/* 17:21 */       Mark.say(new Object[] {"Appear to be running in development mode" });
/* 18:   */     }
/* 19:23 */     webstart = b;
/* 20:   */   }
/* 21:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     utils.Webstart
 * JD-Core Version:    0.7.0.1
 */