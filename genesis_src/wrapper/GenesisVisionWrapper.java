/*  1:   */ package wrapper;
/*  2:   */ 
/*  3:   */ import com.ascent.gui.frame.WFrameApplication;
/*  4:   */ import com.ascent.gui.frame.auxiliaries.WFrameApplicationStartException;
/*  5:   */ import com.ascent.gui.swing.WConstants;
/*  6:   */ import javax.swing.JComponent;
/*  7:   */ import javax.swing.JMenuBar;
/*  8:   */ import javax.swing.JPanel;
/*  9:   */ import links.words.BundleGenerator;
/* 10:   */ import obsolete.mediaFX.GenesisFX;
/* 11:   */ import start.Start;
/* 12:   */ import utils.Mark;
/* 13:   */ 
/* 14:   */ public class GenesisVisionWrapper
/* 15:   */   extends WFrameApplication
/* 16:   */ {
/* 17:   */   GenesisFX genesis;
/* 18:   */   
/* 19:   */   public String getNavigationBarItem()
/* 20:   */   {
/* 21:23 */     return "Vision";
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String getNavigationBarItemHelp()
/* 25:   */   {
/* 26:27 */     return "View the Genesis story understanding system";
/* 27:   */   }
/* 28:   */   
/* 29:   */   public GenesisFX getGenesis()
/* 30:   */   {
/* 31:31 */     if (this.genesis == null) {
/* 32:32 */       this.genesis = new GenesisFX();
/* 33:   */     }
/* 34:34 */     return this.genesis;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public JPanel getControlBar()
/* 38:   */   {
/* 39:38 */     return getGenesis().getControlBar();
/* 40:   */   }
/* 41:   */   
/* 42:   */   public JComponent getView()
/* 43:   */   {
/* 44:43 */     return getGenesis();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void start()
/* 48:   */     throws WFrameApplicationStartException
/* 49:   */   {
/* 50:48 */     super.start();
/* 51:49 */     Mark.say(new Object[] {"Start vision system" });
/* 52:50 */     getGenesis().start();
/* 53:   */   }
/* 54:   */   
/* 55:   */   public boolean isStarted()
/* 56:   */   {
/* 57:54 */     WConstants.setBannerGif(WConstants.getImage(TheGenesisSystem.class, "vision.gif"), 
/* 58:55 */       WConstants.getImage(TheGenesisSystem.class, "genesis-gray.gif"));
/* 59:56 */     return super.isStarted();
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void stop()
/* 63:   */   {
/* 64:61 */     super.stop();
/* 65:62 */     Mark.say(new Object[] {"Stop vision system" });
/* 66:63 */     BundleGenerator.writeWordnetCache();
/* 67:64 */     Start.writeStartCaches();
/* 68:   */   }
/* 69:   */   
/* 70:   */   public JMenuBar getMenuBar()
/* 71:   */   {
/* 72:69 */     return null;
/* 73:   */   }
/* 74:   */   
/* 75:   */   public String getAccessType()
/* 76:   */   {
/* 77:74 */     return null;
/* 78:   */   }
/* 79:   */   
/* 80:   */   public void restoreTaskBarImage() {}
/* 81:   */   
/* 82:   */   public void restoreTaskBarTitle() {}
/* 83:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     wrapper.GenesisVisionWrapper
 * JD-Core Version:    0.7.0.1
 */