/*   1:    */ package matthewFay.viewers;
/*   2:    */ 
/*   3:    */ import java.awt.BorderLayout;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.awt.Container;
/*   6:    */ import java.awt.Font;
/*   7:    */ import java.awt.Graphics;
/*   8:    */ import java.awt.Graphics2D;
/*   9:    */ import java.awt.Point;
/*  10:    */ import java.awt.event.MouseEvent;
/*  11:    */ import java.awt.event.MouseListener;
/*  12:    */ import java.awt.geom.Rectangle2D;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.EventObject;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.HashSet;
/*  17:    */ import java.util.Iterator;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Set;
/*  20:    */ import javax.swing.JFrame;
/*  21:    */ import javax.swing.JPanel;
/*  22:    */ import matthewFay.Utilities.HashMatrix;
/*  23:    */ import utils.Mark;
/*  24:    */ 
/*  25:    */ public class MatrixGridViewer
/*  26:    */   extends JPanel
/*  27:    */   implements MouseListener
/*  28:    */ {
/*  29:    */   private HashMatrix<String, String, Float> matrix;
/*  30: 27 */   private boolean lower_matrix_only = false;
/*  31:    */   private HashMap<String, Float> rmin_values;
/*  32:    */   private HashMap<String, Float> rmax_values;
/*  33:    */   private HashMap<String, Float> rmax2_values;
/*  34:    */   private HashMap<String, Float> cmin_values;
/*  35:    */   private HashMap<String, Float> cmax_values;
/*  36:    */   private HashMap<String, Float> cmax2_values;
/*  37:    */   private int width;
/*  38:    */   private int height;
/*  39:    */   private int name_width;
/*  40:    */   private int name_height;
/*  41:    */   private double scale_x;
/*  42:    */   private double scale_y;
/*  43:    */   private ArrayList<String> key_list;
/*  44:    */   
/*  45:    */   public MatrixGridViewer(HashMatrix<String, String, Float> matrix)
/*  46:    */   {
/*  47: 38 */     setMatrix(matrix);
/*  48:    */     
/*  49: 40 */     addMouseListener(this);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setMatrix(HashMatrix<String, String, Float> matrix)
/*  53:    */   {
/*  54: 44 */     this.matrix = new HashMatrix(matrix);
/*  55:    */     
/*  56: 46 */     this.rmin_values = new HashMap();
/*  57: 47 */     this.rmax_values = new HashMap();
/*  58: 48 */     this.rmax2_values = new HashMap();
/*  59:    */     
/*  60: 50 */     this.cmin_values = new HashMap();
/*  61: 51 */     this.cmax_values = new HashMap();
/*  62: 52 */     this.cmax2_values = new HashMap();
/*  63:    */     
/*  64: 54 */     reweighMatrix();
/*  65: 55 */     invalidate();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void paint(Graphics g)
/*  69:    */   {
/*  70: 60 */     super.paintComponent(g);
/*  71: 61 */     setBackground(Color.WHITE);
/*  72: 62 */     paintMatrix(g);
/*  73:    */   }
/*  74:    */   
/*  75:    */   private void reweighMatrix()
/*  76:    */   {
/*  77: 66 */     if (this.matrix == null) {
/*  78: 67 */       return;
/*  79:    */     }
/*  80: 70 */     Set<String> key_set = new HashSet(this.matrix.keySetRows());
/*  81: 71 */     key_set.addAll(this.matrix.keySetCols());
/*  82: 72 */     ArrayList<String> key_list = new ArrayList(key_set);
/*  83:    */     
/*  84:    */ 
/*  85: 75 */     float min = 3.4028235E+38F;
/*  86: 76 */     float max = 1.4E-45F;
/*  87: 77 */     float max2 = 1.4E-45F;
/*  88: 78 */     for (Iterator localIterator1 = this.matrix.getValues().iterator(); localIterator1.hasNext();)
/*  89:    */     {
/*  90: 78 */       float val = ((Float)localIterator1.next()).floatValue();
/*  91: 79 */       min = Math.min(val, min);
/*  92: 80 */       max = Math.max(val, max);
/*  93:    */     }
/*  94: 82 */     for (localIterator1 = this.matrix.getValues().iterator(); localIterator1.hasNext();)
/*  95:    */     {
/*  96: 82 */       float val = ((Float)localIterator1.next()).floatValue();
/*  97: 83 */       if (val != max) {
/*  98: 84 */         max2 = Math.max(val, max2);
/*  99:    */       }
/* 100:    */     }
/* 101: 87 */     float range = max2 - min;
/* 102: 90 */     for (String row : this.matrix.keySetRows())
/* 103:    */     {
/* 104: 91 */       float rmin = 3.4028235E+38F;
/* 105: 92 */       float rmax = 1.4E-45F;
/* 106: 93 */       float rmax2 = 1.4E-45F;
/* 107: 94 */       for (String col : this.matrix.keySetCols()) {
/* 108: 95 */         if (this.matrix.contains(row, col))
/* 109:    */         {
/* 110: 96 */           float value = ((Float)this.matrix.get(row, col)).floatValue();
/* 111: 97 */           rmin = Math.min(value, rmin);
/* 112: 98 */           rmax = Math.max(value, rmax);
/* 113:    */         }
/* 114:    */       }
/* 115:101 */       for (String col : this.matrix.keySetCols()) {
/* 116:102 */         if (this.matrix.contains(row, col))
/* 117:    */         {
/* 118:103 */           float value = ((Float)this.matrix.get(row, col)).floatValue();
/* 119:104 */           if (value != rmax) {
/* 120:105 */             rmax2 = Math.max(value, rmax2);
/* 121:    */           }
/* 122:    */         }
/* 123:    */       }
/* 124:110 */       rmin = 0.0F;
/* 125:    */       
/* 126:112 */       this.rmin_values.put(row, Float.valueOf(rmin));
/* 127:113 */       this.rmax_values.put(row, Float.valueOf(rmax));
/* 128:114 */       this.rmax2_values.put(row, Float.valueOf(rmax2));
/* 129:    */     }
/* 130:    */     float cmin;
/* 131:    */     float cmax2;
/* 132:117 */     for (String col : this.matrix.keySetCols())
/* 133:    */     {
/* 134:118 */       cmin = 3.4028235E+38F;
/* 135:119 */       float cmax = 1.4E-45F;
/* 136:120 */       cmax2 = 1.4E-45F;
/* 137:121 */       for (String row : this.matrix.keySetRows()) {
/* 138:122 */         if (this.matrix.contains(row, col))
/* 139:    */         {
/* 140:123 */           float value = ((Float)this.matrix.get(row, col)).floatValue();
/* 141:124 */           cmin = Math.min(value, cmin);
/* 142:125 */           cmax = Math.max(value, cmax);
/* 143:    */         }
/* 144:    */       }
/* 145:128 */       for (String row : this.matrix.keySetCols()) {
/* 146:129 */         if (this.matrix.contains(row, col))
/* 147:    */         {
/* 148:130 */           float value = ((Float)this.matrix.get(row, col)).floatValue();
/* 149:131 */           if (value != cmax) {
/* 150:132 */             cmax2 = Math.max(value, cmax2);
/* 151:    */           }
/* 152:    */         }
/* 153:    */       }
/* 154:137 */       cmin = 0.0F;
/* 155:    */       
/* 156:139 */       this.cmin_values.put(col, Float.valueOf(cmin));
/* 157:140 */       this.cmax_values.put(col, Float.valueOf(cmax));
/* 158:141 */       this.cmax2_values.put(col, Float.valueOf(cmax2));
/* 159:    */     }
/* 160:144 */     boolean old_coloring = false;
/* 161:    */     float old_value;
/* 162:148 */     if (old_coloring) {
/* 163:149 */       for (cmin = this.matrix.keySetRows().iterator(); cmin.hasNext(); cmax2.hasNext())
/* 164:    */       {
/* 165:149 */         String row = (String)cmin.next();
/* 166:150 */         cmax2 = this.matrix.keySetCols().iterator(); continue;String col = (String)cmax2.next();
/* 167:151 */         if (this.matrix.contains(row, col))
/* 168:    */         {
/* 169:152 */           old_value = ((Float)this.matrix.get(row, col)).floatValue();
/* 170:153 */           if (old_value >= max)
/* 171:    */           {
/* 172:154 */             this.matrix.put(row, col, Float.valueOf(2.0F));
/* 173:    */           }
/* 174:    */           else
/* 175:    */           {
/* 176:156 */             float val = (old_value - min) / range;
/* 177:157 */             this.matrix.put(row, col, Float.valueOf(val));
/* 178:    */           }
/* 179:    */         }
/* 180:    */       }
/* 181:    */     } else {
/* 182:163 */       for (cmin = this.matrix.keySetRows().iterator(); cmin.hasNext(); old_value.hasNext())
/* 183:    */       {
/* 184:163 */         String row = (String)cmin.next();
/* 185:164 */         float rrange = ((Float)this.rmax2_values.get(row)).floatValue() - ((Float)this.rmin_values.get(row)).floatValue();
/* 186:    */         
/* 187:166 */         old_value = this.matrix.keySetCols().iterator(); continue;String col = (String)old_value.next();
/* 188:167 */         float crange = ((Float)this.cmax2_values.get(col)).floatValue() - ((Float)this.cmin_values.get(col)).floatValue();
/* 189:169 */         if (this.matrix.contains(row, col))
/* 190:    */         {
/* 191:170 */           float old_value = ((Float)this.matrix.get(row, col)).floatValue();
/* 192:171 */           if ((old_value >= ((Float)this.rmax_values.get(row)).floatValue()) && (old_value >= ((Float)this.cmax_values.get(col)).floatValue()))
/* 193:    */           {
/* 194:172 */             this.matrix.put(row, col, Float.valueOf(2.0F));
/* 195:    */           }
/* 196:    */           else
/* 197:    */           {
/* 198:174 */             float rval = (old_value - ((Float)this.rmin_values.get(row)).floatValue()) / rrange;
/* 199:175 */             float cval = (old_value - ((Float)this.cmin_values.get(col)).floatValue()) / crange;
/* 200:176 */             float val = Math.max(rval, cval);
/* 201:177 */             if (val > 1.0F) {
/* 202:178 */               Mark.say(new Object[] {"weird" });
/* 203:    */             }
/* 204:180 */             this.matrix.put(row, col, Float.valueOf(val));
/* 205:    */           }
/* 206:    */         }
/* 207:    */       }
/* 208:    */     }
/* 209:    */   }
/* 210:    */   
/* 211:    */   private void paintMatrix(Graphics g)
/* 212:    */   {
/* 213:197 */     if (this.matrix == null) {
/* 214:198 */       return;
/* 215:    */     }
/* 216:200 */     this.width = getWidth();
/* 217:201 */     this.height = getHeight();
/* 218:    */     
/* 219:203 */     Font font = new Font("Dialog", 0, 12);
/* 220:    */     
/* 221:205 */     Graphics2D canvas = (Graphics2D)g;
/* 222:    */     
/* 223:    */ 
/* 224:208 */     Set<String> key_set = new HashSet(this.matrix.keySetRows());
/* 225:209 */     key_set.addAll(this.matrix.keySetCols());
/* 226:210 */     this.key_list = new ArrayList(key_set);
/* 227:    */     
/* 228:    */ 
/* 229:213 */     this.name_width = 0;
/* 230:214 */     this.name_height = 0;
/* 231:215 */     int x = 0;
/* 232:216 */     int y = 0;
/* 233:217 */     for (String key : this.key_list)
/* 234:    */     {
/* 235:218 */       Rectangle2D bounds = font.getStringBounds(x + ": " + key, canvas.getFontRenderContext());
/* 236:219 */       this.name_width = ((int)Math.max(this.name_width, bounds.getWidth()));
/* 237:220 */       this.name_height = ((int)Math.max(this.name_height, bounds.getHeight()));
/* 238:221 */       x++;
/* 239:    */     }
/* 240:225 */     this.scale_x = (this.width / (this.name_width + this.key_list.size() * 20));
/* 241:226 */     this.scale_y = (this.height / (this.name_height + this.name_height * this.key_list.size()));
/* 242:    */     
/* 243:228 */     canvas.scale(this.scale_x, this.scale_y);
/* 244:    */     
/* 245:230 */     x = 0;
/* 246:231 */     y = 0;
/* 247:    */     
/* 248:233 */     canvas.setColor(Color.BLACK);
/* 249:234 */     canvas.setFont(font);
/* 250:235 */     canvas.drawRect(0, 0, this.name_width, this.name_height);
/* 251:236 */     for (String key : this.key_list)
/* 252:    */     {
/* 253:238 */       canvas.drawString(" " + x, this.name_width + x * 20, this.name_height);
/* 254:239 */       canvas.drawRect(this.name_width + x * 20, 0, 20, this.name_height);
/* 255:240 */       x++;
/* 256:    */     }
/* 257:242 */     x = 0;
/* 258:244 */     for (String row : this.key_list)
/* 259:    */     {
/* 260:245 */       canvas.setColor(Color.BLACK);
/* 261:246 */       canvas.setFont(font);
/* 262:247 */       canvas.drawString(x + ": " + row, 0, (x + 2) * this.name_height);
/* 263:248 */       canvas.drawRect(0, (x + 1) * this.name_height, this.name_width, this.name_height);
/* 264:249 */       for (String col : this.key_list) {
/* 265:250 */         if (this.matrix.contains(row, col))
/* 266:    */         {
/* 267:251 */           float val = ((Float)this.matrix.get(row, col)).floatValue();
/* 268:    */           Color c;
/* 269:    */           Color c;
/* 270:253 */           if (val > 1.0F) {
/* 271:254 */             c = Color.blue;
/* 272:    */           } else {
/* 273:256 */             c = new Color(0.0F, val, 0.0F);
/* 274:    */           }
/* 275:257 */           if ((x > y) && (this.lower_matrix_only)) {
/* 276:258 */             c = Color.black;
/* 277:    */           }
/* 278:259 */           canvas.setColor(c);
/* 279:260 */           canvas.fillRect(this.name_width + x * 20, (y + 1) * this.name_height, 20, this.name_height);
/* 280:261 */           canvas.setColor(Color.BLACK);
/* 281:262 */           canvas.drawRect(this.name_width + x * 20, (y + 1) * this.name_height, 20, this.name_height);
/* 282:263 */           y++;
/* 283:    */         }
/* 284:    */       }
/* 285:266 */       y = 0;
/* 286:267 */       x++;
/* 287:    */     }
/* 288:    */   }
/* 289:    */   
/* 290:    */   public static HashMatrix<String, String, Float> getDemoData()
/* 291:    */   {
/* 292:273 */     HashMatrix<String, String, Float> matrix = new HashMatrix();
/* 293:274 */     matrix.put("A", "A", Float.valueOf(100.0F));
/* 294:275 */     matrix.put("B", "A", Float.valueOf(50.0F));
/* 295:276 */     matrix.put("C", "A", Float.valueOf(25.0F));
/* 296:277 */     matrix.put("A", "B", Float.valueOf(50.0F));
/* 297:278 */     matrix.put("B", "B", Float.valueOf(100.0F));
/* 298:279 */     matrix.put("C", "B", Float.valueOf(50.0F));
/* 299:280 */     matrix.put("A", "C", Float.valueOf(25.0F));
/* 300:281 */     matrix.put("B", "C", Float.valueOf(50.0F));
/* 301:282 */     matrix.put("C", "C", Float.valueOf(100.0F));
/* 302:283 */     return matrix;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public static JFrame createPopoutMatrixViewer(HashMatrix<String, String, Float> matrix)
/* 306:    */   {
/* 307:287 */     MatrixGridViewer viewer = new MatrixGridViewer(matrix);
/* 308:    */     
/* 309:289 */     JFrame frame = new JFrame();
/* 310:    */     
/* 311:291 */     frame.getContentPane().setBackground(Color.WHITE);
/* 312:292 */     frame.getContentPane().setLayout(new BorderLayout());
/* 313:293 */     frame.getContentPane().add(viewer);
/* 314:    */     
/* 315:295 */     frame.setBounds(0, 0, 1024, 768);
/* 316:    */     
/* 317:297 */     frame.setTitle(MatrixGridViewer.class.toString());
/* 318:    */     
/* 319:299 */     frame.setVisible(true);
/* 320:    */     
/* 321:301 */     return frame;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public static void main(String[] args)
/* 325:    */   {
/* 326:306 */     HashMatrix<String, String, Float> matrix = getDemoData();
/* 327:    */     
/* 328:308 */     JFrame frame = createPopoutMatrixViewer(matrix);
/* 329:    */     
/* 330:310 */     frame.setDefaultCloseOperation(3);
/* 331:    */   }
/* 332:    */   
/* 333:    */   public void mouseClicked(MouseEvent arg0) {}
/* 334:    */   
/* 335:    */   public void mouseEntered(MouseEvent arg0) {}
/* 336:    */   
/* 337:    */   public void mouseExited(MouseEvent arg0) {}
/* 338:    */   
/* 339:    */   public void mousePressed(MouseEvent arg0) {}
/* 340:    */   
/* 341:    */   public void mouseReleased(MouseEvent arg0)
/* 342:    */   {
/* 343:339 */     if (arg0.getButton() == 1)
/* 344:    */     {
/* 345:340 */       float x = arg0.getPoint().x;
/* 346:341 */       float y = arg0.getPoint().y;
/* 347:    */       
/* 348:343 */       float count = this.key_list.size();
/* 349:    */       
/* 350:    */ 
/* 351:    */ 
/* 352:    */ 
/* 353:    */ 
/* 354:    */ 
/* 355:    */ 
/* 356:    */ 
/* 357:352 */       int clicked_x = (int)((x / this.scale_x - this.name_width) / (float)(this.width / this.scale_x - this.name_width) * count);
/* 358:353 */       int clicked_y = (int)((y / this.scale_y - this.name_height) / (float)(this.height / this.scale_y - this.name_height) * count);
/* 359:    */       
/* 360:    */ 
/* 361:356 */       String x_name = (String)this.key_list.get(clicked_x);
/* 362:357 */       String y_name = (String)this.key_list.get(clicked_y);
/* 363:    */       
/* 364:359 */       Mark.say(new Object[] {"(x_name,y_name) : (" + x_name + "," + y_name + ")" });
/* 365:360 */       throwMatrixClickEvent(new MatrixClickEvent(this, x_name, y_name));
/* 366:    */     }
/* 367:    */   }
/* 368:    */   
/* 369:364 */   List<MatrixClickListener> listeners = new ArrayList();
/* 370:    */   
/* 371:    */   public void addMatrixClickListener(MatrixClickListener listener)
/* 372:    */   {
/* 373:366 */     this.listeners.add(listener);
/* 374:    */   }
/* 375:    */   
/* 376:    */   public void throwMatrixClickEvent(MatrixClickEvent e)
/* 377:    */   {
/* 378:    */     MatrixClickListener listener;
/* 379:369 */     for (Iterator localIterator = this.listeners.iterator(); localIterator.hasNext(); listener.handleMatrixClickEvent(e)) {
/* 380:369 */       listener = (MatrixClickListener)localIterator.next();
/* 381:    */     }
/* 382:    */   }
/* 383:    */   
/* 384:    */   public class MatrixClickEvent
/* 385:    */     extends EventObject
/* 386:    */   {
/* 387:    */     public String x_name;
/* 388:    */     public String y_name;
/* 389:    */     
/* 390:    */     public MatrixClickEvent(Object source, String x_name, String y_name)
/* 391:    */     {
/* 392:378 */       super();
/* 393:379 */       this.x_name = x_name;
/* 394:380 */       this.y_name = y_name;
/* 395:    */     }
/* 396:    */   }
/* 397:    */   
/* 398:    */   public static abstract interface MatrixClickListener
/* 399:    */   {
/* 400:    */     public abstract void handleMatrixClickEvent(MatrixGridViewer.MatrixClickEvent paramMatrixClickEvent);
/* 401:    */   }
/* 402:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.viewers.MatrixGridViewer
 * JD-Core Version:    0.7.0.1
 */