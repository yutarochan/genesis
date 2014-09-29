/*   1:    */ package matthewFay.StoryAlignment;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Sequence;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import matthewFay.Demo;
/*   8:    */ import matthewFay.Utilities.EntityHelper;
/*   9:    */ import matthewFay.Utilities.EntityHelper.MatchNode;
/*  10:    */ import matthewFay.Utilities.Pair;
/*  11:    */ import minilisp.LList;
/*  12:    */ import utils.Mark;
/*  13:    */ import utils.PairOfEntities;
/*  14:    */ 
/*  15:    */ public class Aligner
/*  16:    */ {
/*  17: 21 */   private static Aligner default_aligner = null;
/*  18:    */   private SequenceAlignment lastReflectionAlignment;
/*  19:    */   private MatchTree matchTree;
/*  20:    */   
/*  21:    */   public static Aligner getAligner()
/*  22:    */   {
/*  23: 23 */     if (default_aligner == null) {
/*  24: 24 */       default_aligner = new Aligner();
/*  25:    */     }
/*  26: 26 */     return default_aligner;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public SortableAlignmentList align(List<Entity> listA, List<Entity> listB)
/*  30:    */   {
/*  31: 30 */     LList<PairOfEntities> bindings = new LList();
/*  32: 31 */     return align(listA, listB, bindings);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public SortableAlignmentList align(List<Entity> listA, List<Entity> listB, List<PairOfEntities> bindings)
/*  36:    */   {
/*  37: 35 */     LList<PairOfEntities> binding_llist = new LList();
/*  38: 36 */     for (PairOfEntities pair : bindings) {
/*  39: 37 */       binding_llist = binding_llist.cons(pair);
/*  40:    */     }
/*  41: 39 */     return align(listA, listB, binding_llist);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public SortableAlignmentList align(List<Entity> listA, List<Entity> listB, LList<PairOfEntities> bindings)
/*  45:    */   {
/*  46: 43 */     Sequence seqA = new Sequence();
/*  47: 44 */     for (Entity element : listA) {
/*  48: 45 */       seqA.addElement(element);
/*  49:    */     }
/*  50: 47 */     Sequence seqB = new Sequence();
/*  51: 48 */     for (Entity element : listB) {
/*  52: 49 */       seqB.addElement(element);
/*  53:    */     }
/*  54: 51 */     return align(seqA, seqB, bindings);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public SortableAlignmentList align(Sequence seqA, Sequence seqB)
/*  58:    */   {
/*  59: 56 */     LList<PairOfEntities> bindings = new LList();
/*  60: 57 */     return align(seqA, seqB, bindings);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public SortableAlignmentList align(Sequence seqA, Sequence seqB, Sequence plotUnitsA, Sequence plotUnitsB)
/*  64:    */   {
/*  65: 62 */     LList<PairOfEntities> bindings = new LList();
/*  66: 63 */     return align(seqA, seqB, plotUnitsA, plotUnitsB, bindings);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public SortableAlignmentList align(Sequence seqA, Sequence seqB, Sequence plotUnitsA, Sequence plotUnitsB, LList<PairOfEntities> bindings)
/*  70:    */   {
/*  71: 68 */     bindings = bindings.append(getPlotUnitBindings(plotUnitsA, plotUnitsB, bindings));
/*  72: 69 */     return align(seqA, seqB, bindings);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public LList<PairOfEntities> getPlotUnitBindings(Sequence plotUnitsA, Sequence plotUnitsB)
/*  76:    */   {
/*  77: 74 */     LList<PairOfEntities> bindings = new LList();
/*  78: 75 */     return getPlotUnitBindings(plotUnitsA, plotUnitsB, bindings);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public SequenceAlignment getLastReflectionAlignment()
/*  82:    */   {
/*  83: 80 */     return this.lastReflectionAlignment;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public LList<PairOfEntities> getPlotUnitBindings(Sequence plotUnitsA, Sequence plotUnitsB, LList<PairOfEntities> bindings)
/*  87:    */   {
/*  88: 84 */     LList<PairOfEntities> plotUnitBindings = new LList();
/*  89:    */     
/*  90:    */ 
/*  91: 87 */     NWStringListAligner plotUnitNameAligner = new NWStringListAligner();
/*  92: 88 */     ArrayList<String> plotUnitTypes1 = new ArrayList();
/*  93: 89 */     ArrayList<String> plotUnitTypes2 = new ArrayList();
/*  94: 90 */     for (int i = 0; i < plotUnitsA.getNumberOfChildren(); i++) {
/*  95: 91 */       plotUnitTypes1.add(plotUnitsA.getElement(i).getType());
/*  96:    */     }
/*  97: 93 */     for (int i = 0; i < plotUnitsB.getNumberOfChildren(); i++) {
/*  98: 94 */       plotUnitTypes2.add(plotUnitsB.getElement(i).getType());
/*  99:    */     }
/* 100: 96 */     Alignment<String, String> plotUnitTypeAlignment = plotUnitNameAligner.align(plotUnitTypes1, plotUnitTypes2);
/* 101:    */     
/* 102:    */ 
/* 103: 99 */     int plotUnitIterator1 = 0;
/* 104:100 */     int plotUnitIterator2 = 0;
/* 105:101 */     for (int alignmentIterator = 0; alignmentIterator < plotUnitTypeAlignment.size(); alignmentIterator++)
/* 106:    */     {
/* 107:102 */       Pair<String, String> pair = (Pair)plotUnitTypeAlignment.get(alignmentIterator);
/* 108:103 */       if ((pair.a != null) || (
/* 109:    */       
/* 110:    */ 
/* 111:    */ 
/* 112:107 */         (pair.a == null) || (
/* 113:    */         
/* 114:    */ 
/* 115:    */ 
/* 116:111 */         (pair.a != null) && (pair.b != null))))
/* 117:    */       {
/* 118:115 */         Sequence plotUnit1 = (Sequence)plotUnitsA.getElement(plotUnitIterator1);
/* 119:116 */         Sequence plotUnit2 = (Sequence)plotUnitsB.getElement(plotUnitIterator2);
/* 120:    */         
/* 121:118 */         SortableAlignmentList alignments = align(plotUnit1, plotUnit2, bindings);
/* 122:121 */         if (alignments.size() > 0)
/* 123:    */         {
/* 124:122 */           SequenceAlignment bestAlignment = (SequenceAlignment)alignments.get(0);
/* 125:123 */           bestAlignment.aName = ((String)pair.a + " - A");
/* 126:124 */           bestAlignment.bName = ((String)pair.b + " - B");
/* 127:126 */           for (PairOfEntities plotUnitPair : bestAlignment.bindings) {
/* 128:127 */             if ((!plotUnitPair.getPattern().getType().equalsIgnoreCase("thing")) && 
/* 129:128 */               (!plotUnitPair.getDatum().getType().equalsIgnoreCase("thing")) && 
/* 130:129 */               (!plotUnitPair.getPattern().getType().equalsIgnoreCase("null")) && 
/* 131:130 */               (!plotUnitPair.getDatum().getType().equalsIgnoreCase("null"))) {
/* 132:131 */               plotUnitBindings = plotUnitBindings.cons(plotUnitPair);
/* 133:    */             }
/* 134:    */           }
/* 135:133 */           this.lastReflectionAlignment = bestAlignment;
/* 136:    */         }
/* 137:    */       }
/* 138:136 */       if (pair.a != null) {
/* 139:137 */         plotUnitIterator1++;
/* 140:    */       }
/* 141:138 */       if (pair.b != null) {
/* 142:139 */         plotUnitIterator2++;
/* 143:    */       }
/* 144:    */     }
/* 145:142 */     return plotUnitBindings;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public MatchTree getLastMatchTree()
/* 149:    */   {
/* 150:147 */     return this.matchTree;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public SortableAlignmentList align(Sequence seqA, Sequence seqB, LList<PairOfEntities> bindings)
/* 154:    */   {
/* 155:152 */     NWSequenceAlignmentScorer scorer = new NWSequenceAlignmentScorer(EntityHelper.sequenceToList(seqA), EntityHelper.sequenceToList(seqB));
/* 156:    */     
/* 157:    */ 
/* 158:155 */     this.matchTree = new MatchTree(EntityHelper.getAllAgents(seqA), EntityHelper.getAllAgents(seqB), scorer);
/* 159:    */     
/* 160:157 */     this.matchTree.primeMatchTree(bindings);
/* 161:    */     
/* 162:159 */     this.matchTree.generateMatchTree();
/* 163:    */     
/* 164:161 */     SortableAlignmentList alignments = new SortableAlignmentList();
/* 165:163 */     for (EntityHelper.MatchNode leaf : this.matchTree.leafNodes)
/* 166:    */     {
/* 167:164 */       SequenceAlignment alignment = scorer.align(leaf);
/* 168:166 */       if ((seqA.getNumberOfChildren() == 0) || (seqB.getNumberOfChildren() == 0))
/* 169:    */       {
/* 170:167 */         Mark.say(new Object[] {"Bad News" });
/* 171:168 */         return alignments;
/* 172:    */       }
/* 173:172 */       if ((seqA.getElement(0).relationP()) && (seqA.getElement(0).getSubject().entityP("you")) && (
/* 174:173 */         (seqA.getElement(0).getObject().functionP("story")) || (seqA.getElement(0).getObject().functionP("reflection")))) {
/* 175:174 */         alignment.aName = seqA.getElement(0).getObject().getSubject().getType();
/* 176:    */       }
/* 177:177 */       if ((seqB.getElement(0).relationP()) && (seqB.getElement(0).getSubject().entityP("you")) && (
/* 178:178 */         (seqB.getElement(0).getObject().functionP("story")) || (seqB.getElement(0).getObject().functionP("reflection")))) {
/* 179:179 */         alignment.bName = seqB.getElement(0).getObject().getSubject().getType();
/* 180:    */       }
/* 181:183 */       alignments.add(alignment);
/* 182:    */     }
/* 183:185 */     alignments.sort();
/* 184:    */     
/* 185:187 */     return alignments;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public SortableAlignmentList alignToPatterns(Sequence seqA, ArrayList<Sequence> patterns)
/* 189:    */   {
/* 190:192 */     SortableAlignmentList patternMatches = new SortableAlignmentList();
/* 191:193 */     for (Sequence pattern : patterns)
/* 192:    */     {
/* 193:194 */       SortableAlignmentList currentMatches = align(seqA, pattern);
/* 194:195 */       patternMatches.add((Alignment)currentMatches.get(0));
/* 195:    */     }
/* 196:197 */     patternMatches.sort();
/* 197:    */     
/* 198:199 */     return patternMatches;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public static void main(String[] args)
/* 202:    */   {
/* 203:203 */     Sequence seqA = Demo.ApproachStory();
/* 204:204 */     Sequence seqB = Demo.CarryStory();
/* 205:    */     
/* 206:206 */     Aligner aligner = new Aligner();
/* 207:207 */     Mark.say(new Object[] {aligner.align(seqA, seqB).get(0) });
/* 208:    */   }
/* 209:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.StoryAlignment.Aligner
 * JD-Core Version:    0.7.0.1
 */