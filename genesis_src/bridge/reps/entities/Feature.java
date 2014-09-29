/*   1:    */ package bridge.reps.entities;
/*   2:    */ 
/*   3:    */ import bridge.utils.CollectionUtils;
/*   4:    */ import bridge.utils.StringUtils;
/*   5:    */ import bridge.utils.collections.ExclusiveSet;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Collection;
/*   9:    */ import java.util.HashSet;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Set;
/*  13:    */ import java.util.Vector;
/*  14:    */ 
/*  15:    */ public class Feature
/*  16:    */   extends Sequence
/*  17:    */   implements Set<Entity>
/*  18:    */ {
/*  19: 48 */   private double score = -1.0D;
/*  20:    */   private static final int FALSE = 0;
/*  21:    */   private static final int TRUE = 1;
/*  22:    */   private static final int UNKNOWN = 2;
/*  23:    */   public static final String FEATURE_MARKER = "graphfeature";
/*  24: 57 */   private int complete = 1;
/*  25: 60 */   private int closed = 1;
/*  26: 63 */   private int consistent = 1;
/*  27: 65 */   private ExclusiveSet<Entity> members = new ExclusiveSet(new HashSet(), Entity.class, 1);
/*  28:    */   
/*  29:    */   public Feature()
/*  30:    */   {
/*  31: 69 */     addType("graphfeature");
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Feature(Entity root)
/*  35:    */   {
/*  36: 74 */     this();
/*  37: 75 */     addRoot(root);
/*  38: 76 */     effectRootCompaction();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Feature(Entity root, double score)
/*  42:    */   {
/*  43: 80 */     this(root);
/*  44: 81 */     setScore(score);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean featureP()
/*  48:    */   {
/*  49: 85 */     return true;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setScore(double score)
/*  53:    */   {
/*  54: 88 */     this.score = score;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public double getScore()
/*  58:    */   {
/*  59: 89 */     return this.score;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Feature(Collection roots)
/*  63:    */   {
/*  64: 93 */     this();
/*  65: 94 */     for (Iterator i = roots.iterator(); i.hasNext();) {
/*  66: 95 */       addRoot((Entity)i.next());
/*  67:    */     }
/*  68: 97 */     effectRootCompaction();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Set<Entity> getMembers()
/*  72:    */   {
/*  73:102 */     HashSet<Entity> result = new HashSet();
/*  74:103 */     result.addAll(this.members);
/*  75:104 */     return result;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public List<Entity> getFeatureRoots()
/*  79:    */   {
/*  80:112 */     ArrayList<Entity> result = new ArrayList();
/*  81:113 */     result.addAll(getElements());
/*  82:114 */     return result;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public boolean addRoot(Entity root)
/*  86:    */   {
/*  87:123 */     if (getElements().contains(root)) {
/*  88:124 */       return false;
/*  89:    */     }
/*  90:126 */     super.addElement(root);
/*  91:127 */     add(root);
/*  92:128 */     this.complete = 2;
/*  93:129 */     this.closed = 2;
/*  94:130 */     this.consistent = 2;
/*  95:131 */     return true;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean addAllRoots(Collection roots)
/*  99:    */   {
/* 100:136 */     boolean result = false;
/* 101:137 */     for (Iterator i = roots.iterator(); i.hasNext();) {
/* 102:138 */       result |= addRoot((Entity)i.next());
/* 103:    */     }
/* 104:140 */     return result;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void addElement(Entity element)
/* 108:    */   {
/* 109:144 */     addRoot(element);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean removeRoot(Entity root)
/* 113:    */   {
/* 114:155 */     if (getElements().contains(root))
/* 115:    */     {
/* 116:156 */       this.complete = 2;
/* 117:157 */       this.closed = 2;
/* 118:158 */       this.consistent = 2;
/* 119:159 */       super.removeElement(root);
/* 120:160 */       return this.members.remove(root);
/* 121:    */     }
/* 122:162 */     return false;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean removeAllRoots(Collection<Entity> r)
/* 126:    */   {
/* 127:171 */     boolean result = false;
/* 128:172 */     for (Iterator<Entity> i = r.iterator(); i.hasNext();) {
/* 129:173 */       result |= removeRoot((Entity)i.next());
/* 130:    */     }
/* 131:175 */     return result;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public boolean retainAllRoots(Collection<Entity> r)
/* 135:    */   {
/* 136:179 */     Collection<Entity> reject = new ArrayList();
/* 137:181 */     for (Iterator<Entity> i = getElements().iterator(); i.hasNext();)
/* 138:    */     {
/* 139:182 */       Entity next = (Entity)i.next();
/* 140:183 */       if (!r.contains(next)) {
/* 141:183 */         reject.add(next);
/* 142:    */       }
/* 143:    */     }
/* 144:185 */     return removeAllRoots(reject);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean removeElement(Entity element)
/* 148:    */   {
/* 149:189 */     return removeRoot(element);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void effectRootCompletion()
/* 153:    */   {
/* 154:199 */     List roots = getFeatureRoots();
/* 155:201 */     for (int i = 0; i < roots.size(); i++) {
/* 156:201 */       this.members.addAll(((Entity)roots.get(i)).getDescendants());
/* 157:    */     }
/* 158:203 */     this.complete = 1;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void effectRootClosure()
/* 162:    */   {
/* 163:212 */     List<Entity> roots = getFeatureRoots();
/* 164:    */     
/* 165:214 */     Set<Entity> mems = getMembers();
/* 166:216 */     for (int i = 0; i < roots.size(); i++) {
/* 167:217 */       mems.removeAll(((Entity)roots.get(i)).getDescendants());
/* 168:    */     }
/* 169:220 */     mems.removeAll(roots);
/* 170:    */     
/* 171:222 */     this.members.removeAll(mems);
/* 172:    */     
/* 173:224 */     this.closed = 1;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void effectRootCompaction()
/* 177:    */   {
/* 178:233 */     effectRootCompletion();
/* 179:234 */     effectRootClosure();
/* 180:235 */     trimUnnecessaryRoots();
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void trimUnnecessaryRoots()
/* 184:    */   {
/* 185:243 */     List<Entity> queue = new ArrayList();
/* 186:244 */     Set<Entity> visited = new HashSet();
/* 187:    */     
/* 188:246 */     queue.addAll(getElements());
/* 189:250 */     while (!queue.isEmpty())
/* 190:    */     {
/* 191:251 */       Entity root = (Entity)queue.remove(0);
/* 192:252 */       if (visited.add(root))
/* 193:    */       {
/* 194:253 */         Set<Entity> children = root.getChildren();
/* 195:254 */         children.retainAll(this.members);
/* 196:255 */         queue.addAll(children);
/* 197:256 */         removeAllRoots(children);
/* 198:    */       }
/* 199:    */     }
/* 200:259 */     this.consistent = 1;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public boolean isComplete()
/* 204:    */   {
/* 205:267 */     if (this.complete == 1) {
/* 206:267 */       return true;
/* 207:    */     }
/* 208:268 */     if (this.complete == 0) {
/* 209:268 */       return false;
/* 210:    */     }
/* 211:270 */     Collection<Entity> mems = getMembers();
/* 212:271 */     for (int i = 0; i < getElements().size(); i++) {
/* 213:272 */       if (!mems.containsAll(((Entity)getElements().get(i)).getDescendants()))
/* 214:    */       {
/* 215:273 */         this.complete = 0;
/* 216:274 */         return false;
/* 217:    */       }
/* 218:    */     }
/* 219:277 */     this.complete = 1;
/* 220:278 */     return true;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public boolean isClosed()
/* 224:    */   {
/* 225:286 */     if (this.closed == 1) {
/* 226:286 */       return true;
/* 227:    */     }
/* 228:287 */     if (this.closed == 0) {
/* 229:287 */       return false;
/* 230:    */     }
/* 231:289 */     List<Entity> list = getFeatureRoots();
/* 232:290 */     Set<Entity> mems = getMembers();
/* 233:291 */     for (int i = 0; i < list.size(); i++) {
/* 234:292 */       mems.removeAll(((Entity)list.get(i)).getDescendants());
/* 235:    */     }
/* 236:294 */     if (mems.isEmpty())
/* 237:    */     {
/* 238:295 */       this.closed = 1;
/* 239:296 */       return true;
/* 240:    */     }
/* 241:298 */     this.closed = 0;
/* 242:299 */     return false;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public boolean isCompact()
/* 246:    */   {
/* 247:304 */     return isComplete() & isClosed();
/* 248:    */   }
/* 249:    */   
/* 250:    */   public boolean isConsistent()
/* 251:    */   {
/* 252:308 */     if (this.consistent == 1) {
/* 253:308 */       return true;
/* 254:    */     }
/* 255:309 */     if (this.consistent == 0) {
/* 256:309 */       return false;
/* 257:    */     }
/* 258:311 */     List<Entity> queue = new ArrayList();
/* 259:312 */     Set<Entity> visited = new HashSet();
/* 260:313 */     Set<Entity> roots = new HashSet();
/* 261:314 */     roots.addAll(getElements());
/* 262:    */     
/* 263:316 */     queue.addAll(getElements());
/* 264:320 */     while (!queue.isEmpty())
/* 265:    */     {
/* 266:321 */       Entity root = (Entity)queue.remove(0);
/* 267:322 */       if (visited.add(root))
/* 268:    */       {
/* 269:323 */         Set<Entity> descendants = root.getDescendants();
/* 270:324 */         visited.addAll(descendants);
/* 271:325 */         queue.removeAll(descendants);
/* 272:326 */         if (roots.removeAll(descendants)) {
/* 273:326 */           return false;
/* 274:    */         }
/* 275:    */       }
/* 276:    */     }
/* 277:328 */     return true;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public boolean isConnectedTo(Feature f)
/* 281:    */   {
/* 282:337 */     return CollectionUtils.isIntersectionNonempty(this, f);
/* 283:    */   }
/* 284:    */   
/* 285:    */   public boolean isConnectedTo(int order, Feature f)
/* 286:    */   {
/* 287:346 */     if (order == 0) {
/* 288:347 */       return isConnectedTo(f);
/* 289:    */     }
/* 290:349 */     Set<Entity> A = getMembers();
/* 291:350 */     Set B = f.getMembers();
/* 292:352 */     for (Iterator<Entity> i = A.iterator(); i.hasNext();)
/* 293:    */     {
/* 294:353 */       Entity next = (Entity)i.next();
/* 295:354 */       if (next.order() < order) {
/* 296:354 */         i.remove();
/* 297:    */       }
/* 298:    */     }
/* 299:356 */     for (Iterator i = B.iterator(); i.hasNext();)
/* 300:    */     {
/* 301:357 */       Entity next = (Entity)i.next();
/* 302:358 */       if (next.order() < order) {
/* 303:358 */         i.remove();
/* 304:    */       }
/* 305:    */     }
/* 306:360 */     return CollectionUtils.isIntersectionNonempty(A, B);
/* 307:    */   }
/* 308:    */   
/* 309:    */   public static String toDebugPrintStringFromCollection(Collection c)
/* 310:    */   {
/* 311:365 */     StringBuffer result = new StringBuffer();
/* 312:367 */     for (Iterator i = c.iterator(); i.hasNext();)
/* 313:    */     {
/* 314:368 */       Feature feature = (Feature)i.next();
/* 315:    */       
/* 316:370 */       result.append(feature.toStringDebug());
/* 317:371 */       result.append("\n\n");
/* 318:    */     }
/* 319:373 */     return result.toString();
/* 320:    */   }
/* 321:    */   
/* 322:    */   public String toStringDebug()
/* 323:    */   {
/* 324:378 */     StringBuffer result = new StringBuffer();
/* 325:379 */     result.append(getName());
/* 326:380 */     result.append(": (");
/* 327:381 */     result.append("Complete? ");
/* 328:382 */     switch (this.complete)
/* 329:    */     {
/* 330:    */     case 0: 
/* 331:383 */       result.append("F; "); break;
/* 332:    */     case 1: 
/* 333:384 */       result.append("T; "); break;
/* 334:    */     case 2: 
/* 335:385 */       result.append("U; ");
/* 336:    */     }
/* 337:387 */     result.append("Closed? ");
/* 338:388 */     switch (this.closed)
/* 339:    */     {
/* 340:    */     case 0: 
/* 341:389 */       result.append("F; "); break;
/* 342:    */     case 1: 
/* 343:390 */       result.append("T; "); break;
/* 344:    */     case 2: 
/* 345:391 */       result.append("U; ");
/* 346:    */     }
/* 347:393 */     result.append("Consistent? ");
/* 348:394 */     switch (this.consistent)
/* 349:    */     {
/* 350:    */     case 0: 
/* 351:395 */       result.append("F"); break;
/* 352:    */     case 1: 
/* 353:396 */       result.append("T"); break;
/* 354:    */     case 2: 
/* 355:397 */       result.append("U");
/* 356:    */     }
/* 357:399 */     result.append(")[");
/* 358:400 */     result.append(getScore());
/* 359:401 */     result.append("]\n");
/* 360:    */     
/* 361:403 */     result.append("\tRoots: ");
/* 362:404 */     String[] names = new String[getElements().size()];
/* 363:405 */     for (int i = 0; i < getElements().size(); i++) {
/* 364:406 */       names[i] = ((Entity)getElements().get(i)).getName();
/* 365:    */     }
/* 366:408 */     result.append(StringUtils.join(names, ", "));
/* 367:    */     
/* 368:410 */     result.append("\n\tMembers: ");
/* 369:411 */     names = new String[this.members.size()];
/* 370:412 */     int j = 0;
/* 371:413 */     for (Iterator<Entity> i = this.members.iterator(); i.hasNext();)
/* 372:    */     {
/* 373:414 */       names[j] = ((Entity)i.next()).getName();
/* 374:415 */       j++;
/* 375:    */     }
/* 376:417 */     result.append(StringUtils.join(names, ", "));
/* 377:    */     
/* 378:419 */     return result.toString();
/* 379:    */   }
/* 380:    */   
/* 381:    */   public boolean add(Entity m)
/* 382:    */   {
/* 383:438 */     boolean result = this.members.add(m);
/* 384:439 */     if (result)
/* 385:    */     {
/* 386:440 */       this.complete = 2;
/* 387:441 */       this.closed = 2;
/* 388:442 */       this.consistent = 2;
/* 389:443 */       return true;
/* 390:    */     }
/* 391:445 */     return false;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public boolean addAll(Collection<? extends Entity> m)
/* 395:    */   {
/* 396:450 */     boolean result = this.members.addAll(m);
/* 397:451 */     if (result)
/* 398:    */     {
/* 399:452 */       this.complete = 2;
/* 400:453 */       this.closed = 2;
/* 401:454 */       this.consistent = 2;
/* 402:455 */       return true;
/* 403:    */     }
/* 404:457 */     return false;
/* 405:    */   }
/* 406:    */   
/* 407:    */   public void clear()
/* 408:    */   {
/* 409:462 */     this.closed = 1;
/* 410:463 */     this.complete = 1;
/* 411:464 */     this.consistent = 1;
/* 412:465 */     for (int i = 0; i < getElements().size(); i++) {
/* 413:466 */       removeElement((Entity)getElements().get(i));
/* 414:    */     }
/* 415:468 */     this.members.clear();
/* 416:    */   }
/* 417:    */   
/* 418:    */   public boolean contains(Object m)
/* 419:    */   {
/* 420:471 */     return this.members.contains(m);
/* 421:    */   }
/* 422:    */   
/* 423:    */   public boolean containsAll(Collection m)
/* 424:    */   {
/* 425:473 */     return this.members.containsAll(m);
/* 426:    */   }
/* 427:    */   
/* 428:    */   public boolean containsSome(Collection m)
/* 429:    */   {
/* 430:482 */     for (Iterator i = m.iterator(); i.hasNext();)
/* 431:    */     {
/* 432:483 */       Object member = i.next();
/* 433:484 */       if (contains(member)) {
/* 434:484 */         return true;
/* 435:    */       }
/* 436:    */     }
/* 437:486 */     return false;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public boolean isEmpty()
/* 441:    */   {
/* 442:489 */     return this.members.isEmpty();
/* 443:    */   }
/* 444:    */   
/* 445:    */   public Iterator<Entity> iterator()
/* 446:    */   {
/* 447:491 */     return this.members.iterator();
/* 448:    */   }
/* 449:    */   
/* 450:    */   public boolean remove(Object m)
/* 451:    */   {
/* 452:494 */     removeRoot((Entity)m);
/* 453:495 */     boolean result = this.members.remove(m);
/* 454:496 */     if (result)
/* 455:    */     {
/* 456:497 */       this.complete = 2;
/* 457:498 */       this.closed = 2;
/* 458:499 */       this.consistent = 2;
/* 459:500 */       return true;
/* 460:    */     }
/* 461:502 */     return false;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public boolean removeAll(Collection<?> m)
/* 465:    */   {
/* 466:509 */     removeAllRoots(m);
/* 467:510 */     boolean result = this.members.removeAll(m);
/* 468:511 */     if (result)
/* 469:    */     {
/* 470:512 */       this.complete = 2;
/* 471:513 */       this.closed = 2;
/* 472:514 */       this.consistent = 2;
/* 473:515 */       return true;
/* 474:    */     }
/* 475:517 */     return false;
/* 476:    */   }
/* 477:    */   
/* 478:    */   public boolean retainAll(Collection<?> m)
/* 479:    */   {
/* 480:524 */     retainAllRoots(m);
/* 481:525 */     boolean result = this.members.retainAll(m);
/* 482:526 */     if (result)
/* 483:    */     {
/* 484:527 */       this.complete = 2;
/* 485:528 */       this.closed = 2;
/* 486:529 */       this.consistent = 2;
/* 487:530 */       return true;
/* 488:    */     }
/* 489:532 */     return false;
/* 490:    */   }
/* 491:    */   
/* 492:    */   public int size()
/* 493:    */   {
/* 494:536 */     return this.members.size();
/* 495:    */   }
/* 496:    */   
/* 497:    */   public Object[] toArray()
/* 498:    */   {
/* 499:538 */     return this.members.toArray();
/* 500:    */   }
/* 501:    */   
/* 502:    */   public <T> T[] toArray(T[] array)
/* 503:    */   {
/* 504:540 */     return this.members.toArray(array);
/* 505:    */   }
/* 506:    */   
/* 507:    */   public static Feature merge(Feature A, Feature B)
/* 508:    */   {
/* 509:552 */     Feature result = new Feature();
/* 510:    */     
/* 511:    */ 
/* 512:    */ 
/* 513:556 */     result.addAll(A.members);
/* 514:557 */     result.addAll(B.members);
/* 515:    */     
/* 516:559 */     result.addAllRoots(A.getElements());
/* 517:560 */     result.addAllRoots(B.getElements());
/* 518:562 */     if (((A.closed == 1 ? 1 : 0) & (B.closed == 1 ? 1 : 0)) != 0) {
/* 519:563 */       result.closed = 1;
/* 520:    */     } else {
/* 521:565 */       result.closed = 2;
/* 522:    */     }
/* 523:568 */     if (((A.complete == 1 ? 1 : 0) & (B.complete == 1 ? 1 : 0)) != 0) {
/* 524:569 */       result.complete = 1;
/* 525:    */     } else {
/* 526:571 */       result.complete = 2;
/* 527:    */     }
/* 528:574 */     result.consistent = 2;
/* 529:    */     
/* 530:576 */     return result;
/* 531:    */   }
/* 532:    */   
/* 533:    */   public static Feature merge(Collection<Feature> features)
/* 534:    */   {
/* 535:584 */     Feature result = new Feature();
/* 536:597 */     for (Iterator<Feature> i = features.iterator(); i.hasNext();)
/* 537:    */     {
/* 538:598 */       Feature f = (Feature)i.next();
/* 539:599 */       result.addAll(f.members);
/* 540:600 */       result.addAllRoots(f.getElements());
/* 541:602 */       if (((result.closed == 1 ? 1 : 0) & (f.closed == 1 ? 1 : 0)) != 0) {
/* 542:603 */         result.closed = 1;
/* 543:    */       } else {
/* 544:605 */         result.closed = 2;
/* 545:    */       }
/* 546:608 */       if (((result.complete == 1 ? 1 : 0) & (f.complete == 1 ? 1 : 0)) != 0) {
/* 547:609 */         result.complete = 1;
/* 548:    */       } else {
/* 549:611 */         result.complete = 2;
/* 550:    */       }
/* 551:    */     }
/* 552:616 */     result.consistent = 2;
/* 553:    */     
/* 554:618 */     return result;
/* 555:    */   }
/* 556:    */   
/* 557:    */   public static Feature mergeConsistently(Feature A, Feature B)
/* 558:    */   {
/* 559:622 */     Collection<Feature> features = new HashSet();
/* 560:623 */     features.add(A);
/* 561:624 */     features.add(B);
/* 562:625 */     return mergeConsistently(features);
/* 563:    */   }
/* 564:    */   
/* 565:    */   public static Feature mergeConsistently(Collection<Feature> features)
/* 566:    */   {
/* 567:629 */     Feature result = merge(features);
/* 568:630 */     result.trimUnnecessaryRoots();
/* 569:631 */     return result;
/* 570:    */   }
/* 571:    */   
/* 572:    */   public static Set<Feature> consolidateConnectedFeatures(Collection<Feature> c)
/* 573:    */   {
/* 574:636 */     ArrayList<Feature> features = new ArrayList();
/* 575:637 */     features.addAll(c);
/* 576:    */     
/* 577:    */ 
/* 578:640 */     HashSet<Feature> connected = new HashSet();
/* 579:641 */     int after = features.size();
/* 580:642 */     int before = after;
/* 581:643 */     int noluck = 0;
/* 582:645 */     while (((before >= after ? 1 : 0) & (noluck < after ? 1 : 0) & (features.isEmpty() ? 0 : 1)) != 0)
/* 583:    */     {
/* 584:646 */       before = after;
/* 585:647 */       Feature A = (Feature)features.remove(0);
/* 586:648 */       connected.clear();
/* 587:649 */       for (int i = 0; i < features.size(); i++)
/* 588:    */       {
/* 589:650 */         Feature B = (Feature)features.get(i);
/* 590:651 */         System.out.println("Testing " + A.getName() + " against " + B.getName());
/* 591:652 */         if (A.isConnectedTo(B))
/* 592:    */         {
/* 593:653 */           System.out.println(A.getName() + " is connected to " + B.getName());
/* 594:654 */           connected.add(B);
/* 595:    */         }
/* 596:    */       }
/* 597:658 */       if (connected.isEmpty())
/* 598:    */       {
/* 599:659 */         noluck++;
/* 600:660 */         features.add(A);
/* 601:    */       }
/* 602:    */       else
/* 603:    */       {
/* 604:662 */         noluck = 0;
/* 605:663 */         features.removeAll(connected);
/* 606:664 */         connected.add(A);
/* 607:665 */         features.add(mergeConsistently(connected));
/* 608:    */       }
/* 609:667 */       System.out.println("# of Features: " + features.size() + ", noluck: " + noluck);
/* 610:668 */       after = features.size();
/* 611:    */     }
/* 612:671 */     Set<Feature> result = new HashSet();
/* 613:672 */     result.addAll(features);
/* 614:673 */     return result;
/* 615:    */   }
/* 616:    */   
/* 617:    */   public static Set<Feature> consolidateConnectedFeatures(int order, Collection<Feature> c)
/* 618:    */   {
/* 619:677 */     if (order == 0) {
/* 620:678 */       return consolidateConnectedFeatures(c);
/* 621:    */     }
/* 622:681 */     ArrayList<Feature> features = new ArrayList();
/* 623:682 */     features.addAll(c);
/* 624:    */     
/* 625:    */ 
/* 626:685 */     HashSet<Feature> connected = new HashSet();
/* 627:686 */     int after = features.size();
/* 628:687 */     int before = after;
/* 629:688 */     int noluck = 0;
/* 630:690 */     while (((before >= after ? 1 : 0) & (noluck < after ? 1 : 0) & (features.isEmpty() ? 0 : 1)) != 0)
/* 631:    */     {
/* 632:691 */       before = after;
/* 633:692 */       Feature A = (Feature)features.remove(0);
/* 634:693 */       connected.clear();
/* 635:694 */       for (int i = 0; i < features.size(); i++)
/* 636:    */       {
/* 637:695 */         Feature B = (Feature)features.get(i);
/* 638:696 */         System.out.println("Testing " + A.getName() + " against " + B.getName());
/* 639:697 */         if (A.isConnectedTo(order, B))
/* 640:    */         {
/* 641:698 */           System.out.println(A.getName() + " is connected to " + B.getName());
/* 642:699 */           connected.add(B);
/* 643:    */         }
/* 644:    */       }
/* 645:703 */       if (connected.isEmpty())
/* 646:    */       {
/* 647:704 */         noluck++;
/* 648:705 */         features.add(A);
/* 649:    */       }
/* 650:    */       else
/* 651:    */       {
/* 652:707 */         noluck = 0;
/* 653:708 */         features.removeAll(connected);
/* 654:709 */         connected.add(A);
/* 655:710 */         features.add(merge(connected));
/* 656:    */       }
/* 657:712 */       System.out.println("# of Features: " + features.size() + ", noluck: " + noluck);
/* 658:713 */       after = features.size();
/* 659:    */     }
/* 660:716 */     Set<Feature> result = new HashSet();
/* 661:717 */     result.addAll(features);
/* 662:718 */     return result;
/* 663:    */   }
/* 664:    */   
/* 665:    */   public static Set<Feature> consolidateConnectedFeaturesConsistently(Collection<Feature> c)
/* 666:    */   {
/* 667:723 */     ArrayList<Feature> features = new ArrayList();
/* 668:724 */     features.addAll(c);
/* 669:    */     
/* 670:    */ 
/* 671:727 */     HashSet<Feature> connected = new HashSet();
/* 672:728 */     int after = features.size();
/* 673:729 */     int before = after;
/* 674:730 */     int noluck = 0;
/* 675:732 */     while (((before >= after ? 1 : 0) & (noluck < after ? 1 : 0) & (features.isEmpty() ? 0 : 1)) != 0)
/* 676:    */     {
/* 677:733 */       before = after;
/* 678:734 */       Feature A = (Feature)features.remove(0);
/* 679:735 */       connected.clear();
/* 680:736 */       for (int i = 0; i < features.size(); i++)
/* 681:    */       {
/* 682:737 */         Feature B = (Feature)features.get(i);
/* 683:738 */         System.out.println("Testing " + A.getName() + " against " + B.getName());
/* 684:739 */         if (A.isConnectedTo(B))
/* 685:    */         {
/* 686:740 */           System.out.println(A.getName() + " is connected to " + B.getName());
/* 687:741 */           connected.add(B);
/* 688:    */         }
/* 689:    */       }
/* 690:745 */       if (connected.isEmpty())
/* 691:    */       {
/* 692:746 */         noluck++;
/* 693:747 */         features.add(A);
/* 694:    */       }
/* 695:    */       else
/* 696:    */       {
/* 697:749 */         noluck = 0;
/* 698:750 */         features.removeAll(connected);
/* 699:751 */         connected.add(A);
/* 700:752 */         features.add(mergeConsistently(connected));
/* 701:    */       }
/* 702:754 */       System.out.println("# of Features: " + features.size() + ", noluck: " + noluck);
/* 703:755 */       after = features.size();
/* 704:    */     }
/* 705:758 */     Set<Feature> result = new HashSet();
/* 706:759 */     result.addAll(features);
/* 707:760 */     return result;
/* 708:    */   }
/* 709:    */   
/* 710:    */   public static Set<Feature> consolidateConnectedFeaturesConsistently(int order, Collection<Feature> c)
/* 711:    */   {
/* 712:764 */     if (order == 0) {
/* 713:765 */       return consolidateConnectedFeaturesConsistently(c);
/* 714:    */     }
/* 715:768 */     ArrayList<Feature> features = new ArrayList();
/* 716:769 */     features.addAll(c);
/* 717:    */     
/* 718:    */ 
/* 719:772 */     HashSet<Feature> connected = new HashSet();
/* 720:773 */     int after = features.size();
/* 721:774 */     int before = after;
/* 722:775 */     int noluck = 0;
/* 723:778 */     while (((before >= after ? 1 : 0) & (noluck < after ? 1 : 0) & (features.isEmpty() ? 0 : 1)) != 0)
/* 724:    */     {
/* 725:779 */       before = after;
/* 726:780 */       Feature A = (Feature)features.remove(0);
/* 727:781 */       connected.clear();
/* 728:782 */       for (int i = 0; i < features.size(); i++)
/* 729:    */       {
/* 730:783 */         Feature B = (Feature)features.get(i);
/* 731:785 */         if (A.isConnectedTo(order, B)) {
/* 732:787 */           connected.add(B);
/* 733:    */         }
/* 734:    */       }
/* 735:791 */       if (connected.isEmpty())
/* 736:    */       {
/* 737:792 */         noluck++;
/* 738:793 */         features.add(A);
/* 739:    */       }
/* 740:    */       else
/* 741:    */       {
/* 742:795 */         noluck = 0;
/* 743:796 */         features.removeAll(connected);
/* 744:797 */         connected.add(A);
/* 745:798 */         features.add(mergeConsistently(connected));
/* 746:    */       }
/* 747:801 */       after = features.size();
/* 748:    */     }
/* 749:804 */     Set<Feature> result = new HashSet();
/* 750:805 */     result.addAll(features);
/* 751:806 */     return result;
/* 752:    */   }
/* 753:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.Feature
 * JD-Core Version:    0.7.0.1
 */