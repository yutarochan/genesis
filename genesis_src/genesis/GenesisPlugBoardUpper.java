/*   1:    */ package genesis;
/*   2:    */ 
/*   3:    */ import connections.Connections;
/*   4:    */ import constants.RecognizedRepresentations;
/*   5:    */ import expert.IdiomExpert;
/*   6:    */ import frames.ActionFrame;
/*   7:    */ import frames.BlockFrame;
/*   8:    */ import frames.CauseFrame;
/*   9:    */ import frames.ForceFrame;
/*  10:    */ import frames.GeometryFrame;
/*  11:    */ import frames.MentalStateFrame;
/*  12:    */ import frames.PlaceFrame;
/*  13:    */ import frames.TransferFrame;
/*  14:    */ import frames.TransitionFrame;
/*  15:    */ import gui.OnsetViewer;
/*  16:    */ import javax.swing.JCheckBox;
/*  17:    */ import javax.swing.JPanel;
/*  18:    */ import javax.swing.JRadioButton;
/*  19:    */ import matthewFay.CharacterModeling.TraitProcessor;
/*  20:    */ import matthewFay.Exporter.ExperimentExportProcessor;
/*  21:    */ import matthewFay.viewers.AlignmentViewer;
/*  22:    */ import matthewFay.viewers.CharacterViewer;
/*  23:    */ import models.MentalModel;
/*  24:    */ import parameters.Radio;
/*  25:    */ import parameters.Switch;
/*  26:    */ import silaSayan.StoryTeller;
/*  27:    */ import silasAast.StaticAudienceModeler;
/*  28:    */ import silasAast.StoryPreSimulator;
/*  29:    */ import start.Generator;
/*  30:    */ import start.Start;
/*  31:    */ import start.StartPostprocessor;
/*  32:    */ import start.StartPreprocessor;
/*  33:    */ import storyProcessor.StoryProcessor;
/*  34:    */ import summarizer.Persuader;
/*  35:    */ import summarizer.Summarizer;
/*  36:    */ import translator.HardWiredTranslator;
/*  37:    */ import translator.Translator;
/*  38:    */ import utils.Mark;
/*  39:    */ 
/*  40:    */ public class GenesisPlugBoardUpper
/*  41:    */   extends GenesisGetters
/*  42:    */ {
/*  43:    */   protected void initializeWiring()
/*  44:    */   {
/*  45: 35 */     initializeStoryProcessorConnections();
/*  46: 36 */     initializeQuestionExpert();
/*  47: 37 */     initializeStoryRecallExpert();
/*  48: 38 */     initializeEscalationExpert();
/*  49: 39 */     initializeOnsetConnections();
/*  50: 40 */     initializeImaginationExpert();
/*  51: 41 */     initializeRest();
/*  52: 42 */     initializeStageDirections();
/*  53: 43 */     initializeDownStreamConnections();
/*  54: 44 */     initializeGeneratorTest();
/*  55: 45 */     connectStoryTellingWires();
/*  56: 46 */     initializeSummarizer();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setToAlignmentMode()
/*  60:    */   {
/*  61: 50 */     if (Radio.alignmentButton.isSelected())
/*  62:    */     {
/*  63: 51 */       Mark.say(new Object[] {"Switching to alignment computing mode" });
/*  64: 52 */       setBottomPanel("Alignment Viewer");
/*  65:    */       
/*  66:    */ 
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71: 59 */       AlignmentViewer.alignmentPanel.removeAll();
/*  72:    */       
/*  73: 61 */       AlignmentViewer.generateNiceOutput.setSelected(true);
/*  74: 62 */       AlignmentViewer.gapFilling.setSelected(true);
/*  75: 63 */       Radio.alignmentButton.setSelected(true);
/*  76:    */     }
/*  77:    */     else
/*  78:    */     {
/*  79: 71 */       Radio.normalModeButton.doClick();
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   private void initializeGeneratorTest()
/*  84:    */   {
/*  85: 76 */     Connections.wire("test", Generator.getGenerator(), getResultContainer());
/*  86:    */   }
/*  87:    */   
/*  88:    */   private void initializeStoryProcessorConnections()
/*  89:    */   {
/*  90: 81 */     Connections.wire("persona port", StartPreprocessor.getStartPreprocessor(), "persona port", getStartParser());
/*  91:    */     
/*  92: 83 */     Connections.wire("change-mode", StartPreprocessor.getStartPreprocessor(), "change-mode", getStartParser());
/*  93:    */     
/*  94: 85 */     Connections.wire("change-mode", getMentalModel1(), "change-mode", getStartParser());
/*  95: 86 */     Connections.wire("change-mode", getMentalModel2(), "change-mode", getStartParser());
/*  96:    */     
/*  97:    */ 
/*  98:    */ 
/*  99: 90 */     Connections.wire("next", GenesisGetters.getAnaphoraExpert(), getMentalModel1());
/* 100: 91 */     Connections.wire("next", GenesisGetters.getAnaphoraExpert(), getMentalModel2());
/* 101:    */     
/* 102: 93 */     Connections.wire("next", GenesisGetters.getAnaphoraExpert(), getNewDisambiguator());
/* 103:    */     
/* 104: 95 */     Connections.wire(getNewDisambiguator(), getTalker());
/* 105:    */     
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:    */ 
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:114 */     Connections.wire("next", getMentalModel1(), getQuestionExpert1());
/* 124:115 */     Connections.wire("next", getMentalModel2(), getQuestionExpert1());
/* 125:    */     
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:    */ 
/* 133:124 */     Connections.wire(getMentalModel1(), getStoryViewer1());
/* 134:125 */     Connections.wire(getMentalModel2(), getStoryViewer2());
/* 135:    */     
/* 136:127 */     Connections.wire("rule port", getMentalModel1(), getRuleViewer1());
/* 137:128 */     Connections.wire("rule port", getMentalModel2(), getRuleViewer2());
/* 138:    */     
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:133 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), getConceptViewer1());
/* 143:134 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), getConceptViewer2());
/* 144:    */     
/* 145:    */ 
/* 146:    */ 
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:    */ 
/* 151:    */ 
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:    */ 
/* 156:    */ 
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:    */ 
/* 166:    */ 
/* 167:    */ 
/* 168:159 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), "clearButtons", getMentalModel1());
/* 169:    */     
/* 170:161 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), "clearButtons", getMentalModel2());
/* 171:    */     
/* 172:163 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), "clear counts", getMentalModel1());
/* 173:164 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), "clear counts", getMentalModel2());
/* 174:    */     
/* 175:166 */     Connections.wire("status port", getFileSourceReader(), getMentalModel1());
/* 176:167 */     Connections.wire("status port", getFileSourceReader(), getMentalModel2());
/* 177:    */     
/* 178:169 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), getElaborationPanel());
/* 179:170 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), getInspectorPanel());
/* 180:    */     
/* 181:172 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), getRuleViewerPanel());
/* 182:173 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), getInstantiatedRuleViewerPanel());
/* 183:174 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), getConceptsViewerPanel());
/* 184:175 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), getInstantiatedConceptViewerPanel());
/* 185:    */     
/* 186:177 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), getOnsetPanel());
/* 187:178 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), getRecallPanel());
/* 188:    */     
/* 189:180 */     Connections.wire("reflections port", getMentalModel1(), getConceptViewer1());
/* 190:181 */     Connections.wire("reflections port", getMentalModel2(), getConceptViewer2());
/* 191:    */   }
/* 192:    */   
/* 193:    */   private void initializeRest()
/* 194:    */   {
/* 195:188 */     Connections.wire("start viewer port", getStartParser(), getStartProcessingViewer());
/* 196:    */     
/* 197:190 */     Connections.wire(getTextEntryBox(), StartPreprocessor.getStartPreprocessor());
/* 198:191 */     Connections.wire(getFileSourceReader(), StartPreprocessor.getStartPreprocessor());
/* 199:    */     
/* 200:193 */     Connections.wire("self", StartPreprocessor.getStartPreprocessor(), StartPreprocessor.getStartPreprocessor());
/* 201:    */     
/* 202:195 */     Connections.wire(StartPreprocessor.getStartPreprocessor(), "sentence", getStartParser());
/* 203:    */     
/* 204:197 */     Connections.wire(getTextEntryBox(), getSourceContainer());
/* 205:    */     
/* 206:199 */     Connections.wire(StartPreprocessor.getStartPreprocessor(), getSourceContainer());
/* 207:    */     
/* 208:    */ 
/* 209:    */ 
/* 210:    */ 
/* 211:    */ 
/* 212:    */ 
/* 213:    */ 
/* 214:    */ 
/* 215:208 */     Connections.wire(getMovieManager(), getSourceContainer());
/* 216:209 */     Connections.wire("state", getFileSourceReader(), getStateMaintainer());
/* 217:    */     
/* 218:211 */     Connections.wire("parse", getStartParser(), Translator.PROCESS, getNewSemanticTranslator());
/* 219:    */     
/* 220:213 */     Connections.wire(getCombinator(), getLinkDisambiguator());
/* 221:214 */     Connections.wire(getHardWiredTranslator(), getLinkDisambiguator());
/* 222:215 */     Connections.wire(getDeriver(), getLinkDisambiguator());
/* 223:216 */     Connections.wire(getLinkDisambiguator(), getAnaphoraExpert());
/* 224:    */     
/* 225:218 */     Connections.wire("result", getNewSemanticTranslator(), StartPostprocessor.getStartPostprocessor());
/* 226:219 */     Connections.wire(StartPostprocessor.getStartPostprocessor(), getIdiomExpert());
/* 227:    */     
/* 228:221 */     Connections.wire(getIdiomExpert(), Switch.disambiguatorSwitch);
/* 229:    */     
/* 230:223 */     Connections.wire("switch tab", getIdiomExpert(), "switch tab", getSourceContainer());
/* 231:224 */     Connections.wire("switch tab", StartPreprocessor.getStartPreprocessor(), "switch tab", getSourceContainer());
/* 232:225 */     Connections.wire("switch tab", getMovieManager(), "switch tab", getSourceContainer());
/* 233:    */     
/* 234:    */ 
/* 235:    */ 
/* 236:    */ 
/* 237:    */ 
/* 238:231 */     Connections.wire("record port", getMentalModel1(), getResultContainer());
/* 239:    */     
/* 240:233 */     Connections.wire("commentary port", getMentalModel1(), getResultContainer());
/* 241:    */     
/* 242:235 */     Connections.wire("record port", getMentalModel1(), getTalker());
/* 243:    */     
/* 244:    */ 
/* 245:    */ 
/* 246:    */ 
/* 247:    */ 
/* 248:    */ 
/* 249:242 */     Connections.wire("complete story analysis port", getMentalModel1(), "COMPLETE STORY ANALYSIS PORT", getAlignmentProcessor());
/* 250:    */     
/* 251:244 */     Connections.wire("complete story analysis port", getMentalModel2(), "COMPLETE STORY ANALYSIS PORT2", getAlignmentProcessor());
/* 252:    */     
/* 253:    */ 
/* 254:247 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), "stage direction", getAlignmentProcessor());
/* 255:    */     
/* 256:249 */     Connections.wire("INSERT ELEMENT PORT", getAlignmentProcessor(), "port for story element injection", getMentalModel1());
/* 257:    */     
/* 258:    */ 
/* 259:252 */     Connections.wire(getAlignmentProcessor(), getAlignmentViewer());
/* 260:253 */     Connections.wire("Graph Port Output", getAlignmentProcessor(), "Graph Port", getAlignmentViewer());
/* 261:    */     
/* 262:255 */     Connections.wire("REFLECTION ALIGNMENT OUTPUT", getAlignmentProcessor(), "REFLECTION ALIGNMENT PORT", getAlignmentViewer());
/* 263:    */     
/* 264:    */ 
/* 265:258 */     Connections.wire("complete story events port", getMentalModel1(), "story port", getClusterProcessor());
/* 266:259 */     Connections.wire("complete story events port", getMentalModel2(), "story port", getClusterProcessor());
/* 267:    */     
/* 268:261 */     Connections.wire("cluster story port", getMentalModel1(), "cluster port", getClusterProcessor());
/* 269:262 */     Connections.wire("cluster story port", getMentalModel2(), "cluster port", getClusterProcessor());
/* 270:    */     
/* 271:    */ 
/* 272:    */ 
/* 273:    */ 
/* 274:    */ 
/* 275:    */ 
/* 276:    */ 
/* 277:    */ 
/* 278:    */ 
/* 279:272 */     Connections.wire("NEW_PLOT_ELEMENT_PORT", getMentalModel1(), "plot play by play port", getCharacterProcessor());
/* 280:    */     
/* 281:274 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), "reset port", getCharacterProcessor());
/* 282:    */     
/* 283:276 */     Connections.wire("complete story analysis port", getMentalModel1(), "complete story analysis port", getCharacterProcessor());
/* 284:    */     
/* 285:    */ 
/* 286:279 */     Connections.wire(StartPreprocessor.getStartPreprocessor(), TraitProcessor.getTraitProcessor());
/* 287:280 */     Connections.wire("next", getAnaphoraExpert(), TraitProcessor.getTraitProcessor());
/* 288:281 */     Connections.wire(TraitProcessor.getTraitProcessor(), getTraitViewer());
/* 289:282 */     Connections.wire(CharacterViewer.TRAIT, getCharacterViewer(), "inspection port", getMentalModel1().getInspectionView());
/* 290:    */     
/* 291:    */ 
/* 292:    */ 
/* 293:286 */     Connections.wire("complete story analysis port", getMentalModel1(), "complete story analysis port", 
/* 294:287 */       ExperimentExportProcessor.getExperimentExportProcessor());
/* 295:    */     
/* 296:    */ 
/* 297:    */ 
/* 298:    */ 
/* 299:292 */     Connections.wire("comparison port", getStoryThreadingProcessor(), "COMPARISON PORT", getStoryThreadingViewer());
/* 300:    */     
/* 301:294 */     Connections.wire("viewer", getCoercionExpert(), getCoerceInterpreter());
/* 302:    */     
/* 303:296 */     Connections.wire("viewer", getAnaphoraExpert(), getRachelsPictureExpert());
/* 304:    */     
/* 305:    */ 
/* 306:    */ 
/* 307:300 */     Connections.wire("complete story analysis port", getMentalModel1(), "Story port", getEntityExpert());
/* 308:301 */     Connections.wire("complete story analysis port", getMentalModel2(), "Story port", getEntityExpert());
/* 309:    */   }
/* 310:    */   
/* 311:    */   private void initializeOnsetConnections()
/* 312:    */   {
/* 313:309 */     Connections.wire("to onset detector port", getMentalModel1(), getOnsetDetector1());
/* 314:310 */     Connections.wire("to onset detector port", getMentalModel2(), getOnsetDetector2());
/* 315:    */     
/* 316:312 */     Connections.wire(getOnsetDetector1(), "discovery port", getOnsetDetector1());
/* 317:313 */     Connections.wire(getOnsetDetector2(), "discovery port", getOnsetDetector2());
/* 318:    */     
/* 319:315 */     Connections.wire("gui port", getOnsetDetector1(), getOnsetViewer1());
/* 320:316 */     Connections.wire("gui port", getOnsetDetector2(), getOnsetViewer2());
/* 321:    */     
/* 322:318 */     Connections.wire("tab port", getOnsetDetector1(), "switch tab", getResultContainer());
/* 323:319 */     Connections.wire("alert port", getOnsetDetector1(), getResultContainer());
/* 324:320 */     Connections.wire("tab port", getOnsetDetector2(), "switch tab", getResultContainer());
/* 325:321 */     Connections.wire("alert port", getOnsetDetector2(), getResultContainer());
/* 326:    */     
/* 327:    */ 
/* 328:    */ 
/* 329:    */ 
/* 330:    */ 
/* 331:    */ 
/* 332:    */ 
/* 333:    */ 
/* 334:    */ 
/* 335:    */ 
/* 336:    */ 
/* 337:    */ 
/* 338:334 */     Connections.wire("tab port", getMentalModel1().getConceptExpert(), "switch tab", getResultContainer());
/* 339:335 */     Connections.wire("English port", getMentalModel1().getConceptExpert(), getResultContainer());
/* 340:336 */     Connections.wire("tab port", getMentalModel2().getConceptExpert(), "switch tab", getResultContainer());
/* 341:337 */     Connections.wire("English port", getMentalModel2().getConceptExpert(), getResultContainer());
/* 342:    */   }
/* 343:    */   
/* 344:    */   private void initializeImaginationExpert()
/* 345:    */   {
/* 346:350 */     Connections.wire("imagine", getCommandExpert(), "imagine", getImagineExpert());
/* 347:351 */     Connections.wire("whether port", getQuestionExpert1(), "question", getImagineExpert());
/* 348:    */     
/* 349:    */ 
/* 350:354 */     Connections.wire("learnedQuantum", getImagineExpert(), getKnowledgeWatcherBlinker());
/* 351:355 */     Connections.wire("learnedQuantum", getImagineExpert(), "learned", getSimpleGenerator());
/* 352:356 */     Connections.wire("imagine", getImagineExpert(), "imagine", getSimpleGenerator());
/* 353:357 */     Connections.wire("say", getImagineExpert(), "say", getSimpleGenerator());
/* 354:    */     
/* 355:    */ 
/* 356:    */ 
/* 357:    */ 
/* 358:    */ 
/* 359:    */ 
/* 360:    */ 
/* 361:365 */     Connections.wire(getImagineExpert(), getExternalMovieViewer());
/* 362:    */   }
/* 363:    */   
/* 364:    */   private void initializeSummarizer()
/* 365:    */   {
/* 366:369 */     Connections.wire("complete story analysis port", getMentalModel1(), "Left perspective input", Summarizer.getSummarizer());
/* 367:370 */     Connections.wire("complete story analysis port", getMentalModel2(), "Right perspective input", Summarizer.getSummarizer());
/* 368:371 */     Connections.wire("Report", Summarizer.getSummarizer(), getSummaryContainer());
/* 369:    */     
/* 370:373 */     Connections.wire("selected tab", getSummaryContainer(), "selected tab", Summarizer.getSummarizer());
/* 371:374 */     Connections.wire("Selected left description to display", Summarizer.getSummarizer(), "summary", getMentalModel1()
/* 372:375 */       .getElaborationView());
/* 373:376 */     Connections.wire("Selected right description to display", Summarizer.getSummarizer(), "summary", getMentalModel2()
/* 374:377 */       .getElaborationView());
/* 375:    */     
/* 376:379 */     Connections.wire("Selected left description to display", Summarizer.getSummarizer(), "inspection port", getMentalModel1()
/* 377:380 */       .getInspectionView());
/* 378:381 */     Connections.wire("Selected right description to display", Summarizer.getSummarizer(), "inspection port", getMentalModel2()
/* 379:382 */       .getInspectionView());
/* 380:    */     
/* 381:384 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), "reset", Summarizer.getSummarizer());
/* 382:    */     
/* 383:386 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), "reset", getMentalModel1().getInspectionView());
/* 384:387 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), "reset", getMentalModel2().getInspectionView());
/* 385:    */     
/* 386:389 */     Connections.wire("reset", Summarizer.getSummarizer(), "reset", getMentalModel1().getPlotUnitBar());
/* 387:390 */     Connections.wire("reset", Summarizer.getSummarizer(), "reset", getMentalModel2().getPlotUnitBar());
/* 388:    */     
/* 389:392 */     Connections.wire("summary", Summarizer.getSummarizer(), Persuader.getPersuader());
/* 390:    */     
/* 391:394 */     Connections.wire("persuade", getCommandExpert(), Persuader.COMMAND, Persuader.getPersuader());
/* 392:    */   }
/* 393:    */   
/* 394:    */   private void initializeStoryRecallExpert()
/* 395:    */   {
/* 396:400 */     Connections.wire("complete story analysis port", getMentalModel1(), "memory port", getStoryRecallExpert1());
/* 397:401 */     Connections.wire("complete story analysis port", getMentalModel2(), "memory port", getStoryRecallExpert2());
/* 398:    */     
/* 399:403 */     Connections.wire("complete story analysis port", getMentalModel1(), "memory port", getStoryRecallExpert2());
/* 400:404 */     Connections.wire("complete story analysis port", getMentalModel2(), "memory port", getStoryRecallExpert1());
/* 401:    */     
/* 402:406 */     Connections.wire("complete story analysis port", getMentalModel1(), "recall port", getStoryRecallExpert1());
/* 403:407 */     Connections.wire("complete story analysis port", getMentalModel2(), "recall port", getStoryRecallExpert2());
/* 404:    */     
/* 405:409 */     Connections.wire(getStoryRecallExpert1(), getStoryRecallViewer1());
/* 406:410 */     Connections.wire(getStoryRecallExpert2(), getStoryRecallViewer2());
/* 407:    */   }
/* 408:    */   
/* 409:    */   private void initializeQuestionExpert()
/* 410:    */   {
/* 411:414 */     Connections.wire("complete story analysis port", getMentalModel1(), "left story", getQuestionExpert1());
/* 412:    */     
/* 413:    */ 
/* 414:417 */     Connections.wire("complete story analysis port", getMentalModel2(), "right story", getQuestionExpert1());
/* 415:418 */     Connections.wire("Reflection analysis port", getMentalModel1(), "left plot units", getQuestionExpert1());
/* 416:419 */     Connections.wire("Reflection analysis port", getMentalModel2(), "right plot units", getQuestionExpert1());
/* 417:    */     
/* 418:421 */     Connections.wire("switch tab", getQuestionExpert1(), "switch tab", getResultContainer());
/* 419:    */     
/* 420:423 */     Connections.wire(getQuestionExpert1(), getResultContainer());
/* 421:    */     
/* 422:425 */     Connections.wire(getQuestionExpert1(), getTalker());
/* 423:    */     
/* 424:    */ 
/* 425:428 */     Connections.wire("causal analysis", getQuestionExpert1(), getCausalTextView());
/* 426:429 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), "reset", getCausalTextView());
/* 427:430 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), getQuestionExpert1());
/* 428:431 */     Connections.wire("insert element port", getQuestionExpert1(), "port for story element injection", getMentalModel1());
/* 429:    */   }
/* 430:    */   
/* 431:    */   private void initializeEscalationExpert()
/* 432:    */   {
/* 433:440 */     Connections.wire("Reflection analysis port", getMentalModel1(), getEscalationExpert1());
/* 434:441 */     Connections.wire("Reflection analysis port", getMentalModel2(), getEscalationExpert2());
/* 435:    */     
/* 436:443 */     Connections.wire("switch tab", getEscalationExpert1(), "switch tab", getResultContainer());
/* 437:444 */     Connections.wire(getEscalationExpert1(), getResultContainer());
/* 438:445 */     Connections.wire("switch tab", getEscalationExpert2(), "switch tab", getResultContainer());
/* 439:446 */     Connections.wire(getEscalationExpert2(), getResultContainer());
/* 440:    */   }
/* 441:    */   
/* 442:    */   private void connectStoryTellingWires()
/* 443:    */   {
/* 444:452 */     Mark.say(
/* 445:    */     
/* 446:    */ 
/* 447:    */ 
/* 448:    */ 
/* 449:    */ 
/* 450:    */ 
/* 451:    */ 
/* 452:    */ 
/* 453:    */ 
/* 454:    */ 
/* 455:    */ 
/* 456:    */ 
/* 457:    */ 
/* 458:    */ 
/* 459:    */ 
/* 460:    */ 
/* 461:    */ 
/* 462:    */ 
/* 463:    */ 
/* 464:    */ 
/* 465:    */ 
/* 466:    */ 
/* 467:    */ 
/* 468:    */ 
/* 469:    */ 
/* 470:    */ 
/* 471:    */ 
/* 472:    */ 
/* 473:    */ 
/* 474:    */ 
/* 475:    */ 
/* 476:    */ 
/* 477:    */ 
/* 478:    */ 
/* 479:    */ 
/* 480:    */ 
/* 481:    */ 
/* 482:    */ 
/* 483:    */ 
/* 484:    */ 
/* 485:    */ 
/* 486:    */ 
/* 487:    */ 
/* 488:    */ 
/* 489:    */ 
/* 490:    */ 
/* 491:    */ 
/* 492:    */ 
/* 493:501 */       new Object[] { "Connecting story teller" });Connections.wire("instantiated reflections", getMentalModel1(), "reflection port 1", getStoryTeller());Connections.wire("complete story events port", getMentalModel1(), StoryTeller.COMPLETE_STORY, getStoryTeller());Connections.wire("Reflection analysis port", getMentalModel1(), "reflection analysis from concept expert", getMentalModel1());Connections.wire("Reflection analysis port", getMentalModel2(), "reflection analysis from concept expert", getMentalModel2());Connections.wire("reflection analysis", getMentalModel1(), StoryTeller.REFLECTION_ANALYSIS, getStoryTeller());Connections.wire("quiescence port", getMentalModel1(), "quiescence port 1", getStoryTeller());Connections.wire("quiescence port", getMentalModel2(), "quiescence port 2", getStoryTeller());Connections.wire("inferences", getMentalModel1(), "teacher inferences", getStoryTeller());Connections.wire("inferences", getMentalModel2(), "inferences", getStoryTeller());Connections.wire("incremental output port", getMentalModel1(), "increment", getStoryTeller());Connections.wire("final-inputs", getMentalModel1(), StoryTeller.EXPLICIT_STORY, getStoryTeller());Connections.wire("rule port", getMentalModel1(), "rules", getStoryTeller());Connections.wire(StoryTeller.TEACH_RULE_PORT, getStoryTeller(), StoryProcessor.LEARNED_RULE_PORT, getMentalModel2());Connections.wire(StoryTeller.NEW_RULE_MESSENGER_PORT, getStoryTeller(), StoryProcessor.NEW_RULE_MESSENGER_PORT, getMentalModel2());Connections.wire(getStoryTeller(), getStoryContainer());Connections.wire("story text", StartPreprocessor.getStartPreprocessor(), getStoryContainer());Connections.wire("story text", StartPreprocessor.getStartPreprocessor(), StoryTeller.PLOT_PORT, getStoryTeller());Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), "stage direction port", getStoryTeller());Connections.wire(StoryTeller.CLEAR, getStoryTeller(), "clear", getStoryContainer());
/* 494:    */   }
/* 495:    */   
/* 496:    */   private void connectNewStoryTellingWires()
/* 497:    */   {
/* 498:544 */     Connections.wire(StaticAudienceModeler.AUDIENCE_COMMONSENSE_OUT, getStaticAudienceModeler(), StoryPreSimulator.AUDIENCE_COMMONSENSE_IN, getStoryPresimulator());
/* 499:    */     
/* 500:546 */     Connections.wire(StaticAudienceModeler.AUDIENCE_REFLECTIVE_OUT, getStaticAudienceModeler(), StoryPreSimulator.AUDIENCE_REFLECTIVE_IN, getStoryPresimulator());
/* 501:    */   }
/* 502:    */   
/* 503:    */   private void initializeStageDirections()
/* 504:    */   {
/* 505:552 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), OnsetViewer.RESET_PORT, getOnsetViewer1());
/* 506:553 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), OnsetViewer.RESET_PORT, getOnsetViewer2());
/* 507:    */     
/* 508:555 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), "idiom", this);
/* 509:556 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), "pause", getFileSourceReader());
/* 510:    */     
/* 511:558 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), Start.STAGE_DIRECTION_PORT, getMentalModel1());
/* 512:559 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), Start.STAGE_DIRECTION_PORT, getMentalModel2());
/* 513:    */     
/* 514:    */ 
/* 515:562 */     Connections.wire("reset port", StartPreprocessor.getStartPreprocessor(), "clear", getResultContainer());
/* 516:    */     
/* 517:564 */     Connections.wire("reset port", StartPreprocessor.getStartPreprocessor(), "clear", getSourceContainer());
/* 518:    */     
/* 519:    */ 
/* 520:    */ 
/* 521:    */ 
/* 522:    */ 
/* 523:    */ 
/* 524:    */ 
/* 525:    */ 
/* 526:573 */     Connections.wire("addPlotUnitButton", getMentalModel1(), "addPlotUnitButton", getInstRulePlotUnitBar1());
/* 527:    */     
/* 528:575 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), "clearButtons", getInstRulePlotUnitBar1());
/* 529:    */     
/* 530:577 */     Connections.wire("addPlotUnitButton", getMentalModel2(), "addPlotUnitButton", getInstRulePlotUnitBar2());
/* 531:    */     
/* 532:579 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), "clearButtons", getInstRulePlotUnitBar2());
/* 533:    */     
/* 534:    */ 
/* 535:582 */     Connections.wire("addPlotUnitButton", getMentalModel1(), "addPlotUnitButton", getRulePlotUnitBar1());
/* 536:    */     
/* 537:584 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), "clearButtons", getRulePlotUnitBar1());
/* 538:    */     
/* 539:586 */     Connections.wire("addPlotUnitButton", getMentalModel2(), "addPlotUnitButton", getRulePlotUnitBar2());
/* 540:    */     
/* 541:588 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), "clearButtons", getRulePlotUnitBar2());
/* 542:    */   }
/* 543:    */   
/* 544:    */   private void initializeDownStreamConnections()
/* 545:    */   {
/* 546:594 */     Connections.wire(IdiomExpert.STORY_PROCESSOR_PORT, getIdiomExpert(), "control", getMentalModel1());
/* 547:595 */     Connections.wire(IdiomExpert.DESCRIBE, getIdiomExpert(), "describe", getMentalModel1());
/* 548:596 */     Connections.wire(IdiomExpert.START_PARSER_PART, getIdiomExpert(), "change-mode", getStartParser());
/* 549:    */     
/* 550:598 */     Connections.wire(IdiomExpert.STORY_PROCESSOR_PORT, getIdiomExpert(), "control", getMentalModel2());
/* 551:599 */     Connections.wire(IdiomExpert.DESCRIBE, getIdiomExpert(), "describe", getMentalModel2());
/* 552:    */     
/* 553:601 */     Connections.wire("self port", getStartParser(), "change-mode", getStartParser());
/* 554:    */     
/* 555:603 */     Connections.wire("up", Switch.disambiguatorSwitch, getStartDisambiguator());
/* 556:604 */     Connections.wire("down", Switch.disambiguatorSwitch, getAnaphoraExpert());
/* 557:605 */     Connections.wire(getStartDisambiguator(), getAnaphoraExpert());
/* 558:    */     
/* 559:607 */     Connections.wire("next", getAnaphoraExpert(), getQuestionExpert1());
/* 560:608 */     Connections.wire("next", getQuestionExpert1(), getCommandExpert());
/* 561:609 */     Connections.wire("next", getCommandExpert(), getDescribeExpert());
/* 562:    */     
/* 563:611 */     Connections.wire("new  element port", getMentalModel1(), "from story port", getCauseExpert());
/* 564:    */     
/* 565:613 */     Connections.wire("next", getDescribeExpert(), getCauseExpert());
/* 566:614 */     Connections.wire("loop", getCauseExpert(), getCauseExpert());
/* 567:615 */     Connections.wire("next", getCauseExpert(), getBeliefExpert());
/* 568:    */     
/* 569:617 */     Connections.wire("next", getBeliefExpert(), getGoalExpert());
/* 570:618 */     Connections.wire("loop", getBeliefExpert(), getCauseExpert());
/* 571:    */     
/* 572:    */ 
/* 573:    */ 
/* 574:622 */     Connections.wire("next", getGoalExpert(), getIntentionExpert());
/* 575:    */     
/* 576:624 */     Connections.wire("next", getIntentionExpert(), getPredictionExpert());
/* 577:    */     
/* 578:626 */     Connections.wire("next", getPredictionExpert(), getPersuationExpert());
/* 579:    */     
/* 580:628 */     Connections.wire("loop", getGoalExpert(), getCauseExpert());
/* 581:    */     
/* 582:630 */     Connections.wire("next", getPersuationExpert(), getCoercionExpert());
/* 583:631 */     Connections.wire("loop", getPersuationExpert(), getCauseExpert());
/* 584:    */     
/* 585:633 */     Connections.wire("next", getCoercionExpert(), getTimeExpert());
/* 586:634 */     Connections.wire("loop", getCoercionExpert(), getCauseExpert());
/* 587:    */     
/* 588:636 */     Connections.wire("next", getTimeExpert(), getRoleExpert());
/* 589:637 */     Connections.wire("loop", getTimeExpert(), getCauseExpert());
/* 590:    */     
/* 591:639 */     Connections.wire("next", getRoleExpert(), getTrajectoryExpert());
/* 592:640 */     Connections.wire("next", getTrajectoryExpert(), getTransitionExpert());
/* 593:    */     
/* 594:642 */     Connections.wire("path", getTrajectoryExpert(), getPathExpert());
/* 595:643 */     Connections.wire("path", getPathExpert(), getPathElementExpert());
/* 596:644 */     Connections.wire("path", getPathElementExpert(), getPlaceExpert());
/* 597:    */     
/* 598:    */ 
/* 599:647 */     Connections.wire("next", getTransitionExpert(), getTransferExpert());
/* 600:    */     
/* 601:649 */     Connections.wire("next", getTransferExpert(), getSocialExpert());
/* 602:    */     
/* 603:651 */     Connections.wire("next", getSocialExpert(), getComparisonExpert());
/* 604:652 */     Connections.wire("next", getComparisonExpert(), getStateExpert());
/* 605:    */     
/* 606:654 */     Connections.wire("next", getStateExpert(), getMoodExpert());
/* 607:655 */     Connections.wire("direct", getStateExpert(), getPathElementExpert());
/* 608:    */     
/* 609:657 */     Connections.wire("next", getMoodExpert(), getPersonalityExpert());
/* 610:    */     
/* 611:659 */     Connections.wire("next", getPersonalityExpert(), getPropertyExpert());
/* 612:660 */     Connections.wire("next", getPropertyExpert(), getPartExpert());
/* 613:    */     
/* 614:662 */     Connections.wire("next", getPartExpert(), getPossessionExpert());
/* 615:    */     
/* 616:664 */     Connections.wire("next", getPossessionExpert(), getJobExpert());
/* 617:665 */     Connections.wire("next", getJobExpert(), getAgentExpert());
/* 618:666 */     Connections.wire("next", getAgentExpert(), getThreadExpert());
/* 619:    */     
/* 620:    */ 
/* 621:669 */     Connections.wire(getLinkDisambiguator(), getLinkViewer());
/* 622:670 */     Connections.wire("port for sending thing to prediction generator", getM2(), getLinkDisambiguator());
/* 623:671 */     Connections.wire(getLinkDisambiguator(), getInternalToEnglishTranslator());
/* 624:672 */     Connections.wire("disambiguated", getLinkDisambiguator(), "disambiguated", getSimpleGenerator());
/* 625:673 */     Connections.wire("disambiguated", getStartDisambiguator(), "disambiguated", getSimpleGenerator());
/* 626:    */     
/* 627:675 */     Connections.wire(getSimpleGenerator(), getTalker());
/* 628:    */     
/* 629:677 */     Connections.wire(getSimpleGenerator(), getRemarksAdapter());
/* 630:    */     
/* 631:    */ 
/* 632:    */ 
/* 633:    */ 
/* 634:    */ 
/* 635:    */ 
/* 636:    */ 
/* 637:    */ 
/* 638:686 */     Connections.wire(getInternalToEnglishTranslator(), getTalkBackViewer());
/* 639:    */     
/* 640:    */ 
/* 641:    */ 
/* 642:    */ 
/* 643:    */ 
/* 644:    */ 
/* 645:    */ 
/* 646:    */ 
/* 647:    */ 
/* 648:    */ 
/* 649:697 */     Connections.wire("port for english sentence input", getDistributionBox(), memorySwitch);
/* 650:698 */     Connections.wire(memorySwitch, "port for english sentence input", getM2());
/* 651:    */     
/* 652:    */ 
/* 653:    */ 
/* 654:    */ 
/* 655:    */ 
/* 656:    */ 
/* 657:705 */     Connections.wire("port for llmerger contents", getM2(), getM2Viewer());
/* 658:    */     
/* 659:707 */     Connections.wire("port for outputing predictions", getM2(), getPredictionsViewer());
/* 660:708 */     Connections.wire("port for sending thing to prediction generator", getM2(), "expectation", getSimpleGenerator());
/* 661:    */     
/* 662:710 */     Connections.wire("expectation", getSimpleGenerator(), "prediction", getTalker());
/* 663:    */     
/* 664:    */ 
/* 665:713 */     Connections.wire(BlockFrame.FRAMETYPE, getM2(), getRepBlockViewer());
/* 666:714 */     Connections.wire(CauseFrame.FRAMETYPE, getM2(), getRepCauseViewer());
/* 667:715 */     Connections.wire(ForceFrame.FRAMETYPE, getM2(), getRepForceViewer());
/* 668:716 */     Connections.wire(ForceFrame.FRAMETYPE, getM2(), getRepCoerceViewer());
/* 669:717 */     Connections.wire((String)RecognizedRepresentations.TIME_REPRESENTATION, getM2(), getRepTimeViewer());
/* 670:    */     
/* 671:719 */     Connections.wire(GeometryFrame.FRAMETYPE, getM2(), getRepGeometryViewer());
/* 672:720 */     Connections.wire((String)RecognizedRepresentations.PATH_THING, getM2(), getRepPathElementViewer());
/* 673:721 */     Connections.wire(PlaceFrame.FRAMETYPE, getM2(), getRepPlaceViewer());
/* 674:    */     
/* 675:    */ 
/* 676:724 */     Connections.wire(TransitionFrame.FRAMETYPE, getM2(), getRepTransitionViewer());
/* 677:725 */     Connections.wire(TransferFrame.FRAMETYPE, getM2(), getRepTransferViewer());
/* 678:726 */     Connections.wire(ActionFrame.FRAMETYPE, getM2(), getRepActionViewer());
/* 679:727 */     Connections.wire((String)RecognizedRepresentations.SOCIAL_REPRESENTATION, getM2(), getRepSocialViewer());
/* 680:728 */     Connections.wire(MentalStateFrame.FRAMETYPE, getM2(), getRepMoodViewer());
/* 681:    */     
/* 682:    */ 
/* 683:    */ 
/* 684:    */ 
/* 685:    */ 
/* 686:    */ 
/* 687:    */ 
/* 688:    */ 
/* 689:    */ 
/* 690:    */ 
/* 691:    */ 
/* 692:    */ 
/* 693:    */ 
/* 694:    */ 
/* 695:    */ 
/* 696:    */ 
/* 697:    */ 
/* 698:    */ 
/* 699:    */ 
/* 700:    */ 
/* 701:    */ 
/* 702:    */ 
/* 703:    */ 
/* 704:    */ 
/* 705:    */ 
/* 706:    */ 
/* 707:755 */     Connections.wire("next", getAnaphoraExpert(), getStartViewer());
/* 708:    */     
/* 709:    */ 
/* 710:758 */     Connections.wire("next", getAnaphoraExpert(), getDistributionBox());
/* 711:    */     
/* 712:760 */     Connections.wire("off", getSyntaxToSemanticsSwitchBox(), HardWiredTranslator.PROCESS, getHardWiredTranslator());
/* 713:    */   }
/* 714:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     genesis.GenesisPlugBoardUpper
 * JD-Core Version:    0.7.0.1
 */