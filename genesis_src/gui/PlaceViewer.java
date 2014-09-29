/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Function;
/*   5:    */ import frames.PlaceFrame;
/*   6:    */ import java.awt.BasicStroke;
/*   7:    */ import java.awt.Color;
/*   8:    */ import java.awt.Container;
/*   9:    */ import java.awt.FontMetrics;
/*  10:    */ import java.awt.Graphics;
/*  11:    */ import java.awt.Graphics2D;
/*  12:    */ import java.awt.Stroke;
/*  13:    */ import java.io.PrintStream;
/*  14:    */ import java.util.List;
/*  15:    */ import javax.swing.JFrame;
/*  16:    */ import translator.RuleSet;
/*  17:    */ 
/*  18:    */ public class PlaceViewer
/*  19:    */   extends NegatableJPanel
/*  20:    */ {
/*  21:    */   String role;
/*  22:    */   String name;
/*  23:    */   
/*  24:    */   public void paint(Graphics graphics)
/*  25:    */   {
/*  26: 29 */     Graphics2D g = (Graphics2D)graphics;
/*  27: 30 */     int height = getHeight();
/*  28: 31 */     int width = getWidth();
/*  29: 32 */     int square = 5 * Math.min(height, width) / 10;
/*  30: 33 */     int radius = 5 * Math.min(height, width) / 80;
/*  31: 34 */     int diameter = radius * 2;
/*  32: 35 */     Color squareColor = new Color(150, 150, 150);
/*  33: 36 */     Color shadowColor = Color.LIGHT_GRAY;
/*  34: 37 */     Color ballColor = Color.BLUE;
/*  35: 38 */     int yCenter = 0;
/*  36: 39 */     int xCenter = 0;
/*  37: 40 */     if ((width == 0) || (height == 0)) {
/*  38: 41 */       return;
/*  39:    */     }
/*  40: 43 */     g.drawRect(0, 0, width - 1, height - 1);
/*  41: 44 */     if (this.role == null) {
/*  42: 45 */       return;
/*  43:    */     }
/*  44: 50 */     FontMetrics fm = g.getFontMetrics();
/*  45: 51 */     g.drawString(this.name, 10, height - 5 - fm.getDescent());
/*  46: 52 */     int xOffset = (width - square) / 2;
/*  47: 53 */     int yOffset = (height - square) / 2;
/*  48: 54 */     int alternativeYOffset = height - square - xOffset;
/*  49:    */     
/*  50:    */ 
/*  51: 57 */     g.setColor(shadowColor);
/*  52: 58 */     int[] x = new int[3];
/*  53: 59 */     int[] y = new int[3]; int 
/*  54: 60 */       tmp193_192 = (yOffset + square / 2);y[1] = tmp193_192;y[0] = tmp193_192;
/*  55: 61 */     y[2] = (yOffset + square); int 
/*  56: 62 */       tmp216_215 = (xOffset + square);x[2] = tmp216_215;x[0] = tmp216_215;
/*  57: 63 */     x[1] = (x[0] + square / 3);
/*  58: 64 */     g.fillPolygon(x, y, 3);
/*  59: 66 */     if (this.role.equalsIgnoreCase("back"))
/*  60:    */     {
/*  61: 67 */       g.setColor(ballColor);
/*  62:    */       
/*  63: 69 */       yCenter = yOffset + -diameter + 2 * square / 3;
/*  64: 70 */       xCenter = xOffset + square - radius;
/*  65: 71 */       g.fillOval(xCenter, yCenter, diameter, diameter);
/*  66:    */     }
/*  67: 74 */     g.setColor(squareColor);
/*  68: 75 */     g.fillRect(xOffset, yOffset, square, square);
/*  69: 76 */     g.setColor(ballColor);
/*  70: 78 */     if ((this.role.equalsIgnoreCase("at")) || (this.role.equalsIgnoreCase("bottom")) || (this.role.equalsIgnoreCase("front")))
/*  71:    */     {
/*  72: 80 */       yCenter = yOffset + square - diameter + radius;
/*  73: 81 */       xCenter = xOffset + square / 2 - radius;
/*  74: 82 */       g.fillOval(xCenter, yCenter, diameter, diameter);
/*  75:    */     }
/*  76: 84 */     else if (this.role.equalsIgnoreCase("top"))
/*  77:    */     {
/*  78: 86 */       yCenter = yOffset - diameter;
/*  79: 87 */       xCenter = xOffset + square / 2 - radius;
/*  80: 88 */       g.fillOval(xCenter, yCenter, diameter, diameter);
/*  81:    */     }
/*  82:102 */     else if (this.role.equalsIgnoreCase("left"))
/*  83:    */     {
/*  84:104 */       yCenter = yOffset + square - diameter + radius;
/*  85:105 */       xCenter = xOffset - diameter - radius;
/*  86:106 */       g.fillOval(xCenter, yCenter, diameter, diameter);
/*  87:    */     }
/*  88:108 */     else if ((this.role.equalsIgnoreCase("right")) || (this.role.equalsIgnoreCase("side")))
/*  89:    */     {
/*  90:110 */       yCenter = yOffset + square - diameter + radius;
/*  91:111 */       xCenter = xOffset + square + radius;
/*  92:112 */       g.fillOval(xCenter, yCenter, diameter, diameter);
/*  93:    */     }
/*  94:114 */     else if (this.role.equalsIgnoreCase("inside"))
/*  95:    */     {
/*  96:116 */       yCenter = yOffset + square - diameter - radius;
/*  97:117 */       xCenter = xOffset + square / 2 - radius;
/*  98:118 */       Stroke drawingStroke = new BasicStroke(2.0F, 0, 2, 0.0F, new float[] { 4.0F }, 0.0F);
/*  99:119 */       g.setStroke(drawingStroke);
/* 100:120 */       g.drawOval(xCenter, yCenter, diameter, diameter);
/* 101:    */     }
/* 102:130 */     else if (!this.role.equalsIgnoreCase("back"))
/* 103:    */     {
/* 104:134 */       yCenter = yOffset - 2 * diameter;
/* 105:135 */       xCenter = xOffset + square / 2 - radius;
/* 106:136 */       g.drawString("?", xCenter, yCenter);
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   private void setParameters(String preposition, String name)
/* 111:    */   {
/* 112:142 */     this.role = preposition;
/* 113:143 */     this.name = name;
/* 114:    */     
/* 115:145 */     repaint();
/* 116:    */   }
/* 117:    */   
/* 118:    */   private void clearData()
/* 119:    */   {
/* 120:149 */     this.role = null;
/* 121:150 */     this.name = null;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public static void main(String[] args)
/* 125:    */   {
/* 126:158 */     PlaceViewer view = new PlaceViewer();
/* 127:159 */     Entity thing = new Entity();
/* 128:160 */     thing.addType("dog");
/* 129:161 */     PlaceFrame place = new PlaceFrame("behind", thing);
/* 130:162 */     JFrame frame = new JFrame();
/* 131:163 */     frame.getContentPane().add(view);
/* 132:164 */     frame.setBounds(0, 0, 200, 200);
/* 133:165 */     frame.setVisible(true);
/* 134:166 */     view.view(place.getPlace());
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void view(Object signal)
/* 138:    */   {
/* 139:170 */     if ((signal instanceof Function))
/* 140:    */     {
/* 141:171 */       Function place = (Function)signal;
/* 142:172 */       String placeName = place.getType();
/* 143:174 */       if (RuleSet.placePrepositions.contains(placeName))
/* 144:    */       {
/* 145:175 */         Entity reference = place.getSubject();
/* 146:176 */         String referenceName = reference.getType();
/* 147:177 */         if ("at".equalsIgnoreCase(placeName)) {
/* 148:178 */           referenceName = placeName;
/* 149:    */         } else {
/* 150:181 */           referenceName = placeName;
/* 151:    */         }
/* 152:183 */         setParameters(placeName, referenceName);
/* 153:    */       }
/* 154:    */     }
/* 155:    */     else
/* 156:    */     {
/* 157:187 */       System.err.println(getClass().getName() + ": Didn't know what to do with input of type " + signal.getClass().toString() + ": " + 
/* 158:188 */         signal + " in the place viewer");
/* 159:    */     }
/* 160:    */   }
/* 161:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.PlaceViewer
 * JD-Core Version:    0.7.0.1
 */