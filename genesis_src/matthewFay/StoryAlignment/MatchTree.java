/*   1:    */ package matthewFay.StoryAlignment;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Sequence;
/*   5:    */ import edu.uci.ics.jung.graph.DelegateTree;
/*   6:    */ import edu.uci.ics.jung.graph.Forest;
/*   7:    */ import edu.uci.ics.jung.graph.util.EdgeType;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Collections;
/*  10:    */ import java.util.List;
/*  11:    */ import matthewFay.Demo;
/*  12:    */ import matthewFay.Utilities.EntityHelper;
/*  13:    */ import matthewFay.Utilities.EntityHelper.MatchNode;
/*  14:    */ import matthewFay.viewers.AlignmentViewer;
/*  15:    */ import minilisp.LList;
/*  16:    */ import utils.Mark;
/*  17:    */ import utils.PairOfEntities;
/*  18:    */ 
/*  19:    */ public class MatchTree
/*  20:    */ {
/*  21: 24 */   public static boolean debugPrintOutputDuringGeneration = false;
/*  22: 28 */   public static boolean thereCanOnlyBeOne = true;
/*  23: 31 */   int nodesProcessed = 0;
/*  24: 32 */   int nodesPruned = 0;
/*  25: 33 */   int entitiesPruned = 0;
/*  26:    */   private EntityHelper.MatchNode root;
/*  27:    */   public ArrayList<EntityHelper.MatchNode> leafNodes;
/*  28:    */   public Forest<EntityHelper.MatchNode, Integer> graph;
/*  29: 39 */   private int edgeCount = 0;
/*  30: 40 */   private boolean foundLeaf = false;
/*  31: 41 */   private float leafScore = (1.0F / -1.0F);
/*  32:    */   private NodeScorer scorer;
/*  33:    */   private ArrayList<EntityHelper.MatchNode> queue;
/*  34:    */   
/*  35:    */   public MatchTree(List<Entity> story1_entities, List<Entity> story2_entities, NodeScorer scorer)
/*  36:    */   {
/*  37: 54 */     this.scorer = scorer;
/*  38: 55 */     this.root = new EntityHelper.MatchNode();
/*  39: 56 */     this.root.story1_entities = story1_entities;
/*  40: 57 */     this.root.story2_entities = story2_entities;
/*  41: 58 */     this.root.score = (1.0F / -1.0F);
/*  42:    */     
/*  43: 60 */     this.queue = new ArrayList();
/*  44: 61 */     this.queue.add(this.root);
/*  45:    */     
/*  46:    */ 
/*  47: 64 */     this.leafNodes = new ArrayList();
/*  48:    */     
/*  49:    */ 
/*  50: 67 */     this.graph = new DelegateTree();
/*  51: 68 */     this.graph.addVertex(this.root);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void primeMatchTree(LList<PairOfEntities> bindings)
/*  55:    */   {
/*  56: 72 */     this.root.bindingSet = this.root.bindingSet.append(bindings);
/*  57: 73 */     for (PairOfEntities pair : bindings)
/*  58:    */     {
/*  59: 74 */       Entity thing1 = pair.getPattern();
/*  60: 75 */       Entity thing2 = pair.getDatum();
/*  61: 76 */       this.root.story1_entities.remove(thing1);
/*  62: 77 */       this.root.story2_entities.remove(thing2);
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   private EntityHelper.MatchNode createNode(EntityHelper.MatchNode parent, Entity entity1, Entity entity2)
/*  67:    */   {
/*  68: 82 */     EntityHelper.MatchNode newNode = new EntityHelper.MatchNode();
/*  69: 83 */     newNode.story1_entities = new ArrayList(parent.story1_entities);
/*  70: 84 */     newNode.story2_entities = new ArrayList(parent.story2_entities);
/*  71:    */     
/*  72: 86 */     newNode.bindingSet = parent.bindingSet.cons(new PairOfEntities(entity1, entity2));
/*  73: 87 */     newNode.story1_entities.remove(entity1);
/*  74: 88 */     newNode.story2_entities.remove(entity2);
/*  75:    */     
/*  76: 90 */     newNode.setParent(parent);
/*  77:    */     
/*  78: 92 */     newNode.score = this.scorer.score(newNode);
/*  79:    */     
/*  80: 94 */     return newNode;
/*  81:    */   }
/*  82:    */   
/*  83:    */   private EntityHelper.MatchNode finishNode(EntityHelper.MatchNode parent)
/*  84:    */   {
/*  85: 98 */     EntityHelper.MatchNode newNode = new EntityHelper.MatchNode();
/*  86: 99 */     newNode.story1_entities = new ArrayList(parent.story1_entities);
/*  87:100 */     newNode.story2_entities = new ArrayList(parent.story2_entities);
/*  88:    */     
/*  89:102 */     newNode.bindingSet = null;
/*  90:103 */     while (!newNode.story2_entities.isEmpty())
/*  91:    */     {
/*  92:104 */       if (newNode.bindingSet == null) {
/*  93:105 */         newNode.bindingSet = parent.bindingSet.cons(new PairOfEntities(new Entity("null"), (Entity)newNode.story2_entities.get(0)));
/*  94:    */       } else {
/*  95:107 */         newNode.bindingSet = newNode.bindingSet.cons(new PairOfEntities(new Entity("null"), (Entity)newNode.story2_entities.get(0)));
/*  96:    */       }
/*  97:108 */       newNode.story2_entities.remove(0);
/*  98:    */     }
/*  99:111 */     newNode.setParent(parent);
/* 100:    */     
/* 101:113 */     newNode.score = this.scorer.score(newNode);
/* 102:    */     
/* 103:115 */     return newNode;
/* 104:    */   }
/* 105:    */   
/* 106:    */   private void removeBadBindings()
/* 107:    */   {
/* 108:119 */     float bad_threshold = 0.25F;
/* 109:120 */     float worstScore = ((EntityHelper.MatchNode)this.queue.get(this.queue.size() - 1)).score;
/* 110:121 */     int worstCount = 0;
/* 111:122 */     for (int i = this.queue.size() - 1; i > 0; i--)
/* 112:    */     {
/* 113:123 */       if (((EntityHelper.MatchNode)this.queue.get(i)).score != worstScore) {
/* 114:    */         break;
/* 115:    */       }
/* 116:124 */       worstCount++;
/* 117:    */     }
/* 118:129 */     if (worstCount / this.queue.size() > bad_threshold) {
/* 119:130 */       while (worstCount > 0)
/* 120:    */       {
/* 121:131 */         this.queue.remove(this.queue.size() - 1);
/* 122:132 */         this.nodesPruned += 1;
/* 123:133 */         worstCount--;
/* 124:    */       }
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void generateMatchTree()
/* 129:    */   {
/* 130:139 */     AlignmentViewer.firstPopout = true;
/* 131:    */     
/* 132:    */ 
/* 133:142 */     ((EntityHelper.MatchNode)this.queue.get(0)).score = this.scorer.score((EntityHelper.MatchNode)this.queue.get(0));
/* 134:    */     
/* 135:144 */     findUnmatchableEntities1((EntityHelper.MatchNode)this.queue.get(0));
/* 136:145 */     findUnmatchableEntities2((EntityHelper.MatchNode)this.queue.get(0));
/* 137:147 */     while (!this.queue.isEmpty())
/* 138:    */     {
/* 139:148 */       if (debugPrintOutputDuringGeneration)
/* 140:    */       {
/* 141:150 */         outputProgress();
/* 142:151 */         AlignmentViewer.popoutVisualTree(this.graph);
/* 143:    */         try
/* 144:    */         {
/* 145:153 */           Thread.sleep(100L);
/* 146:    */         }
/* 147:    */         catch (InterruptedException e)
/* 148:    */         {
/* 149:155 */           e.printStackTrace();
/* 150:    */         }
/* 151:    */       }
/* 152:159 */       EntityHelper.MatchNode bestNode = (EntityHelper.MatchNode)this.queue.get(0);
/* 153:160 */       this.queue.remove(0);
/* 154:164 */       if (!bestNode.story1_entities.isEmpty())
/* 155:    */       {
/* 156:165 */         Entity entity1 = (Entity)bestNode.story1_entities.get(0);
/* 157:    */         
/* 158:    */ 
/* 159:    */ 
/* 160:169 */         EntityHelper.MatchNode newNode = createNode(bestNode, entity1, new Entity("null"));
/* 161:170 */         this.queue.add(newNode);
/* 162:    */         
/* 163:172 */         this.graph.addEdge(Integer.valueOf(this.edgeCount++), bestNode, newNode, EdgeType.DIRECTED);
/* 164:174 */         if (!bestNode.story2_entities.isEmpty()) {
/* 165:175 */           for (int i = 0; i < bestNode.story2_entities.size(); i++)
/* 166:    */           {
/* 167:176 */             Entity entity2 = (Entity)bestNode.story2_entities.get(i);
/* 168:177 */             newNode = createNode(bestNode, entity1, entity2);
/* 169:178 */             this.queue.add(newNode);
/* 170:    */             
/* 171:180 */             this.graph.addEdge(Integer.valueOf(this.edgeCount++), bestNode, newNode, EdgeType.DIRECTED);
/* 172:    */           }
/* 173:    */         }
/* 174:    */       }
/* 175:187 */       else if (!bestNode.story2_entities.isEmpty())
/* 176:    */       {
/* 177:189 */         EntityHelper.MatchNode newNode = finishNode(bestNode);
/* 178:190 */         this.leafNodes.add(newNode);
/* 179:191 */         this.foundLeaf = true;
/* 180:192 */         this.leafScore = newNode.score;
/* 181:    */         
/* 182:194 */         this.graph.addEdge(Integer.valueOf(this.edgeCount++), bestNode, newNode, EdgeType.DIRECTED);
/* 183:    */       }
/* 184:    */       else
/* 185:    */       {
/* 186:197 */         this.leafNodes.add(bestNode);
/* 187:198 */         this.foundLeaf = true;
/* 188:199 */         this.leafScore = bestNode.score;
/* 189:    */       }
/* 190:203 */       Collections.sort(this.queue);
/* 191:204 */       this.nodesProcessed += 1;
/* 192:205 */       if (this.queue.size() >= 1) {
/* 193:208 */         if (this.foundLeaf)
/* 194:    */         {
/* 195:209 */           while (((EntityHelper.MatchNode)this.queue.get(this.queue.size() - 1)).score < ((EntityHelper.MatchNode)this.queue.get(0)).score)
/* 196:    */           {
/* 197:210 */             this.queue.remove(this.queue.size() - 1);
/* 198:211 */             this.nodesPruned += 1;
/* 199:    */           }
/* 200:213 */           if (thereCanOnlyBeOne) {
/* 201:214 */             this.queue.clear();
/* 202:    */           }
/* 203:    */         }
/* 204:    */         else
/* 205:    */         {
/* 206:217 */           removeBadBindings();
/* 207:    */           
/* 208:219 */           float bestThreshold = 0.5F;
/* 209:220 */           float bestScore = ((EntityHelper.MatchNode)this.queue.get(0)).score;
/* 210:221 */           int bestCount = 0;
/* 211:222 */           for (int i = 0; i < this.queue.size() - 2; i++)
/* 212:    */           {
/* 213:223 */             if (((EntityHelper.MatchNode)this.queue.get(i)).score != bestScore) {
/* 214:    */               break;
/* 215:    */             }
/* 216:224 */             bestCount++;
/* 217:    */           }
/* 218:229 */           if (bestCount / this.queue.size() > bestThreshold) {
/* 219:231 */             while (this.queue.size() > bestCount)
/* 220:    */             {
/* 221:232 */               this.queue.remove(this.queue.size() - 1);
/* 222:233 */               this.nodesPruned += 1;
/* 223:    */             }
/* 224:    */           }
/* 225:    */         }
/* 226:    */       }
/* 227:    */     }
/* 228:242 */     if (debugPrintOutputDuringGeneration)
/* 229:    */     {
/* 230:243 */       outputProgress();
/* 231:244 */       AlignmentViewer.popoutVisualTree(this.graph);
/* 232:    */     }
/* 233:    */   }
/* 234:    */   
/* 235:    */   private void findUnmatchableEntities2(EntityHelper.MatchNode bestNode)
/* 236:    */   {
/* 237:250 */     if (!bestNode.story2_entities.isEmpty())
/* 238:    */     {
/* 239:251 */       List<Entity> badEntities = new ArrayList();
/* 240:    */       EntityHelper.MatchNode tempNode;
/* 241:252 */       for (Entity e2 : bestNode.story2_entities)
/* 242:    */       {
/* 243:253 */         tempNode = createNode(bestNode, new Entity("null"), e2);
/* 244:254 */         if (tempNode.score == bestNode.score) {
/* 245:255 */           badEntities.add(e2);
/* 246:    */         }
/* 247:    */       }
/* 248:258 */       if (badEntities.size() > 0)
/* 249:    */       {
/* 250:259 */         EntityHelper.MatchNode verifierNode = new EntityHelper.MatchNode();
/* 251:260 */         verifierNode.story1_entities = new ArrayList(bestNode.story1_entities);
/* 252:261 */         verifierNode.story2_entities = new ArrayList(bestNode.story2_entities);
/* 253:262 */         verifierNode.bindingSet = null;
/* 254:263 */         for (Entity e2 : badEntities)
/* 255:    */         {
/* 256:264 */           if (verifierNode.bindingSet == null) {
/* 257:265 */             verifierNode.bindingSet = bestNode.bindingSet.cons(new PairOfEntities(new Entity("null"), e2));
/* 258:    */           } else {
/* 259:267 */             verifierNode.bindingSet = verifierNode.bindingSet.cons(new PairOfEntities(new Entity("null"), e2));
/* 260:    */           }
/* 261:269 */           verifierNode.story2_entities.remove(e2);
/* 262:    */         }
/* 263:271 */         verifierNode.score = this.scorer.score(verifierNode);
/* 264:272 */         if (verifierNode.score == bestNode.score)
/* 265:    */         {
/* 266:273 */           bestNode.story2_entities = verifierNode.story2_entities;
/* 267:274 */           bestNode.bindingSet = verifierNode.bindingSet;
/* 268:275 */           this.entitiesPruned += badEntities.size();
/* 269:    */         }
/* 270:    */       }
/* 271:    */     }
/* 272:    */   }
/* 273:    */   
/* 274:    */   private void findUnmatchableEntities1(EntityHelper.MatchNode bestNode)
/* 275:    */   {
/* 276:283 */     if (!bestNode.story1_entities.isEmpty())
/* 277:    */     {
/* 278:284 */       List<Entity> badEntities = new ArrayList();
/* 279:    */       EntityHelper.MatchNode tempNode;
/* 280:285 */       for (Entity e1 : bestNode.story1_entities)
/* 281:    */       {
/* 282:286 */         tempNode = createNode(bestNode, e1, new Entity("null"));
/* 283:287 */         if (tempNode.score == bestNode.score) {
/* 284:288 */           badEntities.add(e1);
/* 285:    */         }
/* 286:    */       }
/* 287:291 */       if (badEntities.size() > 0)
/* 288:    */       {
/* 289:292 */         EntityHelper.MatchNode verifierNode = new EntityHelper.MatchNode();
/* 290:293 */         verifierNode.story1_entities = new ArrayList(bestNode.story1_entities);
/* 291:294 */         verifierNode.story2_entities = new ArrayList(bestNode.story2_entities);
/* 292:295 */         verifierNode.bindingSet = null;
/* 293:296 */         for (Entity e1 : badEntities)
/* 294:    */         {
/* 295:297 */           if (verifierNode.bindingSet == null) {
/* 296:298 */             verifierNode.bindingSet = bestNode.bindingSet.cons(new PairOfEntities(e1, new Entity("null")));
/* 297:    */           } else {
/* 298:300 */             verifierNode.bindingSet = verifierNode.bindingSet.cons(new PairOfEntities(e1, new Entity("null")));
/* 299:    */           }
/* 300:302 */           verifierNode.story1_entities.remove(e1);
/* 301:    */         }
/* 302:304 */         verifierNode.score = this.scorer.score(verifierNode);
/* 303:305 */         if (verifierNode.score == bestNode.score)
/* 304:    */         {
/* 305:306 */           bestNode.story1_entities = verifierNode.story1_entities;
/* 306:307 */           bestNode.bindingSet = verifierNode.bindingSet;
/* 307:308 */           this.entitiesPruned += badEntities.size();
/* 308:    */         }
/* 309:    */       }
/* 310:    */     }
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void outputProgress()
/* 314:    */   {
/* 315:315 */     Mark.say(
/* 316:    */     
/* 317:    */ 
/* 318:    */ 
/* 319:    */ 
/* 320:    */ 
/* 321:    */ 
/* 322:    */ 
/* 323:    */ 
/* 324:    */ 
/* 325:    */ 
/* 326:326 */       new Object[] { "-----------------------------" });Mark.say(new Object[] { "Nodes Processed: ", Integer.valueOf(this.nodesProcessed) });Mark.say(new Object[] { "Nodes in queue: ", Integer.valueOf(this.queue.size()) });Mark.say(new Object[] { "Nodes Pruned: ", Integer.valueOf(this.nodesPruned) });Mark.say(new Object[] { "Entities Pruned: ", Integer.valueOf(this.entitiesPruned) });
/* 327:320 */     if (this.queue.size() > 0) {
/* 328:321 */       Mark.say(new Object[] {"Best Score: ", Float.valueOf(((EntityHelper.MatchNode)this.queue.get(0)).score > this.leafScore ? ((EntityHelper.MatchNode)this.queue.get(0)).score : this.leafScore) });
/* 329:    */     } else {
/* 330:323 */       Mark.say(new Object[] {"Best Score: ", Float.valueOf(this.leafScore) });
/* 331:    */     }
/* 332:325 */     Mark.say(new Object[] {"-----------------------------" });
/* 333:    */   }
/* 334:    */   
/* 335:    */   public static void main(String[] args)
/* 336:    */   {
/* 337:330 */     Sequence story1 = Demo.MediatedExchangeStory();
/* 338:331 */     Sequence story2 = Demo.MediatedExchangeStory();
/* 339:    */     
/* 340:    */ 
/* 341:334 */     List<Entity> list1 = EntityHelper.getAllEntities(story1);
/* 342:335 */     Entity mark1 = null;
/* 343:336 */     for (Entity thing : list1) {
/* 344:337 */       if (thing.getName().toLowerCase().contains("mark")) {
/* 345:338 */         mark1 = thing;
/* 346:    */       }
/* 347:    */     }
/* 348:340 */     List<Entity> list2 = EntityHelper.getAllEntities(story2);
/* 349:341 */     Entity mark2 = null;
/* 350:342 */     for (Entity thing : list2) {
/* 351:343 */       if (thing.getName().toLowerCase().contains("mark")) {
/* 352:344 */         mark2 = thing;
/* 353:    */       }
/* 354:    */     }
/* 355:346 */     LList<PairOfEntities> bindings = new LList();
/* 356:347 */     bindings = bindings.cons(new PairOfEntities(mark1, mark2));
/* 357:    */     
/* 358:349 */     MatchTree matchTree = new MatchTree(EntityHelper.getAllEntities(story1), EntityHelper.getAllEntities(story2), new NWSequenceAlignmentScorer(story1.getElements(), story2.getElements()));
/* 359:350 */     matchTree.primeMatchTree(bindings);
/* 360:351 */     matchTree.generateMatchTree();
/* 361:352 */     AlignmentViewer.popoutVisualTree(matchTree.graph);
/* 362:353 */     Mark.say(new Object[] {story1.asString() });
/* 363:354 */     Mark.say(new Object[] {story2.asString() });
/* 364:    */   }
/* 365:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.StoryAlignment.MatchTree
 * JD-Core Version:    0.7.0.1
 */