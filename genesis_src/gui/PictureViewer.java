/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import connections.Connections;
/*   4:    */ import connections.Ports;
/*   5:    */ import images.ImageAnchor;
/*   6:    */ import java.awt.BorderLayout;
/*   7:    */ import java.awt.Color;
/*   8:    */ import java.awt.Container;
/*   9:    */ import java.awt.Dimension;
/*  10:    */ import java.awt.FontMetrics;
/*  11:    */ import java.awt.Graphics;
/*  12:    */ import java.awt.Graphics2D;
/*  13:    */ import java.awt.Image;
/*  14:    */ import java.awt.geom.AffineTransform;
/*  15:    */ import java.io.File;
/*  16:    */ import java.net.URL;
/*  17:    */ import javax.swing.ImageIcon;
/*  18:    */ import javax.swing.JFrame;
/*  19:    */ import javax.swing.SwingUtilities;
/*  20:    */ 
/*  21:    */ public class PictureViewer
/*  22:    */   extends NegatableJPanel
/*  23:    */ {
/*  24: 22 */   private Image image = null;
/*  25: 24 */   private int frameRate = 33;
/*  26:    */   private File source;
/*  27:    */   
/*  28:    */   public PictureViewer(String name)
/*  29:    */   {
/*  30: 29 */     this();
/*  31: 30 */     setImage(name);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public PictureViewer(File file)
/*  35:    */   {
/*  36: 34 */     this();
/*  37: 35 */     setImage(file.getPath());
/*  38: 36 */     setPreferredSize(new Dimension(150, 150));
/*  39: 37 */     this.source = file;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public PictureViewer()
/*  43:    */   {
/*  44: 42 */     setBackground(Color.WHITE);
/*  45: 43 */     setOpaque(true);
/*  46:    */     
/*  47:    */ 
/*  48: 46 */     Connections.getPorts(this).addSignalProcessor("view");
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void view(Object o)
/*  52:    */   {
/*  53: 50 */     if ((o instanceof String)) {
/*  54: 51 */       setImage((String)o);
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setImage(String name)
/*  59:    */   {
/*  60:    */     try
/*  61:    */     {
/*  62: 58 */       URL url = ImageAnchor.class.getResource(name);
/*  63:    */       
/*  64: 60 */       SwingUtilities.invokeLater(new Display(url));
/*  65:    */     }
/*  66:    */     catch (Exception e)
/*  67:    */     {
/*  68: 63 */       e.printStackTrace();
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   class Display
/*  73:    */     implements Runnable
/*  74:    */   {
/*  75:    */     URL name;
/*  76:    */     
/*  77:    */     public Display(URL name)
/*  78:    */     {
/*  79: 71 */       this.name = name;
/*  80:    */     }
/*  81:    */     
/*  82:    */     public void run()
/*  83:    */     {
/*  84: 75 */       ImageIcon icon = new ImageIcon(this.name);
/*  85: 76 */       PictureViewer.this.setImage(icon.getImage());
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setImage(Image image)
/*  90:    */   {
/*  91: 81 */     this.image = image;
/*  92: 82 */     repaint();
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setImage(ImageIcon image)
/*  96:    */   {
/*  97: 86 */     this.image = image.getImage();
/*  98: 87 */     repaint();
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void clear()
/* 102:    */   {
/* 103: 91 */     this.image = null;
/* 104: 92 */     repaint();
/* 105:    */   }
/* 106:    */   
/* 107: 95 */   int yOffset = 0;
/* 108:    */   
/* 109:    */   public int getYOffset()
/* 110:    */   {
/* 111: 98 */     return this.yOffset;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setYOffset(int offset)
/* 115:    */   {
/* 116:102 */     this.yOffset = offset;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public File getSource()
/* 120:    */   {
/* 121:106 */     return this.source;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void paint(Graphics graphics)
/* 125:    */   {
/* 126:110 */     super.paint(graphics);
/* 127:111 */     if (this.image == null) {
/* 128:112 */       return;
/* 129:    */     }
/* 130:115 */     Graphics2D g = (Graphics2D)graphics;
/* 131:116 */     int pWidth = getWidth();
/* 132:117 */     int pHeight = getHeight() - this.yOffset;
/* 133:118 */     int iWidth = this.image.getWidth(this);
/* 134:119 */     int iHeight = this.image.getHeight(this);
/* 135:120 */     int inset = Math.min(pWidth, pHeight) / 20;
/* 136:121 */     pWidth -= 2 * inset;
/* 137:122 */     pHeight -= 2 * inset + 10;
/* 138:    */     
/* 139:124 */     int xBorder = 0;int yBorder = 0;
/* 140:125 */     double scale = 1.0D;
/* 141:126 */     if (pHeight / pWidth < iHeight / iWidth)
/* 142:    */     {
/* 143:128 */       scale = pHeight / iHeight;
/* 144:129 */       xBorder = (int)(pWidth - scale * iWidth) / 2;
/* 145:    */     }
/* 146:    */     else
/* 147:    */     {
/* 148:133 */       scale = pWidth / iWidth;
/* 149:134 */       yBorder = (int)(pHeight - scale * iHeight) / 2;
/* 150:    */     }
/* 151:136 */     AffineTransform t = AffineTransform.getTranslateInstance(xBorder + inset, yBorder + inset + this.yOffset);
/* 152:137 */     t.scale(scale, scale);
/* 153:138 */     g.drawImage(this.image, t, this);
/* 154:139 */     g.drawRect(2, 2, getWidth() - 4, getHeight() - 4);
/* 155:140 */     if (this.source != null)
/* 156:    */     {
/* 157:141 */       FontMetrics fm = g.getFontMetrics();
/* 158:142 */       int width = fm.stringWidth(this.source.getName());
/* 159:143 */       g.drawString(this.source.getName(), (pWidth - width) / 2, getHeight() - 5);
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   public static void main(String[] args)
/* 164:    */   {
/* 165:148 */     ImagePanel view = new ImagePanel();
/* 166:149 */     JFrame frame = new JFrame();
/* 167:150 */     frame.getContentPane().setLayout(new BorderLayout());
/* 168:151 */     frame.getContentPane().add(view, "Center");
/* 169:152 */     frame.setBounds(0, 0, 400, 400);
/* 170:153 */     frame.setVisible(true);
/* 171:154 */     view.processInput("bellbell.jpg");
/* 172:    */   }
/* 173:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.PictureViewer
 * JD-Core Version:    0.7.0.1
 */