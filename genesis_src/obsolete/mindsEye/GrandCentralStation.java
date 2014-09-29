/*   1:    */ package obsolete.mindsEye;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import adam.RPCBox;
/*   5:    */ import adamKraft.Co57Connector;
/*   6:    */ import connections.AbstractWiredBox;
/*   7:    */ import connections.Connections;
/*   8:    */ import connections.Connections.NetWireError;
/*   9:    */ import connections.Connections.NetWireException;
/*  10:    */ import connections.Ports;
/*  11:    */ import connections.WiredBox;
/*  12:    */ import java.io.File;
/*  13:    */ import java.io.FileInputStream;
/*  14:    */ import java.io.FileOutputStream;
/*  15:    */ import java.io.ObjectInputStream;
/*  16:    */ import java.io.ObjectOutputStream;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import java.util.Set;
/*  21:    */ import text.Html;
/*  22:    */ import utils.Mark;
/*  23:    */ import utils.Timer;
/*  24:    */ 
/*  25:    */ public class GrandCentralStation
/*  26:    */   extends AbstractWiredBox
/*  27:    */ {
/*  28: 25 */   boolean skipCo57 = false;
/*  29: 27 */   boolean skipImpact = false;
/*  30:    */   public static final String MOVIE_NAME = "movie name";
/*  31:    */   public static final String TEST = "debugging port";
/*  32:    */   public static final String IMPACT_RCP = "IMPACT calls";
/*  33:    */   public static final String TO_COMMENTATOR = "to commentator";
/*  34:    */   public static final String TO_MOVIE_SELECTOR = "to movie selector";
/*  35:    */   public static final String STATS = "stats";
/*  36:    */   Co57Connector co57Connector;
/*  37: 48 */   private static String co57Path = "/home/br/Demos/wire/movies/";
/*  38:    */   String localMovieLabel;
/*  39: 52 */   private File localCachePath = new File(System.getProperty("user.home") + "/AppData/Local/Genesis/MindsEyeCache");
/*  40:    */   private WiredBox impactBox;
/*  41:    */   private WiredBox berylBox;
/*  42:    */   private static final String BERYL = "beryl.co57.com";
/*  43:    */   private static final String IMPACT = "IMPACT";
/*  44:    */   String title;
/*  45:    */   public static final String GET_MOVIE_TITLES_METHOD = "get_filenames";
/*  46:    */   public static final String DO_MOVIE_METHOD = "run";
/*  47:    */   
/*  48:    */   public GrandCentralStation()
/*  49:    */   {
/*  50: 81 */     Connections.getPorts(this).addSignalProcessor("debugging port", "startMovieAnalysis");
/*  51: 82 */     Connections.getPorts(this).addSignalProcessor("movie name", "startMovieAnalysis");
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setMovieTitles()
/*  55:    */   {
/*  56: 86 */     Co57Connector connector = new Co57Connector();
/*  57:    */     
/*  58:    */ 
/*  59: 89 */     Set<String> moviesThatHaveAnnotations = connector.getMoviesForAnnotationGroup("y2_eval_v3");
/*  60:    */     
/*  61: 91 */     List<String> allTitles = connector.getMovieTitles();
/*  62:    */     
/*  63: 93 */     Mark.say(new Object[] {"There appears to be", Integer.valueOf(allTitles.size()), "titles" });
/*  64: 95 */     for (String title : allTitles) {
/*  65: 96 */       Mark.say(new Object[] {"Title:", title });
/*  66:    */     }
/*  67: 99 */     Mark.say(new Object[] {"... of which", Integer.valueOf(moviesThatHaveAnnotations.size()), "have annotations" });
/*  68:101 */     for (String title : moviesThatHaveAnnotations) {
/*  69:102 */       Mark.say(new Object[] {"Title with annotations:", title });
/*  70:    */     }
/*  71:109 */     ArrayList<String> titles = new ArrayList();
/*  72:111 */     for (String x : moviesThatHaveAnnotations) {
/*  73:112 */       titles.add(x);
/*  74:    */     }
/*  75:122 */     Mark.say(new Object[] {"Transmitting annotated titles", Integer.valueOf(titles.size()) });
/*  76:123 */     Connections.getPorts(this).transmit("to movie selector", titles);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public WiredBox getImpactBox()
/*  80:    */   {
/*  81:127 */     if (this.impactBox == null) {
/*  82:    */       try
/*  83:    */       {
/*  84:129 */         this.impactBox = Connections.subscribe("IMPACT");
/*  85:    */       }
/*  86:    */       catch (Connections.NetWireException e)
/*  87:    */       {
/*  88:132 */         Mark.err(new Object[] {"Unable to communicate with IMPACT" });
/*  89:    */       }
/*  90:    */     }
/*  91:136 */     return this.impactBox;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public WiredBox getBerylBox()
/*  95:    */   {
/*  96:140 */     if (this.berylBox == null) {
/*  97:    */       try
/*  98:    */       {
/*  99:143 */         this.berylBox = Connections.subscribe("beryl.co57.com");
/* 100:    */       }
/* 101:    */       catch (Connections.NetWireException e)
/* 102:    */       {
/* 103:146 */         Mark.err(new Object[] {"Unable to communicate with beryl.co57.com" });
/* 104:    */       }
/* 105:    */     }
/* 106:149 */     return this.berylBox;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void startMovieAnalysis(Object signal)
/* 110:    */     throws Exception
/* 111:    */   {
/* 112:153 */     Mark.say(
/* 113:    */     
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:    */ 
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:    */ 
/* 130:    */ 
/* 131:172 */       new Object[] { "Entering startMovie analysis", signal });this.localMovieLabel = ((String)signal);String name = new File(this.localMovieLabel).getName();String co57MovieLabel = new File(co57Path + "\\" + name).getPath();co57MovieLabel = backslashToForwardSlash(co57MovieLabel);Mark.say(new Object[] { "Movie co57 path complete:", co57MovieLabel });List<Map> attentionTraces = generateAttentionTraceUsingCo57(name);String eventSequence = generateImpactSequenceUsingImpact(co57MovieLabel, attentionTraces);Mark.say(new Object[] { "Sending Impact data to Translator" });long time = System.currentTimeMillis();Connections.getPorts(this).transmit("stats", new BetterSignal(new Object[] { "Timer", Html.line("Starting START processing.") }));Connections.getPorts(this).transmit("to commentator", new BetterSignal(new Object[] { eventSequence }));Connections.getPorts(this).transmit("stats", new BetterSignal(new Object[] { "Timer", Html.line("START processing took " + Timer.time("START", time)) }));Mark.say(new Object[] { "Starting movie" });Connections.getPorts(this).transmit("movie name", this.localMovieLabel);
/* 132:    */   }
/* 133:    */   
/* 134:    */   private String backslashToForwardSlash(String co57Name)
/* 135:    */   {
/* 136:175 */     StringBuffer b = new StringBuffer(co57Name);
/* 137:176 */     int index = -1;
/* 138:177 */     while ((index = b.indexOf("\\")) >= 0) {
/* 139:178 */       b.replace(index, index + 1, "/");
/* 140:    */     }
/* 141:180 */     return b.toString();
/* 142:    */   }
/* 143:    */   
/* 144:    */   private String extractFileName(String path)
/* 145:    */   {
/* 146:184 */     String name = new File(path).getName();
/* 147:185 */     int index = name.indexOf('.');
/* 148:186 */     if (index > 0) {
/* 149:187 */       name = name.substring(0, index);
/* 150:    */     }
/* 151:189 */     return name;
/* 152:    */   }
/* 153:    */   
/* 154:    */   private File generateBerylCache(String path)
/* 155:    */   {
/* 156:193 */     this.localCachePath.mkdirs();
/* 157:194 */     return new File(this.localCachePath.getPath() + "/" + extractFileName(path) + ".beryl.out");
/* 158:    */   }
/* 159:    */   
/* 160:    */   private File generateImpactCache(String path)
/* 161:    */   {
/* 162:198 */     this.localCachePath.mkdirs();
/* 163:199 */     return new File(this.localCachePath.getPath() + "/" + extractFileName(path) + ".impact.out");
/* 164:    */   }
/* 165:    */   
/* 166:    */   protected List<Map> generateAttentionTraceUsingCo57(String co57MovieLabel)
/* 167:    */   {
/* 168:204 */     this.title = co57MovieLabel;
/* 169:    */     
/* 170:206 */     File co57File = generateBerylCache(co57MovieLabel);
/* 171:    */     
/* 172:208 */     List<Map> attentionalTrace = null;
/* 173:    */     try
/* 174:    */     {
/* 175:212 */       if ((this.skipCo57) && (co57File.exists()))
/* 176:    */       {
/* 177:213 */         ObjectInputStream co57Stream = new ObjectInputStream(new FileInputStream(co57File));
/* 178:214 */         Object input = co57Stream.readObject();
/* 179:215 */         co57Stream.close();
/* 180:216 */         if (input != null)
/* 181:    */         {
/* 182:217 */           attentionalTrace = (List)input;
/* 183:218 */           Mark.say(new Object[] {"Reloaded", co57MovieLabel, "attention trace, size is", Integer.valueOf(attentionalTrace.size()), "events." });
/* 184:    */         }
/* 185:    */       }
/* 186:    */     }
/* 187:    */     catch (Exception e1)
/* 188:    */     {
/* 189:223 */       Mark.err(new Object[] {"Could not reload from", co57File.getPath() });
/* 190:    */     }
/* 191:226 */     if (attentionalTrace == null)
/* 192:    */     {
/* 193:227 */       long time = System.currentTimeMillis();
/* 194:228 */       Connections.getPorts(this).transmit("stats", "clear");
/* 195:229 */       Connections.getPorts(this).transmit("stats", new BetterSignal(new Object[] { "Timer", Html.line("Starting Co57 processing.") }));
/* 196:    */       try
/* 197:    */       {
/* 198:231 */         Mark.say(new Object[] {"Sending request to Co57 to process", co57MovieLabel });
/* 199:232 */         attentionalTrace = getCo57Connector().fetchVat(co57MovieLabel);
/* 200:233 */         Mark.say(new Object[] {"Co57 has produced response for", co57MovieLabel });
/* 201:234 */         ObjectOutputStream co57Stream = new ObjectOutputStream(new FileOutputStream(co57File));
/* 202:235 */         co57Stream.writeObject(attentionalTrace);
/* 203:236 */         co57Stream.close();
/* 204:    */       }
/* 205:    */       catch (Exception e)
/* 206:    */       {
/* 207:239 */         Mark.err(new Object[] {"Error in request to Co57 to process", co57MovieLabel });
/* 208:240 */         e.printStackTrace();
/* 209:    */       }
/* 210:    */       finally
/* 211:    */       {
/* 212:243 */         Connections.getPorts(this).transmit("stats", new BetterSignal(new Object[] { "Timer", Html.line("Co57 processing took " + Timer.time("Co57", time)) }));
/* 213:    */       }
/* 214:    */     }
/* 215:    */     else
/* 216:    */     {
/* 217:247 */       Mark.say(new Object[] {"Using stored Co57 trace", co57MovieLabel });
/* 218:    */     }
/* 219:250 */     return attentionalTrace;
/* 220:    */   }
/* 221:    */   
/* 222:    */   private String generateImpactSequenceUsingImpact(String movieTitle, List<Map> attentionalTrace)
/* 223:    */   {
/* 224:255 */     File IMPACTFile = generateImpactCache(movieTitle);
/* 225:256 */     String eventSequence = null;
/* 226:    */     try
/* 227:    */     {
/* 228:259 */       if ((this.skipImpact) && (IMPACTFile.exists()))
/* 229:    */       {
/* 230:260 */         ObjectInputStream IMPACTStream = new ObjectInputStream(new FileInputStream(IMPACTFile));
/* 231:261 */         Object input = IMPACTStream.readObject();
/* 232:262 */         if (input != null)
/* 233:    */         {
/* 234:263 */           eventSequence = (String)input;
/* 235:264 */           Mark.say(new Object[] {"Reloaded", this.title, "IMPACT analysis, size is", Integer.valueOf(eventSequence.length()), "characters." });
/* 236:    */         }
/* 237:    */       }
/* 238:    */     }
/* 239:    */     catch (Exception e1)
/* 240:    */     {
/* 241:269 */       Mark.err(new Object[] {"Could not reload from", IMPACTFile.getPath() });
/* 242:    */     }
/* 243:272 */     if (eventSequence == null)
/* 244:    */     {
/* 245:273 */       long time = System.currentTimeMillis();
/* 246:274 */       Connections.getPorts(this).transmit("stats", new BetterSignal(new Object[] { "Timer", Html.line("Starting IMPACT processing.") }));
/* 247:    */       try
/* 248:    */       {
/* 249:276 */         Mark.say(new Object[] {"Sending request to Impact to process", movieTitle });
/* 250:277 */         eventSequence = processWithImpact(attentionalTrace);
/* 251:278 */         Mark.say(new Object[] {"IMACT has produced response for", this.title });
/* 252:279 */         if (eventSequence != null)
/* 253:    */         {
/* 254:280 */           ObjectOutputStream co57Stream = new ObjectOutputStream(new FileOutputStream(IMPACTFile));
/* 255:281 */           co57Stream.writeObject(eventSequence);
/* 256:282 */           co57Stream.close();
/* 257:    */         }
/* 258:    */         else
/* 259:    */         {
/* 260:285 */           Mark.err(new Object[] {"IMPACT returned a null result" });
/* 261:286 */           return "";
/* 262:    */         }
/* 263:    */       }
/* 264:    */       catch (Exception e)
/* 265:    */       {
/* 266:290 */         Mark.err(new Object[] {"Error in request to IMPACT to process", this.title });
/* 267:291 */         e.printStackTrace();
/* 268:    */       }
/* 269:    */       finally
/* 270:    */       {
/* 271:294 */         Connections.getPorts(this).transmit("stats", new BetterSignal(new Object[] { "Timer", 
/* 272:295 */           Html.line("IMPACT processing took " + Timer.time("IMPACT", time)) }));
/* 273:    */       }
/* 274:294 */       Connections.getPorts(this).transmit("stats", new BetterSignal(new Object[] { "Timer", 
/* 275:295 */         Html.line("IMPACT processing took " + Timer.time("IMPACT", time)) }));
/* 276:    */     }
/* 277:    */     else
/* 278:    */     {
/* 279:299 */       Mark.say(new Object[] {"Using stored Co57 trace", this.title });
/* 280:    */     }
/* 281:302 */     return eventSequence;
/* 282:    */   }
/* 283:    */   
/* 284:    */   private String processWithImpact(List<Map> traces)
/* 285:    */   {
/* 286:    */     try
/* 287:    */     {
/* 288:308 */       if (traces.isEmpty())
/* 289:    */       {
/* 290:309 */         Mark.err(new Object[] {"No impact analysis; ArrayList returned from Beryl is empty" });
/* 291:310 */         return null;
/* 292:    */       }
/* 293:312 */       String string = "Analyze the supplied trial.";
/* 294:313 */       Mark.say(new Object[] {"Traces sent to impact:", ((Map)traces.get(0)).get("FocusedObject") });
/* 295:314 */       for (Map map : traces) {
/* 296:315 */         Mark.say(new Object[] {"Map:", map });
/* 297:    */       }
/* 298:317 */       Object object = ((RPCBox)getImpactBox()).rpc("rpcMethod2", new Object[] { string, traces });
/* 299:    */       
/* 300:319 */       Mark.say(new Object[] {"Got past impact" });
/* 301:321 */       if (((String)object).trim().startsWith("("))
/* 302:    */       {
/* 303:322 */         Mark.err(new Object[] {"Impact has reported error", object.toString().trim() });
/* 304:323 */         return null;
/* 305:    */       }
/* 306:326 */       return (String)object;
/* 307:    */     }
/* 308:    */     catch (Exception e)
/* 309:    */     {
/* 310:331 */       Mark.err(new Object[] {"Attempt at IMPACT processing failed." });
/* 311:    */     }
/* 312:334 */     return null;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public List<String> getMovieTitles()
/* 316:    */   {
/* 317:    */     try
/* 318:    */     {
/* 319:339 */       if (getCo57Connector() != null)
/* 320:    */       {
/* 321:340 */         List<String> allTitles = getCo57Connector().getMovieTitles();
/* 322:341 */         List<String> titles = new ArrayList();
/* 323:342 */         Mark.say(new Object[] {"Got", Integer.valueOf(allTitles.size()), "titles" });
/* 324:343 */         for (String title : allTitles) {
/* 325:345 */           titles.add(title);
/* 326:    */         }
/* 327:347 */         return titles;
/* 328:    */       }
/* 329:    */     }
/* 330:    */     catch (Connections.NetWireError e)
/* 331:    */     {
/* 332:351 */       Mark.err(new Object[] {"Unable to wire up Genesis/Vision to fetch movie titles; probably something is down; check the wire website" });
/* 333:352 */       e.printStackTrace();
/* 334:    */     }
/* 335:355 */     return null;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public Co57Connector getCo57Connector()
/* 339:    */   {
/* 340:359 */     if (this.co57Connector == null) {
/* 341:360 */       this.co57Connector = new Co57Connector();
/* 342:    */     }
/* 343:362 */     return this.co57Connector;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public static void main(String[] ignore)
/* 347:    */     throws Exception
/* 348:    */   {
/* 349:368 */     GrandCentralStation gcs = new GrandCentralStation();
/* 350:369 */     for (String movie : gcs.getMovieTitles()) {
/* 351:370 */       Mark.say(new Object[] {"Movie name:", movie });
/* 352:    */     }
/* 353:    */   }
/* 354:    */   
/* 355:    */   public void initialize()
/* 356:    */   {
/* 357:378 */     setMovieTitles();
/* 358:    */   }
/* 359:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     obsolete.mindsEye.GrandCentralStation
 * JD-Core Version:    0.7.0.1
 */