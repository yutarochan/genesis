/*   1:    */ package matthewFay.representations;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Set;
/*  12:    */ import matthewFay.Utilities.HashMatrix;
/*  13:    */ import utils.Mark;
/*  14:    */ 
/*  15:    */ public class StoryGraph
/*  16:    */ {
/*  17:    */   private Map<Entity, StoryGraphNode> graphNodes;
/*  18:    */   private List<StoryGraphEdge> graphEdges;
/*  19:    */   private HashMatrix<StoryGraphNode, StoryGraphNode, Integer> distances;
/*  20:    */   private HashMatrix<StoryGraphNode, StoryGraphNode, Integer> directedDistances;
/*  21:    */   
/*  22:    */   public StoryGraph()
/*  23:    */   {
/*  24: 24 */     this.graphNodes = new HashMap();
/*  25: 25 */     this.graphEdges = new ArrayList();
/*  26: 26 */     this.distances = new HashMatrix();
/*  27: 27 */     this.directedDistances = new HashMatrix();
/*  28:    */   }
/*  29:    */   
/*  30:    */   public boolean addNode(Entity entity)
/*  31:    */   {
/*  32: 31 */     if (!this.graphNodes.containsKey(entity)) {
/*  33: 32 */       return false;
/*  34:    */     }
/*  35: 33 */     this.graphNodes.put(entity, new StoryGraphNode(entity));
/*  36: 34 */     return true;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void clear()
/*  40:    */   {
/*  41: 38 */     this.graphNodes.clear();
/*  42: 39 */     this.graphEdges.clear();
/*  43: 40 */     this.distances.clear();
/*  44: 41 */     this.directedDistances.clear();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean addEdge(Entity antecedent, Entity consequent, String type)
/*  48:    */   {
/*  49: 45 */     if (!this.graphNodes.containsKey(antecedent)) {
/*  50: 46 */       this.graphNodes.put(antecedent, new StoryGraphNode(antecedent));
/*  51:    */     }
/*  52: 47 */     if (!this.graphNodes.containsKey(consequent)) {
/*  53: 48 */       this.graphNodes.put(consequent, new StoryGraphNode(consequent));
/*  54:    */     }
/*  55: 49 */     StoryGraphNode antecedentNode = (StoryGraphNode)this.graphNodes.get(antecedent);
/*  56: 50 */     StoryGraphNode consequentNode = (StoryGraphNode)this.graphNodes.get(consequent);
/*  57: 51 */     if (antecedentNode.getConsequents().contains(consequentNode))
/*  58:    */     {
/*  59: 52 */       Mark.say(new Object[] {"Rejecting:" });
/*  60: 53 */       Mark.say(new Object[] {antecedent.asString() });
/*  61: 54 */       Mark.say(new Object[] {"leads to" });
/*  62: 55 */       Mark.say(new Object[] {consequent.asString() });
/*  63: 56 */       return false;
/*  64:    */     }
/*  65: 58 */     this.graphEdges.add(new StoryGraphEdge(antecedentNode, consequentNode, type));
/*  66: 59 */     return true;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getNodeCount()
/*  70:    */   {
/*  71: 63 */     return this.graphNodes.size();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public List<StoryGraphNode> getAllNodes()
/*  75:    */   {
/*  76: 66 */     ArrayList<StoryGraphNode> nodes = new ArrayList();
/*  77: 67 */     for (StoryGraphNode node : this.graphNodes.values()) {
/*  78: 68 */       nodes.add(node);
/*  79:    */     }
/*  80: 70 */     return nodes;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public List<Entity> getAllEntities()
/*  84:    */   {
/*  85: 73 */     ArrayList<Entity> entities = new ArrayList();
/*  86: 74 */     for (StoryGraphNode node : this.graphNodes.values()) {
/*  87: 75 */       entities.add(node.getEntity());
/*  88:    */     }
/*  89: 77 */     return entities;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public StoryGraphNode getNode(Entity e)
/*  93:    */   {
/*  94: 80 */     if (this.graphNodes.containsKey(e)) {
/*  95: 81 */       return (StoryGraphNode)this.graphNodes.get(e);
/*  96:    */     }
/*  97: 82 */     return null;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public List<Entity> getAntecedents(Entity e)
/* 101:    */   {
/* 102: 85 */     List<Entity> antecedents = new ArrayList();
/* 103: 86 */     StoryGraphNode node = getNode(e);
/* 104: 87 */     if (node != null) {
/* 105: 88 */       for (StoryGraphNode ante : node.getAntecedents()) {
/* 106: 89 */         antecedents.add(ante.getEntity());
/* 107:    */       }
/* 108:    */     }
/* 109: 92 */     return antecedents;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public List<Entity> getConsequents(Entity e)
/* 113:    */   {
/* 114: 95 */     List<Entity> consequents = new ArrayList();
/* 115: 96 */     StoryGraphNode node = getNode(e);
/* 116: 97 */     if (node != null) {
/* 117: 98 */       for (StoryGraphNode conq : node.getConsequents()) {
/* 118: 99 */         consequents.add(conq.getEntity());
/* 119:    */       }
/* 120:    */     }
/* 121:102 */     return consequents;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public int getEdgeCount()
/* 125:    */   {
/* 126:106 */     return this.graphEdges.size();
/* 127:    */   }
/* 128:    */   
/* 129:    */   public List<StoryGraphEdge> getAllEdges()
/* 130:    */   {
/* 131:109 */     ArrayList<StoryGraphEdge> edges = new ArrayList();
/* 132:110 */     for (StoryGraphEdge edge : this.graphEdges) {
/* 133:111 */       edges.add(edge);
/* 134:    */     }
/* 135:113 */     return edges;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public int distance(Entity origin, Entity target)
/* 139:    */   {
/* 140:117 */     updateDistances();
/* 141:118 */     if (this.distances.contains(getNode(origin), getNode(target)))
/* 142:    */     {
/* 143:119 */       if (((Integer)this.distances.get(getNode(origin), getNode(target))).intValue() < 0) {
/* 144:120 */         return 2147483647;
/* 145:    */       }
/* 146:121 */       return ((Integer)this.distances.get(getNode(origin), getNode(target))).intValue();
/* 147:    */     }
/* 148:123 */     return 2147483647;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void updateDephts()
/* 152:    */   {
/* 153:127 */     updateDistances();
/* 154:128 */     updateDirectedDistances();
/* 155:    */     
/* 156:130 */     Set<StoryGraphNode> currentNodes = new HashSet();
/* 157:131 */     Set<StoryGraphNode> processedNodes = new HashSet();
/* 158:133 */     for (StoryGraphNode node : this.graphNodes.values())
/* 159:    */     {
/* 160:134 */       node.depth = -1;
/* 161:135 */       if (node.getAntecedents().isEmpty())
/* 162:    */       {
/* 163:137 */         node.depth = 0;
/* 164:138 */         currentNodes.add(node);
/* 165:    */       }
/* 166:    */     }
/* 167:142 */     int i = 0;
/* 168:143 */     int max_depth = 0;
/* 169:    */     StoryGraphNode node;
/* 170:    */     int distance;
/* 171:145 */     while (!currentNodes.isEmpty())
/* 172:    */     {
/* 173:    */       Iterator localIterator3;
/* 174:146 */       for (Iterator localIterator2 = currentNodes.iterator(); localIterator2.hasNext(); localIterator3.hasNext())
/* 175:    */       {
/* 176:146 */         StoryGraphNode c_node = (StoryGraphNode)localIterator2.next();
/* 177:147 */         localIterator3 = this.graphNodes.values().iterator(); continue;node = (StoryGraphNode)localIterator3.next();
/* 178:148 */         if (node.depth != 0)
/* 179:    */         {
/* 180:149 */           distance = ((Integer)this.directedDistances.get(c_node, node)).intValue();
/* 181:150 */           if (distance > 0)
/* 182:    */           {
/* 183:151 */             node.depth = Math.max(node.depth, distance + i);
/* 184:152 */             max_depth = Math.max(max_depth, node.depth);
/* 185:    */           }
/* 186:    */         }
/* 187:    */       }
/* 188:157 */       processedNodes.addAll(currentNodes);
/* 189:158 */       currentNodes.clear();
/* 190:159 */       for (StoryGraphNode node : this.graphNodes.values()) {
/* 191:160 */         if ((node.depth == i + 1) && (!processedNodes.contains(node))) {
/* 192:161 */           currentNodes.add(node);
/* 193:    */         }
/* 194:    */       }
/* 195:164 */       i++;
/* 196:    */     }
/* 197:168 */     currentNodes.clear();
/* 198:169 */     processedNodes.clear();
/* 199:170 */     i = 1;
/* 200:171 */     int p = 0;
/* 201:172 */     for (StoryGraphNode node : this.graphNodes.values()) {
/* 202:173 */       if (node.depth == i) {
/* 203:174 */         node.height = (++p);
/* 204:    */       }
/* 205:    */     }
/* 206:176 */     while (i < max_depth)
/* 207:    */     {
/* 208:177 */       for (StoryGraphNode node : this.graphNodes.values()) {
/* 209:178 */         if (node.depth == i)
/* 210:    */         {
/* 211:179 */           node.height = 2147483647;
/* 212:180 */           for (StoryGraphNode ante : node.getAntecedents()) {
/* 213:181 */             node.height = Math.min(node.height, ante.height);
/* 214:    */           }
/* 215:    */         }
/* 216:    */       }
/* 217:185 */       i++;
/* 218:    */     }
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void updateDistances()
/* 222:    */   {
/* 223:190 */     boolean doUpdate = false;
/* 224:    */     
/* 225:192 */     ArrayList<StoryGraphNode> purgeNodes = new ArrayList();
/* 226:193 */     for (StoryGraphNode node : this.distances.keySetRows()) {
/* 227:194 */       if (!this.graphNodes.values().contains(node))
/* 228:    */       {
/* 229:195 */         purgeNodes.add(node);
/* 230:196 */         doUpdate = true;
/* 231:    */       }
/* 232:    */     }
/* 233:201 */     for (StoryGraphNode node_origin : this.graphNodes.values()) {
/* 234:202 */       if (!this.distances.keySetRows().contains(node_origin)) {
/* 235:203 */         doUpdate = true;
/* 236:    */       }
/* 237:    */     }
/* 238:207 */     if (!doUpdate) {
/* 239:208 */       return;
/* 240:    */     }
/* 241:210 */     this.distances.clear();
/* 242:    */     Iterator localIterator2;
/* 243:213 */     for (??? = this.graphNodes.values().iterator(); ???.hasNext(); localIterator2.hasNext())
/* 244:    */     {
/* 245:213 */       StoryGraphNode node_origin = (StoryGraphNode)???.next();
/* 246:214 */       localIterator2 = this.graphNodes.values().iterator(); continue;StoryGraphNode node_target = (StoryGraphNode)localIterator2.next();
/* 247:215 */       if (node_origin == node_target)
/* 248:    */       {
/* 249:216 */         this.distances.put(node_origin, node_target, Integer.valueOf(0));
/* 250:    */       }
/* 251:    */       else
/* 252:    */       {
/* 253:218 */         this.distances.put(node_origin, node_target, Integer.valueOf(-1));
/* 254:219 */         this.distances.put(node_target, node_origin, Integer.valueOf(-1));
/* 255:    */       }
/* 256:    */     }
/* 257:225 */     for (; doUpdate; ???.hasNext())
/* 258:    */     {
/* 259:226 */       doUpdate = false;
/* 260:227 */       ??? = this.graphNodes.values().iterator(); continue;StoryGraphNode node_origin = (StoryGraphNode)???.next();
/* 261:    */       Iterator localIterator3;
/* 262:228 */       for (localIterator2 = this.graphNodes.values().iterator(); localIterator2.hasNext(); localIterator3.hasNext())
/* 263:    */       {
/* 264:228 */         StoryGraphNode node_target = (StoryGraphNode)localIterator2.next();
/* 265:229 */         localIterator3 = node_origin.getAllNeighbors().iterator(); continue;StoryGraphNode node_neighbor = (StoryGraphNode)localIterator3.next();
/* 266:230 */         Integer cur_distance = (Integer)this.distances.get(node_origin, node_target);
/* 267:231 */         Integer new_distance = (Integer)this.distances.get(node_neighbor, node_target);
/* 268:232 */         if ((cur_distance == null) || (cur_distance.intValue() < 0)) {
/* 269:233 */           cur_distance = Integer.valueOf(2147483647);
/* 270:    */         }
/* 271:235 */         if ((new_distance == null) || (new_distance.intValue() < 0)) {
/* 272:236 */           new_distance = Integer.valueOf(2147483647);
/* 273:    */         }
/* 274:238 */         if ((new_distance != cur_distance) && 
/* 275:239 */           (new_distance.intValue() < cur_distance.intValue() - 1))
/* 276:    */         {
/* 277:241 */           this.distances.put(node_origin, node_target, Integer.valueOf(new_distance.intValue() + 1));
/* 278:242 */           this.distances.put(node_target, node_origin, Integer.valueOf(new_distance.intValue() + 1));
/* 279:243 */           doUpdate = true;
/* 280:    */         }
/* 281:    */       }
/* 282:    */     }
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void updateDirectedDistances()
/* 286:    */   {
/* 287:253 */     boolean doUpdate = false;
/* 288:    */     
/* 289:255 */     ArrayList<StoryGraphNode> purgeNodes = new ArrayList();
/* 290:256 */     for (StoryGraphNode node : this.directedDistances.keySetRows()) {
/* 291:257 */       if (!this.graphNodes.values().contains(node))
/* 292:    */       {
/* 293:258 */         purgeNodes.add(node);
/* 294:259 */         doUpdate = true;
/* 295:    */       }
/* 296:    */     }
/* 297:264 */     for (StoryGraphNode node_origin : this.graphNodes.values()) {
/* 298:265 */       if (!this.directedDistances.keySetRows().contains(node_origin)) {
/* 299:266 */         doUpdate = true;
/* 300:    */       }
/* 301:    */     }
/* 302:270 */     if (!doUpdate) {
/* 303:271 */       return;
/* 304:    */     }
/* 305:273 */     this.directedDistances.clear();
/* 306:    */     Iterator localIterator2;
/* 307:276 */     for (??? = this.graphNodes.values().iterator(); ???.hasNext(); localIterator2.hasNext())
/* 308:    */     {
/* 309:276 */       StoryGraphNode node_origin = (StoryGraphNode)???.next();
/* 310:277 */       localIterator2 = this.graphNodes.values().iterator(); continue;StoryGraphNode node_target = (StoryGraphNode)localIterator2.next();
/* 311:278 */       if (node_origin == node_target)
/* 312:    */       {
/* 313:279 */         this.directedDistances.put(node_origin, node_target, Integer.valueOf(0));
/* 314:    */       }
/* 315:    */       else
/* 316:    */       {
/* 317:281 */         this.directedDistances.put(node_origin, node_target, Integer.valueOf(-1));
/* 318:282 */         this.directedDistances.put(node_target, node_origin, Integer.valueOf(-1));
/* 319:    */       }
/* 320:    */     }
/* 321:288 */     for (; doUpdate; ???.hasNext())
/* 322:    */     {
/* 323:289 */       doUpdate = false;
/* 324:290 */       ??? = this.graphNodes.values().iterator(); continue;StoryGraphNode node_origin = (StoryGraphNode)???.next();
/* 325:    */       Iterator localIterator3;
/* 326:291 */       for (localIterator2 = this.graphNodes.values().iterator(); localIterator2.hasNext(); localIterator3.hasNext())
/* 327:    */       {
/* 328:291 */         StoryGraphNode node_target = (StoryGraphNode)localIterator2.next();
/* 329:292 */         localIterator3 = node_origin.getConsequents().iterator(); continue;StoryGraphNode node_neighbor = (StoryGraphNode)localIterator3.next();
/* 330:293 */         Integer cur_distance = (Integer)this.directedDistances.get(node_origin, node_target);
/* 331:294 */         Integer new_distance = (Integer)this.directedDistances.get(node_neighbor, node_target);
/* 332:295 */         if ((cur_distance == null) || (cur_distance.intValue() < 0)) {
/* 333:296 */           cur_distance = Integer.valueOf(2147483647);
/* 334:    */         }
/* 335:298 */         if ((new_distance == null) || (new_distance.intValue() < 0)) {
/* 336:299 */           new_distance = Integer.valueOf(2147483647);
/* 337:    */         }
/* 338:301 */         if ((new_distance != cur_distance) && 
/* 339:302 */           (new_distance.intValue() < cur_distance.intValue() - 1))
/* 340:    */         {
/* 341:304 */           this.directedDistances.put(node_origin, node_target, Integer.valueOf(new_distance.intValue() + 1));
/* 342:305 */           doUpdate = true;
/* 343:    */         }
/* 344:    */       }
/* 345:    */     }
/* 346:    */   }
/* 347:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.representations.StoryGraph
 * JD-Core Version:    0.7.0.1
 */