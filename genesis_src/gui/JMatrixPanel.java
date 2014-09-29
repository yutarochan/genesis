/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import ati.BorderedParallelJPanel;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.awt.Component;
/*   6:    */ import java.awt.Container;
/*   7:    */ import java.awt.Dimension;
/*   8:    */ import java.awt.Insets;
/*   9:    */ import java.awt.LayoutManager;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import javax.swing.JFrame;
/*  12:    */ import javax.swing.JPanel;
/*  13:    */ import utils.Mark;
/*  14:    */ 
/*  15:    */ public class JMatrixPanel
/*  16:    */   extends JPanel
/*  17:    */ {
/*  18:    */   public JMatrixPanel()
/*  19:    */   {
/*  20: 20 */     setLayout(new MyLayoutManager());
/*  21:    */   }
/*  22:    */   
/*  23: 23 */   private ArrayList<Triple> components = new ArrayList();
/*  24: 25 */   private int cWidth = 0;
/*  25: 27 */   private int cHeight = 0;
/*  26:    */   
/*  27:    */   public void add(Component c, int x, int y, int w, int h)
/*  28:    */   {
/*  29: 30 */     super.add(c);
/*  30: 31 */     this.cWidth = Math.max(this.cWidth, x + w);
/*  31: 32 */     this.cHeight = Math.max(this.cHeight, y + h);
/*  32: 33 */     this.components.add(new Triple(c, x, y, w, h));
/*  33: 34 */     setBackground(Color.white);
/*  34: 35 */     setOpaque(true);
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected class MyLayoutManager
/*  38:    */     implements LayoutManager
/*  39:    */   {
/*  40:    */     protected MyLayoutManager() {}
/*  41:    */     
/*  42:    */     public void layoutContainer(Container parent)
/*  43:    */     {
/*  44: 41 */       synchronized (parent.getTreeLock())
/*  45:    */       {
/*  46: 42 */         Insets insets = parent.getInsets();
/*  47: 43 */         int ncomponents = JMatrixPanel.this.getComponentCount();
/*  48: 44 */         if (ncomponents == 0) {
/*  49: 45 */           return;
/*  50:    */         }
/*  51: 48 */         double wMultiplier = JMatrixPanel.this.getWidth() / JMatrixPanel.this.cWidth;
/*  52:    */         
/*  53: 50 */         double hMultiplier = JMatrixPanel.this.getHeight() / JMatrixPanel.this.cHeight;
/*  54: 52 */         for (JMatrixPanel.Triple t : JMatrixPanel.this.components) {
/*  55: 53 */           t.c.setBounds((int)(t.x * wMultiplier), (int)(t.y * hMultiplier), (int)(t.w * wMultiplier), (int)(t.h * hMultiplier));
/*  56:    */         }
/*  57:    */       }
/*  58:    */     }
/*  59:    */     
/*  60:    */     public void addLayoutComponent(String name, Component comp) {}
/*  61:    */     
/*  62:    */     public void removeLayoutComponent(Component comp) {}
/*  63:    */     
/*  64:    */     public Dimension preferredLayoutSize(Container parent)
/*  65:    */     {
/*  66: 70 */       return null;
/*  67:    */     }
/*  68:    */     
/*  69:    */     public Dimension minimumLayoutSize(Container parent)
/*  70:    */     {
/*  71: 75 */       return null;
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   class Triple
/*  76:    */   {
/*  77:    */     Component c;
/*  78:    */     public int x;
/*  79:    */     public int y;
/*  80:    */     public int w;
/*  81:    */     public int h;
/*  82:    */     
/*  83:    */     public Triple(Component c, int x, int y, int w, int h)
/*  84:    */     {
/*  85: 85 */       this.c = c;
/*  86: 86 */       this.x = x;
/*  87: 87 */       this.y = y;
/*  88: 88 */       this.w = w;
/*  89: 89 */       this.h = h;
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static void main(String[] ignore)
/*  94:    */   {
/*  95: 94 */     JFrame frame = new JFrame();
/*  96: 95 */     JMatrixPanel panel = new JMatrixPanel();
/*  97: 96 */     frame.getContentPane().add(panel);
/*  98: 97 */     BorderedParallelJPanel bpjp1 = new BorderedParallelJPanel("Hello");
/*  99: 98 */     BorderedParallelJPanel bpjp2 = new BorderedParallelJPanel("World");
/* 100: 99 */     BorderedParallelJPanel bpjp3 = new BorderedParallelJPanel("Cruel");
/* 101:100 */     bpjp1.addLeft("Hello world");
/* 102:101 */     Mark.say(new Object[] {"Insets", bpjp1.getInsets() });
/* 103:102 */     panel.add(bpjp1, 0, 0, 10, 10);
/* 104:103 */     panel.add(bpjp2, 10, 0, 5, 5);
/* 105:104 */     panel.add(bpjp3, 10, 5, 5, 5);
/* 106:105 */     frame.setBounds(0, 0, 600, 400);
/* 107:106 */     Mark.say(new Object[] {"foo" });
/* 108:107 */     frame.setVisible(true);
/* 109:    */   }
/* 110:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.JMatrixPanel
 * JD-Core Version:    0.7.0.1
 */