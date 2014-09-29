/*   1:    */ package genesis;
/*   2:    */ 
/*   3:    */ import ati.BorderedParallelJPanel;
/*   4:    */ import ati.ParallelJPanel;
/*   5:    */ import connections.ButtonSwitchBox;
/*   6:    */ import gui.ActivityMonitor;
/*   7:    */ import gui.JMatrixPanel;
/*   8:    */ import gui.JTransparentRadioButton;
/*   9:    */ import gui.TabbedMentalModelViewer;
/*  10:    */ import java.awt.Color;
/*  11:    */ import java.awt.Dimension;
/*  12:    */ import javax.swing.ButtonGroup;
/*  13:    */ import javax.swing.JButton;
/*  14:    */ import javax.swing.JCheckBox;
/*  15:    */ import javax.swing.JCheckBoxMenuItem;
/*  16:    */ import javax.swing.JComboBox;
/*  17:    */ import javax.swing.JComponent;
/*  18:    */ import javax.swing.JLabel;
/*  19:    */ import javax.swing.JPanel;
/*  20:    */ import javax.swing.JRadioButton;
/*  21:    */ import javax.swing.JTabbedPane;
/*  22:    */ import parameters.Radio;
/*  23:    */ import parameters.Switch;
/*  24:    */ import utils.Webstart;
/*  25:    */ 
/*  26:    */ public class GenesisControls
/*  27:    */   extends GenesisMenus
/*  28:    */ {
/*  29:    */   private BorderedParallelJPanel storyTellingControls;
/*  30:    */   private BorderedParallelJPanel storyReadingControls;
/*  31:    */   private BorderedParallelJPanel storySummaryControls;
/*  32:    */   protected static JButton nextButton;
/*  33:    */   protected static JButton runButton;
/*  34:    */   private ButtonSwitchBox syntaxToSemanticsSwitchBox;
/*  35:    */   public static JRadioButton leftButton;
/*  36:    */   public static JRadioButton rightButton;
/*  37:    */   public static JRadioButton bothButton;
/*  38:    */   public static JComboBox teachingLevel;
/*  39:    */   public static JCheckBoxMenuItem collaboration;
/*  40: 49 */   public static JButton makeLargeVideoRecordingButton = new JButton("Set video recording dimensions to 1600 x 1200 (4:3)");
/*  41: 51 */   public static JButton makeSmallVideoRecordingButton = new JButton("Set video recording dimensions to 1024 x 768 (4:3, best for PowerPoint)");
/*  42: 53 */   public static JButton makeMediumVideoRecordingButton = new JButton("Set video recording dimensions to 1280 x 1024");
/*  43: 55 */   protected JButton wordnetPurgeButton = new JButton("Purge WordNet cache");
/*  44: 57 */   protected JButton startPurgeButton = new JButton("Purge Start cache");
/*  45: 59 */   protected JButton clearMemoryButton = new JButton("Clear memory");
/*  46: 61 */   protected JButton eraseTextButton = new JButton("Erase text");
/*  47: 63 */   protected JButton disambiguationButton = new JButton("Load disambiguating events");
/*  48: 65 */   protected JButton experienceButton = new JButton("Load visual events");
/*  49: 67 */   protected JButton focusButton = new JButton("Select focus experience");
/*  50: 69 */   protected JButton clearSummaryTableButton = new JButton("Clear summary table");
/*  51:    */   protected ButtonGroup leftRightGroup;
/*  52:    */   private JTabbedPane controls;
/*  53:    */   private static TabbedMentalModelViewer mentalModelViewer;
/*  54:    */   private ParallelJPanel conceptOptionsPanel;
/*  55: 79 */   private ParallelJPanel storyTellingButtons = null;
/*  56:    */   private ParallelJPanel debuggingPanel;
/*  57:    */   private ParallelJPanel checkInPanel;
/*  58:    */   private ParallelJPanel co57Panel;
/*  59:    */   private ParallelJPanel videoPanel;
/*  60:    */   private ParallelJPanel graveyardPanel;
/*  61:    */   private ParallelJPanel actionButtons;
/*  62: 93 */   protected JButton testRepresentationsButton = new JButton("Run test sentences");
/*  63: 95 */   protected JButton testStoriesButton = new JButton("Run test stories");
/*  64: 97 */   protected JButton rerunExperiment = new JButton("Run experiment again");
/*  65: 99 */   protected JButton rereadFile = new JButton("Read file again");
/*  66:101 */   protected JButton demoSimulator = new JButton("Demo simulator");
/*  67:103 */   protected JButton debugVideoFileButton = new JButton("Demo video story");
/*  68:105 */   protected JButton loopButton = new JButton("Run loop");
/*  69:107 */   protected JButton kjFileButton = new JButton("Korea/Japen");
/*  70:109 */   protected JButton kjeFileButton = new JButton("Korea/Japan-Estonia/Russia");
/*  71:111 */   protected JButton loadVideoPrecedents = new JButton("Load precedents");
/*  72:113 */   protected JButton visionFileButton = new JButton("Vision commands");
/*  73:115 */   protected JButton debugButton1 = new JButton("Debug text in debug1.txt");
/*  74:117 */   protected JButton debugButton2 = new JButton("Debug text in debug2.txt");
/*  75:119 */   protected JButton runWorkbenchTest = new JButton("Run Story Workbench test");
/*  76:121 */   protected JButton simulateCo57Button = new JButton("Test/Simulate Co57");
/*  77:123 */   protected JButton connectTestingBox = new JButton("Connect story testing box");
/*  78:125 */   protected JButton disconnectTestingBox = new JButton("Disconnect story testing box");
/*  79:127 */   protected JButton test1Button = new JButton("Check in test 1");
/*  80:129 */   protected JButton test2Button = new JButton("Check in test 2");
/*  81:131 */   public static JCheckBox useNewMatcherCheckBox = new JCheckBox("Use unified matcher", true);
/*  82:133 */   public static JCheckBox reportMatchingDifferencesCheckBox = new JCheckBox("Report Matcher Differences", false);
/*  83:135 */   public static JRadioButton co57LocalPassthrough = new JRadioButton("Use Local Passthrough Server", true);
/*  84:137 */   public static JRadioButton co57Passthrough = new JRadioButton("Use Co57 Passthrough Server");
/*  85:139 */   public static JRadioButton co57SimulatorAndTranslator = new JRadioButton("Use Beryl Simulator Translator", true);
/*  86:141 */   public static JRadioButton co57JustTranslator = new JRadioButton("Only start Beryl Translator");
/*  87:146 */   public static JLabel testStartConnection = new JLabel("Test");
/*  88:    */   private JMatrixPanel mainMatrix;
/*  89:    */   private JMatrixPanel readTellMatrix;
/*  90:    */   private JMatrixPanel miscellaneousMatrix;
/*  91:    */   private JMatrixPanel debuggingMatrix;
/*  92:    */   private JMatrixPanel graveyardMatrix;
/*  93:    */   private BorderedParallelJPanel presentationPanel;
/*  94:    */   private BorderedParallelJPanel modePanel;
/*  95:    */   private BorderedParallelJPanel connectionPanel;
/*  96:    */   private BorderedParallelJPanel testPanel;
/*  97:    */   private BorderedParallelJPanel corpsePanel;
/*  98:    */   
/*  99:    */   protected ButtonSwitchBox getSyntaxToSemanticsSwitchBox()
/* 100:    */   {
/* 101:149 */     if (this.syntaxToSemanticsSwitchBox == null)
/* 102:    */     {
/* 103:150 */       this.syntaxToSemanticsSwitchBox = new ButtonSwitchBox(Switch.useUnderstand);
/* 104:151 */       this.syntaxToSemanticsSwitchBox.setName("Switch");
/* 105:    */     }
/* 106:153 */     return this.syntaxToSemanticsSwitchBox;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public static JButton getNextButton()
/* 110:    */   {
/* 111:157 */     if (nextButton == null)
/* 112:    */     {
/* 113:158 */       nextButton = new JButton("Next");
/* 114:159 */       nextButton.setName("Next button");
/* 115:160 */       nextButton.setEnabled(false);
/* 116:    */     }
/* 117:162 */     return nextButton;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static JButton getRunButton()
/* 121:    */   {
/* 122:166 */     if (runButton == null)
/* 123:    */     {
/* 124:167 */       runButton = new JButton("Run");
/* 125:168 */       runButton.setName("Run button");
/* 126:169 */       runButton.setEnabled(false);
/* 127:    */     }
/* 128:171 */     return runButton;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public JTabbedPane getControls()
/* 132:    */   {
/* 133:175 */     if (this.controls == null)
/* 134:    */     {
/* 135:176 */       this.controls = new JTabbedPane();
/* 136:177 */       this.controls.setBackground(Color.WHITE);
/* 137:178 */       this.controls.setOpaque(true);
/* 138:179 */       this.controls.setName("Controls");
/* 139:180 */       this.controls.addTab("Main", getMainMatrix());
/* 140:181 */       this.controls.addTab("Read/Tell", getReadTellMatrix());
/* 141:182 */       setMinimumHeight(this.controls, 0);
/* 142:    */       
/* 143:184 */       this.controls.addTab("Miscellaneous", getMiscellaneousMatrix());
/* 144:185 */       if (!Webstart.isWebStart())
/* 145:    */       {
/* 146:186 */         this.controls.addTab("Debugging", getDebuggingMatrix());
/* 147:187 */         this.controls.addTab("Graveyard", getGraveyardMatrix());
/* 148:    */       }
/* 149:    */     }
/* 150:190 */     return this.controls;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public static TabbedMentalModelViewer getMentalModelViewer()
/* 154:    */   {
/* 155:194 */     if (mentalModelViewer == null)
/* 156:    */     {
/* 157:195 */       mentalModelViewer = new TabbedMentalModelViewer();
/* 158:    */       
/* 159:197 */       mentalModelViewer.setBackground(Color.WHITE);
/* 160:198 */       mentalModelViewer.setOpaque(true);
/* 161:199 */       mentalModelViewer.setName("Mental Models");
/* 162:200 */       setMinimumHeight(mentalModelViewer, 0);
/* 163:    */     }
/* 164:202 */     return mentalModelViewer;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public JMatrixPanel getMainMatrix()
/* 168:    */   {
/* 169:206 */     if (this.mainMatrix == null)
/* 170:    */     {
/* 171:207 */       this.mainMatrix = new JMatrixPanel();
/* 172:208 */       this.mainMatrix.setMinimumSize(new Dimension(460, 350));
/* 173:209 */       this.mainMatrix.add(getActionButtons(), 0, 0, 10, 6);
/* 174:    */       
/* 175:211 */       this.mainMatrix.add(getStorySummaryControls(), 10, 0, 10, 6);
/* 176:212 */       this.mainMatrix.add(getConnectionPanel(), 0, 6, 14, 4);
/* 177:213 */       this.mainMatrix.add(getPresentationPanel(), 14, 6, 6, 4);
/* 178:    */     }
/* 179:216 */     return this.mainMatrix;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public JMatrixPanel getReadTellMatrix()
/* 183:    */   {
/* 184:220 */     if (this.readTellMatrix == null)
/* 185:    */     {
/* 186:221 */       this.readTellMatrix = new JMatrixPanel();
/* 187:222 */       this.readTellMatrix.add(getStoryReadingControls(), 0, 0, 20, 10);
/* 188:223 */       this.readTellMatrix.add(getStoryTellingControls(), 20, 0, 10, 10);
/* 189:224 */       this.readTellMatrix.add(getModePanel(), 30, 0, 10, 10);
/* 190:    */     }
/* 191:227 */     return this.readTellMatrix;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public JMatrixPanel getMiscellaneousMatrix()
/* 195:    */   {
/* 196:232 */     if (this.miscellaneousMatrix == null)
/* 197:    */     {
/* 198:233 */       this.miscellaneousMatrix = new JMatrixPanel();
/* 199:    */       
/* 200:235 */       this.miscellaneousMatrix.add(getConceptOptionsPanel(), 0, 0, 10, 10);
/* 201:236 */       this.miscellaneousMatrix.add(getVideoPanel(), 0, 10, 10, 15);
/* 202:    */     }
/* 203:240 */     return this.miscellaneousMatrix;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public JMatrixPanel getDebuggingMatrix()
/* 207:    */   {
/* 208:244 */     if (this.debuggingMatrix == null)
/* 209:    */     {
/* 210:245 */       this.debuggingMatrix = new JMatrixPanel();
/* 211:246 */       this.debuggingMatrix.add(getDebuggingPanel(), 0, 0, 10, 5);
/* 212:247 */       this.debuggingMatrix.add(getCheckInPanel(), 0, 5, 2, 5);
/* 213:248 */       this.debuggingMatrix.add(getCo57Panel(), 5, 5, 5, 5);
/* 214:249 */       this.debuggingMatrix.add(getTestPanel(), 2, 5, 3, 5);
/* 215:    */     }
/* 216:251 */     return this.debuggingMatrix;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public JMatrixPanel getGraveyardMatrix()
/* 220:    */   {
/* 221:255 */     if (this.graveyardMatrix == null)
/* 222:    */     {
/* 223:256 */       this.graveyardMatrix = new JMatrixPanel();
/* 224:257 */       this.graveyardMatrix.add(getGraveyardPanel(), 0, 0, 10, 8);
/* 225:258 */       this.graveyardMatrix.add(getCorpsePanel(), 0, 8, 5, 5);
/* 226:    */     }
/* 227:260 */     return this.graveyardMatrix;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public ParallelJPanel getCo57Panel()
/* 231:    */   {
/* 232:264 */     if (this.co57Panel == null)
/* 233:    */     {
/* 234:265 */       this.co57Panel = new BorderedParallelJPanel("Co57 communication");
/* 235:266 */       ButtonGroup group = new ButtonGroup();
/* 236:267 */       group.add(co57LocalPassthrough);
/* 237:268 */       group.add(co57Passthrough);
/* 238:    */       
/* 239:270 */       this.co57Panel.addRight(co57LocalPassthrough);
/* 240:271 */       this.co57Panel.addRight(co57Passthrough);
/* 241:    */       
/* 242:273 */       ButtonGroup group2 = new ButtonGroup();
/* 243:274 */       group2.add(co57SimulatorAndTranslator);
/* 244:275 */       group2.add(co57JustTranslator);
/* 245:    */       
/* 246:277 */       this.co57Panel.addRight(co57SimulatorAndTranslator);
/* 247:278 */       this.co57Panel.addRight(co57JustTranslator);
/* 248:    */     }
/* 249:280 */     return this.co57Panel;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public ParallelJPanel getCheckInPanel()
/* 253:    */   {
/* 254:284 */     if (this.checkInPanel == null)
/* 255:    */     {
/* 256:285 */       this.checkInPanel = new BorderedParallelJPanel("Check in tests");
/* 257:286 */       this.checkInPanel.addLeft(this.test1Button);
/* 258:287 */       this.checkInPanel.addLeft(this.test2Button);
/* 259:    */     }
/* 260:289 */     return this.checkInPanel;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public ParallelJPanel getDebuggingPanel()
/* 264:    */   {
/* 265:293 */     if (this.debuggingPanel == null)
/* 266:    */     {
/* 267:294 */       this.debuggingPanel = new BorderedParallelJPanel("Miscellaneous");
/* 268:295 */       this.debuggingPanel.setBackground(Color.WHITE);
/* 269:296 */       this.debuggingPanel.setOpaque(true);
/* 270:    */       
/* 271:298 */       this.debuggingPanel.addLeft(Switch.timers);
/* 272:299 */       this.debuggingPanel.addLeft(Switch.startTimer);
/* 273:300 */       this.debuggingPanel.addLeft(Switch.assertionTimer);
/* 274:301 */       this.debuggingPanel.addLeft(Switch.storyTimer);
/* 275:302 */       this.debuggingPanel.addLeft(Switch.showStartProcessingDetails);
/* 276:    */       
/* 277:304 */       this.debuggingPanel.addCenter(Switch.showElaborationViewerDetails);
/* 278:305 */       this.debuggingPanel.addCenter(Switch.showTranslationDetails);
/* 279:    */       
/* 280:    */ 
/* 281:    */ 
/* 282:309 */       this.debuggingPanel.addCenter(useNewMatcherCheckBox);
/* 283:310 */       this.debuggingPanel.addCenter(reportMatchingDifferencesCheckBox);
/* 284:    */       
/* 285:312 */       this.debuggingPanel.addRight(this.debugButton1);
/* 286:313 */       this.debuggingPanel.addRight(this.debugButton2);
/* 287:    */       
/* 288:315 */       this.debuggingPanel.addRight(this.clearSummaryTableButton);
/* 289:    */       
/* 290:317 */       this.debuggingPanel.addRight(this.simulateCo57Button);
/* 291:318 */       this.debuggingPanel.addRight(this.connectTestingBox);
/* 292:319 */       this.debuggingPanel.addRight(this.disconnectTestingBox);
/* 293:320 */       this.disconnectTestingBox.setEnabled(false);
/* 294:    */     }
/* 295:323 */     return this.debuggingPanel;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public ParallelJPanel getVideoPanel()
/* 299:    */   {
/* 300:327 */     if (this.videoPanel == null)
/* 301:    */     {
/* 302:328 */       this.videoPanel = new BorderedParallelJPanel("Recording");
/* 303:329 */       this.videoPanel.addLeft(makeSmallVideoRecordingButton);
/* 304:330 */       this.videoPanel.addLeft(makeLargeVideoRecordingButton);
/* 305:331 */       this.videoPanel.addLeft(makeMediumVideoRecordingButton);
/* 306:    */     }
/* 307:333 */     return this.videoPanel;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public BorderedParallelJPanel getPresentationPanel()
/* 311:    */   {
/* 312:338 */     if (this.presentationPanel == null)
/* 313:    */     {
/* 314:339 */       this.presentationPanel = new BorderedParallelJPanel("Presentation");
/* 315:340 */       this.presentationPanel.addLeft(Switch.showTextEntryBox);
/* 316:341 */       this.presentationPanel.addLeft(Switch.showOnsetSwitch);
/* 317:342 */       this.presentationPanel.addLeft(Switch.showDisconnectedSwitch);
/* 318:343 */       this.presentationPanel.addLeft(Switch.useSpeechCheckBox);
/* 319:    */     }
/* 320:346 */     return this.presentationPanel;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public BorderedParallelJPanel getModePanel()
/* 324:    */   {
/* 325:372 */     if (this.modePanel == null)
/* 326:    */     {
/* 327:373 */       this.modePanel = new BorderedParallelJPanel("Mode");
/* 328:374 */       this.modePanel.addLeft(Radio.normalModeButton);
/* 329:375 */       this.modePanel.addLeft(Radio.tellStoryButton);
/* 330:376 */       this.modePanel.addLeft(Radio.calculateSimilarityButton);
/* 331:377 */       this.modePanel.addLeft(Radio.alignmentButton);
/* 332:    */     }
/* 333:379 */     return this.modePanel;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public JPanel getConnectionPanel()
/* 337:    */   {
/* 338:384 */     if (this.connectionPanel == null)
/* 339:    */     {
/* 340:385 */       this.connectionPanel = new BorderedParallelJPanel("Connections");
/* 341:386 */       this.connectionPanel.addLeft(ActivityMonitor.serverStartConnection);
/* 342:387 */       this.connectionPanel.addCenter(ActivityMonitor.directStartConnection);
/* 343:388 */       this.connectionPanel.addRight(ActivityMonitor.serverStartConnectionFault);
/* 344:    */       
/* 345:390 */       this.connectionPanel.addLeft(ActivityMonitor.serverGeneratorConnection);
/* 346:391 */       this.connectionPanel.addCenter(ActivityMonitor.directGeneratorConnection);
/* 347:392 */       this.connectionPanel.addRight(ActivityMonitor.serverWordNetConnection);
/* 348:    */       
/* 349:394 */       this.connectionPanel.addLeft(ActivityMonitor.translatorLight);
/* 350:395 */       this.connectionPanel.addCenter(ActivityMonitor.commonsenseLight);
/* 351:396 */       this.connectionPanel.addRight(ActivityMonitor.conceptLight);
/* 352:    */       
/* 353:398 */       this.connectionPanel.addLeft(Switch.useStartServer);
/* 354:399 */       this.connectionPanel.addCenter(Switch.useStartCache);
/* 355:400 */       this.connectionPanel.addRight(Switch.useWordnetCache);
/* 356:    */     }
/* 357:403 */     return this.connectionPanel;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public JPanel getTestPanel()
/* 361:    */   {
/* 362:407 */     if (this.testPanel == null)
/* 363:    */     {
/* 364:408 */       this.testPanel = new BorderedParallelJPanel("Test");
/* 365:409 */       this.testPanel.addLeft(ActivityMonitor.betaStartConnection);
/* 366:410 */       this.testPanel.addLeft(ActivityMonitor.betaGeneratorConnection);
/* 367:411 */       this.testPanel.addLeft(Switch.useStartBeta);
/* 368:412 */       this.testPanel.addLeft(Switch.testStartBeta);
/* 369:413 */       Switch.testStartBeta.setToolTipText("See console for normal/beta comparison");
/* 370:    */     }
/* 371:415 */     return this.testPanel;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public JPanel getActionButtons()
/* 375:    */   {
/* 376:419 */     if (this.actionButtons == null)
/* 377:    */     {
/* 378:420 */       this.actionButtons = new BorderedParallelJPanel("Actions");
/* 379:421 */       this.actionButtons.setBackground(Color.WHITE);
/* 380:422 */       this.actionButtons.setOpaque(true);
/* 381:423 */       this.actionButtons.addRight(this.rerunExperiment);
/* 382:424 */       this.actionButtons.addRight(this.rereadFile);
/* 383:425 */       this.actionButtons.addLeft(this.testRepresentationsButton);
/* 384:426 */       this.actionButtons.addLeft(this.testStoriesButton);
/* 385:    */       
/* 386:428 */       this.actionButtons.addLeft(this.eraseTextButton);
/* 387:    */       
/* 388:430 */       this.actionButtons.addLeft(this.experienceButton);
/* 389:    */       
/* 390:432 */       this.actionButtons.addRight(getNextButton());
/* 391:433 */       this.actionButtons.addRight(getRunButton());
/* 392:    */       
/* 393:435 */       this.actionButtons.addLeft(this.startPurgeButton);
/* 394:436 */       this.actionButtons.addLeft(this.wordnetPurgeButton);
/* 395:    */       
/* 396:438 */       this.rereadFile.setEnabled(false);
/* 397:439 */       this.rerunExperiment.setEnabled(false);
/* 398:    */       
/* 399:441 */       ButtonGroup group = new ButtonGroup();
/* 400:442 */       group.add(Radio.normalModeButton);
/* 401:443 */       group.add(Radio.tellStoryButton);
/* 402:444 */       group.add(Radio.calculateSimilarityButton);
/* 403:445 */       group.add(Radio.alignmentButton);
/* 404:    */       
/* 405:447 */       Radio.normalModeButton.setOpaque(false);
/* 406:448 */       Radio.tellStoryButton.setOpaque(false);
/* 407:449 */       Radio.calculateSimilarityButton.setOpaque(false);
/* 408:    */     }
/* 409:452 */     return this.actionButtons;
/* 410:    */   }
/* 411:    */   
/* 412:    */   public BorderedParallelJPanel getStoryTellingControls()
/* 413:    */   {
/* 414:456 */     if (this.storyTellingControls == null)
/* 415:    */     {
/* 416:457 */       this.storyTellingControls = new BorderedParallelJPanel("Story telling");
/* 417:458 */       this.storyTellingControls.addLeft(Radio.spoonFeedButton);
/* 418:459 */       this.storyTellingControls.addLeft(Radio.primingButton);
/* 419:460 */       this.storyTellingControls.addLeft(Radio.primingWithIntrospectionButton);
/* 420:461 */       if (!Webstart.isWebStart())
/* 421:    */       {
/* 422:462 */         this.storyTellingControls.addLeft(Radio.summarize);
/* 423:463 */         this.storyTellingControls.addLeft(Radio.conceptSummary);
/* 424:464 */         Radio.summarize.setEnabled(false);
/* 425:465 */         Radio.conceptSummary.setEnabled(false);
/* 426:    */       }
/* 427:468 */       ButtonGroup group = new ButtonGroup();
/* 428:469 */       group.add(Radio.spoonFeedButton);
/* 429:470 */       group.add(Radio.primingButton);
/* 430:471 */       group.add(Radio.primingWithIntrospectionButton);
/* 431:472 */       Radio.spoonFeedButton.setSelected(true);
/* 432:    */     }
/* 433:474 */     return this.storyTellingControls;
/* 434:    */   }
/* 435:    */   
/* 436:    */   public BorderedParallelJPanel getStoryReadingControls()
/* 437:    */   {
/* 438:478 */     if (this.storyReadingControls == null)
/* 439:    */     {
/* 440:479 */       this.storyReadingControls = new BorderedParallelJPanel("Story reading");
/* 441:480 */       this.storyReadingControls.addLeft(Switch.level6LookForMentalModelEvidence);
/* 442:481 */       this.storyReadingControls.addLeft(Switch.level5UseMentalModels);
/* 443:482 */       this.storyReadingControls.addLeft(Switch.level4ConceptPatterns);
/* 444:483 */       this.storyReadingControls.addLeft(Switch.level3ExplantionRules);
/* 445:484 */       this.storyReadingControls.addLeft(Switch.Level2PredictionRules);
/* 446:    */     }
/* 447:486 */     return this.storyReadingControls;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public BorderedParallelJPanel getStorySummaryControls()
/* 451:    */   {
/* 452:490 */     if (this.storySummaryControls == null)
/* 453:    */     {
/* 454:491 */       this.storySummaryControls = new BorderedParallelJPanel("Story summarizing");
/* 455:492 */       this.storySummaryControls.addLeft(Switch.summarizeViaPHW);
/* 456:493 */       this.storySummaryControls.addLeft(Switch.includeUnabriggedProcessing);
/* 457:494 */       this.storySummaryControls.addLeft(Switch.postHocProcessing);
/* 458:495 */       this.storySummaryControls.addLeft(Switch.meansProcessing);
/* 459:496 */       this.storySummaryControls.addLeft(Switch.abductionProcessing);
/* 460:497 */       this.storySummaryControls.addLeft(Switch.includeAgentRolesInSummary);
/* 461:498 */       this.storySummaryControls.addLeft(Switch.countConceptNetWords);
/* 462:    */     }
/* 463:500 */     return this.storySummaryControls;
/* 464:    */   }
/* 465:    */   
/* 466:503 */   public static final JRadioButton summarize = new JTransparentRadioButton("Summarize");
/* 467:    */   
/* 468:    */   public BorderedParallelJPanel getCorpsePanel()
/* 469:    */   {
/* 470:506 */     if (this.corpsePanel == null)
/* 471:    */     {
/* 472:507 */       this.corpsePanel = new BorderedParallelJPanel("Obsoleted/Deprecated/Obscure/Rotted/Moribund---off by default");
/* 473:508 */       this.corpsePanel.addLeft(Switch.disambiguatorSwitch);
/* 474:509 */       this.corpsePanel.addCenter(Switch.showBackgroundElements);
/* 475:510 */       this.corpsePanel.addLeft(memorySwitch);
/* 476:511 */       this.corpsePanel.addCenter(Switch.conceptSwitch);
/* 477:512 */       this.corpsePanel.addRight(Switch.detectMultipleReflectionsSwitch);
/* 478:    */     }
/* 479:514 */     return this.corpsePanel;
/* 480:    */   }
/* 481:    */   
/* 482:    */   public JPanel getConceptOptionsPanel()
/* 483:    */   {
/* 484:518 */     if (this.conceptOptionsPanel == null)
/* 485:    */     {
/* 486:519 */       this.conceptOptionsPanel = new BorderedParallelJPanel("Various");
/* 487:    */       
/* 488:521 */       this.conceptOptionsPanel.addLeft(Switch.reportSubConceptsSwitch);
/* 489:    */       
/* 490:523 */       this.conceptOptionsPanel.addLeft(Switch.findConceptsContinuously);
/* 491:    */     }
/* 492:532 */     return this.conceptOptionsPanel;
/* 493:    */   }
/* 494:    */   
/* 495:    */   public ParallelJPanel getGraveyardPanel()
/* 496:    */   {
/* 497:546 */     if (this.graveyardPanel == null)
/* 498:    */     {
/* 499:547 */       this.graveyardPanel = new BorderedParallelJPanel("Graveyard");
/* 500:548 */       this.graveyardPanel.setBackground(Color.WHITE);
/* 501:549 */       this.graveyardPanel.setOpaque(true);
/* 502:    */       
/* 503:551 */       deprecateLeft(Switch.workWithVision);
/* 504:552 */       deprecateLeft(Switch.useUnderstand);
/* 505:553 */       deprecateLeft(Switch.stepParser);
/* 506:    */       
/* 507:555 */       deprecateCenter(this.debugVideoFileButton);
/* 508:556 */       deprecateCenter(this.demoSimulator);
/* 509:557 */       deprecateCenter(this.kjFileButton);
/* 510:558 */       deprecateCenter(this.kjeFileButton);
/* 511:559 */       deprecateRight(this.visionFileButton);
/* 512:560 */       deprecateRight(this.loadVideoPrecedents);
/* 513:    */       
/* 514:562 */       deprecateRight(this.clearMemoryButton);
/* 515:563 */       deprecateRight(this.focusButton);
/* 516:    */     }
/* 517:566 */     return this.graveyardPanel;
/* 518:    */   }
/* 519:    */   
/* 520:    */   private void deprecateLeft(JComponent c)
/* 521:    */   {
/* 522:570 */     getGraveyardPanel().addLeft(c);
/* 523:571 */     c.setEnabled(false);
/* 524:    */   }
/* 525:    */   
/* 526:    */   private void deprecateCenter(JComponent c)
/* 527:    */   {
/* 528:575 */     getGraveyardPanel().addCenter(c);
/* 529:576 */     c.setEnabled(false);
/* 530:    */   }
/* 531:    */   
/* 532:    */   private void deprecateRight(JComponent c)
/* 533:    */   {
/* 534:580 */     getGraveyardPanel().addRight(c);
/* 535:581 */     c.setEnabled(false);
/* 536:    */   }
/* 537:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     genesis.GenesisControls
 * JD-Core Version:    0.7.0.1
 */