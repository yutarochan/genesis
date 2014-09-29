/*   1:    */ package matthewFay;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Sequence;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.swing.JFrame;
/*   8:    */ import matthewFay.Depricated.SequenceAligner;
/*   9:    */ import matthewFay.StoryAlignment.Alignment;
/*  10:    */ import matthewFay.StoryAlignment.RankedSequenceAlignmentSet;
/*  11:    */ import start.Generator;
/*  12:    */ import translator.Translator;
/*  13:    */ import utils.Mark;
/*  14:    */ 
/*  15:    */ public class Demo
/*  16:    */ {
/*  17:    */   public static Sequence ApproachStory()
/*  18:    */   {
/*  19: 21 */     Generator generator = Generator.getGenerator();
/*  20: 22 */     Translator translator = Translator.getTranslator();
/*  21:    */     
/*  22: 24 */     generator.setStoryMode();
/*  23:    */     
/*  24: 26 */     Sequence story1 = new Sequence();
/*  25:    */     try
/*  26:    */     {
/*  27: 28 */       story1.addElement(translator.translate("Start Story titled \"Approach Story\"").getElement(0));
/*  28: 29 */       story1.addElement(translator.translate("Matt is a person").getElement(0));
/*  29: 30 */       story1.addElement(translator.translate("Mary is a person").getElement(0));
/*  30: 31 */       story1.addElement(translator.translate("Matt is far from Mary.").getElement(0));
/*  31: 32 */       story1.addElement(translator.translate("Matt approaches Mary.").getElement(0));
/*  32: 33 */       story1.addElement(translator.translate("Matt is near Mary.").getElement(0));
/*  33:    */     }
/*  34:    */     catch (Exception e)
/*  35:    */     {
/*  36: 35 */       e.printStackTrace();
/*  37:    */     }
/*  38: 37 */     generator.flush();
/*  39: 38 */     return story1;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static Sequence CarryStory()
/*  43:    */   {
/*  44: 43 */     Generator generator = Generator.getGenerator();
/*  45: 44 */     Translator translator = Translator.getTranslator();
/*  46:    */     
/*  47: 46 */     generator.setStoryMode();
/*  48:    */     
/*  49: 48 */     Sequence story1 = new Sequence();
/*  50:    */     try
/*  51:    */     {
/*  52: 50 */       story1.addElement(translator.translate("Start Story titled \"Carry Story\"").getElement(0));
/*  53: 51 */       story1.addElement(translator.translate("Matt is a person").getElement(0));
/*  54: 52 */       story1.addElement(translator.translate("Mary is a person").getElement(0));
/*  55: 53 */       story1.addElement(translator.translate("The ball is an object").getElement(0));
/*  56: 54 */       story1.addElement(translator.translate("Matt controls the ball.").getElement(0));
/*  57: 55 */       story1.addElement(translator.translate("Matt carries the ball.").getElement(0));
/*  58: 56 */       story1.addElement(translator.translate("Matt approaches Mary.").getElement(0));
/*  59: 57 */       story1.addElement(translator.translate("Matt is near Mary.").getElement(0));
/*  60: 58 */       story1.addElement(translator.translate("The ball is near Mary.").getElement(0));
/*  61:    */     }
/*  62:    */     catch (Exception e)
/*  63:    */     {
/*  64: 60 */       e.printStackTrace();
/*  65:    */     }
/*  66: 62 */     generator.flush();
/*  67: 63 */     return story1;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public static Sequence KickStory()
/*  71:    */   {
/*  72: 68 */     Generator generator = Generator.getGenerator();
/*  73: 69 */     Translator translator = Translator.getTranslator();
/*  74:    */     
/*  75: 71 */     generator.setStoryMode();
/*  76:    */     
/*  77: 73 */     Sequence story1 = new Sequence();
/*  78:    */     try
/*  79:    */     {
/*  80: 75 */       story1.addElement(translator.translate("Start Story titled \"Kick Story\"").getElement(0));
/*  81: 76 */       story1.addElement(translator.translate("Matt is a person").getElement(0));
/*  82: 77 */       story1.addElement(translator.translate("The ball is an object").getElement(0));
/*  83: 78 */       story1.addElement(translator.translate("Matt walks towards the ball").getElement(0));
/*  84: 79 */       story1.addElement(translator.translate("Matt Kicks the ball").getElement(0));
/*  85:    */     }
/*  86:    */     catch (Exception e)
/*  87:    */     {
/*  88: 81 */       e.printStackTrace();
/*  89:    */     }
/*  90: 83 */     generator.flush();
/*  91: 84 */     return story1;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public static Sequence VerboseGive()
/*  95:    */   {
/*  96: 89 */     Generator generator = Generator.getGenerator();
/*  97: 90 */     Translator translator = Translator.getTranslator();
/*  98:    */     
/*  99: 92 */     generator.setStoryMode();
/* 100:    */     
/* 101: 94 */     Sequence story1 = new Sequence();
/* 102:    */     try
/* 103:    */     {
/* 104: 96 */       story1.addElement(translator.translate("Start Story titled \"Verbose Give Story\"").getElement(0));
/* 105: 97 */       story1.addElement(translator.translate("Matt is a person").getElement(0));
/* 106: 98 */       story1.addElement(translator.translate("Mary is a person").getElement(0));
/* 107: 99 */       story1.addElement(translator.translate("Matt controls the ball").getElement(0));
/* 108:100 */       story1.addElement(translator.translate("Matt controls the ball").getElement(0));
/* 109:101 */       story1.addElement(translator.translate("Matt controls the ball").getElement(0));
/* 110:102 */       story1.addElement(translator.translate("Matt controls the ball").getElement(0));
/* 111:103 */       story1.addElement(translator.translate("Matt walks toward Mary").getElement(0));
/* 112:104 */       story1.addElement(translator.translate("Mary walks toward Matt").getElement(0));
/* 113:105 */       story1.addElement(translator.translate("Matt walks toward Mary").getElement(0));
/* 114:106 */       story1.addElement(translator.translate("Mary walks toward Matt").getElement(0));
/* 115:107 */       story1.addElement(translator.translate("Matt gives the ball to Mary").getElement(0));
/* 116:108 */       story1.addElement(translator.translate("Matt gives the ball to Mary").getElement(0));
/* 117:109 */       story1.addElement(translator.translate("Mary controls the ball").getElement(0));
/* 118:110 */       story1.addElement(translator.translate("Mary controls the ball").getElement(0));
/* 119:111 */       story1.addElement(translator.translate("Mary controls the ball").getElement(0));
/* 120:    */     }
/* 121:    */     catch (Exception e)
/* 122:    */     {
/* 123:113 */       e.printStackTrace();
/* 124:    */     }
/* 125:115 */     generator.flush();
/* 126:116 */     return story1;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public static Sequence ComplexGapStory()
/* 130:    */   {
/* 131:120 */     Generator generator = Generator.getGenerator();
/* 132:121 */     Translator translator = Translator.getTranslator();
/* 133:    */     
/* 134:123 */     generator.setStoryMode();
/* 135:    */     
/* 136:125 */     Sequence story1 = new Sequence();
/* 137:    */     try
/* 138:    */     {
/* 139:127 */       story1.addElement(translator.translate("Start Story titled \"Complex Gap Story\"").getElement(0));
/* 140:128 */       story1.addElement(translator.translate("Matt is a person").getElement(0));
/* 141:129 */       story1.addElement(translator.translate("Mary is a person").getElement(0));
/* 142:130 */       story1.addElement(translator.translate("Tim is a person").getElement(0));
/* 143:131 */       story1.addElement(translator.translate("Mark is a person").getElement(0));
/* 144:132 */       story1.addElement(translator.translate("The ball is an object").getElement(0));
/* 145:133 */       story1.addElement(translator.translate("Matt controls the ball").getElement(0));
/* 146:134 */       story1.addElement(translator.translate("Matt walks toward Mary").getElement(0));
/* 147:135 */       story1.addElement(translator.translate("A gap appears").getElement(0));
/* 148:136 */       story1.addElement(translator.translate("Mary controls the ball").getElement(0));
/* 149:137 */       story1.addElement(translator.translate("Mary walks toward Tim").getElement(0));
/* 150:138 */       story1.addElement(translator.translate("A gap appears").getElement(0));
/* 151:139 */       story1.addElement(translator.translate("Tim controls the ball").getElement(0));
/* 152:140 */       story1.addElement(translator.translate("Mark walks toward Tim").getElement(0));
/* 153:141 */       story1.addElement(translator.translate("A gap appears").getElement(0));
/* 154:142 */       story1.addElement(translator.translate("Mark controls the ball").getElement(0));
/* 155:    */     }
/* 156:    */     catch (Exception e)
/* 157:    */     {
/* 158:144 */       e.printStackTrace();
/* 159:    */     }
/* 160:146 */     generator.flush();
/* 161:    */     
/* 162:148 */     return story1;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public static Sequence ComplexTakeStory()
/* 166:    */   {
/* 167:152 */     Generator generator = Generator.getGenerator();
/* 168:153 */     Translator translator = Translator.getTranslator();
/* 169:    */     
/* 170:155 */     generator.setStoryMode();
/* 171:    */     
/* 172:157 */     Sequence story1 = new Sequence();
/* 173:    */     try
/* 174:    */     {
/* 175:159 */       story1.addElement(translator.translate("Start Story titled \"Complex Take Story\"").getElement(0));
/* 176:160 */       story1.addElement(translator.translate("Mark is a person").getElement(0));
/* 177:161 */       story1.addElement(translator.translate("Sally is a person").getElement(0));
/* 178:162 */       story1.addElement(translator.translate("The bowl is an object").getElement(0));
/* 179:163 */       story1.addElement(translator.translate("Mark controls the bowl").getElement(0));
/* 180:164 */       story1.addElement(translator.translate("Sally walks toward Mark").getElement(0));
/* 181:165 */       story1.addElement(translator.translate("Sally takes the bowl from Mark").getElement(0));
/* 182:166 */       story1.addElement(translator.translate("Sally controls the bowl").getElement(0));
/* 183:    */     }
/* 184:    */     catch (Exception e)
/* 185:    */     {
/* 186:168 */       e.printStackTrace();
/* 187:    */     }
/* 188:170 */     generator.flush();
/* 189:    */     
/* 190:172 */     return story1;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public static Sequence ComplexGiveStory()
/* 194:    */   {
/* 195:176 */     Generator generator = Generator.getGenerator();
/* 196:177 */     Translator translator = Translator.getTranslator();
/* 197:    */     
/* 198:179 */     generator.setStoryMode();
/* 199:    */     
/* 200:181 */     Sequence story1 = new Sequence();
/* 201:    */     try
/* 202:    */     {
/* 203:183 */       story1.addElement(translator.translate("Start Story titled \"Complex Give Story\"").getElement(0));
/* 204:184 */       story1.addElement(translator.translate("Mark is a person").getElement(0));
/* 205:185 */       story1.addElement(translator.translate("Sally is a person").getElement(0));
/* 206:186 */       story1.addElement(translator.translate("The bowl is an object").getElement(0));
/* 207:187 */       story1.addElement(translator.translate("Mark controls the bowl").getElement(0));
/* 208:188 */       story1.addElement(translator.translate("Mark walks toward Sally").getElement(0));
/* 209:189 */       story1.addElement(translator.translate("Mark gives the bowl to Sally").getElement(0));
/* 210:190 */       story1.addElement(translator.translate("Sally controls the bowl").getElement(0));
/* 211:    */     }
/* 212:    */     catch (Exception e)
/* 213:    */     {
/* 214:192 */       e.printStackTrace();
/* 215:    */     }
/* 216:194 */     generator.flush();
/* 217:    */     
/* 218:196 */     return story1;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public static Sequence GapStory()
/* 222:    */   {
/* 223:201 */     Generator generator = Generator.getGenerator();
/* 224:202 */     Translator translator = Translator.getTranslator();
/* 225:    */     
/* 226:204 */     generator.setStoryMode();
/* 227:205 */     Sequence story1 = new Sequence();
/* 228:    */     try
/* 229:    */     {
/* 230:207 */       Entity s1_Title = translator.translate("Start Story titled \"Gap Story\"").getElement(0);
/* 231:208 */       Entity s1_A = translator.translate("Matt is a person").getElement(0);
/* 232:209 */       Entity s1_B = translator.translate("Mary is a person").getElement(0);
/* 233:210 */       Entity s1_B_TIM = translator.translate("Tim is a person").getElement(0);
/* 234:211 */       Entity s1_C = translator.translate("The ball is an object").getElement(0);
/* 235:212 */       Entity s1_D = translator.translate("Matt controls the ball").getElement(0);
/* 236:213 */       Entity s1_E = translator.translate("Matt gives the ball to Mary").getElement(0);
/* 237:214 */       Entity s1_E_GAP = translator.translate("A gap appears").getElement(0);
/* 238:215 */       Entity s1_F = translator.translate("Mary controls the ball").getElement(0);
/* 239:216 */       Entity s1_G_TIM = translator.translate("A gap appears").getElement(0);
/* 240:217 */       Entity s1_H_TIM = translator.translate("Tim controls the ball").getElement(0);
/* 241:    */       
/* 242:219 */       generator.flush();
/* 243:    */       
/* 244:    */ 
/* 245:222 */       story1.addElement(s1_Title);
/* 246:223 */       story1.addElement(s1_A);
/* 247:224 */       story1.addElement(s1_B);
/* 248:    */       
/* 249:226 */       story1.addElement(s1_C);
/* 250:227 */       story1.addElement(s1_D);
/* 251:    */       
/* 252:229 */       story1.addElement(s1_E_GAP);
/* 253:230 */       story1.addElement(s1_F);
/* 254:    */     }
/* 255:    */     catch (Exception e)
/* 256:    */     {
/* 257:234 */       e.printStackTrace();
/* 258:    */     }
/* 259:236 */     return story1;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public static Sequence GiveStory()
/* 263:    */   {
/* 264:241 */     Generator generator = Generator.getGenerator();
/* 265:242 */     Translator translator = Translator.getTranslator();
/* 266:    */     
/* 267:244 */     generator.setStoryMode();
/* 268:245 */     Sequence story2 = new Sequence();
/* 269:    */     try
/* 270:    */     {
/* 271:247 */       Entity s2_Title = translator.translate("Start Story titled \"Give Story\"").getElement(0);
/* 272:248 */       Entity s2_A = translator.translate("Matt is a person").getElement(0);
/* 273:249 */       Entity s2_B = translator.translate("Molly is a person").getElement(0);
/* 274:250 */       Entity s2_C = translator.translate("The cup is an object").getElement(0);
/* 275:251 */       Entity s2_D = translator.translate("Matt controls the cup").getElement(0);
/* 276:252 */       Entity s2_E = translator.translate("Matt gives the cup to Molly").getElement(0);
/* 277:253 */       Entity s2_F = translator.translate("Molly controls the cup").getElement(0);
/* 278:    */       
/* 279:255 */       generator.flush();
/* 280:    */       
/* 281:    */ 
/* 282:258 */       story2.addElement(s2_Title);
/* 283:259 */       story2.addElement(s2_A);
/* 284:260 */       story2.addElement(s2_B);
/* 285:261 */       story2.addElement(s2_C);
/* 286:262 */       story2.addElement(s2_D);
/* 287:263 */       story2.addElement(s2_E);
/* 288:264 */       story2.addElement(s2_F);
/* 289:    */     }
/* 290:    */     catch (Exception e)
/* 291:    */     {
/* 292:266 */       e.printStackTrace();
/* 293:    */     }
/* 294:269 */     return story2;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public static Sequence GiveStartStory()
/* 298:    */   {
/* 299:274 */     Generator generator = Generator.getGenerator();
/* 300:275 */     Translator translator = Translator.getTranslator();
/* 301:    */     
/* 302:277 */     generator.setStoryMode();
/* 303:278 */     Sequence story2 = new Sequence();
/* 304:    */     try
/* 305:    */     {
/* 306:280 */       Entity s2_Title = translator.translate("Start Story titled \"Give Story\"").getElement(0);
/* 307:281 */       Entity s2_A = translator.translate("Mark is a person").getElement(0);
/* 308:282 */       Entity s2_B = translator.translate("Sally is a person").getElement(0);
/* 309:283 */       Entity s2_C = translator.translate("The cup is an object").getElement(0);
/* 310:284 */       Entity s2_D = translator.translate("Mark controls the cup").getElement(0);
/* 311:    */       
/* 312:286 */       generator.flush();
/* 313:    */       
/* 314:    */ 
/* 315:289 */       story2.addElement(s2_Title);
/* 316:290 */       story2.addElement(s2_A);
/* 317:291 */       story2.addElement(s2_B);
/* 318:292 */       story2.addElement(s2_C);
/* 319:293 */       story2.addElement(s2_D);
/* 320:    */     }
/* 321:    */     catch (Exception e)
/* 322:    */     {
/* 323:295 */       e.printStackTrace();
/* 324:    */     }
/* 325:298 */     return story2;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public static Sequence FleeStory()
/* 329:    */   {
/* 330:302 */     Generator generator = Generator.getGenerator();
/* 331:303 */     Translator translator = Translator.getTranslator();
/* 332:    */     
/* 333:305 */     generator.setStoryMode();
/* 334:    */     
/* 335:307 */     Sequence fleeStory = new Sequence();
/* 336:    */     try
/* 337:    */     {
/* 338:309 */       Entity flee_Title = translator.translate("Start Story titled \"Flee Story\"").getElement(0);
/* 339:310 */       fleeStory.addElement(flee_Title);
/* 340:311 */       Entity flee_A = translator.translate("Mark is a person").getElement(0);
/* 341:312 */       fleeStory.addElement(flee_A);
/* 342:313 */       Entity flee_B = translator.translate("Sally is a person").getElement(0);
/* 343:314 */       fleeStory.addElement(flee_B);
/* 344:315 */       Entity flee_C = translator.translate("Mark is near Sally").getElement(0);
/* 345:316 */       fleeStory.addElement(flee_C);
/* 346:317 */       Entity flee_D = translator.translate("Sally flees from Mark").getElement(0);
/* 347:318 */       fleeStory.addElement(flee_D);
/* 348:319 */       Entity flee_E = translator.translate("Mark is far from Sally").getElement(0);
/* 349:320 */       fleeStory.addElement(flee_E);
/* 350:    */     }
/* 351:    */     catch (Exception e)
/* 352:    */     {
/* 353:322 */       e.printStackTrace();
/* 354:    */     }
/* 355:324 */     generator.flush();
/* 356:    */     
/* 357:326 */     return fleeStory;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public static Sequence TakeStory()
/* 361:    */   {
/* 362:331 */     Generator generator = Generator.getGenerator();
/* 363:332 */     Translator translator = Translator.getTranslator();
/* 364:    */     
/* 365:334 */     generator.setStoryMode();
/* 366:    */     
/* 367:336 */     Sequence takeStory = new Sequence();
/* 368:    */     try
/* 369:    */     {
/* 370:338 */       Entity take_Title = translator.translate("Start Story titled \"Take Story\"").getElement(0);
/* 371:339 */       takeStory.addElement(take_Title);
/* 372:340 */       Entity take_A = translator.translate("Mark is a person").getElement(0);
/* 373:341 */       takeStory.addElement(take_A);
/* 374:342 */       Entity take_B = translator.translate("Sally is a person").getElement(0);
/* 375:343 */       takeStory.addElement(take_B);
/* 376:344 */       Entity take_C = translator.translate("The cup is an object").getElement(0);
/* 377:345 */       takeStory.addElement(take_C);
/* 378:346 */       Entity take_D = translator.translate("Mark controls the cup").getElement(0);
/* 379:347 */       takeStory.addElement(take_D);
/* 380:348 */       Entity take_E = translator.translate("Sally takes the cup from Mark").getElement(0);
/* 381:349 */       takeStory.addElement(take_E);
/* 382:350 */       Entity take_F = translator.translate("Sally controls the cup").getElement(0);
/* 383:351 */       takeStory.addElement(take_F);
/* 384:    */     }
/* 385:    */     catch (Exception e)
/* 386:    */     {
/* 387:353 */       e.printStackTrace();
/* 388:    */     }
/* 389:355 */     generator.flush();
/* 390:    */     
/* 391:357 */     return takeStory;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public static Sequence ThrowCatchStory()
/* 395:    */   {
/* 396:362 */     Generator generator = Generator.getGenerator();
/* 397:363 */     Translator translator = Translator.getTranslator();
/* 398:    */     
/* 399:365 */     generator.setStoryMode();
/* 400:    */     
/* 401:367 */     Sequence throwCatchStory = new Sequence();
/* 402:    */     try
/* 403:    */     {
/* 404:369 */       Entity throw_catch_Title = translator.translate("Start Story titled \"Throw-Catch Story\"").getElement(0);
/* 405:370 */       throwCatchStory.addElement(throw_catch_Title);
/* 406:371 */       Entity throw_catch_A = translator.translate("Mark is a person").getElement(0);
/* 407:372 */       throwCatchStory.addElement(throw_catch_A);
/* 408:373 */       Entity throw_catch_B = translator.translate("Sally is a person").getElement(0);
/* 409:374 */       throwCatchStory.addElement(throw_catch_B);
/* 410:375 */       Entity throw_catch_C = translator.translate("The cup is an object").getElement(0);
/* 411:376 */       throwCatchStory.addElement(throw_catch_C);
/* 412:377 */       Entity throw_catch_D = translator.translate("Mark controls the cup").getElement(0);
/* 413:378 */       throwCatchStory.addElement(throw_catch_D);
/* 414:379 */       Entity throw_catch_E = translator.translate("Mark throws the cup towards Sally").getElement(0);
/* 415:380 */       throwCatchStory.addElement(throw_catch_E);
/* 416:381 */       Entity throw_catch_F = translator.translate("Sally catches the cup").getElement(0);
/* 417:382 */       throwCatchStory.addElement(throw_catch_F);
/* 418:383 */       Entity throw_catch_G = translator.translate("Sally controls the cup").getElement(0);
/* 419:384 */       throwCatchStory.addElement(throw_catch_G);
/* 420:    */     }
/* 421:    */     catch (Exception e)
/* 422:    */     {
/* 423:386 */       e.printStackTrace();
/* 424:    */     }
/* 425:388 */     generator.flush();
/* 426:    */     
/* 427:390 */     return throwCatchStory;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public static Sequence FollowStory()
/* 431:    */   {
/* 432:395 */     Generator generator = Generator.getGenerator();
/* 433:396 */     Translator translator = Translator.getTranslator();
/* 434:    */     
/* 435:398 */     generator.setStoryMode();
/* 436:399 */     Sequence followStory = new Sequence();
/* 437:    */     try
/* 438:    */     {
/* 439:401 */       Entity follow_Title = translator.translate("Start Story titled \"Follow Story\"").getElement(0);
/* 440:402 */       followStory.addElement(follow_Title);
/* 441:403 */       Entity follow_A = translator.translate("Mark is a person").getElement(0);
/* 442:404 */       followStory.addElement(follow_A);
/* 443:405 */       Entity follow_B = translator.translate("Sally is a person").getElement(0);
/* 444:406 */       followStory.addElement(follow_B);
/* 445:407 */       Entity follow_C = translator.translate("Mark is near Sally").getElement(0);
/* 446:408 */       followStory.addElement(follow_C);
/* 447:409 */       Entity follow_D = translator.translate("Sally walks away from Mark").getElement(0);
/* 448:410 */       followStory.addElement(follow_D);
/* 449:411 */       Entity follow_E = translator.translate("Mark follows Sally").getElement(0);
/* 450:412 */       followStory.addElement(follow_E);
/* 451:413 */       Entity follow_F = translator.translate("Mark is near Sally").getElement(0);
/* 452:414 */       followStory.addElement(follow_F);
/* 453:    */     }
/* 454:    */     catch (Exception e)
/* 455:    */     {
/* 456:416 */       e.printStackTrace();
/* 457:    */     }
/* 458:418 */     generator.flush();
/* 459:    */     
/* 460:420 */     return followStory;
/* 461:    */   }
/* 462:    */   
/* 463:    */   public static Sequence ExchangeStory()
/* 464:    */   {
/* 465:425 */     Generator generator = Generator.getGenerator();
/* 466:426 */     Translator translator = Translator.getTranslator();
/* 467:    */     
/* 468:428 */     generator.setStoryMode();
/* 469:    */     
/* 470:430 */     Sequence exchangeStory = new Sequence();
/* 471:    */     try
/* 472:    */     {
/* 473:432 */       Entity exchange_Title = translator.translate("Start Story titled \"Exchange Story\"").getElement(0);
/* 474:433 */       exchangeStory.addElement(exchange_Title);
/* 475:434 */       Entity exchange_A = translator.translate("Mark is a person").getElement(0);
/* 476:435 */       exchangeStory.addElement(exchange_A);
/* 477:436 */       Entity exchange_B = translator.translate("Sally is a person").getElement(0);
/* 478:437 */       exchangeStory.addElement(exchange_B);
/* 479:438 */       Entity exchange_C = translator.translate("The cup is an object").getElement(0);
/* 480:439 */       exchangeStory.addElement(exchange_C);
/* 481:440 */       Entity exchange_D = translator.translate("The bowl is an object").getElement(0);
/* 482:441 */       exchangeStory.addElement(exchange_D);
/* 483:442 */       Entity exchange_E = translator.translate("Mark controls the cup").getElement(0);
/* 484:443 */       exchangeStory.addElement(exchange_E);
/* 485:444 */       Entity exchange_F = translator.translate("Sally controls the bowl").getElement(0);
/* 486:445 */       exchangeStory.addElement(exchange_F);
/* 487:    */       
/* 488:    */ 
/* 489:    */ 
/* 490:    */ 
/* 491:450 */       Entity exchange_G_A = translator.translate("Mark gives the cup to Sally").getElement(0);
/* 492:451 */       exchangeStory.addElement(exchange_G_A);
/* 493:452 */       Entity exchange_G_B = translator.translate("Sally gives the bowl to Mark").getElement(0);
/* 494:453 */       exchangeStory.addElement(exchange_G_B);
/* 495:    */       
/* 496:455 */       Entity exchange_H = translator.translate("Sally controls the cup").getElement(0);
/* 497:456 */       exchangeStory.addElement(exchange_H);
/* 498:457 */       Entity exchange_I = translator.translate("Mark controls the bowl").getElement(0);
/* 499:458 */       exchangeStory.addElement(exchange_I);
/* 500:    */     }
/* 501:    */     catch (Exception e)
/* 502:    */     {
/* 503:460 */       e.printStackTrace();
/* 504:    */     }
/* 505:462 */     generator.flush();
/* 506:    */     
/* 507:464 */     return exchangeStory;
/* 508:    */   }
/* 509:    */   
/* 510:    */   public static Sequence MediatedExchangeStory()
/* 511:    */   {
/* 512:469 */     Generator generator = Generator.getGenerator();
/* 513:470 */     Translator translator = Translator.getTranslator();
/* 514:    */     
/* 515:472 */     generator.setStoryMode();
/* 516:    */     
/* 517:474 */     Sequence exchangeStory = new Sequence();
/* 518:    */     try
/* 519:    */     {
/* 520:476 */       Entity exchange_Title = translator.translate("Start Story titled \"Mediated Exchange Story\"").getElement(0);
/* 521:477 */       exchangeStory.addElement(exchange_Title);
/* 522:478 */       Entity exchange_A = translator.translate("Mark is a person").getElement(0);
/* 523:479 */       exchangeStory.addElement(exchange_A);
/* 524:480 */       Entity exchange_B = translator.translate("Sally is a person").getElement(0);
/* 525:481 */       exchangeStory.addElement(exchange_B);
/* 526:482 */       Entity exchange_B2 = translator.translate("Mary is a person").getElement(0);
/* 527:483 */       exchangeStory.addElement(exchange_B2);
/* 528:484 */       Entity exchange_C = translator.translate("The cup is an object").getElement(0);
/* 529:485 */       exchangeStory.addElement(exchange_C);
/* 530:486 */       Entity exchange_D = translator.translate("The bowl is an object").getElement(0);
/* 531:487 */       exchangeStory.addElement(exchange_D);
/* 532:488 */       Entity exchange_E = translator.translate("Mark controls the cup").getElement(0);
/* 533:489 */       exchangeStory.addElement(exchange_E);
/* 534:490 */       Entity exchange_F = translator.translate("Sally controls the bowl").getElement(0);
/* 535:491 */       exchangeStory.addElement(exchange_F);
/* 536:    */       
/* 537:    */ 
/* 538:    */ 
/* 539:    */ 
/* 540:496 */       Entity exchange_G_A = translator.translate("Mark gives the cup to Mary").getElement(0);
/* 541:497 */       exchangeStory.addElement(exchange_G_A);
/* 542:498 */       Entity exchange_G_B = translator.translate("Sally gives the bowl to Mary").getElement(0);
/* 543:499 */       exchangeStory.addElement(exchange_G_B);
/* 544:500 */       Entity exchange_X = translator.translate("Mary controls the cup").getElement(0);
/* 545:501 */       exchangeStory.addElement(exchange_X);
/* 546:502 */       Entity exchange_Z = translator.translate("Mary controls the bowl").getElement(0);
/* 547:503 */       exchangeStory.addElement(exchange_Z);
/* 548:504 */       Entity exchange_G_C = translator.translate("Mary gives the bowl to Mark").getElement(0);
/* 549:505 */       exchangeStory.addElement(exchange_G_C);
/* 550:506 */       Entity exchange_G_D = translator.translate("Mary gives the bowl to Sally").getElement(0);
/* 551:507 */       exchangeStory.addElement(exchange_G_D);
/* 552:    */       
/* 553:509 */       Entity exchange_H = translator.translate("Sally controls the cup").getElement(0);
/* 554:510 */       exchangeStory.addElement(exchange_H);
/* 555:511 */       Entity exchange_I = translator.translate("Mark controls the bowl").getElement(0);
/* 556:512 */       exchangeStory.addElement(exchange_I);
/* 557:    */     }
/* 558:    */     catch (Exception e)
/* 559:    */     {
/* 560:514 */       e.printStackTrace();
/* 561:    */     }
/* 562:516 */     generator.flush();
/* 563:    */     
/* 564:518 */     return exchangeStory;
/* 565:    */   }
/* 566:    */   
/* 567:    */   public static void SequenceAlignmentDemo()
/* 568:    */   {
/* 569:523 */     Mark.say(
/* 570:    */     
/* 571:    */ 
/* 572:    */ 
/* 573:    */ 
/* 574:    */ 
/* 575:    */ 
/* 576:    */ 
/* 577:    */ 
/* 578:    */ 
/* 579:533 */       new Object[] { "Sequence Alignment Demo" });Mark.say(new Object[] { "Doing Parsing..." });Sequence gapStory = GapStory();Sequence giveStory = GiveStory();Mark.say(new Object[] { "Doing Alignment..." });SequenceAligner aligner = new SequenceAligner();SequenceAligner.outputAlignment(aligner.align(giveStory, gapStory));
/* 580:    */   }
/* 581:    */   
/* 582:    */   public static void RankedAlignmentDemo()
/* 583:    */   {
/* 584:536 */     Mark.say(
/* 585:    */     
/* 586:    */ 
/* 587:    */ 
/* 588:    */ 
/* 589:    */ 
/* 590:    */ 
/* 591:    */ 
/* 592:    */ 
/* 593:    */ 
/* 594:    */ 
/* 595:    */ 
/* 596:    */ 
/* 597:    */ 
/* 598:    */ 
/* 599:    */ 
/* 600:    */ 
/* 601:    */ 
/* 602:    */ 
/* 603:    */ 
/* 604:    */ 
/* 605:    */ 
/* 606:    */ 
/* 607:    */ 
/* 608:    */ 
/* 609:    */ 
/* 610:    */ 
/* 611:    */ 
/* 612:    */ 
/* 613:    */ 
/* 614:    */ 
/* 615:    */ 
/* 616:    */ 
/* 617:    */ 
/* 618:    */ 
/* 619:    */ 
/* 620:    */ 
/* 621:    */ 
/* 622:    */ 
/* 623:    */ 
/* 624:    */ 
/* 625:    */ 
/* 626:    */ 
/* 627:    */ 
/* 628:    */ 
/* 629:    */ 
/* 630:    */ 
/* 631:    */ 
/* 632:584 */       new Object[] { "Sequence Match Demo" });Mark.say(new Object[] { "Doing Parsing..." });Sequence gapStory = GapStory();Sequence giveStory = GiveStory();Sequence fleeStory = FleeStory();Sequence takeStory = TakeStory();Sequence throwCatchStory = ThrowCatchStory();Sequence followStory = FollowStory();Sequence exchangeStory = ExchangeStory();List<Sequence> patterns = new ArrayList();patterns.add(giveStory);patterns.add(takeStory);patterns.add(throwCatchStory);patterns.add(exchangeStory);SequenceAligner aligner = new SequenceAligner();Mark.say(new Object[] { "Doing alignment..." });Mark.say(new Object[] { "Best Alignment Found:" });SequenceAligner.outputAlignment((Alignment)aligner.findBestAlignments(patterns, gapStory).get(0));Mark.say(new Object[] { "Ranked Alignments" });RankedSequenceAlignmentSet<Entity, Entity> alignments = aligner.findBestAlignments(patterns, gapStory);int rank = 1;
/* 633:565 */     for (Alignment<Entity, Entity> alignment : alignments)
/* 634:    */     {
/* 635:566 */       Mark.say(new Object[] {"***" });
/* 636:567 */       Mark.say(new Object[] {"Rank ", Integer.valueOf(rank), " with score of", Float.valueOf(alignment.score), ":" });
/* 637:568 */       SequenceAligner.outputAlignment(alignment);
/* 638:569 */       rank++;
/* 639:570 */       Mark.say(new Object[] {" " });
/* 640:    */     }
/* 641:573 */     alignments.globalAlignment();
/* 642:    */     
/* 643:575 */     JFrame frame = new JFrame("Story Alignment");
/* 644:    */     
/* 645:    */ 
/* 646:    */ 
/* 647:    */ 
/* 648:580 */     frame.pack();
/* 649:581 */     frame.setVisible(true);
/* 650:    */     
/* 651:583 */     frame.setDefaultCloseOperation(3);
/* 652:    */   }
/* 653:    */   
/* 654:    */   public static void pairMatchingTest()
/* 655:    */   {
/* 656:    */     try
/* 657:    */     {
/* 658:589 */       Generator generator = Generator.getGenerator();
/* 659:590 */       Translator translator = Translator.getTranslator();
/* 660:    */       
/* 661:592 */       generator.setStoryMode();
/* 662:593 */       Entity s1_A = translator.translate("Matt is a person").getElement(0);
/* 663:594 */       Entity s1_B = translator.translate("Mary is a person").getElement(0);
/* 664:595 */       Entity s1_C = translator.translate("The ball is an object").getElement(0);
/* 665:596 */       Entity s1_D = translator.translate("Matt controls the ball").getElement(0);
/* 666:597 */       Entity s1_E = translator.translate("Matt gives the ball to Mary").getElement(0);
/* 667:598 */       Entity s1_F = translator.translate("Mary controls the ball").getElement(0);
/* 668:    */       
/* 669:600 */       generator.flush();
/* 670:    */       
/* 671:602 */       Entity s2_A = translator.translate("Mark is a person").getElement(0);
/* 672:603 */       Entity s2_B = translator.translate("Sally is a person").getElement(0);
/* 673:604 */       Entity s2_C = translator.translate("The cup is an object").getElement(0);
/* 674:605 */       Entity s2_D = translator.translate("Mark controls the cup").getElement(0);
/* 675:606 */       Entity s2_E = translator.translate("Mark gives the cup to Sally").getElement(0);
/* 676:607 */       Entity s2_F = translator.translate("Sally controls the cup").getElement(0);
/* 677:    */       
/* 678:609 */       generator.flush();
/* 679:    */       
/* 680:611 */       Sequence story1 = new Sequence();
/* 681:612 */       story1.addElement(s1_A);
/* 682:613 */       story1.addElement(s1_B);
/* 683:614 */       story1.addElement(s1_C);
/* 684:615 */       story1.addElement(s1_D);
/* 685:616 */       story1.addElement(s1_E);
/* 686:617 */       story1.addElement(s1_F);
/* 687:    */       
/* 688:619 */       Sequence story2 = new Sequence();
/* 689:620 */       story2.addElement(s2_A);
/* 690:621 */       story2.addElement(s2_B);
/* 691:622 */       story2.addElement(s2_C);
/* 692:623 */       story2.addElement(s2_D);
/* 693:624 */       story2.addElement(s2_E);
/* 694:625 */       story2.addElement(s2_F);
/* 695:    */     }
/* 696:    */     catch (Exception e)
/* 697:    */     {
/* 698:627 */       e.printStackTrace();
/* 699:    */     }
/* 700:    */   }
/* 701:    */   
/* 702:    */   public static void main(String[] args)
/* 703:    */     throws Exception
/* 704:    */   {
/* 705:644 */     Generator generator = Generator.getGenerator();
/* 706:645 */     Translator translator = Translator.getTranslator();
/* 707:    */     
/* 708:    */ 
/* 709:648 */     Entity storystart = translator.translate("Mark controls the ball. Mark gives the ball to Matt. Matt controls the ball.");
/* 710:649 */     Mark.say(new Object[] {storystart.asString() });
/* 711:650 */     Mark.say(new Object[] {generator.generate(storystart) });
/* 712:    */   }
/* 713:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.Demo
 * JD-Core Version:    0.7.0.1
 */