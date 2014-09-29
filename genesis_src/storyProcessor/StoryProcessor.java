/*    1:     */ package storyProcessor;
/*    2:     */ 
/*    3:     */ import Signals.BetterSignal;
/*    4:     */ import bridge.reps.entities.Entity;
/*    5:     */ import bridge.reps.entities.Function;
/*    6:     */ import bridge.reps.entities.Relation;
/*    7:     */ import bridge.reps.entities.Sequence;
/*    8:     */ import bridge.reps.entities.Thread;
/*    9:     */ import connections.AbstractWiredBox;
/*   10:     */ import connections.Connections;
/*   11:     */ import connections.Ports;
/*   12:     */ import gui.StatisticsBar;
/*   13:     */ import java.io.PrintStream;
/*   14:     */ import java.util.ArrayList;
/*   15:     */ import java.util.Collection;
/*   16:     */ import java.util.HashMap;
/*   17:     */ import java.util.HashSet;
/*   18:     */ import java.util.Iterator;
/*   19:     */ import java.util.LinkedHashMap;
/*   20:     */ import java.util.List;
/*   21:     */ import java.util.Set;
/*   22:     */ import java.util.Vector;
/*   23:     */ import matchers.RuleMatcher;
/*   24:     */ import matchers.Substitutor;
/*   25:     */ import minilisp.LList;
/*   26:     */ import models.MentalModel;
/*   27:     */ import parameters.Switch;
/*   28:     */ import persistence.JCheckBoxWithMemory;
/*   29:     */ import start.Generator;
/*   30:     */ import start.Start;
/*   31:     */ import text.Punctuator;
/*   32:     */ import tools.Constructors;
/*   33:     */ import tools.Getters;
/*   34:     */ import tools.Predicates;
/*   35:     */ import utils.Mark;
/*   36:     */ import utils.PairOfEntities;
/*   37:     */ import utils.Timer;
/*   38:     */ 
/*   39:     */ public class StoryProcessor
/*   40:     */   extends AbstractWiredBox
/*   41:     */ {
/*   42:     */   public static final String PLOT_PLAY_BY_PLAY_PORT = "NEW_PLOT_ELEMENT_PORT";
/*   43:  32 */   private static boolean debug = false;
/*   44:  36 */   private static boolean debugRebuild = false;
/*   45:  38 */   private static boolean debug2 = false;
/*   46:  40 */   private static boolean debugInput = false;
/*   47:  42 */   private static boolean debugPredictionsAndExplanations = false;
/*   48:  44 */   private static boolean debugAddElement = false;
/*   49:  46 */   private static boolean debugOutput = false;
/*   50:  48 */   private static boolean debugForwardChaining = false;
/*   51:  50 */   private static boolean debugBackwardChaining = false;
/*   52:     */   public static final String TAB = "tab";
/*   53:     */   public static final String STORY_NAME = "story name";
/*   54:     */   public static final String INJECT_RULE = "port for rule injection";
/*   55:     */   public static final String INJECT_CONCEPT = "port for concept injection";
/*   56:     */   public static final String INJECT_ELEMENT = "port for story element injection";
/*   57:     */   public static final String TEST_ELEMENT = "port for testing to see if element supported by may rule";
/*   58:     */   public static final String INJECT_ELEMENT_INTO_TRAIT_MODEL = "port for story element injection into trait-specific mental model";
/*   59:     */   public static final String STOP = "Stop";
/*   60:     */   public static final String STARTING = "Starting";
/*   61:     */   public static final String CLEAR = "Clear";
/*   62:     */   public static final String INFERENCE = "Inference";
/*   63:  76 */   private boolean storyInProgress = false;
/*   64:  78 */   private boolean milestoneEncountered = false;
/*   65:     */   public static final String INFERENCES = "inferences";
/*   66:     */   public static final String FINAL_INFERENCES = "final-inferences";
/*   67:     */   public static final String FINAL_INPUTS = "final-inputs";
/*   68:     */   public static final String CONTROL = "control";
/*   69:     */   public static final String DESCRIBE = "describe";
/*   70:     */   public static final String REFLECTIONS_VIEWER_PORT = "reflections port";
/*   71:     */   public static final String REFLECTION_ONSET_VIEWER_PORT = "reflection onset port";
/*   72:     */   public static final String INDICATIONS_PORT = "indications port";
/*   73:     */   public static final String RULE_PORT = "rule port";
/*   74:     */   public static final String COMPLETE_STORY_EVENTS_PORT = "complete story events port";
/*   75:     */   public static final String COMPLETE_STORY_ANALYSIS_PORT = "complete story analysis port";
/*   76:     */   public static final String NEW_ELEMENT_PORT = "new  element port";
/*   77:     */   public static final String GAP_FILLER_PORT = "gap filler port";
/*   78:     */   public static final String QUIESCENCE_PORT = "quiescence port";
/*   79:     */   public static final String STORY = "story";
/*   80:     */   public static final String CLEAR_REFLECTIONS = "clear reflections";
/*   81:     */   public static final String ONSET = "onset";
/*   82:     */   public static final String TARGET_STORY = "target-story";
/*   83:     */   public static final String STORY_PROCESSOR = "processor";
/*   84:     */   public static final String STORY_WORKBENCH_INPUT = "story workbench input";
/*   85:     */   public static final String FROM_FORWARD_CHAINER = "from forward chainer port";
/*   86:     */   public static final String TO_FORWARD_CHAINER = "to forward chainer port";
/*   87:     */   public static final String FROM_BACKWARD_CHAINER = "from chainer chainer port";
/*   88:     */   public static final String TO_BACKWARD_CHAINER = "to backward chainer port";
/*   89:     */   public static final String TO_ONSET_DETECTOR = "to onset detector port";
/*   90:     */   public static final String TO_COMPLETION_DETECTOR = "to completion detector port";
/*   91:     */   public static final String INCREMENT = "incremental output port";
/*   92:     */   public static final String USED_RULES_PORT = "used rules port";
/*   93:     */   public static final String PREDICTION_RULES = "prediction rules";
/*   94:     */   public static final String RECORD_PORT = "record port";
/*   95:     */   public static final String COMMENTARY_PORT = "commentary port";
/*   96:     */   public static final String CLUSTER_STORY_PORT = "cluster story port";
/*   97:     */   public static final String TO_STATISTICS_BAR = "statistics bar output port";
/*   98:     */   public static final String INCOMING_INSTANTIATIONS = "instantiated reflections from concept expert";
/*   99:     */   public static final String INSTANTIATED_REFLECTIONS = "instantiated reflections";
/*  100:     */   public static final String INCOMING_REFLECTION_ANALYSIS = "reflection analysis from concept expert";
/*  101:     */   public static final String REFLECTION_ANALYSIS = "reflection analysis";
/*  102:     */   public static final String RESET_CONCEPTS_PORT = "reset concepts port";
/*  103:     */   public static final String PERSONALITY_TRAIT_PORT = "personality port";
/*  104:     */   public static final String RESET_RESULTS_VIEWER = "reset story";
/*  105:     */   public static final String EVENT_ADDED = "event added";
/*  106:     */   public static final String EVENT_ADDED_WITH_TRAIT = "event added with trait";
/*  107:     */   public static final String PROCESS_TRAIT_PREDICTIONS = "process trait predictions";
/*  108:     */   public static final String PROCESS_ALL_EXPLANATIONS = "process trait explanations";
/*  109:     */   public static final String FIND_TRAIT_CONCEPTS = "find trait-specific concepts";
/*  110:     */   public static final String DELIVER_TRAIT_CONCEPTS = "deliver trait-specific concepts";
/*  111:     */   public static final String DELIVER_TRAIT_CHARACTERIZATION = "deliver trait characteization";
/*  112: 182 */   private Sequence story = new Sequence("story");
/*  113: 184 */   private Sequence inferences = new Sequence();
/*  114: 186 */   private Sequence explicitElements = new Sequence();
/*  115: 188 */   private Sequence concepts = new Sequence("reflection");
/*  116: 190 */   private Sequence rules = new Sequence();
/*  117: 192 */   private Sequence instantiatedConcepts = new Sequence();
/*  118:     */   private Sequence instantiatedConceptPatterns;
/*  119:     */   private ReflectionAnalysis reflectionAnalysis;
/*  120: 200 */   private Sequence conceptOnsets = new Sequence("onset");
/*  121: 202 */   private HashMap<String, ArrayList<Entity>> predictionRuleMap = new HashMap();
/*  122: 204 */   private HashMap<String, ArrayList<Entity>> explanationRuleMap = new HashMap();
/*  123: 206 */   private HashMap<String, ArrayList<Entity>> censorRuleMap = new HashMap();
/*  124: 208 */   private HashMap<String, ArrayList<Entity>> onsetRuleMap = new HashMap();
/*  125: 210 */   private HashMap<String, ArrayList<Entity>> completionRuleMap = new HashMap();
/*  126: 212 */   private HashMap<String, ArrayList<Sequence>> conceptMap = new HashMap();
/*  127:     */   private ArrayList<HashMap<String, Entity>> sceneMapList;
/*  128:     */   private HashMap<String, Entity> globalCache;
/*  129:     */   private HashMap<String, Entity> mentalModelCache;
/*  130:     */   private HashMap<String, Entity> wantCache;
/*  131:     */   private HashSet<String> alreadyPredicted;
/*  132: 238 */   long storyTimer = System.currentTimeMillis();
/*  133: 240 */   private String currentPersona = "default";
/*  134: 244 */   private boolean asleep = false;
/*  135:     */   private String name;
/*  136: 248 */   private boolean playByPlayInput = false;
/*  137: 250 */   private boolean precedentInput = false;
/*  138: 252 */   int sizeAtPreviousQuiescence = 0;
/*  139:     */   ForwardChainer forwardChainer;
/*  140:     */   BackwardChainer backwardChainer;
/*  141:     */   ConceptExpert conceptExpert;
/*  142:     */   private MentalModel mentalModel;
/*  143: 264 */   public static String START_STORY_INFO_PORT = "start story info port";
/*  144: 266 */   public static String LEARNED_RULE_PORT = "learned rule port";
/*  145: 268 */   public static String NEW_RULE_MESSENGER_PORT = "new rule messenger port";
/*  146: 270 */   public static String EXPLICIT_STORY = "explicit story";
/*  147: 272 */   private boolean newRuleComingIn = false;
/*  148: 274 */   private Entity learnedRule = new Entity();
/*  149:     */   Entity reader;
/*  150:     */   private boolean inert;
/*  151:     */   
/*  152:     */   public StoryProcessor()
/*  153:     */   {
/*  154: 283 */     this("Unnamed story processor");
/*  155:     */   }
/*  156:     */   
/*  157:     */   public StoryProcessor(String name)
/*  158:     */   {
/*  159: 287 */     setName(name);
/*  160:     */     
/*  161:     */ 
/*  162: 290 */     Connections.getPorts(this).addSignalProcessor("input", "processElement");
/*  163: 291 */     Connections.getPorts(this).addSignalProcessor("from forward chainer port", "processForwardRule");
/*  164: 292 */     Connections.getPorts(this).addSignalProcessor("from chainer chainer port", "processBackwardRule");
/*  165:     */     
/*  166: 294 */     Connections.getPorts(this).addSignalProcessor(Start.STAGE_DIRECTION_PORT, "processStageDirections");
/*  167:     */     
/*  168:     */ 
/*  169: 297 */     Connections.wire("to forward chainer port", this, getForwardChainer());
/*  170: 298 */     Connections.wire("to backward chainer port", this, getBackwardChainer());
/*  171:     */     
/*  172:     */ 
/*  173: 301 */     Connections.wire(getForwardChainer(), "from forward chainer port", this);
/*  174: 302 */     Connections.wire(getBackwardChainer(), "from chainer chainer port", this);
/*  175: 303 */     Connections.wire("used rules port", getForwardChainer(), "used rules port", this);
/*  176: 304 */     Connections.wire("used rules port", getBackwardChainer(), "used rules port", this);
/*  177:     */     
/*  178: 306 */     Connections.wire("Instantiated reflections port", getConceptExpert(), "Instantiated reflections port", this);
/*  179: 307 */     Connections.getPorts(this).addSignalProcessor("Instantiated reflections port", "processInstantiatedConcepts");
/*  180:     */     
/*  181:     */ 
/*  182: 310 */     Connections.getPorts(this).addSignalProcessor("used rules port", "transmitUsedRules");
/*  183: 311 */     Connections.getPorts(this).addSignalProcessor("reflections port", "insertReflectionSet");
/*  184: 312 */     Connections.getPorts(this).addSignalProcessor("rule port", "insertRuleSet");
/*  185: 313 */     Connections.getPorts(this).addSignalProcessor("instantiated reflections from concept expert", "setInstantiations");
/*  186: 314 */     Connections.getPorts(this).addSignalProcessor("reflection analysis from concept expert", "setReflectionAnalysis");
/*  187:     */     
/*  188:     */ 
/*  189: 317 */     Connections.getPorts(this).addSignalProcessor(LEARNED_RULE_PORT, "recordRule");
/*  190: 318 */     Connections.getPorts(this).addSignalProcessor(NEW_RULE_MESSENGER_PORT, "processMessenger");
/*  191: 319 */     Connections.getPorts(this).addSignalProcessor("port for story element injection", "processElementInjection");
/*  192: 320 */     Connections.getPorts(this).addSignalProcessor("port for testing to see if element supported by may rule", "testElementInjection");
/*  193:     */     
/*  194:     */ 
/*  195: 323 */     Connections.getPorts(this).addSignalProcessor("port for rule injection", "processRuleInjection");
/*  196: 324 */     Connections.getPorts(this).addSignalProcessor("port for concept injection", "processConceptInjection");
/*  197:     */     
/*  198: 326 */     Connections.getPorts(this).addSignalProcessor("personality port", "processTraitAddition");
/*  199:     */   }
/*  200:     */   
/*  201:     */   public StoryProcessor(String name, MentalModel mentalModel)
/*  202:     */   {
/*  203: 333 */     this(name);
/*  204: 334 */     setMentalModel(mentalModel);
/*  205:     */   }
/*  206:     */   
/*  207:     */   public ForwardChainer getForwardChainer()
/*  208:     */   {
/*  209: 338 */     if (this.forwardChainer == null)
/*  210:     */     {
/*  211: 339 */       this.forwardChainer = new ForwardChainer();
/*  212: 340 */       this.forwardChainer.setName(getName() + " forward chainer");
/*  213:     */     }
/*  214: 342 */     return this.forwardChainer;
/*  215:     */   }
/*  216:     */   
/*  217:     */   public BackwardChainer getBackwardChainer()
/*  218:     */   {
/*  219: 346 */     if (this.backwardChainer == null)
/*  220:     */     {
/*  221: 347 */       this.backwardChainer = new BackwardChainer();
/*  222: 348 */       this.backwardChainer.setName(getName() + " backward chainer");
/*  223:     */     }
/*  224: 350 */     return this.backwardChainer;
/*  225:     */   }
/*  226:     */   
/*  227:     */   public ConceptExpert getConceptExpert()
/*  228:     */   {
/*  229: 354 */     if (this.conceptExpert == null) {
/*  230: 355 */       this.conceptExpert = new ConceptExpert();
/*  231:     */     }
/*  232: 357 */     return this.conceptExpert;
/*  233:     */   }
/*  234:     */   
/*  235:     */   public void processMessenger(Object o)
/*  236:     */   {
/*  237: 363 */     this.newRuleComingIn = true;
/*  238:     */   }
/*  239:     */   
/*  240:     */   public void processStageDirections(Object o)
/*  241:     */   {
/*  242: 367 */     if (!(o instanceof String)) {
/*  243: 368 */       return;
/*  244:     */     }
/*  245: 370 */     String s = (String)o;
/*  246: 371 */     if (s.equalsIgnoreCase("Neither perspective")) {
/*  247: 373 */       setAwake(false);
/*  248: 375 */     } else if ((s.equalsIgnoreCase(getName())) || (s.equalsIgnoreCase("Both perspectives"))) {
/*  249: 377 */       setAwake(true);
/*  250:     */     }
/*  251: 380 */     Mark.say(new Object[] {Boolean.valueOf(debugInput), "Control received", o });
/*  252: 381 */     if (isAsleep()) {
/*  253: 382 */       return;
/*  254:     */     }
/*  255: 384 */     if (s == "theEnd") {
/*  256: 385 */       stopStory();
/*  257: 387 */     } else if (s == "reset") {
/*  258: 388 */       clearAllMemories();
/*  259: 395 */     } else if (s == "radiateRules") {
/*  260: 396 */       radiate();
/*  261: 399 */     } else if (s == "insertBias") {
/*  262: 400 */       insert();
/*  263:     */     }
/*  264:     */   }
/*  265:     */   
/*  266:     */   private void radiate() {}
/*  267:     */   
/*  268:     */   private void insert() {}
/*  269:     */   
/*  270:     */   public void insertRuleSet(Object x)
/*  271:     */   {
/*  272: 426 */     if ((x instanceof Sequence))
/*  273:     */     {
/*  274: 427 */       Sequence s = (Sequence)x;
/*  275: 428 */       this.currentPersona = s.getType();
/*  276: 429 */       for (Entity t : s.getElements())
/*  277:     */       {
/*  278: 430 */         Mark.say(new Object[] {"RULE MAYBE: ", t.asString() });
/*  279: 431 */         recordRule(t);
/*  280:     */       }
/*  281:     */     }
/*  282:     */   }
/*  283:     */   
/*  284:     */   public void processForwardRule(Object x)
/*  285:     */   {
/*  286: 448 */     BetterSignal signal = BetterSignal.isSignal(x);
/*  287: 449 */     if (signal == null) {
/*  288: 450 */       return;
/*  289:     */     }
/*  290: 455 */     Entity instantiatedRule = (Entity)signal.get(0, Entity.class);
/*  291: 456 */     Entity rule = (Entity)signal.get(1, Entity.class);
/*  292:     */     
/*  293: 458 */     processMentalModelConnections(rule, instantiatedRule);
/*  294:     */     
/*  295:     */ 
/*  296:     */ 
/*  297: 462 */     addElement(instantiatedRule, this.story);
/*  298:     */   }
/*  299:     */   
/*  300:     */   public void processBackwardRule(Object x)
/*  301:     */   {
/*  302: 467 */     BetterSignal signal = BetterSignal.isSignal(x);
/*  303: 468 */     if (signal == null) {
/*  304: 469 */       return;
/*  305:     */     }
/*  306: 472 */     Entity instantiatedRule = (Entity)signal.get(0, Entity.class);
/*  307: 474 */     if (signal.size() >= 2)
/*  308:     */     {
/*  309: 475 */       Entity rule = (Entity)signal.get(1, Entity.class);
/*  310: 476 */       processMentalModelConnections(rule, instantiatedRule);
/*  311:     */     }
/*  312: 478 */     addElement(instantiatedRule, this.story);
/*  313:     */   }
/*  314:     */   
/*  315:     */   public void processMentalModelConnections(Entity rule, Entity instantiatedRule)
/*  316:     */   {
/*  317: 486 */     instantiatedRule.addProperty("mental-model-host", rule.getProperty("mental-model-host"));
/*  318:     */   }
/*  319:     */   
/*  320:     */   public void transmitUsedRules(Object x)
/*  321:     */   {
/*  322: 490 */     BetterSignal signal = BetterSignal.isSignal(x);
/*  323: 491 */     if (signal == null) {
/*  324: 492 */       return;
/*  325:     */     }
/*  326: 494 */     Connections.getPorts(this).transmit("used rules port", signal.get(0, Entity.class));
/*  327:     */   }
/*  328:     */   
/*  329:     */   public boolean addElement(Entity element, Sequence story)
/*  330:     */   {
/*  331: 502 */     boolean debug = false;
/*  332: 504 */     if (element == null) {
/*  333: 505 */       return false;
/*  334:     */     }
/*  335: 508 */     Connections.getPorts(this).transmit("statistics bar output port", new BetterSignal(new Object[] { StatisticsBar.STORY_TIMER, 
/*  336: 509 */       Timer.time("storyTimer", this.storyTimer) }));
/*  337: 516 */     if (milestoneP(element)) {
/*  338: 517 */       addSceneMap();
/*  339:     */     }
/*  340: 521 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Received", element.asStringWithIndexes() });
/*  341:     */     
/*  342: 523 */     Entity mentalModelElement = null;
/*  343: 527 */     if (isInert()) {
/*  344: 528 */       element = reassembleAndDereference(element);
/*  345: 530 */     } else if ((!element.isA("prediction")) && (!element.isA("explanation")) && (!element.isA("abduction"))) {
/*  346: 531 */       element = reassembleAndDereference(element);
/*  347:     */     }
/*  348: 535 */     if (isInCurrentScene(element)) {
/*  349: 537 */       return false;
/*  350:     */     }
/*  351: 541 */     if (story.isAPrimed("reflection"))
/*  352:     */     {
/*  353: 542 */       augmentCurrentSceneIfNotInCurrentScene(element);
/*  354: 543 */       return false;
/*  355:     */     }
/*  356: 548 */     if ((element.relationP("cause")) && (!isInert()))
/*  357:     */     {
/*  358: 552 */       MentalModel host = MentalModel.getMentalModelHosts(element);
/*  359: 554 */       if (host != null) {
/*  360: 555 */         host.getStoryProcessor().processElementWithInertInjection(element);
/*  361:     */       }
/*  362: 558 */       element = rebuildCauseRelation(element);
/*  363: 562 */       if (!censored(element.getObject()))
/*  364:     */       {
/*  365: 564 */         addInference(element);
/*  366:     */         
/*  367: 566 */         augmentCurrentSceneIfNotInCurrentScene(element);
/*  368: 567 */         if (story.isAPrimed("reflection")) {
/*  369: 568 */           return false;
/*  370:     */         }
/*  371: 571 */         for (Entity antecedent : element.getSubject().getElements()) {
/*  372: 572 */           if (augmentCurrentSceneIfNotInAnyScene(antecedent))
/*  373:     */           {
/*  374: 573 */             Connections.getPorts(this).transmit("new  element port", antecedent);
/*  375: 574 */             triggerRules(antecedent, story);
/*  376:     */           }
/*  377:     */         }
/*  378: 578 */         if (augmentCurrentSceneIfNotInCurrentScene(element.getObject()))
/*  379:     */         {
/*  380: 579 */           Connections.getPorts(this).transmit("new  element port", element.getObject());
/*  381: 580 */           triggerRules(element.getObject(), story);
/*  382:     */         }
/*  383: 583 */         noteInferenceCount();
/*  384:     */       }
/*  385:     */       else
/*  386:     */       {
/*  387: 587 */         return false;
/*  388:     */       }
/*  389:     */     }
/*  390: 593 */     else if (!censored(element))
/*  391:     */     {
/*  392: 596 */       element = makeNewVersion(element);
/*  393: 598 */       if (augmentCurrentSceneIfNotInCurrentScene(element))
/*  394:     */       {
/*  395: 599 */         Connections.getPorts(this).transmit("new  element port", element);
/*  396: 600 */         triggerRules(element, story);
/*  397:     */       }
/*  398:     */     }
/*  399:     */     else
/*  400:     */     {
/*  401: 605 */       return false;
/*  402:     */     }
/*  403: 614 */     noteIfElementExpressesReaderModel(element, debug);
/*  404: 616 */     if ((element.getType().equals("cause")) || (element.getType().equals("entail")) || 
/*  405: 617 */       (element.getType().equals("means")) || (!element.isA("cause")))
/*  406:     */     {
/*  407: 618 */       this.explicitElements.addElement(element);
/*  408: 619 */       Connections.getPorts(this).transmit(EXPLICIT_STORY, element);
/*  409:     */     }
/*  410: 621 */     Connections.getPorts(this).transmit("new  element port", element);
/*  411:     */     
/*  412:     */ 
/*  413:     */ 
/*  414:     */ 
/*  415:     */ 
/*  416:     */ 
/*  417: 628 */     return true;
/*  418:     */   }
/*  419:     */   
/*  420:     */   private void noteIfElementExpressesReaderModel(Entity element, boolean debug)
/*  421:     */   {
/*  422: 632 */     if ((!element.entityP()) && (element.getSubject().isA("i")) && (element.relationP("personality_trait")))
/*  423:     */     {
/*  424: 633 */       String trait = element.getObject().getType();
/*  425: 634 */       this.reader = element.getSubject();
/*  426: 635 */       Mark.say(new Object[] {"I note that the reader has personality trait", trait });
/*  427: 636 */       MentalModel mentalModel = getMentalModel().getLocalMentalModel(trait);
/*  428: 637 */       if ((mentalModel != null) && (debug))
/*  429:     */       {
/*  430: 638 */         ArrayList<Relation> predicters = mentalModel.getPredictionRules();
/*  431: 639 */         ArrayList<Relation> explainers = mentalModel.getExplanationRules();
/*  432: 640 */         Sequence concepts = mentalModel.getConceptPatterns();
/*  433:     */         
/*  434: 642 */         Mark.say(new Object[] {Boolean.valueOf(predicters != null ? 1 : false), "Predicters", Integer.valueOf(predicters.size()) });
/*  435: 643 */         Mark.say(new Object[] {Boolean.valueOf(explainers != null ? 1 : false), "Explainers", Integer.valueOf(explainers.size()) });
/*  436: 644 */         Mark.say(new Object[] {Boolean.valueOf(concepts != null ? 1 : false), "Concepts", Integer.valueOf(concepts.getElements().size()) });
/*  437:     */       }
/*  438: 647 */       else if (mentalModel == null)
/*  439:     */       {
/*  440: 648 */         Mark.err(new Object[] {"I have no mental model for", trait });
/*  441:     */       }
/*  442:     */     }
/*  443:     */   }
/*  444:     */   
/*  445:     */   private void noteInferenceCount()
/*  446:     */   {
/*  447: 654 */     Connections.getPorts(this).transmit("statistics bar output port", new BetterSignal(new Object[] { StatisticsBar.INFERENCE_COUNT, 
/*  448: 655 */       Integer.valueOf(getInferences().getElements().size()) }));
/*  449:     */   }
/*  450:     */   
/*  451:     */   private void replaceCachedElements(Entity element, HashMap<String, Entity> aMap, HashMap<String, Entity> cMap)
/*  452:     */   {
/*  453: 888 */     Sequence antecedents = new Sequence(element.getSubject().getType());
/*  454: 890 */     for (Entity t : element.getSubject().getElements())
/*  455:     */     {
/*  456:     */       String str;
/*  457: 893 */       for (Iterator localIterator2 = aMap.keySet().iterator(); localIterator2.hasNext(); str = (String)localIterator2.next()) {}
/*  458: 896 */       Entity extant = getFromCache(t, aMap);
/*  459:     */       
/*  460:     */ 
/*  461:     */ 
/*  462:     */ 
/*  463: 901 */       antecedents.addElement(extant);
/*  464:     */     }
/*  465: 903 */     element.setSubject(antecedents);
/*  466: 904 */     Entity extant = getFromCache(element.getObject(), cMap);
/*  467: 905 */     if (extant != element.getObject()) {
/*  468: 909 */       element.setObject(extant);
/*  469:     */     }
/*  470:     */   }
/*  471:     */   
/*  472:     */   private boolean testBindings(LList<PairOfEntities> bindings)
/*  473:     */   {
/*  474: 930 */     if (bindings == null) {
/*  475: 931 */       return false;
/*  476:     */     }
/*  477: 933 */     for (Iterator<PairOfEntities> i = bindings.iterator(); i.hasNext();)
/*  478:     */     {
/*  479: 934 */       PairOfEntities pair = (PairOfEntities)i.next();
/*  480: 935 */       if (pair.getDatum() != pair.getPattern()) {
/*  481: 936 */         return false;
/*  482:     */       }
/*  483:     */     }
/*  484: 939 */     return true;
/*  485:     */   }
/*  486:     */   
/*  487:     */   private boolean censored(Entity element)
/*  488:     */   {
/*  489: 943 */     if (element == null) {
/*  490: 944 */       return false;
/*  491:     */     }
/*  492: 946 */     boolean result = RuleMatcher.censor(element, this.story, getCensorRules(element, this.censorRuleMap, false));
/*  493: 947 */     return result;
/*  494:     */   }
/*  495:     */   
/*  496:     */   private synchronized void triggerRules(Entity element, Sequence story)
/*  497:     */   {
/*  498: 952 */     if (isInert()) {
/*  499: 953 */       return;
/*  500:     */     }
/*  501: 955 */     Mark.say(new Object[] {Boolean.valueOf(debugForwardChaining), "Processing story element", element.asStringWithIndexes() });
/*  502: 957 */     if ((element.relationP()) && (element.isAPrimed("time-relation")))
/*  503:     */     {
/*  504: 958 */       Relation r = (Relation)element;
/*  505: 959 */       addElement(r.getSubject(), story);
/*  506: 960 */       addElement(r.getObject(), story);
/*  507:     */     }
/*  508:     */     else
/*  509:     */     {
/*  510: 967 */       ArrayList<MentalModel> personalityModels = getPersonalityModels(element);
/*  511: 970 */       if (!isAlreadyPredicted(element))
/*  512:     */       {
/*  513: 972 */         BetterSignal signal = new BetterSignal(new Object[] { "process trait explanations", element, this });
/*  514: 973 */         Connections.getPorts(this).transmit("personality port", signal);
/*  515:     */       }
/*  516: 992 */       if (Switch.Level2PredictionRules.isSelected())
/*  517:     */       {
/*  518: 994 */         BetterSignal signal = new BetterSignal(new Object[] { element, getPredictionRules(element, this.predictionRuleMap, true), story, Integer.valueOf(0) });
/*  519:     */         
/*  520: 996 */         Connections.getPorts(this).transmit("to forward chainer port", signal);
/*  521: 998 */         if (Switch.level5UseMentalModels.isSelected())
/*  522:     */         {
/*  523: 999 */           signal = new BetterSignal(new Object[] { "process trait predictions", element, this });
/*  524:1000 */           Connections.getPorts(this).transmit("personality port", signal);
/*  525:     */         }
/*  526:     */       }
/*  527:1004 */       if (Switch.level6LookForMentalModelEvidence.isSelected()) {
/*  528:1006 */         for (MentalModel personalityModel : personalityModels)
/*  529:     */         {
/*  530:1007 */           BetterSignal signal = new BetterSignal(new Object[] { "event added with trait", element, personalityModel, this });
/*  531:1008 */           Connections.getPorts(this).transmit("personality port", signal);
/*  532:     */         }
/*  533:     */       }
/*  534:1027 */       if (Switch.level4ConceptPatterns.isSelected()) {
/*  535:1029 */         Connections.getPorts(this).transmit("to onset detector port", new BetterSignal(new Object[] { element, getPredictionRules(element, this.onsetRuleMap, false), 
/*  536:1030 */           story }));
/*  537:     */       }
/*  538:1032 */       if (Switch.level4ConceptPatterns.isSelected())
/*  539:     */       {
/*  540:1037 */         boolean debug = false;
/*  541:1039 */         if (Switch.level5UseMentalModels.isSelected())
/*  542:     */         {
/*  543:1042 */           BetterSignal signal = new BetterSignal(new Object[] { "find trait-specific concepts", element, this });
/*  544:1043 */           Connections.getPorts(this).transmit("personality port", signal);
/*  545:     */         }
/*  546:1048 */         if (Switch.findConceptsContinuously.isSelected())
/*  547:     */         {
/*  548:1049 */           Mark.say(new Object[] {Boolean.valueOf(debug), "Looking for concepts as I go" });
/*  549:1050 */           BetterSignal signal = new BetterSignal(new Object[] { this.concepts, story, getInferences() });
/*  550:1051 */           Connections.getPorts(this).transmit("to completion detector port", signal);
/*  551:     */         }
/*  552:     */       }
/*  553:     */     }
/*  554:     */   }
/*  555:     */   
/*  556:     */   public static boolean exists(Entity s, String r, Entity o)
/*  557:     */   {
/*  558:1062 */     Vector<Function> relations = s.getSubjectOf(r);
/*  559:1063 */     for (Entity relation : relations)
/*  560:     */     {
/*  561:1064 */       Entity object = relation.getObject();
/*  562:1066 */       if (object.getType().equals(o.getType())) {
/*  563:1068 */         return true;
/*  564:     */       }
/*  565:     */     }
/*  566:1072 */     return false;
/*  567:     */   }
/*  568:     */   
/*  569:     */   public ArrayList<MentalModel> getPersonalityModels(Entity element)
/*  570:     */   {
/*  571:1076 */     ArrayList<MentalModel> personalityModels = new ArrayList();
/*  572:1077 */     ArrayList<Entity> entities = fetchParticipants(element);
/*  573:     */     label190:
/*  574:1079 */     for (Iterator localIterator1 = entities.iterator(); localIterator1.hasNext(); ???.hasNext())
/*  575:     */     {
/*  576:1079 */       Entity subject = (Entity)localIterator1.next();
/*  577:1080 */       if ((!subject.isA("person")) && (!subject.isA("i"))) {
/*  578:     */         break label190;
/*  579:     */       }
/*  580:1083 */       HashSet<String> personalities = new HashSet();
/*  581:1084 */       for (Entity t : subject.getSubjectOf()) {
/*  582:1085 */         if (t.relationP("personality_trait")) {
/*  583:1086 */           personalities.add(t.getObject().getType());
/*  584:     */         }
/*  585:     */       }
/*  586:1089 */       ??? = personalities.iterator(); continue;String personality = (String)???.next();
/*  587:     */       
/*  588:1091 */       MentalModel personalityModel = getMentalModel().getLocalMentalModel(personality);
/*  589:1092 */       if ((personalityModel != null) && 
/*  590:1093 */         (!personalityModels.contains(personalityModel))) {
/*  591:1094 */         personalityModels.add(personalityModel);
/*  592:     */       }
/*  593:     */     }
/*  594:1104 */     return personalityModels;
/*  595:     */   }
/*  596:     */   
/*  597:     */   public ArrayList<Entity> fetchParticipants(Entity element)
/*  598:     */   {
/*  599:1108 */     ArrayList<Entity> entities = new ArrayList();
/*  600:1109 */     Entity subject = element.getSubject();
/*  601:1110 */     if ((subject.entityP()) && (!entities.contains(subject))) {
/*  602:1111 */       entities.add(subject);
/*  603:     */     }
/*  604:1113 */     if (element.relationP())
/*  605:     */     {
/*  606:1114 */       Entity o = Getters.getObject(element);
/*  607:1115 */       if (o != null) {
/*  608:1117 */         if ((o.entityP()) && (!entities.contains(o))) {
/*  609:1118 */           entities.add(o);
/*  610:     */         }
/*  611:     */       }
/*  612:     */     }
/*  613:1126 */     if (this.reader != null) {
/*  614:1127 */       entities.add(this.reader);
/*  615:     */     }
/*  616:1130 */     return entities;
/*  617:     */   }
/*  618:     */   
/*  619:     */   private boolean isTarget(Entity target, Sequence inferences)
/*  620:     */   {
/*  621:1134 */     for (Entity t : inferences.getElements()) {
/*  622:1135 */       if ((t.relationP()) && 
/*  623:1136 */         (target == t.getObject())) {
/*  624:1138 */         return true;
/*  625:     */       }
/*  626:     */     }
/*  627:1142 */     return false;
/*  628:     */   }
/*  629:     */   
/*  630:     */   public boolean storyStarter(Entity t)
/*  631:     */   {
/*  632:1153 */     if ((t.relationP("start")) && (t.getSubject().entityP("you")))
/*  633:     */     {
/*  634:1154 */       Entity x = extractObjectRole(t);
/*  635:1155 */       if (x == null) {
/*  636:1156 */         return false;
/*  637:     */       }
/*  638:1158 */       if (x.entityP("story")) {
/*  639:1160 */         return true;
/*  640:     */       }
/*  641:1162 */       if (x.functionP("story")) {
/*  642:1164 */         return true;
/*  643:     */       }
/*  644:1166 */       if (x.functionP("reflection")) {
/*  645:1168 */         return true;
/*  646:     */       }
/*  647:1170 */       if (x.entityP("precedent"))
/*  648:     */       {
/*  649:1171 */         Mark.say(new Object[] {"Starting video precedent" });
/*  650:1172 */         return true;
/*  651:     */       }
/*  652:1175 */       if (x.functionP("video"))
/*  653:     */       {
/*  654:1176 */         Mark.say(new Object[] {"Starting video" });
/*  655:1177 */         return true;
/*  656:     */       }
/*  657:1180 */       if (x.entityP("video"))
/*  658:     */       {
/*  659:1181 */         Mark.say(new Object[] {"Starting unnamed video" });
/*  660:1182 */         return true;
/*  661:     */       }
/*  662:     */     }
/*  663:1185 */     return false;
/*  664:     */   }
/*  665:     */   
/*  666:     */   public static Entity extractObjectRole(Entity t)
/*  667:     */   {
/*  668:1189 */     Entity roles = t.getObject();
/*  669:1191 */     if ((roles != null) && (roles.sequenceP("roles"))) {
/*  670:1192 */       for (Entity r : roles.getElements()) {
/*  671:1193 */         if (r.functionP("object"))
/*  672:     */         {
/*  673:1194 */           Entity o = r.getSubject();
/*  674:1195 */           return o;
/*  675:     */         }
/*  676:     */       }
/*  677:     */     }
/*  678:1199 */     return null;
/*  679:     */   }
/*  680:     */   
/*  681:     */   public void initializeVideoVariables(Entity t)
/*  682:     */   {
/*  683:1204 */     if ((t.relationP()) && (t.getSubject().entityP("you"))) {
/*  684:1205 */       if (t.getObject().entityP("precedent"))
/*  685:     */       {
/*  686:1206 */         Mark.say(new Object[] {"Starting video precedent" });
/*  687:1207 */         this.precedentInput = true;
/*  688:     */       }
/*  689:1209 */       else if (t.getObject().entityP("video"))
/*  690:     */       {
/*  691:1210 */         Mark.say(new Object[] {"Starting unnamed video" });
/*  692:1211 */         setPlayByPlayInput(true);
/*  693:     */       }
/*  694:     */     }
/*  695:     */   }
/*  696:     */   
/*  697:     */   public boolean storyClusterer(Entity t)
/*  698:     */   {
/*  699:1217 */     if ((t.relationP("cluster")) && (t.getSubject().entityP("you"))) {
/*  700:1218 */       return true;
/*  701:     */     }
/*  702:1220 */     return false;
/*  703:     */   }
/*  704:     */   
/*  705:     */   public void processRuleInjection(Object o)
/*  706:     */   {
/*  707:1224 */     Entity t = (Entity)o;
/*  708:1225 */     recordRule(t);
/*  709:     */   }
/*  710:     */   
/*  711:     */   public void processConceptInjection(Object o)
/*  712:     */   {
/*  713:1229 */     Sequence s = (Sequence)o;
/*  714:1230 */     s.addType(Punctuator.conditionName(s.getType()));
/*  715:1231 */     addConcept(s);
/*  716:     */   }
/*  717:     */   
/*  718:     */   public void testElementInjection(Object o)
/*  719:     */   {
/*  720:1263 */     Mark.say(
/*  721:1264 */       new Object[] { "Testing injection of", o });
/*  722:     */   }
/*  723:     */   
/*  724:     */   private void processElementWithInertInjection(Entity element)
/*  725:     */   {
/*  726:     */     try
/*  727:     */     {
/*  728:1268 */       setInert(true);
/*  729:1269 */       processElementInjection(element);
/*  730:     */     }
/*  731:     */     finally
/*  732:     */     {
/*  733:1272 */       setInert(false);
/*  734:     */     }
/*  735:     */   }
/*  736:     */   
/*  737:     */   public void processElementInjection(Object o)
/*  738:     */   {
/*  739:1277 */     boolean debug = false;
/*  740:1278 */     openStory();
/*  741:     */     Iterator localIterator2;
/*  742:     */     Vector<Entity> copy;
/*  743:     */     TraitExpert expert;
/*  744:     */     Entity element;
/*  745:     */     try
/*  746:     */     {
/*  747:1281 */       Entity t = (Entity)o;
/*  748:1283 */       if (t.sequenceP())
/*  749:     */       {
/*  750:1284 */         for (Entity e : t.getElements())
/*  751:     */         {
/*  752:1285 */           Mark.say(new Object[] {Boolean.valueOf(debug), "Injecting", Boolean.valueOf(this.storyInProgress), t.asStringWithIndexes() });
/*  753:1286 */           processElement(e);
/*  754:     */         }
/*  755:     */       }
/*  756:     */       else
/*  757:     */       {
/*  758:1290 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Injecting", Boolean.valueOf(this.storyInProgress), t.asStringWithIndexes() });
/*  759:1291 */         processElement(t);
/*  760:     */       }
/*  761:     */     }
/*  762:     */     catch (Exception e)
/*  763:     */     {
/*  764:1299 */       e.printStackTrace();
/*  765:     */     }
/*  766:     */     finally
/*  767:     */     {
/*  768:     */       Vector<Entity> copy;
/*  769:     */       TraitExpert expert;
/*  770:     */       Entity element;
/*  771:1306 */       Vector<Entity> copy = (Vector)this.story.getElements().clone();
/*  772:     */       
/*  773:1308 */       TraitExpert expert = getMentalModel().getTraitExpert();
/*  774:1310 */       for (Entity element : copy) {
/*  775:1314 */         if (!isInert()) {
/*  776:1315 */           triggerRules(element, this.story);
/*  777:     */         }
/*  778:     */       }
/*  779:1319 */       closeStory();
/*  780:1321 */       if (!this.story.isA("reflection")) {
/*  781:1322 */         transmitStory();
/*  782:     */       }
/*  783:     */     }
/*  784:     */   }
/*  785:     */   
/*  786:     */   public void processElement(Object object)
/*  787:     */   {
/*  788:1328 */     if ((isAsleep()) || (!(object instanceof Entity))) {
/*  789:1329 */       return;
/*  790:     */     }
/*  791:1332 */     Entity element = (Entity)object;
/*  792:1333 */     if (storyStarter(element))
/*  793:     */     {
/*  794:1334 */       startStory(element);
/*  795:1335 */       return;
/*  796:     */     }
/*  797:1337 */     if (storyClusterer(element))
/*  798:     */     {
/*  799:1338 */       Connections.getPorts(this).transmit("cluster story port", this.story);
/*  800:     */     }
/*  801:1340 */     else if ((element.isAPrimed("cause")) && (element.getObject().hasFeature("possibly")))
/*  802:     */     {
/*  803:1341 */       recordRule(element.clone());
/*  804:1342 */       element.getObject().removeFeature("possibly");
/*  805:     */     }
/*  806:     */     else
/*  807:     */     {
/*  808:1344 */       if ((!this.storyInProgress) && (!element.isAPrimed("cause"))) {
/*  809:1345 */         return;
/*  810:     */       }
/*  811:1347 */       if ((!this.storyInProgress) && (element.isAPrimed("cause")))
/*  812:     */       {
/*  813:1348 */         recordRule(element);
/*  814:1349 */         return;
/*  815:     */       }
/*  816:     */     }
/*  817:1352 */     addElement(element, this.story);
/*  818:1355 */     if ((Switch.level6LookForMentalModelEvidence.isSelected()) && 
/*  819:1356 */       (getMentalModel() != null))
/*  820:     */     {
/*  821:1357 */       ArrayList<MentalModel> models = getMentalModel().getLocalMentalModels();
/*  822:1358 */       BetterSignal signal = new BetterSignal(new Object[] { "event added", element, models });
/*  823:1359 */       Connections.getPorts(this).transmit("personality port", signal);
/*  824:     */     }
/*  825:1367 */     if (isPlayByPlayInput()) {
/*  826:     */       try
/*  827:     */       {
/*  828:1369 */         String comment = Generator.getGenerator().generate(element);
/*  829:1370 */         Connections.getPorts(this).transmit("switch tab", "Results");
/*  830:1371 */         Connections.getPorts(this).transmit("record port", comment);
/*  831:     */       }
/*  832:     */       catch (Exception e)
/*  833:     */       {
/*  834:1374 */         Mark.say(new Object[] {"Unable to say", element.asString() });
/*  835:     */       }
/*  836:     */     }
/*  837:1380 */     if (!this.story.isA("reflection"))
/*  838:     */     {
/*  839:1381 */       transmitStory();
/*  840:1382 */       transmitIncrement();
/*  841:     */     }
/*  842:     */   }
/*  843:     */   
/*  844:     */   private void transmitStory()
/*  845:     */   {
/*  846:1388 */     Connections.getPorts(this).transmit(extractStory());
/*  847:     */   }
/*  848:     */   
/*  849:     */   private void transmitIncrement()
/*  850:     */   {
/*  851:1392 */     Sequence increment = new Sequence();
/*  852:1393 */     if ((this.story != null) && (this.story.isA("story")))
/*  853:     */     {
/*  854:1394 */       List<Entity> newStuff = this.story.getElements().subList(this.sizeAtPreviousQuiescence, this.story.getElements().size());
/*  855:1395 */       for (Entity t : newStuff) {
/*  856:1396 */         increment.addElement(t);
/*  857:     */       }
/*  858:1398 */       Connections.getPorts(this).transmit("quiescence port", increment);
/*  859:     */     }
/*  860:1400 */     this.sizeAtPreviousQuiescence = this.story.getElements().size();
/*  861:     */   }
/*  862:     */   
/*  863:     */   private Object copyIt(Entity t)
/*  864:     */   {
/*  865:1405 */     return null;
/*  866:     */   }
/*  867:     */   
/*  868:     */   private boolean milestoneP(Entity element)
/*  869:     */   {
/*  870:1410 */     if (element.hasProperty("scene")) {
/*  871:1412 */       return true;
/*  872:     */     }
/*  873:1414 */     return false;
/*  874:     */   }
/*  875:     */   
/*  876:     */   public void describe(Object x)
/*  877:     */   {
/*  878:1418 */     if (isAsleep()) {
/*  879:1419 */       return;
/*  880:     */     }
/*  881:1421 */     if (!(x instanceof String)) {
/*  882:1422 */       return;
/*  883:     */     }
/*  884:1424 */     String s = (String)x;
/*  885:1425 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Starting description of reflection", s });
/*  886:1426 */     this.story = new Sequence("story");
/*  887:1427 */     this.story.addType("reflection");
/*  888:1428 */     this.inferences = new Sequence();
/*  889:1429 */     this.storyInProgress = true;
/*  890:1430 */     this.story.addType(s);
/*  891:     */   }
/*  892:     */   
/*  893:     */   public void recordRule(Object input)
/*  894:     */   {
/*  895:1437 */     if (isAsleep()) {
/*  896:1438 */       return;
/*  897:     */     }
/*  898:1441 */     if (!(input instanceof Relation)) {
/*  899:1442 */       return;
/*  900:     */     }
/*  901:1446 */     Relation rule = (Relation)input;
/*  902:1449 */     if ((this.storyInProgress) && (!rule.getObject().hasFeature("possibly")) && 
/*  903:1450 */       (!this.newRuleComingIn)) {
/*  904:1451 */       return;
/*  905:     */     }
/*  906:1461 */     Entity result = rule.getObject();
/*  907:1462 */     Entity cause = rule.getSubject();
/*  908:1469 */     if (rule.getObject().hasFeature("possibly"))
/*  909:     */     {
/*  910:1470 */       addProximityRule(rule);
/*  911:     */     }
/*  912:1472 */     else if ((rule.getObject().hasProperty("certainty", "tentative")) || (rule.isA("explanation")))
/*  913:     */     {
/*  914:1476 */       addExplanationRule(rule);
/*  915:     */     }
/*  916:1494 */     else if ((rule.getObject().hasFeature("not")) && (rule.getObject().hasProperty("modal", "can")))
/*  917:     */     {
/*  918:1495 */       addCensorRule(rule);
/*  919:     */     }
/*  920:     */     else
/*  921:     */     {
/*  922:1502 */       if (!rule.getSubject().sequenceP("conjuction"))
/*  923:     */       {
/*  924:1503 */         Sequence antecedents = new Sequence("conjuction");
/*  925:1504 */         antecedents.addElement(rule.getSubject());
/*  926:1505 */         rule.setSubject(antecedents);
/*  927:     */       }
/*  928:1508 */       if (rule.getObject().hasProperty("imperative", Boolean.valueOf(true))) {
/*  929:1509 */         rule.addType("abduction");
/*  930:1512 */       } else if (rule.isA("enable")) {
/*  931:1514 */         if (rule.getSubject().getElements().size() != 1)
/*  932:     */         {
/*  933:1515 */           Mark.err(new Object[] {"Can only handle enable rules with one antecedent!!!" });
/*  934:     */         }
/*  935:     */         else
/*  936:     */         {
/*  937:1518 */           Sequence sequence = new Sequence("conjuction");
/*  938:1519 */           sequence.addElement(rule.getObject());
/*  939:1520 */           rule.setObject((Entity)rule.getSubject().getElements().get(0));
/*  940:1521 */           rule.setSubject(sequence);
/*  941:1522 */           rule.addType("enabler");
/*  942:     */         }
/*  943:     */       }
/*  944:1526 */       addPredictionRule(rule);
/*  945:     */     }
/*  946:1529 */     if (rule.isAPrimed("entail"))
/*  947:     */     {
/*  948:1531 */       rule.addType("entail");
/*  949:1532 */       this.rules.addElement(rule);
/*  950:     */     }
/*  951:     */   }
/*  952:     */   
/*  953:     */   private void addAbductionRule(Relation rule)
/*  954:     */   {
/*  955:1544 */     rule.addType("abduction");
/*  956:     */     
/*  957:1546 */     boilerPlateForAddingRules(rule);
/*  958:     */   }
/*  959:     */   
/*  960:     */   private void addEnablementRule(Relation rule)
/*  961:     */   {
/*  962:1550 */     rule.addType("enabler");
/*  963:1551 */     boilerPlateForAddingRules(rule);
/*  964:     */   }
/*  965:     */   
/*  966:     */   private void addExplanationRule(Relation rule)
/*  967:     */   {
/*  968:1555 */     rule.addType("explanation");
/*  969:1556 */     addExplanationRule(rule.getObject(), rule);
/*  970:1557 */     boilerPlateForAddingRules(rule);
/*  971:     */   }
/*  972:     */   
/*  973:     */   private void addProximityRule(Relation rule)
/*  974:     */   {
/*  975:1561 */     rule.addType("explanation");
/*  976:1562 */     rule.addType("proximity");
/*  977:1563 */     addExplanationRule(rule.getObject(), rule);
/*  978:1564 */     boilerPlateForAddingRules(rule);
/*  979:     */   }
/*  980:     */   
/*  981:     */   private void boilerPlateForAddingRules(Relation rule)
/*  982:     */   {
/*  983:1568 */     this.rules.addElement(rule);
/*  984:1569 */     Connections.getPorts(this).transmit("rule port", this.rules);
/*  985:1570 */     Connections.getPorts(this).transmit("statistics bar output port", new BetterSignal(new Object[] { StatisticsBar.INFERENCE_RULE_COUNT, Integer.valueOf(countRules()) }));
/*  986:     */   }
/*  987:     */   
/*  988:     */   private void addCensorRule(Relation rule)
/*  989:     */   {
/*  990:1574 */     rule.addType("censor");
/*  991:1575 */     addCensorRule(rule.getObject(), rule);
/*  992:1576 */     boilerPlateForAddingRules(rule);
/*  993:     */   }
/*  994:     */   
/*  995:     */   private void addPredictionRule(Relation rule)
/*  996:     */   {
/*  997:1580 */     rule.addType("prediction");
/*  998:1581 */     for (Entity t : rule.getSubject().getElements()) {
/*  999:1582 */       addPredictionRule(t, reviseRule(t, rule));
/* 1000:     */     }
/* 1001:1584 */     boilerPlateForAddingRules(rule);
/* 1002:     */   }
/* 1003:     */   
/* 1004:     */   private int countRules()
/* 1005:     */   {
/* 1006:1588 */     return getExplanationRules().size() + getPredictionRules().size() + getCensorRules().size();
/* 1007:     */   }
/* 1008:     */   
/* 1009:     */   private Relation reviseRule(Entity cause, Relation rule)
/* 1010:     */   {
/* 1011:1593 */     return rule;
/* 1012:     */   }
/* 1013:     */   
/* 1014:     */   public void startStory()
/* 1015:     */   {
/* 1016:1608 */     Function object = new Function("story", new Entity("Unnamed story"));
/* 1017:1609 */     Entity starter = Constructors.makeRoleFrame(new Entity("you"), "start", object);
/* 1018:1610 */     startStory(starter);
/* 1019:     */   }
/* 1020:     */   
/* 1021:     */   public void startStory(Entity t)
/* 1022:     */   {
/* 1023:1614 */     boolean debug = false;
/* 1024:1615 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Starting story", t.asString() });
/* 1025:1616 */     Connections.getPorts(this).transmit(START_STORY_INFO_PORT, t);
/* 1026:1617 */     resetStoryVariables();
/* 1027:1618 */     Entity objectRole = extractObjectRole(t);
/* 1028:1619 */     if (objectRole == null) {
/* 1029:1620 */       return;
/* 1030:     */     }
/* 1031:1622 */     if (objectRole.functionP("reflection"))
/* 1032:     */     {
/* 1033:1623 */       this.story.addType("reflection");
/* 1034:1624 */       this.story.addType(objectRole.getSubject().getType());
/* 1035:     */     }
/* 1036:1626 */     if (objectRole.functionP("story"))
/* 1037:     */     {
/* 1038:1627 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Story", objectRole });
/* 1039:1628 */       this.story.addType("story");
/* 1040:1629 */       String name = objectRole.getSubject().getType();
/* 1041:1630 */       this.story.addType(name);
/* 1042:     */       
/* 1043:1632 */       Connections.getPorts(this).transmit("story name", name);
/* 1044:     */     }
/* 1045:1635 */     if (objectRole.entityP("story")) {
/* 1046:1636 */       this.story.addType("story");
/* 1047:     */     }
/* 1048:1639 */     initializeVideoVariables(t);
/* 1049:     */   }
/* 1050:     */   
/* 1051:     */   public void resetStoryVariables()
/* 1052:     */   {
/* 1053:1646 */     resetStoryVariablesWithoutTransmission();
/* 1054:     */     
/* 1055:1648 */     Connections.getPorts(this).transmit("prediction rules", this.predictionRuleMap);
/* 1056:1649 */     Connections.getPorts(this).transmit("Starting", "Starting");
/* 1057:1650 */     Connections.getPorts(this).transmit("change-mode", "use-kb");
/* 1058:1651 */     Connections.getPorts(this).transmit("reset concepts port", "reset");
/* 1059:1652 */     Connections.getPorts(this).transmit("inferences", getInferences());
/* 1060:1653 */     transmitStory();
/* 1061:     */   }
/* 1062:     */   
/* 1063:     */   private void resetStoryVariablesWithoutTransmission()
/* 1064:     */   {
/* 1065:1662 */     clearAlreadyPredicted();
/* 1066:1663 */     this.story = new Sequence("story");
/* 1067:1664 */     this.inferences = new Sequence();
/* 1068:1665 */     this.explicitElements = new Sequence();
/* 1069:1666 */     this.sizeAtPreviousQuiescence = 0;
/* 1070:1667 */     this.milestoneEncountered = false;
/* 1071:     */     
/* 1072:1669 */     this.storyInProgress = true;
/* 1073:     */     
/* 1074:1671 */     getAlreadyPredicted().clear();
/* 1075:1672 */     getSceneMapList().clear();
/* 1076:1673 */     getGlobalCache().clear();
/* 1077:1674 */     getMentalModelCache().clear();
/* 1078:1675 */     getWantCache().clear();
/* 1079:     */     
/* 1080:1677 */     this.storyTimer = System.currentTimeMillis();
/* 1081:     */   }
/* 1082:     */   
/* 1083:     */   public void clearAllMemories()
/* 1084:     */   {
/* 1085:1683 */     clearRules();
/* 1086:1684 */     clearConcepts();
/* 1087:1685 */     clearMentalModels();
/* 1088:1686 */     this.roles = new ArrayList();
/* 1089:1687 */     this.story = new Sequence();
/* 1090:1688 */     this.explicitElements = new Sequence();
/* 1091:1689 */     this.inferences = new Sequence();
/* 1092:1690 */     this.concepts = new Sequence();
/* 1093:     */     
/* 1094:1692 */     this.storyInProgress = false;
/* 1095:     */     
/* 1096:1694 */     getAlreadyPredicted().clear();
/* 1097:1695 */     getSceneMapList().clear();
/* 1098:1696 */     getGlobalCache().clear();
/* 1099:1697 */     getMentalModelCache().clear();
/* 1100:1698 */     getWantCache().clear();
/* 1101:     */     
/* 1102:1700 */     Connections.getPorts(this).transmit(this.story);
/* 1103:1701 */     Connections.getPorts(this).transmit("rule port", this.rules);
/* 1104:1702 */     Connections.getPorts(this).transmit("inferences", this.inferences);
/* 1105:1703 */     Connections.getPorts(this).transmit(Start.STAGE_DIRECTION_PORT, "reset");
/* 1106:1704 */     Connections.getPorts(this).transmit("story name", "");
/* 1107:     */   }
/* 1108:     */   
/* 1109:     */   public void openStory()
/* 1110:     */   {
/* 1111:1712 */     this.storyInProgress = true;
/* 1112:     */   }
/* 1113:     */   
/* 1114:     */   public void closeStory()
/* 1115:     */   {
/* 1116:1720 */     this.storyInProgress = false;
/* 1117:     */   }
/* 1118:     */   
/* 1119:     */   public void stopStory()
/* 1120:     */   {
/* 1121:1724 */     this.storyInProgress = false;
/* 1122:1726 */     if (this.story != null)
/* 1123:     */     {
/* 1124:1746 */       if (!this.story.isA("reflection"))
/* 1125:     */       {
/* 1126:1747 */         debugOutput = false;
/* 1127:1750 */         if (debugOutput)
/* 1128:     */         {
/* 1129:1751 */           Mark.say(new Object[] {"Story has", Integer.valueOf(this.story.getElements().size()), "elements" });
/* 1130:1752 */           for (Entity t : this.story.getElements()) {
/* 1131:1753 */             Mark.say(new Object[] {t.asString() });
/* 1132:     */           }
/* 1133:1755 */           Mark.say(new Object[] {"Story has", Integer.valueOf(getCommonsenseRules().getElements().size()), "commonsense rules" });
/* 1134:1756 */           for (Entity t : getCommonsenseRules().getElements()) {
/* 1135:1757 */             Mark.say(new Object[] {t.asString() });
/* 1136:     */           }
/* 1137:1759 */           Mark.say(new Object[] {"Story has", Integer.valueOf(this.concepts.getElements().size()), "concept patterns" });
/* 1138:1760 */           for (Entity t : this.concepts.getElements()) {
/* 1139:1761 */             Mark.say(new Object[] {t.asString() });
/* 1140:     */           }
/* 1141:1763 */           Mark.say(new Object[] {"Prediction rules", Integer.valueOf(this.predictionRuleMap.size()) });
/* 1142:1764 */           Mark.say(new Object[] {"Explanation rules", Integer.valueOf(this.explanationRuleMap.size()) });
/* 1143:     */         }
/* 1144:1767 */         transmitStory();
/* 1145:1768 */         Connections.getPorts(this).transmit("complete story events port", this.story);
/* 1146:     */         
/* 1147:1770 */         Connections.getPorts(this).transmit("final-inputs", getExplicitElements());
/* 1148:1771 */         Connections.getPorts(this).transmit("final-inferences", getInferences());
/* 1149:1775 */         if (Switch.level4ConceptPatterns.isSelected())
/* 1150:     */         {
/* 1151:1777 */           BetterSignal signal = new BetterSignal(new Object[] { this.concepts, this.story, getInferences() });
/* 1152:1778 */           Connections.getPorts(this).transmit("to completion detector port", signal);
/* 1153:     */         }
/* 1154:1780 */         if (!isPlayByPlayInput()) {}
/* 1155:1802 */         BetterSignal signal = new BetterSignal(new Object[] { this.story, getExplicitElements(), getInferences(), getInstantiatedConceptPatterns(), 
/* 1156:1803 */           getReflectionAnalysis() });
/* 1157:     */         
/* 1158:     */ 
/* 1159:     */ 
/* 1160:1807 */         Connections.getPorts(this).transmit("complete story analysis port", signal);
/* 1161:     */       }
/* 1162:     */       else
/* 1163:     */       {
/* 1164:1813 */         this.story.addType(Punctuator.conditionName(this.story.getType()));
/* 1165:1814 */         addConcept(this.story);
/* 1166:     */         
/* 1167:     */ 
/* 1168:     */ 
/* 1169:     */ 
/* 1170:     */ 
/* 1171:     */ 
/* 1172:1821 */         this.sizeAtPreviousQuiescence = 0;
/* 1173:1822 */         this.story = new Sequence("story");
/* 1174:     */       }
/* 1175:1824 */       setPlayByPlayInput(false);
/* 1176:1825 */       this.precedentInput = false;
/* 1177:     */     }
/* 1178:1831 */     Connections.getPorts(this).transmit("statistics bar output port", new BetterSignal(new Object[] { StatisticsBar.STORY_TIMER, 
/* 1179:1832 */       Timer.time("storyTimer", this.storyTimer) }));
/* 1180:     */   }
/* 1181:     */   
/* 1182:     */   private void clearMentalModels()
/* 1183:     */   {
/* 1184:1837 */     MentalModel myMentalModel = getMentalModel();
/* 1185:1838 */     if (myMentalModel != null) {
/* 1186:1839 */       myMentalModel.clearLocalMentalModels();
/* 1187:     */     }
/* 1188:     */   }
/* 1189:     */   
/* 1190:     */   public void clearRules()
/* 1191:     */   {
/* 1192:1844 */     this.predictionRuleMap = new HashMap();
/* 1193:1845 */     this.explanationRuleMap = new HashMap();
/* 1194:1846 */     this.censorRuleMap = new HashMap();
/* 1195:1847 */     this.onsetRuleMap = new HashMap();
/* 1196:1848 */     this.completionRuleMap = new HashMap();
/* 1197:1849 */     this.rules = new Sequence();
/* 1198:     */   }
/* 1199:     */   
/* 1200:     */   public void clearConcepts()
/* 1201:     */   {
/* 1202:1853 */     this.concepts = new Sequence("reflection");
/* 1203:1854 */     this.conceptMap = new HashMap();
/* 1204:1855 */     this.conceptOnsets = new Sequence();
/* 1205:     */   }
/* 1206:     */   
/* 1207:     */   private void addConcept(Sequence completeStory)
/* 1208:     */   {
/* 1209:1859 */     String reflectionName = completeStory.getType();
/* 1210:1860 */     if (this.conceptMap.containsKey(reflectionName))
/* 1211:     */     {
/* 1212:1862 */       ArrayList<Sequence> reflectionArray = (ArrayList)this.conceptMap.get(reflectionName);
/* 1213:1863 */       for (Sequence s : reflectionArray) {
/* 1214:1864 */         if (s.isDeepEqual(completeStory))
/* 1215:     */         {
/* 1216:1865 */           Mark.say(new Object[] {"Duplicate reflection detected: " + reflectionName });
/* 1217:1866 */           return;
/* 1218:     */         }
/* 1219:     */       }
/* 1220:1870 */       reflectionArray.add(completeStory);
/* 1221:     */     }
/* 1222:     */     else
/* 1223:     */     {
/* 1224:1874 */       ArrayList<Sequence> reflectionArray = new ArrayList();
/* 1225:1875 */       reflectionArray.add(completeStory);
/* 1226:1876 */       this.conceptMap.put(reflectionName, reflectionArray);
/* 1227:     */     }
/* 1228:1878 */     Relation onsetRule = extractReflectionOnsetRule(completeStory);
/* 1229:1879 */     this.conceptOnsets.addElement(onsetRule);
/* 1230:1880 */     for (Entity indicator : onsetRule.getSubject().getElements()) {
/* 1231:1881 */       addOnsetRule(indicator, onsetRule);
/* 1232:     */     }
/* 1233:1883 */     Sequence completionRule = extractReflection(completeStory);
/* 1234:1884 */     this.concepts.addElement(completionRule);
/* 1235:     */     
/* 1236:     */ 
/* 1237:     */ 
/* 1238:     */ 
/* 1239:     */ 
/* 1240:     */ 
/* 1241:     */ 
/* 1242:     */ 
/* 1243:     */ 
/* 1244:1894 */     Connections.getPorts(this).transmit("reflections port", this.concepts);
/* 1245:1895 */     Connections.getPorts(this).transmit("reflection onset port", this.conceptOnsets);
/* 1246:     */     
/* 1247:     */ 
/* 1248:1898 */     Connections.getPorts(this).transmit("statistics bar output port", new BetterSignal(new Object[] { StatisticsBar.CONCEPT_COUNT, 
/* 1249:1899 */       Integer.valueOf(this.concepts.getElements().size()) }));
/* 1250:     */   }
/* 1251:     */   
/* 1252:     */   private Sequence extractReflection(Sequence input)
/* 1253:     */   {
/* 1254:2003 */     Sequence reflection = new Sequence("reflection");
/* 1255:2004 */     reflection.addType(input.getType());
/* 1256:2005 */     for (Entity t : input.getElements()) {
/* 1257:2006 */       if (t.isAPrimed("description")) {
/* 1258:2007 */         reflection.addElement(t);
/* 1259:2009 */       } else if (t.isAPrimed("entail")) {
/* 1260:2010 */         reflection.addElement(t);
/* 1261:2012 */       } else if (!t.isAPrimed("classification")) {
/* 1262:2013 */         reflection.addElement(t);
/* 1263:2015 */       } else if ((t.isAPrimed("classification")) && (t.hasProperty("idiom", "sometimes"))) {
/* 1264:2016 */         reflection.addElement(t);
/* 1265:     */       }
/* 1266:     */     }
/* 1267:2020 */     return reflection;
/* 1268:     */   }
/* 1269:     */   
/* 1270:     */   private Relation extractReflectionOnsetRule(Sequence input)
/* 1271:     */   {
/* 1272:2032 */     Sequence antecedents = new Sequence("conjuction");
/* 1273:2033 */     Sequence consequents = new Sequence("conjuction");
/* 1274:2034 */     Relation rule = new Relation("onset", antecedents, consequents);
/* 1275:2035 */     antecedents.addType("indicators");
/* 1276:2036 */     consequents.addType("indications");
/* 1277:2037 */     rule.addType(input.getType());
/* 1278:2038 */     for (Entity t : input.getElements()) {
/* 1279:2039 */       if (t.isAPrimed("description"))
/* 1280:     */       {
/* 1281:2040 */         rule.addElement(t);
/* 1282:     */       }
/* 1283:2042 */       else if (t.isAPrimed("entail"))
/* 1284:     */       {
/* 1285:2043 */         Entity ifs = t.getSubject();
/* 1286:2044 */         Entity then = t.getObject();
/* 1287:2045 */         if (ifs.sequenceP("conjuction")) {
/* 1288:2046 */           for (Entity e : ifs.getElements()) {
/* 1289:2047 */             if (!antecedents.getElements().contains(e)) {
/* 1290:2048 */               antecedents.addElement(e);
/* 1291:     */             }
/* 1292:     */           }
/* 1293:     */         }
/* 1294:2052 */         consequents.addElement(then);
/* 1295:     */       }
/* 1296:2054 */       else if ((!t.isAPrimed("classification")) && 
/* 1297:2055 */         (!antecedents.getElements().contains(t)))
/* 1298:     */       {
/* 1299:2056 */         antecedents.addElement(t);
/* 1300:     */       }
/* 1301:     */     }
/* 1302:2060 */     for (Entity t : antecedents.getElements()) {
/* 1303:2061 */       consequents.getElements().remove(t);
/* 1304:     */     }
/* 1305:2063 */     rule.setSubject(antecedents);
/* 1306:2064 */     rule.setObject(consequents);
/* 1307:2065 */     return rule;
/* 1308:     */   }
/* 1309:     */   
/* 1310:     */   protected static Vector<Entity> copy(Vector<Entity> things)
/* 1311:     */   {
/* 1312:2113 */     Vector<Entity> newVector = new Vector();
/* 1313:2114 */     newVector.addAll(things);
/* 1314:2115 */     return newVector;
/* 1315:     */   }
/* 1316:     */   
/* 1317:     */   public void addInference(Entity target)
/* 1318:     */   {
/* 1319:2148 */     if ((!this.inferences.contains(target)) && (!Predicates.isMeans(target)))
/* 1320:     */     {
/* 1321:2150 */       this.inferences.addElement(target);
/* 1322:2151 */       addAlreadyPredicted(target.getObject());
/* 1323:2152 */       Connections.getPorts(this).transmit("incremental output port", target);
/* 1324:2153 */       Connections.getPorts(this).transmit("inferences", getInferences());
/* 1325:     */     }
/* 1326:     */   }
/* 1327:     */   
/* 1328:     */   public Sequence getInferences()
/* 1329:     */   {
/* 1330:2161 */     return this.inferences;
/* 1331:     */   }
/* 1332:     */   
/* 1333:     */   public Sequence getExplicitElements()
/* 1334:     */   {
/* 1335:2165 */     return this.explicitElements;
/* 1336:     */   }
/* 1337:     */   
/* 1338:     */   public Sequence getStory()
/* 1339:     */   {
/* 1340:2169 */     return this.story;
/* 1341:     */   }
/* 1342:     */   
/* 1343:     */   public Sequence extractStory()
/* 1344:     */   {
/* 1345:2178 */     int storyElementCount = countElementsSansInferences(this.story.getElements());
/* 1346:2179 */     int oldExplicitInputs = storyElementCount - getInferences().getElements().size();
/* 1347:2180 */     int newExplicitInputs = getExplicitElements().getElements().size();
/* 1348:     */     
/* 1349:     */ 
/* 1350:     */ 
/* 1351:     */ 
/* 1352:     */ 
/* 1353:     */ 
/* 1354:2187 */     Connections.getPorts(this).transmit("statistics bar output port", new BetterSignal(new Object[] { StatisticsBar.STORY_ELEMENT_COUNT, Integer.valueOf(storyElementCount) }));
/* 1355:     */     
/* 1356:     */ 
/* 1357:     */ 
/* 1358:     */ 
/* 1359:     */ 
/* 1360:     */ 
/* 1361:2194 */     Connections.getPorts(this).transmit("statistics bar output port", new BetterSignal(new Object[] { "explicit statement count", 
/* 1362:2195 */       Integer.valueOf(newExplicitInputs) }));
/* 1363:     */     
/* 1364:2197 */     return this.story;
/* 1365:     */   }
/* 1366:     */   
/* 1367:     */   private int countElementsSansInferences(Vector<Entity> elements)
/* 1368:     */   {
/* 1369:2201 */     int size = 0;
/* 1370:2202 */     for (Entity t : elements) {
/* 1371:2203 */       if (!t.isAPrimed("cause")) {
/* 1372:2204 */         size++;
/* 1373:     */       }
/* 1374:     */     }
/* 1375:2210 */     return size;
/* 1376:     */   }
/* 1377:     */   
/* 1378:     */   private class RulePackage
/* 1379:     */   {
/* 1380:     */     public Relation rule;
/* 1381:     */     public ArrayList<PairOfEntities> bindings;
/* 1382:     */     public Sequence antecedants;
/* 1383:     */     
/* 1384:     */     public RulePackage(ArrayList<PairOfEntities> r)
/* 1385:     */     {
/* 1386:2259 */       this.rule = r;
/* 1387:2260 */       this.bindings = b;
/* 1388:2261 */       this.antecedants = new Sequence("conjuction");
/* 1389:     */     }
/* 1390:     */     
/* 1391:     */     public RulePackage(ArrayList<PairOfEntities> r, Sequence b)
/* 1392:     */     {
/* 1393:2265 */       this(r, b);
/* 1394:2266 */       this.antecedants = a;
/* 1395:     */     }
/* 1396:     */   }
/* 1397:     */   
/* 1398:     */   public ArrayList<Entity> getCensorRules(Entity t, HashMap<String, ArrayList<Entity>> censorRuleMap, boolean includeHasNegation)
/* 1399:     */   {
/* 1400:2271 */     ArrayList<Entity> mappedRules = new ArrayList();
/* 1401:2272 */     Thread thread = t.getThread("action");
/* 1402:2273 */     if (thread == null) {
/* 1403:2274 */       thread = t.getPrimedThread();
/* 1404:     */     }
/* 1405:2276 */     if (thread == null) {
/* 1406:2277 */       thread = new Thread();
/* 1407:     */     }
/* 1408:2280 */     for (String type : thread)
/* 1409:     */     {
/* 1410:2283 */       ArrayList<Entity> more = (ArrayList)censorRuleMap.get(type);
/* 1411:2285 */       if (more != null) {
/* 1412:2286 */         for (Entity r : more) {
/* 1413:2287 */           if ((r.isAPrimed("censor")) && 
/* 1414:2288 */             (testRuleForNegation(r, includeHasNegation))) {
/* 1415:2289 */             mappedRules.add(r);
/* 1416:     */           }
/* 1417:     */         }
/* 1418:     */       }
/* 1419:     */     }
/* 1420:2295 */     return mappedRules;
/* 1421:     */   }
/* 1422:     */   
/* 1423:     */   public ArrayList<Entity> getPredictionRules(Entity t, HashMap<String, ArrayList<Entity>> predictionRuleMap, boolean includeHasNegation)
/* 1424:     */   {
/* 1425:2300 */     ArrayList<Entity> mappedRules = new ArrayList();
/* 1426:     */     
/* 1427:2302 */     Thread thread = t.getThread("action");
/* 1428:2303 */     if (thread == null) {
/* 1429:2304 */       thread = t.getPrimedThread();
/* 1430:     */     }
/* 1431:2306 */     if (thread == null) {
/* 1432:2307 */       thread = new Thread();
/* 1433:     */     }
/* 1434:2309 */     for (String type : thread)
/* 1435:     */     {
/* 1436:2310 */       ArrayList<Entity> more = (ArrayList)predictionRuleMap.get(type);
/* 1437:2311 */       if (more != null) {
/* 1438:2312 */         for (Entity r : more) {
/* 1439:2313 */           if ((!r.isAPrimed("entail")) && 
/* 1440:2314 */             (testRuleForNegation(r, includeHasNegation)) && 
/* 1441:2315 */             (!mappedRules.contains(r))) {
/* 1442:2316 */             mappedRules.add(r);
/* 1443:     */           }
/* 1444:     */         }
/* 1445:     */       }
/* 1446:     */     }
/* 1447:2328 */     return mappedRules;
/* 1448:     */   }
/* 1449:     */   
/* 1450:     */   public ArrayList<Entity> getExplanationRules(Entity t, HashMap<String, ArrayList<Entity>> explanationRuleMap)
/* 1451:     */   {
/* 1452:2333 */     boolean debug = false;
/* 1453:     */     
/* 1454:2335 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Finding rules for", t.asString() });
/* 1455:     */     
/* 1456:2337 */     ArrayList<Entity> mappedRules = new ArrayList();
/* 1457:2338 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Explanation rule count:", Integer.valueOf(explanationRuleMap.size()), t.asString() });
/* 1458:2339 */     Thread thread = t.getThreadWith("action");
/* 1459:2340 */     if (thread == null) {
/* 1460:2341 */       thread = t.getPrimedThread();
/* 1461:     */     }
/* 1462:2343 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Thread", thread });
/* 1463:2344 */     for (String type : thread)
/* 1464:     */     {
/* 1465:2345 */       ArrayList<Entity> more = (ArrayList)explanationRuleMap.get(type);
/* 1466:2346 */       if (more != null) {
/* 1467:2347 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Working on type", type, "returning", Integer.valueOf(more.size()) });
/* 1468:     */       } else {
/* 1469:2350 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Working on type", type, "no result" });
/* 1470:     */       }
/* 1471:2352 */       if (more != null) {
/* 1472:2353 */         for (Entity r : more) {
/* 1473:2354 */           if ((!r.isAPrimed("entail")) && 
/* 1474:2355 */             (!mappedRules.contains(r))) {
/* 1475:2356 */             mappedRules.add(r);
/* 1476:     */           }
/* 1477:     */         }
/* 1478:     */       }
/* 1479:     */     }
/* 1480:2367 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Found", Integer.valueOf(mappedRules.size()), "rules" });
/* 1481:     */     
/* 1482:2369 */     return mappedRules;
/* 1483:     */   }
/* 1484:     */   
/* 1485:     */   public void addExplanationRules(ArrayList<Relation> rules)
/* 1486:     */   {
/* 1487:2373 */     for (Relation r : rules) {
/* 1488:2374 */       addExplanationRule(r);
/* 1489:     */     }
/* 1490:     */   }
/* 1491:     */   
/* 1492:     */   public void addCensorRules(ArrayList<Relation> rules)
/* 1493:     */   {
/* 1494:2379 */     for (Relation r : rules) {
/* 1495:2380 */       addCensorRule(r);
/* 1496:     */     }
/* 1497:     */   }
/* 1498:     */   
/* 1499:     */   public void addPredictionRules(ArrayList<Relation> rules)
/* 1500:     */   {
/* 1501:2385 */     for (Relation r : rules) {
/* 1502:2386 */       addPredictionRule(r);
/* 1503:     */     }
/* 1504:     */   }
/* 1505:     */   
/* 1506:     */   public ArrayList<Relation> getExplanationRules()
/* 1507:     */   {
/* 1508:2391 */     return getRules(this.explanationRuleMap);
/* 1509:     */   }
/* 1510:     */   
/* 1511:     */   public ArrayList<Relation> getCensorRules()
/* 1512:     */   {
/* 1513:2395 */     return getRules(this.censorRuleMap);
/* 1514:     */   }
/* 1515:     */   
/* 1516:     */   public ArrayList<Relation> getPredictionRules()
/* 1517:     */   {
/* 1518:2399 */     return getRules(this.predictionRuleMap);
/* 1519:     */   }
/* 1520:     */   
/* 1521:     */   private ArrayList<Relation> getRules(HashMap<String, ArrayList<Entity>> map)
/* 1522:     */   {
/* 1523:2403 */     HashSet<Relation> rules = new HashSet();
/* 1524:     */     Iterator localIterator2;
/* 1525:     */     Entity t;
/* 1526:2404 */     for (Iterator localIterator1 = map.values().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 1527:     */     {
/* 1528:2404 */       ArrayList<Entity> l = (ArrayList)localIterator1.next();
/* 1529:2405 */       localIterator2 = l.iterator(); continue;t = (Entity)localIterator2.next();
/* 1530:2406 */       rules.add((Relation)t);
/* 1531:     */     }
/* 1532:2409 */     ArrayList<Relation> result = new ArrayList();
/* 1533:2410 */     for (Entity t : rules) {
/* 1534:2411 */       result.add((Relation)t);
/* 1535:     */     }
/* 1536:2413 */     return result;
/* 1537:     */   }
/* 1538:     */   
/* 1539:     */   private static boolean testRuleForNegation(Entity r, boolean includeHasNegation)
/* 1540:     */   {
/* 1541:2417 */     if (includeHasNegation) {
/* 1542:2418 */       return true;
/* 1543:     */     }
/* 1544:2420 */     if (r.functionP())
/* 1545:     */     {
/* 1546:2421 */       Object antecedents = r.getSubject();
/* 1547:2422 */       if ((antecedents instanceof Sequence)) {
/* 1548:2423 */         for (Entity t : ((Sequence)antecedents).getElements()) {
/* 1549:2424 */           if (t.hasFeature("not")) {
/* 1550:2425 */             return false;
/* 1551:     */           }
/* 1552:     */         }
/* 1553:     */       }
/* 1554:     */     }
/* 1555:2430 */     return true;
/* 1556:     */   }
/* 1557:     */   
/* 1558:     */   private String getRuleKey(Entity t)
/* 1559:     */   {
/* 1560:2437 */     if (t.relationP("perform")) {
/* 1561:2438 */       return "action";
/* 1562:     */     }
/* 1563:2441 */     return t.getType();
/* 1564:     */   }
/* 1565:     */   
/* 1566:     */   private void addPredictionRule(Entity antecedent, Relation rule)
/* 1567:     */   {
/* 1568:2447 */     if (this.story.isA("reflection")) {
/* 1569:2447 */       rule.isA("entail");
/* 1570:     */     }
/* 1571:2450 */     String type = getRuleKey(antecedent);
/* 1572:2454 */     if ((antecedent.functionP("appear")) && (antecedent.getSubject().getType().equals("action")))
/* 1573:     */     {
/* 1574:2455 */       String action_type = antecedent.getSubject().getType();
/* 1575:2456 */       if (!this.predictionRuleMap.containsKey(action_type)) {
/* 1576:2456 */         this.predictionRuleMap.put(action_type, new ArrayList());
/* 1577:     */       }
/* 1578:2457 */       ((ArrayList)this.predictionRuleMap.get(action_type)).add(rule);
/* 1579:     */     }
/* 1580:2460 */     ArrayList<Entity> list = (ArrayList)this.predictionRuleMap.get(type);
/* 1581:2461 */     if (list == null)
/* 1582:     */     {
/* 1583:2462 */       list = new ArrayList();
/* 1584:2463 */       this.predictionRuleMap.put(type, list);
/* 1585:     */     }
/* 1586:2466 */     list.add(rule);
/* 1587:     */   }
/* 1588:     */   
/* 1589:     */   private void addExplanationRule(Entity consequent, Relation rule)
/* 1590:     */   {
/* 1591:2474 */     String type = consequent.getType();
/* 1592:2476 */     if ((consequent.functionP("appear")) && (consequent.getSubject().getType().equals("action")))
/* 1593:     */     {
/* 1594:2477 */       String action_type = consequent.getSubject().getType();
/* 1595:2478 */       if (!this.explanationRuleMap.containsKey(action_type)) {
/* 1596:2478 */         this.explanationRuleMap.put(action_type, new ArrayList());
/* 1597:     */       }
/* 1598:2479 */       ((ArrayList)this.explanationRuleMap.get(action_type)).add(rule);
/* 1599:     */     }
/* 1600:2482 */     ArrayList<Entity> list = (ArrayList)this.explanationRuleMap.get(type);
/* 1601:2483 */     if (list == null)
/* 1602:     */     {
/* 1603:2484 */       list = new ArrayList();
/* 1604:2485 */       this.explanationRuleMap.put(type, list);
/* 1605:     */     }
/* 1606:2487 */     list.add(rule);
/* 1607:     */   }
/* 1608:     */   
/* 1609:     */   private void addCensorRule(Entity consequent, Relation rule)
/* 1610:     */   {
/* 1611:2491 */     if (!consequent.hasFeature("not")) {
/* 1612:2492 */       return;
/* 1613:     */     }
/* 1614:2495 */     String type = consequent.getType();
/* 1615:     */     
/* 1616:     */ 
/* 1617:2498 */     ArrayList<Entity> list = (ArrayList)this.censorRuleMap.get(type);
/* 1618:2499 */     if (list == null)
/* 1619:     */     {
/* 1620:2500 */       list = new ArrayList();
/* 1621:2501 */       this.censorRuleMap.put(type, list);
/* 1622:     */     }
/* 1623:2503 */     list.add(rule);
/* 1624:     */   }
/* 1625:     */   
/* 1626:     */   private void addOnsetRule(Entity antecedent, Relation rule)
/* 1627:     */   {
/* 1628:2507 */     String type = antecedent.getType();
/* 1629:2508 */     if ((antecedent.functionP("appear")) && (antecedent.getSubject().getType().equals("action"))) {
/* 1630:2509 */       type = antecedent.getSubject().getType();
/* 1631:     */     }
/* 1632:2511 */     ArrayList<Entity> list = (ArrayList)this.onsetRuleMap.get(type);
/* 1633:2512 */     if (list == null)
/* 1634:     */     {
/* 1635:2513 */       list = new ArrayList();
/* 1636:2514 */       this.onsetRuleMap.put(type, list);
/* 1637:     */     }
/* 1638:2516 */     list.add(rule);
/* 1639:     */   }
/* 1640:     */   
/* 1641:     */   private HashSet<String> getAlreadyPredicted()
/* 1642:     */   {
/* 1643:2541 */     if (this.alreadyPredicted == null) {
/* 1644:2542 */       this.alreadyPredicted = new HashSet();
/* 1645:     */     }
/* 1646:2544 */     return this.alreadyPredicted;
/* 1647:     */   }
/* 1648:     */   
/* 1649:     */   private boolean isAlreadyPredicted(Entity t)
/* 1650:     */   {
/* 1651:2560 */     return getAlreadyPredicted().contains(t.asString());
/* 1652:     */   }
/* 1653:     */   
/* 1654:     */   private void addAlreadyPredicted(Entity t)
/* 1655:     */   {
/* 1656:2575 */     if (!isAlreadyPredicted(t)) {
/* 1657:2576 */       getAlreadyPredicted().add(t.asStringWithIndexes());
/* 1658:     */     }
/* 1659:     */   }
/* 1660:     */   
/* 1661:     */   private void clearAlreadyPredicted()
/* 1662:     */   {
/* 1663:2581 */     getAlreadyPredicted().clear();
/* 1664:     */   }
/* 1665:     */   
/* 1666:     */   public static String getTitle(Sequence story)
/* 1667:     */   {
/* 1668:2590 */     for (Entity t : story.getElements()) {
/* 1669:2591 */       if (t.relationP("start"))
/* 1670:     */       {
/* 1671:2592 */         Relation r = (Relation)t;
/* 1672:2593 */         if ((r.getSubject().isA("you")) && 
/* 1673:2594 */           (r.getObject().functionP())) {
/* 1674:2595 */           return r.getObject().getSubject().getType();
/* 1675:     */         }
/* 1676:     */       }
/* 1677:     */     }
/* 1678:2600 */     return story.getType();
/* 1679:     */   }
/* 1680:     */   
/* 1681:     */   public static void main(String[] ignore)
/* 1682:     */   {
/* 1683:2604 */     StoryProcessor processor = new StoryProcessor("Foo");
/* 1684:2605 */     processor.story = new Sequence();
/* 1685:2606 */     Entity mark = new Entity("queen");
/* 1686:2607 */     Entity bill = new Entity("king");
/* 1687:2608 */     Relation hit = new Relation("hit", bill, mark);
/* 1688:2609 */     Entity cris = new Entity("knight");
/* 1689:2610 */     Relation friend = new Relation("friend", cris, mark);
/* 1690:2611 */     Relation harm = new Relation("harm", bill, cris);
/* 1691:2612 */     Sequence antecedants = new Sequence();
/* 1692:2613 */     antecedants.addElement(hit);
/* 1693:2614 */     antecedants.addElement(friend);
/* 1694:2615 */     Relation cause = new Relation("cause", antecedants, harm);
/* 1695:     */     
/* 1696:     */ 
/* 1697:     */ 
/* 1698:2619 */     cause.addType("explanation");
/* 1699:     */     
/* 1700:2621 */     ArrayList<Relation> rules = new ArrayList();
/* 1701:     */     
/* 1702:2623 */     processor.recordRule(cause);
/* 1703:     */     
/* 1704:     */ 
/* 1705:2626 */     processor.processElement(cause);
/* 1706:2627 */     processor.storyInProgress = true;
/* 1707:2628 */     Entity mary = new Entity("queen");
/* 1708:2629 */     mary.addType("name");
/* 1709:2630 */     mary.addType("mary");
/* 1710:2631 */     Entity blak = new Entity("king");
/* 1711:2632 */     blak.addType("name");
/* 1712:2633 */     blak.addType("blak");
/* 1713:2634 */     Relation hit2 = new Relation("hit", blak, mary);
/* 1714:2635 */     Entity curt = new Entity("knight");
/* 1715:2636 */     curt.addType("name");
/* 1716:2637 */     curt.addType("curt");
/* 1717:2638 */     Relation friend2 = new Relation("friend", curt, mary);
/* 1718:2639 */     processor.processElement(friend2);
/* 1719:2640 */     processor.processElement(hit2);
/* 1720:     */     
/* 1721:2642 */     Entity sam = new Entity("knight");
/* 1722:2643 */     sam.addType("name");
/* 1723:2644 */     sam.addType("sam");
/* 1724:2645 */     Relation friend3 = new Relation("friend", sam, mary);
/* 1725:2646 */     processor.processElement(friend3);
/* 1726:     */     
/* 1727:2648 */     Relation harm2 = new Relation("harm", blak, curt);
/* 1728:2649 */     Relation harm3 = new Relation("harm", blak, sam);
/* 1729:     */     
/* 1730:2651 */     processor.processElement(harm2);
/* 1731:2652 */     processor.processElement(harm3);
/* 1732:     */   }
/* 1733:     */   
/* 1734:2659 */   ArrayList<Entity> roles = new ArrayList();
/* 1735:     */   
/* 1736:     */   private Entity replace(Entity role)
/* 1737:     */   {
/* 1738:2662 */     for (Entity candidate : this.roles) {
/* 1739:2663 */       if (Substitutor.matchTypesAndSign(role, candidate)) {
/* 1740:2664 */         return candidate;
/* 1741:     */       }
/* 1742:     */     }
/* 1743:2667 */     this.roles.add(role);
/* 1744:2668 */     return role;
/* 1745:     */   }
/* 1746:     */   
/* 1747:     */   private void rebuild(Entity element)
/* 1748:     */   {
/* 1749:2674 */     rebuildAux(element);
/* 1750:     */   }
/* 1751:     */   
/* 1752:     */   private void rebuildAux(Entity element)
/* 1753:     */   {
/* 1754:2680 */     if (element.entityP())
/* 1755:     */     {
/* 1756:2682 */       System.err.println("Bug--got into Thing part of StoryProcessor.thingP");
/* 1757:2683 */       throw new RuntimeException("Bug--got into Thing part of StoryProcessor.thingP");
/* 1758:     */     }
/* 1759:2685 */     if (element.functionP()) {
/* 1760:2686 */       if (element.getSubject().entityP()) {
/* 1761:2687 */         element.setSubject(replace(element.getSubject()));
/* 1762:     */       } else {
/* 1763:2690 */         rebuildAux(element.getSubject());
/* 1764:     */       }
/* 1765:     */     }
/* 1766:2693 */     if (element.relationP())
/* 1767:     */     {
/* 1768:2694 */       if (element.getSubject().entityP()) {
/* 1769:2695 */         element.setSubject(replace(element.getSubject()));
/* 1770:     */       } else {
/* 1771:2698 */         rebuildAux(element.getSubject());
/* 1772:     */       }
/* 1773:2700 */       if (element.getObject().entityP()) {
/* 1774:2701 */         element.setObject(replace(element.getObject()));
/* 1775:     */       } else {
/* 1776:2704 */         rebuildAux(element.getObject());
/* 1777:     */       }
/* 1778:     */     }
/* 1779:2707 */     if (element.sequenceP())
/* 1780:     */     {
/* 1781:2708 */       Vector<Entity> elements = element.getElements();
/* 1782:2709 */       Vector<Entity> scratch = (Vector)elements.clone();
/* 1783:2710 */       for (int i = 0; i < scratch.size(); i++)
/* 1784:     */       {
/* 1785:2711 */         Entity e = (Entity)scratch.get(i);
/* 1786:2712 */         if (e.entityP())
/* 1787:     */         {
/* 1788:2713 */           elements.remove(i);
/* 1789:2714 */           elements.add(i, replace(e));
/* 1790:     */         }
/* 1791:     */         else
/* 1792:     */         {
/* 1793:2717 */           rebuildAux(e);
/* 1794:     */         }
/* 1795:     */       }
/* 1796:     */     }
/* 1797:     */   }
/* 1798:     */   
/* 1799:     */   private Entity rebuildElement(Entity element, HashMap<String, Entity> cache)
/* 1800:     */   {
/* 1801:2725 */     boolean debug = false;
/* 1802:2726 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Looking for", element.asString() });
/* 1803:2727 */     if (checkForElementsInCache(element, cache))
/* 1804:     */     {
/* 1805:2728 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Working on replacement of", element.hash() });
/* 1806:2729 */       return rebuildElementAux(element, cache);
/* 1807:     */     }
/* 1808:2732 */     Mark.say(new Object[] {Boolean.valueOf(debug), "No need to replace", element.hash() });
/* 1809:     */     
/* 1810:     */ 
/* 1811:     */ 
/* 1812:2736 */     return element;
/* 1813:     */   }
/* 1814:     */   
/* 1815:     */   private Entity rebuildWithCurrentScene(Entity element, HashMap<String, Entity> localMap)
/* 1816:     */   {
/* 1817:2741 */     HashMap<String, Entity> cache = getCurrentSceneMap();
/* 1818:2742 */     if (isCached(element, cache)) {
/* 1819:2743 */       return getFromCache(element, cache);
/* 1820:     */     }
/* 1821:2745 */     return reassemble(element, localMap);
/* 1822:     */   }
/* 1823:     */   
/* 1824:     */   private Entity rebuildWithWholeStory(Entity element, HashMap<String, Entity> localMap)
/* 1825:     */   {
/* 1826:2749 */     ArrayList<HashMap<String, Entity>> cacheList = getSceneMapList();
/* 1827:2750 */     for (HashMap<String, Entity> cache : cacheList) {
/* 1828:2751 */       if (isCached(element, cache)) {
/* 1829:2752 */         return getFromCache(element, cache);
/* 1830:     */       }
/* 1831:     */     }
/* 1832:2755 */     return reassemble(element, localMap);
/* 1833:     */   }
/* 1834:     */   
/* 1835:     */   private Entity rebuildCauseRelation(Entity element)
/* 1836:     */   {
/* 1837:2764 */     if (!isInCurrentScene(element)) {
/* 1838:2765 */       element = makeNewVersion(element);
/* 1839:     */     }
/* 1840:2768 */     Vector<Entity> antecedents = ((Sequence)element.getSubject()).getElements();
/* 1841:2770 */     for (int i = 0; i < antecedents.size(); i++)
/* 1842:     */     {
/* 1843:2772 */       Entity antecedent = (Entity)antecedents.get(i);
/* 1844:2773 */       if (!isInAnyScene(antecedent))
/* 1845:     */       {
/* 1846:2775 */         Entity newVersion = makeNewVersion(antecedent);
/* 1847:2776 */         antecedents.set(i, newVersion);
/* 1848:     */       }
/* 1849:     */     }
/* 1850:2779 */     Entity consequent = element.getObject();
/* 1851:2780 */     if (!isInCurrentScene(consequent))
/* 1852:     */     {
/* 1853:2781 */       Entity newVersion = makeNewVersion(consequent);
/* 1854:2782 */       element.setObject(newVersion);
/* 1855:     */     }
/* 1856:2784 */     return element;
/* 1857:     */   }
/* 1858:     */   
/* 1859:     */   private Entity rebuildElementAux(Entity element, HashMap<String, Entity> cache)
/* 1860:     */   {
/* 1861:2788 */     boolean debug = false;
/* 1862:2790 */     if (element.entityP())
/* 1863:     */     {
/* 1864:2794 */       Entity cachedElement = getFromCache(element, cache);
/* 1865:2795 */       if (cachedElement != null) {
/* 1866:2796 */         return cachedElement;
/* 1867:     */       }
/* 1868:2799 */       return element;
/* 1869:     */     }
/* 1870:2801 */     if (isCached(element, cache))
/* 1871:     */     {
/* 1872:2802 */       Entity cachedElement = getFromCache(element, cache);
/* 1873:2803 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Found element in Cache\n", element.asStringWithIndexes(), "\n", cachedElement.asStringWithIndexes() });
/* 1874:2804 */       return cachedElement;
/* 1875:     */     }
/* 1876:2807 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Working on replacement of", element.asStringWithIndexes() });
/* 1877:2808 */     if (element.functionP())
/* 1878:     */     {
/* 1879:2809 */       element = ((Function)element).rebuild();
/* 1880:2810 */       element.setSubject(rebuildElementAux(element.getSubject(), cache));
/* 1881:     */     }
/* 1882:2812 */     else if (element.relationP())
/* 1883:     */     {
/* 1884:2813 */       element = ((Relation)element).rebuild();
/* 1885:2814 */       element.setSubject(rebuildElementAux(element.getSubject(), cache));
/* 1886:2815 */       element.setObject(rebuildElementAux(element.getObject(), cache));
/* 1887:     */     }
/* 1888:2817 */     else if (element.sequenceP())
/* 1889:     */     {
/* 1890:2818 */       Vector<Entity> elements = element.getElements();
/* 1891:2819 */       element = ((Sequence)element).rebuildWithoutElements();
/* 1892:2820 */       for (Entity x : elements) {
/* 1893:2821 */         element.addElement(rebuildElementAux(x, cache));
/* 1894:     */       }
/* 1895:     */     }
/* 1896:2824 */     return element;
/* 1897:     */   }
/* 1898:     */   
/* 1899:     */   private boolean checkForElementsInCache(Entity element, HashMap<String, Entity> cache)
/* 1900:     */   {
/* 1901:2869 */     Mark.say(
/* 1902:     */     
/* 1903:     */ 
/* 1904:     */ 
/* 1905:     */ 
/* 1906:     */ 
/* 1907:     */ 
/* 1908:     */ 
/* 1909:     */ 
/* 1910:     */ 
/* 1911:     */ 
/* 1912:     */ 
/* 1913:     */ 
/* 1914:     */ 
/* 1915:     */ 
/* 1916:     */ 
/* 1917:     */ 
/* 1918:     */ 
/* 1919:     */ 
/* 1920:     */ 
/* 1921:     */ 
/* 1922:     */ 
/* 1923:     */ 
/* 1924:2892 */       new Object[] { "Checking up on", element.asString() });
/* 1925:2870 */     if (element.entityP()) {
/* 1926:2872 */       return false;
/* 1927:     */     }
/* 1928:2874 */     if (isCached(element, cache)) {
/* 1929:2875 */       return true;
/* 1930:     */     }
/* 1931:2878 */     if (element.functionP()) {
/* 1932:2879 */       return checkForElementsInCache(element.getSubject(), cache);
/* 1933:     */     }
/* 1934:2881 */     if (element.relationP()) {
/* 1935:2882 */       return (checkForElementsInCache(element.getSubject(), cache)) || (checkForElementsInCache(element.getObject(), cache));
/* 1936:     */     }
/* 1937:2884 */     if (element.sequenceP()) {
/* 1938:2885 */       for (Entity e : element.getElements()) {
/* 1939:2886 */         if (checkForElementsInCache(e.getSubject(), cache)) {
/* 1940:2887 */           return true;
/* 1941:     */         }
/* 1942:     */       }
/* 1943:     */     }
/* 1944:2891 */     return false;
/* 1945:     */   }
/* 1946:     */   
/* 1947:     */   private Entity getFromCache(Entity t, HashMap<String, Entity> map)
/* 1948:     */   {
/* 1949:2903 */     Entity result = (Entity)map.get(hash(t));
/* 1950:2904 */     if (result == null) {
/* 1951:2906 */       return t;
/* 1952:     */     }
/* 1953:2909 */     return result;
/* 1954:     */   }
/* 1955:     */   
/* 1956:     */   private void insertIntoCache(Entity t, HashMap<String, Entity> cache)
/* 1957:     */   {
/* 1958:2932 */     cache.put(hash(t), t);
/* 1959:     */   }
/* 1960:     */   
/* 1961:     */   private void addToCompleteCache(Entity t, HashMap<String, Entity> cache)
/* 1962:     */   {
/* 1963:2940 */     if (t.entityP())
/* 1964:     */     {
/* 1965:2942 */       insertIntoCache(t, cache);
/* 1966:2943 */       Entity x = getFromCache(t, cache);
/* 1967:2944 */       return;
/* 1968:     */     }
/* 1969:2946 */     if (t.functionP())
/* 1970:     */     {
/* 1971:2947 */       addToCompleteCache(t.getSubject(), cache);
/* 1972:     */     }
/* 1973:2949 */     else if (t.relationP())
/* 1974:     */     {
/* 1975:2950 */       addToCompleteCache(t.getSubject(), cache);
/* 1976:2951 */       addToCompleteCache(t.getObject(), cache);
/* 1977:     */     }
/* 1978:2953 */     else if (t.sequenceP())
/* 1979:     */     {
/* 1980:2954 */       for (Entity e : t.getElements()) {
/* 1981:2955 */         addToCompleteCache(e, cache);
/* 1982:     */       }
/* 1983:     */     }
/* 1984:2959 */     insertIntoCache(t, cache);
/* 1985:     */   }
/* 1986:     */   
/* 1987:     */   private boolean isCached(Entity t, HashMap<String, Entity> cache)
/* 1988:     */   {
/* 1989:2991 */     if (cache.get(hash(t)) != null) {
/* 1990:2992 */       return true;
/* 1991:     */     }
/* 1992:2994 */     return false;
/* 1993:     */   }
/* 1994:     */   
/* 1995:     */   private String hash(Entity t)
/* 1996:     */   {
/* 1997:3036 */     String result = t.hash();
/* 1998:     */     
/* 1999:3038 */     return result;
/* 2000:     */   }
/* 2001:     */   
/* 2002:     */   private boolean isAsleep()
/* 2003:     */   {
/* 2004:3043 */     return this.asleep;
/* 2005:     */   }
/* 2006:     */   
/* 2007:     */   public void setAsleep(boolean asleep)
/* 2008:     */   {
/* 2009:3047 */     this.asleep = asleep;
/* 2010:     */   }
/* 2011:     */   
/* 2012:     */   public boolean isAwake()
/* 2013:     */   {
/* 2014:3051 */     return !this.asleep;
/* 2015:     */   }
/* 2016:     */   
/* 2017:     */   public void setAwake(boolean awake)
/* 2018:     */   {
/* 2019:3055 */     this.asleep = (!awake);
/* 2020:     */   }
/* 2021:     */   
/* 2022:     */   public boolean isPlayByPlayInput()
/* 2023:     */   {
/* 2024:3059 */     return this.playByPlayInput;
/* 2025:     */   }
/* 2026:     */   
/* 2027:     */   public void setPlayByPlayInput(boolean playByPlayInput)
/* 2028:     */   {
/* 2029:3063 */     this.playByPlayInput = playByPlayInput;
/* 2030:     */   }
/* 2031:     */   
/* 2032:     */   public boolean isStoryInProgress()
/* 2033:     */   {
/* 2034:3067 */     return this.storyInProgress;
/* 2035:     */   }
/* 2036:     */   
/* 2037:     */   public void setStoryInProgress(boolean storyInProgress)
/* 2038:     */   {
/* 2039:3071 */     this.storyInProgress = storyInProgress;
/* 2040:     */   }
/* 2041:     */   
/* 2042:     */   public void setInstantiations(Object o)
/* 2043:     */   {
/* 2044:3075 */     if ((o instanceof Sequence)) {
/* 2045:3076 */       this.instantiatedConcepts = ((Sequence)o);
/* 2046:     */     }
/* 2047:3078 */     Connections.getPorts(this).transmit("instantiated reflections", this.instantiatedConcepts);
/* 2048:     */   }
/* 2049:     */   
/* 2050:     */   public void setReflectionAnalysis(Object o)
/* 2051:     */   {
/* 2052:3083 */     if (o == null) {
/* 2053:3084 */       Mark.say(new Object[] {"REFLECTION NULL" });
/* 2054:     */     }
/* 2055:3086 */     if ((o instanceof ReflectionAnalysis))
/* 2056:     */     {
/* 2057:3087 */       this.reflectionAnalysis = ((ReflectionAnalysis)o);
/* 2058:     */       
/* 2059:     */ 
/* 2060:     */ 
/* 2061:     */ 
/* 2062:     */ 
/* 2063:     */ 
/* 2064:     */ 
/* 2065:     */ 
/* 2066:3096 */       Connections.getPorts(this).transmit("reflection analysis", this.reflectionAnalysis);
/* 2067:     */     }
/* 2068:     */   }
/* 2069:     */   
/* 2070:     */   public ReflectionAnalysis getReflectionAnalysis()
/* 2071:     */   {
/* 2072:3102 */     return this.reflectionAnalysis;
/* 2073:     */   }
/* 2074:     */   
/* 2075:     */   public void setReflectionAnalysis(ReflectionAnalysis reflectionAnalysis)
/* 2076:     */   {
/* 2077:3106 */     this.reflectionAnalysis = reflectionAnalysis;
/* 2078:     */   }
/* 2079:     */   
/* 2080:     */   public Sequence getCommonsenseRules()
/* 2081:     */   {
/* 2082:3111 */     return this.rules;
/* 2083:     */   }
/* 2084:     */   
/* 2085:     */   public Sequence getConceptPatterns()
/* 2086:     */   {
/* 2087:3116 */     return this.concepts;
/* 2088:     */   }
/* 2089:     */   
/* 2090:     */   public void addConceptPatterns(Sequence concepts)
/* 2091:     */   {
/* 2092:3120 */     for (Entity s : concepts.getElements()) {
/* 2093:3121 */       addConcept((Sequence)s);
/* 2094:     */     }
/* 2095:     */   }
/* 2096:     */   
/* 2097:     */   public ArrayList<Sequence> getConcepts()
/* 2098:     */   {
/* 2099:3126 */     ArrayList<Sequence> result = new ArrayList();
/* 2100:3127 */     for (Entity t : getConceptPatterns().getElements()) {
/* 2101:3128 */       result.add((Sequence)t);
/* 2102:     */     }
/* 2103:3130 */     return result;
/* 2104:     */   }
/* 2105:     */   
/* 2106:     */   public void addConcepts(ArrayList<Sequence> concepts)
/* 2107:     */   {
/* 2108:3134 */     for (Sequence story : concepts) {
/* 2109:3135 */       addConcept(story);
/* 2110:     */     }
/* 2111:     */   }
/* 2112:     */   
/* 2113:     */   public Sequence getInstantiatedConcepts()
/* 2114:     */   {
/* 2115:3140 */     return this.instantiatedConcepts;
/* 2116:     */   }
/* 2117:     */   
/* 2118:     */   public MentalModel getMentalModel()
/* 2119:     */   {
/* 2120:3144 */     if (this.mentalModel == null) {
/* 2121:3145 */       this.mentalModel = new MentalModel(getName());
/* 2122:     */     }
/* 2123:3147 */     return this.mentalModel;
/* 2124:     */   }
/* 2125:     */   
/* 2126:     */   private void setMentalModel(MentalModel mentalModel)
/* 2127:     */   {
/* 2128:3151 */     this.mentalModel = mentalModel;
/* 2129:     */   }
/* 2130:     */   
/* 2131:     */   public ArrayList<Entity> getPredictionRules(Entity element)
/* 2132:     */   {
/* 2133:3155 */     return getPredictionRules(element, this.predictionRuleMap, true);
/* 2134:     */   }
/* 2135:     */   
/* 2136:     */   public ArrayList<Entity> getExplanationRules(Entity element)
/* 2137:     */   {
/* 2138:3159 */     return getExplanationRules(element, this.explanationRuleMap);
/* 2139:     */   }
/* 2140:     */   
/* 2141:     */   public ArrayList<Entity> getCensorRules(Entity element)
/* 2142:     */   {
/* 2143:3163 */     return getCensorRules(element, this.censorRuleMap, true);
/* 2144:     */   }
/* 2145:     */   
/* 2146:     */   public void processTraitAddition(Object o)
/* 2147:     */   {
/* 2148:3167 */     boolean debug = false;
/* 2149:3168 */     if ((o instanceof BetterSignal))
/* 2150:     */     {
/* 2151:3169 */       BetterSignal signal = (BetterSignal)o;
/* 2152:3170 */       if (signal.get(0, String.class) == "deliver trait-specific concepts")
/* 2153:     */       {
/* 2154:3171 */         for (Entity concept : ((Sequence)signal.get(1, Sequence.class)).getElements()) {
/* 2155:3172 */           if (!this.concepts.contains(concept))
/* 2156:     */           {
/* 2157:3173 */             Mark.say(new Object[] {Boolean.valueOf(debug), "Adding concept", concept.asString() });
/* 2158:3174 */             this.concepts.addElement(concept);
/* 2159:     */           }
/* 2160:     */           else
/* 2161:     */           {
/* 2162:3177 */             Mark.say(new Object[] {Boolean.valueOf(debug), "Already have", concept.asString() });
/* 2163:     */           }
/* 2164:     */         }
/* 2165:     */       }
/* 2166:3181 */       else if (signal.get(0, String.class) == "deliver trait characteization")
/* 2167:     */       {
/* 2168:3182 */         Entity entity = (Entity)signal.get(1, Entity.class);
/* 2169:3183 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Delivering", entity });
/* 2170:3184 */         processElement(entity);
/* 2171:     */       }
/* 2172:     */     }
/* 2173:     */   }
/* 2174:     */   
/* 2175:     */   public void processInstantiatedConcepts(Object o)
/* 2176:     */   {
/* 2177:3190 */     if ((o instanceof Sequence)) {
/* 2178:3190 */       setInstantiatedConceptPatterns((Sequence)o);
/* 2179:     */     }
/* 2180:     */   }
/* 2181:     */   
/* 2182:     */   public void setInstantiatedConceptPatterns(Sequence instantiatedConceptPatterns)
/* 2183:     */   {
/* 2184:3194 */     this.instantiatedConceptPatterns = instantiatedConceptPatterns;
/* 2185:     */   }
/* 2186:     */   
/* 2187:     */   public Sequence getInstantiatedConceptPatterns()
/* 2188:     */   {
/* 2189:3198 */     return this.instantiatedConceptPatterns;
/* 2190:     */   }
/* 2191:     */   
/* 2192:     */   private boolean augmentCurrentSceneIfNotInAnyScene(Entity element)
/* 2193:     */   {
/* 2194:3204 */     if (!isInAnyScene(element))
/* 2195:     */     {
/* 2196:3205 */       addToCurrentScene(element);
/* 2197:     */       
/* 2198:3207 */       return true;
/* 2199:     */     }
/* 2200:3209 */     return false;
/* 2201:     */   }
/* 2202:     */   
/* 2203:     */   private boolean isInAnyScene(Entity element)
/* 2204:     */   {
/* 2205:3213 */     for (HashMap<String, Entity> map : getSceneMapList()) {
/* 2206:3214 */       if (isCached(element, map)) {
/* 2207:3215 */         return true;
/* 2208:     */       }
/* 2209:     */     }
/* 2210:3218 */     return false;
/* 2211:     */   }
/* 2212:     */   
/* 2213:     */   private boolean augmentCurrentSceneIfNotInCurrentScene(Entity element)
/* 2214:     */   {
/* 2215:3222 */     if (!isInCurrentScene(element))
/* 2216:     */     {
/* 2217:3223 */       element = addToCurrentScene(element);
/* 2218:     */       
/* 2219:3225 */       return true;
/* 2220:     */     }
/* 2221:3227 */     return false;
/* 2222:     */   }
/* 2223:     */   
/* 2224:     */   private Entity addToCurrentScene(Entity element)
/* 2225:     */   {
/* 2226:3232 */     if (element.isA("want"))
/* 2227:     */     {
/* 2228:3233 */       String hash = hash(Getters.getObject(element));
/* 2229:3234 */       getWantCache().put(hash, element);
/* 2230:     */     }
/* 2231:     */     else
/* 2232:     */     {
/* 2233:3237 */       String hash = hash(element);
/* 2234:3238 */       Entity want = (Entity)getWantCache().get(hash);
/* 2235:3239 */       if (want != null) {
/* 2236:3240 */         Getters.replaceObject(want, element);
/* 2237:     */       }
/* 2238:     */     }
/* 2239:3247 */     insertIntoCache(element, getCurrentSceneMap());
/* 2240:3248 */     this.story.addElement(element);
/* 2241:3249 */     return element;
/* 2242:     */   }
/* 2243:     */   
/* 2244:     */   public boolean isInCurrentScene(Entity e)
/* 2245:     */   {
/* 2246:3253 */     return isCached(e, getCurrentSceneMap());
/* 2247:     */   }
/* 2248:     */   
/* 2249:     */   public void addSceneMap()
/* 2250:     */   {
/* 2251:3258 */     getSceneMapList().add(0, new HashMap());
/* 2252:     */   }
/* 2253:     */   
/* 2254:     */   public HashMap<String, Entity> getCurrentSceneMap()
/* 2255:     */   {
/* 2256:3262 */     if (getSceneMapList().isEmpty()) {
/* 2257:3263 */       addSceneMap();
/* 2258:     */     }
/* 2259:3265 */     return (HashMap)getSceneMapList().get(0);
/* 2260:     */   }
/* 2261:     */   
/* 2262:     */   public ArrayList<HashMap<String, Entity>> getSceneMapList()
/* 2263:     */   {
/* 2264:3269 */     if (this.sceneMapList == null) {
/* 2265:3270 */       this.sceneMapList = new ArrayList();
/* 2266:     */     }
/* 2267:3272 */     return this.sceneMapList;
/* 2268:     */   }
/* 2269:     */   
/* 2270:     */   public HashMap<String, Entity> getGlobalCache()
/* 2271:     */   {
/* 2272:3276 */     if (this.globalCache == null) {
/* 2273:3277 */       this.globalCache = new LinkedHashMap();
/* 2274:     */     }
/* 2275:3279 */     return this.globalCache;
/* 2276:     */   }
/* 2277:     */   
/* 2278:     */   public HashMap<String, Entity> getMentalModelCache()
/* 2279:     */   {
/* 2280:3283 */     if (this.mentalModelCache == null) {
/* 2281:3284 */       this.mentalModelCache = new LinkedHashMap();
/* 2282:     */     }
/* 2283:3286 */     return this.mentalModelCache;
/* 2284:     */   }
/* 2285:     */   
/* 2286:     */   public HashMap<String, Entity> getWantCache()
/* 2287:     */   {
/* 2288:3290 */     if (this.wantCache == null) {
/* 2289:3291 */       this.wantCache = new LinkedHashMap();
/* 2290:     */     }
/* 2291:3293 */     return this.wantCache;
/* 2292:     */   }
/* 2293:     */   
/* 2294:     */   private Entity makeNewVersion(Entity element)
/* 2295:     */   {
/* 2296:3297 */     boolean debug = false;
/* 2297:3298 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Working on replacement of", element.asStringWithIndexes() });
/* 2298:3299 */     if (element.functionP())
/* 2299:     */     {
/* 2300:3300 */       element = ((Function)element).rebuild();
/* 2301:     */     }
/* 2302:3302 */     else if (element.relationP())
/* 2303:     */     {
/* 2304:3303 */       element = ((Relation)element).rebuild();
/* 2305:     */     }
/* 2306:3306 */     else if (element.sequenceP())
/* 2307:     */     {
/* 2308:3307 */       Vector<Entity> elements = element.getElements();
/* 2309:3308 */       element = ((Sequence)element).rebuildWithoutElements();
/* 2310:3309 */       for (Entity x : elements) {
/* 2311:3310 */         element.addElement(x);
/* 2312:     */       }
/* 2313:     */     }
/* 2314:3313 */     insertIntoCache(element, getGlobalCache());
/* 2315:3314 */     return element;
/* 2316:     */   }
/* 2317:     */   
/* 2318:     */   public Entity reassembleAndDereference(Entity element)
/* 2319:     */   {
/* 2320:3318 */     getGlobalCache().isEmpty();
/* 2321:     */     
/* 2322:     */ 
/* 2323:     */ 
/* 2324:     */ 
/* 2325:     */ 
/* 2326:     */ 
/* 2327:     */ 
/* 2328:     */ 
/* 2329:3327 */     return reassemble(element, getGlobalCache());
/* 2330:     */   }
/* 2331:     */   
/* 2332:     */   private Entity reassemble(Entity element, HashMap<String, Entity> map)
/* 2333:     */   {
/* 2334:3331 */     boolean debug = false;
/* 2335:3332 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Working on replacement of", element.asStringWithIndexes() });
/* 2336:3333 */     if (isCached(element, map))
/* 2337:     */     {
/* 2338:3334 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Got from cache" });
/* 2339:3335 */       return getFromCache(element, map);
/* 2340:     */     }
/* 2341:3337 */     if (element.functionP())
/* 2342:     */     {
/* 2343:3338 */       element = ((Function)element).rebuild();
/* 2344:3339 */       element.setSubject(reassemble(element.getSubject(), map));
/* 2345:3340 */       insertIntoCache(element, map);
/* 2346:     */     }
/* 2347:3342 */     else if (element.relationP())
/* 2348:     */     {
/* 2349:3343 */       element = ((Relation)element).rebuild();
/* 2350:3344 */       element.setSubject(reassemble(element.getSubject(), map));
/* 2351:3345 */       element.setObject(reassemble(element.getObject(), map));
/* 2352:3346 */       insertIntoCache(element, map);
/* 2353:     */     }
/* 2354:3348 */     else if (element.sequenceP())
/* 2355:     */     {
/* 2356:3349 */       Vector<Entity> elements = element.getElements();
/* 2357:3350 */       element = ((Sequence)element).rebuildWithoutElements();
/* 2358:3351 */       for (Entity x : elements) {
/* 2359:3352 */         element.addElement(reassemble(x, map));
/* 2360:     */       }
/* 2361:3354 */       insertIntoCache(element, map);
/* 2362:     */     }
/* 2363:3356 */     return element;
/* 2364:     */   }
/* 2365:     */   
/* 2366:     */   public boolean isInert()
/* 2367:     */   {
/* 2368:3365 */     return this.inert;
/* 2369:     */   }
/* 2370:     */   
/* 2371:     */   public void setInert(boolean inert)
/* 2372:     */   {
/* 2373:3369 */     this.inert = inert;
/* 2374:     */   }
/* 2375:     */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     storyProcessor.StoryProcessor
 * JD-Core Version:    0.7.0.1
 */