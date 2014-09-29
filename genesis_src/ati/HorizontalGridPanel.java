/*  1:   */ package ati;
/*  2:   */ 
/*  3:   */ import java.awt.Component;
/*  4:   */ import java.awt.Dimension;
/*  5:   */ import java.awt.GridLayout;
/*  6:   */ import javax.swing.JComponent;
/*  7:   */ 
/*  8:   */ public class HorizontalGridPanel
/*  9:   */   extends SpecialJPanel
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = -1423772263073479166L;
/* 12:   */   
/* 13:   */   public HorizontalGridPanel()
/* 14:   */   {
/* 15:21 */     setLayout(new GridLayout(1, 0));
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Dimension getPreferredSize()
/* 19:   */   {
/* 20:25 */     Component[] components = getComponents();
/* 21:26 */     int width = 0;int maxWidth = 0;int maxHeight = 0;
/* 22:27 */     for (int i = 0; i < components.length; i++) {
/* 23:28 */       if ((components[i] instanceof JComponent))
/* 24:   */       {
/* 25:29 */         Dimension d = ((JComponent)components[i]).getPreferredSize();
/* 26:30 */         width += d.width;
/* 27:31 */         maxWidth = Math.max(maxWidth, d.width);
/* 28:32 */         maxHeight = Math.max(maxHeight, d.height);
/* 29:   */       }
/* 30:   */     }
/* 31:35 */     return new Dimension(components.length * maxWidth, maxHeight);
/* 32:   */   }
/* 33:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     ati.HorizontalGridPanel
 * JD-Core Version:    0.7.0.1
 */