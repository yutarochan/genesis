/*   1:    */ package ati;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Component;
/*   5:    */ import java.awt.Container;
/*   6:    */ import java.awt.Dimension;
/*   7:    */ import java.awt.Insets;
/*   8:    */ import java.awt.LayoutManager;
/*   9:    */ import java.io.PrintStream;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import javax.swing.JComponent;
/*  12:    */ import javax.swing.JFrame;
/*  13:    */ import javax.swing.JLabel;
/*  14:    */ import javax.swing.JTextField;
/*  15:    */ 
/*  16:    */ public class ParallelJPanel
/*  17:    */   extends SpecialJPanel
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = -6221771356216603892L;
/*  20: 27 */   protected ArrayList leftList = new ArrayList();
/*  21: 29 */   protected ArrayList centerList = new ArrayList();
/*  22: 31 */   protected ArrayList rightList = new ArrayList();
/*  23: 33 */   protected int vSpacer = 10;
/*  24: 34 */   protected int hSpacer = 10;
/*  25:    */   
/*  26:    */   public ParallelJPanel()
/*  27:    */   {
/*  28: 39 */     setLayout(new MyLayoutManager());
/*  29: 40 */     setOpaque(false);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void clear()
/*  33:    */   {
/*  34: 44 */     this.leftList.clear();
/*  35: 45 */     this.centerList.clear();
/*  36: 46 */     this.rightList.clear();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void addLeft(Object j)
/*  40:    */   {
/*  41: 53 */     if ((j instanceof String)) {
/*  42: 54 */       j = new JLabel((String)j);
/*  43:    */     }
/*  44: 56 */     if ((j instanceof JComponent)) {
/*  45: 57 */       add((JComponent)j);
/*  46:    */     }
/*  47: 59 */     this.leftList.add(j);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void addCenter(Object j)
/*  51:    */   {
/*  52: 66 */     if ((j instanceof String)) {
/*  53: 67 */       j = new JLabel((String)j);
/*  54:    */     }
/*  55: 69 */     if ((j instanceof JComponent)) {
/*  56: 70 */       add((JComponent)j);
/*  57:    */     }
/*  58: 72 */     this.centerList.add(j);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void addRight(Object j)
/*  62:    */   {
/*  63: 79 */     if ((j instanceof String)) {
/*  64: 80 */       j = new JLabel((String)j);
/*  65:    */     }
/*  66: 82 */     if ((j instanceof JComponent)) {
/*  67: 83 */       add((JComponent)j);
/*  68:    */     }
/*  69: 85 */     this.rightList.add(j);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void addPair(JComponent left, JComponent right)
/*  73:    */   {
/*  74: 93 */     addLeft(left);addRight(right);
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected int getLWidth()
/*  78:    */   {
/*  79: 98 */     int lWidth = 0;
/*  80: 99 */     int maxCount = Math.max(this.centerList.size(), Math.max(this.leftList.size(), this.rightList.size()));
/*  81:100 */     for (int i = 0; i < maxCount; i++)
/*  82:    */     {
/*  83:101 */       Object l = null;Object c = null;Object r = null;
/*  84:102 */       if (i < this.leftList.size()) {
/*  85:102 */         l = this.leftList.get(i);
/*  86:    */       }
/*  87:103 */       if (i < this.centerList.size()) {
/*  88:103 */         c = this.centerList.get(i);
/*  89:    */       }
/*  90:104 */       if (i < this.rightList.size()) {
/*  91:104 */         r = this.rightList.get(i);
/*  92:    */       }
/*  93:105 */       if ((l instanceof JComponent))
/*  94:    */       {
/*  95:106 */         Dimension lDimension = ((JComponent)l).getPreferredSize();
/*  96:107 */         if ((c != null) || (r != null)) {
/*  97:108 */           lWidth = Math.max(lWidth, lDimension.width);
/*  98:    */         }
/*  99:    */       }
/* 100:    */     }
/* 101:112 */     return lWidth;
/* 102:    */   }
/* 103:    */   
/* 104:    */   protected int getCWidth()
/* 105:    */   {
/* 106:116 */     int cWidth = 0;
/* 107:117 */     int maxCount = Math.max(this.centerList.size(), Math.max(this.leftList.size(), this.rightList.size()));
/* 108:118 */     Object c = null;Object r = null;
/* 109:119 */     for (int i = 0; i < maxCount; i++)
/* 110:    */     {
/* 111:120 */       if (i < this.centerList.size()) {
/* 112:121 */         c = this.centerList.get(i);
/* 113:    */       }
/* 114:123 */       if (i < this.rightList.size()) {
/* 115:124 */         r = this.rightList.get(i);
/* 116:    */       }
/* 117:126 */       if ((c instanceof JComponent))
/* 118:    */       {
/* 119:127 */         Dimension cDimension = ((JComponent)c).getPreferredSize();
/* 120:128 */         if (r != null) {
/* 121:129 */           cWidth = Math.max(cWidth, cDimension.width);
/* 122:    */         }
/* 123:    */       }
/* 124:    */     }
/* 125:133 */     return cWidth;
/* 126:    */   }
/* 127:    */   
/* 128:    */   protected int getRWidth()
/* 129:    */   {
/* 130:137 */     int rWidth = 0;
/* 131:138 */     int maxCount = Math.max(this.centerList.size(), Math.max(this.leftList.size(), this.rightList.size()));
/* 132:139 */     Object r = null;
/* 133:140 */     for (int i = 0; i < maxCount; i++)
/* 134:    */     {
/* 135:141 */       if (i < this.rightList.size()) {
/* 136:142 */         r = this.rightList.get(i);
/* 137:    */       }
/* 138:144 */       if ((r instanceof JComponent))
/* 139:    */       {
/* 140:145 */         Dimension rDimension = ((JComponent)r).getPreferredSize();
/* 141:146 */         rWidth = Math.max(rWidth, rDimension.width);
/* 142:    */       }
/* 143:    */     }
/* 144:149 */     return rWidth;
/* 145:    */   }
/* 146:    */   
/* 147:    */   protected int getMaxHeight()
/* 148:    */   {
/* 149:153 */     int maxCount = Math.max(this.centerList.size(), Math.max(this.leftList.size(), this.rightList.size()));
/* 150:154 */     int lHeight = 0;int cHeight = 0;int rHeight = 0;
/* 151:155 */     for (int i = 0; i < maxCount; i++)
/* 152:    */     {
/* 153:156 */       Object l = null;Object c = null;Object r = null;
/* 154:157 */       if (i < this.leftList.size()) {
/* 155:157 */         l = this.leftList.get(i);
/* 156:    */       }
/* 157:158 */       if (i < this.centerList.size()) {
/* 158:158 */         c = this.centerList.get(i);
/* 159:    */       }
/* 160:159 */       if (i < this.rightList.size()) {
/* 161:159 */         r = this.rightList.get(i);
/* 162:    */       }
/* 163:160 */       if ((l instanceof JComponent))
/* 164:    */       {
/* 165:161 */         Dimension lDimension = ((JComponent)l).getPreferredSize();
/* 166:162 */         lHeight = Math.max(lHeight, lDimension.height);
/* 167:    */       }
/* 168:164 */       if ((c instanceof JComponent))
/* 169:    */       {
/* 170:165 */         Dimension cDimension = ((JComponent)c).getPreferredSize();
/* 171:166 */         cHeight = Math.max(cHeight, cDimension.height);
/* 172:    */       }
/* 173:168 */       if ((r instanceof JComponent))
/* 174:    */       {
/* 175:169 */         Dimension rDimension = ((JComponent)r).getPreferredSize();
/* 176:170 */         rHeight = Math.max(rHeight, rDimension.height);
/* 177:    */       }
/* 178:    */     }
/* 179:173 */     return Math.max(cHeight, Math.max(lHeight, rHeight));
/* 180:    */   }
/* 181:    */   
/* 182:    */   protected int getMinHeight()
/* 183:    */   {
/* 184:177 */     int maxCount = Math.max(this.centerList.size(), Math.max(this.leftList.size(), this.rightList.size()));
/* 185:178 */     int lHeight = 0;int cHeight = 0;int rHeight = 0;
/* 186:179 */     for (int i = 0; i < maxCount; i++)
/* 187:    */     {
/* 188:180 */       Object l = null;Object c = null;Object r = null;
/* 189:181 */       if (i < this.leftList.size()) {
/* 190:181 */         l = this.leftList.get(i);
/* 191:    */       }
/* 192:182 */       if (i < this.centerList.size()) {
/* 193:182 */         c = this.centerList.get(i);
/* 194:    */       }
/* 195:183 */       if (i < this.rightList.size()) {
/* 196:183 */         r = this.rightList.get(i);
/* 197:    */       }
/* 198:184 */       if ((l instanceof JComponent))
/* 199:    */       {
/* 200:185 */         Dimension lDimension = ((JComponent)l).getPreferredSize();
/* 201:186 */         lHeight = Math.max(lHeight, lDimension.height);
/* 202:    */       }
/* 203:188 */       if ((c instanceof JComponent))
/* 204:    */       {
/* 205:189 */         Dimension cDimension = ((JComponent)c).getPreferredSize();
/* 206:190 */         cHeight = Math.max(cHeight, cDimension.height);
/* 207:    */       }
/* 208:192 */       if ((r instanceof JComponent))
/* 209:    */       {
/* 210:193 */         Dimension rDimension = ((JComponent)r).getPreferredSize();
/* 211:194 */         rHeight = Math.max(rHeight, rDimension.height);
/* 212:    */       }
/* 213:    */     }
/* 214:    */     int result;
/* 215:    */     int result;
/* 216:198 */     if (lHeight > 0)
/* 217:    */     {
/* 218:198 */       result = lHeight;
/* 219:    */     }
/* 220:    */     else
/* 221:    */     {
/* 222:    */       int result;
/* 223:199 */       if (cHeight > 0) {
/* 224:199 */         result = cHeight;
/* 225:    */       } else {
/* 226:200 */         result = rHeight;
/* 227:    */       }
/* 228:    */     }
/* 229:201 */     if ((lHeight > 0) && (lHeight < result)) {
/* 230:201 */       result = lHeight;
/* 231:    */     }
/* 232:202 */     if ((cHeight > 0) && (cHeight < result)) {
/* 233:202 */       result = cHeight;
/* 234:    */     }
/* 235:203 */     if ((rHeight > 0) && (rHeight < result)) {
/* 236:203 */       result = rHeight;
/* 237:    */     }
/* 238:204 */     return result;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public Dimension getPreferredSize()
/* 242:    */   {
/* 243:211 */     int maxCount = Math.max(this.centerList.size(), Math.max(this.leftList.size(), this.rightList.size()));
/* 244:212 */     int lWidth = getLWidth();int cWidth = getCWidth();int rWidth = getRWidth();
/* 245:213 */     int maxHeight = getMaxHeight();
/* 246:    */     
/* 247:    */ 
/* 248:216 */     int vSpacer = maxHeight / 2;
/* 249:217 */     int totalHeight = maxCount * maxHeight + (maxCount + 1) * vSpacer;
/* 250:    */     
/* 251:219 */     int hSpacer = 2 * vSpacer;
/* 252:220 */     int totalWidth = lWidth + cWidth + rWidth + hSpacer + 10;
/* 253:221 */     if (cWidth > 0) {
/* 254:221 */       totalWidth += hSpacer;
/* 255:    */     }
/* 256:222 */     if (rWidth > 0) {
/* 257:222 */       totalWidth += hSpacer;
/* 258:    */     }
/* 259:224 */     Dimension result = new Dimension(totalWidth, totalHeight);
/* 260:225 */     return result;
/* 261:    */   }
/* 262:    */   
/* 263:    */   protected class MyLayoutManager
/* 264:    */     implements LayoutManager
/* 265:    */   {
/* 266:    */     protected MyLayoutManager() {}
/* 267:    */     
/* 268:    */     public void removeLayoutComponent(Component component) {}
/* 269:    */     
/* 270:    */     public void addLayoutComponent(String string, Component arg1) {}
/* 271:    */     
/* 272:    */     public Dimension minimumLayoutSize(Container container)
/* 273:    */     {
/* 274:236 */       int count = Math.max(ParallelJPanel.this.centerList.size(), Math.max(ParallelJPanel.this.leftList.size(), ParallelJPanel.this.rightList.size()));
/* 275:237 */       int maxHeight = ParallelJPanel.this.getMaxHeight();
/* 276:238 */       int lWidth = ParallelJPanel.this.getLWidth();int cWidth = ParallelJPanel.this.getCWidth();int rWidth = ParallelJPanel.this.getRWidth();
/* 277:239 */       return new Dimension(lWidth + cWidth + rWidth, count * maxHeight);
/* 278:    */     }
/* 279:    */     
/* 280:    */     public Dimension preferredLayoutSize(Container container)
/* 281:    */     {
/* 282:243 */       int count = Math.max(ParallelJPanel.this.centerList.size(), Math.max(ParallelJPanel.this.leftList.size(), ParallelJPanel.this.rightList.size()));
/* 283:244 */       int maxHeight = ParallelJPanel.this.getMaxHeight();
/* 284:245 */       int lWidth = ParallelJPanel.this.getLWidth();int cWidth = ParallelJPanel.this.getCWidth();int rWidth = ParallelJPanel.this.getRWidth();
/* 285:246 */       return new Dimension(20 + lWidth + cWidth + rWidth, 10 + count * (maxHeight + 10));
/* 286:    */     }
/* 287:    */     
/* 288:    */     public void layoutContainer(Container container)
/* 289:    */     {
/* 290:250 */       int height = ParallelJPanel.this.getHeight() - ParallelJPanel.this.getInsets().top - ParallelJPanel.this.getInsets().bottom;
/* 291:251 */       int width = ParallelJPanel.this.getWidth() - ParallelJPanel.this.getInsets().left - ParallelJPanel.this.getInsets().right;
/* 292:252 */       if ((height == 0) || (width == 0)) {
/* 293:252 */         return;
/* 294:    */       }
/* 295:253 */       int count = Math.max(ParallelJPanel.this.centerList.size(), Math.max(ParallelJPanel.this.leftList.size(), ParallelJPanel.this.rightList.size()));
/* 296:    */       
/* 297:    */ 
/* 298:    */ 
/* 299:257 */       int lWidth = ParallelJPanel.this.getLWidth();int cWidth = ParallelJPanel.this.getCWidth();int rWidth = ParallelJPanel.this.getRWidth();
/* 300:    */       
/* 301:    */ 
/* 302:    */ 
/* 303:261 */       int maxHeight = ParallelJPanel.this.getMaxHeight();
/* 304:    */       
/* 305:263 */       int vSpacer = maxHeight / 2;
/* 306:264 */       int hSpacer = 2 * vSpacer;
/* 307:265 */       int theRightSpace = hSpacer;
/* 308:266 */       int theLeftOffset = 10;
/* 309:    */       
/* 310:268 */       int totalNeededWidth = theLeftOffset + lWidth + hSpacer;
/* 311:269 */       if (cWidth > 0) {
/* 312:269 */         totalNeededWidth += hSpacer + cWidth;
/* 313:    */       }
/* 314:270 */       if (rWidth > 0) {
/* 315:270 */         totalNeededWidth += hSpacer + rWidth;
/* 316:    */       }
/* 317:272 */       if (totalNeededWidth > width)
/* 318:    */       {
/* 319:274 */         int shortage = totalNeededWidth - width;
/* 320:275 */         int original = lWidth + cWidth;
/* 321:276 */         if (original >= shortage)
/* 322:    */         {
/* 323:277 */           lWidth = (int)(lWidth - lWidth / original * shortage);
/* 324:278 */           cWidth = (int)(cWidth - cWidth / original * shortage);
/* 325:    */         }
/* 326:    */         else
/* 327:    */         {
/* 328:281 */           lWidth = 0;
/* 329:282 */           cWidth = 0;
/* 330:283 */           rWidth = rWidth - shortage + original + 2 * hSpacer;
/* 331:    */         }
/* 332:    */       }
/* 333:289 */       int interlineSpace = Math.min(vSpacer, (height - count * maxHeight) / (1 + count));
/* 334:    */       
/* 335:291 */       interlineSpace = Math.max(interlineSpace, 0);
/* 336:    */       
/* 337:293 */       int theCenterOffset = theLeftOffset + lWidth;
/* 338:294 */       int theRightOffset = theCenterOffset + cWidth;
/* 339:295 */       if (lWidth > 0)
/* 340:    */       {
/* 341:296 */         theCenterOffset += hSpacer;
/* 342:297 */         theRightOffset += hSpacer;
/* 343:    */       }
/* 344:299 */       if (cWidth > 0) {
/* 345:300 */         theRightOffset += hSpacer;
/* 346:    */       }
/* 347:302 */       for (int i = 0; i < count; i++)
/* 348:    */       {
/* 349:303 */         Object l = null;Object c = null;Object r = null;
/* 350:304 */         if (i < ParallelJPanel.this.leftList.size()) {
/* 351:304 */           l = ParallelJPanel.this.leftList.get(i);
/* 352:    */         }
/* 353:305 */         if (i < ParallelJPanel.this.centerList.size()) {
/* 354:305 */           c = ParallelJPanel.this.centerList.get(i);
/* 355:    */         }
/* 356:306 */         if (i < ParallelJPanel.this.rightList.size()) {
/* 357:306 */           r = ParallelJPanel.this.rightList.get(i);
/* 358:    */         }
/* 359:308 */         int yOffset = i * (maxHeight + interlineSpace) + interlineSpace;
/* 360:310 */         if ((l instanceof JComponent))
/* 361:    */         {
/* 362:311 */           int preferredWidth = ((JComponent)l).getPreferredSize().width;
/* 363:313 */           if (r != null) {
/* 364:314 */             preferredWidth = Math.min(preferredWidth, theRightOffset - theLeftOffset - hSpacer);
/* 365:    */           }
/* 366:316 */           if (c != null) {
/* 367:317 */             preferredWidth = Math.min(preferredWidth, theCenterOffset - theLeftOffset - hSpacer);
/* 368:    */           }
/* 369:320 */           ((JComponent)l).setBounds(theLeftOffset + ParallelJPanel.this.getInsets().left, yOffset + ParallelJPanel.this.getInsets().top, preferredWidth, maxHeight);
/* 370:    */         }
/* 371:322 */         if ((c instanceof JComponent))
/* 372:    */         {
/* 373:323 */           int preferredWidth = ((JComponent)c).getPreferredSize().width;
/* 374:325 */           if (r != null) {
/* 375:326 */             preferredWidth = Math.min(preferredWidth, theRightOffset - theCenterOffset - hSpacer);
/* 376:    */           }
/* 377:329 */           ((JComponent)c).setBounds(theCenterOffset + ParallelJPanel.this.getInsets().left, yOffset + ParallelJPanel.this.getInsets().top, preferredWidth, maxHeight);
/* 378:    */         }
/* 379:331 */         if ((r instanceof JComponent))
/* 380:    */         {
/* 381:332 */           int preferredWidth = ((JComponent)r).getPreferredSize().width;
/* 382:    */           
/* 383:334 */           preferredWidth = Math.min(preferredWidth, width - theRightOffset - theRightSpace);
/* 384:    */           
/* 385:336 */           ((JComponent)r).setBounds(theRightOffset + ParallelJPanel.this.getInsets().left, yOffset + ParallelJPanel.this.getInsets().top, preferredWidth, maxHeight);
/* 386:    */         }
/* 387:    */       }
/* 388:    */     }
/* 389:    */   }
/* 390:    */   
/* 391:    */   public static void main(String[] ignore)
/* 392:    */   {
/* 393:343 */     TitledJPanel titledPanel = new TitledJPanel("Hello world");
/* 394:344 */     ParallelJPanel panel = new ParallelJPanel();
/* 395:345 */     panel.addLeft(new JLabel("Hello world, how are you?"));
/* 396:346 */     panel.addCenter(null);
/* 397:347 */     panel.addRight(null);
/* 398:348 */     panel.addLeft(null);
/* 399:349 */     panel.addCenter(null);
/* 400:350 */     panel.addRight(new JTextField("Look over here"));
/* 401:351 */     panel.addLeft(new JLabel("Hello"));
/* 402:352 */     panel.addCenter(new JLabel("my"));
/* 403:353 */     panel.addRight(new JLabel("friend"));
/* 404:354 */     panel.addLeft(null);
/* 405:355 */     panel.addCenter(new JTextField("Look over here"));
/* 406:356 */     panel.setBackground(Color.YELLOW);
/* 407:357 */     panel.setOpaque(true);
/* 408:358 */     System.err.println("Preferred left " + panel.getPreferredSize());
/* 409:    */     
/* 410:360 */     ParallelJPanel panel2 = new ParallelJPanel();
/* 411:361 */     panel2.addLeft("x");
/* 412:362 */     panel2.addRight("This is a nice long test");
/* 413:363 */     panel2.setBackground(Color.RED);
/* 414:364 */     panel2.setOpaque(true);
/* 415:365 */     System.err.println("Preferred right " + panel2.getPreferredSize());
/* 416:    */     
/* 417:367 */     HorizontalGridPanel combo = new HorizontalGridPanel();
/* 418:368 */     combo.add(panel);
/* 419:369 */     combo.add(panel2);
/* 420:370 */     JFrame frame = new JFrame();
/* 421:371 */     titledPanel.setMainPanel(combo);
/* 422:372 */     frame.getContentPane().add(titledPanel);
/* 423:373 */     frame.pack();
/* 424:374 */     frame.setVisible(true);
/* 425:    */   }
/* 426:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     ati.ParallelJPanel
 * JD-Core Version:    0.7.0.1
 */