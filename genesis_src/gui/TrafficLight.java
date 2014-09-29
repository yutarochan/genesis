/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import gui.images.GuiImagesAnchor;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.awt.Container;
/*   6:    */ import java.awt.Dimension;
/*   7:    */ import java.awt.Graphics;
/*   8:    */ import java.awt.GridLayout;
/*   9:    */ import java.awt.image.BufferedImage;
/*  10:    */ import java.io.File;
/*  11:    */ import java.io.IOException;
/*  12:    */ import javax.imageio.ImageIO;
/*  13:    */ import javax.swing.JComponent;
/*  14:    */ import javax.swing.JFrame;
/*  15:    */ import javax.swing.JPanel;
/*  16:    */ import utils.Mark;
/*  17:    */ 
/*  18:    */ public class TrafficLight
/*  19:    */   extends JPanel
/*  20:    */ {
/*  21: 25 */   private int red = 1;
/*  22: 25 */   private int yellow = 2;
/*  23: 25 */   private int green = 3;
/*  24: 25 */   private int grey = 0;
/*  25: 30 */   private Light traficLight = new Light(this.grey);
/*  26: 32 */   private BufferedImage greyLight = null;
/*  27:    */   private BufferedImage redLight;
/*  28:    */   private BufferedImage yellowLight;
/*  29:    */   private BufferedImage greenLight;
/*  30:    */   
/*  31:    */   public TrafficLight()
/*  32:    */   {
/*  33:    */     try
/*  34:    */     {
/*  35: 35 */       this.greyLight = ImageIO.read(new File(new GuiImagesAnchor().get("stoplight-grey.png")));
/*  36:    */     }
/*  37:    */     catch (IOException noGrey)
/*  38:    */     {
/*  39: 38 */       Mark.say(new Object[] {"No grey traffic light" });
/*  40:    */     }
/*  41: 42 */     this.redLight = null;
/*  42:    */     try
/*  43:    */     {
/*  44: 45 */       this.redLight = ImageIO.read(new File(new GuiImagesAnchor().get("stoplight-red.png")));
/*  45:    */     }
/*  46:    */     catch (IOException localIOException1) {}
/*  47: 51 */     this.yellowLight = null;
/*  48:    */     try
/*  49:    */     {
/*  50: 54 */       this.yellowLight = ImageIO.read(new File(new GuiImagesAnchor().get("stoplight-yellow.png")));
/*  51:    */     }
/*  52:    */     catch (IOException localIOException2) {}
/*  53: 60 */     this.greenLight = null;
/*  54:    */     try
/*  55:    */     {
/*  56: 63 */       this.greenLight = ImageIO.read(new File(new GuiImagesAnchor().get("stoplight-green.png")));
/*  57:    */     }
/*  58:    */     catch (IOException localIOException3) {}
/*  59: 71 */     setLayout(new GridLayout(1, 1));
/*  60: 72 */     setMinimumSize(new Dimension(0, 0));
/*  61: 73 */     setPreferredSize(new Dimension(100, 200));
/*  62: 74 */     add(this.traficLight);
/*  63:    */   }
/*  64:    */   
/*  65:    */   class Light
/*  66:    */     extends JComponent
/*  67:    */   {
/*  68: 80 */     int colorState = TrafficLight.this.grey;
/*  69:    */     
/*  70:    */     private void setColor(int newColor)
/*  71:    */     {
/*  72: 83 */       this.colorState = newColor;
/*  73:    */     }
/*  74:    */     
/*  75:    */     public Light(int color)
/*  76:    */     {
/*  77: 87 */       this.colorState = color;
/*  78:    */     }
/*  79:    */     
/*  80:    */     public void paint(Graphics g)
/*  81:    */     {
/*  82: 91 */       int width = getWidth();
/*  83: 92 */       int height = getHeight();
/*  84: 93 */       if (this.colorState == TrafficLight.this.grey) {
/*  85: 94 */         g.drawImage(TrafficLight.this.greyLight, 0, 0, width, height, Color.BLUE, null);
/*  86:    */       }
/*  87: 96 */       if (this.colorState == TrafficLight.this.red) {
/*  88: 97 */         g.drawImage(TrafficLight.this.redLight, 0, 0, width, height, Color.BLUE, null);
/*  89:    */       }
/*  90: 99 */       if (this.colorState == TrafficLight.this.yellow) {
/*  91:100 */         g.drawImage(TrafficLight.this.yellowLight, 0, 0, width, height, Color.BLUE, null);
/*  92:    */       }
/*  93:102 */       if (this.colorState == TrafficLight.this.green) {
/*  94:103 */         g.drawImage(TrafficLight.this.greenLight, 0, 0, width, height, Color.BLUE, null);
/*  95:    */       }
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean isGreen()
/* 100:    */   {
/* 101:111 */     return this.traficLight.colorState == this.green;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setGreen(boolean b)
/* 105:    */   {
/* 106:116 */     if (b)
/* 107:    */     {
/* 108:117 */       this.traficLight.setColor(this.green);
/* 109:118 */       repaint();
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   public boolean isYellow()
/* 114:    */   {
/* 115:123 */     return this.traficLight.colorState == this.yellow;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setYellow(boolean b)
/* 119:    */   {
/* 120:127 */     if (b)
/* 121:    */     {
/* 122:128 */       this.traficLight.setColor(this.yellow);
/* 123:129 */       repaint();
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean isRed()
/* 128:    */   {
/* 129:134 */     return this.traficLight.colorState == this.red;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setRed(boolean b)
/* 133:    */   {
/* 134:138 */     if (b)
/* 135:    */     {
/* 136:139 */       this.traficLight.setColor(this.red);
/* 137:140 */       repaint();
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   public static void main(String[] ignore)
/* 142:    */   {
/* 143:145 */     TrafficLight stopLight = new TrafficLight();
/* 144:    */     
/* 145:    */ 
/* 146:    */ 
/* 147:149 */     JFrame frame = new JFrame();
/* 148:150 */     frame.getContentPane().add(stopLight);
/* 149:151 */     frame.pack();
/* 150:152 */     frame.setDefaultCloseOperation(3);
/* 151:153 */     frame.setVisible(true);
/* 152:    */   }
/* 153:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.TrafficLight
 * JD-Core Version:    0.7.0.1
 */