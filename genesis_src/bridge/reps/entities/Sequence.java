/*   1:    */ package bridge.reps.entities;
/*   2:    */ 
/*   3:    */ import bridge.utils.logging.Logger;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.IdentityHashMap;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Set;
/*  11:    */ import java.util.Vector;
/*  12:    */ 
/*  13:    */ public class Sequence
/*  14:    */   extends Entity
/*  15:    */ {
/*  16: 26 */   public static String COMMUTATIVE = "commutativesequence";
/*  17: 28 */   protected Vector<Entity> elements = new Vector();
/*  18:    */   public static final String LOGGER_GROUP = "things";
/*  19:    */   public static final String LOGGER_INSTANCE = "Sequence";
/*  20:    */   public static final String LOGGER = "things.Sequence";
/*  21:    */   
/*  22:    */   public boolean entityP()
/*  23:    */   {
/*  24: 33 */     return false;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public boolean entityP(String type)
/*  28:    */   {
/*  29: 37 */     return false;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean functionP()
/*  33:    */   {
/*  34: 41 */     return false;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean functionP(String type)
/*  38:    */   {
/*  39: 45 */     return false;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean relationP()
/*  43:    */   {
/*  44: 49 */     return false;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean relationP(String type)
/*  48:    */   {
/*  49: 53 */     return false;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean sequenceP()
/*  53:    */   {
/*  54: 63 */     return true;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean sequenceP(String type)
/*  58:    */   {
/*  59: 67 */     return isAPrimed(type);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void addElement(Entity element)
/*  63:    */   {
/*  64: 71 */     saveState();
/*  65: 72 */     this.elements.add(element);
/*  66: 73 */     element.addElementOf(this);
/*  67: 74 */     fireNotification();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean removeElement(Entity element)
/*  71:    */   {
/*  72: 78 */     saveState();
/*  73: 79 */     boolean b = this.elements.remove(element);
/*  74: 80 */     if (b)
/*  75:    */     {
/*  76: 81 */       element.removeElementOf(this);
/*  77: 82 */       fireNotification();
/*  78:    */     }
/*  79: 84 */     return b;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Vector<Entity> getElements()
/*  83:    */   {
/*  84: 88 */     return this.elements;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Set<Entity> getChildren()
/*  88:    */   {
/*  89: 92 */     Set<Entity> result = new HashSet();
/*  90: 93 */     result.addAll(getElements());
/*  91: 94 */     return result;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public int getNumberOfChildren()
/*  95:    */   {
/*  96: 98 */     return getElements().size();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public Entity getElement(int i)
/* 100:    */   {
/* 101:102 */     return (Entity)this.elements.get(i);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean isEqual(Object o)
/* 105:    */   {
/* 106:110 */     if ((o instanceof Sequence))
/* 107:    */     {
/* 108:111 */       Sequence s = (Sequence)o;
/* 109:112 */       if (s.getElements().size() == getElements().size())
/* 110:    */       {
/* 111:115 */         Vector<Entity> e1 = s.getElements();
/* 112:116 */         Vector<Entity> e2 = getElements();
/* 113:117 */         for (int i = 0; i < e1.size(); i++)
/* 114:    */         {
/* 115:118 */           Entity t1 = (Entity)e1.get(i);
/* 116:119 */           Entity t2 = (Entity)e2.get(i);
/* 117:120 */           if (!t1.isEqual(t2)) {
/* 118:121 */             return false;
/* 119:    */           }
/* 120:    */         }
/* 121:124 */         return super.isEqual(s);
/* 122:    */       }
/* 123:    */     }
/* 124:127 */     return false;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Sequence() {}
/* 128:    */   
/* 129:    */   public Sequence(Thread thread, Collection<Entity> elements)
/* 130:    */   {
/* 131:138 */     this();
/* 132:139 */     this.elements.addAll(elements);
/* 133:140 */     setBundle(new Bundle(thread));
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Sequence(String type)
/* 137:    */   {
/* 138:147 */     this();
/* 139:148 */     addType(type);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Sequence(Thread thread)
/* 143:    */   {
/* 144:152 */     this();
/* 145:153 */     setBundle(new Bundle(new Thread(thread)));
/* 146:    */   }
/* 147:    */   
/* 148:    */   public Sequence(Bundle b)
/* 149:    */   {
/* 150:157 */     this();
/* 151:158 */     setBundle(b);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public Sequence(boolean readOnly, String suffix)
/* 155:    */   {
/* 156:166 */     super(readOnly, suffix);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Sequence(String label, List<Entity> elements)
/* 160:    */   {
/* 161:170 */     this(label);
/* 162:171 */     for (Entity t : elements) {
/* 163:172 */       addElement(t);
/* 164:    */     }
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void addElement(int index, Entity element)
/* 168:    */   {
/* 169:180 */     saveState();
/* 170:181 */     this.elements.add(index, element);
/* 171:182 */     fireNotification();
/* 172:    */   }
/* 173:    */   
/* 174:    */   public boolean containsDeprecated(Entity element)
/* 175:    */   {
/* 176:189 */     for (int j = 0; j < getNumberOfChildren(); j++) {
/* 177:190 */       if (getElement(j).isEqual(element)) {
/* 178:191 */         return true;
/* 179:    */       }
/* 180:    */     }
/* 181:194 */     return false;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public boolean contains(Entity element)
/* 185:    */   {
/* 186:201 */     return getElements().contains(element);
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setElements(Vector<Entity> v)
/* 190:    */   {
/* 191:209 */     for (int i = 0; i < v.size(); i++) {
/* 192:210 */       if (!(v.elementAt(i) instanceof Entity))
/* 193:    */       {
/* 194:211 */         System.err.println("Oops, trying to put something into a sequence that is not an Entity instance!");
/* 195:212 */         return;
/* 196:    */       }
/* 197:    */     }
/* 198:215 */     saveState();
/* 199:216 */     this.elements = v;
/* 200:217 */     fireNotification();
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setElementAt(Entity elt, int index)
/* 204:    */   {
/* 205:222 */     saveState();
/* 206:223 */     this.elements.setElementAt(elt, index);
/* 207:224 */     fireNotification();
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void clearElements()
/* 211:    */   {
/* 212:231 */     this.elements.clear();
/* 213:    */   }
/* 214:    */   
/* 215:    */   public List<Entity> getAllComponents()
/* 216:    */   {
/* 217:239 */     List<Entity> result = super.getAllComponents();
/* 218:240 */     result.addAll(getElements());
/* 219:241 */     return result;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public char getPrettyPrintType()
/* 223:    */   {
/* 224:249 */     return 'S';
/* 225:    */   }
/* 226:    */   
/* 227:    */   public String filler(boolean compact)
/* 228:    */   {
/* 229:256 */     if (compact)
/* 230:    */     {
/* 231:257 */       String result = "";
/* 232:258 */       result = result + "sequence (" + this.elements.size() + " elements)\n";
/* 233:259 */       for (int i = 0; i < this.elements.size(); i++)
/* 234:    */       {
/* 235:260 */         String s = ((Entity)this.elements.elementAt(i)).toXML(compact);
/* 236:261 */         result = result + s.replaceFirst("\n", new StringBuilder(" (element ").append(i).append(")\n").toString()) + "\n";
/* 237:    */       }
/* 238:263 */       return result;
/* 239:    */     }
/* 240:266 */     String result = "";
/* 241:267 */     for (int i = 0; i < this.elements.size(); i++) {
/* 242:268 */       result = result + Tags.tag("element", ((Entity)this.elements.elementAt(i)).toXML(compact));
/* 243:    */     }
/* 244:270 */     return Tags.tag("sequence", result);
/* 245:    */   }
/* 246:    */   
/* 247:    */   public String fillerSansName(boolean compact)
/* 248:    */   {
/* 249:278 */     String result = "";
/* 250:279 */     if (this.elements != null)
/* 251:    */     {
/* 252:280 */       if (compact)
/* 253:    */       {
/* 254:282 */         result = result + "sequence (" + this.elements.size() + " elements)\n";
/* 255:283 */         for (int i = 0; i < this.elements.size(); i++)
/* 256:    */         {
/* 257:284 */           String s = ((Entity)this.elements.elementAt(i)).toXMLSansName(compact);
/* 258:285 */           result = result + s.replaceFirst("\n", new StringBuilder(" (element ").append(i).append(")\n").toString()) + "\n";
/* 259:    */         }
/* 260:287 */         return result;
/* 261:    */       }
/* 262:290 */       for (int i = 0; i < this.elements.size(); i++) {
/* 263:291 */         result = result + Tags.tag("element", ((Entity)this.elements.elementAt(i)).toXMLSansName(compact));
/* 264:    */       }
/* 265:293 */       return Tags.tag("sequence", result);
/* 266:    */     }
/* 267:297 */     return result;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public Sequence rebuildWithoutElements()
/* 271:    */   {
/* 272:307 */     Sequence result = new Sequence();
/* 273:308 */     Bundle bundle = (Bundle)getBundle().clone();
/* 274:309 */     result.setBundle(bundle);
/* 275:310 */     Vector<Entity.LabelValuePair> propertyList = clonePropertyList();
/* 276:311 */     result.setPropertyList(propertyList);
/* 277:312 */     return result;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public Object clone(EntityFactory factory)
/* 281:    */   {
/* 282:319 */     Sequence sequence = factory.newSequence();
/* 283:320 */     Bundle bundle = (Bundle)getBundle().clone();
/* 284:321 */     sequence.setBundle(bundle);
/* 285:322 */     Vector<Entity> v = getElements();
/* 286:323 */     for (int i = 0; i < v.size(); i++)
/* 287:    */     {
/* 288:324 */       Entity t = (Entity)v.elementAt(i);
/* 289:325 */       sequence.addElement(t);
/* 290:    */     }
/* 291:328 */     v = getModifiers();
/* 292:329 */     for (int i = 0; i < v.size(); i++)
/* 293:    */     {
/* 294:330 */       Entity t = (Entity)v.elementAt(i);
/* 295:331 */       sequence.addModifier(t);
/* 296:    */     }
/* 297:334 */     return sequence;
/* 298:    */   }
/* 299:    */   
/* 300:    */   protected Entity deepClone(EntityFactory factory, IdentityHashMap<Entity, Entity> cloneMap, boolean newId)
/* 301:    */   {
/* 302:341 */     if (cloneMap.containsKey(this)) {
/* 303:342 */       return (Entity)cloneMap.get(this);
/* 304:    */     }
/* 305:345 */     Sequence clone = factory.newSequence();
/* 306:346 */     if (!newId) {
/* 307:347 */       clone.setNameSuffix(getNameSuffix());
/* 308:    */     }
/* 309:350 */     Bundle bundleClone = (Bundle)getBundle().clone();
/* 310:351 */     clone.setBundle(bundleClone);
/* 311:352 */     Vector<Entity> elements = getElements();
/* 312:353 */     for (int i = 0; i < elements.size(); i++)
/* 313:    */     {
/* 314:354 */       Entity element = (Entity)elements.elementAt(i);
/* 315:355 */       Entity elementClone = element.deepClone(factory, cloneMap, newId);
/* 316:356 */       clone.addElement(elementClone);
/* 317:    */     }
/* 318:359 */     Vector<Entity> modifiers = getModifiers();
/* 319:360 */     for (int i = 0; i < modifiers.size(); i++)
/* 320:    */     {
/* 321:361 */       Entity modifier = (Entity)modifiers.elementAt(i);
/* 322:362 */       Entity modifierClone = modifier.deepClone(factory, cloneMap, newId);
/* 323:363 */       clone.addModifier(modifierClone);
/* 324:    */     }
/* 325:366 */     cloneMap.put(this, clone);
/* 326:367 */     return clone;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public Entity cloneForResolver()
/* 330:    */   {
/* 331:374 */     return cloneForResolver(EntityFactoryDefault.getInstance());
/* 332:    */   }
/* 333:    */   
/* 334:    */   public Entity cloneForResolver(EntityFactory factory)
/* 335:    */   {
/* 336:379 */     boolean cloneRelations = false;
/* 337:    */     
/* 338:381 */     Sequence sequence = factory.newSequence();
/* 339:382 */     Bundle bundle = (Bundle)getBundle().clone();
/* 340:383 */     sequence.setBundle(bundle);
/* 341:384 */     Vector<Entity> v = getElements();
/* 342:385 */     for (int i = 0; i < v.size(); i++)
/* 343:    */     {
/* 344:386 */       Entity t = (Entity)v.elementAt(i);
/* 345:387 */       Entity clone = t.cloneForResolver(factory);
/* 346:388 */       sequence.addElement(clone);
/* 347:    */     }
/* 348:391 */     v = getModifiers();
/* 349:392 */     for (int i = 0; i < v.size(); i++)
/* 350:    */     {
/* 351:393 */       Entity t = (Entity)v.elementAt(i);
/* 352:394 */       Entity tClone = t.cloneForResolver(factory);
/* 353:395 */       sequence.addModifier(tClone);
/* 354:    */     }
/* 355:399 */     if (cloneRelations)
/* 356:    */     {
/* 357:405 */       Vector<Function> subjectRelations = new Vector();
/* 358:406 */       subjectRelations.addAll(getSubjectOf());
/* 359:407 */       for (Iterator<Function> i = subjectRelations.iterator(); i.hasNext();)
/* 360:    */       {
/* 361:408 */         Relation relation = (Relation)i.next();
/* 362:409 */         Relation newRelation = factory.newRelation(sequence, relation.getObject());
/* 363:    */         
/* 364:411 */         bundle = (Bundle)relation.getBundle().clone();
/* 365:412 */         newRelation.setBundle(bundle);
/* 366:413 */         fine("Cloning subjectOf() relation " + relation.getName() + " to " + newRelation.getName());
/* 367:    */       }
/* 368:417 */       Vector<Relation> objectRelations = new Vector();
/* 369:418 */       objectRelations.addAll(getObjectOf());
/* 370:419 */       for (Iterator<Relation> i = objectRelations.iterator(); i.hasNext();)
/* 371:    */       {
/* 372:420 */         Relation relation = (Relation)i.next();
/* 373:421 */         Relation newRelation = factory.newRelation(relation.getSubject(), sequence);
/* 374:    */         
/* 375:423 */         bundle = (Bundle)relation.getBundle().clone();
/* 376:424 */         newRelation.setBundle(bundle);
/* 377:425 */         fine("Cloning objectOf() relation " + relation.getName() + " to " + newRelation.getName());
/* 378:    */       }
/* 379:    */     }
/* 380:429 */     return sequence;
/* 381:    */   }
/* 382:    */   
/* 383:    */   protected static void finest(Object s)
/* 384:    */   {
/* 385:440 */     Logger.getLogger("things.Sequence").finest("Sequence: " + s);
/* 386:    */   }
/* 387:    */   
/* 388:    */   protected static void finer(Object s)
/* 389:    */   {
/* 390:444 */     Logger.getLogger("things.Sequence").finer("Sequence: " + s);
/* 391:    */   }
/* 392:    */   
/* 393:    */   protected static void fine(Object s)
/* 394:    */   {
/* 395:448 */     Logger.getLogger("things.Sequence").fine("Sequence: " + s);
/* 396:    */   }
/* 397:    */   
/* 398:    */   protected static void config(Object s)
/* 399:    */   {
/* 400:452 */     Logger.getLogger("things.Sequence").config("Sequence: " + s);
/* 401:    */   }
/* 402:    */   
/* 403:    */   protected static void info(Object s)
/* 404:    */   {
/* 405:456 */     Logger.getLogger("things.Sequence").info("Sequence: " + s);
/* 406:    */   }
/* 407:    */   
/* 408:    */   protected static void warning(Object s)
/* 409:    */   {
/* 410:460 */     Logger.getLogger("things.Sequence").warning("Sequence: " + s);
/* 411:    */   }
/* 412:    */   
/* 413:    */   protected static void severe(Object s)
/* 414:    */   {
/* 415:464 */     Logger.getLogger("things.Sequence").severe("Sequence: " + s);
/* 416:    */   }
/* 417:    */   
/* 418:    */   public static void main(String[] argv) {}
/* 419:    */   
/* 420:    */   public void addAll(Sequence sequence)
/* 421:    */   {
/* 422:474 */     Vector<Entity> v = sequence.getElements();
/* 423:475 */     for (Iterator<Entity> i = v.iterator(); i.hasNext();) {
/* 424:476 */       addElement((Entity)i.next());
/* 425:    */     }
/* 426:    */   }
/* 427:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.Sequence
 * JD-Core Version:    0.7.0.1
 */