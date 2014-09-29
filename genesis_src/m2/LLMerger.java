/*   1:    */ package m2;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Thread;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.HashSet;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import java.util.Set;
/*  13:    */ import m2.datatypes.Chain;
/*  14:    */ import m2.datatypes.DoubleBundle;
/*  15:    */ 
/*  16:    */ public class LLMerger
/*  17:    */ {
/*  18: 29 */   private Map<String, Set<Chain>> reps = new HashMap();
/*  19: 30 */   private Set<Chain> chains = new HashSet();
/*  20:    */   
/*  21:    */   public synchronized Set<Chain> getChains()
/*  22:    */   {
/*  23: 40 */     return new HashSet(this.chains);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public synchronized Map<String, Set<Chain>> getRepChains()
/*  27:    */   {
/*  28: 45 */     return new HashMap(this.reps);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void add(Entity t)
/*  32:    */   {
/*  33: 53 */     Chain c = new Chain(t);
/*  34:    */     
/*  35:    */ 
/*  36:    */ 
/*  37:    */ 
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46: 66 */     addNew(c);
/*  47: 67 */     doMerges();
/*  48:    */   }
/*  49:    */   
/*  50:    */   private static enum Feature
/*  51:    */   {
/*  52: 72 */     can,  cannot,  did,  didnot;
/*  53:    */   }
/*  54:    */   
/*  55:    */   private synchronized void addNew(Chain newT)
/*  56:    */   {
/*  57:102 */     conflictResolver(newT);
/*  58:    */     
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:107 */     Map<Chain, Integer> candidates = getMisses(newT, 1);
/*  63:109 */     if (!candidates.isEmpty())
/*  64:    */     {
/*  65:110 */       if (M2.DEBUG) {
/*  66:110 */         System.out.println("[M2] LLMerger updating existing Chain.");
/*  67:    */       }
/*  68:112 */       boolean isRecorded = false;
/*  69:113 */       for (Chain cand : candidates.keySet())
/*  70:    */       {
/*  71:114 */         removeChain(cand);
/*  72:    */         
/*  73:116 */         M2.m2assert(((Integer)candidates.get(cand)).intValue() < 2, "addNew -- invalid candidate found");
/*  74:117 */         M2.m2assert(!conflicts(cand, newT), "addNew -- cand conflicting with new chain--is getMisses broken?");
/*  75:120 */         if (M2.DEBUG) {
/*  76:120 */           System.out.println("[M2] LLMerger trying mergeInto.");
/*  77:    */         }
/*  78:121 */         Chain bkp = cand.clone();
/*  79:    */         
/*  80:123 */         mergeInto(cand, newT);
/*  81:124 */         if (getConflicts(cand).isEmpty())
/*  82:    */         {
/*  83:127 */           addChain(cand);
/*  84:128 */           isRecorded = true;
/*  85:    */         }
/*  86:    */         else
/*  87:    */         {
/*  88:133 */           addChain(bkp);
/*  89:    */         }
/*  90:    */       }
/*  91:136 */       if (!isRecorded) {
/*  92:138 */         addChain(newT);
/*  93:    */       }
/*  94:    */     }
/*  95:    */     else
/*  96:    */     {
/*  97:142 */       if (M2.DEBUG) {
/*  98:142 */         System.out.println("[M2] LLMerger creating new Chain.");
/*  99:    */       }
/* 100:145 */       addChain(newT);
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   private void mergeInto(Chain base, Chain n)
/* 105:    */   {
/* 106:155 */     for (int i = 0; i < n.size(); i++)
/* 107:    */     {
/* 108:156 */       Thread pos = ((DoubleBundle)n.get(i)).getPosSingle();
/* 109:157 */       Thread neg = ((DoubleBundle)n.get(i)).getNegSingle();
/* 110:158 */       if (pos != null) {
/* 111:159 */         ((DoubleBundle)base.get(i)).addPos(pos);
/* 112:161 */       } else if (neg != null) {
/* 113:162 */         ((DoubleBundle)base.get(i)).addNeg(neg);
/* 114:    */       }
/* 115:    */     }
/* 116:165 */     base.updateHistory((Entity)n.getInputList().get(0));
/* 117:    */   }
/* 118:    */   
/* 119:    */   private boolean conflictResolver(Chain in)
/* 120:    */   {
/* 121:172 */     List<Chain> conflicts = getConflicts(in);
/* 122:173 */     boolean results = false;
/* 123:174 */     if (!conflicts.isEmpty()) {
/* 124:175 */       results = true;
/* 125:    */     }
/* 126:    */     Iterator localIterator2;
/* 127:177 */     for (Iterator localIterator1 = conflicts.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 128:    */     {
/* 129:177 */       Chain con = (Chain)localIterator1.next();
/* 130:178 */       removeChain(con);
/* 131:179 */       if (M2.DEBUG) {
/* 132:179 */         System.out.println("[M2] LLMerger resolving conflict.");
/* 133:    */       }
/* 134:180 */       List<Chain> newChains = reconstitute(con, in);
/* 135:181 */       localIterator2 = newChains.iterator(); continue;Chain n = (Chain)localIterator2.next();
/* 136:182 */       addChain(n);
/* 137:    */     }
/* 138:185 */     return results;
/* 139:    */   }
/* 140:    */   
/* 141:    */   private boolean conflicts(Chain old1, Chain n1)
/* 142:    */   {
/* 143:191 */     Chain longer = old1;
/* 144:192 */     Chain shorter = n1;
/* 145:193 */     if (old1.size() < n1.size())
/* 146:    */     {
/* 147:194 */       longer = n1;
/* 148:195 */       shorter = old1;
/* 149:    */     }
/* 150:197 */     boolean hardMiss = false;
/* 151:198 */     for (int i = 0; i < shorter.size(); i++)
/* 152:    */     {
/* 153:199 */       int dist = ((DoubleBundle)shorter.get(i)).getDistance((DoubleBundle)longer.get(i));
/* 154:200 */       if (dist == 2)
/* 155:    */       {
/* 156:201 */         if (hardMiss) {
/* 157:202 */           return false;
/* 158:    */         }
/* 159:205 */         hardMiss = true;
/* 160:    */       }
/* 161:208 */       else if (dist == 1)
/* 162:    */       {
/* 163:209 */         return false;
/* 164:    */       }
/* 165:    */     }
/* 166:212 */     if (hardMiss) {
/* 167:214 */       return true;
/* 168:    */     }
/* 169:216 */     return false;
/* 170:    */   }
/* 171:    */   
/* 172:    */   private List<Chain> getConflicts(Chain c)
/* 173:    */   {
/* 174:221 */     List<Chain> results = new ArrayList();
/* 175:223 */     for (Chain cand : this.chains) {
/* 176:224 */       if (conflicts(cand, c)) {
/* 177:225 */         results.add(cand);
/* 178:    */       }
/* 179:    */     }
/* 180:228 */     return results;
/* 181:    */   }
/* 182:    */   
/* 183:    */   private List<Chain> reconstitute(Chain base, Chain neg)
/* 184:    */   {
/* 185:236 */     List<Chain> results = new ArrayList();
/* 186:237 */     List<Entity> inputThings = base.getInputList();
/* 187:238 */     inputThings.addAll(neg.getInputList());
/* 188:    */     
/* 189:240 */     List<Chain> atoms = new ArrayList();
/* 190:242 */     for (Entity in : inputThings)
/* 191:    */     {
/* 192:243 */       Chain c = new Chain(in);
/* 193:244 */       atoms.add(c);
/* 194:    */     }
/* 195:247 */     results.add(((Chain)atoms.get(0)).clone());
/* 196:    */     
/* 197:249 */     int r = 0;
/* 198:250 */     while (r < results.size())
/* 199:    */     {
/* 200:253 */       Chain c1 = (Chain)results.get(r);
/* 201:254 */       for (Chain c2 : atoms)
/* 202:    */       {
/* 203:258 */         if (c1.size() < c2.size()) {
/* 204:    */           break;
/* 205:    */         }
/* 206:261 */         Chain c1bkp = c1.clone();
/* 207:262 */         mergeInto(c1, c2);
/* 208:265 */         for (Chain tester : atoms) {
/* 209:267 */           if (conflicts(c1, tester))
/* 210:    */           {
/* 211:269 */             results.set(r, c1bkp);
/* 212:270 */             boolean newConcept = true;
/* 213:271 */             for (Chain f : results) {
/* 214:273 */               if (c2.overlaps(f))
/* 215:    */               {
/* 216:274 */                 newConcept = false;
/* 217:275 */                 break;
/* 218:    */               }
/* 219:    */             }
/* 220:278 */             if (newConcept) {
/* 221:279 */               results.add(c2.clone());
/* 222:    */             }
/* 223:282 */             c1 = c1bkp;
/* 224:283 */             break;
/* 225:    */           }
/* 226:    */         }
/* 227:    */       }
/* 228:287 */       r++;
/* 229:    */     }
/* 230:292 */     return results;
/* 231:    */   }
/* 232:    */   
/* 233:    */   private synchronized void removeChain(Chain c)
/* 234:    */   {
/* 235:590 */     M2.m2assert(this.chains.remove(c), "LLMerger: removeChain() failed to remove bad chain from chains");
/* 236:591 */     String repString = c.getRepType();
/* 237:592 */     M2.m2assert(((Set)this.reps.get(repString)).remove(c), "LLMerger: removeChain() failed to remove bad chain from reps");
/* 238:    */   }
/* 239:    */   
/* 240:    */   private synchronized void addChain(Chain c)
/* 241:    */   {
/* 242:605 */     if (!getConflicts(c).isEmpty())
/* 243:    */     {
/* 244:606 */       System.out.println("**[M2] BIG ERROR! trying to add a conflicting chain: " + c);
/* 245:607 */       return;
/* 246:    */     }
/* 247:631 */     this.chains.add(c);
/* 248:632 */     String repString = c.getRepType();
/* 249:633 */     if (this.reps.containsKey(repString))
/* 250:    */     {
/* 251:634 */       ((Set)this.reps.get(repString)).add(c);
/* 252:    */     }
/* 253:    */     else
/* 254:    */     {
/* 255:637 */       Set<Chain> repSet = new HashSet();
/* 256:638 */       repSet.add(c);
/* 257:639 */       this.reps.put(repString, repSet);
/* 258:    */     }
/* 259:    */   }
/* 260:    */   
/* 261:    */   private synchronized Map<Chain, Integer> getMisses(Chain chain, int numMisses)
/* 262:    */   {
/* 263:659 */     Map<Chain, Integer> candidates = new HashMap();
/* 264:660 */     String repString = chain.getRepType();
/* 265:661 */     Set<Chain> repMatches = (Set)this.reps.get(repString);
/* 266:662 */     if (repMatches == null) {
/* 267:662 */       repMatches = new HashSet();
/* 268:    */     }
/* 269:663 */     for (Chain c : repMatches) {
/* 270:664 */       candidates.put(c, Integer.valueOf(0));
/* 271:    */     }
/* 272:666 */     return getMissesHelper(chain, candidates, numMisses);
/* 273:    */   }
/* 274:    */   
/* 275:    */   private synchronized Map<Chain, Integer> getMissesHelper(Chain chain, Map<Chain, Integer> cands, int numMisses)
/* 276:    */   {
/* 277:670 */     Map<Chain, Integer> candidates = new HashMap(cands);
/* 278:671 */     for (int i = 0; i < chain.size(); i++)
/* 279:    */     {
/* 280:673 */       if (candidates.isEmpty()) {
/* 281:    */         break;
/* 282:    */       }
/* 283:677 */       DoubleBundle db = (DoubleBundle)chain.get(i);
/* 284:    */       
/* 285:    */ 
/* 286:680 */       Set<Chain> tempCand = new HashSet(candidates.keySet());
/* 287:681 */       for (Chain c : tempCand) {
/* 288:682 */         if (c.size() <= i)
/* 289:    */         {
/* 290:683 */           candidates.remove(c);
/* 291:    */         }
/* 292:    */         else
/* 293:    */         {
/* 294:686 */           int match = matches(db, (DoubleBundle)c.get(i));
/* 295:687 */           if (match == 0) {
/* 296:688 */             candidates.remove(c);
/* 297:    */           }
/* 298:690 */           if (match == 1)
/* 299:    */           {
/* 300:691 */             int misses = ((Integer)candidates.get(c)).intValue();
/* 301:692 */             misses++;
/* 302:693 */             if (misses > numMisses) {
/* 303:694 */               candidates.remove(c);
/* 304:    */             } else {
/* 305:697 */               candidates.put(c, Integer.valueOf(misses));
/* 306:    */             }
/* 307:    */           }
/* 308:    */         }
/* 309:    */       }
/* 310:    */     }
/* 311:702 */     return candidates;
/* 312:    */   }
/* 313:    */   
/* 314:    */   private int matches(DoubleBundle singleton, DoubleBundle db)
/* 315:    */   {
/* 316:729 */     Thread pos = singleton.getPosSingle();
/* 317:730 */     Thread neg = singleton.getNegSingle();
/* 318:731 */     if (pos != null) {
/* 319:732 */       return db.matches(pos);
/* 320:    */     }
/* 321:734 */     if (neg != null) {
/* 322:736 */       return 2 - db.matches(neg);
/* 323:    */     }
/* 324:738 */     return 0;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public int getMissDistance(Entity t)
/* 328:    */   {
/* 329:753 */     Chain c = new Chain(t);
/* 330:754 */     for (int i = 0; i < c.size(); i++)
/* 331:    */     {
/* 332:755 */       Map<Chain, Integer> misses = getMisses(c, i);
/* 333:756 */       if (!misses.isEmpty()) {
/* 334:757 */         return i;
/* 335:    */       }
/* 336:    */     }
/* 337:760 */     return c.size();
/* 338:    */   }
/* 339:    */   
/* 340:    */   public boolean isPossible(Entity t)
/* 341:    */   {
/* 342:770 */     Chain c = new Chain(t);
/* 343:771 */     Map<Chain, Integer> matches = getMisses(c, 0);
/* 344:772 */     if (!matches.isEmpty()) {
/* 345:773 */       return true;
/* 346:    */     }
/* 347:775 */     return false;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public List<Entity> getNearNeighbors(Entity t)
/* 351:    */   {
/* 352:807 */     Map<Chain, Integer> misses = getMisses(new Chain(t), 0);
/* 353:808 */     List<Entity> results = new ArrayList();
/* 354:809 */     for (Chain c : misses.keySet()) {
/* 355:810 */       results.addAll(c.getInputList());
/* 356:    */     }
/* 357:812 */     return results;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public List<Entity> getNeighbors(Entity t)
/* 361:    */   {
/* 362:816 */     Map<Chain, Integer> misses = getMisses(new Chain(t), 2);
/* 363:817 */     List<Entity> results = new ArrayList();
/* 364:818 */     for (Chain c : misses.keySet()) {
/* 365:819 */       results.addAll(c.getInputList());
/* 366:    */     }
/* 367:821 */     return results;
/* 368:    */   }
/* 369:    */   
/* 370:    */   private synchronized void doMerges()
/* 371:    */   {
/* 372:910 */     while (!mergeHelper()) {}
/* 373:    */   }
/* 374:    */   
/* 375:    */   private boolean mergeHelper()
/* 376:    */   {
/* 377:916 */     List<Chain> copy = new ArrayList(this.chains);
/* 378:    */     Iterator localIterator2;
/* 379:917 */     for (Iterator localIterator1 = copy.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 380:    */     {
/* 381:917 */       Chain c1 = (Chain)localIterator1.next();
/* 382:918 */       localIterator2 = copy.iterator(); continue;Chain c2 = (Chain)localIterator2.next();
/* 383:919 */       if ((!c1.equals(c2)) && (c1.overlaps(c2)))
/* 384:    */       {
/* 385:921 */         removeChain(c1);
/* 386:922 */         removeChain(c2);
/* 387:    */         
/* 388:924 */         Chain test = c1.mergeChain(c2);
/* 389:925 */         if (getConflicts(test).isEmpty())
/* 390:    */         {
/* 391:928 */           addChain(test);
/* 392:    */           
/* 393:930 */           return false;
/* 394:    */         }
/* 395:935 */         addChain(c1);
/* 396:936 */         addChain(c2);
/* 397:    */       }
/* 398:    */     }
/* 399:941 */     return true;
/* 400:    */   }
/* 401:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     m2.LLMerger
 * JD-Core Version:    0.7.0.1
 */