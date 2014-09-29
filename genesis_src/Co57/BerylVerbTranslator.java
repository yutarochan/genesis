/*   1:    */ package Co57;
/*   2:    */ 
/*   3:    */ import Co57.services.Co57Passthrough;
/*   4:    */ import Signals.BetterSignal;
/*   5:    */ import bridge.reps.entities.Entity;
/*   6:    */ import bridge.reps.entities.RFactory;
/*   7:    */ import bridge.reps.entities.Relation;
/*   8:    */ import bridge.reps.entities.Sequence;
/*   9:    */ import com.google.gson.Gson;
/*  10:    */ import com.google.gson.JsonArray;
/*  11:    */ import com.google.gson.JsonElement;
/*  12:    */ import com.google.gson.JsonNull;
/*  13:    */ import com.google.gson.JsonObject;
/*  14:    */ import com.google.gson.JsonParser;
/*  15:    */ import com.google.gson.stream.JsonReader;
/*  16:    */ import connections.AbstractWiredBox;
/*  17:    */ import connections.Connections;
/*  18:    */ import connections.Connections.NetWireException;
/*  19:    */ import connections.Ports;
/*  20:    */ import connections.WiredBox;
/*  21:    */ import java.io.StringReader;
/*  22:    */ import java.text.DecimalFormat;
/*  23:    */ import java.util.ArrayList;
/*  24:    */ import java.util.HashMap;
/*  25:    */ import start.Generator;
/*  26:    */ import text.Html;
/*  27:    */ import utils.Mark;
/*  28:    */ 
/*  29:    */ public class BerylVerbTranslator
/*  30:    */   extends AbstractWiredBox
/*  31:    */ {
/*  32:    */   public static final String BERYL_VERB_TRANSLATOR = "BerylVerbTranslator";
/*  33:    */   public static final String PLAY_BY_PLAY_EVENTS = "play by play events";
/*  34:    */   public static final String PLAY_BY_PLAY = "play by play";
/*  35:    */   public static final String EVENT_PREDICTION_PORT = "event predictions port";
/*  36:    */   public static final String STOP_FLAG = "stopped";
/*  37: 40 */   DecimalFormat df = new DecimalFormat("#.##");
/*  38:    */   
/*  39:    */   public BerylVerbTranslator(String name, String PassThroughService)
/*  40:    */   {
/*  41: 43 */     setName(name);
/*  42:    */     
/*  43: 45 */     Connections.getPorts(this).addSignalProcessor(Co57Passthrough.ZMQ_VERB_PORT, "processVerb");
/*  44: 46 */     Connections.getPorts(this).addSignalProcessor(Co57Passthrough.ZMQ_TRACE_PORT, "processTrace");
/*  45:    */     
/*  46: 48 */     Connections.getPorts(this).addSignalProcessor("event predictions port", "processPredictions");
/*  47: 50 */     if (PassThroughService != null) {
/*  48: 51 */       setupBerylConnector(PassThroughService);
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void processPredictions(Object o)
/*  53:    */   {
/*  54: 55 */     BetterSignal signal = BetterSignal.isSignal(o);
/*  55: 56 */     if (signal == null) {
/*  56: 57 */       return;
/*  57:    */     }
/*  58: 58 */     if (!((String)signal.get(0, String.class)).equals("nextEvent")) {
/*  59: 59 */       return;
/*  60:    */     }
/*  61: 60 */     Entity event = (Entity)signal.get(1, Entity.class);
/*  62: 61 */     EventToJSON etj = new EventToJSON(event);
/*  63: 62 */     Mark.say(new Object[] {"Next event prediction: " + event.asString() });
/*  64: 63 */     Mark.say(new Object[] {"To JSON: " + etj });
/*  65:    */   }
/*  66:    */   
/*  67: 67 */   String test = "{\"SR_Contact_Ids\":[-1,-1,-1,-1,-1,-1,-1,-1,0,-1,-1,-1],\"SR_Distribution_Ids\":[1,1,1,0,0,-1,-1,-1,-1,-1,1,1],\"activeids\":[{\"activedescr\":\"object\",\"activeid\":0,\"activetype\":\"object\",\"i0\":114,\"iN\":126,\"j0\":166,\"jN\":177,\"markerR\":0.07504165509901817,\"markerTheta\":2.094074648926872},{\"activedescr\":\"human\",\"activeid\":1,\"activetype\":\"human\",\"i0\":41,\"iN\":209,\"j0\":232,\"jN\":272,\"markerR\":0.1731509457092279,\"markerTheta\":0.3080527810237764,\"microaction\":[{\"None\":0.000226758535642810},{\"crouch\":2.938827576505238e-10},{\"drop\":2.825701256834145e-19},{\"rise\":2.949944195300282e-19},{\"throw\":0.9936601043538563},{\"uthrow\":0.006111015409033069},{\"walk\":2.121407585023658e-06}]},{\"activedescr\":\"human\",\"activeid\":3,\"activetype\":\"human\",\"i0\":56,\"iN\":236,\"j0\":165,\"jN\":208,\"markerR\":0.0,\"markerTheta\":0.0,\"microaction\":[{\"None\":0.0005805112739060012},{\"crouch\":4.653980704509078e-25},{\"drop\":1.812720832796975e-24},{\"rise\":4.287465118015031e-30},{\"throw\":0.01701141852185620},{\"uthrow\":5.516842009470500e-15},{\"walk\":0.9824080702042323}]}],\"dx\":-0.1124999970197678,\"dy\":0.0,\"foaSize\":0.1007812470197678,\"foaid\":3,\"frameid\":571,\"nm\":3,\"refR\":3.141592741012573,\"refTheta\":3.141592741012573}";
/*  68:    */   String penultimateEnglish;
/*  69:    */   
/*  70:    */   public void processTrace(Object o)
/*  71:    */   {
/*  72:    */     try
/*  73:    */     {
/*  74: 71 */       BetterSignal signal = BetterSignal.isSignal(o);
/*  75: 72 */       if (signal == null) {
/*  76: 73 */         return;
/*  77:    */       }
/*  78: 76 */       String json = (String)signal.get(0, String.class);
/*  79: 77 */       Gson gson = new Gson();
/*  80: 78 */       JsonReader reader = new JsonReader(new StringReader(json));
/*  81: 79 */       reader.setLenient(true);
/*  82: 80 */       JsonParser parser = new JsonParser();
/*  83:    */       
/*  84: 82 */       JsonObject object = parser.parse(reader).getAsJsonObject();
/*  85:    */       
/*  86: 84 */       int frameid = object.get("frameid").getAsInt();
/*  87:    */       
/*  88: 86 */       ArrayList<BoundingBox> boundingBoxes = new ArrayList();
/*  89: 87 */       JsonElement activeids_elt = object.get("activeids");
/*  90: 88 */       if (!(activeids_elt instanceof JsonNull))
/*  91:    */       {
/*  92: 89 */         Mark.say(new Object[] {activeids_elt });
/*  93: 90 */         JsonArray objects = activeids_elt.getAsJsonArray();
/*  94: 91 */         for (JsonElement elt : objects)
/*  95:    */         {
/*  96: 92 */           BoundingBox box = (BoundingBox)gson.fromJson(elt.toString(), BoundingBox.class);
/*  97: 93 */           boundingBoxes.add(box);
/*  98:    */         }
/*  99:    */       }
/* 100: 97 */       if (frameid == 810) {
/* 101: 98 */         Mark.say(new Object[] {"Trace Recieved...", object });
/* 102:    */       }
/* 103:107 */       Connections.getPorts(this).transmit("frame stamp", new BetterSignal(new Object[] { "frame stamp", Integer.valueOf(frameid), boundingBoxes }));
/* 104:    */     }
/* 105:    */     catch (Exception e)
/* 106:    */     {
/* 107:110 */       e.printStackTrace();
/* 108:111 */       Mark.err(new Object[] {"JSON problem likely" });
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   public class BoundingBox
/* 113:    */   {
/* 114:116 */     int activeid = -1;
/* 115:118 */     public int i0 = -1;
/* 116:120 */     public int iN = -1;
/* 117:122 */     public int j0 = -1;
/* 118:124 */     public int jN = -1;
/* 119:    */     
/* 120:    */     public BoundingBox() {}
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void processVerb(Object o)
/* 124:    */   {
/* 125:130 */     BetterSignal signal = BetterSignal.isSignal(o);
/* 126:131 */     if (signal == null) {
/* 127:132 */       return;
/* 128:    */     }
/* 129:136 */     String json = (String)signal.get(0, String.class);
/* 130:140 */     if ((json.contains("'command':")) || (json.contains("\"command\":")))
/* 131:    */     {
/* 132:141 */       Gson gson = new Gson();
/* 133:    */       
/* 134:143 */       CommandOutput commandOutput = (CommandOutput)gson.fromJson(json, CommandOutput.class);
/* 135:145 */       if (commandOutput.command.equals("stopped"))
/* 136:    */       {
/* 137:147 */         Mark.say(new Object[] {"Video Stopped Flag Recieved!" });
/* 138:    */         
/* 139:149 */         Connections.getPorts(this).transmit("announce", "Done!");
/* 140:    */       }
/* 141:    */     }
/* 142:    */     else
/* 143:    */     {
/* 144:156 */       Gson gson = new Gson();
/* 145:    */       
/* 146:    */ 
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:162 */       VerbOutput verbOutput = (VerbOutput)gson.fromJson(json, VerbOutput.class);
/* 151:163 */       Entity t = verbOutput.toThing();
/* 152:164 */       int start = verbOutput.start;
/* 153:165 */       int end = verbOutput.end;
/* 154:    */       
/* 155:167 */       BetterSignal output = new BetterSignal(new Object[] { t });
/* 156:168 */       Connections.getPorts(this).transmit(output);
/* 157:    */       
/* 158:    */ 
/* 159:171 */       Connections.getPorts(this).transmit("play by play events", new BetterSignal(new Object[] { "play by play events", t }));
/* 160:172 */       String english = Generator.getGenerator().generate(t);
/* 161:173 */       Mark.say(new Object[] {"English play by play is:", english, "starting", Integer.valueOf(start), "ending", Integer.valueOf(end) });
/* 162:    */       
/* 163:    */ 
/* 164:    */ 
/* 165:177 */       english = Html.p(english);
/* 166:178 */       if (!english.equals(this.penultimateEnglish))
/* 167:    */       {
/* 168:179 */         this.penultimateEnglish = english;
/* 169:180 */         Connections.getPorts(this).transmit("announce", english);
/* 170:181 */         Connections.getPorts(this).transmit("play by play", new BetterSignal(new Object[] { "play by play", english, Integer.valueOf(start), Integer.valueOf(end) }));
/* 171:    */       }
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setupBerylConnector(String service)
/* 176:    */   {
/* 177:    */     try
/* 178:    */     {
/* 179:191 */       WiredBox server = Connections.subscribe(service);
/* 180:    */       
/* 181:    */ 
/* 182:194 */       Connections.wire(Co57Passthrough.ZMQ_VERB_PORT, server, Co57Passthrough.ZMQ_VERB_PORT, this);
/* 183:195 */       Connections.wire(Co57Passthrough.ZMQ_TRACE_PORT, server, Co57Passthrough.ZMQ_TRACE_PORT, this);
/* 184:    */     }
/* 185:    */     catch (Connections.NetWireException e)
/* 186:    */     {
/* 187:198 */       e.printStackTrace();
/* 188:199 */       Mark.say(new Object[] {"Failed to connect to Co57 ZMQ passthrough box." });
/* 189:    */     }
/* 190:    */   }
/* 191:    */   
/* 192:    */   public class CommandOutput
/* 193:    */   {
/* 194:204 */     public String command = "command";
/* 195:    */     
/* 196:    */     public CommandOutput() {}
/* 197:    */   }
/* 198:    */   
/* 199:209 */   public static HashMap<Integer, Entity> ThingCache = new HashMap();
/* 200:211 */   public static HashMap<Entity, Integer> BerylIDCache = new HashMap();
/* 201:    */   
/* 202:    */   public class VerbOutput
/* 203:    */   {
/* 204:215 */     public int end = -1;
/* 205:217 */     public String name = "verb";
/* 206:219 */     public int start = -1;
/* 207:221 */     public int subject = -1;
/* 208:223 */     public String subject_type = "subject";
/* 209:225 */     public int direct = -1;
/* 210:227 */     public String direct_type = "direct";
/* 211:229 */     public int indirect = -1;
/* 212:231 */     public String indirect_type = "indirect";
/* 213:    */     
/* 214:    */     public VerbOutput() {}
/* 215:    */     
/* 216:    */     public Entity toThing()
/* 217:    */     {
/* 218:237 */       Relation r = null;
/* 219:238 */       Entity subject_t = null;
/* 220:239 */       Entity direct_t = null;
/* 221:240 */       Entity indirect_t = null;
/* 222:241 */       if ((this.start > 0) && (this.end > 0) && (this.subject > -1) && (this.name.length() > 0))
/* 223:    */       {
/* 224:243 */         if (BerylVerbTranslator.ThingCache.containsKey(Integer.valueOf(this.subject)))
/* 225:    */         {
/* 226:244 */           subject_t = (Entity)BerylVerbTranslator.ThingCache.get(Integer.valueOf(this.subject));
/* 227:    */         }
/* 228:    */         else
/* 229:    */         {
/* 230:247 */           subject_t = new Entity(this.subject_type);
/* 231:248 */           decorate(subject_t, this.subject);
/* 232:249 */           BerylVerbTranslator.ThingCache.put(Integer.valueOf(this.subject), subject_t);
/* 233:250 */           BerylVerbTranslator.BerylIDCache.put(subject_t, Integer.valueOf(this.subject));
/* 234:    */         }
/* 235:253 */         if ((this.direct > -1) && (this.direct_type != null)) {
/* 236:255 */           if (BerylVerbTranslator.ThingCache.containsKey(Integer.valueOf(this.direct)))
/* 237:    */           {
/* 238:256 */             direct_t = (Entity)BerylVerbTranslator.ThingCache.get(Integer.valueOf(this.direct));
/* 239:    */           }
/* 240:    */           else
/* 241:    */           {
/* 242:259 */             direct_t = new Entity(this.direct_type);
/* 243:    */             
/* 244:    */ 
/* 245:    */ 
/* 246:    */ 
/* 247:    */ 
/* 248:    */ 
/* 249:    */ 
/* 250:267 */             decorate(direct_t, this.direct);
/* 251:268 */             BerylVerbTranslator.ThingCache.put(Integer.valueOf(this.direct), direct_t);
/* 252:269 */             BerylVerbTranslator.BerylIDCache.put(direct_t, Integer.valueOf(this.direct));
/* 253:    */           }
/* 254:    */         }
/* 255:273 */         if ((this.indirect > -1) && (this.indirect_type != null)) {
/* 256:274 */           if (BerylVerbTranslator.ThingCache.containsKey(Integer.valueOf(this.indirect)))
/* 257:    */           {
/* 258:275 */             indirect_t = (Entity)BerylVerbTranslator.ThingCache.get(Integer.valueOf(this.indirect));
/* 259:    */           }
/* 260:    */           else
/* 261:    */           {
/* 262:278 */             indirect_t = new Entity(this.indirect_type);
/* 263:279 */             decorate(indirect_t, this.indirect);
/* 264:280 */             BerylVerbTranslator.ThingCache.put(Integer.valueOf(this.indirect), indirect_t);
/* 265:281 */             BerylVerbTranslator.BerylIDCache.put(indirect_t, Integer.valueOf(this.indirect));
/* 266:    */           }
/* 267:    */         }
/* 268:284 */         this.name = translateVerb(this.name);
/* 269:285 */         r = RFactory.makeRoleFrameRelation(subject_t, this.name, direct_t);
/* 270:286 */         if (indirect_t != null) {
/* 271:288 */           if (this.name.equals("give")) {
/* 272:290 */             RFactory.addRoleFrameTo(indirect_t, r);
/* 273:293 */           } else if (this.name.equals("take")) {
/* 274:295 */             RFactory.addRoleFrameFrom(indirect_t, r);
/* 275:    */           }
/* 276:    */         }
/* 277:    */       }
/* 278:300 */       return r;
/* 279:    */     }
/* 280:    */     
/* 281:    */     private void decorate(Entity t, int n)
/* 282:    */     {
/* 283:304 */       if (n == 1) {
/* 284:305 */         t.addFeature("first");
/* 285:307 */       } else if (n == 2) {
/* 286:308 */         t.addFeature("second");
/* 287:310 */       } else if (n == 3) {
/* 288:311 */         t.addFeature("third");
/* 289:313 */       } else if (n == 4) {
/* 290:314 */         t.addFeature("fourth");
/* 291:316 */       } else if (n == 5) {
/* 292:317 */         t.addFeature("fifth");
/* 293:    */       }
/* 294:319 */       if (n > 0) {
/* 295:320 */         t.addDeterminer("definite");
/* 296:    */       } else {
/* 297:323 */         t.addDeterminer("indefinite");
/* 298:    */       }
/* 299:    */     }
/* 300:    */     
/* 301:    */     private String translateVerb(String name)
/* 302:    */     {
/* 303:328 */       if (name == null)
/* 304:    */       {
/* 305:329 */         Mark.err(new Object[] {"No name given to translateName" });
/* 306:330 */         return null;
/* 307:    */       }
/* 308:332 */       if (name.equals("putdown")) {
/* 309:333 */         return "put_down";
/* 310:    */       }
/* 311:335 */       if (name.equals("pickup2")) {
/* 312:336 */         return "pick_up";
/* 313:    */       }
/* 314:338 */       if (name.equals("pickup")) {
/* 315:339 */         return "pick_up";
/* 316:    */       }
/* 317:341 */       return name;
/* 318:    */     }
/* 319:    */   }
/* 320:    */   
/* 321:    */   public static class EventToJSON
/* 322:    */   {
/* 323:347 */     public String name = "";
/* 324:348 */     public int subject = -1;
/* 325:349 */     public int direct = -1;
/* 326:350 */     public int indirect = -1;
/* 327:    */     
/* 328:    */     public EventToJSON(Entity event)
/* 329:    */     {
/* 330:353 */       if (event.relationP())
/* 331:    */       {
/* 332:354 */         this.name = event.getType();
/* 333:355 */         Entity subj = event.getSubject();
/* 334:356 */         for (Integer i : BerylVerbTranslator.ThingCache.keySet()) {
/* 335:357 */           if (((Entity)BerylVerbTranslator.ThingCache.get(i)).equals(subj)) {
/* 336:358 */             this.subject = i.intValue();
/* 337:    */           }
/* 338:    */         }
/* 339:361 */         if ((event.getObject().getType().equals("roles")) && (event.getObject().sequenceP()))
/* 340:    */         {
/* 341:362 */           Sequence roles = (Sequence)event.getObject();
/* 342:363 */           for (int i = 0; i < roles.getNumberOfChildren(); i++)
/* 343:    */           {
/* 344:364 */             Entity t = roles.getElement(i);
/* 345:365 */             if ((t.functionP()) && (t.getType().equals("object")))
/* 346:    */             {
/* 347:366 */               Entity obj = t.getSubject();
/* 348:367 */               for (Integer index : BerylVerbTranslator.ThingCache.keySet()) {
/* 349:368 */                 if (((Entity)BerylVerbTranslator.ThingCache.get(index)).equals(obj)) {
/* 350:369 */                   this.direct = index.intValue();
/* 351:    */                 }
/* 352:    */               }
/* 353:    */             }
/* 354:373 */             if ((t.functionP()) && (t.getType().equals("from")))
/* 355:    */             {
/* 356:374 */               Entity obj = t.getSubject();
/* 357:375 */               for (Integer index : BerylVerbTranslator.ThingCache.keySet()) {
/* 358:376 */                 if (((Entity)BerylVerbTranslator.ThingCache.get(index)).equals(obj)) {
/* 359:377 */                   this.indirect = index.intValue();
/* 360:    */                 }
/* 361:    */               }
/* 362:    */             }
/* 363:381 */             if ((t.functionP()) && (t.getType().equals("to")))
/* 364:    */             {
/* 365:382 */               Entity obj = t.getSubject();
/* 366:383 */               for (Integer index : BerylVerbTranslator.ThingCache.keySet()) {
/* 367:384 */                 if (((Entity)BerylVerbTranslator.ThingCache.get(index)).equals(obj)) {
/* 368:385 */                   this.indirect = index.intValue();
/* 369:    */                 }
/* 370:    */               }
/* 371:    */             }
/* 372:    */           }
/* 373:    */         }
/* 374:    */       }
/* 375:    */     }
/* 376:    */     
/* 377:    */     public String toString()
/* 378:    */     {
/* 379:396 */       String out = "{";
/* 380:397 */       if (this.name != null)
/* 381:    */       {
/* 382:398 */         out = out + "\"name\":\"" + this.name + "\"";
/* 383:399 */         if (this.subject >= 0) {
/* 384:400 */           out = out + ",\"subject\":\"" + this.subject + "\"";
/* 385:    */         }
/* 386:402 */         if (this.direct >= 0) {
/* 387:403 */           out = out + ",\"direct\":\"" + this.direct + "\"";
/* 388:    */         }
/* 389:405 */         if (this.indirect >= 0) {
/* 390:406 */           out = out + ",\"indirect\":\"" + this.indirect + "\"";
/* 391:    */         }
/* 392:    */       }
/* 393:409 */       out = out + "}";
/* 394:410 */       return out;
/* 395:    */     }
/* 396:    */   }
/* 397:    */   
/* 398:414 */   public static String trace_example = "{\"SR_Contact_Ids\":[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],\"SR_Distribution_Ids\":[-1,-1,-1,-1,-1,0,-1,-1,-1,-1,-1,-1],\"activeids\":[{\"activedescr\":\"object\",\"activeid\":0,\"activetype\":\"object\",\"i0\":152,\"iN\":159,\"j0\":29,\"jN\":34,\"markerR\":0.6365312246857966,\"markerTheta\":3.100734901014173},{\"activedescr\":\"human\",\"activeid\":1,\"activetype\":\"human\",\"i0\":55,\"iN\":281,\"j0\":322,\"jN\":376,\"markerR\":0.0,\"markerTheta\":0.0,\"microaction\":[{\"None\":9.970866467304883e-27},{\"crouch\":5.930341134483627e-49},{\"drop\":5.223783507012783e-76},{\"rise\":9.277858242961297e-74},{\"throw\":3.364195127370787e-60},{\"uthrow\":1.195605665763975e-36},{\"walk\":1.0}]}],\"dx\":-0.1239999979734421,\"dy\":-0.008000000379979610,\"foaSize\":0.1017000004649162,\"foaid\":1,\"frameid\":105,\"nm\":2,\"refR\":-3.077165842056274,\"refTheta\":-3.077165842056274}";
/* 399:    */   
/* 400:    */   public static void main(String[] args)
/* 401:    */   {
/* 402:417 */     String json_test = "{\"SR_Distribution_Ids\":[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1],\"activeids\":null,\"dx\":0.0,\"dy\":0.0,\"foaSize\":0.0,\"foaid\":-1,\"frameid\":2295,\"nm\":0,\"refR\":0.0,\"refTheta\":0.0}";
/* 403:418 */     BerylVerbTranslator c = new BerylVerbTranslator("test", null);
/* 404:419 */     c.processTrace(new BetterSignal(new Object[] { json_test }));
/* 405:    */   }
/* 406:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     Co57.BerylVerbTranslator
 * JD-Core Version:    0.7.0.1
 */