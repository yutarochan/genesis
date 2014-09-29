/*   1:    */ package matchers;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Function;
/*   6:    */ import bridge.reps.entities.Relation;
/*   7:    */ import bridge.reps.entities.Sequence;
/*   8:    */ import constants.Markers;
/*   9:    */ import java.io.PrintStream;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Collection;
/*  12:    */ import java.util.HashSet;
/*  13:    */ import java.util.Vector;
/*  14:    */ import minilisp.LList;
/*  15:    */ import utils.Mark;
/*  16:    */ import utils.PairOfEntities;
/*  17:    */ import utils.Timer;
/*  18:    */ 
/*  19:    */ public class Substitutor
/*  20:    */ {
/*  21: 22 */   private static boolean debug = false;
/*  22:    */   
/*  23:    */   public static Entity substitute(Entity current, Entity remembered, Entity consequence)
/*  24:    */   {
/*  25: 25 */     Mark.say(
/*  26:    */     
/*  27:    */ 
/*  28:    */ 
/*  29:    */ 
/*  30:    */ 
/*  31:    */ 
/*  32:    */ 
/*  33:    */ 
/*  34:    */ 
/*  35:    */ 
/*  36:    */ 
/*  37:    */ 
/*  38:    */ 
/*  39: 39 */       new Object[] { Boolean.valueOf(debug), "Substituting!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" });Mark.say(new Object[] { Boolean.valueOf(debug), "Current\n" + current });Mark.say(new Object[] { Boolean.valueOf(debug), "History\n" + remembered });Mark.say(new Object[] { Boolean.valueOf(debug), "Consequence\n" + consequence });LList<PairOfEntities> matches = new LList();matches = findMatches(current, remembered, matches);
/*  40: 31 */     if (matches == null) {
/*  41: 32 */       return null;
/*  42:    */     }
/*  43: 34 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Matches\n" + matches });
/*  44: 35 */     Entity result = replace(consequence, matches);
/*  45: 36 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Result\n" + result });
/*  46: 37 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Now consequence is\n" + consequence });
/*  47: 38 */     return result;
/*  48:    */   }
/*  49:    */   
/*  50:    */   private static LList<PairOfEntities> findMatches(Entity current, Entity remembered)
/*  51:    */   {
/*  52: 42 */     LList<PairOfEntities> matches = new LList();
/*  53: 43 */     return findMatches(current, remembered, matches);
/*  54:    */   }
/*  55:    */   
/*  56:    */   private static LList<PairOfEntities> findMatches(Entity datum, Entity pattern, LList<PairOfEntities> matches)
/*  57:    */   {
/*  58: 47 */     if ((datum.entityP()) && (pattern.entityP()))
/*  59:    */     {
/*  60: 48 */       Entity match = getMatch(datum, matches);
/*  61: 49 */       if (match == null)
/*  62:    */       {
/*  63: 50 */         matches = matches.cons(new PairOfEntities(pattern, datum));
/*  64: 51 */         return matches;
/*  65:    */       }
/*  66: 53 */       if (match == pattern) {
/*  67: 54 */         return matches;
/*  68:    */       }
/*  69: 56 */       return null;
/*  70:    */     }
/*  71: 58 */     if ((datum.functionP()) && (pattern.functionP()))
/*  72:    */     {
/*  73: 59 */       if (!matchTypes(datum, pattern)) {
/*  74: 60 */         return null;
/*  75:    */       }
/*  76: 62 */       Function p = (Function)datum;
/*  77: 63 */       Function d = (Function)pattern;
/*  78: 64 */       if (findMatches(p.getSubject(), d.getSubject(), matches) == null) {
/*  79: 65 */         return null;
/*  80:    */       }
/*  81:    */     }
/*  82:    */     else
/*  83:    */     {
/*  84:    */       Relation d;
/*  85: 68 */       if ((datum.relationP()) && (pattern.relationP()))
/*  86:    */       {
/*  87: 69 */         if (!matchTypes(datum, pattern)) {
/*  88: 70 */           return null;
/*  89:    */         }
/*  90: 72 */         Relation p = (Relation)datum;
/*  91: 73 */         d = (Relation)pattern;
/*  92: 74 */         if ((findMatches(p.getSubject(), d.getSubject(), matches) == null) || (findMatches(p.getObject(), d.getObject(), matches) == null)) {
/*  93: 75 */           return null;
/*  94:    */         }
/*  95:    */       }
/*  96: 80 */       else if ((datum.sequenceP()) && (pattern.sequenceP()))
/*  97:    */       {
/*  98: 81 */         Mark.say(new Object[] {"Trying to match", datum.asStringWithIndexes(), "\n", pattern.asStringWithIndexes() });
/*  99: 82 */         for (Entity patternElement : ((Sequence)datum).getElements()) {
/* 100: 84 */           for (Entity datumElement : ((Sequence)pattern).getElements()) {
/* 101: 85 */             if (matchTypes(patternElement, datumElement))
/* 102:    */             {
/* 103: 86 */               if (findMatches(patternElement, datumElement, matches) == null)
/* 104:    */               {
/* 105: 87 */                 Mark.say(new Object[] {"Failed" });
/* 106: 88 */                 return null;
/* 107:    */               }
/* 108: 90 */               Mark.say(new Object[] {"Won" });
/* 109: 91 */               break;
/* 110:    */             }
/* 111:    */           }
/* 112:    */         }
/* 113:    */       }
/* 114:    */     }
/* 115: 97 */     return matches;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static Entity replace(Entity rememberedConsequence, LList<PairOfEntities> bindings)
/* 119:    */   {
/* 120:101 */     if (rememberedConsequence.entityP())
/* 121:    */     {
/* 122:102 */       Entity match = getReverseMatch(rememberedConsequence, bindings);
/* 123:103 */       if (match != null) {
/* 124:104 */         return match;
/* 125:    */       }
/* 126:106 */       return rememberedConsequence;
/* 127:    */     }
/* 128:108 */     if (rememberedConsequence.functionP()) {
/* 129:109 */       return new Function(rememberedConsequence.getBundle().copy(), replace(((Function)rememberedConsequence).getSubject(), bindings));
/* 130:    */     }
/* 131:111 */     if (rememberedConsequence.relationP()) {
/* 132:112 */       return new Relation(rememberedConsequence.getBundle().copy(), replace(((Relation)rememberedConsequence).getSubject(), bindings), 
/* 133:113 */         replace(((Relation)rememberedConsequence).getObject(), bindings));
/* 134:    */     }
/* 135:117 */     if (rememberedConsequence.sequenceP())
/* 136:    */     {
/* 137:118 */       Sequence result = new Sequence(rememberedConsequence.getBundle().copy());
/* 138:119 */       for (Entity element : ((Sequence)rememberedConsequence).getElements()) {
/* 139:120 */         result.addElement(replace(element, bindings));
/* 140:    */       }
/* 141:122 */       return result;
/* 142:    */     }
/* 143:124 */     return null;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public static Entity replaceWithDereference(Entity pattern, LList<PairOfEntities> bindings, Sequence story)
/* 147:    */   {
/* 148:128 */     if (pattern.entityP())
/* 149:    */     {
/* 150:129 */       Entity match = getReverseMatch(pattern, bindings);
/* 151:130 */       if (match != null) {
/* 152:131 */         return match;
/* 153:    */       }
/* 154:134 */       match = dereferenceActor(pattern, story);
/* 155:135 */       if (match != null) {
/* 156:136 */         return match;
/* 157:    */       }
/* 158:138 */       return pattern;
/* 159:    */     }
/* 160:140 */     if (pattern.functionP()) {
/* 161:141 */       return new Function(pattern.getBundle().copy(), replaceWithDereference(((Function)pattern).getSubject(), bindings, story));
/* 162:    */     }
/* 163:143 */     if (pattern.relationP()) {
/* 164:144 */       return new Relation(pattern.getBundle().copy(), replaceWithDereference(((Relation)pattern).getSubject(), bindings, story), 
/* 165:145 */         replaceWithDereference(((Relation)pattern).getObject(), bindings, story));
/* 166:    */     }
/* 167:149 */     if (pattern.sequenceP())
/* 168:    */     {
/* 169:150 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Sequence to dereference:", pattern.asString() });
/* 170:151 */       Sequence result = new Sequence(pattern.getBundle().copy());
/* 171:152 */       for (Entity element : ((Sequence)pattern).getElements()) {
/* 172:153 */         result.addElement(replaceWithDereference(element, bindings, story));
/* 173:    */       }
/* 174:155 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Bindngs:                ", bindings });
/* 175:156 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Dereferenced sequence:  ", result.asString() });
/* 176:157 */       return result;
/* 177:    */     }
/* 178:159 */     return null;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public static Entity dereferenceActor(Entity actor, Sequence story)
/* 182:    */   {
/* 183:163 */     Collection<Entity> deconstructedStory = deconstruct(story);
/* 184:164 */     if (!actor.entityP("name")) {
/* 185:165 */       return null;
/* 186:    */     }
/* 187:167 */     for (Entity t : deconstructedStory) {
/* 188:168 */       if (t.entityP("name")) {
/* 189:172 */         if (t.getType().equalsIgnoreCase(actor.getType())) {
/* 190:173 */           return t;
/* 191:    */         }
/* 192:    */       }
/* 193:    */     }
/* 194:176 */     return null;
/* 195:    */   }
/* 196:    */   
/* 197:    */   private static boolean matchTypes(Entity thing, Entity base)
/* 198:    */   {
/* 199:180 */     return base.isA(thing.getType());
/* 200:    */   }
/* 201:    */   
/* 202:    */   private static Entity getMatch(Entity thing, LList<PairOfEntities> matches)
/* 203:    */   {
/* 204:184 */     for (Object object : matches)
/* 205:    */     {
/* 206:185 */       PairOfEntities pairOfThings = (PairOfEntities)object;
/* 207:186 */       if (pairOfThings.getDatum() == thing) {
/* 208:187 */         return pairOfThings.getPattern();
/* 209:    */       }
/* 210:    */     }
/* 211:190 */     return null;
/* 212:    */   }
/* 213:    */   
/* 214:    */   private static Entity getReverseMatch(Entity thing, LList<PairOfEntities> matches)
/* 215:    */   {
/* 216:197 */     for (Object object : matches)
/* 217:    */     {
/* 218:198 */       PairOfEntities pairOfThings = (PairOfEntities)object;
/* 219:199 */       if (pairOfThings.getPattern().getType().equals(thing.getType())) {
/* 220:200 */         return pairOfThings.getDatum();
/* 221:    */       }
/* 222:    */     }
/* 223:203 */     return null;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public static Entity dereferenceElement(Entity element, Sequence scene, Sequence story)
/* 227:    */   {
/* 228:221 */     long time = System.currentTimeMillis();
/* 229:222 */     Collection<Entity> deconstructedScene = deconstruct(scene);
/* 230:223 */     Collection<Entity> deconstructedStory = deconstruct(story);
/* 231:    */     
/* 232:    */ 
/* 233:226 */     Entity result = dereference(element, deconstructedScene, deconstructedStory);
/* 234:    */     
/* 235:    */ 
/* 236:229 */     return result;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public static Collection<Entity> deconstruct(Entity current)
/* 240:    */   {
/* 241:233 */     Collection<Entity> things = new HashSet();
/* 242:234 */     deconstruct(current, things);
/* 243:235 */     return things;
/* 244:    */   }
/* 245:    */   
/* 246:    */   private static synchronized void addElementToMap(Entity thing, Collection<Entity> things)
/* 247:    */   {
/* 248:239 */     things.add(thing);
/* 249:    */   }
/* 250:    */   
/* 251:    */   public static void deconstruct(Entity current, Collection<Entity> things)
/* 252:    */   {
/* 253:243 */     Mark.say(
/* 254:    */     
/* 255:    */ 
/* 256:    */ 
/* 257:    */ 
/* 258:    */ 
/* 259:    */ 
/* 260:    */ 
/* 261:    */ 
/* 262:    */ 
/* 263:    */ 
/* 264:    */ 
/* 265:    */ 
/* 266:    */ 
/* 267:    */ 
/* 268:    */ 
/* 269:    */ 
/* 270:    */ 
/* 271:    */ 
/* 272:    */ 
/* 273:    */ 
/* 274:    */ 
/* 275:    */ 
/* 276:    */ 
/* 277:    */ 
/* 278:    */ 
/* 279:    */ 
/* 280:270 */       new Object[] { Boolean.valueOf(debug), "Deconstructing", current.asStringWithIndexes() });
/* 281:244 */     if (current.entityP())
/* 282:    */     {
/* 283:245 */       addElementToMap(current, things);
/* 284:    */     }
/* 285:247 */     else if (current.functionP())
/* 286:    */     {
/* 287:248 */       deconstruct(current.getSubject(), things);
/* 288:249 */       addElementToMap(current, things);
/* 289:    */     }
/* 290:251 */     else if (current.relationP())
/* 291:    */     {
/* 292:252 */       deconstruct(current.getSubject(), things);
/* 293:253 */       deconstruct(current.getObject(), things);
/* 294:254 */       addElementToMap(current, things);
/* 295:    */     }
/* 296:256 */     else if (current.sequenceP())
/* 297:    */     {
/* 298:264 */       for (Entity t : current.getElements()) {
/* 299:265 */         deconstruct(t, things);
/* 300:    */       }
/* 301:268 */       addElementToMap(current, things);
/* 302:    */     }
/* 303:    */   }
/* 304:    */   
/* 305:    */   public static Entity dereference(Entity current)
/* 306:    */   {
/* 307:273 */     return dereference(current, new ArrayList(), new ArrayList());
/* 308:    */   }
/* 309:    */   
/* 310:    */   private static Bundle getBundleClone(Entity t)
/* 311:    */   {
/* 312:277 */     return t.getBundle().getAllClones();
/* 313:    */   }
/* 314:    */   
/* 315:    */   public static synchronized Entity dereference(Entity current, Collection<Entity> deconstructedScene, Collection<Entity> deconstructedStory)
/* 316:    */   {
/* 317:281 */     Mark.say(
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
/* 387:    */ 
/* 388:    */ 
/* 389:    */ 
/* 390:354 */       new Object[] { Boolean.valueOf(debug), "\n\nLooking for dereference of " + current.asStringWithIndexes() });
/* 391:282 */     if (current.relationP())
/* 392:    */     {
/* 393:283 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Matching relation", current.asString() });
/* 394:284 */       dereference(current.getSubject(), deconstructedScene, deconstructedStory);
/* 395:285 */       dereference(current.getObject(), deconstructedScene, deconstructedStory);
/* 396:286 */       Relation result = (Relation)match(0, current, deconstructedScene);
/* 397:287 */       if (result != null)
/* 398:    */       {
/* 399:288 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Found " + result.asStringWithIndexes() });
/* 400:289 */         return result;
/* 401:    */       }
/* 402:291 */       result = new Relation(getBundleClone(current), dereference(current.getSubject(), deconstructedScene, deconstructedStory), 
/* 403:292 */         dereference(current.getObject(), deconstructedScene, deconstructedStory));
/* 404:293 */       addElementToMap(result, deconstructedScene);
/* 405:294 */       addElementToMap(result, deconstructedStory);
/* 406:295 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Found " + result.asStringWithIndexes() });
/* 407:296 */       return result;
/* 408:    */     }
/* 409:298 */     if (current.functionP())
/* 410:    */     {
/* 411:299 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Matching derivative", current.asString() });
/* 412:300 */       dereference(current.getSubject(), deconstructedScene, deconstructedStory);
/* 413:301 */       Function result = (Function)match(0, current, deconstructedScene);
/* 414:302 */       if (result != null)
/* 415:    */       {
/* 416:303 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Found " + result.asStringWithIndexes() });
/* 417:304 */         return result;
/* 418:    */       }
/* 419:306 */       result = new Function(getBundleClone(current), dereference(current.getSubject(), deconstructedScene, deconstructedStory));
/* 420:307 */       addElementToMap(result, deconstructedScene);
/* 421:308 */       addElementToMap(result, deconstructedStory);
/* 422:309 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Found " + result.asStringWithIndexes() });
/* 423:310 */       return result;
/* 424:    */     }
/* 425:312 */     if (current.entityP())
/* 426:    */     {
/* 427:313 */       if (current == Markers.NULL)
/* 428:    */       {
/* 429:314 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Found NULL " + current.asStringWithIndexes() });
/* 430:315 */         return current;
/* 431:    */       }
/* 432:318 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Matching thing", current.asString() });
/* 433:319 */       Entity result = match(0, current, deconstructedStory);
/* 434:320 */       if (result != null)
/* 435:    */       {
/* 436:321 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Found thing " + result.asStringWithIndexes() });
/* 437:322 */         return result;
/* 438:    */       }
/* 439:324 */       result = new Entity(getBundleClone(current));
/* 440:325 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Created thing " + result.asStringWithIndexes() });
/* 441:326 */       addElementToMap(result, deconstructedScene);
/* 442:327 */       addElementToMap(result, deconstructedStory);
/* 443:328 */       return result;
/* 444:    */     }
/* 445:330 */     if (current.sequenceP())
/* 446:    */     {
/* 447:331 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Matching sequence", current.asStringWithIndexes() });
/* 448:332 */       for (Entity t : current.getElements()) {
/* 449:333 */         dereference(t, deconstructedScene, deconstructedStory);
/* 450:    */       }
/* 451:335 */       Sequence result = (Sequence)match(0, current, deconstructedStory);
/* 452:336 */       if (result != null)
/* 453:    */       {
/* 454:337 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Found " + result.asStringWithIndexes() });
/* 455:338 */         return result;
/* 456:    */       }
/* 457:341 */       result = new Sequence(getBundleClone(current));
/* 458:342 */       for (Entity t : current.getElements()) {
/* 459:343 */         result.addElement(dereference(t, deconstructedScene, deconstructedStory));
/* 460:    */       }
/* 461:345 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Constructed " + result.asStringWithIndexes() });
/* 462:    */       
/* 463:347 */       addElementToMap(result, deconstructedScene);
/* 464:348 */       addElementToMap(result, deconstructedStory);
/* 465:    */       
/* 466:350 */       return result;
/* 467:    */     }
/* 468:353 */     return current;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public static boolean matchTypesAndSign(Entity t1, Entity t2)
/* 472:    */   {
/* 473:    */     try
/* 474:    */     {
/* 475:358 */       if (!t1.getType().equalsIgnoreCase(t2.getType())) {
/* 476:359 */         return false;
/* 477:    */       }
/* 478:361 */       boolean t1Not = t1.hasFeature("not");
/* 479:362 */       boolean t2Not = t2.hasFeature("not");
/* 480:363 */       if (t1Not != t2Not) {
/* 481:364 */         return false;
/* 482:    */       }
/* 483:    */     }
/* 484:    */     catch (Exception e)
/* 485:    */     {
/* 486:368 */       System.err.println("Blew out in matchTypesAndSign with " + t1 + ", " + t2);
/* 487:369 */       e.printStackTrace();
/* 488:    */     }
/* 489:371 */     return true;
/* 490:    */   }
/* 491:    */   
/* 492:    */   private static Entity match(int level, Entity thing, Collection<Entity> candidates)
/* 493:    */   {
/* 494:478 */     if (thing == null) {
/* 495:480 */       return null;
/* 496:    */     }
/* 497:482 */     if (level > 10) {
/* 498:483 */       Mark.say(new Object[] {"Recursing through", Integer.valueOf(level), thing.asString() });
/* 499:    */     }
/* 500:485 */     if (level > 15)
/* 501:    */     {
/* 502:486 */       Mark.say(new Object[] {"Appear to have recursed through", Integer.valueOf(level), thing.asString() });
/* 503:487 */       throw new RuntimeException("Ugh!");
/* 504:    */     }
/* 505:489 */     if (level > 40)
/* 506:    */     {
/* 507:490 */       Mark.say(new Object[] {"Quiting", thing.asString() });
/* 508:491 */       return null;
/* 509:    */     }
/* 510:493 */     for (Entity candidate : candidates) {
/* 511:494 */       if (candidate != null)
/* 512:    */       {
/* 513:498 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Working to match", thing.asString(), "|", candidate.asString() });
/* 514:499 */         if ((thing.relationP()) && (candidate.relationP()))
/* 515:    */         {
/* 516:500 */           if (matchTypesAndSign(thing, candidate))
/* 517:    */           {
/* 518:503 */             Entity subjectT = match(level + 1, thing.getSubject(), candidates);
/* 519:504 */             Entity subjectC = candidate.getSubject();
/* 520:505 */             if (subjectT == subjectC)
/* 521:    */             {
/* 522:508 */               Entity objectT = match(level + 1, thing.getObject(), candidates);
/* 523:509 */               Entity objectC = candidate.getObject();
/* 524:510 */               if (objectT == objectC) {
/* 525:511 */                 return candidate;
/* 526:    */               }
/* 527:    */             }
/* 528:    */           }
/* 529:    */         }
/* 530:514 */         else if ((thing.functionP()) && (candidate.functionP()))
/* 531:    */         {
/* 532:515 */           if (matchTypesAndSign(thing, candidate))
/* 533:    */           {
/* 534:518 */             Entity subjectT = match(level + 1, thing.getSubject(), candidates);
/* 535:519 */             Entity subjectC = candidate.getSubject();
/* 536:520 */             if ((subjectT != null) && (subjectT == subjectC)) {
/* 537:521 */               return candidate;
/* 538:    */             }
/* 539:    */           }
/* 540:    */         }
/* 541:524 */         else if ((thing.entityP()) && (candidate.entityP()))
/* 542:    */         {
/* 543:525 */           if (matchTypesAndSign(thing, candidate))
/* 544:    */           {
/* 545:528 */             if (thing == candidate) {
/* 546:529 */               return candidate;
/* 547:    */             }
/* 548:533 */             if ((sameOwners(thing, candidate)) && (consistenFeatures(thing, candidate))) {
/* 549:534 */               return candidate;
/* 550:    */             }
/* 551:    */           }
/* 552:    */         }
/* 553:545 */         else if ((thing.sequenceP()) && (candidate.sequenceP()))
/* 554:    */         {
/* 555:546 */           long sequenceTime = System.currentTimeMillis();
/* 556:547 */           if (matchTypesAndSign(thing, candidate))
/* 557:    */           {
/* 558:552 */             Vector<Entity> thingElements = ((Sequence)thing).getElements();
/* 559:553 */             Vector<Entity> candidateElements = ((Sequence)candidate).getElements();
/* 560:556 */             if (thingElements.size() == candidateElements.size())
/* 561:    */             {
/* 562:569 */               boolean ok = true;
/* 563:571 */               for (Entity t : thingElements)
/* 564:    */               {
/* 565:572 */                 Entity match = match(level + 1, t, candidates);
/* 566:573 */                 if ((match == null) || (!candidateElements.contains(match)))
/* 567:    */                 {
/* 568:574 */                   ok = false;
/* 569:575 */                   break;
/* 570:    */                 }
/* 571:    */               }
/* 572:578 */               Timer.laptime(false, "Handled sequence in ", sequenceTime);
/* 573:580 */               if (ok) {
/* 574:581 */                 return candidate;
/* 575:    */               }
/* 576:    */             }
/* 577:    */           }
/* 578:    */         }
/* 579:    */       }
/* 580:    */     }
/* 581:585 */     return null;
/* 582:    */   }
/* 583:    */   
/* 584:    */   private static Entity matchRoles(int level, Entity result, Vector<Entity> thingElements, Vector<Entity> candidateElements, Collection<Entity> candidates)
/* 585:    */   {
/* 586:589 */     for (Entity element : thingElements)
/* 587:    */     {
/* 588:590 */       boolean match = false;
/* 589:591 */       for (Entity candidate : candidateElements) {
/* 590:593 */         if ((element.getType().equals(candidate.getType())) && 
/* 591:594 */           (match(level + 1, element, candidates) == candidate)) {
/* 592:596 */           match = true;
/* 593:    */         }
/* 594:    */       }
/* 595:601 */       if (!match) {
/* 596:602 */         return null;
/* 597:    */       }
/* 598:    */     }
/* 599:605 */     return result;
/* 600:    */   }
/* 601:    */   
/* 602:    */   private static boolean consistenFeatures(Entity thing, Entity element)
/* 603:    */   {
/* 604:609 */     Vector<String> thingFeatures = thing.getThread("feature");
/* 605:610 */     Vector<String> elementFeatures = element.getThread("feature");
/* 606:611 */     if ((thingFeatures == null) && (elementFeatures == null)) {
/* 607:612 */       return true;
/* 608:    */     }
/* 609:614 */     if ((thingFeatures != null) && (elementFeatures == null)) {
/* 610:615 */       return false;
/* 611:    */     }
/* 612:617 */     if ((thingFeatures == null) && (elementFeatures != null)) {
/* 613:618 */       return false;
/* 614:    */     }
/* 615:620 */     Vector<String> larger = thingFeatures;
/* 616:621 */     Vector<String> smaller = elementFeatures;
/* 617:622 */     if (larger.size() < smaller.size())
/* 618:    */     {
/* 619:623 */       larger = elementFeatures;
/* 620:624 */       smaller = thingFeatures;
/* 621:    */     }
/* 622:626 */     for (String x : smaller) {
/* 623:627 */       if (!larger.contains(x)) {
/* 624:628 */         return false;
/* 625:    */       }
/* 626:    */     }
/* 627:631 */     return true;
/* 628:    */   }
/* 629:    */   
/* 630:    */   private static boolean sameOwners(Entity thing, Entity element)
/* 631:    */   {
/* 632:637 */     Vector<String> thingOwners = thing.getThread("owners");
/* 633:638 */     Vector<String> elementOwners = element.getThread("owners");
/* 634:639 */     if ((thingOwners == null) && (elementOwners == null)) {
/* 635:640 */       return true;
/* 636:    */     }
/* 637:642 */     if ((thingOwners != null) && (elementOwners == null)) {
/* 638:643 */       return false;
/* 639:    */     }
/* 640:645 */     if ((thingOwners == null) && (elementOwners != null)) {
/* 641:646 */       return false;
/* 642:    */     }
/* 643:648 */     if (thingOwners.size() != elementOwners.size()) {
/* 644:649 */       return false;
/* 645:    */     }
/* 646:651 */     for (String owner : thingOwners) {
/* 647:652 */       if (!elementOwners.contains(owner)) {
/* 648:653 */         return false;
/* 649:    */       }
/* 650:    */     }
/* 651:656 */     return true;
/* 652:    */   }
/* 653:    */   
/* 654:    */   private static ArrayList<Entity> extractThings(Entity current, ArrayList<Entity> things)
/* 655:    */   {
/* 656:660 */     if (current.entityP())
/* 657:    */     {
/* 658:661 */       if (!things.contains(current)) {
/* 659:662 */         things.add(current);
/* 660:    */       }
/* 661:664 */       return things;
/* 662:    */     }
/* 663:666 */     if (current.functionP())
/* 664:    */     {
/* 665:667 */       Function p = (Function)current;
/* 666:668 */       return extractThings(p.getSubject(), things);
/* 667:    */     }
/* 668:670 */     if (current.relationP())
/* 669:    */     {
/* 670:671 */       Relation p = (Relation)current;
/* 671:672 */       extractThings(p.getSubject(), things);
/* 672:673 */       extractThings(p.getObject(), things);
/* 673:674 */       return things;
/* 674:    */     }
/* 675:676 */     if (current.sequenceP())
/* 676:    */     {
/* 677:677 */       for (Entity patternElement : ((Sequence)current).getElements()) {
/* 678:678 */         extractThings(patternElement, things);
/* 679:    */       }
/* 680:680 */       return things;
/* 681:    */     }
/* 682:682 */     return things;
/* 683:    */   }
/* 684:    */   
/* 685:    */   private static LList<PairOfEntities> extractPairs(Entity pattern, ArrayList<Entity> things, LList<PairOfEntities> pairOfThings)
/* 686:    */   {
/* 687:686 */     if (pattern.entityP())
/* 688:    */     {
/* 689:687 */       if (getReverseMatch(pattern, pairOfThings) == null)
/* 690:    */       {
/* 691:688 */         Entity datum = findMatchingThing(pattern, things);
/* 692:689 */         if (datum != null)
/* 693:    */         {
/* 694:690 */           things.remove(datum);
/* 695:691 */           pairOfThings = pairOfThings.cons(new PairOfEntities(pattern, datum), pairOfThings);
/* 696:    */         }
/* 697:    */       }
/* 698:694 */       return pairOfThings;
/* 699:    */     }
/* 700:696 */     if (pattern.functionP())
/* 701:    */     {
/* 702:697 */       Function p = (Function)pattern;
/* 703:698 */       return extractPairs(p.getSubject(), things, pairOfThings);
/* 704:    */     }
/* 705:700 */     if (pattern.relationP())
/* 706:    */     {
/* 707:701 */       Relation p = (Relation)pattern;
/* 708:702 */       extractPairs(p.getSubject(), things, pairOfThings);
/* 709:703 */       extractPairs(p.getObject(), things, pairOfThings);
/* 710:704 */       return pairOfThings;
/* 711:    */     }
/* 712:706 */     if (pattern.sequenceP())
/* 713:    */     {
/* 714:707 */       for (Entity patternElement : ((Sequence)pattern).getElements()) {
/* 715:708 */         extractPairs(patternElement, things, pairOfThings);
/* 716:    */       }
/* 717:710 */       return pairOfThings;
/* 718:    */     }
/* 719:712 */     return pairOfThings;
/* 720:    */   }
/* 721:    */   
/* 722:    */   private static Entity findMatchingThing(Entity current, ArrayList<Entity> things)
/* 723:    */   {
/* 724:716 */     for (Entity match : things) {
/* 725:717 */       if (match.isAPrimed(current.getType())) {
/* 726:718 */         return match;
/* 727:    */       }
/* 728:    */     }
/* 729:721 */     return null;
/* 730:    */   }
/* 731:    */   
/* 732:    */   public static Entity reconcile(Entity cause, Entity result)
/* 733:    */   {
/* 734:725 */     ArrayList<Entity> things = extractThings(cause, new ArrayList());
/* 735:726 */     LList<PairOfEntities> pairOfThings = extractPairs(result, things, new LList());
/* 736:727 */     return replace(result, pairOfThings);
/* 737:    */   }
/* 738:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matchers.Substitutor
 * JD-Core Version:    0.7.0.1
 */