/*  1:   */ package frames;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import constants.RecognizedRepresentations;
/*  5:   */ 
/*  6:   */ public class TrajectoryFrame
/*  7:   */   extends Frame
/*  8:   */ {
/*  9: 7 */   public static final String FRAMETYPE = (String)RecognizedRepresentations.TRAJECTORY_THING;
/* 10:   */   private Entity thing;
/* 11:   */   
/* 12:   */   public TrajectoryFrame(Entity t)
/* 13:   */   {
/* 14:18 */     if (t.isA(FRAMETYPE)) {
/* 15:19 */       this.thing = t;
/* 16:   */     }
/* 17:   */   }
/* 18:   */   
/* 19:   */   public TrajectoryFrame(TrajectoryFrame f)
/* 20:   */   {
/* 21:23 */     this((Entity)f.getThing().clone());
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Entity getThing()
/* 25:   */   {
/* 26:27 */     return this.thing;
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.TrajectoryFrame
 * JD-Core Version:    0.7.0.1
 */