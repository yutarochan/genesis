/*   1:    */ package memory.soms;
/*   2:    */ 
/*   3:    */ import ADTs.UniGraph;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Set;
/*  10:    */ import java.util.SortedMap;
/*  11:    */ import java.util.TreeMap;
/*  12:    */ import memory.multithreading.Watcher;
/*  13:    */ 
/*  14:    */ public class NewSom<E>
/*  15:    */   implements Som<E>
/*  16:    */ {
/*  17: 15 */   private UniGraph<E, Double> graph = new UniGraph();
/*  18:    */   private DistanceMetric<E> dm;
/*  19:    */   private ElementMerger<E> em;
/*  20: 20 */   private double propDist = 0.5D;
/*  21: 22 */   private Map<E, Integer> elementCount = new HashMap();
/*  22: 23 */   private Map<E, E> equivalentElements = new HashMap();
/*  23:    */   
/*  24:    */   public NewSom(DistanceMetric<E> distanceMetric, ElementMerger<E> em, double propDist)
/*  25:    */   {
/*  26: 26 */     this.dm = distanceMetric;
/*  27: 27 */     this.em = em;
/*  28: 28 */     this.propDist = propDist;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void add(E e)
/*  32:    */   {
/*  33: 31 */     if (e == null) {
/*  34: 31 */       return;
/*  35:    */     }
/*  36: 32 */     Set<E> neighbors = neighbors(e);
/*  37:    */     
/*  38: 34 */     boolean newElement = processNew(e, neighbors);
/*  39: 36 */     if (newElement)
/*  40:    */     {
/*  41: 37 */       this.elementCount.put(e, Integer.valueOf(1));
/*  42: 38 */       this.graph.addNode(e);
/*  43: 39 */       merge(e, neighbors);
/*  44:    */     }
/*  45: 42 */     for (Watcher w : this.watchers) {
/*  46: 43 */       w.ping();
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   private void addWithoutMerge(E e)
/*  51:    */   {
/*  52: 48 */     Set<E> neighbors = findNeighbors(e);
/*  53:    */     
/*  54: 50 */     boolean newElement = processNew(e, neighbors);
/*  55: 52 */     if (newElement)
/*  56:    */     {
/*  57: 53 */       this.elementCount.put(e, Integer.valueOf(1));
/*  58: 54 */       this.graph.addNode(e);
/*  59: 55 */       for (E n : neighbors) {
/*  60: 56 */         this.graph.addEdge(e, n, Double.valueOf(this.dm.distance(e, n)));
/*  61:    */       }
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   private boolean processNew(E e, Set<E> neighbors)
/*  66:    */   {
/*  67: 63 */     boolean newElement = true;
/*  68: 64 */     for (E n : neighbors) {
/*  69: 65 */       if (this.dm.distance(e, n) == 0.0D)
/*  70:    */       {
/*  71: 66 */         this.elementCount.put(n, Integer.valueOf(((Integer)this.elementCount.get(n)).intValue() + 1));
/*  72: 67 */         newElement = false;
/*  73: 68 */         this.equivalentElements.put(e, n);
/*  74: 69 */         break;
/*  75:    */       }
/*  76:    */     }
/*  77: 72 */     return newElement;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Set<E> neighbors(E e)
/*  81:    */   {
/*  82: 75 */     if (e == null) {
/*  83: 75 */       return null;
/*  84:    */     }
/*  85: 76 */     if (!this.graph.contains(e)) {
/*  86: 77 */       if (this.equivalentElements.containsKey(e)) {
/*  87: 78 */         e = this.equivalentElements.get(e);
/*  88:    */       } else {
/*  89: 81 */         return findNeighbors(e);
/*  90:    */       }
/*  91:    */     }
/*  92: 89 */     return this.graph.getSuccessors(e);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public List<E> getNearest(E e, int n)
/*  96:    */   {
/*  97:102 */     List<E> nearest = new ArrayList();
/*  98:    */     
/*  99:104 */     SortedMap<Double, Set<E>> distances = new TreeMap();
/* 100:106 */     for (E node : getMemory())
/* 101:    */     {
/* 102:107 */       double d = getDistance(e, node);
/* 103:108 */       if (distances.containsKey(Double.valueOf(d)))
/* 104:    */       {
/* 105:109 */         ((Set)distances.get(Double.valueOf(d))).add(node);
/* 106:    */       }
/* 107:    */       else
/* 108:    */       {
/* 109:112 */         Set<E> tempSet = new HashSet();
/* 110:113 */         tempSet.add(node);
/* 111:114 */         distances.put(Double.valueOf(d), tempSet);
/* 112:    */       }
/* 113:    */     }
/* 114:117 */     while ((n > 0) && (!distances.isEmpty()))
/* 115:    */     {
/* 116:118 */       Set<E> temp = (Set)distances.get(distances.firstKey());
/* 117:119 */       nearest.addAll(temp);
/* 118:120 */       n -= temp.size();
/* 119:121 */       distances.remove(distances.firstKey());
/* 120:    */     }
/* 121:123 */     return nearest;
/* 122:    */   }
/* 123:    */   
/* 124:    */   private void merge(E seed, Set<E> neighbors)
/* 125:    */   {
/* 126:128 */     Set<E> newElements = this.em.merge(seed, neighbors);
/* 127:131 */     for (E neighbor : neighbors) {
/* 128:132 */       decrementOrRemove(neighbor);
/* 129:    */     }
/* 130:135 */     for (E newEl : newElements) {
/* 131:136 */       addWithoutMerge(newEl);
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   private void decrementOrRemove(E element)
/* 136:    */   {
/* 137:144 */     int count = ((Integer)this.elementCount.get(element)).intValue();
/* 138:145 */     if (count == 1)
/* 139:    */     {
/* 140:146 */       this.graph.removeNode(element);
/* 141:147 */       this.elementCount.remove(element);
/* 142:    */     }
/* 143:    */     else
/* 144:    */     {
/* 145:150 */       this.elementCount.put(element, Integer.valueOf(count - 1));
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   private Set<E> findNeighbors(E element)
/* 150:    */   {
/* 151:156 */     Set<E> winners = new HashSet();
/* 152:157 */     if (this.equivalentElements.get(element) != null) {
/* 153:158 */       element = this.equivalentElements.get(element);
/* 154:    */     }
/* 155:160 */     for (E e : this.graph.getNodes()) {
/* 156:161 */       if ((!e.equals(element)) && 
/* 157:162 */         (this.dm.distance(element, e) < this.propDist)) {
/* 158:163 */         winners.add(e);
/* 159:    */       }
/* 160:    */     }
/* 161:166 */     assert (!winners.contains(element));
/* 162:167 */     return winners;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public NewSom<E> clone()
/* 166:    */   {
/* 167:218 */     NewSom<E> clone = new NewSom(this.dm, this.em, this.propDist);
/* 168:219 */     clone.graph = new UniGraph(this.graph);
/* 169:220 */     clone.elementCount = new HashMap(this.elementCount);
/* 170:221 */     return clone;
/* 171:    */   }
/* 172:    */   
/* 173:223 */   Set<Watcher> watchers = new HashSet();
/* 174:    */   
/* 175:    */   public void add(Watcher w)
/* 176:    */   {
/* 177:225 */     this.watchers.add(w);
/* 178:    */   }
/* 179:    */   
/* 180:    */   public Set<E> getMemory()
/* 181:    */   {
/* 182:229 */     return new HashSet(this.graph.getNodes());
/* 183:    */   }
/* 184:    */   
/* 185:    */   public double getPropDist()
/* 186:    */   {
/* 187:232 */     return this.propDist;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public String toString()
/* 191:    */   {
/* 192:236 */     return this.graph.toString();
/* 193:    */   }
/* 194:    */   
/* 195:    */   public boolean containsEquivalent(E e)
/* 196:    */   {
/* 197:239 */     if (e == null) {
/* 198:240 */       return false;
/* 199:    */     }
/* 200:242 */     if (this.graph.contains(e)) {
/* 201:243 */       return true;
/* 202:    */     }
/* 203:245 */     if (this.equivalentElements.containsKey(e)) {
/* 204:246 */       return true;
/* 205:    */     }
/* 206:250 */     for (E n : findNeighbors(e)) {
/* 207:251 */       if (this.dm.distance(e, n) == 0.0D) {
/* 208:252 */         return true;
/* 209:    */       }
/* 210:    */     }
/* 211:256 */     return false;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public double getDistance(E e1, E e2)
/* 215:    */   {
/* 216:259 */     if ((e1 == null) || (e2 == null)) {
/* 217:259 */       return 1.0D;
/* 218:    */     }
/* 219:260 */     return this.dm.distance(e1, e2);
/* 220:    */   }
/* 221:    */   
/* 222:    */   public Map<E, Integer> getWeights()
/* 223:    */   {
/* 224:264 */     return new HashMap(this.elementCount);
/* 225:    */   }
/* 226:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.soms.NewSom
 * JD-Core Version:    0.7.0.1
 */