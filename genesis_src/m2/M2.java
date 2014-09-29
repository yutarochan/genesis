/*   1:    */ package m2;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import connections.AbstractWiredBox;
/*   5:    */ import connections.Connections;
/*   6:    */ import connections.Ports;
/*   7:    */ import constants.RecognizedRepresentations;
/*   8:    */ import java.io.PrintStream;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Collection;
/*  11:    */ import java.util.Collections;
/*  12:    */ import java.util.Comparator;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import java.util.Set;
/*  16:    */ import m2.datatypes.Chain;
/*  17:    */ import m2.models.World;
/*  18:    */ import m2.storage.Raw;
/*  19:    */ import magicLess.utils.EntityUtils;
/*  20:    */ import memory.RepProcessor;
/*  21:    */ import memory.utilities.Distances;
/*  22:    */ import utils.Mark;
/*  23:    */ 
/*  24:    */ public class M2
/*  25:    */   extends AbstractWiredBox
/*  26:    */   implements Mem
/*  27:    */ {
/*  28: 26 */   public static boolean DEBUG = false;
/*  29:    */   private static Mem mem;
/*  30: 30 */   private Raw raw = new Raw();
/*  31: 32 */   private LLMerger merger = new LLMerger();
/*  32: 34 */   private World world = new World();
/*  33:    */   public static final String PORT_ENGLISH = "port for english sentence input";
/*  34:    */   public static final String PORT_VISION = "port for vision input";
/*  35:    */   public static final String PORT_CHAINS = "port for llmerger contents";
/*  36:    */   public static final String PORT_PREDICTIONS = "port for outputing predictions";
/*  37:    */   public static final String PORT_TALKER = "port for sending thing to prediction generator";
/*  38:    */   public static final String PORT_STIMULUS = "port for asking about neighbors";
/*  39:    */   public static final String PORT_RESPONSE = "port for responding with neighbors";
/*  40:    */   
/*  41:    */   private M2()
/*  42:    */   {
/*  43: 54 */     Connections.getPorts(this).addSignalProcessor("port for english sentence input", "wireInputEnglish");
/*  44: 55 */     Connections.getPorts(this).addSignalProcessor("port for vision input", "wireInputVision");
/*  45: 56 */     Connections.getPorts(this).addSignalProcessor("port for asking about neighbors", "wireGetNeighbors");
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void wireInputEnglish(Object input)
/*  49:    */   {
/*  50: 61 */     if (!(input instanceof Entity))
/*  51:    */     {
/*  52: 62 */       if (DEBUG) {
/*  53: 62 */         System.out.println("[M2] Expected Thing, but received: " + input);
/*  54:    */       }
/*  55: 63 */       return;
/*  56:    */     }
/*  57: 65 */     Entity t = (Entity)input;
/*  58:    */     
/*  59:    */ 
/*  60:    */ 
/*  61: 69 */     input(t);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void wireInputVision(Object input)
/*  65:    */   {
/*  66: 73 */     if (!(input instanceof Entity))
/*  67:    */     {
/*  68: 74 */       if (DEBUG) {
/*  69: 74 */         System.out.println("[M2] Expected Thing, but received: " + input);
/*  70:    */       }
/*  71: 75 */       return;
/*  72:    */     }
/*  73: 77 */     input((Entity)input);
/*  74:    */   }
/*  75:    */   
/*  76:    */   private void outputChains(Set<Chain> chains)
/*  77:    */   {
/*  78: 82 */     Connections.getPorts(getMem()).transmit("port for llmerger contents", chains);
/*  79:    */   }
/*  80:    */   
/*  81:    */   private void outputReps(Map<String, Set<Chain>> chains)
/*  82:    */   {
/*  83: 86 */     for (String repString : chains.keySet()) {
/*  84: 87 */       Connections.getPorts(getMem()).transmit(repString, new ArrayList((Collection)chains.get(repString)));
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   private void outputPredictions(Map<String, List<Entity>> preds)
/*  89:    */   {
/*  90: 93 */     Connections.getPorts(getMem()).transmit("port for outputing predictions", preds);
/*  91: 94 */     List<Entity> lst = (List)preds.get("exact causal precedence");
/*  92: 95 */     if ((lst != null) && (lst.size() > 0))
/*  93:    */     {
/*  94: 96 */       Entity t = (Entity)lst.get(0);
/*  95: 97 */       Mark.a(new Object[] {"Prediction sent to talker " + t.asString() });
/*  96: 98 */       Connections.getPorts(getMem()).transmit("port for sending thing to prediction generator", t);
/*  97:    */     }
/*  98:    */     else
/*  99:    */     {
/* 100:102 */       lst = (List)preds.get("analogy from known causal relation");
/* 101:103 */       if ((lst != null) && (lst.size() > 0))
/* 102:    */       {
/* 103:104 */         Entity t = (Entity)lst.get(0);
/* 104:105 */         Connections.getPorts(getMem()).transmit("port for sending thing to prediction generator", t);
/* 105:    */       }
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public static Mem getMem()
/* 110:    */   {
/* 111:115 */     if (mem == null)
/* 112:    */     {
/* 113:116 */       mem = new M2();
/* 114:117 */       ((M2)mem).setName("M2");
/* 115:118 */       if (DEBUG) {
/* 116:118 */         System.out.println("[M2] Debugging print statements are ON! To turn off, set the DEBUG flag in M2.java to false.");
/* 117:    */       }
/* 118:    */     }
/* 119:120 */     return mem;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void input(Entity t)
/* 123:    */   {
/* 124:129 */     boolean modified = false;
/* 125:130 */     Set<Entity> family = RepProcessor.extractSubReps(t);
/* 126:131 */     family.add(t);
/* 127:132 */     for (Entity elt : family) {
/* 128:    */       try
/* 129:    */       {
/* 130:134 */         if (RecognizedRepresentations.ALL_THING_REPS.contains(EntityUtils.getRepType(t))) {
/* 131:137 */           if (EntityUtils.getRepType(t) != RecognizedRepresentations.QUESTION_THING)
/* 132:    */           {
/* 133:140 */             inputRep(elt);
/* 134:141 */             modified = true;
/* 135:    */           }
/* 136:    */         }
/* 137:    */       }
/* 138:    */       catch (RuntimeException e)
/* 139:    */       {
/* 140:144 */         e.printStackTrace();
/* 141:145 */         System.err.println("[M2] Runtime exception in memory system");
/* 142:    */       }
/* 143:    */     }
/* 144:148 */     if (modified)
/* 145:    */     {
/* 146:151 */       outputAll();
/* 147:152 */       outputPredictions(this.world.predict(t));
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void outputAll()
/* 152:    */   {
/* 153:158 */     outputChains(this.merger.getChains());
/* 154:159 */     outputReps(this.merger.getRepChains());
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void inputRep(Entity t)
/* 158:    */   {
/* 159:164 */     if (DEBUG) {
/* 160:164 */       System.out.println("[M2] Processing rep: " + EntityUtils.getRepType(t));
/* 161:    */     }
/* 162:166 */     this.raw.add(t);
/* 163:    */     
/* 164:    */ 
/* 165:    */ 
/* 166:170 */     this.merger.add(t);
/* 167:    */     
/* 168:    */ 
/* 169:    */ 
/* 170:    */ 
/* 171:175 */     this.world.add(t);
/* 172:    */   }
/* 173:    */   
/* 174:    */   public int getMissDistance(Entity t)
/* 175:    */   {
/* 176:179 */     return this.merger.getMissDistance(t);
/* 177:    */   }
/* 178:    */   
/* 179:    */   public int frequency(Entity t)
/* 180:    */   {
/* 181:183 */     return this.raw.frequency(t);
/* 182:    */   }
/* 183:    */   
/* 184:    */   public static void m2assert(boolean b, String s)
/* 185:    */   {
/* 186:187 */     if (!b) {
/* 187:188 */       System.out.println("  !!! [M2] assertion failed: " + s);
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   public List<Entity> neighbors(final Entity t)
/* 192:    */   {
/* 193:193 */     List<Entity> results = this.merger.getNeighbors(t);
/* 194:194 */     Collections.sort(results, new Comparator()
/* 195:    */     {
/* 196:    */       public int compare(Entity e1, Entity e2)
/* 197:    */       {
/* 198:196 */         double s1 = Distances.distance(t, e1);
/* 199:197 */         double s2 = Distances.distance(t, e2);
/* 200:198 */         if (s1 < s2) {
/* 201:198 */           return -1;
/* 202:    */         }
/* 203:199 */         if (s1 > s2) {
/* 204:199 */           return 1;
/* 205:    */         }
/* 206:200 */         return 0;
/* 207:    */       }
/* 208:202 */     });
/* 209:203 */     return results;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public List<Entity> nearNeighbors(final Entity t)
/* 213:    */   {
/* 214:207 */     List<Entity> results = this.merger.getNearNeighbors(t);
/* 215:208 */     Collections.sort(results, new Comparator()
/* 216:    */     {
/* 217:    */       public int compare(Entity e1, Entity e2)
/* 218:    */       {
/* 219:210 */         double s1 = Distances.distance(t, e1);
/* 220:211 */         double s2 = Distances.distance(t, e2);
/* 221:212 */         if (s1 < s2) {
/* 222:212 */           return -1;
/* 223:    */         }
/* 224:213 */         if (s1 > s2) {
/* 225:213 */           return 1;
/* 226:    */         }
/* 227:214 */         return 0;
/* 228:    */       }
/* 229:216 */     });
/* 230:217 */     return results;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public boolean isPossible(Entity t)
/* 234:    */   {
/* 235:221 */     return this.merger.isPossible(t);
/* 236:    */   }
/* 237:    */   
/* 238:    */   public Set<Entity> getContext(Entity t)
/* 239:    */   {
/* 240:225 */     return this.raw.getContext(t);
/* 241:    */   }
/* 242:    */   
/* 243:    */   public Entity getMostRecentRep(Entity t, Object rep)
/* 244:    */   {
/* 245:229 */     return this.world.mostRecentRep(t, rep);
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void wireGetNeighbors(Object input)
/* 249:    */   {
/* 250:233 */     if ((input instanceof Entity))
/* 251:    */     {
/* 252:234 */       List<Entity> n = neighbors((Entity)input);
/* 253:235 */       Connections.getPorts(this).transmit("port for responding with neighbors", n);
/* 254:    */     }
/* 255:238 */     else if (DEBUG)
/* 256:    */     {
/* 257:238 */       System.out.println("[M2] Received invalid object on PORT_STIMULUS: " + input);
/* 258:    */     }
/* 259:    */   }
/* 260:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     m2.M2
 * JD-Core Version:    0.7.0.1
 */