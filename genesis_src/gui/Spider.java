/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Container;
/*   5:    */ import java.awt.Graphics;
/*   6:    */ import java.awt.event.WindowAdapter;
/*   7:    */ import java.awt.event.WindowEvent;
/*   8:    */ import java.io.PrintStream;
/*   9:    */ import javax.swing.JFrame;
/*  10:    */ import javax.swing.JPanel;
/*  11:    */ 
/*  12:    */ public class Spider
/*  13:    */   extends JPanel
/*  14:    */ {
/*  15:  9 */   double[] data = new double[0];
/*  16: 11 */   int fillPercentage = 90;
/*  17: 13 */   int ballPercentage = 5;
/*  18: 14 */   Color ballColor = Color.BLACK;
/*  19: 15 */   Color areaColor = Color.YELLOW;
/*  20: 17 */   boolean connectDots = false;
/*  21: 18 */   boolean fillArea = false;
/*  22:    */   
/*  23:    */   public Spider()
/*  24:    */   {
/*  25: 21 */     setBackground(Color.WHITE);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setConnectDots(boolean b)
/*  29:    */   {
/*  30: 24 */     this.connectDots = b;repaint();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setFillArea(boolean b)
/*  34:    */   {
/*  35: 26 */     this.fillArea = b;repaint();
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setData(double[] points)
/*  39:    */   {
/*  40: 29 */     if ((this.data.length != 0) && (this.data.length != points.length)) {
/*  41: 30 */       System.err.println("setData in Spider got wrong number of data points");
/*  42:    */     }
/*  43: 32 */     this.data = points;
/*  44: 33 */     repaint();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setDataColor(Color c)
/*  48:    */   {
/*  49: 36 */     this.ballColor = c;repaint();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setAreaColor(Color c)
/*  53:    */   {
/*  54: 37 */     this.areaColor = c;repaint();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void paint(Graphics g)
/*  58:    */   {
/*  59: 40 */     super.paint(g);
/*  60: 41 */     if (this.data.length == 0) {
/*  61: 41 */       return;
/*  62:    */     }
/*  63: 42 */     int totalWidth = getWidth();
/*  64: 43 */     int totalHeight = getHeight();
/*  65: 44 */     if ((totalWidth == 0) || (totalHeight == 0)) {
/*  66: 44 */       return;
/*  67:    */     }
/*  68: 45 */     int width = this.fillPercentage * totalWidth / 100;
/*  69: 46 */     int height = this.fillPercentage * totalHeight / 100;
/*  70: 47 */     int radius = Math.min(width, height) / 2;
/*  71: 48 */     int xCenter = totalWidth / 2;
/*  72: 49 */     int yCenter = totalHeight / 2;
/*  73: 50 */     drawData(g, radius, xCenter, yCenter);
/*  74:    */   }
/*  75:    */   
/*  76:    */   private void drawData(Graphics g, int radius, int xCenter, int yCenter)
/*  77:    */   {
/*  78: 54 */     double angle = 6.283185307179586D / this.data.length;
/*  79: 55 */     int[] x = new int[this.data.length];
/*  80: 56 */     int[] y = new int[this.data.length];
/*  81: 57 */     int[] dataX = new int[this.data.length];
/*  82: 58 */     int[] dataY = new int[this.data.length];
/*  83: 59 */     int[] contourX = new int[this.data.length];
/*  84: 60 */     int[] contourY = new int[this.data.length];
/*  85: 61 */     int ballRadius = radius * this.ballPercentage / 100;
/*  86: 62 */     for (int i = 0; i < this.data.length; i++)
/*  87:    */     {
/*  88: 63 */       double theta = i * angle;
/*  89: 64 */       x[i] = ((int)(radius * Math.cos(theta)));
/*  90: 65 */       y[i] = ((int)(radius * Math.sin(theta)));
/*  91: 66 */       dataX[i] = ((int)(x[i] * this.data[i]));
/*  92: 67 */       dataY[i] = ((int)(y[i] * this.data[i]));
/*  93: 68 */       contourX[i] = (xCenter + dataX[i]);
/*  94: 69 */       contourY[i] = (yCenter + dataY[i]);
/*  95:    */     }
/*  96: 72 */     if (this.fillArea)
/*  97:    */     {
/*  98: 73 */       Color handle = g.getColor();
/*  99: 74 */       g.setColor(this.areaColor);
/* 100: 75 */       g.fillPolygon(contourX, contourY, this.data.length);
/* 101: 76 */       g.setColor(handle);
/* 102:    */     }
/* 103: 79 */     for (int i = 0; i < this.data.length; i++) {
/* 104: 80 */       g.drawLine(xCenter, yCenter, xCenter + x[i], yCenter + y[i]);
/* 105:    */     }
/* 106: 83 */     if ((this.connectDots) || (this.fillArea)) {
/* 107: 84 */       for (int i = 0; i < this.data.length; i++)
/* 108:    */       {
/* 109: 85 */         int nextI = (i + 1) % this.data.length;
/* 110: 86 */         g.drawLine(contourX[i], contourY[i], contourX[nextI], contourY[nextI]);
/* 111:    */       }
/* 112:    */     }
/* 113: 90 */     for (int i = 0; i < this.data.length; i++)
/* 114:    */     {
/* 115: 91 */       Color handle = g.getColor();
/* 116: 92 */       g.setColor(this.ballColor);
/* 117: 93 */       drawDataPoint(g, ballRadius, contourX[i], contourY[i]);
/* 118: 94 */       g.setColor(handle);
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   private void drawDataPoint(Graphics g, int radius, int x, int y)
/* 123:    */   {
/* 124: 99 */     g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public static void main(String[] ignore)
/* 128:    */   {
/* 129:103 */     Spider spider = new Spider();
/* 130:104 */     double[] constant1 = { 1.0D, 0.1D, 0.3D, 0.5D, 0.7D, 0.9D };
/* 131:105 */     double[] constant2 = { 1.0D, 0.2D, 0.4D, 0.6D, 0.8D, 1.0D };
/* 132:106 */     spider.setData(constant1);
/* 133:107 */     JFrame frame = new JFrame();
/* 134:108 */     frame.getContentPane().add(spider);
/* 135:109 */     frame.setBounds(100, 100, 500, 700);
/* 136:110 */     frame.addWindowListener(new WindowAdapter()
/* 137:    */     {
/* 138:    */       public void windowClosing(WindowEvent e)
/* 139:    */       {
/* 140:112 */         System.exit(0);
/* 141:    */       }
/* 142:114 */     });
/* 143:115 */     spider.setData(constant2);
/* 144:116 */     spider.setDataColor(Color.RED);
/* 145:117 */     spider.setAreaColor(Color.LIGHT_GRAY);
/* 146:118 */     frame.show();
/* 147:119 */     spider.setConnectDots(true);
/* 148:120 */     spider.setFillArea(true);
/* 149:    */   }
/* 150:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.Spider
 * JD-Core Version:    0.7.0.1
 */