/*  1:   */ package memory.time;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import java.util.Comparator;
/*  5:   */ 
/*  6:   */ @Deprecated
/*  7:   */ public class TimeComparator
/*  8:   */   implements Comparator<Entity>
/*  9:   */ {
/* 10:   */   public int compare(Entity o1, Entity o2)
/* 11:   */   {
/* 12:14 */     TimeLine line = TimeLine.getTimeLine();
/* 13:15 */     if ((line.getTimestamp(o1) == 0L) || (line.getTimestamp(o2) == 0L)) {
/* 14:16 */       return 0;
/* 15:   */     }
/* 16:18 */     if (line.getTimestamp(o1) < line.getTimestamp(o2)) {
/* 17:19 */       return -1;
/* 18:   */     }
/* 19:21 */     if (line.getTimestamp(o1) > line.getTimestamp(o2)) {
/* 20:22 */       return 1;
/* 21:   */     }
/* 22:25 */     return 0;
/* 23:   */   }
/* 24:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.time.TimeComparator
 * JD-Core Version:    0.7.0.1
 */