/*   1:    */ package links.words;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Thread;
/*   6:    */ import edu.mit.jwi.Dictionary;
/*   7:    */ import edu.mit.jwi.IDictionary;
/*   8:    */ import edu.mit.jwi.item.IIndexWord;
/*   9:    */ import edu.mit.jwi.item.ISynset;
/*  10:    */ import edu.mit.jwi.item.ISynsetID;
/*  11:    */ import edu.mit.jwi.item.IWord;
/*  12:    */ import edu.mit.jwi.item.IWordID;
/*  13:    */ import edu.mit.jwi.item.POS;
/*  14:    */ import edu.mit.jwi.item.Pointer;
/*  15:    */ import edu.mit.jwi.morph.SimpleStemmer;
/*  16:    */ import edu.mit.jwi.morph.WordnetStemmer;
/*  17:    */ import java.io.File;
/*  18:    */ import java.io.FileOutputStream;
/*  19:    */ import java.io.IOException;
/*  20:    */ import java.io.InputStream;
/*  21:    */ import java.io.OutputStream;
/*  22:    */ import java.io.PrintStream;
/*  23:    */ import java.net.URI;
/*  24:    */ import java.net.URL;
/*  25:    */ import java.util.ArrayList;
/*  26:    */ import java.util.Arrays;
/*  27:    */ import java.util.Collections;
/*  28:    */ import java.util.Iterator;
/*  29:    */ import java.util.List;
/*  30:    */ import links.words.dict.DictionaryAnchor;
/*  31:    */ import memory.ThreadMemory;
/*  32:    */ import utils.Mark;
/*  33:    */ 
/*  34:    */ public class WordNet
/*  35:    */   implements ThreadMemory
/*  36:    */ {
/*  37:    */   static final String wordNetPath = "links/words/dict/";
/*  38:    */   private SimpleStemmer sstem;
/*  39:    */   private IDictionary idict;
/*  40:    */   public static final String TMP_PATH = "genesis/";
/*  41:    */   
/*  42:    */   private Bundle getBundleForWord(String word, String pos_string)
/*  43:    */   {
/*  44: 39 */     Bundle bucket = new Bundle();
/*  45:    */     
/*  46: 41 */     POS pos = PennTag.convert(pos_string);
/*  47: 43 */     if (pos == null) {
/*  48: 44 */       return bucket;
/*  49:    */     }
/*  50: 47 */     IIndexWord baseIndex = getFirstIndexWord(word, pos);
/*  51: 48 */     if (baseIndex == null) {
/*  52: 50 */       return bucket;
/*  53:    */     }
/*  54: 53 */     List<ISynset> synsets = getSynsetsForWord(word, pos);
/*  55: 54 */     for (ISynset synset : synsets)
/*  56:    */     {
/*  57: 57 */       Entity placeHolder = new Entity(word);
/*  58: 58 */       placeHolder.removeType("thing");
/*  59: 59 */       placeHolder.getPrimedThread().addTypeFront(baseIndex.getLemma());
/*  60: 60 */       Thread primedThread = placeHolder.getPrimedThread();
/*  61: 61 */       List<ISynset> visited = new ArrayList();
/*  62: 62 */       while (synset != null)
/*  63:    */       {
/*  64:    */         try
/*  65:    */         {
/*  66: 65 */           w = (IWord)synset.getWords().get(0);
/*  67:    */         }
/*  68:    */         catch (Exception e)
/*  69:    */         {
/*  70:    */           IWord w;
/*  71: 68 */           System.err.println("Warning: ");
/*  72: 69 */           e.printStackTrace();
/*  73: 70 */           return bucket;
/*  74:    */         }
/*  75:    */         IWord w;
/*  76: 72 */         primedThread.addTypeFront(w.getLemma().replaceAll("_", "-"));
/*  77:    */         try
/*  78:    */         {
/*  79: 75 */           List<ISynsetID> related = synset.getRelatedSynsets(Pointer.HYPERNYM);
/*  80:    */           
/*  81: 77 */           synset = related.isEmpty() ? null : getDict().getSynset((ISynsetID)related.get(0));
/*  82: 78 */           if (visited.contains(synset)) {
/*  83: 79 */             synset = null;
/*  84:    */           } else {
/*  85: 82 */             visited.add(synset);
/*  86:    */           }
/*  87:    */         }
/*  88:    */         catch (NullPointerException e)
/*  89:    */         {
/*  90: 86 */           synset = null;
/*  91:    */         }
/*  92:    */         catch (ArrayIndexOutOfBoundsException e)
/*  93:    */         {
/*  94: 89 */           synset = null;
/*  95:    */         }
/*  96:    */       }
/*  97: 93 */       if (pos == POS.NOUN) {
/*  98: 94 */         primedThread.addTypeFront("thing");
/*  99: 96 */       } else if (pos == POS.VERB) {
/* 100: 97 */         primedThread.addTypeFront("action");
/* 101: 99 */       } else if (pos == POS.ADJECTIVE) {
/* 102:101 */         primedThread.addTypeFront("ad_word");
/* 103:103 */       } else if (pos == POS.ADVERB) {
/* 104:104 */         primedThread.addTypeFront("feature");
/* 105:    */       } else {
/* 106:107 */         Mark.err(new Object[] {"Bugger all!" });
/* 107:    */       }
/* 108:109 */       bucket.add(primedThread);
/* 109:    */     }
/* 110:113 */     return bucket;
/* 111:    */   }
/* 112:    */   
/* 113:    */   private IIndexWord getFirstIndexWord(String wordStr, POS pos)
/* 114:    */   {
/* 115:117 */     List<String> allStems = getStemmer().findStems(wordStr, pos);
/* 116:118 */     if (allStems == null) {
/* 117:119 */       allStems = Collections.emptyList();
/* 118:    */     }
/* 119:122 */     for (String candidate : allStems)
/* 120:    */     {
/* 121:123 */       IIndexWord idxWord = getDict().getIndexWord(candidate, pos);
/* 122:124 */       if (idxWord != null) {
/* 123:125 */         return idxWord;
/* 124:    */       }
/* 125:    */     }
/* 126:128 */     IIndexWord idxWord = getDict().getIndexWord(wordStr, pos);
/* 127:129 */     return idxWord;
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected SimpleStemmer getStemmer()
/* 131:    */   {
/* 132:135 */     if (this.sstem == null) {
/* 133:136 */       this.sstem = new WordnetStemmer(getDict());
/* 134:    */     }
/* 135:138 */     return this.sstem;
/* 136:    */   }
/* 137:    */   
/* 138:    */   protected IDictionary getDict()
/* 139:    */   {
/* 140:144 */     if (this.idict == null) {
/* 141:    */       try
/* 142:    */       {
/* 143:146 */         this.idict = new Dictionary(fileDict("links/words/dict/"));
/* 144:147 */         this.idict.open();
/* 145:    */       }
/* 146:    */       catch (IOException e)
/* 147:    */       {
/* 148:150 */         e.printStackTrace();
/* 149:    */       }
/* 150:    */     }
/* 151:153 */     return this.idict;
/* 152:    */   }
/* 153:    */   
/* 154:    */   private static URL fileDict(String dictPath)
/* 155:    */     throws IOException
/* 156:    */   {
/* 157:168 */     boolean debug = false;
/* 158:169 */     URL jarDict = WordNet.class.getClassLoader().getResource(dictPath);
/* 159:    */     
/* 160:    */ 
/* 161:    */ 
/* 162:173 */     URL testDict = WordNet.class.getResource("dict/");
/* 163:    */     
/* 164:    */ 
/* 165:    */ 
/* 166:    */ 
/* 167:    */ 
/* 168:    */ 
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:184 */     Mark.say(new Object[] {Boolean.valueOf(debug), "wordNetPath", "links/words/dict/" });
/* 174:185 */     Mark.say(new Object[] {Boolean.valueOf(debug), "dictPath", dictPath });
/* 175:186 */     Mark.say(new Object[] {Boolean.valueOf(debug), "testDict", testDict });
/* 176:187 */     Mark.say(new Object[] {Boolean.valueOf(debug), "jarDict", jarDict });
/* 177:189 */     if ((jarDict != null) && (jarDict.getProtocol().equals("file")))
/* 178:    */     {
/* 179:190 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Returning", jarDict });
/* 180:191 */       return jarDict;
/* 181:    */     }
/* 182:194 */     File fileDir = new File(System.getProperty("java.io.tmpdir"), "genesis/");
/* 183:195 */     File fileDict = new File(fileDir, dictPath);
/* 184:    */     
/* 185:197 */     Mark.say(new Object[] {Boolean.valueOf(debug), "fileDir", fileDir });
/* 186:198 */     Mark.say(new Object[] {Boolean.valueOf(debug), "fileDict", fileDict });
/* 187:    */     
/* 188:200 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Using local temp directory for WordNet: " + fileDict });
/* 189:203 */     if (fileDict.exists()) {
/* 190:204 */       return fileDict.toURI().toURL();
/* 191:    */     }
/* 192:239 */     copyFromJarToTemp("adj.exc", fileDict);
/* 193:240 */     copyFromJarToTemp("adv.exc", fileDict);
/* 194:241 */     copyFromJarToTemp("cntlist.rev", fileDict);
/* 195:242 */     copyFromJarToTemp("data.adj", fileDict);
/* 196:243 */     copyFromJarToTemp("data.adv", fileDict);
/* 197:244 */     copyFromJarToTemp("data.noun", fileDict);
/* 198:245 */     copyFromJarToTemp("data.verb", fileDict);
/* 199:246 */     copyFromJarToTemp("frames.vrb", fileDict);
/* 200:247 */     copyFromJarToTemp("index.adj", fileDict);
/* 201:248 */     copyFromJarToTemp("index.adv", fileDict);
/* 202:249 */     copyFromJarToTemp("index.noun", fileDict);
/* 203:250 */     copyFromJarToTemp("index.sense", fileDict);
/* 204:251 */     copyFromJarToTemp("index.verb", fileDict);
/* 205:252 */     copyFromJarToTemp("lexnames", fileDict);
/* 206:253 */     copyFromJarToTemp("log.grind.3.0", fileDict);
/* 207:254 */     copyFromJarToTemp("noun.exc", fileDict);
/* 208:255 */     copyFromJarToTemp("sentidx.vrb", fileDict);
/* 209:256 */     copyFromJarToTemp("sents.vrb", fileDict);
/* 210:257 */     copyFromJarToTemp("verb.exc", fileDict);
/* 211:    */     
/* 212:259 */     Mark.say(new Object[] {"Returning", fileDict.toURI().toURL() });
/* 213:    */     
/* 214:261 */     return fileDict.toURI().toURL();
/* 215:    */   }
/* 216:    */   
/* 217:    */   private static void copyFromJarToTemp(String name, File fileDir)
/* 218:    */     throws IOException
/* 219:    */   {
/* 220:265 */     byte[] buf = new byte[1024];
/* 221:266 */     URL source = DictionaryAnchor.class.getResource(name);
/* 222:267 */     InputStream sourceStream = source.openStream();
/* 223:268 */     OutputStream destinationStream = new FileOutputStream(new File(fileDir, name));
/* 224:269 */     int read = 0;
/* 225:270 */     while (read >= 0)
/* 226:    */     {
/* 227:271 */       destinationStream.write(buf, 0, read);
/* 228:272 */       read = sourceStream.read(buf);
/* 229:    */     }
/* 230:274 */     destinationStream.close();
/* 231:275 */     sourceStream.close();
/* 232:    */   }
/* 233:    */   
/* 234:    */   private List<ISynset> getSynsetsForWord(String word, POS pos)
/* 235:    */   {
/* 236:280 */     IIndexWord idxWord = getFirstIndexWord(word, pos);
/* 237:281 */     List<IWordID> wArr = idxWord.getWordIDs();
/* 238:282 */     List<ISynset> synsets = new ArrayList();
/* 239:283 */     if (wArr == null) {
/* 240:284 */       return new ArrayList();
/* 241:    */     }
/* 242:286 */     for (IWordID wordID : wArr)
/* 243:    */     {
/* 244:287 */       IWord iword = getDict().getWord(wordID);
/* 245:288 */       ISynsetID synsetID = (ISynsetID)iword.getSynset().getID();
/* 246:289 */       ISynset synset = getDict().getSynset(synsetID);
/* 247:290 */       synsets.add(synset);
/* 248:    */     }
/* 249:292 */     return synsets;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void add(String word, Thread thread) {}
/* 253:    */   
/* 254:    */   public Bundle lookup(String word)
/* 255:    */   {
/* 256:303 */     Bundle result = new Bundle();
/* 257:304 */     Iterator localIterator = Arrays.asList(new String[] { "NN", "VB", "JJ", "RB" }).iterator();
/* 258:307 */     while (localIterator.hasNext())
/* 259:    */     {
/* 260:304 */       String tag = (String)localIterator.next();
/* 261:305 */       Bundle thisBundle = lookup(word, tag);
/* 262:306 */       if (thisBundle != null) {
/* 263:307 */         result.addAll(thisBundle);
/* 264:    */       }
/* 265:    */     }
/* 266:311 */     return pruneBundle(result);
/* 267:    */   }
/* 268:    */   
/* 269:    */   private Bundle pruneBundle(Bundle bundle)
/* 270:    */   {
/* 271:315 */     Bundle result = new Bundle();
/* 272:316 */     for (Thread t : bundle) {
/* 273:317 */       if (!result.contains(t)) {
/* 274:318 */         result.addElement(t);
/* 275:    */       }
/* 276:    */     }
/* 277:321 */     return result;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public Bundle lookupFirstChoices(String word)
/* 281:    */   {
/* 282:328 */     Bundle result = new Bundle();
/* 283:329 */     Iterator localIterator = Arrays.asList(new String[] { "NN", "VB", "JJ", "RB" }).iterator();
/* 284:332 */     while (localIterator.hasNext())
/* 285:    */     {
/* 286:329 */       String tag = (String)localIterator.next();
/* 287:330 */       Bundle thisBundle = lookup(word, tag);
/* 288:331 */       if (thisBundle != null) {
/* 289:332 */         result.add(thisBundle.getPrimedThread());
/* 290:    */       }
/* 291:    */     }
/* 292:336 */     return result;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public Bundle lookup(String word, String partOfSpeech)
/* 296:    */   {
/* 297:343 */     Bundle result = getBundleForWord(word, partOfSpeech);
/* 298:344 */     if (result.isEmpty()) {
/* 299:345 */       return new Bundle();
/* 300:    */     }
/* 301:347 */     return result;
/* 302:    */   }
/* 303:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     links.words.WordNet
 * JD-Core Version:    0.7.0.1
 */