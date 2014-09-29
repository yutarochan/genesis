/*  1:   */ package genesis;
/*  2:   */ 
/*  3:   */ import gui.EasyPrint;
/*  4:   */ import java.awt.Component;
/*  5:   */ import java.awt.Dimension;
/*  6:   */ import java.awt.Graphics;
/*  7:   */ import java.awt.print.PageFormat;
/*  8:   */ import java.awt.print.Printable;
/*  9:   */ import javax.swing.JPanel;
/* 10:   */ 
/* 11:   */ public class GenesisFoundation
/* 12:   */   extends JPanel
/* 13:   */   implements Printable
/* 14:   */ {
/* 15:   */   public int print(Graphics graphics, PageFormat format, int pageIndex)
/* 16:   */   {
/* 17:18 */     return EasyPrint.easyPrint(this, graphics, format, pageIndex);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void printMe()
/* 21:   */   {
/* 22:22 */     EasyPrint.easyPrint(this);
/* 23:   */   }
/* 24:   */   
/* 25:   */   protected static void setPreferredWidth(Component c, int w)
/* 26:   */   {
/* 27:26 */     int h = c.getPreferredSize().height;
/* 28:27 */     c.setPreferredSize(new Dimension(w, h));
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected static void setPreferredHeight(Component c, int h)
/* 32:   */   {
/* 33:31 */     int w = c.getPreferredSize().width;
/* 34:32 */     c.setPreferredSize(new Dimension(w, h));
/* 35:   */   }
/* 36:   */   
/* 37:   */   protected static void setMinimumWidth(Component c, int w)
/* 38:   */   {
/* 39:36 */     int h = c.getMinimumSize().height;
/* 40:37 */     c.setMinimumSize(new Dimension(w, h));
/* 41:   */   }
/* 42:   */   
/* 43:   */   protected static void setMinimumHeight(Component c, int h)
/* 44:   */   {
/* 45:41 */     int w = c.getMinimumSize().width;
/* 46:42 */     c.setMinimumSize(new Dimension(w, h));
/* 47:   */   }
/* 48:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     genesis.GenesisFoundation
 * JD-Core Version:    0.7.0.1
 */