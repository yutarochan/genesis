/*   1:    */ package translator;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Sequence;
/*   5:    */ import com.ascent.gui.frame.WFrameApplication;
/*   6:    */ import connections.Connections;
/*   7:    */ import connections.Ports;
/*   8:    */ import connections.WiredBox;
/*   9:    */ import gui.NewFrameViewer;
/*  10:    */ import java.awt.BorderLayout;
/*  11:    */ import java.awt.Color;
/*  12:    */ import java.awt.Dimension;
/*  13:    */ import java.awt.Font;
/*  14:    */ import java.awt.GridLayout;
/*  15:    */ import java.awt.event.ActionEvent;
/*  16:    */ import java.awt.event.ActionListener;
/*  17:    */ import java.awt.event.KeyEvent;
/*  18:    */ import java.awt.event.KeyListener;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.Vector;
/*  21:    */ import java.util.prefs.Preferences;
/*  22:    */ import javax.swing.JButton;
/*  23:    */ import javax.swing.JCheckBox;
/*  24:    */ import javax.swing.JLabel;
/*  25:    */ import javax.swing.JPanel;
/*  26:    */ import links.words.BundleGenerator;
/*  27:    */ import parameters.Switch;
/*  28:    */ import persistence.JCheckBoxWithMemory;
/*  29:    */ import start.Generator;
/*  30:    */ import start.RoleFrame;
/*  31:    */ import start.Start;
/*  32:    */ import utils.Mark;
/*  33:    */ import utils.WiredTextArea;
/*  34:    */ import utils.WiredTextField;
/*  35:    */ 
/*  36:    */ public class TranslatorGeneratorTestApplication
/*  37:    */   extends WFrameApplication
/*  38:    */   implements WiredBox
/*  39:    */ {
/*  40:    */   private JPanel view;
/*  41:    */   private JPanel buttonsAndCheckBoxes;
/*  42:    */   private JPanel buttons;
/*  43:    */   private JPanel checkBoxes;
/*  44:    */   private JPanel mainPanel;
/*  45:    */   private JPanel viewerPanel;
/*  46:    */   private NewFrameViewer viewer;
/*  47:    */   private WiredTextArea fromStartDisplay;
/*  48:    */   private WiredTextArea toStartDisplay;
/*  49:    */   private WiredTextField wiredTextInputField;
/*  50:    */   private WiredTextField wiredTextDireclyBackField;
/*  51:    */   private WiredTextField wiredTextOutputField;
/*  52:    */   private JPanel textFields;
/*  53: 51 */   private final JButton stepButton = new JButton("Step forward");
/*  54: 53 */   private final JButton retreatButton = new JButton("Retreat");
/*  55: 55 */   private final JButton runButton = new JButton("Run");
/*  56: 57 */   private final JButton processStartTripplesButton = new JButton("Process");
/*  57: 59 */   private final JButton processGenesisTripplesButton = new JButton("Process");
/*  58: 61 */   private final JCheckBoxWithMemory autoRunButton = new JCheckBoxWithMemory("Auto run");
/*  59: 63 */   private int textFieldSize = 20;
/*  60: 65 */   private int textFieldWeight = 1;
/*  61: 67 */   private int textAreaSize = 20;
/*  62: 69 */   private int textAreaWeight = 0;
/*  63:    */   public static final String FROM_TRANSLATOR = "from translator";
/*  64:    */   public static final String TRIPLES_FROM_START = "triples from start";
/*  65:    */   public static final String EDITED_PARSE = "edited parse";
/*  66:    */   public static final String EDITED_TRIPLES = "edited triples";
/*  67:    */   public static final String INPUT = "input";
/*  68:    */   private Sequence sequenceFromStart;
/*  69:    */   
/*  70:    */   public TranslatorGeneratorTestApplication()
/*  71:    */   {
/*  72: 84 */     ButtonActionListener l = new ButtonActionListener(null);
/*  73: 85 */     this.stepButton.addActionListener(l);
/*  74: 86 */     this.retreatButton.addActionListener(l);
/*  75: 87 */     this.runButton.addActionListener(l);
/*  76: 88 */     this.processStartTripplesButton.addActionListener(l);
/*  77: 89 */     this.processGenesisTripplesButton.addActionListener(l);
/*  78:    */     
/*  79: 91 */     getWiredTextInputField().addKeyListener(new PunctuationListener());
/*  80:    */     
/*  81: 93 */     getView().add(getTextFields(), "North");
/*  82:    */     
/*  83: 95 */     getWiredTextInputField().requestFocusInWindow();
/*  84:    */     
/*  85: 97 */     Connections.wire(getWiredTextInputField(), "input", this);
/*  86:    */     
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:107 */     Connections.wire(Translator.PROGRESS, Translator.getTranslator(), getViewer());
/*  96:    */     
/*  97:    */ 
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:117 */     Connections.wire("debugging result", Translator.getTranslator(), "from translator", this);
/* 106:    */     
/* 107:119 */     Connections.getPorts(this).addSignalProcessor("from translator", "processTranslatorInput");
/* 108:    */     
/* 109:121 */     Connections.getPorts(this).addSignalProcessor("input", "processInput");
/* 110:    */     
/* 111:123 */     String test = Preferences.userRoot().get(getClass().getName(), "testSentence");
/* 112:    */     
/* 113:125 */     getWiredTextInputField().setText(test);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void processInput(Object o)
/* 117:    */   {
/* 118:130 */     if ((o instanceof String))
/* 119:    */     {
/* 120:131 */       String sentence = (String)o;
/* 121:    */       
/* 122:    */ 
/* 123:134 */       this.sequenceFromStart = Start.getStart().processForTestor(sentence);
/* 124:    */       
/* 125:    */ 
/* 126:    */ 
/* 127:138 */       String rawText = Start.getStart().getProcessedSentence();
/* 128:    */       
/* 129:    */ 
/* 130:    */ 
/* 131:142 */       String triples = extractTriples(rawText);
/* 132:    */       
/* 133:    */ 
/* 134:    */ 
/* 135:146 */       getFromStartDisplay().setText(extractTriples(rawText));
/* 136:    */       
/* 137:148 */       getWiredTextDireclyBackField().setText(Start.getStart().generate(triples));
/* 138:    */       
/* 139:150 */       Translator.getTranslator().setInput(this.sequenceFromStart);
/* 140:152 */       if (this.autoRunButton.isSelected()) {
/* 141:153 */         Translator.getTranslator().go();
/* 142:    */       } else {
/* 143:156 */         Translator.getTranslator().step();
/* 144:    */       }
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void processEditedTriplesFromStart()
/* 149:    */   {
/* 150:164 */     String x = getFromStartDisplay().getText();
/* 151:    */     
/* 152:166 */     Mark.say(new Object[] {"Edited Start triples:", x });
/* 153:    */     
/* 154:168 */     getWiredTextDireclyBackField().setText(Start.getStart().generate(x));
/* 155:    */     
/* 156:170 */     Sequence sequenceFromTriples = Start.getStart().processTripples(x);
/* 157:    */     
/* 158:172 */     Mark.say(new Object[] {"!!!!!!!!!!!!!!!!!", Integer.valueOf(sequenceFromTriples.getElements().size()) });
/* 159:173 */     Translator.getTranslator().setInput(sequenceFromTriples);
/* 160:174 */     if (this.autoRunButton.isSelected()) {
/* 161:175 */       Translator.getTranslator().go();
/* 162:    */     } else {
/* 163:178 */       Translator.getTranslator().step();
/* 164:    */     }
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void processEditedTriplesFromGenesis()
/* 168:    */   {
/* 169:184 */     String x = getToStartDisplay().getText();
/* 170:185 */     Mark.say(new Object[] {"Edited Genesis triples:", x });
/* 171:186 */     String sentence = Start.getStart().generate(x);
/* 172:187 */     Mark.say(new Object[] {"Sentence:", sentence });
/* 173:188 */     getWiredTextOutputField().setText(sentence);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String extractTriples(String s)
/* 177:    */   {
/* 178:192 */     StringBuffer input = new StringBuffer(s);
/* 179:193 */     StringBuffer result = new StringBuffer();
/* 180:    */     int start;
/* 181:195 */     while ((start = input.indexOf("[")) >= 0)
/* 182:    */     {
/* 183:    */       int start;
/* 184:196 */       int end = input.indexOf("]", start);
/* 185:197 */       result.append(input.substring(start, end + 1) + "\n");
/* 186:198 */       input.delete(0, end + 1);
/* 187:    */     }
/* 188:200 */     return result.toString();
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void processTriplesFromStart(Object o)
/* 192:    */   {
/* 193:204 */     String triplesAsString = "";
/* 194:205 */     if ((o instanceof String))
/* 195:    */     {
/* 196:206 */       triplesAsString = o.toString();
/* 197:207 */       String x = Start.getStart().generate(triplesAsString);
/* 198:208 */       getWiredTextDireclyBackField().setText(x);
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void processTranslatorInput(Object o)
/* 203:    */   {
/* 204:213 */     if ((o instanceof Sequence))
/* 205:    */     {
/* 206:214 */       Sequence s = (Sequence)o;
/* 207:215 */       String text = "";
/* 208:216 */       getToStartDisplay().setText("");
/* 209:217 */       for (Entity t : s.getElements())
/* 210:    */       {
/* 211:219 */         RoleFrame roleFrame = Generator.getGenerator().generateFromThing(t);
/* 212:220 */         if (roleFrame != null)
/* 213:    */         {
/* 214:221 */           String triples = roleFrame.getRendering();
/* 215:222 */           getToStartDisplay().setText(getToStartDisplay().getText() + enumerate(triples));
/* 216:223 */           text = text + Generator.getGenerator().generate(roleFrame) + "  ";
/* 217:224 */           getWiredTextOutputField().setText(text);
/* 218:    */         }
/* 219:    */         else
/* 220:    */         {
/* 221:228 */           text = text + "Unable to generate text.";
/* 222:    */         }
/* 223:    */       }
/* 224:231 */       getWiredTextOutputField().setText(text);
/* 225:    */     }
/* 226:    */   }
/* 227:    */   
/* 228:    */   private String enumerate(String triples)
/* 229:    */   {
/* 230:237 */     StringBuffer buffer = new StringBuffer(triples);
/* 231:    */     int index;
/* 232:239 */     while ((index = buffer.indexOf("][")) > 0)
/* 233:    */     {
/* 234:    */       int index;
/* 235:240 */       buffer.replace(index, index + 2, "]\n[");
/* 236:    */     }
/* 237:242 */     buffer.append("\n\n");
/* 238:243 */     return buffer.toString();
/* 239:    */   }
/* 240:    */   
/* 241:    */   public JPanel getButtonsAndCheckBoxes()
/* 242:    */   {
/* 243:247 */     if (this.buttonsAndCheckBoxes == null)
/* 244:    */     {
/* 245:248 */       this.buttonsAndCheckBoxes = new JPanel();
/* 246:249 */       this.buttonsAndCheckBoxes.setLayout(new GridLayout(0, 1));
/* 247:250 */       this.buttonsAndCheckBoxes.add(getCheckBoxes());
/* 248:251 */       this.buttonsAndCheckBoxes.add(getButtons());
/* 249:    */     }
/* 250:253 */     return this.buttonsAndCheckBoxes;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public JPanel getButtons()
/* 254:    */   {
/* 255:257 */     if (this.buttons == null)
/* 256:    */     {
/* 257:258 */       this.buttons = new JPanel();
/* 258:259 */       this.buttons.setLayout(new GridLayout(1, 0));
/* 259:260 */       this.buttons.add(this.stepButton);
/* 260:261 */       this.buttons.add(this.retreatButton);
/* 261:262 */       this.buttons.add(this.runButton);
/* 262:    */     }
/* 263:264 */     return this.buttons;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public JPanel getCheckBoxes()
/* 267:    */   {
/* 268:268 */     if (this.checkBoxes == null)
/* 269:    */     {
/* 270:269 */       this.checkBoxes = new JPanel();
/* 271:270 */       this.checkBoxes.setLayout(new GridLayout(1, 0));
/* 272:271 */       this.checkBoxes.add(this.autoRunButton);
/* 273:272 */       this.checkBoxes.add(Switch.useStartBeta);
/* 274:    */     }
/* 275:274 */     return this.checkBoxes;
/* 276:    */   }
/* 277:    */   
/* 278:    */   private class ButtonActionListener
/* 279:    */     implements ActionListener
/* 280:    */   {
/* 281:    */     private ButtonActionListener() {}
/* 282:    */     
/* 283:    */     public void actionPerformed(ActionEvent e)
/* 284:    */     {
/* 285:279 */       if (e.getSource() == TranslatorGeneratorTestApplication.this.stepButton)
/* 286:    */       {
/* 287:280 */         int steps = Translator.getTranslator().getTransformations().size();
/* 288:281 */         Translator.getTranslator().step();
/* 289:    */       }
/* 290:285 */       else if (e.getSource() == TranslatorGeneratorTestApplication.this.retreatButton)
/* 291:    */       {
/* 292:286 */         Mark.say(new Object[] {"Retreat" });
/* 293:    */         
/* 294:    */ 
/* 295:289 */         int steps = Translator.getTranslator().getTransformations().size();
/* 296:    */         
/* 297:291 */         TranslatorGeneratorTestApplication.this.sequenceFromStart = Start.getStart().parse(TranslatorGeneratorTestApplication.this.wiredTextDireclyBackField.getText());
/* 298:    */         
/* 299:293 */         Translator.getTranslator().setInput(TranslatorGeneratorTestApplication.this.sequenceFromStart);
/* 300:    */         
/* 301:295 */         Mark.say(new Object[] {"Steps", Integer.valueOf(steps) });
/* 302:296 */         steps--;
/* 303:297 */         steps--;
/* 304:300 */         for (int i = 0; i < steps; i++) {
/* 305:301 */           Translator.getTranslator().step();
/* 306:    */         }
/* 307:    */       }
/* 308:304 */       else if (e.getSource() == TranslatorGeneratorTestApplication.this.runButton)
/* 309:    */       {
/* 310:305 */         Translator.getTranslator().go();
/* 311:    */       }
/* 312:307 */       else if (e.getSource() == TranslatorGeneratorTestApplication.this.processStartTripplesButton)
/* 313:    */       {
/* 314:308 */         TranslatorGeneratorTestApplication.this.processEditedTriplesFromStart();
/* 315:    */       }
/* 316:310 */       else if (e.getSource() == TranslatorGeneratorTestApplication.this.processGenesisTripplesButton)
/* 317:    */       {
/* 318:311 */         TranslatorGeneratorTestApplication.this.processEditedTriplesFromGenesis();
/* 319:    */       }
/* 320:    */     }
/* 321:    */     
/* 322:    */     private String removeStars(String sentence)
/* 323:    */     {
/* 324:    */       for (;;)
/* 325:    */       {
/* 326:317 */         int l = sentence.length();
/* 327:318 */         char c = sentence.charAt(l - 1);
/* 328:319 */         if ((c != '*') && (c != ' ')) {
/* 329:    */           break;
/* 330:    */         }
/* 331:320 */         sentence = sentence.substring(0, l - 1);
/* 332:    */       }
/* 333:326 */       return sentence;
/* 334:    */     }
/* 335:    */   }
/* 336:    */   
/* 337:    */   protected class PunctuationListener
/* 338:    */     implements KeyListener
/* 339:    */   {
/* 340:332 */     String steppers = "*";
/* 341:334 */     String runners = "!&";
/* 342:336 */     String parsers = ".\n";
/* 343:    */     
/* 344:    */     protected PunctuationListener() {}
/* 345:    */     
/* 346:    */     public void keyTyped(KeyEvent e)
/* 347:    */     {
/* 348:342 */       String sent = null;
/* 349:343 */       char key = e.getKeyChar();
/* 350:344 */       if (this.parsers.indexOf(key) >= 0)
/* 351:    */       {
/* 352:345 */         BundleGenerator.purgeWordnetCache();
/* 353:346 */         String text = TranslatorGeneratorTestApplication.this.getWiredTextInputField().getText();
/* 354:347 */         text = TranslatorGeneratorTestApplication.this.normalizePeriod(text);
/* 355:348 */         TranslatorGeneratorTestApplication.this.getWiredTextInputField().setText(text);
/* 356:    */         
/* 357:350 */         TranslatorGeneratorTestApplication.this.stimulate(text);
/* 358:351 */         if (TranslatorGeneratorTestApplication.this.autoRunButton.isSelected()) {
/* 359:352 */           TranslatorGeneratorTestApplication.this.runButton.doClick();
/* 360:    */         }
/* 361:    */       }
/* 362:355 */       else if (this.runners.indexOf(key) >= 0)
/* 363:    */       {
/* 364:356 */         Translator.getTranslator().go();
/* 365:357 */         e.consume();
/* 366:    */       }
/* 367:359 */       else if (this.steppers.indexOf(key) >= 0)
/* 368:    */       {
/* 369:362 */         if (countSteppers(TranslatorGeneratorTestApplication.this.getWiredTextInputField().getText()) == 0)
/* 370:    */         {
/* 371:363 */           Connections.getPorts(TranslatorGeneratorTestApplication.this.getWiredTextInputField()).transmit(TranslatorGeneratorTestApplication.this.getWiredTextInputField().getText());
/* 372:364 */           Mark.say(new Object[] {"Hello stepper" });
/* 373:    */         }
/* 374:366 */         Translator.getTranslator().step();
/* 375:    */       }
/* 376:    */     }
/* 377:    */     
/* 378:    */     private int countSteppers(String text)
/* 379:    */     {
/* 380:372 */       int count = 0;
/* 381:373 */       for (int s = 0; s < this.steppers.length(); s++)
/* 382:    */       {
/* 383:374 */         char c = this.steppers.charAt(s);
/* 384:375 */         for (int i = 0; i < text.length(); i++) {
/* 385:376 */           if (c == text.charAt(i)) {
/* 386:377 */             count++;
/* 387:    */           }
/* 388:    */         }
/* 389:    */       }
/* 390:381 */       return count;
/* 391:    */     }
/* 392:    */     
/* 393:    */     public void keyPressed(KeyEvent e) {}
/* 394:    */     
/* 395:    */     public void keyReleased(KeyEvent e) {}
/* 396:    */   }
/* 397:    */   
/* 398:    */   public void stimulate(String s)
/* 399:    */   {
/* 400:392 */     Switch.useStartCache.setSelected(false);
/* 401:393 */     Preferences.userRoot().put(getClass().getName(), s);
/* 402:    */   }
/* 403:    */   
/* 404:    */   public JPanel getView()
/* 405:    */   {
/* 406:397 */     if (this.view == null)
/* 407:    */     {
/* 408:398 */       this.view = new JPanel();
/* 409:399 */       this.view.setLayout(new BorderLayout());
/* 410:400 */       this.view.add(getMainPanel(), "Center");
/* 411:    */     }
/* 412:402 */     return this.view;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public JPanel getMainPanel()
/* 416:    */   {
/* 417:406 */     if (this.mainPanel == null)
/* 418:    */     {
/* 419:407 */       this.mainPanel = new JPanel();
/* 420:408 */       this.mainPanel.setLayout(new GridLayout(1, 0));
/* 421:    */       
/* 422:410 */       JPanel left = new JPanel();
/* 423:411 */       left.setLayout(new BorderLayout());
/* 424:412 */       left.add(getFromStartDisplay(), "Center");
/* 425:413 */       left.add(this.processStartTripplesButton, "South");
/* 426:    */       
/* 427:415 */       JPanel right = new JPanel();
/* 428:416 */       right.setLayout(new BorderLayout());
/* 429:417 */       right.add(getToStartDisplay(), "Center");
/* 430:418 */       right.add(this.processGenesisTripplesButton, "South");
/* 431:    */       
/* 432:420 */       this.mainPanel.add(left);
/* 433:421 */       this.mainPanel.add(getViewerPanel());
/* 434:422 */       this.mainPanel.add(right);
/* 435:    */     }
/* 436:424 */     return this.mainPanel;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public WiredTextArea getFromStartDisplay()
/* 440:    */   {
/* 441:428 */     if (this.fromStartDisplay == null)
/* 442:    */     {
/* 443:429 */       this.fromStartDisplay = new WiredTextArea();
/* 444:430 */       this.fromStartDisplay.setFont(new Font("Dialog", this.textAreaWeight, this.textAreaSize));
/* 445:    */     }
/* 446:432 */     return this.fromStartDisplay;
/* 447:    */   }
/* 448:    */   
/* 449:    */   public WiredTextArea getToStartDisplay()
/* 450:    */   {
/* 451:436 */     if (this.toStartDisplay == null)
/* 452:    */     {
/* 453:437 */       this.toStartDisplay = new WiredTextArea();
/* 454:438 */       this.toStartDisplay.setFont(new Font("Dialog", this.textAreaWeight, this.textAreaSize));
/* 455:    */     }
/* 456:440 */     return this.toStartDisplay;
/* 457:    */   }
/* 458:    */   
/* 459:    */   public NewFrameViewer getViewer()
/* 460:    */   {
/* 461:444 */     if (this.viewer == null)
/* 462:    */     {
/* 463:445 */       this.viewer = new NewFrameViewer();
/* 464:446 */       this.viewer.setPreferredSize(new Dimension(1000, 1000));
/* 465:    */     }
/* 466:448 */     return this.viewer;
/* 467:    */   }
/* 468:    */   
/* 469:    */   public JPanel getTextFields()
/* 470:    */   {
/* 471:452 */     if (this.textFields == null)
/* 472:    */     {
/* 473:454 */       JLabel input = new JLabel("Input:          ");
/* 474:455 */       JLabel direct = new JLabel("Short circuit:  ");
/* 475:456 */       JLabel output = new JLabel("Via Genesis:    ");
/* 476:    */       
/* 477:458 */       this.textFields = new JPanel();
/* 478:459 */       this.textFields.setLayout(new GridLayout(0, 1));
/* 479:460 */       JPanel top = new JPanel();
/* 480:461 */       top.setLayout(new BorderLayout());
/* 481:    */       
/* 482:463 */       input.setOpaque(true);
/* 483:464 */       input.setBackground(Color.WHITE);
/* 484:465 */       input.setFont(new Font("Courier", this.textFieldWeight, this.textFieldSize));
/* 485:466 */       top.add(input, "West");
/* 486:467 */       top.add(getWiredTextInputField(), "Center");
/* 487:468 */       this.textFields.add(top);
/* 488:    */       
/* 489:470 */       JPanel middle = new JPanel();
/* 490:471 */       middle.setLayout(new BorderLayout());
/* 491:    */       
/* 492:473 */       direct.setOpaque(true);
/* 493:474 */       direct.setBackground(Color.WHITE);
/* 494:475 */       direct.setFont(new Font("Courier", this.textFieldWeight, this.textFieldSize));
/* 495:476 */       middle.add(direct, "West");
/* 496:477 */       middle.add(getWiredTextDireclyBackField(), "Center");
/* 497:478 */       this.textFields.add(middle);
/* 498:    */       
/* 499:480 */       JPanel bottom = new JPanel();
/* 500:481 */       bottom.setLayout(new BorderLayout());
/* 501:    */       
/* 502:483 */       output.setOpaque(true);
/* 503:484 */       output.setBackground(Color.WHITE);
/* 504:485 */       output.setFont(new Font("Courier", this.textFieldWeight, this.textFieldSize));
/* 505:486 */       bottom.add(output, "West");
/* 506:487 */       bottom.add(getWiredTextOutputField(), "Center");
/* 507:488 */       this.textFields.add(bottom);
/* 508:    */     }
/* 509:490 */     return this.textFields;
/* 510:    */   }
/* 511:    */   
/* 512:    */   public WiredTextField getWiredTextInputField()
/* 513:    */   {
/* 514:494 */     if (this.wiredTextInputField == null)
/* 515:    */     {
/* 516:495 */       this.wiredTextInputField = new WiredTextField();
/* 517:496 */       this.wiredTextInputField.setFont(new Font("Dialog", this.textFieldWeight, this.textFieldSize));
/* 518:    */     }
/* 519:498 */     return this.wiredTextInputField;
/* 520:    */   }
/* 521:    */   
/* 522:    */   public WiredTextField getWiredTextDireclyBackField()
/* 523:    */   {
/* 524:502 */     if (this.wiredTextDireclyBackField == null)
/* 525:    */     {
/* 526:503 */       this.wiredTextDireclyBackField = new WiredTextField();
/* 527:504 */       this.wiredTextDireclyBackField.setFont(new Font("Dialog", this.textFieldWeight, this.textFieldSize));
/* 528:    */     }
/* 529:506 */     return this.wiredTextDireclyBackField;
/* 530:    */   }
/* 531:    */   
/* 532:    */   public WiredTextField getWiredTextOutputField()
/* 533:    */   {
/* 534:510 */     if (this.wiredTextOutputField == null)
/* 535:    */     {
/* 536:511 */       this.wiredTextOutputField = new WiredTextField();
/* 537:512 */       this.wiredTextOutputField.setFont(new Font("Dialog", this.textFieldWeight, this.textFieldSize));
/* 538:    */     }
/* 539:514 */     return this.wiredTextOutputField;
/* 540:    */   }
/* 541:    */   
/* 542:    */   public JPanel getViewerPanel()
/* 543:    */   {
/* 544:518 */     if (this.viewerPanel == null)
/* 545:    */     {
/* 546:519 */       this.viewerPanel = new JPanel();
/* 547:520 */       this.viewerPanel.setLayout(new BorderLayout());
/* 548:521 */       this.viewerPanel.add(getViewer(), "Center");
/* 549:522 */       this.viewerPanel.add(getButtonsAndCheckBoxes(), "South");
/* 550:    */     }
/* 551:524 */     return this.viewerPanel;
/* 552:    */   }
/* 553:    */   
/* 554:    */   public String getAccessType()
/* 555:    */   {
/* 556:529 */     return null;
/* 557:    */   }
/* 558:    */   
/* 559:    */   public void restoreTaskBarImage() {}
/* 560:    */   
/* 561:    */   public void restoreTaskBarTitle() {}
/* 562:    */   
/* 563:    */   public String getNavigationBarItem()
/* 564:    */   {
/* 565:544 */     return "Test translator/generator";
/* 566:    */   }
/* 567:    */   
/* 568:    */   public String getNavigationBarItemHelp()
/* 569:    */   {
/* 570:550 */     return "Test Start parser, Genesis translator, Genesis generator, Start generator";
/* 571:    */   }
/* 572:    */   
/* 573:    */   public String getName()
/* 574:    */   {
/* 575:555 */     return "Parser/Generator test";
/* 576:    */   }
/* 577:    */   
/* 578:    */   private String normalizePeriod(String text)
/* 579:    */   {
/* 580:559 */     if (text.isEmpty()) {
/* 581:560 */       return text;
/* 582:    */     }
/* 583:562 */     while (text.lastIndexOf('.') == text.length() - 1) {
/* 584:563 */       text = text.substring(0, text.length() - 1);
/* 585:    */     }
/* 586:565 */     return text;
/* 587:    */   }
/* 588:    */   
/* 589:    */   public static void main(String[] args)
/* 590:    */   {
/* 591:576 */     Mark.say(
/* 592:577 */       new Object[] { "Normalized", new TranslatorGeneratorTestApplication().normalizePeriod("Hello world") });
/* 593:    */   }
/* 594:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     translator.TranslatorGeneratorTestApplication
 * JD-Core Version:    0.7.0.1
 */