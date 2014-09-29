/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Relation;
/*   5:    */ import connections.Connections;
/*   6:    */ import connections.WiredJPanel;
/*   7:    */ import frames.GeometryFrame;
/*   8:    */ import java.awt.Color;
/*   9:    */ import java.awt.Container;
/*  10:    */ import java.awt.Graphics;
/*  11:    */ import java.awt.Graphics2D;
/*  12:    */ import java.awt.geom.RoundRectangle2D.Double;
/*  13:    */ import javax.swing.JFrame;
/*  14:    */ 
/*  15:    */ public class GeometryViewer
/*  16:    */   extends WiredJPanel
/*  17:    */ {
/*  18: 12 */   public static Color figureColor = Color.blue;
/*  19: 13 */   public static Color groundColor = Color.gray;
/*  20: 14 */   private boolean viewable = true;
/*  21: 15 */   private String figure = "";
/*  22: 16 */   private String ground = "";
/*  23: 17 */   boolean motional = false;
/*  24:    */   private Ports ports;
/*  25:    */   
/*  26:    */   public void setMotional()
/*  27:    */   {
/*  28: 19 */     this.motional = true;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setLocative()
/*  32:    */   {
/*  33: 22 */     this.motional = false;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setFigure(String geometry)
/*  37:    */   {
/*  38: 25 */     this.figure = geometry;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setGround(String geometry)
/*  42:    */   {
/*  43: 28 */     this.ground = geometry;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public GeometryViewer()
/*  47:    */   {
/*  48: 31 */     setOpaque(false);
/*  49: 32 */     Connections.getPorts(this).addSignalProcessor("setParameters");
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void paintComponent(Graphics x)
/*  53:    */   {
/*  54: 36 */     super.paintComponent(x);
/*  55: 37 */     Graphics2D g = (Graphics2D)x;
/*  56: 38 */     int width = getWidth();
/*  57: 39 */     int height = getHeight();
/*  58: 40 */     int figureX = width / 2;
/*  59: 41 */     int figureY = height / 2 - width / 24;
/*  60: 42 */     int groundX = figureX + width / 50;
/*  61: 43 */     int groundY = figureY + width / 50;
/*  62: 44 */     if ((width == 0) || (height == 0)) {
/*  63: 45 */       return;
/*  64:    */     }
/*  65: 47 */     g.drawRect(0, 0, width - 1, height - 1);
/*  66: 48 */     if (!isViewable()) {
/*  67: 49 */       return;
/*  68:    */     }
/*  69: 51 */     g.setColor(groundColor);
/*  70: 52 */     if (this.ground.equals("point"))
/*  71:    */     {
/*  72: 53 */       drawDot(g, 5 * groundX, 5 * groundY);
/*  73:    */     }
/*  74: 54 */     else if (this.ground.equals("line"))
/*  75:    */     {
/*  76: 55 */       drawLine(g, groundX, groundY);
/*  77:    */     }
/*  78: 56 */     else if (this.ground.equals("plane"))
/*  79:    */     {
/*  80: 57 */       g.fillRect(groundX - width / 4, groundY - height / 4, width / 2, height / 2);
/*  81:    */     }
/*  82: 58 */     else if (this.ground.equals("point-pair"))
/*  83:    */     {
/*  84: 59 */       int deviation = width / 5;
/*  85: 60 */       drawDot(g, figureX, figureY + deviation);
/*  86: 61 */       drawDot(g, figureX, groundY - deviation);
/*  87:    */     }
/*  88: 62 */     else if (this.ground.equals("point-set"))
/*  89:    */     {
/*  90: 63 */       int deviation = width / 5;
/*  91: 64 */       drawDot(g, groundX + deviation, groundY + deviation);
/*  92: 65 */       drawDot(g, groundX - deviation, groundY - deviation);
/*  93: 66 */       drawDot(g, groundX - deviation, groundY + deviation);
/*  94: 67 */       drawDot(g, groundX + deviation, groundY - deviation);
/*  95:    */     }
/*  96: 68 */     else if (this.ground.equals("aggregate"))
/*  97:    */     {
/*  98: 69 */       int radius = getHeight() / 30;
/*  99: 70 */       for (int i = -40; i < 40; i++) {
/* 100: 71 */         g.fillOval(figureX + i * 643201334 / 49887259, figureY + i * 924374328 / 49471903, radius, radius);
/* 101:    */       }
/* 102:    */     }
/* 103: 73 */     else if (this.ground.equals("tube"))
/* 104:    */     {
/* 105: 74 */       g.fill(new RoundRectangle2D.Double(figureX - 3 * width / 10, figureY - height / 10, 3 * width / 5, 
/* 106: 75 */         height / 5, width / 10, height / 4));
/* 107:    */     }
/* 108: 76 */     else if (!this.ground.equals("cylinder"))
/* 109:    */     {
/* 110: 78 */       if (this.ground.equals("distributed"))
/* 111:    */       {
/* 112: 79 */         int radius = getHeight() / 50;
/* 113: 80 */         for (int i = -500; i < 500; i++) {
/* 114: 81 */           g.fillOval(figureX + i * 643201334 / 49887259, figureY + i * 924374328 / 49471903, radius, radius);
/* 115:    */         }
/* 116:    */       }
/* 117:    */     }
/* 118: 84 */     g.setColor(figureColor);
/* 119: 85 */     if (this.figure.equals("point"))
/* 120:    */     {
/* 121: 86 */       drawDot(g, figureX, figureY);
/* 122:    */     }
/* 123: 88 */     else if (this.figure.equals("line"))
/* 124:    */     {
/* 125: 89 */       drawLine(g, figureX, figureY);
/* 126:    */     }
/* 127: 91 */     else if (this.figure.equals("distributed"))
/* 128:    */     {
/* 129: 92 */       int radius = getHeight() / 50;
/* 130: 93 */       for (int i = -100; i < 100; i++) {
/* 131: 94 */         g.fillOval(figureX + i * 673201334 / 55887259, figureY + i * 946374328 / 89371903, radius, radius);
/* 132:    */       }
/* 133:    */     }
/* 134: 97 */     if (this.motional)
/* 135:    */     {
/* 136: 98 */       int arrowWidth = width / 3;
/* 137: 99 */       if (this.figure == "line")
/* 138:    */       {
/* 139:100 */         g.drawLine(figureX - width / 4, figureY - height / 30, figureX + width / 4, figureY - height / 30);
/* 140:101 */         g.drawLine(figureX + width / 4 - width / 40, figureY - height / 30 - height / 40, figureX + width / 4, 
/* 141:102 */           figureY - height / 30);
/* 142:    */       }
/* 143:    */       else
/* 144:    */       {
/* 145:104 */         g.drawLine(figureX, figureY, figureX + arrowWidth, figureY);
/* 146:105 */         g.drawLine(figureX + arrowWidth - width / 40, figureY - height / 40, figureX + arrowWidth, figureY);
/* 147:106 */         g.drawLine(figureX + arrowWidth - width / 40, figureY + height / 40, figureX + arrowWidth, figureY);
/* 148:    */       }
/* 149:    */     }
/* 150:    */   }
/* 151:    */   
/* 152:    */   private void drawDot(Graphics g, int x, int y)
/* 153:    */   {
/* 154:111 */     int radius = getHeight() / 38;
/* 155:112 */     g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
/* 156:    */   }
/* 157:    */   
/* 158:    */   private void drawLine(Graphics g, int x, int y)
/* 159:    */   {
/* 160:115 */     int width = 3 * getWidth() / 4;
/* 161:116 */     int height = getHeight() / 60;
/* 162:117 */     g.fillRect(x - width / 2, y - height / 2, width, height);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public boolean isViewable()
/* 166:    */   {
/* 167:120 */     return this.viewable;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setViewable(boolean b)
/* 171:    */   {
/* 172:123 */     this.viewable = b;
/* 173:124 */     setVisible(b);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void clearData()
/* 177:    */   {
/* 178:127 */     setViewable(false);
/* 179:    */   }
/* 180:    */   
/* 181:    */   public Ports getPorts()
/* 182:    */   {
/* 183:131 */     if (this.ports == null) {
/* 184:132 */       this.ports = new Ports();
/* 185:    */     }
/* 186:134 */     return this.ports;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setParameters(Object o)
/* 190:    */   {
/* 191:137 */     if ((o instanceof GeometryFrame))
/* 192:    */     {
/* 193:138 */       Relation frame = (Relation)((GeometryFrame)o).getThing();
/* 194:139 */       setParameters(frame);
/* 195:    */     }
/* 196:140 */     else if ((o instanceof Relation))
/* 197:    */     {
/* 198:141 */       Relation frame = (Relation)o;
/* 199:142 */       if (frame.isA(GeometryFrame.FRAMETYPE))
/* 200:    */       {
/* 201:143 */         setFigure(GeometryFrame.getFigureGeometry(frame));
/* 202:144 */         setGround(GeometryFrame.getGroundGeometry(frame));
/* 203:145 */         if (GeometryFrame.getRelationship(frame).equals("motional")) {
/* 204:146 */           setMotional();
/* 205:    */         } else {
/* 206:148 */           setLocative();
/* 207:    */         }
/* 208:150 */         setViewable(true);
/* 209:151 */         repaint();
/* 210:    */       }
/* 211:    */     }
/* 212:    */   }
/* 213:    */   
/* 214:    */   public static void main(String[] args)
/* 215:    */   {
/* 216:156 */     GeometryViewer view = new GeometryViewer();
/* 217:157 */     GeometryFrame gFrame = new GeometryFrame(GeometryFrame.makeSchema(new Entity("bike"), "point", new Entity(
/* 218:158 */       "walkway"), "line", "motional"));
/* 219:    */     
/* 220:160 */     JFrame frame = new JFrame();
/* 221:161 */     frame.getContentPane().add(view);
/* 222:162 */     frame.setBounds(0, 0, 200, 200);
/* 223:163 */     frame.setVisible(true);
/* 224:    */   }
/* 225:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.GeometryViewer
 * JD-Core Version:    0.7.0.1
 */