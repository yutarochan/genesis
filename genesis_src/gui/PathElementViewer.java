/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Function;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import connections.Ports;
/*   7:    */ import frames.PathElementFrame;
/*   8:    */ import java.awt.Color;
/*   9:    */ import java.awt.Container;
/*  10:    */ import java.awt.FontMetrics;
/*  11:    */ import java.awt.Graphics;
/*  12:    */ import java.awt.Graphics2D;
/*  13:    */ import java.awt.geom.AffineTransform;
/*  14:    */ import java.io.PrintStream;
/*  15:    */ import javax.swing.BorderFactory;
/*  16:    */ import javax.swing.JFrame;
/*  17:    */ 
/*  18:    */ public class PathElementViewer
/*  19:    */   extends NegatableJPanel
/*  20:    */ {
/*  21:    */   String role;
/*  22:    */   String name;
/*  23:    */   String reference;
/*  24:    */   private Ports ports;
/*  25:    */   
/*  26:    */   public PathElementViewer()
/*  27:    */   {
/*  28: 28 */     setBorder(BorderFactory.createLineBorder(Color.BLACK));
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void paint(Graphics graphics)
/*  32:    */   {
/*  33: 33 */     Graphics2D g = (Graphics2D)graphics;
/*  34: 34 */     int height = getHeight();
/*  35: 35 */     int width = getWidth();
/*  36: 36 */     int thickness = 10;
/*  37: 37 */     int length = 100;
/*  38: 38 */     int headLength = 10;
/*  39: 39 */     int headDelta = 5;
/*  40: 40 */     int square = 6 * width / 10;
/*  41: 41 */     int diameter = 4 * thickness / 2;
/*  42: 42 */     int radius = diameter / 2;
/*  43: 43 */     Color squareColor = new Color(150, 150, 150);
/*  44: 44 */     Color shadowColor = Color.LIGHT_GRAY;
/*  45: 45 */     Color ballColor = Color.BLUE;
/*  46: 46 */     int yCenter = 0;
/*  47: 47 */     int xCenter = 0;
/*  48: 48 */     if ((width == 0) || (height == 0)) {
/*  49: 49 */       return;
/*  50:    */     }
/*  51: 51 */     g.drawRect(0, 0, width - 1, height - 1);
/*  52: 52 */     if (this.role == null) {
/*  53: 53 */       return;
/*  54:    */     }
/*  55: 56 */     FontMetrics fm = g.getFontMetrics();
/*  56: 57 */     g.drawString(this.reference, 10, height - 5 - fm.getDescent());
/*  57:    */     
/*  58: 59 */     g.setColor(shadowColor);
/*  59:    */     
/*  60:    */ 
/*  61: 62 */     int xOffset = (width - square) / 2;
/*  62: 63 */     int yOffset = height / 2;
/*  63: 64 */     int[] x = { 0, length - headLength, length - headLength, length, length - headLength, length - headLength };
/*  64: 65 */     int[] y = { 0, 0, -headDelta, thickness / 2, thickness + headDelta, thickness, thickness };
/*  65: 66 */     double multiplier = square / length;
/*  66: 67 */     int tOffset = (int)((height - thickness * multiplier) / 2.0D);
/*  67: 68 */     AffineTransform transform = g.getTransform();
/*  68: 69 */     transform.translate(xOffset, tOffset);
/*  69: 70 */     transform.scale(multiplier, multiplier);
/*  70: 71 */     g.setTransform(transform);
/*  71:    */     
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80: 81 */     g.setColor(squareColor);
/*  81: 82 */     g.fillPolygon(x, y, 7);
/*  82: 83 */     g.setColor(ballColor);
/*  83: 86 */     if ((this.role.equalsIgnoreCase("over")) || (this.role.equalsIgnoreCase("above")))
/*  84:    */     {
/*  85: 87 */       yCenter = -2 * diameter;
/*  86: 88 */       xCenter = (int)(-radius + 0.5D * length);
/*  87: 89 */       g.fillOval(xCenter, yCenter, diameter, diameter);
/*  88:    */     }
/*  89: 91 */     else if ((this.role.equalsIgnoreCase("under")) || (this.role.equalsIgnoreCase("below")))
/*  90:    */     {
/*  91: 92 */       yCenter = 2 * diameter;
/*  92: 93 */       xCenter = (int)(-radius + 0.5D * length);
/*  93: 94 */       g.fillOval(xCenter, yCenter, diameter, diameter);
/*  94:    */     }
/*  95: 96 */     else if (this.role.equalsIgnoreCase("toward"))
/*  96:    */     {
/*  97: 98 */       yCenter = thickness / 2 - radius;
/*  98: 99 */       xCenter = length + diameter;
/*  99:100 */       g.fillOval(xCenter, yCenter, diameter, diameter);
/* 100:    */     }
/* 101:102 */     else if ((this.role.equalsIgnoreCase("to")) || (this.role.equalsIgnoreCase("in")))
/* 102:    */     {
/* 103:104 */       yCenter = thickness / 2 - radius;
/* 104:105 */       xCenter = length;
/* 105:106 */       g.fillOval(xCenter, yCenter, diameter, diameter);
/* 106:    */     }
/* 107:108 */     else if (this.role.equalsIgnoreCase("from"))
/* 108:    */     {
/* 109:110 */       yCenter = thickness / 2 - radius;
/* 110:111 */       xCenter = -diameter;
/* 111:112 */       g.fillOval(xCenter, yCenter, diameter, diameter);
/* 112:    */     }
/* 113:114 */     else if (this.role.equalsIgnoreCase("awayFrom"))
/* 114:    */     {
/* 115:116 */       yCenter = thickness / 2 - radius;
/* 116:117 */       xCenter = -2 * diameter;
/* 117:118 */       g.fillOval(xCenter, yCenter, diameter, diameter);
/* 118:    */     }
/* 119:    */     else
/* 120:    */     {
/* 121:122 */       yCenter = thickness / 2 - radius;
/* 122:123 */       xCenter = (int)(-radius + 0.5D * length);
/* 123:124 */       g.fillOval(xCenter, yCenter, diameter, diameter);
/* 124:    */     }
/* 125:126 */     if (this.role.equalsIgnoreCase("behind"))
/* 126:    */     {
/* 127:127 */       g.setColor(squareColor);
/* 128:128 */       g.fillPolygon(x, y, 7);
/* 129:129 */       g.setColor(ballColor);
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   private String findThing(Entity t)
/* 134:    */   {
/* 135:134 */     if ((t instanceof Function)) {
/* 136:135 */       return findThing(t.getSubject());
/* 137:    */     }
/* 138:137 */     if ((t instanceof Sequence)) {
/* 139:138 */       return "?";
/* 140:    */     }
/* 141:141 */     return t.getType();
/* 142:    */   }
/* 143:    */   
/* 144:    */   private void setParameters(String preposition, String reference)
/* 145:    */   {
/* 146:146 */     this.role = preposition;
/* 147:147 */     this.reference = reference;
/* 148:148 */     repaint();
/* 149:    */   }
/* 150:    */   
/* 151:    */   private void clearData()
/* 152:    */   {
/* 153:152 */     this.role = null;
/* 154:153 */     this.name = null;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public static void main(String[] args)
/* 158:    */   {
/* 159:160 */     PathElementViewer view = new PathElementViewer();
/* 160:161 */     Entity thing = new Entity();
/* 161:162 */     thing.addType("dog");
/* 162:163 */     PathElementFrame pathElement = new PathElementFrame("toward", thing);
/* 163:164 */     JFrame frame = new JFrame();
/* 164:165 */     frame.getContentPane().add(view);
/* 165:166 */     frame.setBounds(0, 0, 200, 200);
/* 166:167 */     frame.setVisible(true);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public Ports getPorts()
/* 170:    */   {
/* 171:171 */     if (this.ports == null) {
/* 172:172 */       this.ports = new Ports();
/* 173:    */     }
/* 174:174 */     return this.ports;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void view(Object signal)
/* 178:    */   {
/* 179:179 */     if (((signal instanceof Function)) || (signal == null))
/* 180:    */     {
/* 181:180 */       Function derivative = (Function)signal;
/* 182:181 */       String role = derivative.getType();
/* 183:182 */       setParameters(role, findThing(derivative));
/* 184:    */     }
/* 185:    */     else
/* 186:    */     {
/* 187:185 */       System.err.println(getClass().getName() + ": Didn't know what to do with input of type " + signal.getClass().toString() + ": " + 
/* 188:186 */         signal + " in PathElementViewer");
/* 189:    */     }
/* 190:188 */     setTruthValue(signal);
/* 191:    */   }
/* 192:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.PathElementViewer
 * JD-Core Version:    0.7.0.1
 */