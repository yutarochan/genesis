/*   1:    */ package translator;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Function;
/*   6:    */ import bridge.reps.entities.Relation;
/*   7:    */ import bridge.reps.entities.Sequence;
/*   8:    */ import connections.Connections;
/*   9:    */ import connections.Ports;
/*  10:    */ import genesis.GenesisGetters;
/*  11:    */ import gui.ActivityMonitor;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.Iterator;
/*  14:    */ import java.util.Vector;
/*  15:    */ import parameters.Switch;
/*  16:    */ import persistence.JCheckBoxWithMemory;
/*  17:    */ import start.Start;
/*  18:    */ import utils.Mark;
/*  19:    */ 
/*  20:    */ public class Translator
/*  21:    */   extends NewRuleSet
/*  22:    */ {
/*  23: 22 */   private boolean debug = false;
/*  24:    */   public static final String LEFT = "left";
/*  25:    */   public static final String RIGHT = "right";
/*  26:    */   public static final String RESULT = "result";
/*  27:    */   public static final String DEBUGGING_RESULT = "debugging result";
/*  28:    */   public static final String DIALOG_PORT = "dialog port";
/*  29:    */   private static Translator translator;
/*  30:    */   private GenesisGetters gauntlet;
/*  31:    */   
/*  32:    */   public static Translator getTranslator()
/*  33:    */   {
/*  34: 39 */     if (translator == null) {
/*  35: 40 */       translator = new Translator();
/*  36:    */     }
/*  37: 42 */     return translator;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Translator()
/*  41:    */   {
/*  42: 46 */     this(null);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Translator(GenesisGetters gauntlet)
/*  46:    */   {
/*  47: 54 */     this.gauntlet = gauntlet;
/*  48:    */     
/*  49: 56 */     Connections.wire("to activity monitor", this, ActivityMonitor.getActivityMonitor());
/*  50:    */     
/*  51: 58 */     Connections.getPorts(this).addSignalProcessor(PARSE, "setInput");
/*  52:    */     
/*  53:    */ 
/*  54:    */ 
/*  55: 62 */     Connections.getPorts(this).addSignalProcessor(PROCESS, "process");
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void process(Object object)
/*  59:    */   {
/*  60: 67 */     boolean debug = false;
/*  61: 68 */     String marker = null;
/*  62: 69 */     if ((object instanceof BetterSignal))
/*  63:    */     {
/*  64: 70 */       marker = (String)((BetterSignal)object).get(0, String.class);
/*  65: 71 */       object = ((BetterSignal)object).get(1, Object.class);
/*  66:    */     }
/*  67: 74 */     if (!(object instanceof Sequence)) {
/*  68: 75 */       return;
/*  69:    */     }
/*  70: 77 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Semantic expert gets:", ((Sequence)object).asString() });
/*  71: 78 */     Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.TRANSLATOR_WORKING, Boolean.valueOf(true) }));
/*  72: 79 */     Entity result = interpret(object);
/*  73: 80 */     Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.TRANSLATOR_WORKING, Boolean.valueOf(false) }));
/*  74: 81 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Semantic expert reports:", result.asString() });
/*  75: 84 */     if (marker != null) {
/*  76: 85 */       Connections.getPorts(this).transmit("dialog port", new BetterSignal(new Object[] { marker, result }));
/*  77:    */     } else {
/*  78: 88 */       Connections.getPorts(this).transmit("result", result);
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Entity parse(String s)
/*  83:    */     throws Exception
/*  84:    */   {
/*  85: 93 */     return Start.getStart().parse(s);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Entity translate(String s)
/*  89:    */     throws Exception
/*  90:    */   {
/*  91: 97 */     Entity result = interpret(parse(s));
/*  92:    */     
/*  93: 99 */     return result;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Entity translate(String s, Integer start, Integer end)
/*  97:    */     throws Exception
/*  98:    */   {
/*  99:104 */     Entity result = interpret(parse(s));
/* 100:105 */     return translate(result, start, end);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Entity translate(Entity result, Integer start, Integer end)
/* 104:    */   {
/* 105:109 */     if ((result != null) && 
/* 106:110 */       (result.relationP()) && (result.getObject().sequenceP()))
/* 107:    */     {
/* 108:111 */       Sequence roles = (Sequence)result.getObject();
/* 109:112 */       if (start != null)
/* 110:    */       {
/* 111:113 */         Entity from = new Entity(start.toString());
/* 112:114 */         from.addFeature("none");
/* 113:115 */         if (end == null) {
/* 114:116 */           roles.addElement(new Function("at", from));
/* 115:    */         } else {
/* 116:119 */           roles.addElement(new Function("from", from));
/* 117:    */         }
/* 118:    */       }
/* 119:122 */       if (end != null)
/* 120:    */       {
/* 121:123 */         Entity to = new Entity(end.toString());
/* 122:124 */         to.addFeature("none");
/* 123:125 */         if (start == null) {
/* 124:126 */           roles.addElement(new Function("at", to));
/* 125:    */         } else {
/* 126:129 */           roles.addElement(new Function("to", to));
/* 127:    */         }
/* 128:    */       }
/* 129:    */     }
/* 130:134 */     return result;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public Entity interpret(Object o)
/* 134:    */   {
/* 135:138 */     if ((o instanceof Sequence))
/* 136:    */     {
/* 137:139 */       this.parse = ((Sequence)o);
/* 138:    */       
/* 139:141 */       setInput(this.parse);
/* 140:142 */       while (rachet()) {}
/* 141:144 */       Sequence result = (Sequence)getTransformations().get(getTransformations().size() - 1);
/* 142:145 */       removeProcessedFeature(result);
/* 143:146 */       return result;
/* 144:    */     }
/* 145:157 */     return null;
/* 146:    */   }
/* 147:    */   
/* 148:    */   private void removeProcessedFeature(Entity link)
/* 149:    */   {
/* 150:161 */     if (link.hasFeature("processed")) {
/* 151:162 */       link.removeFeature("processed");
/* 152:    */     }
/* 153:164 */     if (!link.entityP()) {
/* 154:166 */       if (link.functionP())
/* 155:    */       {
/* 156:167 */         Function d = (Function)link;
/* 157:168 */         removeProcessedFeature(d.getSubject());
/* 158:    */       }
/* 159:170 */       else if (link.relationP())
/* 160:    */       {
/* 161:171 */         Relation r = (Relation)link;
/* 162:172 */         removeProcessedFeature(r.getSubject());
/* 163:173 */         removeProcessedFeature(r.getObject());
/* 164:    */       }
/* 165:175 */       else if (link.sequenceP())
/* 166:    */       {
/* 167:176 */         Sequence s = (Sequence)link;
/* 168:177 */         for (Entity element : s.getElements()) {
/* 169:178 */           removeProcessedFeature(element);
/* 170:    */         }
/* 171:    */       }
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   public boolean rachet()
/* 176:    */   {
/* 177:184 */     int size = getTransformations().size();
/* 178:185 */     transform();
/* 179:186 */     if (getTransformations().size() == size) {
/* 180:187 */       return false;
/* 181:    */     }
/* 182:189 */     return true;
/* 183:    */   }
/* 184:    */   
/* 185:    */   private class LocalGoClass
/* 186:    */     extends Thread
/* 187:    */   {
/* 188:    */     private LocalGoClass() {}
/* 189:    */     
/* 190:    */     public void run()
/* 191:    */     {
/* 192:196 */       while (Translator.this.step()) {
/* 193:197 */         if ((Translator.this.gauntlet != null) && (Switch.stepParser.isSelected())) {
/* 194:    */           try
/* 195:    */           {
/* 196:199 */             sleep(Translator.delta);
/* 197:    */           }
/* 198:    */           catch (InterruptedException e)
/* 199:    */           {
/* 200:202 */             e.printStackTrace();
/* 201:    */           }
/* 202:    */         }
/* 203:    */       }
/* 204:206 */       Sequence result = (Sequence)Translator.this.getTransformations().get(Translator.this.getTransformations().size() - 1);
/* 205:    */       
/* 206:208 */       Translator.this.reportLocalGoResult(result);
/* 207:    */     }
/* 208:    */   }
/* 209:    */   
/* 210:    */   private void reportLocalGoResult(Sequence result)
/* 211:    */   {
/* 212:215 */     Connections.getPorts(this).transmit("debugging result", result);
/* 213:    */   }
/* 214:    */   
/* 215:226 */   private static int delta = 0;
/* 216:228 */   public static String PARSE = "parse";
/* 217:228 */   public static String STEP = "step";
/* 218:228 */   public static String PROCESS = "process";
/* 219:228 */   public static String RUN = "run";
/* 220:230 */   public static String PROGRESS = "progress";
/* 221:    */   private Sequence parse;
/* 222:    */   private ArrayList<Sequence> transformations;
/* 223:238 */   private boolean transmittable = false;
/* 224:    */   
/* 225:    */   public Sequence getParse()
/* 226:    */   {
/* 227:241 */     if (this.parse == null) {
/* 228:242 */       this.parse = new Sequence();
/* 229:    */     }
/* 230:244 */     return this.parse;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public ArrayList<Sequence> getTransformations()
/* 234:    */   {
/* 235:248 */     if (this.transformations == null) {
/* 236:249 */       this.transformations = new ArrayList();
/* 237:    */     }
/* 238:251 */     return this.transformations;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void go()
/* 242:    */   {
/* 243:255 */     new LocalGoClass(null).start();
/* 244:    */   }
/* 245:    */   
/* 246:    */   private void removeParts(Entity thing, Vector v)
/* 247:    */   {
/* 248:259 */     Vector links = (Vector)v.clone();
/* 249:260 */     for (Iterator<Entity> i = links.iterator(); i.hasNext();)
/* 250:    */     {
/* 251:261 */       Entity t = (Entity)i.next();
/* 252:262 */       if (!t.isA("parse-link")) {
/* 253:263 */         v.remove(t);
/* 254:    */       }
/* 255:    */     }
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void setInput(Object o)
/* 259:    */   {
/* 260:290 */     if ((o instanceof Sequence))
/* 261:    */     {
/* 262:291 */       this.parse = ((Sequence)o);
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
/* 280:    */ 
/* 281:    */ 
/* 282:    */ 
/* 283:    */ 
/* 284:    */ 
/* 285:314 */       getTransformations().clear();
/* 286:315 */       getTransformations().add(this.parse);
/* 287:    */     }
/* 288:    */     else
/* 289:    */     {
/* 290:318 */       Mark.err(new Object[] {"Failed to recognize type in Translator.setInput", o.getClass() });
/* 291:    */     }
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void setInputAndStep(Object o)
/* 295:    */   {
/* 296:323 */     if ((o instanceof Sequence))
/* 297:    */     {
/* 298:324 */       this.parse = ((Sequence)o);
/* 299:325 */       setInput(this.parse);
/* 300:326 */       go();
/* 301:    */     }
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void setParse(Sequence parse)
/* 305:    */   {
/* 306:331 */     this.parse = parse;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void setTransformations(ArrayList<Sequence> transformations)
/* 310:    */   {
/* 311:335 */     this.transformations = transformations;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public Entity getResult()
/* 315:    */   {
/* 316:339 */     while (step()) {}
/* 317:341 */     return (Entity)getTransformations().get(getTransformations().size() - 1);
/* 318:    */   }
/* 319:    */   
/* 320:    */   public boolean step()
/* 321:    */   {
/* 322:345 */     if (rachet())
/* 323:    */     {
/* 324:347 */       Connections.getPorts(this).transmit(PROGRESS, getTransformations().get(getTransformations().size() - 1));
/* 325:348 */       return true;
/* 326:    */     }
/* 327:351 */     Sequence result = (Sequence)getTransformations().get(getTransformations().size() - 1);
/* 328:352 */     Vector v = result.getElements();
/* 329:    */     
/* 330:354 */     Sequence sequence = new Sequence("semantic-interpretation");
/* 331:355 */     for (Iterator i = v.iterator(); i.hasNext();)
/* 332:    */     {
/* 333:356 */       Entity x = (Entity)i.next();
/* 334:357 */       if (x.relationP())
/* 335:    */       {
/* 336:360 */         Relation t = (Relation)x;
/* 337:361 */         if (t.getSubject().isA("root")) {
/* 338:362 */           sequence.addElement(t.getObject());
/* 339:    */         }
/* 340:    */       }
/* 341:    */     }
/* 342:365 */     Connections.getPorts(this).transmit(sequence);
/* 343:366 */     return false;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public boolean step(Object o)
/* 347:    */   {
/* 348:371 */     return step();
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void transform()
/* 352:    */   {
/* 353:378 */     int lastIndex = getTransformations().size() - 1;
/* 354:379 */     Sequence sequence = (Sequence)getTransformations().get(lastIndex);
/* 355:380 */     Sequence result = transform(sequence);
/* 356:381 */     if (result != null) {
/* 357:382 */       getTransformations().add(result);
/* 358:    */     }
/* 359:    */   }
/* 360:    */   
/* 361:    */   private boolean transform(BasicRule runnable, Sequence s)
/* 362:    */   {
/* 363:387 */     runnable.setLinks(s);
/* 364:388 */     if (s.getElements().size() < 1) {
/* 365:389 */       return false;
/* 366:    */     }
/* 367:391 */     if ((runnable instanceof BasicRule3)) {
/* 368:393 */       for (int i = 0; i < s.getElements().size(); i++) {
/* 369:394 */         if (s.getElements().get(i) != null) {
/* 370:397 */           for (int j = i + 1; (j != i) && (j < s.getElements().size()); j++) {
/* 371:398 */             if (s.getElements().get(j) != null) {
/* 372:401 */               for (int k = j + 1; (k != j) && (k != i) && (k < s.getElements().size()); k++) {
/* 373:402 */                 if (s.getElements().get(k) != null)
/* 374:    */                 {
/* 375:405 */                   runnable.setLinks((Entity)s.getElements().get(i), (Entity)s.getElements().get(j), (Entity)s.getElements().get(k));
/* 376:406 */                   runnable.run();
/* 377:407 */                   if (runnable.hasSucceeded())
/* 378:    */                   {
/* 379:408 */                     if (Switch.showTranslationDetails.isSelected()) {
/* 380:409 */                       Mark.say(new Object[] {"Rule " + runnable.getClass().getName() + " succeeded" });
/* 381:    */                     }
/* 382:411 */                     return true;
/* 383:    */                   }
/* 384:    */                 }
/* 385:    */               }
/* 386:    */             }
/* 387:    */           }
/* 388:    */         }
/* 389:    */       }
/* 390:417 */     } else if ((runnable instanceof BasicRule2)) {
/* 391:419 */       for (int i = 0; i < s.getElements().size(); i++) {
/* 392:420 */         if (s.getElements() != null) {
/* 393:423 */           for (int j = i + 1; (j != i) && (j < s.getElements().size()); j++) {
/* 394:424 */             if (s.getElements().get(j) != null)
/* 395:    */             {
/* 396:427 */               runnable.setLinks((Entity)s.getElements().get(i), (Entity)s.getElements().get(j));
/* 397:428 */               runnable.run();
/* 398:429 */               if (runnable.hasSucceeded())
/* 399:    */               {
/* 400:430 */                 if (Switch.showTranslationDetails.isSelected()) {
/* 401:431 */                   Mark.say(new Object[] {"Rule " + runnable.getClass().getName() + " succeeded" });
/* 402:    */                 }
/* 403:433 */                 return true;
/* 404:    */               }
/* 405:    */             }
/* 406:    */           }
/* 407:    */         }
/* 408:    */       }
/* 409:438 */     } else if ((runnable instanceof BasicRule)) {
/* 410:440 */       for (int i = 0; i < s.getElements().size(); i++) {
/* 411:441 */         if (s.getElements().get(i) != null)
/* 412:    */         {
/* 413:444 */           runnable.setLinks((Entity)s.getElements().get(i));
/* 414:445 */           runnable.run();
/* 415:446 */           if (runnable.hasSucceeded())
/* 416:    */           {
/* 417:447 */             if (Switch.showTranslationDetails.isSelected()) {
/* 418:448 */               Mark.say(new Object[] {"Rule " + runnable.getClass().getName() + " succeeded" });
/* 419:    */             }
/* 420:450 */             return true;
/* 421:    */           }
/* 422:    */         }
/* 423:    */       }
/* 424:    */     }
/* 425:455 */     return false;
/* 426:    */   }
/* 427:    */   
/* 428:    */   private Sequence transform(Sequence s)
/* 429:    */   {
/* 430:463 */     for (Rule rule : getRuleSet())
/* 431:    */     {
/* 432:465 */       BasicRule runnable = rule.getRunnable();
/* 433:466 */       if ((runnable != null) && 
/* 434:467 */         (transform(runnable, s))) {
/* 435:470 */         return s;
/* 436:    */       }
/* 437:    */     }
/* 438:475 */     return null;
/* 439:    */   }
/* 440:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     translator.Translator
 * JD-Core Version:    0.7.0.1
 */