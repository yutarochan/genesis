/*   1:    */ package connections.views;
/*   2:    */ 
/*   3:    */ import connections.Test;
/*   4:    */ import connections.WiredOnOffSwitch;
/*   5:    */ import java.awt.BasicStroke;
/*   6:    */ import java.awt.Color;
/*   7:    */ import java.awt.Container;
/*   8:    */ import java.awt.Dimension;
/*   9:    */ import java.awt.Font;
/*  10:    */ import java.awt.FontMetrics;
/*  11:    */ import java.awt.Graphics;
/*  12:    */ import java.awt.Graphics2D;
/*  13:    */ import java.awt.Rectangle;
/*  14:    */ import java.awt.Stroke;
/*  15:    */ import java.awt.event.MouseAdapter;
/*  16:    */ import java.awt.event.MouseEvent;
/*  17:    */ import java.awt.geom.AffineTransform;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.Observable;
/*  20:    */ import java.util.Observer;
/*  21:    */ import java.util.regex.Pattern;
/*  22:    */ import javax.swing.JFrame;
/*  23:    */ import javax.swing.JOptionPane;
/*  24:    */ import javax.swing.JPanel;
/*  25:    */ import javax.swing.JScrollPane;
/*  26:    */ import javax.swing.JSlider;
/*  27:    */ import javax.swing.event.ChangeEvent;
/*  28:    */ import javax.swing.event.ChangeListener;
/*  29:    */ import javax.swing.event.MouseInputListener;
/*  30:    */ 
/*  31:    */ public class ConnectionViewer
/*  32:    */   extends JPanel
/*  33:    */   implements Observer, MouseInputListener
/*  34:    */ {
/*  35: 41 */   ArrayList<ViewerBox> boxes = new ArrayList();
/*  36: 43 */   ArrayList<ViewerWire> wires = new ArrayList();
/*  37: 45 */   int maxHeight = 0;
/*  38: 47 */   int maxWidth = 0;
/*  39: 49 */   private double multiplier = 1.0D;
/*  40:    */   private double offsetX;
/*  41:    */   private double offsetY;
/*  42: 55 */   private int circleRadius = 3;
/*  43:    */   private int circleDiameter;
/*  44: 59 */   private int oldSliderValue = 100;
/*  45:    */   private JSlider slider;
/*  46: 63 */   private float[] dashes = { 5.0F };
/*  47: 65 */   private BasicStroke dashed = new BasicStroke(3.0F, 2, 0, 1.0F, this.dashes, 0.0F);
/*  48: 67 */   private BasicStroke dotted = new BasicStroke(3.0F, 1, 1, 1.0F, new float[] { 9.0F }, 0.0F);
/*  49: 74 */   private BasicStroke cross = new BasicStroke(3.0F, 1, 0);
/*  50:    */   
/*  51:    */   public ConnectionViewer()
/*  52:    */   {
/*  53: 78 */     setBackground(Color.WHITE);
/*  54: 79 */     setOpaque(true);
/*  55: 80 */     addMouseListener(new BoxIdentifier(null));
/*  56: 81 */     this.circleDiameter = (2 * this.circleRadius);
/*  57: 82 */     addMouseListener(this);
/*  58: 83 */     addMouseMotionListener(this);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public ViewerBox getBox(MouseEvent e)
/*  62:    */   {
/*  63: 87 */     int x = (int)((e.getX() - this.offsetX) / this.multiplier);
/*  64: 88 */     int y = (int)((e.getY() - this.offsetY) / this.multiplier);
/*  65: 89 */     for (ViewerBox box : this.boxes) {
/*  66: 90 */       if ((x >= box.getX()) && (x <= box.getX() + box.getWidth()) && (y >= box.getY()) && (y <= box.getY() + box.getHeight())) {
/*  67: 91 */         return box;
/*  68:    */       }
/*  69:    */     }
/*  70: 94 */     return null;
/*  71:    */   }
/*  72:    */   
/*  73:    */   private class BoxIdentifier
/*  74:    */     extends MouseAdapter
/*  75:    */   {
/*  76:    */     private BoxIdentifier() {}
/*  77:    */     
/*  78:    */     public void mouseClicked(MouseEvent e)
/*  79:    */     {
/*  80: 99 */       ViewerBox box = ConnectionViewer.this.getBox(e);
/*  81:100 */       if (box == null)
/*  82:    */       {
/*  83:101 */         if (e.getButton() == 1) {
/*  84:102 */           ConnectionViewer.this.getSlider().setValue(120 * ConnectionViewer.this.getSlider().getValue() / 100);
/*  85:104 */         } else if (e.getButton() == 3) {
/*  86:105 */           ConnectionViewer.this.getSlider().setValue(80 * ConnectionViewer.this.getSlider().getValue() / 100);
/*  87:    */         }
/*  88:    */       }
/*  89:109 */       else if ((e.isControlDown()) && (e.getButton() == 1) && ((box.getSource() instanceof WiredOnOffSwitch)))
/*  90:    */       {
/*  91:110 */         WiredOnOffSwitch wiredOnOffSwitch = (WiredOnOffSwitch)box.getSource();
/*  92:111 */         wiredOnOffSwitch.setSelected(!wiredOnOffSwitch.isSelected());
/*  93:    */       }
/*  94:113 */       else if (e.getButton() == 3)
/*  95:    */       {
/*  96:114 */         String message = "Class is " + box.getSourceClass().getSimpleName();
/*  97:115 */         JOptionPane.showMessageDialog(ConnectionViewer.this, message);
/*  98:    */       }
/*  99:117 */       else if (e.getButton() == 1)
/* 100:    */       {
/* 101:118 */         box.setSelected(!box.isSelected());
/* 102:119 */         ConnectionViewer.this.revalidate();
/* 103:120 */         ConnectionViewer.this.repaint();
/* 104:    */       }
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void addWire(ViewerWire viewerWire)
/* 109:    */   {
/* 110:127 */     this.wires.add(viewerWire);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void addBox(ViewerBox viewerBox)
/* 114:    */   {
/* 115:131 */     this.boxes.add(viewerBox);
/* 116:132 */     this.maxWidth = Math.max(this.maxWidth, viewerBox.getX() + viewerBox.getWidth());
/* 117:133 */     this.maxHeight = Math.max(this.maxHeight, viewerBox.getY() + viewerBox.getHeight());
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Dimension getPreferredSize()
/* 121:    */   {
/* 122:137 */     if (getSlider() != null)
/* 123:    */     {
/* 124:138 */       int scale = getSlider().getValue();
/* 125:139 */       return new Dimension(this.maxWidth * scale / 100, this.maxHeight * scale / 100);
/* 126:    */     }
/* 127:141 */     return super.getPreferredSize();
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void paintComponent(Graphics x)
/* 131:    */   {
/* 132:145 */     super.paintComponent(x);
/* 133:    */     
/* 134:147 */     Graphics2D g = (Graphics2D)x;
/* 135:148 */     int width = getWidth();
/* 136:149 */     int height = getHeight();
/* 137:150 */     g.setStroke(new BasicStroke(3.0F));
/* 138:    */     
/* 139:152 */     AffineTransform transform = g.getTransform();
/* 140:153 */     g.translate(this.offsetX, this.offsetY);
/* 141:154 */     g.scale(this.multiplier, this.multiplier);
/* 142:    */     try
/* 143:    */     {
/* 144:157 */       for (ViewerBox viewerBox : this.boxes) {
/* 145:158 */         if (viewerBox.isVisible()) {
/* 146:159 */           drawBox(g, viewerBox);
/* 147:    */         }
/* 148:    */       }
/* 149:163 */       for (ViewerWire viewerWire : this.wires) {
/* 150:164 */         if (viewerWire.isVisible()) {
/* 151:165 */           drawWire(g, viewerWire, false);
/* 152:    */         }
/* 153:    */       }
/* 154:168 */       for (ViewerWire viewerWire : this.wires) {
/* 155:169 */         if (viewerWire.isVisible()) {
/* 156:170 */           drawWire(g, viewerWire, true);
/* 157:    */         }
/* 158:    */       }
/* 159:    */     }
/* 160:    */     catch (Exception localException) {}
/* 161:177 */     if (this.mouseDown)
/* 162:    */     {
/* 163:178 */       g.setTransform(transform);
/* 164:179 */       drawCross(x, width, height);
/* 165:    */     }
/* 166:    */   }
/* 167:    */   
/* 168:    */   private void drawCross(Graphics g, int width, int height)
/* 169:    */   {
/* 170:185 */     int r = 5;
/* 171:186 */     int w = width / 2;
/* 172:187 */     int h = height / 2;
/* 173:188 */     g.setColor(Color.RED);
/* 174:    */     
/* 175:190 */     g.drawLine(w - r, h, w + r, h);
/* 176:191 */     g.drawLine(w, h - 4, w, h + r);
/* 177:192 */     g.setColor(Color.BLACK);
/* 178:    */   }
/* 179:    */   
/* 180:    */   private void drawBox(Graphics2D g, ViewerBox viewerBox)
/* 181:    */   {
/* 182:197 */     String label = viewerBox.getText();
/* 183:198 */     int x = viewerBox.getX();
/* 184:199 */     int y = viewerBox.getY();
/* 185:200 */     int w = viewerBox.getWidth();
/* 186:201 */     int h = viewerBox.getHeight();
/* 187:202 */     if (viewerBox.getState() == 2) {
/* 188:203 */       g.setColor(Color.RED);
/* 189:205 */     } else if (viewerBox.isSelected()) {
/* 190:206 */       g.setColor(Color.PINK);
/* 191:    */     } else {
/* 192:209 */       g.setColor(viewerBox.getColor());
/* 193:    */     }
/* 194:211 */     if (viewerBox.getSwitchState() == ViewerBox.NEITHER) {
/* 195:212 */       g.fillRect(x, y, w, h);
/* 196:    */     } else {
/* 197:215 */       g.fillOval(x, y, w, h);
/* 198:    */     }
/* 199:218 */     g.setColor(Color.BLACK);
/* 200:219 */     Rectangle rectangle = new Rectangle(x, y, w, h);
/* 201:220 */     Font font = g.getFont();
/* 202:221 */     g.setFont(new Font(font.getName(), 1, font.getSize() + 5));
/* 203:222 */     drawLabel(g, label, rectangle);
/* 204:223 */     g.setFont(font);
/* 205:224 */     if (viewerBox.isToggleSwitch())
/* 206:    */     {
/* 207:225 */       Stroke handle = g.getStroke();
/* 208:226 */       g.setStroke(this.cross);
/* 209:227 */       g.setColor(Color.RED);
/* 210:228 */       int xOffset = (int)(0.5D * w);
/* 211:229 */       int headXOffset = (int)(0.15D * w);
/* 212:230 */       int headYOffset = (int)(0.2D * h);
/* 213:231 */       int yOffset = (int)(0.05D * h);
/* 214:232 */       g.drawLine(x + xOffset, y + yOffset, x + xOffset, y + h - yOffset);
/* 215:233 */       if (viewerBox.getSwitchState() == ViewerBox.ON_SWITCH)
/* 216:    */       {
/* 217:234 */         g.drawLine(x + xOffset, y + yOffset, x + xOffset + headXOffset, y + headYOffset);
/* 218:235 */         g.drawLine(x + xOffset, y + yOffset, x + xOffset - headXOffset, y + headYOffset);
/* 219:    */       }
/* 220:    */       else
/* 221:    */       {
/* 222:238 */         g.drawLine(x + xOffset, y + h - yOffset, x + xOffset + headXOffset, y + h - yOffset - headYOffset);
/* 223:239 */         g.drawLine(x + xOffset, y + h - yOffset, x + xOffset - headXOffset, y + h - yOffset - headYOffset);
/* 224:    */       }
/* 225:241 */       g.setStroke(handle);
/* 226:    */     }
/* 227:243 */     else if (viewerBox.getSwitchState() == ViewerBox.OFF_SWITCH)
/* 228:    */     {
/* 229:244 */       Stroke handle = g.getStroke();
/* 230:245 */       g.setStroke(this.cross);
/* 231:246 */       g.setColor(Color.RED);
/* 232:247 */       int yOffset = (int)(0.15D * h);
/* 233:248 */       int y2Offset = yOffset;
/* 234:249 */       int xOffset = (int)(0.15D * w);
/* 235:250 */       int x2Offset = xOffset;
/* 236:251 */       g.drawLine(x + xOffset, y + yOffset, x + w - x2Offset, y + h - y2Offset);
/* 237:252 */       g.drawLine(x + xOffset, y + h - yOffset, x + w - x2Offset, y + y2Offset);
/* 238:253 */       g.setStroke(handle);
/* 239:    */     }
/* 240:255 */     else if (viewerBox.getSwitchState() == ViewerBox.ON_SWITCH)
/* 241:    */     {
/* 242:256 */       Stroke handle = g.getStroke();
/* 243:257 */       g.setStroke(this.cross);
/* 244:258 */       g.setColor(Color.RED);
/* 245:259 */       int yOffset = (int)(0.75D * h);
/* 246:260 */       int xOffset = (int)(0.15D * w);
/* 247:    */       
/* 248:262 */       int headXOffset = (int)(0.2D * w);
/* 249:263 */       int headYOffset = (int)(0.15D * h);
/* 250:    */       
/* 251:265 */       g.drawLine(x + xOffset, y + yOffset, x + w - xOffset, y + yOffset);
/* 252:266 */       g.drawLine(x + w - xOffset, y + yOffset, x + w - xOffset - headXOffset, y + yOffset + headYOffset);
/* 253:267 */       g.drawLine(x + w - xOffset, y + yOffset, x + w - xOffset - headXOffset, y + yOffset - headYOffset);
/* 254:268 */       g.setStroke(handle);
/* 255:    */     }
/* 256:271 */     g.setColor(Color.BLACK);
/* 257:273 */     if (viewerBox.isNegative()) {
/* 258:276 */       g.setColor(Color.RED);
/* 259:    */     }
/* 260:280 */     if (viewerBox.getSwitchState() == ViewerBox.NEITHER)
/* 261:    */     {
/* 262:281 */       if (viewerBox.isDotted())
/* 263:    */       {
/* 264:282 */         Stroke handle = g.getStroke();
/* 265:283 */         g.setStroke(this.dashed);
/* 266:284 */         g.drawRect(x, y, w, h);
/* 267:285 */         g.setStroke(handle);
/* 268:    */       }
/* 269:    */       else
/* 270:    */       {
/* 271:288 */         g.drawRect(x, y, w, h);
/* 272:    */       }
/* 273:    */     }
/* 274:    */     else {
/* 275:292 */       g.drawOval(x, y, w, h);
/* 276:    */     }
/* 277:    */   }
/* 278:    */   
/* 279:    */   private void drawLabel(Graphics g, String label, Rectangle rectangle)
/* 280:    */   {
/* 281:297 */     String[] words = Pattern.compile(" ").split(label);
/* 282:298 */     FontMetrics fm = g.getFontMetrics();
/* 283:299 */     int width = rectangle.width;
/* 284:300 */     ArrayList<String> result = new ArrayList();
/* 285:301 */     String row = "";
/* 286:302 */     int spaceWidth = fm.stringWidth(" ");
/* 287:303 */     int maxWidth = 0;
/* 288:304 */     for (String word : words)
/* 289:    */     {
/* 290:305 */       int rowWidth = fm.stringWidth(row);
/* 291:306 */       int wordWidth = fm.stringWidth(word);
/* 292:307 */       if (rowWidth == 0)
/* 293:    */       {
/* 294:308 */         row = word;
/* 295:    */       }
/* 296:310 */       else if (rowWidth + spaceWidth + wordWidth < width)
/* 297:    */       {
/* 298:311 */         row = row + " " + word;
/* 299:    */       }
/* 300:    */       else
/* 301:    */       {
/* 302:314 */         result.add(row);
/* 303:315 */         int thisWidth = fm.stringWidth(row);
/* 304:316 */         if (thisWidth > maxWidth) {
/* 305:317 */           maxWidth = thisWidth;
/* 306:    */         }
/* 307:319 */         row = word;
/* 308:    */       }
/* 309:    */     }
/* 310:322 */     if (!row.isEmpty()) {
/* 311:323 */       result.add(row);
/* 312:    */     }
/* 313:325 */     int lineCount = result.size();
/* 314:326 */     int lineHeight = g.getFontMetrics().getHeight();
/* 315:327 */     int height = lineCount * lineHeight;
/* 316:329 */     if ((maxWidth > rectangle.width - 4) || (height > rectangle.height - 4))
/* 317:    */     {
/* 318:330 */       Font font = g.getFont();
/* 319:331 */       g.setFont(new Font(font.getName(), 1, font.getSize() - 1));
/* 320:332 */       drawLabel(g, label, rectangle);
/* 321:    */     }
/* 322:    */     else
/* 323:    */     {
/* 324:335 */       lineHeight = g.getFontMetrics().getHeight();
/* 325:336 */       int heightOffset = (lineCount - 1) * lineHeight / 2;
/* 326:337 */       for (int i = 0; i < lineCount; i++)
/* 327:    */       {
/* 328:338 */         String line = (String)result.get(i);
/* 329:339 */         int stringWidth = g.getFontMetrics().stringWidth(line);
/* 330:340 */         int x = rectangle.x + rectangle.width / 2 - stringWidth / 2;
/* 331:341 */         int y = rectangle.y + rectangle.height / 2;
/* 332:    */         
/* 333:343 */         g.drawString(line, x, y - heightOffset + i * lineHeight);
/* 334:    */       }
/* 335:    */     }
/* 336:    */   }
/* 337:    */   
/* 338:    */   private void drawWire(Graphics2D g, ViewerWire viewerWire, boolean drawIfSpecial)
/* 339:    */   {
/* 340:349 */     ViewerBox source = viewerWire.getSource();
/* 341:350 */     ViewerBox target = viewerWire.getTarget();
/* 342:351 */     int destinationIndex = viewerWire.getDestinationIndex();
/* 343:352 */     int destinationCount = viewerWire.getDestinationCount();
/* 344:    */     
/* 345:    */ 
/* 346:    */ 
/* 347:    */ 
/* 348:    */ 
/* 349:    */ 
/* 350:    */ 
/* 351:    */ 
/* 352:    */ 
/* 353:    */ 
/* 354:363 */     Stroke handle = g.getStroke();
/* 355:    */     
/* 356:365 */     boolean special = false;
/* 357:367 */     if (viewerWire.isDashed()) {
/* 358:368 */       g.setColor(Color.ORANGE);
/* 359:    */     }
/* 360:373 */     if (viewerWire.getColor() != null)
/* 361:    */     {
/* 362:374 */       g.setColor(viewerWire.getColor());
/* 363:375 */       special = true;
/* 364:    */     }
/* 365:377 */     else if ((source.isSelected()) && (target.isSelected()))
/* 366:    */     {
/* 367:378 */       g.setColor(Color.ORANGE);
/* 368:379 */       special = true;
/* 369:    */     }
/* 370:381 */     else if (viewerWire.getPermanentColor() != null)
/* 371:    */     {
/* 372:382 */       g.setColor(viewerWire.getPermanentColor());
/* 373:    */     }
/* 374:384 */     else if (source.isSelected())
/* 375:    */     {
/* 376:385 */       g.setColor(Color.BLUE);
/* 377:386 */       special = true;
/* 378:    */     }
/* 379:388 */     else if (target.isSelected())
/* 380:    */     {
/* 381:389 */       g.setColor(Color.MAGENTA);
/* 382:390 */       special = true;
/* 383:    */     }
/* 384:392 */     if ((!source.isSelected()) && (!target.isSelected())) {
/* 385:392 */       viewerWire.getColor();
/* 386:    */     }
/* 387:395 */     if (viewerWire.isDotted())
/* 388:    */     {
/* 389:396 */       g.setColor(Color.CYAN);
/* 390:397 */       g.setStroke(this.dotted);
/* 391:398 */       special = true;
/* 392:    */     }
/* 393:401 */     if (special != drawIfSpecial)
/* 394:    */     {
/* 395:402 */       g.setColor(Color.BLACK);
/* 396:403 */       g.setStroke(handle);
/* 397:404 */       return;
/* 398:    */     }
/* 399:407 */     int sourceX = source.getX() + source.getWidth();
/* 400:408 */     int targetX = target.getX();
/* 401:409 */     int sourceY = source.getY() + source.getHeight() / 2;
/* 402:410 */     int targetY = target.getY() + destinationIndex * target.getHeight() / (destinationCount + 1);
/* 403:    */     
/* 404:    */ 
/* 405:    */ 
/* 406:414 */     g.fillRect(targetX - this.circleRadius, targetY - this.circleRadius, this.circleDiameter, this.circleDiameter);
/* 407:415 */     if ((targetX > sourceX) && (targetX - sourceX < 2 * target.getDeltaX()))
/* 408:    */     {
/* 409:418 */       g.drawLine(sourceX, sourceY, targetX, targetY);
/* 410:419 */       g.fillRect(sourceX - this.circleRadius, sourceY - this.circleRadius, this.circleDiameter, this.circleDiameter);
/* 411:    */     }
/* 412:    */     else
/* 413:    */     {
/* 414:423 */       int y1 = source.getY();
/* 415:424 */       int x1 = source.getX() + source.getWidth() * 3 / 4;
/* 416:426 */       if (source.getY() < target.getY()) {
/* 417:428 */         y1 += source.getHeight();
/* 418:    */       }
/* 419:431 */       int x2 = x1;
/* 420:432 */       int y2 = y1;
/* 421:433 */       if (source.getY() < target.getY()) {
/* 422:434 */         y2 += source.getDeltaY() / 2;
/* 423:    */       } else {
/* 424:437 */         y2 -= source.getDeltaY() / 2;
/* 425:    */       }
/* 426:439 */       g.drawLine(x1, y1, x2, y2);
/* 427:440 */       g.fillOval(x1 - this.circleRadius, y1 - this.circleRadius, this.circleDiameter, this.circleDiameter);
/* 428:    */       
/* 429:442 */       int x3 = target.getX() - target.getDeltaX() / 4;
/* 430:443 */       int y3 = y2;
/* 431:444 */       g.drawLine(x2, y2, x3, y3);
/* 432:445 */       g.drawLine(x3, y3, targetX, targetY);
/* 433:    */     }
/* 434:447 */     g.setColor(Color.black);
/* 435:448 */     g.setStroke(handle);
/* 436:    */   }
/* 437:    */   
/* 438:    */   public void update(Observable o, Object arg)
/* 439:    */   {
/* 440:453 */     if ("Redo" == arg) {
/* 441:454 */       reconfigure(true);
/* 442:    */     } else {
/* 443:457 */       repaint();
/* 444:    */     }
/* 445:    */   }
/* 446:    */   
/* 447:    */   private void reconfigure(boolean withOffsets)
/* 448:    */   {
/* 449:462 */     int width = getWidth();
/* 450:463 */     int height = getHeight();
/* 451:464 */     if ((this.maxWidth == 0) || (this.maxHeight == 0)) {
/* 452:465 */       return;
/* 453:    */     }
/* 454:467 */     double scaleX = 1.0D * width / this.maxWidth;
/* 455:468 */     double scaleY = 1.0D * height / this.maxHeight;
/* 456:469 */     this.multiplier = (Math.min(scaleY, scaleX) * 0.9D * getSlider().getValue() / 100.0D);
/* 457:470 */     if (withOffsets)
/* 458:    */     {
/* 459:471 */       this.offsetX = ((width - this.maxWidth * this.multiplier) / 2.0D);
/* 460:472 */       this.offsetY = ((height - this.maxHeight * this.multiplier) / 2.0D);
/* 461:    */     }
/* 462:474 */     repaint();
/* 463:    */   }
/* 464:    */   
/* 465:    */   public static void main(String[] args)
/* 466:    */   {
/* 467:478 */     Test.main(args);
/* 468:    */   }
/* 469:    */   
/* 470:    */   public static void viewNetwork()
/* 471:    */   {
/* 472:486 */     JFrame frame = new JFrame();
/* 473:487 */     ConnectionViewer viewer = Adapter.makeConnectionAdapter().getViewer();
/* 474:488 */     frame.getContentPane().add(new JScrollPane(viewer), "Center");
/* 475:489 */     frame.setBounds(0, 0, 800, 800);
/* 476:490 */     frame.setVisible(true);
/* 477:    */   }
/* 478:    */   
/* 479:    */   public void clear()
/* 480:    */   {
/* 481:494 */     this.boxes.clear();
/* 482:495 */     this.wires.clear();
/* 483:496 */     this.maxHeight = 0;
/* 484:497 */     this.maxWidth = 0;
/* 485:498 */     this.multiplier = 1.0D;
/* 486:499 */     this.offsetX = 0.0D;
/* 487:500 */     this.offsetY = 0.0D;
/* 488:501 */     getSlider().setValue(100);
/* 489:    */   }
/* 490:    */   
/* 491:    */   private class ScaleListener
/* 492:    */     implements ChangeListener
/* 493:    */   {
/* 494:    */     private ScaleListener() {}
/* 495:    */     
/* 496:    */     public void stateChanged(ChangeEvent e)
/* 497:    */     {
/* 498:506 */       ConnectionViewer.this.scaleChanged(e);
/* 499:507 */       ConnectionViewer.this.reconfigure(false);
/* 500:    */     }
/* 501:    */   }
/* 502:    */   
/* 503:    */   private void scaleChanged(ChangeEvent e)
/* 504:    */   {
/* 505:513 */     int newSliderValue = getSlider().getValue();
/* 506:514 */     int halfWidth = getWidth() / 2;
/* 507:515 */     int halfHeight = getHeight() / 2;
/* 508:516 */     this.offsetX = (newSliderValue * (this.offsetX - halfWidth) / this.oldSliderValue + halfWidth);
/* 509:517 */     this.offsetY = (newSliderValue * (this.offsetY - halfHeight) / this.oldSliderValue + halfHeight);
/* 510:518 */     this.oldSliderValue = newSliderValue;
/* 511:    */   }
/* 512:    */   
/* 513:    */   public JSlider getSlider()
/* 514:    */   {
/* 515:522 */     if (this.slider == null)
/* 516:    */     {
/* 517:523 */       this.slider = new JSlider(20, 800, this.oldSliderValue);
/* 518:524 */       this.slider.setMajorTickSpacing(20);
/* 519:525 */       this.slider.setPaintTicks(true);
/* 520:526 */       this.slider.setPaintLabels(true);
/* 521:527 */       this.slider.setBackground(Color.WHITE);
/* 522:528 */       this.slider.addChangeListener(new ScaleListener(null));
/* 523:    */     }
/* 524:530 */     return this.slider;
/* 525:    */   }
/* 526:    */   
/* 527:548 */   private int pressedAtX = -1;
/* 528:550 */   private int pressedAtY = -1;
/* 529:552 */   private double offsetXWhenPressed = -1.0D;
/* 530:554 */   private double offsetYWhenPressed = -1.0D;
/* 531:    */   private boolean mouseDown;
/* 532:    */   
/* 533:    */   public void mouseClicked(MouseEvent e) {}
/* 534:    */   
/* 535:    */   public void mouseEntered(MouseEvent e) {}
/* 536:    */   
/* 537:    */   public void mouseExited(MouseEvent e) {}
/* 538:    */   
/* 539:    */   public void mousePressed(MouseEvent e)
/* 540:    */   {
/* 541:560 */     this.pressedAtX = e.getX();
/* 542:561 */     this.pressedAtY = e.getY();
/* 543:562 */     this.offsetXWhenPressed = this.offsetX;
/* 544:563 */     this.offsetYWhenPressed = this.offsetY;
/* 545:564 */     this.mouseDown = true;
/* 546:    */   }
/* 547:    */   
/* 548:    */   public void mouseReleased(MouseEvent e)
/* 549:    */   {
/* 550:570 */     this.pressedAtX = -1;
/* 551:571 */     this.pressedAtY = -1;
/* 552:572 */     this.offsetXWhenPressed = -1.0D;
/* 553:573 */     this.offsetYWhenPressed = -1.0D;
/* 554:574 */     this.mouseDown = false;
/* 555:575 */     repaint();
/* 556:    */   }
/* 557:    */   
/* 558:    */   public void mouseDragged(MouseEvent e)
/* 559:    */   {
/* 560:580 */     if ((this.pressedAtX != -1) && (this.pressedAtY != -1))
/* 561:    */     {
/* 562:581 */       setOffsetX(e.getX() - this.pressedAtX + this.offsetXWhenPressed);
/* 563:582 */       setOffsetY(e.getY() - this.pressedAtY + this.offsetYWhenPressed);
/* 564:    */     }
/* 565:    */   }
/* 566:    */   
/* 567:    */   public void setOffsetX(double offsetX)
/* 568:    */   {
/* 569:587 */     this.offsetX = offsetX;
/* 570:588 */     repaint();
/* 571:    */   }
/* 572:    */   
/* 573:    */   public void setOffsetY(double offsetY)
/* 574:    */   {
/* 575:592 */     this.offsetY = offsetY;
/* 576:593 */     repaint();
/* 577:    */   }
/* 578:    */   
/* 579:    */   public void mouseMoved(MouseEvent e) {}
/* 580:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     connections.views.ConnectionViewer
 * JD-Core Version:    0.7.0.1
 */