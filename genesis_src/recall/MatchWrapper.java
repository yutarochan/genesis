/*  1:   */ package recall;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Sequence;
/*  4:   */ import java.util.TreeSet;
/*  5:   */ 
/*  6:   */ public class MatchWrapper
/*  7:   */   implements Comparable
/*  8:   */ {
/*  9:   */   private double similarity;
/* 10:   */   private StoryVectorWrapper probe;
/* 11:   */   private StoryVectorWrapper precedent;
/* 12:   */   private TreeSet<MatchContribution> contributions;
/* 13:   */   
/* 14:   */   public MatchWrapper(double s, TreeSet<MatchContribution> contributions, StoryVectorWrapper p, StoryVectorWrapper c)
/* 15:   */   {
/* 16:23 */     this.similarity = s;
/* 17:24 */     this.probe = p;
/* 18:25 */     this.precedent = c;
/* 19:26 */     this.contributions = contributions;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String toString()
/* 23:   */   {
/* 24:30 */     return "<" + this.similarity + "\n" + this.contributions + "\n" + this.precedent.toString() + "\n>";
/* 25:   */   }
/* 26:   */   
/* 27:   */   public double getSimilarity()
/* 28:   */   {
/* 29:34 */     return this.similarity;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public StoryVectorWrapper getProbe()
/* 33:   */   {
/* 34:38 */     return this.probe;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public StoryVectorWrapper getPrecedent()
/* 38:   */   {
/* 39:42 */     return this.precedent;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public Sequence getStory()
/* 43:   */   {
/* 44:46 */     return getProbe().getStory();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public TreeSet<MatchContribution> getContributions()
/* 48:   */   {
/* 49:50 */     return this.contributions;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public int compareTo(Object p)
/* 53:   */   {
/* 54:54 */     MatchWrapper that = (MatchWrapper)p;
/* 55:55 */     if (this.similarity < that.similarity) {
/* 56:56 */       return 1;
/* 57:   */     }
/* 58:58 */     if (this.similarity > that.similarity) {
/* 59:59 */       return -1;
/* 60:   */     }
/* 61:62 */     return getPrecedent().getTitle().compareTo(that.getPrecedent().getTitle());
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     recall.MatchWrapper
 * JD-Core Version:    0.7.0.1
 */