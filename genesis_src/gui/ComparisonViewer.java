/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Relation;
/*   5:    */ import bridge.reps.entities.Thread;
/*   6:    */ import connections.Connections;
/*   7:    */ import connections.Ports;
/*   8:    */ import java.awt.Color;
/*   9:    */ import java.awt.Container;
/*  10:    */ import java.awt.FontMetrics;
/*  11:    */ import java.awt.Graphics;
/*  12:    */ import java.awt.Graphics2D;
/*  13:    */ import java.io.PrintStream;
/*  14:    */ import java.util.Arrays;
/*  15:    */ import java.util.List;
/*  16:    */ import javax.swing.JFrame;
/*  17:    */ import start.Start;
/*  18:    */ 
/*  19:    */ public class ComparisonViewer
/*  20:    */   extends NegatableJPanel
/*  21:    */ {
/*  22:    */   String x;
/*  23:    */   String y;
/*  24:    */   String xOwner;
/*  25:    */   String yOwner;
/*  26:    */   String comparitor;
/*  27: 34 */   public List heightWords = Arrays.asList(new String[] { "taller", "shorter" });
/*  28: 36 */   public List widthWords = Arrays.asList(new String[] { "thicker", "thinner", "wider", "narrower" });
/*  29: 38 */   public List reversers = Arrays.asList(new String[] { "shorter", "thinner", "narrower", "smaller", "weaker", "softer" });
/*  30:    */   
/*  31:    */   public ComparisonViewer()
/*  32:    */   {
/*  33: 41 */     setOpaque(false);
/*  34: 42 */     Connections.getPorts(this).addSignalProcessor("view");
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void paint(Graphics graphics)
/*  38:    */   {
/*  39: 47 */     super.paint(graphics);
/*  40:    */     
/*  41: 49 */     Graphics2D g = (Graphics2D)graphics;
/*  42: 50 */     int height = getHeight();
/*  43: 51 */     int width = getWidth();
/*  44: 52 */     g.drawRect(0, 0, width - 1, height - 1);
/*  45: 53 */     int bigHeight = 50;
/*  46: 54 */     int bigWidth = 50;
/*  47: 55 */     int littleHeight = 50;
/*  48: 56 */     int littleWidth = 50;
/*  49: 57 */     int separation = 5;
/*  50: 58 */     Color squareColor = new Color(150, 150, 150);
/*  51: 59 */     int yCenter = 0;
/*  52: 60 */     int xCenter = 0;
/*  53: 61 */     if ((width == 0) || (height == 0)) {
/*  54: 62 */       return;
/*  55:    */     }
/*  56: 64 */     if ((this.x == null) || (this.y == null) || (this.comparitor == null)) {
/*  57: 65 */       return;
/*  58:    */     }
/*  59: 67 */     String left = this.x;
/*  60: 68 */     String right = this.y;
/*  61: 69 */     if (this.heightWords.contains(this.comparitor))
/*  62:    */     {
/*  63: 70 */       bigWidth = 25;
/*  64: 71 */       littleWidth = 25;
/*  65: 72 */       littleHeight = 25;
/*  66:    */     }
/*  67: 74 */     else if (this.widthWords.contains(this.comparitor))
/*  68:    */     {
/*  69: 75 */       littleWidth = 25;
/*  70:    */     }
/*  71:    */     else
/*  72:    */     {
/*  73: 78 */       littleHeight = littleWidth = 25;
/*  74:    */     }
/*  75: 80 */     if (this.reversers.contains(this.comparitor))
/*  76:    */     {
/*  77: 81 */       left = this.y;
/*  78: 82 */       right = this.x;
/*  79:    */     }
/*  80: 85 */     FontMetrics fm = g.getFontMetrics();
/*  81: 86 */     int totalHeight = bigHeight + 3 * fm.getHeight();
/*  82: 87 */     int totalWidth = bigWidth + separation + littleWidth;
/*  83:    */     
/*  84: 89 */     double scale = width / totalWidth;
/*  85:    */     
/*  86: 91 */     double scaleW = height / totalHeight;
/*  87: 93 */     if (scaleW < scale) {
/*  88: 94 */       scale = scaleW;
/*  89:    */     }
/*  90: 96 */     scale *= 0.9D;
/*  91:    */     
/*  92: 98 */     bigWidth = (int)(bigWidth * scale);
/*  93: 99 */     bigHeight = (int)(bigHeight * scale);
/*  94:    */     
/*  95:101 */     littleWidth = (int)(littleWidth * scale);
/*  96:102 */     littleHeight = (int)(littleHeight * scale);
/*  97:    */     
/*  98:104 */     totalWidth = (int)(totalWidth * scale);
/*  99:105 */     totalHeight = (int)(totalHeight * scale);
/* 100:    */     
/* 101:107 */     int xOffset = (width - totalWidth) / 2;
/* 102:108 */     int yOffset = (height - totalHeight) / 2;
/* 103:    */     
/* 104:    */ 
/* 105:    */ 
/* 106:112 */     g.setColor(squareColor);
/* 107:113 */     g.fillRect(xOffset, yOffset, bigWidth, bigHeight);
/* 108:114 */     g.fillRect(xOffset + bigWidth + separation, yOffset + bigHeight - littleHeight, littleWidth, littleHeight);
/* 109:115 */     g.setColor(Color.BLACK);
/* 110:    */     
/* 111:117 */     g.drawString(left, xOffset + bigWidth / 2 - fm.stringWidth(left) / 2, yOffset + bigHeight + 10 + fm.getDescent());
/* 112:118 */     g.drawString(right, xOffset + bigWidth + separation + littleWidth / 2 - fm.stringWidth(right) / 2, yOffset + bigHeight + 10 + 
/* 113:119 */       fm.getDescent());
/* 114:120 */     if (this.xOwner != null) {
/* 115:121 */       g.drawString(this.xOwner, xOffset + bigWidth / 2 - fm.stringWidth(this.xOwner) / 2, 
/* 116:122 */         fm.getHeight() + yOffset + bigHeight + 10 + fm.getDescent());
/* 117:    */     }
/* 118:124 */     if (this.yOwner != null) {
/* 119:125 */       g.drawString(this.yOwner, xOffset + bigWidth + separation + littleWidth / 2 - fm.stringWidth(this.yOwner) / 2, 
/* 120:126 */         fm.getHeight() + yOffset + bigHeight + 10 + fm.getDescent());
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   private void setParameters(String comparitor, Entity tx, Entity ty)
/* 125:    */   {
/* 126:132 */     this.x = tx.getType();
/* 127:133 */     this.y = ty.getType();
/* 128:134 */     this.xOwner = (this.yOwner = null);
/* 129:135 */     Thread ox = tx.getThreadWith("owners");
/* 130:136 */     if (ox != null) {
/* 131:137 */       this.xOwner = Start.strip((String)ox.lastElement());
/* 132:    */     }
/* 133:139 */     Thread oy = ty.getThreadWith("owners");
/* 134:140 */     if (oy != null) {
/* 135:141 */       this.yOwner = Start.strip((String)oy.lastElement());
/* 136:    */     }
/* 137:143 */     this.comparitor = comparitor;
/* 138:144 */     repaint();
/* 139:    */   }
/* 140:    */   
/* 141:    */   private void clearData()
/* 142:    */   {
/* 143:148 */     this.x = null;
/* 144:149 */     this.y = null;
/* 145:150 */     this.comparitor = null;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public static void main(String[] args)
/* 149:    */   {
/* 150:157 */     ComparisonViewer view = new ComparisonViewer();
/* 151:158 */     Relation relation = new Relation("comparison", new Entity("patrick"), new Entity("john"));
/* 152:159 */     relation.addType("taller");
/* 153:160 */     JFrame frame = new JFrame();
/* 154:161 */     frame.getContentPane().add(view);
/* 155:162 */     frame.setBounds(0, 0, 200, 200);
/* 156:163 */     frame.setVisible(true);
/* 157:164 */     view.view(relation);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void view(Object signal)
/* 161:    */   {
/* 162:169 */     if ((signal instanceof Relation))
/* 163:    */     {
/* 164:170 */       Relation comparison = (Relation)signal;
/* 165:171 */       String name = comparison.getType();
/* 166:172 */       if (comparison.isAPrimed("comparison")) {
/* 167:173 */         setParameters(name, comparison.getSubject(), comparison.getObject());
/* 168:    */       }
/* 169:    */     }
/* 170:    */     else
/* 171:    */     {
/* 172:177 */       System.err.println(getClass().getName() + ": Didn't know what to do with input of type " + signal.getClass().toString() + ": " + 
/* 173:178 */         signal + " in the place viewer");
/* 174:    */     }
/* 175:180 */     setTruthValue(signal);
/* 176:    */   }
/* 177:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.ComparisonViewer
 * JD-Core Version:    0.7.0.1
 */