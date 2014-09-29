/*  1:   */ package memory.time;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import frames.Frame;
/*  5:   */ import java.util.Date;
/*  6:   */ 
/*  7:   */ @Deprecated
/*  8:   */ public class TimeInstant
/*  9:   */   extends Frame
/* 10:   */ {
/* 11:   */   private Date time;
/* 12:   */   
/* 13:   */   public TimeInstant(Date time)
/* 14:   */   {
/* 15:15 */     this.time = time;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Date getDate()
/* 19:   */   {
/* 20:18 */     return (Date)this.time.clone();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Entity getThing()
/* 24:   */   {
/* 25:21 */     return null;
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.time.TimeInstant
 * JD-Core Version:    0.7.0.1
 */