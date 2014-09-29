/*   1:    */ package hibaAwad;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import connections.AbstractWiredBox;
/*   7:    */ import connections.Connections;
/*   8:    */ import connections.Ports;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Collection;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.Vector;
/*  13:    */ import matchers.StandardMatcher;
/*  14:    */ import minilisp.LList;
/*  15:    */ import storyProcessor.ReflectionAnalysis;
/*  16:    */ import storyProcessor.ReflectionDescription;
/*  17:    */ import storyProcessor.StoryProcessor;
/*  18:    */ import text.Html;
/*  19:    */ import utils.Mark;
/*  20:    */ import utils.PairOfEntities;
/*  21:    */ import utils.StoryMethods;
/*  22:    */ 
/*  23:    */ public class EastWestExpert
/*  24:    */   extends AbstractWiredBox
/*  25:    */ {
/*  26:    */   Entity test;
/*  27:    */   Entity cause;
/*  28:    */   Entity cause2;
/*  29:    */   Entity result;
/*  30:    */   ReflectionAnalysis analysis;
/*  31: 22 */   public ArrayList<Entity> causes = new ArrayList();
/*  32: 23 */   public ArrayList<String> untranslatedCauses = new ArrayList();
/*  33: 24 */   public Vector<Sequence> stories = new Vector();
/*  34:    */   public static final String FINAL_INFERENCES = "final inferances port";
/*  35:    */   public static final String FINAL_STORY = "final story port";
/*  36:    */   public static final String COHERENCE_DATA = "coherence data port";
/*  37:    */   public static final String COHERENCE_TEXT = "coherence text port";
/*  38:    */   public static final String COHERENCE_LABEL = "coherence label port";
/*  39:    */   public static final String COHERENCE_AXIS = "coherence axis label port";
/*  40:    */   public static final String REFLECTION = "reflection port";
/*  41:    */   public static final String CAUSAL_ANALYSIS = "causal analysis";
/*  42:    */   public static final String FULL_STORY_INPUT_PORT = "complete story input port";
/*  43:    */   public static final String MY_OUTPUT_PORT = "my output port";
/*  44:    */   
/*  45:    */   public EastWestExpert()
/*  46:    */   {
/*  47: 42 */     setName("My story processor");
/*  48: 43 */     Connections.getPorts(this).addSignalProcessor("reflection port", "processReflections");
/*  49: 44 */     Connections.getPorts(this).addSignalProcessor("complete story input port", "processStory");
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void processStory(Object signal)
/*  53:    */   {
/*  54: 85 */     Mark.say(
/*  55:    */     
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65:    */ 
/*  66:    */ 
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:119 */       new Object[] { "Receiving complete story analysis!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" });BetterSignal s = (BetterSignal)signal;Sequence story = (Sequence)s.get(0, Sequence.class);Sequence explicitElements = (Sequence)s.get(1, Sequence.class);Sequence inferences = (Sequence)s.get(2, Sequence.class);Sequence concepts = (Sequence)s.get(3, Sequence.class);Mark.say(new Object[] { "Calling processCoherence" });processCoherence(story);Connections.getPorts(this).transmit("coherence label port", StoryProcessor.getTitle(story));Connections.getPorts(this).transmit("causal analysis", Html.bold(StoryProcessor.getTitle(story)) + "\n");
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static boolean evaluateCause(Sequence story, ArrayList<ReflectionDescription> reflections, Entity cause, Entity result)
/*  92:    */   {
/*  93:131 */     int answer = 0;
/*  94:    */     
/*  95:    */ 
/*  96:134 */     int distance = -1;
/*  97:135 */     int distance2 = -1;
/*  98:    */     
/*  99:    */ 
/* 100:    */ 
/* 101:139 */     Vector<Entity> dist = StoryMethods.distance(cause, result, story);
/* 102:140 */     if (dist != null) {
/* 103:141 */       distance = dist.size();
/* 104:    */     }
/* 105:142 */     dist = StoryMethods.distance(result, cause, story);
/* 106:143 */     if (dist != null) {
/* 107:144 */       distance2 = dist.size();
/* 108:    */     }
/* 109:146 */     Mark.say(new Object[] {Integer.valueOf(distance) });
/* 110:147 */     if ((distance != -1) || (distance2 != -1))
/* 111:    */     {
/* 112:148 */       answer = 1;
/* 113:    */     }
/* 114:    */     else
/* 115:    */     {
/* 116:152 */       Mark.say(new Object[] {"concepts" });
/* 117:    */       Iterator localIterator2;
/* 118:153 */       for (Iterator localIterator1 = reflections.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 119:    */       {
/* 120:153 */         ReflectionDescription rd = (ReflectionDescription)localIterator1.next();
/* 121:    */         
/* 122:155 */         localIterator2 = rd.getStoryElementsInvolved().getElements().iterator(); continue;Entity t = (Entity)localIterator2.next();
/* 123:    */         
/* 124:    */ 
/* 125:158 */         LList<PairOfEntities> bindings = StandardMatcher.getBasicMatcher().match(cause, t);
/* 126:160 */         if (bindings != null) {
/* 127:161 */           answer = 1;
/* 128:    */         }
/* 129:    */       }
/* 130:    */     }
/* 131:166 */     if (answer > 0) {
/* 132:167 */       return true;
/* 133:    */     }
/* 134:168 */     return false;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public static boolean existStory(Entity element, Sequence story)
/* 138:    */   {
/* 139:172 */     boolean found = false;
/* 140:173 */     for (Entity storyElement : story.getElements())
/* 141:    */     {
/* 142:174 */       LList<PairOfEntities> bindings = StandardMatcher.getBasicMatcher().match(storyElement, element);
/* 143:175 */       if (bindings != null) {
/* 144:176 */         found = true;
/* 145:    */       }
/* 146:    */     }
/* 147:179 */     return found;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void processReflections(Object signal)
/* 151:    */   {
/* 152:183 */     this.analysis = ((ReflectionAnalysis)signal);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void processCoherence(Object signal)
/* 156:    */   {
/* 157:234 */     Mark.say(
/* 158:    */     
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
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
/* 173:    */ 
/* 174:    */ 
/* 175:    */ 
/* 176:    */ 
/* 177:    */ 
/* 178:    */ 
/* 179:    */ 
/* 180:    */ 
/* 181:    */ 
/* 182:    */ 
/* 183:    */ 
/* 184:    */ 
/* 185:    */ 
/* 186:    */ 
/* 187:    */ 
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:    */ 
/* 194:    */ 
/* 195:    */ 
/* 196:    */ 
/* 197:    */ 
/* 198:    */ 
/* 199:    */ 
/* 200:    */ 
/* 201:    */ 
/* 202:    */ 
/* 203:    */ 
/* 204:    */ 
/* 205:    */ 
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:    */ 
/* 211:    */ 
/* 212:    */ 
/* 213:    */ 
/* 214:    */ 
/* 215:    */ 
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:    */ 
/* 221:    */ 
/* 222:    */ 
/* 223:    */ 
/* 224:    */ 
/* 225:302 */       new Object[] { "Entering process coherence" });
/* 226:235 */     if ((signal instanceof Entity))
/* 227:    */     {
/* 228:236 */       Entity t = (Entity)signal;
/* 229:237 */       if (t.sequenceP())
/* 230:    */       {
/* 231:238 */         Sequence s = (Sequence)t;
/* 232:239 */         Mark.say(new Object[] {"Story received:" });
/* 233:240 */         int size = getTotalElements(s);
/* 234:    */         
/* 235:242 */         Vector<Entity> uncausedEvents = getUncausedEvents(s);
/* 236:243 */         Vector<Vector<Entity>> chains = getCausalChains(uncausedEvents, s);
/* 237:    */         
/* 238:245 */         double longestChain = 0.0D;
/* 239:247 */         for (int i = 0; i < chains.size(); i++)
/* 240:    */         {
/* 241:248 */           Vector<Entity> chain = (Vector)chains.get(i);
/* 242:249 */           if (chain.size() > longestChain) {
/* 243:250 */             longestChain = chain.size();
/* 244:    */           }
/* 245:    */           Entity localEntity1;
/* 246:253 */           for (Iterator localIterator = chain.iterator(); localIterator.hasNext(); localEntity1 = (Entity)localIterator.next()) {}
/* 247:    */         }
/* 248:258 */         double weightedLongestChain = longestChain / size;
/* 249:    */         
/* 250:260 */         double weightedCausedEvent = (size - uncausedEvents.size()) / size;
/* 251:    */         
/* 252:262 */         double weightedChains = chains.size() / size;
/* 253:    */         
/* 254:    */ 
/* 255:    */ 
/* 256:266 */         double[] data = { longestChain, size - uncausedEvents.size(), chains.size() };
/* 257:    */         
/* 258:    */ 
/* 259:269 */         Connections.getPorts(this).transmit("coherence data port", data);
/* 260:    */         
/* 261:    */ 
/* 262:    */ 
/* 263:273 */         Connections.getPorts(this).transmit("coherence text port", "Story Title: " + StoryProcessor.getTitle(s));
/* 264:274 */         Connections.getPorts(this).transmit("coherence text port", "Number of chains: " + chains.size());
/* 265:275 */         Connections.getPorts(this).transmit("coherence text port", "Length of longest chain: " + longestChain);
/* 266:276 */         Connections.getPorts(this).transmit("coherence text port", "Number of caused event: " + (size - uncausedEvents.size()));
/* 267:    */       }
/* 268:    */     }
/* 269:    */   }
/* 270:    */   
/* 271:    */   public Vector<Vector<Entity>> getCausalChains(Vector<Entity> uncausedEvents, Sequence story)
/* 272:    */   {
/* 273:315 */     Vector<Vector<Entity>> chains = new Vector();
/* 274:    */     Vector<Vector<Entity>> queue;
/* 275:316 */     for (Iterator localIterator1 = uncausedEvents.iterator(); localIterator1.hasNext(); !queue.isEmpty())
/* 276:    */     {
/* 277:316 */       Entity event = (Entity)localIterator1.next();
/* 278:317 */       queue = new Vector();
/* 279:    */       
/* 280:    */ 
/* 281:320 */       Vector<Entity> initialPath = new Vector();
/* 282:321 */       initialPath.add(event);
/* 283:322 */       queue.add(initialPath);
/* 284:    */       
/* 285:324 */       continue;
/* 286:    */       Vector localVector;
/* 287:326 */       for (Iterator localIterator2 = queue.iterator(); localIterator2.hasNext(); localVector = (Vector)localIterator2.next()) {}
/* 288:330 */       Object path = (Vector)queue.firstElement();
/* 289:331 */       queue.remove(0);
/* 290:332 */       Entity lastElement = (Entity)((Vector)path).lastElement();
/* 291:    */       
/* 292:    */ 
/* 293:    */ 
/* 294:    */ 
/* 295:    */ 
/* 296:    */ 
/* 297:339 */       boolean seperate = true;
/* 298:340 */       for (Entity element : story.getElements()) {
/* 299:341 */         if (element.relationP("cause"))
/* 300:    */         {
/* 301:342 */           Vector<Entity> antecedents = element.getSubject()
/* 302:343 */             .getElements();
/* 303:344 */           if ((antecedents.contains(lastElement)) && 
/* 304:345 */             (element.getObject() != null))
/* 305:    */           {
/* 306:346 */             Entity object = element.getObject();
/* 307:348 */             if (!((Vector)path).contains(object))
/* 308:    */             {
/* 309:351 */               seperate = false;
/* 310:352 */               Vector<Entity> newPath = new Vector();
/* 311:353 */               newPath.addAll((Collection)path);
/* 312:354 */               newPath.add(element.getObject());
/* 313:355 */               queue.add(newPath);
/* 314:    */             }
/* 315:    */           }
/* 316:    */         }
/* 317:    */       }
/* 318:363 */       if ((seperate) && 
/* 319:364 */         (!chains.contains(path))) {
/* 320:366 */         chains.add(path);
/* 321:    */       }
/* 322:    */     }
/* 323:373 */     return chains;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public Vector<Entity> getUncausedEvents(Sequence story)
/* 327:    */   {
/* 328:383 */     Vector<Entity> causedEvents = new Vector();
/* 329:    */     Entity antecedents;
/* 330:384 */     for (Entity element : story.getElements()) {
/* 331:385 */       if (element.relationP("cause"))
/* 332:    */       {
/* 333:386 */         antecedents = element.getObject();
/* 334:387 */         causedEvents.add(antecedents);
/* 335:    */       }
/* 336:    */     }
/* 337:391 */     Vector<Entity> uncausedEvents = new Vector();
/* 338:393 */     for (Entity element : story.getElements()) {
/* 339:394 */       if ((!element.relationP("cause")) && 
/* 340:395 */         (!causedEvents.contains(element)) && 
/* 341:396 */         (!uncausedEvents.contains(element))) {
/* 342:397 */         uncausedEvents.add(element);
/* 343:    */       }
/* 344:    */     }
/* 345:402 */     return uncausedEvents;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public int getTotalElements(Sequence story)
/* 349:    */   {
/* 350:406 */     int count = 0;
/* 351:407 */     for (Entity element : story.getElements()) {
/* 352:408 */       if (!element.relationP("cause")) {
/* 353:409 */         count++;
/* 354:    */       }
/* 355:    */     }
/* 356:412 */     return count;
/* 357:    */   }
/* 358:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     hibaAwad.EastWestExpert
 * JD-Core Version:    0.7.0.1
 */