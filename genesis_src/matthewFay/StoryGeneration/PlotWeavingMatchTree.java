/*   1:    */ package matthewFay.StoryGeneration;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import com.google.common.collect.Iterables;
/*   5:    */ import com.google.common.collect.MinMaxPriorityQueue;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Collections;
/*   8:    */ import java.util.Comparator;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Set;
/*  12:    */ import java.util.Vector;
/*  13:    */ import matthewFay.CharacterModeling.CharacterProcessor;
/*  14:    */ import matthewFay.Constraints.BindingInferenceUtility;
/*  15:    */ import matthewFay.Constraints.BindingSet;
/*  16:    */ import matthewFay.StoryAlignment.Aligner;
/*  17:    */ import matthewFay.StoryAlignment.NWSequenceAlignmentScorer;
/*  18:    */ import matthewFay.StoryAlignment.SequenceAlignment;
/*  19:    */ import matthewFay.StoryAlignment.SortableAlignmentList;
/*  20:    */ import matthewFay.Utilities.EntityHelper;
/*  21:    */ import matthewFay.Utilities.HashMatrix;
/*  22:    */ import matthewFay.Utilities.OrderedHashMatrix;
/*  23:    */ import matthewFay.representations.BasicCharacterModel;
/*  24:    */ import minilisp.LList;
/*  25:    */ import utils.Mark;
/*  26:    */ import utils.PairOfEntities;
/*  27:    */ 
/*  28:    */ public class PlotWeavingMatchTree
/*  29:    */ {
/*  30: 27 */   private static boolean debug_logging = true;
/*  31:    */   private List<BasicCharacterModel> characters;
/*  32: 31 */   private List<Entity> all_generic_entities = null;
/*  33: 32 */   private List<Entity> all_character_entities = null;
/*  34: 34 */   private OrderedHashMatrix<Entity, BasicCharacterModel, Float> generic_score_matrix = null;
/*  35: 35 */   private HashMatrix<Entity, Entity, List<PairOfEntities>> generic_constraint_matrix = null;
/*  36: 36 */   private OrderedHashMatrix<BasicCharacterModel, BasicCharacterModel, Float> score_matrix = null;
/*  37:    */   private MatchNode root_node;
/*  38:    */   private MinMaxPriorityQueue<MatchNode> queue;
/*  39:    */   private ArrayList<MatchNode> leaf_nodes;
/*  40:    */   private boolean leaf_found;
/*  41:    */   private float leaf_score;
/*  42: 46 */   private List<PairOfEntities> actual_bindings = new ArrayList();
/*  43:    */   
/*  44:    */   public PlotWeavingMatchTree(List<BasicCharacterModel> characters)
/*  45:    */   {
/*  46: 49 */     this.characters = characters;
/*  47:    */     
/*  48: 51 */     this.queue = MinMaxPriorityQueue.create();
/*  49: 52 */     this.leaf_nodes = new ArrayList();
/*  50:    */     
/*  51: 54 */     this.actual_bindings = obtainActualBindings();
/*  52:    */   }
/*  53:    */   
/*  54:    */   private void init()
/*  55:    */   {
/*  56: 58 */     this.leaf_nodes.clear();
/*  57: 59 */     this.leaf_found = false;
/*  58: 60 */     this.leaf_score = (1.0F / -1.0F);
/*  59:    */     
/*  60: 62 */     this.queue.clear();
/*  61:    */     
/*  62: 64 */     this.all_generic_entities = new ArrayList();
/*  63: 65 */     this.all_character_entities = new ArrayList();
/*  64: 66 */     for (BasicCharacterModel character : this.characters)
/*  65:    */     {
/*  66: 67 */       for (Entity generic_entity : character.getGenericEntities()) {
/*  67: 68 */         this.all_generic_entities.add(generic_entity);
/*  68:    */       }
/*  69: 70 */       this.all_character_entities.add(character.getEntity());
/*  70:    */     }
/*  71: 73 */     generateGenericEntitiesScoreMatrices(this.characters);
/*  72:    */     
/*  73: 75 */     doAlignmentScore(new ArrayList());
/*  74:    */     
/*  75: 77 */     this.root_node = new MatchNode();
/*  76: 78 */     this.root_node.score = (1.0F / 1.0F);
/*  77: 79 */     this.root_node.generic_entities = new ArrayList(this.all_generic_entities);
/*  78: 80 */     this.root_node.bindings = new ArrayList();
/*  79: 84 */     for (??? = this.characters.iterator(); ???.hasNext(); ???.hasNext())
/*  80:    */     {
/*  81: 84 */       BasicCharacterModel origin_character = (BasicCharacterModel)???.next();
/*  82: 85 */       ??? = origin_character.getGenericEntities().iterator(); continue;Entity generic_entity = (Entity)???.next();
/*  83: 86 */       Set<BasicCharacterModel> characters = this.generic_score_matrix.keySetCols(generic_entity);
/*  84: 87 */       if (characters.size() == 1)
/*  85:    */       {
/*  86: 88 */         BasicCharacterModel character = (BasicCharacterModel)Iterables.getOnlyElement(characters);
/*  87: 89 */         this.root_node.bindings.add(new PairOfEntities(generic_entity, character.getEntity()));
/*  88: 90 */         this.root_node.generic_entities.remove(generic_entity);
/*  89:    */       }
/*  90:    */     }
/*  91: 96 */     Mark.say(new Object[] {"Heuristic Bindings: \n" + this.root_node.bindings });
/*  92: 97 */     doAlignmentScore(this.root_node.bindings);
/*  93:    */     
/*  94: 99 */     this.queue.add(this.root_node);
/*  95:    */   }
/*  96:    */   
/*  97:    */   private List<PairOfEntities> obtainActualBindings()
/*  98:    */   {
/*  99:104 */     List<PairOfEntities> bindings = new ArrayList();
/* 100:    */     Iterator localIterator2;
/* 101:106 */     for (Iterator localIterator1 = this.characters.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 102:    */     {
/* 103:106 */       BasicCharacterModel character = (BasicCharacterModel)localIterator1.next();
/* 104:107 */       localIterator2 = character.getGenericEntities().iterator(); continue;Entity generic_entity = (Entity)localIterator2.next();
/* 105:108 */       bindings.add(new PairOfEntities(generic_entity, character.getReplacedEntity(generic_entity)));
/* 106:    */     }
/* 107:112 */     return bindings;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void generateMatchTree()
/* 111:    */   {
/* 112:116 */     init();
/* 113:119 */     if (debug_logging)
/* 114:    */     {
/* 115:123 */       Mark.say(new Object[] {"Target Bindings:" });
/* 116:124 */       Mark.say(new Object[] {this.actual_bindings });
/* 117:    */     }
/* 118:129 */     List<MatchNode> processed_nodes = new ArrayList();
/* 119:130 */     int node_count = 0;
/* 120:    */     Entity next_generic;
/* 121:132 */     while (!this.queue.isEmpty())
/* 122:    */     {
/* 123:133 */       MatchNode best_node = (MatchNode)this.queue.poll();
/* 124:    */       
/* 125:135 */       processed_nodes.add(best_node);
/* 126:136 */       node_count++;
/* 127:139 */       if (best_node.generic_entities.isEmpty())
/* 128:    */       {
/* 129:140 */         if (best_node.score >= this.leaf_score)
/* 130:    */         {
/* 131:141 */           if (best_node.score > this.leaf_score) {
/* 132:142 */             this.leaf_nodes.clear();
/* 133:    */           }
/* 134:143 */           if (!this.leaf_nodes.contains(best_node))
/* 135:    */           {
/* 136:144 */             this.leaf_nodes.add(best_node);
/* 137:145 */             this.leaf_found = true;
/* 138:146 */             this.leaf_score = best_node.score;
/* 139:147 */             break;
/* 140:    */           }
/* 141:    */         }
/* 142:    */       }
/* 143:    */       else
/* 144:    */       {
/* 145:153 */         if ((this.leaf_found) && 
/* 146:154 */           (best_node.score <= this.leaf_score))
/* 147:    */         {
/* 148:155 */           this.queue.clear();
/* 149:156 */           break;
/* 150:    */         }
/* 151:160 */         next_generic = (Entity)best_node.generic_entities.get(0);
/* 152:    */         
/* 153:    */ 
/* 154:163 */         MatchNode next_node = createNode(best_node, next_generic, new Entity("null"));
/* 155:165 */         if (next_node.score > best_node.score) {
/* 156:166 */           Mark.err(new Object[] {"Scores should never increase!" });
/* 157:    */         }
/* 158:170 */         if (next_node.score > 0.0F) {
/* 159:171 */           this.queue.add(next_node);
/* 160:    */         }
/* 161:174 */         List<BasicCharacterModel> ordered_target_characters = this.generic_score_matrix.getOrderedColKeySet(next_generic);
/* 162:176 */         for (BasicCharacterModel target_character : ordered_target_characters)
/* 163:    */         {
/* 164:177 */           if (((Float)this.generic_score_matrix.get(next_generic, target_character)).floatValue() <= 0.0F) {
/* 165:    */             break;
/* 166:    */           }
/* 167:179 */           if (possibleTargets(next_generic, best_node.bindings).contains(target_character))
/* 168:    */           {
/* 169:182 */             next_node = createNode(best_node, next_generic, target_character.getEntity());
/* 170:184 */             if (next_node.score > best_node.score) {
/* 171:185 */               Mark.err(new Object[] {"Scores should never increase!" });
/* 172:    */             }
/* 173:189 */             if (next_node.score > 0.0F) {
/* 174:190 */               this.queue.add(next_node);
/* 175:    */             }
/* 176:    */           }
/* 177:    */         }
/* 178:    */       }
/* 179:    */     }
/* 180:197 */     Mark.say(new Object[] {"Processed " + node_count + " nodes" });
/* 181:    */     
/* 182:199 */     Collections.sort(this.leaf_nodes);
/* 183:200 */     Mark.say(new Object[] {"Plot Weave Matching Results:" });
/* 184:201 */     for (MatchNode leaf_node : this.leaf_nodes)
/* 185:    */     {
/* 186:202 */       Collections.sort(leaf_node.bindings, new Comparator()
/* 187:    */       {
/* 188:    */         public int compare(PairOfEntities arg0, PairOfEntities arg1)
/* 189:    */         {
/* 190:206 */           String p0 = "null";
/* 191:207 */           if (arg0.getPattern() != null) {
/* 192:207 */             p0 = arg0.getPattern().getType();
/* 193:    */           }
/* 194:208 */           String p1 = "null";
/* 195:209 */           if (arg1.getPattern() != null) {
/* 196:209 */             p1 = arg1.getPattern().getType();
/* 197:    */           }
/* 198:210 */           int p = p0.compareTo(p1);
/* 199:211 */           if (p != 0) {
/* 200:211 */             return p;
/* 201:    */           }
/* 202:212 */           String d0 = "null";
/* 203:213 */           if (arg0.getDatum() != null) {
/* 204:213 */             d0 = arg0.getDatum().getType();
/* 205:    */           }
/* 206:214 */           String d1 = "null";
/* 207:215 */           if (arg1.getDatum() != null) {
/* 208:215 */             d1 = arg1.getDatum().getType();
/* 209:    */           }
/* 210:216 */           int d = d0.compareTo(d1);
/* 211:217 */           return d;
/* 212:    */         }
/* 213:220 */       });
/* 214:221 */       Mark.say(new Object[] {"Score: " + leaf_node.score + "=>" + leaf_node.bindings });
/* 215:222 */       Mark.say(new Object[] {"Validation: " });
/* 216:223 */       Mark.say(new Object[] {Float.valueOf(doAlignmentScore(leaf_node.bindings)) });
/* 217:    */     }
/* 218:    */   }
/* 219:    */   
/* 220:    */   private MatchNode createNode(MatchNode best_node, Entity next_generic, Entity target)
/* 221:    */   {
/* 222:228 */     MatchNode next_node = new MatchNode();
/* 223:    */     
/* 224:230 */     next_node.setParent(best_node);
/* 225:231 */     next_node.generic_entities = new ArrayList(best_node.generic_entities);
/* 226:232 */     next_node.generic_entities.remove(next_generic);
/* 227:    */     
/* 228:234 */     next_node.bindings = new ArrayList(best_node.bindings);
/* 229:    */     
/* 230:236 */     PairOfEntities new_binding = new PairOfEntities(next_generic, target);
/* 231:237 */     next_node.bindings.add(new_binding);
/* 232:    */     
/* 233:239 */     next_node.score = doAlignmentScore(next_node.bindings);
/* 234:    */     
/* 235:241 */     return next_node;
/* 236:    */   }
/* 237:    */   
/* 238:    */   private List<BasicCharacterModel> possibleTargets(Entity generic_entity, List<PairOfEntities> bindings)
/* 239:    */   {
/* 240:245 */     ArrayList<BasicCharacterModel> possible_targets = new ArrayList();
/* 241:    */     
/* 242:247 */     BasicCharacterModel originating_character = BasicCharacterModel.getOriginatingCharacter(generic_entity);
/* 243:248 */     List<Entity> sibling_generics = originating_character.getGenericEntities();
/* 244:249 */     List<BasicCharacterModel> sibling_targets = new ArrayList();
/* 245:250 */     for (PairOfEntities binding : bindings)
/* 246:    */     {
/* 247:251 */       Entity pattern = binding.getPattern();
/* 248:252 */       if (sibling_generics.contains(pattern))
/* 249:    */       {
/* 250:253 */         Entity datum = binding.getDatum();
/* 251:254 */         if ((!EntityHelper.isGeneric(datum)) && (!datum.entityP("null")))
/* 252:    */         {
/* 253:255 */           BasicCharacterModel target_character = CharacterProcessor.getCharacterModel(datum, false);
/* 254:256 */           if (target_character != null) {
/* 255:257 */             sibling_targets.add(target_character);
/* 256:    */           }
/* 257:    */         }
/* 258:    */       }
/* 259:    */     }
/* 260:261 */     for (BasicCharacterModel character : this.characters) {
/* 261:262 */       if ((character != originating_character) && 
/* 262:263 */         (!sibling_targets.contains(character))) {
/* 263:264 */         possible_targets.add(character);
/* 264:    */       }
/* 265:    */     }
/* 266:269 */     return possible_targets;
/* 267:    */   }
/* 268:    */   
/* 269:    */   private float doAlignmentScore(List<PairOfEntities> bindings)
/* 270:    */   {
/* 271:273 */     float total_score = 0.0F;
/* 272:    */     
/* 273:275 */     boolean record_results = false;
/* 274:276 */     if (this.score_matrix == null)
/* 275:    */     {
/* 276:277 */       this.score_matrix = new OrderedHashMatrix();
/* 277:278 */       record_results = true;
/* 278:    */     }
/* 279:281 */     BindingInferenceUtility biu = new BindingInferenceUtility(bindings);
/* 280:282 */     biu.inferTargetsFromChaining();
/* 281:283 */     biu.inferEquivalentGenerics();
/* 282:284 */     biu.requireBindingsFor(this.all_generic_entities);
/* 283:285 */     biu.addTargets(this.all_character_entities);
/* 284:286 */     List<PairOfEntities> all_bindings = biu.getTwoWayBindings();
/* 285:289 */     for (int i = 0; i < this.characters.size(); i++)
/* 286:    */     {
/* 287:290 */       BasicCharacterModel character_i = (BasicCharacterModel)this.characters.get(i);
/* 288:291 */       for (int j = i + 1; j < this.characters.size(); j++)
/* 289:    */       {
/* 290:292 */         BasicCharacterModel character_j = (BasicCharacterModel)this.characters.get(j);
/* 291:    */         
/* 292:294 */         List<Entity> character_i_plot = character_i.getGeneralizedCharacterStory();
/* 293:295 */         List<Entity> character_j_plot = character_j.getGeneralizedCharacterStory();
/* 294:    */         
/* 295:    */ 
/* 296:298 */         NWSequenceAlignmentScorer scorer = new NWSequenceAlignmentScorer(character_i_plot, character_j_plot);
/* 297:    */         
/* 298:300 */         SequenceAlignment alignment = scorer.align(all_bindings);
/* 299:    */         
/* 300:302 */         total_score += alignment.score;
/* 301:304 */         if (record_results)
/* 302:    */         {
/* 303:305 */           this.score_matrix.put(character_i, character_j, Float.valueOf(alignment.score));
/* 304:306 */           this.score_matrix.put(character_j, character_i, Float.valueOf(alignment.score));
/* 305:    */         }
/* 306:    */       }
/* 307:    */     }
/* 308:311 */     return total_score;
/* 309:    */   }
/* 310:    */   
/* 311:    */   private List<PairOfEntities> resolveBindingChaining(List<PairOfEntities> bindings)
/* 312:    */   {
/* 313:315 */     List<PairOfEntities> new_bindings = new ArrayList();
/* 314:    */     
/* 315:317 */     BindingSet bs = new BindingSet(bindings);
/* 316:319 */     for (PairOfEntities binding : bindings)
/* 317:    */     {
/* 318:323 */       Entity pattern = binding.getPattern();
/* 319:324 */       Entity datum = bs.getDatum(pattern);
/* 320:325 */       new_bindings.add(binding);
/* 321:327 */       if (EntityHelper.isGeneric(pattern))
/* 322:    */       {
/* 323:328 */         Entity chained_datum = bs.getDatum(datum);
/* 324:329 */         while (chained_datum != null)
/* 325:    */         {
/* 326:330 */           new_bindings.add(new PairOfEntities(pattern, chained_datum));
/* 327:331 */           chained_datum = bs.getDatum(chained_datum);
/* 328:    */         }
/* 329:    */       }
/* 330:    */     }
/* 331:336 */     return new_bindings;
/* 332:    */   }
/* 333:    */   
/* 334:    */   private List<PairOfEntities> filterBindings(List<PairOfEntities> all_bindings, List<Entity> patterns, List<Entity> datums)
/* 335:    */   {
/* 336:340 */     List<PairOfEntities> bindings = new ArrayList();
/* 337:342 */     for (PairOfEntities binding : all_bindings) {
/* 338:343 */       if ((patterns.contains(binding.getPattern())) || (datums.contains(binding.getDatum()))) {
/* 339:344 */         bindings.add(binding);
/* 340:    */       }
/* 341:    */     }
/* 342:347 */     return bindings;
/* 343:    */   }
/* 344:    */   
/* 345:    */   private List<PairOfEntities> getConstraints(Entity generic, Entity target)
/* 346:    */   {
/* 347:351 */     return getConstraints(generic, target, new ArrayList());
/* 348:    */   }
/* 349:    */   
/* 350:    */   private List<PairOfEntities> getConstraints(Entity generic, Entity target, List<PairOfEntities> checked)
/* 351:    */   {
/* 352:356 */     for (PairOfEntities check : checked) {
/* 353:357 */       if ((check.getPattern().equals(generic)) && (check.getDatum().equals(target))) {
/* 354:358 */         return new ArrayList();
/* 355:    */       }
/* 356:    */     }
/* 357:360 */     checked.add(new PairOfEntities(generic, target));
/* 358:    */     
/* 359:362 */     List<PairOfEntities> constraints = (List)this.generic_constraint_matrix.get(generic, target);
/* 360:    */     
/* 361:364 */     Object new_constraints = new ArrayList(constraints);
/* 362:365 */     for (PairOfEntities constraint : (List)new_constraints) {
/* 363:366 */       if (this.generic_constraint_matrix.contains(constraint.getPattern(), constraint.getDatum())) {
/* 364:367 */         constraints.addAll(getConstraints(constraint.getPattern(), constraint.getDatum(), checked));
/* 365:    */       }
/* 366:    */     }
/* 367:371 */     return constraints;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public List<PairOfEntities> getBestBindings()
/* 371:    */   {
/* 372:375 */     if (!this.leaf_found) {
/* 373:376 */       generateMatchTree();
/* 374:    */     }
/* 375:378 */     if (this.leaf_nodes.size() > 0) {
/* 376:379 */       return ((MatchNode)this.leaf_nodes.get(0)).bindings;
/* 377:    */     }
/* 378:380 */     Mark.err(new Object[] {"Unknown Error; No leaf nodes found!" });
/* 379:381 */     return new ArrayList();
/* 380:    */   }
/* 381:    */   
/* 382:    */   private BasicCharacterModel getCharacterModel(Entity character_entity)
/* 383:    */   {
/* 384:385 */     for (BasicCharacterModel character : this.characters) {
/* 385:386 */       if (character.getEntity().equals(character_entity)) {
/* 386:387 */         return character;
/* 387:    */       }
/* 388:    */     }
/* 389:389 */     return null;
/* 390:    */   }
/* 391:    */   
/* 392:    */   private void generateGenericEntitiesScoreMatrices(List<BasicCharacterModel> characters)
/* 393:    */   {
/* 394:393 */     this.generic_score_matrix = new OrderedHashMatrix();
/* 395:394 */     this.generic_constraint_matrix = new HashMatrix();
/* 396:    */     
/* 397:    */ 
/* 398:397 */     Aligner aligner = new Aligner();
/* 399:    */     Iterator localIterator2;
/* 400:400 */     for (Iterator localIterator1 = characters.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 401:    */     {
/* 402:400 */       BasicCharacterModel originating_character = (BasicCharacterModel)localIterator1.next();
/* 403:    */       
/* 404:    */ 
/* 405:403 */       localIterator2 = originating_character.getGenericEntities().iterator(); continue;Entity generic_entity = (Entity)localIterator2.next();
/* 406:404 */       List<BasicCharacterModel> possible_characters = new ArrayList(characters);
/* 407:405 */       possible_characters.remove(originating_character);
/* 408:    */       
/* 409:407 */       List<Entity> generics_plot = originating_character.getGeneralizedCharacterStory();
/* 410:408 */       generics_plot = filterPlotElts(generic_entity, generics_plot);
/* 411:411 */       for (BasicCharacterModel target_character : possible_characters)
/* 412:    */       {
/* 413:414 */         float score = 0.0F;
/* 414:415 */         List<Entity> targets_plot = target_character.getGeneralizedCharacterStory();
/* 415:    */         
/* 416:417 */         PairOfEntities new_pairing = new PairOfEntities(generic_entity, target_character.getEntity());
/* 417:418 */         LList<PairOfEntities> bindings = new LList(new_pairing);
/* 418:    */         
/* 419:    */ 
/* 420:421 */         SortableAlignmentList sal = aligner.align(generics_plot, targets_plot, bindings);
/* 421:423 */         if (sal.size() <= 0)
/* 422:    */         {
/* 423:424 */           score = (1.0F / -1.0F);
/* 424:    */         }
/* 425:    */         else
/* 426:    */         {
/* 427:428 */           SequenceAlignment alignment = (SequenceAlignment)sal.get(0);
/* 428:429 */           score = alignment.score;
/* 429:    */           
/* 430:    */ 
/* 431:432 */           List<PairOfEntities> constraints = filterBindingsToGenericsConstraints(alignment.bindings);
/* 432:434 */           for (PairOfEntities constraint : constraints) {
/* 433:435 */             if ((EntityHelper.isGeneric(constraint.getPattern())) && (!EntityHelper.isGeneric(constraint.getDatum())) && 
/* 434:436 */               (getCharacterModel(constraint.getDatum()) == null)) {
/* 435:437 */               score = (1.0F / -1.0F);
/* 436:    */             }
/* 437:    */           }
/* 438:442 */           if (score > 0.0F) {
/* 439:443 */             this.generic_constraint_matrix.put(generic_entity, target_character.getEntity(), constraints);
/* 440:    */           }
/* 441:    */         }
/* 442:446 */         if (score > 0.0F) {
/* 443:447 */           this.generic_score_matrix.put(generic_entity, target_character, Float.valueOf(score));
/* 444:    */         }
/* 445:    */       }
/* 446:    */     }
/* 447:    */   }
/* 448:    */   
/* 449:    */   private List<PairOfEntities> filterBindingsToGenericsConstraints(LList<PairOfEntities> bindings)
/* 450:    */   {
/* 451:454 */     List<PairOfEntities> constraints = new ArrayList();
/* 452:456 */     for (PairOfEntities binding : bindings) {
/* 453:457 */       if ((binding.getDatum() != null) && (binding.getPattern() != null)) {
/* 454:459 */         if ((!binding.getDatum().entityP("null")) && (!binding.getPattern().entityP("null"))) {
/* 455:461 */           if (EntityHelper.isGeneric(binding.getDatum())) {
/* 456:462 */             constraints.add(new PairOfEntities(binding.getDatum(), binding.getPattern()));
/* 457:465 */           } else if (EntityHelper.isGeneric(binding.getPattern())) {
/* 458:466 */             constraints.add(new PairOfEntities(binding.getPattern(), binding.getDatum()));
/* 459:    */           }
/* 460:    */         }
/* 461:    */       }
/* 462:    */     }
/* 463:471 */     return constraints;
/* 464:    */   }
/* 465:    */   
/* 466:    */   private List<Entity> filterPlotElts(Entity req_elt, List<Entity> plotElts)
/* 467:    */   {
/* 468:475 */     List<Entity> filteredPlotElts = new ArrayList();
/* 469:477 */     for (Entity plotElt : plotElts) {
/* 470:478 */       if (EntityHelper.contains(req_elt, plotElt)) {
/* 471:479 */         filteredPlotElts.add(plotElt);
/* 472:    */       }
/* 473:    */     }
/* 474:482 */     return filteredPlotElts;
/* 475:    */   }
/* 476:    */   
/* 477:    */   private class MatchNode
/* 478:    */     implements Comparable<MatchNode>
/* 479:    */   {
/* 480:    */     private MatchNode parent;
/* 481:489 */     private int depth = 0;
/* 482:    */     private Vector<MatchNode> children;
/* 483:    */     public List<PairOfEntities> bindings;
/* 484:    */     public List<Entity> generic_entities;
/* 485:    */     public float score;
/* 486:    */     
/* 487:    */     public void setParent(MatchNode node)
/* 488:    */     {
/* 489:494 */       if (this.parent == node) {
/* 490:494 */         return;
/* 491:    */       }
/* 492:496 */       if (this.parent != null) {
/* 493:497 */         this.parent.children.remove(this);
/* 494:    */       }
/* 495:500 */       node.children.add(this);
/* 496:501 */       this.parent = node;
/* 497:502 */       this.depth = (this.parent.depth + 1);
/* 498:    */     }
/* 499:    */     
/* 500:    */     public MatchNode()
/* 501:    */     {
/* 502:511 */       this.parent = null;
/* 503:512 */       this.children = new Vector();
/* 504:    */       
/* 505:514 */       this.generic_entities = new ArrayList();
/* 506:515 */       this.score = (1.0F / -1.0F);
/* 507:    */     }
/* 508:    */     
/* 509:    */     public int compareTo(MatchNode o)
/* 510:    */     {
/* 511:523 */       if (this.score > o.score) {
/* 512:523 */         return -1;
/* 513:    */       }
/* 514:524 */       if (this.score < o.score) {
/* 515:524 */         return 1;
/* 516:    */       }
/* 517:525 */       if (this.generic_entities.size() < o.generic_entities.size()) {
/* 518:525 */         return -1;
/* 519:    */       }
/* 520:526 */       if (this.generic_entities.size() > o.generic_entities.size()) {
/* 521:526 */         return 1;
/* 522:    */       }
/* 523:527 */       return 0;
/* 524:    */     }
/* 525:    */     
/* 526:    */     public boolean equals(Object o)
/* 527:    */     {
/* 528:532 */       if ((o instanceof MatchNode))
/* 529:    */       {
/* 530:533 */         MatchNode node = (MatchNode)o;
/* 531:534 */         if (this.score != node.score) {
/* 532:535 */           return false;
/* 533:    */         }
/* 534:536 */         if (this.bindings.size() != node.bindings.size()) {
/* 535:537 */           return false;
/* 536:    */         }
/* 537:538 */         for (PairOfEntities binding : this.bindings)
/* 538:    */         {
/* 539:539 */           boolean found_match = false;
/* 540:540 */           for (PairOfEntities constraint_check : node.bindings) {
/* 541:541 */             if ((binding.getDatum().equals(constraint_check.getDatum())) && 
/* 542:542 */               (binding.getPattern().equals(constraint_check.getPattern())))
/* 543:    */             {
/* 544:543 */               found_match = true;
/* 545:544 */               break;
/* 546:    */             }
/* 547:    */           }
/* 548:547 */           if (!found_match) {
/* 549:548 */             return false;
/* 550:    */           }
/* 551:    */         }
/* 552:550 */         return true;
/* 553:    */       }
/* 554:552 */       return false;
/* 555:    */     }
/* 556:    */     
/* 557:    */     public String toString()
/* 558:    */     {
/* 559:557 */       return this.bindings + ":" + this.score;
/* 560:    */     }
/* 561:    */   }
/* 562:    */   
/* 563:    */   private static void printGenericsMatrix(HashMatrix<Entity, BasicCharacterModel, Float> matrix, List<BasicCharacterModel> characters)
/* 564:    */   {
/* 565:    */     Iterator localIterator2;
/* 566:563 */     for (Iterator localIterator1 = characters.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 567:    */     {
/* 568:563 */       BasicCharacterModel character = (BasicCharacterModel)localIterator1.next();
/* 569:564 */       Mark.say(new Object[] {character.getEntity() });
/* 570:565 */       localIterator2 = character.getGenericEntities().iterator(); continue;Entity generic_entity = (Entity)localIterator2.next();
/* 571:566 */       for (BasicCharacterModel target_character : matrix.keySetCols(generic_entity)) {
/* 572:567 */         Mark.say(new Object[] {" - " + generic_entity + "=>" + target_character.getEntity() + "=" + matrix.get(generic_entity, target_character) });
/* 573:    */       }
/* 574:    */     }
/* 575:    */   }
/* 576:    */   
/* 577:    */   private static void printConstraintsMatrix(HashMatrix<Entity, Entity, List<PairOfEntities>> matrix, List<BasicCharacterModel> characters)
/* 578:    */   {
/* 579:    */     Iterator localIterator2;
/* 580:574 */     for (Iterator localIterator1 = characters.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 581:    */     {
/* 582:574 */       BasicCharacterModel character = (BasicCharacterModel)localIterator1.next();
/* 583:575 */       Mark.say(new Object[] {character.getEntity() });
/* 584:576 */       localIterator2 = character.getGenericEntities().iterator(); continue;Entity generic_entity = (Entity)localIterator2.next();
/* 585:577 */       for (Entity target_character : matrix.keySetCols(generic_entity)) {
/* 586:578 */         Mark.say(new Object[] {" - " + generic_entity + "=>" + target_character + "=" + matrix.get(generic_entity, target_character) });
/* 587:    */       }
/* 588:    */     }
/* 589:    */   }
/* 590:    */   
/* 591:    */   private static void printScoreMatrix(HashMatrix<BasicCharacterModel, BasicCharacterModel, Float> matrix, List<BasicCharacterModel> characters)
/* 592:    */   {
/* 593:    */     Iterator localIterator2;
/* 594:585 */     for (Iterator localIterator1 = characters.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 595:    */     {
/* 596:585 */       BasicCharacterModel character = (BasicCharacterModel)localIterator1.next();
/* 597:586 */       Mark.say(new Object[] {character.getEntity() });
/* 598:587 */       localIterator2 = matrix.keySetCols(character).iterator(); continue;BasicCharacterModel target_character = (BasicCharacterModel)localIterator2.next();
/* 599:588 */       Mark.say(new Object[] {" - " + character.getEntity() + ":" + target_character.getEntity() + "=" + matrix.get(character, target_character) });
/* 600:    */     }
/* 601:    */   }
/* 602:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.StoryGeneration.PlotWeavingMatchTree
 * JD-Core Version:    0.7.0.1
 */