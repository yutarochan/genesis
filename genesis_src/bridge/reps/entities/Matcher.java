/*    1:     */ package bridge.reps.entities;
/*    2:     */ 
/*    3:     */ import bridge.utils.logging.Logger;
/*    4:     */ import java.io.PrintStream;
/*    5:     */ import java.util.ArrayList;
/*    6:     */ import java.util.Vector;
/*    7:     */ import minilisp.LList;
/*    8:     */ import tools.JFactory;
/*    9:     */ import utils.PairOfEntities;
/*   10:     */ 
/*   11:     */ public class Matcher
/*   12:     */ {
/*   13:     */   private static final int minimumTypeMatch = 1;
/*   14:     */   private static final int minimumSequenceMatch = 1;
/*   15:     */   
/*   16:     */   public static BindingSet match(Entity x, Entity y)
/*   17:     */   {
/*   18:  17 */     return match(x, y, new BindingSet(), false);
/*   19:     */   }
/*   20:     */   
/*   21:     */   public static BindingSet forceMatch(Entity x, Entity y)
/*   22:     */   {
/*   23:  25 */     return match(x, y, new BindingSet(), true);
/*   24:     */   }
/*   25:     */   
/*   26:     */   public static BindingSet match(Entity pattern, Entity datum, BindingSet bindings, boolean force)
/*   27:     */   {
/*   28:  33 */     fine("Trying to match " + pattern.getTypes() + " with " + datum.getTypes());
/*   29:  36 */     if (bindings == null) {
/*   30:  37 */       return null;
/*   31:     */     }
/*   32:  41 */     if ((!pattern.getClass().equals(datum.getClass())) && (!pattern.isA("?")) && (!datum.isA("?"))) {
/*   33:  42 */       return null;
/*   34:     */     }
/*   35:  47 */     Entity substitution = fetchValue(pattern, bindings);
/*   36:  48 */     if (substitution != null) {
/*   37:  49 */       return match(substitution, datum, bindings, force);
/*   38:     */     }
/*   39:  53 */     Vector<String> patternTypes = pattern.getMatcherTypes();
/*   40:  54 */     Vector<String> datumTypes = datum.getMatcherTypes();
/*   41:  55 */     Vector<String> excess = vectorDifference(patternTypes, datumTypes);
/*   42:  56 */     excess.remove("?");
/*   43:  57 */     excess.remove("description");
/*   44:  60 */     if ((excess.isEmpty()) || (force))
/*   45:     */     {
/*   46:  61 */       if (patternTypes.contains("?"))
/*   47:     */       {
/*   48:  63 */         bindings.add(new Binding(pattern, datum));
/*   49:  64 */         return bindings;
/*   50:     */       }
/*   51:  67 */       if ((force) && (!excess.isEmpty()))
/*   52:     */       {
/*   53:  68 */         System.out.println("Forcing match by adding " + excess);
/*   54:  69 */         bindings.incrementForcedTypeCount(excess.size());
/*   55:  70 */         datum.addTypes(excess);
/*   56:     */       }
/*   57:     */     }
/*   58:     */     else
/*   59:     */     {
/*   60:  75 */       fine("Failed to match " + pattern.getTypes() + " with " + datum.getTypes());
/*   61:  76 */       return null;
/*   62:     */     }
/*   63:  81 */     if ((pattern.functionP()) && (datum.functionP())) {
/*   64:  82 */       return match(pattern.getSubject(), datum.getSubject(), bindings, force);
/*   65:     */     }
/*   66:  87 */     if ((pattern.relationP()) && (datum.relationP()))
/*   67:     */     {
/*   68:  89 */       bindings = match(((Relation)pattern).getSubject(), ((Relation)datum).getSubject(), bindings, force);
/*   69:  90 */       if (bindings == null) {
/*   70:  91 */         return null;
/*   71:     */       }
/*   72:  95 */       return match(pattern.getObject(), datum.getObject(), bindings, force);
/*   73:     */     }
/*   74: 104 */     if (((pattern instanceof Sequence)) && ((datum instanceof Sequence)))
/*   75:     */     {
/*   76: 105 */       Vector patternElements = ((Sequence)pattern).getElements();
/*   77: 106 */       Vector datumElements = ((Sequence)datum).getElements();
/*   78: 108 */       if (patternElements.size() > datumElements.size()) {
/*   79: 109 */         return null;
/*   80:     */       }
/*   81: 111 */       int successes = -1;
/*   82: 112 */       for (int i = 0; i < patternElements.size(); i++)
/*   83:     */       {
/*   84: 113 */         Entity patternElement = (Entity)patternElements.elementAt(i);
/*   85: 114 */         for (int j = 0; j < datumElements.size(); j++)
/*   86:     */         {
/*   87: 115 */           Entity datumElement = (Entity)datumElements.elementAt(j);
/*   88: 116 */           fine("Trying to match " + patternElement.getTypes() + " with " + datumElement.getTypes());
/*   89: 117 */           if (bindings == null) {
/*   90:     */             break;
/*   91:     */           }
/*   92: 120 */           BindingSet trialMatch = match(patternElement, datumElement, bindings, force);
/*   93: 121 */           if (trialMatch != null)
/*   94:     */           {
/*   95: 122 */             bindings = trialMatch;
/*   96: 123 */             successes++;
/*   97: 124 */             break;
/*   98:     */           }
/*   99:     */         }
/*  100: 127 */         if (successes < i) {
/*  101: 128 */           return null;
/*  102:     */         }
/*  103:     */       }
/*  104: 131 */       return bindings;
/*  105:     */     }
/*  106: 133 */     if (bindings == null) {
/*  107: 134 */       fine("Failed to match " + pattern.getTypes() + " with " + datum.getTypes());
/*  108:     */     }
/*  109: 136 */     return bindings;
/*  110:     */   }
/*  111:     */   
/*  112:     */   private static Entity fetchValue(Entity x, Vector bindingVector)
/*  113:     */   {
/*  114: 143 */     for (int i = 0; i < bindingVector.size(); i++)
/*  115:     */     {
/*  116: 144 */       Binding binding = (Binding)bindingVector.elementAt(i);
/*  117: 145 */       Entity variable = (Entity)binding.getVariable();
/*  118: 146 */       if (x.equals(variable)) {
/*  119: 148 */         return (Entity)binding.getValue();
/*  120:     */       }
/*  121:     */     }
/*  122: 151 */     return null;
/*  123:     */   }
/*  124:     */   
/*  125:     */   private static Entity fetchByValue(Entity x, LList<PairOfEntities> bindingVector)
/*  126:     */   {
/*  127: 155 */     for (Object object : bindingVector)
/*  128:     */     {
/*  129: 156 */       PairOfEntities pairOfThings = (PairOfEntities)object;
/*  130: 157 */       if (pairOfThings.getPattern() == x) {
/*  131: 158 */         return pairOfThings.getDatum();
/*  132:     */       }
/*  133:     */     }
/*  134: 161 */     return null;
/*  135:     */   }
/*  136:     */   
/*  137:     */   private static Entity fetchVariable(Entity x, Vector bindingVector)
/*  138:     */   {
/*  139: 168 */     for (int i = 0; i < bindingVector.size(); i++)
/*  140:     */     {
/*  141: 169 */       Binding binding = (Binding)bindingVector.elementAt(i);
/*  142: 170 */       Entity value = (Entity)binding.getValue();
/*  143: 171 */       if (x.equals(value)) {
/*  144: 173 */         return (Entity)binding.getVariable();
/*  145:     */       }
/*  146:     */     }
/*  147: 176 */     return null;
/*  148:     */   }
/*  149:     */   
/*  150:     */   private static <T> Vector<T> vectorDifference(Vector<T> v1, Vector<T> v2)
/*  151:     */   {
/*  152: 183 */     Vector<T> result = new Vector();
/*  153: 184 */     for (int i = 0; i < v1.size(); i++)
/*  154:     */     {
/*  155: 185 */       T object = v1.elementAt(i);
/*  156: 186 */       if (!v2.contains(object)) {
/*  157: 189 */         result.add(object);
/*  158:     */       }
/*  159:     */     }
/*  160: 192 */     return result;
/*  161:     */   }
/*  162:     */   
/*  163:     */   private static <T> Vector<T> vectorIntersection(Vector<T> v1, Vector<T> v2)
/*  164:     */   {
/*  165: 196 */     Vector<T> result = new Vector();
/*  166: 197 */     for (int i = 0; i < v1.size(); i++)
/*  167:     */     {
/*  168: 198 */       T object = v1.elementAt(i);
/*  169: 199 */       if (v2.contains(object)) {
/*  170: 200 */         result.add(object);
/*  171:     */       }
/*  172:     */     }
/*  173: 203 */     return result;
/*  174:     */   }
/*  175:     */   
/*  176:     */   public static BindingSet exactMatch(Entity pattern, Entity datum)
/*  177:     */   {
/*  178: 210 */     return exactMatch(pattern, datum, false);
/*  179:     */   }
/*  180:     */   
/*  181:     */   public static BindingSet exactMatch(Entity x, Entity y, boolean matchModifiers)
/*  182:     */   {
/*  183: 214 */     BindingSet bs = null;
/*  184: 215 */     if ((x == null) && (y == null)) {
/*  185: 216 */       return new BindingSet();
/*  186:     */     }
/*  187: 218 */     if ((x == null) || (y == null)) {
/*  188: 219 */       return null;
/*  189:     */     }
/*  190:     */     try
/*  191:     */     {
/*  192: 222 */       bs = exactMatch(x, y, new BindingSet(), matchModifiers);
/*  193:     */     }
/*  194:     */     catch (Exception e)
/*  195:     */     {
/*  196: 225 */       e.printStackTrace();
/*  197:     */     }
/*  198: 227 */     return bs;
/*  199:     */   }
/*  200:     */   
/*  201:     */   public static BindingSet exactMatch(Entity pattern, Entity datum, BindingSet bindings)
/*  202:     */   {
/*  203: 235 */     return exactMatch(pattern, datum, bindings, false);
/*  204:     */   }
/*  205:     */   
/*  206:     */   public static BindingSet exactMatch(Entity pattern, Entity datum, BindingSet bindings, boolean matchModifiers)
/*  207:     */   {
/*  208: 239 */     fine("Trying to match " + pattern.getTypes() + " with " + datum.getTypes());
/*  209: 242 */     if (bindings == null) {
/*  210: 243 */       return null;
/*  211:     */     }
/*  212: 247 */     if (!pattern.getClass().equals(datum.getClass())) {
/*  213: 248 */       return null;
/*  214:     */     }
/*  215: 253 */     Vector<String> patternTypes = pattern.getMatcherTypes();
/*  216: 254 */     Vector<String> datumTypes = datum.getMatcherTypes();
/*  217: 255 */     Vector<String> excessP = vectorDifference(patternTypes, datumTypes);
/*  218: 256 */     Vector<String> excessD = vectorDifference(datumTypes, patternTypes);
/*  219: 259 */     if ((!excessP.isEmpty()) || (!excessD.isEmpty()))
/*  220:     */     {
/*  221: 263 */       fine("Failed to match " + pattern.getTypes() + " with " + datum.getTypes());
/*  222: 264 */       return null;
/*  223:     */     }
/*  224: 267 */     if (matchModifiers)
/*  225:     */     {
/*  226: 268 */       Vector patternElements = pattern.getModifiers();
/*  227: 269 */       Vector datumElements = datum.getModifiers();
/*  228: 271 */       if (patternElements.size() != datumElements.size()) {
/*  229: 272 */         return null;
/*  230:     */       }
/*  231: 274 */       int successes = 0;
/*  232: 275 */       for (int i = 0; i < patternElements.size(); i++)
/*  233:     */       {
/*  234: 276 */         Entity patternElement = (Entity)patternElements.elementAt(i);
/*  235: 277 */         Entity datumElement = (Entity)datumElements.elementAt(i);
/*  236: 278 */         BindingSet trialMatch = exactMatch(patternElement, datumElement, bindings, matchModifiers);
/*  237: 279 */         if (trialMatch != null) {
/*  238: 280 */           bindings = trialMatch;
/*  239:     */         } else {
/*  240: 283 */           return null;
/*  241:     */         }
/*  242:     */       }
/*  243:     */     }
/*  244: 289 */     if ((pattern.entityP()) && (datum.entityP()))
/*  245:     */     {
/*  246: 290 */       fine("Binding " + pattern.getTypes() + " to " + datum.getTypes());
/*  247: 291 */       bindings.add(new Binding(pattern, datum));
/*  248: 292 */       return bindings;
/*  249:     */     }
/*  250: 297 */     if ((pattern.functionP()) && (datum.functionP())) {
/*  251: 298 */       return exactMatch(pattern.getSubject(), datum.getSubject(), bindings, matchModifiers);
/*  252:     */     }
/*  253: 303 */     if ((pattern.relationP()) && (datum.relationP()))
/*  254:     */     {
/*  255: 305 */       bindings = exactMatch(((Relation)pattern).getSubject(), ((Relation)datum).getSubject(), bindings, matchModifiers);
/*  256: 306 */       if (bindings == null) {
/*  257: 307 */         return null;
/*  258:     */       }
/*  259: 311 */       return exactMatch(pattern.getObject(), datum.getObject(), bindings, matchModifiers);
/*  260:     */     }
/*  261: 320 */     if ((pattern.sequenceP()) && (datum.sequenceP()))
/*  262:     */     {
/*  263: 321 */       Vector patternElements = ((Sequence)pattern).getElements();
/*  264: 322 */       Vector datumElements = ((Sequence)datum).getElements();
/*  265: 324 */       if (patternElements.size() != datumElements.size()) {
/*  266: 325 */         return null;
/*  267:     */       }
/*  268: 327 */       int successes = 0;
/*  269: 328 */       for (int i = 0; i < patternElements.size(); i++)
/*  270:     */       {
/*  271: 329 */         Entity patternElement = (Entity)patternElements.elementAt(i);
/*  272: 330 */         Entity datumElement = (Entity)datumElements.elementAt(i);
/*  273: 331 */         BindingSet trialMatch = exactMatch(patternElement, datumElement, bindings, matchModifiers);
/*  274: 332 */         if (trialMatch != null) {
/*  275: 333 */           bindings = trialMatch;
/*  276:     */         } else {
/*  277: 336 */           return null;
/*  278:     */         }
/*  279:     */       }
/*  280: 339 */       return bindings;
/*  281:     */     }
/*  282: 341 */     return bindings;
/*  283:     */   }
/*  284:     */   
/*  285:     */   public static BindingSet measureMatch(Entity x, Entity y)
/*  286:     */   {
/*  287: 355 */     ArrayList<String> list = new ArrayList();
/*  288: 356 */     for (int i = 0; i < Entity.pathList.length; i++) {
/*  289: 357 */       list.add(Entity.pathList[i]);
/*  290:     */     }
/*  291: 359 */     for (int i = 0; i < Entity.placeList.length; i++) {
/*  292: 360 */       list.add(Entity.placeList[i]);
/*  293:     */     }
/*  294: 362 */     for (int i = 0; i < Entity.pathList.length; i++) {
/*  295: 363 */       list.add(Entity.changeList[i]);
/*  296:     */     }
/*  297: 365 */     return measureMatch(x, y, new BindingSet(), list);
/*  298:     */   }
/*  299:     */   
/*  300:     */   public static BindingSet measureMatch(Entity pattern, Entity datum, BindingSet bindings, ArrayList matchList)
/*  301:     */   {
/*  302: 376 */     fine("Trying to match " + pattern.getTypes() + " with " + datum.getTypes());
/*  303: 379 */     if (bindings == null) {
/*  304: 380 */       return null;
/*  305:     */     }
/*  306: 384 */     if ((!pattern.getClass().equals(datum.getClass())) && (!pattern.isA("?")) && (!datum.isA("?"))) {
/*  307: 385 */       return null;
/*  308:     */     }
/*  309: 392 */     Entity substitution = fetchValue(pattern, bindings);
/*  310: 393 */     if (substitution != null) {
/*  311: 394 */       return exactMatch(substitution, datum, bindings);
/*  312:     */     }
/*  313: 399 */     if (fetchVariable(datum, bindings) != null) {
/*  314: 400 */       return null;
/*  315:     */     }
/*  316: 404 */     Vector<String> patternTypes = pattern.getMatcherTypes();
/*  317: 405 */     Vector<String> datumTypes = datum.getMatcherTypes();
/*  318: 406 */     Vector<String> excessPatternTypes = vectorDifference(patternTypes, datumTypes);
/*  319: 407 */     Vector<String> excessDatumTypes = vectorDifference(datumTypes, patternTypes);
/*  320: 408 */     Vector<String> intersection = vectorIntersection(datumTypes, patternTypes);
/*  321:     */     
/*  322:     */ 
/*  323: 411 */     int mismatches = excessPatternTypes.size() + excessDatumTypes.size();
/*  324: 414 */     if (intersection.size() < 1)
/*  325:     */     {
/*  326: 415 */       fine("Failed to match " + pattern.getTypes() + " with " + datum.getTypes());
/*  327: 416 */       fine("Intersection size = " + intersection.size());
/*  328: 417 */       fine("Pattern types " + pattern.getMatcherTypes());
/*  329: 418 */       fine("Datum types " + datum.getMatcherTypes());
/*  330: 419 */       return null;
/*  331:     */     }
/*  332: 424 */     for (int i = 0; i < matchList.size(); i++)
/*  333:     */     {
/*  334: 425 */       String x = (String)matchList.get(i);
/*  335: 426 */       if ((pattern.isA(x)) && (!datum.isA(x)))
/*  336:     */       {
/*  337: 427 */         fine("Failed to match " + pattern.getTypes() + " with " + datum.getTypes());
/*  338: 428 */         fine("Mismatch is on " + x);
/*  339: 429 */         return null;
/*  340:     */       }
/*  341: 431 */       if ((datum.isA(x)) && (!pattern.isA(x)))
/*  342:     */       {
/*  343: 432 */         fine("Failed to match " + pattern.getTypes() + " with " + datum.getTypes());
/*  344: 433 */         fine("Mismatch is on " + x);
/*  345: 434 */         return null;
/*  346:     */       }
/*  347:     */     }
/*  348: 439 */     if ((pattern.entityP()) && (datum.entityP()))
/*  349:     */     {
/*  350: 440 */       fine("Binding " + pattern.getTypes() + " to " + datum.getTypes());
/*  351: 441 */       bindings.add(new Binding(pattern, datum));
/*  352: 442 */       return bindings;
/*  353:     */     }
/*  354: 447 */     if ((pattern.functionP()) && (datum.functionP()))
/*  355:     */     {
/*  356: 448 */       fine("Recursing into subjects");
/*  357: 449 */       return measureMatch(pattern.getSubject(), datum.getSubject(), bindings, matchList);
/*  358:     */     }
/*  359: 454 */     if ((pattern.relationP()) && (datum.relationP()))
/*  360:     */     {
/*  361: 456 */       fine("Recursing into subjects");
/*  362: 457 */       bindings = measureMatch(((Relation)pattern).getSubject(), ((Relation)datum).getSubject(), bindings, matchList);
/*  363: 458 */       if (bindings == null) {
/*  364: 459 */         return null;
/*  365:     */       }
/*  366: 463 */       fine("Recursing into objects");
/*  367: 464 */       return measureMatch(pattern.getObject(), datum.getObject(), bindings, matchList);
/*  368:     */     }
/*  369: 471 */     if ((pattern.sequenceP()) && (datum.sequenceP()))
/*  370:     */     {
/*  371: 472 */       Vector patternElements = ((Sequence)pattern).getElements();
/*  372: 473 */       Vector datumElements = ((Sequence)datum).getElements();
/*  373: 474 */       int successes = 0;
/*  374: 475 */       for (int i = 0; i < patternElements.size(); i++)
/*  375:     */       {
/*  376: 476 */         Entity patternElement = (Entity)patternElements.elementAt(i);
/*  377: 477 */         for (int j = 0; j < datumElements.size(); j++)
/*  378:     */         {
/*  379: 478 */           Entity datumElement = (Entity)datumElements.elementAt(j);
/*  380: 479 */           fine("Trying to match " + patternElement.getTypes() + " with " + datumElement.getTypes());
/*  381: 480 */           if (bindings == null) {
/*  382:     */             break;
/*  383:     */           }
/*  384: 483 */           BindingSet trialMatch = measureMatch(patternElement, datumElement, bindings, matchList);
/*  385: 484 */           if (trialMatch != null)
/*  386:     */           {
/*  387: 485 */             bindings = trialMatch;
/*  388: 486 */             successes++;
/*  389: 487 */             break;
/*  390:     */           }
/*  391:     */         }
/*  392:     */       }
/*  393: 493 */       if (((patternElements.size() > 0) || (datumElements.size() > 0)) && (successes < 1)) {
/*  394: 494 */         return null;
/*  395:     */       }
/*  396: 496 */       return bindings;
/*  397:     */     }
/*  398: 498 */     if (bindings == null) {
/*  399: 499 */       fine("Failed to match " + pattern.getTypes() + " with " + datum.getTypes());
/*  400:     */     }
/*  401: 501 */     return bindings;
/*  402:     */   }
/*  403:     */   
/*  404:     */   public static Entity instantiate(Entity precident, BindingSet bindings)
/*  405:     */   {
/*  406: 513 */     fine("Trying to instantiate " + precident.getTypes());
/*  407: 516 */     if (bindings == null) {
/*  408: 517 */       return precident;
/*  409:     */     }
/*  410: 521 */     Entity substitution = fetchValue(precident, bindings);
/*  411: 522 */     if (substitution != null) {
/*  412: 523 */       return substitution;
/*  413:     */     }
/*  414: 527 */     if (precident.entityP())
/*  415:     */     {
/*  416: 528 */       Entity t = (Entity)precident.clone();
/*  417: 529 */       return t;
/*  418:     */     }
/*  419: 534 */     if ((precident.functionP()) && (precident.functionP()))
/*  420:     */     {
/*  421: 535 */       Function d = (Function)precident.clone();
/*  422: 536 */       d.setSubject(instantiate(precident.getSubject(), bindings));
/*  423: 537 */       return d;
/*  424:     */     }
/*  425: 542 */     if (precident.relationP())
/*  426:     */     {
/*  427: 543 */       Relation r = (Relation)precident.clone();
/*  428: 544 */       r.setSubject(instantiate(precident.getSubject(), bindings));
/*  429: 545 */       r.setObject(instantiate(precident.getObject(), bindings));
/*  430: 546 */       return r;
/*  431:     */     }
/*  432: 551 */     if (precident.sequenceP())
/*  433:     */     {
/*  434: 552 */       Sequence s = (Sequence)precident.clone();
/*  435: 553 */       Vector v = (Vector)s.getElements().clone();
/*  436: 554 */       s.clearElements();
/*  437: 555 */       for (int i = 0; i < v.size(); i++)
/*  438:     */       {
/*  439: 556 */         Entity t = (Entity)v.elementAt(i);
/*  440: 557 */         Entity u = instantiate(t, bindings);
/*  441: 558 */         s.addElement(u);
/*  442:     */       }
/*  443: 560 */       return s;
/*  444:     */     }
/*  445: 563 */     return precident;
/*  446:     */   }
/*  447:     */   
/*  448:     */   public static Entity instantiate(Entity precident, LList<PairOfEntities> bindings)
/*  449:     */   {
/*  450: 567 */     fine("Trying to instantiate " + precident.getTypes());
/*  451: 570 */     if (bindings == null) {
/*  452: 571 */       return precident;
/*  453:     */     }
/*  454: 575 */     Entity substitution = fetchByValue(precident, bindings);
/*  455: 576 */     if (substitution != null) {
/*  456: 577 */       return substitution;
/*  457:     */     }
/*  458: 581 */     if (precident.entityP())
/*  459:     */     {
/*  460: 582 */       Entity t = (Entity)precident.clone();
/*  461: 583 */       return t;
/*  462:     */     }
/*  463: 588 */     if ((precident.functionP()) && (precident.functionP()))
/*  464:     */     {
/*  465: 589 */       Function d = (Function)precident.clone();
/*  466: 590 */       d.setSubject(instantiate(precident.getSubject(), bindings));
/*  467: 591 */       return d;
/*  468:     */     }
/*  469: 596 */     if (precident.relationP())
/*  470:     */     {
/*  471: 597 */       Relation r = (Relation)precident.clone();
/*  472: 598 */       r.setSubject(instantiate(precident.getSubject(), bindings));
/*  473: 599 */       r.setObject(instantiate(precident.getObject(), bindings));
/*  474: 600 */       return r;
/*  475:     */     }
/*  476: 605 */     if (precident.sequenceP())
/*  477:     */     {
/*  478: 606 */       Sequence s = (Sequence)precident.clone();
/*  479: 607 */       Vector v = (Vector)s.getElements().clone();
/*  480: 608 */       s.clearElements();
/*  481: 609 */       for (int i = 0; i < v.size(); i++)
/*  482:     */       {
/*  483: 610 */         Entity t = (Entity)v.elementAt(i);
/*  484: 611 */         Entity u = instantiate(t, bindings);
/*  485: 612 */         s.addElement(u);
/*  486:     */       }
/*  487: 614 */       return s;
/*  488:     */     }
/*  489: 617 */     return precident;
/*  490:     */   }
/*  491:     */   
/*  492:     */   public static boolean basicMeasureMatchTest()
/*  493:     */   {
/*  494: 634 */     Entity t1 = new Entity("table");
/*  495: 635 */     Entity t2 = new Entity("pole");
/*  496: 636 */     Sequence pPathB = JFactory.createPath();
/*  497: 637 */     pPathB.addElement(JFactory.createPathElement("from", JFactory.createPlace("nextTo", t1)));
/*  498: 638 */     pPathB.addElement(JFactory.createPathElement("to", JFactory.createPlace("on", t2)));
/*  499:     */     
/*  500:     */ 
/*  501: 641 */     Sequence pPathA = JFactory.createPath();
/*  502: 642 */     pPathA.addElement(JFactory.createPathElement("via", JFactory.createPlace("nextTo", t2)));
/*  503:     */     
/*  504:     */ 
/*  505: 645 */     Entity t4 = new Entity("pole");
/*  506: 646 */     Sequence cPath = JFactory.createPath();
/*  507: 647 */     cPath.addElement(JFactory.createPathElement("to", JFactory.createPlace("on", t4)));
/*  508:     */     
/*  509:     */ 
/*  510: 650 */     t2.addType("big");
/*  511: 651 */     t4.addType("small");
/*  512: 652 */     t2.addType("firePole");
/*  513: 653 */     t4.addType("flagPole");
/*  514:     */     
/*  515:     */ 
/*  516:     */ 
/*  517: 657 */     BindingSet bindings = measureMatch(pPathB, cPath);
/*  518: 660 */     if (bindings == null) {
/*  519: 661 */       return false;
/*  520:     */     }
/*  521: 665 */     boolean test1 = t4 == bindings.getValue(t2);
/*  522:     */     
/*  523:     */ 
/*  524:     */ 
/*  525:     */ 
/*  526: 670 */     Entity instantiation = instantiate(pPathA, bindings);
/*  527:     */     
/*  528:     */ 
/*  529: 673 */     Sequence reference = JFactory.createPath();
/*  530: 674 */     reference.addElement(JFactory.createPathElement("via", JFactory.createPlace("nextTo", t4)));
/*  531:     */     
/*  532:     */ 
/*  533:     */ 
/*  534: 678 */     boolean test2 = exactMatch(pPathA, reference) != null;
/*  535:     */     
/*  536: 680 */     boolean test3 = exactMatch(instantiation, reference) != null;
/*  537:     */     
/*  538:     */ 
/*  539: 683 */     return (test1) && (!test2) && (test3);
/*  540:     */   }
/*  541:     */   
/*  542:     */   public static boolean matchChanges(Entity currentChange, Entity previousChange)
/*  543:     */   {
/*  544: 746 */     if ((!currentChange.functionP()) || (!previousChange.functionP()) || (!currentChange.isA("transitionElement")) || 
/*  545: 747 */       (!previousChange.isA("transitionElement")))
/*  546:     */     {
/*  547: 748 */       fine("At least one of the arguments is not an event derivative");
/*  548: 749 */       return false;
/*  549:     */     }
/*  550: 753 */     if (!matchSurfaces(currentChange, previousChange))
/*  551:     */     {
/*  552: 754 */       fine("Changes do not pass basic matching test");
/*  553: 755 */       return false;
/*  554:     */     }
/*  555: 759 */     Entity eventMover = currentChange.getSubject();
/*  556: 760 */     Entity precedentMover = previousChange.getSubject();
/*  557: 761 */     if (!matchChangingObjects(eventMover, precedentMover)) {
/*  558: 762 */       return false;
/*  559:     */     }
/*  560: 765 */     fine("Matched corresponding changes");
/*  561: 766 */     return true;
/*  562:     */   }
/*  563:     */   
/*  564:     */   private static boolean matchChangingObjects(Entity currentMover, Entity previousMover)
/*  565:     */   {
/*  566: 773 */     if (!matchStructures(currentMover, previousMover))
/*  567:     */     {
/*  568: 774 */       fine("Movers do not match");
/*  569: 775 */       return false;
/*  570:     */     }
/*  571: 777 */     fine("Changing objects match");
/*  572: 778 */     return true;
/*  573:     */   }
/*  574:     */   
/*  575:     */   private static boolean matchStructures(Entity currentStructure, Entity previousStructure)
/*  576:     */   {
/*  577: 785 */     if ((currentStructure.entityP()) && (previousStructure.entityP())) {
/*  578: 786 */       return matchThings(currentStructure, previousStructure);
/*  579:     */     }
/*  580: 788 */     if ((currentStructure.functionP()) && (previousStructure.functionP())) {
/*  581: 789 */       return matchDerivatives(currentStructure, previousStructure);
/*  582:     */     }
/*  583: 791 */     if ((currentStructure.relationP()) && (previousStructure.relationP())) {
/*  584: 792 */       return matchRelations(currentStructure, previousStructure);
/*  585:     */     }
/*  586: 794 */     if ((currentStructure.sequenceP()) && (previousStructure.sequenceP())) {
/*  587: 795 */       return matchSequences(currentStructure, previousStructure);
/*  588:     */     }
/*  589: 797 */     fine("Structures not same type");
/*  590: 798 */     return false;
/*  591:     */   }
/*  592:     */   
/*  593:     */   private static boolean matchDerivatives(Entity currentDerivative, Entity previousDerivative)
/*  594:     */   {
/*  595: 805 */     if ((!currentDerivative.functionP()) || (!previousDerivative.functionP()))
/*  596:     */     {
/*  597: 806 */       fine("At least one of the arguments is not a derivative");
/*  598: 807 */       return false;
/*  599:     */     }
/*  600: 809 */     if (!matchSurfaces(currentDerivative, previousDerivative))
/*  601:     */     {
/*  602: 810 */       fine("Derivatives do not pass basic matching test");
/*  603: 811 */       return false;
/*  604:     */     }
/*  605: 813 */     Entity currentThing = currentDerivative.getSubject();
/*  606: 814 */     Entity previousThing = previousDerivative.getSubject();
/*  607: 815 */     if (!matchStructures(currentThing, previousThing))
/*  608:     */     {
/*  609: 816 */       fine("Subject arguments do not match");
/*  610: 817 */       return false;
/*  611:     */     }
/*  612: 819 */     fine("Derivatives match");
/*  613: 820 */     return true;
/*  614:     */   }
/*  615:     */   
/*  616:     */   public static boolean matchRelations(Entity currentRelation, Entity previousRelation)
/*  617:     */   {
/*  618: 828 */     if ((!currentRelation.relationP()) || (!previousRelation.relationP()))
/*  619:     */     {
/*  620: 829 */       fine("At least one of the arguments is not a relation");
/*  621: 830 */       return false;
/*  622:     */     }
/*  623: 834 */     if (!matchSurfaces(currentRelation, previousRelation))
/*  624:     */     {
/*  625: 835 */       fine("Relations do not pass basic matching test");
/*  626: 836 */       return false;
/*  627:     */     }
/*  628: 840 */     Entity relationSubject = currentRelation.getSubject();
/*  629: 841 */     Entity precedentSubject = previousRelation.getSubject();
/*  630: 842 */     if (!matchStructures(relationSubject, precedentSubject)) {
/*  631: 843 */       return false;
/*  632:     */     }
/*  633: 847 */     Entity relationObject = currentRelation.getObject();
/*  634: 848 */     Entity precedentObject = previousRelation.getObject();
/*  635: 849 */     if (!matchStructures(relationObject, precedentObject)) {
/*  636: 850 */       return false;
/*  637:     */     }
/*  638: 853 */     fine("Relations match");
/*  639: 854 */     return true;
/*  640:     */   }
/*  641:     */   
/*  642:     */   private static boolean matchSequences(Entity currentSequence, Entity previousSequence)
/*  643:     */   {
/*  644: 862 */     if ((!currentSequence.sequenceP()) || (!previousSequence.sequenceP())) {
/*  645: 863 */       fine("At least one of the arguments is not a sequence");
/*  646:     */     }
/*  647: 865 */     if (!matchSurfaces(currentSequence, previousSequence))
/*  648:     */     {
/*  649: 866 */       fine("Sequences do not pass basic matching test");
/*  650: 867 */       return false;
/*  651:     */     }
/*  652: 871 */     Vector cVector = currentSequence.getElements();
/*  653: 872 */     Vector pVector = previousSequence.getElements();
/*  654: 873 */     boolean testResult = false;
/*  655: 874 */     for (int i = 0; i < cVector.size(); i++)
/*  656:     */     {
/*  657: 875 */       Entity currentSequenceElement = (Entity)cVector.elementAt(i);
/*  658: 876 */       if (!checkForSequenceInclusion(currentSequenceElement, pVector))
/*  659:     */       {
/*  660: 877 */         fine("Sequence element missing from previous sequence");
/*  661: 878 */         return false;
/*  662:     */       }
/*  663:     */     }
/*  664: 881 */     fine("Sequences match");
/*  665: 882 */     return true;
/*  666:     */   }
/*  667:     */   
/*  668:     */   private static boolean checkForSequenceInclusion(Entity currentSequenceElement, Vector previousSequenceElements)
/*  669:     */   {
/*  670: 890 */     for (int i = 0; i < previousSequenceElements.size(); i++) {
/*  671: 891 */       if (matchStructures(currentSequenceElement, (Entity)previousSequenceElements.elementAt(i))) {
/*  672: 892 */         return true;
/*  673:     */       }
/*  674:     */     }
/*  675: 895 */     return false;
/*  676:     */   }
/*  677:     */   
/*  678:     */   public static boolean matchEvents(Entity currentEvent, Entity previousEvent)
/*  679:     */   {
/*  680: 908 */     if ((!currentEvent.relationP()) || (!previousEvent.relationP()) || (!currentEvent.isA("event")) || (!previousEvent.isA("event")))
/*  681:     */     {
/*  682: 909 */       fine("At least one of the arguments is not an event relation");
/*  683: 910 */       return false;
/*  684:     */     }
/*  685: 914 */     if (!matchSurfaces(currentEvent, previousEvent))
/*  686:     */     {
/*  687: 915 */       fine("Events do not pass basic matching test");
/*  688: 916 */       return false;
/*  689:     */     }
/*  690: 920 */     Entity eventMover = currentEvent.getSubject();
/*  691: 921 */     Entity precedentMover = previousEvent.getSubject();
/*  692: 922 */     if (!matchMovers(eventMover, precedentMover)) {
/*  693: 923 */       return false;
/*  694:     */     }
/*  695: 927 */     Entity eventPath = currentEvent.getObject();
/*  696: 928 */     Entity precedentPath = previousEvent.getObject();
/*  697: 929 */     if (!matchPaths(eventPath, precedentPath)) {
/*  698: 930 */       return false;
/*  699:     */     }
/*  700: 932 */     fine("Matched corresponding events");
/*  701: 933 */     return true;
/*  702:     */   }
/*  703:     */   
/*  704:     */   private static boolean matchMovers(Entity currentMover, Entity previousMover)
/*  705:     */   {
/*  706: 940 */     if (!matchThings(currentMover, previousMover))
/*  707:     */     {
/*  708: 941 */       fine("Movers do not match");
/*  709: 942 */       return false;
/*  710:     */     }
/*  711: 944 */     fine("Movers match");
/*  712: 945 */     return true;
/*  713:     */   }
/*  714:     */   
/*  715:     */   private static boolean matchThings(Entity currentThing, Entity previousThing)
/*  716:     */   {
/*  717: 953 */     if ((!currentThing.entityP()) || (!previousThing.entityP()))
/*  718:     */     {
/*  719: 954 */       fine("At least one of the arguments is not a thing");
/*  720: 955 */       fine(currentThing);
/*  721: 956 */       fine(previousThing);
/*  722: 957 */       return false;
/*  723:     */     }
/*  724: 959 */     if (currentThing != previousThing)
/*  725:     */     {
/*  726: 960 */       fine("Things do not match");
/*  727: 961 */       return false;
/*  728:     */     }
/*  729: 963 */     fine("Things match");
/*  730: 964 */     return true;
/*  731:     */   }
/*  732:     */   
/*  733:     */   private static boolean matchPaths(Entity currentPath, Entity previousPath)
/*  734:     */   {
/*  735: 971 */     if ((!currentPath.sequenceP()) || (!previousPath.sequenceP()) || (!currentPath.isA("path")) || (!previousPath.isA("path"))) {
/*  736: 972 */       fine("At least one of the paths is not a path sequence");
/*  737:     */     }
/*  738: 974 */     if (!matchSurfaces(currentPath, previousPath))
/*  739:     */     {
/*  740: 975 */       fine("Paths do not pass basic matching test");
/*  741: 976 */       return false;
/*  742:     */     }
/*  743: 980 */     Vector cVector = currentPath.getElements();
/*  744: 981 */     Vector pVector = previousPath.getElements();
/*  745: 982 */     boolean testResult = false;
/*  746: 983 */     for (int i = 0; i < cVector.size(); i++)
/*  747:     */     {
/*  748: 984 */       Entity currentPathElement = (Entity)cVector.elementAt(i);
/*  749: 985 */       if (!checkForPathInclusion(currentPathElement, pVector))
/*  750:     */       {
/*  751: 986 */         fine("Path element missing from previous path");
/*  752: 987 */         return false;
/*  753:     */       }
/*  754:     */     }
/*  755: 990 */     fine("Paths match");
/*  756: 991 */     return true;
/*  757:     */   }
/*  758:     */   
/*  759:     */   private static boolean checkForPathInclusion(Entity currentPathElement, Vector previousPathElements)
/*  760:     */   {
/*  761: 999 */     for (int i = 0; i < previousPathElements.size(); i++) {
/*  762:1000 */       if (matchPathElements(currentPathElement, (Entity)previousPathElements.elementAt(i))) {
/*  763:1001 */         return true;
/*  764:     */       }
/*  765:     */     }
/*  766:1004 */     return false;
/*  767:     */   }
/*  768:     */   
/*  769:     */   private static boolean matchPathElements(Entity currentPathElement, Entity previousPathElement)
/*  770:     */   {
/*  771:1011 */     if ((!currentPathElement.functionP()) || (!previousPathElement.functionP()) || (!currentPathElement.isA("pathElement")) || 
/*  772:1012 */       (!previousPathElement.isA("pathElement")))
/*  773:     */     {
/*  774:1013 */       fine("At least one of the arguments is not a pathElement derivative");
/*  775:1014 */       return false;
/*  776:     */     }
/*  777:1016 */     if (!matchSurfaces(currentPathElement, previousPathElement))
/*  778:     */     {
/*  779:1017 */       fine("Path elements do not pass basic matching test");
/*  780:1018 */       return false;
/*  781:     */     }
/*  782:1020 */     Entity currentThing = currentPathElement.getSubject();
/*  783:1021 */     Entity previousThing = previousPathElement.getSubject();
/*  784:1022 */     if (!matchPlaces(currentThing, previousThing))
/*  785:     */     {
/*  786:1023 */       fine("Path element arguments do not match");
/*  787:1024 */       return false;
/*  788:     */     }
/*  789:1026 */     fine("Path Elements match");
/*  790:1027 */     return true;
/*  791:     */   }
/*  792:     */   
/*  793:     */   private static boolean matchPlaces(Entity currentPlace, Entity previousPlace)
/*  794:     */   {
/*  795:1034 */     if ((!currentPlace.functionP()) || (!previousPlace.functionP()) || (!currentPlace.isA("place")) || (!previousPlace.isA("place")))
/*  796:     */     {
/*  797:1035 */       fine("At least one of the arguments is not a place derivative");
/*  798:1036 */       return false;
/*  799:     */     }
/*  800:1038 */     if (!matchSurfaces(currentPlace, previousPlace))
/*  801:     */     {
/*  802:1039 */       fine("Places do not pass basic matching test");
/*  803:1040 */       return false;
/*  804:     */     }
/*  805:1042 */     Entity currentThing = currentPlace.getSubject();
/*  806:1043 */     Entity previousThing = previousPlace.getSubject();
/*  807:1044 */     if (!matchThings(currentThing, previousThing))
/*  808:     */     {
/*  809:1045 */       fine("Place arguments do not match");
/*  810:1046 */       return false;
/*  811:     */     }
/*  812:1048 */     fine("Places match");
/*  813:1049 */     return true;
/*  814:     */   }
/*  815:     */   
/*  816:     */   private static boolean matchSurfaces(Entity currentThing, Entity previousThing)
/*  817:     */   {
/*  818:1060 */     if (currentThing == previousThing)
/*  819:     */     {
/*  820:1061 */       fine("Matched identical things: " + currentThing.getName() + " matches " + previousThing.getName());
/*  821:1062 */       return true;
/*  822:     */     }
/*  823:1066 */     if (!currentThing.getType().equals(previousThing.getType())) {
/*  824:1067 */       return false;
/*  825:     */     }
/*  826:1072 */     Vector currentDescriptors = currentThing.getThread("description");
/*  827:1073 */     Vector previousDescriptors = previousThing.getThread("description");
/*  828:1074 */     if (currentDescriptors != null)
/*  829:     */     {
/*  830:1075 */       if (previousDescriptors == null) {
/*  831:1076 */         return false;
/*  832:     */       }
/*  833:1078 */       for (int i = 0; i < currentDescriptors.size(); i++)
/*  834:     */       {
/*  835:1079 */         Object descriptor = currentDescriptors.elementAt(i);
/*  836:1080 */         fine("Checking " + descriptor);
/*  837:1082 */         if (!previousDescriptors.contains(descriptor)) {
/*  838:1083 */           return false;
/*  839:     */         }
/*  840:     */       }
/*  841:     */     }
/*  842:1089 */     fine("Matched corresponding things: " + currentThing.getName() + " matches " + previousThing.getName());
/*  843:1090 */     return true;
/*  844:     */   }
/*  845:     */   
/*  846:     */   public static void replace(Entity oldThing, Entity newThing)
/*  847:     */   {
/*  848:1097 */     fine("Replacing new event with antecedant");
/*  849:1098 */     Vector subjectOfVector = (Vector)oldThing.getSubjectOf().clone();
/*  850:1099 */     Vector objectOfVector = (Vector)oldThing.getObjectOf().clone();
/*  851:1100 */     Vector elementOfVector = (Vector)oldThing.getElementOf().clone();
/*  852:1101 */     for (int i = 0; i < subjectOfVector.size(); i++)
/*  853:     */     {
/*  854:1102 */       Function d = (Function)subjectOfVector.elementAt(i);
/*  855:1103 */       d.setSubject(newThing);
/*  856:     */     }
/*  857:1105 */     for (int i = 0; i < objectOfVector.size(); i++)
/*  858:     */     {
/*  859:1106 */       Relation r = (Relation)objectOfVector.elementAt(i);
/*  860:1107 */       r.setObject(newThing);
/*  861:     */     }
/*  862:1109 */     for (int i = 0; i < elementOfVector.size(); i++)
/*  863:     */     {
/*  864:1110 */       Sequence s = (Sequence)elementOfVector.elementAt(i);
/*  865:1111 */       Vector<Entity> v = s.getElements();
/*  866:1112 */       int index = v.indexOf(oldThing);
/*  867:1113 */       if (index < 0) {
/*  868:1114 */         warning("Unable to replace sequence element for some reason");
/*  869:     */       } else {
/*  870:1117 */         v.set(index, newThing);
/*  871:     */       }
/*  872:     */     }
/*  873:     */   }
/*  874:     */   
/*  875:     */   private static void fine(Object s)
/*  876:     */   {
/*  877:1177 */     Logger.getLogger("frames.Matcher").fine(s);
/*  878:     */   }
/*  879:     */   
/*  880:     */   private static void info(Object s)
/*  881:     */   {
/*  882:1181 */     Logger.getLogger("frames.Matcher").info(s);
/*  883:     */   }
/*  884:     */   
/*  885:     */   private static void warning(Object s)
/*  886:     */   {
/*  887:1185 */     Logger.getLogger("frames.Matcher").warning(s);
/*  888:     */   }
/*  889:     */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.Matcher
 * JD-Core Version:    0.7.0.1
 */