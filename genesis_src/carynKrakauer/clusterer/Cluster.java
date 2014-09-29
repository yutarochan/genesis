/*  1:   */ package carynKrakauer.clusterer;
/*  2:   */ 
/*  3:   */ import java.util.Collections;
/*  4:   */ import java.util.Comparator;
/*  5:   */ import java.util.HashMap;
/*  6:   */ import java.util.LinkedList;
/*  7:   */ 
/*  8:   */ public class Cluster
/*  9:   */ {
/* 10:   */   Story mean;
/* 11:   */   LinkedList<Story> stories;
/* 12:   */   HashMap<Story, Difference> meanToStoryDifference;
/* 13:   */   
/* 14:   */   class DifferenceComparator
/* 15:   */     implements Comparator<Story>
/* 16:   */   {
/* 17:   */     DifferenceComparator() {}
/* 18:   */     
/* 19:   */     public int compare(Story o1, Story o2)
/* 20:   */     {
/* 21:17 */       Difference one = (Difference)Cluster.this.meanToStoryDifference.get(o1);
/* 22:18 */       Difference two = (Difference)Cluster.this.meanToStoryDifference.get(o2);
/* 23:20 */       if (one.getValue() > two.getValue()) {
/* 24:21 */         return -1;
/* 25:   */       }
/* 26:23 */       return 1;
/* 27:   */     }
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Cluster(Story mean)
/* 31:   */   {
/* 32:28 */     this.mean = mean;
/* 33:29 */     this.stories = new LinkedList();
/* 34:30 */     this.meanToStoryDifference = new HashMap();
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void setMean(Story newMean)
/* 38:   */   {
/* 39:34 */     this.stories.add(this.mean);
/* 40:35 */     this.mean = newMean;
/* 41:   */     
/* 42:   */ 
/* 43:38 */     this.meanToStoryDifference = new HashMap();
/* 44:39 */     for (Story story : this.stories) {
/* 45:40 */       this.meanToStoryDifference.put(story, new Difference(this.mean, story));
/* 46:   */     }
/* 47:42 */     Collections.sort(this.stories, new DifferenceComparator());
/* 48:   */   }
/* 49:   */   
/* 50:   */   public Story getMean()
/* 51:   */   {
/* 52:46 */     return this.mean;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void addStory(Story story) {}
/* 56:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.clusterer.Cluster
 * JD-Core Version:    0.7.0.1
 */