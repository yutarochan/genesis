/*   1:    */ package silaSayan;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import connections.AbstractWiredBox;
/*   7:    */ import connections.Connections;
/*   8:    */ import connections.Ports;
/*   9:    */ import genesis.GenesisMenus;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.Vector;
/*  13:    */ import javax.swing.JRadioButton;
/*  14:    */ import matchers.StandardMatcher;
/*  15:    */ import parameters.Radio;
/*  16:    */ import start.Generator;
/*  17:    */ import storyProcessor.ReflectionAnalysis;
/*  18:    */ import storyProcessor.ReflectionDescription;
/*  19:    */ import utils.Mark;
/*  20:    */ 
/*  21:    */ public class LocalProcessor
/*  22:    */   extends AbstractWiredBox
/*  23:    */ {
/*  24: 24 */   private boolean isItOnlyRelevant = false;
/*  25: 26 */   private boolean isItFullStory = true;
/*  26: 28 */   private boolean isItMixture = false;
/*  27: 32 */   public final String MY_INPUT_PORT = "my input port";
/*  28: 34 */   public final String MY_OUTPUT_PORT = "my output port";
/*  29:    */   public static final String STORY1 = "story 1";
/*  30:    */   public static final String STORY2 = "story 2";
/*  31:    */   public static final String PLOT = "story plot";
/*  32:    */   public static final String QUIESCENCE_PORT1 = "quiescence port 1";
/*  33:    */   public static final String QUIESCENCE_PORT2 = "quiescence port 2";
/*  34:    */   public static final String RULE_PORT = "rules";
/*  35:    */   public static final String REFLECTION_PORT1 = "reflection port 1";
/*  36:    */   public static final String REFLECTION_PORT2 = "reflection port 2";
/*  37:    */   public static final String START_STORY_INFO1 = "start story info 1";
/*  38:    */   public static final String START_STORY_INFO2 = "start story info 2";
/*  39:    */   public static final String INFERENCES = "inferences";
/*  40:    */   public static final String INCREMENT = "increment";
/*  41: 60 */   public static String TEACH_RULE_PORT = "teach rule port";
/*  42: 62 */   public static String NEW_RULE_MESSENGER_PORT = "new rule messenger port";
/*  43: 67 */   private ArrayList<Entity> unmatchedList = new ArrayList();
/*  44: 69 */   private boolean isOneQuiet = false;
/*  45: 71 */   private boolean isTwoQuiet = false;
/*  46: 73 */   private Sequence quietIntervalOne = new Sequence();
/*  47: 75 */   private Sequence quietIntervalTwo = new Sequence();
/*  48: 77 */   private Sequence rules = new Sequence();
/*  49: 79 */   private Sequence rulesAlreadyReported = new Sequence();
/*  50: 81 */   private Sequence reflectionRules = new Sequence();
/*  51: 83 */   private Sequence relevantRules = new Sequence();
/*  52: 85 */   private Sequence missingRulesToCompareToReflectionRules = new Sequence();
/*  53: 88 */   private Entity startInfo1 = new Entity();
/*  54: 91 */   private Entity startInfo2 = new Entity();
/*  55: 93 */   private Sequence plotReceivedSoFar = new Sequence();
/*  56: 95 */   private Sequence storyToldSoFar = new Sequence();
/*  57: 97 */   private boolean storyHasStarted = false;
/*  58: 99 */   private Sequence preRelevantStoryStream = new Sequence();
/*  59:101 */   private Sequence relevantStoryStream = new Sequence();
/*  60:    */   
/*  61:    */   public LocalProcessor()
/*  62:    */   {
/*  63:106 */     setName("My story processor");
/*  64:    */     
/*  65:    */ 
/*  66:    */ 
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:118 */     Connections.getPorts(this).addSignalProcessor("quiescence port 1", "processQuiescence1");
/*  76:119 */     Connections.getPorts(this).addSignalProcessor("quiescence port 2", "processQuiescence2");
/*  77:    */     
/*  78:    */ 
/*  79:122 */     Connections.getPorts(this).addSignalProcessor("rules", "processRules");
/*  80:    */     
/*  81:    */ 
/*  82:125 */     Connections.getPorts(this).addSignalProcessor("reflection port 1", "processReflectionsForRules1");
/*  83:    */     
/*  84:127 */     Connections.getPorts(this).addSignalProcessor("story plot", "processPlot");
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void processPlot(Object o)
/*  88:    */   {
/*  89:135 */     if (!Radio.tellStoryButton.isSelected()) {
/*  90:136 */       return;
/*  91:    */     }
/*  92:139 */     Generator generator = Generator.getGenerator();
/*  93:141 */     if ((o instanceof Entity))
/*  94:    */     {
/*  95:142 */       Entity e = (Entity)o;
/*  96:143 */       if ((e.relationP()) && (e.getSubject().entityP("you")) && 
/*  97:144 */         (e.getObject().functionP("story")))
/*  98:    */       {
/*  99:145 */         Mark.say(new Object[] {"Story has started!!" });
/* 100:146 */         this.storyHasStarted = true;
/* 101:    */       }
/* 102:150 */       if ((this.storyHasStarted) && 
/* 103:151 */         (!e.functionP()) && 
/* 104:152 */         (!this.plotReceivedSoFar.containsDeprecated(e)))
/* 105:    */       {
/* 106:153 */         this.plotReceivedSoFar.addElement(e);
/* 107:154 */         Mark.say(new Object[] {"Just added ", e.asString(), "to plotReceivedSoFar" });
/* 108:    */       }
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void processReflectionsForRules1(Object o)
/* 113:    */   {
/* 114:168 */     if (!Radio.tellStoryButton.isSelected()) {
/* 115:169 */       return;
/* 116:    */     }
/* 117:172 */     if ((o instanceof ReflectionAnalysis))
/* 118:    */     {
/* 119:173 */       ReflectionAnalysis reflectionAnalysis = (ReflectionAnalysis)o;
/* 120:    */       Iterator localIterator2;
/* 121:174 */       for (Iterator localIterator1 = reflectionAnalysis.getReflectionDescriptions().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 122:    */       {
/* 123:174 */         ReflectionDescription description = (ReflectionDescription)localIterator1.next();
/* 124:175 */         localIterator2 = description.getRules().getElements().iterator(); continue;Entity t = (Entity)localIterator2.next();
/* 125:176 */         this.reflectionRules.addElement(t);
/* 126:    */       }
/* 127:181 */       if (this.isItFullStory) {
/* 128:183 */         filterStoryForRelevance();
/* 129:    */       } else {
/* 130:187 */         outputRelevantRules();
/* 131:    */       }
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void filterStoryForRelevance()
/* 136:    */   {
/* 137:198 */     if (!Radio.tellStoryButton.isSelected()) {
/* 138:199 */       return;
/* 139:    */     }
/* 140:202 */     StandardMatcher matcher = StandardMatcher.getBasicMatcher();
/* 141:203 */     Generator generator = Generator.getGenerator();
/* 142:207 */     for (Entity t : this.preRelevantStoryStream.getElements()) {
/* 143:209 */       if (this.plotReceivedSoFar.containsDeprecated(t))
/* 144:    */       {
/* 145:210 */         this.relevantStoryStream.addElement(t);
/* 146:211 */         String plotResult = generator.generate(t);
/* 147:212 */         Mark.say(new Object[] {"RELEVANT Plot/Rule:  ", plotResult });
/* 148:213 */         BetterSignal signal = new BetterSignal(new Object[] { "Story teller", plotResult });
/* 149:214 */         Connections.getPorts(this).transmit(signal);
/* 150:    */       }
/* 151:216 */       else if (this.reflectionRules.containsDeprecated(t))
/* 152:    */       {
/* 153:217 */         this.relevantStoryStream.addElement(t);
/* 154:218 */         String ruleResult = generator.generate(t);
/* 155:219 */         String ruleResultFinal = "***" + ruleResult + "***";
/* 156:220 */         Mark.say(new Object[] {"RELEVANT Plot/Rule:  ", ruleResultFinal });
/* 157:221 */         BetterSignal signal = new BetterSignal(new Object[] { "Story teller", ruleResultFinal });
/* 158:222 */         Connections.getPorts(this).transmit(signal);
/* 159:    */       }
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void outputRelevantRules()
/* 164:    */   {
/* 165:232 */     if (!Radio.tellStoryButton.isSelected()) {
/* 166:233 */       return;
/* 167:    */     }
/* 168:236 */     StandardMatcher matcher = StandardMatcher.getBasicMatcher();
/* 169:237 */     Generator generator = Generator.getGenerator();
/* 170:238 */     Connections.getPorts(this).transmit("switch tab", "Story teller");
/* 171:    */     Iterator localIterator2;
/* 172:241 */     for (Iterator localIterator1 = this.missingRulesToCompareToReflectionRules.getElements().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 173:    */     {
/* 174:241 */       Entity t = (Entity)localIterator1.next();
/* 175:    */       
/* 176:243 */       localIterator2 = this.reflectionRules.getElements().iterator(); continue;Entity r = (Entity)localIterator2.next();
/* 177:246 */       if ((matcher.match(t, r) != null) && 
/* 178:247 */         (!this.relevantRules.containsDeprecated(t)))
/* 179:    */       {
/* 180:248 */         this.relevantRules.addElement(t);
/* 181:249 */         Mark.say(new Object[] {"Second perspective missing these RELEVANT RULES: " });
/* 182:250 */         String result = generator.generate(t);
/* 183:251 */         String finalResult = "***" + result + "***";
/* 184:252 */         if (result != null)
/* 185:    */         {
/* 186:253 */           BetterSignal signal = new BetterSignal(new Object[] { "Story teller", finalResult });
/* 187:254 */           Connections.getPorts(this).transmit(signal);
/* 188:255 */           Mark.say(new Object[] {result });
/* 189:    */         }
/* 190:    */         else
/* 191:    */         {
/* 192:258 */           Mark.say(new Object[] {"RESULT IS NULL!!" });
/* 193:    */         }
/* 194:    */       }
/* 195:    */     }
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void processRules(Object o)
/* 199:    */   {
/* 200:270 */     if (!Radio.tellStoryButton.isSelected()) {
/* 201:271 */       return;
/* 202:    */     }
/* 203:274 */     if ((o instanceof Sequence)) {
/* 204:275 */       this.rules = ((Sequence)o);
/* 205:    */     }
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void processQuiescence1(Object o)
/* 209:    */   {
/* 210:283 */     if (!Radio.tellStoryButton.isSelected()) {
/* 211:284 */       return;
/* 212:    */     }
/* 213:288 */     if ((o instanceof Sequence))
/* 214:    */     {
/* 215:289 */       Sequence increment = (Sequence)o;
/* 216:    */       
/* 217:291 */       this.isOneQuiet = true;
/* 218:292 */       this.quietIntervalOne = increment;
/* 219:    */     }
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void processQuiescence2(Object o)
/* 223:    */   {
/* 224:304 */     if (!Radio.tellStoryButton.isSelected()) {
/* 225:305 */       return;
/* 226:    */     }
/* 227:308 */     if ((o instanceof Sequence))
/* 228:    */     {
/* 229:309 */       Sequence increment = (Sequence)o;
/* 230:310 */       Generator generator = Generator.getGenerator();
/* 231:    */       String mostRecentPlotString;
/* 232:314 */       if (this.isItFullStory)
/* 233:    */       {
/* 234:316 */         int plotSize = this.plotReceivedSoFar.getElements().size();
/* 235:318 */         if (plotSize != 0)
/* 236:    */         {
/* 237:319 */           Entity mostRecentPlot = this.plotReceivedSoFar.getElement(plotSize - 1);
/* 238:320 */           mostRecentPlotString = generator.generate(mostRecentPlot);
/* 239:321 */           if (!this.storyToldSoFar.containsDeprecated(mostRecentPlot))
/* 240:    */           {
/* 241:322 */             this.storyToldSoFar.addElement(mostRecentPlot);
/* 242:323 */             if (mostRecentPlotString != null) {
/* 243:325 */               if (!this.isItOnlyRelevant)
/* 244:    */               {
/* 245:327 */                 BetterSignal signal = new BetterSignal(new Object[] { "Story teller", mostRecentPlotString });
/* 246:328 */                 Connections.getPorts(this).transmit(signal);
/* 247:    */               }
/* 248:    */               else
/* 249:    */               {
/* 250:334 */                 Mark.say(new Object[] {"PPPP adding to preRelevantStoryStream: ", mostRecentPlotString });
/* 251:335 */                 this.preRelevantStoryStream.addElement(mostRecentPlot);
/* 252:    */               }
/* 253:    */             }
/* 254:    */           }
/* 255:    */         }
/* 256:    */       }
/* 257:343 */       this.isTwoQuiet = true;
/* 258:344 */       this.quietIntervalTwo = increment;
/* 259:346 */       if ((this.isOneQuiet) && (this.isTwoQuiet))
/* 260:    */       {
/* 261:348 */         StandardMatcher matcher = StandardMatcher.getBasicMatcher();
/* 262:350 */         for (Entity e : this.quietIntervalOne.getElements())
/* 263:    */         {
/* 264:352 */           int matchCount = 0;
/* 265:354 */           for (Entity f : this.quietIntervalTwo.getElements()) {
/* 266:357 */             if (matcher.match(e, f) != null)
/* 267:    */             {
/* 268:358 */               matchCount++;
/* 269:359 */               break;
/* 270:    */             }
/* 271:    */           }
/* 272:364 */           if ((matchCount == 0) && 
/* 273:365 */             (!this.unmatchedList.contains(e)))
/* 274:    */           {
/* 275:370 */             this.unmatchedList.add(e);
/* 276:    */             
/* 277:    */ 
/* 278:373 */             int listSize = this.unmatchedList.size();
/* 279:375 */             if (listSize != 0)
/* 280:    */             {
/* 281:377 */               Entity mostRecentMiss = (Entity)this.unmatchedList.get(listSize - 1);
/* 282:378 */               Generator generate = Generator.getGenerator();
/* 283:382 */               if ((GenesisMenus.getSpoonFeedButton().isSelected()) && (!this.isItMixture))
/* 284:    */               {
/* 285:385 */                 if (this.isItOnlyRelevant)
/* 286:    */                 {
/* 287:386 */                   if ((!mostRecentMiss.isA("prediction")) && (!mostRecentMiss.isA("explanation")))
/* 288:    */                   {
/* 289:387 */                     this.missingRulesToCompareToReflectionRules.addElement(mostRecentMiss);
/* 290:388 */                     this.preRelevantStoryStream.addElement(mostRecentMiss);
/* 291:    */                   }
/* 292:    */                 }
/* 293:392 */                 else if ((!mostRecentMiss.isA("prediction")) && (!mostRecentMiss.isA("explanation")))
/* 294:    */                 {
/* 295:393 */                   String mostRecentGenerated = generate.generate(mostRecentMiss);
/* 296:394 */                   String finalString = "*** " + mostRecentGenerated + " ***";
/* 297:395 */                   if (mostRecentGenerated != null)
/* 298:    */                   {
/* 299:396 */                     BetterSignal signal = new BetterSignal(new Object[] { "Story teller", finalString });
/* 300:397 */                     Connections.getPorts(this).transmit(signal);
/* 301:    */                   }
/* 302:    */                 }
/* 303:    */               }
/* 304:406 */               else if (GenesisMenus.getPrimingButton().isSelected())
/* 305:    */               {
/* 306:409 */                 if (this.isItOnlyRelevant)
/* 307:    */                 {
/* 308:410 */                   if ((mostRecentMiss.isA("prediction")) || (mostRecentMiss.isA("explanation")))
/* 309:    */                   {
/* 310:411 */                     this.missingRulesToCompareToReflectionRules.addElement(mostRecentMiss);
/* 311:412 */                     this.preRelevantStoryStream.addElement(mostRecentMiss);
/* 312:    */                   }
/* 313:    */                 }
/* 314:417 */                 else if ((mostRecentMiss.isA("prediction")) || (mostRecentMiss.isA("explanation")))
/* 315:    */                 {
/* 316:418 */                   Mark.say(new Object[] {"Second perspective is missing the following: " });
/* 317:419 */                   String mostRecentGenerated = generate.generate(mostRecentMiss);
/* 318:420 */                   String finalString = "*** " + mostRecentGenerated + " ***";
/* 319:422 */                   if (mostRecentGenerated != null)
/* 320:    */                   {
/* 321:423 */                     BetterSignal signal = new BetterSignal(new Object[] { "Story teller", finalString });
/* 322:424 */                     Connections.getPorts(this).transmit(signal);
/* 323:425 */                     Mark.say(new Object[] {mostRecentGenerated });
/* 324:    */                   }
/* 325:    */                 }
/* 326:    */               }
/* 327:433 */               else if (GenesisMenus.getPrimingWithIntrospectionButton().isSelected())
/* 328:    */               {
/* 329:435 */                 StandardMatcher match = StandardMatcher.getBasicMatcher();
/* 330:437 */                 if (this.isItOnlyRelevant)
/* 331:    */                 {
/* 332:438 */                   if ((mostRecentMiss.isA("prediction")) || (mostRecentMiss.isA("explanation")))
/* 333:    */                   {
/* 334:439 */                     this.missingRulesToCompareToReflectionRules.addElement(mostRecentMiss);
/* 335:440 */                     this.preRelevantStoryStream.addElement(mostRecentMiss);
/* 336:    */                   }
/* 337:    */                 }
/* 338:445 */                 else if ((mostRecentMiss.isA("prediction")) || (mostRecentMiss.isA("explanation")))
/* 339:    */                 {
/* 340:446 */                   String mostRecentGenerated = generator.generate(mostRecentMiss);
/* 341:447 */                   String finalString = "*** " + mostRecentGenerated + " ***";
/* 342:448 */                   BetterSignal signal = new BetterSignal(new Object[] { "Story teller", finalString });
/* 343:449 */                   Connections.getPorts(this).transmit(signal);
/* 344:    */                   
/* 345:451 */                   Mark.say(new Object[] {"Second perspective is missing the following: " });
/* 346:452 */                   Mark.say(new Object[] {mostRecentGenerated });
/* 347:455 */                   for (Entity rule : this.rules.getElements()) {
/* 348:457 */                     if (match.matchRuleToInstantiation(rule, mostRecentMiss) != null)
/* 349:    */                     {
/* 350:458 */                       if (this.rulesAlreadyReported.containsDeprecated(rule)) {
/* 351:    */                         break;
/* 352:    */                       }
/* 353:459 */                       this.rulesAlreadyReported.addElement(rule);
/* 354:460 */                       String result = generator.generate(rule);
/* 355:461 */                       String finalResult = "*** " + result + " ***";
/* 356:462 */                       if (result == null) {
/* 357:    */                         break;
/* 358:    */                       }
/* 359:463 */                       Mark.say(new Object[] {"GENERAL RULE: " });
/* 360:464 */                       Mark.say(new Object[] {result });
/* 361:465 */                       BetterSignal signalTwo = new BetterSignal(new Object[] { "Story teller", finalResult });
/* 362:466 */                       Connections.getPorts(this).transmit(signalTwo);
/* 363:467 */                       Mark.say(new Object[] {"ABOUT TO TRANSMIT NEW RULE ON TEACH-RULE-PORT" });
/* 364:468 */                       Connections.getPorts(this).transmit(TEACH_RULE_PORT, rule);
/* 365:469 */                       Connections.getPorts(this).transmit(NEW_RULE_MESSENGER_PORT, Boolean.valueOf(true));
/* 366:    */                       
/* 367:    */ 
/* 368:    */ 
/* 369:    */ 
/* 370:474 */                       break;
/* 371:    */                     }
/* 372:    */                   }
/* 373:    */                 }
/* 374:    */               }
/* 375:    */             }
/* 376:    */           }
/* 377:    */         }
/* 378:487 */         this.isOneQuiet = false;
/* 379:488 */         this.isTwoQuiet = false;
/* 380:    */       }
/* 381:    */     }
/* 382:    */   }
/* 383:    */   
/* 384:    */   public void processSignal1(Object signal)
/* 385:    */   {
/* 386:497 */     if ((signal instanceof Entity))
/* 387:    */     {
/* 388:498 */       Generator generator = Generator.getGenerator();
/* 389:499 */       Entity e = (Entity)signal;
/* 390:501 */       if ((e.relationP()) && (e.getSubject().entityP("you")) && 
/* 391:502 */         (e.getObject().functionP("story")))
/* 392:    */       {
/* 393:503 */         this.storyHasStarted = true;
/* 394:504 */         Mark.say(new Object[] {"stroy has started and storyHasStarted set to ", Boolean.valueOf(this.storyHasStarted) });
/* 395:    */       }
/* 396:508 */       if ((this.storyHasStarted) && 
/* 397:509 */         (!e.functionP()))
/* 398:    */       {
/* 399:510 */         String element = generator.generate(e);
/* 400:511 */         Mark.say(new Object[] {element });
/* 401:    */       }
/* 402:516 */       Connections.getPorts(this).transmit(e);
/* 403:517 */       Connections.getPorts(this).transmit("my output port", e);
/* 404:    */     }
/* 405:    */   }
/* 406:    */   
/* 407:    */   public void processSignal2(Object signal)
/* 408:    */   {
/* 409:524 */     if ((signal instanceof Entity))
/* 410:    */     {
/* 411:525 */       Generator generator = Generator.getGenerator();
/* 412:526 */       Entity e = (Entity)signal;
/* 413:527 */       String element = generator.generate(e);
/* 414:528 */       Mark.say(new Object[] {element });
/* 415:529 */       Connections.getPorts(this).transmit(e);
/* 416:530 */       Connections.getPorts(this).transmit("my output port", e);
/* 417:    */     }
/* 418:    */   }
/* 419:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     silaSayan.LocalProcessor
 * JD-Core Version:    0.7.0.1
 */