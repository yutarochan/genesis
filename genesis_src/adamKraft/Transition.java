/*   1:    */ package adamKraft;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.FileInputStream;
/*   6:    */ import java.io.InputStreamReader;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.Map;
/*  11:    */ 
/*  12:    */ public abstract class Transition
/*  13:    */ {
/*  14: 14 */   public static final String[] DEFAULT_ACTORS = { "red man", "blue man", "the ball" };
/*  15: 15 */   protected static Map<String, Class> classMap = new HashMap();
/*  16:    */   protected Integer oldValue;
/*  17:    */   protected Integer newValue;
/*  18:    */   protected int frameNumber;
/*  19:    */   
/*  20:    */   public static Transition parse(String tstring)
/*  21:    */   {
/*  22: 22 */     String[] tokens = tstring.split("\\s");
/*  23: 23 */     ArrayList<String> valid = new ArrayList();
/*  24: 24 */     for (String token : tokens)
/*  25:    */     {
/*  26: 25 */       token = token.intern();
/*  27: 26 */       if (token != "") {
/*  28: 27 */         valid.add(token);
/*  29:    */       }
/*  30:    */     }
/*  31: 30 */     if (valid.size() != 4) {
/*  32: 31 */       throw new RuntimeException("invalid string: " + tstring);
/*  33:    */     }
/*  34: 33 */     int frameNumber = Integer.valueOf((String)valid.get(0)).intValue();
/*  35: 34 */     String pattern = (String)valid.get(1);
/*  36:    */     Integer oldVal;
/*  37:    */     Integer oldVal;
/*  38: 36 */     if (valid.get(2) == "?") {
/*  39: 37 */       oldVal = null;
/*  40:    */     } else {
/*  41: 39 */       oldVal = Integer.valueOf((String)valid.get(2));
/*  42:    */     }
/*  43:    */     Integer newVal;
/*  44:    */     Integer newVal;
/*  45: 42 */     if (valid.get(3) == "?") {
/*  46: 43 */       newVal = null;
/*  47:    */     } else {
/*  48: 45 */       newVal = Integer.valueOf((String)valid.get(3));
/*  49:    */     }
/*  50: 47 */     Transition t = dispatch(pattern);
/*  51: 48 */     t.init(oldVal, newVal, frameNumber);
/*  52: 49 */     return t;
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected static Transition dispatch(String pattern)
/*  56:    */   {
/*  57: 54 */     String type = pattern.substring(0, 1).intern();
/*  58: 55 */     String args = pattern.substring(1).intern();
/*  59:    */     try
/*  60:    */     {
/*  61: 57 */       Class clazz = (Class)classMap.get(type);
/*  62: 58 */       if (clazz == null)
/*  63:    */       {
/*  64: 59 */         System.out.println("Error, unknown transition type: " + type);
/*  65: 60 */         System.exit(-1);
/*  66:    */       }
/*  67: 62 */       Transition t = (Transition)clazz.newInstance();
/*  68: 63 */       t.init(args);
/*  69: 64 */       return t;
/*  70:    */     }
/*  71:    */     catch (InstantiationException e)
/*  72:    */     {
/*  73: 66 */       e.printStackTrace();
/*  74: 67 */       System.exit(-1);
/*  75:    */     }
/*  76:    */     catch (IllegalAccessException e)
/*  77:    */     {
/*  78: 69 */       e.printStackTrace();
/*  79: 70 */       System.exit(-1);
/*  80:    */     }
/*  81: 72 */     return null;
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected void init(Integer oldVal, Integer newVal, int frameNumber)
/*  85:    */   {
/*  86: 76 */     this.oldValue = oldVal;
/*  87: 77 */     this.newValue = newVal;
/*  88: 78 */     this.frameNumber = frameNumber;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String valueString(Integer value)
/*  92:    */   {
/*  93: 82 */     if (value == null) {
/*  94: 83 */       return "undefined";
/*  95:    */     }
/*  96: 85 */     throw new RuntimeException("bad value: " + value);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String nameString()
/* 100:    */   {
/* 101: 90 */     return nameString(DEFAULT_ACTORS);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String actorString(int actor)
/* 105:    */   {
/* 106:100 */     return actorString(actor, DEFAULT_ACTORS);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String actorString(int actor, String[] names)
/* 110:    */   {
/* 111:104 */     if ((actor >= 0) && (actor < names.length)) {
/* 112:105 */       return names[actor];
/* 113:    */     }
/* 114:107 */     throw new RuntimeException("unrecognized actor: " + actor);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String toString()
/* 118:    */   {
/* 119:112 */     return render(DEFAULT_ACTORS);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String render(String[] actors)
/* 123:    */   {
/* 124:116 */     return "the " + nameString(actors) + " went from " + valueString(this.oldValue) + " to " + valueString(this.newValue);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public static abstract class OneObject
/* 128:    */     extends Transition
/* 129:    */   {
/* 130:    */     protected Integer actor;
/* 131:    */     
/* 132:    */     protected void init(String args)
/* 133:    */     {
/* 134:123 */       init(Integer.valueOf(args));
/* 135:    */     }
/* 136:    */     
/* 137:    */     protected void init(Integer actor)
/* 138:    */     {
/* 139:126 */       this.actor = actor;
/* 140:    */     }
/* 141:    */     
/* 142:    */     public String nameString(String[] actorNames)
/* 143:    */     {
/* 144:129 */       return typeString() + " of " + actorString(this.actor.intValue(), actorNames);
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   public static abstract class TwoObjects
/* 149:    */     extends Transition
/* 150:    */   {
/* 151:    */     protected Integer actor1;
/* 152:    */     protected Integer actor2;
/* 153:    */     
/* 154:    */     protected void init(String args)
/* 155:    */     {
/* 156:136 */       String[] foo = args.split(",");
/* 157:137 */       init(Integer.valueOf(foo[0]), Integer.valueOf(foo[1]));
/* 158:    */     }
/* 159:    */     
/* 160:    */     protected void init(Integer actor1, Integer actor2)
/* 161:    */     {
/* 162:140 */       this.actor1 = actor1;
/* 163:141 */       this.actor2 = actor2;
/* 164:    */     }
/* 165:    */     
/* 166:    */     public String nameString(String[] actorNames)
/* 167:    */     {
/* 168:144 */       return typeString() + " between " + actorString(this.actor1.intValue(), actorNames) + " and " + actorString(this.actor2.intValue(), actorNames);
/* 169:    */     }
/* 170:    */   }
/* 171:    */   
/* 172:    */   public static class Exists
/* 173:    */     extends Transition.OneObject
/* 174:    */   {
/* 175:    */     public String typeString()
/* 176:    */     {
/* 177:151 */       return "existence";
/* 178:    */     }
/* 179:    */     
/* 180:    */     public String valueString(Integer value)
/* 181:    */     {
/* 182:155 */       if (value == null) {
/* 183:155 */         return super.valueString(value);
/* 184:    */       }
/* 185:156 */       return value.intValue() == 1 ? "present" : value.intValue() == 0 ? "absent" : super.valueString(value);
/* 186:    */     }
/* 187:    */   }
/* 188:    */   
/* 189:    */   public static class XPos
/* 190:    */     extends Transition.OneObject
/* 191:    */   {
/* 192:    */     public String typeString()
/* 193:    */     {
/* 194:163 */       return "horizontal movement";
/* 195:    */     }
/* 196:    */     
/* 197:    */     public String valueString(Integer value)
/* 198:    */     {
/* 199:167 */       if (value == null) {
/* 200:167 */         return super.valueString(value);
/* 201:    */       }
/* 202:168 */       return value.intValue() == 0 ? "staying still" : value.intValue() == 1 ? "moving right" : value.intValue() == -1 ? "moving left" : super.valueString(value);
/* 203:    */     }
/* 204:    */   }
/* 205:    */   
/* 206:    */   public static class YPos
/* 207:    */     extends Transition.OneObject
/* 208:    */   {
/* 209:    */     public String typeString()
/* 210:    */     {
/* 211:175 */       return "vertical movement";
/* 212:    */     }
/* 213:    */     
/* 214:    */     public String valueString(Integer value)
/* 215:    */     {
/* 216:179 */       if (value == null) {
/* 217:179 */         return super.valueString(value);
/* 218:    */       }
/* 219:180 */       return value.intValue() == 0 ? "staying still" : value.intValue() == 1 ? "moving up" : value.intValue() == -1 ? "moving down" : super.valueString(value);
/* 220:    */     }
/* 221:    */   }
/* 222:    */   
/* 223:    */   public static class Size
/* 224:    */     extends Transition.OneObject
/* 225:    */   {
/* 226:    */     public String typeString()
/* 227:    */     {
/* 228:187 */       return "size";
/* 229:    */     }
/* 230:    */     
/* 231:    */     public String valueString(Integer value)
/* 232:    */     {
/* 233:191 */       if (value == null) {
/* 234:191 */         return super.valueString(value);
/* 235:    */       }
/* 236:192 */       return value.intValue() == 0 ? "not changing" : value.intValue() == 1 ? "growing" : value.intValue() == -1 ? "shrinking" : super.valueString(value);
/* 237:    */     }
/* 238:    */   }
/* 239:    */   
/* 240:    */   public static class Movement
/* 241:    */     extends Transition.OneObject
/* 242:    */   {
/* 243:    */     public String typeString()
/* 244:    */     {
/* 245:199 */       return "movement";
/* 246:    */     }
/* 247:    */     
/* 248:    */     public String valueString(Integer value)
/* 249:    */     {
/* 250:203 */       if (value == null) {
/* 251:203 */         return super.valueString(value);
/* 252:    */       }
/* 253:204 */       return value.intValue() == 1 ? "moving" : value.intValue() == 0 ? "not moving" : super.valueString(value);
/* 254:    */     }
/* 255:    */   }
/* 256:    */   
/* 257:    */   public static class Distance
/* 258:    */     extends Transition.TwoObjects
/* 259:    */   {
/* 260:    */     public String typeString()
/* 261:    */     {
/* 262:211 */       return "distance";
/* 263:    */     }
/* 264:    */     
/* 265:    */     public String valueString(Integer value)
/* 266:    */     {
/* 267:215 */       if (value == null) {
/* 268:215 */         return super.valueString(value);
/* 269:    */       }
/* 270:216 */       return value.intValue() == 0 ? "not changing" : value.intValue() == 1 ? "increasing" : value.intValue() == -1 ? "decreasing" : super.valueString(value);
/* 271:    */     }
/* 272:    */   }
/* 273:    */   
/* 274:    */   public static class Direction
/* 275:    */     extends Transition.TwoObjects
/* 276:    */   {
/* 277:    */     public String typeString()
/* 278:    */     {
/* 279:223 */       return "direction";
/* 280:    */     }
/* 281:    */     
/* 282:    */     public String valueString(Integer value)
/* 283:    */     {
/* 284:227 */       if (value == null) {
/* 285:227 */         return super.valueString(value);
/* 286:    */       }
/* 287:228 */       return value.intValue() == 3 ? "down" : value.intValue() == 2 ? "left" : value.intValue() == 1 ? "up" : value.intValue() == 0 ? "right" : super.valueString(value);
/* 288:    */     }
/* 289:    */     
/* 290:    */     public String nameString(String[] actorNames)
/* 291:    */     {
/* 292:232 */       return typeString() + " of " + actorString(this.actor1.intValue(), actorNames) + " relative to " + actorString(this.actor2.intValue(), actorNames);
/* 293:    */     }
/* 294:    */   }
/* 295:    */   
/* 296:    */   public static class Contact
/* 297:    */     extends Transition.TwoObjects
/* 298:    */   {
/* 299:    */     public String typeString()
/* 300:    */     {
/* 301:238 */       return "contact";
/* 302:    */     }
/* 303:    */     
/* 304:    */     public String valueString(Integer value)
/* 305:    */     {
/* 306:242 */       if (value == null) {
/* 307:242 */         return super.valueString(value);
/* 308:    */       }
/* 309:243 */       return value.intValue() == 1 ? "present" : value.intValue() == 0 ? "absent" : super.valueString(value);
/* 310:    */     }
/* 311:    */   }
/* 312:    */   
/* 313:    */   public static class Rotation
/* 314:    */     extends Transition.TwoObjects
/* 315:    */   {
/* 316:    */     public String typeString()
/* 317:    */     {
/* 318:250 */       return "rotation";
/* 319:    */     }
/* 320:    */     
/* 321:    */     public String valueString(Integer value)
/* 322:    */     {
/* 323:254 */       if (value == null) {
/* 324:254 */         return super.valueString(value);
/* 325:    */       }
/* 326:255 */       return value.intValue() == -1 ? "counterclockwise" : value.intValue() == 1 ? "clockwise" : value.intValue() == 0 ? "not rotating" : super.valueString(value);
/* 327:    */     }
/* 328:    */     
/* 329:    */     public String nameString(String[] actorNames)
/* 330:    */     {
/* 331:259 */       return typeString() + " of " + actorString(this.actor1.intValue(), actorNames) + " around " + actorString(this.actor2.intValue(), actorNames);
/* 332:    */     }
/* 333:    */   }
/* 334:    */   
/* 335:    */   public static class Focus
/* 336:    */     extends Transition.TwoObjects
/* 337:    */   {
/* 338:    */     public String typeString()
/* 339:    */     {
/* 340:266 */       return "focus change";
/* 341:    */     }
/* 342:    */     
/* 343:    */     protected void init(String args) {}
/* 344:    */     
/* 345:    */     public String valueString(Integer value)
/* 346:    */     {
/* 347:274 */       return "happened";
/* 348:    */     }
/* 349:    */     
/* 350:    */     public String nameString(String[] actorNames)
/* 351:    */     {
/* 352:278 */       String start = this.oldValue == null ? "undefined" : actorString(this.oldValue.intValue(), actorNames);
/* 353:279 */       String finish = this.newValue == null ? "undefined" : actorString(this.newValue.intValue(), actorNames);
/* 354:280 */       return typeString() + " from " + start + " to " + finish;
/* 355:    */     }
/* 356:    */     
/* 357:    */     public String toString()
/* 358:    */     {
/* 359:283 */       return "a " + nameString() + " " + valueString(this.oldValue);
/* 360:    */     }
/* 361:    */   }
/* 362:    */   
/* 363:    */   static
/* 364:    */   {
/* 365:288 */     classMap.put("E", Exists.class);
/* 366:289 */     classMap.put("X", XPos.class);
/* 367:290 */     classMap.put("Y", YPos.class);
/* 368:291 */     classMap.put("M", Movement.class);
/* 369:292 */     classMap.put("S", Size.class);
/* 370:293 */     classMap.put("M", Movement.class);
/* 371:294 */     classMap.put("R", Distance.class);
/* 372:295 */     classMap.put("T", Direction.class);
/* 373:296 */     classMap.put("C", Contact.class);
/* 374:297 */     classMap.put("W", Rotation.class);
/* 375:298 */     classMap.put("F", Focus.class);
/* 376:    */   }
/* 377:    */   
/* 378:    */   public static void main(String[] args)
/* 379:    */     throws Exception
/* 380:    */   {
/* 381:306 */     File foo = SeedlingHack.allExamplesOf("give")[0];
/* 382:307 */     FileInputStream fis = new FileInputStream(foo);
/* 383:308 */     InputStreamReader in = new InputStreamReader(fis);
/* 384:309 */     BufferedReader b = new BufferedReader(in);
/* 385:    */     String line;
/* 386:311 */     while ((line = b.readLine()) != null)
/* 387:    */     {
/* 388:    */       String line;
/* 389:312 */       System.out.println(parse(line));
/* 390:    */     }
/* 391:    */   }
/* 392:    */   
/* 393:    */   protected abstract void init(String paramString);
/* 394:    */   
/* 395:    */   public abstract String nameString(String[] paramArrayOfString);
/* 396:    */   
/* 397:    */   public abstract String typeString();
/* 398:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adamKraft.Transition
 * JD-Core Version:    0.7.0.1
 */