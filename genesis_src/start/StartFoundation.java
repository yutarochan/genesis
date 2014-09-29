/*  1:   */ package start;
/*  2:   */ 
/*  3:   */ import translator.Translator;
/*  4:   */ import utils.Mark;
/*  5:   */ 
/*  6:   */ public class StartFoundation
/*  7:   */   extends StartSoapConnection
/*  8:   */ {
/*  9:13 */   protected boolean showWebInteraction = false;
/* 10:15 */   private static StartFoundation startFoundation = null;
/* 11:20 */   public static String urlString = "http://start.csail.mit.edu/api.php";
/* 12:   */   
/* 13:   */   public StartFoundation()
/* 14:   */   {
/* 15:23 */     setUrlString(urlString);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public static StartFoundation getStartFoundation()
/* 19:   */   {
/* 20:27 */     if (startFoundation == null) {
/* 21:28 */       startFoundation = new StartFoundation();
/* 22:   */     }
/* 23:30 */     return startFoundation;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String processParseRequest(String request)
/* 27:   */   {
/* 28:   */     try
/* 29:   */     {
/* 30:36 */       StringBuffer buffer = processProbe(request);
/* 31:38 */       if ((buffer == null) || (buffer.length() == 0)) {
/* 32:39 */         return null;
/* 33:   */       }
/* 34:41 */       int startIndex = buffer.indexOf("<PRE>");
/* 35:42 */       int endIndex = buffer.indexOf("</PRE>");
/* 36:43 */       String result = "";
/* 37:44 */       if ((startIndex >= 0) && (endIndex >= 0)) {
/* 38:49 */         result = buffer.substring(startIndex + 5, endIndex).trim();
/* 39:   */       }
/* 40:51 */       return result.toLowerCase();
/* 41:   */     }
/* 42:   */     catch (Exception e)
/* 43:   */     {
/* 44:54 */       Mark.say(new Object[] {"Error thrown inside StartFoundation.processParseRequest" });
/* 45:   */     }
/* 46:57 */     return "";
/* 47:   */   }
/* 48:   */   
/* 49:   */   public String processGeneratorRequest(String request)
/* 50:   */   {
/* 51:63 */     String result = processProbe(request).toString();
/* 52:64 */     if ((result == null) || (result.length() == 0)) {
/* 53:65 */       return null;
/* 54:   */     }
/* 55:68 */     return result;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public static void main(String[] ignore)
/* 59:   */     throws Exception
/* 60:   */   {
/* 61:72 */     Mark.say(
/* 62:   */     
/* 63:   */ 
/* 64:75 */       new Object[] { "Starting" });Mark.say(new Object[] { "The START parse:", Start.getStart().parse("A bird flew flew.") });Mark.say(new Object[] { "The Genesis Innerese:", Translator.getTranslator().translate("A bird flew flew") });
/* 65:   */   }
/* 66:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     start.StartFoundation
 * JD-Core Version:    0.7.0.1
 */