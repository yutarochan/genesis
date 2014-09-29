/*   1:    */ package bridge.reps.entities;
/*   2:    */ 
/*   3:    */ import bridge.utils.logging.Logger;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.util.Arrays;
/*   6:    */ import java.util.Collection;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Vector;
/*  10:    */ 
/*  11:    */ public class Thread
/*  12:    */   extends Vector<String>
/*  13:    */ {
/*  14: 24 */   private Bundle ownerBundle = null;
/*  15:    */   
/*  16:    */   public static Thread parse(String s)
/*  17:    */   {
/*  18: 28 */     Thread th = new Thread();
/*  19: 29 */     List<String> types = Arrays.asList(s.split("\\s+"));
/*  20: 30 */     for (String type : types) {
/*  21: 31 */       th.addType(type);
/*  22:    */     }
/*  23: 33 */     return th;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Thread() {}
/*  27:    */   
/*  28:    */   public Thread(Thread t)
/*  29:    */   {
/*  30: 49 */     super(t);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Object clone()
/*  34:    */   {
/*  35: 53 */     return new Thread(this);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Thread copyThread()
/*  39:    */   {
/*  40: 57 */     Thread result = (Thread)clone();
/*  41: 58 */     return result;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Thread(String type)
/*  45:    */   {
/*  46: 65 */     this();
/*  47: 66 */     addType(type);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String getThreadType()
/*  51:    */   {
/*  52: 76 */     if (size() > 0) {
/*  53: 77 */       return (String)get(0);
/*  54:    */     }
/*  55: 80 */     return null;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void addType(String t)
/*  59:    */   {
/*  60: 88 */     if (contains(t))
/*  61:    */     {
/*  62: 89 */       finest("Type " + t + "is contained.");
/*  63: 90 */       remove(t);
/*  64:    */     }
/*  65: 92 */     finest("Adding type: " + t);
/*  66: 93 */     add(t);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void addTypeFront(String t)
/*  70:    */   {
/*  71:100 */     if (contains(t))
/*  72:    */     {
/*  73:101 */       finest("Type " + t + "is contained.");
/*  74:102 */       remove(t);
/*  75:    */     }
/*  76:104 */     finest("Adding type: " + t);
/*  77:105 */     add(0, t);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getType()
/*  81:    */   {
/*  82:110 */     if (size() == 0) {
/*  83:111 */       return "no type";
/*  84:    */     }
/*  85:114 */     return (String)lastElement();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String getSupertype()
/*  89:    */   {
/*  90:125 */     if (size() < 2) {
/*  91:126 */       return null;
/*  92:    */     }
/*  93:129 */     String supertype = (String)get(size() - 2);
/*  94:130 */     return supertype;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Bundle getOwnerBundle()
/*  98:    */   {
/*  99:141 */     return this.ownerBundle;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setOwnerBundle(Bundle b)
/* 103:    */   {
/* 104:155 */     if (this.ownerBundle == b) {
/* 105:156 */       return;
/* 106:    */     }
/* 107:159 */     if ((this.ownerBundle != null) && 
/* 108:160 */       (!this.ownerBundle.removeThread(this)))
/* 109:    */     {
/* 110:161 */       warning("Unable to remove thread from bundle!");
/* 111:162 */       return;
/* 112:    */     }
/* 113:166 */     this.ownerBundle = b;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setOwnerBundleNull()
/* 117:    */   {
/* 118:170 */     this.ownerBundle = null;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean contains(Object object)
/* 122:    */   {
/* 123:    */     try
/* 124:    */     {
/* 125:176 */       if (!(object instanceof String)) {
/* 126:177 */         return false;
/* 127:    */       }
/* 128:179 */       String type = (String)object;
/* 129:180 */       return super.contains(type);
/* 130:    */     }
/* 131:    */     catch (Exception e)
/* 132:    */     {
/* 133:189 */       System.err.println("Blew out in Thread.contains");
/* 134:    */     }
/* 135:193 */     return false;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean contains(Object[] objects)
/* 139:    */   {
/* 140:200 */     if (objects == null) {
/* 141:200 */       return false;
/* 142:    */     }
/* 143:201 */     for (int i = 0; i < objects.length; i++) {
/* 144:202 */       if (contains(objects[i])) {
/* 145:203 */         return true;
/* 146:    */       }
/* 147:    */     }
/* 148:206 */     return false;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public boolean containsAll(Object[] objects)
/* 152:    */   {
/* 153:216 */     for (int i = 0; i < objects.length; i++) {
/* 154:217 */       if (!contains(objects[i])) {
/* 155:218 */         return false;
/* 156:    */       }
/* 157:    */     }
/* 158:221 */     return true;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public String toString()
/* 162:    */   {
/* 163:228 */     return toString(false);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String toString(boolean compact)
/* 167:    */   {
/* 168:232 */     if (compact)
/* 169:    */     {
/* 170:233 */       String s = "";
/* 171:234 */       Iterator<String> i = iterator();
/* 172:235 */       while (i.hasNext())
/* 173:    */       {
/* 174:236 */         s = s + (String)i.next();
/* 175:237 */         if (i.hasNext()) {
/* 176:237 */           s = s + " ";
/* 177:    */         }
/* 178:    */       }
/* 179:239 */       return s;
/* 180:    */     }
/* 181:242 */     return Tags.tagNoLine("thread", Tags.tag(this));
/* 182:    */   }
/* 183:    */   
/* 184:    */   public String getString()
/* 185:    */   {
/* 186:250 */     String result = "";
/* 187:251 */     for (int i = 0; i < size(); i++)
/* 188:    */     {
/* 189:252 */       result = result + (String)elementAt(i);
/* 190:253 */       if (i < size() - 1) {
/* 191:254 */         result = result + " ";
/* 192:    */       }
/* 193:    */     }
/* 194:257 */     return result;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public String asString()
/* 198:    */   {
/* 199:266 */     if (isEmpty()) {
/* 200:267 */       return "<Thread: Empty>";
/* 201:    */     }
/* 202:269 */     String result = "<Thread: " + (String)get(0);
/* 203:270 */     for (int i = 1; i < size(); i++) {
/* 204:271 */       result = result + "---" + (String)get(i);
/* 205:    */     }
/* 206:273 */     return result + ">";
/* 207:    */   }
/* 208:    */   
/* 209:    */   public static Thread extractInstance(String s)
/* 210:    */   {
/* 211:280 */     Thread result = new Thread();
/* 212:281 */     String x = Tags.untagString("thread", s).trim();
/* 213:282 */     while (x.length() > 0)
/* 214:    */     {
/* 215:283 */       int index = x.indexOf(' ');
/* 216:284 */       if (index < 0)
/* 217:    */       {
/* 218:285 */         result.add(x);
/* 219:286 */         break;
/* 220:    */       }
/* 221:289 */       result.add(x.substring(0, index));
/* 222:290 */       x = x.substring(index + 1).trim();
/* 223:    */     }
/* 224:293 */     return result;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public static String showDifferences(Thread f, Thread s)
/* 228:    */   {
/* 229:297 */     String result = null;
/* 230:299 */     if (f.size() != s.size())
/* 231:    */     {
/* 232:300 */       if (result == null) {
/* 233:301 */         result = "";
/* 234:    */       }
/* 235:303 */       result = result + f + " and " + s + "\nare of different sizes\n";
/* 236:    */     }
/* 237:    */     else
/* 238:    */     {
/* 239:307 */       for (int i = 0; i < f.size(); i++)
/* 240:    */       {
/* 241:308 */         String ft = (String)f.get(i);
/* 242:309 */         String st = (String)s.get(i);
/* 243:310 */         if (!ft.equals(st))
/* 244:    */         {
/* 245:311 */           if (result == null) {
/* 246:312 */             result = "";
/* 247:    */           }
/* 248:314 */           result = result + ft + " and " + st + "\nare not equal\n";
/* 249:    */         }
/* 250:    */       }
/* 251:    */     }
/* 252:319 */     return result;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void remove(int i, int j)
/* 256:    */   {
/* 257:324 */     removeRange(i, j);
/* 258:    */   }
/* 259:    */   
/* 260:    */   public static void main(String[] argv)
/* 261:    */   {
/* 262:329 */     Thread t = new Thread();
/* 263:330 */     t.add("Thing");
/* 264:331 */     t.add("Animal");
/* 265:332 */     System.out.println(t);
/* 266:333 */     System.out.println(extractInstance(t.toString()));
/* 267:    */     
/* 268:335 */     System.out.println("\n\n -----------Testing showDifferences()");
/* 269:336 */     Thread s = new Thread();
/* 270:337 */     s.add("Thing");
/* 271:338 */     s.add("Person");
/* 272:339 */     System.out.println(showDifferences(t, s));
/* 273:    */     
/* 274:341 */     System.out.println("\n\n----------Testing bundle membership code (MAF.13.Jan.04");
/* 275:    */     
/* 276:343 */     Bundle b1 = new Bundle();
/* 277:344 */     Bundle b2 = new Bundle();
/* 278:345 */     b1.addType("Mark");
/* 279:346 */     b2.addType("Steph");
/* 280:347 */     Thread t1 = b1.getPrimedThread();
/* 281:348 */     Thread t2 = b2.getPrimedThread();
/* 282:349 */     System.out.println("\nBundle 1: " + b1);
/* 283:350 */     System.out.println("Thread 1: " + t1);
/* 284:351 */     System.out.println("\nBundle 2: " + b2);
/* 285:352 */     System.out.println("Thread 2: " + t2);
/* 286:    */     
/* 287:354 */     b1.addType("was");
/* 288:355 */     b1.addType("here");
/* 289:356 */     b2.addType("was not here");
/* 290:    */     
/* 291:358 */     System.out.println("\nSwitching thread 2 to bundle 1.");
/* 292:359 */     b1.addThread(t2);
/* 293:    */     
/* 294:361 */     System.out.println("\nBundle 1: " + b1);
/* 295:362 */     System.out.println("\nBundle 2: " + b2);
/* 296:    */   }
/* 297:    */   
/* 298:369 */   boolean saveStringForm = false;
/* 299:    */   
/* 300:    */   public void setSaveStringForm(boolean b)
/* 301:    */   {
/* 302:372 */     this.saveStringForm = b;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setSaveStringFormOnDependents(boolean b)
/* 306:    */   {
/* 307:376 */     setSaveStringForm(b);
/* 308:    */   }
/* 309:    */   
/* 310:    */   public boolean getSaveStringForm()
/* 311:    */   {
/* 312:380 */     return this.saveStringForm;
/* 313:    */   }
/* 314:    */   
/* 315:387 */   boolean notification = false;
/* 316:    */   
/* 317:    */   public void setNotification(boolean b)
/* 318:    */   {
/* 319:396 */     this.notification = b;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public void setNotificationOnDependents(boolean b)
/* 323:    */   {
/* 324:407 */     setNotification(b);
/* 325:    */   }
/* 326:    */   
/* 327:410 */   String previousState = null;
/* 328:    */   public static final String LOGGER_GROUP = "things";
/* 329:    */   public static final String LOGGER_INSTANCE = "Thread";
/* 330:    */   public static final String LOGGER = "things.Thread";
/* 331:    */   
/* 332:    */   protected void saveState()
/* 333:    */   {
/* 334:419 */     if (!this.notification) {
/* 335:420 */       return;
/* 336:    */     }
/* 337:422 */     if (this.saveStringForm) {
/* 338:423 */       this.previousState = toString();
/* 339:    */     }
/* 340:425 */     if (getOwnerBundle() != null) {
/* 341:426 */       getOwnerBundle().saveState();
/* 342:    */     }
/* 343:    */   }
/* 344:    */   
/* 345:    */   protected void fireNotification()
/* 346:    */   {
/* 347:437 */     if (!this.notification) {
/* 348:438 */       return;
/* 349:    */     }
/* 350:440 */     if (getOwnerBundle() != null) {
/* 351:441 */       if (this.saveStringForm) {
/* 352:442 */         getOwnerBundle().threadModified(this, this.previousState, toString());
/* 353:    */       } else {
/* 354:445 */         getOwnerBundle().threadModified(this, null, null);
/* 355:    */       }
/* 356:    */     }
/* 357:    */   }
/* 358:    */   
/* 359:    */   public void add(int index, String element)
/* 360:    */   {
/* 361:459 */     super.add(index, element);
/* 362:    */   }
/* 363:    */   
/* 364:    */   public boolean add(String o)
/* 365:    */   {
/* 366:463 */     saveState();
/* 367:464 */     boolean val = super.add(o);
/* 368:465 */     if (val) {
/* 369:466 */       fireNotification();
/* 370:    */     }
/* 371:468 */     return val;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public boolean addAll(Collection<? extends String> c)
/* 375:    */   {
/* 376:472 */     saveState();
/* 377:473 */     boolean val = super.addAll(c);
/* 378:474 */     if (val) {
/* 379:475 */       fireNotification();
/* 380:    */     }
/* 381:477 */     return val;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public boolean addAll(int index, Collection<? extends String> c)
/* 385:    */   {
/* 386:481 */     saveState();
/* 387:482 */     boolean val = super.addAll(index, c);
/* 388:483 */     if (val) {
/* 389:484 */       fireNotification();
/* 390:    */     }
/* 391:486 */     return val;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public void addElement(String obj)
/* 395:    */   {
/* 396:490 */     saveState();
/* 397:491 */     super.addElement(obj);
/* 398:492 */     fireNotification();
/* 399:    */   }
/* 400:    */   
/* 401:    */   public void clear()
/* 402:    */   {
/* 403:499 */     super.clear();
/* 404:    */   }
/* 405:    */   
/* 406:    */   public void insertElementAt(String obj, int index)
/* 407:    */   {
/* 408:503 */     saveState();
/* 409:504 */     super.insertElementAt(obj, index);
/* 410:505 */     fireNotification();
/* 411:    */   }
/* 412:    */   
/* 413:    */   public String remove(int index)
/* 414:    */   {
/* 415:509 */     saveState();
/* 416:510 */     String obj = (String)super.remove(index);
/* 417:511 */     if (obj != null) {
/* 418:512 */       fireNotification();
/* 419:    */     }
/* 420:514 */     return obj;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public boolean remove(Object o)
/* 424:    */   {
/* 425:521 */     return super.remove(o);
/* 426:    */   }
/* 427:    */   
/* 428:    */   public boolean removeAll(Collection<?> c)
/* 429:    */   {
/* 430:525 */     saveState();
/* 431:526 */     boolean val = super.removeAll(c);
/* 432:527 */     if (val) {
/* 433:528 */       fireNotification();
/* 434:    */     }
/* 435:530 */     return val;
/* 436:    */   }
/* 437:    */   
/* 438:    */   public void removeAllElements()
/* 439:    */   {
/* 440:534 */     saveState();
/* 441:535 */     super.removeAllElements();
/* 442:536 */     fireNotification();
/* 443:    */   }
/* 444:    */   
/* 445:    */   public boolean removeElement(Object obj)
/* 446:    */   {
/* 447:543 */     return super.removeElement(obj);
/* 448:    */   }
/* 449:    */   
/* 450:    */   public void removeElementAt(int index)
/* 451:    */   {
/* 452:547 */     saveState();
/* 453:548 */     super.removeElementAt(index);
/* 454:549 */     fireNotification();
/* 455:    */   }
/* 456:    */   
/* 457:    */   protected void removeRange(int fromIndex, int toIndex)
/* 458:    */   {
/* 459:553 */     saveState();
/* 460:554 */     super.removeRange(fromIndex, toIndex);
/* 461:555 */     fireNotification();
/* 462:    */   }
/* 463:    */   
/* 464:    */   public boolean retainAll(Collection<?> c)
/* 465:    */   {
/* 466:559 */     saveState();
/* 467:560 */     boolean val = super.retainAll(c);
/* 468:561 */     if (val) {
/* 469:562 */       fireNotification();
/* 470:    */     }
/* 471:564 */     return val;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public String set(int index, String element)
/* 475:    */   {
/* 476:568 */     saveState();
/* 477:569 */     String obj = (String)super.set(index, element);
/* 478:570 */     if (obj != null) {
/* 479:571 */       fireNotification();
/* 480:    */     }
/* 481:573 */     return obj;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public void setElementAt(String obj, int index)
/* 485:    */   {
/* 486:577 */     saveState();
/* 487:578 */     super.setElementAt(obj, index);
/* 488:579 */     fireNotification();
/* 489:    */   }
/* 490:    */   
/* 491:    */   public static Logger getLogger()
/* 492:    */   {
/* 493:597 */     return Logger.getLogger("things.Thread");
/* 494:    */   }
/* 495:    */   
/* 496:    */   protected static void finest(Object s)
/* 497:    */   {
/* 498:601 */     Logger.getLogger("things.Thread").finest("Thread: " + s);
/* 499:    */   }
/* 500:    */   
/* 501:    */   protected static void finer(Object s)
/* 502:    */   {
/* 503:605 */     Logger.getLogger("things.Thread").finer("Thread: " + s);
/* 504:    */   }
/* 505:    */   
/* 506:    */   protected static void fine(Object s)
/* 507:    */   {
/* 508:609 */     Logger.getLogger("things.Thread").fine("Thread: " + s);
/* 509:    */   }
/* 510:    */   
/* 511:    */   protected static void config(Object s)
/* 512:    */   {
/* 513:613 */     Logger.getLogger("things.Thread").config("Thread: " + s);
/* 514:    */   }
/* 515:    */   
/* 516:    */   protected static void info(Object s)
/* 517:    */   {
/* 518:617 */     Logger.getLogger("things.Thread").info("Thread: " + s);
/* 519:    */   }
/* 520:    */   
/* 521:    */   protected static void warning(Object s)
/* 522:    */   {
/* 523:621 */     Logger.getLogger("things.Thread").warning("Thread: " + s);
/* 524:    */   }
/* 525:    */   
/* 526:    */   protected static void severe(Object s)
/* 527:    */   {
/* 528:625 */     Logger.getLogger("things.Thread").severe("Thread: " + s);
/* 529:    */   }
/* 530:    */   
/* 531:    */   public boolean equals(Object object)
/* 532:    */   {
/* 533:629 */     if (!(object instanceof Thread)) {
/* 534:630 */       return false;
/* 535:    */     }
/* 536:632 */     Thread t = (Thread)object;
/* 537:633 */     if (size() != t.size()) {
/* 538:634 */       return false;
/* 539:    */     }
/* 540:636 */     for (int i = 0; i < t.size(); i++) {
/* 541:637 */       if (!((String)get(i)).equalsIgnoreCase((String)t.get(i))) {
/* 542:638 */         return false;
/* 543:    */       }
/* 544:    */     }
/* 545:641 */     return true;
/* 546:    */   }
/* 547:    */   
/* 548:    */   public static Vector<String> getClassPairsFromString(String s)
/* 549:    */   {
/* 550:645 */     Vector<String> result = new Vector();
/* 551:    */     
/* 552:647 */     s = Tags.untagString("bundle", s);
/* 553:    */     
/* 554:649 */     int i = 1;
/* 555:    */     String t;
/* 556:653 */     while ((t = Tags.untagString("thread", s, i)) != null)
/* 557:    */     {
/* 558:655 */       int begin = 0;
/* 559:656 */       String t = t.trim();
/* 560:658 */       while (t.indexOf("features") < 0)
/* 561:    */       {
/* 562:661 */         if (t.indexOf("tracers") >= 0) {
/* 563:    */           break;
/* 564:    */         }
/* 565:664 */         int middle = t.indexOf(' ', begin);
/* 566:    */         String lower;
/* 567:    */         int end;
/* 568:    */         String upper;
/* 569:    */         String lower;
/* 570:665 */         if (middle >= 0)
/* 571:    */         {
/* 572:666 */           int end = t.indexOf(' ', middle + 1);
/* 573:    */           String lower;
/* 574:667 */           if (end >= 0)
/* 575:    */           {
/* 576:668 */             String upper = t.substring(begin, middle).trim();
/* 577:669 */             lower = t.substring(middle + 1, end).trim();
/* 578:    */           }
/* 579:    */           else
/* 580:    */           {
/* 581:672 */             end = t.length();
/* 582:673 */             String upper = t.substring(begin, middle).trim();
/* 583:674 */             lower = t.substring(middle + 1, end).trim();
/* 584:    */           }
/* 585:    */         }
/* 586:    */         else
/* 587:    */         {
/* 588:678 */           middle = t.length();
/* 589:679 */           end = t.length();
/* 590:680 */           upper = "";
/* 591:681 */           lower = null;
/* 592:    */         }
/* 593:685 */         if (upper != "") {
/* 594:686 */           result.add(ClassPair.makeClassPair(upper, lower));
/* 595:    */         }
/* 596:689 */         if ((end == t.length()) && (middle == t.length())) {
/* 597:    */           break;
/* 598:    */         }
/* 599:692 */         begin = middle + 1;
/* 600:    */       }
/* 601:694 */       i++;
/* 602:    */     }
/* 603:697 */     return result;
/* 604:    */   }
/* 605:    */   
/* 606:    */   public int indexOf(String s)
/* 607:    */   {
/* 608:701 */     if (s == null) {
/* 609:702 */       return -1;
/* 610:    */     }
/* 611:704 */     for (int i = 0; i < size(); i++)
/* 612:    */     {
/* 613:705 */       String x = (String)get(i);
/* 614:706 */       if (s.equals(x)) {
/* 615:707 */         return i;
/* 616:    */       }
/* 617:    */     }
/* 618:710 */     return -1;
/* 619:    */   }
/* 620:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.Thread
 * JD-Core Version:    0.7.0.1
 */