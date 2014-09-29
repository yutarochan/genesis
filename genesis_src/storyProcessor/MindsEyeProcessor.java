/*   1:    */ package storyProcessor;
/*   2:    */ 
/*   3:    */ import adam.RPCBox;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import connections.AbstractWiredBox;
/*   7:    */ import connections.Connections;
/*   8:    */ import connections.Ports;
/*   9:    */ import connections.WiredBox;
/*  10:    */ import java.io.PrintStream;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.HashMap;
/*  13:    */ import matchers.StandardMatcher;
/*  14:    */ import minilisp.LList;
/*  15:    */ import start.Generator;
/*  16:    */ import translator.Translator;
/*  17:    */ import utils.Mark;
/*  18:    */ import utils.PairOfEntities;
/*  19:    */ 
/*  20:    */ public class MindsEyeProcessor
/*  21:    */   extends AbstractWiredBox
/*  22:    */ {
/*  23: 22 */   boolean debug = false;
/*  24:    */   public static final String COMMENTARY_PORT = "commentary port";
/*  25:    */   public static final String LANGUAGE_PORT = "language port";
/*  26:    */   public static final String VISION_PORT = "vision port";
/*  27:    */   public static final String FRAME_NUMBER_PORT = "frame number port";
/*  28:    */   public static final String SYNCHRONIZE_FRAME_NUMBER_PORT = "synchronize frame number port";
/*  29:    */   public static final String QUESTION_ANSWER_PORT = "question answer port";
/*  30:    */   public static final String REMARK_PORT = "remark port";
/*  31:    */   public static final String VIDEO_NAME_PORT = "video name port";
/*  32: 40 */   public static String wireServer = "http://glue.csail.mit.edu/WireServer";
/*  33: 42 */   public static String server = "co57_test";
/*  34: 44 */   public String currentVideo = "None selected yet!";
/*  35: 46 */   private HashMap<String, String> abbreviations = new HashMap();
/*  36: 48 */   private HashMap<String, String> objects = new HashMap();
/*  37:    */   private static Entity p0;
/*  38:    */   private static Entity p1;
/*  39:    */   private static Entity p2;
/*  40:    */   private static Entity p3;
/*  41:    */   private static Entity p4;
/*  42:    */   RPCBox box;
/*  43:    */   WiredBox proxy;
/*  44:    */   
/*  45:    */   public MindsEyeProcessor()
/*  46:    */   {
/*  47:    */     try
/*  48:    */     {
/*  49: 68 */       setName("Story processor");
/*  50: 69 */       Translator translator = Translator.getTranslator();
/*  51: 70 */       translator.translate("xx is an object");
/*  52: 71 */       translator.translate("yy is an object");
/*  53: 72 */       translator.translate("ff is an integer");
/*  54:    */       
/*  55: 74 */       p0 = translator.translate("Advance video to frame 0").getElement(2);
/*  56: 75 */       p1 = translator.translate("Is xx approaching yy?").getElement(0);
/*  57: 76 */       p2 = translator.translate("Is xx larger than yy?").getElement(0);
/*  58: 77 */       p3 = translator.translate("Is xx bigger than yy?").getElement(0);
/*  59:    */       
/*  60: 79 */       p4 = translator.translate("Look at video named zz.").getElement(0);
/*  61:    */       
/*  62: 81 */       Mark.say(new Object[] {Boolean.valueOf(this.debug), "po:", p0.asString() });
/*  63: 82 */       Mark.say(new Object[] {Boolean.valueOf(this.debug), "p1:", p1.asString() });
/*  64: 83 */       Mark.say(new Object[] {Boolean.valueOf(this.debug), "p2:", p2.asString() });
/*  65: 84 */       Mark.say(new Object[] {Boolean.valueOf(this.debug), "p3:", p3.asString() });
/*  66: 85 */       Mark.say(new Object[] {Boolean.valueOf(this.debug), "p4:", p4.asString() });
/*  67:    */       
/*  68: 87 */       Connections.getPorts(this).addSignalProcessor("language port", "processLanguageInput");
/*  69: 88 */       Connections.getPorts(this).addSignalProcessor("vision port", "processVisionInput");
/*  70: 89 */       Connections.getPorts(this).addSignalProcessor("synchronize frame number port", "synchronize");
/*  71: 90 */       Connections.getPorts(this).addSignalProcessor("commentary port", "comment");
/*  72: 91 */       establishNetworkConnection();
/*  73: 92 */       createAbbreviationMap();
/*  74:    */     }
/*  75:    */     catch (Exception e)
/*  76:    */     {
/*  77: 95 */       Mark.err(new Object[] {"Blew out in MindsEyeProcessor constructor" });
/*  78: 96 */       e.printStackTrace();
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void comment(Object o)
/*  83:    */     throws Exception
/*  84:    */   {
/*  85:101 */     if ((o instanceof Integer))
/*  86:    */     {
/*  87:102 */       String sentence = "Is the second person approaching the first person";
/*  88:103 */       Translator translator = Translator.getTranslator();
/*  89:104 */       Sequence s = (Sequence)translator.translate(sentence);
/*  90:105 */       Mark.say(new Object[] {Boolean.valueOf(this.debug), o.toString(), s.asString() });
/*  91:106 */       for (Entity t : s.getElements()) {
/*  92:107 */         processLanguageInput(t);
/*  93:    */       }
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   private void createAbbreviationMap()
/*  98:    */   {
/*  99:113 */     this.abbreviations.put("macbeth", "hamlet");
/* 100:114 */     this.abbreviations
/* 101:115 */       .put("approach", "MULTIPLE_VERB_02/Approach4_Leave6_Exchange1_A1_C2_Act1_2_Downtown4_MC_EVEN_4761ba00-c5af-11df-ae7f-e80688cb869a.mov");
/* 102:    */   }
/* 103:    */   
/* 104:    */   private String getAbbreviation(String name)
/* 105:    */   {
/* 106:120 */     String result = (String)this.abbreviations.get(name);
/* 107:121 */     if (result == null) {
/* 108:122 */       return name;
/* 109:    */     }
/* 110:124 */     return result;
/* 111:    */   }
/* 112:    */   
/* 113:    */   private void establishNetworkConnection()
/* 114:    */   {
/* 115:    */     try
/* 116:    */     {
/* 117:130 */       this.proxy = Connections.subscribe(server, 5.0D);
/* 118:    */       
/* 119:132 */       System.out.println("Done waiting.");
/* 120:133 */       this.box = ((RPCBox)this.proxy);
/* 121:134 */       if (this.box != null) {
/* 122:135 */         Mark.say(new Object[] {Boolean.valueOf(this.debug), "Connected to the vision system" });
/* 123:    */       } else {
/* 124:138 */         Mark.say(new Object[] {Boolean.valueOf(this.debug), "Did not connect to the vision system" });
/* 125:    */       }
/* 126:    */     }
/* 127:    */     catch (Exception e)
/* 128:    */     {
/* 129:142 */       Mark.err(new Object[] {"Could not connect to a vision system" });
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void processLanguageInput(Object object)
/* 134:    */   {
/* 135:148 */     if ((object instanceof Entity))
/* 136:    */     {
/* 137:149 */       Entity t = (Entity)object;
/* 138:150 */       Mark.say(new Object[] {"Mind's eye language port received", t.asString() });
/* 139:155 */       if (!testAdvanceCommand(p0, t, "look_at")) {
/* 140:157 */         if (!testUnaryCommand(p4, t, "choose")) {
/* 141:167 */           if (!testBinaryPredicate(p1, t, "is_moving_toward")) {
/* 142:173 */             if (!testBinaryPredicate(p2, t, "is_bigger")) {
/* 143:173 */               testBinaryPredicate(p3, t, "is_bigger");
/* 144:    */             }
/* 145:    */           }
/* 146:    */         }
/* 147:    */       }
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void synchronize(Object o)
/* 152:    */   {
/* 153:190 */     issueVoidCommand("look_at", new Object[] { this.currentVideo, o });
/* 154:    */   }
/* 155:    */   
/* 156:    */   private boolean testAdvanceCommand(Entity pattern, Entity t, String predicate)
/* 157:    */   {
/* 158:199 */     LList<PairOfEntities> bindings = StandardMatcher.getBasicMatcher().match(pattern, t);
/* 159:201 */     if (bindings != null)
/* 160:    */     {
/* 161:202 */       String r2 = resolve2("0", bindings);
/* 162:203 */       int i2 = Integer.parseInt(r2);
/* 163:    */       
/* 164:    */ 
/* 165:206 */       Mark.say(new Object[] {Boolean.valueOf(this.debug), "Command", predicate });
/* 166:207 */       Connections.getPorts(this).transmit("frame number port", Integer.valueOf(i2));
/* 167:208 */       Connections.getPorts(this).transmit("remark port", "Ok.");
/* 168:209 */       return true;
/* 169:    */     }
/* 170:211 */     return false;
/* 171:    */   }
/* 172:    */   
/* 173:    */   private boolean testUnaryCommand(Entity pattern, Entity t, String predicate)
/* 174:    */   {
/* 175:220 */     LList<PairOfEntities> bindings = StandardMatcher.getBasicMatcher().match(pattern, t);
/* 176:221 */     Mark.say(new Object[] {"Bindings:", bindings });
/* 177:222 */     if (bindings != null)
/* 178:    */     {
/* 179:223 */       String r1 = resolve2("zz", bindings);
/* 180:224 */       Mark.say(new Object[] {"!!!", predicate, r1 });
/* 181:225 */       issueVoidCommand(predicate, new Object[] { r1 });
/* 182:226 */       Mark.say(new Object[] {Boolean.valueOf(this.debug), "Command", predicate });
/* 183:227 */       Connections.getPorts(this).transmit("video name port", r1);
/* 184:228 */       Connections.getPorts(this).transmit("frame number port", Integer.valueOf(0));
/* 185:229 */       Connections.getPorts(this).transmit("remark port", "Ok.");
/* 186:230 */       return true;
/* 187:    */     }
/* 188:232 */     return false;
/* 189:    */   }
/* 190:    */   
/* 191:    */   private String checkForAntecedant(String name)
/* 192:    */   {
/* 193:236 */     if ("video".equals(name)) {
/* 194:237 */       return this.currentVideo;
/* 195:    */     }
/* 196:240 */     String fullName = getAbbreviation(name);
/* 197:241 */     this.currentVideo = fullName;
/* 198:242 */     return fullName;
/* 199:    */   }
/* 200:    */   
/* 201:    */   private boolean testBinaryPredicate(Entity pattern, Entity t, String predicate)
/* 202:    */   {
/* 203:297 */     LList<PairOfEntities> bindings = StandardMatcher.getBasicMatcher().match(pattern, t);
/* 204:    */     
/* 205:299 */     Mark.say(new Object[] {Boolean.valueOf(this.debug), "Pattern     :", pattern.asString() });
/* 206:300 */     Mark.say(new Object[] {Boolean.valueOf(this.debug), "Input       :", t.asString() });
/* 207:301 */     Mark.say(new Object[] {Boolean.valueOf(this.debug), "Bindings are:", bindings });
/* 208:303 */     if (bindings != null)
/* 209:    */     {
/* 210:305 */       Connections.getPorts(this).transmit("synchronize frame number port", null);
/* 211:306 */       issueFetchCommand("get_all_markers");
/* 212:307 */       String r1 = resolve("xx", bindings);
/* 213:308 */       String r2 = resolve("yy", bindings);
/* 214:309 */       Mark.say(new Object[] {Boolean.valueOf(this.debug), "Rs", r1, r2 });
/* 215:310 */       if ((r1 != null) && (r2 != null))
/* 216:    */       {
/* 217:311 */         Mark.say(new Object[] {Boolean.valueOf(this.debug), "Command", predicate });
/* 218:312 */         issueValueCommand(t.getSubject(), predicate, new Object[] { r1, r2 });
/* 219:313 */         return true;
/* 220:    */       }
/* 221:    */     }
/* 222:317 */     return false;
/* 223:    */   }
/* 224:    */   
/* 225:    */   private void issueVoidCommand(String command, Object... array)
/* 226:    */   {
/* 227:321 */     if (this.box != null)
/* 228:    */     {
/* 229:322 */       for (Object o : array) {
/* 230:323 */         Mark.say(new Object[] {Boolean.valueOf(this.debug), "Arg:", o });
/* 231:    */       }
/* 232:325 */       this.box.rpc(command, array);
/* 233:326 */       Mark.say(new Object[] {Boolean.valueOf(this.debug), "Void command", command, "apparently succeeded" });
/* 234:    */     }
/* 235:    */     else
/* 236:    */     {
/* 237:329 */       Mark.err(new Object[] {"1 Unable to issue command because not connected to vision system." });
/* 238:330 */       Connections.getPorts(this).transmit("question answer port", "No connection so " + command + "-->" + "don't know");
/* 239:    */     }
/* 240:    */   }
/* 241:    */   
/* 242:    */   private void issueValueCommand(Entity t, String command, Object... array)
/* 243:    */   {
/* 244:335 */     String args = "";
/* 245:336 */     for (Object x : array) {
/* 246:337 */       args = args + " " + x.toString();
/* 247:    */     }
/* 248:339 */     if (this.box != null)
/* 249:    */     {
/* 250:    */       try
/* 251:    */       {
/* 252:341 */         Object result = this.box.rpc(command, array);
/* 253:342 */         if ((result instanceof Object[]))
/* 254:    */         {
/* 255:343 */           String values = "";
/* 256:344 */           Mark.say(new Object[] {Boolean.valueOf(this.debug), "Value command", command + args, "apparently succeeded" });
/* 257:345 */           for (Object o : (Object[])result)
/* 258:    */           {
/* 259:346 */             Mark.say(new Object[] {Boolean.valueOf(this.debug), "Result returned is", o.toString() });
/* 260:347 */             values = values + o + " ";
/* 261:    */           }
/* 262:349 */           Connections.getPorts(this).transmit("question answer port", command + args + "-->" + values.trim());
/* 263:    */         }
/* 264:    */         else
/* 265:    */         {
/* 266:352 */           Mark.say(new Object[] {Boolean.valueOf(this.debug), "Value command", command + args, "apparently succeeded" });
/* 267:353 */           Mark.say(new Object[] {Boolean.valueOf(this.debug), "Result returned is", result.toString() });
/* 268:354 */           if ("false".equals(result.toString()))
/* 269:    */           {
/* 270:355 */             t.addFeature("not");
/* 271:    */           }
/* 272:    */           else
/* 273:    */           {
/* 274:358 */             Mark.say(new Object[] {Boolean.valueOf(this.debug), "Trying to say", t.asString() });
/* 275:    */             
/* 276:    */ 
/* 277:361 */             Connections.getPorts(this).transmit("question answer port", Generator.getGenerator().generate(t));
/* 278:    */           }
/* 279:    */         }
/* 280:    */       }
/* 281:    */       catch (Exception e)
/* 282:    */       {
/* 283:371 */         Mark.say(new Object[] {Boolean.valueOf(this.debug), "Value command", command + args, "apparently failed" });
/* 284:372 */         Mark.say(new Object[] {Boolean.valueOf(this.debug), "is_bigger: " + this.box.rpc("is_bigger", new Object[] { "hello", "there" }) });
/* 285:    */       }
/* 286:    */     }
/* 287:    */     else
/* 288:    */     {
/* 289:376 */       Mark.err(new Object[] {"2 Unable to issue command because not connected to vision system." });
/* 290:377 */       Connections.getPorts(this).transmit("question answer port", "No connection so " + command + args + "-->" + "don't know");
/* 291:    */     }
/* 292:    */   }
/* 293:    */   
/* 294:    */   private void issueFetchCommand(String command)
/* 295:    */   {
/* 296:382 */     String args = "";
/* 297:383 */     if (this.box != null)
/* 298:    */     {
/* 299:    */       try
/* 300:    */       {
/* 301:385 */         Object[] input = new Object[0];
/* 302:386 */         Object result = this.box.rpc(command, input);
/* 303:387 */         if ((result instanceof Object[]))
/* 304:    */         {
/* 305:388 */           String values = "";
/* 306:389 */           Mark.say(new Object[] {Boolean.valueOf(this.debug), "Value command", command + args, "apparently succeeded yielding", Integer.valueOf(((Object[])result).length), "object(s)" });
/* 307:390 */           Object[] objects = (Object[])result;
/* 308:391 */           objects.length;
/* 309:399 */           for (int i = 0; i < objects.length; i++)
/* 310:    */           {
/* 311:400 */             Object o = objects[i];
/* 312:401 */             putObject(Integer.toString(i), o.toString());
/* 313:402 */             Mark.say(new Object[] {Boolean.valueOf(this.debug), "Result returned is", o.toString() });
/* 314:403 */             values = values + o + " ";
/* 315:    */           }
/* 316:    */         }
/* 317:    */         else
/* 318:    */         {
/* 319:409 */           Mark.say(new Object[] {Boolean.valueOf(this.debug), "Value command", command + args, "apparently succeeded yielding object" });
/* 320:410 */           Mark.say(new Object[] {Boolean.valueOf(this.debug), "Result returned is", result.toString() });
/* 321:    */         }
/* 322:    */       }
/* 323:    */       catch (Exception e)
/* 324:    */       {
/* 325:414 */         Mark.say(new Object[] {Boolean.valueOf(this.debug), "Value command", command + args, "apparently failed" });
/* 326:415 */         Mark.say(new Object[] {Boolean.valueOf(this.debug), "is_bigger: " + this.box.rpc("is_bigger", new Object[] { "hello", "there" }) });
/* 327:    */       }
/* 328:    */     }
/* 329:    */     else
/* 330:    */     {
/* 331:419 */       Mark.err(new Object[] {"3 Unable to issue command because not connected to vision system." });
/* 332:420 */       Connections.getPorts(this).transmit("question answer port", "No connection so " + command + args + "-->" + "don't know");
/* 333:    */     }
/* 334:    */   }
/* 335:    */   
/* 336:    */   private String resolve2(String s, LList<PairOfEntities> bindings)
/* 337:    */   {
/* 338:425 */     for (Object object : bindings)
/* 339:    */     {
/* 340:426 */       PairOfEntities pair = (PairOfEntities)object;
/* 341:427 */       Entity d = pair.getDatum();
/* 342:428 */       Entity p = pair.getPattern();
/* 343:429 */       if (s.equals(p.getType())) {
/* 344:430 */         return pair.getDatum().getType();
/* 345:    */       }
/* 346:    */     }
/* 347:433 */     return null;
/* 348:    */   }
/* 349:    */   
/* 350:    */   private String resolve(String s, LList<PairOfEntities> bindings)
/* 351:    */   {
/* 352:437 */     for (Object object : bindings)
/* 353:    */     {
/* 354:438 */       PairOfEntities pair = (PairOfEntities)object;
/* 355:439 */       Entity datum = pair.getDatum();
/* 356:440 */       Entity pattern = pair.getPattern();
/* 357:441 */       Mark.say(new Object[] {Boolean.valueOf(this.debug), s, datum.asString(), pattern.asString() });
/* 358:442 */       if ((s.equals(pattern.getType())) && (datum.getFeatures() != null))
/* 359:    */       {
/* 360:443 */         if (datum.getFeatures().indexOf("first") >= 0) {
/* 361:444 */           return getObject("0");
/* 362:    */         }
/* 363:446 */         if (datum.getFeatures().indexOf("second") >= 0) {
/* 364:447 */           return getObject("1");
/* 365:    */         }
/* 366:449 */         if (datum.getFeatures().indexOf("third") >= 0) {
/* 367:450 */           return getObject("2");
/* 368:    */         }
/* 369:    */       }
/* 370:    */     }
/* 371:454 */     return null;
/* 372:    */   }
/* 373:    */   
/* 374:    */   private void putObject(String index, String value)
/* 375:    */   {
/* 376:458 */     this.objects.put(index, value);
/* 377:    */   }
/* 378:    */   
/* 379:    */   private String getObject(String string)
/* 380:    */   {
/* 381:462 */     return (String)this.objects.get(string);
/* 382:    */   }
/* 383:    */   
/* 384:    */   public void processVisionInput(Object object)
/* 385:    */   {
/* 386:466 */     if ((object instanceof Entity))
/* 387:    */     {
/* 388:467 */       Entity t = (Entity)object;
/* 389:468 */       Mark.say(new Object[] {Boolean.valueOf(this.debug), "Mind's eye vision port received", t.asString() });
/* 390:    */     }
/* 391:    */   }
/* 392:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     storyProcessor.MindsEyeProcessor
 * JD-Core Version:    0.7.0.1
 */