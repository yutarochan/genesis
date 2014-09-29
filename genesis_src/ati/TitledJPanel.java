/*   1:    */ package ati;
/*   2:    */ 
/*   3:    */ import java.awt.BorderLayout;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.awt.Component;
/*   6:    */ import java.awt.Container;
/*   7:    */ import java.awt.Dimension;
/*   8:    */ import java.awt.GradientPaint;
/*   9:    */ import java.awt.Graphics;
/*  10:    */ import java.awt.Graphics2D;
/*  11:    */ import java.awt.GridLayout;
/*  12:    */ import java.awt.Insets;
/*  13:    */ import java.awt.LayoutManager;
/*  14:    */ import java.awt.geom.Rectangle2D;
/*  15:    */ import java.awt.geom.Rectangle2D.Float;
/*  16:    */ import javax.swing.Icon;
/*  17:    */ import javax.swing.JComponent;
/*  18:    */ import javax.swing.JFrame;
/*  19:    */ import javax.swing.JLabel;
/*  20:    */ import javax.swing.JPanel;
/*  21:    */ 
/*  22:    */ public class TitledJPanel
/*  23:    */   extends SpecialJPanel
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = -6737701934393843165L;
/*  26:    */   protected JPanel container;
/*  27:    */   private TitledJPanelBorder theBorder;
/*  28:    */   
/*  29:    */   public TitledJPanel(boolean isDoubleBuffered)
/*  30:    */   {
/*  31: 24 */     super(isDoubleBuffered);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public TitledJPanel(LayoutManager layout, boolean isDoubleBuffered)
/*  35:    */   {
/*  36: 28 */     super(layout, isDoubleBuffered);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public TitledJPanel(LayoutManager layout)
/*  40:    */   {
/*  41: 32 */     super(layout);
/*  42:    */   }
/*  43:    */   
/*  44: 35 */   private Dimension preferredTitleSize = new Dimension(50, 20);
/*  45: 37 */   protected TitlePanel titleLabel = new TitlePanel();
/*  46: 39 */   protected JComponent mainPanel = new JPanel();
/*  47: 41 */   private boolean showTitleBar = true;
/*  48: 43 */   private boolean showMainPanel = true;
/*  49:    */   public static final int NORMAL = 0;
/*  50:    */   public static final int HIGHLIGHTED = 1;
/*  51:    */   public static final int FADED = 2;
/*  52: 47 */   private int titleState = 0;
/*  53: 49 */   private JComponent embeddedComponent = new JPanel();
/*  54: 51 */   public static final Color topColor = new Color(184, 187, 207);
/*  55: 53 */   public static final Color bottomColor = new Color(195, 200, 223);
/*  56:    */   public static final int LEFT_MARGIN = 10;
/*  57:    */   
/*  58:    */   public TitledJPanel()
/*  59:    */   {
/*  60: 61 */     this("");
/*  61:    */   }
/*  62:    */   
/*  63:    */   public TitledJPanel(String title)
/*  64:    */   {
/*  65: 65 */     this(title, "");
/*  66:    */   }
/*  67:    */   
/*  68:    */   public TitledJPanel(String title, String border)
/*  69:    */   {
/*  70: 72 */     setTitleState(0);
/*  71: 73 */     this.titleLabel.setOpaque(true);
/*  72: 74 */     this.titleLabel.setForeground(new Color(88, 94, 133));
/*  73: 75 */     this.titleLabel.setPreferredSize(this.preferredTitleSize);
/*  74:    */     
/*  75: 77 */     this.mainPanel.setOpaque(true);
/*  76: 78 */     setMainPanel(this.embeddedComponent);
/*  77: 79 */     setTitle(title);
/*  78:    */     
/*  79: 81 */     this.mainPanel.setLayout(new GridLayout(1, 1));
/*  80:    */     
/*  81: 83 */     this.container = new JPanel();
/*  82: 84 */     this.container.setLayout(new MyLayoutManager());
/*  83: 85 */     this.container.add(this.titleLabel);
/*  84: 86 */     this.container.add(this.mainPanel);
/*  85: 87 */     this.container.add(getAdvicePanel());
/*  86: 88 */     this.theBorder = new TitledJPanelBorder();
/*  87: 89 */     setBorders(border);
/*  88: 90 */     setBorder(this.theBorder);
/*  89: 91 */     setLayout(new BorderLayout());
/*  90: 92 */     add(this.container, "Center");
/*  91:    */     
/*  92: 94 */     this.titleLabel.setBorder(null);
/*  93: 95 */     this.mainPanel.setBorder(null);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setBorders(String border)
/*  97:    */   {
/*  98: 99 */     this.theBorder.setBorders(border);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void includeBorders(String border)
/* 102:    */   {
/* 103:103 */     this.theBorder.includeBorders(border);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Dimension getPreferredSize()
/* 107:    */   {
/* 108:111 */     Dimension d1 = this.titleLabel.getPreferredSize();
/* 109:112 */     Dimension d2 = this.embeddedComponent.getPreferredSize();
/* 110:113 */     Dimension d3 = getAdvicePanel().getPreferredSize();
/* 111:114 */     int height = 0;int width = 0;
/* 112:115 */     if (isShowTitleBar())
/* 113:    */     {
/* 114:116 */       width = d1.width;
/* 115:117 */       height = d1.height;
/* 116:    */     }
/* 117:119 */     if (isShowMainPanel())
/* 118:    */     {
/* 119:120 */       width = Math.max(width, d2.width);
/* 120:121 */       height += d2.height;
/* 121:    */     }
/* 122:123 */     if (isShowAdvice()) {
/* 123:124 */       height += d3.height;
/* 124:    */     }
/* 125:126 */     return new Dimension(width, height);
/* 126:    */   }
/* 127:    */   
/* 128:129 */   Dimension minimumSize = new Dimension(0, 0);
/* 129:    */   
/* 130:    */   public Dimension getMinimumSize()
/* 131:    */   {
/* 132:132 */     return this.minimumSize;
/* 133:    */   }
/* 134:    */   
/* 135:135 */   Dimension maximumSize = new Dimension(10000, 10000);
/* 136:    */   
/* 137:    */   public Dimension getMaximumSize()
/* 138:    */   {
/* 139:138 */     return this.maximumSize;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setRightInset()
/* 143:    */   {
/* 144:145 */     getInsets().right = super.getInsets().right;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setLeftInset()
/* 148:    */   {
/* 149:152 */     getInsets().left = super.getInsets().left;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setTopInset()
/* 153:    */   {
/* 154:159 */     getInsets().top = super.getInsets().top;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setBottomInset()
/* 158:    */   {
/* 159:166 */     getInsets().bottom = super.getInsets().bottom;
/* 160:    */   }
/* 161:    */   
/* 162:    */   /**
/* 163:    */    * @deprecated
/* 164:    */    */
/* 165:    */   public Component addMain(Component c)
/* 166:    */   {
/* 167:173 */     return this.mainPanel.add(c);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setLayoutMain(LayoutManager layout)
/* 171:    */   {
/* 172:180 */     this.mainPanel.setLayout(layout);
/* 173:    */   }
/* 174:    */   
/* 175:    */   public JComponent getMainPanel()
/* 176:    */   {
/* 177:187 */     return this.embeddedComponent;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setMainPanel(JComponent c)
/* 181:    */   {
/* 182:194 */     this.embeddedComponent = c;
/* 183:195 */     this.mainPanel.removeAll();
/* 184:196 */     this.mainPanel.add(c);
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setTitle(String title)
/* 188:    */   {
/* 189:204 */     this.titleLabel.setText(title);
/* 190:    */   }
/* 191:    */   
/* 192:    */   class MyLayoutManager
/* 193:    */     implements LayoutManager
/* 194:    */   {
/* 195:    */     MyLayoutManager() {}
/* 196:    */     
/* 197:    */     public void removeLayoutComponent(Component component) {}
/* 198:    */     
/* 199:    */     public void addLayoutComponent(String string, Component arg1) {}
/* 200:    */     
/* 201:    */     public Dimension minimumLayoutSize(Container container)
/* 202:    */     {
/* 203:216 */       return null;
/* 204:    */     }
/* 205:    */     
/* 206:    */     public Dimension preferredLayoutSize(Container container)
/* 207:    */     {
/* 208:220 */       return null;
/* 209:    */     }
/* 210:    */     
/* 211:    */     public void layoutContainer(Container container)
/* 212:    */     {
/* 213:224 */       int height = TitledJPanel.this.getHeight();
/* 214:225 */       int width = TitledJPanel.this.getWidth();
/* 215:226 */       int tHeight = 0;
/* 216:227 */       int mHeight = 0;
/* 217:228 */       int correctedWidth = width;
/* 218:229 */       int correctedHeight = height;
/* 219:230 */       if (TitledJPanel.this.isShowTitleBar()) {
/* 220:231 */         tHeight = TitledJPanel.this.titleLabel.getPreferredSize().height;
/* 221:    */       }
/* 222:233 */       mHeight = correctedHeight - tHeight - 1;
/* 223:234 */       TitledJPanel.this.titleLabel.setBounds(0, 0, correctedWidth, tHeight);
/* 224:    */       
/* 225:236 */       int adviceHeight = 0;
/* 226:238 */       if ((TitledJPanel.isShowNames()) || ((TitledJPanel.isShowAdvice()) && (TitledJPanel.this.hasAdvice()))) {
/* 227:239 */         adviceHeight = TitledJPanel.this.getAdvicePanel().getPreferredSize().height;
/* 228:    */       } else {
/* 229:242 */         adviceHeight = 0;
/* 230:    */       }
/* 231:244 */       if (TitledJPanel.this.showMainPanel) {
/* 232:245 */         TitledJPanel.this.mainPanel.setBounds(0, tHeight, correctedWidth, mHeight - adviceHeight);
/* 233:    */       } else {
/* 234:248 */         TitledJPanel.this.mainPanel.setBounds(0, tHeight, 0, 0);
/* 235:    */       }
/* 236:254 */       TitledJPanel.this.getAdvicePanel().setBounds(0, correctedHeight - adviceHeight, correctedWidth, adviceHeight);
/* 237:    */     }
/* 238:    */   }
/* 239:    */   
/* 240:    */   protected boolean isShowTitleBar()
/* 241:    */   {
/* 242:260 */     return this.showTitleBar;
/* 243:    */   }
/* 244:    */   
/* 245:    */   protected void setShowTitleBar(boolean showTitleBar)
/* 246:    */   {
/* 247:267 */     this.showTitleBar = showTitleBar;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public boolean isShowMainPanel()
/* 251:    */   {
/* 252:271 */     return this.showMainPanel;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setShowMainPanel(boolean showMainPanel)
/* 256:    */   {
/* 257:278 */     this.showMainPanel = showMainPanel;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public TitlePanel getTitleLabel()
/* 261:    */   {
/* 262:282 */     return this.titleLabel;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setTitleState(int state)
/* 266:    */   {
/* 267:286 */     this.titleState = state;
/* 268:287 */     if (state != 0) {
/* 269:290 */       if (state == 1) {}
/* 270:    */     }
/* 271:    */   }
/* 272:    */   
/* 273:    */   public class TitlePanel
/* 274:    */     extends JLabel
/* 275:    */   {
/* 276:    */     public TitlePanel() {}
/* 277:    */     
/* 278:    */     public void paintComponent(Graphics graphics)
/* 279:    */     {
/* 280:300 */       int width = getWidth();
/* 281:301 */       int height = getHeight();
/* 282:302 */       Graphics2D g = (Graphics2D)graphics;
/* 283:303 */       GradientPaint paint = new GradientPaint(0.0F, 0.0F, TitledJPanel.topColor, 0.0F, height, TitledJPanel.bottomColor);
/* 284:304 */       g.setPaint(paint);
/* 285:305 */       Rectangle2D rectangle = new Rectangle2D.Float(0.0F, 0.0F, width, height);
/* 286:306 */       g.fill(rectangle);
/* 287:    */       
/* 288:    */ 
/* 289:    */ 
/* 290:310 */       g.setColor(Color.BLACK);
/* 291:311 */       Icon icon = getIcon();
/* 292:312 */       int xOffset = 10;
/* 293:313 */       if (icon != null)
/* 294:    */       {
/* 295:314 */         int wIcon = icon.getIconWidth();
/* 296:315 */         int hIcon = icon.getIconHeight();
/* 297:316 */         getIcon().paintIcon(this, g, xOffset, (height - hIcon) / 2);
/* 298:317 */         xOffset = (int)(xOffset + 1.5D * wIcon);
/* 299:    */       }
/* 300:    */     }
/* 301:    */   }
/* 302:    */   
/* 303:    */   public static void main(String[] ignore)
/* 304:    */   {
/* 305:324 */     JFrame frame = new JFrame();
/* 306:325 */     TitledJPanel panel = new TitledJPanel("Hello world");
/* 307:326 */     panel.setLayout(new GridLayout(1, 0));
/* 308:327 */     frame.getContentPane().add(panel);
/* 309:328 */     frame.setSize(800, 400);
/* 310:329 */     frame.setVisible(true);
/* 311:    */   }
/* 312:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     ati.TitledJPanel
 * JD-Core Version:    0.7.0.1
 */