/*   1:    */ package obsolete.mediaFX;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import adamKraft.Co57Connector;
/*   5:    */ import adamKraft.Co57Connector.AnnotationWrapper;
/*   6:    */ import com.ascent.gui.frame.alternative.ALauncher;
/*   7:    */ import com.ascent.gui.frame.alternative.Completable;
/*   8:    */ import connections.Connections;
/*   9:    */ import connections.Ports;
/*  10:    */ import connections.WiredBox;
/*  11:    */ import java.awt.Container;
/*  12:    */ import java.awt.event.WindowAdapter;
/*  13:    */ import java.awt.event.WindowEvent;
/*  14:    */ import java.io.IOException;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.Collections;
/*  17:    */ import java.util.Comparator;
/*  18:    */ import java.util.HashMap;
/*  19:    */ import java.util.Set;
/*  20:    */ import javax.swing.JFrame;
/*  21:    */ import michaelBehr.Annotation;
/*  22:    */ import obsolete.mindsEye.MindsEyeMovieViewer;
/*  23:    */ import text.Html;
/*  24:    */ import utils.Mark;
/*  25:    */ 
/*  26:    */ public class FXMediaPlayer
/*  27:    */   extends Co57Connector
/*  28:    */   implements WiredBox
/*  29:    */ {
/*  30: 30 */   public static String URL = "url";
/*  31: 32 */   public static String TIME = "time";
/*  32: 34 */   public static String SET_TIME = "set time";
/*  33: 36 */   public static int framesPerSecond = 60;
/*  34: 38 */   private static int underRepresented = 50;
/*  35: 40 */   private static int overRepresented = 200;
/*  36: 42 */   private static int fastForwardTrigger = 5000;
/*  37: 44 */   private static int fastForwardGap = 4000;
/*  38: 46 */   private int previousReportTime = 0;
/*  39: 48 */   private int previousEventEndTime = 0;
/*  40: 50 */   private int resetSignalsTrigger = 5000;
/*  41:    */   public static final String COMMENTARY = "written commentary";
/*  42:    */   public static final String ALERT = "spoken commentary";
/*  43:    */   public static final String CONTROL = "control";
/*  44:    */   private String movieTitle;
/*  45: 79 */   private ArrayList<Co57Connector.AnnotationWrapper> annotations = new ArrayList();
/*  46: 81 */   private ArrayList<String> rareVerbs = new ArrayList();
/*  47: 83 */   private ArrayList<String> commonVerbs = new ArrayList();
/*  48:    */   private ArrayList<BetterSignal> signals;
/*  49:    */   
/*  50:    */   public FXMediaPlayer()
/*  51:    */   {
/*  52: 94 */     Connections.getPorts(this).addSignalProcessor("setMedia");
/*  53:    */     
/*  54: 96 */     Connections.getPorts(this).addSignalProcessor(TIME, "report");
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String getName()
/*  58:    */   {
/*  59:156 */     return "Mind's eye movie player";
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void report(Object o)
/*  63:    */   {
/*  64:166 */     if ((o instanceof Double))
/*  65:    */     {
/*  66:167 */       int time = ((Double)o).intValue();
/*  67:172 */       if (Math.abs(time - this.previousReportTime) > this.resetSignalsTrigger)
/*  68:    */       {
/*  69:174 */         String reset = "Resetting to " + formatTime(time) + ".";
/*  70:175 */         Connections.getPorts(this).transmit("written commentary", new BetterSignal(new Object[] { "Commentary", Html.p(reset) }));
/*  71:    */       }
/*  72:179 */       this.previousReportTime = time;
/*  73:182 */       if (this.signals != null) {
/*  74:196 */         for (BetterSignal signal : this.signals) {
/*  75:197 */           if (!((Boolean)signal.get(4, Boolean.class)).booleanValue())
/*  76:    */           {
/*  77:198 */             int startFrame = ((Integer)signal.get(1, Integer.class)).intValue();
/*  78:199 */             int endFrame = ((Integer)signal.get(2, Integer.class)).intValue();
/*  79:200 */             int startTime = startFrame * 1000 / framesPerSecond;
/*  80:201 */             int endTime = endFrame * 1000 / framesPerSecond;
/*  81:202 */             if (time > startTime)
/*  82:    */             {
/*  83:205 */               signal.put(4, Boolean.valueOf(true));
/*  84:    */               
/*  85:    */ 
/*  86:208 */               this.previousEventEndTime = Math.max(this.previousEventEndTime, endTime);
/*  87:    */               
/*  88:    */ 
/*  89:    */ 
/*  90:212 */               String english = ((String)signal.get(0, String.class)).trim();
/*  91:213 */               english = english.substring(0, english.length() - 1);
/*  92:    */               
/*  93:215 */               String sentence = english + " at" + formatTime(startTime) + ".";
/*  94:219 */               if (this.rareVerbs.contains(signal.get(3, String.class)))
/*  95:    */               {
/*  96:220 */                 String message = Html.p(Html.red(sentence));
/*  97:221 */                 Connections.getPorts(this).transmit("spoken commentary", new BetterSignal(new Object[] { "Commentary", message }));
/*  98:222 */                 Connections.getPorts(this).transmit("written commentary", new BetterSignal(new Object[] { "Commentary", message }));
/*  99:    */               }
/* 100:224 */               else if (this.commonVerbs.contains(signal.get(3, String.class)))
/* 101:    */               {
/* 102:225 */                 String message = Html.p(Html.gray(sentence));
/* 103:226 */                 Connections.getPorts(this).transmit("written commentary", new BetterSignal(new Object[] { "Commentary", message }));
/* 104:    */               }
/* 105:    */               else
/* 106:    */               {
/* 107:229 */                 String message = Html.p(sentence);
/* 108:230 */                 Connections.getPorts(this).transmit("written commentary", new BetterSignal(new Object[] { "Commentary", message }));
/* 109:    */               }
/* 110:    */             }
/* 111:    */             else
/* 112:    */             {
/* 113:240 */               if ((time <= this.previousEventEndTime + fastForwardTrigger) || (startTime - time <= fastForwardTrigger)) {
/* 114:    */                 break;
/* 115:    */               }
/* 116:244 */               int newTime = startTime - fastForwardGap;
/* 117:245 */               this.previousReportTime = newTime;
/* 118:246 */               this.previousEventEndTime = Math.max(this.previousEventEndTime, endTime);
/* 119:247 */               String fastForward = "Fast forward to " + formatTime(newTime) + ".";
/* 120:    */               
/* 121:249 */               Connections.getPorts(this).transmit("written commentary", new BetterSignal(new Object[] { "Commentary", Html.p(fastForward) }));
/* 122:250 */               Connections.getPorts(this).transmit(SET_TIME, Integer.valueOf(newTime));
/* 123:251 */               break;
/* 124:    */             }
/* 125:    */           }
/* 126:    */         }
/* 127:    */       }
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   private void resetSignals(int time, ArrayList<BetterSignal> signals)
/* 132:    */   {
/* 133:263 */     for (BetterSignal signal : signals)
/* 134:    */     {
/* 135:264 */       int startFrame = ((Integer)signal.get(1, Integer.class)).intValue();
/* 136:265 */       int endFrame = ((Integer)signal.get(2, Integer.class)).intValue();
/* 137:266 */       int startTime = startFrame * 1000 / framesPerSecond;
/* 138:267 */       int endTime = endFrame * 1000 / framesPerSecond;
/* 139:268 */       if (startTime < time) {
/* 140:269 */         signal.put(4, Boolean.valueOf(true));
/* 141:    */       } else {
/* 142:272 */         signal.put(4, Boolean.valueOf(false));
/* 143:    */       }
/* 144:274 */       this.previousReportTime = time;
/* 145:275 */       this.previousEventEndTime = time;
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setMedia(Object o)
/* 150:    */     throws Exception
/* 151:    */   {
/* 152:281 */     if ((o instanceof String))
/* 153:    */     {
/* 154:282 */       String title = (String)o;
/* 155:    */       
/* 156:    */ 
/* 157:    */ 
/* 158:    */ 
/* 159:287 */       MyVisionCompletable completer = new MyVisionCompletable(title);
/* 160:    */       
/* 161:289 */       ALauncher.launchDataLoader(completer);
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   private class MyVisionCompletable
/* 166:    */     extends Completable
/* 167:    */   {
/* 168:295 */     private boolean done = false;
/* 169:    */     String title;
/* 170:    */     
/* 171:    */     public MyVisionCompletable(String title)
/* 172:    */     {
/* 173:300 */       this.title = title;
/* 174:    */     }
/* 175:    */     
/* 176:    */     public void run()
/* 177:    */     {
/* 178:304 */       Mark.say(
/* 179:    */       
/* 180:    */ 
/* 181:    */ 
/* 182:    */ 
/* 183:    */ 
/* 184:    */ 
/* 185:    */ 
/* 186:    */ 
/* 187:    */ 
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:    */ 
/* 194:    */ 
/* 195:    */ 
/* 196:    */ 
/* 197:    */ 
/* 198:    */ 
/* 199:    */ 
/* 200:    */ 
/* 201:    */ 
/* 202:    */ 
/* 203:    */ 
/* 204:    */ 
/* 205:    */ 
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:    */ 
/* 211:    */ 
/* 212:    */ 
/* 213:    */ 
/* 214:    */ 
/* 215:    */ 
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:    */ 
/* 221:    */ 
/* 222:    */ 
/* 223:    */ 
/* 224:    */ 
/* 225:    */ 
/* 226:    */ 
/* 227:    */ 
/* 228:    */ 
/* 229:355 */         new Object[] { "Trying to play", this.title });
/* 230:    */       try
/* 231:    */       {
/* 232:315 */         FXMediaPlayer.this.setMovieTitle(this.title);
/* 233:    */       }
/* 234:    */       catch (IOException e)
/* 235:    */       {
/* 236:318 */         Mark.err(new Object[] {"Blew out at FXMediaPlaer.MyVisionCompletable" });
/* 237:    */       }
/* 238:321 */       FXMediaPlayer.this.annotations.clear();
/* 239:322 */       Annotation.verbs.clear();
/* 240:    */       
/* 241:324 */       String revisedTitle = FXMediaSelector.replaceExtension(FXMediaPlayer.this.getMovieTitle(), Co57Connector.ANNOTATION_REFERENCE_FORMAT);
/* 242:    */       
/* 243:326 */       Mark.say(new Object[] {"Looking for annotations for", revisedTitle });
/* 244:328 */       for (Co57Connector.AnnotationWrapper w : FXMediaPlayer.this.getAnnotationsForMovie(revisedTitle)) {
/* 245:329 */         if (w.getAnnotationTitle().startsWith("y2_eval_v3")) {
/* 246:330 */           FXMediaPlayer.this.annotations.add(w);
/* 247:    */         }
/* 248:    */       }
/* 249:334 */       Mark.say(new Object[] {"Annotation count is", Integer.valueOf(FXMediaPlayer.this.annotations.size()) });
/* 250:336 */       if ((!FXMediaPlayer.this.annotations.isEmpty()) && (FXMediaPlayer.this.annotations.size() == 1))
/* 251:    */       {
/* 252:338 */         Mark.say(new Object[] {"Working on single annotation file" });
/* 253:339 */         Co57Connector.AnnotationWrapper w = (Co57Connector.AnnotationWrapper)FXMediaPlayer.this.annotations.get(0);
/* 254:    */         
/* 255:341 */         FXMediaPlayer.this.signals = FXMediaPlayer.this.prepareAnnotations(w);
/* 256:    */         
/* 257:343 */         String mp4Title = FXMediaSelector.toMediaUrl(this.title, Co57Connector.MOVIE_FORMAT);
/* 258:    */         
/* 259:345 */         FXMediaPlayer.this.previousReportTime = 0;
/* 260:    */         
/* 261:347 */         FXMediaPlayer.this.previousEventEndTime = 0;
/* 262:    */         
/* 263:349 */         Connections.getPorts(FXMediaPlayer.this).transmit(FXMediaPlayer.URL, mp4Title);
/* 264:    */         
/* 265:351 */         Connections.getPorts(FXMediaPlayer.this).transmit("control", new BetterSignal(new Object[] { "Commentary", "clear" }));
/* 266:    */       }
/* 267:354 */       this.done = true;
/* 268:    */     }
/* 269:    */     
/* 270:    */     public boolean cleanup()
/* 271:    */     {
/* 272:359 */       return false;
/* 273:    */     }
/* 274:    */     
/* 275:    */     public boolean isDone()
/* 276:    */     {
/* 277:364 */       return this.done;
/* 278:    */     }
/* 279:    */   }
/* 280:    */   
/* 281:    */   public String getMovieTitle()
/* 282:    */   {
/* 283:370 */     return this.movieTitle;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setMovieTitle(String movieTitle)
/* 287:    */     throws IOException
/* 288:    */   {
/* 289:374 */     this.movieTitle = movieTitle;
/* 290:    */   }
/* 291:    */   
/* 292:    */   class SignalSorter
/* 293:    */     implements Comparator<BetterSignal>
/* 294:    */   {
/* 295:    */     SignalSorter() {}
/* 296:    */     
/* 297:    */     public int compare(BetterSignal a, BetterSignal b)
/* 298:    */     {
/* 299:396 */       if (((Integer)a.get(1, Integer.class)).intValue() > ((Integer)b.get(1, Integer.class)).intValue()) {
/* 300:397 */         return 1;
/* 301:    */       }
/* 302:399 */       if (((Integer)a.get(1, Integer.class)).intValue() < ((Integer)b.get(1, Integer.class)).intValue()) {
/* 303:400 */         return -1;
/* 304:    */       }
/* 305:402 */       if (((Integer)a.get(2, Integer.class)).intValue() > ((Integer)b.get(2, Integer.class)).intValue()) {
/* 306:403 */         return 1;
/* 307:    */       }
/* 308:405 */       if (((Integer)a.get(2, Integer.class)).intValue() < ((Integer)b.get(2, Integer.class)).intValue()) {
/* 309:406 */         return -1;
/* 310:    */       }
/* 311:409 */       return ((String)a.get(0, String.class)).compareTo((String)b.get(0, String.class));
/* 312:    */     }
/* 313:    */   }
/* 314:    */   
/* 315:    */   private ArrayList<BetterSignal> prepareAnnotations(Co57Connector.AnnotationWrapper w)
/* 316:    */   {
/* 317:415 */     Mark.say(
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
/* 388:    */ 
/* 389:    */ 
/* 390:    */ 
/* 391:489 */       new Object[] { "Entering prepareAnnotations" });int descriptionCount = 0;this.rareVerbs.clear();this.commonVerbs.clear();Mark.say(new Object[] { "Annotation start/end", Integer.valueOf(w.getStartFrame()), "/", Integer.valueOf(w.getEndFrame()) });ArrayList<BetterSignal> signals = null;
/* 392:431 */     if (w != null)
/* 393:    */     {
/* 394:    */       try
/* 395:    */       {
/* 396:435 */         signals = Annotation.extractRoles(w.getAnnotation());
/* 397:    */       }
/* 398:    */       catch (Exception e)
/* 399:    */       {
/* 400:438 */         Mark.err(new Object[] {"Blew out extracting roles from annotation wrapper" });
/* 401:    */       }
/* 402:440 */       Mark.say(new Object[] {"Signal count", Integer.valueOf(signals.size()) });
/* 403:441 */       Mark.say(new Object[] {"Verb count", Integer.valueOf(Annotation.verbs.size()) });
/* 404:442 */       ArrayList<String> sortedVerbs = new ArrayList();
/* 405:443 */       sortedVerbs.addAll(Annotation.verbs.keySet());
/* 406:444 */       Collections.sort(sortedVerbs);
/* 407:445 */       int verbCount = sortedVerbs.size();
/* 408:446 */       int eventCount = 0;
/* 409:447 */       for (String verb : sortedVerbs) {
/* 410:448 */         eventCount += ((Integer)Annotation.verbs.get(verb)).intValue();
/* 411:    */       }
/* 412:452 */       int eventsPerVerb = eventCount / verbCount;
/* 413:453 */       Mark.say(new Object[] {"Expected events per verb", Integer.valueOf(eventsPerVerb) });
/* 414:454 */       for (String verb : sortedVerbs)
/* 415:    */       {
/* 416:455 */         int verbInstances = ((Integer)Annotation.verbs.get(verb)).intValue();
/* 417:456 */         if (verbInstances <= eventsPerVerb * underRepresented / 100) {
/* 418:457 */           this.rareVerbs.add(verb);
/* 419:459 */         } else if (verbInstances >= eventsPerVerb * overRepresented / 100) {
/* 420:460 */           this.commonVerbs.add(verb);
/* 421:    */         }
/* 422:    */       }
/* 423:463 */       for (String verb : sortedVerbs) {
/* 424:464 */         Mark.say(new Object[] {"   ", verb, Annotation.verbs.get(verb), Integer.valueOf(((Integer)Annotation.verbs.get(verb)).intValue() * 100 / eventsPerVerb) });
/* 425:    */       }
/* 426:467 */       Mark.say(new Object[] {"Rare verbs", this.rareVerbs, "all less than", Integer.valueOf(underRepresented), "% of expected" });
/* 427:    */       
/* 428:469 */       Mark.say(new Object[] {"Common verbs", this.commonVerbs, "all greater than", Integer.valueOf(overRepresented), "% of expected" });
/* 429:    */       
/* 430:471 */       Mark.say(new Object[] {"Original signal count:", Integer.valueOf(signals.size()) });
/* 431:472 */       signals = consolodateSignals(signals);
/* 432:473 */       Mark.say(new Object[] {"Consolodated signal count:", Integer.valueOf(signals.size()) });
/* 433:    */     }
/* 434:    */     else
/* 435:    */     {
/* 436:476 */       Mark.err(new Object[] {"No consolodated annotations for this section!!!!!!" });
/* 437:    */     }
/* 438:479 */     Collections.sort(signals, new SignalSorter());
/* 439:    */     
/* 440:481 */     Mark.say(new Object[] {"Sorted and signal count:", Integer.valueOf(signals.size()) });
/* 441:    */     
/* 442:483 */     signals = pruneRedundantStatements(signals);
/* 443:    */     
/* 444:485 */     Mark.say(new Object[] {"Pruned signal count:", Integer.valueOf(signals.size()) });
/* 445:    */     
/* 446:487 */     return signals;
/* 447:    */   }
/* 448:    */   
/* 449:    */   private int findNextSignalIndex(int frame, ArrayList<BetterSignal> signals)
/* 450:    */   {
/* 451:492 */     for (int i = 0; i < signals.size(); i++)
/* 452:    */     {
/* 453:493 */       BetterSignal signal = (BetterSignal)signals.get(i);
/* 454:494 */       int end = ((Integer)signal.get(2, Integer.class)).intValue();
/* 455:495 */       if (end > frame) {
/* 456:496 */         return i;
/* 457:    */       }
/* 458:    */     }
/* 459:499 */     return signals.size();
/* 460:    */   }
/* 461:    */   
/* 462:    */   private int seconds(int i)
/* 463:    */   {
/* 464:508 */     return i / framesPerSecond;
/* 465:    */   }
/* 466:    */   
/* 467:    */   private ArrayList<BetterSignal> pruneRedundantStatements(ArrayList<BetterSignal> signals)
/* 468:    */   {
/* 469:512 */     ArrayList<BetterSignal> result = new ArrayList();
/* 470:513 */     BetterSignal recent = null;
/* 471:514 */     for (BetterSignal signal : signals) {
/* 472:515 */       if (recent == null)
/* 473:    */       {
/* 474:516 */         recent = signal;
/* 475:517 */         result.add(signal);
/* 476:    */       }
/* 477:519 */       else if (!((String)signal.get(0, String.class)).equals(recent.get(0, String.class)))
/* 478:    */       {
/* 479:522 */         recent = signal;
/* 480:523 */         result.add(signal);
/* 481:    */       }
/* 482:    */     }
/* 483:526 */     return result;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public static String time(int t)
/* 487:    */   {
/* 488:530 */     int sec = t / framesPerSecond;
/* 489:531 */     int min = sec / 60;
/* 490:532 */     sec %= 60;
/* 491:533 */     return min + " minutes, " + sec + " seconds";
/* 492:    */   }
/* 493:    */   
/* 494:    */   public static String quickTime(int t)
/* 495:    */   {
/* 496:537 */     int sec = t / framesPerSecond;
/* 497:538 */     int min = sec / 60;
/* 498:539 */     sec %= 60;
/* 499:540 */     return " " + min + " min " + sec + " sec";
/* 500:    */   }
/* 501:    */   
/* 502:    */   public static String formatTime(int t)
/* 503:    */   {
/* 504:544 */     int sec = t / 1000;
/* 505:545 */     int min = sec / 60;
/* 506:546 */     sec %= 60;
/* 507:547 */     return " " + min + " min " + sec + " sec";
/* 508:    */   }
/* 509:    */   
/* 510:    */   private ArrayList<BetterSignal> consolodateSignals(ArrayList<BetterSignal> inputSignals)
/* 511:    */   {
/* 512:551 */     ArrayList<BetterSignal> consolodatedSignals = new ArrayList();
/* 513:552 */     for (BetterSignal signal : inputSignals) {
/* 514:554 */       if (consolodatedSignals.isEmpty())
/* 515:    */       {
/* 516:555 */         consolodatedSignals.add(signal);
/* 517:    */       }
/* 518:    */       else
/* 519:    */       {
/* 520:560 */         BetterSignal last = (BetterSignal)consolodatedSignals.get(consolodatedSignals.size() - 1);
/* 521:561 */         if (last.get(1, Integer.class) == signal.get(1, Integer.class)) {
/* 522:564 */           last.put(0, (String)last.get(0, String.class) + " " + (String)signal.get(0, String.class));
/* 523:    */         } else {
/* 524:569 */           consolodatedSignals.add(signal);
/* 525:    */         }
/* 526:    */       }
/* 527:    */     }
/* 528:572 */     for (BetterSignal signal : consolodatedSignals) {
/* 529:574 */       signal.add(Boolean.valueOf(false));
/* 530:    */     }
/* 531:576 */     return consolodatedSignals;
/* 532:    */   }
/* 533:    */   
/* 534:    */   public static void main(String[] args)
/* 535:    */     throws Exception
/* 536:    */   {
/* 537:588 */     JFrame f = new JFrame("Image reader test app");
/* 538:589 */     f.addWindowListener(new WindowAdapter()
/* 539:    */     {
/* 540:    */       public void windowClosing(WindowEvent e)
/* 541:    */       {
/* 542:591 */         System.exit(0);
/* 543:    */       }
/* 544:593 */     });
/* 545:594 */     MindsEyeMovieViewer mindsEyeMovieViewer = new MindsEyeMovieViewer();
/* 546:595 */     FXMediaPlayer mindsEyeMoviePlayer = new FXMediaPlayer();
/* 547:    */     
/* 548:597 */     f.getContentPane().add(mindsEyeMovieViewer);
/* 549:598 */     f.setBounds(0, 0, 500, 400);
/* 550:    */     
/* 551:600 */     f.setVisible(true);
/* 552:    */     
/* 553:602 */     Connections.wire(mindsEyeMoviePlayer, mindsEyeMovieViewer);
/* 554:    */     
/* 555:604 */     Co57Connector connector = new Co57Connector();
/* 556:    */     
/* 557:606 */     Set<String> moviesThatHaveAnnotations = connector.getMoviesHavingAnnotations();
/* 558:607 */     Mark.say(new Object[] {"There are", Integer.valueOf(moviesThatHaveAnnotations.size()), "with annotations" });
/* 559:608 */     String firstMovieTitle = null;
/* 560:609 */     for (String movieTitle : moviesThatHaveAnnotations) {
/* 561:611 */       if (firstMovieTitle == null)
/* 562:    */       {
/* 563:612 */         firstMovieTitle = movieTitle;
/* 564:613 */         Mark.say(new Object[] {movieTitle });
/* 565:    */       }
/* 566:    */     }
/* 567:619 */     Mark.say(new Object[] {"First movie", firstMovieTitle });
/* 568:    */     
/* 569:621 */     mindsEyeMoviePlayer.setMovieTitle(firstMovieTitle);
/* 570:    */   }
/* 571:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     obsolete.mediaFX.FXMediaPlayer
 * JD-Core Version:    0.7.0.1
 */