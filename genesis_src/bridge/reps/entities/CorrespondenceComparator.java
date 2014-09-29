/*  1:   */ package bridge.reps.entities;
/*  2:   */ 
/*  3:   */ import java.util.Comparator;
/*  4:   */ 
/*  5:   */ public class CorrespondenceComparator
/*  6:   */   implements Comparator
/*  7:   */ {
/*  8:   */   public static final int ASCENDING = 0;
/*  9:   */   public static final int DESCENDING = 1;
/* 10:   */   public final int comparison;
/* 11:   */   
/* 12:   */   public CorrespondenceComparator(int i)
/* 13:   */   {
/* 14:25 */     if (((i != 0 ? 1 : 0) & (i != 1 ? 1 : 0)) != 0) {
/* 15:26 */       throw new IllegalArgumentException("comparison value " + i + " not recognized.");
/* 16:   */     }
/* 17:28 */     this.comparison = i;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public int compare(Object o1, Object o2)
/* 21:   */   {
/* 22:37 */     Correspondence c1 = (Correspondence)o1;
/* 23:38 */     Correspondence c2 = (Correspondence)o2;
/* 24:   */     
/* 25:40 */     double d1 = c1.getScore();
/* 26:41 */     double d2 = c2.getScore();
/* 27:43 */     if (this.comparison == 0)
/* 28:   */     {
/* 29:44 */       if (d1 > d2) {
/* 30:45 */         return 1;
/* 31:   */       }
/* 32:46 */       if (d1 == d2) {
/* 33:47 */         return Double.compare(c1.hashCode(), c2.hashCode());
/* 34:   */       }
/* 35:49 */       return -1;
/* 36:   */     }
/* 37:51 */     if (this.comparison == 1)
/* 38:   */     {
/* 39:52 */       if (d1 > d2) {
/* 40:53 */         return -1;
/* 41:   */       }
/* 42:54 */       if (d1 == d2) {
/* 43:55 */         return Double.compare(c1.hashCode(), c2.hashCode());
/* 44:   */       }
/* 45:57 */       return 1;
/* 46:   */     }
/* 47:60 */     return 0;
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.CorrespondenceComparator
 * JD-Core Version:    0.7.0.1
 */