/*   1:    */ package storyProcessor;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Relation;
/*   6:    */ import bridge.reps.entities.Sequence;
/*   7:    */ import connections.AbstractWiredBox;
/*   8:    */ import connections.Connections;
/*   9:    */ import connections.Ports;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.HashSet;
/*  12:    */ import java.util.List;
/*  13:    */ import matchers.StandardMatcher;
/*  14:    */ import models.MentalModel;
/*  15:    */ import parameters.Switch;
/*  16:    */ import persistence.JCheckBoxWithMemory;
/*  17:    */ import start.Generator;
/*  18:    */ import text.Html;
/*  19:    */ import tools.Innerese;
/*  20:    */ import utils.Mark;
/*  21:    */ 
/*  22:    */ public class TraitExpert
/*  23:    */   extends AbstractWiredBox
/*  24:    */ {
/*  25:    */   public TraitExpert()
/*  26:    */   {
/*  27: 25 */     Connections.getPorts(this).addSignalProcessor("personality port", "decodeAndProcess");
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void decodeAndProcess(Object o)
/*  31:    */   {
/*  32: 29 */     boolean debug = false;
/*  33: 30 */     if ((o instanceof BetterSignal))
/*  34:    */     {
/*  35: 31 */       BetterSignal signal = (BetterSignal)o;
/*  36:    */       
/*  37: 33 */       String key = (String)signal.get(0, String.class);
/*  38: 34 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Personality key", key });
/*  39: 35 */       if (key == "process trait explanations") {
/*  40: 36 */         processAllExplanations((Entity)signal.get(1, Entity.class), (StoryProcessor)signal.get(2, StoryProcessor.class));
/*  41: 38 */       } else if (key == "process trait predictions") {
/*  42: 39 */         processTraitPredictions((Entity)signal.get(1, Entity.class), (StoryProcessor)signal.get(2, StoryProcessor.class));
/*  43: 41 */       } else if (key == "find trait-specific concepts") {
/*  44: 42 */         findTraitSpecificConcepts((Entity)signal.get(1, Entity.class), (StoryProcessor)signal.get(2, StoryProcessor.class));
/*  45: 44 */       } else if (key == "event added") {
/*  46: 45 */         inferPersonalityType((Entity)signal.get(1, Entity.class), (ArrayList)signal.get(2, ArrayList.class));
/*  47: 47 */       } else if (key == "event added with trait") {
/*  48: 48 */         notePersonalityType((Entity)signal.get(1, Entity.class), (MentalModel)signal.get(2, MentalModel.class));
/*  49:    */       }
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void findTraitSpecificConcepts(Entity element, StoryProcessor processor)
/*  54:    */   {
/*  55: 55 */     boolean debug = false;
/*  56:    */     
/*  57: 57 */     ArrayList<MentalModel> personalityModels = processor.getPersonalityModels(element);
/*  58:    */     
/*  59: 59 */     Sequence concepts = new Sequence();
/*  60: 61 */     for (MentalModel personalityModel : personalityModels)
/*  61:    */     {
/*  62: 62 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Getting personality concepts for", element.asString(), "from", personalityModel.getName() });
/*  63: 63 */       BetterSignal signal = new BetterSignal(new Object[] { "deliver trait-specific concepts", personalityModel.getConceptPatterns() });
/*  64: 64 */       Connections.getPorts(this).transmit("personality port", signal);
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void processAllExplanations(Entity element, StoryProcessor processor)
/*  69:    */   {
/*  70: 69 */     boolean debug = false;
/*  71: 73 */     if (Switch.level3ExplantionRules.isSelected())
/*  72:    */     {
/*  73: 77 */       HashSet<Entity> localRuleSet = new HashSet();
/*  74:    */       
/*  75: 79 */       localRuleSet.addAll(processor.getExplanationRules(element));
/*  76: 85 */       if (Switch.level5UseMentalModels.isSelected())
/*  77:    */       {
/*  78: 87 */         ArrayList<MentalModel> personalityModels = processor.getPersonalityModels(element);
/*  79: 89 */         for (MentalModel personalityModel : personalityModels)
/*  80:    */         {
/*  81: 90 */           Connections.wire("new  element port", processor, "port for story element injection into trait-specific mental model", personalityModel
/*  82: 91 */             .getStoryProcessor());
/*  83:    */           
/*  84:    */ 
/*  85: 94 */           ArrayList<Entity> personalityRules = personalityModel.getExplanationRules(element);
/*  86: 95 */           localRuleSet.addAll(personalityRules);
/*  87:    */         }
/*  88:    */       }
/*  89:101 */       ArrayList<Entity> localRules = new ArrayList();
/*  90:102 */       localRules.addAll(localRuleSet);
/*  91:106 */       if (debug) {
/*  92:107 */         for (Entity rule : localRules) {
/*  93:108 */           Mark.say(new Object[] {"Rule:", rule.asString() });
/*  94:    */         }
/*  95:    */       }
/*  96:112 */       Connections.getPorts(this).transmit("to backward chainer port", new BetterSignal(new Object[] { element, localRules, processor.getStory() }));
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void processTraitPredictions(Entity element, StoryProcessor processor)
/* 101:    */   {
/* 102:119 */     boolean debug = false;
/* 103:    */     
/* 104:121 */     HashSet<Entity> localRuleSet = new HashSet();
/* 105:    */     
/* 106:123 */     ArrayList<MentalModel> personalityModels = processor.getPersonalityModels(element);
/* 107:125 */     for (MentalModel personalityModel : personalityModels)
/* 108:    */     {
/* 109:132 */       Connections.wire("new  element port", processor, "port for story element injection into trait-specific mental model", personalityModel
/* 110:133 */         .getStoryProcessor());
/* 111:134 */       ArrayList<Entity> personalityRules = personalityModel.getPredictionRules(element);
/* 112:135 */       if (debug)
/* 113:    */       {
/* 114:136 */         if (personalityRules.isEmpty()) {
/* 115:137 */           Mark.say(new Object[] {"Found NO personality rules for", element.asString() });
/* 116:    */         } else {
/* 117:140 */           Mark.say(new Object[] {"Found personality rules for", element.asString() });
/* 118:    */         }
/* 119:142 */         for (Entity t : personalityRules) {
/* 120:143 */           Mark.say(new Object[] {"        ", t.asString() });
/* 121:    */         }
/* 122:    */       }
/* 123:146 */       localRuleSet.addAll(personalityRules);
/* 124:    */       
/* 125:148 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Provided", Integer.valueOf(localRuleSet.size()), "rules" });
/* 126:    */     }
/* 127:153 */     ArrayList<Entity> localRules = new ArrayList();
/* 128:    */     
/* 129:155 */     Mark.say(new Object[] {Boolean.valueOf(debug), "Adding", Integer.valueOf(localRuleSet.size()), "rules" });
/* 130:156 */     localRules.addAll(localRuleSet);
/* 131:    */     
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:161 */     Connections.getPorts(this).transmit("to forward chainer port", new BetterSignal(new Object[] { element, localRules, processor.getStory(), Integer.valueOf(0) }));
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void notePersonalityType(Entity element, MentalModel personalityModel)
/* 139:    */   {
/* 140:167 */     boolean debug = false;
/* 141:    */     
/* 142:169 */     List<Entity> personalityExamples = personalityModel.getExamples();
/* 143:171 */     for (Entity personalityExample : personalityExamples) {
/* 144:173 */       if (StandardMatcher.getBasicMatcher().match(element, personalityExample) != null)
/* 145:    */       {
/* 146:177 */         Relation r = new Relation("property", element.getSubject(), personalityModel);
/* 147:178 */         r = Innerese.makeBecause(element, new Entity[] { r });
/* 148:179 */         String s = Generator.getGenerator().generate(r);
/* 149:    */         
/* 150:181 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Personality model", personalityModel.getName() });
/* 151:182 */         Mark.say(new Object[] {Boolean.valueOf(debug), s, "\n", element.asStringWithIndexes(), "\n", personalityExample.asStringWithIndexes() });
/* 152:    */         
/* 153:184 */         s = Html.p(s);
/* 154:185 */         BetterSignal signal = new BetterSignal(new Object[] { "Personality analysis", s });
/* 155:    */         
/* 156:187 */         Connections.getPorts(this).transmit("commentary port", signal);
/* 157:    */       }
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void inferPersonalityType(Entity element, ArrayList<MentalModel> models)
/* 162:    */   {
/* 163:199 */     boolean verbose = false;
/* 164:    */     
/* 165:201 */     Entity subject = element.getSubject();
/* 166:204 */     for (MentalModel model : models)
/* 167:    */     {
/* 168:    */       String s;
/* 169:206 */       if (StoryProcessor.exists(subject, "personality_trait", model))
/* 170:    */       {
/* 171:207 */         s = subject.asString() + " is " + model.getName() + " is already known";
/* 172:    */       }
/* 173:    */       else
/* 174:    */       {
/* 175:212 */         List<Entity> memory = model.getExamples();
/* 176:    */         
/* 177:    */ 
/* 178:    */ 
/* 179:216 */         ArrayList<Entity> additions = new ArrayList();
/* 180:218 */         for (Entity rememberedElement : memory) {
/* 181:222 */           if (StandardMatcher.getBasicMatcher().match(element, rememberedElement) != null)
/* 182:    */           {
/* 183:224 */             Relation r = new Relation("property", element.getSubject(), model);
/* 184:225 */             r = Innerese.makeRoleFrame("seem", r);
/* 185:226 */             r = Innerese.makeBecause(r, new Entity[] { element });
/* 186:227 */             String s = Generator.getGenerator().generate(r);
/* 187:    */             
/* 188:229 */             s = Html.p(s);
/* 189:    */             
/* 190:231 */             BetterSignal signal = new BetterSignal(new Object[] { "Personality analysis", s });
/* 191:    */             
/* 192:    */ 
/* 193:234 */             Connections.getPorts(this).transmit("commentary port", signal);
/* 194:    */             
/* 195:236 */             Entity result = new Relation("property", element.getSubject(), model);
/* 196:237 */             result.addType("personality_trait");
/* 197:    */             
/* 198:239 */             Mark.say(new Object[] {Boolean.valueOf(verbose), "About to try to add", result.asString(), "to the story" });
/* 199:240 */             Mark.say(new Object[] {Boolean.valueOf(verbose), "Because", element.asString(), "matches", rememberedElement.asString(), "from", model.getName() });
/* 200:241 */             additions.add(result);
/* 201:    */             
/* 202:243 */             BetterSignal signal2 = new BetterSignal(new Object[] { "deliver trait characteization", result });
/* 203:244 */             Connections.getPorts(this).transmit("personality port", signal2);
/* 204:    */             
/* 205:246 */             break;
/* 206:    */           }
/* 207:    */         }
/* 208:    */       }
/* 209:    */     }
/* 210:    */   }
/* 211:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     storyProcessor.TraitExpert
 * JD-Core Version:    0.7.0.1
 */