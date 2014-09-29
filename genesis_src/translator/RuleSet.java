/*    1:     */ package translator;
/*    2:     */ 
/*    3:     */ import bridge.reps.entities.Entity;
/*    4:     */ import bridge.reps.entities.Function;
/*    5:     */ import bridge.reps.entities.Relation;
/*    6:     */ import bridge.reps.entities.Sequence;
/*    7:     */ import bridge.reps.entities.Thread;
/*    8:     */ import connections.AbstractWiredBox;
/*    9:     */ import java.io.PrintStream;
/*   10:     */ import java.util.ArrayList;
/*   11:     */ import java.util.Arrays;
/*   12:     */ import java.util.Iterator;
/*   13:     */ import java.util.List;
/*   14:     */ import java.util.Vector;
/*   15:     */ import tools.JFactory;
/*   16:     */ 
/*   17:     */ public class RuleSet
/*   18:     */   extends AbstractWiredBox
/*   19:     */ {
/*   20:  17 */   public static boolean reportSuccess = false;
/*   21:     */   protected ArrayList<Rule> ruleSet;
/*   22:  22 */   protected static List<String> pathPrepositions = Arrays.asList(new String[] {"from", "to", "into", "over", "under", "toward", "via", "behind", "between", "past", "by", "over", "above", "down", "up", "under", "below", "on", "in", "near", "off" });
/*   23:  24 */   public static List<String> placePrepositions = Arrays.asList(new String[] { "at", "side", "top", "bottom", "left", "right", "inside", "front", "back" });
/*   24:  26 */   protected static List<String> transitionWords = Arrays.asList(new String[] { "appear", "disappear", "change", "increase", "decrease" });
/*   25:  28 */   protected static List<String> travelWords = Arrays.asList(new String[] { "travel", "leave", "move", "roll", "decrease" });
/*   26:  30 */   protected static List<String> timeWords = Arrays.asList(new String[] { "before", "after", "while" });
/*   27:  32 */   protected static List<String> requireWords = Arrays.asList(new String[] { "force", "desire", "induce", "ask", "necessitate", "express" });
/*   28:  34 */   protected static List<String> transferWords = Arrays.asList(new String[] { "give", "propel", "push", "roll" });
/*   29:  36 */   protected static List<String> roleWords = Arrays.asList(new String[] { "by", "with", "for" });
/*   30:  39 */   protected static List<String> mentalStateWords = Arrays.asList(new String[] {"angry", "calm", "happy", "sad", "unhappy", "excited", "tired", "awake", "asleep", "alive", "dead" });
/*   31:  41 */   protected Entity root = new Entity("root");
/*   32:     */   
/*   33:     */   public RuleSet()
/*   34:     */   {
/*   35:  44 */     makeRuleSet();
/*   36:     */   }
/*   37:     */   
/*   38:     */   private void makeRuleSet()
/*   39:     */   {
/*   40:  50 */     addRule(new KillRedundantRoots());
/*   41:  51 */     addRule(new MergeComplementaryRoles());
/*   42:     */     
/*   43:     */ 
/*   44:  54 */     addRule(new AbsorbDeterminer());
/*   45:  55 */     addRule(new AbsorbeAdjective());
/*   46:  56 */     addRule(new ProcessRegion());
/*   47:     */     
/*   48:     */ 
/*   49:  59 */     addRule(new AbsorbAuxiliary());
/*   50:  60 */     addRule(new AbsorbNegation());
/*   51:     */     
/*   52:     */ 
/*   53:  63 */     addRule(new ProcessWhat());
/*   54:     */     
/*   55:     */ 
/*   56:     */ 
/*   57:     */ 
/*   58:     */ 
/*   59:     */ 
/*   60:  70 */     addRule(new ProcessClassification());
/*   61:  71 */     addRule(new ProcessClassificationQuestion());
/*   62:     */     
/*   63:     */ 
/*   64:  74 */     addRule(new ProcessMentalState());
/*   65:     */     
/*   66:     */ 
/*   67:  77 */     addRule(new EndOfStoryIdiom());
/*   68:     */     
/*   69:     */ 
/*   70:  80 */     addRule(new ProcessOfDeletionA());
/*   71:  81 */     addRule(new ProcessOfDeletionB());
/*   72:  82 */     addRule(new ProcessPath());
/*   73:  83 */     addRule(new ProcessPathFunction());
/*   74:     */     
/*   75:     */ 
/*   76:  86 */     addRule(new ProcessTransfer());
/*   77:     */     
/*   78:     */ 
/*   79:  89 */     addRule(new ProcessTrajectory());
/*   80:  90 */     addRule(new ProcessTouch());
/*   81:     */     
/*   82:     */ 
/*   83:  93 */     addRule(new ProcessTransition());
/*   84:     */     
/*   85:     */ 
/*   86:  96 */     addRule(new ProcessAction());
/*   87:  97 */     addRule(new ProcessRoles());
/*   88:     */     
/*   89:     */ 
/*   90: 100 */     addRule(new Force());
/*   91:     */     
/*   92:     */ 
/*   93:     */ 
/*   94:     */ 
/*   95:     */ 
/*   96: 106 */     addRule(new ProcessImagine());
/*   97: 107 */     addRule(new ProcessDescribe());
/*   98:     */     
/*   99:     */ 
/*  100: 110 */     addRule(new ProcessDoQuestion());
/*  101: 111 */     addRule(new ProcessDoTimeQuestion());
/*  102: 112 */     addRule(new ProcessQuestion());
/*  103:     */     
/*  104:     */ 
/*  105: 115 */     addRule(new ProcessWhy());
/*  106:     */   }
/*  107:     */   
/*  108:     */   private void addRule(BasicRule rule)
/*  109:     */   {
/*  110: 119 */     getRuleSet().add(new Rule(rule));
/*  111:     */   }
/*  112:     */   
/*  113:     */   protected ArrayList<Rule> getRuleSet()
/*  114:     */   {
/*  115: 123 */     if (this.ruleSet == null) {
/*  116: 124 */       this.ruleSet = new ArrayList();
/*  117:     */     }
/*  118: 126 */     return this.ruleSet;
/*  119:     */   }
/*  120:     */   
/*  121:     */   class ProcessDoTimeQuestion
/*  122:     */     extends BasicRule
/*  123:     */   {
/*  124: 166 */     ArrayList firstWords = new ArrayList();
/*  125: 168 */     ArrayList secondWords = new ArrayList();
/*  126:     */     
/*  127:     */     public ProcessDoTimeQuestion()
/*  128:     */     {
/*  129: 171 */       this.firstWords.addAll(RuleSet.timeWords);
/*  130: 172 */       this.firstWords.add("because");
/*  131: 173 */       this.secondWords.add("do");
/*  132: 174 */       this.secondWords.add("have");
/*  133:     */     }
/*  134:     */     
/*  135:     */     public void run()
/*  136:     */     {
/*  137: 178 */       super.run();
/*  138: 179 */       if ((this.firstLinkSubject.isAPrimed(this.firstWords)) && (this.firstLinkObject.isAPrimed(this.secondWords)))
/*  139:     */       {
/*  140: 180 */         Function question = new Function("question", this.firstLinkSubject);
/*  141: 181 */         question.addType("do");
/*  142: 182 */         replace(this.firstLink, new Relation("link", RuleSet.this.root, question));
/*  143: 183 */         succeeded();
/*  144:     */       }
/*  145: 185 */       else if ((this.firstLinkObject.isAPrimed(this.firstWords)) && (this.firstLinkSubject.isAPrimed(this.secondWords)))
/*  146:     */       {
/*  147: 186 */         Function question = new Function("question", this.firstLinkObject);
/*  148: 187 */         question.addType("do");
/*  149: 188 */         replace(this.firstLink, new Relation("link", RuleSet.this.root, question));
/*  150: 189 */         succeeded();
/*  151:     */       }
/*  152:     */     }
/*  153:     */   }
/*  154:     */   
/*  155:     */   private Function getNewDerivative(String type, Entity t)
/*  156:     */   {
/*  157: 218 */     Thread thread = new Thread();
/*  158: 219 */     thread.add(type);
/*  159: 220 */     Function result = new Function(t);
/*  160: 221 */     result.replacePrimedThread(thread);
/*  161: 222 */     return result;
/*  162:     */   }
/*  163:     */   
/*  164:     */   public static List getPathPrepositions()
/*  165:     */   {
/*  166: 226 */     return pathPrepositions;
/*  167:     */   }
/*  168:     */   
/*  169:     */   class KillRedundantRoots
/*  170:     */     extends BasicRule2
/*  171:     */   {
/*  172:     */     KillRedundantRoots() {}
/*  173:     */     
/*  174:     */     public void run()
/*  175:     */     {
/*  176: 236 */       super.run();
/*  177: 237 */       if ((this.firstLinkSubject == RuleSet.this.root) && 
/*  178: 238 */         (this.secondLinkSubject == RuleSet.this.root)) {
/*  179: 239 */         if (firstInsideSecond(this.firstLinkObject, this.secondLinkObject))
/*  180:     */         {
/*  181: 240 */           remove(this.firstLink);
/*  182: 241 */           succeeded();
/*  183:     */         }
/*  184: 243 */         else if (firstInsideSecond(this.secondLinkObject, this.firstLinkObject))
/*  185:     */         {
/*  186: 244 */           remove(this.secondLink);
/*  187: 245 */           succeeded();
/*  188:     */         }
/*  189:     */       }
/*  190:     */     }
/*  191:     */   }
/*  192:     */   
/*  193:     */   class AbsorbDeterminer
/*  194:     */     extends BasicRule
/*  195:     */   {
/*  196:     */     AbsorbDeterminer() {}
/*  197:     */     
/*  198:     */     public void run()
/*  199:     */     {
/*  200: 257 */       super.run();
/*  201: 258 */       if (getFirstLink().getObject().isA("part-of-speech-dt"))
/*  202:     */       {
/*  203: 259 */         if (this.firstLinkObject.isAPrimed("the")) {
/*  204: 260 */           this.firstLinkSubject.addType("definite", "feature");
/*  205: 262 */         } else if ((this.firstLinkObject.isAPrimed("a")) || (this.firstLinkObject.isAPrimed("an"))) {
/*  206: 263 */           this.firstLinkSubject.addType("indefinite", "feature");
/*  207:     */         }
/*  208: 265 */         remove(getFirstLink());
/*  209: 266 */         succeeded();
/*  210:     */       }
/*  211:     */     }
/*  212:     */   }
/*  213:     */   
/*  214:     */   class EndOfStoryIdiom
/*  215:     */     extends BasicRule
/*  216:     */   {
/*  217:     */     EndOfStoryIdiom() {}
/*  218:     */     
/*  219:     */     public void run()
/*  220:     */     {
/*  221: 276 */       super.run();
/*  222: 277 */       if ((this.firstLinkSubject.isA("end")) && 
/*  223: 278 */         (this.firstLinkObject.isAPrimed("is")) && 
/*  224: 279 */         (this.firstLinkSubject.functionP()))
/*  225:     */       {
/*  226: 280 */         Function derivative = (Function)this.firstLinkSubject;
/*  227: 281 */         if (derivative.getSubject().isAPrimed("story"))
/*  228:     */         {
/*  229: 282 */           replace(this.firstLink, new Relation("link", RuleSet.this.root, this.firstLinkSubject));
/*  230: 283 */           succeeded();
/*  231:     */         }
/*  232:     */       }
/*  233:     */     }
/*  234:     */   }
/*  235:     */   
/*  236:     */   class AbsorbeAdjective
/*  237:     */     extends BasicRule
/*  238:     */   {
/*  239:     */     AbsorbeAdjective() {}
/*  240:     */     
/*  241:     */     public void run()
/*  242:     */     {
/*  243: 297 */       super.run();
/*  244: 298 */       if ((this.firstLinkSubject.isAPrimed("entity")) && (this.firstLink.isA("adjectival-modifier")))
/*  245:     */       {
/*  246: 299 */         this.firstLinkSubject.addType(this.firstLinkObject.getType(), "feature");
/*  247: 300 */         remove(this.firstLink);
/*  248: 301 */         succeeded();
/*  249:     */       }
/*  250:     */     }
/*  251:     */   }
/*  252:     */   
/*  253:     */   class AbsorbAuxiliary
/*  254:     */     extends BasicRule
/*  255:     */   {
/*  256:     */     AbsorbAuxiliary() {}
/*  257:     */     
/*  258:     */     public void run()
/*  259:     */     {
/*  260: 315 */       super.run();
/*  261: 316 */       if ((this.firstLinkSubject.isAPrimed("action")) && (this.firstLink.isA("auxiliary")))
/*  262:     */       {
/*  263: 317 */         if (this.firstLinkObject.isAPrimed("do")) {
/*  264: 318 */           return;
/*  265:     */         }
/*  266: 320 */         if (this.firstLinkObject.isAPrimed("to")) {
/*  267: 321 */           return;
/*  268:     */         }
/*  269: 323 */         this.firstLinkSubject.addType(this.firstLinkObject.getType(), "feature");
/*  270: 324 */         remove(this.firstLink);
/*  271: 325 */         succeeded();
/*  272:     */       }
/*  273:     */     }
/*  274:     */   }
/*  275:     */   
/*  276:     */   class AbsorbNegation
/*  277:     */     extends BasicRule
/*  278:     */   {
/*  279:     */     AbsorbNegation() {}
/*  280:     */     
/*  281:     */     public void run()
/*  282:     */     {
/*  283: 339 */       super.run();
/*  284: 340 */       if ((this.firstLinkSubject.isAPrimed("action")) && (this.firstLink.isA("negation-modifier")))
/*  285:     */       {
/*  286: 341 */         this.firstLinkSubject.addType(this.firstLinkObject.getType(), "feature");
/*  287: 342 */         remove(this.firstLink);
/*  288: 343 */         succeeded();
/*  289:     */       }
/*  290:     */     }
/*  291:     */   }
/*  292:     */   
/*  293:     */   class ProcessRegion
/*  294:     */     extends BasicRule2
/*  295:     */   {
/*  296:     */     ProcessRegion() {}
/*  297:     */     
/*  298:     */     public void run()
/*  299:     */     {
/*  300: 356 */       super.run();
/*  301: 357 */       if ((this.firstLinkSubject.isA("location")) && (this.firstLinkObject.isAPrimed("of")) && 
/*  302: 358 */         (this.secondLinkSubject == this.firstLinkObject) && (this.secondLinkObject.isAPrimed("entity")))
/*  303:     */       {
/*  304: 359 */         Function place = new Function("place", this.secondLinkObject);
/*  305: 360 */         remove(this.firstLink);
/*  306: 361 */         remove(this.secondLink);
/*  307: 362 */         transferTypes(this.firstLinkSubject, place);
/*  308: 363 */         replace(this.firstLinkSubject, place);
/*  309: 364 */         succeeded();
/*  310:     */       }
/*  311:     */     }
/*  312:     */   }
/*  313:     */   
/*  314:     */   class ProcessClassification
/*  315:     */     extends BasicRule2
/*  316:     */   {
/*  317:     */     ProcessClassification() {}
/*  318:     */     
/*  319:     */     public void run()
/*  320:     */     {
/*  321: 393 */       super.run();
/*  322: 394 */       if ((this.firstLinkSubject.isAPrimed("entity")) && 
/*  323: 395 */         (this.secondLinkSubject == this.firstLinkSubject) && (this.secondLinkObject.isAPrimed("is")))
/*  324:     */       {
/*  325: 396 */         Thread thread = this.firstLinkObject.getThread("feature");
/*  326:     */         Relation classification;
/*  327:     */         Relation classification;
/*  328: 398 */         if (thread == null)
/*  329:     */         {
/*  330: 399 */           classification = new Relation("classification", this.firstLinkSubject, this.firstLinkObject);
/*  331:     */         }
/*  332:     */         else
/*  333:     */         {
/*  334:     */           Relation classification;
/*  335: 401 */           if (thread.contains("indefinite"))
/*  336:     */           {
/*  337:     */             Relation classification;
/*  338: 402 */             if (this.firstLinkObject.isAPrimed("unknownWord")) {
/*  339: 403 */               classification = new Relation("type", this.firstLinkSubject, this.firstLinkObject);
/*  340:     */             } else {
/*  341: 406 */               classification = new Relation("classification", this.firstLinkSubject, this.firstLinkObject);
/*  342:     */             }
/*  343:     */           }
/*  344:     */           else
/*  345:     */           {
/*  346:     */             Relation classification;
/*  347: 409 */             if (thread.contains("definite")) {
/*  348: 410 */               classification = new Relation("classification", this.firstLinkSubject, this.firstLinkObject);
/*  349:     */             } else {
/*  350: 413 */               classification = new Relation("classification", this.firstLinkSubject, this.firstLinkObject);
/*  351:     */             }
/*  352:     */           }
/*  353:     */         }
/*  354: 415 */         String type = this.firstLinkObject.getType();
/*  355:     */         
/*  356:     */ 
/*  357: 418 */         this.firstLinkObject.addThread(this.firstLinkSubject.getPrimedThread().copyThread());
/*  358: 419 */         this.firstLinkObject.addType(type);
/*  359: 420 */         replace(this.firstLink, new Relation("link", RuleSet.this.root, classification));
/*  360: 421 */         remove(this.secondLink);
/*  361: 422 */         succeeded();
/*  362:     */       }
/*  363:     */     }
/*  364:     */   }
/*  365:     */   
/*  366:     */   class ProcessMentalState
/*  367:     */     extends BasicRule2
/*  368:     */   {
/*  369:     */     ProcessMentalState() {}
/*  370:     */     
/*  371:     */     public void run()
/*  372:     */     {
/*  373: 434 */       super.run();
/*  374: 435 */       if ((this.firstLinkSubject.isAPrimed(RuleSet.mentalStateWords)) && (this.firstLinkObject.isAPrimed("entity")) && 
/*  375: 436 */         (this.secondLinkSubject == this.firstLinkSubject) && (this.secondLinkObject.isAPrimed("is")))
/*  376:     */       {
/*  377: 439 */         Entity quality = new Entity("mental-state");
/*  378: 440 */         quality.addType((String)this.firstLinkSubject.getThread("feature").lastElement());
/*  379: 441 */         Relation mentalState = new Relation("has-mental-state", this.firstLinkObject, quality);
/*  380: 442 */         replace(this.firstLink, new Relation("link", RuleSet.this.root, mentalState));
/*  381: 443 */         remove(this.secondLink);
/*  382: 444 */         succeeded();
/*  383:     */       }
/*  384:     */     }
/*  385:     */   }
/*  386:     */   
/*  387:     */   class ProcessClassificationQuestion
/*  388:     */     extends BasicRule2
/*  389:     */   {
/*  390:     */     ProcessClassificationQuestion() {}
/*  391:     */     
/*  392:     */     public void run()
/*  393:     */     {
/*  394: 455 */       super.run();
/*  395: 456 */       if ((this.firstLinkSubject.isAPrimed("thing")) && (this.firstLinkObject.isAPrimed("is")) && 
/*  396: 457 */         (this.secondLinkSubject == this.firstLinkSubject) && (this.secondLinkObject.isAPrimed("entity")))
/*  397:     */       {
/*  398: 458 */         Relation classification = new Relation("classification", this.firstLinkSubject, this.secondLinkObject);
/*  399: 459 */         classification.addType("question");
/*  400: 460 */         replace(this.firstLink, new Relation("link", RuleSet.this.root, classification));
/*  401: 461 */         remove(this.secondLink);
/*  402: 462 */         succeeded();
/*  403:     */       }
/*  404:     */     }
/*  405:     */   }
/*  406:     */   
/*  407:     */   class ProcessOfDeletionA
/*  408:     */     extends BasicRule2
/*  409:     */   {
/*  410: 473 */     ArrayList words = new ArrayList();
/*  411:     */     
/*  412:     */     ProcessOfDeletionA() {}
/*  413:     */     
/*  414:     */     public void run()
/*  415:     */     {
/*  416: 476 */       super.run();
/*  417: 477 */       if ((this.firstLinkSubject.isAPrimed(RuleSet.travelWords)) && (this.firstLinkObject.isAPrimed("off")) && 
/*  418: 478 */         (this.secondLinkSubject.isAPrimed(RuleSet.travelWords)) && (this.secondLinkObject.isAPrimed("of")))
/*  419:     */       {
/*  420: 479 */         remove(this.secondLink);
/*  421: 480 */         succeeded();
/*  422:     */       }
/*  423:     */     }
/*  424:     */   }
/*  425:     */   
/*  426:     */   class ProcessOfDeletionB
/*  427:     */     extends BasicRule2
/*  428:     */   {
/*  429: 491 */     ArrayList words = new ArrayList();
/*  430:     */     
/*  431:     */     ProcessOfDeletionB() {}
/*  432:     */     
/*  433:     */     public void run()
/*  434:     */     {
/*  435: 494 */       super.run();
/*  436: 495 */       if ((this.firstLinkSubject.isAPrimed(RuleSet.travelWords)) && (this.firstLinkObject.isAPrimed("off")) && 
/*  437: 496 */         (this.secondLinkSubject.isAPrimed("of")) && (this.secondLinkObject.isAPrimed("entity")))
/*  438:     */       {
/*  439: 497 */         Function at = new Function("at", this.secondLinkObject);
/*  440: 498 */         Function pathFunction = new Function("pathFunction", at);
/*  441: 499 */         transferTypes(this.firstLinkObject, pathFunction);
/*  442: 500 */         if (this.firstLinkSubject.relationP())
/*  443:     */         {
/*  444: 501 */           Entity t = this.firstLinkSubject.getObject();
/*  445: 502 */           if (t.sequenceP())
/*  446:     */           {
/*  447: 503 */             Sequence path = (Sequence)t;
/*  448: 504 */             path.addElement(pathFunction);
/*  449: 505 */             remove(this.firstLink);
/*  450: 506 */             remove(this.secondLink);
/*  451: 507 */             succeeded();
/*  452:     */           }
/*  453:     */         }
/*  454:     */       }
/*  455:     */     }
/*  456:     */   }
/*  457:     */   
/*  458:     */   class ProcessPath
/*  459:     */     extends BasicRule2
/*  460:     */   {
/*  461: 519 */     ArrayList placeClasses = new ArrayList();
/*  462: 521 */     ArrayList trajectoryClasses = new ArrayList();
/*  463:     */     
/*  464:     */     public ProcessPath()
/*  465:     */     {
/*  466: 524 */       this.placeClasses.add("entity");
/*  467: 525 */       this.placeClasses.add("location");
/*  468: 526 */       this.trajectoryClasses.add("trajectory");
/*  469: 527 */       this.trajectoryClasses.add("state");
/*  470:     */     }
/*  471:     */     
/*  472:     */     public void run()
/*  473:     */     {
/*  474: 531 */       super.run();
/*  475: 533 */       if ((getFirstLink().getSubject().isAPrimed(this.trajectoryClasses)) && (getFirstLink().getObject().isAPrimed(RuleSet.pathPrepositions))) {
/*  476: 536 */         if ((getSecondLink().getSubject() == getFirstLink().getObject()) && (getSecondLink().getObject().isAPrimed(this.placeClasses)))
/*  477:     */         {
/*  478: 538 */           Relation trajectory = (Relation)getFirstLink().getSubject();
/*  479: 539 */           Sequence path = (Sequence)trajectory.getObject();
/*  480:     */           Function place;
/*  481:     */           Function place;
/*  482: 542 */           if ((this.secondLinkObject.functionP()) && (RuleSet.placePrepositions.contains(this.secondLinkObject.getType()))) {
/*  483: 543 */             place = (Function)this.secondLinkObject;
/*  484:     */           } else {
/*  485: 546 */             place = new Function("at", getSecondLink().getObject());
/*  486:     */           }
/*  487: 548 */           Function pathFunction = new Function("pathFunction", place);
/*  488: 549 */           pathFunction.addType(getSecondLink().getSubject().getType());
/*  489: 550 */           path.addElement(pathFunction);
/*  490: 551 */           remove(getFirstLink());
/*  491: 552 */           remove(getSecondLink());
/*  492: 553 */           succeeded();
/*  493:     */         }
/*  494:     */       }
/*  495:     */     }
/*  496:     */   }
/*  497:     */   
/*  498:     */   class ProcessPathFunction
/*  499:     */     extends BasicRule2
/*  500:     */   {
/*  501: 564 */     ArrayList words = new ArrayList();
/*  502:     */     
/*  503:     */     public ProcessPathFunction()
/*  504:     */     {
/*  505: 567 */       this.words.addAll(RuleSet.travelWords);
/*  506: 568 */       this.words.add("leave");
/*  507: 569 */       this.words.add("move");
/*  508: 570 */       this.words.add("roll");
/*  509:     */     }
/*  510:     */     
/*  511:     */     public void run()
/*  512:     */     {
/*  513: 574 */       super.run();
/*  514: 575 */       if ((this.firstLinkSubject.isAPrimed(this.words)) && (this.firstLinkObject.isAPrimed(RuleSet.pathPrepositions)) && 
/*  515: 576 */         (this.secondLinkSubject == this.firstLinkSubject) && (this.secondLinkObject.isAPrimed("entity")))
/*  516:     */       {
/*  517: 578 */         Function at = new Function("at", this.secondLinkObject);
/*  518: 579 */         Function pathFunction = new Function("pathFunction", at);
/*  519: 580 */         transferTypes(this.firstLinkObject, pathFunction);
/*  520: 581 */         if (this.firstLinkSubject.relationP())
/*  521:     */         {
/*  522: 582 */           Entity t = this.firstLinkSubject.getObject();
/*  523: 583 */           if (t.sequenceP())
/*  524:     */           {
/*  525: 584 */             Sequence path = (Sequence)t;
/*  526: 585 */             path.addElement(pathFunction);
/*  527: 586 */             remove(this.firstLink);
/*  528: 587 */             remove(this.secondLink);
/*  529: 588 */             succeeded();
/*  530:     */           }
/*  531:     */         }
/*  532:     */       }
/*  533:     */     }
/*  534:     */   }
/*  535:     */   
/*  536:     */   class ProcessTrajectory
/*  537:     */     extends BasicRule
/*  538:     */   {
/*  539:     */     ProcessTrajectory() {}
/*  540:     */     
/*  541:     */     public void run()
/*  542:     */     {
/*  543: 601 */       super.run();
/*  544: 602 */       if ((this.firstLinkSubject.isAPrimed(RuleSet.travelWords)) && (this.firstLinkObject.isAPrimed("entity")))
/*  545:     */       {
/*  546: 603 */         Sequence path = JFactory.createPath();
/*  547: 604 */         Relation go = new Relation(getFirstLink().getObject(), path);
/*  548: 605 */         transferTypes(getFirstLink().getSubject(), go);
/*  549: 606 */         addTypeAfterReference("action", "trajectory", go);
/*  550: 607 */         replace(getFirstLink(), new Relation("link", RuleSet.this.root, go));
/*  551: 608 */         replace(getFirstLink().getSubject(), go);
/*  552: 609 */         succeeded();
/*  553:     */       }
/*  554: 611 */       else if ((this.firstLinkObject.isAPrimed(RuleSet.travelWords)) && (this.firstLinkSubject.isAPrimed("entity")))
/*  555:     */       {
/*  556: 612 */         Sequence path = JFactory.createPath();
/*  557: 613 */         Relation go = new Relation(getFirstLink().getSubject(), path);
/*  558: 614 */         transferTypes(getFirstLink().getObject(), go);
/*  559: 615 */         addTypeAfterReference("action", "trajectory", go);
/*  560: 616 */         replace(getFirstLink(), new Relation("link", RuleSet.this.root, go));
/*  561: 617 */         replace(getFirstLink().getObject(), go);
/*  562: 618 */         succeeded();
/*  563:     */       }
/*  564:     */     }
/*  565:     */   }
/*  566:     */   
/*  567:     */   class ProcessTouch
/*  568:     */     extends BasicRule2
/*  569:     */   {
/*  570:     */     ProcessTouch() {}
/*  571:     */     
/*  572:     */     public void run()
/*  573:     */     {
/*  574: 628 */       super.run();
/*  575: 629 */       if ((this.firstLinkSubject.isAPrimed("touch")) && (this.firstLinkObject.isAPrimed("entity")) && 
/*  576: 630 */         (this.secondLinkSubject == this.firstLinkSubject) && (this.secondLinkObject.isAPrimed("entity")) && 
/*  577: 631 */         (this.firstLink.isAPrimed("nominal-subject")) && (!this.secondLink.isAPrimed("nominal-subject")))
/*  578:     */       {
/*  579: 632 */         Relation contact = new Relation("contact", this.firstLinkObject, this.secondLinkObject);
/*  580: 633 */         Function transition = RuleSet.this.getNewDerivative("action", contact);
/*  581: 634 */         transition.addType("transition");
/*  582: 635 */         transition.addType("appear");
/*  583: 636 */         remove(this.secondLink);
/*  584: 637 */         replace(this.firstLinkSubject, transition);
/*  585: 638 */         replace(this.firstLink, new Relation("link", RuleSet.this.root, transition));
/*  586: 639 */         succeeded();
/*  587:     */       }
/*  588:     */     }
/*  589:     */   }
/*  590:     */   
/*  591:     */   class ProcessTransition
/*  592:     */     extends BasicRule
/*  593:     */   {
/*  594:     */     ProcessTransition() {}
/*  595:     */     
/*  596:     */     public void run()
/*  597:     */     {
/*  598: 651 */       super.run();
/*  599: 652 */       if ((getFirstLink().getSubject().isAPrimed(RuleSet.transitionWords)) && (getFirstLink().getObject().isAPrimed("entity")))
/*  600:     */       {
/*  601: 654 */         Function transition = new Function(getFirstLink().getObject());
/*  602: 655 */         transferTypes(getFirstLink().getSubject(), transition);
/*  603: 656 */         replace(getFirstLink(), new Relation("link", RuleSet.this.root, transition));
/*  604: 657 */         replace(getFirstLink().getSubject(), transition);
/*  605: 658 */         addTypeAfterReference("action", "transition", transition);
/*  606: 659 */         succeeded();
/*  607:     */       }
/*  608: 661 */       else if ((getFirstLink().getObject().isAPrimed(RuleSet.transitionWords)) && (getFirstLink().getSubject().isAPrimed("entity")))
/*  609:     */       {
/*  610: 663 */         Function transition = new Function(getFirstLink().getSubject());
/*  611: 664 */         transferTypes(getFirstLink().getObject(), transition);
/*  612: 665 */         replace(getFirstLink(), new Relation("link", RuleSet.this.root, transition));
/*  613: 666 */         replace(getFirstLink().getObject(), transition);
/*  614: 667 */         addTypeAfterReference("action", "transition", transition);
/*  615: 668 */         succeeded();
/*  616:     */       }
/*  617:     */     }
/*  618:     */   }
/*  619:     */   
/*  620:     */   class ProcessAction
/*  621:     */     extends BasicRule
/*  622:     */   {
/*  623:     */     ProcessAction() {}
/*  624:     */     
/*  625:     */     public void run()
/*  626:     */     {
/*  627: 678 */       super.run();
/*  628: 679 */       if ((this.firstLinkSubject.isAPrimed("action")) && (!this.firstLinkSubject.isAPrimed("imagine")) && (!this.firstLinkSubject.isAPrimed(RuleSet.requireWords)) && 
/*  629: 680 */         (!this.firstLinkSubject.isAPrimed(RuleSet.transferWords)) && (this.firstLinkSubject.functionP()) && (this.firstLinkObject.isAPrimed("entity")))
/*  630:     */       {
/*  631: 681 */         System.out.println("Transfer words: " + RuleSet.transferWords);
/*  632: 682 */         System.out.println("Test: " + this.firstLinkSubject.isAPrimed(RuleSet.pathPrepositions));
/*  633:     */         
/*  634: 684 */         Sequence bag = new Sequence("bag");
/*  635: 685 */         Relation roleCoupler = new Relation("roles", this.firstLinkSubject, bag);
/*  636: 686 */         Function slot = new Function("object", this.firstLinkObject);
/*  637: 687 */         bag.addElement(slot);
/*  638: 688 */         replace(this.firstLink, new Relation("link", RuleSet.this.root, roleCoupler));
/*  639: 689 */         succeeded();
/*  640:     */       }
/*  641: 691 */       else if ((this.firstLinkSubject.isAPrimed("action")) && (!this.firstLinkSubject.isAPrimed("imagine")) && (!this.firstLinkSubject.isAPrimed(RuleSet.requireWords)) && 
/*  642: 692 */         (!this.firstLinkSubject.isAPrimed(RuleSet.transferWords)) && (this.firstLinkObject.isAPrimed("entity")))
/*  643:     */       {
/*  644: 694 */         Function action = new Function(this.firstLinkObject);
/*  645: 695 */         transferTypes(this.firstLinkSubject, action);
/*  646: 696 */         replace(this.firstLink, new Relation("link", RuleSet.this.root, action));
/*  647: 697 */         replace(getFirstLink().getSubject(), action);
/*  648: 698 */         succeeded();
/*  649:     */       }
/*  650:     */     }
/*  651:     */   }
/*  652:     */   
/*  653:     */   class ProcessRoles
/*  654:     */     extends BasicRule2
/*  655:     */   {
/*  656:     */     ProcessRoles() {}
/*  657:     */     
/*  658:     */     public void run()
/*  659:     */     {
/*  660: 708 */       super.run();
/*  661: 709 */       if ((this.firstLinkObject.isAPrimed(RuleSet.roleWords)) && (this.firstLinkObject == this.secondLinkSubject))
/*  662:     */       {
/*  663: 711 */         Sequence bag = new Sequence("bag");
/*  664: 712 */         Relation roleCoupler = new Relation("roles", this.firstLinkSubject, bag);
/*  665: 713 */         String preposition = this.firstLinkObject.getType();
/*  666: 714 */         String role = preposition;
/*  667:     */         
/*  668:     */ 
/*  669:     */ 
/*  670:     */ 
/*  671:     */ 
/*  672:     */ 
/*  673:     */ 
/*  674:     */ 
/*  675:     */ 
/*  676:     */ 
/*  677:     */ 
/*  678:     */ 
/*  679:     */ 
/*  680: 728 */         Function slot = new Function(role, this.secondLinkObject);
/*  681: 729 */         bag.addElement(slot);
/*  682: 730 */         replace(this.firstLink, new Relation("link", RuleSet.this.root, roleCoupler));
/*  683: 731 */         remove(this.secondLink);
/*  684: 732 */         succeeded();
/*  685:     */       }
/*  686:     */     }
/*  687:     */   }
/*  688:     */   
/*  689:     */   class MergeComplementaryRoles
/*  690:     */     extends BasicRule2
/*  691:     */   {
/*  692:     */     MergeComplementaryRoles() {}
/*  693:     */     
/*  694:     */     public void run()
/*  695:     */     {
/*  696: 742 */       super.run();
/*  697: 743 */       if ((this.firstLink != this.secondLink) && 
/*  698: 744 */         (this.firstLinkSubject == RuleSet.this.root) && 
/*  699: 745 */         (this.secondLinkSubject == RuleSet.this.root) && 
/*  700: 746 */         (this.firstLinkObject.isAPrimed("roles")) && (this.secondLinkObject.isAPrimed("roles")) && 
/*  701: 747 */         (this.firstLinkObject.relationP()) && (this.secondLinkObject.relationP()) && 
/*  702: 748 */         (this.firstLinkObject.getSubject() == this.secondLinkObject.getSubject()))
/*  703:     */       {
/*  704: 749 */         RuleSet.this.mergeBags(this.firstLinkObject.getObject(), this.secondLinkObject.getObject());
/*  705: 750 */         remove(this.secondLink);
/*  706: 751 */         succeeded();
/*  707:     */       }
/*  708:     */     }
/*  709:     */   }
/*  710:     */   
/*  711:     */   private void mergeBags(Entity object, Entity object2)
/*  712:     */   {
/*  713: 762 */     if ((!object.sequenceP()) || (!object2.sequenceP())) {
/*  714: 763 */       return;
/*  715:     */     }
/*  716: 765 */     Sequence s1 = (Sequence)object;
/*  717: 766 */     Sequence s2 = (Sequence)object2;
/*  718: 767 */     for (Iterator i = s2.getElements().iterator(); i.hasNext();) {
/*  719: 768 */       s1.addElement((Entity)i.next());
/*  720:     */     }
/*  721:     */   }
/*  722:     */   
/*  723:     */   class Force
/*  724:     */     extends BasicRule3
/*  725:     */   {
/*  726:     */     Force() {}
/*  727:     */     
/*  728:     */     public void run()
/*  729:     */     {
/*  730: 777 */       super.run();
/*  731: 779 */       if ((this.firstLinkSubject.isAPrimed(RuleSet.requireWords)) && (this.firstLinkObject.isAPrimed("entity"))) {
/*  732: 781 */         if ((this.thirdLinkSubject.isAPrimed("action")) && (this.thirdLinkObject.isAPrimed("to"))) {
/*  733: 783 */           if ((this.firstLinkSubject == this.secondLinkSubject) && (this.thirdLinkSubject.relationP()) && (this.secondLinkObject == this.thirdLinkSubject.getSubject()))
/*  734:     */           {
/*  735: 785 */             Relation relation = new Relation("force", this.firstLinkObject, this.thirdLinkSubject);
/*  736: 786 */             transferTypes(this.firstLinkSubject, relation);
/*  737: 787 */             replace(this.firstLink, new Relation("link", RuleSet.this.root, relation));
/*  738: 788 */             remove(this.secondLink);
/*  739: 789 */             remove(this.thirdLink);
/*  740: 790 */             succeeded();
/*  741:     */           }
/*  742:     */         }
/*  743:     */       }
/*  744:     */     }
/*  745:     */   }
/*  746:     */   
/*  747:     */   class ProcessDoQuestion
/*  748:     */     extends BasicRule
/*  749:     */   {
/*  750: 842 */     ArrayList words = new ArrayList();
/*  751:     */     
/*  752:     */     public ProcessDoQuestion()
/*  753:     */     {
/*  754: 845 */       this.words.add("do");
/*  755: 846 */       this.words.add("have");
/*  756:     */     }
/*  757:     */     
/*  758:     */     public void run()
/*  759:     */     {
/*  760: 850 */       super.run();
/*  761: 851 */       if ((this.firstLinkSubject.isAPrimed("action")) && (this.firstLinkObject.isAPrimed(this.words)))
/*  762:     */       {
/*  763: 852 */         Function question = new Function("question", this.firstLinkSubject);
/*  764: 853 */         question.addType("do");
/*  765: 854 */         replace(this.firstLink, new Relation("link", RuleSet.this.root, question));
/*  766: 855 */         succeeded();
/*  767:     */       }
/*  768: 857 */       else if ((this.firstLinkObject.isAPrimed("action")) && (this.firstLinkSubject.isAPrimed(this.words)))
/*  769:     */       {
/*  770: 858 */         Function question = new Function("question", this.firstLinkObject);
/*  771: 859 */         question.addType("do");
/*  772: 860 */         replace(this.firstLink, new Relation("link", RuleSet.this.root, question));
/*  773: 861 */         succeeded();
/*  774:     */       }
/*  775:     */     }
/*  776:     */   }
/*  777:     */   
/*  778:     */   class ProcessQuestion
/*  779:     */     extends BasicRule
/*  780:     */   {
/*  781: 871 */     ArrayList subjectWords = new ArrayList();
/*  782: 873 */     ArrayList objectWords = new ArrayList();
/*  783:     */     
/*  784:     */     public ProcessQuestion()
/*  785:     */     {
/*  786: 876 */       this.objectWords.add("do");
/*  787: 877 */       this.objectWords.add("have");
/*  788: 878 */       this.subjectWords.add("because");
/*  789: 879 */       this.subjectWords.addAll(RuleSet.timeWords);
/*  790:     */     }
/*  791:     */     
/*  792:     */     public void run()
/*  793:     */     {
/*  794: 883 */       super.run();
/*  795: 884 */       if ((this.firstLinkSubject.isAPrimed(this.subjectWords)) && (this.firstLinkObject.isAPrimed(this.objectWords)))
/*  796:     */       {
/*  797: 885 */         Function question = new Function("question", this.firstLinkSubject);
/*  798: 886 */         replace(this.firstLink, new Relation("link", RuleSet.this.root, question));
/*  799: 887 */         succeeded();
/*  800:     */       }
/*  801:     */     }
/*  802:     */   }
/*  803:     */   
/*  804:     */   class ProcessImagine
/*  805:     */     extends BasicRule2
/*  806:     */   {
/*  807: 894 */     ArrayList localWords = new ArrayList();
/*  808:     */     
/*  809:     */     public ProcessImagine()
/*  810:     */     {
/*  811: 897 */       this.localWords.add("action");
/*  812: 898 */       this.localWords.add("event");
/*  813: 899 */       this.localWords.add("state");
/*  814: 900 */       this.localWords.addAll(RuleSet.timeWords);
/*  815:     */     }
/*  816:     */     
/*  817:     */     public void run()
/*  818:     */     {
/*  819: 904 */       super.run();
/*  820: 905 */       if ((this.firstLinkSubject.isAPrimed("imagine")) && 
/*  821: 906 */         (this.secondLinkSubject.isA("root")))
/*  822:     */       {
/*  823: 907 */         Function command = new Function("imagine", this.secondLinkObject);
/*  824: 908 */         replace(this.firstLink, new Relation("link", RuleSet.this.root, command));
/*  825: 909 */         succeeded();
/*  826:     */       }
/*  827: 912 */       if ((this.secondLinkSubject.isAPrimed("imagine")) && 
/*  828: 913 */         (this.firstLinkSubject.isA("root")))
/*  829:     */       {
/*  830: 914 */         Function command = new Function("imagine", this.firstLinkObject);
/*  831: 915 */         replace(this.secondLink, new Relation("link", RuleSet.this.root, command));
/*  832: 916 */         succeeded();
/*  833:     */       }
/*  834:     */     }
/*  835:     */   }
/*  836:     */   
/*  837:     */   class ProcessDescribe
/*  838:     */     extends BasicRule
/*  839:     */   {
/*  840: 927 */     ArrayList localWords = new ArrayList();
/*  841:     */     
/*  842:     */     public ProcessDescribe()
/*  843:     */     {
/*  844: 930 */       this.localWords.add("action");
/*  845: 931 */       this.localWords.add("event");
/*  846: 932 */       this.localWords.add("state");
/*  847: 933 */       this.localWords.add(RuleSet.timeWords);
/*  848:     */     }
/*  849:     */     
/*  850:     */     public void run()
/*  851:     */     {
/*  852: 937 */       super.run();
/*  853: 938 */       if (this.firstLinkSubject.isAPrimed("describe"))
/*  854:     */       {
/*  855: 939 */         Function command = new Function("describe", this.firstLinkObject);
/*  856: 940 */         replace(this.firstLink, new Relation("link", RuleSet.this.root, command));
/*  857: 941 */         succeeded();
/*  858:     */       }
/*  859:     */     }
/*  860:     */   }
/*  861:     */   
/*  862:     */   class ProcessWhat
/*  863:     */     extends BasicRule2
/*  864:     */   {
/*  865:     */     ProcessWhat() {}
/*  866:     */     
/*  867:     */     public void run()
/*  868:     */     {
/*  869: 953 */       super.run();
/*  870: 954 */       if ((this.firstLinkObject.isAPrimed("what")) && (this.secondLinkObject.isAPrimed("is")))
/*  871:     */       {
/*  872: 955 */         Entity concept = this.firstLinkSubject;
/*  873: 956 */         Function question = new Function("question", concept);
/*  874: 957 */         question.addType("what");
/*  875: 958 */         replace(this.firstLink, new Relation("link", RuleSet.this.root, question));
/*  876: 959 */         remove(this.secondLink);
/*  877: 960 */         succeeded();
/*  878:     */       }
/*  879: 962 */       else if ((this.firstLinkSubject.isAPrimed("is")) && (this.firstLinkObject.isAPrimed("what")) && (this.secondLinkSubject.isAPrimed("is")) && 
/*  880: 963 */         (this.secondLinkObject.isAPrimed("entity")))
/*  881:     */       {
/*  882: 964 */         Entity concept = this.secondLink.getObject();
/*  883: 965 */         Function question = new Function("question", concept);
/*  884: 966 */         question.addType("what");
/*  885: 967 */         replace(this.firstLink, new Relation("link", RuleSet.this.root, question));
/*  886: 968 */         remove(this.secondLink);
/*  887: 969 */         succeeded();
/*  888:     */       }
/*  889:     */     }
/*  890:     */   }
/*  891:     */   
/*  892:     */   class ProcessWhy
/*  893:     */     extends BasicRule2
/*  894:     */   {
/*  895: 978 */     ArrayList words = new ArrayList();
/*  896:     */     
/*  897:     */     public ProcessWhy()
/*  898:     */     {
/*  899: 981 */       this.words.add("question");
/*  900: 982 */       this.words.add("do");
/*  901: 983 */       this.words.add("have");
/*  902:     */     }
/*  903:     */     
/*  904:     */     public void run()
/*  905:     */     {
/*  906: 987 */       super.run();
/*  907: 988 */       if ((this.firstLinkSubject.functionP()) && (this.firstLinkSubject.isAPrimed(this.words)) && (this.firstLinkObject.isAPrimed("why")))
/*  908:     */       {
/*  909: 989 */         Entity event = ((Function)this.firstLinkSubject).getSubject();
/*  910: 990 */         Function question = new Function("question", event);
/*  911: 991 */         question.addType("why");
/*  912: 992 */         remove(this.firstLink);
/*  913: 993 */         replace(this.firstLinkSubject, question);
/*  914: 994 */         succeeded();
/*  915:     */       }
/*  916:     */     }
/*  917:     */   }
/*  918:     */   
/*  919:     */   class ProcessTransferX
/*  920:     */     extends BasicRule2
/*  921:     */   {
/*  922:     */     ProcessTransferX() {}
/*  923:     */     
/*  924:     */     public void run()
/*  925:     */     {
/*  926:1004 */       super.run();
/*  927:1005 */       if ((this.firstLink.isAPrimed("nominal-subject")) && (this.firstLinkSubject.isAPrimed(RuleSet.transferWords)) && (this.firstLinkObject.isAPrimed("entity")) && 
/*  928:1006 */         (this.secondLink.isAPrimed("direct-object")) && (this.secondLinkSubject.isAPrimed(RuleSet.transferWords)) && 
/*  929:1007 */         (this.secondLinkObject.isAPrimed("entity")))
/*  930:     */       {
/*  931:1008 */         Sequence path = JFactory.createPath();
/*  932:1009 */         Function at = new Function("at", getFirstLink().getObject());
/*  933:1010 */         Function pathFunction = new Function("from", at);
/*  934:1011 */         path.addElement(pathFunction);
/*  935:1012 */         Relation go = new Relation("action", getSecondLink().getObject(), path);
/*  936:1013 */         addTypeAfterReference("action", "move", go);
/*  937:1014 */         addTypeAfterReference("action", "trajectory", go);
/*  938:1015 */         Relation transfer = new Relation("action", this.firstLinkObject, go);
/*  939:1016 */         transferTypes(this.firstLinkSubject, transfer);
/*  940:1017 */         addTypeAfterReference("action", "transfer", transfer);
/*  941:1018 */         replace(getFirstLink(), new Relation("link", RuleSet.this.root, transfer));
/*  942:1019 */         replace(getFirstLink().getSubject(), go);
/*  943:1020 */         remove(this.secondLink);
/*  944:1021 */         succeeded();
/*  945:     */       }
/*  946:     */     }
/*  947:     */   }
/*  948:     */   
/*  949:     */   class ProcessTransfer
/*  950:     */     extends BasicRule2
/*  951:     */   {
/*  952:     */     ProcessTransfer() {}
/*  953:     */     
/*  954:     */     public void run()
/*  955:     */     {
/*  956:1029 */       super.run();
/*  957:1030 */       if ((this.firstLink.isAPrimed("nominal-subject")) && (this.firstLinkSubject.isAPrimed(RuleSet.transferWords)) && (this.firstLinkObject.isAPrimed("entity")) && 
/*  958:1031 */         (this.secondLink.isAPrimed("direct-object")) && (this.secondLinkSubject.isAPrimed(RuleSet.transferWords)) && 
/*  959:1032 */         (this.secondLinkObject.isAPrimed("entity")))
/*  960:     */       {
/*  961:1033 */         Sequence path = JFactory.createPath();
/*  962:1034 */         Function at = new Function("at", this.firstLinkObject);
/*  963:1035 */         Function pathFunction = new Function("from", at);
/*  964:1036 */         path.addElement(pathFunction);
/*  965:1037 */         Relation go = new Relation("action", this.secondLinkObject, path);
/*  966:1038 */         addTypeAfterReference("action", "move", go);
/*  967:1039 */         addTypeAfterReference("action", "trajectory", go);
/*  968:     */         
/*  969:1041 */         Relation transfer = new Relation("action", this.firstLinkObject, go);
/*  970:     */         
/*  971:1043 */         transferTypes(this.firstLinkSubject, transfer);
/*  972:1044 */         addTypeAfterReference("action", "transfer", transfer);
/*  973:1045 */         replace(this.firstLink, new Relation("link", RuleSet.this.root, transfer));
/*  974:1046 */         remove(this.secondLink);
/*  975:1047 */         replace(this.firstLinkSubject, go);
/*  976:1048 */         succeeded();
/*  977:     */       }
/*  978:     */     }
/*  979:     */   }
/*  980:     */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     translator.RuleSet
 * JD-Core Version:    0.7.0.1
 */