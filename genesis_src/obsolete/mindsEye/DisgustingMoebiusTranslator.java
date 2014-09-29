/*   1:    */ package obsolete.mindsEye;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Function;
/*   6:    */ import bridge.reps.entities.Relation;
/*   7:    */ import bridge.reps.entities.Sequence;
/*   8:    */ import connections.AbstractWiredBox;
/*   9:    */ import connections.Connections;
/*  10:    */ import connections.Ports;
/*  11:    */ import java.text.DateFormat;
/*  12:    */ import java.text.ParseException;
/*  13:    */ import java.text.SimpleDateFormat;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.Arrays;
/*  16:    */ import java.util.Comparator;
/*  17:    */ import java.util.Date;
/*  18:    */ import java.util.HashMap;
/*  19:    */ import java.util.TreeSet;
/*  20:    */ import start.Generator;
/*  21:    */ import text.Html;
/*  22:    */ import utils.Mark;
/*  23:    */ 
/*  24:    */ public class DisgustingMoebiusTranslator
/*  25:    */   extends AbstractWiredBox
/*  26:    */ {
/*  27: 25 */   private int stop = 1;
/*  28: 25 */   private int start = 0;
/*  29: 27 */   private int mode = this.start;
/*  30: 29 */   public static String INNERESE = "innerese";
/*  31: 31 */   public static String FROM_IMPACT = "IMPACT analysis";
/*  32: 33 */   public static String TO_TALKER = "say it";
/*  33: 35 */   public static String TO_TEXT_VIEWER = "write it";
/*  34: 37 */   public static String TIME = "time";
/*  35: 39 */   public static String SHOW_ALL_COMMENTS = "show all comments";
/*  36: 41 */   private TreeSet<ThingTimeTriple> triples = new TreeSet(new TripleComparitor(null));
/*  37: 43 */   private ArrayList<ThingTimeTriple> tripleList = new ArrayList();
/*  38: 45 */   private HashMap<String, Entity> things = new HashMap();
/*  39: 47 */   private DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
/*  40: 51 */   private ArrayList<String> significantEvents = new ArrayList();
/*  41: 53 */   private String[] significantEventArray = { "give", "take", "throw", "catch", "drop", "pick_up" };
/*  42: 55 */   private ThingTimeTriple previousTriple = null;
/*  43: 57 */   private HashMap<String, ThingTimeTriple> previousTriples = new HashMap();
/*  44:    */   private long startTime;
/*  45:    */   
/*  46:    */   public DisgustingMoebiusTranslator()
/*  47:    */   {
/*  48:    */     try
/*  49:    */     {
/*  50: 61 */       this.startTime = this.format.parse("2000-1-1T00:00:00.000").getTime();
/*  51:    */     }
/*  52:    */     catch (ParseException e)
/*  53:    */     {
/*  54: 64 */       Mark.say(new Object[] {"Could not comptute start time in DisgustingMoebiusTranslator constructor" });
/*  55: 65 */       e.printStackTrace();
/*  56:    */     }
/*  57: 67 */     this.significantEvents.addAll(Arrays.asList(this.significantEventArray));
/*  58: 68 */     Connections.getPorts(this).addSignalProcessor(FROM_IMPACT, "processImpactResults");
/*  59: 69 */     Connections.getPorts(this).addSignalProcessor(TIME, "commentOnAction");
/*  60: 70 */     Connections.getPorts(this).addSignalProcessor(SHOW_ALL_COMMENTS, "showAllComments");
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void showAllComments(Object signal)
/*  64:    */   {
/*  65: 75 */     for (ThingTimeTriple triple : this.triples)
/*  66:    */     {
/*  67: 76 */       Mark.say(new Object[] {"Transmitting", triple.t.asString() });
/*  68: 77 */       Connections.getPorts(this).transmit(TO_TALKER, triple.t);
/*  69:    */       try
/*  70:    */       {
/*  71: 79 */         Thread.sleep(3000L);
/*  72:    */       }
/*  73:    */       catch (InterruptedException e)
/*  74:    */       {
/*  75: 82 */         e.printStackTrace();
/*  76:    */       }
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void commentOnAction(Object signal)
/*  81:    */   {
/*  82: 88 */     if ((signal instanceof Long))
/*  83:    */     {
/*  84: 89 */       long time = ((Long)signal).longValue();
/*  85: 90 */       if (this.tripleList.isEmpty()) {
/*  86: 91 */         return;
/*  87:    */       }
/*  88: 94 */       ThingTimeTriple firstTriple = (ThingTimeTriple)this.tripleList.get(0);
/*  89:    */       
/*  90: 96 */       long reference = 0L;
/*  91: 98 */       if (this.mode == this.start) {
/*  92: 99 */         reference = firstTriple.from;
/*  93:102 */       } else if (this.mode == this.stop) {
/*  94:103 */         reference = firstTriple.to;
/*  95:    */       }
/*  96:106 */       if (time > reference) {
/*  97:    */         try
/*  98:    */         {
/*  99:108 */           String english = firstTriple.english;
/* 100:109 */           double offset = reference / 1000.0D;
/* 101:110 */           boolean noteworthy = (this.previousTriple == null) || (isNoteworthy(firstTriple, this.previousTriple));
/* 102:    */           
/* 103:112 */           String displayedText = ((offset > 0.0D ? String.format("%.1f ", new Object[] { Double.valueOf(offset) }) : "") + english).trim();
/* 104:114 */           if (noteworthy)
/* 105:    */           {
/* 106:115 */             Connections.getPorts(this).transmit(TO_TEXT_VIEWER, new BetterSignal(new Object[] { "Commentary", Html.line(Html.bold(displayedText)) }));
/* 107:116 */             Connections.getPorts(this).transmit(TO_TALKER, english);
/* 108:    */           }
/* 109:    */           else
/* 110:    */           {
/* 111:119 */             Connections.getPorts(this).transmit(TO_TEXT_VIEWER, new BetterSignal(new Object[] { "Commentary", Html.line(displayedText) }));
/* 112:    */           }
/* 113:121 */           Connections.getPorts(this).transmit(INNERESE, firstTriple.t);
/* 114:    */         }
/* 115:    */         catch (Exception e)
/* 116:    */         {
/* 117:125 */           Mark.err(new Object[] {"Blew out attempting to transmit IMPACT result", firstTriple });
/* 118:    */         }
/* 119:    */         finally
/* 120:    */         {
/* 121:128 */           this.previousTriple = firstTriple;
/* 122:129 */           this.tripleList.remove(firstTriple);
/* 123:    */         }
/* 124:    */       }
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   private boolean isNoteworthy(ThingTimeTriple firstTriple, ThingTimeTriple previousTriple)
/* 129:    */   {
/* 130:137 */     Mark.say(
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
/* 174:181 */       new Object[] { "Inputs to isNoteworthy", firstTriple.english, previousTriple.english });
/* 175:138 */     if (previousTriple == null)
/* 176:    */     {
/* 177:139 */       resetReference(firstTriple);
/* 178:140 */       return true;
/* 179:    */     }
/* 180:143 */     if (previousTriple.english.equals(firstTriple.english))
/* 181:    */     {
/* 182:144 */       Mark.say(new Object[] {"English is the same" });
/* 183:    */     }
/* 184:    */     else
/* 185:    */     {
/* 186:147 */       String type = firstTriple.t.getType();
/* 187:148 */       Mark.say(new Object[] {"Significant types", type, this.significantEvents });
/* 188:150 */       if (this.significantEvents.contains(type))
/* 189:    */       {
/* 190:151 */         Mark.say(new Object[] {firstTriple.english, "is significant" });
/* 191:    */         
/* 192:    */ 
/* 193:    */ 
/* 194:155 */         ThingTimeTriple previousEventOfSameType = (ThingTimeTriple)this.previousTriples.get(type);
/* 195:157 */         if (previousEventOfSameType == null)
/* 196:    */         {
/* 197:158 */           resetReference(firstTriple);
/* 198:159 */           return true;
/* 199:    */         }
/* 200:162 */         if (overlaps(firstTriple, previousEventOfSameType))
/* 201:    */         {
/* 202:167 */           Connections.getPorts(this).transmit(TO_TEXT_VIEWER, new BetterSignal(new Object[] { "Commentary", Html.line("Events overlap [" + 
/* 203:168 */             previousEventOfSameType.from / 1000L + " " + previousEventOfSameType.to / 1000L + "] [" + firstTriple.from / 1000L + " " + 
/* 204:169 */             firstTriple.to / 1000L + "] ") }));
/* 205:    */         }
/* 206:    */         else
/* 207:    */         {
/* 208:172 */           resetReference(firstTriple);
/* 209:173 */           return true;
/* 210:    */         }
/* 211:    */       }
/* 212:    */       else
/* 213:    */       {
/* 214:177 */         Mark.say(new Object[] {firstTriple.english, "NOT significant" });
/* 215:    */       }
/* 216:    */     }
/* 217:180 */     return false;
/* 218:    */   }
/* 219:    */   
/* 220:    */   private void resetReference(ThingTimeTriple triple)
/* 221:    */   {
/* 222:184 */     this.previousTriples.put(triple.t.getType(), triple);
/* 223:185 */     this.previousTriple = triple;
/* 224:    */   }
/* 225:    */   
/* 226:    */   private boolean overlaps(ThingTimeTriple a, ThingTimeTriple b)
/* 227:    */   {
/* 228:189 */     return (a.from < b.to) && (a.to > b.from);
/* 229:    */   }
/* 230:    */   
/* 231:    */   private class TripleComparitor
/* 232:    */     implements Comparator
/* 233:    */   {
/* 234:    */     private TripleComparitor() {}
/* 235:    */     
/* 236:    */     public int compare(Object o1, Object o2)
/* 237:    */     {
/* 238:196 */       DisgustingMoebiusTranslator.ThingTimeTriple x = (DisgustingMoebiusTranslator.ThingTimeTriple)o1;
/* 239:197 */       DisgustingMoebiusTranslator.ThingTimeTriple y = (DisgustingMoebiusTranslator.ThingTimeTriple)o2;
/* 240:198 */       if (DisgustingMoebiusTranslator.this.mode == DisgustingMoebiusTranslator.this.stop)
/* 241:    */       {
/* 242:199 */         if (x.to > y.to) {
/* 243:200 */           return 1;
/* 244:    */         }
/* 245:203 */         return -1;
/* 246:    */       }
/* 247:206 */       if (DisgustingMoebiusTranslator.this.mode == DisgustingMoebiusTranslator.this.start)
/* 248:    */       {
/* 249:207 */         if (x.from > y.from) {
/* 250:208 */           return 1;
/* 251:    */         }
/* 252:211 */         return -1;
/* 253:    */       }
/* 254:214 */       return 0;
/* 255:    */     }
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void processImpactResults(Object o)
/* 259:    */   {
/* 260:220 */     if ((o instanceof BetterSignal))
/* 261:    */     {
/* 262:221 */       BetterSignal signal = (BetterSignal)o;
/* 263:222 */       String input = (String)signal.get(0, String.class);
/* 264:223 */       Connections.getPorts(this).transmit(TO_TEXT_VIEWER, new BetterSignal(new Object[] { "Commentary", "clear" }));
/* 265:224 */       translate(input);
/* 266:    */     }
/* 267:    */   }
/* 268:    */   
/* 269:    */   private void translate(String input)
/* 270:    */   {
/* 271:229 */     if (input.trim().isEmpty())
/* 272:    */     {
/* 273:230 */       Mark.err(new Object[] {"IMPACT produced nothing to translate" });
/* 274:231 */       return;
/* 275:    */     }
/* 276:233 */     this.things.clear();
/* 277:234 */     this.triples.clear();
/* 278:235 */     this.previousTriple = null;
/* 279:236 */     this.previousTriples.clear();
/* 280:    */     
/* 281:238 */     String[] sentences = input.trim().split("\\)\\.");
/* 282:240 */     for (String s : sentences)
/* 283:    */     {
/* 284:241 */       s = s.trim();
/* 285:242 */       Mark.say(new Object[] {"Sentence:", s });
/* 286:    */       try
/* 287:    */       {
/* 288:244 */         String[] parts = s.split("\\(");
/* 289:245 */         ThingTimeTriple triple = constructGenesisAction(parts[0].trim(), parts[1].trim());
/* 290:246 */         this.triples.add(triple);
/* 291:247 */         this.tripleList.clear();
/* 292:248 */         this.tripleList.addAll(this.triples);
/* 293:    */       }
/* 294:    */       catch (Exception e)
/* 295:    */       {
/* 296:252 */         Mark.say(new Object[] {"Blew out trying to process IMPACT result,", s });
/* 297:    */         
/* 298:254 */         e.printStackTrace();
/* 299:    */       }
/* 300:    */     }
/* 301:    */   }
/* 302:    */   
/* 303:    */   private Entity getThing(String s)
/* 304:    */   {
/* 305:260 */     Entity t = (Entity)this.things.get(s);
/* 306:261 */     if (t == null)
/* 307:    */     {
/* 308:263 */       int space = s.indexOf(" ");
/* 309:    */       
/* 310:265 */       t = new Entity(s.substring(1, space));
/* 311:    */     }
/* 312:268 */     return t;
/* 313:    */   }
/* 314:    */   
/* 315:    */   private ThingTimeTriple constructGenesisAction(String verb, String roleStrings)
/* 316:    */   {
/* 317:272 */     Entity subjectThing = null;
/* 318:273 */     long fromMillis = -1L;
/* 319:274 */     long toMillis = -1L;
/* 320:275 */     String[] roleString = roleStrings.split(",");
/* 321:276 */     Sequence roles = new Sequence("roles");
/* 322:277 */     for (String r : roleString)
/* 323:    */     {
/* 324:278 */       r = r.trim();
/* 325:    */       
/* 326:280 */       int index = r.indexOf(" ");
/* 327:281 */       String name = r.substring(0, index).trim();
/* 328:282 */       String value = r.substring(index).trim();
/* 329:284 */       if (name.equalsIgnoreCase("subject:object"))
/* 330:    */       {
/* 331:285 */         subjectThing = getThing(value);
/* 332:286 */         subjectThing.addFeature("indefinite");
/* 333:    */       }
/* 334:288 */       else if (name.equalsIgnoreCase("from:time"))
/* 335:    */       {
/* 336:289 */         fromMillis = extractMillis(value);
/* 337:    */       }
/* 338:291 */       else if (name.equalsIgnoreCase("to:time"))
/* 339:    */       {
/* 340:292 */         toMillis = extractMillis(value);
/* 341:    */       }
/* 342:294 */       else if (name.endsWith(":object"))
/* 343:    */       {
/* 344:295 */         int end = name.indexOf(":object");
/* 345:296 */         String role = name.substring(0, end);
/* 346:297 */         Entity objectThing = getThing(value);
/* 347:299 */         if (objectThing.getType().equals(subjectThing.getType())) {
/* 348:300 */           objectThing.addFeature("another");
/* 349:    */         } else {
/* 350:303 */           objectThing.addFeature("indefinite");
/* 351:    */         }
/* 352:305 */         roles.addElement(new Function(role, objectThing));
/* 353:    */       }
/* 354:    */     }
/* 355:308 */     Relation relation = new Relation(verb, subjectThing, roles);
/* 356:309 */     String english = Generator.getGenerator().generate(relation);
/* 357:    */     
/* 358:    */ 
/* 359:    */ 
/* 360:313 */     return new ThingTimeTriple(english, relation, fromMillis - this.startTime, toMillis - this.startTime);
/* 361:    */   }
/* 362:    */   
/* 363:    */   private long extractMillis(String value)
/* 364:    */   {
/* 365:318 */     int start = value.indexOf("\"");
/* 366:319 */     int end = value.lastIndexOf("\"");
/* 367:320 */     String time = value.substring(start + 1, end);
/* 368:    */     
/* 369:322 */     Date result = null;
/* 370:    */     try
/* 371:    */     {
/* 372:324 */       result = this.format.parse(time);
/* 373:    */     }
/* 374:    */     catch (ParseException e)
/* 375:    */     {
/* 376:327 */       Mark.err(new Object[] {"Unalble to get milliseconds from", value });
/* 377:328 */       return 0L;
/* 378:    */     }
/* 379:331 */     return result.getTime();
/* 380:    */   }
/* 381:    */   
/* 382:    */   private class ThingTimeTriple
/* 383:    */   {
/* 384:    */     long from;
/* 385:    */     long to;
/* 386:    */     Entity t;
/* 387:    */     String english;
/* 388:    */     
/* 389:    */     public ThingTimeTriple(String english, Entity t, long from, long to)
/* 390:    */     {
/* 391:344 */       this.english = english;
/* 392:345 */       this.t = t;
/* 393:346 */       this.from = from;
/* 394:347 */       this.to = to;
/* 395:    */     }
/* 396:    */     
/* 397:    */     public String toString()
/* 398:    */     {
/* 399:351 */       return "<" + this.t + ", " + this.from + ", " + this.to + ">";
/* 400:    */     }
/* 401:    */   }
/* 402:    */   
/* 403:    */   public static void main(String[] ignore)
/* 404:    */     throws InterruptedException
/* 405:    */   {
/* 406:358 */     String arg = "drop(subject:object \"Object 1\", object:object \"Object 0\", from:time \"2011-08-25T20:30:08.733\", to:time \"2011-08-25T20:30:10.300\").";
/* 407:359 */     arg = arg + "drop(subject:object \"Object 1\", object:object \"Object 0\", from:time \"2011-08-25T20:30:08.733\", to:time \"2011-08-25T20:30:10.000\").";
/* 408:    */     
/* 409:    */ 
/* 410:    */ 
/* 411:363 */     arg = arg + "throw(subject:object \"Object 1\", object:object \"Object 0\", from:time \"2011-08-25T20:30:08.733\", to:time \"2011-08-25T20:30:09.467\").";
/* 412:364 */     arg = arg + "bounce(subject:object \"Object 0\", from:time \"2011-08-25T20:30:08.933\", to:time \"2011-08-25T20:30:09.767\").";
/* 413:    */     
/* 414:    */ 
/* 415:    */ 
/* 416:    */ 
/* 417:    */ 
/* 418:    */ 
/* 419:    */ 
/* 420:    */ 
/* 421:    */ 
/* 422:    */ 
/* 423:    */ 
/* 424:    */ 
/* 425:    */ 
/* 426:    */ 
/* 427:    */ 
/* 428:    */ 
/* 429:    */ 
/* 430:    */ 
/* 431:    */ 
/* 432:    */ 
/* 433:385 */     DisgustingMoebiusTranslator translator = new DisgustingMoebiusTranslator();
/* 434:386 */     translator.translate(arg);
/* 435:387 */     translator.commentOnAction(Long.valueOf(2000L));
/* 436:388 */     Thread.sleep(2000L);
/* 437:389 */     translator.commentOnAction(Long.valueOf(4000L));
/* 438:    */   }
/* 439:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     obsolete.mindsEye.DisgustingMoebiusTranslator
 * JD-Core Version:    0.7.0.1
 */