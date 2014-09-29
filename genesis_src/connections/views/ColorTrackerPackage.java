/*  1:   */ package connections.views;
/*  2:   */ 
/*  3:   */ import java.awt.Color;
/*  4:   */ 
/*  5:   */ public class ColorTrackerPackage
/*  6:   */ {
/*  7:12 */   private long showTime = 2000L;
/*  8:   */   private Color permanentColor;
/*  9:   */   private Color temporaryColor;
/* 10:   */   private long quitTime;
/* 11:   */   private ColoredBox coloredBox;
/* 12:   */   
/* 13:   */   public long getQuitTime()
/* 14:   */   {
/* 15:23 */     return this.quitTime;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setQuitTime(long quitTime)
/* 19:   */   {
/* 20:27 */     this.quitTime = quitTime;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public ColoredBox getColoredBox()
/* 24:   */   {
/* 25:31 */     return this.coloredBox;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Color getPermanentColor()
/* 29:   */   {
/* 30:35 */     return this.permanentColor;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Color getTemporaryColor()
/* 34:   */   {
/* 35:39 */     return this.temporaryColor;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public ColorTrackerPackage(Color temporaryColor, Color permanentColor, ColoredBox b)
/* 39:   */   {
/* 40:43 */     this.permanentColor = permanentColor;
/* 41:44 */     this.temporaryColor = temporaryColor;
/* 42:45 */     this.coloredBox = b;
/* 43:46 */     this.quitTime = (System.currentTimeMillis() + this.showTime);
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     connections.views.ColorTrackerPackage
 * JD-Core Version:    0.7.0.1
 */