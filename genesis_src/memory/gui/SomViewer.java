/*   1:    */ package memory.gui;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import connections.Connections;
/*   5:    */ import connections.Ports;
/*   6:    */ import connections.WiredBox;
/*   7:    */ import frames.Frame;
/*   8:    */ import java.awt.BorderLayout;
/*   9:    */ import java.awt.Color;
/*  10:    */ import java.awt.Dimension;
/*  11:    */ import java.awt.Graphics;
/*  12:    */ import java.awt.Point;
/*  13:    */ import java.awt.event.MouseEvent;
/*  14:    */ import java.awt.event.MouseListener;
/*  15:    */ import java.io.PrintStream;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.HashMap;
/*  18:    */ import java.util.HashSet;
/*  19:    */ import java.util.Iterator;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import java.util.Set;
/*  23:    */ import javax.swing.JLabel;
/*  24:    */ import javax.swing.JPanel;
/*  25:    */ import memory.soms.NewSom;
/*  26:    */ import memory.soms.Som;
/*  27:    */ 
/*  28:    */ public class SomViewer
/*  29:    */   extends JPanel
/*  30:    */   implements WiredBox
/*  31:    */ {
/*  32: 28 */   CirclePanel<Entity> circle = new CirclePanel();
/*  33:    */   
/*  34:    */   public SomViewer()
/*  35:    */   {
/*  36: 31 */     setLayout(new BorderLayout());
/*  37: 32 */     add(this.circle, "Center");
/*  38: 33 */     Connections.getPorts(this).addSignalProcessor("input");
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void display(Som<Entity> s)
/*  42:    */   {
/*  43: 43 */     if (s != null)
/*  44:    */     {
/*  45: 45 */       Map<Entity, Set<Entity>> neighborhood = new HashMap();
/*  46: 46 */       for (Entity e : s.getMemory()) {
/*  47: 47 */         neighborhood.put(e, new HashSet(s.neighbors(e)));
/*  48:    */       }
/*  49: 49 */       Map<Entity, Integer> weights = new HashMap();
/*  50: 50 */       if ((s instanceof NewSom)) {
/*  51: 51 */         weights = ((NewSom)s).getWeights();
/*  52:    */       }
/*  53: 53 */       this.circle.setEntries(neighborhood, weights);
/*  54:    */     }
/*  55: 55 */     repaint();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void input(Object input)
/*  59:    */   {
/*  60: 59 */     if ((input instanceof Som)) {
/*  61: 60 */       display((Som)input);
/*  62:    */     } else {
/*  63: 62 */       System.err.println("Bad input to SomViewer");
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void output(Object output)
/*  68:    */   {
/*  69: 67 */     if ((output instanceof Entity))
/*  70:    */     {
/*  71: 68 */       Connections.getPorts(this).transmit(output);
/*  72:    */     }
/*  73: 69 */     else if ((output instanceof Frame))
/*  74:    */     {
/*  75: 70 */       Frame el = (Frame)output;
/*  76: 71 */       Connections.getPorts(this).transmit(el.getThing());
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   private class CirclePanel<E>
/*  81:    */     extends JPanel
/*  82:    */   {
/*  83: 84 */     private Map<E, Set<E>> neighborhood = new HashMap();
/*  84: 85 */     Map<E, Integer> weights = new HashMap();
/*  85: 86 */     private List<E> order = new ArrayList();
/*  86: 87 */     private Map<E, CirclePanel<E>.DotLabel> dots = new HashMap();
/*  87: 88 */     private Map<E, Point> circle = new HashMap();
/*  88:    */     private int width;
/*  89:    */     private int height;
/*  90:    */     private int radius;
/*  91:    */     int dotSize;
/*  92: 92 */     private Point center = new Point(0, 0);
/*  93:    */     SomViewer parent;
/*  94:    */     
/*  95:    */     public CirclePanel()
/*  96:    */     {
/*  97: 96 */       setBackground(Color.WHITE);
/*  98: 97 */       setLayout(null);
/*  99:    */     }
/* 100:    */     
/* 101:    */     public void setEntries(Map<E, Set<E>> neighborhood, Map<E, Integer> weights)
/* 102:    */     {
/* 103:107 */       this.neighborhood = neighborhood;
/* 104:108 */       this.weights = weights;
/* 105:    */       
/* 106:110 */       this.order = new ArrayList(neighborhood.keySet());
/* 107:111 */       this.width = getWidth();
/* 108:112 */       this.height = getHeight();
/* 109:113 */       this.radius = ((Math.min(this.width, this.height) - 20) / 2);
/* 110:114 */       this.center = new Point(this.width / 2, this.height / 2);
/* 111:115 */       this.dotSize = Math.min(Math.max(this.radius / 8, 6), 12);
/* 112:    */       
/* 113:117 */       removeAll();
/* 114:118 */       this.dots.clear();
/* 115:119 */       for (E e : neighborhood.keySet())
/* 116:    */       {
/* 117:120 */         CirclePanel<E>.DotLabel label = new DotLabel(e);
/* 118:121 */         this.dots.put(e, label);
/* 119:122 */         add(label);
/* 120:    */       }
/* 121:125 */       int maxPasses = 22;
/* 122:126 */       for (int pass = 0; pass < maxPasses; pass++)
/* 123:    */       {
/* 124:127 */         boolean doneEarly = true;
/* 125:128 */         for (int i = 0; i < this.order.size(); i++)
/* 126:    */         {
/* 127:130 */           int j = i + 1;
/* 128:131 */           if (j == this.order.size()) {
/* 129:132 */             j = 0;
/* 130:    */           }
/* 131:134 */           E current = this.order.get(i);
/* 132:135 */           E next = this.order.get(j);
/* 133:136 */           double oldScore = computeScore(current) + computeScore(next);
/* 134:137 */           this.order.set(j, current);
/* 135:138 */           this.order.set(i, next);
/* 136:139 */           double newScore = computeScore(current) + computeScore(next);
/* 137:140 */           if (oldScore <= newScore)
/* 138:    */           {
/* 139:141 */             this.order.set(i, current);
/* 140:142 */             this.order.set(j, next);
/* 141:    */           }
/* 142:    */           else
/* 143:    */           {
/* 144:145 */             doneEarly = false;
/* 145:    */           }
/* 146:    */         }
/* 147:152 */         if (doneEarly) {
/* 148:    */           break;
/* 149:    */         }
/* 150:    */       }
/* 151:    */     }
/* 152:    */     
/* 153:    */     private double computeScore(E entry)
/* 154:    */     {
/* 155:159 */       plotEntries();
/* 156:160 */       Point p = (Point)this.circle.get(entry);
/* 157:161 */       double score = 0.0D;
/* 158:162 */       for (E n : (Set)this.neighborhood.get(entry))
/* 159:    */       {
/* 160:163 */         Point d = (Point)this.circle.get(n);
/* 161:164 */         score += d.distance(p);
/* 162:    */       }
/* 163:166 */       return score;
/* 164:    */     }
/* 165:    */     
/* 166:    */     private void plotEntries()
/* 167:    */     {
/* 168:170 */       if (this.order.size() != this.neighborhood.keySet().size()) {
/* 169:171 */         System.err.println("Broken rep invarient in ClickCircle! Expect wonky behavor.");
/* 170:    */       }
/* 171:173 */       this.circle.clear();
/* 172:174 */       double angle = 360.0D / this.order.size();
/* 173:175 */       for (E entry : this.order)
/* 174:    */       {
/* 175:176 */         double x = this.center.getX() + this.radius * 
/* 176:177 */           Math.cos(Math.toRadians(angle * this.order.indexOf(entry)));
/* 177:178 */         double y = this.center.getY() + this.radius * 
/* 178:179 */           Math.sin(Math.toRadians(angle * this.order.indexOf(entry)));
/* 179:    */         
/* 180:181 */         int intX = (int)x;
/* 181:182 */         int intY = (int)y;
/* 182:183 */         this.circle.put(entry, new Point(intX, intY));
/* 183:    */       }
/* 184:185 */       if (!this.neighborhood.keySet().equals(this.circle.keySet())) {
/* 185:186 */         System.err.println("Broken rep invarient in ClickCircle! Expect wonky behavor.");
/* 186:    */       }
/* 187:    */     }
/* 188:    */     
/* 189:    */     public void paintComponent(Graphics g)
/* 190:    */     {
/* 191:192 */       super.paintComponent(g);
/* 192:    */       
/* 193:194 */       this.width = getWidth();
/* 194:195 */       this.height = getHeight();
/* 195:196 */       this.radius = ((Math.min(this.width, this.height) - 20) / 2);
/* 196:197 */       this.center = new Point(this.width / 2, this.height / 2);
/* 197:198 */       this.dotSize = Math.min(Math.max(this.radius / 8, 6), 12);
/* 198:199 */       plotEntries();
/* 199:    */       
/* 200:201 */       g.setColor(Color.LIGHT_GRAY);
/* 201:202 */       g.fillOval((int)this.center.getX() - this.radius - 2, (int)this.center.getY() - this.radius - 2, 
/* 202:203 */         this.radius * 2 + 4, this.radius * 2 + 4);
/* 203:204 */       g.setColor(Color.WHITE);
/* 204:205 */       g.fillOval((int)this.center.getX() - this.radius + 1, (int)this.center.getY() + 1 - this.radius, 
/* 205:206 */         this.radius * 2 - 2, this.radius * 2 - 2);
/* 206:    */       Iterator localIterator2;
/* 207:208 */       for (Iterator localIterator1 = this.circle.keySet().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 208:    */       {
/* 209:208 */         E entry = (Object)localIterator1.next();
/* 210:209 */         Point start = (Point)this.circle.get(entry);
/* 211:210 */         localIterator2 = ((Set)this.neighborhood.get(entry)).iterator(); continue;E target = (Object)localIterator2.next();
/* 212:211 */         Point end = (Point)this.circle.get(target);
/* 213:212 */         g.setColor(Color.BLACK);
/* 214:213 */         g.drawLine(start.x, start.y, end.x, end.y);
/* 215:    */       }
/* 216:217 */       drawDots();
/* 217:    */     }
/* 218:    */     
/* 219:    */     private void drawDots()
/* 220:    */     {
/* 221:221 */       for (E e : this.dots.keySet())
/* 222:    */       {
/* 223:222 */         Point p = (Point)this.circle.get(e);
/* 224:223 */         CirclePanel<E>.DotLabel label = (DotLabel)this.dots.get(e);
/* 225:224 */         label.addMouseListener(label);
/* 226:    */         
/* 227:226 */         int weight = this.weights.get(e) == null ? 1 : ((Integer)this.weights.get(e)).intValue();
/* 228:227 */         int size = this.dotSize + weight * 2;
/* 229:228 */         label.setBounds(p.x - size / 2, p.y - size / 2, size, size);
/* 230:    */       }
/* 231:    */     }
/* 232:    */     
/* 233:    */     class CircleRepainter
/* 234:    */       implements Runnable
/* 235:    */     {
/* 236:    */       SomViewer.CirclePanel circle;
/* 237:    */       
/* 238:    */       public CircleRepainter(SomViewer.CirclePanel c)
/* 239:    */       {
/* 240:235 */         this.circle = c;
/* 241:    */       }
/* 242:    */       
/* 243:    */       public void run()
/* 244:    */       {
/* 245:238 */         this.circle.repaint();
/* 246:    */       }
/* 247:    */     }
/* 248:    */     
/* 249:    */     private class DotLabel
/* 250:    */       extends JLabel
/* 251:    */       implements MouseListener
/* 252:    */     {
/* 253:    */       private E entry;
/* 254:    */       
/* 255:    */       public DotLabel()
/* 256:    */       {
/* 257:245 */         this.entry = e;
/* 258:    */       }
/* 259:    */       
/* 260:    */       public void paintComponent(Graphics g)
/* 261:    */       {
/* 262:249 */         int size = getSize().width;
/* 263:    */         
/* 264:251 */         g.setColor(Color.BLUE);
/* 265:252 */         g.fillOval(0, 0, size, size);
/* 266:253 */         g.setColor(Color.BLACK);
/* 267:254 */         g.drawOval(0, 0, size - 1, size - 1);
/* 268:    */       }
/* 269:    */       
/* 270:    */       public void mouseClicked(MouseEvent e) {}
/* 271:    */       
/* 272:    */       public void mouseEntered(MouseEvent e)
/* 273:    */       {
/* 274:260 */         SomViewer.this.output(this.entry);
/* 275:    */       }
/* 276:    */       
/* 277:    */       public void mouseExited(MouseEvent e) {}
/* 278:    */       
/* 279:    */       public void mousePressed(MouseEvent e) {}
/* 280:    */       
/* 281:    */       public void mouseReleased(MouseEvent e) {}
/* 282:    */     }
/* 283:    */   }
/* 284:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.gui.SomViewer
 * JD-Core Version:    0.7.0.1
 */