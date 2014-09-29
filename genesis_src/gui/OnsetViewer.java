/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import connections.Connections;
/*   5:    */ import connections.Ports;
/*   6:    */ import connections.WiredBox;
/*   7:    */ import java.awt.Color;
/*   8:    */ import java.awt.Container;
/*   9:    */ import java.awt.Graphics;
/*  10:    */ import java.awt.Graphics2D;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import javax.swing.JFrame;
/*  13:    */ import javax.swing.JPanel;
/*  14:    */ import utils.StringIntPair;
/*  15:    */ 
/*  16:    */ public class OnsetViewer
/*  17:    */   extends JPanel
/*  18:    */   implements WiredBox
/*  19:    */ {
/*  20: 23 */   ArrayList<StringIntPair> pairs = new ArrayList();
/*  21: 25 */   public static String RESET_PORT = "reset port";
/*  22:    */   
/*  23:    */   public OnsetViewer()
/*  24:    */   {
/*  25: 28 */     setName("Onset viewer");
/*  26: 29 */     setBackground(Color.WHITE);
/*  27: 30 */     setOpaque(true);
/*  28: 31 */     Connections.getPorts(this).addSignalProcessor("process");
/*  29: 32 */     Connections.getPorts(this).addSignalProcessor(RESET_PORT, "reset");
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void reset(Object signal)
/*  33:    */   {
/*  34: 36 */     if (signal == "reset")
/*  35:    */     {
/*  36: 37 */       this.pairs.clear();
/*  37: 38 */       repaint();
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void process(Object x)
/*  42:    */   {
/*  43: 43 */     BetterSignal signal = BetterSignal.isSignal(x);
/*  44: 44 */     if (signal == null) {
/*  45: 45 */       return;
/*  46:    */     }
/*  47: 47 */     StringIntPair pair = new StringIntPair(signal.get(0, Object.class).toString(), 1);
/*  48: 48 */     addPair(this.pairs, pair);
/*  49:    */   }
/*  50:    */   
/*  51:    */   private void addPair(ArrayList<StringIntPair> pairs, StringIntPair pair)
/*  52:    */   {
/*  53: 52 */     for (StringIntPair element : pairs) {
/*  54: 53 */       if (element.getLabel().equalsIgnoreCase(pair.getLabel()))
/*  55:    */       {
/*  56: 54 */         element.setValue(element.getValue() + pair.getValue());
/*  57: 55 */         repaint();
/*  58: 56 */         return;
/*  59:    */       }
/*  60:    */     }
/*  61: 59 */     pairs.add(pair);
/*  62: 60 */     repaint();
/*  63:    */   }
/*  64:    */   
/*  65:    */   private void setPairs(ArrayList<StringIntPair> signal)
/*  66:    */   {
/*  67: 64 */     this.pairs = signal;
/*  68: 65 */     repaint();
/*  69:    */   }
/*  70:    */   
/*  71:    */   private int maximum()
/*  72:    */   {
/*  73: 69 */     int max = 0;
/*  74: 70 */     for (StringIntPair pair : this.pairs) {
/*  75: 71 */       max = Math.max(max, pair.getValue());
/*  76:    */     }
/*  77: 73 */     return max;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void paint(Graphics graphics)
/*  81:    */   {
/*  82: 78 */     super.paint(graphics);
/*  83: 79 */     int count = this.pairs.size();
/*  84: 80 */     if (count == 0) {
/*  85: 81 */       return;
/*  86:    */     }
/*  87: 83 */     Graphics2D g = (Graphics2D)graphics;
/*  88: 84 */     int height = getHeight();
/*  89: 85 */     int width = getWidth();
/*  90: 86 */     int maximum = maximum();
/*  91: 87 */     int maxBarWidth = width * 9 / 16;
/*  92: 88 */     int labelWidth = width * 3 / 8;
/*  93:    */     
/*  94: 90 */     int halfBarHeight = height / count / 2;
/*  95: 91 */     for (int i = 0; i < count; i++)
/*  96:    */     {
/*  97: 92 */       StringIntPair pair = (StringIntPair)this.pairs.get(i);
/*  98: 93 */       g.drawString(StringUtilities.prepareForDisplay(pair.getLabel()), labelWidth / 8, i * height / count + halfBarHeight);
/*  99:    */     }
/* 100: 96 */     for (int i = 0; i < count; i++)
/* 101:    */     {
/* 102: 97 */       StringIntPair pair = (StringIntPair)this.pairs.get(i);
/* 103: 98 */       int y = i * height / count;
/* 104: 99 */       int h = height / count;
/* 105:100 */       g.setColor(Color.CYAN);
/* 106:101 */       int barWidth = 0;
/* 107:102 */       if (maximum > 0) {
/* 108:103 */         barWidth = pair.getValue() * maxBarWidth / maximum;
/* 109:    */       }
/* 110:105 */       g.fillRect(labelWidth, y, barWidth, h);
/* 111:106 */       g.setColor(Color.BLACK);
/* 112:107 */       g.drawRect(labelWidth, y, barWidth, h);
/* 113:108 */       g.drawString(Integer.toString(pair.getValue()), 9 * labelWidth / 8, i * h + halfBarHeight);
/* 114:109 */       g.drawLine(0, y, width, y);
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static void main(String[] ignore)
/* 119:    */   {
/* 120:114 */     JFrame frame = new JFrame();
/* 121:115 */     OnsetViewer viewer = new OnsetViewer();
/* 122:116 */     ArrayList test = new ArrayList();
/* 123:    */     
/* 124:    */ 
/* 125:    */ 
/* 126:120 */     viewer.process(test);
/* 127:121 */     frame.getContentPane().add(viewer, "Center");
/* 128:122 */     frame.setBounds(0, 0, 500, 500);
/* 129:123 */     frame.setVisible(true);
/* 130:    */   }
/* 131:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.OnsetViewer
 * JD-Core Version:    0.7.0.1
 */