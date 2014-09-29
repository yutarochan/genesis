/*   1:    */ package bridge.reps.entities;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import java.util.Set;
/*  10:    */ import java.util.Vector;
/*  11:    */ 
/*  12:    */ public class Correspondence
/*  13:    */ {
/*  14:    */   private Entity source;
/*  15:    */   private Entity target;
/*  16:    */   private HashMap<String, Entity> nameToThing;
/*  17:    */   private HashMap<Entity, Entity> mappings;
/*  18:    */   private HashMap<CEntry, Double> scores;
/*  19:    */   private double score;
/*  20:    */   
/*  21:    */   public Correspondence(Entity s, Entity t)
/*  22:    */   {
/*  23: 31 */     this(s, t, 0.0D);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Correspondence(Entity s, Entity t, double score)
/*  27:    */   {
/*  28: 35 */     this.source = s;
/*  29: 36 */     this.target = t;
/*  30: 37 */     this.nameToThing = new HashMap();
/*  31: 38 */     this.mappings = new HashMap();
/*  32: 39 */     this.scores = new HashMap();
/*  33: 40 */     setScore(score);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setScore(double s)
/*  37:    */   {
/*  38: 43 */     this.score = s;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Entity getSource()
/*  42:    */   {
/*  43: 45 */     return this.source;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Entity getTarget()
/*  47:    */   {
/*  48: 46 */     return this.target;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Map<Entity, Entity> getMappings()
/*  52:    */   {
/*  53: 47 */     return this.mappings;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Map<CEntry, Double> getScores()
/*  57:    */   {
/*  58: 48 */     return this.scores;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public double getScore()
/*  62:    */   {
/*  63: 49 */     return this.score;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public double getScore(Entity x, Entity y)
/*  67:    */   {
/*  68: 52 */     Double result = new Double(-1.0D);
/*  69: 53 */     CEntry e = new CEntry(x, y);
/*  70: 54 */     for (Iterator<CEntry> i = this.scores.keySet().iterator(); i.hasNext();)
/*  71:    */     {
/*  72: 55 */       CEntry c = (CEntry)i.next();
/*  73: 56 */       if (c == e)
/*  74:    */       {
/*  75: 57 */         result = (Double)this.scores.get(c);
/*  76: 58 */         break;
/*  77:    */       }
/*  78:    */     }
/*  79: 62 */     return result.doubleValue();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Correspondence getSubCorrespondence(Entity s, Entity t)
/*  83:    */   {
/*  84: 66 */     if ((s == null) || (t == null)) {
/*  85: 66 */       return null;
/*  86:    */     }
/*  87: 67 */     Correspondence result = new Correspondence(s, t);
/*  88: 68 */     Vector<Entity> queue = new Vector();
/*  89:    */     
/*  90: 70 */     queue.add(s);
/*  91: 71 */     queue.add(t);
/*  92: 73 */     while (!queue.isEmpty())
/*  93:    */     {
/*  94: 74 */       Entity next = (Entity)queue.remove(0);
/*  95:    */       
/*  96: 76 */       Entity match = (Entity)this.mappings.get(next);
/*  97: 77 */       if (match != null) {
/*  98: 78 */         result.addMatch(next, match);
/*  99:    */       }
/* 100: 81 */       if (next.functionP())
/* 101:    */       {
/* 102: 82 */         match = next.getSubject();
/* 103: 83 */         queue.add(match);
/* 104:    */       }
/* 105: 84 */       else if (next.relationP())
/* 106:    */       {
/* 107: 85 */         match = next.getSubject();
/* 108: 86 */         queue.add(match);
/* 109: 87 */         match = next.getObject();
/* 110: 88 */         queue.add(match);
/* 111:    */       }
/* 112: 89 */       else if (next.sequenceP())
/* 113:    */       {
/* 114: 90 */         queue.addAll(next.getElements());
/* 115:    */       }
/* 116:    */     }
/* 117: 95 */     return result;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public CEntry getMatch(String s, String t)
/* 121:    */   {
/* 122: 99 */     Entity source = (Entity)this.nameToThing.get(s);
/* 123:100 */     Entity target = (Entity)this.nameToThing.get(t);
/* 124:101 */     return new CEntry(source, target);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Set<CEntry> getWithinTreeMatches(CEntry entry)
/* 128:    */   {
/* 129:105 */     HashSet<CEntry> result = new HashSet();
/* 130:    */     
/* 131:    */ 
/* 132:108 */     Entity entrySource = (Entity)entry.key;
/* 133:109 */     Entity entryTarget = (Entity)entry.value;
/* 134:111 */     for (Iterator<CEntry> i = this.scores.keySet().iterator(); i.hasNext();)
/* 135:    */     {
/* 136:112 */       CEntry test = (CEntry)i.next();
/* 137:113 */       Entity testSource = (Entity)test.key;
/* 138:114 */       Entity testTarget = (Entity)test.value;
/* 139:115 */       if ((entrySource.isAncestorOf(testSource)) && (entryTarget.isAncestorOf(testTarget))) {
/* 140:116 */         result.add(test);
/* 141:    */       }
/* 142:    */     }
/* 143:120 */     return result;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Set<CEntry> getInconsistentMatches(CEntry entry)
/* 147:    */   {
/* 148:124 */     HashSet<CEntry> result = new HashSet();
/* 149:    */     
/* 150:    */ 
/* 151:127 */     Entity entrySource = (Entity)entry.key;
/* 152:128 */     Entity entryTarget = (Entity)entry.value;
/* 153:130 */     for (Iterator<CEntry> i = this.scores.keySet().iterator(); i.hasNext();)
/* 154:    */     {
/* 155:131 */       CEntry test = (CEntry)i.next();
/* 156:132 */       Entity testSource = (Entity)test.key;
/* 157:133 */       Entity testTarget = (Entity)test.value;
/* 158:134 */       if ((!entrySource.isAncestorOf(testSource)) && (entryTarget.isAncestorOf(testTarget))) {
/* 159:135 */         result.add(test);
/* 160:    */       }
/* 161:138 */       if ((entrySource.isAncestorOf(testSource)) && (!entryTarget.isAncestorOf(testTarget))) {
/* 162:139 */         result.add(test);
/* 163:    */       }
/* 164:    */     }
/* 165:143 */     return result;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public Entity getMatchSource(int id)
/* 169:    */   {
/* 170:148 */     for (Iterator<Entity> i = this.mappings.keySet().iterator(); i.hasNext();)
/* 171:    */     {
/* 172:149 */       Entity result = (Entity)i.next();
/* 173:150 */       if (result.getID() == id) {
/* 174:151 */         return result;
/* 175:    */       }
/* 176:    */     }
/* 177:154 */     return null;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public Entity getMatchTarget(int id)
/* 181:    */   {
/* 182:159 */     for (Iterator<Entity> i = this.mappings.values().iterator(); i.hasNext();)
/* 183:    */     {
/* 184:160 */       Entity result = (Entity)i.next();
/* 185:161 */       if ((result != null) && 
/* 186:162 */         (result.getID() == id)) {
/* 187:163 */         return result;
/* 188:    */       }
/* 189:    */     }
/* 190:167 */     return null;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void addMatch(Entity x, Entity y)
/* 194:    */   {
/* 195:171 */     addMatch(x, y, 0.0D);
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void addMatch(Entity x, Entity y, double d)
/* 199:    */   {
/* 200:175 */     this.mappings.put(x, y);
/* 201:176 */     this.nameToThing.put(x.getName(), x);
/* 202:177 */     this.nameToThing.put(y.getName(), y);
/* 203:178 */     addScore(new CEntry(x, y), new Double(d));
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void addScore(CEntry e, Double d)
/* 207:    */   {
/* 208:182 */     this.scores.put(e, d);
/* 209:    */   }
/* 210:    */   
/* 211:    */   public String getName()
/* 212:    */   {
/* 213:186 */     String result = "";
/* 214:187 */     if (getSource() != null) {
/* 215:188 */       result = result + getSource().getName();
/* 216:    */     } else {
/* 217:190 */       result = result + "null";
/* 218:    */     }
/* 219:193 */     result = result + " :: ";
/* 220:195 */     if (getTarget() != null) {
/* 221:196 */       result = result + getTarget().getName();
/* 222:    */     } else {
/* 223:198 */       result = result + "null";
/* 224:    */     }
/* 225:201 */     return result;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public String toString()
/* 229:    */   {
/* 230:205 */     StringBuffer result = new StringBuffer();
/* 231:    */     
/* 232:207 */     result.append(getName());
/* 233:208 */     result.append("\n");
/* 234:212 */     for (Iterator i = this.mappings.entrySet().iterator(); i.hasNext();)
/* 235:    */     {
/* 236:213 */       Map.Entry entry = (Map.Entry)i.next();
/* 237:214 */       Entity base = (Entity)entry.getKey();
/* 238:215 */       Entity target = (Entity)entry.getValue();
/* 239:216 */       result.append("<");
/* 240:217 */       result.append(base.getName());
/* 241:218 */       result.append(" ;; ");
/* 242:219 */       result.append(target.getName());
/* 243:220 */       result.append(">");
/* 244:221 */       if (i.hasNext()) {
/* 245:221 */         result.append("\n");
/* 246:    */       }
/* 247:    */     }
/* 248:223 */     return result.toString();
/* 249:    */   }
/* 250:    */   
/* 251:    */   public boolean equals(Object o)
/* 252:    */   {
/* 253:    */     try
/* 254:    */     {
/* 255:228 */       c = (Correspondence)o;
/* 256:    */     }
/* 257:    */     catch (ClassCastException e)
/* 258:    */     {
/* 259:    */       Correspondence c;
/* 260:228 */       return false;
/* 261:    */     }
/* 262:    */     Correspondence c;
/* 263:230 */     if (((getSource() == null ? 1 : 0) & (getTarget() == null ? 1 : 0)) != 0)
/* 264:    */     {
/* 265:234 */       if (((c.getSource() == null ? 1 : 0) & (c.getTarget() == null ? 1 : 0) & getMappings().equals(c.getMappings()) & getScores().equals(c.getScores())) != 0) {
/* 266:235 */         return true;
/* 267:    */       }
/* 268:237 */       return false;
/* 269:    */     }
/* 270:239 */     if (getSource() == null)
/* 271:    */     {
/* 272:243 */       if ((c.getSource() == null & getTarget().equals(c.getTarget()) & getMappings().equals(c.getMappings()) & getScores().equals(c.getScores()))) {
/* 273:244 */         return true;
/* 274:    */       }
/* 275:246 */       return false;
/* 276:    */     }
/* 277:248 */     if (getTarget() == null)
/* 278:    */     {
/* 279:252 */       if ((getSource().equals(c.getSource()) & c.getTarget() == null & getMappings().equals(c.getMappings()) & getScores().equals(c.getScores()))) {
/* 280:253 */         return true;
/* 281:    */       }
/* 282:255 */       return false;
/* 283:    */     }
/* 284:261 */     if ((getSource().equals(c.getSource()) & getTarget().equals(c.getTarget()) & getMappings().equals(c.getMappings()) & getScores().equals(c.getScores()))) {
/* 285:262 */       return true;
/* 286:    */     }
/* 287:264 */     return false;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public int hashCode()
/* 291:    */   {
/* 292:270 */     if (((getSource() == null ? 1 : 0) & (getTarget() == null ? 1 : 0)) != 0) {
/* 293:271 */       return getMappings().hashCode() + getScores().hashCode();
/* 294:    */     }
/* 295:272 */     if (getSource() == null) {
/* 296:273 */       return getTarget().hashCode() + getMappings().hashCode() + getScores().hashCode();
/* 297:    */     }
/* 298:274 */     if (getTarget() == null) {
/* 299:275 */       return getSource().hashCode() + getMappings().hashCode() + getScores().hashCode();
/* 300:    */     }
/* 301:277 */     return getSource().hashCode() + getTarget().hashCode() + getMappings().hashCode() + getScores().hashCode();
/* 302:    */   }
/* 303:    */   
/* 304:    */   public class CEntry
/* 305:    */     implements Map.Entry
/* 306:    */   {
/* 307:    */     Object key;
/* 308:    */     Object value;
/* 309:    */     
/* 310:    */     public CEntry(Object k, Object v)
/* 311:    */     {
/* 312:286 */       this.key = k;
/* 313:287 */       this.value = v;
/* 314:    */     }
/* 315:    */     
/* 316:    */     public Object getKey()
/* 317:    */     {
/* 318:290 */       return this.key;
/* 319:    */     }
/* 320:    */     
/* 321:    */     public Object getValue()
/* 322:    */     {
/* 323:291 */       return this.value;
/* 324:    */     }
/* 325:    */     
/* 326:    */     public Object setValue(Object v)
/* 327:    */     {
/* 328:294 */       Object old = this.value;
/* 329:295 */       this.value = v;
/* 330:296 */       return old;
/* 331:    */     }
/* 332:    */     
/* 333:    */     public boolean equals(Object obj)
/* 334:    */     {
/* 335:300 */       if ((obj instanceof CEntry))
/* 336:    */       {
/* 337:301 */         CEntry e = (CEntry)obj;
/* 338:302 */         if ((e.key == this.key) && (e.value == this.value)) {
/* 339:303 */           return true;
/* 340:    */         }
/* 341:    */       }
/* 342:306 */       return false;
/* 343:    */     }
/* 344:    */     
/* 345:    */     public int hashCode()
/* 346:    */     {
/* 347:310 */       int result = super.hashCode();
/* 348:311 */       if (this.key != null) {
/* 349:312 */         result += this.key.hashCode();
/* 350:    */       }
/* 351:315 */       if (this.value != null) {
/* 352:316 */         result += this.value.hashCode();
/* 353:    */       }
/* 354:319 */       return result;
/* 355:    */     }
/* 356:    */   }
/* 357:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.Correspondence
 * JD-Core Version:    0.7.0.1
 */