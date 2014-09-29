/*   1:    */ package bridge.reps.entities;
/*   2:    */ 
/*   3:    */ import bridge.utils.logging.Level;
/*   4:    */ import bridge.utils.logging.Logger;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import java.util.Collection;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Set;
/*  11:    */ import java.util.Vector;
/*  12:    */ 
/*  13:    */ public class Bundle
/*  14:    */   extends Vector<Thread>
/*  15:    */ {
/*  16: 33 */   private Entity ownerThing = null;
/*  17:    */   
/*  18:    */   public Bundle() {}
/*  19:    */   
/*  20:    */   public Bundle(Thread t)
/*  21:    */   {
/*  22: 42 */     this();
/*  23: 43 */     addThread(t);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Bundle copy()
/*  27:    */   {
/*  28: 47 */     Bundle result = new Bundle();
/*  29: 48 */     result.addAll(this);
/*  30: 49 */     return result;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Object clone()
/*  34:    */   {
/*  35: 56 */     Bundle b = new Bundle();
/*  36: 57 */     for (int i = 0; i < size(); i++) {
/*  37: 58 */       b.add(((Thread)get(i)).copyThread());
/*  38:    */     }
/*  39: 60 */     return b;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Bundle getClone()
/*  43:    */   {
/*  44: 64 */     return (Bundle)clone();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Bundle getThingClones()
/*  48:    */   {
/*  49: 68 */     Bundle bundle = new Bundle();
/*  50: 69 */     for (Thread t : this) {
/*  51: 70 */       if ((t.contains("thing")) || (t.contains("action")) || (t.contains("ad_word"))) {
/*  52: 71 */         bundle.add((Thread)t.clone());
/*  53:    */       }
/*  54:    */     }
/*  55: 74 */     return bundle;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Bundle getAllClones()
/*  59:    */   {
/*  60: 78 */     Bundle bundle = new Bundle();
/*  61: 79 */     for (Thread t : this) {
/*  62: 80 */       bundle.add((Thread)t.clone());
/*  63:    */     }
/*  64: 82 */     return bundle;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Entity getOwnerThing()
/*  68:    */   {
/*  69: 92 */     return this.ownerThing;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setOwnerThing(Entity t)
/*  73:    */   {
/*  74: 96 */     if (this.ownerThing == t) {
/*  75: 97 */       return;
/*  76:    */     }
/*  77:100 */     if (this.ownerThing != null) {
/*  78:101 */       this.ownerThing.setBundle(null);
/*  79:    */     }
/*  80:104 */     this.ownerThing = t;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setOwnerThingNull()
/*  84:    */   {
/*  85:108 */     this.ownerThing = null;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean addThread(Thread t)
/*  89:    */   {
/*  90:116 */     add(0, t);
/*  91:117 */     t.setOwnerBundle(this);
/*  92:118 */     return true;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean removeThread(Thread t)
/*  96:    */   {
/*  97:129 */     boolean val = remove(t);
/*  98:130 */     if (val) {
/*  99:131 */       t.setOwnerBundleNull();
/* 100:    */     }
/* 101:133 */     return val;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean addThreadAtEnd(Thread t)
/* 105:    */   {
/* 106:141 */     add(t);
/* 107:142 */     t.setOwnerBundle(this);
/* 108:143 */     return true;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void pushPrimedThread()
/* 112:    */   {
/* 113:150 */     addThread((Thread)getPrimedThread().clone());
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void swapPrimedThread()
/* 117:    */   {
/* 118:157 */     if (size() < 2) {
/* 119:158 */       return;
/* 120:    */     }
/* 121:160 */     Thread object = (Thread)elementAt(1);
/* 122:161 */     removeElementAt(1);
/* 123:162 */     add(0, object);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void sendPrimedThreadToEnd()
/* 127:    */   {
/* 128:169 */     if (size() > 0)
/* 129:    */     {
/* 130:170 */       Thread thread = (Thread)elementAt(0);
/* 131:171 */       add(thread);
/* 132:172 */       remove(0);
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Thread getPrimedThread()
/* 137:    */   {
/* 138:180 */     if (size() == 0) {
/* 139:181 */       addThread(new Thread());
/* 140:    */     }
/* 141:183 */     return (Thread)firstElement();
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setPrimedThread(int i)
/* 145:    */   {
/* 146:190 */     if ((i >= 0) || (i < size()))
/* 147:    */     {
/* 148:191 */       Thread t = (Thread)elementAt(i);
/* 149:192 */       removeElementAt(i);
/* 150:193 */       add(0, t);
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setPrimedThread(Thread thread)
/* 155:    */   {
/* 156:198 */     int index = indexOf(thread);
/* 157:199 */     if (index >= 0) {
/* 158:200 */       setPrimedThread(index);
/* 159:    */     } else {
/* 160:203 */       add(0, thread);
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   public Thread getThread(String firstElement)
/* 165:    */   {
/* 166:211 */     for (int i = 0; i < size(); i++)
/* 167:    */     {
/* 168:212 */       Thread thread = (Thread)elementAt(i);
/* 169:213 */       if (!thread.isEmpty())
/* 170:    */       {
/* 171:214 */         String first = (String)thread.firstElement();
/* 172:215 */         if (firstElement.equals(first)) {
/* 173:216 */           return thread;
/* 174:    */         }
/* 175:    */       }
/* 176:    */     }
/* 177:220 */     return null;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public Thread getThreadContaining(String element)
/* 181:    */   {
/* 182:230 */     Thread result = null;
/* 183:231 */     for (int i = 0; i < size(); i++)
/* 184:    */     {
/* 185:232 */       Thread thread = (Thread)elementAt(i);
/* 186:233 */       if (thread.contains(element))
/* 187:    */       {
/* 188:234 */         result = thread;
/* 189:235 */         break;
/* 190:    */       }
/* 191:    */     }
/* 192:238 */     return result;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public Bundle filterFor(String element)
/* 196:    */   {
/* 197:242 */     Bundle b = new Bundle();
/* 198:243 */     for (int i = 0; i < size(); i++)
/* 199:    */     {
/* 200:244 */       Thread thread = (Thread)elementAt(i);
/* 201:245 */       if (thread.contains(element)) {
/* 202:246 */         b.add(thread);
/* 203:    */       }
/* 204:    */     }
/* 205:249 */     return b;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public Bundle filterForRoot(String element)
/* 209:    */   {
/* 210:253 */     Bundle b = new Bundle();
/* 211:254 */     for (int i = 0; i < size(); i++)
/* 212:    */     {
/* 213:255 */       Thread thread = (Thread)elementAt(i);
/* 214:256 */       if ((thread.size() > 0) && (((String)thread.get(0)).equals(element))) {
/* 215:257 */         b.add(thread);
/* 216:    */       }
/* 217:    */     }
/* 218:260 */     return b;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public Bundle filterFor(List<String> elements)
/* 222:    */   {
/* 223:264 */     Bundle b = new Bundle();
/* 224:    */     int i;
/* 225:265 */     for (Iterator localIterator = elements.iterator(); localIterator.hasNext(); i < size())
/* 226:    */     {
/* 227:265 */       String element = (String)localIterator.next();
/* 228:266 */       i = 0; continue;
/* 229:267 */       Thread thread = (Thread)elementAt(i);
/* 230:268 */       if ((thread.contains(element)) && (!b.contains(thread))) {
/* 231:269 */         b.add(thread);
/* 232:    */       }
/* 233:266 */       i++;
/* 234:    */     }
/* 235:273 */     return b;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public Bundle filterForNot(String element)
/* 239:    */   {
/* 240:277 */     Bundle b = new Bundle();
/* 241:278 */     for (int i = 0; i < size(); i++)
/* 242:    */     {
/* 243:279 */       Thread thread = (Thread)elementAt(i);
/* 244:280 */       if (!thread.contains(element)) {
/* 245:281 */         b.add(thread);
/* 246:    */       }
/* 247:    */     }
/* 248:284 */     return b;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void addType(String t)
/* 252:    */   {
/* 253:291 */     getPrimedThread().addType(t);
/* 254:    */   }
/* 255:    */   
/* 256:    */   public String getType()
/* 257:    */   {
/* 258:298 */     return getPrimedThread().getType();
/* 259:    */   }
/* 260:    */   
/* 261:    */   public String[] getAllTypes()
/* 262:    */   {
/* 263:308 */     return getAllTypesExcept(null);
/* 264:    */   }
/* 265:    */   
/* 266:    */   public String[] getAllTypesExcept(String[] ignoreTheseThreads)
/* 267:    */   {
/* 268:318 */     Set<String> types = new HashSet();
/* 269:320 */     for (Iterator i = iterator(); i.hasNext();)
/* 270:    */     {
/* 271:321 */       Thread thread = (Thread)i.next();
/* 272:322 */       if (!thread.contains(ignoreTheseThreads)) {
/* 273:323 */         types.addAll(thread);
/* 274:    */       }
/* 275:    */     }
/* 276:325 */     return (String[])types.toArray(new String[types.size()]);
/* 277:    */   }
/* 278:    */   
/* 279:    */   public String getSupertype()
/* 280:    */   {
/* 281:335 */     return getPrimedThread().getSupertype();
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void prune()
/* 285:    */   {
/* 286:345 */     Vector<Thread> result = new Vector();
/* 287:346 */     for (int i = 0; i < size(); i++)
/* 288:    */     {
/* 289:347 */       Thread t = (Thread)elementAt(i);
/* 290:348 */       if ((!bottomSubThread(t)) && 
/* 291:349 */         (!result.contains(t))) {
/* 292:350 */         result.add(t);
/* 293:    */       }
/* 294:    */     }
/* 295:354 */     clear();
/* 296:355 */     addAll(result);
/* 297:    */   }
/* 298:    */   
/* 299:    */   protected void threadModified(Thread t, String oldState, String newState)
/* 300:    */   {
/* 301:374 */     fireNotification();
/* 302:    */   }
/* 303:    */   
/* 304:380 */   boolean saveStringForm = false;
/* 305:    */   
/* 306:    */   public void setSaveStringForm(boolean b)
/* 307:    */   {
/* 308:383 */     this.saveStringForm = b;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void setSaveStringFormOnDependents(boolean b)
/* 312:    */   {
/* 313:387 */     setSaveStringForm(b);
/* 314:388 */     for (int i = 0; i < size(); i++) {
/* 315:389 */       ((Thread)get(i)).setSaveStringFormOnDependents(b);
/* 316:    */     }
/* 317:    */   }
/* 318:    */   
/* 319:    */   public boolean getSaveStringForm()
/* 320:    */   {
/* 321:394 */     return this.saveStringForm;
/* 322:    */   }
/* 323:    */   
/* 324:401 */   boolean notification = false;
/* 325:    */   
/* 326:    */   public void setNotification(boolean b)
/* 327:    */   {
/* 328:410 */     this.notification = b;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void setNotificationOnDependents(boolean b)
/* 332:    */   {
/* 333:421 */     setNotification(b);
/* 334:422 */     for (int i = 0; i < size(); i++) {
/* 335:423 */       ((Thread)get(i)).setNotificationOnDependents(b);
/* 336:    */     }
/* 337:    */   }
/* 338:    */   
/* 339:430 */   protected String previousState = null;
/* 340:    */   public static final String LOGGER_GROUP = "things";
/* 341:    */   public static final String LOGGER_INSTANCE = "Bundle";
/* 342:    */   public static final String LOGGER = "things.Bundle";
/* 343:    */   
/* 344:    */   protected void saveState()
/* 345:    */   {
/* 346:439 */     if (!this.notification) {
/* 347:440 */       return;
/* 348:    */     }
/* 349:442 */     if (this.saveStringForm) {
/* 350:443 */       this.previousState = toString();
/* 351:    */     }
/* 352:445 */     if (getOwnerThing() != null) {
/* 353:446 */       getOwnerThing().saveState();
/* 354:    */     }
/* 355:    */   }
/* 356:    */   
/* 357:    */   protected void fireNotification()
/* 358:    */   {
/* 359:457 */     if (getOwnerThing() != null) {
/* 360:458 */       if (this.saveStringForm) {
/* 361:459 */         getOwnerThing().bundleModified(this, this.previousState, toString());
/* 362:    */       } else {
/* 363:462 */         getOwnerThing().bundleModified(this, null, null);
/* 364:    */       }
/* 365:    */     }
/* 366:    */   }
/* 367:    */   
/* 368:    */   private boolean bottomSubThread(Thread t)
/* 369:    */   {
/* 370:469 */     Vector v = this;
/* 371:470 */     for (int i = 0; i < v.size(); i++)
/* 372:    */     {
/* 373:471 */       Thread t2 = (Thread)v.elementAt(i);
/* 374:473 */       if (!t.equals(t2)) {
/* 375:475 */         if (bottomSubThread(t, t2)) {
/* 376:476 */           return true;
/* 377:    */         }
/* 378:    */       }
/* 379:    */     }
/* 380:479 */     return false;
/* 381:    */   }
/* 382:    */   
/* 383:    */   private static boolean bottomSubThread(Thread t1, Thread t2)
/* 384:    */   {
/* 385:484 */     int i1 = t1.size();
/* 386:485 */     int i2 = t2.size();
/* 387:486 */     if (i2 < i1) {
/* 388:487 */       return false;
/* 389:    */     }
/* 390:489 */     if ((i1 == 0) && (i2 == 0)) {
/* 391:490 */       return false;
/* 392:    */     }
/* 393:492 */     int delta = i2 - i1;
/* 394:493 */     String zero1 = (String)t1.elementAt(0);
/* 395:494 */     String zero2 = (String)t2.elementAt(0);
/* 396:495 */     if (!zero1.equalsIgnoreCase(zero2)) {
/* 397:496 */       return false;
/* 398:    */     }
/* 399:498 */     for (int i = 1; i < t1.size(); i++)
/* 400:    */     {
/* 401:499 */       String s1 = (String)t1.elementAt(i);
/* 402:500 */       String s2 = (String)t2.elementAt(i + delta);
/* 403:501 */       if (!s1.equalsIgnoreCase(s2)) {
/* 404:502 */         return false;
/* 405:    */       }
/* 406:    */     }
/* 407:505 */     return true;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public String toString()
/* 411:    */   {
/* 412:512 */     return toString(false);
/* 413:    */   }
/* 414:    */   
/* 415:    */   public String toString(boolean compact)
/* 416:    */   {
/* 417:516 */     if (compact)
/* 418:    */     {
/* 419:517 */       String s = "(";
/* 420:518 */       Iterator i = iterator();
/* 421:519 */       while (i.hasNext())
/* 422:    */       {
/* 423:520 */         s = s + ((Thread)i.next()).toString(compact);
/* 424:521 */         if (i.hasNext()) {
/* 425:521 */           s = s + ", ";
/* 426:    */         }
/* 427:    */       }
/* 428:523 */       s = s + ")";
/* 429:524 */       return s;
/* 430:    */     }
/* 431:527 */     return Tags.tagNoLine("bundle", Tags.tag(this));
/* 432:    */   }
/* 433:    */   
/* 434:    */   public static String showDifferences(Bundle f, Bundle s)
/* 435:    */   {
/* 436:532 */     String result = null;
/* 437:534 */     if (f.size() != s.size())
/* 438:    */     {
/* 439:535 */       if (result == null) {
/* 440:536 */         result = "";
/* 441:    */       }
/* 442:538 */       result = result + f + " and " + s + "\nare of different sizes\n";
/* 443:    */     }
/* 444:    */     else
/* 445:    */     {
/* 446:543 */       for (int i = 0; i < f.size(); i++)
/* 447:    */       {
/* 448:544 */         Thread ft = (Thread)f.get(i);
/* 449:545 */         Thread st = (Thread)s.get(i);
/* 450:546 */         String diff = Thread.showDifferences(ft, st);
/* 451:547 */         if (diff != null)
/* 452:    */         {
/* 453:548 */           if (result == null) {
/* 454:549 */             result = "";
/* 455:    */           }
/* 456:551 */           result = result + diff + "\n";
/* 457:    */         }
/* 458:    */       }
/* 459:    */     }
/* 460:556 */     return result;
/* 461:    */   }
/* 462:    */   
/* 463:    */   public static Bundle extractInstance(String s)
/* 464:    */   {
/* 465:563 */     Bundle result = new Bundle();
/* 466:564 */     String x = Tags.untagString("bundle", s).trim();
/* 467:565 */     for (IteratorForXML iterator = new IteratorForXML(x, "thread"); iterator.hasNext();)
/* 468:    */     {
/* 469:566 */       Thread thread = Thread.extractInstance(iterator.next());
/* 470:567 */       result.add(thread);
/* 471:    */     }
/* 472:569 */     return result;
/* 473:    */   }
/* 474:    */   
/* 475:    */   public void add(int index, Thread element)
/* 476:    */   {
/* 477:581 */     super.add(index, element);
/* 478:    */   }
/* 479:    */   
/* 480:    */   public boolean add(Thread o)
/* 481:    */   {
/* 482:585 */     saveState();
/* 483:586 */     boolean val = super.add(o);
/* 484:587 */     if (val) {
/* 485:588 */       fireNotification();
/* 486:    */     }
/* 487:590 */     return val;
/* 488:    */   }
/* 489:    */   
/* 490:    */   public boolean addAll(Collection<? extends Thread> c)
/* 491:    */   {
/* 492:594 */     saveState();
/* 493:595 */     boolean val = super.addAll(c);
/* 494:596 */     if (val) {
/* 495:597 */       fireNotification();
/* 496:    */     }
/* 497:599 */     return val;
/* 498:    */   }
/* 499:    */   
/* 500:    */   public boolean addAll(int index, Collection<? extends Thread> c)
/* 501:    */   {
/* 502:603 */     saveState();
/* 503:604 */     boolean val = super.addAll(index, c);
/* 504:605 */     if (val) {
/* 505:606 */       fireNotification();
/* 506:    */     }
/* 507:608 */     return val;
/* 508:    */   }
/* 509:    */   
/* 510:    */   public void addElement(Thread obj)
/* 511:    */   {
/* 512:612 */     saveState();
/* 513:613 */     super.addElement(obj);
/* 514:614 */     fireNotification();
/* 515:    */   }
/* 516:    */   
/* 517:    */   public void clear()
/* 518:    */   {
/* 519:621 */     super.clear();
/* 520:    */   }
/* 521:    */   
/* 522:    */   public void insertElementAt(Thread obj, int index)
/* 523:    */   {
/* 524:625 */     saveState();
/* 525:626 */     super.insertElementAt(obj, index);
/* 526:627 */     fireNotification();
/* 527:    */   }
/* 528:    */   
/* 529:    */   public Thread remove(int index)
/* 530:    */   {
/* 531:631 */     saveState();
/* 532:632 */     Thread obj = (Thread)super.remove(index);
/* 533:633 */     if (obj != null) {
/* 534:634 */       fireNotification();
/* 535:    */     }
/* 536:636 */     return obj;
/* 537:    */   }
/* 538:    */   
/* 539:    */   public boolean remove(Object o)
/* 540:    */   {
/* 541:643 */     return super.remove(o);
/* 542:    */   }
/* 543:    */   
/* 544:    */   public boolean removeAll(Collection c)
/* 545:    */   {
/* 546:647 */     saveState();
/* 547:648 */     boolean val = super.removeAll(c);
/* 548:649 */     if (val) {
/* 549:650 */       fireNotification();
/* 550:    */     }
/* 551:652 */     return val;
/* 552:    */   }
/* 553:    */   
/* 554:    */   public void removeAllElements()
/* 555:    */   {
/* 556:656 */     saveState();
/* 557:657 */     super.removeAllElements();
/* 558:658 */     fireNotification();
/* 559:    */   }
/* 560:    */   
/* 561:    */   public boolean removeElement(Object obj)
/* 562:    */   {
/* 563:665 */     return super.removeElement(obj);
/* 564:    */   }
/* 565:    */   
/* 566:    */   public void removeElementAt(int index)
/* 567:    */   {
/* 568:669 */     saveState();
/* 569:670 */     super.removeElementAt(index);
/* 570:671 */     fireNotification();
/* 571:    */   }
/* 572:    */   
/* 573:    */   protected void removeRange(int fromIndex, int toIndex)
/* 574:    */   {
/* 575:675 */     saveState();
/* 576:676 */     super.removeRange(fromIndex, toIndex);
/* 577:677 */     fireNotification();
/* 578:    */   }
/* 579:    */   
/* 580:    */   public boolean retainAll(Collection c)
/* 581:    */   {
/* 582:681 */     saveState();
/* 583:682 */     boolean val = super.retainAll(c);
/* 584:683 */     if (val) {
/* 585:684 */       fireNotification();
/* 586:    */     }
/* 587:686 */     return val;
/* 588:    */   }
/* 589:    */   
/* 590:    */   public Thread set(int index, Thread element)
/* 591:    */   {
/* 592:690 */     saveState();
/* 593:691 */     Thread obj = (Thread)super.set(index, element);
/* 594:692 */     if (obj != null) {
/* 595:693 */       fireNotification();
/* 596:    */     }
/* 597:695 */     return obj;
/* 598:    */   }
/* 599:    */   
/* 600:    */   public void setElementAt(Thread obj, int index)
/* 601:    */   {
/* 602:699 */     saveState();
/* 603:700 */     super.setElementAt(obj, index);
/* 604:701 */     fireNotification();
/* 605:    */   }
/* 606:    */   
/* 607:    */   public static void main(String[] argv)
/* 608:    */   {
/* 609:709 */     System.out.println("Show differences");
/* 610:710 */     Bundle b = new Bundle();
/* 611:711 */     b.addType("Thing");
/* 612:712 */     b.addType("Animal");
/* 613:713 */     b.addType("Person");
/* 614:714 */     b.addType("Jerk");
/* 615:    */     
/* 616:716 */     Thread t = new Thread();
/* 617:717 */     t.addType("Thing");
/* 618:718 */     t.addType("Person");
/* 619:719 */     t.addType("Jerk");
/* 620:720 */     b.addThread(t);
/* 621:    */     
/* 622:    */ 
/* 623:723 */     Bundle c = new Bundle();
/* 624:724 */     c.addType("Thing");
/* 625:725 */     c.addType("Animal");
/* 626:726 */     c.addType("Person");
/* 627:727 */     c.addType("Jerk");
/* 628:    */     
/* 629:729 */     Thread s = new Thread();
/* 630:730 */     s.addType("Thing");
/* 631:731 */     s.addType("Person");
/* 632:732 */     s.addType("Asshole");
/* 633:733 */     c.addThread(s);
/* 634:734 */     c.prune();
/* 635:735 */     System.out.println(showDifferences(b, c));
/* 636:    */     
/* 637:    */ 
/* 638:    */ 
/* 639:739 */     System.out.println("\n\n---------------Testing Thing/Bundle ownership. (MAF.13.Jan.04)");
/* 640:740 */     Entity.getLogger().setLevel(Level.All);
/* 641:    */     
/* 642:742 */     Entity g1 = new Entity("Mark");
/* 643:743 */     Entity g2 = new Entity("Steph");
/* 644:744 */     Bundle b1 = g1.getBundle();
/* 645:745 */     Bundle b2 = g2.getBundle();
/* 646:746 */     System.out.println("\nThing 1: " + g1);
/* 647:747 */     System.out.println("Bundle 1: " + b1);
/* 648:748 */     System.out.println("Bundle 1 owner id: " + b1.getOwnerThing().getID());
/* 649:749 */     System.out.println("\nThing 2: " + g2);
/* 650:750 */     System.out.println("Bundle 2: " + b2);
/* 651:751 */     System.out.println("Bundle 2 owner id: " + b2.getOwnerThing().getID());
/* 652:    */     
/* 653:753 */     System.out.println("\nSwitching bundle 2 to Thing 1");
/* 654:754 */     g1.setBundle(b2);
/* 655:755 */     System.out.println("\nThing 1: " + g1);
/* 656:    */     
/* 657:757 */     System.out.println("\nPutting bundle 1 on Thing 2");
/* 658:758 */     g2.setBundle(b1);
/* 659:759 */     System.out.println("\nThing 2: " + g2);
/* 660:    */     
/* 661:761 */     System.out.println("\n\n---------------Testing Derivative/Relation. (MAF.14.Jan.04)");
/* 662:    */     
/* 663:763 */     System.out.println("Creating derivative...");
/* 664:764 */     Function d1 = new Function("born", g1);
/* 665:765 */     System.out.println("Creating relation...");
/* 666:766 */     Relation r1 = new Relation("sibling", g1, g2);
/* 667:767 */     System.out.println("Creating sequence...");
/* 668:768 */     Sequence s1 = new Sequence("birth");
/* 669:769 */     s1.addElement(d1);
/* 670:770 */     s1.addElement(r1);
/* 671:    */   }
/* 672:    */   
/* 673:    */   protected static Logger getLogger()
/* 674:    */   {
/* 675:781 */     return Logger.getLogger("things.Bundle");
/* 676:    */   }
/* 677:    */   
/* 678:    */   protected static void finest(Object s)
/* 679:    */   {
/* 680:785 */     Logger.getLogger("things.Bundle").finest("Bundle: " + s);
/* 681:    */   }
/* 682:    */   
/* 683:    */   protected static void finer(Object s)
/* 684:    */   {
/* 685:789 */     Logger.getLogger("things.Bundle").finer("Bundle: " + s);
/* 686:    */   }
/* 687:    */   
/* 688:    */   protected static void fine(Object s)
/* 689:    */   {
/* 690:793 */     Logger.getLogger("things.Bundle").fine("Bundle: " + s);
/* 691:    */   }
/* 692:    */   
/* 693:    */   protected static void config(Object s)
/* 694:    */   {
/* 695:797 */     Logger.getLogger("things.Bundle").config("Bundle: " + s);
/* 696:    */   }
/* 697:    */   
/* 698:    */   protected static void info(Object s)
/* 699:    */   {
/* 700:801 */     Logger.getLogger("things.Bundle").info("Bundle: " + s);
/* 701:    */   }
/* 702:    */   
/* 703:    */   protected static void warning(Object s)
/* 704:    */   {
/* 705:805 */     Logger.getLogger("things.Bundle").warning("Bundle: " + s);
/* 706:    */   }
/* 707:    */   
/* 708:    */   protected static void severe(Object s)
/* 709:    */   {
/* 710:809 */     Logger.getLogger("things.Bundle").severe("Bundle: " + s);
/* 711:    */   }
/* 712:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.Bundle
 * JD-Core Version:    0.7.0.1
 */