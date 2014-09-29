/*   1:    */ package hibaAwad;
/*   2:    */ 
/*   3:    */ import connections.WiredBox;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.awt.Container;
/*   6:    */ import java.awt.Graphics;
/*   7:    */ import java.awt.event.WindowAdapter;
/*   8:    */ import java.awt.event.WindowEvent;
/*   9:    */ import java.io.PrintStream;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import javax.swing.JFrame;
/*  12:    */ import javax.swing.JPanel;
/*  13:    */ import utils.Mark;
/*  14:    */ 
/*  15:    */ public class Spider
/*  16:    */   extends JPanel
/*  17:    */   implements WiredBox
/*  18:    */ {
/*  19: 15 */   ArrayList<double[]> dataset = new ArrayList();
/*  20: 16 */   double[] max = new double[0];
/*  21: 17 */   ArrayList<String> storyLabels = new ArrayList();
/*  22: 18 */   String[] axislabels = new String[0];
/*  23: 19 */   int fillPercentage = 90;
/*  24: 21 */   int ballPercentage = 2;
/*  25: 22 */   Color ballColor = Color.BLACK;
/*  26: 23 */   Color areaColor = Color.YELLOW;
/*  27: 25 */   boolean connectDots = false;
/*  28: 26 */   boolean fillArea = false;
/*  29: 27 */   boolean drawAxis = false;
/*  30:    */   public static final String textPort = "My Text Port";
/*  31:    */   
/*  32:    */   public Spider()
/*  33:    */   {
/*  34: 30 */     setBackground(Color.WHITE);
/*  35: 31 */     setDataColor(Color.RED);
/*  36: 32 */     setAreaColor(Color.LIGHT_GRAY);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void processData(Object o)
/*  40:    */   {
/*  41: 40 */     Mark.say(
/*  42:    */     
/*  43:    */ 
/*  44: 43 */       new Object[] { "Processing", o, "in Viewer viewer via call through direct wire", o.getClass() });processViaDirectCall(o);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setConnectDots(boolean b)
/*  48:    */   {
/*  49: 46 */     this.connectDots = b;
/*  50: 47 */     repaint();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setFillArea(boolean b)
/*  54:    */   {
/*  55: 51 */     this.fillArea = b;
/*  56: 52 */     repaint();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void processViaDirectCall(Object o)
/*  60:    */   {
/*  61: 56 */     double[] datapoints = (double[])o;
/*  62: 57 */     setData(datapoints);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setData(double[] points)
/*  66:    */   {
/*  67: 62 */     if ((this.dataset.size() != 0) && (((double[])this.dataset.get(0)).length != points.length)) {
/*  68: 64 */       System.err.println("setData in Spider got wrong number of data points");
/*  69:    */     }
/*  70: 67 */     if (this.dataset.size() == 0) {
/*  71: 68 */       this.max = ((double[])points.clone());
/*  72:    */     } else {
/*  73: 70 */       for (int i = 0; i < points.length; i++) {
/*  74: 71 */         if (points[i] > this.max[i]) {
/*  75: 72 */           this.max[i] = points[i];
/*  76:    */         }
/*  77:    */       }
/*  78:    */     }
/*  79: 77 */     this.dataset.add((double[])points.clone());
/*  80:    */     
/*  81: 79 */     repaint();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void clearData()
/*  85:    */   {
/*  86: 83 */     this.dataset = new ArrayList();
/*  87: 84 */     this.storyLabels = new ArrayList();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void addStoryLabel(String label)
/*  91:    */   {
/*  92: 89 */     this.storyLabels.add(label);
/*  93: 90 */     repaint();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setDataColor(Color c)
/*  97:    */   {
/*  98: 94 */     this.ballColor = c;
/*  99: 95 */     repaint();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setAreaColor(Color c)
/* 103:    */   {
/* 104: 99 */     this.areaColor = c;
/* 105:100 */     repaint();
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String[] getAxislabels()
/* 109:    */   {
/* 110:103 */     return this.axislabels;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setAxislabels(String[] axislabels)
/* 114:    */   {
/* 115:107 */     this.axislabels = axislabels;
/* 116:108 */     repaint();
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void paint(Graphics g)
/* 120:    */   {
/* 121:114 */     super.paint(g);
/* 122:115 */     if (this.dataset.size() == 0) {
/* 123:116 */       return;
/* 124:    */     }
/* 125:118 */     int totalWidth = getWidth();
/* 126:119 */     int totalHeight = getHeight();
/* 127:120 */     if ((totalWidth == 0) || (totalHeight == 0)) {
/* 128:121 */       return;
/* 129:    */     }
/* 130:123 */     int width = this.fillPercentage * totalWidth / 100;
/* 131:124 */     int height = this.fillPercentage * totalHeight / 100;
/* 132:125 */     int radius = Math.min(width, height) / 2;
/* 133:126 */     int xCenter = totalWidth / 2;
/* 134:127 */     int yCenter = totalHeight / 2;
/* 135:128 */     drawData(g, radius, xCenter, yCenter);
/* 136:    */   }
/* 137:    */   
/* 138:    */   private void drawData(Graphics g, int radius, int xCenter, int yCenter)
/* 139:    */   {
/* 140:133 */     ArrayList<double[]> scaledDataset = new ArrayList();
/* 141:134 */     for (int j = 0; j < this.dataset.size(); j++)
/* 142:    */     {
/* 143:135 */       double[] scaledData = new double[((double[])this.dataset.get(j)).length];
/* 144:136 */       for (int i = 0; i < ((double[])this.dataset.get(j)).length; i++) {
/* 145:137 */         scaledData[i] = (((double[])this.dataset.get(j))[i] / this.max[i]);
/* 146:    */       }
/* 147:139 */       scaledDataset.add(scaledData);
/* 148:    */     }
/* 149:141 */     for (int j = 0; j < scaledDataset.size(); j++)
/* 150:    */     {
/* 151:142 */       double[] data = (double[])scaledDataset.get(j);
/* 152:143 */       double angle = 6.283185307179586D / data.length;
/* 153:144 */       int[] x = new int[data.length];
/* 154:145 */       int[] y = new int[data.length];
/* 155:146 */       int[] dataX = new int[data.length];
/* 156:147 */       int[] dataY = new int[data.length];
/* 157:148 */       int[] contourX = new int[data.length];
/* 158:149 */       int[] contourY = new int[data.length];
/* 159:150 */       int ballRadius = radius * this.ballPercentage / 100;
/* 160:151 */       for (int i = 0; i < data.length; i++)
/* 161:    */       {
/* 162:152 */         double theta = i * angle;
/* 163:153 */         x[i] = ((int)(radius * Math.cos(theta)));
/* 164:154 */         y[i] = ((int)(radius * Math.sin(theta)));
/* 165:155 */         dataX[i] = ((int)(x[i] * data[i]));
/* 166:156 */         dataY[i] = ((int)(y[i] * data[i]));
/* 167:157 */         contourX[i] = (xCenter + dataX[i]);
/* 168:158 */         contourY[i] = (yCenter + dataY[i]);
/* 169:    */       }
/* 170:161 */       if ((j == 0) && 
/* 171:162 */         (this.fillArea))
/* 172:    */       {
/* 173:163 */         Color handle = g.getColor();
/* 174:164 */         g.setColor(this.areaColor);
/* 175:165 */         g.fillPolygon(contourX, contourY, data.length);
/* 176:166 */         g.setColor(handle);
/* 177:    */       }
/* 178:171 */       if (j == 0) {
/* 179:172 */         for (int i = 0; i < data.length; i++) {
/* 180:173 */           g.drawLine(xCenter, yCenter, xCenter + x[i], yCenter + y[i]);
/* 181:    */         }
/* 182:    */       }
/* 183:178 */       if (j == 0) {
/* 184:179 */         for (int i = 0; i < data.length; i++) {
/* 185:180 */           g.drawString(this.axislabels[i], xCenter + x[i] + 10, yCenter + y[i]);
/* 186:    */         }
/* 187:    */       }
/* 188:184 */       if ((j == 0) && (
/* 189:185 */         (this.connectDots) || (this.fillArea))) {
/* 190:186 */         for (int i = 0; i < data.length; i++)
/* 191:    */         {
/* 192:187 */           int nextI = (i + 1) % data.length;
/* 193:188 */           g.drawLine(contourX[i], contourY[i], contourX[nextI], 
/* 194:189 */             contourY[nextI]);
/* 195:    */         }
/* 196:    */       }
/* 197:195 */       for (int i = 0; i < data.length; i++)
/* 198:    */       {
/* 199:196 */         Color handle = g.getColor();
/* 200:197 */         g.setColor(this.ballColor);
/* 201:198 */         drawDataPoint(g, ballRadius, contourX[i], contourY[i]);
/* 202:199 */         g.setColor(handle);
/* 203:    */       }
/* 204:203 */       if (j < this.storyLabels.size()) {
/* 205:205 */         for (int i = 0; i < data.length; i++) {
/* 206:206 */           g.drawString((String)this.storyLabels.get(j), contourX[i] - 20 * (i + 1), contourY[i] + 20);
/* 207:    */         }
/* 208:    */       }
/* 209:    */     }
/* 210:    */   }
/* 211:    */   
/* 212:    */   private void drawDataPoint(Graphics g, int radius, int x, int y)
/* 213:    */   {
/* 214:215 */     g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public static void main(String[] ignore)
/* 218:    */   {
/* 219:224 */     Spider spider = new Spider();
/* 220:225 */     double[] constant1 = { 1.0D, 0.1D, 0.3D, 0.5D, 0.7D, 0.9D };
/* 221:226 */     double[] a = { 5.0D, 10.0D, 3.0D, 4.0D };
/* 222:227 */     double[] b = { 10.0D, 20.0D, 1.0D, 2.0D };
/* 223:228 */     String[] labels = { "hello", "yes", "no", "ouf" };
/* 224:    */     
/* 225:230 */     JFrame frame = new JFrame();
/* 226:231 */     frame.getContentPane().add(spider);
/* 227:232 */     frame.setBounds(100, 100, 500, 700);
/* 228:233 */     frame.addWindowListener(new WindowAdapter()
/* 229:    */     {
/* 230:    */       public void windowClosing(WindowEvent e)
/* 231:    */       {
/* 232:235 */         System.exit(0);
/* 233:    */       }
/* 234:237 */     });
/* 235:238 */     spider.setData(a);
/* 236:239 */     spider.setData(b);
/* 237:240 */     spider.setDataColor(Color.RED);
/* 238:241 */     spider.setAreaColor(Color.LIGHT_GRAY);
/* 239:242 */     spider.setAxislabels(labels);
/* 240:243 */     spider.addStoryLabel("a");
/* 241:244 */     spider.addStoryLabel("b");
/* 242:245 */     frame.show();
/* 243:246 */     spider.setConnectDots(true);
/* 244:247 */     spider.setFillArea(true);
/* 245:    */   }
/* 246:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     hibaAwad.Spider
 * JD-Core Version:    0.7.0.1
 */