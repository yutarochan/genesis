/*  1:   */ package gui;
/*  2:   */ 
/*  3:   */ public class StringUtilities
/*  4:   */ {
/*  5:   */   public static String prepareForDisplay(String s)
/*  6:   */   {
/*  7:12 */     StringBuffer buffer = new StringBuffer(s);
/*  8:   */     for (;;)
/*  9:   */     {
/* 10:15 */       int index = buffer.indexOf("\"");
/* 11:16 */       if (index >= 0)
/* 12:   */       {
/* 13:17 */         buffer.deleteCharAt(index);
/* 14:   */       }
/* 15:   */       else
/* 16:   */       {
/* 17:20 */         index = buffer.indexOf("_");
/* 18:21 */         if (index < 0) {
/* 19:   */           break;
/* 20:   */         }
/* 21:22 */         buffer.replace(index, index + 1, " ");
/* 22:   */       }
/* 23:   */     }
/* 24:27 */     return buffer.toString();
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.StringUtilities
 * JD-Core Version:    0.7.0.1
 */