/*   1:    */ package carynKrakauer;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Set;
/*   7:    */ import matthewFay.Utilities.Pair;
/*   8:    */ 
/*   9:    */ public class StudyResults
/*  10:    */ {
/*  11:    */   private static HashMap<String, HashMap<String, Integer>> matches;
/*  12:    */   private static final String AMERICAN_REVOLUTION = "American revolution";
/*  13:    */   private static final String AFGHANISTAN_CIVIL_WAR = "Afghanistan-civil-war";
/*  14:    */   private static final String AMERICAN_CIVIL_WAR = "American civil war";
/*  15:    */   private static final String CAMBODIA_VIETNAM_INVASION = "Cambodia-vietnam invasion";
/*  16:    */   private static final String CHAD_LIBYAN_WAR = "Chad-libyan war";
/*  17:    */   private static final String CHINA_BORDER_WAR_WITH_INDIA = "China border war with india";
/*  18:    */   private static final String CHINA_BORDER_WAR_WITH_USSR = "China border war with ussr";
/*  19:    */   private static final String CHINA_INVASION_OF_TIBET = "China invasion of tibet";
/*  20:    */   private static final String CHINA_WAR_WITH_VIETNAM = "China war with vietnam";
/*  21:    */   private static final String CONGO_CIVIL_CONFLICT = "Congo civil conflict";
/*  22:    */   private static final String CUBA_BAY_OF_PIGS_INVASION = "Cuba bay of pigs invasion";
/*  23:    */   private static final String CZECHOSLOVAKIA_SOVIET_INVASION = "Czechoslovakia-soviet invasion";
/*  24:    */   private static final String NIGERIAN_CIVIL_WAR = "Nigerian civil war";
/*  25:    */   private static final String PERSIAN_GULF_WAR = "Persian gulf war";
/*  26:    */   private static final String ROMANIA_AND_CEAUSESCU = "Romania and ceausescu";
/*  27:    */   
/*  28:    */   public static HashMap<String, HashMap<String, Integer>> getStudyResults()
/*  29:    */   {
/*  30: 11 */     if (matches == null) {
/*  31: 12 */       buildMatches();
/*  32:    */     }
/*  33: 14 */     return matches;
/*  34:    */   }
/*  35:    */   
/*  36: 35 */   private static final String[] stories = {
/*  37: 36 */     "American revolution", 
/*  38: 37 */     "Afghanistan-civil-war", 
/*  39: 38 */     "American civil war", 
/*  40: 39 */     "Cambodia-vietnam invasion", 
/*  41: 40 */     "Chad-libyan war", 
/*  42: 41 */     "China border war with india", 
/*  43: 42 */     "China border war with ussr", 
/*  44: 43 */     "China invasion of tibet", 
/*  45: 44 */     "China war with vietnam", 
/*  46: 45 */     "Congo civil conflict", 
/*  47: 46 */     "Cuba bay of pigs invasion", 
/*  48: 47 */     "Czechoslovakia-soviet invasion", 
/*  49: 48 */     "Nigerian civil war", 
/*  50: 49 */     "Persian gulf war", 
/*  51: 50 */     "Romania and ceausescu" };
/*  52:    */   
/*  53:    */   public int totalHumans()
/*  54:    */   {
/*  55: 54 */     return 11;
/*  56:    */   }
/*  57:    */   
/*  58:    */   private static void buildMatches()
/*  59:    */   {
/*  60: 58 */     matches = new HashMap();
/*  61: 60 */     for (String story : stories)
/*  62:    */     {
/*  63: 61 */       HashMap<String, Integer> storyMatches = new HashMap();
/*  64: 62 */       for (String oStory : stories) {
/*  65: 63 */         storyMatches.put(oStory, Integer.valueOf(0));
/*  66:    */       }
/*  67: 65 */       matches.put(story, storyMatches);
/*  68:    */     }
/*  69: 68 */     patrick();
/*  70: 69 */     larry();
/*  71: 70 */     mary();
/*  72: 71 */     chyleigh();
/*  73: 72 */     arthur();
/*  74: 73 */     charles();
/*  75: 74 */     ilya();
/*  76: 75 */     ian();
/*  77: 76 */     calvin();
/*  78: 77 */     aaron();
/*  79: 78 */     kelly();
/*  80:    */   }
/*  81:    */   
/*  82:    */   private static void putMatches(String[] american_revolution, String[] afghanistan_civil_war, String[] american_civil_war, String[] cambodia_vietnam_invasion, String[] chad_libyan_war, String[] china_border_war_with_india, String[] china_border_war_with_ussr, String[] china_invasion_of_tibet, String[] china_war_with_vietnam, String[] congo_civil_conflict, String[] cuba_bay_of_pigs_invasion, String[] czechoslovakia_soviet_invasion, String[] nigerian_civil_war, String[] persian_gulf_war, String[] romania_ceausescu)
/*  83:    */   {
/*  84: 99 */     String[] arrayOfString = american_revolution;int j = american_revolution.length;
/*  85: 99 */     for (int i = 0; i < j; i++)
/*  86:    */     {
/*  87: 99 */       String story = arrayOfString[i];((HashMap)matches.get("American revolution")).put(story, Integer.valueOf(((Integer)((HashMap)matches.get("American revolution")).get(story)).intValue() + 1));
/*  88:    */     }
/*  89:100 */     for (String story : afghanistan_civil_war) {
/*  90:100 */       ((HashMap)matches.get("Afghanistan-civil-war")).put(story, Integer.valueOf(((Integer)((HashMap)matches.get("Afghanistan-civil-war")).get(story)).intValue() + 1));
/*  91:    */     }
/*  92:101 */     for (String story : american_civil_war) {
/*  93:101 */       ((HashMap)matches.get("American civil war")).put(story, Integer.valueOf(((Integer)((HashMap)matches.get("American civil war")).get(story)).intValue() + 1));
/*  94:    */     }
/*  95:102 */     for (String story : cambodia_vietnam_invasion) {
/*  96:102 */       ((HashMap)matches.get("Cambodia-vietnam invasion")).put(story, Integer.valueOf(((Integer)((HashMap)matches.get("Cambodia-vietnam invasion")).get(story)).intValue() + 1));
/*  97:    */     }
/*  98:103 */     for (String story : chad_libyan_war) {
/*  99:103 */       ((HashMap)matches.get("Chad-libyan war")).put(story, Integer.valueOf(((Integer)((HashMap)matches.get("Chad-libyan war")).get(story)).intValue() + 1));
/* 100:    */     }
/* 101:104 */     for (String story : china_border_war_with_india) {
/* 102:104 */       ((HashMap)matches.get("China border war with india")).put(story, Integer.valueOf(((Integer)((HashMap)matches.get("China border war with india")).get(story)).intValue() + 1));
/* 103:    */     }
/* 104:105 */     for (String story : china_border_war_with_ussr) {
/* 105:105 */       ((HashMap)matches.get("China border war with ussr")).put(story, Integer.valueOf(((Integer)((HashMap)matches.get("China border war with ussr")).get(story)).intValue() + 1));
/* 106:    */     }
/* 107:106 */     for (String story : china_invasion_of_tibet) {
/* 108:106 */       ((HashMap)matches.get("China invasion of tibet")).put(story, Integer.valueOf(((Integer)((HashMap)matches.get("China invasion of tibet")).get(story)).intValue() + 1));
/* 109:    */     }
/* 110:107 */     for (String story : china_war_with_vietnam) {
/* 111:107 */       ((HashMap)matches.get("China war with vietnam")).put(story, Integer.valueOf(((Integer)((HashMap)matches.get("China war with vietnam")).get(story)).intValue() + 1));
/* 112:    */     }
/* 113:108 */     for (String story : congo_civil_conflict) {
/* 114:108 */       ((HashMap)matches.get("Congo civil conflict")).put(story, Integer.valueOf(((Integer)((HashMap)matches.get("Congo civil conflict")).get(story)).intValue() + 1));
/* 115:    */     }
/* 116:109 */     for (String story : cuba_bay_of_pigs_invasion) {
/* 117:109 */       ((HashMap)matches.get("Cuba bay of pigs invasion")).put(story, Integer.valueOf(((Integer)((HashMap)matches.get("Cuba bay of pigs invasion")).get(story)).intValue() + 1));
/* 118:    */     }
/* 119:110 */     for (String story : czechoslovakia_soviet_invasion) {
/* 120:110 */       ((HashMap)matches.get("Czechoslovakia-soviet invasion")).put(story, Integer.valueOf(((Integer)((HashMap)matches.get("Czechoslovakia-soviet invasion")).get(story)).intValue() + 1));
/* 121:    */     }
/* 122:111 */     for (String story : nigerian_civil_war) {
/* 123:111 */       ((HashMap)matches.get("Nigerian civil war")).put(story, Integer.valueOf(((Integer)((HashMap)matches.get("Nigerian civil war")).get(story)).intValue() + 1));
/* 124:    */     }
/* 125:112 */     for (String story : persian_gulf_war) {
/* 126:112 */       ((HashMap)matches.get("Persian gulf war")).put(story, Integer.valueOf(((Integer)((HashMap)matches.get("Persian gulf war")).get(story)).intValue() + 1));
/* 127:    */     }
/* 128:113 */     for (String story : romania_ceausescu) {
/* 129:113 */       ((HashMap)matches.get("Romania and ceausescu")).put(story, Integer.valueOf(((Integer)((HashMap)matches.get("Romania and ceausescu")).get(story)).intValue() + 1));
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   private static void blank()
/* 134:    */   {
/* 135:118 */     String[] american_revolution = new String[0];
/* 136:119 */     String[] afghanistan_civil_war = new String[0];
/* 137:120 */     String[] american_civil_war = new String[0];
/* 138:121 */     String[] cambodia_vietnam_invasion = new String[0];
/* 139:122 */     String[] chad_libyan_war = new String[0];
/* 140:123 */     String[] china_border_war_with_india = new String[0];
/* 141:124 */     String[] china_border_war_with_ussr = new String[0];
/* 142:125 */     String[] china_invasion_of_tibet = new String[0];
/* 143:126 */     String[] china_war_with_vietnam = new String[0];
/* 144:127 */     String[] congo_civil_conflict = new String[0];
/* 145:128 */     String[] cuba_bay_of_pigs_invasion = new String[0];
/* 146:129 */     String[] czechoslovakia_soviet_invasion = new String[0];
/* 147:130 */     String[] nigerian_civil_war = new String[0];
/* 148:131 */     String[] persian_gulf_war = new String[0];
/* 149:132 */     String[] romania_ceausescu = new String[0];
/* 150:    */     
/* 151:134 */     putMatches(american_revolution, afghanistan_civil_war, american_civil_war, cambodia_vietnam_invasion, 
/* 152:135 */       chad_libyan_war, china_border_war_with_india, china_border_war_with_ussr, 
/* 153:136 */       china_invasion_of_tibet, china_war_with_vietnam, congo_civil_conflict, 
/* 154:137 */       cuba_bay_of_pigs_invasion, czechoslovakia_soviet_invasion, nigerian_civil_war, 
/* 155:138 */       persian_gulf_war, romania_ceausescu);
/* 156:    */   }
/* 157:    */   
/* 158:    */   private static void kelly()
/* 159:    */   {
/* 160:142 */     String[] american_revolution = { "Nigerian civil war" };
/* 161:143 */     String[] afghanistan_civil_war = { "Cuba bay of pigs invasion" };
/* 162:144 */     String[] american_civil_war = { "Nigerian civil war" };
/* 163:145 */     String[] cambodia_vietnam_invasion = new String[0];
/* 164:146 */     String[] chad_libyan_war = { "Romania and ceausescu" };
/* 165:147 */     String[] china_border_war_with_india = { "China invasion of tibet" };
/* 166:148 */     String[] china_border_war_with_ussr = { "Congo civil conflict", "Nigerian civil war" };
/* 167:149 */     String[] china_invasion_of_tibet = { "Nigerian civil war" };
/* 168:150 */     String[] china_war_with_vietnam = { "Cuba bay of pigs invasion" };
/* 169:151 */     String[] congo_civil_conflict = { "Chad-libyan war" };
/* 170:152 */     String[] cuba_bay_of_pigs_invasion = { "Afghanistan-civil-war" };
/* 171:153 */     String[] czechoslovakia_soviet_invasion = { "China border war with india" };
/* 172:154 */     String[] nigerian_civil_war = { "Congo civil conflict" };
/* 173:155 */     String[] persian_gulf_war = { "American civil war" };
/* 174:156 */     String[] romania_ceausescu = { "China border war with ussr" };
/* 175:    */     
/* 176:158 */     putMatches(american_revolution, afghanistan_civil_war, american_civil_war, cambodia_vietnam_invasion, 
/* 177:159 */       chad_libyan_war, china_border_war_with_india, china_border_war_with_ussr, 
/* 178:160 */       china_invasion_of_tibet, china_war_with_vietnam, congo_civil_conflict, 
/* 179:161 */       cuba_bay_of_pigs_invasion, czechoslovakia_soviet_invasion, nigerian_civil_war, 
/* 180:162 */       persian_gulf_war, romania_ceausescu);
/* 181:    */   }
/* 182:    */   
/* 183:    */   private static void aaron()
/* 184:    */   {
/* 185:166 */     String[] american_revolution = { "Persian gulf war" };
/* 186:167 */     String[] afghanistan_civil_war = { "China border war with india" };
/* 187:168 */     String[] american_civil_war = { "Congo civil conflict" };
/* 188:169 */     String[] cambodia_vietnam_invasion = { "China invasion of tibet" };
/* 189:170 */     String[] chad_libyan_war = { "Nigerian civil war" };
/* 190:171 */     String[] china_border_war_with_india = { "Afghanistan-civil-war" };
/* 191:172 */     String[] china_border_war_with_ussr = { "Czechoslovakia-soviet invasion" };
/* 192:173 */     String[] china_invasion_of_tibet = { "Cambodia-vietnam invasion" };
/* 193:174 */     String[] china_war_with_vietnam = { "Cuba bay of pigs invasion" };
/* 194:175 */     String[] congo_civil_conflict = { "American civil war" };
/* 195:176 */     String[] cuba_bay_of_pigs_invasion = { "China war with vietnam" };
/* 196:177 */     String[] czechoslovakia_soviet_invasion = { "China border war with ussr" };
/* 197:178 */     String[] nigerian_civil_war = { "Chad-libyan war" };
/* 198:179 */     String[] persian_gulf_war = { "American revolution" };
/* 199:180 */     String[] romania_ceausescu = { "Afghanistan-civil-war" };
/* 200:    */     
/* 201:182 */     putMatches(american_revolution, afghanistan_civil_war, american_civil_war, cambodia_vietnam_invasion, 
/* 202:183 */       chad_libyan_war, china_border_war_with_india, china_border_war_with_ussr, 
/* 203:184 */       china_invasion_of_tibet, china_war_with_vietnam, congo_civil_conflict, 
/* 204:185 */       cuba_bay_of_pigs_invasion, czechoslovakia_soviet_invasion, nigerian_civil_war, 
/* 205:186 */       persian_gulf_war, romania_ceausescu);
/* 206:    */   }
/* 207:    */   
/* 208:    */   private static void calvin()
/* 209:    */   {
/* 210:190 */     String[] american_revolution = { "Romania and ceausescu" };
/* 211:191 */     String[] afghanistan_civil_war = { "Cuba bay of pigs invasion" };
/* 212:192 */     String[] american_civil_war = { "China border war with ussr" };
/* 213:193 */     String[] cambodia_vietnam_invasion = { "China border war with india" };
/* 214:194 */     String[] chad_libyan_war = { "Nigerian civil war" };
/* 215:195 */     String[] china_border_war_with_india = { "Cambodia-vietnam invasion" };
/* 216:196 */     String[] china_border_war_with_ussr = { "American civil war" };
/* 217:197 */     String[] china_invasion_of_tibet = { "Congo civil conflict" };
/* 218:198 */     String[] china_war_with_vietnam = { "Cuba bay of pigs invasion" };
/* 219:199 */     String[] congo_civil_conflict = { "China invasion of tibet" };
/* 220:200 */     String[] cuba_bay_of_pigs_invasion = { "Afghanistan-civil-war" };
/* 221:201 */     String[] czechoslovakia_soviet_invasion = { "Persian gulf war" };
/* 222:202 */     String[] nigerian_civil_war = { "Chad-libyan war" };
/* 223:203 */     String[] persian_gulf_war = { "Czechoslovakia-soviet invasion" };
/* 224:204 */     String[] romania_ceausescu = { "American revolution" };
/* 225:    */     
/* 226:206 */     putMatches(american_revolution, afghanistan_civil_war, american_civil_war, cambodia_vietnam_invasion, 
/* 227:207 */       chad_libyan_war, china_border_war_with_india, china_border_war_with_ussr, 
/* 228:208 */       china_invasion_of_tibet, china_war_with_vietnam, congo_civil_conflict, 
/* 229:209 */       cuba_bay_of_pigs_invasion, czechoslovakia_soviet_invasion, nigerian_civil_war, 
/* 230:210 */       persian_gulf_war, romania_ceausescu);
/* 231:    */   }
/* 232:    */   
/* 233:    */   private static void ian()
/* 234:    */   {
/* 235:214 */     String[] american_revolution = { "Persian gulf war" };
/* 236:215 */     String[] afghanistan_civil_war = { "China border war with india" };
/* 237:216 */     String[] american_civil_war = { "Nigerian civil war" };
/* 238:217 */     String[] cambodia_vietnam_invasion = { "Cuba bay of pigs invasion" };
/* 239:218 */     String[] chad_libyan_war = { "China invasion of tibet", "China war with vietnam" };
/* 240:219 */     String[] china_border_war_with_india = { "Afghanistan-civil-war" };
/* 241:220 */     String[] china_border_war_with_ussr = { "Congo civil conflict" };
/* 242:221 */     String[] china_invasion_of_tibet = { "Chad-libyan war", "China war with vietnam" };
/* 243:222 */     String[] china_war_with_vietnam = { "Chad-libyan war", "China invasion of tibet" };
/* 244:223 */     String[] congo_civil_conflict = { "China border war with ussr" };
/* 245:224 */     String[] cuba_bay_of_pigs_invasion = { "Cambodia-vietnam invasion" };
/* 246:225 */     String[] czechoslovakia_soviet_invasion = { "Romania and ceausescu" };
/* 247:226 */     String[] nigerian_civil_war = { "American civil war" };
/* 248:227 */     String[] persian_gulf_war = { "American revolution" };
/* 249:228 */     String[] romania_ceausescu = { "Czechoslovakia-soviet invasion" };
/* 250:    */     
/* 251:230 */     putMatches(american_revolution, afghanistan_civil_war, american_civil_war, cambodia_vietnam_invasion, 
/* 252:231 */       chad_libyan_war, china_border_war_with_india, china_border_war_with_ussr, 
/* 253:232 */       china_invasion_of_tibet, china_war_with_vietnam, congo_civil_conflict, 
/* 254:233 */       cuba_bay_of_pigs_invasion, czechoslovakia_soviet_invasion, nigerian_civil_war, 
/* 255:234 */       persian_gulf_war, romania_ceausescu);
/* 256:    */   }
/* 257:    */   
/* 258:    */   private static void ilya()
/* 259:    */   {
/* 260:238 */     String[] american_revolution = { "Persian gulf war", "Romania and ceausescu" };
/* 261:239 */     String[] afghanistan_civil_war = { "Cuba bay of pigs invasion" };
/* 262:240 */     String[] american_civil_war = { "Romania and ceausescu", "Congo civil conflict" };
/* 263:241 */     String[] cambodia_vietnam_invasion = { "American revolution", "Persian gulf war" };
/* 264:242 */     String[] chad_libyan_war = { "Nigerian civil war" };
/* 265:243 */     String[] china_border_war_with_india = { "China invasion of tibet", "China war with vietnam" };
/* 266:244 */     String[] china_border_war_with_ussr = { "Czechoslovakia-soviet invasion" };
/* 267:245 */     String[] china_invasion_of_tibet = { "China border war with india", "China war with vietnam" };
/* 268:246 */     String[] china_war_with_vietnam = { "China border war with india", "China invasion of tibet" };
/* 269:247 */     String[] congo_civil_conflict = { "Romania and ceausescu", "American civil war" };
/* 270:248 */     String[] cuba_bay_of_pigs_invasion = { "Afghanistan-civil-war" };
/* 271:249 */     String[] czechoslovakia_soviet_invasion = { "China border war with ussr" };
/* 272:250 */     String[] nigerian_civil_war = { "Chad-libyan war" };
/* 273:251 */     String[] persian_gulf_war = { "American revolution", "Cambodia-vietnam invasion" };
/* 274:252 */     String[] romania_ceausescu = { "American civil war", "Congo civil conflict" };
/* 275:    */     
/* 276:254 */     putMatches(american_revolution, afghanistan_civil_war, american_civil_war, cambodia_vietnam_invasion, 
/* 277:255 */       chad_libyan_war, china_border_war_with_india, china_border_war_with_ussr, 
/* 278:256 */       china_invasion_of_tibet, china_war_with_vietnam, congo_civil_conflict, 
/* 279:257 */       cuba_bay_of_pigs_invasion, czechoslovakia_soviet_invasion, nigerian_civil_war, 
/* 280:258 */       persian_gulf_war, romania_ceausescu);
/* 281:    */   }
/* 282:    */   
/* 283:    */   private static void charles()
/* 284:    */   {
/* 285:262 */     String[] american_revolution = { "Romania and ceausescu" };
/* 286:263 */     String[] afghanistan_civil_war = { "Chad-libyan war" };
/* 287:264 */     String[] american_civil_war = { "Nigerian civil war" };
/* 288:265 */     String[] cambodia_vietnam_invasion = { "Cuba bay of pigs invasion" };
/* 289:266 */     String[] chad_libyan_war = { "China invasion of tibet" };
/* 290:267 */     String[] china_border_war_with_india = { "China border war with ussr" };
/* 291:268 */     String[] china_border_war_with_ussr = { "China border war with india" };
/* 292:269 */     String[] china_invasion_of_tibet = { "Persian gulf war" };
/* 293:270 */     String[] china_war_with_vietnam = { "Persian gulf war" };
/* 294:271 */     String[] congo_civil_conflict = { "Czechoslovakia-soviet invasion" };
/* 295:272 */     String[] cuba_bay_of_pigs_invasion = { "Cambodia-vietnam invasion" };
/* 296:273 */     String[] czechoslovakia_soviet_invasion = { "Romania and ceausescu" };
/* 297:274 */     String[] nigerian_civil_war = { "American civil war" };
/* 298:275 */     String[] persian_gulf_war = { "China invasion of tibet" };
/* 299:276 */     String[] romania_ceausescu = { "Czechoslovakia-soviet invasion" };
/* 300:    */     
/* 301:278 */     putMatches(american_revolution, afghanistan_civil_war, american_civil_war, cambodia_vietnam_invasion, 
/* 302:279 */       chad_libyan_war, china_border_war_with_india, china_border_war_with_ussr, 
/* 303:280 */       china_invasion_of_tibet, china_war_with_vietnam, congo_civil_conflict, 
/* 304:281 */       cuba_bay_of_pigs_invasion, czechoslovakia_soviet_invasion, nigerian_civil_war, 
/* 305:282 */       persian_gulf_war, romania_ceausescu);
/* 306:    */   }
/* 307:    */   
/* 308:    */   private static void arthur()
/* 309:    */   {
/* 310:286 */     String[] american_revolution = { "Cuba bay of pigs invasion" };
/* 311:287 */     String[] afghanistan_civil_war = { "Chad-libyan war" };
/* 312:288 */     String[] american_civil_war = { "Nigerian civil war" };
/* 313:289 */     String[] cambodia_vietnam_invasion = { "China border war with india" };
/* 314:290 */     String[] chad_libyan_war = { "Afghanistan-civil-war" };
/* 315:291 */     String[] china_border_war_with_india = { "Cambodia-vietnam invasion" };
/* 316:292 */     String[] china_border_war_with_ussr = { "Czechoslovakia-soviet invasion" };
/* 317:293 */     String[] china_invasion_of_tibet = { "Congo civil conflict" };
/* 318:294 */     String[] china_war_with_vietnam = { "Persian gulf war" };
/* 319:295 */     String[] congo_civil_conflict = { "China invasion of tibet" };
/* 320:296 */     String[] cuba_bay_of_pigs_invasion = { "American revolution" };
/* 321:297 */     String[] czechoslovakia_soviet_invasion = { "China border war with ussr" };
/* 322:298 */     String[] nigerian_civil_war = { "American civil war" };
/* 323:299 */     String[] persian_gulf_war = { "China war with vietnam" };
/* 324:300 */     String[] romania_ceausescu = new String[0];
/* 325:    */     
/* 326:302 */     putMatches(american_revolution, afghanistan_civil_war, american_civil_war, cambodia_vietnam_invasion, 
/* 327:303 */       chad_libyan_war, china_border_war_with_india, china_border_war_with_ussr, 
/* 328:304 */       china_invasion_of_tibet, china_war_with_vietnam, congo_civil_conflict, 
/* 329:305 */       cuba_bay_of_pigs_invasion, czechoslovakia_soviet_invasion, nigerian_civil_war, 
/* 330:306 */       persian_gulf_war, romania_ceausescu);
/* 331:    */   }
/* 332:    */   
/* 333:    */   private static void chyleigh()
/* 334:    */   {
/* 335:310 */     String[] american_revolution = { "Czechoslovakia-soviet invasion" };
/* 336:311 */     String[] afghanistan_civil_war = { "China war with vietnam", "China border war with ussr" };
/* 337:312 */     String[] american_civil_war = { "Persian gulf war" };
/* 338:313 */     String[] cambodia_vietnam_invasion = { "Nigerian civil war" };
/* 339:314 */     String[] chad_libyan_war = { "China border war with india" };
/* 340:315 */     String[] china_border_war_with_india = { "Chad-libyan war" };
/* 341:316 */     String[] china_border_war_with_ussr = { "Afghanistan-civil-war", "China war with vietnam" };
/* 342:317 */     String[] china_invasion_of_tibet = { "Congo civil conflict", "Romania and ceausescu" };
/* 343:318 */     String[] china_war_with_vietnam = { "Afghanistan-civil-war", "China border war with ussr" };
/* 344:319 */     String[] congo_civil_conflict = { "China invasion of tibet", "Romania and ceausescu" };
/* 345:320 */     String[] cuba_bay_of_pigs_invasion = new String[0];
/* 346:321 */     String[] czechoslovakia_soviet_invasion = { "American revolution" };
/* 347:322 */     String[] nigerian_civil_war = { "Cambodia-vietnam invasion" };
/* 348:323 */     String[] persian_gulf_war = { "American civil war" };
/* 349:324 */     String[] romania_ceausescu = { "Congo civil conflict", "China invasion of tibet" };
/* 350:    */     
/* 351:326 */     putMatches(american_revolution, afghanistan_civil_war, american_civil_war, cambodia_vietnam_invasion, 
/* 352:327 */       chad_libyan_war, china_border_war_with_india, china_border_war_with_ussr, 
/* 353:328 */       china_invasion_of_tibet, china_war_with_vietnam, congo_civil_conflict, 
/* 354:329 */       cuba_bay_of_pigs_invasion, czechoslovakia_soviet_invasion, nigerian_civil_war, 
/* 355:330 */       persian_gulf_war, romania_ceausescu);
/* 356:    */   }
/* 357:    */   
/* 358:    */   private static void mary()
/* 359:    */   {
/* 360:334 */     String[] american_revolution = { "Cuba bay of pigs invasion" };
/* 361:335 */     String[] afghanistan_civil_war = { "Congo civil conflict" };
/* 362:336 */     String[] american_civil_war = { "Nigerian civil war" };
/* 363:337 */     String[] cambodia_vietnam_invasion = { "China war with vietnam" };
/* 364:338 */     String[] chad_libyan_war = { "China border war with india" };
/* 365:339 */     String[] china_border_war_with_india = { "Chad-libyan war" };
/* 366:340 */     String[] china_border_war_with_ussr = { "Czechoslovakia-soviet invasion" };
/* 367:341 */     String[] china_invasion_of_tibet = { "China war with vietnam" };
/* 368:342 */     String[] china_war_with_vietnam = { "China invasion of tibet" };
/* 369:343 */     String[] congo_civil_conflict = { "Afghanistan-civil-war" };
/* 370:344 */     String[] cuba_bay_of_pigs_invasion = { "American revolution" };
/* 371:345 */     String[] czechoslovakia_soviet_invasion = { "China border war with india" };
/* 372:346 */     String[] nigerian_civil_war = { "American civil war" };
/* 373:347 */     String[] persian_gulf_war = { "Cambodia-vietnam invasion" };
/* 374:348 */     String[] romania_ceausescu = { "Congo civil conflict" };
/* 375:    */     
/* 376:350 */     putMatches(american_revolution, afghanistan_civil_war, american_civil_war, cambodia_vietnam_invasion, 
/* 377:351 */       chad_libyan_war, china_border_war_with_india, china_border_war_with_ussr, 
/* 378:352 */       china_invasion_of_tibet, china_war_with_vietnam, congo_civil_conflict, 
/* 379:353 */       cuba_bay_of_pigs_invasion, czechoslovakia_soviet_invasion, nigerian_civil_war, 
/* 380:354 */       persian_gulf_war, romania_ceausescu);
/* 381:    */   }
/* 382:    */   
/* 383:    */   private static void larry()
/* 384:    */   {
/* 385:358 */     String[] american_revolution = { "Persian gulf war" };
/* 386:359 */     String[] afghanistan_civil_war = { "China border war with india" };
/* 387:360 */     String[] american_civil_war = { "Czechoslovakia-soviet invasion" };
/* 388:361 */     String[] cambodia_vietnam_invasion = { "China war with vietnam" };
/* 389:362 */     String[] chad_libyan_war = { "China border war with india" };
/* 390:363 */     String[] china_border_war_with_india = { "Chad-libyan war" };
/* 391:364 */     String[] china_border_war_with_ussr = { "American revolution" };
/* 392:365 */     String[] china_invasion_of_tibet = { "Cuba bay of pigs invasion" };
/* 393:366 */     String[] china_war_with_vietnam = { "Cambodia-vietnam invasion" };
/* 394:367 */     String[] congo_civil_conflict = { "Czechoslovakia-soviet invasion" };
/* 395:368 */     String[] cuba_bay_of_pigs_invasion = { "China invasion of tibet" };
/* 396:369 */     String[] czechoslovakia_soviet_invasion = { "American civil war" };
/* 397:370 */     String[] nigerian_civil_war = { "American civil war" };
/* 398:371 */     String[] persian_gulf_war = { "American revolution" };
/* 399:372 */     String[] romania_ceausescu = { "American revolution" };
/* 400:    */     
/* 401:374 */     putMatches(american_revolution, afghanistan_civil_war, american_civil_war, cambodia_vietnam_invasion, 
/* 402:375 */       chad_libyan_war, china_border_war_with_india, china_border_war_with_ussr, 
/* 403:376 */       china_invasion_of_tibet, china_war_with_vietnam, congo_civil_conflict, 
/* 404:377 */       cuba_bay_of_pigs_invasion, czechoslovakia_soviet_invasion, nigerian_civil_war, 
/* 405:378 */       persian_gulf_war, romania_ceausescu);
/* 406:    */   }
/* 407:    */   
/* 408:    */   private static void patrick()
/* 409:    */   {
/* 410:382 */     String[] american_revolution = { "Nigerian civil war" };
/* 411:383 */     String[] afghanistan_civil_war = { "Cuba bay of pigs invasion" };
/* 412:384 */     String[] american_civil_war = { "Nigerian civil war" };
/* 413:385 */     String[] cambodia_vietnam_invasion = { "Chad-libyan war" };
/* 414:386 */     String[] chad_libyan_war = { "Cambodia-vietnam invasion" };
/* 415:387 */     String[] china_border_war_with_india = { "China border war with ussr" };
/* 416:388 */     String[] china_border_war_with_ussr = { "China border war with india" };
/* 417:389 */     String[] china_invasion_of_tibet = { "Czechoslovakia-soviet invasion" };
/* 418:390 */     String[] china_war_with_vietnam = { "Persian gulf war" };
/* 419:391 */     String[] congo_civil_conflict = { "Romania and ceausescu" };
/* 420:392 */     String[] cuba_bay_of_pigs_invasion = { "Afghanistan-civil-war" };
/* 421:393 */     String[] czechoslovakia_soviet_invasion = { "China invasion of tibet" };
/* 422:394 */     String[] nigerian_civil_war = { "American revolution", "Afghanistan-civil-war" };
/* 423:395 */     String[] persian_gulf_war = { "China war with vietnam" };
/* 424:396 */     String[] romania_ceausescu = { "Congo civil conflict" };
/* 425:    */     
/* 426:398 */     putMatches(american_revolution, afghanistan_civil_war, american_civil_war, cambodia_vietnam_invasion, 
/* 427:399 */       chad_libyan_war, china_border_war_with_india, china_border_war_with_ussr, 
/* 428:400 */       china_invasion_of_tibet, china_war_with_vietnam, congo_civil_conflict, 
/* 429:401 */       cuba_bay_of_pigs_invasion, czechoslovakia_soviet_invasion, nigerian_civil_war, 
/* 430:402 */       persian_gulf_war, romania_ceausescu);
/* 431:    */   }
/* 432:    */   
/* 433:    */   public static double getMaxStudyResult()
/* 434:    */   {
/* 435:406 */     double max = 0.0D;
/* 436:    */     Iterator localIterator2;
/* 437:407 */     for (Iterator localIterator1 = getStudyResults().keySet().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 438:    */     {
/* 439:407 */       String story = (String)localIterator1.next();
/* 440:408 */       localIterator2 = ((HashMap)getStudyResults().get(story)).keySet().iterator(); continue;String story2 = (String)localIterator2.next();
/* 441:409 */       double value = ((Integer)((HashMap)getStudyResults().get(story)).get(story2)).intValue();
/* 442:410 */       if (value > max) {
/* 443:411 */         max = value;
/* 444:    */       }
/* 445:    */     }
/* 446:415 */     return max;
/* 447:    */   }
/* 448:    */   
/* 449:    */   public static HashMap<String, HashMap<String, Integer>> getRanks()
/* 450:    */   {
/* 451:419 */     HashMap<String, HashMap<String, Integer>> studyResults = getStudyResults();
/* 452:    */     
/* 453:421 */     HashMap<String, ArrayList<Pair<Integer, String>>> ranks = new HashMap();
/* 454:422 */     for (int i = 0; i < stories.length; i++)
/* 455:    */     {
/* 456:423 */       String story1 = stories[i];
/* 457:424 */       story1Ranks = new ArrayList();
/* 458:425 */       ranks.put(story1, story1Ranks);
/* 459:426 */       for (int j = 0; j < stories.length; j++)
/* 460:    */       {
/* 461:427 */         String story2 = stories[j];
/* 462:    */         
/* 463:429 */         Integer val = (Integer)((HashMap)studyResults.get(story1)).get(story2);
/* 464:    */         
/* 465:431 */         boolean found = false;
/* 466:432 */         for (int k = 0; k < story1Ranks.size(); k++) {
/* 467:433 */           if (((Integer)((Pair)story1Ranks.get(k)).a).intValue() < val.intValue())
/* 468:    */           {
/* 469:434 */             story1Ranks.add(k, new Pair(val, story2));
/* 470:435 */             found = true;
/* 471:436 */             break;
/* 472:    */           }
/* 473:    */         }
/* 474:439 */         if (!found) {
/* 475:440 */           story1Ranks.add(new Pair(val, story2));
/* 476:    */         }
/* 477:    */       }
/* 478:    */     }
/* 479:445 */     HashMap<String, HashMap<String, Integer>> outRanks = new HashMap();
/* 480:    */     ArrayList<Pair<Integer, String>> ordering;
/* 481:    */     int i;
/* 482:447 */     for (ArrayList<Pair<Integer, String>> story1Ranks = ranks.keySet().iterator(); story1Ranks.hasNext(); i < ordering.size())
/* 483:    */     {
/* 484:447 */       String story1 = (String)story1Ranks.next();
/* 485:448 */       outRanks.put(story1, new HashMap());
/* 486:449 */       ordering = (ArrayList)ranks.get(story1);
/* 487:450 */       int current_rank = 0;
/* 488:451 */       double current_val = -1.0D;
/* 489:452 */       i = 0; continue;
/* 490:453 */       if (((Integer)((Pair)ordering.get(i)).a).intValue() != current_val)
/* 491:    */       {
/* 492:454 */         current_rank = i;
/* 493:455 */         current_val = ((Integer)((Pair)ordering.get(i)).a).intValue();
/* 494:    */       }
/* 495:457 */       ((HashMap)outRanks.get(story1)).put((String)((Pair)ordering.get(i)).b, Integer.valueOf(current_rank));i++;
/* 496:    */     }
/* 497:461 */     return outRanks;
/* 498:    */   }
/* 499:    */   
/* 500:    */   public static int getRank(String story1, String story2)
/* 501:    */   {
/* 502:465 */     return ((Integer)((HashMap)getRanks().get(story1)).get(story2)).intValue();
/* 503:    */   }
/* 504:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.StudyResults
 * JD-Core Version:    0.7.0.1
 */