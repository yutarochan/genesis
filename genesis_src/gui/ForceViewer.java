/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Relation;
/*   5:    */ import frames.ForceFrame;
/*   6:    */ import java.awt.Color;
/*   7:    */ import java.awt.Container;
/*   8:    */ import java.awt.Font;
/*   9:    */ import java.awt.FontMetrics;
/*  10:    */ import java.awt.Graphics;
/*  11:    */ import java.awt.Graphics2D;
/*  12:    */ import java.awt.geom.AffineTransform;
/*  13:    */ import java.io.PrintStream;
/*  14:    */ import java.util.HashMap;
/*  15:    */ import javax.swing.JFrame;
/*  16:    */ 
/*  17:    */ public class ForceViewer
/*  18:    */   extends NegatableJPanel
/*  19:    */ {
/*  20: 18 */   private String agonistName = "";
/*  21: 19 */   private String antagonistName = "";
/*  22: 20 */   private boolean circleOnLeft = true;
/*  23: 21 */   private boolean arrowheadInCircle = true;
/*  24: 22 */   private boolean plusInRectangle = true;
/*  25: 23 */   private boolean simple = true;
/*  26: 24 */   private boolean antQuestion = false;
/*  27: 25 */   private boolean agoQuestion = false;
/*  28: 26 */   int DOT = 0;
/*  29: 26 */   int ARROW = 1;
/*  30: 26 */   int QUESTION = 2;
/*  31: 27 */   int lineMode = this.DOT;
/*  32: 28 */   private boolean viewable = false;
/*  33: 30 */   private int diameter = 80;
/*  34:    */   
/*  35:    */   public void setAgonistName(String name)
/*  36:    */   {
/*  37: 33 */     this.agonistName = name;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setAntagonistName(String name)
/*  41:    */   {
/*  42: 37 */     this.antagonistName = name;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String getAgonistName()
/*  46:    */   {
/*  47: 41 */     return this.agonistName;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String getAntagonistName()
/*  51:    */   {
/*  52: 45 */     return this.antagonistName;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void unSetAgoQuestion()
/*  56:    */   {
/*  57: 49 */     this.agoQuestion = false;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void unSetAntQuestion()
/*  61:    */   {
/*  62: 52 */     this.antQuestion = false;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setAgoQuestion()
/*  66:    */   {
/*  67: 56 */     this.agoQuestion = true;
/*  68: 57 */     this.circleOnLeft = true;
/*  69: 58 */     setLineMode();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setAntQuestion()
/*  73:    */   {
/*  74: 62 */     this.antQuestion = true;
/*  75: 63 */     this.circleOnLeft = true;
/*  76: 64 */     setLineMode();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setArrow()
/*  80:    */   {
/*  81: 68 */     this.circleOnLeft = true;
/*  82: 69 */     this.arrowheadInCircle = true;
/*  83: 70 */     setLineMode();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setDot()
/*  87:    */   {
/*  88: 74 */     this.circleOnLeft = false;
/*  89: 75 */     this.arrowheadInCircle = false;
/*  90: 76 */     setLineMode();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setPlus()
/*  94:    */   {
/*  95: 80 */     this.plusInRectangle = true;
/*  96:    */     
/*  97: 82 */     setLineMode();
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setMinus()
/* 101:    */   {
/* 102: 86 */     this.plusInRectangle = false;
/* 103:    */     
/* 104: 88 */     setLineMode();
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setSimple()
/* 108:    */   {
/* 109: 92 */     this.simple = true;
/* 110: 93 */     setLineMode();
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setCompound()
/* 114:    */   {
/* 115: 97 */     this.simple = false;
/* 116: 98 */     setLineMode();
/* 117:    */   }
/* 118:    */   
/* 119:    */   private void setLineMode()
/* 120:    */   {
/* 121:102 */     if ((this.antQuestion) || (this.agoQuestion)) {
/* 122:103 */       this.lineMode = this.QUESTION;
/* 123:105 */     } else if (this.plusInRectangle)
/* 124:    */     {
/* 125:106 */       if (this.simple)
/* 126:    */       {
/* 127:107 */         if (this.arrowheadInCircle) {
/* 128:108 */           this.lineMode = this.DOT;
/* 129:    */         } else {
/* 130:111 */           this.lineMode = this.ARROW;
/* 131:    */         }
/* 132:    */       }
/* 133:115 */       else if (this.arrowheadInCircle) {
/* 134:116 */         this.lineMode = this.DOT;
/* 135:    */       } else {
/* 136:119 */         this.lineMode = this.ARROW;
/* 137:    */       }
/* 138:    */     }
/* 139:124 */     else if (this.simple)
/* 140:    */     {
/* 141:125 */       if (this.arrowheadInCircle) {
/* 142:126 */         this.lineMode = this.ARROW;
/* 143:    */       } else {
/* 144:129 */         this.lineMode = this.DOT;
/* 145:    */       }
/* 146:    */     }
/* 147:133 */     else if (this.arrowheadInCircle) {
/* 148:134 */       this.lineMode = this.ARROW;
/* 149:    */     } else {
/* 150:137 */       this.lineMode = this.DOT;
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   public ForceViewer()
/* 155:    */   {
/* 156:146 */     setOpaque(true);
/* 157:147 */     setBackground(Color.WHITE);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void paintComponent(Graphics x)
/* 161:    */   {
/* 162:152 */     super.paintComponent(x);
/* 163:    */     
/* 164:    */ 
/* 165:155 */     Graphics2D g = (Graphics2D)x;
/* 166:156 */     int width = getWidth();
/* 167:157 */     int height = getHeight();
/* 168:158 */     if ((width == 0) || (height == 0)) {
/* 169:158 */       return;
/* 170:    */     }
/* 171:160 */     g.drawRect(0, 0, width - 1, height - 1);
/* 172:161 */     if (!isViewable()) {
/* 173:161 */       return;
/* 174:    */     }
/* 175:162 */     int offsetX = 0;
/* 176:163 */     int offsetY = 5;
/* 177:164 */     int circleX = offsetX;
/* 178:165 */     int separation = 10;
/* 179:166 */     int rectX = circleX + this.diameter + separation;
/* 180:168 */     if (!this.circleOnLeft)
/* 181:    */     {
/* 182:169 */       rectX = offsetX;
/* 183:170 */       circleX = rectX + separation + this.diameter / 3;
/* 184:    */     }
/* 185:173 */     int designSize = 160;
/* 186:174 */     double multiplier = 1.0D;
/* 187:175 */     double tX = 0.0D;
/* 188:176 */     double tY = 0.0D;
/* 189:177 */     if (height > width) {
/* 190:178 */       multiplier = width / designSize;
/* 191:    */     } else {
/* 192:181 */       multiplier = height / designSize;
/* 193:    */     }
/* 194:185 */     tY = height - multiplier * (this.diameter + 2 * separation);
/* 195:186 */     tY /= 2.0D;
/* 196:    */     
/* 197:188 */     tX = width - multiplier * (this.diameter + separation + this.diameter / 3);
/* 198:189 */     tX /= 2.0D;
/* 199:    */     
/* 200:    */ 
/* 201:    */ 
/* 202:    */ 
/* 203:    */ 
/* 204:    */ 
/* 205:    */ 
/* 206:197 */     AffineTransform transform = g.getTransform();
/* 207:198 */     transform.translate(tX, tY);
/* 208:199 */     transform.scale(multiplier, multiplier);
/* 209:    */     
/* 210:201 */     g.setTransform(transform);
/* 211:    */     
/* 212:203 */     Font font = new Font(null, 0, 25);
/* 213:204 */     g.setFont(font);
/* 214:205 */     FontMetrics fm = g.getFontMetrics();
/* 215:206 */     g.drawString(getAntagonistName(), rectX + (this.diameter / 3 - fm.stringWidth(getAntagonistName())) / 2, 0);
/* 216:207 */     g.drawString(getAgonistName(), circleX + (this.diameter - fm.stringWidth(getAgonistName())) / 2, 0);
/* 217:    */     
/* 218:209 */     g.drawOval(circleX, offsetY, this.diameter, this.diameter);
/* 219:210 */     g.drawRect(rectX, offsetY, this.diameter / 3, this.diameter);
/* 220:211 */     int lineY = offsetY + this.diameter + 2 * separation;
/* 221:212 */     int lineWidth = this.diameter + separation + this.diameter / 3;
/* 222:213 */     g.drawLine(offsetX, lineY, offsetX + lineWidth, lineY);
/* 223:    */     
/* 224:215 */     int circleCenterX = circleX + this.diameter / 2;
/* 225:216 */     int circleCenterY = offsetY + this.diameter / 2;
/* 226:218 */     if (this.agoQuestion) {
/* 227:219 */       g.drawString("?", circleCenterX - fm.stringWidth("?") / 2, circleCenterY + fm.getAscent() / 2);
/* 228:221 */     } else if (this.arrowheadInCircle) {
/* 229:222 */       drawArrowhead(g, circleCenterX, circleCenterY);
/* 230:    */     } else {
/* 231:225 */       drawDot(g, circleCenterX, circleCenterY);
/* 232:    */     }
/* 233:228 */     int midLine = (offsetX + lineWidth) / 2;
/* 234:230 */     if (this.lineMode == this.DOT)
/* 235:    */     {
/* 236:231 */       drawDot(g, midLine, lineY);
/* 237:    */     }
/* 238:233 */     else if (this.lineMode == this.ARROW)
/* 239:    */     {
/* 240:234 */       drawArrowhead(g, midLine, lineY);
/* 241:    */     }
/* 242:236 */     else if (this.lineMode == this.ARROW)
/* 243:    */     {
/* 244:237 */       drawArrowhead(g, midLine + 10, lineY);
/* 245:238 */       drawDot(g, midLine - 10, lineY);
/* 246:239 */       drawSlash(g, midLine, lineY);
/* 247:    */     }
/* 248:241 */     else if (this.lineMode == this.DOT)
/* 249:    */     {
/* 250:242 */       drawDot(g, midLine + 10, lineY);
/* 251:243 */       drawArrowhead(g, midLine - 10, lineY);
/* 252:244 */       drawSlash(g, midLine, lineY);
/* 253:    */     }
/* 254:246 */     else if (this.lineMode == this.QUESTION)
/* 255:    */     {
/* 256:247 */       g.drawString("?", midLine, lineY - 2);
/* 257:    */     }
/* 258:250 */     if (this.antQuestion) {
/* 259:251 */       g.drawString("?", rectX + this.diameter / 6, offsetY + 10 + fm.getAscent());
/* 260:253 */     } else if (this.plusInRectangle) {
/* 261:254 */       drawPlusOrMinus(g, rectX + this.diameter / 6, offsetY + 10, true);
/* 262:    */     } else {
/* 263:257 */       drawPlusOrMinus(g, rectX + this.diameter / 6, offsetY + 10, false);
/* 264:    */     }
/* 265:    */   }
/* 266:    */   
/* 267:    */   private void drawPlusOrMinus(Graphics g, int x, int y, boolean plus)
/* 268:    */   {
/* 269:263 */     g.drawLine(x - 5, y, x + 5, y);
/* 270:264 */     if (plus) {
/* 271:265 */       g.drawLine(x, y - 5, x, y + 5);
/* 272:    */     }
/* 273:    */   }
/* 274:    */   
/* 275:    */   private void drawDot(Graphics g, int x, int y)
/* 276:    */   {
/* 277:270 */     int radius = 6;
/* 278:271 */     g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
/* 279:    */   }
/* 280:    */   
/* 281:    */   private void drawArrowhead(Graphics g, int x, int y)
/* 282:    */   {
/* 283:275 */     int arrowheadHeight = 5;
/* 284:276 */     int arrowheadWidth = 8;
/* 285:277 */     g.drawLine(x - arrowheadWidth / 2, y - arrowheadHeight, x + arrowheadWidth / 2, y);
/* 286:278 */     g.drawLine(x - arrowheadWidth / 2, y + arrowheadHeight, x + arrowheadWidth / 2, y);
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void drawSlash(Graphics g, int x, int y)
/* 290:    */   {
/* 291:282 */     g.drawLine(x + 5, y - 8, x - 5, y + 8);
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void clearData()
/* 295:    */   {
/* 296:286 */     setViewable(false);
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void setParameters(ForceFrame frame)
/* 300:    */   {
/* 301:290 */     Relation thing = (Relation)frame.getThing();
/* 302:291 */     setParameters(thing);
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setParameters(Relation thing)
/* 306:    */   {
/* 307:305 */     setAgonistName(ForceFrame.getAgonistName(thing));
/* 308:306 */     setAntagonistName(ForceFrame.getAntagonistName(thing));
/* 309:307 */     unSetAgoQuestion();
/* 310:308 */     unSetAntQuestion();
/* 311:310 */     if (ForceFrame.getAgonistTendency(thing).equals("active")) {
/* 312:311 */       setArrow();
/* 313:312 */     } else if (ForceFrame.getAgonistTendency(thing).equals("unknown")) {
/* 314:313 */       setAgoQuestion();
/* 315:    */     } else {
/* 316:315 */       setDot();
/* 317:    */     }
/* 318:318 */     if (ForceFrame.getAntagonistStrength(thing).equals("strong"))
/* 319:    */     {
/* 320:319 */       setSimple();
/* 321:320 */       setPlus();
/* 322:    */     }
/* 323:321 */     else if (ForceFrame.getAntagonistStrength(thing).equals("weak"))
/* 324:    */     {
/* 325:322 */       setSimple();
/* 326:323 */       setMinus();
/* 327:    */     }
/* 328:324 */     else if (ForceFrame.getAntagonistStrength(thing).equals("grow"))
/* 329:    */     {
/* 330:325 */       setCompound();
/* 331:326 */       setPlus();
/* 332:    */     }
/* 333:327 */     else if (ForceFrame.getAntagonistStrength(thing).equals("fade"))
/* 334:    */     {
/* 335:328 */       setCompound();
/* 336:329 */       setMinus();
/* 337:    */     }
/* 338:330 */     else if (ForceFrame.getAntagonistStrength(thing).equals("unknown"))
/* 339:    */     {
/* 340:331 */       setAntQuestion();
/* 341:    */     }
/* 342:334 */     setViewable(true);
/* 343:335 */     repaint();
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void setParameters(Object object)
/* 347:    */   {
/* 348:340 */     System.err.println("Entering setParameters without proper force frame");
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void view(Object signal)
/* 352:    */   {
/* 353:344 */     if ((signal instanceof Relation))
/* 354:    */     {
/* 355:345 */       Relation force = (Relation)signal;
/* 356:346 */       if (force.isA(ForceFrame.FRAMETYPE)) {
/* 357:347 */         setParameters(force);
/* 358:    */       }
/* 359:    */     }
/* 360:350 */     setTruthValue(signal);
/* 361:    */   }
/* 362:    */   
/* 363:    */   public static void main(String[] args)
/* 364:    */   {
/* 365:354 */     ForceViewer view = new ForceViewer();
/* 366:355 */     JFrame frame = new JFrame();
/* 367:356 */     frame.getContentPane().add(view);
/* 368:357 */     frame.setBounds(0, 0, 200, 200);
/* 369:358 */     frame.setVisible(true);
/* 370:359 */     Entity t = (Entity)ForceFrame.getMap().get("The ball kept rolling because the wind blew on it.");
/* 371:360 */     t.addFeature("not");
/* 372:361 */     view.view(t);
/* 373:    */   }
/* 374:    */   
/* 375:    */   public boolean isViewable()
/* 376:    */   {
/* 377:364 */     return this.viewable;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void setViewable(boolean viewable)
/* 381:    */   {
/* 382:367 */     this.viewable = viewable;
/* 383:    */   }
/* 384:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.ForceViewer
 * JD-Core Version:    0.7.0.1
 */