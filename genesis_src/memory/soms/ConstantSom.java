/*   1:    */ package memory.soms;
/*   2:    */ 
/*   3:    */ import ADTs.UniGraph;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.LinkedHashMap;
/*   7:    */ import java.util.LinkedList;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import java.util.Set;
/*  11:    */ import memory.multithreading.Watcher;
/*  12:    */ 
/*  13:    */ public class ConstantSom<E>
/*  14:    */   implements Som<E>
/*  15:    */ {
/*  16: 21 */   private UniGraph<E, Double> graph = new UniGraph();
/*  17:    */   private DistanceMetric<E> dm;
/*  18:    */   private ElementMerger<E> em;
/*  19:    */   int numNeighbors;
/*  20: 28 */   private Map<E, Integer> elementCount = new HashMap();
/*  21: 29 */   private Map<E, E> equivalentElements = new HashMap();
/*  22:    */   
/*  23:    */   public ConstantSom(DistanceMetric<E> distanceMetric, ElementMerger<E> em, int numNeighbors)
/*  24:    */   {
/*  25: 32 */     this.dm = distanceMetric;
/*  26: 33 */     this.em = em;
/*  27: 34 */     this.numNeighbors = numNeighbors;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void add(E e)
/*  31:    */   {
/*  32: 37 */     if (e == null) {
/*  33: 37 */       return;
/*  34:    */     }
/*  35: 38 */     Set<E> neighbors = neighbors(e);
/*  36:    */     
/*  37: 40 */     boolean newElement = processNew(e, neighbors);
/*  38: 42 */     if (newElement)
/*  39:    */     {
/*  40: 43 */       this.elementCount.put(e, Integer.valueOf(1));
/*  41: 44 */       this.graph.addNode(e);
/*  42: 45 */       merge(e, neighbors);
/*  43:    */     }
/*  44: 48 */     for (Watcher w : this.watchers) {
/*  45: 49 */       w.ping();
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   private void addWithoutMerge(E e)
/*  50:    */   {
/*  51: 54 */     Set<E> neighbors = neighbors(e);
/*  52:    */     
/*  53: 56 */     boolean newElement = processNew(e, neighbors);
/*  54: 58 */     if (newElement)
/*  55:    */     {
/*  56: 59 */       this.elementCount.put(e, Integer.valueOf(1));
/*  57: 60 */       this.graph.addNode(e);
/*  58: 61 */       for (E n : neighbors) {
/*  59: 62 */         this.graph.addEdge(e, n, Double.valueOf(this.dm.distance(e, n)));
/*  60:    */       }
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   private boolean processNew(E e, Set<E> neighbors)
/*  65:    */   {
/*  66: 69 */     boolean newElement = true;
/*  67: 70 */     for (E n : neighbors) {
/*  68: 71 */       if (this.dm.distance(e, n) == 0.0D)
/*  69:    */       {
/*  70: 72 */         this.elementCount.put(e, Integer.valueOf(((Integer)this.elementCount.get(n)).intValue() + 1));
/*  71: 73 */         newElement = false;
/*  72: 74 */         this.equivalentElements.put(e, n);
/*  73: 75 */         break;
/*  74:    */       }
/*  75:    */     }
/*  76: 78 */     return newElement;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Set<E> neighbors(E e)
/*  80:    */   {
/*  81: 81 */     if (e == null) {
/*  82: 81 */       return null;
/*  83:    */     }
/*  84: 82 */     if (!this.graph.contains(e)) {
/*  85: 83 */       if (this.equivalentElements.containsKey(e)) {
/*  86: 84 */         e = this.equivalentElements.get(e);
/*  87:    */       } else {
/*  88: 87 */         return findNeighbors(e);
/*  89:    */       }
/*  90:    */     }
/*  91: 90 */     assert (this.graph.getSuccessors(e).equals(findNeighbors(e)));
/*  92: 91 */     return this.graph.getSuccessors(e);
/*  93:    */   }
/*  94:    */   
/*  95:    */   private void merge(E seed, Set<E> neighbors)
/*  96:    */   {
/*  97: 96 */     Set<E> newElements = this.em.merge(seed, neighbors);
/*  98: 99 */     for (E neighbor : neighbors) {
/*  99:100 */       decrementOrRemove(neighbor);
/* 100:    */     }
/* 101:103 */     for (E newEl : newElements) {
/* 102:104 */       addWithoutMerge(newEl);
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   private void decrementOrRemove(E element)
/* 107:    */   {
/* 108:112 */     int count = ((Integer)this.elementCount.get(element)).intValue();
/* 109:113 */     if (count == 1)
/* 110:    */     {
/* 111:114 */       this.graph.removeNode(element);
/* 112:115 */       this.elementCount.remove(element);
/* 113:    */     }
/* 114:    */     else
/* 115:    */     {
/* 116:118 */       this.elementCount.put(element, Integer.valueOf(count - 1));
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   private Set<E> findNeighbors(E element)
/* 121:    */   {
/* 122:125 */     Map<E, Double> scores = new LinkedHashMap();
/* 123:126 */     List<E> neighbors = new LinkedList();
/* 124:127 */     for (E e : this.graph.getNodes())
/* 125:    */     {
/* 126:128 */       double score = this.dm.distance(e, element);
/* 127:129 */       scores.put(e, Double.valueOf(score));
/* 128:131 */       for (int i = neighbors.size() - 1; i > -1; i--)
/* 129:    */       {
/* 130:132 */         E n = neighbors.get(i);
/* 131:133 */         if ((i == 0) && (score < ((Double)scores.get(n)).doubleValue())) {
/* 132:134 */           neighbors.add(0, e);
/* 133:    */         }
/* 134:136 */         if (score >= ((Double)scores.get(n)).doubleValue()) {
/* 135:140 */           neighbors.add(i + 1, e);
/* 136:    */         }
/* 137:    */       }
/* 138:144 */       if (neighbors.size() > this.numNeighbors) {
/* 139:145 */         for (int i = neighbors.size(); i > this.numNeighbors - 1; i--)
/* 140:    */         {
/* 141:146 */           E dead = neighbors.get(i);
/* 142:147 */           scores.remove(dead);
/* 143:148 */           neighbors.remove(i);
/* 144:    */         }
/* 145:    */       }
/* 146:    */     }
/* 147:152 */     assert (neighbors.size() == this.numNeighbors);
/* 148:153 */     return new HashSet(neighbors);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public ConstantSom<E> clone()
/* 152:    */   {
/* 153:157 */     ConstantSom<E> clone = new ConstantSom(this.dm, this.em, this.numNeighbors);
/* 154:158 */     clone.graph = new UniGraph(this.graph);
/* 155:159 */     clone.elementCount = new HashMap(this.elementCount);
/* 156:160 */     return clone;
/* 157:    */   }
/* 158:    */   
/* 159:162 */   Set<Watcher> watchers = new HashSet();
/* 160:    */   
/* 161:    */   public void add(Watcher w)
/* 162:    */   {
/* 163:164 */     this.watchers.add(w);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public Set<E> getMemory()
/* 167:    */   {
/* 168:168 */     return new HashSet(this.graph.getNodes());
/* 169:    */   }
/* 170:    */   
/* 171:    */   public String toString()
/* 172:    */   {
/* 173:172 */     return this.graph.toString();
/* 174:    */   }
/* 175:    */   
/* 176:    */   public boolean containsEquivalent(E e)
/* 177:    */   {
/* 178:175 */     if (e == null) {
/* 179:176 */       return false;
/* 180:    */     }
/* 181:178 */     if (this.graph.contains(e)) {
/* 182:179 */       return true;
/* 183:    */     }
/* 184:181 */     if (this.equivalentElements.containsKey(e)) {
/* 185:182 */       return true;
/* 186:    */     }
/* 187:186 */     for (E n : findNeighbors(e)) {
/* 188:187 */       if (this.dm.distance(e, n) == 0.0D) {
/* 189:188 */         return true;
/* 190:    */       }
/* 191:    */     }
/* 192:192 */     return false;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public double getDistance(E e1, E e2)
/* 196:    */   {
/* 197:195 */     if ((e1 == null) || (e2 == null)) {
/* 198:195 */       return 1.0D;
/* 199:    */     }
/* 200:196 */     return this.dm.distance(e1, e2);
/* 201:    */   }
/* 202:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.soms.ConstantSom
 * JD-Core Version:    0.7.0.1
 */