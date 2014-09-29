/*  1:   */ package gui;
/*  2:   */ 
/*  3:   */ import gui.images.GuiImagesAnchor;
/*  4:   */ import javax.swing.ImageIcon;
/*  5:   */ import javax.swing.JLabel;
/*  6:   */ 
/*  7:   */ public class OnOffLabel
/*  8:   */   extends JLabel
/*  9:   */ {
/* 10:14 */   public static ImageIcon redIcon = new ImageIcon(GuiImagesAnchor.class.getResource("red.png"));
/* 11:16 */   public static ImageIcon greenIcon = new ImageIcon(GuiImagesAnchor.class.getResource("green.png"));
/* 12:   */   
/* 13:   */   public OnOffLabel(String name)
/* 14:   */   {
/* 15:19 */     setText(name);
/* 16:20 */     setIcon(greenIcon);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void turnOn()
/* 20:   */   {
/* 21:24 */     setIcon(redIcon);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void turnOff()
/* 25:   */   {
/* 26:28 */     setIcon(greenIcon);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void setState(boolean state)
/* 30:   */   {
/* 31:32 */     if (state) {
/* 32:33 */       turnOn();
/* 33:   */     } else {
/* 34:36 */       turnOff();
/* 35:   */     }
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.OnOffLabel
 * JD-Core Version:    0.7.0.1
 */