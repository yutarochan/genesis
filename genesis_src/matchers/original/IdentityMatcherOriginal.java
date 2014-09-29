/*   1:    */ package matchers.original;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Function;
/*   5:    */ import bridge.reps.entities.Relation;
/*   6:    */ import bridge.reps.entities.Sequence;
/*   7:    */ import bridge.reps.entities.Thread;
/*   8:    */ import java.io.PrintStream;
/*   9:    */ import java.util.List;
/*  10:    */ import minilisp.LList;
/*  11:    */ import translator.NewRuleSet;
/*  12:    */ import utils.Mark;
/*  13:    */ import utils.PairOfEntities;
/*  14:    */ 
/*  15:    */ public class IdentityMatcherOriginal
/*  16:    */ {
/*  17: 21 */   private static boolean debug = false;
/*  18:    */   private static IdentityMatcherOriginal identityMatcher;
/*  19: 25 */   private boolean requireSameNames = false;
/*  20:    */   
/*  21:    */   public boolean isRequireSameNames()
/*  22:    */   {
/*  23: 28 */     return this.requireSameNames;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void setRequireSameNames(boolean requireSameNames)
/*  27:    */   {
/*  28: 32 */     this.requireSameNames = requireSameNames;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public LList<PairOfEntities> match(Entity current, Entity remembered)
/*  32:    */   {
/*  33: 37 */     LList<PairOfEntities> matches = new LList();
/*  34: 38 */     LList<PairOfEntities> result = match(current, remembered, matches);
/*  35: 39 */     return result;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public LList<PairOfEntities> matchNegation(Entity current, Entity remembered, LList<PairOfEntities> matches)
/*  39:    */   {
/*  40: 43 */     return match(current, remembered, matches, true);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public LList<PairOfEntities> match(Entity current, Entity remembered, LList<PairOfEntities> bindings)
/*  44:    */   {
/*  45: 47 */     return match(current, remembered, bindings, false);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public LList<PairOfEntities> match(Entity current, Entity remembered, LList<PairOfEntities> bindings, boolean invertSign)
/*  49:    */   {
/*  50: 51 */     Mark.say(
/*  51:    */     
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56: 57 */       new Object[] { Boolean.valueOf(debug), "Trying to match " + current.asString() + " with " + remembered.asString() });LList<PairOfEntities> result = matchAux(current, remembered, bindings, invertSign);
/*  57: 53 */     if (result != null) {
/*  58: 54 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Cause matcher matched " + current.asString() + " with " + remembered.asString() + " yielding " + result });
/*  59:    */     }
/*  60: 56 */     return result;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public LList<PairOfEntities> matchAux(Entity current, Entity remembered, LList<PairOfEntities> matches)
/*  64:    */   {
/*  65: 63 */     return matchAux(current, remembered, matches, false);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public LList<PairOfEntities> matchAux(Entity datum, Entity pattern, LList<PairOfEntities> matches, boolean invertSign)
/*  69:    */   {
/*  70: 74 */     Mark.say(
/*  71:    */     
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
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
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
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
/* 203:207 */       new Object[] { Boolean.valueOf(debug), "Attempting to match with sign " + invertSign, "\n", datum.asString(), pattern.asString() });
/* 204: 75 */     if (matches == null)
/* 205:    */     {
/* 206: 76 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Failed at A" });
/* 207: 77 */       return null;
/* 208:    */     }
/* 209: 80 */     if ((pattern.functionP()) && (pattern.isA("appear")) && (pattern.getSubject().getType().equals("action")) && 
/* 210: 81 */       (datum.isAPrimed("action")))
/* 211:    */     {
/* 212: 82 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Special case trap for actions embedded in appear" });
/* 213: 83 */       boolean hasNotFeature = pattern.hasFeature("not");
/* 214: 84 */       Entity subject = pattern.getSubject();
/* 215: 85 */       if ((invertSign) && (hasNotFeature)) {
/* 216: 89 */         return matchAux(datum, subject, matches, false);
/* 217:    */       }
/* 218: 91 */       if (hasNotFeature) {
/* 219: 94 */         return matchAux(datum, subject, matches, true);
/* 220:    */       }
/* 221: 98 */       return matchAux(datum, subject, matches, false);
/* 222:    */     }
/* 223:101 */     if (!matchTypes(datum, pattern, invertSign))
/* 224:    */     {
/* 225:102 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Failed at D" });
/* 226:103 */       return null;
/* 227:    */     }
/* 228:105 */     if ((datum.isAPrimed("action")) && (pattern.entityP()) && (pattern.isAPrimed("action")))
/* 229:    */     {
/* 230:106 */       Mark.say(new Object[] {Boolean.valueOf(debug), "X.0: Looking for match of " + pattern.asString() });
/* 231:107 */       Entity match = getMatchForRemembered(pattern, matches);
/* 232:108 */       if (match == null)
/* 233:    */       {
/* 234:109 */         matches = matches.cons(new PairOfEntities(pattern, datum));
/* 235:    */         
/* 236:111 */         Mark.say(new Object[] {Boolean.valueOf(debug), "X.1: Remembering match of " + datum.asString() + " with " + pattern.asString() });
/* 237:112 */         Mark.say(new Object[] {Boolean.valueOf(debug), "New bindings: " + matches });
/* 238:113 */         return matches;
/* 239:    */       }
/* 240:115 */       if (match == datum)
/* 241:    */       {
/* 242:116 */         Mark.say(new Object[] {Boolean.valueOf(debug), "X.2: Old match of " + datum.asString() + " with " + pattern.asString() });
/* 243:117 */         return matches;
/* 244:    */       }
/* 245:119 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Failed at B to match " + match.getName() + " " + datum.getName() });
/* 246:120 */       return null;
/* 247:    */     }
/* 248:123 */     if (datum.getClass() != pattern.getClass())
/* 249:    */     {
/* 250:124 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Failed at C to match " + datum.getClass() + ", " + pattern.getClass() });
/* 251:125 */       return null;
/* 252:    */     }
/* 253:127 */     if ((datum.entityP()) && (pattern.entityP()))
/* 254:    */     {
/* 255:128 */       Entity match = getMatchForRemembered(pattern, matches);
/* 256:129 */       if (match == null)
/* 257:    */       {
/* 258:130 */         matches = matches.cons(new PairOfEntities(pattern, datum));
/* 259:131 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Succeded at C.1, " + matches });
/* 260:132 */         return matches;
/* 261:    */       }
/* 262:134 */       if (match == datum)
/* 263:    */       {
/* 264:135 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Succeded at C.2, " + matches });
/* 265:136 */         return matches;
/* 266:    */       }
/* 267:139 */       if (match.getType().equals(datum.getType()))
/* 268:    */       {
/* 269:140 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Succeded at C.3, " + matches });
/* 270:141 */         return matches;
/* 271:    */       }
/* 272:143 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Failed at C.4  to match " + match.getName() + " " + datum.getName() });
/* 273:144 */       return null;
/* 274:    */     }
/* 275:146 */     if ((datum.functionP()) && (pattern.functionP()))
/* 276:    */     {
/* 277:147 */       Function p = (Function)datum;
/* 278:148 */       Function d = (Function)pattern;
/* 279:149 */       LList<PairOfEntities> result = matchAux(p.getSubject(), d.getSubject(), matches);
/* 280:150 */       if (result == null) {
/* 281:151 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Failed at E" });
/* 282:    */       }
/* 283:153 */       return result;
/* 284:    */     }
/* 285:    */     Relation d;
/* 286:155 */     if ((datum.relationP()) && (pattern.relationP()))
/* 287:    */     {
/* 288:156 */       Relation p = (Relation)datum;
/* 289:157 */       d = (Relation)pattern;
/* 290:158 */       LList<PairOfEntities> subjectList = matchAux(p.getSubject(), d.getSubject(), matches);
/* 291:159 */       if (subjectList == null)
/* 292:    */       {
/* 293:160 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Failed at F" });
/* 294:161 */         return null;
/* 295:    */       }
/* 296:164 */       LList<PairOfEntities> objectList = matchAux(p.getObject(), d.getObject(), subjectList);
/* 297:165 */       if (objectList == null)
/* 298:    */       {
/* 299:166 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Failed at G" });
/* 300:167 */         return null;
/* 301:    */       }
/* 302:170 */       return objectList;
/* 303:    */     }
/* 304:176 */     if ((datum.sequenceP()) && (pattern.sequenceP()))
/* 305:    */     {
/* 306:177 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Starting sequence match", datum.asString(), pattern.asString() });
/* 307:178 */       for (Entity patternElement : ((Sequence)datum).getElements())
/* 308:    */       {
/* 309:180 */         String patternType = patternElement.getType();
/* 310:181 */         boolean found = false;
/* 311:182 */         for (Entity datumElement : ((Sequence)pattern).getElements())
/* 312:    */         {
/* 313:183 */           Mark.say(new Object[] {Boolean.valueOf(debug), "Looking for match between", patternType, "and", datumElement.getType() });
/* 314:184 */           if (matchTypes(patternElement, datumElement, false))
/* 315:    */           {
/* 316:185 */             LList<PairOfEntities> matchList = matchAux(patternElement, datumElement, matches);
/* 317:186 */             if (matchList == null)
/* 318:    */             {
/* 319:187 */               Mark.say(new Object[] {Boolean.valueOf(debug), "Failed at H in sequence matching--matching sequence types do not match for", patternType });
/* 320:    */             }
/* 321:    */             else
/* 322:    */             {
/* 323:194 */               found = true;
/* 324:195 */               matches = matchList;
/* 325:196 */               break;
/* 326:    */             }
/* 327:    */           }
/* 328:    */         }
/* 329:200 */         if (!found)
/* 330:    */         {
/* 331:201 */           Mark.say(new Object[] {Boolean.valueOf(debug), "Failed at I in sequence matching--no matching type for", patternType });
/* 332:202 */           return null;
/* 333:    */         }
/* 334:    */       }
/* 335:    */     }
/* 336:206 */     return matches;
/* 337:    */   }
/* 338:    */   
/* 339:    */   private Entity getMatchForRemembered(Entity thing, LList<PairOfEntities> matches)
/* 340:    */   {
/* 341:210 */     Mark.say(
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
/* 353:222 */       new Object[] { Boolean.valueOf(debug), "Looking for remembered match for " + thing.getName() });
/* 354:212 */     for (Object o : matches)
/* 355:    */     {
/* 356:213 */       PairOfEntities pairOfThings = (PairOfEntities)o;
/* 357:214 */       if (pairOfThings.getPattern() == thing)
/* 358:    */       {
/* 359:215 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Found " + pairOfThings.getDatum().getName() + "\n\n" });
/* 360:216 */         return pairOfThings.getDatum();
/* 361:    */       }
/* 362:    */     }
/* 363:219 */     Mark.say(new Object[] {Boolean.valueOf(debug), "None found" });
/* 364:220 */     return null;
/* 365:    */   }
/* 366:    */   
/* 367:    */   private boolean matchTypes(Entity currentThing, Entity memoryThing, boolean invertSign)
/* 368:    */   {
/* 369:228 */     boolean t1Not = currentThing.hasFeature("not");
/* 370:229 */     boolean t2Not = memoryThing.hasFeature("not");
/* 371:232 */     if ((t1Not != t2Not) && (!invertSign))
/* 372:    */     {
/* 373:233 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Incompatible signs", t1Not, t2Not, Boolean.valueOf(invertSign) });
/* 374:234 */       return false;
/* 375:    */     }
/* 376:236 */     if ((t1Not == t2Not) && (invertSign))
/* 377:    */     {
/* 378:237 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Incompatible signs", t1Not, t2Not, Boolean.valueOf(invertSign) });
/* 379:238 */       return false;
/* 380:    */     }
/* 381:244 */     if ((currentThing.isAPrimed("action")) && (memoryThing.getType().equals("action"))) {
/* 382:245 */       return true;
/* 383:    */     }
/* 384:248 */     if (matchTypes(getRegularThread(currentThing), getRegularThread(memoryThing))) {
/* 385:249 */       return true;
/* 386:    */     }
/* 387:252 */     if ((getRegularThread(currentThing).contains("killing")) && (getRegularThread(memoryThing).contains("killing"))) {
/* 388:253 */       return true;
/* 389:    */     }
/* 390:256 */     if ((isPreposition(currentThing.getType())) && (currentThing.getType().equals(memoryThing.getType()))) {
/* 391:257 */       return true;
/* 392:    */     }
/* 393:260 */     for (Thread currentThread : currentThing.getBundle()) {
/* 394:261 */       if (!currentThread.contains("feature")) {
/* 395:264 */         for (Thread memoryThread : memoryThing.getBundle()) {
/* 396:265 */           if (!memoryThread.contains("feature")) {
/* 397:268 */             if (matchTypes(currentThread, memoryThread))
/* 398:    */             {
/* 399:269 */               Mark.say(new Object[] {Boolean.valueOf(debug), "Matched " + currentThread + ", " + memoryThread });
/* 400:270 */               Mark.say(new Object[] {Boolean.valueOf(debug), "... but could not match " + getRegularThread(currentThing) + ", " + getRegularThread(memoryThing) });
/* 401:271 */               return false;
/* 402:    */             }
/* 403:    */           }
/* 404:    */         }
/* 405:    */       }
/* 406:    */     }
/* 407:280 */     return false;
/* 408:    */   }
/* 409:    */   
/* 410:    */   private Thread getRegularThread(Entity t)
/* 411:    */   {
/* 412:284 */     for (Thread currentThread : t.getBundle()) {
/* 413:285 */       if (!currentThread.contains("feature")) {
/* 414:288 */         return currentThread;
/* 415:    */       }
/* 416:    */     }
/* 417:290 */     return null;
/* 418:    */   }
/* 419:    */   
/* 420:    */   private boolean isPreposition(String s)
/* 421:    */   {
/* 422:294 */     return (NewRuleSet.placePrepositions.contains(s)) || (NewRuleSet.pathPrepositions.contains(s));
/* 423:    */   }
/* 424:    */   
/* 425:    */   private boolean matchTypes(Thread currentThread, Thread memoryThread)
/* 426:    */   {
/* 427:304 */     if ((currentThread == null) || (memoryThread == null)) {
/* 428:305 */       return false;
/* 429:    */     }
/* 430:307 */     if ((isRequireSameNames()) && ((currentThread.contains("name")) || (memoryThread.contains("name"))))
/* 431:    */     {
/* 432:308 */       Mark.say(new Object[] {"Testing", currentThread.getType(), memoryThread.getThreadType() });
/* 433:309 */       if (currentThread.getType().equalsIgnoreCase(memoryThread.getType()))
/* 434:    */       {
/* 435:310 */         Mark.say(new Object[] {"Matched", currentThread.getType(), memoryThread.getThreadType() });
/* 436:311 */         return true;
/* 437:    */       }
/* 438:313 */       return false;
/* 439:    */     }
/* 440:315 */     for (int i = 0; i < currentThread.size(); i++)
/* 441:    */     {
/* 442:316 */       if (i >= memoryThread.size()) {
/* 443:317 */         return false;
/* 444:    */       }
/* 445:319 */       if (!((String)memoryThread.get(i)).equalsIgnoreCase((String)currentThread.get(i))) {
/* 446:324 */         return false;
/* 447:    */       }
/* 448:    */     }
/* 449:326 */     return true;
/* 450:    */   }
/* 451:    */   
/* 452:    */   public static void main(String[] ignore)
/* 453:    */   {
/* 454:330 */     Entity t1 = new Entity("foo");
/* 455:331 */     t1.addType("name");
/* 456:332 */     t1.addType("john");
/* 457:333 */     Entity t2 = new Entity("foo");
/* 458:334 */     t2.addType("name");
/* 459:335 */     t2.addType("paul");
/* 460:336 */     Function d1 = new Function("d", t1);
/* 461:337 */     Function d2 = new Function("d", t2);
/* 462:338 */     IdentityMatcherOriginal identityMatcher = new IdentityMatcherOriginal();
/* 463:339 */     identityMatcher.setRequireSameNames(true);
/* 464:340 */     System.out.println("Match: " + identityMatcher.match(d1, d2));
/* 465:341 */     System.out.println("Match: " + identityMatcher.match(t1, t2));
/* 466:    */   }
/* 467:    */   
/* 468:    */   public static IdentityMatcherOriginal getIdentityMatcher()
/* 469:    */   {
/* 470:349 */     if (identityMatcher == null) {
/* 471:350 */       identityMatcher = new IdentityMatcherOriginal();
/* 472:    */     }
/* 473:352 */     return identityMatcher;
/* 474:    */   }
/* 475:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matchers.original.IdentityMatcherOriginal
 * JD-Core Version:    0.7.0.1
 */