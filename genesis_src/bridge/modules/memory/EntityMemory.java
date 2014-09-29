/*   1:    */ package bridge.modules.memory;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.ClassPair;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Relation;
/*   6:    */ import bridge.reps.entities.Thread;
/*   7:    */ import bridge.utils.logging.Logger;
/*   8:    */ import java.io.PrintStream;
/*   9:    */ import java.util.Collection;
/*  10:    */ import java.util.Hashtable;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.Vector;
/*  13:    */ 
/*  14:    */ public class EntityMemory
/*  15:    */   extends BasicMemory
/*  16:    */ {
/*  17: 35 */   Hashtable classCounts = new Hashtable();
/*  18: 43 */   Hashtable classDivisionCount = new Hashtable();
/*  19: 50 */   Hashtable classPairCounts = new Hashtable();
/*  20: 52 */   double classCountAverage = 0.0D;
/*  21: 53 */   double classCountDeviation = 0.0D;
/*  22:    */   
/*  23:    */   public Hashtable getClassCounts()
/*  24:    */   {
/*  25: 56 */     return this.classCounts;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Hashtable getClassDivisionCount()
/*  29:    */   {
/*  30: 60 */     return this.classDivisionCount;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Hashtable getClassPairCounts()
/*  34:    */   {
/*  35: 64 */     return this.classPairCounts;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setAverage(double d)
/*  39:    */   {
/*  40: 68 */     this.classCountAverage = d;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public double getAverage()
/*  44:    */   {
/*  45: 72 */     if (this.avgChanged) {
/*  46: 73 */       calculateAverage();
/*  47:    */     }
/*  48: 75 */     return this.classCountAverage;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public double getDeviation()
/*  52:    */   {
/*  53: 79 */     if (this.devChanged) {
/*  54: 80 */       calculateDeviation();
/*  55:    */     }
/*  56: 82 */     return this.classCountDeviation;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setDeviation(double d)
/*  60:    */   {
/*  61: 86 */     this.classCountDeviation = d;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getClassCount(String c)
/*  65:    */   {
/*  66: 95 */     Integer count = (Integer)this.classCounts.get(c);
/*  67: 96 */     if (count == null) {
/*  68: 97 */       return 0;
/*  69:    */     }
/*  70: 99 */     return count.intValue();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void refresh()
/*  74:    */   {
/*  75:104 */     this.classCounts = new Hashtable();
/*  76:105 */     this.classDivisionCount = new Hashtable();
/*  77:106 */     this.classPairCounts = new Hashtable();
/*  78:    */     
/*  79:    */ 
/*  80:109 */     Vector things = getThings();
/*  81:110 */     for (int i = 0; i < things.size(); i++)
/*  82:    */     {
/*  83:111 */       Entity thing = (Entity)things.get(i);
/*  84:112 */       thingModified(thing, null, thing.toString());
/*  85:    */     }
/*  86:115 */     calculateAverage();
/*  87:116 */     calculateDeviation();
/*  88:    */   }
/*  89:    */   
/*  90:119 */   boolean avgChanged = false;
/*  91:120 */   boolean devChanged = false;
/*  92:    */   public static final String LOGGER_GROUP = "memory";
/*  93:    */   public static final String LOGGER_INSTANCE = "Memory";
/*  94:    */   public static final String LOGGER = "memory.Memory";
/*  95:    */   
/*  96:    */   public void thingModified(Entity t, String oldState, String newState)
/*  97:    */   {
/*  98:130 */     this.avgChanged = true;
/*  99:131 */     this.devChanged = true;
/* 100:    */     
/* 101:133 */     super.thingModified(t, oldState, newState);
/* 102:134 */     Vector remove = Entity.getClassesFromString(oldState);
/* 103:135 */     Vector add = Entity.getClassesFromString(newState);
/* 104:    */     
/* 105:137 */     Vector intersection = new Vector();
/* 106:138 */     intersection.addAll(remove);
/* 107:139 */     intersection.retainAll(add);
/* 108:    */     
/* 109:141 */     remove.removeAll(intersection);
/* 110:142 */     add.removeAll(intersection);
/* 111:143 */     incrementClassCounts(add);
/* 112:144 */     decrementClassCounts(remove);
/* 113:    */     
/* 114:146 */     remove = Thread.getClassPairsFromString(oldState);
/* 115:147 */     add = Thread.getClassPairsFromString(newState);
/* 116:    */     
/* 117:149 */     intersection.clear();
/* 118:150 */     intersection.addAll(remove);
/* 119:151 */     intersection.retainAll(add);
/* 120:    */     
/* 121:153 */     remove.removeAll(intersection);
/* 122:154 */     add.removeAll(intersection);
/* 123:155 */     addClassPairs(add);
/* 124:156 */     removeClassPairs(remove);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void addClassPairs(Vector v)
/* 128:    */   {
/* 129:168 */     Hashtable cpc = getClassPairCounts();
/* 130:169 */     Hashtable cdc = getClassDivisionCount();
/* 131:170 */     for (int i = 0; i < v.size(); i++)
/* 132:    */     {
/* 133:171 */       String cp = (String)v.get(i);
/* 134:172 */       Integer pairCount = (Integer)cpc.get(cp);
/* 135:173 */       Integer homoCount = (Integer)cdc.get(ClassPair.getUpper(cp));
/* 136:174 */       if ((pairCount == null) && (homoCount == null))
/* 137:    */       {
/* 138:175 */         cdc.put(ClassPair.getUpper(cp), new Integer(1));
/* 139:176 */         cpc.put(cp, new Integer(1));
/* 140:    */       }
/* 141:177 */       else if ((pairCount == null) && (homoCount != null))
/* 142:    */       {
/* 143:178 */         cdc.put(ClassPair.getUpper(cp), new Integer(homoCount.intValue() + 1));
/* 144:179 */         cpc.put(cp, new Integer(1));
/* 145:    */       }
/* 146:    */       else
/* 147:    */       {
/* 148:181 */         cpc.put(cp, new Integer(pairCount.intValue() + 1));
/* 149:    */       }
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void removeClassPairs(Vector v)
/* 154:    */   {
/* 155:195 */     Hashtable cpc = getClassPairCounts();
/* 156:196 */     Hashtable cdc = getClassDivisionCount();
/* 157:197 */     for (int i = 0; i < v.size(); i++)
/* 158:    */     {
/* 159:198 */       String cp = (String)v.get(i);
/* 160:199 */       Integer pairCount = (Integer)cpc.get(cp);
/* 161:200 */       Integer homoCount = (Integer)cdc.get(ClassPair.getUpper(cp));
/* 162:201 */       if ((pairCount.intValue() == 1) && (homoCount.intValue() == 1))
/* 163:    */       {
/* 164:202 */         cdc.remove(ClassPair.getUpper(cp));
/* 165:203 */         cpc.remove(cp);
/* 166:    */       }
/* 167:204 */       else if ((pairCount.intValue() == 1) && (homoCount != null))
/* 168:    */       {
/* 169:205 */         cdc.put(ClassPair.getUpper(cp), new Integer(homoCount.intValue() - 1));
/* 170:206 */         cpc.remove(cp);
/* 171:    */       }
/* 172:207 */       else if ((pairCount != null) && (homoCount != null))
/* 173:    */       {
/* 174:208 */         cpc.put(cp, new Integer(pairCount.intValue() - 1));
/* 175:    */       }
/* 176:    */     }
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void incrementClassCounts(Vector v)
/* 180:    */   {
/* 181:221 */     Hashtable cc = getClassCounts();
/* 182:222 */     for (int i = 0; i < v.size(); i++)
/* 183:    */     {
/* 184:223 */       String key = (String)v.get(i);
/* 185:224 */       Integer count = (Integer)cc.get(key);
/* 186:225 */       if (count == null) {
/* 187:226 */         this.classCounts.put(key, new Integer(1));
/* 188:    */       } else {
/* 189:228 */         this.classCounts.put(key, new Integer(count.intValue() + 1));
/* 190:    */       }
/* 191:    */     }
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void decrementClassCounts(Vector v)
/* 195:    */   {
/* 196:242 */     for (int i = 0; i < v.size(); i++)
/* 197:    */     {
/* 198:243 */       String key = (String)v.get(i);
/* 199:244 */       Integer count = (Integer)this.classCounts.get(key);
/* 200:245 */       if (count != null)
/* 201:    */       {
/* 202:248 */         int c = count.intValue() - 1;
/* 203:249 */         if (c == 0) {
/* 204:250 */           this.classCounts.remove(key);
/* 205:    */         } else {
/* 206:252 */           this.classCounts.put(key, new Integer(c));
/* 207:    */         }
/* 208:    */       }
/* 209:    */     }
/* 210:    */   }
/* 211:    */   
/* 212:    */   public double calculateAverage()
/* 213:    */   {
/* 214:263 */     Collection c = getClassCounts().values();
/* 215:264 */     double sum = 0.0D;
/* 216:265 */     for (Iterator i = c.iterator(); i.hasNext();) {
/* 217:266 */       sum += ((Integer)i.next()).intValue();
/* 218:    */     }
/* 219:268 */     double result = sum / c.size();
/* 220:269 */     setAverage(result);
/* 221:270 */     this.avgChanged = false;
/* 222:271 */     return result;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public double calculateDeviation()
/* 226:    */   {
/* 227:279 */     double average = getAverage();
/* 228:280 */     Collection c = getClassCounts().values();
/* 229:281 */     double sum = 0.0D;
/* 230:282 */     for (Iterator i = c.iterator(); i.hasNext();) {
/* 231:283 */       sum += Math.pow(((Integer)i.next()).intValue() - average, 2.0D);
/* 232:    */     }
/* 233:285 */     double variance = sum / c.size();
/* 234:286 */     double result = Math.sqrt(variance);
/* 235:287 */     setDeviation(result);
/* 236:288 */     this.devChanged = false;
/* 237:289 */     return result;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void fireNotification()
/* 241:    */   {
/* 242:299 */     super.fireNotification();
/* 243:300 */     finest("Memory fireNotification() triggered.");
/* 244:    */   }
/* 245:    */   
/* 246:    */   public boolean store(Entity t)
/* 247:    */   {
/* 248:309 */     boolean b = super.store(t);
/* 249:310 */     if (b) {
/* 250:311 */       thingModified(t, "", t.toXMLSansName(false));
/* 251:    */     }
/* 252:313 */     return b;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public boolean forget(Entity t)
/* 256:    */   {
/* 257:322 */     boolean b = super.forget(t);
/* 258:323 */     if (b) {
/* 259:324 */       thingModified(t, t.toXMLSansName(false), "");
/* 260:    */     }
/* 261:326 */     return b;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void clear()
/* 265:    */   {
/* 266:335 */     super.clear();
/* 267:336 */     this.classCounts.clear();
/* 268:    */   }
/* 269:    */   
/* 270:    */   public static void main(String[] arv)
/* 271:    */   {
/* 272:345 */     Entity t1 = new Entity("Mark");
/* 273:346 */     t1.addType("parking");
/* 274:347 */     t1.addType("god");
/* 275:348 */     Entity t2 = new Entity("Steph");
/* 276:349 */     t2.addType("parking");
/* 277:350 */     t2.addType("dunce");
/* 278:351 */     Relation r1 = new Relation("siblings", t1, t2);
/* 279:352 */     r1.addType("related");
/* 280:353 */     r1.addType("fraternal");
/* 281:354 */     r1.addType("zygotes");
/* 282:355 */     Thread d1 = new Thread();
/* 283:356 */     d1.addType("related");
/* 284:357 */     d1.addType("fraternal");
/* 285:358 */     d1.addType("twins");
/* 286:359 */     Thread d2 = new Thread();
/* 287:360 */     d2.addType("features");
/* 288:361 */     d2.addType("the");
/* 289:362 */     r1.addThread(d2);
/* 290:363 */     r1.addThread(d1);
/* 291:    */     
/* 292:365 */     System.out.println("\nRelation is: " + r1);
/* 293:    */     
/* 294:    */ 
/* 295:    */ 
/* 296:    */ 
/* 297:    */ 
/* 298:371 */     EntityMemory m1 = new EntityMemory();
/* 299:372 */     m1.store(r1);
/* 300:373 */     m1.store(t1);
/* 301:374 */     m1.store(t2);
/* 302:375 */     System.out.println("\nClass counts: " + m1.getClassCounts());
/* 303:376 */     System.out.println("\nPair counts: " + m1.getClassPairCounts());
/* 304:377 */     System.out.println("\nHomogeneity counts: " + m1.getClassDivisionCount());
/* 305:    */     
/* 306:379 */     m1.forget(t2);
/* 307:380 */     System.out.println("\n\nClass counts: " + m1.getClassCounts());
/* 308:381 */     System.out.println("\nPair counts: " + m1.getClassPairCounts());
/* 309:382 */     System.out.println("\nHomogeneity counts: " + m1.getClassDivisionCount());
/* 310:    */   }
/* 311:    */   
/* 312:    */   public static Logger getLogger()
/* 313:    */   {
/* 314:434 */     return Logger.getLogger("memory.Memory");
/* 315:    */   }
/* 316:    */   
/* 317:    */   protected static void finest(Object s)
/* 318:    */   {
/* 319:438 */     Logger.getLogger("memory.Memory").finest("Memory: " + s);
/* 320:    */   }
/* 321:    */   
/* 322:    */   protected static void finer(Object s)
/* 323:    */   {
/* 324:441 */     Logger.getLogger("memory.Memory").finer("Memory: " + s);
/* 325:    */   }
/* 326:    */   
/* 327:    */   protected static void fine(Object s)
/* 328:    */   {
/* 329:444 */     Logger.getLogger("memory.Memory").fine("Memory: " + s);
/* 330:    */   }
/* 331:    */   
/* 332:    */   protected static void config(Object s)
/* 333:    */   {
/* 334:447 */     Logger.getLogger("memory.Memory").config("Memory: " + s);
/* 335:    */   }
/* 336:    */   
/* 337:    */   protected static void info(Object s)
/* 338:    */   {
/* 339:450 */     Logger.getLogger("memory.Memory").info("Memory: " + s);
/* 340:    */   }
/* 341:    */   
/* 342:    */   protected static void warning(Object s)
/* 343:    */   {
/* 344:453 */     Logger.getLogger("memory.Memory").warning("Memory: " + s);
/* 345:    */   }
/* 346:    */   
/* 347:    */   protected static void severe(Object s)
/* 348:    */   {
/* 349:456 */     Logger.getLogger("memory.Memory").severe("Memory: " + s);
/* 350:    */   }
/* 351:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.modules.memory.EntityMemory
 * JD-Core Version:    0.7.0.1
 */