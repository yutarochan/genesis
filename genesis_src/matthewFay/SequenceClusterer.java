/*  1:   */ package matthewFay;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Sequence;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.Iterator;
/*  6:   */ import matthewFay.StoryAlignment.Alignment;
/*  7:   */ import matthewFay.StoryAlignment.MatchTree;
/*  8:   */ import matthewFay.StoryAlignment.NWSequenceAlignmentScorer;
/*  9:   */ import matthewFay.StoryAlignment.SequenceAlignment;
/* 10:   */ import matthewFay.StoryAlignment.SortableAlignmentList;
/* 11:   */ import matthewFay.Utilities.EntityHelper;
/* 12:   */ import matthewFay.Utilities.EntityHelper.MatchNode;
/* 13:   */ import matthewFay.Utilities.HashMatrix;
/* 14:   */ import minilisp.LList;
/* 15:   */ import utils.Mark;
/* 16:   */ import utils.PairOfEntities;
/* 17:   */ 
/* 18:   */ public class SequenceClusterer
/* 19:   */   extends KClusterer<Sequence>
/* 20:   */ {
/* 21:   */   HashMatrix<Sequence, Sequence, Float> matrix;
/* 22:   */   
/* 23:   */   public SequenceClusterer(int k)
/* 24:   */   {
/* 25:22 */     super(k);
/* 26:23 */     this.matrix = new HashMatrix();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public float sim(Sequence a, Sequence b)
/* 30:   */   {
/* 31:28 */     if (this.matrix.contains(a, b)) {
/* 32:29 */       return ((Float)this.matrix.get(a, b)).floatValue();
/* 33:   */     }
/* 34:31 */     LList<PairOfEntities> reflectionBindings = new LList();
/* 35:   */     
/* 36:33 */     NWSequenceAlignmentScorer scorer = new NWSequenceAlignmentScorer(EntityHelper.sequenceToList(a), EntityHelper.sequenceToList(b));
/* 37:   */     
/* 38:35 */     MatchTree matchTree = new MatchTree(EntityHelper.getAllEntities(a), EntityHelper.getAllEntities(b), scorer);
/* 39:   */     
/* 40:37 */     matchTree.primeMatchTree(reflectionBindings);
/* 41:   */     
/* 42:39 */     matchTree.generateMatchTree();
/* 43:   */     
/* 44:41 */     SortableAlignmentList alignments = new SortableAlignmentList();
/* 45:43 */     for (EntityHelper.MatchNode leaf : matchTree.leafNodes)
/* 46:   */     {
/* 47:44 */       SequenceAlignment alignment = scorer.align(leaf);
/* 48:   */       
/* 49:46 */       alignments.add(alignment);
/* 50:   */     }
/* 51:49 */     alignments.sort();
/* 52:   */     
/* 53:   */ 
/* 54:52 */     float score = ((Alignment)alignments.get(0)).score;
/* 55:   */     
/* 56:   */ 
/* 57:   */ 
/* 58:56 */     this.matrix.put(a, b, Float.valueOf(score));
/* 59:   */     
/* 60:58 */     return score;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public static void main(String[] args)
/* 64:   */   {
/* 65:63 */     SequenceClusterer sc = new SequenceClusterer(3);
/* 66:   */     
/* 67:65 */     Mark.say(new Object[] {Demo.ComplexGiveStory().asString() });
/* 68:   */     
/* 69:67 */     ArrayList<Sequence> stories = new ArrayList();
/* 70:68 */     stories.add(Demo.ComplexGiveStory());
/* 71:69 */     stories.add(Demo.ComplexTakeStory());
/* 72:70 */     stories.add(Demo.ExchangeStory());
/* 73:71 */     stories.add(Demo.FleeStory());
/* 74:72 */     stories.add(Demo.FollowStory());
/* 75:73 */     stories.add(Demo.MediatedExchangeStory());
/* 76:74 */     stories.add(Demo.ThrowCatchStory());
/* 77:   */     
/* 78:   */ 
/* 79:   */ 
/* 80:   */ 
/* 81:79 */     Mark.say(new Object[] {"Stories Loaded" });
/* 82:   */     
/* 83:81 */     ArrayList<KClusterer.Cluster<Sequence>> clusters = sc.cluster(stories);
/* 84:   */     
/* 85:83 */     Mark.say(new Object[] {"Clustering Complete" });
/* 86:   */     Iterator localIterator2;
/* 87:85 */     for (Iterator localIterator1 = clusters.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 88:   */     {
/* 89:85 */       KClusterer.Cluster<Sequence> cluster = (KClusterer.Cluster)localIterator1.next();
/* 90:86 */       Mark.say(new Object[] {"---" });
/* 91:87 */       Mark.say(new Object[] {"Average Sim: " + cluster.averageSim });
/* 92:88 */       Mark.say(new Object[] {"Variance: " + cluster.variance });
/* 93:89 */       Mark.say(new Object[] {"Centroid: " + ((Sequence)cluster.Centroid).asString() });
/* 94:90 */       localIterator2 = cluster.iterator(); continue;Sequence s = (Sequence)localIterator2.next();
/* 95:   */       
/* 96:92 */       Mark.say(new Object[] {s.asString() });
/* 97:   */     }
/* 98:   */   }
/* 99:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.SequenceClusterer
 * JD-Core Version:    0.7.0.1
 */