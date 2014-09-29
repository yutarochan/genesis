/*   1:    */ package frames.utilities;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Sequence;
/*   5:    */ import bridge.utils.StringUtils;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.util.Vector;
/*   8:    */ 
/*   9:    */ public class SFactory
/*  10:    */ {
/*  11: 14 */   public static String FRAMELABEL = "conceptualization";
/*  12: 15 */   public static String[] actions = { "atrans", "ptrans", "propel", "move", "grasp", "ingest", "expel", "mtrans", "mbuild", "speak", "attend" };
/*  13:    */   
/*  14:    */   public static Sequence makeActiveConceptualization(Entity actor, Entity action, Entity object, Entity direction, Entity instrument)
/*  15:    */   {
/*  16: 17 */     if (!StringUtils.testType(action.getType(), actions))
/*  17:    */     {
/*  18: 18 */       System.err.println("Sorry, the thing " + action + " is not a Conceptual Dependency Theory primitive act.");
/*  19: 19 */       return null;
/*  20:    */     }
/*  21: 21 */     Sequence result = new Sequence(FRAMELABEL);
/*  22: 22 */     result.addType("active");
/*  23: 23 */     Vector<Entity> elts = new Vector();
/*  24: 24 */     elts.insertElementAt(actor, 0);
/*  25: 25 */     elts.insertElementAt(action, 1);
/*  26: 26 */     elts.insertElementAt(object, 2);
/*  27: 27 */     elts.insertElementAt(direction, 3);
/*  28: 28 */     elts.insertElementAt(instrument, 4);
/*  29: 29 */     result.setElements(elts);
/*  30: 30 */     return result;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static Sequence makeStativeConceptualization(Entity sObject, Entity state, Entity value)
/*  34:    */   {
/*  35: 34 */     Sequence result = new Sequence(FRAMELABEL);
/*  36: 35 */     result.addType("stative");
/*  37: 36 */     Vector<Entity> elts = new Vector();
/*  38: 37 */     elts.insertElementAt(sObject, 0);
/*  39: 38 */     elts.insertElementAt(state, 1);
/*  40: 39 */     elts.insertElementAt(value, 2);
/*  41: 40 */     result.setElements(elts);
/*  42: 41 */     return result;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static Entity getActor(Sequence conceptualization)
/*  46:    */   {
/*  47: 45 */     if (!conceptualization.isA(FRAMELABEL))
/*  48:    */     {
/*  49: 46 */       System.err.println("Error:  Argument to getActor must be a conceptualization.");
/*  50: 47 */       return null;
/*  51:    */     }
/*  52: 49 */     if (conceptualization.isA("active")) {
/*  53: 50 */       return (Entity)conceptualization.getElements().get(0);
/*  54:    */     }
/*  55: 52 */     System.err.println("Sorry, can't get actor from a stative conceptualization");
/*  56: 53 */     return null;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static Entity getAction(Sequence conceptualization)
/*  60:    */   {
/*  61: 57 */     if (!conceptualization.isA(FRAMELABEL))
/*  62:    */     {
/*  63: 58 */       System.err.println("Error:  Argument to getAction must be a conceptualization.");
/*  64: 59 */       return null;
/*  65:    */     }
/*  66: 61 */     if (conceptualization.isA("active")) {
/*  67: 62 */       return (Entity)conceptualization.getElements().get(1);
/*  68:    */     }
/*  69: 64 */     System.err.println("Sorry, can't get action from a stative conceptualization");
/*  70: 65 */     return null;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static Entity getObject(Sequence conceptualization)
/*  74:    */   {
/*  75: 69 */     if (!conceptualization.isA(FRAMELABEL))
/*  76:    */     {
/*  77: 70 */       System.err.println("Error:  Argument to getObject must be a conceptualization.");
/*  78: 71 */       return null;
/*  79:    */     }
/*  80: 73 */     if (conceptualization.isA("active")) {
/*  81: 74 */       return (Entity)conceptualization.getElements().get(2);
/*  82:    */     }
/*  83: 76 */     System.err.println("Sorry, can't get object from an active conceptualization");
/*  84: 77 */     return null;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static Entity getDirection(Sequence conceptualization)
/*  88:    */   {
/*  89: 81 */     if (!conceptualization.isA(FRAMELABEL))
/*  90:    */     {
/*  91: 82 */       System.err.println("Error:  Argument to getDirection must be a conceptualization.");
/*  92: 83 */       return null;
/*  93:    */     }
/*  94: 85 */     if (conceptualization.isA("active")) {
/*  95: 86 */       return (Entity)conceptualization.getElements().get(3);
/*  96:    */     }
/*  97: 88 */     System.err.println("Sorry, can't get direction from a stative conceptualization");
/*  98: 89 */     return null;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static Entity getInstrument(Sequence conceptualization)
/* 102:    */   {
/* 103: 93 */     if (!conceptualization.isA(FRAMELABEL))
/* 104:    */     {
/* 105: 94 */       System.err.println("Error:  Argument to getInstrument must be a conceptualization.");
/* 106: 95 */       return null;
/* 107:    */     }
/* 108: 97 */     if (conceptualization.isA("active")) {
/* 109: 98 */       return (Entity)conceptualization.getElements().get(5);
/* 110:    */     }
/* 111:100 */     System.err.println("Sorry, can't get instrument from a stative conceptualization");
/* 112:101 */     return null;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public static Entity getSObject(Sequence conceptualization)
/* 116:    */   {
/* 117:105 */     if (!conceptualization.isA(FRAMELABEL))
/* 118:    */     {
/* 119:106 */       System.err.println("Error:  Argument to getSObject must be a conceptualization.");
/* 120:107 */       return null;
/* 121:    */     }
/* 122:109 */     if (conceptualization.isA("stative")) {
/* 123:110 */       return (Entity)conceptualization.getElements().get(0);
/* 124:    */     }
/* 125:112 */     System.err.println("Sorry, can't get sObject from an active conceptualization");
/* 126:113 */     return null;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public static Entity getState(Sequence conceptualization)
/* 130:    */   {
/* 131:117 */     if (!conceptualization.isA(FRAMELABEL))
/* 132:    */     {
/* 133:118 */       System.err.println("Error:  Argument to getState must be a conceptualization.");
/* 134:119 */       return null;
/* 135:    */     }
/* 136:121 */     if (conceptualization.isA("stative")) {
/* 137:122 */       return (Entity)conceptualization.getElements().get(1);
/* 138:    */     }
/* 139:124 */     System.err.println("Sorry, can't get state from an active conceptualization");
/* 140:125 */     return null;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public static Entity getValue(Sequence conceptualization)
/* 144:    */   {
/* 145:129 */     if (!conceptualization.isA(FRAMELABEL))
/* 146:    */     {
/* 147:130 */       System.err.println("Error:  Argument to getValue must be a conceptualization.");
/* 148:131 */       return null;
/* 149:    */     }
/* 150:133 */     if (conceptualization.isA("stative")) {
/* 151:134 */       return (Entity)conceptualization.getElements().get(2);
/* 152:    */     }
/* 153:136 */     System.err.println("Sorry, can't get value from an active conceptualization");
/* 154:137 */     return null;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public static void setActor(Sequence conceptualization, Entity actor)
/* 158:    */   {
/* 159:141 */     if (!conceptualization.isA(FRAMELABEL))
/* 160:    */     {
/* 161:142 */       System.err.println("Error:  Argument to setActor must be a conceptualization.");
/* 162:143 */       return;
/* 163:    */     }
/* 164:145 */     if (conceptualization.isA("active"))
/* 165:    */     {
/* 166:146 */       conceptualization.setElementAt(actor, 0);
/* 167:147 */       return;
/* 168:    */     }
/* 169:149 */     System.err.println("Sorry, can't set actor in a stative conceptualization");
/* 170:    */   }
/* 171:    */   
/* 172:    */   public static void setAction(Sequence conceptualization, Entity action)
/* 173:    */   {
/* 174:154 */     if (!conceptualization.isA(FRAMELABEL))
/* 175:    */     {
/* 176:155 */       System.err.println("Error:  Argument to setAction must be a conceptualization.");
/* 177:156 */       return;
/* 178:    */     }
/* 179:158 */     if (conceptualization.isA("active"))
/* 180:    */     {
/* 181:159 */       conceptualization.setElementAt(action, 1);
/* 182:160 */       return;
/* 183:    */     }
/* 184:162 */     System.err.println("Sorry, can't set action in a stative conceptualization");
/* 185:    */   }
/* 186:    */   
/* 187:    */   public static void setObject(Sequence conceptualization, Entity object)
/* 188:    */   {
/* 189:166 */     if (!conceptualization.isA(FRAMELABEL))
/* 190:    */     {
/* 191:167 */       System.err.println("Error:  Argument to setObject must be a conceptualization.");
/* 192:168 */       return;
/* 193:    */     }
/* 194:170 */     if (conceptualization.isA("active"))
/* 195:    */     {
/* 196:171 */       conceptualization.setElementAt(object, 2);
/* 197:172 */       return;
/* 198:    */     }
/* 199:174 */     System.err.println("Sorry, can't set object in a stative conceptualization");
/* 200:    */   }
/* 201:    */   
/* 202:    */   public static void setDirection(Sequence conceptualization, Entity direction)
/* 203:    */   {
/* 204:179 */     if (!conceptualization.isA(FRAMELABEL))
/* 205:    */     {
/* 206:180 */       System.err.println("Error:  Argument to setDirection must be a conceptualization.");
/* 207:181 */       return;
/* 208:    */     }
/* 209:183 */     if (conceptualization.isA("active"))
/* 210:    */     {
/* 211:184 */       conceptualization.setElementAt(direction, 3);
/* 212:185 */       return;
/* 213:    */     }
/* 214:187 */     System.err.println("Sorry, can't set direction in a stative conceptualization");
/* 215:    */   }
/* 216:    */   
/* 217:    */   public static void setInstrument(Sequence conceptualization, Entity instrument)
/* 218:    */   {
/* 219:192 */     if (!conceptualization.isA(FRAMELABEL))
/* 220:    */     {
/* 221:193 */       System.err.println("Error:  Argument to setInstrument must be a conceptualization.");
/* 222:194 */       return;
/* 223:    */     }
/* 224:196 */     if (conceptualization.isA("active"))
/* 225:    */     {
/* 226:197 */       conceptualization.setElementAt(instrument, 4);
/* 227:198 */       return;
/* 228:    */     }
/* 229:200 */     System.err.println("Sorry, can't set instrument in a stative conceptualization");
/* 230:    */   }
/* 231:    */   
/* 232:    */   public static void setSObject(Sequence conceptualization, Entity sObject)
/* 233:    */   {
/* 234:205 */     if (!conceptualization.isA(FRAMELABEL))
/* 235:    */     {
/* 236:206 */       System.err.println("Error:  Argument to setSObject must be a conceptualization.");
/* 237:207 */       return;
/* 238:    */     }
/* 239:209 */     if (conceptualization.isA("stative"))
/* 240:    */     {
/* 241:210 */       conceptualization.setElementAt(sObject, 0);
/* 242:211 */       return;
/* 243:    */     }
/* 244:213 */     System.err.println("Sorry, can't set sObject in an active conceptualization");
/* 245:    */   }
/* 246:    */   
/* 247:    */   public static void setState(Sequence conceptualization, Entity state)
/* 248:    */   {
/* 249:218 */     if (!conceptualization.isA(FRAMELABEL))
/* 250:    */     {
/* 251:219 */       System.err.println("Error:  Argument to setState must be a conceptualization.");
/* 252:220 */       return;
/* 253:    */     }
/* 254:222 */     if (conceptualization.isA("stative"))
/* 255:    */     {
/* 256:223 */       conceptualization.setElementAt(state, 1);
/* 257:224 */       return;
/* 258:    */     }
/* 259:226 */     System.err.println("Sorry, can't set state in an active conceptualization");
/* 260:    */   }
/* 261:    */   
/* 262:    */   public static void setValue(Sequence conceptualization, Entity value)
/* 263:    */   {
/* 264:231 */     if (!conceptualization.isA(FRAMELABEL))
/* 265:    */     {
/* 266:232 */       System.err.println("Error:  Argument to setValue must be a conceptualization.");
/* 267:233 */       return;
/* 268:    */     }
/* 269:235 */     if (conceptualization.isA("stative"))
/* 270:    */     {
/* 271:236 */       conceptualization.setElementAt(value, 2);
/* 272:237 */       return;
/* 273:    */     }
/* 274:239 */     System.err.println("Sorry, can't set value in an active conceptualization");
/* 275:    */   }
/* 276:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.utilities.SFactory
 * JD-Core Version:    0.7.0.1
 */