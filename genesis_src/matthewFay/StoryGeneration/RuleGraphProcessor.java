/*   1:    */ package matthewFay.StoryGeneration;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import connections.AbstractWiredBox;
/*   7:    */ import connections.Connections;
/*   8:    */ import connections.Ports;
/*   9:    */ import java.io.BufferedWriter;
/*  10:    */ import java.io.File;
/*  11:    */ import java.io.FileWriter;
/*  12:    */ import java.io.IOException;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Iterator;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Random;
/*  17:    */ import java.util.Vector;
/*  18:    */ import matthewFay.representations.StoryGraph;
/*  19:    */ import matthewFay.representations.StoryGraphNode;
/*  20:    */ import matthewFay.viewers.StoryGraphViewerFX;
/*  21:    */ import utils.Mark;
/*  22:    */ 
/*  23:    */ public class RuleGraphProcessor
/*  24:    */   extends AbstractWiredBox
/*  25:    */ {
/*  26: 22 */   private static RuleGraphProcessor ruleGraphProcessor = null;
/*  27:    */   private StoryGraph ruleGraph;
/*  28:    */   
/*  29:    */   public static RuleGraphProcessor getRuleGraphProcessor()
/*  30:    */   {
/*  31: 24 */     if (ruleGraphProcessor == null) {
/*  32: 25 */       ruleGraphProcessor = new RuleGraphProcessor();
/*  33:    */     }
/*  34: 27 */     return ruleGraphProcessor;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public RuleGraphProcessor()
/*  38:    */   {
/*  39: 33 */     setName("RuleGraphProcessor");
/*  40:    */     
/*  41: 35 */     Connections.getPorts(this).addSignalProcessor("processRules");
/*  42:    */     
/*  43: 37 */     this.ruleGraph = new StoryGraph();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void processRules(Object o)
/*  47:    */   {
/*  48: 45 */     BetterSignal s = BetterSignal.isSignal(o);
/*  49: 46 */     if (o == null)
/*  50:    */     {
/*  51: 47 */       Mark.err(new Object[] {"Not a good Signal!" });
/*  52: 48 */       return;
/*  53:    */     }
/*  54: 50 */     Sequence rules = (Sequence)s.get(2, Sequence.class);
/*  55: 51 */     Mark.say(new Object[] {"Intantiations: " + rules.asString() });
/*  56: 52 */     Sequence plotUnits = (Sequence)s.get(3, Sequence.class);
/*  57: 53 */     Mark.say(new Object[] {"Plot Units: " + plotUnits });
/*  58: 55 */     for (Entity newRule : rules.getElements())
/*  59:    */     {
/*  60: 56 */       String type = newRule.getType();
/*  61: 57 */       List<Entity> antecedents = getAntecedents(newRule);
/*  62: 58 */       Entity consequent = getConsequent(newRule);
/*  63: 59 */       for (Entity antecedent : antecedents) {
/*  64: 60 */         this.ruleGraph.addEdge(antecedent, consequent, type);
/*  65:    */       }
/*  66: 63 */       if (newRule.isAPrimed("prediction")) {
/*  67: 64 */         this.ruleGraph.getNode(consequent).setPrediction(true);
/*  68:    */       }
/*  69:    */     }
/*  70: 69 */     StoryGraphViewerFX viewer = new StoryGraphViewerFX();
/*  71: 70 */     viewer.init_from_swing(this.ruleGraph, plotUnits, "Rule Graph Viewer");
/*  72:    */   }
/*  73:    */   
/*  74: 73 */   Random r = new Random();
/*  75:    */   
/*  76:    */   public Sequence generateRandomConnectedStory(StoryGraph graph)
/*  77:    */   {
/*  78: 75 */     return generateRandomConnectedStory(graph, 3);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public double scoreStory(StoryGraph graph, Sequence story)
/*  82:    */   {
/*  83: 79 */     double story_score = 0.0D;
/*  84:    */     Iterator localIterator2;
/*  85: 81 */     for (Iterator localIterator1 = story.getElements().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/*  86:    */     {
/*  87: 81 */       Entity elt_origin = (Entity)localIterator1.next();
/*  88: 82 */       localIterator2 = story.getElements().iterator(); continue;Entity elt_target = (Entity)localIterator2.next();
/*  89: 83 */       int distance = graph.distance(elt_origin, elt_target);
/*  90: 84 */       story_score += distance;
/*  91:    */     }
/*  92: 88 */     return story_score / 2.0D;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Sequence generateRandomStory(StoryGraph graph, int length)
/*  96:    */   {
/*  97: 92 */     Sequence story = new Sequence();
/*  98:    */     
/*  99: 94 */     graph.updateDephts();
/* 100:    */     
/* 101: 96 */     int max = graph.getNodeCount();
/* 102:    */     
/* 103: 98 */     int index = this.r.nextInt(max);
/* 104: 99 */     Entity root = (Entity)graph.getAllEntities().get(index);
/* 105:100 */     story.addElement(root);
/* 106:102 */     while (story.getNumberOfChildren() < length)
/* 107:    */     {
/* 108:104 */       index = this.r.nextInt(max);
/* 109:105 */       Entity newElt = (Entity)graph.getAllEntities().get(index);
/* 110:106 */       while (story.containsDeprecated(newElt))
/* 111:    */       {
/* 112:107 */         index = this.r.nextInt(max);
/* 113:108 */         newElt = (Entity)graph.getAllEntities().get(index);
/* 114:    */       }
/* 115:111 */       for (int i = 0; i < story.getNumberOfChildren(); i++)
/* 116:    */       {
/* 117:112 */         Entity elt = story.getElement(i);
/* 118:113 */         if (graph.getNode(newElt).depth <= graph.getNode(elt).depth)
/* 119:    */         {
/* 120:114 */           story.addElement(i, newElt);
/* 121:115 */           break;
/* 122:    */         }
/* 123:    */       }
/* 124:118 */       if (!story.getElements().contains(newElt)) {
/* 125:120 */         story.addElement(newElt);
/* 126:    */       }
/* 127:    */     }
/* 128:123 */     return story;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Sequence generateRandomConnectedStory(StoryGraph graph, int length)
/* 132:    */   {
/* 133:127 */     Sequence story = new Sequence();
/* 134:    */     
/* 135:129 */     graph.updateDephts();
/* 136:    */     
/* 137:131 */     int max = graph.getNodeCount();
/* 138:    */     
/* 139:133 */     int index = this.r.nextInt(max);
/* 140:134 */     Entity root = (Entity)graph.getAllEntities().get(index);
/* 141:135 */     story.addElement(root);
/* 142:137 */     while (story.getNumberOfChildren() < length)
/* 143:    */     {
/* 144:138 */       List<Entity> pool = new ArrayList();
/* 145:140 */       for (Entity elt : story.getElements())
/* 146:    */       {
/* 147:141 */         pool.addAll(graph.getAntecedents(elt));
/* 148:142 */         pool.addAll(graph.getConsequents(elt));
/* 149:    */       }
/* 150:145 */       int failures = 0;
/* 151:146 */       index = this.r.nextInt(pool.size());
/* 152:147 */       Entity newElt = (Entity)pool.get(index);
/* 153:148 */       while (story.containsDeprecated(newElt))
/* 154:    */       {
/* 155:149 */         failures++;
/* 156:150 */         if (failures > 50) {
/* 157:    */           break;
/* 158:    */         }
/* 159:152 */         index = this.r.nextInt(pool.size());
/* 160:153 */         newElt = (Entity)pool.get(index);
/* 161:    */       }
/* 162:156 */       for (int i = 0; i < story.getNumberOfChildren(); i++)
/* 163:    */       {
/* 164:157 */         Entity elt = story.getElement(i);
/* 165:158 */         if (graph.getNode(newElt).depth <= graph.getNode(elt).depth)
/* 166:    */         {
/* 167:159 */           story.addElement(i, newElt);
/* 168:160 */           break;
/* 169:    */         }
/* 170:    */       }
/* 171:163 */       if (!story.getElements().contains(newElt)) {
/* 172:165 */         story.addElement(newElt);
/* 173:    */       }
/* 174:    */     }
/* 175:169 */     return story;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void saveStories(StoryGraph graph, List<Sequence> stories)
/* 179:    */   {
/* 180:173 */     int max = 0;
/* 181:174 */     for (Sequence story : stories) {
/* 182:175 */       max = Math.max(story.getNumberOfChildren(), max);
/* 183:    */     }
/* 184:177 */     String csv = "";
/* 185:178 */     for (int i = 0; i < max; i++)
/* 186:    */     {
/* 187:179 */       csv = csv + "Element " + i;
/* 188:180 */       if (i + 1 != max) {
/* 189:181 */         csv = csv + ",";
/* 190:    */       }
/* 191:    */     }
/* 192:183 */     csv = csv + ", Score";
/* 193:184 */     for (Sequence story : stories)
/* 194:    */     {
/* 195:185 */       csv = csv + "\n";
/* 196:186 */       for (int i = 0; i < story.getNumberOfChildren(); i++)
/* 197:    */       {
/* 198:187 */         Entity elt = story.getElement(i);
/* 199:188 */         csv = csv + elt.toEnglish();
/* 200:189 */         if (i + 1 != max) {
/* 201:190 */           csv = csv + ",";
/* 202:    */         }
/* 203:    */       }
/* 204:192 */       csv = csv + ", " + scoreStory(graph, story);
/* 205:    */     }
/* 206:194 */     Mark.say(new Object[] {csv });
/* 207:    */     try
/* 208:    */     {
/* 209:196 */       File file = new File("stories.csv");
/* 210:197 */       if (!file.exists()) {
/* 211:198 */         file.createNewFile();
/* 212:    */       }
/* 213:201 */       FileWriter fw = new FileWriter(file.getAbsoluteFile());
/* 214:202 */       BufferedWriter bw = new BufferedWriter(fw);
/* 215:203 */       bw.write(csv);
/* 216:204 */       bw.close();
/* 217:    */     }
/* 218:    */     catch (IOException e)
/* 219:    */     {
/* 220:207 */       e.printStackTrace();
/* 221:    */     }
/* 222:    */   }
/* 223:    */   
/* 224:    */   private List<Entity> getAntecedents(Entity rule)
/* 225:    */   {
/* 226:212 */     Entity s = rule.getSubject();
/* 227:213 */     List<Entity> antecedents = new ArrayList();
/* 228:214 */     if (s.sequenceP()) {
/* 229:215 */       for (Entity a : s.getElements()) {
/* 230:216 */         antecedents.add(a);
/* 231:    */       }
/* 232:    */     }
/* 233:219 */     return antecedents;
/* 234:    */   }
/* 235:    */   
/* 236:    */   private Entity getConsequent(Entity rule)
/* 237:    */   {
/* 238:223 */     return rule.getObject();
/* 239:    */   }
/* 240:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.StoryGeneration.RuleGraphProcessor
 * JD-Core Version:    0.7.0.1
 */