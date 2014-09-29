/*   1:    */ package matthewFay.Depricated;
/*   2:    */ 
/*   3:    */ import ati.ParallelJPanel;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Relation;
/*   6:    */ import bridge.reps.entities.Sequence;
/*   7:    */ import connections.AbstractWiredBox;
/*   8:    */ import connections.Connections;
/*   9:    */ import connections.Ports;
/*  10:    */ import genesis.GenesisControls;
/*  11:    */ import java.awt.event.ActionEvent;
/*  12:    */ import java.awt.event.ActionListener;
/*  13:    */ import javax.swing.JButton;
/*  14:    */ import javax.swing.JCheckBoxMenuItem;
/*  15:    */ import javax.swing.JComboBox;
/*  16:    */ import javax.swing.JLabel;
/*  17:    */ import javax.swing.JPanel;
/*  18:    */ import javax.swing.JTabbedPane;
/*  19:    */ import utils.Mark;
/*  20:    */ 
/*  21:    */ @Deprecated
/*  22:    */ public class TeachingProcessor
/*  23:    */   extends AbstractWiredBox
/*  24:    */   implements ActionListener
/*  25:    */ {
/*  26:    */   public static final String REFLECTION_PORT = "reflection port";
/*  27:    */   public static final String STORY_PORT = "story port";
/*  28:    */   public static final String RULE_PORT = "rule port";
/*  29:    */   public static final String RULE1 = "rule1";
/*  30:    */   public static final String USED_RULES1 = "used rules1";
/*  31:    */   public static final String REFLECTION_PORT2 = "reflection port2";
/*  32:    */   public static final String STORY_PORT2 = "story port2";
/*  33:    */   public static final String RULE_PORT2 = "rule port2";
/*  34:    */   public static final String RULE2 = "rule2";
/*  35:    */   public static final String USED_RULES2 = "used rules2";
/*  36:    */   public static final String STAGE_DIRECTION = "stage direction";
/*  37:    */   public static final String STAGE_DIRECTION2 = "stage direction 2";
/*  38: 38 */   private Sequence teacherRules = new Sequence();
/*  39: 39 */   private Sequence teacherReflectiveKnowledge = new Sequence();
/*  40: 40 */   private Sequence teacherStory = new Sequence();
/*  41: 41 */   private Sequence teacherUsedRules = new Sequence();
/*  42: 43 */   private Sequence studentRules = new Sequence();
/*  43: 44 */   private Sequence studentReflectiveKnowledge = new Sequence();
/*  44: 45 */   private Sequence studentStory = new Sequence();
/*  45: 46 */   private Sequence studentUsedRules = new Sequence();
/*  46: 48 */   private boolean preserveKnowledge = true;
/*  47: 50 */   private boolean taughtCommonSenseKnowledge = false;
/*  48: 51 */   private boolean taughtReflexiveKnowledge = false;
/*  49: 52 */   private boolean taughtCommonSenseKnowledge2 = false;
/*  50: 53 */   private boolean taughtReflexiveKnowledge2 = false;
/*  51:    */   
/*  52:    */   public TeachingProcessor()
/*  53:    */   {
/*  54: 67 */     setName("Teaching Processor");
/*  55:    */     
/*  56: 69 */     Connections.getPorts(this).addSignalProcessor("story port", "processStory");
/*  57: 70 */     Connections.getPorts(this).addSignalProcessor("reflection port", "processReflections");
/*  58: 71 */     Connections.getPorts(this).addSignalProcessor("rule port", "processRules");
/*  59: 72 */     Connections.getPorts(this).addSignalProcessor("used rules1", "processUsedRules");
/*  60:    */     
/*  61:    */ 
/*  62: 75 */     Connections.getPorts(this).addSignalProcessor("story port2", "processStory2");
/*  63: 76 */     Connections.getPorts(this).addSignalProcessor("reflection port2", "processReflections2");
/*  64: 77 */     Connections.getPorts(this).addSignalProcessor("rule port2", "processRules2");
/*  65: 78 */     Connections.getPorts(this).addSignalProcessor("used rules2", "processUsedRules2");
/*  66:    */     
/*  67: 80 */     Connections.getPorts(this).addSignalProcessor("stage direction", "processDirection");
/*  68: 81 */     Connections.getPorts(this).addSignalProcessor("stage direction 2", "processDirection2");
/*  69:    */     
/*  70: 83 */     LocalGenesis.localGenesis().getControls().addTab("Teaching", getTeachingControls());
/*  71:    */   }
/*  72:    */   
/*  73: 86 */   private static ParallelJPanel teachingControls = null;
/*  74:    */   
/*  75:    */   public JPanel getTeachingControls()
/*  76:    */   {
/*  77: 90 */     if (teachingControls == null)
/*  78:    */     {
/*  79: 91 */       teachingControls = new ParallelJPanel();
/*  80:    */       
/*  81: 93 */       String[] options = { "Teach Everything", "Teach All Unknown Knowledge*", "Teach all relevant knowledge", "Teach only new relevant knowledge*" };
/*  82: 94 */       LocalGenesis.teachingLevel = new JComboBox(options);
/*  83: 95 */       teachingControls.addLeft(LocalGenesis.teachingLevel);
/*  84: 96 */       teachingControls.addLeft(new JLabel("* Teacher requires information about student knowledge."));
/*  85: 97 */       LocalGenesis.collaboration = new JCheckBoxMenuItem("Enable Collaboration");
/*  86: 98 */       teachingControls.addLeft(LocalGenesis.collaboration);
/*  87: 99 */       JButton startTeaching = new JButton("Begin Teaching");
/*  88:100 */       startTeaching.setActionCommand("teaching mode");
/*  89:101 */       startTeaching.addActionListener(this);
/*  90:102 */       teachingControls.addCenter(startTeaching);
/*  91:103 */       JButton stopTeaching = new JButton("Stop Teaching");
/*  92:104 */       stopTeaching.setActionCommand("preparation mode");
/*  93:105 */       stopTeaching.addActionListener(this);
/*  94:106 */       teachingControls.addCenter(stopTeaching);
/*  95:    */     }
/*  96:108 */     return teachingControls;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void actionPerformed(ActionEvent e)
/* 100:    */   {
/* 101:113 */     if (e.getActionCommand().equals("teaching mode"))
/* 102:    */     {
/* 103:116 */       Mark.say(new Object[] {"Beginning New Teaching Pass" });
/* 104:117 */       if (LocalGenesis.teachingLevel.getSelectedIndex() == 0) {
/* 105:119 */         for (int i = 0; i < this.teacherRules.getNumberOfChildren(); i++)
/* 106:    */         {
/* 107:120 */           Relation ruleToSend = (Relation)this.teacherRules.getElement(i);
/* 108:121 */           Mark.say(new Object[] {"Teaching student: ", ruleToSend.asString() });
/* 109:    */         }
/* 110:    */       }
/* 111:125 */       if (LocalGenesis.teachingLevel.getSelectedIndex() == 1)
/* 112:    */       {
/* 113:126 */         boolean newKnowledge = true;
/* 114:128 */         for (int i = 0; i < this.teacherRules.getNumberOfChildren(); i++)
/* 115:    */         {
/* 116:129 */           newKnowledge = true;
/* 117:130 */           Relation ruleToSend = (Relation)this.teacherRules.getElement(i);
/* 118:131 */           for (int j = 0; j < this.studentRules.getNumberOfChildren(); j++) {
/* 119:132 */             if (ruleToSend.isEqual(this.studentRules.getElement(j)))
/* 120:    */             {
/* 121:133 */               newKnowledge = false;
/* 122:134 */               break;
/* 123:    */             }
/* 124:    */           }
/* 125:137 */           if (newKnowledge) {
/* 126:139 */             Mark.say(new Object[] {"Teaching student: ", ruleToSend.asString() });
/* 127:    */           }
/* 128:    */         }
/* 129:    */       }
/* 130:144 */       if (LocalGenesis.teachingLevel.getSelectedIndex() == 2) {
/* 131:146 */         for (int i = 0; i < this.teacherUsedRules.getNumberOfChildren(); i++)
/* 132:    */         {
/* 133:147 */           Relation ruleToSend = (Relation)this.teacherUsedRules.getElement(i);
/* 134:148 */           Mark.say(new Object[] {"Teaching student: ", ruleToSend.asString() });
/* 135:    */         }
/* 136:    */       }
/* 137:152 */       if (LocalGenesis.teachingLevel.getSelectedIndex() == 3)
/* 138:    */       {
/* 139:153 */         boolean newKnowledge = true;
/* 140:155 */         for (int i = 0; i < this.teacherUsedRules.getNumberOfChildren(); i++)
/* 141:    */         {
/* 142:156 */           newKnowledge = true;
/* 143:157 */           Relation ruleToSend = (Relation)this.teacherUsedRules.getElement(i);
/* 144:158 */           for (int j = 0; j < this.studentUsedRules.getNumberOfChildren(); j++) {
/* 145:159 */             if (ruleToSend.isEqual(this.studentUsedRules.getElement(j)))
/* 146:    */             {
/* 147:160 */               newKnowledge = false;
/* 148:161 */               break;
/* 149:    */             }
/* 150:    */           }
/* 151:164 */           if (newKnowledge) {
/* 152:166 */             Mark.say(new Object[] {"Teaching student: ", ruleToSend.asString() });
/* 153:    */           }
/* 154:    */         }
/* 155:    */       }
/* 156:    */     }
/* 157:229 */     e.getActionCommand().equals("preparation mode");
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void processUsedRules(Object signal)
/* 161:    */   {
/* 162:235 */     if ((signal instanceof Entity))
/* 163:    */     {
/* 164:236 */       Entity rule = (Entity)signal;
/* 165:237 */       boolean newRule = true;
/* 166:238 */       for (int j = 0; j < this.studentUsedRules.getNumberOfChildren(); j++) {
/* 167:239 */         if (this.studentUsedRules.getElement(j).isEqual(rule)) {
/* 168:240 */           newRule = false;
/* 169:    */         }
/* 170:    */       }
/* 171:242 */       if (newRule) {
/* 172:243 */         this.studentUsedRules.addElement(rule);
/* 173:    */       }
/* 174:    */     }
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void processUsedRules2(Object signal)
/* 178:    */   {
/* 179:250 */     if ((signal instanceof Entity))
/* 180:    */     {
/* 181:251 */       Entity rule = (Entity)signal;
/* 182:252 */       boolean newRule = true;
/* 183:253 */       for (int j = 0; j < this.teacherUsedRules.getNumberOfChildren(); j++) {
/* 184:254 */         if (this.teacherUsedRules.getElement(j).isEqual(rule)) {
/* 185:255 */           newRule = false;
/* 186:    */         }
/* 187:    */       }
/* 188:257 */       if (newRule) {
/* 189:258 */         this.teacherUsedRules.addElement(rule);
/* 190:    */       }
/* 191:    */     }
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void processDirection(Object o)
/* 195:    */   {
/* 196:265 */     if (o == "reset")
/* 197:    */     {
/* 198:266 */       Mark.say(new Object[] {"processDirection received direction", o });
/* 199:267 */       Mark.say(new Object[] {"reset" });
/* 200:270 */       if (!this.preserveKnowledge)
/* 201:    */       {
/* 202:272 */         this.teacherRules = new Sequence();
/* 203:273 */         this.studentRules = new Sequence();
/* 204:    */         
/* 205:275 */         this.teacherReflectiveKnowledge = new Sequence();
/* 206:276 */         this.studentReflectiveKnowledge = new Sequence();
/* 207:    */         
/* 208:278 */         this.teacherStory = new Sequence();
/* 209:279 */         this.studentStory = new Sequence();
/* 210:    */         
/* 211:281 */         this.teacherUsedRules = new Sequence();
/* 212:282 */         this.studentUsedRules = new Sequence();
/* 213:    */         
/* 214:284 */         this.taughtCommonSenseKnowledge = false;
/* 215:285 */         this.taughtReflexiveKnowledge = false;
/* 216:    */         
/* 217:287 */         this.taughtCommonSenseKnowledge2 = false;
/* 218:288 */         this.taughtReflexiveKnowledge2 = false;
/* 219:    */       }
/* 220:    */     }
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void processDirection2(Object o)
/* 224:    */   {
/* 225:294 */     if (o == "reset") {
/* 226:295 */       Mark.say(new Object[] {"processDirection2 received direction", o });
/* 227:    */     }
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void processStory(Object signal)
/* 231:    */   {
/* 232:300 */     if ((signal instanceof Entity))
/* 233:    */     {
/* 234:301 */       Entity t = (Entity)signal;
/* 235:303 */       if ((signal instanceof Sequence))
/* 236:    */       {
/* 237:304 */         Sequence seq = (Sequence)signal;
/* 238:305 */         this.studentStory.addAll(seq);
/* 239:    */       }
/* 240:    */     }
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void processReflections(Object signal)
/* 244:    */   {
/* 245:311 */     if ((signal instanceof Entity))
/* 246:    */     {
/* 247:312 */       Entity t = (Entity)signal;
/* 248:314 */       if ((signal instanceof Sequence))
/* 249:    */       {
/* 250:315 */         Sequence seq = (Sequence)signal;
/* 251:316 */         this.studentReflectiveKnowledge.addElement(seq.getElement(seq.getNumberOfChildren() - 1));
/* 252:    */       }
/* 253:    */     }
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void processStory2(Object signal)
/* 257:    */   {
/* 258:323 */     if ((signal instanceof Entity))
/* 259:    */     {
/* 260:324 */       Entity t = (Entity)signal;
/* 261:326 */       if ((signal instanceof Sequence))
/* 262:    */       {
/* 263:327 */         Sequence seq = (Sequence)signal;
/* 264:328 */         this.teacherStory.addAll(seq);
/* 265:    */       }
/* 266:    */     }
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void processReflections2(Object signal)
/* 270:    */   {
/* 271:334 */     if ((signal instanceof Entity))
/* 272:    */     {
/* 273:335 */       Entity t = (Entity)signal;
/* 274:337 */       if ((signal instanceof Sequence))
/* 275:    */       {
/* 276:338 */         Sequence seq = (Sequence)signal;
/* 277:339 */         this.teacherReflectiveKnowledge.addElement(seq.getElement(seq.getNumberOfChildren() - 1));
/* 278:    */       }
/* 279:    */     }
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void processRules(Object signal)
/* 283:    */   {
/* 284:345 */     if ((signal instanceof Entity))
/* 285:    */     {
/* 286:346 */       Entity t = (Entity)signal;
/* 287:348 */       if ((signal instanceof Sequence))
/* 288:    */       {
/* 289:349 */         Sequence rules = (Sequence)signal;
/* 290:350 */         Relation newRule = (Relation)rules.getElement(rules.getNumberOfChildren() - 1);
/* 291:351 */         if (!this.studentRules.containsDeprecated(newRule)) {
/* 292:352 */           this.studentRules.addElement(rules.getElement(rules.getNumberOfChildren() - 1));
/* 293:    */         }
/* 294:    */       }
/* 295:354 */       if (!this.taughtCommonSenseKnowledge)
/* 296:    */       {
/* 297:355 */         this.taughtCommonSenseKnowledge = true;
/* 298:356 */         if (LocalGenesis.teachingLevel.getSelectedIndex() == 0) {
/* 299:358 */           for (int i = 0; i < this.teacherRules.getNumberOfChildren(); i++)
/* 300:    */           {
/* 301:359 */             Relation ruleToSend = (Relation)this.teacherRules.getElement(i);
/* 302:360 */             Mark.say(new Object[] {"Teaching student: ", ruleToSend.asString() });
/* 303:361 */             Connections.getPorts(this).transmit("rule1", ruleToSend);
/* 304:    */           }
/* 305:    */         }
/* 306:364 */         if (LocalGenesis.teachingLevel.getSelectedIndex() == 1)
/* 307:    */         {
/* 308:365 */           boolean newKnowledge = true;
/* 309:367 */           for (int i = 0; i < this.teacherRules.getNumberOfChildren(); i++)
/* 310:    */           {
/* 311:368 */             newKnowledge = true;
/* 312:369 */             Relation ruleToSend = (Relation)this.teacherRules.getElement(i);
/* 313:370 */             for (int j = 0; j < this.studentRules.getNumberOfChildren(); j++) {
/* 314:371 */               if (ruleToSend.isEqual(this.studentRules.getElement(j)))
/* 315:    */               {
/* 316:372 */                 newKnowledge = false;
/* 317:373 */                 break;
/* 318:    */               }
/* 319:    */             }
/* 320:376 */             if (newKnowledge)
/* 321:    */             {
/* 322:378 */               Mark.say(new Object[] {"Teaching student: ", ruleToSend.asString() });
/* 323:379 */               Connections.getPorts(this).transmit("rule1", ruleToSend);
/* 324:    */             }
/* 325:    */           }
/* 326:    */         }
/* 327:383 */         if (LocalGenesis.teachingLevel.getSelectedIndex() == 2) {
/* 328:385 */           for (int i = 0; i < this.teacherUsedRules.getNumberOfChildren(); i++)
/* 329:    */           {
/* 330:386 */             Relation ruleToSend = (Relation)this.teacherUsedRules.getElement(i);
/* 331:387 */             Mark.say(new Object[] {"Teaching student: ", ruleToSend.asString() });
/* 332:388 */             Connections.getPorts(this).transmit("rule1", ruleToSend);
/* 333:    */           }
/* 334:    */         }
/* 335:391 */         if (LocalGenesis.teachingLevel.getSelectedIndex() == 3)
/* 336:    */         {
/* 337:392 */           boolean newKnowledge = true;
/* 338:394 */           for (int i = 0; i < this.teacherUsedRules.getNumberOfChildren(); i++)
/* 339:    */           {
/* 340:395 */             newKnowledge = true;
/* 341:396 */             Relation ruleToSend = (Relation)this.teacherUsedRules.getElement(i);
/* 342:397 */             for (int j = 0; j < this.studentUsedRules.getNumberOfChildren(); j++) {
/* 343:398 */               if (ruleToSend.isEqual(this.studentUsedRules.getElement(j)))
/* 344:    */               {
/* 345:399 */                 newKnowledge = false;
/* 346:400 */                 break;
/* 347:    */               }
/* 348:    */             }
/* 349:403 */             if (newKnowledge)
/* 350:    */             {
/* 351:405 */               Mark.say(new Object[] {"Teaching student: ", ruleToSend.asString() });
/* 352:406 */               Connections.getPorts(this).transmit("rule1", ruleToSend);
/* 353:    */             }
/* 354:    */           }
/* 355:    */         }
/* 356:    */       }
/* 357:    */     }
/* 358:    */   }
/* 359:    */   
/* 360:    */   public void processRules2(Object signal)
/* 361:    */   {
/* 362:415 */     if ((signal instanceof Entity))
/* 363:    */     {
/* 364:416 */       Entity t = (Entity)signal;
/* 365:418 */       if ((signal instanceof Sequence))
/* 366:    */       {
/* 367:419 */         Sequence rules = (Sequence)signal;
/* 368:420 */         this.teacherRules.addElement(rules.getElement(rules.getNumberOfChildren() - 1));
/* 369:    */       }
/* 370:422 */       if ((GenesisControls.collaboration.getState()) && 
/* 371:423 */         (!this.taughtCommonSenseKnowledge2))
/* 372:    */       {
/* 373:424 */         this.taughtCommonSenseKnowledge2 = true;
/* 374:425 */         if (LocalGenesis.teachingLevel.getSelectedIndex() == 0) {
/* 375:427 */           for (int i = 0; i < this.studentRules.getNumberOfChildren(); i++)
/* 376:    */           {
/* 377:428 */             Relation ruleToSend = (Relation)this.studentRules.getElement(i);
/* 378:    */             
/* 379:430 */             Connections.getPorts(this).transmit("rule2", ruleToSend);
/* 380:    */           }
/* 381:    */         }
/* 382:433 */         if (LocalGenesis.teachingLevel.getSelectedIndex() == 1)
/* 383:    */         {
/* 384:434 */           boolean newKnowledge = true;
/* 385:436 */           for (int i = 0; i < this.studentRules.getNumberOfChildren(); i++)
/* 386:    */           {
/* 387:437 */             newKnowledge = true;
/* 388:438 */             Relation ruleToSend = (Relation)this.studentRules.getElement(i);
/* 389:439 */             for (int j = 0; j < this.teacherRules.getNumberOfChildren(); j++) {
/* 390:440 */               if (ruleToSend.isEqual(this.teacherRules.getElement(j)))
/* 391:    */               {
/* 392:441 */                 newKnowledge = false;
/* 393:442 */                 break;
/* 394:    */               }
/* 395:    */             }
/* 396:445 */             if (newKnowledge) {
/* 397:448 */               Connections.getPorts(this).transmit("rule2", ruleToSend);
/* 398:    */             }
/* 399:    */           }
/* 400:    */         }
/* 401:452 */         if (LocalGenesis.teachingLevel.getSelectedIndex() == 2) {
/* 402:454 */           for (int i = 0; i < this.studentUsedRules.getNumberOfChildren(); i++)
/* 403:    */           {
/* 404:455 */             Relation ruleToSend = (Relation)this.studentUsedRules.getElement(i);
/* 405:    */             
/* 406:457 */             Connections.getPorts(this).transmit("rule2", ruleToSend);
/* 407:    */           }
/* 408:    */         }
/* 409:460 */         if (LocalGenesis.teachingLevel.getSelectedIndex() == 3)
/* 410:    */         {
/* 411:461 */           boolean newKnowledge = true;
/* 412:463 */           for (int i = 0; i < this.studentUsedRules.getNumberOfChildren(); i++)
/* 413:    */           {
/* 414:464 */             newKnowledge = true;
/* 415:465 */             Relation ruleToSend = (Relation)this.studentUsedRules.getElement(i);
/* 416:466 */             for (int j = 0; j < this.teacherUsedRules.getNumberOfChildren(); j++) {
/* 417:467 */               if (ruleToSend.isEqual(this.teacherUsedRules.getElement(j)))
/* 418:    */               {
/* 419:468 */                 newKnowledge = false;
/* 420:469 */                 break;
/* 421:    */               }
/* 422:    */             }
/* 423:472 */             if (newKnowledge) {
/* 424:475 */               Connections.getPorts(this).transmit("rule2", ruleToSend);
/* 425:    */             }
/* 426:    */           }
/* 427:    */         }
/* 428:    */       }
/* 429:    */     }
/* 430:    */   }
/* 431:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.Depricated.TeachingProcessor
 * JD-Core Version:    0.7.0.1
 */