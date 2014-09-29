/*  1:   */ package wrapper;
/*  2:   */ 
/*  3:   */ import com.ascent.gui.frame.WFrameApplication;
/*  4:   */ import com.ascent.gui.frame.auxiliaries.WFrameApplicationStartException;
/*  5:   */ import com.ascent.gui.swing.WConstants;
/*  6:   */ import genesis.Genesis;
/*  7:   */ import javax.swing.JComponent;
/*  8:   */ import javax.swing.JMenuBar;
/*  9:   */ import links.words.BundleGenerator;
/* 10:   */ import start.Start;
/* 11:   */ import utils.Mark;
/* 12:   */ 
/* 13:   */ public class GenesisStoryWrapper
/* 14:   */   extends WFrameApplication
/* 15:   */ {
/* 16:   */   Genesis genesis;
/* 17:   */   
/* 18:   */   public String getNavigationBarItem()
/* 19:   */   {
/* 20:25 */     return "Genesis";
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getNavigationBarItemHelp()
/* 24:   */   {
/* 25:29 */     return "View the Genesis story understanding system";
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Genesis getGenesis()
/* 29:   */   {
/* 30:33 */     if (this.genesis == null) {
/* 31:34 */       this.genesis = new Genesis();
/* 32:   */     }
/* 33:36 */     return this.genesis;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public JComponent getView()
/* 37:   */   {
/* 38:41 */     return getGenesis();
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void start()
/* 42:   */     throws WFrameApplicationStartException
/* 43:   */   {
/* 44:46 */     super.start();
/* 45:47 */     Mark.say(new Object[] {"Start story system" });
/* 46:48 */     getGenesis().start();
/* 47:   */   }
/* 48:   */   
/* 49:   */   public boolean isStarted()
/* 50:   */   {
/* 51:52 */     WConstants.setBannerGif(WConstants.getImage(TheGenesisSystem.class, "story.gif"), 
/* 52:53 */       WConstants.getImage(TheGenesisSystem.class, "genesis-gray.gif"));
/* 53:54 */     return super.isStarted();
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void stop()
/* 57:   */   {
/* 58:58 */     super.stop();
/* 59:59 */     Mark.say(new Object[] {"Stop story system" });
/* 60:60 */     BundleGenerator.writeWordnetCache();
/* 61:61 */     Start.writeStartCaches();
/* 62:   */   }
/* 63:   */   
/* 64:   */   public JMenuBar getMenuBar()
/* 65:   */   {
/* 66:66 */     return getGenesis().getMenuBar();
/* 67:   */   }
/* 68:   */   
/* 69:   */   public String getAccessType()
/* 70:   */   {
/* 71:71 */     return null;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public void restoreTaskBarImage() {}
/* 75:   */   
/* 76:   */   public void restoreTaskBarTitle() {}
/* 77:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     wrapper.GenesisStoryWrapper
 * JD-Core Version:    0.7.0.1
 */