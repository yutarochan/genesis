/*   1:    */ package frames;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Function;
/*   6:    */ import bridge.reps.entities.Relation;
/*   7:    */ import bridge.reps.entities.Thread;
/*   8:    */ import bridge.utils.StringUtils;
/*   9:    */ import constants.RecognizedRepresentations;
/*  10:    */ import java.io.PrintStream;
/*  11:    */ import java.util.HashMap;
/*  12:    */ 
/*  13:    */ public class ForceFrame
/*  14:    */   extends Frame
/*  15:    */ {
/*  16:    */   private static HashMap<String, Relation> forceMap;
/*  17:    */   
/*  18:    */   public static HashMap<String, Relation> getMap()
/*  19:    */   {
/*  20: 19 */     if (forceMap == null)
/*  21:    */     {
/*  22: 20 */       forceMap = new HashMap();
/*  23: 21 */       forceMap.put("The ball kept rolling because the wind blew on it.", makeForceRelation(new Entity("ball"), "notExit", "weak", "restful", new Entity("wind"), "notExit"));
/*  24: 22 */       forceMap.put("The shed kept standing despite the wind blowing against it.", makeForceRelation(new Entity("shed"), "notExit", "strong", "restful", new Entity("wind"), "notExit"));
/*  25: 23 */       forceMap.put("The ball kept rolling despite the stiff grass", makeForceRelation(new Entity("ball"), "notExit", "strong", "active", new Entity("grass"), "notExit"));
/*  26: 24 */       forceMap.put("The ball stayed on the incline because of the ridge there", makeForceRelation(new Entity("ball"), "notExit", "weak", "active", new Entity("ridge"), "notExit"));
/*  27: 25 */       forceMap.put("The lamp fell from the table because the ball hit it.", makeForceRelation(new Entity("lamp"), "notExit", "weak", "restful", new Entity("ball"), "enter"));
/*  28: 26 */       forceMap.put("The plug's coming loose let the water flow from the tank.", makeForceRelation(new Entity("water"), "notExit", "weak", "active", new Entity("plug"), "exit"));
/*  29: 27 */       forceMap.put("The fire died down because the water dripped onto it", makeForceRelation(new Entity("fire"), "notExit", "weak", "active", new Entity("water"), "enter"));
/*  30: 28 */       forceMap.put("The particles settled because the stirring rod broke.", makeForceRelation(new Entity("particles"), "notExit", "weak", "restful", new Entity("rod"), "exit"));
/*  31: 29 */       forceMap.put("The plug's staying loose let the water drain.", makeForceRelation(new Entity("water"), "notExit", "weak", "active", new Entity("plug"), "notEnter"));
/*  32: 30 */       forceMap.put("The fan's being broken let the smoke hang in the chamber.", makeForceRelation(new Entity("smoke"), "notExit", "weak", "restful", new Entity("fan"), "notEnter"));
/*  33:    */     }
/*  34: 32 */     return forceMap;
/*  35:    */   }
/*  36:    */   
/*  37: 36 */   public static final String[] shiftPatterns = { "enter", "exit", "notEnter", "notExit", "unknown" };
/*  38: 37 */   public static final String[] strengths = { "strong", "weak", "grow", "fade", "unknown" };
/*  39: 38 */   public static final String[] tendencies = { "active", "restful", "unknown" };
/*  40: 39 */   public static final String[] roles = { "agonist", "antagonist" };
/*  41: 40 */   public static final String FRAMETYPE = (String)RecognizedRepresentations.FORCE_THING;
/*  42:    */   public static final String TENDENCY = "forceTendency";
/*  43:    */   public static final String STRENGTH = "forceStrength";
/*  44:    */   public static final String AGENTTYPE = "forceAgent";
/*  45:    */   public static final String RESULT = "result";
/*  46:    */   public static final String PATTERN = "forceShiftPattern";
/*  47:    */   private Relation forceRelation;
/*  48:    */   
/*  49:    */   public static Function makeForceAgent(Entity agent, String role, String shiftPattern, String tendency, String strength)
/*  50:    */   {
/*  51: 56 */     if (!StringUtils.testType(role, roles))
/*  52:    */     {
/*  53: 57 */       System.err.println("Sorry, " + role + " is not a valid force dynamic role--must be agonist or antagonist.");
/*  54: 58 */       return null;
/*  55:    */     }
/*  56: 60 */     if (!StringUtils.testType(shiftPattern, shiftPatterns))
/*  57:    */     {
/*  58: 61 */       System.err.println("Sorry, " + shiftPattern + " is not a valid force dynamic shift pattern.");
/*  59: 62 */       return null;
/*  60:    */     }
/*  61: 64 */     if (!StringUtils.testType(tendency, tendencies))
/*  62:    */     {
/*  63: 65 */       System.err.println("Sorry, " + shiftPattern + " is not a valid force dynamic shift pattern.");
/*  64: 66 */       return null;
/*  65:    */     }
/*  66: 68 */     if (!StringUtils.testType(strength, strengths))
/*  67:    */     {
/*  68: 69 */       System.err.println("Sorry, " + strength + " is not a valid force dynamic strength.");
/*  69: 70 */       return null;
/*  70:    */     }
/*  71: 72 */     Function result = new Function("forceAgent", agent);
/*  72: 73 */     result.addType(role);
/*  73: 74 */     result.addType(shiftPattern, "forceShiftPattern");
/*  74: 75 */     result.addType(strength, "forceStrength");
/*  75: 76 */     result.addType(tendency, "forceTendency");
/*  76:    */     
/*  77: 78 */     return result;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static Relation makeForceRelation(Entity agonist, String agoShift, String agoStrength, String agoTend, Entity antagonist, String antShift)
/*  81:    */   {
/*  82: 91 */     Function ago = makeForceAgent(agonist, "agonist", agoShift, agoTend, agoStrength);
/*  83: 92 */     Function ant = makeForceAgent(antagonist, "antagonist", antShift, oppositeTendency(agoTend), oppositeStrength(agoStrength));
/*  84:    */     
/*  85: 94 */     Relation result = new Relation(FRAMETYPE, ago, ant);
/*  86: 95 */     result.addType(evaluateResult(result), "result");
/*  87: 96 */     return result;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static Entity getAgonistThing(Relation forceRelation)
/*  91:    */   {
/*  92:100 */     if (forceRelation.isA(FRAMETYPE)) {
/*  93:101 */       return forceRelation.getSubject().getSubject();
/*  94:    */     }
/*  95:103 */     System.err.println("Sorry, " + forceRelation + " is not a valid force relation.");
/*  96:104 */     return null;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static Entity getAntagonistThing(Relation forceRelation)
/* 100:    */   {
/* 101:108 */     if (forceRelation.isA(FRAMETYPE)) {
/* 102:109 */       return forceRelation.getObject().getSubject();
/* 103:    */     }
/* 104:111 */     System.err.println("Sorry, " + forceRelation + " is not a valid force relation.");
/* 105:112 */     return null;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static Function getAgonist(Relation forceRelation)
/* 109:    */   {
/* 110:116 */     if (forceRelation.isA(FRAMETYPE)) {
/* 111:117 */       return (Function)forceRelation.getSubject();
/* 112:    */     }
/* 113:119 */     System.err.println("Sorry, " + forceRelation + " is not a valid force relation.");
/* 114:120 */     return null;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public static Function getAntagonist(Relation forceRelation)
/* 118:    */   {
/* 119:124 */     if (forceRelation.isA(FRAMETYPE)) {
/* 120:125 */       return (Function)forceRelation.getObject();
/* 121:    */     }
/* 122:127 */     System.err.println("Sorry, " + forceRelation + " is not a valid force relation.");
/* 123:128 */     return null;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public static String getAgonistName(Relation forceRelation)
/* 127:    */   {
/* 128:132 */     if (!forceRelation.isA(FRAMETYPE))
/* 129:    */     {
/* 130:133 */       System.err.println("Sorry, " + forceRelation + " is not a valid force relation.");
/* 131:134 */       return "";
/* 132:    */     }
/* 133:136 */     return getAgonist(forceRelation).getSubject().getBundle().getType();
/* 134:    */   }
/* 135:    */   
/* 136:    */   public static String getAntagonistName(Relation forceRelation)
/* 137:    */   {
/* 138:140 */     if (!forceRelation.isA(FRAMETYPE))
/* 139:    */     {
/* 140:141 */       System.err.println("Sorry, " + forceRelation + " is not a valid force relation.");
/* 141:142 */       return "";
/* 142:    */     }
/* 143:144 */     return getAntagonist(forceRelation).getSubject().getBundle().getType();
/* 144:    */   }
/* 145:    */   
/* 146:    */   public static String getAgonistStrength(Relation forceRelation)
/* 147:    */   {
/* 148:148 */     if (!forceRelation.isA(FRAMETYPE))
/* 149:    */     {
/* 150:149 */       System.err.println("Sorry, " + forceRelation + " is not a valid force relation.");
/* 151:150 */       return "";
/* 152:    */     }
/* 153:152 */     return getAgonist(forceRelation).getBundle().getThreadContaining("forceStrength").getType();
/* 154:    */   }
/* 155:    */   
/* 156:    */   public static String getAntagonistStrength(Relation forceRelation)
/* 157:    */   {
/* 158:156 */     if (!forceRelation.isA(FRAMETYPE))
/* 159:    */     {
/* 160:157 */       System.err.println("Sorry, " + forceRelation + " is not a valid force relation.");
/* 161:158 */       return "";
/* 162:    */     }
/* 163:160 */     return getAntagonist(forceRelation).getBundle().getThreadContaining("forceStrength").getType();
/* 164:    */   }
/* 165:    */   
/* 166:    */   public static String getAgonistTendency(Relation forceRelation)
/* 167:    */   {
/* 168:164 */     if (!forceRelation.isA(FRAMETYPE))
/* 169:    */     {
/* 170:165 */       System.err.println("Sorry, " + forceRelation + " is not a valid force relation.");
/* 171:166 */       return "";
/* 172:    */     }
/* 173:168 */     return getAgonist(forceRelation).getBundle().getThreadContaining("forceTendency").getType();
/* 174:    */   }
/* 175:    */   
/* 176:    */   public static String getAntagonistTendency(Relation forceRelation)
/* 177:    */   {
/* 178:172 */     if (!forceRelation.isA(FRAMETYPE))
/* 179:    */     {
/* 180:173 */       System.err.println("Sorry, " + forceRelation + " is not a valid force relation.");
/* 181:174 */       return "";
/* 182:    */     }
/* 183:176 */     return getAntagonist(forceRelation).getBundle().getThreadContaining("forceTendency").getType();
/* 184:    */   }
/* 185:    */   
/* 186:    */   public static String getAgonistShiftPattern(Relation forceRelation)
/* 187:    */   {
/* 188:180 */     if (!forceRelation.isA(FRAMETYPE))
/* 189:    */     {
/* 190:181 */       System.err.println("Sorry, " + forceRelation + " is not a force relation.");
/* 191:182 */       return "";
/* 192:    */     }
/* 193:184 */     return getAgonist(forceRelation).getBundle().getThreadContaining("forceShiftPattern").getType();
/* 194:    */   }
/* 195:    */   
/* 196:    */   public static String getAntagonistShiftPattern(Relation forceRelation)
/* 197:    */   {
/* 198:188 */     if (!forceRelation.isA(FRAMETYPE))
/* 199:    */     {
/* 200:189 */       System.err.println("Sorry, " + forceRelation + " is not a force relation.");
/* 201:190 */       return "";
/* 202:    */     }
/* 203:192 */     return getAntagonist(forceRelation).getBundle().getThreadContaining("forceShiftPattern").getType();
/* 204:    */   }
/* 205:    */   
/* 206:    */   public static String evaluateResult(Relation forceRelation)
/* 207:    */   {
/* 208:201 */     if ((getAntagonistShiftPattern(forceRelation) == "exit") || (getAntagonistShiftPattern(forceRelation) == "notEnter")) {
/* 209:202 */       return getAgonistTendency(forceRelation);
/* 210:    */     }
/* 211:203 */     if ((getAgonistStrength(forceRelation) == "strong") || (getAgonistStrength(forceRelation) == "grow")) {
/* 212:204 */       return getAgonistTendency(forceRelation);
/* 213:    */     }
/* 214:205 */     if ((getAgonistTendency(forceRelation) == "unknown") || (getAgonistStrength(forceRelation) == "unknown")) {
/* 215:206 */       return "unknown";
/* 216:    */     }
/* 217:208 */     return getAntagonistTendency(forceRelation);
/* 218:    */   }
/* 219:    */   
/* 220:    */   public static String oppositeTendency(String tendency)
/* 221:    */   {
/* 222:220 */     if (tendency == "active") {
/* 223:221 */       return "restful";
/* 224:    */     }
/* 225:222 */     if (tendency == "restful") {
/* 226:223 */       return "active";
/* 227:    */     }
/* 228:224 */     if (tendency == "unknown") {
/* 229:225 */       return tendency;
/* 230:    */     }
/* 231:227 */     System.err.println("Sorry, " + tendency + " is not a valid force dynamic tendency");
/* 232:228 */     return "";
/* 233:    */   }
/* 234:    */   
/* 235:    */   public static String oppositeStrength(String strength)
/* 236:    */   {
/* 237:238 */     if (strength == "strong") {
/* 238:239 */       return "weak";
/* 239:    */     }
/* 240:240 */     if (strength == "weak") {
/* 241:241 */       return "strong";
/* 242:    */     }
/* 243:242 */     if (strength == "grow") {
/* 244:243 */       return "fade";
/* 245:    */     }
/* 246:244 */     if (strength == "fade") {
/* 247:245 */       return "grow";
/* 248:    */     }
/* 249:246 */     if (strength == "unknown") {
/* 250:247 */       return strength;
/* 251:    */     }
/* 252:249 */     System.err.println("Sorry, " + strength + " is not a valid force dynamic strength.");
/* 253:250 */     return "";
/* 254:    */   }
/* 255:    */   
/* 256:    */   public ForceFrame(Entity t)
/* 257:    */   {
/* 258:262 */     if (t.isA(FRAMETYPE)) {
/* 259:263 */       this.forceRelation = ((Relation)t);
/* 260:    */     } else {
/* 261:265 */       System.err.println("Error, called ForceFrame constructor with non force relation " + t + ".");
/* 262:    */     }
/* 263:    */   }
/* 264:    */   
/* 265:    */   public ForceFrame(ForceFrame s)
/* 266:    */   {
/* 267:270 */     this((Entity)s.getThing().clone());
/* 268:    */   }
/* 269:    */   
/* 270:    */   public Entity getThing()
/* 271:    */   {
/* 272:274 */     return this.forceRelation;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public String toString()
/* 276:    */   {
/* 277:278 */     if (this.forceRelation != null) {
/* 278:279 */       return this.forceRelation.toString();
/* 279:    */     }
/* 280:281 */     return "";
/* 281:    */   }
/* 282:    */   
/* 283:    */   public static void main(String[] args)
/* 284:    */   {
/* 285:285 */     HashMap<String, Relation> map = getMap();
/* 286:    */   }
/* 287:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.ForceFrame
 * JD-Core Version:    0.7.0.1
 */