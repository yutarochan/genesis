/*   1:    */ package adamKraft;
/*   2:    */ 
/*   3:    */ import adamKraft.videoUtils.MovieReader;
/*   4:    */ import adamKraft.videoUtils.UsesNativeVideoLib;
/*   5:    */ import bridge.reps.entities.Entity;
/*   6:    */ import connections.Connections;
/*   7:    */ import connections.Connections.NetWireException;
/*   8:    */ import connections.Ports;
/*   9:    */ import connections.WiredBox;
/*  10:    */ import java.awt.Component;
/*  11:    */ import java.awt.Container;
/*  12:    */ import java.awt.Dimension;
/*  13:    */ import java.awt.Graphics;
/*  14:    */ import java.awt.event.ActionEvent;
/*  15:    */ import java.awt.event.ActionListener;
/*  16:    */ import java.awt.event.KeyEvent;
/*  17:    */ import java.awt.event.KeyListener;
/*  18:    */ import java.awt.event.WindowAdapter;
/*  19:    */ import java.awt.event.WindowEvent;
/*  20:    */ import java.awt.image.BufferedImage;
/*  21:    */ import java.io.File;
/*  22:    */ import java.io.IOException;
/*  23:    */ import java.io.PrintStream;
/*  24:    */ import java.util.ArrayList;
/*  25:    */ import java.util.HashMap;
/*  26:    */ import java.util.Iterator;
/*  27:    */ import java.util.List;
/*  28:    */ import java.util.Map;
/*  29:    */ import java.util.Set;
/*  30:    */ import javax.swing.BoxLayout;
/*  31:    */ import javax.swing.JButton;
/*  32:    */ import javax.swing.JFileChooser;
/*  33:    */ import javax.swing.JFrame;
/*  34:    */ import javax.swing.JOptionPane;
/*  35:    */ import javax.swing.JPanel;
/*  36:    */ import javax.swing.JTextField;
/*  37:    */ import translator.Translator;
/*  38:    */ 
/*  39:    */ public class VideoAnnotator
/*  40:    */   extends JPanel
/*  41:    */ {
/*  42:    */   public static final String englishServiceName = "English annotations";
/*  43:    */   public static final String thingServiceName = "Genesis representation annotations";
/*  44:    */   protected MovieReader mReader;
/*  45:    */   protected int frameNumber;
/*  46: 25 */   protected VideoDisplay vDisp = new VideoDisplay();
/*  47:    */   private Controls controls;
/*  48:    */   private String openFile;
/*  49: 28 */   private boolean remoteMode = false;
/*  50: 29 */   private boolean inGap = false;
/*  51: 31 */   protected WiredBox englishBroadcaster = new Broadcaster(this, "English annotations");
/*  52: 32 */   protected WiredBox thingBroadcaster = new Broadcaster(this, "Genesis representation annotations");
/*  53:    */   protected Map<Integer, List<String>> annotations;
/*  54:    */   
/*  55:    */   protected static class Broadcaster
/*  56:    */     implements WiredBox
/*  57:    */   {
/*  58:    */     private VideoAnnotator va;
/*  59:    */     
/*  60:    */     public String getName()
/*  61:    */     {
/*  62: 38 */       return "irrelevant";
/*  63:    */     }
/*  64:    */     
/*  65:    */     public void playVideo(String videoName)
/*  66:    */     {
/*  67: 42 */       final String fullVideoName = UsesNativeVideoLib.getResourceFileName(videoName);
/*  68: 43 */       new Thread()
/*  69:    */       {
/*  70:    */         public void run()
/*  71:    */         {
/*  72: 45 */           synchronized (VideoAnnotator.this)
/*  73:    */           {
/*  74: 46 */             VideoAnnotator.this.disableControls();
/*  75: 47 */             VideoAnnotator.this.remoteMode = true;
/*  76: 48 */             VideoAnnotator.this.open(fullVideoName);
/*  77: 49 */             while (VideoAnnotator.this.advance()) {}
/*  78: 50 */             VideoAnnotator.this.remoteMode = false;
/*  79: 51 */             VideoAnnotator.this.enableControls();
/*  80:    */           }
/*  81:    */         }
/*  82:    */       }.start();
/*  83:    */     }
/*  84:    */     
/*  85:    */     public Broadcaster(VideoAnnotator v, String service)
/*  86:    */     {
/*  87: 59 */       this.va = v;
/*  88:    */       try
/*  89:    */       {
/*  90: 61 */         Connections.publish(this, service);
/*  91:    */       }
/*  92:    */       catch (Connections.NetWireException e)
/*  93:    */       {
/*  94: 63 */         System.err.println("already published by another process; we are in silent mode");
/*  95:    */       }
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public VideoAnnotator()
/* 100:    */   {
/* 101: 72 */     setLayout(new BoxLayout(this, 1));
/* 102: 73 */     JPanel p = new JPanel();
/* 103: 74 */     p.add(this.vDisp);
/* 104: 75 */     add(p);
/* 105: 76 */     add(getControls());
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected Controls getControls()
/* 109:    */   {
/* 110: 80 */     if (this.controls == null) {
/* 111: 81 */       this.controls = new Controls(this);
/* 112:    */     }
/* 113: 83 */     return this.controls;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void enableControls() {}
/* 117:    */   
/* 118:    */   public void disableControls() {}
/* 119:    */   
/* 120:    */   public synchronized void open(String fileName)
/* 121:    */   {
/* 122:    */     try
/* 123:    */     {
/* 124: 98 */       if (this.mReader != null) {
/* 125: 99 */         this.mReader.close();
/* 126:    */       }
/* 127:101 */       this.mReader = new MovieReader(fileName);
/* 128:102 */       BufferedImage b = this.mReader.getNextFrame();
/* 129:103 */       this.mReader.seek(0L);
/* 130:104 */       this.frameNumber = 0;
/* 131:105 */       this.vDisp.setImg(b);
/* 132:106 */       this.openFile = fileName;
/* 133:107 */       loadAnnotations();
/* 134:    */     }
/* 135:    */     catch (IOException e)
/* 136:    */     {
/* 137:109 */       e.printStackTrace();
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   public synchronized void rewind()
/* 142:    */   {
/* 143:    */     try
/* 144:    */     {
/* 145:115 */       this.mReader.seek(0L);
/* 146:116 */       BufferedImage b = this.mReader.getNextFrame();
/* 147:117 */       this.vDisp.setImg(b);
/* 148:118 */       this.mReader.seek(0L);
/* 149:    */     }
/* 150:    */     catch (IOException e)
/* 151:    */     {
/* 152:120 */       e.printStackTrace();
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   protected void loadAnnotations()
/* 157:    */   {
/* 158:125 */     this.annotations = new HashMap();
/* 159:126 */     File afile = new File((this.openFile + ".annotations").replace("binary", "source"));
/* 160:127 */     if (afile.exists())
/* 161:    */     {
/* 162:128 */       NoBullshitFileIO f = new NoBullshitFileIO(afile, "r");
/* 163:129 */       for (String line : f.getLines())
/* 164:    */       {
/* 165:130 */         String numToken = line.substring(0, line.indexOf(":"));
/* 166:131 */         String lineToken = line.substring(line.indexOf(':') + 1);
/* 167:132 */         String annotation = lineToken.trim();
/* 168:133 */         Integer frame = Integer.valueOf(numToken);
/* 169:134 */         recordAnnotation(frame.intValue(), annotation);
/* 170:    */       }
/* 171:136 */       f.close();
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void eraseAnnotations()
/* 176:    */   {
/* 177:141 */     this.annotations = new HashMap();
/* 178:142 */     saveAnnotations();
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void saveAnnotations()
/* 182:    */   {
/* 183:146 */     File afile = new File((this.openFile + ".annotations").replace("binary", "source"));
/* 184:147 */     NoBullshitFileIO f = new NoBullshitFileIO(afile, "w");
/* 185:    */     Iterator localIterator2;
/* 186:148 */     for (Iterator localIterator1 = this.annotations.keySet().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 187:    */     {
/* 188:148 */       int key = ((Integer)localIterator1.next()).intValue();
/* 189:149 */       localIterator2 = ((List)this.annotations.get(Integer.valueOf(key))).iterator(); continue;String annotation = (String)localIterator2.next();
/* 190:150 */       f.write(key + ": " + annotation + "\n");
/* 191:    */     }
/* 192:153 */     f.close();
/* 193:    */   }
/* 194:    */   
/* 195:    */   protected void recordAnnotation(int frame, String annotation)
/* 196:    */   {
/* 197:157 */     if (!this.annotations.containsKey(Integer.valueOf(frame))) {
/* 198:158 */       this.annotations.put(Integer.valueOf(frame), new ArrayList());
/* 199:    */     }
/* 200:160 */     ((List)this.annotations.get(Integer.valueOf(frame))).add(annotation);
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void recordAnnotation(String annotation)
/* 204:    */   {
/* 205:164 */     recordAnnotation(this.frameNumber, annotation);
/* 206:165 */     printAnnotations();
/* 207:    */   }
/* 208:    */   
/* 209:    */   protected void printAnnotations()
/* 210:    */   {
/* 211:169 */     if (this.annotations.containsKey(Integer.valueOf(this.frameNumber))) {
/* 212:170 */       for (String s : (List)this.annotations.get(Integer.valueOf(this.frameNumber))) {
/* 213:171 */         System.out.println("***\n" + this.frameNumber + ":\t" + s + "\n***");
/* 214:    */       }
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   protected void announceAnnotations()
/* 219:    */   {
/* 220:180 */     printAnnotations();
/* 221:181 */     if (this.annotations.containsKey(Integer.valueOf(this.frameNumber))) {
/* 222:182 */       for (String s : (List)this.annotations.get(Integer.valueOf(this.frameNumber)))
/* 223:    */       {
/* 224:183 */         Connections.getPorts(this.englishBroadcaster).transmit(s);
/* 225:184 */         Entity t = null;
/* 226:    */         try
/* 227:    */         {
/* 228:186 */           t = Translator.getTranslator().translate(s);
/* 229:    */         }
/* 230:    */         catch (Exception e)
/* 231:    */         {
/* 232:189 */           e.printStackTrace();
/* 233:    */         }
/* 234:191 */         Connections.getPorts(this.thingBroadcaster).transmit(t);
/* 235:    */       }
/* 236:    */     }
/* 237:    */   }
/* 238:    */   
/* 239:    */   protected void detectGap()
/* 240:    */   {
/* 241:197 */     if (this.annotations.containsKey(Integer.valueOf(this.frameNumber)))
/* 242:    */     {
/* 243:198 */       for (String s : (List)this.annotations.get(Integer.valueOf(this.frameNumber))) {
/* 244:199 */         if ((s.toLowerCase().contains("gap")) && (s.toLowerCase().contains("appear")))
/* 245:    */         {
/* 246:200 */           this.inGap = true;
/* 247:201 */           return;
/* 248:    */         }
/* 249:    */       }
/* 250:204 */       this.inGap = false;
/* 251:205 */       return;
/* 252:    */     }
/* 253:    */   }
/* 254:    */   
/* 255:    */   public synchronized boolean advance()
/* 256:    */   {
/* 257:    */     try
/* 258:    */     {
/* 259:211 */       BufferedImage b = this.mReader.getNextFrame();
/* 260:212 */       if (b != null)
/* 261:    */       {
/* 262:213 */         announceAnnotations();
/* 263:214 */         detectGap();
/* 264:215 */         if ((this.remoteMode) && (this.inGap))
/* 265:    */         {
/* 266:216 */           BufferedImage blank = new BufferedImage(b.getWidth(), b.getHeight(), 12);
/* 267:217 */           this.vDisp.setImg(blank);
/* 268:    */         }
/* 269:    */         else
/* 270:    */         {
/* 271:219 */           this.vDisp.setImg(b);
/* 272:    */         }
/* 273:221 */         this.frameNumber += 1;
/* 274:222 */         return true;
/* 275:    */       }
/* 276:224 */       return false;
/* 277:    */     }
/* 278:    */     catch (IOException e)
/* 279:    */     {
/* 280:227 */       e.printStackTrace();
/* 281:    */     }
/* 282:228 */     return false;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public synchronized boolean back()
/* 286:    */   {
/* 287:233 */     if (this.frameNumber < 2) {
/* 288:234 */       return false;
/* 289:    */     }
/* 290:    */     try
/* 291:    */     {
/* 292:237 */       this.mReader.seek(this.frameNumber - 2);
/* 293:238 */       this.frameNumber -= 2;
/* 294:239 */       return advance();
/* 295:    */     }
/* 296:    */     catch (IOException e)
/* 297:    */     {
/* 298:241 */       e.printStackTrace();
/* 299:    */     }
/* 300:242 */     return false;
/* 301:    */   }
/* 302:    */   
/* 303:    */   protected static class VideoDisplay
/* 304:    */     extends Component
/* 305:    */   {
/* 306:    */     BufferedImage img;
/* 307:    */     
/* 308:    */     public synchronized void paint(Graphics g)
/* 309:    */     {
/* 310:249 */       if (this.img != null) {
/* 311:250 */         g.drawImage(this.img, 0, 0, null);
/* 312:    */       }
/* 313:    */     }
/* 314:    */     
/* 315:    */     public synchronized void setImg(BufferedImage b)
/* 316:    */     {
/* 317:255 */       this.img = b;
/* 318:256 */       invalidate();
/* 319:257 */       repaint();
/* 320:    */     }
/* 321:    */     
/* 322:    */     public Dimension getPreferredSize()
/* 323:    */     {
/* 324:264 */       if (this.img == null) {
/* 325:265 */         return new Dimension(100, 100);
/* 326:    */       }
/* 327:267 */       return new Dimension(this.img.getWidth(null), this.img.getHeight(null));
/* 328:    */     }
/* 329:    */   }
/* 330:    */   
/* 331:    */   protected static class Controls
/* 332:    */     extends JPanel
/* 333:    */   {
/* 334:    */     VideoAnnotator a;
/* 335:274 */     JButton open = new JButton("open...");
/* 336:275 */     JButton playPause = new JButton(" play ");
/* 337:276 */     JButton fwd = new JButton("step forward");
/* 338:277 */     JButton back = new JButton("step back");
/* 339:278 */     JTextField annotation = new JTextField(60);
/* 340:279 */     JButton anote = new JButton("register annotation");
/* 341:280 */     JButton erase = new JButton("erase annotations file");
/* 342:281 */     JButton rewind = new JButton("rewind");
/* 343:282 */     private boolean playing = false;
/* 344:283 */     private boolean stop = false;
/* 345:    */     
/* 346:    */     protected void initListeners()
/* 347:    */     {
/* 348:286 */       this.open.addActionListener(new ActionListener()
/* 349:    */       {
/* 350:    */         public void actionPerformed(ActionEvent arg0)
/* 351:    */         {
/* 352:289 */           JFileChooser fc = new JFileChooser();
/* 353:290 */           int ret = fc.showOpenDialog(VideoAnnotator.Controls.this.a);
/* 354:291 */           if (ret == 0)
/* 355:    */           {
/* 356:292 */             File f = fc.getSelectedFile();
/* 357:293 */             VideoAnnotator.Controls.this.a.open(f.getAbsolutePath());
/* 358:    */           }
/* 359:    */         }
/* 360:296 */       });
/* 361:297 */       this.rewind.addActionListener(new ActionListener()
/* 362:    */       {
/* 363:    */         public void actionPerformed(ActionEvent arg0)
/* 364:    */         {
/* 365:300 */           VideoAnnotator.Controls.this.a.rewind();
/* 366:    */         }
/* 367:302 */       });
/* 368:303 */       this.playPause.addActionListener(new ActionListener()
/* 369:    */       {
/* 370:    */         public void actionPerformed(ActionEvent arg0)
/* 371:    */         {
/* 372:307 */           if (VideoAnnotator.Controls.this.playing)
/* 373:    */           {
/* 374:308 */             VideoAnnotator.Controls.this.stop = true;
/* 375:309 */             while (VideoAnnotator.Controls.this.playing) {
/* 376:    */               try
/* 377:    */               {
/* 378:311 */                 Thread.sleep(10L);
/* 379:    */               }
/* 380:    */               catch (InterruptedException e)
/* 381:    */               {
/* 382:313 */                 e.printStackTrace();
/* 383:    */               }
/* 384:    */             }
/* 385:316 */             VideoAnnotator.Controls.this.stop = false;
/* 386:317 */             VideoAnnotator.Controls.this.playPause.setText("play");
/* 387:318 */             VideoAnnotator.Controls.this.fwd.setEnabled(true);
/* 388:319 */             VideoAnnotator.Controls.this.back.setEnabled(true);
/* 389:    */           }
/* 390:    */           else
/* 391:    */           {
/* 392:321 */             VideoAnnotator.Controls.this.fwd.setEnabled(false);
/* 393:322 */             VideoAnnotator.Controls.this.back.setEnabled(false);
/* 394:323 */             VideoAnnotator.Controls.this.playPause.setText("stop");
/* 395:324 */             VideoAnnotator.Controls.this.playing = true;
/* 396:325 */             new Thread()
/* 397:    */             {
/* 398:    */               public void run()
/* 399:    */               {
/* 400:328 */                 while (!VideoAnnotator.Controls.this.stop) {
/* 401:329 */                   if (!VideoAnnotator.Controls.this.a.advance()) {
/* 402:    */                     break;
/* 403:    */                   }
/* 404:    */                 }
/* 405:333 */                 VideoAnnotator.Controls.this.playing = false;
/* 406:334 */                 VideoAnnotator.Controls.this.playPause.setText("play");
/* 407:335 */                 VideoAnnotator.Controls.this.fwd.setEnabled(true);
/* 408:336 */                 VideoAnnotator.Controls.this.back.setEnabled(true);
/* 409:    */               }
/* 410:    */             }.start();
/* 411:    */           }
/* 412:    */         }
/* 413:342 */       });
/* 414:343 */       this.back.addActionListener(new ActionListener()
/* 415:    */       {
/* 416:    */         public void actionPerformed(ActionEvent arg0)
/* 417:    */         {
/* 418:351 */           new Thread()
/* 419:    */           {
/* 420:    */             public void run()
/* 421:    */             {
/* 422:349 */               VideoAnnotator.Controls.this.a.back();
/* 423:    */             }
/* 424:    */           }.start();
/* 425:    */         }
/* 426:353 */       });
/* 427:354 */       this.fwd.addActionListener(new ActionListener()
/* 428:    */       {
/* 429:    */         public void actionPerformed(ActionEvent arg0)
/* 430:    */         {
/* 431:362 */           new Thread()
/* 432:    */           {
/* 433:    */             public void run()
/* 434:    */             {
/* 435:360 */               VideoAnnotator.Controls.this.a.advance();
/* 436:    */             }
/* 437:    */           }.start();
/* 438:    */         }
/* 439:364 */       });
/* 440:365 */       this.anote.addActionListener(new ActionListener()
/* 441:    */       {
/* 442:    */         public void actionPerformed(ActionEvent arg0)
/* 443:    */         {
/* 444:368 */           VideoAnnotator.Controls.this.a.recordAnnotation(VideoAnnotator.Controls.this.annotation.getText());
/* 445:369 */           VideoAnnotator.Controls.this.a.saveAnnotations();
/* 446:370 */           VideoAnnotator.Controls.this.annotation.setText("");
/* 447:    */         }
/* 448:372 */       });
/* 449:373 */       this.erase.addActionListener(new ActionListener()
/* 450:    */       {
/* 451:    */         public void actionPerformed(ActionEvent arg0)
/* 452:    */         {
/* 453:376 */           if (JOptionPane.showConfirmDialog(VideoAnnotator.Controls.this.a, "really erase file?", 
/* 454:377 */             "yo", 2) == 0) {
/* 455:378 */             VideoAnnotator.Controls.this.a.eraseAnnotations();
/* 456:    */           }
/* 457:    */         }
/* 458:381 */       });
/* 459:382 */       this.annotation.addKeyListener(new KeyListener()
/* 460:    */       {
/* 461:    */         public void keyPressed(KeyEvent arg0) {}
/* 462:    */         
/* 463:    */         public void keyReleased(KeyEvent arg0)
/* 464:    */         {
/* 465:391 */           if (arg0.getKeyChar() == '\n')
/* 466:    */           {
/* 467:392 */             arg0.consume();
/* 468:393 */             VideoAnnotator.Controls.this.a.recordAnnotation(VideoAnnotator.Controls.this.annotation.getText());
/* 469:394 */             VideoAnnotator.Controls.this.a.saveAnnotations();
/* 470:395 */             VideoAnnotator.Controls.this.annotation.setText("");
/* 471:    */           }
/* 472:    */         }
/* 473:    */         
/* 474:    */         public void keyTyped(KeyEvent arg0) {}
/* 475:    */       });
/* 476:    */     }
/* 477:    */     
/* 478:    */     public void enableAll()
/* 479:    */     {
/* 480:409 */       synchronized (this.a)
/* 481:    */       {
/* 482:410 */         this.open.setEnabled(false);
/* 483:411 */         this.playPause.setEnabled(false);
/* 484:412 */         this.fwd.setEnabled(false);
/* 485:413 */         this.back.setEnabled(false);
/* 486:414 */         this.annotation.setEnabled(false);
/* 487:415 */         this.anote.setEnabled(false);
/* 488:416 */         this.erase.setEnabled(false);
/* 489:    */       }
/* 490:    */     }
/* 491:    */     
/* 492:    */     public void disableAll()
/* 493:    */     {
/* 494:421 */       synchronized (this.a)
/* 495:    */       {
/* 496:422 */         this.open.setEnabled(true);
/* 497:423 */         this.playPause.setEnabled(true);
/* 498:424 */         this.fwd.setEnabled(true);
/* 499:425 */         this.back.setEnabled(true);
/* 500:426 */         this.annotation.setEnabled(true);
/* 501:427 */         this.anote.setEnabled(true);
/* 502:428 */         this.erase.setEnabled(true);
/* 503:    */       }
/* 504:    */     }
/* 505:    */     
/* 506:    */     public Controls(VideoAnnotator a)
/* 507:    */     {
/* 508:433 */       this.a = a;
/* 509:434 */       initListeners();
/* 510:435 */       add(this.open);
/* 511:436 */       add(this.rewind);
/* 512:437 */       add(this.playPause);
/* 513:438 */       add(this.fwd);
/* 514:439 */       add(this.back);
/* 515:440 */       add(this.annotation);
/* 516:441 */       add(this.anote);
/* 517:442 */       add(this.erase);
/* 518:    */     }
/* 519:    */   }
/* 520:    */   
/* 521:    */   public static void start()
/* 522:    */   {
/* 523:447 */     JFrame test = new JFrame();
/* 524:448 */     test.addWindowListener(new WindowAdapter()
/* 525:    */     {
/* 526:    */       public void windowClosing(WindowEvent e)
/* 527:    */       {
/* 528:450 */         System.exit(0);
/* 529:    */       }
/* 530:452 */     });
/* 531:453 */     VideoAnnotator v = new VideoAnnotator();
/* 532:454 */     v.open(UsesNativeVideoLib.getResourceFileName("test.mov"));
/* 533:455 */     test.getContentPane().add(v);
/* 534:456 */     test.pack();
/* 535:457 */     test.setVisible(true);
/* 536:    */   }
/* 537:    */   
/* 538:    */   public static void main(String[] args) {}
/* 539:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adamKraft.VideoAnnotator
 * JD-Core Version:    0.7.0.1
 */