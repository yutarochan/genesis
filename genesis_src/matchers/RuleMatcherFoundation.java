/*   1:    */ package matchers;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Function;
/*   6:    */ import bridge.reps.entities.Matcher;
/*   7:    */ import bridge.reps.entities.Relation;
/*   8:    */ import bridge.reps.entities.Sequence;
/*   9:    */ import connections.Connections;
/*  10:    */ import connections.Ports;
/*  11:    */ import connections.WiredBox;
/*  12:    */ import java.io.PrintStream;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Collections;
/*  15:    */ import java.util.Iterator;
/*  16:    */ import java.util.Vector;
/*  17:    */ import matchers.original.BasicMatcherOriginal;
/*  18:    */ import minilisp.LList;
/*  19:    */ import utils.Mark;
/*  20:    */ import utils.PairOfEntities;
/*  21:    */ 
/*  22:    */ public class RuleMatcherFoundation
/*  23:    */ {
/*  24: 21 */   protected static boolean allowMultipleExplanations = true;
/*  25: 23 */   protected static boolean announceSuccess = false;
/*  26: 25 */   protected static boolean debug = false;
/*  27: 27 */   protected static boolean debugMay = false;
/*  28: 29 */   protected static boolean debugCensor = false;
/*  29: 31 */   protected static boolean debugDereification = false;
/*  30: 33 */   protected static boolean debugAnticipate = false;
/*  31: 35 */   protected static boolean debugInstantiatedRules = false;
/*  32: 37 */   protected static boolean debug3 = false;
/*  33: 39 */   protected static boolean testPredictionRule = false;
/*  34:    */   
/*  35:    */   public static void completePredictionWithGivenRule(int sceneMarker, Sequence story, WiredBox processor, Relation rule, Vector<Entity> satisfiedAntecedants, Vector<Entity> unsatisfiedAntecedants, Entity consequent, LList<PairOfEntities> bindings)
/*  36:    */   {
/*  37: 46 */     boolean debug = false;
/*  38:    */     Sequence antecedants;
/*  39: 47 */     if (unsatisfiedAntecedants.isEmpty())
/*  40:    */     {
/*  41: 49 */       Mark.say(new Object[] {Boolean.valueOf(announceSuccess), "Fired rule", rule.asString() });
/*  42:    */       
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47: 55 */       Entity test = Matcher.instantiate(consequent, bindings);
/*  48:    */       
/*  49: 57 */       consequent = removeActionReification(test);
/*  50:    */       Sequence antecedants;
/*  51: 61 */       if ((rule.isA("abduction")) || (rule.isA("enabler")))
/*  52:    */       {
/*  53: 62 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Working on abduction rule", rule.asString() });
/*  54: 63 */         for (Entity element : satisfiedAntecedants)
/*  55:    */         {
/*  56: 64 */           antecedants = new Sequence();
/*  57: 65 */           antecedants.addElement(consequent);
/*  58: 66 */           antecedants.addType("conjuction");
/*  59: 67 */           Relation instantiatedRule = new Relation("cause", antecedants, element);
/*  60: 68 */           instantiatedRule.addType("inference");
/*  61: 69 */           if (rule.isA("abduction")) {
/*  62: 70 */             instantiatedRule.addType("abduction");
/*  63: 72 */           } else if (rule.isA("enabler")) {
/*  64: 73 */             instantiatedRule.addType("enabler");
/*  65:    */           } else {
/*  66: 76 */             Mark.err(new Object[] {"Strangeness in RuleMatcherFoundation" });
/*  67:    */           }
/*  68: 78 */           Mark.say(new Object[] {Boolean.valueOf(debug), "Asserting", instantiatedRule.asString() });
/*  69: 79 */           Connections.getPorts(processor).transmit("used rules port", new BetterSignal(new Object[] { rule }));
/*  70: 80 */           Connections.getPorts(processor).transmit(new BetterSignal(new Object[] { instantiatedRule, rule }));
/*  71:    */         }
/*  72:    */       }
/*  73:    */       else
/*  74:    */       {
/*  75: 84 */         Mark.say(new Object[] {Boolean.valueOf(testPredictionRule), "Working on prediction rule", rule.asString() });
/*  76: 85 */         antecedants = new Sequence();
/*  77: 86 */         for (Entity satisfiedAntecedant : satisfiedAntecedants) {
/*  78: 87 */           antecedants.addElement(satisfiedAntecedant);
/*  79:    */         }
/*  80: 89 */         Mark.say(new Object[] {Boolean.valueOf(testPredictionRule), "Fully matched rule,", rule.asString(), "bindings are", bindings });
/*  81: 90 */         antecedants.addType("conjuction");
/*  82: 91 */         Relation instantiatedRule = new Relation("cause", antecedants, consequent);
/*  83: 92 */         instantiatedRule.addType("inference");
/*  84: 93 */         instantiatedRule.addType("prediction");
/*  85: 94 */         Mark.say(new Object[] {Boolean.valueOf(debug) });
/*  86: 95 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Rule is             ", rule.asStringWithIndexes() });
/*  87: 96 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Instantiated rule is", instantiatedRule.asStringWithIndexes() });
/*  88: 97 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Bindings:", bindings });
/*  89:    */         
/*  90: 99 */         Mark.say(new Object[] {Boolean.valueOf(testPredictionRule), "Asserting", instantiatedRule.asString() });
/*  91:100 */         Connections.getPorts(processor).transmit("used rules port", new BetterSignal(new Object[] { rule }));
/*  92:101 */         Connections.getPorts(processor).transmit(new BetterSignal(new Object[] { instantiatedRule, rule }));
/*  93:    */       }
/*  94:    */     }
/*  95:    */     else
/*  96:    */     {
/*  97:110 */       Entity unsatisfiedAntecedant = (Entity)unsatisfiedAntecedants.elementAt(0);
/*  98:111 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Working on another antecedant " + unsatisfiedAntecedant.asString() });
/*  99:    */       Object newBindings;
/* 100:114 */       for (Entity storyElement : copyAndReverse(story.getElements())) {
/* 101:117 */         if (satisfiedAntecedants.contains(storyElement))
/* 102:    */         {
/* 103:118 */           Mark.say(new Object[] {Boolean.valueOf(debug3), "Already used", storyElement.asString() });
/* 104:    */         }
/* 105:    */         else
/* 106:    */         {
/* 107:121 */           newBindings = StandardMatcher.getBasicMatcher().match(unsatisfiedAntecedant, storyElement, bindings);
/* 108:122 */           if (newBindings != null)
/* 109:    */           {
/* 110:123 */             Mark.say(new Object[] {Boolean.valueOf(debug), "Matched\n", unsatisfiedAntecedant.asString(), "\n", storyElement.asString(), "\n", newBindings });
/* 111:    */             
/* 112:    */ 
/* 113:    */ 
/* 114:127 */             Vector<Entity> s = copy(satisfiedAntecedants);
/* 115:128 */             Vector<Entity> u = copy(unsatisfiedAntecedants);
/* 116:129 */             u.remove(unsatisfiedAntecedant);
/* 117:130 */             s.add(storyElement);
/* 118:131 */             completePredictionWithGivenRule(sceneMarker, story, processor, rule, s, u, consequent, (LList)newBindings);
/* 119:    */           }
/* 120:    */         }
/* 121:    */       }
/* 122:141 */       if (unsatisfiedAntecedant.hasFeature("not"))
/* 123:    */       {
/* 124:142 */         boolean found = false;
/* 125:143 */         for (newBindings = copyAndReverse(story.getElements()).iterator(); ((Iterator)newBindings).hasNext();)
/* 126:    */         {
/* 127:143 */           Entity storyElement = (Entity)((Iterator)newBindings).next();
/* 128:    */           
/* 129:    */ 
/* 130:    */ 
/* 131:147 */           LList<PairOfEntities> newBindings = StandardMatcher.getBasicMatcher()
/* 132:148 */             .matchNegation(unsatisfiedAntecedant, storyElement, bindings);
/* 133:150 */           if (newBindings != null)
/* 134:    */           {
/* 135:151 */             found = true;
/* 136:152 */             break;
/* 137:    */           }
/* 138:    */         }
/* 139:155 */         if (!found)
/* 140:    */         {
/* 141:157 */           Mark.say(new Object[] {Boolean.valueOf(debug), "Successfully matched another antecedant by absence of positive form " + unsatisfiedAntecedant.asString(), bindings });
/* 142:158 */           Vector<Entity> s = copy(satisfiedAntecedants);
/* 143:159 */           Object u = copy(unsatisfiedAntecedants);
/* 144:160 */           Entity negation = Substitutor.replace(unsatisfiedAntecedant, bindings);
/* 145:161 */           negation = BasicMatcherOriginal.getBasicMatcher().dereference(negation, 0, story);
/* 146:162 */           s.add(negation);
/* 147:163 */           ((Vector)u).remove(unsatisfiedAntecedant);
/* 148:164 */           completePredictionWithGivenRule(sceneMarker, story, processor, rule, s, (Vector)u, consequent, bindings);
/* 149:    */         }
/* 150:    */       }
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   public static ArrayList<Entity> completeAnticipationWithGivenRule(Sequence story, WiredBox processor, Relation rule, Vector<Entity> satisfiedAntecedants, Vector<Entity> unsatisfiedAntecedants, Entity consequent, LList<PairOfEntities> bindings)
/* 155:    */   {
/* 156:175 */     Mark.say(
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
/* 168:    */ 
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:    */ 
/* 174:    */ 
/* 175:    */ 
/* 176:    */ 
/* 177:    */ 
/* 178:    */ 
/* 179:    */ 
/* 180:    */ 
/* 181:    */ 
/* 182:    */ 
/* 183:    */ 
/* 184:    */ 
/* 185:    */ 
/* 186:    */ 
/* 187:    */ 
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:    */ 
/* 194:    */ 
/* 195:    */ 
/* 196:    */ 
/* 197:    */ 
/* 198:    */ 
/* 199:    */ 
/* 200:    */ 
/* 201:    */ 
/* 202:    */ 
/* 203:    */ 
/* 204:    */ 
/* 205:    */ 
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:    */ 
/* 211:    */ 
/* 212:    */ 
/* 213:    */ 
/* 214:    */ 
/* 215:    */ 
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:    */ 
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
/* 232:    */ 
/* 233:    */ 
/* 234:    */ 
/* 235:    */ 
/* 236:    */ 
/* 237:    */ 
/* 238:    */ 
/* 239:    */ 
/* 240:    */ 
/* 241:    */ 
/* 242:    */ 
/* 243:    */ 
/* 244:    */ 
/* 245:    */ 
/* 246:    */ 
/* 247:266 */       new Object[] { Boolean.valueOf(debugAnticipate), "Starting attempt to complete rule", rule.asString() });ArrayList<Entity> implications = new ArrayList();
/* 248:177 */     if (unsatisfiedAntecedants.isEmpty())
/* 249:    */     {
/* 250:178 */       Entity test = Matcher.instantiate(consequent, bindings);
/* 251:    */       
/* 252:    */ 
/* 253:181 */       consequent = test;
/* 254:    */       
/* 255:    */ 
/* 256:184 */       consequent = removeActionReification(consequent);
/* 257:    */     }
/* 258:    */     else
/* 259:    */     {
/* 260:215 */       Entity unsatisfiedAntecedant = (Entity)unsatisfiedAntecedants.elementAt(0);
/* 261:216 */       Mark.say(new Object[] {Boolean.valueOf(debugAnticipate), "Working on another antecedant " + unsatisfiedAntecedant.asString() });
/* 262:    */       LList<PairOfEntities> newBindings;
/* 263:219 */       for (Entity storyElement : copyAndReverse(story.getElements())) {
/* 264:222 */         if (satisfiedAntecedants.contains(storyElement))
/* 265:    */         {
/* 266:223 */           Mark.say(new Object[] {Boolean.valueOf(debug3), "Already used", storyElement.asString() });
/* 267:    */         }
/* 268:    */         else
/* 269:    */         {
/* 270:226 */           newBindings = StandardMatcher.getBasicMatcher().match(unsatisfiedAntecedant, storyElement, bindings);
/* 271:227 */           if (newBindings != null)
/* 272:    */           {
/* 273:231 */             Vector<Entity> s = copy(satisfiedAntecedants);
/* 274:232 */             Vector<Entity> u = copy(unsatisfiedAntecedants);
/* 275:233 */             u.remove(unsatisfiedAntecedant);
/* 276:234 */             s.add(storyElement);
/* 277:235 */             implications.addAll(completeAnticipationWithGivenRule(story, processor, rule, s, u, consequent, newBindings));
/* 278:    */           }
/* 279:    */         }
/* 280:    */       }
/* 281:240 */       if (unsatisfiedAntecedant.hasFeature("not"))
/* 282:    */       {
/* 283:241 */         boolean found = false;
/* 284:242 */         for (Entity storyElement : copyAndReverse(story.getElements()))
/* 285:    */         {
/* 286:246 */           LList<PairOfEntities> newBindings = StandardMatcher.getBasicMatcher()
/* 287:247 */             .matchNegation(unsatisfiedAntecedant, storyElement, bindings);
/* 288:249 */           if (newBindings != null)
/* 289:    */           {
/* 290:250 */             found = true;
/* 291:251 */             break;
/* 292:    */           }
/* 293:    */         }
/* 294:254 */         if (!found)
/* 295:    */         {
/* 296:256 */           Mark.say(new Object[] {Boolean.valueOf(debug), "Successfully matched another antecedant by absence of positive form " + unsatisfiedAntecedant.asString(), bindings });
/* 297:257 */           Object s = copy(satisfiedAntecedants);
/* 298:258 */           Vector<Entity> u = copy(unsatisfiedAntecedants);
/* 299:259 */           ((Vector)s).add(Substitutor.replace(unsatisfiedAntecedant, bindings));
/* 300:260 */           u.remove(unsatisfiedAntecedant);
/* 301:261 */           implications.addAll(completeAnticipationWithGivenRule(story, processor, rule, (Vector)s, u, consequent, bindings));
/* 302:    */         }
/* 303:    */       }
/* 304:    */     }
/* 305:265 */     return implications;
/* 306:    */   }
/* 307:    */   
/* 308:    */   protected static ArrayList<Sequence> processExplanationRule(Sequence story, Vector<Entity> satisfiedAntecedants, Vector<Entity> unsatisfiedAntecedants, LList<PairOfEntities> bindings)
/* 309:    */   {
/* 310:273 */     Mark.say(
/* 311:    */     
/* 312:    */ 
/* 313:    */ 
/* 314:    */ 
/* 315:    */ 
/* 316:    */ 
/* 317:    */ 
/* 318:    */ 
/* 319:    */ 
/* 320:    */ 
/* 321:    */ 
/* 322:    */ 
/* 323:    */ 
/* 324:    */ 
/* 325:    */ 
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
/* 338:    */ 
/* 339:    */ 
/* 340:    */ 
/* 341:    */ 
/* 342:    */ 
/* 343:    */ 
/* 344:    */ 
/* 345:    */ 
/* 346:    */ 
/* 347:    */ 
/* 348:    */ 
/* 349:    */ 
/* 350:    */ 
/* 351:    */ 
/* 352:    */ 
/* 353:    */ 
/* 354:    */ 
/* 355:    */ 
/* 356:    */ 
/* 357:    */ 
/* 358:    */ 
/* 359:    */ 
/* 360:    */ 
/* 361:    */ 
/* 362:    */ 
/* 363:    */ 
/* 364:    */ 
/* 365:    */ 
/* 366:    */ 
/* 367:    */ 
/* 368:    */ 
/* 369:    */ 
/* 370:    */ 
/* 371:    */ 
/* 372:    */ 
/* 373:    */ 
/* 374:    */ 
/* 375:    */ 
/* 376:    */ 
/* 377:    */ 
/* 378:    */ 
/* 379:    */ 
/* 380:    */ 
/* 381:    */ 
/* 382:    */ 
/* 383:    */ 
/* 384:    */ 
/* 385:    */ 
/* 386:    */ 
/* 387:350 */       new Object[] { Boolean.valueOf(debugMay), ">>> Entered ProcessExplanationRule with bindings", bindings });ArrayList<Sequence> result = new ArrayList();
/* 388:275 */     if (unsatisfiedAntecedants.isEmpty())
/* 389:    */     {
/* 390:276 */       Mark.say(new Object[] {Boolean.valueOf(debugMay), ">>> Completed ProcessExplanationRule with bindings", bindings });
/* 391:    */       
/* 392:278 */       Sequence antecedants = new Sequence();
/* 393:279 */       for (Entity satisfiedAntecedant : satisfiedAntecedants) {
/* 394:282 */         antecedants.addElement(satisfiedAntecedant);
/* 395:    */       }
/* 396:287 */       result.add(antecedants);
/* 397:288 */       return result;
/* 398:    */     }
/* 399:292 */     Entity unsatisfiedAntecedant = (Entity)unsatisfiedAntecedants.elementAt(0);
/* 400:293 */     Mark.say(new Object[] {Boolean.valueOf(debugMay), "Story element count " + story.getElements().size() });
/* 401:294 */     for (Entity storyElement : copyAndReverse(story.getElements())) {
/* 402:296 */       if (satisfiedAntecedants.contains(storyElement))
/* 403:    */       {
/* 404:297 */         Mark.say(new Object[] {Boolean.valueOf(debug3), "Already used", storyElement.asString() });
/* 405:    */       }
/* 406:    */       else
/* 407:    */       {
/* 408:301 */         LList<PairOfEntities> testBindings = StandardMatcher.getBasicMatcher().match(unsatisfiedAntecedant, storyElement, bindings);
/* 409:302 */         if (testBindings != null)
/* 410:    */         {
/* 411:305 */           Vector<Entity> s = copy(satisfiedAntecedants);
/* 412:306 */           Vector<Entity> u = copy(unsatisfiedAntecedants);
/* 413:307 */           u.remove(unsatisfiedAntecedant);
/* 414:308 */           s.add(storyElement);
/* 415:309 */           Mark.say(new Object[] {Boolean.valueOf(debugMay), "Satisfied " + unsatisfiedAntecedant.asString() + "; bindings" + testBindings });
/* 416:310 */           ArrayList<Sequence> processResult = processExplanationRule(story, s, u, testBindings);
/* 417:311 */           if (processResult != null)
/* 418:    */           {
/* 419:312 */             result.addAll(processResult);
/* 420:313 */             if (!result.isEmpty()) {
/* 421:314 */               Mark.say(new Object[] {Boolean.valueOf(debugMay), "Matched", storyElement.asString(), result.size() + " ways" });
/* 422:    */             }
/* 423:    */           }
/* 424:    */         }
/* 425:    */       }
/* 426:    */     }
/* 427:321 */     if (unsatisfiedAntecedant.hasFeature("not"))
/* 428:    */     {
/* 429:322 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Unsatisfied antecedant is negated" });
/* 430:323 */       for (Entity storyElement : copyAndReverse(story.getElements()))
/* 431:    */       {
/* 432:327 */         LList<PairOfEntities> newBindings = StandardMatcher.getBasicMatcher()
/* 433:328 */           .matchNegation(unsatisfiedAntecedant, storyElement, bindings);
/* 434:330 */         if (newBindings != null) {
/* 435:331 */           return result;
/* 436:    */         }
/* 437:    */       }
/* 438:335 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Successfully matched another antecedant by absence of positive form " + unsatisfiedAntecedant.asString(), bindings });
/* 439:336 */       Vector<Entity> s = copy(satisfiedAntecedants);
/* 440:337 */       Object u = copy(unsatisfiedAntecedants);
/* 441:338 */       ((Vector)u).remove(unsatisfiedAntecedant);
/* 442:339 */       Entity t = Matcher.instantiate(unsatisfiedAntecedant, bindings);
/* 443:340 */       t.addFeature("assumption");
/* 444:341 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Instatiated   ", unsatisfiedAntecedant.asString() });
/* 445:342 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Producing     ", t.asString() });
/* 446:343 */       Mark.say(new Object[] {Boolean.valueOf(debug), "with bindings ", bindings });
/* 447:    */       
/* 448:345 */       s.add(t);
/* 449:346 */       result.addAll(processExplanationRule(story, s, (Vector)u, bindings));
/* 450:    */     }
/* 451:348 */     return result;
/* 452:    */   }
/* 453:    */   
/* 454:    */   private static Entity removeActionReification(Entity t)
/* 455:    */   {
/* 456:366 */     if (t.entityP()) {
/* 457:367 */       return t;
/* 458:    */     }
/* 459:369 */     if (t.relationP())
/* 460:    */     {
/* 461:370 */       t.setSubject(removeActionReification(t.getSubject()));
/* 462:371 */       t.setObject(removeActionReification(t.getObject()));
/* 463:372 */       return t;
/* 464:    */     }
/* 465:374 */     if (t.sequenceP())
/* 466:    */     {
/* 467:375 */       Vector<Entity> things = t.getElements();
/* 468:376 */       for (int i = 0; i < things.size(); i++) {
/* 469:377 */         things.set(i, removeActionReification((Entity)things.get(i)));
/* 470:    */       }
/* 471:379 */       return t;
/* 472:    */     }
/* 473:382 */     if (t.functionP())
/* 474:    */     {
/* 475:384 */       Function d = (Function)t;
/* 476:385 */       Entity subject = d.getSubject();
/* 477:386 */       if ((d.isAPrimed("appear")) && (subject.isAPrimed("action")))
/* 478:    */       {
/* 479:387 */         Mark.say(new Object[] {Boolean.valueOf(debugDereification), "Triggered dereification of", d.asString() });
/* 480:388 */         return removeActionReification(subject);
/* 481:    */       }
/* 482:391 */       Mark.say(new Object[] {Boolean.valueOf(debugDereification), "Passing on dereification of ", d.asString() });
/* 483:392 */       d.setSubject(removeActionReification(t.getSubject()));
/* 484:393 */       return d;
/* 485:    */     }
/* 486:396 */     System.err.println("Bug in StoryProcessor.removeActionReification; no if-else triggered");
/* 487:397 */     return null;
/* 488:    */   }
/* 489:    */   
/* 490:    */   protected static Vector<Entity> copy(Vector<Entity> things)
/* 491:    */   {
/* 492:401 */     Vector<Entity> newVector = new Vector();
/* 493:402 */     newVector.addAll(things);
/* 494:403 */     return newVector;
/* 495:    */   }
/* 496:    */   
/* 497:    */   protected static Vector<Entity> copyAndReverse(Vector<Entity> things)
/* 498:    */   {
/* 499:407 */     Vector<Entity> newVector = copy(things);
/* 500:408 */     Collections.reverse(newVector);
/* 501:409 */     return newVector;
/* 502:    */   }
/* 503:    */   
/* 504:    */   protected static ArrayList<PairOfEntities> copy(ArrayList<PairOfEntities> pairOfThings)
/* 505:    */   {
/* 506:413 */     ArrayList<PairOfEntities> newList = new ArrayList();
/* 507:414 */     newList.addAll(pairOfThings);
/* 508:415 */     return newList;
/* 509:    */   }
/* 510:    */   
/* 511:    */   protected static void startPredictionWithGivenRule(Entity newStoryElement, int sceneMarker, Sequence story, WiredBox processor, Relation rule)
/* 512:    */   {
/* 513:423 */     Mark.say(
/* 514:    */     
/* 515:    */ 
/* 516:    */ 
/* 517:    */ 
/* 518:    */ 
/* 519:    */ 
/* 520:    */ 
/* 521:    */ 
/* 522:    */ 
/* 523:    */ 
/* 524:    */ 
/* 525:    */ 
/* 526:    */ 
/* 527:    */ 
/* 528:    */ 
/* 529:    */ 
/* 530:    */ 
/* 531:    */ 
/* 532:    */ 
/* 533:    */ 
/* 534:    */ 
/* 535:    */ 
/* 536:    */ 
/* 537:    */ 
/* 538:    */ 
/* 539:    */ 
/* 540:    */ 
/* 541:    */ 
/* 542:    */ 
/* 543:453 */       new Object[] { Boolean.valueOf(debug), "Starting attempt to match element/rule\n", newStoryElement.asString(), "\n", rule.asString() });Entity consequent = rule.getObject();
/* 544:425 */     for (Entity antecedant : rule.getSubject().getElements())
/* 545:    */     {
/* 546:427 */       LList<PairOfEntities> bindings = new LList();
/* 547:428 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Trying\n" + newStoryElement.asString(), "\n", antecedant.asString(), "\n", bindings });
/* 548:429 */       bindings = StandardMatcher.getBasicMatcher().match(antecedant, newStoryElement, bindings);
/* 549:430 */       if (bindings != null)
/* 550:    */       {
/* 551:431 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Successfully matched a rule antecedant " + newStoryElement.asString(), antecedant.asString(), bindings });
/* 552:    */         
/* 553:    */ 
/* 554:    */ 
/* 555:    */ 
/* 556:    */ 
/* 557:    */ 
/* 558:438 */         Vector<Entity> u = copy(((Sequence)rule.getSubject()).getElements());
/* 559:439 */         Vector<Entity> s = new Vector();
/* 560:440 */         u.remove(antecedant);
/* 561:441 */         s.add(newStoryElement);
/* 562:    */         
/* 563:    */ 
/* 564:    */ 
/* 565:    */ 
/* 566:    */ 
/* 567:    */ 
/* 568:448 */         RuleMatcher.completePredictionWithGivenRule(sceneMarker, story, processor, rule, s, u, consequent, bindings);
/* 569:    */       }
/* 570:    */     }
/* 571:    */   }
/* 572:    */   
/* 573:    */   protected static ArrayList<Entity> startAnticipationWithGivenRule(Entity newStoryElement, Sequence story, WiredBox processor, Relation rule)
/* 574:    */   {
/* 575:460 */     Mark.say(
/* 576:    */     
/* 577:    */ 
/* 578:    */ 
/* 579:    */ 
/* 580:    */ 
/* 581:    */ 
/* 582:    */ 
/* 583:    */ 
/* 584:    */ 
/* 585:    */ 
/* 586:    */ 
/* 587:    */ 
/* 588:    */ 
/* 589:    */ 
/* 590:    */ 
/* 591:476 */       new Object[] { Boolean.valueOf(debug), "Starting attempt to match rule", rule.asString() });ArrayList<Entity> implications = new ArrayList();Entity consequent = rule.getObject();
/* 592:463 */     for (Entity antecedant : rule.getSubject().getElements())
/* 593:    */     {
/* 594:464 */       LList<PairOfEntities> bindings = new LList();
/* 595:465 */       bindings = StandardMatcher.getBasicMatcher().match(antecedant, newStoryElement, bindings);
/* 596:466 */       if (bindings != null)
/* 597:    */       {
/* 598:467 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Successfully matched a rule antecedant " + newStoryElement.asString(), antecedant.asString(), bindings });
/* 599:468 */         Vector<Entity> u = copy(((Sequence)rule.getSubject()).getElements());
/* 600:469 */         Vector<Entity> s = new Vector();
/* 601:470 */         u.remove(antecedant);
/* 602:471 */         s.add(newStoryElement);
/* 603:472 */         implications.addAll(RuleMatcher.completeAnticipationWithGivenRule(story, processor, rule, s, u, consequent, bindings));
/* 604:    */       }
/* 605:    */     }
/* 606:475 */     return implications;
/* 607:    */   }
/* 608:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matchers.RuleMatcherFoundation
 * JD-Core Version:    0.7.0.1
 */