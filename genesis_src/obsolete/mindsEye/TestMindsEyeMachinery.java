/*  1:   */ package obsolete.mindsEye;
/*  2:   */ 
/*  3:   */ import Co57.BerylVerbTranslator;
/*  4:   */ import Co57.services.Co57Passthrough;
/*  5:   */ import adamKraft.WiredBoxB;
/*  6:   */ import java.util.Date;
/*  7:   */ import java.util.List;
/*  8:   */ import utils.Mark;
/*  9:   */ 
/* 10:   */ public class TestMindsEyeMachinery
/* 11:   */ {
/* 12:   */   public static void main(String[] args)
/* 13:   */     throws Throwable
/* 14:   */   {
/* 15:29 */     Mark.say(
/* 16:   */     
/* 17:   */ 
/* 18:   */ 
/* 19:   */ 
/* 20:   */ 
/* 21:   */ 
/* 22:   */ 
/* 23:   */ 
/* 24:   */ 
/* 25:   */ 
/* 26:   */ 
/* 27:   */ 
/* 28:   */ 
/* 29:   */ 
/* 30:   */ 
/* 31:   */ 
/* 32:   */ 
/* 33:   */ 
/* 34:   */ 
/* 35:   */ 
/* 36:   */ 
/* 37:   */ 
/* 38:   */ 
/* 39:53 */       new Object[] { "Computing" });WiredBoxB c = new WiredBoxB();List<String> titles = c.getMovieTitles();Mark.say(new Object[] { "Title count is", Integer.valueOf(titles.size()) });int n = 1;BerylVerbTranslator wireClient = new BerylVerbTranslator("BerylVerbTranslator", Co57Passthrough.ZMQ_SERVER_WIRED_BOX_SERVICE);
/* 40:35 */     if (titles.size() > n)
/* 41:   */     {
/* 42:36 */       String title = "capture-26.mp4";
/* 43:37 */       for (int i = 0; i < titles.size(); i++) {
/* 44:38 */         Mark.say(new Object[] {"Moive: " + (String)titles.get(i) });
/* 45:   */       }
/* 46:41 */       Mark.say(new Object[] {"Doing movie " + title });
/* 47:42 */       obsolete.mediaFX.GenesisFX.movieStartTime = new Date().getTime();
/* 48:43 */       c.doMovie(title);
/* 49:   */     }
/* 50:   */     else
/* 51:   */     {
/* 52:46 */       Mark.err(new Object[] {"Get movie titles returned no titles." });
/* 53:   */     }
/* 54:48 */     int sec = 0;
/* 55:   */     for (;;)
/* 56:   */     {
/* 57:50 */       Mark.say(new Object[] {"Time:", Integer.valueOf(sec++ * 10) });
/* 58:51 */       Thread.sleep(10000L);
/* 59:   */     }
/* 60:   */   }
/* 61:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     obsolete.mindsEye.TestMindsEyeMachinery
 * JD-Core Version:    0.7.0.1
 */