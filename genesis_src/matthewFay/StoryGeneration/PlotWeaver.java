/*   1:    */ package matthewFay.StoryGeneration;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Collections;
/*   7:    */ import java.util.Comparator;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.HashSet;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import java.util.Set;
/*  14:    */ import javax.swing.JCheckBox;
/*  15:    */ import matthewFay.CharacterModeling.CharacterProcessor;
/*  16:    */ import matthewFay.StoryAlignment.Aligner;
/*  17:    */ import matthewFay.StoryAlignment.SequenceAlignment;
/*  18:    */ import matthewFay.StoryAlignment.SortableAlignmentList;
/*  19:    */ import matthewFay.Utilities.EntityHelper;
/*  20:    */ import matthewFay.Utilities.HashMatrix;
/*  21:    */ import matthewFay.Utilities.OrderedHashMatrix;
/*  22:    */ import matthewFay.Utilities.Pair;
/*  23:    */ import matthewFay.representations.BasicCharacterModel;
/*  24:    */ import matthewFay.viewers.CharacterViewer;
/*  25:    */ import minilisp.LList;
/*  26:    */ import start.Generator;
/*  27:    */ import translator.Translator;
/*  28:    */ import utils.Mark;
/*  29:    */ import utils.PairOfEntities;
/*  30:    */ 
/*  31:    */ public class PlotWeaver
/*  32:    */ {
/*  33: 38 */   public static boolean debug_logging = false;
/*  34:    */   
/*  35:    */   public static boolean isWeaveCharactersEvent(Entity event)
/*  36:    */   {
/*  37: 40 */     if ((event.relationP("weave")) && 
/*  38: 41 */       (event.getSubject().entityP("you")) && 
/*  39: 42 */       (event.getObject().sequenceP("roles")) && 
/*  40: 43 */       (event.getObject().getElement(0).functionP("object")) && 
/*  41: 44 */       (event.getObject().getElement(0).getSubject().entityP("plots")) && 
/*  42: 45 */       (event.getObject().getElement(1).functionP("for")) && 
/*  43: 46 */       (event.getObject().getElement(1).getSubject().entityP("character"))) {
/*  44: 47 */       return true;
/*  45:    */     }
/*  46: 55 */     return false;
/*  47:    */   }
/*  48:    */   
/*  49: 58 */   private boolean do_gap_filling = true;
/*  50:    */   
/*  51:    */   public void setDoGapFilling(boolean value)
/*  52:    */   {
/*  53: 60 */     this.do_gap_filling = value;
/*  54:    */   }
/*  55:    */   
/*  56: 63 */   private boolean purge_null_generics = true;
/*  57:    */   private List<BasicCharacterModel> characters;
/*  58:    */   
/*  59:    */   public void setPurgeNullGenerics(boolean value)
/*  60:    */   {
/*  61: 65 */     this.purge_null_generics = value;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean isCharacter(Entity e, List<BasicCharacterModel> characters)
/*  65:    */   {
/*  66: 70 */     for (BasicCharacterModel bcm : characters) {
/*  67: 71 */       if (bcm.getEntity().isEqual(e)) {
/*  68: 72 */         return true;
/*  69:    */       }
/*  70:    */     }
/*  71: 74 */     return false;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public BasicCharacterModel getCharacter(Entity e, List<BasicCharacterModel> character)
/*  75:    */   {
/*  76: 77 */     for (BasicCharacterModel bcm : this.characters) {
/*  77: 78 */       if (bcm.getEntity().isEqual(e)) {
/*  78: 79 */         return bcm;
/*  79:    */       }
/*  80:    */     }
/*  81: 81 */     return null;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public List<Entity> filterPlotElts(Entity req_elt, List<Entity> plotElts)
/*  85:    */   {
/*  86: 85 */     List<Entity> filteredPlotElts = new ArrayList();
/*  87: 87 */     for (Entity plotElt : plotElts) {
/*  88: 88 */       if (EntityHelper.contains(req_elt, plotElt)) {
/*  89: 89 */         filteredPlotElts.add(plotElt);
/*  90:    */       }
/*  91:    */     }
/*  92: 92 */     return filteredPlotElts;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public LList<PairOfEntities> filterBindingsToConstraints(LList<PairOfEntities> bindings, Entity e)
/*  96:    */   {
/*  97: 96 */     LList<PairOfEntities> constraints = new LList();
/*  98: 98 */     for (PairOfEntities binding : bindings) {
/*  99: 99 */       if ((binding.getDatum() != null) && (binding.getPattern() != null)) {
/* 100:101 */         if ((!binding.getDatum().entityP("null")) && (!binding.getPattern().entityP("null"))) {
/* 101:103 */           if (!binding.getPattern().equals(e)) {
/* 102:105 */             if ((EntityHelper.isGeneric(binding.getDatum())) || (EntityHelper.isGeneric(binding.getPattern()))) {
/* 103:106 */               constraints = constraints.cons(binding);
/* 104:    */             }
/* 105:    */           }
/* 106:    */         }
/* 107:    */       }
/* 108:    */     }
/* 109:109 */     return constraints;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public float scoreGenericWithTarget(Entity generic_entity, List<Entity> generic_entitys_plot, BasicCharacterModel target)
/* 113:    */   {
/* 114:113 */     Aligner aligner = new Aligner();
/* 115:    */     
/* 116:115 */     List<Entity> targets_plot = target.getGeneralizedCharacterStory();
/* 117:    */     
/* 118:117 */     PairOfEntities new_pairing = new PairOfEntities(generic_entity, target.getEntity());
/* 119:118 */     LList<PairOfEntities> bindings = new LList(new_pairing);
/* 120:    */     
/* 121:120 */     SortableAlignmentList sal = aligner.align(generic_entitys_plot, targets_plot, bindings);
/* 122:122 */     if (sal.size() <= 0) {
/* 123:123 */       return (1.0F / -1.0F);
/* 124:    */     }
/* 125:126 */     SequenceAlignment alignment = (SequenceAlignment)sal.get(0);
/* 126:    */     
/* 127:128 */     return alignment.score;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public OrderedHashMatrix<Entity, BasicCharacterModel, Float> generateGenericEntitiesScoreMatrix(List<BasicCharacterModel> characters)
/* 131:    */   {
/* 132:132 */     OrderedHashMatrix<Entity, BasicCharacterModel, Float> matrix = new OrderedHashMatrix();
/* 133:    */     Iterator localIterator2;
/* 134:135 */     for (Iterator localIterator1 = characters.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 135:    */     {
/* 136:135 */       BasicCharacterModel originating_character = (BasicCharacterModel)localIterator1.next();
/* 137:    */       
/* 138:    */ 
/* 139:138 */       localIterator2 = originating_character.getGenericEntities().iterator(); continue;Entity generic_entity = (Entity)localIterator2.next();
/* 140:139 */       List<BasicCharacterModel> possible_characters = new ArrayList(characters);
/* 141:140 */       possible_characters.remove(originating_character);
/* 142:    */       
/* 143:142 */       List<Entity> generics_plot = originating_character.getGeneralizedCharacterStory();
/* 144:143 */       generics_plot = filterPlotElts(generic_entity, generics_plot);
/* 145:146 */       for (BasicCharacterModel target_character : possible_characters)
/* 146:    */       {
/* 147:149 */         float score = scoreGenericWithTarget(generic_entity, generics_plot, target_character);
/* 148:150 */         matrix.put(generic_entity, target_character, Float.valueOf(score));
/* 149:    */       }
/* 150:    */     }
/* 151:155 */     return matrix;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public Map<Entity, BasicCharacterModel> findBestGenericTargets(List<BasicCharacterModel> characters, OrderedHashMatrix<Entity, BasicCharacterModel, Float> generic_entity_score_matrix)
/* 155:    */   {
/* 156:159 */     Map<Entity, BasicCharacterModel> generic_entity_to_target_character = new HashMap();
/* 157:    */     
/* 158:    */ 
/* 159:162 */     Map<Entity, Float> generic_entity_to_target_score = new HashMap();
/* 160:    */     boolean satisfied_all_generics;
/* 161:163 */     for (Iterator localIterator1 = characters.iterator(); localIterator1.hasNext(); !satisfied_all_generics)
/* 162:    */     {
/* 163:163 */       BasicCharacterModel originating_character = (BasicCharacterModel)localIterator1.next();
/* 164:    */       
/* 165:165 */       Map<BasicCharacterModel, Entity> target_character_to_generic_entity = new HashMap();
/* 166:    */       
/* 167:167 */       satisfied_all_generics = false;
/* 168:168 */       continue;
/* 169:169 */       satisfied_all_generics = true;
/* 170:170 */       boolean change_detected = false;
/* 171:    */       Iterator localIterator3;
/* 172:171 */       for (Iterator localIterator2 = originating_character.getGenericEntities().iterator(); localIterator2.hasNext(); localIterator3.hasNext())
/* 173:    */       {
/* 174:171 */         Entity generic_entity = (Entity)localIterator2.next();
/* 175:172 */         localIterator3 = generic_entity_score_matrix.keySetCols(generic_entity).iterator(); continue;BasicCharacterModel target_character = (BasicCharacterModel)localIterator3.next();
/* 176:173 */         float score = ((Float)generic_entity_score_matrix.get(generic_entity, target_character)).floatValue();
/* 177:174 */         if ((score > 0.0F) && (
/* 178:175 */           (!generic_entity_to_target_character.containsKey(generic_entity)) || 
/* 179:176 */           (score > ((Float)generic_entity_to_target_score.get(generic_entity)).floatValue()))) {
/* 180:178 */           if (!target_character_to_generic_entity.containsKey(target_character))
/* 181:    */           {
/* 182:179 */             if (generic_entity_to_target_character.containsKey(generic_entity)) {
/* 183:180 */               target_character_to_generic_entity.remove(generic_entity_to_target_character.get(generic_entity));
/* 184:    */             }
/* 185:181 */             generic_entity_to_target_character.put(generic_entity, target_character);
/* 186:182 */             target_character_to_generic_entity.put(target_character, generic_entity);
/* 187:183 */             generic_entity_to_target_score.put(generic_entity, Float.valueOf(score));
/* 188:184 */             change_detected = true;
/* 189:    */           }
/* 190:    */           else
/* 191:    */           {
/* 192:186 */             Entity conflicting_generic_entity = (Entity)target_character_to_generic_entity.get(target_character);
/* 193:187 */             float conflicting_score = ((Float)generic_entity_score_matrix.get(conflicting_generic_entity, target_character)).floatValue();
/* 194:188 */             Mark.say(new Object[] {Boolean.valueOf(debug_logging), generic_entity + " wants " + target_character + "with score " + score + ", but " + conflicting_generic_entity + " has it with score " + conflicting_score });
/* 195:190 */             if (score > conflicting_score)
/* 196:    */             {
/* 197:191 */               generic_entity_to_target_character.put(generic_entity, target_character);
/* 198:192 */               target_character_to_generic_entity.put(target_character, generic_entity);
/* 199:193 */               generic_entity_to_target_score.put(generic_entity, Float.valueOf(score));
/* 200:194 */               change_detected = true;
/* 201:    */               
/* 202:    */ 
/* 203:197 */               generic_entity_to_target_character.remove(conflicting_generic_entity);
/* 204:198 */               generic_entity_to_target_score.remove(conflicting_generic_entity);
/* 205:199 */               satisfied_all_generics = false;
/* 206:    */             }
/* 207:    */           }
/* 208:    */         }
/* 209:    */       }
/* 210:205 */       if ((!satisfied_all_generics) && (!change_detected))
/* 211:    */       {
/* 212:206 */         Mark.err(new Object[] {"infiloop" });
/* 213:207 */         System.exit(1);
/* 214:    */       }
/* 215:    */     }
/* 216:212 */     return generic_entity_to_target_character;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public List<PairOfEntities> mapToBindingsList(Map<Entity, BasicCharacterModel> generic_entity_to_target_character)
/* 220:    */   {
/* 221:216 */     List<PairOfEntities> bindings = new ArrayList();
/* 222:217 */     Mark.say(new Object[] {Boolean.valueOf(debug_logging), "Generic to Character mappings" });
/* 223:219 */     for (Entity e : generic_entity_to_target_character.keySet())
/* 224:    */     {
/* 225:220 */       Mark.say(new Object[] {Boolean.valueOf(debug_logging), e + " maps to " + ((BasicCharacterModel)generic_entity_to_target_character.get(e)).getEntity() });
/* 226:221 */       bindings.add(new PairOfEntities(e, ((BasicCharacterModel)generic_entity_to_target_character.get(e)).getEntity()));
/* 227:    */     }
/* 228:223 */     return bindings;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public Map<Entity, BasicCharacterModel> bindingsListToMap(List<PairOfEntities> bindings, List<BasicCharacterModel> characters)
/* 232:    */   {
/* 233:227 */     Map<Entity, BasicCharacterModel> generic_entity_to_target_character = new HashMap();
/* 234:229 */     for (PairOfEntities binding : bindings)
/* 235:    */     {
/* 236:230 */       Entity generic = binding.getPattern();
/* 237:231 */       Entity target = binding.getDatum();
/* 238:233 */       for (BasicCharacterModel character : characters) {
/* 239:234 */         if (character.getEntity().equals(target))
/* 240:    */         {
/* 241:235 */           generic_entity_to_target_character.put(generic, character);
/* 242:236 */           break;
/* 243:    */         }
/* 244:    */       }
/* 245:    */     }
/* 246:241 */     return generic_entity_to_target_character;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void normalizeScoreMatrixOnGenerics(HashMatrix<Entity, BasicCharacterModel, Float> matrix)
/* 250:    */   {
/* 251:245 */     List<Entity> generic_entities = new ArrayList(matrix.keySetRows());
/* 252:246 */     for (Entity generic_entity : generic_entities)
/* 253:    */     {
/* 254:247 */       List<BasicCharacterModel> target_characters = new ArrayList(matrix.keySetCols(generic_entity));
/* 255:248 */       float min = (1.0F / 1.0F);
/* 256:249 */       float max = (1.0F / -1.0F);
/* 257:    */       
/* 258:251 */       float sumsqs = 0.0F;
/* 259:253 */       for (BasicCharacterModel target_character : target_characters)
/* 260:    */       {
/* 261:254 */         float score = ((Float)matrix.get(generic_entity, target_character)).floatValue();
/* 262:    */         
/* 263:256 */         sumsqs += score * score;
/* 264:    */         
/* 265:258 */         min = min < score ? min : score;
/* 266:259 */         max = max > score ? max : score;
/* 267:    */       }
/* 268:261 */       float range = max - min;
/* 269:    */       
/* 270:263 */       float norm = (float)Math.sqrt(sumsqs);
/* 271:264 */       if (norm != 0.0F) {
/* 272:265 */         for (BasicCharacterModel target_character : target_characters)
/* 273:    */         {
/* 274:266 */           float score = ((Float)matrix.get(generic_entity, target_character)).floatValue();
/* 275:267 */           float normal_score = score / norm;
/* 276:268 */           matrix.put(generic_entity, target_character, Float.valueOf(normal_score));
/* 277:    */         }
/* 278:    */       }
/* 279:    */     }
/* 280:    */   }
/* 281:    */   
/* 282:    */   public PlotWeaver(List<BasicCharacterModel> characters)
/* 283:    */   {
/* 284:275 */     this.characters = characters;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public List<Entity> weavePlots()
/* 288:    */   {
/* 289:279 */     List<Entity> wovenPlotElts = new ArrayList();
/* 290:    */     
/* 291:281 */     OrderedHashMatrix<Entity, BasicCharacterModel, Float> generic_entity_score_matrix = generateGenericEntitiesScoreMatrix(this.characters);
/* 292:    */     
/* 293:    */ 
/* 294:    */ 
/* 295:285 */     Map<Entity, BasicCharacterModel> generic_entity_to_target_character = findBestGenericTargets(this.characters, generic_entity_score_matrix);
/* 296:    */     
/* 297:287 */     List<PairOfEntities> bindings = mapToBindingsList(generic_entity_to_target_character);
/* 298:    */     
/* 299:289 */     PlotWeavingMatchTree pwmt = new PlotWeavingMatchTree(this.characters);
/* 300:290 */     pwmt.generateMatchTree();
/* 301:291 */     bindings = pwmt.getBestBindings();
/* 302:292 */     generic_entity_to_target_character = bindingsListToMap(bindings, this.characters);
/* 303:    */     
/* 304:    */ 
/* 305:295 */     HashMap<BasicCharacterModel, List<Entity>> character_plot_threads = new HashMap();
/* 306:    */     List<Entity> plot_elts;
/* 307:    */     Iterator localIterator2;
/* 308:296 */     for (Iterator localIterator1 = this.characters.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 309:    */     {
/* 310:296 */       BasicCharacterModel character = (BasicCharacterModel)localIterator1.next();
/* 311:297 */       character_plot_threads.put(character, new ArrayList());
/* 312:298 */       plot_elts = character.getGeneralizedCharacterStory();
/* 313:299 */       localIterator2 = plot_elts.iterator(); continue;Entity gen_plot_elt = (Entity)localIterator2.next();
/* 314:300 */       Entity plot_elt = gen_plot_elt.deepClone(false);
/* 315:    */       
/* 316:    */ 
/* 317:303 */       boolean null_generic_detected = false;
/* 318:304 */       List<Entity> entities = EntityHelper.getAllEntities(plot_elt);
/* 319:305 */       for (Entity entity : entities) {
/* 320:306 */         if ((EntityHelper.isGeneric(entity)) && 
/* 321:307 */           (!generic_entity_to_target_character.containsKey(entity))) {
/* 322:308 */           null_generic_detected = true;
/* 323:    */         }
/* 324:    */       }
/* 325:313 */       if ((!this.purge_null_generics) || (!null_generic_detected)) {
/* 326:314 */         ((List)character_plot_threads.get(character)).add(plot_elt);
/* 327:    */       }
/* 328:    */     }
/* 329:    */     Object originating_character;
/* 330:    */     int t_index;
/* 331:319 */     if (this.do_gap_filling)
/* 332:    */     {
/* 333:320 */       Aligner aligner = new Aligner();
/* 334:322 */       for (plot_elts = bindings.iterator(); plot_elts.hasNext();)
/* 335:    */       {
/* 336:322 */         binding = (PairOfEntities)plot_elts.next();
/* 337:    */         
/* 338:324 */         Entity generic_entity = ((PairOfEntities)binding).getPattern();
/* 339:    */         
/* 340:326 */         originating_character = BasicCharacterModel.getOriginatingCharacter(generic_entity);
/* 341:327 */         BasicCharacterModel target_character = (BasicCharacterModel)generic_entity_to_target_character.get(generic_entity);
/* 342:329 */         if ((character_plot_threads.containsKey(originating_character)) && (character_plot_threads.containsKey(target_character)))
/* 343:    */         {
/* 344:330 */           List<Entity> originating_plot = (List)character_plot_threads.get(originating_character);
/* 345:331 */           List<Entity> target_plot = (List)character_plot_threads.get(target_character);
/* 346:    */           
/* 347:333 */           LList<PairOfEntities> temp_binding_list = new LList(binding);
/* 348:334 */           SequenceAlignment sa = (SequenceAlignment)aligner.align(originating_plot, target_plot, temp_binding_list).get(0);
/* 349:    */           
/* 350:336 */           sa.selectiveFillGaps(generic_entity);
/* 351:    */           
/* 352:338 */           t_index = 0;
/* 353:339 */           int a_index = 0;
/* 354:340 */           while (a_index < sa.size())
/* 355:    */           {
/* 356:341 */             Entity target_aligned_plot_elt = (Entity)((Pair)sa.get(a_index)).b;
/* 357:342 */             if (target_aligned_plot_elt != null)
/* 358:    */             {
/* 359:343 */               if ((t_index >= target_plot.size()) || (!target_aligned_plot_elt.equals(target_plot.get(t_index)))) {
/* 360:344 */                 target_plot.add(target_aligned_plot_elt);
/* 361:    */               }
/* 362:346 */               t_index++;
/* 363:    */             }
/* 364:348 */             a_index++;
/* 365:    */           }
/* 366:    */         }
/* 367:    */         else
/* 368:    */         {
/* 369:352 */           Mark.err(new Object[] {"Impossible Error! \n >>" + generic_entity + " from " + originating_character + " to " + target_character });
/* 370:    */         }
/* 371:    */       }
/* 372:    */     }
/* 373:359 */     for (Object binding = this.characters.iterator(); ((Iterator)binding).hasNext(); ((Iterator)originating_character).hasNext())
/* 374:    */     {
/* 375:359 */       BasicCharacterModel character = (BasicCharacterModel)((Iterator)binding).next();
/* 376:360 */       List<Entity> plot_elts = (List)character_plot_threads.get(character);
/* 377:361 */       originating_character = plot_elts.iterator(); continue;Entity plot_elt = (Entity)((Iterator)originating_character).next();
/* 378:362 */       EntityHelper.findAndReplace(plot_elt, bindings, true);
/* 379:    */     }
/* 380:    */     List<Entity> plot_elts;
/* 381:367 */     for (binding = character_plot_threads.keySet().iterator(); ((Iterator)binding).hasNext(); ((Iterator)originating_character).hasNext())
/* 382:    */     {
/* 383:367 */       BasicCharacterModel character = (BasicCharacterModel)((Iterator)binding).next();
/* 384:368 */       plot_elts = (List)character_plot_threads.get(character);
/* 385:369 */       Mark.say(new Object[] {Boolean.valueOf(debug_logging), "Pre-weaving plot for " + character.getEntity() });
/* 386:370 */       originating_character = plot_elts.iterator(); continue;Entity plot_elt = (Entity)((Iterator)originating_character).next();
/* 387:371 */       Mark.say(new Object[] {Boolean.valueOf(debug_logging), plot_elt });
/* 388:    */     }
/* 389:377 */     HashMap<BasicCharacterModel, Integer> character_plot_index = new HashMap();
/* 390:378 */     for (BasicCharacterModel character : this.characters) {
/* 391:379 */       character_plot_index.put(character, Integer.valueOf(0));
/* 392:    */     }
/* 393:384 */     boolean all_done = false;
/* 394:385 */     while (!all_done)
/* 395:    */     {
/* 396:387 */       boolean blocked = true;
/* 397:389 */       for (originating_character = character_plot_index.keySet().iterator(); ((Iterator)originating_character).hasNext();)
/* 398:    */       {
/* 399:389 */         BasicCharacterModel cur_character = (BasicCharacterModel)((Iterator)originating_character).next();
/* 400:390 */         int cpi = ((Integer)character_plot_index.get(cur_character)).intValue();
/* 401:391 */         if (cpi < ((List)character_plot_threads.get(cur_character)).size())
/* 402:    */         {
/* 403:393 */           Entity next_plot_elt = (Entity)((List)character_plot_threads.get(cur_character)).get(cpi);
/* 404:394 */           Set<BasicCharacterModel> inc_characters = prune_to_characters(EntityHelper.getAllEntities(next_plot_elt), this.characters);
/* 405:    */           
/* 406:396 */           boolean nonblocking = true;
/* 407:397 */           if (inc_characters.size() > 1) {
/* 408:398 */             nonblocking = false;
/* 409:    */           }
/* 410:401 */           if (nonblocking)
/* 411:    */           {
/* 412:402 */             cpi++;
/* 413:403 */             character_plot_index.put(cur_character, Integer.valueOf(cpi));
/* 414:404 */             wovenPlotElts.add(next_plot_elt);
/* 415:405 */             blocked = false;
/* 416:    */           }
/* 417:    */         }
/* 418:    */       }
/* 419:    */       int elt_count;
/* 420:411 */       if (blocked) {
/* 421:412 */         for (originating_character = character_plot_threads.keySet().iterator(); ((Iterator)originating_character).hasNext();)
/* 422:    */         {
/* 423:412 */           BasicCharacterModel cur_character = (BasicCharacterModel)((Iterator)originating_character).next();
/* 424:413 */           int cpi = ((Integer)character_plot_index.get(cur_character)).intValue();
/* 425:414 */           if (cpi < ((List)character_plot_threads.get(cur_character)).size())
/* 426:    */           {
/* 427:416 */             Entity blocked_plot_elt = (Entity)((List)character_plot_threads.get(cur_character)).get(cpi);
/* 428:417 */             Set<BasicCharacterModel> inc_characters = prune_to_characters(EntityHelper.getAllEntities(blocked_plot_elt), this.characters);
/* 429:418 */             elt_count = 0;
/* 430:419 */             for (BasicCharacterModel inc_character : inc_characters)
/* 431:    */             {
/* 432:420 */               int inc_cpi = ((Integer)character_plot_index.get(inc_character)).intValue();
/* 433:421 */               if (inc_cpi < ((List)character_plot_threads.get(inc_character)).size())
/* 434:    */               {
/* 435:423 */                 Entity inc_character_plot_elt = (Entity)((List)character_plot_threads.get(inc_character)).get(inc_cpi);
/* 436:424 */                 if (blocked_plot_elt.isDeepEqual(inc_character_plot_elt)) {
/* 437:425 */                   elt_count++;
/* 438:    */                 }
/* 439:    */               }
/* 440:    */             }
/* 441:428 */             if (elt_count == inc_characters.size())
/* 442:    */             {
/* 443:430 */               wovenPlotElts.add(blocked_plot_elt);
/* 444:432 */               for (BasicCharacterModel inc_character : inc_characters)
/* 445:    */               {
/* 446:433 */                 int inc_cpi = ((Integer)character_plot_index.get(inc_character)).intValue();
/* 447:434 */                 inc_cpi++;
/* 448:435 */                 character_plot_index.put(inc_character, Integer.valueOf(inc_cpi));
/* 449:    */               }
/* 450:437 */               blocked = false;
/* 451:438 */               break;
/* 452:    */             }
/* 453:    */           }
/* 454:    */         }
/* 455:    */       }
/* 456:444 */       if (blocked)
/* 457:    */       {
/* 458:445 */         Mark.err(new Object[] {"-----Blocked during plot weaving!" });
/* 459:446 */         Mark.say(new Object[] {"-----Blocked on:" });
/* 460:447 */         for (originating_character = character_plot_index.keySet().iterator(); ((Iterator)originating_character).hasNext();)
/* 461:    */         {
/* 462:447 */           BasicCharacterModel blocked_character = (BasicCharacterModel)((Iterator)originating_character).next();
/* 463:448 */           int cpi = ((Integer)character_plot_index.get(blocked_character)).intValue();
/* 464:449 */           if (cpi < ((List)character_plot_threads.get(blocked_character)).size())
/* 465:    */           {
/* 466:451 */             Entity next_plot_elt = (Entity)((List)character_plot_threads.get(blocked_character)).get(cpi);
/* 467:452 */             Mark.say(new Object[] {blocked_character.getEntity() });
/* 468:453 */             Mark.say(new Object[] {next_plot_elt });
/* 469:454 */             for (elt_count = blocked_character.getGenericEntities().iterator(); elt_count.hasNext(); t_index.hasNext())
/* 470:    */             {
/* 471:454 */               Entity generic_entity = (Entity)elt_count.next();
/* 472:455 */               Mark.say(new Object[] {"From: " + blocked_character + " - " + generic_entity + "=>" + (
/* 473:456 */                 generic_entity_to_target_character.containsKey(generic_entity) ? 
/* 474:457 */                 ((BasicCharacterModel)generic_entity_to_target_character.get(generic_entity)).getEntity() + "=" + generic_entity_score_matrix.get(generic_entity, (BasicCharacterModel)generic_entity_to_target_character.get(generic_entity)) : 
/* 475:458 */                 "null") });
/* 476:    */               
/* 477:460 */               t_index = generic_entity_score_matrix.keySetCols(generic_entity).iterator(); continue;BasicCharacterModel target_character = (BasicCharacterModel)t_index.next();
/* 478:461 */               Mark.say(new Object[] {" - " + generic_entity + "=>" + target_character.getEntity() + "=" + generic_entity_score_matrix.get(generic_entity, target_character) });
/* 479:    */             }
/* 480:    */           }
/* 481:    */         }
/* 482:466 */         Mark.say(new Object[] {"-----End of Blocking Error" });
/* 483:467 */         all_done = true;
/* 484:468 */         break;
/* 485:    */       }
/* 486:472 */       all_done = true;
/* 487:473 */       for (originating_character = character_plot_index.keySet().iterator(); ((Iterator)originating_character).hasNext();)
/* 488:    */       {
/* 489:473 */         BasicCharacterModel character = (BasicCharacterModel)((Iterator)originating_character).next();
/* 490:474 */         int cpi = ((Integer)character_plot_index.get(character)).intValue();
/* 491:475 */         if (cpi < ((List)character_plot_threads.get(character)).size())
/* 492:    */         {
/* 493:476 */           all_done = false;
/* 494:477 */           break;
/* 495:    */         }
/* 496:    */       }
/* 497:    */     }
/* 498:482 */     return wovenPlotElts;
/* 499:    */   }
/* 500:    */   
/* 501:    */   private List<BasicCharacterModel> sort_characters_by_plot_length(List<BasicCharacterModel> characters)
/* 502:    */   {
/* 503:486 */     List<BasicCharacterModel> sorted_characters = new ArrayList(characters);
/* 504:487 */     Collections.sort(sorted_characters, new Comparator()
/* 505:    */     {
/* 506:    */       public int compare(BasicCharacterModel arg0, BasicCharacterModel arg1)
/* 507:    */       {
/* 508:492 */         int s0 = arg0.getParticipantEvents().size();
/* 509:493 */         int s1 = arg1.getParticipantEvents().size();
/* 510:    */         
/* 511:495 */         int r = s1 - s0;
/* 512:    */         
/* 513:    */ 
/* 514:498 */         return r;
/* 515:    */       }
/* 516:502 */     });
/* 517:503 */     return sorted_characters;
/* 518:    */   }
/* 519:    */   
/* 520:    */   private Set<BasicCharacterModel> prune_to_characters(List<Entity> entities, List<BasicCharacterModel> characters)
/* 521:    */   {
/* 522:507 */     Set<BasicCharacterModel> just_characters = new HashSet();
/* 523:508 */     for (Entity e : entities) {
/* 524:509 */       for (BasicCharacterModel c : characters) {
/* 525:510 */         if (e.isEqual(c.getEntity()))
/* 526:    */         {
/* 527:511 */           just_characters.add(c);
/* 528:512 */           break;
/* 529:    */         }
/* 530:    */       }
/* 531:    */     }
/* 532:516 */     return just_characters;
/* 533:    */   }
/* 534:    */   
/* 535:    */   public static void main(String[] args)
/* 536:    */     throws Exception
/* 537:    */   {
/* 538:520 */     String[] plot = {
/* 539:521 */       "John is a person.", 
/* 540:522 */       "Mary is a person.", 
/* 541:523 */       "Sally is a person.", 
/* 542:524 */       "The ball is an object.", 
/* 543:525 */       "John controls the ball.", 
/* 544:526 */       "John gives the ball to Mary.", 
/* 545:527 */       "Mary controls the ball.", 
/* 546:528 */       "Mary loves Sally.", 
/* 547:529 */       "Mary hugs Sally.", 
/* 548:530 */       "Mary kicks the ball to Sally.", 
/* 549:531 */       "Sally controls the ball.", 
/* 550:532 */       "Sally punches John.", 
/* 551:533 */       "Sally gives the ball to John.", 
/* 552:534 */       "John controls the ball" };
/* 553:    */     
/* 554:    */ 
/* 555:537 */     plot = new String[] {
/* 556:538 */       "Alpha is a person.", 
/* 557:539 */       "Beta is a person.", 
/* 558:540 */       "Omega is a person.", 
/* 559:541 */       "Delta is a person.", 
/* 560:542 */       "Delta hates Omega", 
/* 561:543 */       "Alpha is Omega's wife.", 
/* 562:544 */       "Beta is Delta's wife.", 
/* 563:545 */       "Omega hugs Alpha.", 
/* 564:546 */       "Omega is happy.", 
/* 565:547 */       "Alpha is happy because Omega is happy", 
/* 566:548 */       "Delta hugs Beta." };
/* 567:    */     
/* 568:    */ 
/* 569:551 */     Translator trans = Translator.getTranslator();
/* 570:552 */     Generator gen = Generator.getGenerator();
/* 571:553 */     gen.setStoryMode();
/* 572:554 */     gen.flush();
/* 573:    */     
/* 574:556 */     List<Entity> plotElts = new ArrayList();
/* 575:557 */     for (String sentence : plot)
/* 576:    */     {
/* 577:558 */       Entity elt = trans.translate(sentence).getElement(0);
/* 578:559 */       plotElts.add(elt);
/* 579:    */     }
/* 580:562 */     CharacterViewer.disableCharacterProcessor.setSelected(false);
/* 581:563 */     CharacterProcessor cp = new CharacterProcessor();
/* 582:564 */     for (Entity elt : plotElts) {
/* 583:565 */       cp.processPlotElement(new BetterSignal(new Object[] { elt }));
/* 584:    */     }
/* 585:567 */     cp.processCompleteStory(null);
/* 586:    */     
/* 587:569 */     Object cms = CharacterProcessor.getCharacterLibrary();
/* 588:570 */     Object characters = new ArrayList(((Map)cms).values());
/* 589:    */     
/* 590:572 */     PlotWeaver weaver = new PlotWeaver((List)characters);
/* 591:573 */     List<Entity> woven_plot = weaver.weavePlots();
/* 592:    */     
/* 593:575 */     Mark.say(new Object[] {"Woven plot:" });
/* 594:576 */     for (Entity plot_elt : woven_plot) {
/* 595:577 */       Mark.say(new Object[] {plot_elt.toEnglish() });
/* 596:    */     }
/* 597:    */   }
/* 598:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.StoryGeneration.PlotWeaver
 * JD-Core Version:    0.7.0.1
 */