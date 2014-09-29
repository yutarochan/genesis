/*   1:    */ package start;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import adamKraft.RemoteAnnotations;
/*   5:    */ import bridge.reps.entities.Entity;
/*   6:    */ import bridge.reps.entities.Sequence;
/*   7:    */ import connections.AbstractWiredBox;
/*   8:    */ import connections.Connections;
/*   9:    */ import connections.Ports;
/*  10:    */ import genesis.GenesisGetters;
/*  11:    */ import gui.WiredSplitPane;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import models.MentalModel;
/*  15:    */ import portico.ElizaMatcher;
/*  16:    */ import transitionSpace.Co57Processor;
/*  17:    */ import tts.Talker;
/*  18:    */ import utils.Mark;
/*  19:    */ 
/*  20:    */ public class StartPreprocessor
/*  21:    */   extends AbstractWiredBox
/*  22:    */ {
/*  23: 27 */   private boolean debug = false;
/*  24:    */   private static final String FRAME = "frame port";
/*  25:    */   public static final String SHORTCUT = "stage direction port";
/*  26:    */   public static final String SELF = "self";
/*  27:    */   public static final String VIDEO_MEMORY_PORT = "video memory port";
/*  28:    */   public static final String RESET_TEXT_DISPLAYS = "reset port";
/*  29: 39 */   private boolean listenToCo57 = false;
/*  30:    */   public static final String TRACES_PORT = "traces port";
/*  31:    */   public static final String STORY_TEXT = "story text";
/*  32: 45 */   private int SILENT = 0;
/*  33: 45 */   private int COMMONSENSE = 1;
/*  34: 45 */   private int CONCEPT = 2;
/*  35: 45 */   private int STORY = 3;
/*  36: 47 */   private int mode = this.SILENT;
/*  37: 49 */   public static String RESET_STATS_PORT = "Reset stats port";
/*  38: 51 */   public static String TO_TEXT_ENTRY_BOX = "To text entry box";
/*  39:    */   private static StartPreprocessor startPreprocessor;
/*  40:    */   
/*  41:    */   public static StartPreprocessor getStartPreprocessor()
/*  42:    */   {
/*  43: 56 */     if (startPreprocessor == null) {
/*  44: 57 */       startPreprocessor = new StartPreprocessor();
/*  45:    */     }
/*  46: 59 */     return startPreprocessor;
/*  47:    */   }
/*  48:    */   
/*  49: 62 */   ElizaMatcher matcher = ElizaMatcher.getElizaMatcher();
/*  50:    */   
/*  51:    */   public StartPreprocessor()
/*  52:    */   {
/*  53: 65 */     Connections.getPorts(this).addSignalProcessor("process");
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void process(Object object)
/*  57:    */   {
/*  58: 85 */     if ((object instanceof Entity))
/*  59:    */     {
/*  60: 86 */       if ((object instanceof Sequence)) {
/*  61: 87 */         for (Entity t : ((Sequence)object).getElements())
/*  62:    */         {
/*  63: 88 */           String sentence = Generator.getGenerator().generate(t);
/*  64: 89 */           if (sentence != null) {
/*  65: 90 */             Connections.getPorts(this).transmit(sentence);
/*  66:    */           } else {
/*  67: 93 */             Mark.say(new Object[] {"Unable to generate from", ((Entity)object).asString() });
/*  68:    */           }
/*  69:    */         }
/*  70:    */       }
/*  71: 97 */       return;
/*  72:    */     }
/*  73: 99 */     if (processAttentionTrace(object)) {
/*  74:100 */       return;
/*  75:    */     }
/*  76:102 */     if (!(object instanceof String)) {
/*  77:103 */       return;
/*  78:    */     }
/*  79:105 */     String input = (String)object;
/*  80:106 */     int index = input.indexOf(';');
/*  81:107 */     if (index > 0)
/*  82:    */     {
/*  83:108 */       if (index == 0)
/*  84:    */       {
/*  85:109 */         Mark.err(new Object[] {"Semicolon at beginning of sentence string in StartPreprocessor.process" });
/*  86:110 */         process(input.substring(1));
/*  87:111 */         return;
/*  88:    */       }
/*  89:113 */       String[] parts = input.split(";");
/*  90:114 */       for (int i = 0; i < parts.length - 1; i++)
/*  91:    */       {
/*  92:115 */         String antecedent = trimAndRemovePeriod(parts[i]);
/*  93:116 */         String consequent = trimAndRemovePeriod(parts[(i + 1)]);
/*  94:117 */         process("Possibly, " + consequent + " because " + antecedent + ".");
/*  95:    */       }
/*  96:119 */       return;
/*  97:    */     }
/*  98:122 */     if (processStageDirections(input)) {
/*  99:124 */       return;
/* 100:    */     }
/* 101:132 */     if (processFrame(input)) {
/* 102:133 */       return;
/* 103:    */     }
/* 104:135 */     if (processWithEliza(input)) {
/* 105:137 */       return;
/* 106:    */     }
/* 107:141 */     if (this.mode == this.STORY) {
/* 108:142 */       Connections.getPorts(this).transmit("story text", new BetterSignal(new Object[] { "Story teller", input }));
/* 109:    */     }
/* 110:145 */     Connections.getPorts(this).transmit(input);
/* 111:    */   }
/* 112:    */   
/* 113:    */   private String trimAndRemovePeriod(String string)
/* 114:    */   {
/* 115:149 */     string = string.trim();
/* 116:150 */     if (string.endsWith(".")) {
/* 117:151 */       return string.substring(0, string.length() - 1);
/* 118:    */     }
/* 119:153 */     return string;
/* 120:    */   }
/* 121:    */   
/* 122:    */   private String removetMarker(String input)
/* 123:    */   {
/* 124:157 */     int index = input.indexOf(':');
/* 125:158 */     if (index < 0) {
/* 126:159 */       return null;
/* 127:    */     }
/* 128:161 */     return input.substring(index + 1);
/* 129:    */   }
/* 130:    */   
/* 131:    */   private String extractMarker(String input)
/* 132:    */   {
/* 133:165 */     int index = input.indexOf(':');
/* 134:166 */     if (index < 0) {
/* 135:167 */       return null;
/* 136:    */     }
/* 137:169 */     return input.substring(0, index);
/* 138:    */   }
/* 139:    */   
/* 140:    */   private boolean markedText(String input)
/* 141:    */   {
/* 142:173 */     if (input.contains(":")) {
/* 143:174 */       return true;
/* 144:    */     }
/* 145:176 */     return false;
/* 146:    */   }
/* 147:    */   
/* 148:    */   private boolean processAttentionTrace(Object input)
/* 149:    */   {
/* 150:180 */     if (((input instanceof HashMap)) && (getListenToCo57()))
/* 151:    */     {
/* 152:183 */       Co57Processor.getCo57Processor();
/* 153:184 */       Connections.getPorts(this).transmit("traces port", input);
/* 154:185 */       return true;
/* 155:    */     }
/* 156:187 */     return false;
/* 157:    */   }
/* 158:    */   
/* 159:    */   private boolean processFrame(String input)
/* 160:    */   {
/* 161:191 */     if (input.isEmpty()) {
/* 162:192 */       return false;
/* 163:    */     }
/* 164:194 */     if (input.charAt(0) == '!') {
/* 165:195 */       input = input.substring(1);
/* 166:    */     } else {
/* 167:200 */       return false;
/* 168:    */     }
/* 169:202 */     HashMap<String, String> result = new HashMap();
/* 170:    */     
/* 171:    */ 
/* 172:205 */     ArrayList<String> keysAndValues = split(input.substring(1, input.length() - 1));
/* 173:206 */     String key = null;
/* 174:207 */     String value = "";
/* 175:208 */     int i = 0;
/* 176:    */     
/* 177:210 */     String word = (String)keysAndValues.get(i);
/* 178:211 */     if (isKey(word))
/* 179:    */     {
/* 180:212 */       if (key != null)
/* 181:    */       {
/* 182:213 */         result.put(strip(key), value);
/* 183:214 */         value = "";
/* 184:    */       }
/* 185:216 */       key = word;
/* 186:    */     }
/* 187:    */     else
/* 188:    */     {
/* 189:219 */       value = value.trim() + " " + word;
/* 190:    */     }
/* 191:221 */     i++;
/* 192:222 */     if (i >= keysAndValues.size())
/* 193:    */     {
/* 194:223 */       if (key != null) {
/* 195:224 */         result.put(strip(key), value);
/* 196:    */       }
/* 197:    */     }
/* 198:    */     else
/* 199:    */     {
/* 200:228 */       analyze(input, result);
/* 201:229 */       return true;
/* 202:    */     }
/* 203:231 */     return false;
/* 204:    */   }
/* 205:    */   
/* 206:    */   private void analyze(String source, HashMap<String, String> result)
/* 207:    */   {
/* 208:236 */     Mark.say(new Object[] {"Frame analyzer dealing with", source });
/* 209:    */     
/* 210:    */ 
/* 211:239 */     Connections.getPorts(this).transmit("frame port", result);
/* 212:    */   }
/* 213:    */   
/* 214:    */   private ArrayList<String> split(String x)
/* 215:    */   {
/* 216:243 */     x = x.trim();
/* 217:244 */     ArrayList<String> result = new ArrayList();
/* 218:246 */     while (!x.isEmpty())
/* 219:    */     {
/* 220:249 */       int index = x.indexOf(' ');
/* 221:250 */       if (index >= 0)
/* 222:    */       {
/* 223:251 */         result.add(x.substring(0, index).trim());
/* 224:252 */         x = x.substring(index).trim();
/* 225:    */       }
/* 226:    */       else
/* 227:    */       {
/* 228:255 */         result.add(x.trim());
/* 229:    */       }
/* 230:    */     }
/* 231:260 */     return result;
/* 232:    */   }
/* 233:    */   
/* 234:    */   private String strip(String key)
/* 235:    */   {
/* 236:264 */     return key.substring(0, key.length() - 1);
/* 237:    */   }
/* 238:    */   
/* 239:    */   private boolean isKey(String word)
/* 240:    */   {
/* 241:268 */     if (word.charAt(word.length() - 1) == ':') {
/* 242:269 */       return true;
/* 243:    */     }
/* 244:271 */     return false;
/* 245:    */   }
/* 246:    */   
/* 247:    */   private boolean processWithEliza(String input)
/* 248:    */   {
/* 249:277 */     if (this.matcher.match("?t is a kind of personality trait", input))
/* 250:    */     {
/* 251:278 */       String name = (String)this.matcher.getBindings().get("?t");
/* 252:279 */       name = removeQuotes(name);
/* 253:280 */       Connections.getPorts(this).transmit(Start.STAGE_DIRECTION_PORT, new BetterSignal(new Object[] { "LoadPersonalityFile", name }));
/* 254:    */       
/* 255:282 */       return false;
/* 256:    */     }
/* 257:285 */     if (this.matcher.match("Load ?t personality file", input))
/* 258:    */     {
/* 259:286 */       String name = (String)this.matcher.getBindings().get("?t");
/* 260:    */       
/* 261:288 */       Connections.getPorts(this).transmit(Start.STAGE_DIRECTION_PORT, new BetterSignal(new Object[] { "LoadPersonalityFile", name }));
/* 262:289 */       return true;
/* 263:    */     }
/* 264:291 */     if (this.matcher.match("Set left panel to *t", input))
/* 265:    */     {
/* 266:292 */       String name = (String)this.matcher.getBindings().get("*t");
/* 267:293 */       Connections.getPorts(this).transmit("set pane", new BetterSignal(new Object[] { "leftPanel", name }));
/* 268:294 */       return true;
/* 269:    */     }
/* 270:296 */     if (this.matcher.match("Set right panel to *t", input))
/* 271:    */     {
/* 272:297 */       String name = (String)this.matcher.getBindings().get("*t");
/* 273:298 */       Connections.getPorts(this).transmit("set pane", new BetterSignal(new Object[] { "rightPanel", name }));
/* 274:299 */       return true;
/* 275:    */     }
/* 276:301 */     if (this.matcher.match("Set bottom panel to *t", input))
/* 277:    */     {
/* 278:302 */       String name = (String)this.matcher.getBindings().get("*t");
/* 279:303 */       Connections.getPorts(this).transmit("set pane", new BetterSignal(new Object[] { "bottomPanel", name }));
/* 280:304 */       return true;
/* 281:    */     }
/* 282:306 */     if ((this.matcher.match("Interpret video named *t", input)) || (this.matcher.match("Interpret video *t", input)))
/* 283:    */     {
/* 284:307 */       String name = (String)this.matcher.getBindings().get("*t");
/* 285:308 */       name = trimOff('"', name);
/* 286:309 */       Mark.say(new Object[] {"Interpreting video titled", name });
/* 287:310 */       Connections.getPorts(this).transmit("self", "Start video");
/* 288:311 */       String message = name + ".mov";
/* 289:312 */       RemoteAnnotations.getInstance().playVideo(message);
/* 290:313 */       return true;
/* 291:    */     }
/* 292:315 */     if (this.matcher.match("Cut connection to *t", input))
/* 293:    */     {
/* 294:316 */       String name = (String)this.matcher.getBindings().get("*t");
/* 295:317 */       name = trimOff('"', name);
/* 296:    */       
/* 297:319 */       MentalModel model = MentalModel.getGlobalMentalModel(name);
/* 298:320 */       Connections.disconnect(Start.STAGE_DIRECTION_PORT, getStartPreprocessor(), Start.STAGE_DIRECTION_PORT, model
/* 299:321 */         .getStoryProcessor());
/* 300:322 */       Connections.disconnect("next", GenesisGetters.getAnaphoraExpert(), model.getStoryProcessor());
/* 301:323 */       return true;
/* 302:    */     }
/* 303:325 */     if (this.matcher.match("Open connection to *t", input))
/* 304:    */     {
/* 305:326 */       String name = (String)this.matcher.getBindings().get("*t");
/* 306:327 */       name = trimOff('"', name);
/* 307:328 */       Mark.say(new Object[] {"Value of mental model is", name });
/* 308:329 */       MentalModel model = MentalModel.getGlobalMentalModel(name);
/* 309:330 */       Connections.wire(Start.STAGE_DIRECTION_PORT, getStartPreprocessor(), Start.STAGE_DIRECTION_PORT, model
/* 310:331 */         .getStoryProcessor());
/* 311:332 */       Connections.wire("next", GenesisGetters.getAnaphoraExpert(), model.getStoryProcessor());
/* 312:333 */       return true;
/* 313:    */     }
/* 314:335 */     if (this.matcher.match("Transfer knowledge from ?s to ?t", input))
/* 315:    */     {
/* 316:336 */       String source = (String)this.matcher.getBindings().get("?s");
/* 317:337 */       source = trimOff('"', source);
/* 318:338 */       Mark.say(new Object[] {"Value of source mental model is", source });
/* 319:339 */       String target = (String)this.matcher.getBindings().get("?t");
/* 320:340 */       target = trimOff('"', target);
/* 321:341 */       Mark.say(new Object[] {"Value of target mental model is", target });
/* 322:342 */       MentalModel sourceModel = MentalModel.getGlobalMentalModel(source);
/* 323:343 */       MentalModel targetModel = MentalModel.getGlobalMentalModel(target);
/* 324:    */       
/* 325:345 */       MentalModel.transferKnoweledge(sourceModel, targetModel);
/* 326:346 */       Mark.say(new Object[] {"Prediction rule count in target", Integer.valueOf(targetModel.getPredictionRules().size()) });
/* 327:    */       
/* 328:348 */       return true;
/* 329:    */     }
/* 330:350 */     if (this.matcher.match("Connect to Co57", input))
/* 331:    */     {
/* 332:351 */       Mark.say(new Object[] {"Connecting to Co57" });
/* 333:352 */       Connections.getPorts(this).transmit("self", "Start video");
/* 334:353 */       return true;
/* 335:    */     }
/* 336:355 */     return false;
/* 337:    */   }
/* 338:    */   
/* 339:    */   private String removeQuotes(String name)
/* 340:    */   {
/* 341:359 */     if (name.startsWith("\"")) {
/* 342:360 */       name = name.substring(1);
/* 343:    */     }
/* 344:362 */     if (name.endsWith("\"")) {
/* 345:363 */       name = name.substring(0, name.length() - 1);
/* 346:    */     }
/* 347:365 */     return name;
/* 348:    */   }
/* 349:    */   
/* 350:    */   private String trimOff(char c, String name)
/* 351:    */   {
/* 352:369 */     if ((name == null) || (name.length() == 0)) {
/* 353:370 */       return name;
/* 354:    */     }
/* 355:372 */     if (name.charAt(0) == c) {
/* 356:373 */       name = name.substring(1);
/* 357:    */     }
/* 358:375 */     if (name.charAt(name.length() - 1) == c) {
/* 359:376 */       name = name.substring(0, name.length() - 1);
/* 360:    */     }
/* 361:378 */     return name;
/* 362:    */   }
/* 363:    */   
/* 364:    */   private boolean processStageDirections(String s)
/* 365:    */   {
/* 366:382 */     String string = stripPunctuation(s);
/* 367:384 */     if ("First perspective".equalsIgnoreCase(string))
/* 368:    */     {
/* 369:385 */       Connections.getPorts(this).transmit(Start.STAGE_DIRECTION_PORT, "Neither perspective");
/* 370:386 */       Connections.getPorts(this).transmit(Start.STAGE_DIRECTION_PORT, "First perspective");
/* 371:387 */       return true;
/* 372:    */     }
/* 373:389 */     if ("Second perspective".equalsIgnoreCase(string))
/* 374:    */     {
/* 375:390 */       Connections.getPorts(this).transmit(Start.STAGE_DIRECTION_PORT, "Neither perspective");
/* 376:391 */       Connections.getPorts(this).transmit(Start.STAGE_DIRECTION_PORT, "Second perspective");
/* 377:392 */       return true;
/* 378:    */     }
/* 379:394 */     if ("Both perspectives".equalsIgnoreCase(string))
/* 380:    */     {
/* 381:395 */       Connections.getPorts(this).transmit(Start.STAGE_DIRECTION_PORT, "Both perspectives");
/* 382:    */       
/* 383:    */ 
/* 384:398 */       return true;
/* 385:    */     }
/* 386:400 */     if ("Neither perspective".equalsIgnoreCase(string))
/* 387:    */     {
/* 388:401 */       Connections.getPorts(this).transmit(Start.STAGE_DIRECTION_PORT, "Neither perspective");
/* 389:402 */       return true;
/* 390:    */     }
/* 391:404 */     if ("Show first perspective".equalsIgnoreCase(string))
/* 392:    */     {
/* 393:405 */       Connections.getPorts(this).transmit(Start.STAGE_DIRECTION_PORT, WiredSplitPane.SHOW_LEFT);
/* 394:406 */       return true;
/* 395:    */     }
/* 396:408 */     if ("Show second perspective".equalsIgnoreCase(string))
/* 397:    */     {
/* 398:409 */       Connections.getPorts(this).transmit(Start.STAGE_DIRECTION_PORT, WiredSplitPane.SHOW_RIGHT);
/* 399:410 */       return true;
/* 400:    */     }
/* 401:412 */     if ("Show both perspectives".equalsIgnoreCase(string))
/* 402:    */     {
/* 403:413 */       Connections.getPorts(this).transmit(Start.STAGE_DIRECTION_PORT, WiredSplitPane.SHOW_BOTH);
/* 404:414 */       return true;
/* 405:    */     }
/* 406:416 */     if ("Clear explanatory text".equalsIgnoreCase(string))
/* 407:    */     {
/* 408:417 */       Connections.getPorts(this).transmit("reset port", "all");
/* 409:418 */       return true;
/* 410:    */     }
/* 411:420 */     if ("Clear story memory".equalsIgnoreCase(string))
/* 412:    */     {
/* 413:421 */       Mark.say(new Object[] {"ZZZ" });
/* 414:422 */       Connections.getPorts(this).transmit(Start.STAGE_DIRECTION_PORT, "reset");
/* 415:423 */       return true;
/* 416:    */     }
/* 417:425 */     if ("Clear mental models".equalsIgnoreCase(string)) {
/* 418:427 */       return true;
/* 419:    */     }
/* 420:429 */     if ("Start general knowledge".equalsIgnoreCase(string))
/* 421:    */     {
/* 422:431 */       Connections.getPorts(this).transmit("switch tab", "silence");
/* 423:432 */       Connections.getPorts(this).transmit("change-mode", "parse");
/* 424:433 */       return true;
/* 425:    */     }
/* 426:435 */     if (string.startsWith("Pause"))
/* 427:    */     {
/* 428:436 */       String minis = string.substring("Pause".length()).trim();
/* 429:437 */       if (minis.isEmpty()) {
/* 430:438 */         Connections.getPorts(this).transmit(Start.STAGE_DIRECTION_PORT, "Pause");
/* 431:    */       } else {
/* 432:441 */         Talker.getTalker().sleep(minis);
/* 433:    */       }
/* 434:443 */       return true;
/* 435:    */     }
/* 436:445 */     if ("Replay story".equalsIgnoreCase(string))
/* 437:    */     {
/* 438:446 */       Connections.getPorts(this).transmit(Start.STAGE_DIRECTION_PORT, "replayStory");
/* 439:447 */       return true;
/* 440:    */     }
/* 441:450 */     if ("Radiate commonsense knowledge".equalsIgnoreCase(string))
/* 442:    */     {
/* 443:451 */       Connections.getPorts(this).transmit(Start.STAGE_DIRECTION_PORT, "radiateRules");
/* 444:452 */       Connections.getPorts(this).transmit("change-mode", "parse");
/* 445:453 */       return true;
/* 446:    */     }
/* 447:456 */     if ("Insert bias".equalsIgnoreCase(string))
/* 448:    */     {
/* 449:457 */       Connections.getPorts(this).transmit(Start.STAGE_DIRECTION_PORT, "insertBias");
/* 450:    */       
/* 451:459 */       return true;
/* 452:    */     }
/* 453:461 */     if (string.startsWith("Load persona"))
/* 454:    */     {
/* 455:462 */       Connections.getPorts(this).transmit("persona port", string.substring("Load persona".length()).trim());
/* 456:463 */       return true;
/* 457:    */     }
/* 458:466 */     if ("Start reflective knowledge".equalsIgnoreCase(string)) {
/* 459:473 */       return true;
/* 460:    */     }
/* 461:476 */     if ("Start commonsense knowledge".equalsIgnoreCase(string)) {
/* 462:482 */       return true;
/* 463:    */     }
/* 464:484 */     if ((string.startsWith("Note that")) && (string.endsWith("is a name")))
/* 465:    */     {
/* 466:485 */       int prefix = "Note that".length();
/* 467:486 */       int suffix = "is a name".length();
/* 468:487 */       String name = string.substring(prefix, string.length() - suffix).trim();
/* 469:    */       
/* 470:489 */       Start.getStart().processName(name);
/* 471:490 */       return true;
/* 472:    */     }
/* 473:492 */     if ((string.startsWith("Start experiment")) || (string.startsWith("Insert file Start experiment")))
/* 474:    */     {
/* 475:493 */       Connections.getPorts(this).transmit(Start.STAGE_DIRECTION_PORT, "First perspective");
/* 476:494 */       Connections.getPorts(this).transmit(Start.STAGE_DIRECTION_PORT, "Second perspective");
/* 477:495 */       Connections.getPorts(this).transmit("reset port", "all");
/* 478:496 */       Connections.getPorts(this).transmit(Start.STAGE_DIRECTION_PORT, "Neither perspective");
/* 479:497 */       Connections.getPorts(this).transmit(Start.STAGE_DIRECTION_PORT, "First perspective");
/* 480:498 */       Connections.getPorts(this).transmit(Start.STAGE_DIRECTION_PORT, WiredSplitPane.SHOW_LEFT);
/* 481:    */       
/* 482:500 */       Start.getStart().clearLocalTripleMaps();
/* 483:    */       
/* 484:    */ 
/* 485:503 */       this.mode = this.SILENT;
/* 486:    */       
/* 487:505 */       Connections.getPorts(this).transmit("change-mode", "parse");
/* 488:506 */       Connections.getPorts(this).transmit("switch tab", "Commonsense knowledge");
/* 489:    */       
/* 490:508 */       Connections.getPorts(this).transmit(TO_TEXT_ENTRY_BOX, "");
/* 491:    */       
/* 492:510 */       return true;
/* 493:    */     }
/* 494:512 */     if (string.startsWith("Start description of"))
/* 495:    */     {
/* 496:513 */       Connections.getPorts(this).transmit("switch tab", "Concept knowledge");
/* 497:    */     }
/* 498:516 */     else if (string.startsWith("Start story titled"))
/* 499:    */     {
/* 500:518 */       this.mode = this.STORY;
/* 501:    */       
/* 502:    */ 
/* 503:    */ 
/* 504:522 */       Connections.getPorts(this).transmit("story text", new BetterSignal(new Object[] { "Story teller", "clear" }));
/* 505:523 */       Connections.getPorts(this).transmit("story text", new BetterSignal(new Object[] { "Predictions", "clear" }));
/* 506:524 */       Connections.getPorts(this).transmit("story text", new BetterSignal(new Object[] { "Concept analysis", "clear" }));
/* 507:    */       
/* 508:    */ 
/* 509:    */ 
/* 510:528 */       Connections.getPorts(this).transmit("switch tab", "Story");
/* 511:    */     }
/* 512:    */     else
/* 513:    */     {
/* 514:532 */       if (string.startsWith("Insert into text box"))
/* 515:    */       {
/* 516:533 */         String text = string.substring("Insert into text box".length()).trim() + ".";
/* 517:534 */         if (text.startsWith(":")) {
/* 518:535 */           text = text.substring(1).trim();
/* 519:    */         }
/* 520:537 */         Mark.say(new Object[] {"Inserting into text box", text });
/* 521:    */         try
/* 522:    */         {
/* 523:540 */           Thread.sleep(2000L);
/* 524:    */         }
/* 525:    */         catch (InterruptedException localInterruptedException) {}
/* 526:545 */         Connections.getPorts(this).transmit(TO_TEXT_ENTRY_BOX, text);
/* 527:    */         
/* 528:547 */         return true;
/* 529:    */       }
/* 530:549 */       if ("The end".equalsIgnoreCase(string))
/* 531:    */       {
/* 532:550 */         this.mode = this.SILENT;
/* 533:551 */         Connections.getPorts(this).transmit(Start.STAGE_DIRECTION_PORT, "theEnd");
/* 534:552 */         Connections.getPorts(this).transmit("change-mode", "parse");
/* 535:    */         
/* 536:554 */         Connections.getPorts(this).transmit("switch tab", "Commonsense knowledge");
/* 537:555 */         return true;
/* 538:    */       }
/* 539:557 */       if ("Clear video memory".equalsIgnoreCase(string)) {
/* 540:560 */         return true;
/* 541:    */       }
/* 542:    */     }
/* 543:562 */     return false;
/* 544:    */   }
/* 545:    */   
/* 546:    */   private String stripPunctuation(String s)
/* 547:    */   {
/* 548:566 */     s = s.trim();
/* 549:567 */     int l = s.length();
/* 550:568 */     char c = s.charAt(l - 1);
/* 551:569 */     if (".?!".indexOf(c) >= 0) {
/* 552:570 */       return s.substring(0, l - 1);
/* 553:    */     }
/* 554:572 */     return s;
/* 555:    */   }
/* 556:    */   
/* 557:    */   public boolean getListenToCo57()
/* 558:    */   {
/* 559:576 */     return this.listenToCo57;
/* 560:    */   }
/* 561:    */   
/* 562:    */   public void setListen(boolean listen)
/* 563:    */   {
/* 564:580 */     this.listenToCo57 = listen;
/* 565:581 */     if (this.listenToCo57) {
/* 566:582 */       Mark.say(new Object[] {"Connecting to Co57 attention traces" });
/* 567:    */     } else {
/* 568:585 */       Mark.betterSay(new Object[] {"Disconnecting from Co57" });
/* 569:    */     }
/* 570:    */   }
/* 571:    */   
/* 572:    */   public static void main(String[] ignore)
/* 573:    */   {
/* 574:590 */     new StartPreprocessor().process("John loves Mary; John kisses Mary; Mary slaps John");
/* 575:    */   }
/* 576:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     start.StartPreprocessor
 * JD-Core Version:    0.7.0.1
 */