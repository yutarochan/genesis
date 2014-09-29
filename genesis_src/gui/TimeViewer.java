/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Relation;
/*   5:    */ import connections.Connections;
/*   6:    */ import connections.Ports;
/*   7:    */ import java.awt.Color;
/*   8:    */ import java.awt.Container;
/*   9:    */ import java.awt.Graphics;
/*  10:    */ import java.awt.Graphics2D;
/*  11:    */ import java.io.PrintStream;
/*  12:    */ import javax.swing.BorderFactory;
/*  13:    */ import javax.swing.JFrame;
/*  14:    */ 
/*  15:    */ public class TimeViewer
/*  16:    */   extends NegatableJPanel
/*  17:    */ {
/*  18: 13 */   public final int CENTER = 2;
/*  19: 13 */   public final int RIGHT = 3;
/*  20: 13 */   public final int LEFT = 1;
/*  21: 15 */   private boolean viewable = false;
/*  22: 17 */   int firstSize = 1;
/*  23: 19 */   int secondSize = 1;
/*  24: 21 */   int lAlign = 1;
/*  25: 23 */   int rAlign = 3;
/*  26:    */   
/*  27:    */   public TimeViewer()
/*  28:    */   {
/*  29: 26 */     setBorder(BorderFactory.createLineBorder(Color.BLACK));
/*  30: 27 */     setOpaque(false);
/*  31: 28 */     Connections.getPorts(this).addSignalProcessor("view");
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setFirstSize(int size)
/*  35:    */   {
/*  36: 32 */     this.firstSize = size;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setSecondSize(int size)
/*  40:    */   {
/*  41: 36 */     this.secondSize = size;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setRAlign(int alignment)
/*  45:    */   {
/*  46: 40 */     this.rAlign = alignment;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setViewable(boolean b)
/*  50:    */   {
/*  51: 44 */     this.viewable = b;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean isViewable()
/*  55:    */   {
/*  56: 48 */     return this.viewable;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void paintComponent(Graphics x)
/*  60:    */   {
/*  61: 52 */     super.paintComponent(x);
/*  62: 53 */     Graphics2D g = (Graphics2D)x;
/*  63: 54 */     int width = getWidth();
/*  64: 55 */     int height = getHeight();
/*  65: 56 */     if ((width == 0) || (height == 0)) {
/*  66: 57 */       return;
/*  67:    */     }
/*  68: 59 */     g.drawRect(0, 0, width - 1, height - 1);
/*  69: 60 */     if (!isViewable()) {
/*  70: 61 */       return;
/*  71:    */     }
/*  72: 63 */     g.drawRect(0, 0, width - 1, height - 1);
/*  73: 64 */     drawBar(g, true, this.lAlign, this.firstSize);
/*  74: 65 */     drawBar(g, false, this.rAlign, this.secondSize);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void drawBar(Graphics g, boolean top, int alignment, int size)
/*  78:    */   {
/*  79: 69 */     int height = getHeight();
/*  80: 70 */     int width = getWidth();
/*  81:    */     
/*  82: 72 */     int thickness = height / 14;
/*  83: 73 */     int baseLength = width / 11;
/*  84: 74 */     int offset = baseLength / 2;
/*  85:    */     int pos;
/*  86:    */     int pos;
/*  87: 75 */     if (top) {
/*  88: 77 */       pos = 3 * height / 7;
/*  89:    */     } else {
/*  90: 80 */       pos = 4 * height / 7;
/*  91:    */     }
/*  92: 82 */     int rest = (10 - size) * baseLength;
/*  93: 83 */     if (alignment == 2) {
/*  94: 84 */       offset += rest / 2;
/*  95: 86 */     } else if (alignment == 3) {
/*  96: 87 */       offset += rest;
/*  97:    */     }
/*  98: 89 */     g.fillRect(offset, pos, size * baseLength, thickness);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void view(Object o)
/* 102:    */   {
/* 103: 93 */     if (!(o instanceof Entity)) {
/* 104: 94 */       return;
/* 105:    */     }
/* 106: 96 */     Entity t = (Entity)o;
/* 107: 97 */     if (t.functionP("milestone"))
/* 108:    */     {
/* 109: 98 */       this.firstSize = 4;
/* 110: 99 */       this.secondSize = 4;
/* 111:100 */       this.lAlign = 3;
/* 112:101 */       this.rAlign = 1;
/* 113:    */     }
/* 114:103 */     else if (t.relationP())
/* 115:    */     {
/* 116:104 */       Relation frame = (Relation)t;
/* 117:105 */       if (frame.isA("before"))
/* 118:    */       {
/* 119:106 */         this.firstSize = 4;
/* 120:107 */         this.secondSize = 4;
/* 121:108 */         this.lAlign = 1;
/* 122:109 */         this.rAlign = 3;
/* 123:    */       }
/* 124:111 */       else if (frame.isA("after"))
/* 125:    */       {
/* 126:112 */         this.firstSize = 4;
/* 127:113 */         this.secondSize = 4;
/* 128:114 */         this.lAlign = 3;
/* 129:115 */         this.rAlign = 1;
/* 130:    */       }
/* 131:117 */       else if (frame.isA("equal"))
/* 132:    */       {
/* 133:118 */         this.firstSize = 10;
/* 134:119 */         this.secondSize = 10;
/* 135:120 */         this.lAlign = 1;
/* 136:121 */         this.rAlign = 1;
/* 137:    */       }
/* 138:123 */       else if (frame.isA("meets"))
/* 139:    */       {
/* 140:124 */         this.firstSize = 5;
/* 141:125 */         this.secondSize = 5;
/* 142:126 */         this.lAlign = 1;
/* 143:127 */         this.rAlign = 3;
/* 144:    */       }
/* 145:129 */       else if (frame.isA("overlaps"))
/* 146:    */       {
/* 147:130 */         this.firstSize = 7;
/* 148:131 */         this.secondSize = 7;
/* 149:132 */         this.lAlign = 1;
/* 150:133 */         this.rAlign = 3;
/* 151:    */       }
/* 152:135 */       else if ((frame.isA("during")) || (frame.isA("while")))
/* 153:    */       {
/* 154:136 */         this.firstSize = 6;
/* 155:137 */         this.secondSize = 10;
/* 156:138 */         this.rAlign = 1;
/* 157:139 */         this.lAlign = 2;
/* 158:    */       }
/* 159:141 */       else if (frame.isA("starts"))
/* 160:    */       {
/* 161:142 */         this.firstSize = 8;
/* 162:143 */         this.secondSize = 10;
/* 163:144 */         this.lAlign = 1;
/* 164:145 */         this.rAlign = 1;
/* 165:    */       }
/* 166:147 */       else if (frame.isA("finishes"))
/* 167:    */       {
/* 168:148 */         this.firstSize = 8;
/* 169:149 */         this.secondSize = 10;
/* 170:150 */         this.rAlign = 1;
/* 171:151 */         this.lAlign = 3;
/* 172:    */       }
/* 173:    */     }
/* 174:    */     else
/* 175:    */     {
/* 176:155 */       System.err.println("Error:  calling TimeViewer.setParameters with a non-time frame.");
/* 177:    */     }
/* 178:157 */     repaint();
/* 179:158 */     setViewable(true);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void clearData()
/* 183:    */   {
/* 184:162 */     setViewable(false);
/* 185:    */   }
/* 186:    */   
/* 187:    */   public static void main(String[] args)
/* 188:    */   {
/* 189:166 */     TimeViewer view = new TimeViewer();
/* 190:167 */     Entity tFrame = new Relation("finishes", new Entity(), new Entity());
/* 191:168 */     view.view(tFrame);
/* 192:169 */     JFrame frame = new JFrame();
/* 193:170 */     frame.getContentPane().add(view);
/* 194:171 */     frame.setBounds(0, 0, 200, 200);
/* 195:172 */     frame.setVisible(true);
/* 196:    */   }
/* 197:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.TimeViewer
 * JD-Core Version:    0.7.0.1
 */