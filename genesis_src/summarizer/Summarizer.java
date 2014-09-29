/*    1:     */ package summarizer;
/*    2:     */ 
/*    3:     */ import Signals.BetterSignal;
/*    4:     */ import bridge.reps.entities.Bundle;
/*    5:     */ import bridge.reps.entities.Entity;
/*    6:     */ import bridge.reps.entities.Function;
/*    7:     */ import bridge.reps.entities.Relation;
/*    8:     */ import bridge.reps.entities.Sequence;
/*    9:     */ import connections.AbstractWiredBox;
/*   10:     */ import connections.Connections;
/*   11:     */ import connections.Ports;
/*   12:     */ import java.util.ArrayList;
/*   13:     */ import java.util.HashSet;
/*   14:     */ import java.util.Iterator;
/*   15:     */ import java.util.List;
/*   16:     */ import java.util.Set;
/*   17:     */ import java.util.Vector;
/*   18:     */ import matthewFay.Utilities.HashMatrix;
/*   19:     */ import parameters.Switch;
/*   20:     */ import persistence.JCheckBoxWithMemory;
/*   21:     */ import persistence.StringWithMemory;
/*   22:     */ import start.Generator;
/*   23:     */ import storyProcessor.ReflectionAnalysis;
/*   24:     */ import storyProcessor.ReflectionDescription;
/*   25:     */ import text.Html;
/*   26:     */ import text.Punctuator;
/*   27:     */ import tools.Predicates;
/*   28:     */ import utils.Mark;
/*   29:     */ 
/*   30:     */ public class Summarizer
/*   31:     */   extends AbstractWiredBox
/*   32:     */ {
/*   33:     */   public static Summarizer getSummarizer()
/*   34:     */   {
/*   35:  32 */     if (summarizer == null) {
/*   36:  33 */       summarizer = new Summarizer();
/*   37:     */     }
/*   38:  35 */     return summarizer;
/*   39:     */   }
/*   40:     */   
/*   41:     */   protected Summarizer()
/*   42:     */   {
/*   43:  43 */     setName("Local story processor");
/*   44:  44 */     Connections.getPorts(this).addSignalProcessor("reset", "assignAndClearTabs");
/*   45:  45 */     Connections.getPorts(this).addSignalProcessor("Left perspective input", "processSignalLeft");
/*   46:  46 */     Connections.getPorts(this).addSignalProcessor("Right perspective input", "processSignalRight");
/*   47:  47 */     Connections.getPorts(this).addSignalProcessor("selected tab", "processTab");
/*   48:  48 */     Connections.getPorts(this).addSignalProcessor("Concept port", "processConcept");
/*   49:     */     
/*   50:  50 */     initializeTable();
/*   51:     */   }
/*   52:     */   
/*   53:     */   public void processSignalLeft(Object signal)
/*   54:     */   {
/*   55:  58 */     if (!Switch.summarizeViaPHW.isSelected()) {
/*   56:  59 */       return;
/*   57:     */     }
/*   58:  61 */     if ((signal instanceof BetterSignal))
/*   59:     */     {
/*   60:  62 */       BetterSignal bs = (BetterSignal)signal;
/*   61:  63 */       this.leftAnalysis = ((Sequence)bs.get(0, Sequence.class));
/*   62:  64 */       this.leftSummaryDescription.clear();
/*   63:  65 */       this.rememberTab = false;
/*   64:  66 */       assignAndClearTabs("reset");
/*   65:     */       
/*   66:  68 */       BetterSignal s = (BetterSignal)signal;
/*   67:  69 */       if (s.size() < 5) {
/*   68:  70 */         return;
/*   69:     */       }
/*   70:  73 */       Sequence complete = pruneStory((Sequence)s.get(0, Sequence.class));
/*   71:  74 */       Sequence explicit = pruneStory((Sequence)s.get(1, Sequence.class));
/*   72:     */       
/*   73:  76 */       List<ReflectionDescription> conceptDescriptions = ((ReflectionAnalysis)s.get(4, ReflectionAnalysis.class)).getReflectionDescriptions();
/*   74:     */       
/*   75:  78 */       this.leftSummaryDescription.setConceptDescriptions(conceptDescriptions);
/*   76:  80 */       if (Switch.includeUnabriggedProcessing.isSelected()) {
/*   77:  81 */         composeCompleteStory(explicit, complete, this.leftSummaryDescription);
/*   78:     */       }
/*   79:  83 */       composeFromAntecedentsAndExplicitCauses(explicit, complete, this.leftSummaryDescription);
/*   80:  84 */       composeConceptCenteredSummaries(explicit, complete, conceptDescriptions, this.leftSummaryDescription);
/*   81:  85 */       composeDominantConceptCenteredSummary(explicit, complete, conceptDescriptions, this.leftSummaryDescription);
/*   82:     */       
/*   83:  87 */       transmitStatistics();
/*   84:  88 */       this.rememberTab = true;
/*   85:  89 */       Connections.getPorts(this).transmit("Report", new BetterSignal(new Object[] { this.rememberedTab.getString(), "" }));
/*   86:     */       
/*   87:     */ 
/*   88:     */ 
/*   89:  93 */       Connections.getPorts(this).transmit("summary", new BetterSignal(new Object[] { "summary", explicit, complete, conceptDescriptions }));
/*   90:     */     }
/*   91:     */   }
/*   92:     */   
/*   93:     */   public void processSignalRight(Object signal)
/*   94:     */   {
/*   95: 103 */     if (!Switch.summarizeViaPHW.isSelected()) {
/*   96: 104 */       return;
/*   97:     */     }
/*   98: 106 */     if ((signal instanceof BetterSignal))
/*   99:     */     {
/*  100: 107 */       BetterSignal bs = (BetterSignal)signal;
/*  101: 108 */       this.rightAnalysis = ((Sequence)bs.get(0, Sequence.class));
/*  102: 109 */       this.rightSummaryDescription.clear();
/*  103: 110 */       this.rememberTab = false;
/*  104:     */       
/*  105: 112 */       BetterSignal s = (BetterSignal)signal;
/*  106: 113 */       if (s.size() < 5) {
/*  107: 114 */         return;
/*  108:     */       }
/*  109: 117 */       Sequence complete = pruneStory((Sequence)s.get(0, Sequence.class));
/*  110: 118 */       Sequence explicit = pruneStory((Sequence)s.get(1, Sequence.class));
/*  111: 119 */       List<ReflectionDescription> conceptDescriptions = ((ReflectionAnalysis)s.get(4, ReflectionAnalysis.class)).getReflectionDescriptions();
/*  112:     */       
/*  113: 121 */       this.rightSummaryDescription.setConceptDescriptions(conceptDescriptions);
/*  114: 123 */       if (Switch.includeUnabriggedProcessing.isSelected()) {
/*  115: 124 */         composeCompleteStory(explicit, complete, this.rightSummaryDescription);
/*  116:     */       }
/*  117: 126 */       composeFromAntecedentsAndExplicitCauses(explicit, complete, this.rightSummaryDescription);
/*  118: 127 */       composeConceptCenteredSummaries(explicit, complete, conceptDescriptions, this.rightSummaryDescription);
/*  119: 128 */       composeDominantConceptCenteredSummary(explicit, complete, conceptDescriptions, this.rightSummaryDescription);
/*  120:     */       
/*  121: 130 */       compareSummaryDescriptions(this.leftSummaryDescription, this.rightSummaryDescription);
/*  122:     */       
/*  123: 132 */       transmitStatistics();
/*  124: 133 */       this.rememberTab = true;
/*  125: 134 */       Connections.getPorts(this).transmit("Report", new BetterSignal(new Object[] { this.rememberedTab.getString(), "" }));
/*  126:     */     }
/*  127:     */   }
/*  128:     */   
/*  129:     */   private void compareSummaryDescriptions(SummaryDescription left, SummaryDescription right)
/*  130:     */   {
/*  131: 140 */     HashSet<String> leftNames = new HashSet();
/*  132: 141 */     HashSet<String> rightNames = new HashSet();
/*  133: 142 */     List<String> missingInRight = new ArrayList();
/*  134: 143 */     List<String> missingInLeft = new ArrayList();
/*  135: 144 */     for (ReflectionDescription r : left.getConceptDescriptions()) {
/*  136: 145 */       leftNames.add(r.getName());
/*  137:     */     }
/*  138: 147 */     for (ReflectionDescription r : right.getConceptDescriptions()) {
/*  139: 148 */       rightNames.add(r.getName());
/*  140:     */     }
/*  141: 150 */     for (String s : leftNames) {
/*  142: 151 */       if (!rightNames.contains(s)) {
/*  143: 152 */         missingInRight.add(s);
/*  144:     */       }
/*  145:     */     }
/*  146: 155 */     for (String s : rightNames) {
/*  147: 156 */       if (!leftNames.contains(s)) {
/*  148: 157 */         missingInLeft.add(s);
/*  149:     */       }
/*  150:     */     }
/*  151: 160 */     String output = "I note that ";
/*  152: 161 */     if (!missingInRight.isEmpty())
/*  153:     */     {
/*  154: 162 */       output = output + Punctuator.punctuateAnd(missingInRight);
/*  155: 163 */       if (missingInRight.size() == 1) {
/*  156: 164 */         output = output + " no longer occurs";
/*  157:     */       } else {
/*  158: 167 */         output = output + " no longer occur";
/*  159:     */       }
/*  160:     */     }
/*  161: 170 */     if ((missingInRight.isEmpty()) && (missingInLeft.isEmpty())) {
/*  162: 171 */       output = output + "there are no noted conceptual differences.";
/*  163: 173 */     } else if ((!missingInRight.isEmpty()) && (missingInLeft.isEmpty())) {
/*  164: 174 */       output = output + "although nothing is missing, ";
/*  165: 176 */     } else if ((missingInRight.isEmpty()) && (!missingInLeft.isEmpty())) {
/*  166: 177 */       output = output + ".";
/*  167: 179 */     } else if ((!missingInRight.isEmpty()) && (!missingInLeft.isEmpty())) {
/*  168: 180 */       output = output + "; but ";
/*  169:     */     }
/*  170: 182 */     if (!missingInLeft.isEmpty())
/*  171:     */     {
/*  172: 183 */       output = output + Punctuator.punctuateAnd(missingInLeft);
/*  173: 184 */       if (missingInRight.size() == 1) {
/*  174: 185 */         output = output + " does occur.";
/*  175:     */       } else {
/*  176: 188 */         output = output + " do occur.";
/*  177:     */       }
/*  178:     */     }
/*  179: 191 */     Mark.say(new Object[] {output });
/*  180:     */     
/*  181: 193 */     Connections.getPorts(this).transmit("Report", new BetterSignal(new Object[] { type4, Html.h2(Html.normal("What-if results")) }));
/*  182:     */     
/*  183: 195 */     Connections.getPorts(this).transmit("Report", new BetterSignal(new Object[] { type4, Html.p(Html.normal(output)) }));
/*  184:     */   }
/*  185:     */   
/*  186:     */   public void composeCompleteStory(Sequence explicit, Sequence complete, SummaryDescription summaryDescription)
/*  187:     */   {
/*  188: 203 */     boolean abductionMemory = Switch.abductionProcessing.isSelected();
/*  189: 204 */     boolean meansMemory = Switch.meansProcessing.isSelected();
/*  190: 205 */     boolean postHocMemory = Switch.postHocProcessing.isSelected();
/*  191:     */     
/*  192: 207 */     Switch.abductionProcessing.setSelected(false);
/*  193: 208 */     Switch.meansProcessing.setSelected(false);
/*  194: 209 */     Switch.postHocProcessing.setSelected(false);
/*  195:     */     
/*  196: 211 */     Set<Entity> keepers = new HashSet();
/*  197: 212 */     keepers.addAll(explicit.getElements());
/*  198: 213 */     summaryDescription.setCompleteStory(keepers);
/*  199: 214 */     tell(type1, explicit, explicit, keepers, null);
/*  200:     */     
/*  201:     */ 
/*  202: 217 */     Switch.abductionProcessing.setSelected(abductionMemory);
/*  203: 218 */     Switch.meansProcessing.setSelected(meansMemory);
/*  204: 219 */     Switch.postHocProcessing.setSelected(postHocMemory);
/*  205:     */   }
/*  206:     */   
/*  207:     */   public void composeFromAntecedentsAndExplicitCauses(Sequence explicit, Sequence complete, SummaryDescription summaryDescription)
/*  208:     */   {
/*  209: 227 */     Set<Entity> keepers = retainIfAntecedentOrExplicitCause(explicit, complete);
/*  210: 228 */     keepers = removeIfInCause(keepers);
/*  211: 229 */     summaryDescription.setConnected(keepers);
/*  212: 230 */     tell(type2, explicit, complete, keepers, null);
/*  213:     */   }
/*  214:     */   
/*  215:     */   public Set<Entity> retainIfAntecedentOrExplicitCause(Sequence explicit, Sequence complete)
/*  216:     */   {
/*  217: 234 */     Set<Entity> keepers = new HashSet();
/*  218: 235 */     for (Entity element : complete.getElements()) {
/*  219: 236 */       if (appearsIn(element, explicit)) {
/*  220: 237 */         if (Predicates.isCause(element)) {
/*  221: 238 */           keepers.add(element);
/*  222: 240 */         } else if (antecedentButNotConsequentIn(element, complete)) {
/*  223: 241 */           keepers.add(element);
/*  224:     */         }
/*  225:     */       }
/*  226:     */     }
/*  227: 245 */     return keepers;
/*  228:     */   }
/*  229:     */   
/*  230:     */   public void composeConceptCenteredSummaries(Sequence explicit, Sequence complete, List<ReflectionDescription> conceptDescriptions, SummaryDescription summaryDescription)
/*  231:     */   {
/*  232: 256 */     Set<Entity> conceptFeeders = removeIfInCause(keepIfConceptFeeder(complete, conceptDescriptions));
/*  233:     */     
/*  234: 258 */     Set<String> names = extractNames(conceptDescriptions);
/*  235:     */     
/*  236: 260 */     summaryDescription.setConcept(conceptFeeders);
/*  237:     */     
/*  238: 262 */     tell(type4, explicit, complete, conceptFeeders, names);
/*  239:     */     
/*  240:     */ 
/*  241:     */ 
/*  242: 266 */     Set<Entity> explicitElements = findExplicitElements(complete);
/*  243:     */     
/*  244: 268 */     Set<Entity> selectedElements = pickAtRandom(conceptFeeders.size(), explicitElements);
/*  245:     */     
/*  246: 270 */     summaryDescription.setRandom(selectedElements);
/*  247:     */     
/*  248: 272 */     tell(type0, explicit, complete, selectedElements, null);
/*  249:     */   }
/*  250:     */   
/*  251:     */   public void composeDominantConceptCenteredSummary(Sequence explicit, Sequence complete, List<ReflectionDescription> conceptDescriptions, SummaryDescription summaryDescription)
/*  252:     */   {
/*  253: 283 */     ArrayList<ReflectionDescription> biggestConcepts = limitToRelevantConcepts(conceptDescriptions);
/*  254:     */     
/*  255: 285 */     Set<Entity> conceptFeeders = removeIfInCause(keepIfConceptFeeder(complete, biggestConcepts));
/*  256:     */     
/*  257: 287 */     Set<String> names = extractNames(biggestConcepts);
/*  258:     */     
/*  259: 289 */     summaryDescription.setDominant(conceptFeeders);
/*  260:     */     
/*  261: 291 */     tell(type5, explicit, complete, conceptFeeders, names);
/*  262:     */     
/*  263:     */ 
/*  264:     */ 
/*  265:     */ 
/*  266:     */ 
/*  267: 297 */     noteUnresolvedQuestions(questions, complete.getType(), complete, conceptFeeders, summaryDescription);
/*  268:     */     
/*  269:     */ 
/*  270:     */ 
/*  271: 301 */     ArrayList<ReflectionDescription> testConcepts = new ArrayList();
/*  272: 303 */     for (ReflectionDescription candidate : conceptDescriptions) {
/*  273: 304 */       if (candidate.getName().equalsIgnoreCase(testConcept)) {
/*  274: 305 */         testConcepts.add(candidate);
/*  275:     */       }
/*  276:     */     }
/*  277: 309 */     conceptFeeders = removeIfInCause(keepIfConceptFeeder(complete, testConcepts));
/*  278:     */     
/*  279: 311 */     names = extractNames(testConcepts);
/*  280:     */     
/*  281: 313 */     summaryDescription.setSpecial(conceptFeeders);
/*  282:     */     
/*  283: 315 */     tell(type6, explicit, complete, conceptFeeders, names);
/*  284:     */   }
/*  285:     */   
/*  286:     */   protected ArrayList<ReflectionDescription> limitToRelevantConcepts(List<ReflectionDescription> conceptDescriptions)
/*  287:     */   {
/*  288: 320 */     ArrayList<ReflectionDescription> biggestConcepts = new ArrayList();
/*  289: 321 */     int maxSize = 0;
/*  290: 322 */     for (ReflectionDescription candidate : conceptDescriptions)
/*  291:     */     {
/*  292: 324 */       int size = 0;
/*  293:     */       
/*  294: 326 */       size = candidate.getStoryElementsInvolved().getElements().size();
/*  295: 328 */       if (size == maxSize)
/*  296:     */       {
/*  297: 329 */         biggestConcepts.add(candidate);
/*  298:     */       }
/*  299: 332 */       else if (size > maxSize)
/*  300:     */       {
/*  301: 333 */         maxSize = size;
/*  302: 334 */         biggestConcepts.clear();
/*  303: 335 */         biggestConcepts.add(candidate);
/*  304:     */       }
/*  305:     */     }
/*  306: 341 */     return biggestConcepts;
/*  307:     */   }
/*  308:     */   
/*  309:     */   private void tell(String mode, Sequence explicitStorySequence, Sequence completeStorySequence, Set<Entity> relevantElements, Set<String> conceptNames)
/*  310:     */   {
/*  311: 353 */     Set<Entity> entities = getEntities(relevantElements);
/*  312: 354 */     Set<Entity> roleElements = new HashSet();
/*  313: 356 */     if (Switch.includeAgentRolesInSummary.isSelected()) {
/*  314: 357 */       roleElements = extractRoleElements(completeStorySequence, entities);
/*  315:     */     }
/*  316: 361 */     if (Switch.meansProcessing.isSelected()) {
/*  317: 362 */       relevantElements = filterUsingMeansSuppression(relevantElements);
/*  318:     */     }
/*  319: 365 */     if (Switch.abductionProcessing.isSelected()) {
/*  320: 366 */       relevantElements = filterUsingAbductionSuppression(relevantElements);
/*  321:     */     }
/*  322: 370 */     relevantElements = removeIfInCause(relevantElements);
/*  323:     */     
/*  324: 372 */     Sequence elementsRetained = filterUsingRelevantElements(completeStorySequence, relevantElements, roleElements);
/*  325: 375 */     if (Switch.postHocProcessing.isSelected()) {
/*  326: 376 */       elementsRetained = filterUsingPostHocErgoPropterHoc(elementsRetained);
/*  327:     */     }
/*  328: 379 */     String name = completeStorySequence.getType();
/*  329:     */     
/*  330: 381 */     int orignalSize = storySize(explicitStorySequence);
/*  331:     */     
/*  332: 383 */     int summarySize = storySize(elementsRetained);
/*  333:     */     
/*  334: 385 */     String revisedStory = composeStoryEnglishFromEntities(elementsRetained);
/*  335:     */     
/*  336: 387 */     String summary = composeCompleteSummaryDescription(mode, name, conceptNames, orignalSize, summarySize, revisedStory);
/*  337:     */     
/*  338: 389 */     Connections.getPorts(this).transmit("Report", new BetterSignal(new Object[] { mode, summary }));
/*  339:     */   }
/*  340:     */   
/*  341:     */   public static Sequence filterUsingRelevantElements(Sequence completeStorySequence, Set<Entity> relevantElements, Set<Entity> roleElements)
/*  342:     */   {
/*  343: 396 */     boolean debug = false;
/*  344: 397 */     Sequence elementsRetained = new Sequence();
/*  345:     */     
/*  346: 399 */     Set<Entity> alreadyProcessed = new HashSet();
/*  347: 401 */     for (Entity element : completeStorySequence.getElements()) {
/*  348: 402 */       if (relevantElements.contains(element))
/*  349:     */       {
/*  350: 403 */         elementsRetained.addElement(element);
/*  351:     */       }
/*  352: 406 */       else if (roleElements.contains(element))
/*  353:     */       {
/*  354: 407 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Element to generate", element });
/*  355: 408 */         alreadyProcessed.add(element);
/*  356: 409 */         elementsRetained.addElement(element);
/*  357:     */       }
/*  358:     */     }
/*  359: 412 */     return elementsRetained;
/*  360:     */   }
/*  361:     */   
/*  362:     */   public static Sequence filterUsingPostHocErgoPropterHoc(Sequence completeStorySequence)
/*  363:     */   {
/*  364: 416 */     boolean debug = false;
/*  365: 417 */     Sequence elementsRetained = new Sequence();
/*  366:     */     
/*  367: 419 */     Set<Entity> alreadyProcessed = new HashSet();
/*  368:     */     
/*  369: 421 */     Entity penultimate = null;
/*  370: 422 */     for (Entity element : completeStorySequence.getElements()) {
/*  371: 424 */       if (!alreadyProcessed.contains(element))
/*  372:     */       {
/*  373: 429 */         if ((penultimate != null) && 
/*  374: 430 */           (Predicates.isCause(element)))
/*  375:     */         {
/*  376: 431 */           List<Entity> elementAntecedents = element.getSubject().getElements();
/*  377: 433 */           if (Predicates.contained(penultimate, elementAntecedents))
/*  378:     */           {
/*  379: 435 */             Mark.say(new Object[] {Boolean.valueOf(debug), "Post hoc processing triggered on", element });
/*  380: 436 */             element = copyWithoutGivenAntecedent(penultimate, element);
/*  381: 437 */             Mark.say(new Object[] {Boolean.valueOf(debug), "Post hoc processing trims to", element });
/*  382:     */           }
/*  383:     */         }
/*  384: 441 */         alreadyProcessed.add(element);
/*  385: 442 */         elementsRetained.addElement(element);
/*  386:     */         
/*  387: 444 */         penultimate = element;
/*  388: 445 */         if (Predicates.isCause(penultimate)) {
/*  389: 446 */           penultimate = penultimate.getObject();
/*  390:     */         }
/*  391:     */       }
/*  392:     */     }
/*  393: 449 */     return elementsRetained;
/*  394:     */   }
/*  395:     */   
/*  396:     */   public static Set<Entity> filterUsingMeansSuppression(Set<Entity> relevantElements)
/*  397:     */   {
/*  398: 475 */     boolean debug = false;
/*  399: 476 */     Set<Entity> elementsRetained = new HashSet();
/*  400:     */     
/*  401: 478 */     Set<Entity> alreadyProcessed = new HashSet();
/*  402: 479 */     for (Entity element : relevantElements) {
/*  403: 481 */       if (!alreadyProcessed.contains(element))
/*  404:     */       {
/*  405: 484 */         if (Predicates.isMeans(element)) {
/*  406: 485 */           element = element.getObject();
/*  407:     */         }
/*  408: 487 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Ordinary element to generate", element });
/*  409: 488 */         alreadyProcessed.add(element);
/*  410: 489 */         elementsRetained.add(element);
/*  411:     */       }
/*  412:     */     }
/*  413: 491 */     return elementsRetained;
/*  414:     */   }
/*  415:     */   
/*  416:     */   protected Set<Entity> filterUsingAbductionSuppression(Set<Entity> relevantElements)
/*  417:     */   {
/*  418: 495 */     boolean debug = false;
/*  419: 496 */     Set<Entity> elementsRetained = new HashSet();
/*  420:     */     
/*  421: 498 */     Set<Entity> alreadyProcessed = new HashSet();
/*  422: 499 */     for (Entity element : relevantElements) {
/*  423: 501 */       if (!alreadyProcessed.contains(element)) {
/*  424: 505 */         if (element.isA("abduction"))
/*  425:     */         {
/*  426: 507 */           Mark.say(new Object[] {Boolean.valueOf(debug), "Abduction element to generate", element });
/*  427: 508 */           alreadyProcessed.add(element);
/*  428: 509 */           alreadyProcessed.add(element.getObject());
/*  429: 510 */           elementsRetained.add(element);
/*  430:     */         }
/*  431:     */         else
/*  432:     */         {
/*  433: 513 */           Mark.say(new Object[] {Boolean.valueOf(debug), "Ordinary element to generate", element });
/*  434: 514 */           alreadyProcessed.add(element);
/*  435: 515 */           elementsRetained.add(element);
/*  436:     */         }
/*  437:     */       }
/*  438:     */     }
/*  439: 518 */     return elementsRetained;
/*  440:     */   }
/*  441:     */   
/*  442:     */   protected String composeStoryEnglishFromEntities(Sequence story)
/*  443:     */   {
/*  444: 522 */     String english = "";
/*  445: 523 */     for (Entity entity : story.getElements())
/*  446:     */     {
/*  447: 524 */       String sentence = Generator.getGenerator().generate(entity);
/*  448: 525 */       if (sentence != null) {
/*  449: 526 */         english = english + sentence.trim() + "  ";
/*  450:     */       } else {
/*  451: 529 */         Mark.err(new Object[] {"Unexpected null sentence in Summarizer" });
/*  452:     */       }
/*  453:     */     }
/*  454: 532 */     return english;
/*  455:     */   }
/*  456:     */   
/*  457:     */   private String composeCompleteSummaryDescription(String mode, String name, Set<String> conceptNames, int storySize, int asToldSize, String revisedStory)
/*  458:     */   {
/*  459: 536 */     String percent = String.format("%3.1f", new Object[] { Double.valueOf(asToldSize * 100.0D / storySize) });
/*  460: 538 */     if (this.row.isEmpty()) {
/*  461: 539 */       this.row.add(Html.size3(Html.bold(Punctuator.conditionName(name))));
/*  462:     */     }
/*  463: 542 */     if ((mode != type0) && (mode != type6)) {
/*  464: 543 */       this.row.add(Html.size3(percent));
/*  465:     */     }
/*  466: 546 */     String summary = "";
/*  467: 548 */     if ((conceptNames != null) && (!conceptNames.isEmpty()))
/*  468:     */     {
/*  469: 549 */       List<String> names = new ArrayList(conceptNames);
/*  470: 550 */       summary = summary + "The story is about " + Punctuator.punctuateAnd(names) + ".  ";
/*  471:     */     }
/*  472: 553 */     if (revisedStory.trim().isEmpty()) {
/*  473: 554 */       summary = summary + "  There is no summary based on the indicated conceptual content.";
/*  474:     */     }
/*  475: 557 */     summary = summary + Html.p(revisedStory);
/*  476:     */     
/*  477: 559 */     summary = summary + Html.p(new StringBuilder("Summary contains ").append(asToldSize).append(" of ").append(storySize).append(" elements in the story or ").append(percent).append("%.").toString());
/*  478:     */     
/*  479:     */ 
/*  480:     */ 
/*  481: 563 */     summary = Html.h2(new StringBuilder(String.valueOf(mode)).append(" summary of ").append(Punctuator.conditionName(name)).toString()) + Html.p(Html.normal(summary));
/*  482: 564 */     return summary;
/*  483:     */   }
/*  484:     */   
/*  485:     */   private void noteUnresolvedQuestions(String mode, String name, Sequence analysis, Set<Entity> summaryElements, SummaryDescription summaryDescription)
/*  486:     */   {
/*  487: 571 */     Set<Entity> processed = new HashSet();
/*  488: 572 */     Set<Entity> unprocessed = new HashSet(summaryElements);
/*  489: 573 */     List<Entity> questions = new ArrayList();
/*  490:     */     
/*  491: 575 */     Generator generator = Generator.getGenerator();
/*  492:     */     
/*  493: 577 */     String summary = "";
/*  494:     */     Iterator localIterator2;
/*  495:     */     label126:
/*  496: 579 */     for (Iterator localIterator1 = summaryElements.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/*  497:     */     {
/*  498: 579 */       Entity element = (Entity)localIterator1.next();
/*  499: 580 */       if ((!Predicates.isCause(element)) || (Predicates.isMeans(element))) {
/*  500:     */         break label126;
/*  501:     */       }
/*  502: 581 */       localIterator2 = element.getSubject().getElements().iterator(); continue;Entity antecedent = (Entity)localIterator2.next();
/*  503: 582 */       unprocessed.add(antecedent);
/*  504:     */     }
/*  505: 586 */     for (Entity element : unprocessed) {
/*  506: 588 */       if (!processed.contains(element))
/*  507:     */       {
/*  508: 593 */         if ((!Predicates.isCause(element)) && (element.isA("action")) && 
/*  509: 594 */           (!isConsequent(element, analysis))) {
/*  510: 596 */           questions.add(element);
/*  511:     */         }
/*  512: 602 */         processed.add(element);
/*  513:     */       }
/*  514:     */     }
/*  515: 604 */     summaryDescription.setQuestions(questions);
/*  516: 605 */     if (questions.isEmpty())
/*  517:     */     {
/*  518: 606 */       summary = summary + "  There are no questions.";
/*  519: 607 */       summary = summary + Html.p("");
/*  520:     */     }
/*  521:     */     else
/*  522:     */     {
/*  523: 610 */       summary = summary + "<ul>";
/*  524: 611 */       for (Entity element : questions)
/*  525:     */       {
/*  526: 612 */         String question = generator.generateInPastTense(new Function("why", element));
/*  527: 613 */         if (question != null) {
/*  528: 614 */           summary = summary + "<li>" + question + "</li>\n";
/*  529:     */         } else {
/*  530: 617 */           Mark.err(new Object[] {"Could not generate question from", element });
/*  531:     */         }
/*  532:     */       }
/*  533: 620 */       summary = summary + "</ul>";
/*  534:     */     }
/*  535: 622 */     summary = summary + "Questions are about unexplained actions that lead to the central concept.";
/*  536:     */     
/*  537: 624 */     summary = Html.h2(new StringBuilder(String.valueOf(mode)).append(" in ").append(Punctuator.conditionName(name)).toString()) + Html.normal(summary);
/*  538:     */     
/*  539: 626 */     Connections.getPorts(this).transmit("Report", new BetterSignal(new Object[] { mode, summary }));
/*  540:     */   }
/*  541:     */   
/*  542:     */   public void assignAndClearTabs(Object o)
/*  543:     */   {
/*  544: 633 */     if (o == "reset")
/*  545:     */     {
/*  546: 634 */       this.leftSummaryDescription = new SummaryDescription();
/*  547: 635 */       this.rightSummaryDescription = new SummaryDescription();
/*  548: 636 */       this.rememberTab = false;
/*  549: 637 */       for (String s : this.modes)
/*  550:     */       {
/*  551: 638 */         Connections.getPorts(this).transmit("Report", new BetterSignal(new Object[] { s, "clear" }));
/*  552: 639 */         Connections.getPorts(this).transmit("Report", new BetterSignal(new Object[] { s, "clear" }));
/*  553:     */       }
/*  554: 641 */       this.rememberTab = true;
/*  555:     */       
/*  556: 643 */       Connections.getPorts(this).transmit("Report", new BetterSignal(new Object[] { this.rememberedTab.getString(), "" }));
/*  557:     */     }
/*  558:     */   }
/*  559:     */   
/*  560:     */   public void initializeTable()
/*  561:     */   {
/*  562: 651 */     this.table = Html.tableWithPadding(15, new String[] { Html.size3("Story"), Html.size3(type2), Html.size3("Concepts"), Html.size3("Dominant") });
/*  563: 652 */     Connections.getPorts(this).transmit("Report", new BetterSignal(new Object[] { stats, "clear" }));
/*  564: 653 */     Connections.getPorts(this).transmit("Report", new BetterSignal(new Object[] { stats, this.table }));
/*  565:     */   }
/*  566:     */   
/*  567:     */   private boolean isConsequent(Entity element, Sequence analysis)
/*  568:     */   {
/*  569: 657 */     for (Entity x : analysis.getElements()) {
/*  570: 658 */       if ((Predicates.isCause(x)) && (Predicates.equals(x.getObject(), element))) {
/*  571: 659 */         return true;
/*  572:     */       }
/*  573:     */     }
/*  574: 662 */     return false;
/*  575:     */   }
/*  576:     */   
/*  577:     */   public void transmitStatistics()
/*  578:     */   {
/*  579: 670 */     this.table = Html.tableAddRow(this.table, this.row);
/*  580: 671 */     this.row.clear();
/*  581: 672 */     Connections.getPorts(this).transmit("Report", new BetterSignal(new Object[] { stats, "clear" }));
/*  582: 673 */     Connections.getPorts(this).transmit("Report", new BetterSignal(new Object[] { stats, this.table }));
/*  583:     */   }
/*  584:     */   
/*  585:     */   public Set<Entity> keepIfConceptFeeder(Sequence story, List<ReflectionDescription> conceptCandidates)
/*  586:     */   {
/*  587: 680 */     Set<Entity> feeders = new HashSet();
/*  588:     */     Iterator localIterator2;
/*  589: 681 */     for (Iterator localIterator1 = conceptCandidates.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/*  590:     */     {
/*  591: 681 */       ReflectionDescription description = (ReflectionDescription)localIterator1.next();
/*  592: 682 */       Sequence storyElementsInvolved = description.getStoryElementsInvolved();
/*  593: 683 */       localIterator2 = storyElementsInvolved.getElements().iterator(); continue;Entity e = (Entity)localIterator2.next();
/*  594:     */       
/*  595: 685 */       feeders.addAll(findFeeders(e, storyElementsInvolved, story));
/*  596:     */     }
/*  597: 688 */     return feeders;
/*  598:     */   }
/*  599:     */   
/*  600:     */   private static Entity copyWithoutGivenAntecedent(Entity penultimate, Entity cause)
/*  601:     */   {
/*  602: 692 */     Sequence sequence = new Sequence("conjuction");
/*  603: 693 */     for (Entity antecedent : cause.getSubject().getElements()) {
/*  604: 694 */       if (!Predicates.equals(penultimate, antecedent)) {
/*  605: 696 */         sequence.addElement(antecedent);
/*  606:     */       }
/*  607:     */     }
/*  608: 703 */     if (sequence.getElements().isEmpty()) {
/*  609: 704 */       return cause.getObject();
/*  610:     */     }
/*  611: 707 */     Relation copy = new Relation("cause", sequence, cause.getObject());
/*  612: 708 */     copy.setBundle((Bundle)cause.getBundle().clone());
/*  613: 709 */     return copy;
/*  614:     */   }
/*  615:     */   
/*  616:     */   public String addDescription(String mode)
/*  617:     */   {
/*  618: 713 */     String summary = "";
/*  619: 714 */     if (mode == type0) {
/*  620: 715 */       summary = "\nSummary consists of random elements in the story.";
/*  621: 717 */     } else if (mode == type1) {
/*  622: 718 */       summary = "\nComplete story consists of all explicit elements.";
/*  623: 720 */     } else if (mode == type2) {
/*  624: 721 */       summary = "\nSummary consists of all elements in the story that cause or are caused.";
/*  625: 723 */     } else if (mode == type4) {
/*  626: 724 */       summary = "\nSummary consists of all elements in the story that cause, but are not caused, and lead to concepts, plus helpful inferences.";
/*  627: 726 */     } else if (mode == type5) {
/*  628: 727 */       summary = "\nSummary consists of all elements in the story that cause, but are not caused, and lead to key concept, plus helpful inferences.";
/*  629: 729 */     } else if (mode == type6) {
/*  630: 730 */       summary = 
/*  631: 731 */         "\nSummary consists of all elements in the story that cause, but are not caused, and lead to " + testConcept + " concept, plus helpful inferences.";
/*  632:     */     }
/*  633: 733 */     summary = summary + "  ";
/*  634: 734 */     return Html.br(summary);
/*  635:     */   }
/*  636:     */   
/*  637:     */   protected int storySize(Sequence story)
/*  638:     */   {
/*  639: 738 */     return countElements(story.getElements());
/*  640:     */   }
/*  641:     */   
/*  642:     */   private int countElements(List<Entity> list)
/*  643:     */   {
/*  644: 746 */     int r = 0;
/*  645: 747 */     Set<String> elements = new HashSet();
/*  646: 748 */     for (Entity e : list)
/*  647:     */     {
/*  648: 749 */       if (Predicates.isCause(e))
/*  649:     */       {
/*  650: 750 */         Entity consequent = e.getObject();
/*  651: 751 */         Vector<Entity> antecedents = e.getSubject().getElements();
/*  652: 753 */         for (Entity x : antecedents) {
/*  653: 754 */           if (!elements.contains(x.toString()))
/*  654:     */           {
/*  655: 755 */             r++;
/*  656: 756 */             elements.add(x.toString());
/*  657:     */           }
/*  658:     */         }
/*  659: 760 */         if (!elements.contains(consequent.toString()))
/*  660:     */         {
/*  661: 761 */           r++;
/*  662: 762 */           elements.add(consequent.toString());
/*  663:     */         }
/*  664:     */       }
/*  665: 766 */       if (!elements.contains(e.toString()))
/*  666:     */       {
/*  667: 767 */         elements.add(e.toString());
/*  668: 768 */         r++;
/*  669:     */       }
/*  670:     */     }
/*  671: 771 */     return r;
/*  672:     */   }
/*  673:     */   
/*  674:     */   private Sequence pruneStory(Sequence story)
/*  675:     */   {
/*  676: 778 */     boolean debug = false;
/*  677: 779 */     Sequence result = new Sequence();
/*  678: 780 */     result.setBundle(story.getBundle());
/*  679: 781 */     for (Entity element : story.getElements()) {
/*  680: 782 */       if ((element.relationP("classification")) && (element.getSubject().isA("character"))) {
/*  681: 783 */         Mark.say(new Object[] {Boolean.valueOf(debug), "No character classification in summary" });
/*  682: 785 */       } else if (element.getSubject().isA("i")) {
/*  683: 786 */         Mark.say(new Object[] {Boolean.valueOf(debug), "No I in summary" });
/*  684: 788 */       } else if (element.relationP("start")) {
/*  685: 789 */         Mark.say(new Object[] {Boolean.valueOf(debug), "No Start in summary" });
/*  686:     */       } else {
/*  687: 792 */         result.addElement(element);
/*  688:     */       }
/*  689:     */     }
/*  690: 795 */     return result;
/*  691:     */   }
/*  692:     */   
/*  693:     */   public Set<Entity> extractRoleElements(Sequence story, Set<Entity> entities)
/*  694:     */   {
/*  695: 803 */     Set<Entity> results = new HashSet();
/*  696:     */     
/*  697: 805 */     Set<Entity> included = new HashSet();
/*  698: 806 */     for (Entity element : story.getElements()) {
/*  699: 807 */       if ((element.relationP("position")) && (entities.contains(element.getSubject())) && 
/*  700: 808 */         (element.getSubject().getProperty("proper") != null))
/*  701:     */       {
/*  702: 809 */         included.add(element.getSubject());
/*  703: 810 */         results.add(element);
/*  704:     */       }
/*  705:     */     }
/*  706: 814 */     for (Entity element : story.getElements()) {
/*  707: 815 */       if (element.relationP("classification")) {
/*  708: 817 */         if ((!element.getSubject().getType().equals("character")) && 
/*  709: 818 */           (entities.contains(element.getObject())) && 
/*  710: 819 */           (element.getObject().getProperty("proper") != null)) {
/*  711: 821 */           if (!included.contains(element.getObject())) {
/*  712: 822 */             results.add(element);
/*  713:     */           }
/*  714:     */         }
/*  715:     */       }
/*  716:     */     }
/*  717: 829 */     for (Entity element : story.getElements()) {
/*  718: 830 */       if ((element.relationP("personality_trait")) || (element.relationP("property"))) {
/*  719: 832 */         results.add(element);
/*  720:     */       }
/*  721:     */     }
/*  722: 836 */     return results;
/*  723:     */   }
/*  724:     */   
/*  725:     */   protected Set<Entity> removeIfInCause(Set<Entity> elements)
/*  726:     */   {
/*  727: 843 */     Set<Entity> result = new HashSet();
/*  728: 844 */     for (Entity candidate : elements)
/*  729:     */     {
/*  730: 845 */       boolean found = false;
/*  731: 846 */       for (Entity possibleCause : elements) {
/*  732: 847 */         if (Predicates.isCause(possibleCause)) {
/*  733: 849 */           if (candidate.isA("abduction"))
/*  734:     */           {
/*  735: 850 */             if ((!Predicates.equals(possibleCause, candidate)) && (Predicates.equals(candidate.getObject(), possibleCause.getObject())))
/*  736:     */             {
/*  737: 851 */               found = true;
/*  738: 852 */               break;
/*  739:     */             }
/*  740:     */           }
/*  741:     */           else
/*  742:     */           {
/*  743: 856 */             if (Predicates.equals(candidate, possibleCause.getObject()))
/*  744:     */             {
/*  745: 857 */               found = true;
/*  746: 858 */               break;
/*  747:     */             }
/*  748: 861 */             if ((possibleCause.getSubject().getElements() != null) && 
/*  749: 862 */               (possibleCause.getSubject().getElements().contains(candidate)))
/*  750:     */             {
/*  751: 863 */               found = true;
/*  752: 864 */               break;
/*  753:     */             }
/*  754:     */           }
/*  755:     */         }
/*  756:     */       }
/*  757: 869 */       if (!found) {
/*  758: 870 */         result.add(candidate);
/*  759:     */       }
/*  760:     */     }
/*  761: 875 */     return result;
/*  762:     */   }
/*  763:     */   
/*  764:     */   protected HashSet<Entity> getEntities(Set<Entity> set)
/*  765:     */   {
/*  766: 882 */     HashSet<Entity> result = new HashSet();
/*  767: 883 */     for (Entity x : set) {
/*  768: 884 */       result.addAll(getEntities(x));
/*  769:     */     }
/*  770: 886 */     return result;
/*  771:     */   }
/*  772:     */   
/*  773:     */   private HashSet<Entity> getEntities(Entity x)
/*  774:     */   {
/*  775: 890 */     HashSet<Entity> result = new HashSet();
/*  776: 891 */     if (x.entityP())
/*  777:     */     {
/*  778: 892 */       result.add(x);
/*  779:     */     }
/*  780: 894 */     else if (x.functionP())
/*  781:     */     {
/*  782: 895 */       result.addAll(getEntities(x.getSubject()));
/*  783:     */     }
/*  784: 897 */     else if (x.relationP())
/*  785:     */     {
/*  786: 898 */       result.addAll(getEntities(x.getSubject()));
/*  787: 899 */       result.addAll(getEntities(x.getObject()));
/*  788:     */     }
/*  789: 901 */     else if (x.sequenceP())
/*  790:     */     {
/*  791: 902 */       for (Entity e : x.getElements()) {
/*  792: 903 */         result.addAll(getEntities(e));
/*  793:     */       }
/*  794:     */     }
/*  795: 906 */     return result;
/*  796:     */   }
/*  797:     */   
/*  798: 909 */   private HashMatrix<Entity, Sequence, Boolean> loop_check = new HashMatrix();
/*  799:     */   
/*  800:     */   private Set<Entity> findFeeders(Entity e, Sequence theInstantiatedConcept, Sequence theStorySequence)
/*  801:     */   {
/*  802: 916 */     boolean debug = false;
/*  803: 917 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Looking for feeders of", e });
/*  804: 918 */     Mark.say(new Object[] {Boolean.valueOf(debug), "From concept", theInstantiatedConcept });
/*  805: 919 */     Mark.say(new Object[] {Boolean.valueOf(debug), "In ", theStorySequence });
/*  806: 920 */     Set<Entity> feeders = new HashSet();
/*  807: 922 */     if ((e == null) || (theInstantiatedConcept == null)) {
/*  808: 922 */       return feeders;
/*  809:     */     }
/*  810: 923 */     if ((this.loop_check.contains(e, theInstantiatedConcept)) && (((Boolean)this.loop_check.get(e, theInstantiatedConcept)).booleanValue())) {
/*  811: 923 */       return feeders;
/*  812:     */     }
/*  813: 924 */     this.loop_check.put(e, theInstantiatedConcept, Boolean.valueOf(true));
/*  814:     */     
/*  815: 926 */     Vector<Entity> story = theStorySequence.getElements();
/*  816: 927 */     boolean found = false;
/*  817:     */     label460:
/*  818: 928 */     for (Iterator localIterator1 = story.iterator(); localIterator1.hasNext(); ???.hasNext())
/*  819:     */     {
/*  820: 928 */       Entity element = (Entity)localIterator1.next();
/*  821: 929 */       if ((!Predicates.isCause(element)) || 
/*  822: 930 */         (e != element.getObject())) {
/*  823:     */         break label460;
/*  824:     */       }
/*  825: 931 */       found = true;
/*  826: 934 */       if (!element.isA("prediction"))
/*  827:     */       {
/*  828: 935 */         feeders.add(element);
/*  829: 937 */         if (theInstantiatedConcept.getElements().contains(e))
/*  830:     */         {
/*  831: 938 */           for (Entity x : theInstantiatedConcept.getElements()) {
/*  832: 939 */             Mark.say(new Object[] {Boolean.valueOf(debug), "Instantiation:", x });
/*  833:     */           }
/*  834: 942 */           Mark.say(new Object[] {Boolean.valueOf(debug), "Stopping at", Generator.getGenerator().generate(element) });
/*  835: 943 */           this.loop_check.remove(e, theInstantiatedConcept);
/*  836: 944 */           return feeders;
/*  837:     */         }
/*  838:     */       }
/*  839: 950 */       ??? = element.getSubject().getElements().iterator(); continue;Entity antecedent = (Entity)???.next();
/*  840: 952 */       for (Entity x : findFeeders(antecedent, theInstantiatedConcept, theStorySequence)) {
/*  841: 954 */         if (feeders.contains(x)) {
/*  842: 955 */           Mark.say(new Object[] {Boolean.valueOf(debug), "Hit same entity again in Summarizer.findFeeders at", x });
/*  843:     */         } else {
/*  844: 958 */           feeders.add(x);
/*  845:     */         }
/*  846:     */       }
/*  847:     */     }
/*  848: 966 */     if (!found) {
/*  849: 967 */       feeders.add(e);
/*  850:     */     }
/*  851: 972 */     this.loop_check.remove(e, theInstantiatedConcept);
/*  852: 973 */     return feeders;
/*  853:     */   }
/*  854:     */   
/*  855:     */   private boolean mayOrLeadsTo(Entity givenElement, Sequence original)
/*  856:     */   {
/*  857: 981 */     if ((Predicates.isCause(givenElement)) && (
/*  858: 982 */       (givenElement.isA("explanation")) || (givenElement.isA("entail")))) {
/*  859: 983 */       for (Entity originalElement : original.getElements())
/*  860:     */       {
/*  861: 984 */         if (Predicates.equals(givenElement.getObject(), originalElement)) {
/*  862: 985 */           return true;
/*  863:     */         }
/*  864: 988 */         if (Predicates.contained(originalElement, givenElement.getSubject().getElements())) {
/*  865: 989 */           return true;
/*  866:     */         }
/*  867:     */       }
/*  868:     */     }
/*  869: 995 */     return false;
/*  870:     */   }
/*  871:     */   
/*  872:     */   private boolean antecedentButNotConsequentIn(Entity element, Sequence analysis)
/*  873:     */   {
/*  874:1019 */     boolean result = false;
/*  875:1020 */     for (Entity entity : analysis.getElements()) {
/*  876:1021 */       if (Predicates.isCause(entity)) {
/*  877:1022 */         if (entity.getSubject().getElements().contains(element)) {
/*  878:1023 */           result = true;
/*  879:1026 */         } else if (!entity.isA("explanation")) {
/*  880:1028 */           if (Predicates.equals(element, entity.getObject())) {
/*  881:1029 */             return false;
/*  882:     */           }
/*  883:     */         }
/*  884:     */       }
/*  885:     */     }
/*  886:1033 */     return result;
/*  887:     */   }
/*  888:     */   
/*  889:     */   private boolean appearsIn(Entity element, Sequence original)
/*  890:     */   {
/*  891:1040 */     return Predicates.contained(element, original.getElements());
/*  892:     */   }
/*  893:     */   
/*  894:     */   protected Set<String> extractNames(List<ReflectionDescription> concepts)
/*  895:     */   {
/*  896:1047 */     Set<String> names = new HashSet();
/*  897:1048 */     for (ReflectionDescription x : concepts) {
/*  898:1049 */       names.add(x.getName());
/*  899:     */     }
/*  900:1051 */     return names;
/*  901:     */   }
/*  902:     */   
/*  903:     */   private Set<Entity> pickAtRandom(int size, Set<Entity> explicitElements)
/*  904:     */   {
/*  905:1058 */     boolean debug = true;
/*  906:1059 */     Mark.say(new Object[] {"Size of random story desired", Integer.valueOf(size) });
/*  907:1060 */     List<Entity> candidates = new ArrayList(explicitElements);
/*  908:1061 */     Set<Entity> result = new HashSet();
/*  909:1062 */     if (explicitElements.size() <= size) {
/*  910:1063 */       return explicitElements;
/*  911:     */     }
/*  912:1065 */     while (result.size() < size)
/*  913:     */     {
/*  914:1066 */       int index = (int)(Math.random() * explicitElements.size());
/*  915:1067 */       result.add((Entity)candidates.get(index));
/*  916:     */     }
/*  917:1069 */     Mark.say(new Object[] {"Size of random story actual", Integer.valueOf(result.size()) });
/*  918:1070 */     return result;
/*  919:     */   }
/*  920:     */   
/*  921:     */   private Set<Entity> findExplicitElements(Sequence story)
/*  922:     */   {
/*  923:1077 */     Set<Entity> result = new HashSet();
/*  924:1078 */     Set<Entity> ineligible = new HashSet();
/*  925:1079 */     List<Entity> storyElements = story.getElements();
/*  926:1080 */     for (Entity element : storyElements) {
/*  927:1082 */       if ((element.isA("prediction")) || (element.isA("explanation")) || (element.isA("abduction")) || 
/*  928:1083 */         (element.isA("entail")))
/*  929:     */       {
/*  930:1084 */         ineligible.add(element);
/*  931:1085 */         ineligible.add(element.getObject());
/*  932:     */       }
/*  933:1088 */       else if (Predicates.isCause(element))
/*  934:     */       {
/*  935:1089 */         for (Entity entity : element.getSubject().getElements()) {
/*  936:1090 */           ineligible.add(entity);
/*  937:     */         }
/*  938:1092 */         ineligible.add(element.getObject());
/*  939:     */       }
/*  940:     */     }
/*  941:1096 */     for (Entity element : storyElements) {
/*  942:1097 */       if (!ineligible.contains(element)) {
/*  943:1098 */         result.add(element);
/*  944:     */       }
/*  945:     */     }
/*  946:1101 */     return result;
/*  947:     */   }
/*  948:     */   
/*  949:     */   public void processConcept(Object signal)
/*  950:     */   {
/*  951:1116 */     if ((signal instanceof BetterSignal))
/*  952:     */     {
/*  953:1117 */       Mark.err(new Object[] {"processConcept not implemented" });
/*  954:1118 */       BetterSignal s = (BetterSignal)signal;
/*  955:1119 */       Sequence story = (Sequence)s.get(0, Sequence.class);
/*  956:1120 */       ReflectionDescription localReflectionDescription = (ReflectionDescription)s.get(1, ReflectionDescription.class);
/*  957:     */     }
/*  958:     */   }
/*  959:     */   
/*  960:     */   public void processTab(Object o)
/*  961:     */   {
/*  962:1128 */     if ((o instanceof BetterSignal))
/*  963:     */     {
/*  964:1129 */       BetterSignal signal = (BetterSignal)o;
/*  965:1130 */       if ((signal.elementIsType(0, String.class)) && (((String)signal.get(0, String.class)).equals("selected tab")))
/*  966:     */       {
/*  967:1131 */         String tab = (String)signal.get(1, String.class);
/*  968:1132 */         if (this.rememberTab) {
/*  969:1133 */           this.rememberedTab.getString(tab);
/*  970:     */         }
/*  971:     */         Set<Entity> rightSummary;
/*  972:     */         Set<Entity> leftSummary;
/*  973:     */         Set<Entity> rightSummary;
/*  974:1139 */         if (tab == type0)
/*  975:     */         {
/*  976:1140 */           Set<Entity> leftSummary = this.leftSummaryDescription.getRandomSummary();
/*  977:1141 */           rightSummary = this.rightSummaryDescription.getRandomSummary();
/*  978:     */         }
/*  979:     */         else
/*  980:     */         {
/*  981:     */           Set<Entity> rightSummary;
/*  982:1143 */           if (tab == type1)
/*  983:     */           {
/*  984:1144 */             Set<Entity> leftSummary = this.leftSummaryDescription.getCompleteStory();
/*  985:1145 */             rightSummary = this.rightSummaryDescription.getCompleteStory();
/*  986:     */           }
/*  987:     */           else
/*  988:     */           {
/*  989:     */             Set<Entity> rightSummary;
/*  990:1147 */             if (tab == type2)
/*  991:     */             {
/*  992:1148 */               Set<Entity> leftSummary = this.leftSummaryDescription.getConnected();
/*  993:1149 */               rightSummary = this.rightSummaryDescription.getConnected();
/*  994:     */             }
/*  995:     */             else
/*  996:     */             {
/*  997:     */               Set<Entity> rightSummary;
/*  998:1152 */               if (tab == type4)
/*  999:     */               {
/* 1000:1153 */                 Set<Entity> leftSummary = this.leftSummaryDescription.getConcept();
/* 1001:1154 */                 rightSummary = this.rightSummaryDescription.getConcept();
/* 1002:     */               }
/* 1003:     */               else
/* 1004:     */               {
/* 1005:     */                 Set<Entity> rightSummary;
/* 1006:1156 */                 if (tab == type5)
/* 1007:     */                 {
/* 1008:1157 */                   Set<Entity> leftSummary = this.leftSummaryDescription.getDominant();
/* 1009:1158 */                   rightSummary = this.rightSummaryDescription.getDominant();
/* 1010:     */                 }
/* 1011:     */                 else
/* 1012:     */                 {
/* 1013:     */                   Set<Entity> rightSummary;
/* 1014:1160 */                   if (tab == type6)
/* 1015:     */                   {
/* 1016:1161 */                     Set<Entity> leftSummary = this.leftSummaryDescription.getSpecial();
/* 1017:1162 */                     rightSummary = this.rightSummaryDescription.getSpecial();
/* 1018:     */                   }
/* 1019:     */                   else
/* 1020:     */                   {
/* 1021:     */                     Set<Entity> rightSummary;
/* 1022:1164 */                     if (tab == questions)
/* 1023:     */                     {
/* 1024:1165 */                       Set<Entity> leftSummary = this.leftSummaryDescription.getDominant();
/* 1025:1166 */                       rightSummary = this.rightSummaryDescription.getDominant();
/* 1026:     */                     }
/* 1027:     */                     else
/* 1028:     */                     {
/* 1029:     */                       Set<Entity> rightSummary;
/* 1030:1168 */                       if (tab == questions)
/* 1031:     */                       {
/* 1032:1169 */                         Set<Entity> leftSummary = this.leftSummaryDescription.getQuestions();
/* 1033:1170 */                         rightSummary = this.rightSummaryDescription.getQuestions();
/* 1034:     */                       }
/* 1035:     */                       else
/* 1036:     */                       {
/* 1037:1173 */                         leftSummary = new HashSet();
/* 1038:1174 */                         rightSummary = new HashSet();
/* 1039:     */                       }
/* 1040:     */                     }
/* 1041:     */                   }
/* 1042:     */                 }
/* 1043:     */               }
/* 1044:     */             }
/* 1045:     */           }
/* 1046:     */         }
/* 1047:1177 */         Connections.getPorts(this).transmit("reset", "reset");
/* 1048:1178 */         Connections.getPorts(this).transmit("Selected left description to display", new BetterSignal(new Object[] { leftSummary, this.leftAnalysis }));
/* 1049:1179 */         Connections.getPorts(this).transmit("Selected right description to display", new BetterSignal(new Object[] { rightSummary, this.rightAnalysis }));
/* 1050:     */       }
/* 1051:     */     }
/* 1052:     */   }
/* 1053:     */   
/* 1054:1186 */   private boolean rememberTab = false;
/* 1055:1188 */   private StringWithMemory rememberedTab = new StringWithMemory("summaryTab", type5);
/* 1056:1192 */   private Sequence leftAnalysis = new Sequence();
/* 1057:1194 */   private Sequence rightAnalysis = new Sequence();
/* 1058:1196 */   private static String testConcept = "Answered prayer";
/* 1059:1198 */   public static String type0 = "Random";
/* 1060:1200 */   public static String type1 = "Unabridged";
/* 1061:1202 */   public static String type2 = "Connected";
/* 1062:1204 */   public static String type4 = "Concept centered";
/* 1063:1206 */   public static String type5 = "Dominant concept centered";
/* 1064:1208 */   private static String type6 = testConcept;
/* 1065:1210 */   private static String stats = "Table";
/* 1066:1212 */   private static String questions = "Unresolved questions";
/* 1067:1216 */   private String[] modes = { type0, type1, type2, type4, type5, type6, questions, stats };
/* 1068:     */   private String table;
/* 1069:1220 */   private ArrayList<String> row = new ArrayList();
/* 1070:     */   private static Summarizer summarizer;
/* 1071:1224 */   private SummaryDescription leftSummaryDescription = new SummaryDescription();
/* 1072:1226 */   private SummaryDescription rightSummaryDescription = new SummaryDescription();
/* 1073:     */   public static final String SELECTED_TAB = "selected tab";
/* 1074:     */   public static final String LEFT_INPUT = "Left perspective input";
/* 1075:     */   public static final String RIGHT_INPUT = "Right perspective input";
/* 1076:     */   public static final String CONCEPT_PORT = "Concept port";
/* 1077:     */   public static final String REPORT_OUTPUT = "Report";
/* 1078:     */   public static final String SUMMARY_OUTPUT = "summary";
/* 1079:     */   public static final String SELECTED_LEFT_DESCRIPTION = "Selected left description to display";
/* 1080:     */   public static final String SELECTED_RIGHT_DESCRIPTION = "Selected right description to display";
/* 1081:     */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     summarizer.Summarizer
 * JD-Core Version:    0.7.0.1
 */