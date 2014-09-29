/*    1:     */ package silaSayan;
/*    2:     */ 
/*    3:     */ import Signals.BetterSignal;
/*    4:     */ import bridge.reps.entities.Entity;
/*    5:     */ import bridge.reps.entities.Sequence;
/*    6:     */ import connections.AbstractWiredBox;
/*    7:     */ import connections.Connections;
/*    8:     */ import connections.Ports;
/*    9:     */ import genesis.GenesisMenus;
/*   10:     */ import java.util.ArrayList;
/*   11:     */ import java.util.Collections;
/*   12:     */ import java.util.Comparator;
/*   13:     */ import java.util.HashMap;
/*   14:     */ import java.util.HashSet;
/*   15:     */ import java.util.Iterator;
/*   16:     */ import java.util.LinkedList;
/*   17:     */ import java.util.List;
/*   18:     */ import java.util.Map;
/*   19:     */ import java.util.Map.Entry;
/*   20:     */ import java.util.Set;
/*   21:     */ import java.util.TreeMap;
/*   22:     */ import java.util.TreeSet;
/*   23:     */ import java.util.Vector;
/*   24:     */ import java.util.regex.Matcher;
/*   25:     */ import java.util.regex.Pattern;
/*   26:     */ import javax.swing.JRadioButton;
/*   27:     */ import matchers.StandardMatcher;
/*   28:     */ import minilisp.LList;
/*   29:     */ import parameters.Radio;
/*   30:     */ import start.Generator;
/*   31:     */ import storyProcessor.ReflectionAnalysis;
/*   32:     */ import storyProcessor.ReflectionDescription;
/*   33:     */ import text.Html;
/*   34:     */ import translator.Translator;
/*   35:     */ import utils.Mark;
/*   36:     */ import utils.PairOfEntities;
/*   37:     */ 
/*   38:     */ public class StoryTeller
/*   39:     */   extends AbstractWiredBox
/*   40:     */ {
/*   41:  28 */   private boolean isItFullStory = true;
/*   42:  32 */   private boolean debug = false;
/*   43:     */   public static final String QUIESCENCE_PORT1 = "quiescence port 1";
/*   44:     */   public static final String QUIESCENCE_PORT2 = "quiescence port 2";
/*   45:     */   public static final String RULE_PORT = "rules";
/*   46:     */   public static final String REFLECTION_PORT1 = "reflection port 1";
/*   47:     */   public static final String REFLECTION_PORT2 = "reflection port 2";
/*   48:     */   public static final String TEACHER_INFERENCES = "teacher inferences";
/*   49:     */   public static final String STUDENT_INFERENCES = "inferences";
/*   50:     */   public static final String INCREMENT = "increment";
/*   51:     */   public static final String STAGE_DIRECTION_PORT = "stage direction port";
/*   52:  54 */   public static String TEACH_RULE_PORT = "teach rule port";
/*   53:  56 */   public static String NEW_RULE_MESSENGER_PORT = "new rule messenger port";
/*   54:  58 */   public static String INSTANTIATED_REFLECTIONS = "instantiated reflections";
/*   55:  60 */   public static String COMPLETE_STORY = "complete story";
/*   56:  62 */   public static String REFLECTION_ANALYSIS = "reflection analysis";
/*   57:  64 */   public static String PLOT_PORT = "plot of story";
/*   58:  66 */   public static String EXPLICIT_STORY = "explicit story";
/*   59:  68 */   public static String CLEAR = "clear";
/*   60:  70 */   public static String FROM_SUMMARY_HELPER = "from summary helper";
/*   61:  75 */   private String[][] userGoalArray = { { "CONCEPT_CENTRIC", "revenge" }, { "CHARACTER_CENTRIC", "favor Macbeth" } };
/*   62:     */   private String[] intendedConcepts;
/*   63:     */   private String focalCharacter;
/*   64:     */   private String intendedDisposition;
/*   65:  86 */   private boolean gotWholeStory = false;
/*   66:  89 */   private boolean narrationDone = false;
/*   67:  91 */   private ArrayList<Entity> unmatchedList = new ArrayList();
/*   68: 101 */   private boolean isOneQuiet = false;
/*   69: 104 */   private boolean isTwoQuiet = false;
/*   70: 107 */   private Sequence quietIntervalOne = new Sequence();
/*   71: 111 */   private Sequence quietIntervalTwo = new Sequence();
/*   72: 115 */   private Sequence studentPerspectiveUnderstanding = new Sequence();
/*   73: 117 */   private Sequence studentInferences = new Sequence();
/*   74: 119 */   private Sequence teacherInferences = new Sequence();
/*   75: 121 */   private Sequence rules = new Sequence();
/*   76: 123 */   private Sequence rulesAlreadyReported = new Sequence();
/*   77: 132 */   private Sequence reflectionRules = new Sequence();
/*   78: 135 */   private Sequence relevantRules = new Sequence();
/*   79: 140 */   private Sequence missingRulesToCompareToReflectionRules = new Sequence();
/*   80: 145 */   private Sequence completeStory = new Sequence();
/*   81: 147 */   private Set<Object> completeStorySet = new HashSet();
/*   82: 149 */   private Sequence storySummarized = new Sequence();
/*   83: 151 */   private ArrayList<Entity> reflectionParts = new ArrayList();
/*   84: 153 */   private Sequence storyReflections = new Sequence();
/*   85:     */   private ReflectionAnalysis reflectionAnalysis;
/*   86: 157 */   private Sequence explicitStory = new Sequence();
/*   87: 159 */   private ArrayList<String> storyPlotStringRep = new ArrayList();
/*   88: 161 */   private LinkedList<Map.Entry<Entity, Integer>> finalSummary = new LinkedList();
/*   89:     */   public static String conceptBeingSummarized;
/*   90: 165 */   private LinkedList<ArrayList<Object>> storyAndComprehensionCompilation = new LinkedList();
/*   91: 167 */   private boolean recordToCompilation = false;
/*   92: 169 */   private boolean startIncrement = false;
/*   93: 171 */   Sequence inferencesInSummary = new Sequence();
/*   94: 174 */   private Map<String, ArrayList<Object>> orderingMap = new HashMap();
/*   95: 176 */   int orderCount = 0;
/*   96:     */   
/*   97:     */   public StoryTeller()
/*   98:     */   {
/*   99: 181 */     setName("My story processor");
/*  100:     */     
/*  101:     */ 
/*  102: 184 */     Connections.getPorts(this).addSignalProcessor("quiescence port 1", "processQuiescence1");
/*  103: 185 */     Connections.getPorts(this).addSignalProcessor("quiescence port 2", "processQuiescence2");
/*  104:     */     
/*  105:     */ 
/*  106:     */ 
/*  107: 189 */     Connections.getPorts(this).addSignalProcessor("rules", "processRules");
/*  108:     */     
/*  109:     */ 
/*  110:     */ 
/*  111: 193 */     Connections.getPorts(this).addSignalProcessor("reflection port 1", "processReflectionsForRules1");
/*  112:     */     
/*  113: 195 */     Connections.getPorts(this).addSignalProcessor(INSTANTIATED_REFLECTIONS, "storeReflections");
/*  114:     */     
/*  115: 197 */     Connections.getPorts(this).addSignalProcessor(COMPLETE_STORY, "setCompleteStory");
/*  116:     */     
/*  117: 199 */     Connections.getPorts(this).addSignalProcessor(REFLECTION_ANALYSIS, "processReflectionAnalysis");
/*  118:     */     
/*  119: 201 */     Connections.getPorts(this).addSignalProcessor(PLOT_PORT, "setPlot");
/*  120:     */     
/*  121: 203 */     Connections.getPorts(this).addSignalProcessor(EXPLICIT_STORY, "setExplicitStory");
/*  122:     */     
/*  123: 205 */     Connections.getPorts(this).addSignalProcessor("inferences", "processStudentInferences");
/*  124: 206 */     Connections.getPorts(this).addSignalProcessor("teacher inferences", "processTeacherInferences");
/*  125:     */     
/*  126: 208 */     Connections.getPorts(this).addSignalProcessor("stage direction port", "processStageDirections");
/*  127: 209 */     Connections.getPorts(this).addSignalProcessor(FROM_SUMMARY_HELPER, "processSummaryHelper");
/*  128:     */   }
/*  129:     */   
/*  130:     */   public void goalStrategyPicker(String[][] userGoalArray)
/*  131:     */   {
/*  132: 215 */     Mark.say(
/*  133:     */     
/*  134:     */ 
/*  135:     */ 
/*  136:     */ 
/*  137:     */ 
/*  138:     */ 
/*  139:     */ 
/*  140:     */ 
/*  141:     */ 
/*  142:     */ 
/*  143:     */ 
/*  144:     */ 
/*  145:     */ 
/*  146:     */ 
/*  147:     */ 
/*  148:     */ 
/*  149:     */ 
/*  150:     */ 
/*  151:     */ 
/*  152:     */ 
/*  153:     */ 
/*  154:     */ 
/*  155:     */ 
/*  156:     */ 
/*  157:     */ 
/*  158:     */ 
/*  159:     */ 
/*  160:     */ 
/*  161:     */ 
/*  162:     */ 
/*  163:     */ 
/*  164:     */ 
/*  165:     */ 
/*  166:     */ 
/*  167:     */ 
/*  168:     */ 
/*  169:     */ 
/*  170:     */ 
/*  171:     */ 
/*  172:     */ 
/*  173: 256 */       new Object[] { "IN NEW FUNCTION GOAL STRATEGY PICKER!!!!!!!!!!!!!" });
/*  174: 217 */     for (String[] goalTypeTuple : userGoalArray)
/*  175:     */     {
/*  176: 218 */       String userGoalType = goalTypeTuple[0];
/*  177: 219 */       Mark.say(new Object[] {"userGoalType: ", userGoalType });
/*  178: 220 */       String userGoal = goalTypeTuple[1];
/*  179: 221 */       Mark.say(new Object[] {"userGoal: ", userGoal });
/*  180: 223 */       if (userGoalType == "CONGRUENCE") {
/*  181: 224 */         return;
/*  182:     */       }
/*  183: 226 */       if (userGoalType == "CHARACTER_CENTRIC")
/*  184:     */       {
/*  185: 227 */         String[] goalParts = userGoal.split("\\s");
/*  186:     */         
/*  187: 229 */         this.intendedDisposition = goalParts[0];
/*  188: 230 */         Mark.say(new Object[] {"intended Disposition: ", this.intendedDisposition, "goalParts[0]: ", goalParts[0] });
/*  189: 231 */         this.focalCharacter = goalParts[1];
/*  190: 232 */         Mark.say(new Object[] {"focalCharacter: ", this.focalCharacter, "goalParts[1]: ", goalParts[1] });
/*  191:     */       }
/*  192:     */       else
/*  193:     */       {
/*  194:     */         String[] goalParts;
/*  195: 234 */         if (userGoalType == "CONCEPT_CENTRIC")
/*  196:     */         {
/*  197: 235 */           Mark.say(new Object[] {"   PROFVIDED GOAL CONCEPT CENTRIC" });
/*  198: 236 */           Mark.say(new Object[] {"UserGoal is: ", userGoal });
/*  199: 237 */           goalParts = userGoal.split(",");
/*  200: 238 */           int i = 0;
/*  201: 239 */           String primingMessage = "This is a story about";
/*  202: 240 */           while (i < goalParts.length)
/*  203:     */           {
/*  204: 242 */             primingMessage = primingMessage + " " + goalParts[i];
/*  205: 243 */             i++;
/*  206:     */           }
/*  207: 245 */           primingMessage = primingMessage + ". ";
/*  208: 246 */           String primingMessageFormatted = Html.green(primingMessage);
/*  209: 247 */           Mark.say(new Object[] {primingMessage });
/*  210: 248 */           BetterSignal signal = new BetterSignal(new Object[] { "Story teller", primingMessageFormatted });
/*  211: 249 */           Connections.getPorts(this).transmit(signal);
/*  212:     */         }
/*  213: 251 */         else if (userGoalType == "MORALISTIC")
/*  214:     */         {
/*  215: 252 */           goalParts = userGoal.split("\\s");
/*  216:     */         }
/*  217:     */       }
/*  218:     */     }
/*  219:     */   }
/*  220:     */   
/*  221:     */   public void processSummaryHelper(BetterSignal o)
/*  222:     */   {
/*  223: 259 */     if (!Radio.tellStoryButton.isSelected()) {
/*  224: 260 */       return;
/*  225:     */     }
/*  226: 263 */     Mark.say(new Object[] {"PROCESSING SUMMARY HELPER" });
/*  227: 264 */     if ((o instanceof BetterSignal))
/*  228:     */     {
/*  229: 265 */       Mark.say(new Object[] {"Got BetterSignal" });
/*  230: 266 */       BetterSignal signal = o;
/*  231: 267 */       Connections.getPorts(this).transmit(signal);
/*  232:     */     }
/*  233:     */   }
/*  234:     */   
/*  235:     */   public void conceptSummary(ReflectionAnalysis reflectionAnalysis, boolean ready)
/*  236:     */     throws Exception
/*  237:     */   {
/*  238: 272 */     if (!Radio.tellStoryButton.isSelected()) {
/*  239: 273 */       return;
/*  240:     */     }
/*  241: 275 */     if (!ready) {
/*  242: 276 */       return;
/*  243:     */     }
/*  244: 278 */     Sequence conceptSummary = new Sequence();
/*  245: 279 */     ReflectionDescription description = (ReflectionDescription)reflectionAnalysis.getReflectionDescriptions().get(0);
/*  246:     */     
/*  247: 281 */     conceptBeingSummarized = description.getName();
/*  248: 282 */     String intro = "From the perspective of " + conceptBeingSummarized + ": ";
/*  249: 283 */     BetterSignal signal = new BetterSignal(new Object[] { conceptBeingSummarized, intro });
/*  250: 284 */     Connections.getPorts(this).transmit(signal);
/*  251:     */     
/*  252: 286 */     conceptSummary = conceptSummaryWrapper(description, this.completeStory);
/*  253:     */     
/*  254: 288 */     Sequence conceptSummaryPart = divideSummary(conceptSummary);
/*  255: 289 */     if (GenesisMenus.getSpoonFeedButton().isSelected())
/*  256:     */     {
/*  257: 290 */       Mark.say(new Object[] {"CALLED SPOONFEED SUMMARY" });
/*  258: 291 */       spoonfeedSummary(conceptSummaryPart);
/*  259:     */     }
/*  260: 293 */     if (GenesisMenus.getPrimingButton().isSelected()) {
/*  261: 294 */       justifySummary(conceptSummaryPart);
/*  262:     */     }
/*  263: 296 */     if (GenesisMenus.getPrimingWithIntrospectionButton().isSelected()) {
/*  264: 297 */       teachSummary(conceptSummaryPart);
/*  265:     */     }
/*  266:     */   }
/*  267:     */   
/*  268:     */   public Sequence conceptSummaryWrapper(ReflectionDescription concept, Sequence searchSpace)
/*  269:     */   {
/*  270: 304 */     if (!Radio.tellStoryButton.isSelected()) {
/*  271: 305 */       return null;
/*  272:     */     }
/*  273: 307 */     if (!this.gotWholeStory)
/*  274:     */     {
/*  275: 308 */       Mark.say(new Object[] {"NULLLL!!!:  WASN'T READY YET" });
/*  276: 309 */       return null;
/*  277:     */     }
/*  278: 311 */     SummaryHelper SummaryHelper = new SummaryHelper();
/*  279:     */     
/*  280:     */ 
/*  281: 314 */     Sequence miniSummary = SummaryHelper.filterSummary(concept);
/*  282:     */     
/*  283:     */ 
/*  284: 317 */     return miniSummary;
/*  285:     */   }
/*  286:     */   
/*  287:     */   public void clearMemories()
/*  288:     */   {
/*  289: 321 */     if (!Radio.tellStoryButton.isSelected()) {
/*  290: 322 */       return;
/*  291:     */     }
/*  292: 325 */     this.gotWholeStory = false;
/*  293: 326 */     this.narrationDone = false;
/*  294: 327 */     this.unmatchedList.clear();
/*  295:     */     
/*  296: 329 */     this.isOneQuiet = false;
/*  297:     */     
/*  298:     */ 
/*  299: 332 */     this.isTwoQuiet = false;
/*  300:     */     
/*  301:     */ 
/*  302: 335 */     this.quietIntervalOne.clearElements();
/*  303: 336 */     this.quietIntervalTwo.clearElements();
/*  304:     */     
/*  305: 338 */     this.studentPerspectiveUnderstanding.clearElements();
/*  306:     */     
/*  307: 340 */     this.studentInferences.clearElements();
/*  308: 341 */     this.teacherInferences.clearElements();
/*  309:     */     
/*  310: 343 */     this.rules.clearElements();
/*  311:     */     
/*  312: 345 */     this.rulesAlreadyReported.clearElements();
/*  313: 346 */     this.reflectionRules.clearElements();
/*  314: 347 */     this.relevantRules.clearElements();
/*  315: 348 */     this.missingRulesToCompareToReflectionRules.clearElements();
/*  316: 349 */     this.completeStory.clearElements();
/*  317: 350 */     this.completeStorySet.clear();
/*  318: 351 */     this.storySummarized.clearElements();
/*  319: 352 */     this.reflectionParts.clear();
/*  320: 353 */     this.storyReflections.clearElements();
/*  321: 354 */     this.explicitStory.clearElements();
/*  322: 355 */     this.storyPlotStringRep.clear();
/*  323: 356 */     this.finalSummary.clear();
/*  324: 357 */     this.recordToCompilation = false;
/*  325: 358 */     this.storyAndComprehensionCompilation.clear();
/*  326: 359 */     this.startIncrement = false;
/*  327:     */     
/*  328: 361 */     this.orderingMap.clear();
/*  329: 362 */     this.orderCount = 0;
/*  330:     */     
/*  331: 364 */     this.inferencesInSummary.clearElements();
/*  332: 365 */     Connections.getPorts(this).transmit(CLEAR, "clear");
/*  333:     */   }
/*  334:     */   
/*  335:     */   public void processStageDirections(Object o)
/*  336:     */   {
/*  337: 369 */     if (!Radio.tellStoryButton.isSelected()) {
/*  338: 370 */       return;
/*  339:     */     }
/*  340: 373 */     if (!(o instanceof String)) {
/*  341: 374 */       return;
/*  342:     */     }
/*  343: 376 */     String s = (String)o;
/*  344: 377 */     if (s == "reset")
/*  345:     */     {
/*  346: 378 */       Mark.say(new Object[] {"Clearing memories now!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" });
/*  347: 379 */       clearMemories();
/*  348:     */     }
/*  349:     */   }
/*  350:     */   
/*  351:     */   public void setExplicitStory(Object o)
/*  352:     */   {
/*  353: 384 */     if (!Radio.tellStoryButton.isSelected()) {
/*  354: 385 */       return;
/*  355:     */     }
/*  356: 387 */     Mark.say(new Object[] {"Trying to setExplicitStory" });
/*  357: 389 */     if ((o instanceof Sequence)) {
/*  358: 390 */       this.explicitStory = ((Sequence)o);
/*  359:     */     }
/*  360:     */   }
/*  361:     */   
/*  362:     */   public void processStudentInferences(Object o)
/*  363:     */   {
/*  364: 401 */     if (!Radio.tellStoryButton.isSelected()) {
/*  365: 402 */       return;
/*  366:     */     }
/*  367: 405 */     if ((o instanceof Sequence))
/*  368:     */     {
/*  369: 406 */       this.studentInferences = ((Sequence)o);
/*  370: 407 */       this.studentInferences.getElements().isEmpty();
/*  371:     */       Entity localEntity;
/*  372: 410 */       for (Iterator localIterator = this.studentInferences.getElements().iterator(); localIterator.hasNext(); localEntity = (Entity)localIterator.next()) {}
/*  373:     */     }
/*  374:     */   }
/*  375:     */   
/*  376:     */   public void processTeacherInferences(Object o)
/*  377:     */   {
/*  378: 418 */     if (!Radio.tellStoryButton.isSelected()) {
/*  379: 419 */       return;
/*  380:     */     }
/*  381: 422 */     if ((o instanceof Sequence))
/*  382:     */     {
/*  383: 423 */       this.teacherInferences = ((Sequence)o);
/*  384: 424 */       this.teacherInferences.getElements().isEmpty();
/*  385:     */       Entity localEntity;
/*  386: 427 */       for (Iterator localIterator = this.teacherInferences.getElements().iterator(); localIterator.hasNext(); localEntity = (Entity)localIterator.next()) {}
/*  387:     */     }
/*  388:     */   }
/*  389:     */   
/*  390:     */   public void setPlot(Object sig)
/*  391:     */     throws Exception
/*  392:     */   {
/*  393: 435 */     if (!Radio.tellStoryButton.isSelected()) {
/*  394: 436 */       return;
/*  395:     */     }
/*  396: 438 */     if ((sig instanceof BetterSignal))
/*  397:     */     {
/*  398: 439 */       BetterSignal signal = (BetterSignal)sig;
/*  399:     */       
/*  400: 441 */       String plotString = (String)signal.get(1, String.class);
/*  401: 442 */       this.storyPlotStringRep.add(plotString);
/*  402:     */     }
/*  403:     */   }
/*  404:     */   
/*  405:     */   public void setCompleteStory(Object o)
/*  406:     */   {
/*  407: 449 */     if (!Radio.tellStoryButton.isSelected()) {
/*  408: 450 */       return;
/*  409:     */     }
/*  410: 452 */     Mark.say(new Object[] {"IN SETCOMPLETESTORY" });
/*  411: 453 */     if ((o instanceof Sequence))
/*  412:     */     {
/*  413: 454 */       this.completeStory = ((Sequence)o);
/*  414: 456 */       for (Entity t : this.completeStory.getAllComponents()) {
/*  415: 457 */         this.completeStorySet.add(t);
/*  416:     */       }
/*  417: 460 */       this.gotWholeStory = true;
/*  418:     */     }
/*  419:     */   }
/*  420:     */   
/*  421:     */   public void orderSummary2(List<SortableThing> unordered)
/*  422:     */   {
/*  423: 466 */     if (!Radio.tellStoryButton.isSelected()) {
/*  424: 467 */       return;
/*  425:     */     }
/*  426: 469 */     Collections.sort(unordered, new OrderingComparator());
/*  427:     */   }
/*  428:     */   
/*  429:     */   class OrderingComparator
/*  430:     */     implements Comparator<StoryTeller.SortableThing>
/*  431:     */   {
/*  432:     */     OrderingComparator() {}
/*  433:     */     
/*  434:     */     public int compare(StoryTeller.SortableThing o1, StoryTeller.SortableThing o2)
/*  435:     */     {
/*  436: 476 */       if (o1.index < o2.index) {
/*  437: 477 */         return -1;
/*  438:     */       }
/*  439: 479 */       if (o1.index > o2.index) {
/*  440: 480 */         return 1;
/*  441:     */       }
/*  442: 482 */       return 0;
/*  443:     */     }
/*  444:     */   }
/*  445:     */   
/*  446:     */   class SortableThing
/*  447:     */   {
/*  448:     */     int index;
/*  449:     */     Entity thing;
/*  450:     */     String type;
/*  451:     */     
/*  452:     */     public SortableThing(int index, Entity thing, String type)
/*  453:     */     {
/*  454: 495 */       this.index = index;
/*  455: 496 */       this.thing = thing;
/*  456: 497 */       this.type = type;
/*  457:     */     }
/*  458:     */   }
/*  459:     */   
/*  460:     */   public void orderSummary(Sequence unordered)
/*  461:     */   {
/*  462: 503 */     if (!Radio.tellStoryButton.isSelected()) {
/*  463: 504 */       return;
/*  464:     */     }
/*  465: 507 */     LinkedList<ArrayList<Object>> elementTypeList = new LinkedList();
/*  466: 508 */     Map<Integer, ArrayList<Object>> mapToOrder = new HashMap();
/*  467: 509 */     StandardMatcher matcher = StandardMatcher.getBasicMatcher();
/*  468: 510 */     Mark.say(new Object[] {"orderingMap size: ", Integer.valueOf(this.orderingMap.size()) });
/*  469: 511 */     Mark.say(new Object[] {"Unordered summary elements: " });
/*  470: 512 */     for (String t : this.orderingMap.keySet())
/*  471:     */     {
/*  472: 513 */       Mark.say(new Object[] {"KEY: ", t });
/*  473: 514 */       Mark.say(new Object[] {"VALUE: ", this.orderingMap.get(t) });
/*  474:     */     }
/*  475:     */     String hashedElement;
/*  476: 516 */     for (Entity t : unordered.getAllComponents())
/*  477:     */     {
/*  478: 518 */       hashedElement = t.hash();
/*  479: 519 */       Mark.say(new Object[] {"HASH: ", hashedElement });
/*  480: 520 */       if (this.orderingMap.containsKey(hashedElement))
/*  481:     */       {
/*  482: 521 */         int orderNumber = ((Integer)((ArrayList)this.orderingMap.get(hashedElement)).get(0)).intValue();
/*  483: 522 */         ArrayList<Object> elementTypeTuple = new ArrayList();
/*  484: 523 */         String type = (String)((ArrayList)this.orderingMap.get(hashedElement)).get(1);
/*  485: 524 */         elementTypeTuple.add(0, t);
/*  486: 525 */         elementTypeTuple.add(1, type);
/*  487: 526 */         Mark.say(new Object[] {"FOUND IT!!! ", Integer.valueOf(orderNumber) });
/*  488: 527 */         mapToOrder.put(Integer.valueOf(orderNumber), elementTypeTuple);
/*  489:     */       }
/*  490:     */     }
/*  491: 531 */     Mark.say(new Object[] {"mapToOrder size: ", Integer.valueOf(mapToOrder.size()) });
/*  492: 532 */     TreeSet<Integer> keySet = new TreeSet(mapToOrder.keySet());
/*  493: 533 */     Mark.say(new Object[] {"keySet size: ", Integer.valueOf(keySet.size()) });
/*  494: 534 */     for (Integer key : keySet)
/*  495:     */     {
/*  496: 535 */       ArrayList<Object> elementAndType = (ArrayList)mapToOrder.get(key);
/*  497: 536 */       elementTypeList.add(elementAndType);
/*  498:     */     }
/*  499: 541 */     makeSummaryPretty(elementTypeList);
/*  500:     */   }
/*  501:     */   
/*  502:     */   public void putSummaryIntoOrder(ArrayList<ArrayList<Object>> unordered)
/*  503:     */   {
/*  504: 556 */     if (!Radio.tellStoryButton.isSelected()) {
/*  505: 557 */       return;
/*  506:     */     }
/*  507: 559 */     SummaryHelper SummaryHelper = new SummaryHelper();
/*  508: 561 */     if (unordered.isEmpty())
/*  509:     */     {
/*  510: 562 */       Mark.say(new Object[] {"Map is empty!" });
/*  511: 563 */       return;
/*  512:     */     }
/*  513: 566 */     ArrayList<ArrayList<Object>> map = new ArrayList();
/*  514: 568 */     for (ArrayList<Object> l : unordered)
/*  515:     */     {
/*  516: 569 */       Entity key = (Entity)l.get(0);
/*  517: 570 */       int value = ((Integer)l.get(1)).intValue();
/*  518: 572 */       if ((this.completeStory.containsDeprecated(key)) && (value != 2))
/*  519:     */       {
/*  520: 573 */         int index = key.getID();
/*  521: 574 */         ArrayList<Object> m = new ArrayList();
/*  522: 575 */         m.add(0, Double.valueOf(index));
/*  523: 576 */         m.add(1, key);
/*  524: 577 */         m.add(2, Integer.valueOf(value));
/*  525: 578 */         map.add(m);
/*  526:     */       }
/*  527: 580 */       else if (value == 2)
/*  528:     */       {
/*  529: 582 */         int preindex = key.getID();
/*  530:     */         
/*  531: 584 */         double index = preindex;
/*  532: 585 */         index += 0.1D;
/*  533:     */         
/*  534:     */ 
/*  535: 588 */         ArrayList<Object> m = new ArrayList();
/*  536: 589 */         m.add(0, Double.valueOf(index));
/*  537: 590 */         m.add(1, key);
/*  538: 591 */         m.add(2, Integer.valueOf(value));
/*  539: 592 */         map.add(m);
/*  540:     */       }
/*  541:     */     }
/*  542: 596 */     LinkedList<ArrayList<Object>> sortedSummary = SummaryHelper.summarySorter(map);
/*  543: 597 */     makeSummaryPrettyTeach(sortedSummary);
/*  544:     */   }
/*  545:     */   
/*  546:     */   public void putSummaryIntoOrder(Map<Entity, Integer> unordered)
/*  547:     */   {
/*  548: 602 */     if (!Radio.tellStoryButton.isSelected()) {
/*  549: 603 */       return;
/*  550:     */     }
/*  551: 605 */     Mark.say(new Object[] {"PUTTING SUMMARY INTO ORDER!" });
/*  552: 606 */     if ((unordered.getClass() == Map.class) && 
/*  553: 607 */       (unordered.isEmpty()))
/*  554:     */     {
/*  555: 608 */       Mark.say(new Object[] {"Map is empty!" });
/*  556: 609 */       return;
/*  557:     */     }
/*  558: 612 */     HashMap<Entity, Integer> unorderedSummary = (HashMap)unordered;
/*  559: 613 */     Map<Double, Map.Entry<Entity, Integer>> map = new TreeMap();
/*  560: 614 */     Iterator<Map.Entry<Entity, Integer>> it = unorderedSummary.entrySet().iterator();
/*  561:     */     int preindex;
/*  562: 617 */     while (it.hasNext())
/*  563:     */     {
/*  564: 618 */       Map.Entry<Entity, Integer> pair = (Map.Entry)it.next();
/*  565: 619 */       Entity key = (Entity)pair.getKey();
/*  566: 620 */       if ((this.completeStory.containsDeprecated(key)) && (((Integer)unorderedSummary.get(key)).intValue() != 2))
/*  567:     */       {
/*  568: 621 */         int index = key.getID();
/*  569: 622 */         map.put(Double.valueOf(index), pair);
/*  570: 623 */         Mark.say(new Object[] {Integer.valueOf(index), " : ", ((Entity)pair.getKey()).asString() });
/*  571:     */       }
/*  572: 626 */       else if (((Integer)unorderedSummary.get(key)).intValue() == 2)
/*  573:     */       {
/*  574: 627 */         preindex = key.getID();
/*  575: 628 */         double index = preindex;
/*  576: 629 */         index -= 0.9D;
/*  577: 630 */         map.put(Double.valueOf(index), pair);
/*  578:     */       }
/*  579:     */     }
/*  580: 635 */     TreeSet<Double> keySet = new TreeSet(map.keySet());
/*  581: 636 */     Mark.say(new Object[] {"Ordered: " });
/*  582: 637 */     for (Double key : keySet)
/*  583:     */     {
/*  584: 638 */       Map.Entry<Entity, Integer> seqToAdd = (Map.Entry)map.get(key);
/*  585: 639 */       Mark.say(new Object[] {key, " : ", ((Entity)seqToAdd.getKey()).asString() });
/*  586: 640 */       this.finalSummary.add(seqToAdd);
/*  587:     */     }
/*  588: 642 */     if (this.finalSummary.isEmpty()) {
/*  589: 643 */       Mark.say(new Object[] {"FINAL SUMMARY EMPTY!" });
/*  590:     */     }
/*  591:     */   }
/*  592:     */   
/*  593:     */   public void makeSummaryPrettyTeach(LinkedList<ArrayList<Object>> summaryList)
/*  594:     */   {
/*  595: 649 */     if (!Radio.tellStoryButton.isSelected()) {
/*  596: 650 */       return;
/*  597:     */     }
/*  598: 652 */     Mark.say(new Object[] {"In makeSummaryPrettyTeach" });
/*  599: 653 */     Generator generator = Generator.getGenerator();
/*  600: 654 */     for (ArrayList<Object> entry : summaryList) {
/*  601: 655 */       if (((Integer)entry.get(1)).intValue() == 0)
/*  602:     */       {
/*  603: 656 */         Entity summaryElement = (Entity)entry.get(0);
/*  604:     */         
/*  605: 658 */         String finalString = generator.generate(summaryElement);
/*  606: 660 */         if (GenesisMenus.getSummarize().isSelected())
/*  607:     */         {
/*  608: 662 */           BetterSignal signal = new BetterSignal(new Object[] { "Summary", finalString });
/*  609: 663 */           Connections.getPorts(this).transmit("switch tab", signal);
/*  610:     */         }
/*  611: 665 */         else if (GenesisMenus.getConceptSummaryButton().isSelected())
/*  612:     */         {
/*  613: 666 */           BetterSignal signal = new BetterSignal(new Object[] { conceptBeingSummarized, finalString });
/*  614: 667 */           Connections.getPorts(this).transmit("switch tab", signal);
/*  615:     */         }
/*  616:     */       }
/*  617: 671 */       else if (((Integer)entry.get(1)).intValue() == 1)
/*  618:     */       {
/*  619: 672 */         Entity summaryElement = (Entity)entry.get(0);
/*  620:     */         String str;
/*  621:     */         String str;
/*  622: 674 */         if (GenesisMenus.getPrimingWithIntrospectionButton().isSelected()) {
/*  623: 675 */           str = generator.generateXPeriod(summaryElement);
/*  624:     */         } else {
/*  625: 678 */           str = generator.generate(summaryElement) + " ";
/*  626:     */         }
/*  627: 681 */         String finalString = Html.red(str);
/*  628: 683 */         if (GenesisMenus.getSummarize().isSelected())
/*  629:     */         {
/*  630: 685 */           BetterSignal signal = new BetterSignal(new Object[] { "Summary", finalString });
/*  631: 686 */           Connections.getPorts(this).transmit(signal);
/*  632:     */         }
/*  633: 688 */         else if (GenesisMenus.getConceptSummaryButton().isSelected())
/*  634:     */         {
/*  635: 689 */           BetterSignal signal = new BetterSignal(new Object[] { conceptBeingSummarized, finalString });
/*  636: 690 */           Connections.getPorts(this).transmit(signal);
/*  637:     */         }
/*  638:     */       }
/*  639: 694 */       else if (((Integer)entry.get(1)).intValue() == 2)
/*  640:     */       {
/*  641: 695 */         Entity summaryElement = (Entity)entry.get(0);
/*  642:     */         
/*  643: 697 */         String str = " because " + generator.generateAsIf(summaryElement.getElement(0)) + " ";
/*  644: 698 */         String finalString = Html.blue(str);
/*  645: 700 */         if (GenesisMenus.getSummarize().isSelected())
/*  646:     */         {
/*  647: 702 */           BetterSignal signal = new BetterSignal(new Object[] { "Summary", finalString });
/*  648: 703 */           Connections.getPorts(this).transmit(signal);
/*  649:     */         }
/*  650: 705 */         else if (GenesisMenus.getConceptSummaryButton().isSelected())
/*  651:     */         {
/*  652: 706 */           BetterSignal signal = new BetterSignal(new Object[] { conceptBeingSummarized, finalString });
/*  653: 707 */           Connections.getPorts(this).transmit(signal);
/*  654:     */         }
/*  655:     */       }
/*  656:     */     }
/*  657:     */   }
/*  658:     */   
/*  659:     */   public void makeSummaryPretty(LinkedList<ArrayList<Object>> summaryList)
/*  660:     */   {
/*  661: 714 */     if (!Radio.tellStoryButton.isSelected()) {
/*  662: 715 */       return;
/*  663:     */     }
/*  664: 717 */     Mark.say(new Object[] {"MKAING SUMMARY PRETTY!" });
/*  665: 718 */     Generator generator = Generator.getGenerator();
/*  666: 719 */     for (ArrayList<Object> entry : summaryList) {
/*  667: 720 */       if (entry.get(1) == "REGULAR")
/*  668:     */       {
/*  669: 721 */         Entity summaryElement = (Entity)entry.get(0);
/*  670: 722 */         String finalString = generator.generate(summaryElement);
/*  671: 724 */         if (GenesisMenus.getSummarize().isSelected())
/*  672:     */         {
/*  673: 725 */           BetterSignal signal = new BetterSignal(new Object[] { "Summary", finalString });
/*  674: 726 */           Connections.getPorts(this).transmit(signal);
/*  675:     */         }
/*  676: 728 */         else if (GenesisMenus.getConceptSummaryButton().isSelected())
/*  677:     */         {
/*  678: 729 */           BetterSignal signal = new BetterSignal(new Object[] { conceptBeingSummarized, finalString });
/*  679: 730 */           Connections.getPorts(this).transmit(signal);
/*  680:     */         }
/*  681:     */       }
/*  682: 734 */       else if (entry.get(1) == "MISSING")
/*  683:     */       {
/*  684: 735 */         Entity summaryElement = (Entity)entry.get(0);
/*  685:     */         String str;
/*  686:     */         String str;
/*  687: 737 */         if (GenesisMenus.getPrimingWithIntrospectionButton().isSelected()) {
/*  688: 738 */           str = generator.generateXPeriod(summaryElement);
/*  689:     */         } else {
/*  690: 741 */           str = generator.generate(summaryElement) + " ";
/*  691:     */         }
/*  692: 744 */         String finalString = Html.red(str);
/*  693: 747 */         if (GenesisMenus.getSummarize().isSelected())
/*  694:     */         {
/*  695: 748 */           BetterSignal signal = new BetterSignal(new Object[] { "Summary", finalString });
/*  696: 749 */           Connections.getPorts(this).transmit(signal);
/*  697:     */         }
/*  698: 751 */         else if (GenesisMenus.getConceptSummaryButton().isSelected())
/*  699:     */         {
/*  700: 752 */           BetterSignal signal = new BetterSignal(new Object[] { conceptBeingSummarized, finalString });
/*  701: 753 */           Connections.getPorts(this).transmit(signal);
/*  702:     */         }
/*  703:     */       }
/*  704: 757 */       else if (entry.get(1) == "RULE")
/*  705:     */       {
/*  706: 758 */         Entity summaryElement = (Entity)entry.get(0);
/*  707: 759 */         String str = " because " + generator.generateAsIf(summaryElement) + " ";
/*  708: 760 */         String finalString = Html.blue(str);
/*  709: 761 */         if (GenesisMenus.getSummarize().isSelected())
/*  710:     */         {
/*  711: 762 */           BetterSignal signal = new BetterSignal(new Object[] { "Summary", finalString });
/*  712: 763 */           Connections.getPorts(this).transmit(signal);
/*  713:     */         }
/*  714: 765 */         else if (GenesisMenus.getConceptSummaryButton().isSelected())
/*  715:     */         {
/*  716: 766 */           BetterSignal signal = new BetterSignal(new Object[] { conceptBeingSummarized, finalString });
/*  717: 767 */           Connections.getPorts(this).transmit(signal);
/*  718:     */         }
/*  719:     */       }
/*  720:     */     }
/*  721:     */   }
/*  722:     */   
/*  723:     */   public void processReflectionAnalysis(Object o)
/*  724:     */     throws Exception
/*  725:     */   {
/*  726: 781 */     if (!Radio.tellStoryButton.isSelected()) {
/*  727: 782 */       return;
/*  728:     */     }
/*  729: 784 */     Mark.say(new Object[] {"Processing ReflectionAnalysis!" });
/*  730: 785 */     Generator generator = Generator.getGenerator();
/*  731: 786 */     if ((o instanceof ReflectionAnalysis))
/*  732:     */     {
/*  733: 787 */       this.reflectionAnalysis = ((ReflectionAnalysis)o);
/*  734:     */       Iterator localIterator2;
/*  735: 788 */       for (Iterator localIterator1 = this.reflectionAnalysis.getReflectionDescriptions().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/*  736:     */       {
/*  737: 788 */         ReflectionDescription d = (ReflectionDescription)localIterator1.next();
/*  738: 789 */         localIterator2 = d.getStoryElementsInvolved().getElements().iterator(); continue;Entity t = (Entity)localIterator2.next();
/*  739: 791 */         if (!this.storyReflections.containsDeprecated(t)) {
/*  740: 792 */           this.storyReflections.addElement(t);
/*  741:     */         }
/*  742:     */       }
/*  743: 797 */       if (GenesisMenus.getConceptSummaryButton().isSelected()) {
/*  744: 798 */         conceptSummary(this.reflectionAnalysis, true);
/*  745:     */       }
/*  746: 800 */       if (!GenesisMenus.getSummarize().isSelected()) {
/*  747: 801 */         return;
/*  748:     */       }
/*  749: 805 */       if (this.storyReflections.getAllComponents().isEmpty())
/*  750:     */       {
/*  751: 806 */         Mark.say(new Object[] {"EMPTY: StoryReflections." });
/*  752: 807 */         return;
/*  753:     */       }
/*  754: 809 */       Mark.say(new Object[] {"Going to call DivideSummary." });
/*  755: 810 */       Sequence summaryPart = divideSummary(this.storyReflections);
/*  756: 811 */       Mark.say(new Object[] {"Back and about to call appropriate SUMMARY," });
/*  757: 812 */       if (GenesisMenus.getSpoonFeedButton().isSelected())
/*  758:     */       {
/*  759: 813 */         Mark.say(new Object[] {"CALLED SPOONFEED SUMMARY" });
/*  760: 814 */         spoonfeedSummary(summaryPart);
/*  761:     */       }
/*  762: 816 */       if (GenesisMenus.getPrimingButton().isSelected()) {
/*  763: 817 */         justifySummary(summaryPart);
/*  764:     */       }
/*  765: 819 */       if (GenesisMenus.getPrimingWithIntrospectionButton().isSelected()) {
/*  766: 820 */         teachSummary(summaryPart);
/*  767:     */       }
/*  768:     */     }
/*  769:     */   }
/*  770:     */   
/*  771:     */   public Sequence divideSummary(Sequence wholeSummary)
/*  772:     */   {
/*  773: 829 */     if (!Radio.tellStoryButton.isSelected()) {
/*  774: 830 */       return wholeSummary;
/*  775:     */     }
/*  776: 833 */     Mark.say(new Object[] {"In DivideSummary." });
/*  777: 834 */     Sequence summaryToSort = wholeSummary;
/*  778: 835 */     Sequence storyCopy = this.explicitStory;
/*  779: 836 */     Sequence inferencesSummarized = new Sequence();
/*  780: 837 */     StandardMatcher matcher = StandardMatcher.getBasicMatcher();
/*  781: 838 */     if (this.explicitStory.getAllComponents().isEmpty()) {
/*  782: 839 */       Mark.say(new Object[] {"EMPTY! explicit story" });
/*  783:     */     }
/*  784:     */     Entity localEntity1;
/*  785: 841 */     for (Iterator localIterator1 = this.explicitStory.getAllComponents().iterator(); localIterator1.hasNext(); localEntity1 = (Entity)localIterator1.next()) {}
/*  786:     */     Iterator localIterator2;
/*  787: 845 */     for (localIterator1 = summaryToSort.getAllComponents().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/*  788:     */     {
/*  789: 845 */       Entity t = (Entity)localIterator1.next();
/*  790:     */       
/*  791: 847 */       localIterator2 = storyCopy.getAllComponents().iterator(); continue;Entity g = (Entity)localIterator2.next();
/*  792: 848 */       LList<PairOfEntities> match = matcher.matchAnyPart(t, g);
/*  793: 849 */       boolean usefulMatch = false;
/*  794: 851 */       if (match != null)
/*  795:     */       {
/*  796: 852 */         usefulMatch = usefulPartialMatch(match);
/*  797: 854 */         if ((usefulMatch) && (!this.storySummarized.containsDeprecated(t)))
/*  798:     */         {
/*  799: 855 */           this.storySummarized.addElement(t);
/*  800: 856 */           storyCopy.removeElement(g);
/*  801: 857 */           summaryToSort.removeElement(t);
/*  802: 858 */           usefulMatch = false;
/*  803:     */         }
/*  804:     */       }
/*  805:     */     }
/*  806: 865 */     for (Entity m : summaryToSort.getAllComponents()) {
/*  807: 866 */       inferencesSummarized.addElement(m);
/*  808:     */     }
/*  809: 869 */     if (inferencesSummarized.getElements().isEmpty()) {
/*  810: 870 */       Mark.say(new Object[] {"EMPTY: inference part of summary." });
/*  811:     */     }
/*  812: 873 */     return inferencesSummarized;
/*  813:     */   }
/*  814:     */   
/*  815:     */   public void spoonfeedSummary(Sequence summaryPart)
/*  816:     */   {
/*  817: 877 */     if (!Radio.tellStoryButton.isSelected()) {
/*  818: 878 */       return;
/*  819:     */     }
/*  820: 880 */     Mark.say(new Object[] {"In spoonfeed summary!" });
/*  821: 881 */     StandardMatcher matcher = StandardMatcher.getBasicMatcher();
/*  822:     */     
/*  823: 883 */     Map<Entity, Integer> toIncludeInSummary = new HashMap();
/*  824: 885 */     for (Entity t : summaryPart.getElements()) {
/*  825: 886 */       for (Entity f : this.studentInferences.getAllComponents())
/*  826:     */       {
/*  827: 887 */         if (matcher.matchAnyPart(t, f) != null) {
/*  828:     */           break;
/*  829:     */         }
/*  830: 891 */         if ((!t.isA("prediction")) && (!t.isA("explanation")))
/*  831:     */         {
/*  832: 892 */           this.inferencesInSummary.addElement(t);
/*  833: 893 */           toIncludeInSummary.put(t, Integer.valueOf(1));
/*  834:     */         }
/*  835:     */       }
/*  836:     */     }
/*  837: 898 */     if (toIncludeInSummary.isEmpty()) {
/*  838: 899 */       Mark.say(new Object[] {"SUMMARY OF IFERENCES EMPTY!" });
/*  839:     */     }
/*  840: 901 */     consolidateSummary(toIncludeInSummary);
/*  841:     */   }
/*  842:     */   
/*  843:     */   public void justifySummary(Sequence summaryPart)
/*  844:     */   {
/*  845: 905 */     if (!Radio.tellStoryButton.isSelected()) {
/*  846: 906 */       return;
/*  847:     */     }
/*  848: 908 */     Mark.say(new Object[] {"In justified summary!" });
/*  849: 909 */     StandardMatcher matcher = StandardMatcher.getBasicMatcher();
/*  850: 910 */     Map<Entity, Integer> toIncludeInSummary = new HashMap();
/*  851: 911 */     for (Entity t : summaryPart.getElements()) {
/*  852: 913 */       for (Entity f : this.studentInferences.getAllComponents())
/*  853:     */       {
/*  854: 914 */         LList<PairOfEntities> match = matcher.matchAnyPart(t, f);
/*  855: 915 */         if (match != null)
/*  856:     */         {
/*  857: 916 */           if (usefulPartialMatch(match)) {
/*  858:     */             break;
/*  859:     */           }
/*  860:     */         }
/*  861:     */         else {
/*  862: 922 */           for (Entity h : this.completeStory.getAllComponents()) {
/*  863: 923 */             if ((h.isA("explanation")) || (h.isA("prediction")))
/*  864:     */             {
/*  865: 924 */               LList<PairOfEntities> matchFull = matcher.matchAnyPart(t, h.getObject());
/*  866: 925 */               if ((matchFull != null) && 
/*  867: 926 */                 (usefulPartialMatch(matchFull))) {
/*  868: 927 */                 toIncludeInSummary.put(h, Integer.valueOf(1));
/*  869:     */               }
/*  870:     */             }
/*  871:     */           }
/*  872:     */         }
/*  873:     */       }
/*  874:     */     }
/*  875: 935 */     consolidateSummary(toIncludeInSummary);
/*  876:     */   }
/*  877:     */   
/*  878:     */   public void teachSummary(Sequence summaryPart)
/*  879:     */     throws Exception
/*  880:     */   {
/*  881: 940 */     if (!Radio.tellStoryButton.isSelected()) {
/*  882: 941 */       return;
/*  883:     */     }
/*  884: 943 */     Mark.say(new Object[] {"In teachSummary" });
/*  885: 944 */     StandardMatcher matcher = StandardMatcher.getBasicMatcher();
/*  886: 945 */     Generator generator = Generator.getGenerator();
/*  887: 946 */     ArrayList<ArrayList<Object>> toIncludeInSummary = new ArrayList();
/*  888: 947 */     Sequence associatedRules = new Sequence();
/*  889: 948 */     for (Entity t : summaryPart.getElements()) {
/*  890: 949 */       for (Entity f : this.studentInferences.getAllComponents())
/*  891:     */       {
/*  892: 950 */         LList<PairOfEntities> match = matcher.matchAnyPart(t, f);
/*  893: 951 */         if (match != null)
/*  894:     */         {
/*  895: 952 */           if (usefulPartialMatch(match)) {
/*  896:     */             break;
/*  897:     */           }
/*  898:     */         }
/*  899:     */         else {
/*  900: 958 */           for (Entity h : this.completeStory.getAllComponents()) {
/*  901: 959 */             if ((h.isA("explanation")) || (h.isA("prediction")))
/*  902:     */             {
/*  903: 960 */               LList<PairOfEntities> matchFull = matcher.matchAnyPart(t, h.getObject());
/*  904: 961 */               if ((matchFull != null) && 
/*  905: 962 */                 (usefulPartialMatch(matchFull)))
/*  906:     */               {
/*  907:     */                 LList<PairOfEntities> matchRule;
/*  908: 964 */                 for (Entity rule : this.rules.getAllComponents())
/*  909:     */                 {
/*  910: 965 */                   matchRule = matcher.matchRuleToInstantiation(rule, h);
/*  911: 966 */                   if (matchRule != null) {
/*  912: 967 */                     associatedRules.addElement(rule);
/*  913:     */                   }
/*  914:     */                 }
/*  915:     */                 String compoundRule;
/*  916:     */                 String compoundRule;
/*  917: 971 */                 if (associatedRules.getAllComponents().size() == 1)
/*  918:     */                 {
/*  919: 972 */                   compoundRule = generator.generate(associatedRules.getElement(0));
/*  920:     */                 }
/*  921:     */                 else
/*  922:     */                 {
/*  923: 975 */                   compoundRule = generator.generateXPeriod(associatedRules.getElement(0));
/*  924: 976 */                   associatedRules.removeElement(associatedRules.getElement(0));
/*  925: 977 */                   for (Entity matchingRule : associatedRules.getAllComponents())
/*  926:     */                   {
/*  927: 978 */                     String addOn = " and " + generator.generateXPeriod(matchingRule);
/*  928: 979 */                     compoundRule = compoundRule + addOn;
/*  929:     */                   }
/*  930: 981 */                   compoundRule = compoundRule + ".";
/*  931:     */                 }
/*  932: 983 */                 Translator translator = Translator.getTranslator();
/*  933: 984 */                 Entity compoundRuleThing = translator.translate(compoundRule);
/*  934: 985 */                 if (compoundRuleThing == null)
/*  935:     */                 {
/*  936: 986 */                   Mark.say(new Object[] {"COULDNT TRANSLATE!!!!!!" });
/*  937: 987 */                   return;
/*  938:     */                 }
/*  939: 990 */                 ArrayList<Object> summaryTuple = new ArrayList();
/*  940: 991 */                 summaryTuple.add(0, t);
/*  941: 992 */                 summaryTuple.add(1, Integer.valueOf(1));
/*  942: 993 */                 toIncludeInSummary.add(summaryTuple);
/*  943: 994 */                 Entity newRule = new Entity();
/*  944: 995 */                 newRule = compoundRuleThing;
/*  945: 996 */                 String associatedName = h.getNameSuffix().substring(1);
/*  946: 997 */                 int associatedID = Integer.parseInt(associatedName);
/*  947:     */                 
/*  948: 999 */                 int ruleID = associatedID + 1;
/*  949:     */                 
/*  950:1001 */                 String ruleName = Integer.toString(ruleID);
/*  951:     */                 
/*  952:1003 */                 String ruleNameFinal = "-" + ruleName;
/*  953:1004 */                 newRule.setNameSuffix(ruleNameFinal);
/*  954:     */                 
/*  955:1006 */                 ArrayList<Object> summaryTupleForRules = new ArrayList();
/*  956:1007 */                 summaryTupleForRules.add(0, newRule);
/*  957:1008 */                 summaryTupleForRules.add(1, Integer.valueOf(2));
/*  958:1009 */                 toIncludeInSummary.add(summaryTupleForRules);
/*  959:1010 */                 break;
/*  960:     */               }
/*  961:     */             }
/*  962:     */           }
/*  963:     */         }
/*  964:     */       }
/*  965:     */     }
/*  966:1023 */     consolidateSummary(toIncludeInSummary);
/*  967:     */   }
/*  968:     */   
/*  969:     */   public void consolidateSummary(ArrayList<ArrayList<Object>> inferenceParts)
/*  970:     */   {
/*  971:1027 */     if (!Radio.tellStoryButton.isSelected()) {
/*  972:1028 */       return;
/*  973:     */     }
/*  974:1030 */     Mark.say(new Object[] {"In consolidateSummary!" });
/*  975:     */     
/*  976:1032 */     ArrayList<ArrayList<Object>> fullEditedSummary = new ArrayList();
/*  977:1033 */     for (Entity t : this.storySummarized.getAllComponents())
/*  978:     */     {
/*  979:1034 */       ArrayList<Object> l = new ArrayList();
/*  980:1035 */       l.add(0, t);
/*  981:1036 */       l.add(1, Integer.valueOf(0));
/*  982:1037 */       fullEditedSummary.add(l);
/*  983:     */     }
/*  984:1039 */     for (ArrayList<Object> l : inferenceParts) {
/*  985:1040 */       fullEditedSummary.add(l);
/*  986:     */     }
/*  987:1043 */     putSummaryIntoOrder(fullEditedSummary);
/*  988:     */   }
/*  989:     */   
/*  990:     */   public void consolidateSummary(Map<Entity, Integer> inferenceParts)
/*  991:     */   {
/*  992:1048 */     if (!Radio.tellStoryButton.isSelected()) {
/*  993:1049 */       return;
/*  994:     */     }
/*  995:1051 */     Mark.say(new Object[] {"In consolidateSummary!" });
/*  996:1052 */     Sequence summaryBeforeOrdering = new Sequence();
/*  997:1053 */     Map<Entity, Integer> fullEditedSummary = new HashMap();
/*  998:1054 */     for (Entity t : this.storySummarized.getAllComponents()) {
/*  999:1055 */       summaryBeforeOrdering.addElement(t);
/* 1000:     */     }
/* 1001:1058 */     for (Entity m : this.inferencesInSummary.getAllComponents()) {
/* 1002:1059 */       summaryBeforeOrdering.addElement(m);
/* 1003:     */     }
/* 1004:1062 */     orderSummary(summaryBeforeOrdering);
/* 1005:     */   }
/* 1006:     */   
/* 1007:     */   public void processReflectionsForRules1(Object o)
/* 1008:     */   {
/* 1009:1071 */     if (!Radio.tellStoryButton.isSelected()) {
/* 1010:1072 */       return;
/* 1011:     */     }
/* 1012:1074 */     if ((o instanceof Sequence))
/* 1013:     */     {
/* 1014:1075 */       Sequence reflectionAnalysis = (Sequence)o;
/* 1015:     */       Iterator localIterator2;
/* 1016:1076 */       for (Iterator localIterator1 = reflectionAnalysis.getElements().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 1017:     */       {
/* 1018:1076 */         Entity description = (Entity)localIterator1.next();
/* 1019:1077 */         this.reflectionRules.addElement(description);
/* 1020:     */         
/* 1021:1079 */         localIterator2 = description.getElements().iterator(); continue;Entity part = (Entity)localIterator2.next();
/* 1022:1081 */         if ((part instanceof Sequence))
/* 1023:     */         {
/* 1024:1082 */           Sequence seqPart = new Sequence();
/* 1025:1083 */           seqPart = (Sequence)part;
/* 1026:1084 */           for (Entity concept : seqPart.getElements()) {
/* 1027:1085 */             this.reflectionParts.add(concept);
/* 1028:     */           }
/* 1029:     */         }
/* 1030:     */       }
/* 1031:     */     }
/* 1032:     */   }
/* 1033:     */   
/* 1034:     */   public void processRules(Object o)
/* 1035:     */   {
/* 1036:1095 */     if (!Radio.tellStoryButton.isSelected()) {
/* 1037:1096 */       return;
/* 1038:     */     }
/* 1039:1099 */     if (!Radio.tellStoryButton.isSelected()) {
/* 1040:1100 */       return;
/* 1041:     */     }
/* 1042:1103 */     if ((o instanceof Sequence)) {
/* 1043:1104 */       this.rules = ((Sequence)o);
/* 1044:     */     }
/* 1045:     */   }
/* 1046:     */   
/* 1047:     */   public void processQuiescence1(Object o)
/* 1048:     */   {
/* 1049:1114 */     if (!Radio.tellStoryButton.isSelected()) {
/* 1050:1115 */       return;
/* 1051:     */     }
/* 1052:1117 */     if ((o instanceof Sequence))
/* 1053:     */     {
/* 1054:1118 */       Sequence increment = (Sequence)o;
/* 1055:1119 */       this.isOneQuiet = true;
/* 1056:1120 */       this.quietIntervalOne = increment;
/* 1057:     */     }
/* 1058:     */   }
/* 1059:     */   
/* 1060:     */   private void recordIntoCompilation(boolean on, Entity toRecord, String type)
/* 1061:     */   {
/* 1062:1127 */     if (!Radio.tellStoryButton.isSelected()) {
/* 1063:1128 */       return;
/* 1064:     */     }
/* 1065:1130 */     if (!on) {
/* 1066:1131 */       return;
/* 1067:     */     }
/* 1068:1133 */     ArrayList<Object> recording = new ArrayList();
/* 1069:1134 */     recording.add(0, toRecord);
/* 1070:1135 */     recording.add(1, type);
/* 1071:1136 */     this.storyAndComprehensionCompilation.add(recording);
/* 1072:     */   }
/* 1073:     */   
/* 1074:     */   public void processQuiescence2(Object o)
/* 1075:     */   {
/* 1076:1142 */     if (!Radio.tellStoryButton.isSelected()) {
/* 1077:1143 */       return;
/* 1078:     */     }
/* 1079:1146 */     for (Entity t : ((Entity)o).getAllComponents()) {
/* 1080:1148 */       if ((t != null) && (t.relationP("start")))
/* 1081:     */       {
/* 1082:1150 */         Mark.say(new Object[] {"WE GOT START!!!!!!!!!" });
/* 1083:1151 */         goalStrategyPicker(this.userGoalArray);
/* 1084:1152 */         this.recordToCompilation = true;
/* 1085:1153 */         this.startIncrement = true;
/* 1086:     */       }
/* 1087:     */     }
/* 1088:1157 */     if ((o instanceof Sequence))
/* 1089:     */     {
/* 1090:1158 */       Sequence increment = (Sequence)o;
/* 1091:1159 */       Generator generator = Generator.getGenerator();
/* 1092:     */       
/* 1093:1161 */       this.isTwoQuiet = true;
/* 1094:1162 */       this.quietIntervalTwo = increment;
/* 1095:1166 */       if ((this.isOneQuiet) && (this.isTwoQuiet))
/* 1096:     */       {
/* 1097:1167 */         StandardMatcher matcher = StandardMatcher.getBasicMatcher();
/* 1098:1169 */         if ((increment.sequenceP()) && (!increment.getAllComponents().isEmpty())) {
/* 1099:1170 */           for (Entity m : increment.getAllComponents()) {
/* 1100:1171 */             recordIntoCompilation(this.recordToCompilation, m, "REGULAR");
/* 1101:     */           }
/* 1102:     */         }
/* 1103:     */         ArrayList<Object> orderTypeTuple;
/* 1104:     */         Pattern pattern;
/* 1105:     */         Matcher regMatcher;
/* 1106:1175 */         if (this.startIncrement)
/* 1107:     */         {
/* 1108:1176 */           boolean foundCharacter = false;
/* 1109:     */           Matcher regexMatcher;
/* 1110:     */           label581:
/* 1111:1181 */           for (Iterator localIterator3 = increment.getAllComponents().iterator(); localIterator3.hasNext(); regexMatcher.find())
/* 1112:     */           {
/* 1113:1181 */             t = (Entity)localIterator3.next();
/* 1114:1182 */             Mark.say(new Object[] {((Entity)t).asString(), " added to orderingMap at ", Integer.valueOf(this.orderCount) });
/* 1115:1183 */             String hashedIncrement = ((Entity)t).hash();
/* 1116:1184 */             orderTypeTuple = new ArrayList();
/* 1117:1185 */             orderTypeTuple.add(0, Integer.valueOf(this.orderCount));
/* 1118:1186 */             orderTypeTuple.add(1, "REGULAR");
/* 1119:1187 */             this.orderingMap.put(hashedIncrement, orderTypeTuple);
/* 1120:1188 */             this.orderCount += 1;
/* 1121:1190 */             if ((foundCharacter) || (!((Entity)t).relationP("classification"))) {
/* 1122:     */               break label581;
/* 1123:     */             }
/* 1124:1191 */             String object = ((Entity)t).getObject().asString();
/* 1125:1192 */             String regPattern = "\\s" + this.focalCharacter + "-";
/* 1126:1193 */             pattern = Pattern.compile(regPattern, 2);
/* 1127:1194 */             regexMatcher = pattern.matcher(object);
/* 1128:1195 */             continue;
/* 1129:1196 */             Mark.say(new Object[] {"Found: ", regexMatcher.group() });
/* 1130:1197 */             foundCharacter = true;
/* 1131:1198 */             String characterMsg = "";
/* 1132:1199 */             Pattern newPattern = Pattern.compile("dis");
/* 1133:1200 */             regMatcher = newPattern.matcher(this.intendedDisposition);
/* 1134:1201 */             if (regMatcher.find()) {
/* 1135:1202 */               characterMsg = this.focalCharacter + " is crazy. ";
/* 1136:     */             } else {
/* 1137:1205 */               characterMsg = this.focalCharacter + " is good. ";
/* 1138:     */             }
/* 1139:1215 */             Mark.say(new Object[] {"CharacterMsg: ", characterMsg });
/* 1140:1216 */             String characterMsgGreen = Html.green(characterMsg);
/* 1141:1217 */             BetterSignal signal = new BetterSignal(new Object[] { "Story teller", characterMsgGreen });
/* 1142:1218 */             Connections.getPorts(this).transmit(signal);
/* 1143:     */           }
/* 1144:     */         }
/* 1145:1225 */         for (Object t = this.quietIntervalOne.getElements().iterator(); ((Iterator)t).hasNext();)
/* 1146:     */         {
/* 1147:1225 */           Entity e = (Entity)((Iterator)t).next();
/* 1148:1226 */           int matchCount = 0;
/* 1149:1227 */           for (Entity f : this.quietIntervalTwo.getElements()) {
/* 1150:1229 */             if (matcher.match(e, f) != null)
/* 1151:     */             {
/* 1152:1230 */               matchCount++;
/* 1153:1231 */               break;
/* 1154:     */             }
/* 1155:     */           }
/* 1156:1235 */           if ((matchCount == 0) && 
/* 1157:1236 */             (!this.unmatchedList.contains(e)))
/* 1158:     */           {
/* 1159:1240 */             this.unmatchedList.add(e);
/* 1160:     */             
/* 1161:     */ 
/* 1162:1243 */             int listSize = this.unmatchedList.size();
/* 1163:1244 */             if (listSize != 0)
/* 1164:     */             {
/* 1165:1245 */               Entity mostRecentMiss = (Entity)this.unmatchedList.get(listSize - 1);
/* 1166:1246 */               Generator generate = Generator.getGenerator();
/* 1167:     */               
/* 1168:1248 */               String mostRecentMissString = generate.generate(mostRecentMiss);
/* 1169:     */               String finalString;
/* 1170:1251 */               if (GenesisMenus.getSpoonFeedButton().isSelected())
/* 1171:     */               {
/* 1172:1255 */                 if (((mostRecentMiss.isA("prediction")) || (mostRecentMiss.isA("explanation"))) && 
/* 1173:1256 */                   (mostRecentMiss.getObject() != null))
/* 1174:     */                 {
/* 1175:1257 */                   if (this.startIncrement) {
/* 1176:1258 */                     for (Entity f : mostRecentMiss.getAllComponents())
/* 1177:     */                     {
/* 1178:1259 */                       Mark.say(new Object[] {f.asString(), " added to orderingMap at ", Integer.valueOf(this.orderCount) });
/* 1179:1260 */                       String hashedMiss = f.hash();
/* 1180:1261 */                       ArrayList<Object> orderTypeTuple = new ArrayList();
/* 1181:1262 */                       orderTypeTuple.add(0, Integer.valueOf(this.orderCount));
/* 1182:1263 */                       orderTypeTuple.add(1, "MISSING");
/* 1183:1264 */                       this.orderingMap.put(hashedMiss, orderTypeTuple);
/* 1184:1265 */                       this.orderCount += 1;
/* 1185:     */                     }
/* 1186:     */                   }
/* 1187:1269 */                   recordIntoCompilation(this.recordToCompilation, mostRecentMiss.getObject(), "MISSING");
/* 1188:1270 */                   String mostRecentGenerated = generate.generate(mostRecentMiss.getObject()) + " ";
/* 1189:1271 */                   finalString = Html.red(mostRecentGenerated);
/* 1190:1272 */                   if (mostRecentGenerated != null)
/* 1191:     */                   {
/* 1192:1273 */                     BetterSignal signal = new BetterSignal(new Object[] { "Story teller", finalString });
/* 1193:1274 */                     Connections.getPorts(this).transmit(signal);
/* 1194:     */                   }
/* 1195:     */                 }
/* 1196:     */               }
/* 1197:     */               else
/* 1198:     */               {
/* 1199:     */                 BetterSignal signal;
/* 1200:1282 */                 if (GenesisMenus.getPrimingButton().isSelected())
/* 1201:     */                 {
/* 1202:1283 */                   if ((mostRecentMiss.isA("prediction")) || (mostRecentMiss.isA("explanation")))
/* 1203:     */                   {
/* 1204:1285 */                     if (this.startIncrement) {
/* 1205:1286 */                       for (Entity f : mostRecentMiss.getAllComponents())
/* 1206:     */                       {
/* 1207:1287 */                         Mark.say(new Object[] {f.asString(), " added to orderingMap at ", Integer.valueOf(this.orderCount) });
/* 1208:1288 */                         String hashedMiss = f.hash();
/* 1209:1289 */                         ArrayList<Object> orderTypeTuple = new ArrayList();
/* 1210:1290 */                         orderTypeTuple.add(0, Integer.valueOf(this.orderCount));
/* 1211:1291 */                         orderTypeTuple.add(1, "MISSING");
/* 1212:1292 */                         this.orderingMap.put(hashedMiss, orderTypeTuple);
/* 1213:1293 */                         this.orderCount += 1;
/* 1214:     */                       }
/* 1215:     */                     }
/* 1216:1296 */                     recordIntoCompilation(this.recordToCompilation, mostRecentMiss, "MISSING");
/* 1217:1297 */                     String mostRecentGenerated = generate.generate(mostRecentMiss) + "  ";
/* 1218:1298 */                     String finalString = Html.red(mostRecentGenerated);
/* 1219:1300 */                     if (mostRecentGenerated != null)
/* 1220:     */                     {
/* 1221:1301 */                       signal = new BetterSignal(new Object[] { "Story teller", finalString });
/* 1222:1302 */                       Connections.getPorts(this).transmit(signal);
/* 1223:     */                     }
/* 1224:     */                   }
/* 1225:     */                 }
/* 1226:1309 */                 else if (GenesisMenus.getPrimingWithIntrospectionButton().isSelected())
/* 1227:     */                 {
/* 1228:1311 */                   StandardMatcher match = StandardMatcher.getBasicMatcher();
/* 1229:1312 */                   if ((mostRecentMiss.isA("prediction")) || (mostRecentMiss.isA("explanation")))
/* 1230:     */                   {
/* 1231:     */                     String hashedMiss;
/* 1232:1313 */                     if (this.startIncrement) {
/* 1233:1314 */                       for (Entity f : mostRecentMiss.getAllComponents())
/* 1234:     */                       {
/* 1235:1315 */                         Mark.say(new Object[] {f.asString(), " added to orderingMap at ", Integer.valueOf(this.orderCount) });
/* 1236:1316 */                         hashedMiss = f.hash();
/* 1237:1317 */                         ArrayList<Object> orderTypeTuple = new ArrayList();
/* 1238:1318 */                         orderTypeTuple.add(0, Integer.valueOf(this.orderCount));
/* 1239:1319 */                         orderTypeTuple.add(1, "MISSING");
/* 1240:1320 */                         this.orderingMap.put(hashedMiss, orderTypeTuple);
/* 1241:1321 */                         this.orderCount += 1;
/* 1242:     */                       }
/* 1243:     */                     }
/* 1244:1324 */                     recordIntoCompilation(this.recordToCompilation, mostRecentMiss, "MISSING");
/* 1245:     */                     
/* 1246:     */ 
/* 1247:     */ 
/* 1248:     */ 
/* 1249:     */ 
/* 1250:     */ 
/* 1251:     */ 
/* 1252:     */ 
/* 1253:     */ 
/* 1254:     */ 
/* 1255:     */ 
/* 1256:     */ 
/* 1257:     */ 
/* 1258:     */ 
/* 1259:1339 */                     String friendRule = "(r prediction (s conjuction (r enemy (t zz-516) (t xx-502)) (r enemy (t zz-516) (t yy-511))) (r friend (t yy-511) (t xx-502)))";
/* 1260:1340 */                     for (Entity rule : this.rules.getElements()) {
/* 1261:1349 */                       if (match.matchRuleToInstantiation(rule, mostRecentMiss) != null)
/* 1262:     */                       {
/* 1263:1350 */                         recordIntoCompilation(this.recordToCompilation, mostRecentMiss, "RULE");
/* 1264:1352 */                         if (this.rulesAlreadyReported.containsDeprecated(rule)) {
/* 1265:     */                           break;
/* 1266:     */                         }
/* 1267:1353 */                         this.rulesAlreadyReported.addElement(rule);
/* 1268:1354 */                         if (this.startIncrement) {
/* 1269:1355 */                           for (Entity f : mostRecentMiss.getAllComponents())
/* 1270:     */                           {
/* 1271:1356 */                             Mark.say(new Object[] {f.asString(), " added to orderingMap at ", Integer.valueOf(this.orderCount) });
/* 1272:1357 */                             String hashedMiss = f.hash();
/* 1273:1358 */                             ArrayList<Object> orderTypeTuple = new ArrayList();
/* 1274:1359 */                             orderTypeTuple.add(0, Integer.valueOf(this.orderCount));
/* 1275:1360 */                             orderTypeTuple.add(1, "RULE");
/* 1276:1361 */                             this.orderingMap.put(hashedMiss, orderTypeTuple);
/* 1277:1362 */                             this.orderCount += 1;
/* 1278:     */                           }
/* 1279:     */                         }
/* 1280:1365 */                         String mostRecentGenerated = generator.generateXPeriod(mostRecentMiss.getObject()) + " ";
/* 1281:1366 */                         String finalString = Html.red(mostRecentGenerated);
/* 1282:1367 */                         BetterSignal signal = new BetterSignal(new Object[] { "Story teller", finalString });
/* 1283:1368 */                         Connections.getPorts(this).transmit(signal);
/* 1284:1369 */                         String result = " because " + generator.generateAsIf(rule) + " ";
/* 1285:1370 */                         String finalResult = Html.blue(result);
/* 1286:1371 */                         if (result == null) {
/* 1287:     */                           break;
/* 1288:     */                         }
/* 1289:1372 */                         BetterSignal signalTwo = new BetterSignal(new Object[] { "Story teller", finalResult });
/* 1290:1373 */                         Connections.getPorts(this).transmit(signalTwo);
/* 1291:     */                         
/* 1292:1375 */                         Connections.getPorts(this).transmit(NEW_RULE_MESSENGER_PORT, Boolean.valueOf(true));
/* 1293:1376 */                         Connections.getPorts(this).transmit(TEACH_RULE_PORT, rule);
/* 1294:     */                         
/* 1295:     */ 
/* 1296:     */ 
/* 1297:     */ 
/* 1298:1381 */                         break;
/* 1299:     */                       }
/* 1300:     */                     }
/* 1301:     */                   }
/* 1302:     */                 }
/* 1303:     */               }
/* 1304:     */             }
/* 1305:     */           }
/* 1306:     */         }
/* 1307:1393 */         this.isOneQuiet = false;
/* 1308:1394 */         this.isTwoQuiet = false;
/* 1309:     */       }
/* 1310:     */     }
/* 1311:     */   }
/* 1312:     */   
/* 1313:     */   public void extractSummaryFromStory(Sequence explicitStory, Sequence reflectionElements, LinkedList<ArrayList<Object>> storyCompilation)
/* 1314:     */   {
/* 1315:1402 */     if (!Radio.tellStoryButton.isSelected()) {
/* 1316:1403 */       return;
/* 1317:     */     }
/* 1318:1405 */     Generator generator = Generator.getGenerator();
/* 1319:1406 */     StandardMatcher matcher = StandardMatcher.getBasicMatcher();
/* 1320:1407 */     Set<Entity> alreadyReported = new HashSet();
/* 1321:     */     
/* 1322:1409 */     Mark.say(new Object[] {"NECESSARY FOR REFLECTION : " });
/* 1323:1410 */     for (Entity t : reflectionElements.getAllComponents()) {
/* 1324:1411 */       Mark.say(new Object[] {t.asString() });
/* 1325:     */     }
/* 1326:1414 */     for (ArrayList<Object> recordedElement : storyCompilation)
/* 1327:     */     {
/* 1328:1415 */       Entity storyElement = (Entity)recordedElement.get(0);
/* 1329:1416 */       Mark.say(new Object[] {"Trying: ", storyElement.asString() });
/* 1330:1417 */       String type = (String)recordedElement.get(1);
/* 1331:1419 */       for (Entity t : reflectionElements.getAllComponents())
/* 1332:     */       {
/* 1333:1420 */         LList<PairOfEntities> match = matcher.match(t, storyElement);
/* 1334:1421 */         if (match != null) {
/* 1335:1422 */           if (type == "REGULAR")
/* 1336:     */           {
/* 1337:1423 */             if (!alreadyReported.contains(t)) {
/* 1338:1424 */               for (Entity d : explicitStory.getAllComponents())
/* 1339:     */               {
/* 1340:1425 */                 LList<PairOfEntities> explicitMatch = matcher.matchAnyPart(d, storyElement);
/* 1341:1426 */                 if (explicitMatch != null)
/* 1342:     */                 {
/* 1343:1428 */                   alreadyReported.add(t);
/* 1344:1429 */                   String toPublish = generator.generate(t);
/* 1345:1430 */                   BetterSignal toGuiTab = new BetterSignal(new Object[] { "Summary", toPublish });
/* 1346:1431 */                   Connections.getPorts(this).transmit(toGuiTab);
/* 1347:1432 */                   break;
/* 1348:     */                 }
/* 1349:     */               }
/* 1350:     */             }
/* 1351:     */           }
/* 1352:1437 */           else if (type == "MISSING")
/* 1353:     */           {
/* 1354:1438 */             if (!alreadyReported.contains(storyElement))
/* 1355:     */             {
/* 1356:1439 */               alreadyReported.add(storyElement);
/* 1357:1440 */               String toPublish = generator.generate(storyElement);
/* 1358:1441 */               String formatted = Html.red(toPublish);
/* 1359:1442 */               BetterSignal toGuiTab = new BetterSignal(new Object[] { "Summary", formatted });
/* 1360:1443 */               Connections.getPorts(this).transmit(toGuiTab);
/* 1361:1444 */               break;
/* 1362:     */             }
/* 1363:     */           }
/* 1364:1447 */           else if (type == "RULE")
/* 1365:     */           {
/* 1366:1448 */             String toPublish = generator.generate(storyElement);
/* 1367:1449 */             String formatted = Html.blue(toPublish);
/* 1368:1450 */             BetterSignal toGuiTab = new BetterSignal(new Object[] { "Summary", formatted });
/* 1369:1451 */             Connections.getPorts(this).transmit(toGuiTab);
/* 1370:1452 */             break;
/* 1371:     */           }
/* 1372:     */         }
/* 1373:     */       }
/* 1374:     */     }
/* 1375:     */   }
/* 1376:     */   
/* 1377:     */   public boolean usefulPartialMatch(LList<PairOfEntities> match)
/* 1378:     */   {
/* 1379:1461 */     if (!Radio.tellStoryButton.isSelected()) {
/* 1380:1462 */       return false;
/* 1381:     */     }
/* 1382:1464 */     if (match.first() != null)
/* 1383:     */     {
/* 1384:1465 */       Entity first_a = ((PairOfEntities)match.first()).getDatum();
/* 1385:1466 */       Entity first_b = ((PairOfEntities)match.first()).getPattern();
/* 1386:1467 */       if (match.second() != null)
/* 1387:     */       {
/* 1388:1468 */         Entity second_a = ((PairOfEntities)match.second()).getDatum();
/* 1389:1469 */         Entity second_b = ((PairOfEntities)match.second()).getPattern();
/* 1390:1470 */         if ((first_a.equals(first_b)) && (second_a.equals(second_b))) {
/* 1391:1471 */           return true;
/* 1392:     */         }
/* 1393:     */       }
/* 1394:1475 */       else if (first_a.equals(first_b))
/* 1395:     */       {
/* 1396:1476 */         return true;
/* 1397:     */       }
/* 1398:     */     }
/* 1399:1481 */     return false;
/* 1400:     */   }
/* 1401:     */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     silaSayan.StoryTeller
 * JD-Core Version:    0.7.0.1
 */