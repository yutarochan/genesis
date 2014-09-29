/*   1:    */ package matchers;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import genesis.GenesisControls;
/*   5:    */ import javax.swing.JCheckBox;
/*   6:    */ import matchers.original.BasicMatcherOriginal;
/*   7:    */ import matchers.representations.BindingPair;
/*   8:    */ import matchers.representations.EntityMatchResult;
/*   9:    */ import minilisp.LList;
/*  10:    */ import utils.Mark;
/*  11:    */ import utils.PairOfEntities;
/*  12:    */ 
/*  13:    */ public class StandardMatcher
/*  14:    */ {
/*  15:    */   private EntityMatcher em;
/*  16:    */   private BindingValidator bv;
/*  17:    */   private static StandardMatcher standardMatcher;
/*  18:    */   private static StandardMatcher identityMatcher;
/*  19:    */   
/*  20:    */   private EntityMatcher getEntityMatcher()
/*  21:    */   {
/*  22: 27 */     return this.em;
/*  23:    */   }
/*  24:    */   
/*  25:    */   private BindingValidator getBindingValidator()
/*  26:    */   {
/*  27: 31 */     return this.bv;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public StandardMatcher()
/*  31:    */   {
/*  32: 36 */     this.em = new EntityMatcher();
/*  33: 37 */     this.bv = new BindingValidator();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static StandardMatcher getBasicMatcher()
/*  37:    */   {
/*  38: 49 */     if (standardMatcher == null) {
/*  39: 50 */       standardMatcher = new StandardMatcher();
/*  40:    */     }
/*  41: 52 */     return standardMatcher;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static StandardMatcher getIdentityMatcher()
/*  45:    */   {
/*  46: 63 */     if (identityMatcher == null)
/*  47:    */     {
/*  48: 64 */       identityMatcher = new StandardMatcher();
/*  49: 65 */       identityMatcher.getEntityMatcher().useIdentityMatching();
/*  50:    */     }
/*  51: 67 */     return identityMatcher;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void useIdentityMatching()
/*  55:    */   {
/*  56: 75 */     this.em.useIdentityMatching();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean matchStructures(Entity pattern, Entity datum)
/*  60:    */   {
/*  61: 88 */     EntityMatchResult result = this.em.match(pattern, datum);
/*  62: 90 */     if (!GenesisControls.useNewMatcherCheckBox.isSelected())
/*  63:    */     {
/*  64: 91 */       boolean old_result = BasicMatcherOriginal.getBasicMatcher().matchStructures(pattern, datum);
/*  65: 92 */       if ((GenesisControls.reportMatchingDifferencesCheckBox.isSelected()) && 
/*  66: 93 */         (old_result != result.structureMatch))
/*  67:    */       {
/*  68: 94 */         Mark.err(new Object[] {"Difference in matching detected!" });
/*  69: 95 */         Mark.say(new Object[] {pattern });
/*  70: 96 */         Mark.say(new Object[] {datum });
/*  71: 97 */         Mark.say(new Object[] {"New Result: " + result.structureMatch });
/*  72: 98 */         Mark.say(new Object[] {"Old Result: " + old_result });
/*  73:    */       }
/*  74:101 */       return old_result;
/*  75:    */     }
/*  76:103 */     return result.structureMatch;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public LList<PairOfEntities> matchRuleToInstantiation(Entity rule, Entity instantiation)
/*  80:    */   {
/*  81:115 */     EntityMatchResult object_result = this.em.match(rule.getObject(), instantiation.getObject());
/*  82:116 */     object_result.bindings = this.bv.validateBindings(object_result.bindings);
/*  83:117 */     if (object_result.bindings == null) {
/*  84:117 */       return null;
/*  85:    */     }
/*  86:118 */     EntityMatchResult subject_result = this.em.match(rule.getSubject(), instantiation.getSubject());
/*  87:119 */     subject_result.bindings = this.bv.validateBindings(subject_result.bindings, object_result.bindings);
/*  88:121 */     if (!GenesisControls.useNewMatcherCheckBox.isSelected())
/*  89:    */     {
/*  90:122 */       LList<PairOfEntities> old_result = BasicMatcherOriginal.getBasicMatcher().matchRuleToInstantiation(rule, instantiation);
/*  91:123 */       if ((GenesisControls.reportMatchingDifferencesCheckBox.isSelected()) && 
/*  92:124 */         (!BindingValidator.equivalencyCheck(subject_result.toLList(), old_result)))
/*  93:    */       {
/*  94:125 */         Mark.err(new Object[] {"Difference in matching detected!" });
/*  95:126 */         Mark.say(new Object[] {rule });
/*  96:127 */         Mark.say(new Object[] {instantiation });
/*  97:128 */         Mark.say(new Object[] {"New Result: " + subject_result.toLList() });
/*  98:129 */         Mark.say(new Object[] {"Old Result: " + old_result });
/*  99:    */       }
/* 100:132 */       return old_result;
/* 101:    */     }
/* 102:135 */     return subject_result.toLList();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public LList<PairOfEntities> match(Entity pattern, Entity datum)
/* 106:    */   {
/* 107:146 */     EntityMatchResult result = this.em.match(pattern, datum);
/* 108:147 */     result.bindings = this.bv.validateBindings(result.bindings);
/* 109:149 */     if (!GenesisControls.useNewMatcherCheckBox.isSelected())
/* 110:    */     {
/* 111:150 */       LList<PairOfEntities> old_result = BasicMatcherOriginal.getBasicMatcher().match(pattern, datum);
/* 112:151 */       if ((GenesisControls.reportMatchingDifferencesCheckBox.isSelected()) && 
/* 113:152 */         (!BindingValidator.equivalencyCheck(result.toLList(), old_result)))
/* 114:    */       {
/* 115:153 */         Mark.err(new Object[] {"Difference in matching detected!" });
/* 116:154 */         Mark.say(new Object[] {pattern });
/* 117:155 */         Mark.say(new Object[] {datum });
/* 118:156 */         Mark.say(new Object[] {"New Result: " + result.toLList() });
/* 119:157 */         Mark.say(new Object[] {"Old Result: " + old_result });
/* 120:    */       }
/* 121:160 */       return old_result;
/* 122:    */     }
/* 123:163 */     return result.toLList();
/* 124:    */   }
/* 125:    */   
/* 126:    */   public LList<PairOfEntities> match(Entity pattern, Entity datum, LList<PairOfEntities> constraints)
/* 127:    */   {
/* 128:176 */     EntityMatchResult result = this.em.match(pattern, datum);
/* 129:177 */     result.bindings = this.bv.validateBindings(result.bindings, constraints);
/* 130:179 */     if (!GenesisControls.useNewMatcherCheckBox.isSelected())
/* 131:    */     {
/* 132:180 */       LList<PairOfEntities> old_result = BasicMatcherOriginal.getBasicMatcher().match(pattern, datum, constraints);
/* 133:181 */       if ((GenesisControls.reportMatchingDifferencesCheckBox.isSelected()) && 
/* 134:182 */         (!BindingValidator.equivalencyCheck(result.toLList(), old_result)))
/* 135:    */       {
/* 136:183 */         Mark.err(new Object[] {"Difference in matching detected!" });
/* 137:184 */         Mark.say(new Object[] {pattern });
/* 138:185 */         Mark.say(new Object[] {datum });
/* 139:186 */         Mark.say(new Object[] {"New Result: " + result.toLList() });
/* 140:187 */         Mark.say(new Object[] {"Old Result: " + old_result });
/* 141:188 */         old_result = BasicMatcherOriginal.getBasicMatcher().match(pattern, datum, constraints);
/* 142:189 */         result = this.em.match(pattern, datum);
/* 143:190 */         result.bindings = this.bv.validateBindings(result.bindings, constraints);
/* 144:    */       }
/* 145:193 */       return old_result;
/* 146:    */     }
/* 147:196 */     return result.toLList();
/* 148:    */   }
/* 149:    */   
/* 150:    */   public LList<PairOfEntities> matchAll(Entity pattern, Entity datum)
/* 151:    */   {
/* 152:209 */     this.em.includeAll();
/* 153:210 */     EntityMatchResult result = this.em.match(pattern, datum);
/* 154:211 */     this.em.includeOnlyEnitites();
/* 155:212 */     result.bindings = this.bv.validateBindings(result.bindings);
/* 156:214 */     if (!GenesisControls.useNewMatcherCheckBox.isSelected())
/* 157:    */     {
/* 158:215 */       LList<PairOfEntities> old_result = BasicMatcherOriginal.getBasicMatcher().matchAll(pattern, datum);
/* 159:216 */       if ((GenesisControls.reportMatchingDifferencesCheckBox.isSelected()) && 
/* 160:217 */         (!BindingValidator.equivalencyCheck(result.toLList(), old_result)))
/* 161:    */       {
/* 162:218 */         Mark.err(new Object[] {"Difference in matching detected!" });
/* 163:219 */         Mark.say(new Object[] {pattern });
/* 164:220 */         Mark.say(new Object[] {datum });
/* 165:221 */         Mark.say(new Object[] {"New Result: " + result.toLList() });
/* 166:222 */         Mark.say(new Object[] {"Old Result: " + old_result });
/* 167:    */       }
/* 168:225 */       return old_result;
/* 169:    */     }
/* 170:228 */     return result.toLList();
/* 171:    */   }
/* 172:    */   
/* 173:    */   public LList<PairOfEntities> matchNegation(Entity pattern, Entity datum, LList<PairOfEntities> constraints)
/* 174:    */   {
/* 175:241 */     EntityMatchResult result = this.em.match(pattern, datum);
/* 176:    */     
/* 177:243 */     result.bindings = this.bv.validateBindings(result.bindings, constraints);
/* 178:245 */     if (!GenesisControls.useNewMatcherCheckBox.isSelected())
/* 179:    */     {
/* 180:246 */       LList<PairOfEntities> old_result = BasicMatcherOriginal.getBasicMatcher().matchNegation(datum, pattern, constraints);
/* 181:247 */       if ((GenesisControls.reportMatchingDifferencesCheckBox.isSelected()) && 
/* 182:248 */         (!BindingValidator.equivalencyCheck(result.toNegationLList(), old_result)))
/* 183:    */       {
/* 184:249 */         Mark.err(new Object[] {"Difference in matching detected!" });
/* 185:250 */         Mark.say(new Object[] {pattern });
/* 186:251 */         Mark.say(new Object[] {datum });
/* 187:252 */         Mark.say(new Object[] {"New Result: " + result.toLList() });
/* 188:253 */         Mark.say(new Object[] {"Old Result: " + old_result });
/* 189:    */       }
/* 190:256 */       return old_result;
/* 191:    */     }
/* 192:259 */     return result.toNegationLList();
/* 193:    */   }
/* 194:    */   
/* 195:    */   public LList<PairOfEntities> matchAnyPart(Entity pattern, Entity datum)
/* 196:    */   {
/* 197:271 */     if (!GenesisControls.useNewMatcherCheckBox.isSelected())
/* 198:    */     {
/* 199:272 */       if (GenesisControls.reportMatchingDifferencesCheckBox.isSelected()) {
/* 200:273 */         Mark.err(new Object[] {"No validation check available for matchAnyPart" });
/* 201:    */       }
/* 202:275 */       return BasicMatcherOriginal.getBasicMatcher().matchAnyPart(pattern, datum);
/* 203:    */     }
/* 204:278 */     if ((datum == null) || (pattern == null)) {
/* 205:278 */       return null;
/* 206:    */     }
/* 207:280 */     EntityMatchResult result = this.em.match(pattern, datum);
/* 208:281 */     result.bindings = this.bv.validateBindings(result.bindings);
/* 209:282 */     if (result.toLList() != null) {
/* 210:282 */       return result.toLList();
/* 211:    */     }
/* 212:285 */     if (datum.entityP()) {
/* 213:285 */       return null;
/* 214:    */     }
/* 215:287 */     if (datum.sequenceP()) {
/* 216:288 */       for (Entity e : datum.getElements())
/* 217:    */       {
/* 218:289 */         result = this.em.match(pattern, e);
/* 219:290 */         result.bindings = this.bv.validateBindings(result.bindings);
/* 220:291 */         if (result.toLList() != null) {
/* 221:291 */           return result.toLList();
/* 222:    */         }
/* 223:    */       }
/* 224:    */     }
/* 225:295 */     if (datum.relationP())
/* 226:    */     {
/* 227:296 */       Entity e = datum.getSubject();
/* 228:297 */       result = this.em.match(pattern, e);
/* 229:298 */       result.bindings = this.bv.validateBindings(result.bindings);
/* 230:299 */       if (result.toLList() != null) {
/* 231:299 */         return result.toLList();
/* 232:    */       }
/* 233:    */     }
/* 234:302 */     if ((datum.functionP()) || (datum.relationP()))
/* 235:    */     {
/* 236:303 */       Entity e = datum.getSubject();
/* 237:304 */       result = this.em.match(pattern, e);
/* 238:305 */       result.bindings = this.bv.validateBindings(result.bindings);
/* 239:306 */       if (result.toLList() != null) {
/* 240:306 */         return result.toLList();
/* 241:    */       }
/* 242:    */     }
/* 243:309 */     return null;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public double distance(Entity imaginedDescription, Entity rememberedDescription)
/* 247:    */   {
/* 248:320 */     this.em.includeAll();
/* 249:321 */     EntityMatchResult result = this.em.match(imaginedDescription, rememberedDescription);
/* 250:322 */     this.em.includeOnlyEnitites();
/* 251:    */     
/* 252:324 */     double distance = 0.0D;
/* 253:325 */     for (BindingPair pair : result.bindings) {
/* 254:326 */       distance += pair.getScore();
/* 255:    */     }
/* 256:329 */     if (!GenesisControls.useNewMatcherCheckBox.isSelected())
/* 257:    */     {
/* 258:330 */       double old_result = BasicMatcherOriginal.getBasicMatcher().distance(imaginedDescription, rememberedDescription);
/* 259:331 */       if ((GenesisControls.reportMatchingDifferencesCheckBox.isSelected()) && 
/* 260:332 */         (distance != old_result))
/* 261:    */       {
/* 262:333 */         Mark.err(new Object[] {"Difference in matching detected!" });
/* 263:334 */         Mark.say(new Object[] {"NOTE: Distances not gaurenteed to be equivalent, just compatable" });
/* 264:335 */         Mark.say(new Object[] {imaginedDescription });
/* 265:336 */         Mark.say(new Object[] {rememberedDescription });
/* 266:337 */         Mark.say(new Object[] {"New Result: " + distance });
/* 267:338 */         Mark.say(new Object[] {"Old Result: " + old_result });
/* 268:    */       }
/* 269:341 */       return old_result;
/* 270:    */     }
/* 271:344 */     return distance;
/* 272:    */   }
/* 273:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matchers.StandardMatcher
 * JD-Core Version:    0.7.0.1
 */