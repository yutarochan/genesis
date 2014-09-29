/*  1:   */ package ati;
/*  2:   */ 
/*  3:   */ import java.awt.Dimension;
/*  4:   */ import javax.swing.JLabel;
/*  5:   */ 
/*  6:   */ public class TitledJPanelAdvice
/*  7:   */   extends JLabel
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = -1972370876623480936L;
/* 10:   */   public static final String adviceHeader = "<html><body><span style=\"color: #2B9F79; font-family:Arial, Verdana, Helvetica, sans-serif; font-size: 14pt; font-weight: bold\">";
/* 11:   */   public static final String adviceTrailer = "</span></body></html>";
/* 12:   */   
/* 13:   */   public TitledJPanelAdvice()
/* 14:   */   {
/* 15:23 */     setOpaque(false);
/* 16:24 */     setBorder(null);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public Dimension getPreferredSize()
/* 20:   */   {
/* 21:28 */     Dimension d = super.getPreferredSize();
/* 22:29 */     return new Dimension(d.width, d.height + 5);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void setText(String advice)
/* 26:   */   {
/* 27:38 */     String text = "<html><body><span style=\"color: #2B9F79; font-family:Arial, Verdana, Helvetica, sans-serif; font-size: 14pt; font-weight: bold\">";
/* 28:39 */     text = text + advice;
/* 29:40 */     text = text + "</span></body></html>";
/* 30:41 */     super.setText(text);
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     ati.TitledJPanelAdvice
 * JD-Core Version:    0.7.0.1
 */