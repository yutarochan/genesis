/*   1:    */ package disambiguator;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Function;
/*   6:    */ import bridge.reps.entities.Relation;
/*   7:    */ import bridge.reps.entities.Sequence;
/*   8:    */ import bridge.reps.entities.Thread;
/*   9:    */ import connections.Connections;
/*  10:    */ import connections.Ports;
/*  11:    */ import connections.WiredBox;
/*  12:    */ import java.util.Vector;
/*  13:    */ import javax.swing.JPanel;
/*  14:    */ import minilisp.LList;
/*  15:    */ import translator.Translator;
/*  16:    */ import utils.Mark;
/*  17:    */ import utils.PairOfEntities;
/*  18:    */ 
/*  19:    */ public class Disambiguator
/*  20:    */   extends JPanel
/*  21:    */   implements WiredBox
/*  22:    */ {
/*  23: 27 */   boolean autoAddToLibrary = true;
/*  24: 29 */   public static String ADD = "add to library";
/*  25: 31 */   public static String CLEAR = "flush library";
/*  26: 33 */   public static String RESOLVE = "resolve classifications";
/*  27: 35 */   private int maxUnion = 0;
/*  28: 35 */   private int minExampleBranch = 1;
/*  29: 35 */   private int minHistoryBranch = 2;
/*  30: 37 */   private int mode = this.minHistoryBranch;
/*  31:    */   private Sequence library;
/*  32: 41 */   private boolean debug = false;
/*  33: 43 */   private boolean debugGeneralizing = true;
/*  34: 45 */   private boolean showBestMatches = true;
/*  35:    */   
/*  36:    */   public Disambiguator()
/*  37:    */   {
/*  38: 48 */     Connections.getPorts(this).addSignalProcessor("process");
/*  39: 49 */     Connections.getPorts(this).addSignalProcessor(ADD, "addToLibrary");
/*  40: 50 */     Connections.getPorts(this).addSignalProcessor(CLEAR, "flushLibrary");
/*  41: 51 */     Connections.getPorts(this).addSignalProcessor(RESOLVE, "resolve");
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void process(Object o) {}
/*  45:    */   
/*  46:    */   private boolean ambiguous(Entity t)
/*  47:    */   {
/*  48: 73 */     Bundle b = t.getBundle();
/*  49: 74 */     int count = 0;
/*  50: 75 */     for (Thread x : b)
/*  51:    */     {
/*  52: 76 */       if ((((String)x.firstElement()).equals("action")) || (((String)x.firstElement()).equals("thing"))) {
/*  53: 77 */         count++;
/*  54:    */       }
/*  55: 79 */       if (count > 1)
/*  56:    */       {
/*  57: 80 */         Mark.say(new Object[] {"Too many threads for", extractType(x) });
/*  58: 81 */         return true;
/*  59:    */       }
/*  60:    */     }
/*  61: 84 */     if (t.functionP()) {
/*  62: 85 */       return ambiguous(t.getSubject());
/*  63:    */     }
/*  64: 87 */     if (t.relationP()) {
/*  65: 88 */       return (ambiguous(t.getSubject())) || (ambiguous(t.getObject()));
/*  66:    */     }
/*  67: 90 */     if (t.sequenceP()) {
/*  68: 91 */       for (Entity x : t.getElements()) {
/*  69: 92 */         if (ambiguous(x)) {
/*  70: 93 */           return true;
/*  71:    */         }
/*  72:    */       }
/*  73:    */     }
/*  74: 97 */     return false;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public Sequence getLibrary()
/*  78:    */   {
/*  79:101 */     if (this.library == null) {
/*  80:102 */       this.library = new Sequence();
/*  81:    */     }
/*  82:104 */     return this.library;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void addToLibrary(Object t)
/*  86:    */   {
/*  87:108 */     if ((t instanceof Entity)) {
/*  88:109 */       getLibrary().addElement((Entity)t);
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void flushLibrary(Object t)
/*  93:    */   {
/*  94:115 */     getLibrary().getElements().clear();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void resolve(Object o)
/*  98:    */   {
/*  99:119 */     if (!(o instanceof Entity)) {
/* 100:120 */       return;
/* 101:    */     }
/* 102:122 */     if (this.mode != this.minHistoryBranch) {
/* 103:123 */       Mark.err(new Object[] {"No longer support selected mode" });
/* 104:    */     }
/* 105:125 */     Entity t = (Entity)o;
/* 106:126 */     int bestScore = 0;
/* 107:127 */     Entity bestReference = null;
/* 108:128 */     LList<PairOfEntities> bestBindingList = null;
/* 109:129 */     if ((this.mode == this.minExampleBranch) || (this.mode == this.minHistoryBranch)) {
/* 110:130 */       bestScore = 1000;
/* 111:    */     }
/* 112:132 */     for (Entity memory : getLibrary().getElements())
/* 113:    */     {
/* 114:133 */       LList<PairOfEntities> match = match(t, memory);
/* 115:134 */       if (match != null)
/* 116:    */       {
/* 117:137 */         int thisScore = 0;
/* 118:138 */         for (PairOfEntities p : match)
/* 119:    */         {
/* 120:139 */           int score = getBestScore(p);
/* 121:141 */           if (score >= 0)
/* 122:    */           {
/* 123:144 */             thisScore += score;
/* 124:145 */             if (thisScore > bestScore)
/* 125:    */             {
/* 126:146 */               Mark.say(new Object[] {Boolean.valueOf(this.debug), "No need to continue,", Integer.valueOf(thisScore), ">", Integer.valueOf(bestScore) });
/* 127:147 */               Mark.say(new Object[] {Boolean.valueOf(this.debug), "Loser", Integer.valueOf(thisScore), memory.asString() });
/* 128:    */             }
/* 129:    */           }
/* 130:    */         }
/* 131:152 */         if (thisScore < bestScore)
/* 132:    */         {
/* 133:153 */           bestScore = thisScore;
/* 134:154 */           bestReference = memory;
/* 135:155 */           bestBindingList = match;
/* 136:156 */           Mark.say(new Object[] {Boolean.valueOf(this.debug), "Improvement", Integer.valueOf(thisScore), memory.asString() });
/* 137:    */         }
/* 138:    */       }
/* 139:    */     }
/* 140:160 */     if (bestReference != null)
/* 141:    */     {
/* 142:161 */       Mark.say(new Object[] {Boolean.valueOf(this.debug), "Best reference at", Integer.valueOf(bestScore), "\n" + t.asString(), "is\n" + bestReference.asString(), "\n" + bestBindingList });
/* 143:162 */       disambiguate(bestBindingList);
/* 144:    */       
/* 145:    */ 
/* 146:    */ 
/* 147:166 */       doGeneralize(t, bestReference, bestBindingList);
/* 148:    */     }
/* 149:    */     else
/* 150:    */     {
/* 151:170 */       Mark.err(new Object[] {"Could not find reference for", t.asString() });
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   private Entity extractObject(Entity t)
/* 156:    */   {
/* 157:175 */     if ((t.relationP()) && (t.getObject().sequenceP())) {
/* 158:176 */       for (Entity e : t.getObject().getElements()) {
/* 159:177 */         if (e.functionP("object")) {
/* 160:178 */           return e.getSubject();
/* 161:    */         }
/* 162:    */       }
/* 163:    */     }
/* 164:182 */     return null;
/* 165:    */   }
/* 166:    */   
/* 167:    */   private void doGeneralize(Entity t, Entity bestReference, LList<PairOfEntities> l)
/* 168:    */   {
/* 169:186 */     Entity result = generalize(t, l);
/* 170:188 */     if ((result != null) && (result.relationP()))
/* 171:    */     {
/* 172:189 */       Thread thread = t.getSubject().getPrimedThread();
/* 173:190 */       Mark.say(new Object[] {Boolean.valueOf(this.debug), "The thread is", thread });
/* 174:    */       
/* 175:192 */       String remark = "The " + extractType(thread) + " is a " + extractParent(thread);
/* 176:193 */       Entity o = extractObject(t);
/* 177:194 */       if (o != null)
/* 178:    */       {
/* 179:195 */         thread = o.getPrimedThread();
/* 180:196 */         remark = remark + "; the " + extractType(thread) + " is a " + extractParent(thread);
/* 181:    */       }
/* 182:198 */       Connections.getPorts(this).transmit(remark);
/* 183:    */     }
/* 184:200 */     if (result != null) {
/* 185:201 */       Mark.say(new Object[] {Boolean.valueOf(this.debugGeneralizing), "Generalized", "\n", t.asString(), "\n", bestReference.asString(), "\nto\n", result.asString() });
/* 186:    */     }
/* 187:    */   }
/* 188:    */   
/* 189:    */   private Entity generalize(Entity t, LList<PairOfEntities> l)
/* 190:    */   {
/* 191:212 */     Bundle b = climbTree(t, l);
/* 192:213 */     if (t.entityP()) {
/* 193:214 */       return new Entity(b);
/* 194:    */     }
/* 195:216 */     if (t.functionP()) {
/* 196:217 */       return new Function(b, generalize(t.getSubject(), l));
/* 197:    */     }
/* 198:219 */     if (t.relationP()) {
/* 199:220 */       return new Relation(b, generalize(t.getSubject(), l), generalize(t.getObject(), l));
/* 200:    */     }
/* 201:222 */     if (t.sequenceP())
/* 202:    */     {
/* 203:223 */       Sequence s = new Sequence(b);
/* 204:224 */       for (int i = 0; i < t.getElements().size(); i++) {
/* 205:225 */         s.addElement(generalize((Entity)t.getElements().get(i), l));
/* 206:    */       }
/* 207:227 */       return s;
/* 208:    */     }
/* 209:229 */     Mark.err(new Object[] {"Never should get here in Disambiguator.generalize" });
/* 210:230 */     return null;
/* 211:    */   }
/* 212:    */   
/* 213:    */   private Bundle climbTree(Entity t, LList<PairOfEntities> l)
/* 214:    */   {
/* 215:234 */     Entity match = findMatch(t, l);
/* 216:235 */     if (match == null) {
/* 217:236 */       return (Bundle)t.getBundle().clone();
/* 218:    */     }
/* 219:238 */     Thread thread = new Thread();
/* 220:239 */     Bundle result = new Bundle(thread);
/* 221:240 */     for (int i = 0; i < Math.min(t.getPrimedThread().size(), match.getPrimedThread().size()); i++)
/* 222:    */     {
/* 223:241 */       String element = (String)t.getPrimedThread().get(i);
/* 224:242 */       if (!element.equals(match.getPrimedThread().get(i))) {
/* 225:    */         break;
/* 226:    */       }
/* 227:243 */       thread.add(element);
/* 228:    */     }
/* 229:249 */     return result;
/* 230:    */   }
/* 231:    */   
/* 232:    */   private Entity findMatch(Entity t, LList<PairOfEntities> l)
/* 233:    */   {
/* 234:253 */     for (PairOfEntities p : l) {
/* 235:254 */       if (p.getPattern() == t) {
/* 236:255 */         return p.getDatum();
/* 237:    */       }
/* 238:    */     }
/* 239:258 */     return null;
/* 240:    */   }
/* 241:    */   
/* 242:    */   private void disambiguate(LList<PairOfEntities> bestBindingList)
/* 243:    */   {
/* 244:262 */     for (PairOfEntities pair : bestBindingList)
/* 245:    */     {
/* 246:263 */       Thread bestThread = getBestMatch(pair);
/* 247:264 */       int bestScore = getBestScore(pair);
/* 248:265 */       Mark.say(new Object[] {Boolean.valueOf(this.showBestMatches), "Best match", Integer.valueOf(bestScore), pair.getDatum().getPrimedThread(), bestThread });
/* 249:266 */       if (bestThread.size() > 3) {
/* 250:267 */         reviseBundle(pair.getPattern(), pair.getDatum(), bestThread);
/* 251:    */       }
/* 252:    */     }
/* 253:    */   }
/* 254:    */   
/* 255:    */   private void reviseBundle(Entity current, Entity reference, Thread thread)
/* 256:    */   {
/* 257:273 */     current.getBundle().clear();
/* 258:274 */     current.getBundle().add(thread);
/* 259:    */   }
/* 260:    */   
/* 261:    */   private Object extractType(Thread best)
/* 262:    */   {
/* 263:280 */     int index = best.indexOf("name");
/* 264:281 */     if (index > 0)
/* 265:    */     {
/* 266:282 */       if (index - 1 >= 0) {
/* 267:283 */         return best.get(index - 1);
/* 268:    */       }
/* 269:    */     }
/* 270:286 */     else if (best.size() > 0) {
/* 271:287 */       return best.get(best.size() - 1);
/* 272:    */     }
/* 273:289 */     return null;
/* 274:    */   }
/* 275:    */   
/* 276:    */   private String extractParent(Thread best)
/* 277:    */   {
/* 278:293 */     int index = best.indexOf("name");
/* 279:294 */     if (index > 0)
/* 280:    */     {
/* 281:295 */       if (index - 2 >= 0) {
/* 282:296 */         return (String)best.get(index - 2);
/* 283:    */       }
/* 284:    */     }
/* 285:299 */     else if (best.size() > 1) {
/* 286:300 */       return (String)best.get(best.size() - 2);
/* 287:    */     }
/* 288:302 */     return null;
/* 289:    */   }
/* 290:    */   
/* 291:    */   private int getBestScore(PairOfEntities p)
/* 292:    */   {
/* 293:309 */     Thread referenceThread = p.getDatum().getPrimedThread();
/* 294:310 */     Bundle candidateBundle = p.getPattern().getBundle();
/* 295:    */     
/* 296:    */ 
/* 297:    */ 
/* 298:    */ 
/* 299:    */ 
/* 300:    */ 
/* 301:    */ 
/* 302:    */ 
/* 303:319 */     Thread bestCandidateThread = null;
/* 304:320 */     int bestScore = -1;
/* 305:321 */     if (this.mode != this.minHistoryBranch) {
/* 306:322 */       Mark.err(new Object[] {"No longer support selected mode" });
/* 307:    */     }
/* 308:324 */     int referenceSize = referenceThread.size();
/* 309:325 */     if (referenceSize == 0) {
/* 310:326 */       Mark.say(new Object[] {"Reference size in Disambiguator.calculateScore mysteriously 0" });
/* 311:    */     }
/* 312:328 */     for (Thread candidateThread : candidateBundle)
/* 313:    */     {
/* 314:329 */       int candidateSize = candidateThread.size();
/* 315:330 */       if (candidateSize != 0) {
/* 316:333 */         if ((((String)candidateThread.get(0)).equals("action")) || (((String)candidateThread.get(0)).equals("thing")))
/* 317:    */         {
/* 318:336 */           int thisScore = calculateThreadScore(referenceThread, candidateThread, referenceSize, candidateSize);
/* 319:337 */           if (thisScore >= 0) {
/* 320:340 */             if ((bestScore < 0) || (thisScore < bestScore))
/* 321:    */             {
/* 322:341 */               bestScore = thisScore;
/* 323:342 */               bestCandidateThread = candidateThread;
/* 324:    */               
/* 325:344 */               Mark.say(new Object[] {Boolean.valueOf(this.debug), "Improving", Integer.valueOf(bestScore), referenceThread, candidateThread });
/* 326:    */             }
/* 327:    */           }
/* 328:    */         }
/* 329:    */       }
/* 330:    */     }
/* 331:347 */     return bestScore;
/* 332:    */   }
/* 333:    */   
/* 334:    */   private Thread getBestMatch(PairOfEntities p)
/* 335:    */   {
/* 336:354 */     Thread referenceThread = p.getDatum().getPrimedThread();
/* 337:355 */     Bundle candidateBundle = p.getPattern().getBundle();
/* 338:356 */     Thread bestCandidateThread = null;
/* 339:357 */     int bestScore = -1;
/* 340:358 */     if (this.mode != this.minHistoryBranch) {
/* 341:359 */       Mark.err(new Object[] {"No longer support selected mode" });
/* 342:    */     }
/* 343:361 */     int referenceSize = referenceThread.size();
/* 344:362 */     if (referenceSize == 0) {
/* 345:363 */       Mark.say(new Object[] {"Reference size in Disambiguator.calculateScore mysteriously 0" });
/* 346:    */     }
/* 347:365 */     for (Thread candidateThread : candidateBundle)
/* 348:    */     {
/* 349:366 */       int candidateSize = candidateThread.size();
/* 350:367 */       if (candidateSize != 0) {
/* 351:370 */         if ((((String)candidateThread.get(0)).equals("action")) || (((String)candidateThread.get(0)).equals("thing")))
/* 352:    */         {
/* 353:373 */           int thisScore = calculateThreadScore(referenceThread, candidateThread, referenceSize, candidateSize);
/* 354:374 */           if (thisScore >= 0) {
/* 355:377 */             if ((bestScore < 0) || (thisScore < bestScore))
/* 356:    */             {
/* 357:379 */               bestScore = thisScore;
/* 358:380 */               bestCandidateThread = candidateThread;
/* 359:    */             }
/* 360:    */           }
/* 361:    */         }
/* 362:    */       }
/* 363:    */     }
/* 364:383 */     return bestCandidateThread;
/* 365:    */   }
/* 366:    */   
/* 367:    */   private int calculateThreadScore(Thread referenceThread, Thread candidateThread, int referenceSize, int candidateSize)
/* 368:    */   {
/* 369:388 */     int union = 0;
/* 370:389 */     for (int i = 0; i < Math.min(referenceSize, candidateSize); i++)
/* 371:    */     {
/* 372:390 */       if (!((String)referenceThread.get(i)).equals(candidateThread.get(i))) {
/* 373:    */         break;
/* 374:    */       }
/* 375:392 */       union++;
/* 376:    */     }
/* 377:399 */     int diff = 0;
/* 378:400 */     for (int i = union; i < referenceSize; i++)
/* 379:    */     {
/* 380:401 */       if (((String)referenceThread.get(i)).equals("name")) {
/* 381:    */         break;
/* 382:    */       }
/* 383:405 */       diff++;
/* 384:    */     }
/* 385:408 */     if (union == 0) {
/* 386:409 */       return -1;
/* 387:    */     }
/* 388:411 */     return diff;
/* 389:    */   }
/* 390:    */   
/* 391:    */   private LList<PairOfEntities> match(Entity t, Entity m)
/* 392:    */   {
/* 393:415 */     return match(t, m, new LList());
/* 394:    */   }
/* 395:    */   
/* 396:    */   private LList<PairOfEntities> match(Entity t, Entity m, LList<PairOfEntities> lList)
/* 397:    */   {
/* 398:419 */     if (lList == null) {
/* 399:420 */       return null;
/* 400:    */     }
/* 401:422 */     if ((t.entityP()) && (m.entityP())) {
/* 402:423 */       return lList.cons(new PairOfEntities(t, m));
/* 403:    */     }
/* 404:425 */     if ((t.functionP()) && (m.functionP())) {
/* 405:426 */       return match(t.getSubject(), m.getSubject(), lList.cons(new PairOfEntities(t, m)));
/* 406:    */     }
/* 407:428 */     if ((t.relationP()) && (m.relationP()))
/* 408:    */     {
/* 409:429 */       lList.cons(new PairOfEntities(t, m));
/* 410:430 */       return match(t.getSubject(), m.getSubject(), match(t.getObject(), m.getObject(), lList.cons(new PairOfEntities(t, m))));
/* 411:    */     }
/* 412:432 */     if ((t.sequenceP()) && (m.sequenceP()))
/* 413:    */     {
/* 414:433 */       if (t.getElements().size() != m.getElements().size()) {
/* 415:434 */         return null;
/* 416:    */       }
/* 417:436 */       for (int i = 0; i < t.getElements().size(); i++)
/* 418:    */       {
/* 419:437 */         LList<PairOfEntities> augmentedList = match((Entity)t.getElements().get(i), (Entity)m.getElements().get(i), lList);
/* 420:438 */         if (augmentedList == null) {
/* 421:442 */           return null;
/* 422:    */         }
/* 423:445 */         lList = augmentedList;
/* 424:    */       }
/* 425:448 */       return lList;
/* 426:    */     }
/* 427:451 */     return null;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public static void main(String[] ignore)
/* 431:    */     throws Exception
/* 432:    */   {
/* 433:455 */     Mark.say(
/* 434:    */     
/* 435:    */ 
/* 436:    */ 
/* 437:    */ 
/* 438:    */ 
/* 439:    */ 
/* 440:    */ 
/* 441:    */ 
/* 442:    */ 
/* 443:    */ 
/* 444:    */ 
/* 445:    */ 
/* 446:    */ 
/* 447:    */ 
/* 448:    */ 
/* 449:    */ 
/* 450:    */ 
/* 451:    */ 
/* 452:    */ 
/* 453:    */ 
/* 454:    */ 
/* 455:    */ 
/* 456:    */ 
/* 457:    */ 
/* 458:    */ 
/* 459:    */ 
/* 460:    */ 
/* 461:    */ 
/* 462:    */ 
/* 463:    */ 
/* 464:    */ 
/* 465:    */ 
/* 466:    */ 
/* 467:    */ 
/* 468:    */ 
/* 469:    */ 
/* 470:    */ 
/* 471:    */ 
/* 472:    */ 
/* 473:    */ 
/* 474:    */ 
/* 475:    */ 
/* 476:    */ 
/* 477:    */ 
/* 478:    */ 
/* 479:    */ 
/* 480:    */ 
/* 481:    */ 
/* 482:    */ 
/* 483:    */ 
/* 484:    */ 
/* 485:    */ 
/* 486:    */ 
/* 487:    */ 
/* 488:    */ 
/* 489:    */ 
/* 490:    */ 
/* 491:    */ 
/* 492:    */ 
/* 493:    */ 
/* 494:    */ 
/* 495:    */ 
/* 496:    */ 
/* 497:    */ 
/* 498:    */ 
/* 499:    */ 
/* 500:    */ 
/* 501:    */ 
/* 502:    */ 
/* 503:    */ 
/* 504:    */ 
/* 505:    */ 
/* 506:    */ 
/* 507:    */ 
/* 508:    */ 
/* 509:    */ 
/* 510:    */ 
/* 511:    */ 
/* 512:    */ 
/* 513:    */ 
/* 514:    */ 
/* 515:    */ 
/* 516:    */ 
/* 517:    */ 
/* 518:    */ 
/* 519:    */ 
/* 520:    */ 
/* 521:    */ 
/* 522:    */ 
/* 523:    */ 
/* 524:    */ 
/* 525:    */ 
/* 526:    */ 
/* 527:    */ 
/* 528:    */ 
/* 529:    */ 
/* 530:    */ 
/* 531:    */ 
/* 532:    */ 
/* 533:    */ 
/* 534:    */ 
/* 535:    */ 
/* 536:    */ 
/* 537:    */ 
/* 538:    */ 
/* 539:    */ 
/* 540:    */ 
/* 541:    */ 
/* 542:    */ 
/* 543:565 */       new Object[] { "Starting" });long translationTime = System.currentTimeMillis();Disambiguator disambiguator = new Disambiguator();String sentence1 = "The robin (flew fly travel) to a (tree organism)";String sentence2A = "The senator (flew fly travel) to a (city municipality)";String sentence2B = "The senator (flew fly travel) to a (conference meeting)";String sentence3A = "The hawk flew to the meeting";String sentence3B = "The hawk flew to the City";String sentence3C = "The hawk flew to the tree";String sentence4 = "The sparrow flew to the bush";String sentence5 = "The sparrow flew to the lake";String sentence6 = "The sparrow flew to the house";String sentence7 = "The cook has a (knife tool)";String sentence8 = "The soldier has a (knife weapon)";String sentence9 = "The cook has a knife";String sentence10 = "The general has a (knife weapon)";String sentence11 = "The general has a weapon";String sentence12 = "The (bus conveyance) (upset disturb) the (woman female).";String sentence13 = "The (woman female) (upset move) the bookcase.";String sentence14 = "The jerk upset the applecart.";String sentence15 = "The applecart upset the jerk.";disambiguator.addToLibrary(Translator.getTranslator().translate(sentence1).getElements().get(0));disambiguator.addToLibrary(Translator.getTranslator().translate(sentence2A).getElements().get(0));disambiguator.addToLibrary(Translator.getTranslator().translate(sentence7).getElements().get(0));disambiguator.addToLibrary(Translator.getTranslator().translate(sentence8).getElements().get(0));disambiguator.addToLibrary(Translator.getTranslator().translate(sentence12).getElements().get(0));disambiguator.addToLibrary(Translator.getTranslator().translate(sentence13).getElements().get(0));
/* 544:513 */     for (Entity t : disambiguator.getLibrary().getElements()) {
/* 545:514 */       Mark.say(new Object[] {t.asString() });
/* 546:    */     }
/* 547:517 */     disambiguator.resolve(Translator.getTranslator().translate(sentence3A).getElements().get(0));
/* 548:    */     
/* 549:519 */     Mark.say(new Object[] {"------" });
/* 550:    */     
/* 551:521 */     disambiguator.resolve(Translator.getTranslator().translate(sentence3B).getElements().get(0));
/* 552:    */     
/* 553:523 */     Mark.say(new Object[] {"------" });
/* 554:    */     
/* 555:525 */     disambiguator.resolve(Translator.getTranslator().translate(sentence3C).getElements().get(0));
/* 556:    */     
/* 557:527 */     Mark.say(new Object[] {"++++++" });
/* 558:    */     
/* 559:529 */     disambiguator.resolve(Translator.getTranslator().translate(sentence3A).getElements().get(0));
/* 560:    */     
/* 561:531 */     Mark.say(new Object[] {"------" });
/* 562:    */     
/* 563:533 */     disambiguator.resolve(Translator.getTranslator().translate(sentence3B).getElements().get(0));
/* 564:    */     
/* 565:535 */     Mark.say(new Object[] {"------" });
/* 566:    */     
/* 567:537 */     disambiguator.resolve(Translator.getTranslator().translate(sentence3C).getElements().get(0));
/* 568:    */     
/* 569:539 */     Mark.say(new Object[] {"------" });
/* 570:    */     
/* 571:541 */     disambiguator.resolve(Translator.getTranslator().translate(sentence10).getElements().get(0));
/* 572:    */     
/* 573:543 */     Mark.say(new Object[] {"------" });
/* 574:    */     
/* 575:545 */     disambiguator.resolve(Translator.getTranslator().translate(sentence11).getElements().get(0));
/* 576:    */     
/* 577:547 */     Mark.say(new Object[] {"------" });
/* 578:    */     
/* 579:549 */     disambiguator.resolve(Translator.getTranslator().translate(sentence9).getElements().get(0));
/* 580:    */     
/* 581:551 */     Mark.say(new Object[] {"------ Sue's examples" });
/* 582:    */     
/* 583:553 */     disambiguator.resolve(Translator.getTranslator().translate(sentence14).getElements().get(0));
/* 584:    */     
/* 585:555 */     disambiguator.resolve(Translator.getTranslator().translate(sentence15).getElements().get(0));
/* 586:    */     
/* 587:557 */     Entity t = (Entity)Translator.getTranslator().translate(sentence15).getElements().get(0);
/* 588:    */     
/* 589:559 */     disambiguator.resolve(t);
/* 590:    */     
/* 591:561 */     Mark.say(new Object[] {"Ambiguous:", Boolean.valueOf(disambiguator.ambiguous(t)) });
/* 592:    */     
/* 593:563 */     Mark.say(new Object[] {"Stopping" });
/* 594:    */   }
/* 595:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     disambiguator.Disambiguator
 * JD-Core Version:    0.7.0.1
 */