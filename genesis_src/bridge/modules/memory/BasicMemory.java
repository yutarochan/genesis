/*    1:     */ package bridge.modules.memory;
/*    2:     */ 
/*    3:     */ import bridge.infrastructure.wires.Wire;
/*    4:     */ import bridge.reps.entities.Bundle;
/*    5:     */ import bridge.reps.entities.Entity;
/*    6:     */ import bridge.reps.entities.Function;
/*    7:     */ import bridge.reps.entities.NameGenerator;
/*    8:     */ import bridge.reps.entities.Relation;
/*    9:     */ import bridge.reps.entities.Sequence;
/*   10:     */ import bridge.reps.entities.Thread;
/*   11:     */ import bridge.utils.CollectionUtils;
/*   12:     */ import bridge.utils.logging.Level;
/*   13:     */ import bridge.utils.logging.Logger;
/*   14:     */ import java.io.FileInputStream;
/*   15:     */ import java.io.FileOutputStream;
/*   16:     */ import java.io.ObjectInputStream;
/*   17:     */ import java.io.ObjectOutputStream;
/*   18:     */ import java.io.PrintStream;
/*   19:     */ import java.io.Serializable;
/*   20:     */ import java.util.ArrayList;
/*   21:     */ import java.util.Collection;
/*   22:     */ import java.util.HashMap;
/*   23:     */ import java.util.Hashtable;
/*   24:     */ import java.util.Iterator;
/*   25:     */ import java.util.List;
/*   26:     */ import java.util.Observable;
/*   27:     */ import java.util.Observer;
/*   28:     */ import java.util.Set;
/*   29:     */ import java.util.Vector;
/*   30:     */ 
/*   31:     */ public class BasicMemory
/*   32:     */   extends Observable
/*   33:     */   implements Serializable, Observer, MemoryForget
/*   34:     */ {
/*   35:     */   private static BasicMemory staticMemory;
/*   36:  48 */   private HashMap<String, ArrayList<Entity>> conceptHash = new HashMap();
/*   37:     */   
/*   38:     */   public static BasicMemory getStaticMemory()
/*   39:     */   {
/*   40:  51 */     if (staticMemory == null)
/*   41:     */     {
/*   42:  52 */       staticMemory = new BasicMemory();
/*   43:  53 */       warning("Creating static memory: " + staticMemory);
/*   44:     */     }
/*   45:  55 */     return staticMemory;
/*   46:     */   }
/*   47:     */   
/*   48:     */   public BasicMemory() {}
/*   49:     */   
/*   50:     */   public void setInput(Object o, Object port)
/*   51:     */   {
/*   52:  72 */     if (!(o instanceof Entity)) {
/*   53:  73 */       return;
/*   54:     */     }
/*   55:  75 */     if (Wire.INPUT.equals(port))
/*   56:     */     {
/*   57:  76 */       store((Entity)o);
/*   58:     */     }
/*   59:  78 */     else if (RECURSIVE.equals(port))
/*   60:     */     {
/*   61:  79 */       storeRecursively((Entity)o);
/*   62:     */     }
/*   63:  81 */     else if (STORE.equals(port))
/*   64:     */     {
/*   65:  82 */       if (!(o instanceof String))
/*   66:     */       {
/*   67:  83 */         Logger.warning(this, "Store port on memory expected a file name");
/*   68:  84 */         return;
/*   69:     */       }
/*   70:  86 */       storeInFile((String)o);
/*   71:     */     }
/*   72:  88 */     else if (RESTORE.equals(port))
/*   73:     */     {
/*   74:  89 */       if (!(o instanceof String))
/*   75:     */       {
/*   76:  90 */         Logger.warning(this, "Store port on memory expected a file name");
/*   77:  91 */         return;
/*   78:     */       }
/*   79:  93 */       restoreFromFile((String)o);
/*   80:     */     }
/*   81:     */   }
/*   82:     */   
/*   83:     */   public BasicMemory(boolean notification)
/*   84:     */   {
/*   85:  98 */     System.out.println("Hello basic memory with argument");
/*   86:  99 */     setNotification(notification);
/*   87:     */   }
/*   88:     */   
/*   89: 106 */   public static final Object SAVE_STATE_THING = new Object();
/*   90: 109 */   public static final Object FIRE_NOTIFICATION_THING = new Object();
/*   91: 115 */   public static final Object SAVE_STATE = new Object();
/*   92: 121 */   public static final Object FIRE_NOTIFICATION = new Object();
/*   93:     */   public static final int ACTION_STORE = 0;
/*   94:     */   public static final int ACTION_FORGET = 0;
/*   95: 127 */   Vector<Entity> things = new Vector();
/*   96: 129 */   Hashtable instances = new Hashtable();
/*   97: 131 */   public static Object RECURSIVE = "Recursive";
/*   98: 133 */   public static Object STORE = "Store in file";
/*   99: 135 */   public static Object RESTORE = "Restore from file";
/*  100:     */   Entity lastChanged;
/*  101:     */   
/*  102:     */   public Object getOutput(Object o)
/*  103:     */   {
/*  104: 141 */     return null;
/*  105:     */   }
/*  106:     */   
/*  107:     */   public Vector<Entity> getThings()
/*  108:     */   {
/*  109: 148 */     return this.things;
/*  110:     */   }
/*  111:     */   
/*  112:     */   public Vector getThings(String type)
/*  113:     */   {
/*  114: 155 */     Vector result = new Vector();
/*  115: 156 */     Vector fodder = getThings();
/*  116: 157 */     for (int i = 0; i < fodder.size(); i++)
/*  117:     */     {
/*  118: 158 */       Entity thing = (Entity)fodder.elementAt(i);
/*  119: 159 */       if (thing.isA(type)) {
/*  120: 160 */         result.add(thing);
/*  121:     */       }
/*  122:     */     }
/*  123: 163 */     return result;
/*  124:     */   }
/*  125:     */   
/*  126:     */   public Vector getThingsOfSupertype(String supertype)
/*  127:     */   {
/*  128: 173 */     Vector result = new Vector();
/*  129:     */     
/*  130: 175 */     Vector fodder = getThings();
/*  131: 176 */     for (int i = 0; i < fodder.size(); i++)
/*  132:     */     {
/*  133: 177 */       Entity thing = (Entity)fodder.elementAt(i);
/*  134: 178 */       if (thing.getSupertype().equals(supertype)) {
/*  135: 179 */         result.add(thing);
/*  136:     */       }
/*  137:     */     }
/*  138: 183 */     info("Result is: " + result);
/*  139: 184 */     return result;
/*  140:     */   }
/*  141:     */   
/*  142:     */   public Hashtable getInstances()
/*  143:     */   {
/*  144: 191 */     return this.instances;
/*  145:     */   }
/*  146:     */   
/*  147:     */   public void setThings(Vector t)
/*  148:     */   {
/*  149: 198 */     saveState();
/*  150: 199 */     this.things = t;
/*  151: 200 */     fireNotification();
/*  152:     */   }
/*  153:     */   
/*  154:     */   public void setInstances(Hashtable t)
/*  155:     */   {
/*  156: 207 */     saveState();
/*  157: 208 */     this.instances = t;
/*  158: 209 */     fireNotification();
/*  159:     */   }
/*  160:     */   
/*  161:     */   public Vector getState()
/*  162:     */   {
/*  163: 216 */     Vector v = new Vector();
/*  164: 217 */     v.add(getInstances());
/*  165: 218 */     v.add(getThings());
/*  166: 219 */     return v;
/*  167:     */   }
/*  168:     */   
/*  169:     */   public void setState(Vector v)
/*  170:     */   {
/*  171: 226 */     if (v == null) {
/*  172: 227 */       return;
/*  173:     */     }
/*  174: 229 */     saveState();
/*  175:     */     try
/*  176:     */     {
/*  177: 231 */       setInstances((Hashtable)v.get(0));
/*  178: 232 */       setThings((Vector)v.get(1));
/*  179:     */     }
/*  180:     */     catch (ClassCastException e)
/*  181:     */     {
/*  182: 235 */       warning("State not compatible.");
/*  183:     */     }
/*  184: 237 */     fireNotification();
/*  185:     */   }
/*  186:     */   
/*  187:     */   public void thingModified(Entity t, String oldState, String newState)
/*  188:     */   {
/*  189: 241 */     if (!this.notification) {
/*  190: 242 */       return;
/*  191:     */     }
/*  192: 246 */     fireNotification();
/*  193:     */   }
/*  194:     */   
/*  195:     */   public void update(Observable o, Object arg)
/*  196:     */   {
/*  197: 254 */     this.lastChanged = ((Entity)o);
/*  198: 255 */     if (arg == Entity.SAVE_STATE) {
/*  199: 256 */       changed(SAVE_STATE_THING);
/*  200: 258 */     } else if (arg == Entity.FIRE_NOTIFICATION) {
/*  201: 259 */       changed(FIRE_NOTIFICATION_THING);
/*  202:     */     }
/*  203:     */   }
/*  204:     */   
/*  205:     */   public void changed()
/*  206:     */   {
/*  207: 265 */     changed(null);
/*  208:     */   }
/*  209:     */   
/*  210:     */   public void changed(Object o)
/*  211:     */   {
/*  212: 269 */     setChanged();
/*  213: 270 */     notifyObservers(o);
/*  214:     */   }
/*  215:     */   
/*  216: 276 */   boolean notification = false;
/*  217:     */   
/*  218:     */   public void setNotification(boolean b)
/*  219:     */   {
/*  220: 285 */     this.notification = b;
/*  221:     */   }
/*  222:     */   
/*  223:     */   public void setNotificationOnDependents(boolean b)
/*  224:     */   {
/*  225: 296 */     setNotification(b);
/*  226: 297 */     Vector v = getThings();
/*  227: 298 */     for (int i = 0; i < v.size(); i++) {
/*  228: 299 */       ((Entity)v.get(i)).setNotificationOnDependents(b);
/*  229:     */     }
/*  230:     */   }
/*  231:     */   
/*  232:     */   public boolean getNotification()
/*  233:     */   {
/*  234: 304 */     return this.notification;
/*  235:     */   }
/*  236:     */   
/*  237:     */   public void saveState()
/*  238:     */   {
/*  239: 314 */     if (this.notification) {
/*  240: 315 */       changed(SAVE_STATE);
/*  241:     */     }
/*  242:     */   }
/*  243:     */   
/*  244:     */   protected void fireNotification()
/*  245:     */   {
/*  246: 320 */     if (this.notification) {
/*  247: 321 */       changed(FIRE_NOTIFICATION);
/*  248:     */     }
/*  249:     */   }
/*  250:     */   
/*  251:     */   protected void fireNotificationStore(Entity t)
/*  252:     */   {
/*  253: 326 */     if (this.notification)
/*  254:     */     {
/*  255: 327 */       fireNotification();
/*  256: 328 */       changed(new ActionObject(t, 0));
/*  257:     */     }
/*  258:     */   }
/*  259:     */   
/*  260:     */   protected void fireNotificationForget(Entity t)
/*  261:     */   {
/*  262: 333 */     if (this.notification)
/*  263:     */     {
/*  264: 334 */       fireNotification();
/*  265: 335 */       changed(new ActionObject(t, 0));
/*  266:     */     }
/*  267:     */   }
/*  268:     */   
/*  269:     */   public boolean store(Entity t)
/*  270:     */   {
/*  271: 343 */     if (getInstances().get(t.getNameSuffix()) == null)
/*  272:     */     {
/*  273: 345 */       saveState();
/*  274:     */       
/*  275: 347 */       t.setNotificationOnDependents(getNotification());
/*  276: 348 */       t.addObserver(this);
/*  277: 349 */       getInstances().put(t.getNameSuffix(), t);
/*  278: 350 */       getThings().add(t);
/*  279:     */       
/*  280: 352 */       fireNotificationStore(t);
/*  281: 353 */       return true;
/*  282:     */     }
/*  283: 356 */     fine("Failed to store thing: " + t.getName());
/*  284: 357 */     return false;
/*  285:     */   }
/*  286:     */   
/*  287:     */   public void storeRecursively(Entity t)
/*  288:     */   {
/*  289: 365 */     fine("Storing thing recursively: " + t.getName());
/*  290: 366 */     Vector superThings = new Vector();
/*  291: 367 */     superThings.addAll(t.getSubjectOf());
/*  292: 368 */     superThings.addAll(t.getObjectOf());
/*  293: 369 */     superThings.addAll(t.getElementOf());
/*  294: 371 */     for (int i = 0; i < superThings.size(); i++)
/*  295:     */     {
/*  296: 372 */       Entity superThing = (Entity)superThings.get(i);
/*  297: 373 */       storeRecursively(superThing);
/*  298:     */     }
/*  299: 376 */     if (!store(t)) {
/*  300: 377 */       return;
/*  301:     */     }
/*  302: 380 */     if (t.functionP())
/*  303:     */     {
/*  304: 381 */       storeRecursively(t.getSubject());
/*  305:     */     }
/*  306: 383 */     else if (t.relationP())
/*  307:     */     {
/*  308: 384 */       storeRecursively(t.getSubject());
/*  309: 385 */       storeRecursively(t.getObject());
/*  310:     */     }
/*  311: 387 */     else if (t.sequenceP())
/*  312:     */     {
/*  313: 388 */       Vector v = t.getElements();
/*  314: 389 */       for (int i = 0; i < v.size(); i++)
/*  315:     */       {
/*  316: 390 */         Entity element = (Entity)v.get(i);
/*  317: 391 */         storeRecursively(element);
/*  318:     */       }
/*  319:     */     }
/*  320:     */   }
/*  321:     */   
/*  322:     */   public boolean forget(Entity t)
/*  323:     */   {
/*  324: 400 */     if (isForgettable(t))
/*  325:     */     {
/*  326: 401 */       saveState();
/*  327:     */       
/*  328: 403 */       getInstances().remove(t.getNameSuffix());
/*  329:     */       
/*  330: 405 */       getThings().remove(t);
/*  331: 406 */       t.deleteObserver(this);
/*  332: 407 */       fireNotificationForget(t);
/*  333: 408 */       return true;
/*  334:     */     }
/*  335: 411 */     warning("Thing could not be forgotten:" + t.getName());
/*  336: 412 */     return false;
/*  337:     */   }
/*  338:     */   
/*  339:     */   public boolean forgetRecursively(Entity t)
/*  340:     */   {
/*  341: 420 */     if (t.functionP())
/*  342:     */     {
/*  343: 421 */       forgetRecursively(t.getSubject());
/*  344:     */     }
/*  345: 423 */     else if (t.relationP())
/*  346:     */     {
/*  347: 424 */       forgetRecursively(t.getSubject());
/*  348: 425 */       forgetRecursively(t.getObject());
/*  349:     */     }
/*  350: 427 */     else if (t.sequenceP())
/*  351:     */     {
/*  352: 428 */       Vector elements = new Vector();
/*  353: 429 */       elements.addAll(t.getElements());
/*  354: 430 */       for (int i = 0; i < elements.size(); i++) {
/*  355: 431 */         forgetRecursively((Entity)elements.get(i));
/*  356:     */       }
/*  357:     */     }
/*  358: 434 */     return forget(t);
/*  359:     */   }
/*  360:     */   
/*  361: 437 */   boolean fascistForgetting = true;
/*  362:     */   public static final String LOGGER_GROUP = "memory";
/*  363:     */   public static final String LOGGER_INSTANCE = "BasicMemory";
/*  364:     */   public static final String LOGGER = "memory.BasicMemory";
/*  365:     */   
/*  366:     */   public void setFascistForgetting(boolean b)
/*  367:     */   {
/*  368: 440 */     this.fascistForgetting = b;
/*  369:     */   }
/*  370:     */   
/*  371:     */   public boolean isForgettable(Entity thing)
/*  372:     */   {
/*  373: 452 */     if (!this.fascistForgetting) {
/*  374: 453 */       return true;
/*  375:     */     }
/*  376: 457 */     for (Iterator i = thing.getParents().iterator(); i.hasNext();)
/*  377:     */     {
/*  378: 458 */       Entity parent = (Entity)i.next();
/*  379: 459 */       if (getInstances().keySet().contains(parent.getNameSuffix())) {
/*  380: 460 */         return false;
/*  381:     */       }
/*  382:     */     }
/*  383: 464 */     return true;
/*  384:     */   }
/*  385:     */   
/*  386:     */   public boolean findThingRecursively(Entity g, Entity t)
/*  387:     */   {
/*  388: 480 */     finest("Looking in and at " + g.getName() + " for " + t.getName());
/*  389: 482 */     if (g == t)
/*  390:     */     {
/*  391: 483 */       finest("Found thing!");
/*  392: 484 */       return true;
/*  393:     */     }
/*  394: 487 */     if (g.getClass() == Function.class) {
/*  395: 488 */       return findThingRecursively(((Function)g).getSubject(), t);
/*  396:     */     }
/*  397: 490 */     if (g.getClass() == Relation.class)
/*  398:     */     {
/*  399: 491 */       Relation r = (Relation)g;
/*  400: 492 */       return (findThingRecursively(r.getSubject(), t)) || (findThingRecursively(r.getObject(), t));
/*  401:     */     }
/*  402: 494 */     if (g.getClass() == Sequence.class)
/*  403:     */     {
/*  404: 495 */       Vector elements = ((Sequence)g).getElements();
/*  405: 496 */       for (int i = 0; i < elements.size(); i++) {
/*  406: 497 */         if (findThingRecursively((Entity)elements.get(i), t)) {
/*  407: 498 */           return true;
/*  408:     */         }
/*  409:     */       }
/*  410:     */     }
/*  411: 503 */     return false;
/*  412:     */   }
/*  413:     */   
/*  414:     */   public Entity findThingInMemory(int id)
/*  415:     */   {
/*  416: 513 */     String suffix = "-" + id;
/*  417: 514 */     return (Entity)getInstances().get(suffix);
/*  418:     */   }
/*  419:     */   
/*  420:     */   public Entity findThingInMemory(Entity thing)
/*  421:     */   {
/*  422: 522 */     String suffix = NameGenerator.extractSuffixFromName(thing.getType());
/*  423: 523 */     if (suffix != null)
/*  424:     */     {
/*  425: 524 */       Object o = getInstances().get(suffix);
/*  426: 526 */       if (o != null) {
/*  427: 527 */         return (Entity)o;
/*  428:     */       }
/*  429:     */     }
/*  430: 530 */     return null;
/*  431:     */   }
/*  432:     */   
/*  433:     */   public Entity findThingInMemory(String name)
/*  434:     */   {
/*  435: 537 */     String suffix = extractSuffixFromName(name);
/*  436: 538 */     if (suffix != null)
/*  437:     */     {
/*  438: 539 */       Object o = getInstances().get(suffix);
/*  439: 541 */       if (o != null)
/*  440:     */       {
/*  441: 542 */         fine("Found in memory: " + name);
/*  442: 543 */         return (Entity)o;
/*  443:     */       }
/*  444: 546 */       warning("Failed to find in memory: " + name);
/*  445:     */     }
/*  446: 549 */     return null;
/*  447:     */   }
/*  448:     */   
/*  449:     */   public Vector getThingsOfType(String type, Vector things)
/*  450:     */   {
/*  451: 559 */     Vector result = new Vector();
/*  452: 561 */     for (int i = 0; i < things.size(); i++)
/*  453:     */     {
/*  454: 562 */       Entity thing = (Entity)things.elementAt(i);
/*  455: 563 */       if (thing.getType().equals(type)) {
/*  456: 564 */         result.add(thing);
/*  457:     */       }
/*  458:     */     }
/*  459: 567 */     return result;
/*  460:     */   }
/*  461:     */   
/*  462:     */   public List getThingsOfSupertype(String supertype, List things)
/*  463:     */   {
/*  464: 577 */     List result = new Vector();
/*  465: 580 */     for (int i = 0; i < things.size(); i++)
/*  466:     */     {
/*  467: 581 */       Entity thing = (Entity)things.get(i);
/*  468: 582 */       String suptype = thing.getSupertype();
/*  469: 583 */       if ((suptype != null) && 
/*  470: 584 */         (suptype.equals(supertype))) {
/*  471: 585 */         result.add(thing);
/*  472:     */       }
/*  473:     */     }
/*  474: 589 */     return result;
/*  475:     */   }
/*  476:     */   
/*  477:     */   public Entity getReferenceX(Entity thing, String butNot)
/*  478:     */   {
/*  479: 593 */     return findMatchingThingX(thing, butNot);
/*  480:     */   }
/*  481:     */   
/*  482:     */   public Entity findMatchingThingX(Entity thing, String butNot)
/*  483:     */   {
/*  484: 603 */     fine("Looking for match to " + thing.getName());
/*  485: 606 */     if (!thing.entityP())
/*  486:     */     {
/*  487: 607 */       fine("...but not thing ");
/*  488: 608 */       return null;
/*  489:     */     }
/*  490: 616 */     Collection theseTypes = thing.getAllTypesForFindMatchingThing();
/*  491: 617 */     Vector possibilities = fetchThings(thing.getType());
/*  492: 618 */     possibilities.remove(this);
/*  493: 619 */     if (possibilities.isEmpty()) {
/*  494: 620 */       return null;
/*  495:     */     }
/*  496: 622 */     int matches = 0;
/*  497: 623 */     Entity result = null;
/*  498: 624 */     for (int i = 0; i < possibilities.size(); i++)
/*  499:     */     {
/*  500: 625 */       Entity possibility = (Entity)possibilities.elementAt(i);
/*  501: 626 */       Collection thoseTypes = possibility.getAllTypesForFindMatchingThing();
/*  502: 627 */       Collection intersection = CollectionUtils.intersection(theseTypes, thoseTypes);
/*  503: 628 */       Collection difference = CollectionUtils.difference(theseTypes, thoseTypes);
/*  504: 629 */       int newMatches = intersection.size();
/*  505: 630 */       if ((butNot == null) || (!possibility.isA(butNot))) {
/*  506: 635 */         if (!possibility.isA("clone")) {
/*  507: 637 */           if ((!(possibility instanceof Function)) && (!(possibility instanceof Sequence))) {
/*  508: 642 */             if (difference.isEmpty()) {
/*  509: 646 */               if (newMatches > matches)
/*  510:     */               {
/*  511: 647 */                 result = possibility;
/*  512: 648 */                 matches = newMatches;
/*  513:     */               }
/*  514: 651 */               else if (newMatches == matches)
/*  515:     */               {
/*  516: 655 */                 result = possibility;
/*  517: 656 */                 matches = newMatches;
/*  518:     */               }
/*  519:     */             }
/*  520:     */           }
/*  521:     */         }
/*  522:     */       }
/*  523:     */     }
/*  524: 660 */     if (result != null)
/*  525:     */     {
/*  526: 661 */       fine("Looks like " + thing.getName() + " best matches " + result.getName());
/*  527: 662 */       fine("Current: " + this);
/*  528: 663 */       fine("Antecedant: " + result);
/*  529:     */     }
/*  530: 665 */     return result;
/*  531:     */   }
/*  532:     */   
/*  533:     */   public void extendVia(Entity thing, String via)
/*  534:     */   {
/*  535: 682 */     Thread thread = thing.getPrimedThread();
/*  536: 685 */     if (thread.size() == 0) {
/*  537: 686 */       return;
/*  538:     */     }
/*  539: 691 */     if (!((String)thread.elementAt(0)).equalsIgnoreCase("thing")) {
/*  540: 692 */       return;
/*  541:     */     }
/*  542: 696 */     if (thread.size() == 1) {
/*  543: 697 */       return;
/*  544:     */     }
/*  545: 701 */     String hook = null;
/*  546: 706 */     if (thread.size() == 1) {
/*  547: 707 */       hook = (String)thread.elementAt(0);
/*  548:     */     } else {
/*  549: 712 */       hook = (String)thread.elementAt(1);
/*  550:     */     }
/*  551: 714 */     Logger.getLogger("extender").fine("Hook is " + hook);
/*  552: 715 */     Logger.getLogger("extender").fine("Total thing instances: " + getThings().size());
/*  553:     */     
/*  554:     */ 
/*  555:     */ 
/*  556:     */ 
/*  557:     */ 
/*  558: 721 */     Vector goodThings = fetchThings(hook, via);
/*  559:     */     
/*  560: 723 */     Logger.getLogger("extender").fine("Thing instances belonging to via class: " + goodThings.size());
/*  561: 724 */     Vector threads = new Vector();
/*  562: 726 */     for (int i = 0; i < goodThings.size(); i++)
/*  563:     */     {
/*  564: 727 */       Entity goodThing = (Entity)goodThings.get(i);
/*  565: 728 */       threads.addAll(goodThing.getBundle());
/*  566:     */     }
/*  567: 735 */     Logger.getLogger("extender").fine("Total threads: " + threads.size());
/*  568: 736 */     Vector goodThreads = new Vector();
/*  569: 737 */     for (int i = 0; i < threads.size(); i++)
/*  570:     */     {
/*  571: 738 */       Thread candidateThread = (Thread)threads.elementAt(i);
/*  572: 739 */       Logger.getLogger("extender").fine("Candidate: " + candidateThread);
/*  573: 741 */       if ((!candidateThread.isEmpty()) && 
/*  574: 742 */         (((String)candidateThread.firstElement()).equalsIgnoreCase("thing")) && (candidateThread.contains(via))) {
/*  575: 743 */         goodThreads.add(candidateThread);
/*  576:     */       }
/*  577:     */     }
/*  578: 748 */     Logger.getLogger("extender").fine("Threads containing via class: " + threads.size());
/*  579: 753 */     for (int i = 0; i < goodThreads.size(); i++)
/*  580:     */     {
/*  581: 754 */       Thread extendingThread = (Thread)goodThreads.elementAt(i);
/*  582:     */       
/*  583:     */ 
/*  584: 757 */       Thread newThread = thread.copyThread();
/*  585:     */       
/*  586:     */ 
/*  587:     */ 
/*  588: 761 */       int insertionIndex = 0;
/*  589: 762 */       if (((String)newThread.elementAt(0)).equalsIgnoreCase("thing")) {
/*  590: 763 */         insertionIndex = 1;
/*  591:     */       }
/*  592: 768 */       int sourceIndex = 0;
/*  593: 769 */       if (((String)extendingThread.elementAt(0)).equalsIgnoreCase("thing")) {
/*  594: 770 */         sourceIndex = 1;
/*  595:     */       }
/*  596: 774 */       if (extendingThread.size() - 2 >= sourceIndex)
/*  597:     */       {
/*  598: 775 */         for (int j = extendingThread.size() - 2; j >= sourceIndex; j--) {
/*  599: 776 */           newThread.add(insertionIndex, (String)extendingThread.elementAt(j));
/*  600:     */         }
/*  601: 778 */         thing.getBundle().addThread(newThread);
/*  602:     */       }
/*  603:     */     }
/*  604: 783 */     thing.getBundle().prune();
/*  605:     */   }
/*  606:     */   
/*  607:     */   protected Vector<Entity> fetchThings(String key)
/*  608:     */   {
/*  609: 790 */     return fetchThings(key, "thing");
/*  610:     */   }
/*  611:     */   
/*  612:     */   protected Vector<Entity> fetchThings(String hook, String via)
/*  613:     */   {
/*  614: 798 */     Vector<Entity> vector = getThings();
/*  615: 799 */     Vector<Entity> result = new Vector();
/*  616: 802 */     for (int i = 0; i < vector.size(); i++)
/*  617:     */     {
/*  618: 803 */       Entity thing = (Entity)vector.elementAt(i);
/*  619: 818 */       if (thing.entityP())
/*  620:     */       {
/*  621: 821 */         Bundle threads = thing.getBundle();
/*  622: 822 */         for (int j = 0; j < threads.size(); j++)
/*  623:     */         {
/*  624: 823 */           Thread thread = (Thread)threads.get(j);
/*  625: 824 */           if ((thread != null) && (!thread.isEmpty()) && (hook.equalsIgnoreCase((String)thread.lastElement())) && (thing.isA(via))) {
/*  626: 825 */             result.add(thing);
/*  627:     */           }
/*  628:     */         }
/*  629:     */       }
/*  630:     */     }
/*  631: 829 */     return result;
/*  632:     */   }
/*  633:     */   
/*  634:     */   public Entity getReference(String name)
/*  635:     */   {
/*  636: 837 */     return findThingInMemory(name);
/*  637:     */   }
/*  638:     */   
/*  639:     */   public int getLargestID()
/*  640:     */   {
/*  641: 848 */     int result = 0;
/*  642: 850 */     for (int i = 0; i < getThings().size(); i++)
/*  643:     */     {
/*  644: 851 */       Entity thing = (Entity)getThings().get(i);
/*  645: 852 */       result = Math.max(result, thing.getID());
/*  646:     */     }
/*  647: 854 */     return result;
/*  648:     */   }
/*  649:     */   
/*  650:     */   protected static String extractSuffixFromName(String name)
/*  651:     */   {
/*  652: 864 */     int index = name.lastIndexOf('-');
/*  653: 865 */     if (index >= 0) {
/*  654: 866 */       return name.substring(index);
/*  655:     */     }
/*  656: 868 */     return null;
/*  657:     */   }
/*  658:     */   
/*  659:     */   public void clear()
/*  660:     */   {
/*  661: 875 */     saveState();
/*  662: 876 */     getInstances().clear();
/*  663: 877 */     getThings().clear();
/*  664: 878 */     NameGenerator.clearNameMemory();
/*  665: 879 */     fireNotification();
/*  666:     */   }
/*  667:     */   
/*  668:     */   public void storeInFile(String file)
/*  669:     */   {
/*  670:     */     try
/*  671:     */     {
/*  672: 887 */       FileOutputStream f = new FileOutputStream(file);
/*  673: 888 */       ObjectOutputStream o = new ObjectOutputStream(f);
/*  674: 889 */       o.writeObject(getState());
/*  675: 890 */       o.close();
/*  676: 891 */       f.close();
/*  677: 892 */       Logger.info(this, "Wrote serialized file " + file);
/*  678:     */     }
/*  679:     */     catch (Exception f)
/*  680:     */     {
/*  681: 895 */       Logger.warning(this, "Encountered exception while writing serialized data file");
/*  682: 896 */       f.printStackTrace();
/*  683:     */     }
/*  684:     */   }
/*  685:     */   
/*  686:     */   public void restoreFromFile(String file)
/*  687:     */   {
/*  688:     */     try
/*  689:     */     {
/*  690: 905 */       FileInputStream stream = new FileInputStream(file);
/*  691: 906 */       ObjectInputStream o = new ObjectInputStream(stream);
/*  692: 907 */       Vector v = (Vector)o.readObject();
/*  693: 908 */       setState(v);
/*  694: 909 */       o.close();
/*  695: 910 */       NameGenerator.setNameMemory(getLargestID() + 1);
/*  696:     */     }
/*  697:     */     catch (Exception f)
/*  698:     */     {
/*  699: 913 */       Logger.warning(this, "Encountered exception while writing serialized data file");
/*  700: 914 */       f.printStackTrace();
/*  701:     */     }
/*  702:     */   }
/*  703:     */   
/*  704:     */   public class ActionObject
/*  705:     */   {
/*  706:     */     final Object object;
/*  707:     */     final int action;
/*  708:     */     
/*  709:     */     public ActionObject(Object o, int a)
/*  710:     */     {
/*  711: 925 */       this.object = o;
/*  712: 926 */       this.action = a;
/*  713:     */     }
/*  714:     */     
/*  715:     */     public Object getObject()
/*  716:     */     {
/*  717: 930 */       return this.object;
/*  718:     */     }
/*  719:     */     
/*  720:     */     public int getAction()
/*  721:     */     {
/*  722: 934 */       return this.action;
/*  723:     */     }
/*  724:     */   }
/*  725:     */   
/*  726:     */   public String fetchAndShow(String key)
/*  727:     */   {
/*  728: 940 */     return showThings(fetchThings(key));
/*  729:     */   }
/*  730:     */   
/*  731:     */   public String contentsAsStrings()
/*  732:     */   {
/*  733: 944 */     return showThings(getThings());
/*  734:     */   }
/*  735:     */   
/*  736:     */   public String showThings(Vector<Entity> v)
/*  737:     */   {
/*  738: 948 */     String result = "";
/*  739: 949 */     for (Entity t : v) {
/*  740: 950 */       result = result + "\nThing: " + t.asString();
/*  741:     */     }
/*  742: 952 */     return result;
/*  743:     */   }
/*  744:     */   
/*  745:     */   public static void main(String[] arv)
/*  746:     */   {
/*  747: 957 */     getLogger().setLevel(Level.All);
/*  748: 958 */     boolean f = false;
/*  749: 959 */     getStaticMemory().setNotificationOnDependents(f);
/*  750: 960 */     System.out.println("Notification switch:" + f);
/*  751: 961 */     Entity t1 = new Entity("man");
/*  752: 962 */     t1.addType("Patrick");
/*  753: 963 */     Entity t2 = new Entity("boy");
/*  754: 964 */     t2.addType("Mark");
/*  755: 965 */     BasicMemory m = getStaticMemory();
/*  756: 966 */     EntityMemory m1 = new EntityMemory();
/*  757:     */     
/*  758: 968 */     Relation r1 = new Relation("friend", t1, t2);
/*  759:     */     
/*  760: 970 */     m.storeRecursively(r1);
/*  761:     */     
/*  762: 972 */     System.out.println("--------------Testing Memory/Thing memberships -- MAF.14.Jan.04");
/*  763: 973 */     System.out.println("Thing we are manipulating is as follows: " + r1.asString());
/*  764: 974 */     System.out.println("\nContents of Static Memory: " + m.contentsAsStrings());
/*  765: 975 */     System.out.println("\nContents of First Memory: " + m1.contentsAsStrings());
/*  766:     */     
/*  767: 977 */     System.out.println("Patrick: " + t1);
/*  768: 978 */     System.out.println("Mark: " + t2);
/*  769:     */     
/*  770: 980 */     System.out.println("\nMoving thing from static to first...");
/*  771: 981 */     m1.store(t1);
/*  772:     */     
/*  773: 983 */     System.out.println("\nContents of Static Memory: " + m.contentsAsStrings());
/*  774: 984 */     System.out.println("\nContents of First Memory: " + m1.contentsAsStrings());
/*  775:     */     
/*  776: 986 */     System.out.println("Man:" + m.fetchAndShow("man"));
/*  777:     */     
/*  778: 988 */     System.out.println("Boy:" + m.fetchAndShow("boy"));
/*  779:     */     
/*  780: 990 */     System.out.println("Patrick:" + m.fetchAndShow("Patrick"));
/*  781:     */     
/*  782: 992 */     System.out.println("Mark:" + m.fetchAndShow("Mark"));
/*  783:     */   }
/*  784:     */   
/*  785:     */   public static Logger getLogger()
/*  786:     */   {
/*  787:1011 */     return Logger.getLogger("memory.BasicMemory");
/*  788:     */   }
/*  789:     */   
/*  790:     */   protected static void finest(Object s)
/*  791:     */   {
/*  792:1015 */     Logger.getLogger("memory.BasicMemory").finest("BasicMemory: " + s);
/*  793:     */   }
/*  794:     */   
/*  795:     */   protected static void finer(Object s)
/*  796:     */   {
/*  797:1019 */     Logger.getLogger("memory.BasicMemory").finer("BasicMemory: " + s);
/*  798:     */   }
/*  799:     */   
/*  800:     */   protected static void fine(Object s)
/*  801:     */   {
/*  802:1023 */     Logger.getLogger("memory.BasicMemory").fine("BasicMemory: " + s);
/*  803:     */   }
/*  804:     */   
/*  805:     */   protected static void config(Object s)
/*  806:     */   {
/*  807:1027 */     Logger.getLogger("memory.BasicMemory").config("BasicMemory: " + s);
/*  808:     */   }
/*  809:     */   
/*  810:     */   protected static void info(Object s)
/*  811:     */   {
/*  812:1031 */     Logger.getLogger("memory.BasicMemory").info("BasicMemory: " + s);
/*  813:     */   }
/*  814:     */   
/*  815:     */   protected static void warning(Object s)
/*  816:     */   {
/*  817:1035 */     Logger.getLogger("memory.BasicMemory").warning("BasicMemory: " + s);
/*  818:     */   }
/*  819:     */   
/*  820:     */   protected static void severe(Object s)
/*  821:     */   {
/*  822:1040 */     Logger.getLogger("memory.BasicMemory").severe("BasicMemory: " + s);
/*  823:     */   }
/*  824:     */   
/*  825:     */   public String getName()
/*  826:     */   {
/*  827:1044 */     return "Basic Memory";
/*  828:     */   }
/*  829:     */   
/*  830:     */   public void storeConcept(Entity t)
/*  831:     */   {
/*  832:1051 */     if (!t.entityP()) {
/*  833:1052 */       store(t);
/*  834:     */     }
/*  835:1054 */     ArrayList<Entity> recorded = new ArrayList();
/*  836:1055 */     storeConcept(t, t, recorded);
/*  837:     */   }
/*  838:     */   
/*  839:     */   public void storeConcept(Entity t, Entity parent, ArrayList<Entity> recorded)
/*  840:     */   {
/*  841:1072 */     if (!recorded.contains(t)) {
/*  842:1075 */       if (t.entityP())
/*  843:     */       {
/*  844:1076 */         hashConcept(t, parent);
/*  845:1077 */         recorded.add(t);
/*  846:     */       }
/*  847:1079 */       else if (t.functionP())
/*  848:     */       {
/*  849:1080 */         storeConcept(t.getSubject(), parent, recorded);
/*  850:     */       }
/*  851:1082 */       else if (t.relationP())
/*  852:     */       {
/*  853:1083 */         storeConcept(t.getSubject(), parent, recorded);
/*  854:1084 */         storeConcept(t.getObject(), parent, recorded);
/*  855:     */       }
/*  856:1086 */       else if (t.sequenceP())
/*  857:     */       {
/*  858:1087 */         Vector<Entity> v = t.getElements();
/*  859:1088 */         for (int i = 0; i < v.size(); i++)
/*  860:     */         {
/*  861:1089 */           Entity element = (Entity)v.get(i);
/*  862:1090 */           storeConcept(element, parent, recorded);
/*  863:     */         }
/*  864:     */       }
/*  865:     */     }
/*  866:     */   }
/*  867:     */   
/*  868:     */   public ArrayList<Entity> retrieveConcept(Entity t)
/*  869:     */   {
/*  870:1096 */     String type = t.getType();
/*  871:1097 */     ArrayList current = (ArrayList)this.conceptHash.get(type);
/*  872:1098 */     if (current == null)
/*  873:     */     {
/*  874:1099 */       current = new ArrayList();
/*  875:1100 */       this.conceptHash.put(type, current);
/*  876:     */     }
/*  877:1102 */     return current;
/*  878:     */   }
/*  879:     */   
/*  880:     */   private void hashConcept(Entity t, Entity parent)
/*  881:     */   {
/*  882:1107 */     String type = t.getType();
/*  883:1108 */     ArrayList current = (ArrayList)this.conceptHash.get(type);
/*  884:1109 */     if (current == null)
/*  885:     */     {
/*  886:1110 */       current = new ArrayList();
/*  887:1111 */       this.conceptHash.put(type, current);
/*  888:     */     }
/*  889:1113 */     current.add(parent);
/*  890:     */   }
/*  891:     */   
/*  892:     */   public Set<String> getTypes()
/*  893:     */   {
/*  894:1118 */     return this.conceptHash.keySet();
/*  895:     */   }
/*  896:     */   
/*  897:     */   public ArrayList<Entity> retrieveConceptByType(String type)
/*  898:     */   {
/*  899:1122 */     ArrayList current = (ArrayList)this.conceptHash.get(type);
/*  900:1123 */     if (current == null)
/*  901:     */     {
/*  902:1124 */       current = new ArrayList();
/*  903:1125 */       this.conceptHash.put(type, current);
/*  904:     */     }
/*  905:1127 */     return current;
/*  906:     */   }
/*  907:     */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.modules.memory.BasicMemory
 * JD-Core Version:    0.7.0.1
 */