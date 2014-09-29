/*   1:    */ package m2.models;
/*   2:    */ 
/*   3:    */ import ADTs.DirectedGraph;
/*   4:    */ import ADTs.DirectedMultiGraph;
/*   5:    */ import bridge.reps.entities.Entity;
/*   6:    */ import bridge.reps.entities.Thread;
/*   7:    */ import constants.RecognizedRepresentations;
/*   8:    */ import frames.Analogy;
/*   9:    */ import java.io.PrintStream;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Collections;
/*  12:    */ import java.util.Comparator;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import java.util.HashSet;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import java.util.Set;
/*  18:    */ import java.util.Vector;
/*  19:    */ import m2.M2;
/*  20:    */ import m2.Mem;
/*  21:    */ import m2.datatypes.Chain;
/*  22:    */ import m2.datatypes.ImmutableEntity;
/*  23:    */ import magicLess.utils.EntityUtils;
/*  24:    */ import memory.RepProcessor;
/*  25:    */ import memory.utilities.Distances;
/*  26:    */ 
/*  27:    */ public class World
/*  28:    */ {
/*  29:    */   public static final String EXPLICIT = "exact causal precedence";
/*  30:    */   public static final String CIRCUMSTANTIAL = "subject precedence";
/*  31:    */   public static final String HISTORICAL = "historical event precedence";
/*  32:    */   public static final String ANALOGICAL = "analogy from known causal relation";
/*  33: 78 */   private Map<Thread, ImmutableEntity> linestarts = new HashMap();
/*  34: 79 */   private Map<Thread, ImmutableEntity> lineends = new HashMap();
/*  35: 81 */   private DirectedMultiGraph<ImmutableEntity, Thread> coGraph = new DirectedMultiGraph();
/*  36: 82 */   private List<ImmutableEntity> history = new ArrayList();
/*  37: 85 */   private DirectedGraph<ImmutableEntity, Integer> causeGraph = new DirectedGraph();
/*  38:    */   
/*  39:    */   public synchronized void add(Entity in)
/*  40:    */   {
/*  41: 92 */     in = RepProcessor.unwrap(in);
/*  42:    */     
/*  43: 94 */     Set<Thread> cos = extractCOs(in);
/*  44: 95 */     ImmutableEntity t = new ImmutableEntity(in);
/*  45: 97 */     for (Thread co : cos) {
/*  46: 98 */       if (this.linestarts.containsKey(co))
/*  47:    */       {
/*  48:100 */         ImmutableEntity node = (ImmutableEntity)this.lineends.get(co);
/*  49:    */         
/*  50:102 */         this.coGraph.addEdge(node, t, co);
/*  51:103 */         this.lineends.put(co, t);
/*  52:    */       }
/*  53:    */       else
/*  54:    */       {
/*  55:107 */         this.coGraph.addNode(t);
/*  56:108 */         this.linestarts.put(co, t);
/*  57:109 */         this.lineends.put(co, t);
/*  58:    */       }
/*  59:    */     }
/*  60:113 */     this.history.add(t);
/*  61:116 */     if (EntityUtils.getRepType(in) == RecognizedRepresentations.CAUSE_THING) {
/*  62:117 */       processCause(in);
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public synchronized Map<String, List<Entity>> predict(Entity input2)
/*  67:    */   {
/*  68:135 */     final Entity input = RepProcessor.unwrap(input2);
/*  69:136 */     final ImmutableEntity in = new ImmutableEntity(input);
/*  70:    */     
/*  71:138 */     List<ImmutableEntity> l1 = null;
/*  72:139 */     if (this.causeGraph.contains(in))
/*  73:    */     {
/*  74:140 */       l1 = new ArrayList(this.causeGraph.getSuccessors(in));
/*  75:141 */       Collections.sort(l1, new Comparator()
/*  76:    */       {
/*  77:    */         public int compare(ImmutableEntity t1, ImmutableEntity t2)
/*  78:    */         {
/*  79:143 */           if (((Integer)World.this.causeGraph.getEdgeBetween(in, t1)).intValue() < ((Integer)World.this.causeGraph.getEdgeBetween(in, t2)).intValue()) {
/*  80:144 */             return -1;
/*  81:    */           }
/*  82:145 */           if (((Integer)World.this.causeGraph.getEdgeBetween(in, t1)).intValue() > ((Integer)World.this.causeGraph.getEdgeBetween(in, t2)).intValue()) {
/*  83:146 */             return 1;
/*  84:    */           }
/*  85:147 */           return 0;
/*  86:    */         }
/*  87:    */       });
/*  88:    */     }
/*  89:152 */     List<ImmutableEntity> l2 = null;
/*  90:154 */     if (this.coGraph.contains(in))
/*  91:    */     {
/*  92:156 */       l2 = new ArrayList(this.coGraph.getSuccessors(in));
/*  93:157 */       if (l2.isEmpty()) {
/*  94:157 */         l2 = null;
/*  95:    */       }
/*  96:    */     }
/*  97:161 */     List<ImmutableEntity> l3 = null;
/*  98:162 */     if (this.history.contains(in))
/*  99:    */     {
/* 100:163 */       List<ImmutableEntity> copy = new ArrayList(this.history);
/* 101:164 */       l3 = new ArrayList();
/* 102:165 */       while (copy.contains(in))
/* 103:    */       {
/* 104:166 */         int pos = copy.indexOf(in);
/* 105:167 */         if ((pos != -1) && (pos < copy.size() - 1)) {
/* 106:168 */           l3.add((ImmutableEntity)copy.get(pos + 1));
/* 107:    */         }
/* 108:170 */         copy.remove(pos);
/* 109:    */       }
/* 110:173 */       Collections.reverse(l3);
/* 111:174 */       if (l3.isEmpty()) {
/* 112:174 */         l3 = null;
/* 113:    */       }
/* 114:    */     }
/* 115:180 */     List<ImmutableEntity> l4 = new ArrayList();
/* 116:181 */     List<Entity> nn = M2.getMem().nearNeighbors(RepProcessor.wrap(input));
/* 117:    */     
/* 118:183 */     Collections.sort(nn, new Comparator()
/* 119:    */     {
/* 120:    */       public int compare(Entity t1, Entity t2)
/* 121:    */       {
/* 122:185 */         if (Distances.distance(t1, input) < Distances.distance(t2, input)) {
/* 123:186 */           return -1;
/* 124:    */         }
/* 125:187 */         if (Distances.distance(t1, input) > Distances.distance(t2, input)) {
/* 126:188 */           return 1;
/* 127:    */         }
/* 128:189 */         return 0;
/* 129:    */       }
/* 130:    */     });
/* 131:191 */     for (Entity baseThing : nn)
/* 132:    */     {
/* 133:192 */       final ImmutableEntity base = new ImmutableEntity(RepProcessor.unwrap(baseThing));
/* 134:193 */       if (this.causeGraph.contains(base))
/* 135:    */       {
/* 136:194 */         List<ImmutableEntity> basies = new ArrayList(this.causeGraph.getSuccessors(base));
/* 137:    */         
/* 138:196 */         Collections.sort(basies, new Comparator()
/* 139:    */         {
/* 140:    */           public int compare(ImmutableEntity t1, ImmutableEntity t2)
/* 141:    */           {
/* 142:198 */             if (((Integer)World.this.causeGraph.getEdgeBetween(base, t1)).intValue() < ((Integer)World.this.causeGraph.getEdgeBetween(base, t2)).intValue()) {
/* 143:199 */               return -1;
/* 144:    */             }
/* 145:200 */             if (((Integer)World.this.causeGraph.getEdgeBetween(base, t1)).intValue() > ((Integer)World.this.causeGraph.getEdgeBetween(base, t2)).intValue()) {
/* 146:201 */               return 1;
/* 147:    */             }
/* 148:202 */             return 0;
/* 149:    */           }
/* 150:203 */         });
/* 151:204 */         Analogy analogy = new Analogy(base.getThing(), input);
/* 152:205 */         for (ImmutableEntity basey : basies) {
/* 153:206 */           l4.add(new ImmutableEntity(analogy.targetify(basey.getThing())));
/* 154:    */         }
/* 155:    */       }
/* 156:    */     }
/* 157:249 */     Map<String, List<Entity>> results = new HashMap();
/* 158:250 */     if (l1 != null) {
/* 159:250 */       results.put("exact causal precedence", convertAndCleanList(l1));
/* 160:    */     }
/* 161:251 */     if (l2 != null) {
/* 162:251 */       results.put("subject precedence", convertAndCleanList(l2));
/* 163:    */     }
/* 164:252 */     if (l3 != null) {
/* 165:252 */       results.put("historical event precedence", convertAndCleanList(l3));
/* 166:    */     }
/* 167:253 */     if (!l4.isEmpty()) {
/* 168:253 */       results.put("analogy from known causal relation", convertAndCleanList(l4));
/* 169:    */     }
/* 170:255 */     return results;
/* 171:    */   }
/* 172:    */   
/* 173:    */   private List<Entity> convertAndCleanList(List<ImmutableEntity> lst)
/* 174:    */   {
/* 175:260 */     List<Entity> result = new ArrayList();
/* 176:261 */     for (int i = 0; i < lst.size(); i++)
/* 177:    */     {
/* 178:262 */       ImmutableEntity item = (ImmutableEntity)lst.get(i);
/* 179:263 */       if (!lst.subList(0, i).contains(item)) {
/* 180:264 */         result.add(RepProcessor.wrap(item.getThing()));
/* 181:    */       }
/* 182:    */     }
/* 183:267 */     return result;
/* 184:    */   }
/* 185:    */   
/* 186:    */   private Set<Thread> extractCOs(Entity t)
/* 187:    */   {
/* 188:273 */     List<Entity> children = Chain.flattenThing(t);
/* 189:274 */     Set<Thread> results = new HashSet();
/* 190:275 */     for (Entity c : children) {
/* 191:276 */       if (c.getTypes().contains("entity")) {
/* 192:277 */         results.add(new Thread(c.getPrimedThread()));
/* 193:    */       }
/* 194:    */     }
/* 195:280 */     return results;
/* 196:    */   }
/* 197:    */   
/* 198:    */   private void processCause(Entity cause)
/* 199:    */   {
/* 200:284 */     ImmutableEntity t1 = new ImmutableEntity(cause.getSubject());
/* 201:285 */     ImmutableEntity t2 = new ImmutableEntity(cause.getObject());
/* 202:286 */     if ((t1 != null) && (t2 != null))
/* 203:    */     {
/* 204:287 */       int edge = 1;
/* 205:288 */       if (this.causeGraph.contains(t1, t2)) {
/* 206:289 */         edge = ((Integer)this.causeGraph.getEdgeBetween(t1, t2)).intValue() + 1;
/* 207:    */       }
/* 208:291 */       this.causeGraph.addEdge(t1, t2, Integer.valueOf(edge));
/* 209:292 */       if (M2.DEBUG) {
/* 210:292 */         System.out.println("[M2] Learned from cause frame");
/* 211:    */       }
/* 212:    */     }
/* 213:    */   }
/* 214:    */   
/* 215:    */   public synchronized Entity mostRecentRep(Entity in, Object rep)
/* 216:    */   {
/* 217:298 */     if (!in.entityP())
/* 218:    */     {
/* 219:299 */       if (M2.DEBUG) {
/* 220:299 */         System.out.println("[M2] invalid input to mostRecentRep -- just input a plain Thing.");
/* 221:    */       }
/* 222:300 */       return null;
/* 223:    */     }
/* 224:302 */     ImmutableEntity co = new ImmutableEntity(in);
/* 225:303 */     ImmutableEntity repThing = null;
/* 226:304 */     ImmutableEntity next = (ImmutableEntity)this.lineends.get(co);
/* 227:305 */     while (next != null)
/* 228:    */     {
/* 229:306 */       if (EntityUtils.getRepType(next.getThing()).equals(rep))
/* 230:    */       {
/* 231:307 */         repThing = next;
/* 232:308 */         break;
/* 233:    */       }
/* 234:310 */       for (ImmutableEntity pos : this.coGraph.getPredecessors(next))
/* 235:    */       {
/* 236:311 */         if (this.coGraph.getEdgesBetween(pos, next).contains(co))
/* 237:    */         {
/* 238:312 */           next = pos;
/* 239:313 */           break;
/* 240:    */         }
/* 241:316 */         next = null;
/* 242:    */       }
/* 243:    */     }
/* 244:320 */     if (repThing == null) {
/* 245:321 */       return null;
/* 246:    */     }
/* 247:323 */     return repThing.getThing();
/* 248:    */   }
/* 249:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     m2.models.World
 * JD-Core Version:    0.7.0.1
 */