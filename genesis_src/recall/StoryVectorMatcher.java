/*  1:   */ package recall;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.HashMap;
/*  5:   */ import java.util.TreeSet;
/*  6:   */ 
/*  7:   */ public class StoryVectorMatcher
/*  8:   */ {
/*  9:   */   public static TreeSet<MatchWrapper> findBestMatches(StoryVectorWrapper probe, ArrayList<StoryVectorWrapper> candidates)
/* 10:   */   {
/* 11:13 */     if ((probe == null) || (candidates == null)) {
/* 12:14 */       return null;
/* 13:   */     }
/* 14:16 */     TreeSet<MatchWrapper> result = new TreeSet();
/* 15:17 */     for (StoryVectorWrapper candidate : candidates) {
/* 16:18 */       if ((probe.getTitle() != null) && (candidate.getTitle() != null) && (!probe.getTitle().equalsIgnoreCase(candidate.getTitle()))) {
/* 17:22 */         result.add(getMatchWrapper(probe, candidate));
/* 18:   */       }
/* 19:   */     }
/* 20:24 */     return result;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public static MatchWrapper getMatchWrapper(StoryVectorWrapper p1, StoryVectorWrapper p2)
/* 24:   */   {
/* 25:28 */     int l1 = 0;
/* 26:29 */     double sum = 0.0D;
/* 27:30 */     TreeSet<MatchContribution> contributions = new TreeSet();
/* 28:   */     int v1;
/* 29:31 */     for (String s : p1.getMap().keySet())
/* 30:   */     {
/* 31:32 */       v1 = p1.getValue(s);
/* 32:33 */       l1 = (int)(l1 + Math.pow(v1, 2.0D));
/* 33:   */     }
/* 34:35 */     int l2 = 0;
/* 35:36 */     for (String s : p2.getMap().keySet())
/* 36:   */     {
/* 37:37 */       int v2 = p2.getValue(s);
/* 38:38 */       l2 = (int)(l2 + Math.pow(v2, 2.0D));
/* 39:   */     }
/* 40:40 */     double denominator = p1.getLength() * p2.getLength();
/* 41:41 */     for (String s : p1.getMap().keySet())
/* 42:   */     {
/* 43:42 */       int v1 = p1.getValue(s);
/* 44:43 */       int v2 = p2.getValue(s);
/* 45:44 */       double result = 0.0D;
/* 46:45 */       if (denominator > 0.0D) {
/* 47:46 */         result = v1 * v2 / denominator;
/* 48:   */       }
/* 49:48 */       sum += result;
/* 50:49 */       contributions.add(new MatchContribution(s, result));
/* 51:   */     }
/* 52:51 */     return new MatchWrapper(sum, contributions, p1, p2);
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     recall.StoryVectorMatcher
 * JD-Core Version:    0.7.0.1
 */