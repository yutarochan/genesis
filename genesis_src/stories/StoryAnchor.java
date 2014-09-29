/*  1:   */ package stories;
/*  2:   */ 
/*  3:   */ import java.net.URL;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import utils.Anchor;
/*  6:   */ import utils.Mark;
/*  7:   */ import utils.TextIO;
/*  8:   */ 
/*  9:   */ public class StoryAnchor
/* 10:   */   extends Anchor
/* 11:   */ {
/* 12:   */   private static StoryAnchor storyAnchor;
/* 13:   */   ArrayList<String> subdirectories;
/* 14:   */   
/* 15:   */   public static StoryAnchor getStoryAnchor()
/* 16:   */   {
/* 17:20 */     if (storyAnchor == null) {
/* 18:21 */       storyAnchor = new StoryAnchor();
/* 19:   */     }
/* 20:23 */     return storyAnchor;
/* 21:   */   }
/* 22:   */   
/* 23:   */   private ArrayList<String> getSubdirectories()
/* 24:   */   {
/* 25:31 */     if (this.subdirectories == null) {
/* 26:32 */       this.subdirectories = new ArrayList();
/* 27:   */     }
/* 28:60 */     return this.subdirectories;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String getContent(String resource)
/* 32:   */   {
/* 33:64 */     URL url = getClass().getResource(resource);
/* 34:65 */     String divider = System.getProperty("file.separator");
/* 35:66 */     if (url == null) {
/* 36:67 */       for (String s : getSubdirectories())
/* 37:   */       {
/* 38:68 */         url = getClass().getResource(divider + s + divider + resource);
/* 39:69 */         if (url != null) {
/* 40:   */           break;
/* 41:   */         }
/* 42:   */       }
/* 43:   */     }
/* 44:74 */     if (url == null) {
/* 45:75 */       Mark.err(new Object[] {"Unable to find", resource });
/* 46:   */     }
/* 47:   */     try
/* 48:   */     {
/* 49:78 */       return TextIO.readStringFromURL(url);
/* 50:   */     }
/* 51:   */     catch (Exception localException) {}
/* 52:82 */     return null;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public static void main(String[] main)
/* 56:   */     throws Exception
/* 57:   */   {
/* 58:86 */     Mark.say(
/* 59:87 */       new Object[] { getStoryAnchor().getContent("macbeh1.txt") });
/* 60:   */   }
/* 61:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     stories.StoryAnchor
 * JD-Core Version:    0.7.0.1
 */