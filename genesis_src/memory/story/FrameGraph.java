/*   1:    */ package memory.story;
/*   2:    */ 
/*   3:    */ import ADTs.DirectedGraph;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Relation;
/*   6:    */ import bridge.reps.entities.Sequence;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Collection;
/*  10:    */ import java.util.HashMap;
/*  11:    */ import java.util.HashSet;
/*  12:    */ import java.util.Iterator;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import java.util.Set;
/*  16:    */ import java.util.Vector;
/*  17:    */ 
/*  18:    */ public class FrameGraph
/*  19:    */ {
/*  20:    */   private DirectedGraph<Entity, Integer> graph;
/*  21:    */   private HashMap<Integer, Map<String, Relation>> edgeRelations;
/*  22:    */   private int numEdges;
/*  23:    */   
/*  24:    */   public FrameGraph()
/*  25:    */   {
/*  26: 40 */     this.graph = new DirectedGraph();
/*  27: 41 */     this.edgeRelations = new HashMap();
/*  28: 42 */     this.numEdges = 0;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public FrameGraph(Sequence input)
/*  32:    */   {
/*  33: 46 */     this();
/*  34: 47 */     addSequence(input);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public int getNumEdges()
/*  38:    */   {
/*  39: 55 */     return this.numEdges;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int getNumNodes()
/*  43:    */   {
/*  44: 63 */     return this.graph.size();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public HashSet<Entity> getNodes()
/*  48:    */   {
/*  49: 74 */     return this.graph.getNodes();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Map<String, Relation> getEdgeRelations(int i)
/*  53:    */   {
/*  54: 84 */     return (Map)this.edgeRelations.get(Integer.valueOf(i));
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void addSequence(Sequence input)
/*  58:    */   {
/*  59: 96 */     Vector<Entity> elements = input.getElements();
/*  60: 98 */     for (int i = 0; i < elements.size(); i++) {
/*  61: 99 */       if ((elements.get(i) instanceof Relation)) {
/*  62:100 */         add((Relation)elements.get(i));
/*  63:    */       }
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void add(Entity t1, Entity t2)
/*  68:    */   {
/*  69:115 */     if (!this.graph.contains(t1, t2))
/*  70:    */     {
/*  71:116 */       this.graph.addEdge(t1, t2, Integer.valueOf(this.numEdges));
/*  72:117 */       this.edgeRelations.put(Integer.valueOf(this.numEdges), new HashMap());
/*  73:118 */       this.numEdges += 1;
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void add(Entity t1, Entity t2, String relationship)
/*  78:    */   {
/*  79:    */     int edgeNum;
/*  80:136 */     if (!this.graph.contains(t1, t2))
/*  81:    */     {
/*  82:137 */       int edgeNum = this.numEdges;
/*  83:138 */       this.graph.addEdge(t1, t2, Integer.valueOf(edgeNum));
/*  84:139 */       this.edgeRelations.put(Integer.valueOf(edgeNum), new HashMap());
/*  85:140 */       this.numEdges += 1;
/*  86:    */     }
/*  87:    */     else
/*  88:    */     {
/*  89:142 */       edgeNum = ((Integer)this.graph.getEdgeBetween(t1, t2)).intValue();
/*  90:    */     }
/*  91:145 */     ((Map)this.edgeRelations.get(Integer.valueOf(edgeNum))).put(relationship, 
/*  92:146 */       new Relation(relationship, t1, t2));
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void add(Entity t1, Entity t2, Relation relationship)
/*  96:    */   {
/*  97:    */     int edgeNum;
/*  98:163 */     if (!this.graph.contains(t1, t2))
/*  99:    */     {
/* 100:164 */       int edgeNum = this.numEdges;
/* 101:165 */       this.graph.addEdge(t1, t2, Integer.valueOf(edgeNum));
/* 102:166 */       this.edgeRelations.put(Integer.valueOf(edgeNum), new HashMap());
/* 103:167 */       this.numEdges += 1;
/* 104:    */     }
/* 105:    */     else
/* 106:    */     {
/* 107:169 */       edgeNum = ((Integer)this.graph.getEdgeBetween(t1, t2)).intValue();
/* 108:    */     }
/* 109:172 */     ((Map)this.edgeRelations.get(Integer.valueOf(edgeNum))).put(relationship.getType(), relationship);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void add(Relation r)
/* 113:    */   {
/* 114:183 */     if (r != null) {
/* 115:184 */       add(r.getSubject(), r.getObject(), r);
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void remove(Entity t1, Entity t2)
/* 120:    */   {
/* 121:198 */     Integer edgeNum = (Integer)this.graph.getEdgeBetween(t1, t2);
/* 122:199 */     if (edgeNum != null) {
/* 123:200 */       this.edgeRelations.remove(edgeNum);
/* 124:    */     }
/* 125:203 */     this.graph.removeEdge(t1, t2);
/* 126:204 */     if ((this.graph.getSuccessors(t1).isEmpty()) && 
/* 127:205 */       (this.graph.getPredecessors(t1).isEmpty())) {
/* 128:206 */       this.graph.removeNode(t1);
/* 129:    */     }
/* 130:208 */     if ((this.graph.getSuccessors(t2).isEmpty()) && 
/* 131:209 */       (this.graph.getPredecessors(t2).isEmpty())) {
/* 132:210 */       this.graph.removeNode(t2);
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void remove(Entity t1, Entity t2, String relationship)
/* 137:    */   {
/* 138:225 */     Integer edgeNum = (Integer)this.graph.getEdgeBetween(t1, t2);
/* 139:226 */     if (edgeNum != null) {
/* 140:227 */       ((Map)this.edgeRelations.get(edgeNum)).remove(relationship);
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void remove(Relation r)
/* 145:    */   {
/* 146:239 */     if (r != null) {
/* 147:240 */       remove(r.getSubject(), r.getObject(), r.getType());
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public Map<String, Relation> getLinkRelationships(Entity t1, Entity t2)
/* 152:    */   {
/* 153:256 */     Integer edgeNum = (Integer)this.graph.getEdgeBetween(t1, t2);
/* 154:258 */     if (edgeNum == null) {
/* 155:259 */       return null;
/* 156:    */     }
/* 157:262 */     return (Map)this.edgeRelations.get(edgeNum);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Relation getLinkRelationship(Entity t1, Entity t2, String relationship)
/* 161:    */   {
/* 162:277 */     Map<String, Relation> results = getLinkRelationships(t1, t2);
/* 163:279 */     if (results == null) {
/* 164:280 */       return null;
/* 165:    */     }
/* 166:283 */     return (Relation)results.get(relationship);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public boolean isLinkRelated(Entity t1, Entity t2, String relationship)
/* 170:    */   {
/* 171:298 */     Integer edgeNum = (Integer)this.graph.getEdgeBetween(t1, t2);
/* 172:299 */     if (edgeNum == null) {
/* 173:300 */       return false;
/* 174:    */     }
/* 175:302 */     return ((Map)this.edgeRelations.get(edgeNum)).containsKey(relationship);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public boolean isLinkRelated(Relation r)
/* 179:    */   {
/* 180:313 */     if (r != null) {
/* 181:314 */       return false;
/* 182:    */     }
/* 183:316 */     return isLinkRelated(r.getSubject(), r.getObject(), r.getType());
/* 184:    */   }
/* 185:    */   
/* 186:    */   public Set<Entity> getLinkStartNodes(Entity t2, String relationship)
/* 187:    */   {
/* 188:331 */     HashSet<Entity> startNodes = this.graph.getPredecessors(t2);
/* 189:332 */     Iterator<Entity> nodeIterator = startNodes.iterator();
/* 190:335 */     while (nodeIterator.hasNext())
/* 191:    */     {
/* 192:336 */       Entity currStartNode = (Entity)nodeIterator.next();
/* 193:337 */       if (!isLinkRelated(currStartNode, t2, relationship)) {
/* 194:338 */         startNodes.remove(currStartNode);
/* 195:    */       }
/* 196:    */     }
/* 197:341 */     return startNodes;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public Set<Entity> getLinkEndNodes(Entity t1, String relationship)
/* 201:    */   {
/* 202:356 */     HashSet<Entity> endNodes = this.graph.getSuccessors(t1);
/* 203:357 */     Iterator<Entity> nodeIterator = endNodes.iterator();
/* 204:360 */     while (nodeIterator.hasNext())
/* 205:    */     {
/* 206:361 */       Entity currEndNode = (Entity)nodeIterator.next();
/* 207:362 */       if (!isLinkRelated(t1, currEndNode, relationship)) {
/* 208:363 */         endNodes.remove(currEndNode);
/* 209:    */       }
/* 210:    */     }
/* 211:366 */     return endNodes;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public List<Entity> getStartNodes(Entity t2, String relationship)
/* 215:    */   {
/* 216:382 */     return getStartNodesHelper(t2, relationship, 
/* 217:383 */       new ArrayList());
/* 218:    */   }
/* 219:    */   
/* 220:    */   private List<Entity> getStartNodesHelper(Entity t2, String relationship, List<Entity> result)
/* 221:    */   {
/* 222:388 */     Set<Entity> currStartNodes = getLinkStartNodes(t2, relationship);
/* 223:390 */     if (!currStartNodes.isEmpty())
/* 224:    */     {
/* 225:391 */       for (Entity node : currStartNodes) {
/* 226:392 */         if (!result.contains(node)) {
/* 227:393 */           result.add(node);
/* 228:    */         }
/* 229:    */       }
/* 230:396 */       for (Entity node : currStartNodes) {
/* 231:397 */         getStartNodesHelper(node, relationship, result);
/* 232:    */       }
/* 233:    */     }
/* 234:400 */     return result;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public List<Entity> getEndNodes(Entity t1, String relationship)
/* 238:    */   {
/* 239:416 */     return getEndNodesHelper(t1, relationship, new ArrayList());
/* 240:    */   }
/* 241:    */   
/* 242:    */   private List<Entity> getEndNodesHelper(Entity t1, String relationship, List<Entity> result)
/* 243:    */   {
/* 244:421 */     Set<Entity> currEndNodes = getLinkEndNodes(t1, relationship);
/* 245:423 */     if (!currEndNodes.isEmpty())
/* 246:    */     {
/* 247:424 */       for (Entity node : currEndNodes) {
/* 248:425 */         if (!result.contains(node)) {
/* 249:426 */           result.add(node);
/* 250:    */         }
/* 251:    */       }
/* 252:429 */       for (Entity node : currEndNodes) {
/* 253:430 */         getEndNodesHelper(node, relationship, result);
/* 254:    */       }
/* 255:    */     }
/* 256:433 */     return result;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public boolean isRelated(Entity t1, Entity t2, String relationship)
/* 260:    */   {
/* 261:447 */     return getEndNodes(t1, relationship).contains(t2);
/* 262:    */   }
/* 263:    */   
/* 264:    */   public int getShortestPathLength(Entity t1, Entity t2, String relationship)
/* 265:    */   {
/* 266:460 */     return getShortestPathLengthHelper(t1, t2, relationship, 0);
/* 267:    */   }
/* 268:    */   
/* 269:    */   private int getShortestPathLengthHelper(Entity t1, Entity t2, String relationship, int currLength)
/* 270:    */   {
/* 271:465 */     Set<Entity> currEndNodes = getLinkEndNodes(t1, relationship);
/* 272:466 */     int pathLength = 0;
/* 273:    */     
/* 274:468 */     currLength++;
/* 275:470 */     if (!currEndNodes.isEmpty()) {
/* 276:471 */       for (Entity node : currEndNodes)
/* 277:    */       {
/* 278:472 */         if (node.equals(t2)) {
/* 279:473 */           return currLength;
/* 280:    */         }
/* 281:476 */         int tempLength = getShortestPathLengthHelper(node, t2, relationship, currLength);
/* 282:477 */         if ((tempLength > 0) && (tempLength < pathLength)) {
/* 283:478 */           pathLength = tempLength;
/* 284:    */         }
/* 285:    */       }
/* 286:    */     }
/* 287:483 */     return pathLength;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public List<String> getAllRelationships()
/* 291:    */   {
/* 292:491 */     ArrayList<String> result = new ArrayList();
/* 293:492 */     Iterator<Map<String, Relation>> edgeValues = this.edgeRelations.values().iterator();
/* 294:    */     Iterator<String> tempSetIter;
/* 295:497 */     for (; edgeValues.hasNext(); tempSetIter.hasNext())
/* 296:    */     {
/* 297:498 */       tempSetIter = ((Map)edgeValues.next()).keySet().iterator();
/* 298:499 */       continue;
/* 299:500 */       String tempStr = (String)tempSetIter.next();
/* 300:501 */       if (!result.contains(tempStr)) {
/* 301:502 */         result.add(tempStr);
/* 302:    */       }
/* 303:    */     }
/* 304:506 */     return result;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public List<String> getRelationships(Entity t1, Entity t2)
/* 308:    */   {
/* 309:518 */     List<String> result = getAllRelationships();
/* 310:520 */     for (String relationship : result) {
/* 311:521 */       if (!isRelated(t1, t2, relationship)) {
/* 312:522 */         result.remove(relationship);
/* 313:    */       }
/* 314:    */     }
/* 315:525 */     return result;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public Sequence toSequence()
/* 319:    */   {
/* 320:536 */     Sequence result = new Sequence();
/* 321:537 */     Iterator<Map<String, Relation>> edgeValues = this.edgeRelations.values()
/* 322:538 */       .iterator();
/* 323:    */     Iterator<Relation> tempValuesIter;
/* 324:542 */     for (; edgeValues.hasNext(); tempValuesIter.hasNext())
/* 325:    */     {
/* 326:543 */       tempValuesIter = ((Map)edgeValues.next()).values().iterator();
/* 327:544 */       continue;
/* 328:545 */       result.addElement((Entity)tempValuesIter.next());
/* 329:    */     }
/* 330:549 */     return result;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public static void main(String[] args)
/* 334:    */   {
/* 335:553 */     FrameGraph graph = new FrameGraph();
/* 336:    */     
/* 337:    */ 
/* 338:    */ 
/* 339:    */ 
/* 340:    */ 
/* 341:559 */     Entity t1 = new Entity("Ray");
/* 342:560 */     Entity t2 = new Entity("Patrick");
/* 343:561 */     Entity t3 = new Entity("Mike");
/* 344:562 */     Entity t4 = new Entity("Mark");
/* 345:    */     
/* 346:564 */     graph.add(t3, t1, "older");
/* 347:565 */     graph.add(t4, t3, new Relation("older", t4, t3));
/* 348:566 */     graph.add(new Relation("older", t2, t4));
/* 349:    */     
/* 350:568 */     System.out.println("Relationships between Patrick and Ray");
/* 351:569 */     List<String> resultListStr = graph.getRelationships(t2, t1);
/* 352:570 */     for (String tempStr : resultListStr) {
/* 353:571 */       System.out.println(tempStr);
/* 354:    */     }
/* 355:574 */     System.out.println("Is Patrick Older than Ray?");
/* 356:575 */     if (graph.isRelated(t2, t1, "older")) {
/* 357:576 */       System.out.println("Yes");
/* 358:    */     } else {
/* 359:578 */       System.out.println("No");
/* 360:    */     }
/* 361:581 */     System.out.println("Who is Patrick older than?");
/* 362:582 */     List<Entity> resultListThing = graph.getEndNodes(t2, "older");
/* 363:583 */     for (Entity tempThing : resultListThing) {
/* 364:584 */       System.out.println(tempThing.getType());
/* 365:    */     }
/* 366:587 */     System.out.println("Who is Mark older than?");
/* 367:588 */     resultListThing = graph.getEndNodes(t4, "older");
/* 368:589 */     for (Entity tempThing : resultListThing) {
/* 369:590 */       System.out.println(tempThing.getType());
/* 370:    */     }
/* 371:593 */     System.out.println("All relationships in this graph:");
/* 372:594 */     resultListStr = graph.getAllRelationships();
/* 373:595 */     for (String tempStr : resultListStr) {
/* 374:596 */       System.out.println(tempStr);
/* 375:    */     }
/* 376:600 */     Sequence graphSeq = graph.toSequence();
/* 377:601 */     FrameGraph newGraph = new FrameGraph(graphSeq);
/* 378:    */   }
/* 379:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.story.FrameGraph
 * JD-Core Version:    0.7.0.1
 */