/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import connections.Connections;
/*   4:    */ import connections.QueuingWiredBox;
/*   5:    */ import images.ImageAnchor;
/*   6:    */ import java.awt.BorderLayout;
/*   7:    */ import java.awt.Color;
/*   8:    */ import java.awt.Container;
/*   9:    */ import java.awt.Cursor;
/*  10:    */ import java.awt.Graphics;
/*  11:    */ import java.awt.image.BufferedImage;
/*  12:    */ import java.awt.image.RenderedImage;
/*  13:    */ import java.io.File;
/*  14:    */ import java.io.PrintStream;
/*  15:    */ import java.net.URL;
/*  16:    */ import javax.swing.BorderFactory;
/*  17:    */ import javax.swing.ImageIcon;
/*  18:    */ import javax.swing.JFrame;
/*  19:    */ import javax.swing.JPanel;
/*  20:    */ 
/*  21:    */ public class BasicPicturePanel
/*  22:    */   extends JPanel
/*  23:    */ {
/*  24: 23 */   private BufferedImage image = null;
/*  25: 25 */   private int frameRate = 33;
/*  26:    */   private File source;
/*  27: 29 */   int xOffset = 0;
/*  28: 29 */   int yOffset = 0;
/*  29: 31 */   double scale = 1.0D;
/*  30:    */   int circleX;
/*  31:    */   int circleY;
/*  32:    */   int circleW;
/*  33: 35 */   boolean drawCircle = false;
/*  34:    */   
/*  35:    */   public BasicPicturePanel()
/*  36:    */   {
/*  37: 38 */     setBackground(Color.WHITE);
/*  38: 39 */     setOpaque(true);
/*  39:    */     
/*  40: 41 */     setBorder(BorderFactory.createLineBorder(Color.BLUE));
/*  41:    */     
/*  42: 43 */     setCursor(new Cursor(1));
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void getInput(Object o)
/*  46:    */   {
/*  47: 47 */     if ((o instanceof String))
/*  48:    */     {
/*  49: 48 */       setImage((String)o);
/*  50:    */     }
/*  51: 50 */     else if ((o instanceof File))
/*  52:    */     {
/*  53: 51 */       File file = (File)o;
/*  54: 52 */       this.source = file;
/*  55: 53 */       setImage(file.getName());
/*  56:    */     }
/*  57: 56 */     else if ((o instanceof BufferedImage))
/*  58:    */     {
/*  59: 57 */       setImage((BufferedImage)o);
/*  60:    */     }
/*  61: 59 */     else if (o != null)
/*  62:    */     {
/*  63: 62 */       System.err.println("ImagePanel.setImage got a " + o.getClass());
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setImage(String name)
/*  68:    */   {
/*  69: 67 */     setImage(getBufferedImageFromIconFileName(name));
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static BufferedImage getBufferedImageFromIconFileName(String name)
/*  73:    */   {
/*  74: 74 */     JPanel p = new JPanel();
/*  75: 75 */     URL url = ImageAnchor.class.getResource(name);
/*  76: 76 */     ImageIcon icon = new ImageIcon(url);
/*  77: 77 */     BufferedImage b = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), 1);
/*  78: 78 */     icon.paintIcon(p, b.createGraphics(), 0, 0);
/*  79: 79 */     return b;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setImage(BufferedImage image)
/*  83:    */   {
/*  84: 83 */     this.image = image;
/*  85: 84 */     repaint();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void clear()
/*  89:    */   {
/*  90: 88 */     this.image = null;
/*  91: 89 */     repaint();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void paint(Graphics graphics)
/*  95:    */   {
/*  96: 93 */     super.paint(graphics);
/*  97: 94 */     if (this.image == null) {
/*  98: 95 */       return;
/*  99:    */     }
/* 100: 98 */     paintImage(graphics, this.image);
/* 101:    */   }
/* 102:    */   
/* 103:    */   private void paintImage(Graphics g, RenderedImage anImage)
/* 104:    */   {
/* 105:103 */     int paneWidth = getWidth();
/* 106:104 */     int paneHeight = getHeight();
/* 107:105 */     int imageWidth = anImage.getWidth();
/* 108:106 */     int imageHeight = anImage.getHeight();
/* 109:107 */     int inset = 15;
/* 110:108 */     int effectiveWidth = paneWidth - 2 * inset;
/* 111:109 */     int effectiveHeight = paneHeight - 2 * inset;
/* 112:111 */     if (effectiveHeight / effectiveWidth < imageHeight / imageWidth) {
/* 113:113 */       this.scale = (effectiveHeight / imageHeight);
/* 114:    */     } else {
/* 115:117 */       this.scale = (effectiveWidth / imageWidth);
/* 116:    */     }
/* 117:119 */     this.xOffset = ((int)((paneWidth - this.scale * imageWidth) / 2.0D));
/* 118:120 */     this.yOffset = ((int)((paneHeight - this.scale * imageHeight) / 2.0D));
/* 119:    */     
/* 120:    */ 
/* 121:123 */     int width = (int)(this.scale * imageWidth);
/* 122:124 */     int height = (int)(this.scale * imageHeight);
/* 123:    */     
/* 124:    */ 
/* 125:    */ 
/* 126:128 */     g.drawImage(this.image, this.xOffset, this.yOffset, width, height, this);
/* 127:129 */     drawTheCircle(g);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public static void main(String[] args)
/* 131:    */   {
/* 132:133 */     ImagePanel view = new ImagePanel();
/* 133:    */     
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:    */ 
/* 145:    */ 
/* 146:    */ 
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:    */ 
/* 151:    */ 
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:    */ 
/* 156:    */ 
/* 157:158 */     JFrame frame = new JFrame();
/* 158:159 */     frame.getContentPane().setLayout(new BorderLayout());
/* 159:160 */     frame.getContentPane().add(view, "Center");
/* 160:161 */     frame.setBounds(0, 0, 400, 400);
/* 161:162 */     frame.setVisible(true);
/* 162:163 */     QueuingWiredBox box = new QueuingWiredBox();
/* 163:164 */     Connections.wire(box, view);
/* 164:165 */     box.process("dog.jpg");
/* 165:166 */     box.process("tree.jpg");
/* 166:    */   }
/* 167:    */   
/* 168:    */   public double getScale()
/* 169:    */   {
/* 170:170 */     return this.scale;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public int getXOffset()
/* 174:    */   {
/* 175:174 */     return this.xOffset;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public int getYOffset()
/* 179:    */   {
/* 180:178 */     return this.yOffset;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public BufferedImage getImage()
/* 184:    */   {
/* 185:182 */     return this.image;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void drawACircle(int x, int y, int w)
/* 189:    */   {
/* 190:186 */     w = (int)(w * (this.scale / 2.0D));
/* 191:187 */     this.circleX = (x - w);
/* 192:188 */     this.circleY = (y - w);
/* 193:189 */     this.circleW = (2 * w);
/* 194:190 */     this.drawCircle = true;
/* 195:191 */     repaint();
/* 196:    */   }
/* 197:    */   
/* 198:    */   private void drawTheCircle(Graphics g)
/* 199:    */   {
/* 200:195 */     if (this.drawCircle) {
/* 201:196 */       g.drawOval(this.circleX, this.circleY, this.circleW, this.circleW);
/* 202:    */     }
/* 203:    */   }
/* 204:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.BasicPicturePanel
 * JD-Core Version:    0.7.0.1
 */