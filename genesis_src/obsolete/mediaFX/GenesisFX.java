/*   1:    */ package obsolete.mediaFX;
/*   2:    */ 
/*   3:    */ import Co57.BerylControls;
/*   4:    */ import Co57.BerylVerbTranslator;
/*   5:    */ import Co57.services.Co57Passthrough;
/*   6:    */ import Signals.BetterSignal;
/*   7:    */ import adamKraft.Co57Connector;
/*   8:    */ import adamKraft.WiredBoxB;
/*   9:    */ import connections.AbstractWiredBox;
/*  10:    */ import connections.Connections;
/*  11:    */ import connections.Connections.NetWireException;
/*  12:    */ import connections.Ports;
/*  13:    */ import connections.TextEntryBox;
/*  14:    */ import genesis.Genesis;
/*  15:    */ import genesis.Genesis.MyActionListener;
/*  16:    */ import genesis.GenesisControls;
/*  17:    */ import gui.RepairedGridLayout;
/*  18:    */ import gui.TabbedTextViewer;
/*  19:    */ import java.awt.BorderLayout;
/*  20:    */ import java.awt.Color;
/*  21:    */ import java.awt.Container;
/*  22:    */ import java.awt.Dimension;
/*  23:    */ import java.awt.event.ActionEvent;
/*  24:    */ import java.awt.event.ActionListener;
/*  25:    */ import java.io.File;
/*  26:    */ import java.io.FileInputStream;
/*  27:    */ import java.io.FileOutputStream;
/*  28:    */ import java.io.ObjectInputStream;
/*  29:    */ import java.io.ObjectOutputStream;
/*  30:    */ import java.net.MalformedURLException;
/*  31:    */ import java.net.URI;
/*  32:    */ import java.net.URL;
/*  33:    */ import java.util.ArrayList;
/*  34:    */ import java.util.Date;
/*  35:    */ import javax.swing.JButton;
/*  36:    */ import javax.swing.JFrame;
/*  37:    */ import javax.swing.JMenuBar;
/*  38:    */ import javax.swing.JPanel;
/*  39:    */ import javax.swing.JSplitPane;
/*  40:    */ import obsolete.mindsEye.DisgustingMoebiusTranslator;
/*  41:    */ import obsolete.mindsEye.GrandCentralStation;
/*  42:    */ import obsolete.mindsEye.MovieSelector;
/*  43:    */ import utils.Mark;
/*  44:    */ import windowGroup.WindowGroupManager;
/*  45:    */ 
/*  46:    */ public class GenesisFX
/*  47:    */   extends Genesis
/*  48:    */ {
/*  49:    */   public static long movieStartTime;
/*  50:    */   private JPanel controlPanel;
/*  51:    */   private BerylVerbTranslator berylVerbTranslator;
/*  52:    */   private WiredBoxB wiredBoxB;
/*  53: 40 */   public String storageFile = "co57signals.data";
/*  54: 42 */   private JButton darpaTestButton = new JButton("DARPA test video");
/*  55: 44 */   private JButton playByPlayButton = new JButton("Real time play by play");
/*  56: 46 */   private JButton recordButton = new JButton("Record Co57 play by play");
/*  57: 48 */   private JButton presentButton = new JButton("Present Co57 play by play");
/*  58:    */   private ButtonListener buttonListener;
/*  59:    */   private RecordedEventAnnouncer recordedEventAnnouncer;
/*  60:    */   private RealtimeEventAnnouncer realtimeEventAnnouncer;
/*  61:    */   private PauseTimer pauseTimer;
/*  62: 58 */   private String penultimateEnglish = null;
/*  63:    */   private FXMediaViewer fxMediaViewer;
/*  64:    */   private FXMediaViewer movieViewer;
/*  65:    */   private FXMediaSelector movieSelector;
/*  66:    */   private FXMediaPlayer fxMediaPlayer;
/*  67:    */   private FXPictureSelector playByPlayVideoSelector;
/*  68:    */   public File currentFile;
/*  69:    */   
/*  70:    */   public static void main(String[] args) {}
/*  71:    */   
/*  72:    */   public GenesisFX()
/*  73:    */   {
/*  74: 87 */     getWindowGroupManager().addJComponent(getMovieViewer());
/*  75:    */     
/*  76: 89 */     getWindowGroupManager().addJComponent(getFxMediaViewer());
/*  77: 90 */     getWindowGroupManager().addJComponent(getMovieSelector());
/*  78: 91 */     getWindowGroupManager().addJComponent(getPlayByPlayVideoSelector());
/*  79:    */     
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83: 96 */     Connections.getPorts(this).addSignalProcessor("real time play by play", "announceRealtimeEvents");
/*  84: 97 */     Connections.getPorts(this).addSignalProcessor("recorded play by play", "announceRecordedEvents");
/*  85:    */     
/*  86: 99 */     this.menuBar.remove(this.demonstrationMenu);
/*  87:    */     
/*  88:101 */     this.menuBar.invalidate();
/*  89:    */     
/*  90:103 */     switchToVision();
/*  91:    */     
/*  92:105 */     revalidate();
/*  93:    */   }
/*  94:    */   
/*  95:    */   private FXPictureSelector getPlayByPlayVideoSelector()
/*  96:    */   {
/*  97:110 */     if (this.playByPlayVideoSelector == null)
/*  98:    */     {
/*  99:111 */       this.playByPlayVideoSelector = new FXPictureSelector();
/* 100:112 */       this.playByPlayVideoSelector.fetchInventory();
/* 101:    */     }
/* 102:115 */     return this.playByPlayVideoSelector;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public FXMediaSelector getMovieSelector()
/* 106:    */   {
/* 107:119 */     if (this.movieSelector == null)
/* 108:    */     {
/* 109:120 */       this.movieSelector = new FXMediaSelector();
/* 110:121 */       this.movieSelector.setName("Media selector");
/* 111:    */     }
/* 112:123 */     return this.movieSelector;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public FXMediaPlayer getFxMediaPlayer()
/* 116:    */   {
/* 117:127 */     if (this.fxMediaPlayer == null) {
/* 118:128 */       this.fxMediaPlayer = new FXMediaPlayer();
/* 119:    */     }
/* 120:130 */     return this.fxMediaPlayer;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public FXMediaViewer getMovieViewer()
/* 124:    */   {
/* 125:134 */     if (this.movieViewer == null)
/* 126:    */     {
/* 127:135 */       this.movieViewer = new FXMediaViewer();
/* 128:136 */       this.movieViewer.setPreferredSize(new Dimension(300, 300));
/* 129:137 */       this.movieViewer.setName("Media");
/* 130:    */     }
/* 131:139 */     return this.movieViewer;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public FXMediaViewer getFxMediaViewer()
/* 135:    */   {
/* 136:143 */     if (this.fxMediaViewer == null)
/* 137:    */     {
/* 138:144 */       this.fxMediaViewer = new FXMediaViewer(true, true, true);
/* 139:145 */       this.fxMediaViewer.setName("Media viewer");
/* 140:146 */       Mark.say(new Object[] {"Creating fxMediaViewer" });
/* 141:    */     }
/* 142:148 */     return this.fxMediaViewer;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public JPanel getControlBar()
/* 146:    */   {
/* 147:152 */     if (this.controlPanel == null)
/* 148:    */     {
/* 149:153 */       this.controlPanel = new JPanel();
/* 150:154 */       this.controlPanel.setLayout(new RepairedGridLayout(1, 0));
/* 151:155 */       this.controlPanel.add(this.darpaTestButton);
/* 152:156 */       this.controlPanel.add(this.playByPlayButton);
/* 153:157 */       this.controlPanel.add(this.recordButton);
/* 154:158 */       this.controlPanel.add(this.presentButton);
/* 155:159 */       this.darpaTestButton.addActionListener(getButtonListener());
/* 156:160 */       this.playByPlayButton.addActionListener(getButtonListener());
/* 157:161 */       this.recordButton.addActionListener(getButtonListener());
/* 158:162 */       this.presentButton.addActionListener(getButtonListener());
/* 159:    */     }
/* 160:164 */     return this.controlPanel;
/* 161:    */   }
/* 162:    */   
/* 163:    */   private BerylVerbTranslator getBerylVerbTranslator()
/* 164:    */   {
/* 165:168 */     if (this.berylVerbTranslator == null) {
/* 166:169 */       this.berylVerbTranslator = new BerylVerbTranslator("BerylVerbTranslator", 
/* 167:170 */         Co57Passthrough.ZMQ_SERVER_WIRED_BOX_SERVICE);
/* 168:    */     }
/* 169:172 */     return this.berylVerbTranslator;
/* 170:    */   }
/* 171:    */   
/* 172:    */   private WiredBoxB getWiredBoxB()
/* 173:    */   {
/* 174:176 */     if (this.wiredBoxB == null) {
/* 175:    */       try
/* 176:    */       {
/* 177:178 */         this.wiredBoxB = new WiredBoxB();
/* 178:179 */         Mark.say(new Object[] {"Created WiredBoxB" });
/* 179:    */       }
/* 180:    */       catch (Connections.NetWireException e)
/* 181:    */       {
/* 182:182 */         Mark.err(new Object[] {"Unable to create WiredBoxB" });
/* 183:183 */         e.printStackTrace();
/* 184:    */       }
/* 185:    */     }
/* 186:186 */     return this.wiredBoxB;
/* 187:    */   }
/* 188:    */   
/* 189:    */   private RealtimeEventAnnouncer getRealtimeEventAnnouncer()
/* 190:    */   {
/* 191:190 */     if (this.realtimeEventAnnouncer == null) {
/* 192:191 */       this.realtimeEventAnnouncer = new RealtimeEventAnnouncer();
/* 193:    */     }
/* 194:193 */     return this.realtimeEventAnnouncer;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public class RealtimeEventAnnouncer
/* 198:    */     extends AbstractWiredBox
/* 199:    */   {
/* 200:198 */     ArrayList<BetterSignal> signals = new ArrayList();
/* 201:    */     
/* 202:    */     private ArrayList<BetterSignal> getSignals()
/* 203:    */     {
/* 204:201 */       return this.signals;
/* 205:    */     }
/* 206:    */     
/* 207:    */     public RealtimeEventAnnouncer()
/* 208:    */     {
/* 209:205 */       Connections.getPorts(this).addSignalProcessor("process");
/* 210:    */     }
/* 211:    */     
/* 212:    */     public void process(Object paramObject)
/* 213:    */     {
/* 214:218 */       throw new Error("Unresolved compilation problem: \n\tThe method getMediaPlayer() from the type FXMediaViewer refers to the missing type MediaPlayer\n");
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   private void switchToVision()
/* 219:    */   {
/* 220:236 */     Mark.say(
/* 221:    */     
/* 222:    */ 
/* 223:    */ 
/* 224:    */ 
/* 225:    */ 
/* 226:    */ 
/* 227:    */ 
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:    */ 
/* 232:248 */       new Object[] { "Switch to genesis vision" });getTextEntryBox().zero();setBottomPanel(getMovieSelector().getName());setLeftPanel(getFxMediaViewer().getName());setRightPanel(getResultContainer().getName());getNorthSouthSplitPane().setDividerLocation(0.75D);getSplitPane().setDividerLocation(0.65D);revalidate();
/* 233:    */   }
/* 234:    */   
/* 235:    */   private ButtonListener getButtonListener()
/* 236:    */   {
/* 237:251 */     if (this.buttonListener == null) {
/* 238:252 */       this.buttonListener = new ButtonListener();
/* 239:    */     }
/* 240:254 */     return this.buttonListener;
/* 241:    */   }
/* 242:    */   
/* 243:    */   class ButtonListener
/* 244:    */     implements ActionListener
/* 245:    */   {
/* 246:    */     ButtonListener() {}
/* 247:    */     
/* 248:    */     public void actionPerformed(ActionEvent e)
/* 249:    */     {
/* 250:260 */       GenesisFX.this.unwire();
/* 251:261 */       if (e.getSource() == GenesisFX.this.darpaTestButton)
/* 252:    */       {
/* 253:263 */         GenesisFX.this.prepareForDarpaTest();
/* 254:    */       }
/* 255:265 */       else if (e.getSource() == GenesisFX.this.playByPlayButton)
/* 256:    */       {
/* 257:266 */         GenesisFX.this.prepareForRealTimePlayByPlay();
/* 258:    */       }
/* 259:268 */       else if (e.getSource() == GenesisFX.this.recordButton)
/* 260:    */       {
/* 261:269 */         Mark.say(new Object[] {"Recording" });
/* 262:270 */         GenesisFX.this.record();
/* 263:    */       }
/* 264:272 */       else if (e.getSource() == GenesisFX.this.presentButton)
/* 265:    */       {
/* 266:273 */         GenesisFX.this.prepareForRecordedPlayByPlay();
/* 267:    */       }
/* 268:    */     }
/* 269:    */   }
/* 270:    */   
/* 271:    */   private void prepareForDarpaTest()
/* 272:    */   {
/* 273:280 */     Mark.say(
/* 274:    */     
/* 275:    */ 
/* 276:    */ 
/* 277:    */ 
/* 278:    */ 
/* 279:    */ 
/* 280:    */ 
/* 281:    */ 
/* 282:    */ 
/* 283:    */ 
/* 284:    */ 
/* 285:    */ 
/* 286:    */ 
/* 287:    */ 
/* 288:    */ 
/* 289:    */ 
/* 290:    */ 
/* 291:    */ 
/* 292:    */ 
/* 293:    */ 
/* 294:    */ 
/* 295:302 */       new Object[] { "Resetting DARPA test and connecting wires..." });Connections.wire("to movie selector", getGrandCentralStation(), getMovieSelector());Connections.wire(MovieSelector.SELECTED_FILE_NAME, getMovieSelector(), getFxMediaPlayer());Connections.wire(FXMediaPlayer.URL, getFxMediaPlayer(), "url", getFxMediaViewer());Connections.wire(FXMediaPlayer.SET_TIME, getFxMediaPlayer(), FXMediaViewer.SET_TIME, getFxMediaViewer());Connections.wire("time", getFxMediaViewer(), FXMediaPlayer.TIME, getFxMediaPlayer());Connections.wire("written commentary", getFxMediaPlayer(), getResultContainer());Connections.wire("control", getFxMediaPlayer(), getResultContainer());Connections.wire("to commentator", getGrandCentralStation(), DisgustingMoebiusTranslator.FROM_IMPACT, getDisgustingMoebiusTranslator());Connections.wire(DisgustingMoebiusTranslator.TO_TALKER, getDisgustingMoebiusTranslator(), "speak", getTalker());Connections.wire(DisgustingMoebiusTranslator.TO_TEXT_VIEWER, getDisgustingMoebiusTranslator(), getResultContainer());Connections.wire(DisgustingMoebiusTranslator.INNERESE, getDisgustingMoebiusTranslator(), getStartViewer());Connections.wire("stats", getGrandCentralStation(), getResultContainer());getGrandCentralStation().initialize();revalidate();
/* 296:    */   }
/* 297:    */   
/* 298:    */   private void prepareForRealTimePlayByPlay()
/* 299:    */   {
/* 300:305 */     Mark.say(
/* 301:    */     
/* 302:    */ 
/* 303:    */ 
/* 304:    */ 
/* 305:    */ 
/* 306:311 */       new Object[] { "Initiating realtime mode" });prepareForPlayByPlay();Connections.wire("selected file name", getPlayByPlayVideoSelector(), "real time play by play", this);Connections.wire("play by play", getBerylVerbTranslator(), getResultContainer());Connections.wire("play by play", getBerylVerbTranslator(), getRealtimeEventAnnouncer());Connections.wire("time", getFxMediaViewer(), getPauseTimer());
/* 307:    */   }
/* 308:    */   
/* 309:    */   private void prepareForRecordedPlayByPlay()
/* 310:    */   {
/* 311:314 */     Mark.say(
/* 312:    */     
/* 313:    */ 
/* 314:    */ 
/* 315:    */ 
/* 316:319 */       new Object[] { "Initiating playback mode" });prepareForPlayByPlay();Connections.wire("selected file name", getPlayByPlayVideoSelector(), "recorded play by play", this);Connections.wire("play by play", getRecordedEventAnnouncer(), getResultContainer());Connections.wire("time", getFxMediaViewer(), getRecordedEventAnnouncer());
/* 317:    */   }
/* 318:    */   
/* 319:    */   private void unwire()
/* 320:    */   {
/* 321:323 */     Connections.disconnect("selected file name", getPlayByPlayVideoSelector(), "real time play by play", this);
/* 322:324 */     Connections.disconnect("play by play", getBerylVerbTranslator(), getResultContainer());
/* 323:325 */     Connections.disconnect("play by play", getBerylVerbTranslator(), getRealtimeEventAnnouncer());
/* 324:326 */     Connections.disconnect("time", getFxMediaViewer(), getPauseTimer());
/* 325:    */     
/* 326:328 */     Connections.disconnect("selected file name", getPlayByPlayVideoSelector(), "recorded play by play", this);
/* 327:329 */     Connections.disconnect("play by play", getRecordedEventAnnouncer(), getResultContainer());
/* 328:330 */     Connections.disconnect("time", getFxMediaViewer(), getRecordedEventAnnouncer());
/* 329:    */   }
/* 330:    */   
/* 331:    */   private void prepareForPlayByPlay()
/* 332:    */   {
/* 333:334 */     getNorthSouthSplitPane().setDividerLocation(0.5D);
/* 334:335 */     setRightPanel("Results");
/* 335:336 */     setBottomPanel(getPlayByPlayVideoSelector().getName());
/* 336:    */   }
/* 337:    */   
/* 338:    */   private String playByPlayFile(File file)
/* 339:    */   {
/* 340:340 */     String name = file.getName();
/* 341:341 */     int index = name.indexOf('.');
/* 342:342 */     name = name.substring(0, index);
/* 343:343 */     return Co57Connector.CACHE + File.separator + "play-by-play" + File.separator + name + ".pbp";
/* 344:    */   }
/* 345:    */   
/* 346:    */   private void record()
/* 347:    */   {
/* 348:348 */     if (this.currentFile == null)
/* 349:    */     {
/* 350:349 */       Mark.err(new Object[] {"No play-by-play file yet; so cannot record pbp file" });
/* 351:350 */       return;
/* 352:    */     }
/* 353:    */     try
/* 354:    */     {
/* 355:353 */       String file = playByPlayFile(this.currentFile);
/* 356:    */       
/* 357:355 */       new File(file).getParentFile().mkdir();
/* 358:356 */       ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file));
/* 359:357 */       stream.writeObject(getRealtimeEventAnnouncer().getSignals());
/* 360:358 */       stream.close();
/* 361:    */     }
/* 362:    */     catch (Exception e)
/* 363:    */     {
/* 364:361 */       Mark.err(new Object[] {"Error in GenesisFX.record" });
/* 365:    */     }
/* 366:    */   }
/* 367:    */   
/* 368:    */   public void announceRecordedEvents(Object object)
/* 369:    */   {
/* 370:366 */     if ((object instanceof String))
/* 371:    */     {
/* 372:367 */       Mark.say(new Object[] {"Announcing recorded events" });
/* 373:368 */       String fileName = (String)object;
/* 374:369 */       this.currentFile = new File(fileName);
/* 375:    */     }
/* 376:    */     else
/* 377:    */     {
/* 378:372 */       Mark.err(new Object[] {"Ooops, wrong argument in announceRecordedEvents" });
/* 379:373 */       return;
/* 380:    */     }
/* 381:375 */     Connections.getPorts(getBerylVerbTranslator()).transmit("play by play", new BetterSignal(new Object[] { "Play by play", 
/* 382:376 */       "clear" }));
/* 383:377 */     String file = playByPlayFile(this.currentFile);
/* 384:    */     try
/* 385:    */     {
/* 386:380 */       ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file));
/* 387:381 */       ArrayList<BetterSignal> signals = (ArrayList)stream.readObject();
/* 388:382 */       stream.close();
/* 389:383 */       Mark.say(new Object[] {"Recorded signal count is", Integer.valueOf(signals.size()) });
/* 390:384 */       getRecordedEventAnnouncer().setSignals(signals);
/* 391:    */     }
/* 392:    */     catch (Exception e)
/* 393:    */     {
/* 394:387 */       Mark.err(new Object[] {"Could not find play by play file ", file });
/* 395:    */     }
/* 396:    */     try
/* 397:    */     {
/* 398:393 */       String url = this.currentFile.toURI().toURL().toExternalForm();
/* 399:394 */       getFxMediaViewer().playMedia(url);
/* 400:    */     }
/* 401:    */     catch (MalformedURLException e)
/* 402:    */     {
/* 403:397 */       Mark.err(new Object[] {"Unable to create url" });
/* 404:398 */       e.printStackTrace();
/* 405:    */     }
/* 406:    */   }
/* 407:    */   
/* 408:    */   public void announceRealtimeEvents(Object object)
/* 409:    */   {
/* 410:403 */     if ((object instanceof String))
/* 411:    */     {
/* 412:404 */       Mark.say(new Object[] {"Announcing real time events" });
/* 413:405 */       String fileName = (String)object;
/* 414:406 */       this.currentFile = new File(fileName);
/* 415:407 */       getRealtimeEventAnnouncer().getSignals().clear();
/* 416:408 */       new AnnounceRealtimeEvents(fileName).run();
/* 417:    */     }
/* 418:    */     else
/* 419:    */     {
/* 420:411 */       Mark.err(new Object[] {"Ooops, wrong argument in announceRealtimeEvents" });
/* 421:412 */       return;
/* 422:    */     }
/* 423:414 */     Connections.getPorts(getBerylVerbTranslator()).transmit("play by play", new BetterSignal(new Object[] { "Play by play", 
/* 424:415 */       "clear" }));
/* 425:    */   }
/* 426:    */   
/* 427:    */   public RecordedEventAnnouncer getRecordedEventAnnouncer()
/* 428:    */   {
/* 429:420 */     if (this.recordedEventAnnouncer == null) {
/* 430:421 */       this.recordedEventAnnouncer = new RecordedEventAnnouncer();
/* 431:    */     }
/* 432:423 */     return this.recordedEventAnnouncer;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public class RecordedEventAnnouncer
/* 436:    */     extends AbstractWiredBox
/* 437:    */   {
/* 438:428 */     ArrayList<BetterSignal> signals = new ArrayList();
/* 439:430 */     int lastAnnounced = -1;
/* 440:432 */     int lastFrame = -1;
/* 441:    */     
/* 442:    */     public RecordedEventAnnouncer()
/* 443:    */     {
/* 444:435 */       Connections.getPorts(this).addSignalProcessor("process");
/* 445:    */     }
/* 446:    */     
/* 447:    */     public void setSignals(ArrayList<BetterSignal> x)
/* 448:    */     {
/* 449:439 */       this.signals = x;
/* 450:    */     }
/* 451:    */     
/* 452:    */     public void process(Object object)
/* 453:    */     {
/* 454:444 */       if ((object instanceof Double))
/* 455:    */       {
/* 456:445 */         Double time = (Double)object;
/* 457:446 */         int frame = (int)(30.0D * time.doubleValue() / 1000.0D);
/* 458:447 */         BetterSignal penultimateSignal = null;
/* 459:449 */         for (int i = 0; i < this.signals.size(); i++)
/* 460:    */         {
/* 461:450 */           BetterSignal signal = (BetterSignal)this.signals.get(i);
/* 462:451 */           if (signal.size() > 5)
/* 463:    */           {
/* 464:452 */             int start = ((Integer)signal.get(2, Integer.class)).intValue();
/* 465:453 */             int end = ((Integer)signal.get(3, Integer.class)).intValue();
/* 466:454 */             int average = (start + end) / 2;
/* 467:455 */             if ((frame >= average) && (i > this.lastAnnounced))
/* 468:    */             {
/* 469:456 */               this.lastAnnounced = i;
/* 470:457 */               this.lastFrame = frame;
/* 471:458 */               String english = (String)signal.get(1, String.class);
/* 472:    */               
/* 473:460 */               english = english.substring(0, english.indexOf('.') + 1);
/* 474:462 */               if (!english.equals(GenesisFX.this.penultimateEnglish))
/* 475:    */               {
/* 476:464 */                 Connections.getPorts(this).transmit("play by play", new BetterSignal(new Object[] { "Play by play", english }));
/* 477:465 */                 GenesisFX.this.penultimateEnglish = english;
/* 478:    */               }
/* 479:470 */               penultimateSignal = signal;
/* 480:    */             }
/* 481:    */             else
/* 482:    */             {
/* 483:472 */               if ((frame >= this.lastFrame) && (frame < average) && (penultimateSignal != null))
/* 484:    */               {
/* 485:474 */                 int currentElapsedTime = ((Integer)signal.get(4, Integer.class)).intValue();
/* 486:475 */                 int previousElapsedTime = ((Integer)penultimateSignal.get(4, Integer.class)).intValue();
/* 487:476 */                 int deltaElapsedTime = currentElapsedTime - previousElapsedTime;
/* 488:    */                 
/* 489:478 */                 int currentFrame = ((Integer)signal.get(3, Integer.class)).intValue();
/* 490:479 */                 int previousFrame = ((Integer)penultimateSignal.get(3, Integer.class)).intValue();
/* 491:480 */                 double deltaFrameTime = (currentFrame - previousFrame) / 30.0D;
/* 492:    */                 
/* 493:482 */                 double ratio = deltaElapsedTime / deltaFrameTime / 3.0D;
/* 494:484 */                 if (ratio <= 1.0D) {
/* 495:    */                   break;
/* 496:    */                 }
/* 497:486 */                 GenesisFX.this.getFxMediaViewer().setRate(0.66D);
/* 498:    */                 
/* 499:488 */                 break;
/* 500:    */               }
/* 501:491 */               if ((frame < this.lastFrame) && (this.lastAnnounced >= 0))
/* 502:    */               {
/* 503:505 */                 this.lastAnnounced = -1;
/* 504:    */                 
/* 505:507 */                 this.lastFrame = frame;
/* 506:    */               }
/* 507:    */             }
/* 508:    */           }
/* 509:    */         }
/* 510:    */       }
/* 511:    */     }
/* 512:    */   }
/* 513:    */   
/* 514:    */   class AnnounceRealtimeEvents
/* 515:    */     extends Thread
/* 516:    */   {
/* 517:    */     String videoName;
/* 518:    */     File videoFile;
/* 519:    */     
/* 520:    */     public AnnounceRealtimeEvents(String videoName)
/* 521:    */     {
/* 522:528 */       videoName = videoName;
/* 523:529 */       this.videoFile = new File(videoName);
/* 524:530 */       GenesisFX.this.currentFile = this.videoFile;
/* 525:    */     }
/* 526:    */     
/* 527:    */     public void run()
/* 528:    */     {
/* 529:535 */       Date date = new Date();
/* 530:    */       
/* 531:537 */       GenesisFX.movieStartTime = date.getTime();
/* 532:    */       
/* 533:539 */       Mark.say(new Object[] {"Specified start time", date });
/* 534:    */       
/* 535:541 */       BerylControls.reset();
/* 536:    */       
/* 537:543 */       String title = this.videoFile.getName();
/* 538:    */       
/* 539:    */ 
/* 540:    */ 
/* 541:    */ 
/* 542:    */ 
/* 543:    */ 
/* 544:    */ 
/* 545:    */ 
/* 546:    */ 
/* 547:    */ 
/* 548:    */ 
/* 549:    */ 
/* 550:    */ 
/* 551:    */ 
/* 552:558 */       Mark.say(new Object[] {"Doing movie " + title });
/* 553:    */       
/* 554:560 */       GenesisFX.this.getWiredBoxB().doMovie(title);
/* 555:    */       try
/* 556:    */       {
/* 557:565 */         String url = this.videoFile.toURI().toURL().toExternalForm();
/* 558:566 */         GenesisFX.this.getFxMediaViewer().playMedia(url);
/* 559:567 */         int pause = 500;
/* 560:568 */         Thread.sleep(pause);
/* 561:569 */         GenesisFX.this.getFxMediaViewer().pauseMedia();
/* 562:570 */         Mark.say(new Object[] {"Paused", Double.valueOf(pause / 1000.0D), "sec" });
/* 563:    */       }
/* 564:    */       catch (MalformedURLException e)
/* 565:    */       {
/* 566:573 */         Mark.err(new Object[] {"Unable to create url" });
/* 567:574 */         e.printStackTrace();
/* 568:    */       }
/* 569:    */       catch (Exception e)
/* 570:    */       {
/* 571:577 */         Mark.err(new Object[] {"Strange exception in GenesisFX.AnnounceRealtimeEvents.run" });
/* 572:    */       }
/* 573:    */     }
/* 574:    */   }
/* 575:    */   
/* 576:    */   private PauseTimer getPauseTimer()
/* 577:    */   {
/* 578:586 */     if (this.pauseTimer == null) {
/* 579:587 */       this.pauseTimer = new PauseTimer();
/* 580:    */     }
/* 581:589 */     return this.pauseTimer;
/* 582:    */   }
/* 583:    */   
/* 584:    */   public class PauseTimer
/* 585:    */     extends AbstractWiredBox
/* 586:    */   {
/* 587:    */     int frame;
/* 588:    */     
/* 589:    */     public PauseTimer()
/* 590:    */     {
/* 591:597 */       Connections.getPorts(this).addSignalProcessor("process");
/* 592:    */     }
/* 593:    */     
/* 594:    */     public void playUntil(int frame)
/* 595:    */     {
/* 596:601 */       this.frame = frame;
/* 597:602 */       GenesisFX.this.getFxMediaViewer().play();
/* 598:    */     }
/* 599:    */     
/* 600:    */     public void process(Object object)
/* 601:    */     {
/* 602:607 */       if ((object instanceof Double))
/* 603:    */       {
/* 604:608 */         Double time = (Double)object;
/* 605:609 */         int currentFrame = (int)(time.doubleValue() * 30.0D / 1000.0D);
/* 606:610 */         if (currentFrame > this.frame) {
/* 607:611 */           GenesisFX.this.getFxMediaViewer().pauseMedia();
/* 608:    */         }
/* 609:    */       }
/* 610:    */     }
/* 611:    */   }
/* 612:    */   
/* 613:    */   public void startInFrame()
/* 614:    */   {
/* 615:621 */     start();
/* 616:622 */     JFrame frame = new JFrame();
/* 617:623 */     frame.setTitle("Genesis");
/* 618:624 */     frame.getContentPane().setBackground(Color.WHITE);
/* 619:625 */     frame.getContentPane().setLayout(new BorderLayout());
/* 620:626 */     frame.getContentPane().add(this);
/* 621:    */     
/* 622:628 */     frame.setBounds(0, 0, 1024, 768);
/* 623:629 */     frame.addWindowListener(this);
/* 624:630 */     frame.setDefaultCloseOperation(3);
/* 625:    */     
/* 626:632 */     frame.setVisible(true);
/* 627:633 */     Mark.say(new Object[] {"Solo initialized" });
/* 628:634 */     GenesisControls.makeSmallVideoRecordingButton.addActionListener(new Genesis.MyActionListener(this, frame));
/* 629:635 */     GenesisControls.makeLargeVideoRecordingButton.addActionListener(new Genesis.MyActionListener(this, frame));
/* 630:    */   }
/* 631:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     obsolete.mediaFX.GenesisFX
 * JD-Core Version:    0.7.0.1
 */