/*   1:    */ package summarizer;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import connections.Connections;
/*   7:    */ import connections.Ports;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.HashSet;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Set;
/*  12:    */ import java.util.Vector;
/*  13:    */ import parameters.Switch;
/*  14:    */ import persistence.JCheckBoxWithMemory;
/*  15:    */ import storyProcessor.ReflectionDescription;
/*  16:    */ import utils.Mark;
/*  17:    */ 
/*  18:    */ public class Persuader
/*  19:    */   extends Summarizer
/*  20:    */ {
/*  21: 20 */   public static String COMMAND = "command";
/*  22:    */   private static Persuader persuader;
/*  23:    */   BetterSignal previousSignal;
/*  24:    */   
/*  25:    */   public static Persuader getPersuader()
/*  26:    */   {
/*  27: 31 */     if (persuader == null) {
/*  28: 32 */       persuader = new Persuader();
/*  29:    */     }
/*  30: 34 */     return persuader;
/*  31:    */   }
/*  32:    */   
/*  33:    */   private Persuader()
/*  34:    */   {
/*  35: 38 */     Connections.getPorts(this).addSignalProcessor("processSignal");
/*  36: 39 */     Connections.getPorts(this).addSignalProcessor(COMMAND, "processCommand");
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void processCommand(Object signal)
/*  40:    */   {
/*  41: 43 */     if ((signal instanceof BetterSignal))
/*  42:    */     {
/*  43: 44 */       BetterSignal bs = (BetterSignal)signal;
/*  44: 45 */       if ((bs.get(0, String.class) == "persuade") && (this.previousSignal != null))
/*  45:    */       {
/*  46: 46 */         Entity e = (Entity)bs.get(1, Entity.class);
/*  47: 47 */         Mark.say(new Object[] {"Bingo, I better do something with previous story, specifically show", e });
/*  48:    */         
/*  49: 49 */         String mode = (String)this.previousSignal.get(0, String.class);
/*  50: 50 */         Sequence explicit = (Sequence)this.previousSignal.get(1, Sequence.class);
/*  51: 51 */         Sequence complete = (Sequence)this.previousSignal.get(2, Sequence.class);
/*  52: 52 */         List<ReflectionDescription> conceptDescriptions = (List)this.previousSignal.get(3, List.class);
/*  53: 53 */         composeSummaryForPersuation(explicit, complete, conceptDescriptions);
/*  54:    */       }
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void processSignal(Object signal)
/*  59:    */   {
/*  60: 59 */     if ((signal instanceof BetterSignal))
/*  61:    */     {
/*  62: 61 */       Mark.say(new Object[] {"Recording signal in persuader" });
/*  63:    */       
/*  64: 63 */       this.previousSignal = ((BetterSignal)signal);
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void composeSummaryForPersuation(Sequence explitStorySequence, Sequence completeStorySequence, List<ReflectionDescription> conceptDescriptions)
/*  69:    */   {
/*  70: 75 */     ArrayList<ReflectionDescription> relevantConcepts = limitToRelevantConcepts(conceptDescriptions);
/*  71:    */     
/*  72: 77 */     Set<String> names = extractNames(relevantConcepts);
/*  73:    */     
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82: 87 */     Set<Entity> keepers = removeIfInCause(keepIfConceptFeeder(completeStorySequence, relevantConcepts));
/*  83:    */     
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88: 93 */     tell(explitStorySequence, completeStorySequence, keepers, names);
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected ArrayList<ReflectionDescription> limitToRelevantConcepts(List<ReflectionDescription> conceptDescriptions)
/*  92:    */   {
/*  93:101 */     ArrayList<ReflectionDescription> relevantConcepts = new ArrayList();
/*  94:102 */     int maxSize = 0;
/*  95:103 */     for (ReflectionDescription candidate : conceptDescriptions)
/*  96:    */     {
/*  97:105 */       int size = 0;
/*  98:    */       
/*  99:107 */       size = candidate.getStoryElementsInvolved().getElements().size();
/* 100:109 */       if (size == maxSize)
/* 101:    */       {
/* 102:110 */         relevantConcepts.add(candidate);
/* 103:    */       }
/* 104:113 */       else if (size > maxSize)
/* 105:    */       {
/* 106:114 */         maxSize = size;
/* 107:115 */         relevantConcepts.clear();
/* 108:116 */         relevantConcepts.add(candidate);
/* 109:    */       }
/* 110:    */     }
/* 111:122 */     return relevantConcepts;
/* 112:    */   }
/* 113:    */   
/* 114:    */   private void tell(Sequence explicitStorySequence, Sequence completeStorySequence, Set<Entity> relevantElements, Set<String> conceptNames)
/* 115:    */   {
/* 116:127 */     Set<Entity> entities = getEntities(relevantElements);
/* 117:128 */     Set<Entity> roleElements = new HashSet();
/* 118:129 */     if (Switch.includeAgentRolesInSummary.isSelected()) {
/* 119:130 */       roleElements = extractRoleElements(completeStorySequence, entities);
/* 120:    */     }
/* 121:134 */     if (Switch.meansProcessing.isSelected()) {
/* 122:135 */       relevantElements = filterUsingMeansSuppression(relevantElements);
/* 123:    */     }
/* 124:138 */     if (Switch.abductionProcessing.isSelected()) {
/* 125:139 */       relevantElements = filterUsingAbductionSuppression(relevantElements);
/* 126:    */     }
/* 127:143 */     relevantElements = removeIfInCause(relevantElements);
/* 128:    */     
/* 129:145 */     Sequence elementsRetained = filterUsingRelevantElements(completeStorySequence, relevantElements, roleElements);
/* 130:148 */     if (Switch.postHocProcessing.isSelected()) {
/* 131:149 */       elementsRetained = filterUsingPostHocErgoPropterHoc(elementsRetained);
/* 132:    */     }
/* 133:152 */     String name = completeStorySequence.getType();
/* 134:    */     
/* 135:154 */     int originalSize = storySize(explicitStorySequence);
/* 136:    */     
/* 137:156 */     int summarySize = storySize(elementsRetained);
/* 138:    */     
/* 139:158 */     String revisedStory = composeStoryEnglishFromEntities(elementsRetained);
/* 140:    */     
/* 141:160 */     Mark.say(new Object[] {"Sizes", Integer.valueOf(originalSize), Integer.valueOf(summarySize), Integer.valueOf(revisedStory.split("\\.").length) });
/* 142:    */     
/* 143:    */ 
/* 144:    */ 
/* 145:    */ 
/* 146:165 */     Mark.say(new Object[] {"Story produced by persuader:" });
/* 147:166 */     for (String s : revisedStory.split("\\.")) {
/* 148:167 */       Mark.say(new Object[] {s.trim() });
/* 149:    */     }
/* 150:    */   }
/* 151:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     summarizer.Persuader
 * JD-Core Version:    0.7.0.1
 */