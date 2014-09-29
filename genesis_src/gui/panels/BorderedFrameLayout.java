/*  1:   */ package gui.panels;
/*  2:   */ 
/*  3:   */ import java.awt.Component;
/*  4:   */ import java.awt.Container;
/*  5:   */ import java.awt.Dimension;
/*  6:   */ import java.awt.LayoutManager;
/*  7:   */ 
/*  8:   */ public class BorderedFrameLayout
/*  9:   */   implements LayoutManager
/* 10:   */ {
/* 11: 9 */   protected int borderPercent = 10;
/* 12:   */   
/* 13:   */   public BorderedFrameLayout() {}
/* 14:   */   
/* 15:   */   public BorderedFrameLayout(int i)
/* 16:   */   {
/* 17:13 */     this();this.borderPercent = i;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void setBorderPercent(int i)
/* 21:   */   {
/* 22:15 */     this.borderPercent = i;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void layoutContainer(Container parent)
/* 26:   */   {
/* 27:20 */     synchronized (parent.getTreeLock())
/* 28:   */     {
/* 29:21 */       if (parent.getComponents().length == 0) {
/* 30:21 */         return;
/* 31:   */       }
/* 32:22 */       int w = parent.getWidth();
/* 33:23 */       int h = parent.getHeight();
/* 34:24 */       int size = Math.min(w, h);
/* 35:25 */       int offset = size * this.borderPercent / 100;
/* 36:   */       
/* 37:27 */       parent.getComponent(0).setBounds(offset, offset, w - 2 * offset, h - 2 * offset);
/* 38:   */     }
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void removeLayoutComponent(Component component) {}
/* 42:   */   
/* 43:   */   public void addLayoutComponent(String arg0, Component component) {}
/* 44:   */   
/* 45:   */   public Dimension minimumLayoutSize(Container parent)
/* 46:   */   {
/* 47:40 */     if (parent.getComponents().length == 0) {
/* 48:40 */       return null;
/* 49:   */     }
/* 50:41 */     return parent.getComponent(0).getMinimumSize();
/* 51:   */   }
/* 52:   */   
/* 53:   */   public Dimension preferredLayoutSize(Container parent)
/* 54:   */   {
/* 55:45 */     if (parent.getComponents().length == 0) {
/* 56:45 */       return null;
/* 57:   */     }
/* 58:46 */     return parent.getComponent(0).getPreferredSize();
/* 59:   */   }
/* 60:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.panels.BorderedFrameLayout
 * JD-Core Version:    0.7.0.1
 */