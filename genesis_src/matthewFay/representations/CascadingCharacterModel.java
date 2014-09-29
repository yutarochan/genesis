/*   1:    */ package matthewFay.representations;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import matthewFay.CharacterModeling.CharacterProcessor;
/*  10:    */ import matthewFay.StoryAlignment.Aligner;
/*  11:    */ import matthewFay.StoryAlignment.SequenceAlignment;
/*  12:    */ import matthewFay.StoryAlignment.SortableAlignmentList;
/*  13:    */ import matthewFay.Utilities.DefaultHashMap;
/*  14:    */ import matthewFay.Utilities.EntityHelper;
/*  15:    */ import matthewFay.Utilities.Generalizer;
/*  16:    */ import matthewFay.Utilities.Pair;
/*  17:    */ import utils.Mark;
/*  18:    */ 
/*  19:    */ public class CascadingCharacterModel
/*  20:    */   extends BasicCharacterModel
/*  21:    */ {
/*  22:    */   public ObservationMode mode;
/*  23:    */   
/*  24:    */   public CascadingCharacterModel(Entity self)
/*  25:    */   {
/*  26: 36 */     super(self);
/*  27: 37 */     init(0);
/*  28:    */   }
/*  29:    */   
/*  30:    */   private CascadingCharacterModel(Entity self, int depth)
/*  31:    */   {
/*  32: 41 */     super(self);
/*  33: 42 */     init(depth);
/*  34:    */   }
/*  35:    */   
/*  36:    */   private void init(int depth)
/*  37:    */   {
/*  38: 46 */     this.depth = depth;
/*  39: 47 */     this.mode = ObservationMode.ONLY_PARTICIPATING;
/*  40: 48 */     this.characterModels = new HashMap();
/*  41: 49 */     this.activeCharacterModels = new HashMap();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static boolean isSimilarCharacterMarker(Entity event)
/*  45:    */   {
/*  46: 55 */     if ((event.relationP("to")) && 
/*  47: 56 */       (event.getSubject().relationP("property")) && 
/*  48: 57 */       (event.getSubject().getObject().entityP("similar"))) {
/*  49: 58 */       return true;
/*  50:    */     }
/*  51: 62 */     return false;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public CascadingCharacterModel extractReferentCharacterModel(Entity event)
/*  55:    */   {
/*  56: 67 */     Entity referentEntity = event.getSubject().getSubject();
/*  57: 68 */     if (this.characterModels.containsKey(referentEntity)) {
/*  58: 69 */       return (CascadingCharacterModel)this.characterModels.get(referentEntity);
/*  59:    */     }
/*  60: 70 */     return null;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public CascadingCharacterModel extractReferenceCharacterModel(Entity event)
/*  64:    */   {
/*  65: 75 */     CascadingCharacterModel referenceCharacterModel = null;
/*  66: 76 */     String referenceName = event.getObject().getType();
/*  67: 78 */     for (CascadingCharacterModel model : this.characterModels.values())
/*  68:    */     {
/*  69: 79 */       String modelName = model.getEntity().getType();
/*  70: 80 */       if (modelName.equals(referenceName)) {
/*  71: 81 */         referenceCharacterModel = model;
/*  72:    */       }
/*  73:    */     }
/*  74: 84 */     return referenceCharacterModel;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public static boolean isSimulateCharactersMarker(Entity event)
/*  78:    */   {
/*  79: 91 */     if ((event.relationP("simulate")) && 
/*  80: 92 */       (event.getSubject().entityP("you")) && 
/*  81: 93 */       (event.getObject().sequenceP("roles")) && 
/*  82: 94 */       (event.getObject().getElement(0).functionP("object")) && 
/*  83: 95 */       (event.getObject().getElement(0).getSubject().entityP("characters"))) {
/*  84: 96 */       return true;
/*  85:    */     }
/*  86:102 */     return false;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Entity simulateNextEvent()
/*  90:    */   {
/*  91:106 */     Entity nextEvent = null;
/*  92:107 */     List<Entity> predictedEvents = new ArrayList();
/*  93:    */     Entity predictedEvent;
/*  94:109 */     for (CascadingCharacterModel characterModel : this.characterModels.values())
/*  95:    */     {
/*  96:110 */       predictedEvent = characterModel.simulateNextEvent();
/*  97:111 */       if (predictedEvent != null) {
/*  98:112 */         predictedEvents.add(predictedEvent);
/*  99:    */       }
/* 100:    */     }
/* 101:115 */     Aligner aligner = new Aligner();
/* 102:116 */     for (BasicCharacterModel characterModel : getSimilarCharacters())
/* 103:    */     {
/* 104:117 */       SortableAlignmentList alignments = aligner.align(getObservedEvents(), characterModel.getObservedEvents());
/* 105:118 */       if (alignments.size() > 0)
/* 106:    */       {
/* 107:119 */         SequenceAlignment alignment = (SequenceAlignment)alignments.get(0);
/* 108:    */         
/* 109:121 */         int eventCount = 0;
/* 110:122 */         int i = 0;
/* 111:123 */         while (eventCount < getObservedEvents().size())
/* 112:    */         {
/* 113:124 */           if (((Pair)alignment.get(i)).a != null) {
/* 114:125 */             eventCount++;
/* 115:    */           }
/* 116:126 */           i++;
/* 117:    */         }
/* 118:128 */         Entity predictionBase = null;
/* 119:129 */         while (i < alignment.size())
/* 120:    */         {
/* 121:130 */           if (((Pair)alignment.get(i)).b != null)
/* 122:    */           {
/* 123:131 */             predictionBase = ((Entity)((Pair)alignment.get(i)).b).deepClone(false);
/* 124:132 */             break;
/* 125:    */           }
/* 126:134 */           i++;
/* 127:    */         }
/* 128:136 */         if (predictionBase != null)
/* 129:    */         {
/* 130:137 */           Entity predictedEvent = EntityHelper.findAndReplace(predictionBase, alignment.bindings);
/* 131:138 */           if (predictedEvent != null) {
/* 132:139 */             predictedEvents.add(predictedEvent);
/* 133:    */           }
/* 134:    */         }
/* 135:    */       }
/* 136:    */     }
/* 137:144 */     if (predictedEvents.size() > 0) {
/* 138:145 */       nextEvent = (Entity)predictedEvents.get(0);
/* 139:    */     }
/* 140:146 */     return nextEvent;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public static enum ObservationMode
/* 144:    */   {
/* 145:150 */     ALL,  ONLY_PARTICIPATING;
/* 146:    */   }
/* 147:    */   
/* 148:153 */   private Map<Entity, CascadingCharacterModel> activeCharacterModels = new HashMap();
/* 149:    */   
/* 150:    */   public Collection<CascadingCharacterModel> getActiveCharacterModels()
/* 151:    */   {
/* 152:155 */     return this.activeCharacterModels.values();
/* 153:    */   }
/* 154:    */   
/* 155:164 */   private static int maximum_depth = 1;
/* 156:165 */   private int depth = 0;
/* 157:    */   private Map<Entity, CascadingCharacterModel> characterModels;
/* 158:    */   
/* 159:    */   public CascadingCharacterModel createCharacterModel(Entity entity)
/* 160:    */   {
/* 161:172 */     if (this.depth >= maximum_depth) {
/* 162:173 */       return null;
/* 163:    */     }
/* 164:174 */     if (!this.characterModels.containsKey(entity))
/* 165:    */     {
/* 166:177 */       CascadingCharacterModel newCharacterModel = new CascadingCharacterModel(entity, this.depth + 1);
/* 167:    */       
/* 168:    */ 
/* 169:180 */       this.characterModels.put(entity, newCharacterModel);
/* 170:181 */       this.activeCharacterModels.put(entity, newCharacterModel);
/* 171:185 */       for (CascadingCharacterModel characterModel : this.activeCharacterModels.values())
/* 172:    */       {
/* 173:186 */         characterModel.createCharacterModel(entity);
/* 174:187 */         newCharacterModel.createCharacterModel(characterModel.getEntity());
/* 175:    */       }
/* 176:    */     }
/* 177:190 */     return (CascadingCharacterModel)this.characterModels.get(entity);
/* 178:    */   }
/* 179:    */   
/* 180:    */   public Collection<CascadingCharacterModel> getAllCharacterModels()
/* 181:    */   {
/* 182:193 */     return this.characterModels.values();
/* 183:    */   }
/* 184:    */   
/* 185:    */   public CascadingCharacterModel getCharacterModel(Entity entity)
/* 186:    */   {
/* 187:196 */     if (this.activeCharacterModels.containsKey(entity)) {
/* 188:197 */       return (CascadingCharacterModel)this.activeCharacterModels.get(entity);
/* 189:    */     }
/* 190:198 */     if (this.characterModels.containsKey(entity)) {
/* 191:199 */       return (CascadingCharacterModel)this.characterModels.get(entity);
/* 192:    */     }
/* 193:200 */     return null;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void endStory()
/* 197:    */   {
/* 198:208 */     this.activeCharacterModels.clear();
/* 199:209 */     getObservedEvents().clear();
/* 200:210 */     getParticipantEvents().clear();
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void observeEvent(Entity event)
/* 204:    */   {
/* 205:221 */     if (isCharacterMarker(event))
/* 206:    */     {
/* 207:223 */       CascadingCharacterModel newCharacterModel = createCharacterModel(extractCharacterEntity(event));
/* 208:224 */       if (newCharacterModel != null) {
/* 209:226 */         for (Entity past_event : getObservedEvents()) {
/* 210:227 */           updateCharacterModel(newCharacterModel, past_event);
/* 211:    */         }
/* 212:    */       }
/* 213:    */     }
/* 214:232 */     if (isSimulateCharactersMarker(event))
/* 215:    */     {
/* 216:233 */       Mark.say(new Object[] {getSimpleName() + ": Do simulation!" });
/* 217:234 */       Entity simEvent = simulateNextEvent();
/* 218:235 */       Mark.say(new Object[] {"Next Event: " + simEvent });
/* 219:236 */       event = simEvent;
/* 220:    */     }
/* 221:239 */     if (event == null) {
/* 222:    */       return;
/* 223:    */     }
/* 224:    */     String semi_generalized_event;
/* 225:243 */     if (EntityHelper.containsEntity(event, getEntity()))
/* 226:    */     {
/* 227:244 */       if (isSimilarCharacterMarker(event))
/* 228:    */       {
/* 229:245 */         CascadingCharacterModel referentCharacterModel = extractReferentCharacterModel(event);
/* 230:246 */         CascadingCharacterModel referenceCharacterModel = extractReferenceCharacterModel(event);
/* 231:247 */         if ((referentCharacterModel != null) && (referenceCharacterModel != null))
/* 232:    */         {
/* 233:248 */           referentCharacterModel.getSimilarCharacters().add(referenceCharacterModel);
/* 234:249 */           Mark.say(new Object[] {getEntity().getType(), ": ", event.toEnglish() });
/* 235:    */         }
/* 236:    */       }
/* 237:253 */       getObservedEvents().add(event);
/* 238:254 */       getParticipantEvents().add(event);
/* 239:    */       
/* 240:    */ 
/* 241:257 */       String generalized_event = Generalizer.generalize(event, getEntity()).toString();
/* 242:258 */       this.generalized_event_counts.put(generalized_event, Integer.valueOf(((Integer)this.generalized_event_counts.get(generalized_event)).intValue() + 1));
/* 243:    */       
/* 244:    */ 
/* 245:261 */       semi_generalized_event = Generalizer.generalize(event, getEntity(), CharacterProcessor.getCharacterLibrary().keySet()).toString();
/* 246:262 */       this.semi_generalized_event_counts.put(generalized_event, Integer.valueOf(((Integer)this.generalized_event_counts.get(generalized_event)).intValue() + 1));
/* 247:    */     }
/* 248:263 */     else if (this.mode == ObservationMode.ALL)
/* 249:    */     {
/* 250:264 */       getObservedEvents().add(event);
/* 251:    */     }
/* 252:267 */     for (CascadingCharacterModel character : getActiveCharacterModels()) {
/* 253:268 */       updateCharacterModel(character, event);
/* 254:    */     }
/* 255:    */   }
/* 256:    */   
/* 257:    */   private void updateCharacterModel(CascadingCharacterModel characterModel, Entity event)
/* 258:    */   {
/* 259:280 */     if (this.mode == ObservationMode.ONLY_PARTICIPATING)
/* 260:    */     {
/* 261:281 */       List<Entity> present_entities = EntityHelper.getAllEntities(event);
/* 262:282 */       if (present_entities.contains(characterModel.getEntity())) {
/* 263:283 */         characterModel.observeEvent(event);
/* 264:    */       }
/* 265:    */     }
/* 266:286 */     if (this.mode == ObservationMode.ALL) {
/* 267:287 */       characterModel.observeEvent(event);
/* 268:    */     }
/* 269:    */   }
/* 270:    */   
/* 271:    */   public String toString()
/* 272:    */   {
/* 273:293 */     String s = "{ \n Character: " + getEntity().getName() + " (d=" + this.depth + ")\n";
/* 274:294 */     s = s + "Observations: \n";
/* 275:295 */     for (Entity event : getObservedEvents()) {
/* 276:296 */       s = s + event + ", \n";
/* 277:    */     }
/* 278:298 */     s = s + "\nKnown Characters of " + getEntity().getName() + " (d=" + this.depth + ")\n";
/* 279:299 */     for (CascadingCharacterModel character : this.characterModels.values()) {
/* 280:300 */       s = s + "\n" + character + ",\n ";
/* 281:    */     }
/* 282:302 */     s = s + "\n}\n";
/* 283:303 */     return s;
/* 284:    */   }
/* 285:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.representations.CascadingCharacterModel
 * JD-Core Version:    0.7.0.1
 */