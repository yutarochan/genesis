/*   1:    */ package magicLess.utils;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Function;
/*   6:    */ import bridge.reps.entities.Relation;
/*   7:    */ import bridge.reps.entities.Sequence;
/*   8:    */ import bridge.reps.entities.Thread;
/*   9:    */ import constants.RecognizedRepresentations;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.HashSet;
/*  12:    */ import java.util.Iterator;
/*  13:    */ import java.util.LinkedList;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Set;
/*  16:    */ import magicLess.distancemetrics.Operations;
/*  17:    */ 
/*  18:    */ public class EntityUtils
/*  19:    */ {
/*  20:    */   public static boolean containsByReference(Entity t, Object pointer)
/*  21:    */   {
/*  22: 28 */     if (t == pointer) {
/*  23: 29 */       return true;
/*  24:    */     }
/*  25: 31 */     for (Entity c : t.getChildren()) {
/*  26: 32 */       if (containsByReference(c, pointer)) {
/*  27: 33 */         return true;
/*  28:    */       }
/*  29:    */     }
/*  30: 36 */     return false;
/*  31:    */   }
/*  32:    */   
/*  33: 44 */   private static String xmlIndent = "   ";
/*  34:    */   
/*  35:    */   public static String adamsPrettyPrint(Entity t)
/*  36:    */   {
/*  37: 48 */     Set<String> visited = new HashSet();
/*  38: 49 */     return ppHelp(t, visited, 0);
/*  39:    */   }
/*  40:    */   
/*  41:    */   private static String ppHelp(Entity t, Set<String> visited, int tabLevel)
/*  42:    */   {
/*  43: 52 */     String markup = "";
/*  44: 53 */     if ((t instanceof Relation))
/*  45:    */     {
/*  46: 54 */       markup = markup + javaIsBusted(xmlIndent, tabLevel) + "<RELATION>\n";
/*  47: 55 */       markup = markup + javaIsBusted(xmlIndent, tabLevel + 1) + "<NAME>" + t.getName() + "</NAME>\n";
/*  48: 56 */       if (!visited.contains(t.getName()))
/*  49:    */       {
/*  50: 57 */         visited.add(t.getName());
/*  51: 58 */         markup = markup + ppHelp(((Relation)t).getObject(), visited, tabLevel + 1);
/*  52: 59 */         markup = markup + ppHelp(((Relation)t).getSubject(), visited, tabLevel + 1);
/*  53:    */       }
/*  54:    */       else
/*  55:    */       {
/*  56: 62 */         markup = markup + javaIsBusted(xmlIndent, tabLevel + 1) + "...body defined previously...\n";
/*  57:    */       }
/*  58: 64 */       markup = markup + ppBundle(t.getBundle(), tabLevel + 1);
/*  59: 65 */       markup = markup + javaIsBusted(xmlIndent, tabLevel) + "</RELATION>\n";
/*  60:    */     }
/*  61: 67 */     else if ((t instanceof Function))
/*  62:    */     {
/*  63: 68 */       markup = markup + javaIsBusted(xmlIndent, tabLevel) + "<DERIVATIVE>\n";
/*  64: 69 */       markup = markup + javaIsBusted(xmlIndent, tabLevel + 1) + "<NAME>" + t.getName() + "</NAME>\n";
/*  65: 70 */       if (!visited.contains(t.getName()))
/*  66:    */       {
/*  67: 71 */         visited.add(t.getName());
/*  68: 72 */         markup = markup + ppHelp(((Function)t).getSubject(), visited, tabLevel + 1);
/*  69:    */       }
/*  70:    */       else
/*  71:    */       {
/*  72: 74 */         markup = markup + javaIsBusted(xmlIndent, tabLevel + 1) + "...body defined previously...\n";
/*  73:    */       }
/*  74: 76 */       markup = markup + ppBundle(t.getBundle(), tabLevel + 1);
/*  75: 77 */       markup = markup + javaIsBusted(xmlIndent, tabLevel) + "</DERIVATIVE>\n";
/*  76:    */     }
/*  77: 79 */     else if ((t instanceof Sequence))
/*  78:    */     {
/*  79: 80 */       markup = markup + javaIsBusted(xmlIndent, tabLevel) + "<SEQUENCE>\n";
/*  80: 81 */       markup = markup + javaIsBusted(xmlIndent, tabLevel + 1) + "<NAME>" + t.getName() + "</NAME>\n";
/*  81: 82 */       if (!visited.contains(t.getName()))
/*  82:    */       {
/*  83: 83 */         visited.add(t.getName());
/*  84: 84 */         for (Entity sub : ((Sequence)t).getElements()) {
/*  85: 85 */           markup = markup + ppHelp(sub, visited, tabLevel + 1);
/*  86:    */         }
/*  87:    */       }
/*  88:    */       else
/*  89:    */       {
/*  90: 88 */         markup = markup + javaIsBusted(xmlIndent, tabLevel + 1) + "...body defined previously...\n";
/*  91:    */       }
/*  92: 90 */       markup = markup + ppBundle(t.getBundle(), tabLevel + 1);
/*  93: 91 */       markup = markup + javaIsBusted(xmlIndent, tabLevel) + "</SEQUENCE>\n";
/*  94:    */     }
/*  95:    */     else
/*  96:    */     {
/*  97: 94 */       markup = markup + javaIsBusted(xmlIndent, tabLevel) + "<THING>\n";
/*  98: 95 */       markup = markup + javaIsBusted(xmlIndent, tabLevel + 1) + "<NAME>" + FakeXMLProcessor.escape(t.getName()) + "</NAME>\n";
/*  99: 96 */       markup = markup + ppBundle(t.getBundle(), tabLevel + 1);
/* 100: 97 */       markup = markup + javaIsBusted(xmlIndent, tabLevel) + "</THING>\n";
/* 101:    */     }
/* 102: 99 */     return markup;
/* 103:    */   }
/* 104:    */   
/* 105:    */   private static String ppBundle(Bundle b, int tabLevel)
/* 106:    */   {
/* 107:102 */     if (b == null) {
/* 108:102 */       return "";
/* 109:    */     }
/* 110:103 */     String markup = javaIsBusted(xmlIndent, tabLevel) + "<BUNDLE>\n";
/* 111:104 */     for (Thread t : b) {
/* 112:105 */       markup = markup + ppThread(t, tabLevel + 1);
/* 113:    */     }
/* 114:107 */     markup = markup + javaIsBusted(xmlIndent, tabLevel) + "</BUNDLE>\n";
/* 115:108 */     return markup;
/* 116:    */   }
/* 117:    */   
/* 118:    */   private static String ppThread(Thread t, int tabLevel)
/* 119:    */   {
/* 120:111 */     String markup = javaIsBusted(xmlIndent, tabLevel) + "<THREAD> ";
/* 121:112 */     for (String s : t) {
/* 122:113 */       markup = markup + FakeXMLProcessor.escape(s) + " ";
/* 123:    */     }
/* 124:115 */     markup = markup + "</THREAD>\n";
/* 125:116 */     return markup;
/* 126:    */   }
/* 127:    */   
/* 128:    */   private static String javaIsBusted(String s, int multiplier)
/* 129:    */   {
/* 130:123 */     if (multiplier == 0) {
/* 131:123 */       return "";
/* 132:    */     }
/* 133:124 */     if (multiplier > 0) {
/* 134:125 */       return s + javaIsBusted(s, multiplier - 1);
/* 135:    */     }
/* 136:127 */     throw new IllegalArgumentException("multiplying String by a negative integer is undefined.");
/* 137:    */   }
/* 138:    */   
/* 139:    */   public static boolean cheapCompare(Entity a, Entity b)
/* 140:    */   {
/* 141:134 */     if (!getRepType(a).equals(getRepType(b))) {
/* 142:135 */       return false;
/* 143:    */     }
/* 144:136 */     if (Operations.distance(a.getBundle(), b.getBundle()) > 1.E-009D) {
/* 145:137 */       return false;
/* 146:    */     }
/* 147:138 */     if (a.getChildren().size() != b.getChildren().size()) {
/* 148:139 */       return false;
/* 149:    */     }
/* 150:141 */     Iterator<Entity> aIter = a.getChildren().iterator();
/* 151:142 */     Iterator<Entity> bIter = b.getChildren().iterator();
/* 152:143 */     while (aIter.hasNext()) {
/* 153:144 */       if (!cheapCompare((Entity)aIter.next(), (Entity)bIter.next())) {
/* 154:145 */         return false;
/* 155:    */       }
/* 156:    */     }
/* 157:148 */     return true;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public static boolean isConsistent(Entity a, Entity b)
/* 161:    */   {
/* 162:153 */     if (!getRepType(a).equals(getRepType(b))) {
/* 163:154 */       return false;
/* 164:    */     }
/* 165:156 */     if (Operations.distance(a.getBundle(), b.getBundle()) < 1.E-009D) {
/* 166:157 */       return true;
/* 167:    */     }
/* 168:159 */     if (a.getChildren().size() != b.getChildren().size()) {
/* 169:160 */       return false;
/* 170:    */     }
/* 171:162 */     Iterator<Entity> aIter = a.getChildren().iterator();
/* 172:163 */     Iterator<Entity> bIter = b.getChildren().iterator();
/* 173:164 */     while (aIter.hasNext()) {
/* 174:165 */       if (!isConsistent((Entity)aIter.next(), (Entity)bIter.next())) {
/* 175:166 */         return false;
/* 176:    */       }
/* 177:    */     }
/* 178:169 */     return true;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public static boolean hasComponents(Entity a)
/* 182:    */   {
/* 183:173 */     return (a.getDescendants() != null) && (a.getDescendants().size() != 0);
/* 184:    */   }
/* 185:    */   
/* 186:    */   public static void replaceAllComponents(Entity replaceIn, Entity replaceWith)
/* 187:    */   {
/* 188:182 */     replaceWith = replaceWith.deepClone();
/* 189:183 */     replaceChildren(replaceIn, replaceWith);
/* 190:184 */     replaceIn.setBundle(replaceWith.getBundle());
/* 191:    */   }
/* 192:    */   
/* 193:    */   public static void replaceChildren(Entity replaceIn, Entity replaceWith)
/* 194:    */   {
/* 195:193 */     replaceIn.setName(replaceWith.getName());
/* 196:    */     
/* 197:195 */     replaceIn.clearModifiers();
/* 198:196 */     for (Entity t : replaceWith.getModifiers()) {
/* 199:197 */       replaceIn.addModifier(t);
/* 200:    */     }
/* 201:199 */     if ((replaceIn instanceof Relation))
/* 202:    */     {
/* 203:200 */       assert ((replaceWith instanceof Relation));
/* 204:201 */       replaceIn.setObject(replaceWith.getObject());
/* 205:    */     }
/* 206:203 */     if ((replaceIn instanceof Function))
/* 207:    */     {
/* 208:204 */       assert ((replaceWith instanceof Function));
/* 209:205 */       replaceIn.setSubject(replaceWith.getSubject());
/* 210:    */     }
/* 211:207 */     if ((replaceIn instanceof Sequence))
/* 212:    */     {
/* 213:208 */       assert ((replaceWith instanceof Sequence));
/* 214:209 */       ((Sequence)replaceIn).setElements(replaceWith.getElements());
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   public static List<Entity> getOrderedChildren(Entity t)
/* 219:    */   {
/* 220:214 */     if ((t instanceof Relation)) {
/* 221:215 */       return getOrderedRChildren((Relation)t);
/* 222:    */     }
/* 223:216 */     if ((t instanceof Function)) {
/* 224:217 */       return getOrderedDChildren((Function)t);
/* 225:    */     }
/* 226:218 */     if ((t instanceof Sequence)) {
/* 227:219 */       return getOrderedSChildren((Sequence)t);
/* 228:    */     }
/* 229:221 */     return new ArrayList();
/* 230:    */   }
/* 231:    */   
/* 232:    */   public static List<Entity> getOrderedDescendants(Entity t)
/* 233:    */   {
/* 234:224 */     List<Entity> result = new ArrayList();
/* 235:225 */     List<Entity> queue = new LinkedList();
/* 236:226 */     queue.addAll(getOrderedChildren(t));
/* 237:228 */     while (!queue.isEmpty())
/* 238:    */     {
/* 239:229 */       Entity next = (Entity)queue.remove(0);
/* 240:230 */       if (result.add(next)) {
/* 241:233 */         queue.addAll(next.getChildren());
/* 242:    */       }
/* 243:    */     }
/* 244:235 */     return result;
/* 245:    */   }
/* 246:    */   
/* 247:    */   private static List<Entity> getOrderedRChildren(Relation t)
/* 248:    */   {
/* 249:239 */     List<Entity> superCh = getOrderedDChildren(t);
/* 250:240 */     superCh.add(t.getObject());
/* 251:241 */     return superCh;
/* 252:    */   }
/* 253:    */   
/* 254:    */   private static List<Entity> getOrderedDChildren(Function t)
/* 255:    */   {
/* 256:244 */     List<Entity> result = new ArrayList();
/* 257:245 */     result.add(t.getSubject());
/* 258:246 */     return result;
/* 259:    */   }
/* 260:    */   
/* 261:    */   private static List<Entity> getOrderedSChildren(Sequence t)
/* 262:    */   {
/* 263:249 */     List<Entity> result = new ArrayList();
/* 264:250 */     result.addAll(t.getElements());
/* 265:251 */     return result;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public static Object getRepType(Entity input)
/* 269:    */   {
/* 270:257 */     Thread tag = input.getPrimedThread();
/* 271:258 */     if (tag.contains("travel")) {
/* 272:259 */       return RecognizedRepresentations.TRAJECTORY_THING;
/* 273:    */     }
/* 274:261 */     if (tag.contains("cause")) {
/* 275:262 */       return RecognizedRepresentations.CAUSE_THING;
/* 276:    */     }
/* 277:264 */     if (tag.contains("time-relation")) {
/* 278:265 */       return RecognizedRepresentations.TIME_REPRESENTATION;
/* 279:    */     }
/* 280:267 */     if (tag.contains("place")) {
/* 281:268 */       return RecognizedRepresentations.PLACE_REPRESENTATION;
/* 282:    */     }
/* 283:270 */     if (tag.contains("path-element")) {
/* 284:271 */       return RecognizedRepresentations.PATH_ELEMENT_THING;
/* 285:    */     }
/* 286:273 */     if (tag.contains("path")) {
/* 287:274 */       return RecognizedRepresentations.PATH_THING;
/* 288:    */     }
/* 289:276 */     if (tag.contains("roles")) {
/* 290:277 */       return RecognizedRepresentations.ROLE_THING;
/* 291:    */     }
/* 292:279 */     if (tag.contains("action")) {
/* 293:280 */       return RecognizedRepresentations.ACTION_REPRESENTATION;
/* 294:    */     }
/* 295:282 */     if (tag.contains("social relation")) {
/* 296:283 */       return RecognizedRepresentations.SOCIAL_REPRESENTATION;
/* 297:    */     }
/* 298:285 */     if (tag.contains("has-mental-state")) {
/* 299:286 */       return RecognizedRepresentations.MENTAL_STATE_THING;
/* 300:    */     }
/* 301:288 */     if (tag.contains("transfer")) {
/* 302:289 */       return RecognizedRepresentations.TRANSFER_THING;
/* 303:    */     }
/* 304:291 */     if (tag.contains("transition")) {
/* 305:292 */       return RecognizedRepresentations.TRANSITION_REPRESENTATION;
/* 306:    */     }
/* 307:294 */     if (tag.contains("geometry")) {
/* 308:295 */       return RecognizedRepresentations.GEOMETRY_THING;
/* 309:    */     }
/* 310:297 */     if (tag.contains("force")) {
/* 311:298 */       return RecognizedRepresentations.FORCE_THING;
/* 312:    */     }
/* 313:300 */     if (tag.contains(RecognizedRepresentations.QUESTION_THING)) {
/* 314:301 */       return RecognizedRepresentations.QUESTION_THING;
/* 315:    */     }
/* 316:303 */     if (tag.contains("answer")) {
/* 317:304 */       return RecognizedRepresentations.ANSWER_THING;
/* 318:    */     }
/* 319:306 */     if (tag.contains("block")) {
/* 320:307 */       return RecognizedRepresentations.BLOCK_THING;
/* 321:    */     }
/* 322:309 */     if (tag.contains("threadMemory")) {
/* 323:310 */       return RecognizedRepresentations.THREAD_THING;
/* 324:    */     }
/* 325:312 */     if (tag.contains(RecognizedRepresentations.PLACE_REPRESENTATION)) {
/* 326:313 */       return RecognizedRepresentations.PLACE_REPRESENTATION;
/* 327:    */     }
/* 328:317 */     return "unknown representation type";
/* 329:    */   }
/* 330:    */   
/* 331:    */   public static String toOpenOfficeFormula(Entity t)
/* 332:    */   {
/* 333:321 */     String s = " bold ";
/* 334:322 */     if ((t instanceof Relation))
/* 335:    */     {
/* 336:323 */       s = s + "color red left [  font fixed stack{  \n";
/* 337:324 */       for (Entity child : getOrderedChildren(t)) {
/* 338:325 */         s = s + "alignl " + toOpenOfficeFormula(child) + " # ";
/* 339:    */       }
/* 340:327 */       s = s + "alignl size 8 \"R \" ";
/* 341:    */     }
/* 342:328 */     else if ((t instanceof Function))
/* 343:    */     {
/* 344:329 */       s = s + "color blue left [  font fixed stack{  \n";
/* 345:330 */       for (Entity child : getOrderedChildren(t)) {
/* 346:331 */         s = s + " alignl " + toOpenOfficeFormula(child) + " # ";
/* 347:    */       }
/* 348:333 */       s = s + "alignl size 8 \"D \" ";
/* 349:    */     }
/* 350:334 */     else if ((t instanceof Sequence))
/* 351:    */     {
/* 352:335 */       s = s + "color black left [  font fixed stack{   \n";
/* 353:336 */       for (Entity child : getOrderedChildren(t)) {
/* 354:337 */         s = s + " alignl " + toOpenOfficeFormula(child) + " # ";
/* 355:    */       }
/* 356:339 */       s = s + "alignl size 8 \"S \" ";
/* 357:    */     }
/* 358:    */     else
/* 359:    */     {
/* 360:341 */       s = s + "color green left [  font fixed stack{   alignl size 8 \"T \"\n";
/* 361:    */     }
/* 362:343 */     boolean first = true;
/* 363:344 */     for (Thread thread : t.getBundle())
/* 364:    */     {
/* 365:345 */       if (first) {
/* 366:346 */         first = false;
/* 367:    */       } else {
/* 368:348 */         s = s + " # alignl ";
/* 369:    */       }
/* 370:350 */       s = s + "nbold color black { ";
/* 371:351 */       Iterator<String> si = thread.iterator();
/* 372:352 */       int counter = thread.size();
/* 373:353 */       while (si.hasNext())
/* 374:    */       {
/* 375:354 */         String label = (String)si.next();
/* 376:355 */         if ((counter == 5) && (counter != thread.size()) && (si.hasNext()))
/* 377:    */         {
/* 378:356 */           s = s + "... rightarrow ";
/* 379:357 */           counter--;
/* 380:    */         }
/* 381:360 */         else if ((counter > 5) && (counter != thread.size()) && (si.hasNext()))
/* 382:    */         {
/* 383:361 */           counter--;
/* 384:    */         }
/* 385:364 */         else if (!label.equals("action"))
/* 386:    */         {
/* 387:366 */           if (!label.equalsIgnoreCase("thing"))
/* 388:    */           {
/* 389:369 */             if (label.equalsIgnoreCase("from")) {
/* 390:370 */               s = s + " italic \"FROM\"";
/* 391:372 */             } else if (label.equalsIgnoreCase("to")) {
/* 392:373 */               s = s + " italic \"TO\"";
/* 393:375 */             } else if (label.equalsIgnoreCase("toward")) {
/* 394:376 */               s = s + " italic \"TOWARD\"";
/* 395:378 */             } else if (label.equalsIgnoreCase("size")) {
/* 396:379 */               s = s + " italic \"SIZE\"";
/* 397:381 */             } else if (label.equalsIgnoreCase("green")) {
/* 398:382 */               s = s + " italic \"GREEN\"";
/* 399:    */             } else {
/* 400:385 */               s = s + label.toUpperCase() + " ";
/* 401:    */             }
/* 402:387 */             if (si.hasNext()) {
/* 403:388 */               s = s + "rightarrow ";
/* 404:    */             }
/* 405:390 */             counter--;
/* 406:    */           }
/* 407:    */         }
/* 408:    */       }
/* 409:392 */       s = s + " }\n";
/* 410:    */     }
/* 411:394 */     s = s + "} right none \n";
/* 412:395 */     return s;
/* 413:    */   }
/* 414:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     magicLess.utils.EntityUtils
 * JD-Core Version:    0.7.0.1
 */