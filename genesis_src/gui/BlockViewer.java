/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Relation;
/*   5:    */ import connections.Connections;
/*   6:    */ import connections.Ports;
/*   7:    */ import connections.WiredBox;
/*   8:    */ import frames.BlockFrame;
/*   9:    */ import java.awt.Color;
/*  10:    */ import java.awt.Container;
/*  11:    */ import java.awt.Graphics;
/*  12:    */ import java.awt.Graphics2D;
/*  13:    */ import java.io.PrintStream;
/*  14:    */ import javax.swing.JFrame;
/*  15:    */ import javax.swing.JPanel;
/*  16:    */ 
/*  17:    */ public class BlockViewer
/*  18:    */   extends JPanel
/*  19:    */   implements WiredBox
/*  20:    */ {
/*  21: 10 */   private boolean viewable = false;
/*  22: 11 */   private boolean complete = false;
/*  23: 12 */   private boolean contain = false;
/*  24: 13 */   private Color blockerColor = Color.gray;
/*  25:    */   
/*  26:    */   public BlockViewer()
/*  27:    */   {
/*  28: 15 */     setOpaque(false);
/*  29: 16 */     Connections.getPorts(this).addSignalProcessor("setParameters");
/*  30:    */   }
/*  31:    */   
/*  32:    */   private void drawThing(Graphics g)
/*  33:    */   {
/*  34: 19 */     int radius = getHeight() / 15;
/*  35: 20 */     int centerX = getWidth() / 2;
/*  36: 21 */     int centerY = getHeight() / 2;
/*  37: 22 */     g.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
/*  38:    */   }
/*  39:    */   
/*  40:    */   private void drawObstruction(Graphics g, boolean complete)
/*  41:    */   {
/*  42: 25 */     int x = 2 * getWidth() / 3;
/*  43:    */     
/*  44: 27 */     int height = getHeight() / 9;
/*  45: 28 */     int width = getWidth() / 9;
/*  46: 29 */     g.fillRect(x, height, width, height);
/*  47: 30 */     g.fillRect(x, 3 * height, width, height);
/*  48: 31 */     g.fillRect(x, 5 * height, width, height);
/*  49: 32 */     g.fillRect(x, 7 * height, width, height);
/*  50: 33 */     if (complete)
/*  51:    */     {
/*  52: 34 */       g.fillRect(x, 2 * height, width, height);
/*  53: 35 */       g.fillRect(x, 4 * height, width, height);
/*  54: 36 */       g.fillRect(x, 6 * height, width, height);
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   private void drawArrow(Graphics g)
/*  59:    */   {
/*  60: 40 */     int lineWidth = getWidth() / 5;
/*  61: 41 */     int linePos = 2 * getHeight() / 3;
/*  62: 42 */     g.drawLine(2 * lineWidth, linePos, 3 * lineWidth, linePos);
/*  63: 43 */     g.drawLine(3 * lineWidth, linePos, 11 * lineWidth / 4, linePos + lineWidth / 8);
/*  64: 44 */     g.drawLine(3 * lineWidth, linePos, 11 * lineWidth / 4, linePos - lineWidth / 8);
/*  65:    */   }
/*  66:    */   
/*  67:    */   private void drawContainer(Graphics g, boolean complete)
/*  68:    */   {
/*  69: 47 */     int centerX = getWidth() / 2;
/*  70: 48 */     int centerY = getHeight() / 2;
/*  71: 49 */     int buffer = getHeight() / 5;
/*  72: 50 */     int height = getHeight() / 9;
/*  73: 51 */     int width = getHeight() / 25;
/*  74: 52 */     g.fillRect(centerX - buffer, centerY - buffer, width, height);
/*  75: 53 */     g.fillRect(centerX - buffer, centerY - buffer, height, width);
/*  76: 54 */     g.fillRect(centerX + buffer - width, centerY + buffer - height, width, height);
/*  77: 55 */     g.fillRect(centerX + buffer - height, centerY + buffer - width, height, width);
/*  78: 56 */     g.fillRect(centerX + buffer - width, centerY - buffer, width, height);
/*  79: 57 */     g.fillRect(centerX + buffer - height, centerY - buffer, height, width);
/*  80: 58 */     g.fillRect(centerX - buffer, centerY + buffer - height, width, height);
/*  81: 59 */     g.fillRect(centerX - buffer, centerY + buffer - width, height, width);
/*  82: 60 */     if (complete)
/*  83:    */     {
/*  84: 61 */       g.fillRect(centerX - buffer, centerY - buffer + height, width, 2 * (buffer - height));
/*  85: 62 */       g.fillRect(centerX + buffer - width, centerY - buffer + height, width, 2 * (buffer - height));
/*  86: 63 */       g.fillRect(centerX - buffer + height, centerY - buffer, 2 * (buffer - height), width);
/*  87: 64 */       g.fillRect(centerX - buffer + height, centerY + buffer - width, 2 * (buffer - height), width);
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void paintComponent(Graphics x)
/*  92:    */   {
/*  93: 69 */     super.paintComponent(x);
/*  94: 70 */     Graphics2D g = (Graphics2D)x;
/*  95: 71 */     int width = getWidth();
/*  96: 72 */     int height = getHeight();
/*  97: 73 */     g.drawRect(0, 0, width - 1, height - 1);
/*  98: 74 */     if ((width == 0) || (height == 0)) {
/*  99: 75 */       return;
/* 100:    */     }
/* 101: 77 */     if (!isViewable()) {
/* 102: 78 */       return;
/* 103:    */     }
/* 104: 80 */     g.drawRect(0, 0, width - 1, height - 1);
/* 105: 81 */     g.setColor(Color.blue);
/* 106: 82 */     drawThing(g);
/* 107: 83 */     g.setColor(this.blockerColor);
/* 108: 84 */     if (this.contain)
/* 109:    */     {
/* 110: 85 */       drawContainer(g, this.complete);
/* 111:    */     }
/* 112:    */     else
/* 113:    */     {
/* 114: 87 */       drawObstruction(g, this.complete);
/* 115: 88 */       g.setColor(Color.red);
/* 116: 89 */       drawArrow(g);
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   public boolean isViewable()
/* 121:    */   {
/* 122: 93 */     return this.viewable;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setViewable(boolean b)
/* 126:    */   {
/* 127: 96 */     this.viewable = b;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setComplete()
/* 131:    */   {
/* 132: 99 */     this.complete = true;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setPartial()
/* 136:    */   {
/* 137:102 */     this.complete = false;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setContain()
/* 141:    */   {
/* 142:105 */     this.contain = true;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setObstruct()
/* 146:    */   {
/* 147:108 */     this.contain = false;
/* 148:    */   }
/* 149:    */   
/* 150:    */   private void clearData()
/* 151:    */   {
/* 152:111 */     setViewable(false);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setParameters(Object input)
/* 156:    */   {
/* 157:114 */     if ((input instanceof Entity))
/* 158:    */     {
/* 159:115 */       if (((Entity)input).isA(BlockFrame.FRAMETYPE))
/* 160:    */       {
/* 161:116 */         Relation frame = (Relation)input;
/* 162:117 */         if (BlockFrame.getBlockType(frame).equals("contains")) {
/* 163:118 */           setContain();
/* 164:119 */         } else if (BlockFrame.getBlockType(frame).equals("obstructs")) {
/* 165:120 */           setObstruct();
/* 166:    */         } else {
/* 167:122 */           System.err.println("Invalid block type" + BlockFrame.getBlockType(frame));
/* 168:    */         }
/* 169:124 */         if (BlockFrame.getMag(frame).equals("partial")) {
/* 170:125 */           setPartial();
/* 171:    */         } else {
/* 172:127 */           setComplete();
/* 173:    */         }
/* 174:129 */         setViewable(true);
/* 175:130 */         repaint();
/* 176:    */       }
/* 177:    */     }
/* 178:132 */     else if ((input instanceof BlockFrame)) {
/* 179:133 */       setParameters(((BlockFrame)input).getThing());
/* 180:    */     } else {
/* 181:135 */       System.err.println("Error: calling BlockViewer.setParameters with a non-block frame.");
/* 182:    */     }
/* 183:    */   }
/* 184:    */   
/* 185:    */   public static void main(String[] args)
/* 186:    */   {
/* 187:139 */     BlockViewer view = new BlockViewer();
/* 188:    */     
/* 189:    */ 
/* 190:142 */     JFrame frame = new JFrame();
/* 191:143 */     frame.getContentPane().add(view);
/* 192:144 */     frame.setBounds(0, 0, 200, 200);
/* 193:145 */     frame.setVisible(true);
/* 194:    */   }
/* 195:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.BlockViewer
 * JD-Core Version:    0.7.0.1
 */