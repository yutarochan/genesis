/*   1:    */ package gui.panels;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Component;
/*   5:    */ import java.awt.Container;
/*   6:    */ import java.awt.Dimension;
/*   7:    */ import java.awt.LayoutManager;
/*   8:    */ import javax.swing.JFrame;
/*   9:    */ import javax.swing.JPanel;
/*  10:    */ 
/*  11:    */ public class FramingPanel
/*  12:    */   extends JPanel
/*  13:    */ {
/*  14: 15 */   FramingLayout layout = new FramingLayout(90);
/*  15:    */   Component component;
/*  16:    */   
/*  17:    */   public FramingPanel()
/*  18:    */   {
/*  19: 21 */     setBackground(Color.WHITE);
/*  20: 22 */     setLayout(this.layout);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public FramingPanel(int percent)
/*  24:    */   {
/*  25: 26 */     this();
/*  26: 27 */     setFillPercent(percent);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public FramingPanel(Component center)
/*  30:    */   {
/*  31: 31 */     this();
/*  32: 32 */     add(center);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public FramingPanel(Component center, int percent)
/*  36:    */   {
/*  37: 36 */     this(center);
/*  38: 37 */     setFillPercent(percent);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Component add(Component center)
/*  42:    */   {
/*  43: 41 */     if (this.component != null) {
/*  44: 41 */       this.component.setBounds(0, 0, 0, 0);
/*  45:    */     }
/*  46: 42 */     removeAll();
/*  47: 43 */     super.add(center);
/*  48: 44 */     this.component = center;
/*  49: 45 */     return center;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setFillPercent(int i)
/*  53:    */   {
/*  54: 49 */     this.layout.setFillPercent(i);
/*  55:    */   }
/*  56:    */   
/*  57:    */   private class FramingLayout
/*  58:    */     implements LayoutManager
/*  59:    */   {
/*  60: 54 */     protected int fillPercent = 90;
/*  61:    */     
/*  62:    */     public FramingLayout() {}
/*  63:    */     
/*  64:    */     public FramingLayout(int i)
/*  65:    */     {
/*  66: 61 */       this();
/*  67: 62 */       this.fillPercent = i;
/*  68:    */     }
/*  69:    */     
/*  70:    */     public void setFillPercent(int i)
/*  71:    */     {
/*  72: 66 */       this.fillPercent = i;
/*  73:    */     }
/*  74:    */     
/*  75:    */     public void layoutContainer(Container parent)
/*  76:    */     {
/*  77: 71 */       synchronized (parent.getTreeLock())
/*  78:    */       {
/*  79: 72 */         if (parent.getComponents().length == 0) {
/*  80: 73 */           return;
/*  81:    */         }
/*  82: 75 */         int w = parent.getWidth();
/*  83: 76 */         int h = parent.getHeight();
/*  84:    */         
/*  85: 78 */         Dimension d = FramingPanel.this.component.getMinimumSize();
/*  86: 79 */         d = FramingPanel.this.component.getMaximumSize();
/*  87: 80 */         d = FramingPanel.this.component.getPreferredSize();
/*  88: 81 */         double centerW = d.getWidth();
/*  89: 82 */         double centerH = d.getHeight();
/*  90:    */         
/*  91:    */ 
/*  92: 85 */         int hSize = h * this.fillPercent / 100;
/*  93: 86 */         int wSize = (int)(centerW / centerH * hSize);
/*  94: 88 */         if (centerW * h / (centerH * w) > 1.0D)
/*  95:    */         {
/*  96: 90 */           wSize = w * this.fillPercent / 100;
/*  97: 91 */           hSize = (int)(centerH / centerW * wSize);
/*  98:    */         }
/*  99: 94 */         int wOffset = (w - wSize) / 2;
/* 100: 95 */         int hOffset = (h - hSize) / 2;
/* 101: 96 */         if (FramingPanel.this.component != null) {
/* 102: 97 */           FramingPanel.this.component.setBounds(wOffset, hOffset, wSize, hSize);
/* 103:    */         }
/* 104:    */       }
/* 105:    */     }
/* 106:    */     
/* 107:    */     public void removeLayoutComponent(Component component) {}
/* 108:    */     
/* 109:    */     public void addLayoutComponent(String arg0, Component component) {}
/* 110:    */     
/* 111:    */     public Dimension minimumLayoutSize(Container parent)
/* 112:    */     {
/* 113:111 */       if (parent.getComponents().length == 0) {
/* 114:112 */         return null;
/* 115:    */       }
/* 116:114 */       return parent.getComponent(0).getMinimumSize();
/* 117:    */     }
/* 118:    */     
/* 119:    */     public Dimension preferredLayoutSize(Container parent)
/* 120:    */     {
/* 121:118 */       if (parent.getComponents().length == 0) {
/* 122:119 */         return null;
/* 123:    */       }
/* 124:121 */       return parent.getComponent(0).getPreferredSize();
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static void main(String[] ignore)
/* 129:    */   {
/* 130:126 */     JFrame frame = new JFrame();
/* 131:127 */     FramingPanel bf = new FramingPanel();
/* 132:128 */     bf.setBackground(Color.WHITE);
/* 133:129 */     bf.setFillPercent(90);
/* 134:130 */     JPanel panel = new JPanel();
/* 135:131 */     panel.setPreferredSize(new Dimension(352, 288));
/* 136:132 */     panel.setBackground(Color.RED);
/* 137:133 */     bf.add(panel);
/* 138:    */     
/* 139:135 */     frame.getContentPane().add(bf);
/* 140:    */     
/* 141:137 */     frame.setBounds(0, 0, 500, 500);
/* 142:138 */     frame.show();
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.panels.FramingPanel
 * JD-Core Version:    0.7.0.1
 */