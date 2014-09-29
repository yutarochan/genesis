/*  1:   */ package magicLess.utils;
/*  2:   */ 
/*  3:   */ import java.net.MalformedURLException;
/*  4:   */ import java.net.URL;
/*  5:   */ 
/*  6:   */ public abstract class AbstractAnchor
/*  7:   */ {
/*  8:   */   public static URL binToSource(URL pathURL)
/*  9:   */   {
/* 10:16 */     String path = binToSource(pathURL.toString());
/* 11:   */     try
/* 12:   */     {
/* 13:18 */       return new URL(path);
/* 14:   */     }
/* 15:   */     catch (MalformedURLException e)
/* 16:   */     {
/* 17:20 */       throw new RuntimeException(e);
/* 18:   */     }
/* 19:   */   }
/* 20:   */   
/* 21:   */   public static String binToSource(String path)
/* 22:   */   {
/* 23:26 */     if (path.contains("/bin/")) {
/* 24:27 */       path = path.replaceFirst("/bin/", "/source/");
/* 25:28 */     } else if (path.contains("\\bin\\")) {
/* 26:29 */       path = path.replaceFirst("\\\\bin\\\\", "\\source\\");
/* 27:   */     }
/* 28:31 */     return path;
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     magicLess.utils.AbstractAnchor
 * JD-Core Version:    0.7.0.1
 */