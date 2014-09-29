/*   1:    */ package translator;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Matcher;
/*   5:    */ import bridge.reps.entities.Relation;
/*   6:    */ import bridge.reps.entities.Sequence;
/*   7:    */ import connections.Connections;
/*   8:    */ import connections.Ports;
/*   9:    */ import genesis.GenesisGetters;
/*  10:    */ import java.io.PrintStream;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.Iterator;
/*  13:    */ import java.util.Vector;
/*  14:    */ import parameters.Switch;
/*  15:    */ import persistence.JCheckBoxWithMemory;
/*  16:    */ import utils.Mark;
/*  17:    */ 
/*  18:    */ public class HardWiredTranslator
/*  19:    */   extends RuleSet
/*  20:    */ {
/*  21:    */   public static final String LEFT = "left";
/*  22:    */   public static final String RIGHT = "right";
/*  23:    */   
/*  24:    */   public HardWiredTranslator()
/*  25:    */   {
/*  26: 24 */     this(null);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public HardWiredTranslator(GenesisGetters gauntlet)
/*  30:    */   {
/*  31: 32 */     this.gauntlet = gauntlet;
/*  32: 33 */     Connections.getPorts(this).addSignalProcessor(PARSE, "setInput");
/*  33: 34 */     Connections.getPorts(this).addSignalProcessor("left", "setInputLeft");
/*  34: 35 */     Connections.getPorts(this).addSignalProcessor("right", "setInputRight");
/*  35: 36 */     Connections.getPorts(this).addSignalProcessor(PROCESS, "setInputAndStep");
/*  36: 37 */     Connections.getPorts(this).addSignalProcessor(STEP, "step");
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setInputLeft(Object object)
/*  40:    */   {
/*  41: 41 */     if (!(object instanceof Sequence)) {
/*  42: 42 */       return;
/*  43:    */     }
/*  44: 44 */     Entity result = interpret(object);
/*  45:    */     
/*  46: 46 */     Connections.getPorts(this).transmit("left", result);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setInputRight(Object object)
/*  50:    */   {
/*  51: 50 */     if (!(object instanceof Sequence)) {
/*  52: 51 */       return;
/*  53:    */     }
/*  54: 53 */     Entity result = interpret(object);
/*  55:    */     
/*  56: 55 */     Connections.getPorts(this).transmit("right", result);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Entity interpret(Object o)
/*  60:    */   {
/*  61: 59 */     if ((o instanceof Sequence))
/*  62:    */     {
/*  63: 60 */       this.parse = ((Sequence)o);
/*  64:    */       
/*  65: 62 */       setInput(this.parse);
/*  66: 63 */       while (rachet()) {}
/*  67: 65 */       Sequence result = (Sequence)getTransformations().get(getTransformations().size() - 1);
/*  68: 66 */       Vector<Entity> v = result.getElements();
/*  69: 67 */       for (Entity t : v) {
/*  70: 68 */         if (t.relationP())
/*  71:    */         {
/*  72: 69 */           Relation r = (Relation)t;
/*  73: 70 */           if (r.getSubject().isA("root")) {
/*  74: 71 */             return r.getObject();
/*  75:    */           }
/*  76:    */         }
/*  77:    */       }
/*  78:    */     }
/*  79: 76 */     return null;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean rachet()
/*  83:    */   {
/*  84: 80 */     int size = getTransformations().size();
/*  85: 81 */     transform();
/*  86: 82 */     if (getTransformations().size() == size) {
/*  87: 83 */       return false;
/*  88:    */     }
/*  89: 85 */     return true;
/*  90:    */   }
/*  91:    */   
/*  92:    */   private class LocalGoClass
/*  93:    */     extends Thread
/*  94:    */   {
/*  95:    */     private LocalGoClass() {}
/*  96:    */     
/*  97:    */     public void run()
/*  98:    */     {
/*  99: 92 */       while (HardWiredTranslator.this.step()) {
/* 100: 93 */         if ((HardWiredTranslator.this.gauntlet != null) && (Switch.stepParser.isSelected())) {
/* 101:    */           try
/* 102:    */           {
/* 103: 95 */             sleep(HardWiredTranslator.delta);
/* 104:    */           }
/* 105:    */           catch (InterruptedException e)
/* 106:    */           {
/* 107: 98 */             e.printStackTrace();
/* 108:    */           }
/* 109:    */         }
/* 110:    */       }
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:113 */   private static int delta = 500;
/* 115:115 */   public static String PARSE = "parse";
/* 116:115 */   public static String STEP = "step";
/* 117:115 */   public static String PROCESS = "process";
/* 118:115 */   public static String RUN = "run";
/* 119:117 */   public static String PROGRESS = "progress";
/* 120:    */   private GenesisGetters gauntlet;
/* 121:    */   private Sequence parse;
/* 122:    */   private ArrayList<Sequence> transformations;
/* 123:    */   
/* 124:    */   public static void main(String[] ignore)
/* 125:    */   {
/* 126:121 */     Entity subject = new Entity("bird");
/* 127:122 */     Entity object = new Entity("fly");
/* 128:123 */     Relation relation = new Relation(subject, object);
/* 129:    */     
/* 130:125 */     Sequence parse = new Sequence();
/* 131:126 */     parse.addElement(relation);
/* 132:    */     
/* 133:128 */     HardWiredTranslator hardWiredTranslator = new HardWiredTranslator(null);
/* 134:129 */     hardWiredTranslator.setInput(parse);
/* 135:130 */     hardWiredTranslator.step(parse);
/* 136:    */     
/* 137:132 */     System.out.println("Result: ");
/* 138:    */     
/* 139:134 */     Matcher matcher = new Matcher();
/* 140:    */   }
/* 141:    */   
/* 142:146 */   private boolean transmittable = false;
/* 143:    */   
/* 144:    */   public Sequence getParse()
/* 145:    */   {
/* 146:149 */     if (this.parse == null) {
/* 147:150 */       this.parse = new Sequence();
/* 148:    */     }
/* 149:152 */     return this.parse;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public ArrayList<Sequence> getTransformations()
/* 153:    */   {
/* 154:156 */     if (this.transformations == null) {
/* 155:157 */       this.transformations = new ArrayList();
/* 156:    */     }
/* 157:159 */     return this.transformations;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void go()
/* 161:    */   {
/* 162:163 */     new LocalGoClass(null).start();
/* 163:    */   }
/* 164:    */   
/* 165:    */   private void removeParts(Entity thing, Vector v)
/* 166:    */   {
/* 167:167 */     Vector links = (Vector)v.clone();
/* 168:168 */     for (Iterator<Entity> i = links.iterator(); i.hasNext();)
/* 169:    */     {
/* 170:169 */       Entity t = (Entity)i.next();
/* 171:170 */       if (!t.isA("parse-link")) {
/* 172:171 */         v.remove(t);
/* 173:    */       }
/* 174:    */     }
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setInput(Object o)
/* 178:    */   {
/* 179:197 */     if ((o instanceof Sequence))
/* 180:    */     {
/* 181:198 */       this.parse = ((Sequence)o);
/* 182:199 */       getTransformations().clear();
/* 183:200 */       getTransformations().add(this.parse);
/* 184:    */     }
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setInputAndStep(Object o)
/* 188:    */   {
/* 189:205 */     if ((o instanceof Sequence))
/* 190:    */     {
/* 191:206 */       this.parse = ((Sequence)o);
/* 192:207 */       setInput(this.parse);
/* 193:208 */       go();
/* 194:    */     }
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setParse(Sequence parse)
/* 198:    */   {
/* 199:213 */     this.parse = parse;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setTransformations(ArrayList<Sequence> transformations)
/* 203:    */   {
/* 204:217 */     this.transformations = transformations;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public boolean step()
/* 208:    */   {
/* 209:221 */     if (rachet())
/* 210:    */     {
/* 211:223 */       Connections.getPorts(this).transmit(PROGRESS, getTransformations().get(getTransformations().size() - 1));
/* 212:224 */       return true;
/* 213:    */     }
/* 214:227 */     Sequence result = (Sequence)getTransformations().get(getTransformations().size() - 1);
/* 215:228 */     Vector v = result.getElements();
/* 216:    */     
/* 217:230 */     Sequence sequence = new Sequence("semantic-interpretation");
/* 218:231 */     for (Iterator i = v.iterator(); i.hasNext();)
/* 219:    */     {
/* 220:232 */       Relation t = (Relation)i.next();
/* 221:233 */       if (t.getSubject().isA("root")) {
/* 222:234 */         sequence.addElement(t.getObject());
/* 223:    */       }
/* 224:    */     }
/* 225:237 */     Connections.getPorts(this).transmit(sequence);
/* 226:238 */     return false;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public boolean step(Object o)
/* 230:    */   {
/* 231:243 */     return step();
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void transform()
/* 235:    */   {
/* 236:250 */     int lastIndex = getTransformations().size() - 1;
/* 237:251 */     Sequence sequence = (Sequence)getTransformations().get(lastIndex);
/* 238:252 */     Sequence result = transform(sequence);
/* 239:253 */     if (result != null) {
/* 240:254 */       getTransformations().add(result);
/* 241:    */     }
/* 242:    */   }
/* 243:    */   
/* 244:    */   private boolean transform(BasicRule runnable, Sequence s)
/* 245:    */   {
/* 246:259 */     runnable.setLinks(s);
/* 247:260 */     if (s.getElements().size() < 1) {
/* 248:261 */       return false;
/* 249:    */     }
/* 250:263 */     if ((runnable instanceof BasicRule3)) {
/* 251:265 */       for (int i = 0; i < s.getElements().size(); i++) {
/* 252:266 */         for (int j = i + 1; (j != i) && (j < s.getElements().size()); j++) {
/* 253:267 */           for (int k = j + 1; (k != j) && (k != i) && (k < s.getElements().size()); k++)
/* 254:    */           {
/* 255:268 */             runnable.setLinks((Entity)s.getElements().get(i), (Entity)s.getElements().get(j), (Entity)s.getElements().get(k));
/* 256:269 */             runnable.run();
/* 257:270 */             if (runnable.hasSucceeded())
/* 258:    */             {
/* 259:271 */               if (RuleSet.reportSuccess) {
/* 260:272 */                 Mark.say(new Object[] {"Rule " + runnable.getClass().getName() + " succeeded" });
/* 261:    */               }
/* 262:274 */               return true;
/* 263:    */             }
/* 264:    */           }
/* 265:    */         }
/* 266:    */       }
/* 267:280 */     } else if ((runnable instanceof BasicRule2)) {
/* 268:282 */       for (int i = 0; i < s.getElements().size(); i++) {
/* 269:283 */         for (int j = i + 1; (j != i) && (j < s.getElements().size()); j++)
/* 270:    */         {
/* 271:284 */           runnable.setLinks((Entity)s.getElements().get(i), (Entity)s.getElements().get(j));
/* 272:285 */           runnable.run();
/* 273:286 */           if (runnable.hasSucceeded())
/* 274:    */           {
/* 275:287 */             if (RuleSet.reportSuccess) {
/* 276:288 */               Mark.say(new Object[] {"Rule " + runnable.getClass().getName() + " succeeded" });
/* 277:    */             }
/* 278:290 */             return true;
/* 279:    */           }
/* 280:    */         }
/* 281:    */       }
/* 282:295 */     } else if ((runnable instanceof BasicRule)) {
/* 283:297 */       for (int i = 0; i < s.getElements().size(); i++)
/* 284:    */       {
/* 285:298 */         runnable.setLinks((Entity)s.getElements().get(i));
/* 286:299 */         runnable.run();
/* 287:300 */         if (runnable.hasSucceeded())
/* 288:    */         {
/* 289:301 */           if (RuleSet.reportSuccess) {
/* 290:302 */             Mark.say(new Object[] {"Rule " + runnable.getClass().getName() + " succeeded" });
/* 291:    */           }
/* 292:304 */           return true;
/* 293:    */         }
/* 294:    */       }
/* 295:    */     }
/* 296:309 */     return false;
/* 297:    */   }
/* 298:    */   
/* 299:    */   private Sequence transform(Sequence s)
/* 300:    */   {
/* 301:318 */     for (Rule rule : getRuleSet())
/* 302:    */     {
/* 303:320 */       BasicRule runnable = rule.getRunnable();
/* 304:321 */       if ((runnable != null) && 
/* 305:322 */         (transform(runnable, s))) {
/* 306:325 */         return s;
/* 307:    */       }
/* 308:    */     }
/* 309:330 */     return null;
/* 310:    */   }
/* 311:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     translator.HardWiredTranslator
 * JD-Core Version:    0.7.0.1
 */