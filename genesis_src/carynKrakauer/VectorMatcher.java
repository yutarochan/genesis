/*   1:    */ package carynKrakauer;
/*   2:    */ 
/*   3:    */ import carynKrakauer.generatedPatterns.ConceptPattern;
/*   4:    */ import carynKrakauer.generatedPatterns.ConceptPatternMatchWrapper;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.List;
/*   8:    */ import utils.Mark;
/*   9:    */ 
/*  10:    */ public class VectorMatcher
/*  11:    */ {
/*  12:    */   private static final float epsilon = 0.0005F;
/*  13:    */   
/*  14:    */   public static VectorMatchWrapper MatchVectors(HashMap<String, Integer> v1, HashMap<String, Integer> v2)
/*  15:    */   {
/*  16: 23 */     double total = 0.0D;
/*  17: 25 */     if ((v1.size() == 0) && (v2.size() == 0)) {
/*  18: 26 */       return new VectorMatchWrapper(1.0D, new ArrayList());
/*  19:    */     }
/*  20: 29 */     double l1 = 0.0D;
/*  21: 30 */     for (String s : v1.keySet()) {
/*  22: 31 */       l1 += Math.pow(((Integer)v1.get(s)).intValue(), 2.0D);
/*  23:    */     }
/*  24: 33 */     l1 = Math.sqrt(l1);
/*  25:    */     
/*  26: 35 */     double l2 = 0.0D;
/*  27: 36 */     for (String s : v2.keySet()) {
/*  28: 37 */       l2 += Math.pow(((Integer)v2.get(s)).intValue(), 2.0D);
/*  29:    */     }
/*  30: 38 */     l2 = Math.sqrt(l2);
/*  31:    */     
/*  32: 40 */     double denominator = l1 * l2;
/*  33: 42 */     if ((int)denominator - (float)denominator < 0.0001D) {
/*  34: 43 */       denominator = (int)(denominator + 0.5D);
/*  35:    */     }
/*  36: 45 */     if (denominator <= 0.0D) {
/*  37: 46 */       return new VectorMatchWrapper(0.0D, new ArrayList());
/*  38:    */     }
/*  39: 48 */     ArrayList<String> matches = new ArrayList();
/*  40:    */     
/*  41: 50 */     ArrayList<String> features = new ArrayList();
/*  42: 51 */     for (String s : v1.keySet()) {
/*  43: 52 */       features.add(s);
/*  44:    */     }
/*  45: 54 */     for (String s : v2.keySet()) {
/*  46: 55 */       if (!features.contains(s)) {
/*  47: 56 */         features.add(s);
/*  48:    */       }
/*  49:    */     }
/*  50: 60 */     for (String s : features)
/*  51:    */     {
/*  52: 61 */       double result = 0.0D;
/*  53:    */       int val1;
/*  54:    */       int val1;
/*  55: 63 */       if (v1.containsKey(s)) {
/*  56: 64 */         val1 = ((Integer)v1.get(s)).intValue();
/*  57:    */       } else {
/*  58: 66 */         val1 = 0;
/*  59:    */       }
/*  60:    */       int val2;
/*  61:    */       int val2;
/*  62: 69 */       if (v2.get(s) != null) {
/*  63: 70 */         val2 = ((Integer)v2.get(s)).intValue();
/*  64:    */       } else {
/*  65: 72 */         val2 = 0;
/*  66:    */       }
/*  67: 74 */       if (denominator > 0.0D)
/*  68:    */       {
/*  69: 75 */         for (int i = 0; i < Math.min(val1, val2); i++) {
/*  70: 76 */           matches.add(s);
/*  71:    */         }
/*  72: 78 */         result = val1 * val2 / denominator;
/*  73:    */       }
/*  74: 80 */       total += result;
/*  75:    */     }
/*  76: 83 */     return new VectorMatchWrapper(total, matches);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static HashMap<String, VectorMatchWrapper> highest_matches(String title, boolean check_all, ReflectionLevelMemory memory)
/*  80:    */   {
/*  81: 88 */     HashMap<String, VectorMatchWrapper> max_matches = new HashMap();
/*  82: 89 */     double max_value = -1.0D;
/*  83:    */     
/*  84: 91 */     HashMap<String, VectorMatchWrapper> matches = match_vectors(title, memory);
/*  85: 93 */     if (check_all) {
/*  86: 94 */       return matches;
/*  87:    */     }
/*  88: 96 */     for (String story : matches.keySet()) {
/*  89: 98 */       if (!story.equals(title))
/*  90:    */       {
/*  91:101 */         VectorMatchWrapper match = (VectorMatchWrapper)matches.get(story);
/*  92:103 */         if (match.getValue() >= max_value)
/*  93:    */         {
/*  94:104 */           if (match.getValue() > max_value + 0.0005000000237487257D)
/*  95:    */           {
/*  96:105 */             max_matches = new HashMap();
/*  97:106 */             max_value = match.getValue();
/*  98:    */           }
/*  99:108 */           max_matches.put(story, (VectorMatchWrapper)matches.get(story));
/* 100:    */         }
/* 101:    */       }
/* 102:    */     }
/* 103:112 */     if (max_value == -1.0D)
/* 104:    */     {
/* 105:113 */       Mark.say(new Object[] {"There is no highest match." });
/* 106:114 */       return new HashMap();
/* 107:    */     }
/* 108:117 */     return max_matches;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static HashMap<String, Integer> getStoryReflectionStringCounts(ArrayList<String> storyFeatures)
/* 112:    */   {
/* 113:126 */     HashMap<String, Integer> accumulator = new HashMap();
/* 114:127 */     for (String feature : storyFeatures) {
/* 115:128 */       accumulator.put(feature, Integer.valueOf(accumulator.get(feature) == null ? 1 : 1 + ((Integer)accumulator.get(feature)).intValue()));
/* 116:    */     }
/* 117:130 */     return accumulator;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static VectorMatchWrapper match_story_vectors(String story1, String story2, ReflectionLevelMemory memory)
/* 121:    */   {
/* 122:134 */     HashMap<String, Integer> story1Hash = memory.getStoryReflectionStringCounts(story1);
/* 123:135 */     HashMap<String, Integer> story2Hash = memory.getStoryReflectionStringCounts(story2);
/* 124:136 */     return MatchVectors(story1Hash, story2Hash);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public static HashMap<String, VectorMatchWrapper> match_vectors(String title, ReflectionLevelMemory memory)
/* 128:    */   {
/* 129:140 */     HashMap<String, VectorMatchWrapper> matches = new HashMap();
/* 130:143 */     for (String story : memory.getStoryNames()) {
/* 131:145 */       if (!title.equals(story))
/* 132:    */       {
/* 133:149 */         HashMap<String, Integer> story1Hash = null;
/* 134:150 */         HashMap<String, Integer> story2Hash = null;
/* 135:151 */         story1Hash = memory.getStoryReflectionStringCounts(title);
/* 136:152 */         story2Hash = memory.getStoryReflectionStringCounts(story);
/* 137:    */         
/* 138:154 */         VectorMatchWrapper match = MatchVectors(story1Hash, story2Hash);
/* 139:155 */         matches.put(story, match);
/* 140:    */       }
/* 141:    */     }
/* 142:159 */     return matches;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public static HashMap<String, ConceptPatternMatchWrapper> plotUnitMatches(String title, ReflectionLevelMemory memory, List<String> toMatch, int size)
/* 146:    */   {
/* 147:169 */     HashMap<String, ConceptPatternMatchWrapper> matches = matchPlotUnits(title, memory, toMatch, size);
/* 148:170 */     return matches;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public static HashMap<String, ConceptPatternMatchWrapper> highestMatchesPlotUnits(String title, boolean check_all, ReflectionLevelMemory memory, int size)
/* 152:    */   {
/* 153:181 */     HashMap<String, ConceptPatternMatchWrapper> max_matches = new HashMap();
/* 154:182 */     double max_value = -1.0D;
/* 155:    */     
/* 156:184 */     HashMap<String, ConceptPatternMatchWrapper> matches = matchPlotUnits(title, memory, size);
/* 157:186 */     if (check_all) {
/* 158:187 */       return matches;
/* 159:    */     }
/* 160:189 */     for (String story : matches.keySet()) {
/* 161:191 */       if (!story.equals(title))
/* 162:    */       {
/* 163:194 */         ConceptPatternMatchWrapper match = (ConceptPatternMatchWrapper)matches.get(story);
/* 164:196 */         if (match.getValue() >= max_value)
/* 165:    */         {
/* 166:197 */           if (match.getValue() > max_value)
/* 167:    */           {
/* 168:198 */             max_matches = new HashMap();
/* 169:199 */             max_value = match.getValue();
/* 170:    */           }
/* 171:201 */           max_matches.put(story, (ConceptPatternMatchWrapper)matches.get(story));
/* 172:    */         }
/* 173:    */       }
/* 174:    */     }
/* 175:205 */     if (max_value == -1.0D)
/* 176:    */     {
/* 177:206 */       Mark.say(new Object[] {"There is no highest match." });
/* 178:207 */       return new HashMap();
/* 179:    */     }
/* 180:210 */     return max_matches;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public static HashMap<String, ConceptPatternMatchWrapper> matchPlotUnits(String title, ReflectionLevelMemory memory, List<String> toMatch, int size)
/* 184:    */   {
/* 185:215 */     HashMap<String, ConceptPatternMatchWrapper> matches = new HashMap();
/* 186:    */     
/* 187:217 */     HashMap<ConceptPattern, Integer> story1Hash = memory.getStoryPlotUnitCounts(title, size);
/* 188:218 */     Mark.say(new Object[] {story1Hash + " " + title });
/* 189:219 */     HashMap<ConceptPattern, Integer> story1HashNonRare = (HashMap)story1Hash.clone();
/* 190:220 */     for (ConceptPattern unit : story1HashNonRare.keySet())
/* 191:    */     {
/* 192:221 */       int count = memory.getGeneratedConceptPatternStoryCounts(unit, size);
/* 193:222 */       if (count > 1) {
/* 194:223 */         story1HashNonRare.put(unit, (Integer)story1Hash.get(unit));
/* 195:    */       }
/* 196:    */     }
/* 197:231 */     for (String story : toMatch) {
/* 198:232 */       if (!story.equals(title))
/* 199:    */       {
/* 200:235 */         HashMap<ConceptPattern, Integer> story2Hash = memory.getStoryPlotUnitCounts(story, size);
/* 201:236 */         HashMap<ConceptPattern, Integer> story2HashNonRare = (HashMap)story2Hash.clone();
/* 202:237 */         for (ConceptPattern unit : story2HashNonRare.keySet())
/* 203:    */         {
/* 204:238 */           int count = memory.getPlotUnitCount(unit, size);
/* 205:239 */           if (count > 2) {
/* 206:240 */             story2HashNonRare.put(unit, (Integer)story2Hash.get(unit));
/* 207:    */           }
/* 208:    */         }
/* 209:245 */         ConceptPatternMatchWrapper match = MatchPlotUnits(story1HashNonRare, story2HashNonRare, memory.getPlotUnits(title, size), memory.getPlotUnits(story, size));
/* 210:246 */         matches.put(story, match);
/* 211:    */       }
/* 212:    */     }
/* 213:250 */     return matches;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public static HashMap<String, ConceptPatternMatchWrapper> matchPlotUnits(String title, ReflectionLevelMemory memory, int size)
/* 217:    */   {
/* 218:261 */     return matchPlotUnits(title, memory, memory.getStoryNames(), size);
/* 219:    */   }
/* 220:    */   
/* 221:    */   private static ConceptPatternMatchWrapper MatchPlotUnits(HashMap<ConceptPattern, Integer> v1, HashMap<ConceptPattern, Integer> v2, ArrayList<ConceptPattern> story1PlotUnits, ArrayList<ConceptPattern> story2PlotUnits)
/* 222:    */   {
/* 223:270 */     double total = 0.0D;
/* 224:272 */     if ((v1.size() == 0) && (v2.size() == 0)) {
/* 225:273 */       return new ConceptPatternMatchWrapper(1.0D, new ArrayList(), story1PlotUnits, story2PlotUnits);
/* 226:    */     }
/* 227:275 */     double l1 = 0.0D;
/* 228:276 */     for (ConceptPattern s : v1.keySet()) {
/* 229:277 */       l1 += Math.pow(((Integer)v1.get(s)).intValue(), 2.0D);
/* 230:    */     }
/* 231:279 */     l1 = Math.sqrt(l1);
/* 232:    */     
/* 233:281 */     double l2 = 0.0D;
/* 234:282 */     for (ConceptPattern s : v2.keySet()) {
/* 235:283 */       l2 += Math.pow(((Integer)v2.get(s)).intValue(), 2.0D);
/* 236:    */     }
/* 237:284 */     l2 = Math.sqrt(l2);
/* 238:    */     
/* 239:286 */     double denominator = l1 * l2;
/* 240:288 */     if ((int)denominator - (float)denominator < 0.0001D) {
/* 241:289 */       denominator = (int)(denominator + 0.5D);
/* 242:    */     }
/* 243:291 */     if (denominator <= 0.0D) {
/* 244:292 */       return new ConceptPatternMatchWrapper(0.0D, new ArrayList(), story1PlotUnits, story2PlotUnits);
/* 245:    */     }
/* 246:295 */     ArrayList<ConceptPattern> features = new ArrayList();
/* 247:296 */     for (ConceptPattern s : v1.keySet())
/* 248:    */     {
/* 249:297 */       boolean found = false;
/* 250:298 */       for (ConceptPattern plotUnit : features) {
/* 251:299 */         if (plotUnit.canAlign(s, s.getSize()))
/* 252:    */         {
/* 253:300 */           found = true;
/* 254:301 */           break;
/* 255:    */         }
/* 256:    */       }
/* 257:304 */       if (!found) {
/* 258:305 */         features.add(s);
/* 259:    */       }
/* 260:    */     }
/* 261:    */     boolean found;
/* 262:309 */     for (ConceptPattern s : v2.keySet())
/* 263:    */     {
/* 264:310 */       found = false;
/* 265:311 */       for (ConceptPattern plotUnit : features) {
/* 266:312 */         if (plotUnit.canAlign(s, s.getSize()))
/* 267:    */         {
/* 268:313 */           found = true;
/* 269:314 */           break;
/* 270:    */         }
/* 271:    */       }
/* 272:317 */       if (!found) {
/* 273:318 */         features.add(s);
/* 274:    */       }
/* 275:    */     }
/* 276:322 */     ArrayList<ConceptPattern> matches = new ArrayList();
/* 277:323 */     for (ConceptPattern s : features)
/* 278:    */     {
/* 279:324 */       double result = 0.0D;
/* 280:    */       
/* 281:326 */       boolean match = false;
/* 282:327 */       ConceptPattern matchingPlotUnit = null;
/* 283:328 */       for (ConceptPattern plotUnit : v1.keySet()) {
/* 284:329 */         if (plotUnit.canAlign(s, plotUnit.getSize()))
/* 285:    */         {
/* 286:330 */           match = true;
/* 287:331 */           matchingPlotUnit = plotUnit;
/* 288:332 */           break;
/* 289:    */         }
/* 290:    */       }
/* 291:    */       int val1;
/* 292:    */       int val1;
/* 293:335 */       if (match) {
/* 294:336 */         val1 = ((Integer)v1.get(matchingPlotUnit)).intValue();
/* 295:    */       } else {
/* 296:338 */         val1 = 0;
/* 297:    */       }
/* 298:340 */       boolean match2 = false;
/* 299:    */       
/* 300:342 */       matchingPlotUnit = null;
/* 301:343 */       for (ConceptPattern plotUnit : v2.keySet()) {
/* 302:344 */         if (plotUnit.canAlign(s, s.getSize()))
/* 303:    */         {
/* 304:345 */           match2 = true;
/* 305:346 */           matchingPlotUnit = plotUnit;
/* 306:347 */           break;
/* 307:    */         }
/* 308:    */       }
/* 309:    */       int val2;
/* 310:    */       int val2;
/* 311:350 */       if (match2) {
/* 312:351 */         val2 = ((Integer)v2.get(matchingPlotUnit)).intValue();
/* 313:    */       } else {
/* 314:353 */         val2 = 0;
/* 315:    */       }
/* 316:355 */       if (denominator > 0.0D)
/* 317:    */       {
/* 318:356 */         for (int i = 0; i < Math.min(val1, val2); i++) {
/* 319:357 */           matches.add(s);
/* 320:    */         }
/* 321:360 */         result = val1 * val2 / denominator;
/* 322:    */       }
/* 323:362 */       total += result;
/* 324:    */     }
/* 325:365 */     return new ConceptPatternMatchWrapper(total, matches, story1PlotUnits, story2PlotUnits);
/* 326:    */   }
/* 327:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.VectorMatcher
 * JD-Core Version:    0.7.0.1
 */