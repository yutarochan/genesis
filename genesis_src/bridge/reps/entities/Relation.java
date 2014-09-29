/*   1:    */ package bridge.reps.entities;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.IdentityHashMap;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Set;
/*   7:    */ import java.util.Vector;
/*   8:    */ 
/*   9:    */ public class Relation
/*  10:    */   extends Function
/*  11:    */ {
/*  12:    */   public static final String PARTICLE = "particle";
/*  13:    */   public static final String MODAL = "modal";
/*  14:    */   public static final String NOT = "not";
/*  15:    */   public static final String SHOULD = "should";
/*  16:    */   protected Entity object;
/*  17:    */   
/*  18:    */   public boolean entityP()
/*  19:    */   {
/*  20: 30 */     return false;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public boolean entityP(String type)
/*  24:    */   {
/*  25: 34 */     return false;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public boolean functionP()
/*  29:    */   {
/*  30: 38 */     return false;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean functionP(String type)
/*  34:    */   {
/*  35: 42 */     return false;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public boolean relationP()
/*  39:    */   {
/*  40: 46 */     return true;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean relationP(String type)
/*  44:    */   {
/*  45: 50 */     return isAPrimed(type);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public boolean sequenceP()
/*  49:    */   {
/*  50: 54 */     return false;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean sequenceP(String type)
/*  54:    */   {
/*  55: 58 */     return false;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setObject(Entity t)
/*  59:    */   {
/*  60: 62 */     saveState();
/*  61: 63 */     this.object = t;
/*  62: 64 */     if (t != null) {
/*  63: 64 */       t.addObjectOf(this);
/*  64:    */     }
/*  65: 65 */     fireNotification();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Entity getObject()
/*  69:    */   {
/*  70: 69 */     return this.object;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Set<Entity> getChildren()
/*  74:    */   {
/*  75: 73 */     Set<Entity> result = super.getChildren();
/*  76: 74 */     result.add(getObject());
/*  77: 75 */     return result;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getNumberOfChildren()
/*  81:    */   {
/*  82: 79 */     return 2;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public boolean isEqual(Object o)
/*  86:    */   {
/*  87: 92 */     if ((o instanceof Relation))
/*  88:    */     {
/*  89: 93 */       Relation r = (Relation)o;
/*  90: 94 */       if (r.getObject().isEqual(getObject())) {
/*  91: 95 */         return super.isEqual(r);
/*  92:    */       }
/*  93:    */     }
/*  94: 98 */     return false;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Relation(Entity subject, Entity object)
/*  98:    */   {
/*  99:111 */     super(subject);
/* 100:112 */     setObject(object);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Relation(Thread thread, Entity subject, Entity object)
/* 104:    */   {
/* 105:116 */     this(subject, object);
/* 106:117 */     setBundle(new Bundle(new Thread(thread)));
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Relation(Bundle b, Entity subject, Entity object)
/* 110:    */   {
/* 111:121 */     this(subject, object);
/* 112:122 */     setBundle(b);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Relation(String type, Entity subject, Entity object)
/* 116:    */   {
/* 117:129 */     this(subject, object);
/* 118:130 */     addType(type);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Relation(boolean readOnly, String suffix, Entity subject, Entity object)
/* 122:    */   {
/* 123:138 */     super(readOnly, suffix, subject);
/* 124:139 */     setObject(object);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public List<Entity> getAllComponents()
/* 128:    */   {
/* 129:147 */     List<Entity> result = super.getAllComponents();
/* 130:148 */     result.add(getObject());
/* 131:149 */     return result;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public char getPrettyPrintType()
/* 135:    */   {
/* 136:157 */     return 'R';
/* 137:    */   }
/* 138:    */   
/* 139:    */   protected String filler(boolean compact)
/* 140:    */   {
/* 141:166 */     if (compact)
/* 142:    */     {
/* 143:167 */       String s = this.subject.toXML(compact).replaceFirst("\n", " (subject)\n");
/* 144:168 */       String o = this.object.toXML(compact).replaceFirst("\n", " (object)\n");
/* 145:169 */       return s + "\n" + o;
/* 146:    */     }
/* 147:172 */     return Tags.tag("subject", this.subject.toXML(compact)) + Tags.tag("object", this.object.toXML(compact));
/* 148:    */   }
/* 149:    */   
/* 150:    */   protected String fillerSansName(boolean compact)
/* 151:    */   {
/* 152:180 */     if (compact)
/* 153:    */     {
/* 154:181 */       String s = "";
/* 155:182 */       String o = "";
/* 156:183 */       if (this.subject != null) {
/* 157:184 */         s = this.subject.toXMLSansName(compact).replaceFirst("\n", " (subject)\n");
/* 158:    */       }
/* 159:186 */       if (this.object != null) {
/* 160:187 */         o = this.object.toXMLSansName(compact).replaceFirst("\n", " (object)\n");
/* 161:    */       }
/* 162:189 */       return s + "\n" + o;
/* 163:    */     }
/* 164:192 */     String s = "";
/* 165:193 */     String o = "";
/* 166:194 */     if (this.subject != null) {
/* 167:195 */       s = Tags.tag("subject", this.subject.toXMLSansName(compact));
/* 168:    */     }
/* 169:197 */     if (this.object != null) {
/* 170:198 */       o = Tags.tag("object", this.object.toXMLSansName(compact));
/* 171:    */     }
/* 172:200 */     return s + o;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public Relation rebuild()
/* 176:    */   {
/* 177:208 */     Relation result = new Relation(getSubject(), getObject());
/* 178:209 */     Bundle bundle = (Bundle)getBundle().clone();
/* 179:210 */     result.setBundle(bundle);
/* 180:211 */     Vector<Entity.LabelValuePair> propertyList = clonePropertyList();
/* 181:212 */     result.setPropertyList(propertyList);
/* 182:213 */     return result;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public Object clone(EntityFactory factory)
/* 186:    */   {
/* 187:220 */     Relation relation = factory.newRelation(getSubject(), getObject());
/* 188:221 */     Bundle bundle = (Bundle)getBundle().clone();
/* 189:222 */     relation.setBundle(bundle);
/* 190:    */     
/* 191:224 */     Vector v = getModifiers();
/* 192:225 */     for (int i = 0; i < v.size(); i++)
/* 193:    */     {
/* 194:226 */       Entity t = (Entity)v.elementAt(i);
/* 195:227 */       relation.addModifier(t);
/* 196:    */     }
/* 197:229 */     return relation;
/* 198:    */   }
/* 199:    */   
/* 200:    */   protected Entity deepClone(EntityFactory factory, IdentityHashMap<Entity, Entity> cloneMap, boolean newId)
/* 201:    */   {
/* 202:236 */     if (cloneMap.containsKey(this)) {
/* 203:237 */       return (Entity)cloneMap.get(this);
/* 204:    */     }
/* 205:240 */     Entity subjectClone = getSubject().deepClone(factory, cloneMap, newId);
/* 206:241 */     Entity objectClone = getObject().deepClone(factory, cloneMap, newId);
/* 207:    */     
/* 208:243 */     Relation clone = factory.newRelation(subjectClone, objectClone);
/* 209:244 */     if (!newId) {
/* 210:245 */       clone.setNameSuffix(getNameSuffix());
/* 211:    */     }
/* 212:248 */     Bundle bundleClone = (Bundle)getBundle().clone();
/* 213:249 */     clone.setBundle(bundleClone);
/* 214:    */     
/* 215:251 */     Vector modifiers = getModifiers();
/* 216:252 */     for (int i = 0; i < modifiers.size(); i++)
/* 217:    */     {
/* 218:253 */       Entity modifier = (Entity)modifiers.elementAt(i);
/* 219:254 */       Entity modifierClone = modifier.deepClone(factory, cloneMap, newId);
/* 220:255 */       clone.addModifier(modifierClone);
/* 221:    */     }
/* 222:258 */     cloneMap.put(this, clone);
/* 223:259 */     return clone;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public Entity cloneForResolver()
/* 227:    */   {
/* 228:266 */     return cloneForResolver(EntityFactoryDefault.getInstance());
/* 229:    */   }
/* 230:    */   
/* 231:    */   public Entity cloneForResolver(EntityFactory factory)
/* 232:    */   {
/* 233:270 */     Relation newRelation = (Relation)clone(factory);
/* 234:271 */     Entity newSubject = getSubject().cloneForResolver(factory);
/* 235:272 */     Entity newObject = getObject().cloneForResolver(factory);
/* 236:273 */     newRelation.setSubject(newSubject);
/* 237:274 */     newRelation.setObject(newObject);
/* 238:    */     
/* 239:276 */     Vector v = getModifiers();
/* 240:277 */     for (int i = 0; i < v.size(); i++)
/* 241:    */     {
/* 242:278 */       Entity t = (Entity)v.elementAt(i);
/* 243:279 */       Entity tClone = t.cloneForResolver(factory);
/* 244:280 */       newRelation.addModifier(tClone);
/* 245:    */     }
/* 246:284 */     return newRelation;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public Thread getModalThread()
/* 250:    */   {
/* 251:293 */     return getThread("modal");
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setModalThread(Thread t)
/* 255:    */   {
/* 256:297 */     if (getModalThread() != null) {
/* 257:298 */       getBundle().removeThread(getModalThread());
/* 258:    */     }
/* 259:300 */     addThread(t);
/* 260:    */   }
/* 261:    */   
/* 262:    */   public boolean isInModalThread(String s)
/* 263:    */   {
/* 264:304 */     if (getModalThread() == null) {
/* 265:305 */       return false;
/* 266:    */     }
/* 267:307 */     return getModalThread().contains(s);
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void addToModalThread(String s)
/* 271:    */   {
/* 272:311 */     if (getModalThread() == null) {
/* 273:312 */       addThread(new Thread("modal"));
/* 274:    */     }
/* 275:314 */     getModalThread().addType(s);
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void removeFromModalThread(String s)
/* 279:    */   {
/* 280:318 */     if (getModalThread() != null) {
/* 281:319 */       getModalThread().remove(s);
/* 282:    */     }
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void negate()
/* 286:    */   {
/* 287:324 */     addToModalThread("not");
/* 288:    */   }
/* 289:    */   
/* 290:    */   public boolean isNegated()
/* 291:    */   {
/* 292:328 */     return isInModalThread("not");
/* 293:    */   }
/* 294:    */   
/* 295:    */   public static void main(String[] argv)
/* 296:    */   {
/* 297:336 */     EntityFactory factory = EntityFactoryDefault.getInstance();
/* 298:337 */     Entity t1 = factory.newThing();
/* 299:338 */     t1.addType("Mark");
/* 300:339 */     Entity t2 = factory.newThing();
/* 301:340 */     t2.addType("Steph");
/* 302:341 */     Relation r = factory.newRelation(t1, t2);
/* 303:342 */     r.addType("Siblings");
/* 304:    */     
/* 305:344 */     Relation r2 = (Relation)r.clone();
/* 306:345 */     System.out.println(r);
/* 307:346 */     System.out.println(r.clone());
/* 308:347 */     System.out.println("Equals? " + r.isEqual(r2));
/* 309:348 */     System.out.println(r.cloneForResolver());
/* 310:    */     
/* 311:350 */     t1 = new Entity("John");
/* 312:351 */     t2 = new Entity("Mary");
/* 313:352 */     r = new Relation("kissed", t1, t2);
/* 314:    */     
/* 315:354 */     r.addToModalThread("able");
/* 316:356 */     if (r.isInModalThread("able")) {
/* 317:357 */       System.out.println("Can't even do it");
/* 318:    */     }
/* 319:359 */     r.removeFromModalThread("able");
/* 320:361 */     if (r.isInModalThread("able")) {
/* 321:362 */       System.out.println("Can't even do it");
/* 322:    */     }
/* 323:    */   }
/* 324:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.Relation
 * JD-Core Version:    0.7.0.1
 */