/*   1:    */ package matchers;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Relation;
/*   6:    */ import bridge.reps.entities.Sequence;
/*   7:    */ import connections.Connections;
/*   8:    */ import connections.Ports;
/*   9:    */ import connections.WiredBox;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Collection;
/*  12:    */ import java.util.Vector;
/*  13:    */ import minilisp.LList;
/*  14:    */ import utils.Mark;
/*  15:    */ import utils.PairOfEntities;
/*  16:    */ 
/*  17:    */ public class RuleMatcher
/*  18:    */   extends RuleMatcherFoundation
/*  19:    */ {
/*  20: 23 */   public static Sequence rulesUsedToPredict = new Sequence();
/*  21:    */   
/*  22:    */   public static synchronized void predict(Entity newStoryElement, int sceneMarker, Sequence story, WiredBox processor, Collection<Relation> rules)
/*  23:    */   {
/*  24: 30 */     for (Relation rule : rules)
/*  25:    */     {
/*  26: 31 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Working on", newStoryElement.asString(), "with", rule.asString() });
/*  27: 32 */       predictWithGivenRule(newStoryElement, sceneMarker, story, processor, rule);
/*  28:    */     }
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static void predictWithGivenRule(Entity newStoryElement, int sceneMarker, Sequence story, WiredBox processor, Relation rule)
/*  32:    */   {
/*  33: 53 */     RuleMatcherFoundation.startPredictionWithGivenRule(newStoryElement, sceneMarker, story, processor, rule);
/*  34:    */   }
/*  35:    */   
/*  36: 61 */   public static Sequence RulesUsedToExplain = new Sequence();
/*  37:    */   
/*  38:    */   public static void explain(Entity element, Sequence story, WiredBox processor, ArrayList<Relation> rules)
/*  39:    */   {
/*  40: 64 */     Mark.say(
/*  41:    */     
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65: 89 */       new Object[] { Boolean.valueOf(debugMay), "Entering explain" });ArrayList<BetterSignal> satisfiers = findSatisfiers(element, story, processor, rules);
/*  66: 66 */     if (satisfiers == null) {
/*  67: 67 */       return;
/*  68:    */     }
/*  69: 69 */     if (satisfiers.size() == 0)
/*  70:    */     {
/*  71: 70 */       Mark.say(new Object[] {Boolean.valueOf(debugMay), "No triggered rules, no action" });
/*  72: 71 */       return;
/*  73:    */     }
/*  74: 74 */     if ((!allowMultipleExplanations) && (satisfiers.size() == 1))
/*  75:    */     {
/*  76: 75 */       Mark.say(new Object[] {Boolean.valueOf(debugMay), "Noting", Integer.valueOf(satisfiers.size()), "satisfier(s) for", element.asString() });
/*  77: 76 */       for (int i = 0; i < satisfiers.size(); i++)
/*  78:    */       {
/*  79: 77 */         Mark.say(new Object[] {Boolean.valueOf(debugMay), "A triggered rule, explained " + element.getName() });
/*  80: 78 */         Connections.getPorts(processor).transmit(satisfiers.get(i));
/*  81:    */       }
/*  82:    */     }
/*  83: 81 */     else if ((allowMultipleExplanations) && (satisfiers.size() >= 1))
/*  84:    */     {
/*  85: 82 */       Mark.say(new Object[] {Boolean.valueOf(debugMay), "Noting", Integer.valueOf(satisfiers.size()), "satisfier(s) for", element.asString() });
/*  86: 83 */       for (int i = 0; i < satisfiers.size(); i++)
/*  87:    */       {
/*  88: 84 */         Mark.say(new Object[] {Boolean.valueOf(debugMay), "A triggered rule, explained " + element.getName() });
/*  89: 85 */         Connections.getPorts(processor).transmit(satisfiers.get(i));
/*  90:    */       }
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public static boolean censor(Entity element, Sequence story, ArrayList<Entity> rules)
/*  95:    */   {
/*  96: 92 */     ArrayList<Relation> list = new ArrayList();
/*  97: 93 */     for (Entity t : rules) {
/*  98: 94 */       if (t.relationP()) {
/*  99: 95 */         list.add((Relation)t);
/* 100:    */       }
/* 101:    */     }
/* 102: 98 */     ArrayList<BetterSignal> satisfiers = findSatisfiers(element, story, null, list);
/* 103: 99 */     if ((satisfiers == null) || (satisfiers.isEmpty())) {
/* 104:100 */       return false;
/* 105:    */     }
/* 106:104 */     return true;
/* 107:    */   }
/* 108:    */   
/* 109:    */   private static ArrayList<BetterSignal> findSatisfiers(Entity element, Sequence story, WiredBox processor, ArrayList<Relation> rules)
/* 110:    */   {
/* 111:108 */     ArrayList<Entity> explanations = new ArrayList();
/* 112:    */     
/* 113:    */ 
/* 114:    */ 
/* 115:112 */     Mark.say(new Object[] {Boolean.valueOf((debugCensor) && (rules.size() > 0) ? 1 : false), "The trigger: " + element.asString(), rules.size() + " rule(s)" });
/* 116:    */     
/* 117:114 */     ArrayList<BetterSignal> satisfiers = new ArrayList();
/* 118:118 */     for (Relation rule : rules) {
/* 119:120 */       if ((rule.isA("explanation")) || (rule.isA("censor")))
/* 120:    */       {
/* 121:123 */         Mark.say(new Object[] {Boolean.valueOf(debugCensor), ">>> Working on may rule", rule.asString() });
/* 122:124 */         Vector<Entity> unsatisfiedAntecedants = ((Sequence)rule.getSubject()).getElements();
/* 123:125 */         Vector<Entity> satisfiedAntecedants = new Vector();
/* 124:126 */         Entity consequent = rule.getObject();
/* 125:    */         
/* 126:128 */         LList<PairOfEntities> bindings = new LList();
/* 127:    */         
/* 128:    */ 
/* 129:131 */         LList<PairOfEntities> newBindings = null;
/* 130:132 */         if (rule.isA("censor")) {
/* 131:135 */           newBindings = StandardMatcher.getBasicMatcher().matchNegation(consequent, element, bindings);
/* 132:    */         } else {
/* 133:138 */           newBindings = StandardMatcher.getBasicMatcher().match(consequent, element, bindings);
/* 134:    */         }
/* 135:140 */         if (newBindings != null)
/* 136:    */         {
/* 137:141 */           Mark.say(new Object[] {Boolean.valueOf(debugCensor), "Bindings with consequent", newBindings });
/* 138:142 */           Mark.say(new Object[] {Boolean.valueOf(debugMay), "Size of binding set : " + newBindings.size() });
/* 139:143 */           if (newBindings.size() > 10)
/* 140:    */           {
/* 141:144 */             Mark.say(new Object[] {Boolean.valueOf(debugMay), "Binding set too large! " + newBindings.size() });
/* 142:145 */             return null;
/* 143:    */           }
/* 144:147 */           ArrayList<Sequence> result = null;
/* 145:    */           Vector<Entity> elements;
/* 146:148 */           if (rule.isA("proximity"))
/* 147:    */           {
/* 148:150 */             Sequence microStory = new Sequence();
/* 149:151 */             elements = story.getElements();
/* 150:152 */             for (int i = 0; i < elements.size(); i++) {
/* 151:153 */               if (element == elements.get(i)) {
/* 152:155 */                 if (i > 0) {
/* 153:156 */                   microStory.addElement((Entity)elements.get(i - 1));
/* 154:    */                 }
/* 155:    */               }
/* 156:    */             }
/* 157:160 */             result = processExplanationRule(microStory, satisfiedAntecedants, unsatisfiedAntecedants, newBindings);
/* 158:    */           }
/* 159:    */           else
/* 160:    */           {
/* 161:163 */             result = processExplanationRule(story, satisfiedAntecedants, unsatisfiedAntecedants, newBindings);
/* 162:    */           }
/* 163:166 */           if ((result != null) && (!result.isEmpty()))
/* 164:    */           {
/* 165:168 */             if (processor != null) {
/* 166:169 */               Connections.getPorts(processor).transmit("used rules port", new BetterSignal(new Object[] { rule }));
/* 167:    */             }
/* 168:173 */             Mark.say(new Object[] {Boolean.valueOf(debugCensor), "Size of result set: " + result.size() });
/* 169:174 */             if (result.size() > 10)
/* 170:    */             {
/* 171:175 */               Mark.say(new Object[] {Boolean.valueOf(debugMay), "Result set too large! " + result.size() });
/* 172:176 */               return null;
/* 173:    */             }
/* 174:179 */             for (Sequence s : result)
/* 175:    */             {
/* 176:182 */               s.addType("conjuction");
/* 177:183 */               Relation instantiatedRule = new Relation("cause", s, element);
/* 178:184 */               instantiatedRule.addType("inference");
/* 179:185 */               instantiatedRule.addType("explanation");
/* 180:186 */               if (rule.isA("proximity")) {
/* 181:187 */                 instantiatedRule.addType("proximity");
/* 182:    */               }
/* 183:190 */               satisfiers.add(new BetterSignal(new Object[] { instantiatedRule, rule }));
/* 184:    */             }
/* 185:    */           }
/* 186:    */         }
/* 187:    */       }
/* 188:    */     }
/* 189:197 */     return satisfiers;
/* 190:    */   }
/* 191:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matchers.RuleMatcher
 * JD-Core Version:    0.7.0.1
 */