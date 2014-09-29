/*   1:    */ package expert;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Function;
/*   6:    */ import bridge.reps.entities.Relation;
/*   7:    */ import bridge.reps.entities.Sequence;
/*   8:    */ import connections.AbstractWiredBox;
/*   9:    */ import connections.Connections;
/*  10:    */ import connections.Ports;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.Iterator;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Vector;
/*  15:    */ import matchers.StandardMatcher;
/*  16:    */ import minilisp.LList;
/*  17:    */ import models.MentalModel;
/*  18:    */ import start.Generator;
/*  19:    */ import storyProcessor.ReflectionAnalysis;
/*  20:    */ import storyProcessor.ReflectionDescription;
/*  21:    */ import storyProcessor.StoryProcessor;
/*  22:    */ import text.Html;
/*  23:    */ import text.Punctuator;
/*  24:    */ import utils.Mark;
/*  25:    */ import utils.PairOfEntities;
/*  26:    */ import utils.StoryMethods;
/*  27:    */ 
/*  28:    */ public class QuestionExpert
/*  29:    */   extends AbstractWiredBox
/*  30:    */ {
/*  31:    */   MentalModel mentalModel;
/*  32:    */   private Sequence leftStory;
/*  33:    */   private Sequence rightStory;
/*  34:    */   private ArrayList<ReflectionDescription> leftPlotUnits;
/*  35:    */   private ArrayList<ReflectionDescription> rightPlotUnits;
/*  36:    */   public static final String CLEAR = "clear";
/*  37:    */   public static final String LEFT_STORY = "left story";
/*  38:    */   public static final String RIGHT_STORY = "right story";
/*  39:    */   public static final String LEFT_PLOT_UNITS = "left plot units";
/*  40:    */   public static final String RIGHT_PLOT_UNITS = "right plot units";
/*  41:    */   public static final String WHETHER_PORT = "whether port";
/*  42:    */   public static final String INSERT_PORT = "insert element port";
/*  43:    */   public static final String LEFT_CAUSE_PATH = "left causal path";
/*  44:    */   public static final String RIGHT_CAUSE_PATH = "right causal path";
/*  45:    */   public static final String TO_DYLAN = "to Dylan";
/*  46:    */   public static final String STORY_KEY = "story key";
/*  47:    */   public static final String QUESTION_KEY = "question key";
/*  48:    */   
/*  49:    */   public QuestionExpert()
/*  50:    */   {
/*  51: 65 */     setName("Question expert");
/*  52: 66 */     Connections.getPorts(this).addSignalProcessor("process");
/*  53: 67 */     Connections.getPorts(this).addSignalProcessor("left story", "setLeftStory");
/*  54: 68 */     Connections.getPorts(this).addSignalProcessor("right story", "setRightStory");
/*  55: 69 */     Connections.getPorts(this).addSignalProcessor("left plot units", "setLeftPlotUnits");
/*  56: 70 */     Connections.getPorts(this).addSignalProcessor("right plot units", "setRightPlotUnits");
/*  57:    */   }
/*  58:    */   
/*  59:    */   public QuestionExpert(MentalModel mm)
/*  60:    */   {
/*  61: 74 */     this.mentalModel = mm;
/*  62: 75 */     setName("Question expert");
/*  63: 76 */     Connections.getPorts(this).addSignalProcessor("process");
/*  64: 77 */     Connections.getPorts(this).addSignalProcessor("left story", "setLeftStory");
/*  65: 78 */     Connections.getPorts(this).addSignalProcessor("right story", "setRightStory");
/*  66: 79 */     Connections.getPorts(this).addSignalProcessor("left plot units", "setLeftPlotUnits");
/*  67: 80 */     Connections.getPorts(this).addSignalProcessor("right plot units", "setRightPlotUnits");
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void clearStories()
/*  71:    */   {
/*  72: 84 */     this.leftStory = null;
/*  73: 85 */     this.rightStory = null;
/*  74: 86 */     this.rightPlotUnits = null;
/*  75: 87 */     this.leftPlotUnits = null;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setLeftPlotUnits(Object object)
/*  79:    */   {
/*  80: 91 */     this.rightPlotUnits = null;
/*  81: 92 */     this.leftPlotUnits = ((ReflectionAnalysis)object).getReflectionDescriptions();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setRightPlotUnits(Object object)
/*  85:    */   {
/*  86: 96 */     this.rightPlotUnits = ((ReflectionAnalysis)object).getReflectionDescriptions();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setLeftStory(Object object)
/*  90:    */   {
/*  91:100 */     BetterSignal s = (BetterSignal)object;
/*  92:101 */     Sequence story = (Sequence)s.get(0, Sequence.class);
/*  93:    */     
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:106 */     this.rightStory = null;
/*  98:107 */     this.leftStory = story;
/*  99:    */     
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:113 */     Connections.getPorts(this).transmit("to Dylan", new BetterSignal(new Object[] { "story key", s }));
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setRightStory(Object object)
/* 108:    */   {
/* 109:117 */     BetterSignal s = (BetterSignal)object;
/* 110:118 */     Sequence story = (Sequence)s.get(0, Sequence.class);
/* 111:119 */     this.rightStory = story;
/* 112:    */   }
/* 113:    */   
/* 114:    */   private String answerBecauseQuestion(Function d)
/* 115:    */   {
/* 116:125 */     String p = "";
/* 117:126 */     String leftresult = answerBecauseQuestion(d, this.leftStory, this.leftPlotUnits, "left");
/* 118:    */     
/* 119:128 */     String rightresult = answerBecauseQuestion(d, this.rightStory, this.rightPlotUnits, "right");
/* 120:129 */     if (leftresult != null) {
/* 121:130 */       p = p + leftresult;
/* 122:    */     }
/* 123:132 */     if (rightresult != null) {
/* 124:133 */       p = p + rightresult;
/* 125:    */     }
/* 126:136 */     return p;
/* 127:    */   }
/* 128:    */   
/* 129:    */   private String answerWhyQuestion(Entity focus)
/* 130:    */   {
/* 131:140 */     String title = Html.h1("On a commonsense level");
/* 132:141 */     String jekyll = answerWhyQuestion(focus, this.leftStory);
/* 133:142 */     String hyde = answerWhyQuestion(focus, this.rightStory);
/* 134:143 */     String p = "";
/* 135:145 */     if ((jekyll != null) && (!jekyll.isEmpty())) {
/* 136:146 */       p = p + "It looks like Dr. Jekyll thinks " + jekyll;
/* 137:    */     }
/* 138:148 */     if ((hyde != null) && (!hyde.isEmpty())) {
/* 139:149 */       p = p + "<p> It looks like Mr. Hyde thinks " + hyde;
/* 140:    */     }
/* 141:151 */     if (((jekyll == null) || (jekyll.isEmpty())) && ((hyde == null) || (hyde.isEmpty()))) {
/* 142:152 */       p = p + "Neither Dr. Jekyll nor Mr. Hyde have an opinion.";
/* 143:    */     }
/* 144:154 */     p = p + "  ";
/* 145:155 */     return title + Html.normal(p);
/* 146:    */   }
/* 147:    */   
/* 148:    */   private String answerWhyQuestionWithTrait(Entity focus)
/* 149:    */   {
/* 150:159 */     String title = Html.h1("From a personality perspective");
/* 151:160 */     String jekyll = answerWhyQuestionWithTrait(focus, this.leftStory);
/* 152:161 */     String hyde = answerWhyQuestionWithTrait(focus, this.rightStory);
/* 153:162 */     if ((jekyll == null) && (hyde == null)) {
/* 154:163 */       return "";
/* 155:    */     }
/* 156:165 */     String p = "";
/* 157:167 */     if ((jekyll != null) && (!jekyll.isEmpty())) {
/* 158:168 */       p = p + "It looks like Dr. Jekyll thinks " + jekyll;
/* 159:    */     }
/* 160:170 */     if ((hyde != null) && (!hyde.isEmpty())) {
/* 161:171 */       p = p + "<p> It looks like Mr. Hyde thinks " + hyde;
/* 162:    */     }
/* 163:173 */     if (((jekyll == null) || (jekyll.isEmpty())) && ((hyde == null) || (hyde.isEmpty()))) {
/* 164:174 */       p = p + "Neither Dr. Jekyll nor Mr. Hyde have an opinion.";
/* 165:    */     }
/* 166:176 */     p = p + "  ";
/* 167:177 */     return title + Html.normal(p);
/* 168:    */   }
/* 169:    */   
/* 170:    */   private String answerWhyQuestion(Entity focus, Sequence story)
/* 171:    */   {
/* 172:181 */     if (story == null) {
/* 173:182 */       return null;
/* 174:    */     }
/* 175:184 */     List<String> answers = new ArrayList();
/* 176:185 */     for (Entity t : story.getElements()) {
/* 177:186 */       if ((t.isAPrimed("cause")) && (t.relationP()))
/* 178:    */       {
/* 179:187 */         Relation r = (Relation)t;
/* 180:188 */         Entity conclusion = r.getObject();
/* 181:    */         
/* 182:190 */         LList<PairOfEntities> bindings = StandardMatcher.getIdentityMatcher().match(conclusion, focus);
/* 183:195 */         if (bindings != null) {
/* 184:198 */           answers.add(constructWhyAnswer(r));
/* 185:    */         }
/* 186:    */       }
/* 187:    */     }
/* 188:202 */     if ((answers != null) && (!answers.isEmpty())) {
/* 189:203 */       return Generator.merge(answers);
/* 190:    */     }
/* 191:205 */     return null;
/* 192:    */   }
/* 193:    */   
/* 194:    */   private String answerBecauseQuestion(Entity focus, Sequence story, ArrayList<ReflectionDescription> plotunits, String side)
/* 195:    */   {
/* 196:211 */     if (story == null) {
/* 197:212 */       return null;
/* 198:    */     }
/* 199:215 */     Entity cause = focus.getSubject();
/* 200:216 */     Entity antecedent = cause.getSubject().getElement(0);
/* 201:217 */     Entity consequent = cause.getObject();
/* 202:    */     
/* 203:    */ 
/* 204:    */ 
/* 205:    */ 
/* 206:    */ 
/* 207:223 */     String result = "";
/* 208:225 */     if (side.equals("left")) {
/* 209:226 */       result = result + "<p>It looks like the Dr. Jekyll reader of ";
/* 210:    */     } else {
/* 211:229 */       result = result + "<p>It looks like the Mr. Hyde reader of ";
/* 212:    */     }
/* 213:232 */     result = result + Html.remove_(Html.ital(Html.capitalize(StoryProcessor.getTitle(story))));
/* 214:    */     
/* 215:    */ 
/* 216:235 */     boolean existStory = existStory(antecedent, story);
/* 217:236 */     if (!existStory)
/* 218:    */     {
/* 219:243 */       MentalModel traitModel = null;
/* 220:    */       String traitName;
/* 221:245 */       for (Entity storyElement : story.getElements()) {
/* 222:246 */         if ((storyElement.relationP("personality_trait")) && (storyElement.getSubject().getType().equalsIgnoreCase("I")))
/* 223:    */         {
/* 224:247 */           traitName = storyElement.getObject().getType();
/* 225:248 */           traitModel = this.mentalModel.getLocalMentalModel(traitName);
/* 226:    */         }
/* 227:    */       }
/* 228:253 */       if (traitModel != null)
/* 229:    */       {
/* 230:254 */         Sequence traitModelStory = traitModel.getStoryProcessor().getExplicitElements();
/* 231:255 */         for (Entity element : traitModelStory.getElements())
/* 232:    */         {
/* 233:256 */           Mark.say(new Object[] {element.asString() });
/* 234:257 */           if ((element.relationP("believe")) && (element.getSubject().getType().equalsIgnoreCase("I")))
/* 235:    */           {
/* 236:259 */             Entity belief = element.getObject().getElement(0).getSubject();
/* 237:    */             
/* 238:    */ 
/* 239:    */ 
/* 240:    */ 
/* 241:264 */             belief = this.mentalModel.getStoryProcessor().reassembleAndDereference(belief);
/* 242:265 */             antecedent = this.mentalModel.getStoryProcessor().reassembleAndDereference(belief);
/* 243:266 */             consequent = this.mentalModel.getStoryProcessor().reassembleAndDereference(belief);
/* 244:    */             
/* 245:268 */             LList<PairOfEntities> bindings = StandardMatcher.getBasicMatcher().match(belief, antecedent);
/* 246:    */             
/* 247:    */ 
/* 248:    */ 
/* 249:272 */             Mark.say(new Object[] {"Initial result", result });
/* 250:273 */             if (bindings != null)
/* 251:    */             {
/* 252:275 */               Connections.getPorts(this).transmit("insert element port", belief);
/* 253:276 */               result = result + ", <b>on reflection, believes</b> " + Generator.getGenerator().generateXPeriod(antecedent);
/* 254:    */               
/* 255:278 */               Sequence newStory = this.mentalModel.getStoryProcessor().getStory();
/* 256:    */               
/* 257:280 */               boolean causalImp = evaluateCause(newStory, plotunits, antecedent, consequent, side);
/* 258:286 */               if (causalImp) {
/* 259:288 */                 result = result + " which <b>enables him to believe</b> ";
/* 260:    */               } else {
/* 261:293 */                 result = result + " but the reader still believes that ";
/* 262:    */               }
/* 263:297 */               return result += Generator.getGenerator().generateXPeriod(cause);
/* 264:    */             }
/* 265:    */           }
/* 266:    */         }
/* 267:    */       }
/* 268:    */       else
/* 269:    */       {
/* 270:304 */         result = result + " <b>does not believe</b> " + Generator.getGenerator().generateXPeriod(antecedent);
/* 271:305 */         result = result + " and therefore <b>cannot believe</b> that ";
/* 272:    */       }
/* 273:308 */       if (side.equals("left")) {
/* 274:310 */         Connections.getPorts(this).transmit("left causal path", new Vector());
/* 275:    */       } else {
/* 276:314 */         Connections.getPorts(this).transmit("right causal path", new Vector());
/* 277:    */       }
/* 278:316 */       return result += Generator.getGenerator().generateXPeriod(cause);
/* 279:    */     }
/* 280:321 */     result = result + " <b>believes</b> " + Generator.getGenerator().generateXPeriod(antecedent);
/* 281:322 */     result = result + " which <b>enables him to believe</b> ";
/* 282:323 */     result = result + Generator.getGenerator().generateXPeriod(cause);
/* 283:    */     
/* 284:325 */     return result;
/* 285:    */   }
/* 286:    */   
/* 287:    */   private String answerWhyQuestionWithTrait(Entity focus, Sequence story)
/* 288:    */   {
/* 289:332 */     if (story == null) {
/* 290:333 */       return null;
/* 291:    */     }
/* 292:335 */     List<String> answers = new ArrayList();
/* 293:336 */     for (Entity t : story.getElements()) {
/* 294:337 */       if ((t.relationP("cause")) && (MentalModel.hasMentalModelHost(t)))
/* 295:    */       {
/* 296:338 */         Relation r = (Relation)t;
/* 297:339 */         Entity conclusion = r.getObject();
/* 298:    */         
/* 299:341 */         LList<PairOfEntities> bindings = StandardMatcher.getIdentityMatcher().match(conclusion, focus);
/* 300:344 */         if (bindings != null) {
/* 301:347 */           answers.add(constructWhyAnswerWithTrait(r));
/* 302:    */         }
/* 303:    */       }
/* 304:    */     }
/* 305:351 */     if ((answers != null) && (!answers.isEmpty())) {
/* 306:352 */       return Generator.merge(answers);
/* 307:    */     }
/* 308:354 */     return null;
/* 309:    */   }
/* 310:    */   
/* 311:    */   private String reflectOnWhyQuestion(Entity focus)
/* 312:    */   {
/* 313:367 */     String title = Html.h1("On a concept level");
/* 314:    */     
/* 315:369 */     String jekyll = reflectOnWhyQuestion(focus, this.leftPlotUnits);
/* 316:370 */     String hyde = reflectOnWhyQuestion(focus, this.rightPlotUnits);
/* 317:371 */     String p = "";
/* 318:372 */     if ((jekyll != null) && (!jekyll.isEmpty())) {
/* 319:373 */       p = p + "It looks like Dr. Jekyll thinks " + jekyll;
/* 320:    */     }
/* 321:375 */     if ((hyde != null) && (!hyde.isEmpty())) {
/* 322:376 */       p = p + "<p> It looks like Mr. Hyde thinks " + hyde;
/* 323:    */     }
/* 324:378 */     if (((jekyll == null) || (jekyll.isEmpty())) && ((hyde == null) || (hyde.isEmpty()))) {
/* 325:379 */       p = p + "Neither Dr. Jekyll nor Mr. Hyde have an opinion.";
/* 326:    */     }
/* 327:381 */     p = p + "  ";
/* 328:382 */     return title + Html.normal(Punctuator.conditionName(p));
/* 329:    */   }
/* 330:    */   
/* 331:    */   private String constructWhyAnswer(Relation r)
/* 332:    */   {
/* 333:386 */     String result = Generator.getGenerator().generate(r);
/* 334:387 */     return result;
/* 335:    */   }
/* 336:    */   
/* 337:    */   private String constructWhyAnswerWithTrait(Relation r)
/* 338:    */   {
/* 339:391 */     Entity c = r.getObject();
/* 340:392 */     Entity agent = c.getSubject();
/* 341:    */     
/* 342:394 */     Entity model = MentalModel.getMentalModelHosts(r);
/* 343:396 */     if (model == null) {
/* 344:397 */       return "";
/* 345:    */     }
/* 346:399 */     Relation characterization = new Relation("classification", model, agent);
/* 347:400 */     Relation cause = new Relation("because", c, characterization);
/* 348:    */     
/* 349:402 */     String result = Generator.getGenerator().generate(cause);
/* 350:403 */     return result;
/* 351:    */   }
/* 352:    */   
/* 353:    */   private String constructBecauseAnswer(boolean b, Entity r)
/* 354:    */   {
/* 355:    */     String result;
/* 356:    */     String result;
/* 357:408 */     if (b) {
/* 358:409 */       result = " <b>believes</b> that ";
/* 359:    */     } else {
/* 360:412 */       result = " <b>does not believe</b> that ";
/* 361:    */     }
/* 362:414 */     String answer = Generator.getGenerator().generate(r);
/* 363:415 */     return result + answer;
/* 364:    */   }
/* 365:    */   
/* 366:    */   private String reflectOnWhyQuestion(Entity focus, ArrayList<ReflectionDescription> plotUnits)
/* 367:    */   {
/* 368:420 */     if (plotUnits == null) {
/* 369:421 */       return null;
/* 370:    */     }
/* 371:424 */     ArrayList<String> result = new ArrayList();
/* 372:425 */     for (Iterator localIterator1 = plotUnits.iterator(); localIterator1.hasNext(); ???.hasNext())
/* 373:    */     {
/* 374:425 */       ReflectionDescription plotUnit = (ReflectionDescription)localIterator1.next();
/* 375:426 */       String plotUnitName = Punctuator.conditionName(plotUnit.getName());
/* 376:427 */       ArrayList<Entity> candidates = new ArrayList();
/* 377:429 */       for (Entity e : plotUnit.getStoryElementsInvolved().getElements()) {
/* 378:430 */         if (e.sequenceP()) {
/* 379:431 */           candidates.addAll(((Sequence)e).getElements());
/* 380:    */         } else {
/* 381:434 */           candidates.add(e);
/* 382:    */         }
/* 383:    */       }
/* 384:438 */       ??? = candidates.iterator(); continue;Entity candidate = (Entity)???.next();
/* 385:    */       
/* 386:    */ 
/* 387:    */ 
/* 388:442 */       LList<PairOfEntities> bindings = StandardMatcher.getIdentityMatcher().match(candidate, focus);
/* 389:445 */       if ((bindings != null) && 
/* 390:446 */         (!result.contains(plotUnitName))) {
/* 391:447 */         result.add(plotUnitName);
/* 392:    */       }
/* 393:    */     }
/* 394:452 */     if (result.isEmpty()) {
/* 395:453 */       return null;
/* 396:    */     }
/* 397:455 */     return Html.normal(constructWhyReflection(result, focus));
/* 398:    */   }
/* 399:    */   
/* 400:    */   private String constructWhyReflection(ArrayList<String> elements, Entity focus)
/* 401:    */   {
/* 402:460 */     String result = Generator.getGenerator().generateXPeriod(focus);
/* 403:461 */     int size = elements.size();
/* 404:462 */     if (size == 0) {
/* 405:463 */       return null;
/* 406:    */     }
/* 407:465 */     if (size == 1) {
/* 408:466 */       result = result + " is part of act of ";
/* 409:468 */     } else if (size > 1) {
/* 410:469 */       result = result + " is part of acts of ";
/* 411:    */     }
/* 412:471 */     result = result + Punctuator.punctuateAnd(elements);
/* 413:472 */     return Punctuator.addPeriod(result);
/* 414:    */   }
/* 415:    */   
/* 416:    */   private void iDoNotKnow(Function d)
/* 417:    */   {
/* 418:477 */     String result = "I don't know " + Generator.getGenerator().generate(d);
/* 419:    */     
/* 420:    */ 
/* 421:    */ 
/* 422:    */ 
/* 423:    */ 
/* 424:    */ 
/* 425:    */ 
/* 426:485 */     Connections.getPorts(this).transmit(result);
/* 427:    */   }
/* 428:    */   
/* 429:    */   public boolean evaluateCause(Sequence story, ArrayList<ReflectionDescription> reflections, Entity cause, Entity result, String side)
/* 430:    */   {
/* 431:499 */     int answer = 0;
/* 432:    */     
/* 433:501 */     Vector<Entity> path1 = StoryMethods.distance(findCauseInStory(cause, story), findCauseInStory(result, story), story);
/* 434:502 */     Vector<Entity> path2 = StoryMethods.distance(findCauseInStory(result, story), findCauseInStory(cause, story), story);
/* 435:512 */     if (side.equals("left"))
/* 436:    */     {
/* 437:513 */       if (!path1.isEmpty())
/* 438:    */       {
/* 439:514 */         answer = 1;
/* 440:    */         
/* 441:516 */         Connections.getPorts(this).transmit("left causal path", path1);
/* 442:    */       }
/* 443:518 */       else if (!path2.isEmpty())
/* 444:    */       {
/* 445:519 */         answer = 1;
/* 446:    */         
/* 447:521 */         Connections.getPorts(this).transmit("left causal path", path2);
/* 448:    */       }
/* 449:    */       else
/* 450:    */       {
/* 451:525 */         Connections.getPorts(this).transmit("left causal path", new Vector());
/* 452:    */       }
/* 453:    */     }
/* 454:530 */     else if (!path1.isEmpty())
/* 455:    */     {
/* 456:531 */       answer = 1;
/* 457:    */       
/* 458:533 */       Connections.getPorts(this).transmit("right causal path", path1);
/* 459:    */     }
/* 460:535 */     else if (!path2.isEmpty())
/* 461:    */     {
/* 462:536 */       answer = 1;
/* 463:    */       
/* 464:538 */       Connections.getPorts(this).transmit("right causal path", path2);
/* 465:    */     }
/* 466:    */     else
/* 467:    */     {
/* 468:542 */       Connections.getPorts(this).transmit("right causal path", new Vector());
/* 469:    */     }
/* 470:546 */     Mark.say(new Object[] {"concepts" });
/* 471:547 */     if (reflections != null)
/* 472:    */     {
/* 473:    */       Iterator localIterator2;
/* 474:548 */       for (Iterator localIterator1 = reflections.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 475:    */       {
/* 476:548 */         ReflectionDescription rd = (ReflectionDescription)localIterator1.next();
/* 477:    */         
/* 478:550 */         localIterator2 = rd.getStoryElementsInvolved().getElements().iterator(); continue;Entity t = (Entity)localIterator2.next();
/* 479:    */         
/* 480:    */ 
/* 481:553 */         LList<PairOfEntities> bindings = StandardMatcher.getIdentityMatcher().match(t, cause);
/* 482:557 */         if (bindings != null)
/* 483:    */         {
/* 484:559 */           Mark.say(new Object[] {"Unexpected match in QuestionExpert.evaluateCause!!!!!!!!!!!!!" });
/* 485:560 */           answer = 1;
/* 486:    */         }
/* 487:    */       }
/* 488:    */     }
/* 489:566 */     if (answer > 0) {
/* 490:567 */       return true;
/* 491:    */     }
/* 492:570 */     return false;
/* 493:    */   }
/* 494:    */   
/* 495:    */   private Entity findCauseInStory(Entity cause, Sequence story)
/* 496:    */   {
/* 497:578 */     for (Entity t : story.getElements()) {
/* 498:579 */       if (t.hash().equals(cause.hash())) {
/* 499:581 */         return t;
/* 500:    */       }
/* 501:    */     }
/* 502:584 */     Mark.err(new Object[] {"Could not find match in story for", cause.asString() });
/* 503:585 */     return null;
/* 504:    */   }
/* 505:    */   
/* 506:    */   public static boolean existStory(Entity element, Sequence story)
/* 507:    */   {
/* 508:589 */     boolean found = false;
/* 509:590 */     for (Entity storyElement : story.getElements())
/* 510:    */     {
/* 511:592 */       LList<PairOfEntities> bindings = StandardMatcher.getIdentityMatcher().match(element, storyElement);
/* 512:597 */       if (bindings != null) {
/* 513:598 */         found = true;
/* 514:    */       }
/* 515:    */     }
/* 516:601 */     return found;
/* 517:    */   }
/* 518:    */   
/* 519:    */   public void process(Object object)
/* 520:    */   {
/* 521:606 */     boolean debug = false;
/* 522:607 */     if (object == "reset")
/* 523:    */     {
/* 524:608 */       clearStories();
/* 525:609 */       return;
/* 526:    */     }
/* 527:612 */     if (!(object instanceof Entity)) {
/* 528:613 */       return;
/* 529:    */     }
/* 530:616 */     Entity entity = (Entity)object;
/* 531:618 */     if (entity.isAPrimed("question"))
/* 532:    */     {
/* 533:620 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Question processor working on", entity });
/* 534:622 */       if ((entity.isAPrimed("where")) && ((entity instanceof Function)))
/* 535:    */       {
/* 536:623 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Encountered where question" });
/* 537:624 */         Function d = (Function)entity;
/* 538:625 */         iDoNotKnow(d);
/* 539:626 */         Connections.getPorts(this).transmit("viewer", d);
/* 540:    */       }
/* 541:628 */       else if ((entity.isAPrimed("why")) && ((entity instanceof Function)))
/* 542:    */       {
/* 543:629 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Encountered why question" });
/* 544:630 */         Function d = (Function)entity;
/* 545:631 */         String result = answerWhyQuestionWithTrait(d.getSubject());
/* 546:    */         
/* 547:    */ 
/* 548:634 */         result = result + answerWhyQuestion(d.getSubject());
/* 549:    */         
/* 550:636 */         result = result + reflectOnWhyQuestion(d.getSubject());
/* 551:    */         
/* 552:638 */         BetterSignal signal = new BetterSignal(new Object[] { "Answers", "clear" });
/* 553:    */         
/* 554:640 */         Connections.getPorts(this).transmit(signal);
/* 555:    */         
/* 556:642 */         signal = new BetterSignal(new Object[] { "Answers", result });
/* 557:    */         
/* 558:644 */         Connections.getPorts(this).transmit(signal);
/* 559:    */       }
/* 560:646 */       else if ((entity.isAPrimed("when")) && ((entity instanceof Function)))
/* 561:    */       {
/* 562:647 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Encountered when question" });
/* 563:648 */         Function d = (Function)entity;
/* 564:649 */         iDoNotKnow(d);
/* 565:650 */         Connections.getPorts(this).transmit("viewer", d);
/* 566:    */       }
/* 567:652 */       else if ((entity.isAPrimed("whether")) && ((entity instanceof Function)))
/* 568:    */       {
/* 569:653 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Encountered whether question" });
/* 570:654 */         Function d = (Function)entity;
/* 571:655 */         Connections.getPorts(this).transmit("whether port", d.getSubject());
/* 572:656 */         Connections.getPorts(this).transmit("viewer", d);
/* 573:    */       }
/* 574:658 */       else if ((entity.isAPrimed("what_if")) && ((entity instanceof Function)))
/* 575:    */       {
/* 576:659 */         Function d = (Function)entity;
/* 577:    */         
/* 578:661 */         dylanToHandle(d);
/* 579:662 */         Connections.getPorts(this).transmit("viewer", d);
/* 580:    */         
/* 581:    */ 
/* 582:    */ 
/* 583:    */ 
/* 584:    */ 
/* 585:668 */         Connections.getPorts(this).transmit("viewer", "hello world");
/* 586:    */         
/* 587:670 */         Mark.say(new Object[] {Boolean.valueOf(true), "Encountered what-happens-if question", d });
/* 588:671 */         Connections.getPorts(this).transmit("to Dylan", new BetterSignal(new Object[] { "question key", d }));
/* 589:    */       }
/* 590:674 */       else if ((entity.isAPrimed("did")) && ((entity instanceof Function)))
/* 591:    */       {
/* 592:676 */         Function d = (Function)entity;
/* 593:677 */         String result = answerBecauseQuestion(d);
/* 594:678 */         Connections.getPorts(this).transmit("causal analysis", "clear");
/* 595:679 */         Connections.getPorts(this).transmit("causal analysis", result);
/* 596:680 */         Connections.getPorts(this).transmit("viewer", d);
/* 597:    */       }
/* 598:    */       else
/* 599:    */       {
/* 600:684 */         Mark.err(new Object[] {"Question expert encounted unknown question type", entity });
/* 601:685 */         Connections.getPorts(this).transmit("next", entity);
/* 602:    */       }
/* 603:    */     }
/* 604:    */     else
/* 605:    */     {
/* 606:689 */       Connections.getPorts(this).transmit("next", entity);
/* 607:    */     }
/* 608:    */   }
/* 609:    */   
/* 610:    */   private void dylanToHandle(Function d)
/* 611:    */   {
/* 612:695 */     String result = "Dylan will handle " + Generator.getGenerator().generate(d);
/* 613:696 */     Connections.getPorts(this).transmit(result);
/* 614:    */   }
/* 615:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.QuestionExpert
 * JD-Core Version:    0.7.0.1
 */