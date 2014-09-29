/*   1:    */ package memory;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import constants.RecognizedRepresentations;
/*   5:    */ import frames.BlockFrame;
/*   6:    */ import frames.ForceFrame;
/*   7:    */ import frames.FrameFactory;
/*   8:    */ import frames.GeometryFrame;
/*   9:    */ import frames.PlaceFrame;
/*  10:    */ import frames.TrajectoryFrame;
/*  11:    */ import frames.TransitionFrame;
/*  12:    */ import java.io.PrintStream;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Collections;
/*  15:    */ import java.util.Comparator;
/*  16:    */ import java.util.HashMap;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import java.util.Set;
/*  20:    */ import java.util.concurrent.BlockingQueue;
/*  21:    */ import java.util.concurrent.LinkedBlockingQueue;
/*  22:    */ import memory.multithreading.Task;
/*  23:    */ import memory.multithreading.Watcher;
/*  24:    */ import memory.multithreading.Worker;
/*  25:    */ import memory.soms.NewSom;
/*  26:    */ import memory.soms.Som;
/*  27:    */ import memory.soms.mergers.ConditionalMerger;
/*  28:    */ import memory.soms.metrics.EntityMetric;
/*  29:    */ 
/*  30:    */ public final class OldSomSpace
/*  31:    */ {
/*  32: 46 */   private final Map<String, Som<Entity>> soms = new HashMap();
/*  33: 47 */   private final Map<String, Worker> workers = new HashMap();
/*  34: 49 */   private final Map<Integer, Entity> originals = new HashMap();
/*  35:    */   public static final String OTHER = "other";
/*  36:    */   
/*  37:    */   public OldSomSpace()
/*  38:    */   {
/*  39: 59 */     this.soms.put(BlockFrame.FRAMETYPE, 
/*  40: 60 */       new NewSom(new EntityMetric(), new ConditionalMerger(), 0.3D));
/*  41: 61 */     this.soms.put(ForceFrame.FRAMETYPE, 
/*  42: 62 */       new NewSom(new EntityMetric(), new ConditionalMerger(), 0.3D));
/*  43: 63 */     this.soms.put(GeometryFrame.FRAMETYPE, 
/*  44: 64 */       new NewSom(new EntityMetric(), new ConditionalMerger(), 0.3D));
/*  45: 65 */     this.soms.put((String)RecognizedRepresentations.PATH_THING, 
/*  46: 66 */       new NewSom(new EntityMetric(), new ConditionalMerger(), 0.3D));
/*  47: 67 */     this.soms.put(PlaceFrame.FRAMETYPE, 
/*  48: 68 */       new NewSom(new EntityMetric(), new ConditionalMerger(), 0.3D));
/*  49: 69 */     this.soms.put(TrajectoryFrame.FRAMETYPE, 
/*  50: 70 */       new NewSom(new EntityMetric(), new ConditionalMerger(), 0.5D));
/*  51: 71 */     this.soms.put(TransitionFrame.FRAMETYPE, 
/*  52: 72 */       new NewSom(new EntityMetric(), new ConditionalMerger(), 0.3D));
/*  53: 73 */     this.soms.put((String)RecognizedRepresentations.TIME_REPRESENTATION, 
/*  54: 74 */       new NewSom(new EntityMetric(), new ConditionalMerger(), 0.3D));
/*  55: 75 */     this.soms.put("other", 
/*  56: 76 */       new NewSom(new EntityMetric(), new ConditionalMerger(), 0.3D));
/*  57: 80 */     for (final String type : this.soms.keySet())
/*  58:    */     {
/*  59: 81 */       this.workers.put(type, new Worker());
/*  60: 82 */       ((Som)this.soms.get(type)).add(new Watcher()
/*  61:    */       {
/*  62:    */         public void ping()
/*  63:    */         {
/*  64: 84 */           Memory.getMemory().outputSOM(type);
/*  65:    */         }
/*  66:    */       });
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void add(Entity tRaw)
/*  71:    */   {
/*  72: 90 */     final Entity t = tRaw.deepClone();
/*  73: 91 */     this.originals.put(Integer.valueOf(t.getID()), tRaw);
/*  74: 92 */     final String type = FrameFactory.getFrameType(t);
/*  75: 93 */     final Som<Entity> som = (Som)this.soms.get(type);
/*  76: 94 */     if (som != null)
/*  77:    */     {
/*  78: 96 */       ((Worker)this.workers.get(type)).put(new Task()
/*  79:    */       {
/*  80:    */         public void execute()
/*  81:    */         {
/*  82: 98 */           som.add(t);
/*  83: 99 */           if (Memory.DEBUG) {
/*  84: 99 */             System.out.println("[MEMORY] Added " + type + " to SOM");
/*  85:    */           }
/*  86:    */         }
/*  87:    */       });
/*  88:    */     }
/*  89:    */     else
/*  90:    */     {
/*  91:104 */       final Som<Entity> otherSom = (Som)this.soms.get("other");
/*  92:    */       
/*  93:106 */       ((Worker)this.workers.get("other")).put(new Task()
/*  94:    */       {
/*  95:    */         public void execute()
/*  96:    */         {
/*  97:108 */           otherSom.add(t);
/*  98:109 */           if (Memory.DEBUG) {
/*  99:109 */             System.out.println("[MEMORY] Added " + type + " to SOM");
/* 100:    */           }
/* 101:    */         }
/* 102:    */       });
/* 103:112 */       if (Memory.DEBUG) {
/* 104:112 */         System.out.println("[MEMORY] No SOM for: " + type);
/* 105:    */       }
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean contains(final Entity t)
/* 110:    */   {
/* 111:118 */     String type = FrameFactory.getFrameType(t);
/* 112:119 */     Som<Entity> s = (Som)this.soms.get(type);
/* 113:120 */     if (s == null)
/* 114:    */     {
/* 115:121 */       type = "other";
/* 116:122 */       s = (Som)this.soms.get(type);
/* 117:    */     }
/* 118:124 */     final Som<Entity> s2 = s;
/* 119:125 */     String type2 = type;
/* 120:126 */     final BlockingQueue<Boolean> q = new LinkedBlockingQueue();
/* 121:127 */     if (this.soms.get(type) != null)
/* 122:    */     {
/* 123:129 */       ((Worker)this.workers.get(type2)).put(new Task()
/* 124:    */       {
/* 125:    */         public void execute()
/* 126:    */         {
/* 127:131 */           if (s2.containsEquivalent(t)) {
/* 128:    */             try
/* 129:    */             {
/* 130:132 */               q.put(Boolean.valueOf(true));
/* 131:    */             }
/* 132:    */             catch (InterruptedException e)
/* 133:    */             {
/* 134:133 */               System.err.println("[MEMORY] Worker Thread Interrupted");
/* 135:    */             }
/* 136:    */           } else {
/* 137:    */             try
/* 138:    */             {
/* 139:136 */               q.put(Boolean.valueOf(false));
/* 140:    */             }
/* 141:    */             catch (InterruptedException e)
/* 142:    */             {
/* 143:137 */               System.err.println("[MEMORY] Worker Thread Interrupted");
/* 144:    */             }
/* 145:    */           }
/* 146:    */         }
/* 147:    */       });
/* 148:    */       for (;;)
/* 149:    */       {
/* 150:    */         try
/* 151:    */         {
/* 152:142 */           return ((Boolean)q.take()).booleanValue();
/* 153:    */         }
/* 154:    */         catch (InterruptedException e)
/* 155:    */         {
/* 156:143 */           System.err.println("[MEMORY] Worker Thread Interrupted");
/* 157:    */         }
/* 158:    */       }
/* 159:    */     }
/* 160:147 */     return false;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public List<Entity> getNeighbors(final Entity t)
/* 164:    */   {
/* 165:158 */     final String type = FrameFactory.getFrameType(t);
/* 166:160 */     if (this.soms.get(type) != null)
/* 167:    */     {
/* 168:161 */       final BlockingQueue<List<Entity>> q = new LinkedBlockingQueue();
/* 169:    */       
/* 170:163 */       ((Worker)this.workers.get(type)).put(new Task()
/* 171:    */       {
/* 172:    */         public void execute()
/* 173:    */         {
/* 174:165 */           final Som<Entity> s = (Som)OldSomSpace.this.soms.get(type);
/* 175:166 */           Set<Entity> neighbors = s.neighbors(t);
/* 176:167 */           List<Entity> sorted = new ArrayList(neighbors);
/* 177:168 */           Collections.sort(sorted, new Comparator()
/* 178:    */           {
/* 179:    */             public int compare(Entity e1, Entity e2)
/* 180:    */             {
/* 181:170 */               double s1 = s.getDistance(this.val$t, e1);
/* 182:171 */               double s2 = s.getDistance(this.val$t, e2);
/* 183:172 */               if (s1 < s2) {
/* 184:172 */                 return -1;
/* 185:    */               }
/* 186:173 */               if (s1 > s2) {
/* 187:173 */                 return 1;
/* 188:    */               }
/* 189:174 */               return 0;
/* 190:    */             }
/* 191:    */           });
/* 192:    */           for (;;)
/* 193:    */           {
/* 194:    */             try
/* 195:    */             {
/* 196:178 */               q.put(sorted);
/* 197:    */             }
/* 198:    */             catch (InterruptedException e)
/* 199:    */             {
/* 200:179 */               System.err.println("[MEMORY] Worker Thread Interrupted");
/* 201:    */             }
/* 202:    */           }
/* 203:    */         }
/* 204:182 */       });
/* 205:183 */       List<Entity> things = null;
/* 206:184 */       while (things == null) {
/* 207:    */         try
/* 208:    */         {
/* 209:185 */           things = (List)q.take();
/* 210:    */         }
/* 211:    */         catch (InterruptedException ie)
/* 212:    */         {
/* 213:186 */           System.err.println("[MEMORY] Worker Thread Interrupted");
/* 214:    */         }
/* 215:    */       }
/* 216:188 */       return things;
/* 217:    */     }
/* 218:191 */     final BlockingQueue<List<Entity>> q = new LinkedBlockingQueue();
/* 219:    */     
/* 220:193 */     ((Worker)this.workers.get("other")).put(new Task()
/* 221:    */     {
/* 222:    */       public void execute()
/* 223:    */       {
/* 224:195 */         final Som<Entity> s = (Som)OldSomSpace.this.soms.get("other");
/* 225:196 */         Set<Entity> neighbors = s.neighbors(t);
/* 226:197 */         List<Entity> sorted = new ArrayList(neighbors);
/* 227:198 */         Collections.sort(sorted, new Comparator()
/* 228:    */         {
/* 229:    */           public int compare(Entity e1, Entity e2)
/* 230:    */           {
/* 231:200 */             double s1 = s.getDistance(this.val$t, e1);
/* 232:201 */             double s2 = s.getDistance(this.val$t, e2);
/* 233:202 */             if (s1 < s2) {
/* 234:202 */               return -1;
/* 235:    */             }
/* 236:203 */             if (s1 > s2) {
/* 237:203 */               return 1;
/* 238:    */             }
/* 239:204 */             return 0;
/* 240:    */           }
/* 241:    */         });
/* 242:    */         for (;;)
/* 243:    */         {
/* 244:    */           try
/* 245:    */           {
/* 246:208 */             q.put(sorted);
/* 247:    */           }
/* 248:    */           catch (InterruptedException e)
/* 249:    */           {
/* 250:209 */             System.err.println("[MEMORY] Worker Thread Interrupted");
/* 251:    */           }
/* 252:    */         }
/* 253:    */       }
/* 254:212 */     });
/* 255:213 */     List<Entity> things = null;
/* 256:214 */     while (things == null) {
/* 257:    */       try
/* 258:    */       {
/* 259:215 */         things = (List)q.take();
/* 260:    */       }
/* 261:    */       catch (InterruptedException ie)
/* 262:    */       {
/* 263:216 */         System.err.println("[MEMORY] Worker Thread Interrupted");
/* 264:    */       }
/* 265:    */     }
/* 266:219 */     if (Memory.DEBUG) {
/* 267:219 */       System.out.println("[MEMORY] Received request for neighbors of an unsupported frame type: " + t);
/* 268:    */     }
/* 269:221 */     return things;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public Som<Entity> getSom(final String type)
/* 273:    */   {
/* 274:231 */     final BlockingQueue<Som<Entity>> q = new LinkedBlockingQueue();
/* 275:    */     
/* 276:233 */     ((Worker)this.workers.get(type)).put(new Task()
/* 277:    */     {
/* 278:    */       public void execute()
/* 279:    */       {
/* 280:    */         try
/* 281:    */         {
/* 282:235 */           q.put(((Som)OldSomSpace.this.soms.get(type)).clone());
/* 283:    */         }
/* 284:    */         catch (InterruptedException e)
/* 285:    */         {
/* 286:236 */           System.err.println("[MEMORY] Worker Thread Interrupted");
/* 287:    */         }
/* 288:    */       }
/* 289:    */     });
/* 290:    */     for (;;)
/* 291:    */     {
/* 292:    */       try
/* 293:    */       {
/* 294:240 */         return (Som)q.take();
/* 295:    */       }
/* 296:    */       catch (InterruptedException e)
/* 297:    */       {
/* 298:241 */         System.err.println("[MEMORY] Worker Thread Interrupted");
/* 299:    */       }
/* 300:    */     }
/* 301:    */   }
/* 302:    */   
/* 303:    */   public static void main(String[] args)
/* 304:    */   {
/* 305:246 */     OldSomSpace soms = new OldSomSpace();
/* 306:247 */     for (String s : soms.soms.keySet()) {
/* 307:248 */       System.out.println(s);
/* 308:    */     }
/* 309:    */   }
/* 310:    */   
/* 311:    */   public List<Entity> getNearest(final Entity t, final int n)
/* 312:    */   {
/* 313:253 */     final String type = FrameFactory.getFrameType(t);
/* 314:254 */     final BlockingQueue<List<Entity>> q = new LinkedBlockingQueue();
/* 315:255 */     if (this.soms.get(type) != null)
/* 316:    */     {
/* 317:257 */       ((Worker)this.workers.get(type)).put(new Task()
/* 318:    */       {
/* 319:    */         public void execute()
/* 320:    */         {
/* 321:259 */           Som<Entity> s = (Som)OldSomSpace.this.soms.get(type);
/* 322:260 */           NewSom<Entity> ns = (NewSom)s;
/* 323:    */           try
/* 324:    */           {
/* 325:261 */             q.put(ns.getNearest(t, n));
/* 326:    */           }
/* 327:    */           catch (InterruptedException e)
/* 328:    */           {
/* 329:262 */             System.err.println("[MEMORY] Worker Thread Interrupted");
/* 330:    */           }
/* 331:    */         }
/* 332:    */       });
/* 333:    */       for (;;)
/* 334:    */       {
/* 335:    */         try
/* 336:    */         {
/* 337:266 */           return (List)q.take();
/* 338:    */         }
/* 339:    */         catch (InterruptedException e)
/* 340:    */         {
/* 341:267 */           System.err.println("[MEMORY] Worker Thread Interrupted");
/* 342:    */         }
/* 343:    */       }
/* 344:    */     }
/* 345:270 */     return new ArrayList();
/* 346:    */   }
/* 347:    */   
/* 348:    */   public int getFrequency(final Entity t)
/* 349:    */   {
/* 350:275 */     final String type = FrameFactory.getFrameType(t);
/* 351:276 */     final BlockingQueue<Integer> q = new LinkedBlockingQueue();
/* 352:277 */     if (this.soms.get(type) != null)
/* 353:    */     {
/* 354:279 */       ((Worker)this.workers.get(type)).put(new Task()
/* 355:    */       {
/* 356:    */         public void execute()
/* 357:    */         {
/* 358:281 */           Som<Entity> s = (Som)OldSomSpace.this.soms.get(type);
/* 359:282 */           NewSom<Entity> ns = (NewSom)s;
/* 360:    */           try
/* 361:    */           {
/* 362:283 */             q.put((Integer)ns.getWeights().get(t));
/* 363:    */           }
/* 364:    */           catch (InterruptedException e)
/* 365:    */           {
/* 366:284 */             System.err.println("[MEMORY] Worker Thread Interrupted");
/* 367:    */           }
/* 368:    */         }
/* 369:    */       });
/* 370:    */       for (;;)
/* 371:    */       {
/* 372:    */         try
/* 373:    */         {
/* 374:288 */           return ((Integer)q.take()).intValue();
/* 375:    */         }
/* 376:    */         catch (InterruptedException e)
/* 377:    */         {
/* 378:289 */           System.err.println("[MEMORY] Worker Thread Interrupted");
/* 379:    */         }
/* 380:    */       }
/* 381:    */     }
/* 382:293 */     return 0;
/* 383:    */   }
/* 384:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.OldSomSpace
 * JD-Core Version:    0.7.0.1
 */