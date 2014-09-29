/*   1:    */ package obsolete.mindsEye;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import adamKraft.Co57Connector;
/*   5:    */ import adamKraft.Co57Connector.AnnotationWrapper;
/*   6:    */ import adamKraft.VideoBrowser;
/*   7:    */ import adamKraft.videoUtils.MovieReader;
/*   8:    */ import connections.Connections;
/*   9:    */ import connections.Ports;
/*  10:    */ import connections.WiredBox;
/*  11:    */ import java.awt.Container;
/*  12:    */ import java.awt.event.MouseAdapter;
/*  13:    */ import java.awt.event.MouseEvent;
/*  14:    */ import java.awt.event.MouseMotionAdapter;
/*  15:    */ import java.awt.event.WindowAdapter;
/*  16:    */ import java.awt.event.WindowEvent;
/*  17:    */ import java.awt.image.BufferedImage;
/*  18:    */ import java.io.IOException;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.Collections;
/*  21:    */ import java.util.Comparator;
/*  22:    */ import java.util.HashMap;
/*  23:    */ import java.util.Set;
/*  24:    */ import javax.swing.JFrame;
/*  25:    */ import javax.swing.JSlider;
/*  26:    */ import michaelBehr.Annotation;
/*  27:    */ import text.Html;
/*  28:    */ import utils.Mark;
/*  29:    */ 
/*  30:    */ public class MindsEyeMoviePlayer
/*  31:    */   extends Co57Connector
/*  32:    */   implements WiredBox
/*  33:    */ {
/*  34: 30 */   public static int framesPerSecond = 60;
/*  35: 32 */   private static int underRepresented = 50;
/*  36: 34 */   private static int overRepresented = 200;
/*  37: 36 */   private static int skip = 1 * framesPerSecond;
/*  38: 38 */   private static int padding = 2 * framesPerSecond;
/*  39:    */   private MindsEyeMovieViewer viewer;
/*  40: 42 */   private int frame = 0;
/*  41: 44 */   private int jumpFrame = -1;
/*  42: 47 */   boolean adjusting = false;
/*  43:    */   public static final int MOVIE_SCALE = 2;
/*  44:    */   public static final String COMMENTARY = "written commentary";
/*  45:    */   public static final String ALERT = "spoken commentary";
/*  46:    */   public static final String CONTROL = "control";
/*  47:    */   public static final String START_END = "start-end";
/*  48:    */   private MovieReader movieReader;
/*  49:    */   private String movieTitle;
/*  50: 63 */   private ArrayList<Co57Connector.AnnotationWrapper> annotations = new ArrayList();
/*  51: 65 */   private ArrayList<String> rareVerbs = new ArrayList();
/*  52: 67 */   private ArrayList<String> commonVerbs = new ArrayList();
/*  53: 69 */   private boolean speedUp = true;
/*  54: 71 */   public static int frameDelay = 1000 / framesPerSecond;
/*  55: 74 */   private PlayerThread playerThread = null;
/*  56:    */   
/*  57:    */   public MindsEyeMoviePlayer()
/*  58:    */   {
/*  59: 77 */     Connections.getPorts(this).addSignalProcessor("startMeUp");
/*  60: 78 */     getController().getStartStopControl().addMouseListener(new PauseListener(null));
/*  61:    */     
/*  62:    */ 
/*  63:    */ 
/*  64: 82 */     getController().getFrameControl().addMouseListener(new SliderMouseWatcher(null));
/*  65: 83 */     getController().getFrameControl().addMouseMotionListener(new SliderMouseMotionWatcher(null));
/*  66:    */   }
/*  67:    */   
/*  68:    */   private void resetFrame(int x, int width)
/*  69:    */   {
/*  70: 88 */     Mark.say(
/*  71:    */     
/*  72: 90 */       new Object[] { "Arguments are:", Integer.valueOf(x), Integer.valueOf(width) });this.jumpFrame = getController().getFrameControl().getValue();
/*  73:    */   }
/*  74:    */   
/*  75:    */   private class SliderMouseMotionWatcher
/*  76:    */     extends MouseMotionAdapter
/*  77:    */   {
/*  78:    */     private SliderMouseMotionWatcher() {}
/*  79:    */     
/*  80:    */     public void mouseDragged(MouseEvent e)
/*  81:    */     {
/*  82: 95 */       Mark.say(
/*  83:    */       
/*  84: 97 */         new Object[] { "Dragging" });MindsEyeMoviePlayer.this.getViewer().getScreen().setFrameAndRepaint(MindsEyeMoviePlayer.this.getController().getFrameControl().getValue());
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   private class SliderMouseWatcher
/*  89:    */     extends MouseAdapter
/*  90:    */   {
/*  91:    */     private SliderMouseWatcher() {}
/*  92:    */     
/*  93:    */     public void mousePressed(MouseEvent e)
/*  94:    */     {
/*  95:104 */       MindsEyeMoviePlayer.this.getViewer().getController().setRunState(false);
/*  96:    */     }
/*  97:    */     
/*  98:    */     public void mouseReleased(MouseEvent e)
/*  99:    */     {
/* 100:108 */       JSlider slider = (JSlider)e.getSource();
/* 101:    */       
/* 102:    */ 
/* 103:111 */       MindsEyeMoviePlayer.this.resetFrame(e.getX(), slider.getWidth());
/* 104:112 */       MindsEyeMoviePlayer.this.getViewer().getController().setRunState(true);
/* 105:    */     }
/* 106:    */     
/* 107:    */     public void mouseExited(MouseEvent e)
/* 108:    */     {
/* 109:117 */       MindsEyeMoviePlayer.this.getViewer().getController().setRunState(true);
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   private class PauseListener
/* 114:    */     extends MouseAdapter
/* 115:    */   {
/* 116:    */     private PauseListener() {}
/* 117:    */     
/* 118:    */     public void mouseClicked(MouseEvent e)
/* 119:    */     {
/* 120:124 */       MindsEyeMoviePlayer.this.getViewer().getController().toggleRunState();
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   public MindsEyeMoviePlayer(String title)
/* 125:    */     throws IOException
/* 126:    */   {
/* 127:129 */     this();
/* 128:130 */     setMovieTitle(title);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String getName()
/* 132:    */   {
/* 133:135 */     return "Mind's eye movie player";
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setMovieReader(MovieReader movieReader)
/* 137:    */   {
/* 138:139 */     this.movieReader = movieReader;
/* 139:140 */     Mark.say(new Object[] {"Movie reader created for", movieReader.getPath() });
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void startMeUp(Object o)
/* 143:    */     throws Exception
/* 144:    */   {
/* 145:144 */     if ((o instanceof String))
/* 146:    */     {
/* 147:145 */       String title = (String)o;
/* 148:146 */       Mark.say(new Object[] {"Trying to play", title });
/* 149:148 */       if (this.playerThread != null)
/* 150:    */       {
/* 151:149 */         this.playerThread.quit();
/* 152:    */         
/* 153:151 */         Thread.sleep(1000L);
/* 154:152 */         this.annotations.clear();
/* 155:153 */         Annotation.verbs.clear();
/* 156:    */       }
/* 157:156 */       setMovieTitle(title);
/* 158:158 */       for (Co57Connector.AnnotationWrapper w : getAnnotationsForMovie(getMovieTitle())) {
/* 159:159 */         if (w.getAnnotationTitle().startsWith("y2_eval_v3")) {
/* 160:160 */           this.annotations.add(w);
/* 161:    */         }
/* 162:    */       }
/* 163:163 */       Connections.getPorts(this).transmit("control", new BetterSignal(new Object[] { "Commentary", "clear" }));
/* 164:164 */       Mark.say(new Object[] {"Annotation count is", Integer.valueOf(this.annotations.size()) });
/* 165:166 */       if ((!this.annotations.isEmpty()) && (this.annotations.size() == 1))
/* 166:    */       {
/* 167:168 */         Mark.say(new Object[] {"Working on single annotation file" });
/* 168:169 */         Co57Connector.AnnotationWrapper w = (Co57Connector.AnnotationWrapper)this.annotations.get(0);
/* 169:    */         
/* 170:171 */         this.playerThread = new PlayerThread(w);
/* 171:172 */         this.playerThread.start();
/* 172:    */       }
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String getMovieTitle()
/* 177:    */   {
/* 178:178 */     return this.movieTitle;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setMovieTitle(String movieTitle)
/* 182:    */     throws IOException
/* 183:    */   {
/* 184:182 */     this.movieTitle = movieTitle;
/* 185:183 */     MovieReader movieReader = VideoBrowser.getVideoBrowser().getMovie(movieTitle);
/* 186:184 */     movieReader.setScaleDown(2);
/* 187:185 */     setMovieReader(movieReader);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public BetterSignal display()
/* 191:    */     throws Exception
/* 192:    */   {
/* 193:191 */     int offset = 0;
/* 194:192 */     this.movieReader.seek(offset);
/* 195:193 */     BetterSignal signal = new BetterSignal(new Object[] { this.movieReader.getNextFrame(), Integer.valueOf(offset) });
/* 196:194 */     Connections.getPorts(this).transmit(signal);
/* 197:195 */     return signal;
/* 198:    */   }
/* 199:    */   
/* 200:    */   class SignalSorter
/* 201:    */     implements Comparator<BetterSignal>
/* 202:    */   {
/* 203:    */     SignalSorter() {}
/* 204:    */     
/* 205:    */     public int compare(BetterSignal a, BetterSignal b)
/* 206:    */     {
/* 207:202 */       if (((Integer)a.get(2, Integer.class)).intValue() > ((Integer)b.get(2, Integer.class)).intValue()) {
/* 208:203 */         return 1;
/* 209:    */       }
/* 210:205 */       if (((Integer)a.get(2, Integer.class)).intValue() < ((Integer)b.get(2, Integer.class)).intValue()) {
/* 211:206 */         return -1;
/* 212:    */       }
/* 213:208 */       if (((Integer)a.get(1, Integer.class)).intValue() < ((Integer)b.get(1, Integer.class)).intValue()) {
/* 214:209 */         return 1;
/* 215:    */       }
/* 216:211 */       if (((Integer)a.get(1, Integer.class)).intValue() > ((Integer)b.get(1, Integer.class)).intValue()) {
/* 217:212 */         return -1;
/* 218:    */       }
/* 219:214 */       return ((String)a.get(0, String.class)).compareTo((String)b.get(0, String.class));
/* 220:    */     }
/* 221:    */   }
/* 222:    */   
/* 223:    */   class PlayerThread
/* 224:    */     extends Thread
/* 225:    */   {
/* 226:    */     Co57Connector.AnnotationWrapper w;
/* 227:222 */     private boolean quit = false;
/* 228:    */     
/* 229:    */     public void quit()
/* 230:    */     {
/* 231:225 */       this.quit = true;
/* 232:    */     }
/* 233:    */     
/* 234:    */     public PlayerThread(Co57Connector.AnnotationWrapper w)
/* 235:    */     {
/* 236:229 */       this.w = w;
/* 237:230 */       MindsEyeMoviePlayer.this.frame = 0;
/* 238:231 */       MindsEyeMoviePlayer.this.jumpFrame = -1;
/* 239:232 */       MindsEyeMoviePlayer.this.getViewer().getController().setRunState(true);
/* 240:    */     }
/* 241:    */     
/* 242:    */     public void run()
/* 243:    */     {
/* 244:236 */       Mark.say(
/* 245:    */       
/* 246:    */ 
/* 247:    */ 
/* 248:    */ 
/* 249:    */ 
/* 250:    */ 
/* 251:    */ 
/* 252:    */ 
/* 253:    */ 
/* 254:    */ 
/* 255:    */ 
/* 256:    */ 
/* 257:    */ 
/* 258:    */ 
/* 259:    */ 
/* 260:    */ 
/* 261:    */ 
/* 262:    */ 
/* 263:    */ 
/* 264:    */ 
/* 265:    */ 
/* 266:    */ 
/* 267:    */ 
/* 268:    */ 
/* 269:    */ 
/* 270:    */ 
/* 271:    */ 
/* 272:    */ 
/* 273:    */ 
/* 274:    */ 
/* 275:    */ 
/* 276:    */ 
/* 277:    */ 
/* 278:    */ 
/* 279:    */ 
/* 280:    */ 
/* 281:    */ 
/* 282:    */ 
/* 283:    */ 
/* 284:    */ 
/* 285:    */ 
/* 286:    */ 
/* 287:    */ 
/* 288:    */ 
/* 289:    */ 
/* 290:    */ 
/* 291:    */ 
/* 292:    */ 
/* 293:    */ 
/* 294:    */ 
/* 295:    */ 
/* 296:    */ 
/* 297:    */ 
/* 298:    */ 
/* 299:    */ 
/* 300:    */ 
/* 301:    */ 
/* 302:    */ 
/* 303:    */ 
/* 304:    */ 
/* 305:    */ 
/* 306:    */ 
/* 307:    */ 
/* 308:    */ 
/* 309:    */ 
/* 310:    */ 
/* 311:    */ 
/* 312:    */ 
/* 313:    */ 
/* 314:    */ 
/* 315:    */ 
/* 316:    */ 
/* 317:    */ 
/* 318:    */ 
/* 319:    */ 
/* 320:    */ 
/* 321:    */ 
/* 322:    */ 
/* 323:    */ 
/* 324:    */ 
/* 325:    */ 
/* 326:    */ 
/* 327:    */ 
/* 328:    */ 
/* 329:    */ 
/* 330:    */ 
/* 331:    */ 
/* 332:    */ 
/* 333:    */ 
/* 334:    */ 
/* 335:    */ 
/* 336:    */ 
/* 337:    */ 
/* 338:    */ 
/* 339:    */ 
/* 340:    */ 
/* 341:    */ 
/* 342:    */ 
/* 343:    */ 
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
/* 354:    */ 
/* 355:    */ 
/* 356:    */ 
/* 357:    */ 
/* 358:    */ 
/* 359:    */ 
/* 360:    */ 
/* 361:    */ 
/* 362:    */ 
/* 363:    */ 
/* 364:    */ 
/* 365:    */ 
/* 366:    */ 
/* 367:    */ 
/* 368:    */ 
/* 369:    */ 
/* 370:    */ 
/* 371:    */ 
/* 372:    */ 
/* 373:    */ 
/* 374:    */ 
/* 375:    */ 
/* 376:    */ 
/* 377:    */ 
/* 378:    */ 
/* 379:    */ 
/* 380:    */ 
/* 381:    */ 
/* 382:    */ 
/* 383:    */ 
/* 384:    */ 
/* 385:    */ 
/* 386:    */ 
/* 387:    */ 
/* 388:380 */         new Object[] { "Entering playSmart" });int descriptionCount = 0;MindsEyeMoviePlayer.this.rareVerbs.clear();MindsEyeMoviePlayer.this.commonVerbs.clear();Mark.say(new Object[] { "Annotation start/end", Integer.valueOf(this.w.getStartFrame()), "/", Integer.valueOf(this.w.getEndFrame()) });MindsEyeMoviePlayer.this.getViewer().setBounds(this.w.getStartFrame(), this.w.getEndFrame());
/* 389:245 */       if (MindsEyeMoviePlayer.this.movieReader == null)
/* 390:    */       {
/* 391:246 */         Mark.err(new Object[] {"No movie known to MindsEyeMoviePlayer" });
/* 392:247 */         return;
/* 393:    */       }
/* 394:252 */       ArrayList<BetterSignal> signals = null;
/* 395:253 */       if (this.w != null)
/* 396:    */       {
/* 397:    */         try
/* 398:    */         {
/* 399:257 */           signals = Annotation.extractRoles(this.w.getAnnotation());
/* 400:    */         }
/* 401:    */         catch (Exception e)
/* 402:    */         {
/* 403:260 */           Mark.err(new Object[] {"Blew out extracting roles from annotation wrapper" });
/* 404:    */         }
/* 405:262 */         Mark.say(new Object[] {"Signal count", Integer.valueOf(signals.size()) });
/* 406:263 */         Mark.say(new Object[] {"Verb count", Integer.valueOf(Annotation.verbs.size()) });
/* 407:264 */         ArrayList<String> sortedVerbs = new ArrayList();
/* 408:265 */         sortedVerbs.addAll(Annotation.verbs.keySet());
/* 409:266 */         Collections.sort(sortedVerbs);
/* 410:267 */         int verbCount = sortedVerbs.size();
/* 411:268 */         int eventCount = 0;
/* 412:269 */         for (String verb : sortedVerbs) {
/* 413:270 */           eventCount += ((Integer)Annotation.verbs.get(verb)).intValue();
/* 414:    */         }
/* 415:273 */         int eventsPerVerb = eventCount / verbCount;
/* 416:274 */         Mark.say(new Object[] {"Expected events per verb", Integer.valueOf(eventsPerVerb) });
/* 417:275 */         for (String verb : sortedVerbs)
/* 418:    */         {
/* 419:276 */           int verbInstances = ((Integer)Annotation.verbs.get(verb)).intValue();
/* 420:277 */           if (verbInstances <= eventsPerVerb * MindsEyeMoviePlayer.underRepresented / 100) {
/* 421:278 */             MindsEyeMoviePlayer.this.rareVerbs.add(verb);
/* 422:280 */           } else if (verbInstances >= eventsPerVerb * MindsEyeMoviePlayer.overRepresented / 100) {
/* 423:281 */             MindsEyeMoviePlayer.this.commonVerbs.add(verb);
/* 424:    */           }
/* 425:    */         }
/* 426:284 */         for (String verb : sortedVerbs) {
/* 427:285 */           Mark.say(new Object[] {"   ", verb, Annotation.verbs.get(verb), Integer.valueOf(((Integer)Annotation.verbs.get(verb)).intValue() * 100 / eventsPerVerb) });
/* 428:    */         }
/* 429:288 */         Mark.say(new Object[] {"Rare verbs", MindsEyeMoviePlayer.this.rareVerbs, "all less than", Integer.valueOf(MindsEyeMoviePlayer.underRepresented), "% of expected" });
/* 430:    */         
/* 431:290 */         Mark.say(new Object[] {"Common verbs", MindsEyeMoviePlayer.this.commonVerbs, "all greater than", Integer.valueOf(MindsEyeMoviePlayer.overRepresented), "% of expected" });
/* 432:    */         
/* 433:292 */         Mark.say(new Object[] {"Original signal count:", Integer.valueOf(signals.size()) });
/* 434:293 */         signals = MindsEyeMoviePlayer.this.consolodateSignals(signals);
/* 435:294 */         Mark.say(new Object[] {"Consolodated signal count:", Integer.valueOf(signals.size()) });
/* 436:    */       }
/* 437:    */       else
/* 438:    */       {
/* 439:297 */         Mark.err(new Object[] {"No consolodated annotations for this section!!!!!!" });
/* 440:    */       }
/* 441:300 */       Collections.sort(signals, new MindsEyeMoviePlayer.SignalSorter(MindsEyeMoviePlayer.this));
/* 442:    */       
/* 443:302 */       Mark.say(new Object[] {"Sorted and signal count:", Integer.valueOf(signals.size()) });
/* 444:    */       
/* 445:304 */       signals = MindsEyeMoviePlayer.this.pruneRedundantStatements(signals);
/* 446:    */       
/* 447:306 */       Mark.say(new Object[] {"Pruned signal count:", Integer.valueOf(signals.size()) });
/* 448:    */       
/* 449:308 */       String english = "";
/* 450:    */       try
/* 451:    */       {
/* 452:312 */         for (int i = 0; i < signals.size(); i++)
/* 453:    */         {
/* 454:315 */           if (MindsEyeMoviePlayer.this.jumpFrame >= 0)
/* 455:    */           {
/* 456:316 */             Mark.say(new Object[] {"Time to jump to different frame with i not same as", Integer.valueOf(i) });
/* 457:317 */             MindsEyeMoviePlayer.this.frame = MindsEyeMoviePlayer.this.jumpFrame;
/* 458:318 */             MindsEyeMoviePlayer.this.jumpFrame = -1;
/* 459:319 */             i = MindsEyeMoviePlayer.this.findNextSignalIndex(MindsEyeMoviePlayer.this.frame, signals);
/* 460:320 */             if (i == signals.size()) {
/* 461:    */               break;
/* 462:    */             }
/* 463:    */           }
/* 464:324 */           else if (this.quit)
/* 465:    */           {
/* 466:325 */             Mark.say(new Object[] {"Quitting movie player" });
/* 467:326 */             return;
/* 468:    */           }
/* 469:329 */           BetterSignal signal = (BetterSignal)signals.get(i);
/* 470:330 */           english = ((String)signal.get(0, String.class)).trim();
/* 471:    */           
/* 472:332 */           english = english.substring(0, english.length() - 1);
/* 473:333 */           int startCurrentRange = ((Integer)signal.get(1, Integer.class)).intValue();
/* 474:334 */           int endCurrentRange = ((Integer)signal.get(2, Integer.class)).intValue();
/* 475:    */           
/* 476:336 */           String sentence = english + " at" + MindsEyeMoviePlayer.quickTime(endCurrentRange) + ".";
/* 477:340 */           if (MindsEyeMoviePlayer.this.seconds(endCurrentRange - 3 * MindsEyeMoviePlayer.padding - MindsEyeMoviePlayer.this.frame) >= 0)
/* 478:    */           {
/* 479:341 */             playSlow(MindsEyeMoviePlayer.this.frame + MindsEyeMoviePlayer.padding);
/* 480:    */             
/* 481:343 */             String fastForward = "Fast forward to " + MindsEyeMoviePlayer.quickTime(endCurrentRange - MindsEyeMoviePlayer.padding) + ".";
/* 482:344 */             Mark.say(new Object[] {"Play by play:", fastForward });
/* 483:345 */             Connections.getPorts(MindsEyeMoviePlayer.this).transmit("written commentary", new BetterSignal(new Object[] { "Commentary", Html.p(fastForward) }));
/* 484:346 */             playFast(endCurrentRange - MindsEyeMoviePlayer.padding);
/* 485:    */           }
/* 486:350 */           playSlow(endCurrentRange);
/* 487:    */           
/* 488:352 */           Mark.say(new Object[] {"Play by play:", english.trim() + " from " + MindsEyeMoviePlayer.quickTime(startCurrentRange) + " to " + MindsEyeMoviePlayer.quickTime(endCurrentRange) });
/* 489:354 */           if (MindsEyeMoviePlayer.this.rareVerbs.contains(signal.get(3, String.class)))
/* 490:    */           {
/* 491:355 */             String message = Html.p(Html.red(sentence));
/* 492:356 */             Connections.getPorts(MindsEyeMoviePlayer.this).transmit("spoken commentary", new BetterSignal(new Object[] { "Commentary", message }));
/* 493:357 */             Connections.getPorts(MindsEyeMoviePlayer.this).transmit("written commentary", new BetterSignal(new Object[] { "Commentary", message }));
/* 494:    */           }
/* 495:359 */           else if (MindsEyeMoviePlayer.this.commonVerbs.contains(signal.get(3, String.class)))
/* 496:    */           {
/* 497:360 */             String message = Html.p(Html.gray(sentence));
/* 498:361 */             Connections.getPorts(MindsEyeMoviePlayer.this).transmit("written commentary", new BetterSignal(new Object[] { "Commentary", message }));
/* 499:    */           }
/* 500:    */           else
/* 501:    */           {
/* 502:364 */             String message = Html.p(sentence);
/* 503:365 */             Connections.getPorts(MindsEyeMoviePlayer.this).transmit("written commentary", new BetterSignal(new Object[] { "Commentary", message }));
/* 504:    */           }
/* 505:368 */           descriptionCount++;
/* 506:    */         }
/* 507:373 */         playFast(this.w.getEndFrame());
/* 508:    */       }
/* 509:    */       catch (Exception e)
/* 510:    */       {
/* 511:377 */         Mark.say(new Object[] {"Blew out playing movie" });
/* 512:    */       }
/* 513:379 */       Mark.say(new Object[] {"Descriptors:", Integer.valueOf(descriptionCount) });
/* 514:    */     }
/* 515:    */     
/* 516:    */     public void playFast(int endCurrentRange)
/* 517:    */       throws IOException, InterruptedException
/* 518:    */     {
/* 519:    */       for (;;)
/* 520:    */       {
/* 521:386 */         if (MindsEyeMoviePlayer.this.jumpFrame >= 0) {
/* 522:387 */           return;
/* 523:    */         }
/* 524:389 */         if (this.quit) {
/* 525:390 */           return;
/* 526:    */         }
/* 527:392 */         if ((!MindsEyeMoviePlayer.this.getViewer().getController().isRunning()) || (MindsEyeMoviePlayer.this.isAdjusting()))
/* 528:    */         {
/* 529:393 */           Thread.sleep(1000L);
/* 530:    */         }
/* 531:    */         else
/* 532:    */         {
/* 533:398 */           BufferedImage bufferedReader = MindsEyeMoviePlayer.this.movieReader.getNextFrame();
/* 534:399 */           if ((bufferedReader == null) || (MindsEyeMoviePlayer.this.frame > endCurrentRange - MindsEyeMoviePlayer.padding)) {
/* 535:    */             break;
/* 536:    */           }
/* 537:401 */           MindsEyeMoviePlayer.this.playFrame(MindsEyeMoviePlayer.this.frame, bufferedReader);
/* 538:    */           
/* 539:403 */           MindsEyeMoviePlayer.this.frame += MindsEyeMoviePlayer.skip;
/* 540:    */           
/* 541:405 */           MindsEyeMoviePlayer.this.movieReader.seek(MindsEyeMoviePlayer.this.frame);
/* 542:    */         }
/* 543:    */       }
/* 544:    */       BufferedImage bufferedReader;
/* 545:    */     }
/* 546:    */     
/* 547:    */     public void playSlow(int endCurrentRange)
/* 548:    */       throws IOException, InterruptedException
/* 549:    */     {
/* 550:    */       for (;;)
/* 551:    */       {
/* 552:417 */         if (MindsEyeMoviePlayer.this.jumpFrame >= 0) {
/* 553:418 */           return;
/* 554:    */         }
/* 555:420 */         if (this.quit) {
/* 556:421 */           return;
/* 557:    */         }
/* 558:423 */         if ((!MindsEyeMoviePlayer.this.getViewer().getController().isRunning()) || (MindsEyeMoviePlayer.this.isAdjusting()))
/* 559:    */         {
/* 560:424 */           Thread.sleep(1000L);
/* 561:    */         }
/* 562:    */         else
/* 563:    */         {
/* 564:427 */           BufferedImage bufferedReader = MindsEyeMoviePlayer.this.movieReader.getNextFrame();
/* 565:428 */           if ((bufferedReader == null) || (MindsEyeMoviePlayer.this.frame > endCurrentRange)) {
/* 566:    */             break;
/* 567:    */           }
/* 568:429 */           Thread.sleep(MindsEyeMoviePlayer.frameDelay);
/* 569:430 */           MindsEyeMoviePlayer.this.playFrame(MindsEyeMoviePlayer.this.frame, bufferedReader);
/* 570:431 */           MindsEyeMoviePlayer.this.frame += 1;
/* 571:    */         }
/* 572:    */       }
/* 573:    */       BufferedImage bufferedReader;
/* 574:    */     }
/* 575:    */   }
/* 576:    */   
/* 577:    */   private int findNextSignalIndex(int frame, ArrayList<BetterSignal> signals)
/* 578:    */   {
/* 579:442 */     for (int i = 0; i < signals.size(); i++)
/* 580:    */     {
/* 581:443 */       BetterSignal signal = (BetterSignal)signals.get(i);
/* 582:444 */       int end = ((Integer)signal.get(2, Integer.class)).intValue();
/* 583:445 */       if (end > frame) {
/* 584:446 */         return i;
/* 585:    */       }
/* 586:    */     }
/* 587:449 */     return signals.size();
/* 588:    */   }
/* 589:    */   
/* 590:    */   public void playFrame(int frame, BufferedImage bufferedReader)
/* 591:    */   {
/* 592:453 */     getViewer().setImage(bufferedReader);
/* 593:454 */     getViewer().setFrame(frame);
/* 594:    */   }
/* 595:    */   
/* 596:    */   private int seconds(int i)
/* 597:    */   {
/* 598:458 */     return i / framesPerSecond;
/* 599:    */   }
/* 600:    */   
/* 601:    */   private ArrayList<BetterSignal> pruneRedundantStatements(ArrayList<BetterSignal> signals)
/* 602:    */   {
/* 603:462 */     ArrayList<BetterSignal> result = new ArrayList();
/* 604:463 */     BetterSignal recent = null;
/* 605:464 */     for (BetterSignal signal : signals) {
/* 606:465 */       if (recent == null)
/* 607:    */       {
/* 608:466 */         recent = signal;
/* 609:467 */         result.add(signal);
/* 610:    */       }
/* 611:469 */       else if (!((String)signal.get(0, String.class)).equals(recent.get(0, String.class)))
/* 612:    */       {
/* 613:472 */         recent = signal;
/* 614:473 */         result.add(signal);
/* 615:    */       }
/* 616:    */     }
/* 617:476 */     return result;
/* 618:    */   }
/* 619:    */   
/* 620:    */   public static String time(int t)
/* 621:    */   {
/* 622:480 */     int sec = t / framesPerSecond;
/* 623:481 */     int min = sec / 60;
/* 624:482 */     sec %= 60;
/* 625:483 */     return min + " minutes, " + sec + " seconds";
/* 626:    */   }
/* 627:    */   
/* 628:    */   public static String quickTime(int t)
/* 629:    */   {
/* 630:487 */     int sec = t / framesPerSecond;
/* 631:488 */     int min = sec / 60;
/* 632:489 */     sec %= 60;
/* 633:490 */     return " " + min + " min " + sec + " sec";
/* 634:    */   }
/* 635:    */   
/* 636:    */   private ArrayList<BetterSignal> consolodateSignals(ArrayList<BetterSignal> inputSignals)
/* 637:    */   {
/* 638:494 */     ArrayList<BetterSignal> consolodatedSignals = new ArrayList();
/* 639:495 */     for (BetterSignal signal : inputSignals) {
/* 640:497 */       if (consolodatedSignals.isEmpty())
/* 641:    */       {
/* 642:498 */         consolodatedSignals.add(signal);
/* 643:499 */         Mark.say(new Object[] {"Starting" });
/* 644:    */       }
/* 645:    */       else
/* 646:    */       {
/* 647:503 */         BetterSignal last = (BetterSignal)consolodatedSignals.get(consolodatedSignals.size() - 1);
/* 648:504 */         if (last.get(1, Integer.class) == signal.get(1, Integer.class))
/* 649:    */         {
/* 650:506 */           Mark.say(new Object[] {"Consolodating" });
/* 651:507 */           last.put(0, (String)last.get(0, String.class) + " " + (String)signal.get(0, String.class));
/* 652:    */         }
/* 653:    */         else
/* 654:    */         {
/* 655:512 */           consolodatedSignals.add(signal);
/* 656:    */         }
/* 657:    */       }
/* 658:    */     }
/* 659:515 */     return consolodatedSignals;
/* 660:    */   }
/* 661:    */   
/* 662:    */   public boolean isSpeedUp()
/* 663:    */   {
/* 664:519 */     return this.speedUp;
/* 665:    */   }
/* 666:    */   
/* 667:    */   public void setSpeedUp(boolean speedUp)
/* 668:    */   {
/* 669:523 */     this.speedUp = speedUp;
/* 670:    */   }
/* 671:    */   
/* 672:    */   public static void main(String[] args)
/* 673:    */     throws Exception
/* 674:    */   {
/* 675:527 */     JFrame f = new JFrame("Image reader test app");
/* 676:528 */     f.addWindowListener(new WindowAdapter()
/* 677:    */     {
/* 678:    */       public void windowClosing(WindowEvent e)
/* 679:    */       {
/* 680:530 */         System.exit(0);
/* 681:    */       }
/* 682:532 */     });
/* 683:533 */     MindsEyeMovieViewer mindsEyeMovieViewer = new MindsEyeMovieViewer();
/* 684:534 */     MindsEyeMoviePlayer mindsEyeMoviePlayer = new MindsEyeMoviePlayer();
/* 685:    */     
/* 686:536 */     f.getContentPane().add(mindsEyeMovieViewer);
/* 687:537 */     f.setBounds(0, 0, 500, 400);
/* 688:    */     
/* 689:539 */     f.setVisible(true);
/* 690:    */     
/* 691:541 */     Connections.wire(mindsEyeMoviePlayer, mindsEyeMovieViewer);
/* 692:    */     
/* 693:543 */     Co57Connector connector = new Co57Connector();
/* 694:    */     
/* 695:545 */     Set<String> moviesThatHaveAnnotations = connector.getMoviesHavingAnnotations();
/* 696:546 */     Mark.say(new Object[] {"There are", Integer.valueOf(moviesThatHaveAnnotations.size()), "with annotations" });
/* 697:547 */     String firstMovieTitle = null;
/* 698:548 */     for (String movieTitle : moviesThatHaveAnnotations) {
/* 699:550 */       if (firstMovieTitle == null) {
/* 700:551 */         firstMovieTitle = movieTitle;
/* 701:    */       }
/* 702:    */     }
/* 703:556 */     Mark.say(new Object[] {"First movie", firstMovieTitle });
/* 704:    */     
/* 705:558 */     mindsEyeMoviePlayer.setMovieTitle(firstMovieTitle);
/* 706:    */     
/* 707:560 */     mindsEyeMoviePlayer.display();
/* 708:    */   }
/* 709:    */   
/* 710:    */   public MindsEyeMovieViewer getViewer()
/* 711:    */   {
/* 712:565 */     if (this.viewer == null) {
/* 713:566 */       this.viewer = new MindsEyeMovieViewer();
/* 714:    */     }
/* 715:568 */     return this.viewer;
/* 716:    */   }
/* 717:    */   
/* 718:    */   public MindsEyeViewerController getController()
/* 719:    */   {
/* 720:572 */     return getViewer().getController();
/* 721:    */   }
/* 722:    */   
/* 723:    */   public boolean isAdjusting()
/* 724:    */   {
/* 725:576 */     return this.adjusting;
/* 726:    */   }
/* 727:    */   
/* 728:    */   public void setAdjusting(boolean adjusting)
/* 729:    */   {
/* 730:580 */     this.adjusting = adjusting;
/* 731:    */   }
/* 732:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     obsolete.mindsEye.MindsEyeMoviePlayer
 * JD-Core Version:    0.7.0.1
 */