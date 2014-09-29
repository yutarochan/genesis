/*  1:   */ package gui;
/*  2:   */ 
/*  3:   */ import java.awt.Component;
/*  4:   */ import java.awt.Container;
/*  5:   */ import java.awt.GridLayout;
/*  6:   */ import java.awt.Insets;
/*  7:   */ 
/*  8:   */ public class RepairedGridLayout
/*  9:   */   extends GridLayout
/* 10:   */ {
/* 11:17 */   int total = 0;
/* 12:   */   
/* 13:   */   public RepairedGridLayout(int r, int c)
/* 14:   */   {
/* 15:19 */     super(r, c);this.total = (r * c);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void layoutContainer(Container parent)
/* 19:   */   {
/* 20:22 */     synchronized (parent.getTreeLock())
/* 21:   */     {
/* 22:23 */       Insets insets = parent.getInsets();
/* 23:24 */       int ncomponents = parent.getComponentCount();
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
/* 34:35 */       int nrows = getRows();
/* 35:36 */       int ncols = getColumns();
/* 36:37 */       if (ncomponents == 0) {
/* 37:38 */         return;
/* 38:   */       }
/* 39:40 */       if (nrows > 0) {
/* 40:41 */         ncols = (ncomponents + nrows - 1) / nrows;
/* 41:   */       } else {
/* 42:43 */         nrows = (ncomponents + ncols - 1) / ncols;
/* 43:   */       }
/* 44:45 */       int width = parent.getWidth() - (insets.left + insets.right);
/* 45:46 */       int height = parent.getHeight() - (insets.top + insets.bottom);
/* 46:47 */       int w = (width - (ncols - 1) * getHgap()) / ncols;
/* 47:48 */       int h = (height - (nrows - 1) * getVgap()) / nrows;
/* 48:50 */       for (int c = 0; c < ncols; c++)
/* 49:   */       {
/* 50:51 */         int xx = insets.left + c * width / ncols;
/* 51:52 */         int ww = insets.left + (c + 1) * width / ncols - xx - getHgap();
/* 52:53 */         for (int r = 0; r < nrows; r++)
/* 53:   */         {
/* 54:54 */           int yy = insets.top + r * height / nrows;
/* 55:55 */           int hh = insets.top + (r + 1) * height / nrows - yy - getVgap();
/* 56:56 */           int i = r * ncols + c;
/* 57:57 */           if (i < ncomponents) {
/* 58:59 */             parent.getComponent(i).setBounds(xx, yy, ww, hh);
/* 59:   */           }
/* 60:   */         }
/* 61:   */       }
/* 62:   */     }
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.RepairedGridLayout
 * JD-Core Version:    0.7.0.1
 */