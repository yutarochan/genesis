/*   1:    */ package models;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Relation;
/*   6:    */ import bridge.reps.entities.Sequence;
/*   7:    */ import connections.AbstractWiredBox;
/*   8:    */ import connections.Connections;
/*   9:    */ import connections.Ports;
/*  10:    */ import connections.WiredBox;
/*  11:    */ import genesis.FileSourceReader;
/*  12:    */ import genesis.GenesisGetters;
/*  13:    */ import gui.ActivityMonitor;
/*  14:    */ import gui.ElaborationView;
/*  15:    */ import gui.NameLabel;
/*  16:    */ import gui.ReflectionBar;
/*  17:    */ import gui.RuleViewer;
/*  18:    */ import gui.StatisticsBar;
/*  19:    */ import gui.StoryViewer;
/*  20:    */ import gui.TabbedMentalModelViewer;
/*  21:    */ import gui.WiredProgressBar;
/*  22:    */ import java.awt.BorderLayout;
/*  23:    */ import java.awt.Color;
/*  24:    */ import java.awt.Dimension;
/*  25:    */ import java.io.IOException;
/*  26:    */ import java.net.URL;
/*  27:    */ import java.util.ArrayList;
/*  28:    */ import java.util.HashMap;
/*  29:    */ import java.util.Vector;
/*  30:    */ import javax.swing.BorderFactory;
/*  31:    */ import javax.swing.JComponent;
/*  32:    */ import javax.swing.JPanel;
/*  33:    */ import javax.swing.JScrollPane;
/*  34:    */ import start.Start;
/*  35:    */ import start.StartPostprocessor;
/*  36:    */ import start.StartPreprocessor;
/*  37:    */ import storyProcessor.ConceptExpert;
/*  38:    */ import storyProcessor.ReflectionAnalysis;
/*  39:    */ import storyProcessor.ReflectionOnsetDetector;
/*  40:    */ import storyProcessor.StoryProcessor;
/*  41:    */ import storyProcessor.TraitExpert;
/*  42:    */ import translator.Translator;
/*  43:    */ import utils.Mark;
/*  44:    */ import utils.PathFinder;
/*  45:    */ 
/*  46:    */ public class MentalModel
/*  47:    */   extends Entity
/*  48:    */   implements WiredBox
/*  49:    */ {
/*  50:    */   public static HashMap<String, MentalModel> globalModels;
/*  51:    */   public HashMap<String, MentalModel> localMentalModels;
/*  52:    */   public static final String INJECT_FILE = "port for file injection";
/*  53:    */   public static final String INJECT_STORY = "port for story sequence injection";
/*  54:    */   public static final String STOP_STORY = "time to run stop story";
/*  55:    */   private FileSourceReader reader;
/*  56:    */   private StartPreprocessor startPreprocessor;
/*  57:    */   private Start startParser;
/*  58:    */   private StartPostprocessor startPostprocessor;
/*  59:    */   private Translator translator;
/*  60:    */   private StoryProcessor storyProcessor;
/*  61:    */   private ShortCut shortCut;
/*  62:    */   private JPanel plotDiagram;
/*  63:    */   private JPanel analysisPanel;
/*  64:    */   private ReflectionBar plotUnitBar;
/*  65:    */   private ConceptExpert conceptExpert;
/*  66:    */   private TraitExpert traitExpert;
/*  67:    */   private ReflectionOnsetDetector onsetDetector;
/*  68:    */   private RuleViewer instantiatedRuleViewer;
/*  69:    */   private StoryViewer instantiatedConceptViewer;
/*  70:    */   private JScrollPane plotUnitBarScroller;
/*  71:    */   private StatisticsBar statisticsBar;
/*  72:    */   public static final String RECORD_REFLECTION_ANALYSIS = "port for receiving information from ReflectionExpert";
/*  73:    */   private ReflectionAnalysis reflectionAnalysis;
/*  74:    */   private WiredProgressBar storyProgressBar;
/*  75:    */   private NameLabel storyNameLabel;
/*  76:    */   private ArrayList<Entity> examples;
/*  77:    */   ElaborationView elaborationView;
/*  78:    */   ElaborationView inspectionView;
/*  79:    */   
/*  80:    */   public MentalModel(String processorName)
/*  81:    */   {
/*  82:110 */     this(processorName, null);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public MentalModel(String processorName, String fileName)
/*  86:    */   {
/*  87:117 */     super(processorName);
/*  88:118 */     boolean verbose = false;
/*  89:119 */     setName(processorName);
/*  90:120 */     Mark.say(new Object[] {Boolean.valueOf(verbose), "Constructing mental model", processorName, "from file", fileName });
/*  91:    */     
/*  92:122 */     wireUp(processorName);
/*  93:    */     
/*  94:124 */     Mark.say(new Object[] {Boolean.valueOf(verbose), "Wiring done" });
/*  95:    */     
/*  96:126 */     GenesisGetters.getMentalModelViewer().addTab(processorName, getPlotDiagram());
/*  97:127 */     GenesisGetters.getMentalModelViewer().setSelectedComponent(getPlotDiagram());
/*  98:129 */     if (fileName != null)
/*  99:    */     {
/* 100:130 */       getFileSourceReader().readStoryWithoutNewThread(fileName);
/* 101:131 */       Mark.say(new Object[] {Boolean.valueOf(verbose), "Done reading mental model file" });
/* 102:    */     }
/* 103:137 */     for (Relation rule : getPredictionRules())
/* 104:    */     {
/* 105:138 */       Mark.say(new Object[] {Boolean.valueOf(verbose), "In", getName(), "have prediction rule", rule.asStringWithIndexes() });
/* 106:139 */       rule.addProperty("mental-model-host", this);
/* 107:    */     }
/* 108:143 */     for (Relation rule : getExplanationRules())
/* 109:    */     {
/* 110:144 */       Mark.say(new Object[] {Boolean.valueOf(verbose), "In", getName(), "have explanation rule", rule.asString() });
/* 111:145 */       rule.addProperty("mental-model-host", this);
/* 112:    */     }
/* 113:149 */     for (Relation rule : getCensorRules())
/* 114:    */     {
/* 115:150 */       Mark.say(new Object[] {Boolean.valueOf(verbose), "In", getName(), "have censor rule", rule.asString() });
/* 116:151 */       rule.addProperty("mental-model-host", this);
/* 117:    */     }
/* 118:155 */     for (Sequence concept : getConcepts()) {
/* 119:157 */       concept.addProperty("mental-model-host", this);
/* 120:    */     }
/* 121:163 */     Vector<Entity> storyElements = getStoryProcessor().getStory().getElements();
/* 122:165 */     if ((storyElements != null) && (!storyElements.isEmpty())) {
/* 123:167 */       getExamples().addAll(storyElements);
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean entityP()
/* 128:    */   {
/* 129:192 */     return true;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void wireUp(String processorName)
/* 133:    */   {
/* 134:197 */     wireUpStoryProcessorInputPorts();
/* 135:    */     
/* 136:199 */     wireUpStoryProcessorOutputPorts();
/* 137:    */     
/* 138:201 */     wireUpInternals();
/* 139:    */     
/* 140:203 */     addSignalProcessors();
/* 141:    */   }
/* 142:    */   
/* 143:    */   private void wireUpStoryProcessorInputPorts()
/* 144:    */   {
/* 145:210 */     Connections.forwardTo("input", this, getStoryProcessor());
/* 146:    */     
/* 147:212 */     Connections.forwardTo("input", this, getStoryProgressBar());
/* 148:    */     
/* 149:214 */     Connections.forwardTo(Start.STAGE_DIRECTION_PORT, this, getStoryProcessor());
/* 150:    */     
/* 151:216 */     Connections.forwardTo("port for story element injection into trait-specific mental model", this, getStoryProcessor());
/* 152:217 */     Connections.forwardTo("port for story element injection", this, getStoryProcessor());
/* 153:218 */     Connections.forwardTo("port for rule injection", this, getStoryProcessor());
/* 154:219 */     Connections.forwardTo("port for concept injection", this, getStoryProcessor());
/* 155:    */     
/* 156:221 */     Connections.forwardTo("clearButtons", this, getPlotUnitBar());
/* 157:    */     
/* 158:223 */     Connections.forwardTo("clear counts", this, getStatisticsBar());
/* 159:    */     
/* 160:225 */     Connections.forwardTo("clear counts", this, getStatisticsBar());
/* 161:    */     
/* 162:    */ 
/* 163:228 */     Connections.forwardTo("used rules port", this, getStoryProcessor());
/* 164:229 */     Connections.forwardTo("reflections port", this, getStoryProcessor());
/* 165:230 */     Connections.forwardTo("rule port", this, getStoryProcessor());
/* 166:231 */     Connections.forwardTo("instantiated reflections from concept expert", this, getStoryProcessor());
/* 167:232 */     Connections.forwardTo("reflection analysis from concept expert", this, getStoryProcessor());
/* 168:    */     
/* 169:    */ 
/* 170:235 */     Connections.forwardTo(StoryProcessor.LEARNED_RULE_PORT, this, getStoryProcessor());
/* 171:236 */     Connections.forwardTo(StoryProcessor.NEW_RULE_MESSENGER_PORT, this, getStoryProcessor());
/* 172:    */   }
/* 173:    */   
/* 174:    */   private void wireUpStoryProcessorOutputPorts()
/* 175:    */   {
/* 176:241 */     Connections.forwardFrom(getStoryProcessor(), this);
/* 177:242 */     Connections.forwardFrom(Start.STAGE_DIRECTION_PORT, getStoryProcessor(), this);
/* 178:243 */     Connections.forwardFrom("change-mode", getStoryProcessor(), this);
/* 179:244 */     Connections.forwardFrom("rule port", getStoryProcessor(), this);
/* 180:245 */     Connections.forwardFrom("final-inferences", getStoryProcessor(), this);
/* 181:246 */     Connections.forwardFrom("reflections port", getStoryProcessor(), this);
/* 182:247 */     Connections.forwardFrom("to onset detector port", getStoryProcessor(), this);
/* 183:248 */     Connections.forwardFrom(StoryProcessor.EXPLICIT_STORY, getStoryProcessor(), this);
/* 184:249 */     Connections.forwardFrom("commentary port", getStoryProcessor(), this);
/* 185:250 */     Connections.forwardFrom("commentary port", getTraitExpert(), this);
/* 186:251 */     Connections.forwardFrom("next", getStoryProcessor(), this);
/* 187:252 */     Connections.forwardFrom("Reflection analysis port", getConceptExpert(), this);
/* 188:253 */     Connections.forwardFrom("complete story events port", getStoryProcessor(), this);
/* 189:254 */     Connections.forwardFrom("complete story analysis port", getStoryProcessor(), this);
/* 190:255 */     Connections.forwardFrom("NEW_PLOT_ELEMENT_PORT", getStoryProcessor(), this);
/* 191:    */     
/* 192:    */ 
/* 193:258 */     Connections.forwardFrom("cluster story port", getStoryProcessor(), this);
/* 194:    */     
/* 195:    */ 
/* 196:    */ 
/* 197:262 */     Connections.forwardFrom("quiescence port", getStoryProcessor(), this);
/* 198:263 */     Connections.forwardFrom("incremental output port", getStoryProcessor(), this);
/* 199:264 */     Connections.forwardFrom("final-inputs", getStoryProcessor(), this);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void wireUpInternals()
/* 203:    */   {
/* 204:270 */     Connections.wire(getFileSourceReader(), getStartPreprocessor());
/* 205:271 */     Connections.wire("status port", getFileSourceReader(), getStoryProgressBar());
/* 206:272 */     Connections.wire("self", getStartPreprocessor(), getStartPreprocessor());
/* 207:    */     
/* 208:274 */     Connections.wire(Start.STAGE_DIRECTION_PORT, getStartPreprocessor(), Start.STAGE_DIRECTION_PORT, getStoryProcessor());
/* 209:275 */     Connections.wire(Start.STAGE_DIRECTION_PORT, getStartPreprocessor(), "clearButtons", getPlotUnitBar());
/* 210:276 */     Connections.wire(Start.STAGE_DIRECTION_PORT, getStartPreprocessor(), getInstantiatedConceptViewer());
/* 211:    */     
/* 212:278 */     Connections.wire("change-mode", getStartPreprocessor(), "change-mode", getStartParser());
/* 213:279 */     Connections.wire(getStartPreprocessor(), "sentence", getStartParser());
/* 214:    */     
/* 215:281 */     Connections.wire("parse", getStartParser(), Translator.PROCESS, getTranslator());
/* 216:282 */     Connections.wire("result", getTranslator(), getStartPostprocessor());
/* 217:283 */     Connections.wire(getStartPostprocessor(), getShortCut());
/* 218:284 */     Connections.wire(getShortCut(), getStoryProcessor());
/* 219:    */     
/* 220:286 */     Connections.wire("change-mode", getStoryProcessor(), "change-mode", getStartParser());
/* 221:    */     
/* 222:288 */     Connections.wire("inferences", getStoryProcessor(), getInstantiatedRuleViewer());
/* 223:289 */     Connections.wire("final-inferences", getStoryProcessor(), "final-inference", getInstantiatedRuleViewer());
/* 224:    */     
/* 225:291 */     Connections.wire("Instantiated reflections port", getConceptExpert(), getInstantiatedConceptViewer());
/* 226:    */     
/* 227:    */ 
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:297 */     Connections.wire(getStoryProcessor(), "story port", getElaborationView());
/* 232:    */     
/* 233:299 */     Connections.wire("to onset detector port", getStoryProcessor(), getOnsetDetector());
/* 234:300 */     Connections.wire(getOnsetDetector(), "discovery port", getOnsetDetector());
/* 235:    */     
/* 236:302 */     Connections.wire("to completion detector port", getStoryProcessor(), getConceptExpert());
/* 237:    */     
/* 238:304 */     Connections.wire("inject element", getConceptExpert(), "port for story element injection", getStoryProcessor());
/* 239:    */     
/* 240:306 */     Connections.wire("test element", getConceptExpert(), "port for testing to see if element supported by may rule", getStoryProcessor());
/* 241:    */     
/* 242:308 */     Connections.wire("reset", getConceptExpert(), "reset", getPlotUnitBar());
/* 243:309 */     Connections.wire("clearButtons", getConceptExpert(), "clearButtons", getPlotUnitBar());
/* 244:    */     
/* 245:    */ 
/* 246:    */ 
/* 247:313 */     Connections.wire("elaboration display port", getPlotUnitBar(), "concept port", getElaborationView());
/* 248:    */     
/* 249:315 */     Connections.wire("elaboration display port", getPlotUnitBar(), "inspection port", getInspectionView());
/* 250:    */     
/* 251:317 */     Connections.wire("addPlotUnitButton", getConceptExpert(), "addPlotUnitButton", getPlotUnitBar());
/* 252:    */     
/* 253:319 */     Connections.wire("statistics bar output port", getPlotUnitBar(), "story processor input port", getStatisticsBar());
/* 254:320 */     Connections.wire("statistics bar output port", getStoryProcessor(), "story processor input port", getStatisticsBar());
/* 255:    */     
/* 256:322 */     Connections.wire("to activity monitor", getStartParser(), ActivityMonitor.getActivityMonitor());
/* 257:    */     
/* 258:324 */     Connections.wire("reset concepts port", getStoryProcessor(), "clearButtons", getPlotUnitBar());
/* 259:    */     
/* 260:326 */     Connections.wire("story name", getStoryProcessor(), getStoryName());
/* 261:    */     
/* 262:328 */     Connections.biwire("personality port", getStoryProcessor(), "personality port", getTraitExpert());
/* 263:    */     
/* 264:330 */     Connections.wire("to forward chainer port", getTraitExpert(), getStoryProcessor().getForwardChainer());
/* 265:    */     
/* 266:332 */     Connections.wire("to backward chainer port", getTraitExpert(), getStoryProcessor().getBackwardChainer());
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void addSignalProcessors()
/* 270:    */   {
/* 271:337 */     Connections.getPorts(this).addSignalProcessor("time to run stop story", "stopStory");
/* 272:338 */     Connections.getPorts(this).addSignalProcessor("port for file injection", "readFile");
/* 273:339 */     Connections.getPorts(this).addSignalProcessor("port for story sequence injection", "injectStory");
/* 274:340 */     Connections.getPorts(this).addSignalProcessor(Start.STAGE_DIRECTION_PORT, "processStageDirections");
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void injectStory(Object o)
/* 278:    */   {
/* 279:345 */     if ((o instanceof Sequence))
/* 280:    */     {
/* 281:346 */       Sequence s = (Sequence)o;
/* 282:347 */       getStoryProcessor().setAwake(true);
/* 283:348 */       for (Entity e : s.getElements()) {
/* 284:349 */         getStoryProcessor().processElement(e);
/* 285:    */       }
/* 286:    */     }
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void processStageDirections(Object o)
/* 290:    */   {
/* 291:355 */     if ((getStoryProcessor().isAwake()) && 
/* 292:356 */       ((o instanceof BetterSignal)))
/* 293:    */     {
/* 294:357 */       BetterSignal bs = (BetterSignal)o;
/* 295:358 */       Object command = bs.get(0, Object.class);
/* 296:359 */       if ("LoadPersonalityFile".equals(command))
/* 297:    */       {
/* 298:360 */         String name = (String)bs.get(1, String.class);
/* 299:    */         
/* 300:362 */         loadLocalMentalModelWithTrait(name);
/* 301:    */       }
/* 302:    */     }
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void describe()
/* 306:    */   {
/* 307:369 */     Mark.say(
/* 308:    */     
/* 309:    */ 
/* 310:    */ 
/* 311:    */ 
/* 312:    */ 
/* 313:375 */       new Object[] { "Mental model description:" });Mark.say(new Object[] { "Name:", getName() });Mark.say(new Object[] { "Rules:", Integer.valueOf(getPredictionRules().size()) });Mark.say(new Object[] { "Concepts:", Integer.valueOf(getConceptPatterns().getElements().size()) });Mark.say(new Object[] { "Instantiated rules", Integer.valueOf(getInferences().getElements().size()) });Mark.say(new Object[] { "Instantiated concepts", Integer.valueOf(getInstantiatedConcepts().getElements().size()) });
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void startStory()
/* 317:    */   {
/* 318:378 */     getStoryProcessor().resetStoryVariables();
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void startStory(Object o)
/* 322:    */   {
/* 323:382 */     startStory();
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void stopStory(Object o)
/* 327:    */   {
/* 328:389 */     getStoryProcessor().stopStory();
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void readFile(Object o)
/* 332:    */   {
/* 333:395 */     if ((o instanceof String))
/* 334:    */     {
/* 335:396 */       String fileName = (String)o;
/* 336:397 */       getFileSourceReader().readStory(fileName);
/* 337:398 */       getPlotDiagram().revalidate();
/* 338:    */     }
/* 339:    */   }
/* 340:    */   
/* 341:    */   public FileSourceReader getFileSourceReader()
/* 342:    */   {
/* 343:403 */     if (this.reader == null)
/* 344:    */     {
/* 345:404 */       this.reader = new FileSourceReader();
/* 346:405 */       this.reader.setName("Mental model file reader");
/* 347:    */     }
/* 348:407 */     return this.reader;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public StartPreprocessor getStartPreprocessor()
/* 352:    */   {
/* 353:411 */     if (this.startPreprocessor == null)
/* 354:    */     {
/* 355:412 */       this.startPreprocessor = new StartPreprocessor();
/* 356:413 */       this.startPreprocessor.setName("Mental model preprocessor");
/* 357:    */     }
/* 358:415 */     return this.startPreprocessor;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public Start getStartParser()
/* 362:    */   {
/* 363:419 */     if (this.startParser == null)
/* 364:    */     {
/* 365:420 */       this.startParser = new Start();
/* 366:421 */       this.startParser.setName("Mental model Start processor");
/* 367:    */     }
/* 368:423 */     return this.startParser;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public StartPostprocessor getStartPostprocessor()
/* 372:    */   {
/* 373:427 */     if (this.startPostprocessor == null)
/* 374:    */     {
/* 375:428 */       this.startPostprocessor = new StartPostprocessor();
/* 376:429 */       this.startPostprocessor.setName("Mental model post processor");
/* 377:    */     }
/* 378:431 */     return this.startPostprocessor;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public Translator getTranslator()
/* 382:    */   {
/* 383:435 */     if (this.translator == null)
/* 384:    */     {
/* 385:436 */       this.translator = new Translator();
/* 386:437 */       this.translator.setName("Mental model translator");
/* 387:    */     }
/* 388:439 */     return this.translator;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public StoryProcessor getStoryProcessor()
/* 392:    */   {
/* 393:443 */     if (this.storyProcessor == null) {
/* 394:444 */       this.storyProcessor = new StoryProcessor(getName(), this);
/* 395:    */     }
/* 396:446 */     return this.storyProcessor;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public ShortCut getShortCut()
/* 400:    */   {
/* 401:450 */     if (this.shortCut == null)
/* 402:    */     {
/* 403:451 */       this.shortCut = new ShortCut();
/* 404:452 */       this.shortCut.setName("Mental model shortcut");
/* 405:    */     }
/* 406:454 */     return this.shortCut;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public ElaborationView getElaborationView()
/* 410:    */   {
/* 411:483 */     if (this.elaborationView == null) {
/* 412:484 */       this.elaborationView = new ElaborationView();
/* 413:    */     }
/* 414:486 */     return this.elaborationView;
/* 415:    */   }
/* 416:    */   
/* 417:    */   public ElaborationView getInspectionView()
/* 418:    */   {
/* 419:495 */     if (this.inspectionView == null)
/* 420:    */     {
/* 421:496 */       this.inspectionView = new ElaborationView();
/* 422:497 */       this.inspectionView.setAlwaysShowAllElements(true);
/* 423:    */     }
/* 424:499 */     return this.inspectionView;
/* 425:    */   }
/* 426:    */   
/* 427:    */   public JComponent getPlotDiagram()
/* 428:    */   {
/* 429:503 */     if (this.plotDiagram == null)
/* 430:    */     {
/* 431:504 */       this.plotDiagram = new JPanel();
/* 432:505 */       this.plotDiagram.setBackground(Color.WHITE);
/* 433:506 */       this.plotDiagram.setOpaque(true);
/* 434:507 */       this.plotDiagram.setLayout(new BorderLayout());
/* 435:508 */       this.plotDiagram.add(getElaborationView());
/* 436:509 */       this.plotDiagram.add(getAnalysisPanel(), "South");
/* 437:510 */       this.plotDiagram.add(getStatisticsBar(), "West");
/* 438:511 */       this.plotDiagram.add(getStoryName(), "North");
/* 439:    */     }
/* 440:513 */     return this.plotDiagram;
/* 441:    */   }
/* 442:    */   
/* 443:    */   protected NameLabel getStoryName()
/* 444:    */   {
/* 445:517 */     if (this.storyNameLabel == null) {
/* 446:518 */       this.storyNameLabel = new NameLabel(new String[] { "h1", "center" });
/* 447:    */     }
/* 448:520 */     return this.storyNameLabel;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public JPanel getAnalysisPanel()
/* 452:    */   {
/* 453:524 */     if (this.analysisPanel == null)
/* 454:    */     {
/* 455:525 */       this.analysisPanel = new JPanel();
/* 456:526 */       this.analysisPanel.setLayout(new BorderLayout());
/* 457:527 */       this.analysisPanel.setPreferredSize(new Dimension(1000, 80));
/* 458:528 */       this.analysisPanel.setBorder(BorderFactory.createTitledBorder("Analysis"));
/* 459:529 */       this.analysisPanel.setBackground(Color.WHITE);
/* 460:530 */       this.analysisPanel.add(getPlotUnitBar(), "Center");
/* 461:531 */       this.analysisPanel.add(getStoryProgressBar(), "South");
/* 462:    */     }
/* 463:533 */     return this.analysisPanel;
/* 464:    */   }
/* 465:    */   
/* 466:    */   public ReflectionBar getPlotUnitBar()
/* 467:    */   {
/* 468:544 */     if (this.plotUnitBar == null)
/* 469:    */     {
/* 470:545 */       this.plotUnitBar = new ReflectionBar();
/* 471:546 */       this.plotUnitBar.setName("Reflections");
/* 472:    */     }
/* 473:548 */     return this.plotUnitBar;
/* 474:    */   }
/* 475:    */   
/* 476:    */   public ReflectionOnsetDetector getOnsetDetector()
/* 477:    */   {
/* 478:552 */     if (this.onsetDetector == null)
/* 479:    */     {
/* 480:553 */       this.onsetDetector = new ReflectionOnsetDetector();
/* 481:554 */       this.onsetDetector.setName("Onset detector");
/* 482:    */     }
/* 483:556 */     return this.onsetDetector;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public ConceptExpert getConceptExpert()
/* 487:    */   {
/* 488:568 */     return getStoryProcessor().getConceptExpert();
/* 489:    */   }
/* 490:    */   
/* 491:    */   public RuleViewer getInstantiatedRuleViewer()
/* 492:    */   {
/* 493:572 */     if (this.instantiatedRuleViewer == null)
/* 494:    */     {
/* 495:573 */       this.instantiatedRuleViewer = new RuleViewer(null);
/* 496:574 */       this.instantiatedRuleViewer.setName("Instantiated rule viewer");
/* 497:    */     }
/* 498:576 */     return this.instantiatedRuleViewer;
/* 499:    */   }
/* 500:    */   
/* 501:    */   public StoryViewer getInstantiatedConceptViewer()
/* 502:    */   {
/* 503:580 */     if (this.instantiatedConceptViewer == null)
/* 504:    */     {
/* 505:581 */       this.instantiatedConceptViewer = new StoryViewer(null);
/* 506:582 */       this.instantiatedConceptViewer.setName("Instantiated reflection viewer 1");
/* 507:    */     }
/* 508:584 */     return this.instantiatedConceptViewer;
/* 509:    */   }
/* 510:    */   
/* 511:    */   public StatisticsBar getStatisticsBar()
/* 512:    */   {
/* 513:588 */     if (this.statisticsBar == null)
/* 514:    */     {
/* 515:589 */       this.statisticsBar = new StatisticsBar();
/* 516:590 */       this.statisticsBar.setName("Statistics bar");
/* 517:    */     }
/* 518:592 */     return this.statisticsBar;
/* 519:    */   }
/* 520:    */   
/* 521:    */   public WiredProgressBar getStoryProgressBar()
/* 522:    */   {
/* 523:596 */     if (this.storyProgressBar == null) {
/* 524:597 */       this.storyProgressBar = new WiredProgressBar();
/* 525:    */     }
/* 526:599 */     return this.storyProgressBar;
/* 527:    */   }
/* 528:    */   
/* 529:    */   public Sequence getCommonsenseRules()
/* 530:    */   {
/* 531:603 */     return getStoryProcessor().getCommonsenseRules();
/* 532:    */   }
/* 533:    */   
/* 534:    */   public Sequence getInferences()
/* 535:    */   {
/* 536:607 */     return getStoryProcessor().getInferences();
/* 537:    */   }
/* 538:    */   
/* 539:    */   public void recordReflectionAnalysis(Object object)
/* 540:    */   {
/* 541:611 */     setReflectionAnalysis((ReflectionAnalysis)object);
/* 542:    */   }
/* 543:    */   
/* 544:    */   public Sequence getInstantiatedConcepts()
/* 545:    */   {
/* 546:615 */     return getStoryProcessor().getInstantiatedConcepts();
/* 547:    */   }
/* 548:    */   
/* 549:    */   public ReflectionAnalysis getReflectionAnalysis()
/* 550:    */   {
/* 551:619 */     return this.reflectionAnalysis;
/* 552:    */   }
/* 553:    */   
/* 554:    */   public void setReflectionAnalysis(ReflectionAnalysis reflectionAnalysis)
/* 555:    */   {
/* 556:623 */     this.reflectionAnalysis = reflectionAnalysis;
/* 557:    */   }
/* 558:    */   
/* 559:    */   public class ShortCut
/* 560:    */     extends AbstractWiredBox
/* 561:    */   {
/* 562:    */     public ShortCut()
/* 563:    */     {
/* 564:632 */       Connections.getPorts(this).addSignalProcessor("process");
/* 565:    */     }
/* 566:    */     
/* 567:    */     public void process(Object o)
/* 568:    */     {
/* 569:636 */       if ((o instanceof Sequence)) {
/* 570:637 */         for (Entity t : ((Sequence)o).getElements()) {
/* 571:638 */           Connections.getPorts(this).transmit(t);
/* 572:    */         }
/* 573:    */       }
/* 574:    */     }
/* 575:    */   }
/* 576:    */   
/* 577:    */   public void clearLocalMentalModels()
/* 578:    */   {
/* 579:682 */     if (this.localMentalModels != null)
/* 580:    */     {
/* 581:684 */       removeAllTabs();
/* 582:685 */       this.localMentalModels.clear();
/* 583:    */     }
/* 584:    */   }
/* 585:    */   
/* 586:    */   public MentalModel getLocalMentalModel(String name)
/* 587:    */   {
/* 588:690 */     if (this.localMentalModels == null) {
/* 589:691 */       this.localMentalModels = new HashMap();
/* 590:    */     }
/* 591:693 */     return (MentalModel)this.localMentalModels.get(name);
/* 592:    */   }
/* 593:    */   
/* 594:    */   public ArrayList<MentalModel> getLocalMentalModels()
/* 595:    */   {
/* 596:697 */     ArrayList<MentalModel> models = new ArrayList();
/* 597:698 */     if (this.localMentalModels != null) {
/* 598:699 */       models.addAll(this.localMentalModels.values());
/* 599:    */     }
/* 600:701 */     return models;
/* 601:    */   }
/* 602:    */   
/* 603:    */   public MentalModel loadLocalMentalModelWithTrait(String name)
/* 604:    */   {
/* 605:705 */     Entity thing = new Entity();
/* 606:706 */     thing.addType("personality_trait");
/* 607:707 */     thing.addType(name);
/* 608:    */     try
/* 609:    */     {
/* 610:710 */       ArrayList<URL> initialResults = PathFinder.listStoryMatches(name);
/* 611:711 */       ArrayList<URL> results = initialResults;
/* 612:713 */       if (results.isEmpty()) {
/* 613:714 */         throw new IOException("Story " + name + " not Found!");
/* 614:    */       }
/* 615:717 */       MentalModel model = loadLocalMentalModel(name, name);
/* 616:718 */       model.setBundle(thing.getBundle());
/* 617:719 */       return model;
/* 618:    */     }
/* 619:    */     catch (IOException e)
/* 620:    */     {
/* 621:722 */       Mark.say(new Object[] {"For trait, " + name + ", no explicit trait defintion file found!" });
/* 622:    */     }
/* 623:723 */     return null;
/* 624:    */   }
/* 625:    */   
/* 626:    */   public MentalModel loadLocalMentalModel(String name)
/* 627:    */   {
/* 628:729 */     return loadLocalMentalModel(name, name);
/* 629:    */   }
/* 630:    */   
/* 631:    */   public MentalModel loadLocalMentalModel(String name, String file)
/* 632:    */   {
/* 633:733 */     boolean verbose = false;
/* 634:734 */     if (this.localMentalModels == null) {
/* 635:735 */       this.localMentalModels = new HashMap();
/* 636:    */     }
/* 637:737 */     MentalModel model = (MentalModel)this.localMentalModels.get(name);
/* 638:738 */     if (model != null)
/* 639:    */     {
/* 640:739 */       Mark.say(new Object[] {Boolean.valueOf(verbose), "Already have mental model of", name, "so reloading" });
/* 641:740 */       removeTab(name);
/* 642:    */     }
/* 643:742 */     Mark.say(new Object[] {Boolean.valueOf(verbose), "Constructing mental model for", name });
/* 644:743 */     model = new MentalModel(name, file);
/* 645:744 */     this.localMentalModels.put(name, model);
/* 646:745 */     Mark.say(new Object[] {Boolean.valueOf(verbose), "Created new mental model of", name });
/* 647:    */     
/* 648:747 */     return model;
/* 649:    */   }
/* 650:    */   
/* 651:    */   private void removeAllTabs()
/* 652:    */   {
/* 653:751 */     GenesisGetters.getMentalModelViewer().removeAll();
/* 654:    */   }
/* 655:    */   
/* 656:    */   private void removeTab(String name)
/* 657:    */   {
/* 658:755 */     for (int i = 0; i < GenesisGetters.getMentalModelViewer().getTabCount(); i++) {
/* 659:756 */       if (name.equals(GenesisGetters.getMentalModelViewer().getTitleAt(i)))
/* 660:    */       {
/* 661:757 */         GenesisGetters.getMentalModelViewer().remove(i);
/* 662:758 */         break;
/* 663:    */       }
/* 664:    */     }
/* 665:    */   }
/* 666:    */   
/* 667:    */   public static MentalModel getGlobalMentalModel(String name)
/* 668:    */   {
/* 669:764 */     return (MentalModel)globalModels.get(name);
/* 670:    */   }
/* 671:    */   
/* 672:    */   public static MentalModel loadGlobalMentalModel(String name)
/* 673:    */   {
/* 674:768 */     return loadGlobalMentalModel(name, name);
/* 675:    */   }
/* 676:    */   
/* 677:    */   public static MentalModel loadGlobalMentalModel(String name, String file)
/* 678:    */   {
/* 679:772 */     if (globalModels == null) {
/* 680:773 */       globalModels = new HashMap();
/* 681:    */     }
/* 682:775 */     MentalModel model = (MentalModel)globalModels.get(name);
/* 683:776 */     if (model != null)
/* 684:    */     {
/* 685:777 */       Mark.say(new Object[] {"Already have mental model of", name, "so reloading" });
/* 686:778 */       return model;
/* 687:    */     }
/* 688:780 */     model = new MentalModel(name, file);
/* 689:781 */     globalModels.put(name, model);
/* 690:782 */     Mark.say(new Object[] {"Created new mental model of", name });
/* 691:    */     
/* 692:784 */     return model;
/* 693:    */   }
/* 694:    */   
/* 695:    */   public ArrayList<Relation> getPredictionRules()
/* 696:    */   {
/* 697:803 */     return getStoryProcessor().getPredictionRules();
/* 698:    */   }
/* 699:    */   
/* 700:    */   public ArrayList<Entity> getPredictionRules(Entity element)
/* 701:    */   {
/* 702:807 */     return getStoryProcessor().getPredictionRules(element);
/* 703:    */   }
/* 704:    */   
/* 705:    */   public void addPredictionRules(ArrayList<Relation> rules)
/* 706:    */   {
/* 707:811 */     getStoryProcessor().addPredictionRules(rules);
/* 708:    */   }
/* 709:    */   
/* 710:    */   public static void transferPredictionRules(MentalModel source, MentalModel target)
/* 711:    */   {
/* 712:815 */     target.addPredictionRules(source.getPredictionRules());
/* 713:    */   }
/* 714:    */   
/* 715:    */   public ArrayList<Relation> getExplanationRules()
/* 716:    */   {
/* 717:819 */     return getStoryProcessor().getExplanationRules();
/* 718:    */   }
/* 719:    */   
/* 720:    */   public ArrayList<Entity> getExplanationRules(Entity element)
/* 721:    */   {
/* 722:823 */     return getStoryProcessor().getExplanationRules(element);
/* 723:    */   }
/* 724:    */   
/* 725:    */   public void addExplanationRules(ArrayList<Relation> rules)
/* 726:    */   {
/* 727:827 */     getStoryProcessor().addExplanationRules(rules);
/* 728:    */   }
/* 729:    */   
/* 730:    */   public static void transferExplanationRules(MentalModel source, MentalModel target)
/* 731:    */   {
/* 732:831 */     target.addExplanationRules(source.getExplanationRules());
/* 733:    */   }
/* 734:    */   
/* 735:    */   public ArrayList<Relation> getCensorRules()
/* 736:    */   {
/* 737:835 */     return getStoryProcessor().getCensorRules();
/* 738:    */   }
/* 739:    */   
/* 740:    */   public ArrayList<Entity> getCensorRules(Entity element)
/* 741:    */   {
/* 742:839 */     return getStoryProcessor().getCensorRules(element);
/* 743:    */   }
/* 744:    */   
/* 745:    */   public void addCensorRules(ArrayList<Relation> rules)
/* 746:    */   {
/* 747:843 */     getStoryProcessor().addCensorRules(rules);
/* 748:    */   }
/* 749:    */   
/* 750:    */   public static void transferCensorRules(MentalModel source, MentalModel target)
/* 751:    */   {
/* 752:847 */     target.addCensorRules(source.getCensorRules());
/* 753:    */   }
/* 754:    */   
/* 755:    */   public Sequence getConceptPatterns()
/* 756:    */   {
/* 757:851 */     return getStoryProcessor().getConceptPatterns();
/* 758:    */   }
/* 759:    */   
/* 760:    */   public void addConceptPatterns(Sequence conceptPatterns)
/* 761:    */   {
/* 762:855 */     getStoryProcessor().addConceptPatterns(conceptPatterns);
/* 763:    */   }
/* 764:    */   
/* 765:    */   public ArrayList<Sequence> getConcepts()
/* 766:    */   {
/* 767:859 */     return getStoryProcessor().getConcepts();
/* 768:    */   }
/* 769:    */   
/* 770:    */   public void addConcepts(ArrayList<Sequence> concepts)
/* 771:    */   {
/* 772:864 */     getStoryProcessor().addConcepts(concepts);
/* 773:    */   }
/* 774:    */   
/* 775:    */   public void clearRules()
/* 776:    */   {
/* 777:868 */     getStoryProcessor().clearRules();
/* 778:    */   }
/* 779:    */   
/* 780:    */   public void clearConcepts()
/* 781:    */   {
/* 782:872 */     getStoryProcessor().clearConcepts();
/* 783:    */   }
/* 784:    */   
/* 785:    */   public void clearAllMemories()
/* 786:    */   {
/* 787:879 */     getStoryProcessor().clearAllMemories();
/* 788:    */   }
/* 789:    */   
/* 790:    */   public static void transferRules(MentalModel source, MentalModel target)
/* 791:    */   {
/* 792:886 */     transferPredictionRules(source, target);
/* 793:887 */     transferExplanationRules(source, target);
/* 794:888 */     transferCensorRules(source, target);
/* 795:    */   }
/* 796:    */   
/* 797:    */   public static void transferConcepts(MentalModel source, MentalModel target)
/* 798:    */   {
/* 799:895 */     target.addConcepts(source.getConcepts());
/* 800:    */   }
/* 801:    */   
/* 802:    */   public static void transferKnoweledge(MentalModel source, MentalModel target)
/* 803:    */   {
/* 804:902 */     transferRules(source, target);
/* 805:903 */     transferConcepts(source, target);
/* 806:    */   }
/* 807:    */   
/* 808:    */   public static MentalModel getMentalModelHosts(Entity t)
/* 809:    */   {
/* 810:914 */     return (MentalModel)t.getProperty("mental-model-host");
/* 811:    */   }
/* 812:    */   
/* 813:    */   public static boolean hasMentalModelHost(Entity t)
/* 814:    */   {
/* 815:918 */     return t.hasProperty("mental-model-host");
/* 816:    */   }
/* 817:    */   
/* 818:    */   public ArrayList<Entity> getExamples()
/* 819:    */   {
/* 820:923 */     if (this.examples == null) {
/* 821:924 */       this.examples = new ArrayList();
/* 822:    */     }
/* 823:927 */     return this.examples;
/* 824:    */   }
/* 825:    */   
/* 826:    */   public TraitExpert getTraitExpert()
/* 827:    */   {
/* 828:931 */     if (this.traitExpert == null) {
/* 829:932 */       this.traitExpert = new TraitExpert();
/* 830:    */     }
/* 831:934 */     return this.traitExpert;
/* 832:    */   }
/* 833:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     models.MentalModel
 * JD-Core Version:    0.7.0.1
 */