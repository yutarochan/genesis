/*  1:   */ package obsolete.mindsEye;
/*  2:   */ 
/*  3:   */ import connections.WiredBox;
/*  4:   */ import java.awt.BorderLayout;
/*  5:   */ import java.awt.Color;
/*  6:   */ import java.awt.image.BufferedImage;
/*  7:   */ import javax.swing.BorderFactory;
/*  8:   */ import javax.swing.JPanel;
/*  9:   */ 
/* 10:   */ public class MindsEyeMovieViewer
/* 11:   */   extends JPanel
/* 12:   */   implements WiredBox
/* 13:   */ {
/* 14:   */   private String movieName;
/* 15:   */   MindsEyeMovieViewerScreen screen;
/* 16:   */   MindsEyeViewerController controller;
/* 17:   */   
/* 18:   */   public MindsEyeMovieViewer(String name)
/* 19:   */   {
/* 20:29 */     this();
/* 21:30 */     this.movieName = name;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public MindsEyeMovieViewer()
/* 25:   */   {
/* 26:34 */     setBackground(Color.WHITE);
/* 27:35 */     setOpaque(true);
/* 28:36 */     setLayout(new BorderLayout());
/* 29:37 */     add(getScreen(), "Center");
/* 30:38 */     add(getController(), "South");
/* 31:   */     
/* 32:40 */     setBorder(BorderFactory.createBevelBorder(0));
/* 33:   */   }
/* 34:   */   
/* 35:   */   public synchronized void setImage(BufferedImage b)
/* 36:   */   {
/* 37:44 */     getScreen().setImage(b);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public synchronized void setFrame(int i)
/* 41:   */   {
/* 42:48 */     getController().setImage(i);
/* 43:49 */     getScreen().setFrame(i);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void setBounds(int start, int end)
/* 47:   */   {
/* 48:53 */     getController().setBounds(start, end);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public MindsEyeViewerController getController()
/* 52:   */   {
/* 53:57 */     if (this.controller == null) {
/* 54:58 */       this.controller = new MindsEyeViewerController();
/* 55:   */     }
/* 56:60 */     return this.controller;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public MindsEyeMovieViewerScreen getScreen()
/* 60:   */   {
/* 61:64 */     if (this.screen == null) {
/* 62:65 */       this.screen = new MindsEyeMovieViewerScreen();
/* 63:   */     }
/* 64:67 */     return this.screen;
/* 65:   */   }
/* 66:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     obsolete.mindsEye.MindsEyeMovieViewer
 * JD-Core Version:    0.7.0.1
 */