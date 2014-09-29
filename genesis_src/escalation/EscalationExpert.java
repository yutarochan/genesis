/*   1:    */ package escalation;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Bundle;
/*   5:    */ import bridge.reps.entities.Entity;
/*   6:    */ import bridge.reps.entities.Relation;
/*   7:    */ import bridge.reps.entities.Sequence;
/*   8:    */ import bridge.reps.entities.Thread;
/*   9:    */ import connections.AbstractWiredBox;
/*  10:    */ import connections.Connections;
/*  11:    */ import connections.Ports;
/*  12:    */ import java.io.BufferedReader;
/*  13:    */ import java.io.StringReader;
/*  14:    */ import java.text.DecimalFormat;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.Iterator;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Vector;
/*  19:    */ import links.words.BundleGenerator;
/*  20:    */ import storyProcessor.ReflectionAnalysis;
/*  21:    */ import storyProcessor.ReflectionDescription;
/*  22:    */ import text.Html;
/*  23:    */ import text.Punctuator;
/*  24:    */ import utils.Mark;
/*  25:    */ import utils.TextIO;
/*  26:    */ 
/*  27:    */ public class EscalationExpert
/*  28:    */   extends AbstractWiredBox
/*  29:    */ {
/*  30: 32 */   boolean debug = false;
/*  31: 34 */   private ArrayList<Goldstein> goldsteinOffences = new ArrayList();
/*  32: 36 */   private ArrayList<Goldstein> weisOffences = new ArrayList();
/*  33: 38 */   private DecimalFormat onePlace = new DecimalFormat("0.0");
/*  34:    */   
/*  35:    */   public EscalationExpert()
/*  36:    */   {
/*  37: 41 */     setName("Escalation expert");
/*  38: 42 */     initializeGoldstein();
/*  39: 43 */     initializeWeis();
/*  40: 44 */     Connections.getPorts(this).addSignalProcessor("process");
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void process(Object o)
/*  44:    */   {
/*  45: 49 */     if (!(o instanceof ReflectionAnalysis)) {
/*  46: 50 */       return;
/*  47:    */     }
/*  48: 53 */     ReflectionAnalysis analysis = (ReflectionAnalysis)o;
/*  49: 54 */     Sequence story = analysis.getStory();
/*  50: 55 */     for (ReflectionDescription reflection : analysis.getReflectionDescriptions())
/*  51:    */     {
/*  52: 56 */       String name = Punctuator.conditionName(reflection.getName());
/*  53: 57 */       Mark.say(new Object[] {Boolean.valueOf(this.debug), "Escalation expert received", name });
/*  54: 58 */       if ((!contains(name, "revenge")) && (!contains(name, "lesson")) && (!contains(name, "even")))
/*  55:    */       {
/*  56: 59 */         Mark.say(new Object[] {Boolean.valueOf(this.debug), "Ignoring", name });
/*  57:    */       }
/*  58:    */       else
/*  59:    */       {
/*  60: 62 */         Mark.say(new Object[] {Boolean.valueOf(this.debug), "Continuing using", reflection.getName(), Integer.valueOf(reflection.getStoryElementsInvolved().getElements().size()), "elements" });
/*  61: 63 */         Entity instigator = null;
/*  62: 64 */         Entity victim = null;
/*  63: 65 */         Relation initialHarm = null;
/*  64: 66 */         Relation reverseHarm = null;
/*  65: 67 */         for (Entity t : reflection.getStoryElementsInvolved().getElements())
/*  66:    */         {
/*  67: 68 */           Mark.say(new Object[] {Boolean.valueOf(this.debug), "Instigator", instigator != null ? instigator.asString() : "null", "; next element is", t.asString() });
/*  68: 69 */           if ((instigator == null) && (t.relationP("harm")))
/*  69:    */           {
/*  70: 70 */             Mark.say(new Object[] {Boolean.valueOf(this.debug), "Looking at harm:", t.asStringWithIndexes() });
/*  71: 71 */             instigator = t.getSubject();
/*  72: 72 */             victim = t.getObject();
/*  73: 73 */             initialHarm = (Relation)t;
/*  74:    */           }
/*  75: 78 */           else if ((t.relationP("harm")) && (instigator == getObject(t)))
/*  76:    */           {
/*  77: 79 */             Mark.say(new Object[] {Boolean.valueOf(this.debug), "Looking at reverse harm:", t.asStringWithIndexes() });
/*  78: 80 */             reverseHarm = (Relation)t;
/*  79:    */           }
/*  80:    */         }
/*  81: 83 */         if ((initialHarm != null) && (reverseHarm != null))
/*  82:    */         {
/*  83: 84 */           Mark.say(new Object[] {Boolean.valueOf(this.debug), "Initial harm", initialHarm.asString() });
/*  84: 85 */           Mark.say(new Object[] {Boolean.valueOf(this.debug), "Consequent harm", reverseHarm.asString() });
/*  85: 86 */           processTheStory(name, story, initialHarm, reverseHarm);
/*  86:    */         }
/*  87:    */       }
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   private Entity getObject(Entity t)
/*  92:    */   {
/*  93: 94 */     if (t.getObject().sequenceP("roles")) {
/*  94: 95 */       for (Entity e : t.getObject().getElements()) {
/*  95: 96 */         if (e.functionP("object")) {
/*  96: 97 */           return e.getSubject();
/*  97:    */         }
/*  98:    */       }
/*  99:    */     }
/* 100:101 */     return null;
/* 101:    */   }
/* 102:    */   
/* 103:    */   private boolean contains(String name, String string)
/* 104:    */   {
/* 105:105 */     if (name.toLowerCase().contains(string.toLowerCase())) {
/* 106:106 */       return true;
/* 107:    */     }
/* 108:108 */     return false;
/* 109:    */   }
/* 110:    */   
/* 111:    */   private void processTheStory(String name, Sequence story, Relation initialHarm, Relation reverseHarm)
/* 112:    */   {
/* 113:112 */     Entity initialOffence = null;
/* 114:113 */     Entity reverseOffence = null;
/* 115:114 */     Mark.say(new Object[] {Boolean.valueOf(this.debug), "Processing the story " + story.getElements().size() });
/* 116:115 */     for (Entity t : story.getElements()) {
/* 117:116 */       if (t.relationP()) {
/* 118:120 */         if (t.isA("cause")) {
/* 119:121 */           if (t.getObject() == initialHarm)
/* 120:    */           {
/* 121:122 */             Mark.say(new Object[] {Boolean.valueOf(this.debug), "Found cause", t.asStringWithIndexes(), initialHarm.asStringWithIndexes() });
/* 122:123 */             initialOffence = backwardChain(t.getSubject().getElements(), story.getElements(), new ArrayList());
/* 123:124 */             if (initialOffence != null) {
/* 124:125 */               Mark.say(new Object[] {Boolean.valueOf(this.debug), "Discovered initial cause", initialOffence.asStringWithIndexes() });
/* 125:    */             }
/* 126:    */           }
/* 127:129 */           else if (t.getObject() == reverseHarm)
/* 128:    */           {
/* 129:130 */             Mark.say(new Object[] {Boolean.valueOf(this.debug), "Found result", t.asStringWithIndexes(), reverseHarm.asStringWithIndexes() });
/* 130:131 */             reverseOffence = backwardChain(t.getSubject().getElements(), story.getElements(), new ArrayList());
/* 131:132 */             if ((reverseOffence != null) && (reverseOffence != initialOffence))
/* 132:    */             {
/* 133:133 */               Mark.say(new Object[] {Boolean.valueOf(this.debug), "Discovered result cause", reverseOffence.asStringWithIndexes() });
/* 134:134 */               break;
/* 135:    */             }
/* 136:    */           }
/* 137:    */         }
/* 138:    */       }
/* 139:    */     }
/* 140:140 */     if (initialOffence == null) {
/* 141:141 */       Mark.say(new Object[] {Boolean.valueOf(this.debug), "Unable to find initial offence tracking back from", initialHarm.asString() });
/* 142:    */     }
/* 143:143 */     if (reverseOffence == null) {
/* 144:144 */       Mark.say(new Object[] {Boolean.valueOf(this.debug), "Unable to find retaliatory offence tracking back from", reverseHarm.asString() });
/* 145:    */     }
/* 146:146 */     if ((initialOffence != null) && (reverseOffence != null))
/* 147:    */     {
/* 148:147 */       Goldstein gInitial = getOffence(initialOffence);
/* 149:148 */       Goldstein gReverse = getOffence(reverseOffence);
/* 150:149 */       if ((gInitial != null) && (gReverse != null))
/* 151:    */       {
/* 152:150 */         String report = getExplanation(gInitial, initialOffence, gReverse, reverseOffence);
/* 153:151 */         BetterSignal message = new BetterSignal(new Object[] { "Concept analysis", Html.p(report) });
/* 154:152 */         Connections.getPorts(this).transmit(message);
/* 155:    */       }
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   private String getExplanation(Goldstein gInitial, Entity initialOffence, Goldstein gReverse, Entity reverseOffence)
/* 160:    */   {
/* 161:160 */     double initialValue = gInitial.getScore();
/* 162:161 */     double reverseValue = gReverse.getScore();
/* 163:162 */     String report = "The response to " + gInitial.getVerb();
/* 164:163 */     if (!gInitial.getVerb().equals(initialOffence.getType())) {
/* 165:164 */       report = report + " (" + initialOffence.getType() + ", " + bundleOverlap(initialOffence.getBundle(), gInitial.getBundle()) + ")";
/* 166:    */     }
/* 167:166 */     report = report + " with " + gReverse.getVerb();
/* 168:167 */     if (!gReverse.getVerb().equals(reverseOffence.getType())) {
/* 169:168 */       report = report + " (" + reverseOffence.getType() + ", " + bundleOverlap(reverseOffence.getBundle(), gReverse.getBundle()) + ")";
/* 170:    */     }
/* 171:170 */     double delta = initialValue - reverseValue;
/* 172:171 */     double threshold = 1.0D;
/* 173:172 */     Mark.say(new Object[] {Boolean.valueOf(this.debug), "Numbers are", Double.valueOf(initialValue), Double.valueOf(reverseValue), Double.valueOf(delta) });
/* 174:    */     
/* 175:174 */     report = report + " gives a Goldstein delta of " + this.onePlace.format(delta);
/* 176:175 */     report = report + " (" + this.onePlace.format(initialValue);
/* 177:176 */     report = report + ", " + this.onePlace.format(reverseValue);
/* 178:177 */     report = report + "); using a threshold of " + this.onePlace.format(threshold) + ", it looks like ";
/* 179:178 */     if (delta > threshold) {
/* 180:179 */       report = report + "an escalation.";
/* 181:181 */     } else if (delta < -threshold) {
/* 182:182 */       report = report + "a de-escalation.";
/* 183:    */     } else {
/* 184:185 */       report = report + "a tit-for-tat.";
/* 185:    */     }
/* 186:187 */     return report;
/* 187:    */   }
/* 188:    */   
/* 189:    */   private Goldstein getOffence(Entity s)
/* 190:    */   {
/* 191:191 */     ArrayList<Goldstein> offenses = this.goldsteinOffences;
/* 192:192 */     Bundle wordBundle = BundleGenerator.getBundle(s.getType());
/* 193:193 */     int bestOverlap = -1;
/* 194:194 */     Goldstein bestPackage = null;
/* 195:195 */     for (Goldstein offence : offenses)
/* 196:    */     {
/* 197:196 */       int d = bundleOverlap(wordBundle, offence.getBundle());
/* 198:197 */       if (s.getType().equalsIgnoreCase(offence.getVerb()))
/* 199:    */       {
/* 200:198 */         bestPackage = offence;
/* 201:199 */         bestOverlap = d;
/* 202:200 */         break;
/* 203:    */       }
/* 204:202 */       if (d >= bestOverlap)
/* 205:    */       {
/* 206:203 */         bestOverlap = d;
/* 207:204 */         bestPackage = offence;
/* 208:    */       }
/* 209:    */     }
/* 210:212 */     return bestPackage;
/* 211:    */   }
/* 212:    */   
/* 213:    */   private int bundleOverlap(Entity t1, Entity t2)
/* 214:    */   {
/* 215:216 */     return bundleOverlap(t1.getBundle(), t2.getBundle());
/* 216:    */   }
/* 217:    */   
/* 218:    */   private int bundleOverlap(Bundle b1, Bundle b2)
/* 219:    */   {
/* 220:220 */     int max = 0;
/* 221:221 */     if ((b1.isEmpty()) || (b2.isEmpty())) {
/* 222:222 */       return 0;
/* 223:    */     }
/* 224:    */     Iterator localIterator2;
/* 225:224 */     for (Iterator localIterator1 = b1.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 226:    */     {
/* 227:224 */       Thread t1 = (Thread)localIterator1.next();
/* 228:225 */       localIterator2 = b2.iterator(); continue;Thread t2 = (Thread)localIterator2.next();
/* 229:226 */       int count = 0;
/* 230:227 */       for (int i = 0; i < Math.min(t1.size(), t2.size()); i++)
/* 231:    */       {
/* 232:228 */         if (!((String)t1.get(i)).equalsIgnoreCase((String)t2.get(i))) {
/* 233:    */           break;
/* 234:    */         }
/* 235:231 */         count = i;
/* 236:    */       }
/* 237:233 */       max = Math.max(max, count);
/* 238:    */     }
/* 239:236 */     return max;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public static void main(String[] ignore)
/* 243:    */   {
/* 244:240 */     Entity t = new Entity("damage");
/* 245:241 */     Bundle b1 = BundleGenerator.getBundle(t.getType());
/* 246:242 */     Bundle b2 = BundleGenerator.getBundle("wound");
/* 247:243 */     Mark.say(new Object[] {"Test", b1, b2, Integer.valueOf(new EscalationExpert().bundleOverlap(b1, b2)) });
/* 248:    */   }
/* 249:    */   
/* 250:    */   private Entity backwardChain(Vector<Entity> queue, List<Entity> storyElements, ArrayList<Entity> extended)
/* 251:    */   {
/* 252:247 */     Vector<Entity> newQueue = new Vector();
/* 253:248 */     for (Entity front : queue)
/* 254:    */     {
/* 255:250 */       if (!front.isA("harm")) {
/* 256:253 */         if ((front.isAPrimed("action")) && (getOffence(front) != null)) {
/* 257:254 */           return front;
/* 258:    */         }
/* 259:    */       }
/* 260:257 */       if (!extended.contains(front))
/* 261:    */       {
/* 262:260 */         extended.add(front);
/* 263:261 */         for (Entity inference : storyElements) {
/* 264:262 */           if (inference.relationP()) {
/* 265:265 */             if (inference.getObject() == front) {
/* 266:266 */               newQueue.addAll(inference.getSubject().getElements());
/* 267:    */             }
/* 268:    */           }
/* 269:    */         }
/* 270:    */       }
/* 271:    */     }
/* 272:270 */     if (newQueue.isEmpty()) {
/* 273:271 */       return null;
/* 274:    */     }
/* 275:273 */     return backwardChain(newQueue, storyElements, extended);
/* 276:    */   }
/* 277:    */   
/* 278:    */   private void initializeGoldstein()
/* 279:    */   {
/* 280:    */     try
/* 281:    */     {
/* 282:278 */       String content = TextIO.readStringFromURL(EscalationExpert.class.getResource("goldstein.txt"));
/* 283:279 */       StringReader sr = new StringReader(content);
/* 284:280 */       BufferedReader br = new BufferedReader(sr);
/* 285:    */       String line;
/* 286:282 */       while ((line = br.readLine()) != null)
/* 287:    */       {
/* 288:    */         String line;
/* 289:283 */         String[] candidate = line.split(" ");
/* 290:284 */         if (candidate.length == 2)
/* 291:    */         {
/* 292:285 */           double score = Double.parseDouble(candidate[0]);
/* 293:286 */           String verb = candidate[1];
/* 294:287 */           this.goldsteinOffences.add(new Goldstein(score, verb, BundleGenerator.getBundle(verb)));
/* 295:    */         }
/* 296:    */       }
/* 297:    */     }
/* 298:    */     catch (Exception e)
/* 299:    */     {
/* 300:292 */       e.printStackTrace();
/* 301:    */     }
/* 302:    */   }
/* 303:    */   
/* 304:    */   private void initializeWeis()
/* 305:    */   {
/* 306:    */     try
/* 307:    */     {
/* 308:298 */       String content = TextIO.readStringFromURL(EscalationExpert.class.getResource("weis.txt"));
/* 309:299 */       StringReader sr = new StringReader(content);
/* 310:300 */       BufferedReader br = new BufferedReader(sr);
/* 311:    */       String line;
/* 312:302 */       while ((line = br.readLine()) != null)
/* 313:    */       {
/* 314:    */         String line;
/* 315:303 */         String[] candidate = line.split(" ");
/* 316:304 */         if (candidate.length == 2)
/* 317:    */         {
/* 318:305 */           double score = Double.parseDouble(candidate[0]);
/* 319:306 */           String verb = candidate[1];
/* 320:307 */           this.weisOffences.add(new Goldstein(score, verb, BundleGenerator.getBundle(verb)));
/* 321:    */         }
/* 322:    */       }
/* 323:    */     }
/* 324:    */     catch (Exception e)
/* 325:    */     {
/* 326:312 */       e.printStackTrace();
/* 327:    */     }
/* 328:    */   }
/* 329:    */   
/* 330:    */   class Goldstein
/* 331:    */   {
/* 332:    */     double score;
/* 333:    */     String verb;
/* 334:    */     Bundle bundle;
/* 335:    */     
/* 336:    */     public double getScore()
/* 337:    */     {
/* 338:324 */       return this.score;
/* 339:    */     }
/* 340:    */     
/* 341:    */     public String getVerb()
/* 342:    */     {
/* 343:328 */       return this.verb;
/* 344:    */     }
/* 345:    */     
/* 346:    */     public Bundle getBundle()
/* 347:    */     {
/* 348:332 */       return this.bundle;
/* 349:    */     }
/* 350:    */     
/* 351:    */     public Goldstein(double score, String verb, Bundle bundle)
/* 352:    */     {
/* 353:337 */       this.score = score;
/* 354:338 */       this.verb = verb;
/* 355:339 */       this.bundle = bundle;
/* 356:    */     }
/* 357:    */   }
/* 358:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     escalation.EscalationExpert
 * JD-Core Version:    0.7.0.1
 */