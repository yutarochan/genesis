/*  1:   */ package standards;
/*  2:   */ 
/*  3:   */ import java.net.URI;
/*  4:   */ import java.net.URISyntaxException;
/*  5:   */ import java.net.URL;
/*  6:   */ import utils.Anchor;
/*  7:   */ import utils.Mark;
/*  8:   */ import utils.WindowsConnection;
/*  9:   */ 
/* 10:   */ public class StandardsAnchor
/* 11:   */   extends Anchor
/* 12:   */ {
/* 13:   */   private static StandardsAnchor standardsAnchor;
/* 14:   */   
/* 15:   */   public static StandardsAnchor getStoryAnchor()
/* 16:   */   {
/* 17:21 */     if (standardsAnchor == null) {
/* 18:22 */       standardsAnchor = new StandardsAnchor();
/* 19:   */     }
/* 20:24 */     return standardsAnchor;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getFile(String resource)
/* 24:   */   {
/* 25:   */     try
/* 26:   */     {
/* 27:29 */       return getContent(resource).toURI().getPath();
/* 28:   */     }
/* 29:   */     catch (URISyntaxException e)
/* 30:   */     {
/* 31:33 */       e.printStackTrace();
/* 32:   */     }
/* 33:34 */     return null;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public URL getContent(String resource)
/* 37:   */   {
/* 38:39 */     URL url = getClass().getResource(resource);
/* 39:40 */     return url;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public static void main(String[] main)
/* 43:   */     throws Exception
/* 44:   */   {
/* 45:44 */     String command = getStoryAnchor().getFile("Shakespeare Macbeth.pdf");
/* 46:45 */     Mark.say(new Object[] {"Command:", command });
/* 47:   */     
/* 48:47 */     command = "\"" + command.substring(1) + "\"";
/* 49:48 */     WindowsConnection.run(command);
/* 50:   */   }
/* 51:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     standards.StandardsAnchor
 * JD-Core Version:    0.7.0.1
 */