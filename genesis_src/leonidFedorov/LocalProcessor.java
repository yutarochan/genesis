/*   1:    */ package leonidFedorov;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Sequence;
/*   5:    */ import connections.AbstractWiredBox;
/*   6:    */ import connections.Connections;
/*   7:    */ import connections.Ports;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.LinkedList;
/*  11:    */ import java.util.Vector;
/*  12:    */ import matchers.StandardMatcher;
/*  13:    */ import minilisp.LList;
/*  14:    */ import storyProcessor.ReflectionAnalysis;
/*  15:    */ import storyProcessor.ReflectionDescription;
/*  16:    */ import utils.Mark;
/*  17:    */ import utils.PairOfEntities;
/*  18:    */ 
/*  19:    */ public class LocalProcessor
/*  20:    */   extends AbstractWiredBox
/*  21:    */ {
/*  22: 18 */   public final String MY_INPUT_PORT = "my input port";
/*  23: 20 */   public final String MY_OUTPUT_PORT = "my output port";
/*  24:    */   public static final String SAMPLE_PORT2 = "sample port2";
/*  25:    */   public static final String UNFOLD_PORT = "unfold port";
/*  26:    */   public static final String REFLECTION_ANALAYSIS1 = "Reflection analysis port 1";
/*  27:    */   public static final String REFLECTION_ANALAYSIS2 = "Reflection analysis port 2";
/*  28:    */   public static final String SINGLE_REFLECTION = "single reflection description port";
/*  29:    */   public static final String RULE_INVERTER = "instantiated to raw rule inverter port";
/*  30: 34 */   Sequence allRules = new Sequence();
/*  31: 35 */   LinkedList<Entity> instReflections1 = new LinkedList();
/*  32: 36 */   LinkedList<Entity> instReflections2 = new LinkedList();
/*  33: 37 */   LinkedList<Entity> singleReflectionRules = new LinkedList();
/*  34: 38 */   LinkedList<Entity> rawRules = new LinkedList();
/*  35: 40 */   private boolean areAllRulesRead = false;
/*  36:    */   
/*  37:    */   public boolean areAllRulesRead()
/*  38:    */   {
/*  39: 43 */     return this.areAllRulesRead;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public LocalProcessor()
/*  43:    */   {
/*  44: 48 */     setName("My unfolding processor");
/*  45: 49 */     Mark.say(new Object[] {"Local Processor Object Created" });
/*  46:    */     
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50: 54 */     Connections.getPorts(this).addSignalProcessor("Reflection analysis port 1", "lookAtReflections1");
/*  51: 55 */     Connections.getPorts(this).addSignalProcessor("Reflection analysis port 2", "lookAtReflections2");
/*  52:    */     
/*  53:    */ 
/*  54: 58 */     Connections.getPorts(this).addSignalProcessor("single reflection description port", "processSingleReflection");
/*  55:    */     
/*  56:    */ 
/*  57: 61 */     Connections.getPorts(this).addSignalProcessor("instantiated to raw rule inverter port", "invertInstToRaw");
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void invertInstToRaw(Object signal)
/*  61:    */   {
/*  62: 69 */     if ((signal instanceof Sequence))
/*  63:    */     {
/*  64: 70 */       Sequence s = (Sequence)signal;
/*  65: 71 */       this.rawRules = ((LinkedList)s.getAllComponents());
/*  66:    */     }
/*  67: 73 */     if ((signal instanceof String))
/*  68:    */     {
/*  69: 74 */       String str = (String)signal;
/*  70: 75 */       if ("radiateRules".equalsIgnoreCase(str))
/*  71:    */       {
/*  72: 76 */         Mark.say(new Object[] {":::==>Rules are radiated  by command: " + str });
/*  73: 77 */         this.areAllRulesRead = true;
/*  74:    */       }
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void processSingleReflection(Object o)
/*  79:    */   {
/*  80: 84 */     if ((o instanceof ReflectionDescription))
/*  81:    */     {
/*  82: 85 */       ReflectionDescription completion = (ReflectionDescription)o;
/*  83: 86 */       Mark.say(new Object[] {"Processing reflection", completion.getName() });
/*  84: 87 */       for (Entity t : completion.getStoryElementsInvolved().getElements())
/*  85:    */       {
/*  86: 88 */         Mark.say(new Object[] {"Rule", t.asString() });
/*  87: 89 */         this.singleReflectionRules.add(t);
/*  88:    */       }
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void lookAtReflections1(Object o)
/*  93:    */   {
/*  94: 99 */     if ((o instanceof ReflectionAnalysis))
/*  95:    */     {
/*  96:100 */       ReflectionAnalysis analysis = (ReflectionAnalysis)o;
/*  97:    */       Iterator localIterator2;
/*  98:101 */       for (Iterator localIterator1 = analysis.getReflectionDescriptions().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/*  99:    */       {
/* 100:101 */         ReflectionDescription description = (ReflectionDescription)localIterator1.next();
/* 101:102 */         Mark.say(new Object[] {"Here are the instantiated rules from processor 1", description.getName() });
/* 102:103 */         localIterator2 = description.getRules().getElements().iterator(); continue;Entity t = (Entity)localIterator2.next();
/* 103:104 */         Mark.say(new Object[] {t.asString() });
/* 104:105 */         this.instReflections1.add(t);
/* 105:    */       }
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void lookAtReflections2(Object o)
/* 110:    */   {
/* 111:113 */     if ((o instanceof ReflectionAnalysis))
/* 112:    */     {
/* 113:114 */       ReflectionAnalysis analysis = (ReflectionAnalysis)o;
/* 114:    */       Iterator localIterator2;
/* 115:115 */       for (Iterator localIterator1 = analysis.getReflectionDescriptions().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 116:    */       {
/* 117:115 */         ReflectionDescription description = (ReflectionDescription)localIterator1.next();
/* 118:116 */         Mark.say(new Object[] {"Here are the instantiated rules from processor 2", description.getName() });
/* 119:117 */         localIterator2 = description.getRules().getElements().iterator(); continue;Entity t = (Entity)localIterator2.next();
/* 120:118 */         Mark.say(new Object[] {t.asString() });
/* 121:119 */         this.instReflections2.add(t);
/* 122:120 */         if (areAllRulesRead())
/* 123:    */         {
/* 124:121 */           Mark.say(new Object[] {"Matching " + description.getName() + "against commonsense" });
/* 125:122 */           StandardMatcher bam = StandardMatcher.getBasicMatcher();
/* 126:    */           Iterator localIterator4;
/* 127:123 */           for (Iterator localIterator3 = this.instReflections2.iterator(); localIterator3.hasNext(); localIterator4.hasNext())
/* 128:    */           {
/* 129:123 */             Entity instRef2 = (Entity)localIterator3.next();
/* 130:124 */             localIterator4 = this.rawRules.iterator(); continue;Entity rawRule = (Entity)localIterator4.next();
/* 131:    */             
/* 132:126 */             LList<PairOfEntities> bindings = bam.matchRuleToInstantiation(rawRule, instRef2);
/* 133:127 */             if (bindings != null) {
/* 134:128 */               Mark.say(new Object[] {":::==>something matched " + bindings.toString() });
/* 135:    */             }
/* 136:    */           }
/* 137:    */         }
/* 138:    */       }
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void unfoldProcessor(Object signal)
/* 143:    */   {
/* 144:139 */     if ((signal instanceof String))
/* 145:    */     {
/* 146:140 */       String str = (String)signal;
/* 147:141 */       if ("theEnd".equalsIgnoreCase(str)) {
/* 148:142 */         Mark.say(new Object[] {"END REACHED " + str });
/* 149:    */       }
/* 150:    */     }
/* 151:145 */     else if ((signal instanceof Entity))
/* 152:    */     {
/* 153:146 */       Entity t = (Entity)signal;
/* 154:147 */       if (t.sequenceP())
/* 155:    */       {
/* 156:148 */         Sequence s = (Sequence)t;
/* 157:    */         
/* 158:150 */         Integer ruleNumber = Integer.valueOf(0);
/* 159:151 */         for (Entity e : s.getAllComponents()) {
/* 160:152 */           if (e.isA("prediction"))
/* 161:    */           {
/* 162:153 */             this.allRules.addElement(e);
/* 163:154 */             ruleNumber = Integer.valueOf(ruleNumber.intValue() + 1);
/* 164:155 */             Mark.say(new Object[] {ruleNumber.toString() + ":: " + e.asString() });
/* 165:    */           }
/* 166:    */         }
/* 167:158 */         Connections.getPorts(this).transmit("my output port", this.allRules);
/* 168:159 */         Mark.say(new Object[] {"all" + this.allRules.asString() });
/* 169:    */       }
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void extractPredictions(Object signal)
/* 174:    */   {
/* 175:177 */     Mark.say(
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
/* 205:207 */       new Object[] { "Signal received" });
/* 206:180 */     if ((signal instanceof Entity))
/* 207:    */     {
/* 208:181 */       Entity t = (Entity)signal;
/* 209:182 */       if (t.sequenceP())
/* 210:    */       {
/* 211:185 */         Vector<Entity> predictions = new Vector();
/* 212:186 */         Sequence s = (Sequence)t;
/* 213:    */         
/* 214:    */ 
/* 215:189 */         Integer predictionNumber = Integer.valueOf(0);
/* 216:190 */         for (Entity e : s.getAllComponents()) {
/* 217:193 */           if (e.isA("prediction"))
/* 218:    */           {
/* 219:195 */             predictionNumber = Integer.valueOf(predictionNumber.intValue() + 1);
/* 220:196 */             Mark.say(new Object[] {predictionNumber.toString() + ":: " + e.asString() });
/* 221:    */           }
/* 222:    */         }
/* 223:200 */         Sequence p = new Sequence();
/* 224:201 */         p.setElements(predictions);
/* 225:    */         
/* 226:    */ 
/* 227:204 */         Connections.getPorts(this).transmit("my output port", p);
/* 228:    */       }
/* 229:    */     }
/* 230:    */   }
/* 231:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     leonidFedorov.LocalProcessor
 * JD-Core Version:    0.7.0.1
 */