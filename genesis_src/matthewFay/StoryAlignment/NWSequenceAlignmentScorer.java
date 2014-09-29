/*   1:    */ package matthewFay.StoryAlignment;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import java.util.List;
/*   5:    */ import matchers.BindingValidator;
/*   6:    */ import matchers.EntityMatcher;
/*   7:    */ import matthewFay.ScoreMatcher;
/*   8:    */ import matthewFay.Utilities.EntityHelper.MatchNode;
/*   9:    */ import matthewFay.Utilities.HashMatrix;
/*  10:    */ import minilisp.LList;
/*  11:    */ import utils.PairOfEntities;
/*  12:    */ 
/*  13:    */ public class NWSequenceAlignmentScorer
/*  14:    */   extends NWAligner<Entity, Entity>
/*  15:    */   implements NodeScorer
/*  16:    */ {
/*  17: 30 */   public static float matchReward = 1.0F;
/*  18: 31 */   public static float gapFactor = 0.0F;
/*  19: 32 */   public static float mismatchPenalty = -1.0E-004F;
/*  20: 34 */   public static float gapPenaltyFactor = -0.0F;
/*  21: 35 */   boolean debugCosts = false;
/*  22:    */   private LList<PairOfEntities> bindings;
/*  23:    */   
/*  24:    */   public static void usePenalizedScoring()
/*  25:    */   {
/*  26: 38 */     matchReward = 1.0F;
/*  27: 39 */     gapFactor = 0.8F;
/*  28: 40 */     mismatchPenalty = -0.5F;
/*  29: 41 */     gapPenaltyFactor = -0.1F;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static void useSimpleScoring()
/*  33:    */   {
/*  34: 45 */     matchReward = 1.0F;
/*  35: 46 */     gapFactor = 0.0F;
/*  36: 47 */     mismatchPenalty = -1.0E-004F;
/*  37: 48 */     gapPenaltyFactor = -0.0F;
/*  38:    */   }
/*  39:    */   
/*  40: 53 */   ScoreMatcher scoreMatcher = new ScoreMatcher();
/*  41: 54 */   EntityMatcher em = new EntityMatcher();
/*  42: 55 */   BindingValidator bv = new BindingValidator();
/*  43:    */   List<Entity> A;
/*  44:    */   List<Entity> B;
/*  45:    */   
/*  46:    */   public NWSequenceAlignmentScorer(List<Entity> A, List<Entity> B)
/*  47:    */   {
/*  48: 62 */     setGapPenalty(gapPenaltyFactor * matchReward);
/*  49: 63 */     this.A = A;
/*  50: 64 */     this.B = B;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public float score(EntityHelper.MatchNode node)
/*  54:    */   {
/*  55: 71 */     SequenceAlignment alignment = align(node);
/*  56: 72 */     node.alignment = alignment;
/*  57: 73 */     return alignment.score;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public SequenceAlignment align(List<PairOfEntities> bindings)
/*  61:    */   {
/*  62: 77 */     ScoreMatcher.useBindingHashes = true;
/*  63: 79 */     if (ScoreMatcher.useBindingHashes) {
/*  64: 80 */       for (PairOfEntities pair : bindings) {
/*  65: 81 */         ScoreMatcher.bindingHashMatrix.put(pair.getPattern().getNameSuffix(), pair.getDatum().getNameSuffix(), Boolean.valueOf(true));
/*  66:    */       }
/*  67:    */     }
/*  68: 86 */     Alignment<Entity, Entity> alignment = align(this.A, this.B);
/*  69: 87 */     SequenceAlignment seqAlignment = new SequenceAlignment(alignment);
/*  70: 88 */     seqAlignment.bindings = new LList();
/*  71: 89 */     for (PairOfEntities binding : bindings) {
/*  72: 90 */       seqAlignment.bindings = seqAlignment.bindings.cons(binding);
/*  73:    */     }
/*  74: 92 */     seqAlignment.aName = "Story A";
/*  75: 93 */     seqAlignment.bName = "Story B";
/*  76:    */     
/*  77:    */ 
/*  78: 96 */     ScoreMatcher.bindingHashMatrix.clear();
/*  79: 97 */     ScoreMatcher.useBindingHashes = false;
/*  80:    */     
/*  81: 99 */     return seqAlignment;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public SequenceAlignment align(EntityHelper.MatchNode node)
/*  85:    */   {
/*  86:103 */     this.bindings = node.bindingSet;
/*  87:    */     
/*  88:105 */     ScoreMatcher.useBindingHashes = true;
/*  89:107 */     if (ScoreMatcher.useBindingHashes) {
/*  90:108 */       for (PairOfEntities pair : node.bindingSet) {
/*  91:109 */         ScoreMatcher.bindingHashMatrix.put(pair.getPattern().getNameSuffix(), pair.getDatum().getNameSuffix(), Boolean.valueOf(true));
/*  92:    */       }
/*  93:    */     }
/*  94:114 */     Alignment<Entity, Entity> alignment = align(this.A, this.B);
/*  95:115 */     SequenceAlignment seqAlignment = new SequenceAlignment(alignment);
/*  96:116 */     seqAlignment.bindings = this.bindings;
/*  97:117 */     seqAlignment.aName = "Story A";
/*  98:118 */     seqAlignment.bName = "Story B";
/*  99:    */     
/* 100:    */ 
/* 101:121 */     ScoreMatcher.bindingHashMatrix.clear();
/* 102:122 */     ScoreMatcher.useBindingHashes = false;
/* 103:    */     
/* 104:124 */     return seqAlignment;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public float sim(Entity pattern, Entity datum)
/* 108:    */   {
/* 109:142 */     float score = matchReward * this.scoreMatcher.scoreMatch(pattern, datum, this.bindings);
/* 110:144 */     if (score > 0.0F) {
/* 111:145 */       score = 1.0F;
/* 112:    */     }
/* 113:158 */     if (score == 0.0F) {
/* 114:159 */       score = matchReward * mismatchPenalty;
/* 115:    */     }
/* 116:163 */     return score;
/* 117:    */   }
/* 118:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.StoryAlignment.NWSequenceAlignmentScorer
 * JD-Core Version:    0.7.0.1
 */