/*   1:    */ package carynKrakauer;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Sequence;
/*   5:    */ import carynKrakauer.commonSubstrings.SubstringTree;
/*   6:    */ import carynKrakauer.generatedPatterns.ConceptPattern;
/*   7:    */ import carynKrakauer.generatedPatterns.ConceptPatternFinder;
/*   8:    */ import carynKrakauer.generatedPatterns.ConceptPatternMatchWrapper;
/*   9:    */ import carynKrakauer.generatedPatterns.PlotEvent;
/*  10:    */ import java.awt.Font;
/*  11:    */ import java.awt.font.FontRenderContext;
/*  12:    */ import java.awt.geom.Rectangle2D;
/*  13:    */ import java.io.PrintStream;
/*  14:    */ import java.util.AbstractMap.SimpleEntry;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.HashMap;
/*  17:    */ import java.util.HashSet;
/*  18:    */ import java.util.Iterator;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map.Entry;
/*  21:    */ import java.util.Set;
/*  22:    */ import java.util.TreeMap;
/*  23:    */ import java.util.Vector;
/*  24:    */ import matthewFay.StoryAlignment.Aligner;
/*  25:    */ import matthewFay.StoryAlignment.Alignment;
/*  26:    */ import matthewFay.StoryAlignment.SortableAlignmentList;
/*  27:    */ import storyProcessor.ReflectionAnalysis;
/*  28:    */ import storyProcessor.ReflectionDescription;
/*  29:    */ import utils.Mark;
/*  30:    */ 
/*  31:    */ public class ReflectionLevelMemory
/*  32:    */ {
/*  33:    */   private SimilarityProcessor parent;
/*  34:    */   private int numStoriesRead;
/*  35:    */   private ArrayList<String> storyNames;
/*  36:    */   private int totalConceptPatterns;
/*  37:    */   private HashMap<String, Integer> conceptPatternCounts;
/*  38:    */   private HashMap<String, Integer> conceptPatternStoryCounts;
/*  39:    */   private HashMap<String, ArrayList<ReflectionDescription>> conceptPatternLists;
/*  40:    */   private HashMap<String, ArrayList<String>> conceptPatternListsStrings;
/*  41:    */   private HashMap<String, HashMap<String, Integer>> conceptPatternHashes;
/*  42:    */   private HashMap<String, HashMap<String, VectorMatchWrapper>> conceptPatternComparisons;
/*  43:    */   private SubstringTree substringTree;
/*  44:    */   private HashMap<String, Sequence> storySequences;
/*  45:    */   private HashMap<String, ArrayList<PlotEvent>> plotEvents;
/*  46: 51 */   private int[] testSizes = { 2, 3 };
/*  47:    */   private HashMap<Integer, HashMap<String, HashMap<ConceptPattern, Integer>>> genConceptPatternHashes;
/*  48:    */   private HashMap<Integer, HashMap<String, ArrayList<ConceptPattern>>> genConceptPatternUnits;
/*  49:    */   private HashMap<Integer, ArrayList<Map.Entry<ConceptPattern, Integer>>> genConceptPatternCounts;
/*  50:    */   private HashMap<Integer, HashMap<String, HashMap<String, ConceptPatternMatchWrapper>>> genConceptPatternComparisons;
/*  51:    */   private HashMap<Integer, HashMap<ConceptPattern, Integer>> generatedConceptPatternStoryCounts;
/*  52:    */   
/*  53:    */   public ReflectionLevelMemory(SimilarityProcessor parent)
/*  54:    */   {
/*  55: 59 */     this.parent = parent;
/*  56:    */     
/*  57: 61 */     this.numStoriesRead = 0;
/*  58: 62 */     this.storyNames = new ArrayList();
/*  59:    */     
/*  60: 64 */     this.totalConceptPatterns = 0;
/*  61: 65 */     this.conceptPatternCounts = new HashMap();
/*  62: 66 */     this.conceptPatternStoryCounts = new HashMap();
/*  63:    */     
/*  64: 68 */     this.conceptPatternLists = new HashMap();
/*  65: 69 */     this.conceptPatternListsStrings = new HashMap();
/*  66: 70 */     this.conceptPatternHashes = new HashMap();
/*  67: 71 */     this.conceptPatternComparisons = new HashMap();
/*  68:    */     
/*  69: 73 */     this.substringTree = new SubstringTree();
/*  70:    */     
/*  71:    */ 
/*  72: 76 */     this.storySequences = new HashMap();
/*  73: 77 */     this.genConceptPatternHashes = new HashMap();
/*  74: 78 */     this.plotEvents = new HashMap();
/*  75: 79 */     this.genConceptPatternUnits = new HashMap();
/*  76: 80 */     this.genConceptPatternCounts = new HashMap();
/*  77: 81 */     this.genConceptPatternComparisons = new HashMap();
/*  78: 82 */     this.generatedConceptPatternStoryCounts = new HashMap();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void readInStory(ReflectionAnalysis analysis)
/*  82:    */   {
/*  83: 86 */     String title = analysis.getStoryName();
/*  84: 87 */     if (this.conceptPatternLists.containsKey(title)) {
/*  85: 88 */       return;
/*  86:    */     }
/*  87: 90 */     this.numStoriesRead += 1;
/*  88: 91 */     this.storyNames.add(title);
/*  89:    */     
/*  90: 93 */     ArrayList<ReflectionDescription> ref_desc = analysis.getReflectionDescriptions();
/*  91:    */     
/*  92: 95 */     TreeMap<Integer, ArrayList<ReflectionDescription>> sortedByID = new TreeMap();
/*  93:    */     
/*  94: 97 */     HashMap<String, Integer> features = new HashMap();
/*  95:    */     
/*  96: 99 */     HashSet<String> rdsAdded = new HashSet();
/*  97:100 */     for (ReflectionDescription rd : ref_desc)
/*  98:    */     {
/*  99:102 */       if (!features.containsKey(rd.getName())) {
/* 100:103 */         features.put(rd.getName(), Integer.valueOf(1));
/* 101:    */       } else {
/* 102:105 */         features.put(rd.getName(), Integer.valueOf(((Integer)features.get(rd.getName())).intValue() + 1));
/* 103:    */       }
/* 104:108 */       Sequence elements = rd.getStoryElementsInvolved();
/* 105:109 */       value = 0;
/* 106:110 */       if (elements.getElements().size() != 0) {
/* 107:111 */         value = elements.getElement(elements.getElements().size() - 1).getID();
/* 108:113 */       } else if (sortedByID.size() != 0) {
/* 109:114 */         value = Math.min(0, ((Integer)sortedByID.firstKey()).intValue() - 1);
/* 110:    */       }
/* 111:117 */       if (!sortedByID.containsKey(Integer.valueOf(value))) {
/* 112:118 */         sortedByID.put(Integer.valueOf(value), new ArrayList());
/* 113:    */       }
/* 114:121 */       ((ArrayList)sortedByID.get(Integer.valueOf(value))).add(rd);
/* 115:124 */       if (!this.conceptPatternCounts.containsKey(rd.getName())) {
/* 116:125 */         this.conceptPatternCounts.put(rd.getName(), Integer.valueOf(0));
/* 117:    */       }
/* 118:126 */       this.conceptPatternCounts.put(rd.getName(), Integer.valueOf(((Integer)this.conceptPatternCounts.get(rd.getName())).intValue() + 1));
/* 119:128 */       if (!this.conceptPatternStoryCounts.containsKey(rd.getName())) {
/* 120:129 */         this.conceptPatternStoryCounts.put(rd.getName(), Integer.valueOf(0));
/* 121:    */       }
/* 122:130 */       if (!rdsAdded.contains(rd.getName()))
/* 123:    */       {
/* 124:131 */         this.conceptPatternStoryCounts.put(rd.getName(), Integer.valueOf(((Integer)this.conceptPatternStoryCounts.get(rd.getName())).intValue() + 1));
/* 125:132 */         rdsAdded.add(rd.getName());
/* 126:    */       }
/* 127:135 */       this.totalConceptPatterns += 1;
/* 128:    */     }
/* 129:139 */     ArrayList<ReflectionDescription> ref_in_order = new ArrayList();
/* 130:140 */     Object ref_names_in_order = new ArrayList();
/* 131:    */     Object localObject1;
/* 132:    */     ReflectionDescription rd;
/* 133:141 */     for (int value = sortedByID.entrySet().iterator(); value.hasNext(); ((Iterator)localObject1).hasNext())
/* 134:    */     {
/* 135:141 */       Map.Entry<Integer, ArrayList<ReflectionDescription>> entry = (Map.Entry)value.next();
/* 136:142 */       localObject1 = ((ArrayList)entry.getValue()).iterator(); continue;rd = (ReflectionDescription)((Iterator)localObject1).next();
/* 137:143 */       ref_in_order.add(rd);
/* 138:144 */       ((ArrayList)ref_names_in_order).add(rd.getName());
/* 139:    */     }
/* 140:147 */     this.conceptPatternLists.put(title, ref_in_order);
/* 141:148 */     this.conceptPatternListsStrings.put(title, ref_names_in_order);
/* 142:    */     
/* 143:150 */     this.conceptPatternHashes.put(title, features);
/* 144:    */     
/* 145:    */ 
/* 146:153 */     this.substringTree.addStoryToTree(title, (ArrayList)ref_names_in_order);
/* 147:    */     
/* 148:155 */     createConceptPatternComparisons(title);
/* 149:157 */     for (int size : this.testSizes) {
/* 150:158 */       createGenConceptPatternComparisons(title, size);
/* 151:    */     }
/* 152:160 */     this.parent.updateComparisons();
/* 153:    */   }
/* 154:    */   
/* 155:    */   public int getStoryCount()
/* 156:    */   {
/* 157:165 */     return this.numStoriesRead;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public ArrayList<String> getStoryNames()
/* 161:    */   {
/* 162:169 */     return this.storyNames;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public boolean hasReadStory(String title)
/* 166:    */   {
/* 167:173 */     return this.conceptPatternLists.containsKey(title);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public HashMap<String, ArrayList<String>> getStoriesString()
/* 171:    */   {
/* 172:177 */     return this.conceptPatternListsStrings;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public HashMap<String, ArrayList<ReflectionDescription>> getStories()
/* 176:    */   {
/* 177:181 */     return this.conceptPatternLists;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public int getTotalConceptCount()
/* 181:    */   {
/* 182:190 */     return this.totalConceptPatterns;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public int getConceptCount(String feature)
/* 186:    */   {
/* 187:194 */     return ((Integer)this.conceptPatternCounts.get(feature)).intValue();
/* 188:    */   }
/* 189:    */   
/* 190:    */   public ArrayList<ReflectionDescription> getStoryReflectionDescriptions(String story)
/* 191:    */   {
/* 192:198 */     return (ArrayList)this.conceptPatternLists.get(story);
/* 193:    */   }
/* 194:    */   
/* 195:    */   public VectorMatchWrapper getDefinedConceptPatternComparison(String story1, String story2)
/* 196:    */   {
/* 197:202 */     if ((this.conceptPatternComparisons.containsKey(story1)) && (((HashMap)this.conceptPatternComparisons.get(story1)).containsKey(story2))) {
/* 198:203 */       return (VectorMatchWrapper)((HashMap)this.conceptPatternComparisons.get(story1)).get(story2);
/* 199:    */     }
/* 200:205 */     return null;
/* 201:    */   }
/* 202:    */   
/* 203:    */   private void createConceptPatternComparisons(String title)
/* 204:    */   {
/* 205:209 */     if (!this.conceptPatternComparisons.containsKey(title)) {
/* 206:210 */       this.conceptPatternComparisons.put(title, new HashMap());
/* 207:    */     }
/* 208:213 */     HashMap<String, VectorMatchWrapper> comparisons = (HashMap)this.conceptPatternComparisons.get(title);
/* 209:    */     
/* 210:215 */     List<String> storyNames = getStoryNames();
/* 211:216 */     for (String story : storyNames) {
/* 212:217 */       if (!comparisons.containsKey(story))
/* 213:    */       {
/* 214:218 */         VectorMatchWrapper match = VectorMatcher.match_story_vectors(title, story, this);
/* 215:219 */         comparisons.put(story, match);
/* 216:220 */         ((HashMap)this.conceptPatternComparisons.get(story)).put(title, match);
/* 217:    */       }
/* 218:    */     }
/* 219:    */   }
/* 220:    */   
/* 221:    */   public ArrayList<String> getStoryReflectionStrings(String story)
/* 222:    */   {
/* 223:226 */     return (ArrayList)this.conceptPatternListsStrings.get(story);
/* 224:    */   }
/* 225:    */   
/* 226:    */   public HashMap<String, Integer> getStoryReflectionStringCounts(String story)
/* 227:    */   {
/* 228:230 */     return (HashMap)this.conceptPatternHashes.get(story);
/* 229:    */   }
/* 230:    */   
/* 231:    */   public HashMap<String, Integer> getConceptPatternStoryCounts()
/* 232:    */   {
/* 233:234 */     return this.conceptPatternStoryCounts;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void putStorySequence(String title, Sequence seq)
/* 237:    */   {
/* 238:242 */     this.storySequences.put(title, seq);
/* 239:243 */     this.plotEvents.put(title, PlotEvent.convertFromSequence(seq));
/* 240:245 */     for (int size : this.testSizes) {
/* 241:246 */       putPlotUnits(title, size);
/* 242:    */     }
/* 243:    */   }
/* 244:    */   
/* 245:    */   public Sequence getSequence(String story)
/* 246:    */   {
/* 247:251 */     return (Sequence)this.storySequences.get(story);
/* 248:    */   }
/* 249:    */   
/* 250:    */   private class MatchThread
/* 251:    */     extends Thread
/* 252:    */   {
/* 253:    */     private String story1;
/* 254:    */     private String story2;
/* 255:    */     private int size;
/* 256:    */     private ReflectionLevelMemory memory;
/* 257:    */     
/* 258:    */     public MatchThread(String story1, String story2, ReflectionLevelMemory memory, int size)
/* 259:    */     {
/* 260:262 */       this.story1 = story1;
/* 261:263 */       this.story2 = story2;
/* 262:264 */       this.size = size;
/* 263:265 */       this.memory = memory;
/* 264:    */     }
/* 265:    */     
/* 266:    */     public void run()
/* 267:    */     {
/* 268:270 */       Mark.say(
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
/* 285:287 */         new Object[] { "matching for stories: " + this.story1 + " - " + this.story2 });
/* 286:272 */       if (this.story1.equals(this.story2))
/* 287:    */       {
/* 288:273 */         ReflectionLevelMemory.this.addPlotUnitComparison(this.story1, this.story2, this.size, new ConceptPatternMatchWrapper(1.0D, this.memory.getPlotUnits(this.story2, this.size), this.memory.getPlotUnits(this.story2, this.size), this.memory.getPlotUnits(this.story2, this.size)));
/* 289:274 */         ReflectionLevelMemory.this.parent.updateComparisons();
/* 290:    */       }
/* 291:278 */       List<String> toMatch = new ArrayList();
/* 292:279 */       toMatch.add(this.story2);
/* 293:    */       
/* 294:281 */       ConceptPatternMatchWrapper match = (ConceptPatternMatchWrapper)VectorMatcher.matchPlotUnits(this.story1, this.memory, toMatch, this.size).get(this.story2);
/* 295:    */       
/* 296:283 */       ReflectionLevelMemory.this.addPlotUnitComparison(this.story1, this.story2, this.size, match);
/* 297:284 */       ReflectionLevelMemory.this.addPlotUnitComparison(this.story2, this.story1, this.size, match);
/* 298:285 */       Mark.say(new Object[] {"Drawing plot comparisons" });
/* 299:286 */       ReflectionLevelMemory.this.parent.updateComparisons();
/* 300:    */     }
/* 301:    */   }
/* 302:    */   
/* 303:    */   private void createGenConceptPatternComparisons(String title, int size)
/* 304:    */   {
/* 305:292 */     if (!this.genConceptPatternComparisons.containsKey(Integer.valueOf(size))) {
/* 306:293 */       this.genConceptPatternComparisons.put(Integer.valueOf(size), new HashMap());
/* 307:    */     }
/* 308:296 */     HashMap<String, HashMap<String, ConceptPatternMatchWrapper>> comparisons = (HashMap)this.genConceptPatternComparisons.get(Integer.valueOf(size));
/* 309:298 */     if (!comparisons.containsKey(title)) {
/* 310:299 */       comparisons.put(title, new HashMap());
/* 311:    */     }
/* 312:302 */     for (String story : getStoryNames()) {
/* 313:303 */       if (!((HashMap)comparisons.get(title)).containsKey(story))
/* 314:    */       {
/* 315:304 */         MatchThread thread = new MatchThread(title, story, this, size);
/* 316:305 */         System.out.println("running thread");
/* 317:306 */         thread.run();
/* 318:307 */         System.out.println("thread ran");
/* 319:    */       }
/* 320:    */     }
/* 321:    */   }
/* 322:    */   
/* 323:    */   public int getGeneratedConceptPatternStoryCounts(ConceptPattern oPattern, int size)
/* 324:    */   {
/* 325:314 */     if (!this.generatedConceptPatternStoryCounts.containsKey(Integer.valueOf(size))) {
/* 326:315 */       populateSizeNPlotUnits(size);
/* 327:    */     }
/* 328:318 */     for (ConceptPattern pattern : ((HashMap)this.generatedConceptPatternStoryCounts.get(Integer.valueOf(size))).keySet()) {
/* 329:319 */       if (pattern.canAlign(oPattern, size)) {
/* 330:320 */         return ((Integer)((HashMap)this.generatedConceptPatternStoryCounts.get(Integer.valueOf(size))).get(pattern)).intValue();
/* 331:    */       }
/* 332:    */     }
/* 333:323 */     return -1;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void putPlotUnits(String title, int size)
/* 337:    */   {
/* 338:327 */     if (!this.genConceptPatternUnits.containsKey(Integer.valueOf(size))) {
/* 339:328 */       this.genConceptPatternUnits.put(Integer.valueOf(size), new HashMap());
/* 340:    */     }
/* 341:330 */     if (!this.genConceptPatternHashes.containsKey(Integer.valueOf(size))) {
/* 342:331 */       this.genConceptPatternHashes.put(Integer.valueOf(size), new HashMap());
/* 343:    */     }
/* 344:333 */     if (!this.genConceptPatternCounts.containsKey(Integer.valueOf(size))) {
/* 345:334 */       this.genConceptPatternCounts.put(Integer.valueOf(size), new ArrayList());
/* 346:    */     }
/* 347:336 */     if (!this.generatedConceptPatternStoryCounts.containsKey(Integer.valueOf(size))) {
/* 348:337 */       this.generatedConceptPatternStoryCounts.put(Integer.valueOf(size), new HashMap());
/* 349:    */     }
/* 350:    */     HashMap<ConceptPattern, Integer> count;
/* 351:340 */     if (!((HashMap)this.genConceptPatternUnits.get(Integer.valueOf(size))).containsKey(title))
/* 352:    */     {
/* 353:341 */       ((HashMap)this.genConceptPatternUnits.get(Integer.valueOf(size))).put(title, ConceptPatternFinder.findConceptPatterns(size, title, this));
/* 354:342 */       for (ConceptPattern pattern : (ArrayList)((HashMap)this.genConceptPatternUnits.get(Integer.valueOf(size))).get(title))
/* 355:    */       {
/* 356:343 */         count = (HashMap)this.generatedConceptPatternStoryCounts.get(Integer.valueOf(size));
/* 357:344 */         boolean found = false;
/* 358:345 */         for (ConceptPattern oPattern : count.keySet()) {
/* 359:346 */           if (pattern.canAlign(oPattern, size))
/* 360:    */           {
/* 361:347 */             count.put(oPattern, Integer.valueOf(((Integer)count.get(oPattern)).intValue() + 1));
/* 362:348 */             found = true;
/* 363:349 */             break;
/* 364:    */           }
/* 365:    */         }
/* 366:352 */         if (!found) {
/* 367:353 */           count.put(pattern, Integer.valueOf(0));
/* 368:    */         }
/* 369:    */       }
/* 370:    */     }
/* 371:358 */     HashMap<ConceptPattern, Integer> units = new HashMap();
/* 372:359 */     for (ConceptPattern plotUnit : (ArrayList)((HashMap)this.genConceptPatternUnits.get(Integer.valueOf(size))).get(title))
/* 373:    */     {
/* 374:361 */       boolean matched = false;
/* 375:    */       List<Entity> things2;
/* 376:362 */       for (ConceptPattern hashUnit : units.keySet()) {
/* 377:363 */         if (hashUnit.asString().equals(plotUnit.asString()))
/* 378:    */         {
/* 379:364 */           List<Entity> things1 = plotUnit.asThings();
/* 380:365 */           things2 = hashUnit.asThings();
/* 381:    */           
/* 382:367 */           Aligner aligner = Aligner.getAligner();
/* 383:    */           
/* 384:369 */           Alignment<Entity, Entity> alignment = (Alignment)aligner.align(things1, things2).get(0);
/* 385:370 */           if (alignment.size() == size) {
/* 386:371 */             units.put(hashUnit, Integer.valueOf(((Integer)units.get(hashUnit)).intValue() + 1));
/* 387:    */           }
/* 388:    */         }
/* 389:    */       }
/* 390:376 */       if (!matched) {
/* 391:377 */         units.put(plotUnit, Integer.valueOf(1));
/* 392:    */       }
/* 393:382 */       ArrayList<ConceptPattern> alreadyPut = new ArrayList();
/* 394:383 */       boolean found = false;
/* 395:    */       boolean put;
/* 396:384 */       for (Map.Entry<ConceptPattern, Integer> entry : (ArrayList)this.genConceptPatternCounts.get(Integer.valueOf(size))) {
/* 397:385 */         if (((ConceptPattern)entry.getKey()).canAlign(plotUnit, plotUnit.getSize()))
/* 398:    */         {
/* 399:386 */           put = false;
/* 400:387 */           for (ConceptPattern unit : alreadyPut) {
/* 401:388 */             if (unit.canAlign(plotUnit, unit.getSize()))
/* 402:    */             {
/* 403:389 */               put = true;
/* 404:390 */               break;
/* 405:    */             }
/* 406:    */           }
/* 407:393 */           if (!put)
/* 408:    */           {
/* 409:394 */             entry.setValue(Integer.valueOf(((Integer)entry.getValue()).intValue() + 1));
/* 410:395 */             alreadyPut.add(plotUnit);
/* 411:    */           }
/* 412:    */         }
/* 413:    */       }
/* 414:399 */       if (!found)
/* 415:    */       {
/* 416:400 */         boolean put = false;
/* 417:401 */         for (ConceptPattern unit : alreadyPut) {
/* 418:402 */           if (unit.canAlign(plotUnit, unit.getSize()))
/* 419:    */           {
/* 420:403 */             put = true;
/* 421:404 */             break;
/* 422:    */           }
/* 423:    */         }
/* 424:407 */         if (!put)
/* 425:    */         {
/* 426:408 */           ((ArrayList)this.genConceptPatternCounts.get(Integer.valueOf(size))).add(new AbstractMap.SimpleEntry(plotUnit, Integer.valueOf(1)));
/* 427:409 */           alreadyPut.add(plotUnit);
/* 428:    */         }
/* 429:    */       }
/* 430:    */     }
/* 431:414 */     ((HashMap)this.genConceptPatternHashes.get(Integer.valueOf(size))).put(title, units);
/* 432:    */   }
/* 433:    */   
/* 434:    */   private void populateSizeNPlotUnits(int size)
/* 435:    */   {
/* 436:418 */     for (String title : this.storyNames) {
/* 437:419 */       putPlotUnits(title, size);
/* 438:    */     }
/* 439:    */   }
/* 440:    */   
/* 441:    */   public int getPlotUnitCount(ConceptPattern unit, int size)
/* 442:    */   {
/* 443:424 */     for (Map.Entry<ConceptPattern, Integer> entry : (ArrayList)this.genConceptPatternCounts.get(Integer.valueOf(size))) {
/* 444:425 */       if (unit.equals(entry.getKey())) {
/* 445:426 */         return ((Integer)entry.getValue()).intValue();
/* 446:    */       }
/* 447:    */     }
/* 448:429 */     return 0;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public float getRarity(ConceptPattern unit, int size)
/* 452:    */   {
/* 453:433 */     for (Map.Entry<ConceptPattern, Integer> entry : (ArrayList)this.genConceptPatternCounts.get(Integer.valueOf(size))) {
/* 454:434 */       if (unit.equals(entry.getKey())) {
/* 455:435 */         return ((Integer)entry.getValue()).intValue() / this.numStoriesRead;
/* 456:    */       }
/* 457:    */     }
/* 458:438 */     return 0.0F;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public HashMap<ConceptPattern, Integer> getStoryPlotUnitCounts(String title, int size)
/* 462:    */   {
/* 463:442 */     boolean found = false;
/* 464:443 */     for (int testSize : this.testSizes) {
/* 465:444 */       if (size == testSize) {
/* 466:445 */         found = true;
/* 467:    */       }
/* 468:    */     }
/* 469:448 */     if (!found) {
/* 470:449 */       populateSizeNPlotUnits(size);
/* 471:    */     }
/* 472:452 */     HashMap<String, HashMap<ConceptPattern, Integer>> sizedPatternHashes = (HashMap)this.genConceptPatternHashes.get(Integer.valueOf(size));
/* 473:453 */     return (HashMap)sizedPatternHashes.get(title);
/* 474:    */   }
/* 475:    */   
/* 476:    */   public ArrayList<PlotEvent> getPlotEvents(String story)
/* 477:    */   {
/* 478:457 */     return (ArrayList)this.plotEvents.get(story);
/* 479:    */   }
/* 480:    */   
/* 481:    */   public ArrayList<ConceptPattern> getPlotUnits(String story, int size)
/* 482:    */   {
/* 483:461 */     boolean found = false;
/* 484:462 */     for (int testSize : this.testSizes) {
/* 485:463 */       if (size == testSize) {
/* 486:464 */         found = true;
/* 487:    */       }
/* 488:    */     }
/* 489:467 */     if (!found) {
/* 490:468 */       populateSizeNPlotUnits(size);
/* 491:    */     }
/* 492:471 */     HashMap<String, ArrayList<ConceptPattern>> sizedPatternUnits = (HashMap)this.genConceptPatternUnits.get(Integer.valueOf(size));
/* 493:472 */     return (ArrayList)sizedPatternUnits.get(story);
/* 494:    */   }
/* 495:    */   
/* 496:    */   public void addPlotUnitComparison(String story1, String story2, int size, ConceptPatternMatchWrapper match)
/* 497:    */   {
/* 498:477 */     if (!this.genConceptPatternComparisons.containsKey(Integer.valueOf(size))) {
/* 499:478 */       this.genConceptPatternComparisons.put(Integer.valueOf(size), new HashMap());
/* 500:    */     }
/* 501:481 */     HashMap<String, HashMap<String, ConceptPatternMatchWrapper>> storyComparisons = (HashMap)this.genConceptPatternComparisons.get(Integer.valueOf(size));
/* 502:483 */     if (!storyComparisons.containsKey(story1)) {
/* 503:484 */       storyComparisons.put(story1, new HashMap());
/* 504:    */     }
/* 505:487 */     ((HashMap)storyComparisons.get(story1)).put(story2, match);
/* 506:    */   }
/* 507:    */   
/* 508:    */   public HashMap<String, HashMap<String, ConceptPatternMatchWrapper>> getPlotUnitComparisons(int size)
/* 509:    */   {
/* 510:491 */     return (HashMap)this.genConceptPatternComparisons.get(Integer.valueOf(size));
/* 511:    */   }
/* 512:    */   
/* 513:    */   public ConceptPatternMatchWrapper getPlotUnitComparison(String story1, String story2, int size)
/* 514:    */   {
/* 515:495 */     if (story1.equals(story2)) {
/* 516:496 */       return ConceptPatternMatchWrapper.getFullMatch(story1, size, this);
/* 517:    */     }
/* 518:498 */     if ((this.genConceptPatternComparisons.containsKey(Integer.valueOf(size))) && (((HashMap)this.genConceptPatternComparisons.get(Integer.valueOf(size))).containsKey(story1))) {
/* 519:499 */       return (ConceptPatternMatchWrapper)((HashMap)((HashMap)this.genConceptPatternComparisons.get(Integer.valueOf(size))).get(story1)).get(story2);
/* 520:    */     }
/* 521:501 */     return null;
/* 522:    */   }
/* 523:    */   
/* 524:    */   public HashMap<String, ArrayList<ConceptPattern>> getGenConceptPatternUnits(int size)
/* 525:    */   {
/* 526:504 */     return (HashMap)this.genConceptPatternUnits.get(Integer.valueOf(size));
/* 527:    */   }
/* 528:    */   
/* 529:    */   public double getWidestNameWidth(Font font, FontRenderContext context)
/* 530:    */   {
/* 531:513 */     double widest = -1.0D;
/* 532:514 */     for (String name : this.storyNames)
/* 533:    */     {
/* 534:515 */       Rectangle2D bounds = font.getStringBounds(name, context);
/* 535:516 */       if (bounds.getWidth() > widest) {
/* 536:517 */         widest = bounds.getWidth();
/* 537:    */       }
/* 538:    */     }
/* 539:520 */     return widest;
/* 540:    */   }
/* 541:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.ReflectionLevelMemory
 * JD-Core Version:    0.7.0.1
 */