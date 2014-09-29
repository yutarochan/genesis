/*   1:    */ package memory.operators;
/*   2:    */ 
/*   3:    */ import bridge.modules.memory.BasicMemory;
/*   4:    */ import bridge.reps.entities.Bundle;
/*   5:    */ import bridge.reps.entities.Entity;
/*   6:    */ import bridge.reps.entities.Function;
/*   7:    */ import bridge.reps.entities.Sequence;
/*   8:    */ import bridge.reps.entities.Thread;
/*   9:    */ import java.io.PrintStream;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.HashSet;
/*  12:    */ import java.util.Iterator;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Set;
/*  15:    */ import java.util.Vector;
/*  16:    */ 
/*  17:    */ @Deprecated
/*  18:    */ public class Operators
/*  19:    */ {
/*  20:    */   public static double compare(Thread t1, Thread t2)
/*  21:    */   {
/*  22: 54 */     if ((t1 == null) && (t2 == null)) {
/*  23: 54 */       return 0.0D;
/*  24:    */     }
/*  25: 55 */     if ((t1 == null) || (t2 == null)) {
/*  26: 55 */       return 1.0D;
/*  27:    */     }
/*  28: 56 */     List<String> a = new ArrayList(t1);
/*  29: 57 */     List<String> b = new ArrayList(t2);
/*  30:    */     
/*  31: 59 */     int i = 0;
/*  32: 60 */     while (i < a.size())
/*  33:    */     {
/*  34: 61 */       int j = 0;
/*  35: 62 */       boolean advance = true;
/*  36: 63 */       while (j < b.size())
/*  37:    */       {
/*  38: 64 */         if (((String)a.get(i)).equals(b.get(j)))
/*  39:    */         {
/*  40: 65 */           a.remove(i);
/*  41: 66 */           b.remove(j);
/*  42: 67 */           advance = false;
/*  43: 68 */           break;
/*  44:    */         }
/*  45: 70 */         j++;
/*  46:    */       }
/*  47: 72 */       if (advance) {
/*  48: 72 */         i++;
/*  49:    */       }
/*  50:    */     }
/*  51: 75 */     int unmatched = a.size() + b.size();
/*  52: 76 */     return unmatched / (t1.size() + t2.size());
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static double compare(Bundle bundle1, Bundle bundle2)
/*  56:    */   {
/*  57: 90 */     Set<Thread> b1 = new HashSet(bundle1);
/*  58: 91 */     Set<Thread> b2 = new HashSet(bundle2);
/*  59:    */     
/*  60: 93 */     Set<Double> scores = new HashSet();
/*  61: 94 */     while ((!b1.isEmpty()) && (!b2.isEmpty()))
/*  62:    */     {
/*  63: 95 */       List<Thread> best = getBestMatch(b1, b2);
/*  64: 96 */       scores.add(Double.valueOf(compare((Thread)best.get(0), (Thread)best.get(1))));
/*  65: 97 */       b1.remove(best.get(0));
/*  66: 98 */       b2.remove(best.get(1));
/*  67:    */     }
/*  68:103 */     for (int i = 0; i < Math.max(b1.size(), b2.size()); i++) {
/*  69:104 */       scores.add(Double.valueOf(1.0D));
/*  70:    */     }
/*  71:107 */     double total = 0.0D;
/*  72:108 */     for (Iterator localIterator = scores.iterator(); localIterator.hasNext();)
/*  73:    */     {
/*  74:108 */       double d = ((Double)localIterator.next()).doubleValue();
/*  75:109 */       total += d;
/*  76:    */     }
/*  77:112 */     return total / scores.size();
/*  78:    */   }
/*  79:    */   
/*  80:    */   private static List<Thread> getBestMatch(Set<Thread> b1, Set<Thread> b2)
/*  81:    */   {
/*  82:117 */     Thread bestT1 = null;
/*  83:118 */     Thread bestT2 = null;
/*  84:119 */     double bestScore = 2.0D;
/*  85:    */     Iterator localIterator2;
/*  86:120 */     for (Iterator localIterator1 = b1.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/*  87:    */     {
/*  88:120 */       Thread t1 = (Thread)localIterator1.next();
/*  89:121 */       localIterator2 = b2.iterator(); continue;Thread t2 = (Thread)localIterator2.next();
/*  90:122 */       double score = compare(t1, t2);
/*  91:123 */       if (score < bestScore)
/*  92:    */       {
/*  93:124 */         bestT1 = t1;
/*  94:125 */         bestT2 = t2;
/*  95:126 */         bestScore = score;
/*  96:    */       }
/*  97:    */     }
/*  98:130 */     List<Thread> results = new ArrayList();
/*  99:131 */     results.add(bestT1);
/* 100:132 */     results.add(bestT2);
/* 101:133 */     return results;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static double compare(Entity thing1, Entity thing2)
/* 105:    */   {
/* 106:148 */     if ((thing1 == null) && (thing2 == null)) {
/* 107:149 */       return 0.0D;
/* 108:    */     }
/* 109:151 */     if ((thing1 == null) || (thing2 == null)) {
/* 110:152 */       return 1.0D;
/* 111:    */     }
/* 112:154 */     return compare(thing1.getBundle(), thing2.getBundle());
/* 113:    */   }
/* 114:    */   
/* 115:    */   public static double compare(Function d1, Function d2)
/* 116:    */   {
/* 117:168 */     if (compare(d1, d2) == 0.0D)
/* 118:    */     {
/* 119:169 */       if ((d1 == null) && (d2 == null)) {
/* 120:170 */         return 0.0D;
/* 121:    */       }
/* 122:172 */       if ((d1 == null) || (d2 == null)) {
/* 123:173 */         return 1.0D;
/* 124:    */       }
/* 125:174 */       return compare(d1.getSubject(), d2.getSubject());
/* 126:    */     }
/* 127:176 */     return 1.0D;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public static double compare(Sequence s1, Sequence s2)
/* 131:    */   {
/* 132:186 */     ArrayList<ArrayList<Integer>> matches = OperatorHelpers.getLinks(s1, s2);
/* 133:    */     
/* 134:188 */     double matchQual = 0.0D;
/* 135:189 */     int totalEls = s1.getElements().size() + s2.getElements().size();
/* 136:190 */     int unmatched = s1.getElements().size() - matches.size() + (s2.getElements().size() - matches.size());
/* 137:191 */     for (ArrayList<Integer> m : matches) {
/* 138:192 */       matchQual = compare(s1.getElement(((Integer)m.get(0)).intValue()), s2.getElement(((Integer)m.get(1)).intValue()));
/* 139:    */     }
/* 140:194 */     if (matches.size() > 0) {
/* 141:195 */       matchQual /= matches.size();
/* 142:    */     }
/* 143:196 */     System.out.println("matchQual: " + matchQual);
/* 144:197 */     System.out.println("unmatched: " + unmatched);
/* 145:198 */     System.out.println("totalEls: " + totalEls);
/* 146:199 */     return (unmatched + matchQual) / totalEls;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public static ArrayList<Entity> generalize(Entity prime, ArrayList<Entity> things)
/* 150:    */   {
/* 151:211 */     ArrayList<Entity> results = new ArrayList();
/* 152:213 */     if (prime == null) {
/* 153:214 */       return results;
/* 154:    */     }
/* 155:216 */     things = OperatorHelpers.getNearMisses(prime, things);
/* 156:217 */     for (Thread t : prime.getBundle()) {
/* 157:218 */       if ((getInstancesInMemory(t.getSupertype()) != 0) && 
/* 158:219 */         ((1 + OperatorHelpers.numberLacking(t, things)) / getInstancesInMemory(t.getSupertype()) > 0.5D))
/* 159:    */       {
/* 160:220 */         Entity newThing = new Entity(t.getSupertype());
/* 161:221 */         BasicMemory.getStaticMemory().extendVia(newThing, "thing");
/* 162:222 */         results.add(newThing);
/* 163:    */       }
/* 164:    */     }
/* 165:228 */     if ((countInstances(prime, things) * 2 > things.size()) && 
/* 166:229 */       (results.size() == 0)) {
/* 167:230 */       results.add(prime);
/* 168:    */     }
/* 169:233 */     return results;
/* 170:    */   }
/* 171:    */   
/* 172:    */   protected static int countInstances(Entity t, List<Entity> l)
/* 173:    */   {
/* 174:237 */     int counter = 0;
/* 175:238 */     for (Entity current : l) {
/* 176:239 */       if (current.isEqual(t)) {
/* 177:240 */         counter++;
/* 178:    */       }
/* 179:    */     }
/* 180:242 */     return counter;
/* 181:    */   }
/* 182:    */   
/* 183:    */   protected static int countInstances(Sequence t, List<Sequence> l)
/* 184:    */   {
/* 185:246 */     int counter = 0;
/* 186:247 */     for (Sequence current : l) {
/* 187:248 */       if (current.isEqual(t)) {
/* 188:249 */         counter++;
/* 189:    */       }
/* 190:    */     }
/* 191:251 */     return counter;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public static ArrayList<Function> generalize(Function prime, ArrayList<Function> derivs)
/* 195:    */   {
/* 196:260 */     ArrayList<Function> results = new ArrayList();
/* 197:261 */     if (prime == null) {
/* 198:262 */       return results;
/* 199:    */     }
/* 200:266 */     for (int i = 0; i < derivs.size(); i++) {
/* 201:267 */       if (compare(prime, (Entity)derivs.get(i)) == 1.0D) {
/* 202:268 */         derivs.remove(i);
/* 203:    */       }
/* 204:    */     }
/* 205:271 */     ArrayList<Entity> subjects = new ArrayList();
/* 206:272 */     for (Function d : derivs) {
/* 207:273 */       subjects.add(d.getSubject());
/* 208:    */     }
/* 209:275 */     for (Entity newThing : generalize(prime.getSubject(), subjects))
/* 210:    */     {
/* 211:276 */       Function newDeriv = (Function)prime.clone();
/* 212:277 */       newDeriv.setSubject(newThing);
/* 213:278 */       results.add(newDeriv);
/* 214:    */     }
/* 215:280 */     return results;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public static ArrayList<Sequence> generalize(Sequence prime, ArrayList<Sequence> seqs)
/* 219:    */   {
/* 220:292 */     ArrayList<Sequence> newSeqs = new ArrayList();
/* 221:    */     ArrayList<ArrayList<Entity>> newThings;
/* 222:    */     int i;
/* 223:293 */     for (Iterator localIterator1 = seqs.iterator(); localIterator1.hasNext(); i < newThings.size())
/* 224:    */     {
/* 225:293 */       Sequence s = (Sequence)localIterator1.next();
/* 226:    */       
/* 227:295 */       ArrayList<ArrayList<Integer>> links = OperatorHelpers.getLinks(prime, s);
/* 228:296 */       newThings = new ArrayList();
/* 229:297 */       for (int i = 0; i < links.size(); i++)
/* 230:    */       {
/* 231:298 */         ArrayList<Entity> wrapper = new ArrayList();
/* 232:299 */         wrapper.add(s.getElement(((Integer)((ArrayList)links.get(i)).get(1)).intValue()));
/* 233:300 */         ArrayList<Entity> emptyList = new ArrayList();
/* 234:301 */         newThings.add(i, emptyList);
/* 235:302 */         for (Entity newThing : generalize(prime.getElement(((Integer)((ArrayList)links.get(i)).get(0)).intValue()), wrapper)) {
/* 236:303 */           ((ArrayList)newThings.get(i)).add(newThing);
/* 237:    */         }
/* 238:    */       }
/* 239:307 */       i = 0; continue;
/* 240:308 */       for (int j = 0; j < ((ArrayList)newThings.get(i)).size(); j++) {
/* 241:309 */         for (int k = 0; k < OperatorHelpers.getShortestList(newThings, i); k++)
/* 242:    */         {
/* 243:310 */           Sequence newSeq = (Sequence)prime.clone();
/* 244:311 */           newSeq.clearElements();
/* 245:312 */           for (int l = 0; l < newThings.size(); l++) {
/* 246:313 */             newSeq.getElements().add(l, (Entity)((ArrayList)newThings.get(l)).get(k));
/* 247:    */           }
/* 248:315 */           newSeq.getElements().set(i, (Entity)((ArrayList)newThings.get(i)).get(j));
/* 249:317 */           if (newSeq.getElements().size() > 0)
/* 250:    */           {
/* 251:318 */             boolean isNew = true;
/* 252:319 */             for (Sequence seq : newSeqs) {
/* 253:320 */               if (newSeq.isEqual(seq)) {
/* 254:321 */                 isNew = false;
/* 255:    */               }
/* 256:    */             }
/* 257:324 */             if (isNew) {
/* 258:325 */               newSeqs.add(newSeq);
/* 259:    */             }
/* 260:    */           }
/* 261:    */         }
/* 262:    */       }
/* 263:307 */       i++;
/* 264:    */     }
/* 265:332 */     if (countInstances(prime, seqs) * 2 > newSeqs.size()) {
/* 266:333 */       newSeqs.add(prime);
/* 267:    */     }
/* 268:335 */     return newSeqs;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public static int getInstancesInMemory(String type)
/* 272:    */   {
/* 273:347 */     int count = 0;
/* 274:    */     Iterator localIterator2;
/* 275:348 */     for (Iterator localIterator1 = BasicMemory.getStaticMemory().getThings(type).iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 276:    */     {
/* 277:348 */       Object o = localIterator1.next();
/* 278:349 */       Entity thing = (Entity)o;
/* 279:350 */       localIterator2 = thing.getBundle().iterator(); continue;Thread t = (Thread)localIterator2.next();
/* 280:351 */       if (type.equals(t.getSupertype())) {
/* 281:352 */         count++;
/* 282:    */       }
/* 283:    */     }
/* 284:356 */     return count;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public static void main(String[] ignore)
/* 288:    */   {
/* 289:363 */     Thread a = new Thread();
/* 290:364 */     Thread b = new Thread();
/* 291:365 */     a.add("thing");
/* 292:366 */     b.add("thing");
/* 293:367 */     a.add("place");
/* 294:368 */     b.add("place");
/* 295:369 */     a.add("property");
/* 296:370 */     a.add("garage");
/* 297:371 */     b.add("property");
/* 298:372 */     b.add("home");
/* 299:373 */     b.add("house");
/* 300:    */     
/* 301:    */ 
/* 302:    */ 
/* 303:377 */     Entity t1 = new Entity();
/* 304:378 */     Entity t2 = new Entity();
/* 305:379 */     Entity t3 = new Entity();
/* 306:380 */     t1.addTypes("thing", "person sam");
/* 307:    */     
/* 308:382 */     t2.addTypes("thing", "person Harrison");
/* 309:383 */     t2.addTypes("thing", "person Joe");
/* 310:384 */     BasicMemory.getStaticMemory().store(t1);
/* 311:385 */     BasicMemory.getStaticMemory().store(t2);
/* 312:386 */     BasicMemory.getStaticMemory().store(t3);
/* 313:    */     
/* 314:    */ 
/* 315:    */ 
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
/* 326:    */ 
/* 327:    */ 
/* 328:    */ 
/* 329:    */ 
/* 330:    */ 
/* 331:    */ 
/* 332:    */ 
/* 333:    */ 
/* 334:    */ 
/* 335:409 */     ArrayList<Entity> frames = new ArrayList();
/* 336:410 */     frames.add(t1);
/* 337:411 */     frames.add(t2);
/* 338:    */     
/* 339:    */ 
/* 340:    */ 
/* 341:415 */     Entity book = new Entity("book");
/* 342:416 */     Entity shelf = new Entity("shelf");
/* 343:417 */     Entity table = new Entity("table");
/* 344:    */     
/* 345:419 */     Vector<Entity> fArgs = new Vector();
/* 346:420 */     fArgs.addElement(table);
/* 347:421 */     fArgs.addElement(table);
/* 348:422 */     fArgs.addElement(table);
/* 349:423 */     fArgs.addElement(book);
/* 350:    */     
/* 351:425 */     Vector<Entity> gArgs = new Vector();
/* 352:426 */     gArgs.addElement(shelf);
/* 353:427 */     gArgs.addElement(table);
/* 354:428 */     gArgs.addElement(shelf);
/* 355:429 */     gArgs.addElement(book);
/* 356:    */     
/* 357:431 */     Vector<Entity> hArgs = new Vector();
/* 358:432 */     hArgs.addElement(shelf);
/* 359:433 */     hArgs.addElement(table);
/* 360:434 */     hArgs.addElement(table);
/* 361:435 */     hArgs.addElement(book);
/* 362:    */   }
/* 363:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.operators.Operators
 * JD-Core Version:    0.7.0.1
 */