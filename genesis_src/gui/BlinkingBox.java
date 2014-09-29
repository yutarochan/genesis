/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import connections.WiredBox;
/*   4:    */ import connections.views.ColorTracker;
/*   5:    */ import connections.views.ColorTrackerPackage;
/*   6:    */ import connections.views.ColoredBox;
/*   7:    */ import java.awt.Color;
/*   8:    */ import java.awt.Component;
/*   9:    */ import java.awt.Container;
/*  10:    */ import java.awt.Dimension;
/*  11:    */ import java.awt.Font;
/*  12:    */ import java.awt.FontMetrics;
/*  13:    */ import java.awt.Graphics;
/*  14:    */ import java.awt.Insets;
/*  15:    */ import java.awt.LayoutManager;
/*  16:    */ import javax.swing.BorderFactory;
/*  17:    */ import javax.swing.JComponent;
/*  18:    */ import javax.swing.JFrame;
/*  19:    */ import javax.swing.JLabel;
/*  20:    */ import javax.swing.JPanel;
/*  21:    */ import javax.swing.border.TitledBorder;
/*  22:    */ 
/*  23:    */ public class BlinkingBox
/*  24:    */   extends JPanel
/*  25:    */   implements WiredBox, ColoredBox
/*  26:    */ {
/*  27: 19 */   private long blinkingTime = 1000L;
/*  28: 21 */   private int count = 0;
/*  29:    */   private Color colorHandle;
/*  30:    */   private JComponent memory;
/*  31:    */   private JComponent graphic;
/*  32:    */   private Font myFont;
/*  33: 31 */   private boolean blinkSwitch = true;
/*  34:    */   
/*  35:    */   public boolean isBlinkSwitch()
/*  36:    */   {
/*  37: 44 */     return this.blinkSwitch;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setBlinkSwitch(boolean blinkSwitch)
/*  41:    */   {
/*  42: 48 */     this.blinkSwitch = blinkSwitch;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public BlinkingBox()
/*  46:    */   {
/*  47: 53 */     setLayout(new MyLayoutManager());
/*  48: 54 */     setBackground(Color.WHITE);
/*  49: 55 */     setOpaque(true);
/*  50: 56 */     TitledBorder border = BorderFactory.createTitledBorder("");
/*  51:    */     
/*  52: 58 */     Font newFont = new Font("Serif", 0, 10);
/*  53: 59 */     border.setTitleFont(newFont);
/*  54: 60 */     setBorder(border);
/*  55:    */     
/*  56: 62 */     setPreferredSize(new Dimension(200, 400));
/*  57: 63 */     Font f = getFont();
/*  58: 64 */     this.myFont = new Font(f.getName(), 1, f.getSize());
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void blink()
/*  62:    */   {
/*  63: 68 */     incrementCount();
/*  64:    */     
/*  65: 70 */     ColorTracker.getTracker().process(new ColorTrackerPackage(Color.YELLOW, Color.WHITE, this));
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setColor(Color color)
/*  69:    */   {
/*  70: 74 */     setBackground(color);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void paintComponent(Graphics g)
/*  74:    */   {
/*  75: 78 */     super.paintComponent(g);
/*  76: 79 */     int w = getWidth();
/*  77: 80 */     if (this.count > 0)
/*  78:    */     {
/*  79: 81 */       g.setFont(this.myFont);
/*  80: 82 */       FontMetrics fm = g.getFontMetrics();
/*  81: 83 */       String report = Integer.toString(this.count);
/*  82: 84 */       g.drawString(report, w - fm.stringWidth(report) - 10, fm.getHeight() + 4);
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   public static void main(String[] args)
/*  87:    */   {
/*  88: 90 */     BlinkingBox box = new BlinkingBox();
/*  89: 91 */     JFrame frame = new JFrame("Testing");
/*  90: 92 */     frame.getContentPane().add(box, "Center");
/*  91: 93 */     frame.setBounds(100, 100, 400, 400);
/*  92: 94 */     frame.setDefaultCloseOperation(3);
/*  93: 95 */     frame.setVisible(true);
/*  94: 96 */     JLabel label = new JLabel("Hello World");
/*  95: 97 */     box.add(label);
/*  96: 98 */     box.blink();
/*  97: 99 */     box.blink();
/*  98:100 */     label.setText("Goodby World");
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setTitle(String text)
/* 102:    */   {
/* 103:105 */     ((TitledBorder)getBorder()).setTitle(text);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void incrementCount()
/* 107:    */   {
/* 108:109 */     this.count += 1;
/* 109:    */   }
/* 110:    */   
/* 111:    */   private Color getColorHandle()
/* 112:    */   {
/* 113:113 */     if (this.colorHandle == null) {
/* 114:114 */       this.colorHandle = getBackground();
/* 115:    */     }
/* 116:116 */     return this.colorHandle;
/* 117:    */   }
/* 118:    */   
/* 119:    */   class MyLayoutManager
/* 120:    */     implements LayoutManager
/* 121:    */   {
/* 122:    */     MyLayoutManager() {}
/* 123:    */     
/* 124:    */     public void layoutContainer(Container container)
/* 125:    */     {
/* 126:122 */       int height = BlinkingBox.this.getHeight();
/* 127:123 */       int width = BlinkingBox.this.getWidth();
/* 128:124 */       Insets insets = BlinkingBox.this.getInsets();
/* 129:125 */       if ((height == 0) || (width == 0)) {
/* 130:126 */         return;
/* 131:    */       }
/* 132:128 */       height -= insets.top + insets.bottom;
/* 133:129 */       int yOffset = 0;
/* 134:136 */       if (BlinkingBox.this.memory != null) {
/* 135:137 */         BlinkingBox.this.memory.setBounds(0, 0, 0, 0);
/* 136:    */       }
/* 137:139 */       if (BlinkingBox.this.graphic != null) {
/* 138:140 */         BlinkingBox.this.graphic.setBounds(insets.left, insets.top + yOffset, width - insets.left - insets.right, height);
/* 139:    */       }
/* 140:    */     }
/* 141:    */     
/* 142:    */     public void removeLayoutComponent(Component component) {}
/* 143:    */     
/* 144:    */     public void addLayoutComponent(String string, Component arg1) {}
/* 145:    */     
/* 146:    */     public Dimension minimumLayoutSize(Container parent)
/* 147:    */     {
/* 148:151 */       return null;
/* 149:    */     }
/* 150:    */     
/* 151:    */     public Dimension preferredLayoutSize(Container parent)
/* 152:    */     {
/* 153:155 */       return null;
/* 154:    */     }
/* 155:    */   }
/* 156:    */   
/* 157:    */   public JComponent getGraphic()
/* 158:    */   {
/* 159:160 */     return this.graphic;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setGraphic(JComponent graphic)
/* 163:    */   {
/* 164:164 */     if (this.graphic != null) {
/* 165:165 */       remove(this.memory);
/* 166:    */     }
/* 167:167 */     this.graphic = graphic;
/* 168:    */     
/* 169:    */ 
/* 170:170 */     add(this.graphic);
/* 171:    */   }
/* 172:    */   
/* 173:    */   public JComponent getMemory()
/* 174:    */   {
/* 175:174 */     return this.memory;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setMemory(JComponent memory)
/* 179:    */   {
/* 180:178 */     if (this.memory != null) {
/* 181:179 */       remove(this.memory);
/* 182:    */     }
/* 183:181 */     this.memory = memory;
/* 184:    */     
/* 185:    */ 
/* 186:184 */     add(this.memory);
/* 187:    */   }
/* 188:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.BlinkingBox
 * JD-Core Version:    0.7.0.1
 */