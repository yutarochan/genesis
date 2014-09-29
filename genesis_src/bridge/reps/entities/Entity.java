/*    1:     */ package bridge.reps.entities;
/*    2:     */ 
/*    3:     */ import bridge.modules.memory.MemoryForget;
/*    4:     */ import bridge.utils.ArrayUtils;
/*    5:     */ import bridge.utils.StringUtils;
/*    6:     */ import bridge.utils.collections.IdentityHashSet;
/*    7:     */ import bridge.utils.logging.Logger;
/*    8:     */ import java.io.PrintStream;
/*    9:     */ import java.io.Serializable;
/*   10:     */ import java.util.ArrayList;
/*   11:     */ import java.util.Arrays;
/*   12:     */ import java.util.Collection;
/*   13:     */ import java.util.HashSet;
/*   14:     */ import java.util.IdentityHashMap;
/*   15:     */ import java.util.Iterator;
/*   16:     */ import java.util.LinkedList;
/*   17:     */ import java.util.List;
/*   18:     */ import java.util.Observable;
/*   19:     */ import java.util.Observer;
/*   20:     */ import java.util.Set;
/*   21:     */ import java.util.Vector;
/*   22:     */ import start.Generator;
/*   23:     */ import utils.Mark;
/*   24:     */ 
/*   25:     */ public class Entity
/*   26:     */   extends Observable
/*   27:     */   implements Serializable, Frame
/*   28:     */ {
/*   29:     */   public static final boolean defaultToXMLIsCompact = false;
/*   30:  52 */   public static final Object SAVE_STATE = new Object();
/*   31:  54 */   public static final Object FIRE_NOTIFICATION = new Object();
/*   32:  56 */   public static String pathElement = "pathElement";
/*   33:  58 */   public static String[] placeList = { "above", "at", "below", "under", "farFrom", "in", "near", "nextTo", "rightOf", "leftOf", "on", "over", "by", 
/*   34:  59 */     "top", "bottom" };
/*   35:  61 */   public static String[] pathList = { "to", "from", "toward", "awayFrom", "down", "up", "via" };
/*   36:  63 */   public static String[] roleList = { "with", "by", "for" };
/*   37:  65 */   public static String[] changeList = { "increase", "decrease", "change", "appear", "disappear", "notIncrease", "notDecrease", "notChange", 
/*   38:  66 */     "notAppear", "notDisappear", "blank" };
/*   39:     */   public static final char TYPECHAR_ENTITY = 'E';
/*   40:     */   public static final char TYPECHAR_FUNCTION = 'F';
/*   41:     */   public static final char TYPECHAR_RELATION = 'R';
/*   42:     */   public static final char TYPECHAR_SEQUENCE = 'S';
/*   43:     */   public static final String MARKER_ENTITY = "thing";
/*   44:     */   public static final String MARKER_ACTION = "action";
/*   45:     */   public static final String MARKER_DESCRIPTION = "description";
/*   46:     */   public static final String MARKER_OWNERS = "owners";
/*   47:     */   public static final String MARKER_FEATURE = "feature";
/*   48:     */   public static final String MARKER_DETERMINER = "determiner";
/*   49:     */   public static final String MARKER_WORD = "word";
/*   50:     */   public static final String MARKER_COMPLETE = "complete";
/*   51:  96 */   protected String name = null;
/*   52:  98 */   protected String nameSuffix = null;
/*   53:     */   protected Bundle bundle;
/*   54: 102 */   protected Vector<Entity> modifiers = new Vector();
/*   55: 104 */   protected Vector<Function> subjectOf = new Vector();
/*   56: 106 */   protected Vector<Relation> objectOf = new Vector();
/*   57: 108 */   protected Vector<Entity> elementOf = new Vector();
/*   58: 110 */   protected Vector<Entity> exampleOf = new Vector();
/*   59: 112 */   protected Vector<Observer> observers = new Vector();
/*   60:     */   private Vector<LabelValuePair> propertyList;
/*   61:     */   
/*   62:     */   public boolean functionP()
/*   63:     */   {
/*   64: 119 */     return false;
/*   65:     */   }
/*   66:     */   
/*   67:     */   public boolean functionP(String type)
/*   68:     */   {
/*   69: 123 */     return false;
/*   70:     */   }
/*   71:     */   
/*   72:     */   public boolean relationP()
/*   73:     */   {
/*   74: 127 */     return false;
/*   75:     */   }
/*   76:     */   
/*   77:     */   public boolean relationP(String type)
/*   78:     */   {
/*   79: 131 */     return false;
/*   80:     */   }
/*   81:     */   
/*   82:     */   public boolean sequenceP()
/*   83:     */   {
/*   84: 135 */     return false;
/*   85:     */   }
/*   86:     */   
/*   87:     */   public boolean sequenceP(String type)
/*   88:     */   {
/*   89: 139 */     return false;
/*   90:     */   }
/*   91:     */   
/*   92:     */   public boolean featureP()
/*   93:     */   {
/*   94: 143 */     return false;
/*   95:     */   }
/*   96:     */   
/*   97:     */   public Entity()
/*   98:     */   {
/*   99: 155 */     setNameSuffix(NameGenerator.getNewName());
/*  100: 156 */     setBundle(new Bundle());
/*  101: 157 */     addType("thing");
/*  102:     */   }
/*  103:     */   
/*  104:     */   public Entity(String type)
/*  105:     */   {
/*  106: 164 */     this();
/*  107: 165 */     addType(type);
/*  108:     */   }
/*  109:     */   
/*  110:     */   public Entity(Thread t)
/*  111:     */   {
/*  112: 173 */     setNameSuffix(NameGenerator.getNewName());
/*  113: 174 */     setBundle(new Bundle(t));
/*  114:     */   }
/*  115:     */   
/*  116:     */   public Entity(Bundle b)
/*  117:     */   {
/*  118: 182 */     setNameSuffix(NameGenerator.getNewName());
/*  119: 183 */     setBundle(b);
/*  120:     */   }
/*  121:     */   
/*  122:     */   public Entity(boolean readOnly, String suffix)
/*  123:     */   {
/*  124: 191 */     if (!readOnly) {
/*  125: 192 */       warning("Any boolean argument of an Entity is supposed to be true!");
/*  126:     */     }
/*  127: 194 */     setBundle(new Bundle());
/*  128: 195 */     addType("thing");
/*  129: 196 */     setNameSuffix(suffix);
/*  130:     */   }
/*  131:     */   
/*  132:     */   public boolean entityP()
/*  133:     */   {
/*  134: 204 */     return true;
/*  135:     */   }
/*  136:     */   
/*  137:     */   public boolean entityP(String type)
/*  138:     */   {
/*  139: 208 */     return isAPrimed(type);
/*  140:     */   }
/*  141:     */   
/*  142:     */   public void setName(String n)
/*  143:     */   {
/*  144: 216 */     this.name = n;
/*  145:     */   }
/*  146:     */   
/*  147:     */   public void setNameSuffix(String suffix)
/*  148:     */   {
/*  149: 223 */     this.nameSuffix = suffix;
/*  150:     */   }
/*  151:     */   
/*  152:     */   public String getNameSuffix()
/*  153:     */   {
/*  154: 231 */     return this.nameSuffix;
/*  155:     */   }
/*  156:     */   
/*  157:     */   public String getIdentifier()
/*  158:     */   {
/*  159: 238 */     return this.nameSuffix.substring(1);
/*  160:     */   }
/*  161:     */   
/*  162:     */   public int getID()
/*  163:     */   {
/*  164: 242 */     return new Integer(getIdentifier()).intValue();
/*  165:     */   }
/*  166:     */   
/*  167:     */   public String getName()
/*  168:     */   {
/*  169: 249 */     if (this.name != null) {
/*  170: 250 */       return this.name;
/*  171:     */     }
/*  172: 252 */     return getType() + this.nameSuffix;
/*  173:     */   }
/*  174:     */   
/*  175:     */   public String getExplicitName()
/*  176:     */   {
/*  177: 259 */     return this.name;
/*  178:     */   }
/*  179:     */   
/*  180:     */   public Vector<String> getKeys()
/*  181:     */   {
/*  182: 279 */     Vector<String> keys = new Vector();
/*  183: 280 */     for (LabelValuePair lv : getPropertyList()) {
/*  184: 281 */       keys.add(lv.getLabel());
/*  185:     */     }
/*  186: 283 */     return keys;
/*  187:     */   }
/*  188:     */   
/*  189:     */   public Vector<LabelValuePair> getPropertyList()
/*  190:     */   {
/*  191: 287 */     if (this.propertyList == null) {
/*  192: 288 */       this.propertyList = new Vector();
/*  193:     */     }
/*  194: 290 */     return this.propertyList;
/*  195:     */   }
/*  196:     */   
/*  197:     */   public void setPropertyList(Vector<LabelValuePair> propertyList)
/*  198:     */   {
/*  199: 294 */     this.propertyList = propertyList;
/*  200:     */   }
/*  201:     */   
/*  202:     */   public Vector<LabelValuePair> clonePropertyList()
/*  203:     */   {
/*  204: 298 */     Vector<LabelValuePair> clone = new Vector();
/*  205: 299 */     for (LabelValuePair pair : getPropertyList()) {
/*  206: 300 */       clone.add(clonePair(pair));
/*  207:     */     }
/*  208: 302 */     return clone;
/*  209:     */   }
/*  210:     */   
/*  211:     */   private LabelValuePair clonePair(LabelValuePair pair)
/*  212:     */   {
/*  213: 306 */     return new LabelValuePair(pair.getLabel(), pair.getValue());
/*  214:     */   }
/*  215:     */   
/*  216:     */   public void addProperty(String label, Object object)
/*  217:     */   {
/*  218: 310 */     for (LabelValuePair pair : getPropertyList()) {
/*  219: 311 */       if (pair.getLabel().equals(label))
/*  220:     */       {
/*  221: 312 */         pair.setValue(object);
/*  222: 313 */         return;
/*  223:     */       }
/*  224:     */     }
/*  225: 316 */     getPropertyList().add(new LabelValuePair(label, object));
/*  226:     */   }
/*  227:     */   
/*  228:     */   public Object getProperty(String label)
/*  229:     */   {
/*  230: 320 */     for (LabelValuePair pair : getPropertyList()) {
/*  231: 321 */       if (pair.getLabel().equals(label)) {
/*  232: 322 */         return pair.getValue();
/*  233:     */       }
/*  234:     */     }
/*  235: 325 */     return null;
/*  236:     */   }
/*  237:     */   
/*  238:     */   public void removeProperty(String label)
/*  239:     */   {
/*  240: 336 */     LabelValuePair remove = null;
/*  241: 337 */     for (LabelValuePair pair : getPropertyList()) {
/*  242: 338 */       if (pair.getLabel().equals(label))
/*  243:     */       {
/*  244: 339 */         remove = pair;
/*  245: 340 */         break;
/*  246:     */       }
/*  247:     */     }
/*  248: 343 */     if (remove != null) {
/*  249: 344 */       getPropertyList().remove(remove);
/*  250:     */     }
/*  251:     */   }
/*  252:     */   
/*  253:     */   public boolean hasProperty(String label)
/*  254:     */   {
/*  255: 352 */     if (getProperty(label) != null) {
/*  256: 353 */       return true;
/*  257:     */     }
/*  258: 355 */     return false;
/*  259:     */   }
/*  260:     */   
/*  261:     */   public boolean hasProperty(String label, Object value)
/*  262:     */   {
/*  263: 362 */     Object v = getProperty(label);
/*  264: 363 */     if ((v instanceof String)) {
/*  265: 364 */       return v.equals(value);
/*  266:     */     }
/*  267: 367 */     return v == value;
/*  268:     */   }
/*  269:     */   
/*  270: 370 */   public static String OWNER = "owner";
/*  271: 372 */   public static String PROPERTY = "property";
/*  272:     */   
/*  273:     */   public class LabelValuePair
/*  274:     */   {
/*  275:     */     private String label;
/*  276:     */     private Object value;
/*  277:     */     
/*  278:     */     public void setValue(Object value)
/*  279:     */     {
/*  280: 381 */       this.value = value;
/*  281:     */     }
/*  282:     */     
/*  283:     */     public String getLabel()
/*  284:     */     {
/*  285: 385 */       return this.label;
/*  286:     */     }
/*  287:     */     
/*  288:     */     public Object getValue()
/*  289:     */     {
/*  290: 389 */       return this.value;
/*  291:     */     }
/*  292:     */     
/*  293:     */     public LabelValuePair(String label, Object value)
/*  294:     */     {
/*  295: 393 */       this.label = label;
/*  296: 394 */       this.value = value;
/*  297:     */     }
/*  298:     */     
/*  299:     */     public LabelValuePair clone(LabelValuePair source)
/*  300:     */     {
/*  301: 398 */       return new LabelValuePair(Entity.this, this.label, this.value);
/*  302:     */     }
/*  303:     */     
/*  304:     */     public String toXML()
/*  305:     */     {
/*  306: 402 */       return "<" + this.label + ", " + this.value.toString() + ">";
/*  307:     */     }
/*  308:     */     
/*  309:     */     public boolean equals(LabelValuePair that)
/*  310:     */     {
/*  311: 406 */       if (this.label.equals(that.getLabel()))
/*  312:     */       {
/*  313: 407 */         if (((this.value instanceof String)) && (this.value.equals(that.getValue()))) {
/*  314: 408 */           return true;
/*  315:     */         }
/*  316: 410 */         if (this.value == that.getValue()) {
/*  317: 411 */           return true;
/*  318:     */         }
/*  319:     */       }
/*  320: 414 */       return false;
/*  321:     */     }
/*  322:     */   }
/*  323:     */   
/*  324:     */   public void addFeature(Object object)
/*  325:     */   {
/*  326: 445 */     for (LabelValuePair pair : getPropertyList()) {
/*  327: 446 */       if ((pair.getLabel().equals("feature")) && (pair.getValue().equals(object))) {
/*  328: 448 */         return;
/*  329:     */       }
/*  330:     */     }
/*  331: 452 */     getPropertyList().add(new LabelValuePair("feature", object));
/*  332:     */   }
/*  333:     */   
/*  334:     */   public boolean removeFeature(Object t)
/*  335:     */   {
/*  336: 457 */     ArrayList<LabelValuePair> clone = new ArrayList();
/*  337: 458 */     clone.addAll(getPropertyList());
/*  338: 459 */     for (LabelValuePair pair : clone) {
/*  339: 460 */       if ((pair.getLabel().equals("feature")) && (pair.getValue().equals(t))) {
/*  340: 462 */         getPropertyList().remove(pair);
/*  341:     */       }
/*  342:     */     }
/*  343: 465 */     return false;
/*  344:     */   }
/*  345:     */   
/*  346:     */   public boolean hasFeature(Object object)
/*  347:     */   {
/*  348: 469 */     for (LabelValuePair pair : getPropertyList()) {
/*  349: 470 */       if ((pair.getLabel().equals("feature")) && (pair.getValue().equals(object))) {
/*  350: 472 */         return true;
/*  351:     */       }
/*  352:     */     }
/*  353: 475 */     return false;
/*  354:     */   }
/*  355:     */   
/*  356:     */   public ArrayList<Object> getFeatures()
/*  357:     */   {
/*  358: 489 */     ArrayList<Object> result = new ArrayList();
/*  359: 490 */     for (LabelValuePair pair : getPropertyList()) {
/*  360: 491 */       if (pair.getLabel().equals("feature")) {
/*  361: 493 */         result.add(pair.getValue());
/*  362:     */       }
/*  363:     */     }
/*  364: 496 */     return result;
/*  365:     */   }
/*  366:     */   
/*  367:     */   public Bundle getBundle()
/*  368:     */   {
/*  369: 507 */     return this.bundle;
/*  370:     */   }
/*  371:     */   
/*  372:     */   public void setBundle(Bundle b)
/*  373:     */   {
/*  374: 515 */     if (this.bundle == b) {
/*  375: 516 */       return;
/*  376:     */     }
/*  377: 518 */     saveState();
/*  378: 519 */     if (this.bundle != null) {
/*  379: 520 */       this.bundle.setOwnerThingNull();
/*  380:     */     }
/*  381: 523 */     this.bundle = b;
/*  382: 525 */     if (this.bundle != null) {
/*  383: 526 */       this.bundle.setOwnerThing(this);
/*  384:     */     }
/*  385: 528 */     fireNotification();
/*  386:     */   }
/*  387:     */   
/*  388:     */   public void addThread(Thread t)
/*  389:     */   {
/*  390: 535 */     this.bundle.addThread(t);
/*  391:     */   }
/*  392:     */   
/*  393:     */   public void replacePrimedThread(Thread t)
/*  394:     */   {
/*  395: 539 */     if (this.bundle.size() != 0) {
/*  396: 540 */       this.bundle.remove(0);
/*  397:     */     }
/*  398: 542 */     this.bundle.add(0, t);
/*  399:     */   }
/*  400:     */   
/*  401:     */   public Thread getPrimedThread()
/*  402:     */   {
/*  403: 549 */     return getBundle().getPrimedThread();
/*  404:     */   }
/*  405:     */   
/*  406:     */   public void setPrimedThread(Thread t)
/*  407:     */   {
/*  408: 556 */     replacePrimedThread(t);
/*  409:     */   }
/*  410:     */   
/*  411:     */   public Thread getThread(String firstElement)
/*  412:     */   {
/*  413: 560 */     return this.bundle.getThread(firstElement);
/*  414:     */   }
/*  415:     */   
/*  416:     */   public Thread getThreadWith(String element)
/*  417:     */   {
/*  418: 564 */     for (Thread t : getBundle()) {
/*  419: 565 */       if (t.contains(element)) {
/*  420: 566 */         return t;
/*  421:     */       }
/*  422:     */     }
/*  423: 569 */     return null;
/*  424:     */   }
/*  425:     */   
/*  426:     */   public Thread getThreadWith(String first, String last)
/*  427:     */   {
/*  428: 573 */     for (Thread t : getBundle()) {
/*  429: 574 */       if ((((String)t.firstElement()).equals(first)) && (((String)t.lastElement()).equals(last))) {
/*  430: 575 */         return t;
/*  431:     */       }
/*  432:     */     }
/*  433: 578 */     return null;
/*  434:     */   }
/*  435:     */   
/*  436:     */   public void swapPrimedThread()
/*  437:     */   {
/*  438: 585 */     getBundle().swapPrimedThread();
/*  439:     */   }
/*  440:     */   
/*  441:     */   public void pushPrimedThread()
/*  442:     */   {
/*  443: 592 */     getBundle().pushPrimedThread();
/*  444:     */   }
/*  445:     */   
/*  446:     */   public void sendPrimedThreadToEnd()
/*  447:     */   {
/*  448: 599 */     getBundle().sendPrimedThreadToEnd();
/*  449:     */   }
/*  450:     */   
/*  451:     */   public String getType()
/*  452:     */   {
/*  453: 607 */     return this.bundle.getType();
/*  454:     */   }
/*  455:     */   
/*  456:     */   public String getSupertype()
/*  457:     */   {
/*  458: 617 */     return this.bundle.getSupertype();
/*  459:     */   }
/*  460:     */   
/*  461:     */   public void addType(String t)
/*  462:     */   {
/*  463: 621 */     Thread thread = getPrimedThread();
/*  464: 622 */     thread.addType(t);
/*  465:     */   }
/*  466:     */   
/*  467:     */   public void addDeterminer(String t)
/*  468:     */   {
/*  469: 626 */     addType(t, "determiner");
/*  470:     */   }
/*  471:     */   
/*  472:     */   public Thread getDeterminer()
/*  473:     */   {
/*  474: 630 */     for (int i = 0; i < this.bundle.size(); i++)
/*  475:     */     {
/*  476: 631 */       Thread thread = (Thread)this.bundle.elementAt(i);
/*  477: 632 */       if (thread.contains("determiner")) {
/*  478: 633 */         return thread;
/*  479:     */       }
/*  480:     */     }
/*  481: 636 */     return null;
/*  482:     */   }
/*  483:     */   
/*  484:     */   public void addType(String type, String threadType)
/*  485:     */   {
/*  486: 640 */     for (int i = 0; i < this.bundle.size(); i++)
/*  487:     */     {
/*  488: 641 */       Thread thread = (Thread)this.bundle.elementAt(i);
/*  489: 643 */       if ((thread != null) && (thread.contains(threadType)))
/*  490:     */       {
/*  491: 644 */         thread.addType(type);
/*  492: 645 */         return;
/*  493:     */       }
/*  494:     */     }
/*  495: 648 */     Thread thread = new Thread();
/*  496: 649 */     thread.addType(threadType);
/*  497: 650 */     thread.addType(type);
/*  498: 651 */     this.bundle.addThreadAtEnd(thread);
/*  499:     */   }
/*  500:     */   
/*  501:     */   public void addTypes(String threadType, String types)
/*  502:     */   {
/*  503: 661 */     int start = 0;
/*  504:     */     
/*  505: 663 */     types = types.trim();
/*  506:     */     int end;
/*  507: 664 */     while ((end = types.indexOf(' ', start + 1)) > -1)
/*  508:     */     {
/*  509:     */       int end;
/*  510: 665 */       addType(types.substring(start, end), threadType);
/*  511: 666 */       start = end + 1;
/*  512:     */     }
/*  513: 668 */     addType(types.substring(start, types.length()), threadType);
/*  514:     */   }
/*  515:     */   
/*  516:     */   public void addType(String t, String[] ok)
/*  517:     */   {
/*  518: 676 */     if (StringUtils.testType(t, ok)) {
/*  519: 677 */       addType(t);
/*  520:     */     } else {
/*  521: 680 */       System.err.println("Tried to add unacceptible type " + t);
/*  522:     */     }
/*  523:     */   }
/*  524:     */   
/*  525:     */   public void addTypes(Vector<?> v)
/*  526:     */   {
/*  527: 688 */     getBundle().pushPrimedThread();
/*  528: 689 */     for (int i = 0; i < v.size(); i++)
/*  529:     */     {
/*  530: 690 */       Object o = v.elementAt(i);
/*  531: 691 */       if ((o instanceof String)) {
/*  532: 692 */         addType((String)o);
/*  533:     */       } else {
/*  534: 695 */         System.err.println("Tryed to add a type that is not a string");
/*  535:     */       }
/*  536:     */     }
/*  537:     */   }
/*  538:     */   
/*  539:     */   public boolean removeType(String type)
/*  540:     */   {
/*  541: 701 */     boolean result = false;
/*  542: 702 */     for (int i = 0; i < this.bundle.size(); i++)
/*  543:     */     {
/*  544: 703 */       Thread thread = (Thread)this.bundle.elementAt(i);
/*  545: 704 */       if (thread.contains(type))
/*  546:     */       {
/*  547: 705 */         thread.remove(type);
/*  548: 706 */         result = true;
/*  549:     */       }
/*  550:     */     }
/*  551: 709 */     return result;
/*  552:     */   }
/*  553:     */   
/*  554:     */   public void imagineType(String t)
/*  555:     */   {
/*  556: 716 */     getBundle().pushPrimedThread();
/*  557: 717 */     addType(t);
/*  558:     */   }
/*  559:     */   
/*  560:     */   public Vector<String> getTypes()
/*  561:     */   {
/*  562: 724 */     Vector<String> v = new Vector();
/*  563: 726 */     if (this.bundle.size() == 0) {
/*  564: 727 */       return v;
/*  565:     */     }
/*  566: 729 */     Thread thread = (Thread)this.bundle.elementAt(0);
/*  567: 730 */     for (int j = 0; j < thread.size(); j++)
/*  568:     */     {
/*  569: 731 */       String s = (String)thread.elementAt(j);
/*  570: 732 */       if (!v.contains(s)) {
/*  571: 733 */         v.add(s);
/*  572:     */       }
/*  573:     */     }
/*  574: 736 */     return v;
/*  575:     */   }
/*  576:     */   
/*  577:     */   public Vector<String> getAllTypes()
/*  578:     */   {
/*  579: 743 */     Vector<String> v = new Vector();
/*  580: 745 */     for (int i = 0; i < this.bundle.size(); i++)
/*  581:     */     {
/*  582: 746 */       Thread thread = (Thread)this.bundle.elementAt(i);
/*  583: 747 */       if (thread.size() > 0) {
/*  584: 748 */         for (int j = 0; j < thread.size(); j++)
/*  585:     */         {
/*  586: 749 */           String s = (String)thread.elementAt(j);
/*  587: 750 */           if (!v.contains(s)) {
/*  588: 751 */             v.add(s);
/*  589:     */           }
/*  590:     */         }
/*  591:     */       }
/*  592:     */     }
/*  593: 756 */     return v;
/*  594:     */   }
/*  595:     */   
/*  596:     */   public Set<String> getAllTypesForFindMatchingThing()
/*  597:     */   {
/*  598: 765 */     String[] ignore = { "features", "tracers" };
/*  599: 766 */     return getAllTypesExcept(ignore);
/*  600:     */   }
/*  601:     */   
/*  602:     */   public Set<String> getAllTypesExcept(String[] ignoreTheseThreads)
/*  603:     */   {
/*  604: 770 */     Set<String> result = new HashSet();
/*  605: 771 */     result.addAll(Arrays.asList(this.bundle.getAllTypesExcept(ignoreTheseThreads)));
/*  606: 772 */     return result;
/*  607:     */   }
/*  608:     */   
/*  609:     */   public Vector<String> getMatcherTypes()
/*  610:     */   {
/*  611: 780 */     Vector<String> v = new Vector();
/*  612: 782 */     for (int i = 0; i < this.bundle.size(); i++)
/*  613:     */     {
/*  614: 783 */       Thread thread = (Thread)this.bundle.elementAt(i);
/*  615: 784 */       if (thread.size() > 0)
/*  616:     */       {
/*  617: 785 */         String top = (String)thread.elementAt(0);
/*  618: 786 */         if ((top.equalsIgnoreCase("thing")) || (top.equalsIgnoreCase("description"))) {
/*  619: 787 */           for (int j = 0; j < thread.size(); j++)
/*  620:     */           {
/*  621: 788 */             String s = (String)thread.elementAt(j);
/*  622: 789 */             if (!v.contains(s)) {
/*  623: 790 */               v.add(s);
/*  624:     */             }
/*  625:     */           }
/*  626:     */         }
/*  627:     */       }
/*  628:     */     }
/*  629: 796 */     return v;
/*  630:     */   }
/*  631:     */   
/*  632:     */   public boolean isA(String s)
/*  633:     */   {
/*  634: 804 */     if (this.bundle.size() == 0) {
/*  635: 805 */       return false;
/*  636:     */     }
/*  637: 807 */     for (int i = 0; i < this.bundle.size(); i++)
/*  638:     */     {
/*  639: 808 */       Thread thread = (Thread)this.bundle.elementAt(i);
/*  640: 809 */       if (thread.contains(s)) {
/*  641: 810 */         return true;
/*  642:     */       }
/*  643:     */     }
/*  644: 813 */     return false;
/*  645:     */   }
/*  646:     */   
/*  647:     */   public boolean isA(String type, String ignoreThread)
/*  648:     */   {
/*  649: 817 */     String[] ignoreThreads = { ignoreThread };
/*  650: 818 */     return isA(type, ignoreThreads);
/*  651:     */   }
/*  652:     */   
/*  653:     */   public boolean isA(List<String> actions)
/*  654:     */   {
/*  655: 822 */     if (actions.isEmpty()) {
/*  656: 823 */       return true;
/*  657:     */     }
/*  658: 825 */     for (Iterator<String> i = actions.iterator(); i.hasNext();) {
/*  659:     */       try
/*  660:     */       {
/*  661: 828 */         if (isA((String)i.next())) {
/*  662: 829 */           return true;
/*  663:     */         }
/*  664:     */       }
/*  665:     */       catch (RuntimeException e)
/*  666:     */       {
/*  667: 833 */         System.err.println(actions);
/*  668: 834 */         e.printStackTrace();
/*  669:     */       }
/*  670:     */     }
/*  671: 837 */     return false;
/*  672:     */   }
/*  673:     */   
/*  674:     */   public boolean isAPrimedAction(String type)
/*  675:     */   {
/*  676: 841 */     Bundle bundle = getBundle();
/*  677: 842 */     for (Thread thread : bundle) {
/*  678: 843 */       if ((!thread.isEmpty()) && (((String)thread.firstElement()).equals("action"))) {
/*  679: 847 */         return thread.contains(type);
/*  680:     */       }
/*  681:     */     }
/*  682: 850 */     return false;
/*  683:     */   }
/*  684:     */   
/*  685:     */   public boolean isAPrimed(String type)
/*  686:     */   {
/*  687:     */     try
/*  688:     */     {
/*  689: 855 */       if (getPrimedThread().contains(type)) {
/*  690: 856 */         return true;
/*  691:     */       }
/*  692:     */     }
/*  693:     */     catch (Exception e)
/*  694:     */     {
/*  695: 860 */       Mark.say(new Object[] {"Exception in Entity.isAPrimed", type });
/*  696:     */     }
/*  697: 863 */     return false;
/*  698:     */   }
/*  699:     */   
/*  700:     */   public boolean isAPrimed(List<String> list)
/*  701:     */   {
/*  702: 867 */     if (list.isEmpty()) {
/*  703: 868 */       return true;
/*  704:     */     }
/*  705: 870 */     for (Iterator<String> i = list.iterator(); i.hasNext();) {
/*  706:     */       try
/*  707:     */       {
/*  708: 873 */         String test = (String)i.next();
/*  709: 874 */         if (isAPrimed(test)) {
/*  710: 875 */           return true;
/*  711:     */         }
/*  712:     */       }
/*  713:     */       catch (RuntimeException e)
/*  714:     */       {
/*  715: 879 */         System.err.println(list);
/*  716: 880 */         e.printStackTrace();
/*  717:     */       }
/*  718:     */     }
/*  719: 883 */     return false;
/*  720:     */   }
/*  721:     */   
/*  722:     */   public boolean isA(String type, String[] ignoreThreads)
/*  723:     */   {
/*  724: 893 */     if (this.bundle.size() == 0) {
/*  725: 894 */       return false;
/*  726:     */     }
/*  727: 896 */     for (int i = 0; i < this.bundle.size(); i++)
/*  728:     */     {
/*  729: 897 */       Thread thread = (Thread)this.bundle.elementAt(i);
/*  730: 898 */       if ((!ArrayUtils.contains(ignoreThreads, thread.getThreadType())) && 
/*  731: 899 */         (thread.contains(type))) {
/*  732: 902 */         return true;
/*  733:     */       }
/*  734:     */     }
/*  735: 906 */     return false;
/*  736:     */   }
/*  737:     */   
/*  738:     */   public boolean isNotA(String s)
/*  739:     */   {
/*  740: 916 */     if (isA(s)) {
/*  741: 917 */       return false;
/*  742:     */     }
/*  743: 919 */     return true;
/*  744:     */   }
/*  745:     */   
/*  746:     */   public boolean isNotAPrimed(String type)
/*  747:     */   {
/*  748: 923 */     if (getPrimedThread().contains(type)) {
/*  749: 924 */       return false;
/*  750:     */     }
/*  751: 926 */     return true;
/*  752:     */   }
/*  753:     */   
/*  754:     */   public boolean isAnyOf(String[] s)
/*  755:     */   {
/*  756: 936 */     return !isNoneOf(s);
/*  757:     */   }
/*  758:     */   
/*  759:     */   public boolean isAnyOf(String[] types, String ignoreThread)
/*  760:     */   {
/*  761: 943 */     String[] ignoreThreads = { ignoreThread };
/*  762: 944 */     return isAnyOf(types, ignoreThreads);
/*  763:     */   }
/*  764:     */   
/*  765:     */   public boolean isAnyOf(String[] types, String[] ignoreThreads)
/*  766:     */   {
/*  767: 951 */     for (int i = 0; i < types.length; i++) {
/*  768: 952 */       if (isA(types[i], ignoreThreads)) {
/*  769: 953 */         return true;
/*  770:     */       }
/*  771:     */     }
/*  772: 956 */     return false;
/*  773:     */   }
/*  774:     */   
/*  775:     */   public boolean isAllOf(String[] s)
/*  776:     */   {
/*  777: 968 */     for (int i = 0; i < s.length; i++) {
/*  778: 969 */       if (!isA(s[i])) {
/*  779: 970 */         return false;
/*  780:     */       }
/*  781:     */     }
/*  782: 973 */     return true;
/*  783:     */   }
/*  784:     */   
/*  785:     */   public boolean isNoneOf(String[] s)
/*  786:     */   {
/*  787: 985 */     for (int i = 0; i < s.length; i++) {
/*  788: 986 */       if (isA(s[i])) {
/*  789: 987 */         return false;
/*  790:     */       }
/*  791:     */     }
/*  792: 990 */     return true;
/*  793:     */   }
/*  794:     */   
/*  795:     */   public boolean isPathElement()
/*  796:     */   {
/*  797:1001 */     return isA(pathElement);
/*  798:     */   }
/*  799:     */   
/*  800:     */   public void setSubject(Entity t)
/*  801:     */   {
/*  802:1009 */     warning("Tried to set subject, but " + asString() + " is not a Derivative");
/*  803:     */   }
/*  804:     */   
/*  805:     */   public Entity getSubject()
/*  806:     */   {
/*  807:1013 */     return null;
/*  808:     */   }
/*  809:     */   
/*  810:     */   public void setObject(Entity t)
/*  811:     */   {
/*  812:1017 */     warning("Tried to set object, but " + asString() + " is not a Relation");
/*  813:     */   }
/*  814:     */   
/*  815:     */   public Entity getObject()
/*  816:     */   {
/*  817:1022 */     warning("Tried to get object, but " + asString() + " is not a Relation");
/*  818:1023 */     return null;
/*  819:     */   }
/*  820:     */   
/*  821:     */   public void addElement(Entity t)
/*  822:     */   {
/*  823:1027 */     warning("Tried to add an element, but " + asString() + " is not a Sequence");
/*  824:     */   }
/*  825:     */   
/*  826:     */   public Vector<Entity> getElements()
/*  827:     */   {
/*  828:1031 */     return null;
/*  829:     */   }
/*  830:     */   
/*  831:     */   public Entity getElement(int i)
/*  832:     */   {
/*  833:1035 */     return null;
/*  834:     */   }
/*  835:     */   
/*  836:     */   public Vector<Function> getSubjectOf()
/*  837:     */   {
/*  838:1042 */     return this.subjectOf;
/*  839:     */   }
/*  840:     */   
/*  841:     */   public Vector<Function> getSubjectOf(String s)
/*  842:     */   {
/*  843:1050 */     Vector<Function> v = new Vector();
/*  844:1051 */     for (int i = 0; i < this.subjectOf.size(); i++) {
/*  845:1052 */       if (((Function)this.subjectOf.get(i)).isA(s)) {
/*  846:1053 */         v.add((Function)this.subjectOf.get(i));
/*  847:     */       }
/*  848:     */     }
/*  849:1056 */     return v;
/*  850:     */   }
/*  851:     */   
/*  852:     */   public void addSubjectOf(Function d)
/*  853:     */   {
/*  854:1063 */     this.subjectOf.add(d);
/*  855:     */   }
/*  856:     */   
/*  857:     */   public boolean removeSubjectOf(Entity t)
/*  858:     */   {
/*  859:1067 */     return this.subjectOf.remove(t);
/*  860:     */   }
/*  861:     */   
/*  862:     */   public Vector<Relation> getObjectOf()
/*  863:     */   {
/*  864:1074 */     return this.objectOf;
/*  865:     */   }
/*  866:     */   
/*  867:     */   public Vector<Relation> getObjectOf(String s)
/*  868:     */   {
/*  869:1081 */     Vector<Relation> v = new Vector();
/*  870:1082 */     for (int i = 0; i < this.objectOf.size(); i++) {
/*  871:1083 */       if (((Relation)this.objectOf.get(i)).isA(s)) {
/*  872:1084 */         v.add((Relation)this.objectOf.get(i));
/*  873:     */       }
/*  874:     */     }
/*  875:1087 */     return v;
/*  876:     */   }
/*  877:     */   
/*  878:     */   public boolean removeObjectOf(Entity t)
/*  879:     */   {
/*  880:1091 */     return this.objectOf.remove(t);
/*  881:     */   }
/*  882:     */   
/*  883:     */   public void addObjectOf(Relation r)
/*  884:     */   {
/*  885:1098 */     this.objectOf.add(r);
/*  886:     */   }
/*  887:     */   
/*  888:     */   public Vector<Entity> getElementOf()
/*  889:     */   {
/*  890:1105 */     return this.elementOf;
/*  891:     */   }
/*  892:     */   
/*  893:     */   public void addElementOf(Sequence s)
/*  894:     */   {
/*  895:1112 */     this.elementOf.add(s);
/*  896:     */   }
/*  897:     */   
/*  898:     */   public boolean removeElementOf(Entity t)
/*  899:     */   {
/*  900:1116 */     return this.elementOf.remove(t);
/*  901:     */   }
/*  902:     */   
/*  903:     */   public boolean removeParent(Entity t)
/*  904:     */   {
/*  905:1120 */     return removeSubjectOf(t) | removeObjectOf(t) | removeElementOf(t);
/*  906:     */   }
/*  907:     */   
/*  908:     */   public Set<Entity> getParents()
/*  909:     */   {
/*  910:1124 */     Set<Entity> result = new HashSet();
/*  911:1125 */     result.addAll(getSubjectOf());
/*  912:1126 */     result.addAll(getObjectOf());
/*  913:1127 */     result.addAll(getElementOf());
/*  914:1128 */     return result;
/*  915:     */   }
/*  916:     */   
/*  917:     */   public Set<Entity> getChildren()
/*  918:     */   {
/*  919:1138 */     return new HashSet();
/*  920:     */   }
/*  921:     */   
/*  922:     */   public int getNumberOfChildren()
/*  923:     */   {
/*  924:1142 */     return 0;
/*  925:     */   }
/*  926:     */   
/*  927:     */   public Set<Entity> getRoots()
/*  928:     */   {
/*  929:1152 */     Set<Entity> result = new HashSet();
/*  930:     */     
/*  931:1154 */     List<Entity> queue = new ArrayList();
/*  932:1155 */     queue.addAll(getParents());
/*  933:1159 */     while (!queue.isEmpty())
/*  934:     */     {
/*  935:1160 */       Entity next = (Entity)queue.remove(0);
/*  936:1161 */       if (result.add(next))
/*  937:     */       {
/*  938:1164 */         Set<Entity> parents = next.getParents();
/*  939:1165 */         if (parents.isEmpty()) {
/*  940:1166 */           result.add(next);
/*  941:     */         } else {
/*  942:1169 */           queue.addAll(parents);
/*  943:     */         }
/*  944:     */       }
/*  945:     */     }
/*  946:1173 */     return result;
/*  947:     */   }
/*  948:     */   
/*  949:     */   public Set<Entity> getAncestors()
/*  950:     */   {
/*  951:1177 */     HashSet<Entity> result = new HashSet();
/*  952:1178 */     List<Entity> queue = new ArrayList();
/*  953:1179 */     queue.addAll(getParents());
/*  954:1182 */     while (!queue.isEmpty())
/*  955:     */     {
/*  956:1183 */       Entity next = (Entity)queue.remove(0);
/*  957:1184 */       if (result.add(next)) {
/*  958:1187 */         queue.addAll(next.getParents());
/*  959:     */       }
/*  960:     */     }
/*  961:1190 */     return result;
/*  962:     */   }
/*  963:     */   
/*  964:     */   public Set<Entity> getDescendants()
/*  965:     */   {
/*  966:1194 */     HashSet<Entity> result = new HashSet();
/*  967:1195 */     List<Entity> queue = new LinkedList();
/*  968:1196 */     queue.addAll(getChildren());
/*  969:1199 */     while (!queue.isEmpty())
/*  970:     */     {
/*  971:1200 */       Entity next = (Entity)queue.remove(0);
/*  972:1201 */       if (result.add(next)) {
/*  973:1204 */         queue.addAll(next.getChildren());
/*  974:     */       }
/*  975:     */     }
/*  976:1207 */     return result;
/*  977:     */   }
/*  978:     */   
/*  979:     */   public Set<Entity> getConnected()
/*  980:     */   {
/*  981:1217 */     HashSet<Entity> visited = new HashSet();
/*  982:     */     
/*  983:1219 */     ArrayList<Entity> queue = new ArrayList();
/*  984:1220 */     queue.add(this);
/*  985:1223 */     while (!queue.isEmpty())
/*  986:     */     {
/*  987:1224 */       Entity current = (Entity)queue.remove(0);
/*  988:1225 */       if (visited.add(current))
/*  989:     */       {
/*  990:1228 */         queue.addAll(current.getParents());
/*  991:1229 */         queue.addAll(current.getChildren());
/*  992:     */       }
/*  993:     */     }
/*  994:1232 */     return visited;
/*  995:     */   }
/*  996:     */   
/*  997:     */   public Collection<Entity> getAncestralTrajectorySpaces()
/*  998:     */   {
/*  999:1239 */     Collection<Entity> result = new HashSet();
/* 1000:1240 */     Collection<Entity> visited = new HashSet();
/* 1001:1241 */     Vector<Entity> queue = new Vector();
/* 1002:1242 */     queue.add(this);
/* 1003:1244 */     while (!queue.isEmpty())
/* 1004:     */     {
/* 1005:1245 */       Entity currentThing = (Entity)queue.remove(0);
/* 1006:1246 */       if (visited.add(currentThing))
/* 1007:     */       {
/* 1008:1247 */         queue.addAll(currentThing.getSubjectOf());
/* 1009:1248 */         queue.addAll(currentThing.getObjectOf());
/* 1010:1249 */         queue.addAll(currentThing.getElementOf());
/* 1011:1250 */         if (currentThing.isA("eventSpace")) {
/* 1012:1251 */           result.add(currentThing);
/* 1013:     */         }
/* 1014:     */       }
/* 1015:     */     }
/* 1016:1255 */     return result;
/* 1017:     */   }
/* 1018:     */   
/* 1019:     */   public boolean isRoot()
/* 1020:     */   {
/* 1021:1259 */     if ((this.elementOf.isEmpty() & this.subjectOf.isEmpty() & this.objectOf.isEmpty())) {
/* 1022:1260 */       return true;
/* 1023:     */     }
/* 1024:1262 */     return false;
/* 1025:     */   }
/* 1026:     */   
/* 1027:     */   public boolean isAncestorOf(Entity t)
/* 1028:     */   {
/* 1029:1272 */     HashSet<Entity> visited = new HashSet();
/* 1030:     */     
/* 1031:1274 */     List<Entity> queue = new ArrayList();
/* 1032:1275 */     queue.add(this);
/* 1033:1278 */     while (!queue.isEmpty())
/* 1034:     */     {
/* 1035:1279 */       Entity current = (Entity)queue.remove(0);
/* 1036:1280 */       if (visited.add(current))
/* 1037:     */       {
/* 1038:1283 */         queue.addAll(current.getChildren());
/* 1039:1284 */         if (current.equals(t)) {
/* 1040:1285 */           return true;
/* 1041:     */         }
/* 1042:     */       }
/* 1043:     */     }
/* 1044:1288 */     return false;
/* 1045:     */   }
/* 1046:     */   
/* 1047:     */   public void breakLinksFromChildren()
/* 1048:     */   {
/* 1049:1300 */     for (Iterator<Entity> i = getChildren().iterator(); i.hasNext();)
/* 1050:     */     {
/* 1051:1301 */       Entity child = (Entity)i.next();
/* 1052:1302 */       child.removeParent(this);
/* 1053:     */     }
/* 1054:     */   }
/* 1055:     */   
/* 1056:     */   public void makeLinksFromChildren()
/* 1057:     */   {
/* 1058:1313 */     if (functionP())
/* 1059:     */     {
/* 1060:1314 */       if (!getSubject().getSubjectOf().contains(this)) {
/* 1061:1315 */         getSubject().getSubjectOf().add((Function)this);
/* 1062:     */       }
/* 1063:     */     }
/* 1064:1318 */     else if (relationP())
/* 1065:     */     {
/* 1066:1319 */       if (!getSubject().getSubjectOf().contains(this)) {
/* 1067:1320 */         getSubject().addSubjectOf((Function)this);
/* 1068:     */       }
/* 1069:1322 */       if (!getObject().getObjectOf().contains(this)) {
/* 1070:1323 */         getObject().addObjectOf((Relation)this);
/* 1071:     */       }
/* 1072:     */     }
/* 1073:1326 */     else if (relationP())
/* 1074:     */     {
/* 1075:1328 */       for (int i = 0; i < getElements().size(); i++)
/* 1076:     */       {
/* 1077:1329 */         Entity child = (Entity)getElements().get(i);
/* 1078:1330 */         if (!child.getElementOf().contains(this)) {
/* 1079:1331 */           child.addElementOf((Sequence)this);
/* 1080:     */         }
/* 1081:     */       }
/* 1082:     */     }
/* 1083:     */   }
/* 1084:     */   
/* 1085:     */   public boolean delete()
/* 1086:     */   {
/* 1087:1345 */     boolean result = true;
/* 1088:     */     
/* 1089:1347 */     Vector<Observer> obs = new Vector();
/* 1090:1348 */     obs.addAll(this.observers);
/* 1091:1351 */     for (Iterator<Observer> i = obs.iterator(); i.hasNext();)
/* 1092:     */     {
/* 1093:1352 */       Observer o = (Observer)i.next();
/* 1094:1353 */       if ((o instanceof MemoryForget))
/* 1095:     */       {
/* 1096:1354 */         MemoryForget mf = (MemoryForget)o;
/* 1097:1355 */         result &= mf.forget(this);
/* 1098:     */       }
/* 1099:     */     }
/* 1100:1358 */     breakLinksFromChildren();
/* 1101:1359 */     return result;
/* 1102:     */   }
/* 1103:     */   
/* 1104:     */   public int order()
/* 1105:     */   {
/* 1106:1369 */     if (entityP()) {
/* 1107:1370 */       return 0;
/* 1108:     */     }
/* 1109:1372 */     if (functionP()) {
/* 1110:1373 */       return getSubject().order() + 1;
/* 1111:     */     }
/* 1112:1375 */     if (relationP()) {
/* 1113:1376 */       return Math.max(getObject().order(), getSubject().order()) + 1;
/* 1114:     */     }
/* 1115:1378 */     if (sequenceP())
/* 1116:     */     {
/* 1117:1379 */       int result = 0;
/* 1118:1380 */       Vector<Entity> elements = getElements();
/* 1119:1381 */       for (int i = 0; i < elements.size(); i++) {
/* 1120:1382 */         result = Math.max(result, ((Entity)elements.get(i)).order());
/* 1121:     */       }
/* 1122:1384 */       return result + 1;
/* 1123:     */     }
/* 1124:1387 */     warning("Thing type not recognized.");
/* 1125:1388 */     return -1;
/* 1126:     */   }
/* 1127:     */   
/* 1128:     */   public boolean isFirstOrderThing()
/* 1129:     */   {
/* 1130:1400 */     if (order() == 1) {
/* 1131:1401 */       return true;
/* 1132:     */     }
/* 1133:1405 */     for (Iterator<Entity> i = getChildren().iterator(); i.hasNext();)
/* 1134:     */     {
/* 1135:1406 */       Entity child = (Entity)i.next();
/* 1136:1407 */       if (!child.isZeroOrderThing()) {
/* 1137:1408 */         return false;
/* 1138:     */       }
/* 1139:     */     }
/* 1140:1412 */     return true;
/* 1141:     */   }
/* 1142:     */   
/* 1143:     */   public boolean isZeroOrderThing()
/* 1144:     */   {
/* 1145:1416 */     if (order() == 0) {
/* 1146:1417 */       return true;
/* 1147:     */     }
/* 1148:1419 */     if ((isA("path") | isA("pathElement"))) {
/* 1149:1420 */       return true;
/* 1150:     */     }
/* 1151:1422 */     return false;
/* 1152:     */   }
/* 1153:     */   
/* 1154:     */   public void addExampleOf(Entity example)
/* 1155:     */   {
/* 1156:1429 */     this.exampleOf.add(example);
/* 1157:1430 */     addType(example.getName(), "example");
/* 1158:     */   }
/* 1159:     */   
/* 1160:     */   public Vector<Entity> getExampleOf()
/* 1161:     */   {
/* 1162:1438 */     return this.exampleOf;
/* 1163:     */   }
/* 1164:     */   
/* 1165:     */   public void addModifier(int index, Entity modifier)
/* 1166:     */   {
/* 1167:1442 */     this.modifiers.add(index, modifier);
/* 1168:     */   }
/* 1169:     */   
/* 1170:     */   public Vector<Entity> getModifiers()
/* 1171:     */   {
/* 1172:1446 */     return this.modifiers;
/* 1173:     */   }
/* 1174:     */   
/* 1175:     */   public void addModifier(Entity modifier)
/* 1176:     */   {
/* 1177:1450 */     this.modifiers.add(modifier);
/* 1178:     */   }
/* 1179:     */   
/* 1180:     */   public void setModifiers(Vector<Entity> v)
/* 1181:     */   {
/* 1182:1456 */     for (int i = 0; i < v.size(); i++) {
/* 1183:1457 */       if (!(v.elementAt(i) instanceof Frame)) {
/* 1184:1458 */         System.err.println("Oops, trying to put something into a modifier slot that is not a Frame instance!");
/* 1185:     */       }
/* 1186:     */     }
/* 1187:1461 */     this.modifiers = v;
/* 1188:     */   }
/* 1189:     */   
/* 1190:     */   public void clearModifiers()
/* 1191:     */   {
/* 1192:1465 */     this.modifiers.clear();
/* 1193:     */   }
/* 1194:     */   
/* 1195:     */   public List<Entity> getAllComponents()
/* 1196:     */   {
/* 1197:1472 */     return new LinkedList();
/* 1198:     */   }
/* 1199:     */   
/* 1200:     */   public IdentityHashSet<Entity> getAllFramesShallow()
/* 1201:     */   {
/* 1202:1476 */     IdentityHashSet<Entity> result = new IdentityHashSet();
/* 1203:1477 */     result.addAll(getAllComponents());
/* 1204:1478 */     result.addAll(getModifiers());
/* 1205:1479 */     return result;
/* 1206:     */   }
/* 1207:     */   
/* 1208:     */   public IdentityHashSet<Entity> getAllFramesDeep()
/* 1209:     */   {
/* 1210:1483 */     IdentityHashSet<Entity> result = new IdentityHashSet();
/* 1211:1484 */     getAllFramesDeepHelper(result);
/* 1212:1485 */     return result;
/* 1213:     */   }
/* 1214:     */   
/* 1215:     */   protected void getAllFramesDeepHelper(Set<Entity> result)
/* 1216:     */   {
/* 1217:1489 */     if (!result.contains(this))
/* 1218:     */     {
/* 1219:1490 */       result.add(this);
/* 1220:     */       
/* 1221:1492 */       Iterator<Entity> iComponents = getAllComponents().iterator();
/* 1222:1493 */       while (iComponents.hasNext())
/* 1223:     */       {
/* 1224:1494 */         Entity component = (Entity)iComponents.next();
/* 1225:1495 */         if (component != null) {
/* 1226:1496 */           result.addAll(component.getAllFramesDeep());
/* 1227:     */         }
/* 1228:     */       }
/* 1229:1500 */       Iterator<Entity> iModifiers = getModifiers().iterator();
/* 1230:1501 */       while (iModifiers.hasNext())
/* 1231:     */       {
/* 1232:1502 */         Entity modifier = (Entity)iModifiers.next();
/* 1233:1503 */         if (modifier != null) {
/* 1234:1504 */           result.addAll(modifier.getAllFramesDeep());
/* 1235:     */         }
/* 1236:     */       }
/* 1237:     */     }
/* 1238:     */   }
/* 1239:     */   
/* 1240:     */   public Entity resolvePath(String path)
/* 1241:     */   {
/* 1242:     */     List<String> splitList;
/* 1243:     */     List<String> splitList;
/* 1244:1521 */     if (path.trim().equals(""))
/* 1245:     */     {
/* 1246:1522 */       splitList = new ArrayList();
/* 1247:     */     }
/* 1248:     */     else
/* 1249:     */     {
/* 1250:1525 */       String[] split = path.split("\\.");
/* 1251:1526 */       splitList = Arrays.asList(split);
/* 1252:     */     }
/* 1253:1528 */     return resolvePath(splitList);
/* 1254:     */   }
/* 1255:     */   
/* 1256:     */   public Entity resolvePath(List<String> path)
/* 1257:     */   {
/* 1258:1561 */     if (path.isEmpty()) {
/* 1259:1561 */       return this;
/* 1260:     */     }
/* 1261:1563 */     String pathHead = ((String)path.get(0)).toLowerCase();
/* 1262:1564 */     List<String> pathRest = path.subList(1, path.size());
/* 1263:1566 */     if (((functionP()) || (relationP())) && 
/* 1264:1567 */       (pathHead.startsWith("subject")))
/* 1265:     */     {
/* 1266:1568 */       Entity next = getSubject();
/* 1267:1569 */       if (next == null) {
/* 1268:1569 */         return null;
/* 1269:     */       }
/* 1270:1570 */       return next.resolvePath(pathRest);
/* 1271:     */     }
/* 1272:1574 */     if ((relationP()) && 
/* 1273:1575 */       (pathHead.startsWith("object")))
/* 1274:     */     {
/* 1275:1576 */       Entity next = getObject();
/* 1276:1577 */       if (next == null) {
/* 1277:1577 */         return null;
/* 1278:     */       }
/* 1279:1578 */       return next.resolvePath(pathRest);
/* 1280:     */     }
/* 1281:1582 */     if (sequenceP())
/* 1282:     */     {
/* 1283:1583 */       if (pathHead.startsWith("element"))
/* 1284:     */       {
/* 1285:     */         int which;
/* 1286:     */         int which;
/* 1287:1585 */         if (pathHead.equals("element"))
/* 1288:     */         {
/* 1289:1586 */           which = 0;
/* 1290:     */         }
/* 1291:     */         else
/* 1292:     */         {
/* 1293:1589 */           String whichStr = pathHead.substring(7);
/* 1294:1590 */           which = Integer.parseInt(whichStr);
/* 1295:     */         }
/* 1296:1593 */         if ((which >= 0) && (getElements().size() > which))
/* 1297:     */         {
/* 1298:1594 */           Entity next = (Entity)getElements().get(which);
/* 1299:1595 */           if (next == null) {
/* 1300:1595 */             return null;
/* 1301:     */           }
/* 1302:1596 */           return next.resolvePath(pathRest);
/* 1303:     */         }
/* 1304:     */       }
/* 1305:1600 */       if (pathHead.startsWith("elt"))
/* 1306:     */       {
/* 1307:     */         int which;
/* 1308:     */         int which;
/* 1309:1602 */         if (pathHead.equals("elt"))
/* 1310:     */         {
/* 1311:1603 */           which = 0;
/* 1312:     */         }
/* 1313:     */         else
/* 1314:     */         {
/* 1315:1606 */           String whichStr = pathHead.substring(3);
/* 1316:1607 */           which = Integer.parseInt(whichStr);
/* 1317:     */         }
/* 1318:1610 */         if ((which >= 0) && (getElements().size() > which))
/* 1319:     */         {
/* 1320:1611 */           Entity next = (Entity)getElements().get(which);
/* 1321:1612 */           if (next == null) {
/* 1322:1612 */             return null;
/* 1323:     */           }
/* 1324:1613 */           return next.resolvePath(pathRest);
/* 1325:     */         }
/* 1326:     */       }
/* 1327:     */     }
/* 1328:1618 */     if (pathHead.startsWith("modifier"))
/* 1329:     */     {
/* 1330:     */       int which;
/* 1331:     */       int which;
/* 1332:1620 */       if (pathHead.equals("modifier"))
/* 1333:     */       {
/* 1334:1621 */         which = 0;
/* 1335:     */       }
/* 1336:     */       else
/* 1337:     */       {
/* 1338:1624 */         String whichStr = pathHead.substring(8);
/* 1339:1625 */         which = Integer.parseInt(whichStr);
/* 1340:     */       }
/* 1341:1628 */       if ((which >= 0) && (this.modifiers.size() > which))
/* 1342:     */       {
/* 1343:1629 */         Entity next = (Entity)this.modifiers.get(which);
/* 1344:1630 */         if (next == null) {
/* 1345:1630 */           return null;
/* 1346:     */         }
/* 1347:1631 */         return next.resolvePath(pathRest);
/* 1348:     */       }
/* 1349:     */     }
/* 1350:1635 */     if (pathHead.startsWith("mod"))
/* 1351:     */     {
/* 1352:     */       int which;
/* 1353:     */       int which;
/* 1354:1637 */       if (pathHead.equals("mod"))
/* 1355:     */       {
/* 1356:1638 */         which = 0;
/* 1357:     */       }
/* 1358:     */       else
/* 1359:     */       {
/* 1360:1641 */         String whichStr = pathHead.substring(3);
/* 1361:1642 */         which = Integer.parseInt(whichStr);
/* 1362:     */       }
/* 1363:1645 */       if ((which >= 0) && (this.modifiers.size() > which))
/* 1364:     */       {
/* 1365:1646 */         Entity next = (Entity)this.modifiers.get(which);
/* 1366:1647 */         if (next == null) {
/* 1367:1647 */           return null;
/* 1368:     */         }
/* 1369:1648 */         return next.resolvePath(pathRest);
/* 1370:     */       }
/* 1371:     */     }
/* 1372:1652 */     List<Entity> components = getAllComponents();
/* 1373:1653 */     Iterator<Entity> iComponents = components.iterator();
/* 1374:1654 */     while (iComponents.hasNext())
/* 1375:     */     {
/* 1376:1655 */       Entity component = (Entity)iComponents.next();
/* 1377:1656 */       if ((component != null) && (component.isA(pathHead))) {
/* 1378:1657 */         return component.resolvePath(pathRest);
/* 1379:     */       }
/* 1380:     */     }
/* 1381:1661 */     List<Entity> modifiers = getModifiers();
/* 1382:1662 */     Iterator<Entity> iModifiers = modifiers.iterator();
/* 1383:1663 */     while (iModifiers.hasNext())
/* 1384:     */     {
/* 1385:1664 */       Entity modifier = (Entity)iModifiers.next();
/* 1386:1665 */       if ((modifier != null) && (modifier.isA(pathHead))) {
/* 1387:1666 */         return modifier.resolvePath(pathRest);
/* 1388:     */       }
/* 1389:     */     }
/* 1390:1670 */     return null;
/* 1391:     */   }
/* 1392:     */   
/* 1393:     */   protected String identitySansName(boolean compact)
/* 1394:     */   {
/* 1395:1680 */     String id = "";
/* 1396:1681 */     String b = "";
/* 1397:1682 */     if (getNameSuffix() != null) {
/* 1398:1683 */       id = new Integer(getID()).toString();
/* 1399:     */     }
/* 1400:1685 */     if (getBundle() != null) {
/* 1401:1686 */       b = getBundle().toString(compact);
/* 1402:     */     }
/* 1403:1689 */     if (compact) {
/* 1404:1690 */       return id + "\n" + b + "\n";
/* 1405:     */     }
/* 1406:1693 */     return Tags.tagNoLine("id", id) + b;
/* 1407:     */   }
/* 1408:     */   
/* 1409:     */   protected String identity(boolean compact)
/* 1410:     */   {
/* 1411:1701 */     if (compact) {
/* 1412:1702 */       return getName() + "\n" + getBundle().toString(compact) + "\n";
/* 1413:     */     }
/* 1414:1705 */     return Tags.tagNoLine("name", getName()) + getBundle().toString(compact);
/* 1415:     */   }
/* 1416:     */   
/* 1417:     */   protected String fillerSansName(boolean compact)
/* 1418:     */   {
/* 1419:1716 */     Class<? extends Entity> c = getClass();
/* 1420:1717 */     if (c.equals(Function.class)) {
/* 1421:1718 */       return ((Function)this).fillerSansName(compact);
/* 1422:     */     }
/* 1423:1720 */     if (c.equals(Relation.class)) {
/* 1424:1721 */       return ((Relation)this).fillerSansName(compact);
/* 1425:     */     }
/* 1426:1724 */     return "";
/* 1427:     */   }
/* 1428:     */   
/* 1429:     */   protected String filler(boolean compact)
/* 1430:     */   {
/* 1431:1731 */     Class<? extends Entity> c = getClass();
/* 1432:1732 */     if (c.equals(Function.class)) {
/* 1433:1733 */       return ((Function)this).filler(compact);
/* 1434:     */     }
/* 1435:1735 */     if (c.equals(Relation.class)) {
/* 1436:1736 */       return ((Relation)this).filler(compact);
/* 1437:     */     }
/* 1438:1739 */     return "";
/* 1439:     */   }
/* 1440:     */   
/* 1441:     */   protected String marker(boolean compact)
/* 1442:     */   {
/* 1443:1746 */     return "thing";
/* 1444:     */   }
/* 1445:     */   
/* 1446:     */   public String toXMLSansName(boolean compact)
/* 1447:     */   {
/* 1448:1757 */     if (compact)
/* 1449:     */     {
/* 1450:1758 */       String s = "";
/* 1451:1759 */       s = s + marker(compact) + ": " + identitySansName(compact);
/* 1452:1760 */       String sf = " " + filler(compact).replaceAll("\n", "\n ");
/* 1453:1761 */       s = s + sf;
/* 1454:1762 */       return s.trim();
/* 1455:     */     }
/* 1456:1765 */     return Tags.tag(marker(compact), identitySansName(compact) + fillerSansName(compact));
/* 1457:     */   }
/* 1458:     */   
/* 1459:     */   public String toXML(boolean compact)
/* 1460:     */   {
/* 1461:1774 */     if (compact)
/* 1462:     */     {
/* 1463:1775 */       String s = "";
/* 1464:1776 */       s = s + marker(compact) + ": " + identity(compact);
/* 1465:1777 */       String sf = " " + filler(compact).replaceAll("\n", "\n ");
/* 1466:1778 */       s = s + sf;
/* 1467:1779 */       return s.trim();
/* 1468:     */     }
/* 1469:1782 */     return Tags.tag(marker(compact), identity(compact) + filler(compact));
/* 1470:     */   }
/* 1471:     */   
/* 1472:     */   public char getPrettyPrintType()
/* 1473:     */   {
/* 1474:1791 */     return 'E';
/* 1475:     */   }
/* 1476:     */   
/* 1477:     */   protected void bundleModified(Bundle b, String oldState, String newState)
/* 1478:     */   {
/* 1479:1795 */     finest("\nBundle modified from:\n-- " + oldState + "\n--(to:) " + newState + "\n--");
/* 1480:1796 */     fireNotification();
/* 1481:     */   }
/* 1482:     */   
/* 1483:1802 */   boolean saveStringForm = false;
/* 1484:     */   
/* 1485:     */   public void setSaveStringForm(boolean b)
/* 1486:     */   {
/* 1487:1805 */     this.saveStringForm = b;
/* 1488:     */   }
/* 1489:     */   
/* 1490:     */   public void setSaveStringFormOnDependents(boolean b)
/* 1491:     */   {
/* 1492:1809 */     setSaveStringForm(b);
/* 1493:1810 */     getBundle().setSaveStringFormOnDependents(b);
/* 1494:     */   }
/* 1495:     */   
/* 1496:     */   public boolean getSaveStringForm()
/* 1497:     */   {
/* 1498:1814 */     return this.saveStringForm;
/* 1499:     */   }
/* 1500:     */   
/* 1501:1821 */   boolean notification = false;
/* 1502:     */   protected String previousState;
/* 1503:     */   public static final String LOGGER_GROUP = "frames";
/* 1504:     */   public static final String LOGGER_INSTANCE = "Entity";
/* 1505:     */   public static final String LOGGER = "frames.Entity";
/* 1506:     */   
/* 1507:     */   public void setNotification(boolean b)
/* 1508:     */   {
/* 1509:1830 */     this.notification = b;
/* 1510:     */   }
/* 1511:     */   
/* 1512:     */   public void setNotificationOnDependents(boolean b)
/* 1513:     */   {
/* 1514:1841 */     setNotification(b);
/* 1515:1842 */     getBundle().setNotificationOnDependents(b);
/* 1516:     */   }
/* 1517:     */   
/* 1518:     */   protected void saveState()
/* 1519:     */   {
/* 1520:1857 */     if (this.notification)
/* 1521:     */     {
/* 1522:1858 */       if (this.saveStringForm) {
/* 1523:1859 */         this.previousState = toXMLSansName(false);
/* 1524:     */       }
/* 1525:1861 */       changed(SAVE_STATE);
/* 1526:     */     }
/* 1527:     */   }
/* 1528:     */   
/* 1529:     */   protected void fireNotification()
/* 1530:     */   {
/* 1531:1867 */     if (this.notification) {
/* 1532:1868 */       changed(FIRE_NOTIFICATION);
/* 1533:     */     }
/* 1534:     */   }
/* 1535:     */   
/* 1536:     */   public void changed()
/* 1537:     */   {
/* 1538:1876 */     changed(null);
/* 1539:     */   }
/* 1540:     */   
/* 1541:     */   public void changed(Object o)
/* 1542:     */   {
/* 1543:1883 */     setChanged();
/* 1544:1884 */     notifyObservers(o);
/* 1545:     */   }
/* 1546:     */   
/* 1547:     */   public Entity getCopy()
/* 1548:     */   {
/* 1549:1888 */     Mark.say(
/* 1550:     */     
/* 1551:1890 */       new Object[] { "A" });return getCopy(EntityFactoryDefault.getInstance());
/* 1552:     */   }
/* 1553:     */   
/* 1554:     */   public Entity getCopy(EntityFactory factory)
/* 1555:     */   {
/* 1556:1893 */     Mark.say(
/* 1557:     */     
/* 1558:     */ 
/* 1559:     */ 
/* 1560:     */ 
/* 1561:1898 */       new Object[] { "B" });Entity thing = factory.newThing();Bundle bundle = (Bundle)getBundle().clone();thing.setBundle(bundle);return thing;
/* 1562:     */   }
/* 1563:     */   
/* 1564:     */   public Object clone()
/* 1565:     */   {
/* 1566:1901 */     Entity t = (Entity)clone(EntityFactoryDefault.getInstance());
/* 1567:1902 */     t.setPropertyList(clonePropertyList());
/* 1568:1903 */     return t;
/* 1569:     */   }
/* 1570:     */   
/* 1571:     */   public Object clone(EntityFactory factory)
/* 1572:     */   {
/* 1573:1910 */     Entity thing = factory.newThing();
/* 1574:     */     
/* 1575:1912 */     thing.setPropertyList(clonePropertyList());
/* 1576:     */     
/* 1577:1914 */     Bundle bundle = (Bundle)getBundle().clone();
/* 1578:1915 */     thing.setBundle(bundle);
/* 1579:     */     
/* 1580:1917 */     List<Entity> modifiers = getModifiers();
/* 1581:1918 */     for (int i = 0; i < modifiers.size(); i++)
/* 1582:     */     {
/* 1583:1919 */       Entity t = (Entity)modifiers.get(i);
/* 1584:1920 */       thing.addModifier(t);
/* 1585:     */     }
/* 1586:1922 */     return thing;
/* 1587:     */   }
/* 1588:     */   
/* 1589:     */   public Entity deepClone()
/* 1590:     */   {
/* 1591:1929 */     return deepClone(EntityFactoryDefault.getInstance());
/* 1592:     */   }
/* 1593:     */   
/* 1594:     */   public Entity deepClone(boolean newId)
/* 1595:     */   {
/* 1596:1933 */     return deepClone(EntityFactoryDefault.getInstance(), newId);
/* 1597:     */   }
/* 1598:     */   
/* 1599:     */   public Entity deepClone(EntityFactory factory)
/* 1600:     */   {
/* 1601:1937 */     return deepClone(factory, true);
/* 1602:     */   }
/* 1603:     */   
/* 1604:     */   public Entity deepClone(EntityFactory factory, boolean newId)
/* 1605:     */   {
/* 1606:1941 */     return deepClone(factory, new IdentityHashMap(), newId);
/* 1607:     */   }
/* 1608:     */   
/* 1609:     */   protected Entity deepClone(EntityFactory factory, IdentityHashMap<Entity, Entity> cloneMap, boolean newId)
/* 1610:     */   {
/* 1611:1948 */     if (cloneMap.containsKey(this)) {
/* 1612:1949 */       return (Entity)cloneMap.get(this);
/* 1613:     */     }
/* 1614:1952 */     Entity clone = factory.newThing();
/* 1615:1953 */     if (!newId) {
/* 1616:1954 */       clone.setNameSuffix(getNameSuffix());
/* 1617:     */     }
/* 1618:1957 */     Bundle bundleClone = (Bundle)getBundle().clone();
/* 1619:1958 */     clone.setBundle(bundleClone);
/* 1620:     */     
/* 1621:1960 */     Vector<Entity> modifiers = getModifiers();
/* 1622:1961 */     for (int i = 0; i < modifiers.size(); i++)
/* 1623:     */     {
/* 1624:1962 */       Entity modifier = (Entity)modifiers.elementAt(i);
/* 1625:1963 */       Entity modifierClone = modifier.deepClone(factory, cloneMap, newId);
/* 1626:1964 */       clone.addModifier(modifierClone);
/* 1627:     */     }
/* 1628:1967 */     cloneMap.put(this, clone);
/* 1629:1968 */     return clone;
/* 1630:     */   }
/* 1631:     */   
/* 1632:     */   public Entity cloneForResolver()
/* 1633:     */   {
/* 1634:1976 */     Mark.say(
/* 1635:     */     
/* 1636:1978 */       new Object[] { "J" });return cloneForResolver(EntityFactoryDefault.getInstance());
/* 1637:     */   }
/* 1638:     */   
/* 1639:     */   public Entity cloneForResolver(EntityFactory factory)
/* 1640:     */   {
/* 1641:1981 */     Mark.say(
/* 1642:     */     
/* 1643:     */ 
/* 1644:     */ 
/* 1645:     */ 
/* 1646:     */ 
/* 1647:     */ 
/* 1648:1988 */       new Object[] { "K" });return this;
/* 1649:     */   }
/* 1650:     */   
/* 1651:     */   public int hashCode()
/* 1652:     */   {
/* 1653:1992 */     String hashname = getName();
/* 1654:     */     
/* 1655:     */ 
/* 1656:     */ 
/* 1657:     */ 
/* 1658:     */ 
/* 1659:1998 */     return hashname.hashCode() + 17;
/* 1660:     */   }
/* 1661:     */   
/* 1662:     */   public int hashCodeSansID()
/* 1663:     */   {
/* 1664:2002 */     return getBundle().hashCode();
/* 1665:     */   }
/* 1666:     */   
/* 1667:     */   public boolean isEqual(Object o)
/* 1668:     */   {
/* 1669:2008 */     if ((o instanceof Entity))
/* 1670:     */     {
/* 1671:2009 */       Entity t = (Entity)o;
/* 1672:2010 */       Bundle b = t.getBundle();
/* 1673:2011 */       return (entityP() == t.entityP()) && (functionP() == t.functionP()) && (relationP() == t.relationP()) && 
/* 1674:2012 */         (sequenceP() == t.sequenceP()) && (b.equals(getBundle()));
/* 1675:     */     }
/* 1676:2014 */     return false;
/* 1677:     */   }
/* 1678:     */   
/* 1679:     */   public boolean isDeepEqual(Object o)
/* 1680:     */   {
/* 1681:2021 */     if (!(o instanceof Entity)) {
/* 1682:2021 */       return false;
/* 1683:     */     }
/* 1684:2022 */     Entity t = (Entity)o;
/* 1685:2023 */     Bundle b = t.getBundle();
/* 1686:2024 */     if ((entityP() != t.entityP()) || (functionP() != t.functionP()) || (relationP() != t.relationP()) || 
/* 1687:2025 */       (sequenceP() != t.sequenceP())) {
/* 1688:2026 */       return false;
/* 1689:     */     }
/* 1690:2028 */     if (!getBundle().equals(b)) {
/* 1691:2029 */       return false;
/* 1692:     */     }
/* 1693:2032 */     List<Entity> components = getAllComponents();
/* 1694:2033 */     List<Entity> oComponents = t.getAllComponents();
/* 1695:2034 */     if (components.size() != oComponents.size()) {
/* 1696:2035 */       return false;
/* 1697:     */     }
/* 1698:2037 */     Iterator<Entity> iComponents = components.iterator();
/* 1699:2038 */     Iterator<Entity> iOComponents = oComponents.iterator();
/* 1700:2039 */     while (iComponents.hasNext())
/* 1701:     */     {
/* 1702:2040 */       Entity component = (Entity)iComponents.next();
/* 1703:2041 */       Entity oComponent = (Entity)iOComponents.next();
/* 1704:2042 */       if (!component.isDeepEqual(oComponent)) {
/* 1705:2043 */         return false;
/* 1706:     */       }
/* 1707:     */     }
/* 1708:2047 */     List<Entity> modifiers = getModifiers();
/* 1709:2048 */     List<Entity> oModifiers = t.getModifiers();
/* 1710:2049 */     if (modifiers.size() != oModifiers.size()) {
/* 1711:2050 */       return false;
/* 1712:     */     }
/* 1713:2052 */     Iterator<Entity> imodifiers = modifiers.iterator();
/* 1714:2053 */     Iterator<Entity> iOModifiers = oModifiers.iterator();
/* 1715:2054 */     while (imodifiers.hasNext())
/* 1716:     */     {
/* 1717:2055 */       Entity modifier = (Entity)imodifiers.next();
/* 1718:2056 */       Entity oModifier = (Entity)iOModifiers.next();
/* 1719:2057 */       if (!modifier.isDeepEqual(oModifier)) {
/* 1720:2058 */         return false;
/* 1721:     */       }
/* 1722:     */     }
/* 1723:2062 */     return true;
/* 1724:     */   }
/* 1725:     */   
/* 1726:     */   public boolean equals(Object o)
/* 1727:     */   {
/* 1728:2068 */     if (super.equals(o)) {
/* 1729:2069 */       return true;
/* 1730:     */     }
/* 1731:2071 */     return (isDeepEqual(o)) && (getName().equals(((Entity)o).getName()));
/* 1732:     */   }
/* 1733:     */   
/* 1734:     */   public String toXML()
/* 1735:     */   {
/* 1736:2078 */     return toXML(false);
/* 1737:     */   }
/* 1738:     */   
/* 1739:     */   public void deleteObservers()
/* 1740:     */   {
/* 1741:2086 */     this.observers.removeAllElements();
/* 1742:2087 */     super.deleteObservers();
/* 1743:     */   }
/* 1744:     */   
/* 1745:     */   public void addObserver(Observer o)
/* 1746:     */   {
/* 1747:2091 */     this.observers.addElement(o);
/* 1748:2092 */     super.addObserver(o);
/* 1749:     */   }
/* 1750:     */   
/* 1751:     */   public void deleteObserver(Observer o)
/* 1752:     */   {
/* 1753:2096 */     this.observers.removeElement(o);
/* 1754:2097 */     super.deleteObserver(o);
/* 1755:     */   }
/* 1756:     */   
/* 1757:     */   public static String toXMLNamesOnly(Collection<Entity> entities)
/* 1758:     */   {
/* 1759:2105 */     StringBuffer result = new StringBuffer();
/* 1760:2106 */     result.append("[");
/* 1761:2108 */     for (Iterator<Entity> i = entities.iterator(); i.hasNext();)
/* 1762:     */     {
/* 1763:2109 */       Entity thing = (Entity)i.next();
/* 1764:2110 */       if (thing != null) {
/* 1765:2111 */         result.append(thing.getName());
/* 1766:     */       } else {
/* 1767:2114 */         result.append("null");
/* 1768:     */       }
/* 1769:2116 */       result.append(", ");
/* 1770:     */     }
/* 1771:2119 */     if (result.length() > 2)
/* 1772:     */     {
/* 1773:2120 */       result.deleteCharAt(result.length() - 1);
/* 1774:2121 */       result.deleteCharAt(result.length() - 1);
/* 1775:     */     }
/* 1776:2123 */     result.append("]");
/* 1777:2124 */     return result.toString();
/* 1778:     */   }
/* 1779:     */   
/* 1780:     */   public static Vector<String> getClassesFromString(String s)
/* 1781:     */   {
/* 1782:2135 */     Vector<String> result = new Vector();
/* 1783:2136 */     if (s == null) {
/* 1784:2137 */       return result;
/* 1785:     */     }
/* 1786:2140 */     s = Tags.untagString("bundle", s);
/* 1787:     */     
/* 1788:2142 */     int i = 1;
/* 1789:     */     String t;
/* 1790:2146 */     while ((t = Tags.untagString("thread", s, i)) != null)
/* 1791:     */     {
/* 1792:2148 */       int begin = 0;
/* 1793:2149 */       String t = t.trim();
/* 1794:2151 */       while (t.indexOf("features") < 0)
/* 1795:     */       {
/* 1796:2154 */         int end = t.indexOf(' ', begin);
/* 1797:2155 */         if (end < 0) {
/* 1798:2156 */           end = t.length();
/* 1799:     */         }
/* 1800:2159 */         String sub = t.substring(begin, end);
/* 1801:2160 */         sub = sub.trim();
/* 1802:2162 */         if (sub != "") {
/* 1803:2163 */           result.add(sub);
/* 1804:     */         }
/* 1805:2166 */         if (end == t.length()) {
/* 1806:     */           break;
/* 1807:     */         }
/* 1808:2169 */         begin = end + 1;
/* 1809:     */       }
/* 1810:2171 */       i++;
/* 1811:     */     }
/* 1812:2174 */     return result;
/* 1813:     */   }
/* 1814:     */   
/* 1815:     */   public static String getPrintStringFromCollection(Collection<Entity> things)
/* 1816:     */   {
/* 1817:2178 */     StringBuffer result = new StringBuffer();
/* 1818:2179 */     result.append("[");
/* 1819:2181 */     for (Iterator<Entity> i = things.iterator(); i.hasNext();)
/* 1820:     */     {
/* 1821:2182 */       Entity thing = (Entity)i.next();
/* 1822:2183 */       result.append(thing.getName());
/* 1823:2184 */       if (i.hasNext()) {
/* 1824:2184 */         result.append(", ");
/* 1825:     */       }
/* 1826:     */     }
/* 1827:2186 */     result.append("]");
/* 1828:2187 */     return result.toString();
/* 1829:     */   }
/* 1830:     */   
/* 1831:     */   public static void printNamesFromCollection(Collection<Entity> things)
/* 1832:     */   {
/* 1833:2191 */     System.out.println(getPrintStringFromCollection(things));
/* 1834:     */   }
/* 1835:     */   
/* 1836:     */   public static String showDifferences(Entity first, Entity second)
/* 1837:     */   {
/* 1838:2196 */     String result = null;
/* 1839:     */     
/* 1840:2198 */     String firstClass = first.getClass().toString();
/* 1841:2199 */     String secondClass = second.getClass().toString();
/* 1842:2201 */     if (!firstClass.equals(secondClass))
/* 1843:     */     {
/* 1844:2202 */       if (result == null) {
/* 1845:2203 */         result = "";
/* 1846:     */       }
/* 1847:2205 */       result = "Entity diff: " + firstClass + " != " + secondClass + "\n";
/* 1848:     */     }
/* 1849:     */     else
/* 1850:     */     {
/* 1851:2208 */       if (first.relationP())
/* 1852:     */       {
/* 1853:2209 */         Relation f = (Relation)first;
/* 1854:2210 */         Relation s = (Relation)second;
/* 1855:     */         
/* 1856:2212 */         String diff = showDifferences(f.getSubject(), s.getSubject());
/* 1857:2213 */         if (diff != null)
/* 1858:     */         {
/* 1859:2214 */           if (result == null) {
/* 1860:2215 */             result = "";
/* 1861:     */           }
/* 1862:2217 */           result = result + "Entity diff: " + diff + "\n";
/* 1863:     */         }
/* 1864:2220 */         diff = showDifferences(f.getObject(), s.getObject());
/* 1865:2221 */         if (diff != null)
/* 1866:     */         {
/* 1867:2222 */           if (result == null) {
/* 1868:2223 */             result = "";
/* 1869:     */           }
/* 1870:2225 */           result = result + "Entity diff: " + diff + "\n";
/* 1871:     */         }
/* 1872:     */       }
/* 1873:2229 */       else if (first.functionP())
/* 1874:     */       {
/* 1875:2230 */         Function f = (Function)first;
/* 1876:2231 */         Function s = (Function)second;
/* 1877:2232 */         String diff = showDifferences(f.getSubject(), s.getSubject());
/* 1878:2233 */         if (diff != null)
/* 1879:     */         {
/* 1880:2234 */           if (result == null) {
/* 1881:2235 */             result = "";
/* 1882:     */           }
/* 1883:2237 */           result = result + "Entity diff: " + diff + "\n";
/* 1884:     */         }
/* 1885:     */       }
/* 1886:2240 */       else if (first.sequenceP())
/* 1887:     */       {
/* 1888:2241 */         Sequence f = (Sequence)first;
/* 1889:2242 */         Sequence s = (Sequence)second;
/* 1890:2243 */         if (f.getElements().size() != s.getElements().size())
/* 1891:     */         {
/* 1892:2244 */           if (result == null) {
/* 1893:2245 */             result = "";
/* 1894:     */           }
/* 1895:2247 */           result = result + "Entity diff: " + f.getName() + " and " + s.getName() + "\nare of different sizes\n";
/* 1896:     */         }
/* 1897:     */         else
/* 1898:     */         {
/* 1899:2250 */           for (int i = 0; i < first.getElements().size(); i++)
/* 1900:     */           {
/* 1901:2251 */             String diff = showDifferences((Entity)first.getElements().get(i), (Entity)second.getElements().get(i));
/* 1902:2252 */             if (diff != null)
/* 1903:     */             {
/* 1904:2253 */               if (result == null) {
/* 1905:2254 */                 result = "";
/* 1906:     */               }
/* 1907:2256 */               result = result + "Entity diff: " + diff + "\n";
/* 1908:     */             }
/* 1909:     */           }
/* 1910:     */         }
/* 1911:     */       }
/* 1912:2263 */       String diff = Bundle.showDifferences(first.getBundle(), second.getBundle());
/* 1913:2264 */       if (diff != null)
/* 1914:     */       {
/* 1915:2265 */         if (result == null) {
/* 1916:2266 */           result = "";
/* 1917:     */         }
/* 1918:2268 */         result = result + "Entity diff: " + diff + "\n";
/* 1919:     */       }
/* 1920:     */     }
/* 1921:2271 */     return result;
/* 1922:     */   }
/* 1923:     */   
/* 1924:     */   public Entity limitTo(String type)
/* 1925:     */   {
/* 1926:2275 */     Bundle b = getBundle();
/* 1927:2276 */     b = b.filterFor(type);
/* 1928:2277 */     setBundle(b);
/* 1929:2278 */     return this;
/* 1930:     */   }
/* 1931:     */   
/* 1932:     */   public Entity limitToRoot(String type)
/* 1933:     */   {
/* 1934:2282 */     Bundle b = getBundle();
/* 1935:2283 */     b = b.filterForRoot(type);
/* 1936:2284 */     setBundle(b);
/* 1937:2285 */     return this;
/* 1938:     */   }
/* 1939:     */   
/* 1940:     */   public static Logger getLogger()
/* 1941:     */   {
/* 1942:2298 */     return Logger.getLogger("frames.Entity");
/* 1943:     */   }
/* 1944:     */   
/* 1945:     */   protected static void finest(Object s)
/* 1946:     */   {
/* 1947:2302 */     Logger.getLogger("frames.Entity").finest("Entity: " + s);
/* 1948:     */   }
/* 1949:     */   
/* 1950:     */   protected static void finer(Object s)
/* 1951:     */   {
/* 1952:2306 */     Logger.getLogger("frames.Entity").finer("Entity: " + s);
/* 1953:     */   }
/* 1954:     */   
/* 1955:     */   protected static void fine(Object s)
/* 1956:     */   {
/* 1957:2310 */     Logger.getLogger("frames.Entity").fine("Entity: " + s);
/* 1958:     */   }
/* 1959:     */   
/* 1960:     */   protected static void config(Object s)
/* 1961:     */   {
/* 1962:2314 */     Logger.getLogger("frames.Entity").config("Entity: " + s);
/* 1963:     */   }
/* 1964:     */   
/* 1965:     */   protected static void info(Object s)
/* 1966:     */   {
/* 1967:2318 */     Logger.getLogger("frames.Entity").info("Entity: " + s);
/* 1968:     */   }
/* 1969:     */   
/* 1970:     */   protected static void warning(Object s)
/* 1971:     */   {
/* 1972:2322 */     Logger.getLogger("frames.Entity").warning("Entity: " + s);
/* 1973:     */   }
/* 1974:     */   
/* 1975:     */   protected static void severe(Object s)
/* 1976:     */   {
/* 1977:2326 */     Logger.getLogger("frames.Entity").severe("Entity: " + s);
/* 1978:     */   }
/* 1979:     */   
/* 1980:     */   public String asString()
/* 1981:     */   {
/* 1982:2330 */     boolean not = hasFeature("not");
/* 1983:2331 */     if (relationP()) {
/* 1984:2332 */       return "(rel " + (not ? "not " : "") + getType() + " " + getSubject().asString() + " " + getObject().asString() + ")";
/* 1985:     */     }
/* 1986:2334 */     if (functionP()) {
/* 1987:2335 */       return "(fun " + (not ? "not " : "") + getType() + " " + getSubject().asString() + ")";
/* 1988:     */     }
/* 1989:2337 */     if (sequenceP())
/* 1990:     */     {
/* 1991:2338 */       String result = "(" + (not ? "not " : "") + "seq ";
/* 1992:2339 */       result = result + getType();
/* 1993:2340 */       for (Entity t : ((Sequence)this).getElements()) {
/* 1994:2341 */         result = result + " " + t.asString();
/* 1995:     */       }
/* 1996:2343 */       result = result + ")";
/* 1997:2344 */       return result;
/* 1998:     */     }
/* 1999:2347 */     return "(ent " + (not ? "not " : "") + getName() + ")";
/* 2000:     */   }
/* 2001:     */   
/* 2002:     */   public String toString()
/* 2003:     */   {
/* 2004:2352 */     return asString();
/* 2005:     */   }
/* 2006:     */   
/* 2007:     */   public String hash()
/* 2008:     */   {
/* 2009:2359 */     boolean not = hasFeature("not");
/* 2010:2361 */     if (relationP()) {
/* 2011:2362 */       return "(" + (not ? "not " : "") + getType() + " " + getSubject().hash() + " " + getObject().hash() + ")";
/* 2012:     */     }
/* 2013:2364 */     if (functionP()) {
/* 2014:2365 */       return "(" + (not ? "not " : "") + getType() + " " + getSubject().hash() + ")";
/* 2015:     */     }
/* 2016:2367 */     if (sequenceP())
/* 2017:     */     {
/* 2018:2368 */       String result = "(" + (not ? "not " : "") + "sequence ";
/* 2019:2369 */       result = result + getType();
/* 2020:2370 */       for (Entity t : ((Sequence)this).getElements()) {
/* 2021:2371 */         result = result + " " + t.hash();
/* 2022:     */       }
/* 2023:2373 */       result = result + ")";
/* 2024:2374 */       return result;
/* 2025:     */     }
/* 2026:2378 */     ArrayList<Object> features = getFeatures();
/* 2027:2379 */     Object owner = getProperty("owner");
/* 2028:2380 */     if (owner != null) {
/* 2029:2381 */       features.add(owner);
/* 2030:     */     }
/* 2031:2383 */     String result = "";
/* 2032:2384 */     if (features != null) {
/* 2033:2385 */       for (Object feature : features) {
/* 2034:2386 */         result = result + feature.toString() + " ";
/* 2035:     */       }
/* 2036:     */     }
/* 2037:2391 */     result = result + (not ? "not " : "") + getType();
/* 2038:     */     
/* 2039:2393 */     return result;
/* 2040:     */   }
/* 2041:     */   
/* 2042:     */   public String hashIncludingThings()
/* 2043:     */   {
/* 2044:2401 */     boolean not = hasFeature("not");
/* 2045:2403 */     if (relationP()) {
/* 2046:2404 */       return 
/* 2047:2405 */         "(" + (not ? "not " : "") + getType() + " " + getSubject().hashIncludingThings() + " " + getObject().hashIncludingThings() + ")";
/* 2048:     */     }
/* 2049:2407 */     if (functionP()) {
/* 2050:2408 */       return "(" + (not ? "not " : "") + getType() + " " + getSubject().hashIncludingThings() + ")";
/* 2051:     */     }
/* 2052:2410 */     if (sequenceP())
/* 2053:     */     {
/* 2054:2411 */       String result = "(" + (not ? "not " : "") + "sequence ";
/* 2055:2412 */       result = result + getType();
/* 2056:2413 */       for (Entity t : ((Sequence)this).getElements()) {
/* 2057:2414 */         result = result + " " + t.hashIncludingThings();
/* 2058:     */       }
/* 2059:2416 */       result = result + ")";
/* 2060:2417 */       return result;
/* 2061:     */     }
/* 2062:2420 */     return (not ? "not " : "") + getType();
/* 2063:     */   }
/* 2064:     */   
/* 2065:     */   public String asStringWithoutIndexes()
/* 2066:     */   {
/* 2067:2426 */     return hash();
/* 2068:     */   }
/* 2069:     */   
/* 2070:     */   public String asStringWithIndexes()
/* 2071:     */   {
/* 2072:2430 */     if (relationP()) {
/* 2073:2431 */       return "(r " + getName() + " " + getSubject().asStringWithIndexes() + " " + getObject().asStringWithIndexes() + ")";
/* 2074:     */     }
/* 2075:2433 */     if (functionP()) {
/* 2076:2434 */       return "(d " + getName() + " " + getSubject().asStringWithIndexes() + ")";
/* 2077:     */     }
/* 2078:2436 */     if (sequenceP())
/* 2079:     */     {
/* 2080:2437 */       String result = "(s ";
/* 2081:2438 */       result = result + getName();
/* 2082:2439 */       for (Entity t : ((Sequence)this).getElements()) {
/* 2083:2440 */         result = result + " " + t.asStringWithIndexes();
/* 2084:     */       }
/* 2085:2442 */       result = result + ")";
/* 2086:2443 */       return result;
/* 2087:     */     }
/* 2088:2446 */     return "(t " + getName() + ")";
/* 2089:     */   }
/* 2090:     */   
/* 2091:     */   public static Entity reader(String s)
/* 2092:     */   {
/* 2093:2454 */     if (s.isEmpty())
/* 2094:     */     {
/* 2095:2455 */       Mark.say(new Object[] {"Sexp bug 1" });
/* 2096:2456 */       return null;
/* 2097:     */     }
/* 2098:2459 */     if (s.startsWith("(t "))
/* 2099:     */     {
/* 2100:2462 */       int match = indexOfMatchingParenthesis(0, s);
/* 2101:2463 */       Thread thread = makeThread(s.substring(2, match).trim());
/* 2102:2464 */       Entity t = new Entity(thread);
/* 2103:     */       
/* 2104:2466 */       return t;
/* 2105:     */     }
/* 2106:2468 */     if (s.startsWith("(d "))
/* 2107:     */     {
/* 2108:2470 */       int first = indexOfNextParenthesis(1, s);
/* 2109:2471 */       Thread thread = makeThread(s.substring(2, first).trim());
/* 2110:2472 */       Function d = new Function(thread, reader(s.substring(first, 1 + indexOfMatchingParenthesis(first, s)).trim()));
/* 2111:     */       
/* 2112:2474 */       return d;
/* 2113:     */     }
/* 2114:2476 */     if (s.startsWith("(r "))
/* 2115:     */     {
/* 2116:2478 */       int first = indexOfNextParenthesis(1, s);
/* 2117:2479 */       int match1 = indexOfMatchingParenthesis(first, s);
/* 2118:2480 */       int second = indexOfNextParenthesis(match1 + 1, s);
/* 2119:2481 */       int match2 = indexOfMatchingParenthesis(second, s);
/* 2120:2482 */       Thread thread = makeThread(s.substring(2, first).trim());
/* 2121:2483 */       Relation r = new Relation(thread, reader(s.substring(first, match1 + 1)), reader(s.substring(second, match2 + 1)));
/* 2122:     */       
/* 2123:2485 */       return r;
/* 2124:     */     }
/* 2125:2487 */     if (s.startsWith("(s "))
/* 2126:     */     {
/* 2127:2489 */       int first = indexOfNextParenthesis(1, s);
/* 2128:2490 */       Thread thread = makeThread(s.substring(2, first).trim());
/* 2129:2491 */       Sequence sequence = new Sequence(thread);
/* 2130:2492 */       int next = first;
/* 2131:2493 */       int match = indexOfMatchingParenthesis(next, s);
/* 2132:     */       for (;;)
/* 2133:     */       {
/* 2134:2495 */         if (first < 0) {
/* 2135:2497 */           return sequence;
/* 2136:     */         }
/* 2137:2499 */         Entity t = reader(s.substring(first, match));
/* 2138:2500 */         sequence.addElement(t);
/* 2139:2501 */         first = indexOfNextParenthesis(match + 1, s);
/* 2140:2502 */         if (first < 0) {
/* 2141:2504 */           return sequence;
/* 2142:     */         }
/* 2143:2506 */         match = indexOfMatchingParenthesis(first, s);
/* 2144:     */       }
/* 2145:     */     }
/* 2146:2509 */     return null;
/* 2147:     */   }
/* 2148:     */   
/* 2149:     */   public static Thread makeThread(String s)
/* 2150:     */   {
/* 2151:2513 */     String[] split = s.split(" ");
/* 2152:2514 */     Thread thread = new Thread();
/* 2153:2515 */     for (String x : split) {
/* 2154:2516 */       thread.add(x);
/* 2155:     */     }
/* 2156:2518 */     return thread;
/* 2157:     */   }
/* 2158:     */   
/* 2159:     */   private static int indexOfNextParenthesis(int start, String s)
/* 2160:     */   {
/* 2161:2522 */     StringBuffer b = new StringBuffer(s);
/* 2162:2523 */     int count = 0;
/* 2163:2524 */     for (int i = start; i < b.length(); i++)
/* 2164:     */     {
/* 2165:2525 */       if (b.charAt(i) == '(') {
/* 2166:2526 */         return i;
/* 2167:     */       }
/* 2168:2528 */       if (b.charAt(i) == ')')
/* 2169:     */       {
/* 2170:2529 */         count--;
/* 2171:     */       }
/* 2172:2531 */       else if (count < 0)
/* 2173:     */       {
/* 2174:2532 */         Mark.say(new Object[] {"Sexp bug 4" });
/* 2175:2533 */         return -1;
/* 2176:     */       }
/* 2177:     */     }
/* 2178:2537 */     return -1;
/* 2179:     */   }
/* 2180:     */   
/* 2181:     */   private static int indexOfMatchingParenthesis(int start, String s)
/* 2182:     */   {
/* 2183:2541 */     StringBuffer b = new StringBuffer(s);
/* 2184:2542 */     int count = 0;
/* 2185:2543 */     for (int i = start; i < b.length(); i++)
/* 2186:     */     {
/* 2187:2544 */       if (b.charAt(i) == '(') {
/* 2188:2545 */         count++;
/* 2189:2547 */       } else if (b.charAt(i) == ')') {
/* 2190:2548 */         count--;
/* 2191:     */       }
/* 2192:2550 */       if (count == 0) {
/* 2193:2551 */         return i;
/* 2194:     */       }
/* 2195:2553 */       if (count < 0)
/* 2196:     */       {
/* 2197:2554 */         Mark.say(new Object[] {"Sexp bug 3" });
/* 2198:2555 */         return -1;
/* 2199:     */       }
/* 2200:     */     }
/* 2201:2558 */     Mark.say(new Object[] {"Sexp bug 3" });
/* 2202:2559 */     return -1;
/* 2203:     */   }
/* 2204:     */   
/* 2205:     */   public String toEnglish()
/* 2206:     */   {
/* 2207:2563 */     String english = "";
/* 2208:2564 */     english = Generator.getGenerator().generate(this);
/* 2209:2565 */     if (english == null) {
/* 2210:2565 */       return toString();
/* 2211:     */     }
/* 2212:2566 */     return english.trim();
/* 2213:     */   }
/* 2214:     */   
/* 2215:     */   public static void main(String[] argv)
/* 2216:     */   {
/* 2217:2575 */     Relation r = new Relation("kill", new Entity("John"), new Entity("Mary"));
/* 2218:2576 */     r.addType("shot");
/* 2219:     */     
/* 2220:2578 */     Mark.say(new Object[] {"Result of construction:", r });
/* 2221:2579 */     Mark.say(new Object[] {"Result of getSubject:", r.getSubject() });
/* 2222:2580 */     Mark.say(new Object[] {"Result of getObject:", r.getObject() });
/* 2223:     */     
/* 2224:     */ 
/* 2225:2583 */     Mark.say(new Object[] {"Result of getElements:", r.getElements() });
/* 2226:2584 */     Mark.say(new Object[] {"Result of getType:", r.getType() });
/* 2227:     */     
/* 2228:2586 */     Mark.say(new Object[] {"Result of getBundle:", r.getBundle() });
/* 2229:     */     
/* 2230:2588 */     Mark.say(new Object[] {"Result of getPrimedThread:", r.getPrimedThread() });
/* 2231:     */     
/* 2232:     */ 
/* 2233:2591 */     r.addProperty("test", "hello world");
/* 2234:2592 */     Mark.say(new Object[] {"The stored property is", r.getProperty("test") });
/* 2235:2593 */     r.addProperty("number", Double.valueOf(3.14D));
/* 2236:2594 */     Mark.say(new Object[] {"The stored property is", r.getProperty("number") });
/* 2237:     */   }
/* 2238:     */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.Entity
 * JD-Core Version:    0.7.0.1
 */