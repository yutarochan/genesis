/*    1:     */ package start;
/*    2:     */ 
/*    3:     */ import Signals.BetterSignal;
/*    4:     */ import bridge.reps.entities.Entity;
/*    5:     */ import bridge.reps.entities.Function;
/*    6:     */ import bridge.reps.entities.Sequence;
/*    7:     */ import connections.AbstractWiredBox;
/*    8:     */ import connections.Connections;
/*    9:     */ import connections.Ports;
/*   10:     */ import java.io.PrintStream;
/*   11:     */ import java.util.ArrayList;
/*   12:     */ import java.util.Arrays;
/*   13:     */ import java.util.HashMap;
/*   14:     */ import java.util.Iterator;
/*   15:     */ import java.util.List;
/*   16:     */ import java.util.Vector;
/*   17:     */ import javax.swing.JCheckBox;
/*   18:     */ import parameters.Switch;
/*   19:     */ import persistence.JCheckBoxWithMemory;
/*   20:     */ import text.Html;
/*   21:     */ import text.Punctuator;
/*   22:     */ import tools.Predicates;
/*   23:     */ import translator.NewRuleSet;
/*   24:     */ import translator.Translator;
/*   25:     */ import utils.Mark;
/*   26:     */ 
/*   27:     */ public class Generator
/*   28:     */   extends AbstractWiredBox
/*   29:     */ {
/*   30:  36 */   public static int MUTE = 0;
/*   31:  36 */   public static int ACTIVE = 1;
/*   32:  40 */   private int mode = ACTIVE;
/*   33:  46 */   protected static List<String> connectors = Arrays.asList(new String[] { "before", "after", "while", "because", "that" });
/*   34:     */   public static final String SAY = "say";
/*   35:     */   public static final String EXPECTATION = "expectation";
/*   36:     */   public static final String IMAGINE = "imagine";
/*   37:     */   public static final String LEARNED = "learned";
/*   38:     */   public static final String DISAMBIGUATED = "disambiguated";
/*   39:     */   public static final String TEST = "test";
/*   40:     */   private static ArrayList<String> features;
/*   41:     */   private Start start;
/*   42:     */   private static Generator generator;
/*   43:     */   private static HashMap<String, String> startGeneratorCache;
/*   44:     */   
/*   45:     */   public static Generator getGenerator()
/*   46:     */   {
/*   47:  49 */     if (generator == null) {
/*   48:  50 */       generator = new Generator();
/*   49:     */     }
/*   50:  52 */     return generator;
/*   51:     */   }
/*   52:     */   
/*   53:     */   private Start getStart()
/*   54:     */   {
/*   55:  58 */     if (this.start == null) {
/*   56:  59 */       this.start = Start.getStart();
/*   57:     */     }
/*   58:  61 */     return this.start;
/*   59:     */   }
/*   60:     */   
/*   61:     */   public Generator()
/*   62:     */   {
/*   63:  65 */     Connections.getPorts(this).addSignalProcessor("processSay");
/*   64:  66 */     Connections.getPorts(this).addSignalProcessor("expectation", "processExpectation");
/*   65:  67 */     Connections.getPorts(this).addSignalProcessor("say", "processSay");
/*   66:  68 */     Connections.getPorts(this).addSignalProcessor("learned", "processLearning");
/*   67:  69 */     Connections.getPorts(this).addSignalProcessor("imagine", "processImagine");
/*   68:  70 */     Connections.getPorts(this).addSignalProcessor("disambiguated", "processDisambiguation");
/*   69:  71 */     features = new ArrayList();
/*   70:  72 */     features.add("dead");
/*   71:  73 */     features.add("alive");
/*   72:     */   }
/*   73:     */   
/*   74:     */   public String generateXPeriod(Entity t)
/*   75:     */   {
/*   76:  77 */     return generateXPeriod(t, "present");
/*   77:     */   }
/*   78:     */   
/*   79:     */   public String generateXPeriod(Entity t, String tense)
/*   80:     */   {
/*   81:  81 */     return stripPeriod(generate(t, tense));
/*   82:     */   }
/*   83:     */   
/*   84:     */   public String stripPeriod(String r)
/*   85:     */   {
/*   86:  85 */     if (r != null)
/*   87:     */     {
/*   88:  86 */       r = r.trim();
/*   89:  87 */       if (r.lastIndexOf('.') == r.length() - 1) {
/*   90:  88 */         return r.substring(0, r.length() - 1);
/*   91:     */       }
/*   92:  91 */       return r;
/*   93:     */     }
/*   94:  94 */     return null;
/*   95:     */   }
/*   96:     */   
/*   97:     */   public String generateTriples(Entity t)
/*   98:     */   {
/*   99:  98 */     return newGenerate(t).getRendering();
/*  100:     */   }
/*  101:     */   
/*  102:     */   public String comment(Entity t)
/*  103:     */   {
/*  104: 102 */     return generate(generateFromThing(t).present().progressive());
/*  105:     */   }
/*  106:     */   
/*  107:     */   public String playByPlay(Entity t, String time, boolean progressive)
/*  108:     */   {
/*  109: 106 */     RoleFrame frame = generateFromThing(t);
/*  110: 107 */     if (progressive) {
/*  111: 108 */       frame.progressive();
/*  112:     */     }
/*  113: 110 */     if (time == "past") {
/*  114: 111 */       frame.past();
/*  115: 113 */     } else if (time == "present") {
/*  116: 114 */       frame.present();
/*  117:     */     } else {
/*  118: 117 */       frame.future();
/*  119:     */     }
/*  120: 120 */     return generate(frame);
/*  121:     */   }
/*  122:     */   
/*  123:     */   public String generateWithoutCache(Entity t)
/*  124:     */   {
/*  125: 124 */     String result = generateAux(t, "present");
/*  126: 125 */     return result;
/*  127:     */   }
/*  128:     */   
/*  129:     */   public String generateWithoutCache(Entity t, String tense)
/*  130:     */   {
/*  131: 129 */     String result = generateAux(t, tense);
/*  132: 130 */     return result;
/*  133:     */   }
/*  134:     */   
/*  135:     */   public String generateInPastTense(Entity t)
/*  136:     */   {
/*  137: 138 */     if ((Switch.useStartCache.isSelected()) && (!Switch.useStartBeta.isSelected()))
/*  138:     */     {
/*  139: 139 */       String result = generateViaCache(t, "past");
/*  140: 140 */       if (result != null) {
/*  141: 141 */         return result;
/*  142:     */       }
/*  143:     */     }
/*  144: 144 */     return generateWithoutCache(t, "past");
/*  145:     */   }
/*  146:     */   
/*  147:     */   public String generate(Entity t)
/*  148:     */   {
/*  149: 149 */     return generate(t, "present");
/*  150:     */   }
/*  151:     */   
/*  152:     */   public String generate(Entity t, String tense)
/*  153:     */   {
/*  154: 153 */     boolean debug = false;
/*  155: 154 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Generating from", t });
/*  156: 156 */     if ((Switch.useStartCache.isSelected()) && (!Switch.useStartBeta.isSelected()))
/*  157:     */     {
/*  158: 157 */       String result = generateViaCache(t);
/*  159: 158 */       if (result != null) {
/*  160: 159 */         return result;
/*  161:     */       }
/*  162:     */     }
/*  163: 162 */     String result = null;
/*  164:     */     try
/*  165:     */     {
/*  166: 164 */       result = generateAux(t, tense);
/*  167:     */     }
/*  168:     */     catch (Exception e)
/*  169:     */     {
/*  170: 167 */       Mark.err(new Object[] {"Unable to generate from", t });
/*  171:     */     }
/*  172: 169 */     if ((Switch.useStartCache.isSelected()) && (!Switch.useStartBeta.isSelected()) && 
/*  173: 170 */       (result != null)) {
/*  174: 171 */       saveInCache(t, tense, result);
/*  175:     */     }
/*  176: 174 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Result is", result });
/*  177: 175 */     return result;
/*  178:     */   }
/*  179:     */   
/*  180:     */   public String generateAux(Entity e, String tense)
/*  181:     */   {
/*  182: 179 */     boolean debug = false;
/*  183: 180 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Generating from", e });
/*  184: 183 */     if ((Predicates.isExplanation(e)) || (Predicates.isAbduction(e)))
/*  185:     */     {
/*  186: 184 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Explanation!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" });
/*  187: 185 */       e.addProperty("property", "explanation");
/*  188:     */     }
/*  189: 193 */     if ((Predicates.isCause(e)) && (!e.isA("entail")) && (!e.isA("means"))) {
/*  190: 194 */       return composeCauseSentence(e, tense, debug);
/*  191:     */     }
/*  192: 197 */     return generateStringFromEntity(e, tense, debug);
/*  193:     */   }
/*  194:     */   
/*  195:     */   public String composeCauseSentence(Entity e, String tense, boolean debug)
/*  196:     */   {
/*  197: 202 */     Mark.say(
/*  198:     */     
/*  199:     */ 
/*  200:     */ 
/*  201:     */ 
/*  202:     */ 
/*  203:     */ 
/*  204:     */ 
/*  205:     */ 
/*  206:     */ 
/*  207:     */ 
/*  208:     */ 
/*  209:     */ 
/*  210: 215 */       new Object[] { Boolean.valueOf(debug), "Trapped!!!!!!!!!!!!!!!!!!" });String consequent = stripPeriod(generateStringFromEntity(e.getObject(), tense, debug));
/*  211: 204 */     if (e.hasProperty("property", "explanation")) {
/*  212: 205 */       consequent = consequent + ", probably";
/*  213:     */     }
/*  214: 207 */     consequent = consequent + " because ";
/*  215: 208 */     ArrayList<String> antecedants = new ArrayList();
/*  216: 209 */     for (Entity x : e.getSubject().getElements())
/*  217:     */     {
/*  218: 210 */       String antecedant = stripPeriod(generateStringFromEntity(x, tense, debug));
/*  219: 211 */       antecedants.add(antecedant);
/*  220:     */     }
/*  221: 213 */     consequent = consequent + composeAnd(antecedants);
/*  222: 214 */     return consequent + ".";
/*  223:     */   }
/*  224:     */   
/*  225:     */   public String generateStringFromEntity(Entity t, String tense, boolean debug)
/*  226:     */   {
/*  227: 218 */     RoleFrame roleFrame = generateFromThing(t);
/*  228: 219 */     if (roleFrame == null)
/*  229:     */     {
/*  230: 220 */       Mark.err(new Object[] {"Unable to generate role frame from", t.toString() });
/*  231: 221 */       return t.toString();
/*  232:     */     }
/*  233: 223 */     if (tense == "past") {
/*  234: 224 */       roleFrame.makePast();
/*  235: 226 */     } else if (tense == "future") {
/*  236: 227 */       roleFrame.makeFuture();
/*  237:     */     }
/*  238: 229 */     String result = generate(roleFrame);
/*  239: 230 */     if (result == null) {
/*  240: 233 */       return t.toString();
/*  241:     */     }
/*  242: 235 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Result is", result });
/*  243: 236 */     return result;
/*  244:     */   }
/*  245:     */   
/*  246:     */   public String generateAsIf(Entity t)
/*  247:     */   {
/*  248: 240 */     boolean debug = false;
/*  249: 241 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Generating from:", t.toString() });
/*  250: 244 */     if (Predicates.isCause(t))
/*  251:     */     {
/*  252: 246 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Trapped!!!!!!!!!!!!!!!!!!" });
/*  253: 247 */       String consequent = generateXPeriod(t.getObject(), "present");
/*  254: 248 */       if (t.isA("explanation")) {
/*  255: 249 */         consequent = consequent + " may be a consequence of ";
/*  256:     */       } else {
/*  257: 252 */         consequent = consequent + " whenever ";
/*  258:     */       }
/*  259: 254 */       ArrayList<String> antecedants = new ArrayList();
/*  260: 255 */       for (Entity x : t.getSubject().getElements())
/*  261:     */       {
/*  262: 256 */         String antecedant = generateXPeriod(x, "present");
/*  263: 257 */         antecedants.add(antecedant);
/*  264:     */       }
/*  265: 259 */       consequent = consequent + composeAnd(antecedants);
/*  266: 260 */       return consequent + ".";
/*  267:     */     }
/*  268: 262 */     RoleFrame roleFrame = newGenerate(t);
/*  269: 263 */     if (roleFrame == null)
/*  270:     */     {
/*  271: 264 */       Mark.say(new Object[] {"Unable to generate role frame from", t.toString() });
/*  272: 265 */       return t.toString();
/*  273:     */     }
/*  274: 267 */     String result = generate(roleFrame);
/*  275: 268 */     if (result == null)
/*  276:     */     {
/*  277: 269 */       Mark.say(new Object[] {"Unable to generate sentence from role frame", roleFrame });
/*  278: 270 */       return t.toString();
/*  279:     */     }
/*  280: 272 */     return result;
/*  281:     */   }
/*  282:     */   
/*  283:     */   public String verticalize(String rendering)
/*  284:     */   {
/*  285: 276 */     StringBuffer buffer = new StringBuffer(rendering);
/*  286: 277 */     int index = -1;
/*  287: 278 */     while ((index = buffer.indexOf("][")) > 0) {
/*  288: 279 */       buffer.replace(index, index + 2, "]\n[");
/*  289:     */     }
/*  290: 281 */     return "\n" + buffer.toString();
/*  291:     */   }
/*  292:     */   
/*  293:     */   private String composeAnd(ArrayList<String> s)
/*  294:     */   {
/*  295: 285 */     if ((s == null) || (s.isEmpty())) {
/*  296: 286 */       return "";
/*  297:     */     }
/*  298: 288 */     if (s.size() == 1) {
/*  299: 289 */       return (String)s.get(0);
/*  300:     */     }
/*  301: 291 */     if (s.size() == 2) {
/*  302: 292 */       return (String)s.get(0) + " and " + (String)s.get(1);
/*  303:     */     }
/*  304: 295 */     String result = "";
/*  305: 296 */     for (int i = 0; i < s.size() - 1; i++) {
/*  306: 297 */       result = result + (String)s.get(i) + ", ";
/*  307:     */     }
/*  308: 299 */     result = result + "and " + (String)s.get(s.size() - 1);
/*  309: 300 */     return result;
/*  310:     */   }
/*  311:     */   
/*  312:     */   public String generate(RoleFrame roleFrame)
/*  313:     */   {
/*  314: 305 */     String roleResult = null;
/*  315: 306 */     String rendering = getCompleteRendering(roleFrame);
/*  316: 307 */     if (roleFrame != null) {
/*  317: 310 */       roleResult = getStart().generate(rendering);
/*  318:     */     }
/*  319: 315 */     if ((roleResult != null) && (roleResult.indexOf("<P>") < 0)) {
/*  320: 316 */       return roleResult;
/*  321:     */     }
/*  322: 318 */     return null;
/*  323:     */   }
/*  324:     */   
/*  325:     */   public String getCompleteRendering(RoleFrame r)
/*  326:     */   {
/*  327: 322 */     String triples = r.getRendering() + r.makeProperty(r.getHead(), "is_main", "Yes");
/*  328:     */     
/*  329: 324 */     return triples;
/*  330:     */   }
/*  331:     */   
/*  332:     */   public String interpret(String triples)
/*  333:     */   {
/*  334: 328 */     return Start.getStart().generate(triples);
/*  335:     */   }
/*  336:     */   
/*  337:     */   public String generateTriplesDeprecated(Entity t)
/*  338:     */   {
/*  339: 332 */     String result = null;
/*  340:     */     
/*  341:     */ 
/*  342:     */ 
/*  343:     */ 
/*  344:     */ 
/*  345:     */ 
/*  346:     */ 
/*  347: 340 */     return result;
/*  348:     */   }
/*  349:     */   
/*  350:     */   private void test()
/*  351:     */   {
/*  352: 344 */     Translator translator = new Translator();
/*  353: 345 */     ArrayList<String> tests = makeTests();
/*  354: 346 */     for (String sentence : tests)
/*  355:     */     {
/*  356: 347 */       Mark.say(new Object[] {"" });
/*  357: 348 */       Mark.say(new Object[] {"The sentence:                     ", sentence });
/*  358: 349 */       String startTriples = getStart().processSentence(sentence);
/*  359: 350 */       Mark.say(new Object[] {"Generation from START's triples:  ", getStart().generate(startTriples) });
/*  360: 351 */       Entity localEntity = translator.interpret(getStart().parse(sentence));
/*  361:     */     }
/*  362:     */   }
/*  363:     */   
/*  364:     */   public void test(String sentence, String... strings)
/*  365:     */   {
/*  366: 356 */     Translator translator = new Translator();
/*  367: 357 */     ArrayList<String> tests = makeTests();
/*  368: 358 */     Mark.say(new Object[] {"" });
/*  369:     */     
/*  370: 360 */     String startTriples = getStart().processSentence(sentence);
/*  371: 361 */     Entity parse = translator.interpret(getStart().parse(sentence));
/*  372: 362 */     Entity x = parse.getElement(0);
/*  373:     */     
/*  374: 364 */     RoleFrame rf = newGenerate(x);
/*  375: 365 */     for (String s : strings) {
/*  376: 366 */       rf.addTriple(s);
/*  377:     */     }
/*  378: 368 */     String result = getGenerator().generate(x);
/*  379:     */     
/*  380: 370 */     sentence = sentence.trim();
/*  381: 371 */     if (sentence.charAt(sentence.length() - 1) != '.') {
/*  382: 372 */       sentence = sentence + '.';
/*  383:     */     }
/*  384: 375 */     if (sentence.equalsIgnoreCase(result.trim()))
/*  385:     */     {
/*  386: 376 */       Mark.say(new Object[] {"Ok:", result });
/*  387:     */     }
/*  388:     */     else
/*  389:     */     {
/*  390: 379 */       Mark.err(new Object[] {"Ooops:\nDesired:", sentence, "\nGot:    ", result });
/*  391: 380 */       Mark.say(new Object[] {"The sentence:", sentence });
/*  392: 381 */       Mark.say(new Object[] {"Start triples:", verticalize(startTriples) });
/*  393:     */       
/*  394: 383 */       Mark.say(new Object[] {"Parse is:", parse.asStringWithIndexes() });
/*  395: 384 */       Mark.say(new Object[] {"Working on:", x.toString() });
/*  396: 385 */       Mark.say(new Object[] {"Role frame yields:", verticalize(rf.getRendering()) });
/*  397: 386 */       Mark.say(new Object[] {"Generation from START's triples:", getStart().generate(startTriples) });
/*  398: 387 */       Mark.say(new Object[] {"Generation from innerese:       ", result });
/*  399:     */     }
/*  400:     */   }
/*  401:     */   
/*  402:     */   public void test(RoleFrame rf, String result)
/*  403:     */   {
/*  404: 394 */     test(rf, result, false);
/*  405:     */   }
/*  406:     */   
/*  407:     */   public void test(RoleFrame rf, String result, boolean showTriples)
/*  408:     */   {
/*  409: 398 */     String generation = "";
/*  410:     */     try
/*  411:     */     {
/*  412: 400 */       generation = getGenerator().generate(rf);
/*  413: 401 */       if (generation != null) {
/*  414: 402 */         generation = generation.trim();
/*  415:     */       }
/*  416: 404 */       result = result.trim();
/*  417: 405 */       Mark.say(new Object[] {Boolean.valueOf(showTriples), "\n|", result, "|\n|", generation, "|" });
/*  418: 406 */       if (result == null)
/*  419:     */       {
/*  420: 407 */         Mark.err(new Object[] {"Oops, generator produced null, should have got", result });
/*  421:     */       }
/*  422: 409 */       else if (result.trim().isEmpty())
/*  423:     */       {
/*  424: 410 */         Mark.err(new Object[] {"Oops, haven't specified output desired; got", generation });
/*  425:     */       }
/*  426:     */       else
/*  427:     */       {
/*  428: 412 */         if (result.equalsIgnoreCase(generation))
/*  429:     */         {
/*  430: 414 */           BetterSignal signal = new BetterSignal(new Object[] { "Generator test", Html.line("Ok: " + result) });
/*  431: 415 */           Connections.getPorts(this).transmit("test", signal);
/*  432: 416 */           return;
/*  433:     */         }
/*  434: 419 */         Mark.say(new Object[] {"Diferences:\n|" + result + "|\n|" + generation + "|" });
/*  435: 420 */         Connections.getPorts(this).transmit("test", new BetterSignal(new Object[] { "Generator test", Html.line("Ooops:") }));
/*  436: 421 */         Connections.getPorts(this).transmit("test", new BetterSignal(new Object[] { "Generator test", Html.line("Desired: " + result) }));
/*  437: 422 */         Connections.getPorts(this).transmit("test", new BetterSignal(new Object[] { "Generator test", Html.line("But got: " + generation) }));
/*  438: 423 */         return;
/*  439:     */       }
/*  440:     */     }
/*  441:     */     catch (Exception e)
/*  442:     */     {
/*  443: 427 */       Mark.err(new Object[] {"Hit exception on", result });
/*  444: 428 */       Mark.err(new Object[] {"Role frame produced:\n", addCr(rf.getRendering()) });
/*  445: 429 */       Mark.err(new Object[] {"Start triples:\n", removeSpans(Start.getStart().processSentence(result)) });
/*  446: 430 */       e.printStackTrace();
/*  447:     */       
/*  448: 432 */       Connections.getPorts(this)
/*  449: 433 */         .transmit("test", new BetterSignal(new Object[] { "Generator test", Html.line("Failed on: " + result + ", generated: " + generation) }));
/*  450:     */     }
/*  451:     */   }
/*  452:     */   
/*  453:     */   private String addCr(String s)
/*  454:     */   {
/*  455:     */     for (;;)
/*  456:     */     {
/*  457: 438 */       int index = s.indexOf("][");
/*  458: 439 */       if (index < 0) {
/*  459:     */         break;
/*  460:     */       }
/*  461: 440 */       s = s.substring(0, index + 1) + "\n" + s.substring(index + 1);
/*  462:     */     }
/*  463: 446 */     return s;
/*  464:     */   }
/*  465:     */   
/*  466:     */   private String removeSpans(String s)
/*  467:     */   {
/*  468: 450 */     if (s == null) {
/*  469: 451 */       return s;
/*  470:     */     }
/*  471:     */     for (;;)
/*  472:     */     {
/*  473: 454 */       int start = s.indexOf("<");
/*  474: 455 */       if (start < 0) {
/*  475:     */         break;
/*  476:     */       }
/*  477: 456 */       int end = s.indexOf(">");
/*  478: 457 */       s = s.substring(0, start) + s.substring(end + 1);
/*  479:     */     }
/*  480: 463 */     return s;
/*  481:     */   }
/*  482:     */   
/*  483:     */   public void setMode(int mode)
/*  484:     */   {
/*  485: 467 */     this.mode = mode;
/*  486:     */   }
/*  487:     */   
/*  488:     */   public static String merge(List<String> list)
/*  489:     */   {
/*  490: 471 */     int l = list.size();
/*  491: 472 */     if (l == 1) {
/*  492: 473 */       return (String)list.get(0);
/*  493:     */     }
/*  494: 475 */     if (l == 2) {
/*  495: 476 */       return "<ul><li>" + Punctuator.removePeriod((String)list.get(0)) + "; <li>" + (String)list.get(1) + "</ul>";
/*  496:     */     }
/*  497: 478 */     if (l < 1)
/*  498:     */     {
/*  499: 479 */       Mark.say(new Object[] {"Bug in question expert.  No antecedents" });
/*  500: 480 */       return null;
/*  501:     */     }
/*  502: 482 */     String result = "<ul>";
/*  503: 483 */     for (int i = 0; i < l; i++) {
/*  504: 484 */       result = result + "<li>" + Punctuator.removePeriod((String)list.get(i)) + "; ";
/*  505:     */     }
/*  506: 486 */     result = result + "</ul>";
/*  507: 487 */     return result;
/*  508:     */   }
/*  509:     */   
/*  510:     */   private ArrayList<String> makeTests()
/*  511:     */   {
/*  512: 491 */     ArrayList<String> tests = new ArrayList();
/*  513: 492 */     tests.add("The king hates the prince because prince hates the king");
/*  514: 493 */     tests.add("Sonja is a dog");
/*  515: 494 */     tests.add("The bird flew");
/*  516:     */     
/*  517: 496 */     tests.add("The king appeared");
/*  518: 497 */     tests.add("The king became dead");
/*  519: 498 */     tests.add("Macbeth became king.");
/*  520:     */     
/*  521: 500 */     tests.add("The king loves beer because the king is nice");
/*  522:     */     
/*  523: 502 */     tests.add("The king wants to drink beer");
/*  524: 503 */     tests.add("Lady Macbeth wants to become queen.");
/*  525:     */     
/*  526: 505 */     tests.add("A mouse persuaded a bull to drink beer");
/*  527: 506 */     tests.add("Lady Macbeth persuaded Macbeth to want to become the king.");
/*  528: 507 */     tests.add("The king forced the queen to love beer");
/*  529:     */     
/*  530: 509 */     tests.add("The king believes the queen is nice");
/*  531:     */     
/*  532: 511 */     tests.add("The king is happy");
/*  533: 512 */     tests.add("The king has wings");
/*  534: 513 */     tests.add("The king is nice");
/*  535: 514 */     tests.add("The king owns a dog");
/*  536: 515 */     tests.add("The king loves his dog");
/*  537: 516 */     tests.add("Patrick is the king");
/*  538: 517 */     tests.add("The dog is the man's friend");
/*  539: 518 */     tests.add("The king drank beer after the queen drank wine");
/*  540:     */     
/*  541: 520 */     tests.add("The king is smarter than the prince");
/*  542:     */     
/*  543: 522 */     tests.add("Jack murdered Jill with a knife.");
/*  544: 523 */     tests.add("The king ate a pear with a knife");
/*  545:     */     
/*  546: 525 */     tests.add("The king drinks beer");
/*  547: 526 */     tests.add("Lady Macbeth kills Duncan.");
/*  548: 527 */     tests.add("The king drinks beer with a stein");
/*  549: 528 */     tests.add("A mouse persuaded a bull to drink beer");
/*  550:     */     
/*  551: 530 */     tests.add("The king does not think that John does not love Mary");
/*  552: 531 */     tests.add("The king thinks that John loves Mary");
/*  553: 532 */     tests.add("George does not think that John does not love Mary");
/*  554: 533 */     tests.add("Lady Macbeth thinks King Hamlet loves Princess Jessica");
/*  555:     */     
/*  556: 535 */     tests.add("Hamlet is King Hamlet's friend");
/*  557: 536 */     tests.add("George's wanting to fly leads to George's flying");
/*  558: 537 */     tests.add("George flew because a cat appeared and a dog appeared.");
/*  559: 538 */     tests.add("Boris fought Sue with a knife.");
/*  560: 539 */     tests.add("Boris relocated a town.");
/*  561: 540 */     tests.add("Start story titled \"Cyber war.\"");
/*  562: 541 */     tests.add("A girl gave a hammer to another girl");
/*  563: 542 */     tests.add("I have learned from experience that The girl takes the ball");
/*  564: 543 */     tests.add("Imagine a jump");
/*  565: 544 */     tests.add("The car left the tree");
/*  566: 545 */     tests.add("Macbeth persuaded Caesar to murder Boris");
/*  567: 546 */     tests.add("Estonia believes computer networks are valuable");
/*  568: 547 */     tests.add("I am Talliban's ally");
/*  569:     */     
/*  570: 549 */     tests.add("The first object is not approaching the second object");
/*  571: 550 */     tests.add("Macbeth murders duncan");
/*  572: 551 */     tests.add("Georgia believes computer networks are important");
/*  573:     */     
/*  574: 553 */     tests.add("Patrick appears to be vicious");
/*  575:     */     
/*  576:     */ 
/*  577:     */ 
/*  578:     */ 
/*  579:     */ 
/*  580:     */ 
/*  581:     */ 
/*  582:     */ 
/*  583: 562 */     return tests;
/*  584:     */   }
/*  585:     */   
/*  586:     */   public void flush()
/*  587:     */   {
/*  588: 566 */     Start.getStart().clearLocalTripleMaps();
/*  589: 567 */     Start.getStart().clearStartReferences();
/*  590:     */   }
/*  591:     */   
/*  592:     */   public void setStoryMode()
/*  593:     */   {
/*  594: 571 */     Start.getStart().setStoryMode();
/*  595:     */   }
/*  596:     */   
/*  597:     */   public void setRegularMode()
/*  598:     */   {
/*  599: 576 */     Start.getStart().setRegularMode();
/*  600:     */   }
/*  601:     */   
/*  602:     */   public RoleFrame generateFromThing(Entity t)
/*  603:     */   {
/*  604: 582 */     return newGenerate(t);
/*  605:     */   }
/*  606:     */   
/*  607:     */   private RoleFrame generateEntityWithQuantity(boolean debug, Entity entity)
/*  608:     */   {
/*  609: 586 */     Mark.say(
/*  610:     */     
/*  611:     */ 
/*  612:     */ 
/*  613:     */ 
/*  614:     */ 
/*  615:     */ 
/*  616: 593 */       new Object[] { Boolean.valueOf(debug), "A", entity.toString() });RoleFrame result = makeEntity(entity);Object o = entity.getProperty("quantity");
/*  617: 589 */     if (o != null) {
/*  618: 590 */       result.addQuantity(Integer.parseInt((String)o));
/*  619:     */     }
/*  620: 592 */     return result;
/*  621:     */   }
/*  622:     */   
/*  623:     */   private RoleFrame newGenerateFunction(boolean debug, Entity entity)
/*  624:     */   {
/*  625: 596 */     RoleFrame result = null;
/*  626: 597 */     if (entity.functionP("expectation"))
/*  627:     */     {
/*  628: 598 */       Mark.say(new Object[] {Boolean.valueOf(debug), "B", entity.toString() });
/*  629: 599 */       result = newGenerate(entity.getSubject());
/*  630:     */     }
/*  631: 601 */     else if (entity.isA(NewRuleSet.pathPrepositions))
/*  632:     */     {
/*  633: 602 */       Mark.say(new Object[] {Boolean.valueOf(debug), "C", entity.toString() });
/*  634: 603 */       Entity reference = entity.getSubject();
/*  635: 604 */       if (reference.functionP("at"))
/*  636:     */       {
/*  637: 605 */         Mark.say(new Object[] {Boolean.valueOf(debug), "C1", entity.toString() });
/*  638: 606 */         result.addRole(entity.getType(), makeEntity(reference.getSubject()));
/*  639:     */       }
/*  640:     */       else
/*  641:     */       {
/*  642: 609 */         Mark.say(new Object[] {Boolean.valueOf(debug), "C2", entity.toString() });
/*  643: 610 */         RoleFrame part = makeEntity(reference.getType());
/*  644: 611 */         result.addRole(entity.getType(), part);
/*  645: 612 */         RoleFrame decoration = makeEventFrame(entity, part, "related-to", makeEntity(reference.getSubject()));
/*  646: 613 */         result.combine(decoration);
/*  647:     */       }
/*  648:     */     }
/*  649: 616 */     else if (entity.functionP("command"))
/*  650:     */     {
/*  651: 617 */       Mark.say(new Object[] {Boolean.valueOf(debug), "D", entity.toString() });
/*  652: 618 */       Entity s = entity.getSubject();
/*  653: 619 */       result = makeEventFrame(entity, "you", "imagine", newGenerate(s)).attitude("imperative");
/*  654:     */     }
/*  655: 621 */     else if ((entity.functionP("transition")) && (entity.getSubject().entityP()))
/*  656:     */     {
/*  657: 622 */       Mark.say(new Object[] {Boolean.valueOf(debug), "E", entity.toString() });
/*  658: 623 */       Entity s = entity.getSubject();
/*  659: 624 */       result = makeEventFrame(entity, makeEntity(s), entity.getType(), null);
/*  660:     */     }
/*  661: 626 */     else if (entity.functionP("transition"))
/*  662:     */     {
/*  663: 627 */       Mark.say(new Object[] {Boolean.valueOf(debug), "F", entity.toString() });
/*  664: 628 */       Entity s = entity.getSubject();
/*  665: 629 */       if (entity.getSubject().isA("position"))
/*  666:     */       {
/*  667: 630 */         Mark.say(new Object[] {Boolean.valueOf(debug), "F1", entity.toString() });
/*  668: 631 */         result = makeEventFrame(entity, makeEntity(s.getSubject()), "become", makeEntity(s.getObject()));
/*  669:     */       }
/*  670: 633 */       else if (entity.getSubject().isA("classification"))
/*  671:     */       {
/*  672: 634 */         Mark.say(new Object[] {Boolean.valueOf(debug), "F2", entity.toString() });
/*  673: 635 */         result = makeEventFrame(entity, makeEntity(s.getObject()), "become", makeEntity(s.getSubject()));
/*  674:     */       }
/*  675:     */       else
/*  676:     */       {
/*  677: 638 */         Mark.say(new Object[] {Boolean.valueOf(debug), "F3", entity.toString() });
/*  678: 639 */         result = makeEventFrame(entity, makeEntity(s.getSubject()), "become", makeEntity(s.getObject()));
/*  679:     */       }
/*  680:     */     }
/*  681: 643 */     else if (entity.functionP("why"))
/*  682:     */     {
/*  683: 644 */       Mark.say(new Object[] {Boolean.valueOf(debug), "W", entity.toString() });
/*  684: 645 */       result = newGenerate(entity.getSubject()).makeWhyQuestion();
/*  685:     */     }
/*  686: 647 */     else if (entity.functionP("what_if"))
/*  687:     */     {
/*  688: 648 */       Mark.say(new Object[] {Boolean.valueOf(debug), "W", entity.toString() });
/*  689: 649 */       result = newGenerate(entity.getSubject()).makeWhatIfQuestion();
/*  690:     */     }
/*  691: 651 */     else if (entity.functionP())
/*  692:     */     {
/*  693: 652 */       Mark.say(new Object[] {Boolean.valueOf(debug), "G", entity.toString() });
/*  694: 653 */       result = makeEntity(entity.getType()).addRole("related-to", newGenerate(entity.getSubject()));
/*  695:     */     }
/*  696: 655 */     return result;
/*  697:     */   }
/*  698:     */   
/*  699:     */   private RoleFrame newGenerateSequence(boolean debug, Entity entity)
/*  700:     */   {
/*  701: 659 */     RoleFrame result = null;
/*  702:     */     
/*  703: 661 */     return result;
/*  704:     */   }
/*  705:     */   
/*  706:     */   private RoleFrame newGenerateRelation(Entity entity)
/*  707:     */   {
/*  708: 665 */     boolean debug = false;
/*  709: 666 */     RoleFrame result = null;
/*  710: 667 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Relation is", entity });
/*  711: 668 */     if ((entity.relationP("start")) && (entity.getSubject().isA("you")))
/*  712:     */     {
/*  713: 670 */       RoleFrame you = makeEntity(new Entity("you"));
/*  714: 671 */       RoleFrame story = makeEntity(new Entity("story"));
/*  715: 672 */       result = new RoleFrame(you, "start", story);
/*  716:     */     }
/*  717: 674 */     else if (entity.isA(connectors))
/*  718:     */     {
/*  719: 675 */       Mark.say(new Object[] {Boolean.valueOf(debug), "H", entity.toString() });
/*  720: 676 */       RoleFrame first = newGenerate(entity.getSubject());
/*  721: 677 */       RoleFrame second = newGenerate(entity.getObject());
/*  722: 678 */       result = first.connect(entity.getType(), second);
/*  723:     */     }
/*  724:     */     else
/*  725:     */     {
/*  726:     */       RoleFrame roleFrame;
/*  727: 680 */       if ((entity.relationP("social relation")) && (entity.getSubject().entityP()) && (entity.getObject().entityP()))
/*  728:     */       {
/*  729: 681 */         Mark.say(new Object[] {Boolean.valueOf(debug), "I", entity.toString() });
/*  730: 682 */         RoleFrame subject = makeEntity(entity.getSubject());
/*  731: 683 */         RoleFrame object = makeEntity(entity.getObject());
/*  732: 684 */         StartEntity x = makeEntity(entity.getType());
/*  733: 685 */         x.addPossessor(subject);
/*  734: 686 */         roleFrame = makeEventFrame(entity, object, "is-a", makeEntity(entity.getType()).addPossessor(subject));
/*  735: 687 */         result = roleFrame;
/*  736:     */       }
/*  737: 689 */       else if (entity.relationP("is_called"))
/*  738:     */       {
/*  739: 690 */         Mark.say(new Object[] {Boolean.valueOf(debug), "J", entity.toString() });
/*  740: 691 */         result = makeEventFrame(entity, makeEntity(entity.getSubject().getType()), "is_called", makeEntity(entity.getObject().getType())
/*  741: 692 */           .noDeterminer());
/*  742:     */       }
/*  743: 694 */       else if (Predicates.isRoleFrame(entity))
/*  744:     */       {
/*  745: 695 */         Mark.say(new Object[] {Boolean.valueOf(debug), "K", entity.toString() });
/*  746: 696 */         Sequence sequence = (Sequence)entity.getObject();
/*  747: 697 */         if (!sequence.getElements().isEmpty())
/*  748:     */         {
/*  749: 698 */           Mark.say(new Object[] {Boolean.valueOf(debug), "K1", entity.toString() });
/*  750: 699 */           Entity object = getObject(sequence.getElements());
/*  751: 700 */           if ((object != null) && (object.sequenceP("path")))
/*  752:     */           {
/*  753: 701 */             Mark.say(new Object[] {Boolean.valueOf(debug), "K11", entity.toString() });
/*  754: 702 */             result = makeEventFrame(entity, generateEntityWithQuantity(debug, entity.getSubject()), entity.getName(), null);
/*  755: 703 */             for (Entity role : withoutObject(object.getElements())) {
/*  756: 704 */               addPathElement(result, role);
/*  757:     */             }
/*  758:     */           }
/*  759: 707 */           else if ((object != null) && (object.sequenceP()))
/*  760:     */           {
/*  761: 708 */             Mark.say(new Object[] {Boolean.valueOf(debug), "K12", entity.toString() });
/*  762: 709 */             result = makeEventFrame(entity, makeEntity(entity.getSubject()), entity.getName(), null);
/*  763: 710 */             for (Entity role : withoutObject(object.getElements())) {
/*  764: 711 */               addPathElement(result, role);
/*  765:     */             }
/*  766:     */           }
/*  767: 714 */           else if (object != null)
/*  768:     */           {
/*  769: 715 */             Mark.say(new Object[] {Boolean.valueOf(debug), "K13", entity.toString() });
/*  770: 716 */             RoleFrame objectFrame = newGenerate(object);
/*  771: 717 */             Mark.say(new Object[] {Boolean.valueOf(debug), "Object is", object });
/*  772: 718 */             if (Predicates.isCause(object))
/*  773:     */             {
/*  774: 719 */               Mark.say(new Object[] {Boolean.valueOf(debug), "K131", object.toString() });
/*  775: 720 */               objectFrame.that();
/*  776: 721 */               result = makeEventFrame(entity, newGenerate(entity.getSubject()), entity.getType(), objectFrame);
/*  777:     */             }
/*  778:     */             else
/*  779:     */             {
/*  780: 729 */               result = makeEventFrame(entity, newGenerate(entity.getSubject()), entity.getType(), objectFrame);
/*  781:     */             }
/*  782:     */           }
/*  783: 732 */           Mark.say(new Object[] {Boolean.valueOf(debug), "K14", result });
/*  784: 733 */           for (Entity role : withoutObject(sequence.getElements()))
/*  785:     */           {
/*  786: 734 */             if (result == null) {
/*  787: 735 */               result = makeEventFrame(entity, newGenerate(entity.getSubject()), entity.getName(), null);
/*  788:     */             }
/*  789: 737 */             result.addRole(role.getType(), newGenerate(role.getSubject()));
/*  790:     */           }
/*  791:     */         }
/*  792:     */         else
/*  793:     */         {
/*  794: 741 */           Mark.say(new Object[] {Boolean.valueOf(debug), "K2", entity.toString() });
/*  795: 742 */           Entity subject = entity.getSubject();
/*  796: 743 */           if (subject.relationP("property"))
/*  797:     */           {
/*  798: 744 */             Mark.say(new Object[] {Boolean.valueOf(debug), "K21", entity.toString() });
/*  799: 745 */             result = makeEventFrame(entity, newGenerate(subject), entity.getName(), null);
/*  800:     */           }
/*  801:     */           else
/*  802:     */           {
/*  803: 748 */             Mark.say(new Object[] {Boolean.valueOf(debug), "K22", entity.toString() });
/*  804: 749 */             result = makeEventFrame(entity, newGenerate(entity.getSubject()), entity.getName(), null);
/*  805:     */           }
/*  806:     */         }
/*  807:     */       }
/*  808: 753 */       else if (entity.relationP("has-part"))
/*  809:     */       {
/*  810: 754 */         Mark.say(new Object[] {Boolean.valueOf(debug), "L", entity.toString() });
/*  811: 755 */         result = makeEntity(entity.getObject()).partOf(makeEntity(entity.getSubject()));
/*  812:     */       }
/*  813: 757 */       else if (entity.relationP("relative"))
/*  814:     */       {
/*  815: 758 */         Mark.say(new Object[] {Boolean.valueOf(debug), "M", entity.toString() });
/*  816: 759 */         RoleFrame owner = makeEntity(entity.getSubject());
/*  817: 760 */         RoleFrame owned = makeEntity(entity.getObject());
/*  818: 761 */         RoleFrame type = new RoleFrame(entity.getType());
/*  819: 762 */         RoleFrame main = makeEventFrame(entity, owned, "is", type);
/*  820: 763 */         RoleFrame relation = makeEventFrame(entity, type, "related-to", owner);
/*  821: 764 */         result = main.combine(relation);
/*  822:     */       }
/*  823: 766 */       else if (isExpression(entity))
/*  824:     */       {
/*  825: 767 */         Mark.say(new Object[] {Boolean.valueOf(debug), "N", entity.toString() });
/*  826: 768 */         result = makeEventFrame(entity, makeEntity(entity.getSubject(), entity.getObject().getType()), "is", makeEntity(entity.getObject()));
/*  827:     */       }
/*  828: 770 */       else if (entity.relationP("position"))
/*  829:     */       {
/*  830: 771 */         Mark.say(new Object[] {Boolean.valueOf(debug), "O", entity.toString() });
/*  831: 772 */         result = makeEventFrame(entity, makeEntity(entity.getSubject()), "is-a", makeEntity(entity.getObject()).indefinite());
/*  832:     */       }
/*  833: 774 */       else if (entity.relationP("classification"))
/*  834:     */       {
/*  835: 775 */         Mark.say(new Object[] {Boolean.valueOf(debug), "P", entity.toString() });
/*  836: 776 */         StartEntity subject = makeEntity(entity.getObject());
/*  837: 777 */         StartEntity object = makeEntity(entity.getSubject()).indefinite();
/*  838: 778 */         object.indefinite();
/*  839: 779 */         result = makeEventFrame(entity, subject, "is", object);
/*  840:     */       }
/*  841: 781 */       else if (entity.relationP("believes-event"))
/*  842:     */       {
/*  843: 782 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Q", entity.toString() });
/*  844: 783 */         RoleFrame subject = makeEntity(entity.getSubject());
/*  845: 784 */         RoleFrame object = newGenerate(entity.getObject());
/*  846: 785 */         result = makeEventFrame(entity, subject, entity.getType(), object);
/*  847:     */       }
/*  848: 788 */       else if ((entity.relationP("goal")) || (entity.relationP("induce")) || (entity.relationP("persuade")))
/*  849:     */       {
/*  850: 789 */         Mark.say(new Object[] {Boolean.valueOf(debug), "R", entity.toString() });
/*  851: 790 */         RoleFrame subject = makeEntity(entity.getSubject());
/*  852: 791 */         RoleFrame object = newGenerate(entity.getObject());
/*  853: 792 */         if ((entity.getObject().relationP()) && (entity.getObject().isA("action")))
/*  854:     */         {
/*  855: 793 */           Mark.say(new Object[] {Boolean.valueOf(debug), "R1", entity.toString() });
/*  856: 794 */           RoleFrame intermediate = makeEntity("to");
/*  857: 795 */           result = makeEventFrame(entity, subject, entity.getType(), makeEntity(entity.getObject().getSubject()));
/*  858: 796 */           result.combine(makeEventFrame(entity, result.extractHead(result), intermediate, object));
/*  859:     */         }
/*  860:     */         else
/*  861:     */         {
/*  862: 799 */           Mark.say(new Object[] {Boolean.valueOf(debug), "R2", entity.toString() });
/*  863: 800 */           result = makeEventFrame(entity, subject, entity.getType(), object);
/*  864:     */         }
/*  865:     */       }
/*  866: 803 */       else if ((entity.relationP("owner")) || (entity.relationP("has-body-part")))
/*  867:     */       {
/*  868: 804 */         Mark.say(new Object[] {Boolean.valueOf(debug), "S", entity.toString() });
/*  869: 805 */         RoleFrame subject = makeEntity(entity.getSubject());
/*  870: 806 */         RoleFrame object = makeEntity(entity.getObject());
/*  871: 807 */         if (entity.relationP("has-body-part"))
/*  872:     */         {
/*  873: 808 */           Mark.say(new Object[] {Boolean.valueOf(debug), "S1", entity.toString() });
/*  874: 809 */           RoleFrame handle = subject;
/*  875: 810 */           subject = object;
/*  876: 811 */           object = handle;
/*  877:     */         }
/*  878: 813 */         object.indefinite();
/*  879: 814 */         result = makeEventFrame(entity, subject, "have", object);
/*  880:     */       }
/*  881: 816 */       else if (entity.relationP("time-relation"))
/*  882:     */       {
/*  883: 817 */         Mark.say(new Object[] {Boolean.valueOf(debug), "T", entity.toString() });
/*  884: 818 */         RoleFrame subject = newGenerate(entity.getSubject());
/*  885: 819 */         RoleFrame object = newGenerate(entity.getObject());
/*  886: 820 */         result = makeEventFrame(entity, subject, entity.getType(), object);
/*  887:     */       }
/*  888:     */       else
/*  889:     */       {
/*  890:     */         RoleFrame x;
/*  891: 822 */         if (entity.relationP("comparison"))
/*  892:     */         {
/*  893: 823 */           Mark.say(new Object[] {Boolean.valueOf(debug), "U", entity.toString() });
/*  894: 824 */           RoleFrame subject = makeEntity(entity.getSubject());
/*  895: 825 */           RoleFrame object = makeEntity(entity.getObject());
/*  896: 826 */           x = makeEventFrame(entity, subject, "is", entity.getType());
/*  897: 827 */           RoleFrame y = makeEventFrame(entity, x.extractHead(x), "than", object);
/*  898: 828 */           result = x.combine(y);
/*  899:     */         }
/*  900: 830 */         else if (entity.relationP("entail"))
/*  901:     */         {
/*  902: 831 */           Mark.say(new Object[] {Boolean.valueOf(debug), "V1", entity.toString() });
/*  903: 832 */           RoleFrame consequentRole = newGenerate(entity.getObject());
/*  904: 833 */           result = new RoleFrame();
/*  905: 834 */           for (Entity x : entity.getSubject().getElements())
/*  906:     */           {
/*  907: 835 */             String connector = "leads_to";
/*  908: 836 */             String consequentHead = consequentRole.getHead();
/*  909: 837 */             RoleFrame oneClause = makeEventFrame(entity, newGenerate(x), connector, consequentRole);
/*  910:     */             
/*  911: 839 */             oneClause.addDecoration(consequentHead, "has_position", "leading");
/*  912: 840 */             oneClause.addDecoration(oneClause.getHead(), "is_main", "yes");
/*  913: 841 */             result.combine(oneClause);
/*  914: 842 */             result.setHead(oneClause.getHead());
/*  915:     */           }
/*  916:     */         }
/*  917: 845 */         else if (entity.relationP("means"))
/*  918:     */         {
/*  919: 846 */           Mark.say(new Object[] {Boolean.valueOf(debug), "V2", entity.toString() });
/*  920: 847 */           RoleFrame consequentRole = newGenerate(entity.getObject());
/*  921: 848 */           result = new RoleFrame();
/*  922: 849 */           for (Entity x : entity.getSubject().getElements())
/*  923:     */           {
/*  924: 850 */             String connector = "has_purpose";
/*  925: 851 */             String consequentHead = consequentRole.getHead();
/*  926: 852 */             RoleFrame oneClause = makeEventFrame(entity, newGenerate(x), connector, consequentRole);
/*  927: 853 */             oneClause.addDecoration(consequentHead, "has_comp", "in_order");
/*  928: 854 */             oneClause.addDecoration(consequentHead, "has_position", "leading");
/*  929: 855 */             oneClause.addDecoration(oneClause.getHead(), "is_main", "yes");
/*  930: 856 */             result.combine(oneClause);
/*  931: 857 */             result.setHead(oneClause.getHead());
/*  932:     */           }
/*  933:     */         }
/*  934: 860 */         else if (entity.relationP("cause"))
/*  935:     */         {
/*  936: 861 */           Mark.say(new Object[] {Boolean.valueOf(debug), "V3", entity.toString() });
/*  937: 862 */           RoleFrame consequentRole = newGenerate(entity.getObject());
/*  938: 863 */           result = new RoleFrame();
/*  939: 864 */           for (Entity x : entity.getSubject().getElements())
/*  940:     */           {
/*  941: 865 */             String connector = "because";
/*  942: 866 */             if (entity.isAPrimed("if")) {
/*  943: 867 */               connector = "if";
/*  944:     */             }
/*  945: 869 */             RoleFrame oneClause = makeEventFrame(entity, consequentRole, connector, newGenerate(x));
/*  946: 870 */             result.combine(oneClause);
/*  947: 871 */             result.setHead(oneClause.getHead());
/*  948:     */           }
/*  949:     */         }
/*  950: 874 */         else if (entity.relationP("to"))
/*  951:     */         {
/*  952: 875 */           Mark.say(new Object[] {Boolean.valueOf(debug), "W", entity.toString() });
/*  953: 876 */           result = newGenerate(entity.getSubject());
/*  954: 877 */           result.addRole(entity.getName(), makeEntity(entity.getObject().getType()));
/*  955:     */         }
/*  956: 879 */         else if (entity.relationP("place"))
/*  957:     */         {
/*  958: 880 */           Mark.say(new Object[] {Boolean.valueOf(debug), "X", entity.toString() });
/*  959: 881 */           Entity object = entity.getObject();
/*  960: 882 */           result = makeEventFrame(entity, newGenerate(entity.getSubject()), "be", null);
/*  961: 883 */           if (object.sequenceP("location"))
/*  962:     */           {
/*  963: 884 */             Mark.say(new Object[] {Boolean.valueOf(debug), "X1", entity.toString() });
/*  964: 885 */             if (!object.getElements().isEmpty()) {
/*  965: 886 */               for (Entity role : object.getElements()) {
/*  966: 887 */                 addPathElement(result, role);
/*  967:     */               }
/*  968:     */             }
/*  969:     */           }
/*  970:     */         }
/*  971: 892 */         else if ((entity.relationP("at")) || (entity.relationP("on")) || (entity.relationP("in")))
/*  972:     */         {
/*  973: 893 */           Mark.say(new Object[] {Boolean.valueOf(debug), "Y", entity.toString() });
/*  974: 894 */           if (entity.getSubject().relationP("place"))
/*  975:     */           {
/*  976: 895 */             Mark.say(new Object[] {Boolean.valueOf(debug), "Y1", entity.toString() });
/*  977: 896 */             result = newGenerate(entity.getSubject());
/*  978:     */           }
/*  979: 898 */           else if (entity.getSubject().relationP("property"))
/*  980:     */           {
/*  981: 899 */             Mark.say(new Object[] {Boolean.valueOf(debug), "Y2", entity.toString() });
/*  982: 900 */             result = newGenerate(entity.getSubject());
/*  983:     */           }
/*  984:     */           else
/*  985:     */           {
/*  986: 903 */             Mark.say(new Object[] {Boolean.valueOf(debug), "Y3", entity.toString() });
/*  987: 904 */             result = makeEventFrame(entity, makeEntity(entity.getSubject()), "be", null);
/*  988:     */           }
/*  989: 906 */           Function loc = new Function(entity.getName(), entity.getObject());
/*  990: 907 */           addPathElement(result, loc);
/*  991:     */         }
/*  992: 909 */         else if (entity.relationP("with"))
/*  993:     */         {
/*  994: 910 */           Mark.say(new Object[] {Boolean.valueOf(debug), "Z", entity.toString() });
/*  995: 911 */           result = newGenerate(entity.getSubject());
/*  996: 912 */           result.addRole("with", newGenerate(entity.getObject()));
/*  997:     */         }
/*  998:     */       }
/*  999:     */     }
/* 1000: 914 */     if (entity.hasProperty("property"))
/* 1001:     */     {
/* 1002: 915 */       Mark.say(new Object[] {Boolean.valueOf(debug), "PPP" });
/* 1003: 916 */       String property = (String)entity.getProperty("property");
/* 1004: 917 */       if (property == "explanation")
/* 1005:     */       {
/* 1006: 918 */         result.addInternalModifier("probably");
/* 1007: 919 */         Mark.say(new Object[] {Boolean.valueOf(debug), "PPP1" });
/* 1008:     */       }
/* 1009:     */     }
/* 1010: 922 */     return result;
/* 1011:     */   }
/* 1012:     */   
/* 1013:     */   private RoleFrame newGenerate(Entity entity)
/* 1014:     */   {
/* 1015: 926 */     boolean debug = false;
/* 1016: 927 */     RoleFrame result = null;
/* 1017: 928 */     if (entity == null)
/* 1018:     */     {
/* 1019: 929 */       Mark.err(new Object[] {"newGenerate got null argument!" });
/* 1020: 930 */       result = makeEntity("HelloWorld");
/* 1021:     */     }
/* 1022: 932 */     else if (entity.entityP())
/* 1023:     */     {
/* 1024: 933 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Generating Entity...", entity.toString() });
/* 1025: 934 */       result = generateEntityWithQuantity(debug, entity);
/* 1026: 935 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Done generating Entity.", entity.toString() });
/* 1027:     */     }
/* 1028: 937 */     else if (entity.functionP())
/* 1029:     */     {
/* 1030: 938 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Generating Function...", entity.toString() });
/* 1031: 939 */       result = newGenerateFunction(debug, entity);
/* 1032: 940 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Done generating Function.", entity.toString() });
/* 1033:     */     }
/* 1034: 942 */     else if (entity.sequenceP())
/* 1035:     */     {
/* 1036: 943 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Generating Sequence...", entity.toString() });
/* 1037: 944 */       result = newGenerateSequence(debug, entity);
/* 1038: 945 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Done generating Sequence.", entity.toString() });
/* 1039:     */     }
/* 1040: 947 */     else if (entity.relationP())
/* 1041:     */     {
/* 1042: 948 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Generating Relation...", entity.toString() });
/* 1043: 949 */       result = newGenerateRelation(entity);
/* 1044: 950 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Done generating Relation.", entity.toString() });
/* 1045:     */     }
/* 1046: 952 */     if (result == null) {
/* 1047: 953 */       Mark.say(new Object[] {Boolean.valueOf(debug), "No result for", entity.toString() });
/* 1048: 955 */     } else if ((!entity.entityP()) && (result != null) && 
/* 1049: 956 */       (entity.hasFeature("not"))) {
/* 1050: 957 */       result.negative();
/* 1051:     */     }
/* 1052: 963 */     if (entity.hasProperty("property"))
/* 1053:     */     {
/* 1054: 964 */       Mark.say(new Object[] {Boolean.valueOf(debug), "PPP" });
/* 1055: 965 */       String property = (String)entity.getProperty("property");
/* 1056: 966 */       if (property == "explanation")
/* 1057:     */       {
/* 1058: 967 */         result.addInternalModifier("probably");
/* 1059: 968 */         Mark.say(new Object[] {Boolean.valueOf(debug), "PPP1" });
/* 1060:     */       }
/* 1061:     */     }
/* 1062: 975 */     return result;
/* 1063:     */   }
/* 1064:     */   
/* 1065:     */   public static void noteClauseHolders(Entity e)
/* 1066:     */   {
/* 1067: 979 */     e.addProperty("clause_holders", getClauseHolders(e));
/* 1068:     */   }
/* 1069:     */   
/* 1070:     */   private static Vector<Entity> getClauseHolders(Entity e)
/* 1071:     */   {
/* 1072: 983 */     Vector<Entity> result = new Vector();
/* 1073: 984 */     if (e.entityP())
/* 1074:     */     {
/* 1075: 985 */       if ((e.getProperty("clauses") != null) && 
/* 1076: 986 */         (!result.contains(e))) {
/* 1077: 987 */         result.add(e);
/* 1078:     */       }
/* 1079:     */     }
/* 1080: 991 */     else if (e.functionP())
/* 1081:     */     {
/* 1082: 992 */       addIfAbsent(getClauseHolders(e.getSubject()), result);
/* 1083:     */     }
/* 1084: 994 */     else if (e.relationP())
/* 1085:     */     {
/* 1086: 995 */       addIfAbsent(getClauseHolders(e.getSubject()), result);
/* 1087: 996 */       addIfAbsent(getClauseHolders(e.getObject()), result);
/* 1088:     */     }
/* 1089: 998 */     else if (e.sequenceP())
/* 1090:     */     {
/* 1091: 999 */       for (Entity x : e.getElements()) {
/* 1092:1000 */         addIfAbsent(getClauseHolders(x), result);
/* 1093:     */       }
/* 1094:     */     }
/* 1095:1003 */     return result;
/* 1096:     */   }
/* 1097:     */   
/* 1098:     */   private static void addIfAbsent(Vector<Entity> more, Vector<Entity> list)
/* 1099:     */   {
/* 1100:1007 */     for (Entity e : more) {
/* 1101:1008 */       if (!list.contains(e)) {
/* 1102:1011 */         list.add(e);
/* 1103:     */       }
/* 1104:     */     }
/* 1105:     */   }
/* 1106:     */   
/* 1107:     */   private RoleFrame makeEventFrame(Entity entity, Object subject, Object action, Object object)
/* 1108:     */   {
/* 1109:1016 */     RoleFrame roleFrame = new RoleFrame(subject, action, object);
/* 1110:1017 */     String tense = (String)entity.getProperty("tense");
/* 1111:1018 */     String modal = (String)entity.getProperty("modal");
/* 1112:     */     Entity clause;
/* 1113:1035 */     if (entity.hasProperty("clause_holders"))
/* 1114:     */     {
/* 1115:     */       Iterator localIterator2;
/* 1116:1036 */       for (Iterator localIterator1 = ((Vector)entity.getProperty("clause_holders")).iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 1117:     */       {
/* 1118:1036 */         Entity holder = (Entity)localIterator1.next();
/* 1119:1037 */         localIterator2 = ((Vector)holder.getProperty("clauses")).iterator(); continue;clause = (Entity)localIterator2.next();
/* 1120:1038 */         roleFrame.combine(newGenerate(clause));
/* 1121:     */       }
/* 1122:     */     }
/* 1123:1044 */     if ("present".equals(tense))
/* 1124:     */     {
/* 1125:1045 */       if ("will".equals(modal)) {
/* 1126:1046 */         roleFrame.future();
/* 1127:1048 */       } else if ("may".equals(modal)) {
/* 1128:1049 */         roleFrame.may();
/* 1129:     */       }
/* 1130:     */     }
/* 1131:1052 */     else if ("past".equals(tense)) {
/* 1132:1053 */       roleFrame.past();
/* 1133:     */     } else {
/* 1134:1055 */       "future".equals(tense);
/* 1135:     */     }
/* 1136:1059 */     if (entity.hasProperty("progressive")) {
/* 1137:1060 */       roleFrame.progressive();
/* 1138:     */     }
/* 1139:1064 */     if (entity.hasProperty("perfective")) {
/* 1140:1065 */       roleFrame.perfective();
/* 1141:     */     }
/* 1142:1069 */     ArrayList<Object> features = entity.getFeatures();
/* 1143:1070 */     for (Object feature : features) {
/* 1144:1072 */       if ((!feature.equals("not")) && (!feature.equals("possibly")) && (feature != "assumption")) {
/* 1145:1075 */         roleFrame.addModifier(feature.toString());
/* 1146:     */       }
/* 1147:     */     }
/* 1148:1081 */     return roleFrame;
/* 1149:     */   }
/* 1150:     */   
/* 1151:     */   private void addPathElement(RoleFrame result, Entity role)
/* 1152:     */   {
/* 1153:1086 */     if (role.getSubject().functionP("at")) {
/* 1154:1088 */       result.addRole(role.getType(), newGenerate(role.getSubject().getSubject()));
/* 1155:     */     } else {
/* 1156:1092 */       result.addRole(role.getType(), newGenerate(role.getSubject()));
/* 1157:     */     }
/* 1158:     */   }
/* 1159:     */   
/* 1160:     */   private Vector<Entity> withoutObject(Vector<Entity> elements)
/* 1161:     */   {
/* 1162:1099 */     Vector<Entity> result = new Vector();
/* 1163:1100 */     for (Entity t : elements) {
/* 1164:1101 */       if (!t.functionP("object")) {
/* 1165:1102 */         result.add(t);
/* 1166:     */       }
/* 1167:     */     }
/* 1168:1105 */     return result;
/* 1169:     */   }
/* 1170:     */   
/* 1171:     */   private Entity getObject(Vector<Entity> elements)
/* 1172:     */   {
/* 1173:1118 */     for (Entity t : elements) {
/* 1174:1119 */       if (t.functionP("object")) {
/* 1175:1120 */         return t.getSubject();
/* 1176:     */       }
/* 1177:     */     }
/* 1178:1123 */     return null;
/* 1179:     */   }
/* 1180:     */   
/* 1181:     */   private boolean isExpression(Entity t)
/* 1182:     */   {
/* 1183:1127 */     if (((t.relationP("property")) || (t.relationP("has-mental-state"))) && 
/* 1184:1128 */       (!t.relationP("entail"))) {
/* 1185:1129 */       return true;
/* 1186:     */     }
/* 1187:1132 */     return false;
/* 1188:     */   }
/* 1189:     */   
/* 1190:     */   public RoleFrame makeEntityRoleFrame(Entity e)
/* 1191:     */   {
/* 1192:1137 */     return makeEntity(e);
/* 1193:     */   }
/* 1194:     */   
/* 1195:     */   private StartEntity makeEntity(String s)
/* 1196:     */   {
/* 1197:1141 */     StartEntity result = new StartEntity(s);
/* 1198:1142 */     return result;
/* 1199:     */   }
/* 1200:     */   
/* 1201:     */   private StartEntity makeEntity(Entity t)
/* 1202:     */   {
/* 1203:1146 */     return makeEntity(t, null);
/* 1204:     */   }
/* 1205:     */   
/* 1206:     */   private StartEntity makeEntity(Entity entity, String exclusion)
/* 1207:     */   {
/* 1208:1150 */     if (entity == null)
/* 1209:     */     {
/* 1210:1151 */       Mark.err(new Object[] {"Null entity passed!" });
/* 1211:1152 */       return null;
/* 1212:     */     }
/* 1213:1155 */     boolean debug = false;
/* 1214:1156 */     StartEntity result = null;
/* 1215:1157 */     if (variable(entity)) {
/* 1216:1158 */       result = makeEntity(getTypeAboveName(entity) + "_" + entity.getName());
/* 1217:     */     } else {
/* 1218:1161 */       result = makeEntity(entity.getName());
/* 1219:     */     }
/* 1220:1166 */     Object owner = entity.getProperty(Entity.OWNER);
/* 1221:1167 */     if ((owner != null) && ((owner instanceof Entity))) {
/* 1222:1168 */       result.possessor(((Entity)owner).getType());
/* 1223:     */     }
/* 1224:1180 */     ArrayList features = entity.getFeatures();
/* 1225:1182 */     if (features != null) {
/* 1226:1183 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Features for", entity.toString(), "are", features });
/* 1227:     */     }
/* 1228:1186 */     if ((features != null) && (features.size() > 0)) {
/* 1229:1187 */       for (int i = 0; i < features.size(); i++)
/* 1230:     */       {
/* 1231:1188 */         Object feature = features.get(i);
/* 1232:1189 */         if (!feature.equals(exclusion)) {
/* 1233:1192 */           if ((feature != "definite") && (feature != "indefinite") && (feature != "none") && (feature != "another")) {
/* 1234:1193 */             result.addFeature(features.get(i).toString());
/* 1235:     */           }
/* 1236:     */         }
/* 1237:     */       }
/* 1238:     */     }
/* 1239:1198 */     Object determiner = entity.getProperty("determiner");
/* 1240:1201 */     if (entity.isAPrimed("ad_word"))
/* 1241:     */     {
/* 1242:1202 */       result.noDeterminer();
/* 1243:1203 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Entity", entity.toString(), "no determiner B" });
/* 1244:     */     }
/* 1245:1205 */     else if (determiner == null)
/* 1246:     */     {
/* 1247:1206 */       result.noDeterminer();
/* 1248:1207 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Entity", entity.toString(), "no determiner C" });
/* 1249:     */     }
/* 1250:1209 */     else if (determiner.equals("another"))
/* 1251:     */     {
/* 1252:1210 */       result.another();
/* 1253:1211 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Entity", entity.toString(), "is another" });
/* 1254:     */     }
/* 1255:1213 */     else if (determiner.equals("none"))
/* 1256:     */     {
/* 1257:1214 */       result.noDeterminer();
/* 1258:1215 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Entity", entity.toString(), "no determiner D" });
/* 1259:     */     }
/* 1260:1217 */     else if (determiner.equals("definite"))
/* 1261:     */     {
/* 1262:1218 */       result.definite();
/* 1263:1219 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Entity", entity.toString(), "is definite" });
/* 1264:     */     }
/* 1265:1221 */     else if (determiner.equals("indefinite"))
/* 1266:     */     {
/* 1267:1222 */       result.indefinite();
/* 1268:1223 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Entity", entity.toString(), "is indefinite" });
/* 1269:     */     }
/* 1270:1225 */     else if (entity.isAPrimed("name"))
/* 1271:     */     {
/* 1272:1226 */       result.noDeterminer();
/* 1273:1227 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Entity", entity.toString(), "no determiner A" });
/* 1274:     */     }
/* 1275:1229 */     else if ((entity.hasProperty("name")) || (entity.hasProperty("proper")))
/* 1276:     */     {
/* 1277:1230 */       result.noDeterminer();
/* 1278:1231 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Entity", entity.toString(), "no determiner A" });
/* 1279:     */     }
/* 1280:     */     else
/* 1281:     */     {
/* 1282:1239 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Entity", entity.toString(), "has no marker" });
/* 1283:     */     }
/* 1284:1241 */     return result;
/* 1285:     */   }
/* 1286:     */   
/* 1287:     */   private String getTypeAboveName(Entity t)
/* 1288:     */   {
/* 1289:1245 */     bridge.reps.entities.Thread x = t.getPrimedThread();
/* 1290:1246 */     for (int i = 0; i < x.size() - 1; i++) {
/* 1291:1247 */       if (((String)x.get(i + 1)).equalsIgnoreCase("name")) {
/* 1292:1248 */         return (String)x.get(i);
/* 1293:     */       }
/* 1294:     */     }
/* 1295:1251 */     return x.getType();
/* 1296:     */   }
/* 1297:     */   
/* 1298:     */   private boolean variable(Entity t)
/* 1299:     */   {
/* 1300:1255 */     if (t == null)
/* 1301:     */     {
/* 1302:1256 */       Mark.err(new Object[] {"Null Entity object passed!" });
/* 1303:1257 */       return false;
/* 1304:     */     }
/* 1305:1259 */     String type = t.getType();
/* 1306:1260 */     if (type == null) {
/* 1307:1261 */       return false;
/* 1308:     */     }
/* 1309:1263 */     if ((type.equalsIgnoreCase("aa")) || (type.equalsIgnoreCase("ww")) || (type.equalsIgnoreCase("xx")) || (type.equalsIgnoreCase("yy")) || 
/* 1310:1264 */       (type.equalsIgnoreCase("zz"))) {
/* 1311:1265 */       return true;
/* 1312:     */     }
/* 1313:1267 */     return false;
/* 1314:     */   }
/* 1315:     */   
/* 1316:     */   public void runAllTests()
/* 1317:     */   {
/* 1318:1271 */     Connections.getPorts(this).transmit("test", "clear");
/* 1319:1272 */     new RunTests(null).start();
/* 1320:     */   }
/* 1321:     */   
/* 1322:     */   private class RunTests
/* 1323:     */     extends java.lang.Thread
/* 1324:     */   {
/* 1325:     */     private RunTests() {}
/* 1326:     */     
/* 1327:     */     public void run()
/* 1328:     */     {
/* 1329:1277 */       Mark.say(
/* 1330:     */       
/* 1331:     */ 
/* 1332:     */ 
/* 1333:     */ 
/* 1334:1282 */         new Object[] { "Running tests" });Generator.this.runBasicTests();Generator.this.runSubordinateTests();
/* 1335:     */     }
/* 1336:     */   }
/* 1337:     */   
/* 1338:     */   public void runSubordinateTests()
/* 1339:     */   {
/* 1340:1287 */     StartEntity e = new StartEntity("package");
/* 1341:     */     
/* 1342:1289 */     RoleFrame rf1 = new RoleFrame(e, "disappear").past();
/* 1343:     */     
/* 1344:1291 */     getGenerator().test(rf1, "The package disappeared.");
/* 1345:     */     
/* 1346:1293 */     RoleFrame rf2 = new RoleFrame("man", "bring", e).past();
/* 1347:     */     
/* 1348:1295 */     getGenerator().test(rf2, "The man brought the package.");
/* 1349:     */     
/* 1350:1297 */     e.that(rf2);
/* 1351:     */     
/* 1352:1299 */     RoleFrame rf3 = new RoleFrame(e, "disappear").past();
/* 1353:     */     
/* 1354:1301 */     getGenerator().test(rf3, "The package that the man brought disappeared.");
/* 1355:     */     
/* 1356:1303 */     e = new StartEntity("package");
/* 1357:     */     
/* 1358:1305 */     rf1 = new RoleFrame(e, "disappear").past();
/* 1359:     */     
/* 1360:1307 */     rf2 = new RoleFrame("man", "bring", e).past();
/* 1361:     */     
/* 1362:1309 */     getGenerator().test(rf2, "The man brought the package.");
/* 1363:     */     
/* 1364:1311 */     e.which(rf2);
/* 1365:     */     
/* 1366:1313 */     rf3 = new RoleFrame(e, "disappear").past();
/* 1367:     */     
/* 1368:1315 */     getGenerator().test(rf3, "The package which the man brought disappeared.");
/* 1369:     */     
/* 1370:1317 */     StartEntity e1 = new StartEntity("soldier");
/* 1371:1318 */     StartEntity e2 = new StartEntity("soldier");
/* 1372:1319 */     StartEntity e3 = new StartEntity("officer");
/* 1373:     */     
/* 1374:1321 */     RoleFrame x1 = new RoleFrame(e1, "dug", "hole").past();
/* 1375:1322 */     RoleFrame x2 = new RoleFrame(e3, "commend", e2).past();
/* 1376:1323 */     e1.who(x1);
/* 1377:1324 */     e2.whom(x2);
/* 1378:1325 */     RoleFrame x3 = new RoleFrame(e1, "approach", e2);
/* 1379:     */     
/* 1380:1327 */     getGenerator().test(x3, "The soldier who dug the hole approaches the soldier whom the officer commended.");
/* 1381:     */     
/* 1382:1329 */     e1 = new StartEntity("soldier");
/* 1383:1330 */     e2 = new StartEntity("soldier");
/* 1384:1331 */     e3 = new StartEntity("officer");
/* 1385:     */     
/* 1386:1333 */     x1 = new RoleFrame(e1, "dug", "hole").past();
/* 1387:1334 */     x2 = new RoleFrame(e3, "commend", e1).past();
/* 1388:1335 */     e1.who(x1);
/* 1389:1336 */     e1.reset();
/* 1390:1337 */     e1.whom(x2);
/* 1391:1338 */     x3 = new RoleFrame(e1, "approach", e1);
/* 1392:     */     
/* 1393:1340 */     getGenerator().test(x3, "The soldier whom the officer commended approaches himself.");
/* 1394:     */   }
/* 1395:     */   
/* 1396:     */   public void runProblemTests()
/* 1397:     */   {
/* 1398:1346 */     Generator generator = getGenerator();
/* 1399:     */     
/* 1400:1348 */     RoleFrame c1 = new RoleFrame("man", "ran");
/* 1401:     */     
/* 1402:1350 */     RoleFrame c2 = new RoleFrame("soldier", "appear");
/* 1403:1351 */     generator.test(new StartEntity("dog").possessor("man"), "The man's dog");
/* 1404:     */   }
/* 1405:     */   
/* 1406:     */   public void runBasicTests()
/* 1407:     */   {
/* 1408:1355 */     Connections.getPorts(this).transmit("test", "clear");
/* 1409:     */     
/* 1410:1357 */     Generator generator = getGenerator();
/* 1411:     */     
/* 1412:     */ 
/* 1413:     */ 
/* 1414:     */ 
/* 1415:     */ 
/* 1416:1363 */     System.out.println("\nEntities\n");
/* 1417:     */     
/* 1418:1365 */     generator.test(new StartEntity("man"), "The man");
/* 1419:1366 */     generator.test(new StartEntity("man").definite(), "The man");
/* 1420:1367 */     generator.test(new StartEntity("man").indefinite(), "A man");
/* 1421:1368 */     generator.test(new StartEntity("men").noDeterminer(), "Men");
/* 1422:1369 */     generator.test(new StartEntity("man").feature("large"), "The large man");
/* 1423:1370 */     generator.test(new StartEntity("dog").possessor("man"), "The man's dog");
/* 1424:1371 */     generator.test(new StartEntity("arm").possessor("man"), "The man's arm");
/* 1425:     */     
/* 1426:     */ 
/* 1427:     */ 
/* 1428:1375 */     generator.test(new RoleFrame("man", "dig", "hole"), "The man digs the hole.");
/* 1429:1376 */     generator.test(new RoleFrame("man", "dig", "hole").present(), "The man digs the hole.");
/* 1430:1377 */     generator.test(new RoleFrame("man", "dig", "hole").past(), "The man dug the hole.");
/* 1431:1378 */     generator.test(new RoleFrame("man", "dig", "hole").future(), "The man will dig the hole.");
/* 1432:1379 */     generator.test(new RoleFrame("man", "dig", "hole").passive(), "The hole is dug by the man.");
/* 1433:1380 */     generator.test(new RoleFrame("man", "dig", "hole").negative(), "The man doesn't dig the hole.");
/* 1434:1381 */     generator.test(new RoleFrame("man", "dig", "hole").may(), "The man may dig the hole.");
/* 1435:1382 */     generator.test(new RoleFrame("man", "dig", "hole").progressive(), "The man is digging the hole.");
/* 1436:1383 */     generator.test(new RoleFrame("man", "dig", "hole").modify("quickly"), "The man digs the hole quickly.");
/* 1437:     */     
/* 1438:1385 */     System.out.println("\nConnections\n");
/* 1439:     */     
/* 1440:1387 */     RoleFrame c1 = new RoleFrame("man", "ran");
/* 1441:1388 */     RoleFrame c2 = new RoleFrame("soldier", "appear");
/* 1442:1389 */     generator.test(c1.connect("after", c2), "The man runs after the soldier appears.");
/* 1443:1390 */     generator.test(c1.after(c2), "The man runs after the soldier appears.");
/* 1444:1391 */     generator.test(c1.before(c2), "The man runs before the soldier appears.");
/* 1445:1392 */     generator.test(c1.makeWhile(c2), "The man runs while the soldier appears.");
/* 1446:1393 */     generator.test(c1.because(c2), "The man runs because the soldier appears.");
/* 1447:     */     
/* 1448:     */ 
/* 1449:     */ 
/* 1450:1397 */     generator
/* 1451:1398 */       .test(new RoleFrame("boy", "hit", "ball").addRole("with", "bat").addRole("toward", "fence"), "The boy hits the ball with the bat toward the fence.");
/* 1452:1399 */     generator.test(new RoleFrame("boy", "hit", "ball").makeNegative().makePast().makePassive(), "The ball wasn't hit by the boy.");
/* 1453:     */     
/* 1454:1401 */     System.out.println("\nMind's Eye\n");
/* 1455:     */     
/* 1456:1403 */     StartEntity man = new StartEntity("man").addFeature("large").makeIndefinite();
/* 1457:1404 */     StartEntity woman = new StartEntity("woman").addFeature("tall");
/* 1458:1405 */     StartEntity soldier = new StartEntity("soldier");
/* 1459:1406 */     StartEntity box = new StartEntity("box").addFeature("heavy").addFeature("black").makeIndefinite();
/* 1460:1407 */     StartEntity ball = new StartEntity("ball").addFeature("green");
/* 1461:1408 */     generator.test(new RoleFrame(ball, "bounce").progressive(), "The green ball is bouncing.");
/* 1462:1409 */     generator.test(new RoleFrame(man, "give", box).addRole("to", woman).makePast(), "A large man gave a heavy black box to the tall woman.");
/* 1463:1410 */     generator
/* 1464:1411 */       .test(new RoleFrame(man.definite(), "flee").future().after(new RoleFrame(soldier, "appear").present()), "The large man will flee after the soldier appears.");
/* 1465:     */   }
/* 1466:     */   
/* 1467:     */   public void runEmbeddingTests()
/* 1468:     */   {
/* 1469:1419 */     Generator generator = getGenerator();
/* 1470:     */     
/* 1471:     */ 
/* 1472:     */ 
/* 1473:     */ 
/* 1474:     */ 
/* 1475:     */ 
/* 1476:1426 */     StartEntity x = new StartEntity("man-1").addFeature("tall");
/* 1477:1427 */     StartEntity y = new StartEntity("man-2").addFeature("short");
/* 1478:1428 */     StartEntity c = new StartEntity("woman");
/* 1479:1429 */     StartEntity p1 = new StartEntity("package");
/* 1480:1430 */     StartEntity p2 = new StartEntity("package");
/* 1481:1431 */     StartEntity p3 = new StartEntity("package").restrict("with", new StartEntity("ribbon").indefinite());
/* 1482:1432 */     StartEntity d = new StartEntity("child");
/* 1483:     */     
/* 1484:     */ 
/* 1485:1435 */     p1.that(new RoleFrame(c, "bring", p1).past());
/* 1486:     */     
/* 1487:1437 */     p2.which(new RoleFrame(c, "bring", p2).past());
/* 1488:     */     
/* 1489:1439 */     d.whom(new RoleFrame(c, "bring", d).past());
/* 1490:     */     
/* 1491:     */ 
/* 1492:     */ 
/* 1493:1443 */     RoleFrame g1That = new RoleFrame(x, "give", p1).past();
/* 1494:     */     
/* 1495:1445 */     g1That.addRole("to", y);
/* 1496:     */     
/* 1497:1447 */     g1That.addModifier("now");
/* 1498:     */     
/* 1499:1449 */     RoleFrame g2Which = new RoleFrame(x, "give", p2).past();
/* 1500:     */     
/* 1501:1451 */     g2Which.addRole("to", y);
/* 1502:     */     
/* 1503:1453 */     g2Which.addModifier("now");
/* 1504:     */     
/* 1505:1455 */     RoleFrame g3Whom = new RoleFrame(y, "kiss", d).past();
/* 1506:     */     
/* 1507:1457 */     generator.test(g1That, "The tall man gave the package that the woman brought to the short man now.");
/* 1508:     */     
/* 1509:1459 */     generator.test(g2Which, "The tall man gave the package which the woman brought to the short man now.");
/* 1510:     */     
/* 1511:1461 */     generator.test(g3Whom, "The short man kissed the child whom the woman brought.");
/* 1512:     */   }
/* 1513:     */   
/* 1514:     */   private String generateViaCache(Entity t)
/* 1515:     */   {
/* 1516:1469 */     return generateViaCache(t, "present");
/* 1517:     */   }
/* 1518:     */   
/* 1519:     */   private String generateViaCache(Entity t, String marker)
/* 1520:     */   {
/* 1521:1473 */     String key = marker + ": " + t.asStringWithoutIndexes();
/* 1522:     */     
/* 1523:1475 */     String result = (String)getStartGeneratorCache().get(key);
/* 1524:     */     
/* 1525:     */ 
/* 1526:     */ 
/* 1527:     */ 
/* 1528:     */ 
/* 1529:     */ 
/* 1530:     */ 
/* 1531:1483 */     return result;
/* 1532:     */   }
/* 1533:     */   
/* 1534:     */   private void saveInCache(Entity t, String marker, String result)
/* 1535:     */   {
/* 1536:1487 */     String key = marker + ": " + t.asStringWithoutIndexes();
/* 1537:1488 */     getStartGeneratorCache().put(key, result);
/* 1538:     */   }
/* 1539:     */   
/* 1540:     */   public static HashMap<String, String> getStartGeneratorCache()
/* 1541:     */   {
/* 1542:1492 */     if (startGeneratorCache == null) {
/* 1543:1493 */       startGeneratorCache = new HashMap();
/* 1544:     */     }
/* 1545:1495 */     return startGeneratorCache;
/* 1546:     */   }
/* 1547:     */   
/* 1548:     */   public static void setStartGeneratorCache(HashMap<String, String> cache)
/* 1549:     */   {
/* 1550:1499 */     startGeneratorCache = cache;
/* 1551:     */   }
/* 1552:     */   
/* 1553:     */   public static void main(String[] ignore)
/* 1554:     */     throws Exception
/* 1555:     */   {
/* 1556:1503 */     Mark.say(
/* 1557:     */     
/* 1558:     */ 
/* 1559:     */ 
/* 1560:     */ 
/* 1561:     */ 
/* 1562:     */ 
/* 1563:1510 */       new Object[] { "Begin" });Mark.say(new Object[] { Translator.getTranslator().translate("Peter is a person.") });Translator.getTranslator().translate("Paul is a person.");Entity s = Translator.getTranslator().translate("In order to murder Peter, Paul stabbed Peter.");Mark.say(new Object[] { s.getElements().get(0) });Mark.say(new Object[] { getGenerator().generate((Entity)s.getElements().get(0)) });Mark.say(new Object[] { "End" });
/* 1564:     */   }
/* 1565:     */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     start.Generator
 * JD-Core Version:    0.7.0.1
 */