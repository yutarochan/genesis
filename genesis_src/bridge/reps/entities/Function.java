/*   1:    */ package bridge.reps.entities;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.IdentityHashMap;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Set;
/*   8:    */ import java.util.Vector;
/*   9:    */ 
/*  10:    */ public class Function
/*  11:    */   extends Entity
/*  12:    */ {
/*  13:    */   protected Entity subject;
/*  14:    */   
/*  15:    */   public boolean entityP()
/*  16:    */   {
/*  17: 23 */     return false;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public boolean entityP(String type)
/*  21:    */   {
/*  22: 27 */     return false;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public boolean relationP()
/*  26:    */   {
/*  27: 31 */     return false;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public boolean relationP(String type)
/*  31:    */   {
/*  32: 35 */     return false;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean sequenceP()
/*  36:    */   {
/*  37: 39 */     return false;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean sequenceP(String type)
/*  41:    */   {
/*  42: 43 */     return false;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Function() {}
/*  46:    */   
/*  47:    */   public Function(boolean readOnly, String suffix)
/*  48:    */   {
/*  49: 50 */     super(readOnly, suffix);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean functionP()
/*  53:    */   {
/*  54: 58 */     return true;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean functionP(String type)
/*  58:    */   {
/*  59: 62 */     return isAPrimed(type);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Entity getSubject()
/*  63:    */   {
/*  64: 66 */     return this.subject;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Set<Entity> getChildren()
/*  68:    */   {
/*  69: 70 */     Set<Entity> result = new HashSet();
/*  70: 71 */     result.add(getSubject());
/*  71: 72 */     return result;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getNumberOfChildren()
/*  75:    */   {
/*  76: 76 */     return 1;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setSubject(Entity t)
/*  80:    */   {
/*  81: 80 */     saveState();
/*  82: 81 */     this.subject = t;
/*  83: 82 */     if (t != null) {
/*  84: 82 */       t.addSubjectOf(this);
/*  85:    */     }
/*  86: 83 */     fireNotification();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean isEqual(Object o)
/*  90:    */   {
/*  91: 87 */     if ((o instanceof Function))
/*  92:    */     {
/*  93: 88 */       Function d = (Function)o;
/*  94: 89 */       if (d.getSubject().isEqual(getSubject())) {
/*  95: 90 */         return super.isEqual(d);
/*  96:    */       }
/*  97:    */     }
/*  98: 93 */     return false;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Function(Entity t)
/* 102:    */   {
/* 103:103 */     setSubject(t);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Function(String string, Entity t)
/* 107:    */   {
/* 108:107 */     this(t);
/* 109:108 */     addType(string);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public Function(Thread thread, Entity t)
/* 113:    */   {
/* 114:112 */     this(t);
/* 115:113 */     setBundle(new Bundle(new Thread(thread)));
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Function(Bundle b, Entity t)
/* 119:    */   {
/* 120:117 */     this(t);
/* 121:118 */     setBundle(b);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public Function(boolean readOnly, String suffix, Entity subject)
/* 125:    */   {
/* 126:126 */     super(readOnly, suffix);
/* 127:127 */     setSubject(subject);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public List<Entity> getAllComponents()
/* 131:    */   {
/* 132:135 */     List<Entity> result = super.getAllComponents();
/* 133:136 */     result.add(getSubject());
/* 134:137 */     return result;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public char getPrettyPrintType()
/* 138:    */   {
/* 139:145 */     return 'F';
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected String filler(boolean compact)
/* 143:    */   {
/* 144:154 */     if (compact) {
/* 145:155 */       return this.subject.toXML(compact).replaceFirst("\n", " (subject)\n") + "\n";
/* 146:    */     }
/* 147:158 */     return Tags.tag("subject", this.subject.toXML(compact));
/* 148:    */   }
/* 149:    */   
/* 150:    */   protected String fillerSansName(boolean compact)
/* 151:    */   {
/* 152:169 */     if (this.subject != null)
/* 153:    */     {
/* 154:170 */       if (compact) {
/* 155:171 */         return this.subject.toXMLSansName(compact).replaceFirst("\n", " (subject)\n") + "\n";
/* 156:    */       }
/* 157:174 */       return Tags.tag("subject", this.subject.toXMLSansName(compact));
/* 158:    */     }
/* 159:177 */     return "";
/* 160:    */   }
/* 161:    */   
/* 162:    */   public Function rebuild()
/* 163:    */   {
/* 164:185 */     Function result = new Function(getSubject());
/* 165:186 */     Bundle bundle = (Bundle)getBundle().clone();
/* 166:187 */     result.setBundle(bundle);
/* 167:188 */     Vector<Entity.LabelValuePair> propertyList = clonePropertyList();
/* 168:189 */     result.setPropertyList(propertyList);
/* 169:190 */     return result;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public Object clone(EntityFactory factory)
/* 173:    */   {
/* 174:197 */     Function derivative = factory.newDerivative(getSubject());
/* 175:198 */     Bundle bundle = (Bundle)getBundle().clone();
/* 176:199 */     derivative.setBundle(bundle);
/* 177:    */     
/* 178:201 */     Vector<Entity> v = getModifiers();
/* 179:202 */     for (int i = 0; i < v.size(); i++)
/* 180:    */     {
/* 181:203 */       Entity t = (Entity)v.elementAt(i);
/* 182:204 */       derivative.addModifier(t);
/* 183:    */     }
/* 184:207 */     return derivative;
/* 185:    */   }
/* 186:    */   
/* 187:    */   protected Entity deepClone(EntityFactory factory, IdentityHashMap<Entity, Entity> cloneMap, boolean newId)
/* 188:    */   {
/* 189:214 */     if (cloneMap.containsKey(this)) {
/* 190:215 */       return (Entity)cloneMap.get(this);
/* 191:    */     }
/* 192:218 */     Entity subjectClone = getSubject().deepClone(factory, cloneMap, newId);
/* 193:    */     
/* 194:220 */     Function clone = factory.newDerivative(subjectClone);
/* 195:221 */     if (!newId) {
/* 196:222 */       clone.setNameSuffix(getNameSuffix());
/* 197:    */     }
/* 198:224 */     Bundle bundleClone = (Bundle)getBundle().clone();
/* 199:225 */     clone.setBundle(bundleClone);
/* 200:    */     
/* 201:227 */     Vector<Entity> modifiers = getModifiers();
/* 202:228 */     for (int i = 0; i < modifiers.size(); i++)
/* 203:    */     {
/* 204:229 */       Entity modifier = (Entity)modifiers.elementAt(i);
/* 205:230 */       Entity modifierClone = modifier.deepClone(factory, cloneMap, newId);
/* 206:231 */       clone.addModifier(modifierClone);
/* 207:    */     }
/* 208:234 */     cloneMap.put(this, clone);
/* 209:235 */     return clone;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public Entity cloneForResolver()
/* 213:    */   {
/* 214:242 */     return cloneForResolver(EntityFactoryDefault.getInstance());
/* 215:    */   }
/* 216:    */   
/* 217:    */   public Entity cloneForResolver(EntityFactory factory)
/* 218:    */   {
/* 219:246 */     Function newDerivative = (Function)clone(factory);
/* 220:247 */     Entity newSubject = getSubject().cloneForResolver(factory);
/* 221:248 */     newDerivative.setSubject(newSubject);
/* 222:    */     
/* 223:250 */     Vector<Entity> v = getModifiers();
/* 224:251 */     for (int i = 0; i < v.size(); i++)
/* 225:    */     {
/* 226:252 */       Entity t = (Entity)v.elementAt(i);
/* 227:253 */       Entity tClone = t.cloneForResolver(factory);
/* 228:254 */       newDerivative.addModifier(tClone);
/* 229:    */     }
/* 230:258 */     return newDerivative;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public static void main(String[] argv)
/* 234:    */   {
/* 235:265 */     EntityFactory factory = EntityFactoryDefault.getInstance();
/* 236:266 */     Entity t = factory.newThing();
/* 237:267 */     t.addType("Mark");
/* 238:268 */     Function d = factory.newDerivative(t);
/* 239:269 */     d.addType("was here");
/* 240:270 */     System.out.println(d);
/* 241:271 */     Function d2 = (Function)d.clone();
/* 242:272 */     System.out.println(d2);
/* 243:273 */     System.out.println("Equal? " + d.isEqual(d2));
/* 244:274 */     System.out.println(d.cloneForResolver());
/* 245:    */   }
/* 246:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.Function
 * JD-Core Version:    0.7.0.1
 */