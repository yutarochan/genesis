/*  1:   */ package carynKrakauer.clusterer;
/*  2:   */ 
/*  3:   */ import carynKrakauer.ReflectionLevelMemory;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.LinkedList;
/*  6:   */ import storyProcessor.ReflectionDescription;
/*  7:   */ 
/*  8:   */ public class Clusterer
/*  9:   */ {
/* 10:   */   ReflectionLevelMemory memory;
/* 11:   */   LinkedList<Cluster> clusters;
/* 12:   */   
/* 13:   */   public Clusterer(ReflectionLevelMemory memory)
/* 14:   */   {
/* 15:17 */     this.memory = memory;
/* 16:   */     
/* 17:19 */     this.clusters = new LinkedList();
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void addStory(ArrayList<ReflectionDescription> newStory)
/* 21:   */   {
/* 22:23 */     Story story = new Story(newStory);
/* 23:26 */     if (this.clusters.size() == 0)
/* 24:   */     {
/* 25:27 */       this.clusters.add(new Cluster(story));
/* 26:28 */       return;
/* 27:   */     }
/* 28:31 */     float minDifferenceValue = 3.4028235E+38F;
/* 29:32 */     Cluster minDifferenceCluster = null;
/* 30:34 */     for (Cluster cluster : this.clusters)
/* 31:   */     {
/* 32:35 */       Story mean = cluster.getMean();
/* 33:36 */       Difference difference = new Difference(story, mean);
/* 34:37 */       if (difference.getValue() < minDifferenceValue)
/* 35:   */       {
/* 36:38 */         minDifferenceValue = difference.getValue();
/* 37:39 */         minDifferenceCluster = cluster;
/* 38:   */       }
/* 39:   */     }
/* 40:44 */     if (minDifferenceCluster == null) {
/* 41:45 */       this.clusters.add(new Cluster(story));
/* 42:   */     } else {
/* 43:48 */       minDifferenceCluster.addStory(story);
/* 44:   */     }
/* 45:52 */     rebalance();
/* 46:   */   }
/* 47:   */   
/* 48:   */   private void rebalance() {}
/* 49:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.clusterer.Clusterer
 * JD-Core Version:    0.7.0.1
 */