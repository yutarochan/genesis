/*   1:    */ package matthewFay.representations;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Set;
/*  10:    */ import matthewFay.CharacterModeling.CharacterProcessor;
/*  11:    */ import matthewFay.CharacterModeling.representations.Trait;
/*  12:    */ import matthewFay.StoryAlignment.Aligner;
/*  13:    */ import matthewFay.StoryAlignment.SequenceAlignment;
/*  14:    */ import matthewFay.StoryAlignment.SortableAlignmentList;
/*  15:    */ import matthewFay.Utilities.DefaultHashMap;
/*  16:    */ import matthewFay.Utilities.EntityHelper;
/*  17:    */ import matthewFay.Utilities.Generalizer;
/*  18:    */ import matthewFay.Utilities.Pair;
/*  19:    */ import utils.Mark;
/*  20:    */ import utils.PairOfEntities;
/*  21:    */ 
/*  22:    */ public class BasicCharacterModel
/*  23:    */ {
/*  24:    */   public BasicCharacterModel(Entity self)
/*  25:    */   {
/*  26: 37 */     init(self);
/*  27:    */   }
/*  28:    */   
/*  29:    */   private void init(Entity self)
/*  30:    */   {
/*  31: 42 */     this.self = self;
/*  32: 43 */     this.mode = ObservationMode.ALL;
/*  33: 44 */     this.participantEvents = new ArrayList();
/*  34: 45 */     this.observedEvents = new ArrayList();
/*  35:    */   }
/*  36:    */   
/*  37: 48 */   private static boolean characterFromPerson = true;
/*  38: 49 */   private static boolean characterFromCountry = true;
/*  39:    */   
/*  40:    */   public static boolean isCharacterMarker(Entity event)
/*  41:    */   {
/*  42: 53 */     if (event.relationP("classification"))
/*  43:    */     {
/*  44: 54 */       if (event.getObject().getName().toLowerCase().startsWith("i-")) {
/*  45: 55 */         return false;
/*  46:    */       }
/*  47: 56 */       if (event.getSubject().entityP("character")) {
/*  48: 57 */         return true;
/*  49:    */       }
/*  50: 58 */       if ((characterFromPerson) && (event.getSubject().getName().contains("person"))) {
/*  51: 59 */         return true;
/*  52:    */       }
/*  53: 60 */       if ((characterFromCountry) && (event.getSubject().getName().contains("country"))) {
/*  54: 61 */         return true;
/*  55:    */       }
/*  56:    */     }
/*  57: 63 */     return false;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static boolean isGenericMarker(Entity event)
/*  61:    */   {
/*  62: 67 */     if ((event.relationP("property")) && 
/*  63: 68 */       (event.getObject().entityP("generic"))) {
/*  64: 69 */       return true;
/*  65:    */     }
/*  66: 71 */     return false;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static Entity extractCharacterEntity(Entity event)
/*  70:    */   {
/*  71: 75 */     if (!isCharacterMarker(event)) {
/*  72: 76 */       return null;
/*  73:    */     }
/*  74: 77 */     return event.getObject();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public static Entity extractGenericEntity(Entity event)
/*  78:    */   {
/*  79: 81 */     if (!isGenericMarker(event)) {
/*  80: 82 */       return null;
/*  81:    */     }
/*  82: 83 */     return event.getSubject();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static boolean isSimilarCharacterMarker(Entity event)
/*  86:    */   {
/*  87: 90 */     if ((event.relationP("to")) && 
/*  88: 91 */       (event.getSubject().relationP("property")) && 
/*  89: 92 */       (event.getSubject().getObject().entityP("similar"))) {
/*  90: 93 */       return true;
/*  91:    */     }
/*  92: 97 */     return false;
/*  93:    */   }
/*  94:    */   
/*  95:100 */   private List<Trait> positive_traits = new ArrayList();
/*  96:101 */   private List<Trait> negative_traits = new ArrayList();
/*  97:    */   private Entity self;
/*  98:    */   public ObservationMode mode;
/*  99:    */   private List<Entity> participantEvents;
/* 100:    */   private List<Entity> observedEvents;
/* 101:    */   
/* 102:    */   public void addTrait(Trait trait, boolean positive_example)
/* 103:    */   {
/* 104:103 */     if (positive_example) {
/* 105:104 */       this.positive_traits.add(trait);
/* 106:    */     } else {
/* 107:106 */       this.negative_traits.add(trait);
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   public List<Trait> getTraits(boolean positive_example)
/* 112:    */   {
/* 113:109 */     if (positive_example) {
/* 114:110 */       return new ArrayList(this.positive_traits);
/* 115:    */     }
/* 116:111 */     return new ArrayList(this.negative_traits);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public BasicCharacterModel extractReferentCharacterModel(Entity event)
/* 120:    */   {
/* 121:115 */     Entity referentEntity = event.getSubject().getSubject();
/* 122:116 */     return CharacterProcessor.findBestCharacterModel(referentEntity);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public BasicCharacterModel extractReferenceCharacterModel(Entity event)
/* 126:    */   {
/* 127:120 */     Entity referenceEntity = event.getObject();
/* 128:121 */     return CharacterProcessor.findBestCharacterModel(referenceEntity);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static boolean isSimulateCharactersMarker(Entity event)
/* 132:    */   {
/* 133:128 */     if ((event.relationP("simulate")) && 
/* 134:129 */       (event.getSubject().entityP("you")) && 
/* 135:130 */       (event.getObject().sequenceP("roles")) && 
/* 136:131 */       (event.getObject().getElement(0).functionP("object")) && 
/* 137:132 */       (event.getObject().getElement(0).getSubject().entityP("characters"))) {
/* 138:133 */       return true;
/* 139:    */     }
/* 140:139 */     return false;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public Entity simulateNextEvent()
/* 144:    */   {
/* 145:143 */     Entity nextEvent = null;
/* 146:144 */     List<Entity> predictedEvents = new ArrayList();
/* 147:    */     
/* 148:146 */     Aligner aligner = new Aligner();
/* 149:147 */     for (BasicCharacterModel characterModel : getSimilarCharacters())
/* 150:    */     {
/* 151:149 */       List<PairOfEntities> bindings = new ArrayList();
/* 152:150 */       bindings.add(new PairOfEntities(getEntity(), characterModel.getEntity()));
/* 153:151 */       SortableAlignmentList alignments = aligner.align(getParticipantEvents(), characterModel.getParticipantEvents(), bindings);
/* 154:152 */       if (alignments.size() > 0)
/* 155:    */       {
/* 156:153 */         SequenceAlignment alignment = (SequenceAlignment)alignments.get(0);
/* 157:    */         
/* 158:155 */         int eventCount = 0;
/* 159:156 */         int i = 0;
/* 160:157 */         while (eventCount < getParticipantEvents().size())
/* 161:    */         {
/* 162:158 */           if (((Pair)alignment.get(i)).a != null) {
/* 163:159 */             eventCount++;
/* 164:    */           }
/* 165:160 */           i++;
/* 166:    */         }
/* 167:162 */         Entity predictionBase = null;
/* 168:163 */         while (i < alignment.size())
/* 169:    */         {
/* 170:164 */           if (((Pair)alignment.get(i)).b != null)
/* 171:    */           {
/* 172:165 */             predictionBase = ((Entity)((Pair)alignment.get(i)).b).deepClone(false);
/* 173:166 */             break;
/* 174:    */           }
/* 175:168 */           i++;
/* 176:    */         }
/* 177:170 */         if (predictionBase != null)
/* 178:    */         {
/* 179:171 */           Entity predictedEvent = EntityHelper.findAndReplace(predictionBase, alignment.bindings, true, true);
/* 180:172 */           if (predictedEvent != null) {
/* 181:173 */             predictedEvents.add(predictedEvent);
/* 182:    */           }
/* 183:    */         }
/* 184:    */       }
/* 185:    */     }
/* 186:178 */     if (predictedEvents.size() > 0) {
/* 187:179 */       nextEvent = (Entity)predictedEvents.get(0);
/* 188:    */     }
/* 189:180 */     return nextEvent;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public Entity getEntity()
/* 193:    */   {
/* 194:186 */     return this.self;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public static enum ObservationMode
/* 198:    */   {
/* 199:189 */     ALL,  ONLY_PARTICIPATING;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public List<Entity> getParticipantEvents()
/* 203:    */   {
/* 204:195 */     return this.participantEvents;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public List<Entity> getObservedEvents()
/* 208:    */   {
/* 209:201 */     return this.observedEvents;
/* 210:    */   }
/* 211:    */   
/* 212:205 */   private static Map<Entity, BasicCharacterModel> generic_entity_to_character = new HashMap();
/* 213:206 */   private List<Entity> generalizedCharacterStory = new ArrayList();
/* 214:207 */   private List<Entity> generic_entities = new ArrayList();
/* 215:    */   
/* 216:    */   public List<Entity> getGeneralizedCharacterStory()
/* 217:    */   {
/* 218:209 */     if (this.generalizedCharacterStory.size() != this.participantEvents.size()) {
/* 219:210 */       generateGeneralizedCharacterStory();
/* 220:    */     }
/* 221:211 */     return this.generalizedCharacterStory;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public static BasicCharacterModel getOriginatingCharacter(Entity generic_entity)
/* 225:    */   {
/* 226:215 */     if (generic_entity_to_character.containsKey(generic_entity)) {
/* 227:216 */       return (BasicCharacterModel)generic_entity_to_character.get(generic_entity);
/* 228:    */     }
/* 229:217 */     return null;
/* 230:    */   }
/* 231:    */   
/* 232:220 */   private Map<Entity, Entity> generic_replacements = new HashMap();
/* 233:    */   
/* 234:    */   public Entity getReplacedEntity(Entity generic)
/* 235:    */   {
/* 236:222 */     return (Entity)this.generic_replacements.get(generic);
/* 237:    */   }
/* 238:    */   
/* 239:    */   public List<Entity> getGenericEntities()
/* 240:    */   {
/* 241:226 */     getGeneralizedCharacterStory();
/* 242:    */     
/* 243:228 */     return this.generic_entities;
/* 244:    */   }
/* 245:    */   
/* 246:    */   private void generateGeneralizedCharacterStory()
/* 247:    */   {
/* 248:232 */     for (Entity generic_entity : this.generic_entities) {
/* 249:233 */       generic_entity_to_character.remove(generic_entity);
/* 250:    */     }
/* 251:235 */     this.generic_entities.clear();
/* 252:236 */     this.generalizedCharacterStory.clear();
/* 253:237 */     this.generic_replacements.clear();
/* 254:238 */     Set<Entity> all_entities_from_story = new HashSet();
/* 255:239 */     for (Entity event : this.participantEvents) {
/* 256:240 */       all_entities_from_story.addAll(EntityHelper.getAllEntities(event));
/* 257:    */     }
/* 258:242 */     Object entitiesToReplace = new ArrayList();
/* 259:243 */     for (Entity entity : all_entities_from_story) {
/* 260:244 */       if ((entity != getEntity()) && (
/* 261:245 */         (CharacterProcessor.getCharacterLibrary().keySet().contains(entity)) || (CharacterProcessor.getGenericsLibrary().contains(entity))))
/* 262:    */       {
/* 263:246 */         Entity generic_entity = EntityHelper.getGenericEntity();
/* 264:247 */         ((List)entitiesToReplace).add(new PairOfEntities(entity, generic_entity));
/* 265:248 */         this.generic_entities.add(generic_entity);
/* 266:249 */         generic_entity_to_character.put(generic_entity, this);
/* 267:250 */         this.generic_replacements.put(generic_entity, entity);
/* 268:    */       }
/* 269:    */     }
/* 270:254 */     for (Entity event : this.participantEvents)
/* 271:    */     {
/* 272:255 */       Entity new_event = event.deepClone(false);
/* 273:256 */       new_event = EntityHelper.findAndReplace(new_event, (List)entitiesToReplace, true);
/* 274:257 */       this.generalizedCharacterStory.add(new_event);
/* 275:    */     }
/* 276:    */   }
/* 277:    */   
/* 278:264 */   private List<BasicCharacterModel> similarCharacters = new ArrayList();
/* 279:    */   
/* 280:    */   public List<BasicCharacterModel> getSimilarCharacters()
/* 281:    */   {
/* 282:266 */     return this.similarCharacters;
/* 283:    */   }
/* 284:    */   
/* 285:270 */   protected DefaultHashMap<String, Integer> generalized_event_counts = new DefaultHashMap(Integer.valueOf(0));
/* 286:    */   
/* 287:    */   public DefaultHashMap<String, Integer> getGeneralizedEventCounts()
/* 288:    */   {
/* 289:272 */     return this.generalized_event_counts;
/* 290:    */   }
/* 291:    */   
/* 292:276 */   protected DefaultHashMap<String, Integer> semi_generalized_event_counts = new DefaultHashMap(Integer.valueOf(0));
/* 293:    */   
/* 294:    */   public DefaultHashMap<String, Integer> getSemiGeneralizedEventCounts()
/* 295:    */   {
/* 296:278 */     return this.semi_generalized_event_counts;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void observeEvent(Entity event)
/* 300:    */   {
/* 301:288 */     if (event == null) {
/* 302:289 */       return;
/* 303:    */     }
/* 304:291 */     if (isSimulateCharactersMarker(event))
/* 305:    */     {
/* 306:292 */       Mark.say(new Object[] {event.getAllTypes() });
/* 307:293 */       Mark.say(new Object[] {getSimpleName() + ": Do simulation!" });
/* 308:294 */       Entity simEvent = null;
/* 309:    */       do
/* 310:    */       {
/* 311:296 */         simEvent = simulateNextEvent();
/* 312:297 */         Mark.say(new Object[] {"Next Event: " + simEvent });
/* 313:298 */         observeEvent(simEvent);
/* 314:299 */       } while (simEvent != null);
/* 315:301 */       return;
/* 316:    */     }
/* 317:305 */     if (EntityHelper.containsEntity(event, this.self))
/* 318:    */     {
/* 319:307 */       if (isSimilarCharacterMarker(event))
/* 320:    */       {
/* 321:308 */         BasicCharacterModel referentCharacterModel = extractReferentCharacterModel(event);
/* 322:309 */         BasicCharacterModel referenceCharacterModel = extractReferenceCharacterModel(event);
/* 323:310 */         if ((referentCharacterModel != null) && (referenceCharacterModel != null))
/* 324:    */         {
/* 325:311 */           referentCharacterModel.getSimilarCharacters().add(referenceCharacterModel);
/* 326:312 */           Mark.say(new Object[] {getEntity().getType(), ": ", event.toEnglish() });
/* 327:    */         }
/* 328:    */       }
/* 329:    */       else
/* 330:    */       {
/* 331:316 */         getObservedEvents().add(event);
/* 332:317 */         getParticipantEvents().add(event);
/* 333:    */         
/* 334:    */ 
/* 335:320 */         String generalized_event = Generalizer.generalize(event, this.self).toString();
/* 336:321 */         this.generalized_event_counts.put(generalized_event, Integer.valueOf(((Integer)this.generalized_event_counts.get(generalized_event)).intValue() + 1));
/* 337:    */         
/* 338:    */ 
/* 339:324 */         String semi_generalized_event = Generalizer.generalize(event, this.self, CharacterProcessor.getCharacterLibrary().keySet()).toString();
/* 340:325 */         this.semi_generalized_event_counts.put(generalized_event, Integer.valueOf(((Integer)this.generalized_event_counts.get(generalized_event)).intValue() + 1));
/* 341:    */       }
/* 342:    */     }
/* 343:327 */     else if (this.mode == ObservationMode.ALL) {
/* 344:328 */       getObservedEvents().add(event);
/* 345:    */     }
/* 346:    */   }
/* 347:    */   
/* 348:    */   public void addEvent(Entity event)
/* 349:    */   {
/* 350:337 */     if (EntityHelper.containsEntity(event, this.self)) {
/* 351:338 */       this.participantEvents.add(event);
/* 352:    */     }
/* 353:    */   }
/* 354:    */   
/* 355:    */   public String getSimpleName()
/* 356:    */   {
/* 357:343 */     return getEntity().getType();
/* 358:    */   }
/* 359:    */   
/* 360:    */   public String toString()
/* 361:    */   {
/* 362:348 */     return this.self.toString();
/* 363:    */   }
/* 364:    */   
/* 365:    */   public String toLongString()
/* 366:    */   {
/* 367:352 */     String s = "{ \n Character: " + this.self.getName() + ")\n";
/* 368:353 */     s = s + "Observations: \n";
/* 369:354 */     for (Entity event : this.observedEvents) {
/* 370:355 */       s = s + event + ", \n";
/* 371:    */     }
/* 372:357 */     s = s + "\nKnown Characters of " + this.self.getName() + ")\n";
/* 373:358 */     s = s + "\n}\n";
/* 374:359 */     return s;
/* 375:    */   }
/* 376:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.representations.BasicCharacterModel
 * JD-Core Version:    0.7.0.1
 */