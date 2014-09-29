/*   1:    */ package matthewFay;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Sequence;
/*   5:    */ import bridge.reps.entities.Thread;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Set;
/*  10:    */ import java.util.Vector;
/*  11:    */ import javax.swing.JCheckBox;
/*  12:    */ import matchers.ThreadMatcher;
/*  13:    */ import matchers.representations.ThreadMatchResult;
/*  14:    */ import matthewFay.Utilities.HashMatrix;
/*  15:    */ import matthewFay.viewers.AlignmentViewer;
/*  16:    */ import minilisp.LList;
/*  17:    */ import start.Generator;
/*  18:    */ import translator.Translator;
/*  19:    */ import utils.Mark;
/*  20:    */ import utils.PairOfEntities;
/*  21:    */ 
/*  22:    */ public class ScoreMatcher
/*  23:    */ {
/*  24: 35 */   boolean fast_matching = true;
/*  25:    */   
/*  26:    */   public ScoreMatcher()
/*  27:    */   {
/*  28: 38 */     this.fast_matching = AlignmentViewer.simpleScorer.isSelected();
/*  29: 39 */     threadMatcher.two_way_match = true;
/*  30:    */   }
/*  31:    */   
/*  32: 42 */   private boolean use_thread_matcher = true;
/*  33: 43 */   private static ThreadMatcher threadMatcher = new ThreadMatcher();
/*  34: 45 */   public static boolean useBindingHashes = false;
/*  35: 47 */   public static HashMatrix<String, String, Boolean> bindingHashMatrix = new HashMatrix();
/*  36: 48 */   public static HashMap<String, HashMap<String, Float>> typeScoreHashMap = new HashMap();
/*  37:    */   
/*  38:    */   public float scoreMatch(Entity thing1, Entity thing2, LList<PairOfEntities> bindings)
/*  39:    */   {
/*  40: 52 */     if ((thing1.functionP()) && (thing2.functionP()))
/*  41:    */     {
/*  42: 54 */       Entity subject1 = thing1.getSubject();
/*  43: 55 */       Entity subject2 = thing2.getSubject();
/*  44:    */       
/*  45: 57 */       float subjectMatch = scoreMatch(subject1, subject2, bindings);
/*  46:    */       
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50: 62 */       String type1 = thing1.getType();
/*  51: 63 */       String type2 = thing2.getType();
/*  52: 64 */       float derivativeMatch = type1.equals(type2) ? 1 : 0;
/*  53:    */       
/*  54:    */ 
/*  55: 67 */       return subjectMatch * derivativeMatch;
/*  56:    */     }
/*  57: 69 */     if ((thing1.featureP()) && (thing2.featureP()))
/*  58:    */     {
/*  59: 71 */       Mark.say(new Object[] {"Features not handled yet, assuming non-match!" });
/*  60: 72 */       return 0.0F;
/*  61:    */     }
/*  62: 73 */     if ((thing1.relationP()) && (thing2.relationP()))
/*  63:    */     {
/*  64: 75 */       Entity subject1 = thing1.getSubject();
/*  65: 76 */       Entity subject2 = thing2.getSubject();
/*  66: 77 */       float subjectMatch = scoreMatch(subject1, subject2, bindings);
/*  67:    */       
/*  68: 79 */       Entity object1 = thing1.getObject();
/*  69: 80 */       Entity object2 = thing2.getObject();
/*  70: 81 */       float objectMatch = scoreMatch(object1, object2, bindings);
/*  71:    */       
/*  72:    */ 
/*  73:    */ 
/*  74: 85 */       String type1 = thing1.getType();
/*  75: 86 */       String type2 = thing2.getType();
/*  76: 87 */       float relationMatch = type1.equals(type2) ? 1 : 0;
/*  77: 88 */       if ((relationMatch == 0.0F) && (
/*  78: 89 */         (thing1.getPrimedThread().containsAll(thing2.getPrimedThread())) || 
/*  79: 90 */         (thing2.getPrimedThread().containsAll(thing1.getPrimedThread())))) {
/*  80: 91 */         relationMatch = 1.0F;
/*  81:    */       }
/*  82: 95 */       return subjectMatch * objectMatch * relationMatch;
/*  83:    */     }
/*  84:    */     float childrenScoreProduct;
/*  85: 96 */     if ((thing1.sequenceP()) && (thing2.sequenceP()))
/*  86:    */     {
/*  87: 98 */       int childrenCount1 = thing1.getNumberOfChildren();
/*  88: 99 */       int childrenCount2 = thing2.getNumberOfChildren();
/*  89:    */       
/*  90:    */ 
/*  91:102 */       int min = childrenCount1 < childrenCount2 ? childrenCount1 : childrenCount2;
/*  92:103 */       int max = childrenCount1 < childrenCount2 ? childrenCount2 : childrenCount1;
/*  93:    */       
/*  94:105 */       childrenScoreProduct = 1.0F;
/*  95:106 */       for (int i = 0; i < min; i++)
/*  96:    */       {
/*  97:107 */         Entity child1 = thing1.getElement(i);
/*  98:108 */         Entity child2 = thing2.getElement(i);
/*  99:109 */         float score = scoreMatch(child1, child2, bindings);
/* 100:110 */         childrenScoreProduct *= score;
/* 101:    */       }
/* 102:114 */       String type1 = thing1.getType();
/* 103:115 */       String type2 = thing2.getType();
/* 104:116 */       float sequenceMatch = type1.equals(type2) ? 1 : 0;
/* 105:    */       
/* 106:118 */       return childrenScoreProduct * sequenceMatch;
/* 107:    */     }
/* 108:119 */     if ((thing1.entityP()) && (thing2.entityP()))
/* 109:    */     {
/* 110:120 */       String suffix1 = thing1.getNameSuffix();
/* 111:121 */       String suffix2 = thing2.getNameSuffix();
/* 112:122 */       if (useBindingHashes)
/* 113:    */       {
/* 114:123 */         if ((!bindingHashMatrix.contains(suffix1, suffix2)) && (
/* 115:124 */           (bindingHashMatrix.keySetRows().contains(suffix1)) || (bindingHashMatrix.keySetCols().contains(suffix2)))) {
/* 116:125 */           return 0.0F;
/* 117:    */         }
/* 118:    */       }
/* 119:    */       else
/* 120:    */       {
/* 121:128 */         boolean bad_found = false;
/* 122:129 */         for (PairOfEntities binding : bindings)
/* 123:    */         {
/* 124:130 */           if (binding.getPattern().getNameSuffix().equals(thing1.getNameSuffix()))
/* 125:    */           {
/* 126:131 */             if (!binding.getDatum().getNameSuffix().equals(thing2.getNameSuffix()))
/* 127:    */             {
/* 128:132 */               bad_found = true; break;
/* 129:    */             }
/* 130:134 */             if (this.fast_matching) {
/* 131:135 */               return 1.0F;
/* 132:    */             }
/* 133:137 */             bad_found = false;
/* 134:138 */             break;
/* 135:    */           }
/* 136:140 */           if (binding.getDatum().getNameSuffix().equals(thing2.getNameSuffix())) {
/* 137:141 */             bad_found = true;
/* 138:    */           }
/* 139:    */         }
/* 140:144 */         if (bad_found) {
/* 141:145 */           return 0.0F;
/* 142:    */         }
/* 143:    */       }
/* 144:147 */       if (this.use_thread_matcher)
/* 145:    */       {
/* 146:148 */         if (threadMatcher.match(thing1, thing2).match) {
/* 147:149 */           return 1.0F;
/* 148:    */         }
/* 149:151 */         return 0.0F;
/* 150:    */       }
/* 151:154 */       if (this.fast_matching) {
/* 152:155 */         return 1.0F;
/* 153:    */       }
/* 154:157 */       float typeScore = 0.0F;
/* 155:158 */       if ((typeScoreHashMap.containsKey(suffix1)) && 
/* 156:159 */         (((HashMap)typeScoreHashMap.get(suffix1)).containsKey(suffix2)))
/* 157:    */       {
/* 158:160 */         typeScore = ((Float)((HashMap)typeScoreHashMap.get(suffix1)).get(suffix2)).floatValue();
/* 159:161 */         return typeScore;
/* 160:    */       }
/* 161:166 */       Vector<String> types1 = thing1.getTypes();
/* 162:167 */       Vector<String> types2 = thing2.getTypes();
/* 163:168 */       int length1 = types1.size();
/* 164:169 */       int length2 = types2.size();
/* 165:170 */       int length = Math.min(length1, length2);
/* 166:171 */       int matches = 0;
/* 167:    */       
/* 168:    */ 
/* 169:    */ 
/* 170:175 */       int type_iterator = 0;
/* 171:176 */       while (type_iterator < length)
/* 172:    */       {
/* 173:177 */         String type1 = (String)types1.get(type_iterator);
/* 174:178 */         String type2 = (String)types2.get(type_iterator);
/* 175:179 */         if ((type1.equals("name")) && (type2.equals("name"))) {
/* 176:180 */           matches++;
/* 177:    */         }
/* 178:182 */         if (!type1.equals(type2)) {
/* 179:    */           break;
/* 180:    */         }
/* 181:183 */         matches++;
/* 182:    */         
/* 183:    */ 
/* 184:    */ 
/* 185:187 */         type_iterator++;
/* 186:    */       }
/* 187:190 */       if (typeScore == 0.0F) {
/* 188:191 */         if (matches >= Math.max(length1, length2)) {
/* 189:192 */           typeScore = 1.0F;
/* 190:    */         } else {
/* 191:195 */           typeScore = matches / Math.max(length1, length2);
/* 192:    */         }
/* 193:    */       }
/* 194:198 */       if (typeScoreHashMap.containsKey(suffix1))
/* 195:    */       {
/* 196:199 */         ((HashMap)typeScoreHashMap.get(suffix1)).put(suffix2, Float.valueOf(typeScore));
/* 197:    */       }
/* 198:    */       else
/* 199:    */       {
/* 200:201 */         typeScoreHashMap.put(suffix1, new HashMap());
/* 201:202 */         ((HashMap)typeScoreHashMap.get(suffix1)).put(suffix2, Float.valueOf(typeScore));
/* 202:    */       }
/* 203:204 */       return typeScore;
/* 204:    */     }
/* 205:206 */     if ((!thing1.entityP()) && (!thing1.sequenceP()) && (!thing1.functionP()) && (!thing1.featureP()) && (!thing1.relationP()))
/* 206:    */     {
/* 207:207 */       Mark.say(new Object[] {"Non match, or non-detected Thing Type" });
/* 208:208 */       Mark.say(new Object[] {"Thing1: ", thing1.asString() });
/* 209:    */     }
/* 210:210 */     if ((!thing2.entityP()) && (!thing2.sequenceP()) && (!thing2.functionP()) && (!thing2.featureP()) && (!thing2.relationP()))
/* 211:    */     {
/* 212:211 */       Mark.say(new Object[] {"Non match, or non-detected Thing Type" });
/* 213:212 */       Mark.say(new Object[] {"Thing2: ", thing2.asString() });
/* 214:    */     }
/* 215:214 */     return 0.0F;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public static void main(String[] args)
/* 219:    */   {
/* 220:    */     try
/* 221:    */     {
/* 222:220 */       Generator generator = Generator.getGenerator();
/* 223:221 */       Translator translator = Translator.getTranslator();
/* 224:    */       
/* 225:223 */       generator.setStoryMode();
/* 226:    */       
/* 227:225 */       Entity s2_A = translator.translate("Matt is a person").getElement(0);
/* 228:226 */       Entity s2_B = translator.translate("Mary is a person").getElement(0);
/* 229:    */       
/* 230:228 */       Entity s2_D = translator.translate("Matt owns the bowl").getElement(0);
/* 231:229 */       Entity s2_E = translator.translate("Matt gives the bowl to Mary").getElement(0);
/* 232:230 */       Entity s2_F = translator.translate("Mary owns the bowl").getElement(0);
/* 233:231 */       Entity story_s2 = new Sequence();
/* 234:232 */       story_s2.addElement(s2_A);
/* 235:233 */       story_s2.addElement(s2_B);
/* 236:    */       
/* 237:235 */       story_s2.addElement(s2_D);
/* 238:236 */       story_s2.addElement(s2_E);
/* 239:237 */       story_s2.addElement(s2_F);
/* 240:    */       
/* 241:239 */       generator.flush();
/* 242:    */       
/* 243:241 */       Entity take_A = translator.translate("Mark is a person").getElement(0);
/* 244:242 */       Entity take_B = translator.translate("Sally is a person").getElement(0);
/* 245:    */       
/* 246:244 */       Entity take_D = translator.translate("Mark owns the cup").getElement(0);
/* 247:245 */       Entity take_E = translator.translate("Sally takes the cup from Mark").getElement(0);
/* 248:246 */       Entity take_F = translator.translate("Sally owns the cup").getElement(0);
/* 249:247 */       Entity story_take = new Sequence();
/* 250:248 */       story_take.addElement(take_A);
/* 251:249 */       story_take.addElement(take_B);
/* 252:    */       
/* 253:251 */       story_take.addElement(take_D);
/* 254:252 */       story_take.addElement(take_E);
/* 255:253 */       story_take.addElement(take_F);
/* 256:    */       
/* 257:255 */       generator.flush();
/* 258:    */       
/* 259:257 */       Entity matt = s2_D.getSubject();
/* 260:258 */       Entity mark = take_D.getSubject();
/* 261:    */       
/* 262:260 */       Entity cup = take_D.getObject().getElement(0);
/* 263:    */       
/* 264:262 */       Mark.say(new Object[] {take_E.asString() });
/* 265:    */       
/* 266:    */ 
/* 267:265 */       ScoreMatcher matcher = new ScoreMatcher();
/* 268:    */       
/* 269:    */ 
/* 270:268 */       Entity cat = translator.translate("Mark owns the cat").getElement(0).getObject().getElement(0);
/* 271:269 */       Entity lion = translator.translate("Mark owns the lion").getElement(0).getObject().getElement(0);
/* 272:270 */       Entity dog = translator.translate("Mark owns the dog").getElement(0).getObject().getElement(0);
/* 273:271 */       Entity wolf = translator.translate("Mark owns the wolf").getElement(0).getObject().getElement(0);
/* 274:272 */       Entity person = translator.translate("Mark owns the person").getElement(0).getObject().getElement(0);
/* 275:273 */       Entity bear = translator.translate("Mark owns the bear").getElement(0).getObject().getElement(0);
/* 276:274 */       Entity airplane = translator.translate("Mark owns the airplane").getElement(0).getObject().getElement(0);
/* 277:275 */       Entity car = translator.translate("Mark owns the car").getElement(0).getObject().getElement(0);
/* 278:276 */       Entity bus = translator.translate("Mark owns the bus").getElement(0).getObject().getElement(0);
/* 279:277 */       Entity truck = translator.translate("Mark owns the truck").getElement(0).getObject().getElement(0);
/* 280:    */       
/* 281:279 */       List<Entity> things = new ArrayList();
/* 282:280 */       things.add(cat);
/* 283:281 */       things.add(lion);
/* 284:282 */       things.add(dog);
/* 285:283 */       things.add(wolf);
/* 286:284 */       things.add(person);
/* 287:285 */       things.add(bear);
/* 288:286 */       things.add(airplane);
/* 289:287 */       things.add(car);
/* 290:288 */       things.add(bus);
/* 291:289 */       things.add(truck);
/* 292:    */       
/* 293:291 */       Mark.say(new Object[] {cat.asString() + " and " + lion.asString() + " , Match Score: ", Float.valueOf(matcher.scoreMatch(cat, lion, null)) });
/* 294:292 */       Mark.say(new Object[] {cat.asString() + " and " + dog.asString() + " , Match Score: ", Float.valueOf(matcher.scoreMatch(cat, dog, null)) });
/* 295:293 */       Mark.say(new Object[] {dog.asString() + " and " + wolf.asString() + " , Match Score: ", Float.valueOf(matcher.scoreMatch(dog, wolf, null)) });
/* 296:    */       
/* 297:295 */       float[][] score = new float[things.size()][things.size()];
/* 298:    */       
/* 299:297 */       int i = 0;
/* 300:298 */       int j = 0;
/* 301:299 */       for (i = 0; i < things.size(); i++) {
/* 302:300 */         for (j = 0; j < things.size(); j++) {
/* 303:301 */           score[i][j] = matcher.scoreMatch((Entity)things.get(i), (Entity)things.get(j), null);
/* 304:    */         }
/* 305:    */       }
/* 306:305 */       for (i = 0; i < things.size(); i++)
/* 307:    */       {
/* 308:306 */         Entity t1 = (Entity)things.get(i);
/* 309:307 */         Mark.say(new Object[] {t1.asString() + ":" });
/* 310:308 */         for (j = 0; j < things.size(); j++)
/* 311:    */         {
/* 312:309 */           Entity t2 = (Entity)things.get(j);
/* 313:310 */           Mark.say(new Object[] {"..." + t2.asString() + ": " + score[i][j] });
/* 314:    */         }
/* 315:    */       }
/* 316:314 */       Mark.say(new Object[] {cat.toString() });
/* 317:315 */       Mark.say(new Object[] {lion.toString() });
/* 318:316 */       Mark.say(new Object[] {person.toString() });
/* 319:317 */       Mark.say(new Object[] {car.toString() });
/* 320:    */     }
/* 321:    */     catch (Exception e)
/* 322:    */     {
/* 323:321 */       e.printStackTrace();
/* 324:    */     }
/* 325:    */   }
/* 326:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.ScoreMatcher
 * JD-Core Version:    0.7.0.1
 */