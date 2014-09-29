/*   1:    */ package start;
/*   2:    */ 
/*   3:    */ import utils.Mark;
/*   4:    */ 
/*   5:    */ public class RoleFrame
/*   6:    */   extends RoleFrameParent
/*   7:    */ {
/*   8: 12 */   protected String rendering = "";
/*   9:    */   
/*  10:    */   public RoleFrame() {}
/*  11:    */   
/*  12:    */   public RoleFrame(Object object)
/*  13:    */   {
/*  14: 24 */     String s = (String)object;
/*  15: 25 */     if ((s.indexOf('+') < 0) && (s.indexOf('-') < 0))
/*  16:    */     {
/*  17: 26 */       index += 1;
/*  18: 27 */       s = s + "+" + index;
/*  19:    */     }
/*  20: 29 */     String o = extractHead(s);
/*  21: 30 */     this.head = o;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public RoleFrame(Object subject, Object action)
/*  25:    */   {
/*  26: 37 */     this(subject, action, null);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public RoleFrame(Object subject, Object action, Object object)
/*  30:    */   {
/*  31: 44 */     String s = extractHead(subject);
/*  32: 45 */     String a = extractNewHead(action);
/*  33: 46 */     String o = extractHead(object);
/*  34: 47 */     this.head = a;
/*  35: 48 */     this.rendering += makeTriple(s, a, o);
/*  36: 50 */     if ((object != null) && (o == null)) {
/*  37: 51 */       Mark.say(new Object[] {"No head for", ((RoleFrame)object).rendering });
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String head(Object o)
/*  42:    */   {
/*  43: 56 */     return head(o);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String extractHead(Object o)
/*  47:    */   {
/*  48: 60 */     return extractHead(o, true);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String extractNewHead(Object o)
/*  52:    */   {
/*  53: 64 */     return extractHead(o, false);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String extractHead(Object o, boolean newIndex)
/*  57:    */   {
/*  58: 71 */     if ((o == null) || ("null".equals(o.toString()))) {
/*  59: 72 */       return null;
/*  60:    */     }
/*  61: 75 */     if ((o instanceof RoleFrame))
/*  62:    */     {
/*  63: 76 */       String newStuff = ((RoleFrame)o).getRendering();
/*  64: 77 */       if (this.rendering.indexOf(newStuff) < 0) {
/*  65: 78 */         this.rendering += newStuff;
/*  66:    */       }
/*  67: 80 */       return ((RoleFrame)o).getHead();
/*  68:    */     }
/*  69: 83 */     if ((o instanceof String))
/*  70:    */     {
/*  71: 84 */       if (newIndex) {
/*  72: 85 */         return getIndexedWord((String)o, false);
/*  73:    */       }
/*  74: 88 */       return getIndexedWord((String)o);
/*  75:    */     }
/*  76: 91 */     Mark.err(new Object[] {"Unexpected type in RoleFrame.extractHead" });
/*  77: 92 */     return null;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setRendering(String rendering)
/*  81:    */   {
/*  82: 96 */     this.rendering = rendering;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String getRendering()
/*  86:    */   {
/*  87:100 */     return this.rendering;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void addTriple(String triple)
/*  91:    */   {
/*  92:104 */     this.rendering += triple;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public RoleFrame modifier(String feature)
/*  96:    */   {
/*  97:108 */     return addModifier(feature);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public RoleFrame addAttitude(String feature)
/* 101:    */   {
/* 102:112 */     this.rendering += makeProperty(this.head, "has_attitude", feature);
/* 103:113 */     return this;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public RoleFrame attitude(String feature)
/* 107:    */   {
/* 108:117 */     return addAttitude(feature);
/* 109:    */   }
/* 110:    */   
/* 111:    */   @Deprecated
/* 112:    */   public RoleFrame feature(String feature)
/* 113:    */   {
/* 114:127 */     return addFeature(feature);
/* 115:    */   }
/* 116:    */   
/* 117:    */   @Deprecated
/* 118:    */   public RoleFrame addFeature(String feature)
/* 119:    */   {
/* 120:135 */     this.rendering += makeProperty(this.head, extractHead("has_property"), feature);
/* 121:136 */     return this;
/* 122:    */   }
/* 123:    */   
/* 124:    */   @Deprecated
/* 125:    */   public RoleFrame indefinite()
/* 126:    */   {
/* 127:144 */     return makeIndefinite();
/* 128:    */   }
/* 129:    */   
/* 130:    */   @Deprecated
/* 131:    */   public RoleFrame makeIndefinite()
/* 132:    */   {
/* 133:152 */     carefullyAddDeterminer(makeProperty(this.head, "has_det", "indefinite"));
/* 134:153 */     return this;
/* 135:    */   }
/* 136:    */   
/* 137:    */   @Deprecated
/* 138:    */   public RoleFrame definite()
/* 139:    */   {
/* 140:161 */     return makeDefinite();
/* 141:    */   }
/* 142:    */   
/* 143:    */   @Deprecated
/* 144:    */   public RoleFrame makeDefinite()
/* 145:    */   {
/* 146:169 */     carefullyAddDeterminer(makeProperty(this.head, "has_det", "definite"));
/* 147:170 */     return this;
/* 148:    */   }
/* 149:    */   
/* 150:    */   @Deprecated
/* 151:    */   public RoleFrame noDeterminer()
/* 152:    */   {
/* 153:178 */     return makeNoDeterminer();
/* 154:    */   }
/* 155:    */   
/* 156:    */   @Deprecated
/* 157:    */   public RoleFrame makeNoDeterminer()
/* 158:    */   {
/* 159:186 */     carefullyAddDeterminer(makeProperty(this.head, "has_det", "null"));
/* 160:187 */     return this;
/* 161:    */   }
/* 162:    */   
/* 163:    */   @Deprecated
/* 164:    */   public RoleFrame another()
/* 165:    */   {
/* 166:195 */     return makeAnother();
/* 167:    */   }
/* 168:    */   
/* 169:    */   @Deprecated
/* 170:    */   public RoleFrame makeAnother()
/* 171:    */   {
/* 172:203 */     makeNoDeterminer();
/* 173:204 */     addFeature("another");
/* 174:205 */     return this;
/* 175:    */   }
/* 176:    */   
/* 177:    */   protected RoleFrame carefullyAddDeterminer(String triple)
/* 178:    */   {
/* 179:210 */     if (!this.rendering.contains(triple)) {
/* 180:212 */       if (this.rendering.contains("has_det"))
/* 181:    */       {
/* 182:213 */         StringBuffer buffer = new StringBuffer(this.rendering);
/* 183:214 */         int index1 = buffer.indexOf("has_det");
/* 184:215 */         int index2 = buffer.indexOf("]", index1);
/* 185:216 */         int index3 = triple.indexOf("has_det");
/* 186:217 */         int index4 = triple.indexOf("]", index3);
/* 187:218 */         buffer.replace(index1, index2, triple.substring(index3, index4));
/* 188:219 */         this.rendering = buffer.toString();
/* 189:    */       }
/* 190:    */       else
/* 191:    */       {
/* 192:222 */         this.rendering += triple;
/* 193:    */       }
/* 194:    */     }
/* 195:224 */     return this;
/* 196:    */   }
/* 197:    */   
/* 198:    */   @Deprecated
/* 199:    */   public RoleFrame plural()
/* 200:    */   {
/* 201:232 */     return makePlural();
/* 202:    */   }
/* 203:    */   
/* 204:    */   @Deprecated
/* 205:    */   public RoleFrame makePlural()
/* 206:    */   {
/* 207:240 */     this.rendering += makeProperty(this.head, "has_number", "plural");
/* 208:241 */     return this;
/* 209:    */   }
/* 210:    */   
/* 211:    */   @Deprecated
/* 212:    */   public RoleFrame singular()
/* 213:    */   {
/* 214:249 */     return makeSingular();
/* 215:    */   }
/* 216:    */   
/* 217:    */   @Deprecated
/* 218:    */   public RoleFrame makeSingular()
/* 219:    */   {
/* 220:257 */     this.rendering += makeProperty(this.head, "has_number", "singular");
/* 221:258 */     return this;
/* 222:    */   }
/* 223:    */   
/* 224:    */   @Deprecated
/* 225:    */   public RoleFrame possessor(Object possessor)
/* 226:    */   {
/* 227:266 */     this.rendering += makeProperty(this.head, extractHead("related-to"), possessor);
/* 228:267 */     return this;
/* 229:    */   }
/* 230:    */   
/* 231:    */   @Deprecated
/* 232:    */   public RoleFrame makePossessor(Object possessor)
/* 233:    */   {
/* 234:275 */     return possessor(possessor);
/* 235:    */   }
/* 236:    */   
/* 237:    */   @Deprecated
/* 238:    */   public RoleFrame addPossessor(Object possessor)
/* 239:    */   {
/* 240:283 */     return possessor(extractHead(possessor));
/* 241:    */   }
/* 242:    */   
/* 243:    */   public RoleFrame addInternalModifier(String feature)
/* 244:    */   {
/* 245:289 */     this.rendering += makeProperty(this.head, extractHead("has_modifier"), feature);
/* 246:    */     
/* 247:291 */     this.rendering += makeProperty("has_modifier", "has_position", "mid_verbal");
/* 248:292 */     return this;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public RoleFrame addModifier(String feature)
/* 252:    */   {
/* 253:296 */     this.rendering += makeProperty(this.head, extractHead("has_modifier"), feature);
/* 254:    */     
/* 255:298 */     this.rendering += makeProperty("has_modifier", "has_position", "leading");
/* 256:299 */     return this;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public RoleFrame modify(String feature)
/* 260:    */   {
/* 261:303 */     addModifier(feature);
/* 262:304 */     return this;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public RoleFrame addParticle(String particle)
/* 266:    */   {
/* 267:308 */     String x = (String)this.head;
/* 268:309 */     int index = x.lastIndexOf('+');
/* 269:310 */     this.head = (x.subSequence(0, index) + "_" + particle + x.substring(index));
/* 270:311 */     StringBuffer b = new StringBuffer(this.rendering);
/* 271:312 */     index = -1;
/* 272:313 */     while ((index = b.indexOf(x)) >= 0) {
/* 273:314 */       b.replace(index, index + x.length(), (String)this.head);
/* 274:    */     }
/* 275:316 */     this.rendering = b.toString();
/* 276:317 */     return this;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public RoleFrame particle(String particle)
/* 280:    */   {
/* 281:321 */     addParticle(particle);
/* 282:322 */     return this;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public RoleFrame role(String role, Object entity)
/* 286:    */   {
/* 287:326 */     return addRole(role, entity);
/* 288:    */   }
/* 289:    */   
/* 290:    */   public RoleFrame addRole(String role, Object entity)
/* 291:    */   {
/* 292:330 */     String e = extractHead(entity);
/* 293:331 */     this.rendering += makeTriple(this.head, role, e);
/* 294:332 */     return this;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public RoleFrame connect(String role, RoleFrame roleFrame)
/* 298:    */   {
/* 299:346 */     RoleFrame result = new RoleFrame(this, role, roleFrame);
/* 300:347 */     return result;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public RoleFrame makeAfter(RoleFrame roleFrame)
/* 304:    */   {
/* 305:351 */     return connect("after", roleFrame);
/* 306:    */   }
/* 307:    */   
/* 308:    */   public RoleFrame after(RoleFrame roleFrame)
/* 309:    */   {
/* 310:355 */     return connect("after", roleFrame);
/* 311:    */   }
/* 312:    */   
/* 313:    */   public RoleFrame makeBefore(RoleFrame roleFrame)
/* 314:    */   {
/* 315:359 */     return connect("before", roleFrame);
/* 316:    */   }
/* 317:    */   
/* 318:    */   public RoleFrame before(RoleFrame roleFrame)
/* 319:    */   {
/* 320:363 */     return connect("before", roleFrame);
/* 321:    */   }
/* 322:    */   
/* 323:    */   public RoleFrame makeWhile(RoleFrame roleFrame)
/* 324:    */   {
/* 325:367 */     return connect("while", roleFrame);
/* 326:    */   }
/* 327:    */   
/* 328:    */   public RoleFrame makeBecause(RoleFrame roleFrame)
/* 329:    */   {
/* 330:371 */     return connect("because", roleFrame);
/* 331:    */   }
/* 332:    */   
/* 333:    */   public RoleFrame because(RoleFrame roleFrame)
/* 334:    */   {
/* 335:375 */     return connect("because", roleFrame);
/* 336:    */   }
/* 337:    */   
/* 338:    */   public RoleFrame then(RoleFrame f)
/* 339:    */   {
/* 340:387 */     return f.connect("then", this);
/* 341:    */   }
/* 342:    */   
/* 343:    */   public RoleFrame partOf(RoleFrame whole)
/* 344:    */   {
/* 345:393 */     RoleFrameGrandParent part = new RoleFrameGrandParent("part+1", new RoleFrameGrandParent[0]);
/* 346:394 */     RoleFrame roleFrame = new RoleFrame(getHead(), "is-a", part.getHead());
/* 347:395 */     roleFrame.rendering += makeTriple(part.getHead(), "of+1", whole.getHead());
/* 348:396 */     roleFrame.rendering += makeProperty(part.getHead(), "has_det", "null");
/* 349:397 */     return roleFrame;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public RoleFrame embed(String embedding, RoleFrame roleFrame)
/* 353:    */   {
/* 354:403 */     return embed(this, embedding, roleFrame);
/* 355:    */   }
/* 356:    */   
/* 357:    */   private RoleFrame embed(RoleFrame subject, String embedding, RoleFrame roleFrame)
/* 358:    */   {
/* 359:407 */     RoleFrame result = new RoleFrame(subject.getHead(), embedding, roleFrame.getHead());
/* 360:408 */     result.rendering += subject.getRendering();
/* 361:409 */     result.rendering += roleFrame.getRendering();
/* 362:410 */     return result;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public RoleFrame believe(RoleFrame roleFrame)
/* 366:    */   {
/* 367:414 */     return embed(this, "believe", roleFrame);
/* 368:    */   }
/* 369:    */   
/* 370:    */   public RoleFrame combine(RoleFrame roleFrame)
/* 371:    */   {
/* 372:425 */     String[] triples = roleFrame.rendering.split("]");
/* 373:426 */     for (String t : triples)
/* 374:    */     {
/* 375:427 */       t = t + ']';
/* 376:428 */       if (!this.rendering.contains(t)) {
/* 377:429 */         this.rendering += t;
/* 378:    */       }
/* 379:    */     }
/* 380:436 */     return this;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public RoleFrame makeWhyQuestion()
/* 384:    */   {
/* 385:440 */     this.rendering += makeProperty(this.head, "is_question", "yes");
/* 386:441 */     this.rendering += makeProperty(this.head, "has_purpose", "why");
/* 387:442 */     return this;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public RoleFrame makeWhatIfQuestion()
/* 391:    */   {
/* 392:446 */     this.rendering += makeProperty(this.head, "is_question", "yes");
/* 393:447 */     this.rendering += makeProperty("happen+1", "if+1", this.head);
/* 394:448 */     this.rendering += makeProperty("what+1", "happen+1", null);
/* 395:449 */     return this;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public RoleFrame addDecoration(String x, String y, String z)
/* 399:    */   {
/* 400:455 */     this.rendering += makeProperty(x, y, z);
/* 401:456 */     return this;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public RoleFrame progressive()
/* 405:    */   {
/* 406:462 */     return makeProgressive();
/* 407:    */   }
/* 408:    */   
/* 409:    */   public RoleFrame makeProgressive()
/* 410:    */   {
/* 411:466 */     this.rendering += makeTriple(this.head, "is_progressive", "Yes");
/* 412:467 */     return this;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public RoleFrame present()
/* 416:    */   {
/* 417:471 */     return makePresent();
/* 418:    */   }
/* 419:    */   
/* 420:    */   public RoleFrame makePresent()
/* 421:    */   {
/* 422:475 */     this.rendering += makeProperty(this.head, "has_tense", "present");
/* 423:476 */     return this;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public RoleFrame past()
/* 427:    */   {
/* 428:480 */     return makePast();
/* 429:    */   }
/* 430:    */   
/* 431:    */   public RoleFrame makePast()
/* 432:    */   {
/* 433:484 */     this.rendering += makeProperty(this.head, "has_tense", "past");
/* 434:485 */     return this;
/* 435:    */   }
/* 436:    */   
/* 437:    */   public RoleFrame future()
/* 438:    */   {
/* 439:489 */     return makeFuture();
/* 440:    */   }
/* 441:    */   
/* 442:    */   public RoleFrame makeFuture()
/* 443:    */   {
/* 444:493 */     Mark.say(
/* 445:    */     
/* 446:    */ 
/* 447:    */ 
/* 448:497 */       new Object[] { "Calling future" });this.rendering += makeProperty(this.head, "has_modal", "will");return this;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public RoleFrame perfective()
/* 452:    */   {
/* 453:500 */     return makePerfective();
/* 454:    */   }
/* 455:    */   
/* 456:    */   public RoleFrame makePerfective()
/* 457:    */   {
/* 458:504 */     this.rendering += makeProperty(this.head, "is_perfective", "yes");
/* 459:505 */     return this;
/* 460:    */   }
/* 461:    */   
/* 462:    */   public RoleFrame negative()
/* 463:    */   {
/* 464:509 */     return makeNegative();
/* 465:    */   }
/* 466:    */   
/* 467:    */   public RoleFrame makeNegative()
/* 468:    */   {
/* 469:513 */     this.rendering += makeProperty(this.head, "is_negative", "Yes");
/* 470:514 */     return this;
/* 471:    */   }
/* 472:    */   
/* 473:    */   public RoleFrame passive()
/* 474:    */   {
/* 475:518 */     return makePassive();
/* 476:    */   }
/* 477:    */   
/* 478:    */   public RoleFrame makePassive()
/* 479:    */   {
/* 480:522 */     this.rendering += makeProperty(this.head, "has_voice", "passive");
/* 481:523 */     return this;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public RoleFrame may()
/* 485:    */   {
/* 486:527 */     return makeMay();
/* 487:    */   }
/* 488:    */   
/* 489:    */   public RoleFrame makeMay()
/* 490:    */   {
/* 491:531 */     this.rendering += makeProperty(this.head, "has_modal", "may");
/* 492:532 */     return this;
/* 493:    */   }
/* 494:    */   
/* 495:    */   public RoleFrame must()
/* 496:    */   {
/* 497:536 */     return makeMust();
/* 498:    */   }
/* 499:    */   
/* 500:    */   public RoleFrame makeMust()
/* 501:    */   {
/* 502:540 */     this.rendering += makeProperty(this.head, "has_modal", "must");
/* 503:541 */     return this;
/* 504:    */   }
/* 505:    */   
/* 506:    */   public RoleFrame makeModal(String modal)
/* 507:    */   {
/* 508:545 */     this.rendering += makeProperty(this.head, "has_modal", modal);
/* 509:546 */     return this;
/* 510:    */   }
/* 511:    */   
/* 512:    */   public RoleFrame that()
/* 513:    */   {
/* 514:550 */     return makeThat();
/* 515:    */   }
/* 516:    */   
/* 517:    */   public RoleFrame makeThat()
/* 518:    */   {
/* 519:554 */     this.rendering += makeProperty(this.head, "has_clause_type", "that");
/* 520:555 */     return this;
/* 521:    */   }
/* 522:    */   
/* 523:    */   public RoleFrame addAtTime(long time)
/* 524:    */   {
/* 525:560 */     addRole("at", Long.valueOf(time));
/* 526:561 */     return this;
/* 527:    */   }
/* 528:    */   
/* 529:    */   public RoleFrame addFromTime(long time)
/* 530:    */   {
/* 531:566 */     addRole("from", Long.valueOf(time));
/* 532:567 */     return this;
/* 533:    */   }
/* 534:    */   
/* 535:    */   public RoleFrame addToTime(long time)
/* 536:    */   {
/* 537:572 */     addRole("to", Long.valueOf(time));
/* 538:573 */     return this;
/* 539:    */   }
/* 540:    */   
/* 541:    */   public void addQuantity(int i)
/* 542:    */   {
/* 543:578 */     this.rendering += makeProperty(this.head, "has_quantity", Integer.toString(i));
/* 544:    */   }
/* 545:    */   
/* 546:    */   public Object clone()
/* 547:    */   {
/* 548:583 */     RoleFrame result = new RoleFrame();
/* 549:584 */     result.setRendering(this.rendering);
/* 550:585 */     return result;
/* 551:    */   }
/* 552:    */   
/* 553:    */   public static void main(String[] ignore)
/* 554:    */     throws Exception
/* 555:    */   {
/* 556:589 */     Mark.say(
/* 557:    */     
/* 558:591 */       new Object[] { "Hello world" });Mark.say(new Object[] { new RoleFrame("man").indefinite().rendering });
/* 559:    */   }
/* 560:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     start.RoleFrame
 * JD-Core Version:    0.7.0.1
 */