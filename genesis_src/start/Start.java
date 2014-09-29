/*    1:     */ package start;
/*    2:     */ 
/*    3:     */ import Signals.BetterSignal;
/*    4:     */ import adam.RPCBox;
/*    5:     */ import bridge.reps.entities.Bundle;
/*    6:     */ import bridge.reps.entities.Entity;
/*    7:     */ import bridge.reps.entities.Relation;
/*    8:     */ import bridge.reps.entities.Sequence;
/*    9:     */ import bridge.reps.entities.Thread;
/*   10:     */ import connections.Connections;
/*   11:     */ import connections.Ports;
/*   12:     */ import connections.WiredBox;
/*   13:     */ import constants.Markers;
/*   14:     */ import gui.ActivityMonitor;
/*   15:     */ import gui.WiredSplitPane;
/*   16:     */ import java.io.File;
/*   17:     */ import java.io.FileInputStream;
/*   18:     */ import java.io.FileOutputStream;
/*   19:     */ import java.io.ObjectInputStream;
/*   20:     */ import java.io.ObjectOutputStream;
/*   21:     */ import java.io.UnsupportedEncodingException;
/*   22:     */ import java.net.URLEncoder;
/*   23:     */ import java.util.ArrayList;
/*   24:     */ import java.util.HashMap;
/*   25:     */ import java.util.UUID;
/*   26:     */ import java.util.Vector;
/*   27:     */ import javax.swing.JCheckBox;
/*   28:     */ import links.words.BundleGenerator;
/*   29:     */ import parameters.Switch;
/*   30:     */ import persistence.JCheckBoxWithMemory;
/*   31:     */ import text.Html;
/*   32:     */ import translator.Translator;
/*   33:     */ import tts.Talker;
/*   34:     */ import utils.IParser;
/*   35:     */ import utils.Mark;
/*   36:     */ import utils.Timer;
/*   37:     */ 
/*   38:     */ public class Start
/*   39:     */   extends StartFoundation
/*   40:     */   implements WiredBox, IParser
/*   41:     */ {
/*   42:  50 */   private boolean debug = false;
/*   43:  52 */   private static HashMap<String, ArrayList<String>> meaningRestrictionMap = new HashMap();
/*   44:     */   private static HashMap<String, String> startParserCache;
/*   45:  60 */   public static String wireServer = "http://glue.csail.mit.edu/WireServer";
/*   46:     */   RPCBox clientBox;
/*   47:     */   WiredBox clientProxy;
/*   48:     */   RPCBox serverBox;
/*   49:     */   WiredBox serverProxy;
/*   50:  72 */   private final String normalServer = "genesis";
/*   51:  74 */   private final String experimentalServer = "e-genesis";
/*   52:     */   public static final String LEFT = "First perspective";
/*   53:     */   public static final String RIGHT = "Second perspective";
/*   54:     */   public static final String BOTH = "Both perspectives";
/*   55:     */   public static final String NEITHER = "Neither perspective";
/*   56:     */   public static final String STOP = "Stop";
/*   57:     */   public static final String PAUSE = "Pause";
/*   58:     */   public static final String SELF = "self port";
/*   59:     */   public static final String TRIPLES = "tripple port";
/*   60:     */   public static final String PERSONA = "persona port";
/*   61:     */   public static final String START_VIEWER_PORT = "start viewer port";
/*   62:     */   public static final String STORY_MODE = "use-kb";
/*   63:     */   public static final String SENTENCE_MODE = "parse";
/*   64: 108 */   public static String STAGE_DIRECTION_PORT = "stage direction port";
/*   65: 110 */   private String mode = "parse";
/*   66:     */   public static final String SENTENCE = "sentence";
/*   67:     */   public static final String MODE = "change-mode";
/*   68:     */   public static final String PARSE = "parse";
/*   69:     */   public static final String TEST_PARSE = "test parse";
/*   70:     */   private String name;
/*   71:     */   private HashMap<String, Entity> thingMap;
/*   72:     */   private HashMap<String, Relation> relationMap;
/*   73:     */   private HashMap<String, String> nameSet;
/*   74:     */   private HashMap<String, Entity> sessionMap;
/*   75: 130 */   private int dummyIndex = 0;
/*   76: 132 */   private static Start start = null;
/*   77: 134 */   UUID uuid = UUID.randomUUID();
/*   78:     */   private String processedSentence;
/*   79:     */   
/*   80:     */   public static Start getStart()
/*   81:     */   {
/*   82: 139 */     if (start == null) {
/*   83: 140 */       start = new Start();
/*   84:     */     }
/*   85: 142 */     return start;
/*   86:     */   }
/*   87:     */   
/*   88:     */   public Start()
/*   89:     */   {
/*   90: 148 */     this.name = "Start connection";
/*   91:     */     
/*   92: 150 */     Connections.wire("to activity monitor", this, ActivityMonitor.getActivityMonitor());
/*   93:     */     
/*   94: 152 */     Connections.getPorts(this).addSignalProcessor("sentence", "process");
/*   95: 153 */     Connections.getPorts(this).addSignalProcessor("change-mode", "callSetMode");
/*   96: 154 */     Connections.getPorts(this).addSignalProcessor("persona port", "testPersonaPort");
/*   97:     */     
/*   98:     */ 
/*   99: 157 */     establishNetworkConnection();
/*  100:     */   }
/*  101:     */   
/*  102:     */   private void establishNetworkConnection()
/*  103:     */   {
/*  104: 162 */     if (!createClient())
/*  105:     */     {
/*  106: 163 */       StartServerBox.getStartServerBox();
/*  107: 164 */       createClient();
/*  108:     */     }
/*  109:     */   }
/*  110:     */   
/*  111:     */   private boolean createClient()
/*  112:     */   {
/*  113:     */     try
/*  114:     */     {
/*  115: 170 */       this.clientBox = PhraseFactory.getPhraseFactory().getClientBox();
/*  116:     */       
/*  117:     */ 
/*  118:     */ 
/*  119: 174 */       return true;
/*  120:     */     }
/*  121:     */     catch (Exception e)
/*  122:     */     {
/*  123: 177 */       Mark.err(new Object[] {"Failed to create Start client" });
/*  124:     */     }
/*  125: 178 */     return false;
/*  126:     */   }
/*  127:     */   
/*  128:     */   public void testPersonaPort(Object o)
/*  129:     */   {
/*  130: 183 */     Mark.say(
/*  131: 184 */       new Object[] { "Load persona from", o });
/*  132:     */   }
/*  133:     */   
/*  134:     */   public void callSetMode(Object input)
/*  135:     */   {
/*  136: 187 */     if (!(input instanceof String)) {
/*  137: 188 */       return;
/*  138:     */     }
/*  139: 190 */     if ((input != "use-kb") && (input != "parse")) {
/*  140: 191 */       return;
/*  141:     */     }
/*  142: 193 */     setMode((String)input);
/*  143:     */   }
/*  144:     */   
/*  145:     */   public void setStoryMode()
/*  146:     */   {
/*  147: 197 */     callSetMode("use-kb");
/*  148:     */   }
/*  149:     */   
/*  150:     */   public void setRegularMode()
/*  151:     */   {
/*  152: 201 */     callSetMode("use-kb");
/*  153:     */   }
/*  154:     */   
/*  155:     */   public void process(Object input)
/*  156:     */   {
/*  157: 206 */     String marker = null;
/*  158: 207 */     if ((input instanceof BetterSignal))
/*  159:     */     {
/*  160: 208 */       marker = (String)((BetterSignal)input).get(0, String.class);
/*  161: 209 */       input = ((BetterSignal)input).get(1, String.class);
/*  162: 210 */       Mark.say(new Object[] {"Process STAGE_DIR: ", input });
/*  163:     */     }
/*  164: 212 */     if (!(input instanceof String))
/*  165:     */     {
/*  166: 213 */       Mark.err(new Object[] {"Bad input is a", input.getClass() });
/*  167: 214 */       return;
/*  168:     */     }
/*  169: 216 */     String string = conditionString((String)input);
/*  170:     */     
/*  171: 218 */     Sequence result = parse(string);
/*  172: 220 */     if (marker == null) {
/*  173: 221 */       Connections.getPorts(this).transmit("parse", result);
/*  174:     */     } else {
/*  175: 224 */       Connections.getPorts(this).transmit("parse", new BetterSignal(new Object[] { marker, result }));
/*  176:     */     }
/*  177: 226 */     if (Switch.showStartProcessingDetails.isSelected()) {
/*  178: 227 */       Connections.getPorts(this).transmit("start viewer port", "<hr/>");
/*  179:     */     }
/*  180:     */   }
/*  181:     */   
/*  182:     */   private void turnStartBetaLightOn()
/*  183:     */   {
/*  184: 232 */     if (!Switch.useStartCache.isSelected()) {
/*  185: 233 */       Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.START_BETA_WORKING, Boolean.valueOf(true) }));
/*  186:     */     }
/*  187:     */   }
/*  188:     */   
/*  189:     */   private void turnStartLightOn()
/*  190:     */   {
/*  191: 238 */     if (!Switch.useStartCache.isSelected())
/*  192:     */     {
/*  193: 239 */       if (Switch.useStartServer.isSelected()) {
/*  194: 241 */         Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.START_SERVER_WORKING, Boolean.valueOf(true) }));
/*  195:     */       } else {
/*  196: 244 */         Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.START_WORKING, Boolean.valueOf(true) }));
/*  197:     */       }
/*  198: 246 */       if (Switch.useStartBeta.isSelected()) {
/*  199: 247 */         Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.START_BETA_WORKING, Boolean.valueOf(true) }));
/*  200:     */       }
/*  201:     */     }
/*  202:     */   }
/*  203:     */   
/*  204:     */   private void turnStartLightsOff()
/*  205:     */   {
/*  206: 253 */     Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.START_SERVER_WORKING, Boolean.valueOf(false) }));
/*  207: 254 */     Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.START_WORKING, Boolean.valueOf(false) }));
/*  208: 255 */     Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.START_BETA_WORKING, Boolean.valueOf(false) }));
/*  209:     */   }
/*  210:     */   
/*  211:     */   public Sequence processForTestor(Object input)
/*  212:     */   {
/*  213: 260 */     String marker = null;
/*  214: 261 */     if ((input instanceof BetterSignal))
/*  215:     */     {
/*  216: 262 */       marker = (String)((BetterSignal)input).get(0, String.class);
/*  217: 263 */       input = ((BetterSignal)input).get(1, String.class);
/*  218: 264 */       Mark.say(new Object[] {"Process STAGE_DIR: ", input });
/*  219:     */     }
/*  220: 266 */     if (!(input instanceof String))
/*  221:     */     {
/*  222: 267 */       Mark.err(new Object[] {"Bad input is a", input.getClass() });
/*  223: 268 */       return null;
/*  224:     */     }
/*  225: 270 */     String string = conditionString((String)input);
/*  226: 271 */     Sequence normalResult = null;
/*  227: 272 */     Sequence experimentalResult = null;
/*  228: 273 */     Sequence transmittedResult = null;
/*  229: 274 */     String normalString = "";
/*  230: 275 */     String testString = "";
/*  231:     */     
/*  232:     */ 
/*  233:     */ 
/*  234: 279 */     transmittedResult = normalResult;
/*  235: 280 */     if (Switch.useStartBeta.isSelected())
/*  236:     */     {
/*  237: 281 */       experimentalResult = parse(string);
/*  238: 282 */       transmittedResult = experimentalResult;
/*  239: 283 */       testExperimentalStart(string, testString, normalString);
/*  240:     */     }
/*  241:     */     else
/*  242:     */     {
/*  243: 286 */       normalResult = parse(string);
/*  244: 287 */       transmittedResult = normalResult;
/*  245:     */     }
/*  246: 289 */     return transmittedResult;
/*  247:     */   }
/*  248:     */   
/*  249:     */   private boolean testExperimentalStart(String input, String testString, String normalString)
/*  250:     */   {
/*  251: 293 */     if (testString.equals(normalString)) {
/*  252: 295 */       return false;
/*  253:     */     }
/*  254: 298 */     Mark.err(new Object[] {"Difference noted on", input });
/*  255:     */     
/*  256: 300 */     int indexL = 0;
/*  257: 301 */     while ((indexL = normalString.indexOf('(', indexL)) >= 0)
/*  258:     */     {
/*  259: 302 */       int indexR = normalString.indexOf(')', indexL);
/*  260: 303 */       String test = normalString.substring(indexL, indexR + 1);
/*  261: 304 */       if (testString.indexOf(test) < 0) {
/*  262: 305 */         Mark.err(new Object[] {"Current has:     ", test });
/*  263:     */       }
/*  264: 307 */       indexL = indexR;
/*  265:     */     }
/*  266: 309 */     while ((indexL = testString.indexOf('(', indexL)) >= 0)
/*  267:     */     {
/*  268: 310 */       int indexR = testString.indexOf(')', indexL);
/*  269: 311 */       String test = testString.substring(indexL, indexR + 1);
/*  270: 312 */       if (normalString.indexOf(test) < 0) {
/*  271: 313 */         Mark.err(new Object[] {"Experimental has:", test });
/*  272:     */       }
/*  273: 315 */       indexL = indexR;
/*  274:     */     }
/*  275: 317 */     return true;
/*  276:     */   }
/*  277:     */   
/*  278:     */   private boolean processStageDirections(String string)
/*  279:     */   {
/*  280: 321 */     Mark.say(
/*  281:     */     
/*  282:     */ 
/*  283:     */ 
/*  284:     */ 
/*  285:     */ 
/*  286:     */ 
/*  287:     */ 
/*  288:     */ 
/*  289:     */ 
/*  290:     */ 
/*  291:     */ 
/*  292:     */ 
/*  293:     */ 
/*  294:     */ 
/*  295:     */ 
/*  296:     */ 
/*  297:     */ 
/*  298:     */ 
/*  299:     */ 
/*  300:     */ 
/*  301:     */ 
/*  302:     */ 
/*  303:     */ 
/*  304:     */ 
/*  305:     */ 
/*  306:     */ 
/*  307:     */ 
/*  308:     */ 
/*  309:     */ 
/*  310:     */ 
/*  311:     */ 
/*  312:     */ 
/*  313:     */ 
/*  314:     */ 
/*  315:     */ 
/*  316:     */ 
/*  317:     */ 
/*  318:     */ 
/*  319:     */ 
/*  320:     */ 
/*  321:     */ 
/*  322:     */ 
/*  323:     */ 
/*  324:     */ 
/*  325:     */ 
/*  326:     */ 
/*  327:     */ 
/*  328:     */ 
/*  329:     */ 
/*  330:     */ 
/*  331:     */ 
/*  332:     */ 
/*  333:     */ 
/*  334:     */ 
/*  335:     */ 
/*  336:     */ 
/*  337:     */ 
/*  338:     */ 
/*  339:     */ 
/*  340:     */ 
/*  341:     */ 
/*  342:     */ 
/*  343:     */ 
/*  344:     */ 
/*  345:     */ 
/*  346:     */ 
/*  347:     */ 
/*  348:     */ 
/*  349:     */ 
/*  350:     */ 
/*  351:     */ 
/*  352:     */ 
/*  353:     */ 
/*  354:     */ 
/*  355:     */ 
/*  356:     */ 
/*  357:     */ 
/*  358:     */ 
/*  359:     */ 
/*  360: 401 */       new Object[] { "Processing stage direction", string });
/*  361: 322 */     if ("First perspective".equalsIgnoreCase(string))
/*  362:     */     {
/*  363: 323 */       Connections.getPorts(this).transmit(STAGE_DIRECTION_PORT, "Neither perspective");
/*  364: 324 */       Connections.getPorts(this).transmit(STAGE_DIRECTION_PORT, "First perspective");
/*  365: 325 */       return true;
/*  366:     */     }
/*  367: 327 */     if ("Second perspective".equalsIgnoreCase(string))
/*  368:     */     {
/*  369: 328 */       Connections.getPorts(this).transmit(STAGE_DIRECTION_PORT, "Neither perspective");
/*  370: 329 */       Connections.getPorts(this).transmit(STAGE_DIRECTION_PORT, "Second perspective");
/*  371: 330 */       return true;
/*  372:     */     }
/*  373: 332 */     if ("Both perspectives".equalsIgnoreCase(string))
/*  374:     */     {
/*  375: 333 */       Mark.say(new Object[] {"B" });
/*  376: 334 */       Connections.getPorts(this).transmit(STAGE_DIRECTION_PORT, "Neither perspective");
/*  377: 335 */       Connections.getPorts(this).transmit(STAGE_DIRECTION_PORT, "First perspective");
/*  378: 336 */       Connections.getPorts(this).transmit(STAGE_DIRECTION_PORT, "Second perspective");
/*  379: 337 */       return true;
/*  380:     */     }
/*  381: 339 */     if ("Neither perspective".equalsIgnoreCase(string))
/*  382:     */     {
/*  383: 340 */       Connections.getPorts(this).transmit(STAGE_DIRECTION_PORT, "Neither perspective");
/*  384: 341 */       return true;
/*  385:     */     }
/*  386: 343 */     if ("Show first perspective".equalsIgnoreCase(string))
/*  387:     */     {
/*  388: 344 */       Connections.getPorts(this).transmit(STAGE_DIRECTION_PORT, WiredSplitPane.SHOW_LEFT);
/*  389: 345 */       return true;
/*  390:     */     }
/*  391: 347 */     if ("Show second perspective".equalsIgnoreCase(string))
/*  392:     */     {
/*  393: 348 */       Connections.getPorts(this).transmit(STAGE_DIRECTION_PORT, WiredSplitPane.SHOW_RIGHT);
/*  394: 349 */       return true;
/*  395:     */     }
/*  396: 351 */     if ("Show both perspectives".equalsIgnoreCase(string))
/*  397:     */     {
/*  398: 352 */       Connections.getPorts(this).transmit(STAGE_DIRECTION_PORT, WiredSplitPane.SHOW_BOTH);
/*  399: 353 */       return true;
/*  400:     */     }
/*  401: 355 */     if ("Clear story memory".equalsIgnoreCase(string))
/*  402:     */     {
/*  403: 356 */       Connections.getPorts(this).transmit(STAGE_DIRECTION_PORT, "reset");
/*  404: 357 */       return true;
/*  405:     */     }
/*  406: 359 */     if ("Start reflective knowledge".equalsIgnoreCase(string))
/*  407:     */     {
/*  408: 360 */       Mark.say(new Object[] {"Switching to reflective knowledge tab" });
/*  409: 361 */       Connections.getPorts(this).transmit("switch tab", "Concept knowledge");
/*  410: 362 */       Connections.getPorts(this).transmit("self port", "parse");
/*  411: 363 */       Connections.getPorts(this).transmit(STAGE_DIRECTION_PORT, "Concept knowledge");
/*  412: 364 */       return true;
/*  413:     */     }
/*  414: 366 */     if ("Start commonsense knowledge".equalsIgnoreCase(string))
/*  415:     */     {
/*  416: 367 */       Mark.say(new Object[] {"Switching to commonsense knowledge tab" });
/*  417: 368 */       Connections.getPorts(this).transmit("switch tab", "Commonsense knowledge");
/*  418: 369 */       Connections.getPorts(this).transmit("self port", "parse");
/*  419: 370 */       return true;
/*  420:     */     }
/*  421: 372 */     if ("Start general knowledge".equalsIgnoreCase(string))
/*  422:     */     {
/*  423: 373 */       Connections.getPorts(this).transmit("switch tab", "silence");
/*  424: 374 */       Connections.getPorts(this).transmit("self port", "parse");
/*  425: 375 */       return true;
/*  426:     */     }
/*  427: 377 */     if (string.startsWith("Pause"))
/*  428:     */     {
/*  429: 378 */       String minis = string.substring("Pause".length()).trim();
/*  430: 379 */       if (minis.isEmpty()) {
/*  431: 380 */         Connections.getPorts(this).transmit(STAGE_DIRECTION_PORT, "Pause");
/*  432:     */       } else {
/*  433: 383 */         Talker.getTalker().sleep(minis);
/*  434:     */       }
/*  435: 385 */       return true;
/*  436:     */     }
/*  437: 387 */     if ("The end".equalsIgnoreCase(string))
/*  438:     */     {
/*  439: 388 */       Connections.getPorts(this).transmit("self port", "parse");
/*  440: 389 */       Connections.getPorts(this).transmit(STAGE_DIRECTION_PORT, "The end");
/*  441: 390 */       Connections.getPorts(this).transmit(STAGE_DIRECTION_PORT, "Pause");
/*  442: 391 */       return true;
/*  443:     */     }
/*  444: 393 */     if (string.startsWith("Load persona"))
/*  445:     */     {
/*  446: 394 */       Connections.getPorts(this).transmit("persona port", string.substring("Load persona".length()).trim());
/*  447: 395 */       return true;
/*  448:     */     }
/*  449: 397 */     if (string.startsWith("Start story titled")) {
/*  450: 398 */       Connections.getPorts(this).transmit("switch tab", "Story");
/*  451:     */     }
/*  452: 400 */     return false;
/*  453:     */   }
/*  454:     */   
/*  455:     */   private String conditionString(String s)
/*  456:     */   {
/*  457: 404 */     StringBuffer buffer = new StringBuffer(s.trim());
/*  458: 406 */     while (".?".indexOf(buffer.charAt(buffer.length() - 1)) >= 0) {
/*  459: 407 */       buffer.deleteCharAt(buffer.length() - 1);
/*  460:     */     }
/*  461:     */     int index;
/*  462: 410 */     while ((index = buffer.indexOf("\n")) >= 0)
/*  463:     */     {
/*  464:     */       int index;
/*  465: 411 */       buffer.replace(index, index + 1, " ");
/*  466:     */     }
/*  467: 413 */     while ((index = buffer.indexOf("  ")) >= 0) {
/*  468: 414 */       buffer.replace(index, index + 1, "");
/*  469:     */     }
/*  470: 416 */     return buffer.toString();
/*  471:     */   }
/*  472:     */   
/*  473:     */   public Sequence parse(String sentence)
/*  474:     */   {
/*  475: 420 */     if (this.mode != "use-kb") {
/*  476: 421 */       clearLocalTripleMaps();
/*  477:     */     } else {
/*  478: 426 */       getRelationMap().clear();
/*  479:     */     }
/*  480: 429 */     long translationTime = System.currentTimeMillis();
/*  481: 430 */     this.processedSentence = processSentence(sentence);
/*  482:     */     
/*  483: 432 */     String time = Timer.laptime("Timer", translationTime);
/*  484: 433 */     long currentTime = System.currentTimeMillis();
/*  485: 435 */     if (currentTime > translationTime + 5000L) {
/*  486: 436 */       Mark.say(new Object[] {"Slow beyond description (", Long.valueOf((currentTime - translationTime) / 1000L), " sec.) on:", sentence });
/*  487:     */     }
/*  488: 438 */     if (this.processedSentence == null)
/*  489:     */     {
/*  490: 439 */       Mark.err(new Object[] {"No Start result for" + sentence });
/*  491: 440 */       return null;
/*  492:     */     }
/*  493: 442 */     if (Switch.startTimer.isSelected())
/*  494:     */     {
/*  495: 443 */       Connections.getPorts(this).transmit("start viewer port", Html.normal("Start result in " + time + "\n" + this.processedSentence));
/*  496:     */       
/*  497:     */ 
/*  498: 446 */       Mark.say(new Object[] {Boolean.valueOf(Switch.startTimer.isSelected()), "Start result in " + time + " for \"" + sentence });
/*  499:     */     }
/*  500: 451 */     Timer.laptime(Switch.startTimer.isSelected(), "Start parsed sentence in ", translationTime);
/*  501: 452 */     Sequence result = processTripples(this.processedSentence);
/*  502: 453 */     if (result.getElements().isEmpty()) {
/*  503: 455 */       return null;
/*  504:     */     }
/*  505: 460 */     return result;
/*  506:     */   }
/*  507:     */   
/*  508:     */   public ArrayList<Triple> getTriples(String string)
/*  509:     */   {
/*  510: 464 */     StringBuffer buffer = new StringBuffer(string);
/*  511:     */     
/*  512:     */ 
/*  513:     */ 
/*  514: 468 */     ArrayList<Triple> triples = new ArrayList();
/*  515:     */     int start;
/*  516: 469 */     while ((start = buffer.indexOf("[")) >= 0)
/*  517:     */     {
/*  518:     */       int start;
/*  519: 470 */       start++;
/*  520: 471 */       int end = buffer.indexOf("]", start);
/*  521: 472 */       triples.add(new Triple(buffer.substring(start, end)));
/*  522: 473 */       buffer.delete(0, end + 1);
/*  523:     */     }
/*  524: 476 */     processGerunds(triples);
/*  525: 477 */     processProperNames(triples);
/*  526: 478 */     processIdentifiers(triples);
/*  527: 479 */     return triples;
/*  528:     */   }
/*  529:     */   
/*  530:     */   public Sequence processTripples(String string)
/*  531:     */   {
/*  532: 485 */     Sequence sequence = new Sequence("semantic-interpretation");
/*  533: 486 */     ArrayList<Triple> triples = getTriples(string);
/*  534: 487 */     Connections.getPorts(this).transmit("tripple port", triples);
/*  535: 489 */     for (Triple t : triples)
/*  536:     */     {
/*  537: 490 */       Mark.say(new Object[] {Boolean.valueOf(this.debug), "Triple:", t });
/*  538:     */       
/*  539: 492 */       String firstString = t.getFirst();
/*  540: 493 */       recordThing(firstString);
/*  541: 494 */       String secondString = t.getSecond();
/*  542: 495 */       recordRelation(secondString);
/*  543: 496 */       String thirdString = t.getThird();
/*  544: 497 */       recordThing(thirdString);
/*  545:     */     }
/*  546: 501 */     for (Triple t : triples)
/*  547:     */     {
/*  548: 502 */       String firstString = t.getFirst();
/*  549: 503 */       String secondString = t.getSecond();
/*  550: 504 */       String thirdString = t.getThird();
/*  551:     */       
/*  552:     */ 
/*  553: 507 */       Entity t1 = getRelation(firstString);
/*  554: 508 */       if (t1 == null) {
/*  555: 510 */         t1 = getThing(firstString);
/*  556:     */       }
/*  557: 513 */       Entity t2 = getRelation(secondString);
/*  558: 514 */       Entity t3 = getRelation(thirdString);
/*  559: 515 */       if (t3 == null) {
/*  560: 516 */         t3 = getThing(thirdString);
/*  561:     */       }
/*  562: 518 */       t2.setSubject(t1);
/*  563: 519 */       t2.setObject(t3);
/*  564: 520 */       sequence.addElement(t2);
/*  565: 521 */       Mark.say(new Object[] {Boolean.valueOf(this.debug), "Map:", t, t2.asString() });
/*  566:     */     }
/*  567: 526 */     return sequence;
/*  568:     */   }
/*  569:     */   
/*  570:     */   private String extractTriples(String string)
/*  571:     */   {
/*  572: 530 */     StringBuffer result = new StringBuffer();
/*  573: 531 */     StringBuffer buffer = new StringBuffer(string);
/*  574:     */     int start;
/*  575: 534 */     while ((start = buffer.indexOf("[")) >= 0)
/*  576:     */     {
/*  577:     */       int start;
/*  578: 535 */       int end = buffer.indexOf("]", start);
/*  579: 536 */       result.append(buffer.substring(start, end + 1) + "\n");
/*  580: 537 */       buffer.delete(0, end + 1);
/*  581:     */     }
/*  582: 539 */     return result.toString();
/*  583:     */   }
/*  584:     */   
/*  585:     */   private void recordRelation(String string)
/*  586:     */   {
/*  587: 543 */     Relation secondThing = (Relation)getRelationMap().get(string);
/*  588: 544 */     if (secondThing == null) {
/*  589: 545 */       getRelationMap().put(string, makeRelation(strip(string)));
/*  590:     */     }
/*  591:     */   }
/*  592:     */   
/*  593:     */   private void recordThing(String string)
/*  594:     */   {
/*  595: 551 */     if (string.endsWith("-0"))
/*  596:     */     {
/*  597: 552 */       Entity t = (Entity)getSessionMap().get(string);
/*  598: 553 */       if (t == null)
/*  599:     */       {
/*  600: 555 */         Entity x = makeThing(string);
/*  601: 556 */         x.setNameSuffix("-0");
/*  602: 557 */         getThingMap().put(string, x);
/*  603:     */       }
/*  604:     */     }
/*  605:     */     else
/*  606:     */     {
/*  607: 561 */       Entity t = (Entity)getThingMap().get(string);
/*  608: 562 */       if (t == null)
/*  609:     */       {
/*  610: 564 */         Entity x = makeThing(string);
/*  611: 565 */         getThingMap().put(string, x);
/*  612:     */       }
/*  613:     */     }
/*  614:     */   }
/*  615:     */   
/*  616:     */   private void processProperNames(ArrayList<Triple> triples)
/*  617:     */   {
/*  618: 571 */     for (Triple triple : triples) {
/*  619: 572 */       if ((strip(triple.getSecond()).equals("is_proper")) && (strip(triple.getThird()).equals("yes")))
/*  620:     */       {
/*  621: 573 */         String name = triple.getFirst();
/*  622: 574 */         getNameSet().put(triple.getFirst(), name);
/*  623:     */       }
/*  624:     */     }
/*  625:     */   }
/*  626:     */   
/*  627:     */   private void processGerunds(ArrayList<Triple> triples)
/*  628:     */   {
/*  629: 580 */     HashMap<String, String> gerunds = new HashMap();
/*  630: 581 */     for (Triple triple : triples) {
/*  631: 582 */       if (triple.getSecond().equals("gerund_of")) {
/*  632: 583 */         gerunds.put(triple.getFirst(), addIdentifierIfNone(triple.getFirst()));
/*  633:     */       }
/*  634:     */     }
/*  635: 586 */     for (Triple triple : triples)
/*  636:     */     {
/*  637: 587 */       Object o = gerunds.get(triple.getFirst());
/*  638: 588 */       if (o != null) {
/*  639: 589 */         triple.setFirst((String)o);
/*  640:     */       }
/*  641: 591 */       o = gerunds.get(triple.getThird());
/*  642: 592 */       if (o != null) {
/*  643: 593 */         triple.setThird((String)o);
/*  644:     */       }
/*  645:     */     }
/*  646:     */   }
/*  647:     */   
/*  648:     */   private void processIdentifiers(ArrayList<Triple> triples)
/*  649:     */   {
/*  650: 600 */     for (Triple triple : triples)
/*  651:     */     {
/*  652: 601 */       triple.setFirst(addIdentifierIfNone(triple.getFirst()));
/*  653: 602 */       triple.setSecond(addIdentifierIfNone(triple.getSecond()));
/*  654: 603 */       triple.setThird(addIdentifierIfNone(triple.getThird()));
/*  655:     */     }
/*  656:     */   }
/*  657:     */   
/*  658:     */   public Entity makeThing(String x)
/*  659:     */   {
/*  660: 628 */     if (x.equalsIgnoreCase("null")) {
/*  661: 629 */       return Markers.NULL;
/*  662:     */     }
/*  663: 631 */     String word = strip(x);
/*  664: 632 */     Entity thing = new Entity(word);
/*  665: 633 */     Bundle bundle = null;
/*  666:     */     try
/*  667:     */     {
/*  668: 635 */       Integer.parseInt(word);
/*  669: 636 */       bundle = new Bundle();
/*  670: 637 */       Thread thread = new Thread();
/*  671: 638 */       thread.add("number");
/*  672: 639 */       thread.add("integer");
/*  673: 640 */       thread.add(word);
/*  674: 641 */       bundle.add(thread);
/*  675:     */     }
/*  676:     */     catch (NumberFormatException e)
/*  677:     */     {
/*  678: 646 */       bundle = restrict(word, BundleGenerator.getBundle(word));
/*  679:     */     }
/*  680: 649 */     if ((bundle != null) && (bundle.size() > 0)) {
/*  681: 652 */       thing.setBundle(bundle);
/*  682:     */     }
/*  683: 655 */     return thing;
/*  684:     */   }
/*  685:     */   
/*  686:     */   public static Relation makeRelation(String word, Entity s, Entity o)
/*  687:     */   {
/*  688: 659 */     Relation r = makeRelation(word);
/*  689: 660 */     r.setSubject(s);
/*  690: 661 */     r.setObject(o);
/*  691: 662 */     return r;
/*  692:     */   }
/*  693:     */   
/*  694:     */   private static Relation makeRelation(String word)
/*  695:     */   {
/*  696: 666 */     Relation relation = new Relation(strip(word), Markers.NULL, Markers.NULL);
/*  697: 667 */     Bundle bundle = restrict(word, BundleGenerator.getBundle(word));
/*  698: 668 */     if ((bundle != null) && (bundle.size() > 0)) {
/*  699: 669 */       relation.setBundle(bundle);
/*  700:     */     }
/*  701: 671 */     return relation;
/*  702:     */   }
/*  703:     */   
/*  704: 674 */   int identifier = 0;
/*  705:     */   
/*  706:     */   private String addIdentifierIfNone(String s)
/*  707:     */   {
/*  708: 677 */     if (s.equalsIgnoreCase("null")) {
/*  709: 678 */       return s;
/*  710:     */     }
/*  711: 680 */     String name = (String)getNameSet().get(s);
/*  712: 681 */     if (name != null)
/*  713:     */     {
/*  714: 682 */       Mark.say(new Object[] {Boolean.valueOf(this.debug), "It's a name", s, name });
/*  715: 683 */       return name;
/*  716:     */     }
/*  717: 685 */     int index = Math.max(s.lastIndexOf('-'), s.lastIndexOf('+'));
/*  718:     */     
/*  719:     */ 
/*  720:     */ 
/*  721:     */ 
/*  722:     */ 
/*  723:     */ 
/*  724: 692 */     String candidate = s.substring(index + 1);
/*  725:     */     try
/*  726:     */     {
/*  727: 694 */       Integer.parseInt(candidate);
/*  728: 695 */       return s;
/*  729:     */     }
/*  730:     */     catch (NumberFormatException e) {}
/*  731: 700 */     return s + "-" + ++this.identifier;
/*  732:     */   }
/*  733:     */   
/*  734:     */   public static String strip(String s)
/*  735:     */   {
/*  736: 705 */     int index = s.lastIndexOf('-');
/*  737: 706 */     index = Math.max(index, s.lastIndexOf('+'));
/*  738: 707 */     if (index < 0) {
/*  739: 708 */       return s;
/*  740:     */     }
/*  741: 710 */     String candidate = s.substring(index + 1);
/*  742:     */     try
/*  743:     */     {
/*  744: 712 */       Integer.parseInt(candidate);
/*  745:     */     }
/*  746:     */     catch (NumberFormatException e)
/*  747:     */     {
/*  748: 715 */       return s;
/*  749:     */     }
/*  750: 717 */     String result = s.substring(0, index);
/*  751: 718 */     return result;
/*  752:     */   }
/*  753:     */   
/*  754:     */   public static String suffix(String s)
/*  755:     */   {
/*  756: 722 */     int index = s.lastIndexOf('-');
/*  757: 723 */     index = Math.max(index, s.lastIndexOf('+'));
/*  758: 724 */     if (index < 0) {
/*  759: 725 */       return null;
/*  760:     */     }
/*  761: 727 */     return s.substring(index + 1);
/*  762:     */   }
/*  763:     */   
/*  764:     */   public void clearLocalTripleMaps()
/*  765:     */   {
/*  766: 744 */     boolean debug = true;
/*  767: 745 */     getThingMap().clear();
/*  768: 746 */     getRelationMap().clear();
/*  769: 747 */     getNameSet().clear();
/*  770: 748 */     meaningRestrictionMap.clear();
/*  771:     */   }
/*  772:     */   
/*  773:     */   public String clearStartReferences()
/*  774:     */   {
/*  775: 753 */     String probe = "query=(flush-lf-kpartition)&action=query&server=" + getServer();
/*  776: 754 */     StringBuffer buffer = processProbe(probe);
/*  777: 755 */     if ((buffer.length() == 0) || (!getThingMap().isEmpty())) {
/*  778: 756 */       Mark.err(new Object[] {"Start.clearStartReferences failed" });
/*  779:     */     }
/*  780: 758 */     return probe.toString();
/*  781:     */   }
/*  782:     */   
/*  783:     */   public static boolean isExperimentalStart()
/*  784:     */   {
/*  785: 762 */     if (Switch.useStartBeta.isSelected()) {
/*  786: 763 */       return true;
/*  787:     */     }
/*  788: 765 */     return false;
/*  789:     */   }
/*  790:     */   
/*  791:     */   public String getServer()
/*  792:     */   {
/*  793: 769 */     if (Switch.useStartBeta.isSelected()) {
/*  794: 770 */       return getExperimentalServer();
/*  795:     */     }
/*  796: 772 */     return getNormalServer();
/*  797:     */   }
/*  798:     */   
/*  799:     */   public String getNormalServer()
/*  800:     */   {
/*  801: 776 */     return "genesis";
/*  802:     */   }
/*  803:     */   
/*  804:     */   public String getExperimentalServer()
/*  805:     */   {
/*  806: 780 */     return "e-genesis";
/*  807:     */   }
/*  808:     */   
/*  809:     */   public String processSentence(String sentence)
/*  810:     */   {
/*  811: 784 */     String s = prepareSentence(sentence);
/*  812: 786 */     if ((Switch.useStartCache.isSelected()) && (!Switch.useStartBeta.isSelected()))
/*  813:     */     {
/*  814: 787 */       String result = processViaCache(s);
/*  815: 788 */       if (result != null) {
/*  816: 789 */         return result;
/*  817:     */       }
/*  818:     */     }
/*  819: 792 */     if (this.mode != "use-kb") {
/*  820: 793 */       clearStartReferences();
/*  821:     */     }
/*  822: 795 */     String result = processWithStart(s);
/*  823:     */     
/*  824:     */ 
/*  825: 798 */     getStartParserCache().put(s, result);
/*  826:     */     
/*  827: 800 */     return result;
/*  828:     */   }
/*  829:     */   
/*  830:     */   public String processWithStart(String sentence)
/*  831:     */   {
/*  832: 804 */     String result = null;
/*  833: 808 */     if (Switch.testStartBeta.isSelected())
/*  834:     */     {
/*  835: 809 */       turnStartLightOn();
/*  836: 810 */       String normalResult = processDirectly(sentence, getNormalServer());
/*  837: 811 */       turnStartLightsOff();
/*  838: 812 */       turnStartBetaLightOn();
/*  839: 813 */       String experimentalResult = processDirectly(sentence, getExperimentalServer());
/*  840: 814 */       turnStartLightsOff();
/*  841: 815 */       if ((normalResult == null) || (!normalResult.equals(experimentalResult))) {
/*  842: 816 */         Mark.err(new Object[] {"Experimental test failed on:", sentence });
/*  843:     */       }
/*  844: 818 */       return experimentalResult;
/*  845:     */     }
/*  846:     */     try
/*  847:     */     {
/*  848: 822 */       if ((this.clientBox != null) && (Switch.useStartServer.isSelected()) && (!Switch.useStartBeta.isSelected()))
/*  849:     */       {
/*  850: 823 */         turnStartLightOn();
/*  851: 824 */         result = processViaServer(sentence, this.mode);
/*  852: 825 */         turnStartLightsOff();
/*  853: 826 */         if (result != null) {
/*  854: 827 */           return result;
/*  855:     */         }
/*  856:     */       }
/*  857:     */     }
/*  858:     */     catch (Exception e)
/*  859:     */     {
/*  860: 832 */       Mark.err(new Object[] {"Blow out in processViaServer" });
/*  861:     */       
/*  862: 834 */       turnStartLightOn();
/*  863: 835 */       result = processDirectly(sentence, getServer());
/*  864: 836 */       turnStartLightsOff();
/*  865:     */     }
/*  866: 837 */     return result;
/*  867:     */   }
/*  868:     */   
/*  869:     */   private String processViaCache(String sentence)
/*  870:     */   {
/*  871: 841 */     return (String)getStartParserCache().get(sentence);
/*  872:     */   }
/*  873:     */   
/*  874:     */   public void processName(String request)
/*  875:     */   {
/*  876: 849 */     boolean debug = false;
/*  877:     */     
/*  878: 851 */     String header = "server=" + getServer() + "&action=add-word&query=";
/*  879: 852 */     String content = "(noun \"" + request + "\" nil neuter t nil)";
/*  880: 853 */     String trailer = "";
/*  881: 854 */     String encodedString = "";
/*  882:     */     try
/*  883:     */     {
/*  884: 856 */       encodedString = URLEncoder.encode(content, "UTF-8");
/*  885:     */     }
/*  886:     */     catch (UnsupportedEncodingException e)
/*  887:     */     {
/*  888: 859 */       e.printStackTrace();
/*  889:     */     }
/*  890: 861 */     String probe = header + encodedString + trailer;
/*  891:     */     
/*  892: 863 */     processProbe(probe);
/*  893: 864 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Name processed directly via:", getServer() });
/*  894:     */   }
/*  895:     */   
/*  896:     */   public String processDirectly(String sentence, String server)
/*  897:     */   {
/*  898: 868 */     String header = "query=";
/*  899: 869 */     String result = null;
/*  900:     */     
/*  901:     */ 
/*  902: 872 */     String trailer = "&pa=" + this.mode + "&action=compute-lf&server=" + server;
/*  903: 873 */     String encodedString = "";
/*  904: 874 */     if (Switch.showStartProcessingDetails.isSelected()) {
/*  905: 875 */       Connections.getPorts(this).transmit("start viewer port", Html.normal(Html.bold("Working on: '" + sentence + "'.")));
/*  906:     */     }
/*  907:     */     try
/*  908:     */     {
/*  909: 878 */       encodedString = URLEncoder.encode(sentence, "UTF-8");
/*  910:     */     }
/*  911:     */     catch (UnsupportedEncodingException e)
/*  912:     */     {
/*  913: 881 */       e.printStackTrace();
/*  914:     */     }
/*  915: 883 */     String request = header + encodedString + trailer;
/*  916:     */     try
/*  917:     */     {
/*  918: 888 */       result = processParseRequest(request);
/*  919:     */     }
/*  920:     */     catch (Exception e)
/*  921:     */     {
/*  922: 891 */       e.printStackTrace();
/*  923:     */     }
/*  924: 893 */     return result;
/*  925:     */   }
/*  926:     */   
/*  927:     */   private String processViaServer(String sentence, String mode)
/*  928:     */   {
/*  929: 897 */     String result = null;
/*  930:     */     try
/*  931:     */     {
/*  932: 899 */       Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.START_SERVER_WORKING, Boolean.valueOf(true) }));
/*  933: 900 */       Object[] arguments = { sentence, mode, getServer(), this.uuid.toString() };
/*  934: 901 */       Object value = this.clientBox.rpc("remoteParse", arguments);
/*  935: 902 */       if (value != null) {
/*  936: 903 */         result = (String)value;
/*  937:     */       }
/*  938:     */     }
/*  939:     */     catch (Exception e)
/*  940:     */     {
/*  941: 907 */       Mark.say(new Object[] {"Bug in effort to process sentence remotely!  Give up and issue start request locally." });
/*  942: 908 */       Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.START_SERVER_FAULT, Boolean.valueOf(true) }));
/*  943: 909 */       result = processDirectly(sentence, getServer());
/*  944: 910 */       Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.START_SERVER_FAULT, Boolean.valueOf(false) }));
/*  945:     */     }
/*  946:     */     finally
/*  947:     */     {
/*  948: 913 */       Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.START_SERVER_WORKING, Boolean.valueOf(false) }));
/*  949:     */     }
/*  950: 915 */     return result;
/*  951:     */   }
/*  952:     */   
/*  953:     */   private String remap(String triples)
/*  954:     */   {
/*  955: 922 */     HashMap<String, String> substitutions = new HashMap();
/*  956: 923 */     int index = 0;
/*  957: 924 */     StringBuffer buffer = new StringBuffer(triples);
/*  958:     */     
/*  959:     */ 
/*  960: 927 */     int from = 0;
/*  961:     */     for (;;)
/*  962:     */     {
/*  963: 929 */       int nextPlus = buffer.indexOf("+", from);
/*  964: 930 */       if (nextPlus < 0) {
/*  965:     */         break;
/*  966:     */       }
/*  967: 934 */       int nextSpace = buffer.indexOf(" ", nextPlus);
/*  968: 935 */       int nextBracket = buffer.indexOf("]", nextPlus);
/*  969: 936 */       int winner = 0;
/*  970: 937 */       if ((nextSpace >= 0) && (nextBracket >= 0)) {
/*  971: 938 */         winner = Math.min(nextSpace, nextBracket);
/*  972: 940 */       } else if (nextSpace >= 0) {
/*  973: 941 */         winner = nextSpace;
/*  974: 943 */       } else if (nextBracket >= 0) {
/*  975: 944 */         winner = nextBracket;
/*  976:     */       } else {
/*  977: 947 */         Mark.err(new Object[] {"Ooops, bug in Start.remap" });
/*  978:     */       }
/*  979: 949 */       String key = buffer.substring(nextPlus, winner);
/*  980: 950 */       String substitution = (String)substitutions.get(key);
/*  981: 951 */       if (substitution == null)
/*  982:     */       {
/*  983: 952 */         substitution = Integer.toString(index++);
/*  984: 953 */         substitutions.put(key, substitution);
/*  985:     */       }
/*  986: 955 */       buffer.replace(nextPlus + 1, winner, substitution);
/*  987: 956 */       from = nextPlus + 1;
/*  988:     */     }
/*  989: 958 */     return buffer.toString();
/*  990:     */   }
/*  991:     */   
/*  992:     */   public String generate(String triples)
/*  993:     */   {
/*  994: 962 */     turnGeneratorLightOn();
/*  995: 963 */     boolean debug = false;
/*  996: 964 */     String remap = remap(triples);
/*  997: 965 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Generating from", remap });
/*  998: 966 */     String result = condition(generateWithStart(remap));
/*  999: 967 */     turnGeneratorLightsOff();
/* 1000: 968 */     return result;
/* 1001:     */   }
/* 1002:     */   
/* 1003:     */   private void turnGeneratorLightOn()
/* 1004:     */   {
/* 1005: 972 */     if (!Switch.useStartCache.isSelected())
/* 1006:     */     {
/* 1007: 973 */       if (Switch.useStartServer.isSelected()) {
/* 1008: 974 */         Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.GENERATOR_SERVER_WORKING, 
/* 1009: 975 */           Boolean.valueOf(true) }));
/* 1010:     */       } else {
/* 1011: 978 */         Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.GENERATOR_WORKING, Boolean.valueOf(true) }));
/* 1012:     */       }
/* 1013: 980 */       if (Switch.useStartBeta.isSelected()) {
/* 1014: 981 */         Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.GENERATOR_BETA_WORKING, 
/* 1015: 982 */           Boolean.valueOf(true) }));
/* 1016:     */       }
/* 1017:     */     }
/* 1018:     */   }
/* 1019:     */   
/* 1020:     */   private void turnStartGeneratorBetaLightOn()
/* 1021:     */   {
/* 1022: 988 */     if (!Switch.useStartCache.isSelected()) {
/* 1023: 989 */       Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.GENERATOR_BETA_WORKING, Boolean.valueOf(true) }));
/* 1024:     */     }
/* 1025:     */   }
/* 1026:     */   
/* 1027:     */   private void turnGeneratorLightsOff()
/* 1028:     */   {
/* 1029: 994 */     Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.GENERATOR_WORKING, Boolean.valueOf(false) }));
/* 1030: 995 */     Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.GENERATOR_SERVER_WORKING, Boolean.valueOf(false) }));
/* 1031: 996 */     Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.GENERATOR_BETA_WORKING, Boolean.valueOf(false) }));
/* 1032:     */   }
/* 1033:     */   
/* 1034:     */   public String generateWithStart(String input)
/* 1035:     */   {
/* 1036:1010 */     String result = null;
/* 1037:1014 */     if (Switch.testStartBeta.isSelected())
/* 1038:     */     {
/* 1039:1015 */       turnGeneratorLightOn();
/* 1040:1016 */       String normalResult = generateDirectly(input, getNormalServer());
/* 1041:1017 */       turnGeneratorLightsOff();
/* 1042:1018 */       turnStartGeneratorBetaLightOn();
/* 1043:1019 */       String experimentalResult = generateDirectly(input, getExperimentalServer());
/* 1044:1020 */       turnGeneratorLightsOff();
/* 1045:1021 */       if ((normalResult == null) || (!normalResult.equals(experimentalResult))) {
/* 1046:1022 */         Mark.err(new Object[] {"Experimental generator test failed:\nNormal:", normalResult, "\nExperimentalResult:", experimentalResult });
/* 1047:     */       }
/* 1048:1024 */       return experimentalResult;
/* 1049:     */     }
/* 1050:1027 */     if ((this.clientBox != null) && (Switch.useStartServer.isSelected()) && (!Switch.useStartBeta.isSelected()))
/* 1051:     */     {
/* 1052:1028 */       turnGeneratorLightOn();
/* 1053:1029 */       result = generateViaServer(input);
/* 1054:1030 */       turnGeneratorLightsOff();
/* 1055:1031 */       if (result != null) {
/* 1056:1032 */         return result;
/* 1057:     */       }
/* 1058:     */     }
/* 1059:1035 */     turnGeneratorLightOn();
/* 1060:1036 */     result = generateDirectly(input, getServer());
/* 1061:1037 */     turnGeneratorLightsOff();
/* 1062:1038 */     return result;
/* 1063:     */   }
/* 1064:     */   
/* 1065:     */   public String generateDirectly(String request, String server)
/* 1066:     */   {
/* 1067:1042 */     boolean debug = false;
/* 1068:1043 */     String result = null;
/* 1069:1044 */     String header = "server=" + server + "&fg=no&te=formated-text&de=n&action=generate&query=";
/* 1070:     */     
/* 1071:     */ 
/* 1072:1047 */     String trailer = "";
/* 1073:1048 */     String encodedString = "";
/* 1074:1049 */     if (Switch.showStartProcessingDetails.isSelected()) {
/* 1075:1050 */       Connections.getPorts(this).transmit("start viewer port", Html.normal(Html.bold("Working on: '" + request + "'.")));
/* 1076:     */     }
/* 1077:     */     try
/* 1078:     */     {
/* 1079:1053 */       encodedString = URLEncoder.encode(request, "UTF-8");
/* 1080:     */     }
/* 1081:     */     catch (UnsupportedEncodingException e)
/* 1082:     */     {
/* 1083:1056 */       e.printStackTrace();
/* 1084:     */     }
/* 1085:1058 */     String probe = header + encodedString + trailer;
/* 1086:1059 */     result = processGeneratorRequest(probe);
/* 1087:1060 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Geneated directly via:", server });
/* 1088:1061 */     return result;
/* 1089:     */   }
/* 1090:     */   
/* 1091:     */   private String generateViaServer(String request)
/* 1092:     */   {
/* 1093:1065 */     boolean debug = false;
/* 1094:1066 */     String result = null;
/* 1095:     */     try
/* 1096:     */     {
/* 1097:1068 */       String id = "&uuid=" + this.uuid + "&fg=no";
/* 1098:1069 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Call:", request + id });
/* 1099:1070 */       Object[] arguments = { request, id };
/* 1100:     */       
/* 1101:1072 */       Object value = this.clientBox.rpc("remoteGenerate", arguments);
/* 1102:1073 */       if (value != null)
/* 1103:     */       {
/* 1104:1074 */         result = ((String)value).trim();
/* 1105:1075 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Generated remotely using generator server:", result });
/* 1106:     */       }
/* 1107:     */     }
/* 1108:     */     catch (Exception e)
/* 1109:     */     {
/* 1110:1079 */       Mark.say(new Object[] {"Bug in effort to process sentence with generator server!  Give up and issue start generator request locally." });
/* 1111:1080 */       result = generateDirectly(request, getServer());
/* 1112:     */     }
/* 1113:1082 */     return result;
/* 1114:     */   }
/* 1115:     */   
/* 1116:     */   private String condition(String result)
/* 1117:     */   {
/* 1118:1086 */     if ((result != null) && (result.indexOf("One of the servers") >= 0)) {
/* 1119:1087 */       return null;
/* 1120:     */     }
/* 1121:1089 */     return result;
/* 1122:     */   }
/* 1123:     */   
/* 1124:     */   private String prepareSentence(String s)
/* 1125:     */   {
/* 1126:1094 */     s = extractMeaningMarkers(s);
/* 1127:1095 */     StringBuffer buffer = new StringBuffer(s);
/* 1128:     */     int index;
/* 1129:1097 */     while ((index = buffer.indexOf(" '")) >= 0)
/* 1130:     */     {
/* 1131:     */       int index;
/* 1132:1098 */       buffer.deleteCharAt(index);
/* 1133:     */     }
/* 1134:1100 */     while ((index = buffer.indexOf("leads to")) >= 0) {
/* 1135:1101 */       buffer.replace(index, index + "leads to".length(), "entails");
/* 1136:     */     }
/* 1137:1103 */     return buffer.toString();
/* 1138:     */   }
/* 1139:     */   
/* 1140:     */   private String extractMeaningMarkers(String s)
/* 1141:     */   {
/* 1142:1107 */     meaningRestrictionMap.clear();
/* 1143:1108 */     StringBuffer buffer = new StringBuffer(s.trim());
/* 1144:     */     for (;;)
/* 1145:     */     {
/* 1146:1110 */       int b = buffer.indexOf("(");
/* 1147:1111 */       int e = buffer.indexOf(")", b);
/* 1148:1112 */       if ((b < 0) || (e <= b)) {
/* 1149:     */         break;
/* 1150:     */       }
/* 1151:1113 */       String[] elements = buffer.substring(b + 1, e).split(" ");
/* 1152:1114 */       String word = elements[0];
/* 1153:1115 */       String root = elements[0];
/* 1154:1116 */       ArrayList<String> categories = new ArrayList();
/* 1155:1117 */       for (int i = 2; i < elements.length; i++) {
/* 1156:1118 */         categories.add(elements[i]);
/* 1157:     */       }
/* 1158:1120 */       if (elements.length >= 3)
/* 1159:     */       {
/* 1160:1121 */         root = elements[1];
/* 1161:1122 */         meaningRestrictionMap.put(root, categories);
/* 1162:     */       }
/* 1163:1124 */       buffer.replace(b, e + 1, word);
/* 1164:     */     }
/* 1165:1132 */     return buffer.toString();
/* 1166:     */   }
/* 1167:     */   
/* 1168:     */   private static Bundle restrict(String word, Bundle bundle)
/* 1169:     */   {
/* 1170:1136 */     ArrayList<String> restrictions = (ArrayList)meaningRestrictionMap.get(word);
/* 1171:1138 */     if (restrictions != null)
/* 1172:     */     {
/* 1173:1140 */       Bundle restrictedBundle = new Bundle();
/* 1174:1141 */       for (Thread thread : bundle)
/* 1175:     */       {
/* 1176:1142 */         boolean winner = true;
/* 1177:1143 */         for (String category : restrictions) {
/* 1178:1144 */           if (!thread.contains(category))
/* 1179:     */           {
/* 1180:1145 */             winner = false;
/* 1181:1146 */             break;
/* 1182:     */           }
/* 1183:     */         }
/* 1184:1149 */         if (winner) {
/* 1185:1150 */           restrictedBundle.add(thread);
/* 1186:     */         }
/* 1187:     */       }
/* 1188:1153 */       int size = restrictedBundle.size();
/* 1189:1154 */       if (size == 0)
/* 1190:     */       {
/* 1191:1155 */         Mark.err(new Object[] {"Bundle restricted by", restrictions, "has no threads" });
/* 1192:     */       }
/* 1193:     */       else
/* 1194:     */       {
/* 1195:1157 */         if (size == 1) {
/* 1196:1159 */           return restrictedBundle;
/* 1197:     */         }
/* 1198:1165 */         Thread first = (Thread)restrictedBundle.get(0);
/* 1199:1166 */         restrictedBundle.clear();
/* 1200:1167 */         restrictedBundle.add(first);
/* 1201:1168 */         return restrictedBundle;
/* 1202:     */       }
/* 1203:     */     }
/* 1204:1173 */     return bundle;
/* 1205:     */   }
/* 1206:     */   
/* 1207:     */   public String getName()
/* 1208:     */   {
/* 1209:1178 */     return this.name;
/* 1210:     */   }
/* 1211:     */   
/* 1212:     */   public void setName(String n)
/* 1213:     */   {
/* 1214:1182 */     this.name = n;
/* 1215:     */   }
/* 1216:     */   
/* 1217:     */   public Entity getThing(String key)
/* 1218:     */   {
/* 1219:1186 */     Entity t = (Entity)getSessionMap().get(key);
/* 1220:1187 */     if (t != null) {
/* 1221:1188 */       return t;
/* 1222:     */     }
/* 1223:1190 */     return (Entity)getThingMap().get(key);
/* 1224:     */   }
/* 1225:     */   
/* 1226:     */   public HashMap<String, Entity> getSessionMap()
/* 1227:     */   {
/* 1228:1194 */     if (this.sessionMap == null) {
/* 1229:1195 */       this.sessionMap = new HashMap();
/* 1230:     */     }
/* 1231:1197 */     return this.sessionMap;
/* 1232:     */   }
/* 1233:     */   
/* 1234:     */   public HashMap<String, Entity> getThingMap()
/* 1235:     */   {
/* 1236:1201 */     if (this.thingMap == null) {
/* 1237:1202 */       this.thingMap = new HashMap();
/* 1238:     */     }
/* 1239:1204 */     return this.thingMap;
/* 1240:     */   }
/* 1241:     */   
/* 1242:     */   public Relation getRelation(String key)
/* 1243:     */   {
/* 1244:1208 */     return (Relation)getRelationMap().get(key);
/* 1245:     */   }
/* 1246:     */   
/* 1247:     */   public HashMap<String, Relation> getRelationMap()
/* 1248:     */   {
/* 1249:1212 */     if (this.relationMap == null) {
/* 1250:1213 */       this.relationMap = new HashMap();
/* 1251:     */     }
/* 1252:1215 */     return this.relationMap;
/* 1253:     */   }
/* 1254:     */   
/* 1255:     */   public HashMap<String, String> getNameSet()
/* 1256:     */   {
/* 1257:1219 */     if (this.nameSet == null) {
/* 1258:1220 */       this.nameSet = new HashMap();
/* 1259:     */     }
/* 1260:1222 */     return this.nameSet;
/* 1261:     */   }
/* 1262:     */   
/* 1263:     */   public class Triple
/* 1264:     */   {
/* 1265:     */     String first;
/* 1266:     */     String second;
/* 1267:     */     String third;
/* 1268:     */     
/* 1269:     */     public Triple(String first, String second, String third)
/* 1270:     */     {
/* 1271:1227 */       this.first = first;
/* 1272:1228 */       this.second = second;
/* 1273:1229 */       this.third = third;
/* 1274:     */     }
/* 1275:     */     
/* 1276:     */     public String toString()
/* 1277:     */     {
/* 1278:1233 */       return "[" + this.first + " " + this.second + " " + this.third + "]";
/* 1279:     */     }
/* 1280:     */     
/* 1281:     */     public void setFirst(String first)
/* 1282:     */     {
/* 1283:1237 */       this.first = first;
/* 1284:     */     }
/* 1285:     */     
/* 1286:     */     public void setSecond(String second)
/* 1287:     */     {
/* 1288:1241 */       this.second = second;
/* 1289:     */     }
/* 1290:     */     
/* 1291:     */     public void setThird(String third)
/* 1292:     */     {
/* 1293:1245 */       this.third = third;
/* 1294:     */     }
/* 1295:     */     
/* 1296:     */     public Triple(String string)
/* 1297:     */     {
/* 1298:1249 */       String[] elements = string.split(" ");
/* 1299:1250 */       if (elements.length != 3)
/* 1300:     */       {
/* 1301:1251 */         Mark.err(new Object[] {"Start.processTripple not handed a tripple: " + string });
/* 1302:     */       }
/* 1303:     */       else
/* 1304:     */       {
/* 1305:1258 */         this.first = elements[0];
/* 1306:1259 */         this.second = elements[1];
/* 1307:1260 */         this.third = elements[2];
/* 1308:     */       }
/* 1309:     */     }
/* 1310:     */     
/* 1311:     */     public String getFirst()
/* 1312:     */     {
/* 1313:1267 */       return this.first;
/* 1314:     */     }
/* 1315:     */     
/* 1316:     */     public String getSecond()
/* 1317:     */     {
/* 1318:1271 */       return this.second;
/* 1319:     */     }
/* 1320:     */     
/* 1321:     */     public String getThird()
/* 1322:     */     {
/* 1323:1275 */       return this.third;
/* 1324:     */     }
/* 1325:     */   }
/* 1326:     */   
/* 1327:     */   public String getMode()
/* 1328:     */   {
/* 1329:1281 */     return this.mode;
/* 1330:     */   }
/* 1331:     */   
/* 1332:     */   public void setMode(String mode)
/* 1333:     */   {
/* 1334:1285 */     this.mode = mode;
/* 1335:     */   }
/* 1336:     */   
/* 1337:     */   public static HashMap<String, String> getStartParserCache()
/* 1338:     */   {
/* 1339:1290 */     if (startParserCache == null) {
/* 1340:1291 */       startParserCache = new HashMap();
/* 1341:     */     }
/* 1342:1293 */     return startParserCache;
/* 1343:     */   }
/* 1344:     */   
/* 1345:     */   public static void setStartParserCache(HashMap<String, String> cache)
/* 1346:     */   {
/* 1347:1297 */     startParserCache = cache;
/* 1348:     */   }
/* 1349:     */   
/* 1350:     */   public static void purgeStartCache()
/* 1351:     */   {
/* 1352:1301 */     Mark.say(
/* 1353:     */     
/* 1354:     */ 
/* 1355:     */ 
/* 1356:     */ 
/* 1357:     */ 
/* 1358:     */ 
/* 1359:1308 */       new Object[] { "Purged Start parser cache,", System.getProperty("user.home") + File.separator + "parser.data", "of", Integer.valueOf(getStartParserCache().size()), "items" });getStartParserCache().clear();Mark.say(new Object[] { "Purged Start generator cache,", System.getProperty("user.home") + File.separator + "generator.data", "of", Integer.valueOf(Generator.getStartGeneratorCache().size()), "items" });getStartParserCache().clear();Generator.getStartGeneratorCache().clear();writeStartCaches();
/* 1360:     */   }
/* 1361:     */   
/* 1362:     */   public static void readStartCaches()
/* 1363:     */   {
/* 1364:1312 */     File file = new File(System.getProperty("user.home") + File.separator + "parser.data");
/* 1365:1313 */     if (!file.exists())
/* 1366:     */     {
/* 1367:1314 */       Mark.say(new Object[] {"No Start parser cache,", file, "to load" });
/* 1368:1315 */       return;
/* 1369:     */     }
/* 1370:1317 */     Mark.say(new Object[] {Boolean.valueOf(true), "Loading Start parser cache" });
/* 1371:     */     try
/* 1372:     */     {
/* 1373:1321 */       FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.home") + File.separator + "parser.data");
/* 1374:1322 */       ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
/* 1375:1323 */       Object object = objectInputStream.readObject();
/* 1376:1324 */       if (object != null)
/* 1377:     */       {
/* 1378:1325 */         setStartParserCache((HashMap)object);
/* 1379:1326 */         Mark.say(new Object[] {Boolean.valueOf(true), "Number of parser items read: " + getStartParserCache().size() });
/* 1380:     */       }
/* 1381:1328 */       objectInputStream.close();
/* 1382:1329 */       fileInputStream.close();
/* 1383:     */     }
/* 1384:     */     catch (Exception e)
/* 1385:     */     {
/* 1386:1332 */       Mark.say(new Object[] {"Start parser cache could not be read, sticking with existing cache" });
/* 1387:     */     }
/* 1388:1335 */     file = new File(System.getProperty("user.home") + File.separator + "generator.data");
/* 1389:1336 */     if (!file.exists())
/* 1390:     */     {
/* 1391:1337 */       Mark.say(new Object[] {"No Start generator cache,", file, "to load" });
/* 1392:1338 */       return;
/* 1393:     */     }
/* 1394:1340 */     Mark.say(new Object[] {Boolean.valueOf(true), "Loading Start generator cache" });
/* 1395:     */     try
/* 1396:     */     {
/* 1397:1342 */       FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.home") + File.separator + "generator.data");
/* 1398:1343 */       ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
/* 1399:1344 */       Object object = objectInputStream.readObject();
/* 1400:1345 */       if (object != null)
/* 1401:     */       {
/* 1402:1346 */         Generator.setStartGeneratorCache((HashMap)object);
/* 1403:1347 */         Mark.say(new Object[] {Boolean.valueOf(true), "Number of generator items read: " + Generator.getStartGeneratorCache().size() });
/* 1404:     */       }
/* 1405:1349 */       objectInputStream.close();
/* 1406:1350 */       fileInputStream.close();
/* 1407:     */     }
/* 1408:     */     catch (Exception e)
/* 1409:     */     {
/* 1410:1353 */       Mark.say(new Object[] {"Start generator cache could not be read, sticking with existing cache" });
/* 1411:     */     }
/* 1412:     */   }
/* 1413:     */   
/* 1414:     */   public static void writeStartCaches()
/* 1415:     */   {
/* 1416:1358 */     Mark.say(
/* 1417:     */     
/* 1418:     */ 
/* 1419:     */ 
/* 1420:     */ 
/* 1421:     */ 
/* 1422:     */ 
/* 1423:     */ 
/* 1424:     */ 
/* 1425:     */ 
/* 1426:     */ 
/* 1427:     */ 
/* 1428:     */ 
/* 1429:     */ 
/* 1430:     */ 
/* 1431:     */ 
/* 1432:     */ 
/* 1433:     */ 
/* 1434:     */ 
/* 1435:1377 */       new Object[] { Boolean.valueOf(true), "Writing Start cache" });
/* 1436:     */     try
/* 1437:     */     {
/* 1438:1361 */       FileOutputStream fileOutputStream = new FileOutputStream(System.getProperty("user.home") + File.separator + "parser.data");
/* 1439:1362 */       ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
/* 1440:1363 */       objectOutputStream.writeObject(getStartParserCache());
/* 1441:1364 */       objectOutputStream.close();
/* 1442:     */       
/* 1443:1366 */       fileOutputStream = new FileOutputStream(System.getProperty("user.home") + File.separator + "generator.data");
/* 1444:1367 */       objectOutputStream = new ObjectOutputStream(fileOutputStream);
/* 1445:1368 */       objectOutputStream.writeObject(Generator.getStartGeneratorCache());
/* 1446:1369 */       objectOutputStream.close();
/* 1447:     */     }
/* 1448:     */     catch (Exception e)
/* 1449:     */     {
/* 1450:1373 */       e.printStackTrace();
/* 1451:     */     }
/* 1452:1375 */     Mark.say(new Object[] {"Number of Start parser items written:", Integer.valueOf(getStartParserCache().size()) });
/* 1453:1376 */     Mark.say(new Object[] {"Number of Start generator items written:", Integer.valueOf(Generator.getStartGeneratorCache().size()) });
/* 1454:     */   }
/* 1455:     */   
/* 1456:     */   public String getProcessedSentence()
/* 1457:     */   {
/* 1458:1380 */     if (this.processedSentence == null) {
/* 1459:1381 */       this.processedSentence = "";
/* 1460:     */     }
/* 1461:1383 */     return this.processedSentence;
/* 1462:     */   }
/* 1463:     */   
/* 1464:     */   public static void main(String[] ignore)
/* 1465:     */     throws Exception
/* 1466:     */   {
/* 1467:1387 */     Mark.say(
/* 1468:     */     
/* 1469:     */ 
/* 1470:1390 */       new Object[] { "Starting" });Mark.say(new Object[] { "The START parse:", getStart().parse("A bird flew flew.") });Mark.say(new Object[] { "The Genesis Innerese:", Translator.getTranslator().translate("A bird flew flew") });
/* 1471:     */   }
/* 1472:     */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     start.Start
 * JD-Core Version:    0.7.0.1
 */