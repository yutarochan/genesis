/*   1:    */ package utils;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Component;
/*   5:    */ import java.awt.Container;
/*   6:    */ import java.awt.Dimension;
/*   7:    */ import java.awt.Graphics;
/*   8:    */ import java.awt.Graphics2D;
/*   9:    */ import java.awt.LayoutManager;
/*  10:    */ import java.awt.event.MouseEvent;
/*  11:    */ import java.awt.event.MouseListener;
/*  12:    */ import java.awt.event.MouseMotionListener;
/*  13:    */ import java.awt.geom.AffineTransform;
/*  14:    */ import javax.swing.JButton;
/*  15:    */ import javax.swing.JFrame;
/*  16:    */ import javax.swing.JPanel;
/*  17:    */ 
/*  18:    */ public class ZoomPanel
/*  19:    */   extends JPanel
/*  20:    */   implements MouseListener, MouseMotionListener
/*  21:    */ {
/*  22:    */   Component component;
/*  23:    */   
/*  24:    */   public Component getComponent()
/*  25:    */   {
/*  26: 19 */     return this.component;
/*  27:    */   }
/*  28:    */   
/*  29: 22 */   double magnification = 1.0D;
/*  30: 24 */   double magStep = 1.5D;
/*  31: 26 */   double xOffset = 0.0D;
/*  32: 28 */   double yOffset = 0.0D;
/*  33:    */   Dimension preferredDimension;
/*  34: 32 */   boolean drawCenterSpot = false;
/*  35: 34 */   boolean clicking = false;
/*  36:    */   double xAnchor;
/*  37:    */   double yAnchor;
/*  38:    */   double xOffsetReference;
/*  39:    */   double yOffsetReference;
/*  40:    */   
/*  41:    */   public ZoomPanel(Component component)
/*  42:    */   {
/*  43: 37 */     this.component = component;
/*  44: 38 */     add(this.component);
/*  45: 39 */     setLayout(new ZoomLayout(null));
/*  46: 40 */     addMouseListener(this);
/*  47: 41 */     addMouseMotionListener(this);
/*  48:    */     
/*  49:    */ 
/*  50: 44 */     repaint();
/*  51:    */     
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55: 49 */     setBackground(Color.YELLOW);
/*  56: 50 */     repaint();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Dimension getPreferredSize()
/*  60:    */   {
/*  61: 54 */     return this.component.getPreferredSize();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void paint(Graphics g)
/*  65:    */   {
/*  66: 59 */     Color handle = g.getColor();
/*  67: 60 */     g.setColor(Color.YELLOW);
/*  68: 61 */     g.fillRect(0, 0, getWidth(), getHeight());
/*  69: 62 */     Graphics2D g2 = (Graphics2D)g;
/*  70:    */     
/*  71: 64 */     AffineTransform t = g2.getTransform();
/*  72: 65 */     AffineTransform t2 = (AffineTransform)t.clone();
/*  73: 66 */     t2.translate(this.xOffset, this.yOffset);
/*  74: 67 */     t2.scale(this.magnification, this.magnification);
/*  75: 68 */     g2.setTransform(t2);
/*  76: 69 */     paintComponents(g2);
/*  77: 70 */     g2.setTransform(t);
/*  78: 71 */     if (this.drawCenterSpot)
/*  79:    */     {
/*  80: 72 */       int w = getWidth();
/*  81: 73 */       int h = getHeight();
/*  82: 74 */       int r = 5;
/*  83: 75 */       g.setColor(Color.red);
/*  84: 76 */       g.fillOval(w / 2 - r, h / 2 - r, 2 * r, 2 * r);
/*  85: 77 */       g.setColor(handle);
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   private class ZoomLayout
/*  90:    */     implements LayoutManager
/*  91:    */   {
/*  92: 84 */     int referenceWidth = 0;
/*  93: 86 */     int referenceHeight = 0;
/*  94:    */     
/*  95:    */     private ZoomLayout() {}
/*  96:    */     
/*  97:    */     public void layoutContainer(Container parent)
/*  98:    */     {
/*  99: 89 */       synchronized (parent.getTreeLock())
/* 100:    */       {
/* 101: 90 */         if (parent.getComponents().length == 0) {
/* 102: 91 */           return;
/* 103:    */         }
/* 104: 94 */         int width = ZoomPanel.this.getWidth();
/* 105: 95 */         int height = ZoomPanel.this.getHeight();
/* 106: 97 */         if ((ZoomPanel.this.getWidth() == 0) || (ZoomPanel.this.getHeight() == 0)) {
/* 107: 98 */           return;
/* 108:    */         }
/* 109:100 */         if (ZoomPanel.this.preferredDimension == null)
/* 110:    */         {
/* 111:102 */           Dimension p = ZoomPanel.this.component.getPreferredSize();
/* 112:104 */           if ((p.width != 0) && (p.height != 0))
/* 113:    */           {
/* 114:106 */             if (p.width * height > p.height * width) {
/* 115:107 */               ZoomPanel.this.preferredDimension = new Dimension(width, width * p.height / p.width);
/* 116:    */             } else {
/* 117:110 */               ZoomPanel.this.preferredDimension = new Dimension(height * p.width / p.height, height);
/* 118:    */             }
/* 119:    */           }
/* 120:    */           else {
/* 121:114 */             ZoomPanel.this.preferredDimension = new Dimension(50, 50);
/* 122:    */           }
/* 123:    */         }
/* 124:123 */         int cWidth = ZoomPanel.this.preferredDimension.width;
/* 125:124 */         int cHeight = ZoomPanel.this.preferredDimension.height;
/* 126:126 */         if ((ZoomPanel.this.component != null) && (
/* 127:127 */           (this.referenceWidth != width) || (this.referenceHeight != height)))
/* 128:    */         {
/* 129:128 */           this.referenceWidth = width;
/* 130:129 */           this.referenceHeight = height;
/* 131:130 */           Mark.say(new Object[] {"Boom" });
/* 132:    */           
/* 133:132 */           int xOffset = (width - cWidth) / 2;
/* 134:133 */           int yOffset = (height - cHeight) / 2;
/* 135:    */           
/* 136:135 */           Mark.say(new Object[] {"Rebounding" });
/* 137:    */           
/* 138:137 */           ZoomPanel.this.component.setBounds(xOffset, yOffset, cWidth, cHeight);
/* 139:    */         }
/* 140:    */       }
/* 141:    */     }
/* 142:    */     
/* 143:    */     public void removeLayoutComponent(Component component) {}
/* 144:    */     
/* 145:    */     public void addLayoutComponent(String arg0, Component component) {}
/* 146:    */     
/* 147:    */     public Dimension minimumLayoutSize(Container parent)
/* 148:    */     {
/* 149:153 */       if (parent.getComponents().length == 0) {
/* 150:154 */         return null;
/* 151:    */       }
/* 152:156 */       return parent.getComponent(0).getMinimumSize();
/* 153:    */     }
/* 154:    */     
/* 155:    */     public Dimension preferredLayoutSize(Container parent)
/* 156:    */     {
/* 157:160 */       if (parent.getComponents().length == 0) {
/* 158:161 */         return null;
/* 159:    */       }
/* 160:163 */       return parent.getComponent(0).getPreferredSize();
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   public static void main(String[] ignore)
/* 165:    */   {
/* 166:168 */     ZoomPanel z = new ZoomPanel(new JButton("Hello World"));
/* 167:169 */     JFrame frame = new JFrame();
/* 168:170 */     frame.getContentPane().add(z);
/* 169:171 */     frame.setBounds(0, 0, 400, 300);
/* 170:172 */     frame.setVisible(true);
/* 171:173 */     Mark.say(new Object[] {"x, y ", Integer.valueOf(z.getWidth()), Integer.valueOf(z.getHeight()) });
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void mouseClicked(MouseEvent e)
/* 175:    */   {
/* 176:182 */     this.clicking = true;
/* 177:183 */     int oldWidth = (int)(this.magnification * this.preferredDimension.width);
/* 178:184 */     int oldHeight = (int)(this.magnification * this.preferredDimension.height);
/* 179:    */     
/* 180:186 */     int xCenter = getWidth() / 2;
/* 181:187 */     int yCenter = getHeight() / 2;
/* 182:    */     
/* 183:189 */     double oldFractionLeftOfCenter = (xCenter - this.xOffset) / oldWidth;
/* 184:190 */     double oldFractionAboveCenter = (yCenter - this.yOffset) / oldHeight;
/* 185:192 */     if (e.getButton() == 1) {
/* 186:193 */       this.magnification *= this.magStep;
/* 187:    */     } else {
/* 188:196 */       this.magnification /= this.magStep;
/* 189:    */     }
/* 190:198 */     int newWidth = (int)(this.magnification * this.preferredDimension.width);
/* 191:199 */     int newHeight = (int)(this.magnification * this.preferredDimension.height);
/* 192:    */     
/* 193:201 */     this.xOffset = (xCenter - newWidth * oldFractionLeftOfCenter);
/* 194:202 */     this.yOffset = (yCenter - newHeight * oldFractionAboveCenter);
/* 195:203 */     revalidate();
/* 196:204 */     this.clicking = false;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void mousePressed(MouseEvent e)
/* 200:    */   {
/* 201:213 */     this.xAnchor = e.getX();
/* 202:214 */     this.yAnchor = e.getY();
/* 203:215 */     this.xOffsetReference = this.xOffset;
/* 204:216 */     this.yOffsetReference = this.yOffset;
/* 205:217 */     this.drawCenterSpot = true;
/* 206:218 */     repaint();
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void mouseReleased(MouseEvent e)
/* 210:    */   {
/* 211:223 */     this.drawCenterSpot = false;
/* 212:224 */     repaint();
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void mouseEntered(MouseEvent e) {}
/* 216:    */   
/* 217:    */   public void mouseExited(MouseEvent e) {}
/* 218:    */   
/* 219:    */   public void mouseDragged(MouseEvent e)
/* 220:    */   {
/* 221:241 */     double xNow = e.getX();
/* 222:242 */     double yNow = e.getY();
/* 223:243 */     double deltaX = xNow - this.xAnchor;
/* 224:244 */     double deltaY = yNow - this.yAnchor;
/* 225:245 */     this.xOffset = (this.xOffsetReference + deltaX);
/* 226:246 */     this.yOffset = (this.yOffsetReference + deltaY);
/* 227:    */     
/* 228:248 */     repaint();
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void mouseMoved(MouseEvent e) {}
/* 232:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     utils.ZoomPanel
 * JD-Core Version:    0.7.0.1
 */