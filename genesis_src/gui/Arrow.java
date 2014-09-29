/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import connections.Connections;
/*   4:    */ import connections.Ports;
/*   5:    */ import connections.WiredBox;
/*   6:    */ import java.awt.Color;
/*   7:    */ import java.awt.Container;
/*   8:    */ import java.awt.Font;
/*   9:    */ import java.awt.FontMetrics;
/*  10:    */ import java.awt.Graphics;
/*  11:    */ import javax.swing.JComponent;
/*  12:    */ import javax.swing.JFrame;
/*  13:    */ 
/*  14:    */ public class Arrow
/*  15:    */   extends JComponent
/*  16:    */   implements WiredBox
/*  17:    */ {
/*  18: 12 */   int taps = 0;
/*  19:    */   private String text;
/*  20:    */   
/*  21:    */   public Arrow()
/*  22:    */   {
/*  23: 15 */     Connections.getPorts(this).addSignalProcessor("setInput");
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void paintComponent(Graphics g)
/*  27:    */   {
/*  28: 20 */     super.paintComponent(g);
/*  29: 21 */     int w = getWidth();
/*  30: 22 */     int h = getHeight();
/*  31: 23 */     if ((w == 0) || (h == 0)) {
/*  32: 24 */       return;
/*  33:    */     }
/*  34: 26 */     int[] x = new int[7];
/*  35: 27 */     int[] y = new int[7];
/*  36: 28 */     x[0] = 0;
/*  37: 29 */     y[0] = (h * 3 / 12);
/*  38: 30 */     x[1] = (19 * w / 20);
/*  39: 31 */     y[1] = y[0];
/*  40: 32 */     x[2] = x[1];
/*  41: 33 */     y[2] = 5;
/*  42: 34 */     x[3] = w;
/*  43: 35 */     y[3] = (h / 2);
/*  44: 36 */     x[4] = x[1];
/*  45: 37 */     y[4] = (h - 5);
/*  46: 38 */     x[5] = x[1];
/*  47: 39 */     y[5] = (9 * h / 12);
/*  48: 40 */     x[6] = x[0];
/*  49: 41 */     y[6] = y[5];
/*  50: 42 */     g.setColor(Color.RED);
/*  51: 43 */     g.fillPolygon(x, y, x.length);
/*  52: 44 */     int tapWidth = y[5] - y[0];
/*  53: 45 */     int tapHalfWidth = tapWidth / 2;
/*  54: 46 */     if (this.taps != 0)
/*  55:    */     {
/*  56: 47 */       int sectionHalfWidth = w / this.taps / 2;
/*  57: 48 */       for (int i = 0; i < this.taps; i++)
/*  58:    */       {
/*  59: 49 */         int tapOrigin = i * w / this.taps;
/*  60: 50 */         int tapOffset = tapOrigin - tapHalfWidth + sectionHalfWidth;
/*  61: 51 */         g.fillRect(tapOffset, 0, tapWidth, y[0]);
/*  62:    */       }
/*  63:    */     }
/*  64: 54 */     if (this.text != null)
/*  65:    */     {
/*  66: 55 */       g.setFont(new Font("Georgia", 1, Math.max(14, h / 5)));
/*  67: 56 */       g.setColor(Color.WHITE);
/*  68: 57 */       FontMetrics metrics = g.getFontMetrics();
/*  69: 58 */       int stringWidth = metrics.stringWidth(this.text);
/*  70: 59 */       int stringHeight = metrics.getHeight();
/*  71:    */       
/*  72: 61 */       g.drawString(this.text, 10, (h + stringHeight) / 2);
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static void main(String[] args)
/*  77:    */   {
/*  78: 77 */     Arrow arrow = new Arrow();
/*  79:    */     
/*  80: 79 */     JFrame frame = new JFrame("Testing");
/*  81: 80 */     frame.getContentPane().add(arrow, "Center");
/*  82: 81 */     frame.setBounds(100, 100, 800, 200);
/*  83: 82 */     frame.setDefaultCloseOperation(3);
/*  84: 83 */     arrow.setText("Hello world");
/*  85: 84 */     frame.setVisible(true);
/*  86:    */   }
/*  87:    */   
/*  88:    */   private int getTaps()
/*  89:    */   {
/*  90: 87 */     return this.taps;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setTaps(int taps)
/*  94:    */   {
/*  95: 90 */     this.taps = taps;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String getText()
/*  99:    */   {
/* 100: 92 */     return this.text;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setText(String text)
/* 104:    */   {
/* 105: 95 */     this.text = text;
/* 106: 96 */     repaint();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setInput(Object input)
/* 110:    */   {
/* 111: 99 */     if ((input instanceof String)) {
/* 112:100 */       setText((String)input);
/* 113:    */     }
/* 114:    */   }
/* 115:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.Arrow
 * JD-Core Version:    0.7.0.1
 */