/*   1:    */ package kevinWhite;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Thread;
/*   5:    */ import connections.AbstractWiredBox;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Arrays;
/*   8:    */ import java.util.Collection;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.HashSet;
/*  11:    */ import java.util.Set;
/*  12:    */ import translator.Translator;
/*  13:    */ import utils.Mark;
/*  14:    */ 
/*  15:    */ public class LatticeLearner
/*  16:    */   extends AbstractWiredBox
/*  17:    */ {
/*  18: 16 */   private static Translator interpreter = ;
/*  19: 17 */   private ArrayList<Entity> sentences = new ArrayList();
/*  20: 18 */   private HashMap<Integer, String> nounThreads = new HashMap();
/*  21: 19 */   private HashMap<Integer, String> verbThreads = new HashMap();
/*  22: 20 */   private static Set<String> impliedPositives = new HashSet();
/*  23: 21 */   private static Set<String> impliedNegatives = new HashSet();
/*  24:    */   private UI nounUI;
/*  25:    */   private UI verbUI;
/*  26:    */   
/*  27:    */   public LatticeLearner()
/*  28:    */     throws Exception
/*  29:    */   {
/*  30: 26 */     interpreter = Translator.getTranslator();
/*  31: 27 */     translateFlyers();
/*  32: 28 */     translateSwimmers();
/*  33: 29 */     translateLandLovers();
/*  34: 30 */     translateFurry();
/*  35:    */     
/*  36:    */ 
/*  37:    */ 
/*  38: 34 */     parse();
/*  39: 35 */     this.nounUI = new UI(this.nounThreads.values().toArray());
/*  40: 36 */     this.nounUI.setTitle("Nouns");
/*  41: 37 */     this.nounUI.setVisible(true);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void translateFlyers()
/*  45:    */     throws Exception
/*  46:    */   {
/*  47: 50 */     Entity birds = interpreter.translate("Birds can fly.");
/*  48: 51 */     Entity fish = interpreter.translate("Fish cannot fly.");
/*  49: 52 */     Entity airplane = interpreter.translate("Airplanes can fly.");
/*  50: 53 */     Entity cars = interpreter.translate("Cars can't fly.");
/*  51: 54 */     Entity bats = interpreter.translate("Bats can't fly.");
/*  52: 55 */     this.sentences.add(birds);
/*  53: 56 */     this.sentences.add(fish);
/*  54: 57 */     this.sentences.add(airplane);
/*  55: 58 */     this.sentences.add(cars);
/*  56: 59 */     this.sentences.add(bats);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void translateSwimmers()
/*  60:    */     throws Exception
/*  61:    */   {
/*  62: 67 */     Entity fish = interpreter.translate("Fish can swim.");
/*  63: 68 */     Entity plants = interpreter.translate("Plants can't swim.");
/*  64: 69 */     Entity boats = interpreter.translate("Boats can swim.");
/*  65: 70 */     Entity cars = interpreter.translate("Cars can't swim.");
/*  66: 71 */     Entity ducks = interpreter.translate("Ducks can swim.");
/*  67: 72 */     Entity sparrow = interpreter.translate("Sparrows cannot swim.");
/*  68: 73 */     Entity dogs = interpreter.translate("Dogs can swim.");
/*  69: 74 */     Entity bats = interpreter.translate("Bats cannot swim.");
/*  70: 75 */     this.sentences.add(fish);
/*  71: 76 */     this.sentences.add(plants);
/*  72: 77 */     this.sentences.add(boats);
/*  73: 78 */     this.sentences.add(cars);
/*  74: 79 */     this.sentences.add(ducks);
/*  75: 80 */     this.sentences.add(sparrow);
/*  76: 81 */     this.sentences.add(dogs);
/*  77: 82 */     this.sentences.add(bats);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void translateLandLovers()
/*  81:    */     throws Exception
/*  82:    */   {
/*  83: 90 */     Entity plants = interpreter.translate("Plants prefer land.");
/*  84: 91 */     Entity fish = interpreter.translate("Fish do not prefer land.");
/*  85: 92 */     Entity cars = interpreter.translate("Cars prefer land.");
/*  86: 93 */     Entity airplane = interpreter.translate("Airplanes don't prefer land.");
/*  87: 94 */     Entity dogs = interpreter.translate("Dogs prefer land.");
/*  88: 95 */     Entity bats = interpreter.translate("Bats don't prefer land.");
/*  89: 96 */     this.sentences.add(plants);
/*  90: 97 */     this.sentences.add(fish);
/*  91: 98 */     this.sentences.add(cars);
/*  92: 99 */     this.sentences.add(airplane);
/*  93:100 */     this.sentences.add(dogs);
/*  94:101 */     this.sentences.add(bats);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void translateFurry()
/*  98:    */     throws Exception
/*  99:    */   {
/* 100:109 */     Entity mammals = interpreter.translate("Mammals are furry.");
/* 101:110 */     Entity birds = interpreter.translate("Birds are not furry.");
/* 102:111 */     Entity rugs = interpreter.translate("Rugs are furry.");
/* 103:112 */     Entity cars = interpreter.translate("Cars aren't furry.");
/* 104:113 */     this.sentences.add(mammals);
/* 105:114 */     this.sentences.add(birds);
/* 106:115 */     this.sentences.add(rugs);
/* 107:116 */     this.sentences.add(cars);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void parse()
/* 111:    */   {
/* 112:124 */     for (Entity sentence : this.sentences)
/* 113:    */     {
/* 114:125 */       int nounKey = sentence.getElement(0).getSubject().getPrimedThread().toString(true).hashCode();
/* 115:126 */       if (!this.nounThreads.containsKey(Integer.valueOf(nounKey))) {
/* 116:127 */         this.nounThreads.put(Integer.valueOf(nounKey), sentence.getElement(0).getSubject().getPrimedThread().toString(true));
/* 117:    */       }
/* 118:130 */       String tempString = "";
/* 119:131 */       int verbKey = 0;
/* 120:132 */       if (sentence.getElement(0).hasFeature("not"))
/* 121:    */       {
/* 122:133 */         tempString = "not " + sentence.getElement(0).getPrimedThread().toString(true);
/* 123:134 */         verbKey = tempString.hashCode();
/* 124:    */       }
/* 125:    */       else
/* 126:    */       {
/* 127:138 */         tempString = sentence.getElement(0).getPrimedThread().toString(true);
/* 128:139 */         verbKey = tempString.hashCode();
/* 129:    */       }
/* 130:142 */       if (!this.verbThreads.containsKey(Integer.valueOf(verbKey))) {
/* 131:143 */         this.verbThreads.put(Integer.valueOf(verbKey), tempString);
/* 132:    */       }
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   public static String answerQuestion(String question, Set<String> affirmatives, Set<String> negatives)
/* 137:    */     throws Exception
/* 138:    */   {
/* 139:158 */     Entity ansEntity = interpreter.translate(question);
/* 140:161 */     if (question.contains("?"))
/* 141:    */     {
/* 142:162 */       String ansSubject = "";
/* 143:    */       try
/* 144:    */       {
/* 145:164 */         ansSubject = ansEntity.getElement(0).getSubject().getSubject().asStringWithoutIndexes().split("-")[0];
/* 146:165 */         Mark.say(new Object[] {Arrays.toString(impliedPositives.toArray()), Arrays.toString(impliedNegatives.toArray()) });
/* 147:166 */         Mark.say(new Object[] {ansSubject });
/* 148:    */       }
/* 149:    */       catch (Exception e)
/* 150:    */       {
/* 151:170 */         ansSubject = ansEntity.getElement(0).getSubject().getFeatures().toString().split(" ")[1];
/* 152:    */       }
/* 153:172 */       if ((affirmatives.contains(ansSubject)) || (impliedPositives.contains(ansSubject))) {
/* 154:173 */         return "Yes.";
/* 155:    */       }
/* 156:175 */       if ((negatives.contains(ansSubject)) || (impliedNegatives.contains(ansSubject))) {
/* 157:176 */         return "No.";
/* 158:    */       }
/* 159:179 */       return "This lattice does not contain the information requested.";
/* 160:    */     }
/* 161:184 */     String ansSubject = ansEntity.getElement(0).getSubject().asStringWithoutIndexes().split("-")[0];
/* 162:185 */     if ((affirmatives.contains(ansSubject)) || (impliedPositives.contains(ansSubject))) {
/* 163:186 */       return "True.";
/* 164:    */     }
/* 165:188 */     if ((negatives.contains(ansSubject)) || (impliedNegatives.contains(ansSubject))) {
/* 166:189 */       return "False.";
/* 167:    */     }
/* 168:192 */     ansSubject = ansEntity.getElement(0).getObject().getFeatures().toString().split(" ")[1];
/* 169:193 */     if ((affirmatives.contains(ansSubject)) || (impliedPositives.contains(ansSubject))) {
/* 170:194 */       return "True.";
/* 171:    */     }
/* 172:196 */     if ((negatives.contains(ansSubject)) || (impliedNegatives.contains(ansSubject))) {
/* 173:197 */       return "False.";
/* 174:    */     }
/* 175:200 */     return "This lattice does not contain the information requested.";
/* 176:    */   }
/* 177:    */   
/* 178:    */   public static void getImplication(boolean isGreen, String thread, Set<String> positives, Set<String> negatives)
/* 179:    */   {
/* 180:207 */     if (isGreen)
/* 181:    */     {
/* 182:208 */       if (impliedNegatives.contains(thread)) {
/* 183:209 */         impliedNegatives.remove(thread);
/* 184:    */       }
/* 185:211 */       impliedPositives.add(thread);
/* 186:    */     }
/* 187:    */     else
/* 188:    */     {
/* 189:215 */       if (impliedPositives.contains(thread)) {
/* 190:216 */         impliedPositives.remove(thread);
/* 191:    */       }
/* 192:218 */       impliedNegatives.add(thread);
/* 193:    */     }
/* 194:221 */     impliedPositives.removeAll(positives);
/* 195:222 */     impliedNegatives.removeAll(negatives);
/* 196:    */   }
/* 197:    */   
/* 198:    */   public static Set<String> getImpliedPositives()
/* 199:    */   {
/* 200:226 */     return impliedPositives;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public static Set<String> getImpliedNegatives()
/* 204:    */   {
/* 205:230 */     return impliedNegatives;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public UI getNounUI()
/* 209:    */   {
/* 210:234 */     return this.nounUI;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public UI getVerbUI()
/* 214:    */   {
/* 215:238 */     return this.verbUI;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public static void main(String[] args)
/* 219:    */   {
/* 220:    */     try
/* 221:    */     {
/* 222:247 */       LatticeLearner localLatticeLearner = new LatticeLearner();
/* 223:    */     }
/* 224:    */     catch (Exception e)
/* 225:    */     {
/* 226:249 */       e.printStackTrace();
/* 227:    */     }
/* 228:    */   }
/* 229:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     kevinWhite.LatticeLearner
 * JD-Core Version:    0.7.0.1
 */