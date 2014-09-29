/*   1:    */ package matthewFay.Utilities;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Function;
/*   5:    */ import bridge.reps.entities.Relation;
/*   6:    */ import bridge.reps.entities.Sequence;
/*   7:    */ import bridge.reps.entities.Thread;
/*   8:    */ import edu.uci.ics.jung.graph.Forest;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.HashMap;
/*  11:    */ import java.util.HashSet;
/*  12:    */ import java.util.Iterator;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Set;
/*  15:    */ import java.util.Vector;
/*  16:    */ import matthewFay.CharacterModeling.CharacterProcessor;
/*  17:    */ import matthewFay.StoryAlignment.SequenceAlignment;
/*  18:    */ import minilisp.LList;
/*  19:    */ import translator.Translator;
/*  20:    */ import utils.Mark;
/*  21:    */ import utils.PairOfEntities;
/*  22:    */ 
/*  23:    */ public class EntityHelper
/*  24:    */ {
/*  25:    */   public static Entity createStartStoryStructure(String name)
/*  26:    */   {
/*  27: 17 */     Relation root = null;
/*  28: 18 */     Entity title = new Entity(name);
/*  29: 19 */     Function story = new Function("story", title);
/*  30: 20 */     Function object = new Function("object", story);
/*  31: 21 */     Sequence roles = new Sequence("roles");
/*  32: 22 */     roles.addElement(object);
/*  33: 23 */     Entity you = new Entity("you");
/*  34: 24 */     root = new Relation("start", you, roles);
/*  35:    */     
/*  36: 26 */     return root;
/*  37:    */   }
/*  38:    */   
/*  39: 29 */   private static int genericCount = 0;
/*  40:    */   static Forest<MatchNode, Integer> graph;
/*  41:    */   
/*  42:    */   public static Entity getGenericEntity()
/*  43:    */   {
/*  44: 32 */     Entity generic = new Entity("GEN_" + genericCount);
/*  45: 33 */     generic.getPrimedThread().add(generic.getPrimedThread().size() - 1, "name");
/*  46: 34 */     genericCount += 1;
/*  47: 35 */     return generic;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static boolean isGeneric(Entity e)
/*  51:    */   {
/*  52: 39 */     if (e.getName().startsWith("GEN_")) {
/*  53: 39 */       return true;
/*  54:    */     }
/*  55: 40 */     return false;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static List<Entity> generalizeListOfEntities(List<Entity> story, Entity focus)
/*  59:    */   {
/*  60: 44 */     List<Entity> generalized_story = new ArrayList();
/*  61:    */     
/*  62: 46 */     Set<Entity> all_entities_from_story = new HashSet();
/*  63: 47 */     for (Entity event : story) {
/*  64: 48 */       all_entities_from_story.addAll(getAllEntities(event));
/*  65:    */     }
/*  66: 50 */     List<PairOfEntities> replacements = new ArrayList();
/*  67: 51 */     replacements.add(new PairOfEntities(focus, Generalizer.getFocus()));
/*  68: 52 */     for (Entity entity : all_entities_from_story) {
/*  69: 53 */       if ((!entity.equals(focus)) && (
/*  70: 54 */         (CharacterProcessor.getCharacterLibrary().keySet().contains(entity)) || (CharacterProcessor.getGenericsLibrary().contains(entity))))
/*  71:    */       {
/*  72: 55 */         Entity generic_entity = getGenericEntity();
/*  73: 56 */         replacements.add(new PairOfEntities(entity, generic_entity));
/*  74:    */       }
/*  75:    */     }
/*  76: 60 */     for (Entity event : story)
/*  77:    */     {
/*  78: 61 */       Entity new_event = event.deepClone(false);
/*  79: 62 */       new_event = findAndReplace(new_event, replacements, true);
/*  80: 63 */       generalized_story.add(new_event);
/*  81:    */     }
/*  82: 66 */     return generalized_story;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static List<Entity> sequenceToList(Sequence sequence)
/*  86:    */   {
/*  87: 70 */     ArrayList<Entity> list = new ArrayList();
/*  88: 72 */     for (int i = 0; i < sequence.getNumberOfChildren(); i++)
/*  89:    */     {
/*  90: 73 */       Entity t = sequence.getElement(i);
/*  91:    */       
/*  92: 75 */       list.add(t);
/*  93:    */     }
/*  94: 78 */     return list;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static String getStoryTitle(Sequence story)
/*  98:    */   {
/*  99:    */     try
/* 100:    */     {
/* 101: 83 */       Relation r = (Relation)story.getElement(0);
/* 102: 84 */       Sequence s = (Sequence)r.getObject();
/* 103: 85 */       Function d = (Function)s.getElement(0);
/* 104: 86 */       d = (Function)d.getSubject();
/* 105: 87 */       Entity t = d.getSubject();
/* 106: 88 */       return t.getName();
/* 107:    */     }
/* 108:    */     catch (Exception e) {}
/* 109: 91 */     return story.getElement(0).asString();
/* 110:    */   }
/* 111:    */   
/* 112:    */   private static void getEntities(Entity thing, List<Entity> currentEntities)
/* 113:    */   {
/* 114: 96 */     if ((thing.getType().equals("appear")) && (thing.functionP()) && (thing.getSubject().isA("gap"))) {
/* 115: 97 */       return;
/* 116:    */     }
/* 117: 99 */     if (thing.functionP())
/* 118:    */     {
/* 119:100 */       Entity subject = thing.getSubject();
/* 120:101 */       getEntities(subject, currentEntities);
/* 121:    */     }
/* 122:103 */     else if (thing.featureP())
/* 123:    */     {
/* 124:104 */       Mark.say(new Object[] {"Features not handled yet..." });
/* 125:    */     }
/* 126:106 */     else if (thing.relationP())
/* 127:    */     {
/* 128:107 */       if (thing.getSubject().entityP("you"))
/* 129:    */       {
/* 130:108 */         if (!thing.getObject().functionP("story")) {
/* 131:108 */           thing.getObject().functionP("reflection");
/* 132:    */         }
/* 133:    */       }
/* 134:112 */       else if ((!thing.getType().equals("appear")) || (!thing.functionP()) || (!thing.getSubject().isA("gap")))
/* 135:    */       {
/* 136:117 */         Entity subject = thing.getSubject();
/* 137:118 */         Entity object = thing.getObject();
/* 138:119 */         getEntities(subject, currentEntities);
/* 139:120 */         getEntities(object, currentEntities);
/* 140:    */       }
/* 141:    */     }
/* 142:123 */     else if (thing.sequenceP())
/* 143:    */     {
/* 144:124 */       for (int i = 0; i < thing.getNumberOfChildren(); i++) {
/* 145:125 */         getEntities(thing.getElement(i), currentEntities);
/* 146:    */       }
/* 147:    */     }
/* 148:128 */     else if ((thing.entityP()) && 
/* 149:129 */       (!currentEntities.contains(thing)))
/* 150:    */     {
/* 151:130 */       currentEntities.add(thing);
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   public static List<Entity> getAllEntities(Entity thing)
/* 156:    */   {
/* 157:136 */     List<Entity> entityList = new ArrayList();
/* 158:137 */     getEntities(thing, entityList);
/* 159:138 */     return entityList;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public static List<Entity> getAllAgents(Entity entity)
/* 163:    */   {
/* 164:150 */     List<Entity> entityList = getAllEntities(entity);
/* 165:151 */     List<Entity> agentList = new ArrayList();
/* 166:152 */     for (Entity e : entityList) {
/* 167:153 */       if ((e.isA("name")) || 
/* 168:154 */         (e.isA("character")) || 
/* 169:155 */         (e.isA("person")) || 
/* 170:156 */         (e.isA("country")) || 
/* 171:157 */         (isGeneric(e)) || 
/* 172:158 */         (e.isA("null"))) {
/* 173:159 */         agentList.add(e);
/* 174:    */       }
/* 175:    */     }
/* 176:162 */     return agentList;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public static List<Entity> getAllEntities(List<Entity> listOfElements)
/* 180:    */   {
/* 181:166 */     List<Entity> entityList = new ArrayList();
/* 182:    */     Iterator localIterator2;
/* 183:167 */     for (Iterator localIterator1 = listOfElements.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 184:    */     {
/* 185:167 */       Entity element = (Entity)localIterator1.next();
/* 186:168 */       List<Entity> entities = getAllEntities(element);
/* 187:169 */       localIterator2 = entities.iterator(); continue;Entity entity = (Entity)localIterator2.next();
/* 188:170 */       if ((!entityList.contains(entity)) && (entity != null)) {
/* 189:170 */         entityList.add(entity);
/* 190:    */       }
/* 191:    */     }
/* 192:172 */     return entityList;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public static boolean containsEntity(Entity element, Entity entity)
/* 196:    */   {
/* 197:176 */     if (element.equals(entity)) {
/* 198:176 */       return true;
/* 199:    */     }
/* 200:177 */     if (element.functionP()) {
/* 201:177 */       return containsEntity(element.getSubject(), entity);
/* 202:    */     }
/* 203:178 */     if (element.relationP()) {
/* 204:179 */       return (containsEntity(element.getSubject(), entity)) || (containsEntity(element.getObject(), entity));
/* 205:    */     }
/* 206:180 */     if (element.sequenceP()) {
/* 207:181 */       for (Entity e : element.getChildren()) {
/* 208:182 */         if (containsEntity(e, entity)) {
/* 209:182 */           return true;
/* 210:    */         }
/* 211:    */       }
/* 212:    */     }
/* 213:185 */     return false;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public static List<PairOfEntities> bindingsToList(LList<PairOfEntities> bindings)
/* 217:    */   {
/* 218:189 */     List<PairOfEntities> new_bindings = new ArrayList();
/* 219:190 */     for (PairOfEntities binding : bindings) {
/* 220:191 */       new_bindings.add(binding);
/* 221:    */     }
/* 222:193 */     return new_bindings;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public static Entity instantiate(Entity element, LList<PairOfEntities> bindings)
/* 226:    */   {
/* 227:197 */     return findAndReplace(element.deepClone(), bindings, true);
/* 228:    */   }
/* 229:    */   
/* 230:    */   public static Entity instantiate(Entity element, List<PairOfEntities> bindings)
/* 231:    */   {
/* 232:201 */     return findAndReplace(element.deepClone(), bindings, true, false);
/* 233:    */   }
/* 234:    */   
/* 235:    */   public static Entity findAndReplace(Entity element, List<PairOfEntities> bindings)
/* 236:    */   {
/* 237:205 */     return findAndReplace(element, bindings, false, false);
/* 238:    */   }
/* 239:    */   
/* 240:    */   public static Entity findAndReplace(Entity element, List<PairOfEntities> bindings, boolean preserveUnknowns)
/* 241:    */   {
/* 242:209 */     return findAndReplace(element, bindings, preserveUnknowns, false);
/* 243:    */   }
/* 244:    */   
/* 245:    */   public static Entity findAndReplace(Entity element, List<PairOfEntities> bindings, boolean preserveUnknowns, boolean reverseBindings)
/* 246:    */   {
/* 247:213 */     if (element.entityP())
/* 248:    */     {
/* 249:215 */       if (reverseBindings) {
/* 250:216 */         for (PairOfEntities pair : bindings) {
/* 251:217 */           if ((pair.getDatum().isDeepEqual(element)) && 
/* 252:218 */             (pair.getDatum().getID() == element.getID()))
/* 253:    */           {
/* 254:219 */             if ((preserveUnknowns) && (pair.getPattern().getType().equals("null"))) {
/* 255:220 */               return element;
/* 256:    */             }
/* 257:222 */             return pair.getPattern();
/* 258:    */           }
/* 259:    */         }
/* 260:    */       } else {
/* 261:227 */         for (PairOfEntities pair : bindings) {
/* 262:228 */           if ((pair.getPattern().isDeepEqual(element)) && 
/* 263:229 */             (pair.getPattern().getID() == element.getID()))
/* 264:    */           {
/* 265:230 */             if ((preserveUnknowns) && (pair.getDatum().getType().equals("null"))) {
/* 266:231 */               return element;
/* 267:    */             }
/* 268:233 */             return pair.getDatum();
/* 269:    */           }
/* 270:    */         }
/* 271:    */       }
/* 272:238 */       if (preserveUnknowns) {
/* 273:239 */         return element;
/* 274:    */       }
/* 275:241 */       return new Entity();
/* 276:    */     }
/* 277:243 */     if (element.relationP())
/* 278:    */     {
/* 279:244 */       element.setSubject(findAndReplace(element.getSubject(), bindings, preserveUnknowns, reverseBindings));
/* 280:245 */       element.setObject(findAndReplace(element.getObject(), bindings, preserveUnknowns, reverseBindings));
/* 281:246 */       return element;
/* 282:    */     }
/* 283:248 */     if (element.functionP())
/* 284:    */     {
/* 285:249 */       element.setSubject(findAndReplace(element.getSubject(), bindings, preserveUnknowns, reverseBindings));
/* 286:250 */       return element;
/* 287:    */     }
/* 288:252 */     if (element.sequenceP())
/* 289:    */     {
/* 290:253 */       int i = 0;
/* 291:254 */       Sequence s = (Sequence)element;
/* 292:255 */       while (i < element.getNumberOfChildren())
/* 293:    */       {
/* 294:256 */         Entity child = element.getElement(i);
/* 295:257 */         child = findAndReplace(child, bindings, preserveUnknowns, reverseBindings);
/* 296:258 */         s.setElementAt(child, i);
/* 297:259 */         i++;
/* 298:    */       }
/* 299:261 */       return element;
/* 300:    */     }
/* 301:264 */     return element;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public static Entity findAndReplace(Entity element, LList<PairOfEntities> bindings, boolean preserveUnknowns, boolean reverseBindings)
/* 305:    */   {
/* 306:268 */     return findAndReplace(element, bindingsToList(bindings), preserveUnknowns, reverseBindings);
/* 307:    */   }
/* 308:    */   
/* 309:    */   public static Entity findAndReplace(Entity element, LList<PairOfEntities> bindings, boolean preserveUnknowns)
/* 310:    */   {
/* 311:272 */     return findAndReplace(element, bindingsToList(bindings), preserveUnknowns, false);
/* 312:    */   }
/* 313:    */   
/* 314:    */   public static Entity findAndReplace(Entity element, LList<PairOfEntities> bindings)
/* 315:    */   {
/* 316:276 */     return findAndReplace(element, bindingsToList(bindings));
/* 317:    */   }
/* 318:    */   
/* 319:    */   public static boolean contains(Entity pattern, Entity container)
/* 320:    */   {
/* 321:280 */     boolean ret = false;
/* 322:281 */     if (container.isEqual(pattern)) {
/* 323:282 */       return true;
/* 324:    */     }
/* 325:283 */     for (Entity inner_container : container.getAllComponents()) {
/* 326:284 */       ret = (ret) || (contains(pattern, inner_container));
/* 327:    */     }
/* 328:285 */     return ret;
/* 329:    */   }
/* 330:    */   
/* 331:290 */   static int edgeCount = 0;
/* 332:    */   static List<MatchNode> leafNodes;
/* 333:    */   
/* 334:    */   public static class MatchNode
/* 335:    */     implements Comparable<MatchNode>
/* 336:    */   {
/* 337:    */     private MatchNode parent;
/* 338:    */     private Vector<MatchNode> children;
/* 339:    */     public List<Entity> story1_entities;
/* 340:    */     public List<Entity> story2_entities;
/* 341:    */     public LList<PairOfEntities> bindingSet;
/* 342:305 */     public SequenceAlignment alignment = null;
/* 343:    */     public float score;
/* 344:    */     
/* 345:    */     public void setParent(MatchNode node)
/* 346:    */     {
/* 347:310 */       if (this.parent == node) {
/* 348:310 */         return;
/* 349:    */       }
/* 350:312 */       if (this.parent != null) {
/* 351:313 */         this.parent.children.remove(this);
/* 352:    */       }
/* 353:316 */       node.children.add(this);
/* 354:317 */       this.parent = node;
/* 355:    */     }
/* 356:    */     
/* 357:    */     public MatchNode getParent()
/* 358:    */     {
/* 359:321 */       return this.parent;
/* 360:    */     }
/* 361:    */     
/* 362:    */     public Vector<MatchNode> getChildren()
/* 363:    */     {
/* 364:325 */       return new Vector(this.children);
/* 365:    */     }
/* 366:    */     
/* 367:    */     public void addChild(MatchNode node)
/* 368:    */     {
/* 369:329 */       if (node.parent != null) {
/* 370:329 */         node.parent.children.remove(node);
/* 371:    */       }
/* 372:330 */       this.children.add(node);
/* 373:    */     }
/* 374:    */     
/* 375:    */     public MatchNode()
/* 376:    */     {
/* 377:334 */       this.parent = null;
/* 378:335 */       this.children = new Vector();
/* 379:336 */       this.story1_entities = null;
/* 380:337 */       this.story2_entities = null;
/* 381:338 */       this.bindingSet = new LList();
/* 382:339 */       this.score = (1.0F / -1.0F);
/* 383:    */     }
/* 384:    */     
/* 385:    */     public int compareTo(MatchNode o)
/* 386:    */     {
/* 387:347 */       if (this.score > o.score) {
/* 388:347 */         return -1;
/* 389:    */       }
/* 390:348 */       if (this.score < o.score) {
/* 391:348 */         return 1;
/* 392:    */       }
/* 393:349 */       return 0;
/* 394:    */     }
/* 395:    */   }
/* 396:    */   
/* 397:    */   public static void main(String[] args)
/* 398:    */     throws Exception
/* 399:    */   {
/* 400:359 */     MatchNode node1 = new MatchNode();
/* 401:360 */     MatchNode node2 = new MatchNode();
/* 402:361 */     node1.score = 3.193436F;
/* 403:362 */     node2.score = 2.987911F;
/* 404:363 */     Mark.say(new Object[] {Integer.valueOf(node1.compareTo(node2)) });
/* 405:    */     
/* 406:365 */     Entity things = Translator.getTranslator().translate("Mark went to the store.");
/* 407:366 */     Mark.say(new Object[] {things.asString() });
/* 408:367 */     List<Entity> list = getAllEntities(things);
/* 409:368 */     Mark.say(new Object[] {Integer.valueOf(list.size()) });
/* 410:369 */     for (Entity thing : getAllEntities(things)) {
/* 411:370 */       Mark.say(new Object[] {thing.asString(), thing.getType() });
/* 412:    */     }
/* 413:372 */     Entity thing2 = new Entity();
/* 414:373 */     Entity thing3 = new Entity("ACK");
/* 415:374 */     Mark.say(new Object[] {thing2.asString(), thing2.getType() });
/* 416:375 */     Mark.say(new Object[] {thing3.asString(), thing3.getType() });
/* 417:    */   }
/* 418:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.Utilities.EntityHelper
 * JD-Core Version:    0.7.0.1
 */