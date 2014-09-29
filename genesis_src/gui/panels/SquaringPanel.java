/*  1:   */ package gui.panels;
/*  2:   */ 
/*  3:   */ import java.awt.Color;
/*  4:   */ import java.awt.Component;
/*  5:   */ import java.awt.Container;
/*  6:   */ import javax.swing.JFrame;
/*  7:   */ import javax.swing.JPanel;
/*  8:   */ 
/*  9:   */ public class SquaringPanel
/* 10:   */   extends BorderingPanel
/* 11:   */ {
/* 12:16 */   protected LocalLayout layout = new LocalLayout(10);
/* 13:   */   
/* 14:   */   public SquaringPanel()
/* 15:   */   {
/* 16:20 */     setLayout(this.layout);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public SquaringPanel(Component center)
/* 20:   */   {
/* 21:24 */     this();
/* 22:25 */     add(center);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void setBorderPercent(int i)
/* 26:   */   {
/* 27:28 */     this.layout.setBorderPercent(i);
/* 28:   */   }
/* 29:   */   
/* 30:   */   class LocalLayout
/* 31:   */     extends BorderedFrameLayout
/* 32:   */   {
/* 33:   */     public LocalLayout(int i)
/* 34:   */     {
/* 35:32 */       super();
/* 36:   */     }
/* 37:   */     
/* 38:   */     public void layoutContainer(Container parent)
/* 39:   */     {
/* 40:35 */       synchronized (parent.getTreeLock())
/* 41:   */       {
/* 42:36 */         if (parent.getComponents().length == 0) {
/* 43:36 */           return;
/* 44:   */         }
/* 45:37 */         int w = parent.getWidth();
/* 46:38 */         int h = parent.getHeight();
/* 47:39 */         int size = (100 - this.borderPercent) * Math.min(w, h) / 100;
/* 48:   */         
/* 49:41 */         parent.getComponent(0).setBounds((w - size) / 2, (h - size) / 2, size, size);
/* 50:   */       }
/* 51:   */     }
/* 52:   */   }
/* 53:   */   
/* 54:   */   public static void main(String[] ignore)
/* 55:   */   {
/* 56:47 */     JFrame frame = new JFrame();
/* 57:48 */     SquaringPanel bf = new SquaringPanel();
/* 58:49 */     bf.setBackground(Color.WHITE);
/* 59:50 */     bf.setBorderPercent(20);
/* 60:51 */     JPanel panel = new JPanel();
/* 61:52 */     panel.setBackground(Color.RED);
/* 62:53 */     bf.add(panel);
/* 63:   */     
/* 64:55 */     frame.getContentPane().add(bf);
/* 65:56 */     frame.setBounds(0, 0, 800, 500);
/* 66:57 */     frame.show();
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.panels.SquaringPanel
 * JD-Core Version:    0.7.0.1
 */