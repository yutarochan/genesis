/*  1:   */ package frames;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Bundle;
/*  4:   */ import bridge.reps.entities.Entity;
/*  5:   */ import bridge.reps.entities.Thread;
/*  6:   */ 
/*  7:   */ public class QualifierFrame
/*  8:   */   extends Frame
/*  9:   */ {
/* 10:   */   Entity thing;
/* 11:   */   
/* 12:   */   public Entity getThing()
/* 13:   */   {
/* 14:15 */     return this.thing;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Entity getSubjectThing()
/* 18:   */   {
/* 19:22 */     return this.thing.getSubject();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getQualification()
/* 23:   */   {
/* 24:26 */     return (String)this.thing.getBundle().getThread("Qualification").get(1);
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.QualifierFrame
 * JD-Core Version:    0.7.0.1
 */