/*    1:     */ package translator;
/*    2:     */ 
/*    3:     */ import bridge.reps.entities.Bundle;
/*    4:     */ import bridge.reps.entities.Entity;
/*    5:     */ import bridge.reps.entities.Function;
/*    6:     */ import bridge.reps.entities.Relation;
/*    7:     */ import bridge.reps.entities.Sequence;
/*    8:     */ import bridge.reps.entities.Thread;
/*    9:     */ import connections.AbstractWiredBox;
/*   10:     */ import constants.Markers;
/*   11:     */ import java.awt.Container;
/*   12:     */ import java.util.ArrayList;
/*   13:     */ import java.util.Arrays;
/*   14:     */ import java.util.List;
/*   15:     */ import java.util.Vector;
/*   16:     */ import javax.swing.JFrame;
/*   17:     */ import links.words.BundleGenerator;
/*   18:     */ import parameters.Switch;
/*   19:     */ import persistence.JCheckBoxWithMemory;
/*   20:     */ import start.Start;
/*   21:     */ import storyProcessor.StoryProcessor;
/*   22:     */ import tools.Constructors;
/*   23:     */ import tools.Getters;
/*   24:     */ import tools.Innerese;
/*   25:     */ import tools.JFactory;
/*   26:     */ import tools.Predicates;
/*   27:     */ import utils.Mark;
/*   28:     */ 
/*   29:     */ public class NewRuleSet
/*   30:     */   extends AbstractWiredBox
/*   31:     */ {
/*   32:     */   protected ArrayList<Rule> ruleSet;
/*   33:     */   
/*   34:     */   private static List<String> getTimeWords()
/*   35:     */   {
/*   36:  29 */     return timeWords;
/*   37:     */   }
/*   38:     */   
/*   39:  34 */   public static List<String> assertionWords = Arrays.asList(new String[] { "know", "see", "show", "demonstrate" });
/*   40:  36 */   public static List<String> frequencyWords = Arrays.asList(new String[] { "sometimes", "often", "occasionally" });
/*   41:  39 */   public static List<String> pathPrepositions = Arrays.asList(new String[] {"to_the_left_of", "to_the_right_of", "from", "to", "into", "under", "toward", "towards", "via", "behind", "between", "past", "by", "over", "above", "down", "up", "below", "on", "in", "near", "off" });
/*   42:  42 */   public static List<String> locationPrepositions = Arrays.asList(new String[] {"to_the_left_of", "to_the_right_of", "over", "under", "behind", "between", "by", "above", "down", "up", "below", "on", "in", "near" });
/*   43:  45 */   public static List<String> placePrepositions = Arrays.asList(new String[] {"at", "side", "top", "bottom", "left", "right", "inside", "front", "back", "next_to" });
/*   44:  47 */   public static List<String> directionWords = Arrays.asList(new String[] { "left", "right" });
/*   45:  50 */   public static List<String> trajectoryWords = Arrays.asList(new String[] {"trajectory", "travel", "go", "come", "leave", "arrive", "move", "roll", "enter", "exit" });
/*   46:  52 */   protected static List<String> causeToMoveWords = Arrays.asList(new String[] { "propel", "push", "roll", "move" });
/*   47:  54 */   protected static List<String> pathWords = Arrays.asList(new String[] { "path", "location" });
/*   48:  56 */   public static List<String> transitionWords = Arrays.asList(new String[] { "occur", "happen", "appear", "disappear", "change", "increase", "decrease" });
/*   49:  58 */   protected static List<String> timeWords = Arrays.asList(new String[] { "before", "after", "while" });
/*   50:  60 */   protected static List<String> newTimeWords = Arrays.asList(new String[] { "before_connector", "after_connector", "while_connector" });
/*   51:  62 */   protected static List<String> timeMarkerWords = Arrays.asList(new String[] { "then", "later", "afterward", "next" });
/*   52:  64 */   protected static List<String> requireWords = Arrays.asList(new String[] { "force", "ask" });
/*   53:  66 */   protected static List<String> goalWords = Arrays.asList(new String[] { "want", "try" });
/*   54:  68 */   protected static List<String> transferWords = Arrays.asList(new String[] { "give", "take", "throw", "pass", "receive" });
/*   55:  70 */   protected static List<String> persuadeWords = Arrays.asList(new String[] { "persuade", "ask" });
/*   56:  72 */   protected static List<String> forceWords = Arrays.asList(new String[] { "force", "coerce" });
/*   57:  74 */   protected static List<String> roleWords = Arrays.asList(new String[] { "by", "with", "for", "at", "about", "in", "against" });
/*   58:  76 */   protected static List<String> positiveWords = Arrays.asList(new String[] { "happy" });
/*   59:  78 */   protected static List<String> negativeWords = Arrays.asList(new String[] { "sad", "unhappy" });
/*   60:  80 */   protected static List<String> mentalStateWords = new ArrayList();
/*   61:  82 */   protected static List<String> jobWords = Arrays.asList(new String[] { "ruler", "legislator", "administrator", "leader", "professional" });
/*   62:  84 */   protected Entity root = new Entity("root");
/*   63:     */   
/*   64:     */   public NewRuleSet()
/*   65:     */   {
/*   66:  87 */     mentalStateWords.addAll(positiveWords);
/*   67:  88 */     mentalStateWords.addAll(negativeWords);
/*   68:  89 */     makeRuleSet();
/*   69:     */   }
/*   70:     */   
/*   71:     */   private void addRule(BasicRule rule)
/*   72:     */   {
/*   73:  93 */     getRuleSet().add(new Rule(rule));
/*   74:     */   }
/*   75:     */   
/*   76:     */   protected ArrayList<Rule> getRuleSet()
/*   77:     */   {
/*   78:  97 */     if (this.ruleSet == null) {
/*   79:  98 */       this.ruleSet = new ArrayList();
/*   80:     */     }
/*   81: 100 */     return this.ruleSet;
/*   82:     */   }
/*   83:     */   
/*   84:     */   class ProcessDoTimeQuestion
/*   85:     */     extends BasicRule
/*   86:     */   {
/*   87: 107 */     ArrayList firstWords = new ArrayList();
/*   88: 109 */     ArrayList secondWords = new ArrayList();
/*   89:     */     
/*   90:     */     public ProcessDoTimeQuestion()
/*   91:     */     {
/*   92: 112 */       this.firstWords.addAll(NewRuleSet.access$0());
/*   93: 113 */       this.firstWords.add("because");
/*   94: 114 */       this.secondWords.add("do");
/*   95: 115 */       this.secondWords.add("have");
/*   96:     */     }
/*   97:     */     
/*   98:     */     public void run()
/*   99:     */     {
/*  100: 119 */       super.run();
/*  101: 120 */       if ((this.firstLinkSubject.isAPrimed(this.firstWords)) && (this.firstLinkObject.isAPrimed(this.secondWords)))
/*  102:     */       {
/*  103: 121 */         Function question = new Function("question", this.firstLinkSubject);
/*  104: 122 */         question.addType("do");
/*  105: 123 */         replace(this.firstLink, new Relation("link", NewRuleSet.this.root, question));
/*  106: 124 */         succeeded();
/*  107:     */       }
/*  108: 126 */       else if ((this.firstLinkObject.isAPrimed(this.firstWords)) && (this.firstLinkSubject.isAPrimed(this.secondWords)))
/*  109:     */       {
/*  110: 127 */         Function question = new Function("question", this.firstLinkObject);
/*  111: 128 */         question.addType("do");
/*  112: 129 */         replace(this.firstLink, new Relation("link", NewRuleSet.this.root, question));
/*  113: 130 */         succeeded();
/*  114:     */       }
/*  115:     */     }
/*  116:     */   }
/*  117:     */   
/*  118:     */   private Function getNewDerivative(String type, Entity t)
/*  119:     */   {
/*  120: 159 */     Thread thread = new Thread();
/*  121: 160 */     thread.add(type);
/*  122: 161 */     Function result = new Function(t);
/*  123: 162 */     result.replacePrimedThread(thread);
/*  124: 163 */     return result;
/*  125:     */   }
/*  126:     */   
/*  127:     */   public static List getPathPrepositions()
/*  128:     */   {
/*  129: 167 */     return pathPrepositions;
/*  130:     */   }
/*  131:     */   
/*  132:     */   private boolean isA(Entity t)
/*  133:     */   {
/*  134: 173 */     return ("is-a".equals(t.getType())) || ("is".equals(t.getType()));
/*  135:     */   }
/*  136:     */   
/*  137:     */   class KillRedundantRoots
/*  138:     */     extends BasicRule2
/*  139:     */   {
/*  140:     */     KillRedundantRoots() {}
/*  141:     */     
/*  142:     */     public void run()
/*  143:     */     {
/*  144: 181 */       super.run();
/*  145: 182 */       if ((this.firstLinkSubject == NewRuleSet.this.root) && 
/*  146: 183 */         (this.secondLinkSubject == NewRuleSet.this.root)) {
/*  147: 184 */         if (firstInsideSecond(this.firstLinkObject, this.secondLinkObject))
/*  148:     */         {
/*  149: 185 */           remove(this.firstLink);
/*  150: 186 */           succeeded();
/*  151:     */         }
/*  152: 188 */         else if (firstInsideSecond(this.secondLinkObject, this.firstLinkObject))
/*  153:     */         {
/*  154: 189 */           remove(this.secondLink);
/*  155: 190 */           succeeded();
/*  156:     */         }
/*  157:     */       }
/*  158:     */     }
/*  159:     */   }
/*  160:     */   
/*  161:     */   class EndOfStoryIdiom
/*  162:     */     extends BasicRule
/*  163:     */   {
/*  164:     */     EndOfStoryIdiom() {}
/*  165:     */     
/*  166:     */     public void run()
/*  167:     */     {
/*  168: 222 */       super.run();
/*  169: 223 */       if ((this.firstLinkSubject.isA("end")) && 
/*  170: 224 */         (this.firstLinkObject.isAPrimed("is")) && 
/*  171: 225 */         (this.firstLinkSubject.functionP()))
/*  172:     */       {
/*  173: 226 */         Function derivative = (Function)this.firstLinkSubject;
/*  174: 227 */         if (derivative.getSubject().isAPrimed("story"))
/*  175:     */         {
/*  176: 228 */           replace(this.firstLink, new Relation("link", NewRuleSet.this.root, this.firstLinkSubject));
/*  177: 229 */           succeeded();
/*  178:     */         }
/*  179:     */       }
/*  180:     */     }
/*  181:     */   }
/*  182:     */   
/*  183:     */   class AbsorbeAdjective
/*  184:     */     extends BasicRule
/*  185:     */   {
/*  186:     */     AbsorbeAdjective() {}
/*  187:     */     
/*  188:     */     public void run()
/*  189:     */     {
/*  190: 243 */       super.run();
/*  191: 244 */       if ((this.firstLinkSubject.isAPrimed("entity")) && (this.firstLink.isA("adjectival-modifier")))
/*  192:     */       {
/*  193: 245 */         this.firstLinkSubject.addFeature(this.firstLinkObject.getType());
/*  194: 246 */         remove(this.firstLink);
/*  195: 247 */         succeeded();
/*  196:     */       }
/*  197:     */     }
/*  198:     */   }
/*  199:     */   
/*  200:     */   class AbsorbAuxiliary
/*  201:     */     extends BasicRule
/*  202:     */   {
/*  203:     */     AbsorbAuxiliary() {}
/*  204:     */     
/*  205:     */     public void run()
/*  206:     */     {
/*  207: 257 */       super.run();
/*  208: 258 */       if ((this.firstLinkSubject.isAPrimed("action")) && (this.firstLink.isA("auxiliary")))
/*  209:     */       {
/*  210: 259 */         if (this.firstLinkObject.isAPrimed("do")) {
/*  211: 260 */           return;
/*  212:     */         }
/*  213: 262 */         if (this.firstLinkObject.isAPrimed("to")) {
/*  214: 263 */           return;
/*  215:     */         }
/*  216: 265 */         this.firstLinkSubject.addFeature(this.firstLinkObject.getType());
/*  217: 266 */         remove(this.firstLink);
/*  218: 267 */         succeeded();
/*  219:     */       }
/*  220:     */     }
/*  221:     */   }
/*  222:     */   
/*  223:     */   class AbsorbNegation
/*  224:     */     extends BasicRule
/*  225:     */   {
/*  226:     */     AbsorbNegation() {}
/*  227:     */     
/*  228:     */     public void run()
/*  229:     */     {
/*  230: 281 */       super.run();
/*  231: 282 */       if ((this.firstLinkSubject.isAPrimed("action")) && (this.firstLink.isA("negation-modifier")))
/*  232:     */       {
/*  233: 283 */         this.firstLinkSubject.addFeature(this.firstLinkObject.getType());
/*  234: 284 */         remove(this.firstLink);
/*  235: 285 */         succeeded();
/*  236:     */       }
/*  237:     */     }
/*  238:     */   }
/*  239:     */   
/*  240:     */   class ProcessRegion
/*  241:     */     extends BasicRule2
/*  242:     */   {
/*  243:     */     ProcessRegion() {}
/*  244:     */     
/*  245:     */     public void run()
/*  246:     */     {
/*  247: 298 */       super.run();
/*  248: 299 */       if ((this.firstLinkSubject.isA("location")) && (this.firstLinkObject.isAPrimed("of")) && 
/*  249: 300 */         (this.secondLinkSubject == this.firstLinkObject) && (this.secondLinkObject.isAPrimed("entity")))
/*  250:     */       {
/*  251: 301 */         Function place = new Function("place", this.secondLinkObject);
/*  252: 302 */         remove(this.firstLink);
/*  253: 303 */         remove(this.secondLink);
/*  254: 304 */         transferTypes(this.firstLinkSubject, place);
/*  255: 305 */         replace(this.firstLinkSubject, place);
/*  256: 306 */         succeeded();
/*  257:     */       }
/*  258:     */     }
/*  259:     */   }
/*  260:     */   
/*  261:     */   class ProcessClassification
/*  262:     */     extends BasicRule2
/*  263:     */   {
/*  264:     */     ProcessClassification() {}
/*  265:     */     
/*  266:     */     public void run()
/*  267:     */     {
/*  268: 335 */       super.run();
/*  269: 336 */       if ((this.firstLinkSubject.isAPrimed("entity")) && 
/*  270: 337 */         (this.secondLinkSubject == this.firstLinkSubject) && (this.secondLinkObject.isAPrimed("is")))
/*  271:     */       {
/*  272: 338 */         Thread thread = this.firstLinkObject.getThread("feature");
/*  273:     */         Relation classification;
/*  274:     */         Relation classification;
/*  275: 340 */         if (thread == null)
/*  276:     */         {
/*  277: 341 */           classification = new Relation("classification", this.firstLinkSubject, this.firstLinkObject);
/*  278:     */         }
/*  279:     */         else
/*  280:     */         {
/*  281:     */           Relation classification;
/*  282: 343 */           if (thread.contains("indefinite"))
/*  283:     */           {
/*  284:     */             Relation classification;
/*  285: 344 */             if (this.firstLinkObject.isAPrimed("unknownWord")) {
/*  286: 345 */               classification = new Relation("type", this.firstLinkSubject, this.firstLinkObject);
/*  287:     */             } else {
/*  288: 348 */               classification = new Relation("classification", this.firstLinkSubject, this.firstLinkObject);
/*  289:     */             }
/*  290:     */           }
/*  291:     */           else
/*  292:     */           {
/*  293:     */             Relation classification;
/*  294: 351 */             if (thread.contains("definite")) {
/*  295: 352 */               classification = new Relation("classification", this.firstLinkSubject, this.firstLinkObject);
/*  296:     */             } else {
/*  297: 355 */               classification = new Relation("classification", this.firstLinkSubject, this.firstLinkObject);
/*  298:     */             }
/*  299:     */           }
/*  300:     */         }
/*  301: 357 */         String type = this.firstLinkObject.getType();
/*  302:     */         
/*  303:     */ 
/*  304:     */ 
/*  305:     */ 
/*  306: 362 */         replace(this.firstLink, new Relation("link", NewRuleSet.this.root, classification));
/*  307: 363 */         remove(this.secondLink);
/*  308: 364 */         succeeded();
/*  309:     */       }
/*  310:     */     }
/*  311:     */   }
/*  312:     */   
/*  313:     */   private boolean inclusion(Entity secondLink, Entity firstLink)
/*  314:     */   {
/*  315: 377 */     if (secondLink == firstLink) {
/*  316: 378 */       return true;
/*  317:     */     }
/*  318: 383 */     if (firstLink.entityP()) {
/*  319: 384 */       return false;
/*  320:     */     }
/*  321: 386 */     if (firstLink.functionP()) {
/*  322: 387 */       return inclusion(secondLink, ((Function)firstLink).getSubject());
/*  323:     */     }
/*  324: 389 */     if (firstLink.relationP())
/*  325:     */     {
/*  326: 390 */       Relation r = (Relation)firstLink;
/*  327: 391 */       return (inclusion(secondLink, r.getSubject())) || (inclusion(secondLink, r.getObject()));
/*  328:     */     }
/*  329: 393 */     if (firstLink.sequenceP())
/*  330:     */     {
/*  331: 394 */       Sequence s = (Sequence)firstLink;
/*  332: 395 */       for (Entity element : s.getElements()) {
/*  333: 396 */         if (inclusion(secondLink, element)) {
/*  334: 397 */           return true;
/*  335:     */         }
/*  336:     */       }
/*  337:     */     }
/*  338: 401 */     return false;
/*  339:     */   }
/*  340:     */   
/*  341:     */   class DirectionExpert
/*  342:     */     extends BasicRule
/*  343:     */   {
/*  344:     */     DirectionExpert() {}
/*  345:     */     
/*  346:     */     public void run()
/*  347:     */     {
/*  348: 437 */       super.run();
/*  349: 438 */       if ((this.firstLink.isA(NewRuleSet.trajectoryWords)) && (this.firstLinkSubject.isA("thing")) && 
/*  350: 439 */         (this.firstLinkObject.isA(NewRuleSet.directionWords)))
/*  351:     */       {
/*  352: 440 */         Sequence path = JFactory.createPath();
/*  353: 441 */         Relation move = new Relation("action", this.firstLinkSubject, path);
/*  354: 442 */         Bundle b = this.firstLink.getBundle();
/*  355: 443 */         b = b.filterFor("action");
/*  356: 444 */         b = b.filterFor(NewRuleSet.trajectoryWords);
/*  357: 445 */         b = b.filterForNot("entity");
/*  358: 446 */         move.setBundle(b);
/*  359: 447 */         addTypeAfterReference("action", "trajectory", move);
/*  360: 448 */         Function at = new Function("at", this.firstLinkObject);
/*  361: 449 */         Function toward = new Function("path-element", at);
/*  362: 450 */         toward.addType("toward");
/*  363: 451 */         path.addElement(toward);
/*  364: 452 */         replace(this.firstLink, move);
/*  365: 453 */         succeeded();
/*  366:     */       }
/*  367:     */     }
/*  368:     */   }
/*  369:     */   
/*  370:     */   class LocationExpert
/*  371:     */     extends BasicRule
/*  372:     */   {
/*  373:     */     LocationExpert() {}
/*  374:     */     
/*  375:     */     public void run()
/*  376:     */     {
/*  377: 465 */       super.run();
/*  378: 466 */       if ((this.firstLink.isA("be")) && (this.firstLinkSubject.isA("thing")) && (!this.firstLink.isA(NewRuleSet.transitionWords)) && 
/*  379: 467 */         (this.firstLink.isNotA("enter")))
/*  380:     */       {
/*  381: 468 */         Sequence path = new Sequence("location");
/*  382: 469 */         if (this.firstLinkObject == Markers.NULL)
/*  383:     */         {
/*  384: 470 */           Relation place = new Relation("place", this.firstLinkSubject, path);
/*  385: 471 */           replace(this.firstLink, place);
/*  386: 472 */           succeeded();
/*  387:     */         }
/*  388:     */       }
/*  389:     */     }
/*  390:     */   }
/*  391:     */   
/*  392:     */   class AtExpert
/*  393:     */     extends BasicRule
/*  394:     */   {
/*  395:     */     AtExpert() {}
/*  396:     */     
/*  397:     */     public void run()
/*  398:     */     {
/*  399: 481 */       super.run();
/*  400: 482 */       if (this.firstLink.isAPrimed("at"))
/*  401:     */       {
/*  402: 483 */         Relation place = (Relation)this.firstLinkSubject;
/*  403: 484 */         Function at = new Function("at", this.firstLinkObject);
/*  404: 485 */         place.getObject().addElement(at);
/*  405: 486 */         replace(this.firstLink, place);
/*  406: 487 */         succeeded();
/*  407:     */       }
/*  408:     */     }
/*  409:     */   }
/*  410:     */   
/*  411:     */   class PathElementExpert
/*  412:     */     extends BasicRule
/*  413:     */   {
/*  414:     */     PathElementExpert() {}
/*  415:     */     
/*  416:     */     public void run()
/*  417:     */     {
/*  418: 497 */       super.run();
/*  419: 498 */       if ((this.firstLink.isAPrimed(NewRuleSet.pathPrepositions)) && (this.firstLinkObject.isA("thing")) && 
/*  420: 499 */         (this.firstLinkSubject.isAPrimed(NewRuleSet.trajectoryWords)) && (this.firstLinkSubject.relationP()))
/*  421:     */       {
/*  422: 500 */         Relation r = (Relation)this.firstLinkSubject;
/*  423: 501 */         if ((r.getObject().isAPrimed(NewRuleSet.pathWords)) && (r.getObject().sequenceP()))
/*  424:     */         {
/*  425: 502 */           Sequence path = (Sequence)r.getObject();
/*  426:     */           Entity place;
/*  427:     */           Entity place;
/*  428: 504 */           if ((this.firstLinkObject.isAPrimed(NewRuleSet.placePrepositions)) && (!this.firstLinkObject.isAPrimed(NewRuleSet.directionWords))) {
/*  429: 505 */             place = this.firstLinkObject;
/*  430:     */           } else {
/*  431: 508 */             place = new Function("at", this.firstLinkObject);
/*  432:     */           }
/*  433: 510 */           Function pathFunction = new Function("path-element", place);
/*  434: 511 */           pathFunction.addType(this.firstLink.getType());
/*  435: 512 */           path.addElement(pathFunction);
/*  436: 513 */           remove(this.firstLink);
/*  437: 514 */           succeeded();
/*  438:     */         }
/*  439:     */       }
/*  440:     */     }
/*  441:     */   }
/*  442:     */   
/*  443:     */   class LocationElementExpert
/*  444:     */     extends BasicRule
/*  445:     */   {
/*  446:     */     LocationElementExpert() {}
/*  447:     */     
/*  448:     */     public void run()
/*  449:     */     {
/*  450: 526 */       super.run();
/*  451: 527 */       if ((this.firstLink.isAPrimed(NewRuleSet.locationPrepositions)) && (this.firstLinkObject.isA("thing")) && 
/*  452: 528 */         (this.firstLinkSubject.relationP("place")))
/*  453:     */       {
/*  454: 529 */         Relation r = (Relation)this.firstLinkSubject;
/*  455: 530 */         if ((r.getObject().isAPrimed(NewRuleSet.pathWords)) && (r.getObject().sequenceP()))
/*  456:     */         {
/*  457: 531 */           Sequence path = (Sequence)r.getObject();
/*  458:     */           Entity place;
/*  459:     */           Entity place;
/*  460: 533 */           if (this.firstLinkObject.isAPrimed(NewRuleSet.placePrepositions)) {
/*  461: 534 */             place = this.firstLinkObject;
/*  462:     */           } else {
/*  463: 537 */             place = new Function("at", this.firstLinkObject);
/*  464:     */           }
/*  465: 539 */           Function pathFunction = new Function("path-element", place);
/*  466: 540 */           pathFunction.addType(this.firstLink.getType());
/*  467: 541 */           path.addElement(pathFunction);
/*  468: 542 */           remove(this.firstLink);
/*  469: 543 */           succeeded();
/*  470:     */         }
/*  471:     */       }
/*  472:     */     }
/*  473:     */   }
/*  474:     */   
/*  475:     */   class PlaceExpert
/*  476:     */     extends BasicRule
/*  477:     */   {
/*  478:     */     public PlaceExpert() {}
/*  479:     */     
/*  480:     */     public void run()
/*  481:     */     {
/*  482: 559 */       super.run();
/*  483: 560 */       if ((this.firstLink.isAPrimed("related-to")) && (this.firstLinkObject.isAPrimed("entity")) && (this.firstLinkSubject.isAPrimed("region")))
/*  484:     */       {
/*  485: 561 */         Function place = new Function("dummy", this.firstLinkObject);
/*  486: 562 */         Bundle b = this.firstLinkSubject.getBundle();
/*  487: 563 */         b = b.filterFor("region");
/*  488: 564 */         place.setBundle(b);
/*  489: 565 */         addTypeAfterReference("region", "place", place);
/*  490: 566 */         replace(this.firstLinkSubject, place);
/*  491: 567 */         remove(this.firstLink);
/*  492: 568 */         succeeded();
/*  493:     */       }
/*  494:     */     }
/*  495:     */   }
/*  496:     */   
/*  497:     */   class TransitionExpert
/*  498:     */     extends BasicRule
/*  499:     */   {
/*  500:     */     TransitionExpert() {}
/*  501:     */     
/*  502:     */     public void run()
/*  503:     */     {
/*  504: 579 */       super.run();
/*  505: 580 */       if ((this.firstLink.relationP()) && (NewRuleSet.transitionWords.contains(this.firstLink.getType())) && (this.firstLinkSubject.isAPrimed("entity")) && 
/*  506: 581 */         (this.firstLinkObject == Markers.NULL))
/*  507:     */       {
/*  508: 583 */         Function transition = new Function("action", this.firstLinkSubject);
/*  509: 584 */         transition.addType("transition");
/*  510: 585 */         transition.addType(this.firstLink.getType());
/*  511: 586 */         if ((this.firstLink.getTypes().contains("occur")) || (this.firstLink.getTypes().contains("happen"))) {
/*  512: 587 */           transition.addType("appear");
/*  513:     */         }
/*  514: 589 */         replace(this.firstLink, transition);
/*  515: 590 */         succeeded();
/*  516:     */       }
/*  517:     */     }
/*  518:     */   }
/*  519:     */   
/*  520:     */   class EventExpert
/*  521:     */     extends BasicRule
/*  522:     */   {
/*  523:     */     EventExpert() {}
/*  524:     */     
/*  525:     */     public void run()
/*  526:     */     {
/*  527: 600 */       super.run();
/*  528: 601 */       if ((this.firstLink.functionP()) && (NewRuleSet.transitionWords.contains(this.firstLink.getType())) && 
/*  529: 602 */         (this.firstLinkSubject.isAPrimed("action")))
/*  530:     */       {
/*  531: 603 */         replace(this.firstLink, this.firstLinkSubject);
/*  532: 604 */         succeeded();
/*  533:     */       }
/*  534:     */     }
/*  535:     */   }
/*  536:     */   
/*  537:     */   class BecomeExpert
/*  538:     */     extends BasicRule
/*  539:     */   {
/*  540:     */     BecomeExpert() {}
/*  541:     */     
/*  542:     */     public void run()
/*  543:     */     {
/*  544: 615 */       super.run();
/*  545: 616 */       if ((this.firstLink.relationP()) && (this.firstLink.isAPrimed("become")))
/*  546:     */       {
/*  547:     */         Relation relation;
/*  548:     */         Relation relation;
/*  549: 618 */         if ((this.firstLinkObject.isAPrimed(NewRuleSet.positiveWords)) || (this.firstLinkObject.isAPrimed(NewRuleSet.negativeWords)) || (this.firstLinkObject.isAPrimed("emotion")))
/*  550:     */         {
/*  551: 620 */           Entity quality = new Entity("mental-state");
/*  552: 621 */           if (this.firstLinkObject.isAPrimed(NewRuleSet.positiveWords)) {
/*  553: 622 */             quality.addType("happy");
/*  554: 624 */           } else if (this.firstLinkObject.isAPrimed(NewRuleSet.negativeWords)) {
/*  555: 625 */             quality.addType("unhappy");
/*  556:     */           }
/*  557: 627 */           relation = new Relation("has-mental-state", this.firstLinkSubject, quality);
/*  558:     */         }
/*  559:     */         else
/*  560:     */         {
/*  561: 630 */           relation = new Relation("classification", this.firstLinkObject, this.firstLinkSubject);
/*  562:     */         }
/*  563: 633 */         Function transition = Constructors.constructAppearTransition(relation);
/*  564: 634 */         replace(this.firstLink, transition);
/*  565: 635 */         succeeded();
/*  566:     */       }
/*  567:     */     }
/*  568:     */   }
/*  569:     */   
/*  570:     */   class NegationExpert
/*  571:     */     extends BasicRule
/*  572:     */   {
/*  573:     */     NegationExpert() {}
/*  574:     */     
/*  575:     */     public void run()
/*  576:     */     {
/*  577: 645 */       super.run();
/*  578: 647 */       if ((this.firstLink.relationP()) && (this.firstLink.isAPrimed("is_negative")) && (this.firstLinkObject.isAPrimed("yes")))
/*  579:     */       {
/*  580: 650 */         this.firstLinkSubject.addFeature("not");
/*  581:     */         
/*  582: 652 */         remove(this.firstLink);
/*  583: 653 */         succeeded();
/*  584:     */       }
/*  585:     */     }
/*  586:     */   }
/*  587:     */   
/*  588:     */   class SometimesTrap
/*  589:     */     extends BasicRule
/*  590:     */   {
/*  591:     */     SometimesTrap() {}
/*  592:     */     
/*  593:     */     public void run()
/*  594:     */     {
/*  595: 660 */       super.run();
/*  596: 661 */       if ((this.firstLink.relationP()) && (this.firstLink.isAPrimed("has_modifier")))
/*  597:     */       {
/*  598: 662 */         if (this.firstLinkObject.isA("sometimes")) {
/*  599: 663 */           this.firstLinkSubject.addProperty("idiom", "sometimes");
/*  600:     */         }
/*  601: 665 */         Relation property = new Relation("property", this.firstLinkSubject, this.firstLinkObject);
/*  602: 666 */         replace(this.firstLink, property);
/*  603: 667 */         succeeded();
/*  604:     */       }
/*  605:     */     }
/*  606:     */   }
/*  607:     */   
/*  608:     */   class IntensifierExpert
/*  609:     */     extends BasicRule
/*  610:     */   {
/*  611:     */     IntensifierExpert() {}
/*  612:     */     
/*  613:     */     public void run()
/*  614:     */     {
/*  615: 674 */       super.run();
/*  616: 675 */       if (this.firstLink.relationP("has_intensifier"))
/*  617:     */       {
/*  618: 677 */         this.firstLinkSubject.addFeature(this.firstLinkObject.getType());
/*  619: 678 */         remove(this.firstLink);
/*  620: 679 */         succeeded();
/*  621:     */       }
/*  622:     */     }
/*  623:     */   }
/*  624:     */   
/*  625:     */   class WouldLikeExpert
/*  626:     */     extends BasicRule2
/*  627:     */   {
/*  628:     */     WouldLikeExpert() {}
/*  629:     */     
/*  630:     */     public void run()
/*  631:     */     {
/*  632: 686 */       super.run();
/*  633: 687 */       Entity firstLinkRole = StoryProcessor.extractObjectRole(this.firstLink);
/*  634: 688 */       if ((this.secondLink == firstLinkRole) && (firstLinkRole != null) && (this.firstLink.isA("like")))
/*  635:     */       {
/*  636: 689 */         Relation relation = new Relation("action", this.firstLinkSubject, this.firstLinkObject);
/*  637: 690 */         relation.addType("goal");
/*  638: 691 */         relation.addType("desire");
/*  639: 692 */         relation.addType("want");
/*  640: 693 */         relation.addProperty("processed", Boolean.valueOf(true));
/*  641:     */         
/*  642: 695 */         remove(this.secondLink);
/*  643: 696 */         replace(this.firstLink, relation);
/*  644:     */         
/*  645: 698 */         succeeded();
/*  646:     */       }
/*  647:     */     }
/*  648:     */   }
/*  649:     */   
/*  650:     */   class ModeExpert
/*  651:     */     extends BasicRule
/*  652:     */   {
/*  653:     */     ModeExpert() {}
/*  654:     */     
/*  655:     */     public void run()
/*  656:     */     {
/*  657: 709 */       super.run();
/*  658: 710 */       if ((this.firstLink.relationP()) && (this.firstLink.isAPrimed("has_modal")))
/*  659:     */       {
/*  660: 711 */         this.firstLinkSubject.addProperty("modal", this.firstLinkObject.getType());
/*  661: 712 */         if (this.firstLinkSubject.isA("like"))
/*  662:     */         {
/*  663: 713 */           remove(this.firstLink);
/*  664: 714 */           succeeded();
/*  665:     */         }
/*  666:     */         else
/*  667:     */         {
/*  668: 717 */           if (this.firstLinkObject.isAPrimed("may"))
/*  669:     */           {
/*  670: 719 */             this.firstLinkSubject.addProperty("certainty", "tentative");
/*  671:     */           }
/*  672: 721 */           else if (this.firstLinkObject.isAPrimed("must"))
/*  673:     */           {
/*  674: 722 */             this.firstLinkSubject.addProperty("imperative", Boolean.valueOf(true));
/*  675:     */           }
/*  676: 725 */           else if ((this.firstLinkObject.isAPrimed("will")) && (!this.firstLinkSubject.isAPrimed("happen")))
/*  677:     */           {
/*  678: 726 */             Function function = new Function("expectation", this.firstLinkSubject);
/*  679: 727 */             addLink(function);
/*  680:     */           }
/*  681: 729 */           remove(this.firstLink);
/*  682: 730 */           succeeded();
/*  683:     */         }
/*  684:     */       }
/*  685: 733 */       else if ((this.firstLink.relationP()) && (this.firstLink.isAPrimed("has_modifier")))
/*  686:     */       {
/*  687: 734 */         this.firstLinkSubject.addFeature(this.firstLinkObject.getType());
/*  688: 735 */         Relation property = new Relation("property", this.firstLinkSubject, this.firstLinkObject);
/*  689: 736 */         replace(this.firstLink, property);
/*  690: 737 */         succeeded();
/*  691:     */       }
/*  692:     */     }
/*  693:     */   }
/*  694:     */   
/*  695:     */   class TenseExpert
/*  696:     */     extends BasicRule
/*  697:     */   {
/*  698:     */     TenseExpert() {}
/*  699:     */     
/*  700:     */     public void run()
/*  701:     */     {
/*  702: 747 */       super.run();
/*  703: 748 */       if ((this.firstLink.relationP()) && (this.firstLink.isAPrimed("has_tense")))
/*  704:     */       {
/*  705: 749 */         this.firstLinkSubject.addProperty("tense", this.firstLinkObject.getType());
/*  706: 751 */         if (this.links.containsDeprecated(this.firstLinkSubject)) {
/*  707: 752 */           remove(this.firstLink);
/*  708:     */         } else {
/*  709: 755 */           replace(this.firstLink, this.firstLinkSubject);
/*  710:     */         }
/*  711: 757 */         succeeded();
/*  712:     */       }
/*  713:     */     }
/*  714:     */   }
/*  715:     */   
/*  716:     */   class ProgressiveExpert
/*  717:     */     extends BasicRule
/*  718:     */   {
/*  719:     */     ProgressiveExpert() {}
/*  720:     */     
/*  721:     */     public void run()
/*  722:     */     {
/*  723: 767 */       super.run();
/*  724: 768 */       if ((this.firstLink.relationP()) && (this.firstLink.isAPrimed("is_progressive")))
/*  725:     */       {
/*  726: 769 */         this.firstLinkSubject.addProperty("progressive", Boolean.valueOf(true));
/*  727: 770 */         replace(this.firstLink, this.firstLinkSubject);
/*  728: 771 */         succeeded();
/*  729:     */       }
/*  730:     */     }
/*  731:     */   }
/*  732:     */   
/*  733:     */   class QuantityExpert
/*  734:     */     extends BasicRule
/*  735:     */   {
/*  736:     */     QuantityExpert() {}
/*  737:     */     
/*  738:     */     public void run()
/*  739:     */     {
/*  740: 781 */       super.run();
/*  741: 782 */       if ((this.firstLink.relationP()) && (this.firstLink.isAPrimed("has_quantity")))
/*  742:     */       {
/*  743: 783 */         this.firstLinkSubject.addProperty("quantity", this.firstLinkObject.getType());
/*  744: 784 */         replace(this.firstLink, this.firstLinkSubject);
/*  745: 785 */         succeeded();
/*  746:     */       }
/*  747:     */     }
/*  748:     */   }
/*  749:     */   
/*  750:     */   class MainClauseExpert
/*  751:     */     extends BasicRule
/*  752:     */   {
/*  753:     */     MainClauseExpert() {}
/*  754:     */     
/*  755:     */     public void run()
/*  756:     */     {
/*  757: 795 */       super.run();
/*  758: 796 */       if ((this.firstLink.relationP()) && (this.firstLink.isAPrimed("is_main")))
/*  759:     */       {
/*  760: 797 */         this.firstLinkSubject.addProperty("is_main", Boolean.valueOf(true));
/*  761: 798 */         this.firstLinkSubject.addProperty("clause_holders", new Vector());
/*  762: 801 */         if (this.links.containsDeprecated(this.firstLinkSubject)) {
/*  763: 802 */           remove(this.firstLink);
/*  764:     */         } else {
/*  765: 805 */           replace(this.firstLink, this.firstLinkSubject);
/*  766:     */         }
/*  767: 807 */         succeeded();
/*  768:     */       }
/*  769:     */     }
/*  770:     */   }
/*  771:     */   
/*  772:     */   class RelativeClauseExpert
/*  773:     */     extends BasicRule
/*  774:     */   {
/*  775:     */     RelativeClauseExpert() {}
/*  776:     */     
/*  777:     */     public void run()
/*  778:     */     {
/*  779: 817 */       super.run();
/*  780: 818 */       if ((this.firstLink.relationP()) && (this.firstLink.isAPrimed("has_rel_clause")))
/*  781:     */       {
/*  782: 819 */         this.firstLinkObject.addProperty("has_rel_clause", this.firstLinkSubject);
/*  783: 820 */         this.firstLinkSubject.addProperty("clauses", new Vector());
/*  784: 821 */         Vector<Entity> clauses = (Vector)this.firstLinkSubject.getProperty("clauses");
/*  785: 822 */         clauses.add(this.firstLinkObject);
/*  786: 823 */         replace(this.firstLink, this.firstLinkObject);
/*  787: 824 */         succeeded();
/*  788:     */       }
/*  789:     */     }
/*  790:     */   }
/*  791:     */   
/*  792:     */   class SometimesExpert
/*  793:     */     extends BasicRule
/*  794:     */   {
/*  795:     */     SometimesExpert() {}
/*  796:     */     
/*  797:     */     public void run()
/*  798:     */     {
/*  799: 834 */       super.run();
/*  800: 835 */       if ((Predicates.isCause(this.firstLink)) && (!this.firstLink.hasProperty("idiom", "sometimes"))) {
/*  801: 836 */         if (this.firstLinkObject.hasProperty("idiom", "sometimes"))
/*  802:     */         {
/*  803: 837 */           this.firstLink.addProperty("idiom", "sometimes");
/*  804: 838 */           succeeded();
/*  805:     */         }
/*  806:     */         else
/*  807:     */         {
/*  808: 841 */           for (Entity e : this.firstLinkSubject.getElements()) {
/*  809: 842 */             if (e.hasProperty("idiom", "sometimes"))
/*  810:     */             {
/*  811: 843 */               this.firstLink.addProperty("idiom", "sometimes");
/*  812: 844 */               succeeded();
/*  813: 845 */               break;
/*  814:     */             }
/*  815:     */           }
/*  816:     */         }
/*  817:     */       }
/*  818:     */     }
/*  819:     */   }
/*  820:     */   
/*  821:     */   class EachOtherExpert
/*  822:     */     extends BasicRule2
/*  823:     */   {
/*  824:     */     EachOtherExpert() {}
/*  825:     */     
/*  826:     */     public void run()
/*  827:     */     {
/*  828: 855 */       super.run();
/*  829: 856 */       Entity firstObject = Getters.getObject(this.firstLink);
/*  830: 857 */       Entity secondObject = Getters.getObject(this.secondLink);
/*  831: 858 */       if ((firstObject != null) && (secondObject != null) && 
/*  832: 859 */         (firstObject.isA("each_other")) && 
/*  833: 860 */         (Getters.getObject(this.firstLink) == Getters.getObject(this.secondLink)))
/*  834:     */       {
/*  835: 861 */         Getters.getSlot("object", this.firstLink).setSubject(this.secondLink.getSubject());
/*  836: 862 */         Getters.getSlot("object", this.secondLink).setSubject(this.firstLink.getSubject());
/*  837: 863 */         succeeded();
/*  838:     */       }
/*  839:     */     }
/*  840:     */   }
/*  841:     */   
/*  842:     */   class ClauseExpert
/*  843:     */     extends BasicRule2
/*  844:     */   {
/*  845:     */     ClauseExpert() {}
/*  846:     */     
/*  847:     */     public void run()
/*  848:     */     {
/*  849: 875 */       super.run();
/*  850: 876 */       if ((this.firstLink.hasProperty("is_main", Boolean.valueOf(true))) && (this.secondLink.hasProperty("has_rel_clause")))
/*  851:     */       {
/*  852: 877 */         Vector<Entity> holders = (Vector)this.firstLink.getProperty("clause_holders");
/*  853: 878 */         Entity holder = (Entity)this.secondLink.getProperty("has_rel_clause");
/*  854: 879 */         if (!holders.contains(holder)) {
/*  855: 880 */           holders.add((Entity)this.secondLink.getProperty("has_rel_clause"));
/*  856:     */         }
/*  857: 882 */         Object clauseObject = holder.getProperty("clauses");
/*  858:     */         Vector<Entity> clauses;
/*  859: 884 */         if (clauseObject == null)
/*  860:     */         {
/*  861: 885 */           Vector<Entity> clauses = new Vector();
/*  862: 886 */           holder.addProperty("clauses", clauses);
/*  863:     */         }
/*  864:     */         else
/*  865:     */         {
/*  866: 889 */           clauses = (Vector)clauseObject;
/*  867:     */         }
/*  868: 892 */         if (!clauses.contains(this.secondLink)) {
/*  869: 893 */           clauses.add(this.secondLink);
/*  870:     */         }
/*  871: 895 */         remove(this.secondLink);
/*  872: 896 */         succeeded();
/*  873:     */       }
/*  874:     */     }
/*  875:     */   }
/*  876:     */   
/*  877:     */   class PossibilityExpert
/*  878:     */     extends BasicRule
/*  879:     */   {
/*  880:     */     PossibilityExpert() {}
/*  881:     */     
/*  882:     */     public void run()
/*  883:     */     {
/*  884: 919 */       super.run();
/*  885: 920 */       if ((this.firstLink.isA("property")) && (this.firstLinkObject.isAPrimed("possibly")))
/*  886:     */       {
/*  887: 921 */         this.firstLinkSubject.addFeature("possibly");
/*  888: 922 */         remove(this.firstLink);
/*  889: 923 */         succeeded();
/*  890:     */       }
/*  891:     */     }
/*  892:     */   }
/*  893:     */   
/*  894:     */   class AttributeExpert
/*  895:     */     extends BasicRule
/*  896:     */   {
/*  897:     */     AttributeExpert() {}
/*  898:     */     
/*  899:     */     public void run()
/*  900:     */     {
/*  901: 933 */       super.run();
/*  902: 934 */       if ((this.firstLink.relationP()) && (this.firstLink.isAPrimed("has_attribute")))
/*  903:     */       {
/*  904: 935 */         remove(this.firstLink);
/*  905: 936 */         succeeded();
/*  906:     */       }
/*  907:     */     }
/*  908:     */   }
/*  909:     */   
/*  910:     */   class TimeExpert
/*  911:     */     extends BasicRule
/*  912:     */   {
/*  913:     */     TimeExpert() {}
/*  914:     */     
/*  915:     */     public void run()
/*  916:     */     {
/*  917: 946 */       super.run();
/*  918: 947 */       if ((this.firstLink.isAPrimed(NewRuleSet.access$0())) && (!this.firstLink.isAPrimed("time-relation")))
/*  919:     */       {
/*  920: 948 */         Thread thread = new Thread("thing");
/*  921: 949 */         thread.addType("action");
/*  922: 950 */         thread.addType("time-relation");
/*  923: 951 */         thread.addType(this.firstLink.getType());
/*  924: 952 */         this.firstLink.setBundle(new Bundle(thread));
/*  925:     */         
/*  926:     */ 
/*  927:     */ 
/*  928:     */ 
/*  929:     */ 
/*  930:     */ 
/*  931:     */ 
/*  932:     */ 
/*  933:     */ 
/*  934: 962 */         succeeded();
/*  935:     */       }
/*  936:     */     }
/*  937:     */   }
/*  938:     */   
/*  939:     */   class EntailExpert
/*  940:     */     extends BasicRule
/*  941:     */   {
/*  942:     */     EntailExpert() {}
/*  943:     */     
/*  944:     */     public void run()
/*  945:     */     {
/*  946: 969 */       super.run();
/*  947: 970 */       if ((this.firstLink.relationP("entail")) && (!this.firstLink.isAPrimed("cause")))
/*  948:     */       {
/*  949: 971 */         this.firstLink.addType("cause");
/*  950: 972 */         this.firstLink.addType("entail");
/*  951: 973 */         succeeded();
/*  952:     */       }
/*  953:     */     }
/*  954:     */   }
/*  955:     */   
/*  956:     */   class EntailPropertyExpert
/*  957:     */     extends BasicRule
/*  958:     */   {
/*  959:     */     EntailPropertyExpert() {}
/*  960:     */     
/*  961:     */     public void run()
/*  962:     */     {
/*  963: 980 */       super.run();
/*  964: 981 */       if ((this.firstLinkSubject.relationP("entail")) && (this.firstLink.isAPrimed("property")))
/*  965:     */       {
/*  966: 982 */         this.firstLinkSubject.addFeature("sometimes");
/*  967: 983 */         replace(this.firstLink, this.firstLinkSubject);
/*  968: 984 */         succeeded();
/*  969:     */       }
/*  970:     */     }
/*  971:     */   }
/*  972:     */   
/*  973:     */   class FrequencyExpert
/*  974:     */     extends BasicRule
/*  975:     */   {
/*  976:     */     FrequencyExpert() {}
/*  977:     */     
/*  978:     */     public void run()
/*  979:     */     {
/*  980: 991 */       super.run();
/*  981: 992 */       if ((this.firstLink.isAPrimed("property")) && 
/*  982: 993 */         (this.firstLinkObject.isAPrimed(NewRuleSet.frequencyWords)))
/*  983:     */       {
/*  984: 994 */         this.firstLinkSubject.addFeature(this.firstLinkObject.getType());
/*  985:     */         
/*  986: 996 */         remove(this.firstLink);
/*  987: 997 */         succeeded();
/*  988:     */       }
/*  989:     */     }
/*  990:     */   }
/*  991:     */   
/*  992:     */   class NominalizationExpert
/*  993:     */     extends BasicRule
/*  994:     */   {
/*  995:     */     NominalizationExpert() {}
/*  996:     */     
/*  997:     */     public void run()
/*  998:     */     {
/*  999:1008 */       super.run();
/* 1000:1009 */       if ((this.firstLink.isAPrimed("has_effect")) && 
/* 1001:1010 */         (this.firstLinkSubject.isAPrimed("make")))
/* 1002:     */       {
/* 1003:1011 */         Relation relation = new Relation("cause", this.firstLinkSubject.getSubject(), this.firstLinkObject);
/* 1004:1012 */         replace(this.firstLink, relation);
/* 1005:1013 */         succeeded();
/* 1006:     */       }
/* 1007:     */     }
/* 1008:     */   }
/* 1009:     */   
/* 1010:     */   class AgentCauseExpert
/* 1011:     */     extends BasicRule
/* 1012:     */   {
/* 1013:     */     AgentCauseExpert() {}
/* 1014:     */     
/* 1015:     */     public void run()
/* 1016:     */     {
/* 1017:1025 */       super.run();
/* 1018:1026 */       if ((this.firstLink.getType().equals("cause")) && 
/* 1019:1027 */         (this.firstLinkSubject.entityP()))
/* 1020:     */       {
/* 1021:1028 */         Relation action = Innerese.makeRoleFrame("perform", this.firstLinkSubject, new Entity("action"));
/* 1022:1029 */         this.firstLink.setSubject(action);
/* 1023:1030 */         succeeded();
/* 1024:     */       }
/* 1025:     */     }
/* 1026:     */   }
/* 1027:     */   
/* 1028:     */   class MakeExpert
/* 1029:     */     extends BasicRule
/* 1030:     */   {
/* 1031:     */     MakeExpert() {}
/* 1032:     */     
/* 1033:     */     public void run()
/* 1034:     */     {
/* 1035:1041 */       super.run();
/* 1036:1042 */       if (this.firstLink.getType().equals("make"))
/* 1037:     */       {
/* 1038:1043 */         Entity object = Getters.getObject(this.firstLink);
/* 1039:1044 */         if ((object != null) && (object.isA("action"))) {
/* 1040:1046 */           if (this.firstLink.hasProperty("processed"))
/* 1041:     */           {
/* 1042:1047 */             Relation relation = new Relation("cause", this.firstLinkSubject, this.firstLinkObject);
/* 1043:1048 */             replace(this.firstLink, relation);
/* 1044:1049 */             succeeded();
/* 1045:     */           }
/* 1046:     */         }
/* 1047:     */       }
/* 1048:     */     }
/* 1049:     */   }
/* 1050:     */   
/* 1051:     */   class BecauseOfExpert
/* 1052:     */     extends BasicRule
/* 1053:     */   {
/* 1054:     */     BecauseOfExpert() {}
/* 1055:     */     
/* 1056:     */     public void run()
/* 1057:     */     {
/* 1058:1062 */       super.run();
/* 1059:1063 */       if (this.firstLink.isAPrimed("because_of"))
/* 1060:     */       {
/* 1061:1064 */         Relation relation = new Relation("cause", this.firstLinkObject, this.firstLinkSubject);
/* 1062:1065 */         replace(this.firstLink, relation);
/* 1063:1066 */         succeeded();
/* 1064:     */       }
/* 1065:     */     }
/* 1066:     */   }
/* 1067:     */   
/* 1068:     */   class CauseExpert
/* 1069:     */     extends BasicRule
/* 1070:     */   {
/* 1071:     */     CauseExpert() {}
/* 1072:     */     
/* 1073:     */     public void run()
/* 1074:     */     {
/* 1075:1077 */       super.run();
/* 1076:1078 */       if ((this.firstLink.isAPrimed("because")) && (!this.firstLink.isAPrimed("action")))
/* 1077:     */       {
/* 1078:1081 */         Relation relation = new Relation(this.firstLinkObject, this.firstLinkSubject);
/* 1079:1082 */         addTypeAfterReference("thing", "action", relation);
/* 1080:1083 */         addTypeAfterReference("action", "cause", relation);
/* 1081:1088 */         if ((this.firstLinkSubject.relationP()) && (this.firstLinkSubject.isAPrimed("has-mental-state")))
/* 1082:     */         {
/* 1083:1089 */           Relation floRelation = (Relation)this.firstLinkSubject;
/* 1084:1090 */           Entity agent = floRelation.getSubject();
/* 1085:1091 */           String state = floRelation.getObject().getType();
/* 1086:     */           Relation localRelation1;
/* 1087:1092 */           if (state == "happy") {
/* 1088:1093 */             localRelation1 = new Relation("viewsAsPositive", agent, this.firstLinkObject);
/* 1089:1096 */           } else if (state == "unhappy") {
/* 1090:1097 */             localRelation1 = new Relation("viewsAsNegative", agent, this.firstLinkObject);
/* 1091:     */           }
/* 1092:     */         }
/* 1093:1101 */         replace(this.firstLink, relation);
/* 1094:1102 */         remove(this.firstLinkSubject);
/* 1095:1103 */         remove(this.firstLinkObject);
/* 1096:1104 */         succeeded();
/* 1097:     */       }
/* 1098:     */     }
/* 1099:     */   }
/* 1100:     */   
/* 1101:     */   class IfExpert
/* 1102:     */     extends BasicRule
/* 1103:     */   {
/* 1104:     */     IfExpert() {}
/* 1105:     */     
/* 1106:     */     public void run()
/* 1107:     */     {
/* 1108:1114 */       super.run();
/* 1109:1115 */       if ((this.firstLink.isAPrimed("if")) && (this.firstLink.isNotAPrimed("cause")))
/* 1110:     */       {
/* 1111:1116 */         Relation relation = new Relation("if", this.firstLinkObject, this.firstLinkSubject);
/* 1112:1117 */         relation.addType("cause");
/* 1113:1118 */         replace(this.firstLink, relation);
/* 1114:1119 */         succeeded();
/* 1115:     */       }
/* 1116:     */     }
/* 1117:     */   }
/* 1118:     */   
/* 1119:     */   class WhatIfExpert
/* 1120:     */     extends BasicRule2
/* 1121:     */   {
/* 1122:     */     WhatIfExpert() {}
/* 1123:     */     
/* 1124:     */     public void run()
/* 1125:     */     {
/* 1126:1126 */       super.run();
/* 1127:1127 */       if ((this.firstLink.isAPrimed("if")) && (this.firstLinkSubject.isAPrimed("happen"))) {
/* 1128:1128 */         if (this.secondLink == this.firstLinkSubject)
/* 1129:     */         {
/* 1130:1129 */           remove(this.secondLink);
/* 1131:1130 */           succeeded();
/* 1132:     */         }
/* 1133:1132 */         else if ((this.secondLink.isAPrimed("is_question")) && (this.firstLinkSubject == this.secondLinkSubject))
/* 1134:     */         {
/* 1135:1133 */           Function question = new Function("question", this.firstLinkObject);
/* 1136:1134 */           question.addType("what_if");
/* 1137:1135 */           replace(this.firstLink, question);
/* 1138:1136 */           remove(this.secondLink);
/* 1139:1137 */           succeeded();
/* 1140:     */         }
/* 1141:     */       }
/* 1142:     */     }
/* 1143:     */   }
/* 1144:     */   
/* 1145:     */   class CauseAntecedantsStarter
/* 1146:     */     extends BasicRule
/* 1147:     */   {
/* 1148:     */     CauseAntecedantsStarter() {}
/* 1149:     */     
/* 1150:     */     public void run()
/* 1151:     */     {
/* 1152:1149 */       super.run();
/* 1153:1150 */       if (((this.firstLink.isAPrimed("cause")) || (this.firstLink.isAPrimed("enable"))) && (!this.firstLinkSubject.isA("conjuction")))
/* 1154:     */       {
/* 1155:1151 */         if (this.firstLink.isAPrimed("enable")) {
/* 1156:1153 */           addTypeBeforeLast("cause", this.firstLink);
/* 1157:     */         }
/* 1158:1155 */         Sequence antecedants = new Sequence("conjuction");
/* 1159:1156 */         antecedants.addElement(this.firstLinkSubject);
/* 1160:1157 */         this.firstLink.setSubject(antecedants);
/* 1161:     */         
/* 1162:1159 */         succeeded();
/* 1163:     */       }
/* 1164:     */     }
/* 1165:     */   }
/* 1166:     */   
/* 1167:     */   class CheckOnExpert
/* 1168:     */     extends BasicRule2
/* 1169:     */   {
/* 1170:     */     CheckOnExpert() {}
/* 1171:     */     
/* 1172:     */     public void run()
/* 1173:     */     {
/* 1174:1166 */       super.run();
/* 1175:1167 */       if ((this.firstLink.isAPrimed("whether")) && (this.secondLink.isAPrimed("check")) && 
/* 1176:1168 */         (this.firstLinkSubject.isAPrimed("check")))
/* 1177:     */       {
/* 1178:1169 */         this.firstLinkObject.addProperty("idiom", "check");
/* 1179:     */         
/* 1180:     */ 
/* 1181:1172 */         remove(this.secondLink);
/* 1182:1173 */         replace(this.firstLink, this.firstLinkObject);
/* 1183:1174 */         succeeded();
/* 1184:     */       }
/* 1185:     */     }
/* 1186:     */   }
/* 1187:     */   
/* 1188:     */   class CauseAntecedantsExpert
/* 1189:     */     extends BasicRule2
/* 1190:     */   {
/* 1191:     */     CauseAntecedantsExpert() {}
/* 1192:     */     
/* 1193:     */     public void run()
/* 1194:     */     {
/* 1195:1182 */       super.run();
/* 1196:1183 */       if ((this.firstLink.isAPrimed("cause")) && (this.secondLink.isAPrimed("cause")) && 
/* 1197:1184 */         (this.firstLinkObject == this.secondLinkObject) && 
/* 1198:1185 */         (this.firstLinkSubject.isA("conjuction")) && (this.secondLinkSubject.isA("conjuction")))
/* 1199:     */       {
/* 1200:1186 */         mergeElements(this.firstLinkSubject, this.secondLinkSubject);
/* 1201:     */         
/* 1202:     */ 
/* 1203:     */ 
/* 1204:     */ 
/* 1205:     */ 
/* 1206:     */ 
/* 1207:     */ 
/* 1208:     */ 
/* 1209:     */ 
/* 1210:     */ 
/* 1211:     */ 
/* 1212:1198 */         remove(this.secondLink);
/* 1213:1199 */         succeeded();
/* 1214:     */       }
/* 1215:     */     }
/* 1216:     */     
/* 1217:     */     private void mergeElements(Entity firstLinkSubject, Entity secondLinkSubject)
/* 1218:     */     {
/* 1219:1206 */       if ((!this.firstLink.hasProperty("idiom", "sometimes")) && (this.secondLink.hasProperty("idiom", "sometimes"))) {
/* 1220:1207 */         this.firstLink.addFeature("sometimes");
/* 1221:     */       }
/* 1222:1209 */       if (firstLinkSubject == secondLinkSubject) {
/* 1223:1211 */         return;
/* 1224:     */       }
/* 1225:     */       try
/* 1226:     */       {
/* 1227:1214 */         for (Entity e : secondLinkSubject.getElements()) {
/* 1228:1215 */           if (!firstLinkSubject.getElements().contains(e)) {
/* 1229:1219 */             firstLinkSubject.addElement(e);
/* 1230:     */           }
/* 1231:     */         }
/* 1232:     */       }
/* 1233:     */       catch (Exception e)
/* 1234:     */       {
/* 1235:1224 */         Mark.err(new Object[] {"Error in NewRuleSet.CauseAntecedentExpert.mergeElements" });
/* 1236:1225 */         Mark.err(new Object[] {firstLinkSubject.asString() });
/* 1237:1226 */         Mark.err(new Object[] {secondLinkSubject.asString() });
/* 1238:     */       }
/* 1239:     */     }
/* 1240:     */   }
/* 1241:     */   
/* 1242:     */   class CauseAntecedantsFixer
/* 1243:     */     extends BasicRule2
/* 1244:     */   {
/* 1245:     */     CauseAntecedantsFixer() {}
/* 1246:     */     
/* 1247:     */     public void run()
/* 1248:     */     {
/* 1249:1235 */       super.run();
/* 1250:1236 */       if ((this.firstLink.isAPrimed("cause")) && (this.secondLink.isAPrimed("at")) && (this.firstLinkSubject.sequenceP("conjuction")) && 
/* 1251:1237 */         (this.firstLinkSubject.getElements().contains(this.secondLinkSubject)))
/* 1252:     */       {
/* 1253:1238 */         this.firstLinkSubject.getElements().remove(this.secondLinkSubject);
/* 1254:1239 */         this.firstLinkSubject.addElement(this.secondLink);
/* 1255:1240 */         succeeded();
/* 1256:     */       }
/* 1257:     */     }
/* 1258:     */   }
/* 1259:     */   
/* 1260:     */   class PartOfExpert
/* 1261:     */     extends BasicRule2
/* 1262:     */   {
/* 1263:     */     PartOfExpert() {}
/* 1264:     */     
/* 1265:     */     public void run()
/* 1266:     */     {
/* 1267:1264 */       super.run();
/* 1268:1265 */       if ((NewRuleSet.this.isA(this.firstLink)) && (this.secondLink.isAPrimed("of")) && 
/* 1269:1266 */         (this.firstLinkObject.isA("part")) && (this.firstLinkObject == this.secondLinkSubject))
/* 1270:     */       {
/* 1271:1267 */         Relation relation = new Relation("has-part", this.secondLinkObject, this.firstLinkSubject);
/* 1272:1268 */         replace(this.firstLink, relation);
/* 1273:1269 */         remove(this.secondLink);
/* 1274:1270 */         succeeded();
/* 1275:     */       }
/* 1276:     */     }
/* 1277:     */   }
/* 1278:     */   
/* 1279:     */   private boolean sameThing(Entity thingA, Entity thingB)
/* 1280:     */   {
/* 1281:1277 */     if (thingA == thingB) {
/* 1282:1278 */       return true;
/* 1283:     */     }
/* 1284:1280 */     if ((thingA.isA("name")) && (thingB.isA("name")) && (thingA.getType().equals(thingB.getType()))) {
/* 1285:1281 */       return true;
/* 1286:     */     }
/* 1287:1283 */     return false;
/* 1288:     */   }
/* 1289:     */   
/* 1290:     */   class MoodExpert
/* 1291:     */     extends BasicRule2
/* 1292:     */   {
/* 1293:     */     MoodExpert() {}
/* 1294:     */     
/* 1295:     */     public void run()
/* 1296:     */     {
/* 1297:1291 */       super.run();
/* 1298:1292 */       if ((this.firstLink.isAPrimed("is")) && (this.firstLinkObject != Markers.NULL) && 
/* 1299:1293 */         (this.secondLink.isAPrimed("has_property")) && (this.secondLinkObject.isAPrimed(NewRuleSet.mentalStateWords)) && 
/* 1300:1294 */         (NewRuleSet.this.sameThing(this.firstLinkSubject, this.secondLinkSubject)) && (this.firstLinkObject.getType().equals(this.secondLinkObject.getType())))
/* 1301:     */       {
/* 1302:1299 */         Entity mood = this.secondLinkObject;
/* 1303:1300 */         Mark.say(new Object[] {"Mood1", mood.asString() });
/* 1304:     */         
/* 1305:1302 */         addTypeAfterLast("mental-state", mood);
/* 1306:1304 */         if (mood.isAPrimed(NewRuleSet.positiveWords)) {
/* 1307:1305 */           addTypeAfterLast("happy", mood);
/* 1308:1307 */         } else if (mood.isAPrimed(NewRuleSet.negativeWords)) {
/* 1309:1308 */           addTypeAfterLast("unhappy", mood);
/* 1310:     */         } else {
/* 1311:1311 */           addTypeAfterLast(mood.getType(), mood);
/* 1312:     */         }
/* 1313:1313 */         Mark.say(new Object[] {"Mood2", mood.asString() });
/* 1314:1314 */         Relation mentalState = new Relation("has-mental-state", this.firstLinkSubject, mood);
/* 1315:1315 */         replace(this.firstLink, mentalState);
/* 1316:1316 */         remove(this.secondLink);
/* 1317:1317 */         remove(this.thirdLink);
/* 1318:1318 */         succeeded();
/* 1319:     */       }
/* 1320:     */     }
/* 1321:     */   }
/* 1322:     */   
/* 1323:     */   class TransferExpert
/* 1324:     */     extends BasicRule
/* 1325:     */   {
/* 1326:     */     TransferExpert() {}
/* 1327:     */     
/* 1328:     */     public void run()
/* 1329:     */     {
/* 1330:1327 */       super.run();
/* 1331:1328 */       if ((this.firstLink.isAPrimed(NewRuleSet.transferWords)) && (this.firstLinkObject.sequenceP("roles")) && (!this.firstLink.isAPrimed("transfer")))
/* 1332:     */       {
/* 1333:1330 */         addTypeAfterReference("action", "transfer", this.firstLink);
/* 1334:1331 */         succeeded();
/* 1335:     */       }
/* 1336:     */     }
/* 1337:     */   }
/* 1338:     */   
/* 1339:     */   class GoalExpert
/* 1340:     */     extends BasicRule
/* 1341:     */   {
/* 1342:     */     GoalExpert() {}
/* 1343:     */     
/* 1344:     */     public void run()
/* 1345:     */     {
/* 1346:1427 */       super.run();
/* 1347:1428 */       if ((this.firstLink.isAPrimed(NewRuleSet.goalWords)) && 
/* 1348:1429 */         (this.firstLink.isNotA("goal")))
/* 1349:     */       {
/* 1350:1430 */         Bundle b = this.firstLink.getBundle();
/* 1351:1431 */         b = b.filterFor("desire");
/* 1352:1432 */         b = b.filterFor("action");
/* 1353:1433 */         if (b.isEmpty())
/* 1354:     */         {
/* 1355:1434 */           b = this.firstLink.getBundle();
/* 1356:1435 */           b.filterFor("act");
/* 1357:1436 */           b = b.filterFor("action");
/* 1358:     */         }
/* 1359:1439 */         Bundle b2 = this.firstLink.getBundle();
/* 1360:1440 */         b2 = b2.filterFor("feature");
/* 1361:1441 */         b.addAll(b2);
/* 1362:1442 */         this.firstLink.setBundle(b);
/* 1363:1443 */         addTypeAfterReference("action", "goal", this.firstLink);
/* 1364:     */         
/* 1365:1445 */         succeeded();
/* 1366:     */       }
/* 1367:     */     }
/* 1368:     */   }
/* 1369:     */   
/* 1370:     */   class ForceAndPersuadeExpert
/* 1371:     */     extends BasicRule
/* 1372:     */   {
/* 1373:     */     ForceAndPersuadeExpert() {}
/* 1374:     */     
/* 1375:     */     public void run()
/* 1376:     */     {
/* 1377:1454 */       super.run();
/* 1378:1455 */       if ((this.firstLink.isAPrimed("to")) && 
/* 1379:1456 */         ((this.firstLinkSubject.isAPrimed(NewRuleSet.forceWords)) || (this.firstLinkSubject.isAPrimed(NewRuleSet.persuadeWords))) && 
/* 1380:1457 */         (Getters.getObject(this.firstLinkSubject) == this.firstLinkObject.getSubject()))
/* 1381:     */       {
/* 1382:1458 */         this.firstLinkSubject.limitToRoot("action");
/* 1383:1459 */         if (this.firstLinkSubject.isAPrimed(NewRuleSet.forceWords)) {
/* 1384:1461 */           addTypeAfterReference("action", "force", this.firstLinkSubject);
/* 1385:1463 */         } else if (this.firstLinkSubject.isAPrimed(NewRuleSet.persuadeWords)) {
/* 1386:1465 */           if (!this.firstLinkSubject.isAPrimed("persuade")) {
/* 1387:1466 */             addTypeAfterReference("action", "persuade", this.firstLinkSubject);
/* 1388:     */           }
/* 1389:     */         }
/* 1390:1469 */         this.firstLinkSubject.setObject(this.firstLinkObject);
/* 1391:1470 */         replace(this.firstLink, this.firstLinkSubject);
/* 1392:1471 */         succeeded();
/* 1393:     */       }
/* 1394:     */     }
/* 1395:     */   }
/* 1396:     */   
/* 1397:     */   class ImperativesAndQuestionsExpert
/* 1398:     */     extends BasicRule2
/* 1399:     */   {
/* 1400:     */     ImperativesAndQuestionsExpert() {}
/* 1401:     */     
/* 1402:     */     public void run()
/* 1403:     */     {
/* 1404:1569 */       super.run();
/* 1405:1570 */       if (((this.secondLink.isAPrimed("has_attitude")) && (this.secondLinkObject.isAPrimed("imperative"))) || ((this.secondLink.isAPrimed("is_imperative")) && 
/* 1406:1571 */         (this.secondLinkObject.isAPrimed("yes"))))
/* 1407:     */       {
/* 1408:1572 */         if (this.firstLink == this.secondLinkSubject)
/* 1409:     */         {
/* 1410:1573 */           this.firstLink.addProperty("imperative", Boolean.valueOf(true));
/* 1411:1574 */           remove(this.secondLink);
/* 1412:1575 */           succeeded();
/* 1413:     */         }
/* 1414:     */       }
/* 1415:1578 */       else if (((this.secondLink.isAPrimed("has_attitude")) && (this.secondLinkObject.isAPrimed("interrogative"))) || (
/* 1416:1579 */         (this.secondLink.isAPrimed("is_question")) && (this.secondLinkObject.isAPrimed("yes")) && 
/* 1417:1580 */         (this.firstLinkSubject == this.secondLinkSubject))) {
/* 1418:1581 */         if ((this.firstLink.isAPrimed("has_location")) && (this.firstLinkObject.isAPrimed("where")))
/* 1419:     */         {
/* 1420:1582 */           Function question = new Function("question", this.firstLinkSubject);
/* 1421:1583 */           question.addType("where");
/* 1422:1584 */           replace(this.firstLink, question);
/* 1423:1585 */           remove(this.secondLink);
/* 1424:1586 */           succeeded();
/* 1425:     */         }
/* 1426:1588 */         else if ((this.firstLink.isAPrimed("has_purpose")) && (this.firstLinkObject.isAPrimed("why")))
/* 1427:     */         {
/* 1428:1589 */           Function question = new Function("question", this.secondLinkSubject);
/* 1429:1590 */           question.addType("why");
/* 1430:1591 */           replace(this.firstLink, question);
/* 1431:1592 */           remove(this.secondLink);
/* 1432:1593 */           succeeded();
/* 1433:     */         }
/* 1434:1595 */         else if ((this.firstLink.isAPrimed("has_time")) && (this.firstLinkObject.isAPrimed("when")))
/* 1435:     */         {
/* 1436:1596 */           Function question = new Function("question", this.firstLinkSubject);
/* 1437:1597 */           question.addType("when");
/* 1438:1598 */           replace(this.firstLink, question);
/* 1439:1599 */           remove(this.secondLink);
/* 1440:1600 */           succeeded();
/* 1441:     */         }
/* 1442:     */       }
/* 1443:     */     }
/* 1444:     */   }
/* 1445:     */   
/* 1446:     */   class ImperativesAndQuestionsExpert1
/* 1447:     */     extends BasicRule
/* 1448:     */   {
/* 1449:     */     ImperativesAndQuestionsExpert1() {}
/* 1450:     */     
/* 1451:     */     public void run()
/* 1452:     */     {
/* 1453:1609 */       super.run();
/* 1454:1610 */       if (((this.firstLink.isAPrimed("has_attitude")) && (this.firstLinkObject.isAPrimed("interrogative"))) || ((this.firstLink.isAPrimed("question")) && 
/* 1455:1611 */         (this.firstLinkObject.isAPrimed("yes"))))
/* 1456:     */       {
/* 1457:1612 */         Function question = new Function("question", this.firstLinkSubject);
/* 1458:1613 */         question.addType("whether");
/* 1459:1614 */         replace(this.firstLink, question);
/* 1460:1615 */         succeeded();
/* 1461:     */       }
/* 1462:1617 */       else if ((this.firstLink.isAPrimed("imagine")) && (this.firstLinkSubject.isAPrimed("you")) && (this.firstLinkObject.isAPrimed("roles")))
/* 1463:     */       {
/* 1464:1618 */         Entity object = Getters.getObject(this.firstLink);
/* 1465:1619 */         if (object != null)
/* 1466:     */         {
/* 1467:1620 */           replace(this.firstLink, new Function("imagine", object));
/* 1468:1621 */           succeeded();
/* 1469:     */         }
/* 1470:     */       }
/* 1471:     */     }
/* 1472:     */   }
/* 1473:     */   
/* 1474:     */   class ImperativesAndQuestionsExpert2
/* 1475:     */     extends BasicRule2
/* 1476:     */   {
/* 1477:     */     ImperativesAndQuestionsExpert2() {}
/* 1478:     */     
/* 1479:     */     public void run()
/* 1480:     */     {
/* 1481:1629 */       super.run();
/* 1482:1630 */       if ((this.firstLink.isAPrimed("cause")) && 
/* 1483:1631 */         (this.secondLink.isAPrimed("is_question")) && 
/* 1484:1632 */         (this.firstLinkObject == this.secondLinkSubject))
/* 1485:     */       {
/* 1486:1633 */         Function question = new Function("question", this.firstLink);
/* 1487:1634 */         question.addType("did");
/* 1488:1635 */         replace(this.firstLink, question);
/* 1489:1636 */         remove(this.secondLink);
/* 1490:1637 */         succeeded();
/* 1491:     */       }
/* 1492:     */     }
/* 1493:     */   }
/* 1494:     */   
/* 1495:     */   private static Entity makeObject(Entity element, Entity object)
/* 1496:     */   {
/* 1497:1645 */     Sequence s = new Sequence("roles");
/* 1498:1646 */     Function f = new Function("object", object);
/* 1499:1647 */     s.addElement(f);
/* 1500:1648 */     element.setObject(s);
/* 1501:1649 */     return element;
/* 1502:     */   }
/* 1503:     */   
/* 1504:     */   class WhatHappensWhenExpert
/* 1505:     */     extends BasicRule3
/* 1506:     */   {
/* 1507:     */     WhatHappensWhenExpert() {}
/* 1508:     */     
/* 1509:     */     public void run()
/* 1510:     */     {
/* 1511:1657 */       super.run();
/* 1512:1658 */       if ((this.firstLink.isAPrimed("has_attitude")) && (this.firstLinkSubject.isAPrimed("when")) && (this.firstLinkObject.isAPrimed("interrogative")) && 
/* 1513:1659 */         (this.secondLink.isAPrimed("when")) && 
/* 1514:1660 */         ((this.secondLinkSubject.isAPrimed("occur")) || (this.secondLinkSubject.isAPrimed("happen"))) && 
/* 1515:1661 */         (this.thirdLink == this.secondLinkSubject))
/* 1516:     */       {
/* 1517:1662 */         Function question = new Function("question", this.secondLinkObject);
/* 1518:1663 */         question.addType("what happens when");
/* 1519:1664 */         replace(this.firstLink, question);
/* 1520:1665 */         remove(this.secondLink);
/* 1521:1666 */         remove(this.thirdLink);
/* 1522:1667 */         succeeded();
/* 1523:     */       }
/* 1524:     */     }
/* 1525:     */   }
/* 1526:     */   
/* 1527:     */   class DescribeExpert
/* 1528:     */     extends BasicRule3
/* 1529:     */   {
/* 1530:     */     DescribeExpert() {}
/* 1531:     */     
/* 1532:     */     public void run()
/* 1533:     */     {
/* 1534:1680 */       super.run();
/* 1535:1681 */       if ((this.firstLink.isAPrimed("has_attitude")) && (this.firstLinkSubject == this.secondLink) && (this.firstLinkObject.isAPrimed("interrogative")) && 
/* 1536:1682 */         (this.secondLink.isAPrimed("tell")) && (this.secondLinkSubject.isAPrimed("you")) && (this.secondLinkObject.isAPrimed("i")) && 
/* 1537:1683 */         (this.thirdLink.isAPrimed("about")) && (this.thirdLinkSubject == this.secondLink))
/* 1538:     */       {
/* 1539:1684 */         Function command = new Function("command", this.thirdLinkObject);
/* 1540:1685 */         command.addType("description");
/* 1541:1686 */         replace(this.secondLink, command);
/* 1542:1687 */         remove(this.thirdLink);
/* 1543:1688 */         remove(this.firstLink);
/* 1544:1689 */         succeeded();
/* 1545:     */       }
/* 1546:     */     }
/* 1547:     */   }
/* 1548:     */   
/* 1549:     */   class RoleInverterExpert
/* 1550:     */     extends BasicRule2
/* 1551:     */   {
/* 1552:     */     RoleInverterExpert() {}
/* 1553:     */     
/* 1554:     */     public void run()
/* 1555:     */     {
/* 1556:1698 */       super.run();
/* 1557:1699 */       if ((this.firstLink.isA("action")) && (this.firstLink.relationP()) && 
/* 1558:1700 */         (this.secondLink.isAPrimed(NewRuleSet.roleWords)) && (this.secondLink.getSubject() == this.firstLink) && 
/* 1559:1701 */         (this.firstLink.getObject().sequenceP()))
/* 1560:     */       {
/* 1561:1702 */         Sequence roles = (Sequence)this.firstLink.getObject();
/* 1562:1703 */         Function role = new Function(this.secondLink.getType(), this.secondLink.getObject());
/* 1563:1704 */         roles.addElement(role);
/* 1564:1705 */         remove(this.secondLink);
/* 1565:1706 */         succeeded();
/* 1566:     */       }
/* 1567:     */     }
/* 1568:     */   }
/* 1569:     */   
/* 1570:     */   private void purgeThingThreads(Entity t)
/* 1571:     */   {
/* 1572:1718 */     Bundle oldB = t.getBundle();
/* 1573:1719 */     Bundle newB = new Bundle();
/* 1574:1720 */     for (Thread thread : oldB) {
/* 1575:1721 */       if (!((String)thread.get(0)).equals("thing")) {
/* 1576:1722 */         newB.add(thread);
/* 1577:     */       }
/* 1578:     */     }
/* 1579:1726 */     t.setBundle(newB);
/* 1580:     */   }
/* 1581:     */   
/* 1582:1762 */   public static boolean USE_ROLES = true;
/* 1583:     */   
/* 1584:     */   class RoleExpert
/* 1585:     */     extends BasicRule2
/* 1586:     */   {
/* 1587:1765 */     private ArrayList<String> testWords = new ArrayList();
/* 1588:     */     
/* 1589:     */     public RoleExpert()
/* 1590:     */     {
/* 1591:1768 */       this.testWords.addAll(NewRuleSet.roleWords);
/* 1592:1769 */       this.testWords.addAll(NewRuleSet.pathPrepositions);
/* 1593:     */     }
/* 1594:     */     
/* 1595:     */     public void run()
/* 1596:     */     {
/* 1597:1773 */       super.run();
/* 1598:1774 */       if ((Innerese.isRoleFrame(this.firstLink)) && 
/* 1599:1775 */         (this.secondLink.isA(this.testWords)) && 
/* 1600:1776 */         (this.firstLink == this.secondLinkSubject))
/* 1601:     */       {
/* 1602:1777 */         Innerese.addRole(this.firstLink, this.secondLink.getType(), this.secondLinkObject);
/* 1603:1778 */         remove(this.secondLink);
/* 1604:1779 */         succeeded();
/* 1605:     */       }
/* 1606:     */     }
/* 1607:     */   }
/* 1608:     */   
/* 1609:     */   class ActionExpert
/* 1610:     */     extends BasicRule
/* 1611:     */   {
/* 1612:1790 */     String[] inclusions = { "enable", "entail", "time-relation", "become" };
/* 1613:1792 */     String[] exclusions = { "travel", "move" };
/* 1614:     */     
/* 1615:     */     ActionExpert() {}
/* 1616:     */     
/* 1617:     */     public void run()
/* 1618:     */     {
/* 1619:1795 */       super.run();
/* 1620:1796 */       if (((!this.firstLink.isAnyOf(this.inclusions)) || (!this.firstLink.isNoneOf(this.exclusions))) && (!Predicates.isCauseWord(this.firstLink)) && 
/* 1621:1797 */         (!Innerese.isRoleFrame(this.firstLink))) {
/* 1622:1799 */         if ((this.firstLink.isA("action")) && (this.firstLink.relationP()) && (!this.firstLink.hasProperty("processed")))
/* 1623:     */         {
/* 1624:1801 */           Thread thread = this.firstLink.getThreadWith("action", this.firstLink.getType());
/* 1625:1802 */           if (thread != null) {
/* 1626:1803 */             this.firstLink.setPrimedThread(thread);
/* 1627:     */           }
/* 1628:     */           Relation roleFrame;
/* 1629:     */           Relation roleFrame;
/* 1630:1807 */           if (!this.firstLinkObject.isA("null")) {
/* 1631:1808 */             roleFrame = Innerese.makeRoleFrame(thread, this.firstLinkSubject, this.firstLinkObject);
/* 1632:     */           } else {
/* 1633:1811 */             roleFrame = Innerese.makeRoleFrame(thread, this.firstLinkSubject);
/* 1634:     */           }
/* 1635:1813 */           replace(this.firstLink, roleFrame);
/* 1636:1814 */           succeeded();
/* 1637:     */         }
/* 1638:     */       }
/* 1639:     */     }
/* 1640:     */   }
/* 1641:     */   
/* 1642:     */   public static Entity getMentalModelStarter()
/* 1643:     */   {
/* 1644:1820 */     Sequence roles = new Sequence("roles");
/* 1645:1821 */     roles.addElement(new Function("object", new Entity("story")));
/* 1646:1822 */     Relation relation = new Relation("start", new Entity("you"), roles);
/* 1647:1823 */     return relation;
/* 1648:     */   }
/* 1649:     */   
/* 1650:     */   class TouchExpert
/* 1651:     */     extends BasicRule
/* 1652:     */   {
/* 1653:     */     TouchExpert() {}
/* 1654:     */     
/* 1655:     */     public void run()
/* 1656:     */     {
/* 1657:1862 */       super.run();
/* 1658:1863 */       if ((this.firstLink.isAPrimed("touch")) && (this.firstLinkSubject.isAPrimed("entity")) && (this.firstLinkObject.isAPrimed("entity")))
/* 1659:     */       {
/* 1660:1864 */         Relation contact = new Relation("contact", this.firstLinkSubject, this.firstLinkObject);
/* 1661:1865 */         Function transition = Constructors.constructAppearTransition(contact);
/* 1662:1866 */         replace(this.firstLink, transition);
/* 1663:1867 */         succeeded();
/* 1664:     */       }
/* 1665:     */     }
/* 1666:     */   }
/* 1667:     */   
/* 1668:     */   class ClassificationExpert
/* 1669:     */     extends BasicRule2
/* 1670:     */   {
/* 1671:     */     ClassificationExpert() {}
/* 1672:     */     
/* 1673:     */     public void run()
/* 1674:     */     {
/* 1675:1975 */       super.run();
/* 1676:1976 */       if ((this.firstLink.relationP("classification")) && (this.secondLink.relationP("has_det")) && 
/* 1677:1977 */         (this.firstLinkSubject == this.secondLinkSubject)) {
/* 1678:1978 */         remove(this.secondLink);
/* 1679:     */       }
/* 1680:     */     }
/* 1681:     */   }
/* 1682:     */   
/* 1683:     */   class ThreadExpert
/* 1684:     */     extends BasicRule
/* 1685:     */   {
/* 1686:     */     ThreadExpert() {}
/* 1687:     */     
/* 1688:     */     public void run()
/* 1689:     */     {
/* 1690:1986 */       super.run();
/* 1691:1988 */       if ((this.firstLink.isA("is-a")) && (this.firstLinkObject != Markers.NULL) && (this.firstLinkObject.isNotA("property")) && 
/* 1692:1989 */         (this.firstLinkObject.isNotA("position")) && (!this.firstLinkObject.isA(NewRuleSet.jobWords)))
/* 1693:     */       {
/* 1694:1992 */         Relation classificationRelation = new Relation("classification", this.firstLinkObject, this.firstLinkSubject);
/* 1695:1993 */         String type = this.firstLinkSubject.getType();
/* 1696:1994 */         Thread thread = (Thread)this.firstLinkObject.getPrimedThread().clone();
/* 1697:1995 */         thread.add("name");
/* 1698:1996 */         thread.add(type);
/* 1699:1997 */         Bundle bundle = this.firstLinkSubject.getBundle();
/* 1700:1998 */         if (!this.firstLinkSubject.hasProperty("characterized", Boolean.valueOf(true))) {
/* 1701:2000 */           bundle.clear();
/* 1702:     */         }
/* 1703:2003 */         bundle.add(0, thread);
/* 1704:2004 */         this.firstLinkSubject.setBundle(bundle);
/* 1705:2005 */         this.firstLinkSubject.addProperty("characterized", Boolean.valueOf(true));
/* 1706:2006 */         BundleGenerator.setBundle(type, bundle);
/* 1707:2007 */         replace(this.firstLink, classificationRelation);
/* 1708:2008 */         succeeded();
/* 1709:     */       }
/* 1710:2010 */       else if (this.firstLink.isA("is-a"))
/* 1711:     */       {
/* 1712:2011 */         Mark.say(new Object[] {"Entering unexpored territory in ThreadExpert", this.firstLink.asStringWithIndexes() });
/* 1713:     */         
/* 1714:2013 */         Relation classification = new Relation("classification", this.firstLinkObject, this.firstLinkSubject);
/* 1715:2015 */         if (this.firstLinkObject.isA("name")) {
/* 1716:2017 */           removeName(this.firstLinkObject);
/* 1717:     */         }
/* 1718:2020 */         if (!this.firstLink.hasProperty("idiom", "sometimes"))
/* 1719:     */         {
/* 1720:2021 */           Thread thread = (Thread)this.firstLinkObject.getPrimedThread().clone();
/* 1721:2022 */           String type = this.firstLinkSubject.getType();
/* 1722:2023 */           thread.add(type);
/* 1723:     */           
/* 1724:2025 */           Bundle bundle = new Bundle(thread);
/* 1725:2027 */           if (this.firstLinkSubject.isA("proper"))
/* 1726:     */           {
/* 1727:2028 */             this.firstLinkSubject.setBundle(bundle);
/* 1728:2029 */             addProperName(this.firstLinkSubject);
/* 1729:     */           }
/* 1730:2031 */           else if (this.firstLinkSubject.isA("name"))
/* 1731:     */           {
/* 1732:2033 */             this.firstLinkSubject.setBundle(bundle);
/* 1733:     */           }
/* 1734:     */           else
/* 1735:     */           {
/* 1736:2038 */             this.firstLinkSubject.setBundle(bundle);
/* 1737:2039 */             addName(this.firstLinkSubject);
/* 1738:     */           }
/* 1739:2041 */           BundleGenerator.setBundle(type, bundle);
/* 1740:     */         }
/* 1741:2043 */         replace(this.firstLink, classification);
/* 1742:2044 */         succeeded();
/* 1743:     */       }
/* 1744:2046 */       else if ((NewRuleSet.this.isA(this.firstLink)) && (this.firstLinkObject != Markers.NULL) && (this.firstLinkObject.isA("personality_trait")))
/* 1745:     */       {
/* 1746:2047 */         Mark.say(new Object[] {"Personality classification" });
/* 1747:2048 */         Relation r = new Relation("personality_trait", this.firstLinkSubject, this.firstLinkObject);
/* 1748:2049 */         replace(this.firstLink, r);
/* 1749:2050 */         remove(this.firstLink);
/* 1750:2051 */         succeeded();
/* 1751:     */       }
/* 1752:2053 */       else if ((NewRuleSet.this.isA(this.firstLink)) && (this.firstLinkObject != Markers.NULL) && (this.firstLinkObject.isA("property")))
/* 1753:     */       {
/* 1754:2054 */         Mark.say(new Object[] {"Property type" });
/* 1755:2055 */         Relation r = new Relation("property", this.firstLinkSubject, this.firstLinkObject);
/* 1756:2056 */         replace(this.firstLink, r);
/* 1757:2057 */         remove(this.firstLink);
/* 1758:2058 */         succeeded();
/* 1759:     */       }
/* 1760:2061 */       else if ((this.firstLink.isAPrimed("kind")) && (!this.firstLink.isAPrimed("like")) && (!this.firstLink.isAPrimed("make")))
/* 1761:     */       {
/* 1762:2062 */         Relation classification = new Relation("classification", this.firstLinkSubject, this.firstLinkObject);
/* 1763:2063 */         Thread thread = (Thread)this.firstLinkSubject.getPrimedThread().clone();
/* 1764:2064 */         String type = this.firstLinkObject.getType();
/* 1765:2065 */         thread.add(type);
/* 1766:2066 */         Bundle bundle = new Bundle(thread);
/* 1767:2067 */         this.firstLinkObject.setBundle(bundle);
/* 1768:2068 */         BundleGenerator.setBundle(type, bundle);
/* 1769:2069 */         replace(this.firstLink, classification);
/* 1770:2070 */         succeeded();
/* 1771:     */       }
/* 1772:     */     }
/* 1773:     */   }
/* 1774:     */   
/* 1775:     */   class NameExpert
/* 1776:     */     extends BasicRule
/* 1777:     */   {
/* 1778:     */     NameExpert() {}
/* 1779:     */     
/* 1780:     */     public void run()
/* 1781:     */     {
/* 1782:2080 */       super.run();
/* 1783:2081 */       if ((this.firstLink.isAPrimed("is_proper")) && (this.firstLinkObject.isA("yes")))
/* 1784:     */       {
/* 1785:2082 */         addProperName(this.firstLinkSubject);
/* 1786:2083 */         remove(this.firstLink);
/* 1787:2084 */         succeeded();
/* 1788:     */       }
/* 1789:2086 */       else if (this.firstLink.isAPrimed("has_det"))
/* 1790:     */       {
/* 1791:2087 */         if (this.firstLinkObject.isA("indefinite"))
/* 1792:     */         {
/* 1793:2088 */           addName(this.firstLinkSubject);
/* 1794:2089 */           this.firstLinkSubject.addDeterminer("indefinite");
/* 1795:     */         }
/* 1796:2091 */         else if (this.firstLinkObject.isA("definite"))
/* 1797:     */         {
/* 1798:2092 */           if (this.firstLinkSubject.isNotA("position")) {
/* 1799:2093 */             addName(this.firstLinkSubject);
/* 1800:     */           }
/* 1801:2095 */           this.firstLinkSubject.addDeterminer("definite");
/* 1802:     */         }
/* 1803:2097 */         remove(this.firstLink);
/* 1804:2098 */         succeeded();
/* 1805:     */       }
/* 1806:2100 */       else if (this.firstLink.isAPrimed("has_property"))
/* 1807:     */       {
/* 1808:2101 */         if (this.firstLinkObject.isA("another"))
/* 1809:     */         {
/* 1810:2102 */           addName(this.firstLinkSubject);
/* 1811:2103 */           this.firstLinkSubject.addDeterminer("another");
/* 1812:2104 */           remove(this.firstLink);
/* 1813:2105 */           succeeded();
/* 1814:     */         }
/* 1815:2107 */         else if (this.firstLinkObject.isA("first"))
/* 1816:     */         {
/* 1817:2108 */           addName(this.firstLinkSubject);
/* 1818:2109 */           this.firstLinkSubject.addFeature("first");
/* 1819:2110 */           remove(this.firstLink);
/* 1820:2111 */           succeeded();
/* 1821:     */         }
/* 1822:2113 */         else if (this.firstLinkObject.isA("second"))
/* 1823:     */         {
/* 1824:2114 */           addName(this.firstLinkSubject);
/* 1825:2115 */           this.firstLinkSubject.addFeature("second");
/* 1826:2116 */           remove(this.firstLink);
/* 1827:2117 */           succeeded();
/* 1828:     */         }
/* 1829:2119 */         else if (this.firstLinkObject.isA("third"))
/* 1830:     */         {
/* 1831:2120 */           addName(this.firstLinkSubject);
/* 1832:2121 */           this.firstLinkSubject.addFeature("third");
/* 1833:2122 */           remove(this.firstLink);
/* 1834:2123 */           succeeded();
/* 1835:     */         }
/* 1836:2125 */         else if (this.firstLinkObject.isA("fourth"))
/* 1837:     */         {
/* 1838:2126 */           addName(this.firstLinkSubject);
/* 1839:2127 */           this.firstLinkSubject.addFeature("fourth");
/* 1840:2128 */           remove(this.firstLink);
/* 1841:2129 */           succeeded();
/* 1842:     */         }
/* 1843:2131 */         else if (this.firstLinkObject.isA("fifth"))
/* 1844:     */         {
/* 1845:2132 */           addName(this.firstLinkSubject);
/* 1846:2133 */           this.firstLinkSubject.addFeature("fifth");
/* 1847:2134 */           remove(this.firstLink);
/* 1848:2135 */           succeeded();
/* 1849:     */         }
/* 1850:     */       }
/* 1851:     */     }
/* 1852:     */   }
/* 1853:     */   
/* 1854:     */   class ConceptExpert
/* 1855:     */     extends BasicRule2
/* 1856:     */   {
/* 1857:     */     ConceptExpert() {}
/* 1858:     */     
/* 1859:     */     public void run()
/* 1860:     */     {
/* 1861:2144 */       super.run();
/* 1862:2145 */       if ((this.firstLink.isAPrimed("start")) && (this.firstLinkSubject.isAPrimed("you")) && (this.firstLinkObject.isAPrimed("description")) && 
/* 1863:2146 */         (this.secondLinkSubject == this.firstLinkObject))
/* 1864:     */       {
/* 1865:2147 */         Function d = new Function("reflection", this.secondLinkObject);
/* 1866:2148 */         this.firstLink.setObject(d);
/* 1867:2149 */         remove(this.secondLink);
/* 1868:2150 */         succeeded();
/* 1869:     */       }
/* 1870:     */     }
/* 1871:     */   }
/* 1872:     */   
/* 1873:     */   class StoryExpert
/* 1874:     */     extends BasicRule2
/* 1875:     */   {
/* 1876:     */     StoryExpert() {}
/* 1877:     */     
/* 1878:     */     public void run()
/* 1879:     */     {
/* 1880:2158 */       super.run();
/* 1881:2159 */       if ((this.firstLink.isAPrimed("start")) && (this.firstLinkSubject.isAPrimed("you")) && (this.secondLinkSubject.isAPrimed("story")) && 
/* 1882:2160 */         (this.secondLinkSubject == this.firstLinkObject))
/* 1883:     */       {
/* 1884:2161 */         Function d = new Function("story", this.secondLinkObject);
/* 1885:2162 */         this.firstLink.setObject(d);
/* 1886:     */         
/* 1887:2164 */         remove(this.secondLink);
/* 1888:2165 */         succeeded();
/* 1889:     */       }
/* 1890:     */     }
/* 1891:     */   }
/* 1892:     */   
/* 1893:     */   class AngryExpert
/* 1894:     */     extends BasicRule2
/* 1895:     */   {
/* 1896:     */     AngryExpert() {}
/* 1897:     */     
/* 1898:     */     public void run()
/* 1899:     */     {
/* 1900:2202 */       super.run();
/* 1901:2203 */       if ((this.firstLink.isAPrimed("property")) && (this.firstLinkObject.isAPrimed("angry")) && 
/* 1902:2204 */         (this.secondLink.isAPrimed("at")) && (this.secondLinkSubject == this.firstLink))
/* 1903:     */       {
/* 1904:2205 */         Relation relation = Start.makeRelation("anger", this.secondLinkObject, this.firstLinkSubject);
/* 1905:2206 */         replace(this.firstLink, relation);
/* 1906:2207 */         remove(this.secondLink);
/* 1907:2208 */         succeeded();
/* 1908:     */       }
/* 1909:     */     }
/* 1910:     */   }
/* 1911:     */   
/* 1912:     */   public static boolean containsRelation(String type, String subject, String object, Entity sequence)
/* 1913:     */   {
/* 1914:2231 */     if (!sequence.sequenceP()) {
/* 1915:2232 */       return false;
/* 1916:     */     }
/* 1917:2234 */     for (Entity t : sequence.getElements()) {
/* 1918:2235 */       if ((t.relationP(type)) && 
/* 1919:2236 */         (t.getSubject().isA(subject)) && (t.getObject().isA(object))) {
/* 1920:2237 */         return true;
/* 1921:     */       }
/* 1922:     */     }
/* 1923:2241 */     return false;
/* 1924:     */   }
/* 1925:     */   
/* 1926:     */   public static boolean isRelation(String type, String subject, String object, Entity t)
/* 1927:     */   {
/* 1928:2245 */     if ((t.relationP(type)) && 
/* 1929:2246 */       (t.getSubject().isA(subject)) && (t.getObject().isA(object))) {
/* 1930:2247 */       return true;
/* 1931:     */     }
/* 1932:2250 */     return false;
/* 1933:     */   }
/* 1934:     */   
/* 1935:     */   class PropertyExpert
/* 1936:     */     extends BasicRule2
/* 1937:     */   {
/* 1938:     */     PropertyExpert() {}
/* 1939:     */     
/* 1940:     */     public void run()
/* 1941:     */     {
/* 1942:2270 */       super.run();
/* 1943:2271 */       if ((this.secondLink.isAPrimed("has_property")) && 
/* 1944:2272 */         ((this.firstLink.isAPrimed("is")) || (this.firstLink.isAPrimed("become"))) && 
/* 1945:2273 */         (this.secondLinkSubject.getType().equals(this.firstLinkSubject.getType())) && 
/* 1946:2274 */         (this.secondLinkObject.getType().equals(this.firstLinkObject.getType())))
/* 1947:     */       {
/* 1948:2275 */         String relationType = "property";
/* 1949:2276 */         Relation relation = new Relation(relationType, this.firstLinkSubject, this.firstLinkObject);
/* 1950:2277 */         if ((this.firstLinkObject.isAPrimed("personality_trait")) || (this.firstLinkObject.isAPrimed("personality_type"))) {
/* 1951:2279 */           relation.addType("personality_trait");
/* 1952:     */         }
/* 1953:2282 */         if ((this.firstLinkObject.isAPrimed(NewRuleSet.positiveWords)) || (this.firstLinkObject.isAPrimed(NewRuleSet.negativeWords)))
/* 1954:     */         {
/* 1955:2284 */           Entity quality = this.firstLinkObject;
/* 1956:2285 */           addTypeBeforeLast("mental-state", quality);
/* 1957:2287 */           if (quality.isAPrimed(NewRuleSet.positiveWords)) {
/* 1958:2288 */             quality.addType("happy");
/* 1959:2290 */           } else if (quality.isAPrimed(NewRuleSet.negativeWords)) {
/* 1960:2291 */             quality.addType("unhappy");
/* 1961:     */           }
/* 1962:2293 */           relation = new Relation("has-mental-state", this.firstLinkSubject, quality);
/* 1963:     */         }
/* 1964:2296 */         if (this.firstLink.isAPrimed("become"))
/* 1965:     */         {
/* 1966:2297 */           Function transition = NewRuleSet.this.getNewDerivative("action", relation);
/* 1967:2298 */           transition.addType("transition");
/* 1968:2299 */           transition.addType("appear");
/* 1969:2300 */           replace(this.firstLink, transition);
/* 1970:     */         }
/* 1971:     */         else
/* 1972:     */         {
/* 1973:2303 */           replace(this.firstLink, relation);
/* 1974:     */         }
/* 1975:2305 */         remove(this.secondLink);
/* 1976:2306 */         succeeded();
/* 1977:     */       }
/* 1978:     */     }
/* 1979:     */   }
/* 1980:     */   
/* 1981:     */   class InvertProperty
/* 1982:     */     extends BasicRule
/* 1983:     */   {
/* 1984:     */     InvertProperty() {}
/* 1985:     */     
/* 1986:     */     public void run()
/* 1987:     */     {
/* 1988:2316 */       super.run();
/* 1989:2317 */       if ((this.firstLinkSubject.isA("property")) && (this.firstLink.isA(NewRuleSet.roleWords)) && 
/* 1990:2318 */         (this.firstLinkSubject.getObject().entityP()))
/* 1991:     */       {
/* 1992:2319 */         Entity roles = Constructors.makeRoles(this.firstLinkSubject.getObject(), "property");
/* 1993:     */         
/* 1994:2321 */         roles.addElement(new Function(this.firstLink.getType(), this.firstLinkObject));
/* 1995:     */         
/* 1996:2323 */         Mark.say(new Object[] {"Role frame is", this.firstLink });
/* 1997:     */         
/* 1998:2325 */         this.firstLinkSubject.setObject(roles);
/* 1999:     */         
/* 2000:2327 */         remove(this.firstLink);
/* 2001:     */         
/* 2002:2329 */         this.links.containsDeprecated(this.firstLinkSubject);
/* 2003:     */         
/* 2004:     */ 
/* 2005:     */ 
/* 2006:2333 */         succeeded();
/* 2007:     */       }
/* 2008:     */     }
/* 2009:     */   }
/* 2010:     */   
/* 2011:     */   class PropertySubstitutor
/* 2012:     */     extends BasicRule
/* 2013:     */   {
/* 2014:2343 */     private List<String> propertySubstitutions = Arrays.asList(new String[] { "chinese", "" });
/* 2015:     */     
/* 2016:     */     PropertySubstitutor() {}
/* 2017:     */     
/* 2018:     */     public void run()
/* 2019:     */     {
/* 2020:2346 */       super.run();
/* 2021:2347 */       if ((!this.firstLink.hasProperty("processed")) && ((this.firstLink.isAPrimed("is")) || (this.firstLink.isAPrimed("become"))) && 
/* 2022:2348 */         (this.firstLinkObject.isAPrimed(this.propertySubstitutions)))
/* 2023:     */       {
/* 2024:2349 */         this.firstLink.addProperty("processed", Boolean.valueOf(true));
/* 2025:2350 */         Relation property = new Relation("has_property", this.firstLinkSubject, this.firstLinkObject);
/* 2026:2351 */         addLinkAtEnd(property);
/* 2027:2352 */         succeeded();
/* 2028:     */       }
/* 2029:     */     }
/* 2030:     */   }
/* 2031:     */   
/* 2032:     */   class PropertyAbsorber
/* 2033:     */     extends BasicRule
/* 2034:     */   {
/* 2035:     */     PropertyAbsorber() {}
/* 2036:     */     
/* 2037:     */     public void run()
/* 2038:     */     {
/* 2039:2362 */       super.run();
/* 2040:2363 */       if ((this.firstLink.getType().equals("property")) && (!this.firstLinkSubject.entityP()))
/* 2041:     */       {
/* 2042:2364 */         if (this.firstLinkObject.isA("then")) {
/* 2043:2365 */           this.firstLinkSubject.addProperty("scene", Boolean.valueOf(true));
/* 2044:     */         } else {
/* 2045:2368 */           this.firstLinkSubject.addProperty("property", this.firstLinkObject.getType());
/* 2046:     */         }
/* 2047:2370 */         replace(this.firstLink, this.firstLinkSubject);
/* 2048:2371 */         succeeded();
/* 2049:     */       }
/* 2050:     */     }
/* 2051:     */   }
/* 2052:     */   
/* 2053:     */   class DeterminerExpert
/* 2054:     */     extends BasicRule
/* 2055:     */   {
/* 2056:     */     DeterminerExpert() {}
/* 2057:     */     
/* 2058:     */     public void run()
/* 2059:     */     {
/* 2060:2378 */       super.run();
/* 2061:2379 */       if ((this.firstLinkSubject.isAPrimed("entity")) && (this.firstLink.isA("has_det")))
/* 2062:     */       {
/* 2063:2380 */         if (this.firstLinkObject == Markers.NULL) {
/* 2064:2381 */           this.firstLinkSubject.addProperty("determiner", "none");
/* 2065:     */         } else {
/* 2066:2385 */           this.firstLinkSubject.addProperty("determiner", this.firstLinkObject.getType());
/* 2067:     */         }
/* 2068:2389 */         remove(this.firstLink);
/* 2069:2390 */         succeeded();
/* 2070:     */       }
/* 2071:     */     }
/* 2072:     */   }
/* 2073:     */   
/* 2074:     */   class AdjectiveExpert
/* 2075:     */     extends BasicRule
/* 2076:     */   {
/* 2077:     */     AdjectiveExpert() {}
/* 2078:     */     
/* 2079:     */     public void run()
/* 2080:     */     {
/* 2081:2414 */       super.run();
/* 2082:2415 */       if (this.firstLink.isAPrimed("has_property"))
/* 2083:     */       {
/* 2084:2416 */         Relation property = new Relation("property", this.firstLinkSubject, this.firstLinkObject);
/* 2085:2417 */         this.firstLinkSubject.addFeature(this.firstLinkObject.getType());
/* 2086:     */         
/* 2087:     */ 
/* 2088:2420 */         remove(this.firstLink);
/* 2089:2421 */         succeeded();
/* 2090:     */       }
/* 2091:     */     }
/* 2092:     */   }
/* 2093:     */   
/* 2094:     */   class QuantifierExpert
/* 2095:     */     extends BasicRule
/* 2096:     */   {
/* 2097:     */     public QuantifierExpert()
/* 2098:     */     {
/* 2099:2428 */       this.sample = "many trees";
/* 2100:     */     }
/* 2101:     */     
/* 2102:     */     public void run()
/* 2103:     */     {
/* 2104:2432 */       super.run();
/* 2105:2433 */       if (this.firstLink.isAPrimed("has_quantifier"))
/* 2106:     */       {
/* 2107:2435 */         remove(this.firstLink);
/* 2108:2436 */         succeeded();
/* 2109:     */       }
/* 2110:     */     }
/* 2111:     */   }
/* 2112:     */   
/* 2113:     */   class JobExpert
/* 2114:     */     extends BasicRule
/* 2115:     */   {
/* 2116:     */     JobExpert() {}
/* 2117:     */     
/* 2118:     */     public void run()
/* 2119:     */     {
/* 2120:2446 */       super.run();
/* 2121:2447 */       if (((NewRuleSet.this.isA(this.firstLink)) || (this.firstLink.isAPrimed("become"))) && 
/* 2122:2448 */         (this.firstLinkObject.isA(NewRuleSet.jobWords)))
/* 2123:     */       {
/* 2124:2449 */         Relation classification = new Relation("position", this.firstLinkSubject, this.firstLinkObject);
/* 2125:2452 */         if (this.firstLink.isAPrimed("become"))
/* 2126:     */         {
/* 2127:2453 */           Function transition = NewRuleSet.this.getNewDerivative("action", classification);
/* 2128:2454 */           transition.addType("transition");
/* 2129:2455 */           transition.addType("appear");
/* 2130:2456 */           replace(this.firstLink, transition);
/* 2131:     */         }
/* 2132:     */         else
/* 2133:     */         {
/* 2134:2459 */           replace(this.firstLink, classification);
/* 2135:     */         }
/* 2136:2462 */         replace(this.firstLink, classification);
/* 2137:2463 */         succeeded();
/* 2138:     */       }
/* 2139:     */     }
/* 2140:     */   }
/* 2141:     */   
/* 2142:     */   class JobExpert2
/* 2143:     */     extends BasicRule
/* 2144:     */   {
/* 2145:     */     JobExpert2() {}
/* 2146:     */     
/* 2147:     */     public void run()
/* 2148:     */     {
/* 2149:2471 */       super.run();
/* 2150:2472 */       if ((this.firstLink.isAPrimed("become")) && 
/* 2151:2473 */         (this.firstLinkObject.isAPrimed(NewRuleSet.jobWords)))
/* 2152:     */       {
/* 2153:2474 */         Relation job = new Relation("position", this.firstLinkObject, this.firstLinkSubject);
/* 2154:     */         
/* 2155:2476 */         Function transition = NewRuleSet.this.getNewDerivative("action", job);
/* 2156:2477 */         transition.addType("transition");
/* 2157:2478 */         transition.addType("appear");
/* 2158:2479 */         replace(this.firstLink, transition);
/* 2159:2480 */         succeeded();
/* 2160:     */       }
/* 2161:     */     }
/* 2162:     */   }
/* 2163:     */   
/* 2164:     */   class CommandExpert
/* 2165:     */     extends BasicRule2
/* 2166:     */   {
/* 2167:     */     CommandExpert() {}
/* 2168:     */     
/* 2169:     */     public void run()
/* 2170:     */     {
/* 2171:2491 */       super.run();
/* 2172:2492 */       if ((this.firstLinkSubject.isAPrimed("you")) && (this.firstLinkObject.isA("action")))
/* 2173:     */       {
/* 2174:2493 */         if (this.secondLink == this.firstLinkObject)
/* 2175:     */         {
/* 2176:2494 */           Function command = new Function("command", this.firstLinkObject);
/* 2177:2495 */           command.addType(this.firstLink.getType());
/* 2178:2496 */           replace(this.firstLink, command);
/* 2179:2497 */           remove(this.secondLink);
/* 2180:2498 */           succeeded();
/* 2181:     */         }
/* 2182:     */       }
/* 2183:2501 */       else if ((this.firstLinkSubject.isAPrimed("you")) && (this.firstLinkObject.isA("event")) && 
/* 2184:2502 */         (this.firstLinkObject == this.secondLinkSubject))
/* 2185:     */       {
/* 2186:2503 */         Function command = new Function("command", this.secondLinkObject);
/* 2187:2504 */         command.addType(this.firstLink.getType());
/* 2188:2505 */         replace(this.firstLink, command);
/* 2189:2506 */         remove(this.secondLink);
/* 2190:2507 */         succeeded();
/* 2191:     */       }
/* 2192:     */     }
/* 2193:     */   }
/* 2194:     */   
/* 2195:     */   class ingExpert
/* 2196:     */     extends BasicRule
/* 2197:     */   {
/* 2198:     */     ingExpert() {}
/* 2199:     */     
/* 2200:     */     public void run()
/* 2201:     */     {
/* 2202:2515 */       super.run();
/* 2203:2516 */       if (this.firstLink.isAPrimed("gerund_of"))
/* 2204:     */       {
/* 2205:2517 */         Mark.say(new Object[] {"Replacing", this.firstLinkSubject.getName(), "with", this.firstLinkObject.getName() });
/* 2206:2518 */         replace(this.firstLinkSubject, this.firstLinkObject);
/* 2207:2519 */         remove(this.firstLink);
/* 2208:2520 */         succeeded();
/* 2209:     */       }
/* 2210:     */     }
/* 2211:     */   }
/* 2212:     */   
/* 2213:     */   class ComparisonExpert
/* 2214:     */     extends BasicRule2
/* 2215:     */   {
/* 2216:     */     ComparisonExpert() {}
/* 2217:     */     
/* 2218:     */     public void run()
/* 2219:     */     {
/* 2220:2532 */       super.run();
/* 2221:2533 */       if ((this.firstLink.isAPrimed("property")) && (this.secondLink.isAPrimed("than")) && (this.firstLink == this.secondLinkSubject))
/* 2222:     */       {
/* 2223:2534 */         Relation relation = new Relation("comparison", this.firstLinkSubject, this.secondLinkObject);
/* 2224:2535 */         relation.addType(this.firstLinkObject.getType());
/* 2225:2536 */         replace(this.firstLink, relation);
/* 2226:2537 */         remove(this.secondLink);
/* 2227:2538 */         succeeded();
/* 2228:     */       }
/* 2229:     */     }
/* 2230:     */   }
/* 2231:     */   
/* 2232:     */   class SocialExpert
/* 2233:     */     extends BasicRule2
/* 2234:     */   {
/* 2235:     */     SocialExpert() {}
/* 2236:     */     
/* 2237:     */     public void run()
/* 2238:     */     {
/* 2239:2549 */       super.run();
/* 2240:2550 */       if (NewRuleSet.this.isA(this.firstLink))
/* 2241:     */       {
/* 2242:2551 */         if ((this.secondLink.isAPrimed("related-to")) && 
/* 2243:2552 */           (this.firstLinkObject == this.secondLinkSubject))
/* 2244:     */         {
/* 2245:2553 */           String name = this.firstLinkObject.getType();
/* 2246:2554 */           Relation relation = new Relation(name, this.secondLinkObject, this.firstLinkSubject);
/* 2247:2555 */           addTypeAfterReference("thing", "social relation", relation);
/* 2248:2556 */           replace(this.firstLink, relation);
/* 2249:2557 */           remove(this.secondLink);
/* 2250:2558 */           succeeded();
/* 2251:     */         }
/* 2252:     */       }
/* 2253:2562 */       else if ((NewRuleSet.this.isA(this.secondLink)) && 
/* 2254:2563 */         (this.firstLink.isAPrimed("related-to")) && 
/* 2255:2564 */         (this.secondLinkObject == this.firstLinkSubject))
/* 2256:     */       {
/* 2257:2565 */         String name = this.secondLinkObject.getType();
/* 2258:2566 */         Relation relation = new Relation(name, this.firstLinkObject, this.secondLinkSubject);
/* 2259:2567 */         addTypeAfterReference("thing", "social relation", relation);
/* 2260:2568 */         replace(this.secondLink, relation);
/* 2261:2569 */         remove(this.firstLink);
/* 2262:2570 */         succeeded();
/* 2263:     */       }
/* 2264:     */     }
/* 2265:     */   }
/* 2266:     */   
/* 2267:     */   class BeliefExpert
/* 2268:     */     extends BasicRule
/* 2269:     */   {
/* 2270:     */     BeliefExpert() {}
/* 2271:     */     
/* 2272:     */     public void run()
/* 2273:     */     {
/* 2274:2581 */       super.run();
/* 2275:2582 */       if ((this.firstLink.isAPrimed("evaluate")) && (!this.firstLink.isAPrimed("believes-event")) && (
/* 2276:2583 */         (this.firstLinkSubject.isA("living-thing")) || (this.firstLinkSubject.isA("group")) || (this.firstLinkSubject.isA("name"))))
/* 2277:     */       {
/* 2278:2584 */         addTypeAfterReference("evaluate", "believes-event", this.firstLink);
/* 2279:2585 */         succeeded();
/* 2280:     */       }
/* 2281:     */     }
/* 2282:     */   }
/* 2283:     */   
/* 2284:     */   class HowToExpertStarter
/* 2285:     */     extends BasicRule
/* 2286:     */   {
/* 2287:     */     HowToExpertStarter() {}
/* 2288:     */     
/* 2289:     */     public void run()
/* 2290:     */     {
/* 2291:2593 */       super.run();
/* 2292:2594 */       if (this.firstLink.isAPrimed("has_purpose"))
/* 2293:     */       {
/* 2294:2595 */         Sequence recipe = new Sequence("conjuction");
/* 2295:2596 */         recipe.addType("recipe");
/* 2296:2597 */         recipe.addElement(this.firstLinkSubject);
/* 2297:2598 */         Relation instructions = new Relation("cause", recipe, this.firstLinkObject);
/* 2298:2599 */         instructions.addType("means");
/* 2299:2600 */         replace(this.firstLink, instructions);
/* 2300:     */         
/* 2301:     */ 
/* 2302:2603 */         succeeded();
/* 2303:     */       }
/* 2304:     */     }
/* 2305:     */   }
/* 2306:     */   
/* 2307:     */   class HowToExpertAugmenter
/* 2308:     */     extends BasicRule2
/* 2309:     */   {
/* 2310:     */     HowToExpertAugmenter() {}
/* 2311:     */     
/* 2312:     */     public void run()
/* 2313:     */     {
/* 2314:2610 */       super.run();
/* 2315:2611 */       if ((this.firstLink.isAPrimed("means")) && 
/* 2316:2612 */         (this.secondLink.isAPrimed("has_purpose")) && 
/* 2317:2613 */         (this.firstLinkObject == this.secondLinkObject))
/* 2318:     */       {
/* 2319:2614 */         this.firstLinkSubject.addElement(this.secondLinkSubject);
/* 2320:2615 */         remove(this.secondLink);
/* 2321:2616 */         succeeded();
/* 2322:     */       }
/* 2323:     */     }
/* 2324:     */   }
/* 2325:     */   
/* 2326:     */   class InputJunkExpert
/* 2327:     */     extends BasicRule
/* 2328:     */   {
/* 2329:     */     InputJunkExpert() {}
/* 2330:     */     
/* 2331:     */     public void run()
/* 2332:     */     {
/* 2333:2625 */       super.run();
/* 2334:2626 */       if (NewRuleSet.this.isInputJunk(this.firstLink))
/* 2335:     */       {
/* 2336:2627 */         remove(this.firstLink);
/* 2337:2628 */         succeeded();
/* 2338:     */       }
/* 2339:     */     }
/* 2340:     */   }
/* 2341:     */   
/* 2342:     */   class OutputJunkExpert
/* 2343:     */     extends BasicRule
/* 2344:     */   {
/* 2345:     */     OutputJunkExpert() {}
/* 2346:     */     
/* 2347:     */     public void run()
/* 2348:     */     {
/* 2349:2635 */       super.run();
/* 2350:2636 */       if (NewRuleSet.this.isOutputJunk(this.firstLink))
/* 2351:     */       {
/* 2352:2637 */         remove(this.firstLink);
/* 2353:2638 */         succeeded();
/* 2354:     */       }
/* 2355:     */     }
/* 2356:     */   }
/* 2357:     */   
/* 2358:     */   private boolean isOutputJunk(Entity t)
/* 2359:     */   {
/* 2360:2644 */     if (t.isAPrimed("has_attribute")) {
/* 2361:2645 */       return true;
/* 2362:     */     }
/* 2363:2647 */     if (t.isAPrimed("has_root")) {
/* 2364:2648 */       return true;
/* 2365:     */     }
/* 2366:2650 */     if (t.isAPrimed("has_det")) {
/* 2367:2651 */       return true;
/* 2368:     */     }
/* 2369:2653 */     if (t.isAPrimed("is_pp")) {
/* 2370:2654 */       return true;
/* 2371:     */     }
/* 2372:2656 */     if (t.isAPrimed("has_comp")) {
/* 2373:2657 */       return true;
/* 2374:     */     }
/* 2375:2659 */     if (t.isAPrimed("related-to")) {
/* 2376:2660 */       return true;
/* 2377:     */     }
/* 2378:2662 */     return false;
/* 2379:     */   }
/* 2380:     */   
/* 2381:     */   private boolean isInputJunk(Entity t)
/* 2382:     */   {
/* 2383:2666 */     if (t.isAPrimed("verb_root")) {
/* 2384:2667 */       return true;
/* 2385:     */     }
/* 2386:2669 */     if (t.isAPrimed("has_person")) {
/* 2387:2670 */       return true;
/* 2388:     */     }
/* 2389:2672 */     if (t.isAPrimed("has_quality")) {
/* 2390:2673 */       return true;
/* 2391:     */     }
/* 2392:2675 */     if (t.isAPrimed("has_sign")) {
/* 2393:2676 */       return true;
/* 2394:     */     }
/* 2395:2678 */     if (t.isAPrimed("has_number")) {
/* 2396:2679 */       return true;
/* 2397:     */     }
/* 2398:2681 */     if (t.isAPrimed("is_clausal")) {
/* 2399:2682 */       return true;
/* 2400:     */     }
/* 2401:2684 */     if (t.isAPrimed("has_surface_form")) {
/* 2402:2685 */       return true;
/* 2403:     */     }
/* 2404:2687 */     if (t.isAPrimed("has_position")) {
/* 2405:2688 */       return true;
/* 2406:     */     }
/* 2407:2690 */     if (t.isAPrimed("has_category")) {
/* 2408:2691 */       return true;
/* 2409:     */     }
/* 2410:2693 */     if (t.isAPrimed("is_imperative")) {
/* 2411:2694 */       return true;
/* 2412:     */     }
/* 2413:2696 */     if (t.isAPrimed("has_voice")) {
/* 2414:2697 */       return true;
/* 2415:     */     }
/* 2416:2699 */     if (t.isAPrimed("has_argument")) {
/* 2417:2700 */       return true;
/* 2418:     */     }
/* 2419:2702 */     if (t.isAPrimed("passive_aux")) {
/* 2420:2703 */       return true;
/* 2421:     */     }
/* 2422:2705 */     if (t.isAPrimed("has_clause_type")) {
/* 2423:2706 */       return true;
/* 2424:     */     }
/* 2425:2708 */     if (t.isAPrimed("has_conjunction")) {
/* 2426:2709 */       return true;
/* 2427:     */     }
/* 2428:2711 */     if (t.isAPrimed("has_counter")) {
/* 2429:2712 */       return true;
/* 2430:     */     }
/* 2431:2714 */     if (t.isAPrimed("is_wh")) {
/* 2432:2715 */       return true;
/* 2433:     */     }
/* 2434:2722 */     return false;
/* 2435:     */   }
/* 2436:     */   
/* 2437:     */   class PurgeEmbeddingsExpert
/* 2438:     */     extends BasicRule2
/* 2439:     */   {
/* 2440:     */     PurgeEmbeddingsExpert() {}
/* 2441:     */     
/* 2442:     */     public void run()
/* 2443:     */     {
/* 2444:2727 */       super.run();
/* 2445:2729 */       if ((!this.secondLink.equals(Getters.getObject(this.firstLink))) || (!this.firstLink.isA(NewRuleSet.assertionWords))) {
/* 2446:2731 */         if ((!this.firstLink.equals(Getters.getObject(this.secondLink))) || (!this.secondLink.isA(NewRuleSet.assertionWords))) {
/* 2447:2733 */           if (NewRuleSet.this.inclusion(this.secondLink, this.firstLink))
/* 2448:     */           {
/* 2449:2735 */             remove(this.secondLink);
/* 2450:2736 */             succeeded();
/* 2451:     */           }
/* 2452:2738 */           else if (NewRuleSet.this.inclusion(this.firstLink, this.secondLink))
/* 2453:     */           {
/* 2454:2740 */             remove(this.firstLink);
/* 2455:2741 */             succeeded();
/* 2456:     */           }
/* 2457:     */         }
/* 2458:     */       }
/* 2459:     */     }
/* 2460:     */   }
/* 2461:     */   
/* 2462:     */   class TimeMarkerExpert
/* 2463:     */     extends BasicRule
/* 2464:     */   {
/* 2465:     */     TimeMarkerExpert() {}
/* 2466:     */     
/* 2467:     */     public void run()
/* 2468:     */     {
/* 2469:2748 */       super.run();
/* 2470:2749 */       if ((this.firstLink.relationP("property")) && (this.firstLinkObject.isAPrimed(NewRuleSet.timeMarkerWords)))
/* 2471:     */       {
/* 2472:2750 */         addLink(new Function("milestone", this.firstLinkObject));
/* 2473:2751 */         replace(this.firstLink, this.firstLinkSubject);
/* 2474:2752 */         succeeded();
/* 2475:     */       }
/* 2476:2754 */       else if ((this.firstLink.relationP("pass")) && (this.firstLinkSubject.isAPrimed("time")))
/* 2477:     */       {
/* 2478:2755 */         replace(this.firstLink, new Function("milestone", this.firstLinkObject));
/* 2479:2756 */         succeeded();
/* 2480:     */       }
/* 2481:     */     }
/* 2482:     */   }
/* 2483:     */   
/* 2484:     */   class AdvanceExpert
/* 2485:     */     extends BasicRule
/* 2486:     */   {
/* 2487:     */     AdvanceExpert() {}
/* 2488:     */     
/* 2489:     */     public void run()
/* 2490:     */     {
/* 2491:2763 */       super.run();
/* 2492:2764 */       if ((this.firstLink.relationP("has_purpose")) && 
/* 2493:2765 */         (this.firstLinkSubject.relationP("advance")) && (this.firstLinkObject.relationP("frame")))
/* 2494:     */       {
/* 2495:2767 */         Entity video = new Entity("video");
/* 2496:2768 */         video.addType("name");
/* 2497:2769 */         video.addType(this.firstLinkSubject.getObject().getType());
/* 2498:2770 */         Entity frame = new Entity("frame");
/* 2499:2771 */         frame.addType("name");
/* 2500:2772 */         frame.addType("number" + this.firstLinkObject.getObject().getType());
/* 2501:2773 */         Relation r = new Relation("imperative", video, frame);
/* 2502:2774 */         r.addType("advance");
/* 2503:2775 */         replace(this.firstLink, r);
/* 2504:     */         
/* 2505:2777 */         succeeded();
/* 2506:     */       }
/* 2507:     */     }
/* 2508:     */   }
/* 2509:     */   
/* 2510:     */   class ContactExpert
/* 2511:     */     extends BasicRule3
/* 2512:     */   {
/* 2513:     */     ContactExpert() {}
/* 2514:     */     
/* 2515:     */     public void run()
/* 2516:     */     {
/* 2517:2786 */       super.run();
/* 2518:2787 */       if ((this.firstLink.functionP("appear")) && (this.firstLinkSubject.isA("contact")) && 
/* 2519:2788 */         (this.secondLink.relationP("between")) && (this.secondLinkSubject == this.firstLinkSubject) && 
/* 2520:2789 */         (this.thirdLink.relationP("between")) && (this.thirdLinkSubject == this.firstLinkSubject))
/* 2521:     */       {
/* 2522:2790 */         Relation relation = new Relation("contact", this.secondLinkObject, this.thirdLinkObject);
/* 2523:2791 */         this.firstLink.setSubject(relation);
/* 2524:2792 */         remove(this.secondLink);
/* 2525:2793 */         remove(this.thirdLink);
/* 2526:2794 */         succeeded();
/* 2527:     */       }
/* 2528:     */     }
/* 2529:     */   }
/* 2530:     */   
/* 2531:     */   class PerfectiveExpert
/* 2532:     */     extends BasicRule
/* 2533:     */   {
/* 2534:     */     PerfectiveExpert() {}
/* 2535:     */     
/* 2536:     */     public void run()
/* 2537:     */     {
/* 2538:2803 */       super.run();
/* 2539:2804 */       if ((this.firstLink.relationP()) && (this.firstLink.isAPrimed("is_perfective")))
/* 2540:     */       {
/* 2541:2805 */         this.firstLinkSubject.addProperty("perfective", Boolean.valueOf(true));
/* 2542:2806 */         replace(this.firstLink, this.firstLinkSubject);
/* 2543:2807 */         succeeded();
/* 2544:     */       }
/* 2545:     */     }
/* 2546:     */   }
/* 2547:     */   
/* 2548:     */   private void makeRuleSet()
/* 2549:     */   {
/* 2550:2815 */     boolean verbose = false;
/* 2551:     */     
/* 2552:2817 */     addRule(new InputJunkExpert());
/* 2553:     */     
/* 2554:2819 */     addRule(new ingExpert());
/* 2555:     */     
/* 2556:     */ 
/* 2557:2822 */     addRule(new TenseExpert());
/* 2558:     */     
/* 2559:     */ 
/* 2560:     */ 
/* 2561:     */ 
/* 2562:     */ 
/* 2563:     */ 
/* 2564:     */ 
/* 2565:     */ 
/* 2566:     */ 
/* 2567:2832 */     addRule(new PropertyExpert());
/* 2568:     */     
/* 2569:2834 */     addRule(new PropertySubstitutor());
/* 2570:2835 */     addRule(new PropertyAbsorber());
/* 2571:     */     
/* 2572:2837 */     addRule(new StoryExpert());
/* 2573:2838 */     addRule(new ConceptExpert());
/* 2574:2839 */     addRule(new ActionExpert());
/* 2575:2840 */     addRule(new RoleExpert());
/* 2576:     */     
/* 2577:     */ 
/* 2578:2843 */     addRule(new CheckOnExpert());
/* 2579:     */     
/* 2580:     */ 
/* 2581:2846 */     addRule(new LocationExpert());
/* 2582:     */     
/* 2583:2848 */     addRule(new LocationElementExpert());
/* 2584:     */     
/* 2585:2850 */     addRule(new PlaceExpert());
/* 2586:2851 */     addRule(new TransitionExpert());
/* 2587:     */     
/* 2588:     */ 
/* 2589:2854 */     addRule(new AngryExpert());
/* 2590:2855 */     addRule(new TransferExpert());
/* 2591:2856 */     addRule(new SocialExpert());
/* 2592:     */     
/* 2593:     */ 
/* 2594:     */ 
/* 2595:     */ 
/* 2596:     */ 
/* 2597:2862 */     addRule(new ComparisonExpert());
/* 2598:     */     
/* 2599:     */ 
/* 2600:2865 */     addRule(new JobExpert());
/* 2601:2866 */     addRule(new JobExpert2());
/* 2602:     */     
/* 2603:2868 */     addRule(new BecomeExpert());
/* 2604:     */     
/* 2605:2870 */     addRule(new QuantifierExpert());
/* 2606:     */     
/* 2607:     */ 
/* 2608:     */ 
/* 2609:     */ 
/* 2610:2875 */     addRule(new PartOfExpert());
/* 2611:     */     
/* 2612:     */ 
/* 2613:     */ 
/* 2614:     */ 
/* 2615:2880 */     addRule(new SometimesTrap());
/* 2616:2881 */     addRule(new IntensifierExpert());
/* 2617:     */     
/* 2618:2883 */     addRule(new ThreadExpert());
/* 2619:2884 */     addRule(new ClassificationExpert());
/* 2620:2885 */     addRule(new NameExpert());
/* 2621:     */     
/* 2622:     */ 
/* 2623:2888 */     addRule(new TouchExpert());
/* 2624:     */     
/* 2625:     */ 
/* 2626:2891 */     addRule(new TimeExpert());
/* 2627:     */     
/* 2628:     */ 
/* 2629:     */ 
/* 2630:     */ 
/* 2631:2896 */     addRule(new GoalExpert());
/* 2632:2897 */     addRule(new ForceAndPersuadeExpert());
/* 2633:     */     
/* 2634:     */ 
/* 2635:     */ 
/* 2636:     */ 
/* 2637:2902 */     addRule(new DescribeExpert());
/* 2638:2903 */     addRule(new NegationExpert());
/* 2639:2904 */     addRule(new BeliefExpert());
/* 2640:2905 */     addRule(new ModeExpert());
/* 2641:2906 */     addRule(new PossibilityExpert());
/* 2642:     */     
/* 2643:     */ 
/* 2644:     */ 
/* 2645:2910 */     addRule(new DeterminerExpert());
/* 2646:2911 */     addRule(new AdjectiveExpert());
/* 2647:     */     
/* 2648:     */ 
/* 2649:2914 */     addRule(new ContactExpert());
/* 2650:     */     
/* 2651:2916 */     addRule(new CommandExpert());
/* 2652:     */     
/* 2653:2918 */     addRule(new EntailExpert());
/* 2654:2919 */     addRule(new WhatIfExpert());
/* 2655:2920 */     addRule(new IfExpert());
/* 2656:2921 */     addRule(new NominalizationExpert());
/* 2657:     */     
/* 2658:2923 */     addRule(new AgentCauseExpert());
/* 2659:2924 */     addRule(new MakeExpert());
/* 2660:     */     
/* 2661:2926 */     addRule(new WouldLikeExpert());
/* 2662:     */     
/* 2663:2928 */     addRule(new BecauseOfExpert());
/* 2664:2929 */     addRule(new CauseExpert());
/* 2665:2930 */     addRule(new CauseAntecedantsExpert());
/* 2666:2931 */     addRule(new CauseAntecedantsStarter());
/* 2667:2932 */     addRule(new CauseAntecedantsFixer());
/* 2668:     */     
/* 2669:     */ 
/* 2670:     */ 
/* 2671:     */ 
/* 2672:     */ 
/* 2673:     */ 
/* 2674:2939 */     addRule(new ImperativesAndQuestionsExpert());
/* 2675:2940 */     addRule(new ImperativesAndQuestionsExpert2());
/* 2676:2941 */     addRule(new ImperativesAndQuestionsExpert1());
/* 2677:2942 */     addRule(new WhatHappensWhenExpert());
/* 2678:     */     
/* 2679:2944 */     addRule(new ProgressiveExpert());
/* 2680:2945 */     addRule(new QuantityExpert());
/* 2681:2946 */     addRule(new AtExpert());
/* 2682:     */     
/* 2683:2948 */     addRule(new MainClauseExpert());
/* 2684:2949 */     addRule(new RelativeClauseExpert());
/* 2685:2950 */     addRule(new ClauseExpert());
/* 2686:     */     
/* 2687:2952 */     addRule(new InvertProperty());
/* 2688:     */     
/* 2689:2954 */     addRule(new AdvanceExpert());
/* 2690:2955 */     addRule(new PurgeEmbeddingsExpert());
/* 2691:     */     
/* 2692:     */ 
/* 2693:     */ 
/* 2694:     */ 
/* 2695:     */ 
/* 2696:     */ 
/* 2697:2962 */     addRule(new FrequencyExpert());
/* 2698:     */     
/* 2699:2964 */     addRule(new TimeMarkerExpert());
/* 2700:     */     
/* 2701:2966 */     addRule(new PerfectiveExpert());
/* 2702:     */     
/* 2703:2968 */     addRule(new HowToExpertAugmenter());
/* 2704:     */     
/* 2705:2970 */     addRule(new HowToExpertStarter());
/* 2706:     */     
/* 2707:2972 */     addRule(new EachOtherExpert());
/* 2708:     */     
/* 2709:2974 */     addRule(new SometimesExpert());
/* 2710:     */     
/* 2711:2976 */     addRule(new OutputJunkExpert());
/* 2712:     */     
/* 2713:2978 */     Mark.say(new Object[] {Boolean.valueOf(verbose), "Rule set includes", Integer.valueOf(getRuleSet().size()), "rules" });
/* 2714:     */   }
/* 2715:     */   
/* 2716:     */   public static void main(String[] ignore)
/* 2717:     */     throws Exception
/* 2718:     */   {
/* 2719:2983 */     TranslatorGeneratorTestApplication d = new TranslatorGeneratorTestApplication();
/* 2720:     */     
/* 2721:     */ 
/* 2722:     */ 
/* 2723:     */ 
/* 2724:     */ 
/* 2725:     */ 
/* 2726:     */ 
/* 2727:     */ 
/* 2728:     */ 
/* 2729:     */ 
/* 2730:     */ 
/* 2731:     */ 
/* 2732:     */ 
/* 2733:     */ 
/* 2734:     */ 
/* 2735:     */ 
/* 2736:     */ 
/* 2737:     */ 
/* 2738:     */ 
/* 2739:     */ 
/* 2740:     */ 
/* 2741:     */ 
/* 2742:     */ 
/* 2743:     */ 
/* 2744:     */ 
/* 2745:     */ 
/* 2746:     */ 
/* 2747:     */ 
/* 2748:     */ 
/* 2749:     */ 
/* 2750:     */ 
/* 2751:     */ 
/* 2752:     */ 
/* 2753:     */ 
/* 2754:     */ 
/* 2755:     */ 
/* 2756:     */ 
/* 2757:     */ 
/* 2758:     */ 
/* 2759:     */ 
/* 2760:     */ 
/* 2761:     */ 
/* 2762:     */ 
/* 2763:     */ 
/* 2764:     */ 
/* 2765:     */ 
/* 2766:     */ 
/* 2767:     */ 
/* 2768:     */ 
/* 2769:     */ 
/* 2770:     */ 
/* 2771:     */ 
/* 2772:     */ 
/* 2773:     */ 
/* 2774:     */ 
/* 2775:     */ 
/* 2776:     */ 
/* 2777:     */ 
/* 2778:     */ 
/* 2779:     */ 
/* 2780:     */ 
/* 2781:     */ 
/* 2782:     */ 
/* 2783:     */ 
/* 2784:     */ 
/* 2785:     */ 
/* 2786:     */ 
/* 2787:     */ 
/* 2788:     */ 
/* 2789:     */ 
/* 2790:     */ 
/* 2791:     */ 
/* 2792:     */ 
/* 2793:     */ 
/* 2794:     */ 
/* 2795:     */ 
/* 2796:     */ 
/* 2797:     */ 
/* 2798:     */ 
/* 2799:     */ 
/* 2800:     */ 
/* 2801:     */ 
/* 2802:     */ 
/* 2803:     */ 
/* 2804:     */ 
/* 2805:     */ 
/* 2806:     */ 
/* 2807:     */ 
/* 2808:     */ 
/* 2809:     */ 
/* 2810:     */ 
/* 2811:     */ 
/* 2812:     */ 
/* 2813:     */ 
/* 2814:     */ 
/* 2815:     */ 
/* 2816:     */ 
/* 2817:     */ 
/* 2818:     */ 
/* 2819:     */ 
/* 2820:     */ 
/* 2821:     */ 
/* 2822:     */ 
/* 2823:     */ 
/* 2824:     */ 
/* 2825:     */ 
/* 2826:     */ 
/* 2827:     */ 
/* 2828:     */ 
/* 2829:     */ 
/* 2830:     */ 
/* 2831:     */ 
/* 2832:     */ 
/* 2833:     */ 
/* 2834:     */ 
/* 2835:     */ 
/* 2836:     */ 
/* 2837:     */ 
/* 2838:     */ 
/* 2839:     */ 
/* 2840:     */ 
/* 2841:     */ 
/* 2842:     */ 
/* 2843:     */ 
/* 2844:     */ 
/* 2845:     */ 
/* 2846:     */ 
/* 2847:     */ 
/* 2848:     */ 
/* 2849:     */ 
/* 2850:     */ 
/* 2851:     */ 
/* 2852:     */ 
/* 2853:     */ 
/* 2854:     */ 
/* 2855:     */ 
/* 2856:     */ 
/* 2857:     */ 
/* 2858:     */ 
/* 2859:     */ 
/* 2860:     */ 
/* 2861:     */ 
/* 2862:     */ 
/* 2863:     */ 
/* 2864:     */ 
/* 2865:     */ 
/* 2866:     */ 
/* 2867:     */ 
/* 2868:     */ 
/* 2869:     */ 
/* 2870:     */ 
/* 2871:     */ 
/* 2872:     */ 
/* 2873:     */ 
/* 2874:     */ 
/* 2875:     */ 
/* 2876:     */ 
/* 2877:     */ 
/* 2878:     */ 
/* 2879:     */ 
/* 2880:     */ 
/* 2881:     */ 
/* 2882:     */ 
/* 2883:     */ 
/* 2884:     */ 
/* 2885:     */ 
/* 2886:     */ 
/* 2887:     */ 
/* 2888:     */ 
/* 2889:     */ 
/* 2890:     */ 
/* 2891:     */ 
/* 2892:     */ 
/* 2893:     */ 
/* 2894:     */ 
/* 2895:     */ 
/* 2896:     */ 
/* 2897:     */ 
/* 2898:     */ 
/* 2899:     */ 
/* 2900:     */ 
/* 2901:     */ 
/* 2902:     */ 
/* 2903:     */ 
/* 2904:     */ 
/* 2905:     */ 
/* 2906:     */ 
/* 2907:     */ 
/* 2908:     */ 
/* 2909:     */ 
/* 2910:     */ 
/* 2911:     */ 
/* 2912:     */ 
/* 2913:     */ 
/* 2914:     */ 
/* 2915:     */ 
/* 2916:     */ 
/* 2917:     */ 
/* 2918:     */ 
/* 2919:     */ 
/* 2920:     */ 
/* 2921:     */ 
/* 2922:     */ 
/* 2923:     */ 
/* 2924:     */ 
/* 2925:     */ 
/* 2926:     */ 
/* 2927:     */ 
/* 2928:     */ 
/* 2929:     */ 
/* 2930:     */ 
/* 2931:     */ 
/* 2932:     */ 
/* 2933:     */ 
/* 2934:     */ 
/* 2935:     */ 
/* 2936:     */ 
/* 2937:     */ 
/* 2938:     */ 
/* 2939:     */ 
/* 2940:     */ 
/* 2941:     */ 
/* 2942:     */ 
/* 2943:     */ 
/* 2944:     */ 
/* 2945:     */ 
/* 2946:     */ 
/* 2947:     */ 
/* 2948:     */ 
/* 2949:     */ 
/* 2950:     */ 
/* 2951:     */ 
/* 2952:     */ 
/* 2953:     */ 
/* 2954:     */ 
/* 2955:     */ 
/* 2956:     */ 
/* 2957:     */ 
/* 2958:     */ 
/* 2959:     */ 
/* 2960:     */ 
/* 2961:     */ 
/* 2962:     */ 
/* 2963:     */ 
/* 2964:     */ 
/* 2965:     */ 
/* 2966:3230 */     Mark.say(new Object[] {"Experimental?", Boolean.valueOf(Start.isExperimentalStart()) });
/* 2967:     */     
/* 2968:     */ 
/* 2969:     */ 
/* 2970:     */ 
/* 2971:     */ 
/* 2972:     */ 
/* 2973:3237 */     d.stimulate("check whether john loves mary.");
/* 2974:     */     
/* 2975:3239 */     Mark.say(new Object[] {"Experimental?", Boolean.valueOf(Start.isExperimentalStart()) });
/* 2976:3240 */     JFrame frame = new JFrame();
/* 2977:3241 */     frame.setDefaultCloseOperation(3);
/* 2978:3242 */     frame.getContentPane().add(d.getView());
/* 2979:3243 */     frame.setSize(800, 600);
/* 2980:3244 */     frame.setVisible(true);
/* 2981:3245 */     Switch.showTranslationDetails.setSelected(true);
/* 2982:     */   }
/* 2983:     */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     translator.NewRuleSet
 * JD-Core Version:    0.7.0.1
 */