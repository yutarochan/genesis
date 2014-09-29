/*  1:   */ package utils;
/*  2:   */ 
/*  3:   */ import java.io.UnsupportedEncodingException;
/*  4:   */ import java.net.URL;
/*  5:   */ import java.net.URLDecoder;
/*  6:   */ 
/*  7:   */ public class Anchor
/*  8:   */ {
/*  9:   */   public String source()
/* 10:   */   {
/* 11:19 */     return source("");
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String source(String resource)
/* 15:   */   {
/* 16:23 */     return get(resource).replace("binary", "source");
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String get()
/* 20:   */   {
/* 21:27 */     return get("");
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String get(String resource)
/* 25:   */   {
/* 26:30 */     URL url = getClass().getResource(resource);
/* 27:   */     try
/* 28:   */     {
/* 29:32 */       if (url == null) {
/* 30:33 */         return "";
/* 31:   */       }
/* 32:35 */       return URLDecoder.decode(url.getPath(), "UTF-8");
/* 33:   */     }
/* 34:   */     catch (UnsupportedEncodingException e)
/* 35:   */     {
/* 36:38 */       e.printStackTrace();
/* 37:   */     }
/* 38:39 */     return "";
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     utils.Anchor
 * JD-Core Version:    0.7.0.1
 */