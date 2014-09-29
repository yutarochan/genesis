/*   1:    */ package leonidFedorov;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Relation;
/*   6:    */ import bridge.reps.entities.Sequence;
/*   7:    */ import connections.AbstractWiredBox;
/*   8:    */ import connections.Connections;
/*   9:    */ import connections.Ports;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Arrays;
/*  12:    */ import java.util.Iterator;
/*  13:    */ import java.util.LinkedList;
/*  14:    */ import java.util.Vector;
/*  15:    */ import matchers.StandardMatcher;
/*  16:    */ import minilisp.LList;
/*  17:    */ import storyProcessor.ReflectionAnalysis;
/*  18:    */ import storyProcessor.ReflectionDescription;
/*  19:    */ import utils.Mark;
/*  20:    */ import utils.PairOfEntities;
/*  21:    */ 
/*  22:    */ public class RuleToReflectionMatcher
/*  23:    */   extends AbstractWiredBox
/*  24:    */ {
/*  25: 19 */   private int index = 0;
/*  26: 21 */   private boolean debug = true;
/*  27:    */   public static final String BIAS_LISTENER2 = "bias listener port 2";
/*  28:    */   public static final String BIAS_RULES2 = "bias rule port2";
/*  29:    */   public static final String RAW_RULES1 = "raw rule port 1";
/*  30:    */   public static final String RAW_RULES2 = "raw rule port 2";
/*  31:    */   public static final String INSTANTIATED_RULES1 = "instantiated rule port 1";
/*  32:    */   public static final String INSTANTIATED_RULES2 = "instantiated rule port 2";
/*  33: 32 */   private ArrayList<AugmentedReflectionDescription> allAugmentedDescriptions1 = new ArrayList();
/*  34: 33 */   private ArrayList<AugmentedReflectionDescription> allAugmentedDescriptions2 = new ArrayList();
/*  35: 35 */   private ArrayList<AugmentedReflectionDescription> augmentedDescriptions1 = new ArrayList();
/*  36: 36 */   private ArrayList<AugmentedReflectionDescription> augmentedDescriptions2 = new ArrayList();
/*  37:    */   public static final String COMMONSENSE_LISTENER1 = "Raw commonsense rule listener port 1";
/*  38:    */   public static final String COMMONSENSE_LISTENER2 = "Raw commonsense rule listener port 2";
/*  39:    */   public static final String REFLECTION_LISTENER1 = "Reflection listener port 1";
/*  40:    */   public static final String REFLECTION_LISTENER2 = "Reflection listener port 2";
/*  41:    */   public static final String SINGLE_INST_REFLECTION1 = "Single instantiated reflection port 1";
/*  42:    */   public static final String SINGLE_INST_REFLECTION2 = "Single instantiated reflection port 2";
/*  43:    */   public static final String SINGLE_RAW_REFLECTION1 = "Single raw reflection port 1";
/*  44:    */   public static final String SINGLE_RAW_REFLECTION2 = "Single raw reflection port 2";
/*  45: 52 */   private LinkedList<Entity> rawRules1 = new LinkedList();
/*  46: 53 */   private LinkedList<Entity> rawRules2 = new LinkedList();
/*  47: 61 */   private boolean areAllRulesRead = false;
/*  48:    */   
/*  49:    */   public boolean areAllRulesRead()
/*  50:    */   {
/*  51: 64 */     return this.areAllRulesRead;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public RuleToReflectionMatcher()
/*  55:    */   {
/*  56: 68 */     setName("Rule to Reflection matcher box");
/*  57: 69 */     Mark.say(new Object[] {Boolean.valueOf(this.debug), "Rule to Reflection matcher object created" });
/*  58:    */     
/*  59:    */ 
/*  60:    */ 
/*  61: 73 */     Connections.getPorts(this).addSignalProcessor("Raw commonsense rule listener port 1", "catchRawRules1");
/*  62: 74 */     Connections.getPorts(this).addSignalProcessor("Raw commonsense rule listener port 2", "catchRawRules2");
/*  63:    */     
/*  64:    */ 
/*  65: 77 */     Connections.getPorts(this).addSignalProcessor("Reflection listener port 1", "extractInstRulesFromReflections1");
/*  66: 78 */     Connections.getPorts(this).addSignalProcessor("Reflection listener port 2", "extractInstRulesFromReflections2");
/*  67:    */     
/*  68:    */ 
/*  69: 81 */     Connections.getPorts(this).addSignalProcessor("Single instantiated reflection port 1", "catchInstReflection1");
/*  70: 82 */     Connections.getPorts(this).addSignalProcessor("Single instantiated reflection port 2", "catchInstReflection2");
/*  71:    */     
/*  72:    */ 
/*  73: 85 */     Connections.getPorts(this).addSignalProcessor("Single raw reflection port 1", "catchRawReflection1");
/*  74: 86 */     Connections.getPorts(this).addSignalProcessor("Single raw reflection port 2", "catchRawReflection2");
/*  75:    */     
/*  76:    */ 
/*  77: 89 */     Connections.getPorts(this).addSignalProcessor("bias listener port 2", "extractMayRules2");
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void extractMayRules2(Object signal)
/*  81:    */   {
/*  82: 94 */     if ((signal instanceof String))
/*  83:    */     {
/*  84: 95 */       String str = (String)signal;
/*  85: 96 */       if ("insertBias".equalsIgnoreCase(str))
/*  86:    */       {
/*  87: 98 */         Mark.say(new Object[] {Boolean.valueOf(this.debug), ":::==>Insert bias idiom found", str });
/*  88: 99 */         this.index += 1;
/*  89:100 */         Mark.say(new Object[] {":::==>SIGNAL received # times: ", Integer.valueOf(this.index) });
/*  90:    */         Iterator localIterator2;
/*  91:103 */         for (Iterator localIterator1 = this.augmentedDescriptions1.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/*  92:    */         {
/*  93:103 */           AugmentedReflectionDescription x = (AugmentedReflectionDescription)localIterator1.next();
/*  94:104 */           Mark.say(new Object[] {"augmented description", Integer.valueOf(this.augmentedDescriptions1.indexOf(x)), x.getReflectionDescription().getName() });
/*  95:105 */           localIterator2 = x.getRawRules().getElements().iterator(); continue;Entity tRawRule = (Entity)localIterator2.next();
/*  96:106 */           Relation rawRule = (Relation)tRawRule;
/*  97:112 */           if (rawRule.isA("explanation"))
/*  98:    */           {
/*  99:113 */             Relation rule = (Relation)rawRule.deepClone();
/* 100:114 */             Entity t = rule.getObject();
/* 101:115 */             t.removeFeature("tentative");
/* 102:116 */             rule.removeType("explanation");
/* 103:117 */             rule.addType("prediction");
/* 104:118 */             Mark.say(new Object[] {":::==>T: types in a bundle", Arrays.toString(rule.getBundle().getAllTypes()) });
/* 105:    */             
/* 106:    */ 
/* 107:121 */             Mark.say(new Object[] {":::==>rule constructed and transmitted", rule });
/* 108:122 */             Connections.getPorts(this).transmit("bias rule port2", rule);
/* 109:    */           }
/* 110:    */         }
/* 111:    */       }
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void catchRawRules1(Object signal)
/* 116:    */   {
/* 117:132 */     if ((signal instanceof Sequence))
/* 118:    */     {
/* 119:133 */       Sequence s = (Sequence)signal;
/* 120:134 */       Mark.say(new Object[] {Boolean.valueOf(this.debug), ":::==>New raw rule received: ", s.asString() });
/* 121:135 */       this.rawRules1 = ((LinkedList)s.getAllComponents());
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void catchRawRules2(Object signal)
/* 126:    */   {
/* 127:148 */     if ((signal instanceof Sequence))
/* 128:    */     {
/* 129:149 */       Sequence s = (Sequence)signal;
/* 130:150 */       Mark.say(new Object[] {Boolean.valueOf(this.debug), ":::==>New raw rule received: ", s.asString() });
/* 131:151 */       this.rawRules2 = ((LinkedList)s.getAllComponents());
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void extractInstRulesFromReflections1(Object signal)
/* 136:    */   {
/* 137:163 */     if ((signal instanceof ReflectionAnalysis))
/* 138:    */     {
/* 139:164 */       ReflectionAnalysis analysis = (ReflectionAnalysis)signal;
/* 140:165 */       this.augmentedDescriptions1.clear();
/* 141:166 */       for (ReflectionDescription description : analysis.getReflectionDescriptions())
/* 142:    */       {
/* 143:167 */         Sequence matchedRules = matchRulesToReflections(description, this.rawRules1);
/* 144:168 */         this.augmentedDescriptions1.add(new AugmentedReflectionDescription(description, matchedRules));
/* 145:    */       }
/* 146:170 */       this.allAugmentedDescriptions1.addAll(this.augmentedDescriptions1);
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void extractInstRulesFromReflections2(Object signal)
/* 151:    */   {
/* 152:175 */     if ((signal instanceof ReflectionAnalysis))
/* 153:    */     {
/* 154:176 */       ReflectionAnalysis analysis = (ReflectionAnalysis)signal;
/* 155:177 */       this.augmentedDescriptions2.clear();
/* 156:178 */       for (ReflectionDescription description : analysis.getReflectionDescriptions())
/* 157:    */       {
/* 158:179 */         Sequence matchedRules = matchRulesToReflections(description, this.rawRules2);
/* 159:180 */         this.augmentedDescriptions2.add(new AugmentedReflectionDescription(description, matchedRules));
/* 160:    */       }
/* 161:182 */       this.allAugmentedDescriptions2.addAll(this.augmentedDescriptions2);
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void catchInstReflection1(Object signal)
/* 166:    */   {
/* 167:187 */     if ((signal instanceof ReflectionDescription))
/* 168:    */     {
/* 169:188 */       ReflectionDescription completion = (ReflectionDescription)signal;
/* 170:189 */       String name = completion.getName();
/* 171:190 */       Sequence rules = getInstRuleBundle(name, this.augmentedDescriptions1);
/* 172:191 */       Connections.getPorts(this).transmit("instantiated rule port 1", rules);
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void catchInstReflection2(Object signal)
/* 177:    */   {
/* 178:196 */     if ((signal instanceof ReflectionDescription))
/* 179:    */     {
/* 180:197 */       ReflectionDescription completion = (ReflectionDescription)signal;
/* 181:198 */       String name = completion.getName();
/* 182:199 */       Sequence rules = getInstRuleBundle(name, this.augmentedDescriptions2);
/* 183:200 */       Connections.getPorts(this).transmit("instantiated rule port 2", rules);
/* 184:    */     }
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void catchRawReflection1(Object signal)
/* 188:    */   {
/* 189:205 */     if ((signal instanceof ReflectionDescription))
/* 190:    */     {
/* 191:206 */       ReflectionDescription completion = (ReflectionDescription)signal;
/* 192:207 */       String name = completion.getName();
/* 193:208 */       Sequence rules = getRawRuleBundle(name, this.augmentedDescriptions1);
/* 194:209 */       Connections.getPorts(this).transmit("raw rule port 1", rules);
/* 195:    */     }
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void catchRawReflection2(Object signal)
/* 199:    */   {
/* 200:214 */     if ((signal instanceof ReflectionDescription))
/* 201:    */     {
/* 202:215 */       ReflectionDescription completion = (ReflectionDescription)signal;
/* 203:216 */       String name = completion.getName();
/* 204:217 */       Sequence rules = getRawRuleBundle(name, this.augmentedDescriptions2);
/* 205:218 */       Connections.getPorts(this).transmit("raw rule port 2", rules);
/* 206:    */     }
/* 207:    */   }
/* 208:    */   
/* 209:    */   private Sequence matchRulesToReflections(ReflectionDescription description, LinkedList<Entity> rawRules)
/* 210:    */   {
/* 211:224 */     Sequence matchedRules = new Sequence();
/* 212:225 */     StandardMatcher bam = StandardMatcher.getBasicMatcher();
/* 213:    */     Iterator localIterator2;
/* 214:226 */     for (Iterator localIterator1 = description.getRules().getElements().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 215:    */     {
/* 216:226 */       Entity t = (Entity)localIterator1.next();
/* 217:    */       
/* 218:228 */       localIterator2 = rawRules.iterator(); continue;Entity rawRule = (Entity)localIterator2.next();
/* 219:229 */       LList<PairOfEntities> bindings = bam.matchRuleToInstantiation(rawRule, t);
/* 220:230 */       if ((bindings != null) && 
/* 221:231 */         (!matchedRules.containsDeprecated(rawRule))) {
/* 222:232 */         matchedRules.addElement(rawRule);
/* 223:    */       }
/* 224:    */     }
/* 225:238 */     return matchedRules;
/* 226:    */   }
/* 227:    */   
/* 228:    */   private Sequence getInstRuleBundle(String name, ArrayList<AugmentedReflectionDescription> descriptions)
/* 229:    */   {
/* 230:246 */     Sequence rules = new Sequence();
/* 231:247 */     for (AugmentedReflectionDescription x : descriptions) {
/* 232:248 */       if (name == null) {
/* 233:249 */         rules.addAll(x.getReflectionDescription().getRules());
/* 234:251 */       } else if (name.equals(x.getReflectionDescription().getName())) {
/* 235:252 */         rules = x.getReflectionDescription().getRules();
/* 236:    */       }
/* 237:    */     }
/* 238:255 */     return rules;
/* 239:    */   }
/* 240:    */   
/* 241:    */   private Sequence getRawRuleBundle(String name, ArrayList<AugmentedReflectionDescription> descriptions)
/* 242:    */   {
/* 243:259 */     Sequence rules = new Sequence();
/* 244:260 */     for (AugmentedReflectionDescription x : descriptions) {
/* 245:261 */       if (name == null) {
/* 246:262 */         rules.addAll(x.getRawRules());
/* 247:264 */       } else if (name.equals(x.getReflectionDescription().getName())) {
/* 248:265 */         rules = x.getRawRules();
/* 249:    */       }
/* 250:    */     }
/* 251:268 */     return rules;
/* 252:    */   }
/* 253:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     leonidFedorov.RuleToReflectionMatcher
 * JD-Core Version:    0.7.0.1
 */