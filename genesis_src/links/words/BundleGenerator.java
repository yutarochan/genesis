/*   1:    */ package links.words;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Thread;
/*   5:    */ import java.io.File;
/*   6:    */ import java.io.FileInputStream;
/*   7:    */ import java.io.FileOutputStream;
/*   8:    */ import java.io.ObjectInputStream;
/*   9:    */ import java.io.ObjectOutputStream;
/*  10:    */ import java.io.PrintStream;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.HashMap;
/*  13:    */ import java.util.HashSet;
/*  14:    */ import java.util.Iterator;
/*  15:    */ import utils.Mark;
/*  16:    */ import utils.Timer;
/*  17:    */ 
/*  18:    */ public class BundleGenerator
/*  19:    */ {
/*  20:    */   private static Implementation instance;
/*  21: 27 */   private static Class clazz = Implementation.class;
/*  22:    */   
/*  23:    */   public static void setSingletonClass(Class c)
/*  24:    */   {
/*  25: 31 */     instance = null;
/*  26: 32 */     clazz = c;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static Implementation getInstance()
/*  30:    */   {
/*  31: 36 */     if ((instance == null) || (instance.getClass() != clazz)) {
/*  32:    */       try
/*  33:    */       {
/*  34: 38 */         instance = (Implementation)clazz.newInstance();
/*  35:    */       }
/*  36:    */       catch (InstantiationException e)
/*  37:    */       {
/*  38: 41 */         e.printStackTrace();
/*  39:    */       }
/*  40:    */       catch (IllegalAccessException e)
/*  41:    */       {
/*  42: 44 */         e.printStackTrace();
/*  43:    */       }
/*  44:    */     }
/*  45: 47 */     return instance;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static class Implementation
/*  49:    */   {
/*  50:    */     private HashMap<String, Bundle> bundleMap;
/*  51: 56 */     private WordNet wordNet = null;
/*  52: 58 */     private int cacheSize = 0;
/*  53:    */     
/*  54:    */     public WordNet getWordNet()
/*  55:    */     {
/*  56: 61 */       if (this.wordNet == null) {
/*  57: 62 */         this.wordNet = new WordNet();
/*  58:    */       }
/*  59: 64 */       return this.wordNet;
/*  60:    */     }
/*  61:    */     
/*  62:    */     public Bundle getRawBundle(String word)
/*  63:    */     {
/*  64: 68 */       Bundle bundle = (Bundle)getBundleMap().get(word);
/*  65: 69 */       if (bundle == null) {
/*  66: 70 */         if (word.indexOf('-') >= 0) {
/*  67: 71 */           bundle = new Bundle();
/*  68:    */         } else {
/*  69:    */           try
/*  70:    */           {
/*  71: 79 */             long start = System.currentTimeMillis();
/*  72: 80 */             bundle = getWordNet().lookup(word);
/*  73: 83 */             if ("thing".equals(word))
/*  74:    */             {
/*  75: 84 */               Thread thread = new Thread();
/*  76: 85 */               thread.add("thing");
/*  77: 86 */               bundle.add(0, thread);
/*  78:    */             }
/*  79: 88 */             Timer.laptime(false, "Word timer", "Time for " + word + ":", start);
/*  80:    */           }
/*  81:    */           catch (Exception e)
/*  82:    */           {
/*  83: 91 */             e.printStackTrace();
/*  84:    */           }
/*  85:    */         }
/*  86:    */       }
/*  87: 99 */       getBundleMap().put(word, bundle);
/*  88:100 */       return bundle;
/*  89:    */     }
/*  90:    */     
/*  91:    */     public Bundle getBundle(String word)
/*  92:    */     {
/*  93:105 */       Bundle bundle = getRawBundle(word).getThingClones();
/*  94:    */       
/*  95:107 */       return bundle;
/*  96:    */     }
/*  97:    */     
/*  98:    */     public HashMap<String, Bundle> getBundleMap()
/*  99:    */     {
/* 100:111 */       if (this.bundleMap == null)
/* 101:    */       {
/* 102:112 */         this.bundleMap = new HashMap();
/* 103:113 */         this.cacheSize = 0;
/* 104:    */       }
/* 105:115 */       return this.bundleMap;
/* 106:    */     }
/* 107:    */     
/* 108:118 */     private static boolean alreadyRead = false;
/* 109:    */     
/* 110:    */     public HashMap<String, Bundle> readWordnetCache()
/* 111:    */     {
/* 112:123 */       if (alreadyRead)
/* 113:    */       {
/* 114:124 */         Mark.say(new Object[] {"Wordnet cache already read" });
/* 115:125 */         return null;
/* 116:    */       }
/* 117:127 */       alreadyRead = true;
/* 118:    */       
/* 119:129 */       File file = new File(System.getProperty("user.home") + File.separator + "wordnet.data");
/* 120:130 */       if (!file.exists())
/* 121:    */       {
/* 122:131 */         Mark.say(new Object[] {"No wordnet cache,", file, "to load" });
/* 123:132 */         return getBundleMap();
/* 124:    */       }
/* 125:134 */       Mark.say(new Object[] {Boolean.valueOf(true), "Loading wordnet cache" });
/* 126:    */       try
/* 127:    */       {
/* 128:138 */         FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.home") + File.separator + "wordnet.data");
/* 129:139 */         ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
/* 130:140 */         Object object = objectInputStream.readObject();
/* 131:141 */         if (object != null) {
/* 132:142 */           this.bundleMap = ((HashMap)object);
/* 133:    */         }
/* 134:144 */         objectInputStream.close();
/* 135:145 */         fileInputStream.close();
/* 136:    */       }
/* 137:    */       catch (Exception e)
/* 138:    */       {
/* 139:148 */         e.printStackTrace();
/* 140:    */       }
/* 141:150 */       this.cacheSize = getBundleMap().size();
/* 142:151 */       Mark.say(new Object[] {Boolean.valueOf(true), "Number of cached items read: " + this.cacheSize });
/* 143:152 */       return getBundleMap();
/* 144:    */     }
/* 145:    */     
/* 146:    */     public void purgeWordnetCache()
/* 147:    */     {
/* 148:156 */       File file = new File(System.getProperty("user.home") + File.separator + "wordnet.data");
/* 149:157 */       if (!file.exists()) {
/* 150:158 */         Mark.say(new Object[] {Boolean.valueOf(true), "Wordnet cache", file, "already purged" });
/* 151:160 */       } else if (file.delete()) {
/* 152:161 */         Mark.say(new Object[] {Boolean.valueOf(true), "Purged wordnet cache,", file, ", of " + this.cacheSize, "items" });
/* 153:    */       } else {
/* 154:164 */         Mark.say(new Object[] {"Unable to purge wordnet cache file,", file });
/* 155:    */       }
/* 156:166 */       getBundleMap().clear();
/* 157:167 */       Mark.say(new Object[] {"Cache size is", Integer.valueOf(getBundleMap().size()) });
/* 158:168 */       this.cacheSize = 0;
/* 159:    */     }
/* 160:    */     
/* 161:    */     public void writeWordnetCache()
/* 162:    */     {
/* 163:172 */       Mark.say(
/* 164:    */       
/* 165:    */ 
/* 166:    */ 
/* 167:    */ 
/* 168:    */ 
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:    */ 
/* 174:    */ 
/* 175:    */ 
/* 176:    */ 
/* 177:    */ 
/* 178:    */ 
/* 179:    */ 
/* 180:    */ 
/* 181:190 */         new Object[] { Boolean.valueOf(true), "Writing wordnet cache" });
/* 182:    */       try
/* 183:    */       {
/* 184:175 */         FileOutputStream fileOutputStream = new FileOutputStream(System.getProperty("user.home") + File.separator + "wordnet.data");
/* 185:176 */         ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
/* 186:177 */         HashMap<String, Bundle> newMap = new HashMap();
/* 187:178 */         for (String key : getBundleMap().keySet()) {
/* 188:179 */           BundleGenerator.purge(key, (Bundle)getBundleMap().get(key));
/* 189:    */         }
/* 190:181 */         objectOutputStream.writeObject(getBundleMap());
/* 191:182 */         objectOutputStream.close();
/* 192:    */       }
/* 193:    */       catch (Exception e)
/* 194:    */       {
/* 195:186 */         e.printStackTrace();
/* 196:    */       }
/* 197:188 */       this.cacheSize = getBundleMap().size();
/* 198:189 */       Mark.say(new Object[] {"Number of Wordnet cached items written:", Integer.valueOf(this.cacheSize) });
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:    */   public static void setBundle(String word, Bundle bundle)
/* 203:    */   {
/* 204:196 */     getBundleMap().put(word, bundle.getClone());
/* 205:    */   }
/* 206:    */   
/* 207:    */   public static Bundle getBundle(String word)
/* 208:    */   {
/* 209:202 */     return getInstance().getBundle(word);
/* 210:    */   }
/* 211:    */   
/* 212:    */   private static HashMap<String, Bundle> getBundleMap()
/* 213:    */   {
/* 214:207 */     return getInstance().getBundleMap();
/* 215:    */   }
/* 216:    */   
/* 217:    */   public static HashMap<String, Bundle> readWordnetCache()
/* 218:    */   {
/* 219:212 */     return getInstance().readWordnetCache();
/* 220:    */   }
/* 221:    */   
/* 222:    */   public static void purgeWordnetCache()
/* 223:    */   {
/* 224:216 */     getInstance().purgeWordnetCache();
/* 225:    */   }
/* 226:    */   
/* 227:    */   public static void writeWordnetCache()
/* 228:    */   {
/* 229:220 */     getInstance().writeWordnetCache();
/* 230:    */   }
/* 231:    */   
/* 232:    */   private static Bundle purge(String key, Bundle input)
/* 233:    */   {
/* 234:224 */     ArrayList<Thread> bundle = new ArrayList();
/* 235:225 */     HashSet<Thread> set = new HashSet();
/* 236:226 */     if ((input != null) && (!input.isEmpty())) {
/* 237:227 */       for (Thread t : input) {
/* 238:228 */         if (!bundle.contains(t)) {
/* 239:229 */           bundle.add(t);
/* 240:    */         }
/* 241:    */       }
/* 242:    */     }
/* 243:233 */     input.clear();
/* 244:234 */     input.addAll(bundle);
/* 245:235 */     if (bundle.size() > 30) {
/* 246:236 */       Mark.say(new Object[] {"Bundle size for", key, "is " + bundle.size() });
/* 247:    */     }
/* 248:238 */     if (bundle.size() < input.size()) {
/* 249:239 */       Mark.say(new Object[] {"Bundle for " + key, "reduced from", input.size() + " to " + bundle.size() });
/* 250:    */     }
/* 251:241 */     return input;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public static ArrayList<String> getSiblings(String word)
/* 255:    */   {
/* 256:246 */     Bundle bundle = getBundle(word);
/* 257:247 */     if (bundle.size() == 0) {
/* 258:248 */       return null;
/* 259:    */     }
/* 260:251 */     Thread primedThread = (Thread)bundle.get(0);
/* 261:252 */     int length = primedThread.size();
/* 262:253 */     ArrayList<String> result = new ArrayList();
/* 263:254 */     for (Thread t : getSiblingThreads(primedThread))
/* 264:    */     {
/* 265:255 */       String category = (String)t.get(length - 1);
/* 266:256 */       if ((!result.contains(category)) && (!category.equalsIgnoreCase(word))) {
/* 267:259 */         result.add(category);
/* 268:    */       }
/* 269:    */     }
/* 270:261 */     return result;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public static ArrayList<Thread> getSiblingThreads(String word)
/* 274:    */   {
/* 275:266 */     Bundle bundle = getBundle(word);
/* 276:267 */     if (bundle.size() == 0) {
/* 277:268 */       return null;
/* 278:    */     }
/* 279:270 */     Thread primedThread = (Thread)bundle.get(0);
/* 280:271 */     return getSiblingThreads(primedThread);
/* 281:    */   }
/* 282:    */   
/* 283:    */   public static ArrayList<Thread> getSiblingThreads(Thread primedThread)
/* 284:    */   {
/* 285:277 */     String word = (String)primedThread.lastElement();
/* 286:    */     
/* 287:279 */     int length = primedThread.size();
/* 288:    */     
/* 289:281 */     ArrayList<Thread> allPrimedThreads = new ArrayList();
/* 290:282 */     for (Bundle b : getBundleMap().values()) {
/* 291:283 */       if (b.size() != 0) {
/* 292:284 */         allPrimedThreads.addAll(b);
/* 293:    */       }
/* 294:    */     }
/* 295:288 */     ArrayList<Thread> testList = new ArrayList();
/* 296:289 */     testList.addAll(allPrimedThreads);
/* 297:290 */     allPrimedThreads.clear();
/* 298:291 */     for (Thread t : testList) {
/* 299:293 */       if (t.size() >= length) {
/* 300:297 */         allPrimedThreads.add(t);
/* 301:    */       }
/* 302:    */     }
/* 303:    */     Thread t;
/* 304:303 */     for (int i = 0; i < primedThread.size() - 1; i++)
/* 305:    */     {
/* 306:304 */       String nextClass = (String)primedThread.get(i);
/* 307:305 */       testList = new ArrayList();
/* 308:306 */       testList.addAll(allPrimedThreads);
/* 309:307 */       allPrimedThreads.clear();
/* 310:308 */       for (Iterator localIterator3 = testList.iterator(); localIterator3.hasNext();)
/* 311:    */       {
/* 312:308 */         t = (Thread)localIterator3.next();
/* 313:309 */         if (nextClass.equalsIgnoreCase((String)t.get(i))) {
/* 314:310 */           allPrimedThreads.add(t);
/* 315:    */         }
/* 316:    */       }
/* 317:    */     }
/* 318:316 */     Object result = new ArrayList();
/* 319:318 */     for (Thread t : allPrimedThreads)
/* 320:    */     {
/* 321:319 */       String aClass = (String)t.get(length - 1);
/* 322:321 */       if ((!((ArrayList)result).contains(aClass)) && (!aClass.equalsIgnoreCase(word))) {
/* 323:326 */         ((ArrayList)result).add(t);
/* 324:    */       }
/* 325:    */     }
/* 326:329 */     return result;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public static void main(String[] ignore)
/* 330:    */   {
/* 331:333 */     for (Thread t : getBundle("run")) {
/* 332:334 */       Mark.say(new Object[] {Boolean.valueOf(true), t });
/* 333:    */     }
/* 334:336 */     System.out.println(getSiblingThreads("bird"));
/* 335:337 */     System.out.println(getSiblings("bird"));
/* 336:    */   }
/* 337:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     links.words.BundleGenerator
 * JD-Core Version:    0.7.0.1
 */