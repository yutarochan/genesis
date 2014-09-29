/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import java.awt.Component;
/*   4:    */ import java.awt.Container;
/*   5:    */ import java.awt.Dimension;
/*   6:    */ import java.awt.LayoutManager;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import javax.swing.JComponent;
/*   9:    */ import javax.swing.JPanel;
/*  10:    */ 
/*  11:    */ public class BlinkingBoxPanel
/*  12:    */   extends JPanel
/*  13:    */ {
/*  14:    */   Arrow arrow;
/*  15: 21 */   private static int maximumItemsPerRow = 8;
/*  16:    */   
/*  17:    */   public BlinkingBoxPanel()
/*  18:    */   {
/*  19: 24 */     setLayout(new MyLayout());
/*  20:    */   }
/*  21:    */   
/*  22:    */   public Dimension getPreferredSize()
/*  23:    */   {
/*  24: 29 */     return new Dimension(1000, 500);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public Dimension getMinimumSize()
/*  28:    */   {
/*  29: 33 */     return new Dimension(100, 50);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Component add(Component c)
/*  33:    */   {
/*  34: 37 */     super.add(c);
/*  35: 38 */     getArrow().setTaps(getBoxes().size());
/*  36: 39 */     return c;
/*  37:    */   }
/*  38:    */   
/*  39:    */   class MyLayout
/*  40:    */     implements LayoutManager
/*  41:    */   {
/*  42: 43 */     ArrayList components = new ArrayList();
/*  43:    */     
/*  44:    */     MyLayout() {}
/*  45:    */     
/*  46:    */     public void addLayoutComponent(String ignore, Component component)
/*  47:    */     {
/*  48: 46 */       this.components.add(component);
/*  49:    */     }
/*  50:    */     
/*  51:    */     public void removeLayoutComponent(Component object)
/*  52:    */     {
/*  53: 50 */       this.components.remove(object);
/*  54:    */     }
/*  55:    */     
/*  56:    */     public Dimension preferredLayoutSize(Container arg0)
/*  57:    */     {
/*  58: 54 */       return new Dimension(800, 800);
/*  59:    */     }
/*  60:    */     
/*  61:    */     public Dimension minimumLayoutSize(Container arg0)
/*  62:    */     {
/*  63: 58 */       return null;
/*  64:    */     }
/*  65:    */     
/*  66:    */     public void layoutContainer(Container arg0)
/*  67:    */     {
/*  68: 62 */       ArrayList boxes = BlinkingBoxPanel.this.getBoxes();
/*  69: 63 */       int width = BlinkingBoxPanel.this.getWidth();
/*  70: 64 */       int height = BlinkingBoxPanel.this.getHeight();
/*  71: 65 */       if ((width == 0) || (height == 0)) {
/*  72: 66 */         return;
/*  73:    */       }
/*  74: 68 */       int items = boxes.size();
/*  75: 69 */       int spacers = items + 1;
/*  76: 70 */       if (items == 0) {
/*  77: 71 */         return;
/*  78:    */       }
/*  79: 74 */       int rows = items / BlinkingBoxPanel.maximumItemsPerRow;
/*  80: 75 */       int leftOvers = items % BlinkingBoxPanel.maximumItemsPerRow;
/*  81:    */       
/*  82: 77 */       int itemsInLastRow = BlinkingBoxPanel.maximumItemsPerRow;
/*  83: 79 */       if (leftOvers > 0)
/*  84:    */       {
/*  85: 80 */         rows++;
/*  86: 81 */         itemsInLastRow = leftOvers;
/*  87:    */       }
/*  88: 84 */       int boxHeight = 9 * height / 10;
/*  89:    */       
/*  90: 86 */       boxHeight /= rows;
/*  91:    */       
/*  92: 88 */       int yOffset = 0;
/*  93: 93 */       for (int row = 0; row < rows; row++)
/*  94:    */       {
/*  95: 95 */         int totalPreferredBoxWidth = 0;
/*  96: 96 */         for (int i = 0; i < (row != rows - 1 ? BlinkingBoxPanel.maximumItemsPerRow : itemsInLastRow); i++)
/*  97:    */         {
/*  98: 97 */           JComponent c = (JComponent)boxes.get(i + row * BlinkingBoxPanel.maximumItemsPerRow);
/*  99: 98 */           totalPreferredBoxWidth += c.getPreferredSize().width;
/* 100:    */         }
/* 101:101 */         int actualTotalBoxWidth = width;
/* 102:    */         
/* 103:103 */         int spacerWidth = 0;
/* 104:104 */         int xOffset = spacerWidth;
/* 105:105 */         for (int i = 0; i < (row != rows - 1 ? BlinkingBoxPanel.maximumItemsPerRow : itemsInLastRow); i++)
/* 106:    */         {
/* 107:108 */           Component c = (Component)boxes.get(i + row * BlinkingBoxPanel.maximumItemsPerRow);
/* 108:109 */           int actualWidth = c.getPreferredSize().width * actualTotalBoxWidth / totalPreferredBoxWidth;
/* 109:110 */           c.setBounds(xOffset, yOffset + row * boxHeight, actualWidth, boxHeight);
/* 110:111 */           xOffset += actualWidth + spacerWidth;
/* 111:    */         }
/* 112:    */       }
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   private ArrayList getBoxes()
/* 117:    */   {
/* 118:122 */     ArrayList list = new ArrayList();
/* 119:123 */     Component[] components = getComponents();
/* 120:124 */     for (int i = 0; i < components.length; i++) {
/* 121:125 */       if ((components[i] instanceof BlinkingBox)) {
/* 122:126 */         list.add(components[i]);
/* 123:128 */       } else if ((components[i] instanceof JPanel)) {
/* 124:129 */         list.add(components[i]);
/* 125:    */       }
/* 126:    */     }
/* 127:132 */     return list;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Arrow getArrow()
/* 131:    */   {
/* 132:171 */     if (this.arrow == null) {
/* 133:172 */       this.arrow = new Arrow();
/* 134:    */     }
/* 135:174 */     return this.arrow;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void redo()
/* 139:    */   {
/* 140:178 */     for (Component c : getComponents()) {
/* 141:179 */       ((JComponent)c).revalidate();
/* 142:    */     }
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.BlinkingBoxPanel
 * JD-Core Version:    0.7.0.1
 */