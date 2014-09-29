/*  1:   */ package matthewFay.representations;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.List;
/*  6:   */ 
/*  7:   */ public class StoryGraphNode
/*  8:   */   implements Comparable<StoryGraphNode>
/*  9:   */ {
/* 10:   */   private Entity entity;
/* 11:   */   private List<StoryGraphEdge> antecedentEdges;
/* 12:   */   private List<StoryGraphEdge> consequentEdges;
/* 13:   */   
/* 14:   */   public Entity getEntity()
/* 15:   */   {
/* 16:15 */     return this.entity;
/* 17:   */   }
/* 18:   */   
/* 19:20 */   public int depth = 0;
/* 20:21 */   public int height = -1;
/* 21:22 */   public int order = -1;
/* 22:27 */   private boolean prediction = false;
/* 23:28 */   private boolean active = false;
/* 24:   */   
/* 25:   */   public void setPrediction(boolean value)
/* 26:   */   {
/* 27:30 */     this.prediction = value;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public boolean getPrediction()
/* 31:   */   {
/* 32:33 */     return this.prediction;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public boolean getAssumed()
/* 36:   */   {
/* 37:37 */     return this.entity.hasFeature("assumption");
/* 38:   */   }
/* 39:   */   
/* 40:   */   public boolean getNegated()
/* 41:   */   {
/* 42:41 */     return this.entity.hasFeature("not");
/* 43:   */   }
/* 44:   */   
/* 45:   */   public boolean getActive()
/* 46:   */   {
/* 47:44 */     return this.active;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void setActive(boolean value)
/* 51:   */   {
/* 52:47 */     this.active = value;
/* 53:   */   }
/* 54:   */   
/* 55:   */   void addAntecedentEdge(StoryGraphEdge edge)
/* 56:   */   {
/* 57:53 */     this.antecedentEdges.add(edge);
/* 58:   */   }
/* 59:   */   
/* 60:   */   void addConsequentEdge(StoryGraphEdge edge)
/* 61:   */   {
/* 62:57 */     this.consequentEdges.add(edge);
/* 63:   */   }
/* 64:   */   
/* 65:   */   public StoryGraphNode(Entity entity)
/* 66:   */   {
/* 67:61 */     this.entity = entity;
/* 68:62 */     this.antecedentEdges = new ArrayList();
/* 69:63 */     this.consequentEdges = new ArrayList();
/* 70:   */   }
/* 71:   */   
/* 72:   */   public List<StoryGraphNode> getAntecedents()
/* 73:   */   {
/* 74:67 */     List<StoryGraphNode> antecedents = new ArrayList();
/* 75:68 */     for (StoryGraphEdge edge : this.antecedentEdges) {
/* 76:69 */       antecedents.add(edge.getAntecedent());
/* 77:   */     }
/* 78:71 */     return antecedents;
/* 79:   */   }
/* 80:   */   
/* 81:   */   public List<StoryGraphNode> getConsequents()
/* 82:   */   {
/* 83:75 */     List<StoryGraphNode> consequents = new ArrayList();
/* 84:76 */     for (StoryGraphEdge edge : this.consequentEdges) {
/* 85:77 */       consequents.add(edge.getConsequent());
/* 86:   */     }
/* 87:79 */     return consequents;
/* 88:   */   }
/* 89:   */   
/* 90:   */   public List<StoryGraphNode> getAllNeighbors()
/* 91:   */   {
/* 92:83 */     List<StoryGraphNode> neighbors = new ArrayList();
/* 93:84 */     neighbors.addAll(getAntecedents());
/* 94:85 */     neighbors.addAll(getConsequents());
/* 95:86 */     return neighbors;
/* 96:   */   }
/* 97:   */   
/* 98:   */   public int compareTo(StoryGraphNode o)
/* 99:   */   {
/* :0:91 */     return this.height - o.height;
/* :1:   */   }
/* :2:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.representations.StoryGraphNode
 * JD-Core Version:    0.7.0.1
 */