/*  1:   */ package gui.panels;
/*  2:   */ 
/*  3:   */ import java.awt.Color;
/*  4:   */ import java.awt.Component;
/*  5:   */ import java.awt.Container;
/*  6:   */ import javax.swing.JFrame;
/*  7:   */ import javax.swing.JPanel;
/*  8:   */ 
/*  9:   */ public class BorderingPanel
/* 10:   */   extends JPanel
/* 11:   */ {
/* 12:13 */   BorderedFrameLayout layout = new BorderedFrameLayout(10);
/* 13:   */   
/* 14:   */   public BorderingPanel()
/* 15:   */   {
/* 16:17 */     setBackground(Color.WHITE);
/* 17:18 */     setLayout(this.layout);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public BorderingPanel(int percent)
/* 21:   */   {
/* 22:22 */     this();
/* 23:23 */     setBorderPercent(percent);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public BorderingPanel(Component center)
/* 27:   */   {
/* 28:27 */     this();
/* 29:28 */     add(center);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public BorderingPanel(Component center, int percent)
/* 33:   */   {
/* 34:32 */     this(center);
/* 35:33 */     setBorderPercent(percent);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Component add(Component center)
/* 39:   */   {
/* 40:37 */     super.add(center);
/* 41:38 */     return center;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void setBorderPercent(int i)
/* 45:   */   {
/* 46:42 */     this.layout.setBorderPercent(i);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static void main(String[] ignore)
/* 50:   */   {
/* 51:46 */     JFrame frame = new JFrame();
/* 52:47 */     BorderingPanel bf = new BorderingPanel();
/* 53:48 */     bf.setBackground(Color.WHITE);
/* 54:49 */     bf.setBorderPercent(20);
/* 55:50 */     JPanel panel = new JPanel();
/* 56:51 */     panel.setBackground(Color.RED);
/* 57:52 */     bf.add(panel);
/* 58:   */     
/* 59:54 */     frame.getContentPane().add(bf);
/* 60:   */     
/* 61:56 */     frame.setBounds(0, 0, 500, 500);
/* 62:57 */     frame.show();
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.panels.BorderingPanel
 * JD-Core Version:    0.7.0.1
 */