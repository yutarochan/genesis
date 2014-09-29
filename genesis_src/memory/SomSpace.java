/*   1:    */ package memory;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import constants.RecognizedRepresentations;
/*   5:    */ import frames.ActionFrame;
/*   6:    */ import frames.BlockFrame;
/*   7:    */ import frames.CauseFrame;
/*   8:    */ import frames.ForceFrame;
/*   9:    */ import frames.FrameFactory;
/*  10:    */ import frames.GeometryFrame;
/*  11:    */ import frames.PlaceFrame;
/*  12:    */ import frames.SOMRoleFrame;
/*  13:    */ import frames.TrajectoryFrame;
/*  14:    */ import frames.TransferFrame;
/*  15:    */ import frames.TransitionFrame;
/*  16:    */ import java.io.PrintStream;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.Collections;
/*  19:    */ import java.util.Comparator;
/*  20:    */ import java.util.HashMap;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import java.util.Set;
/*  24:    */ import java.util.concurrent.BlockingQueue;
/*  25:    */ import java.util.concurrent.LinkedBlockingQueue;
/*  26:    */ import memory.multithreading.Watcher;
/*  27:    */ import memory.soms.NewSom;
/*  28:    */ import memory.soms.Som;
/*  29:    */ import memory.soms.mergers.ConditionalMerger;
/*  30:    */ import memory.soms.metrics.EntityMetric;
/*  31:    */ 
/*  32:    */ public final class SomSpace
/*  33:    */ {
/*  34: 27 */   private final Map<String, Som<Entity>> soms = new HashMap();
/*  35: 30 */   private final Map<Integer, Entity> originals = new HashMap();
/*  36:    */   public static final String OTHER = "other";
/*  37:    */   
/*  38:    */   public SomSpace()
/*  39:    */   {
/*  40: 40 */     this.soms.put(BlockFrame.FRAMETYPE, 
/*  41: 41 */       new NewSom(new EntityMetric(), new ConditionalMerger(), 0.3D));
/*  42: 42 */     this.soms.put(ForceFrame.FRAMETYPE, 
/*  43: 43 */       new NewSom(new EntityMetric(), new ConditionalMerger(), 0.3D));
/*  44: 44 */     this.soms.put(GeometryFrame.FRAMETYPE, 
/*  45: 45 */       new NewSom(new EntityMetric(), new ConditionalMerger(), 0.3D));
/*  46: 46 */     this.soms.put((String)RecognizedRepresentations.PATH_THING, 
/*  47: 47 */       new NewSom(new EntityMetric(), new ConditionalMerger(), 0.3D));
/*  48: 48 */     this.soms.put(PlaceFrame.FRAMETYPE, 
/*  49: 49 */       new NewSom(new EntityMetric(), new ConditionalMerger(), 0.3D));
/*  50: 50 */     this.soms.put(SOMRoleFrame.FRAMETYPE, 
/*  51: 51 */       new NewSom(new EntityMetric(), new ConditionalMerger(), 0.5D));
/*  52: 52 */     this.soms.put(TrajectoryFrame.FRAMETYPE, 
/*  53: 53 */       new NewSom(new EntityMetric(), new ConditionalMerger(), 0.5D));
/*  54: 54 */     this.soms.put(TransitionFrame.FRAMETYPE, 
/*  55: 55 */       new NewSom(new EntityMetric(), new ConditionalMerger(), 0.3D));
/*  56: 56 */     this.soms.put(TransferFrame.FRAMETYPE, 
/*  57: 57 */       new NewSom(new EntityMetric(), new ConditionalMerger(), 0.3D));
/*  58: 58 */     this.soms.put(CauseFrame.FRAMETYPE, 
/*  59: 59 */       new NewSom(new EntityMetric(), new ConditionalMerger(), 0.3D));
/*  60: 60 */     this.soms.put((String)RecognizedRepresentations.TIME_REPRESENTATION, 
/*  61: 61 */       new NewSom(new EntityMetric(), new ConditionalMerger(), 0.3D));
/*  62: 62 */     this.soms.put(ActionFrame.FRAMETYPE, 
/*  63: 63 */       new NewSom(new EntityMetric(), new ConditionalMerger(), 0.5D));
/*  64: 64 */     this.soms.put((String)RecognizedRepresentations.SOCIAL_REPRESENTATION, 
/*  65: 65 */       new NewSom(new EntityMetric(), new ConditionalMerger(), 0.5D));
/*  66: 66 */     this.soms.put("other", 
/*  67: 67 */       new NewSom(new EntityMetric(), new ConditionalMerger(), 0.3D));
/*  68: 71 */     for (final String type : this.soms.keySet()) {
/*  69: 74 */       ((Som)this.soms.get(type)).add(new Watcher()
/*  70:    */       {
/*  71:    */         public void ping()
/*  72:    */         {
/*  73: 76 */           Memory.getMemory().outputSOM(type);
/*  74:    */         }
/*  75:    */       });
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   public synchronized void add(Entity tRaw)
/*  80:    */   {
/*  81: 82 */     Entity t = tRaw.deepClone();
/*  82: 83 */     this.originals.put(Integer.valueOf(t.getID()), tRaw);
/*  83: 84 */     String type = FrameFactory.getFrameType(t);
/*  84: 85 */     Som<Entity> som = (Som)this.soms.get(type);
/*  85: 86 */     if (som != null)
/*  86:    */     {
/*  87: 90 */       som.add(t);
/*  88: 91 */       if (Memory.DEBUG) {
/*  89: 91 */         System.out.println("[MEMORY] Added " + type + " to SOM");
/*  90:    */       }
/*  91:    */     }
/*  92:    */     else
/*  93:    */     {
/*  94: 96 */       Som<Entity> otherSom = (Som)this.soms.get("other");
/*  95:    */       
/*  96:    */ 
/*  97:    */ 
/*  98:100 */       otherSom.add(t);
/*  99:101 */       if (Memory.DEBUG) {
/* 100:101 */         System.out.println("[MEMORY] Added " + type + " to SOM");
/* 101:    */       }
/* 102:104 */       if (Memory.DEBUG) {
/* 103:104 */         System.out.println("[MEMORY] No SOM for: " + type);
/* 104:    */       }
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public synchronized boolean contains(Entity t)
/* 109:    */   {
/* 110:110 */     String type = FrameFactory.getFrameType(t);
/* 111:111 */     Som<Entity> s = (Som)this.soms.get(type);
/* 112:112 */     if (s == null)
/* 113:    */     {
/* 114:113 */       type = "other";
/* 115:114 */       s = (Som)this.soms.get(type);
/* 116:    */     }
/* 117:116 */     Som<Entity> s2 = s;
/* 118:117 */     String type2 = type;
/* 119:118 */     BlockingQueue<Boolean> q = new LinkedBlockingQueue();
/* 120:119 */     if (this.soms.get(type) != null)
/* 121:    */     {
/* 122:123 */       if (s2.containsEquivalent(t)) {
/* 123:    */         try
/* 124:    */         {
/* 125:124 */           q.put(Boolean.valueOf(true));
/* 126:    */         }
/* 127:    */         catch (InterruptedException e)
/* 128:    */         {
/* 129:125 */           System.err.println("[MEMORY] Worker Thread Interrupted");
/* 130:    */         }
/* 131:    */       } else {
/* 132:    */         try
/* 133:    */         {
/* 134:128 */           q.put(Boolean.valueOf(false));
/* 135:    */         }
/* 136:    */         catch (InterruptedException e)
/* 137:    */         {
/* 138:129 */           System.err.println("[MEMORY] Worker Thread Interrupted");
/* 139:    */         }
/* 140:    */       }
/* 141:    */       for (;;)
/* 142:    */       {
/* 143:    */         try
/* 144:    */         {
/* 145:134 */           return ((Boolean)q.take()).booleanValue();
/* 146:    */         }
/* 147:    */         catch (InterruptedException e)
/* 148:    */         {
/* 149:135 */           System.err.println("[MEMORY] Worker Thread Interrupted");
/* 150:    */         }
/* 151:    */       }
/* 152:    */     }
/* 153:139 */     return false;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public synchronized List<Entity> getNeighbors(final Entity t)
/* 157:    */   {
/* 158:150 */     String type = FrameFactory.getFrameType(t);
/* 159:152 */     if (this.soms.get(type) != null)
/* 160:    */     {
/* 161:153 */       BlockingQueue<List<Entity>> q = new LinkedBlockingQueue();
/* 162:    */       
/* 163:    */ 
/* 164:    */ 
/* 165:157 */       final Som<Entity> s = (Som)this.soms.get(type);
/* 166:158 */       Set<Entity> neighbors = s.neighbors(t);
/* 167:159 */       List<Entity> sorted = new ArrayList(neighbors);
/* 168:160 */       Collections.sort(sorted, new Comparator()
/* 169:    */       {
/* 170:    */         public int compare(Entity e1, Entity e2)
/* 171:    */         {
/* 172:162 */           double s1 = s.getDistance(t, e1);
/* 173:163 */           double s2 = s.getDistance(t, e2);
/* 174:164 */           if (s1 < s2) {
/* 175:164 */             return -1;
/* 176:    */           }
/* 177:165 */           if (s1 > s2) {
/* 178:165 */             return 1;
/* 179:    */           }
/* 180:166 */           return 0;
/* 181:    */         }
/* 182:    */       });
/* 183:    */       for (;;)
/* 184:    */       {
/* 185:    */         try
/* 186:    */         {
/* 187:170 */           q.put(sorted);
/* 188:    */         }
/* 189:    */         catch (InterruptedException e)
/* 190:    */         {
/* 191:171 */           System.err.println("[MEMORY] Worker Thread Interrupted");
/* 192:    */         }
/* 193:    */       }
/* 194:175 */       List<Entity> things = null;
/* 195:176 */       while (things == null) {
/* 196:    */         try
/* 197:    */         {
/* 198:177 */           things = (List)q.take();
/* 199:    */         }
/* 200:    */         catch (InterruptedException ie)
/* 201:    */         {
/* 202:178 */           System.err.println("[MEMORY] Worker Thread Interrupted");
/* 203:    */         }
/* 204:    */       }
/* 205:180 */       return things;
/* 206:    */     }
/* 207:183 */     BlockingQueue<List<Entity>> q = new LinkedBlockingQueue();
/* 208:    */     
/* 209:    */ 
/* 210:    */ 
/* 211:187 */     final Som<Entity> s = (Som)this.soms.get("other");
/* 212:188 */     Set<Entity> neighbors = s.neighbors(t);
/* 213:189 */     List<Entity> sorted = new ArrayList(neighbors);
/* 214:190 */     Collections.sort(sorted, new Comparator()
/* 215:    */     {
/* 216:    */       public int compare(Entity e1, Entity e2)
/* 217:    */       {
/* 218:192 */         double s1 = s.getDistance(t, e1);
/* 219:193 */         double s2 = s.getDistance(t, e2);
/* 220:194 */         if (s1 < s2) {
/* 221:194 */           return -1;
/* 222:    */         }
/* 223:195 */         if (s1 > s2) {
/* 224:195 */           return 1;
/* 225:    */         }
/* 226:196 */         return 0;
/* 227:    */       }
/* 228:    */     });
/* 229:    */     for (;;)
/* 230:    */     {
/* 231:    */       try
/* 232:    */       {
/* 233:200 */         q.put(sorted);
/* 234:    */       }
/* 235:    */       catch (InterruptedException e)
/* 236:    */       {
/* 237:201 */         System.err.println("[MEMORY] Worker Thread Interrupted");
/* 238:    */       }
/* 239:    */     }
/* 240:205 */     List<Entity> things = null;
/* 241:206 */     while (things == null) {
/* 242:    */       try
/* 243:    */       {
/* 244:207 */         things = (List)q.take();
/* 245:    */       }
/* 246:    */       catch (InterruptedException ie)
/* 247:    */       {
/* 248:208 */         System.err.println("[MEMORY] Worker Thread Interrupted");
/* 249:    */       }
/* 250:    */     }
/* 251:211 */     if (Memory.DEBUG) {
/* 252:211 */       System.out.println("[MEMORY] Received request for neighbors of an unsupported frame type: " + t);
/* 253:    */     }
/* 254:213 */     return things;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public synchronized Som<Entity> getSom(String type)
/* 258:    */   {
/* 259:223 */     BlockingQueue<Som<Entity>> q = new LinkedBlockingQueue();
/* 260:    */     try
/* 261:    */     {
/* 262:227 */       q.put(((Som)this.soms.get(type)).clone());
/* 263:    */     }
/* 264:    */     catch (InterruptedException e)
/* 265:    */     {
/* 266:228 */       System.err.println("[MEMORY] Worker Thread Interrupted");
/* 267:    */     }
/* 268:    */     for (;;)
/* 269:    */     {
/* 270:    */       try
/* 271:    */       {
/* 272:232 */         return (Som)q.take();
/* 273:    */       }
/* 274:    */       catch (InterruptedException e)
/* 275:    */       {
/* 276:233 */         System.err.println("[MEMORY] Worker Thread Interrupted");
/* 277:    */       }
/* 278:    */     }
/* 279:    */   }
/* 280:    */   
/* 281:    */   public static void main(String[] args)
/* 282:    */   {
/* 283:238 */     SomSpace soms = new SomSpace();
/* 284:239 */     for (String s : soms.soms.keySet()) {
/* 285:240 */       System.out.println(s);
/* 286:    */     }
/* 287:    */   }
/* 288:    */   
/* 289:    */   public synchronized List<Entity> getNearest(Entity t, int n)
/* 290:    */   {
/* 291:245 */     String type = FrameFactory.getFrameType(t);
/* 292:246 */     BlockingQueue<List<Entity>> q = new LinkedBlockingQueue();
/* 293:247 */     if (this.soms.get(type) != null)
/* 294:    */     {
/* 295:251 */       Som<Entity> s = (Som)this.soms.get(type);
/* 296:252 */       NewSom<Entity> ns = (NewSom)s;
/* 297:    */       try
/* 298:    */       {
/* 299:253 */         q.put(ns.getNearest(t, n));
/* 300:    */       }
/* 301:    */       catch (InterruptedException e)
/* 302:    */       {
/* 303:254 */         System.err.println("[MEMORY] Worker Thread Interrupted");
/* 304:    */       }
/* 305:    */       for (;;)
/* 306:    */       {
/* 307:    */         try
/* 308:    */         {
/* 309:258 */           return (List)q.take();
/* 310:    */         }
/* 311:    */         catch (InterruptedException e)
/* 312:    */         {
/* 313:259 */           System.err.println("[MEMORY] Worker Thread Interrupted");
/* 314:    */         }
/* 315:    */       }
/* 316:    */     }
/* 317:262 */     return new ArrayList();
/* 318:    */   }
/* 319:    */   
/* 320:    */   public synchronized int getFrequency(Entity t)
/* 321:    */   {
/* 322:267 */     String type = FrameFactory.getFrameType(t);
/* 323:268 */     BlockingQueue<Integer> q = new LinkedBlockingQueue();
/* 324:269 */     if (this.soms.get(type) != null)
/* 325:    */     {
/* 326:273 */       Som<Entity> s = (Som)this.soms.get(type);
/* 327:274 */       NewSom<Entity> ns = (NewSom)s;
/* 328:    */       try
/* 329:    */       {
/* 330:275 */         q.put((Integer)ns.getWeights().get(t));
/* 331:    */       }
/* 332:    */       catch (InterruptedException e)
/* 333:    */       {
/* 334:276 */         System.err.println("[MEMORY] Worker Thread Interrupted");
/* 335:    */       }
/* 336:    */       for (;;)
/* 337:    */       {
/* 338:    */         try
/* 339:    */         {
/* 340:280 */           return ((Integer)q.take()).intValue();
/* 341:    */         }
/* 342:    */         catch (InterruptedException e)
/* 343:    */         {
/* 344:281 */           System.err.println("[MEMORY] Worker Thread Interrupted");
/* 345:    */         }
/* 346:    */       }
/* 347:    */     }
/* 348:285 */     return 0;
/* 349:    */   }
/* 350:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.SomSpace
 * JD-Core Version:    0.7.0.1
 */