/*  1:   */ package wrapper;
/*  2:   */ 
/*  3:   */ import com.ascent.gui.frame.ABasicFrame;
/*  4:   */ import com.ascent.gui.swing.WConstants;
/*  5:   */ import utils.Mark;
/*  6:   */ import utils.Webstart;
/*  7:   */ 
/*  8:   */ public class TheTestSystem
/*  9:   */   extends ABasicFrame
/* 10:   */ {
/* 11:   */   public TheTestSystem(String[] args)
/* 12:   */   {
/* 13:17 */     super(args, TheTestSystem.class.getResource("test.xml"));
/* 14:18 */     if (args.length != 0) {
/* 15:19 */       Webstart.setWebStart(true);
/* 16:   */     } else {
/* 17:22 */       Webstart.setWebStart(false);
/* 18:   */     }
/* 19:24 */     setTitle("Genesis");
/* 20:25 */     WConstants.setRequiresLogin(false);
/* 21:26 */     WConstants.setBannerGif(WConstants.getImage(TheTestSystem.class, "genesis.gif"), 
/* 22:27 */       WConstants.getImage(TheTestSystem.class, "genesis-gray.gif"));
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static void main(String[] args)
/* 26:   */   {
/* 27:35 */     Mark.say(
/* 28:   */     
/* 29:   */ 
/* 30:   */ 
/* 31:   */ 
/* 32:40 */       new Object[] { System.getProperty("os.name") });Mark.say(new Object[] { System.getProperty("os.arch") });new TheTestSystem(args).start();
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     wrapper.TheTestSystem
 * JD-Core Version:    0.7.0.1
 */