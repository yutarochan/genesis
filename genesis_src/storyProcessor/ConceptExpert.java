/*   1:    */ package storyProcessor;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import connections.AbstractWiredBox;
/*   7:    */ import connections.Connections;
/*   8:    */ import connections.Ports;
/*   9:    */ import gui.ActivityMonitor;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Collections;
/*  12:    */ import java.util.HashMap;
/*  13:    */ import java.util.HashSet;
/*  14:    */ import java.util.Iterator;
/*  15:    */ import java.util.Vector;
/*  16:    */ import javax.swing.JOptionPane;
/*  17:    */ import matchers.StandardMatcher;
/*  18:    */ import matchers.Substitutor;
/*  19:    */ import minilisp.LList;
/*  20:    */ import parameters.Switch;
/*  21:    */ import persistence.JCheckBoxWithMemory;
/*  22:    */ import start.Generator;
/*  23:    */ import text.Html;
/*  24:    */ import tools.Getters;
/*  25:    */ import tools.Predicates;
/*  26:    */ import utils.Mark;
/*  27:    */ import utils.PairOfEntities;
/*  28:    */ 
/*  29:    */ public class ConceptExpert
/*  30:    */   extends AbstractWiredBox
/*  31:    */ {
/*  32:    */   public static final String DISCOVERED_CONCEPT = "Discovered concept";
/*  33:    */   public static final String REFLECTION_ANALYSIS = "Reflection analysis port";
/*  34:    */   public static final String INSTANTIATED_CONCEPTS = "Instantiated reflections port";
/*  35:    */   public static final String TAB = "tab port";
/*  36:    */   public static final String ENGLISH = "English port";
/*  37:    */   public static final String INJECT_ELEMENT = "inject element";
/*  38:    */   public static final String TEST_ELEMENT = "test element";
/*  39: 49 */   private ArrayList<ReflectionDescription> discoveries = new ArrayList();
/*  40:    */   HashSet<Entity> examined;
/*  41:    */   
/*  42:    */   public ConceptExpert()
/*  43:    */   {
/*  44: 52 */     Connections.wire("to activity monitor", this, ActivityMonitor.getActivityMonitor());
/*  45: 53 */     Connections.getPorts(this).addSignalProcessor("process");
/*  46: 54 */     Connections.getPorts(this).addSignalProcessor("Discovered concept", "processDiscoveredConcept");
/*  47: 55 */     Connections.wire("Discovered concept", this, "Discovered concept", this);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void processDiscoveredConcept(Object o)
/*  51:    */   {
/*  52: 60 */     if (!Switch.reportSubConceptsSwitch.isSelected()) {
/*  53: 61 */       this.discoveries = weedOutSubdiscovieries((ReflectionDescription)o, this.discoveries);
/*  54:    */     } else {
/*  55: 64 */       this.discoveries.add((ReflectionDescription)o);
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void process(Object signal)
/*  60:    */   {
/*  61: 74 */     boolean debug = false;
/*  62: 75 */     Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.CONCEPT_FINDER_WORKING, Boolean.valueOf(true) }));
/*  63: 76 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Entering ConceptExpert", signal });
/*  64: 77 */     BetterSignal triple = BetterSignal.isSignal(signal);
/*  65: 78 */     if (triple == null) {
/*  66: 79 */       return;
/*  67:    */     }
/*  68: 81 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Starting concept analysis" });
/*  69:    */     
/*  70: 83 */     this.discoveries.clear();
/*  71: 84 */     String remark = "Reflection pattern detector receiving story of length ";
/*  72: 85 */     remark = remark + ((Entity)triple.get(1, Entity.class)).getElements().size();
/*  73: 86 */     Mark.say(new Object[] {Boolean.valueOf(debug), remark });
/*  74: 87 */     Sequence concepts = (Sequence)triple.get(0, Entity.class);
/*  75:    */     
/*  76: 89 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Working with", Integer.valueOf(concepts.getElements().size()), "concept patterns" });
/*  77:    */     Iterator localIterator2;
/*  78: 90 */     if (debug) {
/*  79: 91 */       for (Iterator localIterator1 = concepts.getElements().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/*  80:    */       {
/*  81: 91 */         Entity reflection = (Entity)localIterator1.next();
/*  82: 92 */         Mark.say(new Object[] {"Reflection elements for", reflection.getName() });
/*  83: 93 */         localIterator2 = reflection.getElements().iterator(); continue;Entity t = (Entity)localIterator2.next();
/*  84: 94 */         Mark.say(new Object[] {"Element:", t.asStringWithIndexes() });
/*  85:    */       }
/*  86:    */     }
/*  87: 99 */     Sequence story = (Sequence)triple.get(1, Entity.class);
/*  88:100 */     Sequence inferences = (Sequence)triple.get(2, Entity.class);
/*  89:104 */     for (Entity concept : concepts.getElements())
/*  90:    */     {
/*  91:105 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Looking for instances of", concept.getType() });
/*  92:106 */       if (debug)
/*  93:    */       {
/*  94:    */         Entity localEntity1;
/*  95:107 */         for (Iterator localIterator3 = concept.getElements().iterator(); localIterator3.hasNext(); localEntity1 = (Entity)localIterator3.next()) {}
/*  96:    */       }
/*  97:111 */       Mark.say(new Object[] {Boolean.valueOf(debug), "The inference count is:", Integer.valueOf(inferences.getElements().size()) });
/*  98:    */       
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:119 */       processOneConcept(translateConceptToList((Sequence)concept), translateStoryToList(story), inferences.getElements(), concept.getType(), new LList(), new LList(), story);
/* 106:120 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Completed search for instances of", concept.getType() });
/* 107:    */     }
/* 108:123 */     Connections.getPorts(this).transmit("clearButtons", "reset");
/* 109:125 */     for (ReflectionDescription completion : this.discoveries)
/* 110:    */     {
/* 111:127 */       Connections.getPorts(this).transmit("addPlotUnitButton", completion);
/* 112:    */       
/* 113:129 */       Sequence instantiation = completion.getInstantiations();
/* 114:    */       
/* 115:131 */       instantiation.addType(completion.getName());
/* 116:    */       
/* 117:    */ 
/* 118:    */ 
/* 119:135 */       message = new BetterSignal(new Object[] { "Concept analysis", Html.normal(ReflectionTranslator.translateReflection(completion)) });
/* 120:136 */       Connections.getPorts(this).transmit("English port", message);
/* 121:    */     }
/* 122:139 */     ReflectionAnalysis analysis = new ReflectionAnalysis(this.discoveries, story);
/* 123:140 */     Sequence instantiations = new Sequence("reflection");
/* 124:142 */     for (Object message = analysis.getReflectionDescriptions().iterator(); ((Iterator)message).hasNext();)
/* 125:    */     {
/* 126:142 */       ReflectionDescription rd = (ReflectionDescription)((Iterator)message).next();
/* 127:    */       Entity localEntity2;
/* 128:143 */       for (Iterator localIterator4 = rd.getInstantiations().getElements().iterator(); localIterator4.hasNext(); localEntity2 = (Entity)localIterator4.next()) {}
/* 129:145 */       instantiations.addElement(rd.getInstantiations());
/* 130:    */     }
/* 131:149 */     Connections.getPorts(this).transmit("Instantiated reflections port", instantiations);
/* 132:150 */     Connections.getPorts(this).transmit("Reflection analysis port", analysis);
/* 133:    */     
/* 134:152 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Completed reflective analysis" });
/* 135:153 */     Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.CONCEPT_FINDER_WORKING, Boolean.valueOf(false) }));
/* 136:    */   }
/* 137:    */   
/* 138:    */   private ArrayList<ReflectionDescription> weedOutSubdiscovieries(ReflectionDescription newCandidate, ArrayList<ReflectionDescription> candidates)
/* 139:    */   {
/* 140:159 */     if (isSubsetOfAnotherCandidateInList(newCandidate, candidates)) {
/* 141:160 */       return candidates;
/* 142:    */     }
/* 143:162 */     if (isSameAsAnotherCandidateInList(newCandidate, candidates)) {
/* 144:163 */       return candidates;
/* 145:    */     }
/* 146:165 */     ArrayList<ReflectionDescription> newCandidateList = new ArrayList();
/* 147:166 */     newCandidateList.add(newCandidate);
/* 148:    */     
/* 149:168 */     ArrayList<ReflectionDescription> resultList = new ArrayList();
/* 150:170 */     for (ReflectionDescription candidate : candidates) {
/* 151:171 */       if (!isSubsetOfAnotherCandidateInList(candidate, newCandidateList)) {
/* 152:172 */         resultList.add(candidate);
/* 153:    */       }
/* 154:    */     }
/* 155:175 */     resultList.add(newCandidate);
/* 156:176 */     return resultList;
/* 157:    */   }
/* 158:    */   
/* 159:    */   private ArrayList<ReflectionDescription> weedOutSubdiscovieries(ArrayList<ReflectionDescription> candidates)
/* 160:    */   {
/* 161:182 */     ArrayList<ReflectionDescription> result = new ArrayList();
/* 162:184 */     for (ReflectionDescription candidate : candidates) {
/* 163:185 */       if ((!isSameAsAnotherCandidateInList(candidate, result)) && 
/* 164:186 */         (!isSubsetOfAnotherCandidateInList(candidate, candidates))) {
/* 165:188 */         result.add(candidate);
/* 166:    */       }
/* 167:    */     }
/* 168:198 */     return result;
/* 169:    */   }
/* 170:    */   
/* 171:    */   private boolean isSubsetOfAnotherCandidateInList(ReflectionDescription candidate, ArrayList<ReflectionDescription> candidates)
/* 172:    */   {
/* 173:202 */     Vector<Entity> candidateStoryElements = candidate.getStoryElementsInvolved().getElements();
/* 174:203 */     for (ReflectionDescription otherCandidate : candidates) {
/* 175:204 */       if (candidate != otherCandidate) {
/* 176:207 */         if (candidate.getName() == otherCandidate.getName())
/* 177:    */         {
/* 178:211 */           Vector<Entity> otherCandidateStoryElements = otherCandidate.getStoryElementsInvolved().getElements();
/* 179:213 */           if (isSubset(candidateStoryElements, otherCandidateStoryElements)) {
/* 180:214 */             return true;
/* 181:    */           }
/* 182:    */         }
/* 183:    */       }
/* 184:    */     }
/* 185:217 */     return false;
/* 186:    */   }
/* 187:    */   
/* 188:    */   private boolean isSubset(Vector<Entity> candidateStoryElements, Vector<Entity> otherCandidateStoryElements)
/* 189:    */   {
/* 190:221 */     for (Entity storyElement : candidateStoryElements) {
/* 191:222 */       if (!otherCandidateStoryElements.contains(storyElement)) {
/* 192:223 */         return false;
/* 193:    */       }
/* 194:    */     }
/* 195:226 */     if (candidateStoryElements.size() == otherCandidateStoryElements.size()) {
/* 196:227 */       return false;
/* 197:    */     }
/* 198:229 */     return true;
/* 199:    */   }
/* 200:    */   
/* 201:    */   private boolean isSameAsAnotherCandidateInList(ReflectionDescription candidate, ArrayList<ReflectionDescription> candidates)
/* 202:    */   {
/* 203:233 */     Vector<Entity> candidateStoryElements = candidate.getStoryElementsInvolved().getElements();
/* 204:234 */     for (ReflectionDescription otherCandidate : candidates) {
/* 205:235 */       if (candidate != otherCandidate) {
/* 206:240 */         if (candidate.getName().equals(otherCandidate.getName()))
/* 207:    */         {
/* 208:242 */           Vector<Entity> otherCandidateStoryElements = otherCandidate.getStoryElementsInvolved().getElements();
/* 209:243 */           if (isSameAs(candidateStoryElements, otherCandidateStoryElements)) {
/* 210:244 */             return true;
/* 211:    */           }
/* 212:    */         }
/* 213:    */       }
/* 214:    */     }
/* 215:248 */     return false;
/* 216:    */   }
/* 217:    */   
/* 218:    */   private boolean isSameAs(Vector<Entity> candidateStoryElements, Vector<Entity> otherCandidateStoryElements)
/* 219:    */   {
/* 220:252 */     for (Entity storyElement : candidateStoryElements) {
/* 221:253 */       if (!otherCandidateStoryElements.contains(storyElement)) {
/* 222:254 */         return false;
/* 223:    */       }
/* 224:    */     }
/* 225:257 */     if (candidateStoryElements.size() == otherCandidateStoryElements.size()) {
/* 226:258 */       return true;
/* 227:    */     }
/* 228:260 */     return false;
/* 229:    */   }
/* 230:    */   
/* 231:    */   private LList<Entity> translateConceptToList(Sequence s)
/* 232:    */   {
/* 233:264 */     LList<Entity> result = new LList();
/* 234:265 */     Vector<Entity> v = s.getElements();
/* 235:    */     
/* 236:    */ 
/* 237:    */ 
/* 238:269 */     Collections.reverse(v);
/* 239:    */     
/* 240:    */ 
/* 241:    */ 
/* 242:273 */     Vector<Entity> front = new Vector();
/* 243:    */     
/* 244:275 */     Vector<Entity> back = new Vector();
/* 245:277 */     for (Entity x : v) {
/* 246:278 */       if (x.hasProperty("idiom", "check")) {
/* 247:279 */         back.add(x);
/* 248:    */       } else {
/* 249:282 */         front.add(x);
/* 250:    */       }
/* 251:    */     }
/* 252:285 */     back.addAll(front);
/* 253:286 */     v = back;
/* 254:288 */     for (int i = v.size() - 1; i >= 0; i--) {
/* 255:289 */       result = result.cons((Entity)v.get(i));
/* 256:    */     }
/* 257:291 */     return result;
/* 258:    */   }
/* 259:    */   
/* 260:    */   private LList<Entity> translateStoryToList(Sequence s)
/* 261:    */   {
/* 262:295 */     LList<Entity> result = new LList();
/* 263:296 */     Vector<Entity> v = s.getElements();
/* 264:299 */     for (int i = v.size() - 1; i >= 0; i--) {
/* 265:300 */       result = result.cons((Entity)v.get(i));
/* 266:    */     }
/* 267:302 */     return result;
/* 268:    */   }
/* 269:    */   
/* 270:    */   private LList<Entity> translateVectorToList(Vector<Entity> v)
/* 271:    */   {
/* 272:306 */     LList<Entity> result = new LList();
/* 273:307 */     for (int i = v.size() - 1; i >= 0; i--) {
/* 274:308 */       result = result.cons((Entity)v.get(i));
/* 275:    */     }
/* 276:310 */     return result;
/* 277:    */   }
/* 278:    */   
/* 279:    */   private Sequence translateListToSequence(LList<Entity> l)
/* 280:    */   {
/* 281:314 */     Sequence result = new Sequence();
/* 282:315 */     for (Entity t : l) {
/* 283:316 */       if (!result.contains(t)) {
/* 284:317 */         result.addElement(t);
/* 285:    */       }
/* 286:    */     }
/* 287:320 */     return result;
/* 288:    */   }
/* 289:    */   
/* 290:    */   private Sequence translateVectorToSequence(Vector<Entity> l)
/* 291:    */   {
/* 292:324 */     Sequence result = new Sequence();
/* 293:325 */     for (Entity t : l) {
/* 294:326 */       if (!result.contains(t)) {
/* 295:327 */         result.addElement(t);
/* 296:    */       }
/* 297:    */     }
/* 298:330 */     return result;
/* 299:    */   }
/* 300:    */   
/* 301:    */   private void processOneConcept(LList<Entity> patterns, LList<Entity> story, Vector<Entity> inferences, String name, LList<Entity> participants, LList<Entity> instantiation, Sequence storySequence)
/* 302:    */   {
/* 303:334 */     boolean debug = false;
/* 304:335 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Processing one concept" });
/* 305:336 */     ArrayList<PairOfEntities> exclusions = new ArrayList();
/* 306:    */     
/* 307:338 */     LList<Entity> handle = new LList();
/* 308:339 */     for (Entity e : patterns) {
/* 309:341 */       if ((e.isA("equal")) && (e.hasFeature("not")) && (e.hasProperty("modal", "must"))) {
/* 310:342 */         exclusions.add(new PairOfEntities(e.getSubject(), Getters.getObject(e)));
/* 311:    */       } else {
/* 312:346 */         handle = handle.cons(e);
/* 313:    */       }
/* 314:    */     }
/* 315:350 */     patterns = handle;
/* 316:352 */     for (Entity t : patterns) {
/* 317:353 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Pattern element:", t.asString() });
/* 318:    */     }
/* 319:358 */     this.examined = new HashSet();
/* 320:    */     
/* 321:    */ 
/* 322:    */ 
/* 323:    */ 
/* 324:    */ 
/* 325:364 */     processNextConceptPattern(patterns, story, new LList(), inferences, name, participants, instantiation, storySequence, exclusions);
/* 326:    */   }
/* 327:    */   
/* 328:    */   private void processNextConceptPattern(LList<Entity> patterns, LList<Entity> fullStory, LList<PairOfEntities> bindings, Vector<Entity> inferences, String name, LList<Entity> participants, LList instantiation, Sequence storySequence, ArrayList<PairOfEntities> exclusions)
/* 329:    */   {
/* 330:373 */     if (!patterns.endP()) {
/* 331:375 */       if (Predicates.isSometimes((Entity)patterns.first()))
/* 332:    */       {
/* 333:377 */         processNextConceptPattern(patterns.rest(), fullStory, bindings, inferences, name, participants, instantiation, storySequence, exclusions);
/* 334:    */       }
/* 335:380 */       else if (((Entity)patterns.first()).hasProperty("idiom", "check"))
/* 336:    */       {
/* 337:382 */         Entity patternElement = (Entity)patterns.first();
/* 338:383 */         boolean found = false;
/* 339:384 */         Entity storyElement = null;
/* 340:386 */         for (Entity e : storySequence.getElements()) {
/* 341:387 */           if (StandardMatcher.getBasicMatcher().match(e, patternElement, bindings) != null)
/* 342:    */           {
/* 343:389 */             storyElement = e;
/* 344:390 */             found = true;
/* 345:391 */             break;
/* 346:    */           }
/* 347:    */         }
/* 348:395 */         if (!found)
/* 349:    */         {
/* 350:396 */           storyElement = Substitutor.replace(patternElement, bindings);
/* 351:397 */           Connections.getPorts(this).transmit("test element", storyElement);
/* 352:    */         }
/* 353:401 */         if (!found)
/* 354:    */         {
/* 355:402 */           storyElement = Substitutor.replace(patternElement, bindings);
/* 356:403 */           String question = Generator.getGenerator().generateXPeriod(storyElement, "present") + "?";
/* 357:404 */           if (JOptionPane.showConfirmDialog(null, question, "", 0) == 0)
/* 358:    */           {
/* 359:406 */             participants = participants.cons(storyElement);
/* 360:    */             
/* 361:408 */             Connections.getPorts(this).transmit("inject element", storyElement);
/* 362:    */             
/* 363:    */ 
/* 364:    */ 
/* 365:412 */             found = true;
/* 366:    */           }
/* 367:    */         }
/* 368:415 */         if (found) {
/* 369:416 */           processNextConceptPattern(patterns.rest(), fullStory, bindings, inferences, name, participants, instantiation, storySequence, exclusions);
/* 370:    */         }
/* 371:    */       }
/* 372:    */     }
/* 373:419 */     processOneConceptPattern(patterns, fullStory, fullStory, bindings, inferences, name, participants, instantiation, storySequence, exclusions);
/* 374:    */   }
/* 375:    */   
/* 376:    */   private void processOneConceptPattern(LList<Entity> patterns, LList<Entity> fullStory, LList<Entity> restOfStory, LList<PairOfEntities> bindings, Vector<Entity> inferences, String name, LList<Entity> participants, LList instantiation, Sequence storySequence, ArrayList<PairOfEntities> exclusions)
/* 377:    */   {
/* 378:423 */     boolean debug = false;
/* 379:    */     Sequence instantiationSequence;
/* 380:425 */     if (patterns.endP())
/* 381:    */     {
/* 382:432 */       Sequence participantSequence = translateListToSequence(participants);
/* 383:433 */       instantiationSequence = translateListToSequence(instantiation);
/* 384:439 */       if (checkForDuplicateBindingsToSameEntity(bindings, exclusions)) {
/* 385:440 */         return;
/* 386:    */       }
/* 387:443 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Name:", name });
/* 388:    */       
/* 389:445 */       ReflectionDescription x = new ReflectionDescription(name, bindings, participantSequence, instantiationSequence, storySequence);
/* 390:    */       
/* 391:    */ 
/* 392:    */ 
/* 393:    */ 
/* 394:    */ 
/* 395:451 */       Connections.getPorts(this).transmit("Discovered concept", x);
/* 396:    */       
/* 397:    */ 
/* 398:    */ 
/* 399:455 */       return;
/* 400:    */     }
/* 401:458 */     if (restOfStory.endP())
/* 402:    */     {
/* 403:459 */       if (debug)
/* 404:    */       {
/* 405:460 */         Mark.say(new Object[] {"End of story, and still not matching" });
/* 406:461 */         for (Entity t : patterns) {
/* 407:462 */           Mark.say(new Object[] {t.asString() });
/* 408:    */         }
/* 409:    */       }
/* 410:465 */       return;
/* 411:    */     }
/* 412:468 */     StandardMatcher matcher = StandardMatcher.getBasicMatcher();
/* 413:469 */     Entity patternElement = (Entity)patterns.first();
/* 414:474 */     if (patternElement.isA("entail"))
/* 415:    */     {
/* 416:476 */       Entity consequent = patternElement.getObject();
/* 417:477 */       Entity antecedents = patternElement.getSubject();
/* 418:478 */       Entity antecedent = null;
/* 419:480 */       if ((antecedents.sequenceP("conjuction")) && (!antecedents.getElements().isEmpty())) {
/* 420:482 */         antecedent = antecedents.getElement(0);
/* 421:    */       } else {
/* 422:486 */         Mark.err(new Object[] {"Malformed entail antecedent construct" });
/* 423:    */       }
/* 424:489 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Discovered antecedent pattern", antecedent.asString() });
/* 425:490 */       processOneEntailAntecedent(antecedent, consequent, patterns, fullStory, fullStory, bindings, inferences, name, participants, instantiation, storySequence, exclusions);
/* 426:491 */       return;
/* 427:    */     }
/* 428:494 */     Entity storyElement = (Entity)restOfStory.first();
/* 429:495 */     boolean test = false;
/* 430:496 */     if ((patternElement.isA("classification")) && (storyElement.isA("classification"))) {
/* 431:497 */       Mark.say(new Object[] {Boolean.valueOf(test), "Working on", patternElement.asString(), storyElement.asString() });
/* 432:    */     }
/* 433:499 */     LList<PairOfEntities> newBindings = matcher.match(patternElement, storyElement, bindings);
/* 434:500 */     if (newBindings != null)
/* 435:    */     {
/* 436:501 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Matched:\n", patternElement.asString(), "\nwith\n", storyElement.asString(), "\n with bindings\n", newBindings, "\nand moving on" });
/* 437:505 */       if ((patternElement.isA("classification")) && 
/* 438:506 */         (patternElement.getSubject().getType().equals(storyElement.getSubject().getType())))
/* 439:    */       {
/* 440:509 */         Mark.say(new Object[] {"Classification test passed", patternElement.asString(), storyElement.asString() });
/* 441:    */         
/* 442:511 */         processNextConceptPattern(patterns.rest(), fullStory, newBindings, inferences, name, participants.cons(storyElement), instantiation.cons(storyElement), storySequence, exclusions);
/* 443:    */       }
/* 444:514 */       else if ((patternElement.isA("position")) && (patternElement.getObject().getType().equals(storyElement.getObject().getType())))
/* 445:    */       {
/* 446:518 */         processNextConceptPattern(patterns.rest(), fullStory, newBindings, inferences, name, participants.cons(storyElement), instantiation.cons(storyElement), storySequence, exclusions);
/* 447:    */       }
/* 448:521 */       else if ((patternElement.isA("classification")) || (patternElement.isA("position")))
/* 449:    */       {
/* 450:524 */         Mark.say(new Object[] {"Classification and job type test failed", patternElement.asString(), storyElement.asString() });
/* 451:    */       }
/* 452:    */       else
/* 453:    */       {
/* 454:527 */         Mark.say(new Object[] {Boolean.valueOf(test), "Not a classification or job type situation" });
/* 455:528 */         processNextConceptPattern(patterns.rest(), fullStory, newBindings, inferences, name, participants.cons(storyElement), instantiation.cons(storyElement), storySequence, exclusions);
/* 456:    */       }
/* 457:    */     }
/* 458:532 */     processOneConceptPattern(patterns, fullStory, restOfStory.rest(), bindings, inferences, name, participants, instantiation, storySequence, exclusions);
/* 459:    */   }
/* 460:    */   
/* 461:    */   private boolean checkForDuplicateBindingsToSameEntity(LList<PairOfEntities> bindings, ArrayList<PairOfEntities> exclusions)
/* 462:    */   {
/* 463:537 */     HashMap<Entity, Entity> map = new HashMap();
/* 464:538 */     for (PairOfEntities p : bindings)
/* 465:    */     {
/* 466:539 */       Entity variable = p.getPattern();
/* 467:540 */       Entity binding = p.getDatum();
/* 468:541 */       map.put(variable, binding);
/* 469:    */     }
/* 470:543 */     for (PairOfEntities p : exclusions) {
/* 471:544 */       if (map.get(p.getPattern()) == map.get(p.getDatum())) {
/* 472:547 */         return true;
/* 473:    */       }
/* 474:    */     }
/* 475:551 */     return false;
/* 476:    */   }
/* 477:    */   
/* 478:    */   private String extractProperNameType(Vector<String> sThread)
/* 479:    */   {
/* 480:555 */     String type = null;
/* 481:556 */     for (String c : sThread)
/* 482:    */     {
/* 483:557 */       if (c.equals("name")) {
/* 484:558 */         return type;
/* 485:    */       }
/* 486:561 */       type = c;
/* 487:    */     }
/* 488:564 */     return null;
/* 489:    */   }
/* 490:    */   
/* 491:    */   private void processOneEntailAntecedent(Entity antecedent, Entity consequent, LList<Entity> patterns, LList<Entity> fullStory, LList<Entity> restOfStory, LList<PairOfEntities> bindings, Vector<Entity> inferences, String name, LList participants, LList instantiation, Sequence storySequence, ArrayList<PairOfEntities> exclusions)
/* 492:    */   {
/* 493:568 */     boolean debug = false;
/* 494:570 */     if (restOfStory.endP()) {
/* 495:575 */       return;
/* 496:    */     }
/* 497:579 */     StandardMatcher matcher = StandardMatcher.getBasicMatcher();
/* 498:580 */     Entity storyElement = (Entity)restOfStory.first();
/* 499:    */     
/* 500:582 */     LList<PairOfEntities> newBindings = matcher.match(antecedent, storyElement, bindings);
/* 501:584 */     if (newBindings != null)
/* 502:    */     {
/* 503:586 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Found instantiation of antecedent\n", antecedent.asString(), "\nnamely\n", storyElement.asString(), "\nand moving on" });
/* 504:587 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Bindings", bindings });
/* 505:588 */       Mark.say(new Object[] {Boolean.valueOf(debug), "New Bindings", newBindings });
/* 506:    */       
/* 507:590 */       processOneEntailConsequent(storyElement, antecedent, consequent, patterns, fullStory, fullStory, newBindings, inferences, name, participants, instantiation, storySequence, exclusions);
/* 508:    */     }
/* 509:    */     else
/* 510:    */     {
/* 511:593 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Unable to match", antecedent, storyElement });
/* 512:    */     }
/* 513:596 */     processOneEntailAntecedent(antecedent, consequent, patterns, fullStory, restOfStory.rest(), bindings, inferences, name, participants, instantiation, storySequence, exclusions);
/* 514:    */   }
/* 515:    */   
/* 516:    */   private void processOneEntailConsequent(Entity instantiatedAntecedent, Entity antecedent, Entity consequent, LList<Entity> patterns, LList<Entity> fullStory, LList<Entity> restOfStory, LList<PairOfEntities> bindings, Vector<Entity> inferences, String name, LList participants, LList instantiation, Sequence storySequence, ArrayList<PairOfEntities> exclusions)
/* 517:    */   {
/* 518:600 */     boolean debug = false;
/* 519:601 */     if (restOfStory.endP())
/* 520:    */     {
/* 521:602 */       Mark.say(new Object[] {Boolean.valueOf(debug), "At end of story and couldn't find consequent pattern", consequent.asString(), "with bindngs\n", bindings });
/* 522:    */       
/* 523:    */ 
/* 524:    */ 
/* 525:606 */       return;
/* 526:    */     }
/* 527:610 */     StandardMatcher matcher = StandardMatcher.getBasicMatcher();
/* 528:611 */     Entity storyElement = (Entity)restOfStory.first();
/* 529:612 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Trying to match", consequent.asString(), "with", storyElement.asString() });
/* 530:    */     
/* 531:614 */     LList<PairOfEntities> newBindings = matcher.match(consequent, storyElement, bindings);
/* 532:615 */     if (newBindings != null)
/* 533:    */     {
/* 534:617 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Found instantiation of consequent", consequent.asString(), "namely", storyElement.asString(), "with bindings\n", bindings, "and moving on" });
/* 535:    */       
/* 536:619 */       Vector<Entity> path = connected(instantiatedAntecedent, storyElement, inferences);
/* 537:620 */       if (path != null)
/* 538:    */       {
/* 539:621 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Antecedent", instantiatedAntecedent.asString(), "IS connected to consequent", storyElement.asString() });
/* 540:    */         
/* 541:    */ 
/* 542:    */ 
/* 543:625 */         Sequence pathSequence = new Sequence("entail");
/* 544:626 */         pathSequence.addElement((Entity)path.get(0));
/* 545:627 */         pathSequence.addElement((Entity)path.get(path.size() - 1));
/* 546:628 */         processNextConceptPattern(patterns.rest(), fullStory, newBindings, inferences, name, participants.append(translateVectorToList(path)), instantiation
/* 547:629 */           .cons(pathSequence), storySequence, exclusions);
/* 548:    */       }
/* 549:    */       else
/* 550:    */       {
/* 551:632 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Antecedent", instantiatedAntecedent.asString(), "NOT connected to consequent", storyElement.asString() });
/* 552:    */       }
/* 553:    */     }
/* 554:639 */     processOneEntailConsequent(instantiatedAntecedent, antecedent, consequent, patterns, fullStory, restOfStory.rest(), bindings, inferences, name, participants, instantiation, storySequence, exclusions);
/* 555:    */   }
/* 556:    */   
/* 557:    */   public static Vector<Entity> connected(Entity instantiatedAntecedent, Entity instantiatedConsequent, Vector<Entity> inferences)
/* 558:    */   {
/* 559:643 */     boolean debug = false;
/* 560:644 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Checking to see if\n", instantiatedAntecedent.asStringWithIndexes(), "\nis connected to\n", instantiatedConsequent
/* 561:645 */       .asStringWithIndexes() });
/* 562:646 */     Vector<Vector<Entity>> queue = new Vector();
/* 563:647 */     Vector<Entity> extendedList = new Vector();
/* 564:    */     
/* 565:649 */     Mark.say(new Object[] {Boolean.valueOf(debug), "There are", Integer.valueOf(inferences.size()), "inferences" });
/* 566:653 */     for (Entity rule : inferences)
/* 567:    */     {
/* 568:654 */       Vector<Entity> antecedents = rule.getSubject().getElements();
/* 569:655 */       if (antecedents == null)
/* 570:    */       {
/* 571:656 */         Mark.err(new Object[] {"Strange lack of antecedents in", rule });
/* 572:    */       }
/* 573:659 */       else if (antecedents.contains(instantiatedAntecedent))
/* 574:    */       {
/* 575:660 */         Vector<Entity> path = new Vector();
/* 576:661 */         path.add(instantiatedAntecedent);
/* 577:662 */         path.add(rule.getObject());
/* 578:663 */         queue.add(path);
/* 579:    */       }
/* 580:    */     }
/* 581:667 */     while (!queue.isEmpty())
/* 582:    */     {
/* 583:668 */       Vector<Entity> path = (Vector)queue.firstElement();
/* 584:669 */       if (instantiatedConsequent == path.lastElement())
/* 585:    */       {
/* 586:670 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Antecedent is connected to consequent!" });
/* 587:    */         
/* 588:672 */         return path;
/* 589:    */       }
/* 590:674 */       queue.remove(0);
/* 591:675 */       Entity lastElement = (Entity)path.lastElement();
/* 592:676 */       if (!extendedList.contains(lastElement))
/* 593:    */       {
/* 594:680 */         extendedList.add(lastElement);
/* 595:    */         
/* 596:682 */         boolean more = false;
/* 597:    */         
/* 598:684 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Looking for", lastElement });
/* 599:685 */         for (int i = 0; i < inferences.size(); i++)
/* 600:    */         {
/* 601:686 */           Entity rule = (Entity)inferences.get(i);
/* 602:    */           
/* 603:688 */           Vector<Entity> antecedents = rule.getSubject().getElements();
/* 604:689 */           boolean test = antecedents.contains(lastElement);
/* 605:695 */           if (antecedents.contains(lastElement))
/* 606:    */           {
/* 607:696 */             Vector<Entity> newPath = new Vector();
/* 608:697 */             newPath.addAll(path);
/* 609:698 */             newPath.add(rule.getObject());
/* 610:699 */             queue.add(newPath);
/* 611:700 */             more = true;
/* 612:    */           }
/* 613:    */         }
/* 614:703 */         if (!more) {
/* 615:704 */           Mark.say(new Object[] {Boolean.valueOf(debug), "Cannot continue path" });
/* 616:    */         }
/* 617:    */       }
/* 618:    */     }
/* 619:708 */     return null;
/* 620:    */   }
/* 621:    */   
/* 622:    */   public boolean member(LList<PairOfEntities> first, LList<LList<PairOfEntities>> rest)
/* 623:    */   {
/* 624:712 */     for (Object b : rest)
/* 625:    */     {
/* 626:713 */       LList<PairOfEntities> bindingSet = (LList)b;
/* 627:714 */       if (equals(first, bindingSet)) {
/* 628:715 */         return true;
/* 629:    */       }
/* 630:    */     }
/* 631:718 */     return false;
/* 632:    */   }
/* 633:    */   
/* 634:    */   private boolean equals(LList<PairOfEntities> first, LList<PairOfEntities> second)
/* 635:    */   {
/* 636:722 */     if ((first.endP()) && (second.endP())) {
/* 637:723 */       return true;
/* 638:    */     }
/* 639:725 */     if ((first.endP()) || (second.endP())) {
/* 640:726 */       return false;
/* 641:    */     }
/* 642:728 */     if (((PairOfEntities)first.first()).equals(second.first())) {
/* 643:729 */       return equals(first.rest(), second.rest());
/* 644:    */     }
/* 645:731 */     return false;
/* 646:    */   }
/* 647:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     storyProcessor.ConceptExpert
 * JD-Core Version:    0.7.0.1
 */