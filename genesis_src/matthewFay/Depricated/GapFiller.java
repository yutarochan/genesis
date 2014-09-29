/*   1:    */ package matthewFay.Depricated;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Relation;
/*   6:    */ import bridge.reps.entities.Sequence;
/*   7:    */ import connections.AbstractWiredBox;
/*   8:    */ import connections.Connections;
/*   9:    */ import connections.Ports;
/*  10:    */ import java.io.FileInputStream;
/*  11:    */ import java.io.FileOutputStream;
/*  12:    */ import java.io.ObjectInputStream;
/*  13:    */ import java.io.ObjectOutputStream;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import javax.swing.JRadioButton;
/*  16:    */ import matthewFay.Demo;
/*  17:    */ import matthewFay.StoryAlignment.RankedSequenceAlignmentSet;
/*  18:    */ import matthewFay.StoryAlignment.SequenceAlignment;
/*  19:    */ import matthewFay.Utilities.Pair;
/*  20:    */ import minilisp.LList;
/*  21:    */ import parameters.Radio;
/*  22:    */ import utils.Mark;
/*  23:    */ import utils.PairOfEntities;
/*  24:    */ 
/*  25:    */ @Deprecated
/*  26:    */ public class GapFiller
/*  27:    */   extends AbstractWiredBox
/*  28:    */ {
/*  29:    */   public static final String GAP_ALIGNMENTS_PORT = "gapAlignments";
/*  30: 36 */   private boolean verbose = false;
/*  31:    */   private ArrayList<Sequence> patternSet;
/*  32: 40 */   public static String FILL_GAP = "fillgap";
/*  33: 41 */   public static String ADD_PATTERN = "pattern";
/*  34: 42 */   public static String CLEAR_PATTERNS = "clear";
/*  35: 44 */   public static String SAVE_PATTERNS = "save";
/*  36: 45 */   public static String LOAD_PATTERNS = "load";
/*  37:    */   
/*  38:    */   public void savePatterns(Object o)
/*  39:    */   {
/*  40: 48 */     String fileName = "gapPatterns.dat";
/*  41: 49 */     if (((o instanceof String)) && 
/*  42: 50 */       (((String)o).length() > 2)) {
/*  43: 51 */       fileName = (String)o;
/*  44:    */     }
/*  45:    */     try
/*  46:    */     {
/*  47: 54 */       FileOutputStream fout = new FileOutputStream(fileName);
/*  48: 55 */       ObjectOutputStream oos = new ObjectOutputStream(fout);
/*  49: 56 */       oos.writeObject(this.patternSet);
/*  50: 57 */       oos.close();
/*  51:    */     }
/*  52:    */     catch (Exception e)
/*  53:    */     {
/*  54: 59 */       e.printStackTrace();
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void loadPatterns(Object o)
/*  59:    */   {
/*  60: 64 */     String fileName = "gapPatterns.dat";
/*  61: 65 */     if (((o instanceof String)) && 
/*  62: 66 */       (((String)o).length() > 2)) {
/*  63: 67 */       fileName = (String)o;
/*  64:    */     }
/*  65:    */     try
/*  66:    */     {
/*  67: 70 */       FileInputStream fin = new FileInputStream(fileName);
/*  68: 71 */       ObjectInputStream ois = new ObjectInputStream(fin);
/*  69: 72 */       Object patternSetObject = ois.readObject();
/*  70: 73 */       this.patternSet = ((ArrayList)patternSetObject);
/*  71: 74 */       ois.close();
/*  72:    */     }
/*  73:    */     catch (Exception e)
/*  74:    */     {
/*  75: 76 */       e.printStackTrace();
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   public GapFiller()
/*  80:    */   {
/*  81: 81 */     setName("GapFiller");
/*  82: 82 */     Connections.getPorts(this).addSignalProcessor("processSignal");
/*  83: 83 */     this.patternSet = new ArrayList();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void processSignal(Object o)
/*  87:    */   {
/*  88: 87 */     if (!Radio.alignmentButton.isSelected()) {
/*  89: 88 */       return;
/*  90:    */     }
/*  91: 89 */     Mark.say(new Object[] {Boolean.valueOf(this.verbose), "GapFiller recieved a signal!" });
/*  92:    */     
/*  93: 91 */     BetterSignal signal = BetterSignal.isSignal(o);
/*  94: 92 */     if (signal == null) {
/*  95: 93 */       return;
/*  96:    */     }
/*  97:    */     try
/*  98:    */     {
/*  99: 95 */       String command = (String)signal.get(0, String.class);
/* 100: 96 */       if (command == ADD_PATTERN)
/* 101:    */       {
/* 102: 98 */         Sequence pattern = (Sequence)signal.get(1, Sequence.class);
/* 103: 99 */         Mark.say(new Object[] {Boolean.valueOf(this.verbose), "Adding video pattern" });
/* 104:100 */         reportStory(pattern);
/* 105:101 */         addPattern(pattern);
/* 106:    */       }
/* 107:102 */       else if (command == FILL_GAP)
/* 108:    */       {
/* 109:103 */         Sequence gap = (Sequence)signal.get(1, Sequence.class);
/* 110:104 */         Mark.say(new Object[] {Boolean.valueOf(this.verbose), "Filling gap in video story" });
/* 111:105 */         reportStory(gap);
/* 112:106 */         fillGap(gap);
/* 113:    */       }
/* 114:107 */       else if (command == CLEAR_PATTERNS)
/* 115:    */       {
/* 116:108 */         Mark.say(new Object[] {Boolean.valueOf(this.verbose), "Got clear indication" });
/* 117:109 */         clearPatternBuffer();
/* 118:    */       }
/* 119:110 */       else if (command == SAVE_PATTERNS)
/* 120:    */       {
/* 121:111 */         if (signal.size() == 1) {
/* 122:112 */           savePatterns("");
/* 123:    */         } else {
/* 124:114 */           savePatterns(signal.get(1, String.class));
/* 125:    */         }
/* 126:    */       }
/* 127:115 */       else if (command == LOAD_PATTERNS)
/* 128:    */       {
/* 129:116 */         if (signal.size() == 1) {
/* 130:117 */           loadPatterns("");
/* 131:    */         } else {
/* 132:119 */           loadPatterns(signal.get(1, String.class));
/* 133:    */         }
/* 134:    */       }
/* 135:120 */       else if (command == "type")
/* 136:    */       {
/* 137:121 */         setAlignmentType((SequenceAligner.AlignmentType)signal.get(1, SequenceAligner.AlignmentType.class));
/* 138:    */       }
/* 139:    */       else
/* 140:    */       {
/* 141:123 */         Mark.say(new Object[] {"Unknown command received at GapFiller" });
/* 142:    */       }
/* 143:    */     }
/* 144:    */     catch (Exception e)
/* 145:    */     {
/* 146:127 */       Mark.say(new Object[] {"Invalid Signal Recieved at GapFiller" });
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   private void reportStory(Sequence gap)
/* 151:    */   {
/* 152:132 */     for (Entity t : gap.getElements()) {
/* 153:133 */       Mark.say(new Object[] {Boolean.valueOf(this.verbose), t.asString() });
/* 154:    */     }
/* 155:    */   }
/* 156:    */   
/* 157:137 */   SequenceAligner.AlignmentType alignmentType = SequenceAligner.AlignmentType.FASTER;
/* 158:    */   
/* 159:    */   public void setAlignmentType(SequenceAligner.AlignmentType alignmentType)
/* 160:    */   {
/* 161:139 */     this.alignmentType = alignmentType;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void addPattern(Object o)
/* 165:    */   {
/* 166:143 */     Mark.say(
/* 167:    */     
/* 168:    */ 
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:    */ 
/* 174:    */ 
/* 175:    */ 
/* 176:    */ 
/* 177:    */ 
/* 178:    */ 
/* 179:    */ 
/* 180:    */ 
/* 181:158 */       new Object[] { Boolean.valueOf(this.verbose), "GapFiller recieved an addPattern signal!" });
/* 182:144 */     if ((o instanceof Sequence))
/* 183:    */     {
/* 184:145 */       this.patternSet.add(SequenceSanitizer.sanitize((Sequence)o));
/* 185:146 */       return;
/* 186:    */     }
/* 187:148 */     BetterSignal signal = BetterSignal.isSignal(o);
/* 188:149 */     if (signal != null) {
/* 189:    */       try
/* 190:    */       {
/* 191:152 */         Sequence pattern = (Sequence)signal.get(0, Sequence.class);
/* 192:153 */         if (pattern != null) {
/* 193:154 */           this.patternSet.add(SequenceSanitizer.sanitize(pattern));
/* 194:    */         }
/* 195:    */       }
/* 196:    */       catch (Exception e)
/* 197:    */       {
/* 198:156 */         e.printStackTrace();
/* 199:    */       }
/* 200:    */     }
/* 201:    */   }
/* 202:    */   
/* 203:    */   private boolean gapCheck(Entity thing)
/* 204:    */   {
/* 205:161 */     if ((thing.getType().equals("appear")) && 
/* 206:162 */       (thing.functionP()) && 
/* 207:163 */       (thing.getSubject().isA("gap"))) {
/* 208:164 */       return true;
/* 209:    */     }
/* 210:166 */     return false;
/* 211:    */   }
/* 212:    */   
/* 213:169 */   public SequenceAlignment lastAlignment = null;
/* 214:170 */   public RankedSequenceAlignmentSet<Entity, Entity> lastAlignments = null;
/* 215:171 */   public ArrayList<Integer> gapsFilledAt = null;
/* 216:    */   
/* 217:    */   public Sequence fillGap(Object o)
/* 218:    */   {
/* 219:174 */     Mark.say(
/* 220:    */     
/* 221:    */ 
/* 222:    */ 
/* 223:    */ 
/* 224:    */ 
/* 225:    */ 
/* 226:    */ 
/* 227:    */ 
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:    */ 
/* 232:    */ 
/* 233:    */ 
/* 234:    */ 
/* 235:    */ 
/* 236:    */ 
/* 237:    */ 
/* 238:    */ 
/* 239:    */ 
/* 240:    */ 
/* 241:    */ 
/* 242:    */ 
/* 243:    */ 
/* 244:    */ 
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
/* 388:    */ 
/* 389:    */ 
/* 390:    */ 
/* 391:    */ 
/* 392:    */ 
/* 393:    */ 
/* 394:349 */       new Object[] { "GapFiller recieved a fillGap signal!" });Sequence gapSequence = null;
/* 395:176 */     if ((o instanceof Sequence)) {
/* 396:177 */       gapSequence = (Sequence)o;
/* 397:    */     }
/* 398:179 */     BetterSignal signal = BetterSignal.isSignal(o);
/* 399:180 */     if ((signal == null) && (gapSequence == null)) {
/* 400:181 */       return null;
/* 401:    */     }
/* 402:    */     try
/* 403:    */     {
/* 404:184 */       this.gapsFilledAt = new ArrayList();
/* 405:185 */       Sequence finishedSequence = new Sequence();
/* 406:186 */       if (gapSequence == null) {
/* 407:187 */         gapSequence = (Sequence)signal.get(0, Sequence.class);
/* 408:    */       }
/* 409:190 */       if (gapSequence == null)
/* 410:    */       {
/* 411:191 */         Mark.say(new Object[] {"No sequence recieved" });
/* 412:192 */         return null;
/* 413:    */       }
/* 414:195 */       if (this.patternSet.isEmpty())
/* 415:    */       {
/* 416:196 */         Mark.say(new Object[] {"No Pattern to fill gap with" });
/* 417:197 */         return null;
/* 418:    */       }
/* 419:200 */       gapSequence = SequenceSanitizer.sanitize(gapSequence);
/* 420:201 */       Mark.say(new Object[] {"Gap Sequence: ", gapSequence.asString() });
/* 421:    */       
/* 422:203 */       Mark.say(new Object[] {"Stripped Gap Sequence: ", gapSequence.asString() });
/* 423:    */       
/* 424:205 */       SequenceAligner aligner = new SequenceAligner();
/* 425:    */       
/* 426:    */ 
/* 427:208 */       Sequence partialGapSequence = new Sequence();
/* 428:    */       
/* 429:    */ 
/* 430:211 */       int gapLocation = -1;
/* 431:212 */       int partialGapLocation = -1;
/* 432:213 */       boolean gapFound = false;
/* 433:214 */       int gapIterator = 0;
/* 434:215 */       boolean firstGap = true;
/* 435:216 */       while (gapIterator < gapSequence.getNumberOfChildren())
/* 436:    */       {
/* 437:218 */         if (partialGapSequence.getNumberOfChildren() > 0)
/* 438:    */         {
/* 439:220 */           Sequence temp = new Sequence();
/* 440:221 */           int i = partialGapLocation + 1;
/* 441:222 */           while (i < partialGapSequence.getNumberOfChildren())
/* 442:    */           {
/* 443:223 */             temp.addElement(partialGapSequence.getElement(i));
/* 444:224 */             i++;
/* 445:    */           }
/* 446:226 */           partialGapSequence = temp;
/* 447:227 */           gapFound = false;
/* 448:228 */           gapLocation = -1;
/* 449:229 */           firstGap = false;
/* 450:    */         }
/* 451:232 */         while (gapIterator < gapSequence.getNumberOfChildren())
/* 452:    */         {
/* 453:233 */           Entity t = gapSequence.getElement(gapIterator);
/* 454:234 */           if (gapCheck(t))
/* 455:    */           {
/* 456:235 */             if (gapFound) {
/* 457:    */               break;
/* 458:    */             }
/* 459:236 */             gapLocation = gapIterator;
/* 460:237 */             partialGapLocation = partialGapSequence.getNumberOfChildren();
/* 461:238 */             gapFound = true;
/* 462:    */           }
/* 463:245 */           partialGapSequence.addElement(t);
/* 464:246 */           gapIterator++;
/* 465:    */         }
/* 466:260 */         RankedSequenceAlignmentSet<Entity, Entity> alignments = aligner.findBestAlignments(this.patternSet, partialGapSequence, this.alignmentType);
/* 467:    */         
/* 468:262 */         SequenceAligner.outputAlignment(alignments);
/* 469:263 */         Connections.getPorts(this).transmit("gapAlignments", new BetterSignal(new Object[] { alignments }));
/* 470:    */         
/* 471:265 */         SequenceAlignment bestAlignment = (SequenceAlignment)alignments.get(0);
/* 472:266 */         LList<PairOfEntities> bindings = bestAlignment.bindings;
/* 473:    */         
/* 474:268 */         int endOfPastPrediction = 0;
/* 475:269 */         int startOfPrediction = 0;
/* 476:270 */         for (int i = 0; i < bestAlignment.size(); i++) {
/* 477:271 */           if (((Pair)bestAlignment.get(i)).b != null)
/* 478:    */           {
/* 479:272 */             endOfPastPrediction = i;
/* 480:273 */             break;
/* 481:    */           }
/* 482:    */         }
/* 483:276 */         for (int i = bestAlignment.size() - 1; i >= 0; i--) {
/* 484:277 */           if (((Pair)bestAlignment.get(i)).b != null)
/* 485:    */           {
/* 486:278 */             startOfPrediction = i;
/* 487:279 */             break;
/* 488:    */           }
/* 489:    */         }
/* 490:282 */         boolean gapPassed = false;
/* 491:283 */         for (int i = endOfPastPrediction; i <= startOfPrediction; i++)
/* 492:    */         {
/* 493:284 */           Pair<Entity, Entity> pair = (Pair)bestAlignment.get(i);
/* 494:286 */           if (pair.a != null)
/* 495:    */           {
/* 496:287 */             if (pair.b == null)
/* 497:    */             {
/* 498:293 */               this.gapsFilledAt.add(Integer.valueOf(finishedSequence.getNumberOfChildren()));
/* 499:294 */               Relation eltToAdd = (Relation)((Entity)pair.a).deepClone();
/* 500:    */               
/* 501:296 */               eltToAdd = (Relation)findAndReplace(eltToAdd, bindings);
/* 502:298 */               if ((firstGap) || (gapPassed)) {
/* 503:299 */                 finishedSequence.addElement(eltToAdd);
/* 504:    */               }
/* 505:300 */               pair.b = eltToAdd;
/* 506:    */             }
/* 507:303 */             else if (gapCheck((Entity)pair.b))
/* 508:    */             {
/* 509:304 */               this.gapsFilledAt.add(Integer.valueOf(finishedSequence.getNumberOfChildren()));
/* 510:    */               
/* 511:306 */               Entity eltToAdd = ((Entity)pair.a).deepClone();
/* 512:    */               
/* 513:    */ 
/* 514:309 */               eltToAdd = findAndReplace(eltToAdd, bindings);
/* 515:    */               
/* 516:    */ 
/* 517:312 */               finishedSequence.addElement(eltToAdd);
/* 518:313 */               pair.b = eltToAdd;
/* 519:314 */               gapPassed = true;
/* 520:    */             }
/* 521:317 */             else if ((firstGap) || (gapPassed))
/* 522:    */             {
/* 523:318 */               finishedSequence.addElement(((Entity)pair.b).deepClone());
/* 524:    */             }
/* 525:    */           }
/* 526:322 */           else if (pair.b != null) {
/* 527:326 */             if (!gapCheck((Entity)pair.b)) {
/* 528:329 */               if ((firstGap) || (gapPassed)) {
/* 529:330 */                 finishedSequence.addElement(((Entity)pair.b).deepClone());
/* 530:    */               }
/* 531:    */             }
/* 532:    */           }
/* 533:    */         }
/* 534:336 */         this.lastAlignment = bestAlignment;
/* 535:337 */         this.lastAlignments = alignments;
/* 536:    */       }
/* 537:340 */       Mark.say(new Object[] {"Gap Filled, Transmitting: ", finishedSequence.asString() });
/* 538:341 */       Connections.getPorts(this).transmit(new BetterSignal(new Object[] { finishedSequence, this.gapsFilledAt }));
/* 539:342 */       return finishedSequence;
/* 540:    */     }
/* 541:    */     catch (Exception e)
/* 542:    */     {
/* 543:345 */       e.printStackTrace();
/* 544:    */     }
/* 545:348 */     return null;
/* 546:    */   }
/* 547:    */   
/* 548:    */   public void clearPatternBuffer()
/* 549:    */   {
/* 550:352 */     this.patternSet.clear();
/* 551:    */   }
/* 552:    */   
/* 553:    */   private Entity findAndReplace(Entity element, LList<PairOfEntities> bindings)
/* 554:    */   {
/* 555:357 */     if (element.entityP())
/* 556:    */     {
/* 557:359 */       for (PairOfEntities pair : bindings) {
/* 558:360 */         if (pair.getPattern().isDeepEqual(element)) {
/* 559:361 */           return pair.getDatum();
/* 560:    */         }
/* 561:    */       }
/* 562:364 */       return new Entity();
/* 563:    */     }
/* 564:366 */     if (element.relationP())
/* 565:    */     {
/* 566:367 */       element.setSubject(findAndReplace(element.getSubject(), bindings));
/* 567:368 */       element.setObject(findAndReplace(element.getObject(), bindings));
/* 568:369 */       return element;
/* 569:    */     }
/* 570:371 */     if (element.functionP())
/* 571:    */     {
/* 572:372 */       element.setSubject(findAndReplace(element.getSubject(), bindings));
/* 573:373 */       return element;
/* 574:    */     }
/* 575:375 */     if (element.sequenceP())
/* 576:    */     {
/* 577:376 */       int i = 0;
/* 578:377 */       Sequence s = (Sequence)element;
/* 579:378 */       while (i < element.getNumberOfChildren())
/* 580:    */       {
/* 581:379 */         Entity child = element.getElement(i);
/* 582:380 */         child = findAndReplace(child, bindings);
/* 583:381 */         s.setElementAt(child, i);
/* 584:382 */         i++;
/* 585:    */       }
/* 586:384 */       return element;
/* 587:    */     }
/* 588:387 */     return element;
/* 589:    */   }
/* 590:    */   
/* 591:    */   public static void main(String[] args)
/* 592:    */   {
/* 593:393 */     Sequence GapStory = Demo.ComplexGapStory();
/* 594:394 */     Sequence GiveStory = Demo.GiveStory();
/* 595:395 */     Sequence ComplexTakeStory = Demo.ComplexTakeStory();
/* 596:    */     
/* 597:397 */     GapFiller gf = new GapFiller();
/* 598:    */     
/* 599:399 */     gf.addPattern(GiveStory);
/* 600:400 */     gf.addPattern(ComplexTakeStory);
/* 601:401 */     Mark.say(new Object[] {GapStory.asString() });
/* 602:402 */     Mark.say(new Object[] {gf.fillGap(GapStory).asString() });
/* 603:    */   }
/* 604:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.Depricated.GapFiller
 * JD-Core Version:    0.7.0.1
 */