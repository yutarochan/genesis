/*   1:    */ package matchers.original;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Function;
/*   6:    */ import bridge.reps.entities.Matcher;
/*   7:    */ import bridge.reps.entities.Relation;
/*   8:    */ import bridge.reps.entities.Sequence;
/*   9:    */ import bridge.reps.entities.Thread;
/*  10:    */ import java.util.Collection;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Vector;
/*  14:    */ import matchers.Substitutor;
/*  15:    */ import minilisp.LList;
/*  16:    */ import tools.Getters;
/*  17:    */ import translator.NewRuleSet;
/*  18:    */ import translator.Translator;
/*  19:    */ import utils.Mark;
/*  20:    */ import utils.PairOfEntities;
/*  21:    */ 
/*  22:    */ public class BasicMatcherOriginal
/*  23:    */ {
/*  24: 21 */   private boolean debugSpecial = false;
/*  25: 23 */   private boolean debugReturn = false;
/*  26: 25 */   public static boolean debug = false;
/*  27: 27 */   public static boolean debugTypeMatch = false;
/*  28: 29 */   private boolean debugAlreadyDone = false;
/*  29: 31 */   private boolean specialMatch = false;
/*  30:    */   private static BasicMatcherOriginal matcher;
/*  31:    */   
/*  32:    */   public LList<PairOfEntities> matchAnyPart(Entity pattern, Entity datum)
/*  33:    */   {
/*  34: 37 */     if ((datum == null) || (pattern == null)) {
/*  35: 38 */       return null;
/*  36:    */     }
/*  37: 41 */     LList<PairOfEntities> match = match(pattern, datum);
/*  38: 42 */     if (match != null) {
/*  39: 43 */       return match;
/*  40:    */     }
/*  41: 47 */     if ((pattern.entityP()) || (datum.entityP())) {
/*  42: 48 */       return null;
/*  43:    */     }
/*  44: 51 */     if (datum.sequenceP())
/*  45:    */     {
/*  46: 52 */       for (Entity t : datum.getElements())
/*  47:    */       {
/*  48: 53 */         match = matchAnyPart(pattern, t);
/*  49: 54 */         if (match != null) {
/*  50: 55 */           return match;
/*  51:    */         }
/*  52:    */       }
/*  53: 58 */       return null;
/*  54:    */     }
/*  55: 61 */     match = matchAnyPart(pattern, datum.getSubject());
/*  56: 62 */     if (match != null) {
/*  57: 63 */       return match;
/*  58:    */     }
/*  59: 66 */     match = matchAnyPart(pattern, datum.getObject());
/*  60: 67 */     if (match != null) {
/*  61: 68 */       return match;
/*  62:    */     }
/*  63: 70 */     return null;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean matchStructures(Entity pattern, Entity datum)
/*  67:    */   {
/*  68: 74 */     if ((pattern.entityP()) && (datum.entityP())) {
/*  69: 75 */       return true;
/*  70:    */     }
/*  71: 77 */     if ((pattern.functionP()) && (datum.functionP())) {
/*  72: 78 */       return matchStructures(pattern.getSubject(), datum.getSubject());
/*  73:    */     }
/*  74: 80 */     if ((pattern.relationP()) && (datum.relationP())) {
/*  75: 81 */       return (matchStructures(pattern.getSubject(), datum.getSubject())) && (matchStructures(pattern.getObject(), datum.getObject()));
/*  76:    */     }
/*  77: 83 */     if ((pattern.sequenceP()) && (datum.sequenceP()))
/*  78:    */     {
/*  79: 84 */       for (Entity p : pattern.getElements())
/*  80:    */       {
/*  81: 85 */         boolean test = false;
/*  82: 86 */         for (Entity d : datum.getElements())
/*  83:    */         {
/*  84: 87 */           boolean result = matchStructures(p, d);
/*  85: 88 */           if (result) {
/*  86: 91 */             test = true;
/*  87:    */           }
/*  88:    */         }
/*  89: 96 */         if (!test) {
/*  90: 97 */           return false;
/*  91:    */         }
/*  92:    */       }
/*  93:101 */       return true;
/*  94:    */     }
/*  95:103 */     return false;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public LList<PairOfEntities> matchRuleToInstantiation(Entity rule, Entity instantiation)
/*  99:    */   {
/* 100:116 */     BasicMatcherOriginal matcher = getBasicMatcher();
/* 101:117 */     LList<PairOfEntities> bindings = matcher.match(rule.getObject(), instantiation.getObject());
/* 102:118 */     if (bindings == null) {
/* 103:119 */       return null;
/* 104:    */     }
/* 105:121 */     bindings = matcher.match(rule.getSubject(), instantiation.getSubject(), bindings);
/* 106:122 */     return bindings;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public static BasicMatcherOriginal getBasicMatcher()
/* 110:    */   {
/* 111:126 */     if (matcher == null) {
/* 112:127 */       matcher = new BasicMatcherOriginal();
/* 113:    */     }
/* 114:129 */     return matcher;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public LList<PairOfEntities> specialMatch(Entity current, Entity remembered)
/* 118:    */   {
/* 119:    */     try
/* 120:    */     {
/* 121:134 */       this.specialMatch = true;
/* 122:135 */       return match(current, remembered);
/* 123:    */     }
/* 124:    */     catch (Exception e)
/* 125:    */     {
/* 126:138 */       e.printStackTrace();
/* 127:    */     }
/* 128:    */     finally
/* 129:    */     {
/* 130:141 */       this.specialMatch = false;
/* 131:    */     }
/* 132:143 */     return null;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public LList<PairOfEntities> match(Entity pattern, Entity current)
/* 136:    */   {
/* 137:148 */     LList<PairOfEntities> matches = new LList();
/* 138:149 */     LList<PairOfEntities> result = match(pattern, current, matches);
/* 139:150 */     return result;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public LList<PairOfEntities> matchAll(Entity pattern, Entity current)
/* 143:    */   {
/* 144:155 */     LList<PairOfEntities> matches = new LList();
/* 145:156 */     LList<PairOfEntities> result = matchAll(pattern, current, matches);
/* 146:157 */     return result;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public LList<PairOfEntities> matchNegation(Entity pattern, Entity remembered, LList<PairOfEntities> matches)
/* 150:    */   {
/* 151:161 */     return matchAux(remembered, pattern, matches, true, false);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public LList<PairOfEntities> match(Entity pattern, Entity datum, LList<PairOfEntities> bindings)
/* 155:    */   {
/* 156:165 */     return matchAux(pattern, datum, bindings, false, false);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public LList<PairOfEntities> matchAll(Entity pattern, Entity datum, LList<PairOfEntities> bindings)
/* 160:    */   {
/* 161:169 */     return matchAux(pattern, datum, bindings, false, true);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public LList<PairOfEntities> matchAux(Entity pattern, Entity current, LList<PairOfEntities> matches)
/* 165:    */   {
/* 166:188 */     return matchAux(pattern, current, matches, false, false);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public boolean allowMatch(LList<PairOfEntities> matches, Entity pattern, Entity datum)
/* 170:    */   {
/* 171:201 */     return false;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public LList<PairOfEntities> matchAux(Entity pattern, Entity datum, LList<PairOfEntities> matches, boolean invertSign, boolean includeAll)
/* 175:    */   {
/* 176:210 */     boolean debugAuxMatch = false;
/* 177:211 */     boolean debugAction = false;
/* 178:212 */     boolean debugSequence = false;
/* 179:213 */     if ((matches == null) || (pattern == null) || (datum == null))
/* 180:    */     {
/* 181:214 */       Mark.say(new Object[] {Boolean.valueOf(debugAuxMatch), "Something is null, cannot match" });
/* 182:215 */       return matches;
/* 183:    */     }
/* 184:    */     try
/* 185:    */     {
/* 186:219 */       Mark.say(new Object[] {Boolean.valueOf(this.debugReturn), "Attempting to match with sign " + invertSign, "\n", pattern.asString(), "\n", datum.asString(), "\n", matches });
/* 187:    */     }
/* 188:    */     catch (Exception e)
/* 189:    */     {
/* 190:222 */       Mark.say(new Object[] {"Blew out in BasicMatcher.matchAux" });
/* 191:    */     }
/* 192:236 */     if ((pattern.relationP("perform")) && (Getters.getObject(pattern) != null) && (datum.getSubject().isNotA("you")))
/* 193:    */     {
/* 194:238 */       Entity pActor = pattern.getSubject();
/* 195:239 */       Entity pAction = Getters.getObject(pattern);
/* 196:240 */       if (pAction.isA("action"))
/* 197:    */       {
/* 198:242 */         Mark.say(new Object[] {Boolean.valueOf(debugAction), "Perform trap, working on\n", pattern.asString(), "\n", datum.asStringWithIndexes(), "\n", matches });
/* 199:243 */         Entity dAction = getBinding(pAction, matches);
/* 200:244 */         Entity dActor = getBinding(pActor, matches);
/* 201:245 */         if (dAction != null)
/* 202:    */         {
/* 203:246 */           Mark.say(new Object[] {Boolean.valueOf(debugAction), "Triggered on A" });
/* 204:248 */           if (dAction == datum)
/* 205:    */           {
/* 206:249 */             Mark.say(new Object[] {Boolean.valueOf(debugAction), "Action succeeded at P1", matches });
/* 207:250 */             return matches;
/* 208:    */           }
/* 209:252 */           return null;
/* 210:    */         }
/* 211:254 */         if (dActor != null)
/* 212:    */         {
/* 213:255 */           Mark.say(new Object[] {Boolean.valueOf(debugAction), "Triggered on B, pattern/datum\n", pattern.asString(), "\n", datum.asString() });
/* 214:257 */           if ((datum.relationP("action")) && (datum.getSubject().entityP()))
/* 215:    */           {
/* 216:258 */             if (dActor == datum.getSubject())
/* 217:    */             {
/* 218:260 */               Mark.say(new Object[] {Boolean.valueOf(debugAction), "Action succeeded at P2 matching", pAction.asString(), "to", datum.asString() });
/* 219:261 */               matches = matches.cons(new PairOfEntities(pAction, datum));
/* 220:262 */               return matches;
/* 221:    */             }
/* 222:    */           }
/* 223:265 */           else if ((datum.functionP("action")) && (datum.functionP("transition"))) {
/* 224:267 */             if (dActor == datum.getSubject().getSubject())
/* 225:    */             {
/* 226:268 */               matches = matches.cons(new PairOfEntities(pAction, datum));
/* 227:269 */               return matches;
/* 228:    */             }
/* 229:    */           }
/* 230:    */         }
/* 231:    */         else
/* 232:    */         {
/* 233:274 */           if ((datum.relationP("action")) && (datum.getSubject().entityP()))
/* 234:    */           {
/* 235:275 */             Mark.say(new Object[] {Boolean.valueOf(debugAction), "Triggered on C" });
/* 236:276 */             matches = matches.cons(new PairOfEntities(pAction, datum));
/* 237:277 */             matches = matches.cons(new PairOfEntities(pActor, datum.getSubject()));
/* 238:278 */             Mark.say(new Object[] {Boolean.valueOf(debugAction), "Action succeeded at P3 matching", pAction.asString(), "to", datum.asString() });
/* 239:279 */             return matches;
/* 240:    */           }
/* 241:282 */           if ((datum.functionP("action")) && (datum.functionP("transition")))
/* 242:    */           {
/* 243:283 */             Mark.say(new Object[] {"Bingo, found case never tested!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" });
/* 244:284 */             Mark.say(new Object[] {Boolean.valueOf(debugAction), "Triggered on D" });
/* 245:285 */             matches = matches.cons(new PairOfEntities(pAction, datum));
/* 246:286 */             matches = matches.cons(new PairOfEntities(pActor, datum.getSubject()));
/* 247:287 */             Mark.say(new Object[] {Boolean.valueOf(debugAction), "Action succeeded at P4 matching", pAction.asString(), "to", datum.asString() });
/* 248:288 */             return matches;
/* 249:    */           }
/* 250:    */         }
/* 251:290 */         Mark.say(new Object[] {Boolean.valueOf(debugAction), "No luck at all on pattern/datum\n", pattern.asString(), "\n", datum.asString() });
/* 252:    */         
/* 253:292 */         return null;
/* 254:    */       }
/* 255:294 */       return null;
/* 256:    */     }
/* 257:296 */     if ((pattern.entityP()) && (pattern.isA("action")) && (datum.isA("action")) && (pattern.isNotA("position")) && 
/* 258:297 */       (datum.isNotA("position")))
/* 259:    */     {
/* 260:299 */       Mark.say(new Object[] {Boolean.valueOf(debugAction), "Special case trap for actions" });
/* 261:300 */       Mark.say(new Object[] {Boolean.valueOf(debugAction), "Pattern:", pattern.asStringWithIndexes() });
/* 262:301 */       Mark.say(new Object[] {Boolean.valueOf(debugAction), "Datum:  ", datum.asStringWithIndexes() });
/* 263:302 */       Entity match = getBinding(pattern, matches);
/* 264:303 */       if (match == null)
/* 265:    */       {
/* 266:304 */         if (allowMatch(matches, pattern, datum))
/* 267:    */         {
/* 268:305 */           matches = matches.cons(new PairOfEntities(pattern, datum));
/* 269:306 */           Mark.say(new Object[] {Boolean.valueOf(debugAction), "Action succeeded at A1", matches });
/* 270:307 */           return matches;
/* 271:    */         }
/* 272:312 */         matches = matches.cons(new PairOfEntities(pattern, datum));
/* 273:313 */         Mark.say(new Object[] {Boolean.valueOf(debugAction), "Action succeded at A2, " + matches });
/* 274:314 */         return matches;
/* 275:    */       }
/* 276:317 */       if (match == datum)
/* 277:    */       {
/* 278:318 */         Mark.say(new Object[] {Boolean.valueOf(debugAction), "Action succeeded at A3", matches });
/* 279:319 */         return matches;
/* 280:    */       }
/* 281:321 */       Mark.say(new Object[] {Boolean.valueOf(debugAction), "Action failed at A4", "null" });
/* 282:322 */       return null;
/* 283:    */     }
/* 284:325 */     if (pattern.entityP("anything"))
/* 285:    */     {
/* 286:326 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Special case trap #1 for elements embedded in appear" });
/* 287:327 */       Entity match = getBinding(pattern, matches);
/* 288:328 */       if (match == null)
/* 289:    */       {
/* 290:329 */         if (allowMatch(matches, datum, pattern))
/* 291:    */         {
/* 292:330 */           matches = matches.cons(new PairOfEntities(pattern, datum));
/* 293:331 */           Mark.say(new Object[] {Boolean.valueOf(this.debugReturn), "D", matches });
/* 294:332 */           return matches;
/* 295:    */         }
/* 296:337 */         matches = matches.cons(new PairOfEntities(pattern, datum));
/* 297:338 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Succeded at E.1, " + matches });
/* 298:339 */         return matches;
/* 299:    */       }
/* 300:342 */       if (match == datum)
/* 301:    */       {
/* 302:343 */         Mark.say(new Object[] {Boolean.valueOf(this.debugReturn), "E", matches });
/* 303:344 */         return matches;
/* 304:    */       }
/* 305:346 */       Mark.say(new Object[] {Boolean.valueOf(this.debugReturn), "F", "null" });
/* 306:347 */       return null;
/* 307:    */     }
/* 308:365 */     if ((pattern.functionP()) && (pattern.isA("appear")) && (pattern.getSubject().getType().equals("action")) && 
/* 309:366 */       (datum.isAPrimed("action")))
/* 310:    */     {
/* 311:367 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Special case trap #2 for actions embedded in appear" });
/* 312:368 */       boolean hasNotFeature = pattern.hasFeature("not");
/* 313:369 */       Entity subject = pattern.getSubject();
/* 314:370 */       if ((invertSign) && (hasNotFeature))
/* 315:    */       {
/* 316:374 */         matches = matchAux(subject, datum, matches, false, includeAll);
/* 317:375 */         Mark.say(new Object[] {Boolean.valueOf(this.debugReturn), "G", matches });
/* 318:376 */         return matches;
/* 319:    */       }
/* 320:378 */       if (hasNotFeature)
/* 321:    */       {
/* 322:381 */         matches = matchAux(subject, datum, matches, true, includeAll);
/* 323:382 */         Mark.say(new Object[] {Boolean.valueOf(this.debugReturn), "H", matches });
/* 324:383 */         return matches;
/* 325:    */       }
/* 326:387 */       matches = matchAux(subject, datum, matches, false, includeAll);
/* 327:388 */       Mark.say(new Object[] {Boolean.valueOf(this.debugReturn), "I", matches });
/* 328:389 */       return matches;
/* 329:    */     }
/* 330:392 */     if (!matchTypes(pattern, datum, invertSign))
/* 331:    */     {
/* 332:393 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Failed at D", pattern.asString(), datum.asString() });
/* 333:394 */       Mark.say(new Object[] {Boolean.valueOf(this.debugReturn), "K", "null" });
/* 334:395 */       return null;
/* 335:    */     }
/* 336:397 */     if ((datum.isAPrimed("action")) && (pattern.entityP()) && (pattern.isAPrimed("action")))
/* 337:    */     {
/* 338:398 */       Mark.say(new Object[] {Boolean.valueOf(debug), "X.0: Looking for match of " + pattern.asString() });
/* 339:399 */       Entity match = getBinding(pattern, matches);
/* 340:400 */       if (match == null)
/* 341:    */       {
/* 342:401 */         if (allowMatch(matches, datum, pattern))
/* 343:    */         {
/* 344:402 */           matches = matches.cons(new PairOfEntities(pattern, datum));
/* 345:    */           
/* 346:404 */           Mark.say(new Object[] {Boolean.valueOf(debug), "X.1: Remembering match of " + datum.asString() + " with " + pattern.asString() });
/* 347:405 */           Mark.say(new Object[] {Boolean.valueOf(debug), "New bindings: " + matches });
/* 348:406 */           Mark.say(new Object[] {Boolean.valueOf(this.debugReturn), "L", matches });
/* 349:407 */           return matches;
/* 350:    */         }
/* 351:410 */         return null;
/* 352:    */       }
/* 353:413 */       if (match == datum)
/* 354:    */       {
/* 355:414 */         Mark.say(new Object[] {Boolean.valueOf(debug), "X.2: Old match of " + datum.asString() + " with " + pattern.asString() });
/* 356:415 */         Mark.say(new Object[] {Boolean.valueOf(this.debugReturn), "M", matches });
/* 357:416 */         return matches;
/* 358:    */       }
/* 359:418 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Failed at B to match " + match.getName() + " " + datum.getName() });
/* 360:419 */       Mark.say(new Object[] {Boolean.valueOf(this.debugReturn), "N", "null" });
/* 361:420 */       return null;
/* 362:    */     }
/* 363:428 */     if ((datum.entityP()) && (pattern.entityP()))
/* 364:    */     {
/* 365:429 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Working in part C" });
/* 366:430 */       Entity match = getBinding(pattern, matches);
/* 367:431 */       if (match == null)
/* 368:    */       {
/* 369:432 */         if (allowMatch(matches, datum, pattern))
/* 370:    */         {
/* 371:433 */           matches = matches.cons(new PairOfEntities(pattern, datum));
/* 372:434 */           Mark.say(new Object[] {Boolean.valueOf(debug), "Succeded at C.1, " + matches });
/* 373:435 */           return matches;
/* 374:    */         }
/* 375:440 */         matches = matches.cons(new PairOfEntities(pattern, datum));
/* 376:441 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Succeded at C.1, " + matches });
/* 377:442 */         return matches;
/* 378:    */       }
/* 379:446 */       if (match == datum)
/* 380:    */       {
/* 381:447 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Succeded at C.2, " + matches });
/* 382:448 */         Mark.say(new Object[] {Boolean.valueOf(this.debugReturn), "Q", matches });
/* 383:449 */         return matches;
/* 384:    */       }
/* 385:453 */       return null;
/* 386:    */     }
/* 387:460 */     if ((datum.functionP()) && (pattern.functionP()))
/* 388:    */     {
/* 389:461 */       Function p = (Function)pattern;
/* 390:462 */       Function d = (Function)datum;
/* 391:463 */       matches = matchAux(p.getSubject(), d.getSubject(), matches);
/* 392:464 */       if (matches == null)
/* 393:    */       {
/* 394:465 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Failed at E" });
/* 395:466 */         Mark.say(new Object[] {Boolean.valueOf(this.debugReturn), "T1", "null" });
/* 396:467 */         return matches;
/* 397:    */       }
/* 398:470 */       Mark.say(new Object[] {Boolean.valueOf(this.debugReturn), "T2", matches });
/* 399:471 */       if (includeAll) {
/* 400:472 */         matches = matches.cons(new PairOfEntities(pattern, datum));
/* 401:    */       }
/* 402:475 */       return matches;
/* 403:    */     }
/* 404:    */     Relation d;
/* 405:477 */     if ((datum.relationP()) && (pattern.relationP()))
/* 406:    */     {
/* 407:478 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Matching relations\n", pattern.asString(), "\n", datum.asString() });
/* 408:479 */       Relation p = (Relation)pattern;
/* 409:480 */       d = (Relation)datum;
/* 410:481 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Matching relation subjects\n", p.getSubject().asString(), "\n", d.getSubject().asString() });
/* 411:482 */       LList<PairOfEntities> subjectList = matchAux(p.getSubject(), d.getSubject(), matches);
/* 412:483 */       if (subjectList == null)
/* 413:    */       {
/* 414:484 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Failed at F" });
/* 415:485 */         Mark.say(new Object[] {Boolean.valueOf(this.debugReturn), "U", "null" });
/* 416:486 */         return null;
/* 417:    */       }
/* 418:489 */       matches = matchAux(p.getObject(), d.getObject(), subjectList);
/* 419:490 */       if (matches == null)
/* 420:    */       {
/* 421:491 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Failed at G" });
/* 422:492 */         Mark.say(new Object[] {Boolean.valueOf(this.debugReturn), "V", "null" });
/* 423:493 */         return null;
/* 424:    */       }
/* 425:496 */       Mark.say(new Object[] {Boolean.valueOf(this.debugReturn), "W", matches });
/* 426:497 */       if (includeAll) {
/* 427:498 */         matches = matches.cons(new PairOfEntities(pattern, datum));
/* 428:    */       }
/* 429:500 */       return matches;
/* 430:    */     }
/* 431:506 */     if ((datum.sequenceP()) && (pattern.sequenceP()))
/* 432:    */     {
/* 433:507 */       Mark.say(new Object[] {Boolean.valueOf(debugSequence), "Starting sequence match\n", pattern.asString(), "\n", datum.asString() });
/* 434:508 */       for (Entity patternElement : pattern.getElements())
/* 435:    */       {
/* 436:509 */         Mark.say(new Object[] {Boolean.valueOf(debugSequence), "Working on pattern element", patternElement.asString() });
/* 437:    */         
/* 438:511 */         String patternType = patternElement.getType();
/* 439:512 */         boolean found = false;
/* 440:513 */         for (Entity datumElement : datum.getElements())
/* 441:    */         {
/* 442:514 */           Mark.say(new Object[] {Boolean.valueOf(debugSequence), "Looking ad datum element", datumElement.asString() });
/* 443:    */           
/* 444:516 */           Mark.say(new Object[] {Boolean.valueOf(debugSequence), "Now matching pattern element", patternElement.asString(), "to", datumElement.asString() });
/* 445:517 */           LList<PairOfEntities> matchList = matchAux(patternElement, datumElement, matches);
/* 446:518 */           if (matchList == null)
/* 447:    */           {
/* 448:519 */             Mark.say(new Object[] {Boolean.valueOf(debugSequence), "Failed at H in sequence matching--matching sequence types do not match for", patternType });
/* 449:    */           }
/* 450:    */           else
/* 451:    */           {
/* 452:526 */             found = true;
/* 453:527 */             matches = matchList;
/* 454:528 */             if (!includeAll) {
/* 455:    */               break;
/* 456:    */             }
/* 457:529 */             matches = matches.cons(new PairOfEntities(patternElement, datumElement));
/* 458:    */             
/* 459:531 */             break;
/* 460:    */           }
/* 461:    */         }
/* 462:535 */         if (!found)
/* 463:    */         {
/* 464:536 */           Mark.say(new Object[] {Boolean.valueOf(debugSequence), "Failed at I in sequence matching--no matching type for", patternType });
/* 465:537 */           Mark.say(new Object[] {Boolean.valueOf(this.debugReturn), "X", "null" });
/* 466:538 */           return null;
/* 467:    */         }
/* 468:    */       }
/* 469:542 */       return matches;
/* 470:    */     }
/* 471:545 */     return null;
/* 472:    */   }
/* 473:    */   
/* 474:    */   private Entity getBinding(Entity key, LList<PairOfEntities> pairs)
/* 475:    */   {
/* 476:551 */     for (PairOfEntities pair : pairs) {
/* 477:552 */       if (key == pair.getPattern()) {
/* 478:553 */         return pair.getDatum();
/* 479:    */       }
/* 480:    */     }
/* 481:556 */     return null;
/* 482:    */   }
/* 483:    */   
/* 484:    */   private boolean matchTypes(Entity patternThing, Entity currentThing, boolean invertSign)
/* 485:    */   {
/* 486:563 */     Mark.say(
/* 487:    */     
/* 488:    */ 
/* 489:    */ 
/* 490:    */ 
/* 491:    */ 
/* 492:    */ 
/* 493:    */ 
/* 494:    */ 
/* 495:    */ 
/* 496:    */ 
/* 497:    */ 
/* 498:    */ 
/* 499:    */ 
/* 500:    */ 
/* 501:    */ 
/* 502:    */ 
/* 503:    */ 
/* 504:    */ 
/* 505:    */ 
/* 506:    */ 
/* 507:    */ 
/* 508:    */ 
/* 509:    */ 
/* 510:    */ 
/* 511:    */ 
/* 512:    */ 
/* 513:    */ 
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
/* 538:615 */       new Object[] { Boolean.valueOf(debug), "Matching types of", patternThing, currentThing });boolean t1Not = currentThing.hasFeature("not");boolean t2Not = patternThing.hasFeature("not");
/* 539:568 */     if ((t1Not != t2Not) && (!invertSign))
/* 540:    */     {
/* 541:569 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Incompatible signs", t1Not, t2Not, Boolean.valueOf(invertSign) });
/* 542:570 */       return false;
/* 543:    */     }
/* 544:572 */     if ((t1Not == t2Not) && (invertSign))
/* 545:    */     {
/* 546:573 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Incompatible signs", t1Not, t2Not, Boolean.valueOf(invertSign) });
/* 547:574 */       return false;
/* 548:    */     }
/* 549:580 */     if ((currentThing.isAPrimed("action")) && (patternThing.getType().equals("action"))) {
/* 550:581 */       return true;
/* 551:    */     }
/* 552:585 */     if (matchTypes(getRegularThread(currentThing), getRegularThread(patternThing))) {
/* 553:587 */       return true;
/* 554:    */     }
/* 555:590 */     if ((isPreposition(currentThing.getType())) && (currentThing.getType().equals(patternThing.getType()))) {
/* 556:591 */       return true;
/* 557:    */     }
/* 558:    */     Iterator localIterator2;
/* 559:    */     label533:
/* 560:594 */     for (Iterator localIterator1 = currentThing.getBundle().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 561:    */     {
/* 562:594 */       Thread currentThread = (Thread)localIterator1.next();
/* 563:595 */       if ((currentThread.contains("feature")) || (currentThread.contains("determiner"))) {
/* 564:    */         break label533;
/* 565:    */       }
/* 566:598 */       localIterator2 = patternThing.getBundle().iterator(); continue;Thread memoryThread = (Thread)localIterator2.next();
/* 567:599 */       if (!memoryThread.contains("feature"))
/* 568:    */       {
/* 569:603 */         if (matchTypes(memoryThread, currentThread))
/* 570:    */         {
/* 571:604 */           Mark.say(new Object[] {Boolean.valueOf(debugTypeMatch), "Matched " + currentThread + ", " + memoryThread });
/* 572:605 */           Mark.say(new Object[] {Boolean.valueOf(debugTypeMatch), "... but could not match " + getRegularThread(currentThing) + ", " + getRegularThread(patternThing) });
/* 573:606 */           return true;
/* 574:    */         }
/* 575:609 */         Mark.say(new Object[] {Boolean.valueOf(debugTypeMatch), "No match " + currentThread + ", " + memoryThread });
/* 576:    */       }
/* 577:    */     }
/* 578:614 */     return false;
/* 579:    */   }
/* 580:    */   
/* 581:    */   private Thread getRegularThread(Entity t)
/* 582:    */   {
/* 583:618 */     for (Thread currentThread : t.getBundle()) {
/* 584:619 */       if (currentThread.contains("feature")) {
/* 585:620 */         Mark.say(new Object[] {"Necesary Check?" });
/* 586:    */       } else {
/* 587:623 */         return currentThread;
/* 588:    */       }
/* 589:    */     }
/* 590:625 */     return null;
/* 591:    */   }
/* 592:    */   
/* 593:    */   private boolean isPreposition(String s)
/* 594:    */   {
/* 595:629 */     return (NewRuleSet.placePrepositions.contains(s)) || (NewRuleSet.pathPrepositions.contains(s));
/* 596:    */   }
/* 597:    */   
/* 598:    */   private boolean matchTypes(Thread currentThread, Thread memoryThread)
/* 599:    */   {
/* 600:639 */     if (this.specialMatch) {
/* 601:640 */       return specialMatchTypes(currentThread, memoryThread);
/* 602:    */     }
/* 603:642 */     if ((currentThread == null) || (memoryThread == null)) {
/* 604:643 */       return false;
/* 605:    */     }
/* 606:645 */     for (int i = 0; i < memoryThread.size(); i++)
/* 607:    */     {
/* 608:646 */       if (((String)memoryThread.get(i)).equals("name"))
/* 609:    */       {
/* 610:648 */         if (((String)currentThread.lastElement()).equalsIgnoreCase("I"))
/* 611:    */         {
/* 612:649 */           if (((String)memoryThread.lastElement()).equalsIgnoreCase("I")) {
/* 613:650 */             return true;
/* 614:    */           }
/* 615:653 */           return false;
/* 616:    */         }
/* 617:657 */         return true;
/* 618:    */       }
/* 619:660 */       if (i >= currentThread.size()) {
/* 620:661 */         return false;
/* 621:    */       }
/* 622:663 */       if (!((String)memoryThread.get(i)).equalsIgnoreCase((String)currentThread.get(i))) {
/* 623:670 */         return false;
/* 624:    */       }
/* 625:    */     }
/* 626:672 */     return true;
/* 627:    */   }
/* 628:    */   
/* 629:    */   private boolean specialMatchTypes(Thread currentThread, Thread memoryThread)
/* 630:    */   {
/* 631:680 */     if ((currentThread == null) || (memoryThread == null)) {
/* 632:681 */       return false;
/* 633:    */     }
/* 634:683 */     int cLocation = currentThread.indexOf("name");
/* 635:684 */     int cLimit = currentThread.size();
/* 636:    */     
/* 637:686 */     int mLocation = memoryThread.indexOf("name");
/* 638:687 */     int mLimit = memoryThread.size();
/* 639:689 */     if (mLocation >= 0) {
/* 640:690 */       mLimit = mLocation;
/* 641:    */     }
/* 642:692 */     if (cLocation >= 0) {
/* 643:693 */       cLimit = cLocation;
/* 644:    */     }
/* 645:697 */     for (int i = 0; i < cLimit; i++)
/* 646:    */     {
/* 647:698 */       String aClass = (String)currentThread.get(i);
/* 648:699 */       if (!memoryThread.contains(aClass))
/* 649:    */       {
/* 650:700 */         Mark.say(new Object[] {Boolean.valueOf(this.debugSpecial), "Failed to match     ", aClass, currentThread, memoryThread, Integer.valueOf(cLimit), Integer.valueOf(mLimit) });
/* 651:701 */         return false;
/* 652:    */       }
/* 653:    */     }
/* 654:704 */     Mark.say(new Object[] {Boolean.valueOf(this.debugSpecial), "Successfully matched", currentThread, memoryThread, "ok" });
/* 655:705 */     return true;
/* 656:    */   }
/* 657:    */   
/* 658:    */   public synchronized Entity dereference(Entity thing, int start, Sequence story)
/* 659:    */   {
/* 660:713 */     Sequence shortStory = new Sequence();
/* 661:714 */     Sequence completeStory = new Sequence();
/* 662:715 */     Vector<Entity> elements = story.getElements();
/* 663:716 */     int storySize = elements.size();
/* 664:717 */     for (int i = 0; i < storySize; i++)
/* 665:    */     {
/* 666:718 */       completeStory.addElement((Entity)elements.get(i));
/* 667:719 */       if (i >= start) {
/* 668:720 */         shortStory.addElement((Entity)elements.get(i));
/* 669:    */       }
/* 670:    */     }
/* 671:723 */     boolean old = true;
/* 672:724 */     if (old) {
/* 673:725 */       return Substitutor.dereferenceElement(thing, shortStory, completeStory);
/* 674:    */     }
/* 675:728 */     Entity dereference = quickDereference(thing, shortStory);
/* 676:    */     
/* 677:    */ 
/* 678:    */ 
/* 679:    */ 
/* 680:    */ 
/* 681:    */ 
/* 682:    */ 
/* 683:736 */     return dereference;
/* 684:    */   }
/* 685:    */   
/* 686:    */   public Entity quickDereference(Entity thing, Sequence shortStory)
/* 687:    */   {
/* 688:741 */     for (Entity element : shortStory.getElements()) {
/* 689:742 */       if (quickMatch(thing, element)) {
/* 690:743 */         return element;
/* 691:    */       }
/* 692:    */     }
/* 693:746 */     return thing;
/* 694:    */   }
/* 695:    */   
/* 696:    */   public boolean quickMatch(Entity newThing, Entity oldThing)
/* 697:    */   {
/* 698:750 */     if ((newThing.entityP()) && (oldThing.entityP())) {
/* 699:751 */       return newThing == oldThing;
/* 700:    */     }
/* 701:753 */     if ((newThing.functionP()) && (oldThing.functionP()) && (Substitutor.matchTypesAndSign(newThing, oldThing))) {
/* 702:754 */       return quickMatch(newThing.getSubject(), oldThing.getSubject());
/* 703:    */     }
/* 704:756 */     if ((newThing.relationP()) && (oldThing.relationP()) && (Substitutor.matchTypesAndSign(newThing, oldThing))) {
/* 705:757 */       return (quickMatch(newThing.getSubject(), oldThing.getSubject())) && (quickMatch(newThing.getObject(), oldThing.getObject()));
/* 706:    */     }
/* 707:759 */     if ((newThing.sequenceP()) && (oldThing.sequenceP()) && (Substitutor.matchTypesAndSign(newThing, oldThing)))
/* 708:    */     {
/* 709:760 */       Collection<Entity> newElements = newThing.getElements();
/* 710:761 */       Collection<Entity> oldElements = newThing.getElements();
/* 711:762 */       if (newElements.size() != oldElements.size()) {
/* 712:763 */         return false;
/* 713:    */       }
/* 714:765 */       for (Entity newElement : newElements)
/* 715:    */       {
/* 716:766 */         boolean result = false;
/* 717:767 */         for (Entity oldElement : oldElements) {
/* 718:768 */           if (quickMatch(newElement, oldElement))
/* 719:    */           {
/* 720:769 */             result = true;
/* 721:770 */             break;
/* 722:    */           }
/* 723:    */         }
/* 724:773 */         if (!result) {
/* 725:774 */           return false;
/* 726:    */         }
/* 727:    */       }
/* 728:778 */       return true;
/* 729:    */     }
/* 730:780 */     return false;
/* 731:    */   }
/* 732:    */   
/* 733:    */   public boolean alreadyExploited(Entity rule, Sequence story)
/* 734:    */   {
/* 735:784 */     for (Entity t : story.getElements())
/* 736:    */     {
/* 737:785 */       if (specialRuleMatch(rule, t))
/* 738:    */       {
/* 739:786 */         Mark.say(new Object[] {Boolean.valueOf(this.debugAlreadyDone), "Story already contains", t.asString() });
/* 740:787 */         Mark.say(new Object[] {Boolean.valueOf(this.debugAlreadyDone), "a varient of          ", rule.asString() });
/* 741:788 */         return true;
/* 742:    */       }
/* 743:790 */       if (t.isA("inference"))
/* 744:    */       {
/* 745:791 */         Mark.say(new Object[] {Boolean.valueOf(this.debugAlreadyDone), "No match of", rule.asString() });
/* 746:792 */         Mark.say(new Object[] {Boolean.valueOf(this.debugAlreadyDone), "to         ", t.asString() });
/* 747:    */       }
/* 748:    */     }
/* 749:796 */     Mark.say(new Object[] {Boolean.valueOf(this.debugAlreadyDone), "No such rule already used", rule.asString() });
/* 750:797 */     return false;
/* 751:    */   }
/* 752:    */   
/* 753:    */   public synchronized boolean identicalElementIsPresent(Entity target, Sequence story)
/* 754:    */   {
/* 755:801 */     return identicalElementIsPresent(target, 0, story);
/* 756:    */   }
/* 757:    */   
/* 758:    */   public synchronized boolean identicalElementIsPresent(Entity target, int start, Sequence story)
/* 759:    */   {
/* 760:805 */     Vector<Entity> elements = story.getElements();
/* 761:806 */     int index = elements.indexOf(target);
/* 762:807 */     if (index >= 0) {
/* 763:808 */       return true;
/* 764:    */     }
/* 765:810 */     return false;
/* 766:    */   }
/* 767:    */   
/* 768:    */   private boolean specialRuleMatch(Entity rule, Entity element)
/* 769:    */   {
/* 770:817 */     if (!matchTypes(element, rule, false)) {
/* 771:818 */       return false;
/* 772:    */     }
/* 773:820 */     if ((rule.entityP()) && (element.entityP())) {
/* 774:822 */       return rule == element;
/* 775:    */     }
/* 776:824 */     if ((rule.functionP()) && (element.functionP()))
/* 777:    */     {
/* 778:825 */       Function p = (Function)rule;
/* 779:826 */       Function d = (Function)element;
/* 780:827 */       return specialRuleMatch(p.getSubject(), d.getSubject());
/* 781:    */     }
/* 782:829 */     if ((rule.relationP()) && (element.relationP()))
/* 783:    */     {
/* 784:830 */       Relation p = (Relation)rule;
/* 785:831 */       Relation d = (Relation)element;
/* 786:832 */       return (specialRuleMatch(p.getSubject(), d.getSubject())) && (specialRuleMatch(p.getObject(), d.getObject()));
/* 787:    */     }
/* 788:834 */     if ((rule.sequenceP()) && (element.sequenceP()))
/* 789:    */     {
/* 790:837 */       Vector<Entity> ruleElements = ((Sequence)rule).getElements();
/* 791:838 */       Vector<Entity> elementElements = ((Sequence)element).getElements();
/* 792:839 */       if (ruleElements.size() != elementElements.size()) {
/* 793:840 */         return false;
/* 794:    */       }
/* 795:842 */       for (int i = 0; i < ruleElements.size(); i++) {
/* 796:843 */         if (!specialRuleMatch((Entity)ruleElements.get(i), (Entity)elementElements.get(i))) {
/* 797:844 */           return false;
/* 798:    */         }
/* 799:    */       }
/* 800:847 */       return true;
/* 801:    */     }
/* 802:849 */     return false;
/* 803:    */   }
/* 804:    */   
/* 805:    */   public double distance(Entity imaginedDescription, Entity rememberedDescription)
/* 806:    */   {
/* 807:853 */     LList<PairOfEntities> bindings = matchAll(imaginedDescription, rememberedDescription);
/* 808:854 */     if (bindings == null)
/* 809:    */     {
/* 810:855 */       Mark.say(new Object[] {"No match at all between", imaginedDescription.asString(), rememberedDescription.asString() });
/* 811:856 */       return 0.0D;
/* 812:    */     }
/* 813:858 */     double result = 0.0D;
/* 814:859 */     for (PairOfEntities pair : bindings) {
/* 815:860 */       result += intersectTypes(pair.getPattern().getBundle(), pair.getDatum().getBundle());
/* 816:    */     }
/* 817:863 */     return result;
/* 818:    */   }
/* 819:    */   
/* 820:    */   public double intersectTypes(Bundle b1, Bundle b2)
/* 821:    */   {
/* 822:867 */     double result = 0.0D;
/* 823:    */     Iterator localIterator2;
/* 824:868 */     for (Iterator localIterator1 = b1.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 825:    */     {
/* 826:868 */       Thread t1 = (Thread)localIterator1.next();
/* 827:869 */       localIterator2 = b2.iterator(); continue;Thread t2 = (Thread)localIterator2.next();
/* 828:870 */       result = Math.max(result, intersectTypes(t1, t2));
/* 829:    */     }
/* 830:873 */     return result;
/* 831:    */   }
/* 832:    */   
/* 833:    */   public double intersectTypes(Thread t1, Thread t2)
/* 834:    */   {
/* 835:877 */     int l1 = t1.size();
/* 836:878 */     int l2 = t2.size();
/* 837:880 */     for (int count = 0; count < Math.min(l1, l2); count++) {
/* 838:881 */       if (!((String)t1.get(count)).equalsIgnoreCase((String)t2.get(count))) {
/* 839:    */         break;
/* 840:    */       }
/* 841:    */     }
/* 842:886 */     return Math.pow(count, 2.0D) / (l1 * l2);
/* 843:    */   }
/* 844:    */   
/* 845:    */   public Entity instantiate(Entity t, LList<PairOfEntities> bindings)
/* 846:    */   {
/* 847:891 */     return Matcher.instantiate(t, bindings);
/* 848:    */   }
/* 849:    */   
/* 850:    */   public static void main(String[] ignore)
/* 851:    */     throws Exception
/* 852:    */   {
/* 853:901 */     BasicMatcherOriginal matcher = getBasicMatcher();
/* 854:    */     
/* 855:    */ 
/* 856:    */ 
/* 857:    */ 
/* 858:    */ 
/* 859:    */ 
/* 860:    */ 
/* 861:    */ 
/* 862:    */ 
/* 863:    */ 
/* 864:    */ 
/* 865:    */ 
/* 866:    */ 
/* 867:    */ 
/* 868:    */ 
/* 869:    */ 
/* 870:    */ 
/* 871:    */ 
/* 872:    */ 
/* 873:    */ 
/* 874:    */ 
/* 875:    */ 
/* 876:    */ 
/* 877:    */ 
/* 878:    */ 
/* 879:    */ 
/* 880:    */ 
/* 881:    */ 
/* 882:    */ 
/* 883:    */ 
/* 884:    */ 
/* 885:    */ 
/* 886:    */ 
/* 887:    */ 
/* 888:    */ 
/* 889:    */ 
/* 890:    */ 
/* 891:    */ 
/* 892:    */ 
/* 893:    */ 
/* 894:    */ 
/* 895:    */ 
/* 896:    */ 
/* 897:945 */     Translator translator = Translator.getTranslator();
/* 898:946 */     Entity t1 = translator.translate("John is glad that Mary is happy");
/* 899:947 */     Entity t2 = (Entity)translator.translate("Mary is happy").getElements().get(0);
/* 900:948 */     Entity t3 = translator.translate("A dog is angry that a cat is miserable");
/* 901:    */     
/* 902:950 */     Mark.say(new Object[] {"T1:", t1.asString() });
/* 903:951 */     Mark.say(new Object[] {"T2:", t2.asString() });
/* 904:952 */     Mark.say(new Object[] {"T3:", t3.asString() });
/* 905:    */     
/* 906:954 */     t1 = translator.translate("A human entered from the left");
/* 907:955 */     t2 = translator.translate("A human entered from the right");
/* 908:    */     
/* 909:957 */     Mark.say(new Object[] {"Match top level:", matcher.match(t2, t1) });
/* 910:958 */     Mark.say(new Object[] {"Match any part:", matcher.matchAnyPart(t2, t1) });
/* 911:    */     
/* 912:    */ 
/* 913:961 */     Mark.say(new Object[] {"Match structure:", Boolean.valueOf(matcher.matchStructures(t1, t2)) });
/* 914:    */   }
/* 915:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matchers.original.BasicMatcherOriginal
 * JD-Core Version:    0.7.0.1
 */