/*   1:    */ package translator;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Function;
/*   6:    */ import bridge.reps.entities.Relation;
/*   7:    */ import bridge.reps.entities.Sequence;
/*   8:    */ import bridge.reps.entities.Thread;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.ListIterator;
/*  11:    */ import java.util.Vector;
/*  12:    */ 
/*  13:    */ public class BasicRule
/*  14:    */   implements Runnable
/*  15:    */ {
/*  16:    */   protected Sequence links;
/*  17: 18 */   boolean succeeded = false;
/*  18:    */   protected Entity firstLink;
/*  19:    */   protected Entity firstLinkSubject;
/*  20:    */   protected Entity firstLinkObject;
/*  21:    */   protected Entity secondLink;
/*  22:    */   protected Entity secondLinkSubject;
/*  23:    */   protected Entity secondLinkObject;
/*  24:    */   protected Entity thirdLink;
/*  25:    */   protected Entity thirdLinkSubject;
/*  26:    */   protected Entity thirdLinkObject;
/*  27: 38 */   protected Entity dummy = new Entity();
/*  28: 40 */   protected String sample = "";
/*  29:    */   
/*  30:    */   public String getSample()
/*  31:    */   {
/*  32: 43 */     return this.sample;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Relation getFirstLink()
/*  36:    */   {
/*  37: 47 */     return (Relation)this.firstLink;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Relation getSecondLink()
/*  41:    */   {
/*  42: 51 */     return (Relation)this.secondLink;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Relation getThirdLink()
/*  46:    */   {
/*  47: 55 */     return (Relation)this.thirdLink;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setLinks(Entity firstLink)
/*  51:    */   {
/*  52: 59 */     this.firstLink = firstLink;
/*  53: 60 */     if ((firstLink != null) && (firstLink.relationP()))
/*  54:    */     {
/*  55: 61 */       this.firstLinkSubject = firstLink.getSubject();
/*  56: 62 */       this.firstLinkObject = firstLink.getObject();
/*  57:    */     }
/*  58: 64 */     else if ((firstLink != null) && (firstLink.functionP()))
/*  59:    */     {
/*  60: 65 */       this.firstLinkSubject = firstLink.getSubject();
/*  61: 66 */       this.firstLinkObject = this.dummy;
/*  62:    */     }
/*  63:    */     else
/*  64:    */     {
/*  65: 69 */       this.firstLinkSubject = this.dummy;
/*  66: 70 */       this.firstLinkObject = this.dummy;
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setLinks(Entity firstLink, Entity secondLink)
/*  71:    */   {
/*  72: 75 */     setLinks(firstLink);
/*  73: 76 */     this.secondLink = secondLink;
/*  74: 77 */     if ((secondLink != null) && (secondLink.relationP()))
/*  75:    */     {
/*  76: 78 */       this.secondLinkSubject = secondLink.getSubject();
/*  77: 79 */       this.secondLinkObject = secondLink.getObject();
/*  78:    */     }
/*  79: 81 */     else if ((secondLink != null) && (secondLink.functionP()))
/*  80:    */     {
/*  81: 82 */       this.secondLinkSubject = secondLink.getSubject();
/*  82: 83 */       this.secondLinkObject = this.dummy;
/*  83:    */     }
/*  84:    */     else
/*  85:    */     {
/*  86: 86 */       this.secondLinkSubject = this.dummy;
/*  87: 87 */       this.secondLinkObject = this.dummy;
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setLinks(Entity firstLink, Entity secondLink, Entity thirdLink)
/*  92:    */   {
/*  93: 92 */     setLinks(firstLink, secondLink);
/*  94: 93 */     this.thirdLink = thirdLink;
/*  95: 94 */     if ((thirdLink != null) && (thirdLink.relationP()))
/*  96:    */     {
/*  97: 95 */       this.thirdLinkSubject = thirdLink.getSubject();
/*  98: 96 */       this.thirdLinkObject = thirdLink.getObject();
/*  99:    */     }
/* 100: 98 */     else if ((thirdLink != null) && (thirdLink.functionP()))
/* 101:    */     {
/* 102: 99 */       this.thirdLinkSubject = thirdLink.getSubject();
/* 103:100 */       this.thirdLinkObject = this.dummy;
/* 104:    */     }
/* 105:    */     else
/* 106:    */     {
/* 107:103 */       this.thirdLinkSubject = this.dummy;
/* 108:104 */       this.thirdLinkObject = this.dummy;
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setLinks(Sequence links)
/* 113:    */   {
/* 114:109 */     this.links = links;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Vector<Entity> getLinkElements()
/* 118:    */   {
/* 119:113 */     return this.links.getElements();
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void addLink(Entity link)
/* 123:    */   {
/* 124:117 */     getLinkElements().add(0, link);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean addLinkAfter(Entity link, Entity after)
/* 128:    */   {
/* 129:121 */     int index = getLinkElements().indexOf(after);
/* 130:122 */     if (index < 0) {
/* 131:123 */       return false;
/* 132:    */     }
/* 133:125 */     getLinkElements().add(index + 1, link);
/* 134:126 */     return true;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public boolean addLinkBefore(Entity link, Entity before)
/* 138:    */   {
/* 139:130 */     int index = getLinkElements().indexOf(before);
/* 140:131 */     if (index < 0) {
/* 141:132 */       return false;
/* 142:    */     }
/* 143:134 */     getLinkElements().add(index, link);
/* 144:135 */     return true;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void addLinkAtEnd(Entity link)
/* 148:    */   {
/* 149:139 */     getLinkElements().add(link);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void remove(Entity link)
/* 153:    */   {
/* 154:143 */     getLinkElements().remove(link);
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void replace(Entity target, Entity replacement)
/* 158:    */   {
/* 159:147 */     replace(replacement, target, this.links);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public static void replace(Entity in, Entity out, Entity structure)
/* 163:    */   {
/* 164:157 */     if (structure.entityP()) {
/* 165:158 */       return;
/* 166:    */     }
/* 167:160 */     if (structure.relationP())
/* 168:    */     {
/* 169:161 */       Relation r = (Relation)structure;
/* 170:162 */       if (r.getSubject() == out) {
/* 171:163 */         r.setSubject(in);
/* 172:    */       } else {
/* 173:166 */         replace(in, out, structure.getSubject());
/* 174:    */       }
/* 175:168 */       if (r.getObject() == out) {
/* 176:169 */         r.setObject(in);
/* 177:    */       } else {
/* 178:172 */         replace(in, out, structure.getObject());
/* 179:    */       }
/* 180:    */     }
/* 181:175 */     else if (structure.functionP())
/* 182:    */     {
/* 183:176 */       Function d = (Function)structure;
/* 184:177 */       if (d.getSubject() == out) {
/* 185:178 */         d.setSubject(in);
/* 186:    */       } else {
/* 187:181 */         replace(in, out, structure.getSubject());
/* 188:    */       }
/* 189:    */     }
/* 190:184 */     else if (structure.sequenceP())
/* 191:    */     {
/* 192:185 */       Sequence s = (Sequence)structure;
/* 193:186 */       Vector<Entity> elements = s.getElements();
/* 194:187 */       Vector<Entity> clone = (Vector)elements.clone();
/* 195:188 */       for (int i = 0; i < clone.size(); i++) {
/* 196:189 */         if (out == clone.get(i))
/* 197:    */         {
/* 198:190 */           elements.remove(i);
/* 199:191 */           elements.add(i, in);
/* 200:    */         }
/* 201:    */         else
/* 202:    */         {
/* 203:194 */           replace(in, out, (Entity)elements.get(i));
/* 204:    */         }
/* 205:    */       }
/* 206:    */     }
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void copyAndReplace(Entity target, Entity replacement)
/* 210:    */   {
/* 211:255 */     ListIterator<Entity> iter = this.links.getElements().listIterator();
/* 212:256 */     while (iter.hasNext())
/* 213:    */     {
/* 214:257 */       Entity e = (Entity)iter.next();
/* 215:258 */       if (!e.entityP()) {
/* 216:262 */         if (contains(e, target))
/* 217:    */         {
/* 218:263 */           Entity copy = copyAndReplace(replacement, target, e);
/* 219:264 */           if (copy != null) {
/* 220:265 */             iter.add(copy);
/* 221:    */           }
/* 222:    */         }
/* 223:    */       }
/* 224:    */     }
/* 225:    */   }
/* 226:    */   
/* 227:    */   public Entity copyAndReplace(Entity in, Entity out, Entity structure)
/* 228:    */   {
/* 229:272 */     if (structure == out) {
/* 230:273 */       return in;
/* 231:    */     }
/* 232:275 */     if (structure.relationP())
/* 233:    */     {
/* 234:276 */       Entity s = copyAndReplace(in, out, structure.getSubject());
/* 235:277 */       Entity o = copyAndReplace(in, out, structure.getObject());
/* 236:278 */       if ((s != null) || (o != null))
/* 237:    */       {
/* 238:279 */         Relation ret = (Relation)structure.clone();
/* 239:281 */         if (s != null) {
/* 240:282 */           ret.setSubject(s);
/* 241:    */         }
/* 242:284 */         if (o != null) {
/* 243:285 */           ret.setObject(o);
/* 244:    */         }
/* 245:288 */         return ret;
/* 246:    */       }
/* 247:    */     }
/* 248:291 */     else if (structure.functionP())
/* 249:    */     {
/* 250:292 */       Entity s = copyAndReplace(in, out, structure.getSubject());
/* 251:293 */       if (s != null)
/* 252:    */       {
/* 253:294 */         Function ret = (Function)structure.clone();
/* 254:295 */         ret.setSubject(s);
/* 255:296 */         return ret;
/* 256:    */       }
/* 257:    */     }
/* 258:299 */     else if (structure.sequenceP())
/* 259:    */     {
/* 260:300 */       Sequence s = (Sequence)structure;
/* 261:301 */       Vector<Entity> newElements = new Vector(s.getElements());
/* 262:302 */       boolean copy = false;
/* 263:303 */       for (int i = 0; i < s.getElements().size(); i++)
/* 264:    */       {
/* 265:304 */         Entity result = copyAndReplace(in, out, s.getElement(i));
/* 266:305 */         if (result != null)
/* 267:    */         {
/* 268:306 */           copy = true;
/* 269:307 */           newElements.set(i, result);
/* 270:    */         }
/* 271:    */       }
/* 272:310 */       if (copy)
/* 273:    */       {
/* 274:311 */         Sequence ret = (Sequence)s.clone();
/* 275:312 */         ret.setElements(newElements);
/* 276:313 */         return ret;
/* 277:    */       }
/* 278:    */     }
/* 279:316 */     return null;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public boolean containsLink(Entity thing)
/* 283:    */   {
/* 284:320 */     return this.links.getElements().contains(thing);
/* 285:    */   }
/* 286:    */   
/* 287:    */   public static boolean contains(Entity structure, Entity target)
/* 288:    */   {
/* 289:324 */     if (structure.entityP()) {
/* 290:325 */       return structure == target;
/* 291:    */     }
/* 292:327 */     if (structure.relationP()) {
/* 293:328 */       return (structure == target) || (contains(structure.getSubject(), target)) || (contains(structure.getObject(), target));
/* 294:    */     }
/* 295:330 */     if (structure.functionP()) {
/* 296:331 */       return (structure == target) || (contains(structure.getSubject(), target));
/* 297:    */     }
/* 298:333 */     if (structure.sequenceP())
/* 299:    */     {
/* 300:334 */       Sequence s = (Sequence)structure;
/* 301:335 */       if (structure == target) {
/* 302:336 */         return true;
/* 303:    */       }
/* 304:338 */       for (Entity e : s.getElements()) {
/* 305:339 */         if (contains(e, target)) {
/* 306:340 */           return true;
/* 307:    */         }
/* 308:    */       }
/* 309:343 */       return false;
/* 310:    */     }
/* 311:345 */     return false;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public static void transferTypes(Entity source, Entity target)
/* 315:    */   {
/* 316:349 */     target.getPrimedThread().clear();
/* 317:350 */     target.getPrimedThread().addAll(source.getPrimedThread());
/* 318:351 */     transferFeatures(source, target);
/* 319:    */   }
/* 320:    */   
/* 321:    */   public static void transferFeatures(Entity source, Entity target)
/* 322:    */   {
/* 323:355 */     if (source.getBundle().getThread("feature") == null) {
/* 324:356 */       return;
/* 325:    */     }
/* 326:358 */     for (String type : source.getThread("feature")) {
/* 327:359 */       target.addType(type, "feature");
/* 328:    */     }
/* 329:    */   }
/* 330:    */   
/* 331:    */   public static void addTypeAfterReference(String reference, String addition, Entity t)
/* 332:    */   {
/* 333:364 */     Thread v = t.getPrimedThread().copyThread();
/* 334:365 */     v.remove(addition);
/* 335:366 */     int index = v.indexOf(reference);
/* 336:367 */     if (index >= 0) {
/* 337:368 */       v.add(index + 1, addition);
/* 338:    */     }
/* 339:370 */     t.setPrimedThread(v);
/* 340:    */   }
/* 341:    */   
/* 342:    */   public static void addTypeBeforeLast(String addition, Entity t)
/* 343:    */   {
/* 344:374 */     Thread v = t.getPrimedThread().copyThread();
/* 345:375 */     v.remove(addition);
/* 346:376 */     int index = v.size();
/* 347:377 */     if (index >= 0) {
/* 348:378 */       v.add(index - 1, addition);
/* 349:    */     }
/* 350:380 */     t.setPrimedThread(v);
/* 351:    */   }
/* 352:    */   
/* 353:    */   public static void addTypeAfterLast(String addition, Entity t)
/* 354:    */   {
/* 355:384 */     Thread v = t.getPrimedThread().copyThread();
/* 356:385 */     v.remove(addition);
/* 357:386 */     v.add(addition);
/* 358:387 */     t.setPrimedThread(v);
/* 359:    */   }
/* 360:    */   
/* 361:    */   public static void addName(Entity t)
/* 362:    */   {
/* 363:393 */     Thread v = t.getPrimedThread().copyThread();
/* 364:    */     
/* 365:395 */     String theName = (String)v.lastElement();
/* 366:396 */     int index = v.size() - 1;
/* 367:398 */     if ((index >= 1) && 
/* 368:399 */       (!v.contains("name"))) {
/* 369:400 */       v.add(index, "name");
/* 370:    */     }
/* 371:410 */     t.addProperty("name", v.getType());
/* 372:411 */     t.setPrimedThread(v);
/* 373:    */   }
/* 374:    */   
/* 375:    */   public static void addProperName(Entity t)
/* 376:    */   {
/* 377:428 */     t.addProperty("proper", t.getType());
/* 378:    */   }
/* 379:    */   
/* 380:    */   public static void removeName(Entity t)
/* 381:    */   {
/* 382:433 */     Thread v = t.getPrimedThread().copyThread();
/* 383:434 */     v.remove("name");
/* 384:435 */     t.setPrimedThread(v);
/* 385:    */   }
/* 386:    */   
/* 387:    */   public void succeeded()
/* 388:    */   {
/* 389:457 */     this.succeeded = true;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void failed()
/* 393:    */   {
/* 394:461 */     this.succeeded = false;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public boolean hasSucceeded()
/* 398:    */   {
/* 399:465 */     return this.succeeded;
/* 400:    */   }
/* 401:    */   
/* 402:    */   protected boolean firstInsideSecond(Entity first, Entity second)
/* 403:    */   {
/* 404:469 */     if (first == second) {
/* 405:470 */       return true;
/* 406:    */     }
/* 407:472 */     if (second.relationP()) {
/* 408:473 */       return (firstInsideSecond(first, second.getSubject())) || (firstInsideSecond(first, second.getObject()));
/* 409:    */     }
/* 410:475 */     if (second.functionP()) {
/* 411:476 */       return firstInsideSecond(first, second.getSubject());
/* 412:    */     }
/* 413:478 */     if (second.sequenceP()) {
/* 414:479 */       for (Iterator i = second.getElements().iterator(); i.hasNext();)
/* 415:    */       {
/* 416:480 */         Entity t = (Entity)i.next();
/* 417:481 */         if (firstInsideSecond(first, t)) {
/* 418:482 */           return true;
/* 419:    */         }
/* 420:    */       }
/* 421:    */     }
/* 422:486 */     return false;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public void run()
/* 426:    */   {
/* 427:490 */     failed();
/* 428:    */   }
/* 429:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     translator.BasicRule
 * JD-Core Version:    0.7.0.1
 */