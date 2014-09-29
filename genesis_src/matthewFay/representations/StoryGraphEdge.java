/*  1:   */ package matthewFay.representations;
/*  2:   */ 
/*  3:   */ public class StoryGraphEdge
/*  4:   */ {
/*  5:   */   private String type;
/*  6:   */   private StoryGraphNode antecedent;
/*  7:   */   private StoryGraphNode consequent;
/*  8:   */   
/*  9:   */   public String getEdgeType()
/* 10:   */   {
/* 11: 6 */     return this.type;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public StoryGraphNode getAntecedent()
/* 15:   */   {
/* 16:11 */     return this.antecedent;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public StoryGraphNode getConsequent()
/* 20:   */   {
/* 21:15 */     return this.consequent;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public StoryGraphEdge(StoryGraphNode antecedent, StoryGraphNode consequent, String type)
/* 25:   */   {
/* 26:19 */     this.antecedent = antecedent;
/* 27:20 */     this.consequent = consequent;
/* 28:21 */     this.type = type;
/* 29:22 */     antecedent.addConsequentEdge(this);
/* 30:23 */     consequent.addAntecedentEdge(this);
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.representations.StoryGraphEdge
 * JD-Core Version:    0.7.0.1
 */