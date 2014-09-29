/*   1:    */ package matchers;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import matchers.representations.BindingPair;
/*   7:    */ import matchers.representations.EntityMatchResult;
/*   8:    */ import matchers.representations.ThreadMatchResult;
/*   9:    */ import start.Generator;
/*  10:    */ import tools.Getters;
/*  11:    */ import translator.Translator;
/*  12:    */ import utils.Mark;
/*  13:    */ 
/*  14:    */ public class EntityMatcher
/*  15:    */ {
/*  16:    */   public static enum MatchMode
/*  17:    */   {
/*  18: 26 */     BASIC,  SCORE;
/*  19:    */   }
/*  20:    */   
/*  21: 29 */   public MatchMode patternMatchMode = MatchMode.BASIC;
/*  22: 31 */   public float score_cutoff = 0.1F;
/*  23: 33 */   private ThreadMatcher threadMatcher = new ThreadMatcher();
/*  24: 36 */   private boolean sequencePatternMatch = true;
/*  25: 38 */   private boolean strictTypeMatching = true;
/*  26: 40 */   private boolean includeAllBindings = false;
/*  27:    */   
/*  28:    */   public void includeAll()
/*  29:    */   {
/*  30: 43 */     this.includeAllBindings = true;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void includeOnlyEnitites()
/*  34:    */   {
/*  35: 47 */     this.includeAllBindings = false;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void useScoreMatching()
/*  39:    */   {
/*  40: 51 */     this.threadMatcher.useScoreMatching();
/*  41: 52 */     this.patternMatchMode = MatchMode.SCORE;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void useIdentityMatching()
/*  45:    */   {
/*  46: 56 */     this.threadMatcher.useIdentityMatching();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public synchronized EntityMatchResult match(Entity pattern, Entity datum)
/*  50:    */   {
/*  51: 68 */     if ((pattern == null) || (datum == null)) {
/*  52: 68 */       return new EntityMatchResult();
/*  53:    */     }
/*  54: 70 */     boolean hasNotFeature1 = pattern.hasFeature("not");
/*  55: 71 */     boolean hasNotFeature2 = datum.hasFeature("not");
/*  56: 72 */     boolean inversion = hasNotFeature1 ^ hasNotFeature2;
/*  57:    */     
/*  58: 74 */     ThreadMatchResult threadMatch = this.threadMatcher.match(pattern, datum);
/*  59:    */     
/*  60: 76 */     EntityMatchResult result = new EntityMatchResult();
/*  61: 79 */     if (((pattern.entityP()) || (datum.entityP())) && (pattern.isA("action")) && (datum.isA("action")))
/*  62:    */     {
/*  63: 80 */       List<BindingPair> bindings = new ArrayList();
/*  64: 81 */       bindings.add(new BindingPair(pattern, datum, threadMatch.score));
/*  65:    */       
/*  66: 83 */       result = new EntityMatchResult(1.0D, inversion, true, bindings);
/*  67:    */     }
/*  68: 86 */     else if ((pattern.functionP("appear")) && (pattern.getSubject().entityP()) && (pattern.getSubject().isA("action")) && 
/*  69: 87 */       (datum.isA("action")))
/*  70:    */     {
/*  71: 88 */       List<BindingPair> bindings = new ArrayList();
/*  72: 89 */       bindings.add(new BindingPair(pattern.getSubject(), datum, threadMatch.score));
/*  73:    */       
/*  74: 91 */       result = new EntityMatchResult(1.0D, inversion, true, bindings);
/*  75:    */     }
/*  76: 95 */     else if (((pattern.entityP("something")) || (datum.entityP("something"))) && (
/*  77: 96 */       (pattern.relationP()) || (datum.relationP()) || (pattern.entityP()) || (datum.entityP()) || (pattern.functionP()) || (datum.functionP())))
/*  78:    */     {
/*  79: 98 */       List<BindingPair> bindings = new ArrayList();
/*  80: 99 */       bindings.add(new BindingPair(pattern, datum, threadMatch.score));
/*  81:    */       
/*  82:101 */       result = new EntityMatchResult(1.0D, inversion, true, bindings);
/*  83:    */     }
/*  84:    */     else
/*  85:    */     {
/*  86:    */       Entity aActor;
/*  87:105 */       if ((pattern.relationP("perform")) && (datum.getSubject().isNotA("you")))
/*  88:    */       {
/*  89:106 */         Entity performElt = pattern;
/*  90:107 */         Entity actionElt = datum;
/*  91:    */         
/*  92:109 */         Entity pActor = performElt.getSubject();
/*  93:110 */         Entity pAction = Getters.getObject(performElt);
/*  94:112 */         if ((pAction != null) && (pAction.isA("action"))) {
/*  95:114 */           if ((actionElt.isA("action")) && ((actionElt.relationP()) || (actionElt.functionP())))
/*  96:    */           {
/*  97:115 */             aActor = actionElt.getSubject();
/*  98:116 */             Entity aAction = actionElt;
/*  99:    */             
/* 100:118 */             List<BindingPair> bindings = new ArrayList();
/* 101:119 */             bindings.add(new BindingPair(pActor, aActor, 1.0D));
/* 102:120 */             bindings.add(new BindingPair(pAction, aAction, 1.0D));
/* 103:    */             
/* 104:122 */             result = new EntityMatchResult(1.0D, false, true, bindings);
/* 105:    */           }
/* 106:    */         }
/* 107:    */       }
/* 108:129 */       else if ((pattern.entityP()) && (datum.entityP()))
/* 109:    */       {
/* 110:130 */         List<BindingPair> bindings = new ArrayList();
/* 111:131 */         bindings.add(new BindingPair(pattern, datum, threadMatch.score));
/* 112:    */         
/* 113:133 */         result = new EntityMatchResult(threadMatch.score, inversion, true, bindings);
/* 114:    */       }
/* 115:137 */       else if ((pattern.functionP()) && (datum.functionP()))
/* 116:    */       {
/* 117:138 */         List<EntityMatchResult> results = new ArrayList();
/* 118:    */         
/* 119:140 */         EntityMatchResult subjectResult = match(pattern.getSubject(), datum.getSubject());
/* 120:    */         
/* 121:142 */         results.add(subjectResult);
/* 122:    */         
/* 123:144 */         result = combineResults(threadMatch.score, inversion, results);
/* 124:    */       }
/* 125:148 */       else if ((pattern.relationP()) && (datum.relationP()))
/* 126:    */       {
/* 127:149 */         List<EntityMatchResult> results = new ArrayList();
/* 128:    */         
/* 129:151 */         EntityMatchResult objectResult = match(pattern.getObject(), datum.getObject());
/* 130:152 */         EntityMatchResult subjectResult = match(pattern.getSubject(), datum.getSubject());
/* 131:    */         
/* 132:154 */         results.add(objectResult);
/* 133:155 */         results.add(subjectResult);
/* 134:    */         
/* 135:157 */         result = combineResults(threadMatch.score, inversion, results);
/* 136:    */       }
/* 137:163 */       else if ((pattern.sequenceP()) && (datum.sequenceP()))
/* 138:    */       {
/* 139:164 */         List<EntityMatchResult> results = new ArrayList();
/* 140:    */         
/* 141:166 */         boolean structureMatch = true;
/* 142:167 */         List<BindingPair> bindings = new ArrayList();
/* 143:169 */         if (((this.sequencePatternMatch) && (pattern.getNumberOfChildren() > datum.getNumberOfChildren())) || (
/* 144:170 */           (!this.sequencePatternMatch) && (pattern.getNumberOfChildren() != datum.getNumberOfChildren()))) {
/* 145:171 */           results.add(new EntityMatchResult());
/* 146:    */         }
/* 147:174 */         for (Entity child1 : pattern.getElements())
/* 148:    */         {
/* 149:175 */           boolean found = false;
/* 150:176 */           EntityMatchResult childResult = null;
/* 151:177 */           EntityMatchResult bestResult = null;
/* 152:178 */           for (Entity child2 : datum.getElements())
/* 153:    */           {
/* 154:179 */             childResult = match(child1, child2);
/* 155:180 */             if (bestResult == null) {
/* 156:180 */               bestResult = childResult;
/* 157:    */             }
/* 158:181 */             if (childResult.score > bestResult.score) {
/* 159:181 */               bestResult = childResult;
/* 160:    */             }
/* 161:    */           }
/* 162:183 */           if ((bestResult != null) && (bestResult.score > 0.0D)) {
/* 163:184 */             results.add(bestResult);
/* 164:    */           } else {
/* 165:187 */             results.add(new EntityMatchResult());
/* 166:    */           }
/* 167:    */         }
/* 168:191 */         if (results.isEmpty()) {
/* 169:191 */           results.add(new EntityMatchResult(1.0D, false, true, new ArrayList()));
/* 170:    */         }
/* 171:193 */         result = combineResults(threadMatch.score, inversion, results);
/* 172:    */       }
/* 173:    */     }
/* 174:196 */     if ((this.includeAllBindings) && ((!pattern.entityP()) || (!datum.entityP()))) {
/* 175:198 */       result.bindings.add(new BindingPair(pattern, datum, threadMatch.score));
/* 176:    */     }
/* 177:205 */     if ((result.score < this.score_cutoff) && (this.patternMatchMode == MatchMode.SCORE)) {
/* 178:205 */       result.semanticMatch = false;
/* 179:    */     }
/* 180:209 */     return result;
/* 181:    */   }
/* 182:    */   
/* 183:    */   private EntityMatchResult combineResults(double score, boolean inversion, List<EntityMatchResult> results)
/* 184:    */   {
/* 185:220 */     if (results.isEmpty()) {
/* 186:220 */       return new EntityMatchResult();
/* 187:    */     }
/* 188:221 */     List<BindingPair> bindings = new ArrayList();
/* 189:222 */     boolean structureMatch = true;
/* 190:223 */     boolean match = true;
/* 191:224 */     boolean stackedInversion = false;
/* 192:225 */     for (EntityMatchResult result : results)
/* 193:    */     {
/* 194:226 */       if (score >= 0.0D) {
/* 195:227 */         if (result.score >= 0.0D) {
/* 196:228 */           score *= result.score;
/* 197:    */         } else {
/* 198:230 */           score = -1.0D;
/* 199:    */         }
/* 200:    */       }
/* 201:233 */       if (result.inversion) {
/* 202:233 */         stackedInversion = true;
/* 203:    */       }
/* 204:234 */       match = (match) && (result.semanticMatch);
/* 205:235 */       bindings.addAll(result.bindings);
/* 206:236 */       structureMatch = (structureMatch) && (result.structureMatch);
/* 207:    */     }
/* 208:238 */     if (stackedInversion) {
/* 209:238 */       inversion = !inversion;
/* 210:    */     }
/* 211:239 */     return new EntityMatchResult(match, score, inversion, structureMatch, bindings);
/* 212:    */   }
/* 213:    */   
/* 214:    */   public static void main(String[] args)
/* 215:    */     throws Exception
/* 216:    */   {
/* 217:243 */     Mark.say(
/* 218:    */     
/* 219:    */ 
/* 220:    */ 
/* 221:    */ 
/* 222:    */ 
/* 223:    */ 
/* 224:    */ 
/* 225:    */ 
/* 226:    */ 
/* 227:    */ 
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:    */ 
/* 232:    */ 
/* 233:    */ 
/* 234:    */ 
/* 235:    */ 
/* 236:    */ 
/* 237:    */ 
/* 238:    */ 
/* 239:    */ 
/* 240:    */ 
/* 241:    */ 
/* 242:268 */       new Object[] { "Initializing Start..." });Translator translator = Translator.getTranslator();Generator generator = Generator.getGenerator();generator.setStoryMode();generator.flush();Mark.say(new Object[] { "Doing translation..." });Entity e1 = translator.translate("mary is a person").getElement(0);Entity e2 = translator.translate("john is a person").getElement(0);Entity e3 = translator.translate("sally is a person").getElement(0);Entity e4 = translator.translate("mark is a person").getElement(0);Entity e5 = translator.translate("Mary thinks Mark likes Sally if John tells Mar").getElement(0);Entity e6 = translator.translate("If john tells mary something then mary thinks something.").getElement(0);Entity e7 = translator.translate("John tells Mary, \"Mark likes Sally\".").getElement(0);Entity e8 = translator.translate("John tells Mary something.").getElement(0);Mark.say(new Object[] { e1 });Mark.say(new Object[] { e2 });Mark.say(new Object[] { e3 });Mark.say(new Object[] { e4 });Mark.say(new Object[] { e5 });Mark.say(new Object[] { e6 });Mark.say(new Object[] { e7 });Mark.say(new Object[] { e8 });
/* 243:    */   }
/* 244:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matchers.EntityMatcher
 * JD-Core Version:    0.7.0.1
 */