/*   1:    */ package matthewFay.StoryAlignment;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import connections.AbstractWiredBox;
/*   7:    */ import connections.Connections;
/*   8:    */ import connections.Connections.NetWireException;
/*   9:    */ import connections.Ports;
/*  10:    */ import java.io.BufferedReader;
/*  11:    */ import java.io.FileInputStream;
/*  12:    */ import java.io.FileOutputStream;
/*  13:    */ import java.io.IOException;
/*  14:    */ import java.io.InputStreamReader;
/*  15:    */ import java.io.ObjectInputStream;
/*  16:    */ import java.io.ObjectOutputStream;
/*  17:    */ import java.io.PrintStream;
/*  18:    */ import java.net.MalformedURLException;
/*  19:    */ import java.net.URL;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.HashMap;
/*  22:    */ import java.util.Set;
/*  23:    */ import javax.swing.JCheckBox;
/*  24:    */ import javax.swing.JRadioButton;
/*  25:    */ import matthewFay.Utilities.StopWatch;
/*  26:    */ import matthewFay.VideoAnalysis.EventPredictor;
/*  27:    */ import matthewFay.viewers.AlignmentViewer;
/*  28:    */ import minilisp.LList;
/*  29:    */ import models.MentalModel;
/*  30:    */ import parameters.Radio;
/*  31:    */ import storyProcessor.StoryProcessor;
/*  32:    */ import utils.Mark;
/*  33:    */ import utils.PairOfEntities;
/*  34:    */ 
/*  35:    */ public class AlignmentProcessor
/*  36:    */   extends AbstractWiredBox
/*  37:    */ {
/*  38:    */   public static final String REFLECTION_ALIGNMENT_OUTPUT = "REFLECTION ALIGNMENT OUTPUT";
/*  39:    */   public static final String GRAPH_PORT_OUTPUT = "Graph Port Output";
/*  40:    */   public static final String RESET_PORT = "reset port";
/*  41:    */   public static final String STAGE_DIRECTION_PORT = "stage direction";
/*  42:    */   public static final String COMPLETE_STORY_ANALYSIS_PORT = "COMPLETE STORY ANALYSIS PORT";
/*  43:    */   public static final String COMPLETE_STORY_ANALYSIS_PORT2 = "COMPLETE STORY ANALYSIS PORT2";
/*  44:    */   public static final String INSERT_PORT = "INSERT ELEMENT PORT";
/*  45: 37 */   private static String ALIGNMENT_PROCESSOR = "AlignmentProcessor";
/*  46:    */   
/*  47:    */   public AlignmentProcessor()
/*  48:    */   {
/*  49: 39 */     setName(ALIGNMENT_PROCESSOR);
/*  50:    */     
/*  51: 41 */     Connections.getPorts(this).addSignalProcessor("COMPLETE STORY ANALYSIS PORT", "processCompleteAnalysis");
/*  52: 42 */     Connections.getPorts(this).addSignalProcessor("COMPLETE STORY ANALYSIS PORT2", "processCompleteAnalysis2");
/*  53:    */     
/*  54: 44 */     Connections.getPorts(this).addSignalProcessor("stage direction", "processDirection");
/*  55: 45 */     Connections.getPorts(this).addSignalProcessor("reset port", "reset");
/*  56:    */     
/*  57: 47 */     instance = this;
/*  58:    */   }
/*  59:    */   
/*  60: 50 */   private static AlignmentProcessor instance = null;
/*  61:    */   
/*  62:    */   public static AlignmentProcessor getAlignmentProcessor()
/*  63:    */   {
/*  64: 52 */     return instance;
/*  65:    */   }
/*  66:    */   
/*  67: 55 */   private Aligner aligner = new Aligner();
/*  68: 57 */   public boolean useReflections = true;
/*  69: 58 */   public boolean gapFilling = false;
/*  70:    */   
/*  71:    */   public void setFlags()
/*  72:    */   {
/*  73: 60 */     MatchTree.debugPrintOutputDuringGeneration = AlignmentViewer.debugTreeGeneration.isSelected();
/*  74: 61 */     this.useReflections = AlignmentViewer.useReflections.isSelected();
/*  75: 62 */     this.gapFilling = AlignmentViewer.gapFilling.isSelected();
/*  76: 63 */     if (AlignmentViewer.simpleScorer.isSelected()) {
/*  77: 64 */       NWSequenceAlignmentScorer.useSimpleScoring();
/*  78:    */     } else {
/*  79: 66 */       NWSequenceAlignmentScorer.usePenalizedScoring();
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83: 70 */   HashMap<Integer, Sequence> stories = new HashMap();
/*  84: 71 */   HashMap<Integer, Sequence> reflections = new HashMap();
/*  85: 73 */   ArrayList<Sequence> patterns = new ArrayList();
/*  86:    */   private HashMap<Integer, Sequence> last_stories;
/*  87:    */   private HashMap<Integer, Sequence> last_reflections;
/*  88:    */   private LList<PairOfEntities> last_bindings;
/*  89:    */   
/*  90:    */   public void saveLibrary()
/*  91:    */   {
/*  92:    */     try
/*  93:    */     {
/*  94: 76 */       FileOutputStream fout = new FileOutputStream("patternlibrary.dat");
/*  95: 77 */       ObjectOutputStream oos = new ObjectOutputStream(fout);
/*  96: 78 */       oos.writeObject(this.patterns);
/*  97: 79 */       oos.close();
/*  98:    */     }
/*  99:    */     catch (Exception e)
/* 100:    */     {
/* 101: 81 */       e.printStackTrace();
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void loadLibrary()
/* 106:    */   {
/* 107:    */     try
/* 108:    */     {
/* 109: 88 */       FileInputStream fin = new FileInputStream("patternlibrary.dat");
/* 110: 89 */       ObjectInputStream ois = new ObjectInputStream(fin);
/* 111: 90 */       this.patterns = ((ArrayList)ois.readObject());
/* 112: 91 */       ois.close();
/* 113:    */     }
/* 114:    */     catch (Exception e)
/* 115:    */     {
/* 116: 93 */       e.printStackTrace();
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void processDirection(Object o)
/* 121:    */   {
/* 122: 97 */     if ("reset".equals(o)) {
/* 123: 98 */       reset(null);
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void reset(Object o)
/* 128:    */   {
/* 129:107 */     this.stories.clear();
/* 130:108 */     this.reflections.clear();
/* 131:109 */     this.patterns.clear();
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void processCompleteAnalysis(Object o)
/* 135:    */   {
/* 136:114 */     BetterSignal ca = BetterSignal.isSignal(o);
/* 137:115 */     if (ca == null) {
/* 138:116 */       return;
/* 139:    */     }
/* 140:117 */     doProcessing(0, ca);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void processCompleteAnalysis2(Object o)
/* 144:    */   {
/* 145:121 */     BetterSignal ca = BetterSignal.isSignal(o);
/* 146:122 */     if (ca == null) {
/* 147:123 */       return;
/* 148:    */     }
/* 149:124 */     doProcessing(1, ca);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void doProcessing(int id, BetterSignal bs)
/* 153:    */   {
/* 154:129 */     if (!Radio.alignmentButton.isSelected()) {
/* 155:130 */       return;
/* 156:    */     }
/* 157:132 */     Sequence story = (Sequence)bs.get(0, Sequence.class);
/* 158:133 */     Sequence conceptPatterns = (Sequence)bs.get(3, Sequence.class);
/* 159:    */     
/* 160:135 */     Mark.say(new Object[] {story.asString() });
/* 161:137 */     if (storyRememberer(story.getElement(story.getNumberOfChildren() - 1)))
/* 162:    */     {
/* 163:139 */       story.removeElement(story.getElement(story.getNumberOfChildren() - 1));
/* 164:140 */       this.patterns.add(story);
/* 165:    */     }
/* 166:143 */     if (storyPatternAligner(story.getElement(story.getNumberOfChildren() - 1)))
/* 167:    */     {
/* 168:145 */       story.removeElement(story.getElement(story.getNumberOfChildren() - 1));
/* 169:146 */       alignToPatterns(story);
/* 170:    */     }
/* 171:147 */     else if (storyTraitEventPrediction(story.getElement(story.getNumberOfChildren() - 1)))
/* 172:    */     {
/* 173:149 */       story.removeElement(story.getElement(story.getNumberOfChildren() - 1));
/* 174:    */       
/* 175:    */ 
/* 176:    */ 
/* 177:153 */       ArrayList<Sequence> traitStories = new ArrayList();
/* 178:154 */       for (Entity t : story.getElements()) {
/* 179:156 */         if (storyPersonalityTrait(t))
/* 180:    */         {
/* 181:158 */           String trait = getPersonalityTrait(t);
/* 182:159 */           Sequence traitStory = MentalModel.loadGlobalMentalModel(trait).getStoryProcessor().getStory();
/* 183:160 */           traitStories.add(traitStory);
/* 184:    */         }
/* 185:    */       }
/* 186:163 */       traitStories.addAll(this.patterns);
/* 187:    */       
/* 188:165 */       EventPredictor ep = new EventPredictor();
/* 189:166 */       Entity prediction = ep.predictMostLikelyNextEvent(story, traitStories);
/* 190:167 */       Mark.say(new Object[] {"Prediction: ", prediction.asString() });
/* 191:168 */       Connections.getPorts(this).transmit("INSERT ELEMENT PORT", prediction);
/* 192:    */     }
/* 193:    */     else
/* 194:    */     {
/* 195:172 */       this.stories.put(Integer.valueOf(id), story);
/* 196:173 */       this.reflections.put(Integer.valueOf(id), conceptPatterns);
/* 197:174 */       BetterSignal alignmentSignal = alignStories(this.stories, this.reflections);
/* 198:175 */       Connections.getPorts(this).transmit(alignmentSignal);
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:    */   public boolean storyPersonalityTrait(Entity t)
/* 203:    */   {
/* 204:181 */     if (t.relationP("personality_trait")) {
/* 205:182 */       return true;
/* 206:    */     }
/* 207:183 */     return false;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public String getPersonalityTrait(Entity t)
/* 211:    */   {
/* 212:187 */     return t.getObject().getType();
/* 213:    */   }
/* 214:    */   
/* 215:    */   public boolean storyRememberer(Entity t)
/* 216:    */   {
/* 217:191 */     if ((t.relationP("remember")) && (t.getSubject().entityP("you"))) {
/* 218:192 */       return true;
/* 219:    */     }
/* 220:194 */     return false;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public boolean storyPatternAligner(Entity t)
/* 224:    */   {
/* 225:198 */     if ((t.relationP("align")) && (t.getSubject().entityP("you"))) {
/* 226:199 */       return true;
/* 227:    */     }
/* 228:201 */     return false;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public boolean storyTraitEventPrediction(Entity t)
/* 232:    */   {
/* 233:205 */     if ((t.relationP("predict")) && (t.getSubject().entityP("you"))) {
/* 234:207 */       return true;
/* 235:    */     }
/* 236:209 */     return false;
/* 237:    */   }
/* 238:    */   
/* 239:    */   private void alignToPatterns(Sequence datum)
/* 240:    */   {
/* 241:213 */     SortableAlignmentList patternMatches = new SortableAlignmentList();
/* 242:214 */     for (Sequence pattern : this.patterns)
/* 243:    */     {
/* 244:215 */       Mark.say(new Object[] {"Aligning: ", datum.asString() });
/* 245:216 */       Mark.say(new Object[] {"To: ", pattern.asString() });
/* 246:    */       
/* 247:218 */       patternMatches.add((Alignment)this.aligner.align(pattern, datum).get(0));
/* 248:    */     }
/* 249:220 */     patternMatches.sort();
/* 250:    */     
/* 251:222 */     BetterSignal patternMatchSignal = new BetterSignal();
/* 252:223 */     for (int i = 0; i < patternMatches.size(); i++) {
/* 253:224 */       patternMatchSignal.add(patternMatches.get(i));
/* 254:    */     }
/* 255:226 */     Connections.getPorts(this).transmit(patternMatchSignal);
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void addPatterns(ArrayList<Sequence> patternsToAdd)
/* 259:    */   {
/* 260:230 */     if (patternsToAdd != null) {
/* 261:231 */       this.patterns.addAll(patternsToAdd);
/* 262:    */     }
/* 263:    */   }
/* 264:    */   
/* 265:    */   public Alignment<Entity, Entity> alignStories(Sequence story1, Sequence story2, LList<PairOfEntities> bootstrappedBindings)
/* 266:    */   {
/* 267:237 */     HashMap<Integer, Sequence> stories = new HashMap();
/* 268:238 */     stories.put(Integer.valueOf(0), story1);
/* 269:239 */     stories.put(Integer.valueOf(1), story2);
/* 270:241 */     if (bootstrappedBindings == null) {
/* 271:242 */       bootstrappedBindings = new LList();
/* 272:    */     }
/* 273:244 */     BetterSignal bs = alignStories(stories, bootstrappedBindings);
/* 274:245 */     return (Alignment)bs.get(0, Alignment.class);
/* 275:    */   }
/* 276:    */   
/* 277:    */   private BetterSignal alignStories(HashMap<Integer, Sequence> stories, LList<PairOfEntities> bootstrappedBindings)
/* 278:    */   {
/* 279:250 */     BetterSignal bs = null;
/* 280:251 */     if ((stories != null) && (stories.keySet().size() > 1)) {
/* 281:253 */       bs = alignStories(stories, new HashMap(), bootstrappedBindings);
/* 282:    */     }
/* 283:255 */     return bs;
/* 284:    */   }
/* 285:    */   
/* 286:    */   private BetterSignal alignStories(HashMap<Integer, Sequence> stories, HashMap<Integer, Sequence> reflections)
/* 287:    */   {
/* 288:260 */     BetterSignal bs = null;
/* 289:261 */     if ((stories != null) && (stories.size() > 1)) {
/* 290:263 */       if ((reflections != null) && (reflections.size() > 1)) {
/* 291:264 */         bs = alignStories(stories, reflections, new LList());
/* 292:    */       }
/* 293:    */     }
/* 294:267 */     return bs;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public BetterSignal rerunLastAlignment()
/* 298:    */   {
/* 299:274 */     return alignStories(this.last_stories, this.last_reflections, this.last_bindings);
/* 300:    */   }
/* 301:    */   
/* 302:    */   private BetterSignal alignStories(HashMap<Integer, Sequence> stories, HashMap<Integer, Sequence> reflections, LList<PairOfEntities> bindings)
/* 303:    */   {
/* 304:279 */     this.last_stories = stories;
/* 305:280 */     this.last_reflections = reflections;
/* 306:281 */     this.last_bindings = bindings;
/* 307:    */     
/* 308:283 */     setFlags();
/* 309:286 */     if (stories.size() < 2)
/* 310:    */     {
/* 311:287 */       Mark.say(new Object[] {"Nothing to align" });
/* 312:288 */       return new BetterSignal();
/* 313:    */     }
/* 314:291 */     StopWatch reflectionStopWatch = new StopWatch();
/* 315:292 */     Mark.say(new Object[] {"Start Reflection Watch" });
/* 316:293 */     reflectionStopWatch.start();
/* 317:295 */     if ((this.useReflections) && (reflections.containsKey(Integer.valueOf(0))) && (reflections.containsKey(Integer.valueOf(1))))
/* 318:    */     {
/* 319:296 */       Mark.say(new Object[] {"Boot Strapping Reflective Knowledge!" });
/* 320:297 */       LList<PairOfEntities> newBindings = this.aligner.getPlotUnitBindings((Sequence)reflections.get(Integer.valueOf(0)), (Sequence)reflections.get(Integer.valueOf(1)), bindings);
/* 321:298 */       bindings = bindings.append(newBindings);
/* 322:299 */       Mark.say(new Object[] {"New bindings from reflection:" });
/* 323:300 */       Mark.say(new Object[] {bindings });
/* 324:301 */       Connections.getPorts(this).transmit("REFLECTION ALIGNMENT OUTPUT", new BetterSignal(new Object[] { this.aligner.getLastReflectionAlignment() }));
/* 325:    */     }
/* 326:304 */     reflectionStopWatch.stop();
/* 327:305 */     Mark.say(new Object[] {"Reflection Alignment Time: " + reflectionStopWatch.getElapsedTime() });
/* 328:    */     
/* 329:307 */     StopWatch stopWatch = new StopWatch();
/* 330:308 */     Mark.say(new Object[] {"Start Watch" });
/* 331:309 */     stopWatch.start();
/* 332:    */     
/* 333:311 */     SortableAlignmentList alignments = this.aligner.align((Sequence)stories.get(Integer.valueOf(0)), (Sequence)stories.get(Integer.valueOf(1)), bindings);
/* 334:312 */     Connections.getPorts(this).transmit("Graph Port Output", new BetterSignal(new Object[] { this.aligner.getLastMatchTree().graph }));
/* 335:    */     
/* 336:314 */     Mark.say(new Object[] {"Alignment Time (before gap filling): " + stopWatch.getElapsedTime() });
/* 337:    */     
/* 338:316 */     BetterSignal alignmentSignal = new BetterSignal();
/* 339:317 */     for (Alignment<Entity, Entity> alignment : alignments)
/* 340:    */     {
/* 341:319 */       if (this.gapFilling) {
/* 342:320 */         alignment = fillGaps(alignment);
/* 343:    */       }
/* 344:322 */       alignmentSignal.add(alignment);
/* 345:    */     }
/* 346:325 */     stopWatch.stop();
/* 347:326 */     Mark.say(new Object[] {"Alignment Time: " + stopWatch.getElapsedTime() });
/* 348:    */     
/* 349:328 */     return alignmentSignal;
/* 350:    */   }
/* 351:    */   
/* 352:    */   private Alignment<Entity, Entity> fillGaps(Alignment<Entity, Entity> alignment)
/* 353:    */   {
/* 354:333 */     SequenceAlignment sa = (SequenceAlignment)alignment;
/* 355:334 */     sa.fillGaps();
/* 356:335 */     return alignment;
/* 357:    */   }
/* 358:    */   
/* 359:    */   private Entity findAndReplace(Entity element, LList<PairOfEntities> bindings)
/* 360:    */   {
/* 361:339 */     if (element.entityP())
/* 362:    */     {
/* 363:341 */       for (PairOfEntities pair : bindings) {
/* 364:342 */         if (pair.getPattern().isDeepEqual(element)) {
/* 365:343 */           return pair.getDatum();
/* 366:    */         }
/* 367:    */       }
/* 368:347 */       for (PairOfEntities pair : bindings) {
/* 369:348 */         if (pair.getDatum().isDeepEqual(element)) {
/* 370:349 */           return pair.getPattern();
/* 371:    */         }
/* 372:    */       }
/* 373:352 */       return new Entity();
/* 374:    */     }
/* 375:354 */     if (element.relationP())
/* 376:    */     {
/* 377:355 */       element.setSubject(findAndReplace(element.getSubject(), bindings));
/* 378:356 */       element.setObject(findAndReplace(element.getObject(), bindings));
/* 379:357 */       return element;
/* 380:    */     }
/* 381:359 */     if (element.functionP())
/* 382:    */     {
/* 383:360 */       element.setSubject(findAndReplace(element.getSubject(), bindings));
/* 384:361 */       return element;
/* 385:    */     }
/* 386:363 */     if (element.sequenceP())
/* 387:    */     {
/* 388:364 */       int i = 0;
/* 389:365 */       Sequence s = (Sequence)element;
/* 390:366 */       while (i < element.getNumberOfChildren())
/* 391:    */       {
/* 392:367 */         Entity child = element.getElement(i);
/* 393:368 */         child = findAndReplace(child, bindings);
/* 394:369 */         s.setElementAt(child, i);
/* 395:370 */         i++;
/* 396:    */       }
/* 397:372 */       return element;
/* 398:    */     }
/* 399:375 */     return element;
/* 400:    */   }
/* 401:    */   
/* 402:378 */   public static String wireServer = "http://glue.csail.mit.edu/WireServer";
/* 403:    */   
/* 404:    */   public static void main(String[] args)
/* 405:    */   {
/* 406:381 */     InputStreamReader converter = new InputStreamReader(System.in);
/* 407:382 */     BufferedReader in = new BufferedReader(converter);
/* 408:383 */     URL serverURL = null;
/* 409:    */     try
/* 410:    */     {
/* 411:385 */       serverURL = new URL(wireServer);
/* 412:    */     }
/* 413:    */     catch (MalformedURLException e)
/* 414:    */     {
/* 415:387 */       e.printStackTrace();
/* 416:388 */       System.exit(1);
/* 417:    */     }
/* 418:    */     try
/* 419:    */     {
/* 420:392 */       String input = "";
/* 421:393 */       ALIGNMENT_PROCESSOR = "AlignmentProcessorService";
/* 422:394 */       Connections.useWireServer(serverURL);
/* 423:395 */       AlignmentProcessor ap = new AlignmentProcessor();
/* 424:396 */       Connections.publish(ap, "AlignmentProcessorService");
/* 425:    */       
/* 426:398 */       System.out.println("AlignmentProcessorService started, input commands");
/* 427:    */       int j;
/* 428:    */       int i;
/* 429:400 */       for (; !input.toLowerCase().equals("quit"); i < j)
/* 430:    */       {
/* 431:401 */         input = in.readLine().trim().intern();
/* 432:402 */         BetterSignal b = new BetterSignal();
/* 433:403 */         String[] sigargs = input.split(" ");
/* 434:    */         String[] arrayOfString1;
/* 435:404 */         j = (arrayOfString1 = sigargs).length;i = 0; continue;String s = arrayOfString1[i];
/* 436:405 */         b.add(s);i++;
/* 437:    */       }
/* 438:    */     }
/* 439:    */     catch (Connections.NetWireException e)
/* 440:    */     {
/* 441:410 */       e.printStackTrace();
/* 442:    */     }
/* 443:    */     catch (IOException e)
/* 444:    */     {
/* 445:412 */       e.printStackTrace();
/* 446:    */     }
/* 447:    */   }
/* 448:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.StoryAlignment.AlignmentProcessor
 * JD-Core Version:    0.7.0.1
 */